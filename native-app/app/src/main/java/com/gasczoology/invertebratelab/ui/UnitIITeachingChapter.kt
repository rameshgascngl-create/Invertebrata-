package com.gasczoology.invertebratelab.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.delay

private val unitIIInk=Color(0xFF40434A)
private val currentBlue=Color(0xFF176B9A)
private val parchment=Color(0xFFFFFCF4)
private fun tr(lang: AppLanguage,en:String,ta:String)=if(lang==AppLanguage.TAMIL) ta else en

// Separate biological descriptions for the two touchable source-drawn
// Obelia stages. Both belong to lesson stage 6, but are NOT identical organs.
private fun obeliaSettlementFocus(focus: Int): BilingualText? = when (focus) {
    0 -> BilingualText(
        "E - Attached planula. The ciliated larva settles onto a firm substratum, attaches at one end, and starts metamorphosis towards a feeding polyp.",
        "E - ஒட்டிய பிளானுலா. சிலியாப் பிளானுலா உறுதியான அடித்தளத்தில் ஒட்டிக் கொண்டு உணவூட்டும் பாலிப்பாக உருமாறத் தொடங்குகிறது.")
    1 -> BilingualText(
        "F - Primary hydranth. After metamorphosis, a tentacle-bearing feeding polyp develops. A basal stolon, or hydrorhiza, extends and produces the new colony.",
        "F - முதன்மை ஹைட்ராந்த். உருமாற்றத்திற்குப் பின் உணவூட்டும் பாலிப்பில் உணர்நீட்சிகள் தோன்றுகின்றன. அடியில் ஹைட்ரோரிசா வளர்ந்து புதிய கூட்டை உருவாக்குகிறது.")
    else -> null
}

/**
 * Type-study teaching is deliberately BEFORE the independently validated A5 questions.
 * A chapter returns to the original native NavHost and persisted CHAPTER destination.
 * No browser runtime, external dependency, invented microscope scale or hardcoded Tamil truncation.
 */
