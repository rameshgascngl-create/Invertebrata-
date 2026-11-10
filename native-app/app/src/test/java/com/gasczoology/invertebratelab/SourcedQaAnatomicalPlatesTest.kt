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
        assertTrue(credits.getValue("sycon/reproduction").en.contains("not sperm transfer"))
        assertTrue(credits.getValue("asterias/internal").en.contains("Hans Hillewaert"))
        assertTrue(credits.getValue("asterias/larval-development").en.contains("bipinnaria"))
        assertTrue(credits.values.all { it.en.contains("review pending") || it.en.contains("review pending.") ||
                    it.en.contains("review pending,") || it.en.contains("expert review pending.") ||
                    it.en.contains("sign-off pending.") })
    }
}
