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
        rule.onNodeWithTag("u1-protozoa-reference-gallery").assertExists()
        for(id in listOf("entamoeba","trypanosoma","leishmania","plasmodium")) {
            rule.onNodeWithTag("u1-reference-select-"+id).performScrollTo().performClick()
            rule.onNodeWithTag("u1-reference-image-"+id).performScrollTo().assertExists()
            rule.onNodeWithTag("u1-reference-missing").assertDoesNotExist()
            rule.onNodeWithTag("u1-reference-explanation").assertExists()
        }
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
            .performScrollToNode(hasTestTag("u1-protozoa-reference-gallery"))
        rule.onNodeWithTag("u1-protozoa-reference-gallery").assertExists()
        rule.onNodeWithTag("u1-reference-image-entamoeba").performScrollTo().assertExists()
        rule.onNodeWithTag("u1-reference-select-leishmania").performScrollTo().performClick()
        rule.onNodeWithTag("u1-reference-image-leishmania").performScrollTo().assertExists()
        rule.onNodeWithTag("u1-reference-missing").assertDoesNotExist()
        rule.onNodeWithTag("u1-reference-explanation").assertTextContains("மேக்ரோபேஜ்")
        screenshot("tamil-parasite-atlas")
    }
}
