package com.gasczoology.invertebratelab.data

/**
 * N2.3B4: explicit academic REVIEW READINESS only.
 *
 * A structurally complete packet is NOT an academically accepted drawing.
 * A human-appointed zoology reviewer and Tamil reviewer must inspect the
 * physical geometry and evidence, then make a separate approval decision.
 */
enum class ExpertReviewVerdict { PENDING, CORRECTION_REQUIRED, SIGNED_OFF }

data class ParameciumFeatureReviewLine(
    val featureId: String,
    val sourceFigureLocator: String = "",
    val sourceSpecies: String = "",
    val geometryEvidenceId: String = "",
    val biologicalReviewer: String = "",
    val tamilReviewer: String = "",
    val biologicalVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
    val tamilVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
)

data class PhysicalDeviceReviewEvidence(
    val deviceModel: String = "",
    val androidVersion: String = "",
    val portraitEvidenceId: String = "",
    val landscapeEvidenceId: String = "",
    val tamil200EvidenceId: String = "",
    val talkBackEvidenceId: String = "",
    val organHitTargetEvidenceId: String = "",
)

data class ParameciumExternalReviewPacket(
    val specimenReferenceFigure: String = "",
    val specimenOrientationReviewedBy: String = "",
    val specimenOrientationVerdict: ExpertReviewVerdict = ExpertReviewVerdict.PENDING,
    val featureReviews: List<ParameciumFeatureReviewLine> = ParameciumExternalReviewGate.emptyLines,
    val physicalDevice: PhysicalDeviceReviewEvidence = PhysicalDeviceReviewEvidence(),
)

/** No transition of NativeAnatomyPlate.status occurs in this code. */
object ParameciumExternalReviewGate {
    private val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
    val requiredIds: List<String> = plate.features.map { it.id }

    val emptyLines: List<ParameciumFeatureReviewLine> =
        requiredIds.map { ParameciumFeatureReviewLine(featureId = it) }

    fun unresolved(packet: ParameciumExternalReviewPacket): List<String> {
        val issues = mutableListOf<String>()
        if (packet.specimenReferenceFigure.isBlank()) issues += "orientation: no exact species-authenticated figure"
        if (packet.specimenOrientationReviewedBy.isBlank()) issues += "orientation: missing human biological reviewer"
        if (packet.specimenOrientationVerdict != ExpertReviewVerdict.SIGNED_OFF)
            issues += "orientation: reviewer has not signed off"

        val ids = packet.featureReviews.map { it.featureId }
        if (ids != requiredIds) issues += "feature list must match all five authoritative IDs in order"
        val references = ParameciumExternalEvidence.records.associateBy { it.featureId }
        for (featureId in requiredIds) {
            val lines = packet.featureReviews.filter { it.featureId == featureId }
            if (lines.size != 1) {
                issues += "$featureId: exactly one review line required"
                continue
            }
            val line = lines.single()
            val ref = references[featureId]
            if (ref?.scope != ParameciumSourceScope.CAUDATUM)
                issues += "$featureId: missing direct P. caudatum source scope"
            if (line.sourceSpecies != "Paramecium caudatum")
                issues += "$featureId: species authentication not documented"
            if (line.sourceFigureLocator.isBlank())
                issues += "$featureId: no exact microscopy figure/slide locator"
            if (line.geometryEvidenceId.isBlank())
                issues += "$featureId: no Canvas-versus-figure overlay/endpoint evidence"
            if (line.biologicalReviewer.isBlank() || line.biologicalVerdict != ExpertReviewVerdict.SIGNED_OFF)
                issues += "$featureId: biological geometry not signed off"
            if (line.tamilReviewer.isBlank() || line.tamilVerdict != ExpertReviewVerdict.SIGNED_OFF)
                issues += "$featureId: Tamil label not signed off"
        }
        val d = packet.physicalDevice
        if (d.deviceModel.isBlank() || d.androidVersion.isBlank())
            issues += "device: model and OS version missing"
        if (d.portraitEvidenceId.isBlank()) issues += "device: portrait evidence missing"
        if (d.landscapeEvidenceId.isBlank()) issues += "device: landscape evidence missing"
        if (d.tamil200EvidenceId.isBlank()) issues += "device: actual 200% Tamil evidence missing"
        if (d.talkBackEvidenceId.isBlank()) issues += "device: TalkBack evidence missing"
        if (d.organHitTargetEvidenceId.isBlank())
            issues += "device: touch-region evidence missing"
        return issues
    }

    /**
     * A zero-issue packet means READY FOR INDEPENDENT APPROVAL CONSIDERATION,
     * not ACCEPTED. It must never automatically mutate anatomy status.
     */
    fun readyForHumanDecision(packet: ParameciumExternalReviewPacket) =
        unresolved(packet).isEmpty()

    init {
        require(plate.status == AcademicWorkStatus.DRAFT_UNVERIFIED && plate.review == null)
        require(requiredIds == ParameciumExternalEvidence.records.map { it.featureId })
        require(!readyForHumanDecision(ParameciumExternalReviewPacket()))
    }
}
