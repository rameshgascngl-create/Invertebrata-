package com.gasczoology.invertebratelab.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.launch

@Composable
internal fun ParameciumPhysiologyPlate(language: AppLanguage,stage: String,selected: String,player: com.gasczoology.invertebratelab.NutritionPlaybackViewModel,onSelect: (String)->Unit,enabled: Boolean,onToggle: ()->Unit,onInspectIntermediate: ()->Unit) {
    val requester=remember {BringIntoViewRequester()};val scope=rememberCoroutineScope()
    val playback=player.state.collectAsState()
    val playing by remember(player) {derivedStateOf {playback.value.playing}}
    Button(onClick={scope.launch {requester.bringIntoView()}},modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-view-physiology-plate")) {
        Text(if(language==AppLanguage.TAMIL)"முழு வாயுப் பரிமாற்றப் படத்தைக் காண்க" else "View the complete physiology plate")
    }
    // Genuine mid-animation frame hold for repeatable inspection, even
    // when TalkBack or 200% Tamil multiline layout delays manual taps.
    OutlinedButton(onClick=onInspectIntermediate,enabled=enabled,
        modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
            .testTag("r19-physiology-inspect-midpoint")) {
        Text(if(language==AppLanguage.TAMIL)
            "இயக்கத்தைத் தொடங்கி இடைநிலைக் காட்சியில் நிறுத்து"
            else "Play and hold the genuine intermediate frame")
    }
    OutlinedButton(onClick=onToggle,enabled=enabled,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-physiology-inline-play-pause")) {
        Text(if(language==AppLanguage.TAMIL) {if(playing)"இடைநிறுத்து" else "இயக்கு"} else {if(playing)"Pause" else "Play"})
    }
    Canvas(Modifier.fillMaxWidth().height(300.dp).bringIntoViewRequester(requester).testTag("r19-physiology-canvas")
        .pointerInput(stage,selected) {detectTapGestures {p ->val f=PhysiologyFigure.fit(size.width.toFloat(),size.height.toFloat())
            PhysiologyFigure.at((p.x-f.dx)/f.scale,(p.y-f.dy)/f.scale)?.let(onSelect)
        }}.semantics {
            contentDescription=(if(language==AppLanguage.TAMIL)"முழுச் செல் திசை; தனியாகப் பெரிதாக்கிய மைட்டோகாண்ட்ரியக் குழல்வடிவ கிரிஸ்டேகள்; O2 நுழைவு, ATP செயல்பாட்டுக் குறி, CO2 பரிமாற்றம். " else "Whole-cell orientation; separately enlarged mitochondrial tubular cristae; O2 entry, ATP function cue and CO2 exchange. ")+ParameciumPhysiology.stages.first {it.id==stage}.explanation.value(language)
            stateDescription=ParameciumNutrition.structure(selected).name.value(language)
            progressBarRangeInfo=ProgressBarRangeInfo(playback.value.phase,0f..1f)
            customActions=ParameciumPhysiology.structures.map {s ->CustomAccessibilityAction(s.name.value(language)) {onSelect(s.id);true}}
        }) {drawPhysiology(stage,selected,playback.value.phase)}
    Text(if(language==AppLanguage.TAMIL)
        "E செல் எல்லை · T மைட்டோகாண்ட்ரியம் · D கரைந்த வாயுப் பரிமாற்றம். இடது முழுச் செல் திசை மட்டும்; வலது தனியாகப் பெரிதாக்கிய மைட்டோகாண்ட்ரிய வெட்டுத் தோற்றம். இரட்டைச் சவ்வு எல்லைகளும் குழல்வடிவ/சுருள் கிரிஸ்டே விளக்கத் தோற்றங்களும் காட்டப்பட்டுள்ளன; எண்ணிக்கை அளவிடப்படவில்லை. நீல O2 குறிகளும் சாம்பல் CO2 குறிகளும் பண்புசார் நகர்வைக் காட்டுகின்றன. ATP குறி வேதிவினையோ அளவிட்ட விளைச்சலோ அல்ல. முப்பரிமாண வடிவம், அளவு, வேகம், சுருள் அமைப்பின் முக்கியத்துவம் ஆகியவை உறுதிப்படுத்தப்படவில்லை. இவை ஒருங்கே நிகழும் செயல்களின் காட்சிகள்; மூச்சுச் சுழற்சி அல்ல."
        else "E cell boundary · T mitochondrion · D dissolved gas exchange. Left: whole-cell orientation only. Right: independently enlarged mitochondrial section. Double membrane boundaries and tubular/helical crista profiles are shown; counts are not measured. Blue O2 and grey CO2 cues indicate qualitative movement. ATP is a function cue, not a chemical reaction or measured yield. Three-dimensional form, scale, speed and helical significance are unresolved. These are views of coexisting processes, not a breathing cycle.",modifier=Modifier.testTag("r19-physiology-legend"))
}

