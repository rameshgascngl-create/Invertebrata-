package com.gasczoology.invertebratelab.data

import org.junit.Assert.fail
import org.junit.Test

/** TEMPORARY measurement: always fails so CI prints the numbers. Removed in the next commit. */
class ZzPageFillReportTest {
    @Test fun report() {
        var oldPages = 0; var newPages = 0; var oldChapters = 0
        val oldFills = ArrayList<Double>(); val newFills = ArrayList<Double>()
        val perChapter = StringBuilder()
        BookChapters.all.filter { BookLayout.unitNumber(it.lessonId) <= 5 }.forEach { book ->
            val rows = BookLayout.rows(book)
            // Old algorithm: separate pages for each introduction paragraph, contents, summary, glossary,
            // quick checks, references, and at most two blocks per body page.
            var start = 0; var oldBody = 0
            while (start < rows.size) {
                var end = start; var blocks = 0
                while (end < rows.size && blocks < 2) {
                    if (end > start && blocks > 0 && rows[end] !is BookRow.Block) break
                    if (rows[end] is BookRow.Block) blocks++
                    end++
                }
                val w = (start until end).sumOf { BookReadingPages.weight(rows[it]) }
                oldFills += w.toDouble() / BookReadingPages.PAGE_BUDGET; oldBody++
                start = end
            }
            val oldTotal = 1 + book.introduction.size + 1 + oldBody + 4
            val pages = BookReadingPages.forChapter(book, emptyList())
            val body = pages.filter { it.rowIndices.isNotEmpty() }
            body.forEach { p -> newFills += p.rowIndices.sumOf { BookReadingPages.weight(rows[it]) }.toDouble() / BookReadingPages.PAGE_BUDGET }
            oldPages += oldTotal; newPages += pages.size; oldChapters++
            perChapter.append("${book.lessonId}:${oldTotal}->${pages.size}(body ${oldBody}->${body.size}) ")
        }
        fun sparse(f: List<Double>) = f.count { it < .45 }
        fail("REPORT chapters=$oldChapters pages(no practice) old=$oldPages new=$newPages | " +
            "old body pages=${oldFills.size} avgFill=${"%.2f".format(oldFills.average())} sparse<45%=${sparse(oldFills)} | " +
            "new body pages=${newFills.size} avgFill=${"%.2f".format(newFills.average())} sparse<45%=${sparse(newFills)} | " +
            perChapter.toString().take(2500))
    }
}
