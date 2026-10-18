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

/**
 * R2 physical Android emulator acceptance: new textbook-first chapters and
 * fail-visible static anatomical image slots. An absent raster MUST be reported,
 * not accepted. Test devices run with the network disabled at 100%/200% fonts.
 */
@RunWith(AndroidJUnit4::class)
class NativeFiveUnitRestorationTest {
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

    private fun enter(unit: Int, chapter: String, tamil: Boolean) {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("native-home")
            .performScrollToNode(hasTestTag("unit-" + unit))
        rule.onNodeWithTag("unit-" + unit).performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-" + unit).fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-" + unit)
            .performScrollToNode(hasTestTag("chapter-" + chapter))
        rule.onNodeWithTag("chapter-" + chapter).performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-" + chapter)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun find(chapter: String, tag: String) {
        rule.onNodeWithTag("native-chapter-" + chapter)
            .performScrollToNode(hasTestTag(tag))
        rule.onNodeWithTag(tag).assertExists()
    }

    private fun capture(scene: String) {
        rule.waitForIdle()
        val inst = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val scale = inst.targetContext.resources.configuration.fontScale
        val suffix = if (scale >= 1.95f) "200" else "100"
        val file = "/sdcard/Download/five-unit-qa/r2-" + suffix + "-" + scene + ".png"
        val out = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("screencap -p " + file)
        ).use { it.readBytes() }
        val bytes = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("cat " + file)
        ).use { it.readBytes() }
        assertTrue("Empty screenshot: " + scene, out.isEmpty() && bytes.size > 12_000)
        val image = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertNotNull("Invalid screenshot: " + scene, image)
        assertTrue(image!!.height > image.width)
        image.recycle()
    }

    @Test fun unitOneProtozoaTeachesBeforeShowingA5() {
        val ch = "u1-protozoa"
        enter(1, ch, false)
        find(ch, "restoration-lesson-heading")
        rule.onNodeWithTag("restoration-lesson-title")
            .assertTextContains("Protozoa", substring = true)
        find(ch, "restored-section-classification")
        capture("unit1-protozoa-english")
        find(ch, "restored-section-cell")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u1-protozoa-A5-1")
    }

    @Test fun unitFivePilaTamilShowsFourMissingMastersAndFullLesson() {
        val ch = "u5-pila"
        enter(5, ch, true)
        find(ch, "r2-type-study-gallery-pila")
        find(ch, "pila-reference-review-warning")
        // The debug-only 12-panel archive must be extracted and display distinct images.
        // Verify all twelve decoded bitmaps, not only the first and last.
        for (file in listOf(
            "01_shell_ventral_REVIEW",
            "02_operculum_views_REVIEW",
            "03_shell_dorsal_REVIEW",
            "04_animal_front_REVIEW",
            "05_nervous_system_REVIEW",
            "06_buccal_mass_REVIEW",
            "07_digestive_system_REVIEW",
            "08_respiratory_system_REVIEW",
            "09_circulatory_system_REVIEW",
            "10_male_reproductive_REVIEW",
            "11_female_reproductive_REVIEW",
            "12_excretory_system_REVIEW"
        )) {
            find(ch, "pila-review-image-" + file)
            rule.onNodeWithTag("pila-review-missing-" + file).assertDoesNotExist()
        }
        for (plate in listOf("external", "mantle-cavity", "ctenidium", "torsion")) {
            find(ch, "r2-plate-slot-pila-" + plate)
            rule.onNodeWithTag("r2-plate-missing-pila-" + plate).assertExists()
        }
        capture("unit5-pila-tamil-missing-plates")
        find(ch, "restored-section-mantle")
        find(ch, "restored-section-organs")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u5-pila-A5-1")
    }

    @Test fun unitFiveAsteriasShowsWaterVascularMasterAsMissingNotCanvas() {
        val ch = "u5-asterias"
        enter(5, ch, false)
        find(ch, "r2-type-study-gallery-asterias")
        find(ch, "asterias-review-warning")
        // The four Asterias candidates must be bundled and actually decode on Android.
        for (file in listOf("external_REVIEW", "water_vascular_REVIEW", "digestive_REVIEW", "nervous_REVIEW")) {
            find(ch, "asterias-review-image-" + file)
            rule.onNodeWithTag("asterias-review-missing-" + file).assertDoesNotExist()
        }
        // The uploaded, source-preserving references must decode as separate bitmaps.
        // These do not satisfy mandatory textbook masters.
        for (file in listOf(
            "digestive_reference_ONLY",
            "water_vascular_reference_ONLY",
            "body_wall_reference_ONLY"
        )) {
            find(ch, "asterias-review-image-" + file)
            rule.onNodeWithTag("asterias-review-missing-" + file).assertDoesNotExist()
        }
        find(ch, "asterias-review-image-water_vascular_reference_ONLY")
        rule.onNodeWithTag("asterias-review-image-water_vascular_reference_ONLY").performClick()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        rule.onNodeWithTag("r2-zoom-in").performClick()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("200%", substring = true)
        capture("unit5-asterias-water-vascular-reference-zoom")
        rule.onNodeWithTag("r2-zoom-reset").performClick()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("100%", substring = true)
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        find(ch, "r2-plate-missing-asterias-water-vascular-system")
        capture("unit5-asterias-english-missing-plate")
        find(ch, "restored-section-water-vascular")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u5-asterias-A5-1")
    }

    @Test fun unitFourPenaeusShowsMissingAnatomicalMastersAndOriginalPractice() {
        val ch = "u4-penaeus"
        enter(4, ch, true)
        find(ch, "r2-type-study-gallery-penaeus")
        find(ch, "r2-plate-missing-penaeus-appendages")
        capture("unit4-penaeus-tamil-missing-appendages")
        find(ch, "u4-penaeus-a5-original-heading")
        find(ch, "a5-card-u4-penaeus-A5-1")
    }
}
