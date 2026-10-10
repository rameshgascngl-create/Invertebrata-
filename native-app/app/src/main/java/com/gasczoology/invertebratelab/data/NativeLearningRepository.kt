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
        val nuclearChapter = stringPreferencesKey("r17_nuclear_chapter")
        val dimorphismView = stringPreferencesKey("r17_dimorphism_view")
        val fissionView = stringPreferencesKey("r17_fission_view")
        val conjugationView = stringPreferencesKey("r17_conjugation_view")
        val selectedNucleus = stringPreferencesKey("r17_selected_nucleus")
        val nuclearReading = stringPreferencesKey("r17_nuclear_reading")
        val nuclearPhase = intPreferencesKey("r17_phase_permille")
        val dimorphismReading = stringPreferencesKey("r17_dimorphism_reading")
        val fissionReading = stringPreferencesKey("r17_fission_reading")
        val conjugationReading = stringPreferencesKey("r17_conjugation_reading")
        val reducedMotion = booleanPreferencesKey("r17_reduced_motion")
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
            val progress = state.nuclearProgress.normalized()
            preferences[Keys.nuclearChapter] = progress.chapterId
            preferences[Keys.dimorphismView] = progress.dimorphismView
            preferences[Keys.fissionView] = progress.fissionView
            preferences[Keys.conjugationView] = progress.conjugationView
            preferences[Keys.selectedNucleus] = progress.selectedNucleus
            preferences[Keys.nuclearReading] = progress.readingId
            preferences[Keys.nuclearPhase] = progress.phasePermille
            preferences[Keys.dimorphismReading] = progress.dimorphismReading
            preferences[Keys.fissionReading] = progress.fissionReading
            preferences[Keys.conjugationReading] = progress.conjugationReading
            preferences[Keys.reducedMotion] = progress.reducedMotion
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
            nuclearProgress = NuclearLearningProgress(
                chapterId = preferences[Keys.nuclearChapter] ?: "dimorphism",
                dimorphismView = preferences[Keys.dimorphismView] ?: "whole-cell",
                fissionView = preferences[Keys.fissionView] ?: "preparation",
                conjugationView = preferences[Keys.conjugationView] ?: "pairing",
                selectedNucleus = preferences[Keys.selectedNucleus] ?: "macronucleus",
                readingId = preferences[Keys.nuclearReading] ?: "1.1",
                phasePermille = preferences[Keys.nuclearPhase] ?: 0,
                dimorphismReading = preferences[Keys.dimorphismReading] ?: "1.1",
                fissionReading = preferences[Keys.fissionReading] ?: "2.1",
                conjugationReading = preferences[Keys.conjugationReading] ?: "3.1",
                reducedMotion = preferences[Keys.reducedMotion] ?: false,
            ).normalized(),
        )
    }
}
