package com.gasczoology.invertebratelab.data

/**
 * Unit IV Penaeus native teaching DRAFT, not an A5 question replacement.
 *
 * Genus-level traits are separated from species-specific exceptions. Do NOT
 * silently substitute freshwater Macrobrachium morphology/larvae for Penaeus.
 *
 * Primary teaching comparators:
 * FAO https://www.fao.org/4/AC179E/AC179E00.htm
 * FAO https://www.fao.org/4/ac232e/ac232e04.htm
 * FAO https://www.fao.org/4/ac765t/ac765t06.htm
 */
internal data class PenaeusTeachingSection(
    val id: String,
    val heading: BilingualText,
    val paragraphs: List<BilingualText>,
    val source: String,
) {
    init {
        require(id.matches(Regex("[a-z]+(-[a-z]+)*")))
        require(paragraphs.size >= 2 && paragraphs.all {
            it.english.length >= 55 && it.tamil.length >= 25
        })
        require(source.startsWith("https://www.fao.org/"))
    }
}

internal data class PenaeusAppendageGroup(
    val id: String,
    val region: BilingualText,
    val names: BilingualText,
    val numberOfPairs: Int,
    val role: BilingualText,
    val caution: BilingualText,
)

internal data class PenaeusLarvalStage(
    val id: String,
    val name: BilingualText,
    val morphology: BilingualText,
    val ecology: BilingualText,
)

internal object UnitIVPenaeusTeaching {
    private fun t(en:String,ta:String)=BilingualText(en,ta)
    private const val FAO_BIO = "https://www.fao.org/4/AC179E/AC179E00.htm"
    private const val FAO_HATCH = "https://www.fao.org/4/ac232e/ac232e04.htm"
    private const val FAO_TAXON = "https://www.fao.org/4/ac765t/ac765t06.htm"

