package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class ParameciumR17ScientificTest {
    @Test fun namedSpeciesUsesOneVegetativeGermNucleusWithoutTreatingInsetsAsCells() {
        assertEquals("Paramecium caudatum",ParameciumNuclearBiology.species)
        assertEquals("DRAFT_UNVERIFIED",ParameciumNuclearBiology.status)
        ParameciumNuclearBiology.dimorphism.views.forEach {
            assertEquals(1,it.cellCount);assertEquals(1,it.germNucleiPerCell);assertEquals(2,it.germPloidy)
        }
    }
    @Test fun textbookSubsectionsAreDevelopedBilingualPassagesWithResolvedSources() {
        ParameciumNuclearBiology.chapters.forEach { chapter ->
            assertTrue(chapter.readings.size>=4)
            assertEquals(chapter.readings.size,chapter.readings.map{it.id}.distinct().size)
            chapter.readings.forEach { r ->
                assertTrue(r.heading.english.startsWith(r.id));assertTrue(r.heading.tamil.startsWith(r.id))
                r.paragraphs.forEach { assertTrue(it.english.length>=250);assertTrue(it.tamil.length>=250) }
            }
            assertTrue(chapter.sourceIds.isNotEmpty());chapter.sourceIds.forEach { assertTrue(ParameciumNuclearBiology.sources.containsKey(it)) }
            assertEquals(chapter.views.size,chapter.views.map{it.id}.distinct().size)
        }
    }
    @Test fun invalidNewProgressIsSanitizedWithoutLosingValidReadingAndOlderFields() {
        val p=NuclearLearningProgress(dimorphismView="removed", selectedNucleus="female",phasePermille=5000,readingId="1.3").normalized()
        assertEquals("whole-cell",p.dimorphismView);assertEquals("macronucleus",p.selectedNucleus)
        assertEquals("1.3",p.readingId);assertEquals(1000,p.phasePermille)
        val old=NativeLearningState(ciliaryStageIndex=3,questionId="prior",answerRevealed=true)
        assertEquals(3,old.copy(nuclearProgress=p).ciliaryStageIndex)
        assertEquals("prior",old.copy(nuclearProgress=p).questionId)
        assertTrue(old.copy(nuclearProgress=p).answerRevealed)
    }
    @Test fun vegetativeFissionConservesDiploidGermlineAndOnlyCytokinesisAddsCells() {
        val views=ParameciumFissionChapter.chapter.views
        assertEquals(listOf(1,1,1,1,1,2),views.map{it.cellCount})
        assertEquals(listOf(1,1,2,2,2,1),views.map{it.germNucleiPerCell})
        assertTrue(views.all{it.germPloidy==2 && it.macronuclearAnlagenPerCell==0 && !it.paired})
        assertEquals(NuclearEvent.REPLICATION,views[1].event)
        assertEquals(NuclearEvent.MITOSIS,views[2].event)
        assertTrue(views.none{it.event==NuclearEvent.FERTILIZATION || it.event==NuclearEvent.RECIPROCAL_EXCHANGE})
    }
    @Test fun newChapterNavigationKeepsIndependentSubsectionAndStage() {
        val p=NuclearLearningProgress().selectReading("1.3").selectView("germline")
            .selectChapter("fission").selectReading("2.3").selectView("constriction")
        val d=p.selectChapter("dimorphism")
        assertEquals("1.3",d.readingId);assertEquals("germline",d.viewId())
        val f=d.selectChapter("fission")
        assertEquals("2.3",f.readingId);assertEquals("constriction",f.viewId())
        assertEquals(f,f.normalized())
    }
    @Test fun originalFissionBodyAndTouchHitRegionsAgreeDuringRealPhaseChanges() {
        for(stage in ParameciumFissionChapter.chapter.views) for(phase in listOf(0f,.5f,1f)) {
            val body=NuclearBodyGeometry.fission(stage,phase)
            assertEquals(stage.cellCount,body.cells.size)
            body.marks.forEach { assertEquals(it.kind,NuclearFigureGeometry.nucleusAt(it.x,it.y,stage,phase)) }
        }
        val stage=ParameciumFissionChapter.chapter.views.first{it.id=="constriction"}
        assertTrue(NuclearBodyGeometry.fission(stage,1f).cells.single().furrow>NuclearBodyGeometry.fission(stage,0f).cells.single().furrow)
    }
    @Test fun anatomyCoordinatesAndInverseFitAgreeInBothAspectRatios() {
        for((w,h) in listOf(320f to 240f,780f to 240f)) {
            val f=NuclearFigureGeometry.fit(w,h)
            assertEquals(780f,f.logicalX(f.x(780f)),.001f)
            assertEquals(380f,f.logicalY(f.y(380f)),.001f)
            assertEquals("macronucleus",NuclearFigureGeometry.nucleusAt(f.logicalX(f.x(780f)),f.logicalY(f.y(155f))))
            assertEquals("micronucleus",NuclearFigureGeometry.nucleusAt(f.logicalX(f.x(780f)),f.logicalY(f.y(380f))))
            assertNull(NuclearFigureGeometry.nucleusAt(10f,10f))
            assertTrue(f.top>=0 && f.left>=0)
        }
    }
}
