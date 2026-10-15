package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage

/**
 * U1.7: Full native bilingual teaching sections, not a question/answer gallery.
 * Original anatomical pencil masters, stage-specific hotspot coordinates,
 * Tamil narration and scientific artwork acceptance remain OPEN.
 * Never present a genus-level source reference as species-level diagnosis.
 */
private data class FlagellateStage(
    val id: String,
    val headingEn: String,
    val headingTa: String,
    val bodyEn: String,
    val bodyTa: String,
)
private data class FlagellateStudy(
    val id: String,
    val titleEn: String,
    val titleTa: String,
    val imageEn: String,
    val imageTa: String,
    val stages: List<FlagellateStage>,
    val credit: String,
    val source: String,
)
private val flagellateStudies = listOf(
    FlagellateStudy(
        id = "trypanosoma",
        titleEn = "Trypanosoma brucei — structure, movement and host–vector cycle",
        titleTa = "டிரிப்பனோசோமா புரூசை — அமைப்பு, இயக்கம், ஓம்புயிரி–கடத்தி சுழற்சி",
        imageEn = "Attributed source illustration of a trypomastigote; not an approved pencil anatomical drawing",
        imageTa = "டிரிப்போமாஸ்டிகோட் ஆதார விளக்கப்படம்; அங்கீகரிக்கப்பட்ட பென்சில் உடலமைப்புப் படமல்ல",
        stages = listOf(
            FlagellateStage("morphology", "1. Bloodstream trypomastigote: identifying structures",
                "1. இரத்த டிரிப்போமாஸ்டிகோட்: அடையாள அமைப்புகள்",
                "The mammalian bloodstream form is elongated and extracellular. A single nucleus occupies a central position; a small kinetoplast lies posteriorly. A flagellum runs along the body with an undulating membrane and extends freely at the anterior end. The kinetoplast is mitochondrial DNA, not a second nucleus.",
                "பாலூட்டியின் இரத்தத்தில் காணப்படும் நீண்ட வடிவம் செல்லுக்கு வெளியே வாழ்கிறது. ஒரு உட்கரு மையப்பகுதியில் உள்ளது; சிறிய கைனெட்டோபிளாஸ்ட் பின்புறம் அமைந்துள்ளது. கசையிழை உடலின் ஓரத்தில் அலைவுச் சவ்வுடன் சென்று முன்புறத்தில் சுதந்திரமாக நீள்கிறது. கைனெட்டோபிளாஸ்ட் மைட்டோகாண்டிரிய DNA ஆகும்; இரண்டாவது உட்கரு அல்ல."),
            FlagellateStage("mammal", "2. Mammalian host: multiplication and pathology",
                "2. பாலூட்டி ஓம்புயிரி: பெருக்கமும் நோயியலும்",
                "Infected tsetse flies inoculate metacyclic trypomastigotes during feeding. Extracellular bloodstream trypomastigotes multiply by longitudinal binary fission and can circulate in blood and lymph; later infection may involve cerebrospinal fluid and the central nervous system. African sleeping sickness is associated with human-infective T. b. gambiense and T. b. rhodesiense.",
                "தொற்றுடைய ட்ஸெட்ஸெ ஈ கடிக்கும்போது மெட்டாசைக்கிளிக் டிரிப்போமாஸ்டிகோட்டுகளை உடலில் செலுத்துகிறது. செல்லுக்கு வெளியே வாழும் இரத்த டிரிப்போமாஸ்டிகோட்டுகள் நீளவாக்குப் பிளவின் மூலம் பெருகி இரத்தத்திலும் நிணநீரிலும் பரவுகின்றன; பின்னர் மூளை–தண்டுவட நீரும் மைய நரம்பு மண்டலமும் பாதிக்கப்படலாம். மனித ஆப்பிரிக்கத் தூக்கநோய் T. b. gambiense மற்றும் T. b. rhodesiense தொற்றுகளுடன் தொடர்புடையது."),
            FlagellateStage("vector", "3. Tsetse fly: differentiated developmental forms",
                "3. ட்ஸெட்ஸெ ஈ: வேறுபட்ட வளர்ச்சி நிலைகள்",
                "A Glossina fly ingests bloodstream trypomastigotes. In its midgut, procyclic trypomastigotes multiply, then development proceeds through epimastigotes in the salivary-gland route. Infective metacyclic trypomastigotes are transmitted at a later blood meal. Do not label the pictured bloodstream form as every vector stage.",
                "குளோசினா ஈ இரத்த டிரிப்போமாஸ்டிகோட்டுகளை உட்கொள்கிறது. ஈயின் நடுக்குடலில் புரோசைக்கிளிக் டிரிப்போமாஸ்டிகோட்டுகள் பெருகி, உமிழ்நீர்ச் சுரப்பிப் பகுதியில் எபிமாஸ்டிகோட் நிலைகள் உருவாகின்றன. பின்னர் தொற்றை ஏற்படுத்தும் மெட்டாசைக்கிளிக் நிலைகள் மற்றொரு கடியின்போது பரவுகின்றன. இங்கே காட்டப்பட்ட இரத்த வடிவத்தை ஈயின் எல்லா நிலைகளாகவும் குறிக்கக் கூடாது."),
            FlagellateStage("contrast", "4. Important comparison: African versus American trypanosomes",
                "4. முக்கிய ஒப்பீடு: ஆப்பிரிக்க மற்றும் அமெரிக்க டிரிப்பனோசோம்கள்",
                "T. brucei is spread by bites of infected tsetse flies and has multiplying extracellular stages in mammals. T. cruzi is associated with triatomine bugs; infective forms from bug faeces can enter through skin breaks or mucosa, and intracellular amastigotes multiply in mammalian tissues. These are different transmission and tissue cycles.",
                "T. brucei தொற்று ட்ஸெட்ஸெ ஈ கடியால் பரவுகிறது; பாலூட்டியில் செல்லுக்கு வெளியே பெருகும் நிலைகள் உள்ளன. T. cruzi தொற்று ட்ரையட்டோமைன் பூச்சியின் மலத்திலுள்ள தொற்று நிலைகள் தோல் காயம் அல்லது சளிச்சவ்வு வழி நுழைவதால் ஏற்படலாம்; பாலூட்டி திசுக்களில் செல்லுக்குள்ளான அமாஸ்டிகோட்டுகள் பெருகுகின்றன. இவை வெவ்வேறு பரவும் முறைகளும் திசுச் சுழற்சிகளும் ஆகும்."),
        ),
        credit = "Johannahoog · CC BY-SA 3.0 · source reference only",
        source = "https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html",
    ),
    FlagellateStudy(
        id = "leishmania",
        titleEn = "Leishmania spp. — amastigote, promastigote and sand-fly cycle",
        titleTa = "லீஷ்மேனியா இனங்கள் — அமாஸ்டிகோட், புரோமாஸ்டிகோட், மணல் ஈச் சுழற்சி",
        imageEn = "Genus-level amastigote source illustration; no promastigote is depicted in this image",
        imageTa = "பேரின அளவிலான அமாஸ்டிகோட் விளக்கப்படம்; இப்பகுதியில் புரோமாஸ்டிகோட் காட்டப்படவில்லை",
        stages = listOf(
            FlagellateStage("morphology", "1. Intracellular amastigote: nucleus and kinetoplast",
                "1. செல்லுக்குள்ளான அமாஸ்டிகோட்: உட்கரு மற்றும் கைனெட்டோபிளாஸ்ட்",
                "In mammalian tissues the parasite is a small oval amastigote inside macrophages and other mononuclear phagocytes. Each parasite has a nucleus and a separate kinetoplast. A long external flagellum is absent at this stage; a short flagellar apparatus is not usually visible by ordinary light microscopy. The image is genus-level, not proof of L. donovani.",
                "பாலூட்டி திசுக்களில் ஒட்டுண்ணி மேக்ரோபேஜ் போன்ற விழுங்கிச் செல்களுக்குள் சிறிய முட்டைவடிவ அமாஸ்டிகோட்டாக உள்ளது. ஒவ்வொரு ஒட்டுண்ணியிலும் உட்கருவும் தனித்த கைனெட்டோபிளாஸ்டும் காணப்படும். இந்நிலையில் வெளியே நீளும் கசையிழை இல்லை; குறுகிய கசையிழை அமைப்பு சாதாரண ஒளி நுண்ணோக்கியில் பெரும்பாலும் தென்படாது. படம் பேரின அளவிலானது; L. donovani இனச் சான்றல்ல."),
            FlagellateStage("mammal", "2. Mammalian infection: phagocytes and replication",
                "2. பாலூட்டித் தொற்று: விழுங்கிச் செல்களும் பெருக்கமும்",
                "An infected female phlebotomine sand fly introduces promastigotes into the skin. Macrophages engulf them; they transform into amastigotes and divide intracellularly. Clinical disease may be cutaneous, mucosal or visceral depending on the infecting parasite and host response; a single image cannot predict the clinical form.",
                "தொற்றுடைய பெண் பிளெபோட்டோமைன் மணல் ஈ தோலில் புரோமாஸ்டிகோட்டுகளைச் செலுத்துகிறது. மேக்ரோபேஜ் செல்கள் அவற்றை விழுங்குகின்றன; அவை அமாஸ்டிகோட்டுகளாக மாறி செல்லுக்குள் பிளவடைந்து பெருகுகின்றன. ஒட்டுண்ணி மற்றும் ஓம்புயிரியின் பதிலைப் பொறுத்து தோல், சளிச்சவ்வு அல்லது உள்ளுறுப்பு நோய் ஏற்படலாம்; ஒரு படம் மட்டும் நோய் வகையை நிர்ணயிக்காது."),
            FlagellateStage("vector", "3. Sand fly: motile promastigote stage",
                "3. மணல் ஈ: இயங்கும் புரோமாஸ்டிகோட் நிலை",
                "The sand fly acquires amastigotes while feeding on infected tissue. In the fly gut, the parasites transform into elongated flagellated promastigotes and develop into transmissible forms. Unlike the intracellular amastigote, a promastigote has an anterior kinetoplast and a projecting anterior flagellum. A separate, reviewed promastigote pencil plate is still required.",
                "பாதிக்கப்பட்ட திசுவிலிருந்து இரத்த உணவு எடுக்கும்போது மணல் ஈ அமாஸ்டிகோட்டுகளைப் பெறுகிறது. ஈயின் குடலில் அவை நீண்ட கசையிழை உடைய புரோமாஸ்டிகோட்டுகளாக மாறி தொற்று ஏற்படுத்தும் நிலைகளாக வளர்கின்றன. செல்லுக்குள்ளான அமாஸ்டிகோட்டிலிருந்து வேறுபட்டு, புரோமாஸ்டிகோட்டில் முன்புறக் கைனெட்டோபிளாஸ்டும் முன்நோக்கி நீளும் கசையிழையும் உள்ளன. தனித்த, மதிப்பாய்வு செய்யப்பட்ட புரோமாஸ்டிகோட் பென்சில் படம் இன்னும் தேவை."),
            FlagellateStage("limits", "4. Taxonomy and microscopy: limits of identification",
                "4. வகைப்பாடும் நுண்ணோக்கியும்: இன அடையாள வரம்புகள்",
                "Leishmania species are not reliably distinguishable by amastigote morphology alone. Tissue microscopy may demonstrate compatible amastigotes, but species identification requires further methods when clinically needed. Distinguish the sand fly vector from the tsetse fly vector of African trypanosomiasis.",
                "அமாஸ்டிகோட்டின் வடிவத்தை மட்டும் கொண்டு லீஷ்மேனியா இனங்களை நம்பகமாக வேறுபடுத்த முடியாது. திசு நுண்ணோக்கியில் ஒத்த அமாஸ்டிகோட்டுகள் தெரியலாம்; மருத்துவத் தேவைக்கேற்ப இன அடையாளத்திற்கு கூடுதல் சோதனைகள் வேண்டும். ஆப்பிரிக்க டிரிப்பனோசோமியாசிஸைப் பரப்பும் ட்ஸெட்ஸெ ஈயிலிருந்து மணல் ஈயை வேறுபடுத்திக் கற்க வேண்டும்."),
        ),
        credit = "NIAID / Ryan Kissinger · public-domain reference",
        source = "https://www.cdc.gov/dpdx/leishmaniasis/",
    ),
)

