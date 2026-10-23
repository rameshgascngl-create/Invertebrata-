package com.gasczoology.invertebratelab.data

/** Book-style chapter: Phylum Arthropoda, general characters and classification. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object ArthropodaBookChapter {
    val chapter = bchapter(
        lessonId = "u4-arthropoda",
        title = "Phylum Arthropoda: general characters and classification" to "தொகுதி ஆர்த்ரோபோடா: பொதுப் பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State the general characters of Phylum Arthropoda." to "தொகுதி ஆர்த்ரோபோடாவின் பொதுப் பண்புகளைக் கூற.",
            "Describe the exoskeleton and the process of ecdysis." to "வெளிக்கூட்டையும் எக்டைசிஸ் நிகழ்வையும் விவரிக்க.",
            "Explain the tagmata and the structure of jointed appendages." to "டேக்மாக்களையும் மூட்டுள்ள உறுப்புகளின் அமைப்பையும் விளக்க.",
            "Describe the open circulation and the body cavity of arthropods." to "ஆர்த்ரோபோடாக்களின் திறந்த இரத்த ஓட்டத்தையும் உடற்குழியையும் விவரிக்க.",
            "Compare an arthropod appendage with an annelid parapodium." to "ஆர்த்ரோபோட் உறுப்பை அனெலிடா பாரபோடியத்துடன் ஒப்பிட.",
            "Name the four teaching classes of the phylum with their characters." to "தொகுதியின் நான்கு கற்பித்தல் வகுப்புகளை அவற்றின் பண்புகளுடன் பெயரிட.",
        ),
        introduction = listOf(
            "The arthropods are the most successful phylum of animals, if success is measured by the number of known species or by the range of habitats in which they live. Insects, crustaceans, spiders, scorpions, mites, centipedes and millipedes all belong to it, and they occupy the sea, fresh water, the soil, and the air. About four-fifths of the named animal species are arthropods, though the proportion changes as new species are described." to
                "அறியப்பட்ட இனங்களின் எண்ணிக்கையாலோ வாழிடங்களின் வீச்சாலோ வெற்றியை அளந்தால் ஆர்த்ரோபோடாக்கள் விலங்குகளின் மிக வெற்றிகரமான தொகுதி. பூச்சிகள், ஓட்டுடலிகள், சிலந்திகள், தேள்கள், உண்ணிகள், பூரான்கள், ஆயிரங்கால் புழுக்கள் அனைத்தும் இதில் அடங்கும்; கடல், நன்னீர், மண், காற்று ஆகியவற்றை அவை ஆக்கிரமித்துள்ளன. பெயரிடப்பட்ட விலங்கு இனங்களில் ஏறத்தாழ ஐந்தில் நான்கு பங்கு ஆர்த்ரோபோடாக்கள்; புதிய இனங்கள் விவரிக்கப்படுவதால் விகிதம் மாறுகிறது.",
            "The chapter lists the general characters of the phylum and then explains the main ones in turn: the exoskeleton and its moults, the division of the body into tagmata, the jointed appendages, the haemocoel and open circulation, and the organs of respiration. It then compares the arthropod limb with the annelid parapodium, a comparison that is often asked, and ends with the classification used in teaching." to
                "அத்தியாயம் தொகுதியின் பொதுப் பண்புகளைப் பட்டியலிட்டு, முக்கியமானவற்றை முறையே விளக்குகிறது: புறச்சட்டகமும் அதன் தோலுரித்தல்களும், உடலை டேக்மாக்களாகப் பிரித்தல், மூட்டுள்ள உறுப்புகள், ஹீமோசீலும் திறந்த இரத்த ஓட்டமும், சுவாச உறுப்புகள். பின்னர் ஆர்த்ரோபோட் உறுப்பை அனெலிடா பாரபோடியத்துடன் ஒப்பிடுகிறது; இது அடிக்கடி கேட்கப்படும் ஒப்பீடு; இறுதியில் கற்பித்தலில் பயன்படும் வகைப்பாட்டுடன் முடிகிறது.",
        ),
        sections = listOf(
            bsec("general-characters", "General characters", "பொதுப் பண்புகள்",
                blocks = listOf(
                    bp("Arthropods are bilaterally symmetrical, triploblastic protostomes. The body is segmented, as in the annelids, but the segments are usually grouped into regions, called tagmata, each specialised for particular functions. The name of the phylum means jointed foot, and it refers to the paired appendages that are jointed and that are the most characteristic feature of the group." to
                        "ஆர்த்ரோபோடாக்கள் இருபக்கச் சமச்சீர், மூவடுக்கு புரோட்டோஸ்டோம்கள். அனெலிடாக்களைப் போல உடல் கண்டமுடையது; ஆனால் கண்டங்கள் பொதுவாக ஒவ்வொன்றும் குறிப்பிட்ட செயல்களுக்குச் சிறப்புறும் டேக்மாக்கள் எனப்படும் பகுதிகளாகத் தொகுக்கப்படுகின்றன. தொகுதியின் பெயர் மூட்டுள்ள பாதம் எனப் பொருள்படும்; குழுவின் மிகத் தனிப்பண்பான மூட்டுள்ள இணை உறுப்புகளைக் குறிக்கிறது."),
                    bpoints("Characters to be listed in an answer", "விடையில் பட்டியலிட வேண்டிய பண்புகள்",
                        "Bilaterally symmetrical, triploblastic protostomes with a segmented body, usually organised into tagmata." to "இருபக்கச் சமச்சீர், மூவடுக்கு புரோட்டோஸ்டோம்கள்; உடல் கண்டமுடையது; பொதுவாக டேக்மாக்களாக அமைந்தது.",
                        "The body is covered by a chitinous exoskeleton, which protects, gives attachment to muscles, and is shed periodically in ecdysis." to "உடல் கைட்டின் வெளிக்கூட்டால் மூடப்பட்டுள்ளது; அது பாதுகாக்கிறது, தசைகளுக்கு இணைப்பு தருகிறது, எக்டைசிஸில் அவ்வப்போது உதிர்க்கப்படுகிறது.",
                        "Paired, jointed appendages are arranged segmentally and are specialised for walking, feeding, sensation and reproduction." to "இணை, மூட்டுள்ள உறுப்புகள் கண்ட வாரியாக அமைந்து நடத்தல், உண்ணுதல், உணர்தல், இனப்பெருக்கத்துக்குச் சிறப்புறுகின்றன.",
                        "The true coelom is reduced; the main body cavity is a haemocoel, and the circulation is generally open, with a dorsal heart." to "உண்மை உடற்குழி குறைக்கப்பட்டுள்ளது; முதன்மை உடற்குழி ஹீமோசீல்; இரத்த ஓட்டம் பொதுவாகத் திறந்தது; முதுகுப்புற இதயம் உண்டு.",
                        "The nervous system is ventral and ganglionated, with a dorsal brain, and the gut is complete." to "நரம்பு மண்டலம் வயிற்றுப்புறம், நரம்புத் திரளுடையது; முதுகுப்புற மூளை உண்டு; குடல் முழுமையானது.",
                        "Respiration is by gills, tracheae, book lungs or other specialised surfaces, depending on the group." to "சுவாசம் குழுவைப் பொறுத்து செவுள்கள், மூச்சுக்குழாய்கள், புத்தக நுரையீரல்கள் அல்லது பிற சிறப்புப் பரப்புகளால்.",
                        "Striated muscle is usual, and the sexes are generally separate." to "வரியுடைய தசை வழக்கம்; பால் பொதுவாகத் தனி.",
                    ),
                    bp("Some further characters complete the picture. The nervous system has a dorsal brain, which is a fusion of the ganglia of the first segments, connectives round the gut and a ventral cord of paired ganglia, which in many groups are fused. The sense organs are well developed, and include compound eyes made of many units, simple eyes, antennae that detect touch and chemicals, and in many, organs of balance and hearing. Muscle is striated and attached to the inside of the exoskeleton. The sexes are generally separate, fertilisation is often internal, and development may be direct or may pass through larval stages, of which the crustacean larvae in a later chapter are examples." to
                        "மேலும் சில பண்புகள் படத்தை நிறைவு செய்கின்றன. நரம்பு மண்டலத்தில் முதல் கண்டங்களின் நரம்பு செல்திரள்களின் இணைவான முதுகுப்புற மூளை, குடலைச் சுற்றும் இணைப்பிகள், பல குழுக்களில் இணைந்த இணை நரம்பு செல்திரள்களுடைய வயிற்றுப்புற வடம் உள்ளன. உணர்வு உறுப்புகள் நன்கு வளர்ந்தவை; பல அலகுகளாலான கூட்டுக் கண்கள், எளிய கண்கள், தொடு, வேதிப்பொருள்களை உணரும் உணர்கொம்புகள், பலவற்றில் சமநிலை, கேட்கும் உறுப்புகள் அடங்கும். தசை வரியுடையது; வெளிக்கூட்டின் உட்புறத்தில் இணைகிறது. பால் பொதுவாகத் தனி; கருவுறுதல் பெரும்பாலும் உள்ளுக்குள்; வளர்ச்சி நேரடியாகவோ லார்வா நிலைகள் வழியாகவோ இருக்கலாம்; அவற்றுக்கு பிந்தைய அத்தியாயத்தின் ஓட்டுடலி லார்வாக்கள் எடுத்துக்காட்டுகள்."),
                    bdef("Tagma", "டேக்மா", "A region of the body formed by the grouping of segments that share a function, such as the head, thorax and abdomen." to
                        "தலை, மார்பு, வயிறு போல, ஒரு செயலைப் பகிரும் கண்டங்களின் தொகுப்பால் உருவான உடலின் பகுதி."),
                    bdef("Exoskeleton", "புறச்சட்டகம்", "A hard covering on the outside of the body, secreted by the epidermis, that protects the animal and supports its muscles." to
                        "எபிடெர்மிஸ் சுரக்கும், விலங்கைப் பாதுகாத்து அதன் தசைகளைத் தாங்கும் உடலின் வெளிப்புற கடின மூடி."),
                    bdef("Chitin", "கைட்டின்", "A tough polysaccharide that, with protein, forms much of the arthropod cuticle." to
                        "புரதத்துடன் சேர்ந்து ஆர்த்ரோபோட் புறத்தோலின் பெரும்பகுதியை உருவாக்கும் உறுதியான பாலிசாக்கரைடு."),
                )),
            bsec("exoskeleton", "The exoskeleton and ecdysis", "புறச்சட்டகமும் எக்டைசிஸும்",
                blocks = listOf(
                    bp("The cuticle of an arthropod is a layered structure secreted by the epidermis. Its outer layer is thin and waxy in many terrestrial forms, which reduces the loss of water, and below it the thick layers contain chitin and protein. In many crustaceans the cuticle is hardened further by deposits of calcium salts, and in insects it is hardened by the cross-linking of proteins, a process called sclerotisation. Where movement is needed, the cuticle remains thin and flexible, as in the joints between the segments and the podomeres of the limbs." to
                        "ஆர்த்ரோபோடாவின் புறத்தோல் எபிடெர்மிஸ் சுரக்கும் அடுக்கு அமைப்பு. அதன் வெளி அடுக்கு பல நில வடிவங்களில் மெல்லியது, மெழுகு போன்றது; நீர் இழப்பைக் குறைக்கிறது; அதன் கீழ் தடித்த அடுக்குகளில் கைட்டினும் புரதமும் உள்ளன. பல ஓட்டுடலிகளில் கால்சியம் உப்புப் படிவுகளால் புறத்தோல் மேலும் கடினமாகிறது; பூச்சிகளில் புரதங்களின் குறுக்கு இணைப்பால் கடினமாகிறது; இது ஸ்க்ளெரோடைசேஷன் எனப்படுகிறது. இயக்கம் தேவையான இடங்களில், கண்டங்களுக்கிடையேயும் கால்களின் போடோமியர்களுக்கிடையேயுமுள்ள மூட்டுகளில் போல, புறத்தோல் மெல்லியதாகவும் நெகிழ்வாகவும் உள்ளது."),
                    bp("The rigid cuticle cannot grow, and an arthropod therefore grows by ecdysis, the periodic shedding of the old cuticle. Before the moult a new cuticle is formed beneath the old, the old one is partly digested at its inner layers, and the animal pulls itself out of the old covering, the exuvia. The new cuticle is soft for some time and is expanded by the intake of water or air before it hardens. During this time the animal is vulnerable. The moults are controlled by hormones, and a young arthropod passes through several moults before maturity, so that growth is stepwise, and not continuous." to
                        "கடின புறத்தோல் வளர முடியாது; எனவே ஆர்த்ரோபோடா பழைய புறத்தோலை அவ்வப்போது உதிர்க்கும் எக்டைசிஸால் வளர்கிறது. உதிர்ப்பதற்கு முன் பழையதன் கீழ் புதிய புறத்தோல் உருவாகிறது; பழையதின் உள் அடுக்குகள் பகுதியாகச் செரிக்கப்படுகின்றன; விலங்கு பழைய மூடியான எக்சூவியத்திலிருந்து தன்னை வெளியே இழுத்துக்கொள்கிறது. புதிய புறத்தோல் சிறிது நேரம் மென்மையாக இருந்து கடினமாவதற்கு முன் நீர் அல்லது காற்றை உட்கொள்வதால் விரிவடைகிறது. இக்காலத்தில் விலங்கு பாதிப்புக்குள்ளாகும். தோலுரித்தல்கள் ஹார்மோன்களால் கட்டுப்படுத்தப்படுகின்றன; இளம் ஆர்த்ரோபோடா முதிர்ச்சிக்கு முன் பல தோலுரித்தல்களைக் கடக்கிறது; எனவே வளர்ச்சி தொடர்ச்சியாக அல்ல, படிப்படியாக நிகழ்கிறது."),
                    bp("The exoskeleton has three functions. It protects against enemies and against loss of water. It provides a rigid frame to which the muscles are attached, so that the limbs work as levers. And, in the aquatic forms, it supports the body in water and, in the terrestrial ones, against gravity. The cost is that the animal cannot grow without moulting, and that the size of terrestrial forms is limited by the weight of the cuticle and by the way in which they breathe." to
                        "வெளிக்கூட்டுக்கு மூன்று செயல்கள் உள்ளன. எதிரிகளிடமிருந்தும் நீர் இழப்பிலிருந்தும் பாதுகாக்கிறது. தசைகள் இணையும் கடின சட்டத்தைத் தருகிறது; எனவே கால்கள் நெம்புகோல்களாக இயங்குகின்றன. நீர்வாழ் வடிவங்களில் நீரிலும் நிலவாழ்வில் ஈர்ப்புக்கு எதிராகவும் உடலைத் தாங்குகிறது. விலை: உதிர்க்காமல் விலங்கு வளர முடியாது; நில வடிவங்களின் அளவு புறத்தோலின் எடையாலும் சுவாச முறையாலும் வரையறுக்கப்படுகிறது."),
                    bdef("Ecdysis", "எக்டைசிஸ்", "The moulting of the cuticle by which an arthropod grows." to
                        "ஆர்த்ரோபோடா வளர்வதற்கான தோலுரித்தல்."),
                    bdef("Exuvia", "எக்சூவியம்", "The cast-off cuticle left after a moult." to
                        "உதிர்த்தலுக்குப் பின் எஞ்சும் கழற்றப்பட்ட புறத்தோல்."),
                    bdef("Sclerotisation", "ஸ்க்ளெரோடைசேஷன்", "The hardening of the cuticle by the cross-linking of its proteins." to
                        "புறத்தோலின் புரதங்களின் குறுக்கு இணைப்பால் ஏற்படும் கடினமாதல்."),
                )),
            bsec("appendages", "Tagmata and jointed appendages", "டேக்மாக்களும் மூட்டுள்ள உறுப்புகளும்",
                blocks = listOf(
                    bp("The segments of an arthropod are grouped into tagmata, and the number and kind of the tagmata differ from one class to another. In insects there are three, the head, thorax and abdomen. In crustaceans the head and thorax are often united under a carapace as a cephalothorax, followed by the abdomen. In arachnids there are two, the prosoma and the opisthosoma. In the myriapods there are two, the head and the long trunk of many segments. The fusion of segments in a tagma is the second principal difference from the annelid, in which the trunk segments are much alike." to
                        "ஆர்த்ரோபோடாவின் கண்டங்கள் டேக்மாக்களாகத் தொகுக்கப்படுகின்றன; டேக்மாக்களின் எண்ணிக்கையும் வகையும் ஒரு வகுப்பிலிருந்து மற்றொன்றுக்கு மாறுகிறது. பூச்சிகளில் தலை, மார்பு, வயிறு என மூன்று. ஓட்டுடலிகளில் தலையும் மார்பும் பெரும்பாலும் ஓட்டின் கீழ் செபலோதொராக்ஸாக இணைந்து பின் வயிறு வருகிறது. அராக்னிடாக்களில் புரோசோமா, ஓப்பிஸ்தோசோமா என இரண்டு. மையரியாபோடாக்களில் தலை, பல கண்டங்களுடைய நீண்ட உடற்பகுதி என இரண்டு. ஒரு டேக்மாவில் கண்டங்கள் இணைவது அனெலிடாவிலிருந்து இரண்டாவது முதன்மை வேறுபாடு; அனெலிடாவில் உடற்பகுதிக் கண்டங்கள் பெரிதும் ஒத்தவை."),
                    bp("An arthropod appendage is a cuticle-covered, segmented structure, with true joints between its parts, the podomeres. The muscles lie inside the limb and cross the joint, so that when a muscle contracts it moves one podomere against the next, and the limb acts as a system of mechanical levers against the exoskeleton. The appendages may be uniramous, with a single branch, or biramous, with two, as in the limbs of many crustaceans. Appendages of different segments are specialised as antennae, mouthparts, walking legs, swimming limbs and the organs for reproduction." to
                        "ஆர்த்ரோபோட் உறுப்பு புறத்தோல் மூடிய, கண்டமுடைய அமைப்பு; அதன் பகுதிகளான போடோமியர்களுக்கிடையே உண்மையான மூட்டுகள் உள்ளன. தசைகள் காலுக்குள் மூட்டைக் கடந்து அமைவதால் தசை சுருங்கும்போது ஒரு போடோமியரை அடுத்ததற்கு எதிராக நகர்த்துகிறது; கால் வெளிக்கூட்டுக்கு எதிராக இயந்திர நெம்புகோல் அமைப்பாகச் செயல்படுகிறது. உறுப்புகள் பல ஓட்டுடலிகளின் கால்களில் போல ஒரு கிளை உடைய யூனிராமஸ் அல்லது இரு கிளை உடைய பைராமஸ் ஆக இருக்கலாம். வெவ்வேறு கண்டங்களின் உறுப்புகள் உணர்கொம்புகள், வாய்ப்பகுதிகள், நடக்கும் கால்கள், நீந்தும் கால்கள், இனப்பெருக்க உறுப்புகள் எனச் சிறப்புறுகின்றன."),
                    bdef("Podomere", "போடோமியர்", "One of the jointed segments of an arthropod limb." to
                        "ஆர்த்ரோபோடா காலின் மூட்டுள்ள கண்டங்களில் ஒன்று."),
                    bdef("Uniramous", "யூனிராமஸ்", "Having a limb with a single branch." to
                        "ஒற்றைக் கிளையுள்ள காலை உடைய."),
                    bdef("Biramous", "பைராமஸ்", "Having a limb with two branches." to
                        "இரு கிளையுள்ள காலை உடைய."),
                    bdef("Cephalothorax", "செபலோதொராக்ஸ்", "A tagma formed by the fusion of the head and thorax, as in a prawn." to
                        "இறாலில் போல, தலையும் மார்பும் இணைவதால் உருவான டேக்மா."),
                )),
            bsec("comparison", "Arthropod appendage and annelid parapodium compared", "ஆர்த்ரோபோட் உறுப்பும் அனெலிடா பாரபோடியமும் ஒப்பீடு",
                blocks = listOf(
                    btable("Arthropod appendage compared with annelid parapodium", "ஆர்த்ரோபோட் உறுப்பும் அனெலிடா பாரபோடியமும்",
                        listOf("Feature" to "பண்பு", "Arthropod appendage" to "ஆர்த்ரோபோட் உறுப்பு", "Annelid parapodium" to "அனெலிடா பாரபோடியம்"),
                        listOf(
                            listOf("Covering" to "மூடி", "Hard cuticle (exoskeleton)" to "கடின புறத்தோல் (புறச்சட்டகம்)", "Thin, soft cuticle" to "மெல்லிய, மென்மையான புறத்தோல்"),
                            listOf("Joints" to "மூட்டுகள்", "True joints between podomeres" to "போடோமியர்களுக்கிடையே உண்மை மூட்டுகள்", "None; a soft fleshy outgrowth" to "இல்லை; மென்மையான சதை வளர்ச்சி"),
                            listOf("Action" to "செயல்", "Muscles act as levers across joints" to "தசைகள் மூட்டுகளைக் கடந்து நெம்புகோலாகச் செயல்படுகின்றன", "Body-wall muscles and fluid pressure" to "உடற்சுவர்த் தசைகளும் திரவ அழுத்தமும்"),
                            listOf("Support" to "தாங்குதல்", "Skeleton of the exoskeleton" to "வெளிக்கூட்டின் எலும்புக்கூடு", "Acicula and chaetae" to "அசிகுலங்கள், சீட்டாக்கள்"),
                            listOf("Branches" to "கிளைகள்", "Uniramous or biramous" to "யூனிராமஸ் அல்லது பைராமஸ்", "Typically notopodium and neuropodium" to "வழக்கமாக நோட்டோபோடியம், நியூரோபோடியம்"),
                            listOf("Specialisation" to "சிறப்பு", "Antennae, mouthparts, legs, swimmerets, genital limbs" to "உணர்கொம்புகள், வாய்ப்பகுதிகள், கால்கள், நீந்து கால்கள், இனப்பெருக்கக் கால்கள்", "Crawling, swimming, gas exchange" to "ஊர்தல், நீந்துதல், வாயுப் பரிமாற்றம்"),
                        )),
                    bp("The two structures can both help in movement or in respiration, and a student may be tempted to treat the parapodium as the early form of the arthropod limb. This is not justified by the evidence presented here, for they differ in construction, and the resemblance of function is not a basis for merging the two body plans. The reader should say that they are different structures that have partly similar jobs." to
                        "இரு அமைப்புகளும் இயக்கத்துக்கும் சுவாசத்துக்கும் உதவலாம்; பாரபோடியத்தை ஆர்த்ரோபோட் காலின் ஆரம்ப வடிவமாகக் கருதத் தூண்டலாம். இங்கு முன்வைக்கப்பட்ட சான்றுகள் இதை நியாயப்படுத்தவில்லை; அவை அமைப்பில் வேறுபடுகின்றன; செயல் ஒற்றுமை இரு உடல் அமைப்புகளை இணைப்பதற்கான அடிப்படை அல்ல. அவை பகுதியளவு ஒத்த பணிகளுடைய வெவ்வேறு அமைப்புகள் என வாசகர் கூற வேண்டும்."),
                    bnote("Lever versus bag", "நெம்புகோல் எதிர் பை",
                        "An arthropod limb works as a lever on a rigid skeleton. An annelid moves by muscles working against a fluid-filled bag. This difference is what the examiner wants to see." to
                            "ஆர்த்ரோபோட் கால் கடின எலும்புக்கூட்டில் நெம்புகோலாகச் செயல்படுகிறது. அனெலிடா திரவம் நிரம்பிய பைக்கு எதிராகச் செயல்படும் தசைகளால் நகர்கிறது. இந்த வேறுபாட்டையே தேர்வாளர் காண விரும்புகிறார்."),
                )),
            bsec("circulation", "Body cavity, circulation and respiration", "உடற்குழி, இரத்த ஓட்டம், சுவாசம்",
                blocks = listOf(
                    bp("In the arthropod the true coelom is reduced to the cavities of the gonads and the excretory organs, and the main body cavity is a haemocoel, a space filled with blood in which the organs lie. The circulation is open. A dorsal tubular heart, with openings called ostia in its wall, pumps blood forward through arteries, and the blood leaves the arteries into the haemocoel and bathes the tissues, and returns to the pericardial sinus to enter the heart again through the ostia. The open system is of lower pressure than the closed system of the annelid." to
                        "ஆர்த்ரோபோடாவில் உண்மை உடற்குழி இனப்பெருக்க சுரப்பிகள், கழிவு உறுப்புகளின் குழிகளாகக் குறைக்கப்பட்டுள்ளது; முதன்மை உடற்குழி உறுப்புகள் அமைந்துள்ள இரத்தம் நிரம்பிய இடமான ஹீமோசீல். இரத்த ஓட்டம் திறந்தது. சுவரில் ஆஸ்டியா எனும் துளைகளுடன் முதுகுப்புறக் குழல் இதயம் இரத்தத்தை தமனிகள் வழியே முன்னோக்கி இறைக்கிறது; இரத்தம் தமனிகளிலிருந்து ஹீமோசீலுக்குள் வெளியேறித் திசுக்களை நனைத்து, பெரிகார்டியல் சைனசுக்குத் திரும்பி ஆஸ்டியா வழியாக மீண்டும் இதயத்துக்குள் நுழைகிறது. திறந்த அமைப்பு அனெலிடாவின் மூடிய அமைப்பை விடக் குறைந்த அழுத்தமுடையது."),
                    bp("The blood of an arthropod is therefore called haemolymph, as it has the double role of blood and tissue fluid. In insects it takes little part in the carriage of oxygen, since the gases are carried by tracheae, and in crustaceans it generally contains a pigment, haemocyanin. The respiratory organs vary with the habitat. Aquatic crustaceans breathe with gills, insects and many myriapods with tracheae, which are branching air tubes that open at spiracles, and spiders and scorpions with book lungs or tracheae or both. The existence of such different organs in the same phylum is one reason for the success of the group in so many environments." to
                        "எனவே ஆர்த்ரோபோடாவின் இரத்தம் இரத்தம், திசு திரவம் என்ற இரட்டைப் பங்கு உடையதால் ஹீமோலிம்ப் எனப்படுகிறது. பூச்சிகளில் வாயுக்கள் மூச்சுக்குழாய்களால் கொண்டு செல்லப்படுவதால் ஆக்சிஜன் கடத்தலில் அது சிறிதே பங்கேற்கிறது; ஓட்டுடலிகளில் பொதுவாக ஹீமோசயனின் நிறமி உண்டு. சுவாச உறுப்புகள் வாழிடத்துக்கு ஏற்ப மாறுகின்றன. நீர்வாழ் ஓட்டுடலிகள் செவுள்களால் சுவாசிக்கின்றன; பூச்சிகளும் பல மையரியாபோடாக்களும் ஸ்பைராக்கிள்களில் திறக்கும் கிளைக்கும் காற்றுக் குழல்களான மூச்சுக்குழாய்களால்; சிலந்திகளும் தேள்களும் புத்தக நுரையீரல்கள் அல்லது மூச்சுக்குழாய்கள் அல்லது இரண்டாலும். ஒரே தொகுதியில் இத்தனை வேறுபட்ட உறுப்புகள் இருப்பது இத்தனை சூழல்களில் குழுவின் வெற்றிக்கு ஒரு காரணம்."),
                    bdef("Haemocoel", "ஹீமோசீல்", "The main body cavity of an arthropod, filled with blood in which the organs are bathed." to
                        "உறுப்புகள் நனையும் இரத்தம் நிரம்பிய, ஆர்த்ரோபோடாவின் முதன்மை உடற்குழி."),
                    bdef("Open circulatory system", "திறந்த இரத்த ஓட்ட மண்டலம்", "A system in which the blood leaves the vessels and flows through spaces among the tissues before returning to the heart." to
                        "இரத்தம் நாளங்களை விட்டு வெளியேறி திசுக்களுக்கிடையேயுள்ள இடங்களில் பாய்ந்து இதயத்துக்குத் திரும்பும் மண்டலம்."),
                    bdef("Ostium", "ஆஸ்டியம்", "A valved opening in the wall of the arthropod heart through which blood enters." to
                        "இரத்தம் நுழையும், ஆர்த்ரோபோடா இதயச் சுவரிலுள்ள வால்வுடைய துளை."),
                    bdef("Trachea", "மூச்சுக்குழாய்", "A branching air tube that carries oxygen directly to the tissues of an insect or other arthropod." to
                        "பூச்சி அல்லது பிற ஆர்த்ரோபோடாவின் திசுக்களுக்கு நேரடியாக ஆக்சிஜனைக் கொண்டு செல்லும் கிளைக்கும் காற்றுக் குழல்."),
                    bdef("Haemolymph", "ஹீமோலிம்ப்", "The blood of an arthropod, which serves also as the tissue fluid." to
                        "திசு திரவமாகவும் செயல்படும் ஆர்த்ரோபோடாவின் இரத்தம்."),
                )),
            bsec("classification", "Classification used in teaching", "கற்பித்தலில் பயன்படும் வகைப்பாடு",
                blocks = listOf(
                    bp("For teaching, the phylum is divided into four classes, Crustacea, Insecta, Arachnida and Myriapoda. Molecular and morphological studies have placed insects and crustaceans together in a group, the Pancrustacea, and have separated the chelicerates, to which the arachnids belong, as a different line. The four classes are retained in a first course because they describe the characters that students are asked to recognise, and the student should know that the grouping is a convenience." to
                        "கற்பித்தலுக்கு தொகுதி கிரஸ்டேசியா, இன்செக்டா, அராக்னிடா, மையரியாபோடா என நான்கு வகுப்புகளாகப் பிரிக்கப்படுகிறது. மூலக்கூறு, உருவ ஆய்வுகள் பூச்சிகளையும் ஓட்டுடலிகளையும் ஒரு குழுவாக, பான்கிரஸ்டேசியாவாக, இணைத்துள்ளன; அராக்னிடாக்கள் சேரும் கீலிசெரேட்டுகளை வேறு கிளையாகப் பிரித்துள்ளன. மாணவர்கள் அடையாளம் காண கேட்கப்படும் பண்புகளை விவரிப்பதால் நான்கு வகுப்புகளும் முதல் பாடத்தில் தக்கவைக்கப்படுகின்றன; இத்தொகுப்பு வசதிக்கானது என மாணவர் அறிய வேண்டும்."),
                    btable("Four teaching classes of Arthropoda", "ஆர்த்ரோபோடாவின் நான்கு கற்பித்தல் வகுப்புகள்",
                        listOf("Class" to "வகுப்பு", "Body regions" to "உடற்பகுதிகள்", "Antennae" to "உணர்கொம்புகள்", "Legs" to "கால்கள்", "Respiration" to "சுவாசம்", "Examples" to "எடுத்துக்காட்டுகள்"),
                        listOf(
                            listOf("Crustacea" to "கிரஸ்டேசியா", "Cephalothorax and abdomen (often)" to "செபலோதொராக்ஸ், வயிறு (பெரும்பாலும்)", "Two pairs" to "இரு இணை", "Many, often biramous" to "பல, பெரும்பாலும் பைராமஸ்", "Gills" to "செவுள்கள்", "Prawn, crab, Daphnia" to "இறால், நண்டு, டாப்னியா"),
                            listOf("Insecta" to "இன்செக்டா", "Head, thorax, abdomen" to "தலை, மார்பு, வயிறு", "One pair" to "ஒரு இணை", "Three pairs" to "மூன்று இணை", "Tracheae" to "மூச்சுக்குழாய்கள்", "Cockroach, butterfly" to "கரப்பான், பட்டாம்பூச்சி"),
                            listOf("Arachnida" to "அராக்னிடா", "Prosoma and opisthosoma" to "புரோசோமா, ஓப்பிஸ்தோசோமா", "None" to "இல்லை", "Four pairs" to "நான்கு இணை", "Book lungs or tracheae" to "புத்தக நுரையீரல்கள் அல்லது மூச்சுக்குழாய்கள்", "Spider, scorpion" to "சிலந்தி, தேள்"),
                            listOf("Myriapoda" to "மையரியாபோடா", "Head and long trunk" to "தலை, நீண்ட உடற்பகுதி", "One pair" to "ஒரு இணை", "Many pairs" to "பல இணை", "Tracheae" to "மூச்சுக்குழாய்கள்", "Centipede, millipede" to "பூரான், ஆயிரங்கால் புழு"),
                        )),
                    bp("The two groups of crustaceans that are of most concern in this unit are the prawn Penaeus, in the next chapter, and the larval forms of crustaceans, which come later. Peripatus, which is not in any of the four classes, is considered separately as a connecting form." to
                        "இந்த அலகில் மிகக் கவனிக்கப்படும் ஓட்டுடலிகளின் இரு குழுக்கள் அடுத்த அத்தியாயத்திலுள்ள இறால் பெனியஸும் பின்னர் வரும் ஓட்டுடலிகளின் லார்வா வடிவங்களும். நான்கு வகுப்புகளில் எதிலும் இல்லாத பெரிபேட்டஸ் இணைக்கும் வடிவமாகத் தனியாகக் கருதப்படுகிறது."),
                    bdef("Pancrustacea", "பான்கிரஸ்டேசியா", "A group that includes the crustaceans and the insects, supported by molecular and morphological evidence." to
                        "மூலக்கூறு, உருவச் சான்றுகளால் ஆதரிக்கப்படும், ஓட்டுடலிகளையும் பூச்சிகளையும் உள்ளடக்கிய குழு."),
                    bdef("Spiracle", "ஸ்பைராக்கிள்", "An external opening of the tracheal system of an insect or other terrestrial arthropod." to
                        "பூச்சி அல்லது பிற நில ஆர்த்ரோபோடாவின் மூச்சுக்குழாய் மண்டலத்தின் வெளித் துளை."),
                )),
        ),
        summary = listOf(
            "Arthropods are bilateral, triploblastic protostomes with a segmented body organised into tagmata and paired jointed appendages." to "ஆர்த்ரோபோடாக்கள் இருபக்கச் சமச்சீர், மூவடுக்கு புரோட்டோஸ்டோம்கள்; டேக்மாக்களாக அமைந்த கண்டமுடைய உடலும் இணை மூட்டுள்ள உறுப்புகளும் உடையவை.",
            "The chitinous exoskeleton protects, supports muscles and is shed in ecdysis, so growth is stepwise." to "கைட்டின் புறச்சட்டகம் பாதுகாக்கிறது, தசைகளைத் தாங்குகிறது, எக்டைசிஸில் உதிர்க்கப்படுகிறது; எனவே வளர்ச்சி படிப்படியானது.",
            "The coelom is reduced, the body cavity is a haemocoel, and circulation is open with a dorsal heart." to "உடற்குழி குறைக்கப்பட்டுள்ளது; உடற்குழி ஹீமோசீல்; முதுகுப்புற இதயத்துடன் இரத்த ஓட்டம் திறந்தது.",
            "The nervous system is ventral and ganglionated; respiration is by gills, tracheae or book lungs." to "நரம்பு மண்டலம் வயிற்றுப்புறம், நரம்புத் திரளுடையது; சுவாசம் செவுள்கள், மூச்சுக்குழாய்கள் அல்லது புத்தக நுரையீரல்களால்.",
            "An arthropod limb is jointed and works as a lever, whereas an annelid parapodium is a soft, unjointed outgrowth." to "ஆர்த்ரோபோட் கால் மூட்டுள்ளது, நெம்புகோலாகச் செயல்படுகிறது; அனெலிடா பாரபோடியம் மென்மையான, மூட்டற்ற வளர்ச்சி.",
            "The four teaching classes are Crustacea, Insecta, Arachnida and Myriapoda." to "நான்கு கற்பித்தல் வகுப்புகள் கிரஸ்டேசியா, இன்செக்டா, அராக்னிடா, மையரியாபோடா.",
        ),
        checks = listOf(
            bqc("What is ecdysis?", "எக்டைசிஸ் என்றால் என்ன?",
                "The moulting of the cuticle by which an arthropod grows.", "ஆர்த்ரோபோடா வளர்வதற்கான தோலுரித்தல்."),
            bqc("Name four teaching classes of Arthropoda.", "ஆர்த்ரோபோடாவின் நான்கு கற்பித்தல் வகுப்புகளைப் பெயரிடுக.",
                "Crustacea, Insecta, Arachnida and Myriapoda.", "கிரஸ்டேசியா, இன்செக்டா, அராக்னிடா, மையரியாபோடா."),
            bqc("What is the main body cavity of an arthropod?", "ஆர்த்ரோபோடாவின் முதன்மை உடற்குழி எது?",
                "The haemocoel, a blood-filled space in which the organs lie.", "உறுப்புகள் அமைந்துள்ள இரத்தம் நிரம்பிய இடமான ஹீமோசீல்."),
            bqc("How does an arthropod limb differ from an annelid parapodium?", "ஆர்த்ரோபோட் கால் அனெலிடா பாரபோடியத்திலிருந்து எவ்வாறு வேறுபடுகிறது?",
                "It is covered by a hard cuticle, has true joints and works as a lever; the parapodium is soft and unjointed.", "கடின புறத்தோலால் மூடப்பட்டு, உண்மை மூட்டுகளுடன் நெம்புகோலாகச் செயல்படுகிறது; பாரபோடியம் மென்மையானது, மூட்டற்றது."),
        ),
    )
}
