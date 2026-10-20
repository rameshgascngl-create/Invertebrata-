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

    @Test fun allNineTypeStudiesHaveDepthAppropriateToOrganismStudy() {
        // Minimum manuscript depth is an engineering coverage requirement,
        // not zoological image or Tamil editorial acceptance.
        val expected = NativeCurriculumManifest.typeStudies.map { it.organismId }
        assertEquals(listOf("paramecium","sycon","obelia","fasciola","ascaris",
            "earthworm","penaeus","pila","asterias"), expected)
        assertTrue(NativeLessonDrafts.paramecium.sections.size >= 10)
        for (id in listOf("u2-sycon","u2-obelia")) {
            assertTrue(UnitIILessons.chapters.getValue(id).sections.size >= 6)
        }
        for (id in listOf("u3-fasciola","u3-ascaris")) {
            assertTrue(UnitIIILessons.chapters.getValue(id).sections.size >= 6)
        }
        assertTrue(UnitIVEarthwormLessons.lesson.sections.size >= 10)
        assertTrue(UnitIVNereisLessons.sections.size >= 14)
        assertTrue(UnitIVPenaeusTeaching.sections.size >= 12)
        assertTrue(FiveUnitRestorationLessons.byId.getValue("u5-pila").sections.size >= 8)
        assertTrue(FiveUnitRestorationLessons.byId.getValue("u5-asterias").sections.size >= 8)
        assertTrue(FiveUnitRestorationLessons.byId.getValue("u5-wvs").sections.size >= 4)
    }

    @Test fun penaeidDevelopmentSeparatesProtozoeaMysisFromCrabMegalopa() {
        val lesson = UnitIVSupplementaryLessons.byId.getValue("u4-crustacean-larvae")
        assertTrue(lesson.sections.size >= 7)
        val sections = lesson.sections.associateBy { it.id }
        val protozoea = sections.getValue("penaeid-protozoea")
        val mysis = sections.getValue("penaeid-mysis")
        for (section in listOf(protozoea, mysis)) {
            assertEquals(2, section.paragraphs.size)
            assertTrue(section.title.english.isNotBlank() && section.title.tamil.isNotBlank())
            assertTrue(section.paragraphs.all { it.english.length >= 200 && it.tamil.length >= 140 })
        }
        val narrative = mysis.paragraphs.joinToString(" ") { it.english }
        assertTrue(narrative.contains("nauplius → protozoea → mysis → postlarva"))
        assertTrue(narrative.contains("zoea → megalopa → juvenile"))
        assertTrue(lesson.reading.any { it.startsWith("https://") })
    }

    @Test fun cephalopodsCoverFunctionalSystemsReproductionAndFisheries() {
        val lesson = FiveUnitRestorationLessons.byId.getValue("u5-cephalopods")
        assertTrue(lesson.sections.size >= 5)
        val sections = lesson.sections.associateBy { it.id }
        for (id in listOf("circulation-and-feeding", "reproduction-and-fisheries")) {
            val section = sections.getValue(id)
            assertEquals(2, section.paragraphs.size)
            assertTrue(section.title.english.isNotBlank() && section.title.tamil.isNotBlank())
            assertTrue(section.paragraphs.all { it.english.length >= 200 && it.tamil.length >= 140 })
        }
        val circulation = sections.getValue("circulation-and-feeding").paragraphs
            .joinToString(" ") { it.english }
        assertTrue(listOf("systemic heart", "branchial hearts", "gills", "radula", "beak")
            .all { circulation.contains(it) })
        val reproduction = sections.getValue("reproduction-and-fisheries").paragraphs
            .joinToString(" ") { it.english }
        assertTrue(listOf("spermatophores", "octopuses", "fisheries", "sustainable harvest")
            .all { reproduction.contains(it) })
    }

    @Test fun unitFourArthropodaCoversClassLevelComparisonsWithoutOverclaimingRanks() {
        val chapter = UnitIVSupplementaryLessons.byId.getValue("u4-arthropoda")
        val byId = chapter.sections.associateBy { it.id }
        assertTrue(chapter.sections.size >= 6)
        val chelicerates = byId.getValue("chelicerate-myriapod-classes")
        val pancrustacea = byId.getValue("crustacean-hexapod-classes")
        for (section in listOf(chelicerates, pancrustacea)) {
            assertEquals(2, section.paragraphs.size)
            assertTrue(section.title.english.isNotBlank() && section.title.tamil.isNotBlank())
            assertTrue(section.paragraphs.all { it.english.length >= 200 && it.tamil.length >= 140 })
        }
        val text = (chelicerates.paragraphs + pancrustacea.paragraphs)
            .joinToString(" ") { it.english }
        for (taxon in listOf("Arachnida", "Chilopoda", "Diplopoda", "Symphyla",
                "Pauropoda", "Malacostraca", "Branchiopoda", "Copepoda",
                "Ostracoda", "Insecta", "Trilobita", "Pancrustacea")) {
            assertTrue("Missing Arthropoda class comparison: " + taxon, text.contains(taxon))
        }
        assertTrue(chapter.reading.any { it.startsWith("https://") })
    }

    @Test fun unitFiveMolluscaExplicitlyDistinguishesSevenAndEightClassSchemes() {
        val lesson = FiveUnitRestorationLessons.byId.getValue("u5-mollusca")
        val byId = lesson.sections.associateBy { it.id }
        assertTrue(lesson.sections.size >= 5)
        val seven = byId.getValue("traditional-seven-classes")
        val eight = byId.getValue("modern-eight-classes")
        for (section in listOf(seven, eight)) {
            assertEquals(2, section.paragraphs.size)
            assertTrue(section.title.english.isNotBlank() && section.title.tamil.isNotBlank())
            assertTrue(section.paragraphs.all { it.english.length >= 220 && it.tamil.length >= 150 })
        }
        val classical = seven.paragraphs.joinToString(" ") { it.english }
        for (taxon in listOf("Aplacophora", "Polyplacophora", "Monoplacophora",
                "Gastropoda", "Bivalvia", "Scaphopoda", "Cephalopoda")) {
            assertTrue("Missing classical molluscan class: " + taxon, classical.contains(taxon))
        }
        val contemporary = eight.paragraphs.joinToString(" ") { it.english }
        assertTrue(contemporary.contains("Solenogastres"))
        assertTrue(contemporary.contains("Caudofoveata"))
        assertTrue(contemporary.contains("eight, not eight plus Aplacophora"))
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
