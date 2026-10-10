package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.*
import org.junit.Assert.*
import org.junit.Test

class ParameciumWaterBalanceTest {
    @Test fun completeBilingualTextbookHasNumberedSubsections() {
        assertEquals(5,ParameciumWaterBalance.readings.size)
        assertEquals(listOf("4.1","4.2","4.3","4.4","4.5"),ParameciumWaterBalance.readings.map { it.id })
        for(r in ParameciumWaterBalance.readings) { assertTrue(r.heading.english.startsWith(r.id));assertTrue(r.heading.tamil.startsWith(r.id))
            assertEquals(2,r.paragraphs.size);for(p in r.paragraphs) { assertTrue(p.english.length>250);assertTrue(p.tamil.length>250) } }
    }
    @Test fun newStageNamesPreserveExistingAcceptedCvcSequence() {
        assertEquals(ParameciumCvcMechanism.phases.map { it.stageId },ParameciumWaterBalance.stages.map { it.id })
        for(s in ParameciumWaterBalance.stages) { assertTrue(s.explanation.english.length>180);assertTrue(s.explanation.tamil.length>150) }
    }
    @Test fun collectingFillingAndDischargeChangeInCorrectDirections() {
        for(stage in listOf("osmosis","collect","fill")) assertTrue(ParameciumWaterBalance.lumen(stage,1f)>ParameciumWaterBalance.lumen(stage,0f))
        assertTrue(ParameciumWaterBalance.lumen("expel",1f)<ParameciumWaterBalance.lumen("expel",0f))
        assertEquals(ParameciumWaterBalance.lumen("fill",1f),ParameciumWaterBalance.lumen("expel",0f),.001f)
        for(s in ParameciumWaterBalance.stages) for(i in 0..100) assertTrue(ParameciumWaterBalance.lumen(s.id,i/100f) in 0f..1f)
    }
    @Test fun sourceScopeDoesNotSilentlyTransferComparativeMeasurements() {
        val content=ParameciumWaterBalance.readings.flatMap { it.paragraphs }.joinToString(" ") { it.english }
        assertTrue(content.contains("P. caudatum"));assertTrue(content.contains("P. multimicronucleatum"))
        assertTrue(content.contains("not a concentration measured"));assertTrue(content.contains("not reconstructed measurements"))
        assertTrue(content.contains("not verified"));assertTrue(content.contains("not a laboratory measurement"))
        assertEquals("DRAFT_UNVERIFIED",ParameciumWaterBalance.reviewStatus);assertEquals(5,ParameciumWaterBalance.sourceNotes.size)
    }
    @Test fun noDecoratedCoatAroundAmpullaOrInvisibleWetMountPumpsClaimed() {
        assertTrue(ParameciumWaterBalance.structure("decorated").explanation.english.contains("not a coat around the ampulla"))
        assertTrue(ParameciumWaterBalance.structure("smooth").explanation.english.contains("cannot be resolved"))
        assertTrue(ParameciumWaterBalance.structure("reservoir").explanation.english.contains("not a digestive food vacuole"))
        assertTrue(ParameciumWaterBalance.readings[0].paragraphs[1].english.contains("Do not infer simultaneous"))
    }
    @Test fun everyStructureHasUniqueSharedGeometryAndCompleteExplanation() {
        assertEquals(6,ParameciumWaterBalance.structures.size)
        assertEquals(ParameciumWaterBalance.structures.map{it.id}.toSet(),WaterBalanceFigure.marks.map{it.id}.toSet())
        for(m in WaterBalanceFigure.marks) { assertEquals(m.id,WaterBalanceFigure.at(m.x,m.y))
            val s=ParameciumWaterBalance.structure(m.id);assertTrue(s.explanation.english.length>200);assertTrue(s.explanation.tamil.length>200) }
        assertNull(WaterBalanceFigure.at(5f,535f))
    }
    @Test fun uniformFigureFitDoesNotStretchPortraitOrLandscape() {
        for((w,h) in listOf(360f to 240f,840f to 240f)) { val f=WaterBalanceFigure.fit(w,h)
            assertTrue(f.x(0f)>=0);assertTrue(f.y(0f)>=0);assertTrue(f.x(1000f)<=w+.1f);assertTrue(f.y(540f)<=h+.1f)
            assertEquals(f.x(100f)-f.x(0f),f.y(100f)-f.y(0f),.001f) }
    }
    @Test fun corruptOrFutureProgressNormalizesWithoutTouchingOldPositions() {
        assertEquals(WaterBalanceProgress(),WaterBalanceProgress("bad","bad","bad",-50).normalized())
        assertEquals(1000,WaterBalanceProgress(phasePermille=9999).normalized().phasePermille)
        val old=NuclearLearningProgress().selectChapter("conjugation").selectView("exchange")
        val state=NativeLearningState(nuclearProgress=old,ciliaryStageIndex=3,textbookSectionId="conjugation")
        val new=state.copy(waterBalanceProgress=WaterBalanceProgress("expel","4.4","decorated",450,true))
        assertEquals(old,new.nuclearProgress);assertEquals(3,new.ciliaryStageIndex);assertEquals("conjugation",new.textbookSectionId)
        assertEquals(1,new.schemaVersion)
    }
}
