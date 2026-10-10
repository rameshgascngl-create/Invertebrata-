package com.gasczoology.invertebratelab.data

import kotlin.math.min

/** Rendering and hit-testing use the same uniformly fitted original-art frame. */
data class NuclearFigureFrame(val scale: Float, val left: Float, val top: Float) {
    fun x(logical: Float) = left + logical * scale
    fun y(logical: Float) = top + logical * scale
    fun logicalX(screen: Float) = (screen - left) / scale
    fun logicalY(screen: Float) = (screen - top) / scale
}
object NuclearFigureGeometry {
    const val width = 1000f
    const val height = 540f
    fun fit(w: Float, h: Float): NuclearFigureFrame {
        val s = min(w / width, h / height).coerceAtLeast(.0001f)
        return NuclearFigureFrame(s, (w - width * s) / 2, (h - height * s) / 2)
    }
    fun nucleusAt(x: Float, y: Float, stage: NuclearView? = null, phase: Float = 0f): String? {
        if(stage!=null && stage.id in ParameciumFissionChapter.chapter.views.map{it.id} && x<600f) {
            return NuclearBodyGeometry.fission(stage,phase).marks.firstOrNull {
                kotlin.math.abs(x-it.x)<=it.width/2+8f && kotlin.math.abs(y-it.y)<=it.height/2+8f
            }?.kind
        }
        return when {
        x in 625f..950f && y in 65f..250f -> "macronucleus"
        x in 650f..930f && y in 285f..490f -> "micronucleus"
        x in 345f..415f && y in 200f..285f -> "micronucleus"
        x in 210f..340f && y in 210f..335f -> "macronucleus"
        else -> null
        }
    }
}
