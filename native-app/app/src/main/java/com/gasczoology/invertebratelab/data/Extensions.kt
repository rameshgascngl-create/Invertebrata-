package com.gasczoology.invertebratelab.data

/**
 * Facade over the extension units (VI and VII) that are appended after the validated
 * five-unit A5 corpus. Extension chapters sit outside the 43-chapter contract.
 */
object Extensions {
    const val MAX_UNIT = FurtherInvertebratesExtension.UNIT_NUMBER

    val units: List<AcademicUnit> get() = listOf(InsectaExtension.unit, FurtherInvertebratesExtension.unit)
    val bookChapters: List<BookChapter> get() = InsectaExtension.bookChapters + FurtherInvertebratesExtension.bookChapters
    fun isExtensionUnit(unit: Int): Boolean = unit in InsectaExtension.UNIT_NUMBER..MAX_UNIT
    fun isExtension(lessonId: String): Boolean = bookChapters.any { it.lessonId == lessonId }
    fun chapterIdsFor(unit: Int): List<String> = when (unit) {
        InsectaExtension.UNIT_NUMBER -> InsectaExtension.chapterIds
        FurtherInvertebratesExtension.UNIT_NUMBER -> FurtherInvertebratesExtension.chapterIds
        else -> emptyList()
    }
}
