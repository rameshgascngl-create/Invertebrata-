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

private fun recoveryArg(key: String, fallback: String) = InstrumentationRegistry.getArguments().getString(key) ?: fallback
private val recoveryChapter get() = recoveryArg("r17RecoveryChapter", "dimorphism")
private val recoveryStage get() = recoveryArg("r17RecoveryStage", "germline")
private val recoveryReading get() = recoveryArg("r17RecoveryReading", "1.3")

/** Only accepted with external ActivityManager background-state, am-kill and PID proof. */
@RunWith(AndroidJUnit4::class)
class NativeR17ProcessDeathSetupTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    @Test fun selectLearningPositionThroughNativeUiThenVerifyAtomicCommit() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking { NativeLearningRepository(context).save(NativeLearningState());LanguagePreferenceRepository(context).setLanguage(AppLanguage.TAMIL) }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.onNodeWithTag("r1-tab-nuclear").performScrollTo().performClick()
        rule.onNodeWithTag("r17-chapter-"+recoveryChapter,true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-reading-"+recoveryReading,true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-stage-"+recoveryStage,true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-select-micronucleus",true).performScrollTo().performClick()
        val stage=ParameciumNuclearBiology.chapter(recoveryChapter).views.first{it.id==recoveryStage}
        rule.onNodeWithTag("r17-stage-explanation",true).performScrollTo().assertTextEquals(stage.explanation.tamil)
        runBlocking { withTimeout(15_000) {
            val s=NativeLearningRepository(context).learningState.first { it.nuclearProgress.chapterId==recoveryChapter && it.nuclearProgress.viewId()==recoveryStage && it.nuclearProgress.readingId==recoveryReading && it.nuclearProgress.selectedNucleus=="micronucleus" }
            assertEquals("nuclear",s.laboratoryTab);assertEquals(StudyDestination.PARAMECIUM_LAB,s.destination)
            Log.i("R17_PROGRESS","committed ${s.nuclearProgress}")
        } }
    }
}

@RunWith(AndroidJUnit4::class)
class NativeR17ProcessDeathVerifyTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    @Test fun recoverWithoutFixtureMutationAfterDifferentPidIsProved() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r17-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        val chapter=ParameciumNuclearBiology.chapter(recoveryChapter)
        val stage=chapter.views.first{it.id==recoveryStage}
        rule.onNodeWithTag("r17-reading-heading",true).performScrollTo().assertTextEquals(chapter.readings.first{it.id==recoveryReading}.heading.tamil)
        rule.onNodeWithTag("r17-stage-"+recoveryStage,true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r17-stage-explanation",true).performScrollTo().assertTextEquals(stage.explanation.tamil)
        rule.onNodeWithTag("r17-select-micronucleus",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r17-playback-state",true).performScrollTo().assertTextEquals("இடைநிறுத்தம்")
        runBlocking { val s=NativeLearningRepository(context).learningState.first()
            assertEquals(1,s.schemaVersion);assertEquals("nuclear",s.laboratoryTab)
            assertEquals(recoveryChapter,s.nuclearProgress.chapterId);assertEquals(recoveryStage,s.nuclearProgress.viewId())
            assertEquals(recoveryReading,s.nuclearProgress.readingId);assertEquals("micronucleus",s.nuclearProgress.selectedNucleus)
            assertEquals(AppLanguage.TAMIL,LanguagePreferenceRepository(context).language.first())
            Log.i("R17_PROGRESS","recovered ${s.nuclearProgress}") }
        // Capture the genuine recovered native plate before the test framework closes it.
        rule.onNodeWithTag("r17-view-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val a=InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("mkdir -p /sdcard/Download/native-anatomy-evidence")).use{it.readBytes()}
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("screencap -p /sdcard/Download/native-anatomy-evidence/r17-recovered-$recoveryChapter-$recoveryStage.png")).use{it.readBytes()}
    }
}
