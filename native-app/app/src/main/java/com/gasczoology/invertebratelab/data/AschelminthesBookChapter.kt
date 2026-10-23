package com.gasczoology.invertebratelab.data

/** Book-style chapter: Aschelminthes (syllabus group) with emphasis on Nematoda. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object AschelminthesBookChapter {
    val chapter = bchapter(
        lessonId = "u3-aschel",
        title = "Aschelminthes and the place of Nematoda: general characters" to
            "அஸ்கெல்மிந்தீஸும் நெமட்டோடாவின் இடமும்: பொதுப்பண்புகள்",
        objectives = listOf(
            "Explain what the syllabus name Aschelminthes means and why Nematoda is treated today as a separate phylum." to "பாடத்திட்டப் பெயரான அஸ்கெல்மிந்தீஸ் என்பதன் பொருளையும், நெமட்டோடா இன்று தனித் தொகுதியாகக் கருதப்படுவதன் காரணத்தையும் விளக்க.",
            "State the general characters of nematodes." to "நெமட்டோடாக்களின் பொதுப்பண்புகளைக் கூற.",
            "Describe the cuticle, the longitudinal muscles and the pseudocoelom and explain how they work together." to "கியூட்டிகிள், நீளவாக்கு தசைகள், போலி உடற்குழி ஆகியவற்றை விவரித்து, அவை இணைந்து செயல்படும் விதத்தை விளக்க.",
            "Explain why nematodes move with a whip-like thrash rather than peristalsis." to "நெமட்டோடாக்கள் புழு போன்ற அலைவுக்கு பதிலாக சாட்டை போன்ற அசைவில் நகர்வது ஏன் என்பதை விளக்க.",
            "Distinguish a pseudocoelom from a true coelom." to "போலி உடற்குழியை உண்மையான சீலோமிலிருந்து வேறுபடுத்த.",
        ),
        introduction = listOf(
            "The roundworms are the commonest and most widely distributed of the multicellular animals. They live in the sea, in fresh water, in soil and in the bodies of plants and animals, and a spadeful of soil or a handful of mud may contain thousands of them. Many are tiny and free-living, and many are parasites, including some of the most important parasites of people, such as Ascaris, hookworms and the filarial worms." to
                "உருளைப்புழுக்கள் பலசெல் விலங்குகளில் மிகப் பொதுவானவையும் மிக விரிவாகப் பரவியவையும். கடல், நன்னீர், மண், தாவர, விலங்கு உடல்களில் வாழ்கின்றன; ஒரு மண்வெட்டி மண்ணிலோ ஒரு கைப்பிடி சேற்றிலோ ஆயிரக்கணக்கானவை இருக்கலாம். பல நுண்ணியவை, தனி வாழ்வுடையவை; பல ஒட்டுண்ணிகள்; அஸ்காரிஸ், கொக்கிப்புழுக்கள், ஃபைலேரியா புழுக்கள் போன்ற மனிதர்களின் மிக முக்கிய ஒட்டுண்ணிகளும் அவற்றில் அடங்கும்.",
            "The paper uses the name Aschelminthes for the group of worms to which they belong, and this chapter first explains what that name means and how it stands in relation to current classification. It then sets out the characters of a nematode, the way in which the body is built and moves, and the way in which a roundworm differs from the flatworms already studied and from the earthworm to be studied later." to
                "அவை சேர்ந்த புழுக் குழுவுக்குத் தாள் அஸ்கெல்மிந்தீஸ் என்ற பெயரைப் பயன்படுத்துகிறது; இந்த அத்தியாயம் முதலில் அப்பெயரின் பொருளையும், அது தற்கால வகைப்பாட்டுடன் எவ்வாறு தொடர்புடையது என்பதையும் விளக்குகிறது. பின்னர் நெமட்டோடாவின் பண்புகளையும், உடல் கட்டமைக்கப்பட்டு இயங்கும் விதத்தையும், ஏற்கெனவே கற்ற தட்டைப்புழுக்களிலிருந்தும் பின்னர் கற்கவிருக்கும் மண்புழுவிலிருந்தும் உருளைப்புழு எவ்வாறு வேறுபடுகிறது என்பதையும் முன்வைக்கிறது.",
        ),
        sections = listOf(
            bsec("name", "The name Aschelminthes", "அஸ்கெல்மிந்தீஸ் என்ற பெயர்",
                blocks = listOf(
                    bp("The name Aschelminthes, meaning cavity worms, was used for a collection of worm-like animals that have a fluid-filled body cavity which is not a true coelom. The group traditionally included the rotifers, gastrotrichs, kinorhynchs, nematodes and some others, which were treated as classes of a single phylum. The grouping was convenient but artificial, because the animals in it differ greatly and do not share a single common ancestor to the exclusion of others." to
                        "குழிப்புழுக்கள் எனப் பொருள்படும் அஸ்கெல்மிந்தீஸ் என்ற பெயர், உண்மையான சீலோம் அல்லாத திரவம் நிறைந்த உடற்குழி கொண்ட புழு போன்ற விலங்குகளின் தொகுப்புக்குப் பயன்படுத்தப்பட்டது. ரோட்டிஃபெர்கள், காஸ்ட்ரோட்ரிக்குகள், கினோரிங்க்குகள், நெமட்டோடாக்கள், வேறு சிலவும் இக்குழுவில் பாரம்பரியமாக அடங்கி, ஒரே தொகுதியின் வகுப்புகளாகக் கருதப்பட்டன. இந்த குழுமம் வசதியானது ஆனால் செயற்கையானது; ஏனெனில் அதிலுள்ள விலங்குகள் பெரிதும் வேறுபடுகின்றன, பிறவற்றை விலக்கி ஒரே பொது மூதாதையைப் பகிர்வதில்லை."),
                    bp("Modern zoology treats each of these groups as a separate phylum, and the Nematoda are therefore a phylum in their own right. The syllabus retains the older name, and the answer to a question on Aschelminthes should say so, and should then give the characters of the Nematoda, which are the focus of the unit. It is a safe practice to write, in an answer, that Aschelminthes is a historical grouping of pseudocoelomate worm-like animals." to
                        "நவீன விலங்கியல் இவற்றில் ஒவ்வொரு குழுவையும் தனித் தொகுதியாகக் கருதுகிறது; எனவே நெமட்டோடா தனக்கே உரிய தொகுதி. பாடத்திட்டம் பழைய பெயரைத் தக்கவைக்கிறது; அஸ்கெல்மிந்தீஸ் குறித்த வினாவின் விடை அதைக் குறிப்பிட்டு, பின்னர் அலகின் மையமான நெமட்டோடாவின் பண்புகளைத் தர வேண்டும். அஸ்கெல்மிந்தீஸ் என்பது போலி உடற்குழி கொண்ட புழு போன்ற விலங்குகளின் வரலாற்றுக் குழுமம் என்று விடையில் எழுதுவது பாதுகாப்பான நடைமுறை."),
                    bdef("Aschelminthes", "அஸ்கெல்மிந்தீஸ்", "A traditional grouping of pseudocoelomate worm-like animals, including the nematodes, now regarded as an artificial assemblage of separate phyla." to
                        "நெமட்டோடாக்கள் உட்பட போலி உடற்குழி கொண்ட புழு போன்ற விலங்குகளின் பாரம்பரிய குழுமம்; இப்போது தனித் தொகுதிகளின் செயற்கைத் தொகுப்பாகக் கருதப்படுகிறது."),
                    bdef("Nematoda", "நெமட்டோடா", "The phylum of roundworms, with a tough cuticle, a pseudocoelom, a complete gut and longitudinal muscles only." to
                        "கடினமான கியூட்டிகிள், போலி உடற்குழி, முழுமையான குடல், நீளவாக்கு தசைகள் மட்டும் கொண்ட உருளைப்புழுக்களின் தொகுதி."),
                )),
            bsec("characters", "General characters", "பொதுப்பண்புகள்",
                blocks = listOf(
                    bpoints("Form and covering", "வடிவமும் உறையும்",
                        "Bilaterally symmetrical, triploblastic and unsegmented." to "இருபக்கச் சமச்சீர், மூவடுக்கு, கண்டமற்றவை.",
                        "Cylindrical (round in cross-section) and usually tapering at both ends; the body is not flattened." to "உருளை வடிவம் (குறுக்கு வெட்டில் வட்டம்); பொதுவாக இரு முனைகளிலும் குறுகும்; உடல் தட்டையல்ல.",
                        "Covered by a tough, flexible, non-living cuticle secreted by the epidermis, which is moulted several times during growth." to "எப்பிடெர்மிஸ் சுரக்கும் கடினமான, நெகிழ்வான, உயிரற்ற கியூட்டிகிளால் மூடப்பட்டுள்ளது; வளர்ச்சியின்போது பலமுறை உதிர்க்கப்படுகிறது.",
                    ),
                    bpoints("Body cavity and muscles", "உடற்குழியும் தசைகளும்",
                        "The body cavity is a fluid-filled pseudocoelom, lying between the body wall and the gut and not lined by mesoderm on its inner side." to "உடற்குழி திரவம் நிறைந்த போலி உடற்குழி; உடற்சுவருக்கும் குடலுக்கும் இடையில் உள்ளது; உட்புறம் மீசோடெர்மால் மூடப்படவில்லை.",
                        "The body wall has longitudinal muscles only; circular muscles are absent." to "உடற்சுவரில் நீளவாக்கு தசைகள் மட்டுமே; வட்ட தசைகள் இல்லை.",
                        "The cuticle, muscles and fluid of the pseudocoelom together form a hydrostatic skeleton." to "கியூட்டிகிள், தசைகள், போலி உடற்குழி திரவம் ஆகியவை இணைந்து நீரியல் எலும்புக்கூட்டை உருவாக்குகின்றன.",
                    ),
                    bpoints("Systems of the body", "உடல் மண்டலங்கள்",
                        "The alimentary canal is complete and straight, with a separate mouth and anus." to "உணவுக்குழல் முழுமையானது, நேரானது; தனி வாய், மலவாய் உண்டு.",
                        "Circulatory and respiratory systems are absent; the pseudocoelomic fluid transports substances." to "சுற்றோட்ட, சுவாச மண்டலங்கள் இல்லை; போலி உடற்குழி திரவம் பொருட்களைக் கடத்துகிறது.",
                        "The excretory system has no flame cells; it consists of an H-shaped system of canals in the lateral lines or of gland cells." to "கழிவு நீக்க மண்டலத்தில் சுடர் செல்கள் இல்லை; பக்கக் கோடுகளிலுள்ள H வடிவ கால்வாய் அமைப்பு அல்லது சுரப்பி செல்களைக் கொண்டது.",
                        "The nervous system is a ring around the pharynx with longitudinal nerve cords, of which the dorsal and ventral are the main ones." to "நரம்பு மண்டலம் தொண்டையைச் சுற்றிய வளையமும் நீளவாக்கு நரம்பு வடங்களும்; முதுகு, வயிற்று வடங்கள் முதன்மையானவை.",
                    ),
                    bpoints("Reproduction", "இனப்பெருக்கம்",
                        "The sexes are usually separate (dioecious), and the female is often larger than the male; this is sexual dimorphism." to "பாலினங்கள் பொதுவாகத் தனித்தனி (ஒருபாலுயிரிகள்); பெண் பெரும்பாலும் ஆணைவிடப் பெரியது; இது பாலின இருவடிவம்.",
                        "Fertilisation is internal, and development is usually direct, with a series of juvenile stages and four moults." to "கருவுறுதல் உட்புறம்; வளர்ச்சி பொதுவாக நேரடியானது; தொடர் இளம் நிலைகளும் நான்கு உதிர்ப்புகளும் உண்டு.",
                        "Free-living and parasitic forms occur, the parasites inhabiting plants, invertebrates and vertebrates." to "தனி வாழ்வு, ஒட்டுண்ணி வடிவங்கள் உள்ளன; ஒட்டுண்ணிகள் தாவரங்கள், முதுகெலும்பிலிகள், முதுகெலும்பிகளில் வாழ்கின்றன.",
                    ),
                    bdef("Pseudocoelom", "போலி உடற்குழி", "A fluid-filled body cavity between the gut and the body wall which is not completely lined by mesoderm." to
                        "மீசோடெர்மால் முழுமையாக மூடப்படாத, குடலுக்கும் உடற்சுவருக்கும் இடையிலான திரவம் நிறைந்த உடற்குழி."),
                    bdef("Cuticle", "கியூட்டிகிள்", "The tough, non-living covering secreted by the epidermis of a nematode, which is moulted during growth." to
                        "நெமட்டோடாவின் எப்பிடெர்மிஸ் சுரக்கும் கடினமான, உயிரற்ற உறை; வளர்ச்சியின்போது உதிர்க்கப்படுகிறது."),
                    bdef("Dioecious", "ஒருபாலுயிரி", "Having the male and female organs in separate individuals." to
                        "ஆண், பெண் உறுப்புகள் தனித்தனி உயிரிகளில் இருக்கும் நிலை."),
                    bdef("Sexual dimorphism", "பாலின இருவடிவம்", "A difference in form between the male and the female of a species." to
                        "ஓர் இனத்தின் ஆணுக்கும் பெண்ணுக்கும் இடையிலான வடிவ வேறுபாடு."),
                    bdef("Moulting", "உதிர்த்தல்", "The shedding of the old cuticle and the formation of a new one as the animal grows." to
                        "விலங்கு வளரும்போது பழைய கியூட்டிகிளை உதிர்த்துப் புதியதை உருவாக்குதல்."),
                )),
            bsec("pseudocoelom", "The pseudocoelom and the hydrostatic skeleton", "போலி உடற்குழியும் நீரியல் எலும்புக்கூடும்",
                blocks = listOf(
                    bp("A true coelom is a cavity within the mesoderm, lined on both the body-wall side and the gut side by mesodermal epithelium (peritoneum). The cavity of a nematode lies between the mesoderm of the body wall and the endoderm of the gut, and has a peritoneal lining on one side only, or none. It is therefore called a pseudocoelom, or false coelom. The difference is not simply one of vocabulary, since the lining of a true coelom helps in the suspension and the movement of the gut and in the development of organs, which a pseudocoelom does not do in the same way." to
                        "உண்மையான சீலோம் மீசோடெர்மினுள் உள்ள குழி; உடற்சுவர் பக்கமும் குடல் பக்கமும் மீசோடெர்ம் எப்பிதீலியத்தால் (பெரிட்டோனியம்) மூடப்பட்டிருக்கும். நெமட்டோடாவின் குழி உடற்சுவரின் மீசோடெர்முக்கும் குடலின் எண்டோடெர்முக்கும் இடையில் உள்ளது; ஒரு பக்கத்தில் மட்டும் பெரிட்டோனிய மூடல் உண்டு, அல்லது இல்லை. எனவே இது போலி உடற்குழி அல்லது பொய் சீலோம் எனப்படுகிறது. வேறுபாடு சொற்களில் மட்டுமல்ல; உண்மை சீலோமின் மூடல் குடலைத் தொங்கவிடவும் இயக்கவும் உறுப்புகள் வளரவும் உதவுகிறது; போலி உடற்குழி அதே விதத்தில் செய்வதில்லை."),
                    btable("True coelom and pseudocoelom compared", "உண்மை சீலோமும் போலி உடற்குழியும் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "True coelom" to "உண்மை சீலோம்", "Pseudocoelom" to "போலி உடற்குழி"),
                        listOf(
                            listOf("Position" to "இடம்", "Within the mesoderm" to "மீசோடெர்முக்குள்", "Between mesoderm and endoderm" to "மீசோடெர்முக்கும் எண்டோடெர்முக்கும் இடையே"),
                            listOf("Lining" to "மூடல்", "Peritoneum on both sides" to "இரு பக்கமும் பெரிட்டோனியம்", "Not lined by mesoderm on the gut side" to "குடல் பக்கத்தில் மீசோடெர்ம் மூடல் இல்லை"),
                            listOf("Examples" to "எடுத்துக்காட்டுகள்", "Earthworm, Nereis" to "மண்புழு, நீரிஸ்", "Ascaris, other nematodes" to "அஸ்காரிஸ், பிற நெமட்டோடாக்கள்"),
                        )),
                    bp("The fluid of the pseudocoelom is under pressure, and as it cannot be compressed it acts with the elastic cuticle and the muscles as a hydrostatic skeleton. This gives the worm a rigid, cylindrical body that resists the pull of the muscles, and the pressure explains why a roundworm is turgid and springs back when it is cut open. The fluid also moves nutrients and wastes about the body in the absence of a circulatory system." to
                        "போலி உடற்குழி திரவம் அழுத்தத்தில் உள்ளது; அதை அழுத்த முடியாததால், மீள்தன்மை கியூட்டிகிள், தசைகளுடன் நீரியல் எலும்புக்கூடாகச் செயல்படுகிறது. இது தசை இழுப்பை எதிர்க்கும் உறுதியான, உருளை வடிவ உடலைப் புழுவுக்குத் தருகிறது; உருளைப்புழு விறைப்பாக இருந்து வெட்டினால் மீண்டும் துள்ளுவதற்கான காரணம் இந்த அழுத்தம். சுற்றோட்ட மண்டலம் இல்லாத நிலையில், ஊட்டச்சத்துகளையும் கழிவுகளையும் உடல் முழுவதும் திரவம் கடத்துகிறது."),
                )),
            bsec("movement", "Movement: why a nematode thrashes", "இயக்கம்: நெமட்டோடா ஏன் அலைகிறது",
                blocks = listOf(
                    bp("A roundworm moves by bending its body from side to side in a whip-like or sinuous thrash, and it does not creep by peristaltic waves as an earthworm does. The reason lies in the muscle layers of the body wall. Nematodes have longitudinal muscles only, arranged in four bands, and no circular muscles. When the longitudinal muscles on one side contract, the body on that side is shortened and the opposite side, held at constant length by the cuticle and the pressure of the fluid, is stretched, and the body bends. Alternate contraction on the two sides gives a series of waves along the body." to
                        "உருளைப்புழு தன் உடலை பக்கவாட்டில் சாட்டை போன்ற அல்லது வளைந்த அலைவில் வளைத்து நகர்கிறது; மண்புழுவைப்போல் நெளியும் அலைகளால் ஊர்வதில்லை. காரணம் உடற்சுவரின் தசை அடுக்குகளில் உள்ளது. நெமட்டோடாக்களுக்கு நான்கு பட்டைகளாக அமைந்த நீளவாக்கு தசைகள் மட்டுமே உள்ளன; வட்ட தசைகள் இல்லை. ஒரு பக்க நீளவாக்கு தசைகள் சுருங்கும்போது அப்பக்க உடல் குட்டையாகிறது; கியூட்டிகிளும் திரவ அழுத்தமும் நிலையான நீளத்தில் வைத்திருக்கும் எதிர்ப்பக்கம் இழுக்கப்படுகிறது; உடல் வளைகிறது. இருபுறமும் மாறிமாறி சுருங்குவது உடல் வழியே தொடர் அலைகளைத் தருகிறது."),
                    bp("An earthworm, by contrast, has both circular and longitudinal muscles. The circular muscles make a part of the body long and thin, and the longitudinal muscles make it short and thick, and the earthworm uses the setae to hold the shortened part in place while the other part extends. Its coelom is divided by septa into compartments, each with its own pressure. A nematode has no circular muscles, no septa, and no setae, and so its mechanics are different, and the difference should not be overlooked in an answer." to
                        "மண்புழுவுக்கு இதற்கு மாறாக, வட்ட, நீளவாக்கு தசைகள் இரண்டும் உள்ளன. வட்ட தசைகள் உடலின் ஒரு பகுதியை நீளமாகவும் மெலிதாகவும், நீளவாக்கு தசைகள் குட்டையாகவும் தடிமனாகவும் ஆக்குகின்றன; குட்டையான பகுதியை நிலைநிறுத்த செட்டாக்களைப் பயன்படுத்தி மற்றொரு பகுதியை நீட்டுகிறது. அதன் சீலோம் தடுப்புகளால் தனித்தனி அழுத்தமுடைய அறைகளாகப் பிரிக்கப்பட்டுள்ளது. நெமட்டோடாவுக்கு வட்ட தசைகள், தடுப்புகள், செட்டாக்கள் இல்லை; எனவே அதன் இயக்கவியல் வேறுபட்டது; விடையில் இவ்வேறுபாட்டை புறக்கணிக்கக் கூடாது."),
                    btable("Movement of a nematode and an earthworm", "நெமட்டோடாவும் மண்புழுவும் இயக்கம்",
                        listOf("Feature" to "பண்பு", "Nematode" to "நெமட்டோடா", "Earthworm" to "மண்புழு"),
                        listOf(
                            listOf("Muscle layers" to "தசை அடுக்குகள்", "Longitudinal only" to "நீளவாக்கு மட்டும்", "Circular and longitudinal" to "வட்டம், நீளவாக்கு"),
                            listOf("Body cavity" to "உடற்குழி", "Undivided pseudocoelom" to "பிரிக்கப்படாத போலி உடற்குழி", "Coelom divided by septa" to "தடுப்புகளால் பிரிக்கப்பட்ட சீலோம்"),
                            listOf("Anchorage" to "நிலைநிறுத்தம்", "None" to "இல்லை", "Setae" to "செட்டாக்கள்"),
                            listOf("Movement" to "இயக்கம்", "Side-to-side thrash" to "பக்கவாட்டு அலைவு", "Peristaltic crawling" to "அலைவு ஊர்தல் (பெரிஸ்டால்சிஸ்)"),
                        )),
                    bdef("Hydrostatic skeleton", "நீரியல் எலும்புக்கூடு", "A skeleton formed by fluid under pressure in a closed body cavity, against which muscles work." to
                        "மூடிய உடற்குழியில் அழுத்தத்திலுள்ள திரவத்தால் உருவான எலும்புக்கூடு; தசைகள் அதற்கு எதிராகச் செயல்படுகின்றன."),
                    bdef("Peristalsis", "பெரிஸ்டால்சிஸ்", "A wave of alternate contraction of circular and longitudinal muscles which passes along the body or a tube." to
                        "உடல் அல்லது குழாய் வழியே செல்லும் வட்ட, நீளவாக்கு தசைகளின் மாறிமாறிச் சுருங்கும் அலை."),
                )),
            bsec("systems", "Organ systems in brief", "உறுப்பு மண்டலங்கள் சுருக்கமாக",
                blocks = listOf(
                    bp("The alimentary canal of a nematode is a straight tube, with a mouth at the front, a muscular pharynx that pumps food in, a long intestine whose wall is one cell thick and a short rectum that opens at the anus. It is the first complete gut in the course. Because food passes in one direction through regions of different function, digestion can be more efficient than in a gut with a single opening." to
                        "நெமட்டோடாவின் உணவுக்குழல் நேரான குழாய்; முன்னால் வாய், உணவை உள்ளே செலுத்தும் தசைத் தொண்டை, ஒரு செல் தடிமன் சுவருள்ள நீண்ட குடல், மலவாயில் திறக்கும் குறுகிய மலக்குடல். இது பாடத்தில் முதல் முழுமையான குடல். உணவு வெவ்வேறு பணிகளுடைய பகுதிகள் வழியாக ஒரே திசையில் செல்வதால், ஒரே திறப்புடைய குடலைவிட செரிமானம் திறமையாக இருக்க முடியும்."),
                    bp("The excretory system consists of one or two large gland cells, or of a pair of lateral canals joined across the body in an H shape, which open by a pore on the ventral side near the pharynx. The nervous system has a ring around the pharynx, from which dorsal and ventral nerve cords run backwards, together with smaller cords and sensory structures at the head, the amphids and papillae. A curious feature is that the muscle cells send out small projections to the nerve cords, and not the other way round." to
                        "கழிவு நீக்க மண்டலத்தில் ஒன்று அல்லது இரண்டு பெரிய சுரப்பி செல்கள், அல்லது உடலுக்குக் குறுக்கே H வடிவில் இணைந்த ஒரு இணை பக்கக் கால்வாய்கள் உள்ளன; அவை தொண்டைக்கு அருகே வயிற்றுப் புறத் துளை வழியாகத் திறக்கின்றன. நரம்பு மண்டலத்தில் தொண்டையைச் சுற்றிய வளையம் உள்ளது; அதிலிருந்து முதுகு, வயிற்று நரம்பு வடங்கள் பின்னோக்கிச் செல்கின்றன; தலையில் ஆம்ஃபிட்கள், பாப்பில்லாக்கள் எனும் சிறு வடங்களும் உணர் அமைப்புகளும் உள்ளன. வியப்பான பண்பு: தசை செல்களே நரம்பு வடங்களுக்கு சிறு நீட்சிகளை அனுப்புகின்றன; எதிர்மாறாக அல்ல."),
                    bp("The male and female reproductive systems are tubular and lie in separate individuals. In the female the tubes are paired and end in a vagina, and in the male the single tube ends in a cloaca with copulatory spicules. The female is often larger, and the male may have a curved posterior end. Development is direct, with larval stages that are separated by four moults before the adult stage is reached." to
                        "ஆண், பெண் இனப்பெருக்க மண்டலங்கள் குழாய் வடிவம், தனித்தனி உயிரிகளில் உள்ளன. பெண்ணில் குழாய்கள் இணையாக இருந்து யோனியில் முடிகின்றன; ஆணில் ஒற்றைக் குழாய் புணர்ச்சி ஸ்பிக்யூல்களுடன் குளோயாக்காவில் முடிகிறது. பெண் பெரும்பாலும் பெரியது; ஆணின் பின் முனை வளைந்திருக்கலாம். வளர்ச்சி நேரடியானது; முதிர்ந்த நிலையை அடையும் முன் நான்கு உதிர்ப்புகளால் பிரிக்கப்பட்ட லார்வா நிலைகள் உண்டு."),
                    bdef("Amphid", "ஆம்ஃபிட்", "One of a pair of chemosensory organs at the front of a nematode." to
                        "நெமட்டோடாவின் முன்புறத்தில் உள்ள ஒரு இணை வேதி உணர் உறுப்புகளில் ஒன்று."),
                    bdef("Spicule", "ஸ்பிக்யூல்", "A needle-like copulatory structure in the male nematode, used to hold the female during mating." to
                        "புணர்ச்சியின்போது பெண்ணைப் பற்றிக்கொள்ள ஆண் நெமட்டோடாவிலுள்ள ஊசி போன்ற புணர்ச்சி அமைப்பு."),
                )),
            bsec("importance", "Nematodes in nature and in human affairs", "இயற்கையிலும் மனித வாழ்விலும் நெமட்டோடாக்கள்",
                blocks = listOf(
                    bp("Free-living nematodes are of great importance in soils and in sediments, where they feed on bacteria, fungi and other small organisms and help to recycle nutrients. The parasites include the roundworms, hookworms and filarial worms of man, the pinworms that infect children, and the species that damage crops by feeding on the roots of plants. The syllabus therefore follows the general characters by the study of Ascaris, as a type, and then by other parasites in the chapters that follow." to
                        "தனி வாழ்வு நெமட்டோடாக்கள் மண்ணிலும் வண்டலிலும் மிக முக்கியமானவை; பாக்டீரியா, பூஞ்சை, பிற சிறு உயிரிகளை உண்டு ஊட்டச்சத்து மறுசுழற்சிக்கு உதவுகின்றன. ஒட்டுண்ணிகளில் மனிதனின் உருளைப்புழுக்கள், கொக்கிப்புழுக்கள், ஃபைலேரியா புழுக்கள், குழந்தைகளைத் தொற்றும் குடற்புழுக்கள், தாவர வேர்களை உண்டு பயிர்களைச் சேதப்படுத்தும் இனங்கள் அடங்கும். எனவே பாடத்திட்டம் பொதுப்பண்புகளைத் தொடர்ந்து அஸ்காரிஸை வகை எடுத்துக்காட்டாகவும், பின்வரும் அத்தியாயங்களில் பிற ஒட்டுண்ணிகளையும் கற்பிக்கிறது."),
                    bp("In an answer on the general characters, the examiner will look for the pseudocoelom, the cuticle, the longitudinal muscles only, the complete gut, and the separate sexes, and will expect the writer to keep the nematode distinct from the flatworm on the one side and the annelid on the other." to
                        "பொதுப்பண்புகள் குறித்த விடையில், தேர்வாளர் போலி உடற்குழி, கியூட்டிகிள், நீளவாக்கு தசைகள் மட்டும், முழுமையான குடல், தனிப் பாலினங்கள் ஆகியவற்றைப் பார்ப்பார்; எழுதுபவர் நெமட்டோடாவை ஒருபுறம் தட்டைப்புழுவிலிருந்தும் மறுபுறம் வளையப்புழுவிலிருந்தும் வேறுபடுத்தி வைத்திருப்பார் என எதிர்பார்ப்பார்."),
                    bpoints("A plan for the answer", "விடைக்கான திட்டம்",
                        "Begin with the status of the name Aschelminthes and the place of the Nematoda." to "அஸ்கெல்மிந்தீஸ் என்ற பெயரின் நிலையையும் நெமட்டோடாவின் இடத்தையும் கூறி தொடங்குக.",
                        "List the characters in groups: form and covering, cavity and muscles, systems, reproduction." to "பண்புகளை குழுக்களாகப் பட்டியலிடுக: வடிவமும் உறையும், குழியும் தசைகளும், மண்டலங்கள், இனப்பெருக்கம்.",
                        "Explain the hydrostatic skeleton and the whip-like movement, since these are common questions." to "நீரியல் எலும்புக்கூடு, சாட்டை போன்ற இயக்கத்தை விளக்குக; இவை பொதுவான வினாக்கள்.",
                        "Contrast the nematode with the flatworm (complete gut, pseudocoelom, separate sexes) and with the earthworm (no circular muscle, no septa)." to "நெமட்டோடாவை தட்டைப்புழுவுடன் (முழுமையான குடல், போலி உடற்குழி, தனிப் பாலினம்) மண்புழுவுடன் (வட்ட தசை இல்லை, தடுப்புகள் இல்லை) ஒப்பிடுக.",
                        "Name Ascaris as the type, and state that the sexes are separate and the female is the larger." to "அஸ்காரிஸை வகையாகக் குறிப்பிட்டு, பாலினங்கள் தனித்தனி, பெண் பெரியது எனக் கூறுக.",
                    ),
                    bp("A word of caution is needed about the phrase round worm. It means round in cross-section, in contrast to the flattened form of the flatworms, and not circular in outline, and the nematodes should not be confused with the annelids, which are also long and cylindrical but are segmented and have a true coelom. The differences between the three groups are among the most frequently asked comparisons in the course." to
                        "உருளைப்புழு என்ற சொற்றொடர் குறித்து ஓர் எச்சரிக்கை தேவை. இது தட்டைப்புழுக்களின் தட்டையான வடிவத்துக்கு மாறாக, குறுக்கு வெட்டில் வட்டம் என்பதைக் குறிக்கிறது; வெளிவடிவ வட்டம் அல்ல. நெமட்டோடாக்களை, நீண்ட உருளையாக இருந்தாலும் கண்டமுள்ள, உண்மை சீலோம் கொண்ட வளையப்புழுக்களுடன் குழப்பக் கூடாது. மூன்று குழுக்களுக்கிடையேயான வேறுபாடுகள் பாடத்தில் மிக அடிக்கடி கேட்கப்படும் ஒப்பீடுகளில் அடங்கும்."),
                )),
        ),
        summary = listOf(
            "Aschelminthes is a historical grouping of pseudocoelomate worm-like animals; Nematoda is now a separate phylum." to "அஸ்கெல்மிந்தீஸ் போலி உடற்குழி புழு போன்ற விலங்குகளின் வரலாற்றுக் குழுமம்; நெமட்டோடா இப்போது தனித் தொகுதி.",
            "Nematodes are bilateral, triploblastic, unsegmented, cylindrical and tapering at both ends." to "நெமட்டோடாக்கள் இருபக்கச் சமச்சீர், மூவடுக்கு, கண்டமற்ற, உருளை வடிவம், இரு முனைகளிலும் குறுகுபவை.",
            "A tough cuticle covers the body and is moulted during growth." to "கடினமான கியூட்டிகிள் உடலை மூடி, வளர்ச்சியின்போது உதிர்க்கப்படுகிறது.",
            "The body cavity is a fluid-filled pseudocoelom; the gut is complete, with mouth and anus." to "உடற்குழி திரவம் நிறைந்த போலி உடற்குழி; குடல் முழுமையானது, வாயும் மலவாயும் உண்டு.",
            "Only longitudinal muscles are present, so movement is a whip-like thrash and not peristalsis." to "நீளவாக்கு தசைகள் மட்டுமே இருப்பதால், இயக்கம் சாட்டை போன்ற அலைவு; பெரிஸ்டால்சிஸ் அல்ல.",
            "The cuticle, muscles and pseudocoelomic fluid form a hydrostatic skeleton." to "கியூட்டிகிள், தசைகள், போலி உடற்குழி திரவம் நீரியல் எலும்புக்கூட்டை உருவாக்குகின்றன.",
            "Sexes are usually separate, and the female is often larger." to "பாலினங்கள் பொதுவாகத் தனித்தனி; பெண் பெரும்பாலும் பெரியது.",
        ),
        checks = listOf(
            bqc("What does the name Aschelminthes mean today?", "அஸ்கெல்மிந்தீஸ் என்ற பெயர் இன்று எதைக் குறிக்கிறது?",
                "A historical grouping of pseudocoelomate worm-like animals; modern zoology treats Nematoda as a separate phylum.", "போலி உடற்குழி புழு போன்ற விலங்குகளின் வரலாற்றுக் குழுமம்; நவீன விலங்கியல் நெமட்டோடாவைத் தனித் தொகுதியாகக் கருதுகிறது."),
            bqc("Why do nematodes lack peristalsis?", "நெமட்டோடாக்களுக்கு பெரிஸ்டால்சிஸ் ஏன் இல்லை?",
                "They have only longitudinal muscles and no circular muscles.", "அவற்றுக்கு நீளவாக்கு தசைகள் மட்டுமே உள்ளன; வட்ட தசைகள் இல்லை."),
            bqc("What forms the hydrostatic skeleton of a nematode?", "நெமட்டோடாவின் நீரியல் எலும்புக்கூட்டை எது உருவாக்குகிறது?",
                "The fluid of the pseudocoelom, the elastic cuticle and the body-wall muscles.", "போலி உடற்குழி திரவம், மீள்தன்மை கியூட்டிகிள், உடற்சுவர் தசைகள்."),
            bqc("How does a pseudocoelom differ from a true coelom?", "போலி உடற்குழி உண்மை சீலோமிலிருந்து எவ்வாறு வேறுபடுகிறது?",
                "It lies between mesoderm and endoderm and is not fully lined by mesoderm, whereas a true coelom is within the mesoderm and lined by peritoneum.", "இது மீசோடெர்முக்கும் எண்டோடெர்முக்கும் இடையே இருந்து மீசோடெர்மால் முழுமையாக மூடப்படவில்லை; உண்மை சீலோம் மீசோடெர்முக்குள் இருந்து பெரிட்டோனியத்தால் மூடப்பட்டிருக்கும்."),
        ),
    )
}
