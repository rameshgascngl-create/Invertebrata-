package com.gasczoology.invertebratelab.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
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
internal fun rememberRequiredRasterBitmap(relativeStem: String, pngOnly: Boolean = false): ImageBitmap? {
    val context = LocalContext.current
    val directory = relativeStem.substringBeforeLast('/')
    val available = remember(context, directory) {
        runCatching {
            context.assets.list(directory).orEmpty()
                .map { "$directory/$it" }.toSet()
        }.getOrDefault(emptySet())
    }
    val asset = remember(relativeStem, available, pngOnly) {
        val selected = if (relativeStem.startsWith("review_only/pila/")) {
            // Editorial-only assets are intentionally outside mandatory curriculum policy.
            // Never broaden the production type-study whitelist.
            val filename = relativeStem.substringAfterLast('/')
            val legacy = filename in setOf(
                "01_shell_ventral_REFERENCE", "02_operculum_outer_inner_REFERENCE",
                "03_shell_dorsal_REFERENCE", "04_animal_front_view_REFERENCE",
            ) && relativeStem.startsWith("review_only/pila/")
            val generated = relativeStem.startsWith("review_only/pila/generated_12/") &&
                Regex("(0[1-9]|1[0-2])_[a-z0-9_]+_REVIEW").matches(filename)
            if ((legacy || generated) && "$relativeStem.png" in available) "$relativeStem.png" else null
        } else if (relativeStem.startsWith("review_only/asterias/")) {
            // Candidate illustrations only; never satisfy the mandatory textbook slots.
            val filename = relativeStem.substringAfterLast('/')
            val allowed = relativeStem.startsWith("review_only/asterias/") &&
                Regex("(external|water_vascular|digestive|nervous)_REVIEW").matches(filename)
            if (allowed && "$relativeStem.png" in available) "$relativeStem.png" else null
        } else RasterArtworkPolicy.selectAsset(relativeStem, available)
        if (pngOnly && selected?.endsWith(".png") != true) null else selected
    }
    return remember(context, asset) {
        asset?.let { path -> PlateBitmapDecoder.decode(context.assets, path) }
    }
}
