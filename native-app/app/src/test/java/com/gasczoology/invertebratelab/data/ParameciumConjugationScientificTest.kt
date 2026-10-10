package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class ParameciumConjugationScientificTest {
    private val views get()=ParameciumConjugationChapter.chapter.views
    private fun stage(id:String)=views.first{it.id==id}
    @Test fun meiosisSeparatesHomologsBeforeSistersWithoutMultiplyingCells() {
        assertEquals(NuclearEvent.HOMOLOG_SEPARATION,stage("meiosis-i").event)
        assertEquals(NuclearEvent.SISTER_SEPARATION,stage("meiosis-ii").event)
        assertEquals(listOf(1,1,2,4),views.take(4).map{it.germNucleiPerCell})
        assertEquals(listOf(2,2,1,1),views.take(4).map{it.germPloidy})
        assertTrue(ConjugationGenetics.genomes(stage("meiosis-i"),0).all{it.replicated && it.ploidy==1})
        assertTrue(ConjugationGenetics.genomes(stage("meiosis-ii"),0).all{!it.replicated && it.ploidy==1})
        assertEquals(3,stage("selection").degeneratingGermNucleiPerCell)
        assertEquals(1,stage("selection").germNucleiPerCell)
    }
    @Test fun reciprocalTransferRetainsEachStationaryNucleusAndRestoresDiploidyOnlyAtFusion() {
        for(cell in 0..1) {
            val own=if(cell==0)PartnerOrigin.A else PartnerOrigin.B
            val other=if(cell==0)PartnerOrigin.B else PartnerOrigin.A
            val before=ConjugationGenetics.genomes(stage("pronuclei"),cell)
            assertTrue(before.all{it.origin==own && it.ploidy==1})
            val received=ConjugationGenetics.genomes(stage("exchange"),cell)
            assertEquals(own,received.first{it.role==GermlineRole.STATIONARY}.origin)
            assertEquals(other,received.first{it.role==GermlineRole.MIGRATORY}.origin)
            assertTrue(received.all{it.ploidy==1})
            val fused=ConjugationGenetics.genomes(stage("fusion"),cell).single()
            assertEquals(PartnerOrigin.BOTH,fused.origin);assertEquals(2,fused.ploidy)
        }
        assertEquals(NuclearEvent.FERTILIZATION,stage("fusion").event)
    }
    @Test fun citedPairSeparationFollowsFirstSynkaryonMitosisAndThreeDivisionsYieldEightProducts() {
        assertTrue(stage("zygote-division-i").paired);assertFalse(stage("separation").paired)
        assertTrue(views.indexOf(stage("zygote-division-i"))<views.indexOf(stage("separation")))
        assertTrue(views.indexOf(stage("separation"))<views.indexOf(stage("zygote-division-ii")))
        assertEquals(listOf(2,4,8),views.filter{it.event==NuclearEvent.POSTZYGOTIC_MITOSIS}.map{it.germNucleiPerCell})
        assertTrue(views.filter{it.event==NuclearEvent.POSTZYGOTIC_MITOSIS}.all{it.germPloidy==2})
    }
    @Test fun somaticFatesDoNotErasePresumptiveGermlineOrOldMacMaterialInstantly() {
        for(id in listOf("nuclear-fates","new-mac")) {
            val s=stage(id);assertEquals(4,s.macronuclearAnlagenPerCell);assertEquals(4,s.germNucleiPerCell)
            assertEquals(0,s.degeneratingGermNucleiPerCell);assertEquals(ParentalMacState.FRAGMENTS,s.parentalMacState)
        }
        assertEquals(ParentalMacState.SKEIN,stage("zygote-division-iii").parentalMacState)
        assertTrue(views.takeLast(2).all{it.residualGermlinePossible && it.parentalMacState==ParentalMacState.RESIDUAL_FRAGMENTS})
    }
    @Test fun onlySeparateLaterCytokinesesIncreaseCellNumberAndDistributeSomaticAnlagen() {
        assertTrue(views.dropLast(2).all{it.cellCount==2 && it.event!=NuclearEvent.CYTOKINESIS})
        assertEquals(listOf(4,8),views.takeLast(2).map{it.cellCount})
        assertTrue(views.takeLast(2).all{it.event==NuclearEvent.CYTOKINESIS && it.germNucleiPerCell==1 && it.germPloidy==2})
        assertEquals(listOf(8,8,8),listOf("new-mac","later-fission-i","later-fission-ii").map{stage(it).let{s->s.cellCount*s.macronuclearAnlagenPerCell}})
    }
    @Test fun animatedBodiesAgreeWithTouchTargetsAndCellCountsWithoutLeavingOriginalFrame() {
        for(s in views)for(t in listOf(0f,.5f,1f)) {
            val b=NuclearBodyGeometry.conjugation(s,t)
            assertEquals(s.cellCount,b.cells.size)
            assertEquals(s.cellCount*s.germNucleiPerCell,b.marks.count{it.kind=="micronucleus" && it.role!="degeneration"})
            assertEquals(s.cellCount*s.macronuclearAnlagenPerCell,b.marks.count{it.role=="new-mac"})
            b.marks.forEach { n ->
                assertTrue("${s.id} $n",n.x in 20f..590f && n.y in 35f..510f)
                assertEquals(n.kind,NuclearFigureGeometry.nucleusAt(n.x,n.y,s,t))
            }
        }
        val s=stage("exchange")
        val a=NuclearBodyGeometry.conjugation(s,0f).marks.first{it.role=="MIGRATORY"}
        val b=NuclearBodyGeometry.conjugation(s,1f).marks.first{it.role=="MIGRATORY"}
        assertNotEquals(a.y,b.y)
    }
    @Test fun allThreeReadingAndStageAnchorsSurviveNormalizationAndChapterRoundTrip() {
        val p=NuclearLearningProgress().selectReading("1.3").selectView("germline")
            .selectChapter("fission").selectReading("2.3").selectView("constriction")
            .selectChapter("conjugation").selectReading("3.4").selectView("exchange").copy(reducedMotion=true)
        val round=p.selectChapter("dimorphism").selectChapter("fission").selectChapter("conjugation").normalized()
        assertEquals("3.4",round.readingId);assertEquals("exchange",round.viewId());assertTrue(round.reducedMotion)
        assertEquals("1.3",round.dimorphismReading);assertEquals("2.3",round.fissionReading)
        assertEquals("germline",round.dimorphismView);assertEquals("constriction",round.fissionView)
    }
}
