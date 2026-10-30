package com.gasczoology.invertebratelab.data

/**
 * Printed-textbook chapter model (pilot). It is ADDITIVE: the flat
 * [NativeBilingualLesson] / [LessonSection] contract and the accepted A5 JSON
 * are untouched. A chapter written in this form is rendered like a book page:
 * numbered sections, definition boxes, numbered figures and tables, summary,
 * glossary, check questions and further reading.
 *
 * Numbers are never typed into the text. They are derived from position by
 * [BookLayout], so inserting a section cannot leave a stale "Fig. 2.3".
 */
sealed interface BookBlock
data class BookParagraph(val text: BilingualText) : BookBlock
data class BookDefinition(val term: BilingualText, val meaning: BilingualText) : BookBlock
data class BookKeyPoints(val title: BilingualText, val points: List<BilingualText>) : BookBlock
data class BookNote(val title: BilingualText, val text: BilingualText) : BookBlock
/** [slot] is a packaged plate id such as "sycon/canal-system". */
data class BookFigure(val slot: String, val caption: BilingualText) : BookBlock
data class BookTable(
    val caption: BilingualText,
    val header: List<BilingualText>,
    val rows: List<List<BilingualText>>,
) : BookBlock

data class BookSubsection(val heading: BilingualText, val blocks: List<BookBlock>)
data class BookSection(
    val id: String,
    val heading: BilingualText,
    val blocks: List<BookBlock>,
    val subsections: List<BookSubsection> = emptyList(),
)
data class BookQuickCheck(val question: BilingualText, val answer: BilingualText)

data class BookChapter(
    val lessonId: String,
    val title: BilingualText,
    val objectives: List<BilingualText>,
    val introduction: List<BilingualText>,
    val sections: List<BookSection>,
    val summary: List<BilingualText>,
    val quickChecks: List<BookQuickCheck>,
    /** Ids of validated A5 five-mark questions that this chapter prepares for. */
    val reviewQuestionIds: List<String>,
    val furtherReading: List<BilingualText>,
    val status: AcademicWorkStatus = AcademicWorkStatus.DRAFT_UNVERIFIED,
) {
    /** Glossary entries are exactly the definition boxes of the chapter. */
    val definitions: List<BookDefinition>
        get() = allBlocks().filterIsInstance<BookDefinition>()

    fun allBlocks(): List<BookBlock> =
        sections.flatMap { s -> s.blocks + s.subsections.flatMap { it.blocks } }
}

/** One renderable line of a chapter, in reading order. Pure and unit-testable. */
sealed interface BookRow {
    data class SectionHeading(
        val number: String, val heading: BilingualText, val listenText: BilingualText,
    ) : BookRow
    data class SubHeading(val number: String, val heading: BilingualText) : BookRow
    /** [label] is "Fig. 2.1" / "Table 2.1" for figures and tables, else null. */
    data class Block(val block: BookBlock, val label: String?) : BookRow
}

object BookLayout {
    /** "u2-sycon" -> unit 2, chapter 2 (its position in the A5 sequence of the unit). */
    fun unitNumber(lessonId: String): Int =
        lessonId.removePrefix("u").substringBefore('-').toInt()

    fun chapterNumber(lessonId: String): Int {
        val unit = unitNumber(lessonId)
        val ids = NativeCurriculumManifest.a5ChapterIdsByUnit[unit]
            ?: Extensions.chapterIdsFor(unit)
        val at = ids.indexOf(lessonId)
        require(at >= 0) { "Unknown chapter: $lessonId" }
        return at + 1
    }

    fun rows(chapter: BookChapter): List<BookRow> {
        val n = chapterNumber(chapter.lessonId)
        var figure = 0
        var table = 0
        val out = ArrayList<BookRow>()
        fun addBlock(b: BookBlock) {
            val label = when (b) {
                is BookFigure -> "Fig. $n.${++figure}"
                is BookTable -> "Table $n.${++table}"
                else -> null
            }
            out += BookRow.Block(b, label)
        }
        chapter.sections.forEachIndexed { i, s ->
            val number = "$n.${i + 1}"
            val spoken = (s.blocks + s.subsections.flatMap { it.blocks })
                .filterIsInstance<BookParagraph>().map { it.text }
            out += BookRow.SectionHeading(number, s.heading,
                BilingualText(spoken.joinToString(" ") { it.english },
                    spoken.joinToString(" ") { it.tamil }))
            s.blocks.forEach(::addBlock)
            s.subsections.forEachIndexed { j, sub ->
                out += BookRow.SubHeading("$number.${j + 1}", sub.heading)
                sub.blocks.forEach(::addBlock)
            }
        }
        return out
    }

    fun figureLabels(chapter: BookChapter): Map<String, String> =
        rows(chapter).filterIsInstance<BookRow.Block>()
            .filter { it.block is BookFigure }
            .associate { (it.block as BookFigure).slot to it.label!! }
}

