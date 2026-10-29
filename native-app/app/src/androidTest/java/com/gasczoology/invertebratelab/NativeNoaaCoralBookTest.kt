package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

/** Screenshots are real SurfaceFlinger output, at the system's actual font scale. */
@RunWith(AndroidJUnit4::class)
class NativeNoaaCoralBookTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
    }

    private fun waitFor(tag: String) = rule.waitUntil(30_000) {
        rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
    }

    private fun capture(name: String) {
        rule.waitForIdle()
        val ui = InstrumentationRegistry.getInstrumentation().uiAutomation
        val dest = "/sdcard/Download/noaa-coral-qa"
        ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("mkdir -p $dest"))
            .use { it.readBytes() }
        val output = "$dest/$name.png"
        ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("screencap -p $output"))
            .use { it.readBytes() }
        val bytes = ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("cat $output"))
            .use { it.readBytes() }
        assertTrue("Real screenshot was not saved: $output", bytes.size > 10000)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertTrue("Corrupt real screenshot", bitmap != null && bitmap.width > 300)
        bitmap?.recycle()
    }

    private fun testReader(language: AppLanguage) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val actual = context.resources.configuration.fontScale
        val expected = InstrumentationRegistry.getArguments().getString("expectedScale")
            ?.toFloatOrNull() ?: error("CI must pass expectedScale")
        assertTrue("System scale mismatch $actual != $expected", abs(actual - expected) < 0.06f)
        val scaleName = if (actual > 1.5f) "200" else "100"
        val langName = if (language == AppLanguage.TAMIL) "tamil" else "english"

        waitFor("native-home")
        rule.onNodeWithTag(if (language == AppLanguage.TAMIL) "language-tamil" else "language-english")
            .performClick()
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        waitFor("native-unit-2")
        rule.onNodeWithTag("native-unit-2")
            .performScrollToNode(hasTestTag("chapter-u2-corals"))
        rule.onNodeWithTag("chapter-u2-corals").performClick()
        waitFor("u2-reader-u2-corals")
        rule.onNodeWithTag("chapter-language-english").assertExists()
        rule.onNodeWithTag("chapter-language-tamil").assertExists()
        rule.onNodeWithTag("chapter-language-toolbar").assertIsDisplayed()
        // Capture the top bar before the reader scrolls it out of view. In
        // addition to semantic checks this documents status-bar clearance.
        capture("chapter-language-$langName-$scaleName")

        rule.onNodeWithTag("u2-reader-u2-corals")
            .performScrollToNode(hasTestTag("book-figure-lib-coral-polyp"))
        val bitmap = rule.onNodeWithTag("r2-plate-bitmap-lib-coral-polyp")
        bitmap.assertExists().performScrollTo()
        rule.onNodeWithTag("r2-plate-missing-lib-coral-polyp").assertDoesNotExist()
        capture("coral-$langName-$scaleName-inline")

        bitmap.performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertExists()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        val viewport = rule.onNodeWithTag("r6-zoom-viewport-clipped")
            .fetchSemanticsNode().boundsInRoot
        // A Compose Dialog creates a second semantics root. Measure against
        // that unique dialog, not onRoot(), which is ambiguous while open.
        val dialog = rule.onNodeWithTag("r2-fullscreen-dialog")
            .fetchSemanticsNode().boundsInRoot
        assertTrue("Full-screen image collapsed at font scale $actual: viewport=${viewport.height}, dialog=${dialog.height}",
            viewport.height > dialog.height * 0.35f)
        rule.onNodeWithTag("r2-fullscreen-close").assertIsDisplayed()
        rule.onNodeWithTag("r2-zoom-in").performClick()
        rule.onNodeWithTag("r6-zoom-level")
            .assertTextEquals(if (language == AppLanguage.TAMIL) "பெரிதாக்கம்: 200%" else "Zoom: 200%")
        capture("coral-$langName-$scaleName-zoom")
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertDoesNotExist()
    }

    @Test fun englishCoralPlateIsVisibleAndZoomable() = testReader(AppLanguage.ENGLISH)
    @Test fun tamilCoralPlateIsVisibleAndZoomable() = testReader(AppLanguage.TAMIL)
}
