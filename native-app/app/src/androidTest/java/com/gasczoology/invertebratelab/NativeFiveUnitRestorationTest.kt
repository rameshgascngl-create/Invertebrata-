package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.semantics.SemanticsProperties
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

    private fun fullscreenPan(): Pair<Int, Int> {
        val value = rule.onNodeWithTag("r6-zoom-viewport-clipped")
            .fetchSemanticsNode().config[SemanticsProperties.StateDescription]
        val match = Regex("x=(-?\\d+);y=(-?\\d+)").matchEntire(value)
            ?: throw AssertionError("Missing pan translation semantics: " + value)
        return match.groupValues[1].toInt() to match.groupValues[2].toInt()
    }

    /**
     * Evidence MUST show the actual bitmap in a full-screen viewer, separately
     * for every Asterias plate and each language. A slot-presence assertion
     * alone can pass even if a screenshot shows only the last larval figure.
     * Runs at both 100% and 200% system font settings.
     */
    private fun captureAsteriasFullScreen(
        chapter: String, plate: String, language: String,
    ) {
        val button = "r2-plate-expand-asterias-" + plate
        find(chapter, button)
        rule.onNodeWithTag(button).performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertExists()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        rule.onNodeWithTag("r6-zoom-level")
            .assertTextContains("100%", substring = true)
        capture("unit5-asterias-" + language + "-" + plate + "-fullscreen-100pct")

        rule.onNodeWithTag("r2-zoom-in").performClick()
        rule.onNodeWithTag("r6-zoom-level")
            .assertTextContains("200%", substring = true)
        capture("unit5-asterias-" + language + "-" + plate + "-fullscreen-200pct")
        assertTrue("Image not centred before pan", fullscreenPan() == (0 to 0))

        // These are real touch swipes inside the magnified bitmap viewport,
        // NOT button-only tests. One axis may be constrained by aspect ratio.
        rule.onNodeWithTag("r6-zoom-viewport-clipped").performTouchInput { swipeLeft() }
        rule.onNodeWithTag("r6-zoom-viewport-clipped").performTouchInput { swipeUp() }
        rule.waitForIdle()
        val displacement = fullscreenPan()
        assertTrue(
            "Magnified ${plate} image did not pan: ${displacement}",
            displacement.first != 0 || displacement.second != 0,
        )
        capture("unit5-asterias-" + language + "-" + plate + "-fullscreen-panned-200pct")

        rule.onNodeWithTag("r2-zoom-out").performClick()
        rule.onNodeWithTag("r6-zoom-level")
            .assertTextContains("100%", substring = true)
        assertTrue("Zoom-out left image displaced", fullscreenPan() == (0 to 0))
        rule.onNodeWithTag("r2-zoom-reset").performClick()
        rule.onNodeWithTag("r6-zoom-level")
            .assertTextContains("100%", substring = true)
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertDoesNotExist()
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
        find(ch, "pila-museum-shell-bitmap")
        rule.onNodeWithTag("pila-museum-shell-not-imported").assertDoesNotExist()
        find(ch, "pila-museum-shell-limits")
        find(ch, "pila-museum-shell-source")
        find(ch, "pila-museum-shell-enlarge")
        rule.onNodeWithTag("pila-museum-shell-enlarge").performClick()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertExists()
        capture("unit5-pila-shell-museum-tamil")
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        // Rejected/unverified Pila editorial archive plates are no longer
        // inserted ahead of anatomy slots in the student textbook.
        rule.onNodeWithTag("pila-reference-review-warning").assertDoesNotExist()
        rule.onNodeWithTag("pila-review-image-01_shell_ventral_REVIEW").assertDoesNotExist()
        for (plate in listOf("external", "mantle-cavity", "ctenidium", "torsion")) {
            find(ch, "r2-plate-slot-pila-" + plate)
            rule.onNodeWithTag("r2-plate-missing-pila-" + plate).assertExists()
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

    @Test fun unitFiveAsteriasShowsFourDistinctLicensedAnatomyMasterPngs() {
        val ch = "u5-asterias"
        enter(5, ch, false)
        find(ch, "r2-type-study-gallery-asterias")
        rule.onNodeWithTag("asterias-review-warning").assertDoesNotExist()
        rule.onNodeWithTag("asterias-review-image-water_vascular_REVIEW").assertDoesNotExist()
        // All four images are now actual source-licensed student-facing figures.
        for (plate in listOf("external", "water-vascular-system", "internal", "larval-development")) {
            find(ch, "r2-plate-bitmap-asterias-" + plate)
            rule.onNodeWithTag("r2-plate-missing-asterias-" + plate).assertDoesNotExist()
        }
        // Capture each of the four plates in real full-screen modes, not a
        // single scrolled screenshot of the bottom larval plate.
        for (plate in listOf("external", "water-vascular-system", "internal", "larval-development")) {
            captureAsteriasFullScreen(ch, plate, "english")
        }
        find(ch, "asterias-external-species-provenance")
        find(ch, "asterias-wvs-legend")
        find(ch, "r2-plate-expand-asterias-water-vascular-system")
        rule.onNodeWithTag("r2-plate-expand-asterias-water-vascular-system").performClick()
        rule.onNodeWithTag("r2-fullscreen-dialog").assertExists()
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        find(ch, "asterias-internal-species-licence")
        find(ch, "asterias-larvae-two-stage-legend")
        find(ch, "r2-plate-bitmap-asterias-larval-development")
        capture("unit5-asterias-all-four-images-english")
        // Historical filenames preserved to avoid weakening the frozen
        // 100%/200% runner evidence inventory; the image is no longer missing.
        capture("unit5-asterias-english-missing-plate")
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
        find(ch, "pila-museum-shell-bitmap")
        find(ch, "pila-museum-shell-source")
        capture("unit5-pila-shell-museum-english")
        // Even after supplementary museum reference is added, compulsory
        // illustrated anatomy remains missing, not silently accepted.
        rule.onNodeWithTag("r2-plate-missing-pila-external").assertExists()
        rule.onNodeWithTag("pila-review-image-01_shell_ventral_REVIEW").assertDoesNotExist()
        find(ch, "r2-plate-missing-pila-mantle-cavity")
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
        for (plate in listOf("external", "water-vascular-system", "internal", "larval-development")) {
            find(ch, "r2-plate-bitmap-asterias-" + plate)
            rule.onNodeWithTag("r2-plate-missing-asterias-" + plate).assertDoesNotExist()
        }
        for (plate in listOf("external", "water-vascular-system", "internal", "larval-development")) {
            captureAsteriasFullScreen(ch, plate, "tamil")
        }
        find(ch, "asterias-wvs-legend")
        find(ch, "asterias-internal-species-licence")
        find(ch, "asterias-larvae-two-stage-legend")
        capture("unit5-asterias-all-four-images-tamil")
        find(ch, "restored-section-feeding-and-stomachs")
        find(ch, "restored-section-reproduction-and-larvae")
        capture("unit5-asterias-tamil-late-chapter")
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
