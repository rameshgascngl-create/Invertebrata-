package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIIIPlateSpec
import com.gasczoology.invertebratelab.data.UnitIIIPencilScanCandidate
import com.gasczoology.invertebratelab.data.UnitIIIPencilScanCandidateStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * DEBUG-ONLY, not an atlas image replacement. An original hand-pencil scan
 * can be supplied for independent scholarly review without overwriting the
 * six bundled candidate images or automatically mapping anatomy hotspots.
 */
@Composable
internal fun UnitIIIPencilScanIntake(
    plate: UnitIIIPlateSpec,
    language: AppLanguage,
    onClose: () -> Unit,
) {
    val context = LocalContext.current
    if (!isNativeSourceAuditDebuggable(context.applicationInfo.flags)) return
    val tamil = language == AppLanguage.TAMIL
    val scope = rememberCoroutineScope()
    var candidate by remember(plate.assetStem) {
        mutableStateOf<UnitIIIPencilScanCandidate?>(null)
    }
    var preview by remember(plate.assetStem) { mutableStateOf<ImageBitmap?>(null) }
    var isBusy by remember(plate.assetStem) { mutableStateOf(false) }
    var status by remember(plate.assetStem) { mutableStateOf("") }
    var showProvenance by remember { mutableStateOf(false) }

    LaunchedEffect(plate.assetStem) {
        candidate = withContext(Dispatchers.IO) {
            UnitIIIPencilScanCandidateStore.load(
                context.applicationContext,plate.assetStem)
        }
    }
    LaunchedEffect(candidate?.sourceSha256) {
        preview = null
        val source = candidate ?: return@LaunchedEffect
        preview = withContext(Dispatchers.Default) {
            val opts = BitmapFactory.Options().apply {
                var sample = 1
                while (maxOf(source.width / sample,source.height / sample) > 1600)
                    sample *= 2
                inSampleSize = sample
            }
            BitmapFactory.decodeByteArray(source.originalBytes,0,
                source.originalBytes.size,opts)?.asImageBitmap()
        }
    }

    val picker=rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri != null && !isBusy) {
            scope.launch {
                isBusy = true
                try {
                    val checked = withContext(Dispatchers.IO) {
                        UnitIIIPencilScanCandidateStore.importDocument(
                            context.applicationContext,plate.assetStem,uri)
                    }
                    candidate = checked
                    status = if (tamil)
                        "படக் கோப்பின் நேர்மை சரிபார்க்கப்பட்டது; கையால் வரைந்ததா/உடற்கூறு சரியா என்பது இன்னும் சரிபார்க்கப்படவில்லை."
                    else "File integrity verified; hand-drawn provenance and anatomy NOT approved."
                } catch (_: Exception) {
                    status = if (tamil)
                        "PNG/JPEG அசல் கோப்பு நிராகரிக்கப்பட்டது: அளவு, வடிவம், EXIF அல்லது பிக்சல் தரவு பொருந்தவில்லை."
                    else "Source rejected: file size, signature, EXIF or decoded pixels invalid."
                } finally {
                    isBusy = false
                }
            }
        }
    }
    val manifestExport = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        val source = candidate
        if (uri != null && source != null) {
            val document=JSONObject()
                .put("schema","invertebrata.u36.original-pencil-intake-candidate.v1")
                .put("approval_status",source.approvalStatus)
                .put("plate_asset_stem",source.plateStem)
                .put("original_source_bytes_sha256",source.sourceSha256)
                .put("original_width_px",source.width)
                .put("original_height_px",source.height)
                .put("original_image_format",source.format)
                .put("original_hand_drawn_authenticity", "NOT_VERIFIED")
                .put("depicted_organism_and_life_stage","REVIEW_PENDING")
                .put("source_copyright_and_reuse","REVIEW_PENDING")
                .put("independent_zoological_reviewers", JSONObject.NULL)
                .put("student_atlas_integration_authorized",false)
                .toString(2)
            scope.launch {
                status=try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri)?.use {
                            it.write(document.toByteArray(Charsets.UTF_8))
                        } ?: error("No file writer")
                    }
                    if(tamil) "ஒப்புதல் பெறாத மூலப்பட அறிக்கை சேமிக்கப்பட்டது."
                    else "Unverified source-intake manifest saved."
                } catch (_: Exception) {
                    if(tamil) "அறிக்கையை சேமிக்க முடியவில்லை."
                    else "Source manifest export failed."
                }
            }
        }
    }

    Dialog(onDismissRequest=onClose,
        properties=DialogProperties(usePlatformDefaultWidth=false)) {
        Surface(modifier=Modifier.fillMaxSize()
            .testTag("u36-source-intake-dialog-"+plate.plateId)) {
            Column(
                modifier=Modifier.fillMaxSize()
                    .statusBarsPadding().navigationBarsPadding().padding(8.dp),
                verticalArrangement=Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        if(tamil) "அசல் பென்சில் படம் — வரைவு"
                        else "Original pencil scan · UNVERIFIED",
                        modifier=Modifier.weight(1f)
                            .testTag("u36-source-intake-title-"+plate.plateId),
                        style=MaterialTheme.typography.titleSmall,
                        maxLines=2,overflow=TextOverflow.Ellipsis,
                    )
                    OutlinedButton(
                        onClick=onClose,
                        modifier=Modifier.heightIn(min=48.dp)
                            .testTag("u36-source-intake-close-"+plate.plateId),
                    ) { Text(if(tamil)"மூடு" else "Close") }
                }
                Text(
                    if(tamil)
                        "அசல் PNG/JPEG கோப்பைத் தேர்ந்தெடுக்கவும். கோப்பின் SHA-256 மட்டும் சரிபார்க்கப்படும்; படம் உண்மையான கையால் வரைந்த பென்சில் படமா, உடற்கூறு சரியா என்பது நிபுணர் ஆய்வுக்குரியது."
                    else "Select a genuine PNG/JPEG source scan using Android Files. SHA-256 verifies file identity, NOT pencil authenticity, biological accuracy or reuse rights.",
                    style=MaterialTheme.typography.labelMedium,
                    color=MaterialTheme.colorScheme.error,
                    modifier=Modifier.testTag("u36-identity-not-approval-"+plate.plateId),
                )
                Box(
                    modifier=Modifier.fillMaxWidth().weight(1f)
                        .testTag("u36-candidate-image-container-"+plate.plateId),
                ) {
                    if(preview != null) {
                        Image(
                            bitmap=requireNotNull(preview),
                            contentDescription=if(tamil)
                                "ஒப்புதல் பெறாத மூல ஸ்கேன் படம்"
                                else "Unapproved candidate source scan, not the student atlas image",
                            modifier=Modifier.fillMaxSize()
                                .testTag("u36-candidate-image-"+plate.plateId),
                            contentScale=ContentScale.Fit,
                        )
                    } else {
                        Text(
                            if(tamil) "பென்சில் ஸ்கேன் இன்னும் சேர்க்கப்படவில்லை"
                            else "No separately supplied scanned pencil plate yet",
                            modifier=Modifier.testTag("u36-no-candidate-"+plate.plateId),
                        )
                    }
                }
                if(candidate != null) {
                    val item=requireNotNull(candidate)
                    Text(
                        "UNVERIFIED · "+item.width+"×"+item.height+" · "+
                            item.format.uppercase()+" · SHA-256: "+
                            item.sourceSha256.take(16)+"…",
                        style=MaterialTheme.typography.labelSmall,
                        modifier=Modifier.testTag("u36-candidate-summary-"+plate.plateId),
                    )
                    OutlinedButton(
                        onClick={showProvenance=true},
                        modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                            .testTag("u36-full-hash-"+plate.plateId),
                    ) { Text(if(tamil) "முழு SHA-256 மற்றும் வரம்புகள்"
                             else "Full SHA-256 and review limitations") }
                }
                Button(
                    onClick={picker.launch(arrayOf("image/png","image/jpeg"))},
                    enabled=!isBusy,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                        .testTag("u36-choose-source-"+plate.plateId),
                ) { Text(if(tamil) "PNG/JPEG கோப்பைத் தேர்ந்தெடு"
                         else "Choose original PNG/JPEG (Android Files)") }
                if(candidate != null) {
                    Row(horizontalArrangement=Arrangement.spacedBy(8.dp),
                        modifier=Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick={manifestExport.launch(
                                "u36-"+plate.plateId+"-SOURCE-UNVERIFIED.json")},
                            modifier=Modifier.weight(1f).heightIn(min=48.dp)
                                .testTag("u36-export-manifest-"+plate.plateId),
                        ) { Text("JSON") }
                        OutlinedButton(
                            onClick={
                                scope.launch {
                                    isBusy=true
                                    val deleted=withContext(Dispatchers.IO) {
                                        UnitIIIPencilScanCandidateStore.discard(
                                            context.applicationContext,plate.assetStem)
                                    }
                                    if(deleted) { candidate=null; preview=null }
                                    status=if(deleted) "DRAFT SOURCE DISCARDED"
                                           else "Source delete failed"
                                    isBusy=false
                                }
                            },
                            enabled=!isBusy,
                            modifier=Modifier.weight(1f).heightIn(min=48.dp)
                                .testTag("u36-discard-source-"+plate.plateId),
                        ) { Text(if(tamil)"நீக்கு" else "Discard") }
                    }
                }
                if(isBusy) LinearProgressIndicator(Modifier.fillMaxWidth())
                if(status.isNotBlank()) Text(status,
                    style=MaterialTheme.typography.bodySmall,
                    modifier=Modifier.testTag("u36-source-status-"+plate.plateId))
            }
            if(showProvenance && candidate != null) {
                AlertDialog(onDismissRequest={showProvenance=false},
                    title={Text(if(tamil)"மூலக் கோப்பு SHA-256" else "Source identity")},
                    text={
                        Text("SHA-256: "+requireNotNull(candidate).sourceSha256+
                            "\nSTATUS: UNVERIFIED_USER_SUPPLIED_SOURCE"+
                            "\nNOT a certified pencil scan or approved anatomy."+
                            "\nSeparate human morphology, origin and rights review required.",
                            modifier=Modifier.testTag("u36-provenance-full-"+plate.plateId))
                    },
                    confirmButton={
                        TextButton(onClick={showProvenance=false}) {
                            Text(if(tamil)"மூடு" else "Close")
                        }
                    })
            }
        }
    }
}
