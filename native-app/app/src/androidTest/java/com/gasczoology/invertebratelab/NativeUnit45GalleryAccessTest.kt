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

/** Verify optional raster reference galleries without duplicate default textbook figures. */
@RunWith(AndroidJUnit4::class)
class NativeUnit45GalleryAccessTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before fun resetHome() {
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

    private fun saveScreenshot(id: String, lang: String, phase: String) {
        rule.waitForIdle()
        val font = if (context.resources.configuration.fontScale > 1.5f) "200" else "100"
        val file = "/sdcard/Download/unit45-gallery-qa/${id}-${lang}-${font}-${phase}.png"
        val ui = InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("screencap -p $file"))
            .use { it.readBytes() }
        val bytes = ParcelFileDescriptor.AutoCloseInputStream(ui.executeShellCommand("cat $file"))
            .use { it.readBytes() }
        assertTrue("Screenshot missing $file", bytes.size > 10000)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertTrue("Unreadable screenshot", bitmap != null && bitmap.height > bitmap.width)
        bitmap?.recycle()
    }

    private fun check(organism: String, unit: Int, lang: AppLanguage) {
        val expected = InstrumentationRegistry.getArguments().getString("expectedScale")
            ?.toFloatOrNull() ?: error("Set expectedScale for 100%/200% test")
        assertTrue("Actual font scale differs from requested scale",
            abs(context.resources.configuration.fontScale - expected) < 0.06f)
        val chapter = "u$unit-$organism"
        val languageName = if (lang == AppLanguage.TAMIL) "tamil" else "english"
        rule.onNodeWithTag("language-$languageName").performClick()
        rule.onNodeWithTag("unit-$unit").performScrollTo().performClick()
        waitFor("native-unit-$unit")
        rule.onNodeWithTag("native-unit-$unit")
            .performScrollToNode(hasTestTag("chapter-$chapter"))
        rule.onNodeWithTag("chapter-$chapter").performClick()
        val readerTag = "book-reader-$chapter"
        waitFor(readerTag)
        rule.onNodeWithTag("chapter-language-toolbar").assertIsDisplayed()
        val toggle = "book-reference-gallery-toggle-$chapter"
        rule.onNodeWithTag(toggle).assertExists()
        rule.onNodeWithTag("r2-type-study-gallery-$organism").assertDoesNotExist()
        rule.onNodeWithTag(readerTag).performScrollToNode(hasTestTag("book-introduction"))
        rule.onNodeWithTag("book-introduction").assertExists()
        saveScreenshot(chapter, languageName, "textbook")

        rule.onNodeWithTag(toggle).performScrollTo().performClick()
        waitFor("r2-type-study-gallery-$organism")
        rule.onNodeWithTag("r2-plate-bitmap-$organism-external").performScrollTo()
            .assertExists()
        rule.onNodeWithTag("book-introduction").assertDoesNotExist()
        saveScreenshot(chapter, languageName, "gallery")

        rule.onNodeWithTag(toggle).performScrollTo().performClick()
        rule.onNodeWithTag("r2-type-study-gallery-$organism").assertDoesNotExist()
        rule.onNodeWithTag(readerTag).performScrollToNode(hasTestTag("book-practice-heading"))
        rule.onNodeWithTag("book-practice-heading").assertExists()
        saveScreenshot(chapter, languageName, "restored-practice")
    }

    @Test fun penaeusEnglish() = check("penaeus", 4, AppLanguage.ENGLISH)
    @Test fun penaeusTamil() = check("penaeus", 4, AppLanguage.TAMIL)
    @Test fun pilaEnglish() = check("pila", 5, AppLanguage.ENGLISH)
    @Test fun pilaTamil() = check("pila", 5, AppLanguage.TAMIL)
    @Test fun asteriasEnglish() = check("asterias", 5, AppLanguage.ENGLISH)
    @Test fun asteriasTamil() = check("asterias", 5, AppLanguage.TAMIL)
}
