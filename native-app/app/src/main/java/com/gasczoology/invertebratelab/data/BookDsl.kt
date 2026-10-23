package com.gasczoology.invertebratelab.data

/** Authoring helpers for [BookChapter] content. Pairs are (English, Tamil). */
internal typealias EnTa = Pair<String, String>

internal fun bt(en: String, ta: String) = BilingualText(en, ta)
internal fun bp(en: String, ta: String) = BookParagraph(bt(en, ta))
internal fun bp(pair: EnTa) = BookParagraph(bt(pair.first, pair.second))
internal fun bdef(termEn: String, termTa: String, en: String, ta: String) =
    BookDefinition(bt(termEn, termTa), bt(en, ta))
internal fun bdef(termEn: String, termTa: String, meaning: EnTa) =
    BookDefinition(bt(termEn, termTa), bt(meaning.first, meaning.second))
internal fun bnote(titleEn: String, titleTa: String, en: String, ta: String) =
    BookNote(bt(titleEn, titleTa), bt(en, ta))
internal fun bnote(titleEn: String, titleTa: String, text: EnTa) =
    BookNote(bt(titleEn, titleTa), bt(text.first, text.second))
internal fun bpoints(titleEn: String, titleTa: String, vararg points: EnTa) =
    BookKeyPoints(bt(titleEn, titleTa), points.map { bt(it.first, it.second) })
internal fun bfig(slot: String, en: String, ta: String) = BookFigure(slot, bt(en, ta))
internal fun bfig(slot: String, caption: EnTa) = BookFigure(slot, bt(caption.first, caption.second))
internal fun btable(capEn: String, capTa: String, header: List<EnTa>, rows: List<List<EnTa>>) =
    BookTable(bt(capEn, capTa), header.map { bt(it.first, it.second) },
        rows.map { r -> r.map { bt(it.first, it.second) } })
internal fun bsub(en: String, ta: String, vararg blocks: BookBlock) =
    BookSubsection(bt(en, ta), blocks.toList())
internal fun bsec(
    id: String, en: String, ta: String,
    blocks: List<BookBlock> = emptyList(), subs: List<BookSubsection> = emptyList(),
) = BookSection(id, bt(en, ta), blocks, subs)
internal fun bqc(qEn: String, qTa: String, aEn: String, aTa: String) =
    BookQuickCheck(bt(qEn, qTa), bt(aEn, aTa))

/** Standard textbook references. Edition details and page numbers are for the reviewer to add. */
internal object BookRefs {
    val ruppert: EnTa = "Ruppert, E. E., Fox, R. S. and Barnes, R. D. Invertebrate Zoology: A Functional Evolutionary Approach, 7th edition, Brooks/Cole: the chapter on this group. Page numbers to be added by the reviewer." to
        "ரூப்பர்ட், ஃபாக்ஸ், பார்ன்ஸ்: Invertebrate Zoology: A Functional Evolutionary Approach, 7ஆம் பதிப்பு, Brooks/Cole: இக்குழுவுக்கான அத்தியாயம். பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."
    val brusca: EnTa = "Brusca, R. C., Moore, W. and Shuster, S. M. Invertebrates, 3rd edition, Sinauer, 2016: the chapter on this group. Page numbers to be added by the reviewer." to
        "புரூஸ்கா, மூர், ஷஸ்டர்: Invertebrates, 3ஆம் பதிப்பு, Sinauer, 2016: இக்குழுவுக்கான அத்தியாயம். பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."
    val hyman: EnTa = "Hyman, L. H. The Invertebrates, McGraw-Hill, 1940 onwards: the volume that covers this group. Volume and page numbers to be added by the reviewer." to
        "ஹைமன்: The Invertebrates, McGraw-Hill, 1940 முதல்: இக்குழுவை உள்ளடக்கிய தொகுதி. தொகுதி, பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."
    val kotpal: EnTa = "Kotpal, R. L. Modern Text Book of Zoology: Invertebrates, Rastogi Publications: the chapter on this group. Edition and page numbers to be added by the reviewer." to
        "கோட்பால்: Modern Text Book of Zoology: Invertebrates, Rastogi Publications: இக்குழுவுக்கான அத்தியாயம். பதிப்பு, பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."
    val jordan: EnTa = "Jordan, E. L. and Verma, P. S. Invertebrate Zoology, S. Chand: the chapter on this group. Edition and page numbers to be added by the reviewer." to
        "ஜோர்டான், வர்மா: Invertebrate Zoology, S. Chand: இக்குழுவுக்கான அத்தியாயம். பதிப்பு, பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."
    val standard: List<EnTa> get() = listOf(ruppert, brusca, kotpal)
}

/** Builds a chapter whose review questions are the two validated A5 questions of the same id. */
internal fun bchapter(
    lessonId: String,
    title: EnTa,
    objectives: List<EnTa>,
    introduction: List<EnTa>,
    sections: List<BookSection>,
    summary: List<EnTa>,
    checks: List<BookQuickCheck>,
    reading: List<EnTa> = BookRefs.standard,
) = BookChapter(
    lessonId = lessonId,
    title = bt(title.first, title.second),
    objectives = objectives.map { bt(it.first, it.second) },
    introduction = introduction.map { bt(it.first, it.second) },
    sections = sections,
    summary = summary.map { bt(it.first, it.second) },
    quickChecks = checks,
    reviewQuestionIds = listOf("$lessonId-A5-1", "$lessonId-A5-2"),
    furtherReading = reading.map { bt(it.first, it.second) },
)
