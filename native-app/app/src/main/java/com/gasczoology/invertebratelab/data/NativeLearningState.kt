package com.gasczoology.invertebratelab.data

/**
 * Stable, versioned learner-position state; no student names or submitted answers.
 * The selected language is stored by LanguagePreferenceRepository.
 */
enum class StudyDestination { HOME, UNIT, CHAPTER, PRACTICE, PARAMECIUM_LAB }

data class NativeLearningState(
    val schemaVersion: Int = CURRENT_SCHEMA,
    val destination: StudyDestination = StudyDestination.HOME,
    val unitNumber: Int = 1,
    val chapterId: String = "",
    val questionId: String = "",
    val answerRevealed: Boolean = false,
) {
    companion object {
        const val CURRENT_SCHEMA = 1
    }

    /**
     * Validate against the *bundled* authoritative corpus, not assumptions about
     * chapter IDs or question count. Invalid/outdated records fall back safely.
     */
    fun validatedAgainst(units: List<AcademicUnit>): NativeLearningState {
        if (schemaVersion != CURRENT_SCHEMA) return NativeLearningState()
        val unit = units.singleOrNull { it.number == unitNumber } ?: return NativeLearningState()
        val chapters = units.flatMap { it.chapters }
        val chapter = chapters.singleOrNull { it.id == chapterId }
        val question = chapters.flatMap { it.a5Questions }.singleOrNull { it.id == questionId }
        return when (destination) {
            StudyDestination.HOME -> copy(destination = StudyDestination.HOME)
            StudyDestination.UNIT -> copy(
                chapterId = "", questionId = "", answerRevealed = false
            )
            StudyDestination.CHAPTER -> if (chapter?.unitNumber == unit.number &&
                (questionId.isEmpty() || question?.chapterId == chapter.id)
            ) this else NativeLearningState()
            StudyDestination.PARAMECIUM_LAB -> if (
                unit.number == 1 && chapterId == "u1-paramecium" &&
                questionId.isEmpty() && !answerRevealed
            ) this else NativeLearningState()
            StudyDestination.PRACTICE -> if (question != null &&
                question.chapterId == chapterId &&
                chapter?.unitNumber == unit.number
            ) this else NativeLearningState()
        }
    }
}
