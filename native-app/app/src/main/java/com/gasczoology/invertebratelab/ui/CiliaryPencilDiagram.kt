package com.gasczoology.invertebratelab.ui

import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.gasczoology.invertebratelab.data.ParameciumCiliaryAcademicContent
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.min

/** Original enlarged, simplified teaching projection; no microscope measurements. */
internal fun DrawScope.drawCiliaryPencilDiagram(stage: String, selectedPart: String) {
    val w = size.width
    val h = size.height
    val ink = PencilAtlasPalette.graphite
    val selected = PencilAtlasPalette.selected
    val baseline = h * .73f
    val line = 1.1.dp.toPx()
    fun colour(part: String): Color = if (part == selectedPart) selected else ink
    drawRect(PencilAtlasPalette.paper)
    drawRect(PencilAtlasPalette.cell, Offset(w * .07f, baseline), Size(w * .86f, h * .22f))
    for (i in 0..32) {
        val x = w * (.07f + i * .026f)
        drawLine(PencilAtlasPalette.hatch, Offset(x, baseline + h * .04f),
            Offset(x + w * .023f, baseline + h * .15f), strokeWidth = .5.dp.toPx())
    }
    // Surface / cortical layer; basal bodies are BELOW it, not dots in a membrane.
    drawLine(ink, Offset(w * .07f, baseline), Offset(w * .93f, baseline), line * 1.4f)
    drawLine(PencilAtlasPalette.mid, Offset(w * .07f, baseline + h * .022f),
        Offset(w * .93f, baseline + h * .022f), line * .65f)
    for (i in 0 until 11) {
        val x = w * (.10f + .074f * i)
        val phase = if (stage == "wave") i % 4 else when (stage) {
            "effective" -> 1
            "recovery" -> 2
            else -> 0
        }
        val lift = h * if (phase == 2 || phase == 3) .16f else .34f
        val lean = w * when (phase) { 1 -> .055f; 2 -> -.062f; 3 -> -.02f; else -> .01f }
        val contour = Path().apply {
            moveTo(x, baseline)
            if (phase == 2 || phase == 3) {
                cubicTo(x - w * .048f, baseline - lift * 1.2f,
                    x - w * .080f, baseline - lift * .3f,
                    x + lean, baseline - lift * .5f)
            } else {
                cubicTo(x - lean * .2f, baseline - lift * .34f,
                    x + lean * .45f, baseline - lift * .77f,
                    x + lean, baseline - lift)
            }
        }
        drawPath(contour, colour("cilia"), style = Stroke(line))
        // Second faint graphite contour gives authored pencil character.
        drawPath(contour, PencilAtlasPalette.mid, style = Stroke(line * .32f))
        val bodyColour = colour("basal")
        drawRoundRect(PencilAtlasPalette.paper, Offset(x - line * 2.2f, baseline + h * .024f),
            Size(line * 4.4f, h * .09f))
        drawRect(bodyColour, Offset(x - line * 2.2f, baseline + h * .024f),
            Size(line * 4.4f, h * .09f), style = Stroke(line * .7f))
        drawLine(bodyColour, Offset(x, baseline + h * .024f), Offset(x, baseline + h * .11f),
            line * .55f)
        drawLine(ink, Offset(x, baseline + h * .11f),
            Offset(x + w * .024f, baseline + h * .15f), line * .65f)
    }
    // Enlarged cross-sections ABOVE the side view. Insets are different scales.
    // Keep enlarged insets clear of the extended ciliary tips in both orientations.
    val radius = min(h * .13f, w * .125f)
    val tubule = radius * .10f
    fun ring(center: Offset, basal: Boolean) {
        val part = if (basal) "basal" else "axoneme"
        val colour = colour(part)
        if (!basal) drawCircle(colour, radius * 1.25f, center, style = Stroke(line * .7f))
        val count = if (basal) ParameciumCiliaryAcademicContent.BASAL_BODY_TRIPLETS
            else ParameciumCiliaryAcademicContent.PERIPHERAL_DOUBLETS
        for (j in 0 until count) {
            val a = 2.0 * PI * j / count
            val radial = Offset(cos(a).toFloat(), sin(a).toFloat())
            val tangent = Offset(-radial.y, radial.x)
            val position = center + radial * radius
            // A complete tubule; B/C partial arcs share its wall in this schematic.
            drawCircle(colour, tubule, position, style = Stroke(line * .65f))
            val b = position + tangent * (tubule * 1.6f)
            drawArc(colour, (a * 180 / PI).toFloat() - 70f, 260f, false,
                b - Offset(tubule, tubule), Size(tubule * 2, tubule * 2),
                style = Stroke(line * .65f))
            if (basal) {
                val c = b + tangent * (tubule * 1.55f)
                drawArc(colour, (a * 180 / PI).toFloat() - 70f, 260f, false,
                    c - Offset(tubule, tubule), Size(tubule * 2, tubule * 2),
                    style = Stroke(line * .65f))
                drawLine(PencilAtlasPalette.mid, center, position - radial * (tubule * 1.4f),
                    line * .45f)
            } else {
                // Radial spokes: simplified, not molecular dimensions.
                drawLine(PencilAtlasPalette.mid, center + radial * radius * .35f,
                    position - radial * (tubule * 1.4f), line * .45f)
            }
        }
        if (basal) {
            // Cartwheel hub is protein; never draw it as a central microtubule pair.
            drawCircle(PencilAtlasPalette.mid, radius * .13f, center, style = Stroke(line * .5f))
        } else {
            repeat(ParameciumCiliaryAcademicContent.CENTRAL_SINGLETS) {
                drawCircle(colour, tubule * 1.05f,
                    center + Offset((it * 2 - 1) * tubule * 1.5f, 0f),
                    style = Stroke(line * .8f))
            }
        }
    }
    ring(Offset(w * .26f, h * .19f), basal = true)
    ring(Offset(w * .75f, h * .19f), basal = false)
}
