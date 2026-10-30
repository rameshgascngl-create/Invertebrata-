package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Candidate geometry clarity, NOT scientific acceptance of anatomical pixels. */
class ParameciumC3ReviewGeometryTest {
    @Test
    fun oralAnatomyHasSpeciesAuthenticReferenceLinksButNoCoordinateSignoff() {
        ParameciumOralReferenceComparison.verifyNoPrematureGeometryApproval()
        assertEquals(
            "https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html",
            ParameciumOralReferenceComparison.WHOLE_CELL_URL
        )
        assertEquals(
            "https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html",
            ParameciumOralReferenceComparison.ORAL_TRANSVERSE_URL
        )
        val oral = ParameciumExternalEvidence.records.single {
            it.featureId == "oral-groove"
        }
        assertEquals(ParameciumSourceScope.CAUDATUM, oral.scope)
        assertFalse(oral.positionReviewed)
        assertFalse(oral.tamilReviewed)
    }

    @Test
    fun largerSelectionRingClearsClosedRidgeAtEveryScale() {
        ParameciumReviewHighlightContract.verifyVisualAndAcademicGuard()
        assertEquals(34f, ParameciumReviewHighlightContract.radiusFor("cytoproct"), 0f)
        for (other in ParameciumExternalGeometry.hotspots.map { it.featureId }
            .filter { it != "cytoproct" }) {
            assertEquals(19f, ParameciumReviewHighlightContract.radiusFor(other), 0f)
        }
        assertTrue(ParameciumReviewHighlightContract.cytoproctRidgeClearance() >= 8f)
        val area = ParameciumExternalGeometry.hotspots.single { it.featureId == "cytoproct" }
        for ((width, height) in listOf(360f to 280f, 1080f to 280f,
            400f to 800f, 1200f to 800f)) {
            val view = PrototypeCanvasViewport.fit(width, height)
            val (cx, cy) = view.referenceToCanvas(
                area.x * ParameciumExternalGeometry.REFERENCE_WIDTH,
                area.y * ParameciumExternalGeometry.REFERENCE_HEIGHT
            )
            assertEquals("cytoproct", ParameciumExternalGeometry.hitCanvas(
                cx, cy, width, height))
            assertTrue(
                ParameciumReviewHighlightContract.cytoproctRidgeClearance() *
                    view.scale > 0f
            )
        }
    }

    @Test
    fun scientificReviewGateRemainsClosedWithUnreviewedGeometry() {
        val plate = ParameciumAnatomyDraft.plates.single {
            it.plateId == "external-cilia"
        }
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertEquals(null, plate.review)
        assertTrue(plate.features.all { it.geometryKey.startsWith("pending-") })
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
    }
}
