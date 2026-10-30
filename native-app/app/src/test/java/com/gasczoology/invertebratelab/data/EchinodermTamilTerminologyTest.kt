package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Editorial guard for the five native echinoderm textbook chapters. */
class EchinodermTamilTerminologyTest {
    private val sourceDir = File("src/main/java/com/gasczoology/invertebratelab/data")
    private val chapters = listOf(
        "AsteriasBookChapter.kt",
        "EchinodermataBookChapter.kt",
        "EchinodermClassesBookChapter.kt",
        "EchinoAffinitiesBookChapter.kt",
        "EchinoLarvaeBookChapter.kt"
    )

    @Test fun echinodermChaptersUseApprovedWaterVascularTerminology() {
        for (chapter in chapters) {
            val file = File(sourceDir, chapter)
            assertTrue("Missing textbook chapter: $chapter", file.isFile)
            val text = file.readText(Charsets.UTF_8)
            for (wrong in listOf("நீர் குருதியோட்ட மண்டலம்", "நீர் நாள மண்டலம்", "நீர்க்குழல் மண்டலம்")) {
                assertFalse("$chapter contains obsolete terminology: $wrong", text.contains(wrong))
            }
        }
        val combined = chapters.joinToString("\n") { File(sourceDir, it).readText(Charsets.UTF_8) }
        assertTrue("Approved water vascular term is missing", combined.contains("நீரோட்டக் குழாய் மண்டலம்"))
    }

    @Test fun echinodermChaptersAvoidMalformedTubeFootPlural() {
        for (chapter in chapters) {
            val text = File(sourceDir, chapter).readText(Charsets.UTF_8)
            for (wrong in listOf("குழாய் கால்ங்கள்", "குழாய் கால்ங்கள", "குழாய்ப்பாதங்கள்")) {
                assertFalse("$chapter contains malformed or superseded tube-foot terminology: $wrong", text.contains(wrong))
            }
        }
    }
}
