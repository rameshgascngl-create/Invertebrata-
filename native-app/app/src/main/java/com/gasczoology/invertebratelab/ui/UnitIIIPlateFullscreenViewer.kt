package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIIIPlateSpec

/**
 * Full-screen, image-first source-raster inspection, including Tamil 200%.
 * This is NOT a reviewed hotspot map; taps never select anatomical organs.
 * The exact bitmap passed from the teaching plate is displayed without
 * drawing, resampling, watermarking or anatomy substitutions.
 */
@Composable
internal fun UnitIIIFullscreenRasterViewer(
    plate: UnitIIIPlateSpec,
    bitmap: ImageBitmap,
    language: AppLanguage,
    onClose: () -> Unit,
) {
    val tamil = language == AppLanguage.TAMIL
    var zoom by rememberSaveable(plate.assetStem) { mutableFloatStateOf(1f) }
    var panX by rememberSaveable(plate.assetStem) { mutableFloatStateOf(0f) }
    var panY by rememberSaveable(plate.assetStem) { mutableFloatStateOf(0f) }
    Dialog(onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize()
            .testTag("u3-fullscreen-dialog-" + plate.plateId)) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding().navigationBarsPadding().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(plate.title.value(language),
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.weight(1f).testTag("u3-fullscreen-title-" + plate.plateId))
                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.heightIn(min = 48.dp)
                            .testTag("u3-fullscreen-close-" + plate.plateId),
                    ) { Text(if (tamil) "மூடு" else "Close") }
                }
                Text(
                    if (tamil)
                        "அசல் மூலப்பட ஆய்வு · தொடுபுள்ளிகள் அறிவியல் ஒப்புதல் பெறவில்லை"
                    else "Source raster inspection · anatomical hotspots NOT approved",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 2,
                    modifier = Modifier.testTag("u3-fullscreen-academic-hold-" + plate.plateId),
                )
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .background(Color.White).clipToBounds()
                        .pointerInput(plate.assetStem) {
                            detectTransformGestures { _, pan, scale, _ ->
                                val next = (zoom * scale).coerceIn(1f, 4f)
                                zoom = next
                                if (next <= 1f) {
                                    panX = 0f; panY = 0f
                                } else {
                                    val xLimit = (next - 1f) * size.width * .5f
                                    val yLimit = (next - 1f) * size.height * .5f
                                    panX = (panX + pan.x).coerceIn(-xLimit, xLimit)
                                    panY = (panY + pan.y).coerceIn(-yLimit, yLimit)
                                }
                            }
                        }
                        .testTag("u3-fullscreen-image-viewport-" + plate.plateId),
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = plate.title.value(language) +
                            (if (tamil) " · மூலப்படம், உடற்கூறு ஒப்புதல் நிலுவை"
                             else " · source bitmap, anatomy review pending"),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                            .graphicsLayer {
                                scaleX = zoom
                                scaleY = zoom
                                translationX = panX
                                translationY = panY
                            }
                            .testTag("u3-fullscreen-real-bitmap-" + plate.plateId),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .testTag("u3-fullscreen-controls-" + plate.plateId),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedButton(
                        onClick = { zoom = (zoom / 2f).coerceAtLeast(1f); panX = 0f; panY = 0f },
                        enabled = zoom > 1f,
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u3-fullscreen-zoom-out-" + plate.plateId),
                    ) { Text("−") }
                    OutlinedButton(
                        onClick = { zoom = (zoom * 2f).coerceAtMost(4f); panX = 0f; panY = 0f },
                        enabled = zoom < 4f,
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u3-fullscreen-zoom-in-" + plate.plateId),
                    ) { Text("+") }
                    OutlinedButton(
                        onClick = { zoom = 1f; panX = 0f; panY = 0f },
                        modifier = Modifier.weight(2f).heightIn(min = 48.dp)
                            .testTag("u3-fullscreen-reset-" + plate.plateId),
                    ) { Text(if (tamil) "மீட்டமை" else "Reset") }
                }
                Text(
                    (if(tamil) "பெரிதாக்கம்: " else "Magnification: ") +
                        zoom.toInt() + "× / 4×",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.testTag("u3-fullscreen-zoom-level-" + plate.plateId),
                )
            }
        }
    }
}
