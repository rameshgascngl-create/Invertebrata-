package com.gasczoology.invertebratelab.ui

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.NativeLessonDrafts
import com.gasczoology.invertebratelab.data.ParameciumLearningEngine
import com.gasczoology.invertebratelab.data.ParameciumProcess
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

private fun bi(lang: AppLanguage, en: String, ta: String) =
    if(lang == AppLanguage.TAMIL) ta else en

private data class AtlasNode(val id: String, val x: Float, val y: Float)
private fun nodes(panel: String): List<AtlasNode> = when(panel) {
    "oral" -> listOf(AtlasNode("oral-groove",.45f,.71f),
        AtlasNode("food-vacuole",.60f,.40f),AtlasNode("cytoproct",.76f,.70f))
    "vacuole" -> listOf(AtlasNode("contractile-vacuole",.32f,.40f),
        AtlasNode("food-vacuole",.65f,.64f))
    else -> listOf(AtlasNode("macronucleus",.48f,.44f),
        AtlasNode("micronucleus",.63f,.44f),
        AtlasNode("trichocysts",.29f,.26f),
        AtlasNode("food-vacuole",.34f,.64f))
}

/**
 * R1 native learning vertical slice. Geometry and Tamil scientific terms remain
 * DRAFT_UNVERIFIED. Simulated stage timing is illustrative, not measured kinetics.
 */
@Composable
fun ParameciumTeachingLab(language:AppLanguage,onBack:()->Unit,onPractice:()->Unit) {
    val context=LocalContext.current
    var ready by remember { mutableStateOf(false) }
    var speechStatus by remember { mutableStateOf("") }
    val speaker=remember(context) {
        TextToSpeech(context.applicationContext) { ready = it == TextToSpeech.SUCCESS }
    }
    DisposableEffect(speaker) { onDispose { speaker.stop(); speaker.shutdown() } }
    fun speak(script:BilingualText) {
        if(!ready) {
            speechStatus=bi(language,"Speech engine not ready; the full text remains on screen.",
                "ஒலிச் சேவை தயாரில்லை; விளக்கம் திரையில் உள்ளது.")
            return
        }
        val locale=Locale.forLanguageTag(if(language==AppLanguage.TAMIL)"ta-IN" else "en-IN")
        val code=speaker.setLanguage(locale)
        if(code==TextToSpeech.LANG_NOT_SUPPORTED || code==TextToSpeech.LANG_MISSING_DATA) {
            speechStatus=bi(language,"Requested voice is not installed on this device.",
                "தேவையான மொழிக் குரல் இந்தச் சாதனத்தில் நிறுவப்படவில்லை.")
        } else {
            speaker.speak(script.value(language),TextToSpeech.QUEUE_FLUSH,null,"R1-organ")
            speechStatus=bi(language,"Playing the scientific explanation",
                "அறிவியல் விளக்கம் ஒலிக்கிறது")
        }
    }
    var tab by rememberSaveable { mutableStateOf("study") }
    // Keep simulation state at the stable laboratory level. Subtree recreation
    // from TTS initialization or tab changes must not reset the current stage.
    var simulationId by rememberSaveable {
        mutableStateOf(ParameciumProcess.OSMOREGULATION.name)
    }
    var simulationStep by rememberSaveable { mutableIntStateOf(0) }
    var simulationPlaying by rememberSaveable { mutableStateOf(false) }
    Scaffold { insets ->
        Column(modifier=Modifier.padding(insets).verticalScroll(rememberScrollState())
            .padding(16.dp).testTag("r1-paramecium-lab"),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick=onBack, modifier=Modifier.heightIn(min=48.dp)) {
                Text(bi(language,"Back","பின்செல்"))
            }
            Text(bi(language,"PARAMECIUM — INTERACTIVE ZOOLOGY LAB",
                "பாரமீசியம் — ஊடாடும் விலங்கியல் ஆய்வகம்"))
            Text(bi(language,"Reference species: Paramecium caudatum. Teaching draft; diagram geometry and Tamil terminology are awaiting independent academic review.",
                "ஆய்வு இனம்: பாரமீசியம் கௌடேட்டம். கற்பித்தல் வரைவு; உடலமைப்பு இடங்களும் தமிழ் சொற்களும் தனி நிபுணர் மதிப்பாய்வுக்காகக் காத்திருக்கின்றன."),
                modifier=Modifier.testTag("r1-review-warning"))
            Row(modifier=Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement=Arrangement.spacedBy(6.dp)) {
                for(section in listOf("study","anatomy","simulate","listen","practice")) {
                    OutlinedButton(onClick={tab=section},
                        modifier=Modifier.heightIn(min=48.dp).testTag("r1-tab-"+section)) {
                        Text(when(section) {
                            "study" -> bi(language,"Study","பாடம்")
                            "anatomy" -> bi(language,"Anatomy","உடலமைப்பு")
                            "simulate" -> bi(language,"Simulate","இயக்கக் காட்சி")
                            "listen" -> bi(language,"Listen","ஒலிவிளக்கம்")
                            else -> bi(language,"Practice","பயிற்சி")
                        })
                    }
                }
            }
            when(tab) {
                "study" -> StudySection(language,::speak)
                "anatomy" -> AtlasSection(language,::speak)
                "simulate" -> SimulatorSection(
                    language, ::speak, simulationId, simulationStep, simulationPlaying,
                    onChoose = { newId ->
                        simulationId = newId
                        simulationStep = 0
                        simulationPlaying = false
                    },
                    onStep = { simulationStep = it },
                    onPlaying = { simulationPlaying = it },
                )
                "listen" -> NarrationSection(language,::speak)
                else -> {
                    Text(bi(language,"Use the preserved question bank for revision after studying the biology.",
                        "உயிரியலைக் கற்ற பின் ஏற்கெனவே உள்ள வினாவங்கியை மீள்பயிற்சிக்குப் பயன்படுத்தவும்."))
                    Button(onClick=onPractice,modifier=Modifier.heightIn(min=48.dp)
                        .fillMaxWidth().testTag("r1-open-practice")) {
                        Text(bi(language,"Open Paramecium practice","பாரமீசியம் வினாப் பயிற்சி"))
                    }
                }
            }
            if(speechStatus.isNotBlank())
                Text(speechStatus,modifier=Modifier.testTag("r1-speech-status"))
        }
    }
}

