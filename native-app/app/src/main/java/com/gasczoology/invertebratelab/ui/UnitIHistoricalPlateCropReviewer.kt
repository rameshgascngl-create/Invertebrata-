package com.gasczoology.invertebratelab.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.HistoricalArchiveIdentity
import com.gasczoology.invertebratelab.data.HistoricalPixelCrop
import com.gasczoology.invertebratelab.data.NativePencilCandidate
import com.gasczoology.invertebratelab.data.NativeRasterCalibration
import com.gasczoology.invertebratelab.data.UnitIHistoricalPlateArchive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.security.MessageDigest

/**
 * Own chapter item, separate from the large image-import card. Rechecking
 * reads only app-private bytes and never reaches the network.
 */
@Composable
internal fun UnitIHistoricalPlateSourceSection(language: AppLanguage) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return
    val tamil = language == AppLanguage.TAMIL
    var version by remember { mutableIntStateOf(0) }
    var candidate by remember { mutableStateOf<NativePencilCandidate?>(null) }
    var decoded by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    LaunchedEffect(version) {
        val source = withContext(Dispatchers.IO) {
            com.gasczoology.invertebratelab.data.NativePencilMasterStore.load(
                context.applicationContext, "leishmania"
            )
        }
        candidate = source
        val identity = UnitIHistoricalPlateArchive.leishmania1922
        decoded = if (source != null && identity.exactSourceMatches(
                source.originalBytes, source.width, source.height)) {
            withContext(Dispatchers.Default) {
                BitmapFactory.decodeByteArray(
                    source.originalBytes, 0, source.originalBytes.size
                )?.asImageBitmap()
            }
        } else null
    }
    Column(
        modifier = Modifier.fillMaxWidth().testTag("u116-archive-section"),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedButton(
            onClick = { version++ },
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                .testTag("u116-refresh-source"),
        ) {
            Text(if (tamil)
                "சேர்க்கப்பட்ட வரலாற்றுப் படத்தை மீண்டும் சரிபார்"
                else "Recheck imported historical source (offline)")
        }
        UnitIHistoricalPlateCropReviewer(
            organism = "leishmania",
            candidate = candidate,
            bitmap = decoded,
            language = language,
        )
    }
}

/** Exact original-pixel crop of a fingerprinted archival file, no resampling. */
internal object NativeHistoricalCropEncoding {
    fun png(sourceBytes: ByteArray, crop: HistoricalPixelCrop): ByteArray {
        val source = requireNotNull(BitmapFactory.decodeByteArray(
            sourceBytes, 0, sourceBytes.size
        )) { "Invalid archival image bytes" }
        try {
            require(crop.inBounds(source.width, source.height)) {
                "Crop outside original image pixels"
            }
            val sub = Bitmap.createBitmap(
                source, crop.left, crop.top, crop.width, crop.height
            )
            try {
                val output = ByteArrayOutputStream()
                check(sub.compress(Bitmap.CompressFormat.PNG, 100, output)) {
                    "Unable to preserve native pixel crop"
                }
                return output.toByteArray()
            } finally {
                if (sub !== source) sub.recycle()
            }
        } finally {
            if (!source.isRecycled) source.recycle()
        }
    }
    fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256")
        .digest(bytes).joinToString("") { "%02x".format(it) }
}

