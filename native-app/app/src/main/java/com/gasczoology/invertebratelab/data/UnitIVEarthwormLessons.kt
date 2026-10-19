package com.gasczoology.invertebratelab.data

/**
 * U4.0 native Earthworm full teaching foundation; source-linked bilingual prose.
 * Historical Pheretima synonym does NOT license mixed-species anatomical plates.
 * No assertion of image acceptance or device verification is made.
 */
object UnitIVEarthwormLessons {
    const val LESSON_ID = NativeCurriculumManifest.EARTHWORM_LESSON_ID
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    private fun section(id: String, en: String, ta: String,
        en1: String, ta1: String, en2: String, ta2: String,
        vararg sources: String
    ) = LessonSection(id,b(en,ta),listOf(b(en1,ta1),b(en2,ta2)),sources.toList())

    val lesson = NativeBilingualLesson(
        lessonId = LESSON_ID,
        title = b("Earthworm — Metaphire posthuma: complete type-study textbook",
                  "மண்புழு — Metaphire posthuma: விரிவான இன ஆய்வுப் பாடநூல்"),
        sections = listOf(
            section("identity",
                "Taxonomy and correct specimen identity",
                "வகைப்பாடும் சரியான இன அடையாளமும்",
                "Earthworm is not the name of one species: it describes numerous terrestrial annelids. The Indian undergraduate type study traditionally called Pheretima posthuma corresponds to Metaphire posthuma (Vaillant, 1868) in current treatments; that name must accompany all species-specific diagrams.",
                "மண்புழு என்பது ஓர் இனத்தின் பெயரல்ல; நிலத்தில் வாழும் பல வளையப்புழுக்களைக் குறிக்கும் பொதுப்பெயர். இந்திய இளநிலைப் பாடங்களில் ‘Pheretima posthuma’ எனக் கற்பிக்கப்படும் வகை, தற்போதைய வகைப்பாட்டில் Metaphire posthuma (Vaillant, 1868) எனக் குறிப்பிடப்படுகிறது. இனத்திற்கே உரிய படங்களில் இதே அடையாளம் பயன்படுத்தப்பட வேண்டும்.",
                "Classical placement is phylum Annelida, class Oligochaeta, family Megascolecidae. Contemporary phylogenetic schemes place earthworms within Clitellata/Crassiclitellata; the traditional Oligochaeta label is retained here as a teaching cross-reference, not asserted to be a complete monophyletic clade.",
                "பாரம்பரிய முறையில் Annelida தொகுதி, Oligochaeta வகுப்பு, Megascolecidae குடும்பம் என்று வழங்கப்படுகிறது. நவீன பரிணாம வகைப்பாட்டில் மண்புழுக்கள் Clitellata/Crassiclitellata குழுவில் அடங்குகின்றன. Oligochaeta என்ற பழைய பெயர் பாடத்திட்ட ஒப்பீட்டுக்காகவே இங்கு பயன்படுத்தப்படுகிறது.",
                "https://appforest.jharkhand.gov.in/fresearch/admin/file/research_357.pdf", "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("habitat",
                "Habitat, feeding ecology and behaviour",
                "வாழிடம், உணவு சூழலியல், நடத்தை",
                "Metaphire posthuma is a soil-dwelling detritivorous earthworm of moist terrestrial habitats. Burrowing mixes soil and organic residues, while castings and tunnelling influence soil structure; ecological functions vary with worm species and soil conditions.",
                "Metaphire posthuma ஈரப்பதமுள்ள நிலமண்ணில் வாழும், மட்கும் கரிமப் பொருட்களை உட்கொள்ளும் வளையப்புழு. மண்ணில் தோண்டும் செயலால் மண்ணும் கரிம எச்சங்களும் கலக்கின்றன; வெளியேறும் மண்கழிவும் சுரங்கங்களும் மண் அமைப்பை மாற்றுகின்றன. இவற்றின் விளைவு இனம் மற்றும் மண்ணின் தன்மையைப் பொறுத்தது.",
                "Earthworms prefer moist soil because their body surface must remain moist for efficient diffusion of respiratory gases. They are terrestrial animals; prolonged drying interferes with cutaneous respiration.",
                "மண்புழு நிலவாழ் விலங்கு; அதன் உடற்பரப்பு ஈரமாக இருப்பது சுவாசத்திற்கு அவசியம். ஈரமான தோலில் ஆக்சிஜன் கரைந்து பரவல் மூலம் உடலுக்குள் செல்கிறது; கார்பன் டைஆக்சைடு எதிர்த்திசையில் வெளியேறுகிறது. உடல் அதிகமாக உலரும்போது இந்த வாயுப் பரிமாற்றம் பாதிக்கப்படுவதால் மண்புழு ஈரப்பதமுள்ள மண்ணை நாடுகிறது.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids", "https://appforest.jharkhand.gov.in/fresearch/admin/file/research_357.pdf"),
            section("external",
                "External segmentation, prostomium and clitellum",
                "வெளிப்புறக் கண்டங்கள், புரோஸ்டோமியம், கிளைடெல்லம்",
                "The elongate bilaterally symmetrical body displays true metameric rings. The prostomium projects above the mouth, the peristomium is the first true segment, and the posterior anal region is distinct from the preceding segmented trunk.",
                "இருபக்கச் சமச்சீருள்ள நீளமான உடலில் உண்மையான கண்டப்பிரிவு வளையங்கள் காணப்படும். வாய்க்கு மேலாக புரோஸ்டோமியம் நீள்கிறது; பெரிஸ்டோமியம் முதல் உண்மைக் கண்டமாகும். பின்புற ஆசனவாய் பகுதி அதற்கு முன் உள்ள கண்டத் தொடரிலிருந்து வேறுபடுகிறது.",
                "In mature M. posthuma, the clitellum typically occupies segments XIV–XVI. Contrary to a common generalized school diagram, published redescription reports setae retained on the ventral clitellar region; do not erase them without specimen-specific evidence.",
                "முதிர்ந்த M. posthuma இல் கிளைடெல்லம் பொதுவாக XIV–XVI கண்டங்களில் உள்ளது. பொதுவான பாடப்படங்களைப் போல கிளைடெல்லத்தில் முற்றிலும் சீட்டாக்கள் இல்லை எனக் காட்டக்கூடாது; வெளியிடப்பட்ட இன மறுவிளக்கத்தில் கிளைடெல்லத்தின் வயிற்றுப்பக்கத்திலும் சீட்டாக்கள் இருப்பதாகக் குறிப்பிடப்பட்டுள்ளது.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://appforest.jharkhand.gov.in/fresearch/admin/file/research_357.pdf"),
            section("pores",
                "Genital, dorsal and spermathecal openings",
                "இனப்பெருக்க, முதுகுப்புற மற்றும் விந்து சேமிப்புத் துளைகள்",
                "Published species diagnosis places the single midventral female genital pore on segment XIV and paired male pores on segment XVIII. Four pairs of spermathecal pores occur in intersegmental furrows 5/6, 6/7, 7/8 and 8/9. These are distinct openings serving different routes.",
                "வெளியிடப்பட்ட இன விளக்கத்தின்படி ஒற்றைப் பெண் இனப்பெருக்கத் துளை XIV கண்டத்தின் நடுவயிற்றுப் பகுதியில், இரட்டை ஆண் துளைகள் XVIII கண்டத்தில் காணப்படும். விந்து சேமிப்புப் பைகளுக்குரிய நான்கு ஜோடித் துளைகள் 5/6, 6/7, 7/8, 8/9 கண்ட இடைவெளிகளில் அமைந்துள்ளன; இவை தனித்தனி பாதைகளுக்குரிய திறப்புகள்.",
                "The first dorsal pore is reported at the 12/13 furrow for this species. A dorsal pore is a coelomic communication, not an external nephridial opening. Any atlas leader must touch the actual pore and match the counted segments.",
                "இந்த இனத்தில் முதல் முதுகுப்புறத் துளை 12/13 கண்ட இடைவெளியில் இருப்பதாகப் பதிவாகியுள்ளது. முதுகுத் துளை உடற்குழியுடன் தொடர்புடையது; அது நெஃப்ரிடியாவின் வெளித்துளை அல்ல. படத்திலுள்ள குறியீட்டுக்கோடு சரியான துளையையும் எண்ணப்பட்ட கண்டத்தையும் தொட வேண்டும்.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://appforest.jharkhand.gov.in/fresearch/admin/file/research_357.pdf"),
            section("body-wall",
                "Body wall, true coelom and septa",
                "உடற்சுவர், உண்மையான உடற்குழி, இடைத்தடைகள்",
                "The body wall consists of a thin protective cuticle, epidermis and circular and longitudinal muscle layers. Coelomic fluid lies within a mesodermally lined true body cavity, and internal septa partition much of the trunk into segments.",
                "உடற்சுவரில் மெல்லிய பாதுகாப்புக் கியூட்டிக்கிள், மேற்றோல், வட்டத் தசை மற்றும் நீளத் தசை அடுக்குகள் உள்ளன. மீசோடெர்மால் அடுக்கால் சூழப்பட்ட உண்மையான உடற்குழியில் திரவம் உள்ளது; உட்புற இடைத்தடைகள் பல கண்டங்களைப் பிரிக்கின்றன.",
                "The coelomic fluid resists compression, so contraction of circular muscles lengthens and narrows a segment, whereas longitudinal muscle contraction shortens and widens it. Septa help transmit and localise these pressure changes between segments. This is a true mesoderm-lined coelom, unlike the pseudocoel of nematodes.",
                "உடற்குழித் திரவம் எளிதில் அமுங்காததால் வட்டத் தசைகள் சுருங்கும்போது ஒரு கண்டம் நீண்டு மெலிகிறது; நீளத் தசைகள் சுருங்கும்போது அது குறுகி அகலமாகிறது. இடைத்தடைகள் கண்டங்களுக்கு இடையிலான அழுத்த மாற்றங்களை ஒழுங்குபடுத்த உதவுகின்றன. இது மீசோடெர்மால் உறையுள்ள உண்மையான உடற்குழியாகும்; நெமடோடுகளின் போலி உடற்குழியிலிருந்து வேறுபடுகிறது.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("setae-locomotion",
                "Perichaetine setae and peristaltic locomotion",
                "சுற்றுச்சீட்டாக்களும் அலைபோன்ற இயக்கமும்",
                "Metaphire has numerous small setae arranged around the circumference of many trunk segments (perichaetine distribution), not four fixed pairs of setae as in certain other earthworms. Each bristle is anchored in the body wall and helps grip the substrate.",
                "Metaphire இன் பல உடற்கண்டங்களைச் சுற்றி ஏராளமான சிறு சீட்டாக்கள் வளையமாக அமைந்துள்ளன (perichaetine அமைப்பு). சில வேறு மண்புழுக்களைப் போல நான்கு ஜோடிகள் மட்டுமே என்று வரையக் கூடாது. ஒவ்வொரு சீட்டாவும் உடற்சுவரில் பதிந்து மண்ணைப் பற்ற உதவுகிறது.",
                "Alternating circular-muscle extension and longitudinal-muscle shortening, coordinated with selective setal anchorage, generates waves of forward progression. The movements are driven by the coelomic hydrostatic system; illustrations must not omit setae from an otherwise detailed locomotion plate.",
                "வட்டத் தசை சுருங்கி உடல் நீள்வதும், நீளத் தசை சுருங்கி உடல் குறுகுவதும், தேர்ந்தெடுத்த சீட்டாக்கள் மண்ணைப் பற்றுவதும் ஒருங்கிணைந்து முன்னேறும் அலை இயக்கத்தை உருவாக்குகின்றன. உடற்குழித் திரவ அழுத்தம் இதற்கு உதவுகிறது; விரிவான இயக்கப் படத்தில் சீட்டாக்களைத் தவிர்க்கக் கூடாது.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids", "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species"),
            section("digestive",
                "Complete alimentary canal and species-specific landmarks",
                "முழுமையான உணவுப்பாதையும் இனத்திற்குரிய இடக்குறிகளும்",
                "The tract passes through mouth, buccal region, muscular pharynx, oesophagus, muscular gizzard and intestine to a terminal anus. In the published M. posthuma redescription, the gizzard occupies approximately segments IX–X and the intestine begins near XV; do not transplant segment numbers from another earthworm genus.",
                "வாய், வாயறை, தசையுள்ள தொண்டை, உணவுக்குழாய், தசைநிறைந்த அரைப்பை, குடல் வழியாக உணவு சென்று இறுதி ஆசனவாய் வழியாக எச்சம் வெளியேறும். வெளியிடப்பட்ட M. posthuma விவரணையில் அரைப்பை சுமார் IX–X கண்டங்களிலும் குடல் XV அருகிலும் தொடங்குகிறது; வேறொரு மண்புழு இனத்தின் கண்ட எண்களை இங்கு நகலெடுக்கக் கூடாது.",
                "The anterior intestine bears paired intestinal caeca originating near segment XXVII and extending forwards in the redescription. A typhlosole is an inward fold of the intestinal wall that increases absorptive surface; it is not a separate tube or external digestive gland. Always mark anus and gizzard correctly.",
                "முன்குடல் பகுதியில் இணையான குடற்பைகள் சுமார் XXVII கண்டத்திலிருந்து முன்னோக்கி நீள்கின்றன என்று இன விளக்கத்தில் கூறப்படுகிறது. டைஃப்லோசோல் என்பது குடற்சுவரின் உள்மடிப்பு; ஊட்டச்சத்து உறிஞ்சும் பரப்பை அதிகரிக்கிறது. அது தனிக் குழாயோ வெளிச் செரிமானச் சுரப்பியோ அல்ல. ஆசனவாயும் அரைப்பையும் சரியாகக் குறிக்க வேண்டும்.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("nutrition",
                "Digestion, absorption and the typhlosole",
                "செரிமானம், உறிஞ்சுதல், டைஃப்லோசோல்",
                "Ingested soil and organic matter are mechanically processed by the muscular gizzard, and digestion and absorption occur along the intestine. The intestinal caeca and the typhlosole should be differentiated by location and tissue continuity in a longitudinal teaching plate.",
                "உட்கொள்ளப்படும் மண்ணும் கரிமப் பொருட்களும் தசையுள்ள அரைப்பையில் இயந்திரமாக அரைக்கப்படுகின்றன; குடலில் செரிமானமும் ஊட்டச்சத்து உறிஞ்சுதலும் நடைபெறுகின்றன. நீளவெட்டுப் படத்தில் குடற்பைகளையும் டைஃப்லோசோலையும் அவற்றின் இடம் மற்றும் திசுத் தொடர்ச்சியால் வேறுபடுத்த வேண்டும்.",
                "The gizzard grinds material and is not a storage-only crop. Textbook pathways for Lumbricus may include a crop, but structures should be labelled for the actual M. posthuma source specimen, not inserted from a generalized annelid template.",
                "அரைப்பை உணவை அரைக்கும் உறுப்பு; வெறும் சேமிப்புப் பை அல்ல. Lumbricus வகையைச் சார்ந்த பொதுப் பாடப்படங்களில் crop காணப்பட்டாலும், M. posthuma வின் உண்மையான மூலமாதிரியில் உறுதிப்படுத்தப்பட்ட உறுப்புகளையே இந்த இனப்புறப் படத்தில் குறிக்க வேண்டும்.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("circulation",
                "Closed circulation and muscular pumping vessels",
                "மூடிய இரத்தச் சுற்றோட்டமும் துடிக்கும் நாளங்களும்",
                "Annelid circulation is generally closed, with longitudinal dorsal and ventral vessels connected through segmental channels. Anterior muscular vessels often called hearts help propel blood; these are vascular arches, not mammalian four-chambered hearts.",
                "வளையப்புழுக்களில் பொதுவாக மூடிய இரத்தச் சுற்றோட்ட அமைப்பு உள்ளது; நீளமான முதுகு, வயிற்று இரத்த நாளங்களை கண்டங்களுக்குரிய இணைநாளங்கள் தொடர்புபடுத்துகின்றன. முன்புறத்தில் இதயம் எனப்படும் துடிக்கும் இரத்தநாள் வளைவுகள் இரத்தத்தைச் செலுத்த உதவுகின்றன; இவை பாலூட்டிகளின் நான்கு அறை இதயங்கள் அல்ல.",
                "Species redescription records the last pair of hearts at segment XIII in M. posthuma. Labels for lateral versus latero-oesophageal vascular arches must be attached to the corresponding vessels; a line ending on the oesophagus or gut fails anatomical audit.",
                "இந்த M. posthuma இன மறுவிளக்கத்தில் கடைசி ஜோடி இதய வளைவுகள் XIII கண்டத்தில் உள்ளதாகக் கூறப்பட்டுள்ளது. பக்கவாட்டு மற்றும் உணவுக்குழாயை ஒட்டிய இரத்தநாள் வளைவுகளை உரிய நாளத்தோடு இணைத்தே குறியிட வேண்டும்; உணவுக்குழாயிலோ குடலிலோ கோடு முடிந்தால் அது உடற்கூறு ஆய்வில் தவறாகும்.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("respiration",
                "Cutaneous gas exchange and moist surface",
                "தோல்வழி வாயுப் பரிமாற்றமும் ஈர மேற்பரப்பும்",
                "Earthworms have no lungs or segmental gills in the adult terrestrial form. Oxygen diffuses across a moist vascularised body surface into blood, while carbon dioxide diffuses outward; dehydration interferes with normal respiration.",
                "நிலத்தில் வாழும் முதிர்ந்த மண்புழுவில் நுரையீரல்களோ ஒவ்வொரு கண்டத்திற்குமான செவுள்களோ இல்லை. ஈரமான இரத்தநாளச் செறிவு மிக்க தோலின் வழியாக ஆக்சிஜன் இரத்தத்தில் கரைகிறது; கார்பன் டைஆக்சைடு வெளியேறுகிறது. உடல் உலர்தல் இயல்பான சுவாசத்தைத் தடுக்கிறது.",
                "A cuticular surface must not be drawn as a thick impermeable exoskeleton. Ask learners to connect body-wall diffusion, soil moisture and the absence of specialised pulmonary organs using the evidence visible in a transverse section.",
                "உடற்புறக் கியூட்டிக்கிளை வாயுக்கள் ஊடுருவ முடியாத தடிமனான வெளி எலும்புக்கூடு போல வரையக் கூடாது. குறுக்குவெட்டில் தெரியும் ஆதாரங்களை வைத்து தோல்வழி பரவல், மண் ஈரப்பதம், தனிச் சுவாச உறுப்புகள் இல்லாமை ஆகியவற்றை மாணவர்கள் தொடர்புபடுத்த வேண்டும்.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("nervous",
                "Cerebral ganglia and segmental nerve coordination",
                "மூளை நரம்புக்குழுக்கள் மற்றும் கண்ட நரம்புக் கட்டுப்பாடு",
                "A pair of supra-pharyngeal cerebral ganglia communicates through circumpharyngeal connectives with subpharyngeal ganglia and a midventral nerve cord. The cord contains paired neural elements with segmental ganglia that coordinate sensory input and muscular activity.",
                "தொண்டைக்கு மேற்புறமுள்ள இணை மூளை நரம்புக் குழுக்கள், தொண்டையைச் சுற்றிச் செல்லும் நரம்பு இணைப்புகள் வழியாக கீழ்தொண்டை நரம்புக் குழுக்களுடனும் நடுவயிற்று நரம்புக் கயிற்றுடனும் தொடர்பு கொண்டுள்ளன. கண்டங்களுக்குரிய நரம்புக் குழுக்கள் உணர்வு தகவலையும் தசைச் செயலையும் ஒருங்கிணைக்கின்றன.",
                "The ventral nerve cord is not the dorsal blood vessel; these run in different anatomical locations. A complete nervous plate should trace the connectives around the pharynx, the ventral trunk cord and segmental branches without leaders terminating on the alimentary canal.",
                "வயிற்றுப்புற நரம்புக் கயிறு முதுகுப்புற இரத்தநாளம் அல்ல; இரண்டும் வேறு இடங்களில் செல்கின்றன. முழுமையான நரம்பு வரைபடம் தொண்டையைச் சுற்றும் நரம்பு இணைப்புகள், நடுவயிற்று நரம்புக் கயிறு, கண்ட நரம்புக் கிளைகள் ஆகியவற்றைக் காட்ட வேண்டும்; குறியீட்டுக் கோடுகள் உணவுப்பாதையில் முடிவடையக் கூடாது.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("excretory",
                "Septal, integumentary and pharyngeal nephridia",
                "இடைச்சுவர், தோல் மற்றும் தொண்டை நெஃப்ரிடியாக்கள்",
                "Indian practical descriptions distinguish septal nephridia associated with intersegmental septa, integumentary nephridia in the body wall, and pharyngeal nephridial groups near the foregut. Their distribution and outlet pathways must be evaluated for the actual species and diagram source.",
                "இந்திய செய்முறைப் பாடங்களில் கண்ட இடைத்தடைகளுடன் தொடர்புள்ள septal nephridia, உடற்சுவரில் உள்ள integumentary nephridia, முன்குடலுக்கு அருகிலுள்ள pharyngeal nephridia என மூன்று வகைகள் வேறுபடுத்தப்படுகின்றன. அவற்றின் பரவலும் வெளியேற்ற வழிகளும் காட்டப்படும் இனத்திற்கும் மூலப்படத்திற்கும் ஏற்ப சரிபார்க்கப்பட வேண்டும்.",
                "Nephridia assist excretion and water-ion balance, but these three groups are not one identical duct system. Do not conflate an enteronephric pathway draining to the gut with exonephric openings to the exterior; reject atlas leaders that merge the distinct categories.",
                "நெஃப்ரிடியாக்கள் கழிவுநீக்கத்திற்கும் நீர்–அயனி சமநிலைக்கும் உதவுகின்றன; மூன்று வகைகளும் ஒரே குழாய் அமைப்பு அல்ல. குடலுக்குள் திறக்கும் enteronephric வழியையும் உடலுக்கு வெளியே திறக்கும் exonephric வழியையும் ஒன்றாகக் குழப்பக் கூடாது; அவற்றைச் சேர்த்து காட்டும் குறியீட்டுக் கோடுகள் நிராகரிக்கப்பட வேண்டும்.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids", "https://appforest.jharkhand.gov.in/fresearch/admin/file/research_357.pdf"),
            section("reproductive",
                "Male and female reproductive tracts",
                "ஆண் மற்றும் பெண் இனப்பெருக்க உறுப்புப்பாதைகள்",
                "Metaphire posthuma is simultaneous hermaphrodite, but male and female organs remain distinct. Species redescription reports testes and sperm funnels in segments X–XI, seminal-vesicle regions around XI–XII, ovaries near XIII, the female pore on XIV and male pores on XVIII.",
                "Metaphire posthuma ஒரே உடலில் ஆண் மற்றும் பெண் இனப்பெருக்க உறுப்புகள் கொண்ட இருபாலுறுப்புயிரி; இருந்தாலும் இருவகை உறுப்புப் பாதைகளும் வேறுபட்டவை. வெளியிடப்பட்ட இன விளக்கத்தில் விந்தகங்கள், விந்து ஏந்துக் குழாய்கள் X–XI கண்டங்களிலும், விந்துச் சேமிப்பு பகுதிகள் XI–XII அருகிலும், சூலகங்கள் XIII அருகிலும், பெண் துளை XIV இலும் ஆண் துளைகள் XVIII இலும் உள்ளன.",
                "Four pairs of spermathecae receive and store exchanged sperm, and their pores lie in furrows 5/6 through 8/9. A reproductive diagram must track sperm ducts, oviducts, spermathecal sacs and prostate ducts separately; neither a segment number nor a leader may be moved merely to fit a page.",
                "நான்கு ஜோடி விந்து சேமிப்புப் பைகள் பரிமாறப்படும் விந்தணுக்களைப் பெறுகின்றன; அவற்றின் துளைகள் 5/6 முதல் 8/9 வரையிலான இடைவெளிகளில் உள்ளன. இனப்பெருக்கப் படத்தில் விந்துக் குழாய்கள், சூலகக் குழாய்கள், விந்து சேமிப்புப் பைகள், புரோஸ்டேட் குழாய்கள் தனித்தனியாகக் காட்டப்பட வேண்டும்; பக்கத்தில் இடம் செய்வதற்காகக் கண்ட எண்ணையோ குறியீட்டுக் கோட்டையோ மாற்றக் கூடாது.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://appforest.jharkhand.gov.in/fresearch/admin/file/research_357.pdf"),
            section("development",
                "Copulation, cocoon formation and direct development",
                "இணைவு, கூடு உருவாக்கம், நேரடி வளர்ச்சி",
                "Two mature worms generally exchange sperm during reciprocal copulation. Clitellar secretions contribute to a mucous envelope and cocoon; eggs and stored sperm meet within the cocoon environment, rather than producing an external aquatic larva.",
                "பொதுவாக இரண்டு முதிர்ந்த மண்புழுக்கள் எதிர்மாறான இணைவில் விந்தணுக்களைப் பரிமாறிக் கொள்கின்றன. கிளைடெல்லம் சுரப்புகள் சளிப்புறையும் முட்டைக் கூடும் உருவாக உதவுகின்றன; முட்டைகளும் சேமித்த விந்தணுக்களும் கூடின் சூழலில் சந்திக்கின்றன. வெளிப்புற நீர்வாழ் இளவுயிரி உருவாகாது.",
                "Earthworms of this terrestrial type have direct development: young wormlets emerge without a trochophore larval stage. A life-cycle animation should show adult exchange, cocoon deposition and juvenile emergence without claiming measured development times.",
                "இந்த நிலவாழ் மண்புழுக்களில் நேரடி வளர்ச்சி நடைபெறுகிறது; trochophore போன்ற தனி இளவுயிரி நிலை இன்றி இளம் மண்புழுக்கள் வெளிவருகின்றன. வாழ்க்கைச் சுழற்சி இயக்கப்படம் முதிர்ந்த புழுக்களின் பரிமாற்றம், கூடு வெளியேற்றம், இளம் புழுக்கள் தோன்றுதல் ஆகியவற்றைக் காட்டலாம்; அளவிடாத காலத்தை உண்மைக் காலமாகச் சித்தரிக்கக் கூடாது.",
                "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids"),
            section("practical",
                "Laboratory identification and evidence limits",
                "ஆய்வக அடையாளமும் ஆதார வரம்புகளும்",
                "Before naming a specimen, examine the clitellum, setal distribution, prostomium, pore positions and—when ethically prepared or archived—the reproductive and intestinal structures. An external photo cannot validate internal ducts, hearts or nephric outlets, and a historical plate needs its own source review.",
                "மாதிரியின் இனத்தை உறுதிப்படுத்தும் முன் கிளைடெல்லம், சீட்டாக்களின் அமைப்பு, புரோஸ்டோமியம், துளைகளின் இடங்கள் மற்றும் முறையாகத் தயாரிக்கப்பட்ட அல்லது ஆவணப்படுத்தப்பட்ட மாதிரியில் இனப்பெருக்க, குடல் அமைப்புகளை ஆராய வேண்டும். வெளிப்புறப் புகைப்படத்தால் உள்ளுறுப்புக் குழாய்கள், இதய வளைவுகள், நெஃப்ரிடியா வெளியேற்ற வழிகள் ஆகியவற்றை உறுதி செய்ய முடியாது.",
                "The six anatomical masters remain an explicit future deliverable: external setae, digestive, circulatory, nervous, excretory and reproductive. Do not mark them present unless lossless raster assets are integrated and every label/leader endpoint receives anatomical review.",
                "வெளிப்புறச் சீட்டாக்கள், செரிமானம், இரத்தச் சுற்றோட்டம், நரம்பமைப்பு, கழிவுநீக்கம், இனப்பெருக்கம் ஆகிய ஆறு உடற்கூறுப் படங்களும் தனித்த தயாரிப்பு இலக்குகளாகவே உள்ளன. தெளிவிழப்பில்லா raster படங்கள் இணைக்கப்பட்டு ஒவ்வொரு உறுப்புப் பெயரும் குறியீட்டுக் கோட்டின் முடிவும் சரிபார்க்கப்படும்வரை படங்கள் உள்ளன என அங்கீகரிக்கக் கூடாது.",
                "https://www.researchgate.net/publication/267412493_A_New_Species_of_the_Terrestrial_Earthworm_of_the_Genus_Metaphire_Sims_and_Easton_1972_from_Thailand_with_Redescription_of_Some_Species", "https://openstax.org/books/biology-2e/pages/28-4-superphylum-lophotrochozoa-mollusks-and-annelids")
        ),
        status = AcademicWorkStatus.DRAFT_UNVERIFIED,
        review = null
    )

