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

    @Test fun namedNematodeParasitesHaveSeparateSourceLinkedEnglishTamilLifeCycles() {
        val chapter = UnitIIILessons.forChapter("u3-nematode-parasites")!!
        val byId = chapter.sections.associateBy { it.id }
        val expectedSources = linkedMapOf(
            "wuchereria" to "https://www.cdc.gov/dpdx/lymphaticfilariasis/",
            "enterobius" to "https://www.cdc.gov/dpdx/enterobiasis/",
            "ancylostoma" to "https://www.cdc.gov/dpdx/hookworm/",
        )
        for ((id, source) in expectedSources) {
            val section = byId[id] ?: error("Missing named nematode type study: $id")
            assertEquals("Bilingual life-cycle paragraphs required for $id", 2, section.paragraphs.size)
            assertTrue("Missing species-specific CDC source for $id",
                section.scientificSources.contains(source))
            assertTrue(section.paragraphs.all { it.english.length >= 220 && it.tamil.length >= 140 })
            assertTrue(section.heading.english.isNotBlank() && section.heading.tamil.isNotBlank())
        }
        val wuchereria = byId.getValue("wuchereria").paragraphs.joinToString(" ") { it.english }
        val enterobius = byId.getValue("enterobius").paragraphs.joinToString(" ") { it.english }
        val ancylostoma = byId.getValue("ancylostoma").paragraphs.joinToString(" ") { it.english }
        assertTrue(wuchereria.contains("mosquito") && wuchereria.contains("microfilariae"))
        assertTrue(wuchereria.contains("L3") && wuchereria.contains("lymphatic"))
        assertTrue(enterobius.contains("perianal") && enterobius.contains("four to six hours"))
        assertTrue(enterobius.contains("adhesive-tape"))
        assertTrue(ancylostoma.contains("rhabditiform") && ancylostoma.contains("filariform"))
        assertTrue(ancylostoma.contains("L3") && ancylostoma.contains("skin"))
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, chapter.status)
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

    @Test fun archivedAnatomicalLeadersCannotReuseProceduralCoordinates() {
        // Spatial ordering within the rejected procedural sketch is NOT
        // evidence about the six new archival photographs/printed figures.
        // Keep biologically required names/explanations, but do NOT invent
        // tested anatomical endpoints for the unreviewed replacement rasters.
        UnitIIIPlateHotspots.validate()
        assertEquals(6, UnitIIIPlateHotspots.byStem.size)
        val internal=UnitIIIPlateHotspots.forStem("type_studies/fasciola/digestive-reproductive")
        assertTrue(setOf("ovary","testis-anterior","testis-posterior","caecum","uterus","vitellaria")
            .all { key -> internal.any { it.id==key } })
        val external=UnitIIIPlateHotspots.forStem("type_studies/fasciola/external")
        assertTrue(setOf("genital","acetabulum","oral").all { key -> external.any { it.id==key } })
        val ascaris=UnitIIIPlateHotspots.forStem("type_studies/ascaris/male-female-external")
        assertTrue(setOf("female-vulva","female-anus","male-cloaca").all { key ->
            ascaris.any { it.id==key } })
        assertEquals(listOf("unembryonated","unfertilized","embryonated","ingestion",
            "hatch","lungs","swallow","adult"),
            UnitIIIPlateHotspots.forStem("type_studies/ascaris/life-cycle").map { it.id })
        for ((stem, items) in UnitIIIPlateHotspots.byStem) {
            assertTrue(!UnitIIIPlateHotspots.geometryApproved(stem))
            assertTrue(items.all { it.x.isNaN() && it.y.isNaN() })
        }
    }

    @Test fun cdcAscarisLifeCycleRetainsAccurateTimingAndNonInfectiveEggDistinction() {
        val chapter = UnitIIILessons.forChapter("u3-ascaris")!!
        val stages = UnitIIILessons.ascarisLifeCycle
        assertEquals(listOf("unembryonated","embryonated","ingestion","lung","swallow","adult"),
            stages.map { it.id })
        val narrative = stages.joinToString(" ") { it.explanation.english }
        assertTrue(narrative.contains("18 days"))
        assertTrue(narrative.contains("10–14 days"))
        assertTrue(narrative.contains("2–3 months"))
        assertFalse(narrative.contains("skin penetration"))
        val controls = UnitIIIPlateHotspots.forStem("type_studies/ascaris/life-cycle")
        assertEquals("Unfertilised egg (not infective)",
            controls.single { it.id=="unfertilized" }.title.english)
        assertTrue(controls.any { it.id=="swallow" })
        assertFalse(UnitIIIPlateHotspots.geometryApproved("type_studies/ascaris/life-cycle"))
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, chapter.status)
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
