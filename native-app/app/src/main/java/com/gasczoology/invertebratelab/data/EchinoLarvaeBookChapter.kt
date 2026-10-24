package com.gasczoology.invertebratelab.data

/** Book-style chapter: echinoderm larvae. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object EchinoLarvaeBookChapter {
    val chapter = bchapter(
        lessonId = "u5-echino-larvae",
        title = "Echinoderm Larvae" to "முட்தோலி லார்வாக்கள்",
        objectives = listOf(
            "Explain why the echinoderm larva is bilateral and what this shows." to "முட்தோலி லார்வா ஏன் இருபக்கச் சமச்சீருடையது, இது எதைக் காட்டுகிறது என விளக்க.",
            "Match each larval form with its class." to "ஒவ்வொரு லார்வா வடிவத்தையும் அதன் வகுப்புடன் பொருத்த.",
            "Describe the bipinnaria and brachiolaria in sequence." to "பிபின்னேரியா, பிராக்கியோலேரியாவை வரிசையாக விவரிக்க.",
            "Describe metamorphosis to the pentaradial juvenile." to "ஐந்தாரை இளவிலங்காக உருமாற்றத்தை விவரிக்க.",
            "Explain why a bipinnaria is not homologous with a nauplius." to "பிபின்னேரியா நாப்ளியஸுக்கு ஒத்தமைவு அல்ல என்பதை விளக்க.",
        ),
        introduction = listOf(
            "The larvae of the echinoderms are among the most beautiful of the planktonic animals, and they are of great importance in understanding the group. The adult starfish or sea urchin has no head and has five arms or five divisions, and it would be difficult to see in it the features of a bilateral animal. The larva makes the position clear, because it swims by cilia, has a left and a right side, and possesses a complete gut with a mouth and an anus. The larva is therefore the best evidence that the echinoderms are bilateral animals that have become secondarily radial." to
                "முட்தோலிகளின் லார்வாக்கள் மிதவை உயிரிகளில் மிக அழகானவற்றுள் ஒன்று; குழுவைப் புரிந்துகொள்வதில் மிக முக்கியமானவை. முதிர்ந்த நட்சத்திர மீன் அல்லது முள்ளெலிக்குத் தலை இல்லை; ஐந்து கரங்கள் அல்லது ஐந்து பிரிவுகள் உண்டு; அதில் இருபக்க விலங்கின் பண்புகளைக் காண்பது கடினம். லார்வா நிலையைத் தெளிவாக்குகிறது: அது கசையிழையால் நீந்துகிறது; இடம்-வலம் பக்கங்கள் உண்டு; வாய், மலவாயுடன் முழுமையான குடல் உள்ளது. எனவே முட்தோலிகள் இரண்டாம் நிலையாக ஆரைச் சமச்சீராக மாறிய இருபக்க விலங்குகள் என்பதற்கான சிறந்த சான்று லார்வாவே.",
            "The names of the larvae are a major part of the topic and are a common source of lost marks. This chapter gives the larva for each class, describes each in a sentence, and notes that the sequences differ between species, so the class association should be stated without implying that every member follows an identical series." to
                "லார்வாக்களின் பெயர்கள் தலைப்பின் முக்கிய பகுதி; மதிப்பெண் இழப்புக்கான பொதுவான காரணம். இவ்வத்தியாயம் ஒவ்வொரு வகுப்புக்கும் லார்வாவைத் தந்து, ஒவ்வொன்றையும் ஒரு வாக்கியத்தில் விவரித்து, வரிசைகள் இனங்களுக்கிடையே மாறுபடுவதைக் குறிப்பிடுகிறது; எனவே ஒவ்வொரு உறுப்பினரும் ஒரே வரிசையைப் பின்பற்றுகிறது என்ற பொருள் வராமல் வகுப்புத் தொடர்பு கூறப்பட வேண்டும்.",
        ),
        sections = listOf(
            bsec("dipleurula", "The dipleurula idea and bilateral symmetry", "டிப்ளூரூலா கருத்தும் இருபக்கச் சமச்சீரும்",
                blocks = listOf(
                    bp("The early larvae of echinoderms differ in detail, but they can be compared to a hypothetical simple larva called the dipleurula, which is bilaterally symmetrical, ciliated, and has a band of cilia that runs in loops over the surface. The dipleurula is a model made by comparing living larvae, and it should not be described as an animal that has been seen. It is useful because it brings together the characters of the larvae of the echinoderms and of the hemichordates, in which the tornaria larva resembles the bipinnaria of the starfishes." to
                        "முட்தோலிகளின் ஆரம்ப லார்வாக்கள் விவரங்களில் வேறுபடுகின்றன; ஆனால் இருபக்கச் சமச்சீருடைய, கசையிழையுடைய, மேற்பரப்பில் வளையங்களாகச் செல்லும் கசையிழைப் பட்டையுடைய டிப்ளூரூலா எனும் கற்பனை எளிய லார்வாவுடன் ஒப்பிடலாம். டிப்ளூரூலா வாழும் லார்வாக்களை ஒப்பிட்டு உருவாக்கிய மாதிரி; பார்க்கப்பட்ட விலங்காக விவரிக்கக் கூடாது. முட்தோலிகள், ஹெமிகார்டேட்டுகளின் லார்வாக்களின் பண்புகளை இணைப்பதால் அது பயனுள்ளது; ஹெமிகார்டேட்டுகளில் டார்னேரியா லார்வா நட்சத்திர மீன்களின் பிபின்னேரியாவை ஒத்தது."),
                    bpoints("Characters of the early echinoderm larva", "ஆரம்ப முட்தோலி லார்வாவின் பண்புகள்",
                        "Bilateral symmetry, with a left and a right side." to "இடம்-வலம் பக்கங்களுடன் இருபக்கச் சமச்சீர்.",
                        "A complete gut, with a mouth, a stomach and an anus." to "வாய், இரைப்பை, மலவாயுடன் முழுமையான குடல்.",
                        "Locomotion and feeding by bands of cilia, with no appendages." to "உறுப்புகள் இன்றி கசையிழைப் பட்டைகளால் இயக்கமும் உணவெடுப்பும்.",
                        "A coelom formed by outgrowths of the gut, which later gives rise to the water-vascular system." to "குடலின் வளர்ச்சிகளால் உருவாகி, பின்னர் நீர் குருதியோட்ட மண்டலத்தைத் தரும் உடற்குழி.",
                    ),
                    bp("Most echinoderms have a planktonic larva that feeds and grows before metamorphosis, and such a larva is called planktotrophic. Some species instead have a larva that does not feed, living on the yolk of the egg, which is lecithotrophic, and a few have direct development with no free larva. The statements of the textbooks about larvae are for the feeding larvae of species that shed their eggs into the sea, and the other modes are exceptions." to
                        "பெரும்பாலான முட்தோலிகளுக்கு உருமாற்றத்துக்கு முன் உண்டு வளரும் மிதவை லார்வா உண்டு; அத்தகைய லார்வா உணவூட்ட லார்வா எனப்படுகிறது. சில இனங்களுக்கு முட்டையின் கருவுணவில் வாழும், உண்ணாத லார்வா உண்டு; அது கருவுணவு லார்வா; சிலவற்றில் தனி லார்வா இன்றி நேரடி வளர்ச்சி. லார்வாக்கள் பற்றிய பாடநூல் கூற்றுகள் முட்டைகளைக் கடலில் விடும் இனங்களின் உண்ணும் லார்வாக்களுக்கானவை; மற்ற முறைகள் விதிவிலக்குகள்."),
                    bdef("Dipleurula", "டிப்ளூரூலா", "A hypothetical bilateral ciliated larva used as a model for the larvae of echinoderms and hemichordates." to
                        "முட்தோலிகள், ஹெமிகார்டேட்டுகளின் லார்வாக்களுக்கு மாதிரியாகப் பயன்படும் கற்பனை இருபக்க கசையிழை லார்வா."),
                    bdef("Planktotrophic larva", "உணவூட்ட லார்வா", "A larva that feeds on plankton while it develops." to
                        "வளரும்போது மிதவை உயிரிகளை உண்ணும் லார்வா."),
                    bdef("Lecithotrophic larva", "கருவுணவு லார்வா", "A larva that does not feed and lives on the yolk of the egg." to
                        "உண்ணாமல் முட்டையின் கருவுணவில் வாழும் லார்வா."),
                    bdef("Ciliary band", "கசையிழைப் பட்டை", "A band of cilia used by a larva for swimming and for collecting food." to
                        "லார்வா நீந்தவும் உணவு சேகரிக்கவும் பயன்படுத்தும் கசையிழைப் பட்டை."),
                    bdef("Tornaria", "டார்னேரியா", "The larva of enteropneust hemichordates, which resembles the bipinnaria." to
                        "பிபின்னேரியாவை ஒத்த, என்டரோப்நியூஸ்ட் ஹெமிகார்டேட்டுகளின் லார்வா."),
                )),
            bsec("asteroid", "Larvae of the starfishes: bipinnaria and brachiolaria", "நட்சத்திர மீன் லார்வாக்கள்: பிபின்னேரியாவும் பிராக்கியோலேரியாவும்",
                blocks = listOf(
                    bp("The characteristic larva of many starfishes is the bipinnaria. It is a bilaterally symmetrical, transparent larva, a fraction of a millimetre to a few millimetres long, with looping ciliary bands that run on the surface, and a pair of lobed arm-like extensions of the body that are set in two groups. The band on the arms and body is used both in swimming and in the collection of unicellular algae, which are driven to the mouth. The gut is complete, with an oesophagus, a stomach and an intestine that opens at the anus." to
                        "பல நட்சத்திர மீன்களின் தனிப்பண்பான லார்வா பிபின்னேரியா. இது இருபக்கச் சமச்சீருடைய, ஒளி ஊடுருவும், ஒரு மில்லிமீட்டரின் பின்னம் முதல் சில மில்லிமீட்டர் நீளமுடைய லார்வா; மேற்பரப்பில் செல்லும் வளையக் கசையிழைப் பட்டைகள், இரு குழுக்களாக அமைந்த உடலின் மடலுடைய கரம் போன்ற நீட்சிகள் கொண்டது. கரங்கள், உடலிலுள்ள பட்டை நீந்தவும், வாய்க்குத் தள்ளப்படும் ஒருசெல் பாசிகளைச் சேகரிக்கவும் பயன்படுகிறது. குடல் உணவுக்குழல், இரைப்பை, மலவாயில் திறக்கும் குடலுடன் முழுமையானது."),
                    bfig("asterias/larval-development", "Larval development of a starfish, from the bipinnaria and brachiolaria to the settled juvenile." to "நட்சத்திரமீனின் லார்வா வளர்ச்சி: பிபின்னேரியா, பிராக்கியோலேரியாவிலிருந்து படிந்த இளவிலங்கு வரை."),
                    bp("In many starfishes the bipinnaria is followed by the brachiolaria, which has the same features and, in addition, three short arms at the front, called brachiolar arms, with an adhesive disc between them. These are used for attachment to the bottom at the time of settlement. The brachiolaria develops from a bipinnaria in the life histories of those species that have it, and not every starfish passes through both. The student should describe the order, bipinnaria followed by brachiolaria, but should add that this is the common sequence and not a rule for all." to
                        "பல நட்சத்திர மீன்களில் பிபின்னேரியாவைத் தொடர்ந்து பிராக்கியோலேரியா வருகிறது; அது அதே பண்புகளுடன் கூடுதலாக முன்புறம் பிராக்கியோலார் கரங்கள் எனும் மூன்று குறுகிய கரங்களையும் அவற்றிடையே ஒட்டும் வட்டையும் கொண்டது. இவை படியும்போது அடியில் ஒட்டிக்கொள்ளப் பயன்படுகின்றன. பிராக்கியோலேரியா அதைக் கொண்ட இனங்களின் வாழ்க்கை வரலாற்றில் பிபின்னேரியாவிலிருந்து வளர்கிறது; ஒவ்வொரு நட்சத்திரமீனும் இரண்டையும் கடப்பதில்லை. மாணவர் பிபின்னேரியாவைத் தொடர்ந்து பிராக்கியோலேரியா என்ற வரிசையை விவரிக்க வேண்டும்; ஆனால் இது பொதுவான வரிசை, அனைவருக்குமான விதி அல்ல என்றும் சேர்க்க வேண்டும்."),
                    bdef("Bipinnaria", "பிபின்னேரியா", "The ciliated feeding larva of many starfishes, with looping ciliary bands and lobed arms." to
                        "வளையக் கசையிழைப் பட்டைகளும் மடல் கரங்களும் உடைய பல நட்சத்திர மீன்களின் உணவூட்ட லார்வா."),
                    bdef("Brachiolaria", "பிராக்கியோலேரியா", "The later larva of many starfishes, with three brachiolar arms and an adhesive disc used for settlement." to
                        "படிவதற்குப் பயன்படும் மூன்று பிராக்கியோலார் கரங்களும் ஒட்டும் வட்டும் கொண்ட பல நட்சத்திர மீன்களின் பிந்திய லார்வா."),
                    bdef("Settlement", "படிதல்", "The stage at which a larva leaves the plankton and attaches to the bottom before metamorphosis." to
                        "உருமாற்றத்துக்கு முன் லார்வா மிதவையை விட்டு அடியில் ஒட்டிக்கொள்ளும் நிலை."),
                )),
            bsec("pluteus", "Larvae of the sea urchins and brittle stars: the pluteus", "கடல் முள்ளெலி, பிரிட்டில் நட்சத்திர லார்வாக்கள்: ப்ளூட்டியஸ்",
                blocks = listOf(
                    bp("The sea urchins and brittle stars have a pluteus, which is a bilaterally symmetrical larva supported by long, slender, calcareous rods that hold out the arms. The ciliary band runs along the margins of the arms and the body, and the number and arrangement of the arms differ with the species and with the age of the larva. The pluteus of a sea urchin is called an echinopluteus, and that of a brittle star an ophiopluteus, and the two differ in the number of arms and in the pattern of the skeletal rods. The pluteus is very widely used in teaching of embryology and in laboratory work, because the eggs of sea urchins are easily fertilised and develop rapidly." to
                        "கடல் முள்ளெலிகளுக்கும் பிரிட்டில் நட்சத்திரங்களுக்கும் ப்ளூட்டியஸ் உண்டு; கரங்களைத் தாங்கும் நீண்ட, மெல்லிய சுண்ணாம்புக் கம்பிகளால் தாங்கப்படும் இருபக்கச் சமச்சீர் லார்வா. கசையிழைப் பட்டை கரங்கள், உடலின் ஓரங்களில் செல்கிறது; கரங்களின் எண்ணிக்கையும் அமைப்பும் இனத்தையும் லார்வாவின் வயதையும் பொறுத்து மாறும். கடல் முள்ளெலியின் ப்ளூட்டியஸ் எக்கினோப்ளூட்டியஸ்; பிரிட்டில் நட்சத்திரத்தின் ஓஃபியோப்ளூட்டியஸ்; இரண்டும் கரங்களின் எண்ணிக்கையிலும் கூட்டுக் கம்பிகளின் அமைப்பிலும் வேறுபடுகின்றன. கடல் முள்ளெலி முட்டைகள் எளிதில் கருவுற்று விரைவாக வளர்வதால் ப்ளூட்டியஸ் கருவியல் கற்பித்தலிலும் ஆய்வகப் பணியிலும் மிக விரிவாகப் பயன்படுகிறது."),
                    bp("A student must not confuse the pluteus with the bipinnaria. The pluteus is distinguished by its skeletal rods and its pointed arms, and the bipinnaria by its looping bands, its soft lobed arms and the absence of any skeleton. The metamorphosis of a pluteus is rapid and dramatic, since the juvenile develops from a rudiment on one side of the larva, and much of the larval body is lost or absorbed." to
                        "மாணவர் ப்ளூட்டியஸை பிபின்னேரியாவுடன் குழப்பக்கூடாது. ப்ளூட்டியஸ் கூட்டுக் கம்பிகளாலும் கூர்மையான கரங்களாலும் வேறுபடுகிறது; பிபின்னேரியா வளையப் பட்டைகள், மென்மையான மடல் கரங்கள், எந்தக் கூடும் இல்லாமையால் வேறுபடுகிறது. ப்ளூட்டியஸின் உருமாற்றம் விரைவானது, வியத்தகுது; ஏனெனில் இளவிலங்கு லார்வாவின் ஒரு பக்கத்திலுள்ள அரும்பிலிருந்து வளர்கிறது; லார்வா உடலின் பெரும்பகுதி இழக்கப்படுகிறது அல்லது உறிஞ்சப்படுகிறது."),
                    bdef("Pluteus", "ப்ளூட்டியஸ்", "The larva of sea urchins and brittle stars, with calcareous skeletal rods supporting long arms." to
                        "நீண்ட கரங்களைத் தாங்கும் சுண்ணாம்புக் கூட்டுக் கம்பிகள் கொண்ட கடல் முள்ளெலி, பிரிட்டில் நட்சத்திர லார்வா."),
                    bdef("Echinopluteus", "எக்கினோப்ளூட்டியஸ்", "The pluteus larva of a sea urchin." to
                        "கடல் முள்ளெலியின் ப்ளூட்டியஸ் லார்வா."),
                    bdef("Ophiopluteus", "ஓஃபியோப்ளூட்டியஸ்", "The pluteus larva of a brittle star." to
                        "பிரிட்டில் நட்சத்திரத்தின் ப்ளூட்டியஸ் லார்வா."),
                )),
            bsec("holothuroid-crinoid", "Larvae of the sea cucumbers and the sea lilies", "கடல் வெள்ளரி, கடல் அல்லி லார்வாக்கள்",
                blocks = listOf(
                    bp("The characteristic feeding larva of many sea cucumbers is the auricularia. It is a transparent, bilaterally symmetrical larva with a single continuous ciliated band that runs in loops and lobes around the body, and it resembles the bipinnaria though it is not identical. The auricularia is followed in many species by a barrel-shaped larva with several separate rings of cilia, called the doliolaria, which does not feed, and which settles to the bottom and changes to a young sea cucumber." to
                        "பல கடல் வெள்ளரிகளின் தனிப்பண்பான உணவூட்ட லார்வா ஆரிக்குலேரியா. இது உடலைச் சுற்றி வளையங்களாகவும் மடல்களாகவும் செல்லும் ஒற்றைத் தொடர்ச்சியான கசையிழைப் பட்டை கொண்ட, ஒளி ஊடுருவும், இருபக்கச் சமச்சீர் லார்வா; பிபின்னேரியாவை ஒத்தது, ஆனால் ஒரே மாதிரி அல்ல. பல இனங்களில் ஆரிக்குலேரியாவைத் தொடர்ந்து பல தனிக் கசையிழை வளையங்களுடன் உருளை வடிவ, உண்ணாத, அடியில் படிந்து இளம் கடல் வெள்ளரியாக மாறும் டோலியோலேரியா லார்வா வருகிறது."),
                    bp("The crinoids commonly have a doliolaria-type larva, which swims for a short time and then attaches by its front end, and in the stalked forms the stalk grows from the attached part. In the feather stars the young animal is at first stalked and attached, and later breaks free to live as a free animal. The larval series varies among species, so that a class association should be given without saying that every member follows exactly the same sequence." to
                        "கிரினாய்டுகளுக்கு பொதுவாக டோலியோலேரியா வகை லார்வா உண்டு; அது சிறிது நேரம் நீந்தி, பின்னர் முன்முனையால் ஒட்டிக்கொள்கிறது; தண்டுடைய வடிவங்களில் ஒட்டிய பகுதியிலிருந்து தண்டு வளர்கிறது. இறகு நட்சத்திரங்களில் இளவிலங்கு முதலில் தண்டுடன் ஒட்டியிருந்து, பின்னர் விடுபட்டு தனி விலங்காக வாழ்கிறது. லார்வா வரிசை இனங்களுக்கிடையே மாறுபடும்; எனவே ஒவ்வொரு உறுப்பினரும் அதே வரிசையைப் பின்பற்றுகிறது என்று கூறாமல் வகுப்புத் தொடர்பு தரப்பட வேண்டும்."),
                    btable("Larval forms and the classes with which they are associated", "லார்வா வடிவங்களும் தொடர்புடைய வகுப்புகளும்",
                        listOf("Larva" to "லார்வா", "Class" to "வகுப்பு", "Note" to "குறிப்பு"),
                        listOf(
                            listOf("Bipinnaria" to "பிபின்னேரியா", "Asteroidea" to "அஸ்டீராய்டியா", "Soft lobed arms, looping ciliary bands, no skeleton" to "மென்மையான மடல் கரங்கள், வளையக் கசையிழைப் பட்டைகள், கூடு இல்லை"),
                            listOf("Brachiolaria" to "பிராக்கியோலேரியா", "Asteroidea" to "அஸ்டீராய்டியா", "Later stage with three brachiolar arms and an adhesive disc" to "மூன்று பிராக்கியோலார் கரங்களும் ஒட்டும் வட்டும் உடைய பிந்திய நிலை"),
                            listOf("Echinopluteus" to "எக்கினோப்ளூட்டியஸ்", "Echinoidea" to "எக்கினாய்டியா", "Skeletal rods supporting long arms" to "நீண்ட கரங்களைத் தாங்கும் கூட்டுக் கம்பிகள்"),
                            listOf("Ophiopluteus" to "ஓஃபியோப்ளூட்டியஸ்", "Ophiuroidea" to "ஓஃபியூராய்டியா", "Pluteus with an arm pattern different from the echinopluteus" to "எக்கினோப்ளூட்டியஸிலிருந்து வேறுபட்ட கர அமைப்புடைய ப்ளூட்டியஸ்"),
                            listOf("Auricularia" to "ஆரிக்குலேரியா", "Holothuroidea" to "ஹோலோதூரோய்டியா", "One continuous ciliary band; may be followed by a doliolaria" to "ஒரே தொடர்ச்சியான கசையிழைப் பட்டை; டோலியோலேரியா தொடரலாம்"),
                            listOf("Doliolaria" to "டோலியோலேரியா", "Holothuroidea and Crinoidea" to "ஹோலோதூரோய்டியா, கிரினாய்டியா", "Barrel-shaped, with several ciliary rings; does not feed" to "பல கசையிழை வளையங்களுடன் உருளை வடிவம்; உண்ணாது"),
                        )),
                    bdef("Auricularia", "ஆரிக்குலேரியா", "The feeding larva of many sea cucumbers, with a single looped ciliary band." to
                        "ஒற்றை வளையக் கசையிழைப் பட்டை உடைய பல கடல் வெள்ளரிகளின் உணவூட்ட லார்வா."),
                    bdef("Doliolaria", "டோலியோலேரியா", "A barrel-shaped non-feeding larva with several rings of cilia, found in sea cucumbers and crinoids." to
                        "கடல் வெள்ளரிகளிலும் கிரினாய்டுகளிலும் காணப்படும் பல கசையிழை வளையங்களுடன் உருளை வடிவ உண்ணாத லார்வா."),
                )),
            bsec("metamorphosis", "Metamorphosis to the pentaradial juvenile", "ஐந்தாரை இளவிலங்காக உருமாற்றம்",
                blocks = listOf(
                    bp("Metamorphosis is the change from the bilateral larva to the pentaradial juvenile, and in the starfishes it takes place after the larva attaches. In the larva the left side of the coelom forms the rudiment of the water-vascular system as a five-lobed structure, and the rudiment grows on the left side of the body, while the right side forms less. The left side of the larva thus becomes the oral surface of the adult, and the right side the aboral surface, and the body is reorganised around the new axis. The larval arms and ciliary bands are absorbed, and the juvenile sets out its first tube feet." to
                        "உருமாற்றம் என்பது இருபக்க லார்வாவிலிருந்து ஐந்தாரை இளவிலங்காக மாறுதல்; நட்சத்திர மீன்களில் லார்வா ஒட்டிய பின் நிகழ்கிறது. லார்வாவில் உடற்குழியின் இடப்பக்கம் நீர் குருதியோட்ட மண்டலத்தின் அரும்பை ஐந்து மடல் அமைப்பாக உருவாக்குகிறது; அரும்பு உடலின் இடப்பக்கத்தில் வளர, வலப்பக்கம் குறைவாக உருவாகிறது. இவ்வாறு லார்வாவின் இடப்பக்கம் முதிர்ந்ததின் வாய்ப்புற மேற்பரப்பாகவும் வலப்பக்கம் மேற்புற மேற்பரப்பாகவும் மாறுகிறது; உடல் புதிய அச்சைச் சுற்றி மறுசீரமைக்கப்படுகிறது. லார்வாவின் கரங்களும் கசையிழைப் பட்டைகளும் உறிஞ்சப்படுகின்றன; இளவிலங்கு தன் முதல் குழாய்ப்பாதங்களை வெளிப்படுத்துகிறது."),
                    bp("The change is therefore not a simple growth of the larva into the adult. The adult axis has no relation to the larval head and tail, and the juvenile is often smaller than the larva that formed it. This is a reason for the statement that the radial symmetry of the adult is secondary, derived during metamorphosis from a bilateral ancestor. The details of the process differ between classes, and the account here is that of the starfishes." to
                        "எனவே மாற்றம் லார்வா முதிர்ந்ததாக வளரும் எளிய வளர்ச்சி அல்ல. முதிர்ந்த அச்சுக்கு லார்வாவின் தலை, வாலுடன் தொடர்பில்லை; இளவிலங்கு பெரும்பாலும் தான் உருவான லார்வாவை விடச் சிறியது. முதிர்ந்ததின் ஆரச்சமச்சீர், இருபக்க மூதாதையிலிருந்து உருமாற்றத்தின்போது பெறப்பட்ட இரண்டாம் நிலை என்ற கூற்றுக்கு இது ஒரு காரணம். செயல்முறையின் விவரங்கள் வகுப்புகளிடையே மாறுபடும்; இங்குள்ள விளக்கம் நட்சத்திர மீன்களுக்கானது."),
                    bnote("Left becomes oral", "இடப்பக்கம் வாய்ப்புறம் ஆகிறது",
                        "In the starfish the left side of the larva becomes the oral surface of the adult. The adult axis is not the larval head-to-tail axis." to
                            "நட்சத்திரமீனில் லார்வாவின் இடப்பக்கம் முதிர்ந்ததின் வாய்ப்புற மேற்பரப்பாகிறது. முதிர்ந்த அச்சு லார்வாவின் தலை-வால் அச்சு அல்ல."),
                    bdef("Metamorphosis", "உருமாற்றம்", "The reorganisation of a larva into a juvenile of different form." to
                        "லார்வா வேறு வடிவ இளவிலங்காக மறுசீரமைக்கப்படுதல்."),
                    bdef("Rudiment", "அரும்பு", "A group of cells in a larva that will form an adult structure." to
                        "முதிர்ந்த அமைப்பாக மாறும் லார்வாவிலுள்ள செல் குழு."),
                    bdef("Juvenile", "இளவிலங்கு", "A young animal that has the form of the adult but is not sexually mature." to
                        "முதிர்ந்ததின் வடிவம் உடைய ஆனால் பாலினமுதிர்ச்சி அடையாத இளம் விலங்கு."),
                )),
            bsec("embryo", "Early development before the larva", "லார்வாவுக்கு முன்னான தொடக்க வளர்ச்சி",
                blocks = listOf(
                    bp("The larvae of echinoderms are the product of a development that follows the deuterostome pattern. The fertilised egg cleaves by radial cleavage, in which the cells lie directly above one another, and the divisions are complete and nearly equal. A hollow ball of cells, the blastula, forms, and one side of it folds inwards to produce the gastrula, with a new cavity, the archenteron, which opens to the outside by the blastopore. In a deuterostome, the blastopore becomes the anus, and the mouth is formed later at another place, which is the reason for the name, meaning second mouth.",
                        "எக்கைனோடெர்ம் லார்வாக்கள் டியூட்டிரோஸ்டோம் அமைப்பைப் பின்பற்றும் வளர்ச்சியின் விளைவு. கருவுற்ற முட்டை, செல்கள் நேரடியாக ஒன்றன் மேல் ஒன்றாக அமையும் ஆரப் பிளவால் பிளக்கிறது; பிளவுகள் முழுமையானவை, ஏறத்தாழ சமமானவை. செல்களின் உள்ளீடற்ற பந்தான பிளாஸ்டுலா உருவாகிறது; அதன் ஒரு பக்கம் உள்நோக்கி மடிந்து புதிய குழியான ஆர்க்கென்டரானுடன் கேஸ்ட்ருலாவை உருவாக்குகிறது; அது பிளாஸ்டோபோர் வழியே வெளியே திறக்கிறது. டியூட்டிரோஸ்டோமில் பிளாஸ்டோபோர் குதமாகிறது; வாய் பின்னர் வேறிடத்தில் உருவாகிறது; இரண்டாம் வாய் எனப் பொருள்படும் பெயருக்கு இதுவே காரணம்."),
                    bp("The coelom also arises in a characteristic way. Pouches grow out from the archenteron and become pinched off to form the coelomic sacs, a process called enterocoely. In the echinoderm larva the left and right coelomic sacs divide into the parts that later form the water vascular system and the body cavity, and it is the left side that is largely responsible for the water vascular system. This detail explains why the adult pentaradial body is so asymmetrical in its early organisation, since the larva is bilateral and the adult is only secondarily radial.",
                        "சீலோமும் தனித்தன்மையுடன் தோன்றுகிறது. ஆர்க்கென்டரானிலிருந்து பைகள் வளர்ந்து கிள்ளி எடுக்கப்பட்டு சீலோம் பைகளை உருவாக்குகின்றன; இது என்டெரோசீலி எனப்படுகிறது. எக்கைனோடெர்ம் லார்வாவில் இடது, வலது சீலோம் பைகள் பின்னர் நீர்க்குழல் மண்டலத்தையும் உடற்குழியையும் உருவாக்கும் பகுதிகளாகப் பிரிகின்றன; நீர்க்குழல் மண்டலத்துக்கு பெரும்பாலும் இடப்பக்கமே காரணம். லார்வா இருபக்கச் சமச்சீர், முதிர்ந்தது இரண்டாம் நிலையாகவே ஆரச்சமச்சீர் என்பதால், முதிர்ந்த ஐந்தாரை உடல் தொடக்க அமைப்பில் இவ்வளவு சமச்சீரற்றது என்பதை இவ்விவரம் விளக்குகிறது."),
                    bdef("Deuterostome", "டியூட்டிரோஸ்டோம்", "An animal in which the blastopore becomes the anus and the mouth forms secondarily; the echinoderms, hemichordates and chordates." to
                        "பிளாஸ்டோபோர் குதமாகி வாய் இரண்டாம் நிலையாக உருவாகும் விலங்கு; எக்கைனோடெர்ம்கள், ஹெமிகார்டேட்டுகள், கார்டேட்டுகள்."),
                    bdef("Enterocoely", "என்டெரோசீலி", "The formation of the coelom from pouches that grow out from the wall of the primitive gut." to
                        "ஆதிக் குடல் சுவரிலிருந்து வளரும் பைகளிலிருந்து சீலோம் உருவாதல்."),
                    bdef("Blastopore", "பிளாஸ்டோபோர்", "The opening of the archenteron to the outside in the gastrula; it becomes the anus in a deuterostome." to
                        "கேஸ்ட்ருலாவில் ஆர்க்கென்டரான் வெளியே திறக்கும் துளை; டியூட்டிரோஸ்டோமில் குதமாகிறது."),
                )),
            bsec("ecology", "Larvae in ecology and in the hatchery", "சூழலியலிலும் குஞ்சு பொரிப்பகத்திலும் லார்வாக்கள்",
                blocks = listOf(
                    bp("A free-swimming larva allows an animal that moves slowly as an adult to spread over long distances, and the dispersal of the larva is the main way in which the populations of sea urchins, sea stars and sea cucumbers are linked to one another. The numbers that survive each year depend on the food supply and on predators in the plankton, and so the abundance of adults may vary a great deal from one year to another. Occasional outbreaks of some species, such as the crown-of-thorns starfish that eats corals, are partly due to the survival of the larvae in years of plentiful food, though the causes are debated.",
                        "ஊர்ந்து மெதுவாக நகரும் முதிர்ந்த விலங்கு நீண்ட தொலைவுக்குப் பரவ, சுதந்திரமாக நீந்தும் லார்வா அனுமதிக்கிறது; கடல் முள்ளெலிகள், நட்சத்திர மீன்கள், கடல் வெள்ளரிகளின் எண்ணிக்கைகள் ஒன்றோடொன்று இணைவதன் முக்கிய வழி லார்வா பரவலே. ஒவ்வொரு ஆண்டும் உயிர் பிழைப்பவை உணவு வழங்கல், மிதவையிலுள்ள வேட்டையாடிகளைப் பொறுத்தவை; எனவே முதிர்ந்தவற்றின் மிகுதி ஆண்டுக்கு ஆண்டு பெரிதும் மாறலாம். பவளங்களை உண்ணும் முள் முடி நட்சத்திர மீன் போன்ற சில இனங்களின் அவ்வப்போதைய பெருக்கங்கள் உணவு நிறைந்த ஆண்டுகளில் லார்வாக்கள் உயிர் பிழைப்பதால் ஓரளவு நிகழ்கின்றன; எனினும் காரணங்கள் விவாதிக்கப்படுகின்றன."),
                    bp("The larvae also have uses in science and aquaculture. The eggs and larvae of sea urchins are easily obtained and transparent, and they have been used for over a century in the study of fertilisation and early development. In India, hatchery methods have been developed to produce the young of the sandfish sea cucumber, Holothuria scabra, which is of commercial value, because the wild stocks are overfished. The larvae are fed on microscopic algae, and the main practical difficulties are the loss of larvae at the time of settlement and the supply of food.",
                        "லார்வாக்களுக்கு அறிவியலிலும் நீர்வள வளர்ப்பிலும் பயன்பாடுகள் உள்ளன. கடல் முள்ளெலிகளின் முட்டைகளும் லார்வாக்களும் எளிதில் கிடைப்பவை, ஒளி ஊடுருவுபவை; கருவுறுதல், தொடக்க வளர்ச்சி ஆய்வில் ஒரு நூற்றாண்டுக்கு மேல் பயன்படுத்தப்பட்டுள்ளன. இந்தியாவில் வணிக மதிப்புள்ள மணல் கடல் வெள்ளரி ஹோலோத்தூரியா ஸ்காப்ராவின் குஞ்சுகளை உருவாக்க, காட்டு இருப்புகள் அதிகமாகப் பிடிக்கப்படுவதால், குஞ்சு பொரிப்பக முறைகள் உருவாக்கப்பட்டுள்ளன. லார்வாக்களுக்கு நுண்பாசிகள் உணவாக அளிக்கப்படுகின்றன; படியும் நேரத்தில் லார்வாக்கள் இழப்பும் உணவு வழங்கலும் முக்கிய நடைமுறைச் சிக்கல்கள்."),
                )),
            bsec("answering", "Answering questions on echinoderm larvae", "எக்கைனோடெர்ம் லார்வாக்கள் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("The most frequent questions are the names and the classes in which the larvae occur, the bilateral symmetry of the larva and the radial symmetry of the adult, and the evidence that echinoderms are related to chordates. A table that lists the class, the larva and one or two distinguishing features is the best form of answer for the first question. For the second, the statement that the larva is bilateral and ciliated and that the adult develops from a rudiment in the larva, with the loss or absorption of most of the larval body, is the central point. The third question draws on the resemblance of the auricularia larva of the sea cucumber to the tornaria larva of the hemichordates.",
                        "மிக அடிக்கடி கேட்கப்படுபவை லார்வாக்களின் பெயர்களும் அவை காணப்படும் வகுப்புகளும், லார்வாவின் இருபக்கச் சமச்சீரும் முதிர்ந்ததின் ஆரச்சமச்சீரும், எக்கைனோடெர்ம்கள் கார்டேட்டுகளுடன் தொடர்புடையவை என்பதற்கான சான்றும். முதல் வினாவுக்கு வகுப்பு, லார்வா, ஓரிரு வேறுபடுத்தும் பண்புகளைப் பட்டியலிடும் அட்டவணை சிறந்த விடை. இரண்டாவதற்கு, லார்வா இருபக்கச் சமச்சீர், சிலியா கொண்டது; முதிர்ந்தது லார்வாவின் அரும்பிலிருந்து வளர்கிறது, லார்வா உடலின் பெரும்பகுதி இழக்கப்படுகிறது அல்லது உறிஞ்சப்படுகிறது என்ற கூற்று மையப்புள்ளி. மூன்றாவது கடல் வெள்ளரியின் ஆரிக்குலேரியா லார்வா ஹெமிகார்டேட்டுகளின் டார்னேரியா லார்வாவை ஒத்திருப்பதிலிருந்து எடுக்கப்படுகிறது."),
                    bpoints("Frequent errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Giving a pluteus to a starfish, or a bipinnaria to a sea urchin." to "நட்சத்திர மீனுக்கு ப்ளூட்டியஸையோ கடல் முள்ளெலிக்கு பிபின்னேரியாவையோ தருதல்.",
                        "Saying that the adult echinoderm is bilateral, or that the larva is radial." to "முதிர்ந்த எக்கைனோடெர்ம் இருபக்கச் சமச்சீர் என்றோ லார்வா ஆரச்சமச்சீர் என்றோ கூறுதல்.",
                        "Omitting that the larva is ciliated and swims by cilia and not by limbs." to "லார்வா சிலியா கொண்டது, உறுப்புகளால் அல்ல சிலியாவால் நீந்துகிறது என்பதைத் தவிர்த்தல்.",
                        "Treating the tornaria as an echinoderm larva; it belongs to the hemichordates." to "டார்னேரியாவை எக்கைனோடெர்ம் லார்வாவாகக் கருதுதல்; அது ஹெமிகார்டேட்டுகளுக்கு உரியது.",
                    ),
                )),
            bsec("nauplius", "Why a bipinnaria is not a nauplius", "பிபின்னேரியா ஏன் நாப்ளியஸ் அல்ல",
                blocks = listOf(
                    bp("A question set in the syllabus asks the student to compare two planktonic larvae, the bipinnaria of a starfish and the nauplius of a crustacean, and to explain why they are not homologous. Both swim in the plankton and both are small. That similarity is functional, the result of a similar way of life, and it is not evidence of a close relationship. The bipinnaria belongs to a deuterostome lineage, the echinoderms, while the nauplius belongs to the arthropods, which are part of the protostome group Ecdysozoa." to
                        "பாடத்திட்டக் கேள்வி இரு மிதவை லார்வாக்களை, நட்சத்திரமீனின் பிபின்னேரியாவையும் நண்டுவகையின் நாப்ளியஸையும், ஒப்பிட்டு அவை ஏன் ஒத்தமைவு அல்ல என விளக்கக் கேட்கிறது. இரண்டும் மிதவையில் நீந்துகின்றன; இரண்டும் சிறியவை. அந்த ஒற்றுமை செயல்பாட்டு ஒற்றுமை; ஒத்த வாழ்க்கை முறையின் விளைவு; நெருங்கிய உறவுக்கான சான்று அல்ல. பிபின்னேரியா இரண்டாம்வாய் பரம்பரையான முட்தோலிகளுக்கு உரியது; நாப்ளியஸ் முதல்வாயிக் குழுவான எக்டைசோசோவாவின் பகுதியான கணுக்காலிகளுக்கு உரியது."),
                    btable("Bipinnaria compared with nauplius", "பிபின்னேரியாவும் நாப்ளியஸும் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Bipinnaria" to "பிபின்னேரியா", "Nauplius" to "நாப்ளியஸ்"),
                        listOf(
                            listOf("Animal group" to "விலங்குக் குழு", "Echinoderm, a deuterostome" to "முட்தோலி, இரண்டாம்வாயி", "Crustacean, an arthropod within Ecdysozoa" to "நண்டுவகை, எக்டைசோசோவாவிலுள்ள கணுக்காலி"),
                            listOf("Body" to "உடல்", "Soft, unsegmented, with lobed arm-like extensions" to "மென்மையான, துண்டுபடாத, மடல் கரம் போன்ற நீட்சிகளுடன்", "Unsegmented, with a covering cuticle" to "துண்டுபடாத, மூடும் கியூட்டிகிளுடன்"),
                            listOf("Locomotion" to "இயக்கம்", "Beating of ciliary bands" to "கசையிழைப் பட்டைகளின் துடிப்பு", "Beating of three pairs of appendages" to "மூன்று இணை உறுப்புகளின் துடிப்பு"),
                            listOf("Eye" to "கண்", "None of the naupliar type" to "நாப்ளியஸ் வகை இல்லை", "A median naupliar eye is commonly present" to "நடு நாப்ளியஸ் கண் பொதுவாக உண்டு"),
                            listOf("Fate" to "முடிவு", "Metamorphosis to a pentaradial juvenile" to "ஐந்தாரை இளவிலங்காக உருமாற்றம்", "Moults through stages to the adult" to "நிலைகள் வழியே தோல் உரித்து முதிர்ந்ததாதல்"),
                        )),
                    bp("The comparison is an example of convergence, in which unrelated animals evolve similar solutions to the problem of living as a small drifting feeder. When asked about homology, a student should say that homology depends on shared ancestry of a structure, and show that the two larvae differ in the origin of their locomotor structures, in body plan and in the lineages that they represent." to
                        "ஒப்பீடு குவிவுப் பரிணாமத்துக்கு எடுத்துக்காட்டு; தொடர்பற்ற விலங்குகள் சிறு மிதக்கும் உண்ணியாக வாழும் சிக்கலுக்கு ஒத்த தீர்வுகளைப் பரிணமிக்கின்றன. ஒத்தமைவு பற்றிக் கேட்கப்படும்போது, ஒத்தமைவு ஒரு அமைப்பின் பகிரப்பட்ட மூதாதைத்தன்மையைச் சார்ந்தது என்றும், இரு லார்வாக்களும் இயக்க அமைப்புகளின் தோற்றம், உடல் திட்டம், அவை பிரதிநிதித்துவப்படுத்தும் பரம்பரைகளில் வேறுபடுகின்றன என்றும் மாணவர் காட்ட வேண்டும்."),
                    bdef("Homology", "ஒத்தமைவு", "Similarity of structures in different organisms because they are inherited from a common ancestor." to
                        "பொது மூதாதையிடமிருந்து பெறப்பட்டதால் வெவ்வேறு உயிரிகளின் அமைப்புகளில் உள்ள ஒற்றுமை."),
                    bdef("Convergence", "குவிவுப் பரிணாமம்", "The independent evolution of similar features in unrelated lineages because of similar ways of life." to
                        "ஒத்த வாழ்க்கை முறைகளால் தொடர்பற்ற பரம்பரைகளில் ஒத்த பண்புகள் தனித்துப் பரிணமித்தல்."),
                    bdef("Nauplius", "நாப்ளியஸ்", "The first larva of many crustaceans, with three pairs of appendages and often a median eye." to
                        "மூன்று இணை உறுப்புகளும் பெரும்பாலும் நடுக் கண்ணும் உடைய பல நண்டுவகைகளின் முதல் லார்வா."),
                )),
        ),
        summary = listOf(
            "Echinoderm larvae are bilateral and ciliated, which shows that the radial adult is secondary." to "முட்தோலி லார்வாக்கள் இருபக்கச் சமச்சீருடையவை, கசையிழை கொண்டவை; முதிர்ந்த ஆரச்சமச்சீர் இரண்டாம் நிலை என்பதை இது காட்டுகிறது.",
            "Starfishes have a bipinnaria, which may be followed by a brachiolaria." to "நட்சத்திர மீன்களுக்கு பிபின்னேரியா உண்டு; அதைத் தொடர்ந்து பிராக்கியோலேரியா வரலாம்.",
            "Sea urchins have an echinopluteus and brittle stars an ophiopluteus." to "கடல் முள்ளெலிகளுக்கு எக்கினோப்ளூட்டியஸும் பிரிட்டில் நட்சத்திரங்களுக்கு ஓஃபியோப்ளூட்டியஸும் உண்டு.",
            "Sea cucumbers have an auricularia, often followed by a doliolaria; crinoids commonly have a doliolaria." to "கடல் வெள்ளரிகளுக்கு ஆரிக்குலேரியா, பெரும்பாலும் டோலியோலேரியா தொடரும்; கிரினாய்டுகளுக்கு பொதுவாக டோலியோலேரியா.",
            "In a starfish the left side of the larva becomes the oral surface of the adult." to "நட்சத்திரமீனில் லார்வாவின் இடப்பக்கம் முதிர்ந்ததின் வாய்ப்புற மேற்பரப்பாகிறது.",
            "A bipinnaria and a nauplius are similar in function only, and are not homologous." to "பிபின்னேரியாவும் நாப்ளியஸும் செயல்பாட்டில் மட்டும் ஒத்தவை; ஒத்தமைவு அல்ல.",
        ),
        checks = listOf(
            bqc("Name the larva of a starfish and the larva of a sea cucumber.", "நட்சத்திர மீன், கடல் வெள்ளரி லார்வாக்களைப் பெயரிடுக.",
                "Bipinnaria for a starfish; auricularia for a sea cucumber.", "நட்சத்திரமீனுக்கு பிபின்னேரியா; கடல் வெள்ளரிக்கு ஆரிக்குலேரியா."),
            bqc("How does a pluteus differ from a bipinnaria?", "ப்ளூட்டியஸ் பிபின்னேரியாவிலிருந்து எவ்வாறு வேறுபடுகிறது?",
                "A pluteus has calcareous skeletal rods and pointed arms; a bipinnaria has soft lobed arms and no skeleton.", "ப்ளூட்டியஸுக்கு சுண்ணாம்புக் கூட்டுக் கம்பிகளும் கூர்மையான கரங்களும் உண்டு; பிபின்னேரியாவுக்கு மென்மையான மடல் கரங்கள், கூடு இல்லை."),
            bqc("Which side of the larva becomes the oral surface in a starfish?", "நட்சத்திரமீனில் லார்வாவின் எந்தப் பக்கம் வாய்ப்புற மேற்பரப்பாகிறது?",
                "The left side.", "இடப்பக்கம்."),
            bqc("Why are the bipinnaria and the nauplius not homologous?", "பிபின்னேரியாவும் நாப்ளியஸும் ஏன் ஒத்தமைவு அல்ல?",
                "They belong to unrelated lineages, a deuterostome and an ecdysozoan, and their similarity is functional only.", "அவை தொடர்பற்ற பரம்பரைகளுக்கு, இரண்டாம்வாயி, எக்டைசோசோவாவுக்கு உரியவை; ஒற்றுமை செயல்பாட்டுக்கு மட்டும்."),
        ),
    )
}
