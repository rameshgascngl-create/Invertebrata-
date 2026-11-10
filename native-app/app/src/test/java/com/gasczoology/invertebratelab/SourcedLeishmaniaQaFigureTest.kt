package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.SourcedLeishmaniaQaFigure
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class SourcedLeishmaniaQaFigureTest {
    @Test fun twoSourceComparisonIsApprovedForBookInclusion() {
        assertEquals("lib/leishmania-amastigote", SourcedLeishmaniaQaFigure.slot)
        assertTrue(SourcedLeishmaniaQaFigure.assetStem.startsWith("qa_source_plates/"))
        assertTrue(SourcedLeishmaniaQaFigure.caption.english.contains("amastigote"))
        assertTrue(SourcedLeishmaniaQaFigure.caption.english.contains("promastigote"))
        assertTrue(SourcedLeishmaniaQaFigure.caption.tamil.contains("புரோமாஸ்டிகோட்"))
        assertTrue(SourcedLeishmaniaQaFigure.credit.en.contains("Servier Medical Art"))
        assertTrue(SourcedLeishmaniaQaFigure.credit.en.contains("CC BY 2.0"))
        assertTrue(SourcedLeishmaniaQaFigure.credit.en.contains("Servier Medical Art"))
        assertTrue(SourcedLeishmaniaQaFigure.credit.en.contains("included in the native Android textbook", ignoreCase = true))
    }
}
