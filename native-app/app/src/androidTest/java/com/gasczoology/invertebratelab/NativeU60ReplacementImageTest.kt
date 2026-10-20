package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.security.MessageDigest

/** Verifies U60 replacement image BYTES and actual Compose placement on Android.
 * This is engineering evidence, never an assertion of biological sign-off.
 */
@RunWith(AndroidJUnit4::class)
class NativeU60ReplacementImageTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        val inst=InstrumentationRegistry.getInstrumentation()
        val key=if(inst.targetContext.resources.configuration.fontScale>=1.95f)"200" else "100"
        val path="/sdcard/Download/u60-qa/u60-"+key+"-"+name+".png"
        ParcelFileDescriptor.AutoCloseInputStream(inst.uiAutomation.executeShellCommand("screencap -p "+path))
            .use { it.readBytes() }
        val raw=ParcelFileDescriptor.AutoCloseInputStream(inst.uiAutomation.executeShellCommand("cat "+path))
            .use { it.readBytes() }
        assertTrue("Screenshot missing: "+path,raw.size>15000)
        val image=checkNotNull(BitmapFactory.decodeByteArray(raw,0,raw.size))
        assertTrue("Screen orientation wrong",image.height>image.width)
        image.recycle()
    }

    @Test fun sixUniqueReplacementsAreRealPackagedPngs() {
        val ctx=InstrumentationRegistry.getInstrumentation().targetContext
        val stems=listOf(
            "unit1/paramecium_external.png",
            "unit1/paramecium_oral.png",
            "unit1/paramecium_internal.png",
            "unit1/paramecium_vacuole.png",
            "unit1/references/trypanosoma.png",
            "unit1/references/leishmania.png")
        val hashes=stems.map { path ->
            val bytes=ctx.assets.open(path).use{it.readBytes()}
            assertTrue("PNG magic missing: "+path,
                bytes.take(8).toByteArray().contentEquals(
                    byteArrayOf(137.toByte(),80,78,71,13,10,26,10)))
            val bitmap=checkNotNull(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
            assertEquals("Wrong source page width: "+path,1700,bitmap.width)
            assertEquals("Wrong source page height: "+path,1700,bitmap.height)
            bitmap.recycle()
            MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") {
                "%02x".format(it)
            }
        }
        assertEquals("The same source raster appears in multiple lessons",6,hashes.toSet().size)
        val manifest=ctx.assets.open("unit1/references/U60_REPLACEMENTS_MANIFEST.json")
            .bufferedReader().use{it.readText()}
        assertTrue(manifest.contains("LadyofHats"))
        assertTrue(manifest.contains("Paramecium diagram.png"))
        assertTrue(manifest.contains("Trypanosoma_African_life_cycle_CDC.jpg"))
    }

    @Test fun tamilUnitOneUsesNewTrypanosomaAndLeishmaniaRaster() {
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
        for (id in listOf("trypanosoma","leishmania")) {
            rule.onNodeWithTag("native-unit-1")
                .performScrollToNode(hasTestTag("u17-study-"+id))
            rule.onNodeWithTag("u17-reference-image-"+id)
                .performScrollTo().assertIsDisplayed()
            rule.onNodeWithTag("u17-image-missing-"+id).assertDoesNotExist()
            screenshot(id+"-replacement-visible")
        }
    }

    @Test fun parameciumReaderNoLongerDisplaysOldPhotographedPlate() {
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r14-jump-to-pencil-plate").performScrollTo().assertExists()
        rule.onNodeWithTag("r14-jump-to-pencil-plate").performClick()
        rule.onNodeWithTag("r14-main-external-png").assertExists()
        screenshot("paramecium-replacement-visible")
    }
}
