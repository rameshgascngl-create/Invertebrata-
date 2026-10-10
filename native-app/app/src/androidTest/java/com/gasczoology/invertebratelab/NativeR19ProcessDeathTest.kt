package com.gasczoology.invertebratelab

import android.os.Bundle
import android.os.ParcelFileDescriptor
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

@RunWith(AndroidJUnit4::class)
class NativeR19ProcessDeathSetupTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    @Test fun commitCytoproctStageReadingOrganAndActualPausedPhase() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {NativeLearningRepository(context).save(NativeLearningState());LanguagePreferenceRepository(context).setLanguage(AppLanguage.TAMIL)}
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r1-tab-nutrition").fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r1-tab-nutrition").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r19-reading-5.7",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-stage-recycling",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-select-recovered-membrane",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-stage-explanation",true).performScrollTo().assertTextEquals(ParameciumEgestion.stages.last().explanation.tamil)
        rule.onNodeWithTag("r19-play-pause",true).performScrollTo().performClick()
        rule.waitUntil(12_000) {rule.onNodeWithTag("r19-egestion-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .4f.. .85f}
        rule.onNodeWithTag("r19-play-pause",true).performClick()
        val saved=runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.first {
            it.nutritionProgress.stageId=="recycling" && it.nutritionProgress.readingId=="5.7" &&
                it.nutritionProgress.selectedStructure=="recovered-membrane" && it.nutritionProgress.phasePermille in 400..850
        }}}
        assertEquals(StudyDestination.PARAMECIUM_LAB,saved.destination);assertEquals("nutrition",saved.laboratoryTab)
        InstrumentationRegistry.getInstrumentation().sendStatus(0,Bundle().apply {putString("r19SavedPhase",saved.nutritionProgress.phasePermille.toString())})
    }
}

/** Deliberately performs no fixture writes: external driver must establish a genuinely different PID. */
@RunWith(AndroidJUnit4::class)
class NativeR19ProcessDeathVerifyTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    @Test fun recoverCytoproctAndExactIntermediatePhaseWithoutMutation() {
        val args=InstrumentationRegistry.getArguments()
        val expected=checkNotNull(args.getString("r19RecoveryPhase")).toInt();assertTrue(expected in 400..850)
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r19-reading-heading",true).performScrollTo().assertTextEquals(ParameciumEgestion.readings.last().heading.tamil)
        rule.onNodeWithTag("r19-stage-recycling",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r19-stage-explanation",true).performScrollTo().assertTextEquals(ParameciumEgestion.stages.last().explanation.tamil)
        rule.onNodeWithTag("r19-select-recovered-membrane",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r19-playback-state",true).performScrollTo().assertTextEquals("இடைநிறுத்தம்")
        runBlocking {val s=NativeLearningRepository(context).learningState.first()
            assertEquals(1,s.schemaVersion);assertEquals("nutrition",s.laboratoryTab);assertEquals(StudyDestination.PARAMECIUM_LAB,s.destination)
            assertEquals(NutritionProgress("recycling","5.7","recovered-membrane",expected),s.nutritionProgress)
            assertEquals(AppLanguage.TAMIL,LanguagePreferenceRepository(context).language.first())}
        rule.onNodeWithTag("r19-view-egestion-plate",true).performScrollTo().performClick();rule.waitForIdle()
        assertEquals(expected/1000f,rule.onNodeWithTag("r19-egestion-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.001f)
        val a=InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("mkdir -p /sdcard/Download/native-anatomy-evidence")).use {it.readBytes()}
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("screencap -p /sdcard/Download/native-anatomy-evidence/r19-recovered-cytoproct.png")).use {it.readBytes()}
    }
}
