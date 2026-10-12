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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import kotlin.math.roundToInt

/**
 * Image-backed Unit II atlas adapter, isolated from the validated Unit II lessons/A5 data.
 * Native offline raster assets: labelled PNG first, WebP as a raster alternative.
 * A missing raster file is a hard, visible teaching-plate defect. It must never
 * silently fall back to programmatic Canvas/SVG anatomy. Android tests fail explicitly.
 *
 * Hotspots are in image-relative coordinates, not arbitrary nearest-point taps.
 * The atlas image and active focus scale/pan together; stage buttons remain native.
 */
private data class AtlasTarget(val stage: Int, val x: Float, val y: Float, val radius: Float = .085f)
private val syconTargets = listOf(
    AtlasTarget(0,.30f,.35f),       // ostia, external view
    AtlasTarget(1,.56f,.61f,.060f), // between radial chamber levels: incurrent passage
    AtlasTarget(2,.595f,.6008f,.028f), // PROVISIONAL prosopyle in lateral radial wall (595,751); outer radial tip remains blind
    AtlasTarget(3,.645f,.540f,.043f), // choanocyte-lined radial canal
    AtlasTarget(4,.648f,.494f,.043f), // PROVISIONAL apopyle at radial outlet
    AtlasTarget(5,.713f,.324f,.085f), // spongocoel (not a filtering chamber)
    AtlasTarget(6,.67f,.075f)       // osculum
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

/**
 * A lesson plate is a plain bitmap, visually independent of the interactive atlas.
 * No hotspots, Canvas, SVG, dynamic labels, arrows or touch-target overlays.
 * Artwork is pencil-style raster; its physical drawing provenance and anatomy
 * are NOT certified until the separate academic gate is signed off.
 */
@Composable
internal fun UnitIILessonPencilPlate(
    sycon: Boolean,
    lifeHistory: Boolean = false,
    language: AppLanguage
) {
    val ctx = LocalContext.current
    val base = if (sycon) "sycon_plate"
               else if (lifeHistory) "obelia_lifecycle"
               else "obelia_colony"
    val available = remember(ctx) {
        runCatching { ctx.assets.list("unit2")?.toSet().orEmpty() }.getOrDefault(emptySet())
    }
    val asset = when {
        "$base.png" in available -> "unit2/$base.png"
        "$base.webp" in available -> "unit2/$base.webp"
        else -> "unit2/$base.png"
    }
    val bitmap = remember(ctx, asset) {
        runCatching {
            ctx.assets.open(asset).use { input ->
                BitmapFactory.decodeStream(input)?.asImageBitmap()
            }
        }.getOrNull()
    }
    if (bitmap == null) {
        Text(
            if (language == AppLanguage.TAMIL)
                "பாடத்திற்கான பென்சில் பாணி படக்கோப்பு இல்லை. மாற்றுப் படம் காட்டப்படாது."
            else "Lesson pencil-style raster plate missing or unreadable; no schematic substitute.",
            modifier = Modifier.fillMaxWidth().testTag("u2-lesson-raster-missing"),
            color = MaterialTheme.colorScheme.error
        )
        return
    }
    Image(
        bitmap = bitmap,
        contentDescription = when {
            sycon -> "Lesson raster illustration of Sycon external and canal-system anatomy"
            lifeHistory -> "Lesson raster illustration of Obelia life-history stages"
            else -> "Lesson raster illustration of Obelia colonial morphology"
        },
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(bitmap.width.toFloat() / bitmap.height)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .testTag(
                when {
                    sycon -> "u2-sycon-lesson-raster"
                    lifeHistory -> "u2-obelia-life-lesson-raster"
                    else -> "u2-obelia-colony-lesson-raster"
                }
            ),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
internal fun UnitIIImagePlate(
    sycon: Boolean,
    progress: Float,
    language: AppLanguage,
    onSelect: (Int) -> Unit
) {
    val ctx = LocalContext.current
    val lifeHistory = !sycon && progress.roundToInt() >= 3
    // A supplied PNG pencil plate takes priority over WebP. Both decode as
    // native Android bitmaps. No SVG, generated vector or Canvas fallback.
    val base = if (sycon) "sycon_plate"
               else if (lifeHistory) "obelia_lifecycle"
               else "obelia_colony"
    val available = remember(ctx) {
        runCatching { ctx.assets.list("unit2")?.toSet().orEmpty() }.getOrDefault(emptySet())
    }
    val asset = when {
        "$base.png" in available -> "unit2/$base.png"
        "$base.webp" in available -> "unit2/$base.webp"
        else -> "unit2/$base.png" // explicit missing raster error below
    }
    val bitmap = remember(ctx,asset) {
        runCatching {
            ctx.assets.open(asset).use { input ->
                BitmapFactory.decodeStream(input)?.asImageBitmap()
            }
        }.getOrNull()
    }
    if (bitmap == null) {
        Text(
            if (language == AppLanguage.TAMIL)
                "பொருத்தமான பென்சில் வரைபடக் கோப்பு இல்லை. மாற்றாக SVG அல்லது Canvas வரைபடம் காட்டப்படாது."
            else "Required labelled pencil plate is missing or unreadable. Raster-only rule: no SVG or Canvas substitute.",
            modifier=Modifier.fillMaxWidth().testTag("u2-atlas-asset-missing"),
            color=MaterialTheme.colorScheme.error,
            style=MaterialTheme.typography.bodySmall
        )
        return
    }

    val targets = if (sycon) syconTargets
                  else if (lifeHistory) obeliaLifeHistoryTargets else obeliaColonyTargets
    var scale by rememberSaveable(asset) { mutableFloatStateOf(1f) }
    var pan by remember(asset) { mutableStateOf(Offset.Zero) }
    var plateSize by remember(asset) { mutableStateOf(IntSize.Zero) }
    val transform = rememberTransformableState { zoomDelta,panDelta,_ ->
        val updated=(scale*zoomDelta).coerceIn(1f,4f)
        val limitX=(updated-1f)*plateSize.width*.5f
        val limitY=(updated-1f)*plateSize.height*.5f
        scale=updated
        pan=if(updated<=1f) Offset.Zero else Offset(
            (pan.x+panDelta.x).coerceIn(-limitX,limitX),
            (pan.y+panDelta.y).coerceIn(-limitY,limitY)
        )
    }
    Column(verticalArrangement=Arrangement.spacedBy(6.dp)) {
        Box(
            Modifier.fillMaxWidth()
                .aspectRatio(bitmap.width.toFloat()/bitmap.height)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .onSizeChanged { plateSize=it }
                .transformable(transform)
                .testTag(if(sycon)"u2-sycon-image-plate" else "u2-obelia-image-plate")
        ){
            Box(Modifier.fillMaxSize().graphicsLayer {
                scaleX=scale
                scaleY=scale
                translationX=pan.x
                translationY=pan.y
            }){
                Image(
                    bitmap=bitmap,
                    contentDescription=if(sycon) "Labelled Sycon pencil plate image"
                        else if(lifeHistory) "Labelled Obelia life-history pencil plate image"
                        else "Labelled Obelia colony pencil plate image",
                    modifier=Modifier.fillMaxSize().testTag("u2-raster-bitmap-visible"),
                    contentScale=ContentScale.FillBounds
                )
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
        // Visible, bilingual correction record for the still-unapproved
        // scanned master. Never imply that green UI tests approve anatomy.
        val provisionalNote = if (sycon) {
            if(language==AppLanguage.TAMIL)
                "படத் திருத்தம்: புரோசோபைல் என்பது உட்செல்லும் கால்வாயிலிருந்து ஆரக் கால்வாய்க்குச் செல்லும் திறப்பு. அப்போபைல் என்பது ஆரக் கால்வாயிலிருந்து ஸ்பாஞ்சோசீலுக்குச் செல்லும் திறப்பு; இவை கால்வாய்களின் பெயர்கள் அல்ல."
            else "Plate correction: a prosopyle is the ENTRY OPENING from incurrent to radial canal; an apopyle is the EXIT OPENING into the spongocoel. Neither is itself a canal."
        } else if (!lifeHistory) {
            if(language==AppLanguage.TAMIL)
                "படத் திருத்தம்: கோனோதீக்கா என்பது பாதுகாப்பு உறை. கோனாஞ்சியம் இனப்பெருக்க பாலிப்; அதன் பிளாஸ்டோஸ்டைலில் மெடூசா மொட்டுகள் தோன்றுகின்றன."
            else "Plate correction: gonotheca = protective sheath; gonangium = reproductive polyp containing a blastostyle and medusa buds."
        } else {
            if(language==AppLanguage.TAMIL)
                "மெடூசா இனச்செல்களால் பாலின இனப்பெருக்கம் செய்கிறது; கருவுற்ற முட்டை வளர்ந்து பிளானுலாவாகிறது."
            else "The free medusa produces gametes; fertilisation and embryonic development precede the swimming planula."
        }
        Text(provisionalNote,
            modifier=Modifier.fillMaxWidth().testTag("u2-anatomical-correction"),
            style=MaterialTheme.typography.bodySmall,
            color=MaterialTheme.colorScheme.onSurface)
        val zoomHint=if(language==AppLanguage.TAMIL)
            "இரு விரல்களால் பெரிதாக்கவும்; குறியிட்ட அமைப்பைத் தொடவும்"
        else "Pinch to zoom; touch a marked anatomical structure"
        // At 100% font scale, horizontal hint/reset placement squeezed the hint
        // below 45% of the viewport. A stacked layout remains legible at every
        // supported Android font scale and preserves the 48.dp reset target.
        Column(
            modifier=Modifier.fillMaxWidth(),
            verticalArrangement=Arrangement.spacedBy(8.dp)
        ) {
            Text(
                zoomHint,
                modifier=Modifier.fillMaxWidth().testTag("u2-zoom-hint"),
                style=MaterialTheme.typography.bodySmall
            )
            OutlinedButton(
                onClick={scale=1f;pan=Offset.Zero},
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("u2-reset-zoom")
            ) {
                Text(if(language==AppLanguage.TAMIL)"அளவை மீட்டமை" else "Reset zoom")
            }
        }
    }
}
