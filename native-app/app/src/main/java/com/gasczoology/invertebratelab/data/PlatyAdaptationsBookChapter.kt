package com.gasczoology.invertebratelab.data

/** Book-style chapter: parasitic adaptations in Platyhelminthes. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PlatyAdaptationsBookChapter {
    val chapter = bchapter(
        lessonId = "u3-platy-adapt",
        title = "Parasitic adaptations in Platyhelminthes" to "பிளாட்டிஹெல்மிந்தீஸில் ஒட்டுண்ணித் தகவமைப்புகள்",
        objectives = listOf(
            "Define parasitism and state the problems that a parasite must solve." to "ஒட்டுண்ணித்தனத்தை வரையறுத்து, ஒட்டுண்ணி தீர்க்க வேண்டிய சிக்கல்களைக் கூற.",
            "Describe the adaptations of trematodes and cestodes for protection, attachment, nutrition and respiration." to "டிரிமட்டோடாக்கள், செஸ்டோடாக்களின் பாதுகாப்பு, ஒட்டுதல், ஊட்டம், சுவாசத்துக்கான தகவமைப்புகளை விவரிக்க.",
            "Explain the reduction of the gut and sense organs as specialisation." to "குடல், உணர் உறுப்புகளின் குறைப்பைச் சிறப்படைதலாக விளக்க.",
            "Explain how reproductive capacity and the life cycle compensate for the risks of transmission." to "இனப்பெருக்கத் திறனும் வாழ்க்கைச் சுழற்சியும் பரவலின் இடர்களை எவ்வாறு ஈடுசெய்கின்றன என்பதை விளக்க.",
            "Explain the role of the snail in the life cycle of Fasciola." to "ஃபாசியோலாவின் வாழ்க்கைச் சுழற்சியில் நத்தையின் பங்கை விளக்க.",
        ),
        introduction = listOf(
            "A parasite is an animal that lives on or in another living animal, its host, from which it takes its food, and to which it does harm, though usually it does not kill it. The way of life brings very particular difficulties. The parasite must find a host, must get into it, must stay in it in the face of the host's defences, must feed on it, and must send its offspring to a new host. The flatworms that are parasites, the flukes and tapeworms, show how these problems are solved." to
                "ஒட்டுண்ணி என்பது இன்னொரு உயிருள்ள விலங்கான விருந்தோம்பியின் மீது அல்லது உள்ளே வாழ்ந்து, அதிலிருந்து உணவைப் பெற்று, அதற்குத் தீங்கு செய்யும் விலங்கு; பொதுவாக அதைக் கொல்லாது. இவ்வாழ்க்கைமுறை மிகத் தனித்துவ இடர்களைத் தருகிறது. ஒட்டுண்ணி விருந்தோம்பியைக் கண்டறிய வேண்டும், அதனுள் செல்ல வேண்டும், அதன் தற்காப்புகளை எதிர்த்து அதனுள் தங்க வேண்டும், அதிலிருந்து உண்ண வேண்டும், தன் சந்ததிகளைப் புதிய விருந்தோம்பிக்கு அனுப்ப வேண்டும். ஒட்டுண்ணித் தட்டைப்புழுக்களான ஃப்ளூக்குகளும் நாடாப்புழுக்களும் இச்சிக்கல்கள் எவ்வாறு தீர்க்கப்படுகின்றன என்பதைக் காட்டுகின்றன.",
            "This chapter groups the adaptations by the problem that each solves, compares the trematodes and cestodes in a table, and explains the part played by the snail in the life cycle of Fasciola. A recurring point is that the adaptations are specialisations, in which what is not needed is reduced and what is needed is enlarged." to
                "இந்த அத்தியாயம் தகவமைப்புகளை அவை தீர்க்கும் சிக்கலின்படி தொகுக்கிறது; டிரிமட்டோடாக்களையும் செஸ்டோடாக்களையும் அட்டவணையில் ஒப்பிடுகிறது; ஃபாசியோலாவின் வாழ்க்கைச் சுழற்சியில் நத்தையின் பங்கை விளக்குகிறது. தகவமைப்புகள் சிறப்படைதல்கள் என்பது திரும்பத் திரும்ப வரும் கருத்து; தேவையற்றது குறைக்கப்படுகிறது, தேவையானது பெரிதாகிறது.",
        ),
        sections = listOf(
            bsec("parasitism", "Parasitism and its problems", "ஒட்டுண்ணித்தனமும் அதன் சிக்கல்களும்",
                blocks = listOf(
                    bp("Parasitism is one of the relationships between two species in which one, the parasite, lives at the expense of the other, the host. It is common among animals, and in some groups, such as the flukes and tapeworms, every species is a parasite. The host provides the parasite with a home and with food, and also with a series of difficulties, since it is a living animal that has defences against the intruder." to
                        "ஒட்டுண்ணித்தனம் இரு இனங்களுக்கிடையேயான உறவுகளில் ஒன்று; ஒன்று, ஒட்டுண்ணி, மற்றொன்றான விருந்தோம்பியின் இழப்பில் வாழ்கிறது. இது விலங்குகளிடையே பொதுவானது; ஃப்ளூக்குகள், நாடாப்புழுக்கள் போன்ற சில குழுக்களில் ஒவ்வொரு இனமும் ஒட்டுண்ணி. விருந்தோம்பி ஒட்டுண்ணிக்கு வீட்டையும் உணவையும், கூடவே தொடர் சிக்கல்களையும் தருகிறது; ஏனெனில் அது ஊடுருவியவருக்கு எதிரான தற்காப்புகளுடைய உயிருள்ள விலங்கு."),
                    bpoints("The problems of a parasite", "ஒட்டுண்ணியின் சிக்கல்கள்",
                        "Finding and entering a host." to "விருந்தோம்பியைக் கண்டறிந்து நுழைதல்.",
                        "Surviving the host's digestive enzymes and immune defences." to "விருந்தோம்பியின் செரிமான நொதிகள், நோயெதிர்ப்பு தற்காப்புகளிலிருந்து தப்பித்தல்.",
                        "Holding on to the place in the host against movement and flow." to "இயக்கம், ஓட்டத்துக்கு எதிராக விருந்தோம்பியினுள் இடத்தைப் பற்றிக்கொள்ளுதல்.",
                        "Obtaining food and, where oxygen is scarce, energy." to "உணவையும், ஆக்சிஜன் குறைவான இடத்தில் ஆற்றலையும் பெறுதல்.",
                        "Reproducing in numbers great enough to replace the losses of transmission." to "பரவலின் இழப்புகளை ஈடுசெய்யும் எண்ணிக்கையில் இனப்பெருக்கம் செய்தல்.",
                        "Reaching a new host." to "புதிய விருந்தோம்பியை அடைதல்.",
                    ),
                    bdef("Parasite", "ஒட்டுண்ணி", "An organism that lives on or in another organism, the host, from which it obtains food and which it harms." to
                        "உணவைப் பெற்று, தீங்கு செய்யும் வகையில், மற்றொரு உயிரியான விருந்தோம்பியின் மீது அல்லது உள்ளே வாழும் உயிரி."),
                    bdef("Host", "விருந்தோம்பி", "The organism on or in which a parasite lives." to
                        "ஒட்டுண்ணி வாழும் உயிரி."),
                    bdef("Adaptation", "தகவமைப்பு", "A structural, physiological or behavioural feature that fits an organism to its way of life." to
                        "உயிரியை அதன் வாழ்க்கைமுறைக்குப் பொருத்தும் அமைப்பு, உடலியல் அல்லது நடத்தைப் பண்பு."),
                )),
            bsec("tegument", "Protection: the tegument", "பாதுகாப்பு: டெகுமென்ட்",
                blocks = listOf(
                    bp("The first adaptation is the covering of the body. A free-living flatworm has a ciliated epidermis. In flukes and tapeworms this is replaced by a tegument, which is a living syncytial layer that is continuous with cell bodies sunk into the parenchyma below. It is resistant to the digestive enzymes of the host. Its surface can be renewed, and in this way the parasite can alter the proteins on its surface, which assists in avoiding the immune response of the host." to
                        "முதல் தகவமைப்பு உடலின் உறை. தனி வாழ்வுத் தட்டைப்புழுவுக்குக் கசையிழை எப்பிடெர்மிஸ் உண்டு. ஃப்ளூக்குகள், நாடாப்புழுக்களில் இது டெகுமென்ட்டால் மாற்றப்படுகிறது; அது கீழுள்ள பாரங்கைமாவில் மூழ்கிய செல் உடல்களுடன் தொடர்ச்சியான உயிருள்ள சின்சிட்டிய அடுக்கு. இது விருந்தோம்பியின் செரிமான நொதிகளை எதிர்க்கும். அதன் பரப்பு புதுப்பிக்கப்படலாம்; இவ்வாறு ஒட்டுண்ணி தன் பரப்பிலுள்ள புரதங்களை மாற்றி, விருந்தோம்பியின் நோயெதிர்ப்பு எதிர்வினையைத் தவிர்க்க உதவுகிறது."),
                    bp("In cestodes the tegument has a second function, which is the absorption of food. Its surface is raised into minute projections, the microtriches, that increase the area greatly, and the tegument takes up sugars and amino acids from the intestinal contents of the host. In flukes the tegument takes part in absorption to a smaller degree, and the gut remains the main organ of feeding." to
                        "செஸ்டோடாக்களில் டெகுமென்ட்டுக்கு இரண்டாவது பணி உண்டு: உணவை உறிஞ்சுதல். அதன் பரப்பு பரப்பளவை பெரிதும் அதிகரிக்கும் மைக்ரோடிரிக்கி எனும் நுண் நீட்சிகளாக உயர்த்தப்பட்டுள்ளது; டெகுமென்ட் விருந்தோம்பியின் குடல் உள்ளடக்கத்திலிருந்து சர்க்கரைகளையும் அமினோ அமிலங்களையும் உறிஞ்சுகிறது. ஃப்ளூக்குகளில் டெகுமென்ட் உறிஞ்சலில் குறைந்த அளவில் பங்கேற்கிறது; குடலே முதன்மை உண்ணும் உறுப்பாக உள்ளது."),
                    bdef("Syncytium", "சின்சிட்டியம்", "A layer of cytoplasm that has many nuclei and is not divided into separate cells." to
                        "பல உட்கருக்கள் கொண்டு தனிச் செல்களாகப் பிரிக்கப்படாத சைட்டோபிளாசத்தின் அடுக்கு."),
                )),
            bsec("attachment", "Attachment organs", "ஒட்டும் உறுப்புகள்",
                blocks = listOf(
                    bp("A parasite in the intestine or the bile duct is exposed to the movement of food and fluid and to the contractions of the wall of the organ, and unless it holds on it will be swept out. Flukes have two muscular suckers, an oral and a ventral one, by which they fix themselves to the lining, and the tegument carries backwardly directed spines. Tapeworms have a scolex, which in the pork tapeworm bears four suckers and a crown of hooks. In some species the hooks are replaced by other structures, such as grooves, and the form of the scolex is therefore used in the identification of species." to
                        "குடலிலோ பித்த நாளத்திலோ உள்ள ஒட்டுண்ணி உணவு, திரவத்தின் இயக்கத்துக்கும் உறுப்புச் சுவரின் சுருக்கங்களுக்கும் வெளிப்படுகிறது; பற்றிக்கொள்ளாவிட்டால் அடித்துச் செல்லப்படும். ஃப்ளூக்குகளுக்கு வாய், வயிற்று உறிஞ்சிகள் என இரு தசை உறிஞ்சிகள் உள்ளன; அவற்றால் உள்ளடுக்கில் தம்மை நிலைநிறுத்துகின்றன; டெகுமென்ட்டில் பின்னோக்கிய முட்கள் உள்ளன. நாடாப்புழுக்களுக்கு ஸ்கொலெக்ஸ் உள்ளது; பன்றி நாடாப்புழுவில் அது நான்கு உறிஞ்சிகளையும் கொக்கிகளின் மகுடத்தையும் தாங்குகிறது. சில இனங்களில் கொக்கிகள் பள்ளங்கள் போன்ற பிற அமைப்புகளால் மாற்றப்படுகின்றன; எனவே இனங்களை அடையாளம் காண ஸ்கொலெக்ஸின் வடிவம் பயன்படுகிறது."),
                    bdef("Rostellum", "ரோஸ்டெல்லம்", "A projection at the top of the scolex of some tapeworms, which carries the hooks." to
                        "சில நாடாப்புழுக்களின் ஸ்கொலெக்ஸ் உச்சியிலுள்ள, கொக்கிகளைத் தாங்கும் நீட்சி."),
                )),
            bsec("nutrition", "Nutrition, respiration and the reduction of organs", "ஊட்டம், சுவாசம், உறுப்புக் குறைப்பு",
                blocks = listOf(
                    bp("The parasite lives in an environment where food is abundant. Flukes retain a gut, but it is simple, and it takes in the bile, blood and tissue of the host. Tapeworms have lost the gut altogether and absorb the digested food of the host across the tegument. The loss can be seen as an economy, since a parasite that does not have to find, grasp and digest food can devote the energy saved to attachment and reproduction." to
                        "ஒட்டுண்ணி உணவு மிகுந்த சூழலில் வாழ்கிறது. ஃப்ளூக்குகள் குடலை வைத்துள்ளன, ஆனால் எளிமையானது; விருந்தோம்பியின் பித்தம், இரத்தம், திசுவை உட்கொள்கிறது. நாடாப்புழுக்கள் குடலை முற்றிலும் இழந்து, விருந்தோம்பியின் செரித்த உணவை டெகுமென்ட் வழியாக உறிஞ்சுகின்றன. இழப்பைச் சிக்கனமாகக் காணலாம்; உணவைத் தேடி, பிடித்து, செரிக்க வேண்டியதில்லாத ஒட்டுண்ணி மிச்சமான ஆற்றலை ஒட்டுதல், இனப்பெருக்கத்துக்குச் செலுத்த முடியும்."),
                    bp("The environment in the gut and bile duct contains little free oxygen, and the parasites there are mainly anaerobic, obtaining energy by the breakdown of glycogen without oxygen. They store large quantities of glycogen for the purpose. Respiratory organs are absent. The sense organs are also reduced in the adult, because the host provides a dark and uniform surrounding in which eyes would serve no purpose. The sensory structures that remain are those of touch and of chemical sensitivity, and are concentrated in the suckers and in the scolex." to
                        "குடல், பித்த நாளச் சூழலில் தனி ஆக்சிஜன் குறைவு; அங்குள்ள ஒட்டுண்ணிகள் முக்கியமாக காற்றில்லாச் சுவாசம் உடையவை; ஆக்சிஜன் இன்றி கிளைக்கோஜனைப் பிரித்து ஆற்றலைப் பெறுகின்றன. இதற்காக அதிக அளவு கிளைக்கோஜனைச் சேமிக்கின்றன. சுவாச உறுப்புகள் இல்லை. முதிர்ந்த புழுவில் உணர் உறுப்புகளும் குறைந்துள்ளன; ஏனெனில் விருந்தோம்பி கண்கள் பயனற்ற இருட்டான, ஒரே மாதிரியான சூழலைத் தருகிறது. எஞ்சிய உணர் அமைப்புகள் தொடுதல், வேதிப்பொருள் உணர்வுக்கானவை; அவை உறிஞ்சிகளிலும் ஸ்கொலெக்ஸிலும் குவிந்துள்ளன."),
                    bnote("Specialisation, not degeneration", "சிறப்படைதல்; சீரழிவு அல்ல",
                        "The reduced organs should be described as a specialisation to the parasitic way of life. Words such as degeneration suggest a loss of fitness, whereas these animals are very successful in the niche that they occupy." to
                            "குறைந்த உறுப்புகளை ஒட்டுண்ணி வாழ்க்கைமுறைக்கான சிறப்படைதலாக விவரிக்க வேண்டும். சீரழிவு போன்ற சொற்கள் தகுதி இழப்பைக் குறிக்கின்றன; ஆனால் இவ்விலங்குகள் தாம் வகிக்கும் சூழல் இடத்தில் மிகவும் வெற்றிகரமானவை."),
                    bdef("Anaerobic respiration", "காற்றில்லாச் சுவாசம்", "The release of energy from food without the use of free oxygen." to
                        "கட்டற்ற ஆக்சிஜனைப் பயன்படுத்தாமல் உணவிலிருந்து ஆற்றலை வெளியிடுதல்."),
                )),
            bsec("reproduction", "Reproduction and transmission", "இனப்பெருக்கமும் பரவலும்",
                blocks = listOf(
                    bp("The greatest risk to a parasite is that of failing to reach a new host, since the chances that any one egg will do so are small. The flatworm parasites meet this risk in two ways. The first is that the reproductive system is large and is developed at the expense of other organs. The gonads, the ducts and the glands fill much of the body, each worm is a hermaphrodite and so can reproduce even if alone, and a tapeworm may produce a proglottid full of eggs every day." to
                        "ஒட்டுண்ணியின் மிகப் பெரிய இடர் புதிய விருந்தோம்பியை அடையத் தவறுவது; ஒரு முட்டை அடைவதற்கான வாய்ப்பு சிறியது. ஒட்டுண்ணித் தட்டைப்புழுக்கள் இவ்விடரை இரு வழிகளில் சந்திக்கின்றன. முதலாவது இனப்பெருக்க மண்டலம் பெரிதாகி மற்ற உறுப்புகளின் இழப்பில் வளர்ந்துள்ளது. சுரப்பிகள், நாளங்கள், துணைச் சுரப்பிகள் உடலில் பெரும்பகுதியை நிரப்புகின்றன; ஒவ்வொரு புழுவும் இருபாலுயிரி என்பதால் தனியாக இருந்தாலும் இனப்பெருக்கம் செய்ய முடியும்; ஒரு நாடாப்புழு தினமும் முட்டைகள் நிரம்பிய ஒரு புரோகிளாட்டிட்டை உருவாக்கக்கூடும்."),
                    bp("The second way is the life cycle. The eggs are protected by shells and can survive outside the host, and the cycle includes larval stages that are each suited to a particular step. The most important of these is the multiplication of larvae in the intermediate host. In a fluke a single miracidium that enters a snail gives rise through the sporocyst and redia stages to a very large number of cercariae, and this asexual multiplication in the snail, sometimes called polyembryony, increases the number of infective stages enormously for the cost of one successful infection." to
                        "இரண்டாவது வழி வாழ்க்கைச் சுழற்சி. முட்டைகள் ஓடுகளால் பாதுகாக்கப்பட்டு விருந்தோம்பிக்கு வெளியே வாழக்கூடியவை; சுழற்சியில் ஒவ்வொன்றும் குறிப்பிட்ட படிக்கு ஏற்ற லார்வா நிலைகள் உள்ளன. இவற்றில் மிக முக்கியமானது இடைநிலை விருந்தோம்பியில் லார்வாக்கள் பெருகுவது. ஃப்ளூக்கில் நத்தைக்குள் நுழையும் ஒரு மிராசிடியம் ஸ்போரோசிஸ்ட், ரெடியா நிலைகள் வழியாக மிகப் பெரிய எண்ணிக்கையிலான செர்க்கேரியாக்களைத் தருகிறது; நத்தையிலுள்ள இந்த பாலிலா பெருக்கம், சில நேரங்களில் பல கருநிலை எனப்படுவது, ஒரு வெற்றிகரமான தொற்றின் விலைக்கு தொற்றும் நிலைகளின் எண்ணிக்கையை மிகப் பெரிதாக உயர்த்துகிறது."),
                    bdef("Polyembryony", "பல கருநிலை", "The production of many embryos or larvae from a single fertilised egg by asexual multiplication, as in the snail stages of a fluke." to
                        "ஃப்ளூக்கின் நத்தை நிலைகளில் போல, ஒரே கருவுற்ற முட்டையிலிருந்து பாலிலா பெருக்கத்தால் பல கருக்கள் அல்லது லார்வாக்களை உருவாக்குதல்."),
                    bdef("Operculum", "ஆபர்குலம்", "The lid of the egg of a fluke through which the miracidium escapes." to
                        "மிராசிடியம் வெளியேறும் ஃப்ளூக் முட்டையின் மூடி."),
                )),
            bsec("snail", "The snail in the life cycle of Fasciola", "ஃபாசியோலாவின் வாழ்க்கைச் சுழற்சியில் நத்தை",
                blocks = listOf(
                    bp("The freshwater snail is the obligatory intermediate host of Fasciola hepatica, which means that the cycle cannot be completed without it. The miracidium that hatches from the egg has a short life in the water and must find a snail within a few hours. It penetrates the snail and changes into a sporocyst, the sporocyst gives rise to rediae, and the rediae produce cercariae. The cercariae leave the snail, swim briefly and encyst on plants or other surfaces as metacercariae, which are infective to the mammal." to
                        "நன்னீர் நத்தை ஃபாசியோலா ஹெப்பாட்டிகாவின் கட்டாய இடைநிலை விருந்தோம்பி; அதாவது அது இன்றி சுழற்சியை முடிக்க முடியாது. முட்டையிலிருந்து பொரிந்த மிராசிடியத்துக்கு நீரில் குறுகிய ஆயுள் உண்டு; சில மணி நேரங்களுக்குள் நத்தையைக் கண்டறிய வேண்டும். அது நத்தையில் ஊடுருவி ஸ்போரோசிஸ்டாக மாறுகிறது; ஸ்போரோசிஸ்ட் ரெடியாக்களைத் தருகிறது; ரெடியாக்கள் செர்க்கேரியாக்களை உருவாக்குகின்றன. செர்க்கேரியாக்கள் நத்தையை விட்டு வெளியேறி, சிறிது நீந்தி, தாவரங்கள் அல்லது பிற பரப்புகளில் பாலூட்டிக்குத் தொற்றும் மெட்டாசெர்க்கேரியாக்களாக உறைகின்றன."),
                    bp("Since the snail's ecology governs the survival of the larvae, it controls the spread of the disease. Snails live in wet places and the disease is therefore a problem of wet pastures and of damp seasons. Control combines treatment of infected animals, safe food and water practices for people, and management of snail habitats where it is suitable. In an answer, the snail should be described as the site of multiplication, and not merely as a carrier, since it is in the snail that a single miracidium becomes hundreds of cercariae, and the snail is therefore the key to both the biology and the control of the disease." to
                        "நத்தையின் சூழலியல் லார்வாக்களின் உயிர்வாழ்வை நிர்ணயிப்பதால், நோய் பரவலை அது கட்டுப்படுத்துகிறது. நத்தைகள் ஈரமான இடங்களில் வாழ்கின்றன; எனவே நோய் ஈர மேய்ச்சல் நிலங்கள், ஈரமான பருவங்களின் சிக்கல். கட்டுப்பாடு தொற்றுற்ற விலங்குகளுக்குச் சிகிச்சை, மனிதர்களுக்குப் பாதுகாப்பான உணவு, நீர் நடைமுறைகள், பொருத்தமான இடத்தில் நத்தை வாழிடங்களின் மேலாண்மை ஆகியவற்றை இணைக்கிறது."),
                )),
            bsec("balance", "The balance between parasite and host", "ஒட்டுண்ணிக்கும் விருந்தோம்பிக்கும் இடையிலான சமநிலை",
                blocks = listOf(
                    bp("The adaptations of a parasite are not only those which allow it to live in a host. They are also those which let it live in a host without killing it, since the death of the host before the parasite has reproduced would be a disaster for the parasite. A well-adapted parasite therefore takes little from each host and is slow to cause harm, and the dramatic diseases, such as the acute stage of liver rot, are as a rule found where a parasite is new to a host, or where the numbers of parasites are very large." to
                        "ஒட்டுண்ணியின் தகவமைப்புகள் விருந்தோம்பியில் வாழ உதவுபவை மட்டுமல்ல. அதைக் கொல்லாமல் வாழ உதவுபவையும் ஆகும்; ஒட்டுண்ணி இனப்பெருக்கம் செய்யும் முன் விருந்தோம்பி இறப்பது ஒட்டுண்ணிக்கு பேரழிவு. எனவே நன்கு தகவமைந்த ஒட்டுண்ணி ஒவ்வொரு விருந்தோம்பியிடமிருந்தும் சிறிதே எடுத்து, தீங்கை மெதுவாக ஏற்படுத்துகிறது; கல்லீரல் அழுகலின் கடுமையான நிலை போன்ற நாடகீய நோய்கள் பொதுவாக ஒட்டுண்ணி விருந்தோம்பிக்குப் புதிதாக இருக்கும் இடத்தில் அல்லது ஒட்டுண்ணிகளின் எண்ணிக்கை மிகப் பெரிதாக இருக்கும் இடத்தில் காணப்படுகின்றன."),
                    bp("The host in turn responds with defences of its own: a thickening of the lining of the duct, inflammation, and immune reactions directed against the surface of the parasite. The parasite answers by the properties of its tegument, which has been described, and by the release of substances which damp the reaction. The relationship is thus a long evolutionary exchange, and a parasite and its host are each the product of the other." to
                        "விருந்தோம்பி தன் சொந்த தற்காப்புகளுடன் பதிலளிக்கிறது: நாளத்தின் உள்ளடுக்குத் தடிப்பு, வீக்கம், ஒட்டுண்ணியின் பரப்புக்கு எதிரான நோயெதிர்ப்பு வினைகள். ஒட்டுண்ணி விவரிக்கப்பட்ட டெகுமென்ட்டின் பண்புகளாலும், எதிர்வினையை மட்டுப்படுத்தும் பொருட்களை வெளியிடுவதாலும் பதிலளிக்கிறது. எனவே உறவு நீண்ட பரிணாமப் பரிமாற்றம்; ஒட்டுண்ணியும் விருந்தோம்பியும் ஒவ்வொன்றும் மற்றதன் விளைவு."),
                    bdef("Immune response", "நோயெதிர்ப்பு எதிர்வினை", "The reaction of the body of a host that is directed against foreign organisms and substances." to
                        "அயல் உயிரிகள், பொருட்களுக்கு எதிராக விருந்தோம்பியின் உடல் காட்டும் எதிர்வினை."),
                    bdef("Obligatory intermediate host", "கட்டாய இடைநிலை விருந்தோம்பி", "An intermediate host that the parasite cannot do without, because it cannot complete its life cycle in any other way." to
                        "வாழ்க்கைச் சுழற்சியை வேறு வழியில் முடிக்க இயலாததால், ஒட்டுண்ணி தவிர்க்க முடியாத இடைநிலை விருந்தோம்பி."),
                )),
            bsec("comparison", "Trematodes and cestodes compared", "டிரிமட்டோடாக்களும் செஸ்டோடாக்களும் ஒப்பீடு",
                blocks = listOf(
                    btable("Adaptations of trematodes and cestodes", "டிரிமட்டோடாக்கள், செஸ்டோடாக்களின் தகவமைப்புகள்",
                        listOf("Problem" to "சிக்கல்", "Trematode (Fasciola)" to "டிரிமட்டோடா (ஃபாசியோலா)", "Cestode (Taenia)" to "செஸ்டோடா (டீனியா)"),
                        listOf(
                            listOf("Protection" to "பாதுகாப்பு", "Spiny tegument" to "முள் டெகுமென்ட்", "Tegument with microtriches" to "மைக்ரோடிரிக்கியுடன் டெகுமென்ட்"),
                            listOf("Attachment" to "ஒட்டுதல்", "Oral and ventral suckers" to "வாய், வயிற்று உறிஞ்சிகள்", "Scolex with suckers and hooks" to "உறிஞ்சிகளும் கொக்கிகளும் கொண்ட ஸ்கொலெக்ஸ்"),
                            listOf("Nutrition" to "ஊட்டம்", "Simple branched gut" to "எளிய கிளைத்த குடல்", "No gut; absorption across tegument" to "குடல் இல்லை; டெகுமென்ட் வழியாக உறிஞ்சல்"),
                            listOf("Respiration" to "சுவாசம்", "Largely anaerobic" to "பெரும்பாலும் காற்றில்லா", "Anaerobic" to "காற்றில்லா"),
                            listOf("Sense organs" to "உணர் உறுப்புகள்", "Reduced in the adult" to "முதிர்ந்ததில் குறைந்தவை", "Greatly reduced" to "மிகவும் குறைந்தவை"),
                            listOf("Reproduction" to "இனப்பெருக்கம்", "Hermaphrodite, many eggs" to "இருபாலுயிரி, பல முட்டைகள்", "Hermaphrodite proglottids, many eggs" to "இருபாலுயிரி புரோகிளாட்டிட்கள், பல முட்டைகள்"),
                            listOf("Amplification" to "பெருக்கம்", "Asexual multiplication in the snail" to "நத்தையில் பாலிலா பெருக்கம்", "Many proglottids; larval multiplication in some" to "பல புரோகிளாட்டிட்கள்; சிலவற்றில் லார்வாப் பெருக்கம்"),
                        )),
                )),
            bsec("answering", "Answering questions on parasitic adaptation", "ஒட்டுண்ணித் தகவமைப்பு குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("An essay on this topic is best arranged by the problems of the parasite, as in this chapter: protection, attachment, nutrition, respiration, reproduction and transmission. Each adaptation should be joined to the problem that it meets, so that the answer reads as a reasoned account and not as a list. Examples should be named, Fasciola for the flukes and Taenia for the tapeworms, and the word specialisation should be used in place of degeneration." to
                        "இத்தலைப்பின் கட்டுரையை இவ்வத்தியாயத்தில் உள்ளவாறு ஒட்டுண்ணியின் சிக்கல்களின்படி அமைப்பது சிறந்தது: பாதுகாப்பு, ஒட்டுதல், ஊட்டம், சுவாசம், இனப்பெருக்கம், பரவல். ஒவ்வொரு தகவமைப்பும் அது சந்திக்கும் சிக்கலுடன் இணைக்கப்பட வேண்டும்; அப்போது விடை பட்டியலாக அல்லாமல் தர்க்கரீதியான விளக்கமாக அமையும். எடுத்துக்காட்டுகள் பெயரிடப்பட வேண்டும்: ஃப்ளூக்குகளுக்கு ஃபாசியோலா, நாடாப்புழுக்களுக்கு டீனியா; சீரழிவு என்பதற்குப் பதில் சிறப்படைதல் என்ற சொல்லைப் பயன்படுத்த வேண்டும்."),
                    bpoints("Points that earn marks", "மதிப்பெண் தரும் குறிப்புகள்",
                        "A resistant, physiologically active tegument." to "எதிர்ப்புத்திறன் உள்ள, உடலியல் செயலுள்ள டெகுமென்ட்.",
                        "Suckers and hooks for attachment." to "ஒட்டுவதற்கான உறிஞ்சிகளும் கொக்கிகளும்.",
                        "Reduction of the gut and sense organs, described as specialisation." to "சிறப்படைதலாக விவரிக்கப்படும் குடல், உணர் உறுப்புகளின் குறைப்பு.",
                        "Large reproductive output and resistant eggs." to "பெரிய இனப்பெருக்க விளைவும் எதிர்ப்புத்திறன் உள்ள முட்டைகளும்.",
                        "Complex life cycle with intermediate host and asexual multiplication in the snail." to "இடைநிலை விருந்தோம்பியும் நத்தையில் பாலிலா பெருக்கமும் கொண்ட சிக்கலான வாழ்க்கைச் சுழற்சி.",
                    ),
                )),
        ),
        summary = listOf(
            "A parasite must find, enter and stay in a host, feed on it, and send its offspring to a new host." to "ஒட்டுண்ணி விருந்தோம்பியைக் கண்டறிந்து, நுழைந்து, தங்கி, உண்டு, சந்ததிகளைப் புதிய விருந்தோம்பிக்கு அனுப்ப வேண்டும்.",
            "The tegument resists the host's enzymes and immune system and, in cestodes, absorbs food." to "டெகுமென்ட் விருந்தோம்பியின் நொதிகள், நோயெதிர்ப்பு மண்டலத்தை எதிர்த்து, செஸ்டோடாக்களில் உணவை உறிஞ்சுகிறது.",
            "Suckers, spines, hooks and the scolex are for attachment." to "உறிஞ்சிகள், முட்கள், கொக்கிகள், ஸ்கொலெக்ஸ் ஒட்டுவதற்கானவை.",
            "The gut is simple in flukes and absent in tapeworms, and respiration is largely anaerobic." to "ஃப்ளூக்குகளில் குடல் எளிது, நாடாப்புழுக்களில் இல்லை; சுவாசம் பெரும்பாலும் காற்றில்லா.",
            "Reproductive capacity is very high, and eggs are protected by shells." to "இனப்பெருக்கத் திறன் மிக உயர்வு; முட்டைகள் ஓடுகளால் பாதுகாக்கப்படுகின்றன.",
            "The snail is the obligatory intermediate host of Fasciola and the site of asexual amplification through sporocyst and redia to cercariae." to "நத்தை ஃபாசியோலாவின் கட்டாய இடைநிலை விருந்தோம்பி; ஸ்போரோசிஸ்ட், ரெடியா வழியாக செர்க்கேரியாக்களுக்கான பாலிலா பெருக்கத்தின் இடம்.",
            "The reduced organs are specialisations, and not signs of degeneration." to "குறைந்த உறுப்புகள் சிறப்படைதல்கள்; சீரழிவின் அறிகுறிகள் அல்ல.",
        ),
        checks = listOf(
            bqc("Name two adaptations for attachment in flukes and tapeworms.", "ஃப்ளூக்குகள், நாடாப்புழுக்களின் ஒட்டுதலுக்கான இரு தகவமைப்புகளைக் குறிப்பிடுக.",
                "Suckers in flukes, and a scolex with suckers and hooks in tapeworms.", "ஃப்ளூக்குகளில் உறிஞ்சிகள்; நாடாப்புழுக்களில் உறிஞ்சிகளும் கொக்கிகளும் கொண்ட ஸ்கொலெக்ஸ்."),
            bqc("Why is the reproductive output of parasites high?", "ஒட்டுண்ணிகளின் இனப்பெருக்க விளைவு ஏன் உயர்வு?",
                "The chance that any one egg reaches a new host is small, so large numbers compensate for the loss.", "ஒரு முட்டை புதிய விருந்தோம்பியை அடையும் வாய்ப்பு சிறியது; பெரிய எண்ணிக்கை இழப்பை ஈடுசெய்கிறது."),
            bqc("What is polyembryony in the life cycle of a fluke?", "ஃப்ளூக்கின் சுழற்சியில் பல கருநிலை என்றால் என்ன?",
                "The asexual multiplication of one miracidium into many cercariae through sporocyst and redia in the snail.", "நத்தையில் ஸ்போரோசிஸ்ட், ரெடியா வழியாக ஒரு மிராசிடியம் பல செர்க்கேரியாக்களாகப் பாலிலாவாகப் பெருகுதல்."),
            bqc("Why is it better to say specialisation than degeneration?", "சீரழிவைவிட சிறப்படைதல் என்பது ஏன் சிறந்தது?",
                "The reduced organs fit the parasite to its niche and the animals are very successful; degeneration wrongly implies lost fitness.", "குறைந்த உறுப்புகள் ஒட்டுண்ணியை அதன் சூழல் இடத்துக்குப் பொருத்துகின்றன; விலங்குகள் மிக வெற்றிகரமானவை; சீரழிவு தகுதி இழப்பைத் தவறாகக் குறிக்கிறது."),
        ),
    )
}
