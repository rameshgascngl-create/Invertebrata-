package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class UnitIIIPixelReviewDraftTest {
    private val sha = "a".repeat(64)
    private fun mark(id: String, x: Float=212.5f, y: Float=640.75f) =
        UnitIIIPixelReviewMark(id,x,y)

    @Test fun allSixCurriculumPlatesHaveSeparateReviewableOrganVocabulary() {
        UnitIIIPlateCatalog.validateAgainstSyllabus()
        assertEquals(6,UnitIIIPlateCatalog.plates.size)
        for (plate in UnitIIIPlateCatalog.plates) {
            val id=UnitIIIPlateHotspots.forStem(plate.assetStem).first().id
            val draft=UnitIIIPixelReviewDraft(plate.assetStem,sha,960,1280,listOf(mark(id)))
            assertEquals("DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF",draft.approvalStatus)
            assertEquals(1,draft.normalizedPoints().size)
            assertEquals(212.5f/960f,draft.normalizedPoints()[0].first,0.00001f)
        }
        assertFalse(UnitIIIPlateHotspots.geometryApproved("type_studies/fasciola/external"))
        assertFalse(UnitIIIPlateHotspots.geometryApproved("type_studies/ascaris/life-cycle"))
    }

    @Test fun rejectsUnknownAndCrossOrganismPointsAndBadSourceIdentity() {
        val spec="type_studies/fasciola/external"
        fun bad(stem:String=spec, checksum:String=sha, id:String="oral") = runCatching {
            UnitIIIPixelReviewDraft(stem,checksum,960,1280,listOf(mark(id)))
        }.isFailure
        assertTrue(bad("type_studies/unknown/external"))
        assertTrue(bad(checksum="unverified"))
        assertTrue(bad(id="female-vulva"))
        assertTrue(bad(id="invalid-hypothetical-organ"))
        assertTrue(runCatching {
            UnitIIIPixelReviewDraft(spec,sha,960,1280,emptyList())
        }.isFailure)
    }

    @Test fun rejectsOutsideSourceAndDuplicateEndpoints() {
        val spec="type_studies/fasciola/external"
        for ((x,y) in listOf(
            -1f to 200f, 960f to 200f, 5f to 1280f,
            Float.NaN to 2f, 1f to Float.POSITIVE_INFINITY
        )) {
            assertTrue(runCatching {
                UnitIIIPixelReviewDraft(spec,sha,960,1280,listOf(mark("oral",x,y)))
            }.isFailure)
        }
        assertTrue(runCatching {
            UnitIIIPixelReviewDraft(spec,sha,960,1280,listOf(
                mark("oral"),mark("oral",300f,450f)))
        }.isFailure)
        assertFalse(runCatching {
            UnitIIIPixelReviewDraft(spec,sha,960,1280,listOf(mark("oral",0f,0f)))
        }.isFailure)
    }

    @Test fun hitMappingRemainsSourcePixelBoundAfterZoomPan() {
        val r=NativeRasterCalibration.project(
            452f,550f,360f,720f,960,1280,3f,20f,-90f)
        assertNotNull(r)
        val h=NativeRasterCalibration.hit(
            r!!.first,r.second,360f,720f,960,1280,3f,20f,-90f)
        assertNotNull(h)
        assertEquals(452f,h!!.pixelX,0.01f)
        assertEquals(550f,h.pixelY,0.01f)
        assertNull(NativeRasterCalibration.hit(
            Float.NaN,1f,360f,720f,960,1280))
    }
}
