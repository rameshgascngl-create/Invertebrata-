package com.gasczoology.invertebratelab.data

/**
 * Requirements inventory and honest editorial progress status. A draft is
 * never equivalent to an academically accepted plate or complete type study.
 * Earthworm is an additional lesson slot, NOT an alteration of the accepted A5 corpus.
 */
enum class AcademicWorkStatus { NOT_AUTHORED, DRAFT_UNVERIFIED, ACCEPTED }

data class NativeLessonSlot(
    val id: String,
    val unitNumber: Int,
    val acceptedA5ChapterId: String?,
    val status: AcademicWorkStatus = AcademicWorkStatus.NOT_AUTHORED,
)

data class TypeStudyRequirement(
    val organismId: String,
    val unitNumber: Int,
    val scientificName: String,
    val lessonId: String,
    val acceptedA5ChapterId: String?,
    val requiredPlateIds: List<String>,
    val status: AcademicWorkStatus = AcademicWorkStatus.NOT_AUTHORED,
)

object NativeCurriculumManifest {
    const val EARTHWORM_LESSON_ID = "u4-earthworm-type-study"
    const val EXISTING_A5_CHAPTER_COUNT = 43
    const val REQUIRED_TYPE_STUDY_COUNT = 9
    const val REQUIRED_LESSON_SLOTS = 44

    // Independently maintained exact sequence from each authoritative A5 JSON.
    val a5ChapterIdsByUnit: Map<Int, List<String>> = linkedMapOf(
        1 to listOf("u1-intro", "u1-protozoa", "u1-paramecium", "u1-proto-parasites",
            "u1-nutrition", "u1-host-parasite", "u1-locomotion"),
        2 to listOf("u2-porifera", "u2-sycon", "u2-canals", "u2-reproduction",
            "u2-cnidaria", "u2-obelia", "u2-corals", "u2-coral-economics",
            "u2-polymorphism"),
        3 to listOf("u3-platy", "u3-fasciola", "u3-platy-adapt", "u3-aschel",
            "u3-ascaris", "u3-nematode-parasites", "u3-nematode-adapt"),
        4 to listOf("u4-annelida", "u4-nereis", "u4-metamerism", "u4-modes-life",
            "u4-arthropoda", "u4-penaeus", "u4-peripatus", "u4-crustacean-larvae"),
        5 to listOf("u5-mollusca", "u5-pila", "u5-torsion", "u5-pearl",
            "u5-echinodermata", "u5-asterias", "u5-echino-larvae",
            "u5-echino-affinities", "u5-fossils", "u5-foot", "u5-wvs",
            "u5-cephalopods"),
    )

    // Plate names are work requirements, not drawings, organ validation or approval.
    val typeStudies: List<TypeStudyRequirement> = listOf(
        TypeStudyRequirement("paramecium", 1, "Paramecium", "u1-paramecium",
            "u1-paramecium", listOf("external-cilia", "oral-apparatus",
                "contractile-vacuoles", "locomotion"),
            AcademicWorkStatus.DRAFT_UNVERIFIED),
        TypeStudyRequirement("sycon", 2, "Sycon", "u2-sycon",
            "u2-sycon", listOf("external", "canal-system", "reproduction")),
        TypeStudyRequirement("obelia", 2, "Obelia", "u2-obelia",
            "u2-obelia", listOf("colony", "hydranth-gonangium", "medusa", "life-cycle")),
        TypeStudyRequirement("fasciola", 3, "Fasciola hepatica", "u3-fasciola",
            "u3-fasciola", listOf("external", "digestive-reproductive", "life-cycle")),
        TypeStudyRequirement("ascaris", 3, "Ascaris lumbricoides", "u3-ascaris",
            "u3-ascaris", listOf("male-female-external", "female-internal", "life-cycle")),
        TypeStudyRequirement("earthworm", 4, "Earthworm (Metaphire/Pheretima)",
            EARTHWORM_LESSON_ID, null,
            listOf("external-setae", "digestive", "circulatory", "nervous",
                "excretory", "reproductive")),
        TypeStudyRequirement("penaeus", 4, "Penaeus", "u4-penaeus",
            "u4-penaeus", listOf("external", "appendages", "internal",
                "larval-development")),
        TypeStudyRequirement("pila", 5, "Pila", "u5-pila",
            "u5-pila", listOf("external", "mantle-cavity", "ctenidium", "torsion")),
        TypeStudyRequirement("asterias", 5, "Asterias", "u5-asterias",
            "u5-asterias", listOf("external", "water-vascular-system", "internal",
                "larval-development")),
    )

    val lessonSlots: List<NativeLessonSlot> =
        a5ChapterIdsByUnit.flatMap { (unit, ids) ->
            ids.map { id -> NativeLessonSlot(id, unit, id,
                if (id == "u1-paramecium") AcademicWorkStatus.DRAFT_UNVERIFIED
                else AcademicWorkStatus.NOT_AUTHORED) }
        } + NativeLessonSlot(EARTHWORM_LESSON_ID, 4, null)

    fun validateAgainst(units: List<AcademicUnit>) {
        ValidatedCorpusContract.validate(units)
        require(a5ChapterIdsByUnit.keys == (1..5).toSet())
        require(a5ChapterIdsByUnit.values.sumOf { it.size } == EXISTING_A5_CHAPTER_COUNT)
        for (number in 1..5) {
            val actual = units.single { it.number == number }.chapters.map { it.id }
            require(actual == a5ChapterIdsByUnit.getValue(number)) {
                "Curriculum/A5 ID mismatch in unit " + number
            }
        }
        require(typeStudies.size == REQUIRED_TYPE_STUDY_COUNT)
        require(typeStudies.map { it.organismId }.toSet().size == REQUIRED_TYPE_STUDY_COUNT)
        require(lessonSlots.size == REQUIRED_LESSON_SLOTS)
        require(lessonSlots.map { it.id }.toSet().size == REQUIRED_LESSON_SLOTS)
        require(lessonSlots.count { it.acceptedA5ChapterId == null } == 1)
        require(lessonSlots.single { it.acceptedA5ChapterId == null }.id == EARTHWORM_LESSON_ID)
        require(lessonSlots.count { it.status == AcademicWorkStatus.DRAFT_UNVERIFIED } == 1)
        require(lessonSlots.single { it.status == AcademicWorkStatus.DRAFT_UNVERIFIED }.id ==
            "u1-paramecium")
        require(lessonSlots.none { it.status == AcademicWorkStatus.ACCEPTED })
        require(typeStudies.count { it.status == AcademicWorkStatus.DRAFT_UNVERIFIED } == 1)
        require(typeStudies.single { it.status == AcademicWorkStatus.DRAFT_UNVERIFIED }.organismId ==
            "paramecium")
        require(typeStudies.none { it.status == AcademicWorkStatus.ACCEPTED })
        for (study in typeStudies) {
            val slot = lessonSlots.single { it.id == study.lessonId }
            require(slot.unitNumber == study.unitNumber)
            require(slot.acceptedA5ChapterId == study.acceptedA5ChapterId)
            require(study.scientificName.isNotBlank() && study.requiredPlateIds.isNotEmpty())
            require(study.requiredPlateIds.size == study.requiredPlateIds.toSet().size)
            require(study.requiredPlateIds.all {
                it.matches(Regex("[a-z0-9]+(-[a-z0-9]+)*"))
            })
        }
        require(typeStudies.single { it.organismId == "earthworm" }.acceptedA5ChapterId == null)
    }
}
