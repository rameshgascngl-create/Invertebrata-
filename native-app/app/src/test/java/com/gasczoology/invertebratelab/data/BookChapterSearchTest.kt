package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class BookChapterSearchTest {
    @Test fun blankQueriesReturnNoResults() {
        val chapter = BookChapters.all.first()
        assertTrue(BookChapterSearch.find(chapter, AppLanguage.ENGLISH, "   ").isEmpty())
    }

    @Test fun searchDestinationsAlwaysPointToAuthoredPages() {
        BookChapters.all.forEach { chapter ->
            val pages = BookReadingPages.forChapter(chapter, emptyList())
            val pageIds = pages.map { it.id }.toSet()
            val rows = BookLayout.rows(chapter)
            listOf(AppLanguage.ENGLISH, AppLanguage.TAMIL).forEach { language ->
                rows.filterIsInstance<BookRow.SectionHeading>().take(2).forEach { heading ->
                    val query = heading.heading.value(language).trim()
                    if (query.isNotEmpty()) {
                        BookChapterSearch.find(chapter, language, query).forEach { hit ->
                            assertTrue("${chapter.lessonId}: ${hit.pageId}", hit.pageId in pageIds)
                            assertTrue(hit.rowIndex in rows.indices)
                            assertTrue(hit.rowIndex in pages.first { it.id == hit.pageId }.rowIndices)
                        }
                    }
                }
            }
        }
    }

    @Test fun englishSearchIgnoresCaseAndWhitespace() {
        val chapter = BookChapters.all.first()
        val heading = BookLayout.rows(chapter).filterIsInstance<BookRow.SectionHeading>().first()
            .heading.english.trim()
        if (heading.isNotBlank()) {
            val results = BookChapterSearch.find(chapter, AppLanguage.ENGLISH, "  ${heading.uppercase()}  ")
            assertTrue("Expected to find the authored heading", results.isNotEmpty())
        }
    }
}
