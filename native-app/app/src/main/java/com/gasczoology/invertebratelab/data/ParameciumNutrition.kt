package com.gasczoology.invertebratelab.data

import kotlin.math.min

data class NutritionReading(val id: String, val heading: BilingualText, val paragraphs: List<BilingualText>)
data class NutritionStructure(val id: String, val name: BilingualText, val explanation: BilingualText)
data class NutritionStage(val id: String, val heading: BilingualText, val explanation: BilingualText)

/** Schema 1 is extended additively; restored physiological playback is always paused. */
data class NutritionProgress(
    val stageId: String = "current", val readingId: String = "5.1",
    val selectedStructure: String = "oral-groove", val phasePermille: Int = 0,
    val reducedMotion: Boolean = false,
) {
    fun normalized() = copy(
        stageId = stageId.takeIf { id -> ParameciumNutrition.stages.any { it.id == id } } ?: "current",
        readingId = readingId.takeIf { id -> ParameciumNutrition.readings.any { it.id == id } } ?: "5.1",
        selectedStructure = selectedStructure.takeIf { id -> ParameciumNutrition.structures.any { it.id == id } } ?: "oral-groove",
        phasePermille = phasePermille.coerceIn(0, 1000),
    )
}

/** Original undergraduate prose. Observed structures and mechanistic interpretation are distinguished. */
object ParameciumNutrition {
    const val reviewStatus = "DRAFT_UNVERIFIED"
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    val structures = listOf(
        NutritionStructure("oral-groove", b("G · Oral groove and vestibule", "G · வாய்ப்பள்ளமும் வாய்ப்புற முன்னறையும்"), b(
            "The ventral oral depression leads into a ciliated vestibular region. These are regions of one cell's feeding apparatus, not a multicellular alimentary canal. The whole-cell drawing establishes orientation only; the enlarged oral inset is a schematic cutaway, not a reconstruction of every membrane fold.",
            "வயிற்றுப்புற வாய்ப்பள்ளம், குறுஇழைகள் கொண்ட வாய்ப்புற முன்னறைக்கு வழிவகுக்கிறது. இவை ஒரே செல்லின் உணவெடுக்கும் அமைப்பின் பகுதிகள்; பலசெல் உயிரியின் உணவுக்குழாய் அல்ல. முழுச் செல் படம் திசையை மட்டும் காட்டுகிறது. பெரிதாக்கிய வாய்ப்புறச் சிறுபடம் விளக்க வெட்டுத் தோற்றமே; ஒவ்வொரு சவ்வு மடிப்பின் துல்லிய மறுகட்டமைப்பு அல்ல.")),
        NutritionStructure("oral-cilia", b("O · Oral ciliary apparatus", "O · வாய்ப்புறக் குறுஇழை அமைப்பு"), b(
            "Specialized oral ciliary fields move water and suitably sized particles towards the feeding opening. Their organization differs from the somatic ciliary rows used in swimming. The paired pencil rows represent feeding activity; their number, three-dimensional beat paths and flow velocities are not measured here.",
            "சிறப்பமைந்த வாய்ப்புறக் குறுஇழைத் தொகுதிகள் நீரையும் ஏற்ற அளவுள்ள துகள்களையும் உணவு நுழைவுத் தளம் நோக்கிக் கொண்டு செல்கின்றன. அவற்றின் அமைப்பு நீந்த உதவும் உடற்புறக் குறுஇழை வரிசைகளிலிருந்து வேறுபடுகிறது. பென்சில் வரிசைகள் உணவெடுக்கும் செயலைக் குறிக்கின்றன; எண்ணிக்கை, முப்பரிமாண அசைவுப் பாதை, ஓட்ட வேகம் ஆகியவை அளவிடப்படவில்லை.")),
        NutritionStructure("cytostome", b("M · Cytostome: cell mouth", "M · சைட்டோஸ்டோம்: செல் வாய்"), b(
            "The cytostome is the specialized cell-mouth region at the entrance to the cytopharyngeal apparatus. It must be distinguished from the broad external oral groove. The label identifies a functional region; a light-microscope outline alone cannot resolve its membrane-level boundaries.",
            "சைட்டோஸ்டோம் என்பது சைட்டோஃபாரிங்ஸ் அமைப்பின் நுழைவிலுள்ள சிறப்பமைந்த செல் வாய்ப் பகுதி. வெளிப்புறப் பரந்த வாய்ப்பள்ளத்திலிருந்து இதை வேறுபடுத்த வேண்டும். குறி ஒரு செயல்பாட்டுப் பகுதியை அடையாளப்படுத்துகிறது; ஒளி நுண்ணோக்கித் தோற்றம் மட்டும் அதன் சவ்வளவிலான எல்லைகளைத் தெளிவாக்காது.")),
        NutritionStructure("cytopharynx", b("F · Cytopharynx and forming vacuole", "F · சைட்டோஃபாரிங்ஸும் உருவாகும் உணவு நுண்குமிழும்"), b(
            "At the cytopharyngeal region food is accumulated within membrane that expands to form a digestive vacuole. Electron microscopy of P. caudatum documents microtubular ribbons and associated membrane vesicles. The growth and release inset omits much ultrastructure; it does not depict a permanent tube running through the cytoplasm.",
            "சைட்டோஃபாரிங்ஸ் பகுதியில், விரிவடைந்து உணவு நுண்குமிழாகும் சவ்வுக்குள் உணவு சேர்கிறது. P. caudatum மின்னணு நுண்ணோக்கிப் படங்களில் நுண்குழல் பட்டைகளும் தொடர்புடைய சவ்வுச் சிறுகுமிழ்களும் பதிவாகியுள்ளன. வளர்ந்து பிரியும் சிறுபடம் பல நுண்ணமைப்புகளை விடுகிறது; சைட்டோபிளாஸ்மத்தின் ஊடே செல்லும் நிரந்தரக் குழாய் அல்ல.")),
        NutritionStructure("vacuole", b("V · Membrane-bound food vacuole", "V · சவ்வால் சூழப்பட்ட உணவு நுண்குமிழ்"), b(
            "A newly released food vacuole encloses particulate food and fluid within a membrane. Its lumen is separate from the cytosol. Its later maturation changes both contents and membrane; it must not be confused with the radiating contractile-vacuole collecting complex. The separate circular inset is enlarged independently of the whole cell.",
            "புதிதாகப் பிரிந்த உணவு நுண்குமிழ், துகள் உணவையும் திரவத்தையும் சவ்வுக்குள் அடைக்கிறது. அதன் உட்குழி சைட்டோசாலிலிருந்து தனியாக உள்ளது. பின்னர் முதிர்வின் போது உள்ளடக்கமும் சவ்வும் மாறுகின்றன. ஆரக் கால்வாய்கள் கொண்ட சுருங்கு நுண்குமிழ்த் தொகுப்புடன் இதைக் குழப்பக்கூடாது. வட்டச் சிறுபடம் முழுச் செல்லிலிருந்து தனியாகப் பெரிதாக்கப்பட்டுள்ளது.")),
    )
    val readings = listOf(
        NutritionReading("5.1", b("5.1 · Nutrition and particle capture", "5.1 · ஊட்டமுறையும் உணவுத்துகள் பிடிப்பும்"), listOf(b(
            "Paramecium caudatum is a heterotrophic ciliate: it obtains organic nutrients from other organisms and particulate material rather than synthesizing all its food by photosynthesis. Bacteria are important food resources, while other suitably sized particles can be ingested. Ingestion does not guarantee nutritional value; prey type, particle properties and culture conditions affect feeding and growth.",
            "Paramecium caudatum பிற ஊட்ட உயிரியாகும். ஒளிச்சேர்க்கை மூலம் தன் உணவு முழுவதையும் உருவாக்குவதற்குப் பதிலாக, பிற உயிரிகளிலிருந்தும் துகள் பொருள்களிலிருந்தும் கரிம ஊட்டச்சத்துகளைப் பெறுகிறது. பாக்டீரியாக்கள் முக்கிய உணவுவளம்; ஏற்ற அளவுள்ள பிற துகள்களும் உட்கொள்ளப்படலாம். உட்கொள்ளப்பட்ட அனைத்தும் ஊட்டமளிப்பவை அல்ல. உணவுயிரி வகை, துகளின் பண்பு, வளர்ப்புச் சூழல் ஆகியவை உணவெடுப்பையும் வளர்ச்சியையும் பாதிக்கின்றன."), b(
            "Oral cilia produce local currents that bring suspended particles towards the oral groove and vestibule. Particle capture, entry into a food vacuole, enzymatic breakdown, uptake of soluble products and release of residues are distinct processes. Holozoic nutrition is the traditional textbook term for this sequence of ingestion and intracellular processing; it does not imply an animal-like gut or digestive gland.",
            "வாய்ப்புறக் குறுஇழைகள் உருவாக்கும் உள்ளூர் நீரோட்டம் மிதக்கும் துகள்களை வாய்ப்பள்ளம் மற்றும் முன்னறை நோக்கிக் கொண்டு செல்கிறது. துகள் பிடிப்பு, உணவு நுண்குமிழுக்குள் நுழைவு, நொதி வழிச் சிதைவு, கரையக்கூடிய விளைபொருள் உறிஞ்சல், எச்ச வெளியேற்றம் ஆகியவை தனித்தனி செயல்கள். இவ்வுணவு உட்கொள்ளல் மற்றும் செல்லுக்குள் செயலாக்க வரிசைக்கான பாரம்பரியப் பாடநூல் சொல் holozoic nutrition ஆகும். விலங்கின் குடல் அல்லது செரிமானச் சுரப்பி போன்ற அமைப்புகள் இருப்பதாக அது பொருள்படாது."), b(
            "A coloured particle in a vacuole demonstrates uptake, not successful digestion or assimilation. An undergraduate observation should record the preparation, reference species, elapsed observation time and the limits of optical resolution. Compare treated and control preparations before assigning a functional cause. Do not apply algal symbiosis in P. bursaria to P. caudatum without species-specific evidence.",
            "நுண்குமிழுக்குள் நிறத் துகள் காணப்படுவது உட்கொள்ளலைக் காட்டுகிறது; வெற்றிகரமான செரிமானத்தையோ தன்மயமாக்கலையோ நிரூபிக்காது. மாணவர் பதிவில் தயாரிப்பு முறை, ஆய்வு இனம், பார்வையிட்ட நேரம், ஒளியியல் தெளிவுத்திறன் வரம்பு ஆகியவை இடம்பெற வேண்டும். செயல்பாட்டுக் காரணம் கூறும் முன் சோதனை மற்றும் கட்டுப்பாட்டுத் தயாரிப்புகளை ஒப்பிட வேண்டும். P. bursaria இன் பாசிக் கூட்டுயிர்வாழ்வை இனத்திற்கான சான்றின்றி P. caudatum க்குப் பொருத்தக்கூடாது."))),
        NutritionReading("5.2", b("5.2 · Oral apparatus and vacuole formation", "5.2 · வாய்ப்புற அமைப்பும் உணவு நுண்குமிழ் உருவாதலும்"), listOf(b(
            "Follow the oral groove into the vestibular region, then distinguish the cytostome from the cytopharyngeal apparatus. These names describe related regions, not four interchangeable labels. Specialized ciliary structures and membrane-associated architecture direct food into a forming vacuole. The native cutaway marks their relationships, while enlarged pencil linework deliberately omits unverified fold dimensions and ciliary counts.",
            "வாய்ப்பள்ளத்திலிருந்து முன்னறைப் பகுதியைத் தொடர்ந்து, சைட்டோஸ்டோமையும் சைட்டோஃபாரிங்ஸ் அமைப்பையும் வேறுபடுத்துக. இப்பெயர்கள் தொடர்புடைய பகுதிகளைக் குறிக்கின்றன; ஒன்றுக்கொன்று மாற்றிப் பயன்படுத்தும் நான்கு பெயர்கள் அல்ல. சிறப்பமைந்த குறுஇழைகளும் சவ்வுடன் தொடர்புடைய அமைப்பும் உணவை உருவாகும் நுண்குமிழுக்குள் செலுத்துகின்றன. இயல்புநிலை வெட்டுத் தோற்றம் அவற்றின் தொடர்புகளைக் காட்டுகிறது. உறுதிசெய்யப்படாத மடிப்பு அளவுகளும் குறுஇழை எண்ணிக்கையும் பென்சில் படத்தில் தவிர்க்கப்பட்டுள்ளன."), b(
            "Food-vacuole formation requires membrane supply as well as accumulation of particles. Allen's electron-microscope study of P. caudatum describes vesicles associated with microtubular ribbons in the oral apparatus and interprets them in membrane recycling. A growing vacuole is released into the cytoplasm as a separate membrane-bound compartment. Its lumen remains separated from the cytosol; swallowed particles are not deposited loose among cellular organelles.",
            "உணவு நுண்குமிழ் உருவாவதற்கு துகள் சேர்வதுடன் சவ்வுப் பொருள் வழங்கலும் தேவை. P. caudatum இல் Allen மேற்கொண்ட மின்னணு நுண்ணோக்கி ஆய்வு, வாய்ப்புற அமைப்பின் நுண்குழல் பட்டைகளுடன் தொடர்புடைய சிறுகுமிழ்களை விவரித்து, அவற்றைச் சவ்வு மறுசுழற்சியுடன் தொடர்புபடுத்துகிறது. வளர்ந்த நுண்குமிழ் தனிச் சவ்வுப் பகுதியாகச் சைட்டோபிளாஸ்மத்திற்குள் பிரிகிறது. அதன் உட்குழி சைட்டோசாலிலிருந்து பிரிந்தே உள்ளது; விழுங்கிய துகள்கள் செல் நுண்ணுறுப்புகளுக்கிடையே சிதறிவிடுவதில்லை."), b(
            "The animated formation view separates growth from release for teaching. It is not a calibrated movie of membrane scission, and the circular shape is an explanatory convention. Vacuolar circulation can be observed, but a universal circular itinerary or constant travel speed must not be inferred. Later acidification, digestion and membrane retrieval require their own evidence rather than being deduced from motion alone.",
            "உருவாதல் இயக்கப்படம் கற்பித்தலுக்காக வளர்தலையும் பிரிதலையும் வேறுபடுத்துகிறது. அது சவ்வுப் பிரிவின் அளவுத்திருத்தம் செய்யப்பட்ட திரைப்படம் அல்ல; வட்ட வடிவம் ஒரு விளக்க மரபு. நுண்குமிழ் நகர்வைக் காணலாம். ஆனால் எல்லா நுண்குமிழ்களுக்கும் ஒரே வட்டப் பாதையோ நிலையான பயண வேகமோ இருப்பதாக முடிவு செய்யக்கூடாது. பின்னர் நிகழும் அமிலமாதல், செரிமானம், சவ்வு மீட்பு ஆகியவற்றுக்கு தனித்தனி சான்றுகள் தேவை; நகர்வை மட்டும் வைத்து அவற்றை ஊகிக்க முடியாது."))),
    )
    val stages = listOf(
        NutritionStage("current", b("Oral feeding current", "வாய்ப்புற உணவெடுக்கும் நீரோட்டம்"), b(
            "Oral ciliary activity carries suspended particles towards the oral apparatus. The animated dots indicate direction only, not resolved microorganisms or measured flow. Capture is distinguished from digestion, which takes place later in a membrane-bound vacuole.",
            "வாய்ப்புறக் குறுஇழை இயக்கம் மிதக்கும் துகள்களை உணவெடுக்கும் அமைப்பு நோக்கிக் கொண்டு செல்கிறது. இயங்கும் புள்ளிகள் திசையை மட்டும் குறிக்கின்றன; தெளிவாகக் காணப்பட்ட நுண்ணுயிரிகளோ அளவிடப்பட்ட ஓட்டமோ அல்ல. பிடிப்பு, பின்னர் சவ்வால் சூழப்பட்ட நுண்குமிழில் நடைபெறும் செரிமானத்திலிருந்து வேறுபடுகிறது.")),
        NutritionStage("entry", b("Cytostome and cytopharyngeal entry", "செல் வாய் மற்றும் சைட்டோஃபாரிங்ஸ் நுழைவு"), b(
            "Particles enter the forming vacuole through the specialized oral region. Cytostome and cytopharynx are identified separately from the external groove. A continuous multicellular digestive tract is not present, and this inset must not be read as one.",
            "சிறப்பமைந்த வாய்ப்புறப் பகுதி வழியாகத் துகள்கள் உருவாகும் நுண்குமிழுக்குள் நுழைகின்றன. சைட்டோஸ்டோமும் சைட்டோஃபாரிங்ஸும் வெளிப்புற வாய்ப்பள்ளத்திலிருந்து தனியாக அடையாளப்படுத்தப்பட்டுள்ளன. தொடர்ச்சியான பலசெல் உணவுக்குழாய் இல்லை; இச்சிறுபடத்தை அவ்வாறு புரிந்துகொள்ளக்கூடாது.")),
        NutritionStage("formation", b("Food-vacuole growth and release", "உணவு நுண்குமிழ் வளர்தலும் பிரிதலும்"), b(
            "Membrane surrounds accumulated food; the growing vacuole is then released as a separate compartment. Growth, a remaining attachment and a detached completed pose are distinguishable native frames. Their proportions and time intervals are schematic, while the separation of lumen from cytosol is essential.",
            "சேர்ந்த உணவைச் சவ்வு சூழ்கிறது; வளர்ந்த நுண்குமிழ் பின்னர் தனிப் பகுதியாகப் பிரிகிறது. வளர்தல், மீதமுள்ள இணைப்பு, பிரிந்த முடிவுத் தோற்றம் ஆகியவை வேறுபடும் இயல்புநிலைக் காட்சிகள். அவற்றின் விகிதங்களும் நேர இடைவெளிகளும் விளக்கத்திற்கானவை. உட்குழியும் சைட்டோசாலும் பிரிந்திருப்பது அடிப்படையான கருத்து.")),
    )
    val sourceNotes = listOf(
        "Allen 1974 · P. caudatum oral ultrastructure and membrane supply · PMID 4373478 · https://pmc.ncbi.nlm.nih.gov/articles/PMC2109358/",
        "Allen microscopy · P. caudatum oral apparatus · CIL:36779 and 36780 · https://www.cellimagelibrary.org/images/36779",
        "Food-resource experiments in P. caudatum · PMID 31542654 · https://pubmed.ncbi.nlm.nih.gov/31542654/ · does not establish one universal ingestion rate",
    )
    fun structure(id: String) = structures.first { it.id == id }
}

data class NutritionMark(val id: String, val x: Float, val y: Float)
data class NutritionFit(val scale: Float, val dx: Float, val dy: Float) {
    fun x(value: Float) = dx + value * scale
    fun y(value: Float) = dy + value * scale
}
object NutritionFigure {
    const val width = 1000f
    const val height = 600f
    val marks = listOf(NutritionMark("oral-groove",560f,85f), NutritionMark("oral-cilia",640f,135f),
        NutritionMark("cytostome",730f,175f), NutritionMark("cytopharynx",795f,210f), NutritionMark("vacuole",740f,435f))
    fun fit(w: Float,h: Float): NutritionFit {
        require(w > 0 && h > 0)
        val scale=min(w/width,h/height)
        return NutritionFit(scale,(w-width*scale)/2,(h-height*scale)/2)
    }
    fun at(x: Float,y: Float) = marks.filter { it.id in ParameciumNutrition.structures.map { s -> s.id } }
        .minByOrNull { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y) }
        ?.takeIf { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y)<=38f*38f }?.id
}
