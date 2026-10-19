package com.gasczoology.invertebratelab.data

/**
 * Unit II undergraduate textbook DRAFT: Porifera and Cnidaria/Coelenterata.
 * All prose is English-Tamil, the A5 corpus is untouched, sources are supplied
 * for review, and scientific/translation/illustration approval is NOT claimed.
 * Sycon and Obelia drawings remain illustrative, not measured micrographs.
 */
object UnitIILessons {
    const val reviewStatus = "DRAFT_UNVERIFIED"
    private const val SPONGE = "https://ycmou.ac.in/wp-content/uploads/custom-assets/ebooks/V100_ZGY101_Animal%20Diversity_org.pdf"
    private const val CANALS = "https://www.wbnsou.ac.in/online_services/SLM_UGC_DEB/UG/SOSCI/HZO/1st%20Year/GE-ZO-11.pdf"
    private const val CNIDARIA = "https://openstax.org/books/biology-2e/pages/28-2-phylum-cnidaria"
    private const val REEFS = "https://www.noaa.gov/education/resource-collections/marine-life/coral-reef-ecosystems"
    private const val BLEACH = "https://oceanservice.noaa.gov/facts/coral_bleach.html"
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    private fun section(id: String, en: String, ta: String,
        en1: String, ta1: String, en2: String, ta2: String, source: String) =
        LessonSection(id, b(en,ta),listOf(b(en1,ta1),b(en2,ta2)),listOf(source))
    private fun lesson(id: String, en: String, ta: String, vararg sections: LessonSection) =
        NativeBilingualLesson(id,b(en,ta),sections.toList(),AcademicWorkStatus.DRAFT_UNVERIFIED)

