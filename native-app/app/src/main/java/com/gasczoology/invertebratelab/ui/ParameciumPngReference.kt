package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage

/**
 * R8: Only a real packaged PNG can occupy this Paramecium illustration slot.
 * There is intentionally no Canvas/SVG/ImageVector fallback and no image-space
 * hotspots. Source-backed figures currently available:
 *   external, oral, vacuole (photographed-source crops) and internal
 *   (historical reference crop pending independent source/biology clearance).
 *
 * This component depicts anatomical context. It NEVER claims a static image
 * shows a different cell-division or ciliary stage merely because stage
 * text/controls change.
 */
@Composable
internal fun ParameciumPngReference(
    panel: String,
    language: AppLanguage,
    modifier: Modifier = Modifier,
    contextOnly: Boolean = false,
) {
    require(panel in setOf("external", "oral", "vacuole", "internal"))
    val tamil = language == AppLanguage.TAMIL
    val bitmap = rememberRequiredRasterBitmap("unit1/paramecium_$panel", pngOnly = true)
    Column(modifier.fillMaxWidth().testTag("r8-paramecium-png-slot-$panel"),
        verticalArrangement = Arrangement.spacedBy(6.dp)) {
        if (bitmap == null) {
            Text(
                if (tamil) "பாரமீசியம் PNG படம் கிடைக்கவில்லை; தவறான கோட்டு வரைபடம் காட்டப்படவில்லை."
                else "Paramecium PNG unavailable; no substitute line drawing is displayed.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("r8-paramecium-png-missing-$panel"),
            )
        } else {
            Image(
                bitmap = bitmap,
                contentDescription = if (tamil)
                    "பாரமீசியம் PNG ஆதாரப்படம்: $panel. உறுப்பு இடம் அறிவியல் சரிபார்ப்பில் உள்ளது."
                else "Source-backed Paramecium PNG reference: $panel. Anatomy and labels await review.",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().heightIn(min = 230.dp)
                    .testTag("r8-paramecium-png-image-$panel"),
            )
        }
        if (contextOnly) {
            Text(
                if (tamil)
                    "இது நிலையான உடலமைப்புக் குறிப்பு படம் மட்டுமே; அடுத்தடுத்த செயல் நிலைகளின் தனித்தனி படங்கள் அல்ல."
                else "Static anatomical reference only; it is NOT a frame-by-frame representation of the selected physiological stage.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("r8-paramecium-not-stage-specific"),
            )
        }
        Text(
            if (tamil)
                "ஆதாரப் பென்சில் படத்தின் PNG நகல்; அறிவியல், உரிமம் மற்றும் தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது."
            else
                "PNG copy of a pencil-image reference; biology, source-rights and Tamil editorial clearance remain pending.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.testTag("r8-paramecium-png-provenance"),
        )
    }
}
