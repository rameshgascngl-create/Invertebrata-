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
        val canvasTag=if(scene.startsWith("sycon"))"u2-sycon-image-plate" else "u2-obelia-image-plate"
        rule.onNodeWithTag("u2-atlas-asset-missing").assertDoesNotExist()
        val plate=rule.onNodeWithTag(canvasTag).fetchSemanticsNode().boundsInRoot
        // The image can fill the available viewport while the zoom instruction
        // is legitimately below it. Scroll that instruction into view before
        // measuring actual rendered width, then restore the image before capture.
        rule.onNodeWithTag("u2-zoom-hint").performScrollTo()
        rule.waitForIdle()
        val viewport=rule.onRoot().fetchSemanticsNode().boundsInRoot
        val hint=rule.onNodeWithTag("u2-zoom-hint").fetchSemanticsNode().boundsInRoot
        org.junit.Assert.assertTrue(
            "Zoom hint is squeezed into an unreadable column: "+scene+
                " hint="+hint.width+" viewport="+viewport.width,
            hint.width>=viewport.width*.45f
        )
        rule.onNodeWithTag(canvasTag).performScrollTo()
        rule.waitForIdle()
        val plateAfterScroll=rule.onNodeWithTag(canvasTag).fetchSemanticsNode().boundsInRoot
        val visible=(minOf(plateAfterScroll.bottom,viewport.bottom-55f)-
            maxOf(plateAfterScroll.top,viewport.top+55f)).coerceAtLeast(0f)
        org.junit.Assert.assertTrue(
            "Anatomy plate not sufficiently visible for screenshot: "+scene+
                " visible="+visible+" height="+plate.height,
            visible>=plateAfterScroll.height*.83f
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
            rule.onAllNodesWithTag("u2-sycon-image-plate").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-lesson-title").assertExists()
        rule.onNodeWithTag("u2-draft-disclosure").assertExists()
        // The oversized Tamil header can leave the atlas below the viewport.
        // First advance within the atlas item, then position the plate itself.
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo()
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo()
        captureRealScreen("sycon-external")
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo()
            .assertHasClickAction().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("03 · புரோசோபைல்")
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo()
        captureRealScreen("sycon-prosopyle")
        rule.onNodeWithTag("u2-stage-apopyle").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("05 · அப்போபைல்")
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo()
        captureRealScreen("sycon-apopyle")
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-practice-heading"))
        rule.onNodeWithTag("u2-practice-heading").assertExists()
    }
    @Test fun obeliaDistinguishesFeedingReproductivePolypMedusaAndPlanula() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.onNodeWithTag("native-unit-2")
            .performScrollToNode(hasTestTag("chapter-u2-obelia"))
        rule.onNodeWithTag("chapter-u2-obelia").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-obelia-image-plate").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-colony")
        for(id in listOf("hydranth","gonangium","medusa","planula","settlement")) {
            rule.onNodeWithTag("u2-stage-"+id).performScrollTo()
                .assertHasClickAction().performClick()
            rule.onNodeWithTag("u2-stage-explanation").assertExists()
            if(id in setOf("hydranth","gonangium","medusa","planula")) {
                rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
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

    @Test fun englishSyconImageAndTapSelectsSpongocoel() {
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("chapter-u2-sycon").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("chapter-u2-sycon").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-sycon-image-plate").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-atlas-asset-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo()
        captureRealScreen("sycon-english")
        rule.onNodeWithTag("u2-anatomical-correction").performScrollTo()
            .assertTextContains("prosopyle", substring = true, ignoreCase = true)
        // Three disjoint, anatomy-directed hit targets on the image itself.
        // Stage labels remain source-authored and the raster is still DRAFT.
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo().performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width*.600f, height*.555f))
        }
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("03 · Prosopyle")
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo().performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width*.676f, height*.461f))
        }
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("05 · Apopyle")
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo().performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width*.713f, height*.324f))
        }
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("06 · Spongocoel")
    }
    @Test fun englishObeliaImageTapAndPlanulaPlate() {
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.onNodeWithTag("native-unit-2")
            .performScrollToNode(hasTestTag("chapter-u2-obelia"))
        rule.onNodeWithTag("chapter-u2-obelia").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-obelia-image-plate").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-atlas-asset-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-anatomical-correction").performScrollTo()
            .assertTextContains("gonotheca", substring = true, ignoreCase = true)
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-english")
        rule.onNodeWithTag("u2-obelia-image-plate").performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width*.50f, height*.14f))
        }
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("02 · Feeding hydranth")
        rule.onNodeWithTag("u2-stage-planula").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("05 · Planula larva")
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-planula-english")
    }
}
