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

/** Explicit orientation-linked, original Bézier geometry; still a schematic. */
data class PrototypePoint(val x: Float, val y: Float) {
    init {
        require(x.isFinite() && y.isFinite() && x in 0f..1000f && y in 0f..600f)
    }
}

data class PrototypeCubicSegment(
    val control1: PrototypePoint,
    val control2: PrototypePoint,
    val end: PrototypePoint,
)

/**
 * N2.3B3 original contours: blunt anterior left, tapered posterior right.
 * These are hand-authored candidates, NOT reconstructed microscopy coordinates.
 */
object ParameciumSchematicContour {
    val anterior = PrototypePoint(155f, 275f)
    val posteriorTip = PrototypePoint(874f, 307f)
    val segments: List<PrototypeCubicSegment> = listOf(
        PrototypeCubicSegment(
            PrototypePoint(125f, 190f), PrototypePoint(220f, 120f),
            PrototypePoint(360f, 105f)),
        PrototypeCubicSegment(
            PrototypePoint(540f, 85f), PrototypePoint(720f, 125f),
            PrototypePoint(815f, 228f)),
        PrototypeCubicSegment(
            PrototypePoint(839f, 255f), PrototypePoint(859f, 288f),
            posteriorTip),
        PrototypeCubicSegment(
            PrototypePoint(823f, 387f), PrototypePoint(735f, 451f),
            PrototypePoint(615f, 477f)),
        PrototypeCubicSegment(
            PrototypePoint(475f, 515f), PrototypePoint(330f, 488f),
            PrototypePoint(226f, 417f)),
        PrototypeCubicSegment(
            PrototypePoint(165f, 370f), PrototypePoint(145f, 322f),
            anterior),
    )

    init {
        require(segments.size == 6 && segments.last().end == anterior)
        require(segments[2].end == posteriorTip)
        require(anterior.x < 175f && posteriorTip.x > 850f)
        require(anterior.x < posteriorTip.x)
        require(segments.all { it.end.x in 0f..1000f })
    }
}

/**
 * N2.3C2: a CANDIDATE illustration of the closed cytoproct ridge.
 *
 * P. caudatum reference: CIL:39181, a TEM SECTION showing a ridge along
 * the ventral posterior suture. A thin section DOES NOT establish the exact
 * whole-cell xy coordinates. These three points are schematic and unapproved.
 */
object ParameciumCytoproctRidgeCandidate {
    const val SOURCE_URL = "https://www.cellimagelibrary.org/images/39181"
    val start = PrototypePoint(765f, 412f)
    val control = PrototypePoint(775f, 423f)
    val end = PrototypePoint(787f, 428f)

    fun verifySchematicContract() {
        val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
        val feature = plate.features.single { it.id == "cytoproct" }
        val evidence = ParameciumExternalEvidence.records.single { it.featureId == "cytoproct" }
        val hotspot = ParameciumExternalGeometry.hotspots.single { it.featureId == "cytoproct" }
        require(evidence.sourceUrl == SOURCE_URL &&
            evidence.scope == ParameciumSourceScope.CAUDATUM &&
            !evidence.positionReviewed && !evidence.tamilReviewed)
        require(plate.status == AcademicWorkStatus.DRAFT_UNVERIFIED && plate.review == null)
        require(feature.geometryKey.startsWith("pending-") &&
            feature.touchTargetKey.startsWith("pending-"))
        // The existing touch hotspot must cover the draft ridge; retain its
        // accessible 48dp alternate control and exact location.
        val cx = hotspot.x * ParameciumExternalGeometry.REFERENCE_WIDTH
        val cy = hotspot.y * ParameciumExternalGeometry.REFERENCE_HEIGHT
        val r = hotspot.radius * ParameciumExternalGeometry.REFERENCE_HEIGHT
        require(listOf(start, control, end).all {
            val dx = it.x - cx
            val dy = it.y - cy
            dx * dx + dy * dy < r * r
        })
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
        PrototypeHotspot("cytoproct", 0.776f, 0.70f, 0.073f),
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
        return hitAtReference(point.first, point.second)
    }

    /**
     * Both public hit APIs use the same CIRCULAR radius in undistorted
     * reference-space units (1000x600), not independent normalized circles.
     */
    fun hitNormalized(x: Float, y: Float): String? {
        if (!x.isFinite() || !y.isFinite() || x !in 0f..1f || y !in 0f..1f) return null
        return hitAtReference(x * REFERENCE_WIDTH, y * REFERENCE_HEIGHT)
    }

    private fun hitAtReference(refX: Float, refY: Float): String? =
        hotspots.asSequence()
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

    init {
        validateAgainst(ParameciumAnatomyDraft.plates.single {
            it.plateId == "external-cilia"
        })
    }
}
