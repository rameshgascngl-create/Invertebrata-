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

    @Test fun androidOriginalPencilScanIntakeIsOfflineHashBoundAndDebugOnly() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation().targetContext
        // Synthetic bitmap is ONLY an Android importer test fixture.
        // It is not a pencil master and is never inserted in production assets.
        val organism = "trypanosoma"
        com.gasczoology.invertebratelab.data.NativePencilMasterStore
            .discard(context, organism)
        try {
            val fixture = android.graphics.Bitmap.createBitmap(
                900, 900, android.graphics.Bitmap.Config.ARGB_8888
            )
            fixture.eraseColor(android.graphics.Color.WHITE)
            val canvas = android.graphics.Canvas(fixture)
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.DKGRAY
                style = android.graphics.Paint.Style.STROKE
                strokeWidth = 2f
            }
            for (index in 0..330) {
                val y = 20f + (index * 37 % 840)
                canvas.drawLine(
                    10f + (index * 23 % 160), y,
                    580f + (index * 47 % 280),
                    50f + (index * 59 % 790),
                    paint
                )
            }
            val bytesOut = java.io.ByteArrayOutputStream()
            assertTrue(fixture.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, bytesOut))
            fixture.recycle()
            val bytes = bytesOut.toByteArray()
            assertTrue(bytes.size > 4096)
            val imported = com.gasczoology.invertebratelab.data.NativePencilMasterStore
                .importBytes(context, organism, bytes)
            assertTrue(imported.width == 900 && imported.height == 900)
            assertTrue(imported.format == "png")
            val expected = java.security.MessageDigest.getInstance("SHA-256")
                .digest(bytes).joinToString("") { "%02x".format(it) }
            assertTrue(imported.sourceSha256 == expected)
            val reloaded = com.gasczoology.invertebratelab.data.NativePencilMasterStore
                .load(context, organism)
            assertTrue(reloaded != null && reloaded.sourceSha256 == expected)
            assertTrue(reloaded!!.originalBytes.contentEquals(bytes))
            try {
                com.gasczoology.invertebratelab.data.NativePencilMasterStore
                    .importBytes(context, organism, "<html>not a PNG</html>".toByteArray())
                org.junit.Assert.fail("Non-image payload accepted")
            } catch (_: IllegalArgumentException) { }
            try {
                com.gasczoology.invertebratelab.data.NativePencilMasterStore
                    .importBytes(context, "../plasmodium", bytes)
                org.junit.Assert.fail("Path traversal accepted")
            } catch (_: IllegalArgumentException) { }
            // JPEG EXIF orientation must NEVER be silently ignored by the
            // BitmapFactory source-pixel reviewer. Reject rotated scans until
            // the author supplies a correctly oriented source master.
            val decoded = android.graphics.BitmapFactory.decodeByteArray(
                bytes, 0, bytes.size
            )!!
            val jpegOut = java.io.ByteArrayOutputStream()
            assertTrue(decoded.compress(
                android.graphics.Bitmap.CompressFormat.JPEG, 90, jpegOut
            ))
            decoded.recycle()
            assertTrue(
                "Normal unrotated JPEG should remain eligible for image review",
                com.gasczoology.invertebratelab.data.NativePencilMasterStore
                    .sourceOrientationAccepted("jpeg", jpegOut.toByteArray())
            )
            val rotatedFile = java.io.File.createTempFile(
                "u114-exif-rotation-", ".jpg", context.cacheDir
            )
            try {
                rotatedFile.writeBytes(jpegOut.toByteArray())
                android.media.ExifInterface(rotatedFile.absolutePath).apply {
                    setAttribute(
                        android.media.ExifInterface.TAG_ORIENTATION,
                        android.media.ExifInterface.ORIENTATION_ROTATE_90.toString(),
                    )
                    saveAttributes()
                }
                val rotatedBytes = rotatedFile.readBytes()
                assertFalse(
                    com.gasczoology.invertebratelab.data.NativePencilMasterStore
                        .sourceOrientationAccepted("jpeg", rotatedBytes)
                )
                try {
                    com.gasczoology.invertebratelab.data.NativePencilMasterStore
                        .importBytes(context, organism, rotatedBytes)
                    org.junit.Assert.fail("EXIF-rotated anatomical scan accepted")
                } catch (_: IllegalArgumentException) { }
                val preserved =
                    com.gasczoology.invertebratelab.data.NativePencilMasterStore
                        .load(context, organism)
                assertTrue("Rejected JPEG corrupted accepted PNG", preserved != null &&
                    preserved.sourceSha256 == expected)
            } finally {
                rotatedFile.delete()
            }
            enter(false)
            rule.onNodeWithTag("native-unit-1")
                .performScrollToNode(hasTestTag("chapter-u1-proto-parasites"))
            rule.onNodeWithTag("chapter-u1-proto-parasites").performClick()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("native-chapter-u1-proto-parasites")
                    .fetchSemanticsNodes().isNotEmpty()
            }
            // U1.14: reviewer intake is an independent LazyColumn page item;
            // it must be directly scrollable at 200% font, not buried beneath
            // the large attributed source-figure lesson card.
            rule.onNodeWithTag("native-chapter-u1-proto-parasites")
                .performScrollToNode(hasTestTag("u110-native-plate-intake-trypanosoma"))
            rule.onNodeWithTag("u110-import-button-trypanosoma")
                .performScrollTo().assertIsDisplayed()
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("u110-candidate-metadata-trypanosoma")
                    .fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithTag("u110-artwork-hold-trypanosoma").assertExists()
            rule.onNodeWithTag("u110-candidate-metadata-trypanosoma")
                .assertTextContains("DRAFT_UNVERIFIED")
            rule.onNodeWithTag("u110-source-checksum-trypanosoma")
                .assertTextContains(expected)
            rule.waitUntil(20_000) {
                rule.onAllNodesWithTag("u110-candidate-image-trypanosoma")
                    .fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithTag("u110-candidate-image-trypanosoma")
                .performScrollTo().assertExists()
            rule.onNodeWithTag("u110-calibrate-imported-trypanosoma")
                .performScrollTo().performClick()
            rule.onNodeWithTag("u19-review-dialog-trypanosoma").assertExists()
            rule.onNodeWithTag("u19-review-hold-trypanosoma").assertExists()
            rule.onNodeWithTag("u113-private-stage-hold-trypanosoma")
                .assertTextContains("IMPORTED UNVERIFIED")
            rule.onNodeWithTag("u110-review-morphotype-trypanosoma")
                .assertTextContains("UNVERIFIED_IMPORTED_STAGE")
            rule.onNodeWithTag("u110-review-speak-trypanosoma")
                .performScrollTo().assertIsNotEnabled()
            rule.onNodeWithTag("u19-tappable-raster-trypanosoma")
                .performScrollTo().performTouchInput { click() }
            rule.onNodeWithTag("u19-mark-count-trypanosoma")
                .assertTextContains("1", substring = true)
            screenshot("u110-imported-review")
            rule.onNodeWithTag("u19-close-trypanosoma")
                .performScrollTo().performClick()
        } finally {
            assertTrue(
                com.gasczoology.invertebratelab.data.NativePencilMasterStore
                    .discard(context, organism)
            )
        }
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
        // U1.11: without force-scrolling the new dialog, the actual bitmap
        // must already be displayed at both 100% and 200% Android font scale.
        rule.onNodeWithTag("u111-image-selection-trypanosoma").assertExists()
        rule.onNodeWithTag("u111-source-raster-visible-trypanosoma")
            .assertIsDisplayed()
        val imageRect = rule.onNodeWithTag("u111-source-raster-visible-trypanosoma")
            .fetchSemanticsNode().boundsInRoot
        assertTrue("Raster collapsed to height: " + imageRect.height,
            imageRect.height > 200f * rule.activity.resources.displayMetrics.density)
        screenshot("u111-trypanosoma-image-first")
        rule.onNodeWithTag("u110-review-morphotype-trypanosoma")
            .assertTextContains("mammalian_bloodstream_trypomastigote", substring = true)
        rule.onNodeWithTag("u110-review-explanation-trypanosoma").assertExists()
        rule.onNodeWithTag("u110-review-speak-trypanosoma")
            .performScrollTo().assertIsDisplayed().assertIsEnabled()
        rule.onNodeWithTag("u110-review-stop-trypanosoma").assertExists()
        rule.onNodeWithTag("u110-review-voice-status-trypanosoma").assertExists()
        rule.onNodeWithTag("u19-structure-trypanosoma-kinetoplast")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u110-review-explanation-trypanosoma")
            .performScrollTo().assertIsDisplayed()
            .assertTextContains("mitochondrial DNA", substring = true)
        screenshot("u110-trypanosoma-source-review")

        rule.onNodeWithTag("u19-tappable-raster-trypanosoma")
            .performScrollTo().performTouchInput { click() }
        rule.onNodeWithTag("u19-mark-count-trypanosoma")
            .assertTextContains("1", substring = true)
        // Draft image-pixel refinement does NOT create scientific hotspots.
        val originalCoordinates = rule.onNodeWithTag("u112-selected-pixel-trypanosoma")
            .performScrollTo().assertTextContains("x=", substring = true)
            .fetchSemanticsNode().config[
                androidx.compose.ui.semantics.SemanticsProperties.Text
            ].joinToString(" ") { it.text }
        rule.onNodeWithTag("u112-unverified-coordinate-note-trypanosoma").assertExists()
        rule.onNodeWithTag("u112-nudge-trypanosoma-x-plus")
            .performScrollTo().assertIsEnabled().performClick()
        val afterRight = rule.onNodeWithTag("u112-selected-pixel-trypanosoma")
            .fetchSemanticsNode().config[
                androidx.compose.ui.semantics.SemanticsProperties.Text
            ].joinToString(" ") { it.text }
        assertTrue("One-pixel adjustment did not update displayed source coords",
            afterRight != originalCoordinates)
        rule.onNodeWithTag("u112-nudge-trypanosoma-x-minus")
            .performScrollTo().assertIsEnabled().performClick()
        val backCoordinates = rule.onNodeWithTag("u112-selected-pixel-trypanosoma")
            .fetchSemanticsNode().config[
                androidx.compose.ui.semantics.SemanticsProperties.Text
            ].joinToString(" ") { it.text }
        assertTrue("One-pixel inverse adjustment drifted", backCoordinates == originalCoordinates)
        rule.onNodeWithTag("u19-tappable-raster-trypanosoma").performScrollTo()
        screenshot("u112-trypanosoma-pixel-refinement")
        rule.onNodeWithTag("u19-export-trypanosoma").assertIsEnabled()
        rule.onNodeWithTag("u19-zoom-in-trypanosoma")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u19-tappable-raster-trypanosoma").performScrollTo()
        screenshot("u19-trypanosoma-calibration")
        rule.onNodeWithTag("u19-view-reset-trypanosoma")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u19-undo-trypanosoma")
            .performScrollTo().performClick()
        rule.onNodeWithTag("u19-mark-count-trypanosoma")
            .assertTextContains("0", substring = true)
        rule.onNodeWithTag("u19-export-trypanosoma").assertIsNotEnabled()
        rule.onNodeWithTag("u19-close-trypanosoma")
            .performScrollTo().performClick()
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
            rule.onNodeWithTag("u18-next-" + id).performScrollTo().assertIsDisplayed().performClick()
            rule.waitForIdle()
            rule.onNodeWithTag("u18-stage-counter-" + id)
                .assertTextContains("Stage 2 of 6")
            screenshot("u18-" + id + "-stage2-english")
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
        rule.onNodeWithTag("u18-next-trypanosoma").performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u18-stage-counter-trypanosoma")
            .assertTextContains("நிலை 2 / 6")
        screenshot("u18-trypanosoma-stage2-tamil")
        rule.onNodeWithTag("u18-stage-explanation-trypanosoma").assertExists()
        rule.onNodeWithTag("u18-reset-trypanosoma").performScrollTo().assertIsDisplayed().performClick()
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
        rule.onNodeWithTag("u18-next-leishmania").performScrollTo().assertIsDisplayed().performClick()
        rule.onNodeWithTag("u18-stage-counter-leishmania")
            .assertTextContains("நிலை 2 / 6")
        screenshot("u18-leishmania-stage2-tamil")
        rule.onNodeWithTag("u18-stage-explanation-leishmania").assertExists()
        rule.onNodeWithTag("u18-reset-leishmania").performScrollTo().assertIsDisplayed().performClick()
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
