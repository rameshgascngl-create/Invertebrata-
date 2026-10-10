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
    // Additive schema-1 fields: old DataStore records keep their assessment state.
    val textbookSectionId: String = "identity-and-habitat",
    val ciliaryStageIndex: Int = 0,
    val laboratoryTab: String = "study",
    val nuclearProgress: NuclearLearningProgress = NuclearLearningProgress(),
    val waterBalanceProgress: WaterBalanceProgress = WaterBalanceProgress(),
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
        val textbookState = copy(
            nuclearProgress = nuclearProgress.normalized(),
            waterBalanceProgress = waterBalanceProgress.normalized(),
            textbookSectionId = textbookSectionId.takeIf { id ->
                NativeLessonDrafts.paramecium.sections.any { it.id == id }
            } ?: "identity-and-habitat",
            ciliaryStageIndex = ciliaryStageIndex.takeIf { it in 0..3 } ?: 0,
            laboratoryTab = laboratoryTab.takeIf {
                it in setOf("study", "anatomy", "simulate", "listen", "practice", "nuclear", "water-balance")
            } ?: "study",
        )
        return when (destination) {
            StudyDestination.HOME -> copy(destination = StudyDestination.HOME)
            StudyDestination.UNIT -> copy(
                chapterId = "", questionId = "", answerRevealed = false
            )
            StudyDestination.CHAPTER -> if (chapter?.unitNumber == unit.number &&
                (questionId.isEmpty() || question?.chapterId == chapter.id)
            ) textbookState else NativeLearningState()
            StudyDestination.PARAMECIUM_LAB -> if (
                unit.number == 1 && chapterId == "u1-paramecium" &&
                questionId.isEmpty() && !answerRevealed
            ) textbookState else NativeLearningState()
            StudyDestination.PRACTICE -> if (question != null &&
                question.chapterId == chapterId &&
                chapter?.unitNumber == unit.number
            ) this else NativeLearningState()
        }
    }
}
