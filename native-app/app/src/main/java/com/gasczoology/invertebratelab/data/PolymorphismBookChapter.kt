package com.gasczoology.invertebratelab.data

/** Book-style chapter: polymorphism in Hydrozoa. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PolymorphismBookChapter {
    val chapter = bchapter(
        lessonId = "u2-polymorphism",
        title = "Polymorphism in Hydrozoa" to "ஹைட்ரோசோவாவில் பல்வடிவத்தன்மை",
        objectives = listOf(
            "Define polymorphism and distinguish it from variation between separate individuals." to "பல்வடிவத்தன்மையை வரையறுத்து, தனி உயிரிகளுக்கிடையேயான வேறுபாட்டிலிருந்து வேறுபடுத்த.",
            "Describe the zooids of a hydrozoan colony and the duties of each." to "ஹைட்ரோசோவா காலனியின் சூயிட்டுகளையும் ஒவ்வொன்றின் பணிகளையும் விவரிக்க.",
            "Explain polymorphism as a division of labour within one genome." to "பல்வடிவத்தன்மையை ஒரே மரபணுத்தொகுதிக்குள் பணிப் பிரிவினையாக விளக்க.",
            "Compare Obelia and Physalia as examples of polymorphism in a table." to "ஒபீலியாவையும் பைசாலியாவையும் பல்வடிவத்தன்மை எடுத்துக்காட்டுகளாக அட்டவணையில் ஒப்பிட.",
            "Explain why the diagrams of the two animals cannot be interchanged." to "இரு விலங்குகளின் வரைபடங்களையும் ஏன் மாற்றிப் பயன்படுத்த இயலாது என்பதை விளக்க.",
        ),
        introduction = listOf(
            "In most animals every individual has the same set of duties, and the whole body is the work of one organism. In a colonial hydrozoan the members of the colony are alike in descent but unlike in form, and they perform different duties. Some feed on behalf of all, some defend, some float, and some reproduce. This is called polymorphism, and it is the clearest example among the invertebrates of the division of labour that makes complicated living things possible." to
                "பெரும்பாலான விலங்குகளில் ஒவ்வொரு உயிரிக்கும் ஒரே பணிகளின் தொகுப்பு உண்டு; முழு உடலும் ஓர் உயிரியின் வேலை. காலனி ஹைட்ரோசோவாவில் காலனியின் உறுப்பினர்கள் தோற்றத்தில் ஒத்தவை, வடிவத்தில் வேறுபட்டவை; வெவ்வேறு பணிகளைச் செய்கின்றன. சில அனைவருக்காகவும் உண்கின்றன, சில தற்காக்கின்றன, சில மிதக்கின்றன, சில இனப்பெருக்கம் செய்கின்றன. இதுவே பல்வடிவத்தன்மை; சிக்கலான உயிரிகளை சாத்தியமாக்கும் பணிப் பிரிவினைக்கு முதுகெலும்பிலிகளில் மிகத் தெளிவான எடுத்துக்காட்டு.",
            "The chapter defines the term, describes the kinds of zooid, sets out two contrasting examples, Obelia and Physalia, and ends with the reasons why the phenomenon matters. The earlier chapter on Obelia should be read first, since the account of its colony is taken as known." to
                "அத்தியாயம் சொல்லை வரையறுக்கிறது, சூயிட் வகைகளை விவரிக்கிறது, ஒபீலியா, பைசாலியா என இரு மாறுபட்ட எடுத்துக்காட்டுகளை முன்வைக்கிறது, நிகழ்வு ஏன் முக்கியம் என்பதற்கான காரணங்களுடன் முடிகிறது. ஒபீலியா பற்றிய முந்தைய அத்தியாயம் முதலில் வாசிக்கப்பட வேண்டும்; அதன் காலனி பற்றிய விளக்கம் அறியப்பட்டதாகக் கொள்ளப்படுகிறது.",
        ),
        sections = listOf(
            bsec("definition", "What polymorphism means", "பல்வடிவத்தன்மை என்பதன் பொருள்",
                blocks = listOf(
                    bp("In colonial hydrozoans, polymorphism is the occurrence of structurally different but genetically related zooids that are specialised for different functions within the same colony or life cycle. The words in this definition each exclude a misreading. The zooids are structurally different, so that mere differences of size are not enough. They are genetically related, because they arise by budding from a single founding individual, and so the differences between them are not due to different genes. And they are specialised, which means that each form serves a purpose for the colony." to
                        "காலனி ஹைட்ரோசோவாக்களில் பல்வடிவத்தன்மை என்பது அதே காலனியில் அல்லது வாழ்க்கைச் சுழற்சியில் வெவ்வேறு பணிகளுக்குச் சிறப்படைந்த, அமைப்பில் வேறுபட்ட ஆனால் மரபுப்படி தொடர்புடைய சூயிட்டுகள் இருத்தல். இவ்வரையறையின் ஒவ்வொரு சொல்லும் ஒரு தவறான புரிதலை விலக்குகிறது. சூயிட்டுகள் அமைப்பில் வேறுபட்டவை; எனவே அளவு வேறுபாடு மட்டும் போதாது. அவை மரபுப்படி தொடர்புடையவை; ஒரே நிறுவன உயிரியிலிருந்து மொட்டுவிடுவதால் தோன்றுவதால், வேறுபாடுகள் வெவ்வேறு மரபணுக்களால் அல்ல. அவை சிறப்படைந்தவை; அதாவது ஒவ்வொரு வடிவமும் காலனிக்கு ஒரு நோக்கத்துக்குப் பயன்படுகிறது."),
                    bdef("Polymorphism", "பல்வடிவத்தன்மை", "The occurrence of structurally different but genetically related zooids, specialised for different functions, in the same colony or life cycle." to
                        "ஒரே காலனி அல்லது வாழ்க்கைச் சுழற்சியில் வெவ்வேறு பணிகளுக்குச் சிறப்படைந்த, அமைப்பில் வேறுபட்ட, மரபுப்படி தொடர்புடைய சூயிட்டுகள் இருத்தல்."),
                    bdef("Division of labour", "பணிப் பிரிவினை", "The sharing out of the functions of life, such as feeding, defence and reproduction, among different parts or members of an organism or colony." to
                        "உணவூட்டம், தற்காப்பு, இனப்பெருக்கம் போன்ற வாழ்க்கைப் பணிகளை உயிரியின் அல்லது காலனியின் வெவ்வேறு பகுதிகள், உறுப்பினர்களிடையே பகிர்ந்தளித்தல்."),
                    bnote("Not different species", "வெவ்வேறு இனங்கள் அல்ல",
                        "The polyp and the medusa of one species, and the zooids of one colony, are not separate species. They are forms of one genome." to
                            "ஓர் இனத்தின் பாலிப்பும் மெடுசாவும், ஒரு காலனியின் சூயிட்டுகளும் தனி இனங்கள் அல்ல; ஒரே மரபணுத்தொகுதியின் வடிவங்கள்."),
                )),
            bsec("zooids", "The kinds of zooid", "சூயிட் வகைகள்",
                blocks = listOf(
                    bp("Three principal kinds of zooid are described in the textbooks. The names of the zooids vary a little from one author to another, and it is the function that matters." to
                        "மூன்று முதன்மை சூயிட் வகைகள் நூல்களில் விவரிக்கப்படுகின்றன. சூயிட்டுகளின் பெயர்கள் ஆசிரியருக்கு ஆசிரியர் சற்று மாறுபடும்; பணியே முக்கியம்."),
                    btable("Kinds of zooid and their functions", "சூயிட் வகைகளும் அவற்றின் பணிகளும்",
                        listOf("Zooid" to "சூயிட்", "Other names" to "பிற பெயர்கள்", "Function" to "பணி"),
                        listOf(
                            listOf("Gastrozooid" to "காஸ்ட்ரோசூயிட்", "Hydranth, trophozooid" to "ஹைட்ராந்த், டிரோஃபோசூயிட்", "Captures and digests food" to "உணவைப் பிடித்துச் செரிக்கிறது"),
                            listOf("Gonozooid" to "கோனோசூயிட்", "Gonangium, gonophore-bearing zooid" to "கோனாஞ்சியம், கோனோஃபோர் தாங்கும் சூயிட்", "Reproduction; produces medusae or gonophores" to "இனப்பெருக்கம்; மெடுசா அல்லது கோனோஃபோர்களை உருவாக்குகிறது"),
                            listOf("Dactylozooid" to "டாக்டிலோசூயிட்", "Tentacular zooid, palpon, protective zooid" to "உணர்கொம்பு சூயிட், பால்போன், பாதுகாப்பு சூயிட்", "Defence and capture of prey with nematocysts" to "நீமட்டோசிஸ்ட்களால் தற்காப்பும் இரை பிடித்தலும்"),
                        )),
                    bp("In some colonies, notably the siphonophores, further kinds are found: a gas-filled float, the pneumatophore, that buoys the colony at the surface, and swimming bells (nectophores) that propel it. Because the terminology depends on the group, in an answer it is wise to name the function as well as the name of the zooid." to
                        "சில காலனிகளில், குறிப்பாக சைஃபோனோஃபோர்களில், மேலும் வகைகள் உண்டு: காலனியை மேற்பரப்பில் மிதக்கச் செய்யும் வாயு நிரம்பிய மிதவையான நியூமட்டோஃபோர்; அதை உந்தும் நீச்சல் மணிகள் (நெக்டோஃபோர்கள்). சொற்கள் குழுவைப் பொறுத்து மாறுவதால், விடையில் சூயிட்டின் பெயருடன் அதன் பணியையும் குறிப்பிடுவது நல்லது."),
                    bdef("Gastrozooid", "காஸ்ட்ரோசூயிட்", "A feeding zooid with a mouth and tentacles." to
                        "வாயும் உணர்கொம்புகளும் உடைய உணவூட்டும் சூயிட்."),
                    bdef("Gonozooid", "கோனோசூயிட்", "A reproductive zooid, which in Obelia is the gonangium that buds off medusae." to
                        "இனப்பெருக்க சூயிட்; ஒபீலியாவில் மெடுசாக்களை மொட்டுவிடும் கோனாஞ்சியம்."),
                    bdef("Dactylozooid", "டாக்டிலோசூயிட்", "A protective or prey-catching zooid armed with nematocysts, usually without a mouth." to
                        "நீமட்டோசிஸ்ட்களால் ஆயுதம் தரித்த, பொதுவாக வாயற்ற, பாதுகாப்பு அல்லது இரை பிடிக்கும் சூயிட்."),
                    bdef("Pneumatophore", "நியூமட்டோஃபோர்", "The gas-filled float of a siphonophore colony." to
                        "சைஃபோனோஃபோர் காலனியின் வாயு நிரம்பிய மிதவை."),
                )),
            bsec("obelia", "Polymorphism in Obelia", "ஒபீலியாவில் பல்வடிவத்தன்மை",
                blocks = listOf(
                    bp("Obelia shows polymorphism in its simplest form. The colony is a sessile, branched hydroid fixed to a surface. Its zooids are of two kinds: the feeding hydranths, which have mouths and tentacles, and the reproductive gonangia, which have neither. The two are joined by the coenosarc, which carries food from the hydranths to the gonangia. The gonangia produce medusae by budding, and these are the sexual, free-swimming stage that forms the gametes. A third form is therefore present in the life cycle, the medusa." to
                        "ஒபீலியா பல்வடிவத்தன்மையை மிக எளிய வடிவில் காட்டுகிறது. காலனி பரப்பில் ஒட்டிய, கிளைத்த நிலையான ஹைட்ராய்டு. சூயிட்டுகள் இரு வகை: வாயும் உணர்கொம்புகளும் உடைய உணவூட்டும் ஹைட்ராந்துகள்; இரண்டும் இல்லாத இனப்பெருக்க கோனாஞ்சியங்கள். இரண்டையும் சீனோசார்க் இணைத்து, ஹைட்ராந்துகளிலிருந்து கோனாஞ்சியங்களுக்கு உணவைக் கொண்டு செல்கிறது. கோனாஞ்சியங்கள் மொட்டுவிடுதலால் மெடுசாக்களை உருவாக்குகின்றன; அவை கேமீட்டுகளை உருவாக்கும் பாலின, சுதந்திரமாக நீந்தும் நிலை. எனவே வாழ்க்கைச் சுழற்சியில் மூன்றாவது வடிவமாக மெடுசா உள்ளது."),
                    bp("The medusa also has a task which the sessile colony cannot do, which is to disperse the species. A colony is fixed, but a medusa can be carried by currents to a new place, and its fertilised egg, as a planula, can found a new colony there. Dispersal is thus one of the duties that are divided between the forms of one life cycle." to
                        "நிலையான காலனியால் செய்ய முடியாத பணியையும் மெடுசா செய்கிறது: இனத்தைப் பரப்புதல். காலனி நிலையானது; ஆனால் மெடுசாவை நீரோட்டங்கள் புதிய இடத்துக்குக் கொண்டு செல்லும்; அதன் கருவுற்ற முட்டை பிளானுலாவாக அங்கு புதிய காலனியை நிறுவ முடியும். எனவே பரவல், ஒரே வாழ்க்கைச் சுழற்சியின் வடிவங்களிடையே பகிரப்படும் பணிகளில் ஒன்று."),
                )),
            bsec("physalia", "Polymorphism in Physalia", "பைசாலியாவில் பல்வடிவத்தன்மை",
                blocks = listOf(
                    bp("Physalia, the Portuguese man-of-war, is a siphonophore, and its colony is entirely different in habit. It is pelagic, which means that it lives in the open sea, and it is not attached to anything. It is held at the surface by a large, gas-filled pneumatophore, which acts as a float and as a sail, while the other zooids hang below it. These include gastrozooids, that feed, very long fishing tentacles, which carry powerful nematocysts and which are regarded as dactylozooids in traditional accounts, and gonozooids, that reproduce. The cnidocytes of the tentacles are dangerous to people." to
                        "பைசாலியா, போர்த்துக்கீசிய போர்க்கப்பல் மீன், ஒரு சைஃபோனோஃபோர்; அதன் காலனி வாழ்முறையில் முற்றிலும் வேறுபட்டது. இது கடல் மேற்பரப்பில் வாழும் (பெலாஜிக்) உயிரி; எதனுடனும் ஒட்டியிருக்கவில்லை. மிதவையாகவும் பாய்மரமாகவும் செயல்படும் பெரிய, வாயு நிரம்பிய நியூமட்டோஃபோரால் மேற்பரப்பில் தாங்கப்படுகிறது; மற்ற சூயிட்டுகள் அதன் கீழ் தொங்குகின்றன. அவற்றில் உண்ணும் காஸ்ட்ரோசூயிட்டுகள், சக்திவாய்ந்த நீமட்டோசிஸ்ட்களைக் கொண்ட, மரபு விளக்கங்களில் டாக்டிலோசூயிட்டுகளாகக் கருதப்படும் மிக நீண்ட மீன்பிடி உணர்கொம்புகள், இனப்பெருக்கம் செய்யும் கோனோசூயிட்டுகள் அடங்கும். உணர்கொம்புகளின் நிடோசைட்டுகள் மனிதர்களுக்கு ஆபத்தானவை."),
                    bp("The differences are not only in the number of forms. In Physalia the zooids are so specialised, and so dependent on one another, that they cannot live apart, and the colony behaves like a single animal. The reproductive parts, too, are highly modified gonophores that remain in the colony and are not typical free medusae. For this reason Physalia is often used as the extreme case of polymorphism, and Obelia as the simple one." to
                        "வேறுபாடு வடிவங்களின் எண்ணிக்கையில் மட்டுமல்ல. பைசாலியாவில் சூயிட்டுகள் மிகவும் சிறப்படைந்து ஒன்றையொன்று சார்ந்திருப்பதால் தனித்து வாழ முடியாது; காலனி ஒற்றை விலங்கைப் போல் செயல்படுகிறது. இனப்பெருக்கப் பகுதிகளும் காலனியுள் தங்கும், வழக்கமான தனிநீந்தும் மெடுசா அல்லாத, அதிகம் மாற்றமடைந்த கோனோஃபோர்கள். இதனால் பைசாலியா பல்வடிவத்தன்மையின் தீவிர நிலைக்கும், ஒபீலியா எளிய நிலைக்கும் எடுத்துக்காட்டாகப் பயன்படுகின்றன."),
                )),
            bsec("origin", "How the forms arise", "வடிவங்கள் எவ்வாறு தோன்றுகின்றன",
                blocks = listOf(
                    bp("Every zooid of a colony is produced by budding, and the form that a bud takes depends on the position in which it arises and on the signals that it receives. The colony begins from a single planula. This settles, or in a siphonophore develops, and by budding gives rise to the first zooids. The later buds then become hydranths, gonangia or other forms according to a pattern that is repeated in each colony of the species. Because all the buds are produced from the same cells, the same genes are present in all of them, and the difference between the forms is a result of which genes are active in each." to
                        "காலனியின் ஒவ்வொரு சூயிட்டும் மொட்டுவிடுதலால் உருவாகிறது; மொட்டு எடுக்கும் வடிவம் அது தோன்றும் இடத்தையும் அது பெறும் சமிக்ஞைகளையும் சார்ந்தது. காலனி ஒரே பிளானுலாவிலிருந்து தொடங்குகிறது. இது படிகிறது, அல்லது சைஃபோனோஃபோரில் வளர்கிறது; மொட்டுவிடுதலால் முதல் சூயிட்டுகளை உருவாக்குகிறது. பின்வரும் மொட்டுகள் இனத்தின் ஒவ்வொரு காலனியிலும் திரும்பும் வடிவத்தின்படி ஹைட்ராந்துகளாகவோ கோனாஞ்சியங்களாகவோ பிற வடிவங்களாகவோ ஆகின்றன. எல்லா மொட்டுகளும் ஒரே செல்களிலிருந்து உருவாவதால், அனைத்திலும் ஒரே மரபணுக்கள் உள்ளன; வடிவங்களுக்கிடையேயான வேறுபாடு ஒவ்வொன்றிலும் எந்த மரபணுக்கள் செயல்படுகின்றன என்பதன் விளைவு."),
                    bp("Two kinds of zooid are distinguished by their ancestry. The polypoid zooids, the hydranths and dactylozooids, are modifications of the polyp. The medusoid zooids, such as the swimming bells of siphonophores and the gonophores that bear gametes, are modifications of the medusa. A colony may therefore be thought of as built from the two basic body forms of the phylum, modified in different degrees, and the gonophore of Physalia can be seen as a medusa which has been reduced and no longer swims." to
                        "இரு வகை சூயிட்டுகள் தோற்றத்தால் வேறுபடுகின்றன. ஹைட்ராந்துகள், டாக்டிலோசூயிட்டுகள் போன்ற பாலிப்பாய்டு சூயிட்டுகள் பாலிப்பின் மாற்றங்கள். சைஃபோனோஃபோர்களின் நீச்சல் மணிகள், கேமீட்டுகளைத் தாங்கும் கோனோஃபோர்கள் போன்ற மெடுசாய்டு சூயிட்டுகள் மெடுசாவின் மாற்றங்கள். எனவே காலனியைத் தொகுதியின் இரு அடிப்படை உடல் வடிவங்களிலிருந்து வெவ்வேறு அளவுகளில் மாற்றப்பட்டுக் கட்டப்பட்டதாகக் கருதலாம்; பைசாலியாவின் கோனோஃபோரை சுருங்கி இனி நீந்தாத மெடுசாவாகக் காணலாம்."),
                    bdef("Polypoid zooid", "பாலிப்பாய்டு சூயிட்", "A zooid that is a modification of the polyp form, such as a hydranth or a dactylozooid." to
                        "ஹைட்ராந்த் அல்லது டாக்டிலோசூயிட் போன்ற, பாலிப் வடிவத்தின் மாற்றமான சூயிட்."),
                    bdef("Medusoid zooid", "மெடுசாய்டு சூயிட்", "A zooid that is a modification of the medusa form, such as a swimming bell or a gonophore." to
                        "நீச்சல் மணி அல்லது கோனோஃபோர் போன்ற, மெடுசா வடிவத்தின் மாற்றமான சூயிட்."),
                    bdef("Gonophore", "கோனோஃபோர்", "A reproductive structure that bears the gametes; it may be a free medusa or a reduced medusoid that stays attached to the colony." to
                        "கேமீட்டுகளைத் தாங்கும் இனப்பெருக்க அமைப்பு; தனி மெடுசாவாகவோ காலனியுடன் ஒட்டியிருக்கும் சுருங்கிய மெடுசாய்டாகவோ இருக்கலாம்."),
                    bdef("Siphonophore", "சைஃபோனோஃபோர்", "A hydrozoan that forms a floating or swimming colony of highly specialised zooids, such as Physalia." to
                        "பைசாலியா போன்ற, மிகவும் சிறப்படைந்த சூயிட்டுகளின் மிதக்கும் அல்லது நீந்தும் காலனியை உருவாக்கும் ஹைட்ரோசோவா."),
                )),
            bsec("intermediate", "Other polymorphic hydroids", "பல்வடிவத்தன்மை உடைய பிற ஹைட்ராய்டுகள்",
                blocks = listOf(
                    bp("Between the simple case of Obelia and the extreme case of Physalia there are many intermediate colonies. Hydractinia, which forms a crust on the shells that hermit crabs live in, has feeding, reproductive and defensive zooids, and so has three kinds in a colony which creeps over the shell. Velella, the by-the-wind sailor, is a floating hydrozoan with a flat float and a sail. Hydra, by contrast, has no polymorphism at all, since it is a single polyp that does everything itself." to
                        "ஒபீலியாவின் எளிய நிலைக்கும் பைசாலியாவின் தீவிர நிலைக்கும் இடையே பல இடைநிலை காலனிகள் உள்ளன. துறவி நண்டுகள் வாழும் ஓடுகளின் மேல் மேலோடு போல் உருவாகும் ஹைட்ராக்டினியாவில் உணவூட்டும், இனப்பெருக்க, தற்காப்பு சூயிட்டுகள் உள்ளன; ஓட்டின் மேல் ஊரும் காலனியில் மூன்று வகை உண்டு. காற்றுடன் செல்லும் பாய்மரக்காரனான வெலெல்லா தட்டை மிதவையும் பாய்மரமும் கொண்ட மிதக்கும் ஹைட்ரோசோவா. இதற்கு மாறாக ஹைட்ராவுக்கு பல்வடிவத்தன்மை இல்லை; எல்லாவற்றையும் தானே செய்யும் ஒற்றைப் பாலிப்."),
                    bp("The series shows that polymorphism has arisen more than once, and that it can be increased step by step, each stage giving a colony that works a little better as a whole. It is for this reason that the hydrozoans are used to illustrate how an association of individuals can turn, by degrees, into an individual." to
                        "பல்வடிவத்தன்மை ஒன்றுக்கு மேற்பட்ட முறை தோன்றியுள்ளதையும், ஒவ்வொரு நிலையிலும் காலனி முழுமையாக சற்று நன்றாகச் செயல்படும்படி படிப்படியாக அதிகரிக்க முடியும் என்பதையும் இத்தொடர் காட்டுகிறது. இதனால்தான் தனி உயிரிகளின் சங்கம் படிப்படியாக ஒரு உயிரியாக மாறுவதை விளக்க ஹைட்ரோசோவாக்கள் பயன்படுகின்றன."),
                )),
            bsec("siphonophores", "Inside a siphonophore colony", "சைஃபோனோஃபோர் காலனியின் உள்ளே",
                blocks = listOf(
                    bp("The siphonophores are the most elaborately polymorphic of all animals, and the student should know that Physalia is only one of several designs. In many siphonophores that swim, such as Diphyes and Nanomia, a stem bears one or more swimming bells, called nectophores, which contract to push water out and drive the colony along. Below the bells the stem carries repeated groups of zooids, each group containing a feeding gastrozooid with a single long tentacle, reproductive gonophores and, in many species, protective bracts. Physalia has no swimming bells. It has given up swimming and drifts on the wind and the sea surface with its float acting as a sail.",
                        "சைஃபோனோஃபோர்கள் எல்லா விலங்குகளிலும் மிகச் சிக்கலான பல்வடிவத்தன்மை கொண்டவை; பைசாலியா பல வடிவமைப்புகளில் ஒன்று மட்டுமே என்பதை மாணவர் அறிய வேண்டும். டைஃபியஸ், நானோமியா போன்ற நீந்தும் சைஃபோனோஃபோர்களில் தண்டு ஒன்று அல்லது பல நீந்தும் மணிகளை (நெக்டோஃபோர்) தாங்குகிறது; அவை சுருங்கி நீரைத் தள்ளி காலனியை முன்செலுத்துகின்றன. மணிகளுக்குக் கீழ் தண்டு சூயிட்டுகளின் திரும்பத் திரும்ப வரும் தொகுப்புகளைக் கொண்டுள்ளது; ஒவ்வொரு தொகுப்பிலும் ஒற்றை நீண்ட உணர்கொம்புடன் உணவூட்டும் காஸ்ட்ரோசூயிட், இனப்பெருக்கக் கோனோஃபோர்கள், பல இனங்களில் காக்கும் பிராக்ட்டுகள் உள்ளன. பைசாலியாவுக்கு நீந்தும் மணிகள் இல்லை; நீந்துவதை விட்டு, மிதவையை பாயாகக் கொண்டு காற்றாலும் கடல் மேற்பரப்பாலும் அலைகிறது."),
                    bp("This variety allows a useful generalisation. The same small set of zooid types is combined in different ways to give a floating colony, a swimming colony or a colony in which the float is the main structure. Evolution seems to have worked by changing the number, position and timing of the buds, and not by inventing new kinds of part. An examiner will not require the names of the zooid groups of the swimming siphonophores, but the nectophore is worth knowing for the contrast with the float of Physalia.",
                        "இவ்வேறுபாடு பயனுள்ள பொதுமைப்படுத்தலை அனுமதிக்கிறது: அதே சிறு சூயிட் வகைகள் வெவ்வேறு விதமாகச் சேர்ந்து மிதக்கும் காலனி, நீந்தும் காலனி அல்லது மிதவையே முதன்மை அமைப்பான காலனியை உருவாக்குகின்றன. புதிய உறுப்பு வகைகளைக் கண்டுபிடிப்பதால் அல்ல, மொட்டுகளின் எண்ணிக்கை, இடம், நேரத்தை மாற்றுவதால் பரிணாமம் செயல்பட்டதாகத் தெரிகிறது. நீந்தும் சைஃபோனோஃபோர்களின் சூயிட் தொகுப்புகளின் பெயர்களை தேர்வாளர் கோரமாட்டார்; ஆனால் பைசாலியாவின் மிதவையுடன் வேறுபடுத்த நெக்டோஃபோரை அறிவது நல்லது."),
                    bdef("Nectophore", "நெக்டோஃபோர்", "A medusoid swimming bell of a siphonophore colony, which contracts to drive water out and propel the colony." to
                        "சைஃபோனோஃபோர் காலனியின் மெடுசாய்டு நீந்தும் மணி; நீரைத் தள்ளி காலனியை முன்செலுத்தச் சுருங்குகிறது."),
                    bdef("Bract", "பிராக்ட் (காப்புத் தகடு)", "A leaf-like protective zooid of some siphonophores, which covers and shields the feeding and reproductive zooids." to
                        "சில சைஃபோனோஃபோர்களின் இலை போன்ற காக்கும் சூயிட்; உணவூட்டும், இனப்பெருக்க சூயிட்டுகளை மூடிக் காக்கிறது."),
                )),
            bsec("lifecycle", "Polymorphism and the life cycle", "பல்வடிவத்தன்மையும் வாழ்க்கைச் சுழற்சியும்",
                blocks = listOf(
                    bp("Two ideas are often confused by students and should be kept distinct. Polymorphism is the occurrence of several kinds of zooid at the same time in one colony. The alternation of a polyp stage with a medusa stage in the life cycle, as in Obelia, is a succession in time. In books this alternation is called metagenesis, and it is distinct from the alternation of generations of plants, since both the polyp and the medusa are diploid and the medusa arises by budding and not by meiosis. The polyp reproduces asexually and the medusa sexually.",
                        "மாணவர்கள் அடிக்கடி குழப்பும் இரு கருத்துகளைத் தனித்தனியாக வைக்க வேண்டும். பல்வடிவத்தன்மை என்பது ஒரே நேரத்தில் ஒரு காலனியில் பல சூயிட் வகைகள் இருப்பது. ஒபீலியாவில் போல வாழ்க்கைச் சுழற்சியில் பாலிப் நிலையும் மெடுசா நிலையும் மாறி வருவது காலத்தில் ஏற்படும் தொடர்ச்சி. நூல்களில் இம்மாற்றம் மெட்டாஜெனிசிஸ் எனப்படுகிறது; தாவரங்களின் தலைமுறை மாற்றத்திலிருந்து இது வேறுபட்டது; ஏனெனில் பாலிப்பும் மெடுசாவும் இரண்டுமே இரட்டை மடிய நிலையிலுள்ளன; மெடுசா ஒடுக்கற்பிரிவால் அல்ல, மொட்டுவிடுதலால் தோன்றுகிறது. பாலிப் பாலிலா முறையிலும் மெடுசா பால் முறையிலும் இனப்பெருக்குகின்றன."),
                    bp("The two ideas are linked, however. The medusa is a zooid-like stage that has been set aside for reproduction and dispersal, and in a siphonophore the medusa has been so reduced and incorporated into the colony that it never leaves it. The path from Obelia, in which the medusa is free, through Hydractinia, in which the medusa may be kept on the colony, to Physalia, where the gonophore is permanently attached, is the main evidence that the medusoid zooids of a siphonophore are the same kind of structure as the free medusa of Obelia.",
                        "எனினும் இவ்விரு கருத்துகளும் தொடர்புடையவை. மெடுசா என்பது இனப்பெருக்கத்துக்கும் பரவலுக்கும் ஒதுக்கப்பட்ட சூயிட் போன்ற நிலை; சைஃபோனோஃபோரில் மெடுசா காலனியுடன் இணைந்து சுருங்கி, அதை விட்டு வெளியேறுவதே இல்லை. மெடுசா தனியாக வெளியேறும் ஒபீலியாவிலிருந்து, காலனியில் மெடுசா தங்கக்கூடிய ஹைட்ராக்டினியா வழியாக, கோனோஃபோர் நிரந்தரமாக ஒட்டியுள்ள பைசாலியா வரையிலான பாதை, சைஃபோனோஃபோரின் மெடுசாய்டு சூயிட்டுகள் ஒபீலியாவின் தனி மெடுசாவுக்கு இணையான அமைப்பே என்பதற்கான முக்கிய சான்று."),
                    bdef("Metagenesis", "மெட்டாஜெனிசிஸ்", "The alternation in a life cycle of an asexual polyp stage and a sexual medusa stage, as in Obelia." to
                        "ஒபீலியா போல வாழ்க்கைச் சுழற்சியில் பாலிலா பாலிப் நிலையும் பால் மெடுசா நிலையும் மாறி வருதல்."),
                )),
            bsec("anthozoa", "Polymorphism outside the Hydrozoa", "ஹைட்ரோசோவாவுக்கு வெளியே பல்வடிவத்தன்மை",
                blocks = listOf(
                    bp("Polymorphism is not confined to hydrozoans. In the sea pens (Pennatula and its relatives), colonies of the octocorals, a large primary polyp forms a stalk and a feather-like shape, and the many secondary polyps on the side branches are of two kinds. The autozooids have tentacles and feed, and the smaller siphonozooids lack tentacles and drive water through the colony. Sea pens therefore show that polymorphism has evolved in the Anthozoa independently of the Hydrozoa.",
                        "பல்வடிவத்தன்மை ஹைட்ரோசோவாக்களுக்கு மட்டும் உரியதல்ல. ஆக்டோகோரல் காலனிகளான கடல் பேனாக்களில் (பென்னேட்டுலா மற்றும் அதன் உறவினர்) பெரிய முதன்மை பாலிப் தண்டையும் இறகு போன்ற வடிவத்தையும் உருவாக்குகிறது; பக்கக் கிளைகளிலுள்ள பல இரண்டாம் பாலிப்புகள் இரு வகைப்படும். ஆட்டோசூயிட்டுகள் உணர்கொம்புகளுடன் உண்கின்றன; சிறிய சைஃபோனோசூயிட்டுகளுக்கு உணர்கொம்புகள் இல்லை; அவை காலனி வழியாக நீரைச் செலுத்துகின்றன. எனவே ஹைட்ரோசோவாவிலிருந்து சார்பின்றி ஆந்தோசோவாவிலும் பல்வடிவத்தன்மை தோன்றியுள்ளது என்பதை கடல் பேனாக்கள் காட்டுகின்றன."),
                    bdef("Autozooid", "ஆட்டோசூயிட்", "In an octocoral colony, a polyp with tentacles that feeds and takes part in reproduction." to
                        "ஆக்டோகோரல் காலனியில், உணர்கொம்புகள் கொண்டு உண்டு இனப்பெருக்கத்தில் பங்கேற்கும் பாலிப்."),
                    bdef("Siphonozooid", "சைஃபோனோசூயிட்", "In an octocoral colony, a small polyp without tentacles that drives water through the canals of the colony." to
                        "ஆக்டோகோரல் காலனியில், உணர்கொம்புகள் இல்லாத, காலனியின் கால்வாய்கள் வழியாக நீரைச் செலுத்தும் சிறு பாலிப்."),
                )),
            bsec("comparison", "Obelia compared with Physalia", "ஒபீலியாவும் பைசாலியாவும் ஒப்பீடு",
                blocks = listOf(
                    btable("Polymorphism in Obelia and Physalia", "ஒபீலியா, பைசாலியாவில் பல்வடிவத்தன்மை",
                        listOf("Feature" to "பண்பு", "Obelia" to "ஒபீலியா", "Physalia" to "பைசாலியா"),
                        listOf(
                            listOf("Type of colony" to "காலனி வகை", "Sessile, branched hydroid" to "நிலையான, கிளைத்த ஹைட்ராய்டு", "Pelagic, floating siphonophore" to "கடல்மேல் மிதக்கும் சைஃபோனோஃபோர்"),
                            listOf("Attachment" to "ஒட்டுதல்", "Fixed to a substratum" to "அடித்தளத்தில் ஒட்டியது", "Free, held at the surface by a float" to "தனிந்தது, மிதவையால் மேற்பரப்பில் தாங்கப்படுவது"),
                            listOf("Float" to "மிதவை", "Absent" to "இல்லை", "Gas-filled pneumatophore" to "வாயு நிரம்பிய நியூமட்டோஃபோர்"),
                            listOf("Feeding zooids" to "உணவூட்டும் சூயிட்டுகள்", "Hydranths" to "ஹைட்ராந்துகள்", "Gastrozooids" to "காஸ்ட்ரோசூயிட்டுகள்"),
                            listOf("Defence and capture" to "தற்காப்பும் இரை பிடித்தலும்", "Tentacles of the hydranths" to "ஹைட்ராந்துகளின் உணர்கொம்புகள்", "Long fishing tentacles (dactylozooids)" to "நீண்ட மீன்பிடி உணர்கொம்புகள் (டாக்டிலோசூயிட்டுகள்)"),
                            listOf("Reproductive zooids" to "இனப்பெருக்க சூயிட்டுகள்", "Gonangia, which bud off free medusae" to "கோனாஞ்சியங்கள்; தனி மெடுசாக்களை மொட்டுவிடுகின்றன", "Gonozooids with modified gonophores" to "மாற்றமடைந்த கோனோஃபோர்களுடன் கோனோசூயிட்டுகள்"),
                            listOf("Degree of specialisation" to "சிறப்படைதலின் அளவு", "Simple; two kinds of zooid and a medusa" to "எளிது; இரு சூயிட் வகைகளும் மெடுசாவும்", "Elaborate; zooids depend on one another" to "சிக்கலானது; சூயிட்டுகள் ஒன்றையொன்று சார்ந்தவை"),
                        )),
                    bnote("The diagrams are not interchangeable", "வரைபடங்கள் மாற்றத்தக்கவை அல்ல",
                        "Draw a pneumatophore only in Physalia. Draw a perisarc, hydrothecae and gonangia with blastostyle only in Obelia. A diagram that gives Obelia a float, or Physalia a fixed stem, is marked as wrong." to
                            "நியூமட்டோஃபோரை பைசாலியாவில் மட்டுமே வரைக. பெரிசார்க், ஹைட்ரோதீகா, பிளாஸ்டோஸ்டைலுடன் கோனாஞ்சியத்தை ஒபீலியாவில் மட்டுமே வரைக. ஒபீலியாவுக்கு மிதவையையோ பைசாலியாவுக்கு நிலையான தண்டையோ தரும் வரைபடம் தவறாக மதிப்பிடப்படும்."),
                )),
            bsec("answering", "Answering questions on polymorphism", "பல்வடிவத்தன்மை குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("An essay on polymorphism in Hydrozoa has a natural plan. It opens with the definition, and then names the zooids with their functions, using Obelia as the first example, and Physalia as the second, more elaborate one. It then explains that the result is a division of labour, and closes by saying that the zooids are genetically related and are not separate species. A table of the two animals, like the one in this chapter, saves time and shows at a glance that the writer knows the difference." to
                        "ஹைட்ரோசோவாவில் பல்வடிவத்தன்மை குறித்த கட்டுரைக்கு இயல்பான திட்டம் உண்டு. வரையறையுடன் தொடங்கி, சூயிட்டுகளை அவற்றின் பணிகளுடன் பெயரிட்டு, முதல் எடுத்துக்காட்டாக ஒபீலியாவையும் இரண்டாவது, சிக்கலான எடுத்துக்காட்டாக பைசாலியாவையும் பயன்படுத்த வேண்டும். பின்னர் விளைவு பணிப் பிரிவினை என விளக்கி, சூயிட்டுகள் மரபுப்படி தொடர்புடையவை, தனி இனங்கள் அல்ல எனக் கூறி முடிக்க வேண்டும். இவ்வத்தியாயத்திலுள்ளது போன்ற இரு விலங்குகளின் அட்டவணை நேரத்தை மிச்சப்படுத்தி, எழுதுபவர் வேறுபாட்டை அறிந்துள்ளார் என்பதை ஒரு பார்வையில் காட்டும்."),
                    bpoints("Common errors to avoid", "தவிர்க்க வேண்டிய பொதுவான பிழைகள்",
                        "Giving Obelia a pneumatophore, or Physalia a perisarc-covered fixed stem." to "ஒபீலியாவுக்கு நியூமட்டோஃபோரையோ பைசாலியாவுக்கு பெரிசார்க் மூடிய நிலையான தண்டையோ தருதல்.",
                        "Saying that the polyp and medusa of Obelia are two species." to "ஒபீலியாவின் பாலிப்பும் மெடுசாவும் இரு இனங்கள் எனக் கூறுதல்.",
                        "Saying that the zooids differ because they have different genes." to "சூயிட்டுகள் வெவ்வேறு மரபணுக்கள் கொண்டிருப்பதால் வேறுபடுகின்றன எனக் கூறுதல்.",
                        "Describing the gonangium of Obelia as feeding." to "ஒபீலியாவின் கோனாஞ்சியம் உண்கிறது என விவரித்தல்.",
                        "Naming zooids without saying what they do." to "சூயிட்டுகளின் பணியைக் கூறாமல் பெயரை மட்டும் குறிப்பிடுதல்.",
                    ),
                )),
            bsec("significance", "Why polymorphism matters", "பல்வடிவத்தன்மை ஏன் முக்கியம்",
                blocks = listOf(
                    bp("Polymorphism is of interest for three reasons. It shows that specialisation can be achieved without separate organs, since the colony divides its labour among whole zooids. It shows how a group of individuals may come to behave like one organism, a process which has a bearing on the origin of larger animals from colonies. And it shows that two forms can be present in a single life cycle, so that the sessile polyp feeds and multiplies while the free medusa reproduces sexually and spreads the species." to
                        "பல்வடிவத்தன்மை மூன்று காரணங்களுக்காக ஆர்வமூட்டுகிறது. தனி உறுப்புகள் இன்றி சிறப்படைதலை அடைய முடியும் என்பதைக் காட்டுகிறது; காலனி தன் பணிகளை முழு சூயிட்டுகளிடையே பகிர்கிறது. உயிரிகளின் குழு ஒரே உயிரியைப்போல் நடந்துகொள்ளும் விதத்தையும் காட்டுகிறது; இது காலனிகளிலிருந்து பெரிய விலங்குகள் தோன்றியதுடன் தொடர்புடையது. ஒரே வாழ்க்கைச் சுழற்சியில் இரு வடிவங்கள் இருக்க முடியும் என்பதையும் காட்டுகிறது; நிலையான பாலிப் உண்டு பெருகும்போது தனிந்த மெடுசா பாலின இனப்பெருக்கம் செய்து இனத்தைப் பரப்புகிறது."),
                    bp("There is also a practical lesson for the examination candidate. Since the forms of a colony are connected through the coenosarc, a hydranth that has caught prey is feeding not only itself but the gonangia and the buds, and a zooid that cannot feed is nevertheless fully supplied. The ability of the colony to share its food is therefore the condition on which every other specialisation rests, and a good answer says so, giving the coenosarc and its common gastrovascular cavity as the reason that food reaches every zooid of the colony." to
                        "தேர்வெழுதுபவருக்கு ஒரு நடைமுறைப் பாடமும் உண்டு. காலனியின் வடிவங்கள் சீனோசார்க் வழியாக இணைந்துள்ளதால், இரை பிடித்த ஹைட்ராந்த் தன்னை மட்டுமல்ல, கோனாஞ்சியங்களையும் மொட்டுகளையும் ஊட்டுகிறது; உண்ண இயலாத சூயிட் முழுமையாக ஊட்டப்படுகிறது. எனவே காலனி தன் உணவைப் பகிரும் திறனே மற்ற அனைத்து சிறப்படைதல்களும் சார்ந்துள்ள நிபந்தனை; நல்ல விடை இதைக் கூறும்."),
                    bp("The same principle appears in other groups, in the castes of social insects, for example, in which the individuals differ in form and duty although they are the members of one family. The hydrozoans show it among animals that have no brain and no organs." to
                        "அதே கொள்கை பிற குழுக்களிலும் தோன்றுகிறது; எடுத்துக்காட்டாக சமூகப் பூச்சிகளின் சாதிகளில், ஒரே குடும்பத்தின் உறுப்பினர்களாக இருந்தும் உயிரிகள் வடிவத்திலும் பணியிலும் வேறுபடுகின்றன. மூளையும் உறுப்புகளும் இல்லாத விலங்குகளிடையே ஹைட்ரோசோவாக்கள் அதைக் காட்டுகின்றன."),
                )),
        ),
        summary = listOf(
            "Polymorphism is the occurrence of structurally different but genetically related zooids, specialised for different functions, in one colony or life cycle." to "பல்வடிவத்தன்மை என்பது ஒரே காலனி அல்லது சுழற்சியில் வெவ்வேறு பணிகளுக்குச் சிறப்படைந்த, அமைப்பில் வேறுபட்ட, மரபுப்படி தொடர்புடைய சூயிட்டுகள் இருத்தல்.",
            "The principal zooids are the gastrozooid (feeding), the gonozooid (reproduction) and the dactylozooid (defence and capture)." to "முதன்மை சூயிட்டுகள் காஸ்ட்ரோசூயிட் (உணவூட்டம்), கோனோசூயிட் (இனப்பெருக்கம்), டாக்டிலோசூயிட் (தற்காப்பு, இரை பிடித்தல்).",
            "The result is a division of labour within one genome, and the forms are not separate species." to "விளைவு ஒரே மரபணுத்தொகுதிக்குள் பணிப் பிரிவினை; வடிவங்கள் தனி இனங்கள் அல்ல.",
            "Obelia is a sessile hydroid with hydranths, gonangia and medusae; its polymorphism is simple." to "ஒபீலியா ஹைட்ராந்துகள், கோனாஞ்சியங்கள், மெடுசாக்களுடன் கூடிய நிலையான ஹைட்ராய்டு; அதன் பல்வடிவத்தன்மை எளிது.",
            "Physalia is a floating siphonophore with a pneumatophore, gastrozooids, fishing tentacles and gonozooids; its polymorphism is elaborate." to "பைசாலியா நியூமட்டோஃபோர், காஸ்ட்ரோசூயிட்டுகள், மீன்பிடி உணர்கொம்புகள், கோனோசூயிட்டுகளுடன் கூடிய மிதக்கும் சைஃபோனோஃபோர்; அதன் பல்வடிவத்தன்மை சிக்கலானது.",
            "The diagrams and zooid labels of the two animals are not interchangeable." to "இரு விலங்குகளின் வரைபடங்களும் சூயிட் குறிப்புகளும் மாற்றத்தக்கவை அல்ல.",
        ),
        checks = listOf(
            bqc("Define polymorphism in Hydrozoa.", "ஹைட்ரோசோவாவில் பல்வடிவத்தன்மையை வரையறுக்க.",
                "The occurrence in one colony of structurally different, genetically related zooids specialised for different functions.", "ஒரே காலனியில் வெவ்வேறு பணிகளுக்குச் சிறப்படைந்த, அமைப்பில் வேறுபட்ட, மரபுப்படி தொடர்புடைய சூயிட்டுகள் இருத்தல்."),
            bqc("Name the three principal zooids and their functions.", "மூன்று முதன்மை சூயிட்டுகளையும் பணிகளையும் குறிப்பிடுக.",
                "Gastrozooid, which feeds; gonozooid, which reproduces; dactylozooid, which defends and captures prey.", "காஸ்ட்ரோசூயிட் உண்கிறது; கோனோசூயிட் இனப்பெருக்கம் செய்கிறது; டாக்டிலோசூயிட் தற்காத்து இரை பிடிக்கிறது."),
            bqc("Which of Obelia and Physalia has a pneumatophore?", "ஒபீலியா, பைசாலியாவில் நியூமட்டோஃபோர் எதற்கு உண்டு?",
                "Physalia only.", "பைசாலியாவுக்கு மட்டும்."),
            bqc("Why are the zooids of a colony not separate species?", "காலனியின் சூயிட்டுகள் ஏன் தனி இனங்கள் அல்ல?",
                "They arise by budding from one individual and so share one genome; their differences arise in development.", "அவை ஒரே உயிரியிலிருந்து மொட்டுவிடுவதால் தோன்றி ஒரே மரபணுத்தொகுதியைப் பகிர்கின்றன; வேறுபாடுகள் வளர்ச்சியில் எழுகின்றன."),
        ),
    )
}
