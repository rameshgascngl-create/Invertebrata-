package com.gasczoology.invertebratelab.data

/** Registry of chapters authored in the printed-book format. Others still use the flat lessons. */
object BookChapters {
    val all: List<BookChapter> = listOf(
        PoriferaBookChapter.chapter,
        PoriferaCanalsBookChapter.chapter,
        PoriferaReproductionBookChapter.chapter,
        CnidariaBookChapter.chapter,
        ObeliaBookChapter.chapter,
        CoralsBookChapter.chapter,
        CoralEconomicsBookChapter.chapter,
        PolymorphismBookChapter.chapter,
        PlatyhelminthesBookChapter.chapter,
        FasciolaBookChapter.chapter,
        PlatyAdaptationsBookChapter.chapter,
        AschelminthesBookChapter.chapter,
        AscarisBookChapter.chapter,
        NematodeParasitesBookChapter.chapter,
        SyconBookChapter.chapter,
    )
    val byLesson: Map<String, BookChapter> = all.associateBy { it.lessonId }
    fun forLesson(lessonId: String): BookChapter? = byLesson[lessonId]
}
