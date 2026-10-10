package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeR17TeachingAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val args get() = InstrumentationRegistry.getArguments()
    private val lang get() = if(args.getString("r17Language")=="TAMIL") AppLanguage.TAMIL else AppLanguage.ENGLISH
    private val landscape get() = args.getString("r17Orientation")=="landscape"
    private val scale get() = args.getString("r17Scale")?.toFloat() ?: 1f
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val prefix get() = "r17-${lang.name.lowercase()}-${if(scale>1.5f)200 else 100}-${if(landscape)"landscape" else "portrait"}"
    @Before fun openNativeTextbook() {
        runBlocking { NativeLearningRepository(context).save(NativeLearningState());LanguagePreferenceRepository(context).setLanguage(lang) }
        rule.activityRule.scenario.recreate()
        rule.activityRule.scenario.onActivity { it.requestedOrientation=if(landscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        rule.waitUntil(20_000) { context.resources.configuration.orientation==if(landscape) Configuration.ORIENTATION_LANDSCAPE else Configuration.ORIENTATION_PORTRAIT }
        assertEquals(scale,context.resources.configuration.fontScale,.05f)
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r1-tab-nuclear").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r1-tab-nuclear").performScrollTo().performClick()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r17-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
    }
    private fun canvas(): SemanticsNodeInteraction {
        rule.onNodeWithTag("r17-view-plate",true).performScrollTo().performClick();rule.waitForIdle()
        val c=rule.onNodeWithTag("r17-nuclear-canvas",true)
        val b=c.fetchSemanticsNode().boundsInRoot;val r=rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Whole native plate must fit viewport: $b / $r",b.width>0 && b.height>0 && b.top>=r.top-1 && b.bottom<=r.bottom+1)
        return c
    }
    @Test fun completeStagesAndDirectNucleusTapsHaveAccessibleNativeControls() {
        for(chapter in ParameciumNuclearBiology.chapters) {
            rule.onNodeWithTag("r17-chapter-"+chapter.id,true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
            chapter.views.forEachIndexed { index,stage ->
                val button=rule.onNodeWithTag("r17-stage-"+stage.id,true)
                button.performScrollTo().assertHeightIsAtLeast(48.dp).assertHasClickAction().performClick()
                button.assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
                rule.onNodeWithTag("r17-stage-title",true).performScrollTo().assertTextEquals(
                    (if(lang==AppLanguage.TAMIL)"காட்சி " else "View ")+"${index+1}/${chapter.views.size} — "+stage.heading.value(lang))
                rule.onNodeWithTag("r17-stage-explanation",true).performScrollTo().assertTextEquals(stage.explanation.value(lang))
                rule.onNodeWithTag("r17-cell-count",true).performScrollTo().assertTextEquals(
                    (if(lang==AppLanguage.TAMIL)"காட்டப்படும் செல்கள்: " else "Cells shown: ")+stage.cellCount)
                assertEquals(2,canvas().fetchSemanticsNode().config[SemanticsActions.CustomActions].size)
                capture(chapter.id+"-"+stage.id)
            }
        }
        rule.onNodeWithTag("r17-chapter-dimorphism",true).performScrollTo().performClick()
        val c=canvas();val bounds=c.fetchSemanticsNode().boundsInRoot
        val f=NuclearFigureGeometry.fit(bounds.width,bounds.height)
        c.performTouchInput { click(Offset(f.x(780f),f.y(380f))) }
        rule.onNodeWithTag("r17-select-micronucleus",true).performScrollTo().assertHeightIsAtLeast(48.dp)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r17-organ-explanation",true).performScrollTo().assertTextEquals(ParameciumNuclearBiology.micronucleusExplanation.value(lang))
        canvas();capture("highlight-micronucleus")
        rule.onNodeWithTag("r17-select-macronucleus",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-organ-explanation",true).performScrollTo().assertTextEquals(ParameciumNuclearBiology.macronucleusExplanation.value(lang))
        canvas();capture("highlight-macronucleus")
        rule.onNodeWithTag("r17-play-pause",true).performScrollTo().assertHeightIsAtLeast(48.dp);capture("controls")
        rule.onNodeWithTag("r17-stage-whole-cell",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-reduced-motion",true).performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r17-play-pause",true).performScrollTo().assertIsNotEnabled()
        rule.onNodeWithTag("r17-next",true).performScrollTo().assertIsEnabled().performClick()
        rule.onNodeWithTag("r17-stage-somatic",true).assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r17-reduced-motion",true).performScrollTo().performClick()
    }
    @Test fun numberedCompleteReadingAndNativeAudioFallbackPersist() {
        val chapter=ParameciumNuclearBiology.dimorphism
        for(c in ParameciumNuclearBiology.chapters) {
        rule.onNodeWithTag("r17-chapter-"+c.id,true).performScrollTo().performClick()
        c.readings.forEach { reading ->
            rule.onNodeWithTag("r17-reading-"+reading.id,true).performScrollTo().performClick()
            rule.onNodeWithTag("r17-reading-heading",true).performScrollTo().assertTextEquals(reading.heading.value(lang))
            reading.paragraphs.forEachIndexed { i,p -> rule.onNodeWithTag("r17-paragraph-"+i,true).performScrollTo().assertTextEquals(p.value(lang)) }
        }
        capture(c.id+"-academic-text")
        }
        rule.onNodeWithTag("r17-chapter-dimorphism",true).performScrollTo().performClick()
        capture("academic-text")
        rule.onNodeWithTag("r17-narrate-stage",true).performScrollTo().performClick()
        rule.onNodeWithTag("r161-audio-written-fallback",true).performScrollTo()
            .assertTextEquals(chapter.views.first().explanation.value(lang))
        capture("audio-fallback")
        rule.onNodeWithTag("r161-stop-audio",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-reading-1.3",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-stage-germline",true).performScrollTo().performClick()
        // Establish a durable commit before recreation, exactly as the existing
        // process-death setup does. Compose idleness does not await DataStore IO.
        runBlocking { withTimeout(15_000) {
            NativeLearningRepository(context).learningState.first { s ->
                s.nuclearProgress.readingId=="1.3" && s.nuclearProgress.dimorphismView=="germline" &&
                    s.nuclearProgress.selectedNucleus=="micronucleus"
            }
        } }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("r17-textbook-title",true).fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("r17-reading-heading",true).performScrollTo().assertTextEquals(chapter.readings[2].heading.value(lang))
        rule.onNodeWithTag("r17-stage-germline",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r17-playback-state",true).performScrollTo().assertTextEquals(if(lang==AppLanguage.TAMIL)"இடைநிறுத்தம்" else "Paused")
        runBlocking { val p=NativeLearningRepository(context).learningState.first().nuclearProgress
            assertEquals("1.3",p.readingId);assertEquals("germline",p.dimorphismView);assertEquals("micronucleus",p.selectedNucleus) }
    }
    @Test fun realPlayAdvancesAndManualResetStopsWithoutAutoplayAfterRecreation() {
        rule.onNodeWithTag("r17-play-pause",true).performScrollTo().performClick()
        rule.waitUntil(12_000) {
            rule.onNodeWithTag("r17-stage-somatic",true).fetchSemanticsNode().config[SemanticsProperties.Selected]
        }
        rule.onNodeWithTag("r17-play-pause",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-reset",true).performScrollTo().performClick()
        rule.onNodeWithTag("r17-stage-whole-cell",true).performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Selected,true))
        rule.onNodeWithTag("r17-playback-state",true).performScrollTo().assertTextEquals(if(lang==AppLanguage.TAMIL)"இடைநிறுத்தம்" else "Paused")
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