@Composable
private fun StudySection(language:AppLanguage,speak:(BilingualText)->Unit) {
    val lesson=NativeLessonDrafts.paramecium
    Text(lesson.title.value(language),modifier=Modifier.testTag("r1-study-title"))
    for(section in lesson.sections) {
        Column(modifier=Modifier.testTag("r1-study-"+section.id),
            verticalArrangement=Arrangement.spacedBy(6.dp)) {
            Text(section.heading.value(language))
            for(p in section.paragraphs) Text(p.value(language))
        }
    }
    Text(bi(language,"Explore organ functions:","உறுப்புகளின் செயல்பாடுகளை ஆராய்க:"))
    for(organ in ParameciumLearningEngine.organs) {
        Text(organ.name.value(language)+" — "+organ.function.value(language))
        OutlinedButton(onClick={speak(organ.narration)},
            modifier=Modifier.heightIn(min=48.dp).testTag("r1-study-voice-"+organ.id)) {
            Text(bi(language,"Hear explanation: ","விளக்கத்தைக் கேள்: ")+organ.name.value(language))
        }
    }
    Text(bi(language,"Scientific reading:","அறிவியல் மேற்கோள்கள்:"))
    for(source in lesson.sections.flatMap { it.scientificSources }.distinct()) Text(source)
}

@Composable
private fun NarrationSection(language:AppLanguage,speak:(BilingualText)->Unit) {
    Text(bi(language,"Organ-specific scientific narration","உறுப்பு சார்ந்த அறிவியல் ஒலிவிளக்கம்"),
        modifier=Modifier.testTag("r1-narration-heading"))
    for(organ in ParameciumLearningEngine.organs) {
        Text(organ.name.value(language))
        Text(organ.narration.value(language))
        Button(onClick={speak(organ.narration)},modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
            .testTag("r1-narrate-"+organ.id)) {
            Text(bi(language,"Play explanation","விளக்கத்தை ஒலிக்கச் செய்"))
        }
    }
    Text(bi(language,"Tamil speech requires an installed Tamil TTS voice. No network access is requested.",
        "தமிழ் ஒலிக்கு சாதனத்தில் தமிழ் உரை-ஒலி குரல் தேவை; இணைய அணுகல் கோரப்படவில்லை."))
}

