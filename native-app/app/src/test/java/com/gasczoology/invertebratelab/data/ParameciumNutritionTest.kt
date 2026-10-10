package com.gasczoology.invertebratelab.data
import org.junit.Assert.*
import org.junit.Test

class ParameciumNutritionTest {
    @Test fun ingestionRegionsRemainDistinctAndOrdered() {
        assertEquals(listOf("current","entry","formation","acidification","digestion","uptake"),ParameciumNutrition.stages.map { it.id })
        assertTrue(ParameciumNutrition.structures.map { it.id }.containsAll(listOf("oral-groove","oral-cilia","cytostome","cytopharynx","vacuole")))
    }
    @Test fun textbookIsDetailedBilingualAndNumbered() {
        assertEquals(listOf("5.1","5.2","5.3","5.4","5.5"),ParameciumNutrition.readings.map { it.id })
        for(r in ParameciumNutrition.readings) {
            assertTrue(r.heading.english.startsWith(r.id));assertTrue(r.heading.tamil.startsWith(r.id))
            assertEquals(3,r.paragraphs.size)
            for(p in r.paragraphs) { assertTrue(p.english.length>180);assertTrue(p.tamil.length>180) }
        }
    }
    @Test fun membraneAndMicroscopyLimitsAreExplicit() {
        val all=ParameciumNutrition.readings.flatMap { it.paragraphs }.joinToString { it.english }
        assertTrue(all.contains("lumen"));assertTrue(all.contains("cytosol"));assertTrue(all.contains("not a calibrated"))
        assertEquals("DRAFT_UNVERIFIED",ParameciumNutrition.reviewStatus)
        assertTrue(ParameciumNutrition.sourceNotes.any { it.contains("4373478") })
    }
    @Test fun acidificationPrecedesEnzymesAndUptakeDoesNotReleaseWholePrey() {
        val acid=ParameciumDigestiveMaturation.pose("acidification",1f)
        assertEquals(1f,acid.acidification,0f);assertEquals(0f,acid.enzymeDelivery,0f)
        assertEquals(0f,acid.breakdown,0f);assertEquals(0f,acid.soluteUptake,0f)
        val early=ParameciumDigestiveMaturation.pose("digestion",.2f)
        assertTrue(early.enzymeDelivery>0f);assertEquals(0f,early.breakdown,0f)
        val late=ParameciumDigestiveMaturation.pose("digestion",1f)
        assertEquals(1f,late.breakdown,0f);assertEquals(0f,late.soluteUptake,0f)
        val uptake=ParameciumDigestiveMaturation.pose("uptake",.6f)
        assertEquals(.6f,uptake.soluteUptake,0f)
        assertTrue(ParameciumNutrition.structure("soluble-products").explanation.english.contains("Assimilation"))
        assertTrue(ParameciumNutrition.readings.first { it.id=="5.5" }.paragraphs.last().english.contains("larger residue"))
    }
    @Test fun hitGeometryFitsBothOrientationsAndRejectsEmptyPaper() {
        for((w,h) in listOf(380f to 300f,900f to 300f)) {
            val f=NutritionFigure.fit(w,h)
            assertTrue(f.dx>=0 && f.dy>=0)
            assertTrue(f.x(NutritionFigure.width)<=w+.01f && f.y(NutritionFigure.height)<=h+.01f)
            for(m in NutritionFigure.marks) assertEquals(m.id,NutritionFigure.at(m.x,m.y))
        }
        assertNull(NutritionFigure.at(10f,580f))
    }
    @Test fun malformedProgressNormalizesWithoutChangingSchema() {
        assertEquals(NutritionProgress(phasePermille=1000),NutritionProgress("bad","bad","bad",9999).normalized())
        assertEquals(1,NativeLearningState.CURRENT_SCHEMA)
    }
    @Test fun additiveStateRetainsEarlierPositions() {
        val old=NativeLearningState(nuclearProgress=NuclearLearningProgress(chapterId="conjugation",conjugationView="exchange"),
            waterBalanceProgress=WaterBalanceProgress(stageId="expel",readingId="4.4"))
        val new=old.copy(nutritionProgress=NutritionProgress(stageId="formation",readingId="5.2",phasePermille=470))
        assertEquals(old.nuclearProgress,new.nuclearProgress);assertEquals(old.waterBalanceProgress,new.waterBalanceProgress)
        assertEquals(470,new.nutritionProgress.normalized().phasePermille)
    }
}
