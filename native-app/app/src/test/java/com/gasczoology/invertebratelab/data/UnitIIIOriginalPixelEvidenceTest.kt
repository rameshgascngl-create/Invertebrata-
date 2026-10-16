package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class UnitIIIOriginalPixelEvidenceTest {
    @Test fun centerCropMapsExactlyToOriginalPixels() {
        val w = UnitIIIOriginalPixelEvidence.window(960,1280,480.5f,640.75f)!!
        assertEquals(390,w.x)
        assertEquals(550,w.y)
        assertEquals(181,w.width)
        assertEquals(181,w.height)
        assertEquals(90.5f,w.focusX,.0001f)
        assertEquals(90.75f,w.focusY,.0001f)
    }

    @Test fun sourceBoundaryCropNeverInventsPixels() {
        val edge = UnitIIIOriginalPixelEvidence.window(960,1280,959.5f,1279.5f)!!
        assertEquals(779,edge.x)
        assertEquals(1099,edge.y)
        assertEquals(180.5f,edge.focusX,.0001f)
        val small = UnitIIIOriginalPixelEvidence.window(50,60,25f,35f)!!
        assertEquals(50,small.width)
        assertEquals(60,small.height)
        assertEquals(0,small.x)
        assertEquals(0,small.y)
    }

    @Test fun invalidOrOffImageCandidatesAreRejectedRatherThanSnapped() {
        assertNull(UnitIIIOriginalPixelEvidence.window(960,1280,960f,600f))
        assertNull(UnitIIIOriginalPixelEvidence.window(960,1280,500f,1280f))
        assertNull(UnitIIIOriginalPixelEvidence.window(960,1280,Float.NaN,600f))
        assertNull(UnitIIIOriginalPixelEvidence.window(960,1280,20f,Float.POSITIVE_INFINITY))
        assertNull(UnitIIIOriginalPixelEvidence.window(0,1280,20f,30f))
        assertNull(UnitIIIOriginalPixelEvidence.window(960,1280,20f,30f,500))
    }

    @Test fun noFindingCanRepresentAcademicApproval() {
        val findings = UnitIIIReviewFinding.values().map{it.name}.toSet()
        assertEquals(setOf("NOT_REVIEWED","VISIBLE_CANDIDATE","CANNOT_RESOLVE","WRONG_STRUCTURE"),
            findings)
        assertFalse(findings.any { it.contains("APPROV") || it.contains("RELEASE") })
        assertEquals("xy",UnitIIIOriginalPixelEvidence.boundedNote("x\u0000y"))
        assertEquals(400,UnitIIIOriginalPixelEvidence.boundedNote("x".repeat(900)).length)
    }

    @Test fun unreviewedSourceBoundCoordinatesRemainDraftAfterVisualFinding() {
        val p = UnitIIIPixelReviewMark("oral",480f,640f,
            UnitIIIReviewFinding.VISIBLE_CANDIDATE,"Clearly inspect at original resolution")
        val draft=UnitIIIPixelReviewDraft(
            "type_studies/fasciola/external","a".repeat(64),960,1280,listOf(p))
        assertEquals("DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF",draft.approvalStatus)
        assertTrue(runCatching{
            UnitIIIPixelReviewDraft(
                "type_studies/fasciola/external","a".repeat(64),960,1280,
                listOf(p.copy(note="a".repeat(401)))
            )
        }.isFailure)
    }
}
