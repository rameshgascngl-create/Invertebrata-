package com.gasczoology.invertebratelab.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.delay
import kotlin.math.sqrt

private val unitIIInk=Color(0xFF40434A)
private val soft=Color(0xFF9FA4AC)
private val currentBlue=Color(0xFF176B9A)
private val focusOrange=Color(0xFFD36A27)
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
                    "Read the explanation, inspect the original diagram, and only then answer the revision questions.",
                    "முதலில் விளக்கத்தைப் படித்து அசல் படத்தை ஆராய்க; அதன்பின் பயிற்சி வினாக்களைப் படிக்கவும்."))
            }
        }
        if(stages.isNotEmpty()) item(key="interactive-atlas"){
            Column(verticalArrangement=Arrangement.spacedBy(10.dp),
                modifier=Modifier.fillMaxWidth().testTag("u2-interactive-atlas")) {
                Text(tr(language,
                    if(sycon)"Sycon · original canal-system cross-section"
                    else "Obelia · colony and life-history atlas",
                    if(sycon)"சைக்கான் · அசல் நீர்க் கால்வாய் வெட்டுப் படம்"
                    else "ஒபீலியா · கூட்டும் வாழ்க்கைச் சுழற்சிப் படமும்"),
                    style=MaterialTheme.typography.titleLarge,
                    modifier=Modifier.semantics{heading()})
                Text(tr(language,
                    "Schematic—not to scale. Select a numbered stage, or touch the marked plate.",
                    "விளக்க வரைபடம்—அளவுப்படி அல்ல. எண்ணிட்ட படிநிலையைத் தேர்க அல்லது படத்தில் தொடுக."))
                val p by animateFloatAsState(targetValue=index.toFloat(),
                    animationSpec=tween(durationMillis=600),label="Unit II teaching stage")
                Card(colors=CardDefaults.cardColors(containerColor=parchment)) {
                    if(sycon) SyconCanalCanvas(p,language,{stage->index=stage;playing=false})
                    else ObeliaColonyCanvas(p,language,{stage->index=stage;playing=false})
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

private val syconMarks=listOf(
    Offset(0.18f,0.52f), Offset(0.25f,0.52f),Offset(0.31f,0.52f),
    Offset(0.37f,0.52f),Offset(0.43f,0.52f),Offset(0.50f,0.52f),
    Offset(0.50f,0.11f)
)
private val obeliaMarks=listOf(
    Offset(.31f,.70f),Offset(.31f,.26f),Offset(.64f,.44f),
    Offset(.78f,.17f),Offset(.80f,.69f),Offset(.74f,.90f)
)
private fun nearestMark(click:Offset, w:Float,h:Float,marks:List<Offset>):Int{
    val x=click.x/w;val y=click.y/h
    return marks.indices.minByOrNull{i->
        val dx=x-marks[i].x;val dy=y-marks[i].y;dx*dx+dy*dy
    }?:0
}
private fun DrawScope.line(a:Offset,b:Offset,color:Color=unitIIInk,width:Float=2f){
    drawLine(color,a,b,width)
}
private fun DrawScope.dot(p:Offset,color:Color,rad:Float=5f) {
    drawCircle(color,rad,p)
}
private fun DrawScope.labelledFocus(stage:Float,marks:List<Offset>) {
    val i=stage.coerceIn(0f,(marks.size-1).toFloat())
    val a=marks[i.toInt()]
    val b=marks[(i.toInt()+1).coerceAtMost(marks.lastIndex)]
    val t=i-i.toInt()
    val p=Offset((a.x+(b.x-a.x)*t)*size.width,(a.y+(b.y-a.y)*t)*size.height)
    drawCircle(focusOrange,20f,p,style=Stroke(width=4f))
    drawCircle(focusOrange.copy(alpha=.40f),9f,p)
}
@Composable
private fun SyconCanalCanvas(progress:Float, language:AppLanguage,onSelect:(Int)->Unit) {
    Canvas(Modifier.fillMaxWidth().height(350.dp).testTag("u2-sycon-canvas")
        .pointerInput(Unit){detectTapGestures{click->onSelect(nearestMark(click,size.width.toFloat(),size.height.toFloat(),syconMarks))}}
        .semantics {
            contentDescription=tr(language,
                "Sycon cutaway: ostia, incurrent canals, prosopyles, choanocyte-lined radial canals, apopyles, central spongocoel, and terminal osculum.",
                "சைக்கான் வெட்டுப் படம்: ஆஸ்டியா, உட்செல்லும் கால்வாய், புரோசோபைல், காலர் செல்கள் கொண்ட ஆரக் கால்வாய், அப்போபைல், ஸ்பாஞ்சோசீல், ஆஸ்குலம்.")
            progressBarRangeInfo=ProgressBarRangeInfo(progress,0f..6f)
        }) {
        // All plate coordinates are normalized; the body and labels cannot be
        // mistaken for a calibrated histological section.
        val w=size.width; val h=size.height
        fun p(x:Float,y:Float)=Offset(w*x,h*y)
        val wall=Color(0xFFE2DDD2)
        // Outer vase, narrow apical opening and patent central cavity.
        val outer=Path().apply{
            moveTo(w*.39f,h*.10f);lineTo(w*.23f,h*.20f);lineTo(w*.19f,h*.87f)
            quadraticTo(w*.50f,h*.98f,w*.81f,h*.87f)
            lineTo(w*.77f,h*.20f);lineTo(w*.61f,h*.10f)
        }
        drawPath(outer,wall)
        drawPath(outer,unitIIInk,style=Stroke(2.7f))
        val cavity=Path().apply{
            moveTo(w*.46f,h*.15f);lineTo(w*.43f,h*.83f)
            quadraticTo(w*.50f,h*.88f,w*.57f,h*.83f)
            lineTo(w*.54f,h*.15f)
        }
        drawPath(cavity,parchment)
        drawPath(cavity,unitIIInk,style=Stroke(2f))
        // Osculum at upper opening, with spicule fringe.
        line(p(.39f,.10f),p(.46f,.15f));line(p(.61f,.10f),p(.54f,.15f))
        for(i in 0..5){ val x=.38f+i*.048f;line(p(x,.098f),p(x-.012f,.055f),soft,1.2f) }
        // Wall fold and alternate incurrent/radial channels. This is a
        // deliberately labelled cutaway, not a claim that every pore lies on
        // the same physical section.
        for(i in 0..5) {
            val y=.26f+i*.105f
            val cut=Color(0xFFF9F7EE)
            val xleft=.25f;val xright=.75f
            drawLine(cut,p(.24f,y),p(.44f,y),strokeWidth=13f)
            drawLine(cut,p(.56f,y),p(.76f,y),strokeWidth=13f)
            drawLine(unitIIInk,p(.24f,y-.018f),p(.44f,y-.018f),strokeWidth=1.6f)
            drawLine(unitIIInk,p(.24f,y+.018f),p(.44f,y+.018f),strokeWidth=1.6f)
            drawLine(unitIIInk,p(.56f,y-.018f),p(.76f,y-.018f),strokeWidth=1.6f)
            drawLine(unitIIInk,p(.56f,y+.018f),p(.76f,y+.018f),strokeWidth=1.6f)
            // Choanocyte collars reside in radial canals only.
            for(k in 0..3) {
                val x=.31f+k*.031f
                dot(p(x,y-.014f),soft,2.3f)
                line(p(x,y-.014f),p(x+.008f,y+.008f),soft,1.0f)
                dot(p(1f-x,y+.014f),soft,2.3f)
            }
            // Apopyles face the central spongocoel. Prosopyles face incurrent canals.
            dot(p(.43f,y),currentBlue,2.2f)
            dot(p(.57f,y),currentBlue,2.2f)
            dot(p(.28f,y),currentBlue,2.2f)
            dot(p(.72f,y),currentBlue,2.2f)
        }
        // Representative route follows exterior -> folded wall -> centre -> top.
        val water=listOf(p(.11f,.52f),p(.22f,.52f),p(.28f,.52f),p(.35f,.52f),
            p(.43f,.52f),p(.50f,.52f),p(.50f,.15f),p(.50f,.035f))
        for(i in 0 until water.lastIndex){
            line(water[i],water[i+1],currentBlue,3.5f)
            val at=water[i+1];dot(at,currentBlue,3.4f)
        }
        for(i in 0..7){
            val y=.29f+i*.065f;line(p(.08f,y),p(.18f,y-.017f),soft,1f)
        }
        labelledFocus(progress,syconMarks)
    }
}
@Composable
private fun ObeliaColonyCanvas(progress:Float,language:AppLanguage,onSelect:(Int)->Unit){
    Canvas(Modifier.fillMaxWidth().height(350.dp).testTag("u2-obelia-canvas")
        .pointerInput(Unit){detectTapGestures{click->onSelect(nearestMark(click,size.width.toFloat(),size.height.toFloat(),obeliaMarks))}}
        .semantics {
            contentDescription=tr(language,
                "Obelia colony with hydrorhiza, hydrocaulus, feeding hydranth and reproductive gonangium, plus a separate medusa, planula, and settled polyp.",
                "ஒபீலியா கூட்டில் ஹைட்ரோரிசா, ஹைட்ரோகாலஸ், உணவு ஹைட்ராந்த், இனப்பெருக்க கோனாஞ்சியம்; தனித்த மெடூசா, பிளானுலா, இளம் பாலிப்.")
            progressBarRangeInfo=ProgressBarRangeInfo(progress,0f..5f)
        }) {
        val w=size.width;val h=size.height
        fun p(x:Float,y:Float)=Offset(w*x,h*y)
        // Basal hydrorhiza and upright hydrocaulus. Branches share a stem.
        line(p(.08f,.91f),p(.55f,.91f),unitIIInk,3.8f)
        for(i in 0..4){val x=.11f+i*.08f;line(p(x,.91f),p(x-.024f,.943f),soft,1.8f)}
        line(p(.31f,.91f),p(.31f,.38f),unitIIInk,5f)
        line(p(.31f,.58f),p(.51f,.58f),unitIIInk,3f)
        line(p(.51f,.58f),p(.62f,.44f),unitIIInk,3f)
        // Hydranth with tentacles around mouth, carried by hydrotheca.
        val cup=Path().apply {
            moveTo(w*.26f,h*.39f);quadraticTo(w*.29f,h*.47f,w*.31f,h*.47f)
            quadraticTo(w*.34f,h*.47f,w*.36f,h*.39f)
        }
        drawPath(cup,unitIIInk,style=Stroke(2f))
        drawOval(color=Color(0xFFD4DDD7),topLeft=p(.28f,.255f),
            size=Size(w*.06f,h*.155f))
        for(i in 0..7){
            val theta=i*3.14159f/7f
            val x=.31f+(i-3.5f)*.028f
            line(p(.31f,.27f),p(x,.16f+(i%2)*.032f),unitIIInk,1.8f)
        }
        dot(p(.31f,.26f),focusOrange,3f)
        // Gonangium: gonotheca sheath enclosing multiple buds.
        val gon=Path().apply {
            moveTo(w*.59f,h*.57f);quadraticTo(w*.54f,h*.38f,w*.59f,h*.29f)
            quadraticTo(w*.65f,h*.23f,w*.70f,h*.29f)
            quadraticTo(w*.75f,h*.39f,w*.69f,h*.57f)
        }
        drawPath(gon,Color(0xFFDFE9E8))
        drawPath(gon,unitIIInk,style=Stroke(2.3f))
        line(p(.64f,.55f),p(.64f,.32f),unitIIInk,2f)
        for(i in 0..3)dot(p(.62f+i%2*.044f,.35f+i*.06f),soft,7f)
        // Separate medusa is diagrammatically above-right; do not join it
        // physically to the colony.
        val bell=Path().apply {
            moveTo(w*.74f,h*.20f);quadraticTo(w*.82f,h*.045f,w*.92f,h*.20f)
            quadraticTo(w*.82f,h*.25f,w*.74f,h*.20f)
        }
        drawPath(bell,Color(0xFFE2E7EE))
        drawPath(bell,unitIIInk,style=Stroke(2f))
        for(i in 0..3) {
            val x=.75f+i*.055f;line(p(x,.21f),p(x-.008f,.30f),unitIIInk,1.2f)
        }
        line(p(.82f,.16f),p(.82f,.26f),unitIIInk,1.7f)
        // Ciliated planula is not a tiny medusa.
        drawOval(soft.copy(alpha=.4f),topLeft=p(.765f,.64f),size=Size(w*.085f,h*.095f))
        drawOval(unitIIInk,topLeft=p(.765f,.64f),size=Size(w*.085f,h*.095f),style=Stroke(1.6f))
        for(i in 0..6){
            val x=.77f+i*.011f;line(p(x,.64f),p(x-.006f,.62f),unitIIInk,1f)
        }
        // Newly settled polyp distinct from larva.
        line(p(.68f,.94f),p(.88f,.94f),unitIIInk,2f)
        line(p(.76f,.94f),p(.76f,.855f),unitIIInk,2.6f)
        for(i in 0..3)line(p(.76f,.855f),p(.72f+i*.027f,.81f),unitIIInk,1.2f)
        labelledFocus(progress,obeliaMarks)
    }
}
