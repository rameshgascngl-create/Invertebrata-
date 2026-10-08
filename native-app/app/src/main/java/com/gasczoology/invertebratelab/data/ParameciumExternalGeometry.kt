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
        PrototypeHotspot("trichocysts", 0.32f, 0.595f, 0.086f),
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
