package com.gasczoology.invertebratelab.data

/**
 * Unit VI (Insecta and applied entomology) is an EXTENSION of the app. It is added
 * after the validated A5 corpus has been checked, so the 43-chapter, 86-question
 * contract of Units I-V is untouched. Extension chapters have no A5 questions.
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
    )

    val chapterIds: List<String> get() = bookChapters.map { it.lessonId }

    fun isExtension(lessonId: String): Boolean = lessonId in chapterIds

    val unit: AcademicUnit
        get() = AcademicUnit(
            number = UNIT_NUMBER,
            title = BilingualText("Unit VI: Insecta and applied entomology",
                "அலகு VI: பூச்சிகளும் பயன்பாட்டுப் பூச்சியியலும்"),
            chapters = bookChapters.map { Chapter(it.lessonId, UNIT_NUMBER, it.title, emptyList()) },
        )
}
