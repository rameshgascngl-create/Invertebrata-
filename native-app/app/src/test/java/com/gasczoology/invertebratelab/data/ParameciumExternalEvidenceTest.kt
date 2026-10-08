package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumExternalEvidenceTest {
    private val plate get() = ParameciumAnatomyDraft.plates.single {
        it.plateId == "external-cilia"
    }

    @Test
    fun allFiveExistingOrganIdsHavePreciseScopeAwareScientificReferences() {
        ParameciumExternalEvidence.validateAgainst(plate)
        val records = ParameciumExternalEvidence.records
        assertEquals(5, records.size)
        assertEquals(plate.features.map { it.id }, records.map { it.featureId })
        assertTrue(records.all {
            it.sourceUrl.startsWith("https://") &&
                it.exactLocator.isNotBlank() && it.biologicalClaim.isNotBlank()
        })
        assertEquals(ParameciumSourceScope.COMPARATIVE_OTHER_SPECIES,
            records.single { it.featureId == "cytoproct" }.scope)
        assertEquals(ParameciumSourceScope.CAUDATUM,
            records.single { it.featureId == "trichocysts" }.scope)
    }

    @Test
    fun figureOrientationAndAcademicApprovalMustRemainExplicitlyPending() {
        val view = ParameciumExternalEvidence.view
        assertEquals("Paramecium caudatum", view.referenceSpecies)
        assertTrue(view.anteriorOnLeft)
        assertTrue(view.posteriorOnRight)
        assertTrue(view.oralVentralOnBottom)
        assertFalse(view.orientationVerifiedAgainstFigure)
        assertTrue(ParameciumExternalEvidence.records.all {
            !it.positionReviewed && !it.tamilReviewed
        })
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertTrue(plate.features.all {
            it.geometryKey.startsWith("pending-") && it.touchTargetKey.startsWith("pending-")
        })
        assertThrows(IllegalArgumentException::class.java) {
            ParameciumExternalEvidence.validateAgainst(
                plate.copy(status = AcademicWorkStatus.ACCEPTED)
            )
        }
    }

    @Test
    fun nativeContourDefinesRoundedLeftAndTaperedRightAndClosedPath() {
        val contour = ParameciumSchematicContour
        assertEquals(6, contour.segments.size)
        assertEquals(contour.anterior, contour.segments.last().end)
        assertEquals(contour.posteriorTip, contour.segments[2].end)
        assertTrue(contour.anterior.x < contour.posteriorTip.x)
        assertTrue(contour.posteriorTip.x > 850f)
        assertTrue(contour.segments.all { segment ->
            listOf(segment.control1, segment.control2, segment.end).all {
                it.x in 0f..1000f && it.y in 0f..600f
            }
        })
    }

    @Test
    fun cytoproctHotspotTracksUpdatedProvisionalVentralEndpoint() {
        val area = ParameciumExternalGeometry.hotspots.single {
            it.featureId == "cytoproct"
        }
        assertEquals(0.776f, area.x, 0.00001f)
        assertEquals(0.70f, area.y, 0.00001f)
        assertEquals("cytoproct",
            ParameciumExternalGeometry.hitNormalized(area.x, area.y))
    }
}
