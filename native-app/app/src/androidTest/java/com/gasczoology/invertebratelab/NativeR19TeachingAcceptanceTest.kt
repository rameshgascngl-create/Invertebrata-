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
class NativeR19TeachingAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val args get() = InstrumentationRegistry.getArguments()
    private val lang get() = if(args.getString("r19Language")=="TAMIL") AppLanguage.TAMIL else AppLanguage.ENGLISH
    private val landscape get() = args.getString("r19Orientation")=="landscape"
    private val scale get() = args.getString("r19Scale")?.toFloat() ?: 1f
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val prefix get() = "r19-${lang.name.lowercase()}-${if(scale>1.5f)200 else 100}-${if(landscape)"landscape" else "portrait"}"
    @Before fun openNativeTextbook() {
        runBlocking { NativeLearningRepository(context).save(NativeLearningState());LanguagePreferenceRepository(context).setLanguage(lang) }
        rule.activityRule.scenario.recreate()
        rule.activityRule.scenario.onActivity { it.requestedOrientation=if(landscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        rule.waitUntil(20_000) { context.resources.configuration.orientation==if(landscape) Configuration.ORIENTATION_LANDSCAPE else Configuration.ORIENTATION_PORTRAIT }
        assertEquals(scale,context.resources.configuration.fontScale,.05f)
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-tab-nutrition").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-tab-nutrition").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
    }
    private fun canvas(): SemanticsNodeInteraction {
        rule.onNodeWithTag("r19-view-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val c=rule.onNodeWithTag("r19-nutrition-canvas",true)
        val b=c.fetchSemanticsNode().boundsInRoot;val r=rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Whole native plate must fit viewport: $b / $r",b.width>0 && b.height>0 && b.top>=r.top-1 && b.bottom<=r.bottom+1)
        return c
    }

    @Test fun stagesAndRealStructureTapsAreVisibleAndAccessible() {
        for((i,stage) in ParameciumNutrition.stages.withIndex()) {
            val button=rule.onNodeWithTag("r19-stage-"+stage.id,true)
            button.performScrollTo().assertHeightIsAtLeast(48.dp).assertHasClickAction().performClick()
            button.assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            rule.onNodeWithTag("r19-stage-title",true).performScrollTo().assertTextEquals(
                (if(lang==AppLanguage.TAMIL)"காட்சி " else "View ")+(i+1)+"/"+ParameciumNutrition.stages.size+" — "+stage.heading.value(lang))
            rule.onNodeWithTag("r19-stage-explanation",true).performScrollTo().assertTextEquals(stage.explanation.value(lang))
            val c=canvas()
            assertEquals(ParameciumNutrition.structures.size,c.fetchSemanticsNode().config[SemanticsActions.CustomActions].size)
            assertEquals(1f,c.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.001f)
            capture("stage-"+stage.id)
        }
        for(m in NutritionFigure.marks) {
            val c=canvas();val b=c.fetchSemanticsNode().boundsInRoot;val f=NutritionFigure.fit(b.width,b.height)
            c.performTouchInput { click(Offset(f.x(m.x),f.y(m.y))) }
            rule.onNodeWithTag("r19-select-"+m.id,true).performScrollTo().assertHeightIsAtLeast(48.dp)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            rule.onNodeWithTag("r19-organ-explanation",true).performScrollTo().assertTextEquals(ParameciumNutrition.structure(m.id).explanation.value(lang))
            canvas();capture("highlight-"+m.id)
        }
        rule.onNodeWithTag("r19-reduced-motion",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-play-pause",true).performScrollTo().assertIsNotEnabled().assertHeightIsAtLeast(48.dp)
        capture("controls")
    }
    @Test fun numberedReadingAndNarrationKeepCompleteWrittenExplanations() {
        for(reading in ParameciumNutrition.readings) {
            rule.onNodeWithTag("r19-reading-"+reading.id,true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.onNodeWithTag("r19-reading-heading",true).performScrollTo().assertTextEquals(reading.heading.value(lang))
            reading.paragraphs.forEachIndexed { i,p -> rule.onNodeWithTag("r19-paragraph-"+i,true).performScrollTo().assertTextEquals(p.value(lang)) }
            capture("reading-"+reading.id)
        }
        rule.onNodeWithTag("r19-narrate-stage",true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback",true).performScrollTo().assertTextEquals(ParameciumNutrition.stages.first().explanation.value(lang))
        capture("stage-audio-fallback")
        rule.onNodeWithTag("r161-stop-audio",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-select-vacuole",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-narrate-organ",true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback",true).performScrollTo().assertTextEquals(ParameciumNutrition.structure("vacuole").explanation.value(lang))
        capture("organ-audio-fallback")
    }
    @Test fun actualFormationPixelsAndIntermediatePhaseSurviveRecreationAndNavigation() {
        rule.onNodeWithTag("r19-stage-formation",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-start-view",true).performScrollTo().performClick()
        val before=canvas().captureToImage().asAndroidBitmap();capture("formation-initial")
        rule.onNodeWithTag("r19-play-pause",true).performScrollTo().performClick()
        rule.waitUntil(12_000) { rule.onNodeWithTag("r19-nutrition-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .35f.. .8f }
        rule.onNodeWithTag("r19-play-pause",true).performClick()
        val after=canvas().captureToImage().asAndroidBitmap()
        assertFalse("Actual growth/release must change native pixels",before.sameAs(after))
        capture("formation-paused");before.recycle();after.recycle()
        val saved=runBlocking { withTimeout(15_000) { NativeLearningRepository(context).learningState.first {
            it.nutritionProgress.stageId=="formation" && it.nutritionProgress.phasePermille in 1..999
        } } }.nutritionProgress.phasePermille
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        assertEquals(saved/1000f,canvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.02f)
        rule.onNodeWithTag("r19-playback-state",true).performScrollTo().assertTextEquals(if(lang==AppLanguage.TAMIL)"இடைநிறுத்தம்" else "Paused")
        canvas();capture("formation-restored")
        rule.onNodeWithTag("r1-tab-water-balance").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r18-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-tab-nutrition").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r19-stage-formation",true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        assertEquals(saved/1000f,canvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.02f)
    }
    @Test fun maturationStagesHaveDistinctNativeEndpointsAndRealIntermediateMotion() {
        val endpoints=mutableListOf<android.graphics.Bitmap>()
        for(id in listOf("acidification","digestion","uptake")) {
            rule.onNodeWithTag("r19-stage-"+id,true).performScrollTo().performClick()
            val end=canvas().captureToImage().asAndroidBitmap();endpoints.add(end)
            rule.onNodeWithTag("r19-start-view",true).performScrollTo().performClick()
            val start=canvas().captureToImage().asAndroidBitmap();capture(id+"-start")
            assertFalse(id+" must have a distinct completed pose",start.sameAs(end))
            rule.onNodeWithTag("r19-play-pause",true).performScrollTo().performClick()
            rule.waitUntil(12_000) { rule.onNodeWithTag("r19-nutrition-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .4f.. .85f }
            rule.onNodeWithTag("r19-play-pause",true).performClick()
            rule.onNodeWithTag("r19-stage-"+id,true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            val midCanvas=canvas()
            val phase=midCanvas.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
            assertTrue("Captured phase must still be intermediate",phase in .4f.. .85f)
            runBlocking { withTimeout(15_000) { NativeLearningRepository(context).learningState.first {
                it.nutritionProgress.stageId==id && it.nutritionProgress.phasePermille in 400..850
            } } }
            val mid=midCanvas.captureToImage().asAndroidBitmap();capture(id+"-intermediate")
            assertFalse(id+" must actually animate",start.sameAs(mid));start.recycle();mid.recycle()
        }
        assertFalse("Acidification and enzymatic digestion must look different",endpoints[0].sameAs(endpoints[1]))
        assertFalse("Enzymatic digestion and solute uptake must look different",endpoints[1].sameAs(endpoints[2]))
        endpoints.forEach { it.recycle() }
    }
    private fun capture(suffix: String) {
        val a=InstrumentationRegistry.getInstrumentation().uiAutomation
        val path="/sdcard/Download/native-anatomy-evidence/"+prefix+"-"+suffix+".png"
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("screencap -p "+path)).use{it.readBytes()}
        val bytes=ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("cat "+path)).use{it.readBytes()}
        val bmp=checkNotNull(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
        assertTrue(bytes.size>1000);assertTrue(if(landscape)bmp.width>bmp.height else bmp.height>bmp.width);bmp.recycle()
    }
}
