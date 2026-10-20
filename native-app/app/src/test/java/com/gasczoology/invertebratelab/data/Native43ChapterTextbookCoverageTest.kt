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


    /**
     * The 43 A5 chapters plus the distinct Earthworm slot must contain their
     * specific biological concepts, not just enough characters to pass a
     * section-count or generic bilingual-prose threshold. This checks source
     * content and Tamil-script presence; expert editorial approval is separate.
     */
    @Test fun all44TeachingSlotsRetainSyllabusSpecificBiologyInBilingualText() {
        val teaching = linkedMapOf<String, Pair<String, String>>()
        fun register(id: String, text: List<BilingualText>) {
            assertFalse("Duplicate native teaching source: " + id, teaching.containsKey(id))
            teaching[id] = text.joinToString(" ") { it.english } to
                text.joinToString(" ") { it.tamil }
        }
        FiveUnitRestorationLessons.chapters.forEach { chapter ->
            register(chapter.chapterId, chapter.sections.flatMap { it.paragraphs })
        }
        UnitIILessons.chapters.forEach { (id, chapter) ->
            register(id, chapter.sections.flatMap { it.paragraphs })
        }
        UnitIIILessons.chapters.forEach { (id, chapter) ->
            register(id, chapter.sections.flatMap { it.paragraphs })
        }
        UnitIVSupplementaryLessons.chapters.forEach { chapter ->
            register(chapter.chapterId, chapter.sections.flatMap { it.paragraphs })
        }
        register("u1-paramecium", NativeLessonDrafts.paramecium.sections.flatMap { it.paragraphs })
        register("u4-nereis", UnitIVNereisLessons.sections.flatMap { it.paragraphs })
        register("u4-penaeus", UnitIVPenaeusTeaching.sections.flatMap { it.paragraphs })
        register(NativeCurriculumManifest.EARTHWORM_LESSON_ID,
            UnitIVEarthwormLessons.lesson.sections.flatMap { it.paragraphs })
        // Parasite chapter uses independent organism-specific native UI models.
        // Examine both files; no A5 answer is used as a teaching-text surrogate.
        val uiRoot = "src/main/java/com/gasczoology/invertebratelab/ui/"
        val parasiteSource = listOf("UnitIFlagellateTypeStudies.kt",
            "UnitIEntamoebaMorphology.kt")
            .joinToString(" ") { File(uiRoot + it).readText(Charsets.UTF_8) }
        teaching["u1-proto-parasites"] = parasiteSource to parasiteSource

        val topics = linkedMapOf(
            "u1-intro" to listOf("invertebrates", "coelom", "taxonomic"),
            "u1-protozoa" to listOf("Sarcodina", "Mastigophora", "Ciliata", "Sporozoa"),
            "u1-paramecium" to listOf("conjugation", "binary fission", "contractile vacuole"),
            "u1-proto-parasites" to listOf("Trypanosoma", "Leishmania", "Entamoeba"),
            "u1-nutrition" to listOf("Holozoic", "Osmotrophic", "phototrophic"),
            "u1-host-parasite" to listOf("Entamoeba", "Plasmodium", "Anopheles"),
            "u1-locomotion" to listOf("pseudopodia", "axoneme", "metachronal"),
            "u2-porifera" to listOf("Calcarea", "Demospongiae", "Hexactinellida"),
            "u2-sycon" to listOf("prosopyle", "apopyle", "amphiblastula"),
            "u2-canals" to listOf("asconoid", "syconoid", "leuconoid"),
            "u2-reproduction" to listOf("budding", "gemmules", "amphiblastula"),
            "u2-cnidaria" to listOf("Hydrozoa", "Scyphozoa", "Anthozoa"),
            "u2-obelia" to listOf("hydranth", "gonangium", "planula"),
            "u2-corals" to listOf("fringing", "barrier", "atoll"),
            "u2-coral-economics" to listOf("fisheries", "tourism", "coastal"),
            "u2-polymorphism" to listOf("Physalia", "medusae"),
            "u3-platy" to listOf("Turbellaria", "Monogenea", "Trematoda", "Cestoda"),
            "u3-fasciola" to listOf("miracidium", "metacercaria", "vitelline"),
            "u3-platy-adapt" to listOf("tegument", "attachment", "eggs"),
            "u3-aschel" to listOf("Aschelminthes", "Nematoda", "pseudocoel"),
            "u3-ascaris" to listOf("embryonated", "lungs", "cloaca"),
            "u3-nematode-parasites" to listOf("Wuchereria", "Enterobius", "Ancylostoma"),
            "u3-nematode-adapt" to listOf("cuticle", "eggs", "larval"),
            "u4-annelida" to listOf("metameric", "prostomium", "chaetae"),
            "u4-nereis" to listOf("parapodia", "prostomium", "trochophore"),
            "u4-metamerism" to listOf("segmentation", "septa", "coelom"),
            "u4-modes-life" to listOf("marine", "soil", "leeches"),
            "u4-arthropoda" to listOf("Arachnida", "Chilopoda", "Malacostraca"),
            "u4-penaeus" to listOf("antennules", "pleopods", "nauplius", "protozoea", "mysis"),
            "u4-peripatus" to listOf("onychophora", "lobopods", "slime"),
            "u4-crustacean-larvae" to listOf("protozoea", "mysis", "megalopa"),
            "u4-earthworm-type-study" to listOf("typhlosole", "nephridia", "setae"),
            "u5-mollusca" to listOf("Monoplacophora", "Solenogastres", "Caudofoveata"),
            "u5-pila" to listOf("osphradium", "radula", "torsion"),
            "u5-torsion" to listOf("coiling", "180-degree"),
            "u5-pearl" to listOf("nacre", "mantle", "aragonite"),
            "u5-echinodermata" to listOf("Crinoidea", "Asteroidea", "Holothuroidea"),
            "u5-asterias" to listOf("madreporite", "pedicellariae", "bipinnaria"),
            "u5-echino-larvae" to listOf("brachiolaria", "auricularia", "pluteus"),
            "u5-echino-affinities" to listOf("Ambulacraria", "hemichordates"),
            "u5-fossils" to listOf("trilobite", "ammonoid", "brachiopod"),
            "u5-foot" to listOf("Pila", "Unio", "cephalopod"),
            "u5-wvs" to listOf("ampulla", "radial", "madreporite"),
            "u5-cephalopods" to listOf("branchial", "spermatophores", "funnel"),
        )
        val authoritative = NativeCurriculumManifest.lessonSlots.map { it.id }.toSet()
        assertEquals(44, authoritative.size)
        assertEquals(authoritative, teaching.keys.toSet())
        assertEquals(authoritative, topics.keys.toSet())
        for ((id, keyTerms) in topics) {
            val (english, tamil) = teaching.getValue(id)
            assertTrue("No substantial English explanatory text: " + id, english.length >= 250)
            assertTrue("Missing substantial Tamil textbook text: " + id,
                tamil.count { it in '\u0B80'..'\u0BFF' } >= 95)
            for (keyword in keyTerms) {
                assertTrue("Missing subject-specific explanation in " + id +
                    ": " + keyword, english.contains(keyword, ignoreCase = true))
            }
        }
        assertTrue(NativeCurriculumManifest.lessonSlots.none {
            it.status == AcademicWorkStatus.ACCEPTED
        })
    }

    @Test fun mandatoryAnatomicalPlatePathsAreUniqueAndDoNotReuseBitmapBytes() {
        val studies = NativeCurriculumManifest.typeStudies
        val stems = studies.flatMap { study ->
            study.requiredPlateIds.map { plate ->
                if (study.organismId == "paramecium")
                    "unit1/replacements/paramecium_" + plate
                else "type_studies/" + study.organismId + "/" + plate
            }
        }
        assertEquals(35, stems.size)
        assertEquals(35, stems.toSet().size)
        val seen = mutableMapOf<String, String>()
        for (stem in stems) {
            val matching = listOf(".png", ".webp")
                .map { File("src/main/assets/" + stem + it) }
                .filter { it.isFile }
            assertTrue("Two incompatible raster formats compete for " + stem,
                matching.size <= 1)
            val file = matching.firstOrNull() ?: continue
            val digest = java.security.MessageDigest.getInstance("SHA-256")
                .digest(file.readBytes())
                .joinToString("") { "%02x".format(it) }
            val prior = seen.putIfAbsent(digest, stem)
            assertEquals("Repeated anatomical bitmap used in distinct mandatory slots: " +
                stem + " and " + prior, null, prior)
        }
        val ui = "src/main/java/com/gasczoology/invertebratelab/ui/"
        val app = File(ui + "InvertebrataApp.kt").readText(Charsets.UTF_8)
        val u2 = File(ui + "UnitIITeachingChapter.kt").readText(Charsets.UTF_8)
        val u3 = File(ui + "UnitIIITeachingChapter.kt").readText(Charsets.UTF_8)
        val earthworm = File(ui + "UnitIVEarthwormReader.kt").readText(Charsets.UTF_8)
        val required = File(ui + "RequiredTypeStudyPlates.kt").readText(Charsets.UTF_8)
        assertTrue(app.contains("RequiredTypeStudyPlates(study, language)"))
        assertTrue(u2.contains("RequiredTypeStudyPlates(study, language)"))
        assertTrue(u3.contains("UnitIIIPlateGallery(chapter.id, language, narrator)"))
        assertFalse(u3.contains("RequiredTypeStudyPlates(study, language)"))
        assertTrue(earthworm.contains("type_studies/earthworm/"))
        assertTrue(required.contains("unit1/replacements/paramecium_"))
        // Source-path checks are NOT approval of the depicted anatomy or Tamil.
    }


    @Test fun asteriasReusedPublicDomainImagesAreRealDistinctPngsWithTraceableCredits() {
        val dir = File("src/main/assets/type_studies/asterias")
        val external = File(dir, "external.png")
        val vascular = File(dir, "water-vascular-system.png")
        val attribution = File(dir, "ATTRIBUTION.md")
        assertTrue("Asterias oral and aboral source plate absent", external.isFile)
        assertTrue("Asterias water-vascular source plate absent", vascular.isFile)
        assertTrue("Public-domain licence and author record absent", attribution.isFile)
        val pngHeader = byteArrayOf(137.toByte(), 80, 78, 71, 13, 10, 26, 10)
        for (file in listOf(external, vascular)) {
            val bytes = file.readBytes()
            assertTrue("Empty or implausibly small figure: " + file.name, bytes.size > 50_000)
            assertTrue("Invalid PNG header in " + file.name,
                bytes.take(8).toByteArray().contentEquals(pngHeader))
        }
        assertFalse("Anatomically unrelated external and vascular figures must be different",
            external.readBytes().contentEquals(vascular.readBytes()))
        val credits = attribution.readText(Charsets.UTF_8)
        for (value in listOf("Hubert Lyman Clark", "Augusta Foote Arnold",
                "PUBLIC DOMAIN", "Asterias forbesi", "generalized")) {
            assertTrue("Source attribution missing: " + value,
                credits.contains(value, ignoreCase = true))
        }
        val plateGallery = File("src/main/java/com/gasczoology/invertebratelab/ui/RequiredTypeStudyPlates.kt")
            .readText(Charsets.UTF_8)
        assertTrue(plateGallery.contains("asterias-external-species-provenance"))
        assertTrue(plateGallery.contains("asterias-wvs-legend"))
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
