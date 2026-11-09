package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import com.gasczoology.invertebratelab.ui.ReadingPositionSemantics
import com.gasczoology.invertebratelab.ui.ReadingCanScrollForwardSemantics
import com.gasczoology.invertebratelab.ui.ReadingPositionRestoredSemantics
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

/** Reader continuity: chapter search, bookmarks, language switch and the shared figure viewer. Runs after the established functional class. */
class NativeReaderContinuityTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val args get() = InstrumentationRegistry.getArguments()
    private val tamil get() = args.getString("functionalLanguage") == "TAMIL"
    private val language get() = if (tamil) AppLanguage.TAMIL else AppLanguage.ENGLISH
    private val landscape get() = args.getString("functionalOrientation") == "landscape"
    private fun waitFor(tag: String) = rule.waitUntil(30_000) { rule.onAllNodesWithTag(tag, true).fetchSemanticsNodes().isNotEmpty() }

    @Before fun cleanLearnerPosition() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            BookReadingRepository(context).clear()
            BookmarkRepository(context).clear()
            LanguagePreferenceRepository(context).setLanguage(language)
        }
        rule.activityRule.scenario.recreate()
        rule.activityRule.scenario.onActivity {
            it.requestedOrientation = if (landscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        waitFor("native-home")
        rule.waitUntil(20_000) { rule.homeUnitOneShows(tamil) }
        rule.waitUntil(20_000) { context.resources.configuration.orientation == if (landscape) android.content.res.Configuration.ORIENTATION_LANDSCAPE else android.content.res.Configuration.ORIENTATION_PORTRAIT }
        val expected = args.getString("expectedScale")?.toFloat() ?: 1f
        assertEquals("Actual Android font scale", expected, context.resources.configuration.fontScale, .06f)
    }
    private fun enterChapter(unit: Int, id: String) {
        rule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("unit-$unit"))
        rule.onNodeWithTag("unit-$unit").performClick()
        waitFor("native-unit-$unit")
        rule.onNodeWithTag("native-unit-$unit").performScrollToNode(hasTestTag("chapter-$id"))
        rule.onNodeWithTag("chapter-$id").performClick()
        waitFor(root(id, unit))
    }
    private fun root(id: String, unit: Int) = when (unit) { 2 -> "u2-reader-$id"; 3 -> "u3-reader-$id"; else -> "book-reader-$id" }
    private fun openPage(id: String, unit: Int, page: String) {
        rule.onNodeWithTag("reading-contents").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("reading-open-$page"))
        rule.onNodeWithTag("reading-open-$page").performClick()
    }
    private fun screenshot(name: String, bitmap: Bitmap? = null) {
        val output = File(context.getExternalFilesDir(null), "functional-evidence").apply { mkdirs() }
        val image = bitmap ?: InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot()
        val file = File(output, "$name.png")
        file.outputStream().use { image.compress(Bitmap.CompressFormat.PNG, 100, it) }
        val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
        val target = "/sdcard/Download/native-functional-evidence/$name.png"
        android.os.ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand("mkdir -p /sdcard/Download/native-functional-evidence")).use { it.readBytes() }
        android.os.ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand("cp ${file.absolutePath} $target")).use { it.readBytes() }
        val bytes = android.os.ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand("cat $target")).use { it.readBytes() }
        assertTrue("Screenshot was not preserved: $target", bytes.size > 1000)
        if (bitmap == null) image.recycle()
    }

    /** The page id comes from the list's own semantics, so it is readable even when the header is scrolled out of view. */
    private fun pageId(): String = rule.onNodeWithTag(root("u1-paramecium", 1)).fetchSemanticsNode().config[ReadingPositionSemantics].pageId

    /** Polls the bookmark file and, on timeout, says what was actually stored. */
    private fun awaitBookmarks(label: String, ok: (Map<String, Set<String>>) -> Boolean) {
        val repo = BookmarkRepository(context)
        val deadline = System.currentTimeMillis() + 10_000
        var last: Map<String, Set<String>> = emptyMap()
        while (System.currentTimeMillis() < deadline) {
            last = runBlocking { repo.bookmarks.first() }
            if (ok(last)) return
            Thread.sleep(200)
        }
        fail("bookmarks $label: stored = $last")
    }

    /** Names the step in the failure message; Compose test errors do not carry a useful stack here. */
    private fun step(name: String, body: () -> Unit) {
        try { body() } catch (e: Throwable) { throw AssertionError("step '$name': ${e.message}", e) }
    }

    /** Search -> hit -> the reader opens the page that holds the block, with the block on screen. */
    @Test fun chapterSearchOpensTheMatchingBlockAndBookmarksPersist() {
        val unit = 1; val id = "u1-paramecium"
        enterChapter(unit, id)
        val book = BookChapters.forLesson(id)!!
        val corpus = ValidatedA5AssetRepository(context).load()
        val chapter = corpus.flatMap { it.chapters }.single { it.id == id }
        val pages = BookReadingPages.forChapter(book, chapter.a5Questions.map { it.id })
        val term = book.definitions.first().term.value(language)
        val expected = BookChapterSearch.search(book, pages, chapter.a5Questions, term, language)
        assertTrue("no expected hits for $term", expected.isNotEmpty())

        step("open contents") { rule.onNodeWithTag("reading-contents").performClick() }
        rule.waitForIdle()
        rule.onNodeWithTag("reading-search-field").performTextInput(term)
        rule.onNodeWithTag("reading-search-field").performImeAction()
        // On a small or heavily scaled viewport the results are below the fold and not yet composed.
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("reading-search-count"))
        rule.onNodeWithTag("reading-search-count").assertExists()
        screenshot("search-results-$id")
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("reading-search-hit-0"))
        step("click first hit") { rule.onNodeWithTag("reading-search-hit-0").performClick() }
        rule.waitUntil(10_000) { rule.onAllNodesWithTag("reading-page-id").fetchSemanticsNodes().isNotEmpty() }
        rule.waitUntil(10_000) { pageId() == expected[0].pageId }
        if (expected[0].rowIndex >= 0) rule.waitUntil(10_000) {
            rule.onAllNodesWithText(term, substring = true, ignoreCase = true).fetchSemanticsNodes().isNotEmpty()
        }
        screenshot("search-opened-$id")

        // Bookmark the opened page, leave, recreate, and find it again from the contents list.
        val marked = pageId()
        step("add bookmark") { rule.onNodeWithTag("reading-bookmark-add").performClick() }
        rule.onNodeWithTag("reading-bookmark-remove").assertExists()
        awaitBookmarks("after add of $marked") { it[id]?.contains(marked) == true }
        val onDisk = File(context.filesDir, "datastore/book_bookmarks.preferences_pb")
        assertTrue("bookmark file not written: ${onDisk.absolutePath}", onDisk.exists() && onDisk.length() > 0)
        rule.activityRule.scenario.recreate()
        waitFor(root(id, unit))
        rule.waitUntil(10_000) { rule.onAllNodesWithTag("reading-bookmark-remove").fetchSemanticsNodes().isNotEmpty() }
        step("open contents") { rule.onNodeWithTag("reading-contents").performClick() }
        rule.waitForIdle()
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("reading-bookmark-$marked"))
        screenshot("bookmarks-$id")
        step("open bookmark") { rule.onNodeWithTag("reading-bookmark-$marked").performClick() }
        rule.waitUntil(10_000) { pageId() == marked }

        // Removing it clears it from disk and from the list.
        step("remove bookmark") { rule.onNodeWithTag("reading-bookmark-remove").performClick() }
        awaitBookmarks("after remove of $marked") { it[id].isNullOrEmpty() }
        rule.onNodeWithTag("reading-bookmark-add").assertExists()

        // A nonsense query reports no match instead of staying silent.
        step("open contents") { rule.onNodeWithTag("reading-contents").performClick() }
        rule.waitForIdle()
        rule.onNodeWithTag("reading-search-field").performTextClearance()
        rule.onNodeWithTag("reading-search-field").performTextInput("zzqxjv")
        rule.onNodeWithTag("reading-search-field").performImeAction()
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("reading-search-empty"))
        rule.onNodeWithTag("reading-search-field").performTextClearance()
    }

    /** Page ids are language independent, so changing language keeps the reader on the same page. */
    @Test fun switchingLanguageKeepsTheSemanticPage() {
        val unit = 1; val id = "u1-paramecium"
        enterChapter(unit, id)
        openPage(id, unit, "row-0")
        rule.waitUntil(10_000) { pageId() == "row-0" }
        val other = if (language == AppLanguage.TAMIL) AppLanguage.ENGLISH else AppLanguage.TAMIL
        runBlocking { LanguagePreferenceRepository(context).setLanguage(other) }
        rule.waitUntil(15_000) { rule.onAllNodesWithText(if (other == AppLanguage.TAMIL) "பின்செல்" else "Back").fetchSemanticsNodes().isNotEmpty() }
        assertEquals("row-0", pageId())
        rule.onNodeWithTag("reading-page-position").assertExists()
        runBlocking { LanguagePreferenceRepository(context).setLanguage(language) }
    }

    /** One viewer for book figures: enlarge, zoom, survive rotation, close back to the same page. */
    @Test fun figureViewerZoomsSurvivesRecreationAndReturnsToTheSamePage() {
        val unit = 1; val id = "u1-paramecium"
        enterChapter(unit, id)
        val book = BookChapters.forLesson(id)!!
        val rows = BookLayout.rows(book)
        val chapter = ValidatedA5AssetRepository(context).load().flatMap { it.chapters }.single { it.id == id }
        val pages = BookReadingPages.forChapter(book, chapter.a5Questions.map { it.id })
        val figureRow = rows.indexOfFirst { (it as? BookRow.Block)?.block is BookFigure }
        assertTrue("chapter has no figure", figureRow >= 0)
        val figure = (rows[figureRow] as BookRow.Block).block as BookFigure
        val tag = figure.slot.replace('/', '-')
        val page = pages.first { figureRow in it.rowIndices }
        openPage(id, unit, page.id)
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("r2-plate-bitmap-$tag"))
        rule.onNodeWithTag("r2-plate-bitmap-$tag").performClick()
        rule.waitUntil(10_000) { rule.onAllNodesWithTag("r2-fullscreen-bitmap").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("100%", substring = true)
        rule.onNodeWithTag("r2-zoom-in").performClick()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("200%", substring = true)
        screenshot("figure-viewer-zoom-$tag")
        rule.activityRule.scenario.recreate()
        rule.waitUntil(15_000) { rule.onAllNodesWithTag("r2-fullscreen-bitmap").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("200%", substring = true)
        rule.onNodeWithTag("r2-zoom-reset").performClick()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("100%", substring = true)
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        rule.waitUntil(10_000) { rule.onAllNodesWithTag("r2-fullscreen-bitmap").fetchSemanticsNodes().isEmpty() }
        assertEquals(page.id, pageId())
        rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("book-figure-$tag"))
        rule.onNodeWithTag("book-figure-$tag").assertExists()
    }

}
