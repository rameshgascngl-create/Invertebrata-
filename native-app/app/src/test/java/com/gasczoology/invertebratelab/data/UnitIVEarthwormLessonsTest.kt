package com.gasczoology.invertebratelab.data

import java.io.File
import org.junit.Assert.*
import org.junit.Test

class UnitIVEarthwormLessonsTest {
    private fun corpus(): List<AcademicUnit> =
        ValidatedA5Parser.parse((1..5).associateWith {
            File("src/main/assets/a5/U" + it + "_A5_VALIDATED.json")
                .readText(Charsets.UTF_8)
        })

    @Test fun substantialSpeciesSpecificBilingualTextbookWithoutA5Substitution() {
        UnitIVEarthwormLessons.validateDraft()
        val lesson=UnitIVEarthwormLessons.lesson
        assertEquals(NativeCurriculumManifest.EARTHWORM_LESSON_ID,lesson.lessonId)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,lesson.status)
        assertNull(lesson.review)
        assertEquals(15,lesson.sections.size)
        assertEquals(15,lesson.sections.map { it.id }.distinct().size)
        assertTrue(lesson.sections.all { section ->
            section.heading.english.isNotBlank() && section.heading.tamil.isNotBlank() &&
                section.paragraphs.size>=2 && section.paragraphs.all {
                    it.english.length>=115 && it.tamil.length>=80
                } && section.scientificSources.all { it.startsWith("https://") }
        })
        val canonical=corpus()
        NativeCurriculumManifest.validateAgainst(canonical)
        assertEquals(43,canonical.flatMap { it.chapters }.size)
        assertEquals(86,canonical.flatMap { it.chapters }.flatMap { it.a5Questions }.size)
        assertFalse(canonical.flatMap { it.chapters }.any { it.id==lesson.lessonId })
    }

    @Test fun sixEarthwormPlatesRemainMissingUntilScientificallyReviewed() {
        val required=NativeCurriculumManifest.typeStudies.single { it.organismId=="earthworm" }
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,required.status)
        assertNull(required.acceptedA5ChapterId)
        assertEquals(listOf("external-setae","digestive","circulatory","nervous",
            "excretory","reproductive"),UnitIVEarthwormLessons.requiredPlates.map { it.first })
        assertEquals(required.requiredPlateIds,UnitIVEarthwormLessons.requiredPlates.map { it.first })
        val present=emptySet<String>()
        for (id in required.requiredPlateIds) {
            val stem=RasterArtworkPolicy.requiredFuturePlateStem(required,id)
            assertNull(RasterArtworkPolicy.selectAsset(stem,present))
        }
    }

    @Test fun essentialEarthwormAnatomyAndHistoricalTaxonomyAreExplicit() {
        val sections=UnitIVEarthwormLessons.lesson.sections.associateBy { it.id }
        for (id in listOf("identity","external","pores","setae-locomotion",
            "digestive","circulation","nervous","excretory","reproductive","development")) {
            assertTrue("Required substantive heading missing: "+id,id in sections)
        }
        val external=sections.getValue("external").paragraphs.joinToString(" ") { it.english }
        assertTrue(external.contains("XIV–XVI"))
        assertTrue(external.contains("setae retained"))
        val pores=sections.getValue("pores").paragraphs.joinToString(" ") { it.english }
        assertTrue(pores.contains("XVIII") && pores.contains("5/6") && pores.contains("12/13"))
        val excretory=sections.getValue("excretory").paragraphs.joinToString(" ") { it.english }
        assertTrue(excretory.contains("septal") && excretory.contains("integumentary") &&
            excretory.contains("pharyngeal"))
        val digestive=sections.getValue("digestive").paragraphs.joinToString(" ") { it.english }
        assertTrue(digestive.contains("gizzard") && digestive.contains("typhlosole") &&
            digestive.contains("anus"))
    }

    @Test fun savedEarthwormRouteIsValidatedAgainstTheRealCorpus() {
        val units=corpus()
        val state=NativeLearningState(destination=StudyDestination.EARTHWORM_TEXTBOOK,
            unitNumber=4,chapterId=NativeCurriculumManifest.EARTHWORM_LESSON_ID,
            textbookSectionId="digestive")
        val restored=state.validatedAgainst(units)
        assertEquals(StudyDestination.EARTHWORM_TEXTBOOK,restored.destination)
        assertEquals("digestive",restored.textbookSectionId)
        assertEquals(4,restored.unitNumber)
        assertEquals(StudyDestination.HOME,
            state.copy(chapterId="u4-penaeus").validatedAgainst(units).destination)
        assertEquals("identity",
            state.copy(textbookSectionId="unknown-section").validatedAgainst(units)
                .textbookSectionId)
        assertEquals(StudyDestination.HOME,
            state.copy(questionId="fake-question").validatedAgainst(units).destination)
    }
}
