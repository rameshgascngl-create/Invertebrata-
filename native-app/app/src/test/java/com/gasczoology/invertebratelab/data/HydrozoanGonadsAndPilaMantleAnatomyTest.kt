package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

/** Reference-grounded comparison of two commonly confused invertebrate systems. */
class HydrozoanGonadsAndPilaMantleAnatomyTest {
    @Test fun obeliaGonadsAreEpidermalInOriginNotAnUnresolvedEitherOr() {
        val medusa = ObeliaBookChapter.chapter.allBlocks()
            .filterIsInstance<BookParagraph>().map { it.text }
            .first { it.english.contains("gonadal tissue is epidermal") }
        assertTrue(medusa.english.contains("epidermal (ectodermal)"))
        assertTrue(medusa.english.contains("rather than gastrodermal"))
        assertTrue(medusa.tamil.contains("புறத்தோல்"))
        assertFalse(medusa.english.contains("some accounts"))
        assertTrue(medusa.english.contains("scyphozoan"))
    }
    @Test fun pilaPulmonaryIsLeftAndCtenidiumIsRight() {
        val breath = PilaBookChapter.chapter.allBlocks().filterIsInstance<BookParagraph>()
            .map { it.text }.first { it.english.contains("pulmonary chamber on the left") }
        assertTrue(breath.english.contains("ctenidial chamber on the right"))
        assertTrue(breath.tamil.contains("இடப்புறத்தில்"))
        assertTrue(breath.tamil.contains("வலப்புறத்தில்"))
        assertFalse(breath.english.contains("branchial chamber on the left"))
    }
}
