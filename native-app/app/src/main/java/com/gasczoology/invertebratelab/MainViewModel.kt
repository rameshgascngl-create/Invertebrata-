package com.gasczoology.invertebratelab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.BookReadingPosition
import com.gasczoology.invertebratelab.data.BookmarkRepository
import com.gasczoology.invertebratelab.data.Bookmarks
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.Extensions
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.NativeCurriculumManifest
import com.gasczoology.invertebratelab.data.ValidatedA5AssetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    private val bookReading = com.gasczoology.invertebratelab.data.BookReadingRepository(application)
    private val _readingPositions = MutableStateFlow<Map<String, BookReadingPosition>?>(null)
    val readingPositions: StateFlow<Map<String, BookReadingPosition>?> = _readingPositions.asStateFlow()
    private val pendingReadingPositions = mutableMapOf<String, BookReadingPosition>()
    private val readingWriteMutex = Mutex()
    private var storedReadingPositions: Map<String, BookReadingPosition> = emptyMap()

    private fun publishReadingPositions() {
        // A matching value from an earlier write is not an acknowledgement of queued writes.
        if (!readingWriteMutex.isLocked) {
            pendingReadingPositions.entries.removeAll { storedReadingPositions[it.key] == it.value }
        }
        _readingPositions.value = storedReadingPositions + pendingReadingPositions
    }

    fun saveReadingPosition(chapter: String, position: BookReadingPosition) {
        // Rotation must see the latest offset before the asynchronous disk write completes.
        pendingReadingPositions[chapter] = position
        _readingPositions.value = _readingPositions.value.orEmpty() + (chapter to position)
        viewModelScope.launch {
            readingWriteMutex.withLock { bookReading.save(chapter, position) }
            publishReadingPositions()
        }
    }

    private val bookmarkRepository = BookmarkRepository(application)
    private val _bookmarks = MutableStateFlow<Map<String, Set<String>>>(emptyMap())
    val bookmarks: StateFlow<Map<String, Set<String>>> = _bookmarks.asStateFlow()
    private val bookmarkMutex = Mutex()

    fun toggleBookmark(chapter: String, pageId: String) {
        // Show the change at once; the disk write below is the source of truth afterwards.
        _bookmarks.value = Bookmarks.toggle(_bookmarks.value, chapter, pageId)
        viewModelScope.launch { bookmarkMutex.withLock { bookmarkRepository.toggle(chapter, pageId) } }
    }

    val persistedLearning: StateFlow<PersistedLearningLoadState> = learningRepository.learningState
        .map<NativeLearningState, PersistedLearningLoadState> { PersistedLearningLoadState.Ready(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, PersistedLearningLoadState.Loading)

    val language: StateFlow<AppLanguage> = preferences.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppLanguage.ENGLISH,
    )

    init {
        viewModelScope.launch {
            bookReading.positions.collect { stored ->
                // An older disk emission must never roll back a newer UI checkpoint.
                storedReadingPositions = stored
                publishReadingPositions()
            }
        }
        viewModelScope.launch {
            bookmarkRepository.bookmarks.collect { stored ->
                // Only trust disk when no write is in flight; otherwise our optimistic value is newer.
                if (!bookmarkMutex.isLocked) _bookmarks.value = stored
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            _academicState.value = try {
                academicRepository.load().let { units ->
                    NativeCurriculumManifest.validateAgainst(units)
                    AcademicLoadState.Ready(units + Extensions.units)
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