/** Original graphite profiles informed by Allen's microscopy; no research image is copied. */
private fun DrawScope.drawPhysiology(stage: String,selected: String,phase: Float) {
    val f=PhysiologyFigure.fit(size.width,size.height);val p=phase.coerceIn(0f,1f)
    val ink=Color(0xFF494641);val faint=ink.copy(alpha=.33f);val amber=Color(0xFFAC672C);val blue=Color(0xFF477185)
    fun line(a: Offset,b: Offset,w: Float=2f,color: Color=ink) {drawLine(color,a,b,w);drawLine(faint,a+Offset(1.2f,1f),b+Offset(1f,1.6f),.8f)}
    fun label(s: String,x: Float,y: Float,color: Color=ink,sz: Float=25f) {drawContext.canvas.nativeCanvas.drawText(s,x,y,Paint().apply {
        this.color=color.toArgb();textSize=sz;isAntiAlias=true;isFakeBoldText=true
    })}
    fun arrow(a: Offset,b: Offset,color: Color) {
        line(a,b,1.6f,color);val v=b-a;val length=v.getDistance();val u=v/length;val cross=Offset(-u.y,u.x)
        line(b,b-u*12f+cross*6f,1.6f,color);line(b,b-u*12f-cross*6f,1.6f,color)
    }
    withTransform({translate(f.dx,f.dy);scale(f.scale,f.scale,pivot=Offset.Zero)}) {
        drawRect(Color(0xFFFFFDF8),size=Size(1000f,600f))
        val cell=Path().apply {moveTo(320f,37f);cubicTo(440f,21f,498f,138f,454f,225f)
            cubicTo(429f,271f,455f,296f,460f,347f);cubicTo(483f,519f,326f,594f,223f,488f)
            cubicTo(145f,405f,145f,203f,210f,106f);cubicTo(239f,64f,281f,40f,320f,37f);close()}
        drawPath(cell,Color(0xFFF0EEE8));drawPath(cell,if(selected=="cell-boundary")amber else ink,style=Stroke(3.5f))
        withTransform({translate(3f,1f)}) {drawPath(cell,faint,style=Stroke(1.2f))}
        for(i in 0..15) {
            val y=100f+i*26f;line(Offset(181f,y),Offset(165f,y-7f),1f)
            if(y<220f || y>330f)line(Offset(455f,y),Offset(473f,y-7f),1f)
        }
        val groove=Path().apply {moveTo(460f,176f);quadraticTo(393f,224f,433f,297f)}
        drawPath(groove,ink,style=Stroke(2.5f))
        drawOval(faint,Offset(279f,179f),Size(62f,112f),style=Stroke(1.7f))
        drawCircle(faint,12f,Offset(350f,270f),style=Stroke(1.5f))
        for((x,y) in listOf(246f to 321f,337f to 390f,272f to 433f)) {
            drawOval(ink,Offset(x,y),Size(39f,17f),style=Stroke(1.6f))
            for(j in 0..3)drawOval(faint,Offset(x+7f+j*6f,y+5f),Size(4f,7f),style=Stroke(.9f))
        }
        label("E",478f,324f);label("D",299f,534f)
        drawRoundRect(faint,Offset(548f,81f),Size(435f,424f),style=Stroke(1.2f))
        label("T",751f,120f)
        val mitoColor=if(selected=="mitochondrion")amber else ink
        drawOval(Color(0xFFF3EFE5),Offset(568f,151f),Size(395f,266f))
        drawOval(mitoColor,Offset(568f,151f),Size(395f,266f),style=Stroke(3f))
        drawOval(faint,Offset(574f,156f),Size(383f,256f),style=Stroke(1f))
        drawOval(ink,Offset(584f,166f),Size(361f,236f),style=Stroke(2f))
        // Paired curving profiles stand for longitudinal tubular/helical crista sections.
        // Cross sections accompany them; they are not generic lamellar zigzag stacks.
        for(i in 0..4) {
            val x=636f+i*57f;val edge=i==0 || i==4;val y=if(edge)228f else 190f;val h=if(edge).58f else 1f
            val path=Path().apply {moveTo(x,y);cubicTo(x-24f,y+40f*h,x+29f,y+63f*h,x+4f,y+95f*h)
                cubicTo(x-23f,y+118f*h,x+22f,y+140f*h,x+5f,y+171f*h)}
            drawPath(path,if(stage=="metabolism")amber.copy(alpha=.35f+.65f*p) else ink,style=Stroke(3f))
            withTransform({translate(5f,0f)}) {drawPath(path,faint,style=Stroke(1.4f))}
            drawOval(ink,Offset(x-10f,y+55f*h),Size(17f,10f),style=Stroke(1f))
        }
        for(i in 0..22)drawCircle(faint,.9f,Offset(608f+(i%9)*36f,228f+(i/9)*54f))
        label("ATP",716f,463f,if(stage=="metabolism")amber.copy(alpha=.25f+.75f*p) else faint,28f)
        if(stage=="metabolism")for(i in 0 until (6*p).toInt())line(Offset(704f+i*19f,481f),Offset(704f+i*19f,473f-18f*p),2f,amber)
        if(stage=="gas-entry" || stage=="gas-exchange") {
            arrow(Offset(57f,281f),Offset(263f,281f),blue);label("O2",58f,257f,blue)
            for(i in 0..2)drawCircle(blue,4.5f,Offset(68f+i*20f+130f*p,281f))
        }
        if(stage=="gas-exchange") {
            arrow(Offset(244f,478f),Offset(76f,543f),ink);label("CO2",53f,575f)
            for(i in 0..2)drawCircle(ink,4f,Offset(246f-i*18f-113f*p,476f+i*6f+44f*p))
        }
        PhysiologyFigure.marks.firstOrNull {it.id==selected}?.let {drawCircle(amber,31f,Offset(it.x,it.y),style=Stroke(4f));drawCircle(ink,35f,Offset(it.x,it.y),style=Stroke(1f))}
    }
}
