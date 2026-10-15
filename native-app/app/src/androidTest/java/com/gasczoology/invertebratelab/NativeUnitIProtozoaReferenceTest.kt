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
            .assertTextContains("gametocyte stages",substring=true)
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertIsDisplayed()
        screenshot("plasmodium-blood-details-english")
        rule.onNodeWithTag("u1-plasmodium-details-close").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertDoesNotExist()
        rule.onNodeWithTag("u1-plasmodium-phase-overview").performClick()
        rule.onNodeWithTag("u1-plasmodium-focus-overview").assertExists()
        rule.onNodeWithTag("u1-reference-zoom-in").performClick()
        rule.onNodeWithTag("u1-reference-zoom-reset")
            .assertContentDescriptionEquals("Reset zoom").performClick()
        screenshot("plasmodium-fullscreen")
        rule.onNodeWithTag("u1-reference-close").performClick()
        rule.onNodeWithTag("u1-reference-fullscreen").assertDoesNotExist()
        screenshot("four-images-english")
    }

    @Test fun debugRasterReviewMapsRealBitmapPixelsWithoutPublishingHotspots() {
        enter(false)
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("chapter-u1-proto-parasites"))
        rule.onNodeWithTag("chapter-u1-proto-parasites").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-u1-proto-parasites")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u17-study-trypanosoma"))
        rule.onNodeWithTag("u19-open-raster-review-trypanosoma")
            .performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u19-review-dialog-trypanosoma").assertExists()
        rule.onNodeWithTag("u19-review-hold-trypanosoma")
            .assertTextContains("UNVERIFIED", substring = true)
        rule.onNodeWithTag("u19-raster-size-trypanosoma").assertExists()
        rule.onNodeWithTag("u19-tappable-raster-trypanosoma")
            .performTouchInput { click() }
        rule.onNodeWithTag("u19-mark-count-trypanosoma")
            .assertTextContains("1", substring = true)
        rule.onNodeWithTag("u19-export-trypanosoma").assertIsEnabled()
        rule.onNodeWithTag("u19-zoom-in-trypanosoma").performClick()
        screenshot("u19-trypanosoma-calibration")
        rule.onNodeWithTag("u19-view-reset-trypanosoma").performClick()
        rule.onNodeWithTag("u19-undo-trypanosoma").performClick()
        rule.onNodeWithTag("u19-mark-count-trypanosoma")
            .assertTextContains("0", substring = true)
        rule.onNodeWithTag("u19-export-trypanosoma").assertIsNotEnabled()
        rule.onNodeWithTag("u19-close-trypanosoma").performClick()
        rule.onNodeWithTag("u19-review-dialog-trypanosoma").assertDoesNotExist()
    }

    @Test fun englishNativeFlagellateLifeCyclesAreNavigableOffline() {
        enter(false)
        rule.onNodeWithTag("native-unit-1")
            .performScrollToNode(hasTestTag("chapter-u1-proto-parasites"))
        rule.onNodeWithTag("chapter-u1-proto-parasites").performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-u1-proto-parasites")
                .fetchSemanticsNodes().isNotEmpty()
        }
        for (id in listOf("trypanosoma", "leishmania")) {
            rule.onNodeWithTag("native-chapter-u1-proto-parasites")
                .performScrollToNode(hasTestTag("u18-cycle-" + id))
            rule.onNodeWithTag("u18-title-" + id).assertExists()
            rule.onNodeWithTag("u18-teaching-cadence-note-" + id).assertExists()
            rule.onNodeWithTag("u18-stage-counter-" + id)
                .assertTextContains("Stage 1 of 6")
            rule.onNodeWithTag("u18-next-" + id).performClick()
            rule.waitForIdle()
            rule.onNodeWithTag("u18-stage-counter-" + id)
                .assertTextContains("Stage 2 of 6")
            rule.onNodeWithTag("u18-stage-explanation-" + id).assertExists()
            rule.onNodeWithTag("u18-prev-" + id).performClick()
            rule.onNodeWithTag("u18-stage-counter-" + id)
                .assertTextContains("Stage 1 of 6")
            rule.onNodeWithTag("u18-play-pause-" + id).assertExists()
            rule.onNodeWithTag("u18-speak-" + id).assertExists()
            rule.onNodeWithTag("u18-stop-audio-" + id).assertExists()
            rule.onNodeWithTag("u18-audio-status-" + id).assertExists()
            screenshot("u18-" + id + "-english")
        }
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
            .performScrollToNode(hasTestTag("u17-flagellate-native-lessons"))
        rule.onNodeWithTag("u17-flagellate-academic-hold").assertExists()
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u17-study-trypanosoma"))
        rule.onNodeWithTag("u17-study-title-trypanosoma").assertExists()
        rule.onNodeWithTag("u17-reference-image-trypanosoma").assertExists()
        rule.onNodeWithTag("u17-image-missing-trypanosoma").assertDoesNotExist()
        rule.onNodeWithTag("u17-image-expand-trypanosoma").performClick()
        rule.onNodeWithTag("u1-reference-fullscreen").assertExists()
        rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("u1-reference-zoom-in").performClick()
        rule.onNodeWithTag("u1-reference-close").performClick()
        rule.onNodeWithTag("u1-reference-fullscreen").assertDoesNotExist()
        for (stage in listOf("morphology", "mammal", "vector", "contrast")) {
            rule.onNodeWithTag("native-chapter-u1-proto-parasites")
                .performScrollToNode(hasTestTag("u17-stage-trypanosoma-" + stage))
            rule.onNodeWithTag("u17-current-heading-trypanosoma-" + stage).assertExists()
            rule.onNodeWithTag("u17-current-explanation-trypanosoma-" + stage).assertExists()
        }
        screenshot("tamil-trypanosoma-reading")
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u18-cycle-trypanosoma"))
        rule.onNodeWithTag("u18-title-trypanosoma").assertExists()
        rule.onNodeWithTag("u18-stage-counter-trypanosoma")
            .assertTextContains("நிலை 1 / 6")
        rule.onNodeWithTag("u18-next-trypanosoma").performClick()
        rule.onNodeWithTag("u18-stage-counter-trypanosoma")
            .assertTextContains("நிலை 2 / 6")
        rule.onNodeWithTag("u18-stage-explanation-trypanosoma").assertExists()
        rule.onNodeWithTag("u18-reset-trypanosoma").performClick()
        rule.onNodeWithTag("u18-stage-counter-trypanosoma")
            .assertTextContains("நிலை 1 / 6")
        rule.onNodeWithTag("u18-audio-status-trypanosoma").assertExists()
        screenshot("u18-trypanosoma-tamil")
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u17-study-leishmania"))
        rule.onNodeWithTag("u17-study-title-leishmania").assertExists()
        rule.onNodeWithTag("u17-reference-image-leishmania").assertExists()
        rule.onNodeWithTag("u17-image-missing-leishmania").assertDoesNotExist()
        rule.onNodeWithTag("u17-image-expand-leishmania").performClick()
        rule.onNodeWithTag("u1-reference-fullscreen").assertExists()
        rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("u1-reference-close").performClick()
        for (stage in listOf("morphology", "mammal", "vector", "limits")) {
            rule.onNodeWithTag("native-chapter-u1-proto-parasites")
                .performScrollToNode(hasTestTag("u17-stage-leishmania-" + stage))
            rule.onNodeWithTag("u17-current-heading-leishmania-" + stage).assertExists()
            rule.onNodeWithTag("u17-current-explanation-leishmania-" + stage).assertExists()
        }
        screenshot("tamil-leishmania-reading")
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u18-cycle-leishmania"))
        rule.onNodeWithTag("u18-title-leishmania").assertExists()
        rule.onNodeWithTag("u18-stage-counter-leishmania")
            .assertTextContains("நிலை 1 / 6")
        rule.onNodeWithTag("u18-next-leishmania").performClick()
        rule.onNodeWithTag("u18-stage-counter-leishmania")
            .assertTextContains("நிலை 2 / 6")
        rule.onNodeWithTag("u18-stage-explanation-leishmania").assertExists()
        rule.onNodeWithTag("u18-reset-leishmania").performClick()
        rule.onNodeWithTag("u18-audio-status-leishmania").assertExists()
        screenshot("u18-leishmania-tamil")
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
            .assertTextContains("கேமீட்டோசைட்",substring=true)
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertIsDisplayed()
        screenshot("plasmodium-blood-details-tamil")
        rule.onNodeWithTag("u1-plasmodium-details-close").performClick()
        rule.onNodeWithTag("u1-plasmodium-details-dialog").assertDoesNotExist()
        rule.onNodeWithTag("u1-reference-expanded-bitmap").assertIsDisplayed()
        rule.onNodeWithTag("u1-reference-zoom-reset")
            .assertContentDescriptionEquals("பெரிதாக்கலை மீட்டமை")
        rule.onNodeWithTag("u1-reference-close").performClick()
    }
}
