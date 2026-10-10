package com.gasczoology.invertebratelab.data

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private val Context.nativeLearningStore by preferencesDataStore(
    name = "native_learning_state",
    corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
)

/**
 * A single atomic DataStore edit holds all related fields. Never store progress in
 * rememberSaveable alone. v1 is deliberately strict: unknown schema versions
 * cannot be misinterpreted as current academic progress.
 */
class NativeLearningRepository(private val context: Context) {
    private object Keys {
        val schema = intPreferencesKey("schema_version")
        val destination = stringPreferencesKey("destination")
        val unit = intPreferencesKey("unit_number")
        val chapter = stringPreferencesKey("chapter_id")
        val question = stringPreferencesKey("question_id")
        val revealed = booleanPreferencesKey("answer_revealed")
        val textbookSection = stringPreferencesKey("textbook_section_id")
        val ciliaryStage = intPreferencesKey("ciliary_stage_index")
        val laboratoryTab = stringPreferencesKey("laboratory_tab")
    }

    val learningState: Flow<NativeLearningState> = context.nativeLearningStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map(::decode)
        .distinctUntilChanged()

    suspend fun save(state: NativeLearningState) {
        require(state.schemaVersion == NativeLearningState.CURRENT_SCHEMA)
        require(state.unitNumber in 1..5)
        context.nativeLearningStore.edit { preferences ->
            preferences[Keys.schema] = state.schemaVersion
            preferences[Keys.destination] = state.destination.name
            preferences[Keys.unit] = state.unitNumber
            preferences[Keys.chapter] = state.chapterId
            preferences[Keys.question] = state.questionId
            preferences[Keys.revealed] = state.answerRevealed
            preferences[Keys.textbookSection] = state.textbookSectionId
            preferences[Keys.ciliaryStage] = state.ciliaryStageIndex
            preferences[Keys.laboratoryTab] = state.laboratoryTab
        }
    }

    internal fun decode(preferences: Preferences): NativeLearningState {
        if (preferences[Keys.schema] != NativeLearningState.CURRENT_SCHEMA) {
            return NativeLearningState()
        }
        val destination = StudyDestination.entries.firstOrNull {
            it.name == preferences[Keys.destination]
        } ?: return NativeLearningState()
        val unit = preferences[Keys.unit] ?: return NativeLearningState()
        if (unit !in 1..5) return NativeLearningState()
        return NativeLearningState(
            destination = destination,
            unitNumber = unit,
            chapterId = preferences[Keys.chapter].orEmpty(),
            questionId = preferences[Keys.question].orEmpty(),
            answerRevealed = preferences[Keys.revealed] ?: false,
            textbookSectionId = preferences[Keys.textbookSection] ?: "identity-and-habitat",
            ciliaryStageIndex = preferences[Keys.ciliaryStage] ?: 0,
            laboratoryTab = preferences[Keys.laboratoryTab] ?: "study",
        )
    }
}
