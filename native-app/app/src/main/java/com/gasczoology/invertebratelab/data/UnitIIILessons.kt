package com.gasczoology.invertebratelab.data

/**
 * Unit III teaching corpus: native Kotlin, offline-first, English–Tamil.
 * Seven syllabus chapters; full source-linked explanations precede unmodified A5.
 * Academic status is editorial DRAFT_UNVERIFIED, never reviewer acceptance.
 */
object UnitIIILessons {
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    private fun section(id: String, en: String, ta: String,
        first: BilingualText, second: BilingualText, source: String) =
        LessonSection(id, b(en, ta), listOf(first, second), listOf(source))
    private fun lesson(id: String, en: String, ta: String,
        vararg sections: LessonSection) =
        NativeBilingualLesson(id, b(en, ta), sections.toList(),
            AcademicWorkStatus.DRAFT_UNVERIFIED)

    val chapterIds = listOf("u3-platy", "u3-fasciola", "u3-platy-adapt", "u3-aschel", "u3-ascaris", "u3-nematode-parasites", "u3-nematode-adapt")
    val chapters: Map<String, NativeBilingualLesson> = linkedMapOf(
        "u3-platy" to lesson("u3-platy", "Platyhelminthes — classification and body plan", "பிளாட்டிஹெல்மின்தஸ் — வகைப்பாடும் உடலமைப்பும்",
            section("defining-characters", "Diagnostic characters", "வரையறுக்கும் பண்புகள்",
                b("Flatworms are triploblastic, bilaterally symmetrical and dorsoventrally flattened; most have no body cavity. The tissue between the gut and body wall is filled with mesenchyme (parenchyma).",
                  "தட்டைப்புழுக்கள் மூன்று கருவடுக்குகள் கொண்டவை; இருபக்கச் சமச்சீரும் முதுகு–வயிற்றுத் திசையில் தட்டையான உடலும் உடையவை. பெரும்பாலானவற்றில் உடற்குழி இல்லை; குடலுக்கும் உடற்சுவருக்கும் இடையிலான பகுதியை பாரெங்கைமா நிரப்புகிறது."),
                b("The majority lack specialised circulatory and respiratory organs. Many possess a branched incomplete digestive tract; cestodes are exceptional because the adult tapeworm lacks an alimentary canal.",
                  "பொதுவாகத் தனிப்பட்ட இரத்த ஓட்ட மற்றும் சுவாச உறுப்புகள் இல்லை. பலவற்றில் கிளைத்த, முழுமையற்ற செரிமானப் பாதை உள்ளது; வளர்ந்த நாடாப்புழுவில் உணவுப்பாதையே இல்லாதது முக்கிய விதிவிலக்கு."),
                "https://openstax.org/books/biology-2e/pages/28-3-superphylum-lophotrochozoa-flatworms-rotifers-and-nemerteans"),
            section("taxonomy", "Traditional classes and modern context", "பாரம்பரிய வகுப்புகளும் நவீன வகைப்பாடும்",
                b("The traditional teaching arrangement recognises Turbellaria, Monogenea, Trematoda and Cestoda. Turbellaria is not a single natural evolutionary lineage; modern systematic treatments distinguish Catenulida and Rhabditophora.",
                  "பாரம்பரிய பாடவகைப்பாட்டில் டர்பெல்லேரியா, மோனோஜீனியா, டிரெமடோடா, செஸ்டோடா ஆகியவை பயன்படுத்தப்படுகின்றன. டர்பெல்லேரியா ஒரு தனித்த இயற்கை பரிணாமக் கிளை அல்ல; நவீன வகைப்பாட்டில் கேட்டெனுலிடா மற்றும் ராப்டிடோஃபோரா போன்ற கிளைகள் வேறுபடுத்தப்படுகின்றன."),
                b("Trematodes are largely parasitic flukes; cestodes are segmented tapeworms with specialised attachment organs. Identifying a specimen requires body form, attachment structures and internal anatomy, not only habitat.",
                  "டிரெமடோடுகள் பெரும்பாலும் ஒட்டுண்ணித் தட்டைப்புழுக்கள்; செஸ்டோடுகள் பற்றுறுப்புகள் கொண்ட பகுதி அமைப்புள்ள நாடாப்புழுக்கள். வாழிடத்தை மட்டும் கொண்டு அல்லாமல், உடல்வடிவம், பற்றுறுப்புகள், உள் உறுப்புகள் மூலம் அடையாளம் காண வேண்டும்."),
                "https://openstax.org/books/biology-2e/pages/28-3-superphylum-lophotrochozoa-flatworms-rotifers-and-nemerteans"),
            section("systems", "Nutrition, excretion and nervous organisation", "உணவூட்டம், கழிவுநீக்கம், நரம்பமைப்பு",
                b("In many flatworms food enters a muscular pharynx and is distributed by a branched gut; an anus is typically absent. A protonephridial system with flame cells helps regulate water and dissolved substances.",
                  "பல தட்டைப்புழுக்களில் தசையுள்ள தொண்டை வழியாக உணவு நுழைந்து கிளைத்த குடலால் பகிரப்படுகிறது; பொதுவாக ஆசனவாய் இல்லை. சுடர்செல்கள் கொண்ட புரோட்டோநெஃப்ரிடியல் அமைப்பு நீரும் கரைசல்களும் சமநிலையில் இருக்க உதவுகிறது."),
                b("Anterior sensory and nervous structures are more prominent in free-living forms; parasitic taxa frequently have modified sensory apparatus and protective coverings suited to host environments.",
                  "சுதந்திரமாக வாழும் வகைகளில் முன்புற உணர் மற்றும் நரம்பு அமைப்புகள் தெளிவாகக் காணப்படும்; ஒட்டுண்ணிகளில் புரவலன் சூழலுக்கேற்ற பாதுகாப்பு மூடியும் மாற்றியமைந்த உணருறுப்புகளும் காணப்படும்."),
                "https://openstax.org/books/biology-2e/pages/28-3-superphylum-lophotrochozoa-flatworms-rotifers-and-nemerteans")
        ),
        "u3-fasciola" to lesson("u3-fasciola", "Fasciola hepatica — illustrated type study", "ஃபாசியோலா ஹெபாட்டிகா — படவிளக்க இன ஆய்வு",
            section("identity", "Classification, habitat and host range", "வகைப்பாடு, வாழிடம், புரவலன்கள்",
                b("Fasciola hepatica is a digenean trematode (liver fluke) of phylum Platyhelminthes. Adult flukes reside primarily in the bile ducts of grazing mammals such as sheep and cattle; humans can be accidental definitive hosts.",
                  "ஃபாசியோலா ஹெபாட்டிகா என்பது பிளாட்டிஹெல்மின்தஸ் தொகுதியைச் சேர்ந்த இருபுரவலன் டிரெமடோடு கல்லீரல் தட்டைப்புழு. வளர்ந்த புழுக்கள் ஆடு, மாடு போன்ற மேயும் பாலூட்டிகளின் பித்தநாளங்களில் வாழ்கின்றன; மனிதர்களும் சில சமயம் இறுதி புரவலன்களாகலாம்."),
                b("An aquatic lymnaeid snail serves as an intermediate host. The adult is leaf-shaped, dorsoventrally flattened, with an anterior cone and two conspicuous suckers for feeding and attachment.",
                  "நீர்வாழ் லிம்னேயிட் நத்தை இடைப்புரவலனாகச் செயல்படுகிறது. வளர்ந்த புழுவின் உடல் இலைவடிவமும் தட்டையுமாக உள்ளது; முன்பகுதியில் கூம்பு போன்ற பகுதியும் பற்றுதலுக்கான இரண்டு உறிஞ்சிகளும் உள்ளன."),
                "https://www.cdc.gov/dpdx/fascioliasis/"),
            section("external", "External morphology and tegument", "புறத்தோற்றமும் உடல்மேற்படலமும்",
                b("The oral sucker surrounds the mouth near the anterior extremity; the ventral sucker (acetabulum) is a distinct attachment organ. The tegument protects the body against host bile and digestive or immune challenges.",
                  "முன்முனையில் வாயைச் சூழ்வது வாயுறிஞ்சி; தனித்த வயிற்றுறிஞ்சி (அசெட்டாபுலம்) பற்றுதலுக்கு உதவுகிறது. உடல்மேற்படலம் புரவலனின் பித்தம், செரிமானச் சூழல் மற்றும் நோயெதிர்ப்புச் சவால்களிலிருந்து பாதுகாக்கிறது."),
                b("A labelled practical plate must distinguish the two suckers, genital pore, body margins and posterior end. Suckers are not a second mouth or an anus.",
                  "செய்முறைப் படத்தில் வாயுறிஞ்சி, வயிற்றுறிஞ்சி, இனப்பெருக்கத் துளை, உடல் ஓரம் மற்றும் பின்முனை தெளிவாக வேறுபடுத்தப்பட வேண்டும். இரண்டாவது உறிஞ்சி இன்னொரு வாயோ ஆசனவாயோ அல்ல."),
                "https://www.cdc.gov/dpdx/fascioliasis/"),
            section("internal", "Alimentary and reproductive anatomy", "செரிமான மற்றும் இனப்பெருக்க உறுப்புகள்",
                b("A short pharynx and oesophagus lead to two intestinal caeca with numerous lateral branches; the alimentary canal is incomplete and has no anus. The extensive branches distribute digested material within the flattened body.",
                  "குறுகிய தொண்டை மற்றும் உணவுக்குழாய், பக்கக் கிளைகள் பல கொண்ட இரண்டு குடற்பைகளாகப் பிரிகின்றன; செரிமானப் பாதை முழுமையற்றது, ஆசனவாய் இல்லை. கிளைகள் தட்டையான உடலின் பல பகுதிகளுக்குச் சத்துகளை எடுத்துச் செல்கின்றன."),
                b("The adult is hermaphroditic. Two deeply branched testes lie one behind the other in the posterior half; the single branched ovary is anterior to them, vitelline follicles occupy broad lateral fields, and the uterus is coiled anteriorly. The common genital pore lies on the ventral midline between the oral and ventral suckers. Intestinal branches are not reproductive ducts.",
                  "முதிர்ந்த புழு இருபாலுறுப்புடையது. மிகவும் கிளைத்த இரண்டு விந்தகங்கள் பின்பாதியில் ஒன்றன்பின் ஒன்றாக அமைந்துள்ளன; அவற்றிற்கு முன்புறமாக ஒற்றைக் கிளைத்த சூலகம், இருபுற ஓரங்களில் மஞ்சள்கருச் சுரப்பிகள், முன்புறத்தில் சுருண்ட கருப்பை காணப்படும். பொதுவான இனப்பெருக்கத் துளை வாயுறிஞ்சிக்கும் வயிற்றுறிஞ்சிக்கும் இடையே நடுவயிற்றுப் பகுதியில் உள்ளது. குடல் கிளைகளை இனப்பெருக்கக் குழாய்களாகக் கருதக்கூடாது."),
                "https://www.cdc.gov/dpdx/fascioliasis/"),
            section("excretion", "Flame cells and nervous coordination", "சுடர்செல்கள் மற்றும் நரம்புக் கட்டுப்பாடு",
                b("The protonephridial excretory network contains flame-cell units, tubules and collecting ducts leading to a posterior excretory pore. Its water-balance role must not be confused with the digestive caeca.",
                  "புரோட்டோநெஃப்ரிடியல் கழிவுநீக்க வலையில் சுடர்செல்கள், நுண்குழாய்கள், சேகரிப்புக் குழாய்கள் உள்ளன; பின்புறக் கழிவுத்துளை வழியாக வெளியேறுகின்றன. நீர்ச்சமநிலை பணியை குடற்பைகளுடன் குழப்பக்கூடாது."),
                b("Paired longitudinal nerve cords and anterior ganglionic structures provide coordination. Draw neural, excretory and reproductive systems on separate teaching plates where overlapping leaders would obscure anatomical identity.",
                  "இணையான நீள்நரம்புக் கயிறுகளும் முன்புற நரம்புக் குழுமங்களும் ஒருங்கிணைப்பை அளிக்கின்றன. குறியீடுகள் ஒன்றையொன்று மறைக்கக்கூடிய சூழலில் நரம்பு, கழிவு, இனப்பெருக்க அமைப்புகளைத் தனிப் படங்களில் காட்ட வேண்டும்."),
                "https://openstax.org/books/biology-2e/pages/28-3-superphylum-lophotrochozoa-flatworms-rotifers-and-nemerteans"),
            section("life-cycle", "Digenean life cycle and infective stage", "இருபுரவலன் வாழ்க்கைச் சுழற்சி மற்றும் தொற்றுநிலை",
                b("Unembryonated eggs leave the bile ducts in faeces; in freshwater the embryo develops and releases a miracidium, which penetrates a suitable lymnaeid snail. Inside the snail, sporocyst and redial stages generate cercariae.",
                  "முட்டைகள் பித்தநாளத்திலிருந்து மலத்துடன் வெளியேறுகின்றன; நன்னீரில் வளர்ந்த முட்டையிலிருந்து மிராசிடியம் வெளிவந்து ஏற்ற லிம்னேயிட் நத்தைக்குள் புகுகிறது. நத்தைக்குள் ஸ்போரோசிஸ்ட், ரீடியா பருவங்கள் வழியாகச் செர்கேரியாக்கள் உண்டாகின்றன."),
                b("Cercariae emerge from snails and encyst as metacercariae on aquatic vegetation. After a mammal ingests metacercariae, young flukes excyst in the duodenum, traverse the intestinal wall and peritoneal cavity, migrate through liver tissue and mature in the bile ducts. Miracidia infect snails; metacercariae infect mammals.",
                  "நத்தையிலிருந்து வெளியேறும் செர்கேரியாக்கள் நீர்ச்செடிகளில் மெட்டாசெர்கேரியாவாக உறைகின்றன. அவற்றைப் பாலூட்டி உட்கொண்டதும் முன்சிறுகுடலில் உறை நீங்கி, இளம்புழுக்கள் குடற்சுவரையும் வயிற்றறையையும் கடந்து கல்லீரல் திசுவின் வழியாகப் பித்தநாளங்களை அடைந்து முதிர்கின்றன. மிராசிடியம் நத்தைக்குத் தொற்றும் நிலை; மெட்டாசெர்கேரியா பாலூட்டிக்குத் தொற்றும் நிலை."),
                "https://www.cdc.gov/dpdx/fascioliasis/")
        ),
        "u3-platy-adapt" to lesson("u3-platy-adapt", "Parasitic adaptations in Platyhelminthes", "தட்டைப்புழுக்களின் ஒட்டுண்ணி வாழ்க்கைத் தகவமைப்புகள்",
            section("attachment", "Attachment and the host interface", "பற்றுதலும் புரவலனுடனான தொடர்பும்",
                b("Trematode suckers and cestode scoleces provide attachment to different host surfaces. These are functionally similar adaptations but not anatomically identical organs.",
                  "டிரெமடோடுகளின் உறிஞ்சிகளும் செஸ்டோடுகளின் ஸ்கோலெக்ஸும் வெவ்வேறு புரவலன் பரப்புகளில் பற்றுவதற்கு உதவுகின்றன. பணியில் ஒற்றுமை இருந்தாலும் இவை உடற்கூறில் ஒரே உறுப்பு அல்ல."),
                b("The trematode tegument and cestode absorptive surface support survival in demanding host environments, nutrient acquisition and interactions with host defences.",
                  "டிரெமடோடின் உடல்மேற்படலமும் செஸ்டோடின் உறிஞ்சும் மேற்பரப்பும் புரவலனுக்குள் உயிர்வாழவும் ஊட்டம் பெறவும் நோயெதிர்ப்பு அமைப்புடன் தொடர்புகொள்ளவும் உதவுகின்றன."),
                "https://openstax.org/books/biology-2e/pages/28-3-superphylum-lophotrochozoa-flatworms-rotifers-and-nemerteans"),
            section("reproduction", "Reproductive output and transmission", "இனப்பெருக்க அளவும் பரவலும்",
                b("Many endoparasitic flatworms produce large numbers of eggs. Complex life cycles increase encounter opportunities with appropriate intermediate and definitive hosts.",
                  "பல அகஒட்டுண்ணித் தட்டைப்புழுக்கள் அதிக எண்ணிக்கையில் முட்டைகள் உற்பத்தி செய்கின்றன. சிக்கலான வாழ்க்கைச் சுழற்சிகள் ஏற்ற இடை மற்றும் இறுதிப் புரவலன்களைச் சந்திக்கும் வாய்ப்புகளை உருவாக்குகின்றன."),
                b("Asexual multiplication within the snail amplifies Fasciola larval numbers, but adult sexual reproduction occurs in the vertebrate definitive host. Do not teach these as the same reproductive process.",
                  "நத்தைக்குள் நடைபெறும் பாலிலாப் பெருக்கம் ஃபாசியோலாவின் இளவுயிரி எண்ணிக்கையைப் பெருக்குகிறது; வளர்ந்த புழுவின் பாலின இனப்பெருக்கம் முதுகெலும்புடைய இறுதிப் புரவலனில் நடைபெறுகிறது. இரண்டையும் ஒரே நிகழ்வாகக் கற்பிக்கக் கூடாது."),
                "https://www.cdc.gov/dpdx/fascioliasis/"),
            section("transmission", "Transmission and biological limits", "தொற்றுப்பரவலும் உயிரியல் வரம்புகளும்",
                b("Parasite adaptations are specific to life-history stages: free-living miracidium is mobile and ciliated, while encysted metacercaria is resistant and dispersal-ready.",
                  "ஒட்டுண்ணித் தகவமைப்புகள் பருவத்திற்கேற்றவை: சுதந்திரமாக நீந்தும் மிராசிடியம் குறுஇழைகள் கொண்டது; உறையிலுள்ள மெட்டாசெர்கேரியா நிலைத்திருக்கவும் பரவவும் ஏற்றது."),
                b("Host specificity, exposure route and habitat must be considered separately; a human infection route cannot automatically be inferred from a livestock example without evidence.",
                  "புரவலன் தேர்வு, உட்புகும் வழி மற்றும் வாழிடம் தனித்தனியாக ஆராயப்பட வேண்டும்; கால்நடைகளின் உதாரணத்திலிருந்து மனிதத் தொற்றுப்பாதையை ஆதாரமின்றி ஊகிக்கக்கூடாது."),
                "https://www.cdc.gov/dpdx/fascioliasis/")
        ),
        "u3-aschel" to lesson("u3-aschel", "Traditional Aschelminthes and modern Nematoda", "பாரம்பரிய ஆஸ்கெல்மின்தஸ் மற்றும் நவீன நெமடோடா",
            section("historical", "Historical syllabus versus modern classification", "பழைய பாடவகைப்பாடும் நவீன வகைப்பாடும்",
                b("Aschelminthes is a historical assemblage of pseudocoelomate animal groups, not a well-supported modern phylum. The type study Ascaris belongs to phylum Nematoda, within Ecdysozoa.",
                  "ஆஸ்கெல்மின்தஸ் என்பது போலி உடற்குழி கொண்ட பல விலங்குக் குழுக்களை இணைத்த பழைய பாடவகைப்பாடு; நவீன இயற்கையான ஒரு தொகுதி அல்ல. அஸ்காரிஸ் நவீன வகைப்பாட்டில் எக்டிசோசோவாவுக்குள் உள்ள நெமடோடா தொகுதியைச் சார்ந்தது."),
                b("Retain the older syllabus name as a cross-reference, but teach Nematoda using cuticle, moulting and molecular relationships rather than assuming every pseudocoelomate shares recent ancestry.",
                  "பழைய பாடப்பெயரை ஒப்பீட்டுக்காக வைத்துக்கொள்ளலாம்; ஆனால் நெமடோடாவைக் கற்பிக்க உடல்புறக் கவசம், தோலுரிதல், மூலக்கூறு உறவுகள் ஆகியவற்றை அடிப்படையாகக் கொள்ள வேண்டும்."),
                "https://openstax.org/books/biology-2e/pages/28-5-superphylum-ecdysozoa-nematodes-and-tardigrades"),
            section("body", "Pseudocoel, cuticle and musculature", "போலி உடற்குழி, கவசம் மற்றும் தசைகள்",
                b("Nematodes are bilaterally symmetrical cylindrical animals with a fluid-filled pseudocoel and a protective, periodically moulted cuticle.",
                  "நெமடோடுகள் இருபக்கச் சமச்சீருடைய உருளை வடிவ விலங்குகள்; திரவம் நிரம்பிய போலி உடற்குழியும் காலம்தோறும் உரியும் பாதுகாப்புக் கவசமும் உள்ளன."),
                b("Longitudinal body-wall muscles act against pseudocoelomic hydrostatic pressure to produce characteristic thrashing movements; conspicuous circular locomotory muscles are not their typical arrangement.",
                  "நீள்தசைகள் போலி உடற்குழித் திரவ அழுத்தத்திற்கு எதிராகச் செயல்பட்டு பக்கவாட்டுச் சாட்டையசைவை உருவாக்குகின்றன; இயக்கத்திற்கான தெளிவான வட்டத் தசைகள் இவற்றின் வழக்கமான அமைப்பு அல்ல."),
                "https://openstax.org/books/biology-2e/pages/28-5-superphylum-ecdysozoa-nematodes-and-tardigrades"),
            section("organisation", "Complete gut and reproductive organisation", "முழுமையான குடலும் இனப்பெருக்க அமைப்பும்",
                b("A nematode has a mouth, muscular pharynx, intestine and anus or cloacal outlet. This complete gut contrasts with the incomplete alimentary canal of most trematodes.",
                  "நெமடோடுகளில் வாய், தசைத்தொண்டை, குடல், ஆசனவாய் அல்லது கிளோயகா வெளியேற்றம் உள்ள முழுமையான உணவுப்பாதை காணப்படுகிறது. இது பெரும்பாலான டிரெமடோடுகளின் முழுமையற்ற குடலிலிருந்து வேறுபடுகிறது."),
                b("Sexes are commonly separate in many nematodes. Some groups are free-living while others parasitise plants or animals; the existence of a pseudocoel alone does not indicate parasitism.",
                  "பல நெமடோடுகளில் ஆண், பெண் தனித்தனியாக உள்ளன. சில சுதந்திர வாழ்வும், பிறவைத் தாவர அல்லது விலங்கு ஒட்டுண்ணி வாழ்க்கையும் கொண்டவை; போலி உடற்குழி இருப்பதால் மட்டுமே ஒட்டுண்ணி எனக் கருதக்கூடாது."),
                "https://openstax.org/books/biology-2e/pages/28-5-superphylum-ecdysozoa-nematodes-and-tardigrades")
        ),
        "u3-ascaris" to lesson("u3-ascaris", "Ascaris lumbricoides — illustrated type study", "அஸ்காரிஸ் லும்பிரிகாய்டஸ் — படவிளக்க இன ஆய்வு",
            section("identity", "Identity, habitat and taxonomy", "அடையாளம், வாழிடம், வகைப்பாடு",
                b("Ascaris lumbricoides is a parasitic nematode of the human small intestine. It is a triploblastic, bilaterally symmetrical, cylindrical pseudocoelomate with a tough cuticle and a complete gut.",
                  "அஸ்காரிஸ் லும்பிரிகாய்டஸ் மனிதச் சிறுகுடலில் வாழும் ஒட்டுண்ணி நெமடோடு. மூன்று கருவடுக்குகள், இருபக்கச் சமச்சீர், உருளை உடல், போலி உடற்குழி, வலுவான கவசம் மற்றும் முழு உணவுப்பாதை கொண்டது."),
                b("It is not a flatworm; its fluid-filled pseudocoel, moulting cuticle and separate sexes distinguish it from Fasciola. Human ascariasis is acquired by swallowing infective embryonated eggs.",
                  "இது தட்டைப்புழு அல்ல; திரவம் நிறைந்த போலி உடற்குழி, தோலுரியும் கவசம் மற்றும் தனித்த ஆண்–பெண் பாலினங்கள் ஆகியவை ஃபாசியோலாவிலிருந்து வேறுபடுத்துகின்றன. தொற்றுத்திறன் பெற்ற கருவுள்ள முட்டைகளை உட்கொள்ளுதலால் அஸ்காரியாசிஸ் ஏற்படுகிறது."),
                "https://www.cdc.gov/dpdx/ascariasis/"),
            section("external", "Male–female sexual dimorphism", "ஆண்–பெண் புறத்தோற்ற வேறுபாடு",
                b("Adult females are generally larger, about 20–35 cm, with a comparatively straight posterior end. Males are typically about 15–30 cm and have a ventrally curved tail with copulatory spicules.",
                  "வளர்ந்த பெண் புழுக்கள் பொதுவாக 20–35 செ.மீ. நீளமும் நேரான பின்முனையும் கொண்டவை. ஆண்கள் சுமார் 15–30 செ.மீ. நீளமுடையவை; வயிற்றுப் பக்கம் வளைந்த பின்முனையும் இணைவு முளைகளும் காணப்படும்."),
                b("The mouth is surrounded by three lips. In females the vulva lies ventrally near the junction of the anterior and middle thirds, whereas the anus opens ventrally just before the posterior tip. Males have a ventrally curved posterior end with a subterminal cloaca and copulatory spicules.",
                  "வாயைச் சுற்றி மூன்று உதடுகள் உள்ளன. பெண்ணின் வல்வா முன் மற்றும் நடு மூன்றில் ஒரு பகுதிகள் சந்திக்கும் இடத்திற்கு அருகே வயிற்றுப் பக்கத்தில் உள்ளது; ஆசனவாய் பின்முனைக்கு சற்றுமுன் வயிற்றுப் பக்கத்தில் திறக்கிறது. ஆணின் பின்பகுதி வயிற்றுப் பக்கம் வளைந்துள்ளது; இறுதிமுனைக்கு சற்று முன்னுள்ள கிளோயகாவும் இணைவு முளைகளும் காணப்படும்."),
                "https://www.cdc.gov/dpdx/ascariasis/"),
            section("female-internal", "Female internal anatomy", "பெண் புழுவின் உள் உறுப்புகள்",
                b("Each of the two slender, extensively coiled ovaries continues into an oviduct and one of two uteri; the paired uteri lead into a common vagina opening through the anterior-third vulva. The relatively straight intestine is separate from the reproductive tubes. Draw the ovaries as thin tubular coils, not as bunches of eggs.",
                  "மிகவும் சுருண்ட மெல்லிய இரண்டு சூலகங்கள் தத்தம் சூலகக் குழாய்கள் வழியாக இரண்டு கருப்பைகளுடன் இணைகின்றன; அவை பொதுவான யோனி வழியாக முன் மூன்றில் ஒரு பகுதியிலுள்ள வல்வாவில் திறக்கின்றன. ஓரளவு நேராகச் செல்லும் குடல் இனப்பெருக்கக் குழாய்களிலிருந்து தனியானது. சூலகங்களை முட்டைக் கொத்துகளாக அல்ல, மெல்லிய சுருண்ட குழாய்களாக வரைய வேண்டும்."),
                b("The pseudocoel is a fluid-filled cavity outside the gut and inside the body wall. A transverse section should identify cuticle, hypodermis, longitudinal muscles, gut, reproductive tubes and lateral cords without conflating them.",
                  "போலி உடற்குழி குடலுக்கும் உடற்சுவருக்கும் இடையிலுள்ள திரவப் பகுதி. குறுக்குவெட்டில் கவசம், ஹைப்போடெர்மிஸ், நீள்தசைகள், குடல், இனப்பெருக்கக் குழாய்கள், பக்கநாண்கள் ஆகியவற்றைத் தெளிவாக வேறுபடுத்த வேண்டும்."),
                "https://openstax.org/books/biology-2e/pages/28-5-superphylum-ecdysozoa-nematodes-and-tardigrades"),
            section("life-cycle", "Egg, larval migration and adult stage", "முட்டை, இளவுயிரி இடம்பெயர்ச்சி, வளர்ந்த பருவம்",
                b("Fertilised and unfertilised eggs are passed with faeces, but unfertilised eggs cannot develop into infective larvae. Fertilised eggs require approximately 18 days to several weeks in warm, moist, shaded soil to become infective. Humans acquire infection by ingesting embryonated fertile eggs, not through larval penetration of skin.",
                  "கருவுற்ற மற்றும் கருவுறாத முட்டைகள் மலத்துடன் வெளியேறுகின்றன; கருவுறாத முட்டைகள் தொற்றும் இளவுயிரியாக வளராது. கருவுற்ற முட்டைகள் ஈரமான, வெப்பமான, நிழலுள்ள மண்ணில் சுமார் 18 நாட்கள் முதல் பல வாரங்கள் வரை வளர்ந்து தொற்றும் நிலையை அடைகின்றன. இவ்வாறு கருவளர்ந்த கருவுற்ற முட்டைகளை விழுங்குவதால் மனிதருக்குத் தொற்று ஏற்படுகிறது; தோல்வழி இளவுயிரிகள் நுழைவதால் அல்ல."),
                b("Larvae hatch in the small intestine, penetrate the intestinal mucosa and migrate through portal and systemic circulation to the lungs. Over approximately 10–14 days they develop in the lungs, enter alveoli, ascend airways, reach the pharynx and are swallowed. Adult worms inhabit the small intestine, where egg production usually begins about 2–3 months after ingestion of infective eggs.",
                  "சிறுகுடலில் வெளிவரும் இளவுயிரிகள் குடலின் சளிச்சவ்வைத் துளைத்து போர்டல் மற்றும் உடல் இரத்த ஓட்டம் வழியாக நுரையீரலை அடைகின்றன. நுரையீரலில் சுமார் 10–14 நாட்களில் மேலும் வளர்ந்து காற்றுப்பைகளுக்குள் நுழைந்து, சுவாசக்கிளைகள் வழியாகத் தொண்டையை அடைந்து மீண்டும் விழுங்கப்படுகின்றன. முதிர்ந்த புழுக்கள் சிறுகுடலில் வாழ்கின்றன; தொற்றுமுட்டைகளை விழுங்கிய சுமார் 2–3 மாதங்களுக்குப் பிறகு முட்டையிடுதல் தொடங்குகிறது."),
                "https://www.cdc.gov/dpdx/ascariasis/"),
            section("eggs", "Egg morphology and laboratory relevance", "முட்டையின் அமைப்பும் ஆய்வக முக்கியத்துவமும்",
                b("Fertilised eggs have a thick protective shell, often with a mammillated outer layer; unfertilised eggs are usually longer and structurally different. Stool microscopy is commonly used to detect intestinal ascariasis.",
                  "கருவுற்ற முட்டைகள் தடித்த பாதுகாப்பு உறையைக் கொண்டுள்ளன; வெளிப்புறத்தில் குமிழ்த்த மேற்பரப்பு காணப்படலாம். கருவுறாத முட்டைகள் பொதுவாக நீளமானவை, அமைப்பிலும் வேறுபடும். மல நுண்ணோக்கி ஆய்வு குடல் அஸ்காரியாசிஸை அடையாளம் காணப் பயன்படுகிறது."),
                b("An egg passed directly in fresh faeces is generally not yet an infective embryonated egg. In a student practical, use fixed prepared slides and standard laboratory precautions rather than handling suspected infectious material.",
                  "புதிதாக வெளியேறும் மலத்தில் உள்ள முட்டை பொதுவாக இன்னும் தொற்றுத்திறன் பெற்ற கருவுள்ள முட்டையாக இருக்காது. மாணவர் செய்முறையில் தொற்றுச் சந்தேக மாதிரிகளுக்குப் பதிலாக நிலைப்படுத்தப்பட்ட தயார்செய்த ஸ்லைடுகளையும் வழக்கமான ஆய்வகப் பாதுகாப்பையும் பயன்படுத்த வேண்டும்."),
                "https://www.cdc.gov/dpdx/ascariasis/")
        ),
        "u3-nematode-parasites" to lesson("u3-nematode-parasites", "Important parasitic nematodes", "முக்கிய ஒட்டுண்ணி நெமடோடுகள்",
            section("examples", "Representative parasites and hosts", "ஒட்டுண்ணிகளும் புரவலன்களும்",
                b("Ascaris lumbricoides inhabits the human small intestine. Hookworms, filarial nematodes and pinworms differ in transmission route, adult habitat and clinical effects; they must not be taught as one identical life cycle.",
                  "அஸ்காரிஸ் லும்பிரிகாய்டஸ் மனிதச் சிறுகுடலில் வாழ்கிறது. கொக்கிப்புழு, ஃபைலேரியப் புழு, ஊசிப்புழு ஆகியவற்றின் தொற்றுப்பாதை, வளர்ந்த வாழிடம், நோய்விளைவுகள் வேறுபடுகின்றன; ஒரே வாழ்க்கைச் சுழற்சி எனக் கற்பிக்கக் கூடாது."),
                b("Diagnostic comparisons should distinguish ingestion of embryonated eggs (Ascaris), skin-penetrating hookworm larvae, and vector-mediated filarial transmission where relevant.",
                  "அடையாள ஒப்பீட்டில் கருவுள்ள முட்டை விழுங்குதல் (அஸ்காரிஸ்), தோல்வழி நுழையும் கொக்கிப்புழு இளவுயிரிகள், கடத்தி மூலம் பரவும் ஃபைலேரியா ஆகியவற்றை வேறுபடுத்த வேண்டும்."),
                "https://www.cdc.gov/dpdx/ascariasis/"),
            section("transmission", "Infective stages and exposure control", "தொற்றுநிலைகளும் தடுப்பு வழிகளும்",
                b("Transmission is parasite-specific; sanitation, safe water, footwear, vector control and appropriate medical programmes act on different exposure pathways.",
                  "தொற்றுப்பரவல் ஒட்டுண்ணிக்கு உரியது; சுகாதாரம், பாதுகாப்பான நீர், காலணி, கடத்தி கட்டுப்பாடு, மருத்துவ நடவடிக்கைகள் ஆகியவை வெவ்வேறு வெளிப்பாட்டு வழிகளில் செயல்படுகின்றன."),
                b("The biology of eggs, soil maturation and larval migration should be described accurately before discussing prevention, so that an ineffective route is not emphasised for the wrong nematode.",
                  "தடுப்பைப் பேசும்முன் முட்டை, மண்ணில் வளர்ச்சி, இளவுயிரி இடம்பெயர்ச்சி ஆகியவற்றைச் சரியாக விளக்க வேண்டும்; இல்லையேல் தவறான ஒட்டுண்ணிக்குத் தொடர்பற்ற வழியை வலியுறுத்த நேரிடும்."),
                "https://www.cdc.gov/dpdx/ascariasis/"),
            section("practical", "Specimen recognition and biosafety", "மாதிரி அடையாளமும் உயிர் பாதுகாப்பும்",
                b("Recognise male and female Ascaris by overall length and tail shape, and compare fertilised with unfertilised egg preparations. An organ or egg label must point to its corresponding real structure.",
                  "அஸ்காரிஸ் ஆண்–பெண் புழுக்களை நீளமும் வால் வடிவமும் கொண்டு வேறுபடுத்துக; கருவுற்ற, கருவுறாத முட்டைத் தயாரிப்புகளை ஒப்பிடுக. உறுப்பு அல்லது முட்டை குறியீடு உண்மையான அமைப்பையே சுட்ட வேண்டும்."),
                b("Use sealed preserved specimens and prepared microscope slides in undergraduate teaching. Live infective material is unnecessary for demonstrating the relevant morphology.",
                  "இளநிலை கற்பித்தலில் மூடிய பாதுகாக்கப்பட்ட மாதிரிகளும் தயார்செய்த நுண்ணோக்கி ஸ்லைடுகளும் பயன்பட வேண்டும். தொடர்புடைய உடற்கூற்றைக் காட்ட உயிருள்ள தொற்றுப் பொருள் அவசியமில்லை."),
                "https://www.cdc.gov/dpdx/ascariasis/")
        ),
        "u3-nematode-adapt" to lesson("u3-nematode-adapt", "Nematode parasitic adaptations", "நெமடோடுகளின் ஒட்டுண்ணித் தகவமைப்புகள்",
            section("barrier", "Cuticle, moulting and host resistance", "கவசம், தோலுரிதல், புரவலன் எதிர்ப்பு",
                b("The robust nematode cuticle helps resist chemical and mechanical conditions within the host. It is distinct from the syncytial tegument of trematodes, despite a comparable protective role.",
                  "வலுவான நெமடோடு கவசம் புரவலனுக்குள் உள்ள வேதியியல் மற்றும் இயந்திரச் சவால்களை எதிர்கொள்ள உதவுகிறது. ஒரே பாதுகாப்புப் பணி இருந்தாலும் இது டிரெமடோடின் ஒருங்கிணைந்த உடல்மேற்படலத்திலிருந்து வேறுபட்டது."),
                b("Periodic moulting enables growth. Protective egg shells and environmental embryonation support transmission, but only a properly developed infective egg transmits Ascaris by ingestion.",
                  "காலம்தோறும் தோலுரிதல் வளர்ச்சிக்கு உதவுகிறது. பாதுகாப்பான முட்டையுறை மற்றும் சுற்றுச்சூழல் வளர்ச்சி பரவலை ஆதரிக்கின்றன; ஆனால் முழுமையாக வளர்ந்த தொற்றுத்திறன் முட்டையை விழுங்கினால்தான் அஸ்காரிஸ் தொற்று ஏற்படும்."),
                "https://openstax.org/books/biology-2e/pages/28-5-superphylum-ecdysozoa-nematodes-and-tardigrades"),
            section("reproduction", "Sexual dimorphism and egg output", "பாலின வேறுபாடும் முட்டை உற்பத்தியும்",
                b("Ascaris males have a posterior reproductive opening associated with a curved tail and copulatory spicules; females possess extensive paired reproductive tubes and produce many eggs.",
                  "அஸ்காரிஸ் ஆண்களில் வளைந்த வாலும் இணைவு முளைகளுடன் தொடர்புடைய பின்புற இனப்பெருக்க வெளியேற்றமும் காணப்படும்; பெண்களில் விரிந்த இரட்டை இனப்பெருக்கக் குழாய்கள் இருந்து அதிக முட்டைகள் உற்பத்தியாகின்றன."),
                b("Producing abundant eggs is a transmission adaptation, not proof that every egg immediately becomes infective. Eggs require a suitable external period to embryonate.",
                  "அதிக முட்டை உற்பத்தி பரவலுக்கான தகவமைப்பு; ஒவ்வொரு முட்டையும் உடனே தொற்றுத்திறன் பெறுகிறது என்பதல்ல. ஏற்ற வெளிச்சூழலில் கருவளர்ச்சி காலம் தேவை."),
                "https://www.cdc.gov/dpdx/ascariasis/"),
            section("migration", "Larval migration as a developmental programme", "வளர்ச்சி நிகழ்வாக இளவுயிரி இடம்பெயர்ச்சி",
                b("Ascaris larval transit through the liver and lungs is part of its developmental route; adult worms live in the small intestine. Distinguish migration from the location of sexual reproduction.",
                  "அஸ்காரிஸ் இளவுயிரிகள் கல்லீரல், நுரையீரல் வழியாக நகர்வது வளர்ச்சி பாதையின் ஒரு பகுதி; வளர்ந்த புழுக்கள் சிறுகுடலில் வாழ்கின்றன. இடம்பெயர்ச்சியையும் பாலின இனப்பெருக்கம் நடைபெறும் இடத்தையும் வேறுபடுத்த வேண்டும்."),
                b("A classroom animation may show the order of organs travelled, but cannot infer actual travel times from playback speed or depict the lungs as an alternate definitive host.",
                  "வகுப்பறை இயங்குபடம் பயணிக்கப்படும் உறுப்புகளின் வரிசையைக் காட்டலாம்; அதன் இயக்க வேகத்திலிருந்து உண்மையான பயண நேரத்தை ஊகிக்கவோ நுரையீரலை மாற்று இறுதிப் புரவலனாகக் காட்டவோ கூடாது."),
                "https://www.cdc.gov/dpdx/ascariasis/")
        )
    )

