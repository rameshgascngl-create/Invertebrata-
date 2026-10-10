package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlin.math.hypot
import kotlin.math.min

/**
 * R1.5: four-stage native graphite mechanism strip for undergraduate reading.
 * This is a FUNCTIONAL FLOW, not a micrographic map of cell/organ coordinates.
 * The exact, pre-existing bilingual FEEDING stages are the sole text source.
 * Academic, Tamil and device approvals remain outstanding.
 */
private val feedingAnchors = listOf(
    Offset(.18f, .27f), Offset(.78f, .27f),
    Offset(.78f, .75f), Offset(.18f, .75f),
)

private fun DrawScope.feedingArrow(from: Offset, to: Offset, radius: Float) {
    val delta = to - from
    val length = hypot(delta.x, delta.y)
    if (length <= 1f) return
    val forward = delta * (1f / length)
    val normal = Offset(-forward.y, forward.x)
    val start = from + forward * (radius + 4f)
    val end = to - forward * (radius + 8f)
    drawLine(PencilAtlasPalette.graphite, start, end, 2.6f)
    drawLine(PencilAtlasPalette.mid, start + normal * 3f, end + normal * 3f, 1f)
    val base = end - forward * 13f
    drawLine(PencilAtlasPalette.graphite, end, base + normal * 6f, 2.6f)
    drawLine(PencilAtlasPalette.graphite, end, base - normal * 6f, 2.6f)
}

/**
 * Each numbered circle carries an ORIGINAL mechanism-specific graphite inset.
 * (1) oral cilia/current, (2) vestibule-to-cytopharynx passage,
 * (3) intracellular food vacuole, (4) cytoproct egestion.
 * This intentionally does not position these structures on a whole-cell map.
 */
private fun DrawScope.feedingPictogram(index: Int, center: Offset, r: Float) {
    val g = PencilAtlasPalette.graphite
    val mid = PencilAtlasPalette.mid
    fun at(x: Float, y: Float) = center + Offset(r * x, r * y)
    when (index) {
        0 -> {
            // Ciliary power strokes in water with suspended food particles.
            drawLine(mid, at(-.68f, .50f), at(.66f, .50f), 2.4f)
            for (i in 0..4) {
                val x = -.54f + i * .25f
                val b = at(x, .47f)
                val c1 = at(x - .05f, .10f)
                val c2 = at(x + .12f, -.26f)
                val tip = at(x + .27f, -.36f)
                drawPath(Path().apply {
                    moveTo(b.x, b.y)
                    cubicTo(c1.x, c1.y, c2.x, c2.y, tip.x, tip.y)
                }, g, style = Stroke(2.5f))
            }
            listOf(-.54f to -.59f, -.22f to -.65f, .03f to -.53f).forEach { (x,y) ->
                drawCircle(g, radius = r * .065f, center = at(x, y))
            }
        }
        1 -> {
            // Funnel diagram: vestibule narrows into the cytopharyngeal region.
            val left = listOf(at(-.67f,-.60f), at(-.45f,-.26f), at(-.23f,.18f),
                at(-.20f,.62f))
            val right = listOf(at(.67f,-.60f), at(.45f,-.26f), at(.23f,.18f),
                at(.20f,.62f))
            for (i in 0..2) {
                drawLine(g, left[i], left[i+1], 3f)
                drawLine(g, right[i], right[i+1], 3f)
            }
            drawCircle(g, radius = r*.075f, center = at(.01f,-.35f))
            drawCircle(mid, radius = r*.065f, center = at(.06f,.04f))
            feedingArrow(at(.04f,.15f), at(.04f,.60f), 0f)
        }
        2 -> {
            // Membrane-bound digestive vesicle: granular, not radiating arms.
            drawCircle(PencilAtlasPalette.cell, radius = r*.62f, center = center)
            drawCircle(g, radius = r*.62f, center = center, style = Stroke(3f))
            drawCircle(mid, radius = r*.53f, center = center, style = Stroke(1.4f))
            listOf(-.31f to -.13f, .22f to -.20f, -.05f to .28f, .30f to .24f)
                .forEachIndexed { i, (x,y) ->
                    drawCircle(if (i % 2 == 0) g else mid,
                        radius = r * (if (i == 0) .13f else .09f),
                        center = at(x,y))
                }
            for (i in 0..4) {
                val x = -.30f + i * .14f
                drawLine(mid, at(x,-.49f), at(x+.11f,-.38f), 1.4f)
            }
        }
        3 -> {
            // Localised cortical egestion opening, not a CV discharge pore.
            drawLine(g, at(.12f,-.68f), at(.12f,-.19f), 3.5f)
            drawLine(g, at(.12f,.19f), at(.12f,.68f), 3.5f)
            drawLine(mid, at(.20f,-.68f), at(.20f,-.19f), 1.2f)
            drawLine(mid, at(.20f,.19f), at(.20f,.68f), 1.2f)
            drawCircle(g, radius = r*.13f, center = at(-.51f,-.06f))
            drawCircle(g, radius = r*.08f, center = at(-.31f,.15f))
            feedingArrow(at(-.21f,0f), at(.72f,0f), 0f)
            drawCircle(mid, radius = r*.08f, center = at(.60f,-.31f))
        }
    }
}

