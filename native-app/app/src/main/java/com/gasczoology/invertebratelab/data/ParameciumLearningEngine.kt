package com.gasczoology.invertebratelab.data

/**
 * R1 teaching engine: authored bilingual organ FUNCTION and simulation stages.
 * Visual coordinates remain illustrative. Research/Tamil editorial sign-off is PENDING.
 * No Android framework dependency, so chronology is deterministic and testable.
 */
enum class ParameciumProcess {
    OSMOREGULATION, CILIARY_MOTION, FEEDING, BINARY_FISSION, CONJUGATION
}

data class ParameciumOrganLesson(
    val id: String,
    val name: BilingualText,
    val function: BilingualText,
    val narration: BilingualText,
    val reference: String,
) {
    init {
        require(id.isNotBlank() && name.english.isNotBlank() && name.tamil.isNotBlank())
        require(function.english.isNotBlank() && function.tamil.isNotBlank())
        require(narration.english.length > 65 && narration.tamil.length > 40)
        require(reference.startsWith("https://"))
    }
}

data class ParameciumStage(
    val id: String,
    val heading: BilingualText,
    val explanation: BilingualText,
    /** Normalized visualization progress; never measured temporal kinetics. */
    val illustrationProgress: Float,
) {
    init {
        require(id.isNotBlank())
        require(heading.english.isNotBlank() && heading.tamil.isNotBlank())
        require(explanation.english.length > 60 && explanation.tamil.length > 35)
        require(illustrationProgress in 0f..1f)
    }
}

data class ParameciumSimulation(
    val process: ParameciumProcess,
    val title: BilingualText,
    val teachingCaution: BilingualText,
    val stages: List<ParameciumStage>,
    val scientificSource: String,
) {
    init {
        require(stages.size >= 4 && stages.map { it.id }.distinct().size == stages.size)
        require(stages.first().illustrationProgress == 0f)
        require(stages.last().illustrationProgress == 1f)
        require(stages.zipWithNext().all { (a,b) ->
            a.illustrationProgress <= b.illustrationProgress
        })
        require(teachingCaution.english.isNotBlank() && teachingCaution.tamil.isNotBlank())
        require(scientificSource.startsWith("https://"))
    }

    fun advance(index: Int): Int {
        require(index in stages.indices)
        return if (index == stages.lastIndex) 0 else index + 1
    }
    fun previous(index: Int): Int {
        require(index in stages.indices)
        return (index - 1).coerceAtLeast(0)
    }
}

object ParameciumLearningEngine {
    private const val REVIEW = "https://pmc.ncbi.nlm.nih.gov/articles/PMC10143506/"
    private const val ORAL = "https://pmc.ncbi.nlm.nih.gov/articles/PMC2109358/"
    private const val OSMO = "https://pubmed.ncbi.nlm.nih.gov/38688044/"
    private const val MOTION = "https://pmc.ncbi.nlm.nih.gov/articles/PMC8208649/"
    private const val PROTISTS = "https://openstax.org/books/biology-2e/pages/23-3-groups-of-protists"

