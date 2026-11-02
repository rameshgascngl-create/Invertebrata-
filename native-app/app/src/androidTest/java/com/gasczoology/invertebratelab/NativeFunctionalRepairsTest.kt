package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

/** Exercises default pages and actual playback from the student-facing routes. */
class NativeFunctionalRepairsTest {
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

    @Test fun defaultPagesAcrossAllFiveUnitsKeepPositionContentsAndExactAnswers() {
        val corpus = ValidatedA5AssetRepository(context).load()
        listOf(1 to "u1-paramecium", 2 to "u2-sycon", 3 to "u3-fasciola", 4 to "u4-penaeus", 5 to "u5-pila").forEach { (unit, id) ->
            enterChapter(unit, id)
            rule.onNodeWithTag("reading-page-previous").assertIsNotEnabled()
            rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("book-objectives"))
            rule.onNodeWithTag("book-objectives").assertExists()
            rule.onNodeWithTag("reading-page-next").performClick()
            rule.onNodeWithTag("book-objectives").assertDoesNotExist()
            rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("book-introduction"))
            rule.onNodeWithTag("book-introduction").assertExists()
            runBlocking { withTimeout(10_000) { BookReadingRepository(context).positions.first { it[id]?.pageId == "introduction-0" } } }
            val beforeScroll = rule.onNodeWithTag(root(id, unit)).fetchSemanticsNode()
                .config[SemanticsProperties.VerticalScrollAxisRange].value()
            rule.activityRule.scenario.recreate()
            waitFor(root(id, unit))
            rule.waitUntil(10_000) {
                kotlin.math.abs(rule.onNodeWithTag(root(id, unit)).fetchSemanticsNode()
                    .config[SemanticsProperties.VerticalScrollAxisRange].value() - beforeScroll) < .0001f
            }
            // Restoring the reading offset can leave the lazy header offscreen.
            rule.onNodeWithTag(root(id, unit)).performScrollToIndex(0)
            rule.onNodeWithTag("reading-page-id").assertContentDescriptionEquals("introduction-0")
            screenshot("$id-restored-page")
            openPage(id, unit, "row-0")
            val book = BookChapters.forLesson(id)!!
            val first = BookLayout.rows(book).filterIsInstance<BookRow.SectionHeading>().first()
            rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag("book-section-" + first.number))
            rule.onNodeWithTag("book-section-" + first.number).assertTextEquals(first.number + "  " + first.heading.value(language))
            val chapter = corpus.flatMap { it.chapters }.single { it.id == id }
            val question = chapter.a5Questions.last()
            openPage(id, unit, "practice-" + question.id)
            val qTag = (if (unit == 3) "u3-question-" else "a5-question-") + question.id
            rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag(qTag))
            rule.onNodeWithTag(qTag).assertTextEquals(question.question.value(language))
            rule.onNodeWithTag("a5-toggle-" + question.id).performScrollTo().performClick()
            question.answerPoints.forEachIndexed { at, point ->
                val tag = "a5-point-${question.id}-${at + 1}"
                rule.onNodeWithTag(root(id, unit)).performScrollToNode(hasTestTag(tag))
                rule.onNodeWithTag(tag).assertTextEquals("${at + 1}. " + point.value(language))
            }
            screenshot("$id-answer")
            rule.onNodeWithTag("reading-page-next").assertIsNotEnabled()
            rule.onNodeWithTag(when (unit) { 2 -> "u2-back"; 3 -> "u3-back"; else -> "book-back" }).performClick()
            waitFor("native-unit-$unit")
            rule.onNodeWithTag("native-unit-$unit").performScrollToIndex(0)
            rule.onNodeWithText(if (tamil) "பின்செல்" else "Back").performClick()
            waitFor("native-home")
        }
    }

    @Test fun parasiteAndPrawnInteractivePagesAreReachable() {
        enterChapter(1, "u1-proto-parasites")
        for (organism in listOf("trypanosoma", "leishmania")) {
            openPage("u1-proto-parasites", 1, organism)
            rule.onNodeWithTag(root("u1-proto-parasites", 1)).performScrollToNode(hasTestTag("u18-next-$organism"))
            rule.onNodeWithTag("u18-next-$organism").performClick()
            rule.onNodeWithTag("u18-stage-counter-$organism").assertTextEquals(if (tamil) "நிலை 2 / " + UnitIFlagellateLifeCycles.forOrganism(organism)!!.phases.size else "Stage 2 of " + UnitIFlagellateLifeCycles.forOrganism(organism)!!.phases.size)
            screenshot("$organism-interactive")
        }
        rule.onNodeWithTag("book-back").performClick(); waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(0)
        rule.onNodeWithText(if (tamil) "பின்செல்" else "Back").performClick(); waitFor("native-home")
        enterChapter(4, "u4-penaeus")
        openPage("u4-penaeus", 4, "appendages")
        rule.onNodeWithTag(root("u4-penaeus", 4)).performScrollToNode(hasTestTag("u4-penaeus-appendage-next"))
        rule.onNodeWithTag("u4-penaeus-appendage-next").performClick()
        rule.onNodeWithTag("u4-penaeus-appendage-name").performScrollTo().assertExists()
        openPage("u4-penaeus", 4, "larvae")
        rule.onNodeWithTag(root("u4-penaeus", 4)).performScrollToNode(hasTestTag("u4-penaeus-life-next"))
        rule.onNodeWithTag("u4-penaeus-life-next").performClick()
        screenshot("penaeus-interactive")
    }

    private fun chooseProcess(process: String) {
        rule.onNodeWithTag("r1-process-selector").performScrollTo()
        rule.onNodeWithTag("r1-process-$process").performScrollTo().performClick()
    }
    private fun changedPixels(a: Bitmap, b: Bitmap): Int {
        assertEquals(a.width, b.width); assertEquals(a.height, b.height)
        var count = 0
        for (y in 0 until a.height step 2) for (x in 0 until a.width step 2) if (a.getPixel(x, y) != b.getPixel(x, y)) count++
        return count
    }
    private fun settledPausedImage(canvas: SemanticsNodeInteraction, expectedPhase: Float): Bitmap {
        val deadline = android.os.SystemClock.elapsedRealtime() + 1_500
        var previous = canvas.captureToImage().asAndroidBitmap()
        while (android.os.SystemClock.elapsedRealtime() < deadline) {
            Thread.sleep(75)
            val actualPhase = canvas.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
            assertTrue("Playback advanced after Pause: $expectedPhase -> $actualPhase",
                kotlin.math.abs(actualPhase - expectedPhase) <= .0011f)
            val next = canvas.captureToImage().asAndroidBitmap()
            if (previous.sameAs(next)) { previous.recycle(); return next }
            previous.recycle()
            previous = next
        }
        previous.recycle()
        throw AssertionError("The paused diagram never reached a stable rendered frame")
    }

    @Test fun allFiveSimulatorsMoveRealDiagramPixelsAndPause() {
        rule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("r1-home-open-paramecium"))
        rule.onNodeWithTag("r1-home-open-paramecium").performClick(); waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-tab-simulate").performScrollTo().performClick()
        data class Case(val process: String, val prefix: String, val canvas: String, val stage: String, val start: String?)
        val cases = listOf(
            Case("osmoregulation", "r18", "r18-water-canvas", "r18-stage-fill", "r18-start-view"),
            Case("ciliary_motion", "ciliary", "ciliary-playback-diagram", "ciliary-playback-select-effective", null),
            Case("feeding", "r19", "r19-nutrition-canvas", "r19-stage-formation", "r19-start-view"),
            Case("binary_fission", "r17", "r17-nuclear-canvas", "r17-stage-constriction", null),
            Case("conjugation", "r17", "r17-nuclear-canvas", "r17-stage-exchange", null),
        )
        for (c in cases) {
            chooseProcess(c.process)
            rule.onNodeWithTag(c.stage, true).performScrollTo().performClick()
            c.start?.let { rule.onNodeWithTag(it, true).performScrollTo().performClick() }
            val canvas = rule.onNodeWithTag(c.canvas, true).performScrollTo()
            rule.onNodeWithTag("${c.prefix}-inline-play-pause").assertIsDisplayed()
            val before = canvas.captureToImage().asAndroidBitmap()
            rule.onNodeWithTag("${c.prefix}-inline-play-pause").performClick()
            rule.waitUntil(10_000) { canvas.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .18f.. .8f }
            rule.onNodeWithTag("${c.prefix}-inline-play-pause").performClick()
            val phase = canvas.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
            assertTrue("${c.process}: playback did not pause at an intermediate phase: $phase", phase > 0f && phase < 1f)
            val after = settledPausedImage(canvas, phase)
            val changed = changedPixels(before, after)
            assertTrue("${c.process}: Play did not change the diagram ($changed sampled pixels)", changed > 12)
            screenshot("${c.process}-start", before); screenshot("${c.process}-paused", after)
            Thread.sleep(300)
            val paused = canvas.captureToImage().asAndroidBitmap()
            assertTrue("${c.process}: pixels moved after Pause", after.sameAs(paused))
            before.recycle(); after.recycle(); paused.recycle()
        }
    }
}
