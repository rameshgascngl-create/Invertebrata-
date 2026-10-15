package com.gasczoology.invertebratelab.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.UnitIFlagellateLifeCycles
import kotlinx.coroutines.delay

/**
 * Native stage-transition teaching model, not a biological speed measurement.
 * No anatomy is rendered or annotated over unverified historical reference art.
 * Offline speech is optional; written explanation remains present at all times.
 */
@Composable
internal fun UnitIFlagellateLifeCycleSimulator(id: String, language: AppLanguage) {
    val cycle = requireNotNull(UnitIFlagellateLifeCycles.forOrganism(id))
    val tamil = language == AppLanguage.TAMIL
    val context = LocalContext.current
    val narrator = remember(context, id) { OfflineNarrator(context.applicationContext) }
    val narrationPhase by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }

    var selected by rememberSaveable(id) { mutableIntStateOf(0) }
    // Playback intentionally does not persist across rotation/process death.
    // A saved educational stage DOES persist through rememberSaveable.
    var playing by remember(id) { mutableStateOf(false) }
    val last = cycle.phases.lastIndex

    LaunchedEffect(id, language) {
        narrator.stop() // Prevent previous-language audio from surviving language switch.
    }
    LaunchedEffect(id, selected, playing) {
        if (playing) {
            delay(2200L)
            if (selected >= last) {
                playing = false
            } else {
                selected += 1 // coroutine cancels/restarts on the new selected stage
                if (selected >= last) playing = false
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("u18-cycle-" + id)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            Text(
                if (tamil) cycle.titleTa else cycle.titleEn,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("u18-title-" + id).semantics { heading() },
            )
            Text(
                if (tamil)
                    "செயல்படும் வாழ்க்கைச் சுழற்சி — கல்விக்கான நிலை வரிசை மட்டும். அசைவின் வேகம் உண்மையான உயிரியல் வளர்ச்சி நேரமல்ல; உடலமைப்புப் படம் அல்ல."
                else
                    "Interactive life-cycle sequence. Stage transition speed is a classroom pacing aid, NOT actual parasite development time or an anatomical illustration.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("u18-teaching-cadence-note-" + id),
            )
            LinearProgressIndicator(
                progress = { (selected + 1).toFloat() / cycle.phases.size },
                modifier = Modifier.fillMaxWidth().height(7.dp)
                    .testTag("u18-cycle-progress-" + id),
            )
            LifeCycleStageTrack(
                stage = selected,
                count = cycle.phases.size,
                language = language,
                id = id,
            )
            Text(
                if (tamil)
                    "நிலை " + (selected + 1) + " / " + cycle.phases.size
                else "Stage " + (selected + 1) + " of " + cycle.phases.size,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.testTag("u18-stage-counter-" + id),
            )
            Crossfade(targetState = selected, label = "u18-native-stage-transition-" + id) { index ->
                val phase = cycle.phases[index.coerceIn(0, last)]
                Surface(
                    tonalElevation = 3.dp,
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            if (tamil) phase.titleTa else phase.titleEn,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.testTag("u18-phase-title-" + id),
                        )
                        Text(
                            if (tamil) phase.placeTa else phase.placeEn,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.testTag("u18-host-location-" + id),
                        )
                        Text(
                            if (tamil) phase.explanationTa else phase.explanationEn,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.testTag("u18-stage-explanation-" + id),
                        )
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { playing = false; narrator.stop(); selected -= 1 },
                    enabled = selected > 0,
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u18-prev-" + id),
                ) { Text(if (tamil) "முந்தையது" else "Previous") }
                OutlinedButton(
                    onClick = { playing = false; narrator.stop(); selected += 1 },
                    enabled = selected < last,
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u18-next-" + id),
                ) { Text(if (tamil) "அடுத்தது" else "Next") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        narrator.stop()
                        if (playing) playing = false
                        else {
                            if (selected >= last) selected = 0
                            playing = true
                        }
                    },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u18-play-pause-" + id),
                ) { Text(
                    if (playing) (if (tamil) "இடைநிறுத்து" else "Pause")
                    else (if (tamil) "நிலைகளை இயக்கு" else "Play sequence")
                ) }
                OutlinedButton(
                    onClick = { playing = false; selected = 0; narrator.stop() },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u18-reset-" + id),
                ) { Text(if (tamil) "மீட்டமை" else "Reset") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        playing = false
                        val current = cycle.phases[selected.coerceIn(0, last)]
                        narrator.speak(
                            BilingualText(
                                current.titleEn + ". " + current.placeEn + ". " +
                                    current.explanationEn,
                                current.titleTa + ". " + current.placeTa + ". " +
                                    current.explanationTa,
                            ),
                            language,
                        )
                    },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u18-speak-" + id),
                ) {
                    Text(if (tamil) "விளக்க ஒலி" else "Speak stage")
                }
                OutlinedButton(
                    onClick = { narrator.stop() },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u18-stop-audio-" + id),
                ) {
                    Text(if (tamil) "ஒலியை நிறுத்து" else "Stop audio")
                }
            }
            Text(
                if (tamil)
                    "குரல் நிலை: " + narrationPhase.name +
                        ". சாதனத்தில் இணையமின்றி இயங்கும் தமிழ் குரல் இல்லாவிட்டாலும் முழுப் பாடம் வாசிக்கக் கிடைக்கும்."
                else
                    "Offline speech status: " + narrationPhase.name +
                        ". If the device lacks an installed offline voice, the full lesson remains readable.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u18-audio-status-" + id),
            )
            Text(
                if (tamil)
                    "உயிரியல் ஆதாரம்: " + cycle.source
                else "Biology source: " + cycle.source,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

/**
 * Native Canvas conceptual stage track. Marker positions correspond to
 * *ordered stages*, never to organ locations or unpublished image hotspots.
 */
@Composable
private fun LifeCycleStageTrack(
    stage: Int,
    count: Int,
    language: AppLanguage,
    id: String,
) {
    val primary = MaterialTheme.colorScheme.primary
    val muted = MaterialTheme.colorScheme.outlineVariant
    val ink = MaterialTheme.colorScheme.onSurfaceVariant
    val marker by animateFloatAsState(
        targetValue = stage.toFloat(),
        label = "u18-stage-marker-" + id,
    )
    Canvas(
        modifier = Modifier.fillMaxWidth().height(62.dp)
            .semantics {
                contentDescription =
                    if (language == AppLanguage.TAMIL)
                        "வாழ்க்கை நிலை வரிசை. தற்போதைய நிலை " + (stage + 1) + " / " + count
                    else "Life-cycle stage sequence. Current stage " + (stage + 1) + " of " + count
            }
            .testTag("u18-native-canvas-track-" + id),
    ) {
        if (count <= 1) return@Canvas
        val left = 18.dp.toPx()
        val usable = (size.width - left * 2).coerceAtLeast(0f)
        val middle = size.height * 0.42f
        fun point(index: Float) = left + usable * (index / (count - 1))
        drawLine(
            color = muted,
            start = Offset(point(0f), middle),
            end = Offset(point((count - 1).toFloat()), middle),
            strokeWidth = 3.dp.toPx(),
        )
        repeat(count) { i ->
            drawCircle(
                color = if (i <= stage) primary else ink,
                radius = 7.dp.toPx(),
                center = Offset(point(i.toFloat()), middle),
            )
            if (i == stage) {
                drawCircle(
                    color = primary,
                    radius = 11.dp.toPx(),
                    center = Offset(point(i.toFloat()), middle),
                    style = Stroke(width = 2.dp.toPx()),
                )
            }
        }
        // Smoothly moving emphasis, NOT parasite locomotion or measured kinetics.
        drawCircle(
            color = primary,
            radius = 4.dp.toPx(),
            center = Offset(point(marker), middle + 21.dp.toPx()),
        )
    }
}
