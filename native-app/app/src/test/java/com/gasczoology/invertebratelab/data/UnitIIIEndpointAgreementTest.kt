package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class UnitIIIEndpointAgreementTest {
    private val sha="c".repeat(64)
    private val plate="type_studies/fasciola/external"
    private fun mark(id:String,x:Float,y:Float)=UnitIIIPixelReviewMark(id,x,y)
    private fun draft(
        stem:String=plate,
        hash:String=sha,
        w:Int=960,
        h:Int=1280,
        marks:List<UnitIIIPixelReviewMark> = listOf(mark("oral",400f,300f))
    ) = UnitIIIPixelReviewDraft(stem,hash,w,h,marks)

    @Test fun equalPointsRemainOnlyGeometricallySimilarNeverAcademicallyApproved() {
        val result=UnitIIIEndpointAgreement.compare(
            draft(),draft(),tolerancePixels=8f)
        assertEquals(1,result.withinTolerance)
        assertEquals(0,result.needsAttention)
        assertEquals(EndpointReviewFinding.WITHIN_PIXEL_TOLERANCE,
            result.rows.single().finding)
        assertFalse(result.approvedForStudentHotspots)
        assertEquals("DRAFT_COMPARISON_NOT_ANATOMY_APPROVAL",result.status)
        assertEquals(0f,result.rows.single().distancePixels!!,0.001f)
    }

    @Test fun rejectsChangedPngCrossOrganismGeometryOrInvalidTolerance() {
        assertTrue(runCatching { UnitIIIEndpointAgreement.compare(
            draft(),draft(hash="d".repeat(64))) }.isFailure)
        assertTrue(runCatching { UnitIIIEndpointAgreement.compare(
            draft(),draft(stem="type_studies/ascaris/life-cycle",
                marks=listOf(mark("ingestion",400f,300f)))) }.isFailure)
        assertTrue(runCatching { UnitIIIEndpointAgreement.compare(
            draft(),draft(w=1000)) }.isFailure)
        for (tol in listOf(-1f,0f,Float.NaN,Float.POSITIVE_INFINITY,55f)) {
            assertTrue(runCatching {
                UnitIIIEndpointAgreement.compare(draft(),draft(),tol)
            }.isFailure)
        }
    }

    @Test fun flagsEightPixelBoundaryMissesAndAbsentOrganEvidence() {
        val first=draft(marks=listOf(
            mark("oral",100f,200f),mark("genital",450f,450f)))
        val second=draft(marks=listOf(
            mark("oral",106f,208f),mark("acetabulum",250f,200f)))
        val c=UnitIIIEndpointAgreement.compare(first,second,8f)
        assertEquals(0,c.withinTolerance) // oral distance 10px, not <=8px
        assertEquals(3,c.needsAttention)
        val byId=c.rows.associateBy { it.organId }
        assertEquals(EndpointReviewFinding.REQUIRES_ANATOMY_REVIEW,
            byId.getValue("oral").finding)
        assertEquals(10f,byId.getValue("oral").distancePixels!!,0.01f)
        assertEquals(EndpointReviewFinding.ONLY_IN_FIRST_DRAFT,
            byId.getValue("genital").finding)
        assertEquals(EndpointReviewFinding.ONLY_IN_SECOND_DRAFT,
            byId.getValue("acetabulum").finding)
        assertFalse(c.approvedForStudentHotspots)
    }

    @Test fun finiteEuclideanDistanceAgreesWithTolerance() {
        val first=draft(marks=listOf(mark("oral",100f,200f)))
        val close=draft(marks=listOf(mark("oral",103f,204f)))
        val report=UnitIIIEndpointAgreement.compare(first,close,5f)
        assertEquals(1,report.withinTolerance)
        assertEquals(5f,report.rows.single().distancePixels!!,0.01f)
    }
}
