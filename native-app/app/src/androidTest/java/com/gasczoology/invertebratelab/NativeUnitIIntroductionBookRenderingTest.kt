package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BookLayout
import com.gasczoology.invertebratelab.data.BookRow
import com.gasczoology.invertebratelab.data.IntroInvertebrataBookChapter
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

/**
 * Exact-commit API34 evidence for the actual Unit I introductory book reader.
 * The driver must set Android system font_scale to 1.0 and 2.0 in separate runs.
 * Screenshots are captured from the emulator display, never fabricated in Compose.
 */
@RunWith(AndroidJUnit4::class)
class NativeUnitIIntroductionBookRenderingTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun resetLearningPosition() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    private fun waitFor(tag: String) {
        rule.waitUntil(timeoutMillis = 30_000L) {
            rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun capture(name: String) {
        val path = "/sdcard/Download/u1-intro-evidence/$name.png"
        val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
        ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("screencap -p $path")
        ).use { it.readBytes() }
        val bytes = ParcelFileDescriptor.AutoCloseInputStream(
            automation.executeShellCommand("cat $path")
        ).use { it.readBytes() }
        assertTrue("Missing actual Android screenshot: $path", bytes.size > 2000)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertNotNull("Invalid display PNG: $path", bitmap)
        bitmap!!.recycle()
    }

    private fun assertHeadingWithinViewport(tag: String, expected: String) {
        val heading = rule.onNodeWithTag(tag)
        heading.assertTextEquals(expected)
        val rect = heading.fetchSemanticsNode().boundsInRoot
        val viewport = rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Heading outside viewport: $tag bounds=$rect root=$viewport",
            rect.width > 0f && rect.height > 0f &&
                rect.left >= viewport.left - 1f && rect.right <= viewport.right + 1f &&
                rect.top >= viewport.top - 1f && rect.bottom <= viewport.bottom + 1f)
    }

    private fun verify(language: AppLanguage) {
        val scale = InstrumentationRegistry.getInstrumentation().targetContext
            .resources.configuration.fontScale
        val expected = InstrumentationRegistry.getArguments()
            .getString("expectedScale")?.toFloatOrNull()
            ?: error("CI must supply expectedScale from actual Android Settings")
        assertTrue("System font scale mismatch: actual=$scale expected=$expected",
            abs(scale - expected) < 0.06f)
        assertTrue("Unexpected scale $scale", abs(scale - 1f) < 0.06f ||
            abs(scale - 2f) < 0.06f)
        val scaleName = if (scale > 1.5f) "200" else "100"
        val langName = if (language == AppLanguage.TAMIL) "tamil" else "english"

        waitFor("native-home")
        rule.onNodeWithTag(if (language == AppLanguage.TAMIL) "language-tamil"
            else "language-english").performClick()
        rule.onNodeWithTag("unit-1").performScrollTo().performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(1)
        rule.onNodeWithTag("chapter-u1-intro").performClick()
        waitFor("book-reader-u1-intro")

        val book = IntroInvertebrataBookChapter.chapter
        val rows = BookLayout.rows(book)
        val sectionRows = rows.withIndex().filter { it.value is BookRow.SectionHeading }
        assertEquals(10, sectionRows.size)

        val reader = rule.onNodeWithTag("book-reader-u1-intro")
        reader.performScrollToIndex(2)
        rule.onNodeWithTag("book-introduction").assertExists()
        capture("u1-intro-$langName-$scaleName-introduction")

        reader.performScrollToIndex(3)
        rule.onNodeWithTag("book-contents").assertExists()
        reader.performScrollToIndex(4 + sectionRows.first().index)
        val first = sectionRows.first().value as BookRow.SectionHeading
        val firstTitle = if (language == AppLanguage.TAMIL)
            first.heading.tamil else first.heading.english
        assertHeadingWithinViewport("book-section-1.1", "1.1  $firstTitle")
        capture("u1-intro-$langName-$scaleName-first-section")

        reader.performScrollToIndex(4 + sectionRows.last().index)
        val last = sectionRows.last().value as BookRow.SectionHeading
        val lastTitle = if (language == AppLanguage.TAMIL)
            last.heading.tamil else last.heading.english
        assertHeadingWithinViewport("book-section-1.10", "1.10  $lastTitle")
        capture("u1-intro-$langName-$scaleName-last-section")

        reader.performScrollToIndex(4 + rows.size)
        rule.onNodeWithTag("book-summary").assertExists()
        reader.performScrollToIndex(0)
        rule.onNodeWithTag("book-back").performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(0)
        rule.onNodeWithText(if (language == AppLanguage.TAMIL) "பின்செல்" else "Back")
            .performClick()
        waitFor("native-home")
    }

    @Test fun englishIntroductionHasSingleNumberedHeadingsAndNavigates() {
        verify(AppLanguage.ENGLISH)
    }

    @Test fun tamilIntroductionHasSingleNumberedHeadingsAndNavigates() {
        verify(AppLanguage.TAMIL)
    }
}
