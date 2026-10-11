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
    Offset(.205f,.572f),Offset(.274f,.572f),Offset(.319f,.548f),
    Offset(.374f,.520f),Offset(.435f,.520f),Offset(.500f,.520f),
    Offset(.500f,.115f)
)
private val obeliaMarks=listOf(
    Offset(.295f,.700f),Offset(.300f,.245f),Offset(.505f,.430f),
    Offset(.786f,.195f),Offset(.790f,.655f),Offset(.765f,.875f)
)
private fun nearestMark(click:Offset,w:Float,h:Float,marks:List<Offset>):Int {
    if(w<=0f || h<=0f) return 0
    val x=click.x/w;val y=click.y/h
    return marks.indices.minByOrNull{i->
        val dx=x-marks[i].x;val dy=y-marks[i].y;dx*dx+dy*dy
    }?:0
}
private fun DrawScope.labelledFocus(stage:Float,marks:List<Offset>) {
    val i=stage.coerceIn(0f,(marks.size-1).toFloat())
    val a=marks[i.toInt()]
    val b=marks[(i.toInt()+1).coerceAtMost(marks.lastIndex)]
    val t=i-i.toInt()
    val point=Offset((a.x+(b.x-a.x)*t)*size.width,(a.y+(b.y-a.y)*t)*size.height)
    drawCircle(focusOrange.copy(alpha=.90f),13f,point,style=Stroke(width=2.7f))
    drawCircle(focusOrange.copy(alpha=.22f),6f,point)
}

