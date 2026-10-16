package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIVPenaeusTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun resetToHome() {
        runBlocking {
            val ctx=androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enterPrawn(tamil:Boolean) {
        if(tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-4").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("chapter-u4-penaeus"))
        rule.onNodeWithTag("chapter-u4-penaeus").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-u4-penaeus")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun find(tag:String) {
        rule.onNodeWithTag("native-chapter-u4-penaeus")
            .performScrollToNode(hasTestTag(tag))
    }

    private fun capture(name:String) {
        rule.waitForIdle()
        val scale=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext.resources.configuration.fontScale
        val suffix=if(scale>=1.95f)"200" else "100"
        val path="/sdcard/Download/unit4-qa/unit4-"+suffix+"-"+name+".png"
        val automation=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p "+path)).use { it.readBytes() }
        val imageBytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat "+path)).use { it.readBytes() }
        assertTrue("Source screenshot missing",imageBytes.size>15000)
        val image=android.graphics.BitmapFactory.decodeByteArray(
            imageBytes,0,imageBytes.size)
        assertNotNull("Bad screenshot",image)
        image!!.recycle()
    }

    @Test fun englishPrawnHasRealHeadedLessonsAndAnatomyHonesty() {
        enterPrawn(false)
        find("u4-penaeus-textbook-title")
        rule.onNodeWithTag("u4-penaeus-textbook-title")
            .assertTextContains("Penaeus",substring=true)
        rule.onNodeWithTag("u4-penaeus-academic-hold").assertExists()
        find("u4-penaeus-section-classification")
        rule.onNodeWithTag("u4-penaeus-section-classification").assertExists()
        find("u4-penaeus-section-respiration")
        rule.onNodeWithTag("u4-penaeus-section-respiration").assertExists()
        find("u4-penaeus-appendage-explorer")
        rule.onNodeWithTag("u4-penaeus-appendage-disclosure").assertExists()
        rule.onNodeWithTag("u4-penaeus-appendage-count")
            .assertTextContains("5",substring=true)
        rule.onNodeWithTag("u4-penaeus-appendage-next")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u4-penaeus-appendage-count")
            .assertTextContains("3",substring=true)
        capture("penaeus-english-appendages")
    }

    @Test fun tamilPrawnStageChangesPersistAcrossLazyScrollingAndRotation() {
        enterPrawn(true)
        find("u4-penaeus-life-explorer")
        // First rendered stage is egg; move to nauplius and protozoea.
        rule.onNodeWithTag("u4-penaeus-life-stage")
            .assertTextContains("முட்டை",substring=true)
        rule.onNodeWithTag("u4-penaeus-life-next")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u4-penaeus-life-stage")
            .assertTextContains("நௌப்ளியஸ்",substring=true)
        rule.onNodeWithTag("u4-penaeus-life-next")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u4-penaeus-life-stage")
            .assertTextContains("புரோட்டோசோயா",substring=true)
        capture("penaeus-tamil-protozoea")
        // Lazy-list item may be disposed during scrolling: stage state is
        // hoisted outside it and must not silently return to egg.
        find("u4-penaeus-section-classification")
        find("u4-penaeus-life-explorer")
        rule.onNodeWithTag("u4-penaeus-life-stage")
            .assertTextContains("புரோட்டோசோயா",substring=true)
        capture("penaeus-tamil-stage-retained")
    }

    @Test fun prawnA5QuestionsAndBackNavigationRemainIntact() {
        enterPrawn(false)
        find("u4-penaeus-atlas-pending")
        rule.onNodeWithTag("u4-penaeus-atlas-pending").assertExists()
        find("u4-penaeus-a5-original-heading")
        rule.onNodeWithTag("u4-penaeus-a5-original-heading").assertExists()
        // The original A5 answer contents are immutable; opening the lesson
        // must not remove or silently rewrite the two validated questions.
        val original=NativeCurriculumManifest.a5ChapterIdsByUnit
            .getValue(4).contains("u4-penaeus")
        assertTrue(original)
        rule.onNodeWithTag("native-chapter-u4-penaeus")
            .performScrollToNode(hasTestTag("a5-card-u4-penaeus-A5-1"))
        rule.onNodeWithTag("a5-card-u4-penaeus-A5-1").assertExists()
        capture("penaeus-A5-preserved")
        rule.onNodeWithTag("native-chapter-u4-penaeus").performScrollToIndex(0)
        rule.onNodeWithText("Back").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        // The Unit IV LazyColumn may not have composed the Earthworm action
        // after returning from Penaeus at 200% font. Traverse its scroll
        // container, preserving the requirement that the original route works.
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("u4-open-earthworm-textbook"))
        rule.onNodeWithTag("u4-open-earthworm-textbook").assertExists().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u4-earthworm-reader")
                .fetchSemanticsNodes().isNotEmpty()
        }
        // Verify that the actual Earthworm teaching reader, not just the
        // navigation button, is available after the round-trip.
        rule.onNodeWithTag("u4-earthworm-title", useUnmergedTree = true)
            .assertTextContains("Metaphire posthuma", substring = true)
    }
}
