package com.gasczoology.invertebratelab

import android.util.Log
import android.content.pm.ActivityInfo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.StudyDestination
import com.gasczoology.invertebratelab.data.ValidatedA5AssetRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Two separate instrumentation entrypoints. The shell driver must terminate the
 * original *app process* between them and prove the original PID disappeared.
 * Running these tests consecutively without the external PID gate is NOT N1.4.
 */
@RunWith(AndroidJUnit4::class)
class NativeProcessDeathSetupTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun prepareActualUiAtQuestionFiveAndVerifyDiskCommit() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val learning = NativeLearningRepository(context)
        val language = LanguagePreferenceRepository(context)
        val allQuestions = ValidatedA5AssetRepository(context).load()
            .flatMap { it.chapters }.flatMap { it.a5Questions }
        val fifth = allQuestions[4]

        // Deterministic fixture: reset through the same production DataStore API,
        // then use the actual native UI to navigate and reveal the fifth answer.
        runBlocking {
            learning.save(NativeLearningState())
            language.setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 15_000L) {
            rule.onAllNodesWithText("அலகு 1 · 7 அத்தியாயங்கள்").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-home").performScrollToIndex(6)
        rule.onNodeWithTag("a5-assessment").performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-assessment").fetchSemanticsNodes().isNotEmpty()
        }
        for (next in 2..5) {
            rule.onNodeWithTag("assessment-next").performScrollTo().performClick()
            rule.waitUntil(timeoutMillis = 15_000L) {
                rule.onAllNodesWithText("$next / 86").fetchSemanticsNodes().isNotEmpty()
            }
        }
        rule.onNodeWithTag("a5-question-" + fifth.id)
            .assertTextEquals(fifth.question.tamil)
        rule.onNodeWithTag("a5-toggle-" + fifth.id).performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 15_000L) {
            rule.onAllNodesWithTag("a5-point-" + fifth.id + "-1")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("a5-point-" + fifth.id + "-1")
            .assertTextEquals("1. " + fifth.answerPoints[0].tamil)

        runBlocking {
            withTimeout(15_000L) {
                val persisted = learning.learningState.first {
                    it.destination == StudyDestination.PRACTICE &&
                    it.questionId == fifth.id && it.answerRevealed
                }
                assertEquals(1, persisted.schemaVersion)
                assertEquals(fifth.chapterId, persisted.chapterId)
                assertTrue(language.language.first() == AppLanguage.TAMIL)
                Log.i("N14_PROGRESS", "persisted schema=1 destination=PRACTICE " +
                    "question=${persisted.questionId} revealed=true language=TAMIL")
            }
        }
    }
}

@RunWith(AndroidJUnit4::class)
class NativeProcessDeathVerifyTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun verifyRestoredQuestionFiveTamilAndRevealedAnswer() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val fifth = ValidatedA5AssetRepository(context).load()
            .flatMap { it.chapters }.flatMap { it.a5Questions }[4]
        // No fixture mutation here: this must read the previous process's disk.
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-assessment").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("a5-question-" + fifth.id).assertTextEquals(fifth.question.tamil)
        rule.waitUntil(timeoutMillis = 15_000L) {
            rule.onAllNodesWithTag("a5-point-" + fifth.id + "-1")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("a5-point-" + fifth.id + "-1")
            .assertTextEquals("1. " + fifth.answerPoints[0].tamil)
        try {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            rule.waitUntil(timeoutMillis = 20_000L) {
                rule.onAllNodesWithTag("native-assessment").fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithTag("a5-question-" + fifth.id).assertTextEquals(fifth.question.tamil)
            rule.onNodeWithTag("a5-point-" + fifth.id + "-1")
                .assertTextEquals("1. " + fifth.answerPoints[0].tamil)
        } finally {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        runBlocking {
            withTimeout(15_000L) {
                val state = NativeLearningRepository(context).learningState.first()
                assertEquals(StudyDestination.PRACTICE, state.destination)
                assertEquals(fifth.id, state.questionId)
                assertTrue(state.answerRevealed)
                assertEquals(AppLanguage.TAMIL, LanguagePreferenceRepository(context).language.first())
                Log.i("N14_PROGRESS", "recovered schema=1 destination=PRACTICE " +
                    "question=${state.questionId} revealed=true language=TAMIL")
            }
        }
    }
}
