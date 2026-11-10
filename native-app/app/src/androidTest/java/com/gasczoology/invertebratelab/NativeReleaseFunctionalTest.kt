package com.gasczoology.invertebratelab

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityEvent
import java.util.concurrent.atomic.AtomicLong
import android.view.MotionEvent
import android.view.InputDevice
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
    private val verticalScrollEvents = AtomicLong()

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
        automation.setOnAccessibilityEventListener { event ->
            if (event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED && event.maxScrollY > 0) verticalScrollEvents.incrementAndGet()
        }
        scenario = ActivityScenario.launch(MainActivity::class.java)
        val landscape = args.getString("functionalOrientation") == "landscape"
        scenario.onActivity {
            it.requestedOrientation = if (landscape)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        // Orientation recreates MainActivity on API 34. A stale Home node can appear
        // before the new Activity has a stable accessibility window.
        val readyBy = SystemClock.elapsedRealtime() + 15_000
        var stableSince = 0L
        var homeReady = false
        while (SystemClock.elapsedRealtime() < readyBy) {
            if (android.os.Build.VERSION.SDK_INT >= 34) automation.clearCache()
            val window = automation.rootInActiveWindow
            val rect = Rect()
            window?.getBoundsInScreen(rect)
            window?.recycle()
            val correctOrientation = rect.width() > 20 && rect.height() > 20 &&
                (if (landscape) rect.width() > rect.height() else rect.height() > rect.width())
            val home = find("native-home")
            val ready = correctOrientation && home != null &&
                scenario.state == androidx.lifecycle.Lifecycle.State.RESUMED
            home?.recycle()
            if (ready) {
                if (stableSince == 0L) stableSince = SystemClock.elapsedRealtime()
                if (SystemClock.elapsedRealtime() - stableSince >= 750L) { homeReady = true; break }
            } else stableSince = 0L
            SystemClock.sleep(100)
        }
        assertTrue("MainActivity did not settle in the requested " +
            (if (landscape) "landscape" else "portrait") + " orientation with Home accessible", homeReady)
        waitFor("native-home").recycle()
        assertEquals(args.getString("expectedScale")?.toFloat() ?: 1f, context.resources.configuration.fontScale, .06f)
    }
    @After fun closeApplication() {
        if (::scenario.isInitialized) scenario.close()
        automation.setOnAccessibilityEventListener(null)
    }

    private fun search(predicate: (AccessibilityNodeInfo) -> Boolean): AccessibilityNodeInfo? {
        if (android.os.Build.VERSION.SDK_INT >= 34) automation.clearCache()
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
        val bounds = Rect(); it.getBoundsInScreen(bounds)
        (it.viewIdResourceName == id || it.viewIdResourceName?.endsWith("/" + id) == true) &&
            (!visible || (it.isVisibleToUser && bounds.width() > 20 && bounds.height() > 20))
    }
    private fun waitFor(id: String): AccessibilityNodeInfo {
        val until = SystemClock.elapsedRealtime() + 20_000
        while (SystemClock.elapsedRealtime() < until) {
            find(id)?.let { return it }
            SystemClock.sleep(75)
        }
        runCatching { saveScreen("failure-" + id) }
        // Say what was on screen: the exact reason (off-screen, too small, wrong page) decides the fix.
        val seen = ArrayList<String>()
        runCatching {
            search { n ->
                if (n.isVisibleToUser) {
                    val r = Rect(); n.getBoundsInScreen(r)
                    val label = (n.viewIdResourceName?.substringAfterLast('/') ?: "") +
                        (n.text?.toString()?.takeIf { it.isNotBlank() }?.let { "'" + it.take(22) + "'" } ?: "")
                    if (label.isNotBlank()) seen += label + "@" + r.top + "-" + r.bottom
                }
                false
            }
        }
        val target = runCatching { find(id, visible = false) }.getOrNull()
        val where = target?.let { val r = Rect(); it.getBoundsInScreen(r); "present, visibleToUser=" + it.isVisibleToUser + " bounds=" + r } ?: "absent from the accessibility tree"
        val display = context.resources.displayMetrics
        throw AssertionError("Android accessibility node not found: " + id + " [" + where + "; screen=" + display.widthPixels + "x" +
            display.heightPixels + " fontScale=" + context.resources.configuration.fontScale + "; scroll:" + scrollTrace + "; visible: " + seen.take(45).joinToString(" | ") + "]")
    }
    private fun windowBounds(): Rect {
        // Android may return null while a requested orientation is recreating the
        // Activity. Retry the real accessibility root; never invent window bounds.
        val deadline = SystemClock.elapsedRealtime() + 5_000
        while (SystemClock.elapsedRealtime() < deadline) {
            if (android.os.Build.VERSION.SDK_INT >= 34) automation.clearCache()
            val node = automation.rootInActiveWindow
            if (node != null) {
                val bounds = Rect()
                node.getBoundsInScreen(bounds)
                node.recycle()
                if (bounds.width() > 20 && bounds.height() > 20) return bounds
            }
            SystemClock.sleep(75)
        }
        throw AssertionError("No active accessibility window after Activity recreation" +
            " (fontScale=" + context.resources.configuration.fontScale + ")")
    }
    private fun fingerprint(): String {
        if (android.os.Build.VERSION.SDK_INT >= 34) automation.clearCache()
        val out = StringBuilder()
        fun visit(node: AccessibilityNodeInfo) {
            if (node.isVisibleToUser) {
                val rect = Rect(); node.getBoundsInScreen(rect)
                out.append(node.viewIdResourceName).append(':').append(node.text).append('@').append(rect).append(';')
            }
            for (at in 0 until node.childCount) node.getChild(at)?.let { visit(it); it.recycle() }
        }
        automation.rootInActiveWindow?.let { visit(it); it.recycle() }
        return out.toString()
    }
    private fun swipe(x0: Float, y0: Float, x1: Float, y1: Float) {
        val down = SystemClock.uptimeMillis()
        fun send(action: Int, x: Float, y: Float) {
            val event = MotionEvent.obtain(down, SystemClock.uptimeMillis(), action, x, y, 0)
            event.source = InputDevice.SOURCE_TOUCHSCREEN
            try { assertTrue("Touch injection failed", automation.injectInputEvent(event, true)) } finally { event.recycle() }
        }
        send(MotionEvent.ACTION_DOWN, x0, y0)
        for (step in 1..8) {
            SystemClock.sleep(20)
            val t = step / 8f
            send(MotionEvent.ACTION_MOVE, x0 + (x1 - x0) * t, y0 + (y1 - y0) * t)
        }
        // End without a fling, so controls cannot be skipped between observations.
        SystemClock.sleep(140)
        send(MotionEvent.ACTION_UP, x1, y1)
        instrumentation.waitForIdleSync()
        SystemClock.sleep(100)
    }
    private fun scroll(action: Int): Boolean {
        val before = fingerprint(); val scrollEvent = verticalScrollEvents.get()
        // Sticky bars take a third of a 200% landscape screen, so a swipe aimed at a fraction of the
        // window can start on the bar and never reach the page. Aim inside the scrollable page instead.
        val bounds = largestScrollableBounds() ?: windowBounds()
        val high = bounds.top + bounds.height() * .32f
        val low = bounds.top + bounds.height() * .78f
        if (action == AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            swipe(bounds.exactCenterX(), low, bounds.exactCenterX(), high)
        else swipe(bounds.exactCenterX(), high, bounds.exactCenterX(), low)
        // A tall image can retain identical clipped bounds while scrolling through it.
        if (before != fingerprint() || verticalScrollEvents.get() != scrollEvent) return true
        // A swipe that starts on an interactive canvas is consumed by it and never scrolls the page
        // (seen at Tamil 200% in the release build). Ask the largest scrollable container directly.
        return scrollLargestContainer(action, before)
    }
    private fun largestScrollableBounds(): Rect? {
        var best: Rect? = null
        fun visit(node: AccessibilityNodeInfo) {
            if (node.isScrollable && node.isVisibleToUser) {
                val r = Rect(); node.getBoundsInScreen(r)
                // A horizontal strip (module or process selector) is not the page.
                if (r.height() >= 300 && r.width() * r.height() > (best?.let { it.width() * it.height() } ?: 0)) best = r
            }
            for (at in 0 until node.childCount) node.getChild(at)?.let(::visit)
        }
        automation.rootInActiveWindow?.let(::visit)
        return best
    }
    private fun scrollLargestContainer(action: Int, before: String): Boolean {
        var best: AccessibilityNodeInfo? = null
        var bestArea = 0
        fun visit(node: AccessibilityNodeInfo) {
            if (node.isScrollable && node.isVisibleToUser) {
                val r = Rect(); node.getBoundsInScreen(r)
                val area = r.width() * r.height()
                if (r.height() >= r.width() * .6f && area > bestArea) { best = node; bestArea = area }
            }
            for (at in 0 until node.childCount) node.getChild(at)?.let(::visit)
        }
        automation.rootInActiveWindow?.let(::visit)
        val target = best ?: return false
        val moved = target.performAction(action)
        instrumentation.waitForIdleSync(); SystemClock.sleep(150)
        return moved && before != fingerprint()
    }
    private fun horizontal(container: String, id: String): AccessibilityNodeInfo {
        scrollTo(container).recycle()
        for (direction in listOf(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD, AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) {
            for (attempt in 0 until 18) {
                find(id)?.let { return it }
                val row = find(container) ?: break
                val bounds = Rect(); row.getBoundsInScreen(bounds); row.recycle()
                bounds.intersect(windowBounds())
                val before = fingerprint()
                val left = bounds.left + bounds.width() * .18f
                val right = bounds.left + bounds.width() * .82f
                if (direction == AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                    swipe(right, bounds.exactCenterY(), left, bounds.exactCenterY())
                else swipe(left, bounds.exactCenterY(), right, bounds.exactCenterY())
                if (before == fingerprint()) break
            }
        }
        return waitFor(id)
    }
    private val scrollTrace = StringBuilder()
    private fun scrollTo(id: String): AccessibilityNodeInfo {
        find(id)?.let { return it }
        if (id.startsWith("r1-tab-")) return horizontal("r1-module-selector", id)
        if (id.startsWith("r1-process-") && id != "r1-process-selector") return horizontal("r1-process-selector", id)
        val directions = if (id == "r1-process-selector")
            listOf(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD, AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
        else listOf(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD, AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
        // Tamil 200% pages are several times taller; give those searches a larger scroll budget.
        val budget = if (context.resources.configuration.fontScale > 1.5f) 140 else 55
        scrollTrace.setLength(0)
        for (direction in directions) {
            var steps = 0
            for (attempt in 0 until budget) {
                find(id)?.let { return it }
                find(id, false)?.let { it.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN.id); it.recycle(); SystemClock.sleep(75) }
                find(id)?.let { return it }
                // One stalled swipe is not the end of the page: inside a paragraph taller than the screen the
                // clipped bounds do not change, so confirm with a second attempt before giving up.
                if (!scroll(direction) && !(SystemClock.sleep(300).let { scroll(direction) })) { scrollTrace.append(" dir=").append(direction).append(" stopped-after=").append(steps); break }
                steps++
                if (attempt == budget - 1) scrollTrace.append(" dir=").append(direction).append(" budget-exhausted=").append(steps)
            }
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
        instrumentation.waitForIdleSync()
        SystemClock.sleep(120)
    }
    private fun tap(id: String) = click(scrollTo(id), id)
    private fun text(id: String): String {
        val node = scrollTo(id); val value = node.text?.toString().orEmpty(); node.recycle(); return value
    }
    private fun waitText(id: String, expected: (String) -> Boolean): String {
        val deadline = SystemClock.elapsedRealtime() + 10_000
        var actual = ""
        while (SystemClock.elapsedRealtime() < deadline) {
            find(id)?.let { actual = it.text?.toString().orEmpty(); it.recycle() }
            if (expected(actual)) return actual
            SystemClock.sleep(75)
        }
        throw AssertionError("Updated text was not observed for " + id + ": " + actual)
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
    /** Full height and the height left after clipping by every scrollable ancestor, or null if absent. */
    private fun clippedHeights(id: String): Pair<Int, Int>? {
        val node = find(id, false) ?: return null
        val r = Rect(); node.getBoundsInScreen(r); val full = r.height()
        var ancestor = node.parent
        while (ancestor != null) {
            if (ancestor.isScrollable) { val clip = Rect(); ancestor.getBoundsInScreen(clip); r.intersect(clip) }
            val next = ancestor.parent; ancestor.recycle(); ancestor = next
        }
        node.recycle()
        return full to maxOf(r.height(), 0)
    }
    private fun visibleHeight(id: String): Int {
        val node = find(id, false) ?: return 0
        val rect = Rect(); node.getBoundsInScreen(rect); node.recycle()
        rect.intersect(windowBounds())
        return maxOf(rect.height(), 0)
    }
    /**
     * ACTION_SHOW_ON_SCREEN scrolls with an animation. Sampling during it captured a 69 px sliver
     * of a 604 px diagram in the release build (CI run 41411776692), so before and after captures
     * differed in size. Repeat the action and wait for the layout to settle until the visible
     * height stops growing.
     */
    private fun bringFullyIntoView(id: String) {
        var last = -1
        repeat(8) {
            find(id, false)?.let { node ->
                node.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN.id)
                node.recycle()
            }
            instrumentation.waitForIdleSync()
            var settled = fingerprint()
            for (poll in 0 until 8) {
                SystemClock.sleep(90)
                val now = fingerprint()
                if (now == settled) break
                settled = now
            }
            val height = visibleHeight(id)
            if (height == last) return
            last = height
        }
    }
    private fun diagram(id: String, align: Boolean = true): Bitmap {
        // Most diagrams need explicit alignment. The ciliary canvas sits immediately
        // above its transport: align the transport once per capture instead. Asking
        // SHOW_ON_SCREEN to align the partly visible ciliary canvas moved its viewport
        // between before/after samples in release Tamil 200% landscape.
        if (align) bringFullyIntoView(id)
        val node = if (align) scrollTo(id) else waitFor(id)
        val bounds = Rect(); node.getBoundsInScreen(bounds)
        val fullHeight = bounds.height()
        var ancestor = node.parent
        while (ancestor != null) {
            if (ancestor.isScrollable) { val clip = Rect(); ancestor.getBoundsInScreen(clip); bounds.intersect(clip) }
            val next = ancestor.parent; ancestor.recycle(); ancestor = next
        }
        node.recycle()
        val screen = checkNotNull(automation.takeScreenshot())
        bounds.intersect(0, 0, screen.width, screen.height)
        if (bounds.width() <= 20 || bounds.height() <= 20) saveScreen("failure-viewport-" + id)
        assertTrue("Diagram is outside the viewport: " + id + " " + bounds, bounds.width() > 20 && bounds.height() > 20)
        assertTrue("Insufficient visible diagram for pixel comparison: " + id + " visible=" + bounds.height() + " full=" + fullHeight,
            bounds.height() >= fullHeight * .5f)
        val region = Bitmap.createBitmap(screen, bounds.left, bounds.top, bounds.width(), bounds.height())
        val result = checkNotNull(region.copy(Bitmap.Config.ARGB_8888, false))
        if (region !== screen) region.recycle()
        screen.recycle()
        return result
    }
    private fun changedPixels(a: Bitmap, b: Bitmap, label: String = ""): Int {
        // Name the diagram and both sizes: a bare "expected:<640> but was:<788>" cannot be traced to a case.
        assertEquals(label + ": diagram capture widths differ (before=" + a.width + "x" + a.height +
            ", after=" + b.width + "x" + b.height + ")", a.width, b.width)
        assertEquals(label + ": diagram capture heights differ (before=" + a.width + "x" + a.height +
            ", after=" + b.width + "x" + b.height + ")", a.height, b.height)
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
            // Objectives and the introduction share the opening page; the contents list is page 2.
            scrollTo("book-objectives").recycle(); scrollTo("book-introduction").recycle()
            tap("reading-page-next")
            val page = waitText("reading-page-position") { it.startsWith(if (tamil) "பக்கம் 2/" else "Page 2/") }
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
            val expected = if (tamil) "நிலை 2 / " + count else "Stage 2 of " + count
            assertEquals(expected, waitText("u18-stage-counter-" + organism) { it == expected })
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
            tap("r1-process-" + c.process); if (c.prefix == "r19" || c.prefix == "r17") tap(c.prefix + "-jump-stages"); tap(c.stage); c.start?.let(::tap)
            val transportId = c.prefix + "-inline-play-pause"
            scrollTo(transportId).recycle()
            // Ciliary playback occupies 190 dp in landscape but its transport is
            // further down the same scroll column. Keep that control as our viewport
            // anchor for all captures so scrolling cannot imitate animated pixels.
            fun captureDiagram(): Bitmap {
                if (c.prefix == "ciliary") {
                    scrollTo(transportId).recycle()
                    return diagram(c.canvas, align = false)
                }
                return diagram(c.canvas)
            }
            val before = captureDiagram()
            // The transport was reached by touch above. Activate its native action
            // without moving the viewport away from the diagram being measured.
            fun playbackControl() = click(checkNotNull(find(transportId, false)), transportId)
            playbackControl()
            val until = SystemClock.elapsedRealtime() + 8_000
            while (phase(c.canvas) !in .18f.. .8f && SystemClock.elapsedRealtime() < until) SystemClock.sleep(30)
            playbackControl(); SystemClock.sleep(150)
            val stopped = phase(c.canvas); assertTrue(c.process + ": no intermediate pause", stopped > 0f && stopped < 1f)
            val after = captureDiagram()
            assertTrue(c.process + ": diagram pixels did not change", changedPixels(before, after, c.process + " / " + c.canvas) > 12)
            saveScreen(c.process + "-release-start", before); saveScreen(c.process + "-release-paused", after)
            SystemClock.sleep(300)
            assertEquals(c.process + ": phase advanced after Pause", stopped, phase(c.canvas), .0011f)
            val paused = captureDiagram()
            assertTrue(c.process + ": pixels moved after Pause", after.sameAs(paused))
            before.recycle(); after.recycle(); paused.recycle()
        }
    }
}
