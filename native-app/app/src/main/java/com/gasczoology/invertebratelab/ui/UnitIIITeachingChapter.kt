package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.delay

private fun u3t(language: AppLanguage, english: String, tamil: String): String =
    if (language == AppLanguage.TAMIL) tamil else english

/**
 * Full Unit III native Kotlin teaching reader. Editorial draft; source-linked.
 * Teaching is BEFORE examination practice. Bitmap lessons and the interactive
 * step-by-step physiological/life-cycle experience are separate views.
 * No programmatic fake pencil drawings or SVG fallback are allowed.
 */
@Composable
internal fun UnitIIITeachingChapter(
    chapter: Chapter,
    language: AppLanguage,
    onBack: () -> Unit,
    revealedQuestionId: String,
    onToggle: (A5Question) -> Unit,
) {
    val lesson = requireNotNull(UnitIIILessons.forChapter(chapter.id))
    val book = BookChapters.forLesson(chapter.id)
    val context = LocalContext.current
    val narrator = remember(context, chapter.id) { OfflineNarrator(context) }
    val voiceStatus by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }
    val stages = when (chapter.id) {
        "u3-fasciola" -> UnitIIILessons.fasciolaLifeCycle
        "u3-ascaris" -> UnitIIILessons.ascarisLifeCycle
        else -> emptyList()
    }
    var selected by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    var playing by rememberSaveable(chapter.id) { mutableStateOf(false) }
    LaunchedEffect(chapter.id, playing) {
        if (playing && stages.isNotEmpty()) {
            while (playing && selected < stages.lastIndex) {
                delay(1600) // Teaching cadence, NEVER parasite development kinetics.
                selected += 1
            }
            playing = false
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
            .testTag("u3-reader-" + chapter.id),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(key = "u3-heading") {
            Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                OutlinedButton(onClick = onBack,
                    modifier = Modifier.heightIn(min = 48.dp).testTag("u3-back")) {
                    Text(u3t(language, "Back to Unit III", "அலகு III-க்குத் திரும்பு"))
                }
                Text(u3t(language,
                    "UNIT III · PLATYHELMINTHES AND NEMATODA",
                    "அலகு III · தட்டைப்புழுக்கள் மற்றும் நெமடோடா"),
                    style = MaterialTheme.typography.labelLarge)
                Text(lesson.title.value(language),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.testTag("u3-lesson-title").semantics { heading() })
                Text(u3t(language,
                    "TEXTBOOK DRAFT — anatomy images, provenance and Tamil expert review pending.",
                    "பாடநூல் வரைவு — உடற்கூறுப் படங்கள், மூல உரிமை, தமிழ் நிபுணர் ஒப்புதல் நிலுவையில் உள்ளது."),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u3-draft-warning"))
            }
        }
        // R7: exactly one inline raster per unique anatomical plate.
        // UnitIIIPlateGallery below is the only student-facing anatomical
        // gallery. The duplicate RequiredTypeStudyPlates and historical source
        // image section were removed. Archived source files are untouched.
        if (book == null) item(key = "u3-one-image-per-plate-disclosure") {
            Text(u3t(language,
                "One image is displayed for each of the three distinct source-backed anatomical plates. Full-screen zoom is available from its plate; scientific and Tamil reviews remain pending.",
                "மூன்று வேறுபட்ட ஆதார உடற்கூறுப் படங்களும் தலா ஒருமுறை மட்டுமே காட்டப்படுகின்றன. ஒவ்வொரு படத்தையும் முழுத்திரையில் பெரிதாக்கலாம்; அறிவியல் மற்றும் தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது."),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u3-no-duplicate-images"))
        }
        // A book chapter shows each figure once, inline, so the gallery is skipped.
        if (stages.isNotEmpty() && book == null) item(key = "u3-required-plate-gallery") {
            UnitIIIPlateGallery(chapter.id, language, narrator)
        }
        if (stages.isNotEmpty()) item(key = "u3-interactive-life-cycle") {
            Column(
                modifier = Modifier.fillMaxWidth().testTag("u3-interactive-life-cycle"),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(u3t(language,
                    "INTERACTIVE LIFE CYCLE · separate from the historical anatomical plate",
                    "தொடுதிறன் வாழ்க்கைச் சுழற்சி · வரலாற்று உடற்கூறுப் படத்திலிருந்து தனி"),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() })
                Text(u3t(language,
                    "Stages are ordered by established parasite biology. Playback intervals are for teaching, not biological time.",
                    "பருவங்கள் சரிபார்க்கப்பட்ட ஒட்டுண்ணி உயிரியலின் வரிசையில் உள்ளன. இயக்க இடைவெளிகள் கற்பித்தலுக்கானவை; உண்மையான வளர்ச்சிக் காலமல்ல."))
                Card {
                    Column(Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stages[selected.coerceIn(stages.indices)].name.value(language),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.testTag("u3-current-life-stage"))
                        Text(stages[selected.coerceIn(stages.indices)].explanation.value(language),
                            modifier = Modifier.testTag("u3-life-stage-explanation"))
                        LinearProgressIndicator(
                            progress = { (selected+1).toFloat()/stages.size },
                            modifier = Modifier.fillMaxWidth())
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { selected=0; playing=false },
                                modifier = Modifier.weight(1f).heightIn(min=48.dp)
                                    .testTag("u3-restart")) {
                                Text(u3t(language, "Restart", "மீண்டும்"))
                            }
                            Button(onClick = {
                                if (playing) playing=false else {
                                    if (selected == stages.lastIndex) selected=0
                                    playing=true
                                }
                            }, modifier = Modifier.weight(1f).heightIn(min=48.dp)
                                .testTag("u3-play-pause")) {
                                Text(u3t(language,
                                    if(playing)"Pause" else "Play stages",
                                    if(playing)"நிறுத்து" else "பருவங்களை இயக்கு"))
                            }
                        }
                        Button(onClick = {
                            narrator.speak(stages[selected.coerceIn(stages.indices)]
                                .explanation, language)
                        }, modifier = Modifier.fillMaxWidth().heightIn(min=48.dp)
                            .testTag("u3-speak-current-stage")) {
                            Text(u3t(language, "Hear current stage (offline voice)",
                                "இப்பருவ விளக்கத்தைக் கேள் (ஆஃப்லைன் குரல்)"))
                        }
                        Text(u3t(language, "Voice state: ", "குரல் நிலை: ") +
                            voiceStatus.name)
                    }
                }
                stages.forEachIndexed { index, stage ->
                    OutlinedButton(onClick = { selected=index; playing=false;
                            narrator.speak(stage.explanation,language) },
                        modifier = Modifier.fillMaxWidth().heightIn(min=48.dp)
                            .testTag("u3-stage-"+stage.id+"-"+index)) {
                        Text(stage.name.value(language))
                    }
                }
            }
        }
        if (book != null) { bookChapterItems(book, language) { narrator.speak(it, language) } } else
        items(lesson.sections, key = { it.id }) { section ->
            Card(modifier = Modifier.fillMaxWidth().testTag("u3-section-"+section.id)) {
                Column(Modifier.padding(16.dp),
                    verticalArrangement=Arrangement.spacedBy(10.dp)) {
                    Text(section.heading.value(language),
                        style=MaterialTheme.typography.titleLarge,
                        modifier=Modifier.semantics { heading() })
                    section.paragraphs.forEach {
                        Text(it.value(language),
                            style=MaterialTheme.typography.bodyLarge)
                    }
                    OutlinedButton(onClick = {
                        narrator.speak(BilingualText(
                            section.paragraphs.joinToString(" ") { it.english },
                            section.paragraphs.joinToString(" ") { it.tamil }
                        ), language)
                    }, modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)) {
                        Text(u3t(language, "Listen to this section",
                            "இந்தப் பாடப்பகுதியைக் கேள்"))
                    }
                    Text(u3t(language, "Sources: ", "ஆதாரங்கள்: ") +
                        section.scientificSources.joinToString(" · "),
                        style=MaterialTheme.typography.bodySmall)
                }
            }
        }
        item(key="u3-practice") {
            Text(u3t(language,
                "EXAM PRACTICE — AFTER THE COMPLETE LESSON",
                "தேர்வுப் பயிற்சி — முழுப் பாடத்திற்குப் பிறகு"),
                modifier=Modifier.testTag("u3-practice-heading").semantics { heading() },
                style=MaterialTheme.typography.titleLarge)
            Text(u3t(language,
                "The validated A5 question/answer corpus is retained unchanged.",
                "சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினா–விடைத் தொகுப்பு மாற்றமின்றி உள்ளது."))
        }
        items(chapter.a5Questions, key = { it.id }) { question ->
            Card(modifier=Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp),
                    verticalArrangement=Arrangement.spacedBy(9.dp)) {
                    Text(question.question.value(language),
                        modifier=Modifier.testTag("u3-question-"+question.id))
                    OutlinedButton(onClick={ onToggle(question) },
                        modifier=Modifier.heightIn(min=48.dp)
                            .testTag("u3-answer-toggle-"+question.id)) {
                        Text(u3t(language,
                            if(revealedQuestionId==question.id) "Hide answer" else "Reveal answer",
                            if(revealedQuestionId==question.id) "விடையை மறை" else "விடையைப் பார்"))
                    }
                    if (revealedQuestionId==question.id)
                        question.answerPoints.forEachIndexed { i, item ->
                            Text("${i+1}. " + item.value(language))
                        }
                }
            }
        }
    }
}
