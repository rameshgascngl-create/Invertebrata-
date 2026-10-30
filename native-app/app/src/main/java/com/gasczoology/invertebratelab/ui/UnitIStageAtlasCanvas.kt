package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.StageAtlasPlate
import com.gasczoology.invertebratelab.data.UnitIStageAtlas

/**
 * U1.15: ORIGINAL PROGRAMMATIC *DIGITAL* graphite-toned concepts in native
 * Android Canvas. NOT physically pencil-drawn, photographs, diagnostic
 * reference specimens or source-verified anatomical plates.
 *
 * Structure selection comes from explicit buttons, not unreviewed
 * source-raster hotspots. No source image is substituted.
 */
@Composable
internal fun UnitIStageAtlasChapter(language: AppLanguage) {
    val tamil = language == AppLanguage.TAMIL
    val context = LocalContext.current
    val narrator = remember(context) { OfflineNarrator(context.applicationContext) }
    val narratorPhase by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }

    var plateId by rememberSaveable { mutableStateOf(UnitIStageAtlas.plates.first().id) }
    val plate = requireNotNull(UnitIStageAtlas.get(plateId))
    var structureId by rememberSaveable(plateId) {
        mutableStateOf(plate.structures.first().id)
    }
    val structure = plate.structures.firstOrNull { it.id == structureId }
        ?: plate.structures.first()
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(plateId, structureId, language) { narrator.stop() }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("u115-morphotype-atlas"),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Text(
                if (tamil) "கசையிழை ஒட்டுண்ணிகள் — PNG ஆதாரப் படங்கள்"
                else "Flagellate morphology — PNG references",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("u115-heading").semantics { heading() },
            )
            Text(
                if (tamil)
                    "இவை PNG ஆதாரப் படங்கள்; உறுதிசெய்யப்பட்ட உடற்கூறு வரைபடங்களோ தொடுபுள்ளிகளோ அல்ல."
                else
                    "PNG source reference only; not an approved anatomical master or calibrated image hotspot. Different life-cycle stages must not be interchanged.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u115-digital-artwork-warning"),
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().testTag("u115-stage-picker"),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(UnitIStageAtlas.plates, key = { it.id }) { option ->
                    FilterChip(
                        selected = option.id == plateId,
                        onClick = { plateId = option.id },
                        label = { Text(if (tamil) option.taxonTa else option.taxonEn) },
                        modifier = Modifier.heightIn(min = 48.dp)
                            .testTag("u115-stage-" + option.id),
                    )
                }
            }
            Text(
                if (tamil) plate.taxonTa else plate.taxonEn,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("u115-current-stage"),
            )
            UnitIStagePng(plate.id, language, Modifier.fillMaxWidth().height(255.dp).testTag("u115-concept-canvas"))
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("u115-enlarge"),
            ) { Text(if (tamil) "PNG படத்தைப் பெரிதாக்கு" else "Enlarge PNG reference") }
            Text(
                if (tamil) plate.contextTa else plate.contextEn,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("u115-stage-explanation"),
            )
            Text(
                if (tamil) "அமைப்பை விளக்கமாகத் தேர்ந்தெடு — படத்தைத் தொடும் மருத்துவக் குறியீடு அல்ல."
                else "Select a named structure for teaching emphasis; these are not calibrated source-image touch hotspots.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().testTag("u115-structure-picker"),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(plate.structures, key = { it.id }) { item ->
                    FilterChip(
                        selected = item.id == structure.id,
                        onClick = { structureId = item.id },
                        label = { Text(if (tamil) item.nameTa else item.nameEn) },
                        modifier = Modifier.heightIn(min = 48.dp)
                            .testTag("u115-organ-" + item.id),
                    )
                }
            }
            Text(
                if (tamil) structure.nameTa else structure.nameEn,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("u115-organ-name"),
            )
            Text(
                if (tamil) structure.explanationTa else structure.explanationEn,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("u115-organ-explanation"),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        narrator.speak(
                            BilingualText(
                                structure.nameEn + ". " + structure.explanationEn,
                                structure.nameTa + ". " + structure.explanationTa,
                            ),
                            language,
                        )
                    },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u115-listen"),
                ) {
                    Text(if (tamil) "விளக்கத்தைக் கேள்" else "Hear explanation")
                }
                OutlinedButton(
                    onClick = { narrator.stop() },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u115-stop-audio"),
                ) {
                    Text(if (tamil) "நிறுத்து" else "Stop narration")
                }
            }
            Text(
                (if (tamil) "சாதனத்தின் ஆஃப்லைன் குரல் நிலை: "
                 else "Device offline speech: ") + narratorPhase.name,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.testTag("u115-narrator-status"),
            )
            Text(
                (if (tamil) "உயிரியல் ஆதாரம்: " else "Biological reference: ") +
                    plate.source,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Surface(modifier = Modifier.fillMaxSize().testTag("u115-expanded")) {
                Column(
                    modifier = Modifier.fillMaxSize().statusBarsPadding()
                        .navigationBarsPadding().padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        if (tamil) plate.taxonTa else plate.taxonEn,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        if (tamil) "PNG ஆதாரப் படம்; கல்வி மதிப்பாய்வு நிலுவையில் உள்ளது"
                        else "PNG REFERENCE — ACADEMIC REVIEW PENDING",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge,
                    )
                    UnitIStagePng(plate.id, language, Modifier.fillMaxWidth().weight(1f).testTag("u115-expanded-canvas"))
                    Text(
                        if (tamil) structure.explanationTa else structure.explanationEn,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Button(
                        onClick = { expanded = false },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u115-expanded-close"),
                    ) { Text(if (tamil) "மூடு" else "Close") }
                }
            }
        }
    }
}

@Composable
private fun UnitIStagePng(stage: String, language: AppLanguage, modifier: Modifier) {
    val stem = when (stage) {
        "brucei-blood" -> "unit1/references/trypanosoma"
        "leishmania-amastigote" -> "unit1/references/leishmania"
        else -> null
    }
    val bitmap = stem?.let { rememberRequiredRasterBitmap(it, pngOnly = true) }
    if (bitmap != null) {
        Image(bitmap = bitmap, contentDescription = "Source PNG reference; morphology review pending",
            contentScale = ContentScale.Fit, modifier = modifier.testTag("u115-png-reference"))
    } else {
        Text(if (language == AppLanguage.TAMIL) "இந்த வளர்ச்சி நிலைக்கான தனிப் PNG படம் இன்னும் சரிபார்க்கப்படவில்லை"
            else "Stage-specific PNG not yet verified; no substitute drawing",
            color = MaterialTheme.colorScheme.error, modifier = modifier.testTag("u115-png-pending"))
    }
}