@Composable
internal fun UnitIHistoricalPlateCropReviewer(
    organism: String,
    candidate: NativePencilCandidate?,
    bitmap: androidx.compose.ui.graphics.ImageBitmap?,
    language: AppLanguage,
) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return
    val record = UnitIHistoricalPlateArchive.forOrganism(organism) ?: return
    val tamil = language == AppLanguage.TAMIL
    val exact = remember(candidate?.sourceSha256, record.publishedSha1) {
        candidate != null &&
            record.exactSourceMatches(
                candidate.originalBytes, candidate.width, candidate.height,
            )
    }
    val groups = record.figureGroups
    var groupId by remember(candidate?.sourceSha256) { mutableStateOf(groups.first().id) }
    val selectedGroup = groups.first { it.id == groupId }
    var first by remember(candidate?.sourceSha256) { mutableStateOf<Offset?>(null) }
    var crop by remember(candidate?.sourceSha256) { mutableStateOf<HistoricalPixelCrop?>(null) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    var exportMessage by remember { mutableStateOf("") }
    var recordedExport by remember(candidate?.sourceSha256, crop) {
        mutableStateOf<Pair<String, String>?>(null) // PNG SHA-256, original crop metadata
    }
    val scope = rememberCoroutineScope()

    val pngSave = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("image/png")
    ) { uri ->
        if (uri != null && exact && candidate != null) {
            val selectedCrop = crop ?: return@rememberLauncherForActivityResult
            val source = candidate.originalBytes
            val provenance = candidate.sourceSha256
            val group = selectedGroup
            scope.launch {
                try {
                    val bytes = withContext(Dispatchers.Default) {
                        NativeHistoricalCropEncoding.png(source, selectedCrop)
                    }
                    val digest = NativeHistoricalCropEncoding.sha256(bytes)
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri)?.use {
                            it.write(bytes)
                        } ?: error("No SAF output stream")
                    }
                    val metadata = JSONObject()
                        .put("schema", "invertebrata.u116.historical-crop-review.v1")
                        .put("status", "DRAFT_FIGURE_CROP_NOT_ANATOMY_SIGNOFF")
                        .put("source_id", record.archiveId)
                        .put("source_catalogue", record.sourcePage)
                        .put("published_source_sha1", record.publishedSha1)
                        .put("imported_original_sha256", provenance)
                        .put("cropped_png_sha256", digest)
                        .put("caption_group", group.id)
                        .put("caption_scope", group.captionEn)
                        .put("stage_status", group.stageClassification)
                        .put("source_left", selectedCrop.left)
                        .put("source_top", selectedCrop.top)
                        .put("source_right_exclusive", selectedCrop.rightExclusive)
                        .put("source_bottom_exclusive", selectedCrop.bottomExclusive)
                        .put("crop_width", selectedCrop.width)
                        .put("crop_height", selectedCrop.height)
                        .put("approval", "NONE — historical print, not a certified hand-pencil scan")
                        .toString(2)
                    recordedExport = digest to metadata
                    exportMessage = if (tamil)
                        "அசல் பிக்சல் வரைவு ஏற்றுமதி செய்யப்பட்டது; அறிவியல் ஒப்புதல் இல்லை."
                    else "Exact pixel crop exported; scientific stage and anatomy UNVERIFIED."
                } catch (e: Exception) {
                    recordedExport = null
                    exportMessage = if (tamil) "வரைவுப் படத்தைச் சேமிக்க முடியவில்லை"
                    else "Crop export failed: " + (e.message ?: "IO")
                }
            }
        }
    }
    val jsonSave = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        val ready = recordedExport?.second
        if (uri != null && ready != null) {
            scope.launch {
                exportMessage = try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri)?.use {
                            it.write(ready.toByteArray(Charsets.UTF_8))
                        } ?: error("No JSON output stream")
                    }
                    if (tamil) "பிக்சல் சான்று JSON சேமிக்கப்பட்டது"
                    else "Draft source/crop checksum manifest saved"
                } catch (_: Exception) {
                    if (tamil) "JSON கோப்பைச் சேமிக்க முடியவில்லை"
                    else "JSON export failed"
                }
            }
        }
    }

    Card(modifier = Modifier.fillMaxWidth().testTag("u116-archive-source-check")) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                if (tamil) "வரலாற்று மூலப் படச் சான்றிதழ் — 1922"
                else "1922 printed archival source — identity check",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                if (tamil)
                    "சாண்ட்லர் (1922), படம் 14. பொதுஉரிமை வரலாற்றுப் புத்தகப் படம்; அசல் பென்சில் வரைதல் என்று சான்றில்லை."
                else
                    "Chandler (1922), Fig. 14. Public-domain historical PRINTED illustration; not verified as a physically hand-drawn pencil master.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                record.sourcePage,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.testTag("u116-source-url"),
            )
            Text(
                if (exact)
                    (if (tamil) "மூலப் படத்தின் பைட்டுகளும் SHA-1 பதிவும் பொருந்துகின்றன; உருவநிலை ஒப்புதல் இல்லை."
                     else "Original historical file fingerprint MATCHED; organism stage and anatomy remain UNAPPROVED.")
                else
                    (if (tamil) "அசல் வரலாற்றுக் கோப்பின் SHA-1 பொருந்தவில்லை அல்லது கோப்பு சேர்க்கப்படவில்லை. வெட்டிப் படம் உருவாக்க அனுமதி இல்லை."
                     else "Historical source fingerprint NOT MATCHED (or missing). Crop export locked; no speculative provenance."),
                color = if (exact) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("u116-source-identity"),
            )
            Text(
                "Archive original: 1552 × 1584 px · SHA-1: " +
                    record.publishedSha1,
                style = MaterialTheme.typography.labelSmall,
            )
            if (exact && bitmap != null && candidate != null) {
                Text(
                    if (tamil)
                        "வரலாற்றுப் படத்தில் உள்ள ஒவ்வொரு எழுத்துப் பகுதியும் தனியாகச் சரிபார்க்கப்பட வேண்டும்."
                    else
                        "Select caption group, then tap TWO corners on the original image. No group is automatically certified as an amastigote/promastigote.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth().testTag("u116-caption-group-picker"),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    items(groups, key = { it.id }) { group ->
                        FilterChip(
                            selected = group.id == groupId,
                            onClick = {
                                groupId = group.id
                                first = null
                                crop = null
                                recordedExport = null
                            },
                            label = { Text(group.id) },
                            modifier = Modifier.heightIn(min = 48.dp)
                                .testTag("u116-group-" + group.id),
                        )
                    }
                }
                Text(
                    if (tamil) selectedGroup.captionTa else selectedGroup.captionEn,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("u116-group-caption"),
                )
                Box(
                    modifier = Modifier.fillMaxWidth().height(355.dp)
                        .onSizeChanged { viewport = it }
                        .pointerInput(candidate.sourceSha256) {
                            detectTapGestures { pos ->
                                val hit = NativeRasterCalibration.hit(
                                    tapX = pos.x, tapY = pos.y,
                                    viewportWidth = viewport.width.toFloat(),
                                    viewportHeight = viewport.height.toFloat(),
                                    rasterWidth = bitmap.width, rasterHeight = bitmap.height,
                                )
                                if (hit != null) {
                                    if (first == null) {
                                        first = Offset(hit.pixelX, hit.pixelY)
                                        crop = null
                                    } else {
                                        crop = HistoricalPixelCrop.between(
                                            first!!.x, first!!.y,
                                            hit.pixelX, hit.pixelY,
                                            bitmap.width, bitmap.height,
                                        )
                                        first = null
                                    }
                                    recordedExport = null
                                }
                            }
                        }
                        .testTag("u116-archive-tap-corners"),
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = if (tamil)
                            "1922 அச்சுப் படத்தின் மூல பிக்சல்கள் — ஆய்வு நிலை"
                        else "1922 published plate original pixels; unapproved figure selection",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize(),
                    )
                    val color = MaterialTheme.colorScheme.error
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        fun projected(x: Float, y: Float): Offset? =
                            NativeRasterCalibration.project(
                                x, y, size.width, size.height,
                                bitmap.width, bitmap.height,
                            )?.let { Offset(it.first, it.second) }
                        first?.let { p ->
                            projected(p.x, p.y)?.let {
                                drawCircle(color, radius = 9.dp.toPx(),
                                    center = it, style = Stroke(2.dp.toPx()))
                            }
                        }
                        crop?.let { bounds ->
                            val tl = projected(bounds.left.toFloat(), bounds.top.toFloat())
                            // right/bottom exclusive: project last actual pixel.
                            val br = projected(
                                (bounds.rightExclusive - 1).toFloat(),
                                (bounds.bottomExclusive - 1).toFloat(),
                            )
                            if (tl != null && br != null) {
                                drawRect(
                                    color, topLeft = tl,
                                    size = Size(br.x-tl.x, br.y-tl.y),
                                    style = Stroke(2.dp.toPx()),
                                )
                            }
                        }
                    }
                }
                Text(
                    crop?.let {
                        "DRAFT pixels [${it.left},${it.top}] to [${it.rightExclusive},${it.bottomExclusive})" +
                            " · ${it.width} × ${it.height}"
                    } ?: (if (tamil) "50×50 பிக்சலுக்குக் குறையாத பகுதியைத் தேர்ந்தெடு"
                          else "Select a rectangle at least 50×50 original pixels"),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.testTag("u116-selected-crop"),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                            first = null; crop = null; recordedExport = null
                        },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u116-reset-crop"),
                    ) { Text(if (tamil) "மீட்டமை" else "Reset corners") }
                    Button(
                        enabled = crop != null,
                        onClick = { pngSave.launch("u116-${groupId}-UNVERIFIED.png") },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u116-export-png"),
                    ) { Text(if (tamil) "வரைவுப் படத்தைச் சேமி" else "Export draft crop") }
                }
                OutlinedButton(
                    enabled = recordedExport != null,
                    onClick = { jsonSave.launch("u116-${groupId}-provenance.json") },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u116-export-json"),
                ) {
                    Text(if (tamil) "SHA மற்றும் அனுமதி நிலைக் குறிப்பைச் சேமி"
                        else "Export source/crop checksum and approval-status JSON")
                }
                if (exportMessage.isNotBlank()) Text(exportMessage)
            }
        }
    }
}
