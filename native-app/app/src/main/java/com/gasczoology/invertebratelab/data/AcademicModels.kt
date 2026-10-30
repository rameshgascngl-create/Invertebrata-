package com.gasczoology.invertebratelab.data

enum class AppLanguage { ENGLISH, TAMIL }

data class BilingualText(
    val english: String,
    val tamil: String,
) {
    fun value(language: AppLanguage): String = when (language) {
        AppLanguage.ENGLISH -> english
        AppLanguage.TAMIL -> tamil
    }
}

data class A5Question(
    val id: String,
    val chapterId: String,
    val question: BilingualText,
    val answerPoints: List<BilingualText>,
) {
    init {
        require(answerPoints.size == 5) { "A5 answer must contain exactly five validated points: $id" }
    }
}

data class Chapter(
    val id: String,
    val unitNumber: Int,
    val title: BilingualText,
    val a5Questions: List<A5Question>,
)

data class AcademicUnit(
    val number: Int,
    val title: BilingualText,
    val chapters: List<Chapter>,
)
