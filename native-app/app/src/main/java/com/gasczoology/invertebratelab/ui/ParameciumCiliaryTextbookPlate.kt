package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.heading
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
import com.gasczoology.invertebratelab.data.ParameciumCiliaryAcademicContent
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

@Composable
internal fun ParameciumCiliaryTextbookPlate(
    language: AppLanguage,
    speak: (BilingualText) -> Unit,
    selectedStage: Int,
    onStageSelected: (Int) -> Unit,
) {
    val process = remember {
        ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION)
    }
    val stageIndex = selectedStage.coerceIn(0, process.stages.lastIndex)
    val stage = process.stages[stageIndex]
    var selectedPart by rememberSaveable { mutableStateOf("cilia") }
    val parts = listOf(
        "cilia" to BilingualText("Cilia", "குறுஇழைகள்"),
        "basal" to BilingualText("Basal bodies · 9 triplets", "அடித்தள உடல்கள் · 9 மும்மைகள்"),
        "axoneme" to BilingualText("Axoneme · 9 + 2", "ஆக்சோனீம் · 9 + 2"),
    )
    val requester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    // A stadium outline clips multiline Tamil at 200% font scale when the
    // button becomes tall. Fixed-radius corners keep the complete text visible.
    val controlShape = RoundedCornerShape(12.dp)

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
                modifier = Modifier.testTag("r16-ciliary-title").semantics { heading() },
            )
            Text(
                ciliaryText(language,
                    "Side-view projection and enlarged basal-body / axoneme cross-sections. Teaching schematics at different scales; three-dimensional strokes are simplified.",
                    "செல் புறப்பகுதியின் பக்கவாட்டுத் தோற்றமும் பெரிதாக்கிய அடித்தள உடல் / ஆக்சோனீம் குறுக்குவெட்டுகளும். வெவ்வேறு அளவுகளில் உள்ள கற்பித்தல் வரைபடங்கள்; முப்பரிமாண அசைவுகள் எளிமைப்படுத்தப்பட்டுள்ளன."),
                style = MaterialTheme.typography.bodyMedium,
            )
            Button(
                onClick = { scope.launch { requester.bringIntoView() } },
                shape = controlShape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r16-view-ciliary-plate"),
            ) {
                Text(ciliaryText(language,
                    "Bring the PNG anatomy reference fully into view",
                    "PNG உடலமைப்பு ஆதாரப் படத்தை திரையில் காண்க"))
            }
            // R8 PNG-only: preserve selectable physiology stages and organ
            // explanations but never draw a synthetic 9+2 ultrastructure.
            ParameciumPngReference(
                panel = "external",
                language = language,
                modifier = Modifier.bringIntoViewRequester(requester),
                contextOnly = true,
            )
            Text(
                ciliaryText(language,
                    "Ciliary stage explanations are given below. The reference PNG does not claim to resolve basal-body triplets or axoneme 9+2 ultrastructure.",
                    "குறுஇழைச் செயல்நிலைகளின் விளக்கம் கீழே உள்ளது. இந்த PNG படத்தில் அடித்தள உடல் மும்மைகளோ ஆக்சோனீம் 9+2 நுண்ணமைப்போ உறுதிப்படுத்தப்படவில்லை."),
                modifier = Modifier.testTag("r16-ciliary-legend"),
                style = MaterialTheme.typography.bodySmall,
            )
            parts.forEach { (id, label) ->
                OutlinedButton(
                    onClick = { selectedPart = id },
                    shape = controlShape,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("r161-structure-" + id).semantics { selected = selectedPart == id },
                ) { Text(label.value(language)) }
            }
            Text(
                ciliaryText(language,
                    "Stage " + (stageIndex + 1) + "/" + process.stages.size + " — ",
                    "நிலை " + (stageIndex + 1) + "/" + process.stages.size + " — ") +
                    stage.heading.value(language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("r16-ciliary-stage-title").semantics { heading() },
            )
            Text(stage.explanation.value(language),
                modifier = Modifier.testTag("r16-ciliary-stage-explanation"),
                style = MaterialTheme.typography.bodyLarge)
            for ((position, candidate) in process.stages.withIndex()) {
                val choose = { onStageSelected(position) }
                val buttonModifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r16-ciliary-stage-" + candidate.id)
                    .semantics { selected = position == stageIndex }
                if (position == stageIndex) {
                    Button(onClick = choose, modifier = buttonModifier,
                        shape = controlShape) {
                        Text((position + 1).toString() + ". " + candidate.heading.value(language))
                    }
                } else {
                    OutlinedButton(onClick = choose, modifier = buttonModifier,
                        shape = controlShape) {
                        Text((position + 1).toString() + ". " + candidate.heading.value(language))
                    }
                }
            }
            OutlinedButton(
                onClick = { speak(stage.explanation) },
                shape = controlShape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("r16-ciliary-narrate"),
            ) {
                Text(ciliaryText(language,
                    "Hear the current physiological stage",
                    "தற்போதைய உடலியல் நிலை விளக்கத்தைக் கேள்"))
            }
            ParameciumCiliaryAcademicContent.subsections.forEachIndexed { position, note ->
                Text(note.heading.value(language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.testTag("r16-ciliary-subheading-" + position).semantics { heading() })
                Text(note.explanation.value(language),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("r16-ciliary-subtext-" + position))
            }
            Text(process.teachingCaution.value(language),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("r16-ciliary-caution"))
            Text(ciliaryText(language, "Scientific reading", "அறிவியல் ஆதாரங்கள்"),
                style = MaterialTheme.typography.titleMedium)
            ParameciumCiliaryAcademicContent.sources.forEach {
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
