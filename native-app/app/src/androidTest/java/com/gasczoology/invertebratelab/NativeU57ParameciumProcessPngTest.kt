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

/** Packaged raster references remain accessible beside the actual native demonstrations. */
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
        rule.waitUntil(20000) {
            rule.onAllNodesWithTag("r1-simulator-heading", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun go(process:String) {
        rule.waitUntil(20000) {
            rule.onAllNodesWithTag("r1-process-"+process, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-process-selector").performScrollTo()
        rule.onNodeWithTag("r1-process-"+process, useUnmergedTree = true).performScrollTo().performClick()
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
    @Test fun rasterReferencesAndReviewedTeachingModelsAreReachable() {
        for(process in listOf("ciliary_motion","osmoregulation","feeding")) {
            go(process)
            val tag = "u57-paramecium-process-bitmap-" + process
            rule.onNodeWithTag(tag).performScrollTo().assertIsDisplayed()
            rule.onNodeWithTag("u58-open-process-image-" + process).performScrollTo().performClick()
            rule.onNodeWithTag("r2-fullscreen-bitmap").assertIsDisplayed()
            rule.onNodeWithTag("r2-zoom-in").performClick()
            rule.onNodeWithTag("r6-zoom-level").assertTextContains("200%", substring = true)
            screenshot(process + "-reference-enlarged")
            rule.onNodeWithTag("r2-fullscreen-close").performClick()
        }
        for ((process, chapter) in listOf("binary_fission" to "fission", "conjugation" to "conjugation")) {
            go(process)
            val views = ParameciumNuclearBiology.chapter(chapter).views
            for (view in views) {
                rule.onNodeWithTag("r17-stage-" + view.id, true).performScrollTo().performClick()
                rule.onNodeWithTag("r17-nuclear-canvas", true).performScrollTo().assertIsDisplayed()
                rule.onNodeWithTag("r17-stage-title", true).performScrollTo().assertTextContains(view.heading.tamil, substring = true)
            }
            rule.onNodeWithTag("u57-paramecium-process-png-missing-" + process).assertDoesNotExist()
            screenshot(process + "-native-sequence")
        }
    }
}
