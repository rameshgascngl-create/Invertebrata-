package com.gasczoology.invertebratelab.ui

import android.graphics.PathMeasure
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import com.gasczoology.invertebratelab.data.*

/** Original graphite outline: rounded anterior left, tapered posterior right. */
internal fun DrawScope.pencilCell(c:NuclearBodyCell) {
    val x=c.cx;val y=c.cy;val w=c.width/2;val h=c.height/2;val pinch=c.furrow
    val path=Path().apply {
        moveTo(x-w*.85f,y-h*.65f)
        cubicTo(x-w*1.16f,y-h*.15f,x-w*1.04f,y+h*.75f,x-w*.65f,y+h*.9f)
        cubicTo(x-w*.30f,y+h*1.10f,x-w*.12f,y+h*(1-pinch),x,y+h*(1-pinch))
        cubicTo(x+w*.20f,y+h*(1-pinch),x+w*.72f,y+h*.58f,x+w,y)
        cubicTo(x+w*.74f,y-h*.70f,x+w*.18f,y-h*(1-pinch),x,y-h*(1-pinch))
        cubicTo(x-w*.20f,y-h*1.15f,x-w*.70f,y-h*1.12f,x-w*.85f,y-h*.65f)
        close()
    }
    drawPath(path,Color(0xFFF1EEE7));drawPath(path,graphite,style=Stroke(3f))
    val measure=PathMeasure(path.asAndroidPath(),true)
    val p=FloatArray(2);val tangent=FloatArray(2)
    for(i in 0..63) {
        measure.getPosTan(measure.length*i/64,p,tangent)
        val normal=Offset(-tangent[1],tangent[0]);val start=Offset(p[0],p[1])
        drawLine(pencil,start,start+normal*13f+Offset(tangent[0],tangent[1])*4f,1.35f)
    }
    val oral=Path().apply{moveTo(x-w*.58f,y+h*.43f);cubicTo(x-w*.15f,y+h*.82f,x+w*.25f,y+h*.67f,x+w*.56f,y+h*.26f)}
    drawPath(oral,pencil,style=Stroke(2.2f))
}
internal fun DrawScope.chromosome(x:Float,y:Float,replicated:Boolean,pattern:Boolean=false,scale:Float=1f) {
    val h=20f*scale;val a=7f*scale
    if(replicated) {
        drawLine(graphite,Offset(x-a,y-h),Offset(x+a,y+h),4f*scale)
        drawLine(graphite,Offset(x+a,y-h),Offset(x-a,y+h),4f*scale)
    } else drawLine(graphite,Offset(x,y-h),Offset(x,y+h),4f*scale)
    if(pattern) for(i in -2..2)drawLine(selectedInk,Offset(x-a,y+i*7f*scale),Offset(x+a,y+i*7f*scale),1.5f*scale)
}
internal fun DrawScope.drawFissionPencilPlate(stage:NuclearView,selected:String,phase:Float) {
    val body=NuclearBodyGeometry.fission(stage,phase)
    body.cells.forEach{pencilCell(it)}
    body.marks.forEach { n -> if(n.kind=="macronucleus") mac(n.x,n.y,n.width,n.height,selected==n.kind)
        else mic(n.x,n.y,n.width/2,selected==n.kind) }
    if(stage.id=="mic-mitosis") {
        val m=body.marks.filter{it.kind=="micronucleus"}
        drawOval(pencil,Offset(m.first().x-25,184f),Size(m.last().x-m.first().x+50,72f),style=Stroke(1.5f))
        for(i in -2..2)drawLine(pencil,Offset(m.first().x,220f+i*4),Offset(m.last().x,220f+i*4),1f)
    }
    if(stage.id=="mac-elongation")for(i in -2..2)drawLine(pencil,Offset(207f,280f+i*8),Offset(411f,280f+i*8),1.3f)
    if(stage.id=="constriction")drawLine(selectedInk,Offset(310f,150f),Offset(310f,391f),1.2f)
    mac(780f,155f,if(stage.id=="mac-elongation")280f else 215f,100f,selected=="macronucleus")
    val replicated=stage.id=="replication"
    if(stage.id=="mic-mitosis") {
        val gap=40f+phase*25f
        drawOval(pencil,Offset(657f,315f),Size(246f,130f),style=Stroke(1.5f))
        for(i in -2..2)drawLine(pencil,Offset(780f-gap,380f+i*7),Offset(780f+gap,380f+i*7),1.3f)
        for(x in listOf(780f-gap,780f+gap)) {
            mic(x,380f,36f,selected=="micronucleus")
            chromosome(x-10,380f,false,false,.6f);chromosome(x+10,380f,false,true,.6f)
        }
    } else {
        mic(780f,380f,70f,selected=="micronucleus")
        chromosome(755f,380f,replicated);chromosome(805f,380f,replicated,true)
    }
    drawLine(pencil,Offset(520f,220f),Offset(640f,155f),1f)
    drawLine(pencil,Offset(515f,270f),Offset(690f,375f),1f)
}
