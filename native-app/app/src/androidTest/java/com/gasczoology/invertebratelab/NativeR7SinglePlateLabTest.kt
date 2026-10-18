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

/** R7: the photographed-pencil source is shown once, never next to Canvas anatomy. */
@RunWith(AndroidJUnit4::class)
class NativeR7SinglePlateLabTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        runBlocking {
            val ctx = androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test fun parameciumLabRendersOnePencilSourceGalleryWithoutLegacyCanvas() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("unit-1"))
        rule.onNodeWithTag("unit-1").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u1-open-diagram-atlas").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r2-type-study-gallery-paramecium")
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r1-atlas-canvas").assertDoesNotExist()
        rule.onNodeWithTag("r11-vacuole-canvas").assertDoesNotExist()
        rule.onNodeWithTag("r2-plate-bitmap-paramecium-external-cilia")
            .performScrollTo().assertIsDisplayed()
        rule.onNodeWithTag("r7-open-paramecium-water-balance")
            .performScrollTo().assertExists()
        val inst = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val scale = inst.targetContext.resources.configuration.fontScale
        val suffix = if (scale >= 1.95f) "200" else "100"
        val name = "/sdcard/Download/five-unit-qa/r7-$suffix-paramecium-single-source-tamil.png"
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("screencap -p $name")
        ).use { it.readBytes() }
        val bytes = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            inst.uiAutomation.executeShellCommand("cat $name")
        ).use { it.readBytes() }
        assertTrue(bytes.size > 12_000)
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertNotNull(bitmap)
        assertTrue(bitmap!!.height > bitmap.width)
        bitmap.recycle()
    }
}
