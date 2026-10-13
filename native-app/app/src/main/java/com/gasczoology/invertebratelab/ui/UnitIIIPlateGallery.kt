package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
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
            if (tamil) "அசல் வரைவின் உறுப்பு இடத்தைத் தொடவும்; அல்லது படத்துக்குக் கீழே உறுப்பைப் தேர்ந்தெடுக்கவும். படங்கள் கல்வி மதிப்பாய்வில் உள்ளன."
            else "Tap a structure in the original artwork or select its accessible organ control below. These drawings remain editorial candidates.",
            style = MaterialTheme.typography.bodySmall
        )
        plates.forEach { plate ->
            val hotspots = remember(plate.assetStem) { UnitIIIPlateHotspots.forStem(plate.assetStem) }
            val geometryVerified = UnitIIIPlateHotspots.geometryApproved(plate.assetStem)
            var selectedId by rememberSaveable(plate.assetStem) {
                mutableStateOf(hotspots.firstOrNull()?.id.orEmpty())
            }
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
                        BoxWithConstraints(
                            modifier = Modifier.fillMaxWidth().aspectRatio(960f / 1280f)
                                .testTag("u3-plate-touch-" + plate.plateId)
                                .pointerInput(plate.assetStem, geometryVerified) {
                                    if (geometryVerified) detectTapGestures { point ->
                                        val fx = point.x / size.width.toFloat()
                                        val fy = point.y / size.height.toFloat()
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
                            Image(
                                bitmap = raster,
                                contentDescription = plate.title.value(language),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                                    .testTag("u3-plate-bitmap-" + plate.plateId)
                            )
                            Canvas(Modifier.fillMaxSize().testTag("u3-hotspot-marker-" + plate.plateId)) {
                                if (geometryVerified && selected != null) {
                                    val origin = Offset(size.width * selected.x, size.height * selected.y)
                                    // Leader originates EXACTLY at the selected anatomical landmark.
                                    val dx = if (selected.x < .5f) 27.dp.toPx() else -27.dp.toPx()
                                    val destination = Offset(origin.x + dx, origin.y - 25.dp.toPx())
                                    drawLine(Color(0xFF554C42),origin,destination,strokeWidth=1.6.dp.toPx())
                                    drawCircle(Color(0xFF7A4C26),radius=5.dp.toPx(),center=origin)
                                    drawCircle(Color(0xFFE9D7B5),radius=10.dp.toPx(),
                                        center=destination)
                                    drawCircle(Color(0xFF66574A),radius=10.dp.toPx(),
                                        center=destination, style=androidx.compose.ui.graphics.drawscope.Stroke(
                                            width=1.5.dp.toPx()))
                                }
                            }
                        }
                        Text(
                            if (tamil)
                                "மூல வரலாற்றுப் படம். புதிய படத்திற்கான தொடுபுள்ளிகள் இன்னும் சரிபார்க்கப்படவில்லை. கீழே உள்ள உறுப்புப் பெயரைத் தேர்ந்தெடுக்கவும்."
                            else "Archived raster. Image taps and leader marks are disabled pending source-specific hotspot calibration; organ controls remain below.",
                            color=MaterialTheme.colorScheme.error,
                            style=MaterialTheme.typography.bodySmall,
                            modifier=Modifier.testTag("u3-source-hotspot-review-hold")
                        )
                        if (hotspots.isNotEmpty()) {
                            LazyRow(horizontalArrangement=Arrangement.spacedBy(7.dp),
                                modifier=Modifier.testTag("u3-hotspot-controls-"+plate.plateId)) {
                                items(hotspots,key={it.id}) { hotspot ->
                                    OutlinedButton(
                                        onClick={
                                            selectedId=hotspot.id
                                            narrator.speak(hotspot.explanation,language)
                                        },
                                        modifier=Modifier.heightIn(min=48.dp)
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
                                OutlinedButton(
                                    onClick={narrator.speak(selected.explanation,language)},
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
