package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.*
import com.gasczoology.invertebratelab.ui.UnitIIIDraftComparisonIntake
import com.gasczoology.invertebratelab.ui.UnitIIIComparisonReportJson
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIIITeachingTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
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

    private fun openChapter(id: String, tamil: Boolean) {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-3").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-3").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-3")
            .performScrollToNode(hasTestTag("chapter-"+id))
        rule.onNodeWithTag("chapter-"+id).performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("u3-reader-"+id).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun capture(scene: String) {
        rule.waitForIdle()
        val context=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext
        val scale=if(context.resources.configuration.fontScale >= 1.95f) "200" else "100"
        val path="/sdcard/Download/unit3-qa/unit3-"+scale+"-"+scene+".png"
        val ui=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p "+path)).use { it.readBytes() }
        val bytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat "+path)).use { it.readBytes() }
        val screenshot=checkNotNull(
            android.graphics.BitmapFactory.decodeByteArray(bytes,0,bytes.size))
        org.junit.Assert.assertTrue(bytes.size>15000)
        org.junit.Assert.assertTrue(screenshot.height>screenshot.width)
        screenshot.recycle()
    }

    @Test fun fasciolaHasDistinctHistoricalLessonFigureAndOrderedSimulation() {
        openChapter("u3-fasciola",false)
        rule.onNodeWithTag("u3-lesson-title").assertExists()
        rule.onNodeWithTag("u3-draft-warning").assertExists()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-static-pencil-raster"))
        rule.onNodeWithTag("u3-static-pencil-raster").assertExists()
        rule.onNodeWithTag("u3-static-pencil-raster").performScrollTo()
        capture("fasciola-historical")
        rule.onNodeWithTag("u3-pencil-master-pending").assertDoesNotExist()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-interactive-life-cycle"))
        rule.onNodeWithTag("u3-stage-miracidium-1")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-current-life-stage")
            .performScrollTo().assertTextContains("miracidium",substring=true,ignoreCase=true)
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-practice-heading"))
        rule.onNodeWithTag("u3-practice-heading").assertExists()
    }

    @Test fun ascarisHasNoSnailAndHasCorrectLungMigration() {
        openChapter("u3-ascaris",true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-static-pencil-raster"))
        rule.onNodeWithTag("u3-static-pencil-raster").assertExists()
        rule.onNodeWithTag("u3-static-pencil-raster").performScrollTo()
        capture("ascaris-historical")
        rule.onNodeWithTag("u3-pencil-master-pending").assertDoesNotExist()
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-interactive-life-cycle"))
        rule.onNodeWithTag("u3-stage-lung-3")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-current-life-stage")
            .performScrollTo().assertTextContains("நுரையீரல்",substring=true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-practice-heading"))
        rule.onNodeWithTag("u3-practice-heading").assertExists()
    }

    @Test fun sourcedFasciolaPlateRejectsObsoleteTouchCoordinates() {
        openChapter("u3-fasciola",false)
        for (plate in listOf("external","digestive-reproductive","life-cycle")) {
            rule.onNodeWithTag("u3-reader-u3-fasciola")
                .performScrollToNode(hasTestTag("u3-plate-bitmap-"+plate))
            rule.onNodeWithTag("u3-plate-bitmap-"+plate).assertExists()
            rule.onNodeWithTag("u3-plate-pending-"+plate).assertDoesNotExist()
        }
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-plate-touch-external"))
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
        rule.onNodeWithTag("u3-reader-u3-fasciola")
            .performScrollToNode(hasTestTag("u3-hotspot-external-acetabulum"))
        rule.onNodeWithTag("u3-hotspot-external-acetabulum")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-external")
            .assertTextContains("Ventral sucker",substring=true)
        rule.onNodeWithTag("u3-organ-explanation-external")
            .assertTextContains("attachment sucker", substring=true)
        rule.onNodeWithTag("u3-voice-request-external")
            .assertTextContains("attachment sucker", substring=true)
        rule.onNodeWithTag("u3-speak-organ-external").assertExists()
        capture("fasciola-revised")
        // A real raster can now be inspected at 2x/4x. These buttons must
        // not activate rejected source-image anatomy hotspots.
        rule.onNodeWithTag("u3-zoom-level-external")
            .performScrollTo().assertTextContains("1×",substring=true)
        rule.onNodeWithTag("u3-zoom-in-external").performScrollTo().performClick()
        rule.onNodeWithTag("u3-zoom-level-external").assertTextContains("2×",substring=true)
        rule.onNodeWithTag("u3-zoom-in-external").performScrollTo().performClick()
        rule.onNodeWithTag("u3-zoom-level-external").assertTextContains("4×",substring=true)
        rule.onNodeWithTag("u3-zoom-in-external").assertIsNotEnabled()
        rule.onNodeWithTag("u3-plate-bitmap-external").performScrollTo().assertIsDisplayed()
        capture("fasciola-magnified-4x")
        rule.onNodeWithTag("u3-zoom-reset-external").performScrollTo().performClick()
        rule.onNodeWithTag("u3-zoom-level-external").assertTextContains("1×",substring=true)
        rule.onNodeWithTag("u3-zoom-out-external").assertIsNotEnabled()
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
        // Full-screen review must show the decoded raster in its own tall
        // viewport, not below a long lesson or inside an over-zoomed card.
        rule.onNodeWithTag("u3-open-fullscreen-external")
            .performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u3-fullscreen-dialog-external").assertExists()
        rule.onNodeWithTag("u3-fullscreen-real-bitmap-external").assertIsDisplayed()
        val fullscreenBounds = rule.onNodeWithTag("u3-fullscreen-image-viewport-external")
            .fetchSemanticsNode().boundsInRoot
        val density = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext.resources.displayMetrics.density
        org.junit.Assert.assertTrue("Fasciola image viewport too short", fullscreenBounds.height >= 350f * density)
        rule.onNodeWithTag("u3-fullscreen-zoom-level-external")
            .assertTextContains("1×", substring=true)
        capture("fasciola-fullscreen-1x")
        rule.onNodeWithTag("u3-fullscreen-zoom-in-external").performClick()
        rule.onNodeWithTag("u3-fullscreen-zoom-level-external")
            .assertTextContains("2×", substring=true)
        rule.onNodeWithTag("u3-fullscreen-zoom-in-external").performClick()
        rule.onNodeWithTag("u3-fullscreen-zoom-level-external")
            .assertTextContains("4×", substring=true)
        rule.onNodeWithTag("u3-fullscreen-zoom-in-external").assertIsNotEnabled()
        rule.onNodeWithTag("u3-fullscreen-academic-hold-external").assertExists()
        capture("fasciola-fullscreen-4x")
        rule.onNodeWithTag("u3-fullscreen-close-external").performClick()
        rule.onNodeWithTag("u3-fullscreen-dialog-external").assertDoesNotExist()
        // The DEBUG-only reviewer must not enable learner image hotspots.
        rule.onNodeWithTag("u33-open-review-external")
            .performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u33-review-dialog-external").assertExists()
        rule.onNodeWithTag("u33-review-decoded-image-external").assertIsDisplayed()
        val reviewerBoundsFasciola = rule.onNodeWithTag("u33-review-original-raster-external")
            .fetchSemanticsNode().boundsInRoot
        org.junit.Assert.assertTrue(
            "At 200% font image is displaced by long labels",
            reviewerBoundsFasciola.height >= 230f * density
        )
        rule.onNodeWithTag("u33-review-export-external").assertIsNotEnabled()
        rule.onNodeWithTag("u34-compare-import-external").assertIsNotEnabled()
        rule.onNodeWithTag("u33-review-original-raster-external")
            .performTouchInput {
                click(androidx.compose.ui.geometry.Offset(width*.5f,height*.5f))
            }
        rule.onNodeWithTag("u33-review-last-point-external")
            .assertTextContains("DRAFT",substring=true)
        rule.waitUntil(20_000) {
            rule.onAllNodes(hasTestTag("u33-review-export-external") and isEnabled())
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u33-review-source-status-external")
            .assertTextContains("SHA-256:",substring=true)
        rule.onNodeWithTag("u34-compare-import-external").assertIsEnabled()
        capture("fasciola-compare-ready")
        rule.onNodeWithTag("u33-review-hold-external")
            .assertTextContains("UNAPPROVED",substring=true)
            .performClick()
        rule.onNodeWithTag("u35-full-safety-description-external")
            .assertTextContains("Direct student hotspots remain disabled",substring=true)
        capture("fasciola-full-warning")
        rule.onNodeWithTag("u35-full-safety-close-external").performClick()
        capture("fasciola-source-review-draft")
        rule.onNodeWithTag("u33-review-clear-external").performClick()
        rule.onNodeWithTag("u33-review-export-external").assertIsNotEnabled()
        rule.onNodeWithTag("u34-compare-import-external").assertIsNotEnabled()
        rule.onNodeWithTag("u33-review-close-external").performClick()
        rule.onNodeWithTag("u33-review-dialog-external").assertDoesNotExist()
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
    }

    @Test fun tamilAscarisPlateUsesAccessibleUnmappedOrganControl() {
        openChapter("u3-ascaris",true)
        for (plate in listOf("male-female-external","female-internal","life-cycle")) {
            rule.onNodeWithTag("u3-reader-u3-ascaris")
                .performScrollToNode(hasTestTag("u3-plate-bitmap-"+plate))
            rule.onNodeWithTag("u3-plate-bitmap-"+plate).assertExists()
            rule.onNodeWithTag("u3-plate-pending-"+plate).assertDoesNotExist()
        }
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-plate-touch-male-female-external"))
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-hotspot-male-female-external-female-vulva"))
        rule.onNodeWithTag("u3-hotspot-male-female-external-female-vulva")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-male-female-external")
            .assertTextContains("வல்வா",substring=true)
        rule.onNodeWithTag("u3-organ-explanation-male-female-external")
            .assertTextContains("வல்வா", substring=true)
        rule.onNodeWithTag("u3-voice-request-male-female-external")
            .assertTextContains("வல்வா", substring=true)
        rule.onNodeWithTag("u3-speak-organ-male-female-external").assertExists()
        capture("ascaris-revised")
    }

    @Test fun cdcCompleteAscarisSourceAndTamilCycleNarrationAreAccessible() {
        openChapter("u3-ascaris",true)
        // Capture REAL onscreen decoded graphite pixels at both font scales,
        // before scrolling down to the accessible organ controls.
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-plate-bitmap-life-cycle"))
        rule.onNodeWithTag("u3-plate-bitmap-life-cycle").assertIsDisplayed()
        capture("ascaris-graphite-bitmap-visible")
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-cdc-complete-life-cycle-credit"))
        rule.onNodeWithTag("u3-cdc-complete-life-cycle-credit")
            .assertTextContains("CDC DPDx",substring=true)
        // Verify a real graphite-tone raster is packaged, not the former
        // full-colour source or unreviewed generative scene reconstruction.
        val ctx=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext
        ctx.assets.open("type_studies/ascaris/life-cycle.png").use { stream ->
            val bitmap=checkNotNull(android.graphics.BitmapFactory.decodeStream(stream))
            org.junit.Assert.assertEquals(960,bitmap.width)
            org.junit.Assert.assertEquals(1280,bitmap.height)
            for(y in 90 until 1180 step 73) for(x in 40 until 930 step 47) {
                val pix=bitmap.getPixel(x,y)
                val rr=android.graphics.Color.red(pix)
                val gg=android.graphics.Color.green(pix)
                val bb=android.graphics.Color.blue(pix)
                org.junit.Assert.assertTrue("Chromatic artwork at ($x,$y)",
                    kotlin.math.abs(rr-gg)<=3 && kotlin.math.abs(gg-bb)<=4)
            }
            bitmap.recycle()
        }
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-hotspot-life-cycle-unfertilized"))
        rule.onNodeWithTag("u3-hotspot-life-cycle-unfertilized")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-life-cycle")
            .assertTextContains("கருவுறாத",substring=true)
        rule.onNodeWithTag("u3-voice-request-life-cycle")
            .assertTextContains("கருவுறாத",substring=true)
        rule.onNodeWithTag("u3-reader-u3-ascaris")
            .performScrollToNode(hasTestTag("u3-hotspot-life-cycle-swallow"))
        rule.onNodeWithTag("u3-hotspot-life-cycle-swallow")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u3-selected-organ-life-cycle")
            .assertTextContains("மீண்டும் விழுங்குதலும்",substring=true)
        rule.onNodeWithTag("u3-voice-request-life-cycle")
            .assertTextContains("மீண்டும் விழுங்கப்படுகின்றன",substring=true)
        capture("ascaris-cdc-complete")
        rule.onNodeWithTag("u3-zoom-level-life-cycle")
            .performScrollTo().assertTextContains("1×",substring=true)
        rule.onNodeWithTag("u3-zoom-in-life-cycle").performScrollTo().performClick()
        rule.onNodeWithTag("u3-zoom-level-life-cycle").assertTextContains("2×",substring=true)
        rule.onNodeWithTag("u3-zoom-in-life-cycle").performScrollTo().performClick()
        rule.onNodeWithTag("u3-zoom-level-life-cycle").assertTextContains("4×",substring=true)
        rule.onNodeWithTag("u3-plate-bitmap-life-cycle").performScrollTo().assertIsDisplayed()
        capture("ascaris-magnified-4x")
        rule.onNodeWithTag("u3-zoom-reset-life-cycle").performScrollTo().performClick()
        rule.onNodeWithTag("u3-zoom-level-life-cycle").assertTextContains("1×",substring=true)
        // Interacting with magnification may never enable unresolved geometry.
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
        rule.onNodeWithTag("u3-open-fullscreen-life-cycle")
            .performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u3-fullscreen-dialog-life-cycle").assertExists()
        rule.onNodeWithTag("u3-fullscreen-real-bitmap-life-cycle").assertIsDisplayed()
        val fullscreenBounds = rule.onNodeWithTag("u3-fullscreen-image-viewport-life-cycle")
            .fetchSemanticsNode().boundsInRoot
        val density = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext.resources.displayMetrics.density
        org.junit.Assert.assertTrue("Ascaris Tamil image viewport too short", fullscreenBounds.height >= 350f * density)
        capture("ascaris-fullscreen-1x")
        rule.onNodeWithTag("u3-fullscreen-zoom-in-life-cycle").performClick()
        rule.onNodeWithTag("u3-fullscreen-zoom-in-life-cycle").performClick()
        rule.onNodeWithTag("u3-fullscreen-zoom-level-life-cycle")
            .assertTextContains("4×", substring=true)
        rule.onNodeWithTag("u3-fullscreen-academic-hold-life-cycle").assertExists()
        capture("ascaris-fullscreen-4x")
        rule.onNodeWithTag("u3-fullscreen-reset-life-cycle").performClick()
        rule.onNodeWithTag("u3-fullscreen-zoom-level-life-cycle")
            .assertTextContains("1×", substring=true)
        rule.onNodeWithTag("u3-fullscreen-close-life-cycle").performClick()
        rule.onNodeWithTag("u3-fullscreen-dialog-life-cycle").assertDoesNotExist()
        rule.onNodeWithTag("u33-open-review-life-cycle")
            .performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u33-review-dialog-life-cycle").assertExists()
        rule.onNodeWithTag("u33-review-decoded-image-life-cycle").assertIsDisplayed()
        val reviewerBoundsAscaris = rule.onNodeWithTag("u33-review-original-raster-life-cycle")
            .fetchSemanticsNode().boundsInRoot
        org.junit.Assert.assertTrue(
            "At 200% font image is displaced by long labels",
            reviewerBoundsAscaris.height >= 230f * density
        )
        rule.onNodeWithTag("u33-review-export-life-cycle").assertIsNotEnabled()
        rule.onNodeWithTag("u34-compare-import-life-cycle").assertIsNotEnabled()
        rule.onNodeWithTag("u33-review-original-raster-life-cycle")
            .performTouchInput {
                click(androidx.compose.ui.geometry.Offset(width*.5f,height*.5f))
            }
        rule.onNodeWithTag("u33-review-last-point-life-cycle")
            .assertTextContains("DRAFT",substring=true)
        rule.waitUntil(20_000) {
            rule.onAllNodes(hasTestTag("u33-review-export-life-cycle") and isEnabled())
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("u33-review-hold-life-cycle")
            .assertTextContains("ஒப்புதல் இல்லை",substring=true)
            .performClick()
        rule.onNodeWithTag("u35-full-safety-description-life-cycle")
            .assertTextContains("மாணவர்களுக்கான",substring=true)
        capture("ascaris-full-warning")
        rule.onNodeWithTag("u35-full-safety-close-life-cycle").performClick()
        rule.onNodeWithTag("u34-compare-import-life-cycle").assertIsEnabled()
        // Exercise the untrusted draft parser using the U3.3 contract.
        val fakeSha="e".repeat(64)
        val fakeJson=org.json.JSONObject()
            .put("schema","invertebrata.u33.source-pixel-review.v1")
            .put("approval_status","DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF")
            .put("plate_asset_stem","type_studies/ascaris/life-cycle")
            .put("actual_apk_png_sha256",fakeSha)
            .put("source_width_px",960).put("source_height_px",1280)
            .put("marks",org.json.JSONArray().put(
                org.json.JSONObject().put("organ_id","ingestion")
                    .put("pixel_x",320.0).put("pixel_y",450.0)))
            .toString()
        val parsed=UnitIIIDraftComparisonIntake.parse(fakeJson)
        org.junit.Assert.assertEquals(fakeSha,parsed.sourceSha256)
        org.junit.Assert.assertEquals(1,parsed.marks.size)
        // A real native report serializes exact source SHA, pixels and findings.
        // It cannot contain academic approval, even at perfect agreement.
        val reference = UnitIIIPixelReviewDraft("type_studies/ascaris/life-cycle",
            fakeSha,960,1280,listOf(UnitIIIPixelReviewMark("ingestion",320f,450f)))
        val other = UnitIIIPixelReviewDraft("type_studies/ascaris/life-cycle",
            fakeSha,960,1280,listOf(UnitIIIPixelReviewMark("ingestion",340f,462f)))
        val compared=UnitIIIEndpointAgreement.compare(reference,other)
        val report=org.json.JSONObject(UnitIIIComparisonReportJson.serialize(compared))
        org.junit.Assert.assertEquals(UnitIIIComparisonReportJson.SCHEMA,
            report.getString("schema"))
        org.junit.Assert.assertEquals(fakeSha,report.getString("source_png_sha256"))
        org.junit.Assert.assertFalse(report.getBoolean("anatomical_accuracy_approved"))
        org.junit.Assert.assertFalse(report.getBoolean("student_image_hotspots_authorized"))
        org.junit.Assert.assertFalse(report.getBoolean("independent_reviewers_verified"))
        org.junit.Assert.assertEquals("REQUIRES_ANATOMY_REVIEW",
            report.getJSONArray("comparison_rows").getJSONObject(0).getString("finding"))
        org.junit.Assert.assertTrue(runCatching {
            UnitIIIDraftComparisonIntake.parse(
                org.json.JSONObject(fakeJson).put("approval_status","APPROVED").toString())
        }.isFailure)
        org.junit.Assert.assertTrue(runCatching {
            UnitIIIDraftComparisonIntake.parse(
                org.json.JSONObject(fakeJson)
                    .put("plate_asset_stem","type_studies/fasciola/external").toString())
        }.isFailure)
        capture("ascaris-compare-ready")
        capture("ascaris-source-review-draft")
        rule.onNodeWithTag("u33-review-close-life-cycle").performClick()
        rule.onNodeWithTag("u33-review-dialog-life-cycle").assertDoesNotExist()
        rule.onAllNodesWithTag("u3-source-hotspot-review-hold").onFirst().assertExists()
    }

    @Test fun fasciolaProvisionalImageIntakeNeverOverwritesBundledAnatomy() {
        val ctx=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext
        val stem="type_studies/fasciola/external"
        val original=ctx.assets.open(stem+".png").use { it.readBytes() }
        val expectedHash=java.security.MessageDigest.getInstance("SHA-256")
            .digest(original).joinToString("") { "%02x".format(it) }
        UnitIIIPencilScanCandidateStore.discard(ctx,stem)
        try {
            // For engineering QA ONLY, import a packaged reference as an
            // intentionally UNVERIFIED candidate. It is not a pencil scan.
            val candidate=UnitIIIPencilScanCandidateStore.importBytes(ctx,stem,original)
            org.junit.Assert.assertEquals(expectedHash,candidate.sourceSha256)
            org.junit.Assert.assertEquals("UNVERIFIED_USER_SUPPLIED_SOURCE",
                candidate.approvalStatus)
            org.junit.Assert.assertEquals(expectedHash,
                requireNotNull(UnitIIIPencilScanCandidateStore.load(ctx,stem)).sourceSha256)
            org.junit.Assert.assertTrue(runCatching {
                UnitIIIPencilScanCandidateStore.importBytes(ctx,stem,byteArrayOf(1,2,3,4))
            }.isFailure)
            org.junit.Assert.assertEquals(expectedHash,
                requireNotNull(UnitIIIPencilScanCandidateStore.load(ctx,stem)).sourceSha256)
            // A second organism's stage cannot be addressed by a false file stem.
            org.junit.Assert.assertTrue(runCatching {
                UnitIIIPencilScanCandidateStore.validate(
                    "type_studies/fasciola/../../ascaris/external",original)
            }.isFailure)
            org.junit.Assert.assertTrue(runCatching {
                UnitIIIPencilScanCandidateStore.validate(
                    "type_studies/ascaris/nonexistent",original)
            }.isFailure)
            openChapter("u3-fasciola",false)
            rule.onNodeWithTag("u3-reader-u3-fasciola")
                .performScrollToNode(hasTestTag("u36-open-source-intake-external"))
            rule.onNodeWithTag("u36-open-source-intake-external")
                .performScrollTo().performClick()
            rule.onNodeWithTag("u36-source-intake-dialog-external").assertExists()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("u36-candidate-image-external")
                    .fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithTag("u36-candidate-image-external").assertIsDisplayed()
            rule.onNodeWithTag("u36-candidate-summary-external")
                .assertTextContains("UNVERIFIED",substring=true)
            rule.onNodeWithTag("u36-export-manifest-external").assertIsEnabled()
            rule.onNodeWithTag("u36-choose-source-external").assertIsEnabled()
            rule.onNodeWithTag("u36-identity-not-approval-external")
                .assertIsDisplayed().performClick()
            rule.onNodeWithTag("u36-full-disclosure-text-external")
                .assertTextContains("two genuinely independent zoologists",substring=true,ignoreCase=true)
            capture("fasciola-pencil-intake-full-disclosure")
            rule.onNodeWithTag("u36-full-disclosure-close-external").performClick()
            rule.onNodeWithTag("u36-full-disclosure-text-external").assertDoesNotExist()
            capture("fasciola-pencil-intake-provisional")
            rule.onNodeWithTag("u36-source-intake-close-external").performClick()
            rule.onNodeWithTag("u36-source-intake-dialog-external").assertDoesNotExist()
            // Never mutate the bundled image or activate student hotspots.
            org.junit.Assert.assertArrayEquals(
                original,ctx.assets.open(stem+".png").use { it.readBytes() })
            org.junit.Assert.assertFalse(UnitIIIPlateHotspots.geometryApproved(stem))
        } finally {
            org.junit.Assert.assertTrue(UnitIIIPencilScanCandidateStore.discard(ctx,stem))
        }
    }

    @Test fun tamilAscarisSourceIntakePendingWithoutUnverifiedImage() {
        val ctx=androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext
        val stem="type_studies/ascaris/life-cycle"
        UnitIIIPencilScanCandidateStore.discard(ctx,stem)
        try {
            openChapter("u3-ascaris",true)
            rule.onNodeWithTag("u3-reader-u3-ascaris")
                .performScrollToNode(hasTestTag("u36-open-source-intake-life-cycle"))
            rule.onNodeWithTag("u36-open-source-intake-life-cycle")
                .performScrollTo().performClick()
            rule.onNodeWithTag("u36-source-intake-dialog-life-cycle").assertExists()
            rule.onNodeWithTag("u36-no-candidate-life-cycle").assertExists()
            rule.onNodeWithTag("u36-choose-source-life-cycle").assertIsEnabled()
            rule.onNodeWithTag("u36-export-manifest-life-cycle").assertDoesNotExist()
            rule.onNodeWithTag("u36-identity-not-approval-life-cycle")
                .assertIsDisplayed().performClick()
            rule.onNodeWithTag("u36-full-disclosure-text-life-cycle")
                .assertTextContains("Fasciola/Ascaris",substring=true)
            capture("ascaris-pencil-intake-full-disclosure")
            rule.onNodeWithTag("u36-full-disclosure-close-life-cycle").performClick()
            rule.onNodeWithTag("u36-full-disclosure-text-life-cycle").assertDoesNotExist()
            capture("ascaris-pencil-intake-pending")
            rule.onNodeWithTag("u36-source-intake-close-life-cycle").performClick()
            rule.onNodeWithTag("u36-source-intake-dialog-life-cycle").assertDoesNotExist()
            org.junit.Assert.assertFalse(UnitIIIPlateHotspots.geometryApproved(stem))
        } finally {
            org.junit.Assert.assertTrue(UnitIIIPencilScanCandidateStore.discard(ctx,stem))
        }
    }

    @Test fun allSevenUnitThreeChaptersOpenNativeTextbooksBeforeQuestions() {
        rule.onNodeWithTag("unit-3").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-3").fetchSemanticsNodes().isNotEmpty()
        }
        for (id in UnitIIILessons.chapterIds) {
            rule.onNodeWithTag("native-unit-3")
                .performScrollToNode(hasTestTag("chapter-"+id))
            rule.onNodeWithTag("chapter-"+id).performClick()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("u3-reader-"+id).fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithTag("u3-lesson-title").assertExists()
            rule.onNodeWithTag("u3-back").performClick()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("native-unit-3").fetchSemanticsNodes().isNotEmpty()
            }
        }
    }
}
