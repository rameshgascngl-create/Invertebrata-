package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class UnitIVPenaeusTeachingTest {
    @Test fun substantiveChaptersCoverOrganSystemsWithBilingualProse() {
        val sections=UnitIVPenaeusTeaching.sections
        assertEquals(12,sections.size)
        assertEquals(12,sections.map { it.id }.distinct().size)
        assertTrue(sections.any { it.id == "respiration" })
        assertTrue(sections.any { it.id == "circulation" })
        assertTrue(sections.any { it.id == "reproduction" })
        assertTrue(sections.any { it.id == "nervous" })
        assertTrue(sections.any { it.id == "practical" })
        assertTrue(sections.all {
            it.heading.english.isNotBlank() && it.heading.tamil.isNotBlank() &&
            it.paragraphs.size>=2 && it.source.startsWith("https://www.fao.org/")
        })
        assertTrue(sections.first().paragraphs.first().english.contains("Penaeus indicus"))
        assertTrue(sections.first().paragraphs.first().english.contains("Fenneropenaeus indicus"))
        assertFalse(NativeCurriculumManifest.typeStudies.single {
            it.organismId=="penaeus" }.status == AcademicWorkStatus.ACCEPTED)
    }

    @Test fun exactlyNineteenPairsAndTailFanNeverCountsTelson() {
        val limbs=UnitIVPenaeusTeaching.appendages
        assertEquals(19,limbs.sumOf { it.numberOfPairs })
        assertEquals(listOf(5,3,5,5,1),limbs.map { it.numberOfPairs })
        assertEquals(listOf("head","thoracic-mouth","thoracic-walking",
            "abdomen-swimming","tail-fan"),limbs.map{it.id})
        assertTrue(limbs.last().names.english.contains("unpaired telson"))
        assertTrue(limbs.all { it.role.english.isNotBlank() && it.role.tamil.isNotBlank() })
    }

    @Test fun penaeidNotMacrobrachiumLifeCycleAndA5IdentityPreserved() {
        assertEquals(listOf("egg","nauplius","protozoea","mysis",
            "postlarva","juvenile","adult"),UnitIVPenaeusTeaching.stages.map{it.id})
        assertTrue(UnitIVPenaeusTeaching.stages.all {
            it.morphology.english.isNotBlank() && it.morphology.tamil.isNotBlank()
        })
        assertEquals(43,NativeCurriculumManifest.EXISTING_A5_CHAPTER_COUNT)
        assertEquals(44,NativeCurriculumManifest.REQUIRED_LESSON_SLOTS)
        assertTrue("u4-penaeus" in NativeCurriculumManifest.a5ChapterIdsByUnit.getValue(4))
        val p=NativeCurriculumManifest.lessonSlots.single {it.id=="u4-penaeus"}
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,p.status)
        assertEquals("u4-penaeus",p.acceptedA5ChapterId)
    }
}
