package com.gasczoology.invertebratelab.data

/**
 * Unit VII: supplementary topics from the TANSCHE Invertebrata syllabus that have no
 * chapter of their own in Units I-V (minor annelid groups, leech, minor mollusc classes,
 * fossil echinoderm classes and a few single topics). An EXTENSION, appended after the
 * validated corpus; chapters carry draft questions from FurtherQuestions.
 */
object FurtherInvertebratesExtension {
    const val UNIT_NUMBER = 7

    val bookChapters: List<BookChapter> = listOf(
        MinorAnnelidsBookChapter.chapter,
        LeechBookChapter.chapter,
        AnnelidExcretionReproductionBookChapter.chapter,
        MinorMolluscsBookChapter.chapter,
        EchinodermClassesBookChapter.chapter,
        AureliaMesenteriesBookChapter.chapter,
    )

    val chapterIds: List<String> get() = bookChapters.map { it.lessonId }

    val unit: AcademicUnit
        get() = AcademicUnit(
            number = UNIT_NUMBER,
            title = BilingualText("Unit VII: Supplementary topics in invertebrate zoology",
                "அலகு VII: முதுகெலும்பிலிகள் விலங்கியலின் கூடுதல் தலைப்புகள்"),
            chapters = bookChapters.map { Chapter(it.lessonId, UNIT_NUMBER, it.title, FurtherQuestions.byChapter[it.lessonId].orEmpty()) },
        )
}
