package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class PlateCreditsTest {
    @Test fun parsesBilingualCreditsAndFallsBackToEnglish() {
        val m = PlateCreditParser.parse(
            """{"a/b":{"en":"Source: X","ta":"மூலம்: X"},"c/d":{"en":"Only English"}}""")
        assertEquals("மூலம்: X", m.getValue("a/b").text(AppLanguage.TAMIL))
        assertEquals("Only English", m.getValue("c/d").text(AppLanguage.TAMIL))
    }

    @Test fun missingOrMalformedJsonNeverThrows() {
        assertTrue(PlateCreditParser.parse("").isEmpty())
        assertTrue(PlateCreditParser.parse("{not json").isEmpty())
        assertTrue(PlateCreditParser.parse("[1,2]").isEmpty())
    }

    /** Every credit must point at a real packaged plate, in both languages. */
    @Test fun packagedCreditsReferenceExistingPlatesWithTamilText() {
        val root = File("src/main/assets/type_studies")
        val file = File(root, "plate_credits.json")
        if (!file.isFile) return
        val credits = PlateCreditParser.parse(file.readText(Charsets.UTF_8))
        for ((slot, credit) in credits) {
            val assetsRoot = File("src/main/assets")
            assertTrue("plate_credits.json lists '$slot' but no image exists for it",
                listOf(root, assetsRoot).any { base ->
                    File(base, "$slot.png").isFile || File(base, "$slot.webp").isFile })
            assertTrue("Credit for $slot needs Tamil text",
                credit.ta.any { it in '஀'..'௿' })
        }
    }
}
