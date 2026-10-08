package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** N2.2 scientific editorial status and offline lesson contract regression. */
class NativeLessonDraftsTest {
    @Test
    fun parameciumIsFirstRealBilingualDraftAndNotAnAcceptedLesson() {
        val lesson = NativeLessonDrafts.paramecium
        BilingualLessonContract.validate(lesson)
        assertEquals("u1-paramecium", lesson.lessonId)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, lesson.status)
        assertNull(lesson.review)
        assertEquals(listOf(
            "identity-and-habitat",
            "pellicle-and-cilia",
            "feeding-and-digestion",
            "osmoregulation",
            "nuclear-dimorphism",
            "conjugation",
            "classroom-observation",
        ), lesson.sections.map { it.id })
        assertTrue(lesson.sections.all { it.paragraphs.size >= 2 })
        assertTrue(lesson.sections.all { it.scientificSources.isNotEmpty() })
        assertTrue(lesson.sections.flatMap { it.scientificSources }.all {
            it.startsWith("https://openstax.org/") ||
                it.startsWith("https://pmc.ncbi.nlm.nih.gov/") ||
                it.startsWith("https://pubmed.ncbi.nlm.nih.gov/")
        })
    }

    @Test
    fun translationIsPresentAcrossEveryTeachingParagraph() {
        val lesson = NativeLessonDrafts.paramecium
        assertTrue(lesson.title.english.isNotBlank() && lesson.title.tamil.isNotBlank())
        for (section in lesson.sections) {
            assertTrue(section.heading.english.isNotBlank())
            assertTrue(section.heading.tamil.isNotBlank())
            assertTrue(section.paragraphs.all {
                it.english.isNotBlank() && it.tamil.isNotBlank()
            })
        }
        assertTrue(lesson.sections.sumOf { it.paragraphs.size } >= 16)
    }

    @Test
    fun noOtherChapterSilentlyInheritsTheParameciumDraft() {
        assertNotNull(NativeLessonDrafts.forChapter("u1-paramecium"))
        assertNull(NativeLessonDrafts.forChapter("u4-earthworm-type-study"))
        assertNull(NativeLessonDrafts.forChapter("u4-penaeus"))
        assertNull(NativeLessonDrafts.forChapter("u5-asterias"))
        assertNull(NativeLessonDrafts.forChapter("nonsense"))
        assertFalse(NativeCurriculumManifest.typeStudies.any {
            it.status == AcademicWorkStatus.ACCEPTED
        })
    }

    @Test
    fun draftFailsIfSomebodyLabelsItAcceptedWithoutIndependentReview() {
        val unsafe = NativeLessonDrafts.paramecium.copy(
            status = AcademicWorkStatus.ACCEPTED,
        )
        var failed = false
        try {
            BilingualLessonContract.validate(unsafe)
        } catch (expected: IllegalArgumentException) {
            failed = true
        }
        assertTrue(failed)
    }
}
