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
 * All processes require subject-specific PNGs. The previous reuse of one fuzzy
 * photographed anatomy master was misleading and is withdrawn. One source-credited
 * conjugation overview is displayed as an UNAPPROVED editorial candidate; its
 * nine schematic steps are not the app's research-stage sequence. Binary fission
 * remains blocked until a dedicated, scientifically checked plate exists.
 * No Canvas / SVG / vector drawing fallback.
 */
internal object ParameciumProcessPngIndex {
    val byProcess: Map<ParameciumProcess, String?> = mapOf(
        ParameciumProcess.CILIARY_MOTION to "unit1/concepts/paramecium_ciliary_locomotion",
        ParameciumProcess.OSMOREGULATION to "unit1/concepts/paramecium_osmoregulation",
        ParameciumProcess.FEEDING to "unit1/concepts/paramecium_nutrition",
        ParameciumProcess.BINARY_FISSION to null,
        ParameciumProcess.CONJUGATION to "unit1/concepts/paramecium_conjugation",
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
                        "பாரமீசியத்தின் தனித்த செயல்முறை PNG படம்; உறுப்புத் தொடுதல் மற்றும் செயல்பாட்டு விளக்கம் மதிப்பாய்வு நிலுவையில் உள்ளன."
                    else "Independent Paramecium process PNG. Stages are explained in text; no animation or unverified image hotspots.",
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
                        title = if (tamil) "பாரமீசியம் · தனிப்படம்" else "Paramecium · concept PNG",
                        language = language,
                        onDismiss = { expanded = false },
                    )
                }
                if (process == ParameciumProcess.CONJUGATION) {
                    Text(
                        if (tamil)
                            "ஆதாரப்படம்: Deuterostome, Wikimedia Commons, CC BY-SA 4.0. ஒன்பது படிநிலைகள் கொண்ட ஆங்கில விளக்கப்படம்; கீழுள்ள ஆய்வுப் படிநிலைகளுடன் நேரடியாக ஒத்திசைக்கப்படவில்லை. உடற்கூறு மற்றும் தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது."
                        else "Source: Deuterostome, Wikimedia Commons, CC BY-SA 4.0. This nine-stage English overview is NOT synchronized with the research-stage selector below. Zoology and Tamil approval remain pending.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("u62-conjugation-licence-and-stage-warning"),
                    )
                }
                Text(
                    if (tamil)
                        "தனித்த செயல்முறை PNG; இதுவொரு இயக்கப்படமல்ல. செயல்நிலை விளக்கத்தைத் தனியாகப் படிக்கவும்."
                    else "Process-specific PNG; stage changes update the explanation without pretending to animate organ movement. Tamil review remains pending.",
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
