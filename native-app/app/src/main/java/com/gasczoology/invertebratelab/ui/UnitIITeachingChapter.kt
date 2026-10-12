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
        state=listState, modifier=Modifier.fillMaxSize().testTag("u2-reader-"+chapter.id),
        contentPadding=PaddingValues(16.dp),verticalArrangement=Arrangement.spacedBy(14.dp)
    ){
        item(key="header") {
            Column(verticalArrangement=Arrangement.spacedBy(9.dp)) {
                OutlinedButton(onClick=onBack,modifier=Modifier.heightIn(min=48.dp).testTag("u2-back")){
                    Text(tr(language,"Back to Unit II","அலகு II-க்குத் திரும்பு"))
                }
                Text("UNIT II  ·  PORIFERA & CNIDARIA",
                    style=MaterialTheme.typography.labelLarge,color=currentBlue)
                Text(lesson.title.value(language),style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,
                    modifier=Modifier.testTag("u2-lesson-title").semantics{heading()})
                Text(tr(language,
                    "ILLUSTRATED TEACHING DRAFT · Source-linked; scientific and Tamil review pending.",
                    "படவிளக்கப் பாட வரைவு · ஆதார இணைப்புகள் உள்ளன; அறிவியல், தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது."),
                    modifier=Modifier.testTag("u2-draft-disclosure"))
                Text(tr(language,
                    "Inspect the separate lesson raster figure, explore the interactive anatomy if useful, and then answer the revision questions.",
                    "தனியாக உள்ள பாடப் படத்தை ஆராய்ந்து, தேவையெனில் தொடுதிறன் உடற்கூறு படத்தைப் பயன்படுத்தி, பின்னர் பயிற்சி வினாக்களுக்குப் பதிலளிக்கவும்."))
            }
        }
        if (stages.isNotEmpty()) item(key = "lesson-pencil-figures") {
            Column(
                modifier = Modifier.fillMaxWidth().testTag("u2-pencil-lesson-section"),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    tr(language, "LESSON ILLUSTRATIONS · PENCIL-STYLE RASTER PLATES",
                        "பாட விளக்கப் படங்கள் · பென்சில் பாணி படக்கோப்புகள்"),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() }
                )
                Text(
                    tr(language,
                        "These are the lesson's static raster figures. They contain no dynamic SVG, Canvas anatomy or interactive hotspot overlay. Drawing provenance and scientific approval are still pending.",
                        "இவை பாடத்திற்கான நிலையான படங்கள். இவற்றின் மீது SVG, Canvas அல்லது தொடுதிறன் குறியீடுகள் சேர்க்கப்படவில்லை. படங்களின் மூலம் மற்றும் அறிவியல் ஒப்புதல் இன்னும் நிலுவையில் உள்ளன."),
                    style = MaterialTheme.typography.bodyMedium
                )
                Card(colors = CardDefaults.cardColors(containerColor = parchment)) {
                    Column(Modifier.padding(8.dp)) {
                        UnitIILessonPencilPlate(sycon = sycon, language = language)
                    }
                }
                if (obelia) {
                    Text(
                        tr(language, "Obelia · separate life-history lesson figure",
                            "ஒபீலியா · தனி வாழ்க்கைச் சுழற்சிப் பாடப் படம்"),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Card(colors = CardDefaults.cardColors(containerColor = parchment)) {
                        Column(Modifier.padding(8.dp)) {
                            UnitIILessonPencilPlate(
                                sycon = false, lifeHistory = true, language = language
                            )
                        }
                    }
                }
            }
        }
        if(stages.isNotEmpty()) item(key="interactive-atlas"){
            Column(verticalArrangement=Arrangement.spacedBy(10.dp),
                modifier=Modifier.fillMaxWidth().testTag("u2-interactive-atlas")) {
                Text(tr(language,
                    if(sycon)"INTERACTIVE ANATOMY · Sycon canal-system hotspots"
                    else "INTERACTIVE ANATOMY · Obelia colony and life history",
                    if(sycon)"தொடுதிறன் உடற்கூறு · சைக்கான் நீர்க் கால்வாய்கள்"
                    else "தொடுதிறன் உடற்கூறு · ஒபீலியா கூட்டு மற்றும் வாழ்க்கைச் சுழற்சி"),
                    style=MaterialTheme.typography.titleLarge,
                    modifier=Modifier.semantics{heading()})
                Text(tr(language,
                    "This is a separate interactive hotspot view of the raster plate; only this panel adds native Canvas touch targets. Not to scale. Select a stage or tap the image.",
                    "இது படக்கோப்பின் தனி தொடுதிறன் காட்சி; இந்தப் பகுதியில் மட்டுமே Canvas தொடு குறியீடுகள் உள்ளன. அளவுப்படி அல்ல. படிநிலையைத் தேர்க அல்லது படத்தில் தொடுக."))
                val p by animateFloatAsState(targetValue=index.toFloat(),
                    animationSpec=tween(durationMillis=600),label="Unit II teaching stage")
                Card(colors=CardDefaults.cardColors(containerColor=parchment)) {
                    // Raster-only anatomy: no Canvas/SVG substitute is permitted.
                    UnitIIImagePlate(
                        sycon=sycon,
                        progress=p,
                        language=language,
                        onSelect={stage ->
                            index=stage
                            playing=false
                            narrator.speak(stages[stage].explanation,language)
                        }
                    )
                }
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
                Button(onClick={narrator.speak(stages[index].explanation,language)},
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
                Text(tr(language,"References and evidence limits","மேற்கோள்களும் ஆதார வரம்புகளும்"),
                    style=MaterialTheme.typography.titleMedium,modifier=Modifier.semantics{heading()})
                Text(tr(language,"Lesson raster illustrations and interactive Canvas hotspots are separate. Authentic hand-scanned drawing provenance, scientific review and human Tamil approval remain pending.",
                    "பாடத்தின் படக்கோப்புகளும் தொடுதிறன் Canvas குறியீடுகளும் தனித்தனியாக உள்ளன. உண்மையான கையால் வரையப்பட்ட படத்தின் மூலம், அறிவியல் மதிப்பாய்வு, தமிழ் நிபுணர் ஒப்புதல் ஆகியவை நிலுவையில் உள்ளன."))
                lesson.sections.flatMap{it.scientificSources}.distinct().forEach{Text(it)}
            }
        }
        item(key="practice-heading"){
            Text(tr(language,"EXAM PRACTICE · AFTER THE LESSON",
                "தேர்வுப் பயிற்சி · பாடத்திற்குப் பின்"),
                style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold,
                modifier=Modifier.testTag("u2-practice-heading").semantics{heading()})
            Text(tr(language,"The validated A5 question/answer corpus is preserved verbatim.",
                "சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினா–விடைத் தொகுப்பு மாற்றமின்றிப் பேணப்பட்டுள்ளது."))
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
