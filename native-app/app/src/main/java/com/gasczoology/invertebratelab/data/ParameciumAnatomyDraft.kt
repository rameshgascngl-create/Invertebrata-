package com.gasczoology.invertebratelab.data

/**
 * N2.3A species-scoped anatomy REQUIREMENTS, not Canvas coordinates or finished art.
 * Paramecium caudatum is the reference organism. Morphology and Tamil labels
 * still require source-plate and subject-specialist review.
 *
 * Every geometry/hit region is deliberately marked pending. Neither SVG nor
 * HTML nor automatic image tracing is used.
 */
object ParameciumAnatomyDraft {
    const val REFERENCE_SPECIES = "Paramecium caudatum"

    private const val SPECIES_REVIEW =
        "https://pmc.ncbi.nlm.nih.gov/articles/PMC10143506/"
    private const val ORAL_ULTRASTRUCTURE =
        "https://pmc.ncbi.nlm.nih.gov/articles/PMC2109358/"
    private const val TEACHING_ATLAS =
        "https://omeka.uottawa.ca/biodidac/items/show/4173"
    private const val OSMOREGULATION =
        "https://pubmed.ncbi.nlm.nih.gov/38688044/"

    private fun organ(id: String, english: String, tamil: String): AnatomyFeatureSpec =
        AnatomyFeatureSpec(
            id = id,
            label = BilingualText(english, tamil),
            geometryKey = "pending-geometry-" + id,
            touchTargetKey = "pending-touch-" + id,
        )

    val plates: List<NativeAnatomyPlate> = listOf(
        NativeAnatomyPlate(
            organismId = "paramecium",
            plateId = "external-cilia",
            features = listOf(
                organ("pellicle", "Pellicle", "பெல்லிக்கிள்"),
                organ("somatic-cilia", "Somatic cilia", "உடற்பரப்புக் குறுஇழைகள்"),
                organ("oral-groove", "Oral groove", "வாய்ப்பள்ளம்"),
                organ("cytoproct", "Cytoproct (cell anus)", "செல் கழிவுத்துளை"),
                organ("trichocysts", "Trichocysts", "ட்ரைக்கோசிஸ்டுகள்"),
            ),
            relationships = listOf(
                FeatureRelationship("somatic-cilia", "pellicle", AnatomicalRelationship.PART_OF),
                FeatureRelationship("oral-groove", "pellicle", AnatomicalRelationship.PART_OF),
                FeatureRelationship("cytoproct", "pellicle", AnatomicalRelationship.PART_OF),
                FeatureRelationship("trichocysts", "pellicle", AnatomicalRelationship.ADJACENT_TO),
            ),
            scientificSources = listOf(SPECIES_REVIEW, TEACHING_ATLAS),
        ),
        NativeAnatomyPlate(
            organismId = "paramecium",
            plateId = "oral-apparatus",
            features = listOf(
                organ("oral-groove", "Oral groove (peristome)", "வாய்ப்பள்ளம் (பெரிஸ்டோம்)"),
                organ("vestibule", "Vestibule", "வெஸ்டிப்யூல்"),
                organ("cytostome", "Cytostome", "சைட்டோஸ்டோம்"),
                organ("cytopharynx", "Cytopharynx", "சைட்டோஃபாரிங்ஸ்"),
                organ("forming-food-vacuole", "Forming food vacuole", "உருவாகும் உணவுக் குமிழ்"),
            ),
            relationships = listOf(
                FeatureRelationship("oral-groove", "vestibule", AnatomicalRelationship.CONNECTS_TO),
                FeatureRelationship("vestibule", "cytostome", AnatomicalRelationship.CONNECTS_TO),
                FeatureRelationship("cytostome", "cytopharynx", AnatomicalRelationship.CONNECTS_TO),
                FeatureRelationship("cytopharynx", "forming-food-vacuole", AnatomicalRelationship.ADJACENT_TO),
            ),
            scientificSources = listOf(SPECIES_REVIEW, ORAL_ULTRASTRUCTURE, TEACHING_ATLAS),
        ),
        NativeAnatomyPlate(
            organismId = "paramecium",
            plateId = "contractile-vacuoles",
            features = listOf(
                organ("anterior-contractile-vacuole", "Anterior contractile vacuole", "முன்புறச் சுருங்கும் நுண்குமிழ்"),
                organ("anterior-collecting-canals", "Anterior collecting canals", "முன்புறச் சேகரிப்புக் கால்வாய்கள்"),
                organ("posterior-contractile-vacuole", "Posterior contractile vacuole", "பின்புறச் சுருங்கும் நுண்குமிழ்"),
                organ("posterior-collecting-canals", "Posterior collecting canals", "பின்புறச் சேகரிப்புக் கால்வாய்கள்"),
            ),
            relationships = listOf(
                FeatureRelationship("anterior-collecting-canals", "anterior-contractile-vacuole", AnatomicalRelationship.CONNECTS_TO),
                FeatureRelationship("posterior-collecting-canals", "posterior-contractile-vacuole", AnatomicalRelationship.CONNECTS_TO),
            ),
            scientificSources = listOf(SPECIES_REVIEW, OSMOREGULATION, TEACHING_ATLAS),
        ),
        NativeAnatomyPlate(
            organismId = "paramecium",
            plateId = "locomotion",
            features = listOf(
                organ("somatic-cilia", "Somatic cilia", "உடற்பரப்புக் குறுஇழைகள்"),
                organ("ciliary-rows", "Longitudinal ciliary rows", "நீள்வரிசைக் குறுஇழைகள்"),
                organ("basal-bodies", "Ciliary basal bodies", "குறுஇழை அடித்தளத் துகள்கள்"),
                organ("pellicle", "Pellicle", "பெல்லிக்கிள்"),
            ),
            relationships = listOf(
                FeatureRelationship("somatic-cilia", "ciliary-rows", AnatomicalRelationship.PART_OF),
                FeatureRelationship("basal-bodies", "pellicle", AnatomicalRelationship.ADJACENT_TO),
                FeatureRelationship("somatic-cilia", "basal-bodies", AnatomicalRelationship.CONNECTS_TO),
            ),
            scientificSources = listOf(SPECIES_REVIEW, TEACHING_ATLAS),
        ),
    ).also { all ->
        require(all.size == 4)
        all.forEach { AnatomySpecificationContract.validate(it) }
        require(all.all { it.status == AcademicWorkStatus.DRAFT_UNVERIFIED })
    }
}
