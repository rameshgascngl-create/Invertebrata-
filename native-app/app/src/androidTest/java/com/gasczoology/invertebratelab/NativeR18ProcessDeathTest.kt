package com.gasczoology.invertebratelab

import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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

/** External deterministic driver must prove background AM state, old PID gone and a new PID. */
@RunWith(AndroidJUnit4::class)
class NativeR18ProcessDeathSetupTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    @Test fun selectDischargeAndReadingThroughRealUiThenAwaitDurableCommit() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking { NativeLearningRepository(context).save(NativeLearningState());LanguagePreferenceRepository(context).setLanguage(AppLanguage.TAMIL) }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.onNodeWithTag("r1-tab-water-balance").performScrollTo().performClick()
        rule.onNodeWithTag("r18-reading-4.4",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-stage-expel",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-select-decorated",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-stage-explanation",true).performScrollTo().assertTextEquals(ParameciumWaterBalance.stages.last().explanation.tamil)
        rule.waitForIdle()
        runBlocking { withTimeout(15_000) {
            val s=NativeLearningRepository(context).learningState.first { it.waterBalanceProgress.stageId=="expel" &&
                it.waterBalanceProgress.readingId=="4.4" && it.waterBalanceProgress.selectedStructure=="decorated" }
            assertEquals("water-balance",s.laboratoryTab);assertEquals(StudyDestination.PARAMECIUM_LAB,s.destination)
            Log.i("R18_PROGRESS","committed ${s.waterBalanceProgress}")
        } }
    }
}

@RunWith(AndroidJUnit4::class)
class NativeR18ProcessDeathVerifyTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    @Test fun recoverDischargeWithoutFixtureMutationAfterNewPid() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r18-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r18-reading-heading",true).performScrollTo().assertTextEquals(ParameciumWaterBalance.readings[3].heading.tamil)
        rule.onNodeWithTag("r18-stage-expel",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r18-stage-explanation",true).performScrollTo().assertTextEquals(ParameciumWaterBalance.stages.last().explanation.tamil)
        rule.onNodeWithTag("r18-select-decorated",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r18-playback-state",true).performScrollTo().assertTextEquals("இடைநிறுத்தம்")
        runBlocking { val s=NativeLearningRepository(context).learningState.first()
            assertEquals(1,s.schemaVersion);assertEquals("water-balance",s.laboratoryTab)
            assertEquals("expel",s.waterBalanceProgress.stageId);assertEquals("4.4",s.waterBalanceProgress.readingId)
            assertEquals("decorated",s.waterBalanceProgress.selectedStructure)
            assertEquals(AppLanguage.TAMIL,LanguagePreferenceRepository(context).language.first())
            Log.i("R18_PROGRESS","recovered ${s.waterBalanceProgress}") }
        rule.onNodeWithTag("r18-view-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val a=InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("mkdir -p /sdcard/Download/native-anatomy-evidence")).use{it.readBytes()}
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("screencap -p /sdcard/Download/native-anatomy-evidence/r18-recovered-expel.png")).use{it.readBytes()}
    }
}
