package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.ParameciumFissionPlateCatalogue

/** Offline debug-only raster viewer. No Canvas sketch or remote network fallback. */
@Composable
internal fun ParameciumFissionPngViewer(stageId: String, language: AppLanguage) {
    val context = LocalContext.current
    val stagePlates = ParameciumFissionPlateCatalogue.forStage(stageId)
    // Keep existing source plates accessible even where a later-stage PNG is absent.
    // Captions identify the actual depicted stage; never relabel F01-F03 as furrow/daughters.
    val plates = if (stagePlates.isEmpty()) ParameciumFissionPlateCatalogue.plates else stagePlates
    Column {
        if (stagePlates.isEmpty()) {
            Text(if (language == AppLanguage.TAMIL)
                "இந்த நிலைக்கான தனிப்படம் இன்னும் இல்லை. கீழே முந்தைய நிலைகளுக்கான கிடைக்கும் மூலப்படங்கள் காட்டப்பட்டுள்ளன; இவை தற்போதைய நிலையைச் சித்தரிக்கவில்லை."
                else "No dedicated image for this stage yet. The available earlier-stage source plates are shown below; they do not depict the selected stage.",
                modifier = Modifier.testTag("paramecium-fission-earlier-stage-disclosure"))
        }
        plates.forEach { plate ->
            val bitmap = remember(plate.sourceFile) {
                runCatching {
                    context.assets.open("paramecium/fission/" + plate.sourceFile).use {
                        BitmapFactory.decodeStream(it)?.asImageBitmap()
                    }
                }.getOrNull()
            }
            Text(if (language == AppLanguage.TAMIL) plate.tamilCaption else plate.englishCaption)
            if (bitmap == null) {
                Text(if (language == AppLanguage.TAMIL)
                    "இந்தப் படம் இப்பதிப்பில் கிடைக்கவில்லை."
                    else "This plate is unavailable in this build.")
            } else {
                var scale by remember(plate.id) { mutableFloatStateOf(1f) }
                var offsetX by remember(plate.id) { mutableFloatStateOf(0f) }
                var offsetY by remember(plate.id) { mutableFloatStateOf(0f) }
                Box(Modifier.fillMaxWidth().height(300.dp).padding(vertical = 8.dp)) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = if (language == AppLanguage.TAMIL)
                            plate.tamilCaption else plate.englishCaption,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                            .graphicsLayer(scaleX = scale, scaleY = scale,
                                translationX = offsetX, translationY = offsetY)
                            .pointerInput(plate.id) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    scale = (scale * zoom).coerceIn(1f, 5f)
                                    offsetX = (offsetX + pan.x).coerceIn(-size.width * (scale - 1f) / 2f, size.width * (scale - 1f) / 2f)
                                    offsetY = (offsetY + pan.y).coerceIn(-size.height * (scale - 1f) / 2f, size.height * (scale - 1f) / 2f)
                                }
                            }
                            .testTag("paramecium-fission-" + plate.id)
                    )
                }
            }
        }
    }
}
