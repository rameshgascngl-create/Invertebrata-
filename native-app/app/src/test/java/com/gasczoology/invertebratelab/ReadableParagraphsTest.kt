package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.ui.readingParagraphs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReadableParagraphsTest {
    @Test fun shortDefinitionLikeParagraphRemainsOneBlock() {
        val text = "Osmoregulation maintains the water balance of the cell."
        assertEquals(listOf(text), readingParagraphs(text, AppLanguage.ENGLISH))
    }

    @Test fun longEnglishTextIsSplitAtSentencesButNoWordsAreLost() {
        val sentences = (1..12).map {
            "Each cilium contributes to locomotion and feeding under controlled conditions."
        }
        val original = sentences.joinToString(" ")
        val blocks = readingParagraphs(original, AppLanguage.ENGLISH)
        assertTrue(blocks.size > 1)
        assertEquals(original, blocks.joinToString(" "))
        assertTrue(blocks.all { it.endsWith(".") })
    }

    @Test fun longTamilScientificTextRemainsCompleteInMultipleReadingBlocks() {
        val sentence = "பாரமீசியம் குறுஇழைகளால் நீரில் நீந்துகிறது. "
        val original = sentence.repeat(12).trim()
        val blocks = readingParagraphs(original, AppLanguage.TAMIL)
        assertTrue(blocks.size > 1)
        assertEquals(original, blocks.joinToString(" "))
    }
}
