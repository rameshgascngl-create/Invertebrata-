package com.gasczoology.invertebratelab.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/** Original native pencil artwork; not copied/traced from stock imagery.
 *  Coordinates, numbers of organelles and external hotspots are UNREVIEWED.
 */
internal object PencilAtlasPalette {
    val paper=Color(0xFFFBFAF6)
    val cell=Color(0xFFF1EFE9)
    val graphite=Color(0xFF44413F)
    val dark=Color(0xFF302E2C)
    val mid=Color(0xFF8A8580)
    val hatch=Color(0xFFD0CBC5)
    val selected=Color(0xFFB55B16)
}

internal fun DrawScope.drawPencilCellDetails(silhouette:Path) {
    clipPath(silhouette) {
        // Parallel hatch and stipple patterns are deterministic at any DPI.
        for(r in 0..24) for(c in 0..27) if((r*3+c)%4 != 0) {
            val x=125f+c*27f+((r*11+c*7)%13)
            val y=118f+15f*r
            drawLine(PencilAtlasPalette.hatch,Offset(x,y),
                Offset(x+8f,y-8f),strokeWidth=1.4f)
        }
        for(i in 0 until 420) {
            drawCircle(PencilAtlasPalette.mid,
                radius=if(i%6==0)1.8f else 1.05f,
                center=Offset(155f+((i*79)%680).toFloat(),
                    142f+((i*43)%320).toFloat()),alpha=.6f)
        }
    }
    // Original kidney-form macronucleus and distinct small micronucleus.
    val nucleus=Path().apply {
        moveTo(387f,262f)
        cubicTo(382f,219f,445f,193f,491f,214f)
        cubicTo(520f,230f,513f,256f,497f,273f)
        cubicTo(488f,292f,507f,311f,482f,335f)
        cubicTo(460f,352f,404f,341f,392f,302f)
        cubicTo(384f,286f,386f,272f,387f,262f)
        close()
    }
    drawPath(nucleus,PencilAtlasPalette.cell)
    drawPath(nucleus,PencilAtlasPalette.dark,style=Stroke(4.2f))
    drawPath(nucleus,PencilAtlasPalette.mid,style=Stroke(1.2f))
    clipPath(nucleus) {
        for(i in 0 until 56) {
            val x=392f+((i*19)%112)
            val y=225f+((i*23)%103)
            drawLine(PencilAtlasPalette.mid,Offset(x,y),
                Offset(x+6f,y+3f),1.8f)
        }
    }
    drawOval(PencilAtlasPalette.cell,topLeft=Offset(520f,298f),
        size=Size(42f,34f))
    drawOval(PencilAtlasPalette.dark,topLeft=Offset(520f,298f),
        size=Size(42f,34f),style=Stroke(3.4f))
    drawCircle(PencilAtlasPalette.mid,radius=7f,center=Offset(541f,315f))
    // Food vacuoles are granular circles, not star-like contractile vacuoles.
    for((x,y,r) in listOf(Triple(640f,229f,35f),
        Triple(335f,373f,32f),Triple(623f,360f,30f))) {
        drawCircle(PencilAtlasPalette.cell,radius=r,center=Offset(x,y))
        drawCircle(PencilAtlasPalette.dark,radius=r,center=Offset(x,y),
            style=Stroke(3.5f))
        drawCircle(PencilAtlasPalette.mid,radius=r*.24f,
            center=Offset(x-r*.18f,y+r*.13f),style=Stroke(2.5f))
        drawCircle(PencilAtlasPalette.mid,radius=r*.11f,
            center=Offset(x+r*.29f,y-r*.21f))
    }
    // The two radial complexes illustrate water-balance organs; spoke count
    // is illustrative, and their timing is not implied to be synchronized.
    for((x,y) in listOf(280f to 230f,708f to 348f)) {
        val o=Offset(x,y)
        drawCircle(PencilAtlasPalette.cell,radius=31f,center=o)
        drawCircle(PencilAtlasPalette.dark,radius=31f,center=o,
            style=Stroke(3.3f))
        drawCircle(PencilAtlasPalette.hatch,radius=14f,center=o,
            style=Stroke(1.9f))
        for(i in 0 until 7) {
            val a=2*PI*i/7
            val v=Offset(cos(a).toFloat(),sin(a).toFloat())
            drawLine(PencilAtlasPalette.dark,o+v*35f,o+v*65f,2.7f)
            drawLine(PencilAtlasPalette.mid,o+v*67f,o+v*74f,1.3f)
        }
    }
    val funnel=Path().apply {
        moveTo(547f,387f)
        cubicTo(555f,353f,570f,339f,590f,322f)
        cubicTo(596f,316f,612f,315f,627f,320f)
    }
    drawPath(funnel,PencilAtlasPalette.dark,style=Stroke(3.3f))
    drawPath(funnel,PencilAtlasPalette.mid,style=Stroke(1f))
}
