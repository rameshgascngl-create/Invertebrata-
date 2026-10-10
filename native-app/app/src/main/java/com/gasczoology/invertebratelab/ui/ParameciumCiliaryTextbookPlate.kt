package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.ParameciumLearningEngine
import com.gasczoology.invertebratelab.data.ParameciumProcess
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * R1.6 isolated native Canvas candidate. The four stages come from the
 * existing physiology engine. The geometry and Tamil are DRAFT_UNVERIFIED.
 */
private fun ciliaryText(language: AppLanguage, en: String, ta: String): String =
    if (language == AppLanguage.TAMIL) ta else en

private data class CiliarySubsection(
    val heading: BilingualText,
    val explanation: BilingualText,
)

private val ciliarySubsections = listOf(
    CiliarySubsection(
        BilingualText("A. Motile ciliary structure", "அ. இயங்கும் குறுஇழையின் அமைப்பு"),
        BilingualText(
            "A typical motile cilium has nine peripheral microtubule doublets around two central singlets (the 9 + 2 axoneme). A basal body anchors the cilium in the cell cortex. The small inset shows the conventional textbook arrangement, not an electron-microscopy image.",
            "பொதுவாக இயங்கும் குறுஇழையில் ஒன்பது புற நுண்குழாய் இரட்டைகளும் மையத்தில் இரண்டு ஒற்றை நுண்குழாய்களும் உள்ளன (9 + 2 ஆக்சோனீம்). அடித்தளத் துகள் குறுஇழையை செல்லின் புறப்பகுதியில் நிலைநிறுத்துகிறது. சிறுபடம் மின்னணு நுண்ணோக்கிப் படம் அல்ல; வழக்கமான பாடநூல் விளக்க வரைபடம்."
        )
    ),
    CiliarySubsection(
        BilingualText("B. Effective stroke and recovery", "ஆ. இயக்க அடியும் மீள்நிலை அடியும்"),
        BilingualText(
            "Axonemal dynein activity drives microtubule sliding that is converted into bending. The effective stroke moves surrounding fluid, while a differently curved recovery stroke prepares the next beat. Pencil curves distinguish the phases: neither stroke angle nor beat frequency has been measured on this plate.",
            "ஆக்சோனீமின் டைனீன் இயக்கம் நுண்குழாய்களின் சறுக்கலை ஏற்படுத்தி அதை வளைவாக மாற்ற உதவுகிறது. இயக்க அடி சுற்றியுள்ள நீரை நகர்த்துகிறது; வேறுவிதமாக வளைந்த மீள்நிலை அடி அடுத்த அசைவுக்குத் தயாராகிறது. பென்சில் கோடுகள் கட்டங்களின் வேறுபாட்டை விளக்குகின்றன; இயக்கக் கோணமோ அசைவு அதிர்வெண்ணோ இதில் அளவிடப்படவில்லை."
        )
    ),
    CiliarySubsection(
        BilingualText("C. Metachronal waves and avoidance", "இ. மெட்டாக்ரோனல் அலைகளும் தவிர்ப்பு எதிர்வினையும்"),
        BilingualText(
            "Neighbouring cilia beat with different phases to create travelling metachronal waves; they do not form a rigid, synchronous brush. Ion fluxes across an excitable cell membrane, including calcium-dependent responses, can alter ciliary beating and briefly reverse swimming. No nervous system is involved.",
            "அருகருகில் உள்ள குறுஇழைகள் வேறுபட்ட இயக்கக் கட்டங்களில் அசைவதால் மெட்டாக்ரோனல் அலைகள் உருவாகின்றன; அவை ஒரே கடினமான தூரிகையாக அசைவதில்லை. கால்சியம் சார்ந்த பதில்கள் உட்பட தூண்டுதிறன் கொண்ட செல் படலத்தின் அயனி ஓட்டங்கள் குறுஇழை அசைவை மாற்றி சிறிது நேரம் பின்னோக்கி நீந்தச் செய்யலாம். இதற்கு நரம்பு மண்டலம் தேவையில்லை."
        )
    )
)

