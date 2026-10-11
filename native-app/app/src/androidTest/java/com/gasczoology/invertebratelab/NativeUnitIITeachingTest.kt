package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIITeachingTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    private val context get()=InstrumentationRegistry.getInstrumentation().targetContext
    @Before fun resetToHome() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun captureRealScreen(scene: String) {
        rule.waitForIdle()
        // A successful screenshot command is not proof that the anatomy was
        // actually in the viewport. Reject header-only/mostly clipped captures.
        val canvasTag=if(scene.startsWith("sycon"))"u2-sycon-canvas" else "u2-obelia-canvas"
        val plate=rule.onNodeWithTag(canvasTag).fetchSemanticsNode().boundsInRoot
        val viewport=rule.onRoot().fetchSemanticsNode().boundsInRoot
        val visible=(minOf(plate.bottom,viewport.bottom-55f)-
            maxOf(plate.top,viewport.top+55f)).coerceAtLeast(0f)
        org.junit.Assert.assertTrue(
            "Anatomy plate not sufficiently visible for screenshot: "+scene+
                " visible="+visible+" height="+plate.height,
            visible>=plate.height*.83f
        )
        val scale = if(context.resources.configuration.fontScale >= 1.95f) "200" else "100"
        val path = "/sdcard/Download/unit2-qa/unit2-" + scale + "-" + scene + ".png"
        val ui = InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("screencap -p " + path))
            .use { it.readBytes() }
        val bytes = ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("cat " + path))
            .use { it.readBytes() }
        val bitmap = checkNotNull(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
        org.junit.Assert.assertTrue(bytes.size > 15000)
        org.junit.Assert.assertTrue(bitmap.height > bitmap.width)
        bitmap.recycle()
    }
    @Test fun syconShowsBilingualTextbookAndAnatomicalWaterRouteBeforeA5() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("chapter-u2-sycon").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("chapter-u2-sycon").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-sycon-canvas").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-lesson-title").assertExists()
        rule.onNodeWithTag("u2-draft-disclosure").assertExists()
        // The oversized Tamil header can leave the atlas below the viewport.
        // First advance within the atlas item, then position the plate itself.
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo()
        rule.onNodeWithTag("u2-sycon-canvas").performScrollTo()
        captureRealScreen("sycon-external")
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo()
            .assertHasClickAction().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("03 · புரோசோபைல்")
        rule.onNodeWithTag("u2-sycon-canvas").performScrollTo()
        captureRealScreen("sycon-prosopyle")
        rule.onNodeWithTag("u2-stage-apopyle").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("05 · அப்போபைல்")
        rule.onNodeWithTag("u2-sycon-canvas").performScrollTo()
        captureRealScreen("sycon-apopyle")
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-practice-heading"))
        rule.onNodeWithTag("u2-practice-heading").assertExists()
    }
    @Test fun obeliaDistinguishesFeedingReproductivePolypMedusaAndPlanula() {
        // Run Obelia in actual Tamil at both OS font scales, not just Sycon.
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.onNodeWithTag("native-unit-2")
            .performScrollToNode(hasTestTag("chapter-u2-obelia"))
        rule.onNodeWithTag("chapter-u2-obelia").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-obelia-canvas").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-lesson-title").assertTextEquals(
            "ஒபீலியா · கூட்டுவாழ் இன ஆய்வும் வாழ்க்கைச் சுழற்சியும்"
        )
        rule.onNodeWithTag("u2-obelia-canvas").performScrollTo()
        captureRealScreen("obelia-colony")
        for(id in listOf("hydranth","gonangium","medusa","planula","settlement")) {
            rule.onNodeWithTag("u2-stage-"+id).performScrollTo()
                .assertHasClickAction().performClick()
            rule.onNodeWithTag("u2-stage-explanation").assertExists()
            if(id in setOf("hydranth","gonangium","medusa","planula")) {
                rule.onNodeWithTag("u2-obelia-canvas").performScrollTo()
                captureRealScreen("obelia-"+id)
            }
        }
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-section-life-history"))
        rule.onNodeWithTag("u2-section-life-history").assertExists()
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-practice-heading"))
        rule.onNodeWithTag("u2-practice-heading").assertExists()
    }
}
