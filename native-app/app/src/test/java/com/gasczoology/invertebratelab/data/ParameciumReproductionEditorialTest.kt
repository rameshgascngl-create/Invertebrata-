package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Protects the undergraduate-to-postgraduate Paramecium editorial sequence.
 * Structural checks do not substitute for independent zoology or Tamil review.
 */
class ParameciumReproductionEditorialTest {
    private val chapter = ParameciumBookChapter.chapter
    private val section = chapter.sections.single { it.id == "nuclei-reproduction" }

    @Test fun undergraduateMechanismsPrecedePostgraduateNuclearReorganization() {
        val headings = section.subsections.map { it.heading.english }
        assertEquals(4, headings.size)
        assertTrue(headings.take(3).all { it.endsWith("(UG)") })
        assertTrue(headings.last().endsWith("(PG)"))
        assertTrue(section.subsections.all { it.heading.tamil.isNotBlank() && it.blocks.isNotEmpty() })
        assertTrue(section.subsections[1].blocks.any { it is BookKeyPoints })
        assertTrue(section.subsections.last().blocks.any { it is BookNote })
    }

    @Test fun conjugationTableDoesNotPutMacronuclearDegenerationAtInitialPairing() {
        val table = section.subsections.flatMap { it.blocks }
            .filterIsInstance<BookTable>().single()
        assertEquals(6, table.rows.size)
        val first = table.rows.first()[1].english
        val last = table.rows.last()[1].english
        assertTrue(first.contains("initially present"))
        assertFalse(first.contains("degenerates"))
        assertTrue(last.contains("synkaryon"))
        assertTrue(last.contains("degenerates"))
    }

    @Test fun chapterPreservesExistingFiguresAndFortyThreeA5Chapters() {
        val figures = chapter.allBlocks().filterIsInstance<BookFigure>().map { it.slot }
        assertEquals(listOf("paramecium/external-cilia", "paramecium/locomotion",
            "paramecium/oral-apparatus", "paramecium/contractile-vacuoles"), figures)
        assertEquals(43, NativeCurriculumManifest.a5ChapterIdsByUnit.values.sumOf { it.size })
    }
}
