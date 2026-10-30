package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * N2.3C2: verifies a drawable schematic AND prevents false academic promotion.
 * Passing tests cannot substitute for a species-figure overlay or expert signoff.
 */
class ParameciumCytoproctRidgeCandidateTest {
    @Test
    fun speciesAuthenticatedSectionSupportsRidgeButNotCanvasCoordinates() {
        val ridge = ParameciumCytoproctRidgeCandidate
        ridge.verifySchematicContract()
        val evidence = ParameciumExternalEvidence.records.single {
            it.featureId == "cytoproct"
        }
        assertEquals("https://www.cellimagelibrary.org/images/39181", ridge.SOURCE_URL)
        assertEquals(ridge.SOURCE_URL, evidence.sourceUrl)
        assertEquals(ParameciumSourceScope.CAUDATUM, evidence.scope)
        assertTrue(evidence.biologicalClaim.contains("ridge"))
        assertFalse(evidence.positionReviewed)
        assertFalse(evidence.tamilReviewed)
        val plate = ParameciumAnatomyDraft.plates.single {
            it.plateId == "external-cilia"
        }
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertEquals(null, plate.review)
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
    }

    @Test
    fun schematicRidgeEndpointsRemainWithinPreviousHitRegionOnAllViewports() {
        val ridge = ParameciumCytoproctRidgeCandidate
        val points = listOf(ridge.start, ridge.control, ridge.end)
        assertEquals(3, points.toSet().size)
        assertTrue(ridge.end.x > ridge.start.x)
        assertTrue(ridge.end.y > ridge.start.y)
        assertEquals("cytoproct", ParameciumExternalGeometry.hitNormalized(
            0.776f, 0.70f))
        for ((width, height) in listOf(360f to 280f, 1080f to 280f,
            400f to 800f, 1200f to 800f)) {
            val fit = PrototypeCanvasViewport.fit(width, height)
            for (point in points) {
                val (x, y) = fit.referenceToCanvas(point.x, point.y)
                assertEquals("cytoproct", ParameciumExternalGeometry.hitCanvas(
                    x, y, width, height))
            }
        }
    }
}
