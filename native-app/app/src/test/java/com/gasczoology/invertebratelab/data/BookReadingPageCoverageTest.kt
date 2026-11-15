package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Protects the page-wise textbook contract: no section or figure may be
 * silently dropped, repeated or placed on an empty content page.
 */
class BookReadingPageCoverageTest {
    @Test fun parameciumRowsAreCoveredExactlyOnce() {
        val chapter = ParameciumBookChapter.chapter
        val rows = BookLayout.rows(chapter)
        val pages = BookReadingPages.forChapter(chapter, chapter.reviewQuestionIds)
        val indices = pages.flatMap { it.rowIndices }
        assertEquals("All chapter rows must appear once", rows.indices.toList(), indices)
        assertTrue("Content pages must not be empty",
            pages.filter { it.id.startsWith("row-") }.all { it.rowIndices.isNotEmpty() })
        assertEquals("Page IDs must be unique", pages.size, pages.map { it.id }.toSet().size)
    }

    @Test fun approvedParameciumPlateAppearsOnExactlyOnePage() {
        val chapter = ParameciumBookChapter.chapter
        val figureIndices = BookLayout.rows(chapter).mapIndexedNotNull { index, row ->
            if (row is BookRow.Block && row.block is BookFigure &&
                row.block.slot == "lib/paramecium-reproduction-approved") index else null
        }
        assertEquals("Exactly one approved plate is expected", 1, figureIndices.size)
        val pages = BookReadingPages.forChapter(chapter, chapter.reviewQuestionIds)
        assertEquals(1, pages.count { figureIndices.single() in it.rowIndices })
    }
}
