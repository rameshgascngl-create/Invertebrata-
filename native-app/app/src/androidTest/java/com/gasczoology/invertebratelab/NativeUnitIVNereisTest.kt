package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIVNereisTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun resetState() {
        runBlocking {
            val ctx=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun enter(tamil:Boolean) {
        if(tamil)rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-4").performScrollTo().performClick()
        rule.waitUntil(20_000){
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("chapter-u4-nereis"))
        rule.onNodeWithTag("chapter-u4-nereis").performClick()
        rule.waitUntil(20_000){
            rule.onAllNodesWithTag("native-chapter-u4-nereis").fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun find(tag:String) {
        rule.onNodeWithTag("native-chapter-u4-nereis")
            .performScrollToNode(hasTestTag(tag))
    }
    private fun screen(name:String) {
        rule.waitForIdle()
        val scale=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.configuration.fontScale
        val suffix=if(scale>=1.95f)"200" else "100"
        val nameOnDisk="/sdcard/Download/unit4-qa/unit4-"+suffix+"-"+name+".png"
        val ui=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p "+nameOnDisk)).use { it.readBytes() }
        val bytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat "+nameOnDisk)).use {it.readBytes()}
        org.junit.Assert.assertTrue("Nereis screenshot missing",bytes.size>12000)
        org.junit.Assert.assertNotNull(android.graphics.BitmapFactory.decodeByteArray(
            bytes,0,bytes.size))
    }

    @Test fun eightStaticNoninteractiveNereisPlatesAndOriginalA5() {
        enter(false)
        find("u44-nereis-introduction")
        rule.onNodeWithTag("u44-nereis-editorial-disclosure").assertExists()
        for(i in 1..8) {
            find("u44-nereis-static-plate-"+i)
            rule.onNodeWithTag("u44-nereis-pencil-style-drawing-"+i).assertExists()
            rule.onNodeWithTag("u44-nereis-image-provenance-"+i).assertExists()
        }
        find("u44-nereis-static-plate-3")
        screen("nereis-english-parapodium")
        find("u44-nereis-lessons-heading")
        rule.onNodeWithTag("u44-nereis-lessons-heading")
            .assertTextContains("14 EXPLANATORY",substring=true)
        find("a5-card-u4-nereis-A5-1")
        rule.onNodeWithTag("a5-card-u4-nereis-A5-1").assertExists()
    }

    @Test fun tamilPlateLegendsAndSubstantiveNereisBiologyAtLargeFonts() {
        enter(true)
        find("u44-nereis-static-plate-7")
        rule.onNodeWithTag("u44-nereis-plate-7-term-0")
            .assertTextContains("நெப்ரோஸ்டோம்",substring=true)
        screen("nereis-tamil-nephridium")
        find("u44-nereis-static-plate-8")
        rule.onNodeWithTag("u44-nereis-plate-8-term-1")
            .assertTextContains("புரோட்டோட்ரோக்",substring=true)
        find("u44-nereis-section-larvae")
        rule.onNodeWithTag("u44-nereis-section-larvae").assertExists()
        find("u44-nereis-section-parapodia")
        rule.onNodeWithTag("u44-nereis-section-parapodia").assertExists()
        find("u44-nereis-static-plate-3")
        screen("nereis-tamil-parapodium")
    }
}
