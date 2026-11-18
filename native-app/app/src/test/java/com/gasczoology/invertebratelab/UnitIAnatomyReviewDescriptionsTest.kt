package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIAnatomyReviewDescriptions
import org.junit.Assert.*
import org.junit.Test

class UnitIAnatomyReviewDescriptionsTest {
    @Test fun bloodstreamAndAmastigoteHaveDifferentCanonicalMorphotypes() {
        val tryp = requireNotNull(UnitIAnatomyReviewDescriptions.forOrganism("trypanosoma"))
        val leish = requireNotNull(UnitIAnatomyReviewDescriptions.forOrganism("leishmania"))
        assertEquals("mammalian_bloodstream_trypomastigote", tryp.morphotype)
        assertEquals("intracellular_mammalian_amastigote", leish.morphotype)
        assertEquals(5, tryp.structures.size)
        assertEquals(4, leish.structures.size)
        assertEquals("https://www.cdc.gov/dpdx/leishmaniasis/", leish.source)
        assertEquals("https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html", tryp.source)
        assertNotNull(tryp.structures.firstOrNull { it.id == "flagellum" })
        assertNull(leish.structures.firstOrNull { it.id == "flagellum" })
    }

    @Test fun noIncorrectPromastigoteOrCrossSpeciesHotspotCanBeExportedAsCompatible() {
        assertFalse(UnitIAnatomyReviewDescriptions.isCompatible(
            "leishmania", "sand_fly_promastigote", "flagellum"))
        assertFalse(UnitIAnatomyReviewDescriptions.isCompatible(
            "leishmania", "intracellular_mammalian_amastigote", "flagellum"))
        assertFalse(UnitIAnatomyReviewDescriptions.isCompatible(
            "trypanosoma", "intracellular_mammalian_amastigote", "nucleus"))
        assertTrue(UnitIAnatomyReviewDescriptions.isCompatible(
            "leishmania", "intracellular_mammalian_amastigote", "macrophage"))
        assertTrue(UnitIAnatomyReviewDescriptions.isCompatible(
            "trypanosoma", "mammalian_bloodstream_trypomastigote", "kinetoplast"))
        assertNull(UnitIAnatomyReviewDescriptions.forOrganism("leishmania-promastigote"))
    }

    @Test fun allNineNarrationsAreBilingualAndExplicitlyUnapproved() {
        for (id in listOf("trypanosoma", "leishmania")) {
            val plate = requireNotNull(UnitIAnatomyReviewDescriptions.forOrganism(id))
            for (organ in plate.structures) {
                assertTrue(organ.en.isNotBlank() && organ.ta.isNotBlank())
                assertTrue(organ.explanationEn.length > 25)
                assertTrue(organ.explanationTa.length > 20)
                assertTrue(organ.narration.value(AppLanguage.ENGLISH).contains(organ.en))
                assertTrue(organ.narration.value(AppLanguage.TAMIL).contains(organ.ta))
            }
        }
    }
}