    val sections=listOf(
        PenaeusTeachingSection("classification",
            t("Scientific identity and classification","அறிவியல் அடையாளமும் வகைப்பாடும்"),
            listOf(
                t("This undergraduate type study is anchored to the Indian white shrimp Penaeus indicus (H. Milne Edwards, 1837), placed in Fenneropenaeus indicus in some taxonomic treatments. It belongs to Arthropoda, Crustacea, Malacostraca, Decapoda and the dendrobranchiate penaeid shrimps. Other Penaeus species cannot be substituted as the same specimen without identification.",
                  "இந்த இளநிலை இன ஆய்வின் மாதிரி இந்திய வெள்ளை இறால் Penaeus indicus (H. Milne Edwards, 1837) ஆகும். சில வகைப்பாட்டு முறைகளில் Fenneropenaeus indicus என்றும் அழைக்கப்படுகிறது. Arthropoda, Crustacea, Malacostraca, Decapoda மற்றும் பெனேயிட் குழுவில் அடங்கும். வேறொரு Penaeus இனப் படத்தை இதே இனமாக அடையாளம் காட்டக்கூடாது."),
                t("Older zoology texts use broad 'prawn' labels, but freshwater Macrobrachium belongs to a different decapod lineage. Its large second chelipeds, brooded eggs and abbreviated larval representations must NOT be copied into a penaeid type study.",
                  "பழைய நூல்களில் 'இறால்' என்ற பொதுப்பெயர் பல்வேறு டெகாபோட் குழுக்களுக்குப் பயன்படுத்தப்படுகிறது. நன்னீர் Macrobrachium வேறொரு குழுவாகும்; அதன் பெரிய இரண்டாவது பிடிக்கால்கள், முட்டைச் சுமத்தல் மற்றும் வளர்ச்சி விவரங்களை Penaeus-க்கு மாற்றிக் காட்டக்கூடாது.")
            ),FAO_TAXON),
        PenaeusTeachingSection("habitat",
            t("Marine habitat and feeding ecology","கடல்வாழிடமும் உணவூட்டச் சூழலியலும்"),
            listOf(
                t("Many penaeid shrimps mature and spawn offshore; planktonic larvae drift toward coastal and estuarine nursery grounds. Habitat use differs among species and growth stages, so an adult is not described as a permanent freshwater prawn.",
                  "பல பெனேயிட் இறால்கள் திறந்த கடலில் முதிர்ந்து இனப்பெருக்கம் செய்கின்றன; மிதவை இளநிலைகள் கடற்கரை மற்றும் கழிமுக வளரிடங்களை அடைகின்றன. இனம், வளர்நிலை ஆகியவற்றைப் பொறுத்து வாழிடங்கள் மாறுபடும்; முதிர்ந்த Penaeus-ஐ நிலையான நன்னீர் இறாலாகக் கருதக்கூடாது."),
                t("Feeding can include detritus and small benthic invertebrates. Penaeid larval food changes with metamorphosis: nauplii use yolk reserves, while later planktonic stages take external food.",
                  "மட்கிய கரிமப் பொருட்களும் சிறிய அடிவாழ் முதுகெலும்பிலிகளும் உணவில் இடம்பெறலாம். வளர்நிலைக்கு ஏற்ப இளஇறாலின் உணவு மாறுகிறது; நௌப்ளியஸ் கரு உணவுச் சேமிப்பைப் பயன்படுத்தும்; பின்னர் வெளிப்புற உணவை உட்கொள்கிறது.")
            ),FAO_BIO),
        PenaeusTeachingSection("external",
            t("External morphology: cephalothorax and abdomen","வெளியமைப்பு: தலைமார்பும் வயிற்றுப்பகுதியும்"),
            listOf(
                t("The adult shows a fused cephalothorax beneath a protective carapace and a muscular, articulated six-segmented abdomen. The anterior rostrum bears species-dependent teeth; stalked compound eyes and paired sensory antennules and antennae occur near the head.",
                  "முதிர்ந்த இறாலில் கடினப் புறக்கவசத்திற்குள் இணைந்த தலைமார்பும், ஆறு கண்டங்களாகப் பிரிந்த தசையுள்ள வயிற்றுப்பகுதியும் உள்ளன. முன்புற ரோஸ்ட்ரத்தில் இனத்திற்கு ஏற்ப பற்கள் இருக்கும்; காம்புள்ள கூட்டுக்கண்களும் உணர்வுக்குரிய முதல், இரண்டாம் உணர்கொம்புகளும் காணப்படும்."),
                t("Pleopods on the first five abdominal segments aid swimming; the sixth pair of abdominal appendages are uropods that flank the unpaired telson, forming the tail fan. The telson itself is NOT a paired appendage.",
                  "வயிற்றின் முதல் ஐந்து கண்டங்களிலுள்ள நீந்துகால்கள் நீந்த உதவுகின்றன. ஆறாவது கண்டத்தின் ஜோடி யூரோபாட்கள் நடுவிலுள்ள ஒற்றை டெல்சனுடன் சேர்ந்து வால் விசிறியை உருவாக்குகின்றன. டெல்சன் தனியாக ஒரு ஜோடிக் காலல்ல.")
            ),FAO_BIO),
        PenaeusTeachingSection("body-wall",
            t("Exoskeleton, muscles and moulting","புறஎலும்புக்கூடு, தசைகள் மற்றும் தோலுரிதல்"),
            listOf(
                t("The chitinous mineralized cuticle of the crustacean exoskeleton supports and protects the soft body and provides attachment sites for striated muscles. Flexible joints between sclerites permit locomotion and abdominal flexion.",
                  "கைட்டின் மற்றும் கனிமப் பொருட்களால் வலுவடைந்த புறஉறை உடலைப் பாதுகாத்து கோடுதசைகள் பற்றுவதற்கான இடமளிக்கிறது. உறைத்தகடுகளிடையிலான வளைந்து கொடுக்கும் மூட்டுகள் நடப்பதற்கும் வயிற்றுப் பகுதியை மடக்குவதற்கும் உதவுகின்றன."),
                t("Growth requires ecdysis: the old cuticle is shed and a new larger cuticle hardens after the moult. Recently moulted shrimps are relatively vulnerable; moulting cannot be described as a continuous increase in a rigid unbroken shell.",
                  "வளர்ச்சிக்குத் தோலுரிதல் (ecdysis) தேவைப்படுகிறது; பழைய புறஉறை நீங்கி புதிய பெரிய உறை பின்னர் வலுப்படுகிறது. தோலுரிந்த உடனே இறால் பாதுகாப்பின்றி இருக்கும்; ஒருபோதும் உரியாத கடின உறைக்குள் தொடர்ந்து வளர்வதாகக் கூறக்கூடாது.")
            ),FAO_BIO),
        PenaeusTeachingSection("appendage-architecture",
            t("Segmental appendages: 19 pairs and serial specialization","கண்ட இணைக் கால்கள்: 19 ஜோடிகளும் பணி வேறுபாடும்"),
            listOf(
                t("The conventional adult appendage series is five cephalic pairs (antennules, antennae, mandibles, maxillulae, maxillae), eight thoracic pairs (three maxillipeds plus five pereiopods) and six abdominal pairs (five pleopods plus one uropod pair): 5 + 8 + 6 = 19. Stalked eyes and the median telson are not additional paired limb entries in this count.",
                  "முதிர்ந்த இறாலின் வழக்கமான கணக்கில் தலையில் 5 ஜோடி (முதல் உணர்கொம்பு, இரண்டாம் உணர்கொம்பு, தாடை, முதல் மேல்தாடை, இரண்டாம் மேல்தாடை), மார்பில் 8 ஜோடி (3 துணை வாய்க்கால்கள், 5 நடைக்கால்கள்), வயிற்றில் 6 ஜோடி (5 நீந்துகால்கள், ஒரு யூரோபாட் ஜோடி): 5 + 8 + 6 = 19. காம்புக் கண்களையும் ஒற்றை டெல்சனையும் இக்கணக்கில் கூடுதல் ஜோடிகளாகச் சேர்க்க வேண்டாம்."),
                t("Crustacean limbs share serial homology but their endopodite, exopodite, basal protopodite, gill-associated structures and chelae vary by limb and taxon. A generalized biramous drawing must not assert that every adult appendage carries two equally developed branches.",
                  "க்ரஸ்டேசியன் கால்களுக்கு தொடர் ஒத்தமைவு இருந்தாலும், அவற்றின் உள்கிளை, வெளிக்கிளை, அடிப்பகுதி, செவுளுடன் தொடர்புடைய அமைப்புகள் மற்றும் பிடிப்புகள் கால்வகை, இனக்குழுவின்படி மாறுகின்றன. எல்லா முதிர்ந்த உறுப்புகளிலும் இரண்டு சமமான கிளைகள் உள்ளன என பொதுவான இருகிளை வரைபடம் காட்டக்கூடாது.")
            ),FAO_TAXON),
        PenaeusTeachingSection("nutrition",
            t("Alimentary canal and digestive gland","உணவுக்குழலும் செரிமானச் சுரப்பியும்"),
            listOf(
                t("Food passes from the mouth through foregut regions to a muscular stomach with cuticular filtering and processing specializations, then into the intestine and anus. The large hepatopancreas contributes digestive secretions and nutrient processing; it is not a vertebrate liver plus pancreas.",
                  "உணவு வாயிலிருந்து முன்குடல் மற்றும் தசையுள்ள இரைப்பையின் சிறப்பமைப்புகளைக் கடந்து குடலுக்கும் ஆசனவாய்க்கும் செல்கிறது. பெரிய ஹெபட்டோபாங்க்ரியாஸ் செரிமானச் சுரப்புகளுக்கும் ஊட்டச்சத்து மாற்றத்திற்கும் உதவுகிறது; இது முதுகெலும்பிகளின் கல்லீரல் மற்றும் கணையம் இரண்டும் தனியாக இருப்பதுபோல அல்ல."),
                t("Mouthpart appendages manipulate particles, while hepatopancreatic tubules are distinct from the gut lumen. Any anatomical plate must separate oesophagus, stomach, digestive gland, intestine and their actual duct connections.",
                  "வாய்க்கால்கள் உணவுத் துகள்களை கையாளுகின்றன; ஹெபட்டோபாங்க்ரியாஸின் நுண்குழல்கள் குடற்குழியிலிருந்து வேறானவை. படம் உணவுக்குழாய், இரைப்பை, செரிமானச் சுரப்பி, குடல் மற்றும் அவற்றின் உண்மையான குழாய் இணைப்புகளைத் தெளிவாகப் பிரிக்க வேண்டும்.")
            ),FAO_TAXON),
        PenaeusTeachingSection("respiration",
            t("Branchial chamber, gills and water flow","செவுள் அறை, செவுள்கள் மற்றும் நீரோட்டம்"),
            listOf(
                t("Respiration occurs across thin gill surfaces housed on the sides of the cephalothorax beneath the carapace. Branchial architecture is related to the dendrobranchiate identity of penaeid shrimp, but the exact gill formula is species-specific.",
                  "தலைமார்பின் பக்கங்களில் புறக்கவசத்தடியில் உள்ள செவுள்களின் மெல்லிய பரப்புகளில் வாயுப் பரிமாற்றம் நிகழ்கிறது. பெனேயிட்களின் கிளைமரச் செவுள் அமைப்பு அவற்றின் குழுவைச் சுட்டுகிறது; ஆனால் செவுள்களின் துல்லிய எண்ணிக்கையும் அமைப்பும் இனத்தைப் பொறுத்தது."),
                t("The scaphognathite region of the second maxilla helps move water through the branchial chamber. Do not confuse water-flow direction and haemolymph movement, or borrow freshwater Macrobrachium gill leaders for an unidentified Penaeus specimen.",
                  "இரண்டாம் மேல்தாடையிலுள்ள ஸ்காஃபோக்நதைட் பகுதி செவுள் அறையூடாக நீர் செல்ல உதவுகிறது. நீரோட்டத்தின் திசையையும் உடற்றிரவ ஓட்டத்தையும் ஒன்றாகக் காட்டக்கூடாது; அடையாளம் தெரியாத Penaeus படத்திற்கு நன்னீர் Macrobrachium செவுள் குறியீடுகளைக் கடன் வாங்கக் கூடாது.")
            ),FAO_TAXON),
        PenaeusTeachingSection("circulation",
            t("Open circulation and excretion","திறந்த சுற்றோட்டமும் கழிவுநீக்கமும்"),
            listOf(
                t("A dorsally placed heart circulates haemolymph in an open vascular system with tissue spaces (haemocoel), rather than a closed network of capillaries. Respiratory exchange and circulation are coupled but are separate processes.",
                  "முதுகுப்புற இதயம் உடற்றிரவத்தை திறந்த சுற்றோட்ட அமைப்பில் உடற்குழிவெளிகளுக்கு செலுத்துகிறது; இது நுண்குழாய்களால் முழுமையாக மூடிய அமைப்பல்ல. சுவாச வாயுப் பரிமாற்றமும் சுற்றோட்டமும் தொடர்புடையவை, ஆனால் வேறு செயல்முறைகள்."),
                t("Paired antennal (green) glands contribute osmoregulatory and excretory functions; their openings are associated with the bases of the antennae. These are not earthworm nephridia or vertebrate kidneys.",
                  "இரட்டை உணர்கொம்பு (பச்சை) சுரப்பிகள் நீர்-உப்புச் சமநிலைக்கும் கழிவுநீக்கத்திற்கும் உதவுகின்றன; அவற்றின் திறப்புகள் உணர்கொம்புகளின் அடிப்பகுதியில் உள்ளன. இவை மண்புழு நெஃப்ரிடியாக்களோ முதுகெலும்பிகளின் சிறுநீரகங்களோ அல்ல.")
            ),FAO_BIO),
        PenaeusTeachingSection("nervous",
            t("Nervous system and sensory structures","நரம்புமண்டலமும் உணர்வு உறுப்புகளும்"),
            listOf(
                t("The brain and circumoesophageal connections link to a ventral nerve cord with segmental ganglia; coordinated activity governs feeding, swimming and abdominal escape flexions. Stalked compound eyes provide vision, while antennae and antennules support chemical and mechanical sensing.",
                  "மூளை, உணவுக்குழாயைச் சுற்றிய நரம்பிணைப்புகள், வயிற்றுப்புற நரம்புத்தண்டு மற்றும் கண்டநரம்புமுடிச்சுகள் உணவு, நீந்துதல், தப்பிக்கும் வயிற்று மடக்கல் போன்ற இயக்கங்களை ஒருங்கிணைக்கின்றன. காம்புள்ள கூட்டுக்கண்கள் பார்வைக்குப் பயன்படும்; உணர்கொம்புகளும் முதலுணர்கொம்புகளும் வேதியியல் மற்றும் இயந்திர உணர்வுகளை வழங்குகின்றன."),
                t("Statocyst-like equilibrium receptors are associated with antennular sensory structures in many decapods; exact anatomy must be confirmed from the selected species or a reviewed source before drawing or hotspot placement.",
                  "பல டெகாபோடுகளில் நிலைச்சமநிலை உணரிகள் முதலுணர்கொம்புகளுடன் தொடர்புடையவை. இவற்றின் துல்லிய அமைப்பை குறிப்பிட்ட இன அல்லது ஆய்வுசெய்யப்பட்ட ஆதாரத்திலிருந்து உறுதிப்படுத்திய பின்னரே படக்குறி அல்லது தொடுபுள்ளி சேர்க்க வேண்டும்.")
            ),FAO_TAXON),
        PenaeusTeachingSection("reproduction",
            t("Sexes, petasma, thelycum and spawning","பால்வேறுபாடு, பெட்டாஸ்மா, தெலிகம், முட்டையிடுதல்"),
            listOf(
                t("Penaeid shrimp have separate sexes. A male's first pleopod pair forms the petasma involved in sperm transfer; the female thelycum is associated with ventral thoracic reproductive structures. Detailed shapes are species-dependent and cannot be inferred from a Macrobrachium image.",
                  "பெனேயிட் இறால்களில் ஆண், பெண் தனித்தனியே உள்ளன. ஆணின் முதல் நீந்துகால் ஜோடியின் மாற்றமைவு பெட்டாஸ்மா விந்துப் பரிமாற்றத்துடன் தொடர்புடையது; பெண்ணின் வயிற்றுப்புற மார்புப் பகுதியில் தெலிகம் அமைந்துள்ளது. இவற்றின் வடிவம் இனத்திற்கு ஏற்ப மாறுவதால் Macrobrachium படத்திலிருந்து ஊகிக்கக் கூடாது."),
                t("Spawning typically occurs in marine waters; eggs enter the water column rather than remaining attached in a long brood beneath the female abdomen. The larval sequence must match the penaeid shrimp source rather than freshwater prawn brood diagrams.",
                  "இனப்பெருக்கம் பொதுவாக கடல்நீரில் நடைபெறுகிறது; முட்டைகள் பெண்ணின் வயிற்றுக்கீழ் நீண்டகாலம் ஒட்டியிருப்பதற்குப் பதிலாக நீருக்குள் வெளியிடப்படுகின்றன. இளநிலை வரிசை பெனேயிட் இறாலுக்குரியதாக இருக்க வேண்டும்; நன்னீர் இறாலின் முட்டைச் சுமத்தல் படத்திற்குரியதல்ல.")
            ),FAO_BIO),
        PenaeusTeachingSection("development",
            t("Nauplius to protozoea, mysis and postlarva","நௌப்ளியஸ் முதல் புரோட்டோசோயா, மைசிஸ், பிந்தைய இளநிலை"),
            listOf(
                t("Penaeid larvae pass through distinct naupliar, protozoeal, mysis and postlarval stages before juvenile and adult forms. Nauplius uses three functional pairs of appendages and yolk reserves; protozoea develops obvious segmentation and feeding structures.",
                  "பெனேயிட் இறாலின் வளர்ச்சியில் நௌப்ளியஸ், புரோட்டோசோயா, மைசிஸ், பிந்தைய இளநிலை, இளமுதிர்நிலை, முதிர்நிலை ஆகியவை உள்ளன. நௌப்ளியஸில் மூன்று செயல்படும் இணை உறுப்புகளும் கரு உணவுச் சேமிப்பும் உள்ளன; புரோட்டோசோயாவில் கண்டங்களும் உணவூட்ட உறுப்புகளும் தெளிவாக உருவாகின்றன."),
                t("The mysis stage shows increasing thoracic appendage differentiation, followed by swimming pleopod development in postlarvae. 'Protozoea' is an older name often grouped as zoea; do not replace the stage series with the direct development typical of many other decapods.",
                  "மைசிஸ் நிலையில் மார்புக்கால்கள் மேலும் வேறுபடுகின்றன; பிந்தைய இளநிலையில் நீந்துகால்கள் செயல்படத் தொடங்குகின்றன. புரோட்டோசோயா என்பதை சில நூல்கள் சோயா நிலையின் ஒரு வகையாகக் குறிப்பிடுகின்றன; வேறு டெகாபோடுகளின் நேர்வளர்ச்சியை இங்கு மாற்றாகக் காட்டக்கூடாது.")
            ),FAO_HATCH),
        PenaeusTeachingSection("practical",
            t("Practical specimen study and limitations","செய்முறை மாதிரி ஆய்வும் வரம்புகளும்"),
            listOf(
                t("For a specimen, record the actual species name when available, rostral teeth, stalked eyes, antennules/antennae, five thoracic walking-leg pairs, five abdominal pleopod pairs, uropods and unpaired telson. Draw only visible structures and label the studied view.",
                  "மாதிரி ஆய்வில் முடிந்தவரை உண்மையான இனப்பெயர், ரோஸ்ட்ரப் பற்கள், காம்புக் கண்கள், முதலுணர்கொம்பு/உணர்கொம்பு, ஐந்து ஜோடி நடைக்கால்கள், ஐந்து ஜோடி நீந்துகால்கள், யூரோபாட்கள் மற்றும் ஒற்றை டெல்சன் ஆகியவற்றைப் பதிவு செய்க. நேரில் தெரியும் அமைப்புகளை மட்டுமே வரைந்து ஆய்வுப் பார்வையைக் குறிப்பிடுக."),
                t("The existing Unit IV Penaeus anatomical plates and organ leader-line hotspots are NOT independently accepted as original pencil scans. Use text and this appendage/larval-stage explorer until the exact specimen, image rights, source pixels, Tamil editing and physical-device audio are verified.",
                  "அலகு IV Penaeus உடற்கூறுப் படங்களும் உறுப்பு தொடுபுள்ளிகளும் அசல் பென்சில் ஸ்கேன்களாக இன்னும் சுயாதீனமாக ஏற்கப்படவில்லை. உண்மை மாதிரி, பட உரிமை, மூல பிக்சல்கள், தமிழ் மொழி மதிப்பாய்வு, இயற்பொறியில் ஒலி ஆகியவை உறுதிப்படுத்தப்படும் வரை உரை மற்றும் இணைக்கால்கள்/இளநிலை ஆய்வையே பயன்படுத்துக.")
            ),FAO_TAXON)
    )

