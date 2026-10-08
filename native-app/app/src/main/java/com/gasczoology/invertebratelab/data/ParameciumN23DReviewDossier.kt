package com.gasczoology.invertebratelab.data

/**
 * N2.3D1: traceable source-to-feature dossier, NOT review or sign-off.
 * Source-authenticated morphology is distinct from verified Canvas positions.
 * This application never downloads reference imagery or assumes its license
 * permits republication. All references are text links for human inspection.
 */
enum class ParameciumEvidenceModality {
    UNDERGRADUATE_TEACHING_DOCUMENT,
    WHOLE_CELL_LIGHT_MICROSCOPY,
    TRANSMISSION_ELECTRON_MICROSCOPY
}

data class ParameciumReviewSource(
    val referenceUrl: String,
    val exactLocator: String,
    val imagingModality: ParameciumEvidenceModality,
    val establishes: BilingualText,
    val doesNotEstablish: BilingualText,
) {
    init {
        require(referenceUrl.startsWith("https://"))
        require(exactLocator.isNotBlank())
        require(establishes.english.isNotBlank() && establishes.tamil.isNotBlank())
        require(doesNotEstablish.english.isNotBlank() && doesNotEstablish.tamil.isNotBlank())
    }
}

data class ParameciumReviewTask(
    val featureId: String,
    val primary: ParameciumReviewSource,
    val supplementary: List<ParameciumReviewSource> = emptyList(),
) {
    val geometryReviewed: Boolean = false
    val tamilReviewed: Boolean = false
}

/**
 * A *review dossier* can be complete while the anatomy is still unapproved.
 * The current state reports no human signatures or physical-device evidence.
 */
object ParameciumN23DReviewDossier {
    private val refRecords = ParameciumExternalEvidence.records.associateBy { it.featureId }
    private fun teaching(id: String, establishedEnglish: String, establishedTamil: String,
        missingEnglish: String, missingTamil: String): ParameciumReviewSource {
        val record = requireNotNull(refRecords[id])
        return ParameciumReviewSource(
            referenceUrl = record.sourceUrl,
            exactLocator = record.exactLocator,
            imagingModality = ParameciumEvidenceModality.UNDERGRADUATE_TEACHING_DOCUMENT,
            establishes = BilingualText(establishedEnglish, establishedTamil),
            doesNotEstablish = BilingualText(missingEnglish, missingTamil)
        )
    }

