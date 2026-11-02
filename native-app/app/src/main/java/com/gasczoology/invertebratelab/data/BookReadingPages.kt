package com.gasczoology.invertebratelab.data

/** Logical pages have language-independent IDs; large type can scroll within a page. */
data class BookReadingPage(
    val id: String,
    val title: BilingualText,
    val rowIndices: List<Int> = emptyList(),
    val introductionIndex: Int = -1,
)

object BookReadingPages {
    /**
     * Language-independent page budget, in "English characters of body text".
     * One phone screen holds roughly 1,000-1,200 of them next to the compact page header; a figure
     * counts as about 650. Pages are packed to this budget so short blocks (a definition, a one-line
     * paragraph) share a page instead of leaving most of it blank. Larger type simply scrolls.
     */
    const val PAGE_BUDGET = 1150

    /** Approximate on-screen weight of one row (see [PAGE_BUDGET]). */
    fun weight(row: BookRow): Int = when (row) {
        is BookRow.SectionHeading -> 150          // heading plus its "Listen" button
        is BookRow.SubHeading -> 70
        is BookRow.Block -> when (val b = row.block) {
            is BookParagraph -> b.text.english.length
            is BookDefinition -> 150 + b.term.english.length + b.meaning.english.length
            is BookKeyPoints -> 130 + b.points.sumOf { it.english.length + 24 }
            is BookNote -> 120 + b.title.english.length + b.text.english.length
            is BookFigure -> 650 + b.caption.english.length
            is BookTable -> 170 + b.rows.sumOf { r -> 80 + r.sumOf { c -> c.english.length / 2 } }
        }
    }

    fun forChapter(chapter: BookChapter, questionIds: List<String>): List<BookReadingPage> = buildList {
        // Objectives and the (short) introduction share the opening page.
        add(BookReadingPage("objectives",
            BilingualText("Objectives and introduction", "நோக்கங்களும் முன்னுரையும்"),
            introductionIndex = if (chapter.introduction.isEmpty()) -1 else 0))
        add(BookReadingPage("contents", BilingualText("Contents", "பொருளடக்கம்")))
        val rows = BookLayout.rows(chapter)
        // A unit is the headings that precede a block plus that block; units are never split.
        val units = ArrayList<IntRange>()
        var at = 0
        while (at < rows.size) {
            var last = at
            while (last < rows.size && rows[last] !is BookRow.Block) last++
            if (last >= rows.size) last = rows.lastIndex
            units += at..last
            at = last + 1
        }
        var heading = chapter.title
        var u = 0
        while (u < units.size) {
            val first = units[u].first
            (rows[first] as? BookRow.SectionHeading)?.let { heading = it.heading }
            (rows[first] as? BookRow.SubHeading)?.let { heading = it.heading }
            var used = 0
            var end = u
            while (end < units.size) {
                val w = units[end].sumOf { weight(rows[it]) }
                if (end > u && used + w > PAGE_BUDGET) break
                used += w
                end++
            }
            add(BookReadingPage("row-$first", heading, (first..units[end - 1].last).toList()))
            u = end
        }
        // Summary and key terms share a page; so do the quick checks and further reading.
        add(BookReadingPage("summary", BilingualText("Summary and key terms", "சுருக்கமும் கலைச்சொற்களும்")))
        add(BookReadingPage("quick-checks", BilingualText("Check your understanding and further reading", "புரிதல் சோதனையும் மேலும் படிக்கவும்")))
        when (chapter.lessonId) {
            "u1-paramecium" -> add(BookReadingPage("interactive", BilingualText("Paramecium laboratory", "பாரமீசியம் ஆய்வகம்")))
            "u1-proto-parasites" -> {
                add(BookReadingPage("trypanosoma", BilingualText("Trypanosoma life cycle", "டிரிப்பனோசோமா வாழ்க்கைச் சுழற்சி")))
                add(BookReadingPage("leishmania", BilingualText("Leishmania life cycle", "லீஷ்மேனியா வாழ்க்கைச் சுழற்சி")))
            }
            "u4-penaeus" -> {
                add(BookReadingPage("appendages", BilingualText("Appendage explorer", "இணையுறுப்பு ஆய்வு")))
                add(BookReadingPage("larvae", BilingualText("Larval sequence", "லார்வா நிலை வரிசை")))
            }
            "u2-sycon", "u2-canals", "u2-obelia", "u2-polymorphism", "u3-fasciola", "u3-ascaris" ->
                add(BookReadingPage("interactive", BilingualText("Study the stage sequence", "நிலை வரிசையை ஆராய்க")))
        }
        questionIds.forEachIndexed { i, id ->
            add(BookReadingPage("practice-$id", BilingualText("Exam practice · ${i + 1}", "தேர்வுப் பயிற்சி · ${i + 1}")))
        }
    }
}

data class BookReadingPosition(val pageId: String = "objectives", val itemIndex: Int = 0, val itemOffset: Int = 0)
