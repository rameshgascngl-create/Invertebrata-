package com.gasczoology.invertebratelab.data

/** Book-style chapter: Phylum Echinodermata, general characters and classification. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object EchinodermataBookChapter {
    val chapter = bchapter(
        lessonId = "u5-echinodermata",
        title = "Phylum Echinodermata: Characters and Classification" to "முட்தோலித் தொகுதி: பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State the general characters of the phylum Echinodermata." to "முட்தோலித் தொகுதியின் பொதுப் பண்புகளைக் கூற.",
            "Explain why adult pentaradial symmetry is secondary." to "முதிர்ந்த ஐந்தாரைச் சமச்சீர் இரண்டாம் நிலை என்பதை விளக்க.",
            "Describe the endoskeleton and the water-vascular system in outline." to "உட்கூடு, நீர்-நாள அமைப்பை சுருக்கமாக விவரிக்க.",
            "Classify the phylum into its five living classes with examples." to "தொகுதியை ஐந்து வாழும் வகுப்புகளாக எடுத்துக்காட்டுகளுடன் வகைப்படுத்த.",
            "Summarise the evidence for deuterostome affinity." to "இரண்டாம்வாய் உறவுக்கான சான்றுகளைச் சுருக்கிக் கூற.",
        ),
        introduction = listOf(
            "The echinoderms, which include the starfishes, brittle stars, sea urchins, sea cucumbers and sea lilies, are among the most distinctive of animals. They are exclusively marine, they have a skeleton of calcareous plates beneath the skin, and they move on hydraulic tube feet that belong to a system found in no other phylum. The adults are built on a five-part radial plan, and yet they are related to the vertebrates and not to the radially symmetrical jellyfishes, which is a fact that the larvae reveal." to
                "நட்சத்திர மீன்கள், பிரிட்டில் நட்சத்திரங்கள், கடல் முள்ளெலிகள், கடல் வெள்ளரிகள், கடல் அல்லிகளை உள்ளடக்கிய முட்தோலிகள் விலங்குகளில் மிகத் தனித்தன்மையானவை. அவை முழுவதும் கடல்வாழ்; தோலுக்கு அடியில் சுண்ணாம்புத் தகடுகளாலான கூடு கொண்டவை; வேறெந்தத் தொகுதியிலும் காணப்படாத அமைப்பைச் சேர்ந்த நீரழுத்தக் குழாய்ப்பாதங்களில் நகர்கின்றன. முதிர்ந்தவை ஐந்துபகுதி ஆரைத் திட்டத்தில் அமைந்திருந்தாலும், ஆரைச் சமச்சீருடைய ஜெல்லிமீன்களுடன் அல்ல, முதுகெலும்பிகளுடன் உறவுடையவை; இதை லார்வாக்கள் வெளிப்படுத்துகின்றன.",
            "This chapter gives the characters of the phylum, the reasoning that links them to the deuterostomes, and a classification down to the level of the class. The detailed types, Asterias, the water-vascular system, the larvae, and the fossil record, are treated in the chapters that follow, and this chapter prepares the framework for them." to
                "இவ்வத்தியாயம் தொகுதியின் பண்புகளையும் அவற்றை இரண்டாம்வாயிகளுடன் இணைக்கும் காரணத்தையும் வகுப்பு நிலை வரையான வகைப்பாட்டையும் தருகிறது. அஸ்டீரியாஸ் வகை, நீர்-நாள அமைப்பு, லார்வாக்கள், புதைபடிவப் பதிவு போன்ற விரிவான பகுதிகள் தொடரும் அத்தியாயங்களில் கையாளப்படுகின்றன; இவ்வத்தியாயம் அவற்றுக்கான கட்டமைப்பைத் தயார் செய்கிறது.",
        ),
        sections = listOf(
            bsec("characters", "General characters", "பொதுப் பண்புகள்",
                blocks = listOf(
                    bpoints("The general characters of Echinodermata", "முட்தோலிகளின் பொதுப் பண்புகள்",
                        "Exclusively marine, triploblastic, coelomate deuterostomes." to "முழுவதும் கடல்வாழ், மூவடுக்கு, உண்மையான உடற்குழியுடைய இரண்டாம்வாயிகள்.",
                        "Adults usually show secondary pentaradial symmetry; the early larvae are bilaterally symmetrical." to "முதிர்ந்தவை பொதுவாக இரண்டாம் நிலை ஐந்தாரைச் சமச்சீர் காட்டுகின்றன; ஆரம்ப லார்வாக்கள் இருபக்கச் சமச்சீருடையவை.",
                        "A mesodermal endoskeleton of calcareous ossicles supports the body and may bear spines and pedicellariae." to "மெசோடெர்மிலிருந்து உருவாகும் சுண்ணாம்பு ஆஸிக்கிள்களின் உட்கூடு உடலைத் தாங்குகிறது; முட்களும் பெடிசல்லேரியாக்களும் இருக்கலாம்.",
                        "A unique water-vascular system with tube feet functions in locomotion, attachment, feeding and gas exchange." to "குழாய்ப்பாதங்களுடன் கூடிய தனித்துவமான நீர்-நாள அமைப்பு இயக்கம், பற்றுதல், உணவெடுப்பு, வாயுப் பரிமாற்றத்தில் செயல்படுகிறது.",
                        "No distinct head; the nervous system is a nerve ring with radial nerves." to "தெளிவான தலை இல்லை; நரம்பு அமைப்பு நரம்பு வளையமும் கதிர் நரம்புகளும் ஆகும்.",
                        "Strong powers of regeneration, and predominantly external fertilisation." to "வலிமையான மீளுருவாக்கத் திறனும் பெரும்பாலும் வெளிப்புறக் கருவுறுதலும்.",
                    ),
                    bp("Several of these characters are best learned as a connected picture. The animal is a coelomate, and the coelom is divided into several parts during development, one of which gives rise to the water-vascular system. The skeleton is an endoskeleton, because it lies within the body wall, beneath an epidermis that covers it, and yet it can project through the skin as spines. A circulatory system exists in a reduced form, as a haemal system in channels of the coelomic spaces, and excretion and gas exchange are carried out by the skin, the tube feet and in some groups special structures, without a specialised kidney." to
                        "இவற்றில் பல பண்புகள் இணைந்த சித்திரமாகக் கற்கப்படுவது சிறந்தது. விலங்கு உண்மையான உடற்குழியுடையது; வளர்ச்சியில் உடற்குழி பல பகுதிகளாகப் பிரிகிறது; அவற்றில் ஒன்று நீர்-நாள அமைப்பைத் தருகிறது. கூடு உடற்சுவருக்குள், அதை மூடும் மேற்றோலுக்கு அடியில் அமைவதால் உட்கூடு; ஆனாலும் முட்களாகத் தோலைத் துளைத்து வெளிவரலாம். சுற்றோட்ட அமைப்பு உடற்குழி இடைவெளிகளின் வழிகளில் ஹீமல் அமைப்பாகக் குறைந்த வடிவில் உள்ளது; வெளியேற்றமும் வாயுப் பரிமாற்றமும் சிறப்பு சிறுநீரகம் இன்றி தோல், குழாய்ப்பாதங்கள், சில குழுக்களில் சிறப்பு அமைப்புகளால் நடைபெறுகின்றன."),
                    bdef("Echinoderm", "முட்தோலி", "A marine deuterostome with a calcareous endoskeleton, a water-vascular system and adult pentaradial symmetry." to
                        "சுண்ணாம்பு உட்கூடு, நீர்-நாள அமைப்பு, முதிர்ந்த ஐந்தாரைச் சமச்சீர் கொண்ட கடல்வாழ் இரண்டாம்வாயி."),
                    bdef("Endoskeleton", "உட்கூடு", "A skeleton that lies within the body, here made of mesodermal calcareous ossicles." to
                        "உடலுக்குள் அமையும் கூடு; இங்கு மெசோடெர்மிலிருந்து உருவாகும் சுண்ணாம்பு ஆஸிக்கிள்கள்."),
                    bdef("Ossicle", "ஆஸிக்கிள்", "A small calcareous plate or piece of the echinoderm skeleton." to
                        "முட்தோலிக் கூட்டின் சிறு சுண்ணாம்புத் தகடு அல்லது துண்டு."),
                    bdef("Pedicellaria", "பெடிசல்லேரியா", "A small pincer-like structure on the surface of some echinoderms that keeps the surface clean and may protect it." to
                        "சில முட்தோலிகளின் மேற்பரப்பில் மேற்பரப்பைத் தூய்மையாக வைத்து பாதுகாக்கக்கூடிய சிறு இடுக்கி போன்ற அமைப்பு."),
                    bdef("Coelomate", "உண்மையான உடற்குழியுடையது", "An animal with a true coelom, a cavity lined by mesoderm." to
                        "மெசோடெர்ம் வரிசையிட்ட உடற்குழியான உண்மையான உடற்குழி கொண்ட விலங்கு."),
                )),
            bsec("symmetry", "Pentaradial symmetry and the bilateral larva", "ஐந்தாரைச் சமச்சீரும் இருபக்க லார்வாவும்",
                blocks = listOf(
                    bp("The adult echinoderm can be divided into similar parts around a central axis, usually five, and this is described as pentaradial symmetry. The student must understand that this symmetry is secondary. The larvae are bilaterally symmetrical, and the radial form arises during metamorphosis, when the bilateral larva is extensively reorganised into a pentaradial juvenile. The fossil record and the larvae both suggest that the ancestors of the echinoderms were bilateral animals, and that radial symmetry was acquired along with a sedentary or slow way of life." to
                        "முதிர்ந்த முட்தோலியை மையச் சுற்றி, வழக்கமாக ஐந்து, ஒத்த பகுதிகளாகப் பிரிக்கலாம்; இது ஐந்தாரைச் சமச்சீர் எனப்படுகிறது. இச்சமச்சீர் இரண்டாம் நிலை என்பதை மாணவர் புரிந்துகொள்ள வேண்டும். லார்வாக்கள் இருபக்கச் சமச்சீருடையவை; உருமாற்றத்தின்போது இருபக்க லார்வா ஐந்தாரை இளவிலங்காக விரிவாக மறுசீரமைக்கப்படும்போது ஆரை வடிவம் தோன்றுகிறது. முட்தோலிகளின் மூதாதையர் இருபக்க விலங்குகள் என்றும் ஆரைச் சமச்சீர் அமர்ந்த அல்லது மெதுவான வாழ்க்கை முறையுடன் பெறப்பட்டது என்றும் புதைபடிவப் பதிவும் லார்வாக்களும் சுட்டுகின்றன."),
                    bp("This also separates echinoderm radial symmetry from that of cnidarians. In a jellyfish or a sea anemone the radial plan is primary, belongs to a diploblastic animal with a single opening to the gut, and is not preceded by a bilateral stage in the same way. The fivefold arrangement of the echinoderm is a secondary radial plan imposed on a triploblastic, coelomate, bilateral ancestry. A comparison between the two kinds of radiality is a favourite examination question." to
                        "இது முட்தோலிகளின் ஆரைச் சமச்சீரை குழியுடலிகளின் ஆரைச் சமச்சீரிலிருந்தும் பிரிக்கிறது. ஜெல்லிமீன் அல்லது கடல்சாமந்தியில் ஆரைத் திட்டம் முதன்மையானது; குடலுக்கு ஒரே திறப்புடைய இருவடுக்கு விலங்குக்கு உரியது; அதே வழியில் இருபக்க நிலை முன்வருவதில்லை. முட்தோலியின் ஐந்துமடங்கு அமைப்பு மூவடுக்கு, உண்மையான உடற்குழியுடைய, இருபக்க மரபின்மேல் சுமத்தப்பட்ட இரண்டாம் நிலை ஆரைத் திட்டம். இரு வகை ஆரைத்தன்மைக்கிடையிலான ஒப்பீடு தேர்வில் பிடித்த கேள்வி."),
                    bnote("Primary and secondary radial symmetry", "முதன்மை, இரண்டாம் நிலை ஆரைச் சமச்சீர்",
                        "Cnidarian radial symmetry is primary. Echinoderm pentaradial symmetry is secondary, derived from a bilateral larva." to
                            "குழியுடலி ஆரைச் சமச்சீர் முதன்மையானது. முட்தோலி ஐந்தாரைச் சமச்சீர் இருபக்க லார்வாவிலிருந்து தோன்றிய இரண்டாம் நிலை."),
                    bdef("Pentaradial symmetry", "ஐந்தாரைச் சமச்சீர்", "A body plan in which similar parts are arranged in fives around a central axis." to
                        "ஒத்த பகுதிகள் மையச் சுற்றி ஐந்தாக அமையும் உடல் திட்டம்."),
                    bdef("Metamorphosis", "உருமாற்றம்", "The change in form from a larva to a juvenile." to
                        "லார்வாவிலிருந்து இளவிலங்காக வடிவம் மாறுதல்."),
                    bdef("Oral surface", "வாய்ப்புற மேற்பரப்பு", "The surface of the body that bears the mouth." to
                        "வாய் அமைந்துள்ள உடல் மேற்பரப்பு."),
                    bdef("Aboral surface", "மேற்புற மேற்பரப்பு", "The surface opposite the mouth, which in a starfish bears the anus and the madreporite." to
                        "வாய்க்கு எதிர் மேற்பரப்பு; நட்சத்திரமீனில் மலவாயும் மேட்ரிபோரைட்டும் உள்ளது."),
                )),
            bsec("wvs-skeleton", "The skeleton and the water-vascular system", "கூடும் நீர்-நாள அமைப்பும்",
                blocks = listOf(
                    bp("The skeleton is made of ossicles, each of which is a meshwork of calcium carbonate in the form of calcite with a small amount of organic matter, formed within cells of the mesoderm. In the sea urchins the ossicles are fused into a rigid shell, the test. In the starfishes they are separate pieces held by connective tissue and muscle, so that the arms can bend. In the sea cucumbers they are reduced to microscopic pieces in a leathery body wall. The surface may bear spines, and in the starfishes and sea urchins the pedicellariae." to
                        "கூடு ஆஸிக்கிள்களால் ஆனது; ஒவ்வொன்றும் சிறிதளவு கரிமப் பொருளுடன் கால்சைட் வடிவ கால்சியம் கார்பனேட்டின் வலைப்பின்னல்; மெசோடெர்ம் உயிரணுக்களுக்குள் உருவாகிறது. கடல் முள்ளெலிகளில் ஆஸிக்கிள்கள் இணைந்து இறுகிய ஓடு, டெஸ்ட் ஆகின்றன. நட்சத்திர மீன்களில் அவை இணைப்புத் திசுவாலும் தசையாலும் பிடிக்கப்பட்ட தனித் துண்டுகள்; எனவே கரங்கள் வளையலாம். கடல் வெள்ளரிகளில் தோல் போன்ற உடற்சுவரில் நுண்ணிய துண்டுகளாகக் குறைந்துள்ளன. மேற்பரப்பில் முட்கள் இருக்கலாம்; நட்சத்திர மீன்கள், முள்ளெலிகளில் பெடிசல்லேரியாக்கள்."),
                    bp("The water-vascular system is a system of canals, derived from part of the coelom, filled with a fluid like sea water and moved by muscles and hydraulic pressure. Seawater enters through the madreporite, a sieve-like plate, and the stone canal leads to a ring canal around the mouth, from which radial canals pass along each arm. Side branches end in tube feet, each connected to a bulb called an ampulla. When the ampulla contracts, fluid is driven into the tube foot, which extends, and muscles of the tube foot retract it. The system works by hydraulic pressure and not by a continuous one-way flow of sea water through the animal, and it is treated in its own chapter." to
                        "நீர்-நாள அமைப்பு உடற்குழியின் ஒரு பகுதியிலிருந்து தோன்றி, கடல்நீர் போன்ற திரவம் நிரம்பி, தசைகளாலும் நீரழுத்தத்தாலும் இயக்கப்படும் கால்வாய்களின் அமைப்பு. கடல்நீர் சல்லடை போன்ற தகடான மேட்ரிபோரைட் வழியே நுழைகிறது; கல் கால்வாய் வாயைச் சுற்றிய வளையக் கால்வாய்க்குச் செல்கிறது; அதிலிருந்து கதிர்க் கால்வாய்கள் ஒவ்வொரு கரத்திலும் செல்கின்றன. பக்கக் கிளைகள் குழாய்ப்பாதங்களில் முடிகின்றன; ஒவ்வொன்றும் ஆம்புலா எனும் குமிழுடன் இணைந்துள்ளது. ஆம்புலா சுருங்கும்போது திரவம் குழாய்ப்பாதத்துக்குள் தள்ளப்பட்டு அது நீள்கிறது; குழாய்ப்பாதத் தசைகள் அதை இழுக்கின்றன. அமைப்பு நீரழுத்தத்தால் இயங்குகிறது; விலங்கு வழியே கடல்நீர் தொடர்ந்து ஒருதிசை ஓடுவதால் அல்ல; இது தனி அத்தியாயத்தில் கையாளப்படுகிறது."),
                    bdef("Madreporite", "மேட்ரிபோரைட்", "The sieve-like plate on the aboral surface through which sea water communicates with the water-vascular system." to
                        "கடல்நீர் நீர்-நாள அமைப்புடன் தொடர்புகொள்ளும் மேற்புறச் சல்லடை போன்ற தகடு."),
                    bdef("Tube foot", "குழாய்ப்பாதம்", "A hollow, extensible, hydraulic projection of the water-vascular system used in movement, attachment and feeding." to
                        "இயக்கம், பற்றுதல், உணவெடுப்பில் பயன்படும் நீர்-நாள அமைப்பின் வெற்று, நீளக்கூடிய நீரழுத்தத் துருத்தல்."),
                    bdef("Ampulla", "ஆம்புலா", "The muscular bulb at the base of a tube foot whose contraction extends the foot." to
                        "சுருங்கும்போது குழாய்ப்பாதத்தை நீட்டும், அதன் அடியிலுள்ள தசைக் குமிழ்."),
                    bdef("Test", "டெஸ்ட்", "The rigid shell of a sea urchin formed from fused ossicles." to
                        "இணைந்த ஆஸிக்கிள்களால் உருவாகும் கடல் முள்ளெலியின் இறுகிய ஓடு."),
                )),
            bsec("classification", "Classification into classes", "வகுப்புகளாக வகைப்பாடு",
                blocks = listOf(
                    bp("The living echinoderms are placed in five classes, and a student should be able to name them with an example and with the character that identifies each. The classes are Asteroidea, the starfishes; Ophiuroidea, the brittle stars; Echinoidea, the sea urchins and sand dollars; Holothuroidea, the sea cucumbers; and Crinoidea, the sea lilies and feather stars. Fossil classes are also known, and a subphylum arrangement is sometimes used, in which the crinoids, which are attached or stalked in many forms, are set apart from the others." to
                        "வாழும் முட்தோலிகள் ஐந்து வகுப்புகளில் வைக்கப்படுகின்றன; மாணவர் ஒவ்வொன்றையும் எடுத்துக்காட்டுடனும் அதை அடையாளப்படுத்தும் பண்புடனும் பெயரிட முடிய வேண்டும். வகுப்புகள்: அஸ்டீராய்டியா, நட்சத்திர மீன்கள்; ஓஃபியூராய்டியா, பிரிட்டில் நட்சத்திரங்கள்; எக்கினாய்டியா, கடல் முள்ளெலிகளும் மணல் டாலர்களும்; ஹோலோதூரோய்டியா, கடல் வெள்ளரிகள்; கிரினாய்டியா, கடல் அல்லிகளும் இறகு நட்சத்திரங்களும். புதைபடிவ வகுப்புகளும் அறியப்படுகின்றன; பல வடிவங்களில் பற்றிய அல்லது தண்டுடைய கிரினாய்டுகள் மற்றவற்றிலிருந்து பிரிக்கப்படும் உபதொகுதி அமைப்பும் சில சமயம் பயன்படுகிறது."),
                    btable("The five living classes of Echinodermata", "முட்தோலிகளின் ஐந்து வாழும் வகுப்புகள்",
                        listOf("Class" to "வகுப்பு", "Common name" to "பொதுப் பெயர்", "Example" to "எடுத்துக்காட்டு", "Notable feature" to "குறிப்பிடத்தக்க பண்பு"),
                        listOf(
                            listOf("Asteroidea" to "அஸ்டீராய்டியா", "Starfish" to "நட்சத்திர மீன்", "Asterias" to "அஸ்டீரியாஸ்", "Arms broad at the base, not sharply marked off from the disc; open ambulacral grooves" to "கரங்கள் அடியில் அகன்று வட்டிலிருந்து தெளிவாகப் பிரியாதவை; திறந்த ஆம்புலாக்ரல் பள்ளங்கள்"),
                            listOf("Ophiuroidea" to "ஓஃபியூராய்டியா", "Brittle star" to "பிரிட்டில் நட்சத்திரம்", "Ophiura" to "ஓஃபியூரா", "Slender arms sharply marked off from the disc; no open ambulacral grooves" to "வட்டிலிருந்து தெளிவாகப் பிரிந்த மெல்லிய கரங்கள்; திறந்த ஆம்புலாக்ரல் பள்ளங்கள் இல்லை"),
                            listOf("Echinoidea" to "எக்கினாய்டியா", "Sea urchin" to "கடல் முள்ளெலி", "Echinus" to "எக்கினஸ்", "Globular or flattened test with movable spines; no arms" to "அசையும் முட்களுடன் உருண்டை அல்லது தட்டை டெஸ்ட்; கரங்கள் இல்லை"),
                            listOf("Holothuroidea" to "ஹோலோதூரோய்டியா", "Sea cucumber" to "கடல் வெள்ளரி", "Holothuria" to "ஹோலோதூரியா", "Elongated, leathery body with tentacles around the mouth; ossicles reduced" to "வாயைச் சுற்றிய உணர்கொம்புகளுடன் நீண்ட, தோல் போன்ற உடல்; ஆஸிக்கிள்கள் குறைந்தவை"),
                            listOf("Crinoidea" to "கிரினாய்டியா", "Sea lily, feather star" to "கடல் அல்லி, இறகு நட்சத்திரம்", "Antedon" to "ஆண்டிடான்", "Mouth upwards; feathery arms; stalked or free" to "வாய் மேல்நோக்கி; இறகு போன்ற கரங்கள்; தண்டுடையவை அல்லது தளர்வானவை"),
                        )),
                    bdef("Asteroidea", "அஸ்டீராய்டியா", "The class of starfishes, with arms continuous with the central disc." to
                        "கரங்கள் மைய வட்டுடன் தொடர்ச்சியான நட்சத்திர மீன்களின் வகுப்பு."),
                    bdef("Ophiuroidea", "ஓஃபியூராய்டியா", "The class of brittle stars, with slender arms sharply set off from the disc." to
                        "வட்டிலிருந்து தெளிவாகப் பிரிந்த மெல்லிய கரங்கள் கொண்ட பிரிட்டில் நட்சத்திரங்களின் வகுப்பு."),
                    bdef("Echinoidea", "எக்கினாய்டியா", "The class of sea urchins and sand dollars, with a rigid test and movable spines." to
                        "இறுகிய டெஸ்ட், அசையும் முட்கள் கொண்ட கடல் முள்ளெலிகள், மணல் டாலர்களின் வகுப்பு."),
                    bdef("Holothuroidea", "ஹோலோதூரோய்டியா", "The class of sea cucumbers, with an elongated body and reduced ossicles." to
                        "நீண்ட உடலும் குறைந்த ஆஸிக்கிள்களும் கொண்ட கடல் வெள்ளரிகளின் வகுப்பு."),
                    bdef("Crinoidea", "கிரினாய்டியா", "The class of sea lilies and feather stars, with the mouth directed upwards." to
                        "வாய் மேல்நோக்கி உள்ள கடல் அல்லிகள், இறகு நட்சத்திரங்களின் வகுப்பு."),
                )),
            bsec("affinities", "Larvae and deuterostome affinity", "லார்வாக்களும் இரண்டாம்வாய் உறவும்",
                blocks = listOf(
                    bp("A deuterostome is an animal in which, in typical development, the blastopore becomes the anus and the mouth forms secondarily, and in which the coelom forms from outgrowths of the gut. The echinoderms are deuterostomes on developmental grounds, and molecular studies support the placement. It is supported also by the larvae: bipinnaria in the starfishes, plutei in the sea urchins and brittle stars, and auricularia in the sea cucumbers. The larvae are bilateral, ciliated and free swimming, and resemble each other and the tornaria larva of the hemichordates." to
                        "இரண்டாம்வாயி என்பது வழக்கமான வளர்ச்சியில் பிளாஸ்டோபோர் மலவாயாகவும் வாய் இரண்டாம் நிலையாகவும் உருவாகும், உடற்குழி குடலின் வளர்ச்சிகளிலிருந்து உருவாகும் விலங்கு. வளர்ச்சிச் சான்றுகளின் அடிப்படையிலும் மூலக்கூறு ஆய்வுகள் இடத்தை ஆதரிப்பதாலும் முட்தோலிகள் இரண்டாம்வாயிகள். லார்வாக்களும் ஆதரிக்கின்றன: நட்சத்திர மீன்களில் பிபின்னேரியா, முள்ளெலிகள், பிரிட்டில் நட்சத்திரங்களில் ப்ளூட்டியஸ், கடல் வெள்ளரிகளில் ஆரிக்குலேரியா. லார்வாக்கள் இருபக்கச் சமச்சீருடையவை, கசையிழை கொண்டவை, தனி நீந்துபவை; ஒன்றுக்கொன்றும் ஹெமிகார்டேட்டுகளின் டார்னேரியா லார்வாவுக்கும் ஒத்தவை."),
                    bp("The current view is that the echinoderms and the hemichordates together form a group, the Ambulacraria, which is the sister group of the chordates. The phylum is therefore a close relative of the vertebrates, though it is far from being similar in adult form. This is a statement about common ancestry, and it does not mean that a chordate ancestor looked like an echinoderm. A student who writes that echinoderms are the ancestors of vertebrates is making the error that the word relative is intended to avoid." to
                        "தற்போதைய கருத்து: முட்தோலிகளும் ஹெமிகார்டேட்டுகளும் சேர்ந்து அம்புலாக்ரேரியா எனும் குழுவை உருவாக்குகின்றன; இது முதுகுநாணிகளின் சகோதரக் குழு. எனவே முதிர்ந்த வடிவத்தில் ஒத்ததாக இல்லாவிட்டாலும் தொகுதி முதுகெலும்பிகளின் நெருங்கிய உறவினர். இது பொது மூதாதையர் பற்றிய கூற்று; முதுகுநாணி மூதாதை முட்தோலி போல் இருந்தார் என்று பொருளல்ல. முட்தோலிகள் முதுகெலும்பிகளின் மூதாதையர் என எழுதும் மாணவர் உறவினர் என்ற சொல் தவிர்க்க நினைக்கும் தவறைச் செய்கிறார்."),
                    bdef("Deuterostome", "இரண்டாம்வாயி", "An animal in which the blastopore becomes the anus and the mouth forms secondarily." to
                        "பிளாஸ்டோபோர் மலவாயாகவும் வாய் இரண்டாம் நிலையாகவும் உருவாகும் விலங்கு."),
                    bdef("Ambulacraria", "அம்புலாக்ரேரியா", "The group formed by the echinoderms and the hemichordates." to
                        "முட்தோலிகளும் ஹெமிகார்டேட்டுகளும் இணைந்த குழு."),
                    bdef("Bipinnaria", "பிபின்னேரியா", "The free-swimming ciliated larva of the starfishes." to
                        "நட்சத்திர மீன்களின் தனி நீந்தும் கசையிழை லார்வா."),
                    bdef("Pluteus", "ப்ளூட்டியஸ்", "The larva of sea urchins and brittle stars, with long skeletal arms." to
                        "நீண்ட கூட்டுக் கரங்கள் கொண்ட கடல் முள்ளெலிகள், பிரிட்டில் நட்சத்திரங்களின் லார்வா."),
                    bdef("Auricularia", "ஆரிக்குலேரியா", "The larva of sea cucumbers." to
                        "கடல் வெள்ளரிகளின் லார்வா."),
                )),
            bsec("systems", "Organ systems in outline", "உறுப்பு அமைப்புகள் சுருக்கமாக",
                blocks = listOf(
                    bp("The alimentary canal is usually complete, with a mouth on the oral side and an anus on the aboral side, though the brittle stars have no anus, and the form of the gut varies with the diet. In the starfishes a stomach can be everted through the mouth over the prey, so that digestion begins outside the body. The nervous system lacks a brain. A nerve ring around the mouth gives off radial nerves into the arms, and there are subepidermal nerve plexuses, with simple sense organs, including the eyespot at the tip of each arm of a starfish. The sexes are usually separate, the gonads are repeated in each arm or interradius, and the eggs and sperm are normally shed into the sea, where fertilisation takes place." to
                        "உணவுக்குழாய் பொதுவாக முழுமையானது: வாய்ப்புறத்தில் வாய், மேற்புறத்தில் மலவாய்; ஆனால் பிரிட்டில் நட்சத்திரங்களுக்கு மலவாய் இல்லை; உணவுக்கேற்ப குடலின் வடிவம் மாறுகிறது. நட்சத்திர மீன்களில் இரைமேல் வாய் வழியே இரைப்பை புரளக்கூடியது; எனவே செரிமானம் உடலுக்கு வெளியே தொடங்குகிறது. நரம்பு அமைப்புக்கு மூளை இல்லை. வாயைச் சுற்றிய நரம்பு வளையம் கரங்களுக்குள் கதிர் நரம்புகளை அனுப்புகிறது; மேற்றோலடி நரம்புப் பின்னல்களும் எளிய உணர்வு உறுப்புகளும் உள்ளன; நட்சத்திரமீனின் ஒவ்வொரு கர நுனியிலும் கண்புள்ளி இதில் அடங்கும். பாலினங்கள் பொதுவாகத் தனி; இனப்பெருக்கச் சுரப்பிகள் ஒவ்வொரு கரத்திலும் அல்லது இடைக்கதிரிலும் மீள்கின்றன; முட்டையும் விந்தும் வழக்கமாகக் கடலில் விடப்பட்டு அங்கே கருவுறுதல் நடைபெறுகிறது."),
                    bp("The body fluid of the coelom has a part in the transport of gases and food, and cells called coelomocytes within it take part in defence and in the removal of waste. Gas exchange takes place across thin areas of the body, including the tube feet and, in the starfishes, small projections of the skin called papulae. These statements apply in outline to the whole phylum, and the particular arrangement in each class is learned from the type." to
                        "உடற்குழித் திரவம் வாயுக்கள், உணவின் கடத்தலில் பங்கு வகிக்கிறது; அதிலுள்ள கொலோமோசைட்டுகள் எனும் உயிரணுக்கள் பாதுகாப்பிலும் கழிவு அகற்றலிலும் பங்கேற்கின்றன. வாயுப் பரிமாற்றம் குழாய்ப்பாதங்கள், நட்சத்திர மீன்களில் பப்புலாக்கள் எனும் சிறு தோல் துருத்தல்கள் உட்பட மெல்லிய உடற்பகுதிகள் வழியே நடைபெறுகிறது. இக்கூற்றுகள் முழுத் தொகுதிக்கும் சுருக்கமாகப் பொருந்தும்; ஒவ்வொரு வகுப்பின் குறிப்பிட்ட அமைப்பு வகை உயிரியிலிருந்து கற்கப்படுகிறது."),
                    bdef("Papula", "பப்புலா", "A small thin-walled projection of the body wall of a starfish that is involved in gas exchange." to
                        "வாயுப் பரிமாற்றத்தில் ஈடுபடும் நட்சத்திரமீனின் உடற்சுவரின் சிறு மெல்லிய சுவர் துருத்தல்."),
                    bdef("Coelomocyte", "கொலோமோசைட்", "A free cell of the coelomic fluid that takes part in defence and waste removal." to
                        "பாதுகாப்பிலும் கழிவு அகற்றலிலும் பங்கேற்கும் உடற்குழித் திரவத்தின் தனி உயிரணு."),
                )),
            bsec("habits", "Habits, regeneration and importance", "பழக்கங்கள், மீளுருவாக்கம், முக்கியத்துவம்",
                blocks = listOf(
                    bp("Echinoderms live on the sea bottom from the shore to the deep sea, and they feed in different ways. The starfishes are mostly carnivores that eat bivalves and other slow animals, the sea urchins graze on algae, the brittle stars scavenge or capture particles, the sea cucumbers collect deposits with their tentacles, and the crinoids filter particles from the water. They have a strong capacity to regenerate lost parts, and in some starfishes and brittle stars a lost arm can be replaced, and in certain forms a detached arm with part of the disc may regenerate a whole animal. The extent of regeneration differs between species, and should be stated in this guarded way." to
                        "முட்தோலிகள் கரையிலிருந்து ஆழ்கடல் வரை கடலடியில் வாழ்கின்றன; பல்வேறு வழிகளில் உண்கின்றன. நட்சத்திர மீன்கள் பெரும்பாலும் இருவோடுகளையும் பிற மெதுவான விலங்குகளையும் உண்ணும் ஊனுண்ணிகள்; முள்ளெலிகள் பாசியை மேய்கின்றன; பிரிட்டில் நட்சத்திரங்கள் இறந்தவற்றை உண்கின்றன அல்லது துகள்களைப் பிடிக்கின்றன; கடல் வெள்ளரிகள் தம் உணர்கொம்புகளால் படிவுகளைச் சேகரிக்கின்றன; கிரினாய்டுகள் நீரிலிருந்து துகள்களை வடிகட்டுகின்றன. இழந்த பகுதிகளை மீளுருவாக்கும் வலிமையான திறன் உண்டு; சில நட்சத்திர மீன்கள், பிரிட்டில் நட்சத்திரங்களில் இழந்த கரம் மாற்றப்படலாம்; சில வடிவங்களில் வட்டின் ஒரு பகுதியுடன் பிரிந்த கரம் முழு விலங்கையும் மீளுருவாக்கலாம். மீளுருவாக்கத்தின் அளவு இனங்களுக்கிடையே மாறுபடும்; இந்த எச்சரிக்கையான வழியில் கூறப்பட வேண்டும்."),
                    bp("Echinoderms are important in the ecology of the sea. Starfishes have been the subject of the keystone predator concept, since their removal from some rocky shores has changed the community, and the sea urchins can control the growth of algae. The sea cucumbers are harvested in some regions for food, which has led to overfishing in many places, and the regulation of such fisheries is a matter for current official sources." to
                        "முட்தோலிகள் கடலின் சூழலியலில் முக்கியமானவை. சில பாறைக் கரைகளில் நட்சத்திர மீன்கள் அகற்றப்பட்டபோது சமூகம் மாறியதால் அவை முக்கிய வேட்டையாடி என்ற கருத்துக்கு உட்பட்டுள்ளன; முள்ளெலிகள் பாசி வளர்ச்சியைக் கட்டுப்படுத்தலாம். சில பகுதிகளில் கடல் வெள்ளரிகள் உணவுக்காக அறுவடை செய்யப்படுகின்றன; இது பல இடங்களில் அதிகப்படியான மீன்பிடிக்கு வழிவகுத்துள்ளது; அத்தகைய மீன்பிடியின் ஒழுங்குமுறை தற்போதைய அதிகாரபூர்வ ஆதாரங்களுக்குரிய விஷயம்."),
                    bdef("Regeneration", "மீளுருவாக்கம்", "The replacement of lost body parts by new growth." to
                        "இழந்த உடல் பகுதிகளை புதிய வளர்ச்சியால் மாற்றுதல்."),
                    bdef("Keystone predator", "முக்கிய வேட்டையாடி", "A predator whose removal changes the structure of a whole community out of proportion to its numbers." to
                        "அதன் எண்ணிக்கைக்குப் பொருந்தாத அளவில் அகற்றப்படும்போது முழு சமூகத்தின் அமைப்பையும் மாற்றும் வேட்டையாடி."),
                )),
            bsec("answers", "Writing the answer", "விடை எழுதுதல்",
                blocks = listOf(
                    bp("The general characters question is answered best in an order that follows the body, starting with habitat and germ layers, followed by symmetry with the note that it is secondary, the skeleton, the water-vascular system, the nervous and the reproductive features, and finishing with development and larvae. A candidate who ends with the larva has the opportunity to state the deuterostome affinity, which is the point that examiners reward. The classification should be written as a list of the five classes with a type and one diagnostic character each, and it is not necessary to carry the classification below the level of the class." to
                        "பொதுப் பண்புகள் கேள்விக்கு உடலைப் பின்பற்றும் வரிசையில், வாழிடம், கருவடுக்குகளுடன் தொடங்கி, இரண்டாம் நிலை என்ற குறிப்புடன் சமச்சீர், கூடு, நீர்-நாள அமைப்பு, நரம்பு, இனப்பெருக்கப் பண்புகள், வளர்ச்சி, லார்வாக்களுடன் முடிப்பது சிறந்த விடை. லார்வாவுடன் முடிக்கும் தேர்வர் தேர்வாளர்கள் மதிப்பளிக்கும் இரண்டாம்வாய் உறவைக் கூற வாய்ப்புப் பெறுகிறார். வகைப்பாடு ஐந்து வகுப்புகளின் பட்டியலாக, ஒவ்வொன்றுக்கும் ஒரு வகையும் ஒரு அடையாளப் பண்பும் சேர்த்து எழுதப்பட வேண்டும்; வகுப்பு நிலைக்குக் கீழ் வகைப்பாட்டைக் கொண்டுசெல்ல அவசியமில்லை."),
                    bpoints("Points that gain marks", "மதிப்பெண் தரும் புள்ளிகள்",
                        "Use the word secondary for the radial symmetry, and give the reason." to "ஆரைச் சமச்சீருக்கு இரண்டாம் நிலை என்ற சொல்லைப் பயன்படுத்தி காரணம் தருக.",
                        "Name the water-vascular system as hydraulic, with the madreporite, ring canal, radial canals and ampullae." to "நீர்-நாள அமைப்பை மேட்ரிபோரைட், வளையக் கால்வாய், கதிர்க் கால்வாய்கள், ஆம்புலாக்களுடன் நீரழுத்தமானது எனக் குறிப்பிடுக.",
                        "Give the larva of at least two classes." to "குறைந்தது இரு வகுப்புகளின் லார்வாவைத் தருக.",
                        "State that echinoderms are relatives of the chordates through the Ambulacraria." to "அம்புலாக்ரேரியா மூலம் முட்தோலிகள் முதுகுநாணிகளின் உறவினர் எனக் கூறுக.",
                    ),
                )),
        ),
        summary = listOf(
            "Echinoderms are marine, triploblastic, coelomate deuterostomes with a calcareous endoskeleton." to "முட்தோலிகள் சுண்ணாம்பு உட்கூடுடைய கடல்வாழ், மூவடுக்கு, உண்மையான உடற்குழியுடைய இரண்டாம்வாயிகள்.",
            "Adult pentaradial symmetry is secondary, derived from a bilateral larva." to "முதிர்ந்த ஐந்தாரைச் சமச்சீர் இரண்டாம் நிலை; இருபக்க லார்வாவிலிருந்து தோன்றியது.",
            "The water-vascular system is hydraulic and unique to the phylum." to "நீர்-நாள அமைப்பு நீரழுத்தமானது; தொகுதிக்கே உரியது.",
            "There are five living classes: Asteroidea, Ophiuroidea, Echinoidea, Holothuroidea and Crinoidea." to "ஐந்து வாழும் வகுப்புகள்: அஸ்டீராய்டியா, ஓஃபியூராய்டியா, எக்கினாய்டியா, ஹோலோதூரோய்டியா, கிரினாய்டியா.",
            "Larval types, bipinnaria, pluteus and auricularia, are class-informative." to "பிபின்னேரியா, ப்ளூட்டியஸ், ஆரிக்குலேரியா லார்வா வகைகள் வகுப்பை அறிய உதவுகின்றன.",
            "Echinoderms and hemichordates form the Ambulacraria, a relative of the chordates, and not their ancestor." to "முட்தோலிகளும் ஹெமிகார்டேட்டுகளும் முதுகுநாணிகளின் உறவினரான அம்புலாக்ரேரியாவை உருவாக்குகின்றன; மூதாதை அல்ல.",
        ),
        checks = listOf(
            bqc("Why is echinoderm radial symmetry called secondary?", "முட்தோலி ஆரைச் சமச்சீர் ஏன் இரண்டாம் நிலை எனப்படுகிறது?",
                "The larva is bilateral, and the radial form arises at metamorphosis.", "லார்வா இருபக்கச் சமச்சீருடையது; உருமாற்றத்தில் ஆரை வடிவம் தோன்றுகிறது."),
            bqc("Name the five living classes.", "ஐந்து வாழும் வகுப்புகளைப் பெயரிடுக.",
                "Asteroidea, Ophiuroidea, Echinoidea, Holothuroidea, Crinoidea.", "அஸ்டீராய்டியா, ஓஃபியூராய்டியா, எக்கினாய்டியா, ஹோலோதூரோய்டியா, கிரினாய்டியா."),
            bqc("What is unique about the water-vascular system?", "நீர்-நாள அமைப்பில் தனித்துவம் என்ன?",
                "A hydraulic system of canals and tube feet connected to a madreporite, found in no other phylum.", "மேட்ரிபோரைட்டுடன் இணைந்த கால்வாய்கள், குழாய்ப்பாதங்களின் நீரழுத்த அமைப்பு; வேறெந்தத் தொகுதியிலும் இல்லை."),
            bqc("Which larva belongs to the starfishes?", "எந்த லார்வா நட்சத்திர மீன்களுக்கு உரியது?",
                "The bipinnaria.", "பிபின்னேரியா."),
        ),
    )
}
