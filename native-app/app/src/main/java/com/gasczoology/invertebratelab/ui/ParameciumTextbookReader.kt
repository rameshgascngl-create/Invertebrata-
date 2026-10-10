package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.NativeLessonDrafts
import com.gasczoology.invertebratelab.data.ParameciumLearningEngine
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * R1.4 candidate: offline-first, illustrated, chapter-by-chapter native reader.
 * Original Compose Canvas drawings are schematic and DRAFT_UNVERIFIED, never
 * copied stock pictures or asserted microscope-derived coordinates.
 * The A5 question bank and previously accepted N1.3 tests are untouched.
 */
private fun view(language: AppLanguage, en: String, ta: String) =
    if (language == AppLanguage.TAMIL) ta else en

private data class DeepDive(
    val heading: BilingualText,
    val explanation: BilingualText,
)

private fun detail(enHeading: String, taHeading: String, en: String, ta: String) =
    DeepDive(BilingualText(enHeading, taHeading), BilingualText(en, ta))

private val elaborations = mapOf(
    "identity-and-habitat" to detail(
        "A complete organism within one cell", "ஒரே செல்லில் முழுமையான உயிரினம்",
        "Observe the distinction between cellular organization and multicellular tissues. Paramecium has no true digestive tract, kidney or nervous system: specialized membrane regions and organelles coordinate these functions. In a pond-water preparation, recorded characters should include cell outline, direction of swimming, reversal and visible vacuolar pulses; none alone is a secure species diagnosis.",
        "பலசெல் உயிரிகளின் திசுக்களுக்கும் பாரமீசியத்தின் ஒற்றைச் செல் அமைப்புக்கும் உள்ள வேறுபாட்டைக் கவனிக்கவும். இதில் உண்மையான செரிமானக் குழாய், சிறுநீரகம் அல்லது நரம்பு மண்டலம் இல்லை. குறிப்பிட்ட செல் படலப் பகுதிகளும் நுண்ணுறுப்புகளும் இப்பணிகளை ஒருங்கிணைக்கின்றன. குளநீர் மாதிரியில் செல் வடிவம், நீந்தும் திசை, பின்னோக்கி நகர்தல், நுண்குமிழ் துடிப்பு ஆகியவற்றைப் பதிவுசெய்யலாம்; இவற்றில் ஒன்றை மட்டும் கொண்டு இனத்தை உறுதிப்படுத்த இயலாது."
    ),
    "pellicle-and-cilia" to detail(
        "Metachronal waves and control of direction", "ஒத்திசை குறுஇழை அலைகளும் திசைக் கட்டுப்பாடும்",
        "Each cilium has an axonemal apparatus supported by basal structures. Its effective and recovery strokes interact with water; adjacent cilia produce coordinated waves. Reorientation is not a change of body organs but a change in ciliary activity. Draw the beat direction as illustrative motion vectors, not as a claim that all cilia move simultaneously.",
        "ஒவ்வொரு குறுஇழையிலும் அடித்தள அமைப்புகளுடன் இணைந்த ஆக்சோனீம் உள்ளது. அதன் இயக்க அசைவும் மீளும் அசைவும் நீருடன் வினைபுரிகின்றன. அடுத்தடுத்த குறுஇழைகள் ஒருங்கிணைந்த அலைகளை உருவாக்குகின்றன. திசைமாற்றம் உடல் உறுப்புகள் மாறுவதால் அல்ல; குறுஇழைகளின் இயக்கம் மாறுவதால் நிகழ்கிறது. அனைத்து குறுஇழைகளும் ஒரே நேரத்தில் அசைகின்றன என்று காட்டாமல், விளக்க இயக்க அம்புகளைப் பயன்படுத்த வேண்டும்."
    ),
    "feeding-and-digestion" to detail(
        "Track one particle from ingestion to egestion", "உணவுத்துகளின் நுழைவு முதல் கழிவு வெளியேற்றம் வரை",
        "Trace the functional sequence: oral groove, oral vestibular region, cytostome, cytopharynx, forming food vacuole, intracellular digestion and egestion at the cytoproct. The cytoproct is not the contractile-vacuole discharge pore. Students should identify which features are directly visible under the classroom microscope and which require a stained specimen or reference micrograph.",
        "செயல்முறையை வரிசைப்படுத்துக: வாய்ப்பள்ளம், வாய்முன் பகுதி, சைட்டோஸ்டோம், சைட்டோஃபாரிங்ஸ், உருவாகும் உணவுக் குமிழ், செல்லுக்குள் செரிமானம், இறுதியில் செல் கழிவுத்துளை வழியாக வெளியேற்றம். செல் கழிவுத்துளை என்பது சுருங்கும் நுண்குமிழின் நீர் வெளியேற்றத் துளை அல்ல. வகுப்பறை நுண்ணோக்கியில் நேரடியாகப் பார்க்கக்கூடிய அமைப்புகளையும் நிறமேற்றப்பட்ட மாதிரி அல்லது ஆதார நுண்படம் தேவைப்படும் அமைப்புகளையும் மாணவர்கள் வேறுபடுத்த வேண்டும்."
    ),
    "osmoregulation" to detail(
        "Water balance is an active physiological process", "நீர்ச்சமநிலை ஒரு செயற்பாட்டு உடலியல் நிகழ்வு",
        "In dilute freshwater, inward osmotic water movement creates an ongoing challenge. Membrane transport, collecting compartments and a contractile-vacuole complex contribute to water elimination. Two complexes may occur in the commonly illustrated species, but their positions, arm number and discharge timing must not be presented as fixed measurements on this draft plate.",
        "கரைசல் செறிவு குறைந்த நன்னீரில் ஒஸ்மோசிஸ் மூலம் உள்ளே வரும் நீர் தொடர்ச்சியான சவாலை ஏற்படுத்துகிறது. படலவழிப் பொருள் கடத்தல், சேகரிப்புப் பகுதிகள் மற்றும் சுருங்கும் நுண்குமிழ் தொகுதி மிகைநீரை வெளியேற்ற உதவுகின்றன. பொதுவாக விளக்கப்படும் இனத்தில் இரண்டு தொகுதிகள் காணப்படலாம்; ஆனால் அவற்றின் அமைவிடம், சேகரிப்புக் கிளைகளின் எண்ணிக்கை, வெளியேற்ற நேரம் ஆகியவற்றை இவ்வரைபடத்தில் அளவிடப்பட்ட உண்மைகளாகக் காட்டக்கூடாது."
    ),
    "nuclear-dimorphism" to detail(
        "Two nuclei, different responsibilities", "இரு உட்கருக்களின் வெவ்வேறு பணிகள்",
        "The macronucleus supports much of the vegetative cell's gene expression; the micronucleus is central to the sexual nuclear processes of conjugation and to inheritance. They should not be labelled male and female nuclei. Binary fission involves coordinated division and redistribution of nuclear and cortical systems.",
        "பேருட்கரு செல் இயல்பாக இயங்கத் தேவையான பல மரபணுச் செயல்பாடுகளை ஆதரிக்கிறது. சிற்றுட்கரு இணைவின்போதான அணுக்கரு நிகழ்வுகளுக்கும் மரபுரிமைக்கும் முக்கியமானது. இவற்றை ஆண், பெண் உட்கருக்கள் என்று அழைக்கக்கூடாது. இருபிளவில் உட்கரு மற்றும் புறப்படல அமைப்புகள் ஒருங்கிணைந்து பிரிந்து பகிரப்படுகின்றன."
    ),
    "conjugation" to detail(
        "Genetic exchange is not immediate multiplication", "மரபணுப் பரிமாற்றம் உடனடி எண்ணிக்கைப் பெருக்கமல்ல",
        "Compatible cells pair temporarily and their micronuclear derivatives participate in meiosis, exchange and nuclear reorganization. Two partners are still two cells at the point of separation. Subsequent divisions and macronuclear development must not be compressed into an instantaneous two-to-four multiplication cartoon.",
        "இணக்கமான செல்கள் தற்காலிகமாக இணைகின்றன. சிற்றுட்கருவிலிருந்து தோன்றும் அணுக்கருக்கள் மியோசிஸ், பரிமாற்றம் மற்றும் அணுக்கரு மறுசீரமைப்பில் பங்கேற்கின்றன. இணைவு முடிந்து பிரியும் தருணத்திலும் இரண்டு இணைச்செல்கள் இரண்டாகவே உள்ளன. பின்னர் நடைபெறும் பிரிவுகளையும் பேருட்கரு வளர்ச்சியையும் உடனடியாக நான்கு செல்களாகும் நிகழ்வாகச் சித்தரிக்கக்கூடாது."
    ),
    "systematics-and-species" to detail(
        "Textbook taxonomy versus evolutionary classification", "பாடநூல் வகைப்பாடும் பரிணாம வகைப்பாடும்",
        "For a modern account place Paramecium among eukaryotic alveolates and ciliates (Ciliophora). Explain that the traditional syllabus category Protozoa is a historical convenience rather than a single natural clade. A genus illustration cannot prove the identity of a particular species without diagnostic evidence.",
        "நவீன வகைப்பாட்டில் பாரமீசியம் யூகேரியோட்டுகளின் ஆல்வியோலேட்டா குழுவிலும் சிலியோஃபோரா தொகுதியிலும் இடம்பெறுகிறது. பாரம்பரிய பாடத்திட்டச் சொல்லான புரோட்டோசோவா ஒரே இயற்கையான இனவழிக் குழுவைச் சுட்டாது என்பதை விளக்கவும். ஒரு பேரினத்தின் வரைபடத்தை மட்டுமே கொண்டு குறிப்பிட்ட இனத்தை உறுதிப்படுத்த இயலாது."
    ),
    "oral-apparatus-details" to detail(
        "Do not confuse an oral channel with a true mouth and intestine", "வாய் அமைப்பை உண்மையான குடலுடன் குழப்ப வேண்டாம்",
        "The oral apparatus is a specialized cortical feeding region. Its membranes, cilia and particle-guiding structures form and detach food vacuoles. A simplified pencil cutaway shows a route through distinct regions, but does not represent a continuous multicellular alimentary canal.",
        "வாயமைப்பு என்பது உணவெடுப்பதற்கான சிறப்புப் புறப்படலப் பகுதி. அதனுடைய படலங்கள், குறுஇழைகள், உணவுத்துகளை வழிநடத்தும் அமைப்புகள் ஆகியவை உணவுக் குமிழ்களை உருவாக்கிப் பிரிக்க உதவுகின்றன. பென்சில் குறுக்குவெட்டு வரைபடம் பகுதிகளின் தொடர்வழியை விளக்கும்; பலசெல் உயிரியின் தொடர்ச்சியான செரிமானக் குழாயைக் குறிக்காது."
    ),
    "cortical-avoidance-response" to detail(
        "Behaviour from excitable membranes", "தூண்டுதலுக்குப் பதிலளிக்கும் படல இயக்கம்",
        "An obstacle can elicit a brief ciliary reversal followed by reorientation. The precise stimulus-response pathway depends on membrane excitability and ion fluxes. Avoid describing this as vision, thought or the command of a nervous system.",
        "ஒரு தடையைச் சந்திப்பதால் குறுஇழைகள் சிறிது நேரம் பின்னோக்கி அசைந்து பின்னர் திசைமாற்றம் ஏற்படலாம். இந்தத் தூண்டுதல்-பதிலளிப்பு வழி செல் படலத் தூண்டுதிறன் மற்றும் அயனி ஓட்டங்களைச் சார்ந்தது. இதை பார்வை, சிந்தனை அல்லது நரம்பு மண்டலக் கட்டளை என்று விளக்கக்கூடாது."
    ),
    "asexual-division-process" to detail(
        "Recognise the division plane", "பிளவுத் தளத்தை அடையாளம் காண்க",
        "In transverse binary fission a constriction divides one parent into two descendants. Distinguish micronuclear mitosis from the different macronuclear division pattern, and note that the daughter cells reorganize parts of their feeding and cortical apparatus. A schematic stage diagram cannot substitute for an observed division series.",
        "குறுக்குத் திசை இருபிளவில் ஓர் தாய்செல்லில் ஏற்படும் சுருக்கம் அதை இரு மகள் செல்களாகப் பிரிக்கிறது. சிற்றுட்கரு மைட்டாசிஸையும் பேருட்கருவின் வேறுபட்ட பிரிவையும் தனித்தறிந்து கூறவும். மகள் செல்களில் உணவமைப்பும் புறப்படலப் பகுதிகளும் மறுசீரமைக்கப்படுகின்றன. விளக்கப் படிநிலை வரைபடம் நேரடியாகக் கவனித்த பிரிவுத் தொடருக்கு மாற்றாகாது."
    ),
    "laboratory-reasoning" to detail(
        "Evidence table: observed, inferred, schematic", "ஆதாரப் பதிவு: பார்த்தது, ஊகித்தது, விளக்கப்படம்",
        "Record cell shape and swimming behaviour as direct observations where visible. Label osmoregulatory function or nuclear exchange as interpretations based on experimental knowledge, not as events confirmed in every wet mount. Annotate any unverified figure with 'schematic/not to scale'.",
        "தெளிவாகத் தெரிந்த செல் வடிவம் மற்றும் நீந்தும் நடத்தையை நேரடிக் கவனிப்புகளாகப் பதிவு செய்க. நீர்ச்சமநிலைப் பணி அல்லது அணுக்கருப் பரிமாற்றம் போன்றவற்றை ஆய்வு அறிவின் அடிப்படையிலான விளக்கங்களாகக் குறிப்பிடுக; ஒவ்வொரு ஈரமாதிரியிலும் நேரடியாக நிரூபித்த நிகழ்வுகளாகக் கருத வேண்டாம். உறுதிசெய்யப்படாத ஒவ்வொரு படத்திலும் 'விளக்க வரைபடம்/அளவுக்கு ஏற்ப அல்ல' என்று குறிக்கவும்."
    ),
    "classroom-observation" to detail(
        "A reproducible observation protocol", "மீண்டும் செய்யக்கூடிய நுண்ணோக்கி நடைமுறை",
        "Begin with low magnification to locate moving ciliates, then adjust illumination and magnification without crushing the specimen. Sketch one observed cell; record scale only if calibrated. Compare your observation with the schematic plate, stating which structures were not resolved. Handle live samples according to institutional laboratory hygiene procedures.",
        "முதலில் குறைந்த உருப்பெருக்கத்தில் நகரும் குறுஇழை உயிரிகளைத் தேடவும்; பின்னர் மாதிரியை நசுக்காமல் ஒளியையும் உருப்பெருக்கத்தையும் சரிசெய்யவும். கண்ட ஒரு செல்லை வரைந்து, அளவுத்திருத்தம் செய்திருந்தால் மட்டுமே அளவைக் குறிப்பிடவும். பார்த்த அமைப்புகளை விளக்கப் படத்துடன் ஒப்பிட்டு தெளிவாகத் தெரியாதவற்றையும் பதிவு செய்க. உயிர்மாதிரிகளை ஆய்வகச் சுகாதார நடைமுறைகளின்படி கையாளவும்."
    ),
)

