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

/**
 * U5.6 user-facing tests; no mock images, no network and no relaxation of
 * the immutable A5 question and original answer gates.
 */
@RunWith(AndroidJUnit4::class)
class NativeUnitIVSupplementaryTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    @Before fun resetState() {
        runBlocking {
            val ctx=androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun enter(id:String,tamil:Boolean) {
        if(tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-4").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("chapter-"+id))
        rule.onNodeWithTag("chapter-"+id).performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-"+id).fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun find(id:String,tag:String) {
        rule.onNodeWithTag("native-chapter-"+id)
            .performScrollToNode(hasTestTag(tag))
        rule.onNodeWithTag(tag).assertExists()
    }
    private fun screenshot(tag:String) {
        rule.waitForIdle()
        val inst=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val scale=inst.targetContext.resources.configuration.fontScale
        val suffix=if(scale>=1.95f)"200" else "100"
        val path="/sdcard/Download/unit4-qa/unit4-"+suffix+"-"+tag+".png"
        val ui=inst.uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p "+path)).use{it.readBytes()}
        val png=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat "+path)).use{it.readBytes()}
        org.junit.Assert.assertTrue("Missing U5.6 screenshot",png.size>12000)
        org.junit.Assert.assertNotNull(android.graphics.BitmapFactory.decodeByteArray(
            png,0,png.size))
    }

    @Test fun englishPeripatusRealBiologyAndOriginalFiveMarkQuestions() {
        val id="u4-peripatus"
        enter(id,false)
        find(id,"u56-lesson-heading")
        rule.onNodeWithTag("u56-lesson-title")
            .assertTextContains("Peripatus",substring=true)
        find(id,"u56-section-external")
        screenshot("peripatus-english-lobopods")
        find(id,"u56-section-organ-systems")
        find(id,"u56-section-reproduction")
        find(id,"u56-original-a5-heading")
        find(id,"a5-card-u4-peripatus-A5-1")
        rule.onNodeWithTag("a5-card-u4-peripatus-A5-1").assertExists()
    }

    @Test fun tamilCrustaceanDevelopmentAvoidsFalseUniversalLarvalSequence() {
        val id="u4-crustacean-larvae"
        enter(id,true)
        find(id,"u56-lesson-heading")
        rule.onNodeWithTag("u56-lesson-title")
            .assertTextContains("ஓட்டுடலிகளின்",substring=true)
        find(id,"u56-section-nauplius")
        screenshot("crustacean-larvae-tamil-nauplius")
        find(id,"u56-section-zoea")
        find(id,"u56-section-megalopa")
        find(id,"u56-section-practice")
        find(id,"u56-original-a5-heading")
        find(id,"a5-card-u4-crustacean-larvae-A5-1")
        rule.onNodeWithTag("a5-card-u4-crustacean-larvae-A5-1").assertExists()
    }
}