    val organs = listOf(
        ParameciumOrganLesson("pellicle", BilingualText("Pellicle","பெல்லிக்கிள்"),
            BilingualText("Flexible cortical covering that helps preserve cell shape.",
                "செல்லின் வடிவத்தைப் பேண உதவும் நெகிழ்வான புறப்படல அமைப்பு."),
            BilingualText("The pellicle is a complex outer cell covering supported by cortical structures. It helps maintain the characteristic slipper-like outline while permitting limited flexibility; it is not a rigid cell wall.",
                "பெல்லிக்கிள் என்பது செல் புறப்பரப்பில் அமைந்த சிக்கலான உறையாகும். இது பாரமீசியத்தின் செருப்பு போன்ற வடிவத்தைப் பேண உதவுகிறது; ஆனால் கடினமான செல் சுவர் அல்ல."), REVIEW),
        ParameciumOrganLesson("somatic-cilia", BilingualText("Somatic cilia","உடற்பரப்புக் குறுஇழைகள்"),
            BilingualText("Coordinated beating drives swimming and creates local currents.",
                "ஒருங்கிணைந்த அசைவால் நீந்துதலும் அருகிலுள்ள நீரோட்டமும் நிகழ்கின்றன."),
            BilingualText("Somatic cilia beat with phase differences that form travelling metachronal waves. During forward swimming the effective stroke displaces water mainly toward the posterior; the curved recovery stroke returns near the surface along a different three-dimensional path. Calcium entry through channels in the ciliary membrane can reverse the effective stroke, producing a brief backward swim before reorientation.",
                "உடற்பரப்புக் குறுஇழைகள் கட்ட வேறுபாட்டுடன் அசைந்து பயணிக்கும் மெட்டாக்ரோனல் அலைகளை உருவாக்குகின்றன. முன்னோக்கு நீந்தலில் செயல்திறன் அடி நீரை முக்கியமாகப் பின்புறம் தள்ளுகிறது; மீள்நிலை அடி மேற்பரப்புக்கு அருகில் வேறொரு முப்பரிமாணப் பாதையில் திரும்புகிறது. குறுஇழைப் படலக் கால்வாய்கள் வழியாகக் கால்சியம் நுழையும்போது செயல்திறன் அடியின் திசை மாறி, திசைமாற்றத்திற்கு முன் சிறிது நேரம் பின்னோக்கு நீந்தல் நிகழலாம்."), MOTION),
        ParameciumOrganLesson("oral-groove", BilingualText("Oral groove","வாய்ப்பள்ளம்"),
            BilingualText("Ciliary currents guide food toward the cytostome and cytopharynx.",
                "குறுஇழை நீரோட்டம் உணவை சைட்டோஸ்டோம், சைட்டோஃபாரிங்ஸ் நோக்கி நகர்த்துகிறது."),
            BilingualText("The oral groove belongs to the feeding apparatus on the oral side. Oral cilia guide suspended particles through the vestibule toward the cytostome and cytopharynx, where food vacuoles can form.",
                "வாய்ப்பள்ளம் உணவெடுக்கும் அமைப்பின் ஒரு பகுதியாகும். அதிலுள்ள குறுஇழைகள் நீரில் மிதக்கும் துகள்களை வெஸ்டிப்யூல் வழியாகச் சைட்டோஸ்டோம், சைட்டோஃபாரிங்ஸ் நோக்கி நகர்த்தி உணவுக் குமிழ் உருவாக உதவுகின்றன."), ORAL),
        ParameciumOrganLesson("cytoproct", BilingualText("Cytoproct","செல் கழிவுவெளியேற்றப் பகுதி"),
            BilingualText("Specialized ventral-posterior egestion site for undigested residues.",
                "செரியாத கழிவுகளை வெளியேற்றும் பின்புற வாய்ப்புறச் சிறப்புப் பகுதி."),
            BilingualText("Following intracellular digestion, undigested residues are released at the cytoproct. In Paramecium caudatum a closed cytoproct can appear as a cortical ridge; its exact marker position in the current drawing is not scientifically approved.",
                "செல்லுக்குள் செரிமானம் முடிந்த பின் செரியாத எச்சங்கள் செல் கழிவுவெளியேற்றப் பகுதியில் வெளியேறுகின்றன. மூடிய நிலையில் இப்பகுதி புறப்படல மேடாகத் தோன்றலாம்; தற்போதைய வரைபடத்தில் அதன் துல்லிய இடம் இன்னும் அறிவியல் ரீதியாக அங்கீகரிக்கப்படவில்லை."), REVIEW),
        ParameciumOrganLesson("trichocysts", BilingualText("Trichocysts","ட்ரைக்கோசிஸ்டுகள்"),
            BilingualText("Subpellicular extrusomes capable of rapid discharge.",
                "பெல்லிக்கிளின் கீழுள்ள, விரைவாக வெளியேற்றக்கூடிய சிறப்புத் துகள்கள்."),
            BilingualText("Trichocysts are cortical extrusomes situated beneath the pellicle. They can rapidly discharge an elongated material in response to stimulation; the exact arrangement in the schematic remains under review.",
                "ட்ரைக்கோசிஸ்டுகள் பெல்லிக்கிளுக்கு அடியில் இருக்கும் புறப்படலச் சிறப்புத் துகள்கள். தூண்டுதலுக்கு ஏற்ப அவற்றிலிருந்து நீண்ட பொருள் வேகமாக வெளியேறலாம்; வரைபடத்தில் அவற்றின் இடமமைவு இன்னும் மதிப்பாய்வுக்குரியது."), REVIEW),
        ParameciumOrganLesson("contractile-vacuole", BilingualText("Contractile vacuole","சுருங்கும் நுண்குமிழ்"),
            BilingualText("Collects and periodically expels excess water in freshwater.",
                "நன்னீரில் செல்லுக்குள் வரும் மிகைநீரைச் சேகரித்து இடைவெளியுடன் வெளியேற்றுகிறது."),
            BilingualText("In hypotonic freshwater, water tends to enter the cytoplasm. The contractile-vacuole complex, including associated collecting canals or spongiome, participates in active water-balance regulation and periodically discharges accumulated fluid.",
                "குறைந்த கரைசல் செறிவு கொண்ட நன்னீரிலிருந்து நீர் செலுக்குள் நுழைய முனைகிறது. சேகரிப்புக் கால்வாய்கள் மற்றும் சுற்றியுள்ள அமைப்புகளுடன் சேர்ந்து சுருங்கும் நுண்குமிழ் நீர்ச்சமநிலையை ஒழுங்குபடுத்தி மிகைநீரை இடைவெளியுடன் வெளியேற்றுகிறது."), OSMO),
        ParameciumOrganLesson("macronucleus", BilingualText("Macronucleus","பேருட்கரு"),
            BilingualText("Somatic transcription and routine cell-function control.",
                "வழக்கமான செல் செயல்பாடுகளுக்கான மரபணு வெளிப்பாட்டில் முக்கியப் பங்கு."),
            BilingualText("The macronucleus supports most everyday transcriptional functions, including growth and metabolism. During sexual nuclear reorganization it can be replaced by a new macronuclear system.",
                "பேருட்கரு வளர்ச்சி, வளர்சிதை மாற்றம் போன்ற வழக்கமான செயல்களுக்குத் தேவையான பெரும்பாலான மரபணு வெளிப்பாட்டில் பங்கு கொள்கிறது. பாலியல் உட்கரு மறுசீரமைப்பின் போது புதிய பேருட்கரு அமைப்பு உருவாகலாம்."), REVIEW),
        ParameciumOrganLesson("micronucleus", BilingualText("Micronucleus","சிற்றுட்கரு"),
            BilingualText("Germline nuclear system essential during conjugation.",
                "இணைவு நிகழ்வில் மரபுப் பொருள் பரிமாற்றத்திற்குத் தேவையான உட்கரு அமைப்பு."),
            BilingualText("The micronucleus stores the germline genome. During conjugation, meiosis and subsequent exchange and fusion of haploid nuclear material allow genetic recombination without immediately multiplying the two paired cells.",
                "சிற்றுட்கரு மரபுவழி மரபணுத் தொகுதியைக் கொண்டுள்ளது. இணைவின் போது மியாசிஸ், ஒற்றைமடியக் கருப் பொருள் பரிமாற்றம் மற்றும் இணைவு நிகழ்ந்து மரபணு மறுசேர்க்கை ஏற்படுகிறது; இந்நிகழ்வால் உடனடியாக செல் எண்ணிக்கை பெருகாது."), REVIEW),
        ParameciumOrganLesson("food-vacuole", BilingualText("Food vacuole","உணவுக் குமிழ்"),
            BilingualText("Internal vesicle for food processing and intracellular digestion.",
                "உணவுப் பொருள்களைச் செல்லுக்குள் செரிக்க உதவும் படலச் சிறுகுமிழ்."),
            BilingualText("A forming food vacuole separates near the cytopharynx. As it circulates through the cytoplasm, digestive compartments process its contents; soluble nutrients are absorbed and indigestible remains can be egested.",
                "சைட்டோஃபாரிங்ஸ் அருகே உருவாகும் உணவுக் குமிழ் தனியாகப் பிரிகிறது. அது சைட்டோபிளாஸ்மத்தில் நகரும் போது செரிமானம் நடைபெற்று ஊட்டப்பொருள்கள் உறிஞ்சப்படுகின்றன; செரியாத எச்சங்கள் பின்னர் வெளியேற்றப்படுகின்றன."), ORAL),
    )