private val teachingSubheads: Map<String, List<BilingualText>> = mapOf(
    "identity-and-habitat" to listOf(
        BilingualText("Habitat and single-cell organization", "வாழிடமும் ஒருசெல் அமைப்பும்"),
        BilingualText("Historical Protozoa terminology", "பாரம்பரிய புரோட்டோசோவா சொல்")),
    "pellicle-and-cilia" to listOf(
        BilingualText("Pellicle and surface cilia", "பெல்லிக்கிளும் மேற்பரப்புக் குறுஇழைகளும்"),
        BilingualText("Metachronal movement and avoidance", "ஒத்திசை இயக்கமும் தடையைத் தவிர்ப்பதும்"),
        BilingualText("Ciliary food currents", "குறுஇழை உணவோட்டங்கள்")),
    "feeding-and-digestion" to listOf(
        BilingualText("Ingestion through the oral apparatus", "வாயமைப்பின் வழி உணவெடுப்பு"),
        BilingualText("Vacuolar digestion and egestion", "நுண்குமிழ்ச் செரிமானமும் கழிவு வெளியேற்றமும்")),
    "osmoregulation" to listOf(
        BilingualText("The freshwater osmotic challenge", "நன்னீரின் ஒஸ்மோசிஸ் சவால்"),
        BilingualText("Specialized collecting complexes", "சிறப்புச் சேகரிப்புத் தொகுதிகள்"),
        BilingualText("Active membrane transport", "செயற்பாட்டு படலக் கடத்தல்")),
    "nuclear-dimorphism" to listOf(
        BilingualText("Somatic and germline nuclear roles", "உடலியக்க மற்றும் மரபுவழி உட்கருப் பணிகள்"),
        BilingualText("Cell multiplication by fission", "இருபிளவு மூலம் செல் பெருக்கம்")),
    "conjugation" to listOf(
        BilingualText("Pairing and micronuclear exchange", "இணைவு மற்றும் சிற்றுட்கருப் பரிமாற்றம்"),
        BilingualText("Nuclear reorganisation without immediate fission", "உடனடி இருபிளவின்றி உட்கரு மறுசீரமைப்பு")),
    "systematics-and-species" to listOf(
        BilingualText("Alveolates, ciliates and taxonomic placement", "ஆல்வியோலேட்டுகள், சிலியேட்டுகள், வகைப்பாட்டு இடம்"),
        BilingualText("Species identity and microscope evidence", "இன அடையாளமும் நுண்ணோக்கி ஆதாரமும்")),
    "oral-apparatus-details" to listOf(
        BilingualText("Oral groove, cytostome and cytopharynx", "வாய்ப்பள்ளம், சைட்டோஸ்டோம், சைட்டோஃபாரிங்ஸ்"),
        BilingualText("Food vacuole maturation and cytoproct", "உணவுக் குமிழ் முதிர்வும் செல் கழிவுத்துளையும்")),
    "cortical-avoidance-response" to listOf(
        BilingualText("Basal bodies and ciliary reversal", "அடித்தளத் துகள்களும் குறுஇழைத் திருப்பமும்"),
        BilingualText("How to interpret slow-motion animation", "மெதுவாக்கப்பட்ட இயக்கப்படத்தின் விளக்க வரம்புகள்")),
    "asexual-division-process" to listOf(
        BilingualText("Micronuclear and macronuclear division", "சிற்றுட்கரு, பேருட்கரு பிரிவு"),
        BilingualText("Transverse cytokinesis and daughter cells", "குறுக்குச் செல் பிரிவும் மகள் செல்களும்")),
    "laboratory-reasoning" to listOf(
        BilingualText("What a wet mount can actually show", "ஈரமாதிரியில் நேரடியாகக் காணக்கூடியவை"),
        BilingualText("Reasoned predictions and misconceptions", "கணிப்புகளும் தவறான கருத்துகளும்")),
    "classroom-observation" to listOf(
        BilingualText("Observe living ciliates under the microscope", "உயிருள்ள சிலியேட்டுகளை நுண்ணோக்கியில் காணல்"),
        BilingualText("Connect visual evidence to cell physiology", "காட்சி ஆதாரத்தையும் செல் உடலியலையும் இணைத்தல்"))
)

