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

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class UnitIReaderAcceptance

/** Opens the same raster gallery through both student routes. */
@UnitIReaderAcceptance
@RunWith(AndroidJUnit4::class)
class NativeUnitIDiagramAccessTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun resetToHome() {
        runBlocking {
            val context = androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enterUnitOne(tamil: Boolean) {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun expectAtlas() {
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r2-type-study-gallery-paramecium").assertExists()
        rule.onNodeWithTag("r2-plate-bitmap-paramecium-external-cilia")
            .performScrollTo().assertIsDisplayed()
        rule.onNodeWithTag("n23b-external-canvas").assertDoesNotExist()
        rule.onNodeWithTag("r2-plate-missing-paramecium-external-cilia").assertDoesNotExist()
    }

    private fun screenshot(scene: String) {
        rule.waitForIdle()
        val automation = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().uiAutomation
        val scale = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext.resources.configuration.fontScale
        val key = if (scale >= 1.95f) "200" else "100"
        val path = "/sdcard/Download/u1-diagram-qa/u1-" + key + "-" + scene + ".png"
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p " + path)
        ).use { it.readBytes() }
        val bytes = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat " + path)
        ).use { it.readBytes() }
        assertTrue("Unit I device screenshot missing", bytes.size > 15_000)
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertNotNull("Unit I screenshot is invalid", bitmap)
        assertTrue(bitmap.height > bitmap.width)
        bitmap.recycle()
    }

    @Test fun unitEntryShowsDistinctRasterPlatesAndFullscreenViewer() {
        enterUnitOne(tamil = false)
        rule.onNodeWithTag("u1-missing-protozoa-plates").assertExists()
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("u1-open-diagram-atlas"))
        rule.onNodeWithTag("u1-open-diagram-atlas").performClick()
        expectAtlas()
        screenshot("english-external")
        for (id in listOf("oral-apparatus", "contractile-vacuoles", "locomotion")) {
            rule.onNodeWithTag("r2-plate-bitmap-paramecium-$id").performScrollTo().assertIsDisplayed()
            rule.onNodeWithTag("r2-plate-missing-paramecium-$id").assertDoesNotExist()
        }
        rule.onNodeWithTag("r2-plate-expand-paramecium-oral-apparatus").performScrollTo().performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertExists()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertDoesNotExist()
    }

    @Test fun parameciumChapterOpensTamilAtlasWithoutDeletingAssessmentRoute() {
        enterUnitOne(tamil = true)
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("chapter-u1-paramecium"))
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("book-reader-u1-paramecium")
                .fetchSemanticsNodes().isNotEmpty()
        }
        openReadingPage(rule, "book-reader-u1-paramecium", "objectives")
        rule.onNodeWithTag("book-reader-u1-paramecium")
            .performScrollToNode(hasTestTag("u1-paramecium-open-diagrams"))
        rule.onNodeWithTag("u1-paramecium-open-diagrams").performClick()
        expectAtlas()
        screenshot("tamil-external")
        // The existing five-mark corpus remains reachable via the Practice tab.
        rule.onNodeWithTag("r1-tab-practice").performScrollTo().performClick()
        rule.onNodeWithTag("r1-open-practice").performScrollTo().performClick()
        val readerTag = "book-reader-u1-paramecium"
        val questionId = "u1-paramecium-A5-1"
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag(readerTag, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        val reader = rule.onNodeWithTag(readerTag, useUnmergedTree = true)
        reader.performScrollToNode(hasTestTag("a5-toggle-$questionId"))
        rule.onNodeWithTag("a5-question-$questionId", useUnmergedTree = true).assertExists()
        rule.onNodeWithTag("a5-toggle-$questionId", useUnmergedTree = true).performClick()
        reader.performScrollToNode(hasTestTag("a5-point-$questionId-5"))
        rule.onNodeWithTag("a5-point-$questionId-5", useUnmergedTree = true).assertIsDisplayed()
    }
}