    val tasks: List<ParameciumReviewTask> = listOf(
        ParameciumReviewTask("pellicle",
            teaching("pellicle",
                "The cell boundary is described as a pellicle.",
                "செல்லின் வெளிப்புற எல்லை பெல்லிக்கிள் என விவரிக்கப்படுகிறது.",
                "Exact contour measurements and specimen registration.",
                "துல்லியமான வெளிவடிவ அளவீடும் மாதிரி உயிரியின் ஒப்பிடப்பட்ட இடமமைவும்.")
        ),
        ParameciumReviewTask("somatic-cilia",
            teaching("somatic-cilia",
                "Numerous surface cilia and a posterior caudal tuft are described.",
                "பல மேற்பரப்புக் குறுஇழைகளும் பின்புற வால் போன்ற குறுஇழைகளும் விவரிக்கப்படுகின்றன.",
                "The illustrated count, spacing and individual stroke placement.",
                "வரையப்பட்ட குறுஇழைகளின் எண்ணிக்கை, இடைவெளி, ஒவ்வொரு கோட்டின் துல்லியமான இடம்.")
        ),
        ParameciumReviewTask(
            "oral-groove",
            teaching("oral-groove",
                "A ventral oral depression is described.",
                "வாய்ப்புறத்தில் வாய்ப்பள்ளம் அமைவது விவரிக்கப்படுகிறது.",
                "The precise U-shaped arc and approved whole-cell coordinates.",
                "U-வடிவ வளைவும் முழுச் செல் வரைபடத்தின் துல்லியமான ஆயத்தொலைவுகளும்."),
            supplementary = listOf(
                ParameciumReviewSource(
                    "https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html",
                    "Fig. 0: P. caudatum, phase-contrast whole cell",
                    ParameciumEvidenceModality.WHOLE_CELL_LIGHT_MICROSCOPY,
                    BilingualText(
                        "Whole-cell image identifies the oral region.",
                        "முழுச் செல் படத்தில் வாய்ப்பகுதி அடையாளம் காணப்படுகிறது."),
                    BilingualText(
                        "No registered Canvas x/y location or curve.",
                        "Canvas ஆயத்தொலைவு அல்லது வளைவு துல்லியமாக உறுதி செய்யப்படவில்லை.")
                ),
                ParameciumReviewSource(
                    "https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html",
                    "Fig. 22: P. caudatum, anterior oral transverse TEM section",
                    ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
                    BilingualText(
                        "The vestibule and buccal ciliary structures are shown.",
                        "வெஸ்டிப்யூலும் வாய்க்குழிப் பகுதியின் குறுஇழை அமைப்புகளும் காட்டப்படுகின்றன."),
                    BilingualText(
                        "Cross section does not establish a longitudinal groove contour.",
                        "குறுக்குவெட்டுப் படம் நீளவாட்ட வாய்ப்பள்ள வளைவை நிரூபிக்காது.")
                ),
            )
        ),
        ParameciumReviewTask("cytoproct", ParameciumReviewSource(
            "https://www.cellimagelibrary.org/images/39181",
            "CIL:39181, P. caudatum, closed cytoproct TEM",
            ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
            BilingualText(
                "The closed cytoproct forms a ridge on the posterior ventral suture.",
                "மூடிய செல் கழிவுவெளியேற்றப் பகுதி பின்புற வாய்ப்புறத் தையல் கோட்டில் மேடுபோல் அமைகிறது."),
            BilingualText(
                "The thin section cannot fix the ridge at a Canvas coordinate.",
                "இந்த மெல்லிய வெட்டுப்படம் Canvas வரைபடத்தில் அதன் துல்லியமான இடத்தைத் தராது.")
        )),
        ParameciumReviewTask("trichocysts", ParameciumReviewSource(
            "https://www.cellimagelibrary.org/images/36755",
            "CIL:36755, P. caudatum, near-anterior cortical TEM section",
            ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
            BilingualText(
                "A layer of trichocysts occurs beneath the cell surface.",
                "செல் மேற்பரப்பின் அடியில் ட்ரைக்கோசிஸ்டுகளின் அடுக்கு காணப்படுகிறது."),
            BilingualText(
                "The exact number or arc spacing of prototype rods.",
                "முன்மாதிரி கோல்களின் துல்லியமான எண்ணிக்கையோ வளைவு இடைவெளியோ உறுதியாகவில்லை.")
        ))
    )

    val outstandingFeatureReviews: Int get() = tasks.count { !it.geometryReviewed }
    val outstandingTamilReviews: Int get() = tasks.count { !it.tamilReviewed }
    val pendingPhysicalDeviceReview: Boolean = true

    fun forFeature(featureId: String): ParameciumReviewTask =
        tasks.single { it.featureId == featureId }

    fun verifyDraftAndSourceIntegrity() {
        val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
        require(tasks.map { it.featureId } == plate.features.map { it.id })
        require(tasks.size == 5 && tasks.map { it.featureId }.toSet().size == 5)
        require(plate.status == AcademicWorkStatus.DRAFT_UNVERIFIED && plate.review == null)
        require(tasks.all { !it.geometryReviewed && !it.tamilReviewed })
        require(tasks.all { task ->
            val record = requireNotNull(refRecords[task.featureId])
            task.primary.referenceUrl == record.sourceUrl &&
                record.scope == ParameciumSourceScope.CAUDATUM &&
                !record.positionReviewed && !record.tamilReviewed
        })
        require(tasks.single { it.featureId == "oral-groove" }.supplementary.map {
            it.imagingModality
        } == listOf(
            ParameciumEvidenceModality.WHOLE_CELL_LIGHT_MICROSCOPY,
            ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY
        ))
        require(!ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
        require(outstandingFeatureReviews == 5 && outstandingTamilReviews == 5)
        require(pendingPhysicalDeviceReview)
    }
}
