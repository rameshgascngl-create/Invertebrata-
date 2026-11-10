package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gasczoology.invertebratelab.WaterBalancePlaybackViewModel
import com.gasczoology.invertebratelab.data.*

/** Reuses the repository's authored ciliary teaching projection; the original PNG stays available. */
@Composable
internal fun CiliaryPlaybackPanel(
    language: AppLanguage, initialStage: Int, initialPhase: Int, initialReducedMotion: Boolean,
    speak: (BilingualText) -> Unit, onProgress: (Int, Int, Boolean) -> Unit,
) {
    val model = remember { ParameciumLearningEngine.simulation(ParameciumProcess.CILIARY_MOTION) }
    var index by remember { mutableIntStateOf(initialStage.coerceIn(model.stages.indices)) }
    var reduced by remember { mutableStateOf(initialReducedMotion) }
    val persist by rememberUpdatedState(onProgress)
    val player: WaterBalancePlaybackViewModel = viewModel(key = "ciliary-playback")
    val playback by player.state.collectAsState()
    val tamil = language == AppLanguage.TAMIL
    fun checkpoint() { persist(index, (player.state.value.phase * 1000).toInt(), reduced) }
    fun choose(at: Int) { player.rewind(); index = at; checkpoint() }
    fun advance(): Boolean {
        if (index == model.stages.lastIndex) return false
        index++; persist(index, 0, reduced); return true
    }
    fun toggle() {
        if (player.state.value.playing) { player.pause(); checkpoint() }
        else if (!reduced) {
            if (player.state.value.phase >= 1f) player.rewind()
            player.play(::advance)
        }
    }
    fun replay() { if (!reduced) { choose(0); player.play(::advance) } }
    LaunchedEffect(Unit) { player.pause(); player.restore(initialPhase) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(player, lifecycle) {
        val observer = LifecycleEventObserver { _, event -> if (event == Lifecycle.Event.ON_STOP) { player.pause(); checkpoint() } }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer); player.pause() }
    }
    Text("${index + 1}/${model.stages.size} · " + model.stages[index].heading.value(language), modifier = Modifier.testTag("ciliary-playback-stage"))
    Canvas(Modifier.fillMaxWidth().height(if (androidx.compose.ui.platform.LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) 190.dp else 230.dp).testTag("ciliary-playback-diagram").semantics {
        contentDescription = model.stages[index].heading.value(language)
        progressBarRangeInfo = ProgressBarRangeInfo(playback.phase, 0f..1f)
    }) { drawCiliaryPencilDiagram(model.stages[index].id, "cilia", playback.phase) }
    SimulationTransport(language, "ciliary", playback.playing, reduced, index > 0, index < model.stages.lastIndex,
        { choose(index - 1) }, ::toggle, { choose(index + 1) }, ::replay, { reduced = false; checkpoint() })
    Text(if (tamil) "குறுஇழை இயக்க விளக்க மாதிரி · அளவுக்கேற்ப அல்ல; நிலைகளின் நேர அளவு உண்மையான இயக்க வேகம் அல்ல."
        else "Ciliary-motion teaching model · not to scale; playback time is not measured biological speed.")
    Text(model.stages[index].explanation.value(language))
    model.stages.forEachIndexed { at, stage ->
        OutlinedButton(onClick = { choose(at) }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("ciliary-playback-select-" + stage.id)
            .semantics { selected = at == index }) { Text(stage.heading.value(language)) }
    }
    OutlinedButton(onClick = { player.pause(); reduced = !reduced; checkpoint() }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("ciliary-reduced-motion")) {
        Text(if (tamil) "குறைக்கப்பட்ட இயக்கம்: ${if (reduced) "ஆம்" else "இல்லை"}" else "Reduced motion: ${if (reduced) "On" else "Off"}")
    }
    OutlinedButton(onClick = { speak(model.stages[index].explanation) }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
        Text(if (tamil) "நிலை விளக்கத்தைக் கேள்" else "Hear this stage")
    }
    ParameciumProcessPng(ParameciumProcess.CILIARY_MOTION, language)
}
