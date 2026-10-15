package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.FlagellateLifeCycle
import com.gasczoology.invertebratelab.data.FlagellateLifePhase
import com.gasczoology.invertebratelab.data.UnitIFlagellateLifeCycles
import org.junit.Assert.*
import org.junit.Test

class UnitIFlagellateLifeCyclesTest {
    @Test fun distinctCycleStagesAndCompleteBilingualCopy() {
        for (cycle in listOf(
            UnitIFlagellateLifeCycles.trypanosoma,
            UnitIFlagellateLifeCycles.leishmania,
        )) {
            assertEquals(6, cycle.phases.size)
            assertEquals(6, cycle.phases.map { it.id }.toSet().size)
            assertTrue(cycle.source.startsWith("https://www.cdc.gov/dpdx/"))
            assertTrue(cycle.phases.all {
                it.titleEn.isNotBlank() && it.titleTa.isNotBlank() &&
                    it.placeEn.isNotBlank() && it.placeTa.isNotBlank() &&
                    it.explanationEn.isNotBlank() && it.explanationTa.isNotBlank()
            })
        }
    }

    @Test fun AfricanCycleUsesExtracellularBloodstreamAndGlossina() {
        val cycle = UnitIFlagellateLifeCycles.trypanosoma
        assertEquals(
            listOf("metacyclic", "blood", "binary-fission", "ingestion",
                "procyclic", "epimastigote"),
            cycle.phases.map { it.id },
        )
        assertTrue(cycle.phases[2].explanationEn.contains("extracellularly"))
        assertTrue(cycle.phases[4].placeEn.contains("midgut"))
        assertTrue(cycle.phases[5].placeEn.contains("salivary"))
        assertFalse(cycle.phases.joinToString { it.explanationEn }.contains("intracellular amastigote"))
    }

    @Test fun SandFlyCycleDistinguishesVectorPromastigoteFromHostAmastigote() {
        val cycle = UnitIFlagellateLifeCycles.leishmania
        assertEquals(
            listOf("inoculation", "phagocytosis", "amastigotes",
                "sandfly-meal", "gut", "proboscis"),
            cycle.phases.map { it.id },
        )
        assertTrue(cycle.phases[2].explanationEn.contains("amastigotes"))
        assertTrue(cycle.phases[4].explanationEn.contains("promastigotes"))
        assertTrue(cycle.phases[4].explanationEn.contains("Viannia"))
    }

    @Test fun InvalidLifeCycleFailsClosed() {
        try {
            FlagellateLifeCycle(
                "invalid", "a", "b", "source",
                List(4) { FlagellateLifePhase("same", "en", "ta", "host", "ஓம்புயிரி",
                    "biology", "உயிரியல்") },
            )
            fail("duplicate stages accepted")
        } catch (_: IllegalArgumentException) {
            // Expected; ordering and identifiers are part of the academic contract.
        }
        assertNull(UnitIFlagellateLifeCycles.forOrganism("unreviewed-organism"))
    }
}
