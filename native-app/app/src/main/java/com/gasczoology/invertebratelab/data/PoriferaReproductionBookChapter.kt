package com.gasczoology.invertebratelab.data

/** Book-style chapter: reproduction in Porifera. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PoriferaReproductionBookChapter {
    val chapter = bchapter(
        lessonId = "u2-reproduction",
        title = "Reproduction in sponges" to "கடற்பஞ்சுகளில் இனப்பெருக்கம்",
        objectives = listOf(
            "Distinguish asexual from sexual reproduction in sponges and give the main methods of each." to
                "கடற்பஞ்சுகளில் பாலிலா, பாலின இனப்பெருக்கத்தை வேறுபடுத்தி, ஒவ்வொன்றின் முக்கிய முறைகளைக் கூற.",
            "Describe the structure, function and significance of a gemmule, and explain why it is not a larva." to
                "ஜெம்யூலின் அமைப்பு, பணி, முக்கியத்துவத்தை விவரித்து, அது ஏன் லார்வா அல்ல என்பதை விளக்க.",
            "Outline gamete formation, fertilisation and early development in a sponge." to
                "கடற்பஞ்சில் கேமீட் உருவாக்கம், கருவுறுதல், ஆரம்ப வளர்ச்சி ஆகியவற்றை வரைந்து விளக்க.",
            "Name the larvae of calcareous and demosponges and describe the inversion of the amphiblastula." to
                "சுண்ணாம்புக் கடற்பஞ்சு, டெமோஸ்பாஞ்சு லார்வாக்களைப் பெயரிட்டு, ஆம்ஃபிபிளாஸ்டுலாவின் புரட்டலை விவரிக்க.",
            "Explain why archaeocytes make regeneration and gemmule formation possible." to
                "மீளுருவாக்கமும் ஜெம்யூல் உருவாக்கமும் ஆர்கியோசைட்டுகளால் எவ்வாறு சாத்தியமாகின்றன என்பதை விளக்க.",
        ),
        introduction = listOf(
            "A sponge has no organs, and so its reproduction is accomplished by cells that are set aside for the purpose or that retain the power to become any other kind of cell. Two kinds of reproduction occur, and they do different work. Asexual reproduction keeps a successful sponge in the place where it is already living, by producing copies of it close by or by carrying it through bad seasons. Sexual reproduction produces new combinations of genes and a free-swimming larva that spreads the species to new places." to
                "கடற்பஞ்சுக்கு உறுப்புகள் இல்லை; எனவே அதன் இனப்பெருக்கம் அதற்காக ஒதுக்கப்பட்ட செல்களாலோ, வேறு எந்த வகை செல்லாகவும் மாறும் ஆற்றலைத் தக்கவைத்த செல்களாலோ நிறைவேறுகிறது. இரு வகை இனப்பெருக்கம் நிகழ்கிறது; இரண்டும் வெவ்வேறு பணிகளைச் செய்கின்றன. பாலிலா இனப்பெருக்கம், வெற்றிகரமான கடற்பஞ்சை அது வாழும் இடத்திலேயே, அருகில் நகல்களை உருவாக்கியோ மோசமான பருவங்களைக் கடக்கச் செய்தோ நிலைநிறுத்துகிறது. பாலின இனப்பெருக்கம் புதிய மரபணுச் சேர்க்கைகளையும், இனத்தைப் புதிய இடங்களுக்குப் பரப்பும் சுதந்திரமாக நீந்தும் லார்வாவையும் தருகிறது.",
            "This chapter treats the two kinds in turn, begins each with a definition and then follows the sequence of events. It ends with the regeneration that underlies both. A useful memory aid, which the examination notes use, is stay or disperse: asexual methods keep the sponge where it is, whereas the sexual larva disperses." to
                "இந்த அத்தியாயம் இரண்டு வகைகளையும் வரிசையாக ஆராய்கிறது; ஒவ்வொன்றையும் வரையறையுடன் தொடங்கி நிகழ்வுகளின் தொடரைப் பின்தொடர்கிறது. இரண்டுக்கும் அடிப்படையான மீளுருவாக்கத்துடன் முடிகிறது. தேர்வுக் குறிப்புகள் பயன்படுத்தும் நினைவூட்டல்: நிலை அல்லது பரவல் — பாலிலா முறைகள் கடற்பஞ்சை இருக்கும் இடத்திலேயே வைக்கின்றன; பாலின லார்வா பரவுகிறது.",
        ),
        sections = listOf(
            bsec("overview", "Two ways of reproducing", "இனப்பெருக்கத்தின் இரு வழிகள்",
                blocks = listOf(
                    bp("Sponges are sessile as adults. A fixed animal cannot go to find a mate, and it cannot move away when conditions turn against it. Both difficulties are met by the reproductive habits of the phylum. The water current that serves feeding also serves mating, since sperm are released into the water and are drawn into the canals of other sponges. And the cells of a sponge are so flexible that a small piece, or a packet of cells in a protective case, can grow into a whole animal." to
                        "வளர்ந்த கடற்பஞ்சுகள் ஒட்டி வாழ்பவை. நிலைத்த விலங்கு இணையைத் தேடிச் செல்ல முடியாது; சூழல் எதிராக மாறும்போது நகர்ந்து செல்லவும் முடியாது. இவ்விரு இடர்களையும் தொகுதியின் இனப்பெருக்கப் பழக்கங்கள் சமாளிக்கின்றன. உணவூட்டத்துக்குப் பயன்படும் நீரோட்டமே இணைசேர்க்கைக்கும் பயன்படுகிறது; விந்தணுக்கள் நீரில் விடப்பட்டு பிற கடற்பஞ்சுகளின் கால்வாய்களுக்குள் இழுக்கப்படுகின்றன. மேலும் கடற்பஞ்சின் செல்கள் மிக நெகிழ்வானவை; சிறு துண்டோ பாதுகாப்பு உறையுடன் கூடிய செல் கட்டோ முழு விலங்காக வளர முடியும்."),
                    btable("Asexual and sexual reproduction in sponges compared", "கடற்பஞ்சுகளில் பாலிலா, பாலின இனப்பெருக்கத்தின் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Asexual" to "பாலிலா", "Sexual" to "பாலினம்"),
                        listOf(
                            listOf("Gametes" to "கேமீட்டுகள்", "Not formed" to "உருவாவதில்லை", "Eggs and sperm formed" to "முட்டையும் விந்தணுவும் உருவாகின்றன"),
                            listOf("Methods" to "முறைகள்", "Budding, fragmentation, gemmules" to "மொட்டுவிடுதல், துண்டாக்கம், ஜெம்யூல்கள்", "Fertilisation, larva, metamorphosis" to "கருவுறுதல், லார்வா, உருமாற்றம்"),
                            listOf("Offspring" to "சந்ததி", "Genetically identical clone" to "மரபுப்படி ஒத்த நகல்", "Genetically new individual" to "மரபுப்படி புதிய உயிரி"),
                            listOf("Main role" to "முதன்மைப் பணி", "Stay in place, survive bad seasons" to "இடத்தில் நிலைத்தல், மோசமான பருவத்தைக் கடத்தல்", "Disperse and recombine genes" to "பரவுதல், மரபணுக்களை மறுசேர்த்தல்"),
                        )),
                    bdef("Sessile", "ஒட்டி வாழும்",
                        "Attached to a surface and not able to move from place to place, as the adult sponge is." to
                            "வளர்ந்த கடற்பஞ்சைப்போல ஒரு பரப்பில் ஒட்டியிருந்து, இடம்விட்டு இடம் நகர இயலாத நிலை."),
                    bdef("Clone", "நகல் உயிரி",
                        "An individual that is genetically identical to the one from which it arose by asexual reproduction." to
                            "பாலிலா இனப்பெருக்கத்தால் தோன்றிய, மூல உயிரியுடன் மரபுப்படி ஒத்த உயிரி."),
                )),
            bsec("asexual", "Asexual reproduction", "பாலிலா இனப்பெருக்கம்",
                subs = listOf(
                    bsub("Budding and fragmentation", "மொட்டுவிடுதலும் துண்டாக்கமும்",
                        bp("In budding a part of the parent grows outwards as a small outgrowth, develops its own canals, chambers and osculum, and either separates or remains attached, so that the bud becomes a member of a colony. Many sponges are colonies of this kind, and the line between one individual and several joined ones is not sharp." to
                            "மொட்டுவிடுதலில் தாய்க் கடற்பஞ்சின் ஒரு பகுதி சிறு புடைப்பாக வெளிநோக்கி வளர்ந்து, தன் சொந்தக் கால்வாய்கள், அறைகள், ஆஸ்குலத்தை உருவாக்கி, பிரிகிறது அல்லது இணைந்தே இருந்து காலனியின் உறுப்பினராகிறது. பல கடற்பஞ்சுகள் இத்தகைய காலனிகளே; ஓர் உயிரிக்கும் இணைந்த பல உயிரிகளுக்கும் இடையிலான எல்லை தெளிவானதல்ல."),
                        bp("In fragmentation a piece is broken from the sponge by waves, by an animal or by a fisherman's net, and, lodging on a suitable surface, it reorganises itself into a new sponge. This ability is the basis of sponge farming, in which pieces cut from a bath sponge are fixed to ropes and allowed to grow." to
                            "துண்டாக்கத்தில் அலைகளாலோ விலங்காலோ வலையாலோ கடற்பஞ்சிலிருந்து ஒரு துண்டு உடைந்து, ஏற்ற பரப்பில் தங்கி, தன்னை மறுசீரமைத்துப் புதிய கடற்பஞ்சாகிறது. குளியல் கடற்பஞ்சிலிருந்து வெட்டிய துண்டுகளைக் கயிறுகளில் கட்டி வளர விடும் கடற்பஞ்சு வளர்ப்பின் அடிப்படை இத்திறனே."),
                        bdef("Budding", "மொட்டுவிடுதல்",
                            "Asexual reproduction in which a new individual grows as an outgrowth of the parent." to
                                "பெற்றோரின் புடைப்பாக புதிய உயிரி வளரும் பாலிலா இனப்பெருக்கம்."),
                        bdef("Fragmentation", "துண்டாக்கம்",
                            "Asexual reproduction in which a broken piece of the parent grows into a new individual." to
                                "பெற்றோரிடமிருந்து உடைந்த துண்டு புதிய உயிரியாக வளரும் பாலிலா இனப்பெருக்கம்."),
                    ),
                    bsub("Gemmules", "ஜெம்யூல்கள்",
                        bp("The gemmule is the specialised resting body of the sponge. It is formed in the mesohyl of freshwater sponges, such as Spongilla, and in a few marine sponges, usually towards the end of the growing season. Archaeocytes gather in a mass, are loaded with food reserves, and are enclosed in a hard coat of spongin, often strengthened by spicules called gemmoscleres. The coat has a small opening, the micropyle." to
                            "ஜெம்யூல் கடற்பஞ்சின் சிறப்பு ஓய்வு உடல். ஸ்பாஞ்சில்லா போன்ற நன்னீர்க் கடற்பஞ்சுகளின் மீசோஹைலிலும் சில கடல் கடற்பஞ்சுகளிலும், பொதுவாக வளர்ச்சிப் பருவத்தின் இறுதியில் உருவாகிறது. ஆர்கியோசைட்டுகள் ஒரு திரளாகக் கூடி, உணவுக் கையிருப்பால் நிரப்பப்பட்டு, ஸ்பாஞ்சினால் ஆன கடின உறையில் அடைக்கப்படுகின்றன; அது பெரும்பாலும் ஜெம்மோஸ்கிளீர் எனப்படும் முட்களால் வலுப்படுகிறது. உறையில் மைக்ரோபைல் என்ற சிறு திறப்பு உண்டு."),
                        bp("When the parent dies in winter or in drought, the gemmules remain alive, resisting cold and drying that would kill an ordinary sponge. When favourable conditions return, the cells emerge through the micropyle, settle, and organise themselves into a small sponge. In this way the population survives a season in which the adult sponges do not." to
                            "குளிர்காலத்திலோ வறட்சியிலோ தாய் இறந்தாலும் ஜெம்யூல்கள் உயிருடன் இருந்து, சாதாரண கடற்பஞ்சைக் கொல்லும் குளிரையும் உலர்வையும் தாங்குகின்றன. சாதகமான நிலை திரும்பும்போது செல்கள் மைக்ரோபைல் வழியாக வெளிவந்து, தங்கி, தம்மை சிறு கடற்பஞ்சாக ஒழுங்கமைக்கின்றன. இவ்வாறு வளர்ந்த கடற்பஞ்சுகள் வாழ முடியாத பருவத்தையும் தொகை கடக்கிறது."),
                        bpoints("Features of a gemmule", "ஜெம்யூலின் பண்புகள்",
                            "An internal asexual packet of archaeocytes, rich in food reserves." to "உணவுக் கையிருப்பு நிறைந்த, ஆர்கியோசைட்டுகளின் உள் பாலிலா கட்டு.",
                            "Enclosed in a tough coat of spongin, often with gemmoscleres." to "ஸ்பாஞ்சினால் ஆன உறுதியான உறையால், பெரும்பாலும் ஜெம்மோஸ்கிளீருடன், அடைக்கப்பட்டுள்ளது.",
                            "Provided with a micropyle through which the cells emerge." to "செல்கள் வெளிவரும் மைக்ரோபைல் உடையது.",
                            "Resists cold, drought and other poor conditions." to "குளிர், வறட்சி, பிற மோசமான நிலைகளைத் தாங்குகிறது.",
                            "Characteristic of freshwater sponges, and not a routine method in marine Sycon." to "நன்னீர்க் கடற்பஞ்சுகளின் பண்பு; கடல் சைக்கானில் வழக்கமான முறை அல்ல.",
                        ),
                        bdef("Gemmule", "ஜெம்யூல்",
                            "A resistant internal bud of archaeocytes enclosed in a protective coat, formed asexually, which survives adverse conditions and later grows into a new sponge." to
                                "பாதுகாப்பு உறையால் அடைக்கப்பட்ட, பாலிலா முறையில் உருவாகும் ஆர்கியோசைட்டுகளின் எதிர்ப்புத்திறன் மிக்க உள் மொட்டு; மோசமான நிலையைத் தாங்கிப் பின்னர் புதிய கடற்பஞ்சாக வளர்கிறது."),
                        bdef("Micropyle", "மைக்ரோபைல்",
                            "The small opening in the coat of a gemmule through which the cells emerge." to
                                "ஜெம்யூலின் உறையிலுள்ள, செல்கள் வெளிவரும் சிறு திறப்பு."),
                        bnote("A gemmule is not a larva", "ஜெம்யூல் லார்வா அல்ல",
                            "A larva comes from a fertilised egg and swims; a gemmule is made without gametes, does not swim, and gives a clone of the parent. Writing that a gemmule is a larva is a recurring error in answers." to
                                "லார்வா கருவுற்ற முட்டையிலிருந்து தோன்றி நீந்துகிறது; ஜெம்யூல் கேமீட்டுகள் இன்றி உருவாகி, நீந்தாமல், தாயின் நகலைத் தருகிறது. ஜெம்யூலை லார்வா எனக் கூறுவது விடைகளில் தொடர்ந்து வரும் பிழை."),
                    ),
                )),
            bsec("sexual", "Sexual reproduction", "பாலின இனப்பெருக்கம்",
                subs = listOf(
                    bsub("Gametes and the sexes", "கேமீட்டுகளும் பாலினங்களும்",
                        bp("Sponges have no gonads. Eggs and sperm arise from cells of the mesohyl, usually from archaeocytes or from choanocytes, and they develop in the body of the sponge. Many sponges are hermaphrodite, producing both kinds of gamete, but usually at different times, so that self-fertilisation is avoided. Others have separate sexes. Sperm commonly appear in great numbers, and a mature sponge may release them in a cloud that can be seen from the surface." to
                            "கடற்பஞ்சுக்கு இனப்பெருக்க உறுப்பு இல்லை. முட்டைகளும் விந்தணுக்களும் மீசோஹைல் செல்களிலிருந்து, பொதுவாக ஆர்கியோசைட்டுகளிலிருந்து அல்லது கோனோசைட்டுகளிலிருந்து, தோன்றி கடற்பஞ்சின் உடலுக்குள் வளர்கின்றன. பல கடற்பஞ்சுகள் இருபால் உயிரிகள்; இருவகை கேமீட்டுகளையும் உருவாக்குகின்றன, ஆனால் பொதுவாக வெவ்வேறு காலத்தில்; இதனால் தன்கருவுறுதல் தவிர்க்கப்படுகிறது. வேறு சிலவற்றில் பாலினங்கள் தனித்தனி. விந்தணுக்கள் பொதுவாக பெருந்தொகையில் தோன்றுகின்றன; முதிர்ந்த கடற்பஞ்சு அவற்றை மேற்பரப்பிலிருந்து தெரியும் மேகமாக வெளியிடக்கூடும்."),
                        bdef("Hermaphrodite", "இருபால் உயிரி",
                            "An individual that produces both eggs and sperm, in a sponge usually at different times." to
                                "முட்டை, விந்தணு இரண்டையும் உருவாக்கும் உயிரி; கடற்பஞ்சில் பொதுவாக வெவ்வேறு காலத்தில்."),
                    ),
                    bsub("Fertilisation", "கருவுறுதல்",
                        bp("The sperm leave one sponge through its osculum and are carried by the water to another. They enter the second sponge through its ostia and are drawn into the canals by the current. In many sponges a choanocyte engulfs a sperm, loses its collar and flagellum, and, as a carrier cell, passes through the mesohyl to the egg, to which it delivers the sperm. The egg is retained in the body of the parent, and fertilisation is therefore internal." to
                            "விந்தணுக்கள் ஒரு கடற்பஞ்சின் ஆஸ்குலம் வழியாக வெளியேறி, நீரால் மற்றொன்றுக்குக் கொண்டு செல்லப்படுகின்றன. அவை இரண்டாவது கடற்பஞ்சின் ஆஸ்டியா வழியாக நுழைந்து ஓட்டத்தால் கால்வாய்களுக்குள் இழுக்கப்படுகின்றன. பல கடற்பஞ்சுகளில் ஒரு கோனோசைட் விந்தணுவை விழுங்கி, காலரையும் கசையிழையையும் இழந்து, நோய்க்கடத்திச் செல்லாக மீசோஹைல் வழியாக முட்டையிடம் சென்று விந்தணுவை ஒப்படைக்கிறது. முட்டை தாயின் உடலில் தக்கவைக்கப்படுகிறது; எனவே கருவுறுதல் உட்புறமானது."),
                        bdef("Carrier cell", "கடத்து செல்",
                            "A choanocyte that has taken up a sperm and carries it through the mesohyl to an egg." to
                                "விந்தணுவை ஏற்று, மீசோஹைல் வழியாக முட்டையிடம் கொண்டு செல்லும் கோனோசைட்."),
                    ),
                    bsub("Cleavage and the larva", "பிளவுறுதலும் லார்வாவும்",
                        bp("The zygote cleaves within the parent and forms a larva, which escapes through the canals and swims away by means of its flagella. The larva has the form of a hollow or solid ball of cells with a covering of flagella, and it differs from the adult in being ciliated on the outside. Two forms are important: the amphiblastula, of calcareous sponges such as Sycon, and the parenchymula, of most demosponges." to
                            "கருமுட்டை தாயினுள் பிளவுற்று லார்வாவை உருவாக்குகிறது; அது கால்வாய்கள் வழியாக வெளியேறி கசையிழைகளால் நீந்திச் செல்கிறது. லார்வா செல்களின் குழி அல்லது திண்ம உருண்டை வடிவில், கசையிழை மூடியுடன் இருக்கிறது; வெளிப்புறத்தில் கசையிழைகள் இருப்பதால் முதிர்ந்த கடற்பஞ்சிலிருந்து வேறுபடுகிறது. இரு வடிவங்கள் முக்கியம்: சைக்கான் போன்ற சுண்ணாம்புக் கடற்பஞ்சுகளின் ஆம்ஃபிபிளாஸ்டுலா; பெரும்பாலான டெமோஸ்பாஞ்சுகளின் பரன்கைமுலா."),
                        btable("Sponge larvae compared", "கடற்பஞ்சு லார்வாக்களின் ஒப்பீடு",
                            listOf("Feature" to "பண்பு", "Amphiblastula" to "ஆம்ஃபிபிளாஸ்டுலா", "Parenchymula" to "பரன்கைமுலா"),
                            listOf(
                                listOf("Found in" to "காணப்படுவது", "Calcareous sponges such as Sycon" to "சைக்கான் போன்ற சுண்ணாம்புக் கடற்பஞ்சுகள்", "Most demosponges" to "பெரும்பாலான டெமோஸ்பாஞ்சுகள்"),
                                listOf("Structure" to "அமைப்பு", "Hollow; flagellated cells at one half, large granular cells at the other" to "குழியானது; ஒரு பாதியில் கசையிழை செல்கள், மறுபாதியில் பெரிய துகள் செல்கள்", "Solid; flagellated outside, cells packed within" to "திண்மமானது; வெளியே கசையிழை, உள்ளே செல்கள் நிரம்பியவை"),
                                listOf("Settling" to "படிதல்", "Flagellated cells turn inwards (inversion)" to "கசையிழை செல்கள் உள்நோக்கித் திரும்புகின்றன (புரட்டல்)", "Flagellated cells move in as the larva settles" to "லார்வா படியும்போது கசையிழை செல்கள் உள்ளே செல்கின்றன"),
                            )),
                        bdef("Amphiblastula", "ஆம்ஃபிபிளாஸ்டுலா",
                            "The free-swimming hollow larva of calcareous sponges, with small flagellated cells at one end and large granular cells at the other." to
                                "சுண்ணாம்புக் கடற்பஞ்சுகளின், ஒரு முனையில் சிறு கசையிழை செல்களும் மறுமுனையில் பெரிய துகள் செல்களும் கொண்ட, சுதந்திரமாக நீந்தும் குழி லார்வா."),
                        bdef("Parenchymula", "பரன்கைமுலா",
                            "The solid, flagellated larva of most demosponges." to
                                "பெரும்பாலான டெமோஸ்பாஞ்சுகளின் திண்ம, கசையிழை லார்வா."),
                    ),
                    bsub("Settlement and inversion", "படிதலும் புரட்டலும்",
                        bp("After a short free life, which may be a matter of hours or a few days, the larva settles on a firm surface, attaching itself by the end at which the flagella are absent or by the anterior end, according to the species. In the amphiblastula the flagellated cells then turn inwards and the granular cells grow over the outside, so that the position of the two kinds of cell is reversed. This reversal is called inversion. It is a feature of calcareous sponges, and it is the reason that the flagellated cells, which were outside in the swimming larva, are on the inside, as the choanocytes of the adult." to
                            "சில மணி நேரங்கள் அல்லது சில நாட்கள் நீடிக்கும் குறுகிய தனி வாழ்க்கைக்குப் பின், லார்வா உறுதியான பரப்பில் படிகிறது; இனத்துக்கு ஏற்ப, கசையிழை இல்லாத முனையால் அல்லது முன் முனையால் ஒட்டிக்கொள்கிறது. ஆம்ஃபிபிளாஸ்டுலாவில் கசையிழை செல்கள் உள்நோக்கித் திரும்ப, துகள் செல்கள் வெளிப்புறத்தின் மேல் வளர்கின்றன; இரு வகை செல்களின் இடம் தலைகீழாகிறது. இந்த மாற்றம் புரட்டல் எனப்படுகிறது. இது சுண்ணாம்புக் கடற்பஞ்சுகளின் பண்பு; நீந்தும் லார்வாவில் வெளியே இருந்த கசையிழை செல்கள் முதிர்ந்த கடற்பஞ்சின் கோனோசைட்டுகளாக உள்ளே இருப்பதற்கான காரணம் இதுவே."),
                        bdef("Inversion", "புரட்டல்",
                            "The turning inside out of the larval cell layers at settlement, which brings the flagellated cells to the inside of the young sponge." to
                                "படியும்போது லார்வாவின் செல் அடுக்குகள் உள்ளும் புறமும் மாறி, கசையிழை செல்களை இளம் கடற்பஞ்சின் உட்புறத்துக்குக் கொண்டு வருவது."),
                        bdef("Metamorphosis", "உருமாற்றம்",
                            "The change from a free-swimming larva to a sessile young sponge." to
                                "சுதந்திரமாக நீந்தும் லார்வா, ஒட்டி வாழும் இளம் கடற்பஞ்சாக மாறும் மாற்றம்."),
                        bnote("Inversion has a cause", "புரட்டலுக்குக் காரணம் உண்டு",
                            "Inversion is not required because the adult current would otherwise fail. It occurs because the flagellated cells, which were the outer layer of the larva, end up inside the sponge, where they line the canals and form the choanocytes. Stating it that way is both correct and enough for the answer." to
                                "முதிர்ந்த கடற்பஞ்சின் ஓட்டம் இல்லையெனில் செயலிழக்கும் என்பதால் புரட்டல் தேவைப்படுவதில்லை. லார்வாவின் வெளியடுக்காக இருந்த கசையிழை செல்கள் கடற்பஞ்சின் உள்ளே சென்று கால்வாய்களை மூடி கோனோசைட்டுகளாவதாலேயே இது நிகழ்கிறது. இவ்வாறு கூறுவது சரியானதும் விடைக்குப் போதுமானதும் ஆகும்."),
                    ),
                )),
            bsec("regeneration", "Regeneration and the archaeocyte", "மீளுருவாக்கமும் ஆர்கியோசைட்டும்",
                blocks = listOf(
                    bp("Both asexual and sexual reproduction depend on the same property of the sponge, the ability of its cells to change from one kind to another. The archaeocyte is totipotent: it can become a choanocyte, a pinacocyte, a sclerocyte, a spongocyte, or an egg or sperm. It is the cell that fills the gemmule, forms the gametes, and rebuilds a lost part of the body." to
                        "பாலிலா, பாலின இனப்பெருக்கங்கள் இரண்டும் கடற்பஞ்சின் ஒரே பண்பைச் சார்ந்துள்ளன: செல்கள் ஒரு வகையிலிருந்து மற்றொன்றாக மாறும் திறன். ஆர்கியோசைட் முழுத்திறன் உடையது: கோனோசைட், பினாகோசைட், ஸ்கிளீரோசைட், ஸ்பாஞ்சோசைட் அல்லது முட்டை, விந்தணுவாக மாற முடியும். ஜெம்யூலை நிரப்புவதும், கேமீட்டுகளை உருவாக்குவதும், இழந்த உடல் பகுதியை மீண்டும் கட்டுவதும் இந்த செல்லே."),
                    bp("The classical demonstration of this plasticity is the dissociation experiment. When a sponge is pressed through fine silk and the cells are separated, the cells come together again in water, sort themselves and rebuild a small sponge. The experiment shows that the organisation of a sponge is a property of its cells and does not depend on organs." to
                        "இந்த நெகிழ்வின் பாரம்பரிய நிரூபணம் பிரிப்புச் சோதனை. கடற்பஞ்சை மென்பட்டுத் துணி வழியாக அழுத்தி செல்களைப் பிரித்தால், செல்கள் நீரில் மீண்டும் கூடி, தம்மை வகைப்படுத்தி, சிறு கடற்பஞ்சை மறுகட்டமைக்கின்றன. கடற்பஞ்சின் அமைப்பு அதன் செல்களின் பண்பே என்பதையும் உறுப்புகளைச் சார்ந்ததல்ல என்பதையும் இச்சோதனை காட்டுகிறது."),
                    bdef("Totipotent", "முழுத்திறன்",
                        "Able to develop into any kind of cell of the body, as the archaeocyte of a sponge can." to
                            "கடற்பஞ்சின் ஆர்கியோசைட்டைப்போல, உடலின் எந்த வகை செல்லாகவும் வளரும் திறன்."),
                    bdef("Regeneration", "மீளுருவாக்கம்",
                        "The replacement of a lost part, or the growth of a whole animal from a piece, by reorganisation of the remaining cells." to
                            "எஞ்சிய செல்களின் மறுசீரமைப்பால் இழந்த பகுதியை மாற்றுவது அல்லது ஒரு துண்டிலிருந்து முழு விலங்கை வளர்ப்பது."),
                )),
            bsec("life-cycle", "The life cycle in sequence", "வாழ்க்கைச் சுழற்சியின் வரிசை",
                blocks = listOf(
                    bpoints("Sexual cycle of a calcareous sponge", "சுண்ணாம்புக் கடற்பஞ்சின் பாலினச் சுழற்சி",
                        "Eggs and sperm form in the mesohyl of different sponges, or of the same sponge at different times." to "முட்டைகளும் விந்தணுக்களும் வெவ்வேறு கடற்பஞ்சுகளின் மீசோஹைலில், அல்லது ஒரே கடற்பஞ்சில் வெவ்வேறு காலத்தில், உருவாகின்றன.",
                        "Sperm leave by the osculum and are carried to another sponge." to "விந்தணுக்கள் ஆஸ்குலம் வழியாக வெளியேறி மற்றொரு கடற்பஞ்சுக்குக் கொண்டு செல்லப்படுகின்றன.",
                        "A carrier cell brings a sperm to a retained egg, which is fertilised." to "கடத்து செல் ஒரு விந்தணுவைத் தக்கவைக்கப்பட்ட முட்டையிடம் கொண்டு வர, முட்டை கருவுறுகிறது.",
                        "The zygote cleaves and forms an amphiblastula inside the parent." to "கருமுட்டை பிளவுற்று தாயினுள் ஆம்ஃபிபிளாஸ்டுலாவை உருவாக்குகிறது.",
                        "The larva leaves through the canals and swims for a short time." to "லார்வா கால்வாய்கள் வழியாக வெளியேறி சிறிது நேரம் நீந்துகிறது.",
                        "It settles, undergoes inversion and metamorphoses into a young sponge." to "அது படிந்து, புரட்டலுக்கு உட்பட்டு, இளம் கடற்பஞ்சாக உருமாறுகிறது.",
                        "The young sponge grows into an adult with its canal system." to "இளம் கடற்பஞ்சு தன் கால்வாய் அமைப்புடன் வளர்ந்த உயிரியாகிறது.",
                    ),
                    bp("The sequence shows where each earlier idea fits. The current that feeds the sponge also brings the sperm; the totipotent cells give rise to the gametes; the choanocytes of the adult are the flagellated cells that were once on the outside of the larva. The reproduction of a sponge is therefore a continuation of its organisation and does not stand apart from it." to
                        "இந்த வரிசை முந்தைய கருத்துகள் ஒவ்வொன்றும் எங்கு பொருந்துகிறது என்பதைக் காட்டுகிறது. கடற்பஞ்சுக்கு உணவூட்டும் ஓட்டமே விந்தணுக்களையும் கொண்டு வருகிறது; முழுத்திறன் செல்கள் கேமீட்டுகளைத் தருகின்றன; முதிர்ந்த கடற்பஞ்சின் கோனோசைட்டுகள், ஒரு காலத்தில் லார்வாவின் வெளிப்புறத்தில் இருந்த கசையிழை செல்களே. எனவே கடற்பஞ்சின் இனப்பெருக்கம் அதன் அமைப்பின் தொடர்ச்சியே; அதிலிருந்து தனித்து நிற்பதல்ல."),
                    bp("The free-swimming larva is short-lived and does not feed in most species. It depends on the reserves in its own cells, and its choice of a surface for settlement determines where the adult will spend its life. A large part of the larvae never settle in a favourable place, which is why sponges produce them in great numbers." to
                        "சுதந்திரமாக நீந்தும் லார்வா குறுகிய ஆயுளுடையது; பெரும்பாலான இனங்களில் உண்பதில்லை. தன் செல்களின் கையிருப்பையே சார்ந்துள்ளது; படிவதற்கு அது தேர்ந்தெடுக்கும் பரப்பே, வளர்ந்த கடற்பஞ்சு தன் வாழ்நாளை எங்கு கழிக்கும் என்பதை முடிவு செய்கிறது. லார்வாக்களில் பெரும்பகுதி சாதகமான இடத்தில் படிவதே இல்லை; இதனால்தான் கடற்பஞ்சுகள் அவற்றைப் பெருந்தொகையில் உருவாக்குகின்றன."),
                )),
            bsec("answering", "Answering questions on sponge reproduction", "கடற்பஞ்சு இனப்பெருக்கம் குறித்த வினாக்களுக்கு விடையளித்தல்",
                blocks = listOf(
                    bp("An essay on reproduction in sponges is best organised in the order of this chapter: first the asexual methods, with the gemmule treated in some detail, then the sexual process from gamete to larva, then the settlement of the larva, and last the totipotency that makes the whole thing possible. A reader who sees that order will see that the answer has a plan." to
                        "கடற்பஞ்சு இனப்பெருக்கம் குறித்த கட்டுரையை இந்த அத்தியாயத்தின் வரிசையில் அமைப்பது சிறந்தது: முதலில் பாலிலா முறைகள், ஜெம்யூல் சற்று விரிவாக; பின்னர் கேமீட்டிலிருந்து லார்வா வரையிலான பாலின நிகழ்வு; பின் லார்வாவின் படிதல்; இறுதியில் அனைத்தையும் சாத்தியமாக்கும் முழுத்திறன். இந்த வரிசையைப் பார்க்கும் வாசகர் விடைக்குத் திட்டம் உண்டு என்பதைக் காண்பார்."),
                    bpoints("Points that earn marks", "மதிப்பெண் தரும் குறிப்புகள்",
                        "Say that the gemmule is asexual and is not a larva." to "ஜெம்யூல் பாலிலாது; அது லார்வா அல்ல எனக் கூறுக.",
                        "Name both larvae, amphiblastula and parenchymula, and give an example of the group for each." to "ஆம்ஃபிபிளாஸ்டுலா, பரன்கைமுலா ஆகிய இரு லார்வாக்களையும் பெயரிட்டு, ஒவ்வொன்றுக்கும் ஒரு குழு எடுத்துக்காட்டு தருக.",
                        "State that inversion brings the flagellated cells to the inside." to "புரட்டல் கசையிழை செல்களை உள்ளே கொண்டு வருகிறது எனக் கூறுக.",
                        "Mention the archaeocyte as the totipotent cell." to "ஆர்கியோசைட்டை முழுத்திறன் செல்லாகக் குறிப்பிடுக.",
                        "Do not give gemmules to marine Sycon as a routine method." to "கடல் சைக்கானுக்கு ஜெம்யூல்களை வழக்கமான முறையாகத் தர வேண்டாம்.",
                    ),
                    bnote("Draw a labelled figure", "பெயரிட்ட படம் வரைக",
                        "A labelled section of a gemmule, showing the coat, the micropyle and the mass of archaeocytes, is quick to draw and is usually rewarded. The amphiblastula should be drawn as a hollow ball with two kinds of cell." to
                            "உறை, மைக்ரோபைல், ஆர்கியோசைட் திரள் ஆகியவற்றைக் காட்டும் ஜெம்யூலின் பெயரிட்ட வெட்டுத்தோற்றம் விரைவாக வரையக்கூடியது; பொதுவாக மதிப்பெண் பெறும். ஆம்ஃபிபிளாஸ்டுலாவை இருவகை செல்களுடன் குழி உருண்டையாக வரைய வேண்டும்."),
                )),
        ),
        summary = listOf(
            "Sponges reproduce asexually, to stay where they are and survive bad seasons, and sexually, to disperse and recombine genes." to "கடற்பஞ்சுகள் இருக்கும் இடத்தில் நிலைத்து மோசமான பருவங்களைக் கடக்க பாலிலாவாகவும், பரவி மரபணுக்களை மறுசேர்க்க பாலினமாகவும் இனப்பெருக்கம் செய்கின்றன.",
            "Asexual methods are budding, fragmentation and gemmule formation." to "பாலிலா முறைகள் மொட்டுவிடுதல், துண்டாக்கம், ஜெம்யூல் உருவாக்கம்.",
            "A gemmule is a resistant packet of archaeocytes in a coat of spongin, typical of freshwater sponges; it is not a larva." to "ஜெம்யூல் ஸ்பாஞ்சின் உறையிலுள்ள ஆர்கியோசைட்டுகளின் எதிர்ப்புத்திறன் மிக்க கட்டு; நன்னீர்க் கடற்பஞ்சுகளின் பண்பு; அது லார்வா அல்ல.",
            "Eggs and sperm arise from archaeocytes or choanocytes; many sponges are hermaphrodite, with gametes formed at different times." to "முட்டைகளும் விந்தணுக்களும் ஆர்கியோசைட்டுகளிலிருந்து அல்லது கோனோசைட்டுகளிலிருந்து தோன்றுகின்றன; பல கடற்பஞ்சுகள் இருபால் உயிரிகள்; கேமீட்டுகள் வெவ்வேறு காலத்தில் உருவாகின்றன.",
            "Sperm are carried by the water current to another sponge and reach a retained egg, often through a carrier cell." to "விந்தணுக்கள் நீரோட்டத்தால் மற்றொரு கடற்பஞ்சுக்குச் சென்று, பெரும்பாலும் கடத்து செல் வழியாக, தக்கவைக்கப்பட்ட முட்டையை அடைகின்றன.",
            "The larva is an amphiblastula in calcareous sponges and a parenchymula in most demosponges." to "லார்வா சுண்ணாம்புக் கடற்பஞ்சுகளில் ஆம்ஃபிபிளாஸ்டுலா; பெரும்பாலான டெமோஸ்பாஞ்சுகளில் பரன்கைமுலா.",
            "Inversion brings the flagellated cells of the amphiblastula to the inside, where they become the choanocytes." to "புரட்டல் ஆம்ஃபிபிளாஸ்டுலாவின் கசையிழை செல்களை உள்ளே கொண்டு வருகிறது; அங்கு அவை கோனோசைட்டுகளாகின்றன.",
            "Totipotent archaeocytes underlie gemmules, gametes and regeneration." to "முழுத்திறன் ஆர்கியோசைட்டுகள் ஜெம்யூல்கள், கேமீட்டுகள், மீளுருவாக்கம் ஆகியவற்றின் அடிப்படை.",
        ),
        checks = listOf(
            bqc("Is a gemmule a larva?", "ஜெம்யூல் லார்வாவா?",
                "No. It is a resistant asexual packet of archaeocytes, formed without gametes.", "இல்லை. இது கேமீட்டுகள் இன்றி உருவாகும், ஆர்கியோசைட்டுகளின் எதிர்ப்புத்திறன் மிக்க பாலிலா கட்டு."),
            bqc("Name two sponge larvae.", "இரண்டு கடற்பஞ்சு லார்வாக்களைக் குறிப்பிடுக.",
                "Amphiblastula and parenchymula.", "ஆம்ஃபிபிளாஸ்டுலா, பரன்கைமுலா."),
            bqc("What is inversion?", "புரட்டல் என்றால் என்ன?",
                "The turning of the flagellated cells of the amphiblastula to the inside at settlement.", "படியும்போது ஆம்ஃபிபிளாஸ்டுலாவின் கசையிழை செல்கள் உள்நோக்கித் திரும்புதல்."),
            bqc("How is cross-fertilisation ensured in a hermaphrodite sponge?", "இருபால் உயிரிக் கடற்பஞ்சில் குறுக்குக் கருவுறுதல் எவ்வாறு உறுதியாகிறது?",
                "The two kinds of gamete are usually produced at different times, and the sperm are carried by the water to another sponge.", "இருவகை கேமீட்டுகளும் பொதுவாக வெவ்வேறு காலத்தில் உருவாகின்றன; விந்தணுக்கள் நீரால் மற்றொரு கடற்பஞ்சுக்குக் கொண்டு செல்லப்படுகின்றன."),
            bqc("Why can a fragment of a sponge grow into a new sponge?", "கடற்பஞ்சின் துண்டு ஏன் புதிய கடற்பஞ்சாக வளர முடிகிறது?",
                "Its totipotent archaeocytes and other cells can reorganise into a complete body.", "அதன் முழுத்திறன் ஆர்கியோசைட்டுகளும் பிற செல்களும் முழு உடலாக மறுசீரமைக்க முடியும்."),
        ),
    )
}
