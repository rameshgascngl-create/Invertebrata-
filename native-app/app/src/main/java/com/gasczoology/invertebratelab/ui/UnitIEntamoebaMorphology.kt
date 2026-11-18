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

/**
 * Unit I U1.5 — source-reviewed TEXT, academically UNVERIFIED ARTWORK.
 *
 * Native teaching content precedes the preserved five-mark A5 questions.
 * Optional original_candidates assets must NEVER silently become accepted:
 * the images are AI-generated graphite-style candidate art (not scanned
 * microscope specimens or physical hand-pencil drawings).
 * A missing candidate image fails closed while the existing, independently
 * sourced reference gallery remains available below this lesson.
 */
@Composable
internal fun UnitIEntamoebaMorphology(language: AppLanguage) {
    val tamil = language == AppLanguage.TAMIL
    Column(
        modifier = Modifier.fillMaxWidth().testTag("u15-entamoeba-morphology"),
        verticalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Text(
            if (tamil) "என்டமீபா ஹிஸ்டோலிட்டிகா — அமைப்பும் வாழ்க்கை நிலைகளும்"
            else "Entamoeba histolytica — morphology and life stages",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.testTag("u15-entamoeba-heading"),
        )
        Text(
            if (tamil)
                "விளக்கப் பாடம்: CDC DPDx ஆதாரத்தைப் பின்பற்றுகிறது. இங்கே காட்டப்படும் வரைவுப் படங்கள் நுண்ணோக்கி வழி இன உறுதிப்படுத்தலுக்கானவை அல்ல."
            else "Teaching lesson based on CDC DPDx morphology. Pencil-style drafts are not microscopy or species-level diagnostic confirmation.",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag("u15-entamoeba-review-hold"),
        )

        EntamoebaStage(
            id = "trophozoite",
            heading = if (tamil) "1. வளர் நிலை — டிரோபோசோயிட்" else "1. Trophozoite (active stage)",
            text = if (tamil)
                "டிரோபோசோயிட் நிலையில் பொதுவாக ஓர் உட்கரு காணப்படும். உட்கருவில் சிறிய மையக் காரியோசோமும், ஓரப் பகுதியில் நுண்ணிய ஒழுங்கான குரோமாட்டினும் உள்ளன. விரல் போன்ற, தெளிவான போலிக்கால்கள் உதவியுடன் நகர்கிறது. அகச்சைட்டோபிளாசம் நுண்துகள்களுடன் காணப்படும்; புறச்சைட்டோபிளாசம் ஒப்பீட்டளவில் தெளிவாக இருக்கலாம். சில தொற்றுகளில் செங்குருதிச் செல்களை உட்கொண்ட டிரோபோசோயிட்டுகள் காணப்படலாம்; இதுவே தனித்து இனத்தை உறுதி செய்யும் சோதனை அல்ல."
            else
                "A trophozoite usually contains one nucleus with a small, typically central karyosome and fine, evenly distributed peripheral chromatin. Progressive movement occurs using hyaline, finger-like pseudopodia. The endoplasm is finely granular, while outer ectoplasm may appear clearer. Ingested erythrocytes are sometimes observed, but erythrophagocytosis alone is not a definitive species-identification test.",
            language = language,
        )

        EntamoebaStage(
            id = "mature_cyst",
            heading = if (tamil) "2. முதிர்ந்த சிஸ்ட் — தொற்றுப் பரவல் நிலை" else "2. Mature cyst (transmission stage)",
            text = if (tamil)
                "முதிர்ந்த சிஸ்ட் பொதுவாக கோள வடிவில், நான்கு உட்கருக்களுடன் காணப்படும். ஒவ்வோர் உட்கருவிலும் சிறிய மையக் காரியோசோமும் ஒழுங்கான நுண்ணிய ஓரக் குரோமாட்டினும் காணப்படலாம். காணப்படும் குரோமாட்டாய்டு தண்டுகளின் முனைகள் மழுங்கி வட்டமாக இருப்பது வழக்கம். சிஸ்டுகள் மாசுபட்ட உணவு அல்லது நீர் வழியாக உட்கொள்ளப்படும் போது தொற்று பரவும். முதிர்ந்த சிஸ்ட் நிலையில் போலிக்கால்கள் இருப்பதாகப் படத்தில் காட்டக் கூடாது."
            else
                "The mature cyst is generally spherical and contains four nuclei. Each typically shows a small central karyosome and delicate, uniform peripheral chromatin. When visible, chromatoid bodies are elongated with rounded, blunt ends. Cysts are transmitted by ingestion through contaminated food or water; mature cysts do not possess motile pseudopodia.",
            language = language,
        )

        Card {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    if (tamil) "3. வாழ்க்கைச் சுழற்சியும் மருத்துவ முக்கியத்துவமும்"
                    else "3. Life cycle and medical significance",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    if (tamil)
                        "முதிர்ந்த சிஸ்டை உட்கொள்வது தொற்றின் முக்கிய வழியாகும். குடலில் சிஸ்டிலிருந்து வளர் நிலைகள் வெளிவந்து பெருகுகின்றன; சில நிலைகளில் குடல் சுவர் ஊடுருவல் மற்றும் கல்லீரல் போன்ற உடற்பகுதிகளுக்குப் பரவல் நிகழலாம். மலத்தில் சிஸ்டுகளும் டிரோபோசோயிட்டுகளும் காணப்படலாம். E. histolytica மற்றும் E. dispar ஆகியவற்றின் ஒத்த வடிவங்களை மட்டும் பார்த்து நம்பகமாக வேறுபடுத்த முடியாது; ஆய்வகச் சோதனைகள் தேவை."
                    else
                        "Infection primarily follows ingestion of mature cysts. Excystation in the intestine releases trophozoites, which multiply; invasive infection can involve the intestinal wall and, occasionally, extraintestinal sites such as the liver. Both cysts and trophozoites may appear in stool. Microscopic morphology alone cannot reliably distinguish E. histolytica from E. dispar; laboratory confirmation is needed.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Text(
            "Scientific source: CDC DPDx · https://www.cdc.gov/dpdx/amebiasis/",
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            if (tamil)
                "குறிப்பு: அசல் வரைவுப் படங்களுக்கான அறிவியல், தமிழ் மற்றும் தொடு-புள்ளி மதிப்பாய்வு இன்னும் நிலுவையில் உள்ளது."
            else
                "Artwork status: DRAFT_UNVERIFIED. Independent anatomical review, Tamil review and hotspot coordinate approval are pending.",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.testTag("u15-entamoeba-pending-acceptance"),
        )
    }
}

