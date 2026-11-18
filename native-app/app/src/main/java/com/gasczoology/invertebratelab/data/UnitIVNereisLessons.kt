package com.gasczoology.invertebratelab.data

/**
 * Native Unit IV Nereis biology: editorial draft distinct from the immutable
 * A5 corpus. The historical syllabus class Polychaeta is not represented
 * as an undisputed modern monophyletic class. Generalised nereidid characters
 * must not be asserted as species-specific measurements for an unnamed Nereis.
 *
 * English anatomical nomenclature is drawn from the user's reference figures
 * and contemporary nereidid morphology. Tamil equivalents are provisional
 * until the actual college bilingual practical manual is supplied and checked.
 *
 * Morphology: https://academic.oup.com/zoolinnean/article/201/1/1/7282981
 * Comparative annelids:
 * https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids
 */
internal data class NereisTextbookSection(
    val id: String,
    val title: BilingualText,
    val paragraphs: List<BilingualText>,
) {
    init {
        require(id.matches(Regex("[a-z-]+")))
        require(paragraphs.size>=2)
        require(paragraphs.all { it.english.length>75 && it.tamil.length>50 })
    }
}
internal data class NereisStaticPlate(
    val id: String,
    val title: BilingualText,
    val terms: List<BilingualText>,
    val teachingNote: BilingualText,
) {
    init { require(terms.size>=4);require(id.matches(Regex("[a-z-]+"))) }
}
internal object UnitIVNereisLessons {
    private fun b(en:String,ta:String)=BilingualText(en,ta)
    private fun s(id:String,en:String,ta:String,en1:String,ta1:String,en2:String,ta2:String)=
        NereisTextbookSection(id,b(en,ta),listOf(b(en1,ta1),b(en2,ta2)))