    // Counts are the serial, not necessarily equally biramous, adult limbs.
    val appendages=listOf(
        PenaeusAppendageGroup("head",t("Cephalon","தலை"),t("Antennules, antennae, mandibles, maxillulae, maxillae","முதலுணர்கொம்புகள், உணர்கொம்புகள், தாடைகள், முதல்/இரண்டாம் மேல்தாடைகள்"),5,
            t("Sense the environment and process food; the maxillary scaphognathite helps branchial water flow.","சுற்றுப்புறத்தை உணர்ந்து உணவை கையாளுகின்றன; மேல்தாடை ஸ்காஃபோக்நதைட் செவுள் நீரோட்டத்திற்கு உதவும்."),
            t("Five paired head appendages, not five pairs of walking legs.","தலையின் ஐந்து இணை உறுப்புகள்; இவை ஐந்து ஜோடி நடைக்கால்கள் அல்ல.")),
        PenaeusAppendageGroup("thoracic-mouth",t("Thorax: feeding","மார்பு: உணவூட்டம்"),t("Three maxilliped pairs","மூன்று ஜோடி வாய்த்துணைக்கால்கள்"),3,
            t("Manipulate and position food near the mouth.","உணவை வாயருகே நகர்த்தி கையாள உதவுகின்றன."),
            t("Do not confuse maxillipeds with the five pereiopod pairs.","வாய்த்துணைக்கால்களையும் ஐந்து ஜோடி நடைக்கால்களையும் ஒன்றாகக் கருத வேண்டாம்.")),
        PenaeusAppendageGroup("thoracic-walking",t("Thorax: locomotion","மார்பு: இயக்கம்"),t("Five pereiopod pairs","ஐந்து ஜோடி நடைக்கால்கள்"),5,
            t("Walking, handling substrate and some feeding tasks; first three pairs may be chelate in penaeids.","நடத்தல், அடித்தளத்தைப் பற்றுதல் மற்றும் சில உணவு செயல்களுக்கு உதவும்; பெனேயிட்களில் முதல் மூன்று ஜோடிகள் பிடிக்கால்களாக இருக்கலாம்."),
            t("Penaeus cannot be shown with a Macrobrachium-like hypertrophied second cheliped.","Macrobrachium போன்ற மிகப் பெரிய இரண்டாவது பிடிக்காலை Penaeus-க்கு வரையக்கூடாது.")),
        PenaeusAppendageGroup("abdomen-swimming",t("Abdomen: swimming","வயிறு: நீந்துதல்"),t("Five pleopod pairs","ஐந்து ஜோடி நீந்துகால்கள்"),5,
            t("Swimming and manoeuvring; male first pleopods are modified as petasma.","நீந்தவும் திசைமாற்றவும் உதவுகின்றன; ஆணின் முதல் ஜோடி பெட்டாஸ்மாவாக மாற்றமடைகிறது."),
            t("These are abdominal limbs, not additional thoracic walking legs.","இவை வயிற்றுப்புற உறுப்புகள்; கூடுதல் மார்புநடைக்கால்கள் அல்ல.")),
        PenaeusAppendageGroup("tail-fan",t("Abdomen: tail fan","வயிறு: வால் விசிறி"),t("One uropod pair + unpaired telson","ஒரு யூரோபாட் ஜோடி + ஒற்றை டெல்சன்"),1,
            t("Fan-like tail structures support rapid rearward propulsion.","விசிறிபோன்ற வால் அமைப்பு விரைவான பின்தள்ளலுக்கு உதவும்."),
            t("Telson is single; never count it as the twentieth paired appendage.","டெல்சன் ஒற்றை அமைப்பு; இதை இருபதாவது இணைக்காலாக எண்ணக்கூடாது."))
    )