/**
 * Editorial floor for a book-style chapter. It checks structure, completeness
 * and bilingual parity. It cannot check that the science is right.
 */
object BookChapterContract {
    const val MIN_ENGLISH_WORDS = 2000
    private val id = Regex("[a-z0-9]+(-[a-z0-9]+)*")
    private val tamilScript = Regex("[\\u0B80-\\u0BFF]")
    private val letters = Regex("[A-Za-z]{3,}")

    fun validate(
        chapter: BookChapter,
        validFigureSlots: Set<String>,
        validReviewIds: Set<String>,
        minEnglishWords: Int = MIN_ENGLISH_WORDS,
    ) {
        val where = chapter.lessonId
        BookLayout.chapterNumber(chapter.lessonId) // throws for unknown lessons
        require(chapter.status != AcademicWorkStatus.NOT_AUTHORED) { "$where: not authored" }
        require(chapter.objectives.size in 3..6) { "$where: 3-6 learning objectives required" }
        require(chapter.introduction.isNotEmpty()) { "$where: introduction required" }
        require(chapter.sections.size >= 5) { "$where: at least 5 numbered sections required" }
        require(chapter.summary.size in 5..8) { "$where: 5-8 summary points required" }
        require(chapter.quickChecks.size >= 3) { "$where: at least 3 check questions required" }
        require(chapter.furtherReading.size >= 2) { "$where: at least 2 further-reading entries" }
        require(chapter.reviewQuestionIds.isNotEmpty() || Extensions.isExtension(chapter.lessonId)) {
            "$where: link at least one A5 question"
        }
        require(validReviewIds.containsAll(chapter.reviewQuestionIds)) {
            "$where: unknown review question ${chapter.reviewQuestionIds - validReviewIds}"
        }
        require(chapter.sections.map { it.id }.toSet().size == chapter.sections.size) {
            "$where: duplicate section ids"
        }
        chapter.sections.forEach {
            require(it.id.matches(id)) { "$where: bad section id ${it.id}" }
            require(it.blocks.isNotEmpty() || it.subsections.isNotEmpty()) {
                "$where: empty section ${it.id}"
            }
            it.subsections.forEach { sub -> require(sub.blocks.isNotEmpty()) {
                "$where: empty sub-section in ${it.id}"
            } }
        }
        val defs = chapter.definitions
        require(defs.size >= 8) { "$where: at least 8 defined terms required" }
        val terms = defs.map { it.term.english.lowercase() }
        require(terms.toSet().size == terms.size) { "$where: a term is defined twice" }

        val figures = chapter.allBlocks().filterIsInstance<BookFigure>()
        require(figures.map { it.slot }.toSet().size == figures.size) {
            "$where: a figure is placed twice"
        }
        figures.forEach {
            require(it.slot in validFigureSlots) { "$where: unknown figure slot ${it.slot}" }
        }
        chapter.allBlocks().filterIsInstance<BookTable>().forEach { t ->
            require(t.header.size >= 2 && t.rows.isNotEmpty()) { "$where: empty table" }
            require(t.rows.all { it.size == t.header.size }) {
                "$where: ragged table '${t.caption.english}'"
            }
        }

        val texts = allTexts(chapter)
        texts.forEach(::complete)
        val words = texts.sumOf { it.english.trim().split(Regex("\\s+")).size }
        require(words >= minEnglishWords) {
            "$where: only $words English words; book chapters need at least $minEnglishWords"
        }
    }

    private fun complete(t: BilingualText) {
        require(t.english.isNotBlank() && t.tamil.isNotBlank()) { "Both languages required: ${t.english}" }
        require(t.english.trim() == t.english && t.tamil.trim() == t.tamil) {
            "Untrimmed text: ${t.english.take(40)}"
        }
        if (letters.containsMatchIn(t.english)) {
            require(tamilScript.containsMatchIn(t.tamil)) {
                "Tamil text has no Tamil script (untranslated?): ${t.english.take(40)}"
            }
        }
    }

    fun allTexts(c: BookChapter): List<BilingualText> {
        val out = ArrayList<BilingualText>()
        out += c.title
        out += c.objectives
        out += c.introduction
        c.sections.forEach { s ->
            out += s.heading
            s.subsections.forEach { out += it.heading }
        }
        c.allBlocks().forEach { b ->
            when (b) {
                is BookParagraph -> out += b.text
                is BookDefinition -> { out += b.term; out += b.meaning }
                is BookKeyPoints -> { out += b.title; out += b.points }
                is BookNote -> { out += b.title; out += b.text }
                is BookFigure -> out += b.caption
                is BookTable -> { out += b.caption; out += b.header; b.rows.forEach { out += it } }
            }
        }
        out += c.summary
        c.quickChecks.forEach { out += it.question; out += it.answer }
        out += c.furtherReading
        return out
    }
}
