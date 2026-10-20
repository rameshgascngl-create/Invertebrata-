package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.TypeStudyRequirement
import com.gasczoology.invertebratelab.data.BilingualText
import kotlin.math.roundToInt

/**
 * Provisional college-practical terminology for ALL 35 type-study slots.
 * These strings are labels OUTSIDE images: never burned onto drawing pixels.
 * Zoology and Tamil editorial review remains mandatory before approval.
 */
internal object RestoredPlateTitles {
    val byId: Map<String, BilingualText> = mapOf(
        "paramecium/external-cilia" to BilingualText("External morphology and cilia", "புறத்தோற்றமும் குறுஇழைகளும்"),
        "paramecium/oral-apparatus" to BilingualText("Oral apparatus", "வாயுறுப்பு அமைப்பு"),
        "paramecium/contractile-vacuoles" to BilingualText("Contractile vacuoles", "சுருங்கும் நுண்குமிழ்கள்"),
        "paramecium/locomotion" to BilingualText("Ciliary locomotion", "குறுஇழை இயக்கம்"),
        "sycon/external" to BilingualText("External morphology", "புறத்தோற்றம்"),
        "sycon/canal-system" to BilingualText("Canal system (longitudinal section)", "நீர்க்கால்வாய் அமைப்பு (நீளவெட்டு)"),
        "sycon/reproduction" to BilingualText("Reproduction and amphiblastula larva", "இனப்பெருக்கமும் ஆம்பிபிளாஸ்டுலா இளநிலையும்"),
        "obelia/colony" to BilingualText("Colony morphology", "கூட்டு அமைப்பின் புறத்தோற்றம்"),
        "obelia/hydranth-gonangium" to BilingualText("Hydranth and gonangium", "ஹைட்ராந்தும் கோனாங்கியமும்"),
        "obelia/medusa" to BilingualText("Medusa anatomy", "மெடூசா உடற்கூறு"),
        "obelia/life-cycle" to BilingualText("Life cycle", "வாழ்க்கைச் சுழற்சி"),
        "fasciola/external" to BilingualText("External morphology and suckers", "புறத்தோற்றமும் உறிஞ்சிகளும்"),
        "fasciola/digestive-reproductive" to BilingualText("Digestive and reproductive systems", "செரிமான மற்றும் இனப்பெருக்க அமைப்புகள்"),
        "fasciola/life-cycle" to BilingualText("Life cycle and snail intermediate host", "வாழ்க்கைச் சுழற்சியும் நத்தை இடைப்புரவலனும்"),
        "ascaris/male-female-external" to BilingualText("Male and female external morphology", "ஆண்–பெண் புறத்தோற்றம்"),
        "ascaris/female-internal" to BilingualText("Female internal anatomy", "பெண் உள் உடற்கூறு"),
        "ascaris/life-cycle" to BilingualText("Egg development and larval migration", "முட்டை வளர்ச்சியும் இளம்புழு இடம்பெயர்ச்சியும்"),
        "earthworm/external-setae" to BilingualText("External morphology and setae", "புறத்தோற்றமும் சீட்டாக்களும்"),
        "earthworm/digestive" to BilingualText("Digestive system", "செரிமான மண்டலம்"),
        "earthworm/circulatory" to BilingualText("Blood vascular system", "இரத்தச் சுற்றோட்ட மண்டலம்"),
        "earthworm/nervous" to BilingualText("Nervous system", "நரம்பு மண்டலம்"),
        "earthworm/excretory" to BilingualText("Excretory system and nephridia", "கழிவுநீக்க அமைப்பும் நெஃப்ரிடியாக்களும்"),
        "earthworm/reproductive" to BilingualText("Reproductive system", "இனப்பெருக்க மண்டலம்"),
        "penaeus/external" to BilingualText("External morphology", "புறத்தோற்றம்"),
        "penaeus/appendages" to BilingualText("Segmental appendages", "கண்டவாரியான இணைக்கால்கள்"),
        "penaeus/internal" to BilingualText("Internal organ systems", "உள் உறுப்பு மண்டலங்கள்"),
        "penaeus/larval-development" to BilingualText("Larval development", "இளம் நிலை வளர்ச்சி"),
        "pila/external" to BilingualText("Shell and external morphology", "ஓடும் புறத்தோற்றமும்"),
        "pila/mantle-cavity" to BilingualText("Mantle cavity", "மேன்டில் அறை"),
        "pila/ctenidium" to BilingualText("Ctenidium and osphradium", "சீனிடியமும் ஆஸ்ப்ரேடியமும்"),
        "pila/torsion" to BilingualText("Gastropod torsion", "வயிற்றுக்காலிகளில் முறுக்கம்"),
        "asterias/external" to BilingualText("Oral and aboral external morphology", "வாய்ப்புற மற்றும் எதிர்வாய்ப்புற புறத்தோற்றம்"),
        "asterias/water-vascular-system" to BilingualText("Water-vascular system", "நீர்க்குழாய் மண்டலம்"),
        "asterias/internal" to BilingualText("Digestive and reproductive anatomy", "செரிமான மற்றும் இனப்பெருக்க உடற்கூறு"),
        "asterias/larval-development" to BilingualText("Bipinnaria and brachiolaria larvae", "பைபின்னாரியா மற்றும் பிராக்கியோலாரியா இளநிலைகள்")
    )
    init {
        val required = com.gasczoology.invertebratelab.data.NativeCurriculumManifest.typeStudies
            .flatMap { study -> study.requiredPlateIds.map { study.organismId + "/" + it } }
            .toSet()
        check(byId.keys == required) { "Type study English–Tamil label inventory diverged from required raster masters" }
    }
    fun value(organism: String, plate: String, language: AppLanguage): String =
        requireNotNull(byId[organism + "/" + plate]).value(language)
}


