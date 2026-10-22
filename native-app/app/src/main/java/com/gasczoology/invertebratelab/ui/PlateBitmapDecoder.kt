package com.gasczoology.invertebratelab.ui

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.gasczoology.invertebratelab.data.PlateDecodePolicy

/**
 * Single decoding path for packaged plate rasters (PNG or WebP).
 *
 * - bounds are read first, then the bitmap is sub-sampled to PlateDecodePolicy.MAX_EDGE_PX;
 * - decoded bitmaps are kept in a size-bounded LRU so a plate that is shown inline and
 *   then full-screen (or revisited) is decoded once;
 * - any failure (missing file, corrupt data, OutOfMemoryError) returns null so callers
 *   keep their visible missing-plate notice. It never draws a substitute.
 */
internal object PlateBitmapDecoder {
    private val cache = object : LruCache<String, Bitmap>(cacheBytes()) {
        override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount
    }

    private fun cacheBytes(): Int =
        (Runtime.getRuntime().maxMemory() / 6L).coerceIn(8L shl 20, 96L shl 20).toInt()

    fun decode(assets: AssetManager, path: String): ImageBitmap? {
        cache.get(path)?.let { if (!it.isRecycled) return it.asImageBitmap() }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        val readable = runCatching {
            assets.open(path).use { BitmapFactory.decodeStream(it, null, bounds) }
        }.isSuccess
        if (!readable || bounds.outWidth <= 0 || bounds.outHeight <= 0) return null
        var sample = PlateDecodePolicy.sampleSizeFor(bounds.outWidth, bounds.outHeight)
        repeat(3) {
            val options = BitmapFactory.Options().apply { inSampleSize = sample }
            val bitmap = runCatching {
                assets.open(path).use { BitmapFactory.decodeStream(it, null, options) }
            }.getOrNull()
            if (bitmap != null) {
                cache.put(path, bitmap)
                return bitmap.asImageBitmap()
            }
            sample *= 2 // likely OutOfMemoryError: retry once or twice at lower resolution
        }
        return null
    }
}
