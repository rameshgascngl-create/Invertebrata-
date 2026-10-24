package com.gasczoology.invertebratelab.data

/** Book-style chapter: Ascaris lumbricoides. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object AscarisBookChapter {
    val chapter = bchapter(
        lessonId = "u3-ascaris",
        title = "Ascaris lumbricoides: the human roundworm" to "அஸ்காரிஸ் லம்பிரிக்காய்டஸ்: மனித உருளைப்புழு",
        objectives = listOf(
            "Describe the habit, habitat and external features of Ascaris, and distinguish the male from the female." to "அஸ்காரிஸின் வாழ்முறை, வாழிடம், புறத்தோற்றத்தை விவரித்து, ஆணைப் பெண்ணிலிருந்து வேறுபடுத்த.",
            "Describe the body wall, the alimentary canal, and the excretory and nervous systems." to "உடற்சுவர், உணவுக்குழல், கழிவு நீக்க, நரம்பு மண்டலங்களை விவரிக்க.",
            "Describe the male and female reproductive systems." to "ஆண், பெண் இனப்பெருக்க மண்டலங்களை விவரிக்க.",
            "Write the life cycle in the correct sequence, including the migration of the larva." to "லார்வாவின் நகர்வு உட்பட வாழ்க்கைச் சுழற்சியைச் சரியான வரிசையில் எழுத.",
            "Explain why the cycle needs no intermediate host and how this affects control." to "சுழற்சிக்கு இடைநிலை விருந்தோம்பி ஏன் தேவையில்லை, இது கட்டுப்பாட்டை எவ்வாறு பாதிக்கிறது என்பதை விளக்க.",
        ),
        introduction = listOf(
            "Ascaris lumbricoides is the large roundworm of the human small intestine. It is one of the most common parasites of people, and the number of persons that it infects in the world is counted in hundreds of millions, mostly children in warm countries where sanitation is poor. The adult worm is large and easily handled, and for this reason it has long been the type study for the phylum Nematoda." to
                "அஸ்காரிஸ் லம்பிரிக்காய்டஸ் மனித சிறுகுடலின் பெரிய உருளைப்புழு. மனிதர்களின் மிகப் பொதுவான ஒட்டுண்ணிகளில் ஒன்று; உலகில் அது தொற்றும் மக்களின் எண்ணிக்கை நூற்றுக்கணக்கான மில்லியன்கள்; பெரும்பாலும் சுகாதாரம் மோசமான வெப்ப நாடுகளின் குழந்தைகள். முதிர்ந்த புழு பெரியது, எளிதில் கையாளக்கூடியது; இதனால் நெமட்டோடா தொகுதியின் வகை ஆய்வாக நீண்ட காலம் இருந்து வருகிறது.",
            "The chapter describes the structure of the worm under the headings of habit and habitat, external features, body wall, and the systems of the body, and it ends with the life cycle and its consequences for the control of the infection. Three figures are placed where the text calls for them, each shown once." to
                "அத்தியாயம் புழுவின் அமைப்பை வாழ்முறையும் வாழிடமும், புறத்தோற்றம், உடற்சுவர், உடலின் மண்டலங்கள் என்ற தலைப்புகளில் விவரித்து, வாழ்க்கைச் சுழற்சியுடனும் தொற்றுக் கட்டுப்பாட்டுக்கான அதன் விளைவுகளுடனும் முடிகிறது. உரை கோரும் இடங்களில் மூன்று படங்கள் வைக்கப்பட்டுள்ளன; ஒவ்வொன்றும் ஒருமுறை காட்டப்படுகிறது.",
        ),
        sections = listOf(
            bsec("habitat", "Habit and habitat", "வாழ்முறையும் வாழிடமும்",
                blocks = listOf(
                    bp("Adult Ascaris live in the lumen of the small intestine of human beings, where they lie free, held in place by the muscular movements of their own bodies against the intestinal wall and the pressure of the contents. They do not attach by hooks or suckers. They feed on the semi-digested food of the host, and in a heavy infection they may compete with the host for nourishment, and in children may impair growth. A closely related species infects pigs." to
                        "முதிர்ந்த அஸ்காரிஸ் மனிதர்களின் சிறுகுடல் குழியில், தனித்து கிடந்து, தங்கள் உடலின் தசை இயக்கங்களால் குடல் சுவருக்கு எதிராகவும் உள்ளடக்கத்தின் அழுத்தத்தாலும் இடத்தில் நிலைத்து வாழ்கின்றன. கொக்கிகள் அல்லது உறிஞ்சிகளால் ஒட்டுவதில்லை. விருந்தோம்பியின் அரைச்செரித்த உணவை உண்கின்றன; கடுமையான தொற்றில் ஊட்டத்துக்கு விருந்தோம்பியுடன் போட்டியிட்டு, குழந்தைகளில் வளர்ச்சியைக் குறைக்கக்கூடும். நெருங்கிய தொடர்புடைய இனம் பன்றிகளைத் தொற்றுகிறது."),
                    bdef("Monoxenous", "ஒரே விருந்தோம்பி (Monoxenous)", "Completing the life cycle in a single host, without an intermediate host." to
                        "இடைநிலை விருந்தோம்பி இல்லாமல் ஒற்றை விருந்தோம்பியில் வாழ்க்கைச் சுழற்சியை முடிக்கும்."),
                )),
            bsec("external", "External features", "புறத்தோற்றம்",
                blocks = listOf(
                    bp("The worm is long and cylindrical, tapering at both ends, and is creamy-white or pinkish in colour. The sexes are separate, and the female is larger, usually twenty to thirty-five centimetres long, whereas the male is about fifteen to thirty centimetres. The body is covered by a cuticle that is marked by fine transverse striations, and four longitudinal lines can be seen along the body, which are the dorsal, ventral and two lateral lines. The lateral lines are the most conspicuous and are white." to
                        "புழு நீண்ட, உருளை, இரு முனைகளிலும் குறுகியது; கிரீம் வெள்ளை அல்லது இளஞ்சிவப்பு நிறம். பாலினங்கள் தனித்தனி; பெண் பெரியது, பொதுவாக இருபது முதல் முப்பத்தைந்து சென்டிமீட்டர் நீளம்; ஆண் சுமார் பதினைந்து முதல் முப்பது சென்டிமீட்டர். உடல் நுண் குறுக்குக் கீறல்கள் கொண்ட கியூட்டிகிளால் மூடப்பட்டுள்ளது; உடலின் நீளம் முழுவதும் முதுகு, வயிறு, இரு பக்கக் கோடுகளான நான்கு நீளவாக்குக் கோடுகள் தெரியும். பக்கக் கோடுகள் மிகத் தெளிவானவை, வெள்ளை நிறம்."),
                    bp("The mouth is at the anterior end and is surrounded by three lips, one dorsal and two ventro-lateral, each bearing small teeth-like denticles and sensory papillae. The anus is a transverse slit near the posterior end, on the ventral side. The female opening, the vulva, lies on the ventral side about one-third of the way from the front, and the excretory pore is a minute opening on the ventral side near the anterior end. In the male the posterior end is curved ventrally and bears two small bristles, the copulatory spicules, which protrude through the cloacal opening." to
                        "வாய் முன் முனையில், ஒரு முதுகுப்புறம், இரு வயிற்று-பக்க உதடுகள் என மூன்று உதடுகளால் சூழப்பட்டுள்ளது; ஒவ்வொன்றிலும் சிறு பல் போன்ற முள்ளுகளும் உணர் பாப்பில்லாக்களும் உள்ளன. மலவாய் பின் முனைக்கு அருகே வயிற்றுப் புறத்தில் குறுக்கு பிளவு. பெண் துளையான வல்வா முன்பகுதியிலிருந்து மூன்றில் ஒரு பங்கு தூரத்தில் வயிற்றுப் புறத்தில் உள்ளது; கழிவு நீக்கத் துளை முன் முனைக்கு அருகே வயிற்றுப் புறத்தில் நுண் திறப்பு. ஆணில் பின் முனை வயிற்றுப் புறமாக வளைந்து, குளோயாக்கல் திறப்பு வழியாக நீட்டும் புணர்ச்சி ஸ்பிக்யூல்கள் எனும் இரு சிறு முட்களைத் தாங்குகிறது."),
                    btable("Differences between male and female Ascaris", "ஆண், பெண் அஸ்காரிஸ் வேறுபாடுகள்",
                        listOf("Feature" to "பண்பு", "Male" to "ஆண்", "Female" to "பெண்"),
                        listOf(
                            listOf("Length" to "நீளம்", "15 to 30 cm" to "15 முதல் 30 செ.மீ.", "20 to 35 cm" to "20 முதல் 35 செ.மீ."),
                            listOf("Posterior end" to "பின் முனை", "Curved ventrally, with two spicules" to "வயிற்றுப்புறமாக வளைந்தது, இரு ஸ்பிக்யூல்களுடன்", "Straight and pointed" to "நேரானது, கூர்மையானது"),
                            listOf("Genital opening" to "இனப்பெருக்கத் திறப்பு", "Cloaca at the posterior end" to "பின் முனையில் குளோயாக்கா", "Vulva about one-third from the front" to "முன்பகுதியிலிருந்து மூன்றில் ஒரு பங்கில் வல்வா"),
                            listOf("Reproductive organs" to "இனப்பெருக்க உறுப்புகள்", "A single testis" to "ஒற்றை விந்தகம்", "Paired ovaries and uteri" to "இணை சூற்பைகளும் கருப்பைகளும்"),
                        )),
                    bfig("ascaris/male-female-external", "Male and female Ascaris lumbricoides, showing the three lips, the lateral lines, the vulva of the female and the curved tail and spicules of the male." to
                        "ஆண், பெண் அஸ்காரிஸ் லம்பிரிக்காய்டஸ்: மூன்று உதடுகள், பக்கக் கோடுகள், பெண்ணின் வல்வா, ஆணின் வளைந்த வால், ஸ்பிக்யூல்கள்."),
                    bdef("Lateral line", "பக்கக் கோடு (Lateral line)", "One of the thickened longitudinal ridges of the epidermis along the side of a nematode, which contain the excretory canals." to
                        "கழிவு நீக்கக் கால்வாய்களைக் கொண்ட, நெமட்டோடாவின் பக்கவாட்டில் நீளவாக்கில் உள்ள எப்பிடெர்மிஸின் தடித்த முகடு."),
                    bdef("Vulva", "வல்வா (Vulva)", "The external opening of the female reproductive system." to
                        "பெண் இனப்பெருக்க மண்டலத்தின் வெளித் திறப்பு."),
                    bdef("Cloaca", "குளோயாக்கா (Cloaca)", "A common chamber into which the gut and the reproductive ducts open, as in the male nematode." to
                        "ஆண் நெமட்டோடாவில் போல், குடலும் இனப்பெருக்கக் குழாய்களும் திறக்கும் பொது அறை."),
                )),
            bsec("wall", "The body wall and pseudocoelom", "உடற்சுவரும் போலி உடற்குழியும்",
                blocks = listOf(
                    bp("The body wall has three layers: the cuticle, the epidermis (also called the hypodermis) and a layer of muscle. The cuticle is a tough, non-living, multilayered covering of collagen secreted by the epidermis, and it resists the digestive enzymes and the pressure of the host's intestine. The epidermis is a syncytium, which means that it is not divided into separate cells, and it is thickened along the dorsal, ventral and lateral lines into four longitudinal cords. The lateral cords enclose the excretory canals, and the dorsal and ventral cords carry the nerves." to
                        "உடற்சுவரில் மூன்று அடுக்குகள் உள்ளன: கியூட்டிகிள், எப்பிடெர்மிஸ் (ஹைப்போடெர்மிஸ் எனவும்), தசை அடுக்கு. கியூட்டிகிள் எப்பிடெர்மிஸ் சுரக்கும் கொலாஜன் கடினமான, உயிரற்ற, பல அடுக்கு உறை; விருந்தோம்பியின் குடலின் செரிமான நொதிகளையும் அழுத்தத்தையும் எதிர்க்கிறது. எப்பிடெர்மிஸ் சின்சிட்டியம்; அதாவது தனிச் செல்களாகப் பிரிக்கப்படவில்லை; முதுகு, வயிறு, பக்கக் கோடுகளில் தடித்து நான்கு நீளவாக்கு வடங்களாகிறது. பக்க வடங்கள் கழிவு நீக்கக் கால்வாய்களை அடைக்கின்றன; முதுகு, வயிற்று வடங்கள் நரம்புகளைக் கொண்டுள்ளன."),
                    bp("Below the epidermis is a single layer of longitudinal muscle cells, divided by the four cords into four bands. There are no circular muscles. The cavity inside the muscle layer is the pseudocoelom, which is filled with fluid and contains the gut and the reproductive organs. The fluid is under pressure and works with the cuticle and muscles as a hydrostatic skeleton, as explained in the preceding chapter." to
                        "எப்பிடெர்மிஸுக்குக் கீழ் ஒற்றை அடுக்கு நீளவாக்கு தசை செல்கள் உள்ளன; நான்கு வடங்களால் நான்கு பட்டைகளாகப் பிரிக்கப்பட்டுள்ளன. வட்ட தசைகள் இல்லை. தசை அடுக்குக்குள் உள்ள குழி போலி உடற்குழி; திரவம் நிறைந்து குடலையும் இனப்பெருக்க உறுப்புகளையும் கொண்டுள்ளது. முந்தைய அத்தியாயத்தில் விளக்கியவாறு திரவம் அழுத்தத்தில் இருந்து கியூட்டிகிள், தசைகளுடன் நீரியல் எலும்புக்கூடாகச் செயல்படுகிறது."),
                    bdef("Epidermis (hypodermis)", "எப்பிடெர்மிஸ் (Epidermis; ஹைப்போடெர்மிஸ்)", "The syncytial layer under the cuticle of a nematode, which secretes the cuticle and forms the longitudinal cords." to
                        "நெமட்டோடாவின் கியூட்டிகிளுக்குக் கீழுள்ள சின்சிட்டிய அடுக்கு; கியூட்டிகிளைச் சுரந்து நீளவாக்கு வடங்களை உருவாக்குகிறது."),
                )),
            bsec("digestive", "Alimentary canal", "உணவுக்குழல்",
                blocks = listOf(
                    bp("The alimentary canal is a straight tube that runs the length of the body. The mouth leads into a small buccal cavity, then into a muscular, triradiate pharynx, which pumps food into the gut. The long intestine is lined by a single layer of columnar cells, which secrete enzymes and absorb food, and it opens through a short rectum at the anus. The gut is lined by cells and has no muscular wall of its own, and food is propelled by the pressure of newly ingested food behind it and by the movements of the worm." to
                        "உணவுக்குழல் உடலின் நீளம் முழுவதும் செல்லும் நேரான குழாய். வாய் சிறு வாய்க் குழிக்கும், பின் தசை, மூன்று கதிர் தொண்டைக்கும் செல்கிறது; அது உணவைக் குடலுக்குள் செலுத்துகிறது. நீண்ட குடல் ஒற்றை அடுக்கு தூண் செல்களால் மூடப்பட்டு நொதிகளைச் சுரந்து உணவை உறிஞ்சுகிறது; குறுகிய மலக்குடல் வழியாக மலவாயில் திறக்கிறது. குடல் செல்களால் மூடப்பட்டு தனக்கென தசைச் சுவர் இல்லை; பின்னால் புதிதாக உண்ட உணவின் அழுத்தத்தாலும் புழுவின் இயக்கங்களாலும் உணவு முன்னேறுகிறது."),
                    bp("There is no special respiratory system. Ascaris lives in an environment that contains very little oxygen and respires anaerobically, obtaining energy from the glycogen that is stored in the tissues. The worm excretes ammonia, and also some organic acids that result from anaerobic metabolism." to
                        "தனிச் சுவாச மண்டலம் இல்லை. அஸ்காரிஸ் ஆக்சிஜன் மிகக் குறைவான சூழலில் வாழ்ந்து, திசுக்களில் சேமித்த கிளைக்கோஜனிலிருந்து ஆற்றலைப் பெற்றுக் காற்றில்லாச் சுவாசம் செய்கிறது. புழு அம்மோனியாவையும், காற்றில்லா வளர்சிதையால் விளையும் சில கரிம அமிலங்களையும் வெளியேற்றுகிறது."),
                    bdef("Pharynx (nematode)", "தொண்டை (Pharynx; நெமட்டோடா)", "The muscular, triradiate organ at the front of the gut which pumps food in." to
                        "உணவை உள்ளே செலுத்தும், குடலின் முன்னுள்ள தசை, மூன்று கதிர் உறுப்பு."),
                )),
            bsec("systems", "Excretory and nervous systems", "கழிவு நீக்க, நரம்பு மண்டலங்கள்",
                blocks = listOf(
                    bp("The excretory system is H-shaped. Two long excretory canals run in the lateral cords, one on each side, and are joined near the front by a transverse canal, from which a single short duct opens at the ventral excretory pore. Flame cells are absent. The canals take up fluid from the pseudocoelom and discharge wastes, and they are also thought to play a part in the regulation of water." to
                        "கழிவு நீக்க மண்டலம் H வடிவம். இரு நீண்ட கழிவு நீக்கக் கால்வாய்கள் ஒவ்வொரு பக்கத்திலும் பக்க வடங்களில் ஓடி, முன்புறத்துக்கு அருகே குறுக்குக் கால்வாயால் இணைகின்றன; அதிலிருந்து ஒற்றைக் குறுகிய நாளம் வயிற்றுக் கழிவு நீக்கத் துளையில் திறக்கிறது. சுடர் செல்கள் இல்லை. கால்வாய்கள் போலி உடற்குழியிலிருந்து திரவத்தை எடுத்துக் கழிவுகளை வெளியேற்றுகின்றன; நீர் ஒழுங்குபாட்டிலும் பங்கு வகிப்பதாகக் கருதப்படுகிறது."),
                    bp("The nervous system consists of a nerve ring around the pharynx with ganglia connected to it, from which six short nerves run forwards to the lips and longitudinal cords run backwards, the dorsal and ventral being the main ones. The sense organs are the sensory papillae on the lips and around the cloaca, and the pair of amphids at the front. They are chemoreceptors and touch receptors, and enable the worm to respond to its surroundings." to
                        "நரம்பு மண்டலத்தில் தொண்டையைச் சுற்றிய நரம்பு வளையமும் அதனுடன் இணைந்த நரம்புத்திரள்களும் உள்ளன; அதிலிருந்து ஆறு குறுகிய நரம்புகள் உதடுகளுக்கு முன்னோக்கியும், நீளவாக்கு வடங்கள் பின்னோக்கியும் செல்கின்றன; முதுகு, வயிற்று வடங்கள் முதன்மையானவை. உணர் உறுப்புகள் உதடுகளிலும் குளோயாக்கா சுற்றிலுமுள்ள உணர் பாப்பில்லாக்களும், முன்னால் உள்ள இணை ஆம்ஃபிட்களும். அவை வேதி, தொடு உணர்விகள்; புழு தன் சூழலுக்குத் துலங்க உதவுகின்றன."),
                )),
            bsec("reproductive", "Reproductive system", "இனப்பெருக்க மண்டலம்",
                blocks = listOf(
                    bp("The male system is a single, long, coiled tube. The thin, thread-like testis passes without a break into a vas deferens, which widens into a seminal vesicle that stores the sperm. A muscular ejaculatory duct leads from this to the cloaca. Two copulatory spicules are lodged in pouches that open into the cloaca, and the sperm of nematodes, unlike those of most animals, are amoeboid and have no flagellum." to
                        "ஆண் மண்டலம் ஒற்றை, நீண்ட, சுருண்ட குழாய். மெல்லிய, நூல் போன்ற விந்தகம் இடைவெளியின்றி வாஸ் டிஃபெரன்ஸாகச் செல்கிறது; அது விந்தணுக்களைச் சேமிக்கும் விந்துப்பையாக விரிகிறது. தசை வெளியேற்று நாளம் அதிலிருந்து குளோயாக்காவுக்குச் செல்கிறது. இரு புணர்ச்சி ஸ்பிக்யூல்கள் குளோயாக்காவில் திறக்கும் பைகளில் உள்ளன; பெரும்பாலான விலங்குகளைப் போலன்றி, நெமட்டோடாக்களின் விந்தணுக்கள் அமீபா போன்றவை, கசையிழை இல்லை."),
                    bp("The female system is paired. There are two ovaries, which are long, thin tubes, each continuous with an oviduct that widens into a long uterus. The two uteri unite to form a short, muscular vagina that opens at the vulva. The ovary, oviduct and uterus are coiled around the intestine and fill much of the pseudocoelom. The fertilised eggs are held in the uteri, where the female may contain tens of millions. A female Ascaris lays an extraordinary number of eggs, of the order of two hundred thousand a day." to
                        "பெண் மண்டலம் இணையானது. இரு சூற்பைகள் நீண்ட, மெல்லிய குழாய்கள்; ஒவ்வொன்றும் நீண்ட கருப்பையாக விரியும் சூல்நாளத்துடன் தொடர்ச்சியாக உள்ளது. இரு கருப்பைகள் இணைந்து வல்வாவில் திறக்கும் குறுகிய, தசை யோனியை உருவாக்குகின்றன. சூற்பை, சூல்நாளம், கருப்பை குடலைச் சுற்றிச் சுருண்டு போலி உடற்குழியின் பெரும்பகுதியை நிரப்புகின்றன. கருவுற்ற முட்டைகள் கருப்பைகளில் தங்குகின்றன; பெண்ணில் பத்து மில்லியன்கள் இருக்கலாம். பெண் அஸ்காரிஸ் நாளொன்றுக்கு இரண்டு லட்சம் அளவில் அசாதாரண எண்ணிக்கையில் முட்டையிடுகிறது."),
                    bp("Fertilisation is internal. The male transfers sperm to the female by inserting his spicules into the vulva. The fertilised egg is oval, and has a thick shell with a rough outer albuminous coat which protects it, and eggs that have not been fertilised are longer and thinner-shelled. The eggs pass out in the faeces, and there they must remain for some weeks, as described below, before they are infective." to
                        "கருவுறுதல் உட்புறம். ஆண் ஸ்பிக்யூல்களை வல்வாவில் நுழைத்து பெண்ணுக்கு விந்தணுக்களைச் செலுத்துகிறது. கருவுற்ற முட்டை முட்டை வடிவம்; பாதுகாக்கும் கரடுமுரடான வெளிப்புற அல்புமின் உறையுடன் தடித்த ஓடு உண்டு; கருவுறாத முட்டைகள் நீளமாகவும் மெல்லிய ஓட்டுடனும் இருக்கும். முட்டைகள் மலத்துடன் வெளியேறி, கீழே விவரிக்கப்படுவதுபோல், தொற்றும் முன் சில வாரங்கள் அங்கு இருக்க வேண்டும்."),
                    bfig("ascaris/female-internal", "Internal anatomy of a female Ascaris, showing the pharynx, the intestine, the paired ovaries, oviducts and uteri, and the vagina opening at the vulva." to
                        "பெண் அஸ்காரிஸின் உள்ளமைப்பு: தொண்டை, குடல், இணை சூற்பைகள், சூல்நாளங்கள், கருப்பைகள், வல்வாவில் திறக்கும் யோனி."),
                    bdef("Dioecious", "ஒரு பால் உயிரி", "Having separate male and female individuals, as Ascaris has." to
                        "அஸ்காரிஸைப் போல் தனி ஆண், பெண் உயிரிகள் கொண்ட."),
                    bdef("Fertilised egg", "கருவுற்ற முட்டை (Fertilised egg)", "The thick-shelled, oval egg of Ascaris, which leaves the host in the faeces and develops in the soil." to
                        "மலத்துடன் விருந்தோம்பியை விட்டு வெளியேறி மண்ணில் வளரும், அஸ்காரிஸின் தடித்த ஓடுடைய, முட்டை வடிவ முட்டை."),
                )),
            bsec("life-cycle", "Life cycle", "வாழ்க்கைச் சுழற்சி",
                blocks = listOf(
                    bp("The life cycle is direct, which means that a single host, man, is all that is needed. The adults live in the small intestine, and the fertilised eggs leave in the faeces. In warm, moist and shaded soil the egg develops over some weeks, and the larva inside it moults to reach the stage that is infective, the larvated egg. A person is infected by swallowing such eggs in contaminated food, water or soil, or on the hands." to
                        "வாழ்க்கைச் சுழற்சி நேரடியானது; அதாவது மனிதன் என்ற ஒற்றை விருந்தோம்பி மட்டுமே தேவை. முதிர்ந்த புழுக்கள் சிறுகுடலில் வாழ்கின்றன; கருவுற்ற முட்டைகள் மலத்துடன் வெளியேறுகின்றன. வெப்பமான, ஈரமான, நிழலான மண்ணில் முட்டை சில வாரங்களில் வளர்கிறது; அதனுள் லார்வா உதிர்த்து தொற்றும் நிலையான லார்வாக் கொண்ட முட்டையை அடைகிறது. மாசடைந்த உணவு, நீர், மண்ணில் அல்லது கைகளில் உள்ள அத்தகைய முட்டைகளை விழுங்குவதால் ஒருவர் தொற்றுக்குள்ளாகிறார்."),
                    bpoints("Sequence of the life cycle", "வாழ்க்கைச் சுழற்சியின் வரிசை",
                        "Adult worms in the small intestine; the female lays fertilised eggs, which pass in the faeces." to "சிறுகுடலில் முதிர்ந்த புழுக்கள்; பெண் கருவுற்ற முட்டைகளையிடுகிறது; அவை மலத்துடன் வெளியேறுகின்றன.",
                        "In warm, moist soil the egg embryonates and becomes the infective larvated egg." to "வெப்பமான, ஈரமான மண்ணில் முட்டை கரு வளர்ந்து தொற்றும் லார்வா முட்டையாகிறது.",
                        "A person swallows infective eggs; the larvae hatch in the small intestine." to "ஒருவர் தொற்றும் முட்டைகளை விழுங்குகிறார்; லார்வாக்கள் சிறுகுடலில் பொரிகின்றன.",
                        "The larvae penetrate the intestinal wall and are carried in the blood to the liver and then the heart, and to the lungs." to "லார்வாக்கள் குடல் சுவரை ஊடுருவி இரத்தத்தில் கல்லீரலுக்கும், பின் இதயத்துக்கும், நுரையீரலுக்கும் கொண்டு செல்லப்படுகின்றன.",
                        "In the lungs they enter the air sacs, grow and moult, and then climb the bronchi and trachea to the pharynx." to "நுரையீரலில் காற்றுப்பைகளுக்குள் நுழைந்து வளர்ந்து உதிர்த்து, மூச்சுக்குழாய்கள், மூச்சுக்குழல் வழியாகத் தொண்டைக்கு ஏறுகின்றன.",
                        "They are swallowed, return to the small intestine, and mature into adults, which begin to lay eggs after about two months." to "அவை விழுங்கப்பட்டு சிறுகுடலுக்குத் திரும்பி முதிர்ந்து, சுமார் இரண்டு மாதங்களுக்குப் பின் முட்டையிடத் தொடங்குகின்றன.",
                    ),
                    bfig("ascaris/life-cycle", "The life cycle of Ascaris lumbricoides, from the adult in the intestine and the egg in the soil, through the swallowed larvated egg and the migration of the larva by the liver and lungs, back to the intestine." to
                        "அஸ்காரிஸ் லம்பிரிக்காய்டஸின் வாழ்க்கைச் சுழற்சி: குடலிலுள்ள முதிர்ந்த புழு, மண்ணிலுள்ள முட்டை, விழுங்கிய லார்வா முட்டை, கல்லீரல், நுரையீரல் வழி லார்வா நகர்வு, குடலுக்குத் திரும்புதல்."),
                    bp("The passage through the liver and lungs is a journey of development and is not a stage in a second host. The larva grows greatly during it. It can cause an inflammation of the lungs, with cough, when the larvae are numerous, and the adults can cause pain, malnutrition and sometimes obstruction of the gut when they are very numerous." to
                        "கல்லீரல், நுரையீரல் வழிப் பயணம் வளர்ச்சிப் பயணம்; இரண்டாம் விருந்தோம்பியிலுள்ள நிலை அல்ல. அதன்போது லார்வா பெருமளவு வளர்கிறது. லார்வாக்கள் அதிகமாக இருக்கும்போது இருமலுடன் நுரையீரல் அழற்சியையும், முதிர்ந்த புழுக்கள் மிக அதிகமாக இருக்கும்போது வலி, ஊட்டச்சத்துக் குறைபாடு, சில நேரங்களில் குடல் அடைப்பையும் ஏற்படுத்தலாம்."),
                    bdef("Larvated egg", "லார்வா முட்டை (Larvated egg)", "An egg that contains a fully developed larva and is infective when swallowed." to
                        "முழுமையாக வளர்ந்த லார்வாவைக் கொண்ட, விழுங்கப்பட்டால் தொற்றும் முட்டை."),
                    bdef("Embryonation", "கரு வளர்ச்சி", "The development of the embryo and larva inside the egg." to
                        "முட்டைக்குள் கருவும் லார்வாவும் வளர்தல்."),
                )),
            bsec("no-host", "No intermediate host, and the consequences for control", "இடைநிலை விருந்தோம்பி இல்லை; கட்டுப்பாட்டுக்கான விளைவுகள்",
                blocks = listOf(
                    bp("Ascaris has no need of an intermediate host. The resistant egg develops to the infective stage in the soil, and it is the egg itself that is swallowed. No miracidium, cercaria or metacercaria is present, and no snail is needed, in contrast to Fasciola. This difference is one of the chief points of comparison between the two parasites." to
                        "அஸ்காரிஸுக்கு இடைநிலை விருந்தோம்பி தேவையில்லை. எதிர்ப்புத்திறன் உள்ள முட்டை மண்ணில் தொற்றும் நிலையை அடைகிறது; முட்டையே விழுங்கப்படுகிறது. மிராசிடியம், செர்க்கேரியா, மெட்டாசெர்க்கேரியா எதுவும் இல்லை; ஃபாசியோலாவுக்கு மாறாக, நத்தை தேவையில்லை. இவ்வேறுபாடு இரு ஒட்டுண்ணிகளுக்கிடையேயான ஒப்பீட்டின் முக்கிய புள்ளிகளில் ஒன்று."),
                    bp("The consequence for control is that the attack is made on the faecal contamination of the soil and on the habits of people. It rests upon sanitation, the safe disposal of faeces, the washing of the hands and of food, and the treatment of infected people, and it does not involve the control of a snail." to
                        "கட்டுப்பாட்டுக்கான விளைவு: தாக்குதல் மண்ணின் மல மாசுபாட்டின் மீதும் மக்களின் பழக்கங்களின் மீதும் நடத்தப்படுகிறது. இது சுகாதாரம், மலத்தைப் பாதுகாப்பாக அகற்றுதல், கைகளையும் உணவையும் கழுவுதல், தொற்றுற்றவர்களுக்குச் சிகிச்சை ஆகியவற்றைச் சார்ந்தது; நத்தைக் கட்டுப்பாடு அதில் இல்லை."),
                    btable("Ascaris compared with Fasciola", "அஸ்காரிஸும் ஃபாசியோலாவும் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Fasciola" to "ஃபாசியோலா", "Ascaris" to "அஸ்காரிஸ்"),
                        listOf(
                            listOf("Class or phylum" to "வகுப்பு அல்லது தொகுதி", "Trematoda (Platyhelminthes)" to "டிரிமட்டோடா (பிளாட்டிஹெல்மிந்தீஸ்)", "Nematoda" to "நெமட்டோடா"),
                            listOf("Sexes" to "பாலினங்கள்", "Hermaphrodite" to "இருபால் உயிரி", "Separate" to "தனித்தனி"),
                            listOf("Gut" to "குடல்", "Branched, no anus" to "கிளைத்தது, மலவாய் இல்லை", "Straight, with anus" to "நேரானது, மலவாயுடன்"),
                            listOf("Intermediate host" to "இடைநிலை விருந்தோம்பி", "Snail" to "நத்தை", "None" to "இல்லை"),
                            listOf("Infective stage" to "தொற்றும் நிலை", "Metacercaria" to "மெட்டாசெர்க்கேரியா", "Larvated egg" to "லார்வா முட்டை"),
                            listOf("Site in host" to "விருந்தோம்பியில் இடம்", "Bile ducts" to "பித்த நாளங்கள்", "Small intestine" to "சிறுகுடல்"),
                        )),
                )),
            bsec("disease", "Ascariasis: effects on health", "அஸ்காரியாசிஸ்: ஆரோக்கியத்தில் விளைவுகள்",
                blocks = listOf(
                    bp("The disease caused by Ascaris is called ascariasis. Many infected people have few or no symptoms, and the effects depend on the number of worms and on the stage of the infection. During the migration of the larvae through the lungs, a cough, wheezing and fever may occur, and in some people an allergic reaction in the lung, known by the name Loeffler's syndrome, develops. Adult worms in the small intestine, if they are numerous, can compete with the child for nutrients and so contribute to poor growth and malnutrition. A large mass of worms may block the intestine, and a worm that wanders into the bile duct or the pancreatic duct can cause serious illness that needs urgent medical care.",
                        "அஸ்காரிஸ் உண்டாக்கும் நோய் அஸ்காரியாசிஸ். தொற்றடைந்த பலருக்கு அறிகுறிகள் குறைவு அல்லது இல்லை; விளைவுகள் புழுக்களின் எண்ணிக்கையையும் தொற்றின் நிலையையும் பொறுத்தவை. நுரையீரல் வழியே லார்வாக்கள் நகரும்போது இருமல், மூச்சிரைப்பு, காய்ச்சல் ஏற்படலாம்; சிலருக்கு லோஃப்ளர் நோய்த்தொகுப்பு எனப்படும் நுரையீரல் ஒவ்வாமை எதிர்வினை உருவாகிறது. சிறுகுடலில் முதிர்ந்த புழுக்கள் அதிகமாக இருந்தால் குழந்தையுடன் ஊட்டச்சத்துக்குப் போட்டியிட்டு மோசமான வளர்ச்சிக்கும் ஊட்டச்சத்துக் குறைபாட்டுக்கும் பங்களிக்கலாம். புழுக்களின் பெரிய கொத்து குடலை அடைக்கலாம்; பித்த நாளம் அல்லது கணைய நாளத்துக்குள் அலையும் புழு அவசர மருத்துவ கவனிப்பு தேவைப்படும் தீவிர நோயை உண்டாக்கலாம்."),
                    bp("Ascariasis is one of the most widespread of human infections. It is most common in warm regions where sanitation is poor and where human faeces reach the soil, and children are affected most because they play in soil and put their hands in their mouths. It is classed as a soil-transmitted helminth infection, together with hookworm and whipworm. The numbers affected are very large, and published estimates vary, so that the student should avoid quoting a figure unless it is from the prescribed text.",
                        "அஸ்காரியாசிஸ் மனிதத் தொற்றுகளில் மிகப் பரவலானவற்றில் ஒன்று. துப்புரவு மோசமாக உள்ள, மனிதக் கழிவு மண்ணை அடையும் வெப்பப் பகுதிகளில் மிகப் பொதுவானது; குழந்தைகள் மண்ணில் விளையாடி கைகளை வாயில் வைப்பதால் மிக அதிகம் பாதிக்கப்படுகின்றனர். கொக்கிப்புழு, சாட்டைப்புழுவுடன் இது மண்வழிப் பரவும் புழுத்தொற்றாக வகைப்படுத்தப்படுகிறது. பாதிக்கப்பட்டோர் எண்ணிக்கை மிகப் பெரியது; வெளியிடப்பட்ட மதிப்பீடுகள் மாறுபடுகின்றன; எனவே பரிந்துரைக்கப்பட்ட நூலில் இல்லாவிடில் எண்ணைக் குறிப்பிடுவதை மாணவர் தவிர்க்க வேண்டும்."),
                    bdef("Ascariasis", "அஸ்காரியாசிஸ் (Ascariasis)", "The disease caused by infection with Ascaris lumbricoides." to
                        "அஸ்காரிஸ் லம்ப்ரிகாய்டீஸ் தொற்றால் ஏற்படும் நோய்."),
                    bdef("Soil-transmitted helminth", "மண்வழிப் பரவும் புழு (Soil-transmitted helminth)", "A parasitic worm whose eggs or larvae must develop in the soil before they can infect a new host." to
                        "புதிய விருந்தோம்பியைத் தொற்றும் முன் முட்டைகளோ லார்வாக்களோ மண்ணில் வளர வேண்டிய ஒட்டுண்ணிப் புழு."),
                )),
            bsec("migration", "Why the larva wanders through the body", "லார்வா உடலில் ஏன் அலைகிறது",
                blocks = listOf(
                    bp("The larva of Ascaris does not simply settle in the intestine where the egg hatched. It bores through the wall, is carried in the blood to the liver and the heart and then to the lungs, climbs the air passages to the throat and is swallowed to reach the small intestine again. The route has long puzzled biologists. One suggestion is that it is the survival of an ancestral route by which larvae of the ancestors of the roundworms in other hosts passed through tissues. Another is that the larva needs the oxygen-rich environment and the growth conditions of the lung before it can mature in the gut. The student should say that the reason is not certain and that the route is a feature of the cycle that must be remembered.",
                        "அஸ்காரிஸ் லார்வா முட்டை பொரித்த குடலில் வெறுமனே தங்குவதில்லை. அது சுவரைத் துளைத்து, இரத்தத்தில் கல்லீரல், இதயத்துக்கும் பின் நுரையீரலுக்கும் கொண்டு செல்லப்பட்டு, காற்றுப் பாதைகள் வழியே தொண்டைக்கு ஏறி, விழுங்கப்பட்டு மீண்டும் சிறுகுடலை அடைகிறது. இப்பாதை உயிரியலாளர்களை நீண்டகாலம் குழப்பியுள்ளது. ஒரு கருத்து, பிற விருந்தோம்பிகளில் உருளைப்புழுக்களின் மூதாதைகளின் லார்வாக்கள் திசுக்கள் வழியே சென்ற மூதாதைப் பாதையின் எச்சம் என்பது. மற்றொன்று, குடலில் முதிர்வதற்கு முன் லார்வாவுக்கு நுரையீரலின் ஆக்சிஜன் நிறைந்த சூழலும் வளர்ச்சி நிலைமைகளும் தேவை என்பது. காரணம் உறுதியாகத் தெரியவில்லை, பாதை நினைவில் கொள்ள வேண்டிய சுழற்சிப் பண்பு என்று மாணவர் கூற வேண்டும்."),
                    bp("The route has medical consequences, since it is the migration through the lung that causes the early symptoms, and it is the reason why a person has worms in the gut only some weeks after swallowing the eggs. In diagnosis, eggs are found in the faeces only after the worms have matured and mated, which takes several weeks, and so a test of the faeces done too early may be negative.",
                        "பாதைக்கு மருத்துவ விளைவுகள் உள்ளன; நுரையீரல் வழி நகர்வே ஆரம்ப அறிகுறிகளுக்குக் காரணம்; முட்டைகளை விழுங்கிய சில வாரங்களுக்குப் பின்னரே குடலில் புழுக்கள் இருப்பதற்கும் இதுவே காரணம். நோயறிதலில் புழுக்கள் முதிர்ந்து இணைந்த பின்னரே, சில வாரங்கள் ஆகும், மலத்தில் முட்டைகள் காணப்படுவதால், மிக முன்னதாக செய்யப்படும் மலப் பரிசோதனை எதிர்மறையாக வரலாம்."),
                )),
            bsec("control", "Prevention and control", "தடுப்பும் கட்டுப்பாடும்",
                blocks = listOf(
                    bpoints("Measures that follow from the life cycle", "வாழ்க்கைச் சுழற்சியிலிருந்து வரும் நடவடிக்கைகள்",
                        "Sanitary disposal of faeces, so that eggs do not reach the soil." to "முட்டைகள் மண்ணை அடையாதபடி மலத்தைத் துப்புரவாக அகற்றுதல்.",
                        "Washing of hands before meals and after contact with soil." to "உணவுக்கு முன்னும் மண்ணைத் தொட்டபின்னும் கைகளைக் கழுவுதல்.",
                        "Washing and cooking of vegetables, and the use of clean water." to "காய்கறிகளைக் கழுவிச் சமைத்தல்; தூய நீரைப் பயன்படுத்துதல்.",
                        "Avoidance of untreated human waste as manure." to "சுத்திகரிக்கப்படாத மனிதக் கழிவை உரமாகப் பயன்படுத்துவதைத் தவிர்த்தல்.",
                        "Periodic treatment of school-age children in areas where the infection is common, through mass deworming programmes, which in India are run on fixed days." to "தொற்று பொதுவாக உள்ள பகுதிகளில் பள்ளிவயது குழந்தைகளுக்கு வெகுஜன குடற்புழு நீக்கத் திட்டங்கள் வழியே, இந்தியாவில் நிர்ணயித்த நாட்களில் நடத்தப்படும், அவ்வப்போது சிகிச்சையளித்தல்.",
                    ),
                    bp("The eggs are resistant, so that they survive in the soil for months, and the usual disinfectants do not destroy them. This is the reason why the control of the disease rests on sanitation and on the interruption of the faecal-oral route, and why treatment alone gives only temporary relief in places where people are re-infected. An answer should connect each measure to the stage of the cycle at which it acts.",
                        "முட்டைகள் தாங்கும் திறன் கொண்டவை; மண்ணில் மாதங்கள் உயிர்வாழ்கின்றன; வழக்கமான கிருமிநாசினிகள் அவற்றை அழிப்பதில்லை. இதனாலேயே நோய்க் கட்டுப்பாடு துப்புரவிலும் மலம்-வாய் வழியை இடைமறிப்பதிலும் சார்ந்துள்ளது; மக்கள் மீண்டும் தொற்றடையும் இடங்களில் சிகிச்சை மட்டும் தற்காலிக நிவாரணமே தருகிறது. ஒவ்வொரு நடவடிக்கையையும் அது செயல்படும் சுழற்சி நிலையுடன் இணைத்து விடை எழுத வேண்டும்."),
                )),
            bsec("answering", "Answering questions on Ascaris", "அஸ்காரிஸ் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("Questions on Ascaris ask for the external features and the differences between the sexes, the reproductive system, the life cycle with a diagram, and the pathogenicity. The male is smaller than the female and has a curved tail bearing copulatory spicules, and the female is straight, with the vulva about one-third of the way along the body. The life cycle begins with the fertilised egg passed in the faeces, the larva develops in the egg in the soil, the infective egg is swallowed, and the larva hatches in the intestine and makes the lung migration before returning to the intestine as an adult. The diagram needs to show the route as a loop and to mark where the infection enters.",
                        "அஸ்காரிஸ் குறித்த வினாக்கள் வெளிப்புறப் பண்புகளும் பாலினங்களுக்கிடையிலான வேறுபாடுகளும், இனப்பெருக்க மண்டலம், படத்துடன் வாழ்க்கைச் சுழற்சி, நோய்க்கடத்தும் தன்மையைக் கேட்கின்றன. ஆண் பெண்ணை விடச் சிறியது; புணர்ச்சி ஸ்பிக்யூல்களைத் தாங்கும் வளைந்த வால் கொண்டது; பெண் நேரானது; வல்வா உடலின் ஏறத்தாழ மூன்றில் ஒரு பங்கு தொலைவில் உள்ளது. வாழ்க்கைச் சுழற்சி மலத்தில் வெளியேறும் கருவுற்ற முட்டையுடன் தொடங்குகிறது; லார்வா மண்ணில் முட்டைக்குள் வளர்கிறது; தொற்றும் முட்டை விழுங்கப்படுகிறது; லார்வா குடலில் பொரித்து, நுரையீரல் வழிப் பயணம் மேற்கொண்டு, முதிர்ந்ததாக மீண்டும் குடலுக்குத் திரும்புகிறது. படம் பாதையை ஒரு வளையமாகக் காட்டி தொற்று நுழையும் இடத்தைக் குறிக்க வேண்டும்."),
                    bpoints("Frequent errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Saying that the worm is transmitted by a mosquito or enters through the skin; the route is by mouth." to "புழு கொசுவால் பரவுகிறது அல்லது தோல் வழி நுழைகிறது என்று கூறுதல்; பாதை வாய் வழியே.",
                        "Giving an intermediate host; Ascaris has none." to "இடைநிலை விருந்தோம்பியைத் தருதல்; அஸ்காரிஸுக்கு இல்லை.",
                        "Stating that the egg that is passed in the faeces is already infective; it needs to develop in the soil." to "மலத்தில் வெளியேறும் முட்டை ஏற்கெனவே தொற்றும் தன்மை கொண்டது என்று கூறுதல்; அது மண்ணில் வளர வேண்டும்.",
                        "Giving the female the curved tail." to "பெண்ணுக்கு வளைந்த வாலைத் தருதல்.",
                    ),
                )),
        ),
        summary = listOf(
            "Ascaris lumbricoides is a large dioecious nematode of the human small intestine; the female is larger than the male." to "அஸ்காரிஸ் லம்பிரிக்காய்டஸ் மனித சிறுகுடலின் பெரிய ஒரு பால் உயிரி நெமட்டோடா; பெண் ஆணைவிடப் பெரியது.",
            "The body has three lips, a cuticle, an epidermis with four cords, longitudinal muscles only, and a pseudocoelom." to "உடலில் மூன்று உதடுகள், கியூட்டிகிள், நான்கு வடங்களுடன் எப்பிடெர்மிஸ், நீளவாக்கு தசைகள் மட்டும், போலி உடற்குழி உண்டு.",
            "The gut is straight and complete; the excretory system is H-shaped, without flame cells." to "குடல் நேரானதும் முழுமையானதும்; கழிவு நீக்க மண்டலம் சுடர் செல்கள் இன்றி H வடிவம்.",
            "The male has a single testis and spicules; the female has paired ovaries and uteri that open at the vulva." to "ஆணுக்கு ஒற்றை விந்தகமும் ஸ்பிக்யூல்களும்; பெண்ணுக்கு வல்வாவில் திறக்கும் இணை சூற்பைகளும் கருப்பைகளும்.",
            "The cycle is direct: egg in soil, infective larvated egg swallowed, larva to liver and lungs, swallowed back to the intestine." to "சுழற்சி நேரடியானது: மண்ணில் முட்டை, தொற்றும் லார்வா முட்டை விழுங்கப்படுதல், லார்வா கல்லீரல், நுரையீரலுக்கு, மீண்டும் குடலுக்கு விழுங்கப்படுதல்.",
            "No snail or other intermediate host is needed; control rests on sanitation, hygiene and treatment." to "நத்தையோ பிற இடைநிலை விருந்தோம்பியோ தேவையில்லை; கட்டுப்பாடு சுகாதாரம், தூய்மை, சிகிச்சையைச் சார்ந்தது.",
        ),
        checks = listOf(
            bqc("How many lips has Ascaris?", "அஸ்காரிஸுக்கு எத்தனை உதடுகள்?",
                "Three: one dorsal and two ventro-lateral.", "மூன்று: ஒரு முதுகுப்புறம், இரு வயிற்று-பக்க உதடுகள்."),
            bqc("Which stage of Ascaris infects man?", "அஸ்காரிஸின் எந்த நிலை மனிதனைத் தொற்றுகிறது?",
                "The embryonated (larvated) egg swallowed with food, water or soil.", "உணவு, நீர், மண்ணுடன் விழுங்கப்படும் கரு வளர்ந்த (லார்வா) முட்டை."),
            bqc("Trace the path of the larva in the body.", "உடலில் லார்வாவின் பாதையைப் பின்தொடர்க.",
                "Intestine, blood to liver and heart, lungs, bronchi and trachea to the pharynx, swallowed to the small intestine.", "குடல், இரத்தம் வழி கல்லீரல், இதயம், நுரையீரல், மூச்சுக்குழாய்கள், மூச்சுக்குழல் வழி தொண்டை, விழுங்கப்பட்டு சிறுகுடல்."),
            bqc("Why does Ascaris need no snail?", "அஸ்காரிஸுக்கு ஏன் நத்தை தேவையில்லை?",
                "Its life cycle is direct; the egg becomes infective in the soil and is swallowed.", "அதன் சுழற்சி நேரடியானது; முட்டை மண்ணில் தொற்றும் நிலையை அடைந்து விழுங்கப்படுகிறது."),
            bqc("What is the shape of the excretory system?", "கழிவு நீக்க மண்டலத்தின் வடிவம் என்ன?",
                "H-shaped, with two lateral canals joined by a transverse canal; no flame cells.", "H வடிவம்; குறுக்குக் கால்வாயால் இணைந்த இரு பக்கக் கால்வாய்கள்; சுடர் செல்கள் இல்லை."),
        ),
    )
}
