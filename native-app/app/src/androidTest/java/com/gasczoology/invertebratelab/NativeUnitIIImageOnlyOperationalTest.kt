package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Operational-only Unit II gate. These tests prove that content, image-backed
 * stages and bilingual controls remain usable when an unapproved historic
 * image is genuinely missing. They NEVER grant illustration approval.
 *
 * Strict all-master acceptance remains in NativeUnitIITeachingTest and the
 * separate unit2 source-image inventory; missing PNGs must not be substituted.
 */
@RunWith(AndroidJUnit4::class)
class NativeUnitIIImageOnlyOperationalTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()
    private val context get()=InstrumentationRegistry.getInstrumentation().targetContext

    @Before fun reset(){
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enter(chapter:String,tamil:Boolean){
        if(tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-2").performScrollTo().performClick()
        rule.onNodeWithTag("native-unit-2")
            .performScrollToNode(hasTestTag("chapter-"+chapter))
        rule.onNodeWithTag("chapter-"+chapter).performClick()
        rule.waitUntil(20_000){
            rule.onAllNodesWithTag("u2-reader-"+chapter).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun capture(scene:String,plateTag:String?=null){
        if(plateTag!=null){
            rule.onNodeWithTag(plateTag).performScrollTo().assertIsDisplayed()
            rule.onNodeWithTag("u2-raster-bitmap-visible").assertExists()
        }
        rule.waitForIdle()
        val scale=if(context.resources.configuration.fontScale>=1.95f)"200" else "100"
        val path="/sdcard/Download/unit2-image-only-qa/unit2-$scale-$scene.png"
        val ui=InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p "+path)).use{it.readBytes()}
        val bytes=ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat "+path)).use{it.readBytes()}
        val picture=BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        org.junit.Assert.assertTrue("Screenshot missing: $scene",bytes.size>12000)
        org.junit.Assert.assertNotNull("Screenshot invalid: $scene",picture)
        org.junit.Assert.assertTrue("Landscape or empty screenshot",picture!!.height>picture.width)
        picture.recycle()
    }

    @Test fun syconRendersRealRasterAndAccessibleAnatomicalStages(){
        enter("u2-sycon",tamil=true)
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-static-plate-missing"))
        rule.onNodeWithTag("u2-static-plate-missing")
            .assertTextContains("SVG/Canvas",substring=true)
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-sycon-image-plate"))
        capture("sycon-raster","u2-sycon-image-plate")
        for((id,expected) in listOf(
            "prosopyle" to "03 · புரோசோபைல்",
            "apopyle" to "05 · அப்போபைல்",
            "spongocoel" to "06 · ஸ்பாஞ்சோசீல்"
        )){
            rule.onNodeWithTag("u2-stage-"+id).performScrollTo().performClick()
            rule.onNodeWithTag("u2-selected-stage").performScrollTo()
                .assertTextContains(expected,substring=true)
        }
        rule.onNodeWithTag("u2-reader-u2-sycon")
            .performScrollToNode(hasTestTag("u2-practice-heading"))
        rule.onNodeWithTag("u2-practice-heading").assertExists()
    }

    @Test fun obeliaKeepsColonyRasterAndExplicitlyMarksMissingLifeHistoryMaster(){
        enter("u2-obelia",tamil=false)
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-static-plate-missing"))
        rule.onNodeWithTag("u2-static-plate-missing")
            .assertTextContains("missing",substring=true,ignoreCase=true)
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-obelia-image-plate"))
        capture("obelia-colony-raster","u2-obelia-image-plate")
        rule.onNodeWithTag("u2-stage-planula").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-stage").performScrollTo()
            .assertTextContains("05 · Planula larva",substring=true)
        rule.onNodeWithTag("u2-atlas-asset-missing").performScrollTo()
            .assertTextContains("missing",substring=true,ignoreCase=true)
        capture("obelia-lifehistory-image-pending")
        rule.onNodeWithTag("u2-stage-settlement").performScrollTo().performClick()
        rule.onNodeWithTag("u2-settlement-choice-f").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-substage").performScrollTo()
            .assertTextContains("F · Primary feeding polyp",substring=true)
        rule.onNodeWithTag("u2-settlement-choice-e").performScrollTo().performClick()
        rule.onNodeWithTag("u2-selected-substage").performScrollTo()
            .assertTextContains("E · Attached transforming planula",substring=true)
        rule.onNodeWithTag("u2-atlas-asset-missing").performScrollTo()
            .assertTextContains("missing",substring=true,ignoreCase=true)
        capture("obelia-settlement-image-pending")
        rule.onNodeWithTag("u2-reader-u2-obelia")
            .performScrollToNode(hasTestTag("u2-practice-heading"))
        rule.onNodeWithTag("u2-practice-heading").assertExists()
    }
}
