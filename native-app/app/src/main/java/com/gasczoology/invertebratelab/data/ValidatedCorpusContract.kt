package com.gasczoology.invertebratelab.data

/**
 * Immutable migration contract derived from the committed A5 acceptance gate.
 * This contains counts and provenance only; approved academic prose is migrated separately.
 */
object ValidatedCorpusContract {
    const val SOURCE_RECOVERY_BRANCH = "recovery/v193-academic-a5-20261008"
    const val SOURCE_ACCEPTANCE_HEAD = "c35cc668820464614c5b5aeadff9573716f2263a"

    const val UNIT_COUNT = 5
    const val CHAPTER_COUNT = 43
    const val A5_PAIR_COUNT = 86

    val a5PairsPerUnit: Map<Int, Int> = linkedMapOf(
        1 to 14,
        2 to 18,
        3 to 14,
        4 to 16,
        5 to 24,
    )

    fun validate(units: List<AcademicUnit>) {
        require(units.size == UNIT_COUNT) { "Expected $UNIT_COUNT units, found ${units.size}" }
        val chapters = units.flatMap { it.chapters }
        require(chapters.size == CHAPTER_COUNT) { "Expected $CHAPTER_COUNT chapters, found ${chapters.size}" }

        val questions = chapters.flatMap { it.a5Questions }
        require(questions.size == A5_PAIR_COUNT) { "Expected $A5_PAIR_COUNT A5 pairs, found ${questions.size}" }
        require(questions.map { it.id }.toSet().size == A5_PAIR_COUNT) { "Duplicate A5 question IDs" }

        for ((unit, expected) in a5PairsPerUnit) {
            val actual = chapters.filter { it.unitNumber == unit }.sumOf { it.a5Questions.size }
            require(actual == expected) { "Unit $unit expected $expected A5 pairs, found $actual" }
        }
    }
}