    val stages=listOf(
        PenaeusLarvalStage("egg",t("Egg","முட்டை"),
            t("Fertilized eggs are released into marine water.","கருவுற்ற முட்டைகள் கடல்நீரில் வெளியிடப்படுகின்றன."),
            t("External marine spawning; not permanent maternal abdominal egg-brooding.","கடல்நீரில் முட்டையிடுதல்; பெண்ணின் வயிற்றில் நிரந்தர முட்டைச் சுமத்தல் இல்லை.")),
        PenaeusLarvalStage("nauplius",t("Nauplius","நௌப்ளியஸ்"),
            t("Small swimming larva with three functional pairs of appendages; yolk-fed.","மூன்று செயலில் உள்ள இணை உறுப்புகளுடன் நீந்தும் சிறு இளநிலை; கருவுணவைப் பயன்படுத்தும்."),
            t("Hatches early and undergoes successive moults.","விரைவாக வெளிவந்து தொடர்ச்சியாக தோலுரிகிறது.")),
        PenaeusLarvalStage("protozoea",t("Protozoea","புரோட்டோசோயா"),
            t("Cephalothoracic and abdominal differentiation becomes clearer; exogenous feeding begins.","தலைமார்பு, வயிற்றுப்பிரிவு தெளிவாகத் தொடங்குகின்றன; வெளி உணவூட்டம் ஆரம்பிக்கிறது."),
            t("Planktonic and frequently feeding on phytoplankton.","மிதவை நிலையில் வாழ்ந்து தாவர மிதவையுணவை உண்ணும்.")),
        PenaeusLarvalStage("mysis",t("Mysis","மைசிஸ்"),
            t("Thoracic limb differentiation increases and abdominal swimmeret buds emerge.","மார்புக்கால்கள் வேறுபட்டு வயிற்று நீந்துகால் மொட்டுகள் தோன்றுகின்றன."),
            t("Planktonic, active swimming stage preceding postlarva.","பிந்தைய இளநிலைக்கு முன்னுள்ள செயலில் நீந்தும் மிதவை நிலை.")),
        PenaeusLarvalStage("postlarva",t("Postlarva","பிந்தைய இளநிலை"),
            t("Shrimp-like form develops functional pleopods.","இறாலைப் போன்ற வடிவம் ஏற்பட்டு செயல்படும் நீந்துகால்கள் உருவாகும்."),
            t("Frequently uses coastal or estuarine nursery habitats.","பொதுவாக கடற்கரை அல்லது கழிமுக வளரிடங்களைப் பயன்படுத்தும்.")),
        PenaeusLarvalStage("juvenile",t("Juvenile","இளம் இறால்"),
            t("Young shrimp resembles an adult but remains sexually immature.","இளம் இறால் முதிர்ந்த வடிவத்தை ஒத்திருக்கும்; இனப்பெருக்க முதிர்ச்சி அடையவில்லை."),
            t("Feeds and grows by successive moults.","உணவூட்டமும் தொடர் தோலுரிதலும் மூலம் வளரும்.")),
        PenaeusLarvalStage("adult",t("Adult","முதிர்ந்த இறால்"),
            t("Sexually mature male and female show distinct reproductive structures.","இனப்பெருக்க முதிர்ச்சியடைந்த ஆண், பெண் தனித்த அமைப்புகளைக் கொண்டவை."),
            t("Many penaeids return to deeper marine waters to reproduce.","பல பெனேயிட்கள் இனப்பெருக்கத்திற்காக ஆழமான கடற்பகுதிகளுக்குச் செல்கின்றன."))
    )

    init {
        require(sections.size == 12 && sections.map { it.id }.distinct().size == 12)
        require(appendages.map { it.numberOfPairs }.sum() == 19)
        require(appendages.size == 5 && appendages.map { it.id }.distinct().size == 5)
        require(stages.map { it.id } == listOf("egg","nauplius","protozoea","mysis","postlarva","juvenile","adult"))
        require(sections.all { it.heading.english.isNotBlank() && it.heading.tamil.isNotBlank() })
    }
}
