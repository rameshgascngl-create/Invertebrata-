package com.gasczoology.invertebratelab

import androidx.datastore.preferences.core.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeR161DataStoreCompatibilityTest {
    @Test fun originalSchemaOnePreferencesWithoutNewKeysKeepAssessmentPosition() {
        val oldRecord = mutablePreferencesOf(
            intPreferencesKey("schema_version") to 1,
            stringPreferencesKey("destination") to "PRACTICE",
            intPreferencesKey("unit_number") to 1,
            stringPreferencesKey("chapter_id") to "u1-paramecium",
            stringPreferencesKey("question_id") to "u1-paramecium-A5-1",
            booleanPreferencesKey("answer_revealed") to true,
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val decoded = NativeLearningRepository(context).decode(oldRecord)
        assertEquals(StudyDestination.PRACTICE, decoded.destination)
        assertEquals("u1-paramecium-A5-1", decoded.questionId)
        assertTrue(decoded.answerRevealed)
        assertEquals(0, decoded.ciliaryStageIndex)
        assertEquals("identity-and-habitat", decoded.textbookSectionId)
        assertEquals("study", decoded.laboratoryTab)
        val units = ValidatedA5AssetRepository(context).load()
        assertEquals(decoded, decoded.validatedAgainst(units))
    }
}
