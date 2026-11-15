package com.gasczoology.invertebratelab.data

/** Book-style chapter: Obelia. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object ObeliaBookChapter {
    val chapter = bchapter(
        lessonId = "u2-obelia",
        title = "Obelia: a colonial hydroid" to "ஒபீலியா: ஒரு காலனி ஹைட்ராய்டு",
        objectives = listOf(
            "Identify the colony's feeding and reproductive structures." to
                "கூட்டின் உணவூட்ட, இனப்பெருக்க அமைப்புகளை அடையாளம் காணுதல்.",
            "Explain the structure and role of the medusa." to
                "மெடூசாவின் அமைப்பையும் பணியையும் விளக்குதல்.",
            "Trace development from fertilisation to a new colony." to
                "கருவுறுதல் முதல் புதிய கூட்டு உருவாதல் வரை வரிசைப்படுத்துதல்.",
        ),
        introduction = listOf(
            "Obelia has an attached colony of polyps and a free-swimming medusa stage. Its life cycle links colony growth, feeding, sexual reproduction and larval settlement." to
                "ஒபீலியாவில் நிலையாக ஒட்டியிருக்கும் பாலிப் கூட்டும் நீந்தும் மெடூசா நிலையும் காணப்படுகின்றன. கூட்டின் வளர்ச்சி, உணவூட்டம், பாலின இனப்பெருக்கம், லார்வா நிலைகொள்ளுதல் ஆகியவை அதன் வாழ்க்கைச் சுழற்சியில் இணைகின்றன.",
        ),
        sections = listOf(
            bsec("habitat", "Habit and habitat", "வாழ்முறையும் வாழிடமும்",
                blocks = listOf(
                    bp("Obelia is a marine hydroid, found in shallow coastal water, where it grows on stones, shells, wooden piles, seaweeds and the hulls of boats. The colony is fixed and so it feeds on the small organisms carried to it by the water. A colony may grow to a few centimetres in height and appears as a delicate, feathery or fern-like growth, often mistaken for a seaweed by the beginner." to
                        "ஒபீலியா கடல் ஹைட்ராய்டு; ஆழமற்ற கடலோர நீரில், கற்கள், ஓடுகள், மரக்கம்பங்கள், கடற்பாசிகள், படகுகளின் அடிப்பகுதிகள் மீது வளர்கிறது. காலனி நிலையானது; நீர் கொண்டு வரும் சிறு உயிரிகளை உண்கிறது. காலனி சில சென்டிமீட்டர் உயரம் வளரக்கூடும்; மென்மையான, இறகு அல்லது பன்னம் போன்ற வளர்ச்சியாகத் தோன்றும்; தொடக்கநிலையினர் அடிக்கடி கடற்பாசி என்று தவறாக எண்ணுவர்."),
                    bdef("Hydroid", "ஹைட்ராய்டு (Hydroid)",
                        "A hydrozoan in its polyp stage, especially one that forms an attached, branching colony." to
                            "பாலிப் நிலையிலுள்ள ஹைட்ரோசோவா; குறிப்பாக ஒட்டிய, கிளைத்த காலனியை உருவாக்குவது."),
                    bdef("Zooid", "சூயிட் (Zooid)",
                        "An individual member of a colony, such as a feeding hydranth or a reproductive gonangium." to
                            "உணவூட்டும் ஹைட்ராந்த் அல்லது இனப்பெருக்க கோனாஞ்சியம் போன்ற காலனியின் தனி உறுப்பினர்."),
                )),
            bsec("colony", "Structure of the colony", "காலனியின் அமைப்பு",
                blocks = listOf(
                    bp("The colony is attached by a root-like stolon, the hydrorhiza, which creeps over the surface. From it arise upright stems that branch, usually in a zig-zag pattern, and at each bend a zooid is borne. The whole colony is built on a single plan, in which a tube of living tissue is enclosed in a covering of non-living material." to
                        "ஒபீலியா கூட்டுயிரியின் அடிப்பகுதியில் ஹைட்ரோரைசா எனப்படும் வேரைப் போன்ற படரும் தண்டு உள்ளது. இது உறுதியான பரப்பில் படர்ந்து கூட்டுயிரியைப் பற்றியிருக்கச் செய்கிறது. இதிலிருந்து மேல்நோக்கி வளரும் தண்டுகள் தோன்றி, பொதுவாக வளைந்து வளைந்து கிளைகளாகப் பிரிகின்றன. கிளைகளின் குறிப்பிட்ட இடங்களில் தனித்தனியான சூயிட்கள் காணப்படுகின்றன. கூட்டுயிரியின் தண்டும் கிளைகளும் உள்ளே உயிருள்ள திசுக்குழாயையும் வெளியே உயிரற்ற பாதுகாப்பு உறையையும் கொண்டுள்ளன."),
                    bpoints("The two parts of the stem", "தண்டின் இரு பகுதிகள்",
                        "Perisarc: the non-living outer covering of chitin, which supports and protects the colony." to "பெரிசார்க்: கைட்டினால் ஆன உயிரற்ற வெளி உறை; காலனியைத் தாங்கிப் பாதுகாக்கிறது.",
                        "Coenosarc: the living tissue inside the perisarc, with an outer epidermis, a thin mesoglea and an inner gastrodermis." to "சீனோசார்க்: பெரிசார்க்கினுள் உள்ள உயிருள்ள திசு; வெளி எப்பிடெர்மிஸ், மெல்லிய மீசோகிளியா, உள் காஸ்ட்ரோடெர்மிஸ் கொண்டது.",
                        "The coenosarc encloses a continuous gastrovascular cavity, and so the food taken by one zooid is distributed to all." to "சீனோசார்க் தொடர்ச்சியான காஸ்ட்ரோவாஸ்குலர் குழியை அடைக்கிறது; எனவே ஒரு சூயிட் உண்ட உணவு அனைத்துக்கும் பகிரப்படுகிறது.",
                    ),
                    bp("Around each hydranth the perisarc widens into a cup, the hydrotheca, into which the hydranth can withdraw. Around each gonangium it forms a longer, flask-shaped case, the gonotheca. The perisarc is often ringed at the joints, which gives the stem a limited flexibility." to
                        "ஒவ்வொரு ஹைட்ராந்தைச் சுற்றியும் பெரிசார்க் கிண்ணமாக விரிகிறது; அது ஹைட்ரோதீகா; ஹைட்ராந்த் அதனுள் பின்வாங்க முடியும். ஒவ்வொரு கோனாஞ்சியத்தைச் சுற்றியும் நீண்ட, குடுவை வடிவ உறையான கோனோதீகாவை உருவாக்குகிறது. பெரிசார்க் மூட்டுகளில் வளையங்களாக இருப்பதால் தண்டுக்கு வரம்புக்குட்பட்ட நெகிழ்வு கிடைக்கிறது."),
                    bfig("obelia/colony", "Part of a colony of Obelia, showing the hydrorhiza, the branching stem with perisarc and coenosarc, feeding hydranths in their hydrothecae, and gonangia." to
                        "ஒபீலியா காலனியின் ஒரு பகுதி: ஹைட்ரோரைசா, பெரிசார்க்கும் சீனோசார்க்கும் கொண்ட கிளைத்த தண்டு, ஹைட்ரோதீகாவில் உள்ள ஹைட்ராந்துகள், கோனாஞ்சியம்."),
                    bdef("Perisarc", "பெரிசார்க் (Perisarc)",
                        "The non-living, chitinous outer covering of the stem and branches of a hydroid colony." to
                            "ஹைட்ராய்டு காலனியின் தண்டு, கிளைகளின் உயிரற்ற, கைட்டின் வெளி உறை."),
                    bdef("Coenosarc", "சீனோசார்க் (Coenosarc)",
                        "The living tissue inside the perisarc that joins all the zooids of the colony and encloses the common gastrovascular cavity." to
                            "பெரிசார்க்கினுள் உள்ள, காலனியின் அனைத்து சூயிட்டுகளையும் இணைத்து பொதுவான காஸ்ட்ரோவாஸ்குலர் குழியை அடைக்கும் உயிருள்ள திசு."),
                    bdef("Hydrotheca", "ஹைட்ரோதீகா (Hydrotheca)",
                        "The cup of perisarc surrounding a hydranth." to
                            "ஹைட்ராந்தைச் சுற்றியுள்ள பெரிசார்க் கிண்ணம்."),
                    bdef("Hydrorhiza", "ஹைட்ரோரைசா (Hydrorhiza)",
                        "The creeping, root-like stolon by which a hydroid colony is attached to the substratum." to
                            "ஹைட்ராய்டு காலனி அடித்தளத்தில் ஒட்டியிருக்கும், ஊர்ந்து செல்லும் வேர் போன்ற ஸ்டோலன்."),
                )),
            bsec("hydranth", "The hydranth, or feeding zooid", "ஹைட்ராந்த், அல்லது உணவூட்டும் சூயிட்",
                blocks = listOf(
                    bp("The hydranth is the feeding polyp of the colony; it is also called the gastrozooid or trophozooid. It is a small cylinder with a raised, cone-shaped hypostome at the free end, bearing the mouth, and a ring of solid tentacles around the hypostome. The tentacles carry batteries of cnidocytes. The body of the hydranth encloses a part of the gastrovascular cavity, which opens through the mouth and is continuous with the cavity of the stem." to
                        "ஹைட்ராந்த் என்பது ஒபீலியா கூட்டுயிரியில் உணவைப் பிடிக்கும் பாலிப் வடிவச் சூயிட் ஆகும். இதைக் காஸ்ட்ரோசூயிட் அல்லது டிரோஃபோசூயிட் என்றும் அழைப்பர். உருளை வடிவமுள்ள இதன் முனையில், வாயைத் தாங்கிய கூம்பு வடிவ ஹைப்போஸ்டோம் காணப்படுகிறது. அதைச் சுற்றி திடமான உணர்கொம்புகள் வளையமாக அமைந்துள்ளன. உணர்கொம்புகளில் இரையைப் பிடிக்க உதவும் நிடோசைட்டுகள் தொகுதிகளாகக் காணப்படுகின்றன. ஹைட்ராந்தின் உள்ளேயுள்ள உணவுக்குழி, வாய் வழியாக வெளியில் திறப்பதுடன் தண்டின் பொதுவான காஸ்ட்ரோவாஸ்குலர் குழியுடனும் தொடர்ச்சியாக இணைந்துள்ளது."),
                    bp("The hydranth catches small animals with its tentacles, paralyses them with nematocysts and passes them through the mouth into the cavity. Digestion begins in the cavity, where enzymes are secreted by the gland cells, and is completed in the cells of the gastrodermis. The partly digested food is circulated through the whole colony by the beating of flagella in the common cavity, and by the movement of the body. Undigested matter is expelled through the mouth." to
                        "ஹைட்ராந்த் உணர்கொம்புகளால் சிறு விலங்குகளைப் பிடித்து, நெமட்டோசிஸ்ட்களால் செயலிழக்கச் செய்து, வாய் வழியாகக் குழிக்குள் செலுத்துகிறது. சுரப்பி செல்கள் சுரக்கும் நொதிகளால் குழியில் செரிமானம் தொடங்கி, காஸ்ட்ரோடெர்மிஸ் செல்களில் முடிகிறது. பகுதியாகச் செரித்த உணவு, பொதுக் குழியிலுள்ள கசையிழைகளின் அசைவாலும் உடல் இயக்கத்தாலும் முழு காலனிக்கும் சுற்றிச் செலுத்தப்படுகிறது. செரியாதவை வாய் வழியாக வெளியேற்றப்படுகின்றன."),
                    bdef("Hypostome", "ஹைப்போஸ்டோம்",
                        "The raised, cone-shaped region of a hydranth that bears the mouth." to
                            "ஹைட்ராந்தின் வாயைத் தாங்கும் உயர்ந்த கூம்பு வடிவப் பகுதி."),
                    bdef("Gastrozooid", "காஸ்ட்ரோசூயிட் (Gastrozooid)",
                        "The feeding zooid, or hydranth, of a hydroid colony." to
                            "ஹைட்ராய்டு காலனியின் உணவூட்டும் சூயிட், அதாவது ஹைட்ராந்த்."),
                )),
            bsec("gonangium", "The gonangium, or reproductive zooid", "கோனாஞ்சியம், அல்லது இனப்பெருக்க சூயிட்",
                blocks = listOf(
                    bp("The gonangium, or gonozooid, is the reproductive zooid. It arises at the junction of the stem and a branch and is a long, vase-shaped structure enclosed in a gonotheca. It has neither mouth nor tentacles and does not feed; it is nourished by the other zooids through the common cavity. Its centre is a stalk of coenosarc, the blastostyle, on which medusa buds are produced. The buds are made by the asexual process of budding." to
                        "கோனாஞ்சியம் அல்லது கோனோசூயிட் என்பது இனப்பெருக்கப் பணியை மேற்கொள்ளும் சூயிட் ஆகும். இது பொதுவாகத் தண்டும் கிளையும் சந்திக்கும் பகுதியில் தோன்றும். நீண்ட குவளை வடிவமுள்ள இவ்வமைப்பை கோனோதீகா என்ற பாதுகாப்பு உறை சூழ்ந்துள்ளது. கோனாஞ்சியத்தில் வாயோ உணர்கொம்புகளோ இல்லை; எனவே இது தனியாக உணவு பிடிப்பதில்லை. ஹைட்ராந்துகள் பெறும் ஊட்டப்பொருள்கள் பொதுவான உணவுக்குழி வழியாக இதற்குக் கிடைக்கின்றன. இதன் மையத்தில் சீனோசார்க்கிலிருந்து தொடரும் பிளாஸ்டோஸ்டைல் என்னும் தண்டு உள்ளது. அதில் பாலிலா மொட்டு விடுதல் மூலம் மெடூசா மொட்டுகள் உருவாகின்றன."),
                    bp("The buds develop in a series along the blastostyle, the oldest at the top, and as each matures it escapes through the open end of the gonotheca as a small, free-swimming medusa. In this way a single gonangium releases many medusae over a period." to
                        "பிளாஸ்டோஸ்டைலில் மெடூசா மொட்டுகள் அடுத்தடுத்து உருவாகின்றன. முதலில் தோன்றிய மொட்டுகள் பொதுவாக மேல்பகுதியில் காணப்படும். ஒவ்வொரு மொட்டும் வளர்ச்சியடைந்ததும் கோனோதீகாவின் திறந்த முனை வழியாக வெளியேறி, தனியாக நீந்தும் சிறிய மெடூசாவாக மாறுகிறது. இவ்வாறு ஒரு கோனாஞ்சியம் குறிப்பிட்ட காலப்பகுதியில் பல மெடூசாக்களை உருவாக்கி வெளியிடுகிறது."),
                    bfig("obelia/hydranth-gonangium", "A hydranth and a gonangium of Obelia, showing the hypostome, tentacles and hydrotheca of the hydranth and the blastostyle with medusa buds inside the gonotheca of the gonangium." to
                        "ஒபீலியாவின் ஹைட்ராந்தும் கோனாஞ்சியமும்: ஹைட்ராந்தின் ஹைப்போஸ்டோம், உணர்கொம்புகள், ஹைட்ரோதீகா; கோனாஞ்சியத்தின் கோனோதீகாவினுள் பிளாஸ்டோஸ்டைலும் மெடுசா மொட்டுகளும்."),
                    bdef("Gonangium", "கோனாஞ்சியம் (Gonangium)",
                        "The reproductive zooid of Obelia, enclosed in a gonotheca, which produces medusa buds on a blastostyle." to
                            "ஒபீலியாவின் இனப்பெருக்க சூயிட்; கோனோதீகாவால் மூடப்பட்டு, பிளாஸ்டோஸ்டைலில் மெடுசா மொட்டுகளை உருவாக்குகிறது."),
                    bdef("Blastostyle", "பிளாஸ்டோஸ்டைல் (Blastostyle)",
                        "The central stalk of a gonangium on which the medusa buds are formed." to
                            "மெடுசா மொட்டுகள் உருவாகும் கோனாஞ்சியத்தின் மையத் தண்டு."),
                    bdef("Gonotheca", "கோனோதீகா (Gonotheca)",
                        "The perisarc case that encloses a gonangium." to
                            "கோனாஞ்சியத்தை மூடும் பெரிசார்க் உறை."),
                )),
            bsec("polymorphism", "Polymorphism", "பல்வடிவத்தன்மை",
                blocks = listOf(
                    bp("A colony of Obelia contains zooids of different forms with different duties. The hydranths feed; the gonangia reproduce. The coenosarc shares what the hydranths obtain with the gonangia, which cannot feed. This is polymorphism, and it is a division of labour among the members of a colony. The phenomenon is treated in a separate chapter, where Obelia is compared with the more extreme polymorphism of Physalia." to
                        "ஒபீலியா காலனியில் வெவ்வேறு பணிகளுக்கான வெவ்வேறு வடிவ சூயிட்டுகள் உள்ளன. ஹைட்ராந்துகள் உண்கின்றன; கோனாஞ்சியங்கள் இனப்பெருக்கம் செய்கின்றன. ஹைட்ராந்துகள் பெறுவதை, உண்ண இயலாத கோனாஞ்சியங்களுடன் சீனோசார்க் பகிர்கிறது. இதுவே பல்வடிவத்தன்மை; காலனி உறுப்பினர்களுக்கிடையிலான பணிப் பிரிவினை. இந்நிகழ்வு தனி அத்தியாயத்தில் கையாளப்படுகிறது; அங்கு ஒபீலியா, பைசாலியாவின் அதிகத் தீவிரமான பல்வடிவத்தன்மையுடன் ஒப்பிடப்படுகிறது."),
                    bnote("One genotype", "ஒரே மரபணுவகை",
                        "All the zooids of a colony are genetically alike because they arise by budding from one planula. Their different forms result from the way they develop, and not from different genes." to
                            "காலனியின் அனைத்து சூயிட்டுகளும் ஒரே பிளானுலாவிலிருந்து மொட்டுவிடுவதால் தோன்றியதால் மரபுப்படி ஒத்தவை. அவற்றின் வெவ்வேறு வடிவங்கள் அவை வளரும் விதத்தால் ஏற்படுகின்றன; வெவ்வேறு மரபணுக்களால் அல்ல."),
                    bdef("Polymorphism", "பல்வடிவத்தன்மை (Polymorphism)",
                        "The occurrence of zooids of different forms and functions in one colony." to
                            "ஒரே காலனியில் வெவ்வேறு வடிவம், பணி கொண்ட சூயிட்டுகள் இருத்தல்."),
                )),
            bsec("physiology", "Life processes of the colony", "காலனியின் வாழ்க்கைச் செயல்முறைகள்",
                blocks = listOf(
                    btable("Hydranth and gonangium compared", "ஹைட்ராந்த், கோனாஞ்சியம் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Hydranth" to "ஹைட்ராந்த்", "Gonangium" to "கோனாஞ்சியம்"),
                        listOf(
                            listOf("Function" to "பணி", "Feeding" to "உண்ணுதல்", "Asexual production of medusae" to "மெடுசாக்களின் பாலிலா உருவாக்கம்"),
                            listOf("Mouth and tentacles" to "வாயும் உணர்கொம்புகளும்", "Present" to "உண்டு", "Absent" to "இல்லை"),
                            listOf("Covering" to "உறை", "Hydrotheca" to "ஹைட்ரோதீகா", "Gonotheca" to "கோனோதீகா"),
                            listOf("Nutrition" to "ஊட்டம்", "Obtains food itself" to "தானே உணவு பெறுகிறது", "Fed through the coenosarc" to "சீனோசார்க் வழியாக ஊட்டப்படுகிறது"),
                        )),
                    bp("Respiration and excretion are carried out by diffusion through the thin epidermis and gastrodermis, since there are no organs for them. The movement of the colony is limited to the contraction of the epitheliomuscular cells, by which a hydranth may be drawn into its hydrotheca when it is disturbed. The nerve net passes the stimulus from one part of a zooid to the others, but between zooids the coenosarc is the only connection, and the response of a colony to a touch is therefore local and slow." to
                        "ஒபீலியாவில் தனித்த சுவாச உறுப்புகளும் கழிவுநீக்க உறுப்புகளும் இல்லை. ஆக்சிஜன் உடற்பரப்பு வழியாக விரவல் முறையில் செல்களுக்குள் செல்கிறது; கார்பன் டைஆக்சைடும் பிற வளர்சிதைமாற்றக் கழிவுகளும் எதிர்த்திசையில் வெளியேறுகின்றன. எப்பிதீலியத் தசைச் செல்கள் சுருங்குவதால் ஹைட்ராந்த் தனது உடலைச் சுருக்கி ஹைட்ரோதீகாவுக்குள் பின்வாங்க முடியும். பரவலாக அமைந்த நரம்பு வலை தூண்டுதல்களுக்கான பதில்களை ஒருங்கிணைக்க உதவுகிறது. சூயிட்டுகள் சீனோசார்க் வழியாகத் தொடர்ச்சியாக இணைந்திருந்தாலும், தொடுதலுக்கான பதில் பொதுவாக உள்ளூர்மயமாகவும் மெதுவாகவும் காணப்படுகிறது."),
                    bp("Growth of the colony is by budding. New zooids and branches arise from the coenosarc, the perisarc being laid down around them by the epidermis, and in this way the colony may grow for years. Since each bud is part of the same living tube, the colony grows as a single system and not as a heap of separate animals." to
                        "காலனியின் வளர்ச்சி மொட்டுவிடுதலால் நிகழ்கிறது. புதிய சூயிட்டுகளும் கிளைகளும் சீனோசார்க்கிலிருந்து தோன்றுகின்றன; அவற்றைச் சுற்றி எப்பிடெர்மிஸ் பெரிசார்க்கை படியவிடுகிறது; இவ்வாறு காலனி பல ஆண்டுகள் வளரக்கூடும். ஒவ்வொரு மொட்டும் அதே உயிருள்ள குழாயின் பகுதியாக இருப்பதால், காலனி தனி விலங்குகளின் குவியலாக அல்லாமல் ஒரே அமைப்பாக வளர்கிறது."),
                )),
            bsec("medusa", "The medusa", "மெடுசா",
                blocks = listOf(
                    bp("The medusa of Obelia is a small, transparent, saucer-shaped animal, a few millimetres across. The convex upper surface is the exumbrella and the concave lower surface is the subumbrella. A shelf of tissue, the velum, projects inwards from the margin of the bell. In the centre of the subumbrella hangs the manubrium, a short tube with the mouth at its tip, and from it four radial canals run to a ring canal around the margin. Numerous short tentacles with cnidocytes hang from the margin, and eight small organs of balance, the statocysts, are placed at the margin." to
                        "ஒபீலியாவின் மெடூசா சில மில்லிமீட்டர் குறுக்களவுடைய, ஒளி ஊடுருவக்கூடிய சிறிய குடை வடிவ உயிரியாகும். குடையின் குவிந்த மேல்பரப்பு எக்சும்பிரெல்லா என்றும் குழிந்த கீழ்ப்பரப்பு சப்பும்பிரெல்லா என்றும் அழைக்கப்படுகின்றன. குடையின் விளிம்பிலிருந்து வெலம் எனப்படும் மெல்லிய திசு மடிப்பு உட்புறமாக நீள்கிறது. சப்பும்பிரெல்லாவின் நடுவில் மேனுப்ரியம் என்ற சிறிய குழாய் போன்ற அமைப்பு தொங்குகிறது; அதன் நுனியில் வாய் அமைந்துள்ளது. மேனுப்ரியத்திலிருந்து நான்கு ஆரக் கால்வாய்கள் புறப்பட்டு, குடை விளிம்பைச் சுற்றியுள்ள வளையக் கால்வாயுடன் இணைகின்றன. விளிம்பில் நிடோசைட்டுகள் கொண்ட பல சிறிய உணர்கொம்புகளும், சமநிலையை உணரும் எட்டு ஸ்டேட்டோசிஸ்ட்களும் காணப்படுகின்றன."),
                    bp("The four gonads lie on the radial canals and are gastrodermal in origin in some accounts and epidermal in others, which is a matter on which textbooks differ; for the answer it is enough to say that the gonads are borne on the radial canals. The sexes are separate. The medusa swims by rhythmic contraction of the bell, with the velum making the jet of water narrower, and it feeds on small animals like the polyp." to
                        "நான்கு இனப்பெருக்கச் சுரப்பிகள் ஆரக் கால்வாய்களுடன் தொடர்புடைய இடங்களில் அமைந்துள்ளன. அவற்றின் திசுத் தோற்றம் குறித்த விளக்கங்கள் சில பாடநூல்களில் வேறுபடுகின்றன. ஒபீலியாவின் மெடூசாக்கள் தனித்தனி பாலினங்களைக் கொண்டவை. குடை வடிவ உடல் சீரான இடைவெளிகளில் சுருங்கி விரிவதால் நீரில் முன்னேறுகின்றன. குடை விளிம்பிலுள்ள வெலம், வெளியேற்றப்படும் நீரோட்டத்தை ஒருங்குபடுத்தி நீந்துவதற்கு உதவுகிறது. மெடூசாவும் சிறிய நீர்வாழ் விலங்குகளை உணவாகக் கொள்கிறது."),
                    bfig("obelia/medusa", "The medusa of Obelia, with the manubrium and mouth, radial and ring canals, gonads, marginal tentacles and statocysts." to
                        "ஒபீலியாவின் மெடுசா: மேனுப்ரியம், வாய், ஆர, வளையக் கால்வாய்கள், இனப்பெருக்கச் சுரப்பிகள், விளிம்பு உணர்கொம்புகள், ஸ்டேட்டோசிஸ்ட்கள்."),
                    bdef("Velum", "வெலம் (Velum)",
                        "The shelf of tissue projecting inwards from the margin of a hydrozoan medusa, which narrows the opening of the bell." to
                            "ஹைட்ரோசோவா மெடூசாவின் குடை விளிம்பிலிருந்து உட்புறமாக நீளும் மெல்லிய திசு மடிப்பு; குடையின் திறப்பைக் குறுக்கி நீந்தும் இயக்கத்திற்கு உதவுகிறது."),
                    bdef("Manubrium", "மேனுப்ரியம் (Manubrium)",
                        "The tube hanging from the centre of the subumbrella of a medusa, with the mouth at its tip." to
                            "மெடுசாவின் சப்பும்பிரெல்லா மையத்திலிருந்து தொங்கும், முனையில் வாயுடைய குழாய்."),
                    bdef("Statocyst", "ஸ்டேட்டோசிஸ்ட் (Statocyst)",
                        "A small organ of balance at the margin of a medusa." to
                            "மெடுசாவின் விளிம்பிலுள்ள சிறு சமநிலை உறுப்பு."),
                )),
            bsec("life-history", "Life history", "வாழ்க்கை வரலாறு",
                blocks = listOf(
                    bpoints("The life history in sequence", "வரிசையில் வாழ்க்கை வரலாறு",
                        "The attached colony of polyps, with hydranths and gonangia, is the asexual stage." to "ஹைட்ராந்துகளும் கோனாஞ்சியங்களும் கொண்ட ஒட்டிய பாலிப் காலனி பாலிலா நிலை.",
                        "The gonangium produces medusa buds on the blastostyle by budding." to "கோனாஞ்சியம் பிளாஸ்டோஸ்டைலில் மெடுசா மொட்டுகளை மொட்டுவிடுதலால் உருவாக்குகிறது.",
                        "The mature medusae leave the gonotheca and swim freely; they are the sexual stage." to "முதிர்ந்த மெடுசாக்கள் கோனோதீகாவை விட்டு சுதந்திரமாக நீந்துகின்றன; இது பாலின நிலை.",
                        "Male and female medusae shed sperm and eggs into the sea water, where fertilisation occurs and a zygote forms." to "ஆண், பெண் மெடுசாக்கள் விந்தணுக்களையும் முட்டைகளையும் கடல் நீரில் விடுகின்றன; கருவுறுதல் நிகழ்ந்து கருமுட்டை உருவாகிறது.",
                        "The zygote develops through a blastula into a ciliated, solid planula larva, which swims freely." to "கருவுற்ற முட்டை தொடர்ச்சியாகப் பிளவடைந்து பிளாஸ்டுலா நிலையை அடைகிறது. பின்னர் உடற்பரப்பில் குறுஇழைகள் (cilia) கொண்ட, பொதுவாகத் திடமான பிளானுலா இளவுயிரியாக வளர்ந்து நீந்துகிறது.",
                        "The planula settles on a solid surface, forms the first polyp, and repeated budding gives a new colony." to "பிளானுலா இளவுயிரி உறுதியான அடித்தளத்தில் ஒட்டிக்கொண்டு உருமாற்றம் அடைந்து முதல் பாலிப்பை உருவாக்குகிறது. அந்தப் பாலிப்பிலிருந்து தொடர்ந்து மொட்டுகள் தோன்றுவதால் புதிய ஒபீலியா கூட்டுயிரி உருவாகிறது.",
                    ),
                    bfig("obelia/life-cycle", "The life cycle of Obelia, from the colony and the medusa bud to the free medusa, the zygote, the planula and the young polyp." to
                        "ஒபீலியாவின் வாழ்க்கைச் சுழற்சி: காலனி, மெடுசா மொட்டு முதல் தனி மெடுசா, கருமுட்டை, பிளானுலா, இளம் பாலிப் வரை."),
                    bp("The life history is an alternation of an asexual polyp generation with a sexual medusa generation. This kind of life cycle is called metagenesis. It should be noted that the medusa is produced asexually by the polyp, but it is the medusa that makes the gametes, and the polyp itself makes none." to
                        "ஒபீலியாவின் வாழ்க்கைச் சுழற்சியில் பாலிலா இனப்பெருக்கம் செய்யும் பாலிப் கூட்டுயிரி நிலையும், பாலின இனப்பெருக்கம் செய்யும் மெடூசா நிலையும் மாறிமாறி வருகின்றன. இவ்வகை வாழ்க்கைச் சுழற்சி தலைமுறை மாற்றம் (metagenesis) எனப்படும். பாலிப் கூட்டுயிரியின் கோனாஞ்சியத்தில் மொட்டுவிடுதல் மூலம் மெடூசாக்கள் உருவாகின்றன; மெடூசாக்களில்தான் விந்தணுக்களும் முட்டைகளும் உருவாகின்றன. எனவே பாலிப் நிலையும் மெடூசா நிலையும் மேற்கொள்ளும் இனப்பெருக்கப் பணிகளைத் தெளிவாக வேறுபடுத்த வேண்டும்."),
                    bdef("Metagenesis", "மெட்டாஜெனிசிஸ் (Metagenesis)",
                        "A life cycle in which an asexual generation alternates with a sexual generation, as the polyp and medusa of Obelia do." to
                            "ஒபீலியாவின் பாலிப்பும் மெடுசாவும் போல, பாலிலா தலைமுறையும் பாலின தலைமுறையும் மாறிமாறி வரும் வாழ்க்கைச் சுழற்சி."),
                    bdef("Planula", "பிளானுலா",
                        "The ciliated, solid, free-swimming larva of cnidarians." to
                            "நிடேரியன்களில் காணப்படும், உடற்பரப்பில் குறுஇழைகள் கொண்ட, பொதுவாகத் திடமான அமைப்புடைய, நீந்திச் செல்லும் இளவுயிரி."),
                    bnote("Not Physalia", "பைசாலியா அல்ல",
                        "Obelia is a fixed colony whose zooids are polyps, with the medusa as a separate sexual stage. Physalia, the Portuguese man-of-war, is a floating colony with a gas-filled pneumatophore and several kinds of zooid. Do not describe a pneumatophore in Obelia." to
                            "ஒபீலியா நிலையான காலனி; சூயிட்டுகள் பாலிப்புகள்; மெடுசா தனிப் பாலின நிலை. பைசாலியா, போர்த்துக்கீசிய போர்க்கப்பல் மீன், வாயு நிரம்பிய நியூமட்டோஃபோருடன் பல வகை சூயிட்டுகள் கொண்ட மிதக்கும் காலனி. ஒபீலியாவில் நியூமட்டோஃபோரை விவரிக்க வேண்டாம்."),
                )),
            bsec("histology", "The body wall and its cells", "உடற்சுவரும் அதன் செல்களும்",
                blocks = listOf(
                    bp("The body of every zooid of Obelia is built of two layers of cells, and for this reason the animal is described as diploblastic. The outer layer is the epidermis, which covers the surface, and the inner layer is the gastrodermis, which lines the gastrovascular cavity. Between the two lies the mesoglea, a thin layer of jelly-like material which has few or no cells. The mesoglea of a hydroid is much thinner than that of a jellyfish, and in the polyp of Obelia it is a delicate supporting layer which can be seen only in sections. The medusa has a thicker mesoglea, and this gives it the firmness of a jelly." to
                        "ஒபீலியாவின் ஒவ்வொரு சூயிட்டின் உடற்சுவரிலும் இரண்டு முதன்மைச் செல் அடுக்குகள் காணப்படுகின்றன. எனவே இது ஈரடுக்கு (diploblastic) விலங்காகும். உடலின் வெளிப்பரப்பை எப்பிடெர்மிஸ் எனப்படும் புறத்தோல் மூடுகிறது; உணவுக்குழியின் உட்பரப்பை காஸ்ட்ரோடெர்மிஸ் எனப்படும் அகத்தோல் வரிசையாக அமைந்து சூழ்கிறது. இவ்விரு அடுக்குகளுக்கும் இடையில் மீசோகிளியா என்ற கூழ்மப் பொருள் உள்ளது. பாலிப் நிலையில் மீசோகிளியா மிகவும் மெல்லிய ஆதரவு அடுக்காகக் காணப்படுகிறது; மெடூசா நிலையில் அது ஒப்பீட்டளவில் தடிமனாக இருப்பதால் குடை வடிவ உடலுக்குத் தாங்குதிறன் அளிக்கிறது."),
                    bp("The cells of the two layers are of several kinds. The commonest cells of the epidermis are the epitheliomuscular cells, which form a covering and have contractile fibres at their bases, lying along the length of the body, so that when they contract the zooid is shortened. The gastrodermis has corresponding nutritive-muscular cells, which line the cavity, bear flagella that stir the contents, and take up food particles by engulfing them. Gland cells of the gastrodermis secrete digestive enzymes into the cavity. Between the bases of the epidermal cells lie small, rounded interstitial cells, which are unspecialised and which can develop into other kinds of cell, including the cells that bear the stinging capsules, the sensory and nerve cells, and the germ cells." to
                        "ஒபீலியாவின் புறத்தோலிலும் அகத்தோலிலும் வெவ்வேறு பணிகளை மேற்கொள்ளும் செல்கள் காணப்படுகின்றன. புறத்தோலில் உள்ள எப்பிதீலியத் தசைச் செல்கள் உடலைப் போர்த்திப் பாதுகாப்பதுடன், அவற்றின் அடிப்பகுதியில் உள்ள சுருங்கும் இழைகள் மூலம் சூயிட்டின் உடலைச் சுருக்குகின்றன. அகத்தோலில் உள்ள ஊட்டத் தசைச் செல்கள் உணவுக்குழியைச் சூழ்ந்து, கசையிழைகளின் அசைவால் அதன் உள்ளடக்கத்தைக் கலக்க உதவுகின்றன; உணவுத் துகள்களை உட்கொண்டு செல்லுக்குள் செரிமானத்திலும் பங்கேற்கின்றன. அகத்தோலின் சுரப்பிச் செல்கள் செரிமான நொதிகளை உணவுக்குழிக்குள் வெளியிடுவதால் செல்லுக்கு வெளியேயான செரிமானம் தொடங்குகிறது. புறத்தோல் செல்களுக்கிடையில் உள்ள சிறப்புறாத இடைநிலைச் செல்கள் வளர்ந்து பல்வேறு செல்வகைகளை உருவாக்க உதவுகின்றன; அவற்றின் பங்களிப்பு வளர்ச்சி நிலையையும் இனத்தையும் பொறுத்து மாறுபடலாம்."),
                    bp("The cells that give the phylum its name are the cnidoblasts, which are also called cnidocytes, the stinging cells. Each contains a capsule, the nematocyst, in which a thread is coiled, and on the surface of the cell there is a small trigger, the cnidocil. When prey touches the trigger the thread is shot out, and it entangles the prey, and in some kinds it injects a poison that paralyses the victim. The stinging cells are thickly set on the tentacles, so that the prey is captured by the tentacles and carried to the mouth. A nematocyst can be fired only once, and the cell which has used it is replaced by a new one, which is derived from the interstitial cells. Nerve cells form a diffuse nerve net below the epidermis, in which there is no brain, and the impulse spreads from the place where it started to the neighbouring regions." to
                        "நிடேரியா தொகுதியின் தனிச்சிறப்பான கொட்டும் செல்கள் நிடோசைட்டுகள் (cnidocytes) எனப்படுகின்றன; வளர்ச்சியடையும் நிலையில் அவை நிடோபிளாஸ்டுகள் என்றும் குறிப்பிடப்படுகின்றன. நிடோசைட்டு என்பது முழுமையான சிறப்புச் செல்; அதன் உள்ளே காணப்படும் கொட்டும் உறையே நெமட்டோசிஸ்ட் (nematocyst) ஆகும். நெமட்டோசிஸ்டுக்குள் சுருண்ட இழை உள்ளது. பொருத்தமான தூண்டுதல் ஏற்பட்டால் அந்த இழை விரைவாக வெளியே பாய்ந்து இரையைப் பற்றிக்கொள்ளவோ நச்சுப் பொருளைச் செலுத்தவோ உதவுகிறது. உணர்கொம்புகளில் நிடோசைட்டுகள் அதிகமாக இருப்பதால் சிறிய இரைகளைப் பிடித்துத் தற்காத்துக்கொள்ள முடிகிறது. ஒருமுறை வெளியேற்றப்பட்ட நெமட்டோசிஸ்ட் மீண்டும் அதேபடி செயல்படாது; புதிய கொட்டும் அமைப்புகள் உருவாக வேண்டும். புறத்தோலுக்கு அடியில் பரவியுள்ள நரம்புச் செல்கள் நரம்பு வலையை உருவாக்குகின்றன. தனித்த மூளை இல்லாவிட்டாலும், இவ்வலை தூண்டுதல்களை அண்டைப் பகுதிகளுக்குக் கடத்தி உடலின் பதில்களை ஒருங்கிணைக்கிறது."),
                    btable("Cell types of Obelia", "ஒபீலியாவின் செல் வகைகள்",
                        listOf("Cell" to "செல்", "Layer" to "அடுக்கு", "Function" to "பணி"),
                        listOf(
                            listOf("Epitheliomuscular cell" to "எபிதீலிய-தசை செல்", "Epidermis" to "புறத்தோல்", "Covering, and contraction of the body" to "உறை; உடல் சுருங்குதல்"),
                            listOf("Nutritive-muscular cell" to "ஊட்ட-தசை செல்", "Gastrodermis" to "உள்தோல்", "Engulfing food; flagella stir the contents" to "உணவை விழுங்குதல்; கசைகள் உள்ளடக்கத்தைக் கலக்குதல்"),
                            listOf("Gland cell" to "சுரப்பி செல்", "Gastrodermis" to "உள்தோல்", "Secretes digestive enzymes" to "செரிமான நொதிகளைச் சுரத்தல்"),
                            listOf("Interstitial cell" to "இடைநிலை செல்", "Epidermis" to "புறத்தோல்", "Replaces other cells, and forms germ cells" to "பிற செல்களை மாற்றுதல்; இனச்செல்களை உருவாக்குதல்"),
                            listOf("Cnidoblast" to "நிடோபிளாஸ்ட்", "Epidermis, mainly on tentacles" to "புறத்தோல், முக்கியமாக உணர்கொம்புகளில்", "Capture of prey and defence" to "இரையைப் பிடித்தல், தற்காப்பு"),
                            listOf("Nerve and sensory cell" to "நரம்பு, உணர்வு செல்", "Beneath the epidermis" to "புறத்தோலுக்குக் கீழ்", "Nerve net, and response to stimuli" to "நரம்பு வலை; தூண்டுதலுக்குப் பதில்"),
                        )),
                    bdef("Diploblastic", "ஈரடுக்கு", "Having a body built from two germ layers, the ectoderm and the endoderm." to
                        "புறப்படை, அகப்படை என இரு கருப்படைகளிலிருந்து உருவான உடலுடைய."),
                    bdef("Mesoglea", "மீசோகிளியா", "The jelly-like layer between the epidermis and the gastrodermis of a cnidarian." to
                        "நிடேரியாவின் புறத்தோல், உள்தோலுக்கு இடையிலுள்ள ஜெல் போன்ற அடுக்கு."),
                    bdef("Cnidoblast", "நிடோபிளாஸ்ட்", "A stinging cell of a cnidarian, which contains a nematocyst." to
                        "நெமட்டோசிஸ்ட் கொண்ட நிடேரியாவின் கொட்டும் செல்."),
                    bdef("Nematocyst", "நெமட்டோசிஸ்ட்", "The capsule of a cnidoblast, in which a thread is coiled that is shot out to capture prey." to
                        "இரையைப் பிடிக்கச் சுடப்படும் நூல் சுருண்டுள்ள நிடோபிளாஸ்ட்டின் கூடு."),
                    bdef("Interstitial cell", "இடைநிலை செல் (Interstitial cell)", "An unspecialised cell of a hydroid that can develop into other kinds of cell." to
                        "பிற வகை செல்களாக வளரக்கூடிய ஹைட்ராய்டின் சிறப்புப் பெறாத செல்."),
                    bdef("Nerve net", "நரம்பு வலை", "A diffuse network of nerve cells, without a brain, in the body wall of a cnidarian." to
                        "நிடேரியாவின் உடற்சுவரிலுள்ள, மூளையற்ற, பரந்த நரம்பு செல் வலை."),
                )),
            bsec("ecology", "Growth, regeneration and way of life", "வளர்ச்சி, மீளுருவாக்கம், வாழ்க்கை முறை",
                blocks = listOf(
                    bp("The colony grows by budding. The tip of each branch contains a growing region, from which new zooids arise in turn, and as the colony grows the branches lengthen and become more numerous. The zooids and the branches are formed by outgrowths of the body wall, with the perisarc following them as a covering, and the new parts remain joined to the old ones through the coenosarc. A colony of Obelia is generally of the size of a small shrub, a matter of a few centimetres, and the shape that it takes depends on the place where it grows and on the currents that pass over it. The rate and the extent of growth are not constant, and they depend on the supply of food and on the temperature." to
                        "ஒபீலியா கூட்டுயிரி மொட்டுவிடுதல் மூலம் வளர்கிறது. கிளைகளின் வளர்ச்சி முனைகளிலிருந்து புதிய கிளைகளும் சூயிட்டுகளும் அடுத்தடுத்து தோன்றுகின்றன. உடற்சுவரிலிருந்து உருவாகும் இப்புதிய பகுதிகளைப் பெரிசார்க் என்னும் பாதுகாப்பு உறை சூழ்கிறது; அவை சீனோசார்க் வழியாக ஏற்கெனவே உள்ள பகுதிகளுடன் உயிர்த்திசுத் தொடர்பைப் பேணுகின்றன. வளர்ந்த கூட்டுயிரி பொதுவாகச் சில சென்டிமீட்டர் உயரமுள்ள மென்மையான கிளைத்த அமைப்பாகக் காணப்படும். அதன் வடிவமும் வளர்ச்சி வேகமும் வாழிடத்தின் நீரோட்டம், உணவுக் கிடைப்புத்தன்மை, வெப்பநிலை ஆகியவற்றைப் பொறுத்து மாறுபடுகின்றன."),
                    bp("Obelia is a marine animal, and the colonies are found attached to rocks, shells, wooden piles, seaweeds and other firm objects in shallow water, mainly near the shore and in the zone between the tides. A fixed animal does not seek its food, and it must wait for the food to come to it. The hydranths capture small animals of the plankton, which is the drifting life of the water, with their tentacles, and the colony takes its food in this way. The medusae live in the plankton, and they spread the species, since the colony itself is fixed and cannot move. The planula, also, travels in the plankton before it settles." to
                        "ஒபீலியா ஆழமற்ற கடலோரப் பகுதிகளில் வாழும் கூட்டுயிரி விலங்காகும். அதன் பாலிப் கூட்டுயிரிகள் பாறைகள், சிப்பி ஓடுகள், கடற்பாசிகள், மரத்தூண்கள் போன்ற உறுதியான பரப்புகளில் ஹைட்ரோரைசா மூலம் ஒட்டியிருக்கின்றன. நிலையாக வாழும் கூட்டுயிரியின் ஹைட்ராந்துகள், நீரோட்டத்துடன் வரும் சிறிய மிதவை விலங்குகளை உணர்கொம்புகளாலும் நிடோசைட்டுகளாலும் பிடித்து உண்கின்றன. இதனால் ஒபீலியா கடலோர உணவுவலையில் சிறிய இரைகளை உண்ணும் விலங்காகப் பங்காற்றுகிறது. சுதந்திரமாக நீந்தும் மெடூசா நிலையும் குறுஇழைகளால் நீந்தும் பிளானுலா இளவுயிரி நிலையும் புதிய இடங்களுக்குப் பரவ உதவுகின்றன. கூட்டுயிரியின் பரவலும் வளர்ச்சியும் அடித்தளத்தின் தன்மை, நீரோட்டம் மற்றும் உணவுக் கிடைப்புத்தன்மையைப் பொறுத்து மாறுபடும்."),
                    bp("Hydroids repair themselves, and a colony can regenerate a lost part, since the cells of the body can multiply and replace what is missing, and the interstitial cells have a large share in this. The same quality is the basis of the budding by which the colony grows. The study of regeneration in hydroids, particularly in the freshwater Hydra, has told us much about the way in which a part of an animal can restore the whole, but it should be noted that a given statement applies to the species that was studied and may not apply to Obelia." to
                        "ஹைட்ராய்டுகள் தம்மைத் தாமே சீர்செய்கின்றன; காலனி இழந்த பகுதியை மீளுருவாக்க முடியும்; ஏனெனில் உடலின் செல்கள் பெருகி இல்லாததை மாற்ற முடியும்; இதில் இடைநிலை செல்களுக்குப் பெரும் பங்கு உண்டு. இதே பண்பே காலனி வளரும் அரும்புதலின் அடிப்படை. ஹைட்ராய்டுகளில், குறிப்பாக நன்னீர் ஹைட்ராவில், மீளுருவாக்கம் பற்றிய ஆய்வு விலங்கின் ஒரு பகுதி முழுமையையும் மீட்டெடுக்கும் விதம் பற்றி நிறைய கற்பித்துள்ளது; ஆனால் ஒரு கூற்று ஆய்வு செய்யப்பட்ட இனத்துக்குப் பொருந்தும், ஒபீலியாவுக்குப் பொருந்தாமலிருக்கலாம் என்பதைக் கவனிக்க வேண்டும்."),
                    bdef("Budding", "மொட்டு விடுதல்", "Asexual reproduction or growth by an outgrowth of the body that develops into a new individual or part." to
                        "புதிய உயிரி அல்லது பகுதியாக வளரும் உடல் வளர்ச்சியால் நிகழும் பாலிலா இனப்பெருக்கம் அல்லது வளர்ச்சி."),
                    bdef("Plankton", "மிதவை உயிரிகள்", "The small organisms that drift in the water and are carried by its movements." to
                        "நீரில் மிதந்து அதன் இயக்கங்களால் கொண்டுசெல்லப்படும் சிறு உயிரிகள்."),
                    bdef("Intertidal zone", "அலைகளிடை மண்டலம் (Intertidal zone)", "The shore between the levels of high tide and low tide." to
                        "உயர் அலை, தாழ் அலை மட்டங்களுக்கிடையிலான கரைப் பகுதி."),
                    bdef("Regeneration", "மீளுருவாக்கம்", "The replacement of a lost part of the body by growth." to
                        "இழந்த உடற்பகுதியை வளர்ச்சியால் மாற்றுதல்."),
                )),
            bsec("answering", "Writing the answer", "விடை எழுதுதல்",
                blocks = listOf(
                    bp("The A5 notes make the answer to a detailed account of Obelia a matter of five points. The first is the architecture of the colony, with the perisarc, which is the outer horny covering, distinguished from the coenosarc, which is the living tissue within it. The second is the feeding hydranth, and the third is the gonangium, which buds off the medusae. The fourth is the sequence of the medusa and the planula, and the fifth is the statement that the colony shows polymorphism, in which forms of different function are produced by one genotype. A student who adds that Obelia should not be confused with the floating colony of Physalia will earn credit for showing that the polymorphism of the two is different." to
                        "ஒபீலியாவின் விரிவான உடலமைப்பையும் வாழ்க்கைச் சுழற்சியையும் விளக்கும் விடையைப் பின்வரும் ஐந்து கருத்துகளின் அடிப்படையில் அமைக்கலாம். (1) ஹைட்ரோரைசா, கிளைத்த தண்டு, உயிரற்ற பெரிசார்க், உயிருள்ள சீனோசார்க் ஆகியவற்றுடன் கூட்டுயிரியின் அமைப்பை விளக்குக. (2) உணவைப் பிடிக்கும் ஹைட்ராந்தின் வாய், ஹைப்போஸ்டோம், உணர்கொம்புகள், ஹைட்ரோதீகா ஆகியவற்றை விவரிக்க. (3) இனப்பெருக்கக் கோனாஞ்சியத்தின் கோனோதீகா, பிளாஸ்டோஸ்டைல், மெடூசா மொட்டுகள் ஆகியவற்றைக் குறிப்பிடுக. (4) மெடூசா பாலின இனச்செல்களை உருவாக்குதல், கருவுறுதல், பிளானுலா இளவுயிரி, புதிய பாலிப் கூட்டுயிரி உருவாதல் ஆகிய நிலைகளை வரிசைப்படுத்துக. (5) உணவூட்டும் சூயிட்டுகளுக்கும் இனப்பெருக்கச் சூயிட்டுகளுக்கும் இடையிலான பணிப்பகிர்வை விளக்குக. ஒபீலியா அடித்தளத்தில் ஒட்டியிருக்கும் கூட்டுயிரி; வாயு மிதவை உறையுடன் நீரில் மிதக்கும் பைசாலியாவிலிருந்து இது வேறுபடுகிறது."),
                    bp("The two-mark questions are answered in a line. The coenosarc is the living tissue inside the perisarc that joins the zooids, and the cup around the hydranth is the hydrotheca. The five-mark question on the life history is answered in sequence: the colony produces medusa buds, the medusae are released and produce gametes, fertilisation gives a zygote, the zygote becomes a blastula and then a ciliated planula, the planula settles and forms the first polyp, and budding then produces a new colony. The sequence is the substance of the answer, and a diagram of the cycle drawn as a circle makes it clear at a glance." to
                        "குறுவிடையில் சீனோசார்க் என்பது சூயிட்டுகளை இணைக்கும் உயிருள்ள திசுக்குழாய் என்றும், ஹைட்ரோதீகா என்பது ஹைட்ராந்தைச் சுற்றியுள்ள பெரிசார்க் உறை என்றும் தெளிவாக வரையறுக்க வேண்டும். வாழ்க்கை வரலாறு பற்றிய விரிவான விடையில் நிகழ்வுகளைச் சரியான வரிசையில் எழுதுக: (1) கோனாஞ்சியத்தில் பாலிலா மொட்டுவிடுதல் மூலம் மெடூசாக்கள் உருவாகின்றன. (2) வெளியேறும் முதிர்ந்த மெடூசாக்கள் விந்தணுக்களையும் முட்டைகளையும் உருவாக்குகின்றன. (3) கருவுறுதலுக்குப் பிறகு உருவாகும் இணைக்கரு பிளவடைந்து பிளாஸ்டுலா நிலையை அடைகிறது. (4) குறுஇழைகள் கொண்ட பிளானுலா இளவுயிரி நீந்திச் சென்று உறுதியான அடித்தளத்தில் ஒட்டுகிறது. (5) உருமாற்றத்தால் முதல் பாலிப் உருவாகி, தொடர்ந்து மொட்டுவிடுவதால் புதிய கூட்டுயிரி வளர்கிறது. இந்நிலைகளைத் தெளிவான அம்புக்குறிகளுடன் வாழ்க்கைச் சுழற்சிப் படமாகக் காட்டலாம்."),
                    bdef("Genotype", "மரபணு வகை", "The genetic make-up of an organism, which all the cells of one colony share." to
                        "ஓர் உயிரியின் மரபணு அமைப்பு; ஒரு காலனியின் அனைத்து செல்களும் பகிர்ந்துகொள்வது."),
                )),
            bsec("significance", "Why Obelia is studied", "ஒபீலியா ஏன் கற்கப்படுகிறது",
                blocks = listOf(
                    bp("Obelia shows in a single species most of the ideas of the phylum: the diploblastic body, the gastrovascular cavity, the cnidocytes, the polyp and the medusa, polymorphism and metagenesis. It also shows how a colony may be an individual in some respects and a group in others, since its zooids are separate animals in form but are one in nutrition and in genotype." to
                        "தொகுதியின் பெரும்பாலான கருத்துகளை ஒபீலியா ஒரே இனத்தில் காட்டுகிறது: இரு அடுக்கு உடல், காஸ்ட்ரோவாஸ்குலர் குழி, நிடோசைட்டுகள், பாலிப், மெடுசா, பல்வடிவத்தன்மை, மெட்டாஜெனிசிஸ். காலனி சில வகையில் தனி உயிரியாகவும் சில வகையில் குழுவாகவும் இருக்க முடியும் என்பதையும் காட்டுகிறது; அதன் சூயிட்டுகள் வடிவத்தில் தனி விலங்குகள், ஆனால் ஊட்டத்திலும் மரபணுவகையிலும் ஒன்றே."),
                )),
        ),
        summary = listOf(
            "Obelia is a fixed, branching marine hydroid colony attached by a hydrorhiza." to "ஒபீலியா ஹைட்ரோரைசாவால் ஒட்டிய, கிளைத்த கடல் ஹைட்ராய்டு காலனி.",
            "The stem has a non-living chitinous perisarc and a living coenosarc that encloses a common gastrovascular cavity." to "தண்டில் உயிரற்ற கைட்டின் பெரிசார்க்கும், பொதுவான காஸ்ட்ரோவாஸ்குலர் குழியை அடைக்கும் உயிருள்ள சீனோசார்க்கும் உள்ளன.",
            "Hydranths, in hydrothecae, feed; gonangia, in gonothecae, reproduce asexually by producing medusa buds on the blastostyle." to "ஹைட்ரோதீகாவில் உள்ள ஹைட்ராந்துகள் உண்கின்றன; கோனோதீகாவில் உள்ள கோனாஞ்சியங்கள் பிளாஸ்டோஸ்டைலில் மெடுசா மொட்டுகளை உருவாக்கிப் பாலிலா இனப்பெருக்கம் செய்கின்றன.",
            "The medusa is free-swimming, with four radial canals, a ring canal, a velum and statocysts, and produces the gametes." to "மெடுசா சுதந்திரமாக நீந்துவது; நான்கு ஆரக் கால்வாய்கள், வளையக் கால்வாய், வெலம், ஸ்டேட்டோசிஸ்ட்கள் கொண்டது; கேமீட்டுகளை உருவாக்குகிறது.",
            "Fertilisation gives a zygote that becomes a ciliated planula, which settles and buds into a new colony." to "கருவுறுதல் கருமுட்டையைத் தருகிறது; அது கசையிழை பிளானுலாவாகி, படிந்து, மொட்டுவிட்டுப் புதிய காலனியாகிறது.",
            "Polymorphism is the division of labour among genetically alike zooids; it is not the pneumatophore-bearing colony of Physalia." to "பல்வடிவத்தன்மை மரபுப்படி ஒத்த சூயிட்டுகளிடையேயான பணிப் பிரிவினை; நியூமட்டோஃபோர் கொண்ட பைசாலியா காலனி அல்ல.",
            "The life history is metagenesis, an alternation of an asexual polyp and a sexual medusa." to "வாழ்க்கை வரலாறு மெட்டாஜெனிசிஸ்; பாலிலா பாலிப்பும் பாலின மெடுசாவும் மாறிமாறி வருவது.",
        ),
        checks = listOf(
            bqc("What is coenosarc?", "சீனோசார்க் என்றால் என்ன?",
                "The living tissue inside the perisarc that joins the zooids of the colony.", "காலனியின் சூயிட்டுகளை இணைக்கும், பெரிசார்க்கினுள் உள்ள உயிருள்ள திசு."),
            bqc("Name the cup around a hydranth.", "ஹைட்ராந்தைச் சுற்றியுள்ள கிண்ணத்தின் பெயர் என்ன?",
                "The hydrotheca.", "ஹைட்ரோதீகா."),
            bqc("Which zooid produces the medusa buds?", "மெடுசா மொட்டுகளை உருவாக்கும் சூயிட் எது?",
                "The gonangium, on its blastostyle.", "கோனாஞ்சியம்; அதன் பிளாஸ்டோஸ்டைலில்."),
            bqc("Which stage of Obelia produces the gametes?", "ஒபீலியாவின் எந்த நிலை கேமீட்டுகளை உருவாக்குகிறது?",
                "The medusa.", "மெடுசா."),
            bqc("Name the larva of Obelia.", "ஒபீலியாவின் லார்வாவின் பெயர் என்ன?",
                "The planula.", "பிளானுலா."),
        ),
    )
}
