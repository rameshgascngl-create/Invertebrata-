package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.UnitIStageAtlas
import org.junit.Assert.*
import org.junit.Test

class UnitIStageAtlasTest {
    @Test fun separateStagesUseDistinctScientificMorphotypesAndSources() {
        val plates = UnitIStageAtlas.plates
        assertEquals(3, plates.size)
        assertEquals(3, plates.map { it.id }.distinct().size)
        assertEquals(3, plates.map { it.morphotype }.distinct().size)
        assertTrue(plates.all {
            it.source.startsWith("https://www.cdc.gov/dpdx/") &&
                it.taxonEn.isNotBlank() && it.taxonTa.isNotBlank() &&
                it.contextEn.isNotBlank() && it.contextTa.isNotBlank()
        })
        assertNull(UnitIStageAtlas.get("leishmania-donovani-confirmed-species"))
    }

    @Test fun trypanosomaBloodstreamKinetoplastPosteriorFlagellumAnterior() {
        val plate = UnitIStageAtlas.get("brucei-blood")!!
        val nucleus = plate.structures.first { it.id == "nucleus" }
        val kinetoplast = plate.structures.first { it.id == "kinetoplast" }
        assertTrue("Posterior kinetoplast should lie behind central nucleus",
            kinetoplast.x < nucleus.x)
        assertTrue(plate.contextEn.contains("extracellular"))
        assertTrue(plate.structures.any { it.id == "membrane" })
        assertTrue(plate.structures.any { it.id == "flagellum" })
        assertFalse(plate.structures.any { it.id == "host" })
    }

    @Test fun amastigoteHasHostCellNotFreeFlagellum() {
        val plate = UnitIStageAtlas.get("leishmania-amastigote")!!
        assertTrue(plate.contextEn.contains("NO external long flagellum"))
        assertTrue(plate.structures.any { it.id == "host-nucleus" })
        assertTrue(plate.structures.any { it.id == "parasite" })
        assertTrue(plate.structures.any { it.id == "kinetoplast" })
        assertFalse(plate.structures.any { it.id == "flagellum" })
    }

    @Test fun promastigoteHasAnteriorKinetoplastButNoUndulatingMembrane() {
        val plate = UnitIStageAtlas.get("leishmania-promastigote")!!
        val nucleus = plate.structures.first { it.id == "nucleus" }
        val kinetoplast = plate.structures.first { it.id == "kinetoplast" }
        assertTrue("Promastigote kinetoplast must be anterior to nucleus",
            kinetoplast.x > nucleus.x)
        assertTrue(plate.contextEn.contains("No undulating membrane"))
        assertTrue(plate.structures.any { it.id == "flagellum" })
        assertFalse(plate.structures.any { it.id == "membrane" })
        assertFalse(plate.structures.any { it.id == "host-nucleus" })
    }

    @Test fun bilingualStructureCopyAndConceptualPointRangeAreComplete() {
        for (plate in UnitIStageAtlas.plates) {
            assertTrue(plate.structures.isNotEmpty())
            assertEquals(plate.structures.size, plate.structures.map { it.id }.distinct().size)
            for (structure in plate.structures) {
                assertTrue(structure.nameEn.isNotBlank())
                assertTrue(structure.nameTa.isNotBlank())
                assertTrue(structure.explanationEn.isNotBlank())
                assertTrue(structure.explanationTa.isNotBlank())
                assertTrue(structure.x in 0f..1000f)
                assertTrue(structure.y in 0f..560f)
            }
        }
    }
}
