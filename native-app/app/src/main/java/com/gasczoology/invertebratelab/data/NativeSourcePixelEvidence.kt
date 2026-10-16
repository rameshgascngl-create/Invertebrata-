package com.gasczoology.invertebratelab.data

import kotlin.math.floor
import kotlin.math.min

/**
 * Independent, bounded inspection window around a MARK ON THE ORIGINAL RASTER.
 * This is never an inferred nucleus/flagellum position or an academic signoff.
 */
data class NativePixelWindow(
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int,
    val focusX: Float,
    val focusY: Float,
)

/**
 * No APPROVED state exists by design. "Visible candidate" means only that a
 * human reviewer believes a feature is visually located in a given image.
 * Morphotype, source and biology remain independently unverified.
 */
enum class NativeDraftFinding {
    NOT_REVIEWED,
    VISIBLE_CANDIDATE,
    CANNOT_RESOLVE,
    WRONG_STRUCTURE,
}

object NativeSourcePixelEvidence {
    const val NOTE_LIMIT = 400
    private const val MAX_RADIUS = 128

    fun cleanNote(text: String): String =
        text.take(NOTE_LIMIT).filter { it != '\u0000' }

    fun window(
        pixelX: Float,
        pixelY: Float,
        imageWidth: Int,
        imageHeight: Int,
        radius: Int = 72,
    ): NativePixelWindow? {
        if (imageWidth <= 0 || imageHeight <= 0 || radius !in 8..MAX_RADIUS ||
            !pixelX.isFinite() || !pixelY.isFinite() ||
            pixelX < 0f || pixelY < 0f ||
            pixelX >= imageWidth.toFloat() || pixelY >= imageHeight.toFloat()
        ) return null
        // Preserve the ORIGINAL pixel coordinate system. Do not resample,
        // rotate, or clamp a marker outside the source image.
        val boxWidth = min(imageWidth, radius * 2 + 1)
        val boxHeight = min(imageHeight, radius * 2 + 1)
        val px = floor(pixelX.toDouble()).toInt()
        val py = floor(pixelY.toDouble()).toInt()
        val left = (px - radius).coerceIn(0, imageWidth - boxWidth)
        val top = (py - radius).coerceIn(0, imageHeight - boxHeight)
        return NativePixelWindow(
            left = left,
            top = top,
            width = boxWidth,
            height = boxHeight,
            focusX = pixelX - left,
            focusY = pixelY - top,
        )
    }

    fun isHashBoundSha256(hash: String): Boolean =
        hash.length == 64 && hash.all { it in '0'..'9' || it in 'a'..'f' }
}
