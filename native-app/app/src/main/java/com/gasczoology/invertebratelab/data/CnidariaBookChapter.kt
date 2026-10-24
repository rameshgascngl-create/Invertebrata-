package com.gasczoology.invertebratelab.data

/** Book-style chapter: Coelenterata (Cnidaria), general characters and classification. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object CnidariaBookChapter {
    val chapter = bchapter(
        lessonId = "u2-cnidaria",
        title = "Phylum Coelenterata (Cnidaria): general characters and classification" to
            "தொகுதி சீலென்டிரேட்டா (நிடேரியா): பொதுப்பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State the general characters of the phylum Coelenterata (Cnidaria)." to "சீலென்டிரேட்டா (நிடேரியா) தொகுதியின் பொதுப்பண்புகளைக் கூற.",
            "Describe the structure and discharge of a nematocyst and the types of cnidocyte." to "நெமட்டோசிஸ்டின் அமைப்பையும் வெளியேற்றத்தையும், நிடோசைட் வகைகளையும் விவரிக்க.",
            "Compare the polyp and the medusa in tabular form." to "பாலிப்பையும் மெடுசாவையும் அட்டவணை வடிவில் ஒப்பிட.",
            "Classify the phylum into Hydrozoa, Scyphozoa and Anthozoa with characters and examples." to "தொகுதியை ஹைட்ரோசோவா, சிஃபோசோவா, ஆந்தோசோவா வகுப்புகளாகப் பண்புகளுடனும் எடுத்துக்காட்டுகளுடனும் வகைப்படுத்த.",
            "Explain why the older name Coelenterata and the current name Cnidaria are both used." to "பழைய பெயரான சீலென்டிரேட்டாவும் தற்போதைய பெயரான நிடேரியாவும் ஏன் இரண்டும் பயன்படுகின்றன என்பதை விளக்க.",
        ),
        introduction = listOf(
            "The Cnidaria are the first animals in the sequence of this course to possess true tissues. Their bodies are built of two cell layers around a jelly-like middle layer, they have radial symmetry and a single opening to the gut, and they are armed with stinging cells that are found in no other animals. The group includes the freshwater Hydra, the colonial hydroids such as Obelia, the jellyfishes, the sea anemones and the reef-building corals." to
                "இந்தப் பாடத்தின் வரிசையில் உண்மையான திசுக்களைக் கொண்ட முதல் விலங்குகள் நிடேரியாக்கள். அவற்றின் உடல் ஜெல்லி போன்ற நடு அடுக்கைச் சுற்றி இரு செல் அடுக்குகளால் கட்டப்பட்டது; ஆரச்சமச்சீர், குடலுக்கு ஒரே திறப்பு கொண்டவை; வேறு எந்த விலங்கிலும் காணப்படாத கொட்டும் செல்களைக் கொண்டவை. நன்னீர் ஹைட்ரா, ஒபீலியா போன்ற காலனி ஹைட்ராய்டுகள், ஜெல்லிமீன்கள், கடல்சாமந்திகள், பவளப்பாறை உருவாக்கும் பவளங்கள் இக்குழுவில் அடங்கும்.",
            "The paper names the phylum Coelenterata, and the current scientific name is Cnidaria. This chapter uses both, as the syllabus does, and sets out the characters that define the group, the two body forms that run through all its life cycles, and the three classes into which it is divided." to
                "பாடத்திட்டம் தொகுதியை சீலென்டிரேட்டா என்று குறிப்பிடுகிறது; தற்போதைய அறிவியல் பெயர் நிடேரியா. பாடத்திட்டத்தைப் பின்பற்றி இந்த அத்தியாயம் இரண்டையும் பயன்படுத்துகிறது; குழுவை வரையறுக்கும் பண்புகள், எல்லா வாழ்க்கைச் சுழற்சிகளிலும் வரும் இரு உடல் வடிவங்கள், தொகுதி பிரியும் மூன்று வகுப்புகள் ஆகியவற்றை விவரிக்கிறது.",
        ),
        sections = listOf(
            bsec("name", "The name of the phylum", "தொகுதியின் பெயர்",
                blocks = listOf(
                    bp("The name Coelenterata, meaning hollow gut, was given to the group because the body is essentially a sac with one central cavity. The name Cnidaria, from the Greek word for a nettle, refers to the stinging cells that are the most distinctive feature of the group. The two names do not mean exactly the same thing, because the older name once included the comb jellies (Ctenophora), which are now placed in a separate phylum. Cnidaria is the name preferred in modern textbooks; Coelenterata is retained in syllabuses and in older books." to
                        "வெற்றுக் குடல் எனப் பொருள்படும் சீலென்டிரேட்டா என்ற பெயர், உடல் அடிப்படையில் ஒரே நடுக்குழி கொண்ட பை என்பதால் வழங்கப்பட்டது. கிரேக்க மொழியில் நெட்டில் செடியைக் குறிக்கும் சொல்லிலிருந்து வந்த நிடேரியா, குழுவின் மிகத் தனித்துவமான பண்பான கொட்டும் செல்களைக் குறிக்கிறது. இரு பெயர்களும் சரியாக ஒரே பொருளல்ல; பழைய பெயரில் முன்பு சீப்பு ஜெல்லிகள் (கடினோஃபோரா) அடங்கின; அவை இப்போது தனித் தொகுதியில் வைக்கப்படுகின்றன. நவீன நூல்கள் நிடேரியா என்பதை விரும்புகின்றன; பாடத்திட்டங்களிலும் பழைய நூல்களிலும் சீலென்டிரேட்டா நிலைத்துள்ளது."),
                    bdef("Coelenterata", "சீலென்டிரேட்டா (Coelenterata)",
                        "The older name of the phylum, referring to its single gut cavity; used in the syllabus alongside Cnidaria." to
                            "தொகுதியின் பழைய பெயர்; ஒற்றைக் குடல் குழியைக் குறிக்கிறது; பாடத்திட்டத்தில் நிடேரியாவுடன் பயன்படுகிறது."),
                    bdef("Cnidaria", "நிடேரியா",
                        "The current name of the phylum, from cnidocytes, the stinging cells that characterise it." to
                            "தொகுதியின் தற்போதைய பெயர்; அதன் பண்பான கொட்டும் செல்களான நிடோசைட்டுகளிலிருந்து வந்தது."),
                )),
            bsec("characters", "General characters", "பொதுப்பண்புகள்",
                blocks = listOf(
                    bp("The characters are best learned in groups: where cnidarians live, how their bodies are built, how they feed and digest, and how they reproduce." to
                        "பண்புகளைக் குழுக்களாகக் கற்பது சிறந்தது: நிடேரியாக்கள் எங்கு வாழ்கின்றன, உடல் எவ்வாறு கட்டப்பட்டுள்ளது, எவ்வாறு உண்டு செரிக்கின்றன, எவ்வாறு இனப்பெருக்கம் செய்கின்றன."),
                    bpoints("Habitat and symmetry", "வாழிடமும் சமச்சீரும்",
                        "Aquatic animals, nearly all marine; a few, such as Hydra, live in fresh water." to "நீர்வாழ் விலங்குகள், கிட்டத்தட்ட அனைத்தும் கடலில்; ஹைட்ரா போன்ற சிலவே நன்னீரில்.",
                        "Solitary or colonial, and attached (sessile) or free-swimming." to "தனித்தவை அல்லது காலனி; ஒட்டி வாழ்பவை அல்லது சுதந்திரமாக நீந்துபவை.",
                        "Radial or biradial symmetry, in which the parts are arranged around a central axis passing through the mouth." to "ஆரச்சமச்சீர் அல்லது இருபக்க ஆரச்சமச்சீர்; பாகங்கள் வாய் வழியே செல்லும் மைய அச்சைச் சுற்றி அமைந்துள்ளன.",
                    ),
                    bpoints("Body construction", "உடல் கட்டமைப்பு",
                        "Tissue grade of organisation: cells are organised into tissues but not into true organs or organ systems." to "திசு நிலை அமைப்பு: செல்கள் திசுக்களாக ஒழுங்கமைந்துள்ளன; உண்மையான உறுப்புகளாகவோ உறுப்பு மண்டலங்களாகவோ அல்ல.",
                        "Diploblastic: the body wall has an outer epidermis and an inner gastrodermis." to "ஈரடுக்கு உடலுடையவை: உடற்சுவரில் வெளி எப்பிடெர்மிஸ், உள் காஸ்ட்ரோடெர்மிஸ்.",
                        "Between the two layers lies the mesoglea, a jelly-like, non-cellular layer, which in some forms contains wandering cells." to "இரு அடுக்குகளுக்கிடையே மீசோகிளியா என்ற ஜெல்லி போன்ற செல்லற்ற அடுக்கு உள்ளது; சில வடிவங்களில் அதில் அலையும் செல்கள் உள்ளன.",
                        "Acoelomate: there is no body cavity other than the gut." to "உடற்குழியற்றவை: குடலைத் தவிர வேறு உடற்குழி இல்லை.",
                    ),
                    bpoints("Cnidocytes and the nervous system", "நிடோசைட்டுகளும் நரம்பு மண்டலமும்",
                        "Stinging cells (cnidocytes) with nematocysts, used to capture prey and for defence, are present, especially on the tentacles." to "நெமட்டோசிஸ்ட் உள்ள கொட்டும் செல்கள் (நிடோசைட்டுகள்) குறிப்பாகக் உணர்கொம்புகளில் உள்ளன; இரை பிடிக்கவும் தற்காப்புக்கும் பயன்படுகின்றன.",
                        "A nerve net of nerve cells lies at the base of the epidermis and often of the gastrodermis; there is no brain." to "நரம்பு செல்களின் நரம்பு வலை எப்பிடெர்மிஸ் அடியிலும் பெரும்பாலும் காஸ்ட்ரோடெர்மிஸ் அடியிலும் உள்ளது; மூளை இல்லை.",
                        "Epitheliomuscular cells provide a simple muscular system, and sense cells respond to touch and chemicals." to "எப்பிதீலியோதசை செல்கள் எளிய தசை அமைப்பைத் தருகின்றன; உணர் செல்கள் தொடுதலுக்கும் வேதிப்பொருட்களுக்கும் துலங்குகின்றன.",
                    ),
                    bpoints("Digestion and excretion", "செரிமானமும் வெளியேற்றமும்",
                        "The digestive cavity is a gastrovascular cavity (coelenteron) with a single opening, the mouth, which also serves as the anus." to "செரிமானக் குழி காஸ்ட்ரோவாஸ்குலர் குழி (சீலென்டிரான்); ஒரே திறப்பான வாய் மலவாயாகவும் செயல்படுகிறது.",
                        "Digestion is partly extracellular, in the cavity, and is completed inside the cells of the gastrodermis." to "செரிமானம் ஒரு பகுதி குழியில் செல்லுக்கு வெளியே நிகழ்ந்து, காஸ்ட்ரோடெர்மிஸ் செல்களினுள் முடிகிறது.",
                        "There are no excretory, respiratory or circulatory organs; gases and wastes diffuse through the body surface." to "கழிவு நீக்க, சுவாச, சுற்றோட்ட உறுப்புகள் இல்லை; வாயுக்களும் கழிவுகளும் உடல் பரப்பு வழியாக விரவுகின்றன.",
                    ),
                    bpoints("Body forms and reproduction", "உடல் வடிவங்களும் இனப்பெருக்கமும்",
                        "Two body forms occur: the polyp and the medusa; one or both may be present in the life cycle." to "இரு உடல் வடிவங்கள் உண்டு: பாலிப், மெடுசா; வாழ்க்கைச் சுழற்சியில் ஒன்று அல்லது இரண்டும் இருக்கலாம்.",
                        "Reproduction is asexual by budding, and sexual by gametes; the sexes are usually separate." to "மொட்டுவிடுவதால் பாலிலாவாகவும் கேமீட்டுகளால் பாலினமாகவும் இனப்பெருக்கம்; பாலினங்கள் பொதுவாகத் தனித்தனி.",
                        "The fertilised egg develops into a ciliated planula larva in most groups." to "கருவுற்ற முட்டை பெரும்பாலான குழுக்களில் கசையிழை பிளானுலா லார்வாவாக வளர்கிறது.",
                        "Alternation of generations occurs in many hydrozoans and in the scyphozoans, in which an asexual polyp stage alternates with a sexual medusa stage." to "பல ஹைட்ரோசோவாக்களிலும் சிஃபோசோவாக்களிலும் தலைமுறை மாற்றம் நிகழ்கிறது; பாலிலா பாலிப் நிலையும் பாலின மெடுசா நிலையும் மாறிமாறி வருகின்றன.",
                    ),
                    bdef("Diploblastic", "ஈரடுக்கு",
                        "Having a body built of two germ layers, ectoderm and endoderm, which form the epidermis and gastrodermis." to
                            "எக்டோடெர்ம், எண்டோடெர்ம் எனும் இரு கருப்படலங்களால் ஆன உடல் கொண்ட; இவை எப்பிடெர்மிஸ், காஸ்ட்ரோடெர்மிஸை உருவாக்குகின்றன."),
                    bdef("Mesoglea", "மீசோகிளியா",
                        "The jelly-like layer between the epidermis and gastrodermis of a cnidarian." to
                            "நிடேரியாவின் எப்பிடெர்மிஸ், காஸ்ட்ரோடெர்மிஸுக்கு இடையிலான ஜெல்லி போன்ற அடுக்கு."),
                    bdef("Gastrovascular cavity", "காஸ்ட்ரோவாஸ்குலர் குழி (Gastrovascular cavity)",
                        "The central cavity of a cnidarian, which both digests food and distributes it through the body." to
                            "உணவைச் செரித்து உடல் முழுவதும் பரப்பும் நிடேரியாவின் நடுக்குழி."),
                    bdef("Radial symmetry", "ஆரச்சமச்சீர்",
                        "Symmetry in which the body can be divided into similar halves by any plane that passes through the central axis." to
                            "மைய அச்சின் வழியே செல்லும் எந்தத் தளத்தாலும் உடலை ஒத்த பாதிகளாகப் பிரிக்கக்கூடிய சமச்சீர்."),
                )),
            bsec("body-wall", "The body wall", "உடற்சுவர்",
                blocks = listOf(
                    bp("The body wall is the key to the plan of the phylum. Its outer layer, the epidermis, is a covering epithelium that contains epitheliomuscular cells, sensory cells, nerve cells, gland cells, interstitial cells and the cnidocytes. The epitheliomuscular cells have contractile extensions at their bases, running along the length of the body, and by their action the animal shortens or bends." to
                        "உடற்சுவரே தொகுதியின் திட்டத்துக்கான திறவுகோல். அதன் வெளியடுக்கான எப்பிடெர்மிஸ் மூடும் எப்பிதீலியம்; அதில் எப்பிதீலியோதசை செல்கள், உணர் செல்கள், நரம்பு செல்கள், சுரப்பி செல்கள், இடைநிலை செல்கள், நிடோசைட்டுகள் உள்ளன. எப்பிதீலியோதசை செல்களின் அடிப்பகுதியில் உடலின் நீளவாக்கில் செல்லும் சுருங்கும் நீட்சிகள் உள்ளன; அவற்றின் செயலால் விலங்கு சுருங்குகிறது அல்லது வளைகிறது."),
                    bp("The inner layer, the gastrodermis, lines the gastrovascular cavity. Its nutritive-muscular cells engulf particles of food, and its gland cells discharge enzymes into the cavity. Between the two layers lies the mesoglea, which supports the body like a thin skeleton. In hydrozoans it is a thin layer without cells; in scyphozoans it is thick and contains wandering cells, and forms the main mass of the jellyfish." to
                        "உள்ளடுக்கான காஸ்ட்ரோடெர்மிஸ் காஸ்ட்ரோவாஸ்குலர் குழியை மூடுகிறது. அதன் ஊட்ட-தசை செல்கள் உணவுத் துகள்களை விழுங்குகின்றன; சுரப்பி செல்கள் நொதிகளைக் குழிக்குள் வெளியிடுகின்றன. இரு அடுக்குகளுக்கிடையே உள்ள மீசோகிளியா மெல்லிய எலும்புக்கூடுபோல் உடலைத் தாங்குகிறது. ஹைட்ரோசோவாக்களில் அது செல்களற்ற மெல்லிய அடுக்கு; சிஃபோசோவாக்களில் தடித்து அலையும் செல்களைக் கொண்டு ஜெல்லிமீனின் பெரும்பகுதியாக அமைகிறது."),
                    bpoints("Cell types of the epidermis", "எப்பிடெர்மிஸின் செல் வகைகள்",
                        "Epitheliomuscular cells: cover the body and contract." to "எப்பிதீலியோதசை செல்கள்: உடலை மூடி சுருங்குகின்றன.",
                        "Sensory and nerve cells: receive stimuli and form the nerve net." to "உணர், நரம்பு செல்கள்: தூண்டல்களைப் பெற்று நரம்பு வலையை உருவாக்குகின்றன.",
                        "Cnidocytes: capture prey and defend." to "நிடோசைட்டுகள்: இரை பிடித்துத் தற்காக்கின்றன.",
                        "Interstitial cells: small, undifferentiated cells that replace the others and form gametes in many hydrozoans." to "இடைநிலை செல்கள்: மற்றவற்றை மாற்றும் சிறிய வேறுபாடற்ற செல்கள்; பல ஹைட்ரோசோவாக்களில் கேமீட்டுகளை உருவாக்குகின்றன.",
                        "Gland cells: secrete mucus and, at the base, adhesive material." to "சுரப்பி செல்கள்: சளியையும், அடியில் ஒட்டும் பொருளையும் சுரக்கின்றன.",
                    ),
                    bdef("Epitheliomuscular cell", "எப்பிதீலியோதசை செல் (Epitheliomuscular cell)",
                        "A cell of the cnidarian epidermis that forms part of the covering epithelium and has a contractile base." to
                            "நிடேரியாவின் எப்பிடெர்மிஸில் மூடும் எப்பிதீலியத்தின் பகுதியாகவும், சுருங்கும் அடிப்பகுதி கொண்டதாகவும் உள்ள செல்."),
                    bdef("Interstitial cell", "இடைநிலை செல் (Interstitial cell)",
                        "A small, undifferentiated cell lying between the other cells, able to change into cnidocytes, nerve cells or gametes." to
                            "மற்ற செல்களுக்கிடையே உள்ள, நிடோசைட்டுகளாகவோ நரம்பு செல்களாகவோ கேமீட்டுகளாகவோ மாறக்கூடிய, சிறிய வேறுபாடற்ற செல்."),
                    bnote("Two layers, not three", "இரு அடுக்கு; மூன்று அல்ல",
                        "The mesoglea is not a germ layer. It is secreted by the two cell layers, which is why the animal is called diploblastic and not triploblastic." to
                            "மீசோகிளியா கருப்படலம் அல்ல; இரு செல் அடுக்குகளாலும் சுரக்கப்படுகிறது; எனவே விலங்கு இரு அடுக்குடையது (டிப்ளோபிளாஸ்டிக்) எனப்படுகிறது; மூன்று அடுக்குடையது அல்ல."),
                )),
            bsec("nematocyst","The cnidocyte and the nematocyst", "நிடோசைட்டும் நெமட்டோசிஸ்டும்",
                blocks = listOf(
                    bp("The cnidocyte is a specialised cell of the epidermis, and in some forms of the gastrodermis. Inside it is the nematocyst, a capsule in which a long, hollow thread is coiled. A small, hair-like trigger, the cnidocil, projects from the surface of the cell. When prey touches it, and chemical stimuli are also received, the capsule discharges: the thread is shot out inside-out, penetrating or entangling the prey, and in many types a toxin is injected." to
                        "நிடோசைட் எப்பிடெர்மிஸின், சில வடிவங்களில் காஸ்ட்ரோடெர்மிஸின், சிறப்பு செல். அதனுள் நெமட்டோசிஸ்ட் என்ற உறை உள்ளது; அதில் நீண்ட, குழாய் இழை சுருண்டுள்ளது. செல்லின் மேற்பரப்பில் நிடோசில் என்ற சிறு முடி போன்ற தூண்டுகோல் நீட்டிக்கொண்டுள்ளது. இரை அதைத் தொடும்போது, வேதித் தூண்டலும் பெறப்படும்போது, உறை வெளியேற்றுகிறது: இழை உள்ளிருந்து வெளியே திருப்பிச் சுடப்பட்டு, இரையைத் துளைக்கிறது அல்லது சுற்றி இறுக்குகிறது; பல வகைகளில் நஞ்சு செலுத்தப்படுகிறது."),
                    bp("A nematocyst discharges once only. The cnidocyte is then replaced by a new cell, formed from an interstitial cell, and the old one is lost. Several kinds of nematocyst occur. Penetrants pierce and inject a toxin; volvents coil around a part of the prey; glutinants stick to it. Spirocysts, which are found in anthozoans, are adhesive threads without a barb." to
                        "நெமட்டோசிஸ்ட் ஒருமுறை மட்டுமே வெளியேற்றுகிறது. அதன் பின் நிடோசைட் புதிய செல்லால் மாற்றப்படுகிறது; அது இடைநிலை செல்லிலிருந்து உருவாகிறது; பழையது இழக்கப்படுகிறது. பல வகை நெமட்டோசிஸ்ட்கள் உண்டு. துளைப்பவை துளைத்து நஞ்சு செலுத்துகின்றன; சுற்றுபவை இரையின் ஒரு பகுதியைச் சுற்றிக்கொள்கின்றன; ஒட்டுபவை அதில் ஒட்டிக்கொள்கின்றன. ஆந்தோசோவாக்களில் காணப்படும் ஸ்பைரோசிஸ்ட்கள் முள்ளற்ற ஒட்டும் இழைகள்."),
                    btable("Principal kinds of nematocyst", "நெமட்டோசிஸ்டின் முக்கிய வகைகள்",
                        listOf("Type" to "வகை", "Action" to "செயல்" ),
                        listOf(
                            listOf("Penetrant (stenotele)" to "துளைப்பது (ஸ்டெனோடீல்)", "Pierces the prey and injects a toxin" to "இரையைத் துளைத்து நஞ்சு செலுத்துகிறது"),
                            listOf("Volvent (desmoneme)" to "சுற்றுவது (டெஸ்மோனீம்)", "Coils around bristles or limbs of the prey" to "இரையின் முட்கள் அல்லது கால்களைச் சுற்றிக்கொள்கிறது"),
                            listOf("Glutinant (isorhiza)" to "ஒட்டுவது (ஐசோரைசா)", "Adheres to the prey by a sticky thread" to "ஒட்டும் இழையால் இரையில் ஒட்டிக்கொள்கிறது"),
                        )),
                    bdef("Cnidocyte", "நிடோசைட்",
                        "A stinging cell, characteristic of cnidarians, that contains a nematocyst." to
                            "நிடேரியாக்களின் பண்பான, நெமட்டோசிஸ்ட்டைக் கொண்ட கொட்டும் செல்."),
                    bdef("Nematocyst", "நெமட்டோசிஸ்ட்",
                        "A capsule inside a cnidocyte, containing a coiled thread that is discharged to capture prey or to defend the animal." to
                            "நிடோசைட்டினுள் உள்ள உறை; இரை பிடிக்க அல்லது தற்காப்புக்கு வெளியேற்றப்படும் சுருண்ட இழையைக் கொண்டது."),
                    bdef("Cnidocil", "நிடோசில் (Cnidocil)",
                        "The small trigger-like process on a cnidocyte that responds to touch and sets off the discharge." to
                            "தொடுதலுக்குத் துலங்கி வெளியேற்றத்தைத் தொடங்கும், நிடோசைட்டிலுள்ள தூண்டுகோல் போன்ற சிறு நீட்சி."),
                    bnote("Do not call it a sting from a gland", "சுரப்பியிலிருந்து வரும் கொட்டு எனக் கூற வேண்டாம்",
                        "The nematocyst is a capsule inside a single cell, and not a gland. In answers state that it is a cell organelle that discharges once and that the cell is then replaced." to
                            "நெமட்டோசிஸ்ட் ஒற்றைச் செல்லுக்குள் உள்ள உறை; சுரப்பி அல்ல. விடையில் அது ஒருமுறை வெளியேற்றும் செல் நுண்ணுறுப்பு என்றும், பின்னர் செல் மாற்றப்படும் என்றும் குறிப்பிடுக."),
                )),
            bsec("forms", "The polyp and the medusa", "பாலிப்பும் மெடுசாவும்",
                blocks = listOf(
                    bp("The two body forms of cnidarians are variations of one plan. The polyp is a cylinder, attached by its base, with the mouth and tentacles at the free end. The medusa may be thought of as a polyp turned upside down and flattened, so that the mouth is directed downwards, with a bell of mesoglea that makes it buoyant. Some cnidarians have only the polyp, some only the medusa, and many have both in a life cycle." to
                        "நிடேரியாக்களின் இரு உடல் வடிவங்கள் ஒரே திட்டத்தின் மாறுபாடுகள். பாலிப் அடியால் ஒட்டிய உருளை; வாயும் உணர்கொம்புகளும் தனித்த முனையில் இருக்கும். மெடுசாவை தலைகீழாகத் திருப்பி தட்டையாக்கிய பாலிப்பாகக் கருதலாம்; வாய் கீழ்நோக்கியிருக்கும்; மீசோகிளியாவால் ஆன மணி அதை மிதக்கச் செய்கிறது. சில நிடேரியாக்களுக்குப் பாலிப் மட்டுமே; சிலவற்றுக்கு மெடுசா மட்டுமே; பலவற்றின் வாழ்க்கைச் சுழற்சியில் இரண்டும் உள்ளன."),
                    btable("Comparison of polyp and medusa", "பாலிப், மெடுசா ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Polyp" to "பாலிப்", "Medusa" to "மெடுசா"),
                        listOf(
                            listOf("Shape" to "வடிவம்", "Cylindrical, tube-like" to "உருளை, குழாய் போன்றது", "Bell- or umbrella-shaped" to "மணி அல்லது குடை வடிவம்"),
                            listOf("Habit" to "வாழ்முறை", "Usually attached" to "பொதுவாக ஒட்டியது", "Usually free-swimming" to "பொதுவாக சுதந்திரமாக நீந்துவது"),
                            listOf("Mouth" to "வாய்", "Directed upwards" to "மேல்நோக்கி", "Directed downwards" to "கீழ்நோக்கி"),
                            listOf("Mesoglea" to "மீசோகிளியா", "Thin" to "மெல்லியது", "Thick and jelly-like" to "தடித்தது, ஜெல்லி போன்றது"),
                            listOf("Number of individuals" to "உயிரிகளின் எண்ணிக்கை", "Solitary or colonial" to "தனித்தது அல்லது காலனி", "Usually solitary" to "பொதுவாகத் தனித்தது"),
                            listOf("Reproduction" to "இனப்பெருக்கம்", "Commonly asexual by budding" to "பொதுவாக மொட்டு விடுதல் மூலம் பாலிலா", "Commonly sexual, forms gametes" to "பொதுவாகப் பாலினம்; கேமீட்டுகளை உருவாக்கும்"),
                        )),
                    bdef("Polyp", "பாலிப்",
                        "The attached, cylindrical, usually asexual body form of a cnidarian, with the mouth and tentacles at the free end." to
                            "நிடேரியாவின் ஒட்டிய, உருளை வடிவ, பொதுவாகப் பாலிலா உடல் வடிவம்; வாயும் உணர்கொம்புகளும் தனித்த முனையில்."),
                    bdef("Medusa", "மெடுசா",
                        "The free-swimming, bell-shaped, usually sexual body form of a cnidarian." to
                            "நிடேரியாவின் சுதந்திரமாக நீந்தும், மணி வடிவ, பொதுவாகப் பாலின உடல் வடிவம்."),
                    bdef("Alternation of generations", "தலைமுறை மாற்றம்",
                        "A life cycle in which an asexual stage, the polyp, and a sexual stage, the medusa, follow each other." to
                            "பாலிலா நிலையான பாலிப்பும் பாலின நிலையான மெடுசாவும் ஒன்றையொன்று தொடரும் வாழ்க்கைச் சுழற்சி."),
                    bnote("Forms of one life cycle", "ஒரே சுழற்சியின் வடிவங்கள்",
                        "The polyp and the medusa of a species are two forms of one life cycle. They are not two species." to
                            "ஓர் இனத்தின் பாலிப்பும் மெடுசாவும் ஒரே சுழற்சியின் இரு வடிவங்கள்; இரு இனங்கள் அல்ல."),
                )),
            bsec("nutrition", "Feeding and digestion", "உணவூட்டமும் செரிமானமும்",
                blocks = listOf(
                    bp("Cnidarians are carnivores. The tentacles, armed with nematocysts, catch small animals such as crustaceans and larvae of fishes, paralyse them and pass them to the mouth, which opens into the gastrovascular cavity. The same opening serves for the intake of food and for the discharge of the undigested remains, since the gut has only one opening. Digestion begins outside the cells (extracellular) in the cavity, where enzymes secreted by gland cells of the gastrodermis break the food into small pieces. The particles are then taken into the cells of the gastrodermis by phagocytosis and digestion is completed inside the cells (intracellular).",
                        "நிடேரியன்கள் ஊனுண்ணிகள். நெமட்டோசிஸ்ட்டுகள் கொண்ட உணர்கொம்புகள் கிரஸ்டேஷியன்கள், மீன் லார்வாக்கள் போன்ற சிறு விலங்குகளைப் பிடித்து, அவற்றை மரத்துப்போகச் செய்து, காஸ்ட்ரோவாஸ்குலர் குழியில் திறக்கும் வாய்க்குக் கொண்டு செல்கின்றன. குடலுக்கு ஒரே துளை இருப்பதால் அதே துளை உணவை உட்கொள்ளவும் செரிக்காத எச்சத்தை வெளியேற்றவும் பயன்படுகிறது. செரிமானம் குழியில் செல்களுக்கு வெளியே (புறச்செல்) தொடங்குகிறது; அங்கு காஸ்ட்ரோடெர்மிஸின் சுரப்பி செல்கள் சுரக்கும் நொதிகள் உணவைச் சிறு துண்டுகளாக்குகின்றன. பின்னர் துகள்கள் காஸ்ட்ரோடெர்மிஸ் செல்களுக்குள் செல்விழுங்கலால் எடுக்கப்பட்டு, செரிமானம் செல்களுக்குள் (அகச்செல்) முடிகிறது."),
                    bp("The cnidarians therefore show an intermediate stage between the entirely intracellular digestion of sponges and the entirely extracellular digestion of higher animals. The distribution of food through the body is by the gastrovascular cavity, which in large forms such as the jellyfish is extended into canals, and no circulatory or respiratory systems are needed because every cell is close to either the water or the cavity, and gases pass by diffusion.",
                        "இவ்வாறு நிடேரியன்கள் கடற்பஞ்சுகளின் முழுமையான அகச்செல் செரிமானத்துக்கும் உயர் விலங்குகளின் முழுமையான புறச்செல் செரிமானத்துக்கும் இடைப்பட்ட நிலையைக் காட்டுகின்றன. உடல் முழுவதும் உணவு பரவல் காஸ்ட்ரோவாஸ்குலர் குழி மூலம் நிகழ்கிறது; ஜெல்லிமீன் போன்ற பெரிய வடிவங்களில் அது கால்வாய்களாக நீள்கிறது; ஒவ்வொரு செல்லும் நீருக்கோ குழிக்கோ அருகிலிருப்பதால் சுற்றோட்ட, சுவாச மண்டலங்கள் தேவையில்லை; வாயுக்கள் பரவலால் கடக்கின்றன."),
                    bdef("Extracellular digestion", "புறச்செல் செரிமானம் (Extracellular digestion)", "Digestion that takes place in the cavity of the gut, outside the cells, by enzymes that the cells secrete." to
                        "செல்கள் சுரக்கும் நொதிகளால், செல்களுக்கு வெளியே குடல் குழியில் நிகழும் செரிமானம்."),
                    bdef("Intracellular digestion", "அகச்செல் செரிமானம் (Intracellular digestion)", "Digestion that takes place inside the cells, in food vacuoles, after the food particles have been engulfed." to
                        "உணவுத் துகள்கள் விழுங்கப்பட்ட பின் செல்களுக்குள், உணவுக் குமிழ்களில் நிகழும் செரிமானம்."),
                )),
            bsec("nerves", "Nerve net, movement and sense organs", "நரம்பு வலை, இயக்கம், உணர் உறுப்புகள்",
                blocks = listOf(
                    bp("The nervous system of a cnidarian is a nerve net, a network of nerve cells that lies at the base of the epidermis and the gastrodermis and is joined by synapses. There is no brain. A stimulus applied at any point spreads outward through the net in all directions, and so the animal responds as a whole. In many species there are also more rapid conducting paths, and the medusae have concentrations of nerve cells near the margin of the bell, which co-ordinate the swimming pulses.",
                        "நிடேரியனின் நரம்பு மண்டலம் நரம்பு வலை; எப்பிடெர்மிஸ், காஸ்ட்ரோடெர்மிஸ் அடிப்பகுதியில் அமைந்து சினாப்சுகளால் இணைக்கப்பட்ட நரம்பு செல்களின் வலைப்பின்னல். மூளை இல்லை. எந்தப் புள்ளியிலும் தரப்படும் தூண்டல் வலை வழியே எல்லாத் திசைகளிலும் பரவுகிறது; எனவே விலங்கு முழுமையாகப் பதிலளிக்கிறது. பல இனங்களில் வேகமான கடத்து வழிகளும் உள்ளன; மெடுசாக்களுக்கு மணியின் விளிம்பருகே நீந்தும் துடிப்புகளை ஒருங்கிணைக்கும் நரம்பு செல் குவிப்புகள் உள்ளன."),
                    bp("The medusae have the best developed sense organs in the phylum. In the scyphozoan jellyfish, groups of sense organs called rhopalia lie in notches at the edge of the bell, and each contains a statocyst for balance and, in many species, light-sensitive ocelli. In the box jellyfish (Cubozoa), the rhopalia include eyes with a lens, which are the most complex eyes found in any cnidarian. Polyps have simpler sense, chiefly the sensory cells of the tentacles that respond to touch and to chemicals from the prey.",
                        "மெடுசாக்களுக்கு இத்தொகுதியில் மிகச் சிறந்த உணர் உறுப்புகள் உள்ளன. சிஃபோசோவா ஜெல்லிமீன்களில் ரோபேலியா எனப்படும் உணர் உறுப்புத் தொகுதிகள் மணியின் விளிம்பிலுள்ள வெட்டுகளில் அமைந்துள்ளன; ஒவ்வொன்றிலும் சமநிலைக்கான ஸ்டேட்டோசிஸ்டும், பல இனங்களில் ஒளியுணர் ஓசெல்லிகளும் உள்ளன. பெட்டி ஜெல்லிமீன்களில் (கியூபோசோவா) ரோபேலியாவில் லென்ஸ் கொண்ட கண்கள் உள்ளன; எந்த நிடேரியனிலும் காணப்படும் மிகச் சிக்கலான கண்கள் இவையே. பாலிப்புகளுக்கு எளிய உணர்வு; முக்கியமாக தொடுதலுக்கும் இரையின் வேதிப்பொருட்களுக்கும் பதிலளிக்கும் உணர்கொம்புகளின் உணர் செல்கள்."),
                    bdef("Nerve net", "நரம்பு வலை", "A diffuse network of nerve cells without a brain, found in cnidarians, through which a stimulus spreads in all directions." to
                        "மூளையின்றி நிடேரியன்களில் காணப்படும், தூண்டல் எல்லாத் திசைகளிலும் பரவும் பரவலான நரம்பு செல் வலை."),
                    bdef("Rhopalium", "ரோபேலியம் (Rhopalium)", "A marginal sense organ of a scyphozoan or cubozoan medusa, containing a statocyst and often light-sensitive cells." to
                        "சிஃபோசோவா அல்லது கியூபோசோவா மெடுசாவின் விளிம்பு உணர் உறுப்பு; ஸ்டேட்டோசிஸ்டும் பெரும்பாலும் ஒளியுணர் செல்களும் கொண்டது."),
                )),
            bsec("reproduction", "Reproduction, regeneration and symbiosis", "இனப்பெருக்கம், மீளுருவாக்கம், கூட்டுயிரி வாழ்க்கை",
                blocks = listOf(
                    bp("Cnidarians reproduce asexually by budding, in which a part of the body wall grows out and develops into a new individual, which may separate, as in Hydra, or remain attached to form a colony, as in Obelia. They reproduce sexually by gametes, and many species are dioecious. The fertilised egg develops into a ciliated, free-swimming larva, the planula, which is solid or hollow and has two layers. It settles, and develops into a polyp. Cnidarians also regenerate well, since the interstitial cells can replace the lost tissue.",
                        "நிடேரியன்கள் மொட்டு விடுதல் மூலம் பாலிலா முறையில் இனப்பெருக்குகின்றன; உடற்சுவரின் ஒரு பகுதி வெளி வளர்ந்து ஹைட்ராவில் போல பிரியலாம் அல்லது ஒபீலியாவில் போல இணைந்திருந்து காலனி உருவாக்கலாம். இனச் செல்கள் மூலம் பால் முறையிலும் இனப்பெருக்குகின்றன; பல இனங்கள் ஒருபாலிகள். கருவுற்ற முட்டை, சிலியா கொண்ட நீந்தும், திடமான அல்லது உள்ளீடற்ற இரு அடுக்கு லார்வாவான பிளானுலாவாக வளர்கிறது. அது படிந்து பாலிப்பாக வளர்கிறது. இடைநிலை செல்கள் இழந்த திசுவை மாற்றுவதால் நிடேரியன்கள் நன்கு மீளுருவாக்குகின்றன."),
                    bp("Many cnidarians live in partnership with algae. The green hydra Hydra viridis contains single-celled green algae in its gastrodermis, and reef-building corals and many sea anemones harbour zooxanthellae. The alga receives shelter and nutrients from the animal, and the animal receives food from the alga. Some sea anemones also live with certain fishes, such as the clownfish, which shelter among the tentacles and are protected from the stings by a mucus coat. These examples are used to illustrate symbiosis, a close association between two kinds of organism.",
                        "பல நிடேரியன்கள் பாசிகளுடன் கூட்டுறவில் வாழ்கின்றன. பசுமை ஹைட்ரா ஹைட்ரா விரிடிஸின் காஸ்ட்ரோடெர்மிஸில் ஒற்றைச் செல் பச்சைப் பாசிகள் உள்ளன; முகடு கட்டும் பவளங்களும் பல கடல் சாமந்திகளும் சூக்சாந்தெல்லேயைக் கொண்டுள்ளன. பாசி விலங்கிடமிருந்து உறைவிடமும் ஊட்டச்சத்தும், விலங்கு பாசியிடமிருந்து உணவும் பெறுகின்றன. சில கடல் சாமந்திகள் கோமாளி மீன் போன்ற சில மீன்களுடனும் வாழ்கின்றன; அவை உணர்கொம்புகளிடையே தங்கி, சளிப் பூச்சால் கொட்டுதலிலிருந்து பாதுகாக்கப்படுகின்றன. இவ்வெடுத்துக்காட்டுகள் இரு வகை உயிரிகளுக்கிடையிலான நெருக்கமான தொடர்பான கூட்டுயிரி வாழ்க்கையை விளக்கப் பயன்படுகின்றன."),
                    bdef("Planula", "பிளானுலா", "The ciliated, free-swimming larva of a cnidarian, which settles and develops into a polyp." to
                        "நிடேரியனின் சிலியா கொண்ட நீந்தும் லார்வா; படிந்து பாலிப்பாக வளர்கிறது."),
                    bdef("Symbiosis", "கூட்டுயிரி வாழ்க்கை (Symbiosis)", "A close and lasting association between two different kinds of organism." to
                        "இரு வெவ்வேறு வகை உயிரிகளுக்கிடையிலான நெருக்கமான நீடித்த தொடர்பு."),
                )),
            bsec("answering", "Answering questions on the phylum", "தொகுதி குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("Examination questions on the phylum ask for the general characters, the nematocyst, the polyp and medusa forms, and the classification. A good answer on the general characters groups the points under symmetry and layers (radial, diploblastic), body cavity and gut (gastrovascular, one opening), the stinging cells, nerve net, and the two forms with the alternation of generations. A question on the polyp and medusa should state that the two forms are variations of one body plan, the medusa being in effect an upside-down polyp with a greatly thickened mesoglea, and it should include a labelled diagram of each.",
                        "தொகுதி குறித்த தேர்வு வினாக்கள் பொதுப்பண்புகள், நெமட்டோசிஸ்ட், பாலிப், மெடுசா வடிவங்கள், வகைப்பாடு ஆகியவற்றைக் கேட்கின்றன. பொதுப்பண்புகள் குறித்த நல்ல விடை புள்ளிகளை சமச்சீரும் அடுக்குகளும் (ஆரம், ஈரடுக்கு), உடற்குழியும் குடலும் (காஸ்ட்ரோவாஸ்குலர், ஒரே துளை), கொட்டும் செல்கள், நரம்பு வலை, தலைமுறை மாற்றத்துடன் இரு வடிவங்கள் என குழுவாக்குகிறது. பாலிப், மெடுசா குறித்த வினா இரு வடிவங்களும் ஒரே உடல் திட்டத்தின் வேறுபாடுகள் என்றும், மெடுசா மிகத் தடித்த மீசோக்ளியாவுடன் தலைகீழான பாலிப் என்றும் கூற வேண்டும்; ஒவ்வொன்றுக்கும் பெயரிட்ட படம் இருக்க வேண்டும்."),
                    bpoints("Common errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Calling the cnidarians triploblastic or giving them an anus." to "நிடேரியன்களை மூவடுக்கு என்றோ குதம் உடையவை என்றோ கூறுதல்.",
                        "Saying that the nematocyst is a cell; it is the capsule within the cnidocyte." to "நெமட்டோசிஸ்ட் செல் என்று கூறுதல்; அது நிடோசைட்டுக்குள் உள்ள காப்ஸ்யூல்.",
                        "Giving Aurelia a dominant polyp; in Aurelia the medusa is the conspicuous stage." to "ஆரீலியாவில் ஆதிக்கமுள்ள பாலிப் என்று தருதல்; ஆரீலியாவில் மெடுசாவே மிகத் தெரியும் நிலை.",
                        "Attributing a brain or a circulatory system to the phylum." to "தொகுதிக்கு மூளையோ சுற்றோட்ட மண்டலமோ உண்டு எனக் கூறுதல்.",
                    ),
                )),
            bsec("classification", "Classification into classes", "வகுப்புகளாக வகைப்படுத்துதல்",
                blocks = listOf(
                    bp("The paper requires classification of the phylum up to classes. Three classes are recognised in the syllabus: Hydrozoa, Scyphozoa and Anthozoa. Modern classifications also recognise further groups, such as the Cubozoa (box jellies), but the three-class scheme is that on which the paper is set and it is followed here." to
                        "வினாத்தாள் தொகுதியை வகுப்புகள் வரை வகைப்படுத்தக் கேட்கிறது. பாடத்திட்டத்தில் மூன்று வகுப்புகள் அங்கீகரிக்கப்படுகின்றன: ஹைட்ரோசோவா, சிஃபோசோவா, ஆந்தோசோவா. நவீன வகைப்பாடுகள் கியூபோசோவா (பெட்டி ஜெல்லிகள்) போன்ற மேலும் குழுக்களையும் ஏற்கின்றன; ஆனால் தாள் அமைந்துள்ள மூன்று வகுப்பு முறையே இங்கு பின்பற்றப்படுகிறது."),
                    btable("Classes of the phylum compared", "தொகுதியின் வகுப்புகள் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Hydrozoa" to "ஹைட்ரோசோவா", "Scyphozoa" to "சிஃபோசோவா", "Anthozoa" to "ஆந்தோசோவா"),
                        listOf(
                            listOf("Dominant form" to "முதன்மை வடிவம்", "Polyp (often colonial)" to "பாலிப் (பெரும்பாலும் காலனி)", "Medusa" to "மெடுசா", "Polyp only" to "பாலிப் மட்டும்"),
                            listOf("Medusa" to "மெடுசா", "With velum" to "வெலத்துடன்", "Without velum" to "வெலம் இல்லை", "Absent" to "இல்லை"),
                            listOf("Mesoglea" to "மீசோகிளியா", "Without cells" to "செல் இல்லை", "With cells" to "செல்களுடன்", "With cells" to "செல்களுடன்"),
                            listOf("Gonads" to "இனப்பெருக்கச் சுரப்பிகள்", "Epidermal" to "எப்பிடெர்மிஸில்", "Gastrodermal" to "காஸ்ட்ரோடெர்மிஸில்", "Gastrodermal" to "காஸ்ட்ரோடெர்மிஸில்"),
                            listOf("Examples" to "எடுத்துக்காட்டுகள்", "Hydra, Obelia, Physalia" to "ஹைட்ரா, ஒபீலியா, பைசாலியா", "Aurelia, Rhizostoma" to "ஆரீலியா, ரைசோஸ்டோமா", "Metridium, Gorgonia, Madrepora" to "மெட்ரீடியம், கோர்கோனியா, மேட்ரபோரா"),
                        )),
                ),
                subs = listOf(
                    bsub("Class Hydrozoa", "வகுப்பு ஹைட்ரோசோவா",
                        bp("Mostly small, often colonial, and marine, though Hydra is a freshwater member. The polyp is the dominant stage in many, and the medusa, where present, has a shelf-like velum and is without a gastric partition. The mesoglea has no cells. The gonads are epidermal. Examples are Hydra, Obelia, Physalia and Velella." to
                            "பெரும்பாலும் சிறியவை, அடிக்கடி காலனி, கடல் வாழ்பவை; ஹைட்ரா நன்னீர் உறுப்பினர். பலவற்றில் பாலிப் முதன்மை நிலை; மெடுசா இருக்குமிடத்து அலமாரி போன்ற வெலம் உண்டு, இரைப்பை பிரிவுகள் இல்லை. மீசோகிளியாவில் செல்கள் இல்லை. இனப்பெருக்கச் சுரப்பிகள் எப்பிடெர்மிஸில் உள்ளன. எடுத்துக்காட்டுகள்: ஹைட்ரா, ஒபீலியா, பைசாலியா, வெலெல்லா."),
                    ),
                    bsub("Class Scyphozoa", "வகுப்பு சிஃபோசோவா",
                        bp("The true jellyfishes. The medusa is the dominant stage and the polyp is small and short-lived, or absent. The mesoglea is thick and contains cells. The gonads are gastrodermal, and the gastric cavity has four pouches with gastric filaments. Examples are Aurelia, Rhizostoma and Cassiopeia." to
                            "உண்மையான ஜெல்லிமீன்கள். மெடுசா முதன்மை நிலை; பாலிப் சிறியது, குறுகிய ஆயுளுடையது அல்லது இல்லை. மீசோகிளியா தடித்து செல்களைக் கொண்டுள்ளது. இனப்பெருக்கச் சுரப்பிகள் காஸ்ட்ரோடெர்மிஸில் உள்ளன; இரைப்பைக் குழியில் இரைப்பை இழைகளுடன் நான்கு பைகள் உண்டு. எடுத்துக்காட்டுகள்: ஆரீலியா, ரைசோஸ்டோமா, காசியோபியா."),
                    ),
                    bsub("Class Anthozoa", "வகுப்பு ஆந்தோசோவா",
                        bp("The sea anemones and corals. Only the polyp is present; the medusa is wholly absent. The polyp is more complex than that of hydrozoans, with a pharynx and internal partitions (mesenteries) that divide the gastrovascular cavity. Solitary or colonial. Examples are Metridium, Gorgonia, Pennatula, and the reef corals such as Madrepora." to
                            "கடல்சாமந்திகளும் பவளங்களும். பாலிப் மட்டுமே உண்டு; மெடுசா முற்றிலும் இல்லை. பாலிப் ஹைட்ரோசோவாக்களைவிடச் சிக்கலானது; தொண்டையும், காஸ்ட்ரோவாஸ்குலர் குழியைப் பிரிக்கும் உள் பிரிவுகளும் (மெசென்டரிகள்) உண்டு. தனித்தவை அல்லது காலனி. எடுத்துக்காட்டுகள்: மெட்ரீடியம், கோர்கோனியா, பென்னாட்டுலா, மேட்ரபோரா போன்ற பவளப்பாறை பவளங்கள்."),
                    ),
                )),
            bsec("importance", "The place of the group in the animal kingdom", "விலங்கு உலகில் இக்குழுவின் இடம்",
                blocks = listOf(
                    bp("Cnidarians show the first division of labour among cells that is organised into tissues. The nerve net, the epitheliomuscular cells and the cnidocytes are simple versions of a nervous system, muscles and weapons, and the gastrovascular cavity is a gut which digests partly outside the cells. They are thus a useful illustration of how a more complex animal may be built from a more elementary one." to
                        "திசுக்களாக ஒழுங்கமைந்த செல்களிடையேயான பணிப் பிரிவினையை நிடேரியாக்கள் முதன்முதலாகக் காட்டுகின்றன. நரம்பு வலை, எப்பிதீலியோதசை செல்கள், நிடோசைட்டுகள் ஆகியவை நரம்பு மண்டலம், தசைகள், ஆயுதங்களின் எளிய வடிவங்கள்; காஸ்ட்ரோவாஸ்குலர் குழி ஓரளவு செல்லுக்கு வெளியே செரிக்கும் குடல். எளிய விலங்கிலிருந்து சிக்கலான விலங்கு எவ்வாறு கட்டமைக்கப்படலாம் என்பதற்கு இவை பயனுள்ள எடுத்துக்காட்டு."),
                    bp("Their importance outside the laboratory is also large. Reef-building corals form the structures that shelter a great part of the animals of the sea, and the group includes the dangerous box jellies and the Portuguese man-of-war. The next chapters treat the colonial hydroid Obelia, the corals and polymorphism." to
                        "ஆய்வகத்துக்கு வெளியிலும் இவற்றின் முக்கியத்துவம் பெரிது. பவளப்பாறை உருவாக்கும் பவளங்கள் கடலின் விலங்குகளில் பெரும்பகுதிக்கு அடைக்கலம் தரும் அமைப்புகளை உருவாக்குகின்றன; ஆபத்தான பெட்டி ஜெல்லிகளும் போர்த்துக்கீசிய போர்க்கப்பல் மீனும் இக்குழுவில் உள்ளன. அடுத்த அத்தியாயங்கள் காலனி ஹைட்ராய்டான ஒபீலியா, பவளங்கள், பல்வடிவத்தன்மை ஆகியவற்றைக் கையாளுகின்றன."),
                )),
        ),
        summary = listOf(
            "Cnidaria (Coelenterata) are radially symmetrical, diploblastic, acoelomate animals of the tissue grade with a gastrovascular cavity and one opening." to "நிடேரியா (சீலென்டிரேட்டா) ஆரச்சமச்சீர், இரு அடுக்கு, உடற்குழியற்ற, திசு நிலை விலங்குகள்; காஸ்ட்ரோவாஸ்குலர் குழியும் ஒரே திறப்பும் கொண்டவை.",
            "A jelly-like mesoglea lies between the epidermis and the gastrodermis." to "எப்பிடெர்மிஸுக்கும் காஸ்ட்ரோடெர்மிஸுக்கும் இடையே ஜெல்லி போன்ற மீசோகிளியா உள்ளது.",
            "Cnidocytes with nematocysts are the diagnostic feature; a nematocyst discharges once and is replaced." to "நெமட்டோசிஸ்ட் உள்ள நிடோசைட்டுகளே அடையாளப் பண்பு; நெமட்டோசிஸ்ட் ஒருமுறை வெளியேற்றி மாற்றப்படுகிறது.",
            "There is a nerve net but no brain, and no excretory, respiratory or circulatory organs." to "நரம்பு வலை உண்டு; மூளையும் கழிவு, சுவாச, சுற்றோட்ட உறுப்புகளும் இல்லை.",
            "The polyp is attached and mouth-up; the medusa is free-swimming and mouth-down; they are two forms of one life cycle." to "பாலிப் ஒட்டியது, வாய் மேல்நோக்கியது; மெடுசா நீந்துவது, வாய் கீழ்நோக்கியது; இவை ஒரே சுழற்சியின் இரு வடிவங்கள்.",
            "Hydrozoa are usually polyp-dominant, Scyphozoa medusa-dominant, and Anthozoa have only polyps." to "ஹைட்ரோசோவா பொதுவாகப் பாலிப் முதன்மை, சிஃபோசோவா மெடுசா முதன்மை, ஆந்தோசோவாவில் பாலிப் மட்டும்.",
            "Examples: Hydra and Obelia (Hydrozoa), Aurelia (Scyphozoa), Metridium and corals (Anthozoa)." to "எடுத்துக்காட்டுகள்: ஹைட்ரா, ஒபீலியா (ஹைட்ரோசோவா); ஆரீலியா (சிஃபோசோவா); மெட்ரீடியம், பவளங்கள் (ஆந்தோசோவா).",
        ),
        checks = listOf(
            bqc("What does a nematocyst do?", "நெமட்டோசிஸ்ட் என்ன செய்கிறது?",
                "It fires a thread to capture prey or to defend the animal.", "இரை பிடிக்க அல்லது தற்காப்புக்கு ஓர் இழையைச் சுடுகிறது."),
            bqc("Name the three classes on the paper.", "தாளில் உள்ள மூன்று வகுப்புகளைக் குறிப்பிடுக.",
                "Hydrozoa, Scyphozoa and Anthozoa.", "ஹைட்ரோசோவா, சிஃபோசோவா, ஆந்தோசோவா."),
            bqc("Which class has no medusa?", "எந்த வகுப்பில் மெடுசா இல்லை?",
                "Anthozoa.", "ஆந்தோசோவா."),
            bqc("Why are the polyp and medusa of Obelia not separate species?", "ஒபீலியாவின் பாலிப்பும் மெடுசாவும் ஏன் தனித்தனி இனங்கள் அல்ல?",
                "They are two forms in the life cycle of one species: the polyp produces medusae asexually, and the medusa produces gametes.", "அவை ஒரே இனத்தின் வாழ்க்கைச் சுழற்சியின் இரு வடிவங்கள்: பாலிப் மெடுசாக்களைப் பாலிலா முறையில் உருவாக்குகிறது; மெடுசா கேமீட்டுகளை உருவாக்குகிறது."),
        ),
    )
}