    private fun s(id: String, en: String, ta: String, detailEn: String, detailTa: String, p: Float) =
        ParameciumStage(id, BilingualText(en, ta), BilingualText(detailEn, detailTa), p)
    private val caution = BilingualText(
        "Diagrammatic sequence, not measured microscopy or physiological timescale. Anatomical placement awaits specialist review.",
        "இது கற்பித்தலுக்கான விளக்க வரிசை மட்டுமே; நுண்ணோக்கி அளவீடோ உண்மையான கால அளவோ அல்ல. உறுப்புகளின் இடமமைவு நிபுணர் ஆய்வுக்கு உட்பட்டது."
    )

    val simulations: List<ParameciumSimulation> = listOf(
        ParameciumSimulation(ParameciumProcess.OSMOREGULATION,
            BilingualText("Contractile-vacuole cycle","சுருங்கும் நுண்குமிழ் சுழற்சி"), caution,
            listOf(
                s("osmosis","Water enters","நீர் உள்ளேறுதல்",
                    "Because the freshwater medium is hypotonic, water tends to enter the cytoplasm across the cell boundary.",
                    "நன்னீரின் கரைசல் செறிவு குறைவாக இருப்பதால் நீர் செல் எல்லை வழியாகச் சைட்டோபிளாஸ்மத்திற்குள் நுழைய முனைகிறது.",0f),
                s("collect","Collecting network","சேகரிக்கும் அமைப்பு",
                    "Associated collecting canals and spongiome pathways transfer fluid into the contractile-vacuole complex.",
                    "சேகரிப்புக் கால்வாய்களும் ஸ்பாஞ்சியோம் அமைப்பும் நீரைச் சுருங்கும் நுண்குமிழ் தொகுதிக்குக் கொண்டு செல்கின்றன.",0.28f),
                s("fill","Vacuole fills","குமிழ் நிரம்புதல்",
                    "The contractile vacuole enlarges as fluid accumulates; membrane transport participates in the water-balance process.",
                    "நீர் திரளும்போது சுருங்கும் நுண்குமிழ் விரிவடைகிறது; படலவழிக் கடத்தலும் நீர்ச்சமநிலை இயக்கத்தில் பங்காற்றுகிறது.",0.7f),
                s("expel","Fluid is discharged","நீர் வெளியேறுதல்",
                    "The vacuole periodically discharges its fluid through a dedicated discharge region; this is osmoregulation, not food digestion.",
                    "நுண்குமிழில் சேர்ந்த நீர் குறிப்பிட்ட வெளியேற்றப் பகுதி வழியாகக் காலந்தோறும் வெளியேறுகிறது; இது நீர்ச்சமநிலைச் செயல், உணவுச் செரிமானம் அல்ல.",1f),
            ), OSMO),
        ParameciumSimulation(ParameciumProcess.CILIARY_MOTION,
            BilingualText("Ciliary locomotion","குறுஇழை இயக்கம்"), caution,
            listOf(
                s("rest","Coordinated cilia","ஒருங்கிணைந்த குறுஇழைகள்",
                    "Somatic cilia arise from basal bodies in longitudinal rows over the cortex. This organization view shows representative ciliated units, not a resting phase or a measured ciliary count.",
                    "செல் புறப்பகுதியில் நீளவாட்டில் அமைந்த அடித்தள உடல்களிலிருந்து உடற்பரப்புக் குறுஇழைகள் தோன்றுகின்றன. இது அமைப்பைக் காட்டும் பிரதிநிதிக் காட்சி; ஓய்வுநிலையோ அளவிடப்பட்ட குறுஇழை எண்ணிக்கையோ அல்ல.",0f),
                s("effective","Effective stroke","செயல்திறன் அடி",
                    "During forward swimming, the effective stroke displaces water mainly toward the posterior and contributes to propulsion. ATP-dependent axonemal dynein drives constrained microtubule sliding and bending; the stroke shown is an illustrative projection.",
                    "முன்னோக்கு நீந்தலில் செயல்திறன் அடி நீரை முக்கியமாகப் பின்புறம் தள்ளி நகர்வுக்கு உதவுகிறது. ATP ஆற்றலைப் பயன்படுத்தும் ஆக்சோனீம் டைனீன், கட்டுப்படுத்தப்பட்ட நுண்குழாய் சறுக்கலையும் வளைவையும் ஏற்படுத்துகிறது. இங்கு காட்டப்படும் அடி விளக்கத் தோற்றம் மட்டுமே.",0.3f),
                s("recovery","Recovery stroke","மீள்நிலை அடி",
                    "The bent recovery stroke follows a different three-dimensional trajectory near the cell surface, limiting opposing fluid displacement before the next effective stroke. This flattened side view cannot show its full out-of-plane movement.",
                    "வளைந்த மீள்நிலை அடி செல் மேற்பரப்புக்கு அருகில் வேறொரு முப்பரிமாணப் பாதையில் நிகழ்கிறது. அடுத்த செயல்திறன் அடிக்கு முன் எதிர்த்திசை நீர் நகர்வை இது குறைக்கிறது. படத்தளத்துக்கு வெளியே நிகழும் முழு அசைவை இப்பக்கவாட்டுத் தோற்றம் காட்ட இயலாது.",0.65f),
                s("wave","Metachronal coordination","மெட்டாக்ரோனல் ஒருங்கிணைவு",
                    "Neighbouring cilia beat with phase offsets, creating travelling metachronal waves. This fourth teaching view combines effective and recovery phases across a row; it is not a separate fourth step in one cilium’s beat. The wave is an activity pattern, not movement of basal bodies along the cortex.",
                    "அடுத்தடுத்த குறுஇழைகள் கட்ட வேறுபாட்டுடன் அசைவதால் பயணிக்கும் மெட்டாக்ரோனல் அலைகள் உருவாகின்றன. நான்காவது கற்பித்தல் காட்சி ஒரு வரிசையிலுள்ள செயல்திறன் மற்றும் மீள்நிலை அடிகளை இணைத்துக் காட்டுகிறது; ஒரு குறுஇழையின் அசைவில் தனியான நான்காவது படி அல்ல. அலை என்பது இயக்க ஒழுங்கமைவு; அடித்தள உடல்கள் செல் புறப்பகுதியில் இடம்பெயர்வது அல்ல.",1f),
            ), MOTION),
        ParameciumSimulation(ParameciumProcess.FEEDING,
            BilingualText("Feeding and digestion","உணவெடுப்பும் செரிமானமும்"), caution,
            listOf(
                s("current","Feeding current","உணவெடுக்கும் நீரோட்டம்",
                    "Specialized oral cilia create currents that move suspended particles toward the ventral oral groove.",
                    "வாய்ப்புறக் குறுஇழைகள் உருவாக்கும் நீரோட்டம் மிதக்கும் உணவுத் துகள்களை வாய்ப்பள்ளம் நோக்கி நகர்த்துகிறது.",0f),
                s("cytostome","Food passage","உணவு செல்லும் பாதை",
                    "Particles pass through the oral vestibule and cytostome toward the cytopharynx; the precise groove contour is still under expert review.",
                    "உணவுத் துகள்கள் வெஸ்டிப்யூல், சைட்டோஸ்டோம் வழியாகச் சைட்டோஃபாரிங்ஸ் நோக்கிச் செல்கின்றன; வரைபட வளைவு இன்னும் நிபுணர் ஆய்வில் உள்ளது.",0.28f),
                s("vacuole","Food vacuole forms","உணவுக் குமிழ் உருவாதல்",
                    "A membrane-bound food vacuole forms near the cytopharynx and enters the cytoplasm for intracellular digestion.",
                    "சைட்டோஃபாரிங்ஸ் அருகே படலத்தால் சூழப்பட்ட உணவுக் குமிழ் உருவாகி செல்லுக்குள் செரிமானத்திற்குச் செல்கிறது.",0.65f),
                s("egestion","Residue egestion","செரியாத கழிவு வெளியேற்றம்",
                    "Nutrients are absorbed during vacuole processing; indigestible material is ultimately released at the cytoproct.",
                    "உணவுக் குமிழில் செரிமானம் நடைபெறும் போது ஊட்டப்பொருள்கள் உறிஞ்சப்படுகின்றன; செரியாத எச்சங்கள் இறுதியில் செல் கழிவுவெளியேற்றப் பகுதியில் வெளியேற்றப்படுகின்றன.",1f),
            ), ORAL),
        ParameciumSimulation(ParameciumProcess.BINARY_FISSION,
            BilingualText("Transverse binary fission","குறுக்குத் திசை இருபிளவு"), caution,
            listOf(
                s("mother","Growing parent cell","வளரும் தாய்செல்",
                    "A vegetative Paramecium cell grows and prepares its nuclear and cortical organization for division.",
                    "வளர்ச்சியடைந்த பாரமீசியம் செல் இருபிளவுக்கு உட்கரு மற்றும் புறப்படல அமைப்புகளைத் தயார் செய்கிறது.",0f),
                s("nuclei","Nuclear division","உட்கருப் பிரிவு",
                    "The micronucleus divides mitotically; the macronuclear system divides by a different, amitotic process.",
                    "சிற்றுட்கரு மைட்டாசிஸ் மூலம் பிரிகிறது; பேருட்கரு வேறுபட்ட அமிட்டாசிஸ் முறையில் பிரிகிறது.",0.36f),
                s("furrow","Transverse constriction","குறுக்குச் சுருக்கம்",
                    "Cytokinesis proceeds with a transverse constriction while cortical structures are reorganized for each daughter cell.",
                    "குறுக்காகச் சுருக்கம் உருவாகி சைட்டோகைனிசிஸ் நடைபெறுகிறது; இரு மகள் செல்களுக்கும் புறப்படல அமைப்புகள் ஒழுங்குபடுத்தப்படுகின்றன.",0.7f),
                s("daughters","Two daughter cells","இரு மகள் செல்கள்",
                    "The two daughter cells separate and continue autonomous growth; this asexual process increases cell number.",
                    "இரு மகள் செல்களும் பிரிந்து தனித்தனியாக வளர்கின்றன; இப்பாலிலா முறையால் செல் எண்ணிக்கை அதிகரிக்கிறது.",1f),
            ), PROTISTS),
        ParameciumSimulation(ParameciumProcess.CONJUGATION,
            BilingualText("Conjugation and genetic exchange","இணைவும் மரபணுப் பரிமாற்றமும்"), caution,
            listOf(
                s("pair","Compatible cells pair","இணக்கமான செல்கள் இணைதல்",
                    "Two compatible mating types temporarily attach along their oral surfaces; conjugation is not binary fission.",
                    "இணக்கமான இரண்டு இணைவு வகைச் செல்கள் வாய்ப்புறங்களில் தற்காலிகமாக இணைகின்றன; இணைவு இருபிளவு அல்ல.",0f),
                s("meiosis","Micronuclear meiosis","சிற்றுட்கரு மியாசிஸ்",
                    "Micronuclear meiosis produces haploid nuclei; subsequent events generate exchangeable migratory and stationary pronuclei.",
                    "சிற்றுட்கருவில் மியாசிஸ் நடைபெற்று ஒற்றைமடியக் கருக்கள் உருவாகின்றன; பின்னர் பரிமாற்றத்திற்கான நகரும் மற்றும் நிலைத்திருக்கும் முன்கருக்கள் தோன்றுகின்றன.",0.3f),
                s("exchange","Exchange and fusion","பரிமாற்றமும் இணைவும்",
                    "Partners exchange a migratory pronucleus; exchanged and stationary pronuclei fuse, restoring a diploid synkaryon.",
                    "இணைச்செல்கள் நகரும் முன்கருக்களைப் பரிமாறிக் கொள்கின்றன; அவை நிலைத்திருக்கும் முன்கருக்களுடன் இணைந்து இருமடிய உட்கருவை உருவாக்குகின்றன.",0.66f),
                s("separate","Nuclear reorganization","உட்கரு மறுசீரமைப்பு",
                    "After partners separate, nuclear reorganization produces new nuclear complements. Conjugation enables recombination rather than immediate numerical multiplication.",
                    "இணைச்செல்கள் பிரிந்த பின் உட்கருக்கள் மறுசீரமைக்கப்படுகின்றன. இணைவு மரபணு மறுசேர்க்கையை ஏற்படுத்துகிறது; உடனடியாக செல் எண்ணிக்கையைப் பெருக்காது.",1f),
            ), REVIEW),
    )

    fun organ(id: String): ParameciumOrganLesson = organs.single { it.id == id }
    fun simulation(process: ParameciumProcess): ParameciumSimulation =
        simulations.single { it.process == process }

    init {
        require(organs.map { it.id }.distinct().size == organs.size)
        require(simulations.map { it.process }.toSet() == ParameciumProcess.entries.toSet())
        require(organs.map { it.id }.containsAll(
            listOf("pellicle","somatic-cilia","oral-groove","cytoproct","trichocysts",
                "contractile-vacuole","macronucleus","micronucleus","food-vacuole")))
        require(ParameciumAnatomyDraft.plates.all {
            it.status == AcademicWorkStatus.DRAFT_UNVERIFIED
        })
    }
}
