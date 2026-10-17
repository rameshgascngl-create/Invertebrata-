package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clipToBounds
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIVNereisLessons

/**
 * U5.3: IMAGE-ONLY Nereis atlas. Eight fixed bilingual raster masters are
 * loaded from the APK itself; no network, Canvas anatomy, SVG alternative,
 * tap targets, or selectable anatomical regions.
 *
 * Generated pencil-style reference art must be accurately attributed:
 * NOT presented as an authentic graphite scan, dissection photograph or
 * a college-approved practical plate.
 *
 * Students can see a draft here only after the eight actual source assets
 * have been bundled. A missing image is a visible content defect, never
 * a reason to render the obsolete skeleton-like Canvas substitute.
 */
internal object NereisRasterManifest {
    val filenames = listOf(
        "nereis/01_external_morphology.png",
        "nereis/02_enlarged_head.png",
        "nereis/03_biramous_parapodium.png",
        "nereis/04_digestive_system.png",
        "nereis/05_circulatory_system.png",
        "nereis/06_nervous_system.png",
        "nereis/07_excretory_system.png",
        "nereis/08_trochophore_larva.png",
    )
    init {
        check(filenames.size == 8)
        check(filenames.distinct().size == 8)
    }
}

@Composable
internal fun UnitIVNereisRasterPlate(index: Int, language: AppLanguage) {
    require(index in 0..7) { "Nereis atlas contains exactly eight plates" }
    val ctx = LocalContext.current
    val source = NereisRasterManifest.filenames[index]
    val bitmap = remember(source) {
        try {
            ctx.assets.open(source).use { BitmapFactory.decodeStream(it) }
        } catch (_: Exception) {
            null
        }
    }
    val title = UnitIVNereisLessons.plateTopics[index]
    val ta = language == AppLanguage.TAMIL
    var imageViewerOpen by rememberSaveable(source) { mutableStateOf(false) }

    Card(Modifier.fillMaxWidth().testTag("u44-nereis-static-plate-" + (index + 1))) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(title.english, style = MaterialTheme.typography.titleMedium)
            Text(title.tamil, style = MaterialTheme.typography.titleMedium)
            if (bitmap == null || bitmap.width < 1000 || bitmap.height < 1200) {
                Text(
                    if (ta) "தேவையான இருமொழி நெரீஸ் படக் கோப்பு APK-இல் இல்லை. சரிபார்க்கப்படாத மாற்றுப் படம் காட்டப்படவில்லை."
                    else "Required bilingual Nereis raster plate is absent or too small in this APK. No substitute diagram is shown.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u53-nereis-raster-missing-" + (index + 1)),
                )
            } else {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = title.english + " — " + title.tamil +
                        ". Eight fixed bilingual source-image plates; no interactive organ hotspots.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(bitmap.width.toFloat() / bitmap.height.toFloat())
                        .background(Color.White)
                        .border(1.dp, Color.LightGray)
                        .testTag("u44-nereis-pencil-style-drawing-" + (index + 1)),
                )
            }
            if (bitmap != null && bitmap.width >= 1000 && bitmap.height >= 1200) {
                OutlinedButton(
                    onClick = { imageViewerOpen = true },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u55-nereis-open-full-image-" + (index + 1)),
                ) {
                    Text(if (ta) "படத்தைப் பெரிதாக்கிப் பார்" else "View full-size drawing")
                }
                if (imageViewerOpen) {
                    NereisFullScreenImageViewer(
                        plateNumber = index + 1,
                        image = bitmap.asImageBitmap(),
                        title = title,
                        tamil = ta,
                        onClose = { imageViewerOpen = false },
                    )
                }
            }
            // Real Compose text is deliberately retained outside the image
            // for TalkBack accessibility, Tamil editorial comparison, search
            // and font-scale scaling, although printed labels are already
            // embedded in the original image pixels.
            nereisPlateTerms(index).forEachIndexed { termIndex, term ->
                Text(
                    (termIndex + 1).toString() + ". " + term.english + "\n" + term.tamil,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag(
                        "u44-nereis-plate-" + (index + 1) + "-term-" + termIndex
                    ),
                )
            }
            Text(
                if (ta) "பென்சில் பாணியில் உருவாக்கப்பட்ட கற்பித்தல் பட வரைவு. அசல் கையால் வரைந்த ஸ்கேன் அல்ல. தமிழ் பெயர்களுக்கும் உடற்கூறு குறியீடுகளுக்கும் இறுதி கல்வி ஒப்புதல் வழங்கப்படவில்லை."
                else "Generated pencil-style teaching illustration, not an original pencil scan. Tamil terms and leader-line anatomy still require college-manual and independent zoology approval.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u44-nereis-image-provenance-" + (index + 1)),
            )
        }
    }
}


