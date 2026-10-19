package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A real offline Android test of contextual Paramecium PNGs.
 * Verifies no chapter-independent repetition of the overview diagram.
 * The separate 43-chapter original A5 bank is not altered.
 */
@RunWith(AndroidJUnit4::class)
class NativeU58ParameciumReaderPngTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun openReading() {
        val ctx = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.TAMIL)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun choose(section: String) {
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-" + section, useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r1-study-" + section, useUnmergedTree = true)
            .performScrollTo().assertExists()
    }

    private fun capture(name: String) {
        val inst = InstrumentationRegistry.getInstrumentation()
        val suffix = if (inst.targetContext.resources.configuration.fontScale >= 1.95f) "200" else "100"
        val p = "/sdcard/Download/u58-qa/reader-$suffix-$name.png"
        ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("screencap -p $p")
        ).use { it.readBytes() }
        val screenshot = ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("cat $p")
        ).use { it.readBytes() }
        assertTrue("PNG screenshot missing", screenshot.size > 12000)
        assertNotNull(BitmapFactory.decodeByteArray(screenshot, 0, screenshot.size))
    }

    @Test fun relevantFullResolutionPngOnlyNoDuplicateOverviewOrUnverifiedTouchClaims() {
        rule.onNodeWithTag("r14-external-png", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("u58-expand-external-png", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        rule.onNodeWithTag("r2-zoom-in").performClick()
        capture("external-png-fullscreen")
        rule.onNodeWithTag("r2-fullscreen-close").performClick()

        choose("osmoregulation")
        rule.onNodeWithTag("u58-concept-figure-vacuole", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("u58-expand-concept-png-vacuole", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        capture("water-balance-png-fullscreen")
        rule.onNodeWithTag("r2-fullscreen-close").performClick()

        choose("nuclear-dimorphism")
        rule.onNodeWithTag("u58-concept-figure-overview", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("u58-concept-figure-vacuole", useUnmergedTree = true).assertDoesNotExist()

        choose("asexual-division-process")
        rule.onNodeWithTag("u58-expand-concept-png-fission", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("u58-concept-figure-overview", useUnmergedTree = true).assertDoesNotExist()

        rule.onNodeWithTag("u58-expand-concept-png-fission", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        capture("fission-candidate-png-fullscreen")
        rule.onNodeWithTag("r2-fullscreen-close").performClick()

        choose("conjugation")
        rule.onNodeWithTag("u58-expand-concept-png-conjugation", useUnmergedTree = true)
            .assertDoesNotExist()
        rule.onNodeWithTag("r14-concept-png-missing-conjugation", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("u58-concept-figure-overview", useUnmergedTree = true).assertDoesNotExist()

        choose("laboratory-reasoning")
        rule.onNodeWithTag("u58-concept-png-card-overview", useUnmergedTree = true)
            .assertDoesNotExist()
        rule.onNodeWithTag("r1-study-laboratory-reasoning", useUnmergedTree = true)
            .assertExists()
        capture("other-lesson-no-duplicate-image")
    }
}
