package com.gasczoology.invertebratelab.data

/** Book-style chapter: introduction to the invertebrates, taxonomic hierarchy. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object IntroInvertebrataBookChapter {
    val chapter = bchapter(
        lessonId = "u1-intro",
        title = "Introduction to the Invertebrata" to "முதுகெலும்பிலிகள் அறிமுகம்",
        objectives = listOf(
            "Describe the taxonomic hierarchy and the ranks in order." to "வகைப்பாட்டு படிநிலையையும் தரங்களையும் வரிசையாக விவரிக்க.",
            "Explain the binomial system and the role of type specimens." to "இருசொல் பெயரிடல் முறையையும் வகை மாதிரிகளின் பங்கையும் விளக்க.",
            "Explain why Invertebrata is not a single phylum." to "முதுகெலும்பிலிகள் ஒற்றைத் தொகுதி அல்ல என ஏன் கூறப்படுகிறது என விளக்க.",
            "Compare the major phyla by level of organisation, symmetry and body cavity." to "முக்கியத் தொகுதிகளை அமைப்பு நிலை, சமச்சீர், உடற்குழி அடிப்படையில் ஒப்பிட.",
            "Distinguish a classification framework from a statement of ancestry." to "வகைப்பாட்டுக் கட்டமைப்பை மூதாதைக் கூற்றிலிருந்து வேறுபடுத்த.",
        ),
        introduction = listOf(
            "The invertebrates are the great majority of animal species, and by any count they outnumber the vertebrates by many times. They live in the sea, in fresh water, on land and in the air, and they have colonised the bodies of other animals as parasites. A course on the invertebrates is therefore a course on most of animal life, and it needs a method for keeping order among a very large number of forms. That method is classification, and this chapter explains the way in which animals are named and arranged." to
                "முதுகெலும்பிலிகள் விலங்கு இனங்களில் பெரும்பான்மை; எந்தக் கணக்கீட்டிலும் அவை முதுகெலும்பிகளை பல மடங்கு மிஞ்சுகின்றன. அவை கடலிலும், நன்னீரிலும், நிலத்திலும், காற்றிலும் வாழ்கின்றன; ஒட்டுண்ணிகளாக பிற விலங்குகளின் உடல்களிலும் குடியேறியுள்ளன. எனவே முதுகெலும்பிலிகள் பாடம் பெரும்பாலான விலங்கு வாழ்க்கை பற்றிய பாடம்; மிகப் பல வடிவங்களிடையே ஒழுங்கைப் பேண ஒரு முறை தேவை. அந்த முறை வகைப்பாடு; விலங்குகள் எவ்வாறு பெயரிடப்பட்டு அடுக்கப்படுகின்றன என்பதை இவ்வத்தியாயம் விளக்குகிறது.",
            "The chapter also makes a point that students often miss. The word Invertebrata is a convenience of the course, and it is not the name of a natural group. The animals that are included in it are as different from one another as any of them is from a vertebrate. To understand the unit one must learn to hold two ideas together: that the invertebrates are taught as a single course, and that they are made up of many separate phyla." to
                "மாணவர்கள் அடிக்கடி தவறவிடும் ஒரு புள்ளியையும் அத்தியாயம் முன்வைக்கிறது. முதுகெலும்பிலிகள் என்ற சொல் பாடத்தின் வசதி; இயற்கைக் குழுவின் பெயர் அல்ல. அதில் சேர்க்கப்பட்ட விலங்குகள் ஒன்றுக்கொன்று, அவற்றில் எதுவும் முதுகெலும்பியிலிருந்து வேறுபடுவது போலவே, வேறுபட்டவை. அலகைப் புரிந்துகொள்ள இரு கருத்துகளை ஒன்றாகப் பிடித்துக்கொள்ளக் கற்க வேண்டும்: முதுகெலும்பிலிகள் ஒரே பாடமாகக் கற்பிக்கப்படுகின்றன; அவை பல தனித் தொகுதிகளால் ஆனவை.",
        ),
        sections = listOf(
            bsec("hierarchy", "The taxonomic hierarchy", "வகைப்பாட்டுப் படிநிலை",
                blocks = listOf(
                    bp("Taxonomic hierarchy arranges organisms in nested ranks, each rank contained within the one above it. In the conventional sequence from the narrowest to the broadest the ranks are species, genus, family, order, class, phylum, kingdom and domain. The species is the basic named rank. Related species are grouped into a genus, related genera into a family, and progressively broader groups form the higher ranks. Each group at any rank is called a taxon, and the plural is taxa." to
                        "வகைப்பாட்டுப் படிநிலை உயிரிகளை உள்ளடக்கிய தரங்களாக அடுக்குகிறது; ஒவ்வொரு தரமும் அதற்கு மேலுள்ளதற்குள் அடங்கும். குறுகியதிலிருந்து அகன்றது வரை வழக்கமான வரிசையில் தரங்கள்: இனம், பேரினம், குடும்பம், வரிசை, வகுப்பு, தொகுதி, இராச்சியம், பேரிராச்சியம். இனம் அடிப்படைப் பெயரிடப்பட்ட தரம். தொடர்புடைய இனங்கள் பேரினமாகவும், தொடர்புடைய பேரினங்கள் குடும்பமாகவும் தொகுக்கப்படுகின்றன; படிப்படியாக அகன்ற குழுக்கள் உயர் தரங்களை உருவாக்குகின்றன. எந்தத் தரத்திலுமுள்ள ஒவ்வொரு குழுவும் வகைப்பாட்டுக் குழு (டாக்சான்); பன்மை டாக்சா."),
                    btable("The ranks of the hierarchy, with a worked example", "படிநிலைத் தரங்களும் எடுத்துக்காட்டும்",
                        listOf("Rank" to "தரம்", "Example: the freshwater snail" to "எடுத்துக்காட்டு: நன்னீர் நத்தை"),
                        listOf(
                            listOf("Domain" to "பேரிராச்சியம்", "Eukarya" to "யூகாரியா"),
                            listOf("Kingdom" to "இராச்சியம்", "Animalia" to "அனிமாலியா"),
                            listOf("Phylum" to "தொகுதி", "Mollusca" to "மொலஸ்கா"),
                            listOf("Class" to "வகுப்பு", "Gastropoda" to "காஸ்ட்ரோபோடா"),
                            listOf("Genus" to "பேரினம்", "Pila" to "பைலா"),
                        )),
                    bp("The sequence is a framework for classification, and the order of ranks is not proof of the relationship between the taxa. A genus and a family do not exist as discoverable objects in the same way as an individual animal. The ranks are conventions by which the groups that biologists recognise are given a place, and the grouping itself is a hypothesis. Whether a group is natural, in the sense that it includes an ancestor and all of its descendants, must be inferred from evidence, and the rank in the table does not decide it." to
                        "வரிசை வகைப்பாட்டுக்கான கட்டமைப்பு; தரங்களின் வரிசை டாக்சாக்களுக்கிடையிலான உறவுக்கான சான்றல்ல. பேரினமும் குடும்பமும் தனி விலங்கு போலக் கண்டறியக்கூடிய பொருட்களாக இல்லை. உயிரியலாளர்கள் அங்கீகரிக்கும் குழுக்களுக்கு இடம் தரும் மரபுகள் தரங்கள்; தொகுப்பே ஒரு கருதுகோள். ஒரு குழு மூதாதையையும் அதன் எல்லா வழித்தோன்றல்களையும் உள்ளடக்கும் பொருளில் இயற்கையானதா என்பது சான்றிலிருந்து ஊகிக்கப்பட வேண்டும்; அட்டவணையிலுள்ள தரம் அதை முடிவு செய்யாது."),
                    bdef("Taxonomy", "வகைப்பாட்டியல்", "The science of describing, naming and classifying organisms." to
                        "உயிரிகளை விவரித்து, பெயரிட்டு, வகைப்படுத்தும் அறிவியல்."),
                    bdef("Taxon", "டாக்சான்", "A group of organisms at any rank that is recognised in classification." to
                        "வகைப்பாட்டில் அங்கீகரிக்கப்படும் எந்தத் தரத்திலுமுள்ள உயிரிகளின் குழு."),
                    bdef("Species", "இனம்", "The basic rank of classification, to which a scientific name is given." to
                        "அறிவியல் பெயர் தரப்படும் வகைப்பாட்டின் அடிப்படைத் தரம்."),
                    bdef("Genus", "பேரினம்", "A group of related species." to
                        "தொடர்புடைய இனங்களின் குழு."),
                    bdef("Phylum", "தொகுதி", "A major group of animals defined by a basic body plan." to
                        "அடிப்படை உடல் திட்டத்தால் வரையறுக்கப்படும் விலங்குகளின் முக்கியக் குழு."),
                )),
            bsec("nomenclature", "Naming animals: binomial nomenclature", "விலங்குகளுக்குப் பெயரிடல்: இருசொல் பெயரிடல்",
                blocks = listOf(
                    bp("In zoological nomenclature a species name is a binomen, which consists of two words, the name of the genus and the specific name, or epithet. The genus name begins with a capital letter and the specific name with a small letter, and both are written in italics, as Pila globosa or Asterias rubens. Only the combination of the two identifies the species, since the same specific name may be used in different genera. The scientific name is the same everywhere, in whatever language the common name is given, and so it makes possible a precise exchange of information between zoologists." to
                        "விலங்கியல் பெயரிடலில் இனப் பெயர் இருசொல்; பேரினப் பெயர், இனச் சிறப்புப் பெயர் அல்லது அடைமொழி என இரு சொற்களைக் கொண்டது. பேரினப் பெயர் பெரிய எழுத்தில், இனச் சிறப்புப் பெயர் சிறிய எழுத்தில் தொடங்கும்; இரண்டும் சாய்வெழுத்தில்: பைலா குளோபோசா அல்லது அஸ்டீரியாஸ் ரூபென்ஸ் போல. அதே சிறப்புப் பெயர் வெவ்வேறு பேரினங்களில் பயன்படலாம் என்பதால் இரண்டின் சேர்க்கை மட்டுமே இனத்தை அடையாளப்படுத்தும். பொதுப் பெயர் எந்த மொழியில் தரப்பட்டாலும் அறிவியல் பெயர் எங்கும் ஒன்றே; எனவே விலங்கியலாளர்களிடையே துல்லியமான தகவல் பரிமாற்றத்தை சாத்தியமாக்குகிறது."),
                    bp("The application of a name to a particular animal is fixed by name-bearing types. A type specimen is a specimen that is kept in a collection, and that serves as the objective reference for the use of the name. If a doubt arises as to what a name means, the type is examined. This arrangement gives a stable basis to names, and prevents the meaning of a name from drifting as ideas about the species change. Names are governed by an international code, which sets out the rules, including the rule of priority, under which the earliest valid name is generally the one in use." to
                        "குறிப்பிட்ட விலங்குக்கு ஒரு பெயரைப் பொருத்துவது பெயர்தாங்கி வகை மாதிரிகளால் நிர்ணயிக்கப்படுகிறது. வகை மாதிரி என்பது தொகுப்பில் வைக்கப்பட்டு, பெயரின் பயன்பாட்டுக்கு புறவய ஆதாரமாக விளங்கும் மாதிரி. பெயர் எதைக் குறிக்கிறது என சந்தேகம் எழுந்தால் வகை மாதிரி ஆராயப்படுகிறது. இந்த ஏற்பாடு பெயர்களுக்கு நிலையான அடிப்படையைத் தருகிறது; இனம் பற்றிய கருத்துகள் மாறும்போது பெயரின் பொருள் நழுவாமல் தடுக்கிறது. பெயர்கள் முன்னுரிமை விதி உள்ளிட்ட விதிகளை வகுக்கும் பன்னாட்டு விதிமுறையால் ஆளப்படுகின்றன; அதன்படி பொதுவாக முதலில் வெளியிடப்பட்ட செல்லுபடியாகும் பெயர் பயன்படும்."),
                    bnote("Writing a scientific name", "அறிவியல் பெயர் எழுதுதல்",
                        "Genus with a capital letter, species with a small letter, both in italics or underlined: Pila globosa." to
                            "பேரினம் பெரிய எழுத்தில், இனம் சிறிய எழுத்தில், இரண்டும் சாய்வெழுத்தில் அல்லது அடிக்கோடிட்டு: பைலா குளோபோசா."),
                    bdef("Binomen", "இருசொல் பெயர்", "The two-word scientific name of a species, made of the genus name and the specific name." to
                        "பேரினப் பெயர், இனச் சிறப்புப் பெயர் கொண்ட இனத்தின் இருசொல் அறிவியல் பெயர்."),
                    bdef("Type specimen", "வகை மாதிரி", "A preserved specimen that serves as the objective reference for the application of a species name." to
                        "இனப் பெயரின் பயன்பாட்டுக்கு புறவய ஆதாரமாக விளங்கும் பாதுகாக்கப்பட்ட மாதிரி."),
                    bdef("Principle of priority", "முன்னுரிமைக் கொள்கை", "The rule that the earliest validly published name of a taxon is the one to be used." to
                        "ஒரு டாக்சானின் முதலில் செல்லுபடியாக வெளியிடப்பட்ட பெயரே பயன்படுத்தப்பட வேண்டும் என்ற விதி."),
                )),
            bsec("evidence", "Characters and evidence used in classification", "வகைப்பாட்டில் பயன்படும் பண்புகளும் சான்றுகளும்",
                blocks = listOf(
                    bp("Classification uses reliable diagnostic characters, which are features that can be observed and that distinguish a group from others. Traditionally these were mostly structural, such as the symmetry of the body, the nature of the body cavity, the presence of segmentation and the form of the larva. Modern systematics also uses evolutionary and molecular evidence, notably the comparison of DNA and protein sequences, in delimiting and relating taxa. The two kinds of evidence often agree, and where they do not, the disagreement is a reason to examine both." to
                        "வகைப்பாடு நம்பகமான அடையாளப் பண்புகளைப் பயன்படுத்துகிறது; இவை கவனிக்கக்கூடிய, ஒரு குழுவை பிறவற்றிலிருந்து வேறுபடுத்தும் தோற்றங்கள். மரபாக இவை பெரும்பாலும் உடல் சமச்சீர், உடற்குழியின் தன்மை, துண்டுபடல் இருப்பு, லார்வா வடிவம் போன்ற அமைப்புப் பண்புகள். நவீன முறைமையியல் டாக்சாக்களை வரையறுக்கவும் தொடர்புபடுத்தவும் பரிணாம, மூலக்கூறுச் சான்றுகளையும், குறிப்பாக DNA, புரதத் தொடர்களின் ஒப்பீட்டையும் பயன்படுத்துகிறது. இரு வகைச் சான்றுகளும் பெரும்பாலும் ஒத்துப்போகின்றன; ஒத்துப்போகாதபோது முரண்பாடு இரண்டையும் ஆராய ஒரு காரணம்."),
                    bpoints("Characters that are used to separate the major animal groups", "முக்கிய விலங்குக் குழுக்களைப் பிரிக்கப் பயன்படும் பண்புகள்",
                        "Level of organisation: cellular, tissue or organ-system." to "அமைப்பு நிலை: உயிரணு, திசு அல்லது உறுப்பு-அமைப்பு.",
                        "Symmetry of the body: none, radial or bilateral." to "உடல் சமச்சீர்: இல்லை, ஆரை அல்லது இருபக்கம்.",
                        "Number of germ layers: two or three." to "கருவடுக்குகளின் எண்ணிக்கை: இரண்டு அல்லது மூன்று.",
                        "Body cavity: absent, a pseudocoelom or a true coelom." to "உடற்குழி: இல்லை, போலி உடற்குழி அல்லது உண்மை உடற்குழி.",
                        "Segmentation: present or absent." to "துண்டுபடல்: உண்டு அல்லது இல்லை.",
                        "Development: the fate of the blastopore, protostome or deuterostome." to "வளர்ச்சி: பிளாஸ்டோபோரின் முடிவு, முதல்வாயி அல்லது இரண்டாம்வாயி.",
                    ),
                    bdef("Diagnostic character", "அடையாளப் பண்பு", "A feature that distinguishes one taxon from others." to
                        "ஒரு டாக்சானை பிறவற்றிலிருந்து வேறுபடுத்தும் பண்பு."),
                    bdef("Systematics", "முறைமையியல்", "The study of the diversity of organisms and their evolutionary relationships." to
                        "உயிரிகளின் பன்முகத்தன்மையையும் அவற்றின் பரிணாம உறவுகளையும் பற்றிய ஆய்வு."),
                    bdef("Germ layer", "கருவடுக்கு", "One of the primary layers of cells in the early embryo: ectoderm, mesoderm or endoderm." to
                        "ஆரம்பக் கருவிலுள்ள முதன்மை உயிரணு அடுக்குகளில் ஒன்று: எக்டோடெர்ம், மெசோடெர்ம் அல்லது எண்டோடெர்ம்."),
                )),
            bsec("not-phylum", "Why Invertebrata is not a single phylum", "முதுகெலும்பிலிகள் ஏன் ஒற்றைத் தொகுதி அல்ல",
                blocks = listOf(
                    bp("Invertebrata is an informal collective term for animals that lack a vertebral column and a cranium. It is not a formally recognised phylum, or any other formal taxon. It contains many distinct animal phyla, among them the Porifera, Cnidaria, Platyhelminthes, Nematoda, Annelida, Mollusca, Arthropoda and Echinodermata. These phyla differ profoundly from one another in their level of organisation, symmetry, body cavities, segmentation, embryology and organ systems, and so they cannot be treated as a single phylum." to
                        "முதுகெலும்பிலிகள் என்பது முதுகெலும்புத் தொடரும் கபாலமும் இல்லாத விலங்குகளுக்கான முறைசாரா கூட்டுச் சொல். அது முறையாக அங்கீகரிக்கப்பட்ட தொகுதியோ பிற முறையான டாக்சானோ அல்ல. அது போரிஃபெரா, நிடேரியா, பிளாட்டிஹெல்மிந்தீஸ், நெமட்டோடா, அனெலிடா, மொலஸ்கா, ஆர்த்ரோபோடா, எக்கினோடெர்மேட்டா உள்ளிட்ட பல தனி விலங்குத் தொகுதிகளைக் கொண்டுள்ளது. இத்தொகுதிகள் அமைப்பு நிலை, சமச்சீர், உடற்குழிகள், துண்டுபடல், கருவியல், உறுப்பு அமைப்புகளில் ஒன்றுக்கொன்று ஆழமாக வேறுபடுகின்றன; எனவே ஒரே தொகுதியாகக் கருத முடியாது."),
                    bp("The absence of a vertebral column is a negative character. It is a convenient way of marking out the animals that are not vertebrates, but it is not a unique derived character defining one natural clade. A group defined by the lack of a feature need not share a common ancestor to the exclusion of other groups. There is a further difficulty, since some invertebrate animals belong to the phylum Chordata, to which the vertebrates also belong. The sea squirts and the lancelets are chordates that lack a vertebral column, and in this respect they are closer to the vertebrates than they are to a jellyfish or a worm." to
                        "முதுகெலும்புத் தொடர் இல்லாமை எதிர்மறைப் பண்பு. முதுகெலும்பிகள் அல்லாத விலங்குகளைக் குறிக்கும் வசதியான வழி; ஆனால் ஒரு இயற்கைக் கிளையை வரையறுக்கும் தனித்த மாறிய பண்பு அல்ல. ஒரு பண்பு இல்லாமையால் வரையறுக்கப்படும் குழு பிற குழுக்களை விலக்கிய பொது மூதாதையைப் பகிர வேண்டியதில்லை. மேலும் ஒரு சிக்கல் உண்டு: சில முதுகெலும்பிலி விலங்குகள் முதுகெலும்பிகளும் சேர்ந்த முதுகுநாணித் தொகுதிக்குள் அடங்குகின்றன. கடல் குடுவைகளும் லான்சிலெட்டுகளும் முதுகெலும்புத் தொடரற்ற முதுகுநாணிகள்; இவ்வகையில் அவை ஜெல்லிமீன் அல்லது புழுவை விட முதுகெலும்பிகளுக்கு நெருக்கமானவை."),
                    bp("In an examination answer, Invertebrata may be used as the grouping of the course, and it is permissible to write about the invertebrates in the plural as a topic. The organisms themselves should be classified under their proper phyla and lower taxa. A student who writes that the invertebrates are a phylum loses the mark, and a student who adds that the term is an informal collective one gains it." to
                        "தேர்வு விடையில் முதுகெலும்பிலிகள் என்பதை பாடத்தின் தொகுப்பாகப் பயன்படுத்தலாம்; முதுகெலும்பிலிகளைப் பன்மையில் ஒரு தலைப்பாக எழுதலாம். உயிரிகளே தம் சரியான தொகுதிகளிலும் கீழ் டாக்சாக்களிலும் வகைப்படுத்தப்பட வேண்டும். முதுகெலும்பிலிகள் ஒரு தொகுதி என எழுதும் மாணவர் மதிப்பெண் இழக்கிறார்; அச்சொல் முறைசாரா கூட்டுச் சொல் எனச் சேர்க்கும் மாணவர் அதைப் பெறுகிறார்."),
                    bnote("A collective term", "கூட்டுச் சொல்",
                        "Invertebrata is an informal grouping of many phyla. Always classify the animal under its own phylum." to
                            "முதுகெலும்பிலிகள் பல தொகுதிகளின் முறைசாரா தொகுப்பு. விலங்கை எப்போதும் அதன் சொந்தத் தொகுதியின் கீழ் வகைப்படுத்துக."),
                    bdef("Invertebrata", "முதுகெலும்பிலிகள்", "An informal collective term for animals without a vertebral column; not a formal taxon." to
                        "முதுகெலும்புத் தொடர் இல்லாத விலங்குகளுக்கான முறைசாரா கூட்டுச் சொல்; முறையான டாக்சான் அல்ல."),
                    bdef("Negative character", "எதிர்மறைப் பண்பு", "A character defined by the absence of a feature, which does not define a natural group." to
                        "ஒரு பண்பின் இல்லாமையால் வரையறுக்கப்படும் பண்பு; இயற்கைக் குழுவை வரையறுக்காது."),
                    bdef("Natural group", "இயற்கைக் குழு", "A group that includes an ancestor and all of its descendants." to
                        "ஒரு மூதாதையையும் அதன் எல்லா வழித்தோன்றல்களையும் உள்ளடக்கும் குழு."),
                    bdef("Clade", "கிளை", "A natural group consisting of an ancestor and all its descendants." to
                        "மூதாதையையும் அதன் அனைத்து வழித்தோன்றல்களையும் கொண்ட இயற்கைக் குழு."),
                )),
            bsec("species", "The species and its limits", "இனமும் அதன் எல்லைகளும்",
                blocks = listOf(
                    bp("The species is the unit of classification that students first meet, and it is easy to suppose that it is a simple, fixed thing. A familiar definition, the biological species concept, describes a species as a group of interbreeding natural populations that are reproductively isolated from other such groups. The definition is useful for animals that reproduce sexually and that can be tested for interbreeding. It is of much less use in many invertebrates, where reproduction is largely asexual, where the animals are known from preserved specimens only, or where the animals are extinct and known as fossils." to
                        "இனம் மாணவர்கள் முதலில் சந்திக்கும் வகைப்பாட்டு அலகு; அது எளிய, நிலையான பொருள் என நினைப்பது எளிது. உயிரியல் இனக் கருத்து என்ற பழகிய வரையறை, இனத்தை பிற அத்தகைய குழுக்களிடமிருந்து இனப்பெருக்கத் தனிமைப்படுத்தப்பட்ட, ஒன்றோடொன்று இனப்பெருக்கம் செய்யும் இயற்கை இனக்கூட்டங்களின் குழுவாக விவரிக்கிறது. பாலினப்பெருக்கம் செய்யும், இனப்பெருக்கம் சோதிக்கக்கூடிய விலங்குகளுக்கு வரையறை பயனுள்ளது. இனப்பெருக்கம் பெரும்பாலும் பாலிலா முறையில் நடக்கும், விலங்குகள் பாதுகாக்கப்பட்ட மாதிரிகளிலிருந்து மட்டுமே அறியப்படும், அல்லது அழிந்து புதைபடிவங்களாக அறியப்படும் பல முதுகெலும்பிலிகளில் மிகக் குறைவாகவே பயன்படுகிறது."),
                    bp("In practice, a zoologist who describes a species usually looks at form, at the structure of the body and its parts, and at distribution, and uses molecular data to test the grouping. Cryptic species, which look alike but are genetically distinct, are found in many invertebrate groups, and the number of described species is certain to change as more are examined. The student should regard the species as the best working unit of classification, which is tested by evidence, and not as a final, definitive object." to
                        "நடைமுறையில் இனத்தை விவரிக்கும் விலங்கியலாளர் வழக்கமாக வடிவத்தையும், உடல் அமைப்பையும் பகுதிகளையும், பரவலையும் பார்த்து, தொகுப்பைச் சோதிக்க மூலக்கூறுத் தரவைப் பயன்படுத்துகிறார். தோற்றத்தில் ஒத்த ஆனால் மரபணுவில் வேறுபட்ட மறைந்த இனங்கள் பல முதுகெலும்பிலிக் குழுக்களில் காணப்படுகின்றன; மேலும் ஆராயப்படும்போது விவரிக்கப்பட்ட இனங்களின் எண்ணிக்கை மாறுவது உறுதி. மாணவர் இனத்தை சான்றால் சோதிக்கப்படும் வகைப்பாட்டின் சிறந்த செயல் அலகாகக் கருத வேண்டும்; இறுதியான, உறுதியான பொருளாக அல்ல."),
                    bp("The same caution applies to the numbers that are given in textbooks for the species in each phylum. Such figures are estimates that depend on the date and the method, and they are revised, so an answer should give them as approximate, with the word about, and should avoid false precision." to
                        "ஒவ்வொரு தொகுதியிலுமுள்ள இனங்களுக்குப் பாடநூல்கள் தரும் எண்களுக்கும் இதே எச்சரிக்கை பொருந்தும். அத்தகைய புள்ளிவிவரங்கள் தேதி, முறையைச் சார்ந்த மதிப்பீடுகள்; திருத்தப்படுகின்றன; எனவே விடை அவற்றை 'சுமார்' என்ற சொல்லுடன் தோராயமாகத் தர வேண்டும்; பொய்யான துல்லியத்தைத் தவிர்க்க வேண்டும்."),
                    bdef("Biological species concept", "உயிரியல் இனக் கருத்து", "A definition of a species as a group of interbreeding populations that are reproductively isolated from other groups." to
                        "பிற குழுக்களிலிருந்து இனப்பெருக்கத் தனிமைப்படுத்தப்பட்ட, இனப்பெருக்கம் செய்யும் இனக்கூட்டங்களின் குழுவாக இனத்தை வரையறுத்தல்."),
                    bdef("Cryptic species", "மறைந்த இனங்கள்", "Species that look alike but are distinct genetically." to
                        "தோற்றத்தில் ஒத்த ஆனால் மரபணுவில் வேறுபட்ட இனங்கள்."),
                    bdef("Reproductive isolation", "இனப்பெருக்கத் தனிமைப்படுத்தல்", "The inability of two populations to interbreed and produce fertile offspring." to
                        "இரு இனக்கூட்டங்கள் இனப்பெருக்கம் செய்து வளமான சந்ததியை உருவாக்க முடியாமை."),
                )),
            bsec("plans", "Body plans across the phyla", "தொகுதிகள் முழுவதிலும் உடல் திட்டங்கள்",
                blocks = listOf(
                    bp("The phyla can be placed in an order that follows the increase in the complexity of organisation, and such an order is a useful way to remember them. The simplest animals have a cellular grade of organisation, in which the cells are loosely associated and do not form true tissues, as in the sponges. The cnidarians have tissues, and two germ layers. The flatworms and the higher phyla have three germ layers and organ systems. The order is a way of organising the facts, and it should not be understood as a ladder of progress from lower to higher, because each living phylum is the product of a long evolutionary history." to
                        "தொகுதிகளை அமைப்பின் சிக்கல் அதிகரிப்பைப் பின்பற்றும் வரிசையில் வைக்கலாம்; அத்தகைய வரிசை அவற்றை நினைவில் வைக்கப் பயனுள்ள வழி. எளிமையான விலங்குகளுக்கு உயிரணு நிலை அமைப்பு உண்டு; அதில் உயிரணுக்கள் தளர்வாக இணைந்து, கடற்பஞ்சுகளில் போல, உண்மைத் திசுக்களை உருவாக்குவதில்லை. நிடேரியன்களுக்கு திசுக்களும் இரு கருவடுக்குகளும் உண்டு. தட்டைப் புழுக்களுக்கும் உயர் தொகுதிகளுக்கும் மூன்று கருவடுக்குகளும் உறுப்பு அமைப்புகளும் உண்டு. வரிசை உண்மைகளை ஒழுங்குபடுத்தும் வழி; தாழ்வானதிலிருந்து உயர்ந்ததுக்கான முன்னேற்றப் படிக்கட்டாகப் புரிந்துகொள்ளக் கூடாது; ஏனெனில் ஒவ்வொரு வாழும் தொகுதியும் நீண்ட பரிணாம வரலாற்றின் விளைவு."),
                    btable("Major invertebrate phyla compared", "முக்கிய முதுகெலும்பிலித் தொகுதிகள் ஒப்பீடு",
                        listOf("Phylum" to "தொகுதி", "Symmetry" to "சமச்சீர்", "Germ layers" to "கருவடுக்குகள்", "Body cavity" to "உடற்குழி"),
                        listOf(
                            listOf("Porifera" to "போரிஃபெரா", "Asymmetrical or radial" to "சமச்சீரற்ற அல்லது ஆரை", "No true layers" to "உண்மை அடுக்குகள் இல்லை", "Absent" to "இல்லை"),
                            listOf("Cnidaria" to "நிடேரியா", "Radial" to "ஆரை", "Two" to "இரண்டு", "Gastrovascular cavity" to "இரைப்பை-நாளக் குழி"),
                            listOf("Platyhelminthes" to "பிளாட்டிஹெல்மிந்தீஸ்", "Bilateral" to "இருபக்கம்", "Three" to "மூன்று", "Acoelomate" to "உடற்குழியற்றது"),
                            listOf("Nematoda" to "நெமட்டோடா", "Bilateral" to "இருபக்கம்", "Three" to "மூன்று", "Pseudocoelom" to "போலி உடற்குழி"),
                            listOf("Annelida" to "அனெலிடா", "Bilateral, segmented" to "இருபக்கம், துண்டுபட்டது", "Three" to "மூன்று", "True coelom" to "உண்மை உடற்குழி"),
                            listOf("Arthropoda" to "ஆர்த்ரோபோடா", "Bilateral, segmented" to "இருபக்கம், துண்டுபட்டது", "Three" to "மூன்று", "Haemocoel" to "ஹீமோசீல்"),
                            listOf("Mollusca" to "மொலஸ்கா", "Bilateral, often modified" to "இருபக்கம், பெரும்பாலும் மாற்றப்பட்டது", "Three" to "மூன்று", "Reduced coelom" to "குறைந்த உடற்குழி"),
                            listOf("Echinodermata" to "எக்கினோடெர்மேட்டா", "Pentaradial in the adult" to "முதிர்ந்ததில் ஐந்தாரை", "Three" to "மூன்று", "True coelom" to "உண்மை உடற்குழி"),
                        )),
                    bp("A single table cannot capture the detail of each phylum, and each is taught as a separate chapter with its own characters and types. The table is intended to show that there are several axes of difference between the phyla, and not one." to
                        "ஒற்றை அட்டவணை ஒவ்வொரு தொகுதியின் விவரத்தையும் பிடிக்க முடியாது; ஒவ்வொன்றும் தனக்குரிய பண்புகளும் வகைகளுமுடன் தனி அத்தியாயமாகக் கற்பிக்கப்படுகிறது. தொகுதிகளுக்கிடையே ஒன்றல்ல, பல வேறுபாட்டு அச்சுகள் உள்ளன என்பதைக் காட்டுவதே அட்டவணையின் நோக்கம்."),
                    bdef("Level of organisation", "அமைப்பு நிலை", "The degree to which cells are combined into tissues, organs and systems." to
                        "உயிரணுக்கள் திசுக்கள், உறுப்புகள், அமைப்புகளாக இணைக்கப்படும் அளவு."),
                    bdef("Symmetry", "சமச்சீர்", "The arrangement of body parts around an axis or plane." to
                        "ஒரு அச்சு அல்லது தளத்தைச் சுற்றி உடல் பகுதிகளின் அமைப்பு."),
                    bdef("Coelom", "உடற்குழி", "A body cavity lined by mesoderm." to
                        "மெசோடெர்ம் வரிசையிட்ட உடற்குழி."),
                    bdef("Acoelomate", "உடற்குழியற்றது", "An animal without a body cavity between the gut and the body wall." to
                        "குடலுக்கும் உடற்சுவருக்கும் இடையே உடற்குழி இல்லாத விலங்கு."),
                )),
        ),
        summary = listOf(
            "The taxonomic hierarchy arranges organisms in nested ranks: species, genus, family, order, class, phylum, kingdom and domain." to "வகைப்பாட்டுப் படிநிலை உயிரிகளை இனம், பேரினம், குடும்பம், வரிசை, வகுப்பு, தொகுதி, இராச்சியம், பேரிராச்சியம் என உள்ளடக்கிய தரங்களாக அடுக்குகிறது.",
            "A species name is a binomen of genus and specific name, and name-bearing types give an objective reference." to "இனப் பெயர் பேரினம், சிறப்புப் பெயரின் இருசொல்; பெயர்தாங்கி வகை மாதிரிகள் புறவய ஆதாரத்தைத் தருகின்றன.",
            "The rank sequence is a framework, not proof of phylogenetic relationship." to "தரவரிசை கட்டமைப்பு; இனவரலாற்று உறவுக்கான சான்றல்ல.",
            "Invertebrata is an informal collective term and not a single phylum." to "முதுகெலும்பிலிகள் முறைசாரா கூட்டுச் சொல்; ஒற்றைத் தொகுதி அல்ல.",
            "Lack of a vertebral column is a negative character; some invertebrate animals are chordates." to "முதுகெலும்புத் தொடர் இல்லாமை எதிர்மறைப் பண்பு; சில முதுகெலும்பிலி விலங்குகள் முதுகுநாணிகள்.",
            "The phyla differ in organisation, symmetry, germ layers, body cavity, segmentation and development." to "தொகுதிகள் அமைப்பு, சமச்சீர், கருவடுக்குகள், உடற்குழி, துண்டுபடல், வளர்ச்சியில் வேறுபடுகின்றன.",
        ),
        checks = listOf(
            bqc("List the ranks of the taxonomic hierarchy from species upwards.", "இனத்திலிருந்து மேல்நோக்கி படிநிலைத் தரங்களைப் பட்டியலிடுக.",
                "Species, genus, family, order, class, phylum, kingdom, domain.", "இனம், பேரினம், குடும்பம், வரிசை, வகுப்பு, தொகுதி, இராச்சியம், பேரிராச்சியம்."),
            bqc("What is a binomen?", "இருசொல் பெயர் என்றால் என்ன?",
                "The two-word scientific name of a species: the genus name and the specific name.", "இனத்தின் இருசொல் அறிவியல் பெயர்: பேரினப் பெயரும் சிறப்புப் பெயரும்."),
            bqc("Why is Invertebrata not a single phylum?", "முதுகெலும்பிலிகள் ஏன் ஒற்றைத் தொகுதி அல்ல?",
                "It is an informal collective term for many phyla that differ profoundly, defined only by the absence of a vertebral column.", "அது ஆழமாக வேறுபடும் பல தொகுதிகளுக்கான முறைசாரா கூட்டுச் சொல்; முதுகெலும்புத் தொடர் இல்லாமையால் மட்டும் வரையறுக்கப்படுகிறது."),
            bqc("Name an invertebrate that is a chordate.", "முதுகுநாணியான ஒரு முதுகெலும்பிலியைப் பெயரிடுக.",
                "A sea squirt or a lancelet.", "கடல் குடுவை அல்லது லான்சிலெட்."),
        ),
    )
}
