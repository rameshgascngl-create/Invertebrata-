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
    @Test fun coralLessonIncludesReefTypesSettlementAndDistinctReproductionModes() {
        val lesson = UnitIILessons.forChapter("u2-corals")!!
        val byId = lesson.sections.associateBy { it.id }
        assertEquals(5, lesson.sections.size)
        val reefs = byId.getValue("reef-types")
        val reproduction = byId.getValue("coral-reproduction")
        assertTrue(reefs.scientificSources.contains(
            "https://oceanservice.noaa.gov/education/tutorial_corals/coral04_reefs.html"))
        assertTrue(reproduction.scientificSources.contains(
            "https://oceanservice.noaa.gov/education/tutorial_corals/coral06_reproduction.html"))
        for (section in listOf(reefs, reproduction)) {
            assertEquals(2, section.paragraphs.size)
            assertTrue(section.heading.english.isNotBlank() && section.heading.tamil.isNotBlank())
            assertTrue(section.paragraphs.all {
                it.english.length >= 200 && it.tamil.length >= 130
            })
        }
        val reefText = reefs.paragraphs.joinToString(" ") { it.english }
        assertTrue(listOf("fringing", "barrier", "atoll", "lagoon", "calcium-carbonate")
            .all { reefText.contains(it, ignoreCase = true) })
        val reproductionText = reproduction.paragraphs.joinToString(" ") { it.english }
        assertTrue(listOf("broadcast-spawning", "brood", "planula", "metamorphose",
            "budding", "fragmentation").all {
                reproductionText.contains(it, ignoreCase = true)
            })
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, lesson.status)
        assertNull(lesson.review)
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
