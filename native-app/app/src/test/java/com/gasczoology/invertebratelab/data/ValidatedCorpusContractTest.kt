package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatedCorpusContractTest {
    @Test
    fun fixedAcceptanceCountsMatchCommittedA5Gate() {
        assertEquals(5, ValidatedCorpusContract.UNIT_COUNT)
        assertEquals(43, ValidatedCorpusContract.CHAPTER_COUNT)
        assertEquals(86, ValidatedCorpusContract.A5_PAIR_COUNT)
        assertEquals(mapOf(1 to 14, 2 to 18, 3 to 14, 4 to 16, 5 to 24), ValidatedCorpusContract.a5PairsPerUnit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun emptyCorpusCannotPassMigrationGate() {
        ValidatedCorpusContract.validate(emptyList())
    }
}
