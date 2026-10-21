package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Content gate only: device rendering and anatomical acceptance are separate. */
class ChapterStudyGuidesTest {
    @Test fun unitFiveGuidesHaveExactChapterMappingsAndBilingualContent() {
        assertEquals(setOf("u5-pila", "u5-asterias"), ChapterStudyGuides.byChapterId.keys)
        ChapterStudyGuides.byChapterId.forEach { (id, guide) ->
            assertTrue(NativeCurriculumManifest.a5ChapterIdsByUnit.getValue(5).contains(id))
            assertEquals(3, guide.objectives.size)
            assertEquals(3, guide.reviewPrompts.size)
            (guide.objectives + guide.reviewPrompts).forEach { entry ->
                assertTrue(entry.english.isNotBlank())
                assertTrue(entry.tamil.isNotBlank())
                assertTrue(entry.tamil.any { it in '\u0B80'..'\u0BFF' })
            }
        }
    }

    @Test fun anatomicalDistinctionsAreExplicitInRevisionGuides() {
        val pila = ChapterStudyGuides.byChapterId.getValue("u5-pila")
        val asterias = ChapterStudyGuides.byChapterId.getValue("u5-asterias")
        val pilaText = (pila.objectives + pila.reviewPrompts).joinToString(" ") { it.english }
        val starText = (asterias.objectives + asterias.reviewPrompts).joinToString(" ") { it.english }
        assertTrue(pilaText.contains("osphradium"))
        assertTrue(pilaText.contains("ctenidium"))
        assertTrue(starText.contains("madreporite"))
        assertTrue(starText.contains("radial canal"))
    }
}