@Composable
internal fun UnitIFlagellateTypeStudiesHeader(language: AppLanguage) {
    val tamil = language == AppLanguage.TAMIL
    Column(
        modifier = Modifier.fillMaxWidth().testTag("u17-flagellate-native-lessons"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            if (tamil) "ஒட்டுண்ணிக் கசையிழை உயிரினங்கள் — விரிவான பாடம்"
            else "Parasitic flagellates — illustrated textbook chapters",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.testTag("u17-flagellate-heading"),
        )
        Text(
            if (tamil)
                "இது கற்றலுக்கான வரைவு. ஆதாரப் படங்கள் உண்மையான பென்சில் வரைபடங்களாகவோ இனநிர்ணய நுண்ணோக்கிப் படங்களாகவோ கருதப்படக்கூடாது. தொடு-புள்ளி, ஒலிவிளக்கம், தனிப் பென்சில் பட ஆய்வு நிலுவையில் உள்ளன."
            else
                "Editorial teaching candidate: these are attributed published reference images, not original pencil anatomical masters or species-diagnostic micrographs. Original plates, reviewed touch hotspots and organ-specific audio remain pending.",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.testTag("u17-flagellate-academic-hold"),
        )
    }
}

/** Separate LazyColumn entries ensure smooth scrolling with 200% Tamil fonts. */
/**
 * Reader layout: each stage is a separate always-visible textbook subsection.
 * This deliberately avoids hiding the academic content behind Q&A-like buttons.
 */
