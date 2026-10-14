package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*

/**
 * U4.0: genuine native Kotlin/Compose type-study textbook, not question cards.
 * Required earthworm raster plates stay visibly pending until anatomically reviewed.
 * TTS is requested offline; audible pronunciation needs physical-device QA.
 */
@Composable
internal fun UnitIVEarthwormReader(language: AppLanguage, onBack: () -> Unit) {
    val lesson = UnitIVEarthwormLessons.lesson
    val tamil = language == AppLanguage.TAMIL
    val context = LocalContext.current
    val narrator = remember(context) { OfflineNarrator(context) }
    val voicePhase by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding().navigationBarsPadding()
            .testTag("u4-earthworm-reader"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(key = "u4-earthworm-heading") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.heightIn(min = 48.dp).testTag("u4-earthworm-back")
                ) {
                    Text(if (tamil) "அலகு IV-க்குத் திரும்பு" else "Back to Unit IV")
                }
                Text(if (tamil) "அலகு IV · அன்னெலிடா / மண்புழு" else
                    "UNIT IV · ANNELIDA / EARTHWORM", style = MaterialTheme.typography.labelLarge)
                Text(
                    lesson.title.value(language),
                    modifier = Modifier.testTag("u4-earthworm-title").semantics { heading() },
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    if (tamil)
                        "உள்ளடக்க நிலை: அறிவியல் மற்றும் தமிழ் மதிப்பாய்வு நிலுவையில். Metaphire posthuma என்ற இனத்திற்குரிய தகவல்களும் பொதுவான வளையப்புழு தகவல்களும் வேறுபடுத்தப்பட்டுள்ளன."
                    else
                        "ACADEMIC DRAFT · independent zoological and Tamil review pending. Species-specific facts for Metaphire posthuma are distinguished from general annelid biology.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u4-earthworm-review-hold")
                )
                Text(if (tamil)
                    "படிவுறுப்பியல், உடலியல், ஆறு உடற்கூறுப் பட இலக்குகள் மற்றும் செய்முறைத் தகவல்கள். வினா–விடைத் தொகுப்பு மாற்றப்படவில்லை."
                else "Full teaching chapters, physiology, six required atlas slots and practical reasoning. Existing assessment questions remain unchanged.")
            }
        }
        item(key = "u4-earthworm-plate-heading") {
            Text(if (tamil) "ஆறு தேவைப்படும் உடற்கூறுப் படங்கள்" else
                "SIX REQUIRED ANATOMICAL PLATES",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics { heading() })
        }
        items(UnitIVEarthwormLessons.requiredPlates, key = { "earthworm-plate-" + it.first }) { plate ->
            Card(modifier = Modifier.fillMaxWidth()
                .testTag("u4-earthworm-plate-" + plate.first)) {
                Column(modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(plate.second.value(language),
                        style = MaterialTheme.typography.titleMedium)
                    val raster = rememberRequiredRasterBitmap("type_studies/earthworm/" + plate.first)
                    if (raster == null) {
                        Text(
                            if (tamil) "இனத்திற்குரிய சரிபார்க்கப்பட்ட பென்சில் படம் இன்னும் இணைக்கப்படவில்லை."
                            else "Species-verified pencil raster is pending; no invented drawing is displayed.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("u4-earthworm-plate-pending-" + plate.first)
                        )
                    } else {
                        Image(
                            bitmap = raster, contentDescription = plate.second.value(language),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth().testTag("u4-earthworm-raster-" + plate.first)
                        )
                        Text(if (tamil) "படம் காணப்படுகிறது; ஆனால் உடற்கூறு ஆய்வு இன்னும் நிலுவையில் உள்ளது."
                            else "Raster present, but anatomical leader/hotspot review is still pending.",
                            color = MaterialTheme.colorScheme.error)
                    }
                    Text(plate.third.value(language),
                        style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item(key = "u4-earthworm-textbook-heading") {
            Text(
                if (tamil) "விரிவான பாடநூல் · 15 துணைத்தலைப்புகள்"
                else "DETAILED TYPE STUDY · 15 textbook sections",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("u4-earthworm-section-heading").semantics { heading() }
            )
        }
        items(lesson.sections, key = { "earthworm-section-" + it.id }) { section ->
            Card(modifier = Modifier.fillMaxWidth().testTag("u4-earthworm-section-" + section.id)) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(section.heading.value(language), style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.semantics { heading() })
                    section.paragraphs.forEach { paragraph ->
                        Text(paragraph.value(language), style = MaterialTheme.typography.bodyLarge)
                    }
                    OutlinedButton(
                        onClick = {
                            narrator.speak(BilingualText(
                                section.paragraphs.joinToString(" ") { it.english },
                                section.paragraphs.joinToString(" ") { it.tamil }
                            ), language)
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u4-earthworm-narrate-" + section.id)
                    ) {
                        Text(if (tamil) "இந்தப் பாடப்பகுதியைக் கேள்" else "Listen to this section")
                    }
                    Text(if (tamil) "குரல் நிலை: " + voicePhase.name
                        else "Voice state: " + voicePhase.name,
                        style = MaterialTheme.typography.bodySmall)
                    Text((if (tamil) "மூலங்கள்: " else "Sources: ") +
                        section.scientificSources.joinToString(" · "),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("u4-earthworm-sources-" + section.id))
                }
            }
        }
        item(key = "u4-earthworm-end") {
            Text(if (tamil)
                "அடுத்த கட்டம்: இனத்திற்குரிய ஆறு உடற்கூறுப் படங்கள், உறுப்பு தொடுபுள்ளிகள், இயக்க விளக்கங்கள், தனித்த சோதனைகள்."
            else "Next gate: six species-specific reviewed pencil plates, anchored hotspots, physiological interactions and physical-device verification.",
                modifier = Modifier.testTag("u4-earthworm-next-gate"))
        }
    }
}
