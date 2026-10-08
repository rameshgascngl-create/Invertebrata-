package com.gasczoology.invertebratelab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.NativeCurriculumManifest
import com.gasczoology.invertebratelab.data.ValidatedA5AssetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface AcademicLoadState {
    data object Loading : AcademicLoadState
    data class Ready(val units: List<AcademicUnit>) : AcademicLoadState
    data class Failure(val reason: String) : AcademicLoadState
}

sealed interface PersistedLearningLoadState {
    data object Loading : PersistedLearningLoadState
    data class Ready(val state: NativeLearningState) : PersistedLearningLoadState
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = LanguagePreferenceRepository(application)
    private val academicRepository = ValidatedA5AssetRepository(application)
    private val learningRepository = NativeLearningRepository(application)
    private val learningWriteMutex = Mutex()
    private val _academicState = MutableStateFlow<AcademicLoadState>(AcademicLoadState.Loading)

    val academicState: StateFlow<AcademicLoadState> = _academicState.asStateFlow()

    val persistedLearning: StateFlow<PersistedLearningLoadState> = learningRepository.learningState
        .map<NativeLearningState, PersistedLearningLoadState> { PersistedLearningLoadState.Ready(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, PersistedLearningLoadState.Loading)

    val language: StateFlow<AppLanguage> = preferences.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppLanguage.ENGLISH,
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _academicState.value = try {
                academicRepository.load().let { units ->
                    NativeCurriculumManifest.validateAgainst(units)
                    AcademicLoadState.Ready(units)
                }
            } catch (error: Exception) {
                // Never show a partial or substituted assessment corpus.
                AcademicLoadState.Failure(error.message ?: "A5 corpus could not be validated")
            }
        }
    }

    /** Invoke the navigation callback only after the learning position is written. */
    fun persistLearning(state: NativeLearningState, onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            learningWriteMutex.withLock { learningRepository.save(state) }
            onSaved()
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch { preferences.setLanguage(language) }
    }
}