@Composable
private fun AtlasSection(language:AppLanguage,speak:(BilingualText)->Unit) {
    var panel by rememberSaveable { mutableStateOf("external") }
    var selected by rememberSaveable { mutableStateOf("oral-groove") }
    Text(bi(language,"Explore anatomical plates","உடலமைப்புப் படங்களை ஆராய்க"),
        modifier=Modifier.testTag("r1-atlas-heading"))
    Row(modifier=Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement=Arrangement.spacedBy(6.dp)) {
        for(p in listOf("external","oral","vacuole","internal")) {
            OutlinedButton(onClick={panel=p;if(p!="external")selected=nodes(p).first().id},
                modifier=Modifier.heightIn(min=48.dp).testTag("r1-atlas-"+p)) {
                Text(when(p) {
                    "external" -> bi(language,"External/cilia","வெளிப்புறம்")
                    "oral" -> bi(language,"Oral feeding","உணவமைப்பு")
                    "vacuole" -> bi(language,"Vacuoles","நுண்குமிழ்கள்")
                    else -> bi(language,"Nuclei/internal","உட்கரு / உள்")
                })
            }
        }
    }
    if(panel=="external") {
        ParameciumExternalCanvas(language, onOrganSelected = { id ->
            speak(ParameciumLearningEngine.organ(id).narration)
        })
    } else {
        val points=nodes(panel)
        AtlasSketch(panel,points,selected,language,{ id ->
            selected=id
            speak(ParameciumLearningEngine.organ(id).narration)
        })
        val organ=ParameciumLearningEngine.organ(selected)
        Text(organ.name.value(language),modifier=Modifier.testTag("r1-atlas-selected"))
        Text(organ.narration.value(language),modifier=Modifier.testTag("r1-atlas-explanation"))
        OutlinedButton(onClick={speak(organ.narration)},modifier=Modifier.fillMaxWidth()
            .heightIn(min=48.dp).testTag("r1-atlas-speak")) {
            Text(bi(language,"Hear this organ","இந்த உறுப்பின் விளக்கத்தைக் கேள்"))
        }
        for(point in points) {
            OutlinedButton(onClick={
                selected=point.id
                speak(ParameciumLearningEngine.organ(point.id).narration)
            },modifier=Modifier.fillMaxWidth()
                .heightIn(min=48.dp).testTag("r1-atlas-select-"+point.id)) {
                Text(ParameciumLearningEngine.organ(point.id).name.value(language))
            }
        }
        Text(bi(language,"These positions are teaching schematics, not verified whole-cell microscopy coordinates.",
            "இவை கற்பித்தல் வரைபட இடங்கள் மட்டுமே; சரிபார்க்கப்பட்ட நுண்ணோக்கி ஆயத்தொலைவுகள் அல்ல."))
    }
}

