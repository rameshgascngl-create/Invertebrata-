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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
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
        "asterias/internal" to BilingualText("Internal dissection — unlabelled source illustration", "உட்புற உடற்கூறு — உறுப்புப் பெயர்கள் குறிக்கப்படாத அசல் விளக்கப்படம்"),
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
            else "ANATOMICAL REFERENCE PLATES · ${study.scientificName}",
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
        if (study.organismId == "pila") {
            Text(
                if (tamil) "பைலா வகையினத்தின் புறத்தோற்றம், மேலங்கிக் குழி, செவுள் மற்றும் முறுக்கம் ஆகிய நான்கு உடற்கூறுப் படங்களும் தனித்தனியாகச் சரிபார்க்கப்பட வேண்டும். ஓட்டு அருங்காட்சியகப் புகைப்படம் இவற்றுக்கு மாற்றாகாது."
                else "The four Pila anatomy requirements—external morphology, mantle cavity, ctenidium and torsion—need separately verified published figures. The museum shell photograph does not replace any of them.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("pila-four-anatomy-release-hold"),
            )
            // Licensed specimen documentation supplements the prescribed
            // anatomical drawings; it MUST NOT satisfy pila/external.
            val museumRaster = rememberRequiredRasterBitmap("type_studies/pila/shell-specimen-reference")
            var museumExpanded by rememberSaveable("pila-museum-shell") { mutableStateOf(false) }
            Card(Modifier.fillMaxWidth().testTag("pila-museum-shell-card")) {
                Column(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        if (tamil) "பைலா குளோபோசா — அருங்காட்சியக ஓட்டு மாதிரி"
                        else "Pila globosa — authenticated museum shell specimen",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        if (tamil) "கவனிக்க: ஓட்டின் வடிவம், சுழற்சிகள் மற்றும் ஓட்டுவாயை அடையாளம் காண்க. இது முழு விலங்கின் உடற்கூறு படம் அல்ல."
                        else "Observe: Identify the shell outline, whorls and aperture. This specimen does not show the complete animal anatomy.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("pila-shell-student-observation"),
                    )
                    Text(
                        if (tamil)
                            "Naturalis Biodiversity Center-இன் அசல் சேகரிப்பு ஒளிப்படம். இது ஓட்டின் புற அமைப்பைக் காட்டும் துணை ஆதாரம் மட்டுமே; தலை, பாதம், மேன்டில் அறை, சீனிடியம் மற்றும் முறுக்கம் ஆகியவற்றின் உடற்கூறு வரைபடங்களுக்கு மாற்றல்ல."
                        else
                            "Authentic Naturalis museum collection photograph, showing shell specimens and different shell aspects. This is supplementary shell evidence, NOT a labelled pencil anatomical plate, and does not replace the required external animal, mantle cavity, ctenidium or torsion drawings.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("pila-museum-shell-limits"),
                    )
                    if (museumRaster == null) {
                        Text(
                            if (tamil) "உரிமம் சரிபார்க்கப்பட்ட அருங்காட்சியகப் படம் இன்னும் இறக்குமதி செய்யப்படவில்லை."
                            else "The provenance-verified museum photograph has not yet been imported.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("pila-museum-shell-not-imported"),
                        )
                    } else {
                        Image(
                            bitmap = museumRaster,
                            contentDescription = if (tamil) "பைலா குளோபோசா ஓட்டு அருங்காட்சியக ஒளிப்படம்" else "Pila globosa museum shell specimen photograph",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                                .aspectRatio(museumRaster.width.toFloat() / museumRaster.height.toFloat())
                                .clickable { museumExpanded = true }
                                .testTag("pila-museum-shell-bitmap"),
                        )
                        OutlinedButton(
                            onClick = { museumExpanded = true },
                            modifier = Modifier.heightIn(min = 48.dp)
                                .testTag("pila-museum-shell-enlarge"),
                        ) {
                            Text(if (tamil) "ஓட்டுப் படத்தைப் பெரிதாக்கு" else "Enlarge original shell photograph")
                        }
                        if (museumExpanded) RequiredTypeStudyFullImage(
                            image = museumRaster,
                            title = if (tamil) "பைலா குளோபோசா — ஓட்டு மாதிரி"
                                    else "Pila globosa — preserved shell specimen",
                            language = language,
                            onDismiss = { museumExpanded = false },
                        )
                    }
                    Text(
                        "Naturalis Biodiversity Center / Wikimedia Commons · ZMA.MOLL.318132 · CC0 1.0",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("pila-museum-shell-source"),
                    )
                    // Keep the full provenance URL in the bundled attribution record;
                    // a long unbroken URL here crowds out the lesson at 200% text scale.
                    Text(
                        if (tamil) "முழு மூல இணைப்பும் உரிம விவரமும் இணைக்கப்பட்ட ஆதாரக் குறிப்பில் உள்ளன."
                        else "Full source URL and licence details are retained in the bundled attribution record.",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
        if (study.organismId == "pila") {
            // Historical comparative figure, NOT a Pila globosa anatomy master.
            val historicPlate = rememberRequiredRasterBitmap("type_studies/pila/swainson-1821-pila-ampullacea")
            var historicExpanded by rememberSaveable("pila-historical-comparative") { mutableStateOf(false) }
            if (historicPlate != null) {
                Card(Modifier.fillMaxWidth().testTag("pila-published-comparative-card")) {
                    Column(Modifier.fillMaxWidth().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            if (tamil) "வரலாற்று ஒப்பீட்டுப் படம் — Pila ampullacea (Pila globosa அல்ல)"
                            else "Published comparative plate — Pila ampullacea (NOT Pila globosa)",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            if (tamil) "வில்லியம் ஸ்வெயின்சன் (1821) வெளியிட்ட ஓட்டு மற்றும் புறத்தோற்றப் படம். இது Pila globosa-வின் உடற்கூறுப் படமல்ல; மேலங்கிக் குழி, செவுள் அல்லது முறுக்கத்திற்கான ஆதாரமும் அல்ல."
                            else "William Swainson's 1821 historical external-shell illustration. It depicts Pila ampullacea, not Pila globosa, and provides no evidence for mantle-cavity, ctenidium or torsion anatomy.",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Image(
                            bitmap = historicPlate,
                            contentDescription = if (tamil) "1821 ஆம் ஆண்டு Pila ampullacea வரலாற்று ஒப்பீட்டுப் படம்"
                                else "1821 published comparative illustration of Pila ampullacea",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                                .aspectRatio(historicPlate.width.toFloat() / historicPlate.height.toFloat())
                                .clickable { historicExpanded = true }
                                .testTag("pila-published-comparative-bitmap"),
                        )
                        Text(
                            "William Swainson · Zoological Illustrations, Vol. II, Plate 103 (1821) · Public domain · Wikimedia Commons",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Text(
                            "https://commons.wikimedia.org/wiki/File:Zoological_Illustrations_Volume_II_Plate_103.jpg",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        if (historicExpanded) RequiredTypeStudyFullImage(
                            image = historicPlate,
                            title = "Pila ampullacea — Swainson (1821)",
                            language = language,
                            onDismiss = { historicExpanded = false },
                        )
                    }
                }
            }
        }
        // Student textbook view: ONLY one mandatory slot per anatomical subject.
        // Pila/Asterias rejected or review-only archive candidates are excluded
        // rather than duplicated above the required student-facing plates.
        // Asset archives remain untouched for separate expert examination.
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
                        if (study.organismId == "asterias" && plateId == "external") {
                            Text(
                                if (tamil)
                                    "ஆதார வரைபடம்: Asterias forbesi. படம் 1 — எதிர்வாய்ப்புறம் (மேற்புறம்); படம் 2 — வாய்ப்புறம் (கீழ்ப்புறம்). இது 1904 ஆம் ஆண்டின் அசல் அறிவியல் வரைபடத்தின் பகுதியாகும்."
                                else
                                    "Documented Asterias forbesi plate: figure 1 = aboral (dorsal) view; figure 2 = oral (ventral) view. Cropped from the original 1904 zoological engraving.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.testTag("asterias-external-species-provenance"),
                            )
                            Text(
                                "Hubert Lyman Clark (1904) · University of Washington Freshwater and Marine Image Bank · Public domain",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                "https://commons.wikimedia.org/wiki/File:FMIB_41097_Asterias_forbesi_(small_specimen)_Arboral_view_(1),_Oral_view_(2);_Asterias_vulgaris_(small_specimen)_Arboral_view_(3),_Oral.jpeg",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        if (study.organismId == "asterias" && plateId == "water-vascular-system") {
                            Text(
                                if (tamil)
                                    "1903 ஆம் ஆண்டின் கடல் நட்சத்திர நீர்க்குழாய் அமைப்பின் பகுதி விளக்கப்படம்; அஸ்டீரியாஸ் ஐந்து கரங்களின் முழு உடற்கூறுப் படம் அல்ல. a — மேட்ரிபோரைட்; b — கற்குழாய்; c — வளையக்குழாய்; d — ஆரக்குழாய்கள்; e — ஆம்புல்லாக்கள்; f — அம்புலாக்ரல் பகுதி."
                                else
                                    "Historic PARTIAL starfish water-vascular schematic (not a complete five-arm Asterias dissection): a madreporite; b stone canal; c ring canal; d radial canals; e ampullae; f ambulacra.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.testTag("asterias-wvs-legend"),
                            )
                            Text(
                                "Augusta Foote Arnold (1903) · University of Washington Freshwater and Marine Image Bank · Public domain",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                "https://commons.wikimedia.org/wiki/File:FMIB_52615_Diagram_of_water-vascular_system_of_a_starfish_;.jpeg",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        if (study.organismId == "asterias" && plateId == "internal") {
                            Text(
                                if (tamil)
                                    "அஸ்டீரியாஸ் ரூபென்ஸ் (Asterias rubens) உட்புற உடற்கூறின் அசல் விளக்கப்படம். இப்படத்தில் உறுப்புப் பெயர்கள் அச்சிடப்படவில்லை; உறுதிசெய்யாத உறுப்புக் குறியீடுகள் சேர்க்கப்படவில்லை."
                                else
                                    "Asterias rubens: original anatomical dissection illustration with no printed organ labels; unverified leader lines have not been invented.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.testTag("asterias-internal-species-licence"),
                            )
                            Text(
                                "Hans Hillewaert (2007) · CC BY-SA 4.0 · converted JPEG to PNG and resized · https://creativecommons.org/licenses/by-sa/4.0/",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                "https://commons.wikimedia.org/wiki/File:Asterias_rubens,_dissection_modified.jpg",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        if (study.organismId == "asterias" && plateId == "larval-development") {
                            Text(
                                if (tamil)
                                    "இடப்புறம்: அஸ்டீரியாஸ் பைபின்னாரியா இளவுயிரி. வலப்புறம்: முதிர்ந்த பிராக்கியோலாரியா இளவுயிரி. எர்ன்ஸ்ட் ஹேக்கல் (1904) வரைபடத்திலிருந்து எடுக்கப்பட்ட இரண்டு தனித்த படங்கள்; இன அடையாளம் Asterias sp. என மட்டும் குறிப்பிடப்பட்டுள்ளது."
                                else
                                    "LEFT: Asterias sp. bipinnaria larva; RIGHT: Asterias sp. older brachiolaria larva. Two distinct historical figures by Ernst Haeckel (1904); species identification is genus-level only.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.testTag("asterias-larvae-two-stage-legend"),
                            )
                            Text(
                                "Ernst Haeckel (1904) · extracted by Haplochromis · public domain (PD-old) · two source pictures combined side by side",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                "https://commons.wikimedia.org/wiki/File:Asterias_sp.,_bipinnaria_larva_from_below.jpg",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                "https://commons.wikimedia.org/wiki/File:Asterias_sp.,_older_brachiolaria_larva_from_below.jpg",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
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
                    // Expose the actual translation, not merely the zoom label,
                    // so an Android touch test can prove drag-to-pan works.
                    .semantics {
                        stateDescription = "x=${panX.roundToInt()};y=${panY.roundToInt()}"
                    }
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
                    OutlinedButton(onClick = {
                        zoom = (zoom / 2f).coerceAtLeast(1f)
                        if (zoom <= 1.01f) { panX = 0f; panY = 0f }
                    },
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
