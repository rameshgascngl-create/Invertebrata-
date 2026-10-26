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

    @Test fun unitFivePilaTamilShowsFourPlateSlotsAndFullLesson() {
        val ch = "u5-pila"
        enter(5, ch, true)
        find(ch, "r2-type-study-gallery-pila")
        find(ch, "pila-museum-shell-bitmap")
        find(ch, "pila-museum-shell-source")
        find(ch, "pila-comparative-bitmap")
        // Rejected/unverified Pila editorial archive plates are no longer
        // inserted ahead of anatomy slots in the student textbook.
        rule.onNodeWithTag("pila-reference-review-warning").assertDoesNotExist()
        rule.onNodeWithTag("pila-review-image-01_shell_ventral_REVIEW").assertDoesNotExist()
        for (plate in listOf("external", "mantle-cavity", "ctenidium", "torsion")) {
            find(ch, "r2-plate-slot-pila-" + plate)
            rule.assertPlateState("pila", plate)
        }
        capture("unit5-pila-tamil-missing-plates")
        // At both 100% and 200% Tamil fonts, traverse EVERY substantive
        // Pila section; no earlier two-section preview may silently return.
        for (section in listOf(
            "external", "mantle", "organs", "dual-respiration",
            "radula-and-gut", "heart-and-haemocoel", "renal-and-sensory",
            "reproduction-and-development", "torsion-in-pila"
        )) {
            find(ch, "restored-section-" + section)
        }
        capture("unit5-pila-tamil-final-lesson")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u5-pila-A5-1")
    }

    @Test fun unitFiveAsteriasDisplaysFourLicensedSourcePlates() {
        val ch = "u5-asterias"
        enter(5, ch, false)
        find(ch, "r2-type-study-gallery-asterias")
        rule.onNodeWithTag("asterias-review-warning").assertDoesNotExist()
        rule.onNodeWithTag("asterias-review-image-water_vascular_REVIEW").assertDoesNotExist()
        // Four separately sourced plates must be present and attributed.
        for (plate in listOf("external", "water-vascular-system", "internal", "larval-development")) {
            find(ch, "r2-plate-bitmap-asterias-" + plate)
            rule.onNodeWithTag("r2-plate-missing-asterias-" + plate).assertDoesNotExist()
            find(ch, "asterias-plate-provenance-" + plate)
        }
        capture("unit5-asterias-english-source-plates")
        for (section in listOf(
            "external", "water-vascular", "internal", "feeding-and-stomachs",
            "body-wall-and-pedicellariae", "respiration-and-wastes",
            "nerve-and-sense", "reproduction-and-larvae"
        )) {
            find(ch, "restored-section-" + section)
        }
        capture("unit5-asterias-english-final-lesson")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u5-asterias-A5-1")
    }

    @Test fun unitFivePilaEnglishNavigatesLastChapterSectionWithoutRejectedImages() {
        val ch = "u5-pila"
        enter(5, ch, false)
        find(ch, "r2-type-study-gallery-pila")
        rule.onNodeWithTag("pila-review-image-01_shell_ventral_REVIEW").assertDoesNotExist()
        find(ch, "r2-plate-slot-pila-mantle-cavity")
        rule.assertPlateState("pila", "mantle-cavity")
        find(ch, "restored-section-reproduction-and-development")
        find(ch, "restored-section-torsion-in-pila")
        capture("unit5-pila-english-late-chapter")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u5-pila-A5-1")
    }

    @Test fun unitFiveAsteriasTamilNavigatesLastChapterSectionWithoutRejectedImages() {
        val ch = "u5-asterias"
        enter(5, ch, true)
        find(ch, "r2-type-study-gallery-asterias")
        rule.onNodeWithTag("asterias-review-image-water_vascular_REVIEW").assertDoesNotExist()
        find(ch, "r2-plate-bitmap-asterias-water-vascular-system")
        rule.onNodeWithTag("r2-plate-missing-asterias-water-vascular-system").assertDoesNotExist()
        find(ch, "asterias-plate-provenance-water-vascular-system")
        find(ch, "restored-section-feeding-and-stomachs")
        find(ch, "restored-section-reproduction-and-larvae")
        capture("unit5-asterias-tamil-late-chapter")
        find(ch, "restoration-a5-heading")
        find(ch, "a5-card-u5-asterias-A5-1")
    }

    @Test fun unitFourPenaeusShowsAppendagesSlotAndOriginalPractice() {
        val ch = "u4-penaeus"
        enter(4, ch, true)
        find(ch, "r2-type-study-gallery-penaeus")
        find(ch, "r2-plate-slot-penaeus-appendages")
        rule.assertPlateState("penaeus", "appendages")
        capture("unit4-penaeus-tamil-missing-appendages")
        find(ch, "u4-penaeus-a5-original-heading")
        find(ch, "a5-card-u4-penaeus-A5-1")
    }
}
