package com.gasczoology.invertebratelab.data

/**
 * U5.6: original bilingual explanatory textbook lessons for the SIX
 * Unit IV A5 chapters which previously exposed assessment-only previews.
 * This material does not modify accepted A5 answers or assert that any
 * historical pencil master has been located/approved.
 *
 * Comparative/authoritative science reading:
 * - https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids
 * - https://openstax.org/books/biology-2e/pages/28-6-superphylum-ecdysozoa-arthropods
 * - https://pmc.ncbi.nlm.nih.gov/articles/PMC11050097/
 * - https://doi.org/10.1016/B978-0-08-102871-1.00028-2
 * Editorial status: bilingual scientific review pending.
 */
internal object UnitIVSupplementaryLessons {
    private fun b(en:String, ta:String)=BilingualText(en,ta)
    private fun section(id:String,headingEn:String,headingTa:String,
                        en1:String,ta1:String,en2:String,ta2:String)=
        NereisTextbookSection(
            id,b(headingEn,headingTa),listOf(b(en1,ta1),b(en2,ta2))
        )
    internal data class ChapterLesson(
        val chapterId:String,
        val heading:BilingualText,
        val sections:List<NereisTextbookSection>,
        val reading:List<String>,
    ) {
        init {
            require(chapterId.startsWith("u4-"))
            require(sections.size>=4)
            require(sections.map{it.id}.distinct().size==sections.size)
        }
    }
    private val annelids="https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"
    private val arthropods="https://openstax.org/books/biology-2e/pages/28-6-superphylum-ecdysozoa-arthropods"
    private val onychophora="https://pmc.ncbi.nlm.nih.gov/articles/PMC11050097/"
    private val larvae="https://doi.org/10.1016/B978-0-08-102871-1.00028-2"
    private val crab="https://invasions.si.edu/nemesis/calnemo/species_summary/98790"

