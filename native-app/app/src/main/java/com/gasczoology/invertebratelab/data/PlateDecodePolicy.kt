package com.gasczoology.invertebratelab.data

/**
 * Pure (JVM-testable) sizing rules for anatomical raster plates.
 *
 * Plates are authored at up to ~2.5 k pixels. Decoding a 2292x2483 RGBA bitmap
 * costs ~23 MB, and an unbounded decode of a 4608x4140 master costs ~76 MB,
 * which exceeds the 100 MB single-bitmap draw limit on many devices and shows
 * up to students as a red "image not available" box. Decoding is therefore
 * capped by [MAX_EDGE_PX] using power-of-two sub-sampling.
 */
object PlateDecodePolicy {
    /** Longest edge decoded at full resolution; larger masters are halved until they fit. */
    const val MAX_EDGE_PX = 2560

    /** Hard ceiling accepted by the intake tool and the packaging test (GPU texture safety). */
    const val MAX_PACKAGED_EDGE_PX = 4096

    fun sampleSizeFor(width: Int, height: Int, maxEdge: Int = MAX_EDGE_PX): Int {
        require(maxEdge > 0) { "maxEdge must be positive" }
        if (width <= 0 || height <= 0) return 1
        var sample = 1
        while (width / sample > maxEdge || height / sample > maxEdge) sample *= 2
        return sample
    }

    /** Approximate decoded ARGB_8888 size in bytes after sub-sampling. */
    fun decodedBytes(width: Int, height: Int, maxEdge: Int = MAX_EDGE_PX): Long {
        val s = sampleSizeFor(width, height, maxEdge)
        return (width / s).toLong().coerceAtLeast(1) * (height / s).toLong().coerceAtLeast(1) * 4L
    }
}
