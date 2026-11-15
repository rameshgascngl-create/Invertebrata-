package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BookLayout
import com.gasczoology.invertebratelab.data.BookRow
import com.gasczoology.invertebratelab.data.ParameciumBookChapter
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Exercises the actual paginated textbook figure renderer, rather than
 * checking only whether the approved PNG exists inside the APK.
 */
@RunWith(AndroidJUnit4::class)
class ApprovedParameciumReaderTest {
    @get:Rule val compose = createComposeRule()

    @Test fun approvedPlateRendersInlineInEnglish() = checkFigure(AppLanguage.ENGLISH)

    @Test fun approvedPlateRendersInlineInTamil() = checkFigure(AppLanguage.TAMIL)

    private fun checkFigure(language: AppLanguage) {
        val chapter = ParameciumBookChapter.chapter
        val figureIndex = BookLayout.rows(chapter).indexOfFirst {
            it is BookRow.Block &&
                it.block is com.gasczoology.invertebratelab.data.BookFigure &&
                it.block.slot == "lib/paramecium-reproduction-approved"
        }
        assertTrue("Approved figure must be present in reading order", figureIndex >= 0)
        compose.setContent {
            InvertebrataTheme {
                LazyColumn(modifier = androidx.compose.ui.Modifier.testTag("paramecium-reader-list")) {
                    bookChapterItems(chapter = chapter, language = language, onListen = {})
                }
            }
        }
        val figureTag = "lib-paramecium-reproduction-approved"
        compose.onNodeWithTag("paramecium-reader-list")
            .performScrollToNode(hasTestTag("book-figure-$figureTag"))
        compose.onNodeWithTag("r2-plate-bitmap-$figureTag").assertExists()
        compose.onNodeWithTag("book-figure-caption-$figureTag").assertExists()
        compose.onNodeWithTag("r2-plate-missing-$figureTag").assertDoesNotExist()
    }
}