    val requiredPlates = listOf(
        Triple("external-setae",b("External morphology and setae","புறத்தோற்றமும் சீட்டாக்களும்"),
            b("Verify prostomium, clitellum XIV–XVI, perichaetine setae and the documented pores.",
              "புரோஸ்டோமியம், XIV–XVI கிளைடெல்லம், சுற்றுச்சீட்டாக்கள் மற்றும் துளைகளைச் சரிபார்க்கவும்.")),
        Triple("digestive",b("Digestive system","செரிமான அமைப்பு"),
            b("Gizzard, intestine, caeca and typhlosole must be separately labelled.",
              "அரைப்பை, குடல், குடற்பைகள், டைஃப்லோசோல் தனித்தனியாகக் குறிக்கப்பட வேண்டும்.")),
        Triple("circulatory",b("Closed blood vascular system","மூடிய இரத்த நாள அமைப்பு"),
            b("Keep longitudinal vessels, last heart arch and digestive tract distinct.",
              "நீள்நாளங்கள், கடைசி இதய வளைவு, உணவுப்பாதை ஆகியவற்றை வேறுபடுத்துக.")),
        Triple("nervous",b("Nervous system","நரம்பு அமைப்பு"),
            b("Check cerebral and subpharyngeal ganglia, connectives and ventral nerve cord.",
              "மூளை, கீழ்தொண்டை நரம்புக் குழுக்கள், இணைப்புகள், வயிற்று நரம்புக் கயிறு ஆகியவற்றைச் சரிபார்க்கவும்.")),
        Triple("excretory",b("Nephridial systems","நெஃப்ரிடியல் அமைப்புகள்"),
            b("Do not conflate septal, integumentary and pharyngeal groups or outlets.",
              "இடைச்சுவர், தோல், தொண்டை நெஃப்ரிடியாக்களையும் வெளியேற்ற வழிகளையும் குழப்ப வேண்டாம்.")),
        Triple("reproductive",b("Reproductive system","இனப்பெருக்க அமைப்பு"),
            b("Check each gland, spermatheca, duct and genital pore at the correct segment.",
              "சுரப்பிகள், விந்து சேமிப்புப் பைகள், குழாய்கள், இனப்பெருக்கத் துளைகளின் கண்டங்களைச் சரிபார்க்கவும்."))
    )
    fun validateDraft() {
        BilingualLessonContract.validate(lesson)
        require(lesson.sections.size == 15)
        require(lesson.sections.all { it.paragraphs.size >= 2 && it.scientificSources.isNotEmpty() })
        require(lesson.status == AcademicWorkStatus.DRAFT_UNVERIFIED && lesson.review == null)
        require(requiredPlates.map { it.first } ==
            NativeCurriculumManifest.typeStudies.single { it.organismId == "earthworm" }.requiredPlateIds)
        require(requiredPlates.all { (_,title,aim) ->
            title.english.isNotBlank() && title.tamil.isNotBlank() &&
            aim.english.isNotBlank() && aim.tamil.isNotBlank()
        })
    }
}
