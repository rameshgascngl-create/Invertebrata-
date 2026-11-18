package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ObeliaArtworkSelectionTest {
    @Test fun polymorphismUsesNativeDetailedAtlasInsteadOfNumberedIconSchematic() {
        val slot = "lib/obelia-colony"
        assertEquals("type_studies/obelia/colony", BookFigureLibrary.stemFor(slot))
        val figure = PolymorphismBookChapter.chapter.allBlocks()
            .filterIsInstance<BookFigure>().single { it.slot == slot }
        assertTrue(figure.caption.english.contains("hydrorhiza"))
        assertTrue(figure.caption.tamil.contains("ஹைட்ராந்து"))
        assertFalse("Outdated numbered keys refer to unrelated pixels",
            figure.caption.english.contains("(1)"))
        assertFalse(figure.caption.tamil.contains("(1)"))
        assertTrue("Reused native atlas must retain a displayed source review credit",
            BookFigureLibrary.creditFor(slot)!!.en.contains("source and rights review pending"))
    }

    @Test fun physaliaTamilDescriptionDoesNotIncorrectlyCallItFish() {
        val all = BookChapterContract.allTexts(PolymorphismBookChapter.chapter)
        assertTrue(all.any { it.english.contains("Portuguese man-of-war") })
        assertFalse(all.any { it.tamil.contains("போர்க்கப்பல் மீன்") })
    }
}
