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
import com.gasczoology.invertebratelab.NutritionPlaybackViewModel
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

private fun nutritionText(lang: AppLanguage,en: String,ta: String) = if(lang==AppLanguage.TAMIL) ta else en

@Composable
internal fun ParameciumNutritionTextbook(language: AppLanguage,speak: (BilingualText)->Unit,
    initialProgress: NutritionProgress,onProgress: (NutritionProgress)->Unit) {
    var progress by remember { mutableStateOf(initialProgress.normalized()) }
    val persist by rememberUpdatedState(onProgress)
    fun change(next: NutritionProgress) { progress=next;persist(next) }
    val index=ParameciumNutrition.stages.indexOfFirst { it.id==progress.stageId }
    val stage=ParameciumNutrition.stages[index]
    val reading=ParameciumNutrition.readings.first { it.id==progress.readingId }
    val player: NutritionPlaybackViewModel = viewModel()
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
    fun chooseStage(id: String,completed: Boolean = true) {
        player.rewind()
        val pose=if(completed)1000 else 0
        player.restore(pose)
        change(progress.copy(stageId=id,phasePermille=pose))
    }
    fun choosePose(completed: Boolean) {
        player.pause()
        val pose=if(completed)1000 else 0
        player.restore(pose)
        change(progress.copy(phasePermille=pose))
    }
    fun chooseStructure(id: String) { change(progress.copy(selectedStructure=id));speak(ParameciumNutrition.structure(id).explanation) }
    val shape=RoundedCornerShape(12.dp)
    val scope=rememberCoroutineScope()
    val requester=remember { BringIntoViewRequester() }
    Text(nutritionText(language,"NUTRITION · Paramecium caudatum","ஊட்டமுறை · Paramecium caudatum"),
        style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,
        modifier=Modifier.testTag("r19-textbook-title").semantics { heading() })
    Text(ParameciumNutrition.reviewStatus+" · "+nutritionText(language,
        "Original pencil schematics, not to scale. Whole-cell orientation and enlarged compartments are distinct; specialist approval pending.",
        "அசல் பென்சில் விளக்கப்படங்கள்; அளவுக்கு ஏற்ப அல்ல. முழுச் செல் திசையும் பெரிதாக்கிய பகுதிகளும் வேறுபடுத்தப்பட்டுள்ளன; நிபுணர் ஒப்புதல் நிலுவை."),
        modifier=Modifier.testTag("r19-review-status"))
    Text("R1.9 · ${progress.stageId} · ${progress.readingId} · ${progress.selectedStructure}",
        modifier=Modifier.testTag("r19-module-position"))
    Card(colors=CardDefaults.cardColors(containerColor=Color(0xFFFFFDF8))) {
        Column(Modifier.fillMaxWidth().padding(12.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            for(item in ParameciumNutrition.readings) {
                OutlinedButton(onClick={change(progress.copy(readingId=item.id))},shape=shape,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-reading-"+item.id)
                        .semantics { selected=item.id==reading.id }) { Text(item.heading.value(language)) }
            }
            Text(reading.heading.value(language),style=MaterialTheme.typography.titleLarge,
                modifier=Modifier.testTag("r19-reading-heading").semantics { heading() })
            reading.paragraphs.forEachIndexed { i,p -> Text(p.value(language),style=MaterialTheme.typography.bodyLarge,
                modifier=Modifier.testTag("r19-paragraph-"+i)) }
            OutlinedButton(onClick={speak(BilingualText(reading.paragraphs.joinToString("\n\n") { it.english },
                reading.paragraphs.joinToString("\n\n") { it.tamil }))},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-narrate-reading")) {
                Text(nutritionText(language,"Hear the complete subsection","முழுப் பாடப்பகுதி விளக்கத்தைக் கேள்"))
            }
        }
    }
    Card(colors=CardDefaults.cardColors(containerColor=Color(0xFFFAF8F3))) {
        Column(Modifier.fillMaxWidth().padding(12.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(nutritionText(language,"View ","காட்சி ")+"${index+1}/${ParameciumNutrition.stages.size} — "+stage.heading.value(language),
                style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold,
                modifier=Modifier.testTag("r19-stage-title").semantics { heading() })
            Button(onClick={scope.launch { requester.bringIntoView() }},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-view-plate")) {
                Text(nutritionText(language,"Bring the complete pencil plate into view","முழுப் பென்சில் படத்தைத் திரையில் காண்க"))
            }
            Canvas(Modifier.fillMaxWidth().height(300.dp).bringIntoViewRequester(requester).testTag("r19-nutrition-canvas")
                .pointerInput(stage.id,progress.selectedStructure) { detectTapGestures { p ->
                    val f=NutritionFigure.fit(size.width.toFloat(),size.height.toFloat())
                    NutritionFigure.at((p.x-f.dx)/f.scale,(p.y-f.dy)/f.scale)?.let(::chooseStructure)
                } }.semantics {
                    contentDescription=nutritionText(language,
                        "Original pencil cutaways: ventral whole-cell orientation, separately enlarged oral apparatus and food vacuole. No permanent gut or universal vacuole itinerary. Current view: ",
                        "அசல் பென்சில் வெட்டுத் தோற்றங்கள்: வயிற்றுப்புற முழுச் செல் திசை, தனியாகப் பெரிதாக்கிய வாய்ப்புற அமைப்பு, உணவு நுண்குமிழ். நிரந்தரக் குடலோ பொதுவான நுண்குமிழ் பயணப் பாதையோ அல்ல. தற்போதைய காட்சி: ")+stage.heading.value(language)+". "+stage.explanation.value(language)
                    stateDescription=ParameciumNutrition.structure(progress.selectedStructure).name.value(language)+
                        nutritionText(language," highlighted"," சிறப்பிக்கப்பட்டது")
                    progressBarRangeInfo=ProgressBarRangeInfo(playback.phase,0f..1f)
                    customActions=ParameciumNutrition.structures.map { item -> CustomAccessibilityAction(item.name.value(language)) { chooseStructure(item.id);true } }
                }) { drawNutritionPencil(stage.id,progress.selectedStructure,playback.phase) }
            Text(nutritionText(language,
                "G oral groove/vestibule · O oral cilia · M cytostome · F cytopharynx · V food vacuole · A acidosome · L lysosome · B vacuolar boundary · S soluble products. Left: ventral orientation only. Upper-right: enlarged oral cutaway. Lower-right: independently enlarged vacuole. Membrane folds, ciliary counts, particle size and timing are schematic; amber particles, stippling and enzyme dots are qualitative teaching cues, not a microscope stain, calibrated pH or resolved proteins.",
                "G வாய்ப்பள்ளம்/முன்னறை · O வாய்ப்புறக் குறுஇழை · M செல் வாய் · F சைட்டோஃபாரிங்ஸ் · V உணவு நுண்குமிழ் · A அமிலச் சிறுகுமிழ் · L லைசோசோம் · B நுண்குமிழ்ச் சவ்வு · S கரையும் விளைபொருள்கள். இடது: வயிற்றுப்புறத் திசை மட்டும். மேல் வலது: பெரிதாக்கிய வாய்ப்புற வெட்டுத் தோற்றம். கீழ் வலது: தனிப் பெரிதாக்கிய நுண்குமிழ். சவ்வு மடிப்புகள், குறுஇழை எண்ணிக்கை, துகள் அளவு, நேரம் ஆகியவை விளக்கத்திற்கானவை; பழுப்பு நிறத் துகள்களும் புள்ளிகளும் கற்பித்தல் குறிகள்; நுண்ணோக்கிச் சாயம், அளவிட்ட pH அல்லது நேரடிப் புரதக் காட்சி அல்ல."),
                modifier=Modifier.testTag("r19-plate-legend"))
            for(item in ParameciumNutrition.structures) {
                OutlinedButton(onClick={chooseStructure(item.id)},shape=shape,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-select-"+item.id)
                        .semantics { selected=progress.selectedStructure==item.id }) { Text(item.name.value(language)) }
            }
            Text(ParameciumNutrition.structure(progress.selectedStructure).explanation.value(language),
                style=MaterialTheme.typography.bodyLarge,modifier=Modifier.testTag("r19-organ-explanation"))
            OutlinedButton(onClick={speak(ParameciumNutrition.structure(progress.selectedStructure).explanation)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-narrate-organ")) {
                Text(nutritionText(language,"Replay the selected structure explanation","தேர்ந்தெடுத்த அமைப்பின் விளக்கத்தை மீண்டும் கேள்"))
            }
            Text(stage.explanation.value(language),style=MaterialTheme.typography.bodyLarge,modifier=Modifier.testTag("r19-stage-explanation"))
            for((i,item) in ParameciumNutrition.stages.withIndex()) {
                OutlinedButton(onClick={chooseStage(item.id)},shape=shape,
                    modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-stage-"+item.id)
                        .semantics { selected=item.id==stage.id }) { Text("${i+1}. "+item.heading.value(language)) }
            }
            Text(nutritionText(language,
                "Manual stage selection shows the completed pose. Inspect either endpoint without motion, or play the stage from its start. Playback pauses at this stage’s endpoint; Next view changes the stage. The pose is a teaching frame, not a measured volume.",
                "கையால் நிலையைத் தேர்ந்தெடுத்தால் முடிவுத் தோற்றம் காட்டப்படும். இயக்கமின்றித் தொடக்க அல்லது முடிவுத் தோற்றத்தைக் காணலாம்; அல்லது தொடக்கத்திலிருந்து நிலையை இயக்கலாம். இந்நிலையின் முடிவில் இயக்கம் இடைநிறுத்தப்படும்; அடுத்த காட்சி நிலையை மாற்றும். இது கற்பித்தல் தோற்றம்; அளவிடப்பட்ட பருமன் அல்ல."))
            Text(nutritionText(language,"View pose: ","காட்சித் தோற்றம்: ")+when {
                playback.phase>=.999f -> nutritionText(language,"Completed","முடிவு")
                playback.phase<=.001f -> nutritionText(language,"Start","தொடக்கம்")
                else -> nutritionText(language,"Intermediate","இடைநிலை")
            },modifier=Modifier.testTag("r19-view-pose"))
            OutlinedButton(onClick={choosePose(false)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-start-view")) {
                Text(nutritionText(language,"Inspect this stage's starting pose","இந்நிலையின் தொடக்கத் தோற்றத்தைக் காண்க"))
            }
            OutlinedButton(onClick={choosePose(true)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-complete-view")) {
                Text(nutritionText(language,"Inspect this stage's completed pose","இந்நிலையின் முடிவுத் தோற்றத்தைக் காண்க"))
            }
            Text(nutritionText(language,"Illustrative speed and proportions; real events overlap. Restored playback remains paused.",
                "கற்பித்தலுக்கான வேகமும் விகிதங்களும்; இயற்கை நிகழ்வுகள் ஒன்றோடொன்று அமையும். மீட்டமைந்த இயக்கம் இடைநிறுத்தத்தில் இருக்கும்."))
            Text(nutritionText(language,if(playback.playing)"Playing" else "Paused",if(playback.playing)"இயங்குகிறது" else "இடைநிறுத்தம்"),
                modifier=Modifier.testTag("r19-playback-state"))
            Button(onClick={if(playback.playing) {player.pause();change(progress.copy(phasePermille=(player.state.value.phase*1000).toInt()))}
                else {
                    if(player.state.value.phase>=1f) { player.rewind();change(progress.copy(phasePermille=0)) }
                    player.play { change(progress.copy(phasePermille=1000)) }
                }},enabled=!progress.reducedMotion,shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-play-pause")) {
                Text(nutritionText(language,if(playback.playing)"Pause" else "Play",if(playback.playing)"இடைநிறுத்து" else "இயக்கு"))
            }
            OutlinedButton(onClick={chooseStage(ParameciumNutrition.stages[(index-1).coerceAtLeast(0)].id)},enabled=index>0,shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-previous")) { Text(nutritionText(language,"Previous view","முந்தைய காட்சி")) }
            OutlinedButton(onClick={chooseStage(ParameciumNutrition.stages[(index+1).coerceAtMost(ParameciumNutrition.stages.lastIndex)].id)},enabled=index<ParameciumNutrition.stages.lastIndex,shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-next")) { Text(nutritionText(language,"Next view","அடுத்த காட்சி")) }
            OutlinedButton(onClick={chooseStage("current",false)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-reset")) { Text(nutritionText(language,"Replay from the beginning","தொடக்கத்திலிருந்து மீண்டும் இயக்கு")) }
            OutlinedButton(onClick={player.pause();change(progress.copy(reducedMotion=!progress.reducedMotion,
                phasePermille=(player.state.value.phase*1000).toInt()))},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-reduced-motion")
                    .semantics { selected=progress.reducedMotion }) {
                Text(nutritionText(language,"Reduced motion: ","குறைக்கப்பட்ட இயக்கம்: ")+nutritionText(language,
                    if(progress.reducedMotion)"On" else "Off",if(progress.reducedMotion)"செயலில்" else "நிறுத்தம்"))
            }
            OutlinedButton(onClick={speak(stage.explanation)},shape=shape,
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-narrate-stage")) {
                Text(nutritionText(language,"Hear this stage's complete explanation","இந்நிலையின் முழு விளக்கத்தைக் கேள்"))
            }
        }
    }
    Text(nutritionText(language,"Scientific sources and scope","அறிவியல் ஆதாரங்களும் வரம்பும்"),style=MaterialTheme.typography.titleLarge,modifier=Modifier.semantics { heading() })
    ParameciumNutrition.sourceNotes.forEach { Text(it,style=MaterialTheme.typography.bodyMedium) }
}


/** Original native graphite drawing: orientation and independently enlarged cutaways, not copied microscopy. */
private fun DrawScope.drawNutritionPencil(stage: String, selected: String, phase: Float) {
    val fit=NutritionFigure.fit(size.width,size.height)
    val ink=Color(0xFF494641);val faint=ink.copy(alpha=.35f);val amber=Color(0xFFAC672C)
    val paper=Color(0xFFFFFDF8)
    fun label(text: String,x: Float,y: Float) {
        drawContext.canvas.nativeCanvas.drawText(text,x,y,Paint().apply {
            color=android.graphics.Color.rgb(60,57,52);textSize=25f;isAntiAlias=true;isFakeBoldText=true
        })
    }
    fun line(a: Offset,b: Offset,width: Float=2f) {
        drawLine(ink,a,b,width);drawLine(faint,a+Offset(1.8f,1f),b+Offset(1f,1.6f),1f)
    }
    withTransform({translate(fit.dx,fit.dy);scale(fit.scale,fit.scale,pivot=Offset.Zero)}) {
        drawRect(paper,size=Size(NutritionFigure.width,NutritionFigure.height))
        val cell=Path().apply {
            moveTo(175f,30f);cubicTo(285f,20f,355f,115f,345f,200f)
            cubicTo(337f,238f,285f,260f,299f,298f)
            cubicTo(329f,349f,355f,454f,301f,529f)
            cubicTo(233f,607f,136f,580f,89f,461f)
            cubicTo(54f,367f,47f,200f,88f,109f)
            cubicTo(109f,62f,141f,37f,175f,30f);close()
        }
        drawPath(cell,Color(0xFFF0EEE8));drawPath(cell,ink,style=Stroke(3.5f))
        withTransform({translate(2f,1f)}) { drawPath(cell,faint,style=Stroke(1.1f)) }
        for(i in 0..17) {
            val y=85f+i*25f
            line(Offset(80f,y),Offset(66f,y-7f),1.2f)
            if(y<205f || y>345f) line(Offset(329f,y),Offset(343f,y-6f),1.2f)
            line(Offset(137f,y),Offset(143f,y+10f),.8f)
        }
        drawOval(faint,Offset(136f,180f),Size(78f,132f),style=Stroke(2f))
        drawCircle(faint,12f,Offset(228f,256f),style=Stroke(2f))
        val groove=Path().apply { moveTo(331f,162f);cubicTo(293f,189f,247f,246f,280f,297f) }
        drawPath(groove,ink,style=Stroke(3f))
        for(i in 0..8) line(Offset(316f-i*4f,176f+i*12f),Offset(302f-i*4f,171f+i*12f),1.5f)
        line(Offset(284f,287f),Offset(256f,319f));line(Offset(294f,295f),Offset(266f,326f))
        val smallRadius=if(stage=="formation")18f+18f*phase else 25f
        val small=if(stage=="formation")Offset(263f-30f*phase,337f+20f*phase) else Offset(230f,360f)
        drawCircle(ink,smallRadius,small,style=Stroke(2.5f))
        drawCircle(amber,4f,small+Offset(-7f,-5f));drawCircle(amber,3f,small+Offset(8f,6f))
        label("G",343f,188f);label("V",197f,399f)
        // Oral enlargement has its own scale; two walls are separate from a digestive gut.
        drawRoundRect(faint,Offset(495f,32f),Size(470f,260f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(8f),style=Stroke(1.5f))
        val upper=Path().apply { moveTo(520f,70f);cubicTo(610f,72f,677f,128f,728f,167f);lineTo(799f,206f) }
        val lower=Path().apply { moveTo(520f,130f);cubicTo(609f,141f,654f,179f,707f,193f);lineTo(786f,222f) }
        drawPath(upper,ink,style=Stroke(3f));drawPath(lower,ink,style=Stroke(3f))
        for(i in 0..7) {
            val x=558f+i*18f;val y=95f+i*10f
            val sway=if(stage=="current")sin(phase*6.28318f+i*.6f)*7f else 0f
            val c=Path().apply { moveTo(x,y);quadraticTo(x+9f,y+8f,x+16f+sway,y+16f) }
            drawPath(c,ink,style=Stroke(1.6f))
        }
        line(Offset(727f,170f),Offset(714f,190f),2.5f)
        line(Offset(790f,209f),Offset(822f,226f));line(Offset(785f,222f),Offset(816f,239f))
        val bud=Offset(850f+if(stage=="formation")phase*24f else 0f,238f)
        val budRadius=if(stage=="formation")17f+phase*12f else 28f
        if(stage!="formation" || phase<.55f) {
            line(Offset(817f,227f),Offset(bud.x-budRadius,bud.y-7f))
            line(Offset(815f,239f),Offset(bud.x-budRadius,bud.y+7f))
        }
        drawCircle(paper,budRadius,bud);drawCircle(ink,budRadius,bud,style=Stroke(2.8f))
        for(i in 0..4) drawCircle(amber,3.2f,bud+Offset((i%3-1)*10f,(i/3-1)*10f))
        if(stage=="current" || stage=="entry") {
            for(i in 0..4) {
                val t=(phase+i*.18f)%1f
                val p=if(stage=="current")Offset(523f+195f*t,96f+77f*t) else Offset(726f+105f*t,179f+50f*t)
                drawCircle(amber,4.5f,p)
            }
        }
        label("G",544f,58f);label("O",621f,126f);label("M",727f,160f);label("F",790f,195f)
        // Independently enlarged food compartment, with cytosol outside and food in the lumen.
        drawRoundRect(faint,Offset(495f,307f),Size(470f,267f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(8f),style=Stroke(1.5f))
        val maturation=ParameciumDigestiveMaturation.pose(stage,phase)
        val center=Offset(740f,435f);val radius=if(stage=="formation")62f+28f*phase else 90f
        drawCircle(Color(0xFFF2EEE5),radius,center);drawCircle(ink,radius,center,style=Stroke(3f))
        drawCircle(faint,radius-4f,center+Offset(1f,1f),style=Stroke(1.2f))
        for(i in 0..8) {
            val x=(i%3-1)*30f;val y=(i/3-1)*29f
            drawCircle(amber,6f*(1f-.7f*maturation.breakdown),center+Offset(x,y))
            if(maturation.breakdown>0f) {
                drawCircle(amber,2.5f,center+Offset(x+10f*maturation.breakdown,y-7f*maturation.breakdown))
                drawCircle(amber,2f,center+Offset(x-9f*maturation.breakdown,y+8f*maturation.breakdown))
            }
            drawLine(faint,center+Offset(x-7,y-8),center+Offset(x+7,y-3),1f)
        }
        // A and L remain identifiable separately; dots are not a measured pH or enzyme count.
        val aPhase=if(stage=="acidification")phase else 0f
        val lPhase=if(stage=="digestion")phase else 0f
        val ac=Offset(530f+125f*aPhase,352f+38f*aPhase)
        val ly=Offset(925f-95f*lPhase,352f+38f*lPhase)
        if(aPhase<.8f) {
            drawCircle(paper,20f,ac);drawCircle(ink,20f,ac,style=Stroke(2f))
            for(i in 0..3) drawLine(faint,ac+Offset(-9f+i*4f,-10f),ac+Offset(-4f+i*4f,8f),1.3f)
        }
        if(lPhase<.8f) {
            drawCircle(paper,20f,ly);drawCircle(ink,20f,ly,style=Stroke(2f))
            for(i in 0..3) drawCircle(ink,2.5f,ly+Offset((i%2)*9f-5f,(i/2)*9f-5f))
        }
        for(i in 0 until (18*maturation.acidification).toInt()) {
            val angle=i*2.39996f;val r=18f+(i%4)*14f
            val p=center+Offset(cos(angle)*r,sin(angle)*r)
            drawLine(faint,p,p+Offset(4f,-2f),1.2f)
        }
        for(i in 0 until (7*maturation.enzymeDelivery).toInt()) {
            val p=center+Offset(-40f+i*12f,15f+(i%2)*14f)
            drawLine(ink,p+Offset(-3f,-3f),p+Offset(3f,3f),1.3f)
            drawLine(ink,p+Offset(-3f,3f),p+Offset(3f,-3f),1.3f)
        }
        if(stage=="uptake") {
            for(i in 0..3) {
                val p=center+Offset(25f+170f*phase,-35f+i*22f)
                drawCircle(amber,2.5f,p)
            }
        }
        label("A",505f,331f);label("L",914f,331f);label("B",843f,432f);label("S",931f,559f)
        label("V",732f,555f)
        val mark=NutritionFigure.marks.firstOrNull { it.id==selected }
        if(mark!=null) {
            val p=Offset(mark.x,mark.y)
            drawCircle(amber,31f,p,style=Stroke(4f));drawCircle(ink,35f,p,style=Stroke(1.5f))
            line(p+Offset(36f,0f),p+Offset(55f,-13f),2.5f)
        }
    }
}