@Composable
private fun AtlasSketch(panel:String, points:List<AtlasNode>, selected:String,
    language:AppLanguage, onSelect:(String)->Unit) {
    Canvas(modifier=Modifier.fillMaxWidth().height(270.dp).testTag("r1-atlas-canvas")
        .semantics { contentDescription=bi(language,
            "Illustrative organ map; accessible selection buttons follow",
            "உறுப்பு வரைபட முன்மாதிரி; கீழுள்ள பொத்தான்கள் மூலம் தேர்வு செய்யலாம்") }
        .pointerInput(panel,onSelect) {
            detectTapGestures { tap ->
                val x=tap.x/size.width.toFloat();val y=tap.y/size.height.toFloat()
                val near=points.minByOrNull { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y) }
                if(near!=null && (x-near.x)*(x-near.x)+(y-near.y)*(y-near.y)<.0225f)
                    onSelect(near.id)
            }
        }) {
        val w=size.width;val h=size.height
        drawRect(Color(0xFFF4FBFA))
        parameciumCell(Offset(w*.5f,h*.5f),w*.80f,h*.76f)
        when(panel) {
            "oral" -> {
                drawLine(Color(0xFF3185A5),Offset(w*.39f,h*.77f),Offset(w*.48f,h*.59f),7f)
                drawLine(Color(0xFF3185A5),Offset(w*.48f,h*.59f),Offset(w*.56f,h*.51f),7f)
                drawCircle(Color(0xFF5BB7C6),radius=h*.10f,center=Offset(w*.60f,h*.4f))
            }
            "vacuole" -> for(pos in listOf(Offset(w*.32f,h*.40f),
                Offset(w*.70f,h*.62f))) {
                drawCircle(Color(0xFF2386A7),radius=h*.10f,center=pos,style=Stroke(5f))
                for(j in 0 until 6) {
                    val angle=j*2*PI/6
                    val delta=Offset(cos(angle).toFloat(),sin(angle).toFloat())
                    drawLine(Color(0xFF56A6B9),pos+delta*(h*.12f),
                        pos+delta*(h*.20f),3f)
                }
            }
            else -> {
                drawOval(Color(0xFFACA4D4),topLeft=Offset(w*.37f,h*.33f),
                    size=Size(w*.25f,h*.23f))
                drawCircle(Color(0xFF745DA7),radius=h*.05f,center=Offset(w*.63f,h*.44f))
                drawCircle(Color(0xFF57AFC8),radius=h*.10f,center=Offset(w*.34f,h*.64f))
            }
        }
        for(p in points) {
            drawCircle(if(p.id==selected)Color(0xFFB55B16)else Color(0xFF11787B),
                radius=if(p.id==selected)14f else 8f,
                center=Offset(p.x*w,p.y*h),style=Stroke(4f))
        }
    }
}

@Composable
private fun SimulatorSection(
    language:AppLanguage,
    speak:(BilingualText)->Unit,
    chosen:String,
    step:Int,
    playing:Boolean,
    onChoose:(String)->Unit,
    onStep:(Int)->Unit,
    onPlaying:(Boolean)->Unit,
) {
    val process=ParameciumProcess.valueOf(chosen)
    val model=ParameciumLearningEngine.simulation(process)
    LaunchedEffect(playing,chosen,step) {
        if(playing) {
            delay(1600L)
            if(step < model.stages.lastIndex) onStep(step + 1) else onPlaying(false)
        }
    }
    val stage=model.stages[step]
    val progress by animateFloatAsState(stage.illustrationProgress,
        animationSpec=tween(700),label="r1-stage-animation")
    Text(bi(language,"Live teaching simulations","இயங்கும் கற்பித்தல் செயல்முறைகள்"),
        modifier=Modifier.testTag("r1-simulator-heading"))
    for(p in ParameciumProcess.entries) {
        OutlinedButton(onClick={onChoose(p.name)},modifier=Modifier.fillMaxWidth()
            .heightIn(min=48.dp).testTag("r1-process-"+p.name.lowercase())) {
            Text(ParameciumLearningEngine.simulation(p).title.value(language))
        }
    }
    Text(model.title.value(language))
    ProcessSketch(process,progress,language)
    Text(bi(language,"Stage ","நிலை ")+(step+1).toString()+"/"+
        model.stages.size.toString()+" — "+stage.heading.value(language),
        modifier=Modifier.testTag("r1-stage-title"))
    Text(stage.explanation.value(language),modifier=Modifier.testTag("r1-stage-explanation"))
    Row(modifier=Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement=Arrangement.spacedBy(6.dp)) {
        OutlinedButton(onClick={onStep(model.previous(step));onPlaying(false)},
            modifier=Modifier.heightIn(min=48.dp).testTag("r1-prev")) {
            Text(bi(language,"Previous","முந்தையது"))
        }
        Button(onClick={
            if(step==model.stages.lastIndex)onStep(0)
            onPlaying(!playing)
        },
            modifier=Modifier.heightIn(min=48.dp).testTag("r1-play")) {
            Text(if(playing)bi(language,"Pause","இடைநிறுத்து")
                else bi(language,"Play","இயக்கு"))
        }
        OutlinedButton(onClick={onStep(model.advance(step));onPlaying(false)},
            modifier=Modifier.heightIn(min=48.dp).testTag("r1-next")) {
            Text(bi(language,"Next","அடுத்தது"))
        }
        OutlinedButton(onClick={onStep(0);onPlaying(false)},
            modifier=Modifier.heightIn(min=48.dp).testTag("r1-replay")) {
            Text(bi(language,"Replay","மீளியக்கு"))
        }
    }
    Button(onClick={speak(stage.explanation)},modifier=Modifier.fillMaxWidth()
        .heightIn(min=48.dp).testTag("r1-stage-audio")) {
        Text(bi(language,"Speak this physiological stage",
            "இந்நிலையின் உடலியங்கியலை ஒலிக்கச் செய்"))
    }
    Text(model.teachingCaution.value(language),modifier=Modifier.testTag("r1-simulation-caution"))
    Text(model.scientificSource)
    Checkpoint(process,language)
}

