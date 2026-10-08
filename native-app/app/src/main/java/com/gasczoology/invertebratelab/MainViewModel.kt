package com.gasczoology.invertebratelab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.ValidatedA5AssetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AcademicLoadState {
    data object Loading : AcademicLoadState
    data class Ready(val units: List<AcademicUnit>) : AcademicLoadState
    data class Failure(val reason: String) : AcademicLoadState
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = LanguagePreferenceRepository(application)
    private val academicRepository = ValidatedA5AssetRepository(application)
    private val _academicState = MutableStateFlow<AcademicLoadState>(AcademicLoadState.Loading)

    val academicState: StateFlow<AcademicLoadState> = _academicState.asStateFlow()

    val language: StateFlow<AppLanguage> = preferences.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppLanguage.ENGLISH,
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _academicState.value = try {
                AcademicLoadState.Ready(academicRepository.load())
            } catch (error: Exception) {
                // Never show a partial or substituted assessment corpus.
                AcademicLoadState.Failure(error.message ?: "A5 corpus could not be validated")
            }
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch { preferences.setLanguage(language) }
    }
}
