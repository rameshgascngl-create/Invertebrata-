package com.gasczoology.invertebratelab.data

enum class SearchKind { HEADING, DEFINITION, PARAGRAPH, KEY_CONCEPT, NOTE, TABLE, FIGURE, OBJECTIVE, INTRODUCTION, SUMMARY, QUICK_CHECK, QUESTION, FURTHER_READING }

/** A match, addressed by the semantic page id and, for body text, the row within the chapter. */
data class BookSearchHit(
    val pageId: String,
    val kind: SearchKind,
    val snippet: String,
    /** Index into [BookLayout.rows], or -1 when the hit is a whole-page item (summary, question...). */
    val rowIndex: Int = -1,
)

/**
 * In-chapter search over the language the student is reading. Pure and unit-testable.
 * Hits come back in reading order and carry the page id the reader must open.
 */
object BookChapterSearch {
    const val MIN_QUERY = 2

    private fun normalise(s: String) = s.lowercase().replace(Regex("\\s+"), " ").trim()

    private fun snippet(text: String, query: String): String {
        val flat = text.replace(Regex("\\s+"), " ").trim()
        val at = flat.lowercase().indexOf(query)
        if (at < 0) return flat.take(90)
        val from = (at - 35).coerceAtLeast(0)
        val to = (at + query.length + 55).coerceAtMost(flat.length)
        return (if (from > 0) "…" else "") + flat.substring(from, to) + (if (to < flat.length) "…" else "")
    }

    fun search(
        chapter: BookChapter,
        pages: List<BookReadingPage>,
        questions: List<A5Question>,
        rawQuery: String,
        language: AppLanguage,
        limit: Int = 40,
    ): List<BookSearchHit> {
        val query = normalise(rawQuery)
        if (query.length < MIN_QUERY) return emptyList()
        val order = pages.withIndex().associate { it.value.id to it.index }
        val pageOfRow = HashMap<Int, String>()
        pages.forEach { p -> p.rowIndices.forEach { pageOfRow[it] = p.id } }
        val hits = ArrayList<BookSearchHit>()
        fun check(pageId: String?, kind: SearchKind, text: String, row: Int = -1) {
            if (pageId != null && normalise(text).contains(query)) hits += BookSearchHit(pageId, kind, snippet(text, query), row)
        }
        chapter.objectives.forEach { check("objectives", SearchKind.OBJECTIVE, it.value(language)) }
        chapter.introduction.forEach { check("objectives", SearchKind.INTRODUCTION, it.value(language)) }
        BookLayout.rows(chapter).forEachIndexed { i, row ->
            val page = pageOfRow[i]
            when (row) {
                is BookRow.SectionHeading -> check(page, SearchKind.HEADING, row.heading.value(language), i)
                is BookRow.SubHeading -> check(page, SearchKind.HEADING, row.heading.value(language), i)
                is BookRow.Block -> when (val b = row.block) {
                    is BookParagraph -> check(page, SearchKind.PARAGRAPH, b.text.value(language), i)
                    is BookDefinition -> check(page, SearchKind.DEFINITION, b.term.value(language) + " — " + b.meaning.value(language), i)
                    is BookKeyPoints -> check(page, SearchKind.KEY_CONCEPT, (listOf(b.title) + b.points).joinToString(" · ") { it.value(language) }, i)
                    is BookNote -> check(page, SearchKind.NOTE, b.title.value(language) + " — " + b.text.value(language), i)
                    is BookFigure -> check(page, SearchKind.FIGURE, b.caption.value(language), i)
                    is BookTable -> check(page, SearchKind.TABLE,
                        (listOf(b.caption) + b.header + b.rows.flatten()).joinToString(" · ") { it.value(language) }, i)
                }
            }
        }
        chapter.summary.forEach { check("summary", SearchKind.SUMMARY, it.value(language)) }
        chapter.quickChecks.forEach { check("quick-checks", SearchKind.QUICK_CHECK, it.question.value(language) + " — " + it.answer.value(language)) }
        chapter.furtherReading.forEach { check("quick-checks", SearchKind.FURTHER_READING, it.value(language)) }
        questions.forEach { q ->
            check("practice-${q.id}", SearchKind.QUESTION, q.question.value(language) + " — " + q.answerPoints.joinToString(" ") { it.value(language) })
        }
        return hits.filter { it.pageId in order }
            .sortedWith(compareBy({ order.getValue(it.pageId) }, { it.rowIndex }))
            .distinctBy { Triple(it.pageId, it.rowIndex, it.snippet) }
            .take(limit)
    }
}