@Composable
internal fun UnitIITeachingChapter(
    chapter:Chapter, language:AppLanguage,onBack:()->Unit,
    revealedQuestionId:String,onToggle:(A5Question)->Unit,
) {
    val lesson=requireNotNull(UnitIILessons.forChapter(chapter.id))
    val book=BookChapters.forLesson(chapter.id)
    val context=LocalContext.current
    val narrator=remember(context,chapter.id){OfflineNarrator(context)}
    val spoken by narrator.script.collectAsState()
    val voiceStatus by narrator.phase.collectAsState()
    DisposableEffect(narrator){onDispose{narrator.close()}}
    val listState=rememberLazyListState()
    val sycon=chapter.id=="u2-sycon" || chapter.id=="u2-canals"
    val obelia=chapter.id=="u2-obelia" || chapter.id=="u2-polymorphism"
    val stages=when {
        sycon->UnitIILessons.syconStages
        obelia->UnitIILessons.obeliaStages
        else->emptyList()
    }
    var index by rememberSaveable(chapter.id){mutableIntStateOf(0)}
    var playing by rememberSaveable(chapter.id){mutableStateOf(false)}
    var selectedSettlementFocus by rememberSaveable(chapter.id){mutableIntStateOf(-1)}
    LaunchedEffect(chapter.id,playing) {
        if(playing && stages.isNotEmpty()){
            while(playing && index<stages.lastIndex) {
                delay(1250) // classroom pacing, not biological kinetics
                index+=1
            }
            playing=false
        }
    }
    LazyColumn(
        state=listState, modifier=Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
            .testTag("u2-reader-"+chapter.id),
        contentPadding=PaddingValues(start=16.dp,top=16.dp,end=16.dp,bottom=32.dp),
        verticalArrangement=Arrangement.spacedBy(14.dp)
    ){
        item(key="header") {
            Column(verticalArrangement=Arrangement.spacedBy(9.dp)) {
                OutlinedButton(onClick=onBack,modifier=Modifier.heightIn(min=48.dp).testTag("u2-back")){
                    Text(tr(language,"Back to Unit II","அலகு II-க்குத் திரும்பு"))
                }
                Text("UNIT II  ·  PORIFERA & CNIDARIA",
                    style=MaterialTheme.typography.labelLarge,color=currentBlue)
                Text((book?.title ?: lesson.title).value(language),style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,
                    modifier=Modifier.testTag("u2-lesson-title").semantics{heading()})
            }
        }
        if (book == null && (chapter.id == "u2-sycon" || chapter.id == "u2-obelia")) {
            item(key = "r3-required-type-study-gallery") {
                val study = NativeCurriculumManifest.typeStudies.single { it.lessonId == chapter.id }
                RequiredTypeStudyPlates(study, language)
            }
        }
        // R7 single-render policy: all required Sycon/Obelia images appear ONCE
        // in RequiredTypeStudyPlates above. The legacy static duplicate gallery
        // was removed; the existing original rasters remain bundled unchanged.
        if(stages.isNotEmpty()) item(key="interactive-atlas"){
            Column(verticalArrangement=Arrangement.spacedBy(10.dp),
                modifier=Modifier.fillMaxWidth().testTag("u2-interactive-atlas")) {
                Text(tr(language,
                    if(sycon)"SYCON · BIOLOGY AND CANAL-FLOW STAGES"
                    else "OBELIA · COLONY AND LIFE-HISTORY STAGES",
                    if(sycon)"சைக்கான் · உயிரியல் மற்றும் நீர்க்கால்வாய் படிநிலைகள்"
                    else "ஒபீலியா · கூட்டு மற்றும் வாழ்க்கைச் சுழற்சி நிலைகள்"),
                    style=MaterialTheme.typography.titleLarge,
                    modifier=Modifier.semantics{heading()})
                Text(tr(language,
                    "Select a stage to study its anatomical features and hear the explanation.",
                    "ஒவ்வொரு படிநிலையையும் தேர்ந்தெடுத்து உடற்கூறுப் பண்புகளை அறிந்து விளக்கத்தைக் கேட்கலாம்."))
                // Biology sequence is narrated text and stage controls, NOT a
                // second raster/Canvas anatomical display. One image per plate.
                Text(stages[index.coerceIn(stages.indices)].name.value(language),
                    style=MaterialTheme.typography.titleMedium,fontWeight=FontWeight.Bold,
                    modifier=Modifier.testTag("u2-selected-stage"))
                Text(stages[index.coerceIn(stages.indices)].explanation.value(language),
                    modifier=Modifier.testTag("u2-stage-explanation"))
                Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick={playing=false;index=0},
                        modifier=Modifier.heightIn(min=48.dp).weight(1f).testTag("u2-reset")) {
                        Text(tr(language,"Restart","மீண்டும்"))
                    }
                    Button(onClick={
                            if(playing) playing=false
                            else {if(index>=stages.lastIndex)index=0;playing=true}
                        },modifier=Modifier.heightIn(min=48.dp).weight(1f).testTag("u2-play-pause")){
                        Text(tr(language,if(playing)"Pause" else "Animate",
                            if(playing)"நிறுத்து" else "இயக்கு"))
                    }
                }
                Button(onClick={
                    val chosen = if(obelia && index == 5)
                        obeliaSettlementFocus(selectedSettlementFocus) else null
                    narrator.speak(chosen ?: stages[index].explanation,language)
                },
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("u2-speak-stage")) {
                    Text(tr(language,"Hear this structure (installed offline voice)",
                        "இந்த அமைப்பைக் கேட்க (நிறுவிய ஆஃப்லைன் குரல்)"))
                }
                stages.forEachIndexed{at,stage->
                    OutlinedButton(onClick={index=at;playing=false},
                        modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                            .testTag("u2-stage-"+stage.id)
                            .semantics{selected=index==at}) {
                        Text(stage.name.value(language))
                    }
                }
                Text(tr(language,"Voice state: ","குரல் நிலை: ")+voiceStatus.name)
                Text(tr(language,
                    "Spoken text always remains visible above; offline audio needs an installed voice.",
                    "உரை மேலே எப்போதும் இருக்கும்; ஆஃப்லைன் ஒலிக்காக நிறுவப்பட்ட குரல் தேவை."))
            }
        }
        if(book!=null){ bookChapterItems(book,language){narrator.speak(it,language)} } else
        items(lesson.sections,key={it.id}){section->
            Card(modifier=Modifier.fillMaxWidth().testTag("u2-section-"+section.id)) {
                Column(Modifier.padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
                    Text(section.heading.value(language),
                        style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.SemiBold,
                        modifier=Modifier.semantics{heading()})
                    section.paragraphs.forEachIndexed{at,paragraph->
                        Text(paragraph.value(language),style=MaterialTheme.typography.bodyLarge,
                            modifier=Modifier.testTag("u2-paragraph-"+section.id+"-"+at))
                    }
                    OutlinedButton(onClick={narrator.speak(BilingualText(
                        section.paragraphs.joinToString(" "){it.english},
                        section.paragraphs.joinToString(" "){it.tamil}),language)},
                        modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                            .testTag("u2-narrate-"+section.id)) {
                        Text(tr(language,"Listen to this subsection","இந்தத் துணைப்பாடத்தைக் கேள்"))
                    }
                    Text(tr(language,"Reference: ","மேற்கோள்: ")+section.scientificSources.joinToString(),
                        style=MaterialTheme.typography.bodySmall)
                }
            }
        }
        item(key="sources") {
            Column(verticalArrangement=Arrangement.spacedBy(8.dp)) {
                Text(tr(language,"Scientific references","அறிவியல் மேற்கோள்கள்"),
                    style=MaterialTheme.typography.titleMedium,modifier=Modifier.semantics{heading()})
                lesson.sections.flatMap{it.scientificSources}.distinct().forEach{Text(it)}
            }
        }
        item(key="practice-heading"){
            Text(tr(language,"EXAM PRACTICE · AFTER THE LESSON",
                "தேர்வுப் பயிற்சி · பாடத்திற்குப் பின்"),
                style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold,
                modifier=Modifier.testTag("u2-practice-heading").semantics{heading()})
        }
        items(chapter.a5Questions,key={it.id}){question->
            Card(modifier=Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    Text(question.question.value(language),style=MaterialTheme.typography.titleMedium,
                        modifier=Modifier.testTag("a5-question-"+question.id))
                    OutlinedButton(onClick={onToggle(question)},
                        modifier=Modifier.heightIn(min=48.dp).testTag("a5-toggle-"+question.id)){
                        Text(tr(language,if(revealedQuestionId==question.id)"Hide answer" else "Reveal five points",
                            if(revealedQuestionId==question.id)"விடையை மறை" else "ஐந்து குறிப்புகளைக் காட்டு"))
                    }
                    if(revealedQuestionId==question.id)
                        question.answerPoints.forEachIndexed{at,point->
                            Text("${at+1}. "+point.value(language),
                                modifier=Modifier.testTag("a5-point-"+question.id+"-"+(at+1)))
                        }
                }
            }
        }
    }
}
