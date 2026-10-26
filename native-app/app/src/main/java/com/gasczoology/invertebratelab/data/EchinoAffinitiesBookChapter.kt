package com.gasczoology.invertebratelab.data

/** Book-style chapter: phylogenetic affinities of Echinodermata (enrichment). DRAFT_UNVERIFIED; Tamil needs the author's review. */
object EchinoAffinitiesBookChapter {
    val chapter = bchapter(
        lessonId = "u5-echino-affinities",
        title = "Affinities of the Echinoderms" to "முட்தோலிகளின் உறவுமுறை",
        objectives = listOf(
            "List the deuterostome characters of the echinoderms." to "முட்தோலிகளின் டியூட்டிரோஸ்டோம் பண்புகளைப் பட்டியலிட.",
            "Explain the grouping Ambulacraria and the evidence for it." to "அம்புலாக்ரேரியா குழுவையும் அதற்கான சான்றுகளையும் விளக்க.",
            "State how the echinoderms are related to the chordates, and how they are not." to "முட்தோலிகள் முதுகுநாணிகளுடன் எவ்வாறு தொடர்புடையவை, எவ்வாறு அல்ல என்பதைக் கூற.",
            "Explain why adult radial symmetry does not link echinoderms to cnidarians." to "முதிர்ந்த ஆரச்சமச்சீர் முட்தோலிகளை குழியுடலிகளுடன் ஏன் இணைக்காது என விளக்க.",
            "Distinguish evidence from inference in phylogenetic statements." to "இனவரலாற்றுக் கூற்றுகளில் சான்றையும் ஊகத்தையும் வேறுபடுத்த.",
        ),
        introduction = listOf(
            "The question of where the echinoderms belong in the tree of animal life is one of the oldest in comparative zoology, and it is a good example of how the answer changed as the kinds of evidence widened. The adult animal gives few clues, since it has no head, a radial plan and a skeleton and water-vascular system that are unlike those of any other phylum. The answer came from the embryo and the larva, and later from the study of DNA and proteins." to
                "விலங்கு வாழ்க்கை மரத்தில் முட்தோலிகள் எங்கு சேர்கின்றன என்ற கேள்வி ஒப்பீட்டு விலங்கியலின் மிகப் பழையவற்றில் ஒன்று; சான்றுகளின் வகைகள் விரிந்தபோது விடை எவ்வாறு மாறியது என்பதற்கு நல்ல எடுத்துக்காட்டு. முதிர்ந்த விலங்கு சிறு குறிப்புகளையே தருகிறது; ஏனெனில் தலை இல்லை; ஆரைத் திட்டம், வேறெந்தத் தொகுதியையும் போலல்லாத கூடும் நீர் குருதியோட்ட மண்டலமும் உள்ளன. விடை கரு, லார்வாவிலிருந்தும் பின்னர் DNA, புரத ஆய்விலிருந்தும் வந்தது.",
            "This chapter is classed as enrichment, and it asks the student to do something that is demanding: to separate what is supported by many independent lines of evidence from what is an inference that depends on one analysis. The position of the echinoderms among the deuterostomes is well supported. Some of the finer details of the relationships within the deuterostomes are less certain, and a reader should treat them as the current view." to
                "இவ்வத்தியாயம் செறிவூட்டலாக வகைப்படுத்தப்படுகிறது; கடினமான ஒன்றைச் செய்ய மாணவரைக் கேட்கிறது: பல தனித்த சான்றுகளால் ஆதரிக்கப்படுவதை, ஒரு பகுப்பாய்வைச் சார்ந்த ஊகத்திலிருந்து பிரித்தல். டியூட்டிரோஸ்டோம்களிடையே முட்தோலிகளின் இடம் நன்கு ஆதரிக்கப்படுகிறது. டியூட்டிரோஸ்டோம்களுக்குள் உள்ள உறவுகளின் நுண் விவரங்கள் சிலவற்றில் உறுதி குறைவு; வாசகர் அவற்றை தற்போதைய கருத்தாகக் கருத வேண்டும்.",
        ),
        sections = listOf(
            bsec("deuterostome", "The deuterostome characters", "டியூட்டிரோஸ்டோம் பண்புகள்",
                blocks = listOf(
                    bp("The animals with a bilateral body plan have been divided into two great lineages, the protostomes and the deuterostomes, according to the way in which the mouth and anus form in the embryo. In a classical deuterostome the blastopore, the first opening of the embryo, becomes the anus, and the mouth forms later as a new opening. In the protostomes the blastopore becomes the mouth, or at least the mouth forms near it. The echinoderms are deuterostomes in the characters of their development, and their larvae have the characteristic form of the deuterostome larva." to
                        "இருபக்க உடல் திட்டம் கொண்ட விலங்குகள், கருவில் வாயும் மலவாயும் உருவாகும் விதத்தின்படி புரோட்டோஸ்டோம்கள், டியூட்டிரோஸ்டோம்கள் என்ற இரு பெரும் பரம்பரைகளாகப் பிரிக்கப்பட்டுள்ளன. செம்மையான டியூட்டிரோஸ்டோமில் கருவின் முதல் திறப்பான பிளாஸ்டோபோர் மலவாயாகிறது; வாய் பின்னர் புதிய திறப்பாக உருவாகிறது. புரோட்டோஸ்டோம்களில் பிளாஸ்டோபோர் வாயாகிறது அல்லது குறைந்தது வாய் அதன் அருகே உருவாகிறது. முட்தோலிகள் தம் வளர்ச்சிப் பண்புகளில் டியூட்டிரோஸ்டோம்கள்; அவற்றின் லார்வாக்கள் டியூட்டிரோஸ்டோம் லார்வாவின் தனிப்பண்பு வடிவம் கொண்டவை."),
                    bfig("lib/blastopore-fate",
                        "The fate of the blastopore (schematic). The red spot is the blastopore and the opening it becomes; the blue spot is the second opening that forms later. (A) Protostome: the blastopore becomes the mouth. (B) Deuterostome, as in the echinoderms: the blastopore becomes the anus. (1) blastopore, (2) archenteron, (3) mouth, (4) anus.",
                        "பிளாஸ்டோபோரின் விதி (படத்திட்டம்). சிவப்புப் புள்ளி பிளாஸ்டோபோரும் அது ஆகும் துளையும்; நீலப் புள்ளி பின்னர் உருவாகும் இரண்டாவது துளை. (A) புரோட்டோஸ்டோம்: பிளாஸ்டோபோர் வாயாகிறது. (B) டியூட்டிரோஸ்டோம், எக்கைனோடெர்ம்களில் போல: பிளாஸ்டோபோர் மலவாயாகிறது. (1) பிளாஸ்டோபோர், (2) ஆர்கென்டிரான், (3) வாய், (4) மலவாய்."),
                    bpoints("Developmental characters shared by the echinoderms and other deuterostomes", "முட்தோலிகளும் பிற டியூட்டிரோஸ்டோம்களும் பகிரும் வளர்ச்சிப் பண்புகள்",
                        "The blastopore gives rise to the anus, and the mouth forms secondarily." to "பிளாஸ்டோபோர் மலவாயைத் தருகிறது; வாய் இரண்டாம் நிலையாக உருவாகிறது.",
                        "The coelom is formed as outgrowths of the primitive gut, which is called enterocoely." to "உடற்குழி ஆதிக்குடலின் வளர்ச்சிகளாக உருவாகிறது; இது என்டெரோசீலி எனப்படுகிறது.",
                        "Cleavage is typically radial and indeterminate, so that the cells of the early embryo keep a wide potential." to "பிளவு வழக்கமாக ஆரை, உறுதியற்றது; எனவே ஆரம்பக் கருவின் செல்கள் பரந்த ஆற்றலைத் தக்கவைக்கின்றன.",
                        "The larvae are ciliated and bilateral, with looping bands of cilia." to "லார்வாக்கள் கசையிழை கொண்டவை, இருபக்கச் சமச்சீருடையவை; வளையக் கசையிழைப் பட்டைகளுடன்.",
                    ),
                    bp("These characters must be handled with care. The distinction between the two lineages was first made on the embryo, and it is now supported by molecular evidence, which means that it does not rest on the embryo alone. Not every feature holds in every species, since the details of cleavage and of the formation of the mouth differ, and some deuterostomes depart from the classical description. The examination answer should therefore say the typical characters, and should not imply that each is found without exception." to
                        "இப்பண்புகளைக் கவனமாகக் கையாள வேண்டும். இரு பரம்பரைகளுக்கிடையிலான வேறுபாடு முதலில் கருவில் செய்யப்பட்டது; இப்போது மூலக்கூறுச் சான்றாலும் ஆதரிக்கப்படுகிறது; அதாவது கருவை மட்டும் சார்ந்தது அல்ல. ஒவ்வொரு பண்பும் ஒவ்வோர் இனத்திலும் பொருந்தாது; பிளவு, வாய் உருவாக்கத்தின் விவரங்கள் மாறுபடும்; சில டியூட்டிரோஸ்டோம்கள் செம்மை விளக்கத்திலிருந்து விலகுகின்றன. எனவே தேர்வு விடை வழக்கமான பண்புகளைக் கூற வேண்டும்; ஒவ்வொன்றும் விதிவிலக்கின்றிக் காணப்படுகிறது எனக் குறிப்பிடக் கூடாது."),
                    bdef("Protostome", "புரோட்டோஸ்டோம் (Protostome)", "An animal in which the blastopore becomes, or is closely associated with, the mouth." to
                        "பிளாஸ்டோபோர் வாயாகும் அல்லது வாயுடன் நெருங்கிய தொடர்புடைய விலங்கு."),
                    bdef("Deuterostome", "டியூட்டிரோஸ்டோம்", "An animal in which the blastopore becomes the anus and the mouth forms secondarily." to
                        "பிளாஸ்டோபோர் மலவாயாகி வாய் இரண்டாம் நிலையாக உருவாகும் விலங்கு."),
                    bdef("Blastopore", "பிளாஸ்டோபோர் (Blastopore)", "The first opening of the embryo into the primitive gut." to
                        "ஆதிக்குடலுக்குள் கருவின் முதல் திறப்பு."),
                    bdef("Enterocoely", "என்டெரோசீலி (Enterocoely)", "The formation of the coelom from outgrowths of the gut." to
                        "குடலின் வளர்ச்சிகளிலிருந்து உடற்குழி உருவாதல்."),
                    bdef("Indeterminate cleavage", "உறுதியற்ற பிளவு (Indeterminate cleavage)", "Cleavage in which the cells of the early embryo keep the potential to form a whole animal." to
                        "ஆரம்பக் கரு செல்கள் முழு விலங்கை உருவாக்கும் ஆற்றலைத் தக்கவைக்கும் பிளவு."),
                )),
            bsec("ambulacraria", "Ambulacraria: echinoderms and hemichordates", "அம்புலாக்ரேரியா: முட்தோலிகளும் ஹெமிகார்டேட்டுகளும்",
                blocks = listOf(
                    bp("Modern molecular and morphological evidence places the echinoderms together with the hemichordates in a clade called the Ambulacraria. The hemichordates are the acorn worms and the pterobranchs, which are soft-bodied marine animals with a body divided into a proboscis, a collar and a trunk, and they do not look like echinoderms as adults. The grouping is based on several lines of evidence, so that it is not rested on a single character." to
                        "நவீன மூலக்கூறு, உருவவியல் சான்றுகள் முட்தோலிகளை ஹெமிகார்டேட்டுகளுடன் அம்புலாக்ரேரியா எனும் கிளையில் வைக்கின்றன. ஹெமிகார்டேட்டுகள் ஏகார்ன் புழுக்களும் டெரோபிராங்குகளும்; துதிக்கை, கழுத்துப் பட்டை, உடல் எனப் பிரிந்த உடலுடைய மென்மையான கடல் விலங்குகள்; முதிர்ந்த நிலையில் முட்தோலிகளைப் போலத் தோன்றாது. குழுவாக்கம் பல சான்றுகளின் அடிப்படையிலானது; ஒரே பண்பின்மேல் நிறுத்தப்பட்டதல்ல."),
                    bpoints("Evidence for the Ambulacraria", "அம்புலாக்ரேரியாவுக்கான சான்றுகள்",
                        "Molecular analyses of many genes and proteins group the two phyla together." to "பல மரபணுக்கள், புரதங்களின் மூலக்கூறு ஆய்வுகள் இவ்விரு தொகுதிகளையும் ஒன்றாக்குகின்றன.",
                        "Both have a coelom organised into parts, derived from the gut, and the parts of the coelom correspond in position." to "இரண்டுக்கும் குடலிலிருந்து உருவாகி பகுதிகளாக அமைந்த உடற்குழி உண்டு; உடற்குழிப் பகுதிகள் நிலையில் ஒத்துள்ளன.",
                        "The larvae of echinoderms of the dipleurula type, such as the bipinnaria, resemble the tornaria larva of the acorn worms." to "பிபின்னேரியா போன்ற டிப்ளூரூலா வகை முட்தோலி லார்வாக்கள் ஏகார்ன் புழுக்களின் டார்னேரியா லார்வாவை ஒத்தவை.",
                        "Features of gene expression in early development show some parallels." to "ஆரம்ப வளர்ச்சியில் மரபணு வெளிப்பாட்டின் பண்புகள் சில இணைகளைக் காட்டுகின்றன.",
                    ),
                    bp("The reason why the grouping is accepted is that these independent lines agree. Where evidence from structure and from molecules gives the same answer, the inference is stronger than where either one is used alone. A student should say that the larvae are suggestive, and that the strongest evidence for the clade is the combination of the molecular and the developmental data." to
                        "குழுவாக்கம் ஏற்கப்படுவதற்குக் காரணம் இத்தனித்த சான்றுகள் ஒத்துப்போவது. அமைப்பிலிருந்தும் மூலக்கூறுகளிலிருந்தும் வரும் சான்று ஒரே விடை தரும்போது, ஏதேனும் ஒன்றை மட்டும் பயன்படுத்துவதை விட ஊகம் வலிமையானது. லார்வாக்கள் சுட்டுபவை என்றும், கிளைக்கான வலிமையான சான்று மூலக்கூறு, வளர்ச்சித் தரவுகளின் சேர்க்கை என்றும் மாணவர் கூற வேண்டும்."),
                    bdef("Ambulacraria", "அம்புலாக்ரேரியா (Ambulacraria)", "The clade formed by the echinoderms and the hemichordates." to
                        "முட்தோலிகளும் ஹெமிகார்டேட்டுகளும் உருவாக்கும் கிளை."),
                    bdef("Hemichordata", "அரைநாணிகள்", "A phylum of marine worm-like animals, including acorn worms, with a proboscis, collar and trunk." to
                        "துதிக்கை, கழுத்துப் பட்டை, உடலுடைய, ஏகார்ன் புழுக்கள் உள்ளிட்ட கடல் புழு போன்ற விலங்குகளின் தொகுதி."),
                    bdef("Clade", "கிளை", "A group consisting of an ancestor and all its descendants." to
                        "மூதாதையையும் அதன் அனைத்து வழித்தோன்றல்களையும் கொண்ட குழு."),
                    bdef("Synapomorphy", "பகிரப்பட்ட பெறுபண்பு (Synapomorphy)", "A derived character shared by the members of a clade and inherited from their common ancestor." to
                        "ஒரு கிளையின் உறுப்பினர்கள் பகிரும், பொது மூதாதையிடமிருந்து பெறப்பட்ட மாறிய பண்பு."),
                )),
            bsec("chordates", "Relationship to the chordates", "முதுகுநாணிகளுடனான உறவு",
                blocks = listOf(
                    bp("The Ambulacraria is related to the Chordata within the Deuterostomia, which is to say that the two groups are descended from a common ancestor that lived in the sea a very long time ago. They are sister groups, in the current view, and neither is the ancestor of the other. The echinoderms are therefore relatives of the vertebrates, and the statement that they are the ancestors of the vertebrates is wrong." to
                        "அம்புலாக்ரேரியா டியூட்டிரோஸ்டோம்களுக்குள் முதுகுநாணிகளுடன் தொடர்புடையது; அதாவது இரு குழுக்களும் மிக நீண்ட காலத்துக்கு முன் கடலில் வாழ்ந்த பொது மூதாதையிடமிருந்து வந்தவை. தற்போதைய கருத்தில் அவை சகோதரக் குழுக்கள்; எதுவும் மற்றதன் மூதாதை அல்ல. எனவே முட்தோலிகள் முதுகெலும்பிகளின் உறவினர்; அவை முதுகெலும்பிகளின் மூதாதையர் என்ற கூற்று தவறு."),
                    bp("The echinoderms lack the defining characters of the chordates. They have no notochord, and no dorsal hollow nerve cord, and no pharyngeal slits or post-anal tail at any stage that is generally recognised. These are the synapomorphies that define the chordates, and the absence of these in the echinoderms is not a puzzle, because the common ancestor need not have had them in the form seen in chordates. Some features of the nervous system and body plan of the hemichordates have been compared with those of chordates, and the interpretation of such comparisons is under discussion." to
                        "முதுகுநாணிகளின் வரையறுக்கும் பண்புகள் முட்தோலிகளுக்கு இல்லை. நாண் இல்லை; முதுகுப்புற வெற்று நரம்பு வடம் இல்லை; பொதுவாக அங்கீகரிக்கப்படும் எந்த நிலையிலும் தொண்டைப் பிளவுகள் அல்லது மலவாய்க்குப் பின் வால் இல்லை. இவை முதுகுநாணிகளை வரையறுக்கும் பகிரப்பட்ட பெறுபண்புகள்; முட்தோலிகளில் இவை இல்லாதது புதிர் அல்ல; ஏனெனில் பொது மூதாதைக்கு முதுகுநாணிகளில் காணும் வடிவில் அவை இருந்திருக்க வேண்டியதில்லை. ஹெமிகார்டேட்டுகளின் நரம்பு அமைப்பு, உடல் திட்டத்தின் சில பண்புகள் முதுகுநாணிகளுடன் ஒப்பிடப்பட்டுள்ளன; அத்தகைய ஒப்பீடுகளின் விளக்கம் விவாதத்தில் உள்ளது."),
                    bnote("Relative, not ancestor", "உறவினர், மூதாதை அல்ல",
                        "Sister groups share a common ancestor. Neither group is the ancestor of the other." to
                            "சகோதரக் குழுக்கள் பொது மூதாதையைப் பகிர்கின்றன. எதுவும் மற்றதன் மூதாதை அல்ல."),
                    bdef("Chordata", "முதுகுநாணிகள்", "The phylum with a notochord, a dorsal hollow nerve cord and pharyngeal slits at some stage." to
                        "ஏதேனும் ஒரு நிலையில் நாண், முதுகுப்புற வெற்று நரம்பு வடம், தொண்டைப் பிளவுகள் கொண்ட தொகுதி."),
                    bdef("Notochord", "நாண்", "A flexible rod along the back of a chordate embryo that supports the body." to
                        "உடலைத் தாங்கும், முதுகுநாணிக் கருவின் முதுகில் செல்லும் நெகிழும் கோல்."),
                    bdef("Sister group", "சகோதரக் குழு (Sister group)", "One of two groups that are each other's closest relatives." to
                        "ஒன்றுக்கொன்று நெருங்கிய உறவினரான இரு குழுக்களில் ஒன்று."),
                )),
            bsec("cnidaria", "Why radial symmetry does not link echinoderms to cnidarians", "ஆரச்சமச்சீர் ஏன் முட்தோலிகளை குழியுடலிகளுடன் இணைக்காது",
                blocks = listOf(
                    bp("Older books sometimes grouped the starfishes, the jellyfishes and the sea anemones together as the radiate animals, because of their radial form. The grouping is no longer accepted. Adult pentaradial symmetry in an echinoderm is a secondary condition that develops from a bilaterally symmetrical larva, and the cnidarians have a fundamentally different, non-bilaterian radial body plan. A similar adult shape does not make animals close relatives, since it can have been reached by different routes." to
                        "பழைய நூல்கள் ஆரை வடிவத்தால் நட்சத்திர மீன்கள், ஜெல்லிமீன்கள், கடல்சாமந்திகளை ஆரை விலங்குகளாக சில சமயம் ஒன்றாக்கின. அக்குழுவாக்கம் இப்போது ஏற்கப்படுவதில்லை. முட்தோலியில் முதிர்ந்த ஐந்தாரைச் சமச்சீர் இருபக்கச் சமச்சீர் லார்வாவிலிருந்து வளரும் இரண்டாம் நிலை; குழியுடலிகளுக்கு அடிப்படையில் வேறுபட்ட, இருபக்கமற்ற ஆரை உடல் திட்டம் உண்டு. ஒத்த முதிர்ந்த வடிவம் விலங்குகளை நெருங்கிய உறவினராக்காது; ஏனெனில் வெவ்வேறு வழிகளில் அடையப்பட்டிருக்கலாம்."),
                    btable("Echinoderms compared with cnidarians", "முட்தோலிகளும் குழியுடலிகளும் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Echinoderms" to "முட்தோலிகள்", "Cnidarians" to "குழியுடலிகள்"),
                        listOf(
                            listOf("Germ layers" to "கருவடுக்குகள்", "Three (triploblastic)" to "மூன்று (மூவடுக்கு)", "Two with mesoglea (diploblastic)" to "மீசோகிளியாவுடன் இரண்டு (ஈரடுக்கு)"),
                            listOf("Body cavity" to "உடற்குழி", "A true coelom" to "உண்மையான உடற்குழி", "A gastrovascular cavity; no coelom" to "இரைப்பை-நாளக் குழி; உடற்குழி இல்லை"),
                            listOf("Larva" to "லார்வா", "Bilateral ciliated larva" to "இருபக்க கசையிழை லார்வா", "Typically a planula" to "பொதுவாக பிளானுலா"),
                            listOf("Skeleton" to "கூடு", "Calcareous mesodermal endoskeleton" to "சுண்ணாம்பு மெசோடெர்மல் அகச்சட்டகம்", "No comparable endoskeleton in the group as a whole" to "குழு முழுவதற்கும் ஒப்பிடத்தக்க அகச்சட்டகம் இல்லை"),
                            listOf("Characteristic structures" to "தனிப்பண்பு அமைப்புகள்", "Water-vascular system" to "நீர் குருதியோட்ட மண்டலம்", "Cnidocytes (stinging cells)" to "நிடோசைட்டுகள் (கொட்டும் செல்கள்)"),
                        )),
                    bp("The example shows why the characters are weighed by their reliability. Adult shape is a poor guide to ancestry, as it is easily changed by the way of life, while the pattern of development and the molecular data change slowly. A student who writes that echinoderms are related to cnidarians because both are radial is making the error of reasoning from a similar appearance to a close relationship." to
                        "பண்புகள் நம்பகத்தன்மையால் எடை போடப்படுவதை இந்த எடுத்துக்காட்டு காட்டுகிறது. முதிர்ந்த வடிவம் மூதாதைத்தன்மைக்கு மோசமான வழிகாட்டி; ஏனெனில் வாழ்க்கை முறையால் எளிதில் மாறுகிறது; வளர்ச்சி முறையும் மூலக்கூறுத் தரவும் மெதுவாக மாறுகின்றன. இரண்டும் ஆரையாக இருப்பதால் முட்தோலிகள் குழியுடலிகளுடன் தொடர்புடையவை என எழுதும் மாணவர் ஒத்த தோற்றத்திலிருந்து நெருங்கிய உறவுக்கு முடிவெடுக்கும் தவற்றைச் செய்கிறார்."),
                    bdef("Radiata", "ரேடியேட்டா (Radiata)", "An older grouping of radially symmetrical animals that is no longer accepted as natural." to
                        "இயற்கையானதாக இனி ஏற்கப்படாத, ஆரச்சமச்சீர் விலங்குகளின் பழைய குழுவாக்கம்."),
                    bdef("Diploblastic", "ஈரடுக்கு", "Having two germ layers, ectoderm and endoderm, with a jelly-like layer between them." to
                        "இடையே ஜெல்லி போன்ற அடுக்குடன் எக்டோடெர்ம், எண்டோடெர்ம் எனும் இரு கருவடுக்குகள் உடைய."),
                    bdef("Triploblastic", "மூவடுக்கு", "Having three germ layers, ectoderm, mesoderm and endoderm." to
                        "எக்டோடெர்ம், மெசோடெர்ம், எண்டோடெர்ம் எனும் மூன்று கருவடுக்குகள் உடைய."),
                    bdef("Convergence", "குவிவுப் பரிணாமம் (Convergence)", "Independent evolution of similar features in unrelated lineages." to
                        "தொடர்பற்ற பரம்பரைகளில் ஒத்த பண்புகள் தனித்துப் பரிணமித்தல்."),
                )),
            bsec("fossil-origin", "How the radial plan arose: what fossils suggest", "ஆரைத் திட்டம் எவ்வாறு தோன்றியது: புதைபடிவங்கள் சுட்டுவது",
                blocks = listOf(
                    bp("The larva shows that the ancestors of the echinoderms were bilateral, and the fossil record adds a second kind of evidence. Among the earliest echinoderm fossils, which come from rocks of the Cambrian period, there are forms with calcareous plates that are not arranged in the five-part pattern, and some are strongly asymmetrical. Such fossils are interpreted as showing that the group was bilateral or irregular at first, and that the radial plan was acquired later in the history of the phylum, probably in association with a way of life that was attached or slow-moving." to
                        "முட்தோலிகளின் மூதாதையர் இருபக்கச் சமச்சீருடையவர் என லார்வா காட்டுகிறது; புதைபடிவப் பதிவு இரண்டாம் வகைச் சான்றைச் சேர்க்கிறது. கேம்ப்ரியன் காலப் பாறைகளிலிருந்து வரும் ஆரம்பகால முட்தோலிப் புதைபடிவங்களில் ஐந்துபகுதி அமைப்பில் அமையாத சுண்ணாம்புத் தகடுகளுடைய வடிவங்கள் உள்ளன; சில கடுமையாகச் சமச்சீரற்றவை. அத்தகைய புதைபடிவங்கள் குழு முதலில் இருபக்கமாக அல்லது ஒழுங்கற்றதாக இருந்தது என்றும், ஆரைத் திட்டம் தொகுதியின் வரலாற்றில் பின்னர், ஒருவேளை ஒட்டிய அல்லது மெதுவான வாழ்க்கை முறையுடன் தொடர்புடன், பெறப்பட்டது என்றும் விளக்கப்படுகின்றன."),
                    bp("The interpretation of the earliest fossils is not settled. The animals are known only from their skeletons, so the soft parts that would show the position of the water-vascular system must be inferred, and different workers have read the same fossils in different ways. The sound statement is that fossil and larval evidence are consistent with a bilateral ancestry, and that the details of the earliest history are under active study. The student should avoid writing that a particular fossil is the ancestor of the living classes." to
                        "ஆரம்பகாலப் புதைபடிவங்களின் விளக்கம் தீர்க்கப்படவில்லை. விலங்குகள் கூடுகளிலிருந்து மட்டுமே அறியப்படுவதால் நீர் குருதியோட்ட மண்டலத்தின் நிலையைக் காட்டும் மென்பகுதிகள் ஊகிக்கப்பட வேண்டும்; ஒரே புதைபடிவங்களை வெவ்வேறு ஆய்வாளர்கள் வெவ்வேறு விதமாகப் படித்துள்ளனர். புதைபடிவ, லார்வாச் சான்றுகள் இருபக்க மரபுடன் ஒத்துப்போகின்றன என்பதும் ஆரம்ப வரலாற்றின் விவரங்கள் தீவிர ஆய்வில் உள்ளன என்பதும் சரியான கூற்று. குறிப்பிட்ட புதைபடிவம் வாழும் வகுப்புகளின் மூதாதை என மாணவர் எழுதுவதைத் தவிர்க்க வேண்டும்."),
                    bp("The two lines of evidence also illustrate a general point about reasoning. A conclusion about history is stronger when a living developmental stage and the fossil record independently point to the same idea, though neither can prove it. The conclusion is held until better evidence replaces it." to
                        "இரு சான்று வரிசைகளும் பகுத்தறிவு பற்றிய பொது விஷயத்தையும் விளக்குகின்றன. வாழும் வளர்ச்சி நிலையும் புதைபடிவப் பதிவும் தனித்து ஒரே கருத்தைச் சுட்டும்போது வரலாறு பற்றிய முடிவு வலிமையடைகிறது; ஆனால் இரண்டில் எதுவும் அதை நிரூபிக்க முடியாது. சிறந்த சான்று அதை மாற்றும் வரை முடிவு பற்றிக்கொள்ளப்படுகிறது."),
                    bp("For an examination answer, the fossil point is best added as one sentence at the end of the section on the radial plan: the early fossils include asymmetrical forms, which agrees with the evidence of the bilateral larva that the radial symmetry of the living classes is a later acquisition. This keeps the statement short and keeps it within what the evidence can support, and it avoids the temptation to name particular fossil genera that the syllabus does not require." to
                        "தேர்வு விடைக்கு, புதைபடிவக் குறிப்பை ஆரைத் திட்டம் பற்றிய பகுதியின் இறுதியில் ஒரு வாக்கியமாகச் சேர்ப்பது சிறந்தது: ஆரம்பகாலப் புதைபடிவங்களில் சமச்சீரற்ற வடிவங்கள் அடங்கும்; இது வாழும் வகுப்புகளின் ஆரச்சமச்சீர் பிற்கால பெறுபண்பு என்ற இருபக்க லார்வாச் சான்றுடன் ஒத்துப்போகிறது. இது கூற்றைச் சுருக்கமாகவும் சான்று ஆதரிக்கக்கூடியதற்குள்ளும் வைத்து, பாடத்திட்டம் கோராத குறிப்பிட்ட புதைபடிவப் பேரினங்களைப் பெயரிடும் ஆசையைத் தவிர்க்கிறது."),
                    bdef("Cambrian", "கேம்ப்ரியன்", "The geological period, beginning about 540 million years ago, in which the main animal groups first appear clearly in the fossil record." to
                        "முக்கிய விலங்குக் குழுக்கள் புதைபடிவப் பதிவில் தெளிவாக முதலில் தோன்றும், சுமார் 54 கோடி ஆண்டுகளுக்கு முன் தொடங்கிய புவியியல் காலம்."),
                    bdef("Sessile", "ஒட்டி வாழும்", "Attached to the bottom and not moving about." to
                        "அடியில் ஒட்டி நகராமல் இருக்கும்."),
                )),
            bsec("hemichordates", "The hemichordates: acorn worms and pterobranchs", "ஹெமிகார்டேட்டுகள்: ஏகார்ன் புழுக்களும் டெரோபிராஞ்சுகளும்",
                blocks = listOf(
                    bp("The hemichordates are marine, worm-like animals that bear on the relationship of echinoderms to chordates. The acorn worms, such as Balanoglossus, live in burrows in sand and mud. The body has three regions: a conical proboscis in front, a short collar behind it, and a long trunk. The trunk carries a row of paired gill slits on each side of the front part, through which water taken in at the mouth leaves, and these are used in feeding and respiration. The circulation is open, there is a nerve cord on the dorsal side in the collar and on the ventral side in the trunk, and the larva of many species is the tornaria.",
                        "ஹெமிகார்டேட்டுகள் எக்கைனோடெர்ம்கள் கார்டேட்டுகளுடன் கொண்ட உறவுடன் தொடர்புடைய, கடல்வாழ், புழு போன்ற விலங்குகள். பாலனோகுளாசஸ் போன்ற ஏகார்ன் புழுக்கள் மணல், சேற்றிலுள்ள வளைகளில் வாழ்கின்றன. உடலில் மூன்று பகுதிகள்: முன்னால் கூம்பு வடிவ துதிக்கை, பின்னால் குறுகிய கழுத்துப்பட்டை, நீண்ட உடல். உடலின் முன்பகுதியின் இரு பக்கங்களிலும் இணை செவுள் பிளவுகளின் வரிசை உள்ளது; வாயில் எடுக்கப்படும் நீர் அவற்றின் வழியே வெளியேறுகிறது; அவை உணவூட்டம், சுவாசத்தில் பயன்படுகின்றன. சுற்றோட்டம் திறந்தது; கழுத்துப்பட்டையில் முதுகுப்புறமும் உடலில் வயிற்றுப்புறமும் நரம்பு வடம் உள்ளது; பல இனங்களின் லார்வா டார்னேரியா."),
                    bp("The pterobranchs, such as Cephalodiscus and Rhabdopleura, are small colonial animals that live in tubes which they secrete, and they feed with a pair or more of arms covered with ciliated tentacles. They are often considered to resemble the ancestral hemichordates, and the extinct graptolites, which are common fossils of the Palaeozoic, had tubes much like theirs. Together with the acorn worms they place the hemichordates near the origin of the deuterostomes, as animals that share features with both the chordates and the echinoderms.",
                        "செபலோடிஸ்கஸ், ரப்டோப்ளூரா போன்ற டெரோபிராஞ்சுகள் தாம் சுரக்கும் குழாய்களில் வாழும் சிறு காலனி விலங்குகள்; சிலியா உணர்கொம்புகளால் மூடப்பட்ட ஒன்று அல்லது அதற்கு மேற்பட்ட இணை கைகளால் உண்கின்றன. அவை மூதாதை ஹெமிகார்டேட்டுகளை ஒத்தவை எனப் பெரும்பாலும் கருதப்படுகின்றன; பேலியோசோயிக் பொது புதைபடிவங்களான அழிந்த கிராப்டோலைட்டுகளுக்கு அவற்றைப் போன்ற குழாய்கள் இருந்தன. ஏகார்ன் புழுக்களுடன் சேர்ந்து, ஹெமிகார்டேட்டுகளை கார்டேட்டுகள், எக்கைனோடெர்ம்கள் இரண்டுடனும் பண்புகளைப் பகிரும் விலங்குகளாக டியூட்டிரோஸ்டோம்களின் தோற்றத்தின் அருகில் வைக்கின்றன."),
                    bdef("Gill slit", "செவுள் பிளவு", "One of a series of openings from the pharynx to the outside, found in hemichordates and chordates." to
                        "தொண்டையிலிருந்து வெளியே திறக்கும் வரிசையான துளைகளில் ஒன்று; ஹெமிகார்டேட்டுகள், கார்டேட்டுகளில் காணப்படுகிறது."),
                    bdef("Tornaria", "டார்னேரியா (Tornaria)", "The ciliated larva of an acorn worm, which closely resembles the auricularia larva of a sea cucumber." to
                        "ஏகார்ன் புழுவின் சிலியா கொண்ட லார்வா; கடல் வெள்ளரியின் ஆரிக்குலேரியா லார்வாவுடன் மிக ஒத்தது."),
                )),
            bsec("comparison", "Echinoderms, hemichordates and chordates compared", "எக்கைனோடெர்ம்கள், ஹெமிகார்டேட்டுகள், கார்டேட்டுகள் ஒப்பீடு",
                blocks = listOf(
                    btable("Characters of the three deuterostome phyla", "மூன்று டியூட்டிரோஸ்டோம் தொகுதிகளின் பண்புகள்",
                        listOf("Character" to "பண்பு", "Echinodermata" to "எக்கைனோடெர்மாடா", "Hemichordata" to "ஹெமிகார்டேட்டா", "Chordata" to "கார்டேட்டா"),
                        listOf(
                            listOf("Blastopore" to "பிளாஸ்டோபோர்", "Becomes the anus" to "குதமாகிறது", "Becomes the anus" to "குதமாகிறது", "Becomes the anus" to "குதமாகிறது"),
                            listOf("Coelom" to "உடற்குழி", "By enterocoely; three pairs of sacs" to "என்டெரோசீலி; மூன்று இணை பைகள்", "By enterocoely; three regions" to "என்டெரோசீலி; மூன்று பகுதிகள்", "By enterocoely or folding" to "என்டெரோசீலி அல்லது மடிப்பு"),
                            listOf("Larva" to "லார்வா", "Dipleurula type (bipinnaria, pluteus, auricularia)" to "டிப்ளூரூலா வகை (பிபின்னேரியா, ப்ளூட்டியஸ், ஆரிக்குலேரியா)", "Tornaria" to "டார்னேரியா", "Tadpole larva in tunicates" to "ஆசிடியன்களில் தலைப்பிரட்டை லார்வா"),
                            listOf("Gill slits" to "செவுள் பிளவுகள்", "Absent" to "இல்லை", "Present in acorn worms" to "ஏகார்ன் புழுக்களில் உண்டு", "Present at some stage" to "ஏதேனும் ஒரு நிலையில் உண்டு"),
                            listOf("Nerve cord" to "நரம்பு வடம்", "Nerve ring and radial nerves" to "நரம்பு வளையமும் கதிர் நரம்புகளும்", "Dorsal and ventral cords" to "முதுகு, வயிற்று வடங்கள்", "Dorsal hollow cord" to "முதுகுப்புற உள்ளீடற்ற வடம்"),
                            listOf("Notochord" to "நோட்டோகார்ட்", "Absent" to "இல்லை", "Absent; stomochord is of doubtful homology" to "இல்லை; ஸ்டோமோகார்டின் ஒத்தமைப்பு ஐயத்துக்குரியது", "Present" to "உண்டு"),
                            listOf("Adult symmetry" to "முதிர்ந்த சமச்சீர்", "Pentaradial" to "ஐந்தாரைச் சமச்சீர்", "Bilateral" to "இருபக்கச் சமச்சீர்", "Bilateral" to "இருபக்கச் சமச்சீர்"),
                        )),
                    bp("The table shows that the three phyla share the embryonic characters, and that the echinoderms have lost, or never had, the adult characters that unite the other two. It is the embryonic and larval characters, and not the adult form, that carry the evidence, which is the reason why the larvae are given such weight in this subject.",
                        "மூன்று தொகுதிகளும் கருவியல் பண்புகளைப் பகிர்கின்றன என்றும், மற்ற இரண்டையும் இணைக்கும் முதிர்ந்த பண்புகளை எக்கைனோடெர்ம்கள் இழந்துள்ளன அல்லது ஒருபோதும் பெற்றிருக்கவில்லை என்றும் அட்டவணை காட்டுகிறது. சான்றைத் தாங்குவது முதிர்ந்த வடிவம் அல்ல, கருவியல், லார்வா பண்புகளே; இத்தலைப்பில் லார்வாக்களுக்கு இவ்வளவு முக்கியத்துவம் தரப்படுவதற்குக் காரணம் இதுவே."),
                )),
            bsec("skeleton", "The skeleton as a character", "ஒரு பண்பாக எலும்புக்கூடு",
                blocks = listOf(
                    bp("The skeleton of an echinoderm is made of ossicles of calcium carbonate in the form of calcite, formed in the mesoderm beneath the epidermis. Each ossicle has a porous, lattice-like structure called stereom, which combines strength with lightness and is found in no other animals in the same form. This internal skeleton is therefore comparable in position, though not in material, to the skeleton of a vertebrate, which is also formed by the mesoderm, and the echinoderm skeleton has been used in the argument that both are deuterostome features. The comparison must, however, be made cautiously, since the skeletons of the two groups differ in material and in structure, and the resemblance is one of position and origin only.",
                        "எக்கைனோடெர்மின் எலும்புக்கூடு எப்பிடெர்மிஸின் கீழ் மீசோடெர்மில் உருவாகும், கால்சைட் வடிவ கால்சியம் கார்பனேட் ஆஸிக்கிள்களால் ஆனது. ஒவ்வொரு ஆசிகிளுக்கும் வலிமையையும் இலேசையும் இணைக்கும் ஸ்டீரியோம் எனும் துளையுடைய வலை போன்ற அமைப்பு உள்ளது; வேறு விலங்குகளில் அதே வடிவில் காணப்படுவதில்லை. எனவே இவ்வுட்புற எலும்புக்கூடு, பொருளில் அல்லாமல் நிலையில், மீசோடெர்மாலும் உருவாகும் முதுகெலும்பியின் எலும்புக்கூட்டுடன் ஒப்பிடத்தக்கது; இரண்டும் டியூட்டிரோஸ்டோம் பண்புகள் என்ற வாதத்தில் எக்கைனோடெர்ம் எலும்புக்கூடு பயன்பட்டுள்ளது. எனினும் இரு குழுக்களின் எலும்புக்கூடுகள் பொருளிலும் அமைப்பிலும் வேறுபடுவதால், ஒப்பீடு எச்சரிக்கையுடன் செய்யப்பட வேண்டும்; ஒற்றுமை நிலை, தோற்றத்தில் மட்டுமே."),
                    bdef("Stereom", "ஸ்டீரியோம் (Stereom)", "The porous, lattice-like calcite structure of an echinoderm ossicle." to
                        "எக்கைனோடெர்ம் ஆசிகிளின் துளையுடைய வலை போன்ற கால்சைட் அமைப்பு."),
                )),
            bsec("answering", "Answering the question on affinities", "உறவுகள் குறித்த வினாவுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("The question usually reads: the echinoderms are radial as adults and yet are classified with the bilateral animals, and are placed close to the chordates; discuss. The answer should be built in three steps. The first is that the larva is bilateral and the radial symmetry is secondary. The second is the list of deuterostome characters: the blastopore forming the anus, the radial and indeterminate cleavage, the enterocoelic coelom and the mesodermal skeleton. The third is the link with the hemichordates and the chordates, through the tornaria and the auricularia, the gill slits and the dorsal nerve cord. The answer should close by pointing out that molecular data support the group Ambulacraria, the echinoderms with the hemichordates, as a sister group of the chordates.",
                        "வினா பொதுவாக இவ்வாறு அமையும்: எக்கைனோடெர்ம்கள் முதிர்ந்த நிலையில் ஆரச்சமச்சீருடையவை; ஆயினும் இருபக்கச் சமச்சீர் விலங்குகளுடன் வகைப்படுத்தப்பட்டு கார்டேட்டுகளுக்கு அருகில் வைக்கப்படுகின்றன; விவாதிக்க. விடை மூன்று படிகளில் கட்டப்பட வேண்டும். முதலாவது, லார்வா இருபக்கச் சமச்சீர்; ஆரச்சமச்சீர் இரண்டாம் நிலை. இரண்டாவது, டியூட்டிரோஸ்டோம் பண்புகளின் பட்டியல்: குதமாகும் பிளாஸ்டோபோர், ஆரப் பிளவு, நிர்ணயமற்ற பிளவு, என்டெரோசீல் உடற்குழி, மீசோடெர்ம் எலும்புக்கூடு. மூன்றாவது, டார்னேரியா, ஆரிக்குலேரியா, செவுள் பிளவுகள், முதுகு நரம்பு வடம் வழியே ஹெமிகார்டேட்டுகள், கார்டேட்டுகளுடனான தொடர்பு. எக்கைனோடெர்ம்களை ஹெமிகார்டேட்டுகளுடன் சேர்த்த அம்புலாக்ரேரியா குழு கார்டேட்டுகளின் சகோதரக் குழுவாக மூலக்கூறுத் தரவால் ஆதரிக்கப்படுகிறது என்று சுட்டிக்காட்டி விடை முடிய வேண்டும்."),
                    bpoints("Frequent errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Saying that echinoderms are chordates, or that they have a notochord." to "எக்கைனோடெர்ம்கள் கார்டேட்டுகள் என்றோ நோட்டோகார்ட் கொண்டவை என்றோ கூறுதல்.",
                        "Basing the relationship on the adult form, which is radial." to "ஆரச்சமச்சீர் முதிர்ந்த வடிவத்தை உறவின் அடிப்படையாகக் கொள்ளுதல்.",
                        "Linking the echinoderms to the cnidarians because both are radial." to "இரண்டும் ஆரச்சமச்சீர் என்பதால் எக்கைனோடெர்ம்களை நிடேரியன்களுடன் இணைத்தல்.",
                        "Leaving out the larval evidence, which is the strongest." to "வலுவான லார்வாச் சான்றை விட்டுவிடுதல்.",
                    ),
                )),
            bsec("evidence", "Evidence and inference in phylogeny", "இனவரலாற்றில் சான்றும் ஊகமும்",
                blocks = listOf(
                    bp("A phylogeny is a hypothesis about the history of descent, and it is tested by evidence. The student should learn to ask of any statement about relationships: what is the evidence, how many independent kinds are there, and how well do they agree? Here the deuterostome position of the echinoderms is supported by development, by larval form and by molecular data. The claim of a sister relationship with the hemichordates is supported by the molecular data, and by some structural parallels, but the structures are not simple to interpret." to
                        "இனவரலாறு வழித்தோன்றல் வரலாறு பற்றிய கருதுகோள்; சான்றுகளால் சோதிக்கப்படுகிறது. உறவுகள் பற்றிய எந்தக் கூற்றிலும் மாணவர் கேட்கக் கற்க வேண்டும்: சான்று என்ன, எத்தனை தனி வகைகள் உள்ளன, அவை எவ்வளவு ஒத்துப்போகின்றன? இங்கு முட்தோலிகளின் டியூட்டிரோஸ்டோம் இடம் வளர்ச்சி, லார்வா வடிவம், மூலக்கூறுத் தரவால் ஆதரிக்கப்படுகிறது. ஹெமிகார்டேட்டுகளுடன் சகோதர உறவு என்ற கூற்று மூலக்கூறுத் தரவாலும் சில அமைப்பு இணைகளாலும் ஆதரிக்கப்படுகிறது; ஆனால் அமைப்புகளை விளக்குவது எளிதல்ல."),
                    bpoints("A checklist for statements about affinity", "உறவுமுறை பற்றிய கூற்றுகளுக்கான சரிபார்ப்புப் பட்டியல்",
                        "Prefer shared derived characters to shared appearance." to "பகிரப்பட்ட தோற்றத்தை விட பகிரப்பட்ட மாறிய பண்புகளை விரும்புக.",
                        "Look for agreement between development, structure and molecules." to "வளர்ச்சி, அமைப்பு, மூலக்கூறுகளிடையே ஒத்திசைவைத் தேடுக.",
                        "Say relative, sister group or common ancestor, and avoid saying that one living group gave rise to another." to "உறவினர், சகோதரக் குழு அல்லது பொது மூதாதை எனக் கூறுக; ஒரு வாழும் குழு மற்றொன்றைத் தோற்றுவித்தது எனக் கூறுவதைத் தவிர்க்க.",
                        "Mark the points which are still debated, and give the view as current." to "இன்னும் விவாதிக்கப்படும் புள்ளிகளைக் குறித்து, கருத்தை தற்போதையது எனத் தருக.",
                    ),
                    bp("A last warning is needed about the classic idea that the larvae of the echinoderms recall an ancestral form. The dipleurula is a model, and the modern larvae are the product of long evolution, and may be specialised for feeding in plankton. The larvae are informative, but they cannot be read as living fossils." to
                        "முட்தோலி லார்வாக்கள் மூதாதை வடிவை நினைவூட்டுகின்றன என்ற செம்மைக் கருத்து பற்றி கடைசி எச்சரிக்கை தேவை. டிப்ளூரூலா ஒரு மாதிரி; நவீன லார்வாக்கள் நீண்ட பரிணாமத்தின் விளைவு; மிதவையில் உண்பதற்குச் சிறப்படைந்திருக்கலாம். லார்வாக்கள் தகவல் தருகின்றன; ஆனால் அவற்றை வாழும் புதைபடிவங்களாகப் படிக்க முடியாது."),
                    bdef("Phylogeny", "இனவரலாறு", "The evolutionary history of descent of a group of organisms." to
                        "உயிரிகளின் குழுவின் பரிணாம வழித்தோன்றல் வரலாறு."),
                    bdef("Common ancestor", "பொது மூதாதை", "An ancestral population from which two or more groups are descended." to
                        "இரண்டு அல்லது அதற்கு மேற்பட்ட குழுக்கள் தோன்றிய மூதாதைய இனக்கூட்டம்."),
                )),
        ),
        summary = listOf(
            "Echinoderms are deuterostomes, on grounds of development, larval form and molecular data." to "வளர்ச்சி, லார்வா வடிவம், மூலக்கூறுத் தரவின் அடிப்படையில் முட்தோலிகள் டியூட்டிரோஸ்டோம்கள்.",
            "With the hemichordates they form the Ambulacraria, supported by several independent lines of evidence." to "ஹெமிகார்டேட்டுகளுடன் அவை பல தனிச் சான்றுகளால் ஆதரிக்கப்படும் அம்புலாக்ரேரியாவை உருவாக்குகின்றன.",
            "The Ambulacraria is the sister group of the Chordata; echinoderms are relatives, not ancestors, of vertebrates." to "அம்புலாக்ரேரியா முதுகுநாணிகளின் சகோதரக் குழு; முட்தோலிகள் முதுகெலும்பிகளின் உறவினர், மூதாதையர் அல்ல.",
            "Echinoderms lack the chordate synapomorphies: notochord and dorsal hollow nerve cord." to "முட்தோலிகளுக்கு முதுகுநாணிப் பகிரப்பட்ட பெறுபண்புகளான நாணும் முதுகுப்புற வெற்று நரம்பு வடமும் இல்லை.",
            "Adult radial symmetry is secondary and does not link echinoderms to cnidarians." to "முதிர்ந்த ஆரச்சமச்சீர் இரண்டாம் நிலை; முட்தோலிகளை குழியுடலிகளுடன் இணைக்காது.",
        ),
        checks = listOf(
            bqc("What is the Ambulacraria?", "அம்புலாக்ரேரியா என்றால் என்ன?",
                "The clade formed by the echinoderms and the hemichordates.", "முட்தோலிகளும் ஹெமிகார்டேட்டுகளும் உருவாக்கும் கிளை."),
            bqc("Are echinoderms the ancestors of the vertebrates?", "முட்தோலிகள் முதுகெலும்பிகளின் மூதாதையரா?",
                "No. They are relatives, and the Ambulacraria is the sister group of the chordates.", "இல்லை. அவை உறவினர்; அம்புலாக்ரேரியா முதுகுநாணிகளின் சகோதரக் குழு."),
            bqc("Name two chordate characters that echinoderms lack.", "முட்தோலிகளுக்கு இல்லாத இரு முதுகுநாணிப் பண்புகளைப் பெயரிடுக.",
                "A notochord and a dorsal hollow nerve cord.", "நாணும் முதுகுப்புற வெற்று நரம்பு வடமும்."),
            bqc("Why is radial symmetry a poor guide to relationship?", "ஆரச்சமச்சீர் உறவுக்கு ஏன் மோசமான வழிகாட்டி?",
                "Similar adult shape can arise by different routes, and in echinoderms it is secondary.", "ஒத்த முதிர்ந்த வடிவம் வெவ்வேறு வழிகளில் தோன்றலாம்; முட்தோலிகளில் அது இரண்டாம் நிலை."),
        ),
    )
}
