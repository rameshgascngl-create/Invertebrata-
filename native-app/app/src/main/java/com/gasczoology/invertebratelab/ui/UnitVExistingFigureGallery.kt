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
internal fun UnitVExistingFigureHeader(language: AppLanguage, count: Int, publishedCount: Int) {
    val withheldCount = count - publishedCount
    Column(Modifier.fillMaxWidth().testTag("u5-existing-image-gallery"),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(if (language == AppLanguage.TAMIL)
            "துணைப் படங்கள்: ${publishedCount} காட்டப்படுகின்றன; ${withheldCount} நிறுத்தப்பட்டுள்ளன"
            else "EXISTING FIGURES · ${publishedCount} supplementary; ${withheldCount} withheld",
            style = MaterialTheme.typography.titleLarge)
        Text(
            if (publishedCount > 0) {
                if (language == AppLanguage.TAMIL)
                    "ஓடு, ஓடுமூடி பற்றிய மூன்று பழைய திட்டப் படங்கள் மட்டும் துணை விளக்கத்திற்காகக் காட்டப்படுகின்றன. மூலப்படங்களின் சொற்களில் உள்ள குறைகளை அருகிலுள்ள தமிழ்க் குறிப்புகள் விளக்குகின்றன. இவை முழுமையாகச் சரிபார்க்கப்பட்ட உடற்கூறு மாதிரிப் படங்கள் அல்ல; மூலத் தெளிவுத்திறன் 384 × 341 மட்டுமே."
                else "Only three existing shell/operculum illustrations are shown as supplementary references. The bilingual notes clarify source-internal orientation terms. They are not approved anatomical master plates; source resolution is only 384 × 341 pixels."
            } else {
                if (language == AppLanguage.TAMIL)
                    "இப்பகுதியின் பழைய படங்கள் உடற்கூறு, குறியீடு மற்றும் மூல உரிமை ஆய்வு முடிவடையாததால் பாடத்தில் காட்டப்படவில்லை. கட்டாய மாதிரிப் படங்களுக்கான ஒப்புதல் இன்னும் நிலுவையில் உள்ளது."
                else "Existing figures for this chapter remain withheld pending anatomical, labelling and provenance review. Mandatory type-study plates are not approved."
            },
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
            Text(figure.provenance.value(language),
                modifier = Modifier.testTag("u5-figure-source-${figure.organism}-${figure.id}"),
                style = MaterialTheme.typography.bodySmall)
            Text(figure.caution.value(language),
                modifier = Modifier.testTag("u5-candidate-caution-${figure.organism}-${figure.id}"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error)
        }
    }
}
