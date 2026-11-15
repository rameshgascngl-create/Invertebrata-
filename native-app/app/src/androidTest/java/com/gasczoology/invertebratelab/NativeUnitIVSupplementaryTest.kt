package com.gasczoology.invertebratelab

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
 * Exercises the actual student-facing, paginated Unit IV book chapters.
 * The old U5.6 generic list and its u56-* tags were replaced by the book
 * reader; retaining their obsolete assertions made these tests time out
 * even though the validated A5 questions were present.
 */
@RunWith(AndroidJUnit4::class)
class NativeUnitIVSupplementaryTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before fun resetState() {
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        await("native-home")
    }

    private fun await(tag: String) {
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun enter(id: String, tamil: Boolean): String {
        if (tamil) rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-4").performScrollTo().performClick()
        await("native-unit-4")
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("chapter-" + id))
        rule.onNodeWithTag("chapter-" + id).performClick()
        val reader = "book-reader-" + id
        await(reader)
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("book-objectives"))
        rule.onNodeWithTag("book-objectives").assertExists()
        return reader
    }

    private fun screenshot(tag: String) {
        rule.waitForIdle()
        val inst = InstrumentationRegistry.getInstrumentation()
        val scale = inst.targetContext.resources.configuration.fontScale
        val suffix = if (scale >= 1.95f) "200" else "100"
        val path = "/sdcard/Download/unit4-qa/unit4-" + suffix + "-" + tag + ".png"
        val ui = inst.uiAutomation
        android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("screencap -p " + path)).use { it.readBytes() }
        val png = android.os.ParcelFileDescriptor.AutoCloseInputStream(
            ui.executeShellCommand("cat " + path)).use { it.readBytes() }
        assertTrue("Missing real Unit IV book screenshot", png.size > 12000)
        assertNotNull(android.graphics.BitmapFactory.decodeByteArray(png, 0, png.size))
    }

    private fun assertOriginalFivePointAnswer(id: String, reader: String, tamil: Boolean) {
        val question = ValidatedA5AssetRepository(context).load()
            .single { it.number == 4 }.chapters
            .single { it.id == id }.a5Questions.first()
        openReadingPage(rule, reader, "practice-" + question.id)
        rule.onNodeWithTag(reader)
            .performScrollToNode(hasTestTag("a5-question-" + question.id))
        rule.onNodeWithTag("a5-question-" + question.id)
            .assertTextEquals(if (tamil) question.question.tamil else question.question.english)
        rule.onNodeWithTag("a5-toggle-" + question.id).performScrollTo().performClick()
        question.answerPoints.forEachIndexed { index, point ->
            rule.onNodeWithTag(reader).performScrollToNode(
                hasTestTag("a5-point-" + question.id + "-" + (index + 1))
            )
            rule.onNodeWithTag("a5-point-" + question.id + "-" + (index + 1))
                .assertTextEquals("${index + 1}. " + if (tamil) point.tamil else point.english)
        }
    }

    @Test fun englishPeripatusRealBiologyAndOriginalFiveMarkQuestions() {
        val id = "u4-peripatus"
        val reader = enter(id, tamil = false)
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("book-introduction"))
        rule.onNodeWithTag("book-introduction").assert(
            hasAnyDescendant(hasText("without treating it as a direct ancestor", substring = true))
        )
        screenshot("peripatus-english-book-introduction")
        openReadingPage(rule, reader, "summary")
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("book-summary"))
        rule.onNodeWithTag("book-summary").assertExists()
        assertOriginalFivePointAnswer(id, reader, tamil = false)
    }

    @Test fun tamilCrustaceanDevelopmentAvoidsFalseUniversalLarvalSequence() {
        val id = "u4-crustacean-larvae"
        val reader = enter(id, tamil = true)
        openReadingPage(rule, reader, "summary")
        rule.onNodeWithTag(reader).performScrollToNode(hasTestTag("book-summary"))
        rule.onNodeWithTag("book-summary").assert(
            hasAnyDescendant(hasText("பல நண்டுகள் நாப்ளியஸை முட்டைக்குள்", substring = true))
        )
        rule.onNodeWithTag("book-summary").assert(
            hasAnyDescendant(hasText("கிரஸ்டேஷியன் லார்வாக்கள் கம்பளிப்புழுக்கள் அல்ல", substring = true))
        )
        screenshot("crustacean-larvae-tamil-book-summary")
        assertOriginalFivePointAnswer(id, reader, tamil = true)
    }
}
