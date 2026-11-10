package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.SourcedQaAnatomicalPlates
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SourcedQaAnatomicalPlatesTest {
    @Test fun onlyThreeSourceDocumentedHeldPlatesHaveDebugOverrides() {
        assertEquals(setOf("sycon/reproduction", "asterias/internal", "asterias/larval-development"),
            SourcedQaAnatomicalPlates.byTypeStudy.keys)
        SourcedQaAnatomicalPlates.byTypeStudy.values.forEach { stem ->
            assertTrue(stem.startsWith("qa_source_plates/"))
        }
    }
    @Test fun scientificSourceCautionsRemain() {
        val credits = SourcedQaAnatomicalPlates.credit
        assertTrue(credits.getValue("sycon/reproduction").english.contains("not sperm transfer"))
        assertTrue(credits.getValue("asterias/internal").english.contains("Hans Hillewaert"))
        assertTrue(credits.getValue("asterias/larval-development").english.contains("bipinnaria"))
        assertTrue(credits.values.all { it.english.contains("review pending") || it.english.contains("review pending.") ||
                    it.english.contains("review pending,") || it.english.contains("expert review pending.") ||
                    it.english.contains("sign-off pending.") })
    }
}
