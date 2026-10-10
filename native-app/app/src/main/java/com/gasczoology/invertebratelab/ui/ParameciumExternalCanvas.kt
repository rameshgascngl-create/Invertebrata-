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
import com.gasczoology.invertebratelab.data.ParameciumReviewHighlightContract
import com.gasczoology.invertebratelab.data.ParameciumN23DReviewDossier
import com.gasczoology.invertebratelab.data.ParameciumN23EReviewerHandoff
import com.gasczoology.invertebratelab.data.ParameciumN23E2EvidenceIntake
import com.gasczoology.invertebratelab.data.E2ImageRights
import com.gasczoology.invertebratelab.data.E2SourceAccess
import com.gasczoology.invertebratelab.data.ParameciumSchematicContour
import com.gasczoology.invertebratelab.data.ParameciumExternalEvidence
import com.gasczoology.invertebratelab.data.PrototypeCanvasViewport

/**
 * R1.3 original pencil Canvas PROTOTYPE, not an accepted anatomical plate.
 * A drawing, bilingual select controls and touch-based highlighting are
 * implemented. The provisional geometry does not satisfy the review gate.
 */
@Composable
fun ParameciumExternalCanvas(language: AppLanguage, modifier: Modifier = Modifier,
    onOrganSelected: (String) -> Unit = {},
    onJumpToPlate: (() -> Unit)? = null,
    compact: Boolean = false,
    plateModifier: Modifier = Modifier) {
    val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
    var selectedId by rememberSaveable { mutableStateOf("pellicle") }
    val chosen = plate.features.single { it.id == selectedId }
    // Read-only source traceability: zero authenticated human approvals are
    // present. Evidence links never become accepted geometric coordinates.
    val reviewTask = ParameciumN23DReviewDossier.forFeature(selectedId)
    val humanHandoff = ParameciumN23EReviewerHandoff.forFeature(selectedId)
    // Offline lookup only: descriptive metadata must never certify specimen xy.
    val e2Audit = ParameciumN23E2EvidenceIntake
    val e2Source = when (selectedId) {
        "cytoproct" -> e2Audit.reference("cil-39181-cytoproct")
        "trichocysts" -> e2Audit.reference("cil-36755-trichocysts")
        else -> e2Audit.reference("sacred-heart-type-study")
    }

    Column(
        modifier = modifier.fillMaxWidth().testTag("n23b-external-preview"),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(if (language == AppLanguage.TAMIL)
            "பாரமீசியம் (P. caudatum) — இடமமைவு வரைபட முன்மாதிரி"
        else "Paramecium caudatum — original pencil atlas draft")

        Canvas(
            modifier = plateModifier.fillMaxWidth().height(280.dp)
                .testTag("n23b-external-canvas")
                .semantics {
                    contentDescription = if (language == AppLanguage.TAMIL)
                        "பாரமீசியம் அமைப்பு முன்மாதிரி; கீழே உள்ள உறுப்புப் பொத்தான்கள் மூலம் தேர்வு செய்யலாம்"
                    else "Paramecium schematic; organ buttons below provide an accessible alternative"
                }
                .pointerInput(onOrganSelected) {
                    detectTapGestures { offset ->
                        val width = size.width.toFloat()
                        val height = size.height.toFloat()
                        if (width > 0f && height > 0f) {
                            ParameciumExternalGeometry.hitCanvas(
                                offset.x, offset.y, width, height,
                            )?.let { selectedId = it; onOrganSelected(it) }
                        }
                    }
                },
        ) {
            drawRect(PencilAtlasPalette.paper)
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
                drawPath(silhouette,PencilAtlasPalette.cell)
                drawPencilCellDetails(silhouette)
                // Hand-pencilled multi-pass contour; unchanged reference path.
                drawPath(silhouette,PencilAtlasPalette.hatch,style=Stroke(11f))
                drawPath(silhouette,PencilAtlasPalette.dark,style=Stroke(4f))
                drawPath(silhouette,
                    if(selectedId=="pellicle") PencilAtlasPalette.selected
                    else PencilAtlasPalette.mid,
                    style=Stroke(width=if(selectedId=="pellicle") 4f else 1.4f)
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
                            PencilAtlasPalette.selected else PencilAtlasPalette.graphite,
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
                    if (selectedId == "oral-groove") PencilAtlasPalette.selected
                    else PencilAtlasPalette.dark,
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
                    if (selectedId == "cytoproct") PencilAtlasPalette.selected
                    else PencilAtlasPalette.graphite,
                    style = Stroke(width = if (selectedId == "cytoproct")
                        ParameciumReviewHighlightContract.SELECTED_RIDGE_STROKE else 7f),
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
                        if (selectedId == "trichocysts") PencilAtlasPalette.selected
                        else PencilAtlasPalette.mid,
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
                            PencilAtlasPalette.selected else PencilAtlasPalette.graphite,
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
                        radius = ParameciumReviewHighlightContract.radiusFor(selectedId),
                        center = Offset(area.x * 1000f, area.y * 600f),
                        style = Stroke(width = ParameciumReviewHighlightContract.RING_STROKE),
                    )
                }
            }
        }

        Text(if (language == AppLanguage.TAMIL)
            "உறுப்புகளின் இடம், தலைப்புகள், தமிழ் சொற்கள் அறிவியல் மதிப்பாய்வுக்கு உட்பட்டவை. தேர்வு செய்ய உறுப்பைத் தொடவும் அல்லது கீழுள்ள பொத்தானைப் பயன்படுத்தவும்."
        else "Provisional organ positions and Tamil terminology: scientific review pending. Tap a marked region or use the accessible buttons below.")

        Text(if(language==AppLanguage.TAMIL)
            "கருநிறப் பென்சில் வரைபடம்; தேர்ந்தெடுத்த உறுப்பிற்கு மட்டும் வண்ணக் குறி. நிபுணர் மதிப்பாய்வு நிலுவை."
        else "Original graphite-pencil anatomical drawing. Amber identifies only the selected structure; expert review pending.",
            modifier=Modifier.testTag("r13-pencil-atlas-style"))

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


        Text(
            (if (language == AppLanguage.TAMIL) "தேர்ந்தெடுத்த உறுப்பு: "
                else "Highlighted structure: ") + chosen.label.value(language),
            modifier = Modifier.testTag("n23b-selected-label"),
        )
        if (!compact) {
        Text(
            if (language == AppLanguage.TAMIL)
                "N2.3D1 மதிப்பாய்வு நிலை — உயிரியல்: 0/5; தமிழ்: 0/5; நேரடி சாதனச் சோதனை: நிலுவை"
            else "N2.3D1 review records — biology: 0/5; Tamil: 0/5; physical-device QA: pending",
            modifier = Modifier.testTag("n23d-review-summary"),
        )
        Text(
            (if (language == AppLanguage.TAMIL)
                "ஆதாரத்தில் உறுதியான தகவல்: " else "Source supports: ") +
                reviewTask.primary.establishes.value(language),
            modifier = Modifier.testTag("n23d-source-support"),
        )
        Text(
            (if (language == AppLanguage.TAMIL)
                "இன்னும் உறுதியாகாதவை: " else "Not yet established: ") +
                reviewTask.primary.doesNotEstablish.value(language),
            modifier = Modifier.testTag("n23d-source-limit"),
        )
        Text(
            "Reference: " + reviewTask.primary.exactLocator +
                " — " + reviewTask.primary.referenceUrl,
            modifier = Modifier.testTag("n23d-source-locator"),
        )
        Text(
            (if (language == AppLanguage.TAMIL) "N2.3E1 நிபுணர் மதிப்பாய்வுக் கேள்வி: "
                else "N2.3E1 expert-review question: ") +
                humanHandoff.reviewQuestion.value(language),
            modifier = Modifier.testTag("n23e-review-question"),
        )
        Text(
            (if (language == AppLanguage.TAMIL) "ஆதாரத்தின் வரம்பு: "
                else "Evidence limitation: ") +
                humanHandoff.evidenceBoundary.value(language),
            modifier = Modifier.testTag("n23e-review-boundary"),
        )
        Text(
            if (language == AppLanguage.TAMIL)
                "நிபுணர் உயிரியல் ஒப்புதல்: 0/5; தமிழ் ஒப்புதல்: 0/5; நேரடி மதிப்பாய்வு நிலுவை"
            else "Independent biology approvals: 0/5; Tamil approvals: 0/5; human inspection pending",
            modifier = Modifier.testTag("n23e-review-status"),
        )
        Text(
            if (language == AppLanguage.TAMIL)
                "N2.3E2 ஆதார அணுகல்: " +
                    (if (e2Source.access == E2SourceAccess.SEARCH_INDEX_METADATA_ONLY)
                        "தேடல் குறியீட்டுத் தகவல் மட்டுமே; முதன்மைப் படத்திற்கான நேரடி மதிப்பாய்வு நிலுவை."
                    else "ஆதார விளக்க உரை சரிபார்க்கப்பட்டது; பட ஒப்புதல் நிலுவை.")
            else "N2.3E2 source access: " +
                (if (e2Source.access == E2SourceAccess.SEARCH_INDEX_METADATA_ONLY)
                    "indexed metadata only; direct reference-image inspection pending."
                else "source description retrieved; image-level inspection pending."),
            modifier = Modifier.testTag("n23e2-source-access"),
        )
        Text(
            if (language == AppLanguage.TAMIL)
                "பட உரிமை: " + when (e2Source.rights) {
                    E2ImageRights.PUBLIC_DOMAIN_REPORTED_ON_RECORD -> "பதிவில் பொதுச் சொத்து; மீண்டும் உறுதிசெய்ய வேண்டும்."
                    E2ImageRights.COPYRIGHT_PERMISSION_REQUIRED -> "பயன்படுத்த உரிய அனுமதி தேவை."
                    else -> "பட மறுபயன்பாட்டுக்கான உரிமை உறுதிப்படுத்தப்படவில்லை."
                }
            else "Image rights: " + when (e2Source.rights) {
                E2ImageRights.PUBLIC_DOMAIN_REPORTED_ON_RECORD -> "record states public domain; reconfirm before reuse."
                E2ImageRights.COPYRIGHT_PERMISSION_REQUIRED -> "permission required for reproduction."
                else -> "reuse rights not established for this reference."
            },
            modifier = Modifier.testTag("n23e2-image-rights"),
        )
        if (selectedId == "cytoproct") {
            Text(
                if (language == AppLanguage.TAMIL)
                    "மூடிய நிலையில் செல் கழிவு வெளியேறும் பகுதி மேடுபோலத் தோன்றும். இவ்வரைபடத்தில் அதன் துல்லியமான இடம் இன்னும் சரிபார்க்கப்படவில்லை."
                else "Closed cytoproct shown schematically as a ridge; its exact position has not been verified against a whole-cell micrograph.",
                modifier = Modifier.testTag("n23c2-cytoproct-evidence-limit"),
            )
        }
        if (selectedId == "oral-groove") {
            Text(
                if (language == AppLanguage.TAMIL)
                    "வாய்ப்பள்ளம் வாய்ப்புறத்தில் அமைந்துள்ளது. இவ்வரைபடத்தில் காட்டியுள்ள அதன் வளைவும் துல்லியமான இடமும் முழுச் செல் நுண்ணோக்கிப் படத்துடன் இன்னும் சரிபார்க்கப்படவில்லை."
                else "The oral groove lies on the oral/ventral surface. Its curve and exact position in this schematic have not been validated against whole-cell microscopy.",
                modifier = Modifier.testTag("n23c3-oral-geometry-limit"),
            )
        }
        }
        // Explicit large semantic touch targets; Canvas gesture is supplementary.
        for (feature in plate.features) {
            OutlinedButton(
                onClick = { selectedId = feature.id; onOrganSelected(feature.id) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("n23b-select-" + feature.id),
            ) {
                Text(feature.label.value(language))
            }
        }
        Text(
            if (language == AppLanguage.TAMIL)
                "குறிப்பு: இந்த வடிவமும் தொடு-பகுதிகளும் மாதிரி மட்டுமே; சரிபார்க்கப்பட்ட உடலமைப்புப் படம் அல்ல."
            else "Original graphite-pencil plate; no stock art was copied. Organ counts, positions, density and touch geometry remain unverified illustrations.",
            modifier = Modifier.padding(top = 4.dp),
        )
        if (onJumpToPlate != null) {
            OutlinedButton(
                onClick = onJumpToPlate,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r13-jump-to-pencil-atlas"),
            ) {
                Text(if (language == AppLanguage.TAMIL)
                    "பென்சில் உடலமைப்பு வரைபடத்திற்குச் செல்"
                else "View pencil anatomy plate")
            }
        }
    }
}
