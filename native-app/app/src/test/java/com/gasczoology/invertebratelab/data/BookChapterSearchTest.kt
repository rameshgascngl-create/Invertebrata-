package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class BookChapterSearchTest {
    private fun pagesOf(book: BookChapter) = BookReadingPages.forChapter(book, book.reviewQuestionIds)

    @Test fun shortOrBlankQueriesFindNothing() {
        val book = BookChapters.all.first()
        listOf("", " ", "a", "  x ").forEach {
            assertTrue(BookChapterSearch.search(book, pagesOf(book), emptyList(), it, AppLanguage.ENGLISH).isEmpty())
        }
    }

    /** Every definition term is findable and opens a page that really contains that definition row. */
    @Test fun everyDefinitionTermResolvesToItsOwnPageAndRow() {
        BookChapters.all.forEach { book ->
            val pages = pagesOf(book)
            val rows = BookLayout.rows(book)
            rows.forEachIndexed { i, row ->
                val def = (row as? BookRow.Block)?.block as? BookDefinition ?: return@forEachIndexed
                listOf(AppLanguage.ENGLISH, AppLanguage.TAMIL).forEach { lang ->
                    val term = def.term.value(lang)
                    val hit = BookChapterSearch.search(book, pages, emptyList(), term, lang, limit = 500)
                        .firstOrNull { it.rowIndex == i && it.kind == SearchKind.DEFINITION }
                    assertNotNull("${book.lessonId}: '$term' ($lang) not found", hit)
                    val page = pages.first { it.id == hit!!.pageId }
                    assertTrue("${book.lessonId}: hit page ${page.id} does not hold row $i", i in page.rowIndices)
                }
            }
        }
    }

    @Test fun hitsAreInReadingOrderAndReferenceRealPages() {
        BookChapters.all.forEach { book ->
            val pages = pagesOf(book)
            val order = pages.withIndex().associate { it.value.id to it.index }
            val hits = BookChapterSearch.search(book, pages, emptyList(), "the", AppLanguage.ENGLISH, limit = 500)
            assertTrue(book.lessonId, hits.all { it.pageId in order })
            assertEquals(book.lessonId, hits.map { order.getValue(it.pageId) }, hits.map { order.getValue(it.pageId) }.sorted())
        }
    }

    @Test fun searchUsesTheReadingLanguageOnly() {
        val book = BookChapters.all.first()
        val pages = pagesOf(book)
        val englishWord = book.objectives.first().english.split(" ").first { it.length >= 5 }
        assertTrue(BookChapterSearch.search(book, pages, emptyList(), englishWord, AppLanguage.ENGLISH).isNotEmpty())
        assertTrue("English word must not match Tamil text",
            BookChapterSearch.search(book, pages, emptyList(), englishWord, AppLanguage.TAMIL).none { it.snippet.contains(englishWord, true) && it.kind == SearchKind.OBJECTIVE })
    }

    @Test fun matchingIsCaseAndWhitespaceInsensitive() {
        val book = BookChapters.all.first()
        val pages = pagesOf(book)
        val term = book.definitions.first().term.english
        val a = BookChapterSearch.search(book, pages, emptyList(), term.uppercase(), AppLanguage.ENGLISH)
        val b = BookChapterSearch.search(book, pages, emptyList(), "  " + term.lowercase().replace(" ", "   ") + " ", AppLanguage.ENGLISH)
        assertTrue(a.isNotEmpty())
        assertEquals(a, b)
    }
}
