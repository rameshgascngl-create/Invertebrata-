package com.gasczoology.invertebratelab.data

import java.io.File
import java.security.MessageDigest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** No source substitution, synthetic artwork, hidden resampling or invented label numbers. */
class NoaaCoralPlateTest {
    private val slot = "lib/coral-polyp"
    private val file = File("src/main/assets/book_figures/coral-polyp-noaa.png")

    @Test fun highResolutionPublicDomainSourceIsPackagedWithoutModification() {
        assertEquals("book_figures/coral-polyp-noaa", BookFigureLibrary.stemFor(slot))
        assertTrue("NOAA PNG missing from offline assets", file.isFile)
        val bytes = file.readBytes()
        assertTrue(bytes.size > 50_000)
        assertTrue(bytes.take(8).toByteArray().contentEquals(
            byteArrayOf(0x89.toByte(), 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)))
        fun u32(at: Int) = ((bytes[at].toInt() and 255) shl 24) or
            ((bytes[at + 1].toInt() and 255) shl 16) or
            ((bytes[at + 2].toInt() and 255) shl 8) or (bytes[at + 3].toInt() and 255)
        assertEquals(1920, u32(16))
        assertEquals(2716, u32(20))
        assertEquals("c7cca1be823372d01cab802cc25b5a4b50c53666bd5b901d8e8374511b87905d",
            MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) })
        val credit = BookFigureLibrary.creditFor(slot)!!
        assertTrue(credit.en.contains("NOAA"))
        assertTrue(credit.en.contains("Earlza"))
        assertTrue(credit.ta.contains("பவள"))
        assertTrue(credit.en.contains("review pending"))
    }

    @Test fun coralLessonDescribesActualSourceNotRemovedSevenNumberDiagram() {
        val figure = CoralsBookChapter.chapter.allBlocks()
            .filterIsInstance<BookFigure>().single { it.slot == slot }
        assertTrue(figure.caption.english.contains("longitudinal section"))
        assertTrue(figure.caption.tamil.contains("நீள்வெட்டுத்"))
        assertFalse(figure.caption.english.contains("(1)"))
        assertFalse(figure.caption.tamil.contains("(1)"))
        val keys = CoralsBookChapter.chapter.allBlocks()
            .filterIsInstance<BookKeyPoints>().single { it.title.english.startsWith("Anatomical labels") }
        assertTrue(keys.points.size >= 6)
        assertTrue(keys.points.all { it.english.isNotBlank() && it.tamil.isNotBlank() })
    }
}