@Composable
private fun Checkpoint(process:ParameciumProcess,language:AppLanguage) {
    var answer by rememberSaveable(process.name) { mutableIntStateOf(-1) }
    val statement=when(process) {
        ParameciumProcess.OSMOREGULATION -> BilingualText(
            "The contractile vacuole primarily regulates excess water, not digestion.",
            "சுருங்கும் நுண்குமிழ் மிகைநீரைச் சீராக்குகிறது; உணவு செரிப்பதில்லை.")
        ParameciumProcess.CILIARY_MOTION -> BilingualText(
            "Coordinated cilia contribute to Paramecium locomotion.",
            "ஒருங்கிணைந்த குறுஇழைகள் பாரமீசியம் நகர உதவுகின்றன.")
        ParameciumProcess.FEEDING -> BilingualText(
            "Food vacuoles form only when two Paramecium cells conjugate.",
            "இரண்டு பாரமீசியம் செல்கள் இணையும் போது மட்டுமே உணவுக் குமிழ்கள் உருவாகின்றன.")
        ParameciumProcess.BINARY_FISSION -> BilingualText(
            "Transverse binary fission increases the number of cells.",
            "குறுக்குத் திசை இருபிளவு செல் எண்ணிக்கையை அதிகரிக்கிறது.")
        ParameciumProcess.CONJUGATION -> BilingualText(
            "Conjugation immediately increases cell number like binary fission.",
            "இணைவு இருபிளவைப் போலவே உடனடியாக செல் எண்ணிக்கையை அதிகரிக்கிறது.")
    }
    val correct=process!=ParameciumProcess.FEEDING && process!=ParameciumProcess.CONJUGATION
    Text(bi(language,"Think & check: true or false?","சிந்தித்து சரிபார்: சரியா, தவறா?"))
    Text(statement.value(language))
    Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick={answer=1},modifier=Modifier.heightIn(min=48.dp)
            .testTag("r1-answer-true")) { Text(bi(language,"True","சரி")) }
        OutlinedButton(onClick={answer=0},modifier=Modifier.heightIn(min=48.dp)
            .testTag("r1-answer-false")) { Text(bi(language,"False","தவறு")) }
    }
    if(answer>=0) Text(if((answer==1)==correct)
        bi(language,"Correct. Your answer matches the biological explanation.",
            "சரி. உங்கள் விடை உயிரியல் விளக்கத்துடன் பொருந்துகிறது.")
        else bi(language,"Revisit the stage sequence and try again.",
            "நிலைகளை மீண்டும் படித்து முயலவும்."),
        modifier=Modifier.testTag("r1-checkpoint-feedback"))
}

