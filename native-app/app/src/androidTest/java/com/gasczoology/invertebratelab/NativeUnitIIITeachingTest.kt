package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIIITeachingTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        runBlocking {
            val context=androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun openChapter(id: String, tamil: Boolean) {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-3").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-3").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-3")
            .performScrollToNode(hasTestTag("chapter-"+id))
        rule.onNodeWithTag("chapter-"+id).performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u3-reader-"+id).fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test fun fasciolaHasDistinctHistoricalLessonFigureAndOrderedSimulation() {
        openChapter("u3-fasciola",false)
        rule.onNodeWithTag("u3-lesson-title").assertExists()
        rule.onNodeWithTag("u3-draft-warning").assertExists()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-static-pencil-raster"))
        rule.onNodeWithTag("u3-static-pencil-raster").assertExists()
        rule.onNodeWithTag("u3-pencil-master-pending").assertDoesNotExist()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-interactive-life-cycle"))
        rule.onNodeWithTag("u3-stage-miracidium-1")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-current-life-stage")
            .performScrollTo().assertTextContains("miracidium",ignoreCase=true)
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-practice-heading"))
        rule.onNodeWithTag("u3-practice-heading").assertExists()
    }

    @Test fun ascarisHasNoSnailAndHasCorrectLungMigration() {
        openChapter("u3-ascaris",true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-static-pencil-raster"))
        rule.onNodeWithTag("u3-static-pencil-raster").assertExists()
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-interactive-life-cycle"))
        rule.onNodeWithTag("u3-stage-lung-3")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-current-life-stage")
            .performScrollTo().assertTextContains("நுரையீரல்",substring=true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-practice-heading"))
        rule.onNodeWithTag("u3-practice-heading").assertExists()
    }

    @Test fun allSevenUnitThreeChaptersOpenNativeTextbooksBeforeQuestions() {
        rule.onNodeWithTag("unit-3").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-3").fetchSemanticsNodes().isNotEmpty()
        }
        for (id in UnitIIILessons.chapterIds) {
            rule.onNodeWithTag("native-unit-3")
                .performScrollToNode(hasTestTag("chapter-"+id))
            rule.onNodeWithTag("chapter-"+id).performClick()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("u3-reader-"+id).fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithTag("u3-lesson-title").assertExists()
            rule.onNodeWithTag("u3-back").performClick()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("native-unit-3").fetchSemanticsNodes().isNotEmpty()
            }
        }
    }
}
