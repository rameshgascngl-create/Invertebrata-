package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** R6 checks real image decode, accessibility, Tamil and zoom; no Canvas substitute. */
@RunWith(AndroidJUnit4::class)
class NativeR6ParameciumPencilTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        runBlocking {
            val ctx = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) { rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty() }
    }

    private fun find(tag: String) {
        rule.onNodeWithTag("native-chapter-u1-paramecium").performScrollToNode(hasTestTag(tag))
        rule.onNodeWithTag(tag).assertExists()
    }

    @Test fun parameciumShowsFourPlateSlotsAndZoomsInTamil() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("unit-1"))
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("chapter-u1-paramecium"))
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-u1-paramecium").fetchSemanticsNodes().isNotEmpty()
        }
        find("r2-type-study-gallery-paramecium")
        for (plate in listOf("external-cilia", "oral-apparatus", "contractile-vacuoles")) {
            val tag = "r2-plate-bitmap-paramecium-" + plate
            find(tag)
            rule.onNodeWithTag(tag).assertIsDisplayed()
        }
        find("r2-plate-slot-paramecium-locomotion")
        rule.assertPlateState("paramecium", "locomotion")
        val zoom = "r2-plate-expand-paramecium-contractile-vacuoles"
        find(zoom)
        rule.onNodeWithTag(zoom).performClick()
        rule.onNodeWithTag("r2-fullscreen-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("r6-zoom-viewport-clipped").assertExists()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("100%", substring = true)
        rule.onNodeWithTag("r2-zoom-in").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("200%", substring = true)
        rule.onNodeWithTag("r2-zoom-reset").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("r6-zoom-level").assertTextContains("100%", substring = true)
        val inst = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val scale = inst.targetContext.resources.configuration.fontScale
        val suffix = if (scale >= 1.95f) "200" else "100"
        val shot = "/sdcard/Download/five-unit-qa/r6-$suffix-unit1-paramecium-pencil-tamil.png"
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("screencap -p $shot")
        ).use { it.readBytes() }
        val bytes = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("cat $shot")
        ).use { it.readBytes() }
        assertTrue("No valid device screenshot", bytes.size > 12000)
        val bmp = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertNotNull("Screenshot not decodable", bmp)
        assertTrue(bmp!!.height > bmp.width)
        bmp.recycle()
        rule.onNodeWithTag("r2-fullscreen-close").performClick()
        find("r2-plate-slot-paramecium-locomotion")
    }
}
