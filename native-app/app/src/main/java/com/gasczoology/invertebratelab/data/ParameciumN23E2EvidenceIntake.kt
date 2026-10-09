package com.gasczoology.invertebratelab.data

/**
 * N2.3E2: frozen source-access observations and human-evidence intake checks.
 * Reviewers must recheck live access/rights; these records do not download images.
 * A structurally ready submission never mutates the academic plate's status.
 */
enum class E2ReferenceType {
    WHOLE_CELL_LIGHT_MICROGRAPH,
    ORAL_REGION_TRANSVERSE_TEM,
    CYTOPROCT_TEM,
    CORTICAL_TRICHOCYST_TEM,
    PEDAGOGICAL_PDF
}

enum class E2ImageRights {
    COPYRIGHT_PERMISSION_REQUIRED,
    PUBLIC_DOMAIN_REPORTED_ON_RECORD,
    NOT_INDEPENDENTLY_CONFIRMED,
    TEACHING_DOCUMENT_LINK_ONLY
}

enum class E2SourceAccess {
    DIRECT_TEXT_DESCRIPTION_REVIEWED,
    SEARCH_INDEX_METADATA_ONLY
}

data class E2ReferenceRecord(
    val id: String,
    val sourceUrl: String,
    val locator: String,
    val sourceSpecies: String,
    val type: E2ReferenceType,
    val rights: E2ImageRights,
    val access: E2SourceAccess,
    val directCoordinateRegistration: Boolean = false,
) {
    init {
        require(id.isNotBlank() && locator.isNotBlank() && sourceUrl.startsWith("https://"))
        require(sourceSpecies == ParameciumAnatomyDraft.REFERENCE_SPECIES)
        require(!directCoordinateRegistration) {
            "Publication evidence must not silently grant Canvas-coordinate registration"
        }
    }
}

data class E2FeatureEvidenceSubmission(
    val featureId: String,
    val wholeCellReferenceId: String = "",
    val wholeCellFigureLocator: String = "",
    val orientationFinding: String = "",
    val geometryComparisonEvidenceId: String = "",
    val geometryFinding: String = "",
    val biologicalReviewer: String = "",
    val biologicalReviewDate: String = "",
    val biologicalVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
    val tamilTerminologyEvidenceId: String = "",
    val tamilFinding: String = "",
    val tamilReviewer: String = "",
    val tamilReviewDate: String = "",
    val tamilVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
)

/**
 * Fail-closed E2 handoff: review evidence has to be actually supplied by humans.
 * Do not accept a TEM-only source as the whole-cell geometry reference.
 * Signatures/names are claims and require out-of-band human verification.
 */
object ParameciumN23E2EvidenceIntake {
    const val AUDIT_DATE = "2026-10-09"

    val references: List<E2ReferenceRecord> = listOf(
        E2ReferenceRecord(
            "allen-whole-cell-fig0",
            "https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html",
            "Richard Allen atlas Fig. 0; D.J. Patterson and Mark Farmer image",
            "Paramecium caudatum",
            E2ReferenceType.WHOLE_CELL_LIGHT_MICROGRAPH,
            E2ImageRights.COPYRIGHT_PERMISSION_REQUIRED,
            E2SourceAccess.DIRECT_TEXT_DESCRIPTION_REVIEWED,
        ),
        E2ReferenceRecord(
            "allen-oral-tem-fig22",
            "https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html",
            "Richard Allen atlas Fig. 22; oral apparatus, anterior transverse section",
            "Paramecium caudatum",
            E2ReferenceType.ORAL_REGION_TRANSVERSE_TEM,
            E2ImageRights.NOT_INDEPENDENTLY_CONFIRMED,
            E2SourceAccess.DIRECT_TEXT_DESCRIPTION_REVIEWED,
        ),
        E2ReferenceRecord(
            "cil-39181-cytoproct",
            "https://www.cellimagelibrary.org/images/39181",
            "CIL:39181; closed cytoproct ridge; indexed description only",
            "Paramecium caudatum",
            E2ReferenceType.CYTOPROCT_TEM,
            E2ImageRights.NOT_INDEPENDENTLY_CONFIRMED,
            E2SourceAccess.SEARCH_INDEX_METADATA_ONLY,
        ),
        E2ReferenceRecord(
            "cil-36755-trichocysts",
            "https://www.cellimagelibrary.org/images/36755",
            "CIL:36755; anterior transverse section and cortical trichocysts",
            "Paramecium caudatum",
            E2ReferenceType.CORTICAL_TRICHOCYST_TEM,
            E2ImageRights.PUBLIC_DOMAIN_REPORTED_ON_RECORD,
            E2SourceAccess.SEARCH_INDEX_METADATA_ONLY,
        ),
        E2ReferenceRecord(
            "sacred-heart-type-study",
            "https://www.shcollege.ac.in/wp-content/uploads/NAAC_Documents_IV_Cycle/" +
                "Criterion-II/2.3.2/Type-UG1-AnimalDiversity-NC-Paramecium.pdf",
            "58-page PDF; external shape p.5, pellicle pp.6-7, oral groove p.8, cilia pp.9-10",
            "Paramecium caudatum",
            E2ReferenceType.PEDAGOGICAL_PDF,
            E2ImageRights.TEACHING_DOCUMENT_LINK_ONLY,
            E2SourceAccess.DIRECT_TEXT_DESCRIPTION_REVIEWED,
        )
    )

