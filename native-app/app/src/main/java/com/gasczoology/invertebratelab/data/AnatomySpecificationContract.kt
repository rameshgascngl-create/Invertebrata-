package com.gasczoology.invertebratelab.data

/**
 * N2.1 typed specifications, not rendered Canvas assets, accepted anatomical
 * figures, physiological animation engines or working text-to-speech.
 */
data class AnatomyFeatureSpec(
    val id: String,
    val label: BilingualText,
    val geometryKey: String,
    val touchTargetKey: String,
    val narration: BilingualText? = null,
)

enum class AnatomicalRelationship { CONNECTS_TO, CONTAINS, ADJACENT_TO, PART_OF }

data class FeatureRelationship(
    val fromId: String,
    val toId: String,
    val relationship: AnatomicalRelationship,
)

data class AnatomyReviewRecord(
    val exactReference: String,
    val biologicalReviewer: String,
    val tamilReviewer: String,
    val reviewedFeatureIds: Set<String>,
    val reviewedGeometryKeys: Set<String>,
)

data class NativeAnatomyPlate(
    val organismId: String,
    val plateId: String,
    val features: List<AnatomyFeatureSpec>,
    val relationships: List<FeatureRelationship>,
    val scientificSources: List<String>,
    val status: AcademicWorkStatus = AcademicWorkStatus.DRAFT_UNVERIFIED,
    val review: AnatomyReviewRecord? = null,
)

object AnatomySpecificationContract {
    fun validate(
        plate: NativeAnatomyPlate,
        studies: List<TypeStudyRequirement> = NativeCurriculumManifest.typeStudies,
    ) {
        val study = studies.singleOrNull { it.organismId == plate.organismId }
            ?: throw IllegalArgumentException("Unknown study organism")
        require(plate.plateId in study.requiredPlateIds)
        require(plate.status != AcademicWorkStatus.NOT_AUTHORED)
        require(plate.scientificSources.isNotEmpty() && plate.scientificSources.all {
            it.isNotBlank()
        })
        require(plate.features.isNotEmpty())
        val ids = plate.features.map { it.id }
        val geometry = plate.features.map { it.geometryKey }
        val targets = plate.features.map { it.touchTargetKey }
        require(ids.size == ids.toSet().size) { "Duplicate organ IDs" }
        require(geometry.size == geometry.toSet().size) { "Duplicate geometry" }
        require(targets.size == targets.toSet().size) { "Duplicate hit region" }
        for (feature in plate.features) {
            require(feature.id.isNotBlank() && feature.geometryKey.isNotBlank() &&
                feature.touchTargetKey.isNotBlank())
            require(feature.label.english.isNotBlank() && feature.label.tamil.isNotBlank())
            feature.narration?.let {
                require(it.english.isNotBlank() && it.tamil.isNotBlank())
            }
        }
        for (edge in plate.relationships) {
            require(edge.fromId in ids && edge.toId in ids)
            require(edge.fromId != edge.toId)
        }
        if (plate.status == AcademicWorkStatus.ACCEPTED) {
            require(plate.features.none {
                it.geometryKey.startsWith("pending-") ||
                    it.touchTargetKey.startsWith("pending-")
            }) { "Unmapped geometry or touch regions cannot be academically accepted" }
            val record = requireNotNull(plate.review) { "Missing independent anatomy review" }
            require(record.exactReference.isNotBlank())
            require(record.biologicalReviewer.isNotBlank() && record.tamilReviewer.isNotBlank())
            require(record.reviewedFeatureIds == ids.toSet())
            require(record.reviewedGeometryKeys == geometry.toSet())
        }
    }
}