/**
 * U5.5 viewer: this is a STATIC raster image zoom/pan viewport, not a
 * diagram, organ hotspot, annotation overlay, Canvas substitute or quiz.
 * The same packaged PNG bytes are shown without resampling the source file.
 * Zoom is optional and accessible through 48dp controls; users can dismiss
 * using Back or Close. Normal chapter scrolling/assessment is unchanged.
 */
@Composable
private fun NereisFullScreenImageViewer(
    plateNumber: Int,
    image: ImageBitmap,
    title: com.gasczoology.invertebratelab.data.BilingualText,
    tamil: Boolean,
    onClose: () -> Unit,
) {
    var zoom by remember(plateNumber) { mutableFloatStateOf(1f) }
    var offset by remember(plateNumber) { mutableStateOf(Offset.Zero) }
    var viewport by remember(plateNumber) { mutableStateOf(IntSize.Zero) }

    fun bounded(newZoom: Float, newOffset: Offset): Offset {
        val x = ((newZoom - 1f) * viewport.width / 2f).coerceAtLeast(0f)
        val y = ((newZoom - 1f) * viewport.height / 2f).coerceAtLeast(0f)
        return Offset(
            newOffset.x.coerceIn(-x, x),
            newOffset.y.coerceIn(-y, y),
        )
    }
    val transform = rememberTransformableState { zoomChange, panChange, _ ->
        val next = (zoom * zoomChange).coerceIn(1f, 5f)
        zoom = next
        offset = bounded(next, offset + panChange)
    }
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
                .testTag("u55-nereis-fullscreen-dialog-" + plateNumber),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                Modifier.fillMaxSize().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        if (tamil) title.tamil else title.english,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                            .testTag("u55-nereis-fullscreen-title"),
                    )
                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.heightIn(min = 48.dp)
                            .testTag("u55-nereis-close-full-image"),
                    ) {
                        Text(if (tamil) "மூடு" else "Close")
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f).clipToBounds()
                        .background(Color.White)
                        .border(1.dp, Color.LightGray)
                        .onSizeChanged { viewport = it }
                        .testTag("u55-nereis-zoom-viewport"),
                ) {
                    Image(
                        bitmap = image,
                        contentDescription = title.english + ". " + title.tamil +
                            ". Original fixed raster, enlarge using the zoom controls.",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                            .graphicsLayer {
                                scaleX = zoom
                                scaleY = zoom
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .transformable(state = transform)
                            .testTag("u55-nereis-fullscreen-image-" + plateNumber),
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    OutlinedButton(
                        onClick = {
                            val next = (zoom - 0.5f).coerceAtLeast(1f)
                            zoom = next
                            offset = bounded(next, offset)
                        },
                        enabled = zoom > 1f,
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u55-nereis-zoom-out"),
                    ) { Text("−") }
                    Text(
                        String.format(java.util.Locale.ROOT, "%.1f×", zoom),
                        modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically)
                            .testTag("u55-nereis-zoom-level"),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    OutlinedButton(
                        onClick = {
                            val next = (zoom + 0.5f).coerceAtMost(5f)
                            zoom = next
                            offset = bounded(next, offset)
                        },
                        enabled = zoom < 5f,
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag("u55-nereis-zoom-in"),
                    ) { Text("+") }
                    OutlinedButton(
                        onClick = {
                            zoom = 1f
                            offset = Offset.Zero
                        },
                        modifier = Modifier.weight(1.5f).heightIn(min = 48.dp)
                            .testTag("u55-nereis-zoom-reset"),
                    ) { Text(if (tamil) "மீட்டமை" else "Reset") }
                }
                Text(
                    if (tamil)
                        "படத்தை பெரிதாக்க / நகர்த்த இரண்டு விரல்களைப் பயன்படுத்தவும். உறுப்புத் தொடுதல் இல்லை."
                    else
                        "Pinch to enlarge, drag to inspect. This is a static image, not an organ-hotspot diagram.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
