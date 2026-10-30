package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class ParameciumLearningEngineTest {
    @Test fun allOrgansContainFullBilingualNarration() {
        assertEquals(9, ParameciumLearningEngine.organs.size)
        assertTrue(ParameciumLearningEngine.organs.all {
            it.narration.english.length > 65 && it.narration.tamil.length > 40 &&
                it.reference.startsWith("https://")
        })
        val ids = ParameciumAnatomyDraft.plates.flatMap { it.features }.map { it.id }.toSet()
        assertTrue(ParameciumLearningEngine.organs.map { it.id }.containsAll(
            listOf("pellicle","somatic-cilia","oral-groove","cytoproct","trichocysts")))
        assertTrue(ids.contains("anterior-contractile-vacuole"))
    }
    @Test fun everyBiologicalCycleIsOrderedAndCanReplay() {
        assertEquals(ParameciumProcess.entries.size, ParameciumLearningEngine.simulations.size)
        for (sim in ParameciumLearningEngine.simulations) {
            assertTrue(sim.stages.size >= 4)
            assertEquals(0f,sim.stages.first().illustrationProgress)
            assertEquals(1f,sim.stages.last().illustrationProgress)
            assertEquals(0,sim.advance(sim.stages.lastIndex))
            assertEquals(0,sim.previous(0))
            for (i in 0 until sim.stages.lastIndex) assertEquals(i+1,sim.advance(i))
        }
    }
    @Test fun reproductionAndOsmoregulationClaimsNotConfused() {
        val conjugation = ParameciumLearningEngine.simulation(ParameciumProcess.CONJUGATION)
        val fission = ParameciumLearningEngine.simulation(ParameciumProcess.BINARY_FISSION)
        assertEquals("exchange", conjugation.stages[2].id)
        assertEquals("daughters", fission.stages.last().id)
        assertTrue(conjugation.stages.last().explanation.english.contains("without immediate numerical multiplication"))
        assertTrue(fission.stages.last().explanation.english.contains("increases cell number"))
        assertEquals(listOf("osmosis","collect","fill","expel"),
            ParameciumLearningEngine.simulation(ParameciumProcess.OSMOREGULATION).stages.map { it.id })
    }
    @Test fun conjugationRespectsNamedSpeciesNuclearChronologyAndCellCounts() {
        val stages = ParameciumLearningEngine.simulation(ParameciumProcess.CONJUGATION).stages
        assertEquals(
            listOf("pair", "meiosis", "exchange", "first-division", "separate",
                "differentiation", "later-fission"),
            stages.map { it.id },
        )
        assertTrue(stages.zipWithNext().all { (a, b) -> a.illustrationProgress < b.illustrationProgress })
        assertTrue(stages[3].explanation.english.contains("while the partners are still associated"))
        assertTrue(stages[4].explanation.english.contains("after the first synkaryon division"))
        assertTrue(stages[5].explanation.english.contains("eight diploid nuclear products per exconjugant"))
        assertTrue(stages[5].explanation.english.contains("four posterior"))
        assertTrue(stages[6].explanation.english.contains("eight postzygotic nuclei per cell"))
        assertTrue(stages[3].explanation.tamil.contains("இரண்டு செல்களே"))
        assertTrue(stages[4].explanation.tamil.contains("முதல் சின்காரியான் பிரிவுக்குப் பின்"))
        assertTrue(stages[5].explanation.tamil.contains("எட்டு இருமய உட்கரு"))
    }

    @Test fun unapprovedAnatomyIsNeverPromotedBySimulationData() {
        assertTrue(ParameciumAnatomyDraft.plates.all {
            it.status == AcademicWorkStatus.DRAFT_UNVERIFIED && it.review == null })
        assertEquals(0,ParameciumN23EReviewerHandoff.biologicalApprovals)
        assertEquals(0,ParameciumN23EReviewerHandoff.tamilApprovals)
    }
}
