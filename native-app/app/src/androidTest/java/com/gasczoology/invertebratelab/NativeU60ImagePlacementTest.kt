package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Exact-source student image-routing test. Pending replacements are a HOLD:
 * not academic artwork approval and not a claim that any new PNG was bundled.
 */
@RunWith(AndroidJUnit4::class)
class NativeU60ImagePlacementTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enterUnit(tamil: Boolean) {
        if(tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun openChapter(id: String) {
        rule.onNodeWithTag("native-unit-1").performScrollToNode(hasTestTag("chapter-"+id))
        rule.onNodeWithTag("chapter-"+id).performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-chapter-"+id).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun shot(scene: String) {
        val inst=InstrumentationRegistry.getInstrumentation()
        val scale=if(inst.targetContext.resources.configuration.fontScale>=1.95f) "200" else "100"
        val path="/sdcard/Download/u60-qa/u60-"+scale+"-"+scene+".png"
        ParcelFileDescriptor.AutoCloseInputStream(inst.uiAutomation.executeShellCommand(
            "screencap -p "+path)).use { it.readBytes() }
        val data=ParcelFileDescriptor.AutoCloseInputStream(inst.uiAutomation.executeShellCommand(
            "cat "+path)).use { it.readBytes() }
        assertTrue("Screenshot missing",data.size>12000)
        val bmp=BitmapFactory.decodeByteArray(data,0,data.size)
        assertNotNull(bmp)
        assertTrue(bmp!!.height>bmp.width)
        bmp.recycle()
    }

    @Test fun unitOverviewKeepsEntamoebaPlasmodiumButNotWithdrawnFlagellateCartoons() {
        enterUnit(false)
        rule.onNodeWithTag("native-unit-1").performScrollToNode(
            hasTestTag("u1-protozoa-reference-toggle"))
        rule.onNodeWithTag("u1-protozoa-reference-toggle").performClick()
        rule.onNodeWithTag("u1-reference-select-entamoeba").assertExists()
        rule.onNodeWithTag("u1-reference-select-plasmodium").assertExists()
        rule.onNodeWithTag("u1-reference-select-trypanosoma").assertDoesNotExist()
        rule.onNodeWithTag("u1-reference-select-leishmania").assertDoesNotExist()
        shot("reference-scope")
    }

    @Test fun parasiteTextbookShowsDistinctMissingImageSlotsAndRetainsBilingualLessons() {
        enterUnit(true)
        openChapter("u1-proto-parasites")
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u17-study-trypanosoma"))
        rule.onNodeWithTag("u17-image-missing-trypanosoma").assertExists()
        rule.onNodeWithTag("u17-reference-image-trypanosoma").assertDoesNotExist()
        shot("trypanosoma-replacement-pending")
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u17-study-leishmania"))
        rule.onNodeWithTag("u17-image-missing-leishmania").assertExists()
        rule.onNodeWithTag("u17-reference-image-leishmania").assertDoesNotExist()
        shot("leishmania-replacement-pending")
        rule.onNodeWithTag("native-chapter-u1-proto-parasites")
            .performScrollToNode(hasTestTag("u17-stage-leishmania-morphology"))
        rule.onNodeWithTag("u17-current-explanation-leishmania-morphology")
            .assertTextContains("அமாஸ்டிகோட்",substring=true)
        rule.onNodeWithTag("u1-independent-protozoa-reference-atlas").assertDoesNotExist()
    }

    @Test fun parameciumTypeStudyShowsFourSlotsWithoutReusingOnePhotograph() {
        enterUnit(true)
        openChapter("u1-paramecium")
        for (plate in listOf("external-cilia","oral-apparatus","contractile-vacuoles","locomotion")) {
            rule.onNodeWithTag("native-chapter-u1-paramecium")
                .performScrollToNode(hasTestTag("r2-plate-slot-paramecium-"+plate))
            rule.assertPlateState("paramecium", plate)
        }
        shot("paramecium-missing-unique-plates")
        // The A5 heading is a lazy-list item below the four reference slots.\n        // Scroll it into composition before asserting it, including at 200% Tamil.\n        rule.onNodeWithTag("native-chapter-u1-paramecium")\n            .performScrollToNode(hasTestTag("restoration-a5-heading"))\n        rule.onNodeWithTag("restoration-a5-heading").assertIsDisplayed()
    }
}
