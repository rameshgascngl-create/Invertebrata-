package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumExternalGeometryTest {
    private val plate = ParameciumAnatomyDraft.plates.single {
        it.plateId == "external-cilia"
    }

    @Test
    fun everyProvisionalHotspotMapsToExactlyOneExistingAnatomyFeature() {
        ParameciumExternalGeometry.validateAgainst(plate)
        val specIds = plate.features.map { it.id }
        assertEquals(specIds, ParameciumExternalGeometry.hotspots.map { it.featureId })
        assertEquals(5, ParameciumExternalGeometry.hotspots.size)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertTrue(plate.features.all {
            it.geometryKey.startsWith("pending-") && it.touchTargetKey.startsWith("pending-")
        })
    }

    @Test
    fun touchingEachPrototypeCenterSelectsOnlyItsOwnOrgan() {
        for (area in ParameciumExternalGeometry.hotspots) {
            assertEquals(area.featureId,
                ParameciumExternalGeometry.hitNormalized(area.x, area.y))
        }
        assertNull(ParameciumExternalGeometry.hitNormalized(0.03f, 0.03f))
        assertNull(ParameciumExternalGeometry.hitNormalized(-0.1f, 0.5f))
        assertNull(ParameciumExternalGeometry.hitNormalized(1.2f, 0.4f))
        assertNull(ParameciumExternalGeometry.hitNormalized(Float.NaN, 0.4f))
        assertNull(ParameciumExternalGeometry.hitNormalized(0.5f, Float.POSITIVE_INFINITY))
    }

    @Test
    fun prototypeCoordinatesRemainWithinCanvasAndUniquelyAssigned() {
        assertTrue(ParameciumExternalGeometry.hotspots.all {
            it.x in 0f..1f && it.y in 0f..1f && it.radius in 0.01f..0.25f
        })
        assertThrows(IllegalArgumentException::class.java) {
            PrototypeHotspot("bad", Float.NaN, 0.1f, 0.1f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            PrototypeHotspot("bad", 0.5f, 0.1f, -0.1f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            ParameciumExternalGeometry.validateAgainst(
                plate.copy(status = AcademicWorkStatus.ACCEPTED)
            )
        }
    }
}
