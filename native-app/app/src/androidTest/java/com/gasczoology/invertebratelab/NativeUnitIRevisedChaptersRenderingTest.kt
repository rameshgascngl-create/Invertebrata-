package com.gasczoology.invertebratelab

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BookChapter
import com.gasczoology.invertebratelab.data.BookLayout
import com.gasczoology.invertebratelab.data.BookRow
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.ProtozoaBookChapter
import com.gasczoology.invertebratelab.data.ParameciumBookChapter
import com.gasczoology.invertebratelab.data.ProtoParasitesBookChapter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

/** Run on a real API34 emulator with Android system font_scale set by the CI driver. */
@RunWith(AndroidJUnit4::class)
class NativeUnitIRevisedChaptersRenderingTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
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

    private fun checkChapter(chapter: BookChapter, language: AppLanguage) {
        val actual = InstrumentationRegistry.getInstrumentation().targetContext
            .resources.configuration.fontScale
        val expected = InstrumentationRegistry.getArguments().getString("expectedScale")
            ?.toFloatOrNull() ?: error("Missing expectedScale")
        assertTrue("Actual Android font scale $actual != $expected", abs(actual - expected) < 0.06f)
        waitFor("native-home")
        rule.onNodeWithTag(if (language == AppLanguage.TAMIL) "language-tamil" else "language-english")
            .performClick()
        rule.onNodeWithTag("unit-1").performClick()
        waitFor("native-unit-1")
        val chapterNumber = BookLayout.chapterNumber(chapter.lessonId)
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(chapterNumber)
        rule.onNodeWithTag("chapter-" + chapter.lessonId).performClick()
        val readerTag = "book-reader-" + chapter.lessonId
        waitFor(readerTag)
        val rows = BookLayout.rows(chapter)
        val headings = rows.withIndex().filter { it.value is BookRow.SectionHeading }
        assertTrue("No sections in " + chapter.lessonId, headings.isNotEmpty())
        val reader = rule.onNodeWithTag(readerTag)
        reader.performScrollToIndex(2)
        rule.onNodeWithTag("book-introduction").assertExists()
        for (entry in listOf(headings.first(), headings.last())) {
            reader.performScrollToIndex(4 + entry.index)
            val heading = entry.value as BookRow.SectionHeading
            val tag = "book-section-" + heading.number
            rule.onNodeWithTag(tag).assertTextEquals(
                heading.number + "  " +
                    if (language == AppLanguage.TAMIL) heading.heading.tamil else heading.heading.english
            )
            val rect = rule.onNodeWithTag(tag).fetchSemanticsNode().boundsInRoot
            val root = rule.onRoot().fetchSemanticsNode().boundsInRoot
            assertTrue("Clipped heading " + tag + " at font scale " + actual,
                rect.width > 0f && rect.height > 0f &&
                    rect.left >= root.left - 1f && rect.right <= root.right + 1f &&
                    rect.top >= root.top - 1f && rect.bottom <= root.bottom + 1f)
        }
        reader.performScrollToIndex(4 + rows.size)
        rule.onNodeWithTag("book-summary").assertExists()
        reader.performScrollToIndex(0)
        rule.onNodeWithTag("book-back").performClick()
        waitFor("native-unit-1")
    }

    @Test fun englishProtozoa() = checkChapter(ProtozoaBookChapter.chapter, AppLanguage.ENGLISH)
    @Test fun tamilProtozoa() = checkChapter(ProtozoaBookChapter.chapter, AppLanguage.TAMIL)
    @Test fun englishParamecium() = checkChapter(ParameciumBookChapter.chapter, AppLanguage.ENGLISH)
    @Test fun tamilParamecium() = checkChapter(ParameciumBookChapter.chapter, AppLanguage.TAMIL)
    @Test fun englishProtoParasites() = checkChapter(ProtoParasitesBookChapter.chapter, AppLanguage.ENGLISH)
    @Test fun tamilProtoParasites() = checkChapter(ProtoParasitesBookChapter.chapter, AppLanguage.TAMIL)
}
