package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.ParameciumLearningEngine
import com.gasczoology.invertebratelab.data.ParameciumProcess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Execute on API 34 with actual Android Settings font_scale=2.0.
 * No fake Compose density/fontScale or independently rendered PNG qualifies.
 */
@RunWith(AndroidJUnit4::class)
class NativeR16Tamil200AcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun cleanLearningState() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    @Test
    fun tamilCiliaryChapterAndPencilDrawingAreReadableAtActualSystemTwoHundredPercent() {
        val scale = InstrumentationRegistry.getInstrumentation().targetContext
            .resources.configuration.fontScale
        assertTrue("R1.6 requires Android system fontScale >= 1.95; actual=" + scale,
            scale >= 1.95f)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-pellicle-and-cilia",
            useUnmergedTree = true).performScrollTo().performClick()
        val wave = ParameciumLearningEngine
            .simulation(ParameciumProcess.CILIARY_MOTION).stages.last()
        rule.onNodeWithTag("r16-ciliary-stage-wave", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r16-ciliary-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("நிலை 4/4 — " + wave.heading.tamil)
        rule.onNodeWithTag("r16-ciliary-stage-explanation", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(wave.explanation.tamil)
        for (i in 0 until 3) {
            rule.onNodeWithTag("r16-ciliary-subheading-" + i,
                useUnmergedTree = true).performScrollTo()
        }
        rule.onNodeWithTag("r16-view-ciliary-plate", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val diagram = rule.onNodeWithTag("r16-ciliary-canvas", useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        val root = rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Actual Tamil 200% viewport clipping: diagram=" + diagram +
            " root=" + root,
            diagram.height > 0f && diagram.top >= root.top - 1f &&
                diagram.bottom <= root.bottom + 1f)
        val path = "/sdcard/Download/native-anatomy-evidence/r16-ciliary-tamil200.png"
        val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
        // Screenshot folder is created by the CI driver before instrumentation.
        ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p " + path)
        ).use { it.readBytes() }
        val png = ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat " + path)
        ).use { it.readBytes() }
        check(png.size > 1000) { "Missing real screen PNG: " + path }
        val image = checkNotNull(BitmapFactory.decodeByteArray(png, 0, png.size))
        try {
            assertTrue(image.width > 100 && image.height > 100)
            var paper = 0
            var graphite = 0
            for (y in 0 until image.height step 12) {
                for (x in 0 until image.width step 12) {
                    when (image.getPixel(x, y) and 0x00FFFFFF) {
                        0x00FBFAF6 -> paper++
                        0x0044413F, 0x00302E2C, 0x008A8580 -> graphite++
                    }
                }
            }
            assertTrue("No pencil-paper plate in real Tamil 200% PNG", paper > 10)
            assertTrue("No graphite marks in real Tamil 200% PNG", graphite > 1)
        } finally {
            image.recycle()
        }
    }
}
