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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

/** U1.2 isolated reference-asset routing test; NOT pencil or scientific acceptance. */
@RunWith(AndroidJUnit4::class)
class NativeUnitIProtozoaReferenceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        runBlocking {
            val context=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enter(tamil:Boolean) {
        if(tamil)rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun screenshot(name:String) {
        rule.waitForIdle()
        val automation=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().uiAutomation
        val scale=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext.resources.configuration.fontScale
        val key=if(scale>=1.95f)"200" else "100"
        val path="/sdcard/Download/u12-reference-qa/u12-"+key+"-"+name+".png"
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p "+path)).use {it.readBytes()}
        val bytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat "+path)).use {it.readBytes()}
        assertTrue(bytes.size>15000)
        val bitmap=android.graphics.BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        assertNotNull(bitmap)
        bitmap.recycle()
    }

    @Test fun englishUnitOneDisplaysFourIndependentReferenceBitmaps() {
        enter(false)
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("u1-protozoa-reference-toggle"))
        rule.onNodeWithTag("u1-protozoa-reference-toggle").performClick()
        // Opening the long Unit I gallery must expose it immediately at 100/200%
        // font size. Do not weaken this to a non-visible existence check.
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u1-protozoa-reference-gallery", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u1-protozoa-reference-gallery", useUnmergedTree = true).assertIsDisplayed()
        for(id in listOf("entamoeba","trypanosoma","leishmania","plasmodium")) {
            rule.onNodeWithTag("u1-reference-select-"+id).performScrollTo().performClick()
            rule.onNodeWithTag("u1-reference-image-"+id).performScrollTo().assertExists()
            rule.onNodeWithTag("u1-reference-missing").assertDoesNotExist()
            rule.onNodeWithTag("u1-reference-explanation").assertExists()
        }
        rule.onNodeWithTag("u1-reference-expand").performScrollTo().performClick()
        rule.onNodeWithTag("u1-reference-fullscreen").assertExists()
        rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("u1-plasmodium-phase-overview").assertExists()
        for(phase in listOf("liver","blood","mosquito")) {
            rule.onNodeWithTag("u1-plasmodium-phase-"+phase).performClick()
            rule.onNodeWithTag("u1-plasmodium-focus-"+phase).assertExists()
            rule.onNodeWithTag("u1-plasmodium-panel-missing").assertDoesNotExist()
            rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
            val actual=rule.onNodeWithTag("u1-reference-expanded-bitmap")
                .fetchSemanticsNode().boundsInRoot.height
            assertTrue("Plasmodium image collapses at normal font scale: $actual",
                actual > 195f * rule.activity.resources.displayMetrics.density)
            screenshot("plasmodium-"+phase+"-english")
        }
        rule.onNodeWithTag("u1-plasmodium-phase-blood").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-open").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertExists()
        rule.onNodeWithTag("u1-plasmodium-details-body")
            .assertTextContains("Merozoites",substring=true)
        rule.onNodeWithTag("u1-plasmodium-details-close").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertDoesNotExist()
        rule.onNodeWithTag("u1-plasmodium-phase-overview").performClick()
        rule.onNodeWithTag("u1-plasmodium-focus-overview").assertExists()
        rule.onNodeWithTag("u1-reference-zoom-in").performClick()
        rule.onNodeWithTag("u1-reference-zoom-reset").performClick()
        screenshot("plasmodium-fullscreen")
        rule.onNodeWithTag("u1-reference-close").performClick()
        rule.onNodeWithTag("u1-reference-fullscreen").assertDoesNotExist()
        screenshot("four-images-english")
    }

    @Test fun tamilParasiticProtozoaChapterRetainsOwnReferencesAndA5() {
        enter(true)
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("chapter-u1-proto-parasites"))
        rule.onNodeWithTag("chapter-u1-proto-parasites").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-u1-proto-parasites").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u15-entamoeba-morphology"))
        rule.onNodeWithTag("u15-entamoeba-heading").assertExists()
        rule.onNodeWithTag("u15-entamoeba-review-hold").assertExists()
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u1-protozoa-reference-gallery"))
        rule.onNodeWithTag("u1-protozoa-reference-gallery", useUnmergedTree = true).assertExists()
        rule.onNodeWithTag("u1-reference-image-entamoeba").performScrollTo().assertExists()
        rule.onNodeWithTag("u1-reference-select-leishmania").performScrollTo().performClick()
        rule.onNodeWithTag("u1-reference-image-leishmania").performScrollTo().assertExists()
        rule.onNodeWithTag("u1-reference-missing").assertDoesNotExist()
        rule.onNodeWithTag("u1-reference-explanation").assertTextContains("மேக்ரோபேஜ்", substring = true)
        screenshot("tamil-parasite-atlas")
        rule.onNodeWithTag("u1-reference-select-plasmodium").performScrollTo().performClick()
        rule.onNodeWithTag("u1-reference-expand").performScrollTo().performClick()
        for((phase,word) in listOf(
            "liver" to "கல்லீரல்", "blood" to "இரத்த", "mosquito" to "கொசு"
        )) {
            rule.onNodeWithTag("u1-plasmodium-phase-"+phase)
                .assertTextContains(word,substring=true).performClick()
            rule.onNodeWithTag("u1-plasmodium-focus-"+phase).assertExists()
            rule.onNodeWithTag("u1-plasmodium-panel-missing").assertDoesNotExist()
            rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
            val actual=rule.onNodeWithTag("u1-reference-expanded-bitmap")
                .fetchSemanticsNode().boundsInRoot.height
            assertTrue("Tamil enlarged-font image viewport collapsed: $actual",
                actual > 195f * rule.activity.resources.displayMetrics.density)
            screenshot("plasmodium-"+phase+"-tamil")
        }
        rule.onNodeWithTag("u1-plasmodium-phase-blood").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-open")
            .assertTextContains("விளக்கம்",substring=true).performClick()
        rule.onNodeWithTag("u1-plasmodium-details-body")
            .assertTextContains("மெரோசோயிட்டுகள்",substring=true)
        rule.onNodeWithTag("u1-plasmodium-details-close").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertDoesNotExist()
        rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("u1-reference-close").performClick()
    }
}
