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

/** R8 forbids drawn Paramecium fallback in the real student simulator. */
@RunWith(AndroidJUnit4::class)
class NativeR8ParameciumPngOnlyTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    @Before fun reset() {
        runBlocking {
            val ctx = androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }
    @Test fun tamilSimulatorUsesActualPngNotHandDrawnCanvasAndRetainsStages() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("native-home")
            .performScrollToNode(hasTestTag("unit-1"))
        rule.onNodeWithTag("unit-1").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u1-open-diagram-atlas").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-tab-simulate").performScrollTo().performClick()
        rule.onNodeWithTag("r1-simulator-heading").performScrollTo().assertExists()
        rule.onNodeWithTag("r8-paramecium-png-image-vacuole")
            .performScrollTo().assertIsDisplayed()
        rule.onNodeWithTag("r8-paramecium-not-stage-specific")
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r1-process-canvas").assertDoesNotExist()
        rule.onNodeWithTag("r1-next").performScrollTo().performClick()
        rule.onNodeWithTag("r1-stage-title").performScrollTo()
            .assertTextContains("2/",substring=true)
        rule.onNodeWithTag("r1-process-ciliary_motion")
            .performScrollTo().performClick()
        rule.onNodeWithTag("r8-paramecium-png-image-external")
            .performScrollTo().assertIsDisplayed()
        rule.onNodeWithTag("r1-process-canvas").assertDoesNotExist()
        val inst=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val scale=inst.targetContext.resources.configuration.fontScale
        val suffix=if(scale>=1.95f)"200" else "100"
        val path="/sdcard/Download/five-unit-qa/r8-$suffix-paramecium-png-simulator-tamil.png"
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("screencap -p $path")
        ).use{it.readBytes()}
        val png=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("cat $path")
        ).use{it.readBytes()}
        assertTrue("Missing R8 screenshot",png.size>12_000)
        val bmp=android.graphics.BitmapFactory.decodeByteArray(png,0,png.size)
        assertNotNull(bmp)
        bmp!!.recycle()
    }
}