    private val byId = references.associateBy { it.id }

    // The biological Canvas features and this handoff must always stay aligned.
    val emptySubmissions: List<E2FeatureEvidenceSubmission> =
        ParameciumExternalReviewGate.requiredIds.map {
            E2FeatureEvidenceSubmission(featureId = it)
        }

    fun reference(id: String): E2ReferenceRecord = requireNotNull(byId[id]) {
        "No verified E2 source record exists for $id"
    }

    fun issues(entries: List<E2FeatureEvidenceSubmission>): List<String> {
        val out = mutableListOf<String>()
        if (entries.map { it.featureId } != ParameciumExternalReviewGate.requiredIds) {
            out += "All five feature IDs must appear once in authoritative order"
        }
        for (id in ParameciumExternalReviewGate.requiredIds) {
            val matches = entries.filter { it.featureId == id }
            if (matches.size != 1) {
                out += "$id: missing/duplicate human evidence record"
                continue
            }
            val e = matches.single()
            val source = byId[e.wholeCellReferenceId]
            if (source == null || source.type != E2ReferenceType.WHOLE_CELL_LIGHT_MICROGRAPH) {
                out += "$id: authentic whole-cell reference required; TEM/PDF is not geometry registration"
            }
            if (e.wholeCellFigureLocator.isBlank() || e.orientationFinding.isBlank())
                out += "$id: exact specimen figure and orientation finding required"
            if (e.geometryComparisonEvidenceId.isBlank() || e.geometryFinding.isBlank())
                out += "$id: independently inspected geometry overlay and finding required"
            if (e.biologicalReviewer.isBlank() || !validDate(e.biologicalReviewDate) ||
                e.biologicalVerdict != ExpertReviewVerdict.SIGNED_OFF)
                out += "$id: independently documented biological reviewer, date and signoff required"
            if (e.tamilTerminologyEvidenceId.isBlank() || e.tamilFinding.isBlank() ||
                e.tamilReviewer.isBlank() || !validDate(e.tamilReviewDate) ||
                e.tamilVerdict != ExpertReviewVerdict.SIGNED_OFF)
                out += "$id: separate documented Tamil reviewer, evidence, date and signoff required"
        }
        return out
    }

    private val isoDate = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2}")
    private fun validDate(value: String) = isoDate.matches(value)

    /** READY FOR SEPARATE HUMAN DECISION, never automatic anatomy approval. */
    fun readyForIndependentDecision(entries: List<E2FeatureEvidenceSubmission>): Boolean =
        issues(entries).isEmpty()

    init {
        require(references.map { it.id }.toSet().size == references.size)
        require(references.single { it.id == "allen-whole-cell-fig0" }.rights ==
            E2ImageRights.COPYRIGHT_PERMISSION_REQUIRED)
        require(references.single { it.id == "cil-39181-cytoproct" }.access ==
            E2SourceAccess.SEARCH_INDEX_METADATA_ONLY)
        require(references.none { it.directCoordinateRegistration })
        require(!readyForIndependentDecision(emptySubmissions))
        require(ParameciumN23EReviewerHandoff.biologicalApprovals == 0)
        require(ParameciumN23EReviewerHandoff.tamilApprovals == 0)
        require(ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
            .status == AcademicWorkStatus.DRAFT_UNVERIFIED)
    }
}
