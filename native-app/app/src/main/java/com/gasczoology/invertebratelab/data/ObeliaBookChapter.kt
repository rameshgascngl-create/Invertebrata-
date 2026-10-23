package com.gasczoology.invertebratelab.data

/** Book-style chapter: Obelia. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object ObeliaBookChapter {
    val chapter = bchapter(
        lessonId = "u2-obelia",
        title = "Obelia: a colonial hydroid" to "ஒபீலியா: ஒரு காலனி ஹைட்ராய்டு",
        objectives = listOf(
            "Describe the habitat and the architecture of the Obelia colony, distinguishing perisarc from coenosarc." to "ஒபீலியாவின் வாழிடத்தையும் காலனியின் கட்டமைப்பையும் விவரித்து, பெரிசார்க்கையும் சீனோசார்க்கையும் வேறுபடுத்த.",
            "Describe the structure and function of the hydranth, the gonangium and the medusa." to "ஹைட்ராந்த், கோனாஞ்சியம், மெடூசா ஆகியவற்றின் அமைப்பையும் பணியையும் விவரிக்க.",
            "Explain polymorphism in Obelia as division of labour among zooids." to "ஒபீலியாவில் பல்வடிவத்தன்மையை சூயிட்டுகளுக்கிடையேயான பணிப் பிரிவினையாக விளக்க.",
            "Write the life history of Obelia in the correct sequence." to "ஒபீலியாவின் வாழ்க்கை வரலாற்றைச் சரியான வரிசையில் எழுத.",
            "Explain metagenesis and distinguish Obelia from Physalia." to "மெட்டாஜெனிசிஸை விளக்கி, ஒபீலியாவை ஃபைசாலியாவிலிருந்து வேறுபடுத்த.",
        ),
        introduction = listOf(
            "Obelia is the standard example of the hydroid colony in the syllabus. Unlike Hydra, which lives alone, Obelia is a branching colony fixed to a solid surface in shallow sea water, in which many small animals, the zooids, are joined by living tissue and share one gut. It also has a life cycle that includes a free-swimming medusa, and so it shows both of the body forms of the phylum in a single species." to
                "ஒபீலியா பாடத்திட்டத்தில் ஹைட்ராய்டு காலனிக்கான நிலையான எடுத்துக்காட்டு. தனியாக வாழும் ஹைட்ராவைப் போலன்றி, ஆழமற்ற கடல் நீரில் உறுதியான பரப்பில் பொருந்திய கிளைத்த காலனி; இதில் பல சிறு விலங்குகளான சூயிட்டுகள் உயிருள்ள திசுவால் இணைந்து ஒரே குடலைப் பகிர்கின்றன. சுதந்திரமாக நீந்தும் மெடூசா வாழ்க்கைச் சுழற்சியில் உள்ளதால், தொகுதியின் இரு உடல் வடிவங்களையும் ஒரே இனத்தில் காட்டுகிறது.",
            "The chapter follows the usual order for a type study: habit and habitat, structure of the colony and of each kind of zooid, the way of life, and the life history. Throughout it should be remembered that the polyp and the medusa are two stages of the same animal, and not two kinds of animal." to
                "அத்தியாயம் வகை ஆய்வின் வழக்கமான வரிசையைப் பின்பற்றுகிறது: வாழ்முறையும் வாழிடமும், காலனியின் அமைப்பும் ஒவ்வொரு சூயிட் வகையின் அமைப்பும், வாழ்க்கைமுறை, வாழ்க்கை வரலாறு. பாலிப்பும் மெடூசாவும் ஒரே விலங்கின் இரு நிலைகள்; இரு வகை விலங்குகள் அல்ல என்பதை முழுவதும் நினைவில் கொள்ள வேண்டும்.",
        ),
        sections = listOf(
            bsec("habitat", "Habit and habitat", "வாழ்முறையும் வாழிடமும்",
                blocks = listOf(
                    bp("Obelia is a marine hydroid, found in shallow coastal water, where it grows on stones, shells, wooden piles, seaweeds and the hulls of boats. The colony is fixed and so it feeds on the small organisms carried to it by the water. A colony may grow to a few centimetres in height and appears as a delicate, feathery or fern-like growth, often mistaken for a seaweed by the beginner." to
                        "ஒபீலியா கடல் ஹைட்ராய்டு; ஆழமற்ற கடலோர நீரில், கற்கள், ஓடுகள், மரக்கம்பங்கள், கடற்பாசிகள், படகுகளின் அடிப்பகுதிகள் மீது வளர்கிறது. காலனி நிலையானது; நீர் கொண்டு வரும் சிறு உயிரிகளை உண்கிறது. காலனி சில சென்டிமீட்டர் உயரம் வளரக்கூடும்; மென்மையான, இறகு அல்லது பன்னம் போன்ற வளர்ச்சியாகத் தோன்றும்; தொடக்கநிலையினர் அடிக்கடி கடற்பாசி என்று தவறாக எண்ணுவர்."),
                    bdef("Hydroid", "ஹைட்ராய்டு",
                        "A hydrozoan in its polyp stage, especially one that forms an attached, branching colony." to
                            "பாலிப் நிலையிலுள்ள ஹைட்ரோசோவா; குறிப்பாக ஒட்டிய, கிளைத்த காலனியை உருவாக்குவது."),
                    bdef("Zooid", "சூயிட்",
                        "An individual member of a colony, such as a feeding hydranth or a reproductive gonangium." to
                            "உணவூட்டும் ஹைட்ராந்த் அல்லது இனப்பெருக்க கோனாஞ்சியம் போன்ற காலனியின் தனி உறுப்பினர்."),
                )),
            bsec("colony", "Structure of the colony", "காலனியின் அமைப்பு",
                blocks = listOf(
                    bp("The colony is attached by a root-like stolon, the hydrorhiza, which creeps over the surface. From it arise upright stems that branch, usually in a zig-zag pattern, and at each bend a zooid is borne. The whole colony is built on a single plan, in which a tube of living tissue is enclosed in a covering of non-living material." to
                        "காலனி ஹைட்ரோரைசா என்ற வேர் போன்ற ஸ்டோலனால் ஒட்டியுள்ளது; அது பரப்பின் மேல் ஊர்கிறது. அதிலிருந்து நிமிர்ந்த தண்டுகள் எழுந்து, பொதுவாக ஜிக்-ஜாக் வடிவில் கிளைக்கின்றன; ஒவ்வொரு வளைவிலும் ஒரு சூயிட் தாங்கப்படுகிறது. முழு காலனியும் ஒரே திட்டத்தில் கட்டப்பட்டது: உயிருள்ள திசுவின் குழாய், உயிரற்ற பொருளின் உறையால் மூடப்பட்டுள்ளது."),
                    bpoints("The two parts of the stem", "தண்டின் இரு பகுதிகள்",
                        "Perisarc: the non-living outer covering of chitin, which supports and protects the colony." to "பெரிசார்க்: கைட்டினால் ஆன உயிரற்ற வெளி உறை; காலனியைத் தாங்கிப் பாதுகாக்கிறது.",
                        "Coenosarc: the living tissue inside the perisarc, with an outer epidermis, a thin mesoglea and an inner gastrodermis." to "சீனோசார்க்: பெரிசார்க்கினுள் உள்ள உயிருள்ள திசு; வெளி எப்பிடெர்மிஸ், மெல்லிய மீசோகிளியா, உள் காஸ்ட்ரோடெர்மிஸ் கொண்டது.",
                        "The coenosarc encloses a continuous gastrovascular cavity, and so the food taken by one zooid is distributed to all." to "சீனோசார்க் தொடர்ச்சியான காஸ்ட்ரோவாஸ்குலர் குழியை அடைக்கிறது; எனவே ஒரு சூயிட் உண்ட உணவு அனைத்துக்கும் பகிரப்படுகிறது.",
                    ),
                    bp("Around each hydranth the perisarc widens into a cup, the hydrotheca, into which the hydranth can withdraw. Around each gonangium it forms a longer, flask-shaped case, the gonotheca. The perisarc is often ringed at the joints, which gives the stem a limited flexibility." to
                        "ஒவ்வொரு ஹைட்ராந்தைச் சுற்றியும் பெரிசார்க் கிண்ணமாக விரிகிறது; அது ஹைட்ரோதீகா; ஹைட்ராந்த் அதனுள் பின்வாங்க முடியும். ஒவ்வொரு கோனாஞ்சியத்தைச் சுற்றியும் நீண்ட, குடுவை வடிவ உறையான கோனோதீகாவை உருவாக்குகிறது. பெரிசார்க் மூட்டுகளில் வளையங்களாக இருப்பதால் தண்டுக்கு வரம்புக்குட்பட்ட நெகிழ்வு கிடைக்கிறது."),
                    bfig("obelia/colony", "Part of a colony of Obelia, showing the hydrorhiza, the branching stem with perisarc and coenosarc, feeding hydranths in their hydrothecae, and gonangia." to
                        "ஒபீலியா காலனியின் ஒரு பகுதி: ஹைட்ரோரைசா, பெரிசார்க்கும் சீனோசார்க்கும் கொண்ட கிளைத்த தண்டு, ஹைட்ரோதீகாவில் உள்ள ஹைட்ராந்துகள், கோனாஞ்சியம்."),
                    bdef("Perisarc", "பெரிசார்க்",
                        "The non-living, chitinous outer covering of the stem and branches of a hydroid colony." to
                            "ஹைட்ராய்டு காலனியின் தண்டு, கிளைகளின் உயிரற்ற, கைட்டின் வெளி உறை."),
                    bdef("Coenosarc", "சீனோசார்க்",
                        "The living tissue inside the perisarc that joins all the zooids of the colony and encloses the common gastrovascular cavity." to
                            "பெரிசார்க்கினுள் உள்ள, காலனியின் அனைத்து சூயிட்டுகளையும் இணைத்து பொதுவான காஸ்ட்ரோவாஸ்குலர் குழியை அடைக்கும் உயிருள்ள திசு."),
                    bdef("Hydrotheca", "ஹைட்ரோதீகா",
                        "The cup of perisarc surrounding a hydranth." to
                            "ஹைட்ராந்தைச் சுற்றியுள்ள பெரிசார்க் கிண்ணம்."),
                    bdef("Hydrorhiza", "ஹைட்ரோரைசா",
                        "The creeping, root-like stolon by which a hydroid colony is attached to the substratum." to
                            "ஹைட்ராய்டு காலனி அடித்தளத்தில் ஒட்டியிருக்கும், ஊர்ந்து செல்லும் வேர் போன்ற ஸ்டோலன்."),
                )),
            bsec("hydranth", "The hydranth, or feeding zooid", "ஹைட்ராந்த், அல்லது உணவூட்டும் சூயிட்",
                blocks = listOf(
                    bp("The hydranth is the feeding polyp of the colony; it is also called the gastrozooid or trophozooid. It is a small cylinder with a raised, cone-shaped hypostome at the free end, bearing the mouth, and a ring of solid tentacles around the hypostome. The tentacles carry batteries of cnidocytes. The body of the hydranth encloses a part of the gastrovascular cavity, which opens through the mouth and is continuous with the cavity of the stem." to
                        "ஹைட்ராந்த் காலனியின் உணவூட்டும் பாலிப்; இது காஸ்ட்ரோசூயிட் அல்லது டிரோஃபோசூயிட் எனவும் அழைக்கப்படுகிறது. தனித்த முனையில் வாயைத் தாங்கும் உயர்ந்த கூம்பு வடிவ ஹைப்போஸ்டோமும், அதைச் சுற்றி திண்ம உணர்கொம்புகளின் வளையமும் உள்ள சிறு உருளை. உணர்கொம்புகள் நிடோசைட் தொகுதிகளைக் கொண்டுள்ளன. ஹைட்ராந்தின் உடல் காஸ்ட்ரோவாஸ்குலர் குழியின் ஒரு பகுதியை அடைக்கிறது; அது வாய் வழியாகத் திறந்து, தண்டின் குழியுடன் தொடர்ச்சியாக உள்ளது."),
                    bp("The hydranth catches small animals with its tentacles, paralyses them with nematocysts and passes them through the mouth into the cavity. Digestion begins in the cavity, where enzymes are secreted by the gland cells, and is completed in the cells of the gastrodermis. The partly digested food is circulated through the whole colony by the beating of flagella in the common cavity, and by the movement of the body. Undigested matter is expelled through the mouth." to
                        "ஹைட்ராந்த் உணர்கொம்புகளால் சிறு விலங்குகளைப் பிடித்து, நீமட்டோசிஸ்ட்களால் செயலிழக்கச் செய்து, வாய் வழியாகக் குழிக்குள் செலுத்துகிறது. சுரப்பி செல்கள் சுரக்கும் நொதிகளால் குழியில் செரிமானம் தொடங்கி, காஸ்ட்ரோடெர்மிஸ் செல்களில் முடிகிறது. பகுதியாகச் செரித்த உணவு, பொதுக் குழியிலுள்ள கசையிழைகளின் அசைவாலும் உடல் இயக்கத்தாலும் முழு காலனிக்கும் சுற்றிச் செலுத்தப்படுகிறது. செரியாதவை வாய் வழியாக வெளியேற்றப்படுகின்றன."),
                    bdef("Hypostome", "ஹைப்போஸ்டோம்",
                        "The raised, cone-shaped region of a hydranth that bears the mouth." to
                            "ஹைட்ராந்தின் வாயைத் தாங்கும் உயர்ந்த கூம்பு வடிவப் பகுதி."),
                    bdef("Gastrozooid", "காஸ்ட்ரோசூயிட்",
                        "The feeding zooid, or hydranth, of a hydroid colony." to
                            "ஹைட்ராய்டு காலனியின் உணவூட்டும் சூயிட், அதாவது ஹைட்ராந்த்."),
                )),
            bsec("gonangium", "The gonangium, or reproductive zooid", "கோனாஞ்சியம், அல்லது இனப்பெருக்க சூயிட்",
                blocks = listOf(
                    bp("The gonangium, or gonozooid, is the reproductive zooid. It arises at the junction of the stem and a branch and is a long, vase-shaped structure enclosed in a gonotheca. It has neither mouth nor tentacles and does not feed; it is nourished by the other zooids through the common cavity. Its centre is a stalk of coenosarc, the blastostyle, on which medusa buds are produced. The buds are made by the asexual process of budding." to
                        "கோனாஞ்சியம் அல்லது கோனோசூயிட் இனப்பெருக்க சூயிட். தண்டும் கிளையும் சந்திக்குமிடத்தில் தோன்றுகிறது; கோனோதீகாவால் மூடப்பட்ட நீண்ட, குவளை வடிவ அமைப்பு. வாயும் உணர்கொம்புகளும் இல்லை; உண்பதில்லை; பொதுக் குழி வழியாக மற்ற சூயிட்டுகளால் ஊட்டமளிக்கப்படுகிறது. அதன் மையம் சீனோசார்க்கின் தண்டான பிளாஸ்டோஸ்டைல்; அதில் மெடூசா மொட்டுகள் உருவாகின்றன. மொட்டுகள் மொட்டுவிடுதல் என்ற பாலிலா செயல்முறையால் உருவாகின்றன."),
                    bp("The buds develop in a series along the blastostyle, the oldest at the top, and as each matures it escapes through the open end of the gonotheca as a small, free-swimming medusa. In this way a single gonangium releases many medusae over a period." to
                        "மொட்டுகள் பிளாஸ்டோஸ்டைல் வழியாகத் தொடராக வளர்கின்றன; முதிர்ந்தது மேலே இருக்கும்; ஒவ்வொன்றும் முதிர்ந்ததும் கோனோதீகாவின் திறந்த முனை வழியாக சிறிய, சுதந்திரமாக நீந்தும் மெடூசாவாக வெளியேறுகிறது. இவ்வாறு ஒரே கோனாஞ்சியம் ஒரு காலப்பகுதியில் பல மெடூசாக்களை வெளிவிடுகிறது."),
                    bfig("obelia/hydranth-gonangium", "A hydranth and a gonangium of Obelia, showing the hypostome, tentacles and hydrotheca of the hydranth and the blastostyle with medusa buds inside the gonotheca of the gonangium." to
                        "ஒபீலியாவின் ஹைட்ராந்தும் கோனாஞ்சியமும்: ஹைட்ராந்தின் ஹைப்போஸ்டோம், உணர்கொம்புகள், ஹைட்ரோதீகா; கோனாஞ்சியத்தின் கோனோதீகாவினுள் பிளாஸ்டோஸ்டைலும் மெடூசா மொட்டுகளும்."),
                    bdef("Gonangium", "கோனாஞ்சியம்",
                        "The reproductive zooid of Obelia, enclosed in a gonotheca, which produces medusa buds on a blastostyle." to
                            "ஒபீலியாவின் இனப்பெருக்க சூயிட்; கோனோதீகாவால் மூடப்பட்டு, பிளாஸ்டோஸ்டைலில் மெடூசா மொட்டுகளை உருவாக்குகிறது."),
                    bdef("Blastostyle", "பிளாஸ்டோஸ்டைல்",
                        "The central stalk of a gonangium on which the medusa buds are formed." to
                            "மெடூசா மொட்டுகள் உருவாகும் கோனாஞ்சியத்தின் மையத் தண்டு."),
                    bdef("Gonotheca", "கோனோதீகா",
                        "The perisarc case that encloses a gonangium." to
                            "கோனாஞ்சியத்தை மூடும் பெரிசார்க் உறை."),
                )),
            bsec("polymorphism", "Polymorphism", "பல்வடிவத்தன்மை",
                blocks = listOf(
                    bp("A colony of Obelia contains zooids of different forms with different duties. The hydranths feed; the gonangia reproduce. The coenosarc shares what the hydranths obtain with the gonangia, which cannot feed. This is polymorphism, and it is a division of labour among the members of a colony. The phenomenon is treated in a separate chapter, where Obelia is compared with the more extreme polymorphism of Physalia." to
                        "ஒபீலியா காலனியில் வெவ்வேறு பணிகளுக்கான வெவ்வேறு வடிவ சூயிட்டுகள் உள்ளன. ஹைட்ராந்துகள் உண்கின்றன; கோனாஞ்சியங்கள் இனப்பெருக்கம் செய்கின்றன. ஹைட்ராந்துகள் பெறுவதை, உண்ண இயலாத கோனாஞ்சியங்களுடன் சீனோசார்க் பகிர்கிறது. இதுவே பல்வடிவத்தன்மை; காலனி உறுப்பினர்களுக்கிடையிலான பணிப் பிரிவினை. இந்நிகழ்வு தனி அத்தியாயத்தில் கையாளப்படுகிறது; அங்கு ஒபீலியா, ஃபைசாலியாவின் அதிகத் தீவிரமான பல்வடிவத்தன்மையுடன் ஒப்பிடப்படுகிறது."),
                    bnote("One genotype", "ஒரே மரபணுவகை",
                        "All the zooids of a colony are genetically alike because they arise by budding from one planula. Their different forms result from the way they develop, and not from different genes." to
                            "காலனியின் அனைத்து சூயிட்டுகளும் ஒரே பிளானுலாவிலிருந்து மொட்டுவிடுவதால் தோன்றியதால் மரபுப்படி ஒத்தவை. அவற்றின் வெவ்வேறு வடிவங்கள் அவை வளரும் விதத்தால் ஏற்படுகின்றன; வெவ்வேறு மரபணுக்களால் அல்ல."),
                    bdef("Polymorphism", "பல்வடிவத்தன்மை",
                        "The occurrence of zooids of different forms and functions in one colony." to
                            "ஒரே காலனியில் வெவ்வேறு வடிவம், பணி கொண்ட சூயிட்டுகள் இருத்தல்."),
                )),
            bsec("physiology", "Life processes of the colony", "காலனியின் வாழ்க்கைச் செயல்முறைகள்",
                blocks = listOf(
                    btable("Hydranth and gonangium compared", "ஹைட்ராந்த், கோனாஞ்சியம் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Hydranth" to "ஹைட்ராந்த்", "Gonangium" to "கோனாஞ்சியம்"),
                        listOf(
                            listOf("Function" to "பணி", "Feeding" to "உண்ணுதல்", "Asexual production of medusae" to "மெடூசாக்களின் பாலிலா உருவாக்கம்"),
                            listOf("Mouth and tentacles" to "வாயும் உணர்கொம்புகளும்", "Present" to "உண்டு", "Absent" to "இல்லை"),
                            listOf("Covering" to "உறை", "Hydrotheca" to "ஹைட்ரோதீகா", "Gonotheca" to "கோனோதீகா"),
                            listOf("Nutrition" to "ஊட்டம்", "Obtains food itself" to "தானே உணவு பெறுகிறது", "Fed through the coenosarc" to "சீனோசார்க் வழியாக ஊட்டப்படுகிறது"),
                        )),
                    bp("Respiration and excretion are carried out by diffusion through the thin epidermis and gastrodermis, since there are no organs for them. The movement of the colony is limited to the contraction of the epitheliomuscular cells, by which a hydranth may be drawn into its hydrotheca when it is disturbed. The nerve net passes the stimulus from one part of a zooid to the others, but between zooids the coenosarc is the only connection, and the response of a colony to a touch is therefore local and slow." to
                        "சுவாசமும் கழிவு நீக்கமும் மெல்லிய எப்பிடெர்மிஸ், காஸ்ட்ரோடெர்மிஸ் வழியாக விரவுதலால் நிகழ்கின்றன; அவற்றுக்கு உறுப்புகள் இல்லை. காலனியின் இயக்கம் எப்பிதீலியோதசை செல்களின் சுருக்கத்துக்கு வரம்புக்குட்பட்டது; தொந்தரவு செய்யப்படும்போது ஹைட்ராந்த் அதன் ஹைட்ரோதீகாவுக்குள் இழுக்கப்படலாம். நரம்பு வலை ஒரு சூயிட்டின் ஒரு பகுதியிலிருந்து மற்றவற்றுக்குத் தூண்டலைக் கடத்துகிறது; ஆனால் சூயிட்டுகளுக்கிடையே சீனோசார்க் மட்டுமே இணைப்பு; எனவே தொடுதலுக்கு காலனியின் துலங்கல் உள்ளூர்த் தன்மையும் மெதுவும் ஆனது."),
                    bp("Growth of the colony is by budding. New zooids and branches arise from the coenosarc, the perisarc being laid down around them by the epidermis, and in this way the colony may grow for years. Since each bud is part of the same living tube, the colony grows as a single system and not as a heap of separate animals." to
                        "காலனியின் வளர்ச்சி மொட்டுவிடுதலால் நிகழ்கிறது. புதிய சூயிட்டுகளும் கிளைகளும் சீனோசார்க்கிலிருந்து தோன்றுகின்றன; அவற்றைச் சுற்றி எப்பிடெர்மிஸ் பெரிசார்க்கை படியவிடுகிறது; இவ்வாறு காலனி பல ஆண்டுகள் வளரக்கூடும். ஒவ்வொரு மொட்டும் அதே உயிருள்ள குழாயின் பகுதியாக இருப்பதால், காலனி தனி விலங்குகளின் குவியலாக அல்லாமல் ஒரே அமைப்பாக வளர்கிறது."),
                )),
            bsec("medusa", "The medusa", "மெடூசா",
                blocks = listOf(
                    bp("The medusa of Obelia is a small, transparent, saucer-shaped animal, a few millimetres across. The convex upper surface is the exumbrella and the concave lower surface is the subumbrella. A shelf of tissue, the velum, projects inwards from the margin of the bell. In the centre of the subumbrella hangs the manubrium, a short tube with the mouth at its tip, and from it four radial canals run to a ring canal around the margin. Numerous short tentacles with cnidocytes hang from the margin, and eight small organs of balance, the statocysts, are placed at the margin." to
                        "ஒபீலியாவின் மெடூசா சிறிய, ஒளிபுகும், தட்டு வடிவ விலங்கு; குறுக்களவு சில மில்லிமீட்டர். குவிந்த மேற்பரப்பு எக்சும்பிரெல்லா; குழிந்த கீழ்ப்பரப்பு சப்பும்பிரெல்லா. மணியின் விளிம்பிலிருந்து திசுவின் அலமாரியான வெலம் உள்நோக்கி நீட்டுகிறது. சப்பும்பிரெல்லாவின் மையத்தில் முனையில் வாயுடைய குறுகிய குழாயான மேனுப்ரியம் தொங்குகிறது; அதிலிருந்து நான்கு ஆரக் கால்வாய்கள் விளிம்பைச் சுற்றியுள்ள வளையக் கால்வாய்க்குச் செல்கின்றன. விளிம்பிலிருந்து நிடோசைட்டுகளுடன் பல குறுகிய உணர்கொம்புகள் தொங்குகின்றன; விளிம்பில் எட்டு சிறு சமநிலை உறுப்புகளான ஸ்டேட்டோசிஸ்ட்கள் உள்ளன."),
                    bp("The four gonads lie on the radial canals and are gastrodermal in origin in some accounts and epidermal in others, which is a matter on which textbooks differ; for the answer it is enough to say that the gonads are borne on the radial canals. The sexes are separate. The medusa swims by rhythmic contraction of the bell, with the velum making the jet of water narrower, and it feeds on small animals like the polyp." to
                        "நான்கு இனப்பெருக்கச் சுரப்பிகள் ஆரக் கால்வாய்களின் மேல் உள்ளன; அவற்றின் தோற்றம் சில நூல்களில் காஸ்ட்ரோடெர்மிஸ், சிலவற்றில் எப்பிடெர்மிஸ் எனக் கூறப்படுகிறது; நூல்கள் இதில் வேறுபடுகின்றன; விடைக்கு சுரப்பிகள் ஆரக் கால்வாய்களின் மேல் தாங்கப்படுகின்றன எனக் கூறுவது போதுமானது. பாலினங்கள் தனித்தனி. மெடூசா மணியின் தாள சுருக்கத்தால் நீந்துகிறது; வெலம் நீர்ப்பீச்சை ஒடுக்குகிறது; பாலிப்பைப் போலவே சிறு விலங்குகளை உண்கிறது."),
                    bfig("obelia/medusa", "The medusa of Obelia, with the manubrium and mouth, radial and ring canals, gonads, marginal tentacles and statocysts." to
                        "ஒபீலியாவின் மெடூசா: மேனுப்ரியம், வாய், ஆர, வளையக் கால்வாய்கள், இனப்பெருக்கச் சுரப்பிகள், விளிம்பு உணர்கொம்புகள், ஸ்டேட்டோசிஸ்ட்கள்."),
                    bdef("Velum", "வெலம்",
                        "The shelf of tissue projecting inwards from the margin of a hydrozoan medusa, which narrows the opening of the bell." to
                            "ஹைட்ரோசோவா மெடூசாவின் மணி விளிம்பிலிருந்து உள்நோக்கி நீட்டும், மணியின் திறப்பைச் சுருக்கும் திசு அலமாரி."),
                    bdef("Manubrium", "மேனுப்ரியம்",
                        "The tube hanging from the centre of the subumbrella of a medusa, with the mouth at its tip." to
                            "மெடூசாவின் சப்பும்பிரெல்லா மையத்திலிருந்து தொங்கும், முனையில் வாயுடைய குழாய்."),
                    bdef("Statocyst", "ஸ்டேட்டோசிஸ்ட்",
                        "A small organ of balance at the margin of a medusa." to
                            "மெடூசாவின் விளிம்பிலுள்ள சிறு சமநிலை உறுப்பு."),
                )),
            bsec("life-history", "Life history", "வாழ்க்கை வரலாறு",
                blocks = listOf(
                    bpoints("The life history in sequence", "வரிசையில் வாழ்க்கை வரலாறு",
                        "The attached colony of polyps, with hydranths and gonangia, is the asexual stage." to "ஹைட்ராந்துகளும் கோனாஞ்சியங்களும் கொண்ட ஒட்டிய பாலிப் காலனி பாலிலா நிலை.",
                        "The gonangium produces medusa buds on the blastostyle by budding." to "கோனாஞ்சியம் பிளாஸ்டோஸ்டைலில் மெடூசா மொட்டுகளை மொட்டுவிடுதலால் உருவாக்குகிறது.",
                        "The mature medusae leave the gonotheca and swim freely; they are the sexual stage." to "முதிர்ந்த மெடூசாக்கள் கோனோதீகாவை விட்டு சுதந்திரமாக நீந்துகின்றன; இது பாலின நிலை.",
                        "Male and female medusae shed sperm and eggs into the sea water, where fertilisation occurs and a zygote forms." to "ஆண், பெண் மெடூசாக்கள் விந்தணுக்களையும் முட்டைகளையும் கடல் நீரில் விடுகின்றன; கருவுறுதல் நிகழ்ந்து கருமுட்டை உருவாகிறது.",
                        "The zygote develops through a blastula into a ciliated, solid planula larva, which swims freely." to "கருமுட்டை பிளாஸ்டுலா வழியாக கசையிழை, திண்ம பிளானுலா லார்வாவாக வளர்ந்து சுதந்திரமாக நீந்துகிறது.",
                        "The planula settles on a solid surface, forms the first polyp, and repeated budding gives a new colony." to "பிளானுலா உறுதியான பரப்பில் படிந்து முதல் பாலிப்பை உருவாக்குகிறது; மீண்டும் மீண்டும் மொட்டுவிடுவதால் புதிய காலனி தோன்றுகிறது.",
                    ),
                    bfig("obelia/life-cycle", "The life cycle of Obelia, from the colony and the medusa bud to the free medusa, the zygote, the planula and the young polyp." to
                        "ஒபீலியாவின் வாழ்க்கைச் சுழற்சி: காலனி, மெடூசா மொட்டு முதல் தனி மெடூசா, கருமுட்டை, பிளானுலா, இளம் பாலிப் வரை."),
                    bp("The life history is an alternation of an asexual polyp generation with a sexual medusa generation. This kind of life cycle is called metagenesis. It should be noted that the medusa is produced asexually by the polyp, but it is the medusa that makes the gametes, and the polyp itself makes none." to
                        "இவ்வாழ்க்கை வரலாறு பாலிலா பாலிப் தலைமுறையும் பாலின மெடூசா தலைமுறையும் மாறிமாறி வருவது. இவ்வகை வாழ்க்கைச் சுழற்சி மெட்டாஜெனிசிஸ் எனப்படுகிறது. மெடூசா பாலிப்பால் பாலிலா முறையில் உருவாகிறது, ஆனால் கேமீட்டுகளை உருவாக்குவது மெடூசாவே; பாலிப் தானே எதையும் உருவாக்குவதில்லை என்பதைக் கவனிக்க வேண்டும்."),
                    bdef("Metagenesis", "மெட்டாஜெனிசிஸ்",
                        "A life cycle in which an asexual generation alternates with a sexual generation, as the polyp and medusa of Obelia do." to
                            "ஒபீலியாவின் பாலிப்பும் மெடூசாவும் போல, பாலிலா தலைமுறையும் பாலின தலைமுறையும் மாறிமாறி வரும் வாழ்க்கைச் சுழற்சி."),
                    bdef("Planula", "பிளானுலா",
                        "The ciliated, solid, free-swimming larva of cnidarians." to
                            "நிடேரியாக்களின் கசையிழை, திண்ம, சுதந்திரமாக நீந்தும் லார்வா."),
                    bnote("Not Physalia", "ஃபைசாலியா அல்ல",
                        "Obelia is a fixed colony whose zooids are polyps, with the medusa as a separate sexual stage. Physalia, the Portuguese man-of-war, is a floating colony with a gas-filled pneumatophore and several kinds of zooid. Do not describe a pneumatophore in Obelia." to
                            "ஒபீலியா நிலையான காலனி; சூயிட்டுகள் பாலிப்புகள்; மெடூசா தனிப் பாலின நிலை. ஃபைசாலியா, போர்த்துக்கீசிய போர்க்கப்பல் மீன், வாயு நிரம்பிய நியூமட்டோஃபோருடன் பல வகை சூயிட்டுகள் கொண்ட மிதக்கும் காலனி. ஒபீலியாவில் நியூமட்டோஃபோரை விவரிக்க வேண்டாம்."),
                )),
            bsec("significance", "Why Obelia is studied", "ஒபீலியா ஏன் கற்கப்படுகிறது",
                blocks = listOf(
                    bp("Obelia shows in a single species most of the ideas of the phylum: the diploblastic body, the gastrovascular cavity, the cnidocytes, the polyp and the medusa, polymorphism and metagenesis. It also shows how a colony may be an individual in some respects and a group in others, since its zooids are separate animals in form but are one in nutrition and in genotype." to
                        "தொகுதியின் பெரும்பாலான கருத்துகளை ஒபீலியா ஒரே இனத்தில் காட்டுகிறது: இரு அடுக்கு உடல், காஸ்ட்ரோவாஸ்குலர் குழி, நிடோசைட்டுகள், பாலிப், மெடூசா, பல்வடிவத்தன்மை, மெட்டாஜெனிசிஸ். காலனி சில வகையில் தனி உயிரியாகவும் சில வகையில் குழுவாகவும் இருக்க முடியும் என்பதையும் காட்டுகிறது; அதன் சூயிட்டுகள் வடிவத்தில் தனி விலங்குகள், ஆனால் ஊட்டத்திலும் மரபணுவகையிலும் ஒன்றே."),
                )),
        ),
        summary = listOf(
            "Obelia is a fixed, branching marine hydroid colony attached by a hydrorhiza." to "ஒபீலியா ஹைட்ரோரைசாவால் ஒட்டிய, கிளைத்த கடல் ஹைட்ராய்டு காலனி.",
            "The stem has a non-living chitinous perisarc and a living coenosarc that encloses a common gastrovascular cavity." to "தண்டில் உயிரற்ற கைட்டின் பெரிசார்க்கும், பொதுவான காஸ்ட்ரோவாஸ்குலர் குழியை அடைக்கும் உயிருள்ள சீனோசார்க்கும் உள்ளன.",
            "Hydranths, in hydrothecae, feed; gonangia, in gonothecae, reproduce asexually by producing medusa buds on the blastostyle." to "ஹைட்ரோதீகாவில் உள்ள ஹைட்ராந்துகள் உண்கின்றன; கோனோதீகாவில் உள்ள கோனாஞ்சியங்கள் பிளாஸ்டோஸ்டைலில் மெடூசா மொட்டுகளை உருவாக்கிப் பாலிலா இனப்பெருக்கம் செய்கின்றன.",
            "The medusa is free-swimming, with four radial canals, a ring canal, a velum and statocysts, and produces the gametes." to "மெடூசா சுதந்திரமாக நீந்துவது; நான்கு ஆரக் கால்வாய்கள், வளையக் கால்வாய், வெலம், ஸ்டேட்டோசிஸ்ட்கள் கொண்டது; கேமீட்டுகளை உருவாக்குகிறது.",
            "Fertilisation gives a zygote that becomes a ciliated planula, which settles and buds into a new colony." to "கருவுறுதல் கருமுட்டையைத் தருகிறது; அது கசையிழை பிளானுலாவாகி, படிந்து, மொட்டுவிட்டுப் புதிய காலனியாகிறது.",
            "Polymorphism is the division of labour among genetically alike zooids; it is not the pneumatophore-bearing colony of Physalia." to "பல்வடிவத்தன்மை மரபுப்படி ஒத்த சூயிட்டுகளிடையேயான பணிப் பிரிவினை; நியூமட்டோஃபோர் கொண்ட ஃபைசாலியா காலனி அல்ல.",
            "The life history is metagenesis, an alternation of an asexual polyp and a sexual medusa." to "வாழ்க்கை வரலாறு மெட்டாஜெனிசிஸ்; பாலிலா பாலிப்பும் பாலின மெடூசாவும் மாறிமாறி வருவது.",
        ),
        checks = listOf(
            bqc("What is coenosarc?", "சீனோசார்க் என்றால் என்ன?",
                "The living tissue inside the perisarc that joins the zooids of the colony.", "காலனியின் சூயிட்டுகளை இணைக்கும், பெரிசார்க்கினுள் உள்ள உயிருள்ள திசு."),
            bqc("Name the cup around a hydranth.", "ஹைட்ராந்தைச் சுற்றியுள்ள கிண்ணத்தின் பெயர் என்ன?",
                "The hydrotheca.", "ஹைட்ரோதீகா."),
            bqc("Which zooid produces the medusa buds?", "மெடூசா மொட்டுகளை உருவாக்கும் சூயிட் எது?",
                "The gonangium, on its blastostyle.", "கோனாஞ்சியம்; அதன் பிளாஸ்டோஸ்டைலில்."),
            bqc("Which stage of Obelia produces the gametes?", "ஒபீலியாவின் எந்த நிலை கேமீட்டுகளை உருவாக்குகிறது?",
                "The medusa.", "மெடூசா."),
            bqc("Name the larva of Obelia.", "ஒபீலியாவின் லார்வாவின் பெயர் என்ன?",
                "The planula.", "பிளானுலா."),
        ),
    )
}
