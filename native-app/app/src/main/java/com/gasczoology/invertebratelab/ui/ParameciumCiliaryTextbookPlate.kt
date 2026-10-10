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
                    "Bring the pencil diagram fully into view",
                    "பென்சில் வரைபடத்தை முழுவதும் திரையில் காண்க"))
            }
            Canvas(
                Modifier.fillMaxWidth().height(220.dp)
                    .bringIntoViewRequester(requester)
                    .testTag("r16-ciliary-canvas")
                    .pointerInput(stage.id) {
                        detectTapGestures { point ->
                            selectedPart = when {
                                point.y < size.height * .42f -> if (point.x < size.width * .5f) "basal" else "axoneme"
                                point.y > size.height * .73f -> "basal"
                                else -> "cilia"
                            }
                        }
                    }
                    .semantics {
                        contentDescription = ciliaryText(language,
                            "Schematic: left inset nine basal-body triplets without a central pair; right inset nine axonemal doublets and two central singlets. Basal bodies are beneath the surface. Selected stage: " + stage.heading.english,
                            "விளக்கப்படம்: இடது சிறுபடத்தில் மைய இணை இல்லாத ஒன்பது அடித்தள உடல் மும்மைகள்; வலதில் ஒன்பது ஆக்சோனீம் இரட்டைகளும் இரண்டு மைய ஒற்றைகளும். அடித்தள உடல்கள் மேற்பரப்புக்குக் கீழே உள்ளன. தேர்ந்தெடுத்த நிலை: " + stage.heading.tamil)
                        stateDescription = ciliaryText(language, "Highlighted: ", "சிறப்பித்துக் காட்டுவது: ") +
                            parts.first { it.first == selectedPart }.second.value(language)
                        customActions = parts.map { (id, label) ->
                            CustomAccessibilityAction(label.value(language)) { selectedPart = id; true }
                        }
                    }
            ) {
                drawCiliaryPencilDiagram(stage.id, selectedPart)
            }

            Text(
                ciliaryText(language,
                    "Left inset: basal body, 9 triplets, no central pair · right: shaft axoneme, 9 doublets + 2 singlets · below: surface, basal bodies and rootlets. Protein details are simplified.",
                    "இடது சிறுபடம்: அடித்தள உடல், 9 மும்மைகள், மைய இணை இல்லை · வலது: தண்டு ஆக்சோனீம், 9 இரட்டைகள் + 2 ஒற்றைகள் · கீழே: மேற்பரப்பு, அடித்தள உடல்கள், வேரிழைகள். புரத விவரங்கள் எளிமைப்படுத்தப்பட்டுள்ளன."),
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
