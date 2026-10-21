package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitVExistingFigure

/** Lazy-list image cards: decode only when an individual card is composed. */
@Composable
internal fun UnitVExistingFigureHeader(language: AppLanguage, count: Int) {
    Column(Modifier.fillMaxWidth().testTag("u5-existing-image-gallery"),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(if (language == AppLanguage.TAMIL)
            "ஒப்புதல் பெறாத படங்கள் · $count; பாடத்தில் காட்டப்படவில்லை"
            else "UNAPPROVED IMAGE ARCHIVE · $count withheld from lessons",
            style = MaterialTheme.typography.titleLarge)
        Text(if (language == AppLanguage.TAMIL)
            "மூலக் கோப்புகளின் படப்புள்ளிகள் மாற்றப்படவில்லை. இவற்றின் ஆங்கிலக் குறியீடுகள் சிலவற்றில் பிழைகள் உள்ளன. அறிவியல், தமிழ் மற்றும் பயன்பாட்டு உரிமை ஆய்வுகள் நிலுவையில் உள்ளன; கட்டாய உடற்கூறு படங்களுக்கான ஒப்புதல் அல்ல."
            else "These original repository PNGs are included without changing the pixels. Some image-internal English labels are incorrect. Scientific, Tamil and source-rights checks remain open; these are not accepted replacements for mandatory anatomical plates.",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag("u5-existing-image-disclaimer"))
    }
}

@Composable
internal fun UnitVExistingFigureCard(figure: UnitVExistingFigure, language: AppLanguage) {
    val bitmap = rememberRequiredRasterBitmap(figure.assetStem, pngOnly = true)
    var expanded by rememberSaveable(figure.assetStem) { mutableStateOf(false) }
    val caption = figure.caption.value(language)
    Card(Modifier.fillMaxWidth().testTag("u5-candidate-card-${figure.organism}-${figure.id}")) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(caption, style = MaterialTheme.typography.titleMedium)
            if (bitmap == null) {
                Text(if (language == AppLanguage.TAMIL) "படக் கோப்பு கிடைக்கவில்லை"
                     else "Source raster not bundled",
                    modifier = Modifier.testTag("u5-candidate-missing-${figure.organism}-${figure.id}"),
                    color = MaterialTheme.colorScheme.error)
            } else {
                Image(bitmap = bitmap,
                    contentDescription = caption,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio((bitmap.width.toFloat() / bitmap.height.toFloat()).coerceIn(0.6f, 2f))
                        .clickable { expanded = true }
                        .testTag("u5-candidate-bitmap-${figure.organism}-${figure.id}"))
                OutlinedButton(onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u5-candidate-expand-${figure.organism}-${figure.id}")) {
                    Text(if (language == AppLanguage.TAMIL) "படத்தைப் பெரிதாக்கு" else "Enlarge source image")
                }
                if (expanded) {
                    RequiredTypeStudyFullImage(bitmap, caption, language) { expanded = false }
                }
            }
            Text(figure.caution.value(language),
                modifier = Modifier.testTag("u5-candidate-caution-${figure.organism}-${figure.id}"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error)
        }
    }
}
