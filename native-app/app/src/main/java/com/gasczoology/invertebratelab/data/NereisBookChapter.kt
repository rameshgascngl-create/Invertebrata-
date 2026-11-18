package com.gasczoology.invertebratelab.data

/** Book-style chapter: Nereis, an errant polychaete. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object NereisBookChapter {
    val chapter = bchapter(
        lessonId = "u4-nereis",
        title = "Nereis: structure, parapodia and epitoky" to "நெரிஸ்: அமைப்பு, பாரபோடியா, எபிடோகி",
        objectives = listOf(
            "Describe the habit, habitat and external features of Nereis." to "நெரிஸின் பழக்கம், வாழிடம், வெளிப்புற அம்சங்களை விவரிக்க.",
            "Describe the structure of a parapodium and state its functions." to "பாரபோடியத்தின் அமைப்பை விவரித்து அதன் செயல்களைக் கூற.",
            "Outline the digestive, circulatory, excretory and nervous systems." to "செரிமான, இரத்த ஓட்ட, கழிவு நீக்க, நரம்பு மண்டலங்களை வரையறுக்க.",
            "Describe reproduction and the trochophore larva." to "இனப்பெருக்கத்தையும் ட்ரோகோபோர் லார்வாவையும் விவரிக்க.",
            "Explain epitoky and state the limits of the generalisation." to "எபிடோகியை விளக்கி பொதுமைப்படுத்தலின் வரம்புகளைக் கூற.",
        ),
        introduction = listOf(
            "Nereis is the standard example of the errant polychaetes, the free-moving bristle worms that crawl, burrow and swim. The common species used in laboratories live in burrows or under stones in the muddy or sandy parts of the shore, and in estuaries, where they come out to feed. The worm is a good example for a student because the body is simple and the repetition of its organs is easy to see, and because it shows how the general characters of the phylum are expressed in a marine animal." to
                "நெரிஸ் எரண்ட் பாலிகீட்டாக்களின், அதாவது ஊர்ந்து, துளையிட்டு, நீந்தும் சுதந்திரமாக நகரும் முள்மயிர்ப் புழுக்களின், நிலையான எடுத்துக்காட்டு. ஆய்வகங்களில் பயன்படும் பொதுவான இனங்கள் கரையின் சேற்று அல்லது மணற் பகுதிகளிலும் முகத்துவாரங்களிலும் துளைகளிலோ கற்களுக்கடியிலோ வாழ்ந்து உண்பதற்காக வெளியே வருகின்றன. உடல் எளிமையானது, உறுப்புகளின் மீள்வு எளிதில் தெரிவது, தொகுதியின் பொதுப் பண்புகள் கடல் விலங்கில் எவ்வாறு வெளிப்படுகின்றன என்பதைக் காட்டுவது ஆகியவற்றால் மாணவருக்கு இது நல்ல எடுத்துக்காட்டு.",
            "The chapter describes the external form, the parapodium in detail, since it is the structure most often asked for, then the internal systems, and finally the reproduction of the worm, which includes the transformation called epitoky." to
                "வெளிப்புற வடிவத்தையும், பாரபோடியத்தை விரிவாகவும், ஏனெனில் அதுவே அடிக்கடி கேட்கப்படும் அமைப்பு, பின்னர் உள் மண்டலங்களையும், இறுதியாக எபிடோகி எனப்படும் உருமாற்றத்தை உள்ளடக்கும் புழுவின் இனப்பெருக்கத்தையும் அத்தியாயம் விவரிக்கிறது.",
        ),
        sections = listOf(
            bsec("external", "Habit and external features", "பழக்கமும் வெளிப்புற அம்சங்களும்",
                blocks = listOf(
                    bp("The body of Nereis is elongated, slightly flattened from top to bottom, and in the larger species may be some ten to twenty centimetres long. It is made up of a head region, a trunk of many similar segments and a terminal pygidium. The colour is brownish or greenish, and in life the surface has an iridescent sheen from the thin cuticle. The ventral surface is paler. The worm is a predator and an omnivore, feeding on small animals, algae and detritus, and it is active mainly at night." to
                        "நெரிஸின் உடல் நீளமானது, மேலிருந்து கீழாகச் சற்றுத் தட்டையானது; பெரிய இனங்களில் சுமார் பத்து முதல் இருபது சென்டிமீட்டர் நீளம் இருக்கலாம். தலைப் பகுதி, பல ஒத்த கண்டங்களின் உடற்பகுதி, முனை பைஜிடியம் ஆகியவற்றால் ஆனது. நிறம் பழுப்பு அல்லது பச்சை; உயிருள்ளபோது மெல்லிய புறத்தோலால் மேற்பரப்பில் வானவில் மினுமினுப்பு உண்டு. வயிற்றுப்புறம் வெளிறியது. புழு வேட்டையாடியும் அனைத்துண்ணியும்; சிறு விலங்குகள், பாசிகள், சிதைவுப் பொருள்களை உண்கிறது; பெரும்பாலும் இரவில் செயல்படுகிறது."),
                    bp("The head consists of the prostomium, which lies in front of the mouth, and the peristomium, the first body ring, around the mouth. The prostomium carries two short sensory tentacles, two palps with two lobes each, and four small eyes. The peristomium carries four pairs of long tentacular cirri. These are all sensory structures. The pygidium at the posterior end bears the anus and a pair of anal cirri. Between them lie the trunk segments, each bearing a pair of parapodia." to
                        "தலை வாய்க்கு முன்னுள்ள புரோஸ்டோமியம், வாயைச் சுற்றிய முதல் உடல் வளையமான பெரிஸ்டோமியம் ஆகியவற்றால் ஆனது. புரோஸ்டோமியம் இரு குட்டை உணர்வுக் கொம்புகள், ஒவ்வொன்றும் இரு பகுதிகளுடைய இரு பால்ப்கள், நான்கு சிறு கண்களைத் தாங்குகிறது. பெரிஸ்டோமியம் நான்கு இணை நீண்ட உணர்கொம்புத் தண்டுகளைத் தாங்குகிறது. இவை அனைத்தும் உணர்வு அமைப்புகள். பின் முனையிலுள்ள பைஜிடியம் மலவாயையும் ஓர் இணை மலவாய்த் தண்டுகளையும் தாங்குகிறது. இவற்றுக்கிடையே ஒவ்வொன்றும் ஓர் இணை பாரபோடியாக்களைத் தாங்கும் உடற்பகுதி கண்டங்கள் உள்ளன."),
                    bfig("lib/nereis-external",
                        "External features of Nereis, dorsal view.",
                        "நெரிஸின் வெளிப்புற அம்சங்கள், முதுகுப் புறக் காட்சி."),
                    bfig("lib/nereis-head",
                        "The enlarged head region of Nereis.",
                        "நெரிஸின் தலைப் பகுதி, பெரிதாக்கிய காட்சி."),
                    bdef("Errant polychaete", "எரண்ட் பாலிகீட்டா (Errant polychaete)", "A polychaete that moves freely, crawling, burrowing or swimming, and does not live permanently in a tube." to
                        "நிரந்தரமாகக் குழாயில் வாழாமல் ஊர்ந்து, துளையிட்டு அல்லது நீந்தி சுதந்திரமாக நகரும் பாலிகீட்டா."),
                    bdef("Prostomium", "புரோஸ்டோமியம்", "The part of the head in front of the mouth, bearing tentacles, palps and eyes." to
                        "வாய்க்கு முன்னுள்ள தலைப் பகுதி; உணர்கொம்புகள், பால்ப்கள், கண்களைத் தாங்குகிறது."),
                    bdef("Peristomium", "பெரிஸ்டோமியம்", "The first body ring, surrounding the mouth, which bears the tentacular cirri." to
                        "வாயைச் சுற்றியுள்ள முதல் உடல் வளையம்; உணர்கொம்புத் தண்டுகளைத் தாங்குகிறது."),
                    bdef("Pygidium", "பைஜிடியம்", "The terminal part of the body of an annelid, carrying the anus, from which new segments are added." to
                        "அனெலிடாவின் உடலின் முனைப் பகுதி; மலவாயைத் தாங்குகிறது; புதிய கண்டங்கள் இங்கிருந்து சேர்க்கப்படுகின்றன."),
                )),
            bsec("parapodium", "The parapodium", "பாரபோடியம்",
                blocks = listOf(
                    bp("A typical trunk parapodium is a paired, fleshy lateral outgrowth of the segment, and it is biramous, which means that it has two branches or lobes. The dorsal branch is the notopodium and the ventral one is the neuropodium. Each lobe is supported internally by a stout chitinous rod, the aciculum, and each lobe carries a bundle of chaetae that project through its tip. Attached to the base of the notopodium is a dorsal cirrus, and attached to the neuropodium is a ventral cirrus. Both are slender and sensory." to
                        "வழக்கமான உடற்பகுதிப் பாரபோடியம் கண்டத்தின் இணைந்த சதைப்பற்றுள்ள பக்க வளர்ச்சி; அது இருகிளையுடையது, அதாவது இரு கிளைகள் அல்லது மடல்கள் கொண்டது. முதுகுப்புறக் கிளை நோட்டோபோடியம்; வயிற்றுப்புறக் கிளை நியூரோபோடியம். ஒவ்வொரு மடலும் உள்ளே தடித்த கைட்டின் தண்டான அசிகுலத்தால் தாங்கப்படுகிறது; ஒவ்வொரு மடலும் அதன் நுனியில் வெளியே நீளும் சீட்டாத் தொகுப்பைத் தாங்குகிறது. நோட்டோபோடியத்தின் அடியில் முதுகுப்புறத் தண்டும் நியூரோபோடியத்தில் வயிற்றுப்புறத் தண்டும் இணைந்துள்ளன. இரண்டும் மெல்லியவை, உணர்வுடையவை."),
                    bfig("lib/nereis-parapodium",
                        "A biramous parapodium of Nereis.",
                        "நெரிஸின் இருகிளைப் பாரபோடியம்."),
                    bpoints("Functions of the parapodium", "பாரபோடியத்தின் செயல்கள்",
                        "Locomotion: the parapodia are moved in a coordinated way in crawling and, as paddles, in swimming." to "இயக்கம்: ஊர்தலில் ஒருங்கிணைந்து அசைக்கப்படுகின்றன; நீந்துதலில் துடுப்புகளாகச் செயல்படுகின்றன.",
                        "Anchorage and traction: the chaetae grip the substratum and help the worm to hold its place in its burrow." to "நங்கூரமும் இழுவையும்: சீட்டாக்கள் அடித்தளத்தைப் பற்றி புழு துளையில் இடத்தைத் தக்கவைக்க உதவுகின்றன.",
                        "Respiration: the parapodium is richly supplied with blood vessels, and its large surface is used for gas exchange." to "சுவாசம்: பாரபோடியத்துக்கு இரத்த நாளங்கள் மிகுதி; அதன் பெரிய பரப்பு வாயுப் பரிமாற்றத்துக்குப் பயன்படுகிறது.",
                        "Sensation: the dorsal and ventral cirri are sensory." to "உணர்தல்: முதுகுப்புற, வயிற்றுப்புறத் தண்டுகள் உணர்வுடையவை.",
                    ),
                    bp("In an answer on the parapodium the function of respiration should be linked with the blood supply, since Nereis has no gills of special form. The exchange of gases takes place across the thin surface of the whole body, and the parapodia, which are highly vascular, provide most of the area. The sentence that the parapodium is the respiratory organ of Nereis should be modified: it is a principal site of gas exchange, and the whole surface contributes." to
                        "பாரபோடியம் பற்றிய விடையில் சுவாசச் செயலை இரத்த விநியோகத்துடன் இணைக்க வேண்டும்; ஏனெனில் நெரிஸுக்கு சிறப்பு வடிவச் செவுள்கள் இல்லை. வாயுப் பரிமாற்றம் முழு உடலின் மெல்லிய மேற்பரப்பிலும் நடக்கிறது; அதிக நாளங்களுள்ள பாரபோடியாக்கள் பெரும்பகுதிப் பரப்பைத் தருகின்றன. பாரபோடியமே நெரிஸின் சுவாச உறுப்பு என்ற வாக்கியம் மாற்றப்பட வேண்டும்: அது வாயுப் பரிமாற்றத்தின் ஒரு முதன்மை இடம்; முழு மேற்பரப்பும் பங்களிக்கிறது."),
                    bdef("Biramous", "இருகிளை (Biramous)", "Having two branches, as a parapodium with a dorsal notopodium and a ventral neuropodium." to
                        "முதுகுப்புற நோட்டோபோடியம், வயிற்றுப்புற நியூரோபோடியத்துடன் கூடிய பாரபோடியம் போல, இரு கிளைகள் உடைய."),
                    bdef("Aciculum", "அசிகுலம் (Aciculum)", "A stout chitinous rod that supports a lobe of the parapodium internally." to
                        "பாரபோடியத்தின் ஒரு மடலை உள்ளே தாங்கும் தடித்த கைட்டின் தண்டு."),
                    bdef("Cirrus", "தண்டு (சிரஸ்)", "A slender, sensory, tentacle-like outgrowth, such as those on the parapodium and the peristomium." to
                        "பாரபோடியம், பெரிஸ்டோமியத்தில் உள்ளவை போன்ற மெல்லிய, உணர்வுடைய, உணர்கொம்பு போன்ற வளர்ச்சி."),
                )),
            bsec("internal", "Digestive, circulatory, excretory and nervous systems", "செரிமான, இரத்த ஓட்ட, கழிவு நீக்க, நரம்பு மண்டலங்கள்",
                blocks = listOf(
                    bp("The mouth opens into a muscular pharynx that can be everted through the mouth, like a glove-finger, to seize prey. The everted pharynx carries a pair of strong chitinous jaws and, in many species, small teeth or papillae. The pharynx is withdrawn by retractor muscles. It leads into a short oesophagus, a straight intestine that runs the length of the body, and the anus on the pygidium. Digestive glands open into the oesophagus, and digestion and absorption take place in the intestine." to
                        "வாய் தசைத் தொண்டைக்குள் திறக்கிறது; இரையைப் பிடிக்க கையுறை விரல் போல வாய் வழியே இதை வெளித்தள்ளலாம். வெளித்தள்ளிய தொண்டை ஒரு இணை வலிமையான கைட்டின் தாடைகளையும், பல இனங்களில் சிறு பற்கள் அல்லது முளைப்புகளையும் தாங்குகிறது. தொண்டை இழுப்புத் தசைகளால் உள்ளிழுக்கப்படுகிறது. அது குட்டை உணவுக்குழல், உடல் நீளம் முழுவதும் ஓடும் நேரான குடல், பிஜிடியத்திலுள்ள மலவாய் வழியாகச் செல்கிறது. செரிமானச் சுரப்பிகள் உணவுக்குழலில் திறக்கின்றன; செரிமானமும் உறிஞ்சுதலும் குடலில் நடக்கின்றன."),
                    bp("The circulation is closed. A dorsal vessel carries the blood forwards, a ventral vessel carries it backwards, and the two are joined in each segment by segmental vessels that supply the gut, the body wall and the parapodia. The blood contains a respiratory pigment dissolved in the plasma, and the dorsal vessel is contractile and drives the circulation. Excretion is by paired metanephridia in the segments, each opening into the coelom by a ciliated funnel and to the outside by a pore on the ventral side of the segment behind." to
                        "இரத்த ஓட்டம் மூடியது. முதுகுப்புற நாளம் இரத்தத்தை முன்னோக்கியும் வயிற்றுப்புற நாளம் பின்னோக்கியும் கொண்டு செல்கின்றன; குடல், உடற்சுவர், பாரபோடியாக்களுக்கு விநியோகிக்கும் கண்ட நாளங்களால் ஒவ்வொரு கண்டத்திலும் இரண்டும் இணைகின்றன. இரத்தத்தில் பிளாஸ்மாவில் கரைந்த சுவாச நிறமி உண்டு; முதுகுப்புற நாளம் சுருங்கி ஓட்டத்தை இயக்குகிறது. கழிவு நீக்கம் கண்டங்களிலுள்ள இணை மெட்டாநெஃப்ரிடியாக்களால்; ஒவ்வொன்றும் கசையிழைப் புனலால் உடற்குழிக்குள்ளும் பின்புறக் கண்டத்தின் வயிற்றுப்புறத் துளையால் வெளியேயும் திறக்கிறது."),
                    bp("The nervous system consists of a bilobed brain in the prostomium, a pair of circumpharyngeal connectives and a double ventral nerve cord with a ganglion in each segment, from which nerves pass to the muscles and the parapodia. The eyes, palps, tentacles and cirri are the sense organs, and a pair of ciliated pits behind the prostomium, the nuchal organs, are chemoreceptors. The coelom is divided by septa, which in Nereis are thin, and the coelomic fluid serves as the hydrostatic skeleton." to
                        "நரம்பு மண்டலம் புரோஸ்டோமியத்திலுள்ள இருகூறு மூளை, ஓர் இணை தொண்டைச்சுற்று இணைப்பிகள், ஒவ்வொரு கண்டத்திலும் தசைகளுக்கும் பாரபோடியாக்களுக்கும் நரம்புகளை அனுப்பும் ஒரு நரம்புத் திரளுடைய இரட்டை வயிற்றுப்புற நரம்புவடம் ஆகியவற்றைக் கொண்டது. கண்கள், பால்ப்கள், உணர்கொம்புகள், தண்டுகள் உணர்வு உறுப்புகள்; புரோஸ்டோமியத்துக்குப் பின்னுள்ள ஓர் இணை கசையிழைக் குழிகளான நுக்கல் உறுப்புகள் வேதி ஏற்பிகள். உடற்குழி தடுப்புகளால் பிரிக்கப்பட்டுள்ளது; நெரிஸில் அவை மெல்லியவை; உடற்குழி திரவம் நீரழுத்த எலும்புக்கூடாகப் பயன்படுகிறது."),
                    bfig("lib/nereis-digestive",
                        "The digestive system of Nereis.",
                        "நெரிஸின் செரிமான மண்டலம்."),
                    bfig("lib/nereis-circulatory",
                        "The circulatory system of Nereis.",
                        "நெரிஸின் இரத்த ஓட்ட மண்டலம்."),
                    bfig("lib/nereis-nervous",
                        "The nervous system of Nereis.",
                        "நெரிஸின் நரம்பு மண்டலம்."),
                    bfig("lib/nereis-excretory",
                        "The excretory system of Nereis.",
                        "நெரிஸின் கழிவு நீக்க மண்டலம்."),
                    bdef("Eversible pharynx", "வெளித்தள்ளும் தொண்டை (Eversible pharynx)", "A pharynx that can be turned inside out through the mouth, as a proboscis, to seize food." to
                        "உணவைப் பிடிக்க, துதிக்கையாக வாய் வழி உட்புறம் வெளியாகத் திருப்பப்படக்கூடிய தொண்டை."),
                    bdef("Nuchal organ", "நுக்கல் உறுப்பு (Nuchal organ)", "A ciliated chemosensory pit on the head of a polychaete." to
                        "பாலிகீட்டாவின் தலையிலுள்ள கசையிழை வேதி உணர் குழி."),
                )),
            bsec("locomotion", "Movement and way of life", "இயக்கமும் வாழ்முறையும்",
                blocks = listOf(
                    bp("Nereis uses three kinds of movement. When it crawls over a surface, the parapodia of one side move forward together and then press backwards against the ground, and the waves of the two sides alternate, so that the body is pushed forward with a slight side-to-side flexure. When it swims, the body is thrown into waves of lateral undulation and the parapodia act as paddles, the movement being quicker and more vigorous. When it burrows into mud or sand, the pharynx and the head are pushed in first and the longitudinal and circular muscles then draw the rest of the body after them." to
                        "நெரிஸ் மூன்று வகை இயக்கங்களைப் பயன்படுத்துகிறது. மேற்பரப்பில் ஊரும்போது ஒரு பக்கத்தின் பாரபோடியாக்கள் ஒன்றாக முன்னோக்கி நகர்ந்து பின்னர் தரைக்கு எதிராகப் பின்னோக்கி அழுத்துகின்றன; இரு பக்கங்களின் அலைகளும் மாறி மாறி வருவதால் சிறிய பக்கவாட்டு வளைவுடன் உடல் முன்னே தள்ளப்படுகிறது. நீந்தும்போது உடல் பக்கவாட்டு அலைகளாக வளைகிறது; பாரபோடியாக்கள் துடுப்புகளாகச் செயல்படுகின்றன; இயக்கம் வேகமாகவும் வீரியமாகவும் உள்ளது. சேறு அல்லது மணலில் துளையிடும்போது தொண்டையும் தலையும் முதலில் உள்ளே தள்ளப்பட்டு, நீளவாட்டு, வட்டத் தசைகள் உடலின் மீதியை அவற்றின் பின் இழுக்கின்றன."),
                    bp("The coordination of so many appendages depends upon the segmental nervous system. Each ganglion controls the muscles of its own segment, but the ganglia are linked by the ventral cord, so that the movements of consecutive segments are timed with a regular delay and a wave passes along the body. This is an advantage of metamerism that is considered in the next chapters. The simple point to retain is that repetition of the nerves and muscles makes it possible for the worm to produce a wave of movement and to alter the pattern for crawling, swimming or burrowing." to
                        "இத்தனை உறுப்புகளின் ஒருங்கிணைப்பு கண்ட நரம்பு மண்டலத்தைச் சார்ந்தது. ஒவ்வொரு நரம்புத் திரளும் தன் கண்டத்தின் தசைகளைக் கட்டுப்படுத்துகிறது; ஆனால் திரள்கள் வயிற்றுப்புற வடத்தால் இணைக்கப்பட்டுள்ளதால் அடுத்தடுத்த கண்டங்களின் இயக்கங்கள் ஒழுங்கான தாமதத்துடன் நேரம் வகுக்கப்பட்டு அலை உடலில் கடந்து செல்கிறது. இது உடற்கண்டமாதலின் நன்மை; அடுத்த அத்தியாயங்களில் பரிசீலிக்கப்படுகிறது. நினைவில் கொள்ள வேண்டிய எளிய புள்ளி: நரம்புகளும் தசைகளும் மீள்வதால் புழு இயக்க அலையை உருவாக்கவும் ஊர்தல், நீந்துதல், துளையிடலுக்கு ஏற்ப வடிவத்தை மாற்றவும் முடிகிறது."),
                    bp("The way of life of the worm is that of an active predator and scavenger. It lies in wait in its burrow, or moves about at night, everts the pharynx to seize small animals, and draws algae and bits of dead material into the mouth. The sense organs, the tentacles and the cirri, tell it of the presence of prey, and the nuchal organs of food by chemical means. Nereis in turn is food for fish and for birds on the shore, and in the sea its swarming stage is a very important source of food for fish." to
                        "புழுவின் வாழ்முறை செயலூக்கம் மிக்க வேட்டையாடியும் தோட்டியும் ஆகும். அது தன் துளையில் காத்திருக்கிறது அல்லது இரவில் நகர்கிறது; சிறு விலங்குகளைப் பிடிக்க தொண்டையை வெளித்தள்ளுகிறது; பாசிகளையும் இறந்த பொருள் துண்டுகளையும் வாய்க்குள் இழுக்கிறது. உணர்வு உறுப்புகளான உணர்கொம்புகளும் தண்டுகளும் இரையின் இருப்பையும், நுக்கல் உறுப்புகள் உணவை வேதிமுறையிலும் அதற்குத் தெரிவிக்கின்றன. நெரிஸ் தன் பங்குக்கு மீன்களுக்கும் கரைப் பறவைகளுக்கும் உணவாகிறது; கடலில் அதன் கூட்டமாகச் சேரும் நிலை மீன்களுக்கு மிக முக்கிய உணவு ஆதாரம்."),
                    bdef("Lateral undulation", "பக்கவாட்டு அலைவு (Lateral undulation)", "Movement of the body in waves from side to side, used in swimming." to
                        "நீந்துவதற்குப் பயன்படும், உடல் பக்கவாட்டில் அலைகளாக அசைதல்."),
                )),
            bsec("reproduction", "Reproduction and the trochophore", "இனப்பெருக்கமும் ட்ரோகோஃபோரும்",
                blocks = listOf(
                    bp("The sexes are separate. In Nereis there are no permanent gonads, and the gametes arise from the lining of the coelom in many segments, and mature while floating in the coelomic fluid. The sexes are not easily distinguished from the outside until the worm becomes mature. At maturity the gametes are released into the water, usually by rupture of the body wall or through the nephridia, depending on the species, and fertilisation is external." to
                        "பால் தனி. நெரிஸில் நிலையான இனப்பெருக்க சுரப்பிகள் இல்லை; கேமீட்டுகள் பல கண்டங்களின் உடற்குழி உள்ளுறையிலிருந்து தோன்றி உடற்குழி திரவத்தில் மிதந்தபடி முதிர்கின்றன. புழு முதிர்ச்சியடையும் வரை வெளியிலிருந்து பாலை எளிதில் வேறுபடுத்த முடியாது. முதிர்ச்சியில் இனத்தைப் பொறுத்து, பெரும்பாலும் உடற்சுவர் உடைந்தோ நெஃப்ரிடியாக்கள் வழியாகவோ கேமீட்டுகள் நீரில் விடுவிக்கப்படுகின்றன; கருவுறுதல் வெளிப்புறம்."),
                    bp("The fertilised egg undergoes spiral cleavage and develops into a trochophore, a free-swimming larva with a band of cilia, the prototroch, around its middle and a tuft at the apex. It feeds on plankton. The trochophore elongates, acquires segments and chaetae, and becomes a metatrochophore and then a young worm that settles to the bottom, a stage in some species called the nectochaete. The trochophore is an important character linking the annelids with the molluscs, the larvae of which are similar." to
                        "கருவுற்ற முட்டை சுருள் பிளவுக்குட்பட்டு, நடுவில் ப்ரோடோட்ரோக் எனும் கசையிழைப் பட்டையும் உச்சியில் குஞ்சமும் உடைய தனி நீந்தும் லார்வாவான ட்ரோகோஃபோராக வளர்கிறது. அது பிளாங்க்டனை உண்கிறது. ட்ரோகோபோர் நீண்டு கண்டங்களையும் சீட்டாக்களையும் பெற்று மெட்டாட்ரோகோஃபோராகவும் பின்னர் அடிமட்டத்தில் குடியேறும் இளம் புழுவாகவும், சில இனங்களில் நெக்டோகீட் எனப்படும் நிலையாகவும் மாறுகிறது. ட்ரோகோபோர் அனெலிடாக்களை, லார்வாக்கள் ஒத்த மெல்லுடலிகளுடன் இணைக்கும் முக்கியப் பண்பு."),
                    bfig("lib/nereis-trochophore",
                        "The trochophore larva of Nereis.",
                        "நெரிஸின் ட்ரோகோஃபோர் இளவுயிரி."),
                    bdef("Prototroch", "ப்ரோடோட்ரோக் (Prototroch)", "The main band of cilia around the middle of a trochophore larva, used in swimming and feeding." to
                        "நீந்துதல், உண்ணுதலுக்குப் பயன்படும் ட்ரோகோபோர் லார்வாவின் நடுவைச் சுற்றியுள்ள முதன்மைக் கசையிழைப் பட்டை."),
                    bdef("Spiral cleavage", "சுருள் பிளவு (Spiral cleavage)", "A pattern of early cell division in which the cells of each tier lie obliquely over those below, typical of annelids and molluscs." to
                        "அனெலிடாக்கள், மெல்லுடலிகளுக்கு வழக்கமான, ஒவ்வொரு அடுக்கின் செல்களும் கீழுள்ளவற்றின் மேல் சரிவாக அமையும் தொடக்கப் பிளவு முறை."),
                )),
            bsec("respiration", "Respiration, blood and body fluid", "சுவாசம், இரத்தம், உடல் திரவம்",
                blocks = listOf(
                    bp("Nereis has no gills or lungs. Gas exchange takes place across the thin, moist surface of the body and especially across the parapodia, whose flattened lobes are richly supplied with blood vessels. The beating of the parapodia and the undulation of the body bring a current of water over the surface, and the worm, which lives in a burrow, ventilates the burrow in this manner. The surface used for gas exchange is therefore increased by the parapodia, and this is one of their several functions, together with locomotion and sensation.",
                        "நீரிஸுக்கு செவுள்களோ நுரையீரலோ இல்லை. வாயுப் பரிமாற்றம் உடலின் மெல்லிய ஈரமான மேற்பரப்பு வழியாகவும், குறிப்பாக இரத்தக் குழாய்கள் நிறைந்த தட்டையான மடல்களைக் கொண்ட பாரபோடியாக்கள் வழியாகவும் நிகழ்கிறது. பாரபோடியாக்களின் அடிப்பும் உடலின் அலைவும் மேற்பரப்பின் மேல் நீரோட்டத்தைக் கொண்டுவருகின்றன; வளையில் வாழும் புழு இவ்வாறு வளையை காற்றோட்டமாக்குகிறது. எனவே வாயுப் பரிமாற்றப் பரப்பு பாரபோடியாக்களால் அதிகரிக்கிறது; இயக்கம், உணர்வுடன் இது அவற்றின் பல பணிகளில் ஒன்று."),
                    bp("The circulation is closed: blood is confined to vessels, and is driven forwards in a dorsal vessel by waves of contraction and backwards in a ventral vessel, with segmental vessels connecting the two and supplying the parapodia, gut and body wall. The blood is red because it contains a respiratory pigment, which is dissolved in the plasma and not carried in blood cells. It is a large molecule related to haemoglobin, and it carries oxygen from the surface of the body to the tissues. The closed circulation is another feature that Nereis shares with the earthworm and that distinguishes the annelids from the open circulation of the arthropods and most molluscs.",
                        "சுற்றோட்டம் மூடியது: இரத்தம் குழாய்களுக்குள் அடங்கியுள்ளது; முதுகுக் குழாயில் சுருக்க அலைகளால் முன்னோக்கியும் வயிற்றுக் குழாயில் பின்னோக்கியும் செலுத்தப்படுகிறது; கண்டக் குழாய்கள் இரண்டையும் இணைத்து பாரபோடியா, குடல், உடற்சுவருக்கு வழங்குகின்றன. இரத்தம் சுவாச நிறமி கொண்டிருப்பதால் சிவப்பாக உள்ளது; அது பிளாஸ்மாவில் கரைந்துள்ளது, இரத்த செல்களில் கொண்டு செல்லப்படுவதில்லை. ஹீமோகுளோபினுடன் தொடர்புடைய பெரிய மூலக்கூறான அது உடலின் மேற்பரப்பிலிருந்து திசுக்களுக்கு ஆக்சிஜனைக் கொண்டு செல்கிறது. மூடிய சுற்றோட்டம் நீரிஸ் மண்புழுவுடன் பகிரும் மற்றொரு பண்பு; ஆர்த்ரோபோடாக்களின், பெரும்பாலான மெல்லுடலிகளின் திறந்த சுற்றோட்டத்திலிருந்து அனெலிடாக்களை வேறுபடுத்துகிறது."),
                    bdef("Closed circulatory system", "மூடிய இரத்தச் சுற்றோட்ட மண்டலம் (Closed circulatory system)", "A circulatory system in which the blood is confined to vessels and does not flow freely among the tissues." to
                        "இரத்தம் குழாய்களுக்குள் அடங்கி, திசுக்களிடையே தடையின்றி ஓடாத சுற்றோட்ட மண்டலம்."),
                    bdef("Dorsal blood vessel", "முதுகு இரத்தக் குழாய் (Dorsal blood vessel)", "The main longitudinal vessel above the gut in an annelid, in which blood is driven forwards." to
                        "அனெலிடாவில் குடலுக்கு மேலுள்ள, இரத்தம் முன்னோக்கிச் செலுத்தப்படும் முதன்மை நீளவாக்குக் குழாய்."),
                )),
            bsec("regeneration", "Sensory behaviour and regeneration", "உணர்வு நடத்தையும் மீளுருவாக்கமும்",
                blocks = listOf(
                    bp("The head of Nereis carries the eyes, palps, tentacles and nuchal organs, and the animal responds to light, touch, chemicals and vibration. It withdraws into its burrow when disturbed, and in the dark it comes out to feed, so that it is largely active at night. The ventral nerve cord, with a ganglion in each segment, co-ordinates the movements of the parapodia in sequence, while the cerebral ganglia, or brain, above the pharynx receive input from the sense organs of the head.",
                        "நீரிஸின் தலை கண்கள், பால்ப்புகள், உணர்கொம்புகள், நுக்கல் உறுப்புகளைக் கொண்டுள்ளது; விலங்கு ஒளி, தொடுதல், வேதிப்பொருட்கள், அதிர்வுக்குப் பதிலளிக்கிறது. தொந்தரவு செய்யும்போது வளைக்குள் பின்வாங்குகிறது; இருட்டில் உண்ண வெளியே வருவதால் பெரும்பாலும் இரவில் செயல்படுகிறது. ஒவ்வொரு கண்டத்திலும் நரம்புத் திரள் கொண்ட வயிற்று நரம்பு வடம் பாரபோடியாக்களின் இயக்கங்களை வரிசையாக ஒருங்கிணைக்கிறது; தொண்டைக்கு மேலுள்ள பெருமூளை நரம்புத் திரள்கள் (மூளை) தலையின் உணர் உறுப்புகளிலிருந்து உள்ளீடு பெறுகின்றன."),
                    bp("Polychaetes regenerate well. A worm that has lost part of its hind end can replace it from the cut surface, because the cells of the stump can grow and divide to form a new pygidium and a growth zone that produces new segments. The loss of the head, however, is not replaced as readily, and the regeneration of the front end varies with the species and with the number of segments that remain. The ability explains why the polychaetes can escape from a predator that has caught the hind end.",
                        "பாலிகீட்டாக்கள் நன்கு மீளுருவாக்குகின்றன. பின் முனையின் ஒரு பகுதியை இழந்த புழு அதை வெட்டுப் பரப்பிலிருந்து மாற்ற முடியும்; ஏனெனில் தூண்டின் செல்கள் வளர்ந்து பிரிந்து புதிய பைஜிடியத்தையும் புதிய கண்டங்களை உருவாக்கும் வளர்ச்சி மண்டலத்தையும் உருவாக்க முடியும். ஆனால் தலை இழப்பு அவ்வளவு எளிதில் ஈடுசெய்யப்படுவதில்லை; முன் முனையின் மீளுருவாக்கம் இனத்தையும் எஞ்சிய கண்டங்களின் எண்ணிக்கையையும் பொறுத்து மாறுபடுகிறது. பின் முனையைப் பிடித்த வேட்டையாடியிடமிருந்து பாலிகீட்டாக்கள் தப்ப முடிவதற்கு இத்திறன் காரணம்."),
                )),
            bsec("ecology", "Nereis in the ecosystem and in use", "சூழல்மண்டலத்திலும் பயன்பாட்டிலும் நீரிஸ்",
                blocks = listOf(
                    bp("Nereid worms are abundant in the soft sediments of estuaries, mangroves and the shore, where they are important in two ways. They are a major food for fishes, crabs and wading birds, so that they pass the energy of the mud to larger animals, and their burrows move oxygen into the sediment, which affects the bacteria and the other animals in it. The group is also of value to people, for polychaetes are collected as bait for fishing and, in aquaculture, are used as food for the broodstock of prawns, which are fed on them to improve maturation.",
                        "நீரிஸ் புழுக்கள் கழிமுகங்கள், சதுப்புநிலக் காடுகள், கடற்கரையின் மென் வண்டலில் மிகுதியாக உள்ளன; அங்கு இரு வகையில் முக்கியமானவை. அவை மீன்கள், நண்டுகள், நீர்ப்பறவைகளுக்கு முக்கிய உணவு; எனவே சேற்றின் ஆற்றலை பெரிய விலங்குகளுக்குக் கடத்துகின்றன; அவற்றின் வளைகள் வண்டலுக்குள் ஆக்சிஜனை நகர்த்தி, அதிலுள்ள பாக்டீரியாக்களையும் பிற விலங்குகளையும் பாதிக்கின்றன. இக்குழு மனிதருக்கும் மதிப்புடையது: பாலிகீட்டாக்கள் மீன்பிடிக்கும் இரையாகச் சேகரிக்கப்படுகின்றன; நீர்வள வளர்ப்பில் இறால் தாய் இருப்புகள் முதிர்ச்சி மேம்பட அவற்றை உண்கின்றன."),
                    bp("The scientific name needs a note. Many of the species that were once placed in the genus Nereis have been moved by taxonomists into other genera, such as Alitta and Neanthes, and the names that are used in books differ. The prescribed text, and not a recent revision, should be followed for the name in the examination, though the characters that are described apply to the family Nereididae as a whole.",
                        "அறிவியல் பெயருக்குக் குறிப்பு தேவை. ஒருகாலத்தில் நீரிஸ் பேரினத்தில் வைக்கப்பட்ட பல இனங்கள் வகைப்பாட்டாளர்களால் அலிட்டா, நியான்தெஸ் போன்ற பிற பேரினங்களுக்கு மாற்றப்பட்டுள்ளன; நூல்களில் பயன்படும் பெயர்கள் வேறுபடுகின்றன. தேர்வில் பெயருக்கு சமீபத்திய திருத்தத்தை அல்ல, பரிந்துரைக்கப்பட்ட நூலையே பின்பற்ற வேண்டும்; எனினும் விவரிக்கப்படும் பண்புகள் நீரிடிடே குடும்பம் முழுமைக்கும் பொருந்தும்."),
                )),
            bsec("answering", "Answering questions on Nereis", "நீரிஸ் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("Questions on Nereis usually take the form of a description of the external features, a note on the parapodium, the structure of the pharynx and its use, the reproduction with epitoky, and the trochophore larva. A labelled diagram of the anterior end, with the prostomium, peristomium, eyes, tentacles, palps and cirri, and a diagram of a parapodium with its lobes, aciculum and setae, are expected. For epitoky the answer should state the changes in the body, the swarming and the death of the worm after spawning, and should identify the advantages in synchronising reproduction.",
                        "நீரிஸ் குறித்த வினாக்கள் பொதுவாக வெளிப்புறப் பண்புகளின் விளக்கம், பாரபோடியம் பற்றிய குறிப்பு, தொண்டையின் அமைப்பும் பயனும், எபிடோகியுடன் இனப்பெருக்கம், ட்ரோகோஃபோர் லார்வா வடிவில் வருகின்றன. புரோஸ்டோமியம், பெரிஸ்டோமியம், கண்கள், உணர்கொம்புகள், பால்ப்புகள், சிர்ரிகளுடன் முன் முனையின் பெயரிட்ட படமும், மடல்கள், அசிகுலம், செட்டாக்களுடன் பாரபோடியத்தின் படமும் எதிர்பார்க்கப்படுகின்றன. எபிடோகிக்கு உடல் மாற்றங்கள், கூட்டமாகச் சேர்தல், முட்டையிட்ட பின் புழுவின் இறப்பு ஆகியவற்றைக் கூறி, இனப்பெருக்கத்தை ஒத்திசைப்பதன் நன்மைகளைக் குறிப்பிட வேண்டும்."),
                    bpoints("Frequent errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Giving Nereis a clitellum or hermaphrodite organs; the sexes are separate." to "நீரிஸுக்கு கிளைட்டெல்லம் அல்லது இருபால் உறுப்புகள் தருதல்; பாலினங்கள் தனித்தனி.",
                        "Describing the parapodium as a jointed limb; it is a soft lobe." to "பாரபோடியத்தை மூட்டுள்ள உறுப்பு என விவரித்தல்; அது மென்மையான மடல்.",
                        "Omitting that the pharynx is everted for feeding and bears jaws." to "உண்ணும்போது தொண்டை வெளித்தள்ளப்படுகிறது, தாடைகளைத் தாங்குகிறது என்பதைத் தவிர்த்தல்.",
                        "Confusing the trochophore with the nauplius of crustaceans." to "ட்ரோகோஃபோரை கிரஸ்டேஷியன்களின் நாப்ளியஸுடன் குழப்புதல்.",
                    ),
                )),
            bsec("epitoky", "Epitoky", "எபிடோகி",
                blocks = listOf(
                    bp("Epitoky is the transformation of a benthic, non-reproductive nereidid, called an atoke, into a specialised swimming reproductive form, the epitoke. The word has been used for the whole change of the body, and the older name for the form, heteronereis, is still met in books. The change takes place as the worm reaches sexual maturity, and its effect is to fit the animal for life in the open water, where it will release its gametes." to
                        "எபிடோகி என்பது அடோக் எனப்படும் அடிமட்ட, இனப்பெருக்கமற்ற நெரிடிட், எபிடோக் எனப்படும் சிறப்பு நீந்தும் இனப்பெருக்க வடிவமாக மாறும் உருமாற்றம். இச்சொல் உடலின் முழு மாற்றத்துக்கும் பயன்படுத்தப்பட்டுள்ளது; வடிவத்தின் பழைய பெயரான ஹெட்டிரோநெரிஸ் நூல்களில் இன்னும் காணப்படுகிறது. புழு பாலின முதிர்ச்சி அடையும்போது மாற்றம் நிகழ்கிறது; அதன் விளைவு திறந்த நீரில், கேமீட்டுகளை விடுவிக்கும் இடத்தில் வாழ்வதற்கு விலங்கைத் தகுதிப்படுத்துவது."),
                    bpoints("What changes in the epitoke", "எபிடோக்கில் மாறுவது என்ன",
                        "The parapodia enlarge and develop flattened natatory lobes, with paddle-like chaetae, for swimming." to "நீந்துவதற்கு பாரபோடியாக்கள் பெரிதாகி, துடுப்பு போன்ற சீட்டாக்களுடன் தட்டையான நீந்தும் மடல்களை உருவாக்குகின்றன.",
                        "The body is differentiated into an anterior non-natatory region and a posterior natatory region, which is filled with gametes." to "உடல் முன்புற நீந்தாப் பகுதியாகவும் கேமீட்டுகளால் நிரம்பிய பின்புற நீந்தும் பகுதியாகவும் வேறுபடுகிறது.",
                        "The eyes commonly enlarge." to "கண்கள் பொதுவாகப் பெரிதாகின்றன.",
                        "Parts of the muscles and of the digestive system may be remodelled as the worm matures." to "புழு முதிரும்போது தசைகளின், செரிமான மண்டலத்தின் பகுதிகள் மறுவடிவமைக்கப்படலாம்.",
                        "Mature epitokes swarm up into the water, and release their gametes for fertilisation in the water." to "முதிர்ந்த எபிடோக்குகள் நீரில் கூட்டமாக மேலெழுந்து, நீரில் கருவுறுதலுக்காக கேமீட்டுகளை விடுவிக்கின்றன.",
                    ),
                    bp("Swarming has the advantage that many individuals release their gametes together, and so the chance of fertilisation is raised, and in many species it is linked to the phase of the moon or to the season. Many nereidids are semelparous, which means that they breed once and die, and in them the epitokous change is followed by death after spawning. This must not be generalised. The degree of the change differs among species and between males and females, and some species survive to breed again." to
                        "கூட்டமாகச் சேர்தலின் நன்மை பல உயிரிகள் ஒன்றாக கேமீட்டுகளை விடுவதால் கருவுறும் வாய்ப்பு உயர்வது; பல இனங்களில் இது நிலவின் நிலையுடன் அல்லது பருவத்துடன் தொடர்புடையது. பல நெரிடிட்கள் செமெல்பேரஸ், அதாவது ஒருமுறை இனப்பெருக்கம் செய்து இறக்கின்றன; அவற்றில் எபிடோகி மாற்றத்தைத் தொடர்ந்து முட்டையிட்ட பின் இறப்பு நிகழ்கிறது. இதைப் பொதுமைப்படுத்தக் கூடாது. மாற்றத்தின் அளவு இனங்களுக்கிடையேயும் ஆண், பெண்ணுக்கிடையேயும் வேறுபடுகிறது; சில இனங்கள் மீண்டும் இனப்பெருக்கம் செய்ய உயிர் பிழைக்கின்றன."),
                    bdef("Epitoky", "எபிடோகி (Epitoky)", "The transformation of a bottom-living nereidid into a swimming reproductive form." to
                        "அடிமட்டத்தில் வாழும் நெரிடிட் நீந்தும் இனப்பெருக்க வடிவமாக மாறும் உருமாற்றம்."),
                    bdef("Semelparous", "ஒருமுறை இனப்பெருக்கம் செய்யும் (Semelparous)", "Breeding once in a lifetime, and dying after reproduction." to
                        "வாழ்நாளில் ஒருமுறை இனப்பெருக்கம் செய்து, அதன் பின் இறக்கும்."),
                    bnote("Epitoky is a transformation, not a new worm", "எபிடோகி ஒரு உருமாற்றம், புதிய புழு அல்ல",
                        "The epitoke is the same individual as the atoke after a change of form, and not a separate generation or species, although old books gave it a separate name." to
                            "பழைய நூல்கள் தனிப்பெயர் தந்தாலும், எபிடோக் என்பது வடிவ மாற்றத்துக்குப் பின் அடோக்கின் அதே உயிரியே; தனி தலைமுறையோ இனமோ அல்ல."),
                )),
        ),
        summary = listOf(
            "Nereis is an errant polychaete with a head bearing tentacles, palps and eyes, a trunk of similar segments and a terminal pygidium." to "நெரிஸ் உணர்கொம்புகள், பால்ப்கள், கண்கள் தாங்கும் தலை, ஒத்த கண்டங்களின் உடற்பகுதி, முனை பைஜிடியம் கொண்ட எரண்ட் பாலிகீட்டா.",
            "The biramous parapodium has a notopodium and a neuropodium, supported by acicula, with chaetae and dorsal and ventral cirri." to "இருகிளைப் பாரபோடியத்தில் அசிகுலங்கள் தாங்கும் நோட்டோபோடியமும் நியூரோபோடியமும், சீட்டாக்கள், முதுகுப்புற, வயிற்றுப்புறத் தண்டுகள் உள்ளன.",
            "Parapodia serve for locomotion, anchorage, gas exchange and sensation." to "பாரபோடியாக்கள் இயக்கம், நங்கூரம், வாயுப் பரிமாற்றம், உணர்தலுக்கு உதவுகின்றன.",
            "The pharynx is eversible and carries jaws; circulation is closed, excretion is by metanephridia, and the nerve cord is double and ventral." to "தொண்டை வெளித்தள்ளக்கூடியது, தாடைகள் உண்டு; இரத்த ஓட்டம் மூடியது; கழிவு நீக்கம் மெட்டாநெஃப்ரிடியாக்களால்; நரம்புவடம் இரட்டை, வயிற்றுப்புறம்.",
            "Sexes are separate, fertilisation is external, and development passes through a trochophore." to "பால் தனி; கருவுறுதல் வெளிப்புறம்; வளர்ச்சி ட்ரோகோபோர் வழியாக.",
            "Epitoky changes a bottom-living worm into a swimming reproductive epitoke, but its extent varies between species and sexes." to "எபிடோகி அடிமட்டப் புழுவை நீந்தும் இனப்பெருக்க எபிடோக்காக மாற்றுகிறது; ஆனால் அதன் அளவு இனங்கள், பாலிடையே மாறுகிறது.",
        ),
        checks = listOf(
            bqc("What is an aciculum?", "அசிகுலம் என்றால் என்ன?",
                "A supporting chitinous rod inside a lobe of the parapodium.", "பாரபோடியத்தின் மடலுக்குள் உள்ள ஆதரவு கைட்டின் தண்டு."),
            bqc("Name the larva of many polychaetes.", "பல பாலிகீட்டாக்களின் லார்வாவைப் பெயரிடுக.",
                "The trochophore.", "ட்ரோகோபோர்."),
            bqc("Name the two lobes of the parapodium of Nereis.", "நெரிஸின் பாரபோடியத்தின் இரு மடல்களைப் பெயரிடுக.",
                "The dorsal notopodium and the ventral neuropodium.", "முதுகுப்புற நோட்டோபோடியம், வயிற்றுப்புற நியூரோபோடியம்."),
            bqc("What is epitoky?", "எபிடோகி என்றால் என்ன?",
                "The transformation of a benthic atoke into a swimming reproductive epitoke.", "அடிமட்ட அடோக் நீந்தும் இனப்பெருக்க எபிடோக்காக மாறும் உருமாற்றம்."),
            bqc("Is every nereidid known to die after spawning?", "ஒவ்வொரு நெரிடிட்டும் முட்டையிட்ட பின் இறக்கிறதா?",
                "No; many are semelparous, but the details differ among species.", "இல்லை; பல ஒருமுறை இனப்பெருக்கம் செய்பவை; ஆனால் விவரங்கள் இனங்களுக்கிடையே மாறுபடுகின்றன."),
        ),
    )
}
