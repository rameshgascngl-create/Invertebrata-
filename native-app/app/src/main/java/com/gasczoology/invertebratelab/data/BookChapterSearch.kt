package com.gasczoology.invertebratelab.data

import java.util.Locale

/**
 * Offline, chapter-local search over authored textbook blocks.
 *
 * Results use semantic page IDs rather than a fragile numeric page index, so switching
 * languages or changing the reader's font scale does not change their destinations.
 * No user data or queries leave the device.
 */
data class BookSearchHit(
    val pageId: String,
    val rowIndex: Int,
    val excerpt: String,
)

object BookChapterSearch {
    private fun text(row: BookRow, language: AppLanguage): String = when (row) {
        is BookRow.SectionHeading -> row.heading.value(language)
        is BookRow.SubHeading -> row.heading.value(language)
        is BookRow.Block -> when (val block = row.block) {
            is BookParagraph -> block.text.value(language)
            is BookDefinition -> block.term.value(language) + " " + block.meaning.value(language)
            is BookKeyPoints -> block.title.value(language) + " " +
                block.points.joinToString(" ") { it.value(language) }
            is BookNote -> block.title.value(language) + " " + block.text.value(language)
            is BookFigure -> block.caption.value(language)
            is BookTable -> block.rows.joinToString(" ") { cells ->
                cells.joinToString(" ") { it.value(language) }
            }
        }
    }

    fun find(
        chapter: BookChapter,
        language: AppLanguage,
        query: String,
        questionIds: List<String> = emptyList(),
    ): List<BookSearchHit> {
        val needle = query.trim().lowercase(Locale.ROOT)
        if (needle.isEmpty()) return emptyList()
        val rows = BookLayout.rows(chapter)
        val pages = BookReadingPages.forChapter(chapter, questionIds)
        val destinations = pages.flatMap { page -> page.rowIndices.map { it to page.id } }.toMap()
        return rows.mapIndexedNotNull { index, row ->
            val content = text(row, language)
            if (!content.lowercase(Locale.ROOT).contains(needle)) null
            else destinations[index]?.let { pageId ->
                BookSearchHit(pageId, index, content.take(220))
            }
        }
    }
}
