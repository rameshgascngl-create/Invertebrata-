package com.gasczoology.invertebratelab.data

/** Book-style chapter: Phylum Annelida, general characters and classification. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object AnnelidaBookChapter {
    val chapter = bchapter(
        lessonId = "u4-annelida",
        title = "Phylum Annelida: general characters and classification" to "தொகுதி அனெலிடா: பொதுப் பண்புகளும் வகைப்பாடும்",
        objectives = listOf(
            "State the general characters of Phylum Annelida." to "தொகுதி அனெலிடாவின் பொதுப் பண்புகளைக் கூற.",
            "Distinguish true metamerism from simple external ringing." to "உண்மையான உடற்கண்டமாதலை எளிய வெளிப்புற வளையத்திலிருந்து வேறுபடுத்த.",
            "Describe the coelom, septa, setae, excretion and nervous system of annelids." to "அனெலிடாக்களின் உடற்குழி, தடுப்புகள், சீட்டாக்கள், கழிவு நீக்கம், நரம்பு மண்டலத்தை விவரிக்க.",
            "Describe closed circulation in five points." to "மூடிய இரத்த ஓட்டத்தை ஐந்து புள்ளிகளில் விவரிக்க.",
            "Classify the phylum into Polychaeta, Oligochaeta and Hirudinea with characters and examples." to "தொகுதியை பாலிகீட்டா, ஒலிகோகீட்டா, ஹிருடினியா எனப் பண்புகள், எடுத்துக்காட்டுகளுடன் வகைப்படுத்த.",
        ),
        introduction = listOf(
            "The annelids are the segmented worms. The phylum includes the marine bristle worms, the earthworms of the soil and the freshwater and terrestrial leeches, and it contains about seventeen thousand described species, though the numbers are revised from time to time. The name means little rings, and it refers to the appearance of the body, which is divided along its length into a series of similar rings, the segments." to
                "அனெலிடாக்கள் கண்டமுடைய புழுக்கள். தொகுதியில் கடல் முள்மயிர்ப் புழுக்கள், மண்ணின் மண்புழுக்கள், நன்னீர், தரை அட்டைகள் அடங்கும்; விவரிக்கப்பட்ட இனங்கள் சுமார் பதினேழாயிரம், எண்ணிக்கை அவ்வப்போது திருத்தப்படுகிறது. பெயர் சிறு வளையங்கள் எனப் பொருள்படும்; உடல் நீளவாட்டில் ஒத்த வளையங்களாக, கண்டங்களாகப் பிரிந்திருக்கும் தோற்றத்தைக் குறிக்கிறது.",
            "The importance of the group in a course on invertebrates is that it is the first in which the body is built of repeated units that include the internal organs, and not only the outer covering. This chapter lists the characters of the phylum, explains what true segmentation means, describes closed circulation, and then divides the phylum into its three traditional classes. The later chapters of this unit take up the individual types, Nereis and the earthworm, and the advantages and consequences of metamerism." to
                "முதுகெலும்பிலிகள் பாடத்தில் குழுவின் முக்கியத்துவம் என்னவெனில், புறப் போர்வை மட்டுமல்ல, உள் உறுப்புகளும் உட்பட மீள்வரும் அலகுகளால் உடல் கட்டப்பட்ட முதல் குழு இது. இந்த அத்தியாயம் தொகுதியின் பண்புகளைப் பட்டியலிட்டு, உண்மையான கண்டமாதல் என்றால் என்ன என்பதை விளக்கி, மூடிய இரத்த ஓட்டத்தை விவரித்து, தொகுதியை மூன்று மரபு வகுப்புகளாகப் பிரிக்கிறது. இந்த அலகின் பின்வரும் அத்தியாயங்கள் நெரிஸ், மண்புழு ஆகிய வகைகளையும் உடற்கண்டமாதலின் நன்மைகள், விளைவுகளையும் எடுத்துக்கொள்கின்றன.",
        ),
        sections = listOf(
            bsec("general-characters", "General characters", "பொதுப் பண்புகள்",
                blocks = listOf(
                    bp("Annelids are bilaterally symmetrical, triploblastic animals, with a body built of three germ layers, and they are protostomes, in which the mouth develops from or near the blastopore. They have a true coelom, a fluid-filled body cavity lined by tissue of mesodermal origin, and the body shows true metameric segmentation. These features distinguish them from the pseudocoelomate roundworms, and from the flatworms, which have no body cavity." to
                        "அனெலிடாக்கள் இருபக்கச் சமச்சீர் உடைய, மூன்று கருப்படைகளால் கட்டப்பட்ட மூவடுக்குள்ள விலங்குகள்; வாய் பிளாஸ்டோபோரிலிருந்து அல்லது அதன் அருகில் உருவாகும் புரோட்டோஸ்டோம்கள். இவற்றுக்கு மீசோடெர்ம் தோற்றத் திசுவால் உள்ளுறையிடப்பட்ட திரவம் நிறைந்த உண்மையான உடற்குழி உண்டு; உடல் உண்மையான கண்டமாதலைக் காட்டுகிறது. இப்பண்புகள் அவற்றை போலி உடற்குழி உருளைப்புழுக்களிலிருந்தும் உடற்குழி இல்லாத தட்டைப்புழுக்களிலிருந்தும் வேறுபடுத்துகின்றன."),
                    bpoints("Characters to be listed in an answer", "விடையில் பட்டியலிட வேண்டிய பண்புகள்",
                        "Bilaterally symmetrical, triploblastic, protostome and coelomate, with a body divided into segments." to "இருபக்கச் சமச்சீர், மூவடுக்கு, புரோட்டோஸ்டோம், உடற்குழி உடையவை; உடல் கண்டங்களாகப் பிரிந்துள்ளது.",
                        "The coelom is usually divided by transverse septa into compartments, one to a segment." to "உடற்குழி பொதுவாக குறுக்குத் தடுப்புகளால் ஒரு கண்டத்துக்கு ஒன்றாக அறைகளாகப் பிரிக்கப்படுகிறது.",
                        "The body wall has circular and longitudinal muscles, which act against the coelomic fluid as a hydrostatic skeleton." to "உடற்சுவரில் வட்டத் தசைகளும் நீளவாட்டுத் தசைகளும் உள்ளன; அவை உடற்குழி திரவத்துக்கு எதிராக நீரழுத்த எலும்புக்கூடாகச் செயல்படுகின்றன.",
                        "Chitinous bristles, the chaetae or setae, are usually present; many polychaetes carry them on fleshy parapodia." to "கைட்டின் மயிர்களான சீட்டாக்கள் பொதுவாக உண்டு; பல பாலிகீட்டாக்கள் அவற்றைச் சதைப்பற்றுள்ள பாரபோடியாக்களில் கொண்டுள்ளன.",
                        "The alimentary canal is complete, extending from mouth to anus." to "செரிமானக் குழாய் வாயிலிருந்து மலவாய் வரை முழுமையானது.",
                        "Most have a closed circulatory system, with blood in vessels." to "பெரும்பாலானவற்றுக்கு மூடிய இரத்த ஓட்ட மண்டலம் உண்டு; இரத்தம் நாளங்களில் உள்ளது.",
                        "Excretion is chiefly by paired metanephridia in the segments." to "கழிவு நீக்கம் முக்கியமாக கண்டங்களிலுள்ள இணைந்த மெட்டாநெஃப்ரிடியாக்களால்.",
                        "The nervous system has a dorsal brain, a ventral nerve cord and segmental ganglia." to "நரம்பு மண்டலத்தில் முதுகுப்புற மூளை, வயிற்றுப்புற நரம்புவடம், கண்ட நரம்பு செல்திரள்கள் உள்ளன.",
                        "Gas exchange is through the body surface, or the parapodia and gills in some marine forms." to "வாயுப் பரிமாற்றம் உடல் மேற்பரப்பு வழியாக; சில கடல் வடிவங்களில் பாரபோடியா, செவுள்கள் வழியாக.",
                    ),
                    bp("Reproduction differs between the classes and is dealt with under each of them, but a few general statements are safe. Sexes are separate in polychaetes and the gametes are usually shed into the coelom and escape to the water, where fertilisation takes place. Oligochaetes and leeches are hermaphrodite, mate by exchange of sperm, and lay their eggs in a cocoon secreted by the clitellum. Many annelids can regenerate lost parts of the body to some extent, and a few reproduce asexually by fragmentation or budding. The capacity to regenerate varies greatly, and an answer should not claim that all annelids can regrow a whole animal from a piece." to
                        "இனப்பெருக்கம் வகுப்புகளிடையே மாறுபடுகிறது; ஒவ்வொன்றின் கீழும் கையாளப்படுகிறது; ஆனால் சில பொதுக் கூற்றுகள் பாதுகாப்பானவை. பாலிகீட்டாக்களில் பால் தனி; கேமீட்டுகள் பொதுவாக உடற்குழிக்குள் விடுவிக்கப்பட்டு நீருக்குத் தப்பி, அங்கு கருவுறுதல் நடக்கிறது. ஒலிகோகீட்டாக்களும் அட்டைகளும் இருபால் உயிரிகள்; விந்து பரிமாற்றத்தால் இணைந்து, கிளைடெல்லம் சுரக்கும் கூட்டில் முட்டையிடுகின்றன. பல அனெலிடாக்கள் இழந்த உடல் பகுதிகளை ஓரளவு மீளுருவாக்க முடியும்; சில துண்டாதல் அல்லது அரும்பலால் பாலிலா இனப்பெருக்கம் செய்கின்றன. மீளுருவாக்கத் திறன் பெரிதும் மாறுபடுகிறது; ஒரு துண்டிலிருந்து முழு விலங்கையும் அனைத்து அனெலிடாக்களும் மீள வளர்க்கும் என்று விடை கூறக்கூடாது."),
                    bdef("Coelom", "உடற்குழி", "A fluid-filled body cavity lined by tissue derived from mesoderm, lying between the gut and the body wall." to
                        "குடலுக்கும் உடற்சுவருக்கும் இடையே, மீசோடெர்மிலிருந்து தோன்றிய திசுவால் உள்ளுறையிடப்பட்ட, திரவம் நிறைந்த உடற்குழி."),
                    bdef("Protostome", "புரோட்டோஸ்டோம்", "An animal in which the mouth develops from or near the blastopore of the embryo." to
                        "கருவின் பிளாஸ்டோபோரிலிருந்து அல்லது அதன் அருகில் வாய் உருவாகும் விலங்கு."),
                    bdef("Chaeta (seta)", "சீட்டா", "A chitinous bristle that projects from the body wall of an annelid and gives grip in movement." to
                        "அனெலிடாவின் உடற்சுவரிலிருந்து நீண்டு, இயக்கத்தில் பிடிப்பைத் தரும் கைட்டின் மயிர்."),
                    bdef("Metanephridium", "மெட்டாநெஃப்ரிடியம்", "A tubular excretory organ that opens into the coelom by a ciliated funnel and to the outside by a pore." to
                        "கசையிழை புனல் வழியாக உடற்குழிக்குள்ளும் துளை வழியாக வெளியிலும் திறக்கும் குழல் வடிவ கழிவு நீக்க உறுப்பு."),
                    bnote("Leeches and chaetae", "அட்டைகளும் சீட்டாக்களும்",
                        "The statement that annelids have chaetae has exceptions. Leeches have secondarily lost them, and their coelom is reduced by connective tissue, so write that chaetae are usually present." to
                            "அனெலிடாக்களுக்கு சீட்டாக்கள் உண்டு என்பதற்கு விதிவிலக்குகள் உண்டு. அட்டைகள் அவற்றை இரண்டாம் நிலையில் இழந்துவிட்டன; இணைப்புத் திசுவால் அவற்றின் உடற்குழி குறைக்கப்பட்டுள்ளது; எனவே சீட்டாக்கள் பொதுவாக உண்டு என எழுதுக."),
                )),
            bsec("metamerism", "True segmentation", "உண்மையான கண்டமாதல்",
                blocks = listOf(
                    bp("Segmentation, or metamerism, is the repetition along the axis of the body of a set of similar units. The word is used strictly for the annelids. A body that is merely ringed on the outside is not segmented in this sense, and a worm whose surface bears creases but whose organs are not repeated is not an annelid. What is meant by true metamerism is that the units are repeated internally as well. Each segment has its own compartment of the coelom, its own pair of excretory organs, its own ganglion and nerves, and its own set of muscles, vessels and, in many forms, its own gonads." to
                        "கண்டமாதல் அல்லது மெட்டாமெரிசம் என்பது உடலின் அச்சில் ஒத்த அலகுகளின் தொகுப்பு மீள்வருவது. இச்சொல் அனெலிடாக்களுக்குக் கண்டிப்பாகப் பயன்படுத்தப்படுகிறது. வெளியே வளையங்கள் மட்டுமுள்ள உடல் இப்பொருளில் கண்டமுடையதல்ல; மேற்பரப்பில் மடிப்புகள் உள்ளன, ஆனால் உறுப்புகள் மீள்வராத புழு அனெலிடா அல்ல. உண்மையான கண்டமாதல் என்பதன் பொருள், அலகுகள் உள்ளேயும் மீள்வருகின்றன என்பது. ஒவ்வொரு கண்டத்துக்கும் தன் உடற்குழி அறை, தன் இணை கழிவு உறுப்புகள், தன் நரம்புத் திரளும் நரம்புகளும், தன் தசைத் தொகுப்பு, நாளங்கள், பல வடிவங்களில் தன் இனப்பெருக்க சுரப்பிகளும் உண்டு."),
                    bp("The septa that separate the compartments are thin partitions of tissue. They allow the coelomic fluid in each compartment to be put under pressure separately, so that a part of the body can be made rigid, or lengthened, or shortened, while the neighbouring part does the reverse. This is what allows a worm to burrow, and it is the first great advantage of the segmented plan. The segments are not all identical, for the anterior segments are specialised to carry the mouth and the sense organs, and a few segments of the earthworm carry the clitellum. The newest segments are added in front of a terminal region, the pygidium, which carries the anus." to
                        "அறைகளைப் பிரிக்கும் தடுப்புகள் திசுவின் மெல்லிய பிரிவுகள். ஒவ்வொரு அறையிலுள்ள உடற்குழி திரவத்தையும் தனித்தனியாக அழுத்தத்தில் வைக்க இவை அனுமதிக்கின்றன; அதனால் உடலின் ஒரு பகுதி விறைப்பாகவோ நீளவோ சுருங்கவோ செய்யப்படலாம்; அருகிலுள்ள பகுதி எதிர்மாறாகச் செய்கிறது. புழு துளையிட இதுவே அனுமதிக்கிறது; கண்டமுடைய அமைப்பின் முதல் பெரிய நன்மை இது. கண்டங்கள் எல்லாம் ஒரே மாதிரியல்ல; முன்புறக் கண்டங்கள் வாயையும் உணர்வு உறுப்புகளையும் தாங்க சிறப்புற்றவை; மண்புழுவின் சில கண்டங்கள் கிளைடெல்லத்தைத் தாங்குகின்றன. புதிய கண்டங்கள் மலவாயைத் தாங்கும் முனைப் பகுதியான பிஜிடியத்துக்கு முன்னால் சேர்க்கப்படுகின்றன."),
                    bdef("Metamerism", "உடற்கண்டமாதல்", "The repetition of similar body units, each with its own set of organs, along the length of the body." to
                        "உடலின் நீளத்தில், ஒவ்வொன்றும் தன் உறுப்புத் தொகுப்புடன், ஒத்த உடல் அலகுகள் மீள்வருதல்."),
                    bdef("Septum", "தடுப்பு", "A partition of tissue that separates one coelomic compartment from the next." to
                        "ஒரு உடற்குழி அறையை அடுத்ததிலிருந்து பிரிக்கும் திசுத் தடுப்பு."),
                    bdef("Segment (metamere)", "கண்டம்", "One of the repeated units of the body of an annelid." to
                        "அனெலிடாவின் உடலில் மீள்வரும் அலகுகளில் ஒன்று."),
                    bnote("Not every ringed worm is an annelid", "வளையமுள்ள ஒவ்வொரு புழுவும் அனெலிடா அல்ல",
                        "Annulation of the skin alone is not segmentation. The test is whether the coelom, nerves, excretory organs and muscles are repeated." to
                            "தோல் வளையங்கள் மட்டும் கண்டமாதல் அல்ல. உடற்குழி, நரம்புகள், கழிவு உறுப்புகள், தசைகள் மீள்வருகின்றனவா என்பதே சோதனை."),
                )),
            bsec("body-wall", "Body wall, coelom and locomotion", "உடற்சுவர், உடற்குழி, இயக்கம்",
                blocks = listOf(
                    bp("The body wall consists of a thin cuticle secreted by the epidermis, the epidermis itself, a layer of circular muscle and a thicker layer of longitudinal muscle, and the lining of the coelom. The cuticle of annelids is thin and flexible, and unlike that of roundworms it is not shed in a series of moults. Muscles of two kinds in opposition, with the fluid of the coelom as an incompressible skeleton, give two kinds of movement. When the circular muscles contract the segments become thinner and longer, and when the longitudinal muscles contract they become shorter and thicker." to
                        "உடற்சுவர் எபிடெர்மிஸ் சுரக்கும் மெல்லிய புறத்தோல், எபிடெர்மிஸ், வட்டத் தசை அடுக்கு, தடிமனான நீளவாட்டுத் தசை அடுக்கு, உடற்குழியின் உள்ளுறை ஆகியவற்றைக் கொண்டது. அனெலிடாக்களின் புறத்தோல் மெல்லியது, நெகிழ்வானது; உருளைப்புழுக்களைப் போல தோலுரித்தல் தொடரால் நீக்கப்படுவதில்லை. எதிர்த்து இயங்கும் இரு வகைத் தசைகளும் அழுத்த முடியாத எலும்புக்கூடாக உடற்குழி திரவமும் இரு வகை இயக்கம் தருகின்றன. வட்டத் தசைகள் சுருங்கும்போது கண்டங்கள் மெலிந்து நீள்கின்றன; நீளவாட்டுத் தசைகள் சுருங்கும்போது குட்டையாகி தடிக்கின்றன."),
                    bp("Waves of these contractions pass backwards along the body, and the chaetae, which are pushed out in the shortened and thickened segments, grip the ground and prevent the body from slipping backwards. In the polychaetes the parapodia, fleshy lateral outgrowths with bundles of chaetae, add oar-like movements to swimming and crawling. In the leeches, which have lost the chaetae, two suckers take the place of the grip, and the body moves by looping." to
                        "இச்சுருக்கங்களின் அலைகள் உடலில் பின்னோக்கிச் செல்கின்றன; குட்டையாகி தடித்த கண்டங்களில் வெளியே தள்ளப்படும் சீட்டாக்கள் தரையைப் பற்றி உடல் பின்னோக்கி வழுக்குவதைத் தடுக்கின்றன. பாலிகீட்டாக்களில் சீட்டாத் தொகுப்புகளுடன் கூடிய சதைப்பற்றுள்ள பக்க வளர்ச்சிகளான பாரபோடியாக்கள் நீந்துதல், ஊர்தலில் துடுப்பு போன்ற இயக்கங்களைச் சேர்க்கின்றன. சீட்டாக்களை இழந்த அட்டைகளில் இரு உறிஞ்சிகள் பிடிப்பின் இடத்தைப் பெறுகின்றன; உடல் வளைந்து நகர்கிறது."),
                    bdef("Parapodium", "பாரபோடியம்", "A fleshy lateral outgrowth of a segment of a polychaete, carrying chaetae and often gills." to
                        "பாலிகீட்டாவின் கண்டத்தின் சதைப்பற்றுள்ள பக்க வளர்ச்சி; சீட்டாக்களையும் பெரும்பாலும் செவுள்களையும் தாங்குகிறது."),
                    bdef("Hydrostatic skeleton", "நீர்ம சட்டகம்", "A body fluid under pressure that gives the muscles something to act against." to
                        "தசைகள் எதிராகச் செயல்பட ஏதேனும் தரும் அழுத்தத்தில் உள்ள உடல் திரவம்."),
                )),
            bsec("systems", "Gut, excretion, nerves and sense organs", "குடல், கழிவு நீக்கம், நரம்புகள், உணர்வு உறுப்புகள்",
                blocks = listOf(
                    bp("The alimentary canal is a straight tube from the mouth to the anus, and in most annelids it is differentiated into regions, such as the buccal cavity, pharynx, oesophagus, a crop or gizzard in some, and the intestine. The digestive tube runs through the septa, and its absorptive surface is increased in some forms by a fold, the typhlosole, of the dorsal wall of the intestine. Digestion is extracellular." to
                        "செரிமானக் குழாய் வாயிலிருந்து மலவாய் வரை நேரான குழாய்; பெரும்பாலான அனெலிடாக்களில் வாய்க்குழி, தொண்டை, உணவுக்குழல், சிலவற்றில் பயிர் அல்லது அரைப்பான், குடல் எனப் பகுதிகளாகப் பிரிகிறது. செரிமானக் குழாய் தடுப்புகள் வழியே செல்கிறது; சில வடிவங்களில் குடலின் முதுகுச் சுவரின் மடிப்பான டைஃப்ளோசோலால் உறிஞ்சும் பரப்பு அதிகரிக்கிறது. செரிமானம் செல்புறமானது."),
                    bp("Excretion is by metanephridia, which occur as a pair in most segments. Each has a ciliated funnel, the nephrostome, which opens into the coelom of one segment, and a coiled tubule that passes through the septum behind it into the next segment and opens to the outside by a nephridiopore. Waste and some of the useful materials enter at the funnel, the useful materials are reabsorbed in the tubule, and urine is passed to the outside. The nephridia of earthworms are of several kinds, and they also help to regulate the water content of the body." to
                        "கழிவு நீக்கம் பெரும்பாலான கண்டங்களில் இணையாக உள்ள மெட்டாநெஃப்ரிடியாக்களால். ஒவ்வொன்றுக்கும் ஒரு கண்டத்தின் உடற்குழிக்குள் திறக்கும் கசையிழை புனலான நெஃப்ரோஸ்டோமும், பின்னால் உள்ள தடுப்பு வழியாக அடுத்த கண்டத்துக்குச் சென்று நெஃப்ரிடியோதுளை வழியாக வெளியே திறக்கும் சுருண்ட குழலும் உண்டு. கழிவும் சில பயனுள்ள பொருள்களும் புனலில் நுழைகின்றன; பயனுள்ளவை குழலில் மீள உறிஞ்சப்படுகின்றன; சிறுநீர் வெளியே செல்கிறது. மண்புழுக்களின் நெஃப்ரிடியாக்கள் பல வகை; அவை உடலின் நீர் அளவையும் ஒழுங்குபடுத்த உதவுகின்றன."),
                    bp("The nervous system consists of a dorsal bilobed cerebral ganglion, the brain, above the pharynx, a pair of connectives around the pharynx and a ventral nerve cord, which has a ganglion in each segment and gives off segmental nerves. The ventral position of the cord is a feature of annelids, arthropods and some other protostomes. The sense organs include touch receptors, sensitive to chemicals, light-sensitive cells and, in the free-living polychaetes, eyes, tentacles and palps." to
                        "நரம்பு மண்டலம் தொண்டைக்கு மேல் முதுகுப்புற இருகூறு பெருமூளை நரம்புத் திரளான மூளை, தொண்டையைச் சுற்றும் இணைப்பிகள் இரண்டு, ஒவ்வொரு கண்டத்திலும் ஒரு நரம்புத் திரளுடன் கண்ட நரம்புகளை வெளியிடும் வயிற்றுப்புற நரம்புவடம் ஆகியவற்றைக் கொண்டது. வடத்தின் வயிற்றுப்புற நிலை அனெலிடாக்கள், கணுக்காலிகள், வேறு சில புரோட்டோஸ்டோம்களின் பண்பு. உணர்வு உறுப்புகளில் தொடு ஏற்பிகள், வேதிப்பொருள் உணர்வு செல்கள், ஒளி உணர்வு செல்கள், தனிவாழ் பாலிகீட்டாக்களில் கண்கள், உணர்கொம்புகள், பால்ப்கள் அடங்கும்."),
                    bdef("Typhlosole", "டைஃப்ளோசோல்", "A longitudinal fold of the dorsal wall of the intestine of an earthworm that increases the area for absorption." to
                        "உறிஞ்சும் பரப்பை அதிகரிக்கும் மண்புழுக் குடலின் முதுகுச் சுவரிலுள்ள நீளவாட்டு மடிப்பு."),
                    bdef("Nephrostome", "நெஃப்ரோஸ்டோம்", "The ciliated funnel of a nephridium that opens into the coelom." to
                        "உடற்குழிக்குள் திறக்கும் நெஃப்ரிடியத்தின் கசையிழைப் புனல்."),
                )),
            bsec("circulation", "Closed circulation", "மூடிய இரத்த ஓட்டம்",
                blocks = listOf(
                    bpoints("Closed circulation in annelids", "அனெலிடாக்களில் மூடிய இரத்த ஓட்டம்",
                        "In most annelids the blood stays inside vessels and does not flow freely through the main body cavity." to "பெரும்பாலான அனெலிடாக்களில் இரத்தம் நாளங்களுக்குள் இருக்கிறது; முதன்மை உடற்குழி வழியே சுதந்திரமாகப் பாய்வதில்லை.",
                        "A dorsal and a ventral longitudinal vessel are the principal channels, and are joined by transverse segmental vessels." to "முதுகுப்புற, வயிற்றுப்புற நீளவாட்டு நாளங்கள் முதன்மைப் பாதைகள்; குறுக்கு கண்ட நாளங்களால் இணைக்கப்படுகின்றன.",
                        "Smaller vessels and capillary networks supply the gut, the body wall and other tissues." to "சிறிய நாளங்களும் தந்துகி வலைகளும் குடல், உடற்சுவர், பிற திசுக்களுக்கு விநியோகிக்கின்றன.",
                        "Respiratory pigments such as haemoglobin may be dissolved in the plasma and help to carry respiratory gases." to "ஹீமோகுளோபின் போன்ற சுவாச நிறமிகள் பிளாஸ்மாவில் கரைந்து வாயுக்களைக் கொண்டு செல்ல உதவலாம்.",
                        "Contraction of the vessels and associated pumps keeps the blood moving, supporting the active muscles; some annelids show secondary modifications of the plan." to "நாளங்களும் தொடர்புடைய பம்புகளும் சுருங்கி இரத்தத்தை நகர்த்துகின்றன; செயலூக்க தசைகளுக்கு உதவுகின்றன; சில அனெலிடாக்கள் இத்திட்டத்தின் இரண்டாம் நிலை மாற்றங்களைக் காட்டுகின்றன.",
                    ),
                    bp("In the earthworm the dorsal vessel carries blood forwards and is the main contractile vessel, and the ventral vessel carries blood backwards. Several pairs of enlarged, contractile commissural vessels, commonly called hearts, connect the two in the anterior segments. The direction of flow in the dorsal vessel, forward, is a useful point to remember. The word modification in the fifth point refers to the leeches, in which the coelom is filled with tissue and blood flows in sinuses, and to some polychaetes, in which vessels are reduced." to
                        "மண்புழுவில் முதுகுப்புற நாளம் இரத்தத்தை முன்னோக்கிக் கொண்டு செல்லும் முதன்மை சுருங்கும் நாளம்; வயிற்றுப்புற நாளம் பின்னோக்கிக் கொண்டு செல்கிறது. பொதுவாக இதயங்கள் எனப்படும் பல இணை பெரிதாகிய சுருங்கும் இணைப்பு நாளங்கள் முன்புறக் கண்டங்களில் இரண்டையும் இணைக்கின்றன. முதுகுப்புற நாளத்தில் ஓட்டத்தின் திசை முன்னோக்கி என்பது நினைவில் கொள்ளத்தக்கது. ஐந்தாம் புள்ளியிலுள்ள மாற்றம் என்ற சொல் உடற்குழி திசுவால் நிரப்பப்பட்டு இரத்தம் சைனஸ்களில் பாயும் அட்டைகளையும், நாளங்கள் குறைந்த சில பாலிகீட்டாக்களையும் குறிக்கிறது."),
                    bdef("Closed circulatory system", "மூடிய இரத்த ஓட்ட மண்டலம்", "A circulatory system in which the blood is confined to vessels throughout its course." to
                        "இரத்தம் முழுப் பாதையிலும் நாளங்களில் மட்டும் அடங்கும் இரத்த ஓட்ட மண்டலம்."),
                    bdef("Commissural vessel", "இணைப்பு நாளம்", "A transverse vessel linking the dorsal and ventral longitudinal vessels." to
                        "முதுகுப்புற, வயிற்றுப்புற நீளவாட்டு நாளங்களை இணைக்கும் குறுக்கு நாளம்."),
                )),
            bsec("reproduction", "Reproduction and development", "இனப்பெருக்கமும் வளர்ச்சியும்",
                blocks = listOf(
                    bp("The three classes differ in their reproduction, and the differences are a good basis for comparison. Most polychaetes are dioecious, shed the eggs and sperm into the sea water, where fertilisation takes place, and have a free-swimming trochophore larva, which develops into a young worm by the addition of segments. The oligochaetes and the leeches, in contrast, are hermaphrodites, with the male and female organs in the same individual, and they do not have a free larva. They are united by the clitellum, a glandular region of the body wall which secretes the cocoon in which the eggs are laid, and for this reason they are grouped as the clitellates.",
                        "மூன்று வகுப்புகளும் இனப்பெருக்கத்தில் வேறுபடுகின்றன; வேறுபாடுகள் ஒப்பீட்டுக்கு நல்ல அடிப்படை. பெரும்பாலான பாலிகீட்டாக்கள் ஒருபாலிகள்; முட்டைகளையும் விந்தையும் கடல்நீரில் வெளியிடுகின்றன; அங்கு கருவுறுதல் நிகழ்கிறது; நீந்தும் ட்ரோகோஃபோர் லார்வா கண்டங்கள் சேர்வதால் இளம் புழுவாக வளர்கிறது. ஒலிகோகீட்டாக்களும் அட்டைகளும் இதற்கு மாறாக ஒரே உயிரியில் ஆண், பெண் உறுப்புகள் கொண்ட இருபாலிகள்; அவற்றுக்கு சுதந்திர லார்வா இல்லை. முட்டைகள் இடப்படும் கூட்டைச் சுரக்கும் உடற்சுவரின் சுரப்பிப் பகுதியான கிளைட்டெல்லத்தால் அவை ஒன்றுபடுகின்றன; இதனால் அவை கிளைட்டெல்லேட்டுகள் என்று தொகுக்கப்படுகின்றன."),
                    bp("In the earthworm, two worms exchange sperm in copulation, with each one receiving the sperm of the other into its spermathecae, and the clitellum later secretes a cocoon, into which the eggs and the stored sperm pass, so that fertilisation takes place in the cocoon. The young worms hatch from the cocoon as small worms resembling the adult. Self-fertilisation is prevented by the position of the male and female openings and by the exchange of sperm. This is direct development, in which there is no larval stage.",
                        "மண்புழுவில் இரு புழுக்கள் புணர்ச்சியில் விந்தைப் பரிமாறிக்கொள்கின்றன; ஒவ்வொன்றும் மற்றதன் விந்தைத் தன் விந்துப் பைகளுக்குள் பெறுகிறது; கிளைட்டெல்லம் பின்னர் கூட்டைச் சுரக்கிறது; முட்டைகளும் சேமித்த விந்தும் அதற்குள் செல்கின்றன; கூட்டிலேயே கருவுறுதல் நிகழ்கிறது. இளம் புழுக்கள் முதிர்ந்ததை ஒத்த சிறு புழுக்களாக கூட்டிலிருந்து பொரிக்கின்றன. ஆண், பெண் துளைகளின் நிலையாலும் விந்துப் பரிமாற்றத்தாலும் தன்கருவுறுதல் தடுக்கப்படுகிறது. இது லார்வா நிலை இல்லாத நேரடி வளர்ச்சி."),
                    btable("Reproduction in the three classes", "மூன்று வகுப்புகளில் இனப்பெருக்கம்",
                        listOf("Feature" to "பண்பு", "Polychaeta" to "பாலிகீட்டா", "Oligochaeta" to "ஒலிகோகீட்டா", "Hirudinea" to "ஹிருடினியா"),
                        listOf(
                            listOf("Sexes" to "பாலினம்", "Separate" to "தனித்தனி", "Hermaphrodite" to "இருபாலி", "Hermaphrodite" to "இருபாலி"),
                            listOf("Clitellum" to "கிளைட்டெல்லம்", "Absent" to "இல்லை", "Present" to "உண்டு", "Present" to "உண்டு"),
                            listOf("Fertilisation" to "கருவுறுதல்", "External, in water" to "வெளிப்புறம், நீரில்", "In the cocoon" to "கூட்டில்", "In the cocoon" to "கூட்டில்"),
                            listOf("Larva" to "லார்வா", "Trochophore" to "ட்ரோகோஃபோர்", "None" to "இல்லை", "None" to "இல்லை"),
                        )),
                    bdef("Cocoon", "கூடு", "A protective case secreted by the clitellum in which the eggs are laid and develop." to
                        "முட்டைகள் இடப்பட்டு வளரும், கிளைட்டெல்லம் சுரக்கும் பாதுகாப்பு உறை."),
                )),
            bsec("importance", "Annelids and people", "அனெலிடாக்களும் மனிதரும்",
                blocks = listOf(
                    bpoints("Benefits and harm", "நன்மைகளும் தீமைகளும்",
                        "Earthworms improve the soil by mixing, aerating and enriching it, and are used in vermicomposting." to "மண்புழுக்கள் மண்ணைக் கலந்து, காற்றோட்டமாக்கி, செறிவாக்கி மேம்படுத்துகின்றன; மண்புழு உரமாக்கலில் பயன்படுகின்றன.",
                        "Polychaetes are important food for fishes and shrimps and are used as bait and as feed in aquaculture." to "பாலிகீட்டாக்கள் மீன்கள், இறால்களுக்கு முக்கிய உணவு; இரையாகவும் நீர்வள வளர்ப்புத் தீவனமாகவும் பயன்படுகின்றன.",
                        "Medicinal leeches are used in some forms of surgery to relieve congestion of blood and to keep blood flowing." to "மருத்துவ அட்டைகள் சில அறுவை சிகிச்சைகளில் இரத்த நெரிசலைக் குறைக்கவும் இரத்த ஓட்டத்தைத் தொடரச் செய்யவும் பயன்படுகின்றன.",
                        "Hirudin, the anticoagulant from leech saliva, has been studied as a drug." to "அட்டை உமிழ்நீரிலுள்ள இரத்த உறைவு எதிர்ப்பியான ஹிருடின் மருந்தாக ஆராயப்பட்டுள்ளது.",
                        "Some leeches are blood-sucking pests of cattle and people, and land leeches trouble walkers in wet forests." to "சில அட்டைகள் கால்நடைகள், மனிதரின் இரத்தம் உறிஞ்சும் தொந்தரவுகள்; நில அட்டைகள் ஈரக் காடுகளில் நடப்போரைத் தொந்தரவு செய்கின்றன.",
                    ),
                    bp("The earthworm has been called the farmer's friend since the time of Darwin, who devoted his last scientific book to its action on the soil. The application of the idea is seen in vermicomposting, which is now widely practised in India for the recycling of farm and household waste.",
                        "டார்வின் காலத்திலிருந்தே மண்புழு உழவரின் நண்பன் என்று அழைக்கப்படுகிறது; அவர் தன் கடைசி அறிவியல் நூலை மண்ணின் மீதான அதன் செயலுக்கு அர்ப்பணித்தார். இக்கருத்தின் பயன்பாடு மண்புழு உரமாக்கலில் காணப்படுகிறது; பண்ணை, வீட்டுக் கழிவை மறுசுழற்சி செய்ய இது இந்தியாவில் இப்போது பரவலாகப் பின்பற்றப்படுகிறது."),
                )),
            bsec("position", "The annelids in the animal tree", "விலங்கு மரத்தில் அனெலிடாக்கள்",
                blocks = listOf(
                    bp("Annelids are protostomes, and within them they belong to the lophotrochozoans, the great group that also includes the molluscs. The trochophore larva is the evidence for this. The similar trochophore of polychaetes and molluscs, and the spiral cleavage of the eggs, suggest that the two phyla are related, and this has long been used to argue for an ancestor in common. The segmentation of the annelids is therefore not a sign of close relationship to the arthropods, with which they were once united in the group Articulata, and the molecular evidence places the arthropods in a different group, the moulting animals.",
                        "அனெலிடாக்கள் புரோட்டோஸ்டோம்கள்; அவற்றுள் மெல்லுடலிகளையும் உள்ளடக்கிய பெரும் குழுவான லோஃபோட்ரோகோசோவாக்களைச் சேர்ந்தவை. ட்ரோகோஃபோர் லார்வா இதற்கு சான்று. பாலிகீட்டாக்கள், மெல்லுடலிகளின் ஒத்த ட்ரோகோஃபோரும் முட்டைகளின் சுருள் பிளவும் இரு தொகுதிகள் தொடர்புடையவை என்று குறிக்கின்றன; பொது மூதாதைக்கு வாதிட இது நீண்டகாலம் பயன்பட்டுள்ளது. எனவே அனெலிடாக்களின் கண்டமாதல், ஒருகாலத்தில் ஆர்ட்டிகுலேட்டா குழுவில் ஒன்றிணைக்கப்பட்ட ஆர்த்ரோபோடாக்களுடன் நெருங்கிய உறவின் அடையாளம் அல்ல; மூலக்கூறுச் சான்று ஆர்த்ரோபோடாக்களை தோலுரிக்கும் விலங்குகள் எனும் வேறு குழுவில் வைக்கிறது."),
                    bp("The limits of the phylum have also been changed. Several groups that were once treated as separate phyla, including the echiurans or spoon worms and the beard worms or pogonophorans, have been shown by molecular studies to lie within the annelids, and the same is thought to be true of the sipunculans by some authors, though this is debated. The student should use the classification in the prescribed text but may note that it is a simplification.",
                        "தொகுதியின் எல்லைகளும் மாறியுள்ளன. ஒருகாலத்தில் தனித் தொகுதிகளாகக் கருதப்பட்ட எக்கியூரான்கள் அல்லது கரண்டிப் புழுக்கள், தாடிப் புழுக்கள் அல்லது போகோனோஃபோரான்கள் உள்ளிட்ட பல குழுக்கள் மூலக்கூறு ஆய்வுகளால் அனெலிடாக்களுக்குள் இருப்பதாகக் காட்டப்பட்டுள்ளன; சிபுன்குலான்களுக்கும் இது உண்மை என்று சில ஆசிரியர்கள் கருதுகின்றனர்; எனினும் இது விவாதத்துக்குரியது. மாணவர் பரிந்துரைக்கப்பட்ட நூலின் வகைப்பாட்டைப் பயன்படுத்த வேண்டும்; ஆனால் அது எளிமைப்படுத்தல் என்பதைக் குறிப்பிடலாம்."),
                )),
            bsec("classification", "Classification", "வகைப்பாடு",
                blocks = listOf(
                    bp("The phylum is traditionally divided into three classes, Polychaeta, Oligochaeta and Hirudinea. Modern molecular studies show that the relationships are more complicated than the old classification suggests, and that the oligochaetes and leeches are placed within the polychaetes, but the three classes are retained in courses and textbooks because they describe the principal body plans, and the student should know their characters." to
                        "தொகுதி மரபாக மூன்று வகுப்புகளாக, பாலிகீட்டா, ஒலிகோகீட்டா, ஹிருடினியா எனப் பிரிக்கப்படுகிறது. நவீன மூலக்கூறு ஆய்வுகள் உறவுகள் பழைய வகைப்பாடு குறிப்பதை விடச் சிக்கலானவை எனவும், ஒலிகோகீட்டாக்களும் அட்டைகளும் பாலிகீட்டாக்களுக்குள் வைக்கப்படுகின்றன எனவும் காட்டுகின்றன; ஆனால் முதன்மை உடல் அமைப்புகளை விவரிப்பதால் மூன்று வகுப்புகளும் பாடங்களிலும் பாடநூல்களிலும் தக்கவைக்கப்படுகின்றன; மாணவர் அவற்றின் பண்புகளை அறிய வேண்டும்."),
                    btable("Classes of Annelida", "அனெலிடா வகுப்புகள்",
                        listOf("Feature" to "பண்பு", "Polychaeta" to "பாலிகீட்டா", "Oligochaeta" to "ஒலிகோகீட்டா", "Hirudinea" to "ஹிருடினியா"),
                        listOf(
                            listOf("Habitat" to "வாழிடம்", "Mostly marine" to "பெரும்பாலும் கடல்", "Soil and fresh water" to "மண், நன்னீர்", "Fresh water, damp land, some marine" to "நன்னீர், ஈர நிலம், சில கடல்"),
                            listOf("Parapodia" to "பாரபோடியா", "Present" to "உண்டு", "Absent" to "இல்லை", "Absent" to "இல்லை"),
                            listOf("Chaetae" to "சீட்டாக்கள்", "Many, on parapodia" to "பல, பாரபோடியாவில்", "Few, in segments" to "சில, கண்டங்களில்", "Absent" to "இல்லை"),
                            listOf("Head" to "தலை", "Distinct, with eyes and tentacles" to "தெளிவான, கண்கள், உணர்கொம்புகளுடன்", "Not distinct" to "தெளிவற்றது", "Not distinct, with suckers" to "தெளிவற்றது, உறிஞ்சிகளுடன்"),
                            listOf("Clitellum" to "கிளைடெல்லம்", "Absent" to "இல்லை", "Present" to "உண்டு", "Present" to "உண்டு"),
                            listOf("Sexes" to "பால்", "Separate" to "தனிப்பால்", "Hermaphrodite" to "இருபால் உயிரி", "Hermaphrodite" to "இருபால் உயிரி"),
                            listOf("Development" to "வளர்ச்சி", "Usually through a trochophore larva" to "பொதுவாக ட்ரோகோபோர் லார்வா வழி", "Direct, in a cocoon" to "நேரடி, கூட்டில்", "Direct, in a cocoon" to "நேரடி, கூட்டில்"),
                            listOf("Examples" to "எடுத்துக்காட்டுகள்", "Nereis, Arenicola, Chaetopterus" to "நெரிஸ், அரெனிகோலா, கீட்டாப்டெரஸ்", "Pheretima, Lumbricus" to "ஃபெரிடிமா, லம்பிரிகஸ்", "Hirudinaria, Hirudo" to "ஹிருடினாரியா, ஹிருடோ"),
                        )),
                    bp("Polychaeta are the bristle worms, with many chaetae borne on parapodia, a distinct head with sense organs, and usually separate sexes. Oligochaeta have few chaetae, no parapodia, no distinct head and a clitellum that secretes the cocoon, and are hermaphrodite with direct development. Hirudinea, the leeches, have a fixed number of segments, an anterior and a posterior sucker, no chaetae in most and no septa in the usual sense, and are also hermaphrodite and have a clitellum." to
                        "பாலிகீட்டாக்கள் முள்மயிர்ப் புழுக்கள்; பாரபோடியாக்களில் பல சீட்டாக்கள், உணர்வு உறுப்புகளுடன் தெளிவான தலை, பொதுவாக தனிப்பால். ஒலிகோகீட்டாக்களுக்கு சில சீட்டாக்கள், பாரபோடியா இல்லை, தெளிவான தலை இல்லை, கூட்டைச் சுரக்கும் கிளைடெல்லம் உண்டு; இருபால் உயிரிகள், நேரடி வளர்ச்சி. ஹிருடினியா, அட்டைகள், நிலையான கண்ட எண்ணிக்கை, முன், பின் உறிஞ்சிகள், பெரும்பாலானவற்றில் சீட்டாக்கள் இல்லை, வழக்கமான பொருளில் தடுப்புகள் இல்லை; அவையும் இருபால் உயிரிகள், கிளைடெல்லம் உண்டு."),
                    bdef("Clitellum", "கிளைடெல்லம்", "A glandular band of the body wall, found in oligochaetes and leeches, that secretes the cocoon." to
                        "ஒலிகோகீட்டாக்கள், அட்டைகளில் காணப்படும், கூட்டைச் சுரக்கும் உடற்சுவரின் சுரப்பிப் பட்டை."),
                    bdef("Trochophore", "ட்ரோகோபோர்", "The free-swimming ciliated larva of polychaetes and some other protostomes." to
                        "பாலிகீட்டாக்கள், வேறு சில புரோட்டோஸ்டோம்களின் தனி நீந்தும் கசையிழை லார்வா."),
                    bdef("Hermaphrodite", "இருபால் உயிரி", "An animal that has both male and female reproductive organs in the same individual." to
                        "ஒரே உயிரியில் ஆண், பெண் இனப்பெருக்க உறுப்புகள் இரண்டும் உள்ள விலங்கு."),
                )),
        ),
        summary = listOf(
            "Annelids are bilateral, triploblastic, protostome coelomates with true metameric segmentation." to "அனெலிடாக்கள் இருபக்கச் சமச்சீர், மூவடுக்கு, புரோட்டோஸ்டோம், உடற்குழி உடையவை; உண்மையான கண்டமாதல் உடையவை.",
            "True segmentation repeats the coelom, nerves, excretory organs and muscles, not only the surface rings." to "உண்மையான கண்டமாதல் மேற்பரப்பு வளையங்களை மட்டுமல்ல, உடற்குழி, நரம்புகள், கழிவு உறுப்புகள், தசைகளையும் மீள்கிறது.",
            "Septa divide the coelom, and circular and longitudinal muscles act against the coelomic fluid." to "தடுப்புகள் உடற்குழியைப் பிரிக்கின்றன; வட்ட, நீளவாட்டுத் தசைகள் உடற்குழி திரவத்துக்கு எதிராகச் செயல்படுகின்றன.",
            "Metanephridia excrete, and the nervous system has a dorsal brain and ventral ganglionated cord." to "மெட்டாநெஃப்ரிடியாக்கள் கழிவு நீக்குகின்றன; நரம்பு மண்டலத்தில் முதுகுப்புற மூளையும் வயிற்றுப்புற நரம்பு செல்திரள் வடமும் உள்ளன.",
            "Most annelids have a closed circulation with dorsal and ventral vessels linked by segmental vessels." to "பெரும்பாலான அனெலிடாக்களில் முதுகுப்புற, வயிற்றுப்புற நாளங்கள் கண்ட நாளங்களால் இணைந்த மூடிய இரத்த ஓட்டம் உள்ளது.",
            "The phylum is divided into Polychaeta, Oligochaeta and Hirudinea." to "தொகுதி பாலிகீட்டா, ஒலிகோகீட்டா, ஹிருடினியா எனப் பிரிக்கப்படுகிறது.",
        ),
        checks = listOf(
            bqc("What is a septum?", "தடுப்பு என்றால் என்ன?",
                "A partition of tissue between coelomic compartments.", "உடற்குழி அறைகளுக்கிடையே உள்ள திசுத் தடுப்பு."),
            bqc("Name the three classes of Annelida.", "அனெலிடாவின் மூன்று வகுப்புகளைப் பெயரிடுக.",
                "Polychaeta, Oligochaeta and Hirudinea.", "பாலிகீட்டா, ஒலிகோகீட்டா, ஹிருடினியா."),
            bqc("Which excretory organs are typical of annelids?", "அனெலிடாக்களுக்கு வழக்கமான கழிவு உறுப்புகள் எவை?",
                "Segmental metanephridia.", "கண்ட மெட்டாநெஃப்ரிடியாக்கள்."),
            bqc("Why is a worm with ringed skin alone not an annelid?", "தோல் வளையம் மட்டுமுள்ள புழு ஏன் அனெலிடா அல்ல?",
                "Because the internal organs, coelom and nerves are not repeated; true metamerism is internal as well.", "ஏனெனில் உள் உறுப்புகள், உடற்குழி, நரம்புகள் மீள்வதில்லை; உண்மையான கண்டமாதல் உள்ளேயும் உள்ளது."),
        ),
    )
}
