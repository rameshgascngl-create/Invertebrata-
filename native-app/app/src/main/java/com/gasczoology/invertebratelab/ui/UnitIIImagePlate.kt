package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.testTag
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
 * Image-only academic rule: no Canvas overlays, drawn markers or anatomical taps.
 * Students select Sycon/Obelia stages using accessible native lesson buttons.
 * Raster zoom/pan is permitted and remains independent of lesson navigation.
 */
@Composable
internal fun UnitIIImagePlate(
    sycon: Boolean,
    progress: Float,
    language: AppLanguage
) {
    val lifeHistory = !sycon && progress.roundToInt() >= 3
    val settlement = !sycon && progress.roundToInt() >= 5
    val stem = if (sycon) "unit2/sycon_plate"
               else if (settlement) "unit2/obelia_settlement"
               else if (lifeHistory) "unit2/obelia_lifecycle"
               else "unit2/obelia_colony"
    // Only the source-linked raster is displayed. No drawn markers,
    // artificial anatomical tap targets, Canvas, SVG or vector substitutes.
    val bitmap = rememberRequiredRasterBitmap(stem, pngOnly=lifeHistory)
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

    var scale by rememberSaveable(stem) { mutableFloatStateOf(1f) }
    var pan by remember(stem) { mutableStateOf(Offset.Zero) }
    var plateSize by remember(stem) { mutableStateOf(IntSize.Zero) }
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
                        else if(settlement) "Obelia labelled planula attachment and primary polyp pencil drawing"
                        else if(lifeHistory) "Labelled Obelia life-history pencil plate image"
                        else "Labelled Obelia colony pencil plate image",
                    modifier=Modifier.fillMaxSize().testTag("u2-raster-bitmap-visible"),
                    contentScale=ContentScale.FillBounds
                )

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
        } else if(settlement) {
            if(language==AppLanguage.TAMIL)
                "பிளானுலா அடித்தளத்தில் ஒட்டிக் கொண்டு உருமாற்றம் அடைகிறது. முதல் உணவூட்டும் பாலிப்பில் உணர்நீட்சிகள் தோன்றுகின்றன; ஹைட்ரோரிசா வளர்ந்து புதிய கூட்டை உருவாக்குகிறது."
            else "The ciliated planula attaches to the substratum and metamorphoses into a primary feeding polyp. A basal stolon (hydrorhiza) develops, followed by colony growth."
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
            "படத்தை இரு விரல்களால் பெரிதாக்கலாம். உறுப்புப் படிநிலைகளை கீழே உள்ள பொத்தான்களில் தேர்ந்தெடுக்கவும்."
        else "Pinch to enlarge the static image. Select anatomical stages using the buttons below."
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
