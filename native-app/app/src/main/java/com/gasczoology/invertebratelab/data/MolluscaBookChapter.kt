package com.gasczoology.invertebratelab.data

/** Book-style chapter: Phylum Mollusca, general characters and classification. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object MolluscaBookChapter {
    val chapter = bchapter(
        lessonId = "u5-mollusca",
        title = "Phylum Mollusca: general characters and classification" to "தொகுதி மொலஸ்கா: பொதுப் பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State the general characters of Phylum Mollusca." to "தொகுதி மொலஸ்காவின் பொதுப் பண்புகளைக் கூற.",
            "Describe the parts of the molluscan body: head, foot, visceral mass and mantle." to "மெல்லுடலியின் உடல் பகுதிகளான தலை, பாதம், உள்ளுறுப்புத் திரள், மேண்டிலை விவரிக்க.",
            "Explain the mantle cavity, the shell and the radula." to "மேண்டில் குழி, ஓடு, ராடுலாவை விளக்க.",
            "Explain the major modifications of the foot in the classes." to "வகுப்புகளில் பாதத்தின் முக்கிய மாற்றங்களை விளக்க.",
            "Name and characterise the four classes used in the syllabus." to "பாடத்திட்டத்தில் பயன்படும் நான்கு வகுப்புகளைப் பெயரிட்டு பண்புகளைக் கூற.",
        ),
        introduction = listOf(
            "The molluscs are the second largest phylum of animals, after the arthropods, and include snails, slugs, mussels, clams, oysters, chitons, squids, octopuses and the nautilus. They live in the sea, in fresh water and on land. They vary in size from animals of a millimetre in length to the giant squid, which is among the largest of all invertebrates. Much of the variety is in the form of the shell and the foot, which are modified in every class, but beneath the variety there is a common plan." to
                "மெல்லுடலிகள் ஆர்த்ரோபோடாக்களுக்கு அடுத்த இரண்டாவது பெரிய விலங்குத் தொகுதி; நத்தைகள், ஸ்லக்குகள், சிப்பிகள், கிளிஞ்சல்கள், கைட்டான்கள், கணவாய்கள், ஆக்டோபஸ்கள், நாட்டிலஸ் ஆகியவை உள்ளடங்கும். கடல், நன்னீர், நிலத்தில் வாழ்கின்றன. ஒரு மில்லிமீட்டர் நீள விலங்குகளிலிருந்து முதுகெலும்பிலிகளில் மிகப் பெரியவற்றில் ஒன்றான பெரும் கணவாய் வரை அளவு மாறுகிறது. பெரும்பாலான வேறுபாடு ஒவ்வொரு வகுப்பிலும் மாற்றப்பட்ட ஓடு, பாதத்தின் வடிவத்தில் உள்ளது; ஆனால் வேறுபாட்டின் அடியில் பொதுத் திட்டம் உள்ளது.",
            "This chapter describes the plan first, as a body with a head, a muscular foot, a visceral mass and a mantle, then discusses the three features that identify the phylum, the mantle cavity, the shell and the radula, and finishes with the classification and the modifications of the foot. The later chapters of the unit take the types, Pila and the pearl oyster, and the gastropod torsion." to
                "இவ்வத்தியாயம் முதலில் தலை, தசைப் பாதம், உள்ளுறுப்புத் திரள், மேண்டில் கொண்ட உடலாகத் திட்டத்தை விவரித்து, தொகுதியை அடையாளம் காட்டும் மூன்று பண்புகளான மேண்டில் குழி, ஓடு, ராடுலாவைப் பற்றி விவாதித்து, வகைப்பாடு, பாதத்தின் மாற்றங்களுடன் முடிகிறது. அலகின் பின்வரும் அத்தியாயங்கள் வகைகளான பைலா, முத்துச் சிப்பி, இரைப்பைக்காலி முறுக்கலை எடுத்துக்கொள்கின்றன.",
        ),
        sections = listOf(
            bsec("general-characters", "General characters", "பொதுப் பண்புகள்",
                blocks = listOf(
                    bp("Molluscs are triploblastic protostomes with a soft, usually unsegmented body. Bilateral symmetry is the basic condition, though a secondary asymmetry appears in the gastropods as a result of torsion and coiling. The true coelom is reduced to the cavity round the heart, the pericardial cavity, and to the cavity of the gonads, and the main body cavity is a haemocoel. The absence of segmentation is one of the features that separate the molluscs from the annelids and the arthropods, in spite of the resemblance of the early development." to
                        "மெல்லுடலிகள் மென்மையான, பொதுவாகக் கண்டமற்ற உடல் உடைய மூவடுக்கு புரோட்டோஸ்டோம்கள். இருபக்கச் சமச்சீர் அடிப்படை நிலை; ஆனால் இரைப்பைக்காலிகளில் முறுக்கலும் சுருள் உருவாக்கமும் காரணமாக இரண்டாம் நிலை சமச்சீரின்மை தோன்றுகிறது. உண்மை உடற்குழி இதயத்தைச் சுற்றிய பெரிகார்டியல் குழிக்கும் இனப்பெருக்க சுரப்பிகளின் குழிக்கும் குறைக்கப்பட்டுள்ளது; முதன்மை உடற்குழி ஹீமோசீல். தொடக்க வளர்ச்சியின் ஒற்றுமை இருந்தாலும், கண்டமாதல் இல்லாமை மெல்லுடலிகளை அனெலிடாக்கள், ஆர்த்ரோபோடாக்களிலிருந்து பிரிக்கும் பண்புகளில் ஒன்று."),
                    bpoints("Characters to be listed in an answer", "விடையில் பட்டியலிட வேண்டிய பண்புகள்",
                        "Triploblastic, protostome, soft-bodied and usually unsegmented, with basic bilateral symmetry; gastropods are secondarily asymmetrical." to "மூவடுக்கு, புரோட்டோஸ்டோம், மென்மையான உடல், பொதுவாகக் கண்டமற்றவை; அடிப்படை இருபக்கச் சமச்சீர்; இரைப்பைக்காலிகள் இரண்டாம் நிலையில் சமச்சீரற்றவை.",
                        "The body has a head, a muscular ventral foot and a visceral mass covered dorsally by the mantle." to "உடலில் தலை, தசை வயிற்றுப்புறப் பாதம், முதுகில் மேண்டிலால் மூடிய உள்ளுறுப்புத் திரள் உள்ளன.",
                        "The mantle forms the mantle cavity and in many species secretes a calcareous shell; the cavity contains the gills and the openings of the excretory, digestive and reproductive systems." to "மேண்டில் மேண்டில் குழியை உருவாக்கி, பல இனங்களில் சுண்ணாம்பு ஓட்டைச் சுரக்கிறது; குழியில் செவுள்களும் கழிவு, செரிமான, இனப்பெருக்க மண்டலத் துளைகளும் உள்ளன.",
                        "A radula is present in most classes for scraping or cutting food, but is absent in bivalves." to "பெரும்பாலான வகுப்புகளில் உணவைச் சுரண்ட அல்லது வெட்ட ராடுலா உண்டு; இரு ஓட்டுடலிகளில் இல்லை.",
                        "The true coelom is reduced and the main cavity is a haemocoel; the circulation is generally open, but closed in cephalopods." to "உண்மை உடற்குழி குறைக்கப்பட்டுள்ளது; முதன்மைக் குழி ஹீமோசீல்; இரத்த ஓட்டம் பொதுவாகத் திறந்தது; தலைகாலிகளில் மூடியது.",
                        "Excretion is by one or more pairs of kidneys, which open into the pericardial cavity and to the mantle cavity." to "கழிவு நீக்கம் பெரிகார்டியல் குழிக்குள்ளும் மேண்டில் குழிக்குள்ளும் திறக்கும் ஒன்று அல்லது அதற்கு மேற்பட்ட இணை சிறுநீரகங்களால்.",
                        "The nervous system has paired ganglia, cerebral, pedal and visceral, joined by connectives and cords." to "நரம்பு மண்டலத்தில் பெருமூளை, பாத, உள்ளுறுப்பு இணை நரம்பு செல்திரள்கள் இணைப்பிகள், வடங்களால் இணைகின்றன.",
                        "Development is commonly through a trochophore and a veliger larva in marine groups." to "கடல் குழுக்களில் வளர்ச்சி பொதுவாக ட்ரோகோபோர், வெலிகர் லார்வா வழியாக.",
                    ),
                    bdef("Visceral mass", "உள்ளுறுப்புத் திரள்", "The part of the molluscan body that contains the organs of digestion, circulation, excretion and reproduction." to
                        "செரிமான, இரத்த ஓட்ட, கழிவு நீக்க, இனப்பெருக்க உறுப்புகள் அடங்கிய மெல்லுடலி உடலின் பகுதி."),
                    bdef("Mantle", "மேண்டில்", "A fold of the body wall that covers the visceral mass and secretes the shell." to
                        "உள்ளுறுப்புத் திரளை மூடி, ஓட்டைச் சுரக்கும் உடற்சுவர் மடிப்பு."),
                    bdef("Veliger", "வெலிகர்", "The second larval stage of many molluscs, with a ciliated swimming organ, the velum." to
                        "வெலம் எனும் கசையிழை நீந்தும் உறுப்புடைய பல மெல்லுடலிகளின் இரண்டாம் லார்வா நிலை."),
                )),
            bsec("mantle", "The mantle, the mantle cavity and the shell", "மேண்டில், மேண்டில் குழி, ஓடு",
                blocks = listOf(
                    bp("The mantle is a sheet of tissue that hangs from the dorsal body wall and encloses the space between itself and the body, the mantle cavity. This cavity is of great importance. In most molluscs it houses the gills, called ctenidia, which are used for gas exchange and in some groups also for feeding, and it receives the openings of the gut, the kidneys and the gonads. Water or air is passed through the cavity, so that the gills are ventilated, and the wastes and the gametes are carried out. The cavity is thus a place where respiration, excretion and reproduction meet." to
                        "மேண்டில் முதுகுப்புற உடற்சுவரிலிருந்து தொங்கி தனக்கும் உடலுக்கும் இடையிலான இடமான மேண்டில் குழியை மூடும் திசுத் தகடு. இக்குழு மிக முக்கியமானது. பெரும்பாலான மெல்லுடலிகளில் வாயுப் பரிமாற்றத்துக்கும் சில குழுக்களில் உண்ணுதலுக்கும் பயன்படும் டீனிடியா எனும் செவுள்களை இது கொண்டுள்ளது; குடல், சிறுநீரகங்கள், இனப்பெருக்க சுரப்பிகளின் துளைகளையும் ஏற்கிறது. நீர் அல்லது காற்று குழி வழியே செலுத்தப்படுவதால் செவுள்கள் காற்றோட்டம் பெறுகின்றன; கழிவுகளும் கேமீட்டுகளும் வெளியே கொண்டு செல்லப்படுகின்றன. எனவே குழி சுவாசம், கழிவு நீக்கம், இனப்பெருக்கம் சந்திக்கும் இடம்."),
                    bp("The mantle also secretes the shell, which is made mostly of calcium carbonate laid down in a protein framework. The shell has typically three layers: an outer organic layer, the periostracum, a middle layer of calcium carbonate in a prismatic arrangement and an inner layer, often pearly, the nacre. The shell grows by additions at the edge of the mantle, and so it records growth. The shell is an optional product and not a defining feature of the whole phylum: it is a single piece in the gastropods, two valves in the bivalves, eight plates in the chitons, an internal reduced structure in the squids and absent in the octopuses and the slugs." to
                        "மேண்டில் ஓட்டையும் சுரக்கிறது; அது பெரும்பாலும் புரத அமைப்பில் படிந்த கால்சியம் கார்பனேட்டால் ஆனது. ஓட்டுக்கு பொதுவாக மூன்று அடுக்குகள்: வெளிக் கரிம அடுக்கான பெரியாஸ்டிராகம், பட்டக அமைப்பில் கால்சியம் கார்பனேட்டின் நடு அடுக்கு, பெரும்பாலும் முத்து போன்ற நாக்கர் உள் அடுக்கு. ஓடு மேண்டில் விளிம்பில் சேர்ப்பதால் வளர்கிறது; எனவே வளர்ச்சியைப் பதிவு செய்கிறது. ஓடு விருப்பப் பொருள்; முழுத் தொகுதியின் வரையறுக்கும் பண்பு அல்ல: இரைப்பைக்காலிகளில் ஒரே துண்டு, இருவோட்டுடலிகளில் இரு ஓடுகள், கைட்டான்களில் எட்டு தகடுகள், கணவாய்களில் உள் குறைந்த அமைப்பு, ஆக்டோபஸ்கள், ஸ்லக்குகளில் இல்லை."),
                    bdef("Mantle cavity", "மேண்டில் குழி", "The space between the mantle and the body, containing the gills and the openings of the main systems." to
                        "மேண்டிலுக்கும் உடலுக்கும் இடையிலான இடம்; செவுள்களும் முக்கிய மண்டலங்களின் துளைகளும் அடங்கியது."),
                    bdef("Ctenidium", "டீனிடியம்", "A gill of a mollusc, lying in the mantle cavity." to
                        "மேண்டில் குழியில் அமைந்த மெல்லுடலியின் செவுள்."),
                    bdef("Periostracum", "பெரியாஸ்டிராகம்", "The outer organic layer of the shell of a mollusc." to
                        "மெல்லுடலி ஓட்டின் வெளிக் கரிம அடுக்கு."),
                    bdef("Nacre", "நாக்கர்", "The pearly inner layer of a mollusc shell, made of thin plates of calcium carbonate." to
                        "கால்சியம் கார்பனேட்டின் மெல்லிய தகடுகளால் ஆன, மெல்லுடலி ஓட்டின் முத்து போன்ற உள் அடுக்கு."),
                )),
            bsec("radula", "The radula and feeding", "ராடுலாவும் உண்ணுதலும்",
                blocks = listOf(
                    bp("The radula is a ribbon of chitin bearing many rows of small teeth, which lies in the floor of the buccal cavity and is supported by a cartilaginous organ, the odontophore. It is moved forwards and backwards over the odontophore, so that it rasps or scrapes the food and draws it into the mouth, like a file, and the teeth are worn away at the front and replaced by new ones formed at the back. The radula is a character of the phylum, and is absent in the bivalves, which are filter feeders." to
                        "ராடுலா பல வரிசைச் சிறு பற்களைத் தாங்கும் கைட்டின் ரிப்பன்; வாய்க்குழியின் தரையில் அமைந்து குருத்தெலும்பு உறுப்பான ஓடோன்டோஃபோரால் தாங்கப்படுகிறது. அது ஓடோன்டோஃபோர் மீது முன்னும் பின்னும் நகர்த்தப்படுவதால் அரம் போல உணவைச் சுரண்டி வாய்க்குள் இழுக்கிறது; முன் பற்கள் தேய்ந்து பின்னால் உருவாகும் புதியவை இடம் பெறுகின்றன. ராடுலா தொகுதியின் பண்பு; வடிகட்டி உண்ணிகளான இருவோட்டுடலிகளில் இல்லை."),
                    bp("The radula differs with the diet. In the herbivorous snails it has many small teeth and scrapes algae from rocks. In the carnivorous snails it has fewer, stronger teeth, and some, like the cone shells, use a single modified tooth as a harpoon. In the cephalopods the radula is accompanied by a pair of horny jaws, forming a beak, which tears the prey. The student should say that the radula is a rasping organ and not a grinding one, and should note that it is not the same as the gastric mill of a crustacean." to
                        "ராடுலா உணவுக்கு ஏற்ப மாறுகிறது. தாவரவுண்ணி நத்தைகளில் பாறைகளிலிருந்து பாசிகளைச் சுரண்டும் பல சிறு பற்கள் உள்ளன. ஊனுண்ணி நத்தைகளில் குறைவான, வலிமையான பற்கள் உள்ளன; கோன் நத்தைகள் போன்ற சில மாற்றப்பட்ட ஒரு பல்லை ஈட்டியாகப் பயன்படுத்துகின்றன. தலைகாலிகளில் ராடுலாவுடன் இரையைக் கிழிக்கும் அலகை உருவாக்கும் ஓர் இணை கொம்புத் தாடைகள் உள்ளன. ராடுலா அரைக்கும் உறுப்பு அல்ல, சுரண்டும் உறுப்பு எனவும், ஓட்டுடலியின் இரைப்பை அரவையுடன் ஒன்றல்ல எனவும் மாணவர் கூற வேண்டும்."),
                    bdef("Radula", "ராடுலா", "A chitinous ribbon bearing rows of small teeth, used by molluscs to scrape or cut food." to
                        "உணவைச் சுரண்ட அல்லது வெட்ட மெல்லுடலிகள் பயன்படுத்தும், சிறு பற்களின் வரிசைகளைத் தாங்கும் கைட்டின் ரிப்பன்."),
                    bdef("Odontophore", "ஓடோன்டோஃபோர்", "The cartilaginous organ over which the radula is moved." to
                        "ராடுலா மேல் நகர்த்தப்படும் குருத்தெலும்பு உறுப்பு."),
                    bdef("Filter feeder", "வடிகட்டி உண்ணி", "An animal that feeds by straining small particles from water." to
                        "நீரிலிருந்து சிறு துகள்களை வடிகட்டி உண்ணும் விலங்கு."),
                )),
            bsec("foot", "The foot and its modifications", "பாதமும் அதன் மாற்றங்களும்",
                blocks = listOf(
                    bp("The molluscan foot is a muscular region of the ventral body wall, and it has the same basic position in all the classes, but is modified for different modes of movement and feeding. The variation shows the modification of one body region and not the presence of unrelated structures." to
                        "மெல்லுடலிப் பாதம் வயிற்றுப்புற உடற்சுவரின் தசைப் பகுதி; எல்லா வகுப்புகளிலும் ஒரே அடிப்படை இடம்; ஆனால் வெவ்வேறு இயக்க, உண்ணும் முறைகளுக்கு மாற்றப்படுகிறது. வேறுபாடு ஒரு உடல் பகுதியின் மாற்றத்தைக் காட்டுகிறது; தொடர்பற்ற அமைப்புகளின் இருப்பை அல்ல."),
                    btable("Modifications of the molluscan foot", "மெல்லுடலிப் பாதத்தின் மாற்றங்கள்",
                        listOf("Class" to "வகுப்பு", "Form of the foot" to "பாதத்தின் வடிவம்", "Use" to "பயன்"),
                        listOf(
                            listOf("Gastropoda" to "இரைப்பைக்காலி", "Broad, flat creeping sole" to "அகன்ற தட்டை ஊரும் அடிப்பகுதி", "Creeping by muscular waves aided by mucus" to "சளியின் உதவியுடன் தசை அலைகளால் ஊர்தல்"),
                            listOf("Bivalvia" to "இருவோட்டுடலி", "Laterally compressed, wedge or hatchet shaped" to "பக்கவாட்டில் அழுத்தப்பட்ட, ஆப்பு அல்லது கோடாரி வடிவம்", "Burrowing; some make byssus threads for attachment" to "துளையிடல்; சில பிடிப்புக்காக பிஸ்ஸஸ் இழைகளைச் செய்கின்றன"),
                            listOf("Cephalopoda" to "தலைகாலி", "Reorganised into arms or tentacles and a funnel" to "கைகள் அல்லது உணர்கொம்புகளாகவும் புனலாகவும் மறுஒழுங்கு", "Grasping prey; jet propulsion by the funnel" to "இரையைப் பிடித்தல்; புனலால் ஜெட் உந்துதல்"),
                            listOf("Scaphopoda" to "ஸ்காஃபோபோடா", "Conical" to "கூம்பு வடிவம்", "Burrowing" to "துளையிடல்"),
                            listOf("Polyplacophora" to "பாலிபிளாகோஃபோரா", "Broad, flat, sucker-like" to "அகன்ற, தட்டை, உறிஞ்சி போன்றது", "Clinging to rock" to "பாறையில் ஒட்டுதல்"),
                        )),
                    bdef("Byssus", "பிஸ்ஸஸ்", "Threads secreted by a gland in the foot of some bivalves, used for attachment." to
                        "சில இருவோட்டுடலிகளின் பாதத்திலுள்ள சுரப்பி சுரக்கும், பிடிப்புக்குப் பயன்படும் இழைகள்."),
                    bdef("Funnel (siphon)", "புனல்", "In a cephalopod, the tube through which water is expelled from the mantle cavity in jet propulsion." to
                        "தலைகாலியில், ஜெட் உந்துதலில் மேண்டில் குழியிலிருந்து நீர் வெளியேற்றப்படும் குழாய்."),
                )),
            bsec("systems", "Gut, excretion, nerves and development", "குடல், கழிவு நீக்கம், நரம்புகள், வளர்ச்சி",
                blocks = listOf(
                    bp("The alimentary canal is complete. The mouth leads to a buccal cavity with the radula, then an oesophagus, a stomach into which the ducts of a large digestive gland open, and a coiled intestine that ends at the anus in or near the mantle cavity. The digestive gland, often called the liver, secretes enzymes, absorbs the digested food and stores reserves. In the gastropods the intestine is bent so that the anus lies near the head, a consequence of torsion that is discussed in a later chapter. In bivalves the stomach contains a rod of mucus and enzyme, the crystalline style, which is rotated and releases enzymes into the stomach." to
                        "செரிமானக் குழாய் முழுமையானது. வாய் ராடுலாவுடன் வாய்க்குழிக்கும், பின் உணவுக்குழல், பெரிய செரிமானச் சுரப்பியின் நாளங்கள் திறக்கும் இரைப்பை, மேண்டில் குழிக்குள் அல்லது அருகே மலவாயில் முடியும் சுருண்ட குடலுக்கும் செல்கிறது. கல்லீரல் எனப் பெரும்பாலும் அழைக்கப்படும் செரிமானச் சுரப்பி நொதிகளைச் சுரந்து, செரித்த உணவை உறிஞ்சி, சேமிப்புகளை வைக்கிறது. இரைப்பைக்காலிகளில் முறுக்கலின் விளைவாக மலவாய் தலைக்கு அருகில் இருக்கும்படி குடல் வளைந்துள்ளது; இது பிந்தைய அத்தியாயத்தில் விவாதிக்கப்படுகிறது. இருவோட்டுடலிகளின் இரைப்பையில் சுழற்றப்பட்டு இரைப்பைக்குள் நொதிகளை வெளியிடும் சளியும் நொதியுமான தண்டு, படிகத் தண்டு (கிரிஸ்டலின் ஸ்டைல்) உள்ளது."),
                    bp("The excretory organs are the kidneys, which are one or more pairs of tubes. Each opens by a funnel into the pericardial cavity, which is the remnant of the coelom, and by a pore into the mantle cavity, and the waste is passed out into the water or the air. The nervous system consists of paired ganglia, the cerebral ganglia above the gut, the pedal ganglia in the foot and the visceral ganglia, joined by connectives, with a ring around the oesophagus. In the active cephalopods these are concentrated into a brain. The sense organs include tentacles, eyes of different complexity, statocysts for balance and the osphradium, a chemical sense organ that tests the water entering the mantle cavity." to
                        "கழிவு உறுப்புகள் ஒன்று அல்லது அதற்கு மேற்பட்ட இணைக் குழாய்களான சிறுநீரகங்கள். ஒவ்வொன்றும் உடற்குழியின் எச்சமான பெரிகார்டியல் குழிக்குள் புனலாலும் மேண்டில் குழிக்குள் துளையாலும் திறக்கிறது; கழிவு நீர் அல்லது காற்றில் வெளியேறுகிறது. நரம்பு மண்டலம் உணவுக்குழலைச் சுற்றி வளையத்துடன், இணைப்பிகளால் இணைந்த குடலுக்கு மேலுள்ள பெருமூளை நரம்பு செல்திரள்கள், பாதத்திலுள்ள பாத நரம்பு செல்திரள்கள், உள்ளுறுப்பு நரம்பு செல்திரள்கள் என இணைத் திரள்களைக் கொண்டது. செயலூக்கமுள்ள தலைகாலிகளில் இவை மூளையாகச் செறிகின்றன. உணர்வு உறுப்புகளில் உணர்கொம்புகள், வெவ்வேறு சிக்கலான கண்கள், சமநிலைக்கான ஸ்டேட்டோசிஸ்ட்கள், மேண்டில் குழிக்குள் நுழையும் நீரைச் சோதிக்கும் வேதி உணர் உறுப்பான ஆஸ்ஃப்ரேடியம் அடங்கும்."),
                    bp("The sexes are separate in most molluscs, though some gastropods are hermaphrodite. In marine forms the fertilised egg undergoes spiral cleavage and develops into a trochophore larva, which in most groups is followed by a veliger, with a shell and a ciliated velum used for swimming and feeding. Many freshwater and land forms have direct development, and some bivalves of fresh water, the unionids, have a special larva, the glochidium, which is a parasite on fish. The trochophore is one of the characters used to relate the molluscs to the annelids." to
                        "பெரும்பாலான மெல்லுடலிகளில் பால் தனி; சில இரைப்பைக்காலிகள் இருபால் உயிரிகள். கடல் வடிவங்களில் கருவுற்ற முட்டை சுருள் பிளவுக்குட்பட்டு ட்ரோகோபோர் லார்வாவாக வளர்கிறது; பெரும்பாலான குழுக்களில் ஓடும் நீந்துதல், உண்ணுதலுக்கான கசையிழை வெலமும் உடைய வெலிகர் தொடர்கிறது. பல நன்னீர், நில வடிவங்களுக்கு நேரடி வளர்ச்சி உண்டு; நன்னீர் இருவோட்டுடலிகளான யூனியோனிட்களுக்கு மீன்களில் ஒட்டுண்ணியான சிறப்பு லார்வா, குளோகிடியம் உண்டு. மெல்லுடலிகளை அனெலிடாக்களுடன் தொடர்புபடுத்தப் பயன்படும் பண்புகளில் ட்ரோகோபோர் ஒன்று."),
                    bdef("Crystalline style", "படிகத் தண்டு", "A rod of mucus and enzyme in the stomach of a bivalve that is rotated to release digestive enzymes." to
                        "செரிமான நொதிகளை வெளியிடச் சுழற்றப்படும், இருவோட்டுடலியின் இரைப்பையிலுள்ள சளியும் நொதியுமான தண்டு."),
                    bdef("Osphradium", "ஆஸ்ஃப்ரேடியம்", "A sense organ in the mantle cavity that tests the water or air entering it." to
                        "மேண்டில் குழிக்குள் நுழையும் நீர் அல்லது காற்றைச் சோதிக்கும் உணர்வு உறுப்பு."),
                    bdef("Glochidium", "குளோகிடியம்", "The parasitic larva of freshwater mussels of the unionid family, which attaches to fish." to
                        "மீன்களில் ஒட்டிக்கொள்ளும், யூனியோனிட் குடும்ப நன்னீர் சிப்பிகளின் ஒட்டுண்ணி லார்வா."),
                )),
            bsec("circulation", "Respiration, circulation and body fluid", "சுவாசம், சுற்றோட்டம், உடல் திரவம்",
                blocks = listOf(
                    bp("Most molluscs breathe with the ctenidia in the mantle cavity, and the movement of water over them is driven by cilia or, in cephalopods, by the muscular pumping of the mantle. Some land snails have lost the ctenidium and use the lining of the mantle cavity as a lung, and some molluscs breathe through the general surface of the mantle. The ctenidia of bivalves have become enlarged and also act as the apparatus for filtering food.",
                        "பெரும்பாலான மெல்லுடலிகள் மேண்டில் குழியிலுள்ள டீனிடியங்களால் சுவாசிக்கின்றன; அவற்றின் மேல் நீர் ஓட்டம் சிலியாவாலும், செபலோபாட்களில் மேண்டிலின் தசை இறைப்பாலும் நிகழ்கிறது. சில நில நத்தைகள் டீனிடியத்தை இழந்து மேண்டில் குழியின் உட்சுவரை நுரையீரலாகப் பயன்படுத்துகின்றன; சில மெல்லுடலிகள் மேண்டிலின் பொது மேற்பரப்பால் சுவாசிக்கின்றன. இருவோடுடையவற்றின் டீனிடியங்கள் பெரிதாகி உணவு வடிகட்டும் கருவியாகவும் செயல்படுகின்றன."),
                    bp("The circulation is open in most molluscs. The heart lies in a cavity around it, the pericardial cavity, which is the remnant of the coelom, and it usually has one ventricle and two auricles. Blood is pumped through arteries into sinuses, which are spaces among the organs, and returns to the heart through the gills. The blood commonly contains the copper pigment haemocyanin, which is blue when oxygenated, and not haemoglobin, although a few molluscs have haemoglobin. The cephalopods are the exception: their circulation is closed, with capillaries and extra hearts that drive blood through the gills, and this is related to their active mode of life.",
                        "பெரும்பாலான மெல்லுடலிகளில் சுற்றோட்டம் திறந்தது. இதயம் தன்னைச் சுற்றியுள்ள சீலோமின் எச்சமான பெரிகார்டியல் குழியில் உள்ளது; வழக்கமாக ஒரு வென்ட்ரிக்கிளும் இரு ஆரிக்கிள்களும் உண்டு. இரத்தம் தமனிகள் வழியே உறுப்புகளுக்கிடையிலான இடைவெளிகளான சைனஸ்களுக்குள் இறைக்கப்பட்டு, செவுள்கள் வழியே இதயத்துக்குத் திரும்புகிறது. இரத்தத்தில் பொதுவாக ஹீமோகுளோபின் அல்ல, ஆக்சிஜனேற்றம் அடைந்தால் நீல நிறமான செம்பு நிறமி ஹீமோசயனின் உள்ளது; சில மெல்லுடலிகளுக்கு ஹீமோகுளோபின் உண்டு. செபலோபாட்கள் விதிவிலக்கு: அவற்றின் சுற்றோட்டம் மூடியது; தந்துகிகளும் செவுள்கள் வழியே இரத்தத்தைச் செலுத்தும் கூடுதல் இதயங்களும் உள்ளன; இது அவற்றின் செயல்மிகு வாழ்க்கை முறையுடன் தொடர்புடையது."),
                    bdef("Haemocyanin", "ஹீமோசயனின்", "The copper-containing respiratory pigment found in the blood of many molluscs, blue when carrying oxygen." to
                        "பல மெல்லுடலிகளின் இரத்தத்தில் உள்ள, ஆக்சிஜனைக் கொண்டிருக்கும்போது நீலமாகும் செம்பு கொண்ட சுவாச நிறமி."),
                    bdef("Pericardial cavity", "பெரிகார்டியல் குழி", "The cavity around the heart of a mollusc, which is the main remnant of the true coelom." to
                        "மெல்லுடலியின் இதயத்தைச் சுற்றியுள்ள குழி; உண்மையான சீலோமின் முக்கிய எச்சம்."),
                )),
            bsec("pearl", "How a pearl is formed", "முத்து எவ்வாறு உருவாகிறது",
                blocks = listOf(
                    bp("A pearl is made of the same material as the inner layer of the shell, nacre, and is formed by the mantle. If a foreign body or a piece of tissue gets between the mantle and the shell, or into the tissue of the mantle, the cells of the mantle epithelium around it form a closed pocket called a pearl sac. The cells of the sac secrete layers of nacre, which build up around the body, and a pearl is the result. The quality of a pearl depends on its shape, its lustre, its size and its colour, and a good pearl is formed by many thin layers that are laid down slowly.",
                        "முத்து சிப்பியோட்டின் உள்ளடுக்கு பொருளான நாக்கரால் ஆனது; மேண்டிலால் உருவாக்கப்படுகிறது. அயலுடலோ திசுத் துண்டோ மேண்டிலுக்கும் ஓட்டுக்கும் இடையில் அல்லது மேண்டில் திசுவுக்குள் சென்றால், அதைச் சுற்றியுள்ள மேண்டில் எப்பிதீலிய செல்கள் முத்துப் பை எனும் மூடிய பையை உருவாக்குகின்றன. பையின் செல்கள் நாக்கர் அடுக்குகளைச் சுரந்து அவ்வுடலைச் சுற்றிப் படியச் செய்கின்றன; அதன் விளைவாக முத்து உருவாகிறது. முத்தின் தரம் வடிவம், பளபளப்பு, அளவு, நிறத்தைப் பொறுத்தது; நல்ல முத்து மெதுவாகப் படியும் பல மெல்லிய அடுக்குகளால் உருவாகிறது."),
                    bp("Natural pearls form by chance, but cultured pearls are produced deliberately by inserting a small bead of shell with a piece of mantle tissue from a donor oyster into the gonad or the mantle of a host oyster. The species used for pearl culture belong to the genus Pinctada, and the pearl oysters of the Gulf of Mannar have long been known for their pearl fishery. The question may be asked as a short note, and the three points to include are the source of the material (the mantle), the pearl sac and the nacre.",
                        "இயற்கை முத்துகள் தற்செயலாக உருவாகின்றன; வளர்ப்பு முத்துகள் நன்கொடை சிப்பியின் மேண்டில் திசுத் துண்டுடன் ஓட்டின் சிறு மணியை ஓம்புச் சிப்பியின் இனப்பெருக்க உறுப்பிலோ மேண்டிலிலோ செலுத்தி வேண்டுமென்றே உருவாக்கப்படுகின்றன. முத்து வளர்ப்புக்கான இனங்கள் பிங்க்டாடா பேரினத்தைச் சேர்ந்தவை; மன்னார் வளைகுடாவின் முத்துச் சிப்பிகள் முத்துக் குளிப்புக்குப் பழங்காலம் முதல் பெயர் பெற்றவை. இது சிறு குறிப்பாகக் கேட்கப்படலாம்; சேர்க்க வேண்டிய மூன்று புள்ளிகள் பொருளின் மூலம் (மேண்டில்), முத்துப் பை, நாக்கர்."),
                    bdef("Pearl sac", "முத்துப் பை", "The closed pocket of mantle epithelium that forms around an irritant and secretes the nacre of a pearl." to
                        "எரிச்சலூட்டும் பொருளைச் சுற்றி உருவாகி, முத்தின் நாக்கரைச் சுரக்கும் மேண்டில் எப்பிதீலிய மூடிய பை."),
                )),
            bsec("economic", "Economic importance of molluscs", "மெல்லுடலிகளின் பொருளாதார முக்கியத்துவம்",
                blocks = listOf(
                    bpoints("Useful molluscs", "பயனுள்ள மெல்லுடலிகள்",
                        "Food: oysters, mussels, clams, cockles, squids, cuttlefishes, octopuses and some snails are eaten in many parts of the world." to "உணவு: சிப்பிகள், மட்டிகள், கிளாம்கள், காக்கிள்கள், கணவாய், கட்டில்ஃபிஷ், ஆக்டோபஸ்கள், சில நத்தைகள் உலகின் பல பகுதிகளில் உண்ணப்படுகின்றன.",
                        "Pearls and mother-of-pearl: pearl oysters, and the shell material used for buttons and ornaments." to "முத்தும் முத்துச்சிப்பியும்: முத்துச் சிப்பிகள்; பொத்தான்கள், அணிகலன்களுக்குப் பயன்படும் ஓட்டுப் பொருள்.",
                        "Shell: used for lime, for ornament, for the manufacture of cement and as poultry grit." to "ஓடு: சுண்ணாம்பு, அலங்காரம், சிமெண்ட் தயாரிப்பு, கோழிக்கான சரளை.",
                        "Ink and dyes: the ink of cephalopods and the purple dye of some marine snails have been used." to "மையும் சாயமும்: செபலோபாட்களின் மையும் சில கடல் நத்தைகளின் ஊதாச் சாயமும் பயன்படுத்தப்பட்டுள்ளன.",
                        "Ecosystem services: filter-feeding bivalves clear the water, and molluscs are food for fishes, birds and other animals." to "சூழல்மண்டல சேவைகள்: வடிகட்டி உண்ணும் இருவோடுடையவை நீரைத் தெளிவாக்குகின்றன; மெல்லுடலிகள் மீன், பறவை, பிற விலங்குகளுக்கு உணவு.",
                    ),
                    bpoints("Harmful molluscs", "தீங்கு விளைவிக்கும் மெல்லுடலிகள்",
                        "Intermediate hosts: freshwater snails carry the larvae of liver flukes and blood flukes." to "இடைநிலை ஓம்புயிர்கள்: நன்னீர் நத்தைகள் கல்லீரல் புழு, இரத்தப் புழுக்களின் லார்வாக்களைக் கொண்டுள்ளன.",
                        "Pests of crops and gardens: land snails and slugs, and the apple snail Pila in rice fields." to "பயிர், தோட்டப் பூச்சிகள்: நில நத்தைகள், ஓடற்ற நத்தைகள் (ஸ்லக்); நெல் வயல்களில் ஆப்பிள் நத்தை பைலா.",
                        "Borers: the shipworm Teredo, a bivalve, bores into wooden piers, boats and piles." to "துளைப்பிகள்: இருவோடுடைய கப்பல்புழு டெரிடோ மரத் துறைமுகங்கள், படகுகள், தூண்களைத் துளைக்கிறது.",
                        "Fouling: mussels and other bivalves settle on the hulls of ships and in pipes and intake channels." to "படிவு: மட்டிகள், பிற இருவோடுடையவை கப்பல் அடிப்பகுதி, குழாய்கள், உட்கொள்ளும் வாய்க்கால்களில் படிகின்றன.",
                        "Poisons: shellfish may accumulate toxins from algae, and the cone snails produce potent venoms." to "நஞ்சுகள்: ஓட்டு உயிர்கள் பாசிகளிலிருந்து நச்சுகளைச் சேர்க்கலாம்; கூம்பு நத்தைகள் வலிமையான நஞ்சை உருவாக்குகின்றன.",
                    ),
                    bp("In an essay on the economic importance of molluscs, the useful and harmful groups should be listed in separate columns, and the examples should be named at the level of the species or class. The student should say, in the case of the shipworm, that it is a bivalve and not a worm, since the name is a frequent source of mistakes.",
                        "மெல்லுடலிகளின் பொருளாதார முக்கியத்துவம் பற்றிய கட்டுரையில் பயனுள்ள, தீங்கு விளைவிக்கும் குழுக்களைத் தனிப்பத்திகளில் பட்டியலிட வேண்டும்; எடுத்துக்காட்டுகள் இனம் அல்லது வகுப்பு மட்டத்தில் பெயரிடப்பட வேண்டும். கப்பல்புழு புழு அல்ல, இருவோடுடையது என்று மாணவர் கூற வேண்டும்; பெயர் அடிக்கடி பிழைகளுக்கு மூலமாகிறது."),
                    bdef("Fouling organism", "படியும் உயிரி", "An organism that settles and grows on submerged surfaces, such as ship hulls and pipes, and interferes with their use." to
                        "கப்பல் அடிப்பகுதி, குழாய்கள் போன்ற மூழ்கிய பரப்புகளில் படிந்து வளர்ந்து அவற்றின் பயன்பாட்டில் இடையூறு செய்யும் உயிரி."),
                )),
            bsec("classification", "Classification", "வகைப்பாடு",
                blocks = listOf(
                    bp("Four classes are used in this syllabus: Gastropoda, Bivalvia, Cephalopoda and Polyplacophora. The Scaphopoda, the tusk shells, are also a class but are less often studied. A student must be able to give a diagnosis of each class and an example." to
                        "இப்பாடத்திட்டத்தில் நான்கு வகுப்புகள் பயன்படுகின்றன: காஸ்ட்ரோபோடா, பைவால்வியா, செபலோபோடா, பாலிபிளாகோஃபோரா. தந்த ஓடுகளான ஸ்காஃபோபோடாவும் ஒரு வகுப்பு; ஆனால் குறைவாகக் கற்கப்படுகிறது. மாணவர் ஒவ்வொரு வகுப்பின் அடையாளத்தையும் எடுத்துக்காட்டையும் தர முடிய வேண்டும்."),
                    btable("Four classes of Mollusca", "மொலஸ்காவின் நான்கு வகுப்புகள்",
                        listOf("Class" to "வகுப்பு", "Shell" to "ஓடு", "Head and radula" to "தலையும் ராடுலாவும்", "Other features" to "பிற பண்புகள்", "Examples" to "எடுத்துக்காட்டுகள்"),
                        listOf(
                            listOf("Gastropoda" to "காஸ்ட்ரோபோடா", "Single, usually coiled" to "ஒற்றை, பொதுவாகச் சுருண்டது", "Distinct head with tentacles and eyes; radula" to "உணர்கொம்புகள், கண்களுடன் தெளிவான தலை; ராடுலா", "Torsion; asymmetrical body" to "முறுக்கல்; சமச்சீரற்ற உடல்", "Pila, Helix, Limax" to "பைலா, ஹெலிக்ஸ், லிமாக்ஸ்"),
                            listOf("Bivalvia" to "பைவால்வியா", "Two valves with a dorsal hinge" to "முதுகுப்புற இணைப்புடன் இரு ஓடுகள்", "Head reduced; no radula" to "தலை குறைந்தது; ராடுலா இல்லை", "Filter feeding with gills; laterally compressed" to "செவுள்களால் வடிகட்டி உண்ணுதல்; பக்கவாட்டில் அழுத்தம்", "Unio, Pinctada, Mytilus" to "யூனியோ, பிங்க்டாடா, மைடிலஸ்"),
                            listOf("Cephalopoda" to "செபலோபோடா", "Internal, reduced, or external in Nautilus" to "உள், குறைந்த; நாட்டிலஸில் வெளிப்புறம்", "Well-developed head, eyes, beak and radula" to "நன்கு வளர்ந்த தலை, கண்கள், அலகு, ராடுலா", "Closed circulation; arms; funnel" to "மூடிய இரத்த ஓட்டம்; கைகள்; புனல்", "Sepia, Loligo, Octopus" to "செபியா, லோலிகோ, ஆக்டோபஸ்"),
                            listOf("Polyplacophora" to "பாலிபிளாகோஃபோரா", "Eight overlapping dorsal plates" to "எட்டு மேற்பொருந்திய முதுகுத் தகடுகள்", "Head indistinct; radula present" to "தலை தெளிவற்றது; ராடுலா உண்டு", "Broad foot; many pairs of gills" to "அகன்ற பாதம்; பல இணை செவுள்கள்", "Chiton" to "கைட்டான்"),
                        )),
                    bp("The circulation is a point to be remembered. It is open in most molluscs, with a heart that lies in a pericardial cavity and normally consists of one or two auricles and a single ventricle, and the blood bathes the tissues in sinuses. In the cephalopods, however, the blood is retained in vessels, and the system is closed, which is associated with the active life of these animals. The class is also exceptional in having the most elaborate brain and eyes among invertebrates." to
                        "இரத்த ஓட்டம் நினைவில் கொள்ளத்தக்க புள்ளி. பெரும்பாலான மெல்லுடலிகளில் இது திறந்தது; இதயம் பெரிகார்டியல் குழியில் அமைந்து வழக்கமாக ஒன்று அல்லது இரண்டு ஆரிக்கிள்களும் ஒற்றை வென்ட்ரிக்கிளும் கொண்டது; இரத்தம் சைனஸ்களில் திசுக்களை நனைக்கிறது. ஆனால் தலைகாலிகளில் இரத்தம் நாளங்களில் தக்கவைக்கப்படுகிறது; மண்டலம் மூடியது; இது இவ்விலங்குகளின் செயலூக்க வாழ்வுடன் தொடர்புடையது. முதுகெலும்பிலிகளில் மிகச் சிக்கலான மூளையும் கண்களும் இருப்பதிலும் இவ்வகுப்பு விதிவிலக்கு."),
                    bnote("Shell is optional", "ஓடு விருப்பம்",
                        "Do not define a mollusc as an animal with a shell. Octopus and slugs have no external shell, and the defining features are the body plan, the mantle and the radula." to
                            "மெல்லுடலியை ஓடு உள்ள விலங்கு எனக் கூற வேண்டாம். ஆக்டோபஸ், ஸ்லக்குகளுக்கு வெளி ஓடு இல்லை; வரையறுக்கும் பண்புகள் உடல் அமைப்பு, மேண்டில், ராடுலா."),
                )),
        ),
        summary = listOf(
            "Molluscs are triploblastic protostomes with a soft, unsegmented body: head, muscular foot, visceral mass and mantle." to "மெல்லுடலிகள் மென்மையான, கண்டமற்ற உடல்: தலை, தசைப் பாதம், உள்ளுறுப்புத் திரள், மேண்டில் உடைய மூவடுக்கு புரோட்டோஸ்டோம்கள்.",
            "The mantle forms the mantle cavity with the gills, and secretes the shell in many species." to "மேண்டில் செவுள்களுடன் மேண்டில் குழியை உருவாக்கி, பல இனங்களில் ஓட்டைச் சுரக்கிறது.",
            "The shell is an optional product: it varies from one piece to two valves, eight plates or an internal remnant." to "ஓடு விருப்பப் பொருள்: ஒரு துண்டு, இரு ஓடுகள், எட்டு தகடுகள் அல்லது உள் எச்சமாக மாறுகிறது.",
            "The radula is a rasping ribbon of teeth and is absent in bivalves." to "ராடுலா சுரண்டும் பற்களின் ரிப்பன்; இருவோட்டுடலிகளில் இல்லை.",
            "The coelom is reduced, the haemocoel is the main cavity, and circulation is open except in cephalopods." to "உடற்குழி குறைந்தது; ஹீமோசீல் முதன்மைக் குழி; தலைகாலிகள் தவிர இரத்த ஓட்டம் திறந்தது.",
            "The foot is modified as a creeping sole, a burrowing wedge, arms and funnel, or a clinging sole." to "பாதம் ஊரும் அடிப்பகுதி, துளையிடும் ஆப்பு, கைகள் - புனல் அல்லது ஒட்டும் அடிப்பகுதியாக மாற்றப்படுகிறது.",
            "The four classes are Gastropoda, Bivalvia, Cephalopoda and Polyplacophora." to "நான்கு வகுப்புகள் காஸ்ட்ரோபோடா, பைவால்வியா, செபலோபோடா, பாலிபிளாகோஃபோரா.",
        ),
        checks = listOf(
            bqc("What is a radula?", "ராடுலா என்றால் என்ன?",
                "A toothed chitinous ribbon for rasping food; absent in bivalves.", "உணவைச் சுரண்டும் பற்களுடைய கைட்டின் ரிப்பன்; இருவோட்டுடலிகளில் இல்லை."),
            bqc("Name four classes of Mollusca used in this paper.", "இத்தாளில் பயன்படும் மொலஸ்காவின் நான்கு வகுப்புகளைப் பெயரிடுக.",
                "Gastropoda, Bivalvia, Cephalopoda and Polyplacophora.", "காஸ்ட்ரோபோடா, பைவால்வியா, செபலோபோடா, பாலிபிளாகோஃபோரா."),
            bqc("Which class has a closed circulation?", "எந்த வகுப்புக்கு மூடிய இரத்த ஓட்டம் உண்டு?",
                "The Cephalopoda.", "செபலோபோடா."),
            bqc("How is the foot of a cephalopod modified?", "தலைகாலியின் பாதம் எவ்வாறு மாற்றப்படுகிறது?",
                "Part forms the arms or tentacles, and part forms the funnel for jet propulsion.", "ஒரு பகுதி கைகள் அல்லது உணர்கொம்புகளாகவும் மற்றொன்று ஜெட் உந்துதல் புனலாகவும் அமைகிறது."),
        ),
    )
}
