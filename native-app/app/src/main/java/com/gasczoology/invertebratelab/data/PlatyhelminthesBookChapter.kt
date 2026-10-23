package com.gasczoology.invertebratelab.data

/** Book-style chapter: Platyhelminthes general characters and classification. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PlatyhelminthesBookChapter {
    val chapter = bchapter(
        lessonId = "u3-platy",
        title = "Phylum Platyhelminthes: general characters and classification" to
            "தொகுதி பிளாட்டிஹெல்மிந்தீஸ்: பொதுப்பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State the general characters of the phylum Platyhelminthes." to "பிளாட்டிஹெல்மிந்தீஸ் தொகுதியின் பொதுப்பண்புகளைக் கூற.",
            "Explain the terms bilateral symmetry, triploblastic, acoelomate and parenchyma." to "இருபக்கச் சமச்சீர், மூவடுக்கு, உடற்குழியற்ற, பாரங்கைமா ஆகிய சொற்களை விளக்க.",
            "Describe the protonephridial system with flame cells and its function." to "சுடர் செல்களுடன் கூடிய புரோட்டோநெஃப்ரிடிய அமைப்பையும் அதன் பணியையும் விவரிக்க.",
            "Classify the phylum into Turbellaria, Trematoda and Cestoda with characters and examples." to "தொகுதியை டர்பெல்லேரியா, டிரிமட்டோடா, செஸ்டோடா என்ற வகுப்புகளாகப் பண்புகளுடனும் எடுத்துக்காட்டுகளுடனும் வகைப்படுத்த.",
            "Explain why cestodes have no alimentary canal." to "செஸ்டோடாக்களுக்கு உணவுக்குழல் ஏன் இல்லை என்பதை விளக்க.",
        ),
        introduction = listOf(
            "The flatworms are the first animals in this course to have three germ layers and a body that is bilaterally symmetrical. They are soft, flattened worms with no body cavity, no anus in most species and no circulatory or respiratory system. The phylum includes free-living worms of ponds, streams and the sea, and also the flukes and tapeworms, which are among the most important parasites of people and domestic animals." to
                "இந்தப் பாடத்தில் மூன்று கருப்படலங்களும் இருபக்கச் சமச்சீர் உடலும் கொண்ட முதல் விலங்குகள் தட்டைப்புழுக்கள். இவை மென்மையான, தட்டையான புழுக்கள்; உடற்குழி இல்லை, பெரும்பாலானவற்றுக்கு மலவாய் இல்லை, சுற்றோட்ட, சுவாச மண்டலங்களும் இல்லை. குளங்கள், ஓடைகள், கடல்களில் வாழும் தனி வாழ்வுப் புழுக்களும், மனிதர்கள், வீட்டு விலங்குகளின் மிக முக்கிய ஒட்டுண்ணிகளான தட்டைப்புழுக்கள், நாடாப்புழுக்களும் இத்தொகுதியில் அடங்கும்.",
            "The chapter lists the general characters of the phylum in groups, describes the one organ system that is characteristic of it, the flame-cell excretory system, and then classifies the phylum into three classes. It closes by raising the question why a tapeworm has lost its gut, which leads into the study of parasitic adaptation in the chapters that follow." to
                "அத்தியாயம் தொகுதியின் பொதுப்பண்புகளைக் குழுக்களாகப் பட்டியலிடுகிறது; அதன் தனிப்பண்பான சுடர் செல் கழிவு நீக்க அமைப்பை விவரிக்கிறது; பின்னர் தொகுதியை மூன்று வகுப்புகளாக வகைப்படுத்துகிறது. நாடாப்புழு தன் குடலை ஏன் இழந்தது என்ற கேள்வியுடன் முடிகிறது; இது தொடரும் அத்தியாயங்களில் ஒட்டுண்ணித் தகவமைப்புகளைக் கற்பதற்கு இட்டுச் செல்கிறது.",
        ),
        sections = listOf(
            bsec("characters", "General characters", "பொதுப்பண்புகள்",
                blocks = listOf(
                    bpoints("Symmetry and layers", "சமச்சீரும் அடுக்குகளும்",
                        "Bilaterally symmetrical: the body can be divided into similar right and left halves by one plane only." to "இருபக்கச் சமச்சீர்: உடலை ஒரே தளத்தால் மட்டுமே ஒத்த வலது, இடது பாதிகளாகப் பிரிக்க முடியும்.",
                        "Triploblastic: the body is formed from ectoderm, mesoderm and endoderm." to "மூவடுக்கு: உடல் எக்டோடெர்ம், மீசோடெர்ம், எண்டோடெர்மிலிருந்து உருவாகிறது.",
                        "Dorsoventrally flattened and unsegmented, with a distinct anterior end that bears the sense organs." to "முதுகு-வயிற்றுப் பக்கமாகத் தட்டையானது; கண்டங்களற்றது; உணர் உறுப்புகள் உள்ள தெளிவான முன் முனை உண்டு.",
                        "Organ-system grade of organisation, although some systems are absent." to "உறுப்பு மண்டல நிலை அமைப்பு; சில மண்டலங்கள் இல்லை.",
                    ),
                    bpoints("Body space and covering", "உடல் இடமும் உறையும்",
                        "Acoelomate: there is no true coelom, and the space between the body wall and the gut is filled with parenchyma (mesenchyme)." to "உடற்குழியற்றவை: உண்மையான சீலோம் இல்லை; உடற்சுவருக்கும் குடலுக்கும் இடையிலான இடம் பாரங்கைமாவால் (மீசென்கைமா) நிரம்பியுள்ளது.",
                        "The body is covered by a ciliated epidermis in free-living forms and by a tegument in the parasitic classes." to "தனி வாழ்வு வகைகளில் கசையிழை எப்பிடெர்மிஸாலும், ஒட்டுண்ணி வகுப்புகளில் டெகுமென்ட்டாலும் உடல் மூடப்பட்டுள்ளது.",
                        "Muscle layers lie below the covering: circular, longitudinal and sometimes diagonal fibres." to "மூடலுக்குக் கீழே தசை அடுக்குகள் உள்ளன: வட்டம், நீளவாக்கு, சில நேரங்களில் மூலைவிட்ட இழைகள்.",
                    ),
                    bpoints("Digestion, excretion and circulation", "செரிமானம், கழிவு நீக்கம், சுற்றோட்டம்",
                        "The digestive system is incomplete: a mouth leads to a gut, often branched, which ends blindly, and there is no anus. In cestodes the gut is absent." to "செரிமான மண்டலம் முழுமையற்றது: வாய் பெரும்பாலும் கிளைத்த குடலுக்குச் செல்கிறது; அது குருட்டு முடிவுடையது; மலவாய் இல்லை. செஸ்டோடாக்களில் குடல் இல்லை.",
                        "Excretion and osmoregulation are by protonephridia with flame cells." to "கழிவு நீக்கமும் சவ்வூடுபரவல் ஒழுங்குபடுத்தல்ம் சுடர் செல்களுடன் கூடிய புரோட்டோநெஃப்ரிடியாவால்.",
                        "Circulatory and respiratory systems are absent; gases diffuse through the body surface." to "சுற்றோட்ட, சுவாச மண்டலங்கள் இல்லை; வாயுக்கள் உடல் பரப்பு வழியாக விரவுகின்றன.",
                    ),
                    bpoints("Nervous system, reproduction and habit", "நரம்பு மண்டலம், இனப்பெருக்கம், வாழ்முறை",
                        "The nervous system has an anterior ganglionic mass, or brain, and longitudinal nerve cords joined by cross-connections." to "நரம்பு மண்டலத்தில் முன்புற நரம்பு செல்திரள் அல்லது மூளையும், குறுக்கு இணைப்புகளால் இணைந்த நீளவாக்கு நரம்பு வடங்களும் உள்ளன.",
                        "Most are hermaphrodite, with complex reproductive systems, and fertilisation is internal." to "பெரும்பாலானவை இருபால் உயிரிகள்; சிக்கலான இனப்பெருக்க மண்டலங்கள்; உட்கருவுறுதல்.",
                        "Some live free, but many are ectoparasites or endoparasites, and the parasitic forms have complex life cycles." to "சில தனித்து வாழ்கின்றன; பல புறவொட்டுண்ணிகள் அல்லது அகவொட்டுண்ணிகள்; ஒட்டுண்ணி வடிவங்களுக்குச் சிக்கலான வாழ்க்கைச் சுழற்சிகள்.",
                    ),
                    bdef("Triploblastic", "மூவடுக்கு", "Having a body built from three germ layers: ectoderm, mesoderm and endoderm." to
                        "எக்டோடெர்ம், மீசோடெர்ம், எண்டோடெர்ம் என்ற மூன்று கருப்படலங்களால் ஆன உடல் கொண்ட."),
                    bdef("Acoelomate", "உடற்குழியற்ற", "Having no coelom; the space between body wall and gut is filled with solid tissue." to
                        "சீலோம் இல்லாத; உடற்சுவருக்கும் குடலுக்கும் இடையிலான இடம் திண்ம திசுவால் நிரம்பிய."),
                    bdef("Parenchyma", "பாரங்கைமா", "The loose, solid tissue that fills the space between the gut and body wall of a flatworm." to
                        "தட்டைப்புழுவின் குடலுக்கும் உடற்சுவருக்கும் இடையிலான இடத்தை நிரப்பும் தளர்வான திண்ம திசு."),
                    bdef("Bilateral symmetry", "இருபக்கச் சமச்சீர்", "Symmetry in which only one plane divides the body into similar right and left halves." to
                        "ஒரே தளம் மட்டுமே உடலை ஒத்த வலது, இடது பாதிகளாகப் பிரிக்கும் சமச்சீர்."),
                    bdef("Tegument", "டெகுமென்ட்", "The living, metabolically active outer covering of flukes and tapeworms, which protects the parasite and absorbs nutrients." to
                        "ஃப்ளூக்குகள், நாடாப்புழுக்களின் உயிருள்ள, வளர்சிதை செயலுள்ள வெளி உறை; ஒட்டுண்ணியைப் பாதுகாத்து ஊட்டச்சத்துகளை உறிஞ்சுகிறது."),
                    bdef("Hermaphrodite", "இருபால் உயிரி", "An individual that has both male and female reproductive organs." to
                        "ஆண், பெண் இனப்பெருக்க உறுப்புகள் இரண்டையும் கொண்ட உயிரி."),
                )),
            bsec("flame-cells", "The excretory system and flame cells", "கழிவு நீக்க அமைப்பும் சுடர் செல்களும்",
                blocks = listOf(
                    bp("The excretory organs of flatworms are protonephridia. Each begins in a flame cell, a hollow cell in the parenchyma that has a tuft of beating cilia projecting into its cavity. The beating of the tuft resembles the flicker of a flame when seen under the microscope, and this is how the cell gets its name. The cilia draw fluid in through the fine slits of the wall of the cell and drive it into a capillary tubule. The tubules join to form larger ducts that open to the outside through one or more excretory pores." to
                        "தட்டைப்புழுக்களின் கழிவு நீக்க உறுப்புகள் புரோட்டோநெஃப்ரிடியா. ஒவ்வொன்றும் சுடர் செல்லில் தொடங்குகிறது; பாரங்கைமாவிலுள்ள, குழிக்குள் நீட்டும் அசையும் கசையிழைக் கொத்தைக் கொண்ட வெற்று செல் அது. நுண்ணோக்கியில் இக்கொத்தின் அசைவு சுடரின் படபடப்பைப் போல் தோன்றுவதால் செல்லுக்கு இப்பெயர். கசையிழைகள் செல்சுவரின் நுண் பிளவுகள் வழியாக நீரை இழுத்து நுண்குழாயில் செலுத்துகின்றன. குழாய்கள் இணைந்து பெரிய நாளங்களாகி ஒன்று அல்லது அதற்கு மேற்பட்ட கழிவு நீக்கத் துளைகள் வழியாக வெளியே திறக்கின்றன."),
                    bpoints("Function of the protonephridial system", "புரோட்டோநெஃப்ரிடிய அமைப்பின் பணி",
                        "Osmoregulation: removal of excess water, which is of great importance in the freshwater planarian." to "சவ்வூடுபரவல் ஒழுங்குபடுத்தல்: மிகுதி நீரை அகற்றுதல்; நன்னீர் பிளனேரியாவுக்கு மிக முக்கியம்.",
                        "Excretion: removal of some soluble wastes, although most nitrogenous waste leaves by diffusion across the body surface." to "கழிவு நீக்கம்: சில கரையும் கழிவுகளை அகற்றுதல்; ஆயினும் பெரும்பாலான நைட்ரஜன் கழிவு உடல் பரப்பு வழியாக விரவி வெளியேறுகிறது.",
                    ),
                    bdef("Flame cell", "சுடர் செல்", "A hollow excretory cell with a tuft of cilia that begins a protonephridial tubule." to
                        "புரோட்டோநெஃப்ரிடியக் குழாயைத் தொடங்கும், கசையிழைக் கொத்துடைய வெற்று கழிவு நீக்க செல்."),
                    bdef("Protonephridium", "புரோட்டோநெஃப்ரிடியம்", "An excretory organ that ends blindly in a flame cell, found in flatworms and in some other groups." to
                        "சுடர் செல்லில் குருட்டு முடிவு கொண்ட கழிவு நீக்க உறுப்பு; தட்டைப்புழுக்களிலும் வேறு சில குழுக்களிலும் உள்ளது."),
                    bdef("Osmoregulation", "சவ்வூடுபரவல் ஒழுங்குபடுத்தல்", "The control of the water and salt content of the body." to
                        "உடலின் நீர், உப்பு அளவைக் கட்டுப்படுத்துதல்."),
                )),
            bsec("classification", "Classification into classes", "வகுப்புகளாக வகைப்படுத்துதல்",
                blocks = listOf(
                    bp("The syllabus divides the phylum into three classes, which are the traditional ones. Modern classifications treat the group differently, and in them the Turbellaria are no longer one natural group, but the three-class scheme remains the basis of teaching." to
                        "பாடத்திட்டம் தொகுதியை மூன்று பாரம்பரிய வகுப்புகளாகப் பிரிக்கிறது. நவீன வகைப்பாடுகள் குழுவை வேறுவிதமாகக் கையாளுகின்றன; அவற்றில் டர்பெல்லேரியா இனி ஒரே இயற்கைக் குழு அல்ல; ஆயினும் மூன்று வகுப்பு முறையே கற்பித்தலின் அடிப்படையாக உள்ளது."),
                    btable("The three classes of Platyhelminthes", "பிளாட்டிஹெல்மிந்தீஸின் மூன்று வகுப்புகள்",
                        listOf("Feature" to "பண்பு", "Turbellaria" to "டர்பெல்லேரியா", "Trematoda" to "டிரிமட்டோடா", "Cestoda" to "செஸ்டோடா"),
                        listOf(
                            listOf("Common name" to "பொதுப்பெயர்", "Planarians" to "பிளனேரியன்கள்", "Flukes" to "தட்டைப்புழுக்கள் (ஃப்ளூக்)", "Tapeworms" to "நாடாப்புழுக்கள்"),
                            listOf("Habit" to "வாழ்முறை", "Mostly free-living" to "பெரும்பாலும் தனி வாழ்வு", "Ecto- or endoparasites" to "புற அல்லது அகவொட்டுண்ணிகள்", "Endoparasites" to "அகவொட்டுண்ணிகள்"),
                            listOf("Covering" to "உறை", "Ciliated epidermis" to "கசையிழை எப்பிடெர்மிஸ்", "Tegument" to "டெகுமென்ட்", "Tegument" to "டெகுமென்ட்"),
                            listOf("Alimentary canal" to "உணவுக்குழல்", "Present, branched, no anus" to "உண்டு, கிளைத்தது, மலவாய் இல்லை", "Present, branched, no anus" to "உண்டு, கிளைத்தது, மலவாய் இல்லை", "Absent" to "இல்லை"),
                            listOf("Attachment" to "ஒட்டுதல்", "Adhesive glands in some" to "சிலவற்றில் ஒட்டும் சுரப்பிகள்", "Suckers" to "உறிஞ்சிகள்", "Scolex with suckers and often hooks" to "உறிஞ்சிகளும் பெரும்பாலும் கொக்கிகளும் கொண்ட ஸ்கொலெக்ஸ்"),
                            listOf("Body" to "உடல்", "Unsegmented, leaf-shaped" to "கண்டமற்றது, இலை வடிவம்", "Unsegmented, leaf-shaped" to "கண்டமற்றது, இலை வடிவம்", "Chain of proglottids" to "புரோகுளோட்டிட் சங்கிலி"),
                            listOf("Examples" to "எடுத்துக்காட்டுகள்", "Planaria, Dugesia" to "பிளனேரியா, டுகீசியா", "Fasciola, Schistosoma" to "ஃபாசியோலா, ஸ்கிஸ்டோசோமா", "Taenia, Echinococcus" to "டீனியா, எக்கினோகாக்கஸ்"),
                        )),
                ),
                subs = listOf(
                    bsub("Class Turbellaria", "வகுப்பு டர்பெல்லேரியா",
                        bp("Mostly free-living flatworms, found in fresh water, in the sea and in damp places on land. The body is covered with a ciliated epidermis, which contains rod-like bodies called rhabdites. The worm glides over surfaces on a film of mucus. The mouth is ventral and there is a muscular pharynx. Planaria is the common example, and its power of regeneration is well known." to
                            "பெரும்பாலும் தனி வாழ்வுத் தட்டைப்புழுக்கள்; நன்னீர், கடல், நிலத்தின் ஈரமான இடங்களில் காணப்படுகின்றன. உடல் ராப்டைட்டுகள் எனப்படும் கோல் வடிவ உடலங்கள் கொண்ட கசையிழை எப்பிடெர்மிஸால் மூடப்பட்டுள்ளது. புழு சளிப் படலத்தின் மேல் பரப்புகளில் வழுக்கிச் செல்கிறது. வாய் வயிற்றுப் புறத்தில் உள்ளது; தசைத் தொண்டை உண்டு. பிளனேரியா பொதுவான எடுத்துக்காட்டு; அதன் மீளுருவாக்கத் திறன் நன்கு அறியப்பட்டது."),
                    ),
                    bsub("Class Trematoda", "வகுப்பு டிரிமட்டோடா",
                        bp("The flukes, which are all parasitic. The body is leaf-shaped and unsegmented, covered by a tegument and armed with an oral sucker and a ventral sucker. The gut is forked. The life cycle is complex and includes at least one intermediate host, which is a snail. Fasciola hepatica, the liver fluke of sheep, is the standard example." to
                            "முற்றிலும் ஒட்டுண்ணிகளான ஃப்ளூக்குகள். உடல் இலை வடிவம், கண்டமற்றது, டெகுமென்ட்டால் மூடப்பட்டு வாய் உறிஞ்சியும் வயிற்று உறிஞ்சியும் கொண்டது. குடல் கிளைத்தது. வாழ்க்கைச் சுழற்சி சிக்கலானது; குறைந்தது ஒரு இடைநிலை விருந்தோம்பி, நத்தை, உள்ளது. ஆட்டின் கல்லீரல் ஃப்ளூக்கான ஃபாசியோலா ஹெப்பாட்டிகா நிலையான எடுத்துக்காட்டு."),
                    ),
                    bsub("Class Cestoda", "வகுப்பு செஸ்டோடா",
                        bp("The tapeworms, which are endoparasites of the intestine of vertebrates. The body is long, flat and ribbon-like, with a small head, the scolex, that bears suckers and often hooks, a short neck, and a chain of segments called proglottids that carry the reproductive organs. There is no mouth and no gut. Taenia solium, the pork tapeworm, is the usual example." to
                            "முதுகெலும்பிகளின் குடலின் அகவொட்டுண்ணிகளான நாடாப்புழுக்கள். உடல் நீண்ட, தட்டையான, நாடா வடிவம்; உறிஞ்சிகளும் பெரும்பாலும் கொக்கிகளும் கொண்ட சிறிய தலையான ஸ்கொலெக்ஸ், குறுகிய கழுத்து, இனப்பெருக்க உறுப்புகளைத் தாங்கும் புரோகுளோட்டிட்கள் என்ற துண்டுச் சங்கிலி ஆகியவை உண்டு. வாயும் குடலும் இல்லை. பன்றி நாடாப்புழுவான டீனியா சோலியம் வழக்கமான எடுத்துக்காட்டு."),
                        bdef("Scolex", "ஸ்கொலெக்ஸ்", "The head of a tapeworm, with suckers and often hooks, by which the worm attaches to the intestinal wall of the host." to
                            "நாடாப்புழுவின் தலை; உறிஞ்சிகளும் பெரும்பாலும் கொக்கிகளும் கொண்டு விருந்தோம்பியின் குடல் சுவரில் புழு ஒட்ட உதவுகிறது."),
                        bdef("Proglottid", "புரோகுளோட்டிட்", "One of the segments of a tapeworm, each containing a set of reproductive organs." to
                            "நாடாப்புழுவின் துண்டுகளில் ஒன்று; ஒவ்வொன்றிலும் ஒரு தொகுதி இனப்பெருக்க உறுப்புகள் உள்ளன."),
                    ),
                )),
            bsec("planarian", "A free-living flatworm: the planarian", "தனி வாழ்வுத் தட்டைப்புழு: பிளனேரியன்",
                blocks = listOf(
                    bp("A planarian is a convenient picture of the plan of the phylum before parasitism alters it. The body is a leaf-shaped, flattened worm a centimetre or two long. At the front are two simple eye spots, which respond to light and enable the worm to move away from it, and two lobes at the sides of the head, the auricles, which are sensitive to chemicals and to water currents. The ventral mouth lies about the middle of the body and opens into a muscular pharynx, which can be extended from the mouth to take hold of food. The gut has three main branches, one running forwards and two backwards, each with many side branches that carry food to all parts of the body, which is necessary since there is no circulatory system." to
                        "ஒட்டுண்ணித்தனம் மாற்றும் முன் தொகுதியின் திட்டத்தை பிளனேரியன் வசதியாகக் காட்டுகிறது. உடல் ஓரிரு சென்டிமீட்டர் நீளமுள்ள இலை வடிவ, தட்டையான புழு. முன்னால் ஒளிக்குத் துலங்கி அதிலிருந்து விலகிச் செல்ல உதவும் இரு எளிய கண்புள்ளிகளும், தலையின் பக்கங்களில் வேதிப்பொருட்கள், நீரோட்டத்துக்கு உணர்திறன் உள்ள ஆரிக்கிள் எனும் இரு மடல்களும் உள்ளன. வயிற்றுப்புற வாய் உடலின் நடுவில் உள்ளது; அது உணவைப் பற்றிக்கொள்ள வாயிலிருந்து நீட்டக்கூடிய தசைத் தொண்டைக்குள் திறக்கிறது. குடலில் ஒன்று முன்னோக்கியும் இரண்டு பின்னோக்கியும் செல்லும் மூன்று முதன்மைக் கிளைகள் உள்ளன; ஒவ்வொன்றிலும் பல பக்கக் கிளைகள் உடலின் எல்லாப் பகுதிகளுக்கும் உணவைக் கொண்டு செல்கின்றன; சுற்றோட்ட மண்டலம் இல்லாததால் இது தேவை."),
                    bp("The planarian glides by the beating of the cilia of its ventral surface over a film of slime, and it wriggles by contraction of its muscles. The nervous system has a pair of anterior ganglia and two longitudinal nerve cords connected by cross-nerves, giving a ladder-like arrangement. The free-living flatworms are also famous for their power of regeneration, since a piece cut from a planarian may grow into a whole worm, a property that rests on the presence of undifferentiated cells scattered through the parenchyma." to
                        "பிளனேரியன் வயிற்றுப் பரப்பின் கசையிழைகள் சளிப் படலத்தின் மேல் அசைவதால் வழுக்கிச் செல்கிறது; தசைச் சுருக்கத்தால் நெளிகிறது. நரம்பு மண்டலத்தில் ஒரு இணை முன்புற நரம்புத்திரள்களும், குறுக்கு நரம்புகளால் இணைந்த இரு நீளவாக்கு நரம்பு வடங்களும் உள்ளன; ஏணி போன்ற அமைப்பு. தனி வாழ்வுத் தட்டைப்புழுக்கள் மீளுருவாக்கத் திறனுக்கும் பிரபலமானவை; பிளனேரியனிலிருந்து வெட்டிய துண்டு முழுப் புழுவாக வளரலாம்; இத்திறன் பாரங்கைமா முழுவதும் சிதறிய வேறுபடாத செல்களைச் சார்ந்தது."),
                    bp("Planarians are carnivores and scavengers. The extended pharynx sucks in small animals or fragments of dead ones, and digestion begins in the gut cavity and is completed inside the cells that line it. Undigested remains are returned through the mouth, since there is no anus. This single opening serving both for entry and for exit is a mark of the incomplete gut of the phylum, and it contrasts with the complete gut of the nematodes that are studied later in this unit." to
                        "பிளனேரியன்கள் ஊனுண்ணிகளும் அழுகல் உண்ணிகளும். நீட்டிய தொண்டை சிறு விலங்குகளை அல்லது இறந்தவற்றின் துண்டுகளை உறிஞ்சுகிறது; செரிமானம் குடல் குழியில் தொடங்கி அதை மூடும் செல்களினுள் முடிகிறது. மலவாய் இல்லாததால் செரியாத எச்சங்கள் வாய் வழியாக மீண்டும் வெளியேற்றப்படுகின்றன. நுழைவுக்கும் வெளியேற்றத்துக்கும் ஒரே திறப்பு என்பது தொகுதியின் முழுமையற்ற குடலின் அடையாளம்; இந்த அலகில் பின்னர் கற்கப்படும் நெமட்டோடாக்களின் முழுமையான குடலுடன் இது மாறுபடுகிறது."),
                    bdef("Auricle", "ஆரிக்கிள்", "One of the lateral lobes of the head of a planarian that are sensitive to chemicals and currents." to
                        "வேதிப்பொருட்கள், நீரோட்டத்துக்கு உணர்திறன் உள்ள, பிளனேரியனின் தலையிலுள்ள பக்க மடல்களில் ஒன்று."),
                    bdef("Pharynx", "தொண்டை", "The muscular tube that leads from the mouth to the gut, and that can be extended to seize food." to
                        "வாயிலிருந்து குடலுக்குச் செல்லும் தசைக் குழாய்; உணவைப் பற்றிக்கொள்ள நீட்டப்படலாம்."),
                )),
            bsec("reproduction", "Reproduction and life cycles", "இனப்பெருக்கமும் வாழ்க்கைச் சுழற்சிகளும்",
                blocks = listOf(
                    bp("Nearly all flatworms are hermaphrodite, and an individual has both testes and ovaries with a system of ducts and accessory glands. Self-fertilisation is usually avoided and the worms exchange sperm by copulation. In the free-living forms the egg develops directly into a small worm, but in parasites there is often a series of larval stages, each fitted to a particular host or to a stage in the transmission of the parasite." to
                        "கிட்டத்தட்ட எல்லாத் தட்டைப்புழுக்களும் இருபால் உயிரிகள்; உயிரிக்கு விந்தகங்களும் சூற்பைகளும், நாளங்களும் துணைச் சுரப்பிகளும் உண்டு. தன்கருவுறுதல் பொதுவாகத் தவிர்க்கப்படுகிறது; புழுக்கள் புணர்ச்சி மூலம் விந்தணுக்களைப் பரிமாறிக்கொள்கின்றன. தனி வாழ்வு வகைகளில் முட்டை நேரடியாக சிறு புழுவாக வளர்கிறது; ஒட்டுண்ணிகளில் பெரும்பாலும் ஒவ்வொன்றும் ஒரு விருந்தோம்பிக்கு அல்லது ஒட்டுண்ணிப் பரவலின் ஒரு நிலைக்கு ஏற்ற தொடர் லார்வா நிலைகள் உள்ளன."),
                    bp("The reproductive capacity of the parasites is very large. A single tapeworm releases a great number of eggs, and a liver fluke in the sheep lays eggs for years, while in the snail a single miracidium multiplies asexually to give a great many cercariae. This is not waste. It is the means by which a species compensates for the high chance that any one egg will fail to find the next host." to
                        "ஒட்டுண்ணிகளின் இனப்பெருக்கத் திறன் மிகப் பெரியது. ஒரு நாடாப்புழு பெருந்தொகை முட்டைகளை வெளியிடுகிறது; ஆட்டிலுள்ள கல்லீரல் ஃப்ளூக் பல ஆண்டுகள் முட்டையிடுகிறது; நத்தையில் ஒரு மிராசிடியம் பாலிலா முறையில் பெருகி பல செர்க்கேரியாக்களைத் தருகிறது. இது வீணல்ல. எந்த ஒரு முட்டையும் அடுத்த விருந்தோம்பியைக் கண்டடையாமல் தோல்வியுறும் அதிக வாய்ப்பை ஒரு இனம் ஈடுசெய்யும் வழி இது."),
                )),
            bsec("cestode-gut","Why a tapeworm has no gut", "நாடாப்புழுவுக்கு ஏன் குடல் இல்லை",
                blocks = listOf(
                    bp("An adult tapeworm lives in the intestine of a vertebrate, in the midst of food that the host has already digested. The nutrients that it needs are therefore present as small molecules, ready for absorption, and the parasite takes them up across its tegument. The surface of the tegument is greatly increased by minute projections called microtriches, which provide a large area for absorption. A mouth and a gut would add nothing to this arrangement, and a worm that does not have to maintain them can put its energy into attachment, growth and the production of eggs." to
                        "முதிர்ந்த நாடாப்புழு முதுகெலும்பியின் குடலில், விருந்தோம்பி ஏற்கெனவே செரித்த உணவின் நடுவே வாழ்கிறது. எனவே அதற்குத் தேவையான ஊட்டச்சத்துகள் உறிஞ்சத் தயாரான சிறு மூலக்கூறுகளாக உள்ளன; ஒட்டுண்ணி அவற்றை டெகுமென்ட் வழியாக உறிஞ்சுகிறது. டெகுமென்ட்டின் பரப்பு மைக்ரோடிரிக்கி எனும் நுண் நீட்சிகளால் பெரிதும் அதிகரிக்கப்பட்டு உறிஞ்சலுக்குப் பெரிய பரப்பை அளிக்கிறது. வாயும் குடலும் இந்த ஏற்பாட்டுக்கு எதையும் சேர்க்காது; அவற்றைப் பேண வேண்டியதில்லாத புழு தன் ஆற்றலை ஒட்டுதல், வளர்ச்சி, முட்டை உற்பத்திக்குச் செலுத்த முடிகிறது."),
                    bnote("Specialisation, not degeneration", "சிறப்படைதல்; சீரழிவு அல்ல",
                        "Loss of the gut is a specialised adaptation to an endoparasitic life. It should be described as functional specialisation and not as degeneration, which implies a judgement that the biology does not support." to
                            "குடலின் இழப்பு அகவொட்டுண்ணி வாழ்க்கைக்கான சிறப்புத் தகவமைப்பு. அதை சீரழிவு எனக் கூறாமல் செயல்பாட்டுச் சிறப்படைதல் எனக் கூற வேண்டும்; சீரழிவு என்பது உயிரியல் ஆதரிக்காத மதிப்பீட்டைக் குறிக்கிறது."),
                    bdef("Microtriches", "மைக்ரோடிரிக்கி", "Minute projections of the tegument of a tapeworm that increase the surface area for absorption." to
                        "உறிஞ்சல் பரப்பை அதிகரிக்கும் நாடாப்புழு டெகுமென்ட்டின் நுண் நீட்சிகள்."),
                )),
            bsec("importance", "Why the phylum matters", "தொகுதி ஏன் முக்கியம்",
                blocks = listOf(
                    bp("Flatworms occupy a position of interest in the study of the animal kingdom. They are the simplest animals in which the three germ layers, bilateral symmetry and a distinct head are all present, and they are therefore thought of as a model of the ancestors of the more advanced bilaterians. Their parasitic members are also among the most important of the human parasites in the world, and the liver fluke and tapeworm are therefore studied in some detail in the chapters that follow." to
                        "விலங்கு உலகின் கற்றலில் தட்டைப்புழுக்கள் ஆர்வமூட்டும் இடத்தை வகிக்கின்றன. மூன்று கருப்படலங்கள், இருபக்கச் சமச்சீர், தெளிவான தலை ஆகியவை அனைத்தும் இருக்கும் எளிய விலங்குகள் இவையே; எனவே மேம்பட்ட இருபக்கச் சமச்சீர் விலங்குகளின் மூதாதையர்களின் மாதிரியாகக் கருதப்படுகின்றன. இவற்றின் ஒட்டுண்ணி உறுப்பினர்கள் உலகின் மிக முக்கிய மனித ஒட்டுண்ணிகளிலும் அடங்குகின்றனர்; எனவே கல்லீரல் ஃப்ளூக்கும் நாடாப்புழுவும் தொடரும் அத்தியாயங்களில் விரிவாகக் கற்கப்படுகின்றன."),
                    bp("For the examination, the general characters should be written in an orderly way, preferably in the groups used in this chapter, and the classification should always be accompanied by a distinguishing character and an example for each class. The answer on the absence of a gut in cestodes needs three statements: that the worm lives in the host's intestine, that the food is already digested, and that it is absorbed across the tegument." to
                        "தேர்வுக்கு, பொதுப்பண்புகளை இவ்வத்தியாயத்தில் பயன்படுத்திய குழுக்களில் ஒழுங்காக எழுத வேண்டும்; வகைப்பாட்டில் ஒவ்வொரு வகுப்புக்கும் ஒரு வேறுபடுத்தும் பண்பும் எடுத்துக்காட்டும் எப்போதும் இருக்க வேண்டும். செஸ்டோடாக்களுக்கு குடல் இல்லாதது பற்றிய விடைக்கு மூன்று கூற்றுகள் தேவை: புழு விருந்தோம்பியின் குடலில் வாழ்கிறது; உணவு ஏற்கெனவே செரிக்கப்பட்டுள்ளது; அது டெகுமென்ட் வழியாக உறிஞ்சப்படுகிறது."),
                )),
        ),
        summary = listOf(
            "Platyhelminthes are bilaterally symmetrical, triploblastic, flattened, unsegmented, acoelomate worms." to "பிளாட்டிஹெல்மிந்தீஸ் இருபக்கச் சமச்சீர், மூவடுக்கு, தட்டையான, கண்டமற்ற, உடற்குழியற்ற புழுக்கள்.",
            "The space between body wall and gut is filled with parenchyma." to "உடற்சுவருக்கும் குடலுக்கும் இடையிலான இடம் பாரங்கைமாவால் நிரம்பியுள்ளது.",
            "The gut is incomplete, with no anus, and is absent in cestodes." to "குடல் முழுமையற்றது, மலவாய் இல்லை; செஸ்டோடாக்களில் குடல் இல்லை.",
            "Excretion and osmoregulation are by protonephridia with flame cells." to "கழிவு நீக்கமும் சவ்வூடுபரவல் ஒழுங்குபடுத்தல்ம் சுடர் செல்களுடன் கூடிய புரோட்டோநெஃப்ரிடியாவால்.",
            "Most are hermaphrodite, and the parasitic forms have complex life cycles." to "பெரும்பாலானவை இருபால் உயிரிகள்; ஒட்டுண்ணி வடிவங்களுக்குச் சிக்கலான வாழ்க்கைச் சுழற்சிகள்.",
            "The classes are Turbellaria (free-living), Trematoda (flukes) and Cestoda (tapeworms)." to "வகுப்புகள்: டர்பெல்லேரியா (தனி வாழ்வு), டிரிமட்டோடா (ஃப்ளூக்குகள்), செஸ்டோடா (நாடாப்புழுக்கள்).",
            "Cestodes lack a gut because they absorb already digested food across the tegument; this is specialisation and not degeneration." to "செஸ்டோடாக்கள் ஏற்கெனவே செரித்த உணவை டெகுமென்ட் வழியாக உறிஞ்சுவதால் குடல் இல்லை; இது சிறப்படைதல், சீரழிவு அல்ல.",
        ),
        checks = listOf(
            bqc("What is the space between the gut and the body wall of a flatworm filled with?", "தட்டைப்புழுவின் குடலுக்கும் உடற்சுவருக்கும் இடையிலான இடம் எதனால் நிரம்பியுள்ளது?",
                "Parenchyma (mesenchyme); there is no true coelom.", "பாரங்கைமா (மீசென்கைமா); உண்மையான சீலோம் இல்லை."),
            bqc("Which structure begins a protonephridium?", "புரோட்டோநெஃப்ரிடியத்தைத் தொடங்கும் அமைப்பு எது?",
                "The flame cell.", "சுடர் செல்."),
            bqc("Name the three classes of Platyhelminthes.", "பிளாட்டிஹெல்மிந்தீஸின் மூன்று வகுப்புகளைக் குறிப்பிடுக.",
                "Turbellaria, Trematoda and Cestoda.", "டர்பெல்லேரியா, டிரிமட்டோடா, செஸ்டோடா."),
            bqc("Why can a tapeworm manage without a gut?", "நாடாப்புழு குடல் இல்லாமல் எவ்வாறு சமாளிக்கிறது?",
                "It absorbs the host's already digested food across its tegument, which has microtriches that increase the surface.", "விருந்தோம்பி ஏற்கெனவே செரித்த உணவை, பரப்பை அதிகரிக்கும் மைக்ரோடிரிக்கி உள்ள டெகுமென்ட் வழியாக உறிஞ்சுகிறது."),
        ),
    )
}
