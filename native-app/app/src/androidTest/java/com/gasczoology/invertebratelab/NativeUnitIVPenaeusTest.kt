package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIVPenaeusTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val reader = "book-reader-u4-penaeus"

    @Before fun resetToHome() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        await("native-home")
    }

    private fun node(tag: String) = rule.onNodeWithTag(tag, useUnmergedTree = true)

    private fun await(tag: String) {
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag(tag, useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun find(tag: String): SemanticsNodeInteraction {
        node(reader).performScrollToNode(hasTestTag(tag))
        return node(tag).performScrollTo()
    }

    private fun enterPrawn(language: AppLanguage) {
        if (language == AppLanguage.TAMIL) node("language-tamil").performClick()
        node("unit-4").performScrollTo().performClick()
        await("native-unit-4")
        node("native-unit-4").performScrollToNode(hasTestTag("chapter-u4-penaeus"))
        node("chapter-u4-penaeus").performClick()
        await(reader)
        openReadingPage(rule, reader, "objectives")
        find("book-introduction").assertIsDisplayed()
    }

    @Test fun englishAppendageExplorerIsReachableFromTheOpeningPage() {
        enterPrawn(AppLanguage.ENGLISH)
        find("book-introduction").assert(hasAnyDescendant(hasText("Penaeus indicus", substring = true)))
        find("penaeus-open-appendages").performClick()
        find("u4-penaeus-appendage-disclosure").assertTextContains("19 PAIRS", substring = true)
        for ((index, option) in UnitIVPenaeusTeaching.appendages.withIndex()) {
            find("u4-penaeus-appendage-name").assertTextEquals(option.names.english)
            find("u4-penaeus-appendage-count").assertTextContains(
                option.numberOfPairs.toString(), substring = true
            )
            if (index < UnitIVPenaeusTeaching.appendages.lastIndex) {
                find("u4-penaeus-appendage-next").performClick()
            }
        }
    }

    @Test fun tamilLarvalStagePersistsAcrossPagingAndActualRotation() {
        enterPrawn(AppLanguage.TAMIL)
        find("penaeus-open-larvae").performClick()
        val stages = UnitIVPenaeusTeaching.stages
        fun assertStage(index: Int) {
            find("u4-penaeus-life-stage")
                .assertTextEquals("${index + 1} / ${stages.size} — ${stages[index].name.tamil}")
        }
        assertStage(0)
        find("u4-penaeus-life-next").performClick()
        assertStage(1)
        find("u4-penaeus-life-next").performClick()
        assertStage(2)
        openReadingPage(rule, reader, "objectives")
        find("penaeus-open-larvae").performClick()
        assertStage(2)
        try {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            rule.waitUntil(20_000) {
                context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            }
            await(reader)
            assertStage(2)
        } finally {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            rule.waitUntil(20_000) {
                context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            }
        }
    }

    @Test fun prawnFiveMarkAnswerAndEarthwormBackNavigationRemainIntact() {
        enterPrawn(AppLanguage.ENGLISH)
        val question = ValidatedA5AssetRepository(context).load().single { it.number == 4 }
            .chapters.single { it.id == "u4-penaeus" }.a5Questions.first()
        openReadingPage(rule, reader, "practice-${question.id}")
        find("a5-question-${question.id}").assertTextEquals(question.question.english)
        find("a5-toggle-${question.id}").performClick()
        question.answerPoints.forEachIndexed { index, point ->
            find("a5-point-${question.id}-${index + 1}").assertTextEquals("${index + 1}. ${point.english}")
        }
        node("book-back").performClick()
        await("native-unit-4")
        node("native-unit-4").performScrollToNode(hasTestTag("u4-open-earthworm-textbook"))
        node("u4-open-earthworm-textbook").performClick()
        await("u4-earthworm-reader")
        node("u4-earthworm-title").assertTextContains("Metaphire posthuma", substring = true)
    }

    @Test fun tamilPrawnAnatomyOpensTheCanonicalRasterAndCredit() {
        enterPrawn(AppLanguage.TAMIL)
        find("book-reference-gallery-toggle-u4-penaeus").performClick()
        val image = find("r2-plate-bitmap-penaeus-external").assertIsDisplayed()
        find("plate-credit-penaeus-external").assertIsDisplayed()
        image.performScrollTo().performClick()
        node("r2-fullscreen-bitmap").assertIsDisplayed()
        node("r2-zoom-in").performClick()
        node("r2-fullscreen-close").performClick()
        find("r2-plate-bitmap-penaeus-external").assertIsDisplayed()
    }
}
