package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.ValidatedA5AssetRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking

/**
 * N1.3 device-level acceptance, executed on the installed native Android app.
 * Expected text is parsed afresh from the bundled authoritative source assets.
 * No coordinate tapping, OCR, browser, HTML or network dependency is used.
 */
@RunWith(AndroidJUnit4::class)
class NativeUiAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    // Isolation only: persistence intentionally survives individual instrumentation
    // tests, so each existing N1.3 assertion needs a clean navigation fixture.
    @Before fun resetLearningPosition() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    private val units: List<AcademicUnit> by lazy {
        ValidatedA5AssetRepository(InstrumentationRegistry.getInstrumentation().targetContext).load()
    }

    private fun waitFor(tag: String) {
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun englishHome() {
        waitFor("native-home")
        rule.onNodeWithTag("language-english").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithText("Unit 1 · 7 chapters").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun allFiveUnitsAndAll43ChaptersDisplayTheExact86EnglishQuestions() {
        englishHome()
        assertEquals(5, units.size)
        assertEquals(43, units.sumOf { it.chapters.size })
        for (unit in units) {
            rule.onNodeWithTag("native-home").performScrollToIndex(unit.number)
            rule.onNodeWithTag("unit-" + unit.number).performClick()
            waitFor("native-unit-" + unit.number)
            for ((chapterIndex, chapter) in unit.chapters.withIndex()) {
                rule.onNodeWithTag("native-unit-" + unit.number)
                    .performScrollToIndex(chapterIndex + 1)
                rule.onNodeWithTag("chapter-" + chapter.id).performClick()
                waitFor("native-chapter-" + chapter.id)
                assertEquals(2, chapter.a5Questions.size)
                for ((questionIndex, question) in chapter.a5Questions.withIndex()) {
                    rule.onNodeWithTag("native-chapter-" + chapter.id)
                        .performScrollToIndex(questionIndex + 1)
                    rule.onNodeWithTag("a5-question-" + question.id)
                        .assertTextEquals(question.question.english)
                }
                rule.onNodeWithTag("native-chapter-" + chapter.id).performScrollToIndex(0)
                rule.onNodeWithText("Back").performClick()
                waitFor("native-unit-" + unit.number)
            }
            rule.onNodeWithTag("native-unit-" + unit.number).performScrollToIndex(0)
            rule.onNodeWithText("Back").performClick()
            waitFor("native-home")
        }
    }

    @Test
    fun practiceTraversesAll86QuestionsAndRevealsAll430ExactEnglishAnswerPoints() {
        englishHome()
        val allQuestions = units.flatMap { it.chapters }.flatMap { it.a5Questions }
        assertEquals(86, allQuestions.size)
        rule.onNodeWithTag("native-home").performScrollToIndex(6)
        rule.onNodeWithTag("a5-assessment").performClick()
        waitFor("native-assessment")

        for ((index, question) in allQuestions.withIndex()) {
            rule.onNodeWithTag("a5-question-" + question.id)
                .assertTextEquals(question.question.english)
            rule.onNodeWithTag("a5-toggle-" + question.id).performScrollTo().performClick()
            assertEquals(5, question.answerPoints.size)
            for ((pointIndex, point) in question.answerPoints.withIndex()) {
                rule.onNodeWithTag("a5-point-" + question.id + "-" + (pointIndex + 1))
                    .assertTextEquals((pointIndex + 1).toString() + ". " + point.english)
            }
            if (index < allQuestions.lastIndex) {
                rule.onNodeWithTag("assessment-next").performScrollTo().performClick()
            }
        }
        rule.onNodeWithTag("assessment-next").assertIsNotEnabled()
    }

    @Test
    fun tamilAnswerAndQuestionPositionSurviveRotation() {
        waitFor("native-home")
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithText("அலகு 1 · 7 அத்தியாயங்கள்").fetchSemanticsNodes().isNotEmpty()
        }

        val questions = units.flatMap { it.chapters }.flatMap { it.a5Questions }
        rule.onNodeWithTag("native-home").performScrollToIndex(6)
        rule.onNodeWithTag("a5-assessment").performClick()
        waitFor("native-assessment")
        repeat(4) {
            rule.onNodeWithTag("assessment-next").performScrollTo().performClick()
        }
        val fifth = questions[4]
        rule.onNodeWithTag("a5-question-" + fifth.id).assertTextEquals(fifth.question.tamil)
        rule.onNodeWithTag("a5-toggle-" + fifth.id).performScrollTo().performClick()
        rule.onNodeWithTag("a5-point-" + fifth.id + "-1")
            .assertTextEquals("1. " + fifth.answerPoints[0].tamil)

        try {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            waitFor("native-assessment")
            rule.onNodeWithTag("a5-question-" + fifth.id).assertTextEquals(fifth.question.tamil)
            rule.onNodeWithTag("a5-point-" + fifth.id + "-1")
                .assertTextEquals("1. " + fifth.answerPoints[0].tamil)
            rule.onNodeWithText("5 / 86").assertExists()
        } finally {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }

    @Test
    fun primaryActionSemanticsAndTouchTargetsArePresent() {
        englishHome()
        for (tag in listOf("language-english", "language-tamil", "unit-1", "a5-assessment")) {
            if (tag == "a5-assessment") rule.onNodeWithTag("native-home").performScrollToIndex(6)
            rule.onNodeWithTag(tag)
                .assertHasClickAction()
                .assertWidthIsAtLeast(48.dp)
                .assertHeightIsAtLeast(48.dp)
        }
    }
}

/** Execute this class separately on API 34 with system font_scale=2.0. */
@RunWith(AndroidJUnit4::class)
class NativeTamilLargeTextAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun resetLearningPosition() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    @Test
    fun tamilNavigationAndFullAnswerRemainReachableAtTwoHundredPercent() {
        val units = ValidatedA5AssetRepository(
            InstrumentationRegistry.getInstrumentation().targetContext
        ).load()
        val first = units.first().chapters.first().a5Questions.first()
        val fontScale = InstrumentationRegistry.getInstrumentation().targetContext
            .resources.configuration.fontScale
        assertTrue("Expected actual 200% font scale, found " + fontScale,
            fontScale >= 1.95f)

        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithText("அலகு 1 · 7 அத்தியாயங்கள்").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(1)
        rule.onNodeWithTag("chapter-" + units.first().chapters.first().id).performClick()
        rule.onNodeWithTag("a5-question-" + first.id).assertTextEquals(first.question.tamil)
        rule.onNodeWithTag("a5-toggle-" + first.id).performScrollTo().performClick()
        for ((index, point) in first.answerPoints.withIndex()) {
            rule.onNodeWithTag("a5-point-" + first.id + "-" + (index + 1))
                .assertTextEquals((index + 1).toString() + ". " + point.tamil)
        }
    }
}
