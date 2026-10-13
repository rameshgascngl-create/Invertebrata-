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
 * Pure, labelled source-image pane. No Canvas, SVG, gestures or touch overlays.
 * Historical scanned artworks and their original labels must not be passed off
 * as machine-drawn organism geometry; reviewer acceptance stays separate.
 */
@Composable
internal fun UnitIIStaticLessonPlate(
    relativeStem: String,
    language: AppLanguage,
    tag: String,
    captionEnglish: String,
    captionTamil: String,
) {
    val bitmap=rememberRequiredRasterBitmap(relativeStem,
        pngOnly=relativeStem.endsWith("_historical") ||
            relativeStem.contains("lifecycle") ||
            relativeStem.contains("settlement"))
    if (bitmap == null) {
        Text(
            if(language == AppLanguage.TAMIL)
                "உறுதிசெய்யப்பட்ட அசல் படக்கோப்பு இல்லை. மாற்றாக SVG/Canvas படம் காட்டப்படாது."
            else "Required historical lesson raster missing or unreadable. No SVG or Canvas substitute.",
            color=MaterialTheme.colorScheme.error,
            modifier=Modifier.testTag("u2-static-plate-missing"))
        return
    }
    Column(verticalArrangement=Arrangement.spacedBy(8.dp)) {
        Image(bitmap=bitmap, contentScale=ContentScale.FillWidth,
            contentDescription=if(language==AppLanguage.TAMIL) captionTamil else captionEnglish,
            modifier=Modifier.fillMaxWidth().testTag(tag))
        Text(if(language==AppLanguage.TAMIL) captionTamil else captionEnglish,
            style=MaterialTheme.typography.bodySmall)
    }
}
