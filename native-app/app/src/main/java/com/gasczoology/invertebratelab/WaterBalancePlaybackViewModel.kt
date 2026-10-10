package com.gasczoology.invertebratelab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WaterBalancePlayback(val playing: Boolean = false, val phase: Float = 0f)
/** Presentation speed is illustrative. Semantic stages are separately persisted. */
class WaterBalancePlaybackViewModel : ViewModel() {
    private val mutable = MutableStateFlow(WaterBalancePlayback())
    val state = mutable.asStateFlow()
    private var clock: Job? = null
    fun restore(permille: Int) { if (!mutable.value.playing) mutable.value = WaterBalancePlayback(phase = permille.coerceIn(0, 1000) / 1000f) }
    fun pause() { clock?.cancel(); clock = null; mutable.value = mutable.value.copy(playing = false) }
    fun rewind() { pause(); mutable.value = WaterBalancePlayback() }
    fun play(advance: () -> Boolean) {
        if (mutable.value.playing) return
        mutable.value = mutable.value.copy(playing = true)
        clock = viewModelScope.launch {
            while (true) {
                val start = (mutable.value.phase * 60).toInt()
                for (frame in start..60) {
                    mutable.value = WaterBalancePlayback(true, frame / 60f)
                    delay(50)
                }
                if (!advance()) { mutable.value = WaterBalancePlayback(false, 1f); break }
                mutable.value = WaterBalancePlayback(true, 0f)
            }
        }
    }
}
