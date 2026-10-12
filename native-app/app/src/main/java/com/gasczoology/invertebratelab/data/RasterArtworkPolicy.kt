package com.gasczoology.invertebratelab.data

/**
 * REQUIRED for newly authored native type-study plates, Units II–V and
 * future improvements of Unit I. Anatomy is a labelled PNG or WebP master,
 * never a runtime SVG/Canvas/Compose substitute. Native interactive hotspot
 * and selection overlays are allowed above the decoded raster.
 *
 * An AVAILABLE raster is not necessarily scientifically or artistically
 * accepted. Scientific, bilingual, accessibility and device QA remain
 * independent mandatory gates.
 */
object RasterArtworkPolicy {
    // PNG first for lossless labelled artwork; WebP for compact offline delivery.
    private val formats = listOf("png", "webp") // PNG first
    private val stemPattern = Regex("[a-z0-9]+(?:[-_][a-z0-9]+)*(?:/[a-z0-9]+(?:[-_][a-z0-9]+)*)+")

    val requiredOrganismIds: Set<String>
        get() = NativeCurriculumManifest.typeStudies.map { it.organismId }.toSet()

    fun candidatePaths(relativeStem: String): List<String> {
        require(stemPattern.matches(relativeStem)) {
            "Invalid type-study raster stem: $relativeStem"
        }
        return formats.map { "$relativeStem.$it" }
    }

    /** Returns null on missing files, NEVER a drawing fallback or substitute. */
    fun selectAsset(relativeStem: String, availableAssetPaths: Set<String>): String? =
        candidatePaths(relativeStem).firstOrNull { it in availableAssetPaths }

    fun requiredFuturePlateStem(study: TypeStudyRequirement, plateId: String): String {
        require(study.organismId in requiredOrganismIds)
        require(plateId in study.requiredPlateIds)
        return "type_studies/${study.organismId}/$plateId"
    }

    /**
     * Acceptance gate for ALL required plates of one type study.
     * Do not mark a later organism academically accepted if even one
     * required anatomical raster is absent.
     */
    fun requireCompleteTypeStudy(
        study: TypeStudyRequirement,
        availableAssetPaths: Set<String>
    ): Map<String, String> {
        val selected = linkedMapOf<String, String>()
        for (id in study.requiredPlateIds) {
            val stem = requiredFuturePlateStem(study, id)
            selected[id] = selectAsset(stem, availableAssetPaths)
                ?: error("Missing mandatory pencil-drawn raster plate: $stem.(png|webp)")
        }
        return selected
    }
}