@Composable
internal fun ParameciumFeedingPathwayPlate(
    language: AppLanguage,
    speak: (BilingualText) -> Unit,
) {
    val model = ParameciumLearningEngine.simulation(ParameciumProcess.FEEDING)
    // The four source stages must remain in order; never replace them with
    // separately authored shortened exam-answer text.
    require(model.stages.map { it.id } ==
        listOf("current", "cytostome", "vacuole", "egestion"))
    var selected by rememberSaveable { mutableIntStateOf(0) }
    // Real reader navigation: selected-stage text may be many screens below
    // the diagram at Android's 200% Tamil font scale.
    val diagramRequester = remember { BringIntoViewRequester() }
    val diagramScope = rememberCoroutineScope()
    val stage = model.stages[selected]
    fun select(index: Int) {
        selected = index
        speak(model.stages[index].explanation)
    }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("r15-feeding-pathway"),
        colors = CardDefaults.cardColors(containerColor = PencilAtlasPalette.paper),
    ) {
        Column(Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                if (language == AppLanguage.TAMIL)
                    "உணவு நகரும் வழி: தொடக்கத்திலிருந்து கழிவு வெளியேற்றம் வரை"
                else "Follow a food particle: entry to egestion",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("r15-feeding-title"),
            )
            Text(
                if (language == AppLanguage.TAMIL)
                    "அசல் பென்சில் வழித்தடப் படம் · நான்கு செயல்நிலைகள் · அமைவிட வரைபடம் அல்ல"
                else "Original graphite process plate · four stages · NOT an organ-position map",
                style = MaterialTheme.typography.bodySmall,
            )
            Canvas(
                modifier = Modifier.fillMaxWidth().height(220.dp)
                    .bringIntoViewRequester(diagramRequester)
                    .testTag("r15-feeding-pathway-canvas")
                    .semantics {
                        contentDescription = if (language == AppLanguage.TAMIL)
                            "நான்கு உணவூட்ட நிலைகளைக் காட்டும் விளக்கப் படம்; கீழுள்ள பொத்தான்களாலும் நிலையைத் தேர்ந்தெடுக்கலாம்"
                        else "Four-step feeding schematic. Choose any stage with the accessible buttons below."
                    }
                    .pointerInput(language) {
                        detectTapGestures { tap ->
                            val centers = feedingAnchors.map {
                                Offset(it.x * size.width, it.y * size.height)
                            }
                            val nearest = centers.indices.minByOrNull { i ->
                                val dx = tap.x - centers[i].x
                                val dy = tap.y - centers[i].y
                                dx * dx + dy * dy
                            } ?: return@detectTapGestures
                            val dx = tap.x - centers[nearest].x
                            val dy = tap.y - centers[nearest].y
                            val range = min(size.width * .16f, size.height * .18f)
                            if (dx * dx + dy * dy <= range * range) select(nearest)
                        }
                    },
            ) {
                drawRect(PencilAtlasPalette.paper)
                val centers = feedingAnchors.map {
                    Offset(it.x * size.width, it.y * size.height)
                }
                val radius = min(size.width * .085f, size.height * .14f)
                for (index in 0 until centers.lastIndex) {
                    feedingArrow(centers[index], centers[index + 1], radius)
                }
                centers.forEachIndexed { index, center ->
                    val active = index == selected
                    val outline = if (active) PencilAtlasPalette.selected
                        else PencilAtlasPalette.graphite
                    drawCircle(PencilAtlasPalette.cell, radius = radius, center = center)
                    drawCircle(outline, radius = radius, center = center,
                        style = Stroke(width = if (active) 4.2f else 2.5f))
                    drawCircle(PencilAtlasPalette.mid, radius = radius - 5f,
                        center = center, style = Stroke(width = 1.1f))
                    // Four different biological processes, not four empty markers.
                    feedingPictogram(index, center, radius)
                }
            }
            Text(
                if (language == AppLanguage.TAMIL)
                    "ஒவ்வொரு வட்டத்தையோ கீழே உள்ள நிலைப் பொத்தானையோ தொட்டு படிநிலையை ஒளிரச்செய்து ஒலிவிளக்கத்தைக் கேளுங்கள்."
                else "Tap a graphite circle or its stage button to highlight the pathway and hear the corresponding explanation.",
                style = MaterialTheme.typography.bodyMedium,
            )
            model.stages.forEachIndexed { index, item ->
                val modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r15-feeding-stage-" + item.id)
                if (index == selected) {
                    Button(onClick = { select(index) }, modifier = modifier) {
                        Text("${index + 1}. ${item.heading.value(language)}")
                    }
                } else {
                    OutlinedButton(onClick = { select(index) }, modifier = modifier) {
                        Text("${index + 1}. ${item.heading.value(language)}")
                    }
                }
            }
            Text(
                (if (language == AppLanguage.TAMIL) "நிலை " else "Stage ") +
                    "${selected + 1}/${model.stages.size} — " + stage.heading.value(language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.testTag("r15-feeding-active-title"),
            )
            Text(stage.explanation.value(language),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("r15-feeding-explanation"))
            // Learner-facing recovery control, not a test-only scroll shortcut.
            // At 200% Tamil system font the four stage buttons and explanation
            // can place the Canvas entirely off-screen.
            Button(
                onClick = { diagramScope.launch { diagramRequester.bringIntoView() } },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r15-view-feeding-diagram"),
            ) {
                Text(if (language == AppLanguage.TAMIL)
                    "நான்கு நிலைகளின் பென்சில் வரைபடத்தை மீண்டும் பார்"
                    else "View the four-stage pencil diagram")
            }
            OutlinedButton(onClick = { speak(stage.explanation) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r15-feeding-replay")) {
                Text(if (language == AppLanguage.TAMIL) "இந்நிலையின் விளக்கத்தைக் கேள்"
                    else "Hear this stage again")
            }
            Text(
                if (language == AppLanguage.TAMIL)
                    "சான்றியல் வரம்பு: இது கற்பித்தலுக்கான செயல்முறை வரிசை மட்டுமே; துகளின் உண்மையான பாதை, உறுப்புகளின் இடம், கால அளவுகள் அளவிடப்படவில்லை. செல் கழிவுத்துளை என்பது சுருங்கும் நுண்குமிழின் நீர் வெளியேற்றத் துளை அல்ல."
                else "Evidence limit: this is a teaching sequence, not a measured particle trajectory, organ map or timed record. Cytoproct egestion is distinct from contractile-vacuole water discharge.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("r15-feeding-caution"),
            )
        }
    }
}
