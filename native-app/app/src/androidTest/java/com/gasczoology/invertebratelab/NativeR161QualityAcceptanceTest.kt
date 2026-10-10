package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeR161QualityAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val args get() = InstrumentationRegistry.getArguments()
    private val landscape get() = args.getString("r161Orientation") == "landscape"
    private val expectedScale get() = args.getString("r161Scale")?.toFloat() ?: 1f
    private val prefix get() = "r161-tamil${if (expectedScale > 1.5f) "200" else "100"}-${if (landscape) "landscape" else "portrait"}"
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before fun openActualTamilCiliaryLesson() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.TAMIL)
        }
        rule.activityRule.scenario.recreate()
        rule.activityRule.scenario.onActivity {
            it.requestedOrientation = if (landscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        rule.waitUntil(20_000) {
            context.resources.configuration.orientation == if (landscape) Configuration.ORIENTATION_LANDSCAPE
                else Configuration.ORIENTATION_PORTRAIT
        }
        val scale = context.resources.configuration.fontScale
        assertEquals("Actual Android system font scale", expectedScale, scale, .05f)
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r14-contents-toggle", true).performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-pellicle-and-cilia", true).performScrollTo().performClick()
    }
    private fun canvasInView(): SemanticsNodeInteraction {
        rule.onNodeWithTag("r16-view-ciliary-plate", true).performScrollTo().performClick()
        rule.waitForIdle()
        val canvas = rule.onNodeWithTag("r16-ciliary-canvas", true)
        val bounds = canvas.fetchSemanticsNode().boundsInRoot
        val root = rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Entire Canvas must fit actual visible viewport $bounds / $root",
            bounds.width > 0 && bounds.height > 0 && bounds.top >= root.top - 1 && bounds.bottom <= root.bottom + 1)
        return canvas
    }
    @Test fun fourStagesAndTouchSelectionExposeScientificAccessibilitySemantics() {
        val motion = ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION)
        motion.stages.forEachIndexed { index, stage ->
            val button = rule.onNodeWithTag("r16-ciliary-stage-" + stage.id, true)
            button.performScrollTo().assertHeightIsAtLeast(48.dp).assertHasClickAction().performClick()
            button.assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected, true))
            rule.onNodeWithTag("r16-ciliary-stage-title", true).performScrollTo()
                .assertTextEquals("நிலை ${index + 1}/4 — " + stage.heading.tamil)
            rule.onNodeWithTag("r16-ciliary-stage-explanation", true).performScrollTo()
                .assertTextEquals(stage.explanation.tamil)
            val canvas = canvasInView()
            val description = canvas.fetchSemanticsNode().config[SemanticsProperties.ContentDescription].joinToString()
            assertTrue(description.contains(stage.heading.tamil))
            assertEquals(3, canvas.fetchSemanticsNode().config[SemanticsProperties.CustomActions].size)
            capture("stage-${index + 1}")
        }
        val canvas = canvasInView()
        val bounds = canvas.fetchSemanticsNode().boundsInRoot
        canvas.performTouchInput { click(Offset(bounds.width * .75f, bounds.height * .23f)) }
        rule.onNodeWithTag("r161-structure-axoneme", true).performScrollTo().assertHeightIsAtLeast(48.dp)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected, true))
        canvasInView(); capture("highlight-axoneme")
        rule.onNodeWithTag("r161-structure-basal", true).performScrollTo().performClick()
        canvasInView(); capture("highlight-basal")
        rule.onNodeWithTag("r16-ciliary-stage-wave", true).performScrollTo()
        capture("controls")
        rule.onNodeWithTag("r16-ciliary-subtext-2", true).performScrollTo(); capture("academic-text")
    }
    @Test fun learningPositionSurvivesTabsChapterNavigationAndActivityRecreation() {
        rule.onNodeWithTag("r16-ciliary-stage-wave", true).performScrollTo().performClick()
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        rule.onNodeWithTag("r1-tab-study").performScrollTo().performClick()
        rule.onNodeWithTag("r16-ciliary-stage-title", true).performScrollTo()
            .assertTextEquals("நிலை 4/4 — " + ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION).stages.last().heading.tamil)
        rule.onNodeWithTag("r14-contents-toggle", true).performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-cortical-avoidance-response", true).performScrollTo().performClick()
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-study-cortical-avoidance-response", true).fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r16-ciliary-stage-title", true).performScrollTo()
            .assertTextEquals("நிலை 4/4 — " + ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION).stages.last().heading.tamil)
        runBlocking {
            val state = NativeLearningRepository(context).learningState.first()
            assertEquals(3, state.ciliaryStageIndex); assertEquals("cortical-avoidance-response", state.textbookSectionId)
            assertEquals("study", state.laboratoryTab)
        }
    }
    @Test fun realTtsRequestReplayAndStopPreserveCompleteWrittenFallback() {
        val firstStage = ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION).stages.first()
        rule.onNodeWithTag("r16-ciliary-narrate", true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback", true).performScrollTo().assertTextEquals(firstStage.explanation.tamil)
        rule.onNodeWithTag("r1-speech-status", true).performScrollTo()
        capture("audio-result")
        Log.i("R161_AUDIO_QA", "ACTUAL_ENGINE_STATUS=" + rule.onNodeWithTag("r1-speech-status", true).fetchSemanticsNode().config[SemanticsProperties.Text].joinToString())
        rule.onNodeWithTag("r16-ciliary-narrate", true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-stop-audio", true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r161-audio-written-fallback", true).performScrollTo().assertTextEquals(firstStage.explanation.tamil)
        // Real organ narration traverses the same native engine.
        rule.onNodeWithTag("r1-tab-listen").performScrollTo().performClick()
        rule.onNodeWithTag("r1-narrate-somatic-cilia", true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback", true).performScrollTo()
            .assertTextEquals(ParameciumLearningEngine.organ("somatic-cilia").narration.tamil)
    }
    private fun capture(suffix: String) {
        val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
        val path = "/sdcard/Download/native-anatomy-evidence/$prefix-$suffix.png"
        ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand("screencap -p $path")).use { it.readBytes() }
        val bytes = ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand("cat $path")).use { it.readBytes() }
        assertTrue(bytes.size > 1000)
        val bmp = checkNotNull(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
        assertTrue(if (landscape) bmp.width > bmp.height else bmp.height > bmp.width)
        bmp.recycle()
    }
}
