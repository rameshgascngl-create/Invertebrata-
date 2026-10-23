package com.gasczoology.invertebratelab.data

/** Book-style chapter: Asterias, the starfish type study. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object AsteriasBookChapter {
    val chapter = bchapter(
        lessonId = "u5-asterias",
        title = "Asterias: The Starfish" to "அஸ்டீரியாஸ்: நட்சத்திர மீன்",
        objectives = listOf(
            "Describe the external features and distinguish the oral and aboral surfaces." to "வெளிப்புறப் பண்புகளை விவரித்து வாய்ப்புற, மேற்புற மேற்பரப்புகளை வேறுபடுத்த.",
            "Describe the body wall, skeleton and coelom." to "உடற்சுவர், கூடு, உடற்குழியை விவரிக்க.",
            "Trace digestion and the eversible stomach." to "செரிமானத்தையும் புரளும் இரைப்பையையும் தொடர்ந்து விளக்க.",
            "Trace the water-vascular pathway and explain its hydraulic action." to "நீர் குருதியோட்டப் பாதையைத் தொடர்ந்து அதன் நீரழுத்தச் செயலை விளக்க.",
            "Outline the nervous, reproductive systems and regeneration." to "நரம்பு, இனப்பெருக்க அமைப்புகளையும் மீளுருவாக்கத்தையும் சுருக்கமாகக் கூற.",
        ),
        introduction = listOf(
            "Asterias is the common starfish that is used as the type of the class Asteroidea, and its study shows the general plan of an echinoderm in a form that is large enough to dissect. It is a marine animal of rocky and sandy shores, a predator of bivalves, and the example from which the textbook account of the water-vascular system, the eversible stomach and the pentaradial body is taken. The species in the textbooks differ between countries, and the account here is that of a generalised starfish of the genus." to
                "அஸ்டீரியாஸ் அஸ்டீராய்டியா வகுப்பின் வகை உயிரியாகப் பயன்படும் பொதுவான நட்சத்திர மீன்; அதன் ஆய்வு முட்தோலியின் பொதுத் திட்டத்தை வெட்டிப் பார்க்கத்தக்க பெரிய வடிவில் காட்டுகிறது. இது பாறை, மணல் கரைகளின் கடல் விலங்கு; இருவோடுகளை வேட்டையாடுவது; நீர் குருதியோட்ட மண்டலம், புரளும் இரைப்பை, ஐந்தாரை உடல் பற்றிய பாடநூல் விளக்கம் இதிலிருந்து எடுக்கப்படுகிறது. பாடநூல்களில் உள்ள இனங்கள் நாடுகளுக்கிடையே மாறுபடும்; இங்குள்ள விளக்கம் பேரினத்தின் பொதுப்படுத்தப்பட்ட நட்சத்திரமீனுக்குரியது.",
            "The student must keep two surfaces distinct throughout. The oral surface bears the mouth and the ambulacral grooves with their tube feet, and it is applied to the ground or the prey. The aboral surface is the upper surface, and bears the madreporite and the anus. Many errors in diagrams and answers arise from confusing these, and the chapter states the surface for every structure." to
                "மாணவர் இரு மேற்பரப்புகளையும் தொடர்ந்து வேறுபடுத்த வேண்டும். வாய்ப்புற மேற்பரப்பு வாயையும் குழாய்ப்பாதங்களுடன் கூடிய ஆம்புலாக்ரல் பள்ளங்களையும் தாங்கி, நிலத்தின்மேல் அல்லது இரைமேல் அழுத்தப்படுகிறது. மேற்புற மேற்பரப்பு மேல்பக்கம்; மேட்ரிபோரைட்டையும் மலவாயையும் தாங்குகிறது. படங்களிலும் விடைகளிலும் பல தவறுகள் இவற்றைக் குழப்புவதால் வருகின்றன; ஒவ்வொரு அமைப்புக்கும் அவ்வத்தியாயம் மேற்பரப்பைக் குறிப்பிடுகிறது.",
        ),
        sections = listOf(
            bsec("external", "External features", "வெளிப்புறப் பண்புகள்",
                blocks = listOf(
                    bp("The body consists of a central disc and, usually, five arms that are broad at the base and are not sharply marked off from the disc, which is a character of the class. The size of the animal varies with the species, and the colour varies from orange and red to brown and purple. There is no head, and no front or back, since any arm may lead in movement." to
                        "உடல் மைய வட்டையும் வழக்கமாக அடியில் அகன்று வட்டிலிருந்து தெளிவாகப் பிரியாத ஐந்து கரங்களையும் கொண்டது; இது வகுப்பின் பண்பு. விலங்கின் அளவு இனத்தைப் பொறுத்து மாறும்; நிறம் ஆரஞ்சு, சிவப்பிலிருந்து பழுப்பு, ஊதா வரை மாறுபடும். தலை இல்லை; எந்தக் கரமும் இயக்கத்தில் முன்செல்லலாம் என்பதால் முன்-பின்னும் இல்லை."),
                    bfig("asterias/external", "Asterias, external features: the aboral surface with the madreporite, spines and papulae, and the oral surface with the mouth and ambulacral grooves." to "அஸ்டீரியாஸ், வெளிப்புறப் பண்புகள்: மேட்ரிபோரைட், முட்கள், பப்புலாக்களுடன் மேற்புற மேற்பரப்பு; வாய், ஆம்புலாக்ரல் பள்ளங்களுடன் வாய்ப்புற மேற்பரப்பு."),
                    bpoints("The aboral surface", "மேற்புற மேற்பரப்பு",
                        "The madreporite, a sieve-like calcareous plate, lies towards the margin of the disc between two arms." to "மேட்ரிபோரைட், சல்லடை போன்ற சுண்ணாம்புத் தகடு, இரு கரங்களுக்கிடையே வட்டின் விளிம்பை நோக்கி அமைந்துள்ளது.",
                        "The anus is a small opening near the centre of the disc." to "மலவாய் வட்டின் மையத்தருகே சிறு திறப்பு.",
                        "Blunt calcareous spines are scattered over the surface, with pedicellariae at their bases." to "மழுங்கிய சுண்ணாம்பு முட்கள் மேற்பரப்பில் சிதறியுள்ளன; அடியில் பெடிசல்லேரியாக்கள்.",
                        "Thin-walled papulae, or dermal branchiae, project between the ossicles and take part in gas exchange." to "மெல்லிய சுவர் பப்புலாக்கள் அல்லது தோல் செவுள்கள் ஆஸிக்கிள்களுக்கிடையே துருத்தி வாயுப் பரிமாற்றத்தில் பங்கேற்கின்றன.",
                    ),
                    bpoints("The oral surface", "வாய்ப்புற மேற்பரப்பு",
                        "The mouth lies in the centre of the disc, surrounded by a soft peristome." to "வாய் வட்டின் மையத்தில், மென்மையான பெரிஸ்டோம் சூழ, அமைந்துள்ளது.",
                        "An ambulacral groove runs along the middle of each arm from the mouth to the tip." to "ஒவ்வொரு கரத்தின் நடுவிலும் வாயிலிருந்து நுனி வரை ஆம்புலாக்ரல் பள்ளம் செல்கிறது.",
                        "Rows of tube feet lie in the groove, protected at the sides by movable spines." to "பள்ளத்தில் குழாய்ப்பாதங்களின் வரிசைகள் உள்ளன; பக்கங்களில் அசையும் முட்கள் பாதுகாக்கின்றன.",
                        "At the tip of each arm a short terminal tentacle and a red eyespot, which detects light, are present." to "ஒவ்வொரு கர நுனியிலும் குறுகிய இறுதி உணர்கொம்பும் ஒளியை உணரும் சிவப்புக் கண்புள்ளியும் உள்ளன.",
                    ),
                    bdef("Disc", "வட்டு", "The central region of the starfish body from which the arms radiate." to
                        "கரங்கள் கதிர்போல் பிரியும் நட்சத்திர மீன் உடலின் மையப் பகுதி."),
                    bdef("Ambulacral groove", "ஆம்புலாக்ரல் பள்ளம்", "The groove along the oral side of each arm that holds the tube feet." to
                        "குழாய்ப்பாதங்களைக் கொண்ட, ஒவ்வொரு கரத்தின் வாய்ப்புறத்தில் செல்லும் பள்ளம்."),
                    bdef("Peristome", "பெரிஸ்டோம்", "The soft membranous area surrounding the mouth." to
                        "வாயைச் சுற்றிய மென்மையான சவ்வுப் பகுதி."),
                    bdef("Eyespot", "கண்புள்ளி", "A simple photoreceptive organ at the tip of each arm." to
                        "ஒவ்வொரு கர நுனியிலும் உள்ள எளிய ஒளியுணர் உறுப்பு."),
                )),
            bsec("body-wall", "Body wall, skeleton and coelom", "உடற்சுவர், கூடு, உடற்குழி",
                blocks = listOf(
                    bp("The body wall has an outer layer of ciliated epidermis, beneath which lies a dermis of connective tissue that contains the ossicles. The ossicles form an open network, and they are held together by connective tissue and muscle so that the arms are flexible but are held in shape when needed. A muscular layer of circular and longitudinal fibres lies deeper, and is lined by the peritoneum of the coelom. The spines are outgrowths of the ossicles covered by the epidermis, and the pedicellariae keep the surface, and the papulae in particular, free of settling particles and larvae." to
                        "உடற்சுவரின் வெளி அடுக்கு கசையிழை மேற்றோல்; அதற்கு அடியில் ஆஸிக்கிள்களைக் கொண்ட இணைப்புத் திசு தோல் உள்ளது. ஆஸிக்கிள்கள் திறந்த வலைப்பின்னலாக அமைந்து இணைப்புத் திசுவாலும் தசையாலும் பிடிக்கப்படுகின்றன; எனவே கரங்கள் வளையும், தேவைப்படும்போது வடிவத்தில் நிற்கும். ஆழத்தில் வட்ட, நீள் இழைகளாலான தசை அடுக்கு உள்ளது; உடற்குழியின் பெரிட்டோனியம் அதை வரிசையிடுகிறது. முட்கள் மேற்றோலால் மூடப்பட்ட ஆஸிக்கிள்களின் வளர்ச்சிகள்; பெடிசல்லேரியாக்கள் மேற்பரப்பையும், குறிப்பாக பப்புலாக்களையும், படியும் துகள்கள், லார்வாக்களிலிருந்து விடுபட்டு வைக்கின்றன."),
                    bp("The coelom is a large cavity that occupies the disc and extends into each arm, and it is filled with coelomic fluid that contains coelomocytes. It surrounds the gut and the gonads, and it extends into the papulae, where the fluid comes close to the sea water for gas exchange. The coelom is ciliated, and the beating of the cilia circulates the fluid. There is no excretory organ of the kind seen in an annelid, and the waste is taken by the coelomocytes, which migrate to the surface, particularly through the papulae, and are shed." to
                        "உடற்குழி வட்டை நிரப்பி ஒவ்வொரு கரத்துக்குள்ளும் நீளும் பெரிய குழி; கொலோமோசைட்டுகள் அடங்கிய உடற்குழித் திரவம் நிரம்பியுள்ளது. அது குடலையும் இனப்பெருக்கச் சுரப்பிகளையும் சூழ்கிறது; பப்புலாக்களுக்குள்ளும் நீள்கிறது; அங்கு திரவம் வாயுப் பரிமாற்றத்துக்காக கடல்நீரை நெருங்குகிறது. உடற்குழி கசையிழை கொண்டது; கசையிழை அசைவு திரவத்தைச் சுற்றோட்டுகிறது. வளைதசைப் புழுவில் உள்ளது போன்ற வெளியேற்ற உறுப்பு இல்லை; கொலோமோசைட்டுகள் கழிவை எடுத்து, குறிப்பாக பப்புலாக்கள் வழியே மேற்பரப்புக்குச் சென்று, வெளியேற்றப்படுகின்றன."),
                    bdef("Dermis", "தோல்", "The connective tissue layer of the body wall beneath the epidermis, containing the ossicles." to
                        "மேற்றோலுக்கு அடியில் ஆஸிக்கிள்களைக் கொண்ட உடற்சுவரின் இணைப்புத் திசு அடுக்கு."),
                    bdef("Peritoneum", "பெரிட்டோனியம்", "The epithelial lining of the coelom." to
                        "உடற்குழியின் மேலணி வரிசை."),
                    bdef("Coelomic fluid", "உடற்குழித் திரவம்", "The fluid that fills the coelom and carries coelomocytes, gases and food." to
                        "உடற்குழியை நிரப்பி கொலோமோசைட்டுகள், வாயுக்கள், உணவைக் கடத்தும் திரவம்."),
                )),
            bsec("digestion", "Feeding and digestion", "உணவெடுப்பும் செரிமானமும்",
                blocks = listOf(
                    bp("Asterias feeds mainly on bivalves, which it opens by pulling on the valves with its tube feet, using the strong attachment of the feet and the muscles of the arms. A very small gap between the valves is enough, and the starfish then pushes its stomach out of the mouth, through the gap, and over the soft tissue of the prey. Digestive enzymes are secreted onto the prey, which is digested outside the starfish, and the partly digested food is taken in. The eversible stomach is the feature for which the animal is best known, and it is the basis for the statement that the starfish digests the prey externally." to
                        "அஸ்டீரியாஸ் முக்கியமாக இருவோடுகளை உண்கிறது; குழாய்ப்பாதங்களின் வலிமையான பற்றுதலையும் கரங்களின் தசைகளையும் பயன்படுத்தி மூடிகளை இழுத்துத் திறக்கிறது. மூடிகளுக்கிடையே மிகச் சிறு இடைவெளியே போதும்; நட்சத்திர மீன் பின்னர் தன் இரைப்பையை வாய் வழியே, இடைவெளி வழியே, இரையின் மென்திசுவின்மேல் வெளியே தள்ளுகிறது. செரிமான நொதிகள் இரைமீது சுரக்கப்பட்டு, நட்சத்திரமீனுக்கு வெளியே செரிக்கப்படுகிறது; பகுதி செரிந்த உணவு உள்ளெடுக்கப்படுகிறது. புரளும் இரைப்பையே விலங்கு மிகவும் அறியப்படும் பண்பு; நட்சத்திர மீன் இரையை வெளிப்புறமாகச் செரிக்கிறது என்ற கூற்றின் அடிப்படை இதுவே."),
                    bfig("asterias/internal", "Asterias, internal organs: the cardiac and pyloric stomachs, the pyloric caeca in the arms, and the gonads." to "அஸ்டீரியாஸ், உள்ளுறுப்புகள்: கார்டியாக், பைலோரிக் இரைப்பைகள், கரங்களிலுள்ள பைலோரிக் சீக்கா, இனப்பெருக்கச் சுரப்பிகள்."),
                    bpoints("The parts of the gut", "குடலின் பகுதிகள்",
                        "The mouth opens into a short oesophagus and a large, folded cardiac stomach, which is the part that is everted." to "வாய் குறுகிய உணவுக்குழலிலும் பெரிய, மடிந்த கார்டியாக் இரைப்பையிலும் திறக்கிறது; புரளுவது இப்பகுதியே.",
                        "The cardiac stomach is held to the walls of the arms by retractor muscles and mesenteries, which pull it back after feeding." to "கார்டியாக் இரைப்பை இழுப்புத் தசைகளாலும் குடலிணைப்புகளாலும் கரச் சுவர்களில் பிடிக்கப்பட்டுள்ளது; உண்ட பின் அவை அதை உள்ளே இழுக்கின்றன.",
                        "Above it lies a smaller, flatter pyloric stomach, which receives ducts from the pyloric caeca." to "அதற்கு மேலே சிறிய, தட்டையான பைலோரிக் இரைப்பை உள்ளது; பைலோரிக் சீக்காவின் குழாய்களைப் பெறுகிறது.",
                        "Two pyloric caeca lie in each arm and are the main site of secretion of enzymes, absorption and storage." to "ஒவ்வொரு கரத்திலும் இரு பைலோரிக் சீக்கா அமைந்து நொதிச் சுரப்பு, உறிஞ்சுதல், சேமிப்பின் முக்கிய இடமாகின்றன.",
                        "A short intestine and rectum lead to the anus on the aboral surface, and small rectal caeca may be present." to "குறுகிய குடலும் மலக்குடலும் மேற்புறத்திலுள்ள மலவாயை நோக்கிச் செல்கின்றன; சிறு மலக்குடல் சீக்காக்கள் இருக்கலாம்.",
                    ),
                    bp("Digestion is therefore partly extracellular outside the body and partly extracellular in the stomachs and caeca, with absorption in the caeca. The anus is small and in many starfishes is of little use, since much of the waste is removed by other routes, and so the aboral position of the anus is better regarded as a structural feature than as the main route of waste disposal. The food reserves are stored in the pyloric caeca, and the animal can live for some time without feeding." to
                        "எனவே செரிமானம் ஒரு பகுதி உடலுக்கு வெளியே, ஒரு பகுதி இரைப்பைகள், சீக்காக்களில் புறவுயிரணுவாகவும், உறிஞ்சுதல் சீக்காக்களிலும் நடைபெறுகிறது. மலவாய் சிறியது; பல நட்சத்திர மீன்களில் குறைந்த பயன் உடையது; ஏனெனில் கழிவின் பெரும்பகுதி பிற வழிகளில் அகற்றப்படுகிறது; எனவே மலவாயின் மேற்புற நிலை கழிவகற்றலின் முக்கிய வழியை விட அமைப்புப் பண்பாகக் கருதப்படுவது சிறந்தது. உணவுச் சேமிப்பு பைலோரிக் சீக்காக்களில் உள்ளது; விலங்கு சிறிது காலம் உண்ணாமல் வாழலாம்."),
                    bdef("Cardiac stomach", "கார்டியாக் இரைப்பை", "The large eversible stomach of a starfish, which is pushed out over the prey." to
                        "இரைமேல் வெளியே தள்ளப்படும் நட்சத்திரமீனின் பெரிய புரளும் இரைப்பை."),
                    bdef("Pyloric stomach", "பைலோரிக் இரைப்பை", "The smaller stomach above the cardiac stomach that receives the ducts of the pyloric caeca." to
                        "கார்டியாக் இரைப்பைக்கு மேலுள்ள, பைலோரிக் சீக்கா குழாய்களைப் பெறும் சிறிய இரைப்பை."),
                    bdef("Pyloric caecum", "பைலோரிக் சீக்கம்", "One of the paired digestive glands in the arms where enzymes are secreted and food is absorbed and stored." to
                        "நொதிகள் சுரந்து உணவு உறிஞ்சப்பட்டு சேமிக்கப்படும், கரங்களிலுள்ள இணைச் செரிமானச் சுரப்பிகளில் ஒன்று."),
                    bdef("Extra-oral digestion", "வாய்க்கு வெளிப்புறச் செரிமானம்", "Digestion of food outside the body by enzymes secreted onto it." to
                        "உணவின்மீது சுரக்கப்படும் நொதிகளால் உடலுக்கு வெளியே நடைபெறும் செரிமானம்."),
                )),
            bsec("wvs", "The water-vascular system", "நீர் குருதியோட்ட மண்டலம்",
                blocks = listOf(
                    bp("The water-vascular system is the characteristic locomotory organ system. Sea water communicates with it through the sieve-like madreporite on the aboral surface. A stone canal, which has calcareous deposits in its wall, passes from the madreporite to the ring canal that surrounds the mouth, and from the ring canal a radial canal runs along the ambulacral region of each arm. Short lateral canals, which are provided with valves, join the radial canal to the ampulla and tube foot units, and each tube foot is connected to its ampulla through the skeleton." to
                        "நீர் குருதியோட்ட மண்டலம் இயக்க உறுப்பு அமைப்பின் தனிப்பண்பு. மேற்புறத்திலுள்ள சல்லடை போன்ற மேட்ரிபோரைட் வழியே கடல்நீர் அதனுடன் தொடர்புகொள்கிறது. சுவரில் சுண்ணாம்புப் படிவுகளுடைய கல் கால்வாய் மேட்ரிபோரைட்டிலிருந்து வாயைச் சுற்றிய வளையக் கால்வாய்க்குச் செல்கிறது; வளையக் கால்வாயிலிருந்து ஒவ்வொரு கரத்தின் ஆம்புலாக்ரல் பகுதியிலும் கதிர்க் கால்வாய் செல்கிறது. வால்வுகளைக் கொண்ட குறுகிய பக்கக் கால்வாய்கள் கதிர்க் கால்வாயை ஆம்புலா, குழாய்ப்பாத அலகுகளுடன் இணைக்கின்றன; ஒவ்வொரு குழாய்ப்பாதமும் கூட்டின் வழியே அதன் ஆம்புலாவுடன் இணைந்துள்ளது."),
                    bfig("asterias/water-vascular-system", "The water-vascular system of Asterias: madreporite, stone canal, ring canal, radial canals, ampullae and tube feet." to "அஸ்டீரியாஸின் நீர் குருதியோட்ட மண்டலம்: மேட்ரிபோரைட், கல் கால்வாய், வளையக் கால்வாய், கதிர்க் கால்வாய்கள், ஆம்புலாக்கள், குழாய்ப்பாதங்கள்."),
                    bp("The action is hydraulic. The valve in the lateral canal closes when the ampulla contracts, so the fluid is driven into the tube foot, which extends. The foot then attaches to the surface by its terminal disc, and its own longitudinal muscles contract to shorten the foot and pull the body forward. Hundreds of feet working in sequence, but not in perfect step, move the animal. Attachment is by adhesion, with a chemical secretion that is released and removed, and not simply by suction, though the disc and its muscles have some part in the grip. It is wrong to describe the system as a continuous one-way stream of sea water that flows through the animal, since the fluid inside is largely a closed volume that is moved by pressure." to
                        "செயல் நீரழுத்தமானது. ஆம்புலா சுருங்கும்போது பக்கக் கால்வாயின் வால்வு மூடுகிறது; எனவே திரவம் குழாய்ப்பாதத்துக்குள் தள்ளப்பட்டு அது நீள்கிறது. பாதம் பின்னர் தன் இறுதி வட்டால் மேற்பரப்பில் ஒட்டுகிறது; அதன் நீள்தசைகள் சுருங்கி பாதத்தைச் சுருக்கி உடலை முன்னே இழுக்கின்றன. நூற்றுக்கணக்கான பாதங்கள் வரிசையாக, ஆனால் முழுமையான ஒத்திசைவின்றி, செயல்பட்டு விலங்கை நகர்த்துகின்றன. பற்றுதல் வெளியிடப்பட்டு அகற்றப்படும் வேதிச் சுரப்பால் ஒட்டுதலாகும்; வெறும் உறிஞ்சுதலால் அல்ல; வட்டும் அதன் தசைகளும் பிடிப்பில் ஓரளவு பங்கு வகிக்கின்றன. அமைப்பை விலங்கு வழியே ஓடும் தொடர்ச்சியான ஒருதிசை கடல்நீர் ஓட்டமாக விவரிப்பது தவறு; உள்ளே இருக்கும் திரவம் பெரும்பாலும் அழுத்தத்தால் நகர்த்தப்படும் மூடிய கன அளவு."),
                    bdef("Ring canal", "வளையக் கால்வாய்", "The circular canal of the water-vascular system that surrounds the mouth." to
                        "வாயைச் சுற்றியுள்ள நீர் குருதியோட்ட மண்டலத்தின் வட்டக் கால்வாய்."),
                    bdef("Radial canal", "கதிர்க் கால்வாய்", "A canal that runs from the ring canal along each arm." to
                        "வளையக் கால்வாயிலிருந்து ஒவ்வொரு கரத்திலும் செல்லும் கால்வாய்."),
                    bdef("Stone canal", "கல் கால்வாய்", "The calcified canal joining the madreporite to the ring canal." to
                        "மேட்ரிபோரைட்டை வளையக் கால்வாயுடன் இணைக்கும் சுண்ணாம்பேறிய கால்வாய்."),
                    bdef("Podium", "போடியம்", "The extensible tube-foot portion outside the skeleton." to
                        "கூட்டுக்கு வெளியே நீளக்கூடிய குழாய்ப்பாதப் பகுதி."),
                )),
            bsec("other-systems", "Circulation, nervous system and reproduction", "சுற்றோட்டம், நரம்பு அமைப்பு, இனப்பெருக்கம்",
                blocks = listOf(
                    bp("The circulatory system is a haemal system, consisting of an oral ring and an aboral ring, together with radial vessels that lie in sinuses. These are spaces, not true vessels with a muscular wall, and transport is slow. An axial organ lies near the stone canal and is of uncertain function, and the fluid of the coelom has a greater share in transport. The system should be described in outline, and details that textbooks give differ between authors." to
                        "சுற்றோட்ட அமைப்பு ஹீமல் அமைப்பு; வாய் வளையம், மேற்புற வளையம், சைனஸ்களில் அமையும் கதிர் நாளங்களைக் கொண்டது. இவை இடைவெளிகள்; தசைச் சுவருடைய உண்மை நாளங்கள் அல்ல; கடத்தல் மெதுவானது. கல் கால்வாய் அருகே அச்சு உறுப்பு உள்ளது; அதன் செயல் உறுதியற்றது; கடத்தலில் உடற்குழித் திரவத்துக்கு அதிகப் பங்கு உண்டு. அமைப்பைச் சுருக்கமாக விவரிக்க வேண்டும்; பாடநூல்கள் தரும் விவரங்கள் ஆசிரியர்களுக்கிடையே மாறுபடும்."),
                    bp("The nervous system is simple and has no brain. A nerve ring surrounds the mouth, and a radial nerve runs along the oral side of each arm, lying beneath the ambulacral groove and ending at the eyespot. There are also nerve plexuses in the epidermis and the tube feet. The coordination of the feet depends on this system, since if the animal is turned over it rights itself by a sequence of movements of the arms and feet." to
                        "நரம்பு அமைப்பு எளியது; மூளை இல்லை. வாயைச் சுற்றி நரம்பு வளையம் உள்ளது; ஒவ்வொரு கரத்தின் வாய்ப்புறத்திலும் ஆம்புலாக்ரல் பள்ளத்தின் கீழ் அமைந்து கண்புள்ளியில் முடியும் கதிர் நரம்பு செல்கிறது. மேற்றோலிலும் குழாய்ப்பாதங்களிலும் நரம்புப் பின்னல்களும் உள்ளன. பாதங்களின் ஒருங்கிணைப்பு இவ்வமைப்பைச் சார்ந்தது; ஏனெனில் விலங்கு புரட்டப்பட்டால் கரங்கள், பாதங்களின் தொடர் அசைவுகளால் தன்னைச் சரிசெய்துகொள்கிறது."),
                    bp("The sexes are separate, though the two sexes cannot be told apart by the outer appearance. Each arm contains two gonads, so that ten are present, and a short duct from each opens through a gonopore on the aboral surface between the bases of the arms. The eggs and sperm are shed into the sea, where fertilisation takes place, and the zygote develops into a free-swimming bilateral larva, the bipinnaria, which later metamorphoses. The life cycle is treated fully in the chapter on echinoderm larvae." to
                        "பாலினங்கள் தனி; ஆனால் வெளித்தோற்றத்தால் இருபாலையும் வேறுபடுத்த முடியாது. ஒவ்வொரு கரத்திலும் இரு இனப்பெருக்கச் சுரப்பிகள் இருப்பதால் பத்து உள்ளன; ஒவ்வொன்றிலிருந்தும் குறுகிய குழாய் கரங்களின் அடிகளுக்கிடையே மேற்புறத்திலுள்ள இனப்பெருக்கத் துளை வழியே திறக்கிறது. முட்டையும் விந்தும் கடலில் விடப்பட்டு கருவுறுதல் நடைபெறுகிறது; கருமுட்டை தனி நீந்தும் இருபக்க லார்வாவான பிபின்னேரியாவாக வளர்ந்து பின்னர் உருமாறுகிறது. வாழ்க்கைச் சுழற்சி முட்தோலி லார்வாக்கள் அத்தியாயத்தில் முழுமையாகக் கையாளப்படுகிறது."),
                    bfig("asterias/larval-development", "Larval development of Asterias: the bipinnaria and its metamorphosis into a young starfish." to "அஸ்டீரியாஸின் லார்வா வளர்ச்சி: பிபின்னேரியாவும் இளம் நட்சத்திரமீனாக அதன் உருமாற்றமும்."),
                    bdef("Gonopore", "இனப்பெருக்கத் துளை", "The opening through which the gametes leave the gonad." to
                        "இனச்செல்கள் சுரப்பியிலிருந்து வெளியேறும் துளை."),
                    bdef("Haemal system", "ஹீமல் அமைப்பு", "A system of sinuses and channels with a role in transport, reduced in echinoderms." to
                        "கடத்தலில் பங்குடைய, முட்தோலிகளில் குறைந்துள்ள சைனஸ்கள், வழிகளின் அமைப்பு."),
                    bdef("Righting reflex", "தன்னைச் சரிசெய்தல்", "The sequence of movements by which an overturned starfish regains its normal position." to
                        "புரட்டப்பட்ட நட்சத்திர மீன் இயல்பு நிலையை மீண்டும் பெறும் அசைவுத் தொடர்."),
                )),
            bsec("regeneration", "Regeneration and habits", "மீளுருவாக்கமும் பழக்கங்களும்",
                blocks = listOf(
                    bp("A starfish that loses an arm can regenerate it, and the animal can shed an arm at a breakage plane when it is seized. In some species a detached arm with part of the central disc can regenerate a whole animal, and the common saying that cutting a starfish into pieces makes more starfishes is true only in this restricted sense, since a piece of arm without part of the disc will not do so in most species. Asexual reproduction by division of the disc is known in a few starfishes, though not as a usual means of reproduction in Asterias." to
                        "கரம் இழந்த நட்சத்திர மீன் அதை மீளுருவாக்க முடியும்; பிடிக்கப்படும்போது விலங்கு உடைவுத் தளத்தில் ஒரு கரத்தை உதிர்க்கலாம். சில இனங்களில் மைய வட்டின் ஒரு பகுதியுடன் பிரிந்த கரம் முழு விலங்கையும் மீளுருவாக்கலாம்; நட்சத்திரமீனைத் துண்டுகளாக வெட்டினால் அதிக நட்சத்திர மீன்கள் உருவாகும் என்ற பொதுப் பேச்சு இந்தக் கட்டுப்படுத்தப்பட்ட பொருளில் மட்டுமே உண்மை; பெரும்பாலான இனங்களில் வட்டுப் பகுதி இன்றி கரத் துண்டு அவ்வாறு செய்யாது. வட்டுப் பிளவால் பாலிலா இனப்பெருக்கம் சில நட்சத்திர மீன்களில் அறியப்படுகிறது; ஆனால் அஸ்டீரியாஸில் வழக்கமான வழி அல்ல."),
                    bp("The ecological effect of starfishes on shellfish beds has long been noted, because predation on bivalves can be heavy, and the animals have been a problem for some shellfish growers. At the same time their removal can change a shore community, which is why some starfishes are used in the idea of the keystone predator." to
                        "இருவோடு வேட்டை அதிகமாக இருக்கலாம் என்பதால் சிப்பிப் படுகைகளில் நட்சத்திர மீன்களின் சூழலியல் விளைவு நீண்ட காலமாகக் கவனிக்கப்படுகிறது; சில சிப்பி வளர்ப்பாளர்களுக்கு விலங்குகள் சிக்கலாக இருந்துள்ளன. அதே நேரத்தில் அவற்றை அகற்றுவது கரைச் சமூகத்தை மாற்றலாம்; அதனால்தான் சில நட்சத்திர மீன்கள் முக்கிய வேட்டையாடி என்ற கருத்தில் பயன்படுகின்றன."),
                    bdef("Autotomy", "தன்னுறுப்பு துண்டிப்பு", "The voluntary shedding of a body part, such as an arm, at a breakage plane." to
                        "உடைவுத் தளத்தில் கரம் போன்ற உடல் பகுதியை தன்னுறுப்பு துண்டிப்பு."),
                    bdef("Fission", "பிளவு", "Asexual reproduction by division of the body into parts that each become a whole animal." to
                        "உடலை ஒவ்வொன்றும் முழு விலங்காகும் பகுதிகளாகப் பிரித்து நடைபெறும் பாலிலா இனப்பெருக்கம்."),
                )),
        ),
        summary = listOf(
            "Asterias has a central disc and five broad-based arms; the madreporite and anus are aboral, the mouth and ambulacral grooves oral." to "அஸ்டீரியாஸுக்கு மைய வட்டும் அடி அகன்ற ஐந்து கரங்களும் உள்ளன; மேட்ரிபோரைட்டும் மலவாயும் மேற்புறம், வாயும் ஆம்புலாக்ரல் பள்ளங்களும் வாய்ப்புறம்.",
            "The body wall has an open network of ossicles, with spines, pedicellariae and papulae." to "உடற்சுவரில் முட்கள், பெடிசல்லேரியாக்கள், பப்புலாக்களுடன் ஆஸிக்கிள்களின் திறந்த வலைப்பின்னல் உள்ளது.",
            "The cardiac stomach is everted over bivalve prey, so digestion starts outside the body." to "கார்டியாக் இரைப்பை இருவோட்டு இரைமேல் புரள்கிறது; எனவே செரிமானம் உடலுக்கு வெளியே தொடங்குகிறது.",
            "The water-vascular system is hydraulic: madreporite, stone canal, ring canal, radial canals, lateral canals, ampullae and tube feet." to "நீர் குருதியோட்ட மண்டலம் நீரழுத்தமானது: மேட்ரிபோரைட், கல் கால்வாய், வளையக் கால்வாய், கதிர்க் கால்வாய்கள், பக்கக் கால்வாய்கள், ஆம்புலாக்கள், குழாய்ப்பாதங்கள்.",
            "The nervous system has a nerve ring and radial nerves, with no brain." to "நரம்பு அமைப்பில் நரம்பு வளையமும் கதிர் நரம்புகளும் உள்ளன; மூளை இல்லை.",
            "Fertilisation is external, the larva is the bipinnaria, and regeneration needs part of the disc in most species." to "கருவுறுதல் வெளிப்புறம்; லார்வா பிபின்னேரியா; பெரும்பாலான இனங்களில் மீளுருவாக்கத்துக்கு வட்டின் ஒரு பகுதி தேவை.",
        ),
        checks = listOf(
            bqc("On which surface is the madreporite?", "மேட்ரிபோரைட் எந்த மேற்பரப்பில் உள்ளது?",
                "The aboral surface.", "மேற்புற மேற்பரப்பு."),
            bqc("What does the cardiac stomach do during feeding?", "உணவெடுக்கும்போது கார்டியாக் இரைப்பை என்ன செய்கிறது?",
                "It is everted through the mouth over the prey, and digestion begins outside the disc.", "அது வாய் வழியே இரைமேல் புரண்டு, வட்டுக்கு வெளியே செரிமானம் தொடங்குகிறது."),
            bqc("How does a tube foot extend?", "குழாய்ப்பாதம் எவ்வாறு நீள்கிறது?",
                "The ampulla contracts and drives fluid into the foot, with the lateral canal valve closed.", "பக்கக் கால்வாய் வால்வு மூடி, ஆம்புலா சுருங்கி திரவத்தைப் பாதத்துக்குள் தள்ளுகிறது."),
            bqc("How many gonads does Asterias have?", "அஸ்டீரியாஸுக்கு எத்தனை இனப்பெருக்கச் சுரப்பிகள் உள்ளன?",
                "Ten, two in each arm.", "பத்து; ஒவ்வொரு கரத்திலும் இரண்டு."),
        ),
    )
}
