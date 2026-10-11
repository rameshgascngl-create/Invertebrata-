package com.gasczoology.invertebratelab

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
class NativeUnitIITeachingTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    private val context get()=InstrumentationRegistry.getInstrumentation().targetContext
    @Before fun resetToHome() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }
    @Test fun syconShowsBilingualTextbookAndAnatomicalWaterRouteBeforeA5() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("chapter-u2-sycon").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("chapter-u2-sycon").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-sycon-canvas").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-lesson-title").assertExists()
        rule.onNodeWithTag("u2-draft-disclosure").assertExists()
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo()
            .assertHasClickAction().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("03 · புரோசோபைல்")
        rule.onNodeWithTag("u2-stage-apopyle").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("05 · அப்போபைல்")
        rule.onNodeWithTag("u2-practice-heading").performScrollTo().assertExists()
    }
    @Test fun obeliaDistinguishesFeedingReproductivePolypMedusaAndPlanula() {
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.onNodeWithTag("chapter-u2-obelia").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-obelia-canvas").fetchSemanticsNodes().isNotEmpty()
        }
        for(id in listOf("hydranth","gonangium","medusa","planula","settlement")) {
            rule.onNodeWithTag("u2-stage-"+id).performScrollTo()
                .assertHasClickAction().performClick()
            rule.onNodeWithTag("u2-stage-explanation").assertExists()
        }
        rule.onNodeWithTag("u2-section-life-history").performScrollTo().assertExists()
        rule.onNodeWithTag("u2-practice-heading").performScrollTo().assertExists()
    }
}
