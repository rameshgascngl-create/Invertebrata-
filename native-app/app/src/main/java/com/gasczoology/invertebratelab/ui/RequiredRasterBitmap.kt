package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.gasczoology.invertebratelab.data.RasterArtworkPolicy

/**
 * Shared source of anatomy imagery for current/future native type studies.
 * PNG is preferred; WebP is allowed. Loading errors return null and callers
 * MUST show a visible missing-plate error. Never draw replacement anatomy.
 *
 * Composables may draw touch/zoom/selection indicators ON TOP of the bitmap.
 */
@Composable
internal fun rememberRequiredRasterBitmap(relativeStem: String): ImageBitmap? {
    val context = LocalContext.current
    val directory = relativeStem.substringBeforeLast('/')
    val available = remember(context, directory) {
        runCatching {
            context.assets.list(directory).orEmpty()
                .map { "$directory/$it" }.toSet()
        }.getOrDefault(emptySet())
    }
    val asset = remember(relativeStem, available) {
        RasterArtworkPolicy.selectAsset(relativeStem, available)
    }
    return remember(context, asset) {
        asset?.let { path ->
            runCatching {
                context.assets.open(path).use { stream ->
                    BitmapFactory.decodeStream(stream)?.asImageBitmap()
                }
            }.getOrNull()
        }
    }
}
