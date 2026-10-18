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
import com.gasczoology.invertebratelab.data.ParameciumProcess

/**
 * Unit I image-only display for the existing physiology stage selector.
 *
 * This intentionally avoids falsely animated anatomical coordinates.
 * PNGs for 3 processes are decoded from the checked-in, offline Android
 * assets. Binary fission and conjugation need their OWN verified anatomical
 * images; a cilia/food-vacuole crop is NOT a reproduction diagram.
 * No Canvas / SVG / vector drawing fallback.
 */
internal object ParameciumProcessPngIndex {
    val byProcess: Map<ParameciumProcess, String?> = mapOf(
        ParameciumProcess.CILIARY_MOTION to "unit1/paramecium_external",
        ParameciumProcess.OSMOREGULATION to "unit1/paramecium_vacuole",
        ParameciumProcess.FEEDING to "unit1/paramecium_oral",
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
                        "பாரமீசியத்தின் அசல் மூலப் புகைப்படத்திலிருந்து பிரிக்கப்பட்ட நிலையான PNG படம். செயல்நிலைகள் கீழே விளக்கப்படுகின்றன."
                    else "Static source-derived Paramecium PNG. Physiology stages are explained in text and are NOT animated on the image.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(image.width.toFloat()/image.height.toFloat())
                        .testTag("u57-paramecium-process-bitmap-" + process.name.lowercase()),
                )
                Text(
                    if (tamil)
                        "மூலப் படத்திலிருந்து பெறப்பட்ட நிலையான PNG. இது மாற்றம் பெறும் உயிரியல் இயக்கக் காட்சி அல்ல; கீழுள்ள நிலைகளின் விளக்கத்தைப் படிக்கவும்."
                    else "Fixed source-derived PNG; changing stages updates the explanation, not the image. Morphology and Tamil review pending.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.testTag("u57-static-png-disclosure"),
                )
            } else {
                Text(
                    if (tamil)
                        "இந்த செயலுக்கான தனிப்பட்ட, அறிவியல் சரிபார்க்கப்பட்ட PNG படம் இன்னும் சேர்க்கப்படவில்லை. தவறான வரைபடம் பயன்படுத்தப்படாது; செயல்நிலை விளக்கம் கீழே உள்ளது."
                    else "A dedicated, scientifically reviewed PNG for this process is not yet bundled. No unrelated image or vector substitute is shown; follow the stage explanation below.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag(
                        "u57-paramecium-process-png-missing-" + process.name.lowercase()),
                )
            }
        }
    }
}
