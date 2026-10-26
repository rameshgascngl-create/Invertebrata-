package com.gasczoology.invertebratelab.data

/** Book-style chapter: corals and coral reefs. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object CoralsBookChapter {
    val chapter = bchapter(
        lessonId = "u2-corals",
        title = "Corals and coral reefs" to "பவளங்களும் பவளப்பாறைகளும்",
        objectives = listOf(
            "Describe the structure of a coral polyp and the way in which it builds its skeleton." to "பவள பாலிப்பின் அமைப்பையும், அது தன் எலும்புக்கூட்டைக் கட்டும் விதத்தையும் விவரிக்க.",
            "Explain the association between a reef-building coral and its zooxanthellae." to "பவளப்பாறை உருவாக்கும் பவளத்துக்கும் அதன் சூக்சாந்தெல்லேக்கும் இடையிலான தொடர்பை விளக்க.",
            "Name and distinguish the fringing reef, the barrier reef and the atoll." to "ஓர முகடு, தடுப்பு முகடு, வளைய முகடு ஆகியவற்றைப் பெயரிட்டு வேறுபடுத்த.",
            "State the conditions in which reef-building corals flourish and explain why." to "பவளப்பாறை உருவாக்கும் பவளங்கள் செழிக்கும் நிலைகளைக் கூறி, காரணத்தை விளக்க.",
            "Explain coral bleaching, its causes and its consequences." to "பவள வெளிறலையும் அதன் காரணங்கள், விளைவுகளையும் விளக்க.",
        ),
        introduction = listOf(
            "Corals are anthozoans, and so they exist only as polyps. A single coral polyp is small, but corals live in great numbers, and over long periods the skeletons that they leave build up into structures large enough to be seen from space. A coral reef is the largest structure built by living things, and it shelters a greater variety of animals than any other marine environment of shallow water." to
                "பவளங்கள் ஆந்தோசோவாக்கள்; எனவே பாலிப்பாக மட்டுமே உள்ளன. ஒரு பவள பாலிப் சிறியது; ஆனால் பவளங்கள் பெருந்தொகையில் வாழ்கின்றன; நீண்ட காலத்தில் அவை விட்டுச் செல்லும் எலும்புக்கூடுகள் விண்வெளியிலிருந்து காணக்கூடிய அளவுக்குப் பெரிய அமைப்புகளாகத் திரள்கின்றன. பவளப்பாறை உயிரிகள் கட்டும் மிகப்பெரிய அமைப்பு; ஆழமற்ற நீரின் வேறு எந்தக் கடல் சூழலைவிடவும் அதிக வகை விலங்குகளுக்கு அடைக்கலம் தருகிறது.",
            "This chapter treats the coral polyp and its skeleton, the partnership with algae that makes reef building possible, the three kinds of reef, the conditions that a reef requires, and the danger of bleaching. It is important to remember throughout that a reef is an ecosystem, in which the coral is one organism among many, and is not itself a single animal." to
                "இந்த அத்தியாயம் பவள பாலிப்பையும் அதன் எலும்புக்கூட்டையும், பாறை உருவாக்கத்தை சாத்தியமாக்கும் பாசிக் கூட்டுறவையும், மூன்று வகை முகடுகளையும், முகடுக்குத் தேவையான நிலைகளையும், வெளிறல் ஆபத்தையும் கையாளுகிறது. முகடு ஒரு சூழல்மண்டலம்; பவளம் அதிலுள்ள பல உயிரிகளில் ஒன்று; முகடே ஒற்றை விலங்கு அல்ல என்பதை முழுவதும் நினைவில் கொள்ள வேண்டும்.",
        ),
        sections = listOf(
            bsec("polyp", "The coral polyp", "பவள பாலிப்",
                blocks = listOf(
                    bp("A coral polyp is a small cylinder with a mouth surrounded by tentacles, closely resembling a sea anemone, to which it is related. Its tentacles, which carry nematocysts, number six or eight, or multiples of six, and catch small animals of the plankton, mainly at night. A short pharynx leads from the mouth into the gastrovascular cavity, which is divided by vertical partitions, the mesenteries. In a colonial coral the cavities of neighbouring polyps are joined by a thin sheet of living tissue, which covers the surface of the colony." to
                        "பவள பாலிப் சிறிய உருளை; வாயைச் சுற்றி உணர்கொம்புகள் உள்ளன; தொடர்புடைய கடல் அனிமோனை நெருக்கமாகப் போல் தோன்றுகிறது. நெமட்டோசிஸ்ட்களைத் தாங்கும் உணர்கொம்புகள் ஆறு அல்லது எட்டு, அல்லது ஆறின் மடங்குகள்; பிளாங்க்டனின் சிறு விலங்குகளை, பெரும்பாலும் இரவில், பிடிக்கின்றன. குறுகிய தொண்டை வாயிலிருந்து மெசென்டரிகள் எனும் நிமிர்ந்த பிரிவுகளால் பிரிக்கப்பட்ட காஸ்ட்ரோவாஸ்குலர் குழிக்குச் செல்கிறது. காலனி பவளத்தில் அருகிலுள்ள பாலிப்களின் குழிகள் காலனியின் பரப்பை மூடும் உயிருள்ள திசுவின் மெல்லிய படலத்தால் இணைக்கப்பட்டுள்ளன."),
                    bfig("lib/coral-polyp",
                        "A stony-coral polyp in its skeletal cup, in section (schematic): (1) tentacle, (2) mouth on a short pharynx, (3) body wall, (4) gastrovascular cavity, (5) skeletal cup (calyx), (6) septum, a skeletal ridge, (7) zooxanthellae in the tissue.",
                        "கல் பவளத்தின் பாலிப், எலும்புக்கூட்டுக் கிண்ணத்துக்குள், வெட்டுத்தோற்றம் (படத்திட்டம்): (1) உணர்நீட்சி, (2) குறுகிய தொண்டையின் மேல் வாய், (3) உடற்சுவர், (4) இரைப்பை-நாளக் குழி, (5) எலும்புக்கூட்டுக் கிண்ணம் (கேலிக்ஸ்), (6) செப்டம், எலும்புக்கூட்டு முகடு, (7) திசுவிலுள்ள சூக்சாந்தெல்லா."),
                    bdef("Mesentery", "மெசென்டரி (Mesentery)",
                        "One of the vertical partitions that divide the gastrovascular cavity of an anthozoan polyp." to
                            "ஆந்தோசோவா பாலிப்பின் காஸ்ட்ரோவாஸ்குலர் குழியைப் பிரிக்கும் நிமிர்ந்த பிரிவுகளில் ஒன்று."),
                    bdef("Coral", "பவளம்", "An anthozoan polyp, solitary or colonial, that secretes a skeleton of calcium carbonate." to
                        "கால்சியம் கார்பனேட் எலும்புக்கூட்டைச் சுரக்கும் தனித்த அல்லது காலனி ஆந்தோசோவா பாலிப்."),
                )),
            bsec("skeleton", "The skeleton", "எலும்புக்கூடு",
                blocks = listOf(
                    bp("The stony corals, or madreporarians, secrete a skeleton of calcium carbonate from the epidermis at the base and sides of the polyp. The polyp sits in a cup, the calyx, formed in this way, and the floor and walls of the cup are strengthened by radiating plates, the septa. When the polyp is disturbed it withdraws into the cup. As the colony grows, new polyps are budded off and build their own cups on the skeletons of the old, and the dead skeleton below accumulates as the living layer grows above it." to
                        "கல் பவளங்கள் அல்லது மேட்ரபோரேரியர்கள், பாலிப்பின் அடியிலும் பக்கங்களிலும் உள்ள எப்பிடெர்மிஸிலிருந்து கால்சியம் கார்பனேட் எலும்புக்கூட்டைச் சுரக்கின்றன. பாலிப் இவ்வாறு உருவான கிண்ணத்தில் (கேலிக்ஸ்) அமர்கிறது; கிண்ணத்தின் தளமும் சுவர்களும் ஆரமாகப் பரவும் தகடுகளான செப்டாவால் வலுப்படுகின்றன. தொந்தரவு செய்யப்படும்போது பாலிப் கிண்ணத்துக்குள் பின்வாங்குகிறது. காலனி வளரும்போது புதிய பாலிப்புகள் மொட்டுவிட்டு பழையவற்றின் எலும்புக்கூடுகளின் மேல் தங்கள் கிண்ணங்களைக் கட்டுகின்றன; மேலே உயிருள்ள அடுக்கு வளர, கீழே இறந்த எலும்புக்கூடு திரள்கிறது."),
                    bp("The skeleton is thus a record of the colony's past growth, and the dead part forms the great mass of a reef. It is built slowly, at rates which for many corals are a few millimetres to a few centimetres in a year, and it is broken down by waves, by boring and grazing animals and by the dissolving action of the sea. A reef exists where the building outpaces the breakdown." to
                        "எனவே எலும்புக்கூடு காலனியின் கடந்தகால வளர்ச்சியின் பதிவு; இறந்த பகுதி முகட்டின் பெரும் திணிவாகிறது. இது மெதுவாகக் கட்டப்படுகிறது; பல பவளங்களுக்கு ஆண்டுக்கு சில மில்லிமீட்டர் முதல் சில சென்டிமீட்டர் வரை; அலைகள், துளைக்கும், மேயும் விலங்குகள், கடலின் கரைக்கும் செயல் ஆகியவற்றால் உடைக்கப்படுகிறது. கட்டுதல் உடைவை மிஞ்சும் இடத்தில் முகடு நிலைக்கிறது."),
                    bdef("Calyx", "கேலிக்ஸ் (Calyx)", "The cup of calcium carbonate in which a coral polyp sits." to
                        "பவள பாலிப் அமரும் கால்சியம் கார்பனேட் கிண்ணம்."),
                    bdef("Septum (plural: septa)", "செப்டம் (பன்மை: செப்டா)", "One of the radiating plates of the coral skeleton inside the calyx." to
                        "கேலிக்ஸினுள் உள்ள பவள எலும்புக்கூட்டின் ஆரமாகப் பரவும் தகடுகளில் ஒன்று."),
                    bnote("Not all corals build reefs", "எல்லாப் பவளங்களும் முகடு கட்டுவதில்லை",
                        "Sea fans, such as Gorgonia, and sea pens, such as Pennatula, are anthozoans with a flexible skeleton and do not build reefs. Only the stony corals with zooxanthellae make the reef framework." to
                            "கோர்கோனியா போன்ற கடல் விசிறிகளும் பென்னாட்டுலா போன்ற கடல் எழுதுகோல்களும் வளையும் எலும்புக்கூடு கொண்ட ஆந்தோசோவாக்கள்; முகடு கட்டுவதில்லை. சூக்சாந்தெல்லே உள்ள கல் பவளங்கள் மட்டுமே முகட்டின் கட்டமைப்பை உருவாக்குகின்றன."),
                )),
            bsec("zooxanthellae", "Partnership with zooxanthellae", "சூக்சாந்தெல்லேயுடன் கூட்டுறவு",
                blocks = listOf(
                    bp("The tissues of most shallow-water reef-building corals contain large numbers of single-celled photosynthetic dinoflagellates, called zooxanthellae, that live inside the cells of the gastrodermis. The relationship is a mutualism. The alga receives a protected home and a supply of carbon dioxide and nitrogen compounds from the coral's waste. The coral receives from the alga a large part of its energy, in the form of sugars and other compounds that are made by photosynthesis." to
                        "ஆழமற்ற நீரில் முகடு கட்டும் பெரும்பாலான பவளங்களின் திசுக்களில், காஸ்ட்ரோடெர்மிஸ் செல்களினுள் வாழும் ஒருசெல் ஒளிச்சேர்க்கை டைனோஃபிளாஜெல்லேட்டுகளான சூக்சாந்தெல்லே பெருந்தொகையில் உள்ளன. இது பகிர்ந்து வாழ்தல். பாசி பாதுகாப்பான வீட்டையும், பவளக் கழிவிலிருந்து கார்பன் டை ஆக்சைடு, நைட்ரஜன் சேர்மங்களையும் பெறுகிறது. பவளம் பாசியிடமிருந்து ஒளிச்சேர்க்கையால் உருவான சர்க்கரைகள் மற்றும் பிற சேர்மங்கள் வடிவில் தன் ஆற்றலின் பெரும்பகுதியைப் பெறுகிறது."),
                    bp("The partnership also helps the coral to build its skeleton. The removal of carbon dioxide by the alga favours the deposition of calcium carbonate, and corals with zooxanthellae lay down their skeletons much faster than those without. Light is therefore essential to a reef-building coral, not because the polyp itself photosynthesises but because its partner does." to
                        "கூட்டுறவு பவளம் தன் எலும்புக்கூட்டைக் கட்டவும் உதவுகிறது. பாசி கார்பன் டை ஆக்சைடை நீக்குவது கால்சியம் கார்பனேட் படிவதற்கு உகந்தது; சூக்சாந்தெல்லே உள்ள பவளங்கள் இல்லாதவற்றைவிட மிக வேகமாக எலும்புக்கூட்டைப் படியவைக்கின்றன. எனவே முகடு கட்டும் பவளத்துக்கு ஒளி இன்றியமையாதது; பாலிப் தானே ஒளிச்சேர்க்கை செய்வதால் அல்ல, அதன் கூட்டாளி செய்வதால்."),
                    bdef("Zooxanthellae", "சூக்சாந்தெல்லே (Zooxanthellae)", "Symbiotic, single-celled photosynthetic dinoflagellates that live inside the cells of reef-building corals." to
                        "முகடு கட்டும் பவளங்களின் செல்களினுள் வாழும், கூட்டுயிர் ஒருசெல் ஒளிச்சேர்க்கை டைனோஃபிளாஜெல்லேட்டுகள்."),
                    bdef("Mutualism", "பகிர்ந்து வாழ்தல்", "A relationship between two species in which each benefits." to
                        "இரு இனங்களுக்கிடையே ஒவ்வொன்றும் பயன்பெறும் உறவு."),
                    bdef("Photic zone", "ஒளி மண்டலம் (Photic zone)", "The upper layer of water into which enough sunlight penetrates for photosynthesis." to
                        "ஒளிச்சேர்க்கைக்குப் போதுமான சூரிய ஒளி ஊடுருவும் நீரின் மேல் அடுக்கு."),
                )),
            bsec("types", "Types of coral reef", "பவளப்பாறை வகைகள்",
                blocks = listOf(
                    bp("Three kinds of reef are recognised, according to their relation to the land." to
                        "நிலத்துடனான தொடர்புக்கு ஏற்ப மூன்று வகை முகடுகள் அங்கீகரிக்கப்படுகின்றன."),
                    btable("The three principal kinds of coral reef", "பவளப்பாறையின் மூன்று முக்கிய வகைகள்",
                        listOf("Type" to "வகை", "Position" to "அமைவிடம்", "Lagoon" to "கடற்காயல்"),
                        listOf(
                            listOf("Fringing reef" to "ஓர முகடு", "Grows directly from, or close to, the shore" to "கரையிலிருந்தே அல்லது அதன் அருகிலேயே வளர்கிறது", "None, or a narrow shallow channel" to "இல்லை, அல்லது குறுகிய ஆழமற்ற கால்வாய்"),
                            listOf("Barrier reef" to "தடுப்பு முகடு", "Lies parallel to the land, separated from it" to "நிலத்துக்கு இணையாக, அதிலிருந்து பிரிந்து கிடக்கிறது", "Broad or deep lagoon between reef and land" to "முகட்டுக்கும் நிலத்துக்கும் இடையே அகன்ற அல்லது ஆழமான கடற்காயல்"),
                            listOf("Atoll" to "வளைய முகடு", "A ring or oval of reef in open ocean" to "திறந்த கடலில் வளைய அல்லது முட்டை வடிவ முகடு", "Central lagoon enclosed by the reef" to "முகட்டால் சூழப்பட்ட மைய கடற்காயல்"),
                        )),
                    bp("The three types are often discussed as stages of one process. A fringing reef forms around a volcanic island; if the island sinks slowly while the coral grows upwards, a lagoon opens between the reef and the shrinking land and the fringing reef becomes a barrier reef; when the island has sunk below the surface, only the ring of reef remains as an atoll. This is Darwin's subsidence theory. It accounts for atolls that rest on volcanic rock, but it is not the only explanation of reef form and does not apply to every reef. The Great Barrier Reef of Australia is the largest barrier reef, and the Maldives and Lakshadweep islands are examples of atolls near India." to
                        "மூன்று வகைகளும் ஒரே செயல்முறையின் நிலைகளாகப் பெரும்பாலும் விவாதிக்கப்படுகின்றன. எரிமலைத் தீவைச் சுற்றி ஓர முகடு உருவாகிறது; பவளம் மேல்நோக்கி வளரும்போது தீவு மெதுவாக அமிழ்ந்தால், முகட்டுக்கும் சுருங்கும் நிலத்துக்கும் இடையே கடற்காயல் திறந்து, ஓர முகடு தடுப்பு முகடாகிறது; தீவு நீர்மட்டத்துக்குக் கீழே அமிழ்ந்ததும் முகட்டு வளையம் மட்டும் வளைய முகடாக எஞ்சுகிறது. இது டார்வினின் அமிழ்வுக் கோட்பாடு. எரிமலைப் பாறையின் மேல் அமைந்த வளைய முகடுகளை இது விளக்குகிறது; ஆனால் முகட்டு வடிவத்துக்கான ஒரே விளக்கம் அல்ல, எல்லா முகடுகளுக்கும் பொருந்தாது. ஆஸ்திரேலியாவின் பெரும் தடுப்பு முகடு மிகப்பெரிய தடுப்பு முகடு; இந்தியாவுக்கு அருகிலுள்ள மாலத்தீவுகளும் லட்சத்தீவுகளும் வளைய முகடுகளின் எடுத்துக்காட்டுகள்."),
                    bdef("Fringing reef", "ஓர முகடு (Fringing reef)", "A reef growing directly from or close to the shore." to
                        "கரையிலிருந்தே அல்லது அதன் அருகிலேயே வளரும் முகடு."),
                    bdef("Barrier reef", "தடுப்பு முகடு (Barrier reef)", "A reef separated from the land by a broad or deep lagoon." to
                        "அகன்ற அல்லது ஆழமான கடற்காயலால் நிலத்திலிருந்து பிரிக்கப்பட்ட முகடு."),
                    bdef("Atoll", "வளைய முகடு (Atoll)", "A ring- or oval-shaped reef enclosing a central lagoon in the open sea." to
                        "திறந்த கடலில் மைய கடற்காயலைச் சூழ்ந்த வளைய அல்லது முட்டை வடிவ முகடு."),
                    bdef("Lagoon", "கடற்காயல் (Lagoon)", "The body of shallow water between a reef and the land, or enclosed by an atoll." to
                        "முகட்டுக்கும் நிலத்துக்கும் இடையே அல்லது வளைய முகட்டால் சூழப்பட்ட ஆழமற்ற நீர்ப்பகுதி."),
                )),
            bsec("conditions", "Conditions for reef growth", "முகடு வளர்ச்சிக்கான நிலைகள்",
                blocks = listOf(
                    bpoints("Requirements of reef-building corals", "முகடு கட்டும் பவளங்களின் தேவைகள்",
                        "Warm water, generally with a temperature of about 23 to 29 degrees Celsius." to "வெதுவெதுப்பான நீர்; பொதுவாக சுமார் 23 முதல் 29 டிகிரி செல்சியஸ் வெப்பநிலை.",
                        "Shallow, clear water into which sunlight penetrates, since the zooxanthellae need light." to "சூக்சாந்தெல்லேக்கு ஒளி தேவை என்பதால், சூரிய ஒளி ஊடுருவும் ஆழமற்ற, தெளிந்த நீர்.",
                        "Normal ocean salinity; reef corals are damaged by large falls in salinity such as occur near river mouths." to "இயல்பான கடல் உவர்ப்பு; ஆற்று முகத்துவாரங்களுக்கு அருகில் ஏற்படும் பெரிய உவர்ப்புக் குறைவால் பவளங்கள் பாதிக்கப்படுகின்றன.",
                        "Water free of much silt, which would smother the polyps and shade the algae." to "பாலிப்புகளை மூடி, பாசிகளுக்கு நிழலிடும் அதிக வண்டல் இல்லாத நீர்.",
                        "A firm surface on which the larva can settle, and moving water that brings food and oxygen." to "லார்வா படியும் உறுதியான பரப்பு; உணவையும் ஆக்சிஜனையும் கொண்டு வரும் ஓடும் நீர்.",
                    ),
                    bp("These conditions explain the distribution of reefs. They are found in the tropics, on the eastern sides of continents where warm currents flow, and almost always in water that is not deeper than about fifty metres. The shallow water near the shore also explains why reefs are most affected by human activity." to
                        "இந்நிலைகள் முகடுகளின் பரவலை விளக்குகின்றன. அவை வெப்ப மண்டலத்தில், வெதுவெதுப்பான நீரோட்டங்கள் பாயும் கண்டங்களின் கிழக்குப் பக்கங்களில், கிட்டத்தட்ட எப்போதும் சுமார் ஐம்பது மீட்டருக்கு மிகாத ஆழமுள்ள நீரில் காணப்படுகின்றன. கரைக்கு அருகிலுள்ள ஆழமற்ற நீர், முகடுகள் மனித செயல்பாட்டால் அதிகம் பாதிக்கப்படுவதற்கும் காரணம்."),
                )),
            bsec("ecosystem", "The reef as an ecosystem", "முகடு ஒரு சூழல்மண்டலமாக",
                blocks = listOf(
                    bp("A reef is not one organism. It is a framework built by corals and by calcareous algae, and it provides surfaces, crevices and shelter for a great number of species: sponges, worms, molluscs, crustaceans, echinoderms and fishes. Food chains on a reef are very productive, in spite of the poor water around it, because nutrients are held and recycled within the community, a fact for which the partnership of coral and alga is partly responsible." to
                        "முகடு ஒற்றை உயிரி அல்ல. பவளங்களும் சுண்ணாம்புப் பாசிகளும் கட்டிய கட்டமைப்பு அது; கடற்பஞ்சுகள், புழுக்கள், மெல்லுடலிகள், கிரஸ்டேஷியன்கள், முட்தோலிகள், மீன்கள் என ஏராளமான இனங்களுக்கு பரப்புகள், பிளவுகள், அடைக்கலம் தருகிறது. சுற்றியுள்ள நீர் ஊட்டமற்றது என்றாலும் முகட்டிலுள்ள உணவுச் சங்கிலிகள் மிக உற்பத்தித் திறனுடையவை; ஏனெனில் ஊட்டச்சத்துகள் சமுதாயத்தினுள் பிடித்து மறுசுழற்சி செய்யப்படுகின்றன; இதற்குப் பவளம்-பாசிக் கூட்டுறவு ஓரளவு காரணம்."),
                    bnote("Reef, not animal", "முகடு, விலங்கு அல்ல",
                        "In answers write that the reef is an ecosystem built by many organisms. Only the coral polyp is the animal, and the reef as a whole is neither an animal nor a single colony." to
                            "விடைகளில் முகடு பல உயிரிகள் கட்டிய சூழல்மண்டலம் என எழுதுக. பவள பாலிப் மட்டுமே விலங்கு; முகடு முழுவதும் விலங்கும் அல்ல, ஒற்றைக் காலனியும் அல்ல."),
                )),
            bsec("reproduction", "Growth and reproduction of corals", "பவளங்களின் வளர்ச்சியும் இனப்பெருக்கமும்",
                blocks = listOf(
                    bp("A colony grows by asexual budding of its polyps, which is also how it increases in size and how a fragment broken from it by a storm may start a new colony. Sexual reproduction supplies the larvae by which a reef is founded and by which a damaged reef is restored. The polyps of most corals form eggs and sperm; some corals are hermaphrodite and others have separate sexes." to
                        "காலனி பாலிப்புகளின் பாலிலா மொட்டுவிடுதலால் வளர்கிறது; இதனாலேயே அது அளவில் பெருகுகிறது, புயலால் உடைந்த துண்டு புதிய காலனியைத் தொடங்கவும் முடிகிறது. பாலின இனப்பெருக்கம் முகடு நிறுவப்படவும் சேதமடைந்த முகடு மீளவும் உதவும் லார்வாக்களை வழங்குகிறது. பெரும்பாலான பவளங்களின் பாலிப்புகள் முட்டைகளையும் விந்தணுக்களையும் உருவாக்குகின்றன; சில பவளங்கள் இருபால் உயிரிகள், சில தனிப் பாலினம்."),
                    bp("In many reef corals the eggs and sperm are released into the water in a brief period, often on a few nights after the full moon in a particular month, so that great numbers of gametes are shed at the same moment by all the colonies of many species on a reef. Other corals brood the larvae inside the polyp. Fertilisation produces a planula, which swims, settles on a firm surface and becomes the first polyp of a new colony. The synchrony improves the chance of fertilisation, but the larvae face heavy loss, and only a small proportion settle in a suitable place." to
                        "பல முகடு பவளங்களில் முட்டைகளும் விந்தணுக்களும் குறுகிய காலத்தில், பெரும்பாலும் குறிப்பிட்ட மாதத்தில் முழு நிலவுக்குப் பின் சில இரவுகளில், நீரில் வெளியிடப்படுகின்றன; இதனால் முகட்டிலுள்ள பல இனங்களின் எல்லாக் காலனிகளும் ஒரே தருணத்தில் பெருந்தொகை கேமீட்டுகளை வெளியிடுகின்றன. வேறு பவளங்கள் லார்வாக்களை பாலிப்புக்குள் அடைகாக்கின்றன. கருவுறுதல் பிளானுலாவைத் தருகிறது; அது நீந்தி, உறுதியான பரப்பில் படிந்து புதிய காலனியின் முதல் பாலிப்பாகிறது. ஒத்திசைவு கருவுறுதல் வாய்ப்பை உயர்த்துகிறது; ஆனால் லார்வாக்கள் பெருமளவு இழப்பைச் சந்திக்கின்றன; சிறிய பகுதியே தகுந்த இடத்தில் படிகிறது."),
                    bdef("Planula", "பிளானுலா", "The ciliated, free-swimming larva of cnidarians, including corals." to
                        "பவளங்கள் உட்பட நிடேரியாக்களின் கசையிழை, சுதந்திரமாக நீந்தும் லார்வா."),
                    bdef("Broadcast spawning", "பரப்பு முட்டையிடல் (Broadcast spawning)", "The release of eggs and sperm into the water, where fertilisation takes place." to
                        "முட்டைகளையும் விந்தணுக்களையும் நீரில் வெளியிடுதல்; அங்கு கருவுறுதல் நிகழ்கிறது."),
                )),
            bsec("india", "Coral reefs of India", "இந்தியாவின் பவளப்பாறைகள்",
                blocks = listOf(
                    bp("Coral reefs in Indian waters occur in the Gulf of Mannar and Palk Bay on the south-east coast, the Gulf of Kachchh in the west, the Lakshadweep islands, which are atolls, and the Andaman and Nicobar islands, which have fringing reefs. The Gulf of Mannar, off the coast of Tamil Nadu, is a marine biosphere reserve with a rich reef fauna. The reefs of the area support fisheries on which many coastal people depend, and are exposed to the stresses of bleaching, sedimentation and destructive fishing that are described in this chapter." to
                        "இந்திய நீரில் பவளப்பாறைகள் தென்கிழக்குக் கடற்கரையின் மன்னார் வளைகுடா, பாக் விரிகுடாவிலும், மேற்கில் கச்சு வளைகுடாவிலும், வளைய முகடுகளான லட்சத்தீவுகளிலும், ஓர முகடுகள் உள்ள அந்தமான், நிகோபார் தீவுகளிலும் உள்ளன. தமிழகக் கடற்கரையை ஒட்டிய மன்னார் வளைகுடா வளமான முகட்டு விலங்கினம் கொண்ட கடல் உயிர்க்கோளக் காப்பகம். அப்பகுதியின் முகடுகள் பல கடலோர மக்கள் சார்ந்துள்ள மீன்பிடித்தொழிலுக்கு ஆதரவளிக்கின்றன; இந்த அத்தியாயத்தில் விவரிக்கப்பட்டுள்ள வெளிறல், வண்டல் படிவு, அழிவுமிக்க மீன்பிடி ஆகிய அழுத்தங்களுக்கும் உட்படுகின்றன."),
                )),
            bsec("kinds", "Kinds of coral: stony, soft and horny", "பவள வகைகள்: கல், மென்மை, கொம்பு",
                blocks = listOf(
                    bp("The word coral is used for polyps of two quite different subclasses of the Anthozoa, and it is useful to separate them. The Hexacorallia (Zoantharia) have tentacles and mesenteries in sixes or multiples of six. They include the sea anemones, which have no skeleton, and the stony corals, the Scleractinia, which are the principal builders of reefs. The Octocorallia (Alcyonaria) have eight tentacles, each with side branches like the barbs of a feather, and eight mesenteries. They include the soft corals, the sea fans and sea whips, the sea pens and the organ-pipe coral.",
                        "பவளம் என்ற சொல் ஆந்தோசோவாவின் இரு வெவ்வேறு துணை வகுப்புகளின் பாலிப்புகளுக்குப் பயன்படுகிறது; அவற்றைப் பிரித்தறிவது பயனுள்ளது. ஹெக்சாகோரல்லியாவில் (சூயாந்தேரியா) உணர்கொம்புகளும் மெசென்டரிகளும் ஆறின் மடங்குகளில் உள்ளன. எலும்புக்கூடற்ற கடல் சாமந்திகளும், முகடுகளின் முதன்மைக் கட்டுநர்களான கல் பவளங்களும் (ஸ்கிளீராக்டினியா) இதில் அடங்கும். ஆக்டோகோரல்லியாவில் (ஆல்சியோனேரியா) எட்டு உணர்கொம்புகள் உள்ளன; ஒவ்வொன்றுக்கும் இறகின் முட்கள் போன்ற பக்கக் கிளைகள் உண்டு; எட்டு மெசென்டரிகள் உள்ளன. மென் பவளங்கள், கடல் விசிறிகள், கடல் சாட்டைகள், கடல் பேனாக்கள், உறுப்புக்குழல் பவளம் ஆகியவை இதில் அடங்கும்."),
                    btable("Stony corals and octocorals compared", "கல் பவளங்களும் ஆக்டோகோரல்களும் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Stony corals (Scleractinia)" to "கல் பவளங்கள் (ஸ்கிளீராக்டினியா)", "Octocorals (Alcyonaria)" to "ஆக்டோகோரல்கள் (ஆல்சியோனேரியா)"),
                        listOf(
                            listOf("Tentacles" to "உணர்கொம்புகள்", "Six or multiples of six, simple" to "ஆறு அல்லது ஆறின் மடங்கு, எளிமையானவை", "Eight, with side branches (pinnate)" to "எட்டு, பக்கக் கிளைகளுடன் (இறகு வடிவம்)"),
                            listOf("Mesenteries" to "மெசென்டரிகள்", "In sixes or multiples of six" to "ஆறு அல்லது ஆறின் மடங்கு", "Eight" to "எட்டு"),
                            listOf("Skeleton" to "எலும்புக்கூடு", "Solid cup of calcium carbonate (aragonite) with septa" to "செப்டாக்களுடன் கால்சியம் கார்பனேட் (அரகோனைட்) திடக் கோப்பை", "Separate small sclerites, or a horny or calcareous axis" to "தனித்தனி சிறு ஸ்கிளீரைட்டுகள், அல்லது கொம்பு அல்லது சுண்ணாம்பு அச்சு"),
                            listOf("Role in reefs" to "முகடுகளில் பங்கு", "Principal framework builders" to "முதன்மைச் சட்டக் கட்டுநர்கள்", "Add variety and sometimes some framework" to "வகைமையையும் சிலவேளை சில சட்டகத்தையும் சேர்க்கின்றன"),
                            listOf("Examples" to "எடுத்துக்காட்டுகள்", "Fungia, Acropora, Porites, brain coral" to "ஃபங்கியா, அக்ரோபோரா, போரைட்டிஸ், மூளைப் பவளம்", "Alcyonium, sea fan, Pennatula, Tubipora" to "ஆல்சியோனியம், கடல் விசிறி, பென்னேட்டுலா, டியூபிபோரா"),
                        )),
                    bp("Among the octocorals, the red or precious coral, Corallium, has a hard red axis of calcium carbonate that is used in jewellery, and it lives in deeper water and does not form reefs. The blue coral, Heliopora, is unusual among octocorals in having a massive calcareous skeleton, and it contributes to the framework of some reefs. The black corals, the Antipatharia, belong to the Hexacorallia and have a horny black axis. Care is needed in an answer not to assign these to the wrong subclass.",
                        "ஆக்டோகோரல்களில் சிவப்பு அல்லது விலைமதிப்புள்ள பவளம் கோரல்லியம் கடினமான சிவப்புச் சுண்ணாம்பு அச்சைக் கொண்டுள்ளது; அது நகைகளில் பயன்படுகிறது; ஆழமான நீரில் வாழ்கிறது; முகடுகளை உருவாக்குவதில்லை. நீல பவளம் ஹீலியோபோரா ஆக்டோகோரல்களுக்கிடையே பெரிய சுண்ணாம்பு எலும்புக்கூடு கொண்டிருப்பதில் வேறுபடுகிறது; சில முகடுகளின் சட்டகத்துக்கு பங்களிக்கிறது. கருப்பு பவளங்கள் (ஆன்டிபாத்தேரியா) ஹெக்சாகோரல்லியாவைச் சேர்ந்தவை; கொம்புப் பொருளாலான கருப்பு அச்சு கொண்டவை. இவற்றைத் தவறான துணை வகுப்பில் இடாதபடி விடையில் கவனம் தேவை."),
                    bdef("Scleractinia", "ஸ்கிளீராக்டினியா (Scleractinia)", "The order of stony corals, with a skeleton of calcium carbonate and tentacles in multiples of six; the principal reef builders." to
                        "கால்சியம் கார்பனேட் எலும்புக்கூடும் ஆறின் மடங்குகளில் உணர்கொம்புகளும் உடைய கல் பவளங்களின் வரிசை; முகடு கட்டுநர்கள்."),
                    bdef("Octocorallia", "ஆக்டோகோரல்லியா (Octocorallia)", "The subclass of anthozoans whose polyps have eight pinnate tentacles and eight mesenteries; the soft corals, sea fans and sea pens." to
                        "பாலிப்புகள் எட்டு இறகு வடிவ உணர்கொம்புகளும் எட்டு மெசென்டரிகளும் கொண்ட ஆந்தோசோவாக்களின் துணை வகுப்பு; மென் பவளங்கள், கடல் விசிறிகள், கடல் பேனாக்கள்."),
                    bdef("Sclerite", "ஸ்கிளீரைட்", "A small calcareous piece, or spicule, embedded in the tissue of an octocoral." to
                        "ஆக்டோகோரலின் திசுவில் பதிந்துள்ள சிறு சுண்ணாம்புத் துண்டு அல்லது நுண்முள்."),
                )),
            bsec("calcification", "How the skeleton is laid down", "எலும்புக்கூடு எவ்வாறு படிகிறது",
                blocks = listOf(
                    bp("The skeleton of a stony coral is not made by the polyp as a whole but by a layer of cells at its base, the calicoblastic epidermis, which lies against the surface of the skeleton. These cells take up calcium and carbonate from the sea water and deposit crystals of calcium carbonate in the form of aragonite. Under the polyp lies a thin layer of fluid in which the crystals form, and the process is called calcification. The skeleton is added to continuously, and the upper surface of the skeleton is therefore the newest.",
                        "கல் பவளத்தின் எலும்புக்கூடை பாலிப் முழுவதும் உருவாக்குவதில்லை; அதன் அடிப்பகுதியில் எலும்புக்கூட்டின் மேற்பரப்பை ஒட்டியிருக்கும் செல் அடுக்கான கால்சிப்ளாஸ்டிக் எப்பிடெர்மிஸே உருவாக்குகிறது. இச்செல்கள் கடல்நீரிலிருந்து கால்சியத்தையும் கார்பனேட்டையும் எடுத்து, கால்சியம் கார்பனேட் படிகங்களை அரகோனைட்டாகப் படியச் செய்கின்றன. பாலிப்பின் கீழ் மெல்லிய திரவ அடுக்கு உள்ளது; அதில் படிகங்கள் உருவாகின்றன; இச்செயல்முறை சுண்ணாம்புப் படிவு எனப்படுகிறது. எலும்புக்கூடு தொடர்ந்து சேர்க்கப்படுகிறது; எனவே எலும்புக்கூட்டின் மேற்பரப்பே புதியது."),
                    bp("Calcification is faster in light and slower in the dark, and this is one of the effects of the zooxanthellae. The rate depends on the temperature and on the chemistry of the sea water, in particular on the availability of carbonate ions, and for this reason ocean acidification is a threat to reef-building corals. Growth is not always even through the year, and some corals lay down bands of skeleton that differ with the season, which scientists count as they would count the rings of a tree to estimate the age of a coral and to reconstruct past conditions in the water.",
                        "சுண்ணாம்புப் படிவு ஒளியில் வேகமாகவும் இருளில் மெதுவாகவும் நடக்கிறது; இது சூக்சாந்தெல்லேயின் விளைவுகளில் ஒன்று. வீதம் வெப்பநிலையையும் கடல்நீரின் வேதியியலையும், குறிப்பாக கார்பனேட் அயனிகளின் கிடைப்பையும் சார்ந்தது; எனவே கடல் அமிலமாதல் முகடு கட்டும் பவளங்களுக்கு அச்சுறுத்தல். வளர்ச்சி ஆண்டு முழுவதும் சீராக இருப்பதில்லை; சில பவளங்கள் பருவத்துக்கேற்ப வேறுபடும் எலும்புக்கூட்டுப் பட்டைகளை உருவாக்குகின்றன; மரத்தின் வளையங்களை எண்ணுவது போல விஞ்ஞானிகள் இவற்றை எண்ணி பவளத்தின் வயதை மதிப்பிடுகிறார்கள், நீரின் கடந்த நிலைகளையும் மீட்டமைக்கிறார்கள்."),
                    bdef("Calicoblastic epidermis", "கால்சிப்ளாஸ்டிக் எப்பிடெர்மிஸ் (Calicoblastic epidermis)", "The layer of cells at the base of a coral polyp that deposits the skeleton of calcium carbonate." to
                        "பவளப் பாலிப்பின் அடிப்பகுதியிலுள்ள, கால்சியம் கார்பனேட் எலும்புக்கூட்டைப் படியச் செய்யும் செல் அடுக்கு."),
                    bdef("Calcification", "சுண்ணாம்புப் படிவு (Calcification)", "The deposition of calcium carbonate by an organism to form a skeleton or shell." to
                        "உயிரி எலும்புக்கூடு அல்லது ஓடு உருவாக்க கால்சியம் கார்பனேட்டைப் படியச் செய்தல்."),
                )),
            bsec("forms", "Solitary and colonial forms", "தனி மற்றும் காலனி வடிவங்கள்",
                blocks = listOf(
                    bp("Stony corals may be solitary or colonial. Fungia, the mushroom coral, is a solitary coral, a single large polyp on a disc-like skeleton that lies free on the sea floor. Most reef-building corals are colonial, and the colonies take characteristic growth forms: the branching, tree-like or table-like forms of Acropora, the massive, rounded boulders of Porites, and the brain corals, in which the mouths of the polyps lie in winding valleys, so that the surface resembles the folds of a brain. The growth form is shaped by the conditions where the coral lives, with branching forms in calm, clear water and sturdy, massive forms where waves are strong.",
                        "கல் பவளங்கள் தனியாகவோ காலனியாகவோ இருக்கலாம். காளான் பவளமான ஃபங்கியா தனி பவளம்; கடல் தரையில் தனியாகக் கிடக்கும் தட்டு போன்ற எலும்புக்கூட்டின் மீதுள்ள ஒற்றைப் பெரிய பாலிப். முகடு கட்டும் பெரும்பாலான பவளங்கள் காலனிகள்; காலனிகள் தனித்தன்மை வாய்ந்த வளர்ச்சி வடிவங்களை எடுக்கின்றன: அக்ரோபோராவின் கிளைத்த, மரம் போன்ற அல்லது மேசை போன்ற வடிவங்கள், போரைட்டிஸின் பெரிய, உருண்டையான பாறைகள், பாலிப்புகளின் வாய்கள் வளைந்த பள்ளத்தாக்குகளில் அமைந்து மூளையின் மடிப்புகளைப் போலத் தோன்றும் மூளைப் பவளங்கள். வளர்ச்சி வடிவம் பவளம் வாழும் நிலைமையால் அமைகிறது; அமைதியான தெளிந்த நீரில் கிளைத்த வடிவங்களும் அலைகள் வலுவான இடத்தில் உறுதியான பெரிய வடிவங்களும் காணப்படுகின்றன."),
                    bp("The growth forms matter for the reef as a whole. Branching colonies grow fast and give a complex, three-dimensional habitat for fishes, but they break easily in storms and in anchoring. Massive colonies grow slowly but may live for centuries and hold the framework together. A mixed assemblage of forms is therefore the most resilient, and a reef dominated by one form is more exposed to a single kind of damage.",
                        "வளர்ச்சி வடிவங்கள் முழு முகட்டுக்கும் முக்கியம். கிளைத்த காலனிகள் வேகமாக வளர்ந்து மீன்களுக்குச் சிக்கலான முப்பரிமாண வாழிடம் அளிக்கின்றன; ஆனால் புயலிலும் நங்கூரமிடலிலும் எளிதில் உடைகின்றன. பெரிய காலனிகள் மெதுவாக வளர்கின்றன; ஆனால் நூற்றாண்டுகள் வாழ்ந்து சட்டகத்தைப் பிடித்து வைக்கின்றன. எனவே கலப்பு வடிவத் தொகுப்பே மிகவும் தாங்கும் திறன் கொண்டது; ஒரே வடிவம் ஆதிக்கம் செலுத்தும் முகடு ஒரே வகைச் சேதத்துக்கு அதிகம் வெளிப்படுகிறது."),
                    bdef("Solitary coral", "தனி பவளம் (Solitary coral)", "A coral consisting of a single polyp and its skeleton, such as Fungia." to
                        "ஃபங்கியா போல ஒற்றைப் பாலிப்பும் அதன் எலும்புக்கூடும் கொண்ட பவளம்."),
                    bdef("Colonial coral", "காலனி பவளம் (Colonial coral)", "A coral in which many polyps are joined by living tissue and a shared skeleton, formed by budding." to
                        "மொட்டுவிடுதலால் உருவான, உயிர்த் திசுவாலும் பொது எலும்புக்கூட்டாலும் இணைந்த பல பாலிப்புகளைக் கொண்ட பவளம்."),
                )),
            bsec("answering", "Answering questions on corals", "பவளங்கள் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("The questions on corals fall into four groups: the structure and skeleton of the polyp, the partnership with zooxanthellae, the types and formation of reefs, and bleaching and conservation. For the first, a labelled diagram of the polyp in its calyx with septa and tentacles is expected. For the second, the benefits to both partners should be stated. For the third, the three reef types should be tabulated, with the Darwin sequence from fringing reef to barrier reef to atoll as an explanation. For the fourth, causes, process and consequences should be separated.",
                        "பவளங்கள் குறித்த வினாக்கள் நான்கு குழுக்களாகின்றன: பாலிப்பின் அமைப்பும் எலும்புக்கூடும், சூக்சாந்தெல்லேயுடனான கூட்டுறவு, முகடுகளின் வகைகளும் உருவாக்கமும், வெளிறலும் பாதுகாப்பும். முதலாவதற்கு கேலிக்ஸ், செப்டா, உணர்கொம்புகளுடன் பாலிப்பின் பெயரிட்ட படம் எதிர்பார்க்கப்படுகிறது. இரண்டாவதற்கு இரு கூட்டாளிகளுக்கும் ஏற்படும் நன்மைகளைக் கூற வேண்டும். மூன்றாவதற்கு மூன்று முகட்டு வகைகளை அட்டவணைப்படுத்தி, ஓர முகடு, தடுப்பு முகடு, வளைய முகடு என்ற டார்வின் வரிசையை விளக்கமாகத் தர வேண்டும். நான்காவதற்கு காரணங்கள், செயல்முறை, விளைவுகளைப் பிரித்துக் கூற வேண்டும்."),
                    bpoints("Frequent errors to avoid", "தவிர்க்க வேண்டிய அடிக்கடி நிகழும் பிழைகள்",
                        "Saying that the coral is a plant, or that the zooxanthellae are the coral's own cells." to "பவளம் தாவரம் என்றோ, சூக்சாந்தெல்லே பவளத்தின் சொந்த செல்கள் என்றோ கூறுதல்.",
                        "Giving the polyp six tentacles in a soft coral, which has eight." to "மென் பவளத்தின் பாலிப்புக்கு ஆறு உணர்கொம்புகள் தருதல்; அதற்கு எட்டு உள்ளன.",
                        "Describing bleaching as the death of the coral rather than the loss of the algae." to "வெளிறலை பாசி இழப்பு என்று அல்லாமல் பவளத்தின் இறப்பு என்று விவரித்தல்.",
                        "Confusing the reef types in the Darwin sequence." to "டார்வின் வரிசையில் முகட்டு வகைகளைக் குழப்புதல்.",
                    ),
                )),
            bsec("bleaching", "Coral bleaching", "பவள வெளிறல்",
                blocks = listOf(
                    bp("Bleaching is the paling or whitening of a coral. When a coral is under stress it expels its zooxanthellae or loses their pigments, and since the tissue of the coral is transparent, the white skeleton beneath becomes visible. The most important cause of mass bleaching is a rise in sea temperature above the normal summer maximum, even by one or two degrees if it lasts for weeks. Strong light, changes in salinity and pollution may also contribute." to
                        "வெளிறல் என்பது பவளம் வெளிர்வது அல்லது வெண்மையாவது. அழுத்தத்தில் உள்ள பவளம் தன் சூக்சாந்தெல்லேயை வெளியேற்றுகிறது அல்லது அவற்றின் நிறமிகளை இழக்கிறது; பவளத் திசு ஒளிபுகும் தன்மையுடையதால் அடியிலுள்ள வெள்ளை எலும்புக்கூடு தெரிகிறது. பெருமளவு வெளிறலுக்கான மிக முக்கிய காரணம் கடல் வெப்பநிலை இயல்பான கோடை உச்சத்துக்கு மேல் உயர்வது; சில வாரங்கள் நீடித்தால் ஒன்று அல்லது இரண்டு டிகிரி உயர்வுகூட போதும். கடுமையான ஒளி, உவர்ப்பு மாற்றங்கள், மாசு ஆகியவையும் பங்களிக்கலாம்."),
                    bp("A bleached coral is not necessarily dead. It has lost its main source of energy, but if the stress passes soon enough the algae may return and the coral may recover. If the stress is prolonged the coral starves; growth and reproduction fall, disease becomes more likely, and many colonies die. The dead skeleton is then overgrown by seaweed and the reef loses its structure." to
                        "வெளிறிய பவளம் இறந்ததாகாது. அது தன் முதன்மை ஆற்றல் மூலத்தை இழந்துள்ளது; ஆனால் அழுத்தம் விரைவில் நீங்கினால் பாசிகள் திரும்பி பவளம் மீளலாம். அழுத்தம் நீடித்தால் பவளம் பட்டினி கிடக்கிறது; வளர்ச்சியும் இனப்பெருக்கமும் குறைகின்றன, நோய் வாய்ப்பு அதிகரிக்கிறது, பல காலனிகள் இறக்கின்றன. இறந்த எலும்புக்கூடு கடற்பாசியால் மூடப்பட்டு முகடு தன் கட்டமைப்பை இழக்கிறது."),
                    bpoints("Sequence of bleaching", "வெளிறலின் வரிசை",
                        "Water temperature rises above the normal maximum for some weeks." to "நீர் வெப்பநிலை சில வாரங்களுக்கு இயல்பான உச்சத்துக்கு மேல் உயர்கிறது.",
                        "The coral loses its zooxanthellae or their pigments." to "பவளம் சூக்சாந்தெல்லேயை அல்லது அவற்றின் நிறமிகளை இழக்கிறது.",
                        "The white skeleton shows through the transparent tissue." to "ஒளிபுகும் திசு வழியாக வெள்ளை எலும்புக்கூடு தெரிகிறது.",
                        "The coral loses its main energy source and is weakened." to "பவளம் தன் முதன்மை ஆற்றல் மூலத்தை இழந்து பலவீனமாகிறது.",
                        "If the stress ends in time the coral recovers; if not, it dies." to "அழுத்தம் உரிய நேரத்தில் முடிந்தால் பவளம் மீளும்; இல்லையெனில் இறக்கும்.",
                    ),
                    bdef("Coral bleaching", "பவள வெளிறல் (Coral bleaching)", "The loss of zooxanthellae or their pigments from a coral under stress, which exposes the white skeleton." to
                        "அழுத்தத்தில் உள்ள பவளம் சூக்சாந்தெல்லேயை அல்லது அவற்றின் நிறமிகளை இழந்து வெள்ளை எலும்புக்கூட்டை வெளிப்படுத்துதல்."),
                    bnote("Bleached is not dead", "வெளிறியது இறந்தது அல்ல",
                        "Do not write that bleaching kills the coral at once. Bleaching removes the coral's main energy source, and death follows only if the stress continues." to
                            "வெளிறல் பவளத்தை உடனே கொல்கிறது என எழுத வேண்டாம். வெளிறல் பவளத்தின் முதன்மை ஆற்றல் மூலத்தை நீக்குகிறது; அழுத்தம் தொடர்ந்தால் மட்டுமே இறப்பு ஏற்படுகிறது."),
                )),
        ),
        summary = listOf(
            "A coral is an anthozoan polyp that secretes a cup of calcium carbonate with septa; colonies grow by budding." to "பவளம் செப்டாவுடன் கூடிய கால்சியம் கார்பனேட் கிண்ணத்தைச் சுரக்கும் ஆந்தோசோவா பாலிப்; காலனிகள் மொட்டுவிடுதலால் வளர்கின்றன.",
            "Reef-building corals live in mutualism with zooxanthellae, which give them energy and help them lay down the skeleton." to "முகடு கட்டும் பவளங்கள் சூக்சாந்தெல்லேயுடன் பரஸ்பர நன்மை உறவில் வாழ்கின்றன; அவை ஆற்றலையும் எலும்புக்கூடு படிவதற்கான உதவியையும் தருகின்றன.",
            "Light is essential because the algae need it for photosynthesis, and so reefs are shallow and in clear water." to "பாசிகளுக்கு ஒளிச்சேர்க்கைக்கு ஒளி தேவை என்பதால் ஒளி இன்றியமையாதது; எனவே முகடுகள் ஆழமற்ற தெளிந்த நீரில் உள்ளன.",
            "The three kinds of reef are fringing, barrier and atoll." to "முகடுகளின் மூன்று வகைகள்: ஓரம், தடுப்பு, வளையம்.",
            "A reef is an ecosystem built by many organisms, and not a single animal." to "முகடு பல உயிரிகள் கட்டிய சூழல்மண்டலம்; ஒற்றை விலங்கு அல்ல.",
            "Bleaching is the loss of the algae under stress, chiefly heat; it weakens the coral and may kill it if the stress continues." to "வெளிறல் அழுத்தத்தால், முக்கியமாக வெப்பத்தால், பாசிகள் இழக்கப்படுவது; பவளத்தை பலவீனப்படுத்தும்; அழுத்தம் தொடர்ந்தால் கொல்லும்.",
        ),
        checks = listOf(
            bqc("What are zooxanthellae?", "சூக்சாந்தெல்லே என்றால் என்ன?",
                "Symbiotic photosynthetic dinoflagellates living in the cells of reef-building corals.", "முகடு கட்டும் பவளங்களின் செல்களில் வாழும் கூட்டுயிர் ஒளிச்சேர்க்கை டைனோஃபிளாஜெல்லேட்டுகள்."),
            bqc("Name the three main types of coral reef.", "பவளப்பாறையின் மூன்று முக்கிய வகைகளைக் குறிப்பிடுக.",
                "Fringing reef, barrier reef and atoll.", "ஓர முகடு, தடுப்பு முகடு, வளைய முகடு."),
            bqc("Why do reef-building corals need clear, shallow water?", "முகடு கட்டும் பவளங்களுக்கு ஏன் தெளிந்த, ஆழமற்ற நீர் தேவை?",
                "Their zooxanthellae need sunlight for photosynthesis, and silt would smother the polyps.", "அவற்றின் சூக்சாந்தெல்லேக்கு ஒளிச்சேர்க்கைக்கு சூரிய ஒளி தேவை; வண்டல் பாலிப்புகளை மூடிவிடும்."),
            bqc("Is a bleached coral always dead?", "வெளிறிய பவளம் எப்போதும் இறந்ததா?",
                "No. It has lost its main energy source and may recover if the stress ends in time.", "இல்லை. அது முதன்மை ஆற்றல் மூலத்தை இழந்துள்ளது; அழுத்தம் உரிய நேரத்தில் முடிந்தால் மீளலாம்."),
        ),
    )
}