    /** Explicit sequence; prosopyle and apopyle are NOT interchangeable. */
    val syconWaterRoute = listOf("ostia","incurrent-canal","prosopyle",
        "radial-canal","apopyle","spongocoel","osculum")
    data class TeachingStage(val id: String, val name: BilingualText, val explanation: BilingualText)
    val syconStages = listOf(
        TeachingStage("ostia",b("01 · Dermal ostia","01 · புறத்துளைகள்"),
            b("Seawater enters the small openings in the external body wall. These are not the osculum.",
                "கடல்நீர் உடற்சுவரிலுள்ள சிறிய புறத்துளைகளுக்குள் நுழைகிறது. இவை ஆஸ்குலம் அல்ல.")),
        TeachingStage("incurrent-canal",b("02 · Incurrent canal","02 · உட்செல்லும் கால்வாய்"),
            b("Water follows an incurrent canal between the folded walls; these canals are not lined by choanocytes.",
                "மடிந்த உடற்சுவருக்கிடையே நீர் உட்செல்லும் கால்வாயில் செல்கிறது; இதில் காலர் செல்களின் உள்வரிசை இல்லை.")),
        TeachingStage("prosopyle",b("03 · Prosopyle","03 · புரோசோபைல்"),
            b("A prosopyle connects an incurrent canal to a choanocyte-lined radial canal.",
                "புரோசோபைல் என்பது உட்செல்லும் கால்வாயையும் காலர் செல்கள் கொண்ட ஆரக் கால்வாயையும் இணைக்கும் நுண்துளை.")),
        TeachingStage("radial-canal",b("04 · Radial canal","04 · ஆரக் கால்வாய்"),
            b("Choanocyte flagella help drive water and their collars capture suspended particles in the radial canals.",
                "ஆரக் கால்வாயின் காலர் செல்களுடைய கசையிழைகள் நீரோட்டத்திற்கு உதவுகின்றன; காலர்கள் உணவுத்துகள்களைப் பிடிக்கின்றன.")),
        TeachingStage("apopyle",b("05 · Apopyle","05 · அப்போபைல்"),
            b("Water passes from the radial canal into the spongocoel through an apopyle; do not confuse it with a prosopyle.",
                "ஆரக் கால்வாயிலிருந்து நீர் அப்போபைல் வழியாக ஸ்பாஞ்சோசீலுக்குள் செல்கிறது; இதைப் புரோசோபைலுடன் குழப்ப வேண்டாம்.")),
        TeachingStage("spongocoel",b("06 · Spongocoel","06 · ஸ்பாஞ்சோசீல்"),
            b("The central spongocoel collects the outgoing current. In Sycon its surface is not the main choanocyte-lined feeding surface.",
                "நடுவிலுள்ள ஸ்பாஞ்சோசீல் வெளியேறும் நீரைச் சேகரிக்கிறது. சைக்கானில் அதன் சுவரே முக்கிய காலர் செல் உணவுப்பரப்பு அல்ல.")),
        TeachingStage("osculum",b("07 · Osculum and exit","07 · ஆஸ்குலம் வழி வெளியேற்றம்"),
            b("Water leaves through the terminal osculum. The highlighted route is a schematic, not measured flow speed.",
                "நீர் மேல்முனை ஆஸ்குலம் வழியாக வெளியேறுகிறது. காட்டப்படும் வழி விளக்கப்படம்; அளவிட்ட நீரோட்ட வேகம் அல்ல."))
    )
    val obeliaStages = listOf(
        TeachingStage("colony",b("01 · Colonial hydroid","01 · கூட்டுவாழ் ஹைட்ராய்டு"),
            b("A fixed, branching colony shares the living coenosarc and protective perisarc. Feeding and reproductive zooids differ.",
                "நிலைத்த கிளைகொண்ட கூட்டில் உயிருள்ள இணைத்திசு கோயனோசார்க்கும் பாதுகாப்பு பெரிசார்க்கும் உள்ளன; உணவுப் பாலிப்புகளும் இனப்பெருக்கப் பாலிப்புகளும் வேறுபடுகின்றன.")),
        TeachingStage("hydranth",b("02 · Feeding hydranth","02 · உணவுப் ஹைட்ராந்த்"),
            b("A hydranth has a mouth and tentacles carrying cnidocytes for prey capture.",
                "ஹைட்ராந்தில் வாயும் இரையைப் பிடிக்க நிமடோசைட்கள் கொண்ட உணர்கொம்புகளும் உள்ளன.")),
        TeachingStage("gonangium",b("03 · Reproductive gonangium","03 · இனப்பெருக்க கோனாஞ்சியம்"),
            b("The gonangium bears medusa buds; the gonotheca surrounds the developing reproductive structures.",
                "கோனாஞ்சியத்தில் மெடூசா மொட்டுகள் உருவாகின்றன; வளர்கின்ற இனப்பெருக்க அமைப்புகளை கோனோதீக்கா சூழ்கிறது.")),
        TeachingStage("medusa",b("04 · Free medusa","04 · நீந்தும் மெடூசா"),
            b("Released medusae are free-swimming sexual stages and can form gametes; their outline is simplified here.",
                "வெளியேறும் மெடூசாக்கள் நீந்தும் பாலினப் பருவங்கள்; அவை இனச்செல்களை உருவாக்கும். இங்குள்ள வடிவம் எளிமைப்படுத்தப்பட்டது.")),
        TeachingStage("planula",b("05 · Planula larva","05 · பிளானுலா இளவுயிரி"),
            b("After fertilization, development produces a ciliated planula that swims before settlement.",
                "கருவுறுதலுக்குப் பின் குறுஇழை கொண்ட பிளானுலா இளவுயிரி தோன்றி, நிலைபெறுவதற்கு முன் நீந்துகிறது.")),
        TeachingStage("settlement",b("06 · Settlement and new colony","06 · நிலைபெறுதலும் புதிய கூட்டும்"),
            b("A planula settles and metamorphoses to found a new polyp colony. The sequence does not specify measured durations.",
                "பிளானுலா தரையில் நிலைபெற்று உருமாறி புதிய பாலிப் கூட்டை உருவாக்குகிறது. கால அளவுகள் இங்கு அளவிடப்படவில்லை."))
    )
    val chapters: Map<String, NativeBilingualLesson> = linkedMapOf(
        "u2-porifera" to lesson("u2-porifera","Porifera · classification and body plan","போரிஃபெரா · வகைப்பாடும் உடலமைப்பும்",
            section("fundamental-plan","Cellular organization","செல்மட்ட உடலமைப்பு",
                "Sponges are aquatic, predominantly marine, sessile metazoans with pores, a water-canal system and intracellular digestion. They lack the true tissue and organ organization seen in most other animals.",
                "துளைகளும் நீர்க் கால்வாய் அமைப்பும் கொண்ட கடல்வாழ் நிலைத்த பலசெல் விலங்குகளே பெரும்பாலான கடற்பஞ்சுகள். உணவு செல்களுக்குள் செரிக்கப்படுகிறது; பெரும்பாலான பிற விலங்குகளைப் போன்ற உண்மையான திசு, உறுப்பு அமைப்பு இவற்றில் இல்லை.",
                "Pinacocytes cover surfaces, choanocytes generate filtration currents, and amoeboid cells occur in the mesohyl. Mineral spicules or spongin support the body; their composition varies among lineages.",
                "பினாகோசைட்டுகள் மேற்பரப்பை மூடுகின்றன; கோனோசைட்டுகள் வடிகட்டும் நீரோட்டத்தை உருவாக்குகின்றன; மீசோஹைலில் அமீப வடிவச் செல்கள் காணப்படும். கனிம ஊசிகள் அல்லது ஸ்பாஞ்சின் உடலைத் தாங்குகின்றன; அவற்றின் அமைப்பு குழுவிற்கு ஏற்ப மாறும்.",SPONGE),
            section("living-classes","Major modern classes and diagnostic evidence","முக்கிய நவீன வகுப்புகளும் அடையாளக் குறிப்புகளும்",
                "Calcarea commonly possess calcium-carbonate spicules; Hexactinellida characteristically have siliceous six-rayed spicules; Demospongiae include most living species and often have siliceous spicules and/or spongin. Homoscleromorpha is also recognized as a separate class in contemporary classifications.",
                "கால்கேரியாவில் பெரும்பாலும் கால்சியம் கார்பனேட் ஊசிகள்; ஹெக்சாக்டினெல்லிடாவில் ஆறு கதிர் சிலிக்கா ஊசிகள்; டீமோஸ்பாஞ்ஜியாவில் சிலிக்கா ஊசிகள் அல்லது ஸ்பாஞ்சின் காணப்படும். நவீன வகைப்பாட்டில் ஹோமோஸ்க்ளீரோமோர்ஃபா தனி வகுப்பாகவும் கருதப்படுகிறது.",
                "Class-level identification needs spicule type, body organization and other evidence. The asconoid, syconoid and leuconoid arrangements are water-system designs, not taxonomic classes.",
                "வகுப்பை அறிய ஊசிகளின் தன்மை, உடலமைப்பு முதலிய ஆதாரங்கள் அவசியம். அஸ்கனாய்டு, சைக்கனாய்டு, லியூகனாய்டு ஆகியவை நீர்க் கால்வாய் அமைப்பு வகைகள்; வகைப்பாட்டு வகுப்புகள் அல்ல.",CANALS)),
        "u2-sycon" to lesson("u2-sycon","Sycon (Scypha) · illustrated type study","சைக்கான் (ஸ்கைஃபா) · படவிளக்க இன ஆய்வு",
            section("identity","Classification, habit and external form","வகைப்பாடு, வாழ்க்கைமுறை, வெளிவடிவம்",
                "Sycon is a small marine calcareous sponge studied as a syconoid body plan (Porifera, Calcarea). The tubular or vase-shaped body attaches to a substratum; an apical osculum may be surrounded by calcareous spicules.",
                "சைக்கான் என்பது போரிஃபெரா தொகுதியின் கால்கேரியா வகுப்பைச் சேர்ந்த சிறிய கடற்பஞ்சு. குழாய் அல்லது குவளை வடிவ உடல் அடித்தளத்தில் ஒட்டியிருக்கும்; மேல்முனை ஆஸ்குலத்தைச் சுற்றி சுண்ணாம்பு ஊசிகள் காணப்படலாம்.",
                "Ostia are numerous small entry openings in the external body wall, while the osculum is the main outflow opening. A whole-organism drawing cannot directly reveal every internal aperture.",
                "உடலின் வெளிச்சுவரில் உள்ள பல சிறு துளைகள் ஆஸ்டியா; ஆஸ்குலம் நீர் வெளியேறும் முக்கியத் திறப்பு. முழு உடல் வரைபடத்தால் ஒவ்வொரு உள்துளையையும் நேரடியாகக் காண முடியாது.",SPONGE),
            section("body-wall","Three-dimensional folded body wall","மடிந்த உடற்சுவரின் முப்பரிமாண அமைப்பு",
                "The syconoid wall contains alternating incurrent canals and choanocyte-lined radial canals. The incurrent canals are not lined by choanocytes; the radial canals house the principal filtering collar cells.",
                "சைக்கனாய்டு உடற்சுவரில் உட்செல்லும் கால்வாய்களும் காலர் செல்கள் வரிசையாக அமைந்த ஆரக் கால்வாய்களும் மாறிமாறி உள்ளன. உட்செல்லும் கால்வாயில் காலர் செல்கள் இல்லை; ஆரக் கால்வாயே முக்கிய உணவுவடிகட்டும் பகுதி.",
                "Mesohyl contains cells and calcareous spicules that support the folded wall. The central spongocoel is an outflow cavity, not a digestive stomach.",
                "மீசோஹைலில் உள்ள செல்களும் சுண்ணாம்பு ஊசிகளும் மடிந்த சுவரைத் தாங்குகின்றன. நடு ஸ்பாஞ்சோசீல் நீர் வெளியேறும் குழி; செரிமான வயிறு அல்ல.",CANALS),
            section("water-route","Syconoid canal-system sequence","சைக்கனாய்டு நீர்க் கால்வாய் வழித்தடம்",
                "The complete directed route is exterior → ostia → incurrent canals → prosopyles → radial canals → apopyles → spongocoel → osculum → exterior. Prosopyles are entry connections to the radial canals; apopyles open from them to the central cavity.",
                "முழு வழித்தடம்: வெளியிலுள்ள நீர் → ஆஸ்டியா → உட்செல்லும் கால்வாய் → புரோசோபைல் → ஆரக் கால்வாய் → அப்போபைல் → ஸ்பாஞ்சோசீல் → ஆஸ்குலம் → வெளிச்சூழல். புரோசோபைல் ஆரக் கால்வாயில் நுழைவதற்கும் அப்போபைல் அதிலிருந்து வெளியேறுவதற்கும் உரிய திறப்புகள்.",
                "Flagellar beating by choanocytes maintains a continuous water current through the canal system. Water passes from incurrent canals through prosopyles into radial canals, where suspended food particles are captured, and then leaves through apopyles, the spongocoel and osculum. The direction is biologically important; textbook arrows do not indicate measured water speed.",
                "கோனோசைட்டுகளின் கசையிழை அசைவால் கால்வாய் அமைப்பில் தொடர்ச்சியான நீரோட்டம் ஏற்படுகிறது. உட்செல்லும் கால்வாய்களிலிருந்து நீர் புரோசோபைல்கள் வழியாக ஆரக் கால்வாய்களுக்குச் செல்கிறது; அங்கு மிதக்கும் உணவுத்துகள்கள் பிடிக்கப்படுகின்றன. பின்னர் நீர் அப்போபைல்கள், ஸ்பாஞ்சோசீல், ஆஸ்குலம் வழியாக வெளியேறுகிறது. நீரோட்டத்தின் திசை உயிரியல் ரீதியில் முக்கியமானது; பாடநூல் அம்புகள் அளவிடப்பட்ட வேகத்தைக் குறிக்கவில்லை.",SPONGE),
            section("nutrition","Choanocytes, filtration and intracellular digestion","காலர் செல்கள், வடிகட்டல், செல்செரிமானம்",
                "Choanocyte flagella help generate water currents, while microvillar collars intercept suspended food particles. Captured food is digested intracellularly and nutrients are distributed among cells.",
                "கோனோசைட் கசையிழைகள் நீரோட்டத்திற்கு உதவுகின்றன; நுண்விரல் வளையங்கள் நீரில் மிதக்கும் உணவுத்துகள்களைப் பிடிக்கின்றன. உணவு செல்களுக்குள் செரிக்கப்பட்டு ஊட்டச்சத்து பிற செல்களுக்குக் கடத்தப்படுகிறது.",
                "Gas exchange and metabolic waste removal occur through cell–water exchange, aided by the current; Sycon has neither a through-gut nor blood circulation. Water flowing into the sponge is not the same as food entering a digestive cavity.",
                "செல்–நீர் பரிமாற்றமும் நீரோட்டமும் வாயுப் பரிமாற்றத்துக்கும் கழிவுநீக்கத்துக்கும் உதவுகின்றன; சைக்கானுக்கு தொடர்ச்சியான குடலோ இரத்த ஓட்டமோ இல்லை. உடலுக்குள் நுழையும் நீரை உணவு செரியும் குடலுடன் குழப்ப வேண்டாம்.",SPONGE),
            section("reproduction","Asexual and sexual reproduction","பாலிலா மற்றும் பாலின இனப்பெருக்கம்",
                "Budding and regeneration can contribute to asexual propagation in sponges. Sexual reproduction involves gametes and development through free-swimming ciliated larval stages before settlement; specific larval anatomy varies among sponge groups.",
                "மொட்டுவிடுதலும் மறுஉருவாக்கமும் கடற்பஞ்சுகளில் பாலிலா இனப்பெருக்கத்திற்கு உதவலாம். பாலின இனப்பெருக்கத்தில் இனச்செல்கள் இணைந்து, குறுஇழை கொண்ட நீந்தும் இளவுயிர் பருவம் தோன்றி பின்னர் நிலைபெறும்; இளவுயிரின் அமைப்பு குழுவிற்கு ஏற்ப மாறுபடும்.",
                "In calcareous sponges such as Sycon, an amphiblastula-type larva is classically described. Embryological details and species identity must be checked against suitable source material rather than generalized to every sponge.",
                "சைக்கான் போன்ற சுண்ணாம்புக் கடற்பஞ்சுகளில் ஆம்பிபிளாஸ்டுலா வகை இளவுயிரி பாரம்பரியமாக விவரிக்கப்படுகிறது. கருவியல் விவரங்களையும் இன அடையாளத்தையும் தகுந்த ஆதாரங்களோடு சரிபார்க்க வேண்டும்; எல்லாக் கடற்பஞ்சுகளுக்கும் பொதுமைப்படுத்தக்கூடாது.",SPONGE),
            section("practical-evidence","Understanding external and internal sponge organization","கடற்பஞ்சின் புற மற்றும் அக அமைப்பைப் புரிந்துகொள்ளுதல்",
                "Sycon has a vase-shaped external body, but its internal wall is folded into incurrent and choanocyte-lined radial canals. Dermal ostia admit water, prosopyles connect to radial canals, and apopyles discharge into the spongocoel; the osculum is the final exit.",
                "சைக்கானின் வெளிப்புற உடல் குவளை வடிவமுடையது; அதன் உட்சுவர் மடிந்து உட்செல்லும் கால்வாய்களையும் கோனோசைட்டுகள் வரிசையாக உள்ள ஆரக் கால்வாய்களையும் உருவாக்குகிறது. ஆஸ்டியா வழியாக நீர் நுழைந்து, புரோசோபைல்கள் வழியாக ஆரக் கால்வாய்களை அடைந்து, அப்போபைல்கள் வழியாக ஸ்பாஞ்சோசீலுக்குச் செல்கிறது; ஆஸ்குலம் இறுதி வெளியேற்றத் திறப்பாகும்.",
                "The folded wall increases the area occupied by feeding choanocytes without turning the spongocoel into a stomach. Cross-sectional and longitudinal views represent different planes of the same three-dimensional canal system, not separate kinds of sponge.",
                "மடிந்த உடற்சுவர் உணவுத் துகள்களைப் பிடிக்கும் கோனோசைட்டுகளுக்கான பரப்பை அதிகரிக்கிறது; இதனால் ஸ்பாஞ்சோசீல் செரிமான வயிறாக மாறுவதில்லை. குறுக்குவெட்டும் நீள்வெட்டும் ஒரே முப்பரிமாணக் கால்வாய் அமைப்பின் வெவ்வேறு தோற்றங்களாகும்.",CANALS)),
        "u2-canals" to lesson("u2-canals","Canal systems in Porifera","போரிஃபெராவில் நீர்க் கால்வாய் அமைப்புகள்",
            section("three-plans","Asconoid, syconoid and leuconoid","அஸ்கனாய்டு, சைக்கனாய்டு, லியூகனாய்டு",
                "Asconoid sponges possess a simple canal arrangement with choanocytes lining the central cavity. Syconoid sponges add folded choanocyte-lined radial canals. Leuconoid systems organize choanocytes within numerous chambers and route water through more branched passages.",
                "அஸ்கனாய்டு கடற்பஞ்சுகளில் எளிய கால்வாய் அமைப்பும் நடுக் குழியைச் சுற்றிக் காலர் செல்களும் காணப்படும். சைக்கனாய்டில் மடிந்த ஆரக் கால்வாய்களில் காலர் செல்கள் அமைந்துள்ளன. லியூகனாய்டில் பல சிற்றறைகளில் காலர் செல்களும் கிளைத்த நீர்வழிகளும் காணப்படும்.",
                "These plans differ in topology and filtration area; they are not an obligatory evolutionary ladder for every living lineage. Species and developmental variation should be acknowledged.",
                "இவ்வகைகள் நீர்வழி அமைப்பு மற்றும் வடிகட்டும் பரப்பளவில் வேறுபடுகின்றன; ஒவ்வொரு உயிரினக் குழுவும் கடக்க வேண்டிய கட்டாயப் பரிணாமப் படிகளாகக் கருத முடியாது.",CANALS),
            section("flow-evidence","How to interpret a flow diagram","நீரோட்டப் படத்தைப் படிக்கும் முறை",
                "In Sycon the directed sequence from ostia through incurrent canals, prosopyles, radial canals and apopyles terminates in the spongocoel and osculum. An illustration must not reverse prosopyle and apopyle or put choanocytes throughout the spongocoel.",
                "சைக்கானில் ஆஸ்டியா தொடங்கி உட்செல்லும் கால்வாய், புரோசோபைல், ஆரக் கால்வாய், அப்போபைல் வழியாக நீர் ஸ்பாஞ்சோசீலுக்கும் ஆஸ்குலத்துக்கும் செல்கிறது. புரோசோபைலையும் அப்போபைலையும் மாற்றிச் சித்தரிக்கக்கூடாது; ஸ்பாஞ்சோசீல் முழுவதும் காலர் செல்கள் உள்ளன என்றும் காட்டக்கூடாது.",
                "Water is driven mainly by choanocyte flagellar activity. The explanatory stage selector shows anatomical connectivity, not simultaneous valves opening or a measured pulse cycle.",
                "நீரோட்டத்திற்கு முக்கியக் காரணம் கோனோசைட்டுகளின் கசையிழை இயக்கம். படிநிலைத் தேர்வு அமைப்பியல் இணைப்பைக் காட்டுகிறது; ஒரே நேரத்தில் வால்வுகள் திறப்பதையோ அளவிட்ட துடிப்புச் சுழற்சியையோ அல்ல.",SPONGE)),
        "u2-reproduction" to lesson("u2-reproduction","Reproduction and development in sponges","கடற்பஞ்சுகளில் இனப்பெருக்கமும் வளர்ச்சியும்",
            section("asexual","Budding, fragments and restoration","மொட்டுவிடுதல், துண்டுகள், மறுஉருவாக்கம்",
                "Some sponges reproduce asexually by external budding or fragmentation; retained fragments may regenerate. Freshwater demosponges can produce resistant gemmules, but gemmules must not be presented as the typical Sycon reproductive structure.",
                "சில கடற்பஞ்சுகள் வெளிமொட்டுகள் அல்லது உடல் துண்டுகள் மூலம் பாலிலா இனப்பெருக்கம் செய்கின்றன; துண்டுகள் மறுஉருவாக்கமடையலாம். நன்னீர் டீமோஸ்பாஞ்ஜியாவில் பாதுகாப்பு ஜெம்மியூல்கள் உண்டாகலாம்; அவற்றை சைக்கானின் வழக்கமான இனப்பெருக்க அமைப்பாகக் காட்டக்கூடாது.",
                "The cellular reorganization enabling regeneration differs from a developmental claim that every separated cell can become a complete sponge. Different species have different capacities.",
                "மறுஉருவாக்கத்தில் செல்களின் மறுசீரமைப்பு நடைபெறுகிறது. பிரிந்த ஒவ்வொரு செல்லும் முழுக் கடற்பஞ்சாக மாறும் என்ற தவறான பொதுக்கூற்றைத் தவிர்க்க வேண்டும்; இனங்களுக்கு ஏற்ப திறன் மாறும்.",SPONGE),
            section("sexual","Gametes, larva and settlement","இனச்செல்கள், இளவுயிரி, நிலைபெறுதல்",
                "Sperm and eggs form in sponge tissues, and many species retain embryos during early development. Ciliated larvae disperse before attachment; morphology and fertilization route vary among groups.",
                "கடற்பஞ்சுத் திசுக்களில் விந்தணுவும் முட்டையும் உருவாகின்றன. பல இனங்களில் தொடக்கக் கரு வளர்ச்சி உடலுக்குள் நடைபெறும். குறுஇழை இளவுயிரிகள் பரவி பின்னர் ஒட்டுகின்றன; உருவமும் கருவுறும் முறையும் குழுவிற்கு ஏற்ப மாறும்.",
                "For Sycon the amphiblastula is a useful traditional type-study example. A teaching sequence must distinguish embryonic cleavage, larval swimming, settlement and young sponge from a direct adult-to-adult transformation.",
                "சைக்கானின் ஆம்பிபிளாஸ்டுலா பாரம்பரிய இன ஆய்வில் குறிப்பிடத்தக்கது. கருப் பிளவு, இளவுயிர் நீந்தல், நிலைபெறுதல், இளம் கடற்பஞ்சு வளர்ச்சி ஆகியவற்றை வேறுபடுத்திக் காட்ட வேண்டும்.",SPONGE)),
        "u2-cnidaria" to lesson("u2-cnidaria","Cnidaria · classification and body plans","நிடேரியா · வகைப்பாடும் உடல்வடிவங்களும்",
            section("diagnostic-plan","Cnidocytes and tissue-grade organization","நிமடோசைட்டுகள் மற்றும் திசுமட்ட அமைப்பு",
                "Cnidarians are predominantly aquatic animals with cnidocytes, usually bearing nematocysts used for capture or defense. They have tissue-level organization, a gastrovascular cavity and typically radial or modified radial symmetry.",
                "நிடேரியன்கள் பெரும்பாலும் நீர்வாழ் விலங்குகள்; அவற்றின் நிமடோசைட்டுகளில் இரையைப் பிடிக்கவும் பாதுகாப்பிற்கும் உதவும் நிமடோசிஸ்ட்கள் உள்ளன. திசுமட்ட அமைப்பு, இரைப்பை-நாளக் குழி, பொதுவாக ஆரச்சமச்சீர் ஆகியவை காணப்படும்.",
                "The polyp is typically attached and columnar; the medusa is commonly free-swimming and bell-shaped. Not all cnidarians have both stages and neither stage is a true sponge.",
                "பாலிப் பொதுவாக ஒட்டிய தூண் வடிவம்; மெடூசா பல நேரங்களில் நீந்தும் மணிவடிவம். எல்லா நிடேரியன்களிலும் இரு பருவங்களும் இல்லை; இரண்டுமே கடற்பஞ்சுகள் அல்ல.",CNIDARIA),
            section("major-classes","Hydrozoa, Scyphozoa, Cubozoa, Anthozoa and Staurozoa","ஹைட்ரோசோவா, ஸ்கைஃபோசோவா, கியூபோசோவா, அந்தோசோவா, ஸ்டாரோசோவா",
                "Hydrozoa includes colonial Obelia and many other hydroids; Scyphozoa includes typical true jellyfish; Cubozoa includes box jellyfish; Anthozoa includes corals and sea anemones. Staurozoa, the stalked jellyfish, is recognized as a distinct class in many modern treatments.",
                "ஹைட்ரோசோவாவில் கூட்டுவாழ் ஒபீலியா உள்ளிட்ட ஹைட்ராய்டுகள்; ஸ்கைஃபோசோவாவில் வழக்கமான ஜெல்லிமீன்கள்; கியூபோசோவாவில் பெட்டி ஜெல்லிமீன்கள்; அந்தோசோவாவில் பவளங்களும் கடல் அனிமோன்களும் அடங்கும். நவீன வகைப்பாட்டில் ஸ்டாரோசோவா தனிவகுப்பாகவும் கொள்ளப்படுகிறது.",
                "Older syllabi may use Coelenterata more broadly. Explain the historical name without placing ctenophores inside modern Cnidaria; class boundaries and examples should be supported with a current reference.",
                "பழைய பாடத்திட்டங்களில் சீலென்டெராட்டா என்ற பரந்த பெயர் இடம்பெறலாம். நவீன நிடேரியாவிற்குள் டீனோஃபோர்களைச் சேர்க்காமல் அதன் வரலாற்றுப் பயன்பாட்டை விளக்க வேண்டும்.",CNIDARIA)),
        "u2-obelia" to lesson("u2-obelia","Obelia · colonial type study and life cycle","ஒபீலியா · கூட்டுவாழ் இன ஆய்வும் வாழ்க்கைச் சுழற்சியும்",
            section("classification","Hydrozoan identity and colonial habit","ஹைட்ரோசோவா அடையாளமும் கூட்டுவாழ்வும்",
                "Obelia is a marine hydrozoan with a branching, attached polyp colony. The living coenosarc links zooids inside a protective outer perisarc; this is one connected colonial organism rather than a loose assemblage.",
                "ஒபீலியா கடல்வாழ் ஹைட்ரோசோவா; கிளைத்த நிலைத்த பாலிப் கூட்டு உடையது. உயிருள்ள கோயனோசார்க் பல பாலிப்புகளை இணைக்கிறது; வெளியே பெரிசார்க் பாதுகாப்பளிக்கிறது. இது தனித்தனி உயிரிகளின் தளர்ந்த தொகுப்பு அல்ல.",
                "A stolon or hydrorhiza anchors the colony and a hydrocaulus carries upright branches. Feeding and budding zooids are specialized members of the colony.",
                "ஹைட்ரோரிசா அல்லது நிலக்கிளை கூட்டை அடித்தளத்தில் பதிக்கிறது; ஹைட்ரோகாலஸ் மேல்நோக்கிய கிளைகளைத் தாங்குகிறது. உணவுப் பாலிப்புகளும் மொட்டுவிடும் பாலிப்புகளும் சிறப்புப் பணிகளைக் கொண்டவை.",CNIDARIA),
            section("feeding-polyp","Hydranth, mouth, tentacles and hydrotheca","ஹைட்ராந்த், வாய், உணர்கொம்புகள், ஹைட்ரோதீக்கா",
                "The hydranth or gastrozooid captures prey using tentacles equipped with cnidocytes and conveys food through the mouth to the gastrovascular cavity. The surrounding hydrotheca is a protective perisarc cup, not the feeding cavity.",
                "ஹைட்ராந்த் எனும் உணவுப் பாலிப் நிமடோசைட்கள் கொண்ட உணர்கொம்புகளால் இரையைப் பிடித்து வாய்வழியாக இரைப்பை-நாளக் குழிக்குள் செலுத்துகிறது. அதைச் சுற்றிய ஹைட்ரோதீக்கா பாதுகாப்புக் கோப்பை; உணவுக்குழி அல்ல.",
                "The gastrovascular spaces of the zooids are connected through the living coenosarc, allowing digested nutrients to be distributed within the colony. A hydranth captures prey using cnidocyte-bearing tentacles and digests it in the gastrovascular cavity; it has no separate complete intestine or anus.",
                "கூட்டிலுள்ள பாலிப்புகளின் இரைப்பை–நாளக் குழிகள் உயிருள்ள கோயனோசார்க் வழியாக இணைந்துள்ளதால் செரிக்கப்பட்ட ஊட்டச்சத்துகள் கூட்டின் பகுதிகளுக்குப் பகிரப்படுகின்றன. நிமடோசைட்டுகள் கொண்ட உணர்கொம்புகளால் ஹைட்ராந்த் இரையைப் பிடித்து இரைப்பை–நாளக் குழியில் செரிக்கிறது. இதற்குத் தனியான முழுமையான குடலோ ஆசனவாயோ இல்லை.",CNIDARIA),
            section("budding-polyp","Gonangium and medusa buds","கோனாஞ்சியமும் மெடூசா மொட்டுகளும்",
                "A reproductive gonangium contains a blastostyle bearing medusa buds within a protective gonotheca. Gonangium and hydranth are different zooid forms and should never be given identical functions in the atlas.",
                "இனப்பெருக்கக் கோனாஞ்சியத்தில் கோனோதீக்காவால் பாதுகாக்கப்படும் பிளாஸ்டோஸ்டைலில் மெடூசா மொட்டுகள் உருவாகின்றன. கோனாஞ்சியமும் ஹைட்ராந்தும் தனித்த பணியுடைய பாலிப்புகள்.",
                "Released buds develop as small medusae. Budding is asexual production by the colony and must be distinguished from sexual gamete production by medusae.",
                "மொட்டுகள் பிரிந்து சிறிய மெடூசாக்களாக உருவாகின்றன. கூட்டில் மொட்டுவிடுதல் பாலிலா செயல்; மெடூசாவின் இனச்செல் உருவாக்கம் பாலினச் செயல்.",CNIDARIA),
            section("medusa","Umbrella, velum and reproductive organs","குடை, வேலம், இனப்பெருக்க உறுப்புகள்",
                "An Obelia medusa is a small, free-swimming hydrozoan form with umbrella, marginal tentacles, a velum and radial canals. Gametes form in gonads associated with the radial-canal regions in traditional descriptions.",
                "ஒபீலியா மெடூசா சிறிய நீந்தும் ஹைட்ரோசோவா வடிவம்; குடை, விளிம்பு உணர்கொம்புகள், வேலம், ஆரக் கால்வாய்கள் உள்ளன. பாரம்பரிய விளக்கங்களில் ஆரக் கால்வாய் பகுதிகளுடன் தொடர்புடைய இனப்பெருக்கத் திசுக்களில் இனச்செல்கள் உருவாகின்றன.",
                "The medusa is not a detached polyp with an unchanged body plan. Its morphology and swimming behavior must be treated as a distinct developmental stage.",
                "மெடூசா பிரிந்த பாலிப் அதே வடிவத்தில் இருப்பது அல்ல. அதன் உருவமும் நீந்தும் பண்பும் வேறான வளர்ச்சிப் பருவமாகக் கருதப்பட வேண்டும்.",CNIDARIA),
            section("life-history","Gametes, planula, settlement and colony","இனச்செல்கள், பிளானுலா, நிலைபெறுதல், கூட்டு",
                "After sexual reproduction, a fertilized egg develops into a ciliated planula that swims, settles and establishes a new polyp colony. The mature colony subsequently buds medusae, linking asexual colonial and sexual free-swimming phases.",
                "பாலின இனப்பெருக்கத்திற்கு பின் கருவுற்ற முட்டையிலிருந்து குறுஇழையுள்ள பிளானுலா உருவாகி நீந்தி நிலைபெற்று புதிய பாலிப் கூட்டை உருவாக்குகிறது. முதிர்ந்த கூட்டு பின்னர் மெடூசா மொட்டுகளை உருவாக்கி பாலிலா, பாலினப் பருவங்களை இணைக்கிறது.",
                "The interactive sequence is descriptive, not a measured period; developmental details vary among species. A planula is not a miniature medusa or already a feeding hydranth.",
                "ஊடாடும் வரிசை விளக்கத்திற்கானது; அளவிட்ட கால அளவு அல்ல. இனங்களுக்கு ஏற்ப விவரம் மாறும். பிளானுலா சிறு மெடூசாவோ உணவெடுக்கும் ஹைட்ராந்தோ அல்ல.",CNIDARIA),
            section("practical","Polymorphism and division of labour","பலவடிவத்தன்மையும் பணிப்பகிர்வும்",
                "The hydrorhiza anchors the colony, the hydrocaulus supports its branches, and the perisarc protects the living coenosarc. Tentacle-bearing hydranths feed the colony, whereas gonangia produce medusa buds. The released medusae represent a distinct free-swimming reproductive phase.",
                "ஹைட்ரோரிசா கூட்டை அடித்தளத்தில் பதிக்கிறது; ஹைட்ரோகாலஸ் கிளைகளைத் தாங்குகிறது; பெரிசார்க் உயிருள்ள கோயனோசார்க்கைப் பாதுகாக்கிறது. உணர்கொம்புள்ள ஹைட்ராந்துகள் உணவு பெறுகின்றன; கோனாஞ்சியங்கள் மெடூசா மொட்டுகளை உருவாக்குகின்றன. பிரிந்து நீந்தும் மெடூசாக்கள் தனித்த இனப்பெருக்கப் பருவமாகும்.",
                "Polymorphism in Obelia is functional differentiation among connected zooids: feeding hydranths and reproductive gonangia perform complementary roles. Medusae arise from buds within gonangia, not by direct transformation of an existing feeding hydranth.",
                "ஒபீலியாவில் பலவடிவத்தன்மை என்பது இணைந்த பாலிப்புகளின் பணிச் சிறப்பாக்கமாகும்: உணவு பெறும் ஹைட்ராந்துகளும் இனப்பெருக்கக் கோனாஞ்சியங்களும் வேறுபட்ட பணிகளைச் செய்கின்றன. மெடூசாக்கள் கோனாஞ்சியங்களுக்குள் உருவாகும் மொட்டுகளிலிருந்து தோன்றுகின்றன; ஏற்கெனவே உள்ள உணவுப் பாலிப் நேரடியாக மெடூசாவாக மாறுவதில்லை.",CNIDARIA)),
        "u2-corals" to lesson("u2-corals","Corals and coral reefs","பவளங்களும் பவளப்பாறைகளும்",
            section("coral-polyps","Anthozoan coral body plan","அந்தோசோவா பவளப் பாலிப்பின் அமைப்பு",
                "Corals are cnidarian animals, mostly anthozoans, that often grow as colonies of polyps. Reef-building hard corals secrete calcium-carbonate skeletons; not all cnidarians and not all coral species construct tropical reefs.",
                "பவளங்கள் பெரும்பாலும் அந்தோசோவா வகுப்பைச் சேர்ந்த நிடேரியன் விலங்குகள்; பல இனங்களில் பாலிப்புகள் கூட்டாக வளரும். பாறை அமைக்கும் கடினப் பவளங்கள் கால்சியம் கார்பனேட் எலும்புக்கூடு உருவாக்குகின்றன; எல்லாப் பவளங்களும் வெப்பமண்டலப் பாறைகள் கட்டுவதில்லை.",
                "The polyp has a mouth and tentacles with cnidocytes. The polyp's living tissues are distinct from the mineral skeleton and from photosynthetic symbionts inside its cells.",
                "பாலிப்பில் வாயும் நிமடோசைட்டுகள் கொண்ட உணர்கொம்புகளும் உள்ளன. அதன் உயிருள்ள திசுக்கள், கனிம எலும்புக்கூடு மற்றும் செல்லுக்குள்ளான ஒளிச்சேர்க்கை இணைவாழ் உயிரிகள் ஆகியவை வேறானவை.",REEFS),
            section("reef-ecology","Reefs, symbiosis and ecological function","பவளப்பாறைகள், இணைவாழ்வு, சூழலியல்",
                "Many shallow tropical reef-building corals host photosynthetic dinoflagellate symbionts, often called zooxanthellae. Photosynthate supports the animal; the association does not turn a coral polyp into a plant.",
                "பல ஆழமற்ற வெப்பமண்டலப் பாறைப் பவளங்களின் திசுக்களில் ஒளிச்சேர்க்கை செய்யும் டைனோஃபிளாஜெல்லேட் இணைவாழ் உயிரிகள் உள்ளன; இவை பொதுவாக சூசாந்தெல்லே என அழைக்கப்படுகின்றன. அவை வழங்கும் ஊட்டம் பவளத்துக்கு உதவுகிறது; இதனால் பவளம் தாவரமாகாது.",
                "Coral reefs provide habitat for diverse marine life, coastal protection and livelihood support. Deep-water corals may lack photosynthetic symbionts, so sunlight dependence is not universal.",
                "பவளப்பாறைகள் கடல்வாழ் பல உயிரிகளுக்கு வாழிடம், கடற்கரைக்குப் பாதுகாப்பு, மக்களுக்கு வாழ்வாதாரம் வழங்குகின்றன. ஆழ்கடல் பவளங்களில் ஒளிச்சேர்க்கை இணைவாழ் உயிரிகள் இல்லாமல் இருக்கலாம்; ஒளிசார்பு எல்லா பவளங்களுக்கும் பொதுவல்ல.",REEFS),
            section("bleaching","Bleaching is a stress response","வெளிறுதல் என்பது மனஅழுத்தப் பதில்வினை",
                "Heat stress and other disturbances can lead to loss of algal symbionts or pigments, revealing pale tissues and the underlying white skeleton. Bleached coral is stressed but not necessarily immediately dead.",
                "அதிக வெப்பம் அல்லது பிறச் சூழல் பாதிப்பால் இணைவாழ் பாசிகளோ அவற்றின் நிறமிகளோ குறைந்து, பவளத்தின் வெளிறிய திசுக்களும் அடியிலுள்ள வெள்ளை எலும்புக்கூடும் தெரியும். வெளிறிய பவளம் உடனே இறந்துவிட்டது என்று பொருளல்ல.",
                "Prolonged severe bleaching increases mortality risk, while recovery is possible if conditions improve. A classroom plate must distinguish a living bleached polyp from a dead exposed skeleton.",
                "நீண்டகாலக் கடுமையான வெளிறுதல் இறப்பு ஆபத்தை உயர்த்துகிறது; சூழல் மேம்பட்டால் மீட்பு சாத்தியம். உயிருள்ள வெளிறிய பாலிப்பையும் இறந்த பவளத்தின் வெற்று எலும்புக்கூட்டையும் வேறுபடுத்துக.",BLEACH)),
        "u2-coral-economics" to lesson("u2-coral-economics","Ecological and economic importance of corals","பவளங்களின் சூழலியல், பொருளாதார முக்கியத்துவம்",
            section("ecosystem-benefits","Food webs, fisheries and coastlines","உணவுவலை, மீன்வளம், கடற்கரை",
                "Reef habitat sustains nursery, feeding and shelter functions for fish and invertebrates, contributing to local fisheries and coastal livelihoods. Reef structures can dissipate wave energy and reduce erosion.",
                "பவளப்பாறைகள் மீன்களுக்கும் முதுகெலும்பில்லா உயிரிகளுக்கும் வளரிட, உணவிடம், புகலிடம் அளித்து மீன்வளத்துக்கும் கடலோர வாழ்வாதாரத்துக்கும் உதவுகின்றன. பாறைகள் அலை ஆற்றலைக் குறைத்து கரையரிப்பையும் தடுக்க உதவுகின்றன.",
                "Education, tourism and biomedical research also benefit from reefs. Economic values depend on local conditions and should not be presented as one invariant monetary number.",
                "கல்வி, சுற்றுலா, உயிரிமருத்துவ ஆய்வு ஆகியவற்றுக்கும் பவளப்பாறைகள் பயனளிக்கின்றன. பொருளாதார மதிப்பு இடம்தோறும் மாறும்; ஒன்றே எல்லா இடங்களுக்கும் பொருந்தும் பணமதிப்பாகக் காட்டக்கூடாது.",REEFS),
            section("conservation","Threats and responsible management","அச்சுறுத்தல்களும் பொறுப்பான மேலாண்மையும்",
                "Marine heatwaves, acidification, sediment, pollution and destructive fishing can impair coral ecosystems. Climate mitigation and locally effective water-quality and reef management both matter.",
                "கடல்வெப்ப அலைகள், அமிலமாதல், படிவங்கள், மாசுபாடு, அழிவூட்டும் மீன்பிடித்தல் ஆகியவை பவளச் சூழலமைப்புகளைப் பாதிக்கின்றன. காலநிலை மாற்றத் தணிப்பும் நீர்த் தரம் மற்றும் பாறை மேலாண்மையும் அவசியம்.",
                "Collecting live coral or breaking skeletons for souvenirs can damage reefs and may be restricted by law. Observe and document with non-destructive methods.",
                "நேரடி பவளங்களைச் சேகரித்தல், நினைவுப் பொருளுக்காக எலும்புக்கூட்டை உடைத்தல் போன்றவை பாதிப்புகளை ஏற்படுத்தும்; சட்டக் கட்டுப்பாடுகளும் இருக்கலாம். சேதமில்லா முறைகளில் பதிவு செய்து கற்க வேண்டும்.",REEFS)),
        "u2-polymorphism" to lesson("u2-polymorphism","Polymorphism in Hydrozoa","ஹைட்ரோசோவாவில் பலவடிவத்தன்மை",
            section("division-of-labour","Differentiated zooids in a colony","கூட்டில் வேறுபட்ட பாலிப்புகளின் பணிகள்",
                "Hydrozoan colonial polymorphism means interconnected zooids specialized for functions such as feeding, reproduction and in some groups defense. In Obelia the familiar comparison is a feeding hydranth and a reproductive gonangium.",
                "ஹைட்ரோசோவா கூட்டின் பலவடிவத்தன்மை என்பது இணைந்த பாலிப்புகள் உணவெடுப்பு, இனப்பெருக்கம், சில குழுக்களில் பாதுகாப்பு போன்ற வேறுபட்ட பணிகளைச் செய்வது. ஒபீலியாவில் உணவு ஹைட்ராந்தும் இனப்பெருக்க கோனாஞ்சியமும் முக்கிய ஒப்பீடு.",
                "The coenosarc maintains colony continuity, and protective hydrothecae or gonothecae must not be confused with independent living zooids.",
                "கோயனோசார்க் கூட்டு ஒற்றுமையைப் பேணுகிறது; பாதுகாப்பான ஹைட்ரோதீக்கா அல்லது கோனோதீக்காவை தனி உயிருள்ள பாலிப்புகளாகக் குழப்பக்கூடாது.",CNIDARIA),
            section("examples-limits","Compare Obelia with other hydrozoans","ஒபீலியா மற்றும் பிற ஹைட்ரோசோவா ஒப்பீடு",
                "Physalia and certain siphonophores exhibit specialized zooids with more extreme division of labour; their organization must not be copied uncritically into a typical Obelia colony.",
                "பிசாலியா போன்ற சில சைஃபோனோஃபோர்களில் வேலைப்பகிர்வு அதிகமாகச் சிறப்புபெற்ற பாலிப்புகள் உள்ளன. அவற்றின் அமைப்பை ஒபீலியா கூட்டிற்கு அப்படியே பொருத்தக்கூடாது.",
                "Polyp-to-medusa alternation describes life-history organization, while colony polymorphism describes simultaneous specialization among connected zooids. These are related but distinct concepts.",
                "பாலிப்–மெடூசா பருவமாற்றம் வாழ்க்கைச் சுழற்சியை விளக்குகிறது; கூட்டு பலவடிவத்தன்மை ஒரே கூட்டில் இணைந்த பாலிப்புகளின் வேலைப்பிரிவைக் குறிக்கிறது. இவை தொடர்புடைய வேறுபட்ட கருத்துகள்.",CNIDARIA))
    )
    val chapterIds get() = chapters.keys.toList()
    fun forChapter(id: String): NativeBilingualLesson? = chapters[id]
    fun validateDrafts() {
        require(chapterIds == NativeCurriculumManifest.a5ChapterIdsByUnit.getValue(2))
        require(syconWaterRoute == syconStages.map { it.id })
        require(obeliaStages.size == 6)
        for (draft in chapters.values) {
            BilingualLessonContract.validate(draft)
            require(draft.status == AcademicWorkStatus.DRAFT_UNVERIFIED && draft.review == null)
            require(draft.sections.size >= 2)
            require(draft.sections.all { it.paragraphs.size >= 2 && it.scientificSources.all(String::isNotBlank) })
        }
    }
}
