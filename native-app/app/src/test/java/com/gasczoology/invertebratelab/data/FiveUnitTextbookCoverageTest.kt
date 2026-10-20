package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Manuscript/integration coverage gate, NOT scientific, translation or image approval.
 * The original A5 43-chapter inventory remains the independent source of truth.
 */
class FiveUnitTextbookCoverageTest {
    private val inventory get() = NativeCurriculumManifest.a5ChapterIdsByUnit

    @Test
    fun all43SyllabusChaptersHaveExactlyOneNamedNativeReaderContentRoute() {
        val unitOneAndFive = FiveUnitRestorationLessons.byId.keys
        val unitTwo = UnitIILessons.chapterIds.toSet()
        val unitThree = UnitIIILessons.chapterIds.toSet()
        val unitFourSupplementary = UnitIVSupplementaryLessons.byId.keys
        val special = setOf("u1-paramecium", "u1-proto-parasites", "u4-nereis", "u4-penaeus")

        assertEquals(
            (inventory.getValue(1) - setOf("u1-paramecium", "u1-proto-parasites") +
                inventory.getValue(5)).toSet(), unitOneAndFive)
        assertEquals(inventory.getValue(2).toSet(), unitTwo)
        assertEquals(inventory.getValue(3).toSet(), unitThree)
        assertEquals((inventory.getValue(4) - setOf("u4-nereis", "u4-penaeus")).toSet(),
            unitFourSupplementary)

        val allRoutes = listOf(unitOneAndFive, unitTwo, unitThree, unitFourSupplementary, special)
        val flattened = allRoutes.flatMap { it }
        assertEquals(43, flattened.size)
        assertEquals(43, flattened.distinct().size)
        assertEquals(inventory.values.flatten().toSet(), flattened.toSet())
    }

    @Test
    fun unitFiveTypeStudiesContainFullBilingualFunctionalSections() {
        val pila = FiveUnitRestorationLessons.byId.getValue("u5-pila")
        val asterias = FiveUnitRestorationLessons.byId.getValue("u5-asterias")
        val wvs = FiveUnitRestorationLessons.byId.getValue("u5-wvs")
        assertEquals(listOf("external", "mantle", "organs",
            "respiration-mechanism", "circulation-excretion"),
            pila.sections.map { it.id })
        assertEquals(listOf("external", "water-vascular", "internal",
            "nervous-coelom", "development"),
            asterias.sections.map { it.id })
        assertEquals(listOf("route", "operation", "functional-sequence"),
            wvs.sections.map { it.id })
        for (lesson in listOf(pila, asterias, wvs)) {
            assertTrue(lesson.heading.english.isNotBlank())
            assertTrue(lesson.heading.tamil.isNotBlank())
            assertEquals(lesson.sections.size, lesson.sections.map { it.id }.toSet().size)
            for (section in lesson.sections) {
                assertTrue(section.title.english.isNotBlank())
                assertTrue(section.title.tamil.isNotBlank())
                assertTrue(section.paragraphs.size >= 2)
                assertTrue(section.paragraphs.all {
                    it.english.isNotBlank() && it.tamil.isNotBlank()
                })
            }
        }
        // Keep core physiological content explicit, not a generic one-paragraph summary.
        assertTrue(pila.sections.single { it.id == "respiration-mechanism" }
            .paragraphs.first().english.contains("ctenidium"))
        assertTrue(asterias.sections.single { it.id == "development" }
            .paragraphs.first().english.contains("brachiolaria"))
    }

    @Test
    fun unapprovedReaderContentAppearsBeforeUnchangedA5PracticeInNativeUi() {
        val ui = File("src/main/java/com/gasczoology/invertebratelab/ui/InvertebrataApp.kt")
            .readText(Charsets.UTF_8)
        assertTrue(ui.contains("if (restoredLesson != null)"))
        assertTrue(ui.contains("restoredLesson.sections"))
        assertTrue(ui.contains("UnitIITeachingChapter(chapter"))
        assertTrue(ui.contains("UnitIIITeachingChapter(chapter"))
        assertTrue(ui.contains("UnitIVSupplementaryLessons.byId.containsKey(chapter.id)"))
        assertTrue(ui.indexOf("if (restoredLesson != null)") <
            ui.indexOf("items(chapter.a5Questions, key = { it.id })"))
        assertFalse(ui.contains("AcademicWorkStatus.ACCEPTED"))
    }
}
