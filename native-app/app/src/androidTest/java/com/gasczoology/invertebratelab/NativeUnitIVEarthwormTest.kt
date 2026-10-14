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

@RunWith(AndroidJUnit4::class)
class NativeUnitIVEarthwormTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun resetToHome() {
        runBlocking {
            val context=androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enterEarthworm(tamil: Boolean) {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-4").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u4-open-earthworm-textbook").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u4-earthworm-reader").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        val scale = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext.resources.configuration.fontScale
        val key=if(scale>=1.95f)"200" else "100"
        val path="/sdcard/Download/unit4-qa/unit4-"+key+"-"+name+".png"
        val automation=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p "+path)).use { it.readBytes() }
        val bytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat "+path)).use { it.readBytes() }
        assertTrue("Missing physical-screen pixels",bytes.size>15000)
        val image=android.graphics.BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        assertNotNull("Invalid screenshot image data",image)
        assertTrue(image.height>image.width)
        image.recycle()
    }

    @Test fun englishEarthwormOpensDetailedTextbookAndSixHonestRasterSlots() {
        enterEarthworm(false)
        rule.onNodeWithTag("u4-earthworm-title").assertTextContains("Metaphire posthuma",substring=true)
        rule.onNodeWithTag("u4-earthworm-review-hold").assertExists()
        rule.onNodeWithTag("u4-earthworm-reader")
            .performScrollToNode(hasTestTag("u4-earthworm-plate-external-setae"))
        rule.onNodeWithTag("u4-earthworm-plate-pending-external-setae").assertExists()
        rule.onNodeWithTag("u4-earthworm-reader")
            .performScrollToNode(hasTestTag("u4-earthworm-plate-reproductive"))
        rule.onNodeWithTag("u4-earthworm-plate-pending-reproductive").assertExists()
        rule.onNodeWithTag("u4-earthworm-reader")
            .performScrollToNode(hasTestTag("u4-earthworm-section-digestive"))
        rule.onNodeWithTag("u4-earthworm-section-digestive").assertExists()
        screenshot("earthworm-english-digestive")
    }

    @Test fun tamilEarthwormLongExcretoryLessonAndOfflineSpeechRequest() {
        enterEarthworm(true)
        rule.onNodeWithTag("u4-earthworm-title").assertTextContains("மண்புழு",substring=true)
        rule.onNodeWithTag("u4-earthworm-reader")
            .performScrollToNode(hasTestTag("u4-earthworm-section-excretory"))
        rule.onNodeWithTag("u4-earthworm-section-excretory").assertExists()
        rule.onNodeWithTag("u4-earthworm-narrate-excretory").performScrollTo().performClick()
        rule.onNodeWithTag("u4-earthworm-voice-script-excretory").assertExists()
        screenshot("earthworm-tamil-excretory")
    }

    @Test fun backNavigationDoesNotModifyOriginalUnitFourA5Entries() {
        enterEarthworm(false)
        rule.onNodeWithTag("u4-earthworm-back").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        // LazyColumn only composes visible chapter buttons. At 200% font the
        // untouched Penaeus A5 chapter may be below the viewport, so scroll to
        // verify it exists rather than silently relaxing this assertion.
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("chapter-u4-penaeus"))
        rule.onNodeWithTag("chapter-u4-penaeus").assertExists()
        // Exercise the chapter route, not merely the existence of its lazy-list button.
        // This protects the original Penaeus A5 chapter when Earthworm is reopened.
        rule.onNodeWithTag("chapter-u4-penaeus").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-u4-penaeus").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithText("Chapter: u4-penaeus").assertExists()
        rule.onNodeWithText("Back").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("u4-open-earthworm-textbook"))
        rule.onNodeWithTag("u4-open-earthworm-textbook").performClick()
        rule.onNodeWithTag("u4-earthworm-title").assertExists()
        screenshot("earthworm-return-navigation")
    }
}