    data class LifeStage(val id: String, val name: BilingualText,
        val explanation: BilingualText)

    val fasciolaLifeCycle = listOf(
        LifeStage("egg", b("01 · Eggs passed in faeces","01 · மலத்துடன் வெளியேறும் முட்டைகள்"),
            b("Eggs leave the definitive host unembryonated.","கரு வளர்ச்சி இன்னும் தொடங்காத முட்டைகள் இறுதிப் புரவலனிலிருந்து வெளியேறுகின்றன.")),
        LifeStage("miracidium", b("02 · Ciliated miracidium","02 · குறுஇழை மிராசிடியம்"),
            b("Embryonated eggs hatch in freshwater; a miracidium infects a lymnaeid snail.","நன்னீரில் வளர்ந்த முட்டையிலிருந்து வெளிவரும் மிராசிடியம் லிம்னேயிட் நத்தையைத் தொற்றுகிறது.")),
        LifeStage("snail", b("03 · Sporocyst and redia in snail","03 · நத்தைக்குள் ஸ்போரோசிஸ்ட் மற்றும் ரீடியா"),
            b("Asexual multiplication inside the intermediate snail produces cercariae.","இடைப்புரவலன் நத்தைக்குள் பாலிலாப் பெருக்கம் மூலம் செர்கேரியாக்கள் உருவாகின்றன.")),
        LifeStage("cercaria", b("04 · Released cercaria","04 · வெளியேறும் செர்கேரியா"),
            b("Cercariae leave the snail and encyst on plants or other substrates.","செர்கேரியாக்கள் நத்தையை விட்டு வெளியேறி நீர்ச்செடிகள் போன்ற பரப்புகளில் உறைகின்றன.")),
        LifeStage("metacercaria", b("05 · Encysted metacercaria","05 · உறைந்த மெட்டாசெர்கேரியா"),
            b("Metacercariae are the infective stage for grazing mammals and humans.","மேயும் பாலூட்டிகளுக்கும் மனிதர்களுக்கும் தொற்றுத்திறன் கொண்ட நிலை மெட்டாசெர்கேரியா ஆகும்.")),
        LifeStage("adult", b("06 · Adult in bile ducts","06 · பித்தநாளத்தில் வளர்ந்த புழு"),
            b("After ingestion, juvenile flukes migrate to the liver and mature in bile ducts.","உட்கொள்ளப்பட்ட பின் இளம்புழுக்கள் கல்லீரலை நோக்கிச் சென்று பித்தநாளங்களில் முதிர்கின்றன."))
    )

