package com.gasczoology.invertebratelab.ui

import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIAnatomyReviewDescriptions
import com.gasczoology.invertebratelab.data.NativeRasterCalibration
import com.gasczoology.invertebratelab.data.NativeDraftPixelRefinement
import com.gasczoology.invertebratelab.data.NativeSourcePixelEvidence
import com.gasczoology.invertebratelab.data.NativeDraftFinding
import com.gasczoology.invertebratelab.data.RasterPixelLocation
import java.security.MessageDigest
import java.util.Locale
import org.json.JSONArray
import org.json.JSONObject

/**
 * U1.9 — ANDROID DEBUG-BUILD-ONLY anatomical plate coordinate CALIBRATION.
 *
 * A reviewer points at a source raster and exports unapproved coordinates.
 * There are NO pre-authorised organ hotspots, anatomical leader lines or
 * silent production activation. The source image itself is never repainted.
 */
private data class DraftRasterMark(
    val structureId: String,
    val point: RasterPixelLocation,
    val finding: NativeDraftFinding = NativeDraftFinding.NOT_REVIEWED,
    val note: String = \"\",
)

internal fun isNativeSourceAuditDebuggable(flags: Int): Boolean =
    flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

private fun rasterSha256(context: android.content.Context, organism: String): String? {
    return runCatching {
        val digest = MessageDigest.getInstance("SHA-256")
        context.assets.open("unit1/references/" + organism + ".png").use { stream ->
            val buffer = ByteArray(8192)
            while (true) {
                val count = stream.read(buffer)
                if (count < 0) break
                digest.update(buffer, 0, count)
            }
        }
        digest.digest().joinToString("") { "%02x".format(it) }
    }.getOrNull()
}

private fun reviewJson(
    organism: String,
    sourceId: String,
    checksum: String,
    morphotype: String,
    bitmap: ImageBitmap,
    marks: List<DraftRasterMark>,
): String {
    val array = JSONArray()
    marks.forEach { mark ->
        val crop = NativeSourcePixelEvidence.window(
            mark.point.pixelX, mark.point.pixelY, bitmap.width, bitmap.height
        )
        val cropJson = if (crop == null) JSONObject.NULL else JSONObject()
            .put("left", crop.left)
            .put("top", crop.top)
            .put("width", crop.width)
            .put("height", crop.height)
        array.put(
            JSONObject()
                .put("inspection_window_source_pixels", cropJson)
                .put("structure_id", mark.structureId)
                .put("pixel_x", mark.point.pixelX.toDouble())
                .put("pixel_y", mark.point.pixelY.toDouble())
                .put("normalized_x", mark.point.normalizedX.toDouble())
                .put("normalized_y", mark.point.normalizedY.toDouble())
                .put("finding", mark.finding.name)
                .put("reviewer_note", NativeSourcePixelEvidence.cleanNote(mark.note))
        )
    }
    return JSONObject()
        .put("schema", "invertebrata.u116.source-pixel-evidence-draft.v1")
        .put("independent_scientific_signoff", false)
        .put("student_hotspots_enabled", false)
        .put("status", "DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF")
        .put("source_image", sourceId)
        .put("source_kind", if (sourceId.startsWith("app-private:"))
            "UNVERIFIED_PRIVATE_IMPORT" else "ATTRIBUTED_SOURCE_REFERENCE")
        .put("source_sha256", checksum)
        .put("organism", organism)
        .put("morphotype", morphotype)
        .put("image_width_px", bitmap.width)
        .put("image_height_px", bitmap.height)
        .put("marks", array)
        .toString(2)
}

@Composable
internal fun NativeRasterReviewerLauncher(
    organism: String,
    bitmap: ImageBitmap,
    language: AppLanguage,
) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return
    var open by remember { mutableStateOf(false) }
    OutlinedButton(
        onClick = { open = true },
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
            .testTag("u19-open-raster-review-" + organism),
    ) {
        Text(if (language == AppLanguage.TAMIL)
            "உடலமைப்புப் பட ஆய்வு (QA வரைவு மட்டும்)"
            else "Anatomy pixel calibration (debug QA only)")
    }
    if (open) NativeRasterReviewer(
        organism = organism,
        bitmap = bitmap,
        language = language,
        onClose = { open = false },
    )
}

