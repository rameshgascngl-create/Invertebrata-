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

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class R19PhysiologyGate

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

    @R19PhysiologyGate @Test fun stagesAndRealStructureTapsAreVisibleAndAccessible() {
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
        rule.onNodeWithTag("r19-nutrition-inline-play-pause",true).performScrollTo().assertIsNotEnabled().assertHeightIsAtLeast(48.dp)
        capture("controls")
    }
    @R19PhysiologyGate @Test fun numberedReadingAndNarrationKeepCompleteWrittenExplanations() {
        for(reading in ParameciumNutrition.readings) {
            rule.onNodeWithTag("r19-reading-"+reading.id,true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.onNodeWithTag("r19-reading-heading",true).performScrollTo().assertTextEquals(reading.heading.value(lang))
            reading.paragraphs.forEachIndexed { i,p -> rule.onNodeWithTag("r19-paragraph-"+i,true).performScrollTo().assertTextEquals(p.value(lang)) }
            capture("reading-"+reading.id)
            val script=reading.paragraphs.joinToString("\n\n") {it.value(lang)}
            assertTrue("Complete subsection must fit the Android TTS input limit",script.length<=android.speech.tts.TextToSpeech.getMaxSpeechInputLength())
            rule.onNodeWithTag("r19-narrate-reading",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.onNodeWithTag("r161-audio-written-fallback",true).performScrollTo().assertTextEquals(script)
            if(context.packageManager.queryIntentServices(android.content.Intent(android.speech.tts.TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE),0).isEmpty()) {
                rule.waitUntil(10_000) {rule.onAllNodesWithTag("r1-speech-status",true).fetchSemanticsNodes().any {n ->
                    n.config[SemanticsProperties.Text].any {it.text.contains(if(lang==AppLanguage.TAMIL)"குரல் கிடைக்கவில்லை" else "voice unavailable")}
                }}
            }
            capture("reading-audio-"+reading.id)
            rule.onNodeWithTag("r161-stop-audio",true).performScrollTo().performClick()
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
        rule.onNodeWithTag("r19-nutrition-inline-play-pause",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitUntil(12_000) { rule.onNodeWithTag("r19-nutrition-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .35f.. .8f }
        rule.onNodeWithTag("r19-nutrition-inline-play-pause",true).performScrollTo().performClick()
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
            rule.onNodeWithTag("r19-nutrition-inline-play-pause",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.waitUntil(12_000) { rule.onNodeWithTag("r19-nutrition-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .4f.. .85f }
            rule.onNodeWithTag("r19-nutrition-inline-play-pause",true).performScrollTo().performClick()
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
    private fun egestionCanvas(): SemanticsNodeInteraction {
        rule.onNodeWithTag("r19-view-egestion-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val c=rule.onNodeWithTag("r19-egestion-canvas",true)
        val b=c.fetchSemanticsNode().boundsInRoot;val r=rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Complete cytoproct plate must fit viewport",b.top>=r.top-1 && b.bottom<=r.bottom+1 && b.width>0)
        return c
    }
    @Test fun cytoproctReallyOpensReleasesResiduesAndReclosesWithAccessibleHits() {
        val ends=mutableListOf<android.graphics.Bitmap>()
        for(id in ParameciumEgestion.stageIds) {
            rule.onNodeWithTag("r19-stage-"+id,true).performScrollTo().performClick()
            val end=egestionCanvas().captureToImage().asAndroidBitmap();ends.add(end);capture("cytoproct-"+id+"-end")
            rule.onNodeWithTag("r19-start-view",true).performScrollTo().performClick()
            val start=egestionCanvas().captureToImage().asAndroidBitmap();capture("cytoproct-"+id+"-start")
            assertFalse(id+" must change the actual cytoproct pixels",start.sameAs(end))
            rule.onNodeWithTag("r19-cytoproct-inline-play-pause",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            rule.waitUntil(12_000) {rule.onNodeWithTag("r19-egestion-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .4f.. .85f}
            rule.onNodeWithTag("r19-cytoproct-inline-play-pause",true).performScrollTo().performClick()
            rule.onNodeWithTag("r19-stage-"+id,true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            val mid=egestionCanvas();val phase=mid.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
            assertTrue("$id captured phase=$phase must remain intermediate",phase in .4f.. .85f)
            val pixels=mid.captureToImage().asAndroidBitmap();assertFalse(start.sameAs(pixels));capture("cytoproct-"+id+"-intermediate")
            start.recycle();pixels.recycle()
        }
        assertFalse(ends[0].sameAs(ends[1]));assertFalse(ends[1].sameAs(ends[2]));ends.forEach {it.recycle()}
        for(m in EgestionFigure.marks) {
            val c=egestionCanvas();assertEquals(3,c.fetchSemanticsNode().config[SemanticsActions.CustomActions].size)
            val b=c.fetchSemanticsNode().boundsInRoot;val f=EgestionFigure.fit(b.width,b.height)
            c.performTouchInput {click(Offset(f.x(m.x),f.y(m.y)))}
            rule.onNodeWithTag("r19-select-"+m.id,true).performScrollTo().assertHeightIsAtLeast(48.dp)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            rule.onNodeWithTag("r19-organ-explanation",true).performScrollTo().assertTextEquals(ParameciumNutrition.structure(m.id).explanation.value(lang))
            egestionCanvas();capture("cytoproct-highlight-"+m.id)
        }
    }
    @Test fun leavingDuringPlaybackCheckpointsPhaseWithoutOverwritingNavigation() {
        rule.onNodeWithTag("r19-stage-egestion",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-cytoproct-inline-play-pause",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitUntil(12_000) {rule.onNodeWithTag("r19-egestion-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .35f.. .8f}
        // Invoke Android Back immediately while motion is still intermediate. Scrolling through
        // a long chapter to a top tab can legitimately outlast the illustrative three-second stage.
        rule.activityRule.scenario.onActivity {it.onBackPressedDispatcher.onBackPressed()}
        val saved=runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.first {
            it.destination==StudyDestination.HOME && it.nutritionProgress.stageId=="egestion" && it.nutritionProgress.phasePermille in 1..999
        }}}.nutritionProgress
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        assertEquals(saved.phasePermille/1000f,egestionCanvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.02f)
        rule.onNodeWithTag("r19-playback-state",true).performScrollTo().assertTextEquals(if(lang==AppLanguage.TAMIL)"இடைநிறுத்தம்" else "Paused")
        rule.onNodeWithTag("r1-tab-water-balance").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r18-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.first {it.laboratoryTab=="water-balance" && it.nutritionProgress==saved}}}
        rule.onNodeWithTag("r1-tab-nutrition").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        assertEquals(saved.phasePermille/1000f,egestionCanvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.02f)
        rule.activityRule.scenario.onActivity {it.onBackPressedDispatcher.onBackPressed()}
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()}
        runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.onEach {Log.i("R19_NUTRITION","home record ${it.destination} ${it.laboratoryTab} ${it.nutritionProgress}; expected=$saved")}.first {it.destination==StudyDestination.HOME && it.nutritionProgress==saved}}}
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        assertEquals(saved.phasePermille/1000f,egestionCanvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.02f)
        capture("navigation-checkpoint-restored")
    }
    private fun physiologyCanvas(): SemanticsNodeInteraction {
        rule.onNodeWithTag("r19-view-physiology-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val c=rule.onNodeWithTag("r19-physiology-canvas",true)
        val b=c.fetchSemanticsNode().boundsInRoot;val r=rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Complete native physiology plate must fit viewport",b.top>=r.top-1 && b.bottom<=r.bottom+1 && b.width>0)
        return c
    }
    @R19PhysiologyGate @Test fun physiologicalViewsDistinguishExchangeFromEnergyMetabolismWithRealMotion() {
        val ends=mutableListOf<android.graphics.Bitmap>()
        for(id in ParameciumPhysiology.stageIds) {
            rule.onNodeWithTag("r19-stage-"+id,true).performScrollTo().performClick()
            val end=physiologyCanvas().captureToImage().asAndroidBitmap();ends.add(end);capture("physiology-"+id+"-end")
            rule.onNodeWithTag("r19-start-view",true).performScrollTo().performClick()
            val start=physiologyCanvas().captureToImage().asAndroidBitmap();capture("physiology-"+id+"-start")
            assertFalse(id+" must have distinguishable endpoints",start.sameAs(end))
            // The playback clock advances every 50 ms. Scroll *before* playback:
            // scrolling the multiline Tamil Pause control after observing a
            // midframe lets the real clock cross the accepted 0.85 limit.
            // The monotonic 0.45 trigger does not change the required
            // 0.40–0.85 captured intermediate range below.
            val pauseButton=rule.onNodeWithTag("r19-physiology-inline-play-pause",true)
            pauseButton.performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            // Monotonic phase trigger cannot miss a short sampling window:
            // invoke the actual exposed Compose OnClick action in the SAME
            // waitUntil evaluation that first observes phase >= 0.45. The
            // frozen result is still required to lie in 0.40..0.85 below.
            var observedAtPause = -1f
            rule.waitUntil(12_000) {
                val observed = rule.onNodeWithTag("r19-physiology-canvas",true)
                    .fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
                if (observed < .45f) false else {
                    observedAtPause = observed
                    pauseButton.performSemanticsAction(SemanticsActions.OnClick)
                    true
                }
            }
            Log.i("R19_PAUSE_ISOLATION",
                "stage=$id phase_on_pause_action=$observedAtPause")
            rule.onNodeWithTag("r19-playback-state",true).assertTextEquals(
                if(lang==AppLanguage.TAMIL) "இடைநிறுத்தம்" else "Paused")
            val c=physiologyCanvas()
            val phase=c.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current
            assertTrue("$id must capture a genuine intermediate phase: $phase",phase in .4f.. .85f)
            rule.onNodeWithTag("r19-stage-"+id,true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.first {it.nutritionProgress.stageId==id && it.nutritionProgress.phasePermille in 400..850}}}
            val pixels=c.captureToImage().asAndroidBitmap();assertFalse(start.sameAs(pixels));capture("physiology-"+id+"-intermediate")
            start.recycle();pixels.recycle()
        }
        assertFalse(ends[0].sameAs(ends[1]));assertFalse(ends[1].sameAs(ends[2]));ends.forEach {it.recycle()}
        for(m in PhysiologyFigure.marks) {
            val c=physiologyCanvas();assertEquals(3,c.fetchSemanticsNode().config[SemanticsActions.CustomActions].size)
            val b=c.fetchSemanticsNode().boundsInRoot;val f=PhysiologyFigure.fit(b.width,b.height)
            c.performTouchInput {click(Offset(f.x(m.x),f.y(m.y)))}
            rule.onNodeWithTag("r19-select-"+m.id,true).performScrollTo().assertHeightIsAtLeast(48.dp)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
            rule.onNodeWithTag("r19-organ-explanation",true).performScrollTo().assertTextEquals(ParameciumNutrition.structure(m.id).explanation.value(lang))
            physiologyCanvas();capture("physiology-highlight-"+m.id)
        }
        rule.onNodeWithTag("r19-reduced-motion",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-physiology-inline-play-pause",true).performScrollTo().assertIsNotEnabled().assertHeightIsAtLeast(48.dp)
    }
    @R19PhysiologyGate @Test fun readerLinkAndActualRotationPreserveDetailedLearningPosition() {
        rule.onNodeWithTag("r19-reading-5.11",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-stage-metabolism",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-select-mitochondrion",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-physiology-inline-play-pause",true).performScrollTo().performClick()
        rule.waitUntil(12_000) {rule.onNodeWithTag("r19-physiology-canvas",true).fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current in .4f.. .85f}
        rule.onNodeWithTag("r19-physiology-inline-play-pause",true).performScrollTo().performClick()
        val saved=runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.first {
            it.nutritionProgress.stageId=="metabolism" && it.nutritionProgress.readingId=="5.11" &&
                it.nutritionProgress.selectedStructure=="mitochondrion" && it.nutritionProgress.phasePermille in 400..850
        }}}.nutritionProgress
        rule.activityRule.scenario.onActivity {it.requestedOrientation=if(landscape)ActivityInfo.SCREEN_ORIENTATION_PORTRAIT else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE}
        rule.waitUntil(20_000) {context.resources.configuration.orientation==if(landscape)Configuration.ORIENTATION_PORTRAIT else Configuration.ORIENTATION_LANDSCAPE}
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        assertEquals(scale,context.resources.configuration.fontScale,.05f)
        assertEquals(saved.phasePermille/1000f,physiologyCanvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.001f)
        rule.onNodeWithTag("r19-playback-state",true).performScrollTo().assertTextEquals(if(lang==AppLanguage.TAMIL)"இடைநிறுத்தம்" else "Paused")
        physiologyCanvas();capture("rotated-paused-physiology",!landscape)
        rule.activityRule.scenario.onActivity {it.requestedOrientation=if(landscape)ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT}
        rule.waitUntil(20_000) {context.resources.configuration.orientation==if(landscape)Configuration.ORIENTATION_LANDSCAPE else Configuration.ORIENTATION_PORTRAIT}
        rule.onNodeWithTag("r1-tab-study").performScrollTo().performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r14-contents-toggle",true).fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r14-contents-toggle",true).performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-feeding-and-digestion",true).performScrollTo().performClick()
        rule.onNodeWithTag("r19-from-reader",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitUntil(20_000) {rule.onAllNodesWithTag("r19-textbook-title",true).fetchSemanticsNodes().isNotEmpty()}
        rule.onNodeWithTag("r19-reading-heading",true).performScrollTo().assertTextEquals(ParameciumPhysiology.readings.last().heading.value(lang))
        assertEquals(saved.phasePermille/1000f,physiologyCanvas().fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo].current,.001f)
        runBlocking {withTimeout(15_000) {NativeLearningRepository(context).learningState.first {it.laboratoryTab=="nutrition" && it.nutritionProgress==saved}}}
        capture("reader-link-restored-physiology")
    }

    private fun capture(suffix: String,landscapeExpected: Boolean=landscape) {
        val a=InstrumentationRegistry.getInstrumentation().uiAutomation
        val path="/sdcard/Download/native-anatomy-evidence/"+prefix+"-"+suffix+".png"
        ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("screencap -p "+path)).use{it.readBytes()}
        val bytes=ParcelFileDescriptor.AutoCloseInputStream(a.executeShellCommand("cat "+path)).use{it.readBytes()}
        val bmp=checkNotNull(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
        assertTrue(bytes.size>1000);assertTrue(if(landscapeExpected)bmp.width>bmp.height else bmp.height>bmp.width);bmp.recycle()
    }
}
