package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Actual packaged PNG decode in the lab, no substituted process Canvas. */
@RunWith(AndroidJUnit4::class)
class NativeU57ParameciumProcessPngTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun openLab() {
        val ctx=InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.TAMIL)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20000) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-tab-simulate").performScrollTo().performClick()
    }
    private fun go(process:String) {
        rule.onNodeWithTag("r1-process-"+process).performScrollTo().performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("r1-simulator-heading").performScrollTo()
    }
    private fun screenshot(name:String) {
        val inst=InstrumentationRegistry.getInstrumentation()
        val scale=inst.targetContext.resources.configuration.fontScale
        val text=if(scale>=1.95f)"200" else "100"
        val path="/sdcard/Download/u57-qa/paramecium-$text-$name.png"
        ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("screencap -p $path")
        ).use {it.readBytes()}
        val png=ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("cat $path")
        ).use {it.readBytes()}
        assertTrue("Missing screenshot",png.size>12000)
        assertNotNull(BitmapFactory.decodeByteArray(png,0,png.size))
    }
    @Test fun pngOnlyPhysiologyStagesNeverDisplayCanvasOrWrongReproductionArtwork() {
        for(process in listOf("ciliary_motion","osmoregulation","feeding")) {
            go(process)
            val tag="u57-paramecium-process-bitmap-"+process
            rule.onNodeWithTag(tag).performScrollTo().assertIsDisplayed()
            rule.onNodeWithTag("r1-process-canvas").assertDoesNotExist()
            rule.onNodeWithTag("u57-static-png-disclosure").performScrollTo().assertExists()
            if(process=="osmoregulation") {
                screenshot("vacuole-source-png")
                rule.onNodeWithTag("u58-open-process-image-"+process).performScrollTo().performClick()
                rule.onNodeWithTag("r2-fullscreen-dialog").assertExists()
                rule.onNodeWithTag("r2-fullscreen-bitmap").assertIsDisplayed()
                rule.onNodeWithTag("r2-zoom-in").performClick()
                rule.onNodeWithTag("r6-zoom-level").assertTextContains("200%", substring = true)
                screenshot("vacuole-png-enlarged")
                rule.onNodeWithTag("r2-fullscreen-close").performClick()
                rule.onNodeWithTag("r2-fullscreen-dialog").assertDoesNotExist()
                rule.onNodeWithTag(tag).performScrollTo().assertExists()
            }
        }
        for(process in listOf("binary_fission")) {
            go(process)
            val tag="u57-paramecium-process-png-missing-"+process
            rule.onNodeWithTag(tag).performScrollTo().assertIsDisplayed()
            rule.onNodeWithTag("r1-process-canvas").assertDoesNotExist()
            rule.onNodeWithTag("u57-paramecium-process-bitmap-"+process).assertDoesNotExist()
            screenshot("fission-text-no-vector")
        }
        go("conjugation")
        rule.onNodeWithTag("u57-paramecium-process-bitmap-conjugation").performScrollTo().assertIsDisplayed()
        rule.onNodeWithTag("r1-stage-title").performScrollTo().assertExists()
        rule.onNodeWithTag("r1-next").performScrollTo().performClick()
        rule.onNodeWithTag("r1-stage-explanation").performScrollTo().assertExists()
    }
}
