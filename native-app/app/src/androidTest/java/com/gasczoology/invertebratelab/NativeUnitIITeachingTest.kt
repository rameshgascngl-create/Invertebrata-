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
        // A rendered Compose Canvas fallback is not a valid teaching atlas.
        // Assert the real decoded raster bitmap exists in the semantics tree.
        rule.onNodeWithTag("u2-raster-bitmap-visible").assertExists()
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
            rule.onAllNodesWithTag("u2-reader-u2-sycon").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-lesson-title").assertExists()
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-sycon-historic-static-raster"))
        rule.onNodeWithTag("u2-sycon-historic-static-raster").assertExists()
        rule.onNodeWithTag("u2-static-plate-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-sycon-image-plate"))
        rule.onNodeWithTag("u2-sycon-image-plate").assertExists()
        // Title and disclosure were verified before moving to the independent
        // static illustration; lazy column may dispose the header off-screen.
        // The oversized Tamil header can leave the atlas below the viewport.
        // First advance within the atlas item, then position the plate itself.
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo()
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo()
        captureRealScreen("sycon-external")
        // Image has no hotspots: stage is selected through accessible native buttons.
        rule.onNodeWithTag("u2-stage-prosopyle").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("03 · புரோசோபைல்")
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
            rule.onAllNodesWithTag("u2-reader-u2-obelia").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-colony-static-raster"))
        rule.onNodeWithTag("u2-obelia-colony-static-raster").assertExists()
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-life-static-raster"))
        rule.onNodeWithTag("u2-obelia-life-static-raster").assertExists()
        rule.onNodeWithTag("u2-static-plate-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-image-plate"))
        rule.onNodeWithTag("u2-obelia-image-plate").assertExists()
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-colony")
        for(id in listOf("hydranth","gonangium","medusa","planula","settlement")) {
            rule.onNodeWithTag("u2-stage-"+id).performScrollTo()
                .assertHasClickAction().performClick()
            rule.onNodeWithTag("u2-stage-explanation").assertExists()
            if(id in setOf("hydranth","gonangium","medusa","planula","settlement")) {
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

    @Test fun englishSyconImageAndAccessibleStageControlsSelectSpongocoel() {
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("chapter-u2-sycon").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("chapter-u2-sycon").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-reader-u2-sycon").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-lesson-title").assertExists()
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-sycon-historic-static-raster"))
        rule.onNodeWithTag("u2-sycon-historic-static-raster").assertExists()
        rule.onNodeWithTag("u2-static-plate-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-sycon-image-plate"))
        rule.onNodeWithTag("u2-sycon-image-plate").assertExists()
        rule.onNodeWithTag("u2-atlas-asset-missing").assertDoesNotExist()
        // A rendered Compose Canvas fallback is not a valid teaching atlas.
        // Assert the real decoded raster bitmap exists in the semantics tree.
        rule.onNodeWithTag("u2-raster-bitmap-visible").assertExists()
        rule.onNodeWithTag("u2-sycon-image-plate").performScrollTo()
        captureRealScreen("sycon-english")
        rule.onNodeWithTag("u2-anatomical-correction").performScrollTo()
            .assertTextContains("prosopyle", substring = true, ignoreCase = true)
        // Native semantic stage controls replace anatomy taps.
        for ((id,expected) in listOf(
            "prosopyle" to "03 · Prosopyle",
            "apopyle" to "05 · Apopyle",
            "spongocoel" to "06 · Spongocoel"
        )) {
            rule.onNodeWithTag("u2-stage-"+id).performScrollTo().performClick()
            rule.onNodeWithTag("u2-selected-stage").performScrollTo()
                .assertTextEquals(expected)
        }
    }
    @Test fun englishObeliaStaticImageAndPlanulaStageButtons() {
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.onNodeWithTag("native-unit-2")
            .performScrollToNode(hasTestTag("chapter-u2-obelia"))
        rule.onNodeWithTag("chapter-u2-obelia").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u2-reader-u2-obelia").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-colony-static-raster"))
        rule.onNodeWithTag("u2-obelia-colony-static-raster").assertExists()
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-life-static-raster"))
        rule.onNodeWithTag("u2-obelia-life-static-raster").assertExists()
        rule.onNodeWithTag("u2-static-plate-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-image-plate"))
        rule.onNodeWithTag("u2-obelia-image-plate").assertExists()
        rule.onNodeWithTag("u2-atlas-asset-missing").assertDoesNotExist()
        // A rendered Compose Canvas fallback is not a valid teaching atlas.
        // Assert the real decoded raster bitmap exists in the semantics tree.
        rule.onNodeWithTag("u2-raster-bitmap-visible").assertExists()
        rule.onNodeWithTag("u2-anatomical-correction").performScrollTo()
            .assertTextContains("gonotheca", substring = true, ignoreCase = true)
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-english")
        rule.onNodeWithTag("u2-stage-hydranth").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("02 · Feeding hydranth")
        rule.onNodeWithTag("u2-stage-planula").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("05 · Planula larva")
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-planula-english")
        // This must be the *new high-resolution PNG*, not the superseded
        // 730x300 WebP strip or a Compose fallback.
        context.assets.open("unit2/obelia_lifecycle.png").use { stream ->
            val info=android.graphics.BitmapFactory.Options().apply { inJustDecodeBounds=true }
            BitmapFactory.decodeStream(stream,null,info)
            org.junit.Assert.assertEquals("image/png",info.outMimeType)
            org.junit.Assert.assertEquals(2200,info.outWidth)
            org.junit.Assert.assertEquals(3440,info.outHeight)
        }
        for((stage,expected) in listOf(
            "medusa" to "04 · Free medusa",
            "planula" to "05 · Planula larva",
            "settlement" to "06 · Settlement and new colony"
        )) {
            rule.onNodeWithTag("u2-stage-"+stage).performScrollTo().performClick()
            rule.onNodeWithTag("u2-selected-stage").performScrollTo()
                .assertTextEquals(expected)
        }
        // The settlement stage MUST switch to Wolcott's hand-drawn PNG.
        context.assets.open("unit2/obelia_settlement.png").use { stream ->
            val info=android.graphics.BitmapFactory.Options().apply { inJustDecodeBounds=true }
            BitmapFactory.decodeStream(stream,null,info)
            org.junit.Assert.assertEquals("image/png",info.outMimeType)
            org.junit.Assert.assertEquals(1500,info.outWidth)
            org.junit.Assert.assertEquals(1250,info.outHeight)
        }
        rule.onNodeWithTag("u2-atlas-asset-missing").assertDoesNotExist()
        rule.onNodeWithTag("u2-raster-bitmap-visible").assertExists()
        rule.onNodeWithTag("u2-obelia-image-plate").performScrollTo()
        captureRealScreen("obelia-settlement-english")
        // E and F remain independently accessible without diagram touch targets.
        rule.onNodeWithTag("u2-settlement-choice-f").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("06 · Settlement and new colony")
        rule.onNodeWithTag("u2-selected-substage").performScrollTo()
            .assertTextContains("F · Primary feeding polyp", substring=true)
        rule.onNodeWithTag("u2-settlement-choice-e").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextEquals("06 · Settlement and new colony")
        rule.onNodeWithTag("u2-selected-substage").performScrollTo()
            .assertTextContains("E · Attached transforming planula", substring=true)
    }
}
