package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIITeachingTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun resetToHome() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        awaitTag("native-home")
    }

    private fun node(tag: String) = rule.onNodeWithTag(tag, useUnmergedTree = true)

    private fun awaitTag(tag: String) {
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag(tag, useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun openStages(id: String, tamil: Boolean): String {
        if (tamil) node("language-tamil").performClick()
        node("unit-2").performScrollTo().performClick()
        awaitTag("native-unit-2")
        node("native-unit-2").performScrollToNode(hasTestTag("chapter-$id"))
        node("chapter-$id").performClick()
        val reader = "u2-reader-$id"
        awaitTag(reader)
        openReadingPage(rule, reader, "interactive")
        awaitTag("u2-interactive-atlas")
        return reader
    }

    private fun selectStage(reader: String, id: String, expectedTitle: String, term: String) {
        val stage = "u2-stage-$id"
        node(reader).performScrollToNode(hasTestTag(stage))
        node(stage).performScrollTo().assertHasClickAction().performClick()
        node("u2-selected-stage").performScrollTo().assertTextEquals(expectedTitle)
        node("u2-stage-explanation").performScrollTo().assertTextContains(term, substring = true)
    }

    private fun verifyFigure(reader: String, slot: String, expectedStage: String, zoom: Boolean = false) {
        node("u2-open-stage-figure").performScrollTo().performClick()
        val figure = "r2-plate-bitmap-$slot"
        awaitTag("book-figure-$slot")
        node(reader).performScrollToNode(hasTestTag(figure))
        node(figure).performScrollTo().assertIsDisplayed().assertHasClickAction()
        if (zoom) {
            node(figure).performClick()
            node("r2-fullscreen-bitmap").assertIsDisplayed()
            node("r2-zoom-in").performClick()
            node("r2-fullscreen-bitmap").assertIsDisplayed()
            node("r2-fullscreen-close").performClick()
        }
        rule.activityRule.scenario.onActivity { it.onBackPressedDispatcher.onBackPressed() }
        awaitTag("u2-interactive-atlas")
        node("u2-selected-stage").performScrollTo().assertTextEquals(expectedStage)
    }

    private fun verifyFivePointAnswer(reader: String, id: String) {
        openReadingPage(rule, reader, "practice-$id")
        val toggle = "a5-toggle-$id"
        node(reader).performScrollToNode(hasTestTag(toggle))
        node(toggle).performScrollTo().performClick()
        for (point in 1..5) {
            node("a5-point-$id-$point").performScrollTo().assertIsDisplayed()
        }
    }

    @Test fun tamilSyconStageLinksToCanalFigureAndPractice() {
        val reader = openStages("u2-sycon", tamil = true)
        selectStage(reader, "prosopyle", "03 · புரோசோபைல்", "ஆரக் கால்வாயையும்")
        verifyFigure(reader, "sycon-canal-system", "03 · புரோசோபைல்", zoom = true)
        selectStage(reader, "apopyle", "05 · அப்போபைல்", "ஸ்பாஞ்சோசீலுக்குள்")
        verifyFivePointAnswer(reader, "u2-sycon-A5-1")
    }

    @Test fun englishSyconSpongocoelLinksToTheCanonicalCanalFigure() {
        val reader = openStages("u2-sycon", tamil = false)
        selectStage(reader, "spongocoel", "06 · Spongocoel", "collects the outgoing current")
        verifyFigure(reader, "sycon-canal-system", "06 · Spongocoel")
    }

    @Test fun tamilObeliaLinksFeedingReproductiveMedusaAndLarvalStages() {
        val reader = openStages("u2-obelia", tamil = true)
        selectStage(reader, "hydranth", "02 · உணவுப் ஹைட்ராந்த்", "இரையைப் பிடிக்க")
        verifyFigure(reader, "obelia-hydranth-gonangium", "02 · உணவுப் ஹைட்ராந்த்")
        selectStage(reader, "gonangium", "03 · இனப்பெருக்க கோனாஞ்சியம்", "கோனோதீக்கா")
        verifyFigure(reader, "obelia-hydranth-gonangium", "03 · இனப்பெருக்க கோனாஞ்சியம்")
        selectStage(reader, "medusa", "04 · நீந்தும் மெடூசா", "இனச்செல்களை")
        verifyFigure(reader, "obelia-medusa", "04 · நீந்தும் மெடூசா")
        selectStage(reader, "planula", "05 · பிளானுலா இளவுயிரி", "குறுஇழை")
        verifyFigure(reader, "obelia-life-cycle", "05 · பிளானுலா இளவுயிரி")
    }

    @Test fun englishObeliaLinksColonyAndSettlementToDistinctFigures() {
        val reader = openStages("u2-obelia", tamil = false)
        selectStage(reader, "colony", "01 · Colonial hydroid", "coenosarc")
        verifyFigure(reader, "obelia-colony", "01 · Colonial hydroid")
        selectStage(reader, "settlement", "06 · Settlement and new colony", "metamorphoses")
        verifyFigure(reader, "obelia-life-cycle", "06 · Settlement and new colony", zoom = true)
        verifyFivePointAnswer(reader, "u2-obelia-A5-1")
    }
}