// Graphite marks are deterministically over-traced, with fine stipple/hatching.
// This native Canvas plate uses no raster assets or embedded browser runtime.
// Its explanatory morphology is NOT represented as a histological micrograph.
private val graphiteDark=Color(0xFF343738)
private val graphiteMid=Color(0xFF73716C)
private val graphitePale=Color(0xFFB6AEA0)
private fun DrawScope.pencilLine(a:Offset,b:Offset,weight:Float=1.8f) {
    drawLine(graphitePale.copy(alpha=.34f),a+Offset(.7f,-.5f),
        b+Offset(.4f,.65f),weight*2.35f)
    drawLine(graphiteDark.copy(alpha=.82f),a,b,weight)
    drawLine(graphiteMid.copy(alpha=.32f),a+Offset(-.6f,.5f),
        b+Offset(.5f,-.5f),weight*.75f)
}
private fun DrawScope.pencilPath(path:Path,weight:Float=2f) {
    drawPath(path,graphitePale.copy(alpha=.28f),style=Stroke(width=weight*2.5f))
    drawPath(path,graphiteDark.copy(alpha=.84f),style=Stroke(width=weight))
    drawPath(path,graphiteMid.copy(alpha=.30f),style=Stroke(width=weight*.54f))
}
private fun DrawScope.pencilDot(point:Offset,radius:Float=1.35f) {
    drawCircle(graphiteMid.copy(alpha=.53f),radius,point)
}
@Composable
private fun SyconCanalCanvas(progress:Float,language:AppLanguage,onSelect:(Int)->Unit) {
    Canvas(Modifier.fillMaxWidth().height(350.dp).testTag("u2-sycon-canvas")
        .pointerInput(Unit) { detectTapGestures { click ->
            onSelect(nearestMark(click,size.width.toFloat(),size.height.toFloat(),syconMarks))
        } }
        .semantics {
            contentDescription=tr(language,
                "Original graphite cutaway of Sycon. Dermal ostia lead to incurrent canals, through prosopyles to choanocyte-lined radial canals, then through apopyles to the spongocoel and apical osculum. Not to scale.",
                "சைக்கான் கரிக்கோல் வெட்டுப் படம். ஆஸ்டியா வழியாக உட்செல்லும் கால்வாய், புரோசோபைல் வழியாக காலர் செல்கள் கொண்ட ஆரக் கால்வாய், அப்போபைல் வழியாக ஸ்பாஞ்சோசீல், மேலே ஆஸ்குலம். அளவுப்படி அல்ல.")
            progressBarRangeInfo=ProgressBarRangeInfo(progress,0f..6f)
        }) {
        val w=size.width; val h=size.height
        fun p(x:Float,y:Float)=Offset(w*x,h*y)
        // Vase-shaped original contour with an open osculum; not a solid polygon.
        val wall=Path().apply {
            moveTo(w*.415f,h*.122f)
            cubicTo(w*.365f,h*.138f,w*.286f,h*.160f,w*.251f,h*.197f)
            cubicTo(w*.226f,h*.401f,w*.213f,h*.687f,w*.226f,h*.838f)
            cubicTo(w*.330f,h*.922f,w*.676f,h*.924f,w*.774f,h*.837f)
            cubicTo(w*.793f,h*.666f,w*.777f,h*.387f,w*.745f,h*.197f)
            cubicTo(w*.710f,h*.161f,w*.634f,h*.138f,w*.585f,h*.122f)
            close()
        }
        drawPath(wall,Color(0xFFF2EFE5))
        // Two separate graphite outlines preserve the open terminal aperture.
        val outerOutline=Path().apply {
            moveTo(w*.415f,h*.122f)
            cubicTo(w*.365f,h*.138f,w*.286f,h*.160f,w*.251f,h*.197f)
            cubicTo(w*.226f,h*.401f,w*.213f,h*.687f,w*.226f,h*.838f)
            cubicTo(w*.330f,h*.922f,w*.676f,h*.924f,w*.774f,h*.837f)
            cubicTo(w*.793f,h*.666f,w*.777f,h*.387f,w*.745f,h*.197f)
            cubicTo(w*.710f,h*.161f,w*.634f,h*.138f,w*.585f,h*.122f)
        }
        pencilPath(outerOutline,2.4f)
        // Stippled mesohyl. Keep the central spongocoel unfilled.
        for(i in 0 until 330) {
            val x=.255f+((i*79)%317)/317f*.490f
            val y=.210f+((i*53)%311)/311f*.625f
            if(x<.422f || x>.578f) pencilDot(p(x,y),.65f+(i%3)*.22f)
        }
        // Needles at the oscular margin suggest calcareous spicules, not cilia.
        for(i in 0..11) {
            val x=.400f+i*.018f
            pencilLine(p(x,.122f),p(x+(i%3-1)*.012f,.062f+(i%4)*.010f),1.05f)
        }
        // Patent, choanocyte-free spongocoel; its boundary is drawn independently.
        val lumen=Path().apply {
            moveTo(w*.466f,h*.122f)
            cubicTo(w*.460f,h*.290f,w*.438f,h*.615f,w*.433f,h*.820f)
            quadraticTo(w*.500f,h*.869f,w*.567f,h*.820f)
            cubicTo(w*.562f,h*.616f,w*.542f,h*.287f,w*.534f,h*.122f)
            close()
        }
        drawPath(lumen,parchment)
        val lumenSides=Path().apply {
            moveTo(w*.466f,h*.122f)
            cubicTo(w*.460f,h*.290f,w*.438f,h*.615f,w*.433f,h*.820f)
            quadraticTo(w*.500f,h*.869f,w*.567f,h*.820f)
            cubicTo(w*.562f,h*.616f,w*.542f,h*.287f,w*.534f,h*.122f)
        }
        pencilPath(lumenSides,1.75f)
        // Separate radially elongated, choanocyte-lined chambers. Between these
        // lie INCURRENT passages; they are not straight ostium-to-spongocoel tubes.
        for(i in 0..4) {
            val y=.285f+i*.117f
            for(side in 0..1) {
                val mirror=side==1
                fun q(x:Float,yy:Float)=p(if(mirror) 1f-x else x,yy)
                val chamber=Path().apply {
                    val a=q(.303f,y-.022f); moveTo(a.x,a.y)
                    val b=q(.433f,y-.024f);lineTo(b.x,b.y)
                    val c=q(.439f,y+.019f);quadraticTo(q(.462f,y).x,q(.462f,y).y,c.x,c.y)
                    val d=q(.306f,y+.020f);lineTo(d.x,d.y)
                    val e=q(.303f,y-.022f);quadraticTo(q(.288f,y).x,q(.288f,y).y,e.x,e.y)
                    close()
                }
                drawPath(chamber,Color(0xFFFFFCF5))
                pencilPath(chamber,1.45f)
                // Choanocyte collars and inwardly directed flagella are only
                // drawn on chamber walls, never lining the spongocoel.
                for(k in 0..7) {
                    val x=.321f+k*.013f
                    val top=q(x,y-.018f);val bottom=q(x,y+.017f)
                    pencilDot(top,1.9f); pencilDot(bottom,1.9f)
                    pencilLine(top,q(x+.007f,y-.003f),.75f)
                    pencilLine(bottom,q(x+.007f,y+.002f),.75f)
                }
                // Apopyle is a distinct opening at the spongocoel end.
                val opening=q(.440f,y)
                drawCircle(parchment,4.1f,opening)
                pencilLine(q(.438f,y-.021f),q(.449f,y-.023f),1.0f)
                pencilLine(q(.438f,y+.020f),q(.449f,y+.024f),1.0f)
                if(i<4) {
                    val midway=y+.0585f
                    // An ostium enters an incurrent canal running between
                    // radial chambers; a prosopyle turns toward a chamber.
                    val pore=q(.243f,midway)
                    drawCircle(parchment,3.6f,pore)
                    val inflow=Path().apply {
                        moveTo(q(.252f,midway).x,q(.252f,midway).y)
                        quadraticTo(q(.279f,midway-.014f).x,q(.279f,midway-.014f).y,
                            q(.294f,midway).x,q(.294f,midway).y)
                        quadraticTo(q(.306f,midway-.011f).x,q(.306f,midway-.011f).y,
                            q(.310f,y+.018f).x,q(.310f,y+.018f).y)
                    }
                    pencilPath(inflow,1.1f)
                    drawCircle(parchment,3.0f,q(.310f,y+.020f))
                }
            }
        }
        // Light angled spicule triads in the outside pinacoderm/mesohyl.
        for(i in 0..12) {
            val yy=.236f+i*.045f
            for(x in listOf(.257f,.739f)) {
                val sign=if(x<.5f)1f else -1f
                pencilLine(p(x,yy),p(x+sign*.025f,yy-.017f),.75f)
                pencilLine(p(x,yy),p(x+sign*.023f,yy+.011f),.75f)
            }
        }
        // Diagrammatic route deliberately bends between incurrent and radial
        // components. Blue signifies flow; never interpretation as cell anatomy.
        val route=listOf(
            p(.140f,.577f),p(.242f,.577f),p(.280f,.577f),
            p(.307f,.552f),p(.329f,.519f),p(.380f,.519f),
            p(.445f,.519f),p(.500f,.519f),p(.500f,.124f),p(.500f,.036f)
        )
        for(i in 0 until route.lastIndex) {
            drawLine(currentBlue.copy(alpha=.64f),route[i],route[i+1],2.2f)
        }
        for(i in listOf(1,3,5,7,9))drawCircle(currentBlue.copy(alpha=.75f),2.2f,route[i])
        labelledFocus(progress,syconMarks)
    }
}
@Composable
private fun ObeliaColonyCanvas(progress:Float,language:AppLanguage,onSelect:(Int)->Unit) {
    Canvas(Modifier.fillMaxWidth().height(350.dp).testTag("u2-obelia-canvas")
        .pointerInput(Unit) {detectTapGestures{click->
            onSelect(nearestMark(click,size.width.toFloat(),size.height.toFloat(),obeliaMarks))
        }}
        .semantics {
            contentDescription=tr(language,
                "Graphite zoological plate: Obelia attached colony with branching hydrocaulus, hydrorhiza, feeding hydranth and reproductive gonangium containing medusa buds; separate free medusa, ciliated planula and young settled polyp. Not to scale.",
                "ஒபீலியா கரிக்கோல் படம்: ஹைட்ரோரிசா, கிளை ஹைட்ரோகாலஸ், உணவு ஹைட்ராந்த், மெடூசா மொட்டுகளுடன் கோனாஞ்சியம்; தனி மெடூசா, குறுஇழை பிளானுலா, புதிய பாலிப். அளவுப்படி அல்ல.")
            progressBarRangeInfo=ProgressBarRangeInfo(progress,0f..5f)
        }) {
        val w=size.width; val h=size.height
        fun p(x:Float,y:Float)=Offset(w*x,h*y)
        // Dashed separator prevents interpreting medusa/larva as attached zooids.
        for(i in 0..10) pencilLine(p(.617f,.09f+i*.074f),p(.617f,.119f+i*.074f),.75f)
        // Basal hydrorhiza and irregular branching coenosarc, under perisarc.
        val basal=Path().apply {
            moveTo(w*.075f,h*.851f)
            cubicTo(w*.182f,h*.837f,w*.361f,h*.865f,w*.565f,h*.846f)
        }
        pencilPath(basal,2.65f)
        for(i in 0..9){
            val x=.09f+i*.049f
            pencilLine(p(x,.852f),p(x-.015f,.879f+(i%3)*.008f),1f)
        }
        val stem=Path().apply {
            moveTo(w*.298f,h*.849f)
            cubicTo(w*.302f,h*.712f,w*.292f,h*.576f,w*.300f,h*.415f)
        }
        pencilPath(stem,3.3f)
        pencilPath(Path().apply {
            moveTo(w*.307f,h*.702f)
            cubicTo(w*.410f,h*.701f,w*.414f,h*.590f,w*.494f,h*.572f)
        },2.9f)
        // Fine perisarc parallel to the coenosarc (not a solid steel rod).
        pencilLine(p(.290f,.842f),p(.289f,.472f),.78f)
        pencilLine(p(.307f,.842f),p(.309f,.460f),.78f)
        for(i in 0..20) {
            val y=.435f+i*.018f
            pencilLine(p(.295f,y),p(.301f,y-.004f),.65f)
        }
        // Protective hydrotheca shaped as a clear cup around the hydranth.
        val cup=Path().apply {
            moveTo(w*.244f,h*.315f)
            cubicTo(w*.246f,h*.372f,w*.260f,h*.412f,w*.300f,h*.428f)
            cubicTo(w*.341f,h*.411f,w*.356f,h*.372f,w*.359f,h*.315f)
        }
        pencilPath(cup,1.7f)
        pencilLine(p(.239f,.316f),p(.255f,.316f),1.1f)
        pencilLine(p(.350f,.316f),p(.367f,.316f),1.1f)
        val hydranth=Path().apply {
            moveTo(w*.300f,h*.392f)
            cubicTo(w*.262f,h*.358f,w*.264f,h*.267f,w*.289f,h*.235f)
            quadraticTo(w*.301f,h*.220f,w*.310f,h*.236f)
            cubicTo(w*.339f,h*.272f,w*.342f,h*.355f,w*.300f,h*.392f)
            close()
        }
        drawPath(hydranth,Color(0xFFF0EEE6))
        pencilPath(hydranth,1.75f)
        // Hypostome and mouth, with individually curved nematocyst-bearing
        // tentacles (not a bouquet of straight spokes).
        pencilDot(p(.301f,.221f),2.25f)
        for(i in 0..10) {
            val x=.300f+(i-5)*.025f
            val y=.104f+(i%3)*.013f+((i-5)*(i-5))*.001f
            val end=p(x,y)
            val curve=Path().apply {
                moveTo(w*.301f,h*.232f)
                quadraticTo(w*(.301f+(i-5)*.012f),h*.174f,end.x,end.y)
            }
            pencilPath(curve,1.05f)
            for(j in 1..3){
                val t=j/4f
                pencilDot(p(.301f+(x-.301f)*t,.232f+(y-.232f)*t),.6f)
            }
        }
        // Reproductive gonangium, distinct from a feeding hydranth.
        pencilLine(p(.494f,.572f),p(.508f,.523f),2.1f)
        val gonotheca=Path().apply {
            moveTo(w*.484f,h*.552f)
            cubicTo(w*.441f,h*.498f,w*.458f,h*.365f,w*.478f,h*.324f)
            cubicTo(w*.496f,h*.292f,w*.527f,h*.292f,w*.545f,h*.324f)
            cubicTo(w*.565f,h*.381f,w*.568f,h*.509f,w*.532f,h*.552f)
            close()
        }
        drawPath(gonotheca,Color(0xFFF6F4EC))
        pencilPath(gonotheca,1.9f)
        val blastostyle=Path().apply {
            moveTo(w*.507f,h*.546f)
            cubicTo(w*.506f,h*.468f,w*.505f,h*.388f,w*.507f,h*.340f)
        }
        pencilPath(blastostyle,1.2f)
        for(i in 0..3){
            val yy=.367f+i*.044f
            val cx=if(i%2==0) .523f else .489f
            val bud=Path().apply{
                moveTo(w*(cx-.018f),h*(yy+.009f))
                quadraticTo(w*cx,h*(yy-.022f),w*(cx+.018f),h*(yy+.009f))
                quadraticTo(w*cx,h*(yy+.017f),w*(cx-.018f),h*(yy+.009f))
                close()
            }
            pencilPath(bud,.9f)
            pencilDot(p(cx,yy+.008f),.9f)
        }
        // Additional modest branch indicates colonial habit, not solitary hydra.
        val twig=Path().apply{
            moveTo(w*.295f,h*.653f)
            quadraticTo(w*.215f,h*.645f,w*.202f,h*.564f)
        }
        pencilPath(twig,1.9f)
        val smallerCup=Path().apply{
            moveTo(w*.174f,h*.555f)
            quadraticTo(w*.205f,h*.608f,w*.232f,h*.555f)
        }
        pencilPath(smallerCup,1.0f)
        pencilPath(Path().apply{
            moveTo(w*.204f,h*.555f);quadraticTo(w*.178f,h*.529f,w*.204f,h*.506f)
            quadraticTo(w*.230f,h*.529f,w*.204f,h*.555f)
        },1.1f)
        for(i in 0..5) pencilLine(p(.204f,.513f),p(.154f+i*.020f,.466f+(i%2)*.018f),.8f)
        // Inset: free sexual medusa. No connection to colony at any point.
        val bell=Path().apply {
            moveTo(w*.665f,h*.239f)
            cubicTo(w*.683f,h*.110f,w*.866f,h*.102f,w*.918f,h*.239f)
            quadraticTo(w*.788f,h*.267f,w*.665f,h*.239f)
            close()
        }
        drawPath(bell,Color(0xFFF1EFE6))
        pencilPath(bell,1.9f)
        // Velum, manubrium, radial and ring canals.
        pencilPath(Path().apply {
            moveTo(w*.685f,h*.236f)
            quadraticTo(w*.788f,h*.249f,w*.900f,h*.236f)
        },.85f)
        pencilLine(p(.786f,.147f),p(.786f,.292f),1.35f)
        pencilLine(p(.733f,.211f),p(.786f,.175f),.85f)
        pencilLine(p(.843f,.211f),p(.786f,.175f),.85f)
        for(i in 0..11){
            val x=.675f+i*.021f
            val yy=.239f-((i-5.5f)*(i-5.5f))*.0006f
            pencilLine(p(x,yy),p(x+(i%3-1)*.005f,.306f+(i%3)*.012f),.85f)
        }
        for(x in listOf(.732f,.843f))pencilDot(p(x,.211f),2.1f)
        // Planula: ovoid ciliated larva, not a miniature jellyfish.
        val planula=Path().apply{
            moveTo(w*.695f,h*.654f)
            cubicTo(w*.702f,h*.605f,w*.857f,h*.600f,w*.886f,h*.653f)
            cubicTo(w*.861f,h*.702f,w*.719f,h*.704f,w*.695f,h*.654f)
            close()
        }
        drawPath(planula,Color(0xFFF0EEE6))
        pencilPath(planula,1.45f)
        for(i in 0..17){
            val x=.700f+i*.010f
            val f=(i%4)*.003f
            pencilLine(p(x,.626f-f),p(x-.006f,.602f-f),.75f)
            pencilLine(p(x,.681f+f),p(x+.006f,.704f+f),.75f)
        }
        for(i in 0..18){
            val x=.720f+((i*19)%17)*.008f
            val y=.641f+((i*7)%13)*.003f
            pencilDot(p(x,y),.7f)
        }
        // Settlement leads to a newly founded polyp on its own substrate.
        pencilPath(Path().apply{
            moveTo(w*.669f,h*.919f)
            quadraticTo(w*.784f,h*.914f,w*.903f,h*.919f)
        },1.5f)
        pencilLine(p(.784f,.918f),p(.783f,.837f),1.85f)
        pencilPath(Path().apply{
            moveTo(w*.770f,h*.842f)
            quadraticTo(w*.786f,h*.812f,w*.800f,h*.842f)
        },1.0f)
        for(i in 0..7){
            val xx=.718f+i*.019f
            pencilLine(p(.785f,.824f),p(xx,.785f+(i%2)*.009f),.82f)
        }
        labelledFocus(progress,obeliaMarks)
    }
}
