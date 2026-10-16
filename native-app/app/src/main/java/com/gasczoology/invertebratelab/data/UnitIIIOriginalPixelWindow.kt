package com.gasczoology.invertebratelab.data

import kotlin.math.floor
import kotlin.math.min

/**
 * Source-image coordinates only. Does NOT locate or identify anatomy.
 * The close-up may show an incorrect point; human checking is mandatory.
 */
internal data class UnitIIIOriginalPixelWindow(
    val x: Int, val y: Int, val width: Int, val height: Int,
    val focusX: Float, val focusY: Float,
)

internal object UnitIIIOriginalPixelEvidence {
    const val MAX_NOTE_LENGTH = 400
    fun boundedNote(value: String) = value.take(MAX_NOTE_LENGTH).filter { it != '\u0000' }

    fun window(
        sourceWidth: Int,
        sourceHeight: Int,
        x: Float, y: Float,
        radius: Int = 90,
    ): UnitIIIOriginalPixelWindow? {
        if (sourceWidth <= 0 || sourceHeight <= 0 || radius !in 8..128 ||
            !x.isFinite() || !y.isFinite() || x < 0f || y < 0f ||
            x >= sourceWidth || y >= sourceHeight
        ) return null
        val width = min(sourceWidth, 2 * radius + 1)
        val height = min(sourceHeight, 2 * radius + 1)
        val left = (floor(x.toDouble()).toInt() - radius).coerceIn(0, sourceWidth - width)
        val top = (floor(y.toDouble()).toInt() - radius).coerceIn(0, sourceHeight - height)
        return UnitIIIOriginalPixelWindow(
            left, top, width, height,
            x - left.toFloat(), y - top.toFloat(),
        )
    }
}

/** Review statuses never imply publication, species identity or approval. */
internal enum class UnitIIIReviewFinding {
    NOT_REVIEWED,
    VISIBLE_CANDIDATE,
    CANNOT_RESOLVE,
    WRONG_STRUCTURE,
}
