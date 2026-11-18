package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumN23E2EvidenceIntakeTest {
    @Test fun sourcesRetainIndependentRightsAndModalityBoundaries() {
        val intake = ParameciumN23E2EvidenceIntake
        assertEquals(5, intake.references.size)
        assertTrue(intake.references.none { it.directCoordinateRegistration })
        assertEquals(E2ImageRights.COPYRIGHT_PERMISSION_REQUIRED,
            intake.reference("allen-whole-cell-fig0").rights)
        assertEquals(E2SourceAccess.SEARCH_INDEX_METADATA_ONLY,
            intake.reference("cil-39181-cytoproct").access)
        assertEquals(E2ImageRights.NOT_INDEPENDENTLY_CONFIRMED,
            intake.reference("cil-39181-cytoproct").rights)
        assertEquals(E2ImageRights.PUBLIC_DOMAIN_REPORTED_ON_RECORD,
            intake.reference("cil-36755-trichocysts").rights)
    }

    @Test fun emptyEvidenceHasNoSignoffsOrApproval() {
        val intake = ParameciumN23E2EvidenceIntake
        assertEquals(ParameciumExternalReviewGate.requiredIds,
            intake.emptySubmissions.map { it.featureId })
        assertFalse(intake.readyForIndependentDecision(intake.emptySubmissions))
        assertEquals(0, ParameciumN23EReviewerHandoff.biologicalApprovals)
        assertEquals(0, ParameciumN23EReviewerHandoff.tamilApprovals)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,
            ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }.status)
    }

    private fun completedFixture() = ParameciumExternalReviewGate.requiredIds.map { id ->
        E2FeatureEvidenceSubmission(
            featureId = id,
            wholeCellReferenceId = "allen-whole-cell-fig0",
            wholeCellFigureLocator = "fixture-only-specimen-image",
            orientationFinding = "fixture-only-orientation-finding",
            geometryComparisonEvidenceId = "fixture-only-overlay-$id",
            geometryFinding = "fixture-only-morphological-review-$id",
            biologicalReviewer = "fixture-only-zoologist",
            biologicalReviewDate = "2026-10-09",
            biologicalVerdict = ExpertReviewVerdict.SIGNED_OFF,
            tamilTerminologyEvidenceId = "fixture-only-tamil-$id",
            tamilFinding = "fixture-only-terminology-reading",
            tamilReviewer = "fixture-only-Tamil-reviewer",
            tamilReviewDate = "2026-10-09",
            tamilVerdict = ExpertReviewVerdict.SIGNED_OFF,
        )
    }

    @Test fun temOnlyEvidenceCannotActAsWholeCellRegistration() {
        val changed = completedFixture().map {
            if (it.featureId == "cytoproct") it.copy(
                wholeCellReferenceId = "cil-39181-cytoproct"
            ) else it
        }
        val issues = ParameciumN23E2EvidenceIntake.issues(changed)
        assertTrue(issues.any { it.contains("cytoproct: authentic whole-cell") })
        assertFalse(ParameciumN23E2EvidenceIntake.readyForIndependentDecision(changed))
    }

    @Test fun absentOrDuplicatedSignaturesAndEvidenceRemainBlocking() {
        val changed = completedFixture().map {
            if (it.featureId == "oral-groove") it.copy(
                tamilReviewer = "",
                tamilVerdict = ExpertReviewVerdict.PENDING,
                geometryComparisonEvidenceId = "",
            ) else it
        }
        val issues = ParameciumN23E2EvidenceIntake.issues(changed)
        assertTrue(issues.any { it.contains("oral-groove: independently inspected") })
        assertTrue(issues.any { it.contains("oral-groove: separate documented Tamil") })
        assertFalse(ParameciumN23E2EvidenceIntake.readyForIndependentDecision(changed))
        val duplicate = completedFixture().dropLast(1) + completedFixture().first()
        assertFalse(ParameciumN23E2EvidenceIntake.readyForIndependentDecision(duplicate))
    }

    @Test fun completeFixtureIsReviewReadinessOnlyNotAcademicAcceptance() {
        assertTrue(ParameciumN23E2EvidenceIntake.readyForIndependentDecision(
            completedFixture()))
        val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertEquals(null, plate.review)
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
    }
}
