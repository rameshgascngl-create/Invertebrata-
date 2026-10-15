package com.gasczoology.invertebratelab.data

import kotlin.math.min

/**
 * Pure Kotlin geometry for Android Image(contentScale = ContentScale.Fit)
 * embedded in a viewport with a centre-pivot graphicsLayer transform.
 *
 * Coordinates refer to real source-raster pixels, NOT invented organ hotspots.
 * Producing a candidate coordinate does not constitute academic approval.
 */
data class RasterPixelLocation(
    val pixelX: Float,
    val pixelY: Float,
    val normalizedX: Float,
    val normalizedY: Float,
)

object NativeRasterCalibration {
    private data class Fit(
        val scale: Float,
        val offsetX: Float,
        val offsetY: Float,
        val centerX: Float,
        val centerY: Float,
    )

    private fun fit(
        viewportWidth: Float,
        viewportHeight: Float,
        rasterWidth: Int,
        rasterHeight: Int,
    ): Fit? {
        if (viewportWidth <= 0f || viewportHeight <= 0f ||
            rasterWidth <= 0 || rasterHeight <= 0) return null
        val scale = min(
            viewportWidth / rasterWidth.toFloat(),
            viewportHeight / rasterHeight.toFloat(),
        )
        if (!scale.isFinite() || scale <= 0f) return null
        return Fit(
            scale = scale,
            offsetX = (viewportWidth - rasterWidth * scale) * .5f,
            offsetY = (viewportHeight - rasterHeight * scale) * .5f,
            centerX = viewportWidth * .5f,
            centerY = viewportHeight * .5f,
        )
    }

    /** Undo the centred graphicsLayer scale+translation, then the bitmap fit. */
    fun hit(
        tapX: Float,
        tapY: Float,
        viewportWidth: Float,
        viewportHeight: Float,
        rasterWidth: Int,
        rasterHeight: Int,
        zoom: Float = 1f,
        panX: Float = 0f,
        panY: Float = 0f,
    ): RasterPixelLocation? {
        val f = fit(viewportWidth, viewportHeight, rasterWidth, rasterHeight)
            ?: return null
        if (!zoom.isFinite() || zoom <= 0f || !panX.isFinite() || !panY.isFinite() ||
            !tapX.isFinite() || !tapY.isFinite()) return null
        val localX = (tapX - f.centerX - panX) / zoom + f.centerX
        val localY = (tapY - f.centerY - panY) / zoom + f.centerY
        val x = (localX - f.offsetX) / f.scale
        val y = (localY - f.offsetY) / f.scale
        // Do not clamp outside taps onto an organ: reject letterbox/background.
        if (x < 0f || y < 0f || x > rasterWidth || y > rasterHeight) return null
        return RasterPixelLocation(
            pixelX = x,
            pixelY = y,
            normalizedX = x / rasterWidth,
            normalizedY = y / rasterHeight,
        )
    }

    /** Forward-map an image pixel to its current on-screen marker location. */
    fun project(
        pixelX: Float,
        pixelY: Float,
        viewportWidth: Float,
        viewportHeight: Float,
        rasterWidth: Int,
        rasterHeight: Int,
        zoom: Float = 1f,
        panX: Float = 0f,
        panY: Float = 0f,
    ): Pair<Float, Float>? {
        val f = fit(viewportWidth, viewportHeight, rasterWidth, rasterHeight)
            ?: return null
        if (!zoom.isFinite() || zoom <= 0f || !panX.isFinite() || !panY.isFinite())
            return null
        if (pixelX !in 0f..rasterWidth.toFloat() ||
            pixelY !in 0f..rasterHeight.toFloat()) return null
        val localX = f.offsetX + pixelX * f.scale
        val localY = f.offsetY + pixelY * f.scale
        return Pair(
            f.centerX + (localX - f.centerX) * zoom + panX,
            f.centerY + (localY - f.centerY) * zoom + panY,
        )
    }
}