@Composable
private fun ProcessSketch(process:ParameciumProcess,progress:Float,language:AppLanguage) {
    Canvas(modifier=Modifier.fillMaxWidth().height(260.dp)
        .testTag("r1-process-canvas")
        .semantics { contentDescription=bi(language,
            "Schematic visualization changes with stage; read the stage explanation below",
            "நிலைக்கு ஏற்ப மாறும் விளக்க வரைபடம்; நிலை விளக்கம் கீழே உள்ளது") }) {
        val w=size.width;val h=size.height;val center=Offset(w*.5f,h*.5f)
        drawRect(Color(0xFFF4FBFA))
        if(process==ParameciumProcess.CONJUGATION) {
            parameciumCell(Offset(w*.5f,h*.34f),w*.60f,h*.26f)
            parameciumCell(Offset(w*.5f,h*.67f),w*.60f,h*.26f)
            drawCircle(Color(0xFF765DA7),radius=11f,center=Offset(w*.45f,h*.33f))
            drawCircle(Color(0xFF765DA7),radius=11f,center=Offset(w*.55f,h*.67f))
            if(progress>.30f)drawLine(Color(0xFFB55B16),
                Offset(w*.46f,h*.46f),Offset(w*.54f,h*.55f),5f)
            if(progress>.65f) {
                drawCircle(Color(0xFFB55B16),radius=8f,center=Offset(w*.54f,h*.33f))
                drawCircle(Color(0xFFB55B16),radius=8f,center=Offset(w*.46f,h*.67f))
            }
        } else if(process==ParameciumProcess.BINARY_FISSION) {
            if(progress>=.99f) {
                parameciumCell(Offset(w*.29f,h*.50f),w*.36f,h*.43f)
                parameciumCell(Offset(w*.71f,h*.50f),w*.36f,h*.43f)
            } else {
                parameciumCell(center,w*.73f,h*.64f)
                drawCircle(Color(0xFF765DA7),radius=13f,center=Offset(w*.40f,h*.50f))
                if(progress>.30f)drawCircle(Color(0xFF765DA7),radius=13f,
                    center=Offset(w*.61f,h*.50f))
                if(progress>.60f)drawLine(Color(0xFFB55B16),
                    Offset(w*.5f,h*(.17f+progress*.12f)),
                    Offset(w*.5f,h*(.83f-progress*.12f)),5f)
            }
        } else {
            parameciumCell(center,w*.73f,h*.64f)
            when(process) {
                ParameciumProcess.OSMOREGULATION -> {
                    val radius=9f+progress*20f
                    for(v in listOf(Offset(w*.32f,h*.39f),Offset(w*.70f,h*.60f))) {
                        drawCircle(Color(0xFF1C85A7),radius=if(progress>=1f)7f else radius,
                            center=v,style=Stroke(4f))
                        for(k in 0 until 6) {
                            val a=k*2*PI/6
                            val d=Offset(cos(a).toFloat(),sin(a).toFloat())
                            drawLine(Color(0xFF63AFC3),v+d*(radius+2f),
                                v+d*(radius+19f),3f)
                        }
                    }
                    if(progress>=1f)drawCircle(Color(0xFF1C85A7),radius=8f,
                        center=Offset(w*.88f,h*.31f))
                }
                ParameciumProcess.CILIARY_MOTION -> {
                    for(k in 0 until 34) {
                        val a=2*PI*k/34
                        val d=Offset(cos(a).toFloat(),sin(a).toFloat())
                        val p=center+Offset(d.x*w*.365f,d.y*h*.32f)
                        val swing=sin(progress*2*PI+k*.34).toFloat()*14f
                        drawLine(Color(0xFF176F7F),p,
                            p+Offset(d.x*16f-d.y*swing,d.y*16f+d.x*swing),3f)
                    }
                    drawLine(Color(0xFFB55B16),Offset(w*.37f,h*.51f),
                        Offset(w*.62f,h*.51f),4f)
                }
                ParameciumProcess.FEEDING -> {
                    drawLine(Color(0xFF147C99),Offset(w*.43f,h*.77f),
                        Offset(w*.56f,h*.52f),6f)
                    val x=if(progress<.3f)w*(.40f+progress*.50f)
                        else if(progress<.7f)w*(.55f+(progress-.3f)*.2f)
                        else w*(.63f+(progress-.7f)*.68f)
                    val y=if(progress<.3f)h*(.75f-progress*.6f)
                        else if(progress<.7f)h*(.57f-(progress-.3f)*.4f)
                        else h*(.41f+(progress-.7f)*.9f)
                    drawCircle(Color(0xFFB55B16),radius=11f,center=Offset(x,y))
                    drawCircle(Color(0xFF288EB2),radius=19f,
                        center=Offset(w*.62f,h*.42f),style=Stroke(4f))
                }
                else -> Unit
            }
        }
    }
}

private fun DrawScope.parameciumCell(center:Offset,w:Float,h:Float) {
    drawOval(Color(0xFFD2F0E8),topLeft=Offset(center.x-w/2,center.y-h/2),
        size=Size(w,h))
    drawOval(Color(0xFF146E76),topLeft=Offset(center.x-w/2,center.y-h/2),
        size=Size(w,h),style=Stroke(4f))
}
