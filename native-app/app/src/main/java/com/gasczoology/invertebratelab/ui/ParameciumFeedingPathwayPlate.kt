package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
                    // Original graphite hatching: no microscopic coordinates implied.
                    for (hatch in -2..2) {
                        val start = Offset(center.x - radius * .32f + hatch * 4f,
                            center.y + radius * .25f)
                        val end = Offset(start.x + radius * .31f,
                            start.y - radius * .32f)
                        drawLine(if (active) PencilAtlasPalette.selected
                            else PencilAtlasPalette.mid, start, end, 1.2f)
                    }
                    drawCircle(if (active) PencilAtlasPalette.selected
                        else PencilAtlasPalette.graphite,
                        radius = if (active) 6.5f else 4f, center = center)
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
