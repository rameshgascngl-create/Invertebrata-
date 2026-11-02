package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage

/** Controls stay immediately beside the drawing, including the reason for disabled motion. */
@Composable
internal fun SimulationTransport(
    language: AppLanguage, prefix: String, playing: Boolean, reducedMotion: Boolean,
    previousEnabled: Boolean, nextEnabled: Boolean,
    onPrevious: () -> Unit, onPlayPause: () -> Unit, onNext: () -> Unit,
    onReplay: () -> Unit, onEnableMotion: () -> Unit,
) {
    val tamil = language == AppLanguage.TAMIL
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedButton(onClick = onPrevious, enabled = previousEnabled, contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("$prefix-inline-previous")
                .semantics { contentDescription = if (tamil) "முந்தைய நிலை" else "Previous stage" }) { Text("‹") }
        Button(onClick = onPlayPause, enabled = !reducedMotion, contentPadding = PaddingValues(6.dp),
            modifier = Modifier.weight(2f).heightIn(min = 48.dp).testTag("$prefix-inline-play-pause")) {
            Text(if (tamil) (if (playing) "நிறுத்து" else "இயக்கு") else (if (playing) "Pause" else "Play"))
        }
        OutlinedButton(onClick = onNext, enabled = nextEnabled, contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("$prefix-inline-next")
                .semantics { contentDescription = if (tamil) "அடுத்த நிலை" else "Next stage" }) { Text("›") }
        OutlinedButton(onClick = onReplay, enabled = !reducedMotion, contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("$prefix-inline-replay")
                .semantics { contentDescription = if (tamil) "தொடக்கத்திலிருந்து மீளியக்கு" else "Replay from the beginning" }) { Text("↻") }
    }
    if (reducedMotion) TextButton(onClick = onEnableMotion, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("$prefix-enable-motion")) {
        Text(if (tamil) "குறைக்கப்பட்ட இயக்கம் செயலில் உள்ளது · இயக்கத்தை அனுமதி" else "Reduced motion is on · enable playback")
    }
}
