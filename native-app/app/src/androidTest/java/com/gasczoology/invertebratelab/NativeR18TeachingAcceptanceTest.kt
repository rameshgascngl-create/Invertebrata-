package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeR18TeachingAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val args get() = InstrumentationRegistry.getArguments()
    private val lang get() = if(args.getString("r18Language")=="TAMIL") AppLanguage.TAMIL else AppLanguage.ENGLISH
    private val landscape get() = args.getString("r18Orientation")=="landscape"
    private val scale get() = args.getString("r18Scale")?.toFloat() ?: 1f
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val prefix get() = "r18-${lang.name.lowercase()}-${if(scale>1.5f)200 else 100}-${if(landscape)"landscape" else "portrait"}"
    @Before fun openNativeTextbook() {
        runBlocking { NativeLearningRepository(context).save(NativeLearningState());LanguagePreferenceRepository(context).setLanguage(lang) }
        rule.activityRule.scenario.recreate()
        rule.activityRule.scenario.onActivity { it.requestedOrientation=if(landscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        rule.waitUntil(20_000) { context.resources.configuration.orientation==if(landscape) Configuration.ORIENTATION_LANDSCAPE else Configuration.ORIENTATION_PORTRAIT }
        assertEquals(scale,context.resources.configuration.fontScale,.05f)
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-tab-water-balance").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-tab-water-balance").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r18-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
    }
    private fun canvas(): SemanticsNodeInteraction {
        rule.onNodeWithTag("r18-view-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val c=rule.onNodeWithTag("r18-water-canvas",true)
        val b=c.fetchSemanticsNode().boundsInRoot;val r=rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Whole native plate must fit viewport: $b / $r",b.width>0 && b.height>0 && b.top>=r.top-1 && b.bottom<=r.bottom+1)
        return c
    }
    @Test fun allStagesAndRealStructureTapsPreserveReadableAccessibleControls() {
        for((i,stage) in ParameciumWaterBalance.stages.withIndex()) {
            val button=rule.onNodeWithTag("r18-stage-"+stage.id,true)
            button.performScrollTo().assertHeightIsAtLeast(48.dp).assertHasClickAction().performClick()
            button.assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            rule.onNodeWithTag("r18-stage-title",true).performScrollTo().assertTextEquals(
                (if(lang==AppLanguage.TAMIL)"காட்சி " else "View ")+"${i+1}/4 — "+stage.heading.value(lang))
            rule.onNodeWithTag("r18-stage-explanation",true).performScrollTo().assertTextEquals(stage.explanation.value(lang))
            val c=canvas();assertEquals(6,c.fetchSemanticsNode().config[SemanticsActions.CustomActions].size)
            assertTrue(c.fetchSemanticsNode().config[SemanticsProperties.ContentDescription].joinToString().contains(stage.heading.value(lang)))
            capture("stage-"+stage.id)
        }
        for(m in WaterBalanceFigure.marks) {
            val c=canvas();val b=c.fetchSemanticsNode().boundsInRoot;val fit=WaterBalanceFigure.fit(b.width,b.height)
            c.performTouchInput { click(Offset(fit.x(m.x),fit.y(m.y))) }
            rule.onNodeWithTag("r18-select-"+m.id,true).performScrollTo().assertHeightIsAtLeast(48.dp)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            rule.onNodeWithTag("r18-organ-explanation",true).performScrollTo().assertTextEquals(ParameciumWaterBalance.structure(m.id).explanation.value(lang))
            canvas();capture("highlight-"+m.id)
        }
        rule.onNodeWithTag("r18-play-pause",true).performScrollTo().assertHeightIsAtLeast(48.dp);capture("controls")
        rule.onNodeWithTag("r18-reduced-motion",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-play-pause",true).assertIsNotEnabled()
        rule.onNodeWithTag("r18-reset",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-next",true).performScrollTo().assertIsEnabled().performClick()
        rule.onNodeWithTag("r18-stage-collect",true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
    }
    @Test fun completeNumberedReadingAndStructureStageAudioKeepWrittenFallback() {
        for(reading in ParameciumWaterBalance.readings) {
            rule.onNodeWithTag("r18-reading-"+reading.id,true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.onNodeWithTag("r18-reading-heading",true).performScrollTo().assertTextEquals(reading.heading.value(lang))
            reading.paragraphs.forEachIndexed { i,p -> rule.onNodeWithTag("r18-paragraph-"+i,true).performScrollTo().assertTextEquals(p.value(lang)) }
            capture("reading-"+reading.id)
        }
        rule.onNodeWithTag("r18-narrate-stage",true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback",true).performScrollTo().assertTextEquals(ParameciumWaterBalance.stages.first().explanation.value(lang))
        capture("stage-audio-fallback")
        rule.onNodeWithTag("r161-stop-audio",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-select-decorated",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-narrate-organ",true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback",true).performScrollTo().assertTextEquals(ParameciumWaterBalance.structure("decorated").explanation.value(lang))
        capture("organ-audio-fallback")
    }
    @Test fun nativeMotionReallyChangesPixelsAndPausesAtIntermediatePhase() {
        rule.onNodeWithTag("r18-stage-fill",true).performScrollTo().performClick()
        val before=canvas().captureToImage().asAndroidBitmap();capture("fill-phase-initial")
        rule.onNodeWithTag("r18-play-pause",true).performScrollTo().performClick()
        rule.waitUntil(12_000) { rule.onNodeWithTag("r18-water-canvas",true).fetchSemanticsNode()
            .config[SemanticsProperties.ProgressBarRangeInfo].current in .35f.. .8f }
        rule.onNodeWithTag("r18-play-pause",true).performClick()
        rule.onNodeWithTag("r18-playback-state",true).assertTextEquals(if(lang==AppLanguage.TAMIL)"இடைநிறுத்தம்" else "Paused")
        val c=canvas();val p=c.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
        assertTrue(p>0 && p<1)
        val after=c.captureToImage().asAndroidBitmap();assertFalse("Native radius/flow must change pixels",before.sameAs(after))
        capture("fill-phase-paused");before.recycle();after.recycle()
        rule.onNodeWithTag("r18-reset",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-play-pause",true).performScrollTo().performClick()
        rule.waitUntil(12_000) { rule.onNodeWithTag("r18-stage-collect",true).fetchSemanticsNode().config[SemanticsProperties.Selected] }
        rule.onNodeWithTag("r18-play-pause",true).performClick()
    }
    @Test fun realDataStoreRecreationAndChapterNavigationPreservePosition() {
        rule.onNodeWithTag("r18-reading-4.4",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-stage-expel",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-select-decorated",true).performScrollTo().performClick()
        rule.onNodeWithTag("r18-reduced-motion",true).performScrollTo().performClick()
        rule.waitForIdle()
        runBlocking { withTimeout(15_000) { NativeLearningRepository(context).learningState.first {
            it.waterBalanceProgress.stageId=="expel" && it.waterBalanceProgress.readingId=="4.4" &&
                it.waterBalanceProgress.selectedStructure=="decorated" && it.waterBalanceProgress.reducedMotion
        } } }
        rule.onNodeWithTag("r1-tab-nuclear").performScrollTo().performClick()
        rule.onNodeWithTag("r17-chapter-conjugation",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-stage-exchange",true).performScrollTo().performClick()
        rule.onNodeWithTag("r1-tab-water-balance").performScrollTo().performClick()
        rule.onNodeWithTag("r18-stage-expel",true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r18-reading-heading",true).assertTextEquals(ParameciumWaterBalance.readings[3].heading.value(lang))
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r18-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r18-select-decorated",true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r18-reduced-motion",true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r18-play-pause",true).assertIsNotEnabled()
        runBlocking { val s=NativeLearningRepository(context).learningState.first()
            assertEquals("conjugation",s.nuclearProgress.chapterId);assertEquals("exchange",s.nuclearProgress.viewId())
            assertEquals("expel",s.waterBalanceProgress.stageId);assertEquals(1,s.schemaVersion) }
        canvas();capture("restored-expel")
    }
    private fun capture(suffix: String) {
        val a=InstrumentationRegistry.getInstrumentation().uiAutomation
        val path="/sdcard/Download/native-anatomy-evidence/$prefix-$suffix.png"
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("screencap -p $path")).use{it.readBytes()}
        val bytes=ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("cat $path")).use{it.readBytes()}
        val bmp=checkNotNull(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
        assertTrue(bytes.size>1000);assertTrue(if(landscape)bmp.width>bmp.height else bmp.height>bmp.width);bmp.recycle()
    }
}
