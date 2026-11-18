package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Prevent old inflated prose and circulatory-system mistranslation returning. */
class AsteriasIntroductionReadabilityTest {
    @Test fun conciseBilingualAnatomyIntroductionWithoutGenericTextbookFiller() {
        val chapter = AsteriasBookChapter.chapter
        assertEquals(2, chapter.introduction.size)
        assertTrue(chapter.introduction.all { it.english.length in 150..340 })
        assertTrue(chapter.introduction.all { it.tamil.length in 150..400 })
        val en = chapter.introduction.joinToString(" ") { it.english }
        val ta = chapter.introduction.joinToString(" ") { it.tamil }
        assertTrue(en.contains("Asteroidea") && en.contains("water-vascular"))
        assertTrue(en.contains("oral surface") && en.contains("aboral surface"))
        assertTrue(ta.contains("வாய்ப்புற") && ta.contains("எதிர்வாய்ப்புற"))
        assertFalse(en.contains("The student must"))
        assertFalse(en.contains("textbook account"))
    }

    @Test fun waterVascularSystemIsNeverDescribedAsBloodCirculation() {
        val chapter = AsteriasBookChapter.chapter
        val objectivesTamil = chapter.objectives.joinToString(" ") { it.tamil }
        assertTrue(objectivesTamil.contains("நீர்க்குழாய் மண்டல"))
        assertFalse(objectivesTamil.contains("நீர் குருதியோட்ட"))
    }
}
