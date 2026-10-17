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
        state=listState, modifier=Modifier.fillMaxSize().statusBarsPadding().testTag("u2-reader-"+chapter.id),
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
                    "Read the explanation, inspect the original diagram, and only then answer the revision questions.",
                    "முதலில் விளக்கத்தைப் படித்து அசல் படத்தை ஆராய்க; அதன்பின் பயிற்சி வினாக்களைப் படிக்கவும்."))
            }
        }
        if (chapter.id == "u2-sycon" || chapter.id == "u2-obelia") {
            item(key = "r3-required-type-study-gallery") {
                val study = NativeCurriculumManifest.typeStudies.single { it.lessonId == chapter.id }
                RequiredTypeStudyPlates(study, language)
            }
        }
        if (stages.isNotEmpty()) item(key="independent-pencil-lesson-figures") {
            Column(
                modifier=Modifier.fillMaxWidth().testTag("u2-pencil-lesson-section"),
                verticalArrangement=Arrangement.spacedBy(12.dp)
            ) {
                Text(tr(language,
                    "TEXTBOOK ANATOMY · STATIC RASTER CANDIDATES · NOT VERIFIED SCANS",
                    "பாடநூல் உடற்கூறு · நிலையான படக்கோப்பு வரைவுகள் · ஸ்கேன் உறுதி செய்யப்படவில்லை"),
                    style=MaterialTheme.typography.titleLarge,
                    modifier=Modifier.semantics { heading() })
                Text(tr(language,
                    "These are bundled digital raster candidates, not authenticated historical scans. Anatomical and source-rights review remain pending.",
                    "இவை இணைக்கப்பட்ட டிஜிட்டல் வரைவு படங்கள்; உறுதி செய்யப்பட்ட வரலாற்று ஸ்கேன்கள் அல்ல. உடற்கூறு மற்றும் மூல உரிமை மதிப்பாய்வு நிலுவையில் உள்ளது."),
                    style=MaterialTheme.typography.bodyMedium)
                if (sycon) {
                    Card {
                        Column(Modifier.padding(8.dp)) {
                            UnitIIStaticLessonPlate(
                                relativeStem="unit2/sycon_plate",
                                language=language,
                                tag="u2-sycon-historic-static-raster",
                                captionEnglish="Sycon labelled teaching image from the bundled WebP; this is a digital candidate, not an authenticated 1903 pencil scan. Canal anatomy and leaders await expert review.",
                                captionTamil="சைக்கான் குறியிடப்பட்ட WebP கற்பித்தல் வரைவு. இது உறுதிசெய்யப்பட்ட 1903 பென்சில் ஸ்கேன் அல்ல; கால்வாய் உறுப்புகளும் குறிக்கோடுகளும் நிபுணர் மதிப்பாய்வில் உள்ளன."
                            )
                        }
                    }
                } else if (obelia) {
                    Card {
                        Column(Modifier.padding(8.dp)) {
                            UnitIIStaticLessonPlate(
                                relativeStem="unit2/obelia_colony",
                                language=language,
                                tag="u2-obelia-colony-static-raster",
                                captionEnglish="Obelia colony: hydranth, gonangium and protective thecae. Digital graphite draft; review pending.",
                                captionTamil="ஒபீலியா கூட்டு: உணவுப் பாலிப், இனப்பெருக்கப் பாலிப், பாதுகாப்பு உறைகள். பென்சில் பாணி வரைவு; மதிப்பாய்வு நிலுவையில் உள்ளது."
                            )
                        }
                    }
                    Card {
                        Column(Modifier.padding(8.dp)) {
                            UnitIIStaticLessonPlate(
                                relativeStem="unit2/obelia_lifecycle",
                                language=language,
                                tag="u2-obelia-life-static-raster",
                                captionEnglish="Bundled Obelia life-history WebP candidate. Original image provenance, reproductive stages and Tamil labels require expert verification.",
                                captionTamil="ஒபீலியா வாழ்க்கைச் சுழற்சிக்கான இணைக்கப்பட்ட WebP கற்பித்தல் வரைவு. மூல உரிமை, இனப்பெருக்க நிலைகள், தமிழ் குறியீடுகள் மதிப்பாய்வில் உள்ளன."
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
                    if(sycon)"INTERACTIVE ANATOMY · Sycon provisional canal hotspots"
                    else "INTERACTIVE ANATOMY · Obelia colony and life-history hotspots",
                    if(sycon)"தொடுதிறன் உடற்கூறு · சைக்கான் நீர்க் கால்வாய் வரைவு"
                    else "தொடுதிறன் உடற்கூறு · ஒபீலியா கூட்டு மற்றும் வாழ்க்கைச் சுழற்சி"),
                    style=MaterialTheme.typography.titleLarge,
                    modifier=Modifier.semantics{heading()})
                Text(tr(language,
                    "Labelled pencil raster plate—schematic, not to scale. Select a stage or touch the illustration.",
                    "பென்சில் வரைபடப் படத்தாள்—அளவுப்படி அல்ல. படிநிலையைத் தேர்ந்தெடுக்கவும் அல்லது படத்தைத் தொடவும்."))
                val p by animateFloatAsState(targetValue=index.toFloat(),
                    animationSpec=tween(durationMillis=600),label="Unit II teaching stage")
                Card(colors=CardDefaults.cardColors(containerColor=parchment)) {
                    // Raster-only anatomy: no Canvas/SVG substitute is permitted.
                    UnitIIImagePlate(
                        sycon=sycon,
                        progress=p,
                        language=language,
                        onSelect={stage, focus ->
                            index=stage
                            selectedSettlementFocus = if(obelia && stage == 5) focus else -1
                            playing=false
                            val chosen = if (obelia && stage == 5)
                                obeliaSettlementFocus(focus) else null
                            narrator.speak(chosen ?: stages[stage].explanation,language)
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
                Text(tr(language,"All diagrams are explanatory original artwork. Slide photographs, full-paper anatomical review and human Tamil approval remain pending.",
                    "எல்லாப் படங்களும் அசல் விளக்க வரைபடங்கள். நுண்ணோக்கிப் படங்கள், முழு ஆய்வுக் கட்டுரை மதிப்பாய்வு, தமிழ் நிபுணர் ஒப்புதல் ஆகியவை நிலுவையில் உள்ளன."))
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
