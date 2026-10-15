package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.*

/**
 * Four visible Unit I anatomical views are decoded PNGs, never Canvas
 * redraws. Historic source labels require expert confirmation.
 * Image-space hotspots are disabled until independently calibrated.
 */
@Composable
internal fun ParameciumRasterAtlas(
    language: AppLanguage, panel: String, selected: String,
    onSelect: (String) -> Unit, speak: (BilingualText) -> Unit,
    onExploreOsmoregulation: () -> Unit
) {
    val tamil=language==AppLanguage.TAMIL
    val ids=when(panel) {
        "external" -> listOf("pellicle","somatic-cilia","oral-groove","cytoproct","trichocysts")
        "oral" -> listOf("oral-groove","food-vacuole","cytoproct")
        "vacuole" -> listOf("contractile-vacuole","food-vacuole")
        else -> listOf("macronucleus","micronucleus","trichocysts","food-vacuole")
    }
    val bitmap=rememberRequiredRasterBitmap("unit1/paramecium_"+panel)
    var zoom by rememberSaveable(panel){mutableFloatStateOf(1f)}
    var dx by rememberSaveable(panel){mutableFloatStateOf(0f)}
    var dy by rememberSaveable(panel){mutableFloatStateOf(0f)}
    if(panel=="vacuole") Text(
        if(tamil) "நுண்குமிழ்கள்: தனித்த உடலியக்க அமைப்புகள்"
        else "Vacuoles: distinct physiological systems",
        modifier=Modifier.testTag("r11-vacuole-heading"))
    if(bitmap==null) {
        Text(if(tamil) "பென்சில் வரைபடக் கோப்பு இல்லை; மாற்று வரைபடம் காட்டப்படாது."
             else "Pencil raster master missing; no schematic fallback.",
            color=MaterialTheme.colorScheme.error,
            modifier=Modifier.testTag("u1-raster-asset-missing"))
    } else {
        Image(bitmap=bitmap,
            contentDescription=if(tamil) "பாரமீசியம் கைவரைவு: நேரடித் தொடுதல் மதிப்பாய்வில் உள்ளது"
                 else "Original hand-drawn Paramecium image; direct organ taps await scientific review",
            contentScale=ContentScale.Fit,
            modifier=Modifier.fillMaxWidth().height(282.dp)
                .graphicsLayer {
                    scaleX=zoom;scaleY=zoom;translationX=dx;translationY=dy
                }
                .pointerInput(panel) {
                    detectTransformGestures { _, pan, gestureZoom, _ ->
                        zoom=(zoom*gestureZoom).coerceIn(1f,4f)
                        if(zoom<=1.01f){dx=0f;dy=0f}
                        else {
                            dx=(dx+pan.x).coerceIn(-size.width.toFloat(),size.width.toFloat())
                            dy=(dy+pan.y).coerceIn(-size.height.toFloat(),size.height.toFloat())
                        }
                    }
                }
                .testTag("u1-paramecium-raster-"+panel))
        OutlinedButton(onClick={zoom=1f;dx=0f;dy=0f},
            modifier=Modifier.heightIn(min=48.dp).testTag("u1-raster-reset-zoom")) {
            Text(if(tamil) "பெரிதாக்கலை மீட்டமை" else "Reset zoom")
        }
    }
    Text(if(tamil) "மூலம்: Rijan Bhandari (2014), அசல் கைவரைவு, CC BY-SA 3.0. அறிவியல் ஆய்வு நிலுவையில்."
         else "Rijan Bhandari (2014), original photographed pencil drawing, CC BY-SA 3.0. Scientific review pending.",
        style=MaterialTheme.typography.bodySmall,
        modifier=Modifier.testTag("u1-raster-attribution"))
    Text(if(tamil) "உறுப்பைத் தேர்ந்தெடுக்கவும்; உறுதி செய்யப்படாத படத் தொடுபுள்ளிகள் முடக்கப்பட்டுள்ளன."
         else "Select an organ below; unverified image hotspots remain disabled.",
         style=MaterialTheme.typography.bodySmall)
    ids.forEach { id ->
        val organ=ParameciumLearningEngine.organ(id)
        OutlinedButton(onClick={onSelect(id)},
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                .testTag("r1-atlas-select-"+id)) {
            Text(organ.name.value(language))
        }
    }
    if(selected in ids) {
        val organ=ParameciumLearningEngine.organ(selected)
        Text(organ.name.value(language),modifier=Modifier.testTag("r1-atlas-selected"))
        Text(organ.narration.value(language),modifier=Modifier.testTag("r1-atlas-explanation"))
        OutlinedButton(onClick={speak(organ.narration)},
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r1-atlas-speak")) {
            Text(if(tamil) "இந்த உறுப்பின் விளக்கத்தைக் கேள்" else "Hear this organ")
        }
    }
    if(panel=="vacuole") {
        OutlinedButton(onClick=onExploreOsmoregulation,
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                .testTag("r1-atlas-explore-osmoregulation")) {
            Text(if(tamil) "சவ்வூடுபரவல் ஒழுங்குமுறையை ஆராய்க" else "Explore osmoregulation")
        }
    }
}
