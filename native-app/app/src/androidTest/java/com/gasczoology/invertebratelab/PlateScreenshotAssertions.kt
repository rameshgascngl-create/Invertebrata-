package com.gasczoology.invertebratelab

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.geometry.Rect
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/** The ciliary-locomotion teaching plate is this PNG (PNG-only policy). */
internal const val CILIARY_PNG_ASSET = "unit1/concepts/paramecium_ciliary_locomotion.png"

internal fun loadAssetBitmap(path: String): Bitmap =
    checkNotNull(
        InstrumentationRegistry.getInstrumentation().targetContext.assets.open(path)
            .use { BitmapFactory.decodeStream(it) }
    ) { "Cannot decode asset: $path" }

/** Grey-level thumbnail (size x size) of a region, by sparse box averaging. */
private fun thumbnail(src: Bitmap, left: Int, top: Int, width: Int, height: Int, size: Int): DoubleArray {
    val out = DoubleArray(size * size)
    for (ty in 0 until size) for (tx in 0 until size) {
        val x0 = left + tx * width / size
        val x1 = max(x0 + 1, left + (tx + 1) * width / size)
        val y0 = top + ty * height / size
        val y1 = max(y0 + 1, top + (ty + 1) * height / size)
        val stepX = max(1, (x1 - x0) / 6)
        val stepY = max(1, (y1 - y0) / 6)
        var sum = 0.0
        var n = 0
        var y = y0
        while (y < y1) {
            var x = x0
            while (x < x1) {
                val c = src.getPixel(min(x, src.width - 1), min(y, src.height - 1))
                sum += (((c shr 16) and 0xFF) * 299 + ((c shr 8) and 0xFF) * 587 + (c and 0xFF) * 114) / 1000.0
                n++
                x += stepX
            }
            y += stepY
        }
        out[ty * size + tx] = if (n > 0) sum / n else 0.0
    }
    return out
}

/** Pearson correlation; 0.0 when either thumbnail is flat (blank screen or flat placeholder). */
internal fun correlation(a: DoubleArray, b: DoubleArray): Double {
    val ma = a.average()
    val mb = b.average()
    var cov = 0.0
    var va = 0.0
    var vb = 0.0
    for (i in a.indices) {
        val da = a[i] - ma
        val db = b[i] - mb
        cov += da * db
        va += da * da
        vb += db * db
    }
    return if (va < 1e-6 || vb < 1e-6) 0.0 else cov / sqrt(va * vb)
}

/**
 * Evidence that the REAL screenshot shows the PNG plate: the on-screen rectangle where a
 * ContentScale.Fit image is painted must correlate with the PNG asset itself. A blank
 * screen, a text placeholder, or a different picture correlates poorly. This replaces the
 * old "pencil-paper colour" pixel count, which only described the removed Canvas drawing.
 *
 * [nodeBounds] is the tagged node's boundsInRoot in pixels; the image is centred in it.
 * Assumes the Compose root is at the screen origin (full-screen, edge-to-edge activity).
 */
internal fun assertPlateShowsAsset(
    screenshot: Bitmap,
    nodeBounds: Rect,
    asset: Bitmap,
    minCorrelation: Double = 0.6,
) {
    val scale = min(nodeBounds.width / asset.width, nodeBounds.height / asset.height)
    val w = (asset.width * scale).toInt()
    val h = (asset.height * scale).toInt()
    val left = (nodeBounds.left + (nodeBounds.width - w) / 2f).toInt()
    val top = (nodeBounds.top + (nodeBounds.height - h) / 2f).toInt()
    assertTrue(
        "Plate rectangle ($left,$top ${w}x$h) is outside the ${screenshot.width}x${screenshot.height} screenshot",
        w > 16 && h > 16 && left >= 0 && top >= 0 &&
            left + w <= screenshot.width && top + h <= screenshot.height,
    )
    val r = correlation(
        thumbnail(screenshot, left, top, w, h, 24),
        thumbnail(asset, 0, 0, asset.width, asset.height, 24),
    )
    assertTrue(
        "Screenshot region does not show the PNG plate (correlation %.2f < %.2f)".format(r, minCorrelation),
        r >= minCorrelation,
    )
}
