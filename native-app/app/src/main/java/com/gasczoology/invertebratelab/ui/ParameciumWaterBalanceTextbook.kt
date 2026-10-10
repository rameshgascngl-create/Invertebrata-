package com.gasczoology.invertebratelab.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gasczoology.invertebratelab.WaterBalancePlaybackViewModel
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

private fun waterText(lang: AppLanguage,en: String,ta: String) = if(lang==AppLanguage.TAMIL) ta else en

@Composable
internal fun ParameciumWaterBalanceTextbook(language: AppLanguage,speak: (BilingualText)->Unit,
    initialProgress: WaterBalanceProgress,onProgress: (WaterBalanceProgress)->Unit) {
    var progress by remember { mutableStateOf(initialProgress.normalized()) }
    val persist by rememberUpdatedState(onProgress)
    fun change(next: WaterBalanceProgress) { progress=next;persist(next) }
    val index=ParameciumWaterBalance.stages.indexOfFirst { it.id==progress.stageId }
    val stage=ParameciumWaterBalance.stages[index]
    val reading=ParameciumWaterBalance.readings.first { it.id==progress.readingId }
    val player: WaterBalancePlaybackViewModel = viewModel()
    val playback by player.state.collectAsState()
    LaunchedEffect(stage.id) { player.restore(progress.phasePermille) }
    val lifecycle=LocalLifecycleOwner.current.lifecycle
    DisposableEffect(player,lifecycle) {
        val observer=LifecycleEventObserver { _,event -> if(event==Lifecycle.Event.ON_STOP) {
            player.pause();change(progress.copy(phasePermille=(player.state.value.phase*1000).toInt()))
        } }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer);player.pause() }
    }
    fun chooseStage(id: String) { player.rewind();change(progress.copy(stageId=id,phasePermille=0)) }
    fun chooseStructure(id: String) { change(progress.copy(selectedStructure=id));speak(ParameciumWaterBalance.structure(id).explanation) }
    fun advance(): Boolean {
        val current=ParameciumWaterBalance.stages.indexOfFirst { it.id==progress.stageId }
        if(current==ParameciumWaterBalance.stages.lastIndex) return false
        change(progress.copy(stageId=ParameciumWaterBalance.stages[current+1].id,phasePermille=0));return true
    }
    val shape=RoundedCornerShape(12.dp)
    val scope=rememberCoroutineScope()
    val requester=remember { BringIntoViewRequester() }
    Text(waterText(language,"WATER BALANCE · Paramecium caudatum","நீர்ச் சமநிலை · Paramecium caudatum"),
        style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,
        modifier=Modifier.testTag("r18-textbook-title").semantics { heading() })
    Text(ParameciumWaterBalance.reviewStatus+" · "+waterText(language,
        "Original pencil schematics, not to scale. Comparative ultrastructure is labelled; specialist approval pending.",
        "அசல் பென்சில் விளக்கப்படங்கள்; அளவுக்கு ஏற்ப அல்ல. ஒப்பீட்டு நுண்ணமைப்பு குறிக்கப்பட்டுள்ளது; நிபுணர் ஒப்புதல் நிலுவை."),
        modifier=Modifier.testTag("r18-review-status"))
    Text("R1.8 · ${progress.stageId} · ${progress.readingId} · ${progress.selectedStructure}",
        modifier=Modifier.testTag("r18-module-position"))
    Card(colors=CardDefaults.cardColors(containerColor=Color(0xFFFFFDF8))) {
        Column(Modifier.fillMaxWidth().padding(12.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            for(item in ParameciumWaterBalance.readings) {
                OutlinedButton(onClick={change(progress.copy(readingId=item.id))},shape=shape,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-reading-"+item.id)
                        .semantics { selected=item.id==reading.id }) { Text(item.heading.value(language)) }
            }
            Text(reading.heading.value(language),style=MaterialTheme.typography.titleLarge,
                modifier=Modifier.testTag("r18-reading-heading").semantics { heading() })
            reading.paragraphs.forEachIndexed { i,p -> Text(p.value(language),style=MaterialTheme.typography.bodyLarge,
                modifier=Modifier.testTag("r18-paragraph-"+i)) }
            OutlinedButton(onClick={speak(BilingualText(reading.paragraphs.joinToString("\n\n") { it.english },
                reading.paragraphs.joinToString("\n\n") { it.tamil }))},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-narrate-reading")) {
                Text(waterText(language,"Hear the complete subsection","முழுப் பாடப்பகுதி விளக்கத்தைக் கேள்"))
            }
        }
    }
    Card(colors=CardDefaults.cardColors(containerColor=Color(0xFFFAF8F3))) {
        Column(Modifier.fillMaxWidth().padding(12.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(waterText(language,"View ","காட்சி ")+"${index+1}/4 — "+stage.heading.value(language),
                style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold,
                modifier=Modifier.testTag("r18-stage-title").semantics { heading() })
            Button(onClick={scope.launch { requester.bringIntoView() }},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-view-plate")) {
                Text(waterText(language,"Bring the complete pencil plate into view","முழுப் பென்சில் படத்தைத் திரையில் காண்க"))
            }
            Canvas(Modifier.fillMaxWidth().height(240.dp).bringIntoViewRequester(requester).testTag("r18-water-canvas")
                .pointerInput(stage.id,progress.selectedStructure) { detectTapGestures { p ->
                    val f=WaterBalanceFigure.fit(size.width.toFloat(),size.height.toFloat())
                    WaterBalanceFigure.at((p.x-f.dx)/f.scale,(p.y-f.dy)/f.scale)?.let(::chooseStructure)
                } }.semantics {
                    contentDescription=waterText(language,
                        "Original pencil cutaway: one representative contractile-vacuole complex, cortex and discharge pore. Two whole-cell positions are static locators, not synchronized pulses. Separate comparative tubule inset. Current view: ",
                        "அசல் பென்சில் வெட்டுத் தோற்றம்: ஒரு பிரதிநிதிச் சுருங்கு நுண்குமிழ்த் தொகுப்பு, செல்புறப் படலம், வெளியேற்றுத் துளை. முழுச் செல்லில் இரு இடக் குறிகள்; ஒரே நேரத் துடிப்புகள் அல்ல. தனி ஒப்பீட்டுக் குழாய்ச் சிறுபடம். தற்போதைய காட்சி: ")+stage.heading.value(language)+". "+stage.explanation.value(language)
                    stateDescription=ParameciumWaterBalance.structure(progress.selectedStructure).name.value(language)+
                        waterText(language," highlighted"," சிறப்பிக்கப்பட்டது")
                    progressBarRangeInfo=ProgressBarRangeInfo(playback.phase,0f..1f)
                    customActions=ParameciumWaterBalance.structures.map { item -> CustomAccessibilityAction(item.name.value(language)) { chooseStructure(item.id);true } }
                }) { drawWaterBalancePencil(stage.id,progress.selectedStructure,playback.phase) }
            Text(waterText(language,
                "C reservoir · A ampulla · R collecting canal · S smooth spongiome · D decorated spongiome · P pore. Upper-right: two P. caudatum positions only. Lower-right: separately enlarged P. multimicronucleatum tubules. Representative arms; discontinuity marks omit changing connections. Blue flow is a teaching cue, not a stain or measured water path.",
                "C மைய நுண்குமிழ் · A விரிந்த பகுதி · R சேகரிப்புக் கால்வாய் · S மென்மையான குழாய் வலை · D துருத்தக் குழாய் வலை · P துளை. மேல் வலது: P. caudatum இன் இரு இடங்கள் மட்டும். கீழ் வலது: தனியாகப் பெரிதாக்கிய P. multimicronucleatum குழாய்கள். பிரதிநிதிக் கிளைகள்; முறிவுக் குறிகள் மாறும் இணைப்புகளை விடுகின்றன. நீல ஓட்டம் கற்பித்தல் குறி; சாயமோ அளவிடப்பட்ட நீர்ப் பாதையோ அல்ல."),
                modifier=Modifier.testTag("r18-plate-legend"))
            for(item in ParameciumWaterBalance.structures) {
                OutlinedButton(onClick={chooseStructure(item.id)},shape=shape,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-select-"+item.id)
                        .semantics { selected=progress.selectedStructure==item.id }) { Text(item.name.value(language)) }
            }
            Text(ParameciumWaterBalance.structure(progress.selectedStructure).explanation.value(language),
                style=MaterialTheme.typography.bodyLarge,modifier=Modifier.testTag("r18-organ-explanation"))
            OutlinedButton(onClick={speak(ParameciumWaterBalance.structure(progress.selectedStructure).explanation)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-narrate-organ")) {
                Text(waterText(language,"Replay the selected structure explanation","தேர்ந்தெடுத்த அமைப்பின் விளக்கத்தை மீண்டும் கேள்"))
            }
            Text(stage.explanation.value(language),style=MaterialTheme.typography.bodyLarge,modifier=Modifier.testTag("r18-stage-explanation"))
            for((i,item) in ParameciumWaterBalance.stages.withIndex()) {
                OutlinedButton(onClick={chooseStage(item.id)},shape=shape,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-stage-"+item.id)
                        .semantics { selected=item.id==stage.id }) { Text("${i+1}. "+item.heading.value(language)) }
            }
            Text(waterText(language,"Illustrative speed and proportions; real events overlap. Restored playback remains paused.",
                "கற்பித்தலுக்கான வேகமும் விகிதங்களும்; இயற்கை நிகழ்வுகள் ஒன்றோடொன்று அமையும். மீட்டமைந்த இயக்கம் இடைநிறுத்தத்தில் இருக்கும்."))
            Text(waterText(language,if(playback.playing)"Playing" else "Paused",if(playback.playing)"இயங்குகிறது" else "இடைநிறுத்தம்"),
                modifier=Modifier.testTag("r18-playback-state"))
            Button(onClick={if(playback.playing) {player.pause();change(progress.copy(phasePermille=(player.state.value.phase*1000).toInt()))}
                else player.play(::advance)},enabled=!progress.reducedMotion,shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-play-pause")) {
                Text(waterText(language,if(playback.playing)"Pause" else "Play",if(playback.playing)"இடைநிறுத்து" else "இயக்கு"))
            }
            OutlinedButton(onClick={chooseStage(ParameciumWaterBalance.stages[(index-1).coerceAtLeast(0)].id)},enabled=index>0,shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-previous")) { Text(waterText(language,"Previous view","முந்தைய காட்சி")) }
            OutlinedButton(onClick={chooseStage(ParameciumWaterBalance.stages[(index+1).coerceAtMost(3)].id)},enabled=index<3,shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-next")) { Text(waterText(language,"Next view","அடுத்த காட்சி")) }
            OutlinedButton(onClick={chooseStage("osmosis")},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-reset")) { Text(waterText(language,"Replay from the beginning","தொடக்கத்திலிருந்து மீண்டும் இயக்கு")) }
            OutlinedButton(onClick={player.pause();change(progress.copy(reducedMotion=!progress.reducedMotion,
                phasePermille=(player.state.value.phase*1000).toInt()))},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-reduced-motion")
                    .semantics { selected=progress.reducedMotion }) {
                Text(waterText(language,"Reduced motion: ","குறைக்கப்பட்ட இயக்கம்: ")+waterText(language,
                    if(progress.reducedMotion)"On" else "Off",if(progress.reducedMotion)"செயலில்" else "நிறுத்தம்"))
            }
            OutlinedButton(onClick={speak(stage.explanation)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r18-narrate-stage")) {
                Text(waterText(language,"Hear this stage's complete explanation","இந்நிலையின் முழு விளக்கத்தைக் கேள்"))
            }
        }
    }
    Text(waterText(language,"Scientific sources and scope","அறிவியல் ஆதாரங்களும் வரம்பும்"),style=MaterialTheme.typography.titleLarge,modifier=Modifier.semantics { heading() })
    ParameciumWaterBalance.sourceNotes.forEach { Text(it,style=MaterialTheme.typography.bodyMedium) }
}

/** Original graphite-like linework. Insets and qualitative blue flow are explicitly identified in native text. */
private fun DrawScope.drawWaterBalancePencil(stage: String,selected: String,phase: Float) {
    val fit=WaterBalanceFigure.fit(size.width,size.height)
    val ink=Color(0xFF4A4844);val faint=ink.copy(alpha=.30f);val blue=Color(0xFF457F95);val accent=Color(0xFF966124)
    val open=stage=="expel"
    fun label(s: String,x: Float,y: Float) { drawContext.canvas.nativeCanvas.drawText(s,x,y,Paint().apply { color=android.graphics.Color.rgb(61,58,52);textSize=26f;isAntiAlias=true;isFakeBoldText=true }) }
    fun graphite(a: Offset,b: Offset,w: Float=2f) { drawLine(ink,a,b,w);drawLine(faint,a+Offset(1.5f,1f),b+Offset(1f,2f),1f) }
    withTransform({translate(fit.dx,fit.dy);scale(fit.scale,fit.scale,pivot=Offset.Zero)}) {
        drawRect(Color(0xFFFFFDF8),size=Size(1000f,540f))
        // One expanded complex. The outlet route is illustrative, not a reconstructed membrane neck.
        graphite(Offset(55f,110f),Offset(327f,110f),3f);graphite(Offset(353f,110f),Offset(625f,110f),3f)
        graphite(Offset(55f,119f),Offset(327f,119f));graphite(Offset(353f,119f),Offset(625f,119f))
        if(!open) graphite(Offset(327f,110f),Offset(353f,110f),4f)
        for(y in 140..220 step 18) { drawLine(faint,Offset(332f,y.toFloat()),Offset(332f,y+9f),2f);drawLine(faint,Offset(348f,y.toFloat()),Offset(348f,y+9f),2f) }
        label("P",365f,102f)
        val radius=48f+72f*ParameciumWaterBalance.lumen(stage,phase)
        drawCircle(blue.copy(alpha=.10f),radius,Offset(340f,320f))
        drawCircle(ink,radius,Offset(340f,320f),style=Stroke(3f))
        drawCircle(faint,radius+3f,Offset(339f,321f),style=Stroke(1.5f))
        for(i in 0..20) { val angle=i*6.28318f/21;val p=Offset(340f+cos(angle)*(radius+5f),320f+sin(angle)*(radius+5f));
            drawLine(faint,p,p+Offset(cos(angle)*8f,sin(angle)*8f),1.5f) }
        label("C",326f,328f)
        // Representative arms, not a claimed anatomical count. Breaks avoid drawing permanent patency.
        for(angle in listOf(0f,45f,135f,180f)) {
            val rad=angle*.0174533f;val direction=Offset(cos(rad),sin(rad));val perpendicular=Offset(-sin(rad),cos(rad))
            val start=Offset(340f,320f)+direction*120f;val end=Offset(340f,320f)+direction*235f
            graphite(start+perpendicular*8f,end+perpendicular*8f)
            graphite(start-perpendicular*8f,end-perpendicular*8f)
            val amp=Offset(340f,320f)+direction*135f
            drawOval(Color(0xFFFFFDF8),amp-Offset(28f,19f),Size(56f,38f))
            drawOval(ink,amp-Offset(28f,19f),Size(56f,38f),style=Stroke(2f))
            graphite(start-direction*15f-perpendicular*11f,start-direction*5f+perpendicular*11f)
            graphite(start-direction*6f-perpendicular*11f,start+direction*4f+perpendicular*11f)
            for(i in 3..6) {
                val center=Offset(340f,320f)+direction*(135f+i*15f)
                drawOval(faint,center+perpendicular*17f-Offset(10f,10f),Size(20f,20f),style=Stroke(1.3f))
                drawOval(faint,center-perpendicular*17f-Offset(10f,10f),Size(20f,20f),style=Stroke(1.3f))
            }
            if(stage=="collect" || stage=="fill") { val t=1f-phase;val p=start+(end-start)*t;drawCircle(blue,5f,p) }
        }
        label("A",465f,293f);label("R",557f,294f)
        if(open) { val y=218f-165f*phase;drawCircle(blue,6f,Offset(340f,y));
            drawLine(blue,Offset(340f,95f),Offset(340f,52f),3f);drawLine(blue,Offset(340f,52f),Offset(330f,66f),3f);drawLine(blue,Offset(340f,52f),Offset(350f,66f),3f) }
        // Static whole-cell locator; no animation phase enters either vacuole.
        val body=Path().apply { moveTo(735f,146f);cubicTo(747f,59f,882f,62f,971f,146f);cubicTo(915f,245f,743f,234f,735f,146f);close() }
        drawPath(body,ink,style=Stroke(2.2f));drawOval(faint,Offset(827f,117f),Size(36f,52f),style=Stroke(2f))
        for(x in 760..940 step 15) { graphite(Offset(x.toFloat(),88f),Offset(x-4f,78f),1f);graphite(Offset(x.toFloat(),208f),Offset(x+3f,220f),1f) }
        for(x in listOf(788f,906f)) { drawCircle(ink,14f,Offset(x,151f),style=Stroke(1.7f));for(a in 0..5) { val r=a*1.0472f;graphite(Offset(x+cos(r)*15,151+sin(r)*15),Offset(x+cos(r)*25,151+sin(r)*25),1f) } }
        label("1",781f,155f);label("2",899f,155f)
        // Comparative radial-arm inset: smooth tubules near canal, decorated tubules peripheral, never around ampulla.
        drawRoundRect(faint,Offset(695f,280f),Size(285f,235f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(8f,8f),style=Stroke(1.5f))
        drawOval(ink,Offset(708f,410f),Size(57f,40f),style=Stroke(2.5f))
        graphite(Offset(765f,422f),Offset(960f,422f));graphite(Offset(765f,438f),Offset(960f,438f))
        for(x in 792..938 step 22) {
            val smooth=Path().apply { moveTo(x.toFloat(),422f);cubicTo(x-20f,408f,x-10f,392f,x+5f,393f);cubicTo(x+27f,392f,x+20f,410f,x+13f,421f) }
            drawPath(smooth,ink,style=Stroke(1.7f))
            val lower=Path().apply { moveTo(x.toFloat(),438f);cubicTo(x-20f,455f,x-10f,470f,x+5f,469f);cubicTo(x+27f,470f,x+20f,451f,x+13f,439f) }
            drawPath(lower,ink,style=Stroke(1.7f))
            graphite(Offset(x.toFloat(),371f),Offset(x+17f,371f),2.5f);graphite(Offset(x.toFloat(),488f),Offset(x+17f,488f),2.5f)
            graphite(Offset(x+8f,371f),Offset(x+5f,394f),1f);graphite(Offset(x+8f,488f),Offset(x+5f,468f),1f)
            for(dx in 0..16 step 8) { graphite(Offset(x+dx.toFloat(),371f),Offset(x+dx.toFloat(),360f),1.5f);drawCircle(ink,3f,Offset(x+dx.toFloat(),357f));
                graphite(Offset(x+dx.toFloat(),488f),Offset(x+dx.toFloat(),499f),1.5f);drawCircle(ink,3f,Offset(x+dx.toFloat(),502f)) }
        }
        label("A",720f,405f);label("R",939f,416f);label("S",850f,407f);label("D",850f,348f)
        // Non-colour cues: double ring and pointer at the selected marked anatomical region.
        WaterBalanceFigure.marks.first { it.id==selected }.let { m ->
            val r=if(selected=="reservoir") radius+12f else if(selected=="ampulla")36f else 29f
            drawCircle(accent.copy(alpha=.09f),r,Offset(m.x,m.y));drawCircle(accent,r,Offset(m.x,m.y),style=Stroke(4f));drawCircle(ink,r+5f,Offset(m.x,m.y),style=Stroke(1.5f))
            drawLine(ink,Offset(m.x+r+6f,m.y),Offset(m.x+r+23f,m.y-14f),2f)
        }
    }
}
