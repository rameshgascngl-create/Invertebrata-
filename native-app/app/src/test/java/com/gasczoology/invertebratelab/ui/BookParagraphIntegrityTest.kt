package com.gasczoology.invertebratelab.ui

import com.gasczoology.invertebratelab.data.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Numbered display must not truncate or paraphrase the original academic prose.
 * Sentence segmentation may change whitespace at sentence boundaries only.
 */
class BookParagraphIntegrityTest {
    @Test fun englishTextRemainsCompleteAndOrdered() {
        val original = List(12) { index ->
            "Point ${index + 1} describes a distinct anatomical relationship and retains the original terminology."
        }.joinToString(" ")
        val segments = readingParagraphs(original, AppLanguage.ENGLISH)
        assertTrue(segments.size > 1)
        assertEquals(original, segments.joinToString(" "))
    }

    @Test fun tamilTextRemainsCompleteAndOrdered() {
        val original = List(12) { index ->
            "பகுதி ${index + 1} உடற்கூறு விளக்கத்தையும் அறிவியல் கலைச்சொற்களையும் முழுமையாகக் கொண்டுள்ளது."
        }.joinToString(" ")
        val segments = readingParagraphs(original, AppLanguage.TAMIL)
        assertTrue(segments.isNotEmpty())
        assertEquals(original, segments.joinToString(" "))
    }

    @Test fun shortPassageIsNotModified() {
        val original = "The ostia are distinct from the osculum."
        assertEquals(listOf(original), readingParagraphs(original, AppLanguage.ENGLISH))
    }
}
