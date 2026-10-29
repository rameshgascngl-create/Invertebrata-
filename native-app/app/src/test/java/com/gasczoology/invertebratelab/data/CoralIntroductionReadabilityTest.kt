package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Safeguard the textbook opening against lengthy app-status prose and biological overclaims. */
class CoralIntroductionReadabilityTest {
    @Test fun coralIntroductionIsShortBilingualAndScientificallySpecific() {
        val opening = CoralsBookChapter.chapter.introduction
        assertEquals("Two focused introductory paragraphs", 2, opening.size)
        assertTrue(opening.all { it.english.length in 120..330 })
        assertTrue(opening.all { it.tamil.length in 95..400 })
        val en = opening.joinToString(" ") { it.english }
        val ta = opening.joinToString(" ") { it.tamil }
        assertTrue(en.contains("Anthozoa") && en.contains("polyp") && en.contains("ecosystem"))
        assertTrue(ta.contains("ஆந்தோசோவா") && ta.contains("சூழல்மண்டலம்"))
        assertFalse("Unsupported superlatives should not appear", en.contains("largest structure"))
        assertFalse("Generic chapter introductions do not help students", en.contains("This chapter treats"))
        assertFalse("No editorial notices in teaching prose", en.contains("review pending", true))
    }

    @Test fun conciseOpeningPreservesScienceAndReviewQuestionStructure() {
        val chapter = CoralsBookChapter.chapter
        assertTrue(chapter.objectives.size >= 3)
        assertTrue(chapter.sections.size >= 5)
        assertTrue(chapter.quickChecks.size >= 3)
        assertTrue(chapter.definitions.size >= 8)
        assertTrue(chapter.reviewQuestionIds.isNotEmpty())
    }
}
