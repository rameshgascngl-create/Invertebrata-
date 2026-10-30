package com.gasczoology.invertebratelab.data

/**
 * N2.3E1: offline, read-only HANDOFF instructions for independent reviewers.
 *
 * This does not acquire a reviewer signature, approve specimen orientation,
 * infer Canvas locations from TEM sections, or change anatomy acceptance.
 * Reviewers must inspect the real reference and candidate independently.
 */
data class ParameciumReviewerHandoffLine(
    val featureId: String,
    val label: BilingualText,
    val reviewQuestion: BilingualText,
    val evidenceBoundary: BilingualText,
    val proposedHotspot: PrototypeHotspot,
    val sourceLocator: String,
    val sourceUrl: String,
    val sourceModality: ParameciumEvidenceModality,
    val biologicalVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
    val tamilVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
) {
    init {
        require(featureId.isNotBlank() && proposedHotspot.featureId == featureId)
        require(sourceUrl.startsWith("https://") && sourceLocator.isNotBlank())
        require(reviewQuestion.english.isNotBlank() && reviewQuestion.tamil.isNotBlank())
        require(evidenceBoundary.english.isNotBlank() && evidenceBoundary.tamil.isNotBlank())
    }
}

object ParameciumN23EReviewerHandoff {
    private val plate = ParameciumAnatomyDraft.plates.single {
        it.plateId == "external-cilia"
    }
    private val tasks = ParameciumN23DReviewDossier.tasks.associateBy { it.featureId }
    private val hotspots = ParameciumExternalGeometry.hotspots.associateBy { it.featureId }

    private fun question(id: String) = when (id) {
        "pellicle" -> BilingualText(
            "Compare the entire drawn boundary and rounded-anterior / tapered-posterior orientation with an authenticated whole-cell specimen. Record deviations.",
            "உறுதிப்படுத்தப்பட்ட முழுச் செல் மாதிரிப் படத்துடன் வரையப்பட்ட வெளிப்புற எல்லையையும் வட்டமான முன்புறம் / கூரான பின்புறம் அமைவையும் ஒப்பிட்டு, வேறுபாடுகளைப் பதிவு செய்யவும்.")
        "somatic-cilia" -> BilingualText(
            "Check surface ciliary rows and caudal tuft against species evidence; do not treat the 90 illustrative strokes as a measured ciliary count.",
            "இனத்திற்குரிய ஆதாரத்துடன் மேற்பரப்புக் குறுஇழை வரிசைகளையும் பின்புறக் குறுஇழைத் தொகுப்பையும் ஒப்பிடவும்; வரையப்பட்ட 90 கோடுகளை அளவிடப்பட்ட எண்ணிக்கையாகக் கருத வேண்டாம்.")
        "oral-groove" -> BilingualText(
            "Verify ventral identity, orientation and oral-groove curve against an authenticated whole-cell view; record whether the arc must be redrawn.",
            "உறுதிப்படுத்தப்பட்ட முழுச் செல் படத்துடன் வாய்ப்புற அமைவு, திசை மற்றும் வாய்ப்பள்ள வளைவை ஒப்பிட்டு, வளைவு திருத்தப்பட வேண்டுமா எனப் பதிவு செய்யவும்.")
        "cytoproct" -> BilingualText(
            "Confirm a closed posterior-ventral cytoproct ridge from the TEM record, then independently review its candidate whole-cell marker location.",
            "TEM ஆதாரத்தில் மூடிய பின்புற-வாய்ப்புறக் கழிவுவெளியேற்ற மேட்டைச் சரிபார்த்த பின், முழுச் செல் வரைபடத்தில் அதன் முன்மொழியப்பட்ட இடத்தைத் தனியாக ஆய்வு செய்யவும்.")
        "trichocysts" -> BilingualText(
            "Confirm subpellicular cortical placement; review the illustrative rod distribution without inferring density or coordinates from one TEM section.",
            "பெல்லிக்கிளின் கீழுள்ள புறப்படல அமைவைக் கண்டறிந்து, ஒரே TEM வெட்டிலிருந்து அடர்த்தியையோ ஆயத்தொலைவுகளையோ ஊகிக்காமல், வரையப்பட்ட குச்சி அமைப்பை ஆய்வு செய்யவும்.")
        else -> error("Unknown external feature: $id")
    }