    val sections=listOf(
        s("identity","Identity and taxonomy","அடையாளமும் வகைப்பாடும்",
          "Nereis is a genus of marine nereidid annelids, not a synonym for every segmented marine worm. In the traditional undergraduate syllabus it is taught under Annelida, Polychaeta; current phylogenetic treatments generally place nereidids among errant annelids (Errantia). Do not assume all pictured worms are the same species.",
          "Nereis என்பது கடலில் வாழும் Nereididae குடும்ப வளையப்புழுக்களின் பேரினம். அனைத்து கடல் வளையப்புழுக்களையும் Nereis என்று குறிப்பிடக் கூடாது. பாரம்பரியப் பாடத்திட்டத்தில் Annelida, Polychaeta என்றும் நவீன வகைப்பாட்டில் Errantia குழுவிலும் இடம் பெறுகின்றது.",
          "Nereidid identification depends on the prostomium, antennae, palps, tentacular cirri, pharyngeal jaws and paragnaths, parapodial ligules and chaetae. These vary by species and segment position; use generic diagrams only for common teaching characters.",
          "இனத்தை அடையாளம் காண புரோஸ்டோமியம், உணர்கொம்புகள், பல்புகள், கூடாரச் சிர்ரைகள், தொண்டைத்தாடைகள், பாராப்போடியக் கிளைகள் மற்றும் சீட்டாக்கள் கவனிக்கப்படுகின்றன. இவ்வமைப்புகள் இனம் மற்றும் கண்டம் பொறுத்து மாறக்கூடும்."),
        s("habitat","Habit, habitat and feeding","வாழிடம், பழக்கவழக்கம் மற்றும் உணவூட்டம்",
          "Nereidids inhabit marine coastal sediments, crevices and burrows. Many crawl or swim with paired parapodia; trophic ecology varies from predation to omnivory and scavenging among species. The environment is saline and should not be confused with freshwater earthworm habitats.",
          "நெரீயிட் வளையப்புழுக்கள் கடற்கரைக் படிவுகள், பாறை இடுக்குகள் மற்றும் வளைகளில் வாழ்கின்றன. பாராப்போடியா உதவியுடன் ஊர்ந்தோ நீந்தியோ செல்கின்றன. இனங்களுக்கு ஏற்ப ஊனுணவு, கலப்புணவு அல்லது இறந்த உயிரினங்களை உண்ணுதல் காணப்படும்.",
          "Protrusible muscular pharynx and paired chitinous jaws assist food capture in many Nereis species. Do not label a crop or gizzard solely by copying the earthworm digestive diagram.",
          "பல Nereis இனங்களில் வெளியே நீட்டக்கூடிய தசைமிக்க தொண்டையும் இரண்டு கைட்டின் தாடைகளும் உணவைப் பிடிக்க உதவுகின்றன. மண்புழுவின் செரிமானப் படத்திலிருந்து crop அல்லது gizzard போன்றவற்றை இங்கு நகலிடக் கூடாது."),
        s("external","External morphology and metamerism","வெளிப்புற அமைப்பும் கண்டப்பிரிவும்",
          "The elongate, bilaterally symmetrical body consists of the prostomium, peristomium, repeated chaetigerous trunk segments and a terminal pygidium with the anus and often paired anal cirri. Chaetigers bear chaetae; the anterior head region is not just an ordinary chaetiger.",
          "நீண்ட இருபக்கச் சமச்சீருள்ள உடலில் புரோஸ்டோமியம், பெரிஸ்டோமியம், சீட்டா கொண்ட கண்டங்கள் மற்றும் பின்முனைப் பைகிடியம் காணப்படும். பைகிடியத்தில் ஆசனவாயும் பொதுவாக இணை ஆசனச் சிர்ரைகளும் உள்ளன. தலைப்பகுதி ஒரு சாதாரண கண்டம் அல்ல.",
          "Segment boundaries, paired lateral parapodia and associated bundles of chaetae illustrate metameric organisation. An immature individual, an epitokous swimming individual and different species need not have the same parapodial outlines.",
          "ஒவ்வொரு உடற்கண்டத்தின் பக்கங்களிலும் பொதுவாக இணையான பாராப்போடியாவும் சீட்டாக்களின் கொத்துகளும் காணப்படுகின்றன. இளம் புழுவிலும் இனப்பெருக்கத்திற்கான மாற்றம் பெற்ற எப்பிடோக் வடிவத்திலும் பாராப்போடிய அமைப்பு மாறலாம்."),
        s("head","Prostomium, peristomium and sensory organs","தலைப்பகுதி மற்றும் உணர்வுறுப்புகள்",
          "The prostomium is the preoral head region carrying paired palps, antennae and often two pairs of eyes. The peristomium surrounds the mouth and bears tentacular cirri; the cirri must not be mistaken for prostomial antennae. Exact eye arrangement is species dependent.",
          "வாய்க்கு முன்புள்ள புரோஸ்டோமியத்தில் பல்புகள், உணர்கொம்புகள் மற்றும் பொதுவாக இரண்டு ஜோடி கண்கள் உள்ளன. வாயைச் சூழும் பெரிஸ்டோமியத்தில் கூடாரச் சிர்ரைகள் காணப்படும். இச்சிர்ரைகளை புரோஸ்டோமிய உணர்கொம்புகளாகக் கருதக் கூடாது.",
          "Paired nuchal organs behind the prostomium function in chemosensation. A drawing viewed dorsally should not label a hidden ventral mouth as an exposed dorsal opening without a view annotation.",
          "புரோஸ்டோமியத்திற்குப் பின்புறம் உள்ள இணை நூக்கல் உறுப்புகள் வேதியியல் உணர்வில் உதவுகின்றன. முதுகுப்புறப் பார்வையில் மறைந்திருக்கும் வயிற்றுப்புற வாயை வெளிப்பட்ட முதுகுப்புறத் துளையாகக் காட்டக்கூடாது."),
        s("parapodia","Biramous parapodia and chaetae","இருகிளைப் பாராப்போடியாவும் சீட்டாக்களும்",
          "A representative chaetiger commonly bears a dorsal notopodium and a ventral neuropodium. Each ramus carries supporting acicula and chaetal bundles, while dorsal and ventral cirri occupy different positions. Ligules and chaetal types vary along the body and across species.",
          "ஒரு பொதுவான சீட்டா கொண்ட கண்டத்தில் மேல்புற நோட்டோபோடியமும் கீழ்ப்புற நியூரோபோடியமும் காணப்படும். ஆதரவளிக்கும் அசிகுலாவும் சீட்டாக்களின் கொத்துகளும் கிளைகளுடன் தொடர்புடையவை; முதுகுச் சிர்ரஸும் வயிற்றுச் சிர்ரஸும் வேறு இடங்களில் அமைந்துள்ளன.",
          "Chaetae are segmental bristles and the parapodium is a paired muscular body-wall outgrowth involved in locomotion, sometimes assisting respiration. Some anterior chaetigers may be uniramous in certain nereidids; one isolated drawing does not prove a uniform arrangement for all segments.",
          "சீட்டாக்கள் உடற்கண்ட முட்களாகும். பாராப்போடியா தசைமிக்க பக்க நீட்சியாக இயங்கிச் செல்வதற்கும் சில சமயங்களில் வாயுப் பரிமாற்றத்திற்கும் உதவுகிறது. முன்புறக் கண்டங்கள் சில இனங்களில் ஓர்கிளை அமைப்பாக இருக்கலாம்."),
        s("bodywall","Body wall, coelom and movement","உடற்சுவர், உடற்குழி மற்றும் இயக்கம்",
          "The body wall comprises cuticle, epidermis and muscle layers. The fluid-filled coelom, septa and segmented musculature provide a hydrostatic basis for movement. Alternating muscular action with parapodial strokes produces crawling and swimming.",
          "கியூட்டிக்கிள், மேல்தோல் மற்றும் தசை அடுக்குகள் உடற்சுவரை உருவாக்குகின்றன. திரவம் நிரம்பிய உடற்குழியும் இடைத்தடுப்புகளும் தசைகளும் இயக்கத்திற்கு ஆதரவளிக்கின்றன. பாராப்போடிய அசைவுகளுடன் தசைச் சுருக்கம் இணைந்து ஊர்தல் மற்றும் நீந்துதல் நடைபெறுகின்றன.",
          "Distinguish segmental chaetae from the head's sensory cirri. A whole-organism pencil figure should display bilateral parapodia and the pygidium, rather than an earthworm-like body with a clitellum.",
          "உடற்கண்டச் சீட்டாக்களையும் தலைப்பகுதி உணர்ச்சிச் சிர்ரைகளையும் வேறுபடுத்த வேண்டும். முழு நெரீஸ் படத்தில் இருபக்க பாராப்போடியாவும் பைகிடியமும் காணப்பட வேண்டும்; மண்புழுவின் கிளிட்டெல்லத்தைச் சேர்க்கக் கூடாது."),
        s("digestion","Digestive system and eversible pharynx","செரிமான அமைப்பும் வெளியே நீளும் தொண்டையும்",
          "Food passes from the mouth into a muscular, eversible pharyngeal apparatus, then through the oesophagus to the intestine and posterior anus. Many nereidids bear paired jaws and denticles or paragnaths within the pharyngeal region. The precise distribution is important for taxonomy.",
          "வாய் வழியாக நுழையும் உணவு வெளியே நீட்டக்கூடிய தசைமிக்க தொண்டை, உணவுக்குழாய், குடல் வழியாகப் பின்புற ஆசனவாயை அடைகிறது. பல நெரீயிடுகளில் தொண்டைப் பகுதியில் இணையான தாடைகளும் பற்களோ சிறு முட்களோ காணப்படுகின்றன.",
          "In a practical drawing the pharynx, oesophagus, tubular intestine and anal opening should be continuous. Do not invent a crop, gizzard or caeca where the particular genus-level source does not establish them.",
          "செய்முறைப் படத்தில் தொண்டை, உணவுக்குழாய், நீளமான குடல் மற்றும் ஆசனவாய் தொடர்ச்சியாகத் தெளிவாக இருக்க வேண்டும். ஆதாரம் இல்லாமல் அரைப்பை, சேமிப்புப்பை அல்லது குடற்பைகள் போன்ற உறுப்புகளைச் சேர்க்கக்கூடாது."),
        s("circulation","Closed circulation and segmental vessels","மூடிய இரத்தச் சுற்றோட்டமும் கண்ட நாளங்களும்",
          "Nereidids have a closed circulatory arrangement with longitudinal dorsal and ventral vessels and connections to segmental and peripheral vascular beds. Vascular detail, contractility and gill/peripheral supply vary; a schematic should not equate both longitudinal vessels in the same anatomical plane.",
          "நெரீயிடுகளில் மூடிய இரத்தச் சுற்றோட்டம் காணப்படுகிறது. நீளமாகச் செல்லும் முதுகு மற்றும் வயிற்று இரத்த நாளங்கள் கண்டநாளங்களுடனும் வெளிப்புற நாள வலையுடனும் தொடர்புடையவை. இந்நாளங்கள் ஒரே உடற்கூறு தளத்தில் அருகருகே இருப்பதாகக் காட்டக்கூடாது.",
          "The dorsal vessel lies near the gut dorsally; the ventral vessel belongs on the ventral side. Explain their relative position in a cross-sectional inset instead of drawing them as two side-by-side surface stripes.",
          "முதுகு நாளம் குடலின் மேல்புறத்திலும் வயிற்று நாளம் கீழ்ப்புறத்திலும் உள்ளது. இரண்டையும் பக்கத்துக்குப் பக்கம் வரைபடத்தில் வரையாமல் குறுக்குவெட்டு விளக்கத்தில் அவற்றின் இடத்தைத் தெரிவிக்க வேண்டும்."),
        s("respiration","Respiration over body surface and parapodia","உடற்பரப்பிலும் பாராப்போடியாவிலும் சுவாசம்",
          "Gas exchange occurs across the moist body surface, including vascularised parapodial surfaces; specialised branchial structures vary widely among annelid groups and among nereidids. Do not draw a uniform row of true gills on every Nereis parapodium without a source.",
          "ஈரமான உடற்பரப்பிலும் இரத்த நாளங்கள் கொண்ட பாராப்போடிய மேற்பரப்பிலும் வாயுப் பரிமாற்றம் நடைபெறலாம். சில வளையப்புழுக்களின் சிறப்பு செவுள்கள் அனைத்து Nereis இனங்களுக்கும் பொதுவானவை அல்ல.",
          "The circulatory system transports gases and nutrients, but oxygen exchange and muscular swimming should be discussed separately in physiology lessons. Water-flow arrows must not be confused with blood-flow arrows.",
          "இரத்தச் சுற்றோட்டம் வாயுக்கள் மற்றும் ஊட்டச்சத்துகளை எடுத்துச் செல்கிறது; நீரில் வாயுப் பரிமாற்றமும் பாராப்போடியா மூலம் நீந்துதலும் வெவ்வேறு செயல்களாக விளக்கப்பட வேண்டும்."),
        s("excretion","Segmental excretion and nephridia","கண்டவாரிக் கழிவுநீக்கமும் நெப்ரிடியாவும்",
          "Metanephridia are generally paired segmental excretory units in polychaete teaching models. A ciliated nephrostome receives coelomic fluid; the tubule modifies the filtrate and a nephridiopore opens outward. Arrangement can change with segment and maturity.",
          "பலசீட்டா வளையப்புழுக்களின் பொதுவான மாதிரியில் இணையான கண்டவாரி நெப்ரிடியா காணப்படும். சிலியா கொண்ட நெப்ரோஸ்டோம் உடற்குழித் திரவத்தைப் பெறுகிறது; நெப்ரிடியக் குழாயில் மாற்றங்கள் நடந்தபின் கழிவு நெப்ரிடியத் துளை வழியாக வெளியேறும்.",
          "The funnel, coiled tubule, septal relationship and external pore must form a continuous meaningful pathway. Do not label a generic funnel-shaped picture as an authenticated Nereis histological specimen.",
          "புனல் வடிவ நுழைவு, சுருண்ட குழாய், இடைத்தடுப்புத் தொடர்பு மற்றும் வெளிப்புறத் துளை ஆகியவை சரியான தொடர்ச்சியுடன் காட்டப்பட வேண்டும். ஒரு பொதுவான படத்தை உண்மையான Nereis திசுவியல் மாதிரியாகக் குறிப்பிடக்கூடாது."),
        s("nervous","Brain and ventral nerve-cord organisation","மூளை மற்றும் வயிற்று நரம்புக்கயிறு",
          "The anterior cerebral (supraoesophageal) ganglia connect around the pharynx to a suboesophageal nerve centre and a paired or fused ventral nerve cord. Segmental ganglia and peripheral nerves coordinate movements of body-wall muscles and parapodia.",
          "முன்புற மூளைத் நரம்புத்திரள்கள் தொண்டையைச் சுற்றிய இணைப்புகள் வழியாகத் தொண்டைக்கீழ் நரம்பு மையத்துடனும் வயிற்று நரம்புக்கயிறுடனும் தொடர்புகொள்கின்றன. கண்டவாரி நரம்புத்திரள்கள் பாராப்போடியா மற்றும் உடற்சுவர் தசைகளை ஒருங்கிணைக்கின்றன.",
          "A dorsal-view dissection and a ventral-nerve-cord diagram are different projections. Leader lines must point to the true nerve connection rather than an empty coelomic space.",
          "முதுகுப்புறப் பிரிப்பு மற்றும் வயிற்று நரம்புக்கயிறுப் படம் வெவ்வேறு பார்வைகள் ஆகும். குறிப்புக்கோடுகள் வெற்றிடத்தை நோக்காமல் உண்மையான நரம்பு இணைப்பைப் பின்தொடர வேண்டும்."),
        s("reproduction","Reproduction, epitoky and swarming","இனப்பெருக்கம், எப்பிட்டோகி மற்றும் கூட்டுநீந்துதல்",
          "Sexes are usually separate in many Nereis species. At reproductive maturity some nereidids undergo epitoky: parapodia and chaetae change, body regions specialise for swimming and gametes are released into the sea, often during synchronised swarming.",
          "பல Nereis இனங்களில் ஆண், பெண் தனித்தனியாக உள்ளன. இனப்பெருக்க முதிர்ச்சியில் சில நெரீயிடுகள் எப்பிட்டோகி மாற்றம் அடைகின்றன; பாராப்போடியாவும் சீட்டாக்களும் நீந்துதலுக்காக மாறி இனச்செல்கள் கடல்நீரில் வெளியிடப்படுகின்றன.",
          "The swimming reproductive morph should not be named as a different unrelated animal. Historical names such as heteronereid epitoke refer to an altered reproductive state; seasonal and species-specific differences should be stated.",
          "இனப்பெருக்கத்திற்கான நீந்தும் வடிவம் வேறு தொடர்பற்ற விலங்கல்ல. ஹெட்டரோநெரீஸ் போன்ற பழைய பெயர்கள் மாற்றம் பெற்ற இனப்பெருக்க நிலையை விவரிக்கும்; காலநிலை மற்றும் இன வேறுபாடுகள் முக்கியம்."),
        s("larvae","Trochophore and later larval development","ட்ரோகோஃபோர் மற்றும் பின்னைய வளர்நிலைகள்",
          "The classic trochophore is a planktonic ciliated larval organisation with an apical region and ciliary band (prototroch), a mouth, alimentary canal and anus. In nereidids larval patterns and timing vary; later metatrochophore and nectochaete stages add segmentation and chaetal structures.",
          "பொதுவான ட்ரோகோஃபோர் வளர்நிலையில் உச்சிப் பகுதி, சிலியா வளையமான புரோட்டோட்ரோக், வாய், செரிமானக் குழாய் மற்றும் ஆசனவாய் காணப்படும். நெரீயிடுகளில் வளர்ச்சி வரிசை இனத்திற்கு ஏற்ப மாறலாம்; பின்னர் கண்டங்களும் சீட்டாக்களும் தோன்றும்.",
          "Do not draw a late multi-chaetiger juvenile with numerous parapodia and label it as the earliest unsegmented trochophore. Nor should a species-specific trochophore be reconstructed from an unrelated crustacean nauplius.",
          "பல கண்டங்களும் பாராப்போடியாவும் உள்ள பிந்தைய இளநிலையை ஆரம்ப ட்ரோகோஃபோர் என்று குறிக்கக் கூடாது. இறாலின் நௌப்ளியஸ் படத்தைப் பயன்படுத்தி நெரீஸ் வளர்நிலையை உருவாக்கவும் கூடாது."),
        s("ecology","Ecological value and practical identification","சூழலியல் பங்கும் செய்முறை அடையாளமும்",
          "Burrowing and feeding nereidids contribute to benthic sediment mixing, nutrient cycling and food webs. In a laboratory exercise first identify annelid segmentation and parapodia, then distinguish prostomium, peristomium, pygidium and biramous parapodium.",
          "கடற்படிவில் வளையமைத்து வாழும் நெரீயிடுகள் படிவக் கலப்பு, ஊட்டச்சத்து சுழற்சி மற்றும் உணவுச் சங்கிலிகளில் பங்கெடுக்கின்றன. செய்முறையில் முதலில் வளையப்பிரிவும் பாராப்போடியாவும், பின்னர் புரோஸ்டோமியம், பெரிஸ்டோமியம், பைகிடியம் ஆகியவற்றைக் காண வேண்டும்.",
          "The record diagram should display the paired notopodium and neuropodium, dorsal and ventral cirri, acicula and distinct chaetal bundles. Explanatory labels must not be placed over a body outline with no connecting anatomical structure.",
          "செய்முறைப் படத்தில் நோட்டோபோடியம், நியூரோபோடியம், முதுகு/வயிற்றுச் சிர்ரைகள், அசிகுலா மற்றும் தனித்த சீட்டாக் கொத்துகள் தெளிவாக இருக்க வேண்டும். சரியான உறுப்பு இல்லாத இடத்தில் குறிப்புக்கோடுகள் முடிவடையக் கூடாது.")
    )
    val sources=listOf(
        "https://academic.oup.com/zoolinnean/article/201/1/1/7282981",
        "https://doi.org/10.5281/zenodo.10997984",
        "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"
    )
    val plateTopics=listOf(
        b("01 · External morphology","01 · வெளியமைப்பியல்"),
        b("02 · Enlarged head","02 · பெரிதாக்கிய தலை"),
        b("03 · Biramous parapodium","03 · இருகிளைப் பாராப்போடியம்"),
        b("04 · Digestive system","04 · செரிமான அமைப்பு"),
        b("05 · Circulatory system","05 · இரத்தச் சுற்றோட்ட அமைப்பு"),
        b("06 · Nervous system","06 · நரம்பு அமைப்பு"),
        b("07 · Excretory system","07 · கழிவுநீக்க அமைப்பு"),
        b("08 · Trochophore larva","08 · ட்ரோகோஃபோர் இளநிலை")
    )
}