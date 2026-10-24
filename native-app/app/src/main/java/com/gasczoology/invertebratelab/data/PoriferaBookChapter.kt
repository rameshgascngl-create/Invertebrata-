package com.gasczoology.invertebratelab.data

/** Book-style chapter: Phylum Porifera. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PoriferaBookChapter {
    val chapter = bchapter(
        lessonId = "u2-porifera",
        title = "Phylum Porifera: general characters and classification" to
            "துளையுடலிகள் தொகுதி: பொதுப் பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State what a sponge is and explain the meaning of the name Porifera." to
                "கடற்பஞ்சு என்றால் என்ன என்பதைக் கூறி, துளையுடலிகள் என்ற பெயரின் பொருளை விளக்க.",
            "List the general characters of the phylum." to
                "தொகுதியின் பொதுப் பண்புகளைப் பட்டியலிட.",
            "Describe the cell types of a sponge and the function of each." to
                "கடற்பஞ்சின் செல் வகைகளையும் அவற்றின் பணிகளையும் விவரிக்க.",
            "Compare the skeletons of the three main classes and classify sponges up to class with examples." to
                "மூன்று முதன்மை வகுப்புகளின் எலும்புக்கூட்டை ஒப்பிட்டு, எடுத்துக்காட்டுகளுடன் வகுப்புவரை வகைப்படுத்த.",
            "Explain the cellular basis of regeneration in sponges." to
                "கடற்பஞ்சுகளில் மீளுருவாக்கத்தின் செல்மட்ட அடிப்படையை விளக்க.",
        ),
        introduction = listOf(
            "Sponges look so little like animals that they were long classed as plants. They do not move, they have no head, mouth or nerves, and a living specimen may look like a lump of rock or a mat on a shell. Their animal nature was settled only when naturalists in the early nineteenth century, among them Robert Grant, observed the currents of water that pass through them and saw that sponges feed on particles carried by that water." to
                "கடற்பஞ்சுகள் விலங்குகளைப் போலவே தோன்றாததால் நீண்ட காலம் தாவரங்களாகவே கருதப்பட்டன. அவை நகர்வதில்லை; தலை, வாய், நரம்புகள் இல்லை; உயிருள்ள மாதிரி பாறைத் துண்டுபோலவோ ஓட்டின் மீதுள்ள படலம்போலவோ தோன்றலாம். பத்தொன்பதாம் நூற்றாண்டின் தொடக்கத்தில் ராபர்ட் கிராண்ட் உள்ளிட்ட இயற்கை ஆய்வாளர்கள் அவற்றினூடே நீரோட்டம் செல்வதைக் கண்டு, அந்த நீருடன் வரும் துகள்களையே அவை உண்கின்றன என்பதை அறிந்த பின்னரே அவற்றின் விலங்குத் தன்மை உறுதியானது.",
            "This chapter introduces the phylum as a whole. It states the general characters, describes the cells and the skeleton on which the classification rests, and then classifies sponges up to class. The type study Sycon, the canal systems and reproduction are treated in the chapters that follow." to
                "இந்த அத்தியாயம் தொகுதியை முழுமையாக அறிமுகப்படுத்துகிறது. பொதுப் பண்புகளைக் கூறி, வகைப்பாட்டின் அடிப்படையான செல்களையும் எலும்புக்கூட்டையும் விவரித்து, வகுப்புவரை வகைப்படுத்துகிறது. இன ஆய்வான சைக்கான், கால்வாய் அமைப்புகள், இனப்பெருக்கம் ஆகியவை பின்வரும் அத்தியாயங்களில் விளக்கப்படுகின்றன.",
        ),
        sections = listOf(
            bsec("meaning", "The name and the animal", "பெயரும் விலங்கும்",
                blocks = listOf(
                    bp("The name Porifera comes from the Latin words porus, a pore, and ferre, to bear, and means pore-bearing. It describes the most obvious feature of the body: it is perforated by countless small pores through which water enters, and by one or more larger openings through which water leaves." to
                        "துளையுடலிகள் என்ற பெயர் லத்தீன் சொற்களான porus (துளை), ferre (தாங்குதல்) என்பவற்றிலிருந்து வந்தது; துளை தாங்குவது என்று பொருள். உடலின் மிகத் தெளிவான பண்பை இது குறிக்கிறது: எண்ணற்ற சிறு துளைகள் வழியாக நீர் உள்ளே வருகிறது; ஒன்று அல்லது அதற்கு மேற்பட்ட பெரிய துளைகள் வழியாக வெளியேறுகிறது."),
                    bdef("Porifera", "துளையுடலிகள்",
                        "The phylum of aquatic, mostly marine and sessile animals whose body is perforated by pores and traversed by a system of canals and chambers through which water is driven. The cells are organised at the cellular grade and do not form true organs." to
                            "துளைகள் நிறைந்த உடலும் நீர் செலுத்தப்படும் கால்வாய், அறை அமைப்பும் கொண்ட, பெரும்பாலும் கடல்வாழ், நிலைத்த விலங்குகளின் தொகுதி; செல்கள் செல்மட்ட அமைப்பில் உள்ளன, உண்மையான உறுப்புகளை உருவாக்குவதில்லை."),
                    bdef("Aquiferous system", "நீர்வழி அமைப்பு (Aquiferous system)",
                        "The system of ostia, canals, chambers and oscula through which a sponge passes a current of water for feeding, gas exchange and removal of waste." to
                            "உணவூட்டம், வாயுப் பரிமாற்றம், கழிவு நீக்கம் ஆகியவற்றுக்காக கடற்பஞ்சு நீரோட்டத்தைச் செலுத்தும் ஆஸ்டியா, கால்வாய், அறை, ஆஸ்குலம் ஆகியவற்றின் தொகுப்பு."),
                    bdef("Sessile", "ஒட்டி வாழும்",
                        "Permanently attached to a surface and unable to move from place to place in the adult stage." to
                            "முதிர்நிலையில் ஒரு பரப்பில் நிரந்தரமாக ஒட்டி, இடம்பெயர இயலாத நிலை."),
                    bp("Sponges occur from the shore to the deep sea. Most are marine; one family, the Spongillidae, lives in fresh water. They are attached to rocks, shells, roots or coral, and take many shapes: crusts, cups, tubes, vases, branches and masses. Some are only a few millimetres across, while others reach more than a metre." to
                        "கடற்பஞ்சுகள் கடற்கரை முதல் ஆழ்கடல் வரை காணப்படுகின்றன. பெரும்பாலானவை கடல்வாழ்; ஸ்பாஞ்சில்லிடே என்ற ஒரு குடும்பம் நன்னீரில் வாழ்கிறது. பாறை, ஓடு, வேர், பவளம் ஆகியவற்றில் ஒட்டி, படலம், கிண்ணம், குழாய், குவளை, கிளை, திரள் எனப் பல வடிவங்களில் உள்ளன. சில சில மில்லிமீட்டர் மட்டுமே; சில ஒரு மீட்டருக்கு மேல் வளர்கின்றன."),
                )),
            bsec("general-characters", "General characters", "பொதுப் பண்புகள்",
                blocks = listOf(
                    bp("The characters below are those that every examination answer on the phylum should state. They are arranged from the whole animal to its cells and its development." to
                        "கீழ்க்காணும் பண்புகளை தொகுதி பற்றிய ஒவ்வொரு தேர்வு விடையிலும் குறிப்பிட வேண்டும். முழு விலங்கிலிருந்து அதன் செல்கள், வளர்ச்சி வரை வரிசைப்படுத்தப்பட்டுள்ளன."),
                    bpoints("General characters of Porifera", "துளையுடலிகளின் பொதுப் பண்புகள்",
                        "Aquatic animals, mostly marine, and sessile as adults." to "நீர்வாழ் விலங்குகள்; பெரும்பாலும் கடல்வாழ்; முதிர்நிலையில் நிலைத்தவை.",
                        "The body is asymmetrical or radially symmetrical and is perforated by many ostia and one or more oscula." to "உடல் சமச்சீரற்றது அல்லது கதிர்ச் சமச்சீர் உடையது; பல ஆஸ்டியாவும் ஒன்று அல்லது அதற்கு மேற்பட்ட ஆஸ்குலமும் உள்ளன.",
                        "The organisation is cellular: there are no true tissues or organs." to "அமைப்பு செல்மட்டத்தில் உள்ளது; உண்மையான திசுக்களோ உறுப்புகளோ இல்லை.",
                        "A system of canals and chambers, the aquiferous system, carries a current of water through the body." to "கால்வாய், அறை அமைப்பான நீர்வழி அமைப்பு உடல் வழியாக நீரோட்டத்தைச் செலுத்துகிறது.",
                        "Flagellated collar cells, the choanocytes, line the canals or chambers and drive the water current." to "கசையிழை காலர் செல்களான கோனோசைட்டுகள் கால்வாய்கள் அல்லது அறைகளை வரிசையாக மூடி நீரோட்டத்தை உருவாக்குகின்றன.",
                        "The skeleton is made of calcareous or siliceous spicules, of spongin fibres, or of both." to "எலும்புக்கூடு சுண்ணாம்பு அல்லது சிலிக்கா ஊசிகள், ஸ்பாஞ்சின் இழைகள் அல்லது இரண்டாலும் ஆனது.",
                        "Digestion is mainly intracellular; there is no mouth or digestive tract." to "செரிமானம் முக்கியமாக செல்லுக்குள் நடைபெறுகிறது; வாயோ செரிமானக் குழாயோ இல்லை.",
                        "There are no nervous system, sense organs, circulatory organs or excretory organs; gas exchange and excretion occur by diffusion into the flowing water." to "நரம்புமண்டலம், உணர்வு உறுப்புகள், சுற்றோட்ட உறுப்புகள், கழிவு நீக்க உறுப்புகள் இல்லை; வாயுப் பரிமாற்றமும் கழிவு நீக்கமும் ஓடும் நீருடன் விரவல் மூலம் நடைபெறுகின்றன.",
                        "Reproduction is asexual, by budding, fragmentation or gemmules, and sexual, by eggs and sperm; the larva is free-swimming." to "பாலிலா இனப்பெருக்கம் மொட்டு விடுதல், துண்டாதல், ஜெம்யூல்கள் மூலமும், பாலின இனப்பெருக்கம் முட்டை, விந்து மூலமும் நடைபெறுகிறது; இளவுயிரி நீந்தக்கூடியது.",
                        "Regeneration is well developed." to "மீளுருவாக்கம் நன்கு வளர்ந்துள்ளது."),
                )),
            bsec("cells", "Cells of a sponge", "கடற்பஞ்சின் செல்கள்",
                blocks = listOf(
                    bp("A sponge has no tissues, but its cells are not alike. Several types can be recognised, each with a particular position and task. The types are not fixed for ever: many of them can change into one another when the animal grows or is injured." to
                        "கடற்பஞ்சுக்குத் திசுக்கள் இல்லை; ஆனால் செல்கள் அனைத்தும் ஒரே வகையல்ல. ஒவ்வொன்றுக்கும் குறிப்பிட்ட இடமும் பணியும் உள்ள பல வகைகள் அடையாளம் காணப்படுகின்றன. இவ்வகைகள் நிரந்தரமானவையல்ல; விலங்கு வளரும்போதோ காயமடையும்போதோ பல செல்கள் ஒன்றிலிருந்து மற்றொன்றாக மாறுகின்றன."),
                    btable("Principal cell types of a sponge", "கடற்பஞ்சின் முதன்மை செல் வகைகள்",
                        listOf("Cell" to "செல்", "Position" to "இடம்", "Function" to "பணி"),
                        listOf(
                            listOf("Pinacocyte" to "பினாகோசைட்", "Outer surface and lining of canals" to "வெளிப்பரப்பு, கால்வாய் உள்ளடுக்கு", "Protection and covering" to "பாதுகாப்பும் மூடுதலும்"),
                            listOf("Porocyte" to "போரோசைட்", "Wall, forming a pore" to "சுவரில், துளையை அமைத்து", "Admits water" to "நீரை உள்ளே அனுமதிக்கிறது"),
                            listOf("Choanocyte" to "கோனோசைட்", "Lining of canals or chambers" to "கால்வாய், அறை உள்ளடுக்கு", "Drives the current and traps food" to "நீரோட்டத்தை உருவாக்கி உணவைப் பிடிக்கிறது"),
                            listOf("Archaeocyte" to "ஆர்க்கியோசைட்", "Mesohyl" to "மீசோஹைல்", "Digests and carries food; changes into other cells" to "உணவைச் செரித்துக் கடத்துகிறது; பிற செல்களாக மாறுகிறது"),
                            listOf("Sclerocyte" to "ஸ்கிளீரோசைட்", "Mesohyl" to "மீசோஹைல்", "Secretes spicules" to "ஊசிகளைச் சுரக்கிறது"),
                            listOf("Spongocyte" to "ஸ்பாஞ்சோசைட்", "Mesohyl" to "மீசோஹைல்", "Secretes spongin fibres" to "ஸ்பாஞ்சின் இழைகளைச் சுரக்கிறது"),
                            listOf("Myocyte" to "மயோசைட்", "Around oscula and ostia" to "ஆஸ்குலம், ஆஸ்டியாவைச் சுற்றி", "Contracts slowly to narrow openings" to "மெதுவாகச் சுருங்கி துளைகளைக் குறுக்குகிறது"),
                        )),
                    bdef("Pinacocyte", "பினாகோசைட்",
                        "A flat cell that forms the outer covering of the sponge and lines the incurrent canals and the central cavity." to
                            "கடற்பஞ்சின் வெளி மூடியையும் உட்செல்லும் கால்வாய்கள், நடுக்குழி ஆகியவற்றின் உள்ளடுக்கையும் அமைக்கும் தட்டை செல்."),
                    bdef("Choanocyte", "கோனோசைட் (Choanocyte; காலர் செல்)",
                        "A flagellated cell with a collar of microvilli round the base of the flagellum. It creates the water current and captures food particles." to
                            "கசையிழையின் அடியில் நுண்விரல் காலர் கொண்ட செல்; நீரோட்டத்தை உருவாக்கி உணவுத் துகள்களைப் பிடிக்கிறது."),
                    bdef("Archaeocyte", "ஆர்க்கியோசைட்",
                        "A large amoeboid cell of the mesohyl that can change into other cell types. Archaeocytes digest food, carry nutrients and take part in regeneration and in the formation of gametes and gemmules." to
                            "மீசோஹைலிலுள்ள பெரிய அமீபா போன்ற செல்; பிற செல் வகைகளாக மாறக்கூடியது. உணவைச் செரித்து ஊட்டச்சத்தைக் கடத்தி, மீளுருவாக்கம், இனச்செல், ஜெம்யூல் உருவாக்கத்தில் பங்கேற்கிறது."),
                    bdef("Mesohyl", "மீசோஹைல் (Mesohyl)",
                        "The gelatinous middle layer between the outer covering and the choanocytes. It contains collagen, the skeleton and the wandering cells." to
                            "வெளி மூடிக்கும் கோனோசைட்டுகளுக்கும் இடையிலுள்ள ஜெல் போன்ற நடு அடுக்கு; கொலாஜன், எலும்புக்கூடு, அலையும் செல்கள் இதில் உள்ளன."),
                    bnote("The cellular grade", "செல்மட்ட அமைப்பு",
                        "Saying that sponges have no true tissues does not mean that their cells are disorganised. The cells are arranged in definite layers and have definite tasks, but they are not bound together into organs with coordinated function, and there is no nervous control." to
                            "கடற்பஞ்சுகளுக்கு உண்மையான திசுக்கள் இல்லை என்பதால் செல்கள் ஒழுங்கற்றவை என்று பொருளல்ல. செல்கள் திட்டவட்டமான அடுக்குகளிலும் பணிகளிலும் உள்ளன; ஆனால் ஒருங்கிணைந்த செயல் கொண்ட உறுப்புகளாக இணைக்கப்படவில்லை; நரம்புக் கட்டுப்பாடும் இல்லை."),
                )),
            bsec("skeleton", "The skeleton", "எலும்புக்கூடு",
                blocks = listOf(
                    bp("The skeleton gives the soft body its shape and holds the canals open. It consists of spicules, of spongin, or of both, and its nature is the main character used to separate the classes." to
                        "எலும்புக்கூடு மென்மையான உடலுக்கு வடிவம் தந்து கால்வாய்களைத் திறந்து வைத்திருக்கிறது. இது ஊசிகள், ஸ்பாஞ்சின் அல்லது இரண்டாலும் ஆனது; வகுப்புகளைப் பிரிக்கப் பயன்படும் முக்கியப் பண்பு இதன் தன்மையே."),
                ),
                subs = listOf(
                    bsub("Spicules", "ஊசிகள்",
                        bp("Spicules are made by sclerocytes. Calcareous spicules are of calcium carbonate and may be rod-like, three-rayed or four-rayed. Siliceous spicules are of silica; they may be single-axis rods, four-rayed, or six-rayed. In many sponges the large spicules that form the main framework are called megascleres, and the small ones scattered in the flesh are called microscleres." to
                            "ஊசிகளை ஸ்கிளீரோசைட்டுகள் உருவாக்குகின்றன. சுண்ணாம்பு ஊசிகள் கால்சியம் கார்பனேட்டால் ஆனவை; கோல், மூன்று கதிர் அல்லது நான்கு கதிர் வடிவில் இருக்கலாம். சிலிக்கா ஊசிகள் சிலிக்காவால் ஆனவை; ஓரச்சுக் கோல், நான்கு கதிர் அல்லது ஆறு கதிர் வடிவில் இருக்கலாம். பல கடற்பஞ்சுகளில் முதன்மைச் சட்டகத்தை அமைக்கும் பெரிய ஊசிகள் மெகாஸ்கிளீர்கள் எனவும், சதையில் சிதறியுள்ள சிறிய ஊசிகள் மைக்ரோஸ்கிளீர்கள் எனவும் அழைக்கப்படுகின்றன."),
                        bdef("Spicule", "நுண்முள் (Spicule)",
                            "A minute skeletal element of calcium carbonate or silica, secreted by cells of the mesohyl." to
                                "மீசோஹைல் செல்களால் சுரக்கப்படும், கால்சியம் கார்பனேட் அல்லது சிலிக்காவாலான நுண்ணிய எலும்புக்கூட உறுப்பு."),
                    ),
                    bsub("Spongin", "ஸ்பாஞ்சின்",
                        bp("Spongin is a flexible protein, related to collagen, secreted by spongocytes. It forms a network of fibres that may carry siliceous spicules or may be the only skeleton. The soft, elastic skeleton of the bath sponge consists of spongin alone." to
                            "ஸ்பாஞ்சின் கொலாஜனுடன் தொடர்புடைய நெகிழ்வான புரதம்; ஸ்பாஞ்சோசைட்டுகள் சுரக்கின்றன. இது சிலிக்கா ஊசிகளைத் தாங்கக்கூடிய, அல்லது ஒரே எலும்புக்கூடாகவும் அமையக்கூடிய இழை வலையை உருவாக்குகிறது. குளியல் கடற்பஞ்சின் மென்மையான, மீள்தன்மை கொண்ட எலும்புக்கூடு ஸ்பாஞ்சினால் மட்டுமே ஆனது."),
                        bdef("Spongin", "ஸ்பாஞ்சின் (Spongin)",
                            "A flexible, collagen-related protein that forms the fibrous skeleton of many sponges." to
                                "பல கடற்பஞ்சுகளின் இழை எலும்புக்கூட்டை அமைக்கும் நெகிழ்வான, கொலாஜனுடன் தொடர்புடைய புரதம்."),
                    ),
                )),
            bsec("physiology", "Life processes in brief", "உயிர்ச்செயல்கள் சுருக்கமாக",
                blocks = listOf(
                    bp("All the life processes of a sponge depend on the current of water. The beating of the flagella of the choanocytes draws water in through the ostia, passes it through the canals and chambers, and expels it through the oscula. The current is slow and continuous, but because a sponge is built so that a large area of choanocytes is exposed to the water, a sponge can filter a volume of water many times its own volume in an hour." to
                        "கடற்பஞ்சின் எல்லா உயிர்ச்செயல்களும் நீரோட்டத்தைச் சார்ந்தவை. கோனோசைட்டுகளின் கசையிழை அசைவு ஆஸ்டியா வழியாக நீரை இழுத்து, கால்வாய்கள், அறைகள் வழியே செலுத்தி, ஆஸ்குலம் வழியாக வெளியேற்றுகிறது. ஓட்டம் மெதுவானது, தொடர்ச்சியானது; ஆனால் அதிகப் பரப்பளவு கோனோசைட்டுகள் நீருக்கு வெளிப்படும்படி உடல் அமைந்துள்ளதால், ஒரு மணி நேரத்தில் தன் கொள்ளளவைவிடப் பல மடங்கு நீரை அது வடிகட்ட முடியும்."),
                    bp("Food consists of bacteria, single-celled algae and fine organic particles. The collars of the choanocytes trap them, and they are digested inside the cells, either by the choanocytes or by amoeboid cells to which the food is passed. Oxygen enters, and carbon dioxide and ammonia leave, by diffusion into the passing water. Freshwater sponges have contractile vacuoles that remove excess water from the cells." to
                        "உணவு பாக்டீரியா, ஒருசெல் பாசிகள், நுண்ணிய கரிமத் துகள்கள். கோனோசைட் காலர்கள் அவற்றைப் பிடிக்கின்றன; கோனோசைட்டுகளிலோ உணவு கடத்தப்படும் அமீபா போன்ற செல்களிலோ செல்லுக்குள் செரிக்கப்படுகின்றன. ஆக்சிஜன் உள்ளே நுழைவதும் கார்பன் டை ஆக்சைடும் அம்மோனியாவும் வெளியேறுவதும் செல்லும் நீருடன் விரவல் மூலம் நடைபெறுகின்றன. நன்னீர் கடற்பஞ்சுகளில் செல்களிலிருந்து மிகை நீரை வெளியேற்றும் சுருங்கும் குமிழ்கள் உள்ளன."),
                    bp("There is no nervous system, but sponges are not inert. Myocytes around the oscula and ostia contract slowly and can narrow or close them, and a stimulus can spread slowly from cell to cell. Such responses are local and slow, quite unlike the quick, coordinated actions of animals with nerves." to
                        "நரம்புமண்டலம் இல்லை; ஆனால் கடற்பஞ்சுகள் செயலற்றவை அல்ல. ஆஸ்குலம், ஆஸ்டியாவைச் சுற்றியுள்ள மயோசைட்டுகள் மெதுவாகச் சுருங்கி அவற்றைக் குறுக்கவோ மூடவோ முடியும்; தூண்டல் செல்லிலிருந்து செல்லுக்கு மெதுவாகப் பரவக்கூடும். இத்தகைய பதில்கள் உள்ளூரில் நிகழும் மெதுவானவை; நரம்புள்ள விலங்குகளின் விரைவான ஒருங்கிணைந்த செயல்களிலிருந்து முற்றிலும் வேறுபட்டவை."),
                )),
            bsec("position", "Position among the animals", "விலங்குகளிடையே இடம்",
                blocks = listOf(
                    bp("Because sponges have no true tissues, older textbooks placed them in a separate group, the Parazoa, beside the Eumetazoa, which includes all animals with true tissues. The name is still met in examinations, but it does not describe a natural group, and it is used only for convenience." to
                        "கடற்பஞ்சுகளுக்கு உண்மையான திசுக்கள் இல்லாததால், உண்மைத் திசுக்கள் கொண்ட எல்லா விலங்குகளும் அடங்கிய யூமெட்டாசோவாவுக்கு அருகே பழைய பாடநூல்கள் அவற்றைப் பாராசோவா என்ற தனிக் குழுவில் வைத்தன. இப்பெயர் இன்றும் தேர்வுகளில் வருகிறது; ஆனால் இது இயற்கையான குழுவைக் குறிக்காது; வசதிக்காக மட்டுமே பயன்படுகிறது."),
                    bp("Sponges are the descendants of one of the earliest branches of animal life, and their choanocytes resemble the collared flagellate cells of the choanoflagellates, the protists thought to be the closest living relatives of animals. This resemblance is the main evidence cited for an origin of animals from a colonial, collared flagellate ancestor. The exact order in which sponges and other early animal groups branched is still being investigated." to
                        "கடற்பஞ்சுகள் விலங்கு வாழ்வின் மிக ஆரம்பக் கிளைகளில் ஒன்றின் வழித்தோன்றல்கள்; அவற்றின் கோனோசைட்டுகள், விலங்குகளின் மிக நெருங்கிய வாழும் உறவினர்களாகக் கருதப்படும் புரோட்டிஸ்டுகளான கோனோஃபிளாஜெல்லேட்டுகளின் காலர் கசையிழை செல்களை ஒத்துள்ளன. விலங்குகள் காலர் கசையிழை கொண்ட கூட்டு மூதாதையிடமிருந்து தோன்றின என்பதற்குக் காட்டப்படும் முக்கிய சான்று இந்த ஒற்றுமையே. கடற்பஞ்சுகளும் பிற ஆரம்ப விலங்குக் குழுக்களும் எந்த வரிசையில் கிளைத்தன என்பது இன்னும் ஆராயப்படுகிறது."),
                    bdef("Parazoa", "பாராசோவா",
                        "A traditional name for animals without true tissues, that is, the sponges, contrasted with the Eumetazoa, which have true tissues. It is a convenience of teaching and not a natural group." to
                            "உண்மைத் திசுக்கள் இல்லாத விலங்குகளான கடற்பஞ்சுகளைக் குறிக்கும் மரபுப் பெயர்; உண்மைத் திசுக்கள் உள்ள யூமெட்டாசோவாவுக்கு எதிரானது. இது கற்பித்தல் வசதியே; இயற்கைக் குழு அல்ல."),
                )),
            bsec("classification", "Classification up to class", "வகுப்புவரை வகைப்பாடு",
                blocks = listOf(
                    bp("Syllabuses usually recognise three classes, separated mainly by the skeleton. Modern classifications add a fourth class, Homoscleromorpha, which was formerly placed in the Demospongiae; the three-class scheme is the one required for examinations unless your university prescribes otherwise." to
                        "பாடத்திட்டங்கள் பொதுவாக மூன்று வகுப்புகளை ஏற்கின்றன; எலும்புக்கூடே முதன்மைப் பிரிவுப் பண்பு. நவீன வகைப்பாடுகள், முன்பு டெமோஸ்பாஞ்சியேயில் இருந்த ஹோமோஸ்கிளீரோமார்ஃபா என்ற நான்காம் வகுப்பையும் சேர்க்கின்றன; பல்கலைக்கழகம் வேறுவிதமாகக் கூறாவிட்டால் தேர்வுக்கு மூன்று வகுப்பு முறையே தேவை."),
                    btable("The three main classes of Porifera", "துளையுடலிகளின் மூன்று முதன்மை வகுப்புகள்",
                        listOf("Class" to "வகுப்பு", "Skeleton" to "எலும்புக்கூடு", "Features" to "சிறப்புப் பண்புகள்", "Examples" to "எடுத்துக்காட்டுகள்"),
                        listOf(
                            listOf("Calcarea" to "கால்கேரியா", "Calcareous spicules, separate" to "சுண்ணாம்பு ஊசிகள், தனித்தவை", "All marine; mostly shallow water; small; asconoid, syconoid or leuconoid" to "அனைத்தும் கடல்வாழ்; பெரும்பாலும் ஆழமற்ற நீர்; சிறியவை; அஸ்கோனாய்டு, சைக்கனாய்டு அல்லது லியூக்கோனாய்டு", "Leucosolenia, Sycon, Grantia" to "லியூக்கோசோலீனியா, சைக்கான், கிராண்ஷியா"),
                            listOf("Hexactinellida" to "ஹெக்சாக்டினெல்லிடா", "Siliceous six-rayed spicules" to "சிலிக்கா ஆறு கதிர் ஊசிகள்", "Glass sponges; mostly deep sea; cup or vase shaped; tissue is largely syncytial" to "கண்ணாடிக் கடற்பஞ்சுகள்; பெரும்பாலும் ஆழ்கடல்; கிண்ணம் அல்லது குவளை வடிவம்; திசு பெரும்பாலும் சின்சிடியம்", "Euplectella, Hyalonema" to "யூப்ளெக்டெல்லா, ஹயலோனீமா"),
                            listOf("Demospongiae" to "டெமோஸ்பாஞ்சியே", "Siliceous spicules not six-rayed, spongin fibres, or both" to "ஆறு கதிரற்ற சிலிக்கா ஊசிகள், ஸ்பாஞ்சின் இழைகள் அல்லது இரண்டும்", "The largest class, with most living species; leuconoid; marine and freshwater" to "பெரும்பாலான உயிர் இனங்கள் கொண்ட மிகப்பெரிய வகுப்பு; லியூக்கோனாய்டு; கடல், நன்னீர்", "Spongilla, Euspongia, Cliona" to "ஸ்பாஞ்சில்லா, யூஸ்பாஞ்சியா, கிளையோனா"),
                        )),
                    bdef("Calcarea", "கால்கேரியா (Calcarea)",
                        "The class of sponges with skeletons of separate calcareous spicules; all are marine." to
                            "தனித்தனி சுண்ணாம்பு ஊசி எலும்புக்கூடு கொண்ட கடற்பஞ்சு வகுப்பு; அனைத்தும் கடல்வாழ்."),
                    bdef("Hexactinellida", "ஹெக்சாக்டினெல்லிடா (Hexactinellida)",
                        "The class of glass sponges, whose skeleton contains siliceous spicules with six rays." to
                            "ஆறு கதிர் சிலிக்கா ஊசிகளைக் கொண்ட கண்ணாடிக் கடற்பஞ்சு வகுப்பு."),
                    bdef("Demospongiae", "டெமோஸ்பாஞ்சியே (Demospongiae)",
                        "The largest class of sponges, with siliceous spicules that are not six-rayed, with spongin, or with both." to
                            "ஆறு கதிரற்ற சிலிக்கா ஊசிகள், ஸ்பாஞ்சின் அல்லது இரண்டும் கொண்ட, கடற்பஞ்சுகளின் மிகப்பெரிய வகுப்பு."),
                    bnote("A common slip", "பொதுவான தவறு",
                        "The bath sponge is a demosponge with a skeleton of spongin only; it is not a calcareous sponge. Sycon is calcareous. Do not exchange the examples between classes." to
                            "குளியல் கடற்பஞ்சு ஸ்பாஞ்சின் மட்டும் கொண்ட டெமோஸ்பாஞ்சு; அது சுண்ணாம்புக் கடற்பஞ்சு அல்ல. சைக்கான் சுண்ணாம்புடையது. எடுத்துக்காட்டுகளை வகுப்புகளுக்கிடையே மாற்றி எழுதக் கூடாது."),
                )),
            bsec("feeding", "Feeding as a filtering machine", "வடிகட்டும் இயந்திரமாக உணவூட்டம்",
                blocks = listOf(
                    bp("A sponge is a pump and a filter. The flagella of the choanocytes beat and draw water in through the small pores in the body wall, pass it through the canals and chambers, and drive it out through the osculum. As the water passes over the collars of the choanocytes, bacteria, unicellular algae and fine organic particles are trapped and engulfed. Particles are also taken in by the pinacocytes lining the canals. Digestion is intracellular, in food vacuoles, and the amoeboid cells of the mesohyl carry the food to the other cells of the body.",
                        "கடற்பஞ்சு ஒரு இறைப்பானும் வடிகட்டியுமாகும். கோனோசைட்டுகளின் கசையிழைகள் அடித்து, உடற்சுவரின் சிறு துளைகள் வழியே நீரை உள்ளிழுத்து, கால்வாய்கள், அறைகள் வழியே அனுப்பி, ஆஸ்குலம் வழியே வெளியேற்றுகின்றன. நீர் கோனோசைட்டுகளின் கழுத்துப்பட்டைகளைக் கடக்கும்போது பாக்டீரியா, ஒற்றைச் செல் பாசிகள், நுண்ணிய கரிமத் துகள்கள் பிடிக்கப்பட்டு விழுங்கப்படுகின்றன. கால்வாய்களை மூடியுள்ள பினாக்கோசைட்டுகளும் துகள்களை எடுக்கின்றன. செரிமானம் உணவுக் குமிழ்களில் அகச்செல்; மீசோஹைலின் அமீபா போன்ற செல்கள் உணவை உடலின் பிற செல்களுக்குக் கொண்டு செல்கின்றன."),
                    bp("The quantity of water that passes through a sponge is large in relation to its size. A sponge can pump many times its own volume of water in an hour, and sponges on a reef are major consumers of the fine particles and dissolved matter in the sea water. Because the water is also the carrier of oxygen and the means of removing carbon dioxide and wastes, the same current serves for feeding, breathing, excretion and the delivery of sperm. This economy of design is the reason why a sponge can do without organs.",
                        "ஒரு கடற்பஞ்சு வழியே செல்லும் நீரின் அளவு அதன் அளவுடன் ஒப்பிடும்போது பெரியது. கடற்பஞ்சு ஒரு மணி நேரத்தில் தன் சொந்த கொள்ளளவை விடப் பல மடங்கு நீரை இறைக்க முடியும்; முகட்டிலுள்ள கடற்பஞ்சுகள் கடல்நீரிலுள்ள நுண்துகள்கள், கரைந்த பொருட்களை உட்கொள்ளும் பெரும் நுகர்வோர். நீர் ஆக்சிஜனைக் கொண்டு வருவதுடன் கார்பன் டை ஆக்சைடையும் கழிவுகளையும் அகற்றும் வழியாகவும் இருப்பதால், அதே நீரோட்டம் உணவூட்டம், சுவாசம், கழிவு நீக்கம், விந்து வழங்கலுக்குப் பயன்படுகிறது. வடிவமைப்பின் இந்தச் சிக்கனமே கடற்பஞ்சு உறுப்புகள் இன்றி வாழ முடிவதற்கு காரணம்."),
                    bdef("Filter feeding", "வடிகட்டி உண்ணல் (Filter feeding)", "Feeding by straining small particles out of water that is passed through the body." to
                        "உடலினூடே செலுத்தப்படும் நீரிலிருந்து சிறு துகள்களை வடிகட்டி உண்ணுதல்."),
                )),
            bsec("coordination", "Coordination without nerves", "நரம்பின்றி ஒருங்கிணைப்பு",
                blocks = listOf(
                    bp("Sponges have no nerve cells, no sensory organs and no muscles in the true sense, and yet they respond to stimuli. When a sponge is touched or disturbed the oscula may close slowly and the canals may contract, and the response spreads from cell to cell over the body in a matter of minutes. The agents are contractile cells, called myocytes, which lie around the osculum and the larger pores and which, like muscle cells, shorten on stimulation. Cells communicate by chemical signals and by direct contact, and not through a nervous system.",
                        "கடற்பஞ்சுகளுக்கு நரம்பு செல்களோ உணர் உறுப்புகளோ உண்மைப் பொருளில் தசைகளோ இல்லை; ஆயினும் தூண்டல்களுக்குப் பதிலளிக்கின்றன. கடற்பஞ்சைத் தொட்டாலோ தொந்தரவு செய்தாலோ ஆஸ்குலாக்கள் மெதுவாக மூடலாம், கால்வாய்கள் சுருங்கலாம்; பதில் சில நிமிடங்களில் செல்லிலிருந்து செல்லாக உடல் முழுவதும் பரவுகிறது. செயல்படுபவை ஆஸ்குலத்தையும் பெரிய துளைகளையும் சுற்றியுள்ள, தசை செல்கள் போல தூண்டலில் சுருங்கும் மயோசைட்டுகள் எனும் சுருங்கும் செல்கள். செல்கள் நரம்பு மண்டலம் மூலம் அல்ல, வேதிச் சமிக்ஞைகளாலும் நேரடித் தொடர்பாலும் தொடர்பு கொள்கின்றன."),
                    bdef("Myocyte", "மயோசைட்", "A contractile cell of a sponge, usually arranged around the osculum or pores, which can narrow the opening." to
                        "கடற்பஞ்சின் சுருங்கும் செல்; பொதுவாக ஆஸ்குலம் அல்லது துளைகளைச் சுற்றி அமைந்து திறப்பைக் குறுக்கும்."),
                )),
            bsec("chemistry", "Chemical defence and drugs from sponges", "வேதிப் பாதுகாப்பும் கடற்பஞ்சு மருந்துகளும்",
                blocks = listOf(
                    bp("Sponges are sessile and soft, and have few physical defences apart from their spicules. Many of them protect themselves by producing chemical compounds that are distasteful or toxic to fishes, that prevent other organisms from growing on them, or that stop bacteria and fungi. These compounds are often unusual in structure, and the sponges have been a major source of new compounds in the search for drugs. Nucleosides first found in a Caribbean sponge led to the development of antiviral and anticancer drugs, and a compound from a Japanese sponge was the starting point for a drug used in the treatment of cancer of the breast.",
                        "கடற்பஞ்சுகள் அசையாதவை, மென்மையானவை; நுண்முள்களைத் தவிர சில பௌதிகப் பாதுகாப்புகளே உண்டு. அவற்றில் பல மீன்களுக்கு விரும்பத்தகாத அல்லது நச்சான, பிற உயிரிகள் தம் மேல் வளர்வதைத் தடுக்கும், அல்லது பாக்டீரியா, பூஞ்சைகளை நிறுத்தும் வேதிச் சேர்மங்களை உருவாக்கித் தம்மைப் பாதுகாக்கின்றன. இச்சேர்மங்கள் பெரும்பாலும் அமைப்பில் அசாதாரணமானவை; மருந்து தேடலில் புதிய சேர்மங்களின் முக்கிய மூலமாக கடற்பஞ்சுகள் இருந்துள்ளன. கரீபியன் கடற்பஞ்சில் முதலில் கண்டறியப்பட்ட நியூக்ளியோசைடுகள் வைரஸ் எதிர்ப்பு, புற்றுநோய் எதிர்ப்பு மருந்துகளின் வளர்ச்சிக்கு வழிவகுத்தன; ஜப்பானிய கடற்பஞ்சின் சேர்மம் மார்பகப் புற்றுநோய் சிகிச்சை மருந்தின் தொடக்கப் புள்ளியாக இருந்தது."),
                    bp("In the answer it is wise to avoid giving the names and uses of particular drugs unless they are in the prescribed text, since the information changes. The principle, that a sessile animal which cannot flee has evolved a chemical defence, and that these compounds are valuable, is what the examiner will want. The associated bacteria in the tissue of a sponge, which may form a large part of its mass, are in many cases the actual makers of the compounds.",
                        "விடையில் குறிப்பிட்ட மருந்துகளின் பெயர்களையும் பயன்களையும், பரிந்துரைக்கப்பட்ட நூலில் இல்லாவிடில், தவிர்ப்பது நல்லது; ஏனெனில் தகவல் மாறுகிறது. தப்பிச் செல்ல இயலாத அசையா விலங்கு வேதிப் பாதுகாப்பை உருவாக்கியுள்ளது, இச்சேர்மங்கள் மதிப்புள்ளவை என்ற கொள்கையே தேர்வாளர் எதிர்பார்ப்பது. கடற்பஞ்சின் திசுவிலுள்ள, அதன் எடையின் பெரும்பகுதியாக இருக்கக்கூடிய தொடர்புடைய பாக்டீரியாக்கள் பல நேரங்களில் உண்மையில் சேர்மங்களை உருவாக்குபவை."),
                    bdef("Bioactive compound", "உயிர்ச்செயல் சேர்மம் (Bioactive compound)", "A chemical made by an organism that has an effect on other living things, such as a toxin, an antibiotic or a deterrent." to
                        "நச்சு, நுண்ணுயிர்க்கொல்லி, தடுப்பான் போன்ற பிற உயிர்களில் விளைவு ஏற்படுத்தும், உயிரி உருவாக்கும் வேதிப்பொருள்."),
                )),
            bsec("boring", "Boring sponges and the erosion of reefs", "துளையிடும் கடற்பஞ்சுகளும் முகடு அரிப்பும்",
                blocks = listOf(
                    bp("Some demosponges, of which Cliona is the best-known, live in tunnels that they excavate in limestone, in the shells of molluscs and in dead coral. Special cells etch tiny chips out of the calcium carbonate, which are swept away in the water current, so that the sponge slowly eats its way into the rock. The effect on a coral reef is significant, since the sponge weakens the skeleton of corals and the base of the reef, and it is one of the agents of bioerosion on a reef. Boring sponges also damage the shells of oysters in cultured beds.",
                        "க்ளியோனா மிகவும் அறியப்பட்ட சில டெமோஸ்பாஞ்சுகள் சுண்ணாம்புப் பாறை, மெல்லுடலிகளின் ஓடுகள், இறந்த பவளத்தில் தாம் துளைக்கும் சுரங்கங்களில் வாழ்கின்றன. சிறப்புச் செல்கள் கால்சியம் கார்பனேட்டிலிருந்து சிறு துகள்களைச் செதுக்குகின்றன; அவை நீரோட்டத்தால் அடித்துச் செல்லப்படுகின்றன; இவ்வாறு கடற்பஞ்சு மெதுவாகப் பாறைக்குள் தின்று செல்கிறது. பவள முகட்டில் விளைவு குறிப்பிடத்தக்கது; ஏனெனில் கடற்பஞ்சு பவளங்களின் எலும்புக்கூட்டையும் முகட்டின் அடியையும் பலவீனமாக்குகிறது; முகட்டின் உயிரி அரிப்பு காரணிகளில் ஒன்று. துளையிடும் கடற்பஞ்சுகள் வளர்ப்புப் படுகைகளிலுள்ள சிப்பியோடுகளையும் சேதப்படுத்துகின்றன."),
                    bdef("Bioerosion", "உயிரி அரிப்பு (Bioerosion)", "The breaking down of hard rock or skeleton by the activity of living organisms such as boring sponges, worms and grazing fishes." to
                        "துளையிடும் கடற்பஞ்சுகள், புழுக்கள், மேயும் மீன்கள் போன்ற உயிரிகளின் செயல்பாட்டால் கடின பாறை அல்லது எலும்புக்கூடு உடைதல்."),
                )),
            bsec("answering", "Answering questions on sponges", "கடற்பஞ்சுகள் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("The phylum is examined through its general characters, the cell types and their functions, the skeleton and the classification. An answer on the general characters should be brief and organised: cellular grade of organisation, aquiferous system, sessile and asymmetrical or radially symmetrical body, spicules or spongin, and the absence of organs and nerves. The question on the cells is best answered with a table of the cell, its position and its function, and the question on classification with a table of the three classes, the type of spicule and the examples. The three canal types and the reproduction have their own chapters, to which the student may cross-refer.",
                        "தொகுதி பொதுப்பண்புகள், செல் வகைகளும் பணிகளும், எலும்புக்கூடு, வகைப்பாடு வழியாகத் தேர்வு செய்யப்படுகிறது. பொதுப்பண்புகள் குறித்த விடை சுருக்கமாகவும் ஒழுங்காகவும் இருக்க வேண்டும்: செல் நிலை அமைப்பு, நீர்க்கால்வாய் மண்டலம், அசையா, சமச்சீரற்ற அல்லது ஆரச்சமச்சீர் உடல், நுண்முள்கள் அல்லது ஸ்பாஞ்ஜின், உறுப்புகள், நரம்புகளின்மை. செல்கள் குறித்த வினாவுக்கு செல், இடம், பணி அட்டவணையும், வகைப்பாடு வினாவுக்கு மூன்று வகுப்புகள், நுண்முள் வகை, எடுத்துக்காட்டுகளின் அட்டவணையும் சிறந்தவை. மூன்று கால்வாய் வகைகளுக்கும் இனப்பெருக்கத்துக்கும் தனி அத்தியாயங்கள் உள்ளன; மாணவர் குறுக்குக் குறிப்பிடலாம்."),
                    bpoints("Frequent errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Describing the sponge as a plant, or as having tissues and organs." to "கடற்பஞ்சை தாவரம் என்றோ திசுக்களும் உறுப்புகளும் உடையது என்றோ விவரித்தல்.",
                        "Saying that the water enters by the osculum; it enters by the pores and leaves by the osculum." to "நீர் ஆஸ்குலம் வழியே உள்ளே வருகிறது என்று கூறுதல்; அது துளைகள் வழியே உள்ளே வந்து ஆஸ்குலம் வழியே வெளியேறுகிறது.",
                        "Placing the flagella on the pinacocytes; they belong to the choanocytes." to "கசையிழைகளை பினாக்கோசைட்டுகளில் இடுதல்; அவை கோனோசைட்டுகளுக்கு உரியவை.",
                        "Giving siliceous spicules to the Calcarea or calcareous ones to the Hexactinellida." to "கால்கேரியாவுக்கு சிலிக்கா நுண்முள்களையோ ஹெக்சாக்டினெல்லிடாவுக்கு சுண்ணாம்பு நுண்முள்களையோ தருதல்.",
                    ),
                )),
            bsec("regeneration", "Regeneration and cellular plasticity", "மீளுருவாக்கமும் செல் மாற்றத்திறனும்",
                blocks = listOf(
                    bp("Sponges regenerate with remarkable ease. A piece cut from a living sponge can re-form a complete animal, and a sponge squeezed through fine cloth into separate cells will reaggregate into a new sponge in suitable conditions." to
                        "கடற்பஞ்சுகள் வியப்பூட்டும் எளிமையுடன் மீளுருவாகின்றன. உயிருள்ள கடற்பஞ்சிலிருந்து வெட்டிய துண்டு முழு விலங்காக மீண்டும் உருவாகும்; மெல்லிய துணி வழியாகப் பிழிந்து தனிச் செல்களாக்கப்பட்ட கடற்பஞ்சு, பொருத்தமான சூழலில் மீண்டும் ஒன்றுசேர்ந்து புதிய கடற்பஞ்சாகும்."),
                    bp("The basis is the plasticity of the cells. The archaeocytes behave like stem cells and can change into several other cell types. After injury or fragmentation, surviving cells migrate, divide and reorganise to restore the canals and chambers. Choanocytes and other cells can also convert from one type to another during growth and repair." to
                        "இதன் அடிப்படை செல்களின் மாற்றத்திறன். ஆர்க்கியோசைட்டுகள் ஸ்டெம் செல்கள் போல நடந்து பல செல் வகைகளாக மாறுகின்றன. காயம் அல்லது துண்டாதலுக்குப் பின் எஞ்சிய செல்கள் இடம்பெயர்ந்து, பிரிந்து, மீண்டும் அமைந்து கால்வாய்களையும் அறைகளையும் மீட்கின்றன. வளர்ச்சி, சீரமைப்பின்போது கோனோசைட்டுகள் உள்ளிட்ட செல்களும் ஒரு வகையிலிருந்து மற்றொன்றாக மாறக்கூடும்."),
                    bnote("Regeneration is not gemmule formation", "மீளுருவாக்கம் ஜெம்யூல் உருவாக்கம் அல்ல",
                        "A fragment that regrows is an example of regeneration, and when it happens in nature it is a kind of asexual reproduction called fragmentation. Gemmules are different: they are resistant internal buds, formed especially by freshwater sponges, and are described in the chapter on reproduction." to
                            "மீண்டும் வளரும் துண்டு மீளுருவாக்கத்துக்கு எடுத்துக்காட்டு; இயற்கையில் நிகழும்போது அது துண்டாதல் எனும் பாலிலா இனப்பெருக்கம். ஜெம்யூல்கள் வேறுபட்டவை: அவை தாங்குதிறன் கொண்ட உள் மொட்டுகள்; குறிப்பாக நன்னீர் கடற்பஞ்சுகளில் உருவாகின்றன; இனப்பெருக்க அத்தியாயத்தில் விளக்கப்படுகின்றன."),
                )),
            bsec("importance", "Sponges and people", "கடற்பஞ்சுகளும் மனிதரும்",
                blocks = listOf(
                    bp("The elastic skeleton of the bath sponge has long been used for washing and cleaning, although most such products are now synthetic. Sponges are also a rich source of unusual chemical compounds. Nucleosides first isolated from a sponge helped to inspire the anticancer drug cytarabine and the antiviral drug vidarabine, and many other sponge compounds are being tested for medical use." to
                        "குளியல் கடற்பஞ்சின் மீள்தன்மை எலும்புக்கூடு நீண்ட காலமாக கழுவவும் துடைக்கவும் பயன்படுகிறது; எனினும் இப்போது பெரும்பாலான பொருள்கள் செயற்கையானவை. கடற்பஞ்சுகள் வியப்பான வேதிச் சேர்மங்களுக்கும் வளமான மூலம். ஒரு கடற்பஞ்சிலிருந்து முதலில் பிரித்தெடுக்கப்பட்ட நியூக்ளியோசைடுகள் சைடராபின் என்ற புற்றுநோய் மருந்தையும் விடராபின் என்ற வைரஸ் எதிர்ப்பு மருந்தையும் உருவாக்க உந்துதல் அளித்தன; வேறு பல சேர்மங்கள் மருத்துவப் பயன்பாட்டுக்குச் சோதிக்கப்படுகின்றன."),
                    bp("In the sea, sponges filter large volumes of water, give shelter to many small animals, and in some cases harm other organisms: boring sponges such as Cliona excavate galleries in the shells of oysters and in coral rock." to
                        "கடலில் கடற்பஞ்சுகள் பெருமளவு நீரை வடிகட்டுகின்றன; பல சிறு விலங்குகளுக்கு அடைக்கலம் தருகின்றன; சில நேரங்களில் பிற உயிரிகளுக்குத் தீங்கும் செய்கின்றன: கிளையோனா போன்ற துளையிடும் கடற்பஞ்சுகள் சிப்பி ஓடுகளிலும் பவளப் பாறையிலும் குகைகளைத் தோண்டுகின்றன."),
                    bpoints("Points to include in a five-mark answer", "ஐந்து மதிப்பெண் விடையில் சேர்க்க வேண்டியவை",
                        "Definition and position: aquatic, mostly marine, sessile, cellular grade." to "வரையறையும் இடமும்: நீர்வாழ், பெரும்பாலும் கடல்வாழ், நிலைத்த, செல்மட்டம்.",
                        "Ostia, oscula and the aquiferous system with choanocytes." to "ஆஸ்டியா, ஆஸ்குலம், கோனோசைட்டுகளுடன் கூடிய நீர்வழி அமைப்பு.",
                        "Skeleton of spicules, spongin or both." to "ஊசிகள், ஸ்பாஞ்சின் அல்லது இரண்டாலான எலும்புக்கூடு.",
                        "Intracellular digestion, diffusion for gas exchange and excretion, and the absence of organs and nerves." to "செல்லுக்குள் செரிமானம், வாயுப் பரிமாற்றம், கழிவு நீக்கத்துக்கு விரவல்; உறுப்புகளும் நரம்புகளும் இல்லாமை.",
                        "Reproduction by budding, fragmentation, gemmules and gametes, and the larva." to "மொட்டு விடுதல், துண்டாதல், ஜெம்யூல், இனச்செல்கள் மூலம் இனப்பெருக்கம்; இளவுயிரி."),
                )),
        ),
        summary = listOf(
            "Porifera are aquatic, mostly marine, sessile animals perforated by ostia and oscula and organised at the cellular grade." to "துளையுடலிகள் ஆஸ்டியா, ஆஸ்குலம் துளைகள் கொண்ட, செல்மட்ட அமைப்புடைய, நீர்வாழ், பெரும்பாலும் கடல்வாழ், நிலைத்த விலங்குகள்.",
            "A current of water driven by choanocytes passes through the aquiferous system and brings food and oxygen." to "கோனோசைட்டுகள் செலுத்தும் நீரோட்டம் நீர்வழி அமைப்பில் சென்று உணவையும் ஆக்சிஜனையும் கொண்டுவருகிறது.",
            "The main cells are pinacocytes, porocytes, choanocytes, archaeocytes, sclerocytes, spongocytes and myocytes." to "முதன்மை செல்கள் பினாகோசைட், போரோசைட், கோனோசைட், ஆர்க்கியோசைட், ஸ்கிளீரோசைட், ஸ்பாஞ்சோசைட், மயோசைட்.",
            "The skeleton is of calcareous or siliceous spicules, of spongin, or of both, and it separates the classes." to "எலும்புக்கூடு சுண்ணாம்பு அல்லது சிலிக்கா ஊசிகள், ஸ்பாஞ்சின் அல்லது இரண்டாலும் ஆனது; வகுப்புகளை இதுவே பிரிக்கிறது.",
            "The classes are Calcarea, Hexactinellida and Demospongiae; Homoscleromorpha is recognised in modern schemes." to "வகுப்புகள் கால்கேரியா, ஹெக்சாக்டினெல்லிடா, டெமோஸ்பாஞ்சியே; நவீன வகைப்பாடுகளில் ஹோமோஸ்கிளீரோமார்ஃபாவும் ஏற்கப்படுகிறது.",
            "Archaeocytes and other plastic cells make regeneration possible; this is distinct from gemmule formation." to "ஆர்க்கியோசைட்டுகளும் பிற மாற்றத்திறன் செல்களும் மீளுருவாக்கத்தை சாத்தியமாக்குகின்றன; இது ஜெம்யூல் உருவாக்கத்திலிருந்து வேறுபட்டது.",
        ),
        checks = listOf(
            bqc("What does the name Porifera mean?", "துளையுடலிகள் என்ற பெயரின் பொருள் என்ன?",
                "Pore-bearing, from the Latin porus (pore) and ferre (to bear).", "துளை தாங்குவது; லத்தீன் porus (துளை), ferre (தாங்குதல்) என்பவற்றிலிருந்து."),
            bqc("Which cells create the water current in a sponge?", "கடற்பஞ்சில் நீரோட்டத்தை உருவாக்கும் செல்கள் எவை?",
                "The choanocytes, by the beating of their flagella.", "கோனோசைட்டுகள்; தம் கசையிழை அசைவால்."),
            bqc("Name the skeleton of the bath sponge.", "குளியல் கடற்பஞ்சின் எலும்புக்கூட்டின் பெயர் என்ன?",
                "Spongin fibres only.", "ஸ்பாஞ்சின் இழைகள் மட்டும்."),
            bqc("Give one example each of Calcarea, Hexactinellida and Demospongiae.", "கால்கேரியா, ஹெக்சாக்டினெல்லிடா, டெமோஸ்பாஞ்சியே ஆகியவற்றுக்கு ஒவ்வொரு எடுத்துக்காட்டு தருக.",
                "Sycon, Euplectella and Spongilla (or Euspongia).", "சைக்கான், யூப்ளெக்டெல்லா, ஸ்பாஞ்சில்லா (அல்லது யூஸ்பாஞ்சியா)."),
            bqc("Which cells are mainly responsible for regeneration?", "மீளுருவாக்கத்துக்கு முக்கியமாகப் பொறுப்பான செல்கள் எவை?",
                "The archaeocytes, which behave like stem cells and can change into other cell types.", "ஆர்க்கியோசைட்டுகள்; இவை ஸ்டெம் செல்கள் போல நடந்து பிற செல்களாக மாறுகின்றன."),
        ),
    )
}
