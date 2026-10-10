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
internal fun ParameciumEgestionPlate(language: AppLanguage,stage: String,selected: String,phase: Float,onSelect: (String)->Unit) {
    val requester=remember { BringIntoViewRequester() };val scope=rememberCoroutineScope()
    val description=if(language==AppLanguage.TAMIL)
        "சைட்டோப்ராக்ட் வெட்டுத் தோற்றம்: மூடிய முகடு, தற்காலிகத் திறப்பு, வெளியேறும் எச்சங்கள், சவ்வு மீட்பு. மேல் வெளிச்சூழல்; கீழ் சைட்டோசால். அளவோ நேரமோ அளவிடப்படவில்லை. "
        else "Cytoproct section: closed ridge, transient opening, released residues and membrane recovery. Exterior above; cytosol below. Scale and time are not measured. "
    Button(onClick={scope.launch { requester.bringIntoView() }},modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r19-view-egestion-plate")) {
        Text(if(language==AppLanguage.TAMIL)"முழு எச்ச வெளியேற்றப் படத்தைக் காண்க" else "View the complete egestion plate")
    }
    Canvas(Modifier.fillMaxWidth().height(300.dp).bringIntoViewRequester(requester).testTag("r19-egestion-canvas")
        .pointerInput(stage,selected) { detectTapGestures { p -> val f=EgestionFigure.fit(size.width.toFloat(),size.height.toFloat())
            EgestionFigure.at((p.x-f.dx)/f.scale,(p.y-f.dy)/f.scale)?.let(onSelect)
        } }.semantics {
            contentDescription=description+ParameciumNutrition.stages.first { it.id==stage }.explanation.value(language)
            stateDescription=ParameciumNutrition.structure(selected).name.value(language)
            progressBarRangeInfo=ProgressBarRangeInfo(phase,0f..1f)
            customActions=ParameciumEgestion.structures.map { s -> CustomAccessibilityAction(s.name.value(language)) { onSelect(s.id);true } }
        }) { drawEgestion(stage,selected,phase) }
    Text(if(language==AppLanguage.TAMIL)
        "C சைட்டோப்ராக்ட் · R எச்ச நுண்குமிழ் · H மீட்கப்பட்ட சவ்வு. இடது முழுச் செல் திசைக் குறி மட்டும்; வலது தனியாகப் பெரிதாக்கிய புறப்படல வெட்டுத் தோற்றம். மேலே வெளிச்சூழல்; கீழே சைட்டோசால். பெரிய சாம்பல் துகள்கள் செரிக்காத எச்சங்கள்; சைட்டோசாலில் சிதறுவதில்லை. குறுஇழை, நுண்குழல், சவ்வு விகிதங்கள் விளக்கத்திற்கானவை. முழுச் செல் அமைவிடமும் விசை விளக்கமும் நிபுணர் ஒப்புதலுக்குக் காத்திருக்கின்றன."
        else "C cytoproct · R residue vacuole · H retrieved membrane. Left: whole-cell orientation only. Right: independently enlarged cortical section. Exterior above; cytosol below. Large grey particles are indigestible residues, never scattered in cytosol. Ciliary, microtubular and membrane proportions are schematic. Whole-cell placement and force interpretation await specialist approval.",modifier=Modifier.testTag("r19-egestion-legend"))
}

