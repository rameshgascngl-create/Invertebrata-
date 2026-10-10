package com.gasczoology.invertebratelab

import androidx.datastore.preferences.core.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeR19DataStoreCompatibilityTest {
    private val repository get()=NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
    private fun acceptedRecord()=mutablePreferencesOf(
        intPreferencesKey("schema_version") to 1,stringPreferencesKey("destination") to "PARAMECIUM_LAB",
        intPreferencesKey("unit_number") to 1,stringPreferencesKey("laboratory_tab") to "water-balance",
        stringPreferencesKey("textbook_section_id") to "feeding-and-digestion",intPreferencesKey("ciliary_stage_index") to 3,
        stringPreferencesKey("r17_nuclear_chapter") to "conjugation",stringPreferencesKey("r17_conjugation_view") to "exchange",
        stringPreferencesKey("r17_nuclear_reading") to "3.4",stringPreferencesKey("r17_conjugation_reading") to "3.4",
        stringPreferencesKey("r17_selected_nucleus") to "micronucleus",intPreferencesKey("r17_phase_permille") to 450,
        stringPreferencesKey("r18_water_stage") to "expel",stringPreferencesKey("r18_water_reading") to "4.4",
        stringPreferencesKey("r18_water_structure") to "decorated",intPreferencesKey("r18_water_phase_permille") to 670,
        booleanPreferencesKey("r18_water_reduced_motion") to true)
    @Test fun legacyR18PreferencesWithoutNutritionKeysRetainEveryEarlierAnchor() {
        val state=repository.decode(acceptedRecord())
        assertEquals(NutritionProgress(),state.nutritionProgress)
        assertEquals(3,state.ciliaryStageIndex);assertEquals("feeding-and-digestion",state.textbookSectionId)
        assertEquals("exchange",state.nuclearProgress.viewId());assertEquals("3.4",state.nuclearProgress.readingId)
        assertEquals(450,state.nuclearProgress.phasePermille);assertEquals("micronucleus",state.nuclearProgress.selectedNucleus)
        assertEquals(WaterBalanceProgress("expel","4.4","decorated",670,true),state.waterBalanceProgress)
        val updated=state.copy(laboratoryTab="nutrition",nutritionProgress=NutritionProgress("recycling","5.7","recovered-membrane",620,true))
        runBlocking {repository.save(updated);assertEquals(updated,repository.learningState.first())}
    }
    @Test fun malformedNewKeysNormalizeWithoutResettingAcceptedFields() {
        val prefs=acceptedRecord().apply {
            this[stringPreferencesKey("r19_nutrition_stage")]="invalid"
            this[stringPreferencesKey("r19_nutrition_reading")]="invalid"
            this[stringPreferencesKey("r19_nutrition_structure")]="invalid"
            this[intPreferencesKey("r19_nutrition_phase_permille")]=9000
        }
        val before=repository.decode(acceptedRecord());val after=repository.decode(prefs)
        assertEquals(NutritionProgress(phasePermille=1000),after.nutritionProgress)
        assertEquals(before.waterBalanceProgress,after.waterBalanceProgress);assertEquals(before.nuclearProgress,after.nuclearProgress)
        assertEquals(before.laboratoryTab,after.laboratoryTab);assertEquals(1,after.schemaVersion)
    }
}
