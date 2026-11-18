package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Distinguish Old World Pila globosa from pink-egg Pomacea and preserve the
 * calcium-bearing inner operculum. Biological review is separate from JVM QA.
 * Supporting sources:
 * https://keys.lucidcentral.org/keys/v3/freshwater_molluscs/key/australian_freshwater_molluscs/Media/Html/entities/pomacea_canaliculata.htm
 * https://doi.org/10.52168/bjf.2020.32.06
 */
class PilaSpeciesAccuracyTest {
    private val chapter = PilaBookChapter.chapter
    private val paragraphs = chapter.allBlocks().filterIsInstance<BookParagraph>()
    private val en = paragraphs.joinToString(" ") { it.text.english }
    private val ta = paragraphs.joinToString(" ") { it.text.tamil }

    @Test fun eggLayingIsSpeciesSpecificNotBorrowedFromPomacea() {
        assertTrue(en.contains("pale, calcareous eggs"))
        assertTrue(en.contains("sheltered hollows"))
        assertTrue(ta.contains("ஈரமான") && ta.contains("முட்டை"))
        assertFalse(en.contains("pinkish eggs on plants"))
        assertFalse(en.contains("on plants, walls or the banks"))
        assertFalse(ta.contains("இளஞ்சிவப்பு முட்டைக் கொத்துகளை"))
        assertFalse(ta.contains("தாவரங்கள், சுவர்கள்"))
    }

    @Test fun operculumIsDescribedAsHornyOutsideAndCalcifiedInside() {
        val definition = chapter.definitions.single { it.term.english == "Operculum" }
        assertTrue(definition.meaning.english.contains("horny externally"))
        assertTrue(definition.meaning.english.contains("calcified internally"))
        assertTrue(definition.meaning.tamil.contains("உட்புறம்"))
        assertTrue(en.contains("outer surface is horny and its inner surface is calcified"))
    }

    @Test fun torsionOccursInTheEmbryoNotOnlyInAncestors() {
        val notes = chapter.allBlocks().filterIsInstance<BookNote>()
        val torsion = notes.single { it.title.english.contains("Torsion") }
        assertTrue(torsion.text.english.contains("each Pila within its egg"))
        assertTrue(torsion.text.english.contains("veliger stage"))
        assertTrue(torsion.text.english.contains("hatches already torted"))
        assertTrue(torsion.text.tamil.contains("முட்டைக்குள்"))
        assertTrue(torsion.text.tamil.contains("வெலிகர்"))
        assertFalse(torsion.text.english.contains("larva of its ancestors"))
        assertFalse(torsion.text.tamil.contains("மூதாதையரின் லார்வாவில்"))
    }

    @Test fun bilingualOpeningFocusesOnBiologyInsteadOfGenericChapterDescription() {
        assertTrue(chapter.introduction.size == 2)
        assertTrue(chapter.introduction.all { it.english.length < 400 && it.tamil.length < 490 })
        assertTrue(chapter.introduction.all { !it.english.startsWith("The chapter describes") })
        assertTrue(chapter.introduction.all { it.tamil.isNotBlank() })
        assertTrue(en.contains("juvenile snail resembling the adult"))
        assertFalse(en.contains("hatches and drops into the water"))
    }
}
