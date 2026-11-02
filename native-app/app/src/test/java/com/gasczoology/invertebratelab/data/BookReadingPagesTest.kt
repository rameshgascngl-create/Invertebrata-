package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class BookReadingPagesTest {
    @Test fun everyAuthoredRowAndQuestionAppearsExactlyOnceInOrder() {
        BookChapters.all.forEach { book ->
            val pages = BookReadingPages.forChapter(book, book.reviewQuestionIds)
            assertEquals(book.lessonId, BookLayout.rows(book).indices.toList(), pages.flatMap { it.rowIndices })
            assertEquals(book.lessonId, pages.size, pages.map { it.id }.toSet().size)
            assertEquals(book.reviewQuestionIds.map { "practice-$it" }, pages.filter { it.id.startsWith("practice-") }.map { it.id })
            assertEquals(book.lessonId, if (book.introduction.isEmpty()) emptyList() else listOf(0), pages.filter { it.introductionIndex >= 0 }.map { it.introductionIndex })
            assertTrue(book.lessonId, pages.size > book.sections.size)
            assertTrue(pages.all { it.title.english.isNotBlank() && it.title.tamil.isNotBlank() })
        }
    }

    @Test fun sectionHeadingsNeverTrailThePreviousPage() {
        BookChapters.all.forEach { book ->
            val rows = BookLayout.rows(book)
            BookReadingPages.forChapter(book, book.reviewQuestionIds).filter { it.rowIndices.isNotEmpty() }.forEach { page ->
                assertTrue("${book.lessonId}/${page.id} ends in a heading", rows[page.rowIndices.last()] is BookRow.Block)
                val blocks = page.rowIndices.count { rows[it] is BookRow.Block }
                val weight = page.rowIndices.sumOf { BookReadingPages.weight(rows[it]) }
                assertTrue("${book.lessonId}/${page.id} weighs $weight over ${BookReadingPages.PAGE_BUDGET}",
                    blocks == 1 || weight <= BookReadingPages.PAGE_BUDGET)
            }
        }
    }

    /** Pages are packed to a screen's worth; a page that is mostly blank is a defect. */
    @Test fun bodyPagesAreMostlyFilled() {
        val fills = ArrayList<Double>()
        val sparse = ArrayList<String>()
        BookChapters.all.forEach { book ->
            val rows = BookLayout.rows(book)
            val body = BookReadingPages.forChapter(book, book.reviewQuestionIds).filter { it.rowIndices.isNotEmpty() }
            body.dropLast(1).forEach { page ->            // a chapter's last body page may legitimately be short
                val fill = page.rowIndices.sumOf { BookReadingPages.weight(rows[it]) }.toDouble() / BookReadingPages.PAGE_BUDGET
                fills += fill
                if (fill < .45) sparse += "${book.lessonId}/${page.id}=${"%.2f".format(fill)}"
            }
        }
        val average = fills.average()
        println("PAGE_FILL average=${"%.2f".format(average)} pages=${fills.size} sparse=${sparse.size}")
        assertTrue("average page fill $average; sparse pages: ${sparse.take(25)}", average >= .6)
        assertTrue("too many sparse pages (${sparse.size} of ${fills.size}): ${sparse.take(25)}", sparse.size <= fills.size * .25)
    }

    @Test fun formerlyBypassedInteractiveComponentsHaveReadingDestinations() {
        fun ids(id: String) = BookReadingPages.forChapter(BookChapters.forLesson(id)!!, emptyList()).map { it.id }
        assertTrue(ids("u1-proto-parasites").containsAll(listOf("trypanosoma", "leishmania")))
        assertTrue(ids("u4-penaeus").containsAll(listOf("appendages", "larvae")))
        listOf("u1-paramecium", "u2-sycon", "u2-obelia", "u3-fasciola", "u3-ascaris").forEach { assertTrue(ids(it).contains("interactive")) }
    }
    @Test fun fiveUnitChapterOpeningsStayBriefAndBilingual() {
        val books = BookChapters.all.filter { BookLayout.unitNumber(it.lessonId) <= 5 }
        assertEquals(43, books.size)
        // These established openings have separate science-specific contracts.
        // Preserve their two focused paragraphs rather than deleting biological detail.
        val establishedOpenings = setOf("u1-protozoa", "u2-corals", "u5-pila", "u5-asterias")
        books.forEach { book ->
            assertEquals(book.lessonId, 3, book.objectives.size)
            val established = book.lessonId in establishedOpenings
            assertEquals(book.lessonId, if (established) 2 else 1, book.introduction.size)
            book.introduction.forEach { passage ->
                assertTrue(book.lessonId, passage.english.split(Regex("\\s+")).size <= if (established) 85 else 40)
                assertTrue(book.lessonId, passage.tamil.isNotBlank())
                assertTrue(book.lessonId, passage.tamil.length < if (established) 700 else 450)
            }
        }
    }

}
