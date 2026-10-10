package com.gasczoology.invertebratelab.data

/** R1.6.1 source-audited teaching candidate; independent human approval is pending. */
data class CiliarySubsection(val heading: BilingualText, val explanation: BilingualText)

object ParameciumCiliaryAcademicContent {
    const val PERIPHERAL_DOUBLETS = 9
    const val CENTRAL_SINGLETS = 2
    const val BASAL_BODY_TRIPLETS = 9
    const val BASAL_BODY_CENTRAL_PAIR = 0
    val sources = listOf(
        "https://pmc.ncbi.nlm.nih.gov/articles/PMC2108756/", // Tamm, SEM, P. multimicronucleatum
        "https://link.springer.com/article/10.1186/s13630-017-0050-z", // Bengueddach et al., P. tetraurelia
        "https://pmc.ncbi.nlm.nih.gov/articles/PMC5091652/", // Lodh et al., P. tetraurelia
        "https://www.ncbi.nlm.nih.gov/books/NBK26888/", // Molecular Biology of the Cell
    )
    val subsections = listOf(
        CiliarySubsection(
            BilingualText("A. Motile ciliary structure", "அ. இயக்கக் குறுஇழையின் அமைப்பு"),
            BilingualText(
                "Somatic cilia arise from basal bodies arranged in longitudinal cortical rows. A cortical unit may contain one or two basal bodies; not every basal body necessarily bears a cilium. The enlarged side view shows representative ciliated units rather than the complete cell or a measured ciliary count. Basal bodies lie beneath the surface and dock through the transition zone. Their proximal wall has nine microtubule triplets and no central pair.\n\nThe shaft of a typical motile cilium contains nine peripheral microtubule doublets around two central singlets: the 9 + 2 axoneme. Each doublet has a complete A-tubule and an incomplete B-tubule sharing a wall. The two enlarged insets distinguish axoneme from basal body; they are simplified cross-sections, not electron micrographs. Protein complexes, transition-zone plates and protofilament counts are omitted, and ciliary tips have a different organization.",
                "உடற்பரப்புக் குறுஇழைகள் செல் புறப்பகுதியில் நீளவாட்டில் அமைந்த அடித்தள உடல்களிலிருந்து (basal bodies) தோன்றுகின்றன. ஒரு புறப்பகுதி அலகில் ஒன்று அல்லது இரண்டு அடித்தள உடல்கள் இருக்கலாம்; அனைத்திலும் குறுஇழை இருக்க வேண்டியதில்லை. பெரிதாக்கிய பக்கவாட்டுப் படம் சில குறுஇழை அலகுகளை மட்டுமே காட்டுகிறது; முழுச் செல்லையோ அளவிடப்பட்ட குறுஇழை எண்ணிக்கையையோ காட்டவில்லை. அடித்தள உடல் மேற்பரப்புக்குக் கீழே இருந்து மாற்றுப்பகுதி (transition zone) வழியாகப் படலத்தில் நிலைபெறுகிறது. அதன் அண்மைப் பகுதியில் ஒன்பது நுண்குழாய் மும்மைகள் உள்ளன; மைய நுண்குழாய் இணை இல்லை.\n\nபொதுவான இயக்கக் குறுஇழையின் தண்டில், ஒன்பது புற நுண்குழாய் இரட்டைகள் இரண்டு மைய ஒற்றை நுண்குழாய்களைச் சூழ்ந்துள்ளன. இது 9 + 2 ஆக்சோனீம் (axoneme) அமைப்பு. ஒவ்வோர் இரட்டையிலும் முழுமையான A நுண்குழாயும் அதனுடன் சுவரைப் பகிரும் முழுமையற்ற B நுண்குழாயும் உள்ளன. இரு பெரிதாக்கிய சிறுபடங்களும் ஆக்சோனீமையும் அடித்தள உடலையும் வேறுபடுத்தும் எளிமைப்படுத்திய குறுக்குவெட்டுகள்; மின்னணு நுண்ணோக்கிப் படங்கள் அல்ல. புரதத் தொகுதிகள், மாற்றுப்பகுதித் தட்டுகள், புரத இழை எண்ணிக்கைகள் காட்டப்படவில்லை; குறுஇழை நுனியின் அமைப்பு மாறுபடும்."
            )
        ),
        CiliarySubsection(
            BilingualText("B. Effective stroke and recovery", "ஆ. செயல்திறன் அடியும் மீள்நிலை அடியும்"),
            BilingualText(
                "Axonemal dynein uses ATP to slide neighbouring doublets; structural constraints convert sliding into bending. During forward swimming, the effective stroke displaces water mainly toward the posterior. The recovery stroke follows a different, curved path nearer the cell surface, reducing the opposing fluid displacement. It is not a rigid rod retracing the same line.\n\nMicroscopy of Paramecium multimicronucleatum reveals a three-dimensional beat, including an out-of-plane recovery movement; viscosity can alter the waveform. This side-view projection therefore illustrates asymmetry, not a species-specific reconstruction. Stage 1 introduces organization, stages 2 and 3 isolate phases, and stage 4 shows phase offsets across cilia. These are teaching views, not four successive physiological events. Beat angle, frequency and wave speed are not measured here.",
                "ஆக்சோனீமிலுள்ள டைனீன் (dynein) ATP ஆற்றலைப் பயன்படுத்தி அடுத்தடுத்த நுண்குழாய் இரட்டைகளைச் சறுக்கச் செய்கிறது. அமைப்பின் கட்டுப்பாடுகள் இந்தச் சறுக்கலை வளைவாக மாற்றுகின்றன. முன்னோக்கி நீந்தும்போது செயல்திறன் அடி நீரை முக்கியமாகப் பின்புறம் தள்ளுகிறது. மீள்நிலை அடி செல் மேற்பரப்புக்கு அருகில் வேறொரு வளைந்த பாதையில் நிகழ்வதால் எதிர்த்திசை நீர் நகர்வு குறைகிறது. இது அதே பாதையில் திரும்பும் விறைப்பான குச்சியின் அசைவு அல்ல.\n\nParamecium multimicronucleatum இனத்தின் நுண்ணோக்கி ஆய்வில் முப்பரிமாண அசைவும் படத்தளத்துக்கு வெளியே நிகழும் மீள்நிலை இயக்கமும் காணப்படுகின்றன; நீரின் பாகுத்தன்மை அசைவின் வடிவத்தை மாற்றலாம். எனவே இப்பக்கவாட்டுத் தோற்றம் அசைவின் சமச்சீரின்மையை விளக்குகிறது; குறிப்பிட்ட இனத்தின் துல்லிய மறுஉருவாக்கம் அல்ல. நிலை 1 அமைப்பையும், நிலைகள் 2, 3 தனித்தனி அடிகளையும், நிலை 4 குறுஇழைகளுக்கு இடையிலான கட்ட வேறுபாட்டையும் காட்டுகின்றன. இவை கற்பித்தல் காட்சிகள்; தொடர்ச்சியான நான்கு உடலியல் நிகழ்வுகள் அல்ல. அசைவுக் கோணம், அதிர்வெண், அலை வேகம் இங்கு அளவிடப்படவில்லை."
            )
        ),
        CiliarySubsection(
            BilingualText("C. Metachronal waves and avoidance", "இ. மெட்டாக்ரோனல் அலைகளும் தவிர்ப்பு எதிர்வினையும்"),
            BilingualText(
                "Neighbouring cilia maintain phase differences, producing a travelling metachronal wave. Coordination does not mean all cilia move in the same phase; a wave is a pattern of activity, not cilia migrating along the cortex. The highlighted cilium is a teaching selection, not a leader controlling its neighbours.\n\nA suitable anterior mechanical stimulus can depolarize the membrane. Voltage-gated calcium channels in the ciliary membrane then admit Ca2+ into the cilia, changing the effective-stroke direction and briefly driving backward swimming. As ciliary calcium falls, the cell reorients and resumes forward swimming. Not every stimulus causes reversal: posterior mechanical stimulation can instead accelerate forward swimming. Calcium-channel experiments cited here used Paramecium tetraurelia; no species-specific kinetics are claimed for the reference Paramecium caudatum. There is no nervous system. Ordinary light microscopy can show swimming and reversal; resolving basal-body triplets and axonemal doublets requires electron microscopy, while the ion mechanism requires physiological evidence.",
                "அடுத்தடுத்த குறுஇழைகள் கட்ட வேறுபாட்டுடன் அசைவதால் பயணிக்கும் மெட்டாக்ரோனல் அலை (metachronal wave) உருவாகிறது. ஒருங்கிணைவு என்பது அனைத்தும் ஒரே கட்டத்தில் அசைதல் அல்ல. அலை என்பது இயக்கத்தின் ஒழுங்கமைவு; குறுஇழைகள் செல் புறப்பகுதியில் இடம்பெயர்வது அல்ல. சிறப்பித்துக் காட்டப்படும் குறுஇழை கற்பித்தலுக்கான தேர்வு மட்டுமே; பிற குறுஇழைகளைக் கட்டுப்படுத்தும் தலைமை அமைப்பு அல்ல.\n\nமுன்புறத்தில் ஏற்படும் பொருத்தமான இயந்திரத் தூண்டுதல் செல் படலத்தின் உட்புற எதிர்மின்னழுத்தத்தைக் குறைக்கலாம் (depolarization). இதனால் குறுஇழைப் படலத்திலுள்ள மின்னழுத்தச் சார்பு கால்சியம் அயனிக் கால்வாய்கள் திறந்து Ca2+ குறுஇழைக்குள் நுழைகிறது. செயல்திறன் அடியின் திசை மாறி உயிரி சிறிது நேரம் பின்னோக்கி நீந்துகிறது. குறுஇழைக்குள் கால்சியம் அளவு குறையும்போது உயிரி திசையை மாற்றி மீண்டும் முன்னோக்கி நீந்துகிறது. எல்லாத் தூண்டுதல்களும் பின்னோக்கு அசைவை ஏற்படுத்துவதில்லை; பின்புற இயந்திரத் தூண்டுதல் முன்னோக்கு நீந்தலை வேகப்படுத்தலாம். இங்கு மேற்கோளிடப்படும் கால்சியம் கால்வாய் ஆய்வுகள் Paramecium tetraurelia இனத்தில் நடத்தப்பட்டவை; ஆய்வு இனமான Paramecium caudatum-க்கான துல்லிய நேர அளவுகள் இங்கு கூறப்படவில்லை. நரம்பு மண்டலம் இல்லை. சாதாரண ஒளி நுண்ணோக்கியில் நீந்துதலையும் பின்னோக்கு அசைவையும் காணலாம்; அடித்தள உடலின் மும்மைகளையும் ஆக்சோனீமின் இரட்டைகளையும் காண மின்னணு நுண்ணோக்கி தேவை. அயனி இயக்கத்தை உறுதிப்படுத்த உடலியல் ஆய்வு ஆதாரம் தேவை."
            )
        ),
    )
}
