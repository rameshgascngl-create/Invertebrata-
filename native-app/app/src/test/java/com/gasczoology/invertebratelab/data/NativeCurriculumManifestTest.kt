package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class NativeCurriculumManifestTest {
    private fun corpus(): List<AcademicUnit> {
        val dir = File("src/main/assets/a5")
        return ValidatedA5Parser.parse((1..5).associateWith {
            File(dir, "U" + it + "_A5_VALIDATED.json").readText(Charsets.UTF_8)
        })
    }

    @Test
    fun exactOrderOfAll43AcceptedChaptersMatchesIndependentManifest() {
        val units = corpus()
        NativeCurriculumManifest.validateAgainst(units)
        assertEquals(listOf(7, 9, 7, 8, 12), units.map { it.chapters.size })
        assertEquals(43, NativeCurriculumManifest.lessonSlots.count {
            it.acceptedA5ChapterId != null
        })
        assertEquals(44, NativeCurriculumManifest.lessonSlots.size)
        assertTrue(NativeCurriculumManifest.lessonSlots.all {
            it.status == AcademicWorkStatus.NOT_AUTHORED
        })
    }

    @Test
    fun earthwormIsExplicitSeparateUnitFourSlotWithoutFakeA5Questions() {
        val earthworm = NativeCurriculumManifest.typeStudies.single {
            it.organismId == "earthworm"
        }
        assertEquals(4, earthworm.unitNumber)
        assertEquals(NativeCurriculumManifest.EARTHWORM_LESSON_ID, earthworm.lessonId)
        assertEquals(null, earthworm.acceptedA5ChapterId)
        assertFalse(corpus().flatMap { it.chapters }.any { it.id == earthworm.lessonId })
        assertEquals(setOf("external-setae", "digestive", "circulatory",
            "nervous", "excretory", "reproductive"), earthworm.requiredPlateIds.toSet())
    }

    @Test
    fun nineTypeStudiesHaveCorrectUnitsAndAllRemainUnauthored() {
        val studies = NativeCurriculumManifest.typeStudies
        assertEquals(9, studies.size)
        assertEquals(mapOf("paramecium" to 1, "sycon" to 2, "obelia" to 2,
            "fasciola" to 3, "ascaris" to 3, "earthworm" to 4, "penaeus" to 4,
            "pila" to 5, "asterias" to 5),
            studies.associate { it.organismId to it.unitNumber })
        assertTrue(studies.all { it.status == AcademicWorkStatus.NOT_AUTHORED })
        assertTrue(studies.all { it.requiredPlateIds.isNotEmpty() })
    }

    @Test
    fun wrongChapterIdentityFailsDespiteMatchingLegacyQuestionCounts() {
        val units = corpus()
        val first = units.first()
        val changed = first.copy(chapters = first.chapters.mapIndexed { index, c ->
            if (index == 0) c.copy(id = "u1-improper-substitution") else c
        })
        val modified = listOf(changed) + units.drop(1)
        ValidatedCorpusContract.validate(modified)
        assertThrows(IllegalArgumentException::class.java) {
            NativeCurriculumManifest.validateAgainst(modified)
        }
    }
}
