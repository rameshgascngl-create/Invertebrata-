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
    private val file = File("src/main/assets/book_figures/coral-polyp-noaa-original.png")

    @Test fun highResolutionPublicDomainSourceIsPackagedWithoutModification() {
        assertEquals("book_figures/coral-polyp-noaa-original", BookFigureLibrary.stemFor(slot))
        assertTrue("NOAA PNG missing from offline assets", file.isFile)
        assertFalse("Erroneously labelled derivative must not ship",
            File("src/main/assets/book_figures/coral-polyp-noaa.png").exists())
        val bytes = file.readBytes()
        assertTrue(bytes.size > 50_000)
        assertTrue(bytes.take(8).toByteArray().contentEquals(
            byteArrayOf(0x89.toByte(), 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)))
        fun u32(at: Int) = ((bytes[at].toInt() and 255) shl 24) or
            ((bytes[at + 1].toInt() and 255) shl 16) or
            ((bytes[at + 2].toInt() and 255) shl 8) or (bytes[at + 3].toInt() and 255)
        assertEquals(1000, u32(16))
        assertEquals(1005, u32(20))
        assertEquals("0bdc2bab45339da873ef2879ac4acd5f59948f8e1fea2e377ff3d699a92cac62",
            MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) })
        val credit = BookFigureLibrary.creditFor(slot)!!
        assertTrue(credit.en.contains("NOAA"))
        assertTrue(credit.en.contains("Flower Garden Banks"))
        assertTrue(credit.ta.contains("பவள"))
        assertTrue(credit.en.contains("review pending"))
    }

    @Test fun coralLessonDescribesActualSourceNotRemovedSevenNumberDiagram() {
        val figure = CoralsBookChapter.chapter.allBlocks()
            .filterIsInstance<BookFigure>().single { it.slot == slot }
        assertTrue(figure.caption.english.contains("NOAA"))
        assertTrue(figure.caption.tamil.contains("நீள்வெட்டுத்"))
        assertTrue("Keep coral caption brief and move details to the key",
            figure.caption.english.length < 145 && figure.caption.tamil.length < 145)
        assertFalse(figure.caption.english.contains("(1)"))
        assertFalse(figure.caption.tamil.contains("(1)"))
        val keys = CoralsBookChapter.chapter.allBlocks()
            .filterIsInstance<BookKeyPoints>().single { it.title.english.startsWith("Anatomical labels") }
        assertTrue(keys.points.size >= 6)
        assertTrue(keys.points.all { it.english.isNotBlank() && it.tamil.isNotBlank() })
    }
}
