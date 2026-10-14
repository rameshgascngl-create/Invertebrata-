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

    private fun capture(scene: String) {
        rule.waitForIdle()
        val context=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext
        val scale=if(context.resources.configuration.fontScale >= 1.95f) "200" else "100"
        val path="/sdcard/Download/unit3-qa/unit3-"+scale+"-"+scene+".png"
        val ui=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p "+path)).use { it.readBytes() }
        val bytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat "+path)).use { it.readBytes() }
        val screenshot=checkNotNull(
            android.graphics.BitmapFactory.decodeByteArray(bytes,0,bytes.size))
        org.junit.Assert.assertTrue(bytes.size>15000)
        org.junit.Assert.assertTrue(screenshot.height>screenshot.width)
        screenshot.recycle()
    }

    @Test fun fasciolaHasDistinctHistoricalLessonFigureAndOrderedSimulation() {
        openChapter("u3-fasciola",false)
        rule.onNodeWithTag("u3-lesson-title").assertExists()
        rule.onNodeWithTag("u3-draft-warning").assertExists()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-static-pencil-raster"))
        rule.onNodeWithTag("u3-static-pencil-raster").assertExists()
        rule.onNodeWithTag("u3-static-pencil-raster").performScrollTo()
        capture("fasciola-historical")
        rule.onNodeWithTag("u3-pencil-master-pending").assertDoesNotExist()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-interactive-life-cycle"))
        rule.onNodeWithTag("u3-stage-miracidium-1")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-current-life-stage")
            .performScrollTo().assertTextContains("miracidium",substring=true,ignoreCase=true)
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-practice-heading"))
        rule.onNodeWithTag("u3-practice-heading").assertExists()
    }

    @Test fun ascarisHasNoSnailAndHasCorrectLungMigration() {
        openChapter("u3-ascaris",true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-static-pencil-raster"))
        rule.onNodeWithTag("u3-static-pencil-raster").assertExists()
        rule.onNodeWithTag("u3-static-pencil-raster").performScrollTo()
        capture("ascaris-historical")
        rule.onNodeWithTag("u3-pencil-master-pending").assertDoesNotExist()
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

    @Test fun sourcedFasciolaPlateRejectsObsoleteTouchCoordinates() {
        openChapter("u3-fasciola",false)
        for (plate in listOf("external","digestive-reproductive","life-cycle")) {
            rule.onNodeWithTag("u3-reader-u3-fasciola")
                .performScrollToNode(hasTestTag("u3-plate-bitmap-"+plate))
            rule.onNodeWithTag("u3-plate-bitmap-"+plate).assertExists()
            rule.onNodeWithTag("u3-plate-pending-"+plate).assertDoesNotExist()
        }
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-plate-touch-external"))
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-hotspot-external-acetabulum"))
        rule.onNodeWithTag("u3-hotspot-external-acetabulum")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-external")
            .assertTextContains("Ventral sucker",substring=true)
        rule.onNodeWithTag("u3-organ-explanation-external")
            .assertTextContains("attachment sucker", substring=true)
        rule.onNodeWithTag("u3-voice-request-external")
            .assertTextContains("attachment sucker", substring=true)
        rule.onNodeWithTag("u3-speak-organ-external").assertExists()
        capture("fasciola-revised")
    }

    @Test fun tamilAscarisPlateUsesAccessibleUnmappedOrganControl() {
        openChapter("u3-ascaris",true)
        for (plate in listOf("male-female-external","female-internal","life-cycle")) {
            rule.onNodeWithTag("u3-reader-u3-ascaris")
                .performScrollToNode(hasTestTag("u3-plate-bitmap-"+plate))
            rule.onNodeWithTag("u3-plate-bitmap-"+plate).assertExists()
            rule.onNodeWithTag("u3-plate-pending-"+plate).assertDoesNotExist()
        }
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-plate-touch-male-female-external"))
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-hotspot-male-female-external-female-vulva"))
        rule.onNodeWithTag("u3-hotspot-male-female-external-female-vulva")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-male-female-external")
            .assertTextContains("வல்வா",substring=true)
        rule.onNodeWithTag("u3-organ-explanation-male-female-external")
            .assertTextContains("வல்வா", substring=true)
        rule.onNodeWithTag("u3-voice-request-male-female-external")
            .assertTextContains("வல்வா", substring=true)
        rule.onNodeWithTag("u3-speak-organ-male-female-external").assertExists()
        capture("ascaris-revised")
    }

    @Test fun cdcCompleteAscarisSourceAndTamilCycleNarrationAreAccessible() {
        openChapter("u3-ascaris",true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-cdc-complete-life-cycle-credit"))
        rule.onNodeWithTag("u3-cdc-complete-life-cycle-credit")
            .assertTextContains("CDC DPDx",substring=true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-hotspot-life-cycle-unfertilized"))
        rule.onNodeWithTag("u3-hotspot-life-cycle-unfertilized")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-life-cycle")
            .assertTextContains("கருவுறாத",substring=true)
        rule.onNodeWithTag("u3-voice-request-life-cycle")
            .assertTextContains("கருவுறாத",substring=true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-hotspot-life-cycle-swallow"))
        rule.onNodeWithTag("u3-hotspot-life-cycle-swallow")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-life-cycle")
            .assertTextContains("மீண்டும் விழுங்குதலும்",substring=true)
        rule.onNodeWithTag("u3-voice-request-life-cycle")
            .assertTextContains("மீண்டும் விழுங்கப்படுகின்றன",substring=true)
        capture("ascaris-cdc-complete")
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
