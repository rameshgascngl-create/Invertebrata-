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
    @Test fun pilaShowsBothRecoveredDrawingsButDoesNotClaimFourAcceptedAnatomyPlates() {
        val root = enter(5, "u5-pila", false)
        find(root, "pila-recovered-gallery")
        for (name in listOf("04_animal_front", "08_respiratory_system")) {
            find(root, "pila-recovered-$name")
            rule.onNodeWithTag("pila-recovered-$name").assertIsDisplayed()
        }
        for (plate in listOf("external", "mantle-cavity", "ctenidium", "torsion")) {
            find(root, "r2-plate-missing-pila-$plate")
        }
        capture("u5-pila-recovered-english")
    }

    @Test fun syconShowsRealCanalSystemImageAndTracksTwoMissingMasters() {
        val root = enter(2, "u2-sycon", false)
        find(root, "r2-type-study-gallery-sycon")
        for (plate in listOf("external","reproduction"))
            find(root, "r2-plate-missing-sycon-$plate")
        find(root, "r2-plate-bitmap-sycon-canal-system")
        rule.onNodeWithTag("r2-plate-bitmap-sycon-canal-system").assertIsDisplayed()
        capture("u2-sycon-english")
        find(root, "u2-stage-explanation")
        rule.onNodeWithTag("u2-sycon-historic-static-raster").assertDoesNotExist()
    }
    @Test fun obeliaShowsRealColonyAndLifeCycleImagesWithTwoPendingMasters() {
        val root = enter(2, "u2-obelia", true)
        find(root, "r2-type-study-gallery-obelia")
        for (plate in listOf("hydranth-gonangium","medusa"))
            find(root, "r2-plate-missing-obelia-$plate")
        find(root, "r2-plate-bitmap-obelia-colony")
        rule.onNodeWithTag("r2-plate-bitmap-obelia-colony").assertIsDisplayed()
        find(root, "r2-plate-bitmap-obelia-life-cycle")
        rule.onNodeWithTag("r2-plate-bitmap-obelia-life-cycle").assertIsDisplayed()
        capture("u2-obelia-tamil")
        find(root, "u2-stage-explanation")
        rule.onNodeWithTag("u2-obelia-colony-static-raster").assertDoesNotExist()
    }
    @Test fun fasciolaDisplaysEachDistinctSourceOnceWithoutDuplicateGalleries() {
        val root = enter(3, "u3-fasciola", false)
        find(root, "u3-required-plate-gallery")
        for (plate in listOf("external","digestive-reproductive","life-cycle")) {
            find(root, "u3-plate-bitmap-$plate")
            rule.onNodeWithTag("u3-plate-bitmap-$plate").assertIsDisplayed()
        }
        capture("u3-fasciola-english")
        find(root, "u3-no-duplicate-images")
        rule.onNodeWithTag("u3-static-pencil-raster").assertDoesNotExist()
        rule.onNodeWithTag("r2-type-study-gallery-fasciola").assertDoesNotExist()
    }
    @Test fun ascarisDisplaysThreeIndependentSourcesWithoutDuplicateGalleries() {
        val root = enter(3, "u3-ascaris", true)
        find(root, "u3-required-plate-gallery")
        for (plate in listOf("male-female-external","female-internal","life-cycle")) {
            find(root, "u3-plate-bitmap-$plate")
            rule.onNodeWithTag("u3-plate-bitmap-$plate").assertIsDisplayed()
        }
        capture("u3-ascaris-tamil")
        find(root, "u3-no-duplicate-images")
        rule.onNodeWithTag("u3-static-pencil-raster").assertDoesNotExist()
        rule.onNodeWithTag("r2-type-study-gallery-ascaris").assertDoesNotExist()
    }
}
