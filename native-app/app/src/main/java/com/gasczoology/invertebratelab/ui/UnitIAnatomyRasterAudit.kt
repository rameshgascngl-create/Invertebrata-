package com.gasczoology.invertebratelab.ui

import android.content.pm.ApplicationInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
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
import com.gasczoology.invertebratelab.data.RasterPixelLocation
import java.security.MessageDigest
import org.json.JSONArray
import org.json.JSONObject

/**
 * U1.9 — ANDROID DEBUG-BUILD-ONLY anatomical plate coordinate CALIBRATION.
 *
 * A reviewer points at a source raster and exports unapproved coordinates.
 * There are NO pre-authorised organ hotspots, anatomical leader lines or
 * silent production activation. The source image itself is never repainted.
 */
private data class DraftRasterMark(val structureId: String, val point: RasterPixelLocation)

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
    checksum: String,
    morphotype: String,
    bitmap: ImageBitmap,
    marks: List<DraftRasterMark>,
): String {
    val array = JSONArray()
    marks.forEach { mark ->
        array.put(
            JSONObject()
                .put("structure_id", mark.structureId)
                .put("pixel_x", mark.point.pixelX.toDouble())
                .put("pixel_y", mark.point.pixelY.toDouble())
                .put("normalized_x", mark.point.normalizedX.toDouble())
                .put("normalized_y", mark.point.normalizedY.toDouble())
        )
    }
    return JSONObject()
        .put("schema", "invertebrata.u110.morphotype-coordinate-draft.v1")
        .put("status", "DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF")
        .put("source_image", "unit1/references/" + organism + ".png")
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
    val sourceHash = remember(organism) { rasterSha256(context, organism) }
    var selectedId by remember(organism) { mutableStateOf(choices.first().id) }
    val selected = choices.first { it.id == selectedId }
    val marks = remember(organism) { mutableStateListOf<DraftRasterMark>() }
    var zoom by remember(organism) { mutableFloatStateOf(1f) }
    var panX by remember(organism) { mutableFloatStateOf(0f) }
    var panY by remember(organism) { mutableFloatStateOf(0f) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    var exportStatus by remember { mutableStateOf("") }
    LaunchedEffect(language, selectedId) { narrator.stop() }
    val json = remember(organism, sourceHash, marks.toList()) {
        reviewJson(organism, sourceHash.orEmpty(), plate.morphotype, bitmap, marks)
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
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    choices.forEach { option ->
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
                        marks.forEach { mark ->
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
                            }
                        }
                    }
                }
                // Detailed anatomy and reviewer-only speech follow the visible
                // raster. Keep these scrollable, readable and explicitly draft.
                Text(
                    if (tamil)
                        "தேர்ந்த அமைப்பு: " + selected.ta + ". பெரிதாக்கிப் படத்தைத் தொடுக."
                    else "Selected structure: " + selected.en +
                        ". Pinch to enlarge, then tap the source pixel.",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    if (tamil) selected.explanationTa else selected.explanationEn,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("u110-review-explanation-" + organism),
                )
                Text(
                    if (tamil)
                        "உருவ நிலை: " + plate.morphotype +
                            ". இது வரைவு மட்டும்; மூலப் படத்தில் இல்லாத அமைப்பைக் குறிக்கக் கூடாது."
                    else "Reference morphotype: " + plate.morphotype +
                        ". No approved hotspot or promastigote on this source raster.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u110-review-morphotype-" + organism),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    OutlinedButton(
                        onClick = { narrator.speak(selected.narration, language) },
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
                        enabled = sourceHash != null && marks.isNotEmpty(),
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
