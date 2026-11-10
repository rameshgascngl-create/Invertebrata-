package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import com.gasczoology.invertebratelab.data.ParameciumProcess

/** Original bundled raster references. Playback is provided by the adjacent native demonstration. */
internal object ParameciumProcessPngIndex {
    val byProcess: Map<ParameciumProcess, String?> = mapOf(
        ParameciumProcess.CILIARY_MOTION to "unit1/concepts/paramecium_ciliary_locomotion",
        ParameciumProcess.OSMOREGULATION to "unit1/concepts/paramecium_osmoregulation",
        ParameciumProcess.FEEDING to "unit1/concepts/paramecium_nutrition",
        ParameciumProcess.BINARY_FISSION to null,
        ParameciumProcess.CONJUGATION to null,
    )
    init { check(byProcess.keys == ParameciumProcess.entries.toSet()) }
}

@Composable
internal fun ParameciumProcessPng(process: ParameciumProcess, language: AppLanguage) {
    val tamil = language == AppLanguage.TAMIL
    val stem = ParameciumProcessPngIndex.byProcess.getValue(process)
    val image = stem?.let { rememberRequiredRasterBitmap(it, pngOnly = true) }
    var expanded by rememberSaveable(process.name) { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth().testTag("u57-simulator-png-card")
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (image != null) {
                Image(
                    bitmap = image,
                    contentDescription = if (tamil)
                        "பாரமீசியத்தின் மூலப்படத்திலிருந்து எடுக்கப்பட்ட PNG பகுதி; மேலுள்ள இயக்கம் செயன்முறை விளக்க மாதிரி."
                    else "Paramecium reference PNG; use the playback controls above for the teaching model.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(image.width.toFloat()/image.height.toFloat())
                        .clickable(onClickLabel = if (tamil) "படத்தைப் பெரிதாக்கு" else "Enlarge image") {
                            expanded = true
                        }
                        .testTag("u57-paramecium-process-bitmap-" + process.name.lowercase()),
                )
                stem?.let { rememberPlateCredit(it) }?.let { credit ->
                    Text(credit.text(language), style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("plate-credit-process-" + process.name.lowercase()))
                }
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u58-open-process-image-" + process.name.lowercase()),
                ) {
                    Text(if (tamil) "படத்தை முழுத்திரையில் பெரிதாக்கு" else "View full-size source image")
                }
                if (expanded) {
                    RequiredTypeStudyFullImage(
                        image = image,
                        title = if (tamil) "பாரமீசியம் · உடற்கூறு ஆதாரப் படம்" else "Paramecium · source anatomy image",
                        language = language,
                        onDismiss = { expanded = false },
                    )
                }
                Text(
                    if (tamil)
                        "ஆதார PNG படம் நிலையானது; மேலுள்ள இயக்கக் கட்டுப்பாடுகள் செயன்முறை விளக்க மாதிரியை இயக்குகின்றன."
                    else "The source PNG is a still image. The controls above animate the separate teaching model.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.testTag("u57-static-png-disclosure"),
                )
            } else {
                Text(
                    if (tamil)
                        "இந்த செயலுக்கு தனிப் PNG படம் இல்லை. கீழே உள்ள செயல்நிலை விளக்கத்தையும் இயக்கக் கட்டுப்பாடுகளையும் பயன்படுத்துக."
                    else "No separate PNG is available for this process; use the interactive stages and explanations below.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag(
                        "u57-paramecium-process-png-missing-" + process.name.lowercase()),
                )
            }
        }
    }
}
