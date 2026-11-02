package com.gasczoology.invertebratelab.data

/** Logical pages have language-independent IDs; large type can scroll within a page. */
data class BookReadingPage(
    val id: String,
    val title: BilingualText,
    val rowIndices: List<Int> = emptyList(),
    val introductionIndex: Int = -1,
)

object BookReadingPages {
    fun forChapter(chapter: BookChapter, questionIds: List<String>): List<BookReadingPage> = buildList {
        add(BookReadingPage("objectives", BilingualText("Learning objectives", "கற்றல் நோக்கங்கள்")))
        chapter.introduction.indices.forEach { i ->
            add(BookReadingPage("introduction-$i", BilingualText("Introduction", "முன்னுரை"), introductionIndex = i))
        }
        add(BookReadingPage("contents", BilingualText("Contents", "பொருளடக்கம்")))
        val rows = BookLayout.rows(chapter)
        var heading = chapter.title
        var start = 0
        while (start < rows.size) {
            (rows[start] as? BookRow.SectionHeading)?.let { heading = it.heading }
            (rows[start] as? BookRow.SubHeading)?.let { heading = it.heading }
            var end = (start + 2).coerceAtMost(rows.size)
            // Keep each new section/subsection with its first block, never at a page's foot.
            if (end < rows.size && rows[end - 1] !is BookRow.Block) end--
            if (end == start) end = (start + 2).coerceAtMost(rows.size)
            add(BookReadingPage("row-$start", heading, (start until end).toList()))
            start = end
        }
        add(BookReadingPage("summary", BilingualText("Chapter summary", "அத்தியாயச் சுருக்கம்")))
        add(BookReadingPage("glossary", BilingualText("Key terms", "கலைச்சொற்கள்")))
        add(BookReadingPage("quick-checks", BilingualText("Check your understanding", "புரிதலைச் சரிபார்க்க")))
        add(BookReadingPage("references", BilingualText("Further reading", "மேலும் படிக்க")))
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
