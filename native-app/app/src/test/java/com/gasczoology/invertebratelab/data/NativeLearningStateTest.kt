package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Test

class NativeLearningStateTest {
    private val question = A5Question(
        "u1-paramecium-A5-1", "u1-paramecium",
        BilingualText("Question", "வினா"),
        List(5) { BilingualText("answer-$it", "விடை-$it") },
    )
    private val units = listOf(
        AcademicUnit(
            1, BilingualText("Unit 1", "அலகு 1"),
            listOf(
                Chapter("u1-paramecium", 1,
                    BilingualText("Paramecium", "பாரமீசியம்"),
                    listOf(question)),
            ),
        ),
    )

    @Test fun validNativePracticePositionSurvivesValidation() {
        val state = NativeLearningState(
            destination = StudyDestination.PRACTICE,
            unitNumber = 1, chapterId = "u1-paramecium",
            questionId = question.id, answerRevealed = true,
        )
        assertEquals(state, state.validatedAgainst(units))
    }

    @Test fun outdatedSchemaFallsBackSafely() {
        val state = NativeLearningState(schemaVersion = 99,
            destination = StudyDestination.PRACTICE, questionId = question.id)
        assertEquals(NativeLearningState(), state.validatedAgainst(units))
    }

    @Test fun mismatchedChapterAndQuestionCannotRestore() {
        val state = NativeLearningState(destination = StudyDestination.PRACTICE,
            unitNumber = 1, chapterId = "u1-intro",
            questionId = question.id, answerRevealed = true)
        assertEquals(NativeLearningState(), state.validatedAgainst(units))
    }

    @Test fun unknownQuestionDoesNotBypassValidation() {
        val state = NativeLearningState(destination = StudyDestination.PRACTICE,
            unitNumber = 1, chapterId = "u1-paramecium",
            questionId = "u1-paramecium-A5-999", answerRevealed = true)
        assertEquals(NativeLearningState(), state.validatedAgainst(units))
    }
}
