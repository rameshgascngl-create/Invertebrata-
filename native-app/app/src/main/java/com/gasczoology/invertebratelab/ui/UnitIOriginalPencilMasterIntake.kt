package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.NativePencilCandidate
import com.gasczoology.invertebratelab.data.NativePencilMasterStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Strictly native Android review-only intake of genuine supplied pencil scans.
 *
 * Imported user files are PROVISIONAL, remain app-private, and NEVER substitute
 * for bundled source figures or become visible in non-debuggable student builds.
 */
@Composable
internal fun UnitIOriginalPencilMasterIntake(
    organism: String,
    language: AppLanguage,
) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return

    val tamil = language == AppLanguage.TAMIL
    val scope = rememberCoroutineScope()
    var candidate by remember(organism) { mutableStateOf<NativePencilCandidate?>(null) }
    var busy by remember(organism) { mutableStateOf(false) }
    var status by remember(organism) { mutableStateOf("") }
    var openReviewer by remember(organism) { mutableStateOf(false) }
    var decodedBitmap by remember(organism) {
        mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null)
    }

    LaunchedEffect(candidate?.sourceSha256) {
        decodedBitmap = null
        val source = candidate ?: return@LaunchedEffect
        // Decode on a background dispatcher: a full scanned page can be 10MP.
        // InSampleSize remains 1, so reviewer coordinates map to SOURCE pixels.
        decodedBitmap = withContext(Dispatchers.Default) {
            BitmapFactory.decodeByteArray(
                source.originalBytes, 0, source.originalBytes.size,
            )?.asImageBitmap()
        }
    }

    LaunchedEffect(organism) {
        candidate = withContext(Dispatchers.IO) {
            NativePencilMasterStore.load(context.applicationContext, organism)
        }
    }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri != null && !busy) {
            scope.launch {
                busy = true
                status = ""
                try {
                    val checked = withContext(Dispatchers.IO) {
                        NativePencilMasterStore.importDocument(
                            context.applicationContext, organism, uri
                        )
                    }
                    // A replacement never reuses previous marks. The SHA binds
                    // exported draft pixels to exactly these bytes.
                    candidate = checked
                    openReviewer = false
                    status = if (tamil) "வரைவுப் படம் உள்வாங்கப்பட்டது — ஆய்வு நிலுவையில் உள்ளது"
                    else "Candidate scan imported — NOT anatomically approved"
                } catch (e: Exception) {
                    val rotatedJpeg = e.message?.contains("EXIF rotation/mirroring") == true
                    status = if (tamil && rotatedJpeg)
                        "JPEG படத்தில் சுழற்சி அல்லது கண்ணாடிப் பிரதிபலிப்பு தகவல் உள்ளது. " +
                            "படத்தைச் சரியான திசையில் PNG/JPEG ஆகச் சேமித்து மீண்டும் தேர்ந்தெடுக்கவும். " +
                            "தவறான உடலமைப்புப் பிக்சல் அளவீட்டைத் தவிர்க்க படம் நிராகரிக்கப்பட்டது."
                    else if (tamil)
                        "படத்தை ஏற்க முடியவில்லை: " + (e.message ?: "தவறான கோப்பு")
                    else
                        "Image rejected: " + (e.message ?: "invalid file")
                } finally {
                    busy = false
                }
            }
        }
    }

    Card(modifier = Modifier.fillMaxWidth().testTag("u110-native-plate-intake-" + organism)) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Text(
                if (tamil) "உண்மையான பென்சில் வரைபடத்தைச் சேர்க்க — ஆய்வாளர் மட்டும்"
                else "Import an actual pencil-scan plate — reviewer only",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                if (tamil)
                    "புகைப்படமோ PNG/JPEG ஸ்கேனோ சேர்க்கலாம். கோப்பு வடிவமும் அளவும் பரிசோதிக்கப்படும்; அசல் கையால் வரையப்பட்டதா, உயிரியல் சரியா என்பதை இயந்திரம் சான்றளிக்காது. மாணவர் பதிப்பில் இந்த வரைவு தோன்றாது."
                else
                    "Choose a photographed or scanned PNG/JPEG pencil plate. File integrity can be checked automatically; original hand-drawing provenance and zoological accuracy cannot. This candidate is excluded from student builds.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u110-artwork-hold-" + organism),
            )
            OutlinedButton(
                onClick = { picker.launch(arrayOf("image/png", "image/jpeg")) },
                enabled = !busy,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("u110-import-button-" + organism),
            ) {
                Text(if (tamil) "PNG/JPEG பென்சில் ஸ்கேனைத் தேர்ந்தெடு"
                    else "Choose scanned pencil plate (Android Files)")
            }
            if (busy) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            if (status.isNotBlank()) Text(
                status,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u110-import-status-" + organism),
            )
            if (candidate == null) {
                Text(
                    if (tamil) "தனிப் பென்சில் வரைபடம் இன்னும் சேர்க்கப்படவில்லை."
                    else "No authentic pencil plate candidate has been supplied.",
                    modifier = Modifier.testTag("u110-no-candidate-" + organism),
                )
            } else {
                val master = requireNotNull(candidate)
                val bitmap = decodedBitmap
                Text(
                    "DRAFT_UNVERIFIED · " + master.width + " × " + master.height +
                        " px · " + master.format.uppercase(),
                    modifier = Modifier.testTag("u110-candidate-metadata-" + organism),
                )
                Text(
                    "SHA-256: " + master.sourceSha256,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.testTag("u110-source-checksum-" + organism),
                )
                if (bitmap == null) {
                    Text(
                        if (tamil) "முன்காட்சி ஏற்றப்படுகிறது அல்லது படம் வாசிக்க முடியவில்லை"
                        else "Loading exact source pixels or unable to decode image",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.testTag("u110-preview-loading-" + organism),
                    )
                } else {
                    Image(
                        bitmap = bitmap,
                        contentDescription = if (tamil)
                            "ஏற்றப்பட்ட ஆய்வு வரைவு; அறிவியல் ஒப்புதல் பெறவில்லை"
                        else "Imported unverified pencil plate candidate",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth().height(290.dp)
                            .testTag("u110-candidate-image-" + organism),
                    )
                    Button(
                        onClick = { openReviewer = true },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u110-calibrate-imported-" + organism),
                    ) {
                        Text(if (tamil) "இந்த வரைவுப் படத்தின் உண்மையான பிக்சல்களை ஆய்வு செய்"
                            else "Calibrate this exact imported image")
                    }
                    if (openReviewer) {
                        NativeImportedPlateReviewer(
                            organism = organism,
                            bitmap = bitmap,
                            sha256 = master.sourceSha256,
                            language = language,
                            onClose = { openReviewer = false },
                        )
                    }
                }
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            busy = true
                            val deleted = withContext(Dispatchers.IO) {
                                NativePencilMasterStore.discard(context.applicationContext, organism)
                            }
                            if (deleted) {
                                candidate = null
                                openReviewer = false
                                status = if (tamil) "ஆய்வுப் படம் நீக்கப்பட்டது"
                                else "Review candidate discarded"
                            } else status = if (tamil) "நீக்க முடியவில்லை" else "Delete failed"
                            busy = false
                        }
                    },
                    enabled = !busy,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u110-discard-candidate-" + organism),
                ) {
                    Text(if (tamil) "ஆய்வுப் படத்தை நீக்கு" else "Discard unverified candidate")
                }
            }
            // Printed 1922 Leishmania source: strict original-file fingerprint.
            // Preview/crop is available only on a byte-identical source image,
            // and never silently becomes an approved student-facing plate.
            UnitIHistoricalPlateCropReviewer(
                organism = organism,
                candidate = candidate,
                bitmap = decodedBitmap,
                language = language,
            )
        }
    }
}
