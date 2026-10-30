package com.gasczoology.invertebratelab

import androidx.datastore.preferences.core.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeR18DataStoreCompatibilityTest {
    private val repository get()=NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
    @Test fun acceptedR17RecordWithoutWaterKeysKeepsAllNuclearAndCiliaryAnchors() {
        val old=mutablePreferencesOf(
            intPreferencesKey("schema_version") to 1,stringPreferencesKey("destination") to "PARAMECIUM_LAB",
            intPreferencesKey("unit_number") to 1,stringPreferencesKey("chapter_id") to "u1-paramecium",
            stringPreferencesKey("laboratory_tab") to "nuclear",stringPreferencesKey("textbook_section_id") to "conjugation",
            intPreferencesKey("ciliary_stage_index") to 3,stringPreferencesKey("r17_nuclear_chapter") to "conjugation",
            stringPreferencesKey("r17_conjugation_view") to "exchange",stringPreferencesKey("r17_nuclear_reading") to "3.4",
            stringPreferencesKey("r17_conjugation_reading") to "3.4",stringPreferencesKey("r17_selected_nucleus") to "micronucleus",
            intPreferencesKey("r17_phase_permille") to 450,booleanPreferencesKey("r17_reduced_motion") to true,
        )
        val decoded=repository.decode(old)
        assertEquals(1,decoded.schemaVersion);assertEquals("conjugation",decoded.textbookSectionId);assertEquals(3,decoded.ciliaryStageIndex)
        assertEquals("conjugation",decoded.nuclearProgress.chapterId);assertEquals("exchange",decoded.nuclearProgress.viewId())
        assertEquals("3.4",decoded.nuclearProgress.readingId);assertEquals("micronucleus",decoded.nuclearProgress.selectedNucleus)
        assertEquals(450,decoded.nuclearProgress.phasePermille);assertTrue(decoded.nuclearProgress.reducedMotion)
        assertEquals(WaterBalanceProgress(),decoded.waterBalanceProgress)
    }
    @Test fun malformedWaterKeysNormalizeWithoutResettingOtherLearningState() {
        val record=mutablePreferencesOf(intPreferencesKey("schema_version") to 1,
            stringPreferencesKey("destination") to "PARAMECIUM_LAB",intPreferencesKey("unit_number") to 1,
            stringPreferencesKey("chapter_id") to "u1-paramecium",stringPreferencesKey("laboratory_tab") to "water-balance",
            stringPreferencesKey("r18_water_stage") to "unknown",stringPreferencesKey("r18_water_reading") to "unknown",
            stringPreferencesKey("r18_water_structure") to "unknown",intPreferencesKey("r18_water_phase_permille") to 4000,
            stringPreferencesKey("r17_nuclear_chapter") to "fission",stringPreferencesKey("r17_fission_view") to "constriction")
        val decoded=repository.decode(record)
        assertEquals("osmosis",decoded.waterBalanceProgress.stageId);assertEquals("4.1",decoded.waterBalanceProgress.readingId)
        assertEquals("reservoir",decoded.waterBalanceProgress.selectedStructure);assertEquals(1000,decoded.waterBalanceProgress.phasePermille)
        assertEquals("fission",decoded.nuclearProgress.chapterId);assertEquals("constriction",decoded.nuclearProgress.viewId())
        assertEquals("water-balance",decoded.laboratoryTab)
    }
}
