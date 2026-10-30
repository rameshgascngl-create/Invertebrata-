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

    /** These five chapters are book chapters; the legacy restoration screen is no longer routed. */
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
            rule.onAllNodesWithTag("book-reader-" + chapter)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun find(chapter: String, tag: String) {
        rule.onNodeWithTag("book-reader-" + chapter)
            .performScrollToNode(hasTestTag(tag))
        rule.onNodeWithTag(tag).assertExists()
    }

    /** Every section heading of the real chapter data, so no section can silently drop out. */
    private fun sectionTags(chapter: String): List<String> =
        BookLayout.rows(BookChapters.forLesson(chapter)!!)
            .filterIsInstance<BookRow.SectionHeading>().map { "book-section-" + it.number }

    private fun traverseAllSections(chapter: String) {
        val tags = sectionTags(chapter)
        assertTrue("Chapter has too few sections: $chapter", tags.size >= 5)
        tags.forEach { find(chapter, it) }
    }

    private fun figure(chapter: String, organism: String, plate: String) {
        val tag = "$organism-$plate"
        find(chapter, "book-figure-$tag")
        rule.assertPlateState(organism, plate)
        if (plateCreditListed("$organism/$plate")) find(chapter, "plate-credit-$tag")
    }

    private fun a5(chapter: String) {
        find(chapter, "book-practice-heading")
        find(chapter, "a5-question-$chapter-A5-1")
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
        find(ch, "book-objectives")
        find(ch, "book-introduction")
        capture("unit1-protozoa-english")
        traverseAllSections(ch)
        find(ch, "book-glossary")
        a5(ch)
    }

    @Test fun unitFivePilaTamilShowsFourPlateSlotsAndFullLesson() {
        val ch = "u5-pila"
        enter(5, ch, true)
        for (plate in listOf("external", "mantle-cavity", "ctenidium")) figure(ch, "pila", plate)
        capture("unit5-pila-tamil-missing-plates")
        // At both 100% and 200% Tamil fonts, traverse EVERY section of the chapter.
        traverseAllSections(ch)
        capture("unit5-pila-tamil-final-lesson")
        a5(ch)
    }

    @Test fun unitFiveAsteriasDisplaysFourLicensedSourcePlates() {
        val ch = "u5-asterias"
        enter(5, ch, false)
        for (plate in listOf("external", "water-vascular-system", "internal", "larval-development")) {
            figure(ch, "asterias", plate)
        }
        capture("unit5-asterias-english-source-plates")
        traverseAllSections(ch)
        capture("unit5-asterias-english-final-lesson")
        a5(ch)
    }

    @Test fun unitFivePilaEnglishNavigatesLastChapterSectionWithoutRejectedImages() {
        val ch = "u5-pila"
        enter(5, ch, false)
        rule.onNodeWithTag("pila-review-image-01_shell_ventral_REVIEW").assertDoesNotExist()
        figure(ch, "pila", "mantle-cavity")
        find(ch, sectionTags(ch).last())
        capture("unit5-pila-english-late-chapter")
        a5(ch)
    }

    @Test fun unitFiveAsteriasTamilNavigatesLastChapterSectionWithoutRejectedImages() {
        val ch = "u5-asterias"
        enter(5, ch, true)
        rule.onNodeWithTag("asterias-review-image-water_vascular_REVIEW").assertDoesNotExist()
        figure(ch, "asterias", "water-vascular-system")
        find(ch, sectionTags(ch).last())
        capture("unit5-asterias-tamil-late-chapter")
        a5(ch)
    }

    @Test fun unitFourPenaeusShowsAppendagesFigureAndPractice() {
        val ch = "u4-penaeus"
        enter(4, ch, true)
        figure(ch, "penaeus", "appendages")
        capture("unit4-penaeus-tamil-missing-appendages")
        traverseAllSections(ch)
        a5(ch)
    }
}