@Composable
internal fun ParameciumCiliaryTextbookPlate(
    language: AppLanguage,
    speak: (BilingualText) -> Unit,
) {
    val process = remember {
        ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION)
    }
    var stageIndex by rememberSaveable { mutableIntStateOf(0) }
    val stage = process.stages[stageIndex]
    val requester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth().testTag("r16-ciliary-textbook"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF8F3)),
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                ciliaryText(language, "PENCIL ATLAS · CILIARY LOCOMOTION",
                    "பென்சில் உடலமைப்புப் படம் · குறுஇழை இயக்கம்"),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("r16-ciliary-title"),
            )
            Text(
                ciliaryText(language,
                    "Side-view of a ciliated cortex; ciliary strokes and 9 + 2 inset are teaching schematics, not to scale.",
                    "குறுஇழைகள் கொண்ட செல் புறப்பகுதியின் பக்கவாட்டுக் காட்சி; அசைவுகளும் 9 + 2 சிறுபடமும் அளவுக்கு ஏற்ப அல்ல."),
                style = MaterialTheme.typography.bodyMedium,
            )
            Button(
                onClick = { scope.launch { requester.bringIntoView() } },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r16-view-ciliary-plate"),
            ) {
                Text(ciliaryText(language,
                    "Bring the pencil diagram fully into view",
                    "பென்சில் வரைபடத்தை முழுவதும் திரையில் காண்க"))
            }
            Canvas(
                Modifier.fillMaxWidth().height(220.dp)
                    .bringIntoViewRequester(requester)
                    .testTag("r16-ciliary-canvas")
                    .semantics {
                        contentDescription = ciliaryText(language,
                            "Side-view schematic of cilia, basal bodies and a 9 plus 2 axoneme. Selected stage: " + stage.heading.english,
                            "குறுஇழைகள், அடித்தளத் துகள்கள் மற்றும் 9 + 2 ஆக்சோனீம் விளக்கப்படம். தேர்ந்தெடுத்த நிலை: " + stage.heading.tamil)
                    }
            ) {
                val w = size.width
                val h = size.height
                val graphite = PencilAtlasPalette.graphite
                val selected = PencilAtlasPalette.selected
                val baseline = h * .68f
                drawRect(PencilAtlasPalette.paper)
                drawRect(PencilAtlasPalette.cell,
                    topLeft = Offset(w * .08f, baseline + 4f),
                    size = Size(w * .84f, h * .20f))
                drawLine(graphite, Offset(w * .08f, baseline),
                    Offset(w * .92f, baseline), strokeWidth = 3f)
                drawLine(PencilAtlasPalette.hatch,
                    Offset(w * .08f, baseline + 8f),
                    Offset(w * .92f, baseline + 8f), strokeWidth = 1.5f)

                // Eleven representative strokes; schematic phase, not motion capture.
                for (i in 0 until 11) {
                    val x = w * (.13f + .074f * i)
                    val lean = when (stage.id) {
                        "effective" -> .085f
                        "recovery" -> -.060f
                        "wave" -> ((i % 5) - 2) * .031f
                        else -> .008f
                    }
                    val lift = h *
                        (if (stage.id == "wave" && i % 3 == 0) .28f else .30f)
                    val contour = Path().apply {
                        moveTo(x, baseline)
                        quadraticBezierTo(
                            x + lean * w * .30f, baseline - lift * .65f,
                            x + lean * w, baseline - lift)
                    }
                    val emphasis = i == stageIndex * 2 + 1
                    drawPath(contour, color = if (emphasis) selected else graphite,
                        style = Stroke(width = if (emphasis) 3.6f else 2.2f))
                    drawCircle(graphite, radius = 3.2f,
                        center = Offset(x, baseline + 2.5f))
                }

                // Representative 9+2 cross-section; central pair != 2 doublets.
                val center = Offset(w * .80f, h * .21f)
                val radius = h * .064f
                drawCircle(graphite, radius = radius + h * .022f,
                    center = center, style = Stroke(width = 1.6f))
                for (j in 0 until 9) {
                    val angle = 2.0 * PI * j / 9
                    val x = center.x + cos(angle).toFloat() * radius
                    val y = center.y + sin(angle).toFloat() * radius
                    drawCircle(graphite, radius = 2.4f, center = Offset(x - 2.2f, y))
                    drawCircle(graphite, radius = 2.4f, center = Offset(x + 2.2f, y))
                }
                drawCircle(selected, radius = 2.6f,
                    center = Offset(center.x - 3.5f, center.y))
                drawCircle(selected, radius = 2.6f,
                    center = Offset(center.x + 3.5f, center.y))
            }
            Text(
                ciliaryText(language,
                    "Cell cortex below · basal bodies at the dark line · axonemes above · inset: typical 9 + 2",
                    "கீழே செல் புறப்பகுதி · கரிய கோட்டில் அடித்தளத் துகள்கள் · மேலே ஆக்சோனீம்கள் · சிறுபடம்: வழக்கமான 9 + 2"),
                modifier = Modifier.testTag("r16-ciliary-legend"),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                ciliaryText(language,
                    "Stage " + (stageIndex + 1) + "/" + process.stages.size + " — ",
                    "நிலை " + (stageIndex + 1) + "/" + process.stages.size + " — ") +
                    stage.heading.value(language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("r16-ciliary-stage-title"),
            )
            Text(stage.explanation.value(language),
                modifier = Modifier.testTag("r16-ciliary-stage-explanation"),
                style = MaterialTheme.typography.bodyLarge)
            for ((position, candidate) in process.stages.withIndex()) {
                val choose = { stageIndex = position }
                val buttonModifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r16-ciliary-stage-" + candidate.id)
                if (position == stageIndex) {
                    Button(onClick = choose, modifier = buttonModifier) {
                        Text((position + 1).toString() + ". " + candidate.heading.value(language))
                    }
                } else {
                    OutlinedButton(onClick = choose, modifier = buttonModifier) {
                        Text((position + 1).toString() + ". " + candidate.heading.value(language))
                    }
                }
            }
            OutlinedButton(
                onClick = { speak(stage.explanation) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r16-ciliary-narrate"),
            ) {
                Text(ciliaryText(language,
                    "Hear the current physiological stage",
                    "தற்போதைய உடலியல் நிலை விளக்கத்தைக் கேள்"))
            }
            ciliarySubsections.forEachIndexed { position, note ->
                Text(note.heading.value(language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.testTag("r16-ciliary-subheading-" + position))
                Text(note.explanation.value(language),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("r16-ciliary-subtext-" + position))
            }
            Text(process.teachingCaution.value(language),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("r16-ciliary-caution"))
            Text(ciliaryText(language, "Scientific reading: ", "அறிவியல் ஆதாரம்: ") +
                    process.scientificSource,
                style = MaterialTheme.typography.bodySmall)
        }
    }
}
