package com.gasczoology.invertebratelab.data

/** Book-style chapter: economic importance of corals and reefs. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object CoralEconomicsBookChapter {
    val chapter = bchapter(
        lessonId = "u2-coral-economics",
        title = "Economic importance of corals and coral reefs" to "பவளங்கள், பவளப்பாறைகளின் பொருளாதார முக்கியத்துவம்",
        objectives = listOf(
            "List the services that living coral reefs provide to people and explain how each is supplied." to "உயிருள்ள பவளப்பாறைகள் மக்களுக்கு வழங்கும் சேவைகளைப் பட்டியலிட்டு, ஒவ்வொன்றும் எவ்வாறு வழங்கப்படுகிறது என்பதை விளக்க.",
            "Distinguish services that come from a living reef from products that are got by extraction." to "உயிருள்ள முகடு தரும் சேவைகளை, பிரித்தெடுப்பால் பெறப்படும் பொருட்களிலிருந்து வேறுபடுத்த.",
            "Explain why damage to a reef is much faster than its recovery." to "முகட்டுச் சேதம் மீட்சியைவிட மிக வேகமாக நிகழ்வது ஏன் என்பதை விளக்க.",
            "Describe the main human causes of reef damage." to "முகட்டுச் சேதத்தின் முக்கிய மனிதக் காரணங்களை விவரிக்க.",
            "Outline the principal measures for the protection of reefs." to "முகடுகளைப் பாதுகாக்கும் முக்கிய நடவடிக்கைகளை வரைய.",
        ),
        introduction = listOf(
            "A coral reef is valued by the people who live beside it in many ways. It yields fish, it protects the shore, it draws visitors, and it supplies materials. A distinction needs to be drawn at the outset between two different kinds of value. Some arise from the living reef, which can continue to supply them for ever if it is not harmed. Others are obtained by taking the reef to pieces, and these destroy the source of the first kind." to
                "முகட்டின் அருகே வாழும் மக்கள் அதைப் பல வகையில் மதிக்கின்றனர். மீன் தருகிறது, கரையைக் காக்கிறது, பார்வையாளர்களை ஈர்க்கிறது, பொருட்களை வழங்குகிறது. தொடக்கத்திலேயே இரு வேறு வகை மதிப்புகளை வேறுபடுத்த வேண்டும். சில உயிருள்ள முகட்டிலிருந்து எழுபவை; அது பாதிக்கப்படாவிட்டால் என்றென்றும் வழங்கக்கூடியவை. மற்றவை முகட்டைத் துண்டாக எடுப்பதால் பெறப்படுபவை; இவை முதல் வகையின் மூலத்தையே அழிக்கின்றன.",
            "This chapter sets out the services of a living reef, the products of extraction, the reasons why a reef is easily damaged and slowly restored, and the means by which it may be protected. Together with the preceding chapter on the biology of corals, it completes the treatment of the Anthozoa." to
                "இந்த அத்தியாயம் உயிருள்ள முகட்டின் சேவைகள், பிரித்தெடுப்புப் பொருட்கள், முகடு எளிதில் சேதமடைந்து மெதுவாக மீள்வதற்கான காரணங்கள், அதைப் பாதுகாக்கும் வழிகளை விவரிக்கிறது. பவளங்களின் உயிரியல் குறித்த முந்தைய அத்தியாயத்துடன் சேர்ந்து ஆந்தோசோவா பற்றிய கையாளலை நிறைவு செய்கிறது.",
        ),
        sections = listOf(
            bsec("services", "Services of the living reef", "உயிருள்ள முகட்டின் சேவைகள்",
                subs = listOf(
                    bsub("Fisheries and food", "மீன்பிடியும் உணவும்",
                        bp("A reef provides shelter, breeding grounds and feeding grounds for a very large number of fishes, crustaceans, molluscs and other animals, and many of the fish that are caught in the open sea spend part of their lives on the reef. Reef fisheries support both commercial fishing and the subsistence fishing of coastal communities, and in many tropical countries fish from the reef are a main source of protein for people living near the coast." to
                            "முகடு மிகப் பெரிய எண்ணிக்கையிலான மீன்கள், ஓட்டுடலிகள், மெல்லுடலிகள், பிற விலங்குகளுக்கு அடைக்கலம், இனப்பெருக்கம், உணவு இடங்களைத் தருகிறது; திறந்த கடலில் பிடிக்கப்படும் பல மீன்கள் தங்கள் வாழ்வின் ஒரு பகுதியை முகட்டில் கழிக்கின்றன. முகட்டு மீன்பிடி வணிக மீன்பிடிக்கும் கடலோர சமூகங்களின் வாழ்வாதார மீன்பிடிக்கும் ஆதரவளிக்கிறது; பல வெப்ப மண்டல நாடுகளில் முகட்டு மீன்கள் கடலோர மக்களின் முதன்மைப் புரத மூலம்."),
                        bdef("Nursery ground", "வளர்ப்பிடம்", "An area in which the young of a species grow in shelter before they join the adult population elsewhere." to
                            "ஒரு இனத்தின் இளம் உயிரிகள் வேறிடத்தில் வளர்ந்தவற்றுடன் சேரும் முன் பாதுகாப்பில் வளரும் பகுதி."),
                    ),
                    bsub("Protection of the shore", "கரைப் பாதுகாப்பு",
                        bp("A reef is a natural breakwater. As waves cross the reef crest much of their energy is dissipated, and the shore behind the reef is exposed to smaller waves. Reefs thereby reduce erosion, flooding and storm damage, and the lagoon behind a reef gives sheltered water for harbours and for fishing boats. The sand of many tropical beaches is itself produced by the breakdown of coral and calcareous algae. When a reef dies and its structure erodes, this protection is lost." to
                            "முகடு இயற்கை அலைத்தடுப்பு. அலைகள் முகட்டு உச்சியைக் கடக்கும்போது அவற்றின் ஆற்றலில் பெரும்பகுதி சிதறடிக்கப்படுகிறது; முகட்டுக்குப் பின்னுள்ள கரை சிறிய அலைகளுக்கு மட்டும் வெளிப்படுகிறது. இதனால் முகடுகள் அரிப்பு, வெள்ளம், புயல் சேதத்தைக் குறைக்கின்றன; முகட்டுக்குப் பின்னுள்ள கடற்காயல் துறைமுகங்கள், மீன்பிடிப் படகுகளுக்குப் பாதுகாப்பான நீரை வழங்குகிறது. பல வெப்ப மண்டலக் கடற்கரை மணல் பவளமும் சுண்ணாம்புப் பாசியும் உடைவதாலேயே உருவாகிறது. முகடு இறந்து அதன் கட்டமைப்பு அரிக்கப்படும்போது இப்பாதுகாப்பு இழக்கப்படுகிறது."),
                    ),
                    bsub("Tourism and recreation", "சுற்றுலாவும் பொழுதுபோக்கும்",
                        bp("The scenery and the variety of life on a reef attract divers, snorkellers and visitors in boats with glass bottoms, and tourism based on reefs supports hotels, guides, boat operators and many other forms of local employment and income. Because a tourist comes to see a living reef, this is a use that rewards the protection of the reef and not its removal." to
                            "முகட்டின் காட்சியும் உயிரினப் பன்மையும் மூழ்குபவர்களையும், மூச்சுக்குழாய் நீச்சலர்களையும், கண்ணாடி அடிப்பகுதிப் படகுகளில் வருபவர்களையும் ஈர்க்கின்றன; முகடு சார்ந்த சுற்றுலா விடுதிகள், வழிகாட்டிகள், படகு இயக்குபவர்கள், பிற பல உள்ளூர் வேலைவாய்ப்பு, வருமான வடிவங்களுக்கு ஆதரவளிக்கிறது. சுற்றுலாப் பயணி உயிருள்ள முகட்டைக் காண வருவதால், இது முகட்டை அகற்றுவதை அல்லாமல் அதைப் பாதுகாப்பதைப் பரிசளிக்கும் பயன்பாடு."),
                    ),
                    bsub("Science, medicine and culture", "அறிவியல், மருத்துவம், பண்பாடு",
                        bp("Reef organisms, especially sponges, soft corals and tunicates, make a wide range of chemical compounds for defence, and some of these are the subject of research for drugs against infection and cancer. Reefs are used for teaching and for research on symbiosis, calcification and the effects of climate. In many coastal cultures reefs have a place in tradition and in the identity of the people. These values are difficult to measure in money, but they are real." to
                            "முகட்டு உயிரிகள், குறிப்பாக கடற்பஞ்சுகள், மென்பவளங்கள், டியூனிகேட்டுகள், தற்காப்புக்காக பலவகை வேதிச் சேர்மங்களை உருவாக்குகின்றன; இவற்றில் சில தொற்று, புற்றுநோய்க்கு எதிரான மருந்துகளுக்கான ஆய்வின் பொருள். முகடுகள் கற்பித்தலுக்கும், கூட்டுயிர், சுண்ணமாக்கம், காலநிலை விளைவுகள் குறித்த ஆய்வுக்கும் பயன்படுகின்றன. பல கடலோரப் பண்பாடுகளில் முகடுகளுக்கு மரபிலும் மக்கள் அடையாளத்திலும் இடம் உண்டு. இம்மதிப்புகளைப் பணத்தில் அளவிடுவது கடினம்; ஆனால் அவை உண்மையானவை."),
                        bdef("Ecosystem service", "சூழல்மண்டல சேவை", "A benefit that people obtain from a natural system, such as food, protection from storms or recreation." to
                            "உணவு, புயல் பாதுகாப்பு, பொழுதுபோக்கு போன்று இயற்கை அமைப்பிலிருந்து மக்கள் பெறும் பயன்."),
                    ),
                )),
            bsec("extraction", "Products obtained by extraction", "பிரித்தெடுப்பால் பெறப்படும் பொருட்கள்",
                blocks = listOf(
                    bp("Coral skeletons have been taken from reefs for a long time. Dead and living coral has been quarried for building blocks and burnt in kilns to make lime for cement and for whitewash; the whole skeletons of corals and the shells of reef animals are sold as ornaments and curios; and living corals, fishes and invertebrates are collected for aquaria. Black coral and precious red coral are used in jewellery." to
                        "பவள எலும்புக்கூடுகள் நீண்ட காலமாக முகடுகளிலிருந்து எடுக்கப்படுகின்றன. இறந்த, உயிருள்ள பவளம் கட்டுமானக் கற்களுக்காகக் குவாரி செய்யப்பட்டு, சிமெண்ட், வெள்ளையடிப்புக்கான சுண்ணாம்புக்காக சூளைகளில் எரிக்கப்படுகிறது; முழு பவள எலும்புக்கூடுகளும் முகட்டு விலங்குகளின் ஓடுகளும் அலங்காரப் பொருட்களாக விற்கப்படுகின்றன; உயிருள்ள பவளங்கள், மீன்கள், முதுகெலும்பிலிகள் மீன்தொட்டிகளுக்காக சேகரிக்கப்படுகின்றன. கருப்புப் பவளமும் விலையுயர்ந்த சிவப்புப் பவளமும் நகைகளில் பயன்படுகின்றன."),
                    bp("The cost of this extraction is that the reef itself is lost. A reef that is quarried no longer protects the shore, no longer shelters fish, and no longer attracts visitors, and the income from the blocks or the lime is small in comparison with the long-term value of the services that are lost. For this reason extraction of coral from reefs is restricted by law in India, where corals are protected under the Wildlife (Protection) Act, 1972, and collection and trade are forbidden. The schedule and the current provisions are for the reviewer to confirm." to
                        "இப்பிரித்தெடுப்பின் விலை முகடே இழக்கப்படுவது. குவாரி செய்யப்பட்ட முகடு இனி கரையைக் காக்காது, மீனுக்கு அடைக்கலம் தராது, பார்வையாளர்களை ஈர்க்காது; கற்கள் அல்லது சுண்ணாம்பிலிருந்து வரும் வருமானம் இழக்கப்படும் சேவைகளின் நீண்டகால மதிப்புடன் ஒப்பிட மிகச் சிறியது. இதனால் இந்தியாவில் முகடுகளிலிருந்து பவளம் எடுப்பது சட்டத்தால் கட்டுப்படுத்தப்படுகிறது; வனவிலங்கு (பாதுகாப்பு) சட்டம், 1972-இன் கீழ் பவளங்கள் பாதுகாக்கப்படுகின்றன; சேகரிப்பும் வணிகமும் தடை செய்யப்பட்டுள்ளன. அட்டவணையும் தற்போதைய விதிகளும் மதிப்பாய்வாளர் உறுதிப்படுத்த வேண்டியவை."),
                    btable("Services and extraction compared", "சேவைகளும் பிரித்தெடுப்பும் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Services of the living reef" to "உயிருள்ள முகட்டின் சேவைகள்", "Extraction" to "பிரித்தெடுப்பு"),
                        listOf(
                            listOf("Examples" to "எடுத்துக்காட்டுகள்", "Fisheries, shore protection, tourism" to "மீன்பிடி, கரைப் பாதுகாப்பு, சுற்றுலா", "Lime, building blocks, curios" to "சுண்ணாம்பு, கட்டுமானக் கற்கள், அலங்காரப் பொருட்கள்"),
                            listOf("Effect on reef" to "முகட்டின் மீது விளைவு", "Reef remains intact" to "முகடு சேதமின்றி இருக்கும்", "Reef is damaged or destroyed" to "முகடு சேதமடைகிறது அல்லது அழிகிறது"),
                            listOf("Duration" to "கால அளவு", "Continues for as long as the reef lives" to "முகடு உயிருடன் இருக்கும் வரை தொடர்கிறது", "One-time gain, then loss of the source" to "ஒருமுறை ஆதாயம்; பின் மூலத்தை இழப்பு"),
                        )),
                    bnote("Living service first", "முதலில் உயிருள்ள சேவை",
                        "In an answer on economic importance, begin with the services that depend on a living reef. Extraction is then described as a use that removes the capital that produces those services." to
                            "பொருளாதார முக்கியத்துவம் குறித்த விடையில் உயிருள்ள முகடு சார்ந்த சேவைகளுடன் தொடங்குக. பின்னர் அச்சேவைகளை உருவாக்கும் மூலதனத்தையே அகற்றும் பயன்பாடாக பிரித்தெடுப்பை விவரிக்க."),
                )),
            bsec("capital", "Growth as slow capital", "மெதுவான மூலதனமாக வளர்ச்சி",
                blocks = listOf(
                    bp("An economist would describe a reef as natural capital: a stock which produces a flow of benefits. Capital of this kind is built up slowly. The skeleton of a massive coral grows by a few millimetres to a few centimetres in a year, and a reef that has taken centuries or millennia to form can be broken in a short time. Damage therefore comes much faster than repair, which is the point on which examination answers on the economics of reefs generally turn." to
                        "பொருளாதார அறிஞர் முகட்டை இயற்கை மூலதனமாக விவரிப்பார்: பயன்களின் ஓட்டத்தை உருவாக்கும் இருப்பு. இத்தகைய மூலதனம் மெதுவாகக் கட்டப்படுகிறது. பெரிய பவளத்தின் எலும்புக்கூடு ஆண்டுக்கு சில மில்லிமீட்டர் முதல் சில சென்டிமீட்டர் வளர்கிறது; பல நூற்றாண்டுகள் அல்லது ஆயிரமாண்டுகள் எடுத்த முகட்டை குறுகிய காலத்தில் உடைக்க முடியும். எனவே சேதம் பழுதுபார்ப்பைவிட மிக வேகமாக வருகிறது; முகடுகளின் பொருளாதாரம் பற்றிய தேர்வு விடைகள் பொதுவாகத் திரும்புவது இந்தப் புள்ளியிலேயே."),
                    bdef("Natural capital", "இயற்கை மூலதனம்", "A stock of natural resources, such as a reef, that yields a flow of useful services over time." to
                        "காலப்போக்கில் பயனுள்ள சேவைகளின் ஓட்டத்தைத் தரும் முகடு போன்ற இயற்கை வளங்களின் இருப்பு."),
                    bp("Recovery requires a chain of events, each of which takes time: coral colonies must survive; larvae must be supplied from other reefs by the currents; the larvae must settle in a suitable place; the young polyps must survive the first years, in which most die; and they must grow through repeated seasons. Where the stress comes again before this has been done, as with repeated bleaching or repeated storms, the losses add up faster than the reef can rebuild." to
                        "மீட்சிக்குத் தொடர் நிகழ்வுகள் தேவை; ஒவ்வொன்றும் நேரம் எடுக்கும்: பவளக் காலனிகள் உயிர் பிழைக்க வேண்டும்; லார்வாக்கள் பிற முகடுகளிலிருந்து நீரோட்டத்தால் வந்து சேர வேண்டும்; அவை தகுந்த இடத்தில் படிய வேண்டும்; இளம் பாலிப்புகள் பெரும்பாலானவை இறக்கும் முதல் ஆண்டுகளைத் தாண்ட வேண்டும்; மீண்டும் மீண்டும் பருவங்களில் வளர வேண்டும். இது நிறைவேறும் முன் மீண்டும் மீண்டும் வெளிறல் அல்லது புயல்கள் போல அழுத்தம் மீண்டும் வந்தால், முகடு மீளக் கட்டும் வேகத்தைவிட இழப்புகள் வேகமாகக் கூடுகின்றன."),
                )),
            bsec("damage", "Causes of damage", "சேதத்தின் காரணங்கள்",
                blocks = listOf(
                    bpoints("Principal causes of damage to reefs", "முகடுகளுக்குச் சேதத்தின் முதன்மைக் காரணங்கள்",
                        "Heat stress, which causes mass bleaching over large areas and, if prolonged, widespread death of corals." to "வெப்ப அழுத்தம்; பெரும்பரப்பில் பெருமளவு வெளிறலையும், நீடித்தால் பரவலான பவள இறப்பையும் ஏற்படுத்துகிறது.",
                        "Blast fishing, in which explosives shatter the reef to stun fish; poisons such as cyanide used to capture fish for aquaria." to "வெடி மீன்பிடி; மீன்களை மயக்க வெடிபொருட்கள் முகட்டை நொறுக்குகின்றன; மீன்தொட்டிகளுக்கு மீன் பிடிக்கச் சயனைடு போன்ற நஞ்சுகள்.",
                        "Mining of coral for lime and building material, and damage by boat anchors and by trampling." to "சுண்ணாம்பு, கட்டுமானப் பொருளுக்காகப் பவளம் வெட்டுதல்; படகு நங்கூரங்களாலும் மிதிப்பாலும் ஏற்படும் சேதம்.",
                        "Pollution and sediment from land, including sewage and fertiliser, which shade the corals and feed seaweeds that overgrow them." to "நிலத்திலிருந்து வரும் மாசும் வண்டலும்; கழிவுநீர், உரம் உட்பட; அவை பவளங்களுக்கு நிழலிட்டு, அவற்றை மூடும் கடற்பாசிகளுக்கு உணவாகின்றன.",
                        "Severe storms, which physically break colonies, and outbreaks of predators of corals." to "பவளக் காலனிகளை உடைக்கும் கடும் புயல்கள்; பவளம் உண்ணும் வேட்டையாடிகளின் பெருக்கம்.",
                    ),
                    bp("Several of these causes act together. A reef weakened by pollution is less able to withstand a hot summer, and a reef broken by blast fishing is slow to be recolonised. The causes that are local, such as fishing methods and pollution, can be dealt with by the people who live beside the reef. Those that are global, such as warming of the sea, can be reduced only by action beyond the local reef." to
                        "இக்காரணங்களில் பலவும் சேர்ந்து செயல்படுகின்றன. மாசால் பலவீனமான முகடு வெப்பமான கோடையைத் தாங்கும் திறன் குறைவு; வெடி மீன்பிடியால் உடைந்த முகடு மீண்டும் குடியேற மெதுவாகிறது. மீன்பிடி முறைகள், மாசு போன்ற உள்ளூர் காரணங்களை முகட்டருகே வாழ்பவர்கள் சமாளிக்க முடியும். கடல் வெப்பமடைதல் போன்ற உலகளாவியவற்றை உள்ளூர் முகட்டுக்கு அப்பாற்பட்ட நடவடிக்கையால் மட்டுமே குறைக்க முடியும்."),
                    bdef("Blast fishing", "வெடி மீன்பிடி", "The use of explosives to kill or stun fish, which also shatters the coral." to
                        "மீன்களைக் கொல்ல அல்லது மயக்க வெடிபொருட்களைப் பயன்படுத்துதல்; பவளத்தையும் நொறுக்குகிறது."),
                )),
            bsec("valuing", "Valuing a reef", "முகட்டுக்கு மதிப்பிடுதல்",
                blocks = listOf(
                    bp("The values of a reef are of three kinds, which differ in how easily they are measured. Direct use values, such as the weight of fish landed or the number of visitors, can be counted and priced. Indirect use values, such as the protection which a reef gives to the shore, are not sold, but can be estimated by the cost of building a sea wall to do the same work. Non-use values, such as the satisfaction people feel in knowing that a reef exists, are the hardest to measure." to
                        "முகட்டின் மதிப்புகள் மூன்று வகை; எவ்வளவு எளிதில் அளவிடலாம் என்பதில் வேறுபடுகின்றன. இறக்கிய மீன் எடை, பார்வையாளர் எண்ணிக்கை போன்ற நேரடிப் பயன் மதிப்புகளை எண்ணி விலையிட முடியும். முகடு கரைக்குத் தரும் பாதுகாப்பு போன்ற மறைமுகப் பயன் மதிப்புகள் விற்கப்படுவதில்லை; ஆனால் அதே பணியைச் செய்யும் கடல்சுவர் கட்டும் செலவால் மதிப்பிடலாம். முகடு இருப்பதை அறிவதில் மக்கள் உணரும் மனநிறைவு போன்ற பயன்படுத்தாத மதிப்புகளை அளவிடுவது மிகக் கடினம்."),
                    bp("A valuation made only from the direct, sold products undervalues a reef, because most of its worth is in services that do not pass through a market. This is one reason why reefs have been mined for the sake of a small and immediate profit." to
                        "நேரடியான, விற்கப்படும் பொருட்களை மட்டும் கொண்டு செய்யப்படும் மதிப்பீடு முகட்டுக்குக் குறைவாக மதிப்பிடுகிறது; ஏனெனில் அதன் பெரும்பகுதி மதிப்பு சந்தையின் வழியே செல்லாத சேவைகளில் உள்ளது. சிறிய, உடனடி இலாபத்துக்காக முகடுகள் வெட்டப்பட்டதற்கு இதுவும் ஒரு காரணம்."),
                    bdef("Breakwater", "அலைத்தடுப்பு", "A barrier, natural or built, that reduces the force of waves reaching the shore." to
                        "கரையை அடையும் அலைகளின் விசையைக் குறைக்கும், இயற்கை அல்லது கட்டிய தடுப்பு."),
                    bdef("Subsistence fishing", "வாழ்வாதார மீன்பிடி", "Fishing to feed the fisher's own family and community rather than mainly to sell." to
                        "முக்கியமாக விற்பதற்காக அல்லாமல், மீனவரின் குடும்பத்துக்கும் சமூகத்துக்கும் உணவளிக்கச் செய்யும் மீன்பிடி."),
                    bdef("Sedimentation", "வண்டல் படிவு", "The settling of silt and other particles on the bottom, which can smother corals." to
                        "கடலடியில் வண்டலும் பிற துகள்களும் படிதல்; பவளங்களை மூடிவிடக்கூடும்."),
                    bdef("Marine protected area", "கடல் பாதுகாக்கப்பட்ட பகுதி", "An area of the sea in which human activities are limited by law to protect the living things and habitats." to
                        "உயிரினங்களையும் வாழிடங்களையும் காக்க மனித செயல்பாடுகள் சட்டத்தால் வரையறுக்கப்பட்ட கடல் பகுதி."),
                )),
            bsec("answering", "Answering questions on reef economics", "முகட்டுப் பொருளாதாரம் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("Questions on this topic usually ask for the economic and ecological importance of reefs, or for the reasons why damage is faster than recovery. In the first case the answer is best organised under the headings of this chapter: the fisheries and nursery grounds, the protection of the shore, tourism, the contribution to science and medicine, and the contrast with extraction. In the second case it is the slowness of growth, the chain of steps in recovery, and the speed of destruction by heat, blasting, mining and storms that carry the marks." to
                        "இத்தலைப்பின் வினாக்கள் பொதுவாக முகடுகளின் பொருளாதார, சூழலியல் முக்கியத்துவத்தையோ, சேதம் மீட்சியைவிட வேகமாக இருப்பதற்கான காரணங்களையோ கேட்கும். முதல் வினாவுக்கு இவ்வத்தியாய தலைப்புகளில் விடையை அமைப்பது சிறந்தது: மீன்பிடியும் வளர்ப்பிடங்களும், கரைப் பாதுகாப்பு, சுற்றுலா, அறிவியல், மருத்துவத்துக்கு பங்களிப்பு, பிரித்தெடுப்புடன் முரண். இரண்டாவது வினாவுக்கு வளர்ச்சியின் மெதுவான தன்மை, மீட்சியின் படிநிலைத் தொடர், வெப்பம், வெடிப்பு, வெட்டுதல், புயல்களால் அழிவின் வேகம் ஆகியவை மதிப்பெண் பெறுகின்றன."),
                    bpoints("Points that earn marks", "மதிப்பெண் தரும் குறிப்புகள்",
                        "State the living services first and the extraction second." to "உயிருள்ள சேவைகளை முதலிலும் பிரித்தெடுப்பை இரண்டாவதிலும் கூறுக.",
                        "Give the mechanism of shore protection: wave energy is dissipated on the reef." to "கரைப் பாதுகாப்பின் வழிமுறையைத் தருக: அலை ஆற்றல் முகட்டில் சிதறடிக்கப்படுகிறது.",
                        "Name at least three causes of damage, and distinguish local from global ones." to "குறைந்தது மூன்று சேதக் காரணங்களைக் குறிப்பிட்டு, உள்ளூர், உலகளாவியவற்றை வேறுபடுத்துக.",
                        "List the stages of recovery: survival, larval supply, settlement, recruitment and growth." to "மீட்சியின் நிலைகளை எழுதுக: உயிர்பிழைப்பு, லார்வா வழங்கல், படிதல், சேர்க்கை, வளர்ச்சி.",
                        "Do not give figures for money value unless you can cite the source." to "மூலத்தை மேற்கோள் காட்ட முடியாதவரை பண மதிப்பு எண்களைத் தர வேண்டாம்.",
                    ),
                )),
            bsec("protection", "Protection and management", "பாதுகாப்பும் மேலாண்மையும்",
                blocks = listOf(
                    bpoints("Measures for the protection of reefs", "முகடுகளைப் பாதுகாக்கும் நடவடிக்கைகள்",
                        "Legal protection of corals and the prohibition of mining, blast fishing and poisons." to "பவளங்களுக்குச் சட்டப் பாதுகாப்பும், வெட்டுதல், வெடி மீன்பிடி, நஞ்சுகளுக்குத் தடையும்.",
                        "Marine protected areas, such as the Gulf of Mannar Marine National Park and Biosphere Reserve, in which the reef is managed." to "மன்னார் வளைகுடா கடல் தேசியப் பூங்கா, உயிர்க்கோளக் காப்பகம் போன்ற கடல் பாதுகாப்புப் பகுதிகளில் முகடு மேலாண்மை.",
                        "Control of pollution and sediment from the land, and of anchoring and tourist activity." to "நிலத்திலிருந்து வரும் மாசு, வண்டல், நங்கூரமிடல், சுற்றுலா செயல்பாடு ஆகியவற்றைக் கட்டுப்படுத்துதல்.",
                        "Alternative livelihoods for people who now depend on extraction, such as seaweed culture and managed tourism." to "இப்போது பிரித்தெடுப்பைச் சார்ந்தவர்களுக்கு கடற்பாசி வளர்ப்பு, மேலாண்மை செய்யப்பட்ட சுற்றுலா போன்ற மாற்று வாழ்வாதாரங்கள்.",
                        "Monitoring of bleaching and of the health of the reef, and restoration, where it is possible, by transplanting coral fragments." to "வெளிறலையும் முகட்டு நலத்தையும் கண்காணித்தல்; இயலுமிடத்தில் பவளத் துண்டுகளை மாற்றிநட்டு மீட்டமைத்தல்.",
                        "Education of the people who live near reefs and of visitors." to "முகடுகளுக்கு அருகில் வாழும் மக்களுக்கும் பார்வையாளர்களுக்கும் கல்வி.",
                    ),
                    bp("The measures are most effective when they are combined, and when the people who live beside the reef take part in them and gain from them. Restoration by transplanting is expensive and slow, and it cannot replace protection of a reef that is still alive." to
                        "நடவடிக்கைகள் இணைக்கப்படும்போதும், முகட்டருகே வாழ்பவர்கள் அவற்றில் பங்கேற்று பயன்பெறும்போதும் மிகவும் திறனுடையவை. மாற்றிநடுதல் மூலம் மீட்டமைத்தல் செலவு மிக்கதும் மெதுவானதும்; உயிருடன் இருக்கும் முகட்டைப் பாதுகாப்பதற்கு அது மாற்றாகாது."),
                    bnote("The last word", "இறுதிக் கருத்து",
                        "A coral reef pays continuously when it is left alive, and pays once when it is mined. This is the economic argument that supports every conservation measure, and it is the point to put at the end of an essay." to
                            "உயிருடன் விடப்பட்ட முகடு தொடர்ந்து பயன் தருகிறது; வெட்டப்பட்டால் ஒருமுறை மட்டுமே. ஒவ்வொரு பாதுகாப்பு நடவடிக்கையையும் தாங்கும் பொருளாதார வாதம் இதுவே; கட்டுரையின் முடிவில் இடவேண்டிய கருத்தும் இதுவே."),
                )),
        ),
        summary = listOf(
            "A living reef provides fisheries, nursery grounds, protection of the shore, tourism, and material for research, medicine and culture." to "உயிருள்ள முகடு மீன்பிடி, வளர்ப்பிடங்கள், கரைப் பாதுகாப்பு, சுற்றுலா, ஆய்வு, மருத்துவம், பண்பாட்டுக்கான பொருள் ஆகியவற்றை வழங்குகிறது.",
            "Reefs dissipate wave energy and so reduce erosion, flooding and storm damage." to "முகடுகள் அலை ஆற்றலைச் சிதறடித்து அரிப்பு, வெள்ளம், புயல் சேதத்தைக் குறைக்கின்றன.",
            "Extraction, for lime and building material or curios, destroys the capital which produces these services." to "சுண்ணாம்பு, கட்டுமானப் பொருள், அலங்காரப் பொருட்களுக்கான பிரித்தெடுப்பு இச்சேவைகளை உருவாக்கும் மூலதனத்தை அழிக்கிறது.",
            "Reefs grow slowly and are a form of natural capital." to "முகடுகள் மெதுவாக வளர்கின்றன; இயற்கை மூலதனத்தின் வடிவம்.",
            "Damage, by heat stress, blast fishing, mining, pollution and storms, is much faster than recovery, which needs survival, larval supply, settlement and growth." to "வெப்ப அழுத்தம், வெடி மீன்பிடி, வெட்டுதல், மாசு, புயல்களால் ஏற்படும் சேதம், உயிர்பிழைப்பு, லார்வா வழங்கல், படிதல், வளர்ச்சி தேவைப்படும் மீட்சியைவிட மிக வேகமானது.",
            "Protection combines law, protected areas, pollution control, alternative livelihoods and monitoring." to "பாதுகாப்பு சட்டம், பாதுகாக்கப்பட்ட பகுதிகள், மாசுக் கட்டுப்பாடு, மாற்று வாழ்வாதாரங்கள், கண்காணிப்பை இணைக்கிறது.",
        ),
        checks = listOf(
            bqc("Name two services of a living coral reef.", "உயிருள்ள பவளப்பாறையின் இரு சேவைகளைக் குறிப்பிடுக.",
                "Shore protection and fisheries; tourism is a third.", "கரைப் பாதுகாப்பு, மீன்பிடி; சுற்றுலா மூன்றாவது."),
            bqc("Why does mining of coral cost more than it earns?", "பவளம் வெட்டுவது ஏன் ஈட்டுவதைவிட அதிகம் செலவாகிறது?",
                "It destroys the reef that gives shore protection, fisheries and tourism, whose long-term value exceeds the income from the blocks or lime.", "கரைப் பாதுகாப்பு, மீன்பிடி, சுற்றுலாவைத் தரும் முகட்டை அது அழிக்கிறது; அவற்றின் நீண்டகால மதிப்பு கற்கள் அல்லது சுண்ணாம்பு வருமானத்தைவிட அதிகம்."),
            bqc("Why can reef damage occur much faster than recovery?", "முகட்டுச் சேதம் மீட்சியைவிட மிக வேகமாக நிகழ்வது ஏன்?",
                "Reefs grow slowly and recovery needs survival, larval supply, settlement and growth, while destruction can be sudden and stress may recur before recovery.", "முகடுகள் மெதுவாக வளர்கின்றன; மீட்சிக்கு உயிர்பிழைப்பு, லார்வா வழங்கல், படிதல், வளர்ச்சி தேவை; அழிவு திடீரென நிகழலாம்; மீட்சிக்கு முன் அழுத்தம் மீண்டும் வரலாம்."),
            bqc("Give one local and one global cause of reef damage.", "முகட்டுச் சேதத்தின் ஓர் உள்ளூர், ஒரு உலகளாவிய காரணம் தருக.",
                "Local: blast fishing or pollution. Global: warming of the sea.", "உள்ளூர்: வெடி மீன்பிடி அல்லது மாசு. உலகளாவியது: கடல் வெப்பமடைதல்."),
        ),
    )
}
