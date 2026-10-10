package com.gasczoology.invertebratelab.ui

import android.graphics.Paint as LabelPaint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import com.gasczoology.invertebratelab.data.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private fun DrawScope.label(value:String,x:Float,y:Float) {
    drawIntoCanvas { it.nativeCanvas.drawText(value,x,y,LabelPaint(LabelPaint.ANTI_ALIAS_FLAG).apply {
        color=android.graphics.Color.rgb(73,70,65);textSize=25f;isFakeBoldText=true
    }) }
}
private fun DrawScope.origin(origin:PartnerOrigin?,x:Float,y:Float,r:Float) {
    if(origin==PartnerOrigin.B || origin==PartnerOrigin.BOTH) {
        clipPath(Path().apply{addOval(androidx.compose.ui.geometry.Rect(x-r,y-r,x+r,y+r))}) {
            for(i in -5..5)drawLine(graphite,Offset(x-r,y+i*6f),Offset(x+r,y+i*6f),1.5f)
        }
    }
    if(origin==PartnerOrigin.A || origin==PartnerOrigin.BOTH)drawCircle(graphite,r*.2f,Offset(x,y))
}
private fun DrawScope.anlage(x:Float,y:Float,w:Float,h:Float,selected:Boolean) {
    drawOval(Color(0xFFEAE4D8),Offset(x-w/2,y-h/2),Size(w,h))
    clipPath(Path().apply{addOval(androidx.compose.ui.geometry.Rect(x-w/2,y-h/2,x+w/2,y+h/2))}) {
        for(i in -8..8)drawLine(pencil,Offset(x+i*8f-30,y-50),Offset(x+i*8f+30,y+50),1.2f)
    }
    drawOval(if(selected)selectedInk else graphite,Offset(x-w/2,y-h/2),Size(w,h),style=Stroke(if(selected)4f else 2f))
}
private fun DrawScope.oldMac(state:ParentalMacState,x:Float,y:Float,w:Float,h:Float,selected:Boolean) {
    if(state==ParentalMacState.INTACT) {mac(x,y,w,h,selected);return}
    if(state==ParentalMacState.SKEIN) {
        val path=Path().apply{moveTo(x-w/2,y);for(i in 0..7)cubicTo(x-w/2,y-h/2+i*3,x+w/2,y+h/2-i*3,x-w/2+i*w/7,y+i*3-9)}
        drawPath(path,if(selected)selectedInk else graphite,style=Stroke(2.5f))
    } else {
        drawOval(pencil,Offset(x-w/2,y-h/2),Size(w,h),style=Stroke(1.4f))
        drawLine(graphite,Offset(x-w/3,y-h/3),Offset(x+w/3,y+h/3),1.4f)
    }
}
private fun DrawScope.arrow(a:Offset,b:Offset) {
    drawLine(pencil,a,b,2f);val v=b-a;val length=v.getDistance().coerceAtLeast(1f);val u=v/length;val n=Offset(-u.y,u.x)
    drawLine(graphite,b,b-u*13f+n*6f,2f);drawLine(graphite,b,b-u*13f-n*6f,2f)
}
internal fun DrawScope.drawConjugationPencilPlate(stage:NuclearView,selected:String,phase:Float) {
    val body=NuclearBodyGeometry.conjugation(stage,phase)
    body.cells.forEachIndexed { i,c ->
        pencilCell(c)
        label(if(stage.cellCount==2)if(i==0)"A" else "B" else "${i+1}",c.cx-c.width*.45f,c.cy-c.height*.57f)
    }
    if(stage.paired) {
        // Oral-facing association marker, not a permanently functioning mouth.
        drawLine(pencil,Offset(295f,250f),Offset(295f,290f),2f)
        drawLine(pencil,Offset(310f,250f),Offset(310f,290f),2f)
    }
    if(stage.id=="exchange") {
        arrow(Offset(231f,237f),Offset(231f,302f));arrow(Offset(285f,302f),Offset(285f,237f))
    }
    if(stage.event in setOf(NuclearEvent.POSTZYGOTIC_MITOSIS,NuclearEvent.HOMOLOG_SEPARATION,NuclearEvent.SISTER_SEPARATION)) {
        body.cells.indices.forEach { index ->
            val products=body.marks.filter{it.cellIndex==index && it.kind=="micronucleus"}
            products.chunked(2).filter{it.size==2}.forEach { p ->
                for(j in -1..1)drawLine(pencil,Offset(p[0].x,p[0].y+j*3f),Offset(p[1].x,p[1].y+j*3f),1f)
            }
        }
    }
    body.marks.forEach { n ->
        val isSelected=selected==n.kind
        when {
            n.role=="degeneration" -> {
                drawCircle(pencil,n.width/2,Offset(n.x,n.y),style=Stroke(1.2f))
                drawLine(pencil,Offset(n.x-7,n.y-7),Offset(n.x+7,n.y+7),1.3f)
            }
            n.kind=="macronucleus" && n.role=="new-mac" -> anlage(n.x,n.y,n.width,n.height,isSelected)
            n.kind=="macronucleus" -> oldMac(stage.parentalMacState,n.x,n.y,n.width,n.height,isSelected)
            else -> {mic(n.x,n.y,n.width/2,isSelected);origin(n.origin,n.x,n.y,n.width*.4f)}
        }
    }
    label("MAC",670f,62f);label("MIC",670f,285f)
    if(stage.macronuclearAnlagenPerCell>0) {
        repeat(stage.macronuclearAnlagenPerCell){i ->
            val cols=if(stage.macronuclearAnlagenPerCell==4)2 else stage.macronuclearAnlagenPerCell
            anlage(740f+(i%cols)*90f,if(stage.macronuclearAnlagenPerCell==4)120f+(i/cols)*75f else 155f,
                65f,50f,selected=="macronucleus")
        }
        for(i in 0..2)oldMac(ParentalMacState.FRAGMENTS,710f+i*65f,232f,24f,12f,false)
    } else oldMac(stage.parentalMacState,780f,155f,210f,95f,selected=="macronucleus")
    val genomes=ConjugationGenetics.genomes(stage,0)
    if(stage.id=="exchange") {
        // Separate explanatory inset: A's retained stationary nucleus and the
        // incoming B nucleus converge; body animation shows both actual transfers.
        val gap=65f-phase*20f
        for((i,g) in genomes.withIndex()) {
            val x=780f+if(i==0)-gap else gap
            mic(x,385f,35f,selected=="micronucleus");origin(g.origin,x,385f,28f)
            label(if(i==0)"A · n" else "B · n",x-25,449f)
        }
    } else {
        val count=genomes.size;val cols=if(count>4)4 else if(count>1)2 else 1
        val r=if(count>4)25f else if(count>1)38f else 66f
        genomes.forEachIndexed { i,g ->
            val rows=(count+cols-1)/cols
            val x=780f+(i%cols-(cols-1)/2f)*(if(count>4)58f else 106f)
            val y=385f+(i/cols-(rows-1)/2f)*(if(count>4)66f else 94f)
            mic(x,y,r,selected=="micronucleus");origin(g.origin,x,y,r*.8f)
            if(g.ploidy==2) {
                chromosome(x-r*.27f,y,g.replicated,false,if(count>1).45f else .8f)
                chromosome(x+r*.27f,y,g.replicated,true,if(count>1).45f else .8f)
            } else chromosome(x,y,g.replicated,false,if(count>1).5f else .8f)
        }
    }
    if(stage.residualGermlinePossible) {
        // Not placed inside every descendant: no invented allocation of remnants.
        for(i in 0..2) {
            drawCircle(pencil,7f,Offset(708f+i*25f,488f),style=Stroke(1.2f))
            drawLine(pencil,Offset(703f+i*25f,483f),Offset(713f+i*25f,493f),1f)
        }
        label("residual?",793f,497f)
    }
}
