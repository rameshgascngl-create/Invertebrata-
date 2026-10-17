package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.TypeStudyRequirement

/**
 * R2: fail-visible, raster-only textbook plate gallery for the nine required
 * organism type studies. No anatomical Canvas, SVG or placeholder sketches.
 *
 * Missing masters are missing academic content, not a green release gate.
 * Images present are *candidates* until scientific, rights and Tamil review.
 * The bitmap itself is never relabelled by guessed image coordinates.
 */
@Composable
internal fun RequiredTypeStudyPlates(
    study: TypeStudyRequirement,
    language: AppLanguage,
) {
    val tamil = language == AppLanguage.TAMIL
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth().testTag("r2-type-study-gallery-" + study.organismId),
    ) {
        Text(
            if (tamil) "அவசியமான உடற்கூறுப் பென்சில் படங்கள் · ${study.scientificName}"
            else "REQUIRED ANATOMICAL PENCIL PLATES · ${study.scientificName}",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            if (tamil) "இவை தனித்த படக்கோப்புகளாக செயலியில் இணைக்கப்பட வேண்டும். படம் இருந்தாலும் அதன் உடற்கூறு, உரிமை, தமிழ் குறியீடுகள் மதிப்பாய்வுக்கு உட்பட்டவை."
            else "Each figure must be an actual bundled image. Presence alone does not verify morphology, image rights or Tamil terminology.",
            style = MaterialTheme.typography.bodyMedium,
        )
        study.requiredPlateIds.forEach { plateId ->
            val stem = "type_studies/${study.organismId}/${plateId}"
            val raster = rememberRequiredRasterBitmap(stem)
            val readable = plateId.replace('-', ' ').replaceFirstChar { it.uppercase() }
            var expanded by rememberSaveable(stem) { mutableStateOf(false) }
            Card(Modifier.fillMaxWidth().testTag("r2-plate-slot-${study.organismId}-${plateId}")) {
                Column(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(readable, style = MaterialTheme.typography.titleMedium)
                    if (raster == null) {
                        Text(
                            if (tamil) "படக்கோப்பு இணைக்கப்படவில்லை: ${stem}.png / .webp. மாற்று Canvas படம் பயன்படுத்தப்படாது."
                            else "Image missing: ${stem}.png / .webp. No Canvas anatomy substitute.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("r2-plate-missing-${study.organismId}-${plateId}"),
                        )
                    } else {
                        Image(
                            bitmap = raster,
                            contentDescription = "${study.scientificName}: ${readable}",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth().height(280.dp)
                                .testTag("r2-plate-bitmap-${study.organismId}-${plateId}"),
                        )
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.heightIn(min = 48.dp)
                                .testTag("r2-plate-expand-${study.organismId}-${plateId}"),
                        ) {
                            Text(if (tamil) "முழு அளவில் காட்டு" else "Enlarge original image")
                        }
                        Text(
                            if (tamil) "ஆய்வுக்கான வரைவு · இறுதி அறிவியல் ஒப்புதல் பெறவில்லை."
                            else "Raster candidate · anatomy and bilingual review pending.",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        if (expanded) {
                            RequiredTypeStudyFullImage(
                                image = raster, title = "${study.scientificName} · ${readable}",
                                language = language, onDismiss = { expanded = false },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RequiredTypeStudyFullImage(
    image: ImageBitmap,
    title: String,
    language: AppLanguage,
    onDismiss: () -> Unit,
) {
    var zoom by remember(title) { mutableFloatStateOf(1f) }
    var panX by remember(title) { mutableFloatStateOf(0f) }
    var panY by remember(title) { mutableFloatStateOf(0f) }
    val tamil = language == AppLanguage.TAMIL
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(
            Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).padding(14.dp)
                .testTag("r2-fullscreen-dialog"),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Box(
                Modifier.fillMaxWidth().weight(1f)
                    .pointerInput(title) {
                        detectTransformGestures { _, pan, change, _ ->
                            zoom = (zoom * change).coerceIn(1f, 4f)
                            if (zoom <= 1.01f) { panX = 0f; panY = 0f }
                            else {
                                panX = (panX + pan.x).coerceIn(-size.width.toFloat(), size.width.toFloat())
                                panY = (panY + pan.y).coerceIn(-size.height.toFloat(), size.height.toFloat())
                            }
                        }
                    },
            ) {
                Image(
                    bitmap = image,
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().graphicsLayer {
                        scaleX = zoom; scaleY = zoom
                        translationX = panX; translationY = panY
                    }.testTag("r2-fullscreen-bitmap"),
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { zoom = (zoom / 2f).coerceAtLeast(1f) },
                    modifier = Modifier.heightIn(min = 48.dp).testTag("r2-zoom-out")) {
                    Text("−")
                }
                OutlinedButton(onClick = { zoom = (zoom * 2f).coerceAtMost(4f) },
                    modifier = Modifier.heightIn(min = 48.dp).testTag("r2-zoom-in")) {
                    Text("+")
                }
                OutlinedButton(onClick = { zoom = 1f; panX = 0f; panY = 0f },
                    modifier = Modifier.heightIn(min = 48.dp).testTag("r2-zoom-reset")) {
                    Text(if (tamil) "மீட்டமை" else "Reset")
                }
                OutlinedButton(onClick = onDismiss,
                    modifier = Modifier.heightIn(min = 48.dp).testTag("r2-fullscreen-close")) {
                    Text(if (tamil) "மூடு" else "Close")
                }
            }
            Text(
                if (tamil) "அறிவியல் மற்றும் தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது"
                else "Anatomical and Tamil verification pending",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
