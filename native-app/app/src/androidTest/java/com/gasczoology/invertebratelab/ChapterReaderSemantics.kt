package com.gasczoology.invertebratelab

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gasczoology.invertebratelab.data.A5Question
import com.gasczoology.invertebratelab.data.BookChapters
import com.gasczoology.invertebratelab.data.Chapter

/**
 * Which screen a chapter really opens in. Mirrors ChapterBody in InvertebrataApp.kt:
 * Unit II and Unit III have their own teaching readers, chapters with book content
 * open the book reader, and only the rest use the generic "native-chapter-<id>" list.
 * Only the generic list carries the "native-chapter-" container tag, so tests that
 * wait for it on every chapter time out on the other readers.
 */
internal enum class ReaderRoute(val containerPrefix: String, val backTag: String?) {
    NATIVE("native-chapter-", null),
    BOOK("book-reader-", "book-back"),
    UNIT_II("u2-reader-", "u2-back"),
    UNIT_III("u3-reader-", "u3-back"),
}

internal fun readerRoute(chapter: Chapter): ReaderRoute = when {
    chapter.unitNumber == 2 -> ReaderRoute.UNIT_II
    chapter.unitNumber == 3 -> ReaderRoute.UNIT_III
    BookChapters.forLesson(chapter.id) != null -> ReaderRoute.BOOK
    else -> ReaderRoute.NATIVE
}

internal fun readerContainerTag(chapter: Chapter): String =
    readerRoute(chapter).containerPrefix + chapter.id

/** Unit III renders question text under its own tag; every other reader uses a5-question-. */
internal fun readerQuestionTag(chapter: Chapter, question: A5Question): String =
    (if (readerRoute(chapter) == ReaderRoute.UNIT_III) "u3-question-" else "a5-question-") + question.id

internal fun leaveReader(rule: SemanticsNodeInteractionsProvider, chapter: Chapter) {
    val back = readerRoute(chapter).backTag
    if (back == null) rule.onNodeWithText("Back").performClick()
    else rule.onNodeWithTag(back).performClick()
}

/** Select the public continuous-reader mode before tests that traverse the entire corpus. */
internal fun showContinuousReader(rule: SemanticsNodeInteractionsProvider) {
    if (rule.onAllNodesWithTag("reading-mode-continuous").fetchSemanticsNodes().isNotEmpty()) {
        rule.onNodeWithTag("reading-mode-continuous").performClick()
    }
}
