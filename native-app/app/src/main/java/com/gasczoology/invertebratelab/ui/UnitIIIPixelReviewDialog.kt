package com.gasczoology.invertebratelab.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.NativeRasterCalibration
import com.gasczoology.invertebratelab.data.UnitIIIPlateHotspots
import com.gasczoology.invertebratelab.data.UnitIIIPlateSpec
import com.gasczoology.invertebratelab.data.UnitIIIEndpointAgreement
import com.gasczoology.invertebratelab.data.UnitIIIEndpointComparison
import com.gasczoology.invertebratelab.data.UnitIIIPixelReviewDraft
import com.gasczoology.invertebratelab.data.UnitIIIPixelReviewMark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.security.MessageDigest

/**
 * DEBUG-ONLY. Records unapproved source-pixel endpoints against the actual
 * packaged PNG SHA-256; never enables source anatomy touch in student builds.
 */
@Composable
internal fun UnitIIIPixelReviewDialog(
    plate: UnitIIIPlateSpec,
    bitmap: ImageBitmap,
    language: AppLanguage,
    onClose: () -> Unit,
) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return
    val tamil = language == AppLanguage.TAMIL
    val organs = remember(plate.assetStem) { UnitIIIPlateHotspots.forStem(plate.assetStem) }
    if (organs.isEmpty()) return

    var selectedId by remember(plate.assetStem) { mutableStateOf(organs.first().id) }
    var marks by remember(plate.assetStem) { mutableStateOf(emptyList<UnitIIIPixelReviewMark>()) }
    var zoom by remember(plate.assetStem) { mutableFloatStateOf(1f) }
    var panX by remember(plate.assetStem) { mutableFloatStateOf(0f) }
    var panY by remember(plate.assetStem) { mutableFloatStateOf(0f) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    var digest by remember(plate.assetStem) { mutableStateOf<String?>(null) }
    var fileState by remember { mutableStateOf("") }
    var comparison by remember(plate.assetStem) {
        mutableStateOf<UnitIIIEndpointComparison?>(null)
    }
    var comparisonError by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val assetPath = plate.assetStem + ".png"

    // Read the SHA-256 of the actual unmodified APK asset, not a derived
    // screenshot, displayed canvas or user-entered provenance claim.
    LaunchedEffect(plate.assetStem) {
        digest = withContext(Dispatchers.IO) {
            runCatching {
                val sha = MessageDigest.getInstance("SHA-256")
                context.assets.open(assetPath).use { input ->
                    val buf = ByteArray(8192)
                    while (true) {
                        val n = input.read(buf)
                        if (n < 0) break
                        sha.update(buf, 0, n)
                    }
                }
                sha.digest().joinToString("") { "%02x".format(it) }
            }.getOrNull()
        }
    }

    val evidence = remember(plate.assetStem, digest, marks, bitmap.width, bitmap.height) {
        runCatching {
            UnitIIIPixelReviewDraft(
                plate.assetStem, requireNotNull(digest),
                bitmap.width, bitmap.height, marks,
            )
        }.getOrNull()
    }
    // Any change to draft points or source identity invalidates an earlier
    // comparison. Never show stale agreement after re-marking an organ.
    LaunchedEffect(marks, digest) {
        comparison = null
        comparisonError = ""
    }
    val comparePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri != null) {
            val current = evidence
            if (current == null) {
                comparisonError = if (tamil)
                    "முதலில் ஒரு உறுப்பின் இடத்தைக் குறிக்கவும்."
                else "Mark a source organ before comparing a second draft."
            } else {
                scope.launch {
                    try {
                        val second = withContext(Dispatchers.IO) {
                            val bytes = ByteArrayOutputStream()
                            context.contentResolver.openInputStream(uri)?.use { input ->
                                val buffer = ByteArray(4096)
                                while (true) {
                                    val n = input.read(buffer)
                                    if (n == -1) break
                                    require(bytes.size() + n <=
                                        UnitIIIDraftComparisonIntake.MAX_JSON_BYTES) {
                                        "Second draft too large"
                                    }
                                    bytes.write(buffer, 0, n)
                                }
                            } ?: error("No source document")
                            UnitIIIDraftComparisonIntake.parse(
                                bytes.toString(Charsets.UTF_8.name()))
                        }
                        comparison = UnitIIIEndpointAgreement.compare(current, second)
                        comparisonError = ""
                    } catch (_: Exception) {
                        comparison = null
                        comparisonError = if (tamil)
                            "ஒப்பீடு நிராகரிக்கப்பட்டது: ஒரே படம் / SHA-256 / வரைவு விவரம் தேவை."
                        else "Comparison rejected: mismatched plate, SHA-256, size or invalid draft."
                    }
                }
            }
        }
    }
    val export = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        val ready = evidence
        if (uri != null && ready != null) {
            val array = JSONArray()
            ready.marks.forEach { point ->
                array.put(JSONObject()
                    .put("organ_id", point.organId)
                    .put("pixel_x", point.pixelX.toDouble())
                    .put("pixel_y", point.pixelY.toDouble())
                    .put("normalized_x", point.pixelX / ready.imageWidth)
                    .put("normalized_y", point.pixelY / ready.imageHeight))
            }
            val json = JSONObject()
                .put("schema", "invertebrata.u33.source-pixel-review.v1")
                .put("approval_status", ready.approvalStatus)
                .put("plate_asset_stem", ready.plateStem)
                .put("actual_apk_png_sha256", ready.sourceSha256)
                .put("source_width_px", ready.imageWidth)
                .put("source_height_px", ready.imageHeight)
                .put("human_anatomy_reviewer", JSONObject.NULL)
                .put("reviewed_subfigure", JSONObject.NULL)
                .put("image_rights_review", "PENDING")
                .put("marks", array).toString(2)
            scope.launch {
                fileState = try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri)?.use {
                            it.write(json.toByteArray(Charsets.UTF_8))
                        } ?: error("Android Files output is unavailable")
                    }
                    if (tamil) "வரைவு JSON சேமிக்கப்பட்டது; அறிவியல் ஒப்புதல் இல்லை."
                    else "Draft evidence saved; no scientific approval granted."
                } catch (_: Exception) {
                    if (tamil) "JSON கோப்பைச் சேமிக்க முடியவில்லை"
                    else "Draft JSON export failed"
                }
            }
        }
    }

    Dialog(onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize().testTag("u33-review-dialog-" + plate.plateId)) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding().navigationBarsPadding().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        if (tamil) "பிக்சல் ஆய்வு · வரைவு"
                        else "Pixel review · DRAFT",
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f).testTag("u33-review-title-" + plate.plateId),
                    )
                    OutlinedButton(
                        onClick = { comparePicker.launch(arrayOf("application/json", "text/plain")) },
                        enabled = evidence != null,
                        modifier = Modifier.heightIn(min=48.dp)
                            .testTag("u34-compare-import-" + plate.plateId),
                    ) { Text(if (tamil) "ஒப்பிடு" else "Compare") }
                    OutlinedButton(onClick = onClose,
                        modifier = Modifier.heightIn(min=48.dp)
                            .testTag("u33-review-close-" + plate.plateId)) {
                        Text(if (tamil) "மூடு" else "Close")
                    }
                }
                Text(
                    if (tamil)
                        "ஒப்புதல் பெறாத வரைவு. படத்தில் உறுப்பைக் குறி; மாணவர் தொடுபுள்ளி முடக்கப்பட்டுள்ளது."
                    else "UNAPPROVED draft. Select an organ, then tap source pixels; learner hotspots stay disabled.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("u33-review-hold-" + plate.plateId),
                )
                // The actual bitmap receives the majority of the screen.
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .clipToBounds().background(Color.White)
                        .onSizeChanged { viewport = it }
                        .pointerInput(plate.assetStem) {
                            detectTransformGestures { _, pan, scale, _ ->
                                val newZoom = (zoom * scale).coerceIn(1f, 4f)
                                zoom = newZoom
                                if (newZoom <= 1f) {
                                    panX = 0f; panY = 0f
                                } else {
                                    panX = (panX + pan.x).coerceIn(
                                        -(newZoom-1f)*size.width*.5f,
                                        (newZoom-1f)*size.width*.5f,
                                    )
                                    panY = (panY + pan.y).coerceIn(
                                        -(newZoom-1f)*size.height*.5f,
                                        (newZoom-1f)*size.height*.5f,
                                    )
                                }
                            }
                        }
                        .pointerInput(plate.assetStem, selectedId, zoom, panX, panY, viewport) {
                            detectTapGestures { pos ->
                                val hit = NativeRasterCalibration.hit(
                                    pos.x, pos.y,
                                    size.width.toFloat(), size.height.toFloat(),
                                    bitmap.width, bitmap.height, zoom, panX, panY,
                                )
                                if (hit != null) {
                                    val mark = UnitIIIPixelReviewMark(
                                        selectedId, hit.pixelX, hit.pixelY,
                                    )
                                    marks = marks.filterNot { it.organId == selectedId } + mark
                                }
                            }
                        }
                        .testTag("u33-review-original-raster-" + plate.plateId),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().graphicsLayer {
                            scaleX = zoom
                            scaleY = zoom
                            translationX = panX
                            translationY = panY
                        }
                    ) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = plate.title.value(language) +
                                " · exact APK source bitmap; draft coordinates only",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                                .testTag("u33-review-decoded-image-" + plate.plateId),
                        )
                        Canvas(Modifier.fillMaxSize()) {
                            marks.forEach { mark ->
                                val xy = NativeRasterCalibration.project(
                                    mark.pixelX, mark.pixelY,
                                    size.width, size.height,
                                    bitmap.width, bitmap.height,
                                )
                                if (xy != null) {
                                    drawCircle(
                                        Color(0xFFB64625),
                                        radius = 11.dp.toPx(),
                                        center = Offset(xy.first, xy.second),
                                        style = Stroke(2.dp.toPx()),
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    if (tamil) "உறுப்பு:" else "Organ:",
                    style = MaterialTheme.typography.labelSmall,
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = Modifier.fillMaxWidth()
                        .testTag("u33-review-organ-chooser-" + plate.plateId)) {
                    items(organs, key={ it.id }) { organ ->
                        FilterChip(
                            selected = selectedId == organ.id,
                            onClick = { selectedId = organ.id },
                            label = { Text(organ.title.value(language), maxLines=1) },
                            modifier = Modifier.heightIn(min=48.dp)
                                .testTag("u33-review-organ-" + plate.plateId + "-" + organ.id),
                        )
                    }
                }
                Text(
                    "SHA-256: " + ((digest?.take(12) ?: "VERIFYING") + "…") +
                        " · " + bitmap.width + "×" + bitmap.height +
                        " px · draft: " + marks.size,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    modifier = Modifier.testTag("u33-review-source-status-" + plate.plateId),
                )
                Text(
                    marks.lastOrNull()?.let {
                        "DRAFT " + it.organId + " · x=" + it.pixelX.toInt() +
                            ", y=" + it.pixelY.toInt()
                    } ?: (if(tamil) "படத்தில் தொடு — இன்னும் புள்ளி இல்லை"
                          else "Touch a source pixel — no candidate point yet"),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("u33-review-last-point-" + plate.plateId),
                )
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { zoom = 1f; panX = 0f; panY = 0f },
                        modifier = Modifier.weight(1f).heightIn(min=48.dp)
                            .testTag("u33-review-reset-zoom-" + plate.plateId),
                    ) { Text("1×") }
                    OutlinedButton(
                        onClick = { zoom = (zoom * 2f).coerceAtMost(4f); panX=0f; panY=0f },
                        enabled = zoom < 4f,
                        modifier = Modifier.weight(1f).heightIn(min=48.dp)
                            .testTag("u33-review-zoom-in-" + plate.plateId),
                    ) { Text("+") }
                    OutlinedButton(
                        onClick = { marks = emptyList() },
                        modifier = Modifier.weight(1f).heightIn(min=48.dp)
                            .semantics {
                                contentDescription = if (tamil)
                                    "வரைவு புள்ளிகள் அனைத்தையும் அழி"
                                else "Clear all draft points"
                            }
                            .testTag("u33-review-clear-" + plate.plateId),
                    ) { Text("×", maxLines=1) }
                    Button(
                        onClick = { export.launch("u33-" + plate.plateId + "-UNVERIFIED.json") },
                        enabled = evidence != null,
                        modifier = Modifier.weight(1.5f).heightIn(min=48.dp)
                            .semantics {
                                contentDescription = if (tamil)
                                    "ஒப்புதல் பெறாத வரைவு JSON-ஐச் சேமி"
                                else "Export UNVERIFIED draft JSON"
                            }
                            .testTag("u33-review-export-" + plate.plateId),
                    ) { Text("JSON", maxLines=1) }
                }
                if (fileState.isNotBlank()) Text(fileState)
            }
            if (comparison != null || comparisonError.isNotBlank()) {
                val result = comparison
                AlertDialog(
                    onDismissRequest = {
                        comparison = null
                        comparisonError = ""
                    },
                    title = {
                        Text(if (tamil) "உடற்கூறு புள்ளி ஒப்பீடு · ஒப்புதல் இல்லை"
                             else "Pixel agreement audit · NOT APPROVAL")
                    },
                    text = {
                        Column(
                            modifier = Modifier.heightIn(max = 360.dp)
                                .verticalScroll(rememberScrollState())
                                .testTag("u34-comparison-report-" + plate.plateId),
                            verticalArrangement = Arrangement.spacedBy(7.dp),
                        ) {
                            if (result == null) {
                                Text(comparisonError)
                            } else {
                                Text(
                                    "DRAFT COMPARISON · SHA-256: " +
                                        result.sourceSha256.take(12) + "…"
                                )
                                Text(
                                    if (tamil)
                                        "±" + result.tolerancePixels.toInt() +
                                            " மூல பிக்சல் ஒத்துப்போவது: " +
                                            result.withinTolerance +
                                            "; மீள் ஆய்வு: " + result.needsAttention
                                    else "Agreement within " +
                                        result.tolerancePixels.toInt() +
                                        " original pixels: " + result.withinTolerance +
                                        "; review needed: " + result.needsAttention
                                )
                                result.rows.forEach { line ->
                                    Text(line.organId + ": " + line.finding.name +
                                        (line.distancePixels?.let {
                                            " (" + String.format(java.util.Locale.US, "%.1f", it) +
                                                "px)"
                                        } ?: ""),
                                        style = MaterialTheme.typography.bodySmall)
                                }
                                Text(
                                    if (tamil)
                                        "ஒரே கோப்பு இருமுறை தரப்பட்டிருக்கலாம். ஒப்பீடு தனித்த நிபுணர் சான்றோ, உடற்கூறு ஒப்புதலோ அல்ல. மாணவர் தொடுபுள்ளிகள் முடக்கப்பட்டுள்ளன."
                                    else "The same file may have been submitted twice. Matching pixels do not establish independent reviewers or biological correctness. Student hotspots remain disabled.",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                comparison = null
                                comparisonError = ""
                            },
                            modifier = Modifier.testTag("u34-comparison-close-" + plate.plateId),
                        ) { Text(if (tamil) "மூடு" else "Close") }
                    },
                )
            }
        }
    }
}