@Composable
private fun EntamoebaStage(
    id: String,
    heading: String,
    text: String,
    language: AppLanguage,
) {
    val image = rememberRequiredRasterBitmap(
        "unit1/original_candidates/entamoeba_" + id, pngOnly = false
    )
    Card {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(heading, style = MaterialTheme.typography.titleMedium)
            if (image == null) {
                Text(
                    if (language == AppLanguage.TAMIL)
                        "தனித்த பென்சில் பாணி படத்திற்கான ஆய்வும் இறக்குமதியும் நிலுவையில் உள்ளன. கீழேயுள்ள ஆதார விளக்கப்படத்தைப் பயன்படுத்தலாம்."
                    else
                        "Original graphite-style candidate not yet imported or reviewed; use the attributed reference gallery below.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u15-plate-pending-" + id),
                )
            } else {
                Image(
                    bitmap = image,
                    contentDescription = heading,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(290.dp)
                        .testTag("u15-entamoeba-plate-" + id),
                )
                Text(
                    if (language == AppLanguage.TAMIL)
                        "AI உருவாக்கிய பென்சில் பாணி வரைவு மட்டுமே; கையால் வரையப்பட்ட நுண்ணோக்கிப் படம் அல்ல."
                    else "AI-generated graphite-style draft only. Not a scanned pencil drawing or diagnostic micrograph.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Text(text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
