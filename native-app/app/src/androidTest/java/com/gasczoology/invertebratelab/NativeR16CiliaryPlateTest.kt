package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.ParameciumLearningEngine
import com.gasczoology.invertebratelab.data.ParameciumProcess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * R1.6 separate candidate tests. Do not modify R1.5's original gates.
 * True 200% device evidence is required separately before acceptance.
 */
@RunWith(AndroidJUnit4::class)
class NativeR16CiliaryPlateTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun resetLearningPosition() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    private fun openSection(sectionId: String, tamil: Boolean = false) {
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-" + sectionId, useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r16-ciliary-textbook", useUnmergedTree = true)
            .performScrollTo().assertExists()
    }

    @Test
    fun fullEnglishCiliaryTeachingPlateHasCorrectStagesAndSubsections() {
        openSection("pellicle-and-cilia")
        val motion = ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION)
        assertEquals(listOf("rest", "effective", "recovery", "wave"),
            motion.stages.map { it.id })
        rule.onNodeWithTag("r16-ciliary-canvas", useUnmergedTree = true)
            .performScrollTo().assertExists()
        for ((position, stage) in motion.stages.withIndex()) {
            rule.onNodeWithTag("r16-ciliary-stage-" + stage.id, useUnmergedTree = true)
                .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.onNodeWithTag("r16-ciliary-stage-title", useUnmergedTree = true)
                .performScrollTo().assertTextEquals(
                    "Stage " + (position + 1) + "/4 — " + stage.heading.english)
            rule.onNodeWithTag("r16-ciliary-stage-explanation", useUnmergedTree = true)
                .performScrollTo().assertTextEquals(stage.explanation.english)
        }
        for (i in 0 until 3) {
            rule.onNodeWithTag("r16-ciliary-subheading-" + i, useUnmergedTree = true)
                .performScrollTo().assertExists()
            rule.onNodeWithTag("r16-ciliary-subtext-" + i, useUnmergedTree = true)
                .performScrollTo().assertExists()
        }
        rule.onNodeWithTag("r16-ciliary-caution", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r16-ciliary-narrate", useUnmergedTree = true)
            .performScrollTo().assertHasClickAction()
        rule.onNodeWithTag("r16-view-ciliary-plate", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.waitForIdle()
        captureRealR16Screen("r16-ciliary-normal.png")
    }

    @Test
    fun ciliaryStageSurvivesSwitchingBetweenTextbookSections() {
        openSection("pellicle-and-cilia")
        rule.onNodeWithTag("r16-ciliary-stage-wave", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-cortical-avoidance-response",
            useUnmergedTree = true).performScrollTo().performClick()
        val lastStage = ParameciumLearningEngine
            .simulation(ParameciumProcess.CILIARY_MOTION).stages.last()
        rule.onNodeWithTag("r16-ciliary-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("Stage 4/4 — " + lastStage.heading.english)
        rule.onNodeWithTag("r16-ciliary-stage-explanation", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(lastStage.explanation.english)
    }

    @Test
    fun nativeTamilCiliaryDiagramFitsTheRealVisibleViewport() {
        openSection("cortical-avoidance-response", tamil = true)
        val stage = ParameciumLearningEngine
            .simulation(ParameciumProcess.CILIARY_MOTION).stages[2]
        rule.onNodeWithTag("r16-ciliary-stage-recovery", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r16-ciliary-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("நிலை 3/4 — " + stage.heading.tamil)
        rule.onNodeWithTag("r16-ciliary-stage-explanation", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(stage.explanation.tamil)
        rule.onNodeWithTag("r16-view-ciliary-plate", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val bounds = rule.onNodeWithTag("r16-ciliary-canvas", useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        val screen = rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("R1.6 pencil ciliary Canvas must fit the visible Android viewport: " +
            "canvas=" + bounds + " viewport=" + screen,
            bounds.height > 0f && bounds.top >= screen.top - 1f &&
                bounds.bottom <= screen.bottom + 1f)

        val scale = InstrumentationRegistry.getInstrumentation().targetContext
            .resources.configuration.fontScale
        captureRealR16Screen(
            if (scale >= 1.95f) "r16-ciliary-tamil200.png"
            else "r16-ciliary-tamil-normal.png")
    }

    private fun captureRealR16Screen(fileName: String) {
        check(fileName in setOf(
            "r16-ciliary-normal.png",
            "r16-ciliary-tamil200.png",
            "r16-ciliary-tamil-normal.png"))
        val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
        val folder = "/sdcard/Download/native-anatomy-evidence"
        ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("mkdir -p " + folder)).use { it.readBytes() }
        val path = folder + "/" + fileName
        ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p " + path)).use { it.readBytes() }
        val png = ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat " + path)).use { it.readBytes() }
        check(png.size > 1000) { "Actual screen capture missing: " + path }
        val bitmap = checkNotNull(BitmapFactory.decodeByteArray(png, 0, png.size))
        try {
            assertTrue(bitmap.width > 100 && bitmap.height > 100)
            var paper = 0
            var pencil = 0
            for (y in 0 until bitmap.height step 12) {
                for (x in 0 until bitmap.width step 12) {
                    when (bitmap.getPixel(x, y) and 0x00FFFFFF) {
                        0x00FBFAF6 -> paper++
                        0x0044413F, 0x00302E2C, 0x008A8580 -> pencil++
                    }
                }
            }
            // Technical evidence only. Existing anatomical fidelity and pixel
            // thresholds are not replaced, relaxed or reinterpreted.
            assertTrue("R1.6 screen missing pencil-paper pixels", paper > 10)
            assertTrue("R1.6 screen missing graphite pixels", pencil > 1)
        } finally {
            bitmap.recycle()
        }
    }
}
