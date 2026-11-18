package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class ParameciumCvcMechanismTest {
    @Test fun phaseOrderMatchesExistingScientificLessonExactly() {
        val stageIds = ParameciumLearningEngine
            .simulation(ParameciumProcess.OSMOREGULATION).stages.map { it.id }
        assertEquals(listOf("osmosis", "collect", "fill", "expel"), stageIds)
        assertEquals(stageIds, ParameciumCvcMechanism.phases.map { it.stageId })
        stageIds.forEach { assertEquals(it, ParameciumCvcMechanism.phase(it).stageId) }
    }

    @Test fun lumenFillsBeforeDischargeAndOnlyTheLastStageOpensThePore() {
        val stages = ParameciumCvcMechanism.phases
        assertTrue(stages.all { it.lumenFraction >= 0f && it.lumenFraction <= 1f })
        assertTrue(stages[0].lumenFraction < stages[1].lumenFraction)
        assertTrue(stages[1].lumenFraction < stages[2].lumenFraction)
        assertTrue(stages[3].lumenFraction < stages[2].lumenFraction)
        assertTrue(stages[1].collectingActive && stages[2].collectingActive)
        assertFalse(stages[0].poreOpen)
        assertFalse(stages[1].poreOpen)
        assertFalse(stages[2].poreOpen)
        assertTrue(stages[3].poreOpen)
    }

    @Test fun foodVacuoleRemainsCategoricallySeparateFromCvcAndCytoproct() {
        val landmarks = ParameciumVacuolePlate.landmarks
        assertEquals(2, landmarks.count { it.organId == "contractile-vacuole" })
        assertEquals(1, landmarks.count { it.organId == "food-vacuole" })
        assertFalse(landmarks.any { it.organId == "cytoproct" })
        assertEquals(4, ParameciumCvcMechanism.phases.size)
        assertEquals(0, ParameciumN23EReviewerHandoff.biologicalApprovals)
        assertEquals(0, ParameciumN23EReviewerHandoff.tamilApprovals)
    }

    @Test(expected = NoSuchElementException::class)
    fun rejectUnrecordedMechanismPhaseInsteadOfInventingKinetics() {
        ParameciumCvcMechanism.phase("invented")
    }
}
