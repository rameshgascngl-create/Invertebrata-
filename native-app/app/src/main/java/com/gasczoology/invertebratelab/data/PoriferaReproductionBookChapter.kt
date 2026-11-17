package com.gasczoology.invertebratelab.data

/** Book-style chapter: reproduction in Porifera. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PoriferaReproductionBookChapter {
    val chapter = bchapter(
        lessonId = "u2-reproduction",
        title = "Reproduction in sponges" to "கடற்பஞ்சுகளில் இனப்பெருக்கம்",
        objectives = listOf(
            "Distinguish asexual and sexual reproduction in sponges." to
                "கடற்பஞ்சுகளின் பாலிலா, பாலியல் இனப்பெருக்க முறைகளை வேறுபடுத்துதல்.",
            "Explain fertilisation and representative larval stages." to
                "கருவுறுதலையும் முக்கிய லார்வா நிலைகளையும் விளக்குதல்.",
            "Relate dispersal and settlement to the life cycle." to
                "பரவலையும் நிலைகொள்ளுதலையும் வாழ்க்கைச் சுழற்சியுடன் தொடர்புபடுத்துதல்.",
        ),
        introduction = listOf(
            "Sponges reproduce both asexually and sexually. Their larvae disperse, settle and develop into attached adults; the details vary among sponge groups." to
                "கடற்பஞ்சுகள் பாலிலா முறையிலும் பாலியல் முறையிலும் இனப்பெருக்கம் செய்கின்றன. லார்வாக்கள் பரவி, பொருத்தமான இடத்தில் நிலைகொண்டு வளர்கின்றன; வளர்ச்சி நிலைகள் வகைகளுக்கு ஏற்ப மாறுபடும்.",
        ),
        sections = listOf(
            bsec("overview", "Two ways of reproducing", "இனப்பெருக்கத்தின் இரு வழிகள்",
                blocks = listOf(
                    bp("Sponges are sessile as adults. A fixed animal cannot go to find a mate, and it cannot move away when conditions turn against it. Both difficulties are met by the reproductive habits of the phylum. The water current that serves feeding also serves mating, since sperm are released into the water and are drawn into the canals of other sponges. And the cells of a sponge are so flexible that a small piece, or a packet of cells in a protective case, can grow into a whole animal." to
                        "வளர்ந்த கடற்பஞ்சுகள் ஒட்டி வாழ்பவை. நிலைத்த விலங்கு இணையைத் தேடிச் செல்ல முடியாது; சூழல் எதிராக மாறும்போது நகர்ந்து செல்லவும் முடியாது. இவ்விரு இடர்களையும் தொகுதியின் இனப்பெருக்கப் பழக்கங்கள் சமாளிக்கின்றன. உணவூட்டத்திற்கு உதவும் நீரோட்டமே பாலின இனப்பெருக்கத்திற்கும் துணைபுரிகிறது. நீரில் வெளியிடப்படும் விந்தணுக்கள் நீரோட்டத்தால் மற்றொரு கடற்பஞ்சின் உட்செல்லும் துளைகள் வழியாக அதன் கால்வாய்களை அடைகின்றன. மேலும் கடற்பஞ்சின் செல்கள் மிக நெகிழ்வானவை; சிறு துண்டோ பாதுகாப்பு உறையுடன் கூடிய செல் கட்டோ முழு விலங்காக வளர முடியும்."),
                    btable("Asexual and sexual reproduction in sponges compared", "கடற்பஞ்சுகளில் பாலிலா, பாலியல் இனப்பெருக்கத்தின் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Asexual" to "பாலிலா", "Sexual" to "பாலியல்"),
                        listOf(
                            listOf("Gametes" to "கேமீட்டுகள்", "Not formed" to "உருவாவதில்லை", "Eggs and sperm formed" to "முட்டையும் விந்தணுவும் உருவாகின்றன"),
                            listOf("Methods" to "முறைகள்", "Budding, fragmentation, gemmules" to "மொட்டு விடுதல், துண்டாக்கம், ஜெம்யூல்கள்", "Fertilisation, larva, metamorphosis" to "கருவுறுதல், லார்வா, உருமாற்றம்"),
                            listOf("Offspring" to "சந்ததி", "Genetically identical clone" to "மரபணு அமைப்பில் ஒத்த நகல் உயிரி", "Genetically new individual" to "புதிய மரபணுச் சேர்க்கையைக் கொண்ட சந்ததி"),
                            listOf("Main role" to "முதன்மைப் பணி", "Stay in place, survive bad seasons" to "அதே வாழிடத்தில் நிலைத்திருத்தல்; பாதகமான பருவங்களிலும் உயிர்வாழ்தல்", "Disperse and recombine genes" to "பரவுதல், மரபணுக்களை மறுசேர்த்தல்"),
                        )),
                    bdef("Sessile", "ஒட்டி வாழும்",
                        "Attached to a surface and not able to move from place to place, as the adult sponge is." to
                            "வளர்ந்த கடற்பஞ்சைப்போல ஒரு பரப்பில் ஒட்டியிருந்து, இடம்விட்டு இடம் நகர இயலாத நிலை."),
                    bdef("Clone", "நகல் உயிரி (Clone)",
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
                        bdef("Budding", "மொட்டு விடுதல்",
                            "Asexual reproduction in which a new individual grows as an outgrowth of the parent." to
                                "பெற்றோரின் புடைப்பாக புதிய உயிரி வளரும் பாலிலா இனப்பெருக்கம்."),
                        bdef("Fragmentation", "துண்டாக்கம் (Fragmentation)",
                            "Asexual reproduction in which a broken piece of the parent grows into a new individual." to
                                "பெற்றோரிடமிருந்து உடைந்த துண்டு புதிய உயிரியாக வளரும் பாலிலா இனப்பெருக்கம்."),
                    ),
                    bsub("Gemmules", "ஜெம்யூல்கள்",
                        bp("The gemmule is the specialised resting body of the sponge. It is formed in the mesohyl of freshwater sponges, such as Spongilla, and in a few marine sponges, usually towards the end of the growing season. Archaeocytes gather in a mass, are loaded with food reserves, and are enclosed in a hard coat of spongin, often strengthened by spicules called gemmoscleres. The coat has a small opening, the micropyle." to
                            "ஜெம்யூல் என்பது பாதகமான சூழ்நிலைகளைத் தாங்கும் சிறப்பான ஓய்வுநிலை மொட்டு. ஸ்பாஞ்சில்லா போன்ற நன்னீர்க் கடற்பஞ்சுகளின் மீசோஹைலிலும் சில கடல் கடற்பஞ்சுகளிலும், பொதுவாக வளர்ச்சிப் பருவத்தின் இறுதியில் உருவாகிறது. ஆர்கியோசைட்டுகள் ஒரு திரளாகக் கூடி, சேமிப்பு உணவுப் பொருள்களைக் கொண்டவையாகி, ஸ்பாஞ்சினால் ஆன கடின உறையில் அடைக்கப்படுகின்றன; அது பெரும்பாலும் ஜெம்மோஸ்கிளீர் எனப்படும் முட்களால் வலுப்படுகிறது. உறையில் மைக்ரோபைல் என்ற சிறு திறப்பு உண்டு."),
                        bp("When the parent dies in winter or in drought, the gemmules remain alive, resisting cold and drying that would kill an ordinary sponge. When favourable conditions return, the cells emerge through the micropyle, settle, and organise themselves into a small sponge. In this way the population survives a season in which the adult sponges do not." to
                            "குளிர்காலத்திலோ வறட்சியிலோ தாய் இறந்தாலும் ஜெம்யூல்கள் உயிருடன் இருந்து, சாதாரண கடற்பஞ்சைக் கொல்லும் குளிரையும் உலர்வையும் தாங்குகின்றன. சாதகமான நிலை திரும்பும்போது செல்கள் மைக்ரோபைல் வழியாக வெளிவந்து, தங்கி, தம்மை சிறு கடற்பஞ்சாக ஒழுங்கமைக்கின்றன. இவ்வாறு முதிர்ந்த கடற்பஞ்சுகள் உயிர்வாழ முடியாத பருவத்திலும் அவற்றின் இனக்கூட்டம் தொடர்ந்து நிலைத்திருக்கிறது."),
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
                        bdef("Micropyle", "மைக்ரோபைல் (Micropyle)",
                            "The small opening in the coat of a gemmule through which the cells emerge." to
                                "ஜெம்யூலின் உறையிலுள்ள, செல்கள் வெளிவரும் சிறு திறப்பு."),
                        bnote("A gemmule is not a larva", "ஜெம்யூல் லார்வா அல்ல",
                            "A larva comes from a fertilised egg and swims; a gemmule is made without gametes, does not swim, and gives a clone of the parent. Writing that a gemmule is a larva is a recurring error in answers." to
                                "லார்வா கருவுற்ற முட்டையிலிருந்து தோன்றி நீந்துகிறது; ஜெம்யூல் கேமீட்டுகள் இன்றி உருவாகி, நீந்தாமல், தாயின் நகலைத் தருகிறது. ஜெம்யூலை லார்வா எனக் கூறுவது விடைகளில் தொடர்ந்து வரும் பிழை."),
                    ),
                )),
            bsec("sexual", "Sexual reproduction", "பாலியல் இனப்பெருக்கம்",
                subs = listOf(
                    bsub("Gametes and the sexes", "கேமீட்டுகளும் பாலினங்களும்",
                        bp("Sponges have no gonads. Eggs and sperm arise from cells of the mesohyl, usually from archaeocytes or from choanocytes, and they develop in the body of the sponge. Many sponges are hermaphrodite, producing both kinds of gamete, but usually at different times, so that self-fertilisation is avoided. Others have separate sexes. Sperm commonly appear in great numbers, and a mature sponge may release them in a cloud that can be seen from the surface." to
                            "கடற்பஞ்சுகளில் தனித்த இனச்சுரப்பிகள் (gonads) இல்லை. முட்டைகளும் விந்தணுக்களும் மீசோஹைல் செல்களிலிருந்து, பொதுவாக ஆர்கியோசைட்டுகளிலிருந்து அல்லது கோயனோசைட்டுகளிலிருந்து, தோன்றி கடற்பஞ்சின் உடலுக்குள் வளர்கின்றன. பல கடற்பஞ்சுகள் இருபால் உயிரிகள்; இருவகை கேமீட்டுகளையும் உருவாக்குகின்றன, ஆனால் பொதுவாக வெவ்வேறு காலத்தில்; இதனால் தன்கருவுறுதல் தவிர்க்கப்படுகிறது. வேறு சிலவற்றில் பாலினங்கள் தனித்தனி. விந்தணுக்கள் பொதுவாக பெருந்தொகையில் தோன்றுகின்றன; முதிர்ந்த கடற்பஞ்சு அவற்றை மேற்பரப்பிலிருந்து தெரியும் மேகமாக வெளியிடக்கூடும்."),
                        bdef("Hermaphrodite", "இருபால் உயிரி",
                            "An individual that produces both eggs and sperm, in a sponge usually at different times." to
                                "முட்டை, விந்தணு இரண்டையும் உருவாக்கும் உயிரி; கடற்பஞ்சில் பொதுவாக வெவ்வேறு காலத்தில்."),
                    ),
                    bsub("Fertilisation", "கருவுறுதல்",
                        bp("The sperm leave one sponge through its osculum and are carried by the water to another. They enter the second sponge through its ostia and are drawn into the canals by the current. In many sponges a choanocyte engulfs a sperm, loses its collar and flagellum, and, as a carrier cell, passes through the mesohyl to the egg, to which it delivers the sperm. The egg is retained in the body of the parent, and fertilisation is therefore internal." to
                            "விந்தணுக்கள் ஒரு கடற்பஞ்சின் ஆஸ்குலம் வழியாக வெளியேறி, நீரால் மற்றொன்றுக்குக் கொண்டு செல்லப்படுகின்றன. அவை இரண்டாவது கடற்பஞ்சின் ஆஸ்டியா வழியாக நுழைந்து ஓட்டத்தால் கால்வாய்களுக்குள் இழுக்கப்படுகின்றன. பல கடற்பஞ்சுகளில் ஒரு கோயனோசைட் விந்தணுவை விழுங்கி, காலரையும் கசையிழையையும் இழந்து, விந்தணுவைக் கடத்தும் செல்லாக மீசோஹைல் வழியாக முட்டையிடம் சென்று விந்தணுவை ஒப்படைக்கிறது. முட்டை தாயின் உடலில் தக்கவைக்கப்படுகிறது; எனவே கருவுறுதல் உட்புறமானது."),
                        bdef("Carrier cell", "கடத்து செல் (Carrier cell)",
                            "A choanocyte that has taken up a sperm and carries it through the mesohyl to an egg." to
                                "விந்தணுவை ஏற்று, மீசோஹைல் வழியாக முட்டையிடம் கொண்டு செல்லும் கோயனோசைட்."),
                    ),
                    bsub("Cleavage and the larva", "பிளவுறுதலும் லார்வாவும்",
                        bp("In many sponges, the zygote cleaves within the parent and develops into a swimming larva that is released through the canal system. Motile cilia on the larval surface propel it through water; these larval cilia should be distinguished from the flagellum of each adult choanocyte. The hollow amphiblastula of calcareous sponges such as Sycon has ciliated cells at one pole and larger non-ciliated granular cells at the other. The parenchymula of many demosponges is usually solid and has ciliated cells on much of its surface. Other larval types also occur among sponges." to
                            "பல கடற்பஞ்சுகளில் கருவுற்ற முட்டை தாயுயிரியின் உடலுக்குள் பிளவுற்று, நீந்தும் லார்வாவாக வளர்ந்து கால்வாய் அமைப்பு வழியாக வெளியேறுகிறது. லார்வாவின் மேற்பரப்பிலுள்ள அசையும் குறுஇழைகள் நீரில் நீந்த உதவுகின்றன; இவற்றை முதிர்ந்த கடற்பஞ்சின் ஒவ்வொரு கோயனோசைட்டிலும் உள்ள கசையிழையிலிருந்து வேறுபடுத்த வேண்டும். சைக்கான் போன்ற சுண்ணாம்புக் கடற்பஞ்சுகளின் குழியான ஆம்ஃபிபிளாஸ்டுலா லார்வாவில் ஒரு முனையில் குறுஇழையுடைய செல்களும் மறுமுனையில் பெரிய, குறுஇழையற்ற துகள் செல்களும் உள்ளன. பல டெமோஸ்பாஞ்சுகளில் காணப்படும் பரன்கைமுலா லார்வா பொதுவாகத் திண்மமானது; அதன் மேற்பரப்பின் பெரும்பகுதியில் குறுஇழையுடைய செல்கள் காணப்படுகின்றன. கடற்பஞ்சுகளில் வேறு லார்வா வகைகளும் உள்ளன."),
                        btable("Sponge larvae compared", "கடற்பஞ்சு லார்வாக்களின் ஒப்பீடு",
                            listOf("Feature" to "பண்பு", "Amphiblastula" to "ஆம்ஃபிபிளாஸ்டுலா", "Parenchymula" to "பரன்கைமுலா"),
                            listOf(
                                listOf("Found in" to "காணப்படுவது", "Calcareous sponges such as Sycon" to "சைக்கான் போன்ற சுண்ணாம்புக் கடற்பஞ்சுகள்", "Most demosponges" to "பெரும்பாலான டெமோஸ்பாஞ்சுகள்"),
                                listOf("Structure" to "அமைப்பு", "Hollow; ciliated cells at one pole, large non-ciliated granular cells at the other" to "குழியானது; ஒரு முனையில் குறுஇழையுடைய செல்கள், மறுமுனையில் பெரிய குறுஇழையற்ற துகள் செல்கள்", "Usually solid; ciliated cells on much of the outside, cells packed within" to "பொதுவாகத் திண்மமானது; வெளிப்புறத்தின் பெரும்பகுதியில் குறுஇழையுடைய செல்களும் உட்புறத்தில் நெருக்கமான செல்களும் உள்ளன"),
                                listOf("Settling" to "படிதல்", "Larval ciliated cells become internal during settlement and metamorphosis" to "படிதலும் உருமாற்றமும் நடைபெறும்போது லார்வாவின் குறுஇழையுடைய செல்கள் உட்புறத்திற்கு இடம்பெயர்கின்றன", "Some larval ciliated cells become internal during settlement" to "லார்வா படியும்போது சில குறுஇழையுடைய செல்கள் உட்புறத்திற்குச் செல்கின்றன"),
                            )),
                        bdef("Amphiblastula", "ஆம்ஃபிபிளாஸ்டுலா (Amphiblastula)",
                            "The free-swimming hollow larva of many calcareous sponges, with small ciliated cells at one pole and large non-ciliated granular cells at the other." to
                                "பல சுண்ணாம்புக் கடற்பஞ்சுகளில் காணப்படும், ஒரு முனையில் சிறிய குறுஇழையுடைய செல்களும் மறுமுனையில் பெரிய குறுஇழையற்ற துகள் செல்களும் கொண்ட, உட்புறக் குழியுடன் சுதந்திரமாக நீந்தும் லார்வா."),
                        bdef("Parenchymula", "பரன்கைமுலா (Parenchymula)",
                            "The usually solid larva of many demosponges, with motile cilia on much of its surface." to
                                "பல டெமோஸ்பாஞ்சுகளில் காணப்படும், பொதுவாகத் திண்மமானதும் மேற்பரப்பின் பெரும்பகுதியில் அசையும் குறுஇழைகளைக் கொண்டதுமான லார்வா."),
                    ),
                    bsub("Settlement and inversion", "படிதலும் புரட்டலும்",
                        bp("After a short swimming period, often lasting hours or days, a sponge larva settles on a suitable surface and undergoes metamorphosis. The site of attachment and the rearrangement of larval cells vary among species. In amphiblastula-bearing calcareous sponges, ciliated larval cells become internal during metamorphosis and contribute to the developing choanocyte population, while granular cells contribute to the outer covering. This post-settlement rearrangement should be distinguished from the embryonic inversion that exposes the cilia before the amphiblastula is released." to
                            "பொதுவாகச் சில மணி நேரங்களோ நாட்களோ நீந்திய பின்னர் கடற்பஞ்சு லார்வா ஏற்ற மேற்பரப்பில் படிந்து உருமாற்றம் அடைகிறது. ஒட்டிக்கொள்ளும் இடமும் லார்வா செல்கள் மறுசீரமைவதும் இனத்துக்கு ஏற்ப மாறுபடுகின்றன. ஆம்ஃபிபிளாஸ்டுலா கொண்ட சுண்ணாம்புக் கடற்பஞ்சுகளில் குறுஇழையுடைய லார்வா செல்கள் உருமாற்றத்தின்போது உட்புறம் சென்று வளரும் கோயனோசைட் செல்களுக்குப் பங்களிக்கின்றன; துகள் செல்கள் வெளிப்புற மூடிக்கு பங்களிக்கின்றன. படிதலுக்குப் பிந்தைய இம்மறுசீரமைப்பை, ஆம்ஃபிபிளாஸ்டுலா வெளியேறுவதற்கு முன்பே குறுஇழைகளை வெளிப்படுத்தும் கருவளர்ச்சிக் காலப் புரட்டலிலிருந்து வேறுபடுத்த வேண்டும்."),
                        bdef("Inversion", "புரட்டல் (Inversion)",
                            "In amphiblastula development, an embryonic rearrangement that turns the cell layer inside out and exposes the cilia before larval release; distinct from the later cell movements during settlement." to
                                "ஆம்ஃபிபிளாஸ்டுலா வளர்ச்சியின்போது, லார்வா வெளியேறுவதற்கு முன் செல் அடுக்கு உள்ளும் புறமும் மாறி குறுஇழைகளை வெளிப்புறத்தில் தோன்றச் செய்யும் கருவளர்ச்சி மறுசீரமைப்பு; இது படிதலின்போது பின்னர் நிகழும் செல் இடப்பெயர்ச்சியிலிருந்து வேறுபட்டது."),
                        bdef("Metamorphosis", "உருமாற்றம்",
                            "The change from a free-swimming larva to a sessile young sponge." to
                                "சுதந்திரமாக நீந்தும் லார்வா, ஒட்டி வாழும் இளம் கடற்பஞ்சாக மாறும் மாற்றம்."),
                        bnote("Inversion has a cause", "புரட்டலுக்குக் காரணம் உண்டு",
                            "In calcareous sponges with an amphiblastula, embryonic inversion exposes the cilia on the swimming larva. After settlement, larval cells rearrange and differentiate; ciliated cells can contribute to the internal choanocytes of the juvenile. These are distinct developmental events, and the exact sequence varies among sponge lineages." to
                                "ஆம்ஃபிபிளாஸ்டுலா கொண்ட சுண்ணாம்புக் கடற்பஞ்சுகளில் கருவளர்ச்சிக் காலப் புரட்டலால் நீந்தும் லார்வாவின் குறுஇழைகள் வெளிப்புறத்தில் தோன்றுகின்றன. படிதலுக்குப் பின்னர் லார்வா செல்கள் மறுசீரமைந்து வேறுபடுகின்றன; குறுஇழையுடைய செல்கள் இளம் கடற்பஞ்சின் உட்புறக் கோயனோசைட்டுகளாக உருவாகப் பங்களிக்கலாம். இவை வெவ்வேறு வளர்ச்சி நிகழ்வுகள்; அவற்றின் துல்லியமான வரிசை கடற்பஞ்சு மரபுக்குழுக்களுக்கிடையே மாறுபடும்."),
                    ),
                )),
            bsec("regeneration", "Regeneration and the archaeocyte", "மீளுருவாக்கமும் ஆர்கியோசைட்டும்",
                blocks = listOf(
                    bp("Both asexual and sexual reproduction depend on the same property of the sponge, the ability of its cells to change from one kind to another. The archaeocyte is totipotent: it can become a choanocyte, a pinacocyte, a sclerocyte, a spongocyte, or an egg or sperm. It is the cell that fills the gemmule, forms the gametes, and rebuilds a lost part of the body." to
                        "பாலிலா, பாலின இனப்பெருக்கங்கள் இரண்டும் கடற்பஞ்சின் ஒரே பண்பைச் சார்ந்துள்ளன: செல்கள் ஒரு வகையிலிருந்து மற்றொன்றாக மாறும் திறன். ஆர்கியோசைட் முழுத்திறன் உடையது: கோயனோசைட், பினாகோசைட், ஸ்கிளீரோசைட், ஸ்பாஞ்சோசைட் அல்லது முட்டை, விந்தணுவாக மாற முடியும். ஜெம்யூலை நிரப்புவதும், கேமீட்டுகளை உருவாக்குவதும், இழந்த உடல் பகுதியை மீண்டும் கட்டுவதும் இந்த செல்லே."),
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
                    bfig("lib/sponge-sexual-cycle",
                        "Sexual cycle of a calcareous sponge (schematic): (1) adult with eggs and sperm in the mesohyl, (2) sperm leave by the osculum, (3) a carrier cell brings a sperm to a retained egg, which is fertilised, (4) the zygote cleaves inside the parent, (5) the amphiblastula leaves and swims, (6) it settles and undergoes inversion, (7) the young sponge.",
                        "சுண்ணாம்புக் கடற்பஞ்சின் பாலினச் சுழற்சி (படத்திட்டம்): (1) மீசோஹைலில் முட்டைகளும் விந்தணுக்களும் உள்ள முதிர்ந்த பஞ்சு, (2) விந்தணுக்கள் ஆஸ்குலம் வழியாக வெளியேறுகின்றன, (3) கடத்தி செல் ஒரு விந்தணுவை தாயுயிரிக்குள் தங்கியுள்ள முட்டையிடம் கொண்டுவருகிறது, கருவுறுதல் நிகழ்கிறது, (4) சைகோட் தாய்ப்பஞ்சுக்குள் பிளவுறுகிறது, (5) ஆம்ஃபிபிளாஸ்டுலா வெளியேறி நீந்துகிறது, (6) அது படிந்து தலைகீழாக மாறுகிறது (இன்வெர்ஷன்), (7) இளம் பஞ்சு."),
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
                        "இந்த வரிசை முந்தைய கருத்துகள் ஒவ்வொன்றும் எங்கு பொருந்துகிறது என்பதைக் காட்டுகிறது. கடற்பஞ்சுக்கு உணவூட்டும் ஓட்டமே விந்தணுக்களையும் கொண்டு வருகிறது; முழுத்திறன் செல்கள் கேமீட்டுகளைத் தருகின்றன; முதிர்ந்த கடற்பஞ்சின் கோயனோசைட்டுகள், ஒரு காலத்தில் லார்வாவின் வெளிப்புறத்தில் இருந்த கசையிழை செல்களே. எனவே கடற்பஞ்சின் இனப்பெருக்கம் அதன் அமைப்பின் தொடர்ச்சியே; அதிலிருந்து தனித்து நிற்பதல்ல."),
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
            bsec("spawning", "Spawning, brooding and timing", "முட்டையிடுதல், அடைகாத்தல், நேரம்",
                blocks = listOf(
                    bp("Sponges differ in what they do with their eggs and sperm. In many species the sperm are released into the water through the osculum, sometimes in such numbers that a cloud is seen to rise from the sponge, and they are carried by the current to another individual, which draws them in with its feeding stream. The eggs may be released into the water as well and fertilised there, an habit called broadcast spawning, or they may be retained in the body of the parent and fertilised there, in which case the embryos develop within the sponge and are released later as larvae. This second habit is called brooding. The two habits are not shared by all sponges, and many textbooks describe the brooding type because it is the one most closely linked with the free-swimming larva that the chapter has discussed.",
                        "முட்டைகளையும் விந்தணுக்களையும் கடற்பஞ்சுகள் எவ்வாறு கையாளுகின்றன என்பதில் வேறுபடுகின்றன. பல இனங்களில் விந்தணுக்கள் ஆஸ்குலம் வழியாக நீரில் விடுவிக்கப்படுகின்றன; சில சமயம் கடற்பஞ்சிலிருந்து ஒரு மேகம் எழுவது காணப்படும் அளவு மிகுதியாக; அவை நீரோட்டத்தால் மற்றொரு உயிரிக்குக் கொண்டு செல்லப்பட்டு, அதன் உணவு நீரோட்டத்துடன் இழுக்கப்படுகின்றன. முட்டைகளும் நீரில் விடுவிக்கப்பட்டு அங்கு கருவுறலாம்; இம்முறை நீரில் இனச்செல்களை வெளியிட்டுக் கருவுறுதல் (broadcast spawning) எனப்படுகிறது; அல்லது பெற்றோர் உடலில் தக்கவைக்கப்பட்டு அங்கு கருவுறலாம்; அப்போது கருக்கள் கடற்பஞ்சுக்குள் வளர்ந்து பின்னர் லார்வாக்களாக விடுவிக்கப்படுகின்றன. இரண்டாவது முறை கருக்களை உடலுக்குள் தக்கவைத்து வளர்த்தல் (brooding) எனப்படுகிறது. எல்லாக் கடற்பஞ்சுகளும் இவ்விரு பழக்கங்களையும் பகிர்வதில்லை; அத்தியாயம் விவாதித்த தனி நீந்தும் லார்வாவுடன் மிக நெருக்கமாகத் தொடர்புடையதாக இருப்பதால் பல பாடநூல்கள் அடைகாக்கும் வகையை விவரிக்கின்றன."),
                    bp("The timing of reproduction is often seasonal. Many sponges release gametes or larvae at a particular time of year, and in some it is reported that the release of sperm in one individual is followed by the release in neighbours, a coordination that helps to ensure that sperm meet eggs in water that is otherwise dilute. The cues that are suggested include temperature and day length, but the evidence differs from species to species and the matter is still studied, so a statement in an answer should be restricted to the observation that reproduction is seasonal in many sponges. Freshwater sponges show a different seasonal pattern: they form gemmules in autumn, and the gemmules over-winter and hatch when the weather improves, which links the timing of reproduction to the survival of the species through a bad season.",
                        "இனப்பெருக்க நேரம் பெரும்பாலும் பருவகாலம் சார்ந்தது. பல கடற்பஞ்சுகள் ஆண்டின் குறிப்பிட்ட நேரத்தில் கேமீட்டுகளையோ லார்வாக்களையோ விடுவிக்கின்றன; சிலவற்றில் ஓர் உயிரியில் விந்தணு வெளியீட்டைத் தொடர்ந்து அண்டை உயிரிகளில் வெளியீடு நிகழ்வதாகக் கூறப்படுகிறது; இந்த ஒருங்கிணைப்பு, இல்லாவிடில் நீர்த்துப்போன நீரில் விந்தணுக்கள் முட்டைகளைச் சந்திப்பதை உறுதிசெய்ய உதவுகிறது. இதனைத் தூண்டும் காரணிகளாக வெப்பநிலையும் பகல் நேரத்தின் நீளமும் கருதப்படுகின்றன; ஆனால் சான்று இனத்துக்கு இனம் மாறுபடுகிறது; விஷயம் இன்னும் ஆய்வில் உள்ளது; எனவே விடையில் கூற்று பல கடற்பஞ்சுகளில் இனப்பெருக்கம் பருவகாலம் சார்ந்தது என்ற கவனிப்புக்குள் மட்டுப்பட வேண்டும். நன்னீர்க் கடற்பஞ்சுகள் வேறு பருவ முறையைக் காட்டுகின்றன: இலையுதிர் காலத்தில் ஜெம்யூல்களை உருவாக்குகின்றன; அவை குளிர்காலம் கடந்து வானிலை சீராகும்போது பொரிகின்றன; இது இனப்பெருக்க நேரத்தை மோசமான பருவத்தில் இனத்தின் உயிர்வாழ்வுடன் இணைக்கிறது."),
                    bdef("Broadcast spawning", "இனச்செல்களை நீரில் வெளியிடுதல் (Broadcast spawning)", "The release of eggs and sperm into the surrounding water, where fertilisation takes place." to
                        "முட்டைகளையும் விந்தணுக்களையும் சுற்றியுள்ள நீரில் வெளியிடுவதால், நீரிலேயே கருவுறுதல் நிகழும் முறை."),
                    bdef("Brooding", "அடைகாத்தல்", "The retention of fertilised eggs or embryos in the body of the parent until they are released as larvae." to
                        "கருவுற்ற முட்டைகள் அல்லது கருக்கள் லார்வாக்களாக விடுவிக்கப்படும் வரை பெற்றோர் உடலில் தக்கவைத்தல்."),
                )),
            bsec("strategy", "Why two modes? The value of dispersal and of cloning", "இரு முறைகள் ஏன்? பரவலின், நகல் உருவாக்கலின் மதிப்பு",
                blocks = listOf(
                    bp("A sponge cannot move, and the two modes of reproduction solve two different problems. Sexual reproduction produces a larva that swims for a short period, often a day or a few days, and then settles. The larva therefore carries the species to a new place, which is the only chance a sessile animal has of colonising fresh ground, and the mixing of genes in fertilisation produces offspring that differ from each other and from their parents. Asexual reproduction, by budding, by fragmentation and by gemmules, produces copies of the parent at or close to the same place, which is of value when conditions there are known to be suitable, and it gives the species a way of surviving harsh conditions in the form of gemmules. A good answer states that the first mode gains variety and distance and the second gains reliability and endurance.",
                        "கடற்பஞ்சு நகர முடியாது; பாலின மற்றும் பாலிலா இனப்பெருக்க முறைகள் வெவ்வேறு உயிரியல் நன்மைகளை அளிக்கின்றன. பாலின இனப்பெருக்கம் குறுகிய காலம், பெரும்பாலும் ஒரு நாள் அல்லது சில நாள், நீந்தி பின் ஒட்டிக்கொள்ளும் லார்வாவை உருவாக்குகிறது. எனவே லார்வா இனத்தை புதிய இடத்துக்குக் கொண்டு செல்கிறது; புதிய இடங்களில் குடியேறுவதற்கான முக்கியமான வழிகளில் இதுவும் ஒன்று; கருவுறுதலில் மரபணுக்கள் கலப்பதால் ஒன்றுக்கொன்றும் பெற்றோரிலிருந்தும் வேறுபடும் சந்ததிகள் உருவாகின்றன. மொட்டு விடுதல், துண்டாக்கம், ஜெம்யூல்கள் வழி பாலிலா இனப்பெருக்கம், பெற்றோரின் இடத்திலோ அருகிலோ நகல்களை உருவாக்குகிறது; அங்குள்ள நிலைமைகள் பொருத்தமானவை என அறியப்படும்போது இது மதிப்புள்ளது; ஜெம்யூல் வடிவில் கடுமையான நிலைமைகளில் உயிர்வாழும் வழியையும் இனத்துக்குத் தருகிறது. பாலின இனப்பெருக்கம் மரபியல் வேறுபாட்டையும் லார்வாக்கள் வழியாகப் புதிய இடங்களுக்குப் பரவலையும் ஏற்படுத்துகிறது; பாலிலா இனப்பெருக்கம் ஏற்கெனவே ஏற்ற இடத்தில் இனக்கூட்டம் நிலைத்திருக்க உதவுகிறது; ஜெம்யூல்கள் பாதகமான சூழலைத் தாங்கவும் உதவுகின்றன என்பதை நல்ல விடை கூறுகிறது."),
                    bp("The two modes also have a cost. The larva faces many dangers, most larvae die before they settle, and a parent therefore produces many more than will survive; this is the high output with a low rate of success that is typical of species with a swimming larva. A bud or a fragment starts life with the size and the tissues of a piece of the parent and has a far higher chance of survival, but a population that arises by cloning has little genetic variety, so that a change in the environment, for example a new disease, may affect all of its members. An examiner who sets a question on the advantages and the disadvantages of each mode is usually looking for these paired statements. Lists of advantages are acceptable, but the pairs of advantage and cost show understanding more clearly.",
                        "இரு முறைகளிலும் சில பாதகங்களும் உள்ளன. லார்வா பல ஆபத்துகளை எதிர்கொள்கிறது; பெரும்பாலான லார்வாக்கள் ஒட்டுவதற்கு முன் இறக்கின்றன; எனவே பெற்றோர் உயிர்வாழ்வதைவிட அதிகமானவற்றை உருவாக்குகிறது; நீந்தும் லார்வா உடைய இனங்களுக்கே உரிய, குறைந்த உயிர்வாழ்வு விகிதத்தை ஈடுசெய்ய அதிக எண்ணிக்கையில் இளவுயிரிகள் உருவாக்கப்படுகின்றன. ஒரு மொட்டு அல்லது துண்டு பெற்றோரின் ஒரு பகுதியின் அளவு, திசுக்களுடன் வாழ்வைத் தொடங்கி, மிக அதிக உயிர்வாழும் வாய்ப்பைப் பெறுகிறது; ஆனால் நகல் உருவாக்கலால் எழும் எண்ணிக்கையில் மரபியல் பன்முகத்தன்மை குறைவு; எனவே சூழலில் ஒரு மாற்றம், எடுத்துக்காட்டாக புதிய நோய், அனைத்து உறுப்பினர்களையும் பாதிக்கலாம். ஒவ்வொரு முறையின் நன்மை, தீமைகள் பற்றி வினா அமைக்கும் தேர்வாளர் பொதுவாக இந்த இணைக் கூற்றுகளையே தேடுகிறார். நன்மைகளின் பட்டியல் ஏற்கத்தக்கது; ஆனால் நன்மையையும் அதனுடன் தொடர்புடைய பாதகத்தையும் இணைத்துக் கூறுவது கருத்தைப் புரிந்திருப்பதைத் தெளிவாகக் காட்டுகிறது."),
                    bdef("Genetic variety", "மரபியல் பன்முகத்தன்மை (Genetic variety)", "The differences in genes among the individuals of a population, produced by sexual reproduction." to
                        "பாலின இனப்பெருக்கத்தால் உருவாகும், ஒரு உயிர்த்தொகையில் உள்ள தனி உயிரிகளுக்கிடையேயான மரபணு வேறுபாடுகள்."),
                )),
        ),
        summary = listOf(
            "Sponges reproduce asexually, to stay where they are and survive bad seasons, and sexually, to disperse and recombine genes." to "கடற்பஞ்சுகள் தற்போதைய வாழிடத்தில் இனக்கூட்டம் நிலைத்திருக்கவும் பாதகமான பருவங்களைத் தாங்கவும் பாலிலா முறையிலும், புதிய இடங்களுக்குப் பரவி மரபணுக்களை மறுசேர்க்கப் பாலியல் முறையிலும் இனப்பெருக்கம் செய்கின்றன.",
            "Asexual methods are budding, fragmentation and gemmule formation." to "பாலிலா முறைகள் மொட்டு விடுதல், துண்டாக்கம், ஜெம்யூல் உருவாக்கம்.",
            "A gemmule is a resistant packet of archaeocytes in a coat of spongin, typical of freshwater sponges; it is not a larva." to "ஜெம்யூல் ஸ்பாஞ்சின் உறையிலுள்ள ஆர்கியோசைட்டுகளின் எதிர்ப்புத்திறன் மிக்க கட்டு; நன்னீர்க் கடற்பஞ்சுகளின் பண்பு; அது லார்வா அல்ல.",
            "Eggs and sperm arise from archaeocytes or choanocytes; many sponges are hermaphrodite, with gametes formed at different times." to "முட்டைகளும் விந்தணுக்களும் ஆர்கியோசைட்டுகளிலிருந்து அல்லது கோயனோசைட்டுகளிலிருந்து தோன்றுகின்றன; பல கடற்பஞ்சுகள் இருபால் உயிரிகள்; கேமீட்டுகள் வெவ்வேறு காலத்தில் உருவாகின்றன.",
            "Sperm are carried by the water current to another sponge and reach a retained egg, often through a carrier cell." to "விந்தணுக்கள் நீரோட்டத்தால் மற்றொரு கடற்பஞ்சுக்குச் சென்று, பெரும்பாலும் கடத்து செல் வழியாக, தக்கவைக்கப்பட்ட முட்டையை அடைகின்றன.",
            "The larva is an amphiblastula in calcareous sponges and a parenchymula in most demosponges." to "லார்வா சுண்ணாம்புக் கடற்பஞ்சுகளில் ஆம்ஃபிபிளாஸ்டுலா; பெரும்பாலான டெமோஸ்பாஞ்சுகளில் பரன்கைமுலா.",
            "Inversion brings the flagellated cells of the amphiblastula to the inside, where they become the choanocytes." to "புரட்டல் ஆம்ஃபிபிளாஸ்டுலாவின் கசையிழை செல்களை உள்ளே கொண்டு வருகிறது; அங்கு அவை கோயனோசைட்டுகளாகின்றன.",
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
