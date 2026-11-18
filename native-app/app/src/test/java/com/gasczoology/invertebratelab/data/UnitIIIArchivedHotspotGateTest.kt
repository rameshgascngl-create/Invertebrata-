package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class UnitIIIArchivedHotspotGateTest {
    @Test fun rejectedProceduralCoordinatesCannotBeReusedForArchivedRasters() {
        UnitIIIPlateHotspots.validate()
        assertEquals(6,UnitIIIPlateHotspots.byStem.size)
        for ((stem, organs) in UnitIIIPlateHotspots.byStem) {
            assertFalse("Unreviewed original source cannot enable direct taps: $stem",
                UnitIIIPlateHotspots.geometryApproved(stem))
            for (organ in organs) {
                assertTrue("Old source x survives: $stem / ${organ.id}",organ.x.isNaN())
                assertTrue("Old source y survives: $stem / ${organ.id}",organ.y.isNaN())
            }
        }
    }

    @Test fun everyAvailableOrganRequestsItsOwnEnglishAndTamilNarration() {
        UnitIIIPlateHotspots.validate()
        for ((stem, organs) in UnitIIIPlateHotspots.byStem) {
            for (organ in organs) {
                val requested = UnitIIIPlateHotspots.narrationFor(stem,organ.id)
                assertEquals(organ.explanation.english, requested.value(AppLanguage.ENGLISH))
                assertEquals(organ.explanation.tamil, requested.value(AppLanguage.TAMIL))
                assertTrue(requested.english.isNotBlank())
                assertTrue(requested.tamil.isNotBlank())
            }
            assertEquals(organs.size, organs.map{ it.id }.distinct().size)
        }
        assertThrows(IllegalArgumentException::class.java) {
            UnitIIIPlateHotspots.narrationFor("type_studies/ascaris/life-cycle","invented-lung-graphic")
        }
    }
}
