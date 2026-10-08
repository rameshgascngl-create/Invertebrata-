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
import com.gasczoology.invertebratelab.data.ParameciumCytoproctRidgeCandidate
import com.gasczoology.invertebratelab.data.ParameciumSchematicContour
import com.gasczoology.invertebratelab.data.ParameciumExternalEvidence
import com.gasczoology.invertebratelab.data.PrototypeCanvasViewport

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

        // The orientation is explicitly stated to avoid a figure being read
        // with reversed anterior/posterior or dorsal/ventral axes.
        val proposedView = ParameciumExternalEvidence.view
        Text(
            if (language == AppLanguage.TAMIL)
                "முன்புறம் (வட்டம்) ←   பின்புறம் (கூர்மை) →   வாய்ப்புறம்: கீழ்ப்பக்கம்"
            else "Anterior (rounded) ←   Posterior (tapered) →   Oral/ventral side: bottom",
            modifier = Modifier.testTag("n23b-orientation"),
        )
        require(!proposedView.orientationVerifiedAgainstFigure)

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
                            ParameciumExternalGeometry.hitCanvas(
                                offset.x, offset.y, width, height,
                            )?.let { selectedId = it }
                        }
                    }
                },
        ) {
            drawRect(Color(0xFFF4FBFA))
            // Uniform scale + centered letterboxing. Pointer input shares the
            // precise inverse transform to avoid distorted anatomy or false taps.
            val viewport = PrototypeCanvasViewport.fit(size.width, size.height)
            withTransform({
                translate(viewport.left, viewport.top)
                scale(viewport.scale, viewport.scale, pivot = Offset.Zero)
            }) {
                val silhouette = Path().apply {
                    // One coordinate source for the silhouette, test constraints
                    // and declared rounded-anterior / tapered-posterior orientation.
                    val contour = ParameciumSchematicContour
                    moveTo(contour.anterior.x, contour.anterior.y)
                    for (part in contour.segments) {
                        cubicTo(part.control1.x, part.control1.y,
                            part.control2.x, part.control2.y,
                            part.end.x, part.end.y)
                    }
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

                // N2.3C2: illustrate the CLOSED cytoproct as a short
                // ventral posterior ridge, not a permanently open round hole.
                // The TEM source supports morphology, NOT these xy coordinates.
                val ridge = ParameciumCytoproctRidgeCandidate
                val cytoproctPath = Path().apply {
                    moveTo(ridge.start.x, ridge.start.y)
                    quadraticBezierTo(ridge.control.x, ridge.control.y,
                        ridge.end.x, ridge.end.y)
                }
                drawPath(cytoproctPath,
                    if (selectedId == "cytoproct") Color(0xFFB55B16)
                    else Color(0xFF276A6D),
                    style = Stroke(width = if (selectedId == "cytoproct") 11f else 7f),
                )

                // N2.3C1 DRAFT: subpellicular cortical trichocysts sampled
                // along the native cell outline. Arc spacing is illustrative
                // and NOT a microscopy-validated density or organ position.
                for (i in 0 until 44) {
                    val distance = measure.length * (i + 0.5f) / 44f
                    val at = measure.getPosition(distance)
                    val tangent = measure.getTangent(distance)
                    val inward = Offset(-tangent.y, tangent.x)
                    drawLine(
                        if (selectedId == "trichocysts") Color(0xFFB55B16)
                        else Color(0xFF387C83),
                        start = at + inward * 10f,
                        end = at + inward * 32f,
                        strokeWidth = if (selectedId == "trichocysts") 6f else 3.5f,
                    )
                }

                // Longer posterior cilia, the characteristic caudal tuft of
                // P. caudatum. These original strokes are schematic and unreviewed.
                for (i in -3..3) {
                    drawLine(
                        color = if (selectedId == "somatic-cilia")
                            Color(0xFFB55B16) else Color(0xFF357F82),
                        start = Offset(872f, 307f + i * 2f),
                        end = Offset(910f + (3 - kotlin.math.abs(i)) * 5f,
                            307f + i * 16f),
                        strokeWidth = if (selectedId == "somatic-cilia") 4f else 2.5f,
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
        if (selectedId == "cytoproct") {
            Text(
                if (language == AppLanguage.TAMIL)
                    "மூடிய நிலையில் செல் கழிவு வெளியேறும் பகுதி மேடுபோலத் தோன்றும். இவ்வரைபடத்தில் அதன் துல்லியமான இடம் இன்னும் சரிபார்க்கப்படவில்லை."
                else "Closed cytoproct shown schematically as a ridge; its exact position has not been verified against a whole-cell micrograph.",
                modifier = Modifier.testTag("n23c2-cytoproct-evidence-limit"),
            )
        }
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