    val ascarisLifeCycle = listOf(
        LifeStage("unembryonated", b("01 · Eggs in faeces","01 · மலத்தில் முட்டைகள்"),
            b("Newly passed eggs usually need environmental development before becoming infective.","புதிதாக வெளியேறும் முட்டைகள் தொற்றுத்திறன் பெறச் சுற்றுச்சூழலில் வளர வேண்டும்.")),
        LifeStage("embryonated", b("02 · Embryonated infective egg","02 · கருவுள்ள தொற்றுமுட்டை"),
            b("Fertilised eggs embryonate in suitable soil over about 18 days to several weeks. Only the developed embryonated egg is infective when ingested.","கருவுற்ற முட்டைகள் ஏற்ற மண்ணில் சுமார் 18 நாட்கள் முதல் பல வாரங்கள் வரை கருவளர்ச்சி அடைகின்றன. முழுமையாகக் கருவளர்ந்த முட்டையை விழுங்கினால்தான் தொற்று ஏற்படும்.")),
        LifeStage("ingestion", b("03 · Egg ingestion and intestinal hatch","03 · முட்டை உட்கொள்ளுதலும் குடலில் பொரிதலும்"),
            b("Larvae emerge after an infective embryonated egg is swallowed.","தொற்றுத்திறன் பெற்ற கருவுள்ள முட்டை விழுங்கப்பட்டதும் இளவுயிரி குடலில் வெளிவருகிறது.")),
        LifeStage("lung", b("04 · Blood circulation and lung migration","04 · இரத்த ஓட்டமும் நுரையீரல் இடம்பெயர்ச்சியும்"),
            b("Larvae penetrate intestinal tissues and reach the lungs through circulation.","இளவுயிரிகள் குடல் திசுக்களைத் துளைத்து இரத்த ஓட்டம் வழியாக நுரையீரலை அடைகின்றன.")),
        LifeStage("swallow", b("05 · Airway ascent and swallowing","05 · சுவாசப்பாதை ஏறுதலும் விழுங்குதலும்"),
            b("Larvae develop further in the lungs for approximately 10–14 days, ascend the bronchial tree to the throat and are swallowed.","இளவுயிரிகள் நுரையீரலில் சுமார் 10–14 நாட்கள் மேலும் வளர்ந்து சுவாசக்கிளைகள் வழியாகத் தொண்டைக்கு வந்து மீண்டும் விழுங்கப்படுகின்றன.")),
        LifeStage("adult", b("06 · Small-intestine adult","06 · சிறுகுடலில் வளர்ந்த புழு"),
            b("Adults reproduce in the small intestine; egg laying starts around 2–3 months after infection. There is no snail intermediate host.","முதிர்ந்த புழுக்கள் சிறுகுடலில் இனப்பெருக்கம் செய்கின்றன; தொற்று ஏற்பட்ட சுமார் 2–3 மாதங்களுக்குப் பிறகு முட்டையிடுதல் தொடங்குகிறது. நத்தை இடைப்புரவலன் இல்லை."))
    )

    fun forChapter(id: String) = chapters[id]

    fun validateDrafts() {
        require(chapterIds == NativeCurriculumManifest.a5ChapterIdsByUnit.getValue(3))
        require(chapters.keys.toList() == chapterIds)
        require(chapters.values.all { it.status == AcademicWorkStatus.DRAFT_UNVERIFIED })
        require(chapters.values.all { it.sections.size >= 3 })
        require(chapters.values.flatMap { it.sections }.all {
            it.paragraphs.size >= 2 && it.scientificSources.all(String::isNotBlank)
        })
        require(fasciolaLifeCycle.map { it.id } ==
            listOf("egg", "miracidium", "snail", "cercaria", "metacercaria", "adult"))
        require(ascarisLifeCycle.map { it.id } ==
            listOf("unembryonated", "embryonated", "ingestion", "lung", "swallow", "adult"))
    }
}
