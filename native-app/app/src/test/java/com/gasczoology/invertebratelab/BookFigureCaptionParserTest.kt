package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.ui.parseFigureCaption
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BookFigureCaptionParserTest {
    @Test fun tamilFigureLegendSplitsIntoIndependentlyWrappingEntries() {
        val result = parseFigureCaption(
            "ஒபீலியா குடியிருப்பு (படத்திட்டம்): (1) ஹைட்ராந்த், (2) கோனாஞ்சியம், (3) கோயினோசார்க்"
        )
        assertEquals("ஒபீலியா குடியிருப்பு (படத்திட்டம்)", result.title)
        assertEquals(listOf("1", "2", "3"), result.numberedKey.map { it.first })
        assertEquals(listOf("ஹைட்ராந்த்", "கோனாஞ்சியம்", "கோயினோசார்க்"),
            result.numberedKey.map { it.second })
    }

    @Test fun captionWithoutNumberedAnatomicalKeyRemainsVerbatim() {
        val plain = "Paramecium, general anatomy: nuclei, cilia and vacuoles."
        val result = parseFigureCaption(plain)
        assertEquals(plain, result.title)
        assertTrue(result.numberedKey.isEmpty())
    }

    @Test fun englishLegendDoesNotLoseFinalItemOrPunctuationInsideDescriptions() {
        val result = parseFigureCaption(
            "Coral-polyp cross-section: (1) tentacles, surrounding the mouth, (2) oral opening; (3) skeleton."
        )
        assertEquals(3, result.numberedKey.size)
        assertEquals("tentacles, surrounding the mouth", result.numberedKey[0].second)
        assertEquals("skeleton.", result.numberedKey.last().second)
    }
}
