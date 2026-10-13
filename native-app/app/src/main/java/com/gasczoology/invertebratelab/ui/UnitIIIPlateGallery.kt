package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIIIPlateCatalog

/**
 * Honest native art slots: a PNG/WebP raster is shown if actually present.
 * Otherwise the student sees a bilingual editorial HOLD, never fake anatomy.
 * Individual hotspot endpoints require independent anatomical sign-off.
 */
@Composable
internal fun UnitIIIPlateGallery(chapterId: String, language: AppLanguage) {
    val plates = UnitIIIPlateCatalog.forChapter(chapterId)
    if (plates.isEmpty()) return
    val tamil = language == AppLanguage.TAMIL

    Column(
        modifier = Modifier.fillMaxWidth().testTag("u3-required-plate-gallery"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            if (tamil) "தேவையான உடற்கூறுப் படங்கள் — அறிவியல் சரிபார்ப்பு நிலுவையில்"
            else "REQUIRED ANATOMICAL PLATES · scientific review pending",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { heading() }
        )
        Text(
            if (tamil) "வரலாற்று மூலப்படங்கள் தனியே வைக்கப்பட்டுள்ளன. கீழுள்ள புதிய வரைவுகளுக்கு உடற்கூறு ஒப்புதல் இன்னும் வழங்கப்படவில்லை."
            else "Historical reference artwork is separate. These are the six required replacement slots; bitmap presence does not imply anatomical acceptance.",
            style = MaterialTheme.typography.bodySmall
        )
        plates.forEach { plate ->
            Card(Modifier.fillMaxWidth().testTag("u3-plate-slot-" + plate.plateId)) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(plate.title.value(language), style = MaterialTheme.typography.titleMedium)
                    val raster = rememberRequiredRasterBitmap(plate.assetStem)
                    if (raster == null) {
                        Text(
                            if (tamil) "உறுதிசெய்யப்பட்ட பென்சில் படமில்லை — தற்காலிகப் படம் சேர்க்கப்படாது."
                            else "Corrected pencil master pending — no substitute diagram displayed.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("u3-plate-pending-" + plate.plateId)
                        )
                    } else {
                        Image(
                            bitmap = raster,
                            contentDescription = plate.title.value(language),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                                .testTag("u3-plate-bitmap-" + plate.plateId)
                        )
                        Text(
                            if (tamil) "வரைவுப் படம் — உயிரியல் மற்றும் தொடுதிறன் பரிசோதனைக்கு பின் மட்டுமே ஒப்புதல்."
                            else "Candidate raster only — anatomy and touch targets still require verification.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(plate.learningAim.value(language), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
