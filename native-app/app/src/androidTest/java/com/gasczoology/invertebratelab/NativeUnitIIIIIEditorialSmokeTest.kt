package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BookChapters
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

/**
 * Actual Unit II / III textbook-screen QA on offline Android.
 * Verifies chapter content and practice navigation, not just a successful build.
 * Each case is run again by CI at 100% and 200% Android system font scale.
 */
@RunWith(AndroidJUnit4::class)
class NativeUnitIIIIIEditorialSmokeTest {
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

    private fun screenshot(chapter: String, language: String, location: String) {
        rule.waitForIdle()
        val scale = if (context.resources.configuration.fontScale >= 1.5f) "200" else "100"
        val output = "/sdcard/Download/unit23-editorial-qa/${chapter}-${language}-${scale}-${location}.png"
        val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p $output")
        ).use { it.readBytes() }
        val data = ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat $output")
        ).use { it.readBytes() }
        assertTrue("Screenshot missing or empty: $output", data.size > 10_000)
        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        assertTrue("Screenshot unreadable: $output",
            bitmap != null && bitmap.height > bitmap.width)
        bitmap?.recycle()
    }

    private fun verify(chapterId: String, unit: Int, language: AppLanguage) {
        val expectedScale = InstrumentationRegistry.getArguments()
            .getString("expectedScale")?.toFloatOrNull()
            ?: error("CI must pass expectedScale for this QA test")
        assertTrue("Actual system font-scale mismatch",
            abs(context.resources.configuration.fontScale - expectedScale) < 0.06f)

        val langName = if (language == AppLanguage.TAMIL) "tamil" else "english"
        rule.onNodeWithTag("language-${langName}").performClick()
        rule.onNodeWithTag("unit-$unit").performScrollTo().performClick()
        val unitTag = "native-unit-$unit"
        waitFor(unitTag)
        rule.onNodeWithTag(unitTag)
            .performScrollToNode(hasTestTag("chapter-$chapterId"))
        rule.onNodeWithTag("chapter-$chapterId").performClick()
        val readerTag = "u$unit-reader-$chapterId"
        waitFor(readerTag)
        rule.onNodeWithTag("chapter-language-toolbar").assertIsDisplayed()
        rule.onNodeWithTag("chapter-language-english").assertExists()
        rule.onNodeWithTag("chapter-language-tamil").assertExists()
        rule.onNodeWithTag("u$unit-lesson-title")
            .assertTextEquals(BookChapters.forLesson(chapterId)!!.title.value(language))
        rule.onNodeWithTag(if (unit == 2) "u2-draft-disclosure" else "u3-draft-warning")
            .assertDoesNotExist()
        screenshot(chapterId, langName, "heading")

        rule.onNodeWithTag(readerTag)
            .performScrollToNode(hasTestTag("book-introduction"))
        rule.onNodeWithTag("book-introduction").assertIsDisplayed()
        screenshot(chapterId, langName, "introduction")

        val practiceTag = if (unit == 2) "u2-practice-heading" else "u3-practice-heading"
        rule.onNodeWithTag(readerTag)
            .performScrollToNode(hasTestTag(practiceTag))
        rule.onNodeWithTag(practiceTag).assertExists()
        screenshot(chapterId, langName, "practice")
    }

    @Test fun syconEnglish() = verify("u2-sycon", 2, AppLanguage.ENGLISH)
    @Test fun syconTamil() = verify("u2-sycon", 2, AppLanguage.TAMIL)
    @Test fun obeliaEnglish() = verify("u2-obelia", 2, AppLanguage.ENGLISH)
    @Test fun obeliaTamil() = verify("u2-obelia", 2, AppLanguage.TAMIL)
    @Test fun fasciolaEnglish() = verify("u3-fasciola", 3, AppLanguage.ENGLISH)
    @Test fun fasciolaTamil() = verify("u3-fasciola", 3, AppLanguage.TAMIL)
    @Test fun ascarisEnglish() = verify("u3-ascaris", 3, AppLanguage.ENGLISH)
    @Test fun ascarisTamil() = verify("u3-ascaris", 3, AppLanguage.TAMIL)
}
