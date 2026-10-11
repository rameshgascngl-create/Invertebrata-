package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import kotlin.math.roundToInt

/**
 * Image-backed Unit II atlas adapter, isolated from the validated Unit II lessons/A5 data.
 * Assets are offline images at assets/unit2/sycon_plate.webp and obelia_colony.webp.
 * An image missing from the APK is NOT an accepted atlas: the caller renders
 * its prior schematic, and the screenshot acceptance test must fail explicitly.
 *
 * Hotspots are in image-relative coordinates, not arbitrary nearest-point taps.
 * The atlas image and active focus scale/pan together; stage buttons remain native.
 */
private data class AtlasTarget(val stage: Int, val x: Float, val y: Float, val radius: Float = .085f)
private val syconTargets = listOf(
    AtlasTarget(0,.30f,.35f),  // dermal ostia, external morphology
    AtlasTarget(1,.68f,.60f),  // incurrent passage, cutaway
    AtlasTarget(2,.64f,.59f),  // prosopyle - requires plate review
    AtlasTarget(3,.59f,.61f),  // radial canal
    AtlasTarget(4,.64f,.72f),  // apopyle - requires plate review
    AtlasTarget(5,.70f,.42f),  // spongocoel
    AtlasTarget(6,.67f,.075f)  // osculum
)
private val obeliaColonyTargets = listOf(
    AtlasTarget(0,.33f,.71f),  // hydrocaulus, living colony
    AtlasTarget(1,.50f,.14f),  // feeding hydranth
    AtlasTarget(2,.67f,.39f)   // gonangium (theca, blastostyle, buds)
)
private val obeliaLifeHistoryTargets = listOf(
    AtlasTarget(3,.17f,.40f,.12f), // free medusa
    AtlasTarget(4,.50f,.40f,.12f), // ciliated planula
    AtlasTarget(5,.83f,.40f,.12f)  // settled young colony
)

@Composable
internal fun UnitIIImagePlate(
    sycon: Boolean,
    progress: Float,
    language: AppLanguage,
    onSelect: (Int) -> Unit,
    fallback: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    val lifeHistory = !sycon && progress.roundToInt() >= 3
    val asset = if (sycon) "unit2/sycon_plate.webp"
                else if (lifeHistory) "unit2/obelia_lifecycle.webp"
                else "unit2/obelia_colony.webp"
    val bitmap = remember(ctx,asset) {
        runCatching {
            ctx.assets.open(asset).use { input ->
                BitmapFactory.decodeStream(input)?.asImageBitmap()
            }
        }.getOrNull()
    }
    if (bitmap == null) {
        fallback()
        Text(
            if (language == AppLanguage.TAMIL)
                "அங்கீகரிக்கப்பட்ட படத்தாள் APK-இல் இல்லை; முந்தைய விளக்க வரைபடம் காட்டப்படுகிறது."
            else "Scanned atlas asset absent from APK; showing the previous schematic.",
            modifier=Modifier.testTag("u2-atlas-asset-missing"),
            color=MaterialTheme.colorScheme.error,
            style=MaterialTheme.typography.bodySmall
        )
        return
    }

    val targets = if (sycon) syconTargets
                  else if (lifeHistory) obeliaLifeHistoryTargets else obeliaColonyTargets
    var scale by rememberSaveable(asset) { mutableFloatStateOf(1f) }
    var pan by remember(asset) { mutableStateOf(Offset.Zero) }
    val transform = rememberTransformableState { zoomDelta,panDelta,_ ->
        val updated=(scale*zoomDelta).coerceIn(1f,4f)
        scale=updated
        pan=if(updated<=1f) Offset.Zero else pan+panDelta
    }
    Column(verticalArrangement=Arrangement.spacedBy(6.dp)) {
        Box(
            Modifier.fillMaxWidth()
                .aspectRatio(bitmap.width.toFloat()/bitmap.height)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .transformable(transform)
                .testTag(if(sycon)"u2-sycon-image-plate" else "u2-obelia-image-plate")
        ){
            Box(Modifier.fillMaxSize().graphicsLayer {
                scaleX=scale
                scaleY=scale
                translationX=pan.x
                translationY=pan.y
            }){
                Image(bitmap=bitmap,contentDescription=null,
                    modifier=Modifier.fillMaxSize(),contentScale=ContentScale.FillBounds)
                Canvas(
                    Modifier.fillMaxSize()
                        .pointerInput(asset) {
                            detectTapGestures { click ->
                                val xx=click.x/size.width.toFloat()
                                val yy=click.y/size.height.toFloat()
                                val nearest=targets.minByOrNull {
                                    val dx=xx-it.x;val dy=yy-it.y
                                    dx*dx+dy*dy
                                }
                                if(nearest!=null) {
                                    val dx=xx-nearest.x;val dy=yy-nearest.y
                                    if(dx*dx+dy*dy<=nearest.radius*nearest.radius) {
                                        onSelect(nearest.stage)
                                    }
                                }
                            }
                        }
                        .semantics {
                            contentDescription=if(sycon)
                                "Sycon pencil-style plate. Pinch to enlarge; select a canal structure."
                            else "Obelia pencil-style colony plate. Pinch to enlarge; select a polyp or medusa."
                        }
                ){
                    val selected=targets.firstOrNull{it.stage==progress.roundToInt()}
                    if(selected!=null) {
                        val center=Offset(selected.x*size.width,selected.y*size.height)
                        drawCircle(Color(0xFFD36A27),radius=14f,center=center,
                            style=Stroke(width=2.5f))
                        drawCircle(Color(0x55D36A27),radius=5f,center=center)
                    }
                }
            }
        }
        Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            Text(
                if(language==AppLanguage.TAMIL)
                    "இரு விரல்களால் பெரிதாக்கவும்; குறியிட்ட அமைப்பைத் தொடவும்"
                else "Pinch to zoom; touch a marked anatomical structure",
                modifier=Modifier.weight(1f),style=MaterialTheme.typography.bodySmall
            )
            OutlinedButton(
                onClick={scale=1f;pan=Offset.Zero},
                modifier=Modifier.heightIn(min=48.dp).testTag("u2-reset-zoom")
            ){
                Text(if(language==AppLanguage.TAMIL)"அளவை மீட்டமை" else "Reset zoom")
            }
        }
    }
}
