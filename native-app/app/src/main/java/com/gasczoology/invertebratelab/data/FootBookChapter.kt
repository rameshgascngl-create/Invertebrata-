package com.gasczoology.invertebratelab.data

/** Book-style chapter: modifications of the molluscan foot. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object FootBookChapter {
    val chapter = bchapter(
        lessonId = "u5-foot",
        title = "Modifications of the Molluscan Foot" to "மெல்லுடலிப் பாதத்தின் மாற்றங்கள்",
        objectives = listOf(
            "Describe the basic molluscan foot and its musculature." to "அடிப்படை மெல்லுடலிப் பாதத்தையும் அதன் தசையமைப்பையும் விவரிக்க.",
            "Describe the foot of a gastropod, a bivalve and a cephalopod." to "இரைப்பைக்காலி, இருவோடு, தலைக்காலியின் பாதத்தை விவரிக்க.",
            "Relate each modification to the mode of life." to "ஒவ்வொரு மாற்றத்தையும் வாழ்க்கை முறையுடன் தொடர்புபடுத்த.",
            "Explain why the cephalopod funnel is a derivative of the foot." to "தலைக்காலிக் குழல் பாதத்தின் வழிப்பெறு அமைப்பு என ஏன் கருதப்படுகிறது என விளக்க.",
            "Avoid describing the classes as a ladder of advancement." to "வகுப்புகளை முன்னேற்றப் படிக்கட்டாக விவரிப்பதைத் தவிர்க்க.",
        ),
        introduction = listOf(
            "The foot is one of the three characteristic parts of the molluscan body, with the visceral mass and the mantle, and it is the part that varies most in form between the classes. In the simple, hypothetical ancestral mollusc it is a broad, flat, muscular organ on the lower surface, used for creeping. In the living classes the same organ is a creeping sole in a snail, a digging wedge in a mussel, and the arms and the funnel of an octopus, which seem to share almost nothing at first sight." to
                "மெல்லுடலி உடலின் மூன்று தனிப்பண்பு பகுதிகளில், உள்ளுறுப்புத் திரள், மேண்டிலுடன், பாதம் ஒன்று; வகுப்புகளிடையே வடிவத்தில் மிக மாறுபடும் பகுதி இதுவே. எளிய, கற்பனை மூதாதை மெல்லுடலியில் அது கீழ்ப்பரப்பில் ஊர்தலுக்குப் பயன்படும் அகன்ற, தட்டையான தசை உறுப்பு. வாழும் வகுப்புகளில் அதே உறுப்பு நத்தையில் ஊரும் அடிப்பகுதி; சிப்பியில் தோண்டும் ஆப்பு; ஆக்டோபஸில் கரங்களும் குழலும்; முதல் பார்வையில் அவை எதையும் பகிர்வதாகத் தோன்றாது.",
            "The question asks for the structural modifications of the foot to be related to the mode of life, with reference to Pila, Unio and a cephalopod. The chapter therefore sets out the basic foot, then each modification in turn, and ends with the argument that these are homologous organs that have diverged. It also warns against the idea that the three are steps in a ladder of improvement, since each is suited to its own way of living." to
                "பைலா, யூனியோ, ஒரு தலைக்காலியைக் குறிப்பிட்டு பாதத்தின் அமைப்பு மாற்றங்களை வாழ்க்கை முறையுடன் தொடர்புபடுத்த கேள்வி கோருகிறது. எனவே அத்தியாயம் அடிப்படைப் பாதத்தை முன்வைத்து, ஒவ்வொரு மாற்றத்தையும் வரிசையாகத் தந்து, இவை வேறுபட்டுப் பிரிந்த ஒத்தமைவு உறுப்புகள் என்ற வாதத்துடன் முடிகிறது. மூன்றும் மேம்பாட்டுப் படிக்கட்டின் படிகள் என்ற எண்ணத்தையும் எச்சரிக்கிறது; ஏனெனில் ஒவ்வொன்றும் தனக்குரிய வாழ்க்கை முறைக்குப் பொருந்தியது.",
        ),
        sections = listOf(
            bsec("basic-foot", "The basic molluscan foot", "அடிப்படை மெல்லுடலிப் பாதம்",
                blocks = listOf(
                    bp("The foot is a muscular outgrowth of the ventral body wall. It is made of interwoven muscle fibres running in several directions, with connective tissue and blood spaces between them, and it is supplied by a pair of pedal ganglia and the nerves from them. It is richly supplied with gland cells, which secrete mucus, and the surface carries cilia in many species. These features make it suited to creeping on a layer of mucus, which is the starting point for its other uses." to
                        "பாதம் வயிற்றுப்புற உடற்சுவரின் தசை வளர்ச்சி. இது பல திசைகளில் ஓடும் பின்னிய தசை இழைகளாலும் அவற்றுக்கிடையே இணைப்புத் திசு, இரத்த இடைவெளிகளாலும் ஆனது; ஒரு இணை பெடல் நரம்பு செல்திரள்களும் அவற்றிலிருந்து வரும் நரம்புகளும் அதற்கு அளிக்கின்றன. சளி சுரக்கும் சுரப்பி உயிரணுக்கள் நிறைந்தது; பல இனங்களில் மேற்பரப்பில் கசையிழை உண்டு. இப்பண்புகள் சளி அடுக்கின்மேல் ஊர்வதற்கு ஏற்றவை; இதுவே பிற பயன்பாடுகளின் தொடக்கப் புள்ளி."),
                    bp("Since a muscle can only pull, the foot depends on a second element for its action, which is the fluid in the blood spaces. The incompressible fluid works as a hydrostatic skeleton, so that when the muscles of one direction contract, the fluid is forced elsewhere and the foot changes shape, and when other muscles act, it changes back. This principle appears in all the specialised feet, and it is the key to understanding burrowing, creeping, and the control of the arms of a cephalopod." to
                        "தசை இழுக்க மட்டுமே முடியும் என்பதால் பாதம் செயல்பட இரண்டாம் கூறைச் சார்ந்துள்ளது: இரத்த இடைவெளிகளிலுள்ள திரவம். அழுத்தப்பட முடியாத திரவம் நீர்நிலைக் கூடாகச் செயல்படுகிறது; ஒரு திசைத் தசைகள் சுருங்கும்போது திரவம் வேறிடம் தள்ளப்பட்டு பாதம் வடிவம் மாறுகிறது; பிற தசைகள் செயல்படும்போது மீண்டும் மாறுகிறது. இக்கொள்கை எல்லா சிறப்புப் பாதங்களிலும் தோன்றுகிறது; தோண்டுதல், ஊர்தல், தலைக்காலியின் கரக் கட்டுப்பாடு ஆகியவற்றைப் புரிந்துகொள்ளும் திறவுகோல்."),
                    bpoints("The basic foot in outline", "அடிப்படைப் பாதம் சுருக்கமாக",
                        "A ventral, muscular organ, broad and flat in the simple form." to "வயிற்றுப்புற, தசை உறுப்பு; எளிய வடிவில் அகன்று தட்டையானது.",
                        "Interwoven muscles with blood spaces that act as a hydrostatic skeleton." to "நீர்நிலைக் கூடாகச் செயல்படும் இரத்த இடைவெளிகளுடன் பின்னிய தசைகள்.",
                        "Pedal ganglia and nerves control its movement." to "பெடல் நரம்பு செல்திரள்களும் நரம்புகளும் அசைவைக் கட்டுப்படுத்துகின்றன.",
                        "Mucus glands and cilia that assist creeping." to "ஊர்தலுக்கு உதவும் சளி சுரப்பிகளும் கசையிழையும்.",
                    ),
                    bdef("Foot", "பாதம்", "The ventral muscular organ of a mollusc, used for creeping, burrowing, attachment or, in modified form, for other functions." to
                        "ஊர்தல், தோண்டுதல், ஒட்டுதல் அல்லது மாறிய வடிவில் பிற செயல்களுக்குப் பயன்படும் மெல்லுடலியின் வயிற்றுப்புற தசை உறுப்பு."),
                    bdef("Pedal ganglion", "பெடல் நரம்பு செல்திரள்", "A ganglion in the foot that controls its movement." to
                        "பாதத்தின் அசைவைக் கட்டுப்படுத்தும் அதிலுள்ள நரம்பு செல்திரள்."),
                    bdef("Hydrostatic skeleton", "நீர்நிலைக் கூடு", "A fluid-filled body region that gives support and allows movement when muscles act against the fluid." to
                        "தசைகள் திரவத்துக்கு எதிராகச் செயல்படும்போது ஆதரவும் இயக்கமும் தரும் திரவம் நிரம்பிய உடற்பகுதி."),
                    bdef("Homologous", "ஒத்தமைவான", "Having the same evolutionary origin, whatever the function." to
                        "செயல் எதுவாயினும் ஒரே பரிணாமத் தோற்றம் கொண்ட."),
                )),
            bsec("gastropod", "The foot of a gastropod: Pila", "இரைப்பைக்காலியின் பாதம்: பைலா",
                blocks = listOf(
                    bp("In Pila the foot is a broad, flat, muscular sole on which the animal creeps over the mud and plants of its pond. The sole is ciliated and rich in mucous glands, and locomotion is produced by waves of muscular contraction that pass along it. The waves are called pedal waves, and in a snail with a broad foot they may pass from the back to the front, so that parts of the sole are lifted and moved forward while the rest is attached. The mucus makes the movement smooth and also gives the grip needed to climb. The pace is slow, which is in keeping with the habit of grazing on algae and plant debris." to
                        "பைலாவில் பாதம் அகன்ற, தட்டையான தசை அடிப்பகுதி; அதன்மீது விலங்கு குளத்தின் சேறு, தாவரங்கள்மேல் ஊர்கிறது. அடிப்பகுதி கசையிழை கொண்டது, சளிச் சுரப்பிகள் நிறைந்தது; அதன் வழியே செல்லும் தசைச் சுருக்க அலைகளால் இயக்கம் உண்டாகிறது. அலைகள் பெடல் அலைகள் எனப்படுகின்றன; அகன்ற பாதம் கொண்ட நத்தையில் அவை பின்னிலிருந்து முன்னுக்குச் செல்லலாம்; எனவே அடிப்பகுதியின் சில பகுதிகள் தூக்கப்பட்டு முன்னே நகர்த்தப்பட மற்றவை ஒட்டியிருக்கும். சளி இயக்கத்தை மென்மையாக்கி ஏறத் தேவையான பிடிப்பையும் தருகிறது. வேகம் மெதுவானது; பாசி, தாவரக் கழிவுகளை மேயும் பழக்கத்துக்கு ஏற்றது."),
                    bp("The foot of Pila has a second modification, which is the operculum. This is a horny plate attached to the upper and hinder part of the foot, and when the foot is withdrawn into the shell the operculum comes to lie across the aperture, shutting it like a lid. It protects the soft body from predators and from drying when the pond dries and the snail is exposed, which relates to the habit of the animal of withdrawing into its shell during dry seasons. Whether the snail should be described as aestivating in a given situation should be stated cautiously, as it depends on conditions." to
                        "பைலாவின் பாதத்துக்கு இரண்டாம் மாற்றம் உண்டு: ஆப்பர்குலம். இது பாதத்தின் மேல், பின் பகுதியில் ஒட்டிய கொம்புத் தகடு; பாதம் ஓட்டுக்குள் இழுக்கப்படும்போது ஆப்பர்குலம் வாய்த்துளையின் குறுக்கே மூடியாக வந்து மூடுகிறது. வேட்டையாடிகளிடமிருந்தும், குளம் வற்றி நத்தை வெளிப்படும்போது உலர்வதிலிருந்தும் மென்உடலைப் பாதுகாக்கிறது; வறண்ட பருவங்களில் ஓட்டுக்குள் பின்வாங்கும் விலங்கின் பழக்கத்துடன் தொடர்புடையது. குறிப்பிட்ட சூழ்நிலையில் நத்தை கோடைத் தூக்கத்தில் உள்ளது எனக் கூறுவது நிலைமையைச் சார்ந்தது என்பதால் எச்சரிக்கையுடன் கூறப்பட வேண்டும்."),
                    bp("The foot is also related to torsion and the shell. The head and the foot can be withdrawn into the shell, which sits like a hood over the hump of the viscera, and the operculum seals the retreat. The head and foot are the parts that did not turn in torsion, which is why the foot and the sole are still oriented forward in the torted animal." to
                        "பாதம் முறுக்கல், ஓடுடனும் தொடர்புடையது. உள்ளுறுப்புக் கூம்பின் மேல் தொப்பி போல அமரும் ஓட்டுக்குள் தலையும் பாதமும் இழுக்கப்படலாம்; ஆப்பர்குலம் பின்வாங்கலை அடைக்கிறது. தலையும் பாதமும் முறுக்கலில் திரும்பாத பகுதிகள்; எனவே முறுக்கப்பட்ட விலங்கிலும் பாதமும் அடிப்பகுதியும் முன்னோக்கி நோக்குகின்றன."),
                    bdef("Sole", "அடிப்பகுதி", "The flat creeping surface of the foot of a gastropod." to
                        "இரைப்பைக்காலியின் பாதத்தின் தட்டையான ஊரும் மேற்பரப்பு."),
                    bdef("Pedal wave", "பெடல் அலை", "A wave of muscular contraction that passes along the sole and moves the animal." to
                        "அடிப்பகுதி வழியே சென்று விலங்கை நகர்த்தும் தசைச் சுருக்க அலை."),
                    bdef("Operculum", "ஆப்பர்குலம்", "A horny plate on the hind part of the foot that closes the shell aperture when the animal withdraws." to
                        "விலங்கு பின்வாங்கும்போது ஓட்டு வாயை மூடும், பாதத்தின் பின்பகுதியிலுள்ள கொம்புத் தகடு."),
                    bdef("Pedal mucus", "பெடல் சளி", "The mucus secreted by the foot that lubricates and grips the surface." to
                        "மேற்பரப்பை வழுவழுப்பாக்கிப் பிடிப்பைத் தரும், பாதம் சுரக்கும் சளி."),
                )),
            bsec("bivalve", "The foot of a bivalve: Unio", "இருவோட்டின் பாதம்: யூனியோ",
                blocks = listOf(
                    bp("In a bivalve such as the freshwater mussel Unio the foot is laterally compressed and wedge-shaped or hatchet-shaped, and it projects from between the valves on the lower side. The animal lives in mud or sand at the bottom of streams and ponds, with the hinder part of the shell exposed to the water, and the foot is the organ that it uses to move slowly and to anchor itself. The head is absent, so the foot is the main part of the body that comes out of the shell in front." to
                        "நன்னீர் சிப்பி யூனியோ போன்ற இருவோட்டில் பாதம் பக்கவாட்டில் அழுத்தப்பட்டு ஆப்பு அல்லது கோடாரி வடிவமாகி, மூடிகளுக்கிடையே கீழ்ப்பக்கம் துருத்துகிறது. விலங்கு ஓடைகள், குளங்களின் அடியில் சேறு அல்லது மணலில் வாழ்கிறது; ஓட்டின் பின்பகுதி நீருக்கு வெளிப்படும்; பாதம் மெதுவாக நகரவும் தன்னை நங்கூரமிடவும் அது பயன்படுத்தும் உறுப்பு. தலை இல்லை; எனவே முன்புறம் ஓட்டிலிருந்து வெளிவரும் உடலின் முக்கிய பகுதி பாதமே."),
                    bp("Burrowing follows a sequence. The foot is extended forward and downward into the substratum, and the tip is anchored by being swollen with blood. The adductor and other muscles of the shell then contract, and the foot muscles draw the shell down towards the anchored foot, so that the animal moves into the mud. The extension of the foot is produced by muscular action and by the pressure of the haemolymph in its spaces, which makes the foot stiff as it pushes in. The sequence is repeated, and it explains why a mussel in a tank leaves a furrow behind it." to
                        "தோண்டுதல் ஒரு வரிசையைப் பின்பற்றுகிறது. பாதம் முன்னோக்கியும் கீழ்நோக்கியும் அடியுள்ளே நீட்டப்பட்டு, முனை இரத்தத்தால் வீங்கி நங்கூரமிடப்படுகிறது. பின்னர் மூடித் தசைகளும் பிற தசைகளும் சுருங்க, பாதத் தசைகள் ஓட்டை நங்கூரமிட்ட பாதத்தை நோக்கி இழுக்கின்றன; எனவே விலங்கு சேற்றுக்குள் நகர்கிறது. பாதத்தின் நீட்சி தசைச் செயலாலும் அதன் இடைவெளிகளிலுள்ள இரத்தத் திரவ அழுத்தத்தாலும் உண்டாகிறது; உள்ளே தள்ளும்போது பாதம் விறைக்கிறது. வரிசை மீண்டும் நிகழ்கிறது; தொட்டியிலுள்ள சிப்பி பின்னால் ஒரு பள்ளத்தை விடுவதற்கு இது காரணம்."),
                    bp("In some bivalves the foot is modified in a further way. Mussels of the sea and some others have a byssus gland at the base of the foot, which secretes proteinaceous threads, the byssal threads, that fix the animal to a rock or other hard surface. These sedentary forms use the foot little in movement, and the foot is small. The comparison with Unio, which burrows, shows how a single organ is modified by the habit of life, to dig in one animal and to spin threads in another." to
                        "சில இருவோடுகளில் பாதம் மேலும் மாற்றப்படுகிறது. கடல் சிப்பிகளுக்கும் வேறு சிலவற்றுக்கும் பாதத்தின் அடியில் பிஸ்ஸஸ் சுரப்பி உள்ளது; அது பாறை அல்லது பிற கடினப் பரப்பில் விலங்கை நிலைநிறுத்தும் புரத இழைகளான பிஸ்ஸஸ் இழைகளைச் சுரக்கிறது. இவ்வமர்ந்த வடிவங்கள் இயக்கத்தில் பாதத்தை குறைவாகப் பயன்படுத்துகின்றன; பாதம் சிறியது. தோண்டும் யூனியோவுடன் ஒப்பீடு, ஒரே உறுப்பு வாழ்க்கைப் பழக்கத்தால் ஒரு விலங்கில் தோண்டவும் மற்றொன்றில் இழை நூற்கவும் மாற்றப்படுவதைக் காட்டுகிறது."),
                    bdef("Byssus", "பிஸ்ஸஸ்", "The bundle of protein threads secreted by a gland at the base of the foot that fixes some bivalves to a surface." to
                        "சில இருவோடுகளை மேற்பரப்பில் நிலைநிறுத்த, பாதத்தின் அடிச் சுரப்பி சுரக்கும் புரத இழைக் கற்றை."),
                    bdef("Byssal gland", "பிஸ்ஸஸ் சுரப்பி", "The gland in the foot that secretes the byssus." to
                        "பிஸ்ஸஸைச் சுரக்கும் பாதத்திலுள்ள சுரப்பி."),
                    bdef("Haemolymph", "ஹீமோலிம்ப்", "The circulating body fluid of a mollusc with an open circulation." to
                        "திறந்த சுற்றோட்டமுடைய மெல்லுடலியின் சுற்றும் உடல் திரவம்."),
                    bdef("Substratum", "அடித்தளம்", "The surface or material on which an animal lives or in which it burrows." to
                        "விலங்கு வாழும் அல்லது தோண்டும் மேற்பரப்பு அல்லது பொருள்."),
                    bdef("Adductor muscle", "உட்புற இழுப்புத் தசை", "A muscle that draws the two valves of a bivalve shell together." to
                        "இருவோட்டு ஓட்டின் இரு மூடிகளையும் இழுத்து மூடும் தசை."),
                )),
            bsec("cephalopod", "The foot of a cephalopod", "தலைக்காலியின் பாதம்",
                blocks = listOf(
                    bp("The name Cephalopoda means head-foot, and it points to the extensive reorganisation of the ancestral foot around the head. The front part of the foot has been drawn into a circle of arms or tentacles round the mouth, and a hinder portion has been made into a muscular tube called the funnel or hyponome. The animal does not creep on a sole, so the broad foot of the gastropod has no counterpart. The arms bear suckers in the squids, cuttlefish and octopuses, and they are used to seize prey, to manipulate objects and, in the octopus, to crawl." to
                        "தலைக்காலி என்ற பெயர் தலை-பாதம் எனப் பொருள்படும்; தலையைச் சுற்றி மூதாதைப் பாதத்தின் விரிவான மறுசீரமைப்பைச் சுட்டுகிறது. பாதத்தின் முன்பகுதி வாயைச் சுற்றிய கரங்கள் அல்லது உணர்கொம்புகளின் வட்டமாக இழுக்கப்பட்டுள்ளது; பின்பகுதி குழல் அல்லது ஹைபோனோம் எனும் தசைக் குழாயாக மாற்றப்பட்டுள்ளது. விலங்கு அடிப்பகுதியில் ஊர்வதில்லை; எனவே இரைப்பைக்காலியின் அகன்ற பாதத்துக்கு இணை இல்லை. ஸ்குவிட், கட்டில்மீன், ஆக்டோபஸ்களில் கரங்கள் உறிஞ்சிகளைத் தாங்கி இரையைப் பற்றவும் பொருட்களைக் கையாளவும், ஆக்டோபஸில் ஊரவும் பயன்படுகின்றன."),
                    bp("The funnel is the more striking modification. When the muscles of the mantle contract, the pressure in the mantle cavity rises, and water is forced out through the funnel in a jet, which drives the animal in the opposite direction. The funnel can be pointed in different directions, and so it steers the animal. It also carries the water that leaves the cavity after it has passed over the gills, along with wastes and, when it is released, the ink. The funnel is therefore a tube of the foot that has become a jet nozzle." to
                        "குழல் இன்னும் கவனிக்கத்தக்க மாற்றம். மேண்டிலின் தசைகள் சுருங்கும்போது மேண்டில் குழியின் அழுத்தம் உயர்ந்து, நீர் குழல் வழியே பீச்சாக வெளியே தள்ளப்பட்டு, விலங்கை எதிர்த்திசையில் செலுத்துகிறது. குழலை வெவ்வேறு திசைகளில் திருப்பலாம்; எனவே அது விலங்கை வழிநடத்துகிறது. செவுள்கள்மேல் கடந்த பின் குழியிலிருந்து வெளியேறும் நீரையும் கழிவுகள், வெளியிடப்படும்போது மையையும் அது கொண்டுசெல்கிறது. எனவே குழல் பீச்சு முனையாக மாறிய பாதத்தின் குழாய்."),
                    bp("Why is the funnel considered part of the foot, when it lies near the head and takes water from the mantle cavity? A student might suppose that it is a siphon of the mantle, like the inhalant and exhalant siphons of the bivalves. The answer is that developmental and comparative evidence, from the nerves that supply it and from the way that it forms in the embryo, identifies it as a derivative of the foot. Its position near the head does not make it a mantle siphon." to
                        "குழல் தலைக்கு அருகிலும் மேண்டில் குழியிலிருந்து நீரை எடுப்பதாகவும் இருக்கும்போது, அது பாதத்தின் பகுதி என ஏன் கருதப்படுகிறது? அது இருவோடுகளின் உள்வரும், வெளிவரும் குழாய்கள் போல மேண்டிலின் குழாய் என மாணவர் கருதலாம். விடை: அதற்கு அளிக்கும் நரம்புகளிலிருந்தும் கருவில் அது உருவாகும் விதத்திலிருந்தும் வரும் வளர்ச்சி, ஒப்பீட்டுச் சான்றுகள் அதை பாதத்தின் வழிப்பெறு அமைப்பாக அடையாளப்படுத்துகின்றன. தலைக்கு அருகிலான அதன் இடம் அதை மேண்டில் குழாயாக்காது."),
                    bnote("Funnel and siphon", "குழலும் சைஃபனும்",
                        "The funnel is a modified part of the foot, not a mantle siphon, though both carry water out of the mantle cavity." to
                            "குழல் பாதத்தின் மாறிய பகுதி; மேண்டில் சைஃபன் அல்ல; இரண்டும் மேண்டில் குழியிலிருந்து நீரை வெளியே கொண்டுசெல்கின்றன."),
                    bdef("Funnel", "குழல்", "The muscular tube of a cephalopod, derived from the foot, through which water is expelled from the mantle cavity." to
                        "மேண்டில் குழியிலிருந்து நீர் வெளியேற்றப்படும், பாதத்திலிருந்து உருவான தலைக்காலியின் தசைக் குழாய்."),
                    bdef("Hyponome", "ஹைபோனோம்", "Another name for the funnel of a cephalopod." to
                        "தலைக்காலியின் குழலுக்கான மற்றொரு பெயர்."),
                    bdef("Jet propulsion", "பீச்சு உந்துதல்", "Movement produced by expelling a jet of water in the opposite direction." to
                        "எதிர்த்திசையில் நீர்ப் பீச்சை வெளியேற்றுவதால் உண்டாகும் இயக்கம்."),
                    bdef("Sucker", "உறிஞ்சி", "A muscular cup on the arm of a cephalopod used for gripping." to
                        "பற்றுவதற்குப் பயன்படும் தலைக்காலிக் கரத்திலுள்ள தசைக் கிண்ணம்."),
                )),
            bsec("comparison", "Comparison and the argument from homology", "ஒப்பீடும் ஒத்தமைவு வாதமும்",
                blocks = listOf(
                    btable("The foot in three classes", "மூன்று வகுப்புகளில் பாதம்",
                        listOf("Feature" to "பண்பு", "Gastropod (Pila)" to "இரைப்பைக்காலி (பைலா)", "Bivalve (Unio)" to "இருவோடு (யூனியோ)", "Cephalopod" to "தலைக்காலி"),
                        listOf(
                            listOf("Form" to "வடிவம்", "Broad, flat sole" to "அகன்ற, தட்டையான அடிப்பகுதி", "Wedge or hatchet" to "ஆப்பு அல்லது கோடாரி", "Arms and funnel" to "கரங்களும் குழலும்"),
                            listOf("Action" to "செயல்", "Creeping by pedal waves on mucus" to "சளிமேல் பெடல் அலைகளால் ஊர்தல்", "Burrowing by haemolymph pressure and muscle" to "இரத்தத் திரவ அழுத்தம், தசையால் தோண்டுதல்", "Seizing prey; jet propulsion" to "இரை பற்றுதல்; பீச்சு உந்துதல்"),
                            listOf("Special feature" to "சிறப்புப் பண்பு", "Operculum seals the aperture" to "ஆப்பர்குலம் வாயை அடைக்கிறது", "Byssus in sedentary forms" to "அமர்ந்த வடிவங்களில் பிஸ்ஸஸ்", "Suckers on the arms" to "கரங்களில் உறிஞ்சிகள்"),
                            listOf("Way of life" to "வாழ்க்கை முறை", "Slow grazer" to "மெதுவாக மேய்வது", "Burrower or sedentary filter feeder" to "தோண்டுவது அல்லது அமர்ந்த வடிகட்டி உண்ணி", "Active swimmer and predator" to "சுறுசுறுப்பான நீச்சல்காரர், வேட்டையாடி"),
                        )),
                    bp("The three feet are homologous, which is to say that they have the same origin as the ventral muscular foot of the basic molluscan plan. They have diverged in the classes into different functions, and the differences follow the way of life. This is the central point of the answer, and the student should state the homology first and then illustrate the divergence." to
                        "மூன்று பாதங்களும் ஒத்தமைவானவை; அதாவது அடிப்படை மெல்லுடலித் திட்டத்தின் வயிற்றுப்புற தசைப் பாதத்தின் அதே தோற்றம் கொண்டவை. வகுப்புகளில் அவை வெவ்வேறு செயல்களுக்கு வேறுபட்டுப் பிரிந்துள்ளன; வேறுபாடுகள் வாழ்க்கை முறையைப் பின்பற்றுகின்றன. இதுவே விடையின் மையப் புள்ளி; மாணவர் ஒத்தமைவை முதலில் கூறி, பின்னர் பிரிவை விளக்க வேண்டும்."),
                    bp("The student must not present the three as a ladder from simple to advanced, in which the gastropod is the primitive state and the cephalopod the best. The living classes are the products of long and separate histories, and each is well fitted to its own life. A cephalopod is not a more developed snail, and a snail is not an unfinished cephalopod. The wording should be that the foot is more specialised in one class than another for a particular function, and not that one is better." to
                        "மாணவர் மூன்றையும் எளிமையிலிருந்து மேம்பட்டது வரையான படிக்கட்டாக, இரைப்பைக்காலி ஆதி நிலை, தலைக்காலி சிறந்தது என்று, முன்வைக்கக் கூடாது. வாழும் வகுப்புகள் நீண்ட, தனித்த வரலாறுகளின் விளைவுகள்; ஒவ்வொன்றும் தன் வாழ்க்கைக்கு நன்கு பொருந்தியது. தலைக்காலி மேம்பட்ட நத்தை அல்ல; நத்தை முடிவடையாத தலைக்காலி அல்ல. சொற்றொடர், ஒரு குறிப்பிட்ட செயலுக்கு ஒரு வகுப்பில் பாதம் மற்றொன்றை விட அதிகம் சிறப்படைந்துள்ளது என்பதாக இருக்க வேண்டும்; ஒன்று சிறந்தது என்பதாக அல்ல."),
                    bdef("Divergence", "விலகல்", "The evolution of different forms and functions from a common ancestral structure." to
                        "பொது மூதாதை அமைப்பிலிருந்து வெவ்வேறு வடிவங்கள், செயல்கள் பரிணமித்தல்."),
                    bdef("Specialisation", "சிறப்படைதல்", "Adaptation of a structure to a particular function or way of life." to
                        "அமைப்பை குறிப்பிட்ட செயல் அல்லது வாழ்க்கை முறைக்கு ஏற்ப மாற்றுதல்."),
                )),
        ),
        summary = listOf(
            "The molluscan foot is a ventral muscular organ that works with a hydrostatic skeleton of blood spaces." to "மெல்லுடலிப் பாதம் இரத்த இடைவெளிகளின் நீர்நிலைக் கூடுடன் செயல்படும் வயிற்றுப்புற தசை உறுப்பு.",
            "In Pila the foot is a creeping sole with pedal waves, and it carries the operculum." to "பைலாவில் பாதம் பெடல் அலைகளுடன் ஊரும் அடிப்பகுதி; ஆப்பர்குலத்தையும் தாங்குகிறது.",
            "In Unio the foot is a wedge used for burrowing; in some bivalves a byssus gland fixes the animal." to "யூனியோவில் பாதம் தோண்டும் ஆப்பு; சில இருவோடுகளில் பிஸ்ஸஸ் சுரப்பி விலங்கை நிலைநிறுத்துகிறது.",
            "In cephalopods the foot forms arms around the head and the funnel for jet propulsion." to "தலைக்காலிகளில் பாதம் தலையைச் சுற்றிய கரங்களையும் பீச்சு உந்துதலுக்கான குழலையும் உருவாக்குகிறது.",
            "The funnel is a derivative of the foot, not a mantle siphon." to "குழல் பாதத்தின் வழிப்பெறு அமைப்பு; மேண்டில் சைஃபன் அல்ல.",
            "The three feet are homologous and have diverged; they are not steps in a ladder of improvement." to "மூன்று பாதங்களும் ஒத்தமைவானவை, விலகியவை; மேம்பாட்டுப் படிக்கட்டின் படிகள் அல்ல.",
        ),
        checks = listOf(
            bqc("What is the operculum, and which animal here has it?", "ஆப்பர்குலம் என்றால் என்ன, இங்கு எந்த விலங்குக்கு உண்டு?",
                "A horny plate on the hind foot that closes the shell aperture; Pila.", "ஓட்டு வாயை மூடும் பின் பாதத்தின் கொம்புத் தகடு; பைலா."),
            bqc("How does Unio burrow?", "யூனியோ எவ்வாறு தோண்டுகிறது?",
                "The foot is thrust into the substratum and anchored by its swollen tip, then the muscles draw the shell down.", "பாதம் அடித்தளத்துக்குள் தள்ளப்பட்டு வீங்கிய முனையால் நங்கூரமிடப்படுகிறது; பின்னர் தசைகள் ஓட்டை கீழே இழுக்கின்றன."),
            bqc("What is a byssus?", "பிஸ்ஸஸ் என்றால் என்ன?",
                "Protein threads secreted by a gland at the base of the foot that attach some bivalves to a surface.", "சில இருவோடுகளை மேற்பரப்பில் இணைக்கும், பாதத்தின் அடிச் சுரப்பி சுரக்கும் புரத இழைகள்."),
            bqc("Why is the funnel not a mantle siphon?", "குழல் ஏன் மேண்டில் சைஃபன் அல்ல?",
                "Developmental and comparative evidence shows that it is derived from the foot.", "வளர்ச்சி, ஒப்பீட்டுச் சான்றுகள் அது பாதத்திலிருந்து உருவானது எனக் காட்டுகின்றன."),
        ),
    )
}
