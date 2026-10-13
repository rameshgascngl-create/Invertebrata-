package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class UnitIIILessonsTest {
    @Test fun sevenNativeTeachingChaptersPreserveAuthoritativeUnitThreeOrder() {
        UnitIIILessons.validateDrafts()
        assertEquals(NativeCurriculumManifest.a5ChapterIdsByUnit.getValue(3),
            UnitIIILessons.chapters.keys.toList())
        assertEquals(7, UnitIIILessons.chapters.size)
        assertTrue(UnitIIILessons.chapters.values.all {
            it.sections.size >= 3 && it.status == AcademicWorkStatus.DRAFT_UNVERIFIED
        })
        assertTrue(UnitIIILessons.chapters.values.flatMap { it.sections }.all { section ->
            section.paragraphs.all { it.english.length >= 100 && it.tamil.length >= 55 }
        })
        assertTrue(UnitIIILessons.chapters.values.all { it.review == null })
    }

    @Test fun fasciolaAndAscarisLifeCyclesAreDifferentAndScientificallyOrdered() {
        assertEquals(listOf("egg","miracidium","snail","cercaria","metacercaria","adult"),
            UnitIIILessons.fasciolaLifeCycle.map { it.id })
        assertEquals(listOf("unembryonated","embryonated","ingestion","lung","swallow","adult"),
            UnitIIILessons.ascarisLifeCycle.map { it.id })
        assertFalse(UnitIIILessons.ascarisLifeCycle.map { it.id }.contains("snail"))
        for (stages in listOf(UnitIIILessons.fasciolaLifeCycle,UnitIIILessons.ascarisLifeCycle)) {
            assertTrue(stages.all { it.name.english.isNotBlank() &&
                it.name.tamil.isNotBlank() && it.explanation.tamil.isNotBlank() })
        }
    }

    @Test fun teachingDraftDoesNotBecomeAcademicAcceptance() {
        assertTrue(UnitIIILessons.chapters.values.none {
            it.status == AcademicWorkStatus.ACCEPTED
        })
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,
            NativeCurriculumManifest.typeStudies.single {
                it.organismId=="fasciola"
            }.status)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,
            NativeCurriculumManifest.typeStudies.single {
                it.organismId=="ascaris"
            }.status)
    }
}
