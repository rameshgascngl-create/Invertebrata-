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
                    "ILLUSTRATED TEXTBOOK DRAFT — historical image anatomy, provenance and Tamil expert review pending.",
                    "படவிளக்கப் பாட வரைவு — வரலாற்றுப் படங்களின் உடற்கூறு, மூலம், தமிழ் நிபுணர் ஒப்புதல் நிலுவையில் உள்ளது."),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u3-draft-warning"))
            }
        }
        if (chapter.id == "u3-fasciola" || chapter.id == "u3-ascaris") {
            item(key = "r3-required-type-study-gallery") {
                val study = NativeCurriculumManifest.typeStudies.single { it.lessonId == chapter.id }
                RequiredTypeStudyPlates(study, language)
            }
        }
        if (stages.isNotEmpty()) item(key = "u3-static-figure") {
            Column(
                modifier = Modifier.fillMaxWidth().testTag("u3-lesson-illustration-section"),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(u3t(language, "LESSON ANATOMY · HISTORICAL SCANNED ILLUSTRATION",
                    "பாட உடற்கூறு · வரலாற்று அச்சுப்படத்தின் ஸ்கேன்"),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() })
                val stem = if (chapter.id == "u3-fasciola")
                    "unit3/fasciola_type" else "unit3/ascaris_type"
                val picture = rememberRequiredRasterBitmap(stem)
                if (picture == null) {
                    Text(u3t(language,
                        "Historical source raster is missing or unreadable. No schematic Canvas or SVG substitute will be shown.",
                        "முழுத் தெளிவுள்ள சரிபார்க்கப்பட்ட மூலப்படம் இன்னும் இணைக்கப்படவில்லை. மாற்றாக Canvas அல்லது SVG படம் காட்டப்படாது."),
                        modifier = Modifier.testTag("u3-pencil-master-pending"),
                        color = MaterialTheme.colorScheme.error)
                } else {
                    Image(bitmap = picture,
                        contentDescription = u3t(language,
                            "Historical zoology anatomy illustration for the lesson",
                            "பாடத்திற்கான வரலாற்று விலங்கியல் உடற்கூறு படம்"),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                            .testTag("u3-static-pencil-raster"))
                }
                Text(
                    if (chapter.id == "u3-fasciola")
                        u3t(language,
                            "Source: Encyclopaedia Britannica (1888), Trematoda Fig. 1. A–E show Fasciola organ systems and the egg. Each historical letter key requires source-based checking.",
                            "மூலம்: என்சைக்ளோபீடியா பிரிட்டானிக்கா (1888), டிரெமடோடா படம் 1. A–E பகுதிகள் ஃபாசியோலாவின் உறுப்பமைப்புகளையும் முட்டையையும் காட்டுகின்றன; எழுத்துக் குறியீடுகள் மூலநூலுடன் சரிபார்க்கப்பட வேண்டும்.")
                    else
                        u3t(language,
                            "Source: Paul Pfurtscheller, Zoologische Wandtafeln No. 38 (1925). This is a coloured historical anatomical wall chart, NOT a graphite-pencil scan. Anatomy review remains pending.",
                            "மூலம்: பால் ப்ஃபுர்ட்ஷெல்லர், விலங்கியல் சுவர் படங்கள் எண் 38 (1925). இது நிறமுள்ள வரலாற்று உடற்கூறுப் படம்; கிராஃபைட் பென்சில் வரைபட ஸ்கேன் அல்ல. உடற்கூறு மதிப்பாய்வு நிலுவையில் உள்ளது."),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.testTag("u3-source-artwork-disclosure")
                )
                Text(u3t(language,
                    "Image authorship, original resolution, anatomical leader lines and reuse rights must be verified separately.",
                    "படத்தின் ஆசிரியர், அசல் தெளிவு, உறுப்புக் குறியீடுகள், மறுபயன்பாட்டு உரிமைகள் ஆகியவை தனியாகச் சரிபார்க்கப்பட வேண்டும்."),
                    style = MaterialTheme.typography.bodySmall)
            }
        }
        if (stages.isNotEmpty()) item(key = "u3-required-plate-gallery") {
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
