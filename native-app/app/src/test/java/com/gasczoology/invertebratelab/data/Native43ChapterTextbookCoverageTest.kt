package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Engineering manuscript-coverage gate, never an assertion of scientific,
 * Tamil-editorial or illustration approval.
 *
 * Match the 43 existing A5 chapter identities exactly. Earthworm is a separate
 * curriculum teaching slot and must not be inserted into the accepted A5 JSON.
 */
class Native43ChapterTextbookCoverageTest {
    private val coreSources = listOf(
        FiveUnitRestorationLessons.byId.keys,
        UnitIILessons.chapters.keys,
        UnitIIILessons.chapters.keys,
        UnitIVSupplementaryLessons.byId.keys,
        setOf("u1-paramecium", "u1-proto-parasites", "u4-nereis", "u4-penaeus")
    )

    @Test fun everyAcceptedChapterHasExactlyOneNativeTeachingSource() {
        val identities = coreSources.flatMap { it.toList() }
        val expected = NativeCurriculumManifest.a5ChapterIdsByUnit.values.flatten()
        assertEquals(43, expected.size)
        assertEquals(43, identities.size)
        assertEquals(43, identities.toSet().size)
        assertEquals(expected.toSet(), identities.toSet())
        assertFalse(identities.contains(NativeCurriculumManifest.EARTHWORM_LESSON_ID))
        assertEquals(44, NativeCurriculumManifest.lessonSlots.size)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,
            NativeCurriculumManifest.lessonSlots.single {
                it.id == NativeCurriculumManifest.EARTHWORM_LESSON_ID
            }.status)
    }

    private fun checkParagraphs(sections: List<NereisTextbookSection>) {
        assertTrue(sections.size >= 3)
        assertEquals(sections.size, sections.map { it.id }.distinct().size)
        for (section in sections) {
            assertTrue(section.title.english.isNotBlank() && section.title.tamil.isNotBlank())
            assertTrue(section.paragraphs.size >= 2)
            for (p in section.paragraphs) {
                assertTrue("Short English paragraph in " + section.id, p.english.length >= 75)
                assertTrue("Short Tamil paragraph in " + section.id, p.tamil.length >= 50)
            }
        }
    }

    @Test fun unitOneAndFiveHaveSubstantiveBilingualTextRatherThanTwoSectionPreviews() {
        assertEquals(17, FiveUnitRestorationLessons.chapters.size)
        FiveUnitRestorationLessons.chapters.forEach { chapter ->
            checkParagraphs(chapter.sections)
        }
    }

    private fun checkBilingualDraft(lesson: NativeBilingualLesson) {
        assertTrue(lesson.sections.size >= 3)
        assertTrue(lesson.title.english.isNotBlank() && lesson.title.tamil.isNotBlank())
        assertEquals(lesson.sections.size, lesson.sections.map { it.id }.distinct().size)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, lesson.status)
        assertEquals(null, lesson.review)
        for (section in lesson.sections) {
            assertTrue(section.heading.english.isNotBlank() && section.heading.tamil.isNotBlank())
            assertTrue(section.paragraphs.size >= 2)
            for (p in section.paragraphs) {
                assertTrue(p.english.length >= 35 && p.tamil.length >= 20)
            }
            assertTrue(section.scientificSources.any { it.startsWith("https://") })
        }
    }

    @Test fun allUnitTwoAndThreeChaptersHaveThreeOrMoreSourcedSections() {
        assertEquals(9, UnitIILessons.chapters.size)
        assertEquals(7, UnitIIILessons.chapters.size)
        UnitIILessons.validateDrafts()
        UnitIIILessons.validateDrafts()
        UnitIILessons.chapters.values.forEach(::checkBilingualDraft)
        UnitIIILessons.chapters.values.forEach(::checkBilingualDraft)
    }

    @Test fun allUnitFourChaptersHaveSubstantialBilingualTeachingRoutes() {
        assertEquals(6, UnitIVSupplementaryLessons.chapters.size)
        UnitIVSupplementaryLessons.chapters.forEach { chapter ->
            assertTrue(chapter.sections.size >= 4)
            checkParagraphs(chapter.sections)
            assertTrue(chapter.reading.any { it.startsWith("https://") })
        }
        assertTrue(UnitIVNereisLessons.sections.size >= 14)
        assertTrue(UnitIVPenaeusTeaching.sections.size >= 12)
        assertTrue(UnitIVEarthwormLessons.lesson.sections.size >= 3)
        UnitIVNereisLessons.sections.forEach { section ->
            assertTrue(section.paragraphs.all {
                it.english.length > 75 && it.tamil.length > 50
            })
        }
    }

    @Test fun unitOneSpecialTypeStudiesHaveNativeTextAndLifecycleExplanations() {
        val paramecium = NativeLessonDrafts.paramecium
        assertEquals("u1-paramecium", paramecium.lessonId)
        assertTrue(paramecium.sections.size >= 10)
        BilingualLessonContract.validate(paramecium)
        val source = File(
            "src/main/java/com/gasczoology/invertebratelab/ui/UnitIFlagellateTypeStudies.kt"
        ).readText(Charsets.UTF_8)
        assertTrue(source.contains("UnitIFlagellateLessonSection("))
        for (species in listOf("trypanosoma", "leishmania")) {
            assertTrue(source.contains("id = \"$species\""))
        }
        assertTrue(UnitIFlagellateLifeCycles.trypanosoma.phases.size >= 4)
        assertTrue(UnitIFlagellateLifeCycles.leishmania.phases.size >= 4)
    }
}
