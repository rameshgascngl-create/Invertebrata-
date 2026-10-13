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

    @Test fun sixRequiredPlateSlotsMatchSyllabusAndDoNotClaimAcceptance() {
        UnitIIIPlateCatalog.validateAgainstSyllabus()
        assertEquals(6, UnitIIIPlateCatalog.plates.size)
        assertEquals(3, UnitIIIPlateCatalog.forChapter("u3-fasciola").size)
        assertEquals(3, UnitIIIPlateCatalog.forChapter("u3-ascaris").size)
        assertTrue(UnitIIIPlateCatalog.forChapter("u3-platy").isEmpty())
        assertTrue(UnitIIIPlateCatalog.plates.all {
            it.title.english.isNotBlank() && it.title.tamil.isNotBlank() &&
                it.learningAim.english.isNotBlank() && it.learningAim.tamil.isNotBlank() &&
                it.assetStem.startsWith("type_studies/")
        })
        assertEquals(listOf("external", "digestive-reproductive", "life-cycle"),
            UnitIIIPlateCatalog.forChapter("u3-fasciola").map { it.plateId })
        assertEquals(listOf("male-female-external", "female-internal", "life-cycle"),
            UnitIIIPlateCatalog.forChapter("u3-ascaris").map { it.plateId })
    }

    @Test fun anatomicalLeadersAreAnchoredToDistinctBiologicalStructures() {
        UnitIIIPlateHotspots.validate()
        assertEquals(6, UnitIIIPlateHotspots.byStem.size)
        val internal=UnitIIIPlateHotspots.forStem("type_studies/fasciola/digestive-reproductive")
        val anterior=internal.single { it.id=="testis-anterior" }
        val posterior=internal.single { it.id=="testis-posterior" }
        val ovary=internal.single { it.id=="ovary" }
        assertTrue(ovary.y < anterior.y && anterior.y < posterior.y)
        assertTrue(kotlin.math.abs(anterior.x-posterior.x) < .08f)
        val external=UnitIIIPlateHotspots.forStem("type_studies/fasciola/external")
        assertTrue(external.single{it.id=="genital"}.y <
            external.single{it.id=="acetabulum"}.y)
        val ascaris=UnitIIIPlateHotspots.forStem("type_studies/ascaris/male-female-external")
        val vulva=ascaris.single { it.id=="female-vulva" }
        val anus=ascaris.single { it.id=="female-anus" }
        assertTrue(vulva.y < .45f && anus.y > .85f)
        assertEquals(listOf("unembryonated","embryonated","ingestion","hatch","lungs","adult"),
            UnitIIIPlateHotspots.forStem("type_studies/ascaris/life-cycle").map { it.id })
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
