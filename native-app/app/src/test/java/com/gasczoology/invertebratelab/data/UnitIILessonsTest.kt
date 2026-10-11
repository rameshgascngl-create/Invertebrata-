package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class UnitIILessonsTest {
    @Test fun nineUnitTwoChaptersAllHaveSubstantialBilingualTextbookDrafts() {
        UnitIILessons.validateDrafts()
        assertEquals(NativeCurriculumManifest.a5ChapterIdsByUnit.getValue(2),UnitIILessons.chapterIds)
        assertEquals(9,UnitIILessons.chapters.size)
        for ((id, lesson) in UnitIILessons.chapters) {
            assertEquals(id, lesson.lessonId)
            assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,lesson.status)
            assertNull(lesson.review)
            assertTrue(lesson.sections.all { it.paragraphs.size >= 2 })
            assertTrue(lesson.sections.all { it.scientificSources.all(String::isNotBlank) })
        }
        assertNull(UnitIILessons.forChapter("u3-fasciola"))
    }
    @Test fun syconProsopylePrecedesChoanocyteRadialCanalAndApopyle() {
        assertEquals(listOf("ostia","incurrent-canal","prosopyle","radial-canal",
            "apopyle","spongocoel","osculum"),UnitIILessons.syconWaterRoute)
        assertTrue(UnitIILessons.syconStages.all {
            it.name.english.isNotBlank() && it.name.tamil.isNotBlank() &&
            it.explanation.english.isNotBlank() && it.explanation.tamil.isNotBlank()
        })
        assertEquals(7,UnitIILessons.syconStages.size)
        assertEquals(6,UnitIILessons.obeliaStages.size)
    }
    @Test fun noAuthoringIsMistakenForScientificAcceptance() {
        assertTrue(NativeCurriculumManifest.lessonSlots.none {
            it.status == AcademicWorkStatus.ACCEPTED
        })
        assertTrue(NativeCurriculumManifest.typeStudies.none {
            it.status == AcademicWorkStatus.ACCEPTED
        })
    }
}
