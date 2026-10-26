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

/** R3: real five-unit atlas continuity. Do not replace absent images with sketches. */
@RunWith(AndroidJUnit4::class)
class NativeFiveUnitMissingRasterCoverageTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    @Before fun resetHome() {
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
    private fun enter(unit: Int, chapter: String, tamil: Boolean): String {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-$unit").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-$unit").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-$unit")
            .performScrollToNode(hasTestTag("chapter-$chapter"))
        rule.onNodeWithTag("chapter-$chapter").performClick()
        val root = "u$unit-reader-$chapter"
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag(root).fetchSemanticsNodes().isNotEmpty()
        }
        return root
    }
    private fun find(root: String, tag: String) {
        rule.onNodeWithTag(root).performScrollToNode(hasTestTag(tag))
        rule.onNodeWithTag(tag).assertExists()
    }
    private fun capture(name: String) {
        rule.waitForIdle()
        val inst = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val size = if (inst.targetContext.resources.configuration.fontScale >= 1.95f)
            "200" else "100"
        val path = "/sdcard/Download/five-unit-qa/r3-$size-$name.png"
        val ui = inst.uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p $path")).use { it.readBytes() }
        val bytes = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat $path")).use { it.readBytes() }
        assertTrue("Missing real screenshot $name", bytes.size > 12_000)
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertNotNull("Invalid Android PNG $name", bitmap)
        assertTrue(bitmap!!.height > bitmap.width)
        bitmap.recycle()
    }
    @Test fun syconBookChapterShowsAllThreeFiguresInlineOnce() {
        val root = enter(2, "u2-sycon", false)
        find(root, "book-objectives")
        for (plate in listOf("external", "canal-system", "reproduction")) {
            find(root, "r2-plate-bitmap-sycon-$plate")
            rule.onNodeWithTag("r2-plate-bitmap-sycon-$plate").assertIsDisplayed()
            rule.onNodeWithTag("r2-plate-missing-sycon-$plate").assertDoesNotExist()
        }
        capture("u2-sycon-english")
        find(root, "book-glossary")
        find(root, "u2-stage-explanation")
        rule.onNodeWithTag("u2-sycon-historic-static-raster").assertDoesNotExist()
    }
    /** Book chapters show each plate once, inline; the legacy galleries must not return beside them. */
    private fun inlineFigures(root: String, organism: String, plates: List<String>) {
        for (plate in plates) {
            val tag = "$organism-$plate"
            find(root, "book-figure-$tag")
            rule.assertPlateState(organism, plate)
            if (typeStudyPlateShips(organism, plate)) {
                find(root, "r2-plate-bitmap-$tag")
                rule.onNodeWithTag("r2-plate-bitmap-$tag").assertIsDisplayed()
                if (plateCreditListed("$organism/$plate")) find(root, "plate-credit-$tag")
            }
            rule.onAllNodesWithTag("r2-plate-bitmap-$tag").assertCountEquals(1)
        }
    }
    @Test fun obeliaBookChapterShowsAllFourPlatesInlineOnce() {
        val root = enter(2, "u2-obelia", true)
        inlineFigures(root, "obelia", listOf("colony", "hydranth-gonangium", "medusa", "life-cycle"))
        capture("u2-obelia-tamil")
        find(root, "u2-stage-explanation")
        rule.onNodeWithTag("r2-type-study-gallery-obelia").assertDoesNotExist()
        rule.onNodeWithTag("u2-obelia-colony-static-raster").assertDoesNotExist()
    }
    @Test fun fasciolaBookChapterShowsEachSourceOnceWithoutDuplicateGalleries() {
        val root = enter(3, "u3-fasciola", false)
        inlineFigures(root, "fasciola", listOf("external", "digestive-reproductive", "life-cycle"))
        capture("u3-fasciola-english")
        rule.onNodeWithTag("u3-required-plate-gallery").assertDoesNotExist()
        rule.onNodeWithTag("u3-static-pencil-raster").assertDoesNotExist()
        rule.onNodeWithTag("r2-type-study-gallery-fasciola").assertDoesNotExist()
    }
    @Test fun ascarisBookChapterShowsThreeIndependentSourcesWithoutDuplicateGalleries() {
        val root = enter(3, "u3-ascaris", true)
        inlineFigures(root, "ascaris", listOf("male-female-external", "female-internal", "life-cycle"))
        capture("u3-ascaris-tamil")
        rule.onNodeWithTag("u3-required-plate-gallery").assertDoesNotExist()
        rule.onNodeWithTag("u3-static-pencil-raster").assertDoesNotExist()
        rule.onNodeWithTag("r2-type-study-gallery-ascaris").assertDoesNotExist()
    }
}
