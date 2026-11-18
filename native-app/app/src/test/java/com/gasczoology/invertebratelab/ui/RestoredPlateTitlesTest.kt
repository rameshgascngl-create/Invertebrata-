package com.gasczoology.invertebratelab.ui

import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.NativeCurriculumManifest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RestoredPlateTitlesTest {
    @Test fun all35TypeStudyPlateNamesHaveSeparateTamilAndEnglishTerms() {
        val keys = NativeCurriculumManifest.typeStudies.flatMap { study ->
            study.requiredPlateIds.map { study.organismId + "/" + it }
        }
        assertEquals(35, keys.size)
        assertEquals(35, keys.toSet().size)
        assertEquals(keys.toSet(), RestoredPlateTitles.byId.keys)
        for (study in NativeCurriculumManifest.typeStudies) {
            for (plate in study.requiredPlateIds) {
                val en = RestoredPlateTitles.value(study.organismId, plate, AppLanguage.ENGLISH)
                val ta = RestoredPlateTitles.value(study.organismId, plate, AppLanguage.TAMIL)
                assertTrue("Empty English term: $plate", en.length >= 10)
                assertTrue("Empty Tamil term: $plate", ta.length >= 5)
                assertFalse("English fallback used in Tamil: $plate", en == ta)
                assertTrue("Tamil script absent: $plate", ta.any { it in '\u0B80'..'\u0BFF' })
            }
        }
    }
    @Test fun terminologyAvoidsFalseHydranthAndGonangiumEquivalence() {
        val a = RestoredPlateTitles.value("obelia", "hydranth-gonangium", AppLanguage.ENGLISH)
        assertTrue(a.contains("Hydranth"))
        assertTrue(a.contains("gonangium"))
        val b = RestoredPlateTitles.value("pila", "ctenidium", AppLanguage.ENGLISH)
        assertTrue(b.contains("osphradium"))
    }
}
