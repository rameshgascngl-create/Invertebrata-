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

    @Test fun sourceCreditedCommonsPlatesDisplayAuthorLicenceAndStayUnapproved() {
        val root = File("src/main/assets/type_studies/plate_credits.json")
        val source = File("../../provenance/plates.json")
        assertTrue("Missing packaged credit register", root.isFile)
        assertTrue("Missing source-of-truth provenance register", source.isFile)
        val packaged = PlateCreditParser.parse(root.readText(Charsets.UTF_8))
        val provenance = com.google.gson.JsonParser.parseString(source.readText(Charsets.UTF_8))
            .asJsonObject.getAsJsonObject("plates")
        val cases = mapOf(
            "sycon/external" to Triple("Julien Renoult", "CC BY 4.0", "Sycon_ciliatum_29886028.jpg"),
            "sycon/reproduction" to Triple("E. A. Minchin", "Public domain", "Types_of_Sponge_Larvae.jpg"),
            "obelia/medusa" to Triple("The Other 95%", "CC BY-SA 4.0", "Obelia_Medusa_Larva.jpg"),
        )
        for ((slot, evidence) in cases) {
            val record = provenance.getAsJsonObject(slot)
            assertEquals("Do not auto-approve figure: " + slot,
                "DRAFT_UNVERIFIED", record.get("review_status").asString)
            assertTrue("Must preserve original non-AI status: " + slot,
                !record.get("ai_generated").asBoolean)
            val credit = packaged.getValue(slot)
            assertEquals("English displayed credit diverges: " + slot,
                record.get("credit_en").asString, credit.en)
            assertEquals("Tamil displayed credit diverges: " + slot,
                record.get("credit_ta").asString, credit.ta)
            assertTrue("Missing author: " + slot, credit.en.contains(evidence.first))
            assertTrue("Missing source licence: " + slot, credit.en.contains(evidence.second))
            assertTrue("Missing source URL: " + slot,
                credit.en.contains(evidence.third))
            assertTrue("Tamil source licence not shown: " + slot,
                credit.ta.contains(if (slot == "sycon/reproduction") "பொதுக் கள உரிமை" else evidence.second))
        }
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
