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

/**
 * Unit I image-only display for the existing physiology stage selector.
 *
 * This intentionally avoids falsely animated anatomical coordinates.
 * Existing physiology slots use source-photo-derived crops; independent, scientifically validated concept masters remain pending.
 * The previous use of one photographed anatomy master for multiple processes
 * was misleading and is withdrawn. Fission and conjugation remain gated until
 * their own correctly staged scientific artwork is available.
 * No Canvas / SVG / vector drawing fallback.
 */
internal object ParameciumProcessPngIndex {
    val byProcess: Map<ParameciumProcess, String?> = mapOf(
        ParameciumProcess.CILIARY_MOTION to "unit1/paramecium_external",
        ParameciumProcess.OSMOREGULATION to "unit1/paramecium_vacuole",
        ParameciumProcess.FEEDING to "unit1/paramecium_oral",
        ParameciumProcess.BINARY_FISSION to "unit1/reference/paramecium_parker_fission_reference",
        ParameciumProcess.CONJUGATION to "unit1/reference/paramecium_conjugation_reference",
    )
    init { check(byProcess.keys == ParameciumProcess.entries.toSet()) }
}

@Composable
internal fun ParameciumProcessPng(process: ParameciumProcess, language: AppLanguage) {
    val tamil = language == AppLanguage.TAMIL
    val stem = ParameciumProcessPngIndex.byProcess.getValue(process)
    val image = stem?.let { rememberRequiredRasterBitmap(it, pngOnly = true) }
    val reproductive = process == ParameciumProcess.BINARY_FISSION || process == ParameciumProcess.CONJUGATION
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
                    contentDescription = if (reproductive) {
                        if (process == ParameciumProcess.BINARY_FISSION) {
                            if (tamil) "பாரமீசியம் குறுக்குப் பிளவு: பார்க்கர் வரலாற்றுப் படத்தில் D பகுதி; A–C இனப்பெருக்க நிலைகள் அல்ல." else "Historical Parker Paramecium plate: panel D shows transverse binary fission; A–C are not fission stages."
                        } else {
                            if (tamil) "பாரமீசியம் இணைவு: விளக்கப்படத் தொகுப்பு; நிலை எண்களையும் உட்கரு நிகழ்வுகளையும் பாடவிளக்கத்துடன் படிக்கவும்." else "Paramecium conjugation overview; follow the lesson text for nuclear chronology, not the image numbering alone."
                        }
                    } else if (tamil)
                        "பாரமீசியத்தின் பழைய பென்சில் ஓவியப் புகைப்படத்திலிருந்து பெறப்பட்ட PNG பகுதி; தனியாகச் சரிபார்க்கப்பட்ட உடற்கூறுப் படம் அல்ல."
                    else "Source-derived photographed pencil PNG crop; not an independently validated anatomical master.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(image.width.toFloat()/image.height.toFloat())
                        .clickable(onClickLabel = if (tamil) "படத்தைப் பெரிதாக்கு" else "Enlarge image") {
                            expanded = true
                        }
                        .testTag("u57-paramecium-process-bitmap-" + process.name.lowercase()),
                )
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
                        title = if (reproductive) {
                            if (process == ParameciumProcess.BINARY_FISSION) (if (tamil) "பாரமீசியம் · குறுக்குப் பிளவு" else "Paramecium · binary fission")
                            else (if (tamil) "பாரமீசியம் · இணைவு" else "Paramecium · conjugation")
                        } else if (tamil) "பாரமீசியம் · உடற்கூறுப் படம்" else "Paramecium · anatomy PNG",
                        language = language,
                        onDismiss = { expanded = false },
                    )
                }
                Text(
                    if (tamil)
                        "மூல பென்சில் ஓவியப் புகைப்படத்திலிருந்து பெறப்பட்ட PNG பகுதி; தனியான அறிவியல் சான்றுபெற்ற படம் அல்ல. பெரிதாக்குவதால் புதிய உடற்கூறு விவரங்கள் கிடைக்காது."
                    else "Source-derived PNG crop, not an independently verified anatomical plate. Enlargement cannot recover missing source detail; the stage explanations are textual. Tamil review remains pending.",
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