private fun DrawScope.pencilBody(cx: Float, cy: Float, w: Float, h: Float) {
    drawOval(PencilAtlasPalette.cell, topLeft = Offset(cx - w / 2f, cy - h / 2f), size = Size(w, h))
    drawOval(PencilAtlasPalette.graphite, topLeft = Offset(cx - w / 2f, cy - h / 2f),
        size = Size(w, h), style = Stroke(2.6f))
    drawOval(PencilAtlasPalette.hatch, topLeft = Offset(cx - w / 2f + 4f, cy - h / 2f + 3f),
        size = Size(w - 8f, h - 6f), style = Stroke(2f))
    for (i in 0 until 64) {
        val angle = 2 * PI * i / 64
        val dx = cos(angle).toFloat()
        val dy = sin(angle).toFloat()
        val base = Offset(cx + dx * w * .50f, cy + dy * h * .50f)
        drawLine(PencilAtlasPalette.mid, base,
            base + Offset(dx * 11f - dy * 3f, dy * 11f + dx * 3f), 1.25f)
    }
}

@Composable
private fun PencilMechanismFigure(figure: String, language: AppLanguage) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF8F3))) {
        Column(Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(view(language, "ORIGINAL GRAPHITE STUDY PLATE · schematic / not to scale",
                "அசல் பென்சில் ஆய்வுப் படம் · விளக்க வரைபடம் / அளவுக்கு ஏற்ப அல்ல"),
                style = MaterialTheme.typography.labelSmall, color = Color(0xFF60564D))
            Canvas(Modifier.fillMaxWidth().height(240.dp).testTag("r14-pencil-" + figure)) {
                val w = size.width
                val h = size.height
                val g = PencilAtlasPalette.graphite
                val s = PencilAtlasPalette.selected
                drawRect(PencilAtlasPalette.paper)
                when (figure) {
                    "fission" -> {
                        pencilBody(w * .29f, h * .52f, w * .34f, h * .48f)
                        pencilBody(w * .73f, h * .52f, w * .34f, h * .48f)
                        drawCircle(g, radius = h * .050f, center = Offset(w * .26f, h * .48f))
                        drawCircle(g, radius = h * .050f, center = Offset(w * .70f, h * .48f))
                        drawLine(s, Offset(w * .48f, h * .28f),
                            Offset(w * .55f, h * .28f), 5f)
                    }
                    "conjugation" -> {
                        pencilBody(w * .51f, h * .35f, w * .56f, h * .29f)
                        pencilBody(w * .51f, h * .68f, w * .56f, h * .29f)
                        drawCircle(g, radius = h * .05f, center = Offset(w * .40f, h * .33f))
                        drawCircle(g, radius = h * .05f, center = Offset(w * .62f, h * .69f))
                        drawLine(s, Offset(w * .49f, h * .48f),
                            Offset(w * .54f, h * .55f), 4f)
                    }
                    "oral" -> {
                        pencilBody(w * .51f, h * .51f, w * .70f, h * .70f)
                        val a = Offset(w * .47f, h * .72f)
                        val b = Offset(w * .53f, h * .57f)
                        val c = Offset(w * .60f, h * .44f)
                        drawLine(s, a, b, 6f)
                        drawLine(s, b, c, 5f)
                        drawCircle(g, radius = h * .065f, center = Offset(w * .65f, h * .38f),
                            style = Stroke(3f))
                        for (i in 0 until 5) {
                            drawCircle(g, radius = 2.8f,
                                center = Offset(w * (.21f + i * .04f), h * (.64f + i * .014f)))
                        }
                    }
                    "vacuole" -> {
                        pencilBody(w * .51f, h * .51f, w * .70f, h * .70f)
                        for ((x, y) in listOf(.32f to .39f, .73f to .59f)) {
                            val center = Offset(w * x, h * y)
                            val radius = h * .042f
                            drawCircle(g, radius = radius, center = center, style = Stroke(2.8f))
                            for (i in 0 until 7) {
                                val angle = 2 * PI * i / 7
                                val d = Offset(cos(angle).toFloat(), sin(angle).toFloat())
                                drawLine(g, center + d * (radius + 2f),
                                    center + d * (radius + 17f), 2.5f)
                            }
                        }
                        drawCircle(PencilAtlasPalette.hatch, radius = h * .06f,
                            center = Offset(w * .51f, h * .65f))
                    }
                    else -> {
                        pencilBody(w * .51f, h * .51f, w * .70f, h * .70f)
                        drawOval(PencilAtlasPalette.hatch,
                            topLeft = Offset(w * .40f, h * .36f),
                            size = Size(w * .20f, h * .20f))
                        drawOval(g, topLeft = Offset(w * .40f, h * .36f),
                            size = Size(w * .20f, h * .20f), style = Stroke(2f))
                        drawCircle(g, radius = h * .03f, center = Offset(w * .61f, h * .47f))
                        drawLine(s, Offset(w * .40f, h * .74f),
                            Offset(w * .51f, h * .58f), 4f)
                    }
                }
            }
            Text(when (figure) {
                "vacuole" -> view(language,
                    "Two collecting complexes (radial graphite marks) are separate from the unarmed food vacuole.",
                    "இரண்டு சேகரிப்புத் தொகுதிகள் (கதிர்வடிவப் பென்சில் குறிகள்) கிளைகளற்ற உணவுக் குமிழிலிருந்து வேறுபட்டவை.")
                "oral" -> view(language, "Oral groove → cytostome/cytopharynx → food vacuole.",
                    "வாய்ப்பள்ளம் → சைட்டோஸ்டோம்/சைட்டோஃபாரிங்ஸ் → உணவுக் குமிழ்.")
                "fission" -> view(language,
                    "Transverse fission produces two daughter cells; arrows are explanatory, not time-calibrated.",
                    "குறுக்குத் திசை இருபிளவு இரண்டு மகள் செல்களை உருவாக்கும்; அம்பு நேர அளவீடு அல்ல.")
                "conjugation" -> view(language, "Temporary pairing and genetic exchange do not immediately increase cell number.",
                    "தற்காலிக இணைவும் மரபணுப் பரிமாற்றமும் உடனடியாகச் செல் எண்ணிக்கையை அதிகரிக்காது.")
                else -> view(language,
                    "Pencil outline: ciliation, nuclear structures and oral region shown schematically.",
                    "பென்சில் வரைபடத்தில் குறுஇழை அமைப்பு, உட்கருக்கள், வாய்ப்பகுதி விளக்கமாகக் காட்டப்பட்டுள்ளன.")
            }, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

/**
 * Textbook-style main experience: original teaching prose followed by integrated
 * native illustrations; questions are available separately as practice only.
 */
@Composable
internal fun ParameciumTextbookReader(
    language: AppLanguage,
    speak: (BilingualText) -> Unit,
    onAnatomy: () -> Unit,
    onSimulation: () -> Unit,
) {
    val lesson = NativeLessonDrafts.paramecium
    var chapter by rememberSaveable { mutableIntStateOf(0) }
    var contentsExpanded by rememberSaveable { mutableStateOf(false) }
    val plateRequester = remember { BringIntoViewRequester() }
    val plateScope = rememberCoroutineScope()
    val section = lesson.sections[chapter]
    val topics = teachingSubheads[section.id].orEmpty()
    val extra = elaborations[section.id]

    Text(lesson.title.value(language), style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold, modifier = Modifier.testTag("r1-study-title"))
    Text(view(language,
        "Illustrated undergraduate textbook | 12 guided sections | English / தமிழ்",
        "படவிளக்க இளநிலைப் பாடநூல் | 12 வழிகாட்டுப் பகுதிகள் | ஆங்கிலம் / தமிழ்"),
        style = MaterialTheme.typography.titleMedium)
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F5F3))) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(view(language, "What you will learn", "கற்றல் நோக்கங்கள்"),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Text(view(language,
                "Recognise the cortical organelles; trace feeding and digestion; explain osmotic water control and ciliary locomotion; distinguish transverse fission from conjugation; relate microscopy observations to evidence.",
                "புறப்படல நுண்ணுறுப்புகளை அடையாளம் காணுதல்; உணவூட்டத்தையும் செரிமானத்தையும் வரிசைப்படுத்துதல்; நீர்ச்சமநிலையையும் குறுஇழை இயக்கத்தையும் விளக்குதல்; குறுக்கு இருபிளவையும் இணைவையும் வேறுபடுத்துதல்; நுண்ணோக்கிக் கவனிப்பை ஆதாரத்துடன் இணைத்தல்."))
            Text(view(language,
                "Scientific and Tamil review pending: original diagrams are teaching schematics, not certified microscopic plates.",
                "அறிவியல் மற்றும் தமிழ் மதிப்பாய்வு நிலுவை: அசல் படங்கள் கற்பித்தல் விளக்கப்படங்கள்; சான்றளிக்கப்பட்ட நுண்படங்கள் அல்ல."),
                style = MaterialTheme.typography.bodySmall)
        }
    }

    // Put the full original hand-drawn, interactive atlas ABOVE the lesson
    // paragraphs on the first page, instead of burying it after exam questions.
    if (chapter == 0) {
        Button(
            onClick = { plateScope.launch { plateRequester.bringIntoView() } },
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                .testTag("r14-jump-to-pencil-plate"),
        ) {
            Text(view(language, "VIEW THE ORIGINAL PENCIL ANATOMY PLATE",
                "அசல் பென்சில் உடலமைப்புப் படத்தைப் பார்"))
        }
        ParameciumExternalCanvas(
            language = language,
            compact = true,
            plateModifier = Modifier.bringIntoViewRequester(plateRequester),
            onOrganSelected = { organId ->
                speak(ParameciumLearningEngine.organ(organId).narration)
            },
        )
    }

    OutlinedButton(onClick = { contentsExpanded = !contentsExpanded },
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r14-contents-toggle")) {
        Text(view(language,
            if (contentsExpanded) "Hide chapter contents" else "Browse 12 lesson sections",
            if (contentsExpanded) "பாடப்பொருளடக்கத்தை மறை" else "12 பாடப்பகுதிகளைப் பார்"))
    }
    if (contentsExpanded) {
        Card {
            Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                lesson.sections.forEachIndexed { index, item ->
                    OutlinedButton(onClick = {
                        chapter = index
                        contentsExpanded = false
                    }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("r14-open-section-" + item.id)) {
                        Text((index + 1).toString() + ". " + item.heading.value(language))
                    }
                }
            }
        }
    }
    Text(view(language, "SECTION ", "பகுதி ") + (chapter + 1).toString() +
        " / " + lesson.sections.size.toString(),
        style = MaterialTheme.typography.labelLarge)
    Card(modifier = Modifier.fillMaxWidth().testTag("r1-study-" + section.id),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF9))) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
            Text(section.heading.value(language), style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold, modifier = Modifier.testTag("r14-section-heading"))
            section.paragraphs.forEachIndexed { index, paragraph ->
                Text((chapter + 1).toString() + "." + (index + 1).toString() +
                    "  " + (topics.getOrNull(index)?.value(language)
                    ?: view(language, "Mechanism and significance", "செயல்முறையும் முக்கியத்துவமும்")),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)
                Text(paragraph.value(language), style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 25.sp)
            }
            if (extra != null) {
                Text((chapter + 1).toString() + "." + (section.paragraphs.size + 1).toString() +
                    "  " + extra.heading.value(language),
                    style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(extra.explanation.value(language), style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 25.sp)
                OutlinedButton(onClick = { speak(extra.explanation) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("r14-listen-chapter")) {
                    Text(view(language, "Listen to this explanation", "இவ்விளக்கத்தைக் கேள்"))
                }
            }
        }
    }
    // A complete pencil-illustrated ciliary mechanism occupies the native
    // textbook reading flow, reusing the authoritative four-stage engine.
    // Feeding keeps its separate R1.5 plate and unchanged acceptance tests.
    if (section.id == "pellicle-and-cilia" ||
        section.id == "cortical-avoidance-response") {
        ParameciumCiliaryTextbookPlate(language, speak)
    } else if (section.id == "feeding-and-digestion" ||
        section.id == "oral-apparatus-details") {
        ParameciumFeedingPathwayPlate(language, speak)
    } else if (chapter != 0) {
        val figure = when (section.id) {
            "osmoregulation" -> "vacuole"
            "asexual-division-process", "nuclear-dimorphism" -> "fission"
            "conjugation" -> "conjugation"
            else -> "overview"
        }
        PencilMechanismFigure(figure, language)
    }
    if (chapter == 0) {
        Text(view(language,
            "Anatomical pencil plate: touch individual organs in the Anatomy section for highlighting and narration.",
            "உறுப்பு பென்சில் படம்: உறுப்புகளைத் தொட்டு ஒளிரச்செய்யவும் ஒலிவிளக்கம் பெறவும் உடலமைப்புப் பகுதிக்குச் செல்லவும்."),
            style = MaterialTheme.typography.bodyMedium)
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = onAnatomy,
            modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                .testTag("r14-from-study-anatomy")) {
            Text(view(language, "Anatomy plates", "உடலமைப்புப் படங்கள்"))
        }
        Button(onClick = onSimulation, modifier = Modifier.weight(1f).heightIn(min = 48.dp)
            .testTag("r14-from-study-simulation")) {
            Text(view(language, "Simulate", "இயக்கக் காட்சி"))
        }
    }
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F1EC))) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(view(language, "Source reading and scientific limitations",
                "ஆதார வாசிப்பும் அறிவியல் வரம்புகளும்"),
                style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            section.scientificSources.forEach { Text(it, style = MaterialTheme.typography.bodySmall) }
            Text(view(language,
                "Do not interpret schematic geometry, organ counts or timing as microscopy measurements.",
                "விளக்கப்பட வடிவம், உறுப்புகளின் எண்ணிக்கை, நேர அளவு ஆகியவற்றை நுண்ணோக்கி அளவீடுகளாகக் கருத வேண்டாம்."),
                style = MaterialTheme.typography.bodySmall)
        }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = { chapter = (chapter - 1).coerceAtLeast(0) },
            enabled = chapter > 0, modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                .testTag("r14-previous-section")) {
            Text(view(language, "Previous section", "முந்தைய பகுதி"))
        }
        Button(onClick = { chapter = (chapter + 1).coerceAtMost(lesson.sections.lastIndex) },
            enabled = chapter < lesson.sections.lastIndex,
            modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                .testTag("r14-next-section")) {
            Text(view(language, "Next section", "அடுத்த பகுதி"))
        }
    }
}