    val chapters=listOf(
        ChapterLesson("u4-annelida",b("Annelida: organization and diversity","வளையப்புழுக்கள்: உடலமைப்பும் பன்முகத்தன்மையும்"),listOf(
            section("diagnosis","General characters and taxonomic position","பொதுப்பண்புகளும் வகைப்பாட்டு நிலையும்",
                "Annelids are bilaterally symmetrical, triploblastic protostomes with an elongate body showing true metameric organization. Many segments contain repeated muscles, coelomic compartments and neural or excretory components. A thin flexible cuticle covers the epidermis; the body is not enclosed in an articulated chitinous exoskeleton.",
                "வளையப்புழுக்கள் இருபக்கச் சமச்சீருடைய மூவடுக்கு முதல்வாயுயிரிகள். உண்மையான உடற்கண்டப் பிரிவு காணப்படும். தசைகள், உடற்குழிகள், நரம்பு மற்றும் கழிவுநீக்க அமைப்புகள் பல கண்டங்களில் மீண்டும் மீண்டும் அமைந்துள்ளன. மெல்லிய நெகிழும் புறத்தோல் காணப்படும்; இணைப்புகளுடைய கடின வெளிச்சட்டகம் இல்லை.",
                "Traditional undergraduate texts group marine polychaetes, earthworms and leeches in separate classes; modern annelid phylogeny has revised several classical boundaries. Use the syllabus classification for examinations while identifying older group names as teaching conventions rather than universally accepted modern clades.",
                "பாரம்பரியப் பாடநூல்களில் கடற்பலசீட்டா புழுக்கள், மண்புழுக்கள், அட்டைகள் ஆகியவை தனித்த வகுப்புகளாக விவரிக்கப்படுகின்றன. நவீன உறவியல் ஆய்வுகள் இப்பிரிவுகளின் எல்லைகளை மாற்றியுள்ளன. தேர்வுக்கான பழைய வகைப்பாட்டையும் தற்போதைய அறிவியல் நிலைப்பாட்டையும் தெளிவாக வேறுபடுத்த வேண்டும்."),
            section("body-plan","Body regions, coelom and locomotion","உடற்பகுதிகள், உடற்குழி மற்றும் இயக்கம்",
                "A typical annelid body has an anterior prostomium, a mouth-bearing peristomium, many trunk segments and a posterior pygidium bearing the anus. In coelomate forms, mesodermally lined compartments and septa help segment the internal body. Coelomic fluid works with circular and longitudinal muscles as a hydrostatic skeleton.",
                "பொதுவான வளையப்புழுவில் முன்புற புரோஸ்டோமியம், வாய் உள்ள பெரிஸ்டோமியம், பல உடற்கண்டங்கள், பின்புற ஆசனவாய் கொண்ட பைகிடியம் காணப்படுகின்றன. இடைத்தடுப்புகள் உடற்குழியைப் பிரிக்கக்கூடும். உடற்குழித் திரவமும் வட்டத் தசை, நீளத் தசைகளும் சேர்ந்து நீரழுத்த எலும்புக்கூடாகச் செயல்படுகின்றன.",
                "Chaetae provide grip in earthworms, while marine nereidids employ paired parapodia with bundles of chaetae. Leeches use suckers and predominantly crawling or swimming movements. A single textbook sketch of one annelid should not be treated as a universal body plan.",
                "மண்புழுவில் சீட்டாக்கள் நிலத்தைப் பற்ற உதவுகின்றன; நெரீஸ் போன்ற கடற்புழுக்களில் சீட்டாக்கள் கொண்ட இணைப் பாராப்போடியா இயக்கத்திற்கு உதவும். அட்டைகள் உறிஞ்சிகளால் நகர்கின்றன. ஒரு உயிரியின் படத்தை எல்லா வளையப்புழுக்களுக்கும் ஒரே அமைப்பாகக் கருதக் கூடாது."),
            section("organ-systems","Digestion, circulation, respiration and excretion","செரிமானம், சுற்றோட்டம், சுவாசம், கழிவுநீக்கம்",
                "Many annelids possess a complete alimentary canal extending from mouth to anus. A longitudinal nervous system usually includes an anterior cerebral region and ventral neural pathways. Closed blood vessels occur in several familiar textbook annelids, but circulation and respiratory specializations are not identical in all lineages.",
                "பல வளையப்புழுக்களில் வாய் முதல் ஆசனவாய் வரை முழுமையான உணவுக்குழாய் அமைப்பு உள்ளது. முன்புற நரம்பு மையமும் வயிற்றுப்புற நரம்புப் பாதைகளும் இயக்கத்தை ஒருங்கிணைக்கின்றன. சில பிரதிநிதிகளில் மூடிய இரத்த நாள அமைப்பு இருந்தாலும் சுவாசமும் சுற்றோட்டமும் எல்லா இனங்களிலும் ஒரே மாதிரி இல்லை.",
                "Gas exchange commonly occurs across moist body surfaces; parapodial or branchial surfaces assist in some aquatic forms. Paired metanephridial units are useful classroom examples for excretion, though their precise organization differs across annelid taxa and body regions.",
                "ஈரமான உடற்பரப்பில் வாயுப் பரிமாற்றம் நடைபெறலாம்; சில நீர்வாழ் வடிவங்களில் பாராப்போடியாவும் செவுள் அமைப்புகளும் உதவுகின்றன. இணையான நெப்ரிடியா கழிவுநீக்கத்தின் பொதுவான எடுத்துக்காட்டுகள்; அவற்றின் எண்ணிக்கையும் அமைப்பும் இனங்களுக்கு ஏற்ப மாறுகின்றன."),
            section("representatives","Ecology, examples and comparison","சூழலியல், எடுத்துக்காட்டுகள் மற்றும் ஒப்பீடு",
                "Nereis is a coastal errant annelid with parapodia and a distinct sensory head. The terrestrial clitellate earthworm burrows through soil and contributes to aeration and decomposition. Leeches include free-living predators and blood-feeding species. These differences reflect specialization, not a hierarchy of 'higher' and 'lower' worms.",
                "நெரீஸ் கடற்கரையில் பாராப்போடியாவுடன் இயங்கும் வளையப்புழு. நிலவாழ் கிளிட்டெல்லம் கொண்ட மண்புழு மண்ணைத் துளைத்து காற்றோட்டத்திற்கும் சிதைவுக்கும் உதவுகிறது. அட்டைகளில் வேட்டையாடும் வகைகளும் இரத்தம் உறிஞ்சும் வகைகளும் உள்ளன. இவை சூழலுக்கு ஏற்ப ஏற்பட்ட சிறப்பியல்புகள்.",
                "In practical identification, first establish external ringlike segmentation and look for the prostomium, chaetae, parapodia, clitellum or suckers where appropriate. Record which structures are actually observable instead of copying genital openings or vascular labels from a different organism.",
                "செய்முறை அடையாளத்தில் உடற்கண்டங்கள், புரோஸ்டோமியம், சீட்டாக்கள், பாராப்போடியா, கிளிட்டெல்லம் அல்லது உறிஞ்சிகள் ஆகியவற்றை உரிய உயிரியில் மட்டுமே கவனிக்க வேண்டும். வேறு உயிரியின் இனப்பெருக்கத் துளைகளையோ இரத்தநாளக் குறியீடுகளையோ இங்கு நகலிடக் கூடாது.")
        ), listOf(annelids)),
        ChapterLesson("u4-metamerism",b("Metamerism: repetition and specialization","மெட்டாமெரிசம்: மீளமைவும் சிறப்பியல்பும்"),listOf(
            section("meaning","True metamerism and segmentation","உண்மையான உடற்கண்டப் பிரிவும் மீளமைவும்",
                "Metamerism is the serial repetition of comparable structural units along the anterior–posterior axis. An annelid commonly displays visible grooves externally and repeated mesodermal coelomic compartments, neural components and muscles internally. The segments are not necessarily identical in every adult body region.",
                "மெட்டாமெரிசம் என்பது முன்–பின் அச்சில் ஒத்த அமைப்புகள் தொடர்ச்சியாக மீண்டும் காணப்படுதல் ஆகும். வளையப்புழுவில் வெளிப்புறக் கண்டப் பள்ளங்களும் உள்ளே உடற்குழிகள், தசைகள், நரம்புக் கூறுகளும் மீள்கின்றன. அனைத்து உடற்கண்டங்களும் முழுமையாக ஒன்றுபோல் இருப்பதில்லை.",
                "A superficial ring in a cuticle does not by itself prove true metamerism. Distinguish external annulation from repeated internal mesodermal structures. The posterior pygidium is a terminal region and is not counted as an ordinary trunk segment.",
                "வெளிப்புறத் தோலில் வளையக் கோடு இருப்பதாலேயே உண்மையான மெட்டாமெரிசம் என்று உறுதிப்படுத்த முடியாது. புறவளையங்களையும் உட்புற மீளமைப்புகளையும் வேறுபடுத்த வேண்டும். பின்முனைப் பைகிடியம் வழக்கமான உடற்கண்டம் அல்ல."),
            section("development","Developmental origins and variation","வளர்ச்சித் தோற்றமும் வேறுபாடுகளும்",
                "In many annelids additional trunk segments arise from a posterior growth zone located anterior to the pygidium. Segmentation is integrated with the development of nervous, muscular and coelomic arrangements. In leeches, coelomic spaces become modified and external rings need not correspond one-to-one with internal segments.",
                "பல வளையப்புழுக்களில் பைகிடியத்திற்கு முன்புள்ள வளர்ச்சிப் பகுதியிலிருந்து புதிய உடற்கண்டங்கள் உருவாகின்றன. நரம்பு, தசை, உடற்குழி அமைப்புகளின் வளர்ச்சியுடன் கண்டப்பிரிவு தொடர்புடையது. அட்டைகளில் உடற்குழிகள் மாற்றம் பெறுவதால் வெளிவளையங்களும் உள்கண்டங்களும் ஒன்றுக்கு ஒன்று சமமாக இருக்காது.",
                "Metameric organisation occurs in distant animal lineages, including arthropods, but annelid segments and arthropod segments should not be assumed developmentally identical merely because both animals display serial repetition.",
                "வளையப்புழுக்களுக்கும் கணுக்காலிகளுக்கும் உடற்கண்ட மீளமைப்பு காணப்படலாம். இரண்டிலும் கண்டங்கள் இருப்பதால் அவற்றின் உருவாக்கப் பாதையும் பரிணாம உறவும் முழுவதும் ஒன்றே என்று கருதக்கூடாது."),
            section("functions","Hydrostatic locomotion and flexibility","நீரழுத்த இயக்கமும் உடல் நெகிழ்வும்",
                "Intersegmental septa can allow coelomic compartments to function partly independently. A peristaltic earthworm alternates circular muscle contraction, longitudinal contraction and anchorage by chaetae. Segmental control helps bends propagate along the body without requiring uniform whole-body contraction.",
                "இடைத்தடுப்புகளால் பிரிக்கப்பட்ட உடற்குழிகள் ஓரளவு தனித்தனியாக இயங்க முடியும். மண்புழுவில் வட்டத் தசை, நீளத் தசை சுருக்கம் மற்றும் சீட்டாக்களின் நிலப்பற்று மாறி நிகழ்கின்றன. இதனால் முழு உடலும் ஒரே நேரத்தில் சுருங்காமல் அலைபோன்ற இயக்கம் ஏற்படுகிறது.",
                "In a marine nereidid, repeated parapodia and segmental muscles generate coordinated crawling and swimming. Metamerism permits regional specializations, including distinct head sensory structures and reproductive modifications.",
                "கடல் நெரீஸில் மீண்டும் மீண்டும் அமைந்த பாராப்போடியாவும் கண்டத் தசைகளும் ஊர்தல் மற்றும் நீந்துதலை ஒருங்கிணைக்கின்றன. தலை உணர்வுறுப்புகள், இனப்பெருக்க மாற்றங்கள் போன்ற குறிப்பிட்ட பகுதிச் சிறப்புகள் உடற்கண்ட அமைப்புடன் இணைகின்றன."),
            section("identification","Recognizing segments correctly","உடற்கண்டங்களைச் சரியாக அடையாளம் காணுதல்",
                "Draw a representative annelid with clearly differentiated prostomium, peristomium, trunk segments and pygidium. Label the position and limits of any clitellum only when the depicted organism actually possesses one. Nereis does not have an earthworm-like clitellum.",
                "படத்தில் புரோஸ்டோமியம், பெரிஸ்டோமியம், உடற்கண்டங்கள், பைகிடியம் ஆகியவற்றைத் தனித்தனியாகக் காட்ட வேண்டும். கிளிட்டெல்லம் உள்ள உயிரியில் மட்டுமே அதைக் குறிக்க வேண்டும். நெரீஸில் மண்புழுவைப் போன்ற கிளிட்டெல்லம் இல்லை.",
                "Use a comparison of an earthworm annulus, a Nereis chaetiger, a leech annulus and arthropod tagmata to identify the difference between surface repetition, true segmented anatomy and fused functional body regions.",
                "மண்புழுக் கண்டம், நெரீஸ் சீட்டா கொண்ட கண்டம், அட்டையின் புறவளையம், கணுக்காலியின் இணைந்த உடற்பகுதிகள் ஆகியவற்றை ஒப்பிட்டால் வெளிப்புற மீளமைப்பும் உண்மையான உடற்கண்டமும் சிறப்பாக்கப்பட்ட பகுதியும் வேறுபடும்.")
        ), listOf(annelids,arthropods)),
        ChapterLesson("u4-modes-life",b("Annelid modes of life and adaptations","வளையப்புழுக்களின் வாழ்வியல் முறைகளும் தகவமைப்புகளும்"),listOf(
            section("marine","Marine free-living and tube-dwelling forms","கடலில் சுதந்திர மற்றும் குழாய்வாழ் வடிவங்கள்",
                "Active nereidids forage across sediments and crevices using coordinated parapodial movements; their sensory heads and eversible pharynx assist food search and capture. In contrast, many sedentary marine annelids inhabit permanent tubes or burrows and employ specialized tentacles or feeding fans.",
                "நெரீஸ் போன்ற செயலில் இயங்கும் கடற்புழுக்கள் பாராப்போடியா உதவியுடன் படிவங்களிலும் பாறை இடுக்குகளிலும் உணவு தேடுகின்றன. உணர்வுறுப்புள்ள தலைவும் வெளியே நீளும் தொண்டையும் உதவும். பல நிலைவாழ் கடற்புழுக்கள் குழாய்களிலும் வளைகளிலும் வாழ்ந்து தனித்த உணவு சேகரிப்பு உறுப்புகளைப் பயன்படுத்துகின்றன.",
                "The phrase 'polychaete' includes ecologically different feeding forms: predatory, grazing, deposit-feeding and suspension-feeding. Do not portray all parapodia as identical fins or all marine annelids as active hunters.",
                "பலசீட்டா கடற்புழுக்களில் வேட்டையாடுதல், மேய்தல், படிவ உணவூட்டம், நீரில் மிதக்கும் துகள்களை வடிகட்டுதல் போன்ற பல்வேறு முறைகள் உள்ளன. எல்லா பாராப்போடியாவும் ஒரே துடுப்பாகவோ எல்லா கடற்புழுக்களும் வேட்டையாடுபவையாகவோ இல்லை."),
            section("soil","Earthworm burrowing and soil ecology","மண்புழுவின் வளையமைத்தலும் மண் சூழலியலும்",
                "Soil-dwelling clitellates rely on moist integument for gas exchange and use body-wall muscles with small chaetae for traction. Feeding and burrowing mix organic matter with mineral soil, creating casts and channels that can influence aeration, water infiltration and nutrient turnover.",
                "நிலவாழ் கிளிட்டெல்லம் கொண்ட மண்புழுக்கள் ஈரமான உடற்பரப்பில் வாயுப் பரிமாற்றம் செய்கின்றன; தசைகளும் சிறிய சீட்டாக்களும் நிலப்பற்றிற்கு உதவும். உணவூட்டமும் வளையமைத்தலும் கரிமப் பொருளை மண்ணுடன் கலந்து காற்றோட்டம், நீர் ஊடுருவல் மற்றும் ஊட்டச்சத்து சுழற்சியைப் பாதிக்கின்றன.",
                "Ecological functions depend on species, soil and environmental conditions. Deep-burrowing and surface-litter-feeding earthworms are not interchangeable in ecological experiments; the presence of earthworms alone does not guarantee improved soil health in every ecosystem.",
                "மண்புழுவின் பங்கு இனம், மண் மற்றும் சுற்றுச்சூழலைப் பொறுத்தது. ஆழமாக வளையமைக்கும் இனங்களும் மேற்பரப்புக் கழிவுகளை உண்ணும் இனங்களும் ஒரே பணியைச் செய்யாது. மண்புழுக்கள் இருப்பதால் எல்லா சூழல்களிலும் மண் வளம் அதிகரிக்கிறது என பொதுமைப்படுத்தக் கூடாது."),
            section("leeches","Leech predation, attachment and feeding","அட்டைகளின் வேட்டையாடுதல், பற்றுதல் மற்றும் உணவூட்டம்",
                "Many leeches are predators of small aquatic organisms; some feed on vertebrate blood or tissue fluids. Anterior and posterior suckers assist attachment and locomotion. Blood-feeding species may secrete anticoagulant compounds, but the familiar medicinal leech is not a representative of every leech lifestyle.",
                "பல அட்டைகள் நீர்வாழ் சிறு உயிரினங்களை வேட்டையாடுகின்றன; சில வகைகள் முதுகெலும்பிகளின் இரத்தம் அல்லது உடல் திரவத்தை உண்ணுகின்றன. முன் மற்றும் பின் உறிஞ்சிகள் பற்றுதலுக்கும் இயக்கத்திற்கும் உதவும். இரத்தம் உறிஞ்சும் இனங்களின் பண்புகளை எல்லா அட்டைகளுக்கும் பொதுவாக்கக் கூடாது.",
                "Leech body surfaces may exhibit multiple secondary annuli for one underlying segment. Unlike Nereis, typical leeches lack large paired parapodia. Their coelomic and circulatory arrangements are specialized and should not be redrawn from a polychaete diagram.",
                "அட்டையின் ஒரு உள்கண்டத்திற்கு பல புறவளையங்கள் இருக்கலாம். நெரீஸில் இருப்பதைப் போன்ற பெரிய இணைப் பாராப்போடியா வழக்கமான அட்டைகளில் இல்லை. அவற்றின் உடற்குழி மற்றும் சுற்றோட்ட அமைப்பை கடற்புழுப் படத்திலிருந்து நகலிடக் கூடாது."),
            section("reproduction","Reproductive diversity and life-history change","இனப்பெருக்கப் பன்முகத்தன்மையும் வாழ்வியல் மாற்றமும்",
                "Clitellate annelids often possess a glandular clitellum that participates in cocoon formation. Many marine annelids release gametes into seawater and may pass through ciliated larval stages. Some nereidids develop reproductive swimming forms, while many other annelids reproduce differently.",
                "கிளிட்டெல்லம் கொண்ட வளையப்புழுக்களில் அதன் சுரப்பு முட்டைக் கூடு உருவாக்க உதவும். பல கடற்புழுக்கள் கடல் நீரில் இனச்செல்களை வெளியிட்டு சிலியா கொண்ட இளநிலைகளை உருவாக்கலாம். சில நெரீயிடுகளில் இனப்பெருக்க நீந்தும் வடிவம் உண்டு; எல்லா வளையப்புழுக்களுக்கும் இது பொதுவானது அல்ல.",
                "Adaptations reflect habitat and lineage rather than a single linear progression from marine to terrestrial forms. Compare the structures and habitat of at least two named representatives before asserting how an organ functions.",
                "தகவமைப்புகள் வாழிடத்தையும் பரிணாமக் குழுவையும் பொறுத்தவை; கடலிலிருந்து நிலத்துக்கு ஒரே நேர்கோட்டுப் பரிணாம வரிசை என்று கருத முடியாது. ஓர் உறுப்பின் செயலை விளக்குவதற்கு முன் இரண்டு பெயரிடப்பட்ட பிரதிநிதிகளை ஒப்பிட்டுப் பார்க்க வேண்டும்.")
        ), listOf(annelids)),
        ChapterLesson("u4-arthropoda",b("Arthropoda: diversity and functional anatomy","கணுக்காலிகள்: பன்முகத்தன்மையும் உடல்செயல்முறையும்"),listOf(
            section("diagnosis","Characters, ecdysis and jointed appendages","பண்புகள், தோலுரித்தல் மற்றும் மூட்டுக்கால்கள்",
                "Arthropods possess a segmented body with jointed appendages and an external cuticle reinforced by chitin and often other materials. Because the cuticle does not expand indefinitely, growth involves ecdysis, a coordinated molt. Arthropods are members of Ecdysozoa, not segmented annelids with a thin nonmolting cuticle.",
                "கணுக்காலிகளில் கண்டங்களாகப் பிரிந்த உடலும் மூட்டுகளுடைய இணைக் கால்களும் கைட்டின் சார்ந்த வெளிச்சட்டகமும் உள்ளன. வளர்ச்சிக்காகப் பழைய தோலை உரிக்கும் எக்டிசிஸ் நடைபெறுகிறது. இவை தோலுரிக்கும் விலங்குகளின் Ecdysozoa குழுவைச் சேர்ந்தவை; வளையப்புழுக்கள் அல்ல.",
                "The main body regions are specialized tagmata. An insect usually shows head, thorax and abdomen; many decapod crustaceans possess a cephalothorax and abdomen. The number of anatomical segments within a tagma is not necessarily the same across arthropod groups.",
                "சிறப்பாக்கப்பட்ட இணைந்த உடற்பகுதிகள் டாக்மாட்டா எனப்படும். பூச்சியில் தலை, மார்பு, வயிறு காணப்படும்; பல பத்துக்காலி ஓட்டுடலிகளில் தலைமார்பும் வயிறும் உள்ளன. ஒவ்வொரு பகுதியின் கண்ட எண்ணிக்கை எல்லா கணுக்காலிகளிலும் ஒன்றுபோல இருக்காது."),
            section("diversity","Major living groups and comparative characters","முக்கிய உயிருள்ள குழுக்களும் ஒப்பீட்டுப் பண்புகளும்",
                "Chelicerates include spiders, scorpions and horseshoe crabs; their anterior appendages include chelicerae, and they lack antennae. Myriapods include centipedes and millipedes, with numerous trunk segments. Hexapods include insects with three pairs of adult thoracic legs.",
                "சிலிசெரேட்டுகளில் சிலந்தி, தேள், குதிரைநண்டு போன்றவை அடங்கும்; முன்புற சிலிசெரா உறுப்புகள் உள்ளன, உணர்கொம்புகள் இல்லை. மிரியாபோடுகளில் நூற்றுக்காலி, ஆயிரங்காலி போன்றவை பல உடற்கண்டங்களுடன் காணப்படும். பூச்சிகளில் முதிர்ந்த நிலையில் மார்பில் மூன்று ஜோடி கால்கள் உள்ளன.",
                "Crustacean diversity includes copepods, barnacles, crabs, shrimps and prawns, with substantial differences in larval development. Traditional college classification and modern phylogeny are not completely identical; in particular, crustaceans and hexapods share complex evolutionary relationships.",
                "ஓட்டுடலிகளில் கோப்பிபோடுகள், ஒட்டுநண்டுகள், நண்டுகள், இறால்கள் போன்ற பன்முக வடிவங்கள் உள்ளன. இவற்றின் இளநிலை வளர்ச்சியும் மாறுபடும். பழைய கல்லூரி வகைப்பாட்டும் நவீன பரிணாம உறவுகளும் முழுமையாக ஒன்றல்ல; ஓட்டுடலிகளுக்கும் பூச்சிகளுக்கும் நெருங்கிய பரிணாமத் தொடர்புகள் உள்ளன."),
            section("physiology","Respiration, excretion and circulation","சுவாசம், கழிவுநீக்கம் மற்றும் சுற்றோட்டம்",
                "Most familiar arthropods circulate hemolymph through an open hemocoelic system using a dorsal contractile vessel. Insects typically exchange gases through tracheae opening at spiracles, crustaceans often use gills, and arachnids may employ book lungs or tracheae. No single respiratory structure characterizes the entire phylum.",
                "பல கணுக்காலிகளில் முதுகுப்புறச் சுருங்கும் நாளத்தின் உதவியுடன் திறந்த ஹீமோகோயல் சுற்றோட்டம் காணப்படுகிறது. பூச்சிகளில் காற்றுக்குழல்கள், ஓட்டுடலிகளில் பெரும்பாலும் செவுள்கள், சிலந்திக் குழுக்களில் புத்தக நுரையீரல்களோ காற்றுக்குழல்களோ இருக்கலாம். ஒரே சுவாச அமைப்பு எல்லா கணுக்காலிகளுக்கும் பொதுவல்ல.",
                "Excretory mechanisms differ substantially: many insects possess Malpighian tubules, while decapod crustaceans commonly employ antennal glands alongside branchial ion regulation. Arthropod locomotion depends on muscles working across articulated cuticular joints.",
                "கழிவுநீக்கமும் குழுவுக்கு ஏற்ப மாறுபடும். பல பூச்சிகளில் மால்பீஜியன் குழாய்கள் உள்ளன; இறால் போன்ற பத்துக்காலிகளில் உணர்கொம்புச் சுரப்பிகள் மற்றும் செவுள்களின் அயனி ஒழுங்குப்பாடு முக்கியம். மூட்டுச்சட்டகங்களில் இணைந்த தசைகள் இயக்கத்தை ஏற்படுத்துகின்றன."),
            section("relevance","Economic, ecological and practical importance","பொருளாதாரம், சூழலியல் மற்றும் செய்முறை முக்கியத்துவம்",
                "Arthropods include pollinators, decomposers, predators, agricultural pests and disease vectors. Fisheries and aquaculture depend on crustaceans such as shrimps and crabs. Pest management must distinguish the target species and its life stage, rather than relying only on generic diagrams.",
                "கணுக்காலிகளில் மகரந்தச் சேர்க்கையாளர்கள், சிதைப்புயிரிகள், வேட்டையாளர்கள், விவசாயப் பூச்சிகள், நோய்க்காவிகள் உள்ளன. இறால், நண்டு போன்ற ஓட்டுடலிகள் மீன்வளத்திலும் வளர்ப்பிலும் முக்கியம். பூச்சி மேலாண்மையில் உயிரி இனத்தையும் வளர்நிலையையும் சரியாக அடையாளம் காண வேண்டும்.",
                "For practical recognition, record the type and number of antennae, body tagmata, walking-leg pairs and mouthpart modifications of the actual animal. Do not depict a penaeid marine shrimp with enlarged freshwater Macrobrachium second chelipeds.",
                "செய்முறையில் உயிரியின் உணர்கொம்புகள், உடற்பகுதிகள், நடைக்கால் எண்ணிக்கை, வாயுறுப்பு மாற்றங்கள் ஆகியவற்றைக் கவனிக்க வேண்டும். கடல் பெனீயிட் இறாலை நன்னீர் Macrobrachium-இன் பெரிய இரண்டாம் பிடிக்கால்களுடன் வரையக் கூடாது.")
        ), listOf(arthropods)),
        ChapterLesson("u4-peripatus",b("Peripatus and velvet-worm biology","பெரிபேட்டஸ் மற்றும் வெல்வெட் புழு உயிரியல்"),listOf(
            section("identity","Onychophora: classification and distribution","ஒனிக்கோஃபோரா: வகைப்பாடும் பரவலும்",
                "Peripatus is a velvet-worm genus in phylum Onychophora; the name is sometimes used loosely for velvet worms as a group. Extant onychophorans belong to the families Peripatidae and Peripatopsidae and occur in suitable humid terrestrial habitats, with many species geographically restricted.",
                "பெரிபேட்டஸ் என்பது ஒனிக்கோஃபோரா தொகுதியைச் சேர்ந்த வெல்வெட் புழுப் பேரினம்; சில நூல்களில் அனைத்து வெல்வெட் புழுக்களுக்கும் பொதுப்பெயராகப் பயன்படுத்தப்படுகிறது. வாழும் இக்குழுவில் பெரிபேட்டிடே, பெரிபேட்டாப்சிடே குடும்பங்கள் உள்ளன. ஈரமான நிலவாழிடங்களில் பல இனங்கள் குறுகிய பரப்பில் காணப்படுகின்றன.",
                "Older texts call velvet worms a 'connecting link' between annelids and arthropods. This is historical shorthand, not a literal living ancestor of either phylum: Onychophora is a distinct living evolutionary lineage within Panarthropoda.",
                "பழைய நூல்களில் வளையப்புழுக்களுக்கும் கணுக்காலிகளுக்கும் இடைப்பட்ட இணைப்பாக வெல்வெட் புழுக்கள் குறிப்பிடப்படுகின்றன. இது வரலாற்றுப் பாடநூல் வழக்கம்; இன்றைய வெல்வெட் புழு எந்தக் குழுவின் நேரடி உயிருள்ள மூதாதையும் அல்ல. ஒனிக்கோஃபோரா தனி பரிணாமக் குழுவாகும்."),
            section("external","Antennae, oral papillae and lobopods","உணர்கொம்புகள், வாய்ப் பாப்பில்லாக்கள் மற்றும் மென்மைக் கால்கள்",
                "The soft, elongated trunk bears paired, unjointed lobopod limbs ending in claws. The head bears a pair of sensory antennae, jaws within the mouth and paired slime papillae close to the mouth. Lobopods differ fundamentally from the stiff jointed appendages of true arthropods.",
                "மென்மையான நீண்ட உடற்பகுதியில் மூட்டுகளற்ற இணையான லோபோபோட் கால்களும் அவற்றின் நுனியில் நகங்களும் உள்ளன. தலைப்பகுதியில் இணை உணர்கொம்புகள், வாயில் தாடைகள் மற்றும் வாய்க்கருகில் இணை ஒட்டுச் சளிப் பாப்பில்லாக்கள் காணப்படும். இவை கணுக்காலிகளின் கடின மூட்டுக்கால்களிலிருந்து வேறுபடுகின்றன.",
                "The cuticle is flexible and periodically molted; the animal loses water readily and favours moist protected microhabitats. Draw the antennae, oral papillae, trunk limbs and terminal claws as four distinct features rather than labelling an undifferentiated set of tentacles.",
                "நெகிழும் புறத்தோல் காலந்தோறும் உரிகிறது. நீர் இழப்பிற்கு உணர்திறன் அதிகமாதலால் ஈரமான மறைவிடங்கள் விரும்பப்படுகின்றன. உணர்கொம்பு, வாய்ப் பாப்பில்லா, உடற்கால், நுனி நகம் ஆகியவற்றை படத்தில் தனித்த உறுப்புகளாகக் காட்ட வேண்டும்."),
            section("organ-systems","Slime capture, respiration and internal organisation","ஒட்டுச்சளி பிடிப்பு, சுவாசம் மற்றும் உட்புற அமைப்பு",
                "Predatory velvet worms eject adhesive threads from the paired slime papillae, entangling prey before the jaws process it. The secretion is produced by slime glands and is not venom delivered through articulated fangs. Their locomotion combines lobopod movement with a fluid-supported muscular body.",
                "வேட்டையாடும் வெல்வெட் புழுக்கள் இணையான வாய்ப் பாப்பில்லாக்களிலிருந்து ஒட்டும் நூல்களை வெளியிட்டு இரையைச் சிக்கவைக்கின்றன; பின்னர் தாடைகள் உணவைக் கையாளும். இச்சுரப்பு சளிச் சுரப்பிகளில் உற்பத்தியாகிறது; மூட்டுப் பற்கள் வழியாக செலுத்தும் நஞ்சு அல்ல. மென்மைக் கால்களும் தசைமிக்க உடலும் இயக்கத்திற்கு உதவும்.",
                "Onychophorans have an open hemocoelic circulation, tracheal respiratory tubes opening by small spiracles, and segmentally associated excretory organs. Their tracheal system and other traits differ in detail from those of insects; do not copy an insect's diagram uncritically.",
                "ஒனிக்கோஃபோராவில் திறந்த ஹீமோகோயல் சுற்றோட்டம், சிறு சுவாசத்துளைகளால் வெளித்திறக்கும் காற்றுக்குழல்கள் மற்றும் கண்டத் தொடர்புடைய கழிவுநீக்க உறுப்புகள் உள்ளன. இவற்றின் காற்றுக்குழல் அமைப்பு பூச்சிகளின் அமைப்புடன் முழுவதும் ஒன்றல்ல; பூச்சியின் படத்தை நேரடியாக நகலிடக் கூடாது."),
            section("reproduction","Reproduction, development and conservation","இனப்பெருக்கம், வளர்ச்சி மற்றும் பாதுகாப்பு",
                "Velvet-worm reproduction is diverse: egg laying, retention of developing eggs and live birth occur in different lineages. Females can have long gestation, but a particular reproductive mechanism must not be assigned to all Peripatus species without source evidence.",
                "வெல்வெட் புழுக்களில் முட்டையிடுதல், உடலில் முட்டையை வைத்துப் பாதுகாத்தல், உயிரோடு குட்டி ஈனுதல் போன்ற முறைகள் வெவ்வேறு குழுக்களில் காணப்படுகின்றன. சில இனங்களில் கருக்காலம் நீண்டது. ஒரு குறிப்பிட்ட முறையை எல்லா பெரிபேட்டஸ் இனங்களுக்கும் பொதுவாகக் கருதக்கூடாது.",
                "Many velvet worms require stable humid habitats and have narrow ranges, making habitat fragmentation a conservation issue. Identify specimens by documented genus-level characters and source; do not infer the species from colour or leg count alone.",
                "பல வெல்வெட் புழுக்கள் நிலையான ஈரப்பதமுள்ள குறுகிய வாழிடங்களைச் சார்ந்துள்ளதால் வாழிடச் சிதைவு பாதுகாப்புச் சிக்கலாகிறது. நிறம் அல்லது கால்களின் எண்ணிக்கை மட்டுமே கொண்டு இனத்தை முடிவு செய்யாமல் நம்பத்தகுந்த அடையாளப் பண்புகளையும் ஆதாரத்தையும் பயன்படுத்த வேண்டும்.")
        ), listOf(onychophora)),
        ChapterLesson("u4-crustacean-larvae",b("Crustacean larval forms and development","ஓட்டுடலிகளின் இளநிலைகளும் வளர்ச்சியும்"),listOf(
            section("development","Why larval pathways differ among crustaceans","ஓட்டுடலிகளில் வளர்ச்சி பாதைகள் ஏன் மாறுபடுகின்றன",
                "Crustaceans show considerable developmental diversity: some hatch as nauplii, others as later larval forms or miniature juveniles. Embryonic development, hatch timing, feeding and number of molts vary with taxon. A single universal 'nauplius → zoea → megalopa → adult' chain is scientifically incorrect.",
                "ஓட்டுடலிகளில் வளர்ச்சி மிகவும் மாறுபடும். சிலவற்றில் நௌப்ளியஸ், சிலவற்றில் பிந்தைய இளநிலை அல்லது சிறிய இளம் உயிரி வெளிவரும். கருவளர்ச்சி, குஞ்சு வெளிவரும் நேரம், உணவூட்டம், தோலுரித்தல் எண்ணிக்கை குழுவுக்கு ஏற்ப மாறும். அனைத்து ஓட்டுடலிகளுக்கும் ஒரே நௌப்ளியஸ்–சோயியா–மெகலோபா வரிசை இல்லை.",
                "Larval morphology has practical value for plankton identification, aquaculture and recruitment studies. Each drawing should show structures that define the stage, not merely a generic oval animal with arbitrary antennae.",
                "இளநிலை உடலமைப்பு மிதவை உயிரின ஆய்வு, வளர்ப்பு மற்றும் இளஉயிரி சேர்க்கை ஆய்வுகளில் உதவும். ஒவ்வொரு படத்திலும் அந்த வளர்நிலைக்குரிய அடையாள உறுப்புகள் இருக்க வேண்டும்; சாதாரண நீள்வட்ட உடலுடன் சீரற்ற உணர்கொம்புகளை மட்டும் வரையக் கூடாது."),
            section("nauplius","Nauplius: three paired swimming appendages","நௌப்ளியஸ்: மூன்று ஜோடி நீந்தும் உறுப்புகள்",
                "A typical nauplius has three pairs of functional appendages: antennules, antennae and mandibles, often with a median naupliar eye. Posterior segmentation and later thoracic appendages are not yet expressed as in the juvenile. This stage is characteristic of many crustacean life histories but is not always released as a free-swimming hatchling.",
                "பொதுவான நௌப்ளியஸில் முதலுணர்கொம்பு, இரண்டாம் உணர்கொம்பு, மண்டிபிள் என மூன்று ஜோடி செயல்படும் இணைப்புறுப்புகள் உள்ளன; பலவற்றில் நடுக்கண் காணப்படும். பின்புறக் கண்டங்களும் பின்னைய மார்புறுப்புகளும் இன்னும் முழுமையாக வெளிப்படவில்லை. எல்லா இனங்களிலும் இது சுதந்திர நீந்தும் குஞ்சாக வெளியே வருவதில்லை.",
                "A penaeid marine shrimp normally passes through free-swimming nauplius stages after hatching, followed by protozoea, mysis and postlarval stages. This sequence must not be substituted with the developmental sequence of a brachyuran crab or freshwater Macrobrachium prawn.",
                "கடல் பெனீயிட் இறாலில் நௌப்ளியஸ் நிலைகளைத் தொடர்ந்து புரோட்டோசோயியா, மைசிஸ், பிந்தைய இளநிலைகள் வருகின்றன. இவ்வரிசையை உண்மை நண்டு அல்லது நன்னீர் Macrobrachium இறாலின் வளர்ச்சி வரிசையுடன் மாற்றிப் பயன்படுத்தக் கூடாது."),
            section("zoea","Zoea and mysis: different decapod usages","சோயியா மற்றும் மைசிஸ்: வேறுபட்ட பத்துக்காலி நிலைகள்",
                "In many brachyuran crabs, zoeae bear conspicuous larval spines and use thoracic appendages for swimming, with morphology changing through molts. The number of zoeal instars varies by species. In penaeid shrimp teaching, 'protozoea' and 'mysis' describe particular successive stages; these are not simply identical to the familiar crab zoea.",
                "பல உண்மை நண்டுகளின் சோயியா இளநிலையில் நீண்ட முட்கள் மற்றும் நீந்த உதவும் மார்புறுப்புகள் காணப்படும்; தோலுரித்தலுடன் அவை மாறுகின்றன. சோயியா நிலைகளின் எண்ணிக்கை இனத்தைப் பொறுத்தது. பெனீயிட் இறாலின் புரோட்டோசோயியா, மைசிஸ் ஆகியவற்றை நண்டின் சோயியாவுடன் முழுவதும் ஒன்றாகக் கருதக்கூடாது.",
                "Larval appendages and feeding behaviour change with development. The scientific name of the organism and the view of the illustrated specimen must accompany any stage label to prevent a crab zoea being mistaken for a Penaeus mysis.",
                "வளர்ச்சியுடன் இணைப்புறுப்புகளும் உணவூட்ட முறையும் மாறுகின்றன. படத்தில் உயிரியின் அறிவியல் பெயரும் எந்தப் பக்கப் பார்வை என்பதும் குறிப்பிடப்பட வேண்டும்; நண்டு சோயியாவை Penaeus மைசிஸ் என்று தவறாகக் குறிக்கக் கூடாது."),
            section("megalopa","Megalopa, settlement and metamorphosis","மெகலோபா, குடியேற்றம் மற்றும் உருமாற்றம்",
                "A brachyuran crab commonly passes from successive zoeal stages to a megalopa, a transitional form combining swimming ability with a more crab-like body and developed appendages. After settlement and molt the first juvenile crab appears. Settlement timing depends on species and environmental cues.",
                "பல உண்மை நண்டுகளில் சோயியா நிலைகளைத் தொடர்ந்து மெகலோபா உருவாகிறது. இதில் நீந்தும் திறனுடன் நண்டைப் போன்ற உடலமைப்பும் வளர்ந்த இணைப்புறுப்புகளும் காணப்படும். அடித்தளத்தில் குடியேறி தோலுரித்த பிறகு இளம் நண்டு உருவாகிறது. குடியேற்ற நேரம் இனத்தையும் சூழல் சுட்டுகளையும் பொறுத்தது.",
                "Megalopa is not a compulsory stage in the textbook Penaeus nauplius–protozoea–mysis–postlarva sequence. Likewise, the postlarva of a shrimp is not automatically equivalent in morphology to a crab megalopa, even when both represent later developmental transitions.",
                "மெகலோபா என்பது பெனீயிட் இறாலின் நௌப்ளியஸ்–புரோட்டோசோயியா–மைசிஸ்–பிந்தைய இளநிலை வரிசையில் கட்டாய நிலை அல்ல. இறாலின் பிந்தைய இளநிலையும் நண்டின் மெகலோபாவும் உருவ அமைப்பில் ஒன்றல்ல."),
            section("practice","Recognizing larval stages in the laboratory","ஆய்வகத்தில் இளநிலைகளை அடையாளம் காணுதல்",
                "Begin with the specimen's identified crustacean group. For a nauplius check three paired appendages and any median eye; for a crab zoea inspect the spine arrangement, carapace and larval swimming appendages; for a crab megalopa inspect more developed walking legs and a reorganizing abdomen.",
                "முதலில் மாதிரி எந்த ஓட்டுடலி குழுவைச் சேர்ந்தது என்பதை உறுதிப்படுத்த வேண்டும். நௌப்ளியஸில் மூன்று ஜோடி உறுப்புகளையும் நடுக்கண்ணையும்; நண்டு சோயியாவில் முட்கள், புறக்கவசம், நீந்தும் உறுப்புகளையும்; மெகலோபாவில் வளர்ந்த நடைக்கால்களையும் மாற்றம் பெறும் வயிற்றுப்பகுதியையும் கவனிக்க வேண்டும்.",
                "Do not deduce an exact larval instar from artistic size alone. Use calibrated microscopy, taxonomic context and stage-specific anatomical features. Practical-record drawings should have accurate leader lines and explicitly identify whether they depict a shrimp, a true crab or another crustacean.",
                "படத்தின் அளவை மட்டும் கொண்டு துல்லியமான வளர்நிலையை முடிவு செய்யக்கூடாது. அளவீடு கொண்ட நுண்ணோக்கி ஆய்வு, வகைப்பாட்டுத் தகவல், நிலைக்குரிய உடற்கூறு பண்புகள் ஆகியவை தேவை. படத்தில் இறாலா, உண்மை நண்டா அல்லது வேறு ஓட்டுடலியா என்பதைத் தெளிவாகக் குறிப்பிட வேண்டும்.")
        ), listOf(larvae,crab))
    )
    val byId:Map<String,ChapterLesson> = chapters.associateBy { it.chapterId }
    init {
        check(chapters.size==6)
        check(byId.size==6)
        check(chapters.map{it.chapterId}.toSet()==setOf("u4-annelida","u4-metamerism","u4-modes-life","u4-arthropoda","u4-peripatus","u4-crustacean-larvae"))
        check(chapters.all { it.sections.all { section -> section.paragraphs.all {
            paragraph -> paragraph.english.length>=100 && paragraph.tamil.length>=70
        } } })
    }
}