package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.security.MessageDigest

class UnitVExistingFigureCatalogTest {
    @Test fun allSixteenExistingImagesAreBundledAndUnique() {
        assertEquals(12, UnitVExistingFigureCatalog.forChapter("u5-pila").size)
        assertEquals(4, UnitVExistingFigureCatalog.forChapter("u5-asterias").size)
        assertTrue(UnitVExistingFigureCatalog.forChapter("u4-penaeus").isEmpty())
        val digest = MessageDigest.getInstance("SHA-256")
        val hashes = UnitVExistingFigureCatalog.figures.map { figure ->
            val file = File("src/main/assets/${figure.assetStem}.png")
            assertTrue("Missing offline source image: ${figure.assetStem}", file.isFile)
            val bytes = file.readBytes()
            assertTrue("Blank or malformed PNG: ${figure.assetStem}", bytes.size > 16_000)
            val signature = byteArrayOf(-119,80,78,71,13,10,26,10)
            assertTrue("Not a PNG: ${figure.assetStem}", bytes.copyOfRange(0,8).contentEquals(signature))
            assertTrue("Missing native caption", figure.caption.english.isNotBlank() && figure.caption.tamil.isNotBlank())
            assertTrue("Missing science warning", figure.caution.english.isNotBlank() && figure.caution.tamil.isNotBlank())
            digest.digest(bytes).joinToString("") { "%02x".format(it) }
        }
        assertEquals("Duplicated source PNGs in Unit V lessons", hashes.size, hashes.distinct().size)
        assertFalse(UnitVExistingFigureCatalog.figures.any { it.assetStem.startsWith("type_studies/") })
    }

    @Test fun unapprovedArchiveNeverEntersStudentChapterSections() {
        for (section in listOf("external", "dual-respiration", "radula-and-gut",
                "heart-and-haemocoel", "reproduction-and-development")) {
            assertTrue(UnitVExistingFigureCatalog.forSection("u5-pila", section).isEmpty())
        }
        for (section in listOf("external", "water-vascular", "internal",
                "feeding-and-stomachs", "nerve-and-sense")) {
            assertTrue(UnitVExistingFigureCatalog.forSection("u5-asterias", section).isEmpty())
        }
    }

        @Test fun mandatoryAcademicPlatesStillRequireSeparateApproval() {
        val primary = File("src/main/assets/type_studies")
        for (organism in listOf("pila", "asterias")) {
            val study = NativeCurriculumManifest.typeStudies.single { it.organismId == organism }
            assertTrue(study.status == AcademicWorkStatus.DRAFT_UNVERIFIED)
            val available = primary.resolve(organism).listFiles().orEmpty().map { it.name }.toSet()
            assertTrue("Candidate image silently promoted to accepted master", available.isEmpty())
        }
    }
}
