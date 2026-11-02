package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertTrue
import org.junit.Test

/** TEMPORARY measurement: the set of FAILED test names reveals the statistics. Removed next commit. */
class ZzPageFillReportTest {
    private object M {
        var oldAvg = 0.0; var newAvg = 0.0; var oldSparsePct = 0.0; var newSparsePct = 0.0
        var pageRatioPct = 0.0; var pagesNew = 0; var pagesOld = 0
        init {
            val oldFills = ArrayList<Double>(); val newFills = ArrayList<Double>()
            BookChapters.all.filter { BookLayout.unitNumber(it.lessonId) <= 5 }.forEach { book ->
                val rows = BookLayout.rows(book)
                var start = 0; var oldBody = 0
                while (start < rows.size) {
                    var end = start; var blocks = 0
                    while (end < rows.size && blocks < 2) {
                        if (end > start && blocks > 0 && rows[end] !is BookRow.Block) break
                        if (rows[end] is BookRow.Block) blocks++
                        end++
                    }
                    oldFills += (start until end).sumOf { BookReadingPages.weight(rows[it]) }.toDouble() / BookReadingPages.PAGE_BUDGET
                    oldBody++; start = end
                }
                pagesOld += 1 + book.introduction.size + 1 + oldBody + 4
                val pages = BookReadingPages.forChapter(book, emptyList())
                pages.filter { it.rowIndices.isNotEmpty() }.forEach { p ->
                    newFills += p.rowIndices.sumOf { BookReadingPages.weight(rows[it]) }.toDouble() / BookReadingPages.PAGE_BUDGET
                }
                pagesNew += pages.size
            }
            oldAvg = oldFills.average(); newAvg = newFills.average()
            oldSparsePct = 100.0 * oldFills.count { it < .45 } / oldFills.size
            newSparsePct = 100.0 * newFills.count { it < .45 } / newFills.size
            pageRatioPct = 100.0 * pagesNew / pagesOld
        }
    }
    @Test fun oldAvgFillAtLeast30() { assertTrue(M.oldAvg * 100 >= 30) }
    @Test fun newAvgFillAtLeast30() { assertTrue(M.newAvg * 100 >= 30) }
    @Test fun oldAvgFillAtLeast35() { assertTrue(M.oldAvg * 100 >= 35) }
    @Test fun newAvgFillAtLeast35() { assertTrue(M.newAvg * 100 >= 35) }
    @Test fun oldAvgFillAtLeast40() { assertTrue(M.oldAvg * 100 >= 40) }
    @Test fun newAvgFillAtLeast40() { assertTrue(M.newAvg * 100 >= 40) }
    @Test fun oldAvgFillAtLeast45() { assertTrue(M.oldAvg * 100 >= 45) }
    @Test fun newAvgFillAtLeast45() { assertTrue(M.newAvg * 100 >= 45) }
    @Test fun oldAvgFillAtLeast50() { assertTrue(M.oldAvg * 100 >= 50) }
    @Test fun newAvgFillAtLeast50() { assertTrue(M.newAvg * 100 >= 50) }
    @Test fun oldAvgFillAtLeast55() { assertTrue(M.oldAvg * 100 >= 55) }
    @Test fun newAvgFillAtLeast55() { assertTrue(M.newAvg * 100 >= 55) }
    @Test fun oldAvgFillAtLeast60() { assertTrue(M.oldAvg * 100 >= 60) }
    @Test fun newAvgFillAtLeast60() { assertTrue(M.newAvg * 100 >= 60) }
    @Test fun oldAvgFillAtLeast65() { assertTrue(M.oldAvg * 100 >= 65) }
    @Test fun newAvgFillAtLeast65() { assertTrue(M.newAvg * 100 >= 65) }
    @Test fun oldAvgFillAtLeast70() { assertTrue(M.oldAvg * 100 >= 70) }
    @Test fun newAvgFillAtLeast70() { assertTrue(M.newAvg * 100 >= 70) }
    @Test fun oldAvgFillAtLeast75() { assertTrue(M.oldAvg * 100 >= 75) }
    @Test fun newAvgFillAtLeast75() { assertTrue(M.newAvg * 100 >= 75) }
    @Test fun oldAvgFillAtLeast80() { assertTrue(M.oldAvg * 100 >= 80) }
    @Test fun newAvgFillAtLeast80() { assertTrue(M.newAvg * 100 >= 80) }
    @Test fun oldAvgFillAtLeast85() { assertTrue(M.oldAvg * 100 >= 85) }
    @Test fun newAvgFillAtLeast85() { assertTrue(M.newAvg * 100 >= 85) }
    @Test fun oldAvgFillAtLeast90() { assertTrue(M.oldAvg * 100 >= 90) }
    @Test fun newAvgFillAtLeast90() { assertTrue(M.newAvg * 100 >= 90) }
    @Test fun oldAvgFillAtLeast95() { assertTrue(M.oldAvg * 100 >= 95) }
    @Test fun newAvgFillAtLeast95() { assertTrue(M.newAvg * 100 >= 95) }
    @Test fun oldAvgFillAtLeast100() { assertTrue(M.oldAvg * 100 >= 100) }
    @Test fun newAvgFillAtLeast100() { assertTrue(M.newAvg * 100 >= 100) }
    @Test fun oldSparsePctAtMost5() { assertTrue(M.oldSparsePct <= 5) }
    @Test fun newSparsePctAtMost5() { assertTrue(M.newSparsePct <= 5) }
    @Test fun oldSparsePctAtMost10() { assertTrue(M.oldSparsePct <= 10) }
    @Test fun newSparsePctAtMost10() { assertTrue(M.newSparsePct <= 10) }
    @Test fun oldSparsePctAtMost15() { assertTrue(M.oldSparsePct <= 15) }
    @Test fun newSparsePctAtMost15() { assertTrue(M.newSparsePct <= 15) }
    @Test fun oldSparsePctAtMost20() { assertTrue(M.oldSparsePct <= 20) }
    @Test fun newSparsePctAtMost20() { assertTrue(M.newSparsePct <= 20) }
    @Test fun oldSparsePctAtMost25() { assertTrue(M.oldSparsePct <= 25) }
    @Test fun newSparsePctAtMost25() { assertTrue(M.newSparsePct <= 25) }
    @Test fun oldSparsePctAtMost30() { assertTrue(M.oldSparsePct <= 30) }
    @Test fun newSparsePctAtMost30() { assertTrue(M.newSparsePct <= 30) }
    @Test fun oldSparsePctAtMost40() { assertTrue(M.oldSparsePct <= 40) }
    @Test fun newSparsePctAtMost40() { assertTrue(M.newSparsePct <= 40) }
    @Test fun oldSparsePctAtMost50() { assertTrue(M.oldSparsePct <= 50) }
    @Test fun newSparsePctAtMost50() { assertTrue(M.newSparsePct <= 50) }
    @Test fun oldSparsePctAtMost60() { assertTrue(M.oldSparsePct <= 60) }
    @Test fun newSparsePctAtMost60() { assertTrue(M.newSparsePct <= 60) }
    @Test fun oldSparsePctAtMost70() { assertTrue(M.oldSparsePct <= 70) }
    @Test fun newSparsePctAtMost70() { assertTrue(M.newSparsePct <= 70) }
    @Test fun pagesNewOverOldAtMost50pct() { assertTrue(M.pageRatioPct <= 50) }
    @Test fun pagesNewOverOldAtMost55pct() { assertTrue(M.pageRatioPct <= 55) }
    @Test fun pagesNewOverOldAtMost60pct() { assertTrue(M.pageRatioPct <= 60) }
    @Test fun pagesNewOverOldAtMost65pct() { assertTrue(M.pageRatioPct <= 65) }
    @Test fun pagesNewOverOldAtMost70pct() { assertTrue(M.pageRatioPct <= 70) }
    @Test fun pagesNewOverOldAtMost75pct() { assertTrue(M.pageRatioPct <= 75) }
    @Test fun pagesNewOverOldAtMost80pct() { assertTrue(M.pageRatioPct <= 80) }
    @Test fun pagesNewOverOldAtMost85pct() { assertTrue(M.pageRatioPct <= 85) }
    @Test fun pagesNewOverOldAtMost90pct() { assertTrue(M.pageRatioPct <= 90) }
    @Test fun pagesNewOverOldAtMost95pct() { assertTrue(M.pageRatioPct <= 95) }
    @Test fun pagesNewOverOldAtMost100pct() { assertTrue(M.pageRatioPct <= 100) }
}
