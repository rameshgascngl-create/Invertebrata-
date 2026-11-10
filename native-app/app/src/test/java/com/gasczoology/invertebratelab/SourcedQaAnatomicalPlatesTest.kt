package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.SourcedQaAnatomicalPlates
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SourcedQaAnatomicalPlatesTest {
    @Test fun tenSourceDocumentedTypeStudyPlatesHaveStandardBuildOverrides() {
        assertEquals(setOf("sycon/reproduction", "asterias/internal", "asterias/larval-development",
            "paramecium/oral-apparatus","sycon/external","obelia/medusa","obelia/life-cycle",
            "ascaris/female-internal","earthworm/excretory","pila/external"),
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
        assertEquals(10, credits.size)
        assertTrue(credits.values.all { it.english.length > 80 && it.tamil.any { ch -> ch in '\u0B80'..'\u0BFF' } })
        assertTrue(credits.values.none { it.english.contains("review pending", ignoreCase = true) })
        assertTrue(credits.getValue("pila/external").english.contains("OUTER BASES"))
        assertTrue(credits.getValue("obelia/life-cycle").english.contains("eight-stage", ignoreCase = true))
        assertTrue(credits.getValue("ascaris/female-internal").english.contains("LEFT"))
        assertTrue(credits.getValue("sycon/external").english.contains("GENERALISED SYCONOID"))
    }
}
