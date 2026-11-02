package com.gasczoology.invertebratelab

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.File

/** Checks the installed R8 APK using platform accessibility, without Compose test hooks. */
@RunWith(AndroidJUnit4::class)
class NativeReleaseFunctionalTest {
    private val instrumentation get() = InstrumentationRegistry.getInstrumentation()
    private val context get() = instrumentation.targetContext
    private val automation get() = instrumentation.uiAutomation
    private val args get() = InstrumentationRegistry.getArguments()
    private val tamil get() = args.getString("functionalLanguage") == "TAMIL"
    private val language get() = if (tamil) AppLanguage.TAMIL else AppLanguage.ENGLISH
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before fun launchCleanApplication() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            BookReadingRepository(context).clear()
            LanguagePreferenceRepository(context).setLanguage(language)
        }
        val info = checkNotNull(automation.serviceInfo)
        info.flags = info.flags or AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
            AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS or AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        automation.serviceInfo = info
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { it.requestedOrientation =
            if (args.getString("functionalOrientation") == "landscape")
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        waitFor("native-home").recycle()
        assertEquals(args.getString("expectedScale")?.toFloat() ?: 1f, context.resources.configuration.fontScale, .06f)
    }
    @After fun closeApplication() { if (::scenario.isInitialized) scenario.close() }

    private fun search(predicate: (AccessibilityNodeInfo) -> Boolean): AccessibilityNodeInfo? {
        fun visit(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            if (predicate(node)) return node
            for (at in 0 until node.childCount) {
                val found = node.getChild(at)?.let(::visit)
                if (found != null) { node.recycle(); return found }
            }
            node.recycle()
            return null
        }
        return automation.rootInActiveWindow?.let(::visit)
    }
    private fun find(id: String, visible: Boolean = true) = search {
        (it.viewIdResourceName == id || it.viewIdResourceName?.endsWith("/" + id) == true) && (!visible || it.isVisibleToUser)
    }
    private fun waitFor(id: String): AccessibilityNodeInfo {
        val until = SystemClock.elapsedRealtime() + 20_000
        while (SystemClock.elapsedRealtime() < until) {
            find(id)?.let { return it }
            SystemClock.sleep(75)
        }
        runCatching { saveScreen("failure-" + id) }
        throw AssertionError("Android accessibility node not found: " + id)
    }
    private fun largestScroller(): AccessibilityNodeInfo? {
        var best: AccessibilityNodeInfo? = null
        var height = 0
        fun visit(node: AccessibilityNodeInfo) {
            val rect = Rect(); node.getBoundsInScreen(rect)
            if (node.isScrollable && rect.height() > height) {
                best?.recycle(); best = AccessibilityNodeInfo.obtain(node); height = rect.height()
            }
            for (i in 0 until node.childCount) node.getChild(i)?.let { visit(it); it.recycle() }
        }
        automation.rootInActiveWindow?.let { visit(it); it.recycle() }
        return best
    }
    private fun scroll(action: Int): Boolean {
        val node = largestScroller() ?: return false
        val moved = node.performAction(action); node.recycle()
        SystemClock.sleep(100)
        return moved
    }
    private fun horizontal(container: String, id: String): AccessibilityNodeInfo {
        scrollTo(container).recycle()
        for (direction in listOf(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD, AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
            for (attempt in 0 until 18) {
                find(id)?.let { return it }
                val row = find(container) ?: break
                val moved = row.performAction(direction); row.recycle()
                SystemClock.sleep(90)
                if (!moved) break
            }
        }
        return waitFor(id)
    }
    private fun scrollTo(id: String): AccessibilityNodeInfo {
        find(id)?.let { return it }
        if (id.startsWith("r1-tab-")) return horizontal("r1-module-selector", id)
        if (id.startsWith("r1-process-") && id != "r1-process-selector") return horizontal("r1-process-selector", id)
        val directions = if (id == "r1-process-selector")
            listOf(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD, AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
        else listOf(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD, AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
        for (direction in directions) for (attempt in 0 until 55) {
            find(id)?.let { return it }
            find(id, false)?.let { it.performAction(AccessibilityNodeInfo.ACTION_SHOW_ON_SCREEN); it.recycle(); SystemClock.sleep(75) }
            find(id)?.let { return it }
            if (!scroll(direction)) break
        }
        return waitFor(id)
    }
    private fun click(node: AccessibilityNodeInfo, label: String) {
        var target = node
        while (!target.isClickable) {
            val parent = target.parent ?: break
            target.recycle(); target = parent
        }
        val clicked = target.performAction(AccessibilityNodeInfo.ACTION_CLICK); target.recycle()
        assertTrue("Click failed: " + label, clicked)
        SystemClock.sleep(80)
    }
    private fun tap(id: String) = click(scrollTo(id), id)
    private fun text(id: String): String {
        val node = scrollTo(id); val value = node.text?.toString().orEmpty(); node.recycle(); return value
    }
    private fun root(id: String, unit: Int) = when (unit) {
        2 -> "u2-reader-" + id; 3 -> "u3-reader-" + id; else -> "book-reader-" + id
    }
    private fun enterChapter(unit: Int, id: String) {
        tap("unit-" + unit); waitFor("native-unit-" + unit).recycle()
        tap("chapter-" + id); waitFor(root(id, unit)).recycle()
    }
    private fun openPage(id: String) { tap("reading-contents"); tap("reading-open-" + id) }
    private fun backToHome(unit: Int) {
        tap(when (unit) { 2 -> "u2-back"; 3 -> "u3-back"; else -> "book-back" })
        waitFor("native-unit-" + unit).recycle()
        val back = if (tamil) "பின்செல்" else "Back"
        for (attempt in 0 until 45) {
            search { it.isVisibleToUser && it.text?.toString() == back }?.let {
                click(it, back); waitFor("native-home").recycle(); return
            }
            if (!scroll(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) break
        }
        throw AssertionError("Unit Back button was not reachable")
    }
    private fun saveScreen(name: String, supplied: Bitmap? = null) {
        val bitmap = supplied ?: checkNotNull(automation.takeScreenshot())
        val file = File(context.getExternalFilesDir(null), name + ".png")
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        val destination = "/sdcard/Download/native-functional-evidence/" + name + ".png"
        ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand("cp " + file.absolutePath + " " + destination)).use { it.readBytes() }
        if (supplied == null) bitmap.recycle()
    }
    private fun phase(id: String): Float {
        val node = waitFor(id)
        val result = checkNotNull(node.rangeInfo) { "Diagram has no Android range semantics: " + id }.current
        node.recycle(); return result
    }
    private fun diagram(id: String): Bitmap {
        val node = waitFor(id); val bounds = Rect(); node.getBoundsInScreen(bounds)
        var ancestor = node.parent
        while (ancestor != null) {
            if (ancestor.isScrollable) { val clip = Rect(); ancestor.getBoundsInScreen(clip); bounds.intersect(clip) }
            val next = ancestor.parent; ancestor.recycle(); ancestor = next
        }
        node.recycle()
        val screen = checkNotNull(automation.takeScreenshot())
        bounds.intersect(0, 0, screen.width, screen.height)
        assertTrue("Diagram is outside the viewport", bounds.width() > 20 && bounds.height() > 20)
        val region = Bitmap.createBitmap(screen, bounds.left, bounds.top, bounds.width(), bounds.height())
        val result = checkNotNull(region.copy(Bitmap.Config.ARGB_8888, false))
        if (region !== screen) region.recycle()
        screen.recycle()
        return result
    }
    private fun changedPixels(a: Bitmap, b: Bitmap): Int {
        assertEquals(a.width, b.width); assertEquals(a.height, b.height)
        var changed = 0
        for (y in 0 until a.height step 2) for (x in 0 until a.width step 2)
            if (a.getPixel(x, y) != b.getPixel(x, y)) changed++
        return changed
    }

    @Test fun defaultPagesAcrossAllFiveUnitsKeepPositionContentsAndExactAnswers() {
        val corpus = ValidatedA5AssetRepository(context).load()
        for ((unit, id) in listOf(1 to "u1-paramecium", 2 to "u2-sycon", 3 to "u3-fasciola", 4 to "u4-penaeus", 5 to "u5-pila")) {
            enterChapter(unit, id)
            val previous = waitFor("reading-page-previous"); assertFalse(previous.isEnabled); previous.recycle()
            scrollTo("book-objectives").recycle(); tap("reading-page-next")
            scrollTo("book-introduction").recycle()
            val page = text("reading-page-position")
            scenario.recreate(); waitFor(root(id, unit)).recycle()
            assertEquals(page, text("reading-page-position"))
            saveScreen(id + "-release-restored-page")
            openPage("row-0")
            val first = BookLayout.rows(BookChapters.forLesson(id)!!).filterIsInstance<BookRow.SectionHeading>().first()
            assertEquals(first.number + "  " + first.heading.value(language), text("book-section-" + first.number))
            val question = corpus.flatMap { it.chapters }.single { it.id == id }.a5Questions.last()
            openPage("practice-" + question.id)
            assertEquals(question.question.value(language), text((if (unit == 3) "u3-question-" else "a5-question-") + question.id))
            tap("a5-toggle-" + question.id)
            question.answerPoints.forEachIndexed { index, point ->
                assertEquals((index + 1).toString() + ". " + point.value(language), text("a5-point-" + question.id + "-" + (index + 1)))
            }
            saveScreen(id + "-release-answer")
            val next = waitFor("reading-page-next"); assertFalse(next.isEnabled); next.recycle()
            backToHome(unit)
        }
    }

    @Test fun parasiteAndPrawnInteractivePagesAreReachable() {
        enterChapter(1, "u1-proto-parasites")
        for (organism in listOf("trypanosoma", "leishmania")) {
            openPage(organism); tap("u18-next-" + organism)
            val count = UnitIFlagellateLifeCycles.forOrganism(organism)!!.phases.size
            assertEquals(if (tamil) "நிலை 2 / " + count else "Stage 2 of " + count, text("u18-stage-counter-" + organism))
            saveScreen(organism + "-release-interactive")
        }
        backToHome(1)
        enterChapter(4, "u4-penaeus")
        openPage("appendages"); tap("u4-penaeus-appendage-next")
        assertTrue(text("u4-penaeus-appendage-name").isNotBlank())
        openPage("larvae"); tap("u4-penaeus-life-next")
        saveScreen("penaeus-release-interactive")
    }

    @Test fun allFiveSimulatorsMoveRealDiagramPixelsAndPause() {
        tap("r1-home-open-paramecium"); waitFor("r1-paramecium-lab").recycle(); tap("r1-tab-simulate")
        data class Case(val process: String, val prefix: String, val canvas: String, val stage: String, val start: String?)
        val cases = listOf(
            Case("osmoregulation", "r18", "r18-water-canvas", "r18-stage-fill", "r18-start-view"),
            Case("ciliary_motion", "ciliary", "ciliary-playback-diagram", "ciliary-playback-select-effective", null),
            Case("feeding", "r19", "r19-nutrition-canvas", "r19-stage-formation", "r19-start-view"),
            Case("binary_fission", "r17", "r17-nuclear-canvas", "r17-stage-constriction", null),
            Case("conjugation", "r17", "r17-nuclear-canvas", "r17-stage-exchange", null),
        )
        for (c in cases) {
            tap("r1-process-" + c.process); tap(c.stage); c.start?.let(::tap)
            scrollTo(c.prefix + "-inline-play-pause").recycle()
            val before = diagram(c.canvas)
            tap(c.prefix + "-inline-play-pause")
            val until = SystemClock.elapsedRealtime() + 8_000
            while (phase(c.canvas) !in .18f.. .8f && SystemClock.elapsedRealtime() < until) SystemClock.sleep(30)
            tap(c.prefix + "-inline-play-pause"); SystemClock.sleep(150)
            val stopped = phase(c.canvas); assertTrue(c.process + ": no intermediate pause", stopped > 0f && stopped < 1f)
            val after = diagram(c.canvas)
            assertTrue(c.process + ": diagram pixels did not change", changedPixels(before, after) > 12)
            saveScreen(c.process + "-release-start", before); saveScreen(c.process + "-release-paused", after)
            SystemClock.sleep(300)
            assertEquals(c.process + ": phase advanced after Pause", stopped, phase(c.canvas), .0011f)
            val paused = diagram(c.canvas)
            assertTrue(c.process + ": pixels moved after Pause", after.sameAs(paused))
            before.recycle(); after.recycle(); paused.recycle()
        }
    }
}