    private fun limitation(id: String) = when (id) {
        "pellicle" -> BilingualText(
            "Teaching material alone does not register a specimen contour to Canvas pixels.",
            "பாடக் குறிப்புகள் மட்டும் மாதிரிச் செல்லின் எல்லையை Canvas பிக்சல்களுடன் துல்லியமாக இணைக்காது.")
        "somatic-cilia" -> BilingualText(
            "Individual cilium stroke placement and total count are illustrative, not observations.",
            "ஒவ்வொரு குறுஇழைக் கோட்டின் இடமும் மொத்த எண்ணிக்கையும் விளக்கத்துக்கானவை; நேரடி அளவீடுகள் அல்ல.")
        "oral-groove" -> BilingualText(
            "A transverse TEM section cannot establish the longitudinal whole-cell groove arc.",
            "TEM குறுக்குவெட்டால் முழுச் செல்லின் நீளவாட்ட வாய்ப்பள்ள வளைவை நிரூபிக்க முடியாது.")
        "cytoproct" -> BilingualText(
            "The CIL:39181 thin section establishes ridge morphology, not this Canvas marker's x/y coordinates.",
            "CIL:39181 மெல்லிய வெட்டு மேட்டின் அமைப்பை மட்டுமே உறுதிசெய்கிறது; இங்குள்ள Canvas x/y இடத்தை அல்ல.")
        "trichocysts" -> BilingualText(
            "CIL:36755 is a near-anterior cross-section, not a whole-cell map of all 44 schematic rods.",
            "CIL:36755 முன்புறத்துக்கு அருகிலுள்ள குறுக்குவெட்டு; வரைபடத்தின் 44 குச்சிகளுக்கான முழுச் செல் இடவியல் வரைபடம் அல்ல.")
        else -> error("Unknown external feature: $id")
    }

    val entries: List<ParameciumReviewerHandoffLine> = plate.features.map { feature ->
        val source = requireNotNull(tasks[feature.id]).primary
        ParameciumReviewerHandoffLine(
            featureId = feature.id,
            label = feature.label,
            reviewQuestion = question(feature.id),
            evidenceBoundary = limitation(feature.id),
            proposedHotspot = requireNotNull(hotspots[feature.id]),
            sourceLocator = source.exactLocator,
            sourceUrl = source.referenceUrl,
            sourceModality = source.imagingModality,
        )
    }

    val biologicalApprovals: Int get() = entries.count {
        it.biologicalVerdict == ExpertReviewVerdict.SIGNED_OFF
    }
    val tamilApprovals: Int get() = entries.count {
        it.tamilVerdict == ExpertReviewVerdict.SIGNED_OFF
    }

    fun forFeature(id: String): ParameciumReviewerHandoffLine =
        entries.single { it.featureId == id }

    fun verifyUnapprovedHandoff() {
        require(entries.map { it.featureId } == ParameciumExternalReviewGate.requiredIds)
        require(plate.status == AcademicWorkStatus.DRAFT_UNVERIFIED && plate.review == null)
        require(plate.features.all { it.geometryKey.startsWith("pending-") })
        require(entries.all {
            it.biologicalVerdict == ExpertReviewVerdict.PENDING &&
                it.tamilVerdict == ExpertReviewVerdict.PENDING
        })
        require(biologicalApprovals == 0 && tamilApprovals == 0)
        require(entries.all { it.sourceModality == tasks[it.featureId]?.primary?.imagingModality })
        require(forFeature("cytoproct").sourceModality ==
            ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY)
        require(forFeature("trichocysts").sourceModality ==
            ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY)
        require(!ParameciumExternalEvidence.view.orientationVerifiedAgainstFigure)
        require(!ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
        require(ParameciumN23DReviewDossier.pendingPhysicalDeviceReview)
    }
}
