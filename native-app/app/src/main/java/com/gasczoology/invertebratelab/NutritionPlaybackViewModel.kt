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
    fun play(onComplete: () -> Unit) {
        if (mutable.value.playing) return
        mutable.value = mutable.value.copy(playing = true)
        clock = viewModelScope.launch {
            val start = (mutable.value.phase * 120).toInt()
            for (frame in start..120) {
                mutable.value = NutritionPlayback(true, frame / 120f)
                delay(50)
            }
            mutable.value = NutritionPlayback(false, 1f)
            onComplete()
        }
    }
}
