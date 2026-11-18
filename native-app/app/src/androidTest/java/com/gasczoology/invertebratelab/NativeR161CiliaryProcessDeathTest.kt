package com.gasczoology.invertebratelab

import android.util.Log
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Only qualifies with the external ActivityManager/PID driver between classes. */
@RunWith(AndroidJUnit4::class)
class NativeR161CiliaryProcessDeathSetupTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    @Test fun selectStageFourThroughActualUiAndVerifyAtomicCommit() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.TAMIL)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r14-contents-toggle", true).performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-pellicle-and-cilia", true).performScrollTo().performClick()
        rule.onNodeWithTag("r16-ciliary-stage-wave", true).performScrollTo().performClick()
        val stage = ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION).stages.last()
        rule.onNodeWithTag("r16-ciliary-stage-title", true).performScrollTo()
            .assertTextEquals("நிலை 4/4 — " + stage.heading.tamil)
        rule.onNodeWithTag("r16-ciliary-stage-explanation", true).performScrollTo().assertTextEquals(stage.explanation.tamil)
        runBlocking { withTimeout(15_000) {
            val state = NativeLearningRepository(context).learningState.first { it.ciliaryStageIndex == 3 }
            assertEquals(StudyDestination.PARAMECIUM_LAB, state.destination)
            assertEquals("pellicle-and-cilia", state.textbookSectionId)
            assertEquals("study", state.laboratoryTab)
            assertEquals(AppLanguage.TAMIL, LanguagePreferenceRepository(context).language.first())
            Log.i("R161_PROGRESS", "persisted schema=1 section=${state.textbookSectionId} stage=4 language=TAMIL tab=study")
        } }
    }
}

@RunWith(AndroidJUnit4::class)
class NativeR161CiliaryProcessDeathVerifyTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    @Test fun readPreviousProcessWithoutFixtureMutationAndRestoreStageFour() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty() }
        // No reset, chapter selection or stage click allowed in this verifier.
        rule.onNodeWithTag("r1-study-pellicle-and-cilia", true).assertExists()
        val stage = ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION).stages.last()
        rule.onNodeWithTag("r16-ciliary-stage-title", true).performScrollTo()
            .assertTextEquals("நிலை 4/4 — " + stage.heading.tamil)
        rule.onNodeWithTag("r16-ciliary-stage-explanation", true).performScrollTo().assertTextEquals(stage.explanation.tamil)
        runBlocking { withTimeout(15_000) {
            val state = NativeLearningRepository(context).learningState.first()
            assertEquals(1, state.schemaVersion)
            assertEquals(StudyDestination.PARAMECIUM_LAB, state.destination)
            assertEquals("u1-paramecium", state.chapterId)
            assertEquals("pellicle-and-cilia", state.textbookSectionId)
            assertEquals(3, state.ciliaryStageIndex)
            assertEquals("study", state.laboratoryTab)
            assertEquals(AppLanguage.TAMIL, LanguagePreferenceRepository(context).language.first())
            Log.i("R161_PROGRESS", "recovered schema=1 section=${state.textbookSectionId} stage=4 language=TAMIL tab=study")
        } }
    }
}
