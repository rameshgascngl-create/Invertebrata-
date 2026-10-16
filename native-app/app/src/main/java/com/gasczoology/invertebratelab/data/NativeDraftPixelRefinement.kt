package com.gasczoology.invertebratelab.data

/**
 * U1.12 — discrete pixel refinement in a debug-only zoological raster reviewer.
 *
 * Returns null instead of snapping markers to an edge or fabricating anatomy.
 * This modifies a *candidate coordinate*, never the immutable source image.
 */
object NativeDraftPixelRefinement {
    fun nudge(
        point: RasterPixelLocation,
        dx: Int,
        dy: Int,
        imageWidth: Int,
        imageHeight: Int,
    ): RasterPixelLocation? {
        if (imageWidth <= 0 || imageHeight <= 0) return null
        if (dx !in -1..1 || dy !in -1..1 || (dx == 0 && dy == 0)) return null
        if (!point.pixelX.isFinite() || !point.pixelY.isFinite()) return null
        val x = point.pixelX + dx
        val y = point.pixelY + dy
        if (!x.isFinite() || !y.isFinite() ||
            x < 0f || y < 0f || x >= imageWidth.toFloat() ||
            y >= imageHeight.toFloat()) return null
        return RasterPixelLocation(
            pixelX = x,
            pixelY = y,
            normalizedX = x / imageWidth.toFloat(),
            normalizedY = y / imageHeight.toFloat(),
        )
    }
}
