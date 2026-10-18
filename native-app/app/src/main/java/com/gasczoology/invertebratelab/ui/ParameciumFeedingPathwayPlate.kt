package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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

/**
 * R1.5: four-stage native graphite mechanism strip for undergraduate reading.
 * This is a FUNCTIONAL FLOW, not a micrographic map of cell/organ coordinates.
 * The exact, pre-existing bilingual FEEDING stages are the sole text source.
 * Academic, Tamil and device approvals remain outstanding.
 */
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
                    "தனி உணவூட்ட PNG படம் · நான்கு நிலைகள் · அறிவியல் ஆய்வு நிலுவையில்"
                else "Individual nutrition PNG · four stages · scientific review pending",
                style = MaterialTheme.typography.bodySmall,
            )
            val nutritionPng = rememberRequiredRasterBitmap(
                "unit1/concepts/paramecium_nutrition", pngOnly = true)
            if (nutritionPng != null) {
                androidx.compose.foundation.Image(
                    bitmap = nutritionPng,
                    contentDescription = if (language == AppLanguage.TAMIL)
                        "பாரமீசியம் உணவு உட்கொள்ளுதல், உணவுக் குமிழ், கழிவு வெளியேற்றம் ஆகியவற்றின் PNG படம்"
                    else "Paramecium nutrition: ingestion, food vacuole and egestion PNG",
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                        .bringIntoViewRequester(diagramRequester)
                        .testTag("r15-feeding-pathway-png"))
            } else {
                Text(if (language == AppLanguage.TAMIL)
                    "உணவூட்ட செயல்முறைக்கான தனி PNG படம் இன்னும் சரிபார்க்கப்படவில்லை; மாற்று வரைபடம் இல்லை."
                    else "Dedicated nutrition PNG awaits verification; no substitute drawing.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("r15-feeding-png-pending"))
            }
            Text(if (language == AppLanguage.TAMIL)
                "கீழுள்ள நிலைப் பொத்தான்களைத் தேர்ந்தெடுத்து ஒவ்வொரு படிநிலையின் ஒலிவிளக்கத்தையும் கேளுங்கள்."
                else "Select the stage buttons below to read and hear each process step.",
                style = MaterialTheme.typography.bodyMedium)
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
                    "உணவூட்ட PNG படத்தை மீண்டும் பார்"
                    else "View nutrition PNG")
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
