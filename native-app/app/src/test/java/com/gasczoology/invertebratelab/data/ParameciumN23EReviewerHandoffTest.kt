package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Review preparation, not human signoff or anatomical approval. */
class ParameciumN23EReviewerHandoffTest {
    @Test
    fun allFiveFeatureQuestionsAreBilingualAndSourceScoped() {
        val handoff = ParameciumN23EReviewerHandoff
        handoff.verifyUnapprovedHandoff()
        assertEquals(ParameciumExternalReviewGate.requiredIds,
            handoff.entries.map { it.featureId })
        for (entry in handoff.entries) {
            assertTrue(entry.reviewQuestion.english.isNotBlank())
            assertTrue(entry.reviewQuestion.tamil.isNotBlank())
            assertTrue(entry.evidenceBoundary.english.isNotBlank())
            assertTrue(entry.evidenceBoundary.tamil.isNotBlank())
            assertTrue(entry.sourceUrl.startsWith("https://"))
            assertEquals(entry.featureId, entry.proposedHotspot.featureId)
            assertEquals(ExpertReviewVerdict.PENDING, entry.biologicalVerdict)
            assertEquals(ExpertReviewVerdict.PENDING, entry.tamilVerdict)
        }
    }

    @Test
    fun thinSectionsCannotBePromotedIntoWholeCellCoordinates() {
        val handoff = ParameciumN23EReviewerHandoff
        val cytoproct = handoff.forFeature("cytoproct")
        val trichocysts = handoff.forFeature("trichocysts")
        assertEquals(ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
            cytoproct.sourceModality)
        assertEquals(ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
            trichocysts.sourceModality)
        assertTrue(cytoproct.evidenceBoundary.english.contains("not this Canvas"))
        assertTrue(trichocysts.evidenceBoundary.english.contains("not a whole-cell map"))
        val oral = handoff.forFeature("oral-groove")
        assertTrue(oral.evidenceBoundary.english.contains("cannot establish"))
    }

    @Test
    fun handoffAndGreenCiDoNotGrantAnyApprovals() {
        val handoff = ParameciumN23EReviewerHandoff
        assertEquals(0, handoff.biologicalApprovals)
        assertEquals(0, handoff.tamilApprovals)
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
        val plate = ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertEquals(null, plate.review)
        assertTrue(ParameciumN23DReviewDossier.pendingPhysicalDeviceReview)
    }
}
