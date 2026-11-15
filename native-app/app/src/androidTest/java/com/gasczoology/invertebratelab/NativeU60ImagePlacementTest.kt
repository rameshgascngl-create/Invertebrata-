package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * U60 source-image routing through the current paginated textbook.
 * Unavailable raster art remains a visible hold; a rendered image does not
 * certify anatomical correctness or copyright clearance.
 */
@RunWith(AndroidJUnit4::class)
class NativeU60ImagePlacementTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before fun reset() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        await("native-home")
    }

    private fun await(tag: String) {
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enterUnit(tamil: Boolean) {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        await("native-unit-1")
    }

    private fun openChapter(id: String): String {
        rule.onNodeWithTag("native-unit-1").performScrollToNode(hasTestTag("chapter-" + id))
        rule.onNodeWithTag("chapter-" + id).performClick()
        val reader = "book-reader-" + id
        await(reader)
        return reader
    }

    /**
     * Derive the figure's logical page from the actual chapter rows, rather
     * than scrolling a retired all-in-one LazyColumn.
     */
    private fun openFigure(chapterId: String, reader: String, slot: String) {
        val book = requireNotNull(BookChapters.forLesson(chapterId))
        val rows = BookLayout.rows(book)
        val rowIndex = rows.indexOfFirst {
            val figure = (it as? BookRow.Block)?.block as? BookFigure
            figure?.slot == slot
        }
        assertTrue("Required figure missing from book corpus: " + slot, rowIndex >= 0)
        val chapter = ValidatedA5AssetRepository(context).load()
            .flatMap { it.chapters }.single { it.id == chapterId }
        val page = BookReadingPages.forChapter(book, chapter.a5Questions.map { it.id })
            .single { rowIndex in it.rowIndices }
        openReadingPage(rule, reader, page.id)
        val tag = "book-figure-" + slot.replace('/', '-')
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag(tag))
        rule.onNodeWithTag(tag).assertExists()
        val suffix = slot.replace('/', '-')
        val bitmap = rule.onAllNodesWithTag("r2-plate-bitmap-" + suffix).fetchSemanticsNodes().size
        val missing = rule.onAllNodesWithTag("r2-plate-missing-" + suffix).fetchSemanticsNodes().size
        assertEquals("Figure must be rendered or transparently marked missing: " + slot,
            1, bitmap + missing)
    }

    private fun shot(scene: String) {
        val inst = InstrumentationRegistry.getInstrumentation()
        val scale = if (inst.targetContext.resources.configuration.fontScale >= 1.95f) "200" else "100"
        val path = "/sdcard/Download/u60-qa/u60-" + scale + "-" + scene + ".png"
        ParcelFileDescriptor.AutoCloseInputStream(inst.uiAutomation.executeShellCommand(
            "screencap -p " + path)).use { it.readBytes() }
        val data = ParcelFileDescriptor.AutoCloseInputStream(inst.uiAutomation.executeShellCommand(
            "cat " + path)).use { it.readBytes() }
        assertTrue("Screenshot missing", data.size > 12000)
        val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
        assertNotNull(bmp)
        assertTrue(bmp!!.height > bmp.width)
        bmp.recycle()
    }

    @Test fun unitOverviewKeepsEntamoebaPlasmodiumButNotWithdrawnFlagellateCartoons() {
        enterUnit(false)
        rule.onNodeWithTag("native-unit-1").performScrollToNode(
            hasTestTag("u1-protozoa-reference-toggle"))
        rule.onNodeWithTag("u1-protozoa-reference-toggle").performClick()
        rule.onNodeWithTag("u1-reference-select-entamoeba").assertExists()
        rule.onNodeWithTag("u1-reference-select-plasmodium").assertExists()
        rule.onNodeWithTag("u1-reference-select-trypanosoma").assertDoesNotExist()
        rule.onNodeWithTag("u1-reference-select-leishmania").assertDoesNotExist()
        shot("reference-scope")
    }

    @Test fun parasiteBookKeepsDistinctFigureSlotsAndTamilLifeCycles() {
        enterUnit(tamil = true)
        val id = "u1-proto-parasites"
        val reader = openChapter(id)
        val trypanosoma = "lib/trypanosoma-cartoon"
        val leishmania = "lib/leishmania-amastigote"
        assertNotEquals(BookFigureLibrary.stemFor(trypanosoma), BookFigureLibrary.stemFor(leishmania))
        openFigure(id, reader, trypanosoma)
        shot("trypanosoma-figure-status")
        openFigure(id, reader, leishmania)
        shot("leishmania-figure-status")
        openReadingPage(rule, reader, "summary")
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("book-summary"))
        rule.onNodeWithTag("book-summary").assert(
            hasAnyDescendant(hasText("அமாஸ்டிகோட்", substring = true))
        )
        openReadingPage(rule, reader, "trypanosoma")
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("u18-cycle-trypanosoma"))
        rule.onNodeWithTag("u18-title-trypanosoma").assertExists()
        openReadingPage(rule, reader, "leishmania")
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("u18-cycle-leishmania"))
        rule.onNodeWithTag("u18-title-leishmania").assertExists()
    }

    @Test fun parameciumBookHasFourIndependentFiguresAndOriginalA5() {
        enterUnit(tamil = true)
        val id = "u1-paramecium"
        val reader = openChapter(id)
        for (plate in listOf("external-cilia", "oral-apparatus",
                "contractile-vacuoles", "locomotion")) {
            openFigure(id, reader, "paramecium/" + plate)
            rule.assertPlateState("paramecium", plate)
        }
        shot("paramecium-four-book-figures")
        val question = ValidatedA5AssetRepository(context).load().single { it.number == 1 }
            .chapters.single { it.id == id }.a5Questions.first()
        openReadingPage(rule, reader, "practice-" + question.id)
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("a5-question-" + question.id))
        rule.onNodeWithTag("a5-question-" + question.id).assertTextEquals(question.question.tamil)
    }
}
