package com.gasczoology.invertebratelab.data

/**
 * N2.3B1 prototype hit-regions for the EXTERNAL/CILIA plate only.
 * All locations are schematic normalized coordinates, NOT accepted morphology.
 *
 * This does not replace pending geometry keys in ParameciumAnatomyDraft.
 */
data class PrototypeHotspot(
    val featureId: String,
    val x: Float,
    val y: Float,
    val radius: Float,
) {
    init {
        require(featureId.isNotBlank())
        require(x.isFinite() && y.isFinite() && radius.isFinite())
        require(x in 0f..1f && y in 0f..1f)
        require(radius > 0f && radius <= 0.25f)
    }
}

/**
 * One aspect-preserving transform shared by Compose drawing AND pointer hit tests.
 * Letterbox bars are not interactive anatomical regions.
 * Coordinates are in native Canvas pixels; no Android dependencies.
 */
class PrototypeCanvasViewport private constructor(
    val canvasWidth: Float,
    val canvasHeight: Float,
    val scale: Float,
    val left: Float,
    val top: Float,
) {
    val contentWidth: Float get() = ParameciumExternalGeometry.REFERENCE_WIDTH * scale
    val contentHeight: Float get() = ParameciumExternalGeometry.REFERENCE_HEIGHT * scale

    fun referenceToCanvas(x: Float, y: Float): Pair<Float, Float> =
        (left + x * scale) to (top + y * scale)

    fun canvasToReference(x: Float, y: Float): Pair<Float, Float>? {
        if (!x.isFinite() || !y.isFinite() ||
            x < left || x > left + contentWidth ||
            y < top || y > top + contentHeight
        ) return null
        return ((x - left) / scale) to ((y - top) / scale)
    }

    companion object {
        fun fit(width: Float, height: Float): PrototypeCanvasViewport {
            require(width.isFinite() && height.isFinite() && width > 0f && height > 0f)
            val factor = minOf(
                width / ParameciumExternalGeometry.REFERENCE_WIDTH,
                height / ParameciumExternalGeometry.REFERENCE_HEIGHT,
            )
            require(factor > 0f && factor.isFinite())
            val left = (width - ParameciumExternalGeometry.REFERENCE_WIDTH * factor) / 2f
            val top = (height - ParameciumExternalGeometry.REFERENCE_HEIGHT * factor) / 2f
            return PrototypeCanvasViewport(width, height, factor, left, top)
        }
    }
}

object ParameciumExternalGeometry {
    const val REFERENCE_WIDTH = 1000f
    const val REFERENCE_HEIGHT = 600f

    // Centered on provisional schematic landmarks; an expert must review
    // organism orientation and actual hit shapes before biological acceptance.
    val hotspots: List<PrototypeHotspot> = listOf(
        PrototypeHotspot("pellicle", 0.30f, 0.27f, 0.087f),
        PrototypeHotspot("somatic-cilia", 0.64f, 0.165f, 0.085f),
        PrototypeHotspot("oral-groove", 0.55f, 0.705f, 0.085f),
        PrototypeHotspot("cytoproct", 0.76f, 0.666f, 0.073f),
        PrototypeHotspot("trichocysts", 0.31f, 0.70f, 0.075f),
    )

    fun validateAgainst(plate: NativeAnatomyPlate) {
        require(plate.organismId == "paramecium" && plate.plateId == "external-cilia")
        require(plate.status == AcademicWorkStatus.DRAFT_UNVERIFIED)
        require(plate.review == null)
        val ids = plate.features.map { it.id }
        require(ids.size == ids.toSet().size)
        require(hotspots.map { it.featureId } == ids) {
            "Every external feature needs exactly one prototype control"
        }
        require(plate.features.all {
            it.geometryKey.startsWith("pending-") && it.touchTargetKey.startsWith("pending-")
        }) { "Do not promote prototype coordinates into accepted anatomical geometry" }
    }

    /**
     * Pixel-perfect inverse of the shared viewport transform. All hit radii
     * are measured in the same (undistorted) reference units as the artwork.
     * No taps in empty letterbox space are treated as touching the organism.
     */
    fun hitCanvas(x: Float, y: Float, width: Float, height: Float): String? {
        if (!x.isFinite() || !y.isFinite() ||
            !width.isFinite() || !height.isFinite() ||
            width <= 0f || height <= 0f
        ) return null
        val view = PrototypeCanvasViewport.fit(width, height)
        val point = view.canvasToReference(x, y) ?: return null
        val refX = point.first
        val refY = point.second
        return hotspots.asSequence()
            .map { area ->
                val dx = refX - area.x * REFERENCE_WIDTH
                val dy = refY - area.y * REFERENCE_HEIGHT
                area to (dx * dx + dy * dy)
            }
            .filter { (area, squaredDistance) ->
                val radius = area.radius * REFERENCE_HEIGHT
                squaredDistance <= radius * radius
            }
            .minByOrNull { it.second }?.first?.featureId
    }

    /** Returns null outside valid circular prototype landmarks; never guesses an organ. */
    fun hitNormalized(x: Float, y: Float): String? {
        if (!x.isFinite() || !y.isFinite() || x !in 0f..1f || y !in 0f..1f) return null
        return hotspots.asSequence()
            .map { it to ((x - it.x) * (x - it.x) + (y - it.y) * (y - it.y)) }
            .filter { (area, d2) -> d2 <= area.radius * area.radius }
            .minByOrNull { it.second }?.first?.featureId
    }

    init {
        validateAgainst(ParameciumAnatomyDraft.plates.single {
            it.plateId == "external-cilia"
        })
    }
}
