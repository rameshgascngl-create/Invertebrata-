package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumExternalReviewGateTest {
    @Test
    fun noHumanReviewCanNeverBeInferredFromCitationsAndGreenCI() {
        val pending = ParameciumExternalReviewPacket()
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(pending))
        assertTrue(ParameciumExternalReviewGate.unresolved(pending).size >= 20)
        assertEquals(
            listOf("pellicle", "somatic-cilia", "oral-groove", "cytoproct", "trichocysts"),
            ParameciumExternalReviewGate.requiredIds,
        )
        val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertTrue(plate.features.all { it.geometryKey.startsWith("pending-") })
    }

    private fun completedPacket(): ParameciumExternalReviewPacket =
        ParameciumExternalReviewPacket(
            specimenReferenceFigure = "fixture-only-species-authenticated-figure",
            specimenOrientationReviewedBy = "fixture-only-biological-reviewer",
            specimenOrientationVerdict = ExpertReviewVerdict.SIGNED_OFF,
            featureReviews = ParameciumExternalReviewGate.requiredIds.map { id ->
                ParameciumFeatureReviewLine(
                    featureId = id,
                    sourceFigureLocator = "fixture-only-figure-for-$id",
                    sourceSpecies = "Paramecium caudatum",
                    geometryEvidenceId = "fixture-only-morphology-comparison-for-$id",
                    biologicalReviewer = "fixture-only-subject-expert",
                    tamilReviewer = "fixture-only-language-reviewer",
                    biologicalVerdict = ExpertReviewVerdict.SIGNED_OFF,
                    tamilVerdict = ExpertReviewVerdict.SIGNED_OFF,
                )
            },
            physicalDevice = PhysicalDeviceReviewEvidence(
                deviceModel = "fixture-device",
                androidVersion = "fixture-api",
                portraitEvidenceId = "fixture-portrait",
                landscapeEvidenceId = "fixture-landscape",
                tamil200EvidenceId = "fixture-tamil200",
                talkBackEvidenceId = "fixture-talkback",
                organHitTargetEvidenceId = "fixture-touch",
            ),
        )

    @Test
    fun completeFixtureOnlyMeansReadyForSeparateHumanDecisionNotAccepted() {
        val fixture = completedPacket()
        assertTrue(ParameciumExternalReviewGate.unresolved(fixture).isEmpty())
        assertTrue(ParameciumExternalReviewGate.readyForHumanDecision(fixture))
        val anatomy = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
        // Readiness must never mutate the authoritative anatomy state or fill approval.
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, anatomy.status)
        assertEquals(null, anatomy.review)
    }

    @Test
    fun crossSpeciesOrMissingTamilOrAbsentDeviceEvidenceBlocksReview() {
        val fixture = completedPacket()
        val altered = fixture.copy(
            featureReviews = fixture.featureReviews.map {
                if (it.featureId == "cytoproct")
                    it.copy(sourceSpecies = "Paramecium tetraurelia",
                        tamilVerdict = ExpertReviewVerdict.PENDING)
                else it
            },
            physicalDevice = fixture.physicalDevice.copy(tamil200EvidenceId = ""),
        )
        val issues = ParameciumExternalReviewGate.unresolved(altered)
        assertTrue(issues.any { it.contains("cytoproct: species") })
        assertTrue(issues.any { it.contains("cytoproct: Tamil") })
        assertTrue(issues.any { it.contains("200% Tamil") })
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(altered))
    }

    @Test
    fun duplicateOrMissingFeatureReviewBlocksEvenIfOthersClaimSignoff() {
        val fixture = completedPacket()
        val altered = fixture.copy(
            featureReviews = fixture.featureReviews.dropLast(1) +
                fixture.featureReviews.first(),
        )
        val issues = ParameciumExternalReviewGate.unresolved(altered)
        assertTrue(issues.any { it.contains("feature list") })
        assertTrue(issues.any { it.contains("trichocysts: exactly one") })
        assertTrue(issues.any { it.contains("pellicle: exactly one") })
    }
}