@Composable
internal fun UnitIFlagellateStudyIntroduction(id: String, language: AppLanguage) {
    val study = flagellateStudies.single { it.id == id }
    val tamil = language == AppLanguage.TAMIL
    val bitmap = rememberRequiredRasterBitmap("unit1/references/" + study.id, pngOnly = true)
    var expanded by rememberSaveable(study.id) { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().testTag("u17-study-" + study.id)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                if (tamil) study.titleTa else study.titleEn,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("u17-study-title-" + study.id),
            )
            if (bitmap == null) {
                Text(
                    if (tamil) "குறிப்பிட்ட ஆதாரப் படம் கிடைக்கவில்லை; தவறான மாற்றுப் படம் உருவாக்கப்படாது."
                    else "Cited PNG not present. No substitute drawing is generated.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u17-image-missing-" + study.id),
                )
            } else {
                Image(
                    bitmap = bitmap,
                    contentDescription = if (tamil) study.imageTa else study.imageEn,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                        .testTag("u17-reference-image-" + study.id),
                )
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u17-image-expand-" + study.id),
                ) {
                    Text(if (tamil) "ஆதாரப் படத்தை முழுத்திரையில் பெரிதாக்கு"
                         else "Open source figure full-screen and zoom")
                }
                if (expanded) {
                    ExpandedProtozoaReferenceImage(
                        bitmap = bitmap,
                        title = if (tamil) study.titleTa else study.titleEn,
                        language = language,
                        referenceId = study.id,
                        onClose = { expanded = false },
                    )
                }
            }
            Text(
                if (tamil) study.imageTa else study.imageEn,
                style = MaterialTheme.typography.labelMedium,
            )
            Text(study.credit, style = MaterialTheme.typography.labelSmall)
            Text(study.source, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
internal fun UnitIFlagellateLessonSection(
    studyId: String,
    stageId: String,
    language: AppLanguage,
) {
    val study = flagellateStudies.single { it.id == studyId }
    val stage = study.stages.single { it.id == stageId }
    val tamil = language == AppLanguage.TAMIL
    Card(
        modifier = Modifier.fillMaxWidth()
            .testTag("u17-stage-" + studyId + "-" + stageId),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                if (tamil) stage.headingTa else stage.headingEn,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("u17-current-heading-" + studyId + "-" + stageId),
            )
            Text(
                if (tamil) stage.bodyTa else stage.bodyEn,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("u17-current-explanation-" + studyId + "-" + stageId),
            )
        }
    }
}