private fun DrawScope.drawEgestion(stage: String,selected: String,phase: Float) {
    val f=EgestionFigure.fit(size.width,size.height);val pose=ParameciumEgestion.pose(stage,phase)
    val ink=Color(0xFF494641);val faint=ink.copy(alpha=.35f);val paper=Color(0xFFFFFDF8)
    fun line(a: Offset,b: Offset,w: Float=2f) {drawLine(ink,a,b,w);drawLine(faint,a+Offset(1f,2f),b+Offset(1f,1f),1f)}
    fun label(s: String,x: Float,y: Float) {drawContext.canvas.nativeCanvas.drawText(s,x,y,Paint().apply {
        color=android.graphics.Color.rgb(60,57,52);textSize=25f;isAntiAlias=true;isFakeBoldText=true
    })}
    withTransform({translate(f.dx,f.dy);scale(f.scale,f.scale,pivot=Offset.Zero)}) {
        drawRect(paper,size=Size(1000f,600f))
        val cell=Path().apply {moveTo(172f,35f);cubicTo(314f,16f,379f,141f,310f,245f)
            cubicTo(279f,287f,351f,390f,305f,511f);cubicTo(222f,636f,100f,555f,81f,361f)
            cubicTo(58f,186f,74f,73f,172f,35f);close()}
        drawPath(cell,Color(0xFFF0EEE8));drawPath(cell,ink,style=Stroke(3f))
        for(i in 0..16) {val y=87f+i*26f;line(Offset(83f,y),Offset(66f,y-8),1f)
            if(y<230 || y>320)line(Offset(319f,y),Offset(337f,y-7),1f)}
        drawOval(faint,Offset(145f,190f),Size(70f,125f),style=Stroke(2f))
        val groove=Path().apply {moveTo(313f,160f);quadraticTo(249f,230f,285f,296f)}
        drawPath(groove,ink,style=Stroke(3f));label("C?",304f,470f)
        line(Offset(276f,445f),Offset(302f,466f),2f)
        // The orientation mark is explicitly provisional; the section has an independent scale.
        drawRoundRect(faint,Offset(467f,34f),Size(510f,530f),style=Stroke(1.2f))
        label("C",724f,130f);label("R",723f,469f);label("H",918f,445f)
        val gap=110f*pose.opening
        line(Offset(490f,185f),Offset(735f-gap/2,185f),3f)
        line(Offset(735f+gap/2,185f),Offset(958f,185f),3f)
        // Paired sub-surface profiles stand for cortical alveoli; they are not open external ducts.
        for(x in listOf(505f,555f,855f,905f)) {
            drawOval(faint,Offset(x,205f),Size(40f,25f),style=Stroke(2f))
            line(Offset(x+20f,238f),Offset(680f,340f),.9f)
        }
        val centerY=440f-130f*pose.approach
        if(stage=="docking") {
            drawCircle(Color(0xFFF2EEE5),100f,Offset(735f,centerY))
            drawCircle(ink,100f,Offset(735f,centerY),style=Stroke(3f))
            drawCircle(faint,96f,Offset(736f,centerY+1),style=Stroke(1f))
        } else if(pose.recovery<1f) {
            val depth=225f*(1f-pose.recovery)
            val cup=Path().apply {moveTo(735f-gap/2,185f)
                cubicTo(620f,185f+depth*.45f,625f,185f+depth,735f,185f+depth)
                cubicTo(845f,185f+depth,850f,185f+depth*.45f,735f+gap/2,185f)}
            drawPath(cup,ink,style=Stroke(3f))
        }
        for(i in 0..2) {
            val y=if(stage=="docking")centerY-20f+i*17f else 297f+i*17f-245f*pose.residueRelease
            val x=719f+i*16f
            drawOval(faint,Offset(x-7,y-7),Size(15f,12f));drawOval(ink,Offset(x-7,y-7),Size(15f,12f),style=Stroke(1.2f))
            line(Offset(x-5,y-2),Offset(x+5,y+3),1f)
        }
        for(i in 0 until (5*pose.recovery).toInt()) {
            val x=857f+i*18f;val y=285f+25f*i
            drawOval(ink,Offset(x,y),Size(14f,25f),style=Stroke(2f))
            drawOval(faint,Offset(x+2,y+1),Size(11f,23f),style=Stroke(1f))
        }
        EgestionFigure.marks.firstOrNull { it.id==selected }?.let {
            drawCircle(Color(0xFFAC672C),31f,Offset(it.x,it.y),style=Stroke(4f))
            drawCircle(ink,35f,Offset(it.x,it.y),style=Stroke(1.2f))
        }
    }
}
