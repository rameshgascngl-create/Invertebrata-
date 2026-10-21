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
        // Student textbook view: ONLY one mandatory slot per anatomical subject.
        // Pila/Asterias rejected or review-only archive candidates are excluded
        // rather than duplicated above the required student-facing plates.
        // Asset archives remain untouched for separate expert examination.
        study.requiredPlateIds.forEach { plateId ->
            val stem = "type_studies/${study.organismId}/${plateId}"
            val fallbackStem = when (study.organismId + "/" + plateId) {
                "paramecium/external-cilia" -> "unit1/paramecium_external"
                "paramecium/oral-apparatus" -> "unit1/paramecium_oral"
                "paramecium/contractile-vacuoles" -> "unit1/paramecium_vacuole"
                "obelia/colony" -> "unit2/obelia_colony"
                "obelia/life-cycle" -> "unit2/obelia_lifecycle"
                else -> stem
            }
            val primaryRaster = rememberRequiredRasterBitmap(stem)
            val fallbackRaster = rememberRequiredRasterBitmap(fallbackStem)
            val raster = primaryRaster ?: fallbackRaster
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