@Composable
private fun NativeRasterReviewer(
    organism: String,
    bitmap: ImageBitmap,
    language: AppLanguage,
    onClose: () -> Unit,
    sourceHashOverride: String? = null,
    sourceIdOverride: String? = null,
    morphotypeOverride: String? = null,
) {
    val context = LocalContext.current
    val tamil = language == AppLanguage.TAMIL
    val plate = requireNotNull(remember(organism) {
        UnitIAnatomyReviewDescriptions.forOrganism(organism)
    })
    val choices = plate.structures
    val narrator = remember(context, organism) { OfflineNarrator(context.applicationContext) }
    val narrationPhase by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }
    val sourceHash = remember(organism, sourceHashOverride) {
        sourceHashOverride ?: rasterSha256(context, organism)
    }
    val sourceId = sourceIdOverride ?: "unit1/references/" + organism + ".png"
    val reviewMorphotype = morphotypeOverride ?: plate.morphotype
    val isPrivateCandidate = sourceHashOverride != null
    // LazyColumn can temporarily dispose the parent type-study item during
    // long 200% Tamil/English dialog scrolls. Preserve the reviewer selection
    // by exact organism/source hash rather than silently resetting to nucleus.
    var selectedId by rememberSaveable(organism, sourceHash) {
        mutableStateOf(choices.first().id)
    }
    val selected = choices.first { it.id == selectedId }
    val marks = remember(organism, sourceHash) { mutableStateListOf<DraftRasterMark>() }
    var zoom by remember(organism) { mutableFloatStateOf(1f) }
    var panX by remember(organism) { mutableFloatStateOf(0f) }
    var panY by remember(organism) { mutableFloatStateOf(0f) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    var exportStatus by remember { mutableStateOf("") }
    LaunchedEffect(language, selectedId) { narrator.stop() }
    val json = remember(organism, sourceHash, sourceId, reviewMorphotype, marks.toList()) {
        reviewJson(
            organism, sourceId, sourceHash.orEmpty(), reviewMorphotype, bitmap, marks
        )
    }
    val latestJson by rememberUpdatedState(json)
    val exportFile = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            exportStatus = try {
                context.contentResolver.openOutputStream(uri)?.use {
                    it.write(latestJson.toByteArray(Charsets.UTF_8))
                } ?: error("Document stream not available")
                if (tamil) "வரைவு JSON சேமிக்கப்பட்டது" else "Draft review JSON saved"
            } catch (_: Exception) {
                if (tamil) "கோப்பைச் சேமிக்க முடியவில்லை" else "Draft export failed"
            }
        }
    }

    // Candidate points remain tied to source raster pixels. Reviewers can
    // inspect/fine-tune the LAST draft endpoint; never fabricate an organ map.
    fun adjustLastDraftPoint(dx: Int, dy: Int) {
        val lastIndex = marks.lastIndex
        if (lastIndex < 0) return
        val old = marks[lastIndex]
        val updated = NativeDraftPixelRefinement.nudge(
            point = old.point, dx = dx, dy = dy,
            imageWidth = bitmap.width, imageHeight = bitmap.height,
        ) ?: return
        // A change to the exact source pixel invalidates its prior observation.
        marks[lastIndex] = old.copy(
            point = updated,
            finding = NativeDraftFinding.NOT_REVIEWED,
            note = "",
        )
    }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(modifier = Modifier.fillMaxSize().testTag("u19-review-dialog-" + organism)) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding().navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Text(
                    if (tamil)
                        "பட ஆய்வு — அங்கீகரிக்கப்படாத பிக்சல் புள்ளிகள்"
                    else "Raster audit — UNVERIFIED anatomical candidate points",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u19-review-hold-" + organism),
                )
                Text(
                    if (tamil) "மூலப் படம்: " + bitmap.width + " × " + bitmap.height
                    else "Original source raster: " + bitmap.width + " × " + bitmap.height,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.testTag("u19-raster-size-" + organism),
                )
                // Native LazyRow exposes ScrollToIndex/ScrollToNode semantics,
                // allowing TalkBack and API34 tests to bring a currently
                // off-screen organ control into view at 200% font. Do NOT
                // preselect a structure or weaken organ-identity assertions.
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                        .semantics {
                            contentDescription = if (tamil)
                                "உடலமைப்பு தேர்வுகள் — கிடைமட்டமாக நகர்த்திப் பார்க்கலாம்"
                            else "Anatomy structure choices — horizontal scrolling"
                        }
                        .testTag("u114-structure-picker-" + organism),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    items(items = choices, key = { it.id }) { option ->
                        OutlinedButton(
                            onClick = { selectedId = option.id },
                            modifier = Modifier.heightIn(min = 48.dp)
                                .testTag("u19-structure-" + organism + "-" + option.id),
                            colors = if (selectedId == option.id)
                                ButtonDefaults.outlinedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            else ButtonDefaults.outlinedButtonColors(),
                        ) {
                            Text(
                                if (tamil) option.ta else option.en,
                                maxLines = 2,
                            )
                        }
                    }
                }
                // The actual decoded anatomical reference comes FIRST. At 200%
                // Tamil text scaling a full explanation and TTS row previously
                // pushed the image beneath the initial viewport.
                Text(
                    if (tamil) "படத்தில் குறி: " + selected.ta
                    else "Mark on source image: " + selected.en,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.testTag("u111-image-selection-" + organism),
                )
                Box(
                    modifier = Modifier.fillMaxWidth().height(360.dp).clipToBounds()
                        .onSizeChanged { viewport = it }
                        .pointerInput(organism, selectedId, viewport, zoom, panX, panY) {
                            detectTapGestures { tap ->
                                val pixel = NativeRasterCalibration.hit(
                                    tapX = tap.x,
                                    tapY = tap.y,
                                    viewportWidth = viewport.width.toFloat(),
                                    viewportHeight = viewport.height.toFloat(),
                                    rasterWidth = bitmap.width,
                                    rasterHeight = bitmap.height,
                                    zoom = zoom,
                                    panX = panX,
                                    panY = panY,
                                )
                                if (pixel != null) {
                                    marks.add(DraftRasterMark(selectedId, pixel))
                                }
                            }
                        }
                        .pointerInput(organism) {
                            detectTransformGestures { _, pan, scale, _ ->
                                val next = (zoom * scale).coerceIn(1f, 8f)
                                zoom = next
                                if (zoom <= 1.01f) {
                                    panX = 0f; panY = 0f
                                } else {
                                    panX = (panX + pan.x).coerceIn(-size.width.toFloat(), size.width.toFloat())
                                    panY = (panY + pan.y).coerceIn(-size.height.toFloat(), size.height.toFloat())
                                }
                            }
                        }
                        .testTag("u19-tappable-raster-" + organism),
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = if (tamil) "ஆதாரப் படம் — ஆய்வு நிலை"
                            else "Source bitmap for provisional coordinate audit",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                            .testTag("u111-source-raster-visible-" + organism)
                            .graphicsLayer {
                            scaleX = zoom
                            scaleY = zoom
                            translationX = panX
                            translationY = panY
                        },
                    )
                    val markerColor = MaterialTheme.colorScheme.error
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        marks.forEachIndexed { markerIndex, mark ->
                            val screen = NativeRasterCalibration.project(
                                pixelX = mark.point.pixelX,
                                pixelY = mark.point.pixelY,
                                viewportWidth = size.width,
                                viewportHeight = size.height,
                                rasterWidth = bitmap.width,
                                rasterHeight = bitmap.height,
                                zoom = zoom,
                                panX = panX,
                                panY = panY,
                            )
                            if (screen != null) {
                                drawCircle(
                                    color = markerColor,
                                    radius = 6.dp.toPx(),
                                    center = Offset(screen.first, screen.second),
                                )
                                if (markerIndex == marks.lastIndex) {
                                    // Ring denotes the *selected draft point*,
                                    // NOT a verified anatomical hotspot.
                                    drawCircle(
                                        color = markerColor,
                                        radius = 10.dp.toPx(),
                                        center = Offset(screen.first, screen.second),
                                        style = Stroke(width = 2.dp.toPx()),
                                    )
                                }
                            }
                        }
                    }
                }
                // U1.12: exact source-pixel evidence accompanies each manual tap.
                // This panel is below the image to preserve U1.11 image-first QA.
                val selectedDraft = marks.lastOrNull()
                if (selectedDraft != null) {
                    val x = String.format(Locale.ROOT, "%.2f", selectedDraft.point.pixelX)
                    val y = String.format(Locale.ROOT, "%.2f", selectedDraft.point.pixelY)
                    val u = String.format(Locale.ROOT, "%.5f", selectedDraft.point.normalizedX)
                    val v = String.format(Locale.ROOT, "%.5f", selectedDraft.point.normalizedY)
                    Text(
                        if (tamil) "வரைவுப் பிக்சல் குறி: x=" + x + ", y=" + y +
                            "; விகிதங்கள் u=" + u + ", v=" + v
                        else "Draft source pixel: x=" + x + ", y=" + y +
                            "; normalized u=" + u + ", v=" + v,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.testTag("u112-selected-pixel-" + organism),
                    )
                    Text(
                        if (tamil)
                            "கடைசிப் புள்ளியை ஒவ்வொரு உண்மையான பிக்சலாகச் சரிசெய்க. அறிவியல் ஒப்புதல் இல்லை."
                        else
                            "Fine-adjust last draft mark by exactly one source pixel; this does not approve anatomy.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("u112-unverified-coordinate-note-" + organism),
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val moves = listOf(
                            Triple("x-minus", "←", Pair(-1, 0)),
                            Triple("x-plus", "→", Pair(1, 0)),
                            Triple("y-minus", "↑", Pair(0, -1)),
                            Triple("y-plus", "↓", Pair(0, 1)),
                        )
                        moves.forEach { (tag, glyph, shift) ->
                            val allowed = NativeDraftPixelRefinement.nudge(
                                selectedDraft.point, shift.first, shift.second,
                                bitmap.width, bitmap.height,
                            ) != null
                            OutlinedButton(
                                onClick = { adjustLastDraftPoint(shift.first, shift.second) },
                                enabled = allowed,
                                modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                                    .semantics {
                                        contentDescription = when (tag) {
                                            "x-minus" -> if (tamil) "X ஒரு பிக்சல் குறை"
                                                else "Decrease X by one source pixel"
                                            "x-plus" -> if (tamil) "X ஒரு பிக்சல் கூட்டு"
                                                else "Increase X by one source pixel"
                                            "y-minus" -> if (tamil) "Y ஒரு பிக்சல் குறை"
                                                else "Decrease Y by one source pixel"
                                            else -> if (tamil) "Y ஒரு பிக்சல் கூட்டு"
                                                else "Increase Y by one source pixel"
                                        }
                                    }
                                    .testTag("u112-nudge-" + organism + "-" + tag),
                            ) { Text(glyph) }
                        }
                    }
                }
                if (selectedDraft != null) {
                    val win = remember(bitmap, selectedDraft.point.pixelX, selectedDraft.point.pixelY) {
                        NativeSourcePixelEvidence.window(
                            selectedDraft.point.pixelX,
                            selectedDraft.point.pixelY,
                            bitmap.width,
                            bitmap.height,
                        )
                    }
                    val crop = remember(bitmap, win) {
                        if (win == null) null else runCatching {
                            Bitmap.createBitmap(
                                bitmap.asAndroidBitmap(),
                                win.left, win.top, win.width, win.height,
                            ).asImageBitmap()
                        }.getOrNull()
                    }
                    Text(
                        if (tamil)
                            "உண்மையான மூலப் பிக்சல்களின் பெரிதாக்கிய பகுதி — உடலமைப்பு உறுதி அல்ல"
                        else
                            "Magnified ORIGINAL source pixels — NOT anatomical verification",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("u116-closeup-title-" + organism),
                    )
                    if (win != null && crop != null) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(200.dp).clipToBounds()
                                .testTag("u116-closeup-" + organism),
                        ) {
                            Image(
                                bitmap = crop,
                                contentDescription = if (tamil)
                                    "அசல் படத்தின் பிக்சல் பகுதி; அங்கீகரிக்கப்படாத புள்ளி"
                                else "Unmodified source-pixel crop around provisional organ marker",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit,
                            )
                            val ink = MaterialTheme.colorScheme.error
                            Canvas(Modifier.fillMaxSize()) {
                                val at = NativeRasterCalibration.project(
                                    pixelX = win.focusX,
                                    pixelY = win.focusY,
                                    viewportWidth = size.width,
                                    viewportHeight = size.height,
                                    rasterWidth = win.width,
                                    rasterHeight = win.height,
                                )
                                if (at != null) {
                                    drawCircle(
                                        color = ink,
                                        radius = 8.dp.toPx(),
                                        center = Offset(at.first, at.second),
                                        style = Stroke(width = 2.dp.toPx()),
                                    )
                                }
                            }
                        }
                        Text(
                            "SHA-256: " + (sourceHash ?: "UNAVAILABLE") +
                                " · Crop: x=" + win.left + " y=" + win.top +
                                " w=" + win.width + " h=" + win.height,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.testTag("u116-source-binding-" + organism),
                        )
                    } else {
                        Text(
                            if (tamil) "மூல பிக்சல் பகுதியைப் பெற முடியவில்லை — ஆய்வு முடிக்க இயலாது"
                            else "Source-pixel close-up unavailable — do not verify this endpoint",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("u116-crop-unavailable-" + organism),
                        )
                    }
                    Text(
                        if (tamil)
                            "அமைப்புக் குறியை ஆய்வாளராக மதிப்பிடுக; இது அறிவியல் ஒப்புதல் அல்ல."
                        else
                            "Record your inspection finding. No option confers scientific approval.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("u116-review-status-hold-" + organism),
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                            .testTag("u116-finding-picker-" + organism),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(NativeDraftFinding.values().toList(), key = { it.name }) { option ->
                            val label = if (tamil) when (option) {
                                NativeDraftFinding.NOT_REVIEWED -> "ஆய்வு செய்யவில்லை"
                                NativeDraftFinding.VISIBLE_CANDIDATE -> "காணக்கூடிய வரைவு"
                                NativeDraftFinding.CANNOT_RESOLVE -> "உறுதி செய்ய இயலவில்லை"
                                NativeDraftFinding.WRONG_STRUCTURE -> "தவறான அமைப்பு"
                            } else when (option) {
                                NativeDraftFinding.NOT_REVIEWED -> "Not reviewed"
                                NativeDraftFinding.VISIBLE_CANDIDATE -> "Visually plausible"
                                NativeDraftFinding.CANNOT_RESOLVE -> "Cannot resolve"
                                NativeDraftFinding.WRONG_STRUCTURE -> "Incorrect structure"
                            }
                            FilterChip(
                                selected = selectedDraft.finding == option,
                                onClick = {
                                    val index = marks.lastIndex
                                    if (index >= 0) marks[index] = marks[index].copy(finding = option)
                                },
                                label = { Text(label) },
                                modifier = Modifier.heightIn(min = 48.dp)
                                    .testTag("u116-finding-" + organism + "-" + option.name),
                            )
                        }
                    }
                    OutlinedTextField(
                        value = selectedDraft.note,
                        onValueChange = { value ->
                            val index = marks.lastIndex
                            if (index >= 0) marks[index] = marks[index].copy(
                                note = NativeSourcePixelEvidence.cleanNote(value),
                            )
                        },
                        label = {
                            Text(if (tamil) "ஆய்வாளர் குறிப்பு (அங்கீகாரம் அல்ல)"
                                else "Review note (not a signoff)")
                        },
                        minLines = 2,
                        maxLines = 4,
                        modifier = Modifier.fillMaxWidth()
                            .testTag("u116-review-note-" + organism),
                    )
                }
                if (isPrivateCandidate) {
                    Text(
                        if (tamil)
                            "இறக்குமதி செய்த படம்: உயிரின அடையாளமும் வளர்ச்சி நிலையும் உறுதிசெய்யப்படவில்லை. இவை ஆய்வுக் குறிப்புகள் மட்டும்."
                        else
                            "IMPORTED UNVERIFIED SCAN: organism, stage and structure names are review prompts, NOT confirmed anatomical identities.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("u113-private-stage-hold-" + organism),
                    )
                }
                // Detailed anatomy and reviewer-only speech follow the visible
                // raster. Keep these scrollable, readable and explicitly draft.
                Text(
                    if (tamil)
                        "தேர்ந்த அமைப்பு: " + selected.ta + ". பெரிதாக்கிப் படத்தைத் தொடுக."
                    else "Selected structure: " + selected.en +
                        ". Pinch to enlarge, then tap the source pixel.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.testTag("u114-selected-structure-" + organism),
                )
                Text(
                    if (tamil) selected.explanationTa else selected.explanationEn,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("u110-review-explanation-" + organism),
                )
                Text(
                    if (tamil)
                        "உருவ நிலை: " + reviewMorphotype +
                            ". இது வரைவு மட்டும்; மூலப் படத்தில் இல்லாத அமைப்பைக் குறிக்கக் கூடாது."
                    else "Review morphotype: " + reviewMorphotype +
                        ". No approved hotspot or promastigote on this source raster.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u110-review-morphotype-" + organism),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    OutlinedButton(
                        onClick = { narrator.speak(selected.narration, language) },
                        enabled = !isPrivateCandidate, // Unknown source stage: no organ-name narration.
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u110-review-speak-" + organism),
                    ) {
                        Text(if (tamil) "தேர்ந்த அமைப்பின் விளக்க ஒலி" else "Speak selected structure")
                    }
                    OutlinedButton(
                        onClick = { narrator.stop() },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u110-review-stop-" + organism),
                    ) {
                        Text(if (tamil) "ஒலியை நிறுத்து" else "Stop voice")
                    }
                }
                Text(
                    if (tamil) "இணையமற்ற குரல் நிலை: " + narrationPhase.name
                    else "Offline voice state: " + narrationPhase.name,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.testTag("u110-review-voice-status-" + organism),
                )
                Text(
                    if (tamil) "பதிவு செய்த புள்ளிகள்: " + marks.size
                    else "Draft candidate points: " + marks.size,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.testTag("u19-mark-count-" + organism),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    OutlinedButton(
                        onClick = { zoom = (zoom / 1.5f).coerceAtLeast(1f) },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .semantics { contentDescription = if (tamil) "சிறிதாக்கு" else "Zoom out" }
                            .testTag("u19-zoom-out-" + organism),
                    ) { Text("−") }
                    OutlinedButton(
                        onClick = { zoom = (zoom * 1.5f).coerceAtMost(8f) },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .semantics { contentDescription = if (tamil) "பெரிதாக்கு" else "Zoom in" }
                            .testTag("u19-zoom-in-" + organism),
                    ) { Text("+") }
                    OutlinedButton(
                        onClick = { zoom = 1f; panX = 0f; panY = 0f },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .semantics { contentDescription = if (tamil) "காட்சி மீட்டமை" else "Reset view" }
                            .testTag("u19-view-reset-" + organism),
                    ) { Text("↺") }
                    Button(
                        onClick = onClose,
                        modifier = Modifier.weight(2f).heightIn(min = 48.dp)
                            .testTag("u19-close-" + organism),
                    ) { Text(if (tamil) "மூடு" else "Close") }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    OutlinedButton(
                        onClick = { if (marks.isNotEmpty()) marks.removeAt(marks.lastIndex) },
                        enabled = marks.isNotEmpty(),
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u19-undo-" + organism),
                    ) { Text(if (tamil) "பின்செயல்" else "Undo point") }
                    Button(
                        onClick = { exportFile.launch("u19-" + organism + "-UNVERIFIED-pixels.json") },
                        enabled = sourceHash?.let(NativeSourcePixelEvidence::isHashBoundSha256) == true &&
                            marks.isNotEmpty(),
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u19-export-" + organism),
                    ) { Text(if (tamil) "வரைவு JSON" else "Export draft JSON") }
                }
                if (sourceHash == null) Text(
                    if (tamil) "மூலப் பட SHA-256 கிடைக்கவில்லை; ஏற்றுமதி தடை."
                    else "Original PNG checksum unavailable; export blocked.",
                    color = MaterialTheme.colorScheme.error,
                )
                if (exportStatus.isNotBlank()) Text(exportStatus, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

/**
 * Reuse the U1.11 image-first and U1.12 one-pixel reviewer on a scanned
 * PNG/JPEG. This source is NOT a biologically confirmed morphotype.
 */
@Composable
internal fun NativeImportedPlateReviewer(
    organism: String,
    bitmap: ImageBitmap,
    sha256: String,
    language: AppLanguage,
    onClose: () -> Unit,
) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return
    if (!sha256.matches(Regex("[0-9a-f]{64}"))) return
    NativeRasterReviewer(
        organism = organism,
        bitmap = bitmap,
        language = language,
        onClose = onClose,
        sourceHashOverride = sha256,
        sourceIdOverride = "app-private:u110_unverified_pencil_candidates/" +
            organism + ".source",
        morphotypeOverride = "UNVERIFIED_IMPORTED_STAGE_REQUIRES_SCIENTIFIC_REVIEW",
    )
}
