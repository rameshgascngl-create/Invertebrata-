package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

/** Scientific textbook assertions rendered on Android at real 100%/200% font scale. */
@RunWith(AndroidJUnit4::class)
class NativePilaBiologyTextbookTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before fun reset() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        waitFor("native-home")
    }

    private fun waitFor(tag: String) = rule.waitUntil(30_000) {
        rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
    }

    private fun capture(lang: String, label: String, scale: Int) {
        rule.waitForIdle()
        val dest = "/sdcard/Download/pila-biology-qa"
        val filename = "$dest/pila-$lang-$scale-$label.png"
        val ui = InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("mkdir -p $dest"))
            .use { it.readBytes() }
        ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("screencap -p $filename"))
            .use { it.readBytes() }
        val bytes = ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("cat $filename"))
            .use { it.readBytes() }
        assertTrue("No screenshot: $filename", bytes.size > 10_000)
        val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertTrue("Invalid portrait screenshot: $filename",
            image != null && image.height > image.width)
        image?.recycle()
    }

    private fun inspect(language: AppLanguage) {
        val targetScale = InstrumentationRegistry.getArguments()
            .getString("expectedScale")?.toFloatOrNull()
            ?: error("expectedScale missing from CI instrumentation arguments")
        val actualScale = context.resources.configuration.fontScale
        assertTrue("Wrong Android font scale: $actualScale expected $targetScale",
            abs(actualScale - targetScale) < 0.06f)
        val scale = if (actualScale > 1.5f) 200 else 100
        val lang = if (language == AppLanguage.TAMIL) "tamil" else "english"
        rule.onNodeWithTag("language-$lang").performClick()
        rule.onNodeWithTag("unit-5").performScrollTo().performClick()
        waitFor("native-unit-5")
        rule.onNodeWithTag("native-unit-5")
            .performScrollToNode(hasTestTag("chapter-u5-pila"))
        rule.onNodeWithTag("chapter-u5-pila").performClick()
        val reader = "book-reader-u5-pila"
        waitFor(reader)
        rule.onNodeWithTag("chapter-language-toolbar").assertIsDisplayed()

        val egg = if (language == AppLanguage.TAMIL)
            "வெண்மையான சுண்ணாம்புச் சத்துடைய" else "pale, calcareous eggs"
        rule.onNodeWithTag(reader).performScrollToNode(hasText(egg, substring = true))
        rule.onNodeWithText(egg, substring = true).assertExists()
        capture(lang, "egg-laying", scale)

        val operculum = "book-def-operculum"
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag(operculum))
        rule.onNodeWithTag(operculum).assertExists()
        val opText = if (language == AppLanguage.TAMIL) "உட்புறம் சுண்ணாம்புச் சத்தால்"
            else "horny externally and calcified internally"
        rule.onNodeWithText(opText, substring = true).assertExists()
        capture(lang, "operculum", scale)

        val torsion = "book-note-torsion-is-completed-before-hatching"
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag(torsion))
        rule.onNodeWithTag(torsion).assertIsDisplayed()
        val torsionText = if (language == AppLanguage.TAMIL) "முட்டைக்குள் வளரும்போது"
            else "During the development of each Pila within its egg"
        rule.onNodeWithText(torsionText, substring = true).assertExists()
        capture(lang, "torsion", scale)
    }

    @Test fun englishPilaEggsOperculumAndTorsion() = inspect(AppLanguage.ENGLISH)
    @Test fun tamilPilaEggsOperculumAndTorsion() = inspect(AppLanguage.TAMIL)
}
