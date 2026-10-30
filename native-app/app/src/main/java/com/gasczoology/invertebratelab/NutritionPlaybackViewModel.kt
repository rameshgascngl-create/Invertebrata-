package com.gasczoology.invertebratelab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NutritionPlayback(val playing: Boolean = false, val phase: Float = 0f)
/** Six-second presentation gives time to inspect/pause a plate; it is not biological kinetics. */
class NutritionPlaybackViewModel : ViewModel() {
    private val mutable = MutableStateFlow(NutritionPlayback())
    val state = mutable.asStateFlow()
    private var clock: Job? = null
    fun restore(permille: Int) { if (!mutable.value.playing) mutable.value = NutritionPlayback(phase = permille.coerceIn(0, 1000) / 1000f) }
    fun pause() { clock?.cancel(); clock = null; mutable.value = mutable.value.copy(playing = false) }
    fun rewind() { pause(); mutable.value = NutritionPlayback() }
    /**
     * Optional teaching hold runs the actual frame clock to a reproducible
     * intermediate phase; it never substitutes an invented screenshot frame.
     * Ordinary play() still runs all 120 ticks and invokes onComplete.
     * All lifecycle/manual pauses use the same non-playing state.
     */
    fun play(stopAt: Float? = null, onHold: ((Float) -> Unit)? = null,
             onComplete: () -> Unit) {
        if (mutable.value.playing) return
        require(stopAt == null || stopAt in 0.4f..0.85f)
        mutable.value = mutable.value.copy(playing = true)
        clock = viewModelScope.launch {
            val start = (mutable.value.phase * 120).toInt()
            for (frame in start..120) {
                val phase = frame / 120f
                if (stopAt != null && phase >= stopAt) {
                    // Freeze on a real emitted frame, before any Compose scroll,
                    // button recomposition or screenshot capture can advance it.
                    mutable.value = NutritionPlayback(false, phase)
                    clock = null
                    onHold?.invoke(phase)
                    return@launch
                }
                mutable.value = NutritionPlayback(true, phase)
                delay(50)
            }
            clock = null
            mutable.value = NutritionPlayback(false, 1f)
            onComplete()
        }
    }
}
