package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*
import kotlin.math.pow

/**
 * Bilingual native raster plates, geometry-anchored hotspot selection and voice.
 * Image art is always decoded from assets, never redrawn with Canvas/SVG.
 * Canvas draws ONLY the selected marker/leader; all anatomy stays in raster.
 */
@Composable
internal fun UnitIIIPlateGallery(chapterId: String, language: AppLanguage, narrator: OfflineNarrator) {
    val plates = UnitIIIPlateCatalog.forChapter(chapterId)
    if (plates.isEmpty()) return
    val tamil = language == AppLanguage.TAMIL
    val context = LocalContext.current
    val isReviewerBuild = isNativeSourceAuditDebuggable(context.applicationInfo.flags)
    val requestedSpeech by narrator.script.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth().testTag("u3-required-plate-gallery"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            if (tamil) "உடற்கூறுப் படங்கள் — அறிவியல் மதிப்பாய்வு நிலுவையில்"
            else "ANATOMICAL PLATES · expert review pending",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { heading() }
        )
        Text(
            if (tamil) "ஆய்வு நிலுவையிலுள்ள படங்களை 2×/4× பெரிதாக்கிப் பார்க்கலாம். நிபுணர் ஒப்புதல் பெறாத நேரடித் தொடுபுள்ளிகள் முடக்கப்பட்டுள்ளன; கீழுள்ள உறுப்புப் பொத்தான்களைப் பயன்படுத்துக."
            else "Inspect the original raster at 2×/4× with zoom and pan. Unreviewed image hotspots are disabled; use the accessible organ buttons below.",
            style = MaterialTheme.typography.bodySmall
        )
        plates.forEach { plate ->
            val completeAscarisCycle = plate.assetStem == "type_studies/ascaris/life-cycle"
            val hotspots = remember(plate.assetStem) { UnitIIIPlateHotspots.forStem(plate.assetStem) }
            val geometryVerified = UnitIIIPlateHotspots.geometryApproved(plate.assetStem)
            var selectedId by rememberSaveable(plate.assetStem) {
                mutableStateOf("")
            }
            // Real native bitmap magnification is per plate and survives lazy
            // row disposal; no artwork pixels or draft organ coordinates change.
            var magnification by rememberSaveable(plate.assetStem) { mutableFloatStateOf(1f) }
            var imagePanX by rememberSaveable(plate.assetStem) { mutableFloatStateOf(0f) }
            var imagePanY by rememberSaveable(plate.assetStem) { mutableFloatStateOf(0f) }
            var fullscreenOpen by remember { mutableStateOf(false) }
            var draftPixelReviewerOpen by remember { mutableStateOf(false) }
            var newPencilCandidateOpen by remember { mutableStateOf(false) }
            val selected = hotspots.firstOrNull { it.id == selectedId }
            Card(Modifier.fillMaxWidth().testTag("u3-plate-slot-" + plate.plateId)) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(plate.title.value(language), style = MaterialTheme.typography.titleMedium)
                    val raster = rememberRequiredRasterBitmap(plate.assetStem)
                    if (raster == null) {
                        Text(
                            if (tamil) "வரைவுப் படம் இல்லை — மாற்றாகப் போலியான படம் காட்டப்படாது."
                            else "Anatomical master unavailable — no substitute diagram displayed.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("u3-plate-pending-" + plate.plateId)
                        )
                    } else {
                        OutlinedButton(
                            onClick = { fullscreenOpen = true },
                            modifier = Modifier.fillMaxWidth().heightIn(min=48.dp)
                                .testTag("u3-open-fullscreen-" + plate.plateId),
                        ) {
                            Text(if (tamil)
                                "படத்தை முழுத்திரையில் பெரிதாக்கிப் பார்"
                                else "Open full-screen anatomy image")
                        }
                        if (fullscreenOpen) {
                            UnitIIIFullscreenRasterViewer(
                                plate = plate,
                                bitmap = raster,
                                language = language,
                                onClose = { fullscreenOpen = false },
                            )
                        }
                        // DEBUG-ONLY source-pixel review. No coordinates are
                        // handed to UnitIIIPlateHotspots or student builds.
                        if (isReviewerBuild) {
                            OutlinedButton(
                                onClick = { draftPixelReviewerOpen = true },
                                modifier = Modifier.fillMaxWidth().heightIn(min=48.dp)
                                    .testTag("u33-open-review-" + plate.plateId),
                            ) {
                                Text(if (tamil)
                                    "உறுப்பு இடத்தை அசல் பிக்சலில் ஆய்வு செய் — வரைவு"
                                    else "Review original-pixel organ coordinates — DRAFT")
                            }
                            if (draftPixelReviewerOpen) {
                                UnitIIIPixelReviewDialog(
                                    plate = plate,
                                    bitmap = raster,
                                    language = language,
                                    onClose = { draftPixelReviewerOpen = false },
                                )
                            }
                            // U3.6 keeps a real user-supplied pencil scan in
                            // separate private storage, NEVER in this raster or
                            // the student image/hash hotspot authorization.
                            OutlinedButton(
                                onClick = { newPencilCandidateOpen = true },
                                modifier = Modifier.fillMaxWidth().heightIn(min=48.dp)
                                    .testTag("u36-open-source-intake-" + plate.plateId),
                            ) {
                                Text(if (tamil)
                                    "உண்மையான பென்சில் ஸ்கேனைச் சேர் — நிபுணர் வரைவு"
                                    else "Import original pencil scan — reviewer only")
                            }
                            if (newPencilCandidateOpen) {
                                UnitIIIPencilScanIntake(
                                    plate = plate,
                                    language = language,
                                    onClose = { newPencilCandidateOpen = false },
                                )
                            }
                        }
                        BoxWithConstraints(
                            modifier = Modifier.fillMaxWidth().aspectRatio(960f / 1280f)
                                .clipToBounds()
                                .testTag("u3-plate-touch-" + plate.plateId)
                                .pointerInput(plate.assetStem) {
                                    detectTransformGestures { _, pan, change, _ ->
                                        val next = (magnification * change).coerceIn(1f, 4f)
                                        magnification = next
                                        if (next <= 1f) {
                                            imagePanX = 0f
                                            imagePanY = 0f
                                        } else {
                                            val limitX = (next - 1f) * size.width * .5f
                                            val limitY = (next - 1f) * size.height * .5f
                                            imagePanX = (imagePanX + pan.x).coerceIn(-limitX, limitX)
                                            imagePanY = (imagePanY + pan.y).coerceIn(-limitY, limitY)
                                        }
                                    }
                                }
                                .pointerInput(plate.assetStem, geometryVerified, magnification, imagePanX, imagePanY) {
                                    if (geometryVerified) detectTapGestures { point ->
                                        // Unproject through centre-pivot zoom and pan before
                                        // any future independently approved source-pixel hit.
                                        val px = (point.x - size.width * .5f - imagePanX) /
                                            magnification + size.width * .5f
                                        val py = (point.y - size.height * .5f - imagePanY) /
                                            magnification + size.height * .5f
                                        val fx = px / size.width.toFloat()
                                        val fy = py / size.height.toFloat()
                                        val hit = hotspots.minByOrNull {
                                            (it.x - fx).pow(2) + (it.y - fy).pow(2)
                                        }
                                        if (hit != null &&
                                            (hit.x - fx).pow(2) + (hit.y - fy).pow(2) < .0064f) {
                                            selectedId = hit.id
                                            narrator.speak(hit.explanation, language)
                                        }
                                    }
                                }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize().graphicsLayer {
                                    scaleX = magnification
                                    scaleY = magnification
                                    translationX = imagePanX
                                    translationY = imagePanY
                                }
                            ) {
                            Image(
                                bitmap = raster,
                                contentDescription = if (completeAscarisCycle) {
                                    if (tamil) "சிடிசி டிபிடிஎக்ஸ் அஸ்காரிஸ் முழு வாழ்க்கைச் சுழற்சி: மலத்தில் முட்டைகள், மண்ணில் கருவளர்ச்சி, விழுங்குதல், குடலில் இளவுயிரி வெளியேறுதல், நுரையீரல் இடம்பெயர்ச்சி, மீண்டும் விழுங்குதல், சிறுகுடலில் முதிர்ச்சி"
                                    else "CDC DPDx complete Ascaris life cycle: eggs passed in feces, maturation in soil, infective egg ingestion, intestinal hatching, lung migration, reswallowing and adults in small intestine"
                                } else plate.title.value(language),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                                    .testTag("u3-plate-bitmap-" + plate.plateId)
                            )
                            }
                        }
                        // Controls remain reachable in English/Tamil at 200% font.
                        // Their bounded centre-pivot geometry applies identically
                        // to decoded pixels and any future APPROVED marker overlay.
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .testTag("u3-magnification-controls-" + plate.plateId),
                            horizontalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    magnification = (magnification / 2f).coerceAtLeast(1f)
                                    // Re-centre when using the accessible step controls.
                                    // Exact bounds are only known during pointer gestures.
                                    imagePanX = 0f
                                    imagePanY = 0f
                                },
                                enabled = magnification > 1f,
                                modifier = Modifier.weight(1f).heightIn(min=48.dp)
                                    .testTag("u3-zoom-out-" + plate.plateId),
                            ) { Text("−") }
                            OutlinedButton(
                                onClick = {
                                    magnification = (magnification * 2f).coerceAtMost(4f)
                                    imagePanX = 0f
                                    imagePanY = 0f
                                },
                                enabled = magnification < 4f,
                                modifier = Modifier.weight(1f).heightIn(min=48.dp)
                                    .testTag("u3-zoom-in-" + plate.plateId),
                            ) { Text("+") }
                            OutlinedButton(
                                onClick = {
                                    magnification=1f
                                    imagePanX=0f
                                    imagePanY=0f
                                },
                                modifier = Modifier.weight(2f).heightIn(min=48.dp)
                                    .testTag("u3-zoom-reset-" + plate.plateId),
                            ) { Text(if(tamil) "மீட்டமை" else "Reset") }
                        }
                        Text(
                            if (tamil) "பெரிதாக்கம்: " + magnification.toInt() + "× (அதிகபட்சம் 4×)"
                            else "Image magnification: " + magnification.toInt() + "× (maximum 4×)",
                            modifier = Modifier.testTag("u3-zoom-level-" + plate.plateId),
                            style=MaterialTheme.typography.labelMedium
                        )
                        if (completeAscarisCycle) {
                            Text(
                                if (tamil) "அஸ்காரிஸ் வாழ்க்கைச் சுழற்சி: CDC DPDx மூலப்படத்தின் கிராஃபைட் நிறத் தோற்றம். இது கையால் வரைந்த அசல் பென்சில் படம் அல்ல. நேரடித் தொடுபுள்ளி ஆய்வு நிலுவையில் உள்ளது."
                                else "Source: CDC DPDx Ascariasis. Graphite-toned facsimile preserving the original CDC anatomy, labels and arrows; NOT an original pencil drawing. Pixel-coordinate review pending.",
                                modifier = Modifier.testTag("u3-cdc-complete-life-cycle-credit"),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            if (tamil)
                                "படத்தின் நேரடித் தொடுதலும் குறியீட்டுக் கோடுகளும் மூலப்படக் கோண ஆய்வு முடியும் வரை முடக்கப்பட்டுள்ளன. கீழே உள்ள தமிழ்ப் பகுதி விளக்கங்களைத் தேர்ந்தெடுக்கவும்."
                            else "Direct image taps and leader marks remain disabled until the exact source-pixel positions are reviewed. Accessible narrated stage controls remain available below.",
                            color=MaterialTheme.colorScheme.error,
                            style=MaterialTheme.typography.bodySmall,
                            modifier=Modifier.testTag("u3-source-hotspot-review-hold")
                        )
                        if (hotspots.isNotEmpty()) {
                            // Non-lazy, vertically stacked controls: every organ remains
                            // realized, scrollable and reachable at Android font scale 2.0.
                            // Buttons are semantic selectors, NOT unreviewed image endpoints.
                            Column(verticalArrangement=Arrangement.spacedBy(7.dp),
                                modifier=Modifier.fillMaxWidth()
                                    .testTag("u3-hotspot-controls-"+plate.plateId)) {
                                hotspots.forEach { hotspot ->
                                    OutlinedButton(
                                        onClick={
                                            selectedId=hotspot.id
                                            narrator.speak(
                                                UnitIIIPlateHotspots.narrationFor(plate.assetStem,hotspot.id),
                                                language
                                            )
                                        },
                                        modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                                            .testTag("u3-hotspot-"+plate.plateId+"-"+hotspot.id)
                                    ) { Text(hotspot.title.value(language)) }
                                }
                            }
                            if (selected != null) {
                                Text(selected.title.value(language),
                                    style=MaterialTheme.typography.titleMedium,
                                    modifier=Modifier.testTag("u3-selected-organ-"+plate.plateId))
                                Text(selected.explanation.value(language),
                                    modifier=Modifier.testTag("u3-organ-explanation-"+plate.plateId))
                                // This verifies the EXACT request submitted to Android TTS,
                                // not audible playback (which needs a physical phone).
                                if (requestedSpeech == selected.explanation) {
                                    Text(
                                        (if(tamil) "குரலுக்குக் கோரிய விளக்கம்: "
                                         else "Narration requested: ") +
                                        requestedSpeech!!.value(language),
                                        modifier=Modifier.testTag("u3-voice-request-"+plate.plateId),
                                        style=MaterialTheme.typography.bodySmall
                                    )
                                }
                                OutlinedButton(
                                    onClick={narrator.speak(
                                        UnitIIIPlateHotspots.narrationFor(plate.assetStem,selected.id),
                                        language
                                    )},
                                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                                        .testTag("u3-speak-organ-"+plate.plateId)
                                ) {
                                    Text(if(tamil)"உறுப்பின் விளக்கத்தைக் கேள்" else "Hear organ explanation")
                                }
                            }
                        }
                    }
                    Text(plate.learningAim.value(language),style=MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
