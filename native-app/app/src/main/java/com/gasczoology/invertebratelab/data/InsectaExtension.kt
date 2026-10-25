package com.gasczoology.invertebratelab.data

/**
 * Unit VI (Insecta and applied entomology) is an EXTENSION of the app. It is added
 * after the validated A5 corpus has been checked, so the 43-chapter, 86-question
 * contract of Units I-V is untouched. Extension chapters carry their own draft five-mark questions
 * (InsectaQuestions), which are not part of the validated 86.
 */
object InsectaExtension {
    const val UNIT_NUMBER = 6

    val bookChapters: List<BookChapter> = listOf(
        InsectaGeneralBookChapter.chapter,
        CockroachBookChapter.chapter,
        PollinatorsBookChapter.chapter,
        NaturalEnemiesBookChapter.chapter,
        MedicalInsectsBookChapter.chapter,
        HouseholdInsectsBookChapter.chapter,
        PestsIpmBookChapter.chapter,
        CerealPestsBookChapter.chapter,
        CropPestsBookChapter.chapter,
        ChelicerataBookChapter.chapter,
        MyriapodaBookChapter.chapter,
        MinorArthropodsBookChapter.chapter,
    )

    val chapterIds: List<String> get() = bookChapters.map { it.lessonId }

    fun isExtension(lessonId: String): Boolean = lessonId in chapterIds

    val unit: AcademicUnit
        get() = AcademicUnit(
            number = UNIT_NUMBER,
            title = BilingualText("Unit VI: Further Arthropoda, Insecta and applied entomology",
                "அலகு VI: மேலும் கணுக்காலிகள், பூச்சிகள், பயன்பாட்டுப் பூச்சியியல்"),
            chapters = bookChapters.map { Chapter(it.lessonId, UNIT_NUMBER, it.title, InsectaQuestions.byChapter[it.lessonId].orEmpty()) },
        )
}
