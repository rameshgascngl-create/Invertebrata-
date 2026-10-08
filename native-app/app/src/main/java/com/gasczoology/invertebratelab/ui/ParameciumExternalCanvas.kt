package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.ParameciumAnatomyDraft
import com.gasczoology.invertebratelab.data.ParameciumExternalGeometry

/**
 * N2.3B1 native Canvas PROTOTYPE, not an accepted anatomical plate.
 * A drawing, bilingual select controls and touch-based highlighting are
 * implemented. The provisional geometry does not satisfy the review gate.
 */
@Composable
fun ParameciumExternalCanvas(language: AppLanguage, modifier: Modifier = Modifier) {
    val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
    var selectedId by rememberSaveable { mutableStateOf("pellicle") }
    val chosen = plate.features.single { it.id == selectedId }

    Column(
        modifier = modifier.fillMaxWidth().testTag("n23b-external-preview"),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(if (language == AppLanguage.TAMIL)
            "பாரமீசியம் (P. caudatum) — இடமமைவு வரைபட முன்மாதிரி"
        else "Paramecium caudatum — schematic Canvas prototype")
        Text(if (language == AppLanguage.TAMIL)
            "உறுப்புகளின் இடம், தலைப்புகள், தமிழ் சொற்கள் அறிவியல் மதிப்பாய்வுக்கு உட்பட்டவை. தேர்வு செய்ய உறுப்பைத் தொடவும் அல்லது கீழுள்ள பொத்தானைப் பயன்படுத்தவும்."
        else "Provisional organ positions and Tamil terminology: scientific review pending. Tap a marked region or use the accessible buttons below.")

        Canvas(
            modifier = Modifier.fillMaxWidth().height(280.dp)
                .testTag("n23b-external-canvas")
                .semantics {
                    contentDescription = if (language == AppLanguage.TAMIL)
                        "பாரமீசியம் அமைப்பு முன்மாதிரி; கீழே உள்ள உறுப்புப் பொத்தான்கள் மூலம் தேர்வு செய்யலாம்"
                    else "Paramecium schematic; organ buttons below provide an accessible alternative"
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val width = size.width.toFloat()
                        val height = size.height.toFloat()
                        if (width > 0f && height > 0f) {
                            ParameciumExternalGeometry.hitNormalized(
                                offset.x / width, offset.y / height
                            )?.let { selectedId = it }
                        }
                    }
                },
        ) {
            drawRect(Color(0xFFF4FBFA))
            val horizontalScale = size.width / ParameciumExternalGeometry.REFERENCE_WIDTH
            val verticalScale = size.height / ParameciumExternalGeometry.REFERENCE_HEIGHT
            withTransform({
                scale(horizontalScale, verticalScale, pivot = Offset.Zero)
            }) {
                val silhouette = Path().apply {
                    moveTo(156f, 280f)
                    cubicTo(118f, 190f, 214f, 118f, 376f, 105f)
                    cubicTo(574f, 76f, 767f, 150f, 823f, 266f)
                    cubicTo(866f, 350f, 791f, 435f, 624f, 480f)
                    cubicTo(477f, 524f, 331f, 491f, 232f, 425f)
                    cubicTo(166f, 378f, 152f, 327f, 156f, 280f)
                    close()
                }
                drawPath(silhouette, Color(0xFFD3F1EC))
                drawPath(silhouette,
                    if (selectedId == "pellicle") Color(0xFFB55B16)
                    else Color(0xFF176D70),
                    style = Stroke(width = if (selectedId == "pellicle") 9f else 5f),
                )

                // Cilia sample the actual native Path contour rather than an unrelated oval.
                val measure = PathMeasure()
                measure.setPath(silhouette, true)
                for (i in 0 until 90) {
                    val length = measure.length
                    val distance = length * i.toFloat() / 90f
                    val at = measure.getPosition(distance)
                    val tangent = measure.getTangent(distance)
                    val outward = Offset(tangent.y, -tangent.x)
                    drawLine(
                        color = if (selectedId == "somatic-cilia")
                            Color(0xFFB55B16) else Color(0xFF357F82),
                        start = at,
                        end = at + outward * 18f,
                        strokeWidth = if (selectedId == "somatic-cilia") 4.5f else 2.5f,
                    )
                }

                // Candidate peristomal depression on the ventral side.
                val oralGroove = Path().apply {
                    moveTo(480f, 469f)
                    cubicTo(500f, 434f, 513f, 394f, 540f, 383f)
                    cubicTo(571f, 372f, 589f, 403f, 605f, 457f)
                }
                drawPath(
                    oralGroove,
                    if (selectedId == "oral-groove") Color(0xFFB55B16)
                    else Color(0xFF19888D),
                    style = Stroke(width = if (selectedId == "oral-groove") 14f else 9f),
                )

                // Cytoproct is depicted as a small separate surface opening.
                drawCircle(
                    if (selectedId == "cytoproct") Color(0xFFB55B16)
                    else Color(0xFF276A6D),
                    radius = if (selectedId == "cytoproct") 13f else 8f,
                    center = Offset(756f, 400f),
                )

                // Trichocysts: short cortical rods, not cilia or food vacuoles.
                for (i in 0..6) {
                    val x = 275f + i * 19f
                    val y = 355f + (i % 2) * 18f
                    drawLine(
                        if (selectedId == "trichocysts") Color(0xFFB55B16)
                        else Color(0xFF387C83),
                        start = Offset(x, y),
                        end = Offset(x + 15f, y - 22f),
                        strokeWidth = if (selectedId == "trichocysts") 7f else 4f,
                    )
                }

                // Selected structure gets a visual, testable positional marker.
                ParameciumExternalGeometry.hotspots.firstOrNull {
                    it.featureId == selectedId
                }?.let { area ->
                    drawCircle(
                        Color(0xFFE28D3E),
                        radius = 19f,
                        center = Offset(area.x * 1000f, area.y * 600f),
                        style = Stroke(width = 5f),
                    )
                }
            }
        }

        Text(
            (if (language == AppLanguage.TAMIL) "தேர்ந்தெடுத்த உறுப்பு: "
                else "Highlighted structure: ") + chosen.label.value(language),
            modifier = Modifier.testTag("n23b-selected-label"),
        )
        // Explicit large semantic touch targets; Canvas gesture is supplementary.
        for (feature in plate.features) {
            OutlinedButton(
                onClick = { selectedId = feature.id },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("n23b-select-" + feature.id),
            ) {
                Text(feature.label.value(language))
            }
        }
        Text(
            if (language == AppLanguage.TAMIL)
                "குறிப்பு: இந்த வடிவமும் தொடு-பகுதிகளும் மாதிரி மட்டுமே; சரிபார்க்கப்பட்ட உடலமைப்புப் படம் அல்ல."
            else "Note: schematic paths and hit regions are not academically verified geometry.",
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
