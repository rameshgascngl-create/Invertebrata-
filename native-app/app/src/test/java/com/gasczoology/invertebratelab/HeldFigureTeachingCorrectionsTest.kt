package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.BookFigureLibrary
import com.gasczoology.invertebratelab.data.TypeStudyFigureReviewNotes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Guards corrections for held source illustrations; does not mark them approved. */
class HeldFigureTeachingCorrectionsTest {
    @Test fun tenHeldTypeStudyFiguresHaveEnglishAndTamilCautions() {
        val expected = setOf(
            "paramecium/oral-apparatus", "sycon/external", "sycon/reproduction",
            "obelia/medusa", "obelia/life-cycle", "ascaris/female-internal",
            "earthworm/excretory", "pila/external", "asterias/internal",
            "asterias/larval-development"
        )
        assertEquals(expected, TypeStudyFigureReviewNotes.held.keys)
        for ((_, note) in TypeStudyFigureReviewNotes.held) {
            assertTrue(note.english.length > 80)
            assertTrue(note.tamil.length > 70)
        }
    }

    @Test fun pilaEyePositionAndObeliaCycleAreCorrectedWithoutChangingRaster() {
        val pila = TypeStudyFigureReviewNotes.held.getValue("pila/external")
        assertTrue(pila.english.contains("BASES"))
        assertTrue(pila.english.contains("not at their distal tips"))
        val obelia = TypeStudyFigureReviewNotes.held.getValue("obelia/life-cycle")
        for (stage in listOf("gonangia", "medusae", "gametes", "planula", "colony"))
            assertTrue(obelia.english.contains(stage))
    }

    @Test fun leishmaniaIsExplicitlyAnAmastigoteOnlyTeachingReference() {
        val credit = BookFigureLibrary.creditFor("lib/leishmania-amastigote")
        assertTrue(credit != null)
        assertTrue(credit!!.en.contains("AMASTIGOTE ONLY"))
        assertTrue(credit.en.contains("PROMASTIGOTE"))
        assertTrue(credit.ta.contains("புரோமாஸ்டிகோட்"))
        assertTrue(credit.en.contains("rights not yet verified"))
    }
}
