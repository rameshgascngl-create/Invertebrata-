package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Protects scientific distinctions in the native textbook manuscript.
 * Passing these assertions is not equivalent to independent academic acceptance.
 */
class SupplementaryScientificContentRegressionTest {
    private fun english(sections: List<NereisTextbookSection>, id: String): String =
        sections.single { it.id == id }.paragraphs.joinToString(" ") { it.english }

    @Test fun protistComparisonsDoNotConfuseColonialAlgaeWithAnimals() {
        val text = english(FiveUnitRestorationLessons.byId.getValue("u1-protozoa").sections,
            "comparative-amoeba-euglena-volvox")
        listOf("pseudopodia", "flagellum", "colonial green alga", "daughter colonies")
            .forEach { assertTrue(it, text.contains(it, ignoreCase = true)) }
        assertTrue(text.contains("not make it a direct ancestor of animals"))
    }

    @Test fun echinodermAndCephalopodComparisonsRemainAnatomicallySpecific() {
        val echinoderm = english(FiveUnitRestorationLessons.byId.getValue("u5-echinodermata").sections,
            "echinoid-holothuroid-comparison")
        listOf("Echinoidea", "Holothuroidea", "Aristotle's lantern", "water-vascular")
            .forEach { assertTrue(it, echinoderm.contains(it, ignoreCase = true)) }
        val sepia = english(FiveUnitRestorationLessons.byId.getValue("u5-cephalopods").sections,
            "sepia-specialisations")
        listOf("eight arms", "two retractile", "cuttlebone", "funnel", "chromatophores")
            .forEach { assertTrue(it, sepia.contains(it, ignoreCase = true)) }
    }

    @Test fun hydraHasBilingualSourcedFunctionalAnatomy() {
        val section = UnitIILessons.forChapter("u2-cnidaria")!!.sections.single {
            it.id == "hydra-functional-biology"
        }
        assertEquals(2, section.paragraphs.size)
        assertTrue(section.paragraphs.all { it.english.length > 150 && it.tamil.length > 100 })
        assertTrue(section.paragraphs.joinToString(" ") { it.english }.contains("cnidocytes"))
        assertTrue(section.scientificSources.any { it.startsWith("https://") })
    }

    @Test fun taeniaDistinguishesIngestionOfEggsFromCysticerci() {
        val section = UnitIIILessons.forChapter("u3-platy")!!.sections.single {
            it.id == "taenia-disease-distinction"
        }
        val en = section.paragraphs.joinToString(" ") { it.english }
        assertTrue(en.contains("Eating cysticerci causes adult intestinal infection"))
        assertTrue(en.contains("swallowing eggs causes larval tissue infection"))
        assertTrue(en.contains("neurocysticercosis"))
        assertTrue(section.paragraphs.all { it.tamil.length > 100 })
    }

    @Test fun taeniaPreventionDoesNotConfusePorkCookingWithEggTransmission() {
        val section = UnitIIILessons.forChapter("u3-platy")!!.sections.single {
            it.id == "taenia-prevention-diagnosis"
        }
        val en = section.paragraphs.joinToString(" ") { it.english }
        assertTrue(en.contains("Pork cooking alone cannot prevent cysticercosis"))
        assertTrue(en.contains("eggs alone do not reliably distinguish"))
        assertTrue(en.contains("brain imaging"))
        assertTrue(section.paragraphs.all { it.tamil.length > 150 })
        assertTrue(section.scientificSources.any { it.contains("cdc.gov") })
    }

    @Test fun cockroachRespirationAndMetamorphosisRemainDistinct() {
        val section = UnitIVSupplementaryLessons.byId.getValue("u4-arthropoda")
            .sections.single { it.id == "periplaneta-organ-systems" }
        val en = section.paragraphs.joinToString(" ") { it.english }
        listOf("tracheoles", "haemolymph is not the main respiratory carrier",
            "Malpighian tubules", "without a pupal stage").forEach {
            assertTrue(it, en.contains(it, ignoreCase = true))
        }
        assertFalse(section.paragraphs.any { it.tamil.isBlank() })
    }
}