/**
 * R2: fail-visible, raster-only textbook plate gallery for the nine required
 * organism type studies. No anatomical Canvas, SVG or placeholder sketches.
 *
 * Missing masters are missing academic content, not a green release gate.
 * Images present are *candidates* until scientific, rights and Tamil review.
 * The bitmap itself is never relabelled by guessed image coordinates.
 */
@Composable
internal fun RequiredTypeStudyPlates(
    study: TypeStudyRequirement,
    language: AppLanguage,
) {
    val tamil = language == AppLanguage.TAMIL
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth().testTag("r2-type-study-gallery-" + study.organismId),
    ) {
        Text(
            if (tamil) "உடற்கூறு ஆதாரப் படங்கள் · ${study.scientificName}"
            else "REQUIRED ANATOMICAL IMAGE PLATES · ${study.scientificName}",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            if (tamil) "இவை தனித்த படக்கோப்புகளாக இணைக்கப்படுகின்றன. சிலவை வரலாற்று மை/வண்ணப் படங்கள்; அசல் பென்சில் ஸ்கேன்கள் அல்ல. உடற்கூறு, உரிமை, தமிழ் குறியீடுகள் ஆய்வு நிலுவையில் உள்ளன."
            else "Each figure is a real bundled raster, if present. Historical ink and colour references are NOT authentic pencil scans; morphology, rights and Tamil terminology still require verification.",
            style = MaterialTheme.typography.bodyMedium,
        )
        if (study.organismId == "paramecium") {
            Text(
                if (tamil) "பழைய ஒரு புகைப்பட வரைபடத்தைப் பல உறுப்புப் படங்களாக மீண்டும் பயன்படுத்துவது நிறுத்தப்பட்டுள்ளது. வெளிப்புறம், வாயமைப்பு, சுருங்கும் நுண்குமிழ், இயக்கம் ஆகிய நான்கிற்கும் தனித்த சரிபார்க்கப்பட்ட PNG கோப்புகள் தேவை."
                else "Retired the old photographed pencil drawing and its repeated crops. External cilia, oral apparatus, contractile vacuoles and locomotion each require a distinct verified replacement PNG.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("r6-single-source-disclosure"),
            )
        }
        // Editorial reference viewer is deliberately outside the mandatory academic slots.
        // These files must never satisfy the five-unit release inventory.
        if (study.organismId == "pila") {
            val reviewViews = listOf(
                "01_shell_ventral_REVIEW" to ("Shell — ventral view" to "ஓடு — அடிப்புறத் தோற்றம்"),
                "02_operculum_views_REVIEW" to ("Operculum — two surfaces" to "ஓட்டுமூடி — இரு புறத் தோற்றங்கள்"),
                "03_shell_dorsal_REVIEW" to ("Shell — dorsal view" to "ஓடு — முதுகுப்புறத் தோற்றம்"),
                "04_animal_front_REVIEW" to ("Animal — front view" to "உயிரி — முன்புறத் தோற்றம்"),
                "05_nervous_system_REVIEW" to ("Nervous system — unverified" to "நரம்பு மண்டலம் — சரிபார்க்கப்படவில்லை"),
                "06_buccal_mass_REVIEW" to ("Buccal mass — unverified" to "வாய்த் தசைத்தொகுதி — சரிபார்க்கப்படவில்லை"),
                "07_digestive_system_REVIEW" to ("Digestive system — unverified" to "செரிமான மண்டலம் — சரிபார்க்கப்படவில்லை"),
                "08_respiratory_system_REVIEW" to ("Respiratory system — unverified" to "சுவாச மண்டலம் — சரிபார்க்கப்படவில்லை"),
                "09_circulatory_system_REVIEW" to ("Circulatory system — unverified" to "இரத்த ஓட்ட மண்டலம் — சரிபார்க்கப்படவில்லை"),
                "10_male_reproductive_REVIEW" to ("Male reproductive system — unverified" to "ஆண் இனப்பெருக்க மண்டலம் — சரிபார்க்கப்படவில்லை"),
                "11_female_reproductive_REVIEW" to ("Female reproductive system — unverified" to "பெண் இனப்பெருக்க மண்டலம் — சரிபார்க்கப்படவில்லை"),
                "12_excretory_system_REVIEW" to ("Excretory system — unverified" to "கழிவுநீக்க மண்டலம் — சரிபார்க்கப்படவில்லை"),
            )
            Text(
                if (tamil) "ஆசிரியர் ஆய்வுக்கான குறிப்புப் படங்கள் — வெளியீட்டிற்கு ஒப்புதல் இல்லை"
                else "EDITORIAL REFERENCES — NOT APPROVED FOR PUBLICATION",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("pila-reference-review-warning"),
            )
            reviewViews.forEach { (file, labels) ->
                val title = if (tamil) labels.second else labels.first
                val raster = rememberRequiredRasterBitmap("review_only/pila/generated_12/$file")
                var expanded by rememberSaveable(file) { mutableStateOf(false) }
                Card(Modifier.fillMaxWidth().testTag("pila-review-$file")) {
                    Column(Modifier.fillMaxWidth().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(title, style = MaterialTheme.typography.titleMedium)
                        if (raster == null) {
                            Text(if (tamil) "ஆய்வுப் படக்கோப்பு இணைக்கப்படவில்லை"
                                else "Review-only reference bitmap not bundled",
                                modifier = Modifier.testTag("pila-review-missing-$file"))
                        } else {
                            Image(bitmap = raster, contentDescription = title,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxWidth()
                                    .aspectRatio(raster.width.toFloat() / raster.height.toFloat())
                                    .clickable { expanded = true }
                                    .testTag("pila-review-image-$file"))
                            OutlinedButton(onClick = { expanded = true }) {
                                Text(if (tamil) "பெரிதாக்கு" else "Enlarge reference")
                            }
                            if (expanded) RequiredTypeStudyFullImage(
                                image = raster, title = "REVIEW ONLY · $title",
                                language = language, onDismiss = { expanded = false })
                        }
                    }
                }
            }
        }
        if (study.organismId == "asterias") {
            // These are separate editorial candidates, NOT accepted type-study masters.
            val candidates = listOf(
                Triple("external_REVIEW", "REJECTED: internal composite, not external morphology", "நிராகரிக்கப்பட்டது: புறத்தோற்றப் படம் அல்ல"),
                Triple("water_vascular_REVIEW", "REVIEW: incorrect aboral-view caption", "ஆய்வு: எதிர்வாய்ப்புறக் குறிப்பு தவறு"),
                Triple("digestive_REVIEW", "REJECTED: stomach anatomy needs correction", "நிராகரிக்கப்பட்டது: இரைப்பை உடற்கூறு திருத்தம் தேவை"),
                Triple("nervous_REVIEW", "REVIEW: nerve details not verified", "ஆய்வு: நரம்பு விவரங்கள் சரிபார்க்கப்படவில்லை"),
                Triple("digestive_reference_ONLY", "SOURCE REFERENCE: mixed-system dissection; not approved digestive plate", "மூலக் குறிப்பு: பல மண்டலங்கள் உள்ள படம்; ஒப்புதல் இல்லை"),
                Triple("water_vascular_reference_ONLY", "SOURCE REFERENCE: central water-vascular anatomy; review only", "மூலக் குறிப்பு: நீர்க்குழாய் மண்டலம்; ஆய்வுக்கு மட்டும்"),
                Triple("body_wall_reference_ONLY", "SOURCE REFERENCE: body-wall transverse section; review only", "மூலக் குறிப்பு: உடற்சுவர் குறுக்குவெட்டு; ஆய்வுக்கு மட்டும்"),
            )
            Text(
                if (tamil) "அஸ்டீரியாஸ் வரைவு படங்கள் — உடற்கூறு ஒப்புதல் நிலுவை"
                else "ASTERIAS DRAFT IMAGES AND SOURCE REFERENCES — NOT APPROVED",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("asterias-review-warning"),
            )
            candidates.forEach { (file, english, tamilTitle) ->
                val title = if (tamil) tamilTitle else english
                val raster = rememberRequiredRasterBitmap("review_only/asterias/$file")
                var expanded by rememberSaveable(file) { mutableStateOf(false) }
                Card(Modifier.fillMaxWidth().testTag("asterias-review-$file")) {
                    Column(
                        Modifier.fillMaxWidth().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(title, style = MaterialTheme.typography.titleMedium)
                        if (raster == null) {
                            Text(
                                if (tamil) "வரைவு படக்கோப்பு இன்னும் இணைக்கப்படவில்லை"
                                else "Draft image not yet bundled",
                                modifier = Modifier.testTag("asterias-review-missing-$file"),
                            )
                        } else {
                            Image(
                                bitmap = raster,
                                contentDescription = title,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxWidth()
                                    .aspectRatio(raster.width.toFloat() / raster.height.toFloat())
                                    .clickable { expanded = true }
                                    .testTag("asterias-review-image-$file"),
                            )
                            OutlinedButton(onClick = { expanded = true }) {
                                Text(if (tamil) "பெரிதாக்கு" else "Enlarge draft")
                            }
                            if (expanded) RequiredTypeStudyFullImage(
                                image = raster,
                                title = "REVIEW ONLY · $title",
                                language = language,
                                onDismiss = { expanded = false },
                            )
                        }
                    }
                }
            }
        }
        study.requiredPlateIds.forEach { plateId ->
            val stem = if (study.organismId == "paramecium")
                "unit1/replacements/paramecium_${plateId}"
            else "type_studies/${study.organismId}/${plateId}"
            val raster = rememberRequiredRasterBitmap(stem)
            val readable = RestoredPlateTitles.value(study.organismId, plateId, language)
            var expanded by rememberSaveable(stem) { mutableStateOf(false) }
            Card(Modifier.fillMaxWidth().testTag("r2-plate-slot-${study.organismId}-${plateId}")) {
                Column(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(readable, style = MaterialTheme.typography.titleMedium)
                    if (raster == null) {
                        Text(
                            if (tamil) "இந்த உடற்கூறுப் படம் இன்னும் கிடைக்கவில்லை. சரிபார்க்கப்பட்ட படம் சேர்க்கப்பட்டவுடன் இங்கே காணலாம்."
                            else "This anatomical illustration is not yet available. A verified image will appear here when ready.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("r2-plate-missing-${study.organismId}-${plateId}"),
                        )
                    } else {
                        Image(
                            bitmap = raster,
                            contentDescription = "${study.scientificName}: ${readable}",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                                .aspectRatio(raster.width.toFloat() / raster.height.toFloat())
                                .clickable(onClickLabel = if (tamil) "முழுத்திரையில் பெரிதாக்கு" else "Open full-size image") { expanded = true }
                                .testTag("r2-plate-bitmap-${study.organismId}-${plateId}"),
                        )
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.heightIn(min = 48.dp)
                                .testTag("r2-plate-expand-${study.organismId}-${plateId}"),
                        ) {
                            Text(if (tamil) "படத்தைப் பெரிதாக்கு" else "Tap to enlarge image")
                        }
                        Text(
                            if (tamil) "ஆய்வுக்கான வரைவு · இறுதி அறிவியல் ஒப்புதல் பெறவில்லை."
                            else "Raster candidate · anatomy and bilingual review pending.",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        if (expanded) {
                            RequiredTypeStudyFullImage(
                                image = raster, title = "${study.scientificName} · ${readable}",
                                language = language, onDismiss = { expanded = false },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun RequiredTypeStudyFullImage(
    image: ImageBitmap,
    title: String,
    language: AppLanguage,
    onDismiss: () -> Unit,
) {
    var zoom by remember(title) { mutableFloatStateOf(1f) }
    var panX by remember(title) { mutableFloatStateOf(0f) }
    var panY by remember(title) { mutableFloatStateOf(0f) }
    val tamil = language == AppLanguage.TAMIL
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(
            Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding().navigationBarsPadding().padding(14.dp)
                .testTag("r2-fullscreen-dialog"),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Box(
                Modifier.fillMaxWidth().weight(1f).clipToBounds()
                    .testTag("r6-zoom-viewport-clipped")
                    .pointerInput(title) {
                        detectTapGestures(onDoubleTap = {
                            zoom = if (zoom > 1.01f) 1f else 2f
                            panX = 0f; panY = 0f
                        })
                    }
                    .pointerInput(title) {
                        detectTransformGestures { _, pan, change, _ ->
                            zoom = (zoom * change).coerceIn(1f, 8f)
                            if (zoom <= 1.01f) { panX = 0f; panY = 0f }
                            else {
                                val imageAspect = image.width.toFloat() / image.height.toFloat()
                                val viewportAspect = size.width.toFloat() / size.height.toFloat()
                                val fittedWidth = if (imageAspect > viewportAspect) size.width.toFloat() else size.height * imageAspect
                                val fittedHeight = if (imageAspect > viewportAspect) size.width / imageAspect else size.height.toFloat()
                                val maxX = ((fittedWidth * zoom - size.width) / 2f).coerceAtLeast(0f)
                                val maxY = ((fittedHeight * zoom - size.height) / 2f).coerceAtLeast(0f)
                                panX = (panX + pan.x).coerceIn(-maxX, maxX)
                                panY = (panY + pan.y).coerceIn(-maxY, maxY)
                            }
                        }
                    },
            ) {
                Image(
                    bitmap = image,
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().graphicsLayer {
                        scaleX = zoom; scaleY = zoom
                        translationX = panX; translationY = panY
                    }.testTag("r2-fullscreen-bitmap"),
                )
            }
            Text(
                if (tamil) "பெரிதாக்கம்: ${(zoom * 100f).roundToInt()}%"
                else "Zoom: ${(zoom * 100f).roundToInt()}%",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.testTag("r6-zoom-level"),
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { zoom = (zoom / 2f).coerceAtLeast(1f) },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("r2-zoom-out")) {
                        Text("−")
                    }
                    OutlinedButton(onClick = { zoom = (zoom * 2f).coerceAtMost(8f) },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("r2-zoom-in")) {
                        Text("+")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { zoom = 1f; panX = 0f; panY = 0f },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("r2-zoom-reset")) {
                        Text(if (tamil) "மீட்டமை" else "Reset")
                    }
                    OutlinedButton(onClick = onDismiss,
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("r2-fullscreen-close")) {
                        Text(if (tamil) "மூடு" else "Close")
                    }
                }
            }
            Text(
                if (tamil) "அறிவியல் மற்றும் தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது"
                else "Anatomical and Tamil verification pending",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
