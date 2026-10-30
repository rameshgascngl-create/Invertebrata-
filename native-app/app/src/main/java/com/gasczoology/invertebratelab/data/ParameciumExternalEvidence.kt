package com.gasczoology.invertebratelab.data

/**
 * N2.3B3: scientific SOURCE TRACEABILITY, not certification of Canvas artwork.
 *
 * References document the stated morphology at species/genus/comparative scope.
 * No reference on its own establishes accurate pixel placement or Tamil review.
 */
enum class ParameciumSourceScope {
    CAUDATUM, GENUS_PARAMECIUM, COMPARATIVE_OTHER_SPECIES
}

data class ParameciumFeatureEvidence(
    val featureId: String,
    val biologicalClaim: String,
    val sourceUrl: String,
    val exactLocator: String,
    val scope: ParameciumSourceScope,
    val positionReviewed: Boolean = false,
    val tamilReviewed: Boolean = false,
)

data class ProposedParameciumView(
    val referenceSpecies: String,
    val anteriorOnLeft: Boolean,
    val posteriorOnRight: Boolean,
    val oralVentralOnBottom: Boolean,
    val orientationVerifiedAgainstFigure: Boolean = false,
)

object ParameciumExternalEvidence {
    val view = ProposedParameciumView(
        referenceSpecies = ParameciumAnatomyDraft.REFERENCE_SPECIES,
        anteriorOnLeft = true,
        posteriorOnRight = true,
        oralVentralOnBottom = true,
        // The drawing orientation is explicitly DECLARED, not approved.
        orientationVerifiedAgainstFigure = false,
    )

    private const val UG_REFERENCE =
        "https://www.shcollege.ac.in/wp-content/uploads/NAAC_Documents_IV_Cycle/" +
            "Criterion-II/2.3.2/Type-UG1-AnimalDiversity-NC-Paramecium.pdf"
    private const val RESEARCH_REVIEW =
        "https://pmc.ncbi.nlm.nih.gov/articles/PMC10143506/"
    private const val CAUDATUM_MORPHOLOGY =
        "https://pmc.ncbi.nlm.nih.gov/articles/PMC8208649/"
    // Direct species-authenticated microscopic record, DOI 10.7295/W9CIL39181.
    private const val CAUDATUM_CYTOPROCT =
        "https://www.cellimagelibrary.org/images/39181"
    private const val CYTOPROCT_PRIMARY_STUDY =
        "https://pubmed.ncbi.nlm.nih.gov/4364579/"
    private const val CORTICAL_IMAGE =
        "https://www.cellimagelibrary.org/images/36755"

    val records: List<ParameciumFeatureEvidence> = listOf(
        ParameciumFeatureEvidence(
            featureId = "pellicle",
            biologicalClaim = "A pellicle maintains the P. caudatum cell outline.",
            sourceUrl = UG_REFERENCE,
            exactLocator = "Pellicle section, PDF slides 6-7",
            scope = ParameciumSourceScope.CAUDATUM,
        ),
        ParameciumFeatureEvidence(
            featureId = "somatic-cilia",
            biologicalClaim = "Numerous cilia cover P. caudatum; posterior cilia can form a caudal tuft.",
            sourceUrl = UG_REFERENCE,
            exactLocator = "Cilia section, PDF slides 9-10",
            scope = ParameciumSourceScope.CAUDATUM,
        ),
        ParameciumFeatureEvidence(
            featureId = "oral-groove",
            biologicalClaim = "An oblique oral groove lies on the ventral side in P. caudatum.",
            sourceUrl = UG_REFERENCE,
            exactLocator = "Oral groove section, PDF slide 8",
            scope = ParameciumSourceScope.CAUDATUM,
        ),
        ParameciumFeatureEvidence(
            featureId = "cytoproct",
            biologicalClaim = "In P. caudatum the cytoproct is an egestion ridge along the posterior suture on the ventral cell surface. A thin section cannot establish this Canvas marker's exact whole-cell coordinates.",
            sourceUrl = CAUDATUM_CYTOPROCT,
            exactLocator = "Cell Image Library CIL:39181, electron-microscope description; Allen and Wolf (1974), J Cell Sci 14:611-631",
            scope = ParameciumSourceScope.CAUDATUM,
        ),
        ParameciumFeatureEvidence(
            featureId = "trichocysts",
            biologicalClaim = "Trichocysts form a layer immediately beneath the P. caudatum cell surface.",
            sourceUrl = CORTICAL_IMAGE,
            exactLocator = "Cell Image Library CIL:36755; anterior transverse-section description",
            scope = ParameciumSourceScope.CAUDATUM,
        ),
    )

    val orientationReferences: List<String> = listOf(
        "$CAUDATUM_MORPHOLOGY ; Figure 1B (P. caudatum, posterior pointed)",
        "$RESEARCH_REVIEW ; Figure 1A-C (species morphology and cortical organelles)",
        "$UG_REFERENCE ; External features, PDF slide 5 (rounded anterior, tapered posterior)",
        "$CYTOPROCT_PRIMARY_STUDY ; Allen and Wolf 1974, cytoproct of P. caudatum; PMID 4364579",
    )

    fun validateAgainst(plate: NativeAnatomyPlate) {
        require(view.referenceSpecies == "Paramecium caudatum")
        require(view.anteriorOnLeft && view.posteriorOnRight && view.oralVentralOnBottom)
        require(!view.orientationVerifiedAgainstFigure) {
            "The current figure orientation has NOT been independently approved"
        }
        require(plate.organismId == "paramecium" && plate.plateId == "external-cilia")
        require(plate.status == AcademicWorkStatus.DRAFT_UNVERIFIED && plate.review == null)
        require(records.map { it.featureId } == plate.features.map { it.id })
        require(records.all {
            it.biologicalClaim.isNotBlank() && it.exactLocator.isNotBlank() &&
                it.sourceUrl.startsWith("https://") &&
                !it.positionReviewed && !it.tamilReviewed
        })
        // No comparative-only species evidence is allowed to stand for a
        // P. caudatum organ in the specimen-specific review packet.
        require(records.all { it.scope == ParameciumSourceScope.CAUDATUM })
        require(records.single { it.featureId == "cytoproct" }.sourceUrl ==
            CAUDATUM_CYTOPROCT)
        require(orientationReferences.size >= 2)
    }

    init {
        validateAgainst(ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" })
    }
}
