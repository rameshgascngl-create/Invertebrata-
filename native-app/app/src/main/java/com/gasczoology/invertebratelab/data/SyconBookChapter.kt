package com.gasczoology.invertebratelab.data

/**
 * PILOT book-style chapter: Sycon (Scypha). DRAFT_UNVERIFIED.
 * English follows standard invertebrate-zoology textbooks; the Tamil is a
 * working draft and needs the author's terminology review. Nothing here has
 * been checked by a subject expert. The A5 exam notes are not altered.
 */
object SyconBookChapter {
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    private fun p(en: String, ta: String) = BookParagraph(b(en, ta))
    private fun def(termEn: String, termTa: String, en: String, ta: String) =
        BookDefinition(b(termEn, termTa), b(en, ta))

    val chapter = BookChapter(
        lessonId = "u2-sycon",
        title = b("Sycon (Scypha): a syconoid calcareous sponge",
            "சைக்கான் (ஸ்கைஃபா): சைக்கனாய்டு சுண்ணாம்புக் கடற்பஞ்சு"),
        objectives = listOf(
            b("Place Sycon in the classification of Porifera and state why it is the type study of the class Calcarea.",
                "போரிஃபெராவின் வகைப்பாட்டில் சைக்கானின் இடத்தைக் கூறி, அது கால்கேரியா வகுப்பின் இன ஆய்வாகக் கொள்ளப்படுவதற்கான காரணத்தை எழுத."),
            b("Describe the external form and identify the osculum, the ostia and the fringe of spicules.",
                "வெளிவடிவத்தை விவரித்து ஆஸ்குலம், ஆஸ்டியா, ஊசி விளிம்பு ஆகியவற்றை அடையாளம் காண."),
            b("Distinguish the layers and cell types of the body wall and state the function of each.",
                "உடற்சுவரின் அடுக்குகளையும் செல் வகைகளையும் வேறுபடுத்தி ஒவ்வொன்றின் பணியையும் கூற."),
            b("Trace the path of water through the syconoid canal system without confusing prosopyles and apopyles.",
                "புரோசோபைலையும் அப்போபைலையும் குழப்பாமல் சைக்கனாய்டு கால்வாய் அமைப்பில் நீர் செல்லும் பாதையை எழுத."),
            b("Explain how feeding, respiration and excretion are carried out by an animal without a gut, blood or nervous system.",
                "குடல், இரத்தம், நரம்புமண்டலம் இல்லாத விலங்கில் உணவூட்டம், சுவாசம், கழிவு நீக்கம் எவ்வாறு நடைபெறுகின்றன என்பதை விளக்க."),
            b("Describe asexual and sexual reproduction, including the inversion of the stomoblastula to form the amphiblastula.",
                "பாலிலா, பாலின இனப்பெருக்கத்தையும் ஸ்டோமோபிளாஸ்டுலா திரும்பி ஆம்ஃபிபிளாஸ்டுலா ஆகும் நிகழ்வையும் விளக்க."),
        ),
        introduction = listOf(
            b("Sponges are the simplest multicellular animals in the usual teaching sequence, and Sycon is the standard laboratory type for the class Calcarea. A student who understands Sycon understands the central idea of the whole phylum: an animal built around a stream of water that is drawn through the body wall, filtered and expelled. There is no mouth, no gut and no true organ, yet the sponge feeds, respires, excretes, reproduces and repairs itself.",
                "கடற்பஞ்சுகள் வழக்கமான கற்பித்தல் வரிசையில் எளிய பலசெல் விலங்குகள்; கால்கேரியா வகுப்பின் செய்முறை இனமாக சைக்கான் கொள்ளப்படுகிறது. சைக்கானைப் புரிந்துகொண்டால் முழுத் தொகுதியின் மையக் கருத்தும் புரியும்: உடற்சுவர் வழியாக நீரை இழுத்து, வடிகட்டி, வெளியேற்றுவதை அடிப்படையாகக் கொண்ட உடலமைப்பு. வாயோ குடலோ உண்மையான உறுப்புகளோ இல்லாவிட்டாலும் கடற்பஞ்சு உண்கிறது, சுவாசிக்கிறது, கழிவை நீக்குகிறது, இனப்பெருக்கம் செய்கிறது, சேதத்தைச் சீரமைத்துக் கொள்கிறது."),
            b("This chapter follows the order of a printed practical record: position in the classification, external features, body wall, canal system, life processes and, finally, reproduction and development. Figures and tables are numbered in the order in which they are discussed, and every technical term is defined in a box so that it can be learned exactly.",
                "இந்த அத்தியாயம் அச்சிடப்பட்ட செய்முறை நூலின் வரிசையைப் பின்பற்றுகிறது: வகைப்பாட்டில் இடம், வெளியமைப்பு, உடற்சுவர், கால்வாய் அமைப்பு, உயிர்ச்செயல்கள், இறுதியாக இனப்பெருக்கமும் வளர்ச்சியும். படங்களும் அட்டவணைகளும் விவாதிக்கப்படும் வரிசையில் எண்ணிடப்பட்டுள்ளன; ஒவ்வொரு கலைச்சொல்லும் பெட்டியில் வரையறுக்கப்பட்டுள்ளது."),
        ),
        sections = listOf(
            BookSection(
                id = "position-habitat",
                heading = b("Systematic position and habitat", "வகைப்பாட்டில் இடமும் வாழிடமும்"),
                blocks = emptyList(),
                subsections = listOf(
                    BookSubsection(b("Classification", "வகைப்பாடு"), listOf(
                        BookTable(
                            b("Position of Sycon in the animal kingdom", "விலங்கு உலகில் சைக்கானின் இடம்"),
                            listOf(b("Rank", "நிலை"), b("Taxon", "பிரிவு")),
                            listOf(
                                listOf(b("Kingdom", "திணை"), b("Animalia", "அனிமேலியா (விலங்குகள்)")),
                                listOf(b("Phylum", "தொகுதி"), b("Porifera", "போரிஃபெரா")),
                                listOf(b("Class", "வகுப்பு"), b("Calcarea (Calcispongiae)", "கால்கேரியா (சுண்ணாம்புக் கடற்பஞ்சுகள்)")),
                                listOf(b("Order", "வரிசை"), b("Sycettida (called Heterocoela in older books)", "சைசெட்டிடா (பழைய நூல்களில் ஹெட்டிரோசீலா)")),
                                listOf(b("Genus", "பேரினம்"), b("Sycon (Scypha in many older textbooks)", "சைக்கான் (பல பழைய பாடநூல்களில் ஸ்கைஃபா)")),
                            )),
                        def("Porifera", "போரிஃபெரா",
                            "The phylum of aquatic, mostly marine and sessile animals whose body is perforated by pores and traversed by a canal system through which water circulates. The cells are arranged in layers but do not form true organs.",
                            "துளைகள் நிறைந்த உடலும் நீர் சுற்றும் கால்வாய் அமைப்பும் கொண்ட, பெரும்பாலும் கடல்வாழ், நிலைத்த விலங்குகளின் தொகுதி. செல்கள் அடுக்குகளாக அமைந்திருக்கும்; ஆனால் உண்மையான உறுப்புகளை உருவாக்குவதில்லை."),
                        def("Calcarea", "கால்கேரியா",
                            "The class of sponges whose skeleton consists of separate spicules of calcium carbonate (calcite). The spicules are rod-like, three-rayed or four-rayed, unlike the siliceous spicules of other sponge classes.",
                            "கால்சியம் கார்பனேட்டால் (கால்சைட்) ஆன தனித்தனி ஊசிகளை எலும்புக்கூடாகக் கொண்ட கடற்பஞ்சு வகுப்பு. இதன் ஊசிகள் கோல்வடிவம், மூன்று கதிர் அல்லது நான்கு கதிர் உடையவை; மற்ற வகுப்புகளின் சிலிக்கா ஊசிகளிலிருந்து வேறுபடுகின்றன."),
                        p("Older textbooks use the generic name Scypha, whereas current databases list the animal as Sycon. For examination purposes the two names refer to the same type study; write the name prescribed by your university and give the other in brackets.",
                            "பழைய பாடநூல்கள் ஸ்கைஃபா என்ற பேரினப் பெயரைப் பயன்படுத்துகின்றன; தற்கால தரவுத்தளங்கள் சைக்கான் எனப் பட்டியலிடுகின்றன. தேர்வு நோக்கில் இரண்டு பெயர்களும் ஒரே இன ஆய்வைக் குறிக்கும்; பல்கலைக்கழகம் குறிப்பிடும் பெயரை எழுதி, மற்றொன்றை அடைப்புக்குறிக்குள் தருக."),
                    )),
                    BookSubsection(b("Habitat and habit", "வாழிடமும் வாழ்க்கைமுறையும்"), listOf(
                        p("Sycon is a marine animal of shallow coastal water. It is attached to rocks, shells, seaweeds and other firm surfaces, and occurs singly or in small groups. Individuals are usually a few centimetres long and white, cream or greyish in colour. The adult does not move from place to place; only the larva swims.",
                            "சைக்கான் ஆழமற்ற கடலோர நீரில் வாழும் கடல்வாழ் விலங்கு. பாறைகள், ஓடுகள், கடற்பாசிகள் போன்ற உறுதியான பரப்புகளில் ஒட்டி, தனியாகவோ சிறு கூட்டங்களாகவோ காணப்படுகிறது. பொதுவாக சில சென்டிமீட்டர் நீளமும் வெண்மை, கிரீம் அல்லது சாம்பல் நிறமும் கொண்டவை. முதிர்ந்த உயிரி இடம்விட்டு இடம் நகர்வதில்லை; இளவுயிரி மட்டுமே நீந்துகிறது."),
                        def("Sessile", "நிலைத்த (ஒட்டிவாழ்)",
                            "Permanently attached to a surface and unable to move from place to place in the adult stage.",
                            "முதிர்நிலையில் ஒரு பரப்பில் நிரந்தரமாக ஒட்டிக்கொண்டு இடம்பெயர இயலாத நிலை."),
                    )),
                ),
            ),
            BookSection(
                id = "external-features",
                heading = b("External features", "வெளியமைப்பு"),
                blocks = listOf(
                    p("The body is cylindrical, spindle-shaped or vase-like. It is fixed at the lower, basal end and free at the upper, apical end. Because the body is organised around a central axis that runs from the base to the apex, it is described as radially symmetrical.",
                        "உடல் உருளை, கதிர் அல்லது குவளை வடிவில் இருக்கும். கீழ் அடிமுனை ஒட்டியிருக்கும்; மேல் நுனி தனித்திருக்கும். அடியிலிருந்து நுனி வரை செல்லும் நடு அச்சைச் சுற்றி உடல் அமைந்திருப்பதால் கதிர்ச் சமச்சீர் உடையதாகக் கூறப்படுகிறது."),
                    BookFigure("sycon/external",
                        b("External view of Sycon attached to a surface. (Photograph; the species is to be confirmed, see the credit line.)",
                            "பரப்பில் ஒட்டியுள்ள சைக்கானின் வெளித்தோற்றம். (ஒளிப்படம்; இனம் உறுதிப்படுத்தப்பட வேண்டும், மூலக் குறிப்பைப் பார்க்க.)")),
                    p("The apex carries one large opening, the osculum, which is surrounded by a fringe of long, slender spicules that project like a crown. The remaining surface looks bristly because short spicules project from it. The very small inhalant pores are hard to see in a whole-animal view and are usually shown as dots in diagrams.",
                        "நுனியில் ஆஸ்குலம் என்ற ஒரு பெரிய துளை உள்ளது; அதைச் சுற்றி நீண்ட மெல்லிய ஊசிகள் மகுடம்போல் நீட்டிக் கொண்டிருக்கும். மீதமுள்ள மேற்பரப்பு குட்டையான ஊசிகள் நீட்டிக் கொண்டிருப்பதால் முட்கள் போலத் தோன்றும். மிகச் சிறிய உட்செல்லும் துளைகளை முழு உடலின் தோற்றத்தில் காண்பது கடினம்; வரைபடங்களில் அவை பொதுவாகப் புள்ளிகளாகக் காட்டப்படும்."),
                    def("Osculum", "ஆஸ்குலம்",
                        "The single, large exhalant opening at the apex of the sponge, through which filtered water leaves the central cavity.",
                        "கடற்பஞ்சின் நுனியில் உள்ள ஒரே பெரிய வெளிச்செல்லும் துளை; வடிகட்டப்பட்ட நீர் நடுக்குழியிலிருந்து இதன் வழியாக வெளியேறுகிறது."),
                    def("Ostium (plural: ostia)", "ஆஸ்டியம் (பன்மை: ஆஸ்டியா)",
                        "A minute inhalant pore in the outer body wall through which water enters an incurrent canal.",
                        "புறச்சுவரில் உள்ள நுண்ணிய உட்செல்லும் துளை; இதன் வழியாக நீர் உட்செல்லும் கால்வாய்க்குள் நுழைகிறது."),
                    BookNote(b("Do not confuse", "குழப்பிக்கொள்ள வேண்டாம்"),
                        b("The large opening at the apex is the osculum. It must never be labelled an ostium. Ostia are numerous and microscopic.",
                            "நுனியிலுள்ள பெரிய துளை ஆஸ்குலம்; அதை ஆஸ்டியம் எனக் குறிக்கக் கூடாது. ஆஸ்டியா எண்ணற்றவை, நுண்ணியவை.")),
                ),
            ),
            BookSection(
                id = "body-wall",
                heading = b("Body wall and cell types", "உடற்சுவரும் செல் வகைகளும்"),
                blocks = listOf(
                    p("The wall of Sycon is thick and folded. In a transverse section it is not a plain tube but a series of alternating incurrent and radial canals. Three layers can be distinguished: an outer layer of flat cells, a middle gelatinous layer, and an inner layer of flagellated collar cells.",
                        "சைக்கானின் உடற்சுவர் தடித்தும் மடிந்தும் உள்ளது. குறுக்குவெட்டில் இது சாதாரணக் குழாயாக இல்லாமல், மாறிமாறி அமைந்த உட்செல்லும் கால்வாய்களும் ஆரக் கால்வாய்களுமாகத் தெரியும். மூன்று அடுக்குகள் உள்ளன: தட்டையான செல்களாலான வெளி அடுக்கு, ஜெல் போன்ற நடு அடுக்கு, கசையிழையும் காலரும் உடைய செல்களாலான உள் அடுக்கு."),
                ),
                subsections = listOf(
                    BookSubsection(b("The outer layer: pinacoderm", "வெளி அடுக்கு: பினாகோடெர்ம்"), listOf(
                        p("The outer surface is covered by a single layer of flat cells, the pinacocytes. The same kind of cell lines the incurrent canals and the central cavity. Among them lie porocytes, tube-like cells pierced by a canal that runs through the cell itself; in textbook accounts the porocytes form the ostia and the small pores that connect the canals.",
                            "வெளிப்பரப்பு பினாகோசைட் எனப்படும் தட்டையான செல்களின் ஒற்றை அடுக்கால் மூடப்பட்டுள்ளது. உட்செல்லும் கால்வாய்களையும் நடுக்குழியையும் இதே வகை செல்கள் வரிசையாக மூடுகின்றன. இவற்றிடையே போரோசைட்டுகள் உள்ளன; இவை செல்லின் உள்ளேயே ஒரு கால்வாய் ஊடுருவிச் செல்லும் குழாய்போன்ற செல்கள். பாடநூல் விளக்கங்களில் ஆஸ்டியாவையும் கால்வாய்களை இணைக்கும் சிறு துளைகளையும் போரோசைட்டுகள் உருவாக்குகின்றன."),
                        def("Pinacocyte", "பினாகோசைட்",
                            "A flat, protective epithelial-like cell that forms the outer layer of the sponge and lines the incurrent canals and the central cavity.",
                            "கடற்பஞ்சின் வெளி அடுக்கையும் உட்செல்லும் கால்வாய்கள், நடுக்குழி ஆகியவற்றின் உள்ளடுக்கையும் அமைக்கும் தட்டையான பாதுகாப்பு செல்."),
                        def("Porocyte", "போரோசைட்",
                            "A tube-like cell pierced by a channel through its cytoplasm; porocytes form the ostia and regulate the entry of water.",
                            "சைட்டோபிளாசம் வழியாக ஒரு வழி செல்லும் குழாய்வடிவ செல்; ஆஸ்டியாவை உருவாக்கி நீர் நுழைவை ஒழுங்குபடுத்துகிறது."),
                    )),
                    BookSubsection(b("The middle layer: mesohyl", "நடு அடுக்கு: மீசோஹைல்"), listOf(
                        p("Between the outer layer and the collar cells lies the mesohyl, a gelatinous matrix that contains the skeleton and several kinds of wandering cells. The most important are the amoebocytes. Some digest food and carry it to other cells, some secrete the spicules, and some give rise to the eggs.",
                            "வெளி அடுக்குக்கும் காலர் செல்களுக்கும் இடையே மீசோஹைல் என்ற ஜெல் போன்ற பொருள் உள்ளது; இதில் எலும்புக்கூடும் பல வகை அலையும் செல்களும் அமைந்துள்ளன. இவற்றில் அமீபோசைட்டுகள் முக்கியமானவை. சில உணவைச் செரித்து பிற செல்களுக்குக் கொண்டு செல்கின்றன; சில ஊசிகளைச் சுரக்கின்றன; சில முட்டைகளாக மாறுகின்றன."),
                        def("Mesohyl", "மீசோஹைல்",
                            "The gelatinous middle layer of a sponge, lying between the outer pinacoderm and the inner choanoderm, which contains the skeleton and the amoebocytes.",
                            "கடற்பஞ்சின் வெளி பினாகோடெர்முக்கும் உள் கோனோடெர்முக்கும் இடையிலுள்ள ஜெல் போன்ற நடு அடுக்கு; எலும்புக்கூடும் அமீபோசைட்டுகளும் இதில் உள்ளன."),
                        def("Amoebocyte", "அமீபோசைட்",
                            "A wandering cell of the mesohyl that moves like an amoeba; amoebocytes digest and distribute food, secrete spicules and give rise to gametes.",
                            "மீசோஹைலில் அமீபாவைப்போல் நகரும் அலையும் செல்; உணவைச் செரித்துப் பகிர்கிறது, ஊசிகளைச் சுரக்கிறது, இனச்செல்களாக மாறுகிறது."),
                    )),
                    BookSubsection(b("The inner layer: choanoderm", "உள் அடுக்கு: கோனோடெர்ம்"), listOf(
                        p("The radial canals are lined by choanocytes. Each choanocyte is a small flask-shaped or cylindrical cell with one long flagellum, and the base of the flagellum is surrounded by a collar of fine microvilli. The beating of the flagella drives water along the canal, and the collar traps the food particles carried by it.",
                            "ஆரக் கால்வாய்கள் கோனோசைட்டுகளால் வரிசையாக மூடப்பட்டுள்ளன. ஒவ்வொரு கோனோசைட்டும் குடுவை அல்லது உருளை வடிவ சிறு செல்; ஒரு நீண்ட கசையிழையும், அதன் அடியைச் சுற்றி நுண்விரல்களால் ஆன காலரும் உண்டு. கசையிழை அசைந்து நீரைக் கால்வாய் வழியே செலுத்துகிறது; காலர் அந்த நீரிலுள்ள உணவுத் துகள்களைப் பிடிக்கிறது."),
                        def("Choanocyte", "கோனோசைட் (காலர் செல்)",
                            "A flagellated cell with a collar of microvilli around the base of the flagellum; choanocytes create the water current and capture food particles.",
                            "கசையிழையின் அடியில் நுண்விரல் காலர் கொண்ட செல்; நீரோட்டத்தை உருவாக்கி உணவுத் துகள்களைப் பிடிக்கிறது."),
                        BookNote(b("An important point", "முக்கியக் குறிப்பு"),
                            b("In Sycon the choanocytes are confined to the radial canals. The central cavity and the incurrent canals are lined by flat cells. This is the main structural difference between Sycon and the simpler sponge Leucosolenia.",
                                "சைக்கானில் கோனோசைட்டுகள் ஆரக் கால்வாய்களில் மட்டுமே உள்ளன; நடுக்குழியும் உட்செல்லும் கால்வாய்களும் தட்டை செல்களால் மூடப்பட்டுள்ளன. எளிய கடற்பஞ்சான லியூக்கோசோலீனியாவிலிருந்து இதுவே முக்கிய அமைப்பு வேறுபாடு.")),
                    )),
                    BookSubsection(b("The skeleton: spicules", "எலும்புக்கூடு: ஊசிகள்"), listOf(
                        p("The skeleton consists of calcareous spicules secreted by amoebocytes called scleroblasts. Three shapes occur: rod-like (monaxon), three-rayed (triradiate) and four-rayed (tetraradiate). The long rod-like spicules form the fringe around the osculum and project from the surface. The three-rayed and four-rayed spicules lie in the wall and support the canals; in many tetraradiate spicules the fourth ray projects towards the central cavity.",
                            "எலும்புக்கூடு, ஸ்கிளீரோபிளாஸ்ட் எனப்படும் அமீபோசைட்டுகள் சுரக்கும் சுண்ணாம்பு ஊசிகளால் ஆனது. மூன்று வடிவங்கள் உண்டு: கோல்வடிவம் (ஓரச்சு), மூன்று கதிர், நான்கு கதிர். நீண்ட கோல் ஊசிகள் ஆஸ்குலத்தைச் சுற்றிய விளிம்பை அமைத்து மேற்பரப்பிலும் நீட்டிக் கொண்டிருக்கும். மூன்று, நான்கு கதிர் ஊசிகள் சுவரில் இருந்து கால்வாய்களைத் தாங்குகின்றன; பல நான்கு கதிர் ஊசிகளில் நான்காம் கதிர் நடுக்குழியை நோக்கி நீளும்."),
                        def("Spicule", "ஸ்பிக்யூல் (ஊசி)",
                            "A minute needle-like or star-like skeletal element secreted by cells of the mesohyl. In Sycon the spicules are made of calcium carbonate.",
                            "மீசோஹைல் செல்கள் சுரக்கும் நுண்ணிய ஊசி அல்லது நட்சத்திர வடிவ எலும்புக்கூட உறுப்பு. சைக்கானில் இவை கால்சியம் கார்பனேட்டால் ஆனவை."),
                        BookNote(b("Practical test", "செய்முறைச் சோதனை"),
                            b("Calcareous spicules dissolve in dilute acid with effervescence, whereas siliceous spicules do not. This is the quickest way to show that a sponge spicule is calcareous.",
                                "சுண்ணாம்பு ஊசிகள் நீர்த்த அமிலத்தில் குமிழ்களுடன் கரையும்; சிலிக்கா ஊசிகள் கரையாது. ஒரு கடற்பஞ்சு ஊசி சுண்ணாம்புடையது என்பதைக் காட்ட இது விரைவான வழி.")),
                    )),
                ),
            ),
            BookSection(
                id = "canal-system",
                heading = b("The syconoid canal system", "சைக்கனாய்டு கால்வாய் அமைப்பு"),
                blocks = listOf(
                    p("Sponges are classified into three grades of organisation according to the way the body wall is built. In the syconoid grade the wall is folded into finger-like pockets so that the choanocytes are confined to radial canals, while the central cavity is lined by flat cells. Sycon is the classical example.",
                        "உடற்சுவர் அமையும் விதத்தைப் பொறுத்து கடற்பஞ்சுகள் மூன்று அமைப்பு நிலைகளாகப் பிரிக்கப்படுகின்றன. சைக்கனாய்டு நிலையில் சுவர் விரல்போன்ற பைகளாக மடிந்து, கோனோசைட்டுகள் ஆரக் கால்வாய்களில் மட்டும் இருக்க, நடுக்குழி தட்டை செல்களால் மூடப்பட்டிருக்கும். சைக்கான் இதன் பாரம்பரிய எடுத்துக்காட்டு."),
                    def("Syconoid", "சைக்கனாய்டு",
                        "The grade of sponge organisation in which the body wall is folded into radial canals lined by choanocytes, with incurrent canals between them and a central cavity lined by flat cells.",
                        "உடற்சுவர் மடிந்து கோனோசைட்டுகள் வரிசையாக உள்ள ஆரக் கால்வாய்களும் அவற்றுக்கிடையே உட்செல்லும் கால்வாய்களும், தட்டை செல்களால் மூடிய நடுக்குழியும் கொண்ட கடற்பஞ்சு அமைப்பு நிலை."),
                    BookFigure("sycon/canal-system",
                        b("Canal system of Sycon. Check each label against the definitions in this section.",
                            "சைக்கானின் கால்வாய் அமைப்பு. ஒவ்வொரு குறிப்பையும் இப்பகுதியின் வரையறைகளோடு ஒப்பிட்டுப் பார்க்க.")),
                ),
                subsections = listOf(
                    BookSubsection(b("Parts of the canal system", "கால்வாய் அமைப்பின் பகுதிகள்"), listOf(
                        def("Incurrent canal", "உட்செல்லும் கால்வாய்",
                            "A blind-ended canal of the body wall that opens to the outside through ostia and communicates with the radial canals through prosopyles. It is lined by flat cells, not by choanocytes.",
                            "உடற்சுவரில் ஒரு முனை மூடிய கால்வாய்; ஆஸ்டியா வழியாக வெளியே திறந்து, புரோசோபைல்கள் வழியாக ஆரக் கால்வாய்களுடன் இணைகிறது. இதன் உள்ளடுக்கு தட்டை செல்கள்; கோனோசைட்டுகள் இல்லை."),
                        def("Radial canal", "ஆரக் கால்வாய்",
                            "A blind-ended canal of the body wall, lined by choanocytes, that alternates with the incurrent canals and opens into the central cavity through an apopyle.",
                            "உடற்சுவரில் ஒரு முனை மூடிய, கோனோசைட்டுகள் வரிசையாக உள்ள கால்வாய்; உட்செல்லும் கால்வாய்களுடன் மாறிமாறி அமைந்து அப்போபைல் வழியாக நடுக்குழியில் திறக்கிறது."),
                        def("Prosopyle", "புரோசோபைல்",
                            "A small pore through which water passes from an incurrent canal into a radial canal.",
                            "உட்செல்லும் கால்வாயிலிருந்து ஆரக் கால்வாய்க்கு நீர் செல்லும் சிறு துளை."),
                        def("Apopyle", "அப்போபைல்",
                            "An opening through which water passes from a radial canal into the central cavity.",
                            "ஆரக் கால்வாயிலிருந்து நடுக்குழிக்கு நீர் செல்லும் திறப்பு."),
                        def("Spongocoel", "ஸ்பாஞ்சோசீல்",
                            "The central cavity of a sponge. In Sycon it is lined by flat cells, receives water from the radial canals and leads to the osculum. It is not a digestive stomach.",
                            "கடற்பஞ்சின் நடுக்குழி. சைக்கானில் இது தட்டை செல்களால் மூடப்பட்டு, ஆரக் கால்வாய்களிலிருந்து நீரைப் பெற்று ஆஸ்குலத்துக்கு அனுப்புகிறது. இது செரிமான வயிறு அல்ல."),
                        BookNote(b("Remembering the two pores", "இரு துளைகளை நினைவில் கொள்ள"),
                            b("The Greek prefix pros- means towards and apo- means away from. A prosopyle is therefore the pore towards the radial canal (entry), and an apopyle is the pore leading away from it (exit).",
                                "கிரேக்க முன்னொட்டு pros- என்றால் நோக்கி, apo- என்றால் விலகி. எனவே புரோசோபைல் ஆரக் கால்வாயை நோக்கிய துளை (நுழைவு); அப்போபைல் அதிலிருந்து விலகிச் செல்லும் துளை (வெளியேற்றம்).")),
                    )),
                    BookSubsection(b("The path of water", "நீரின் பாதை"), listOf(
                        BookKeyPoints(b("Water route through Sycon, in order", "சைக்கானில் நீரின் பாதை, வரிசைப்படி"), listOf(
                            b("Water enters through the many ostia of the outer wall.", "வெளிச்சுவரிலுள்ள பல ஆஸ்டியா வழியாக நீர் உள்ளே நுழைகிறது."),
                            b("It flows into the incurrent canals.", "உட்செல்லும் கால்வாய்களுக்குள் பாய்கிறது."),
                            b("It passes through the prosopyles into the radial canals.", "புரோசோபைல்கள் வழியாக ஆரக் கால்வாய்களுக்குச் செல்கிறது."),
                            b("In the radial canals the choanocytes filter it and remove food.", "ஆரக் கால்வாய்களில் கோனோசைட்டுகள் அதை வடிகட்டி உணவை எடுக்கின்றன."),
                            b("It leaves the radial canals through the apopyles into the spongocoel.", "அப்போபைல்கள் வழியாக ஆரக் கால்வாய்களிலிருந்து ஸ்பாஞ்சோசீலுக்குச் செல்கிறது."),
                            b("It leaves the spongocoel through the osculum to the outside.", "ஆஸ்குலம் வழியாக ஸ்பாஞ்சோசீலிலிருந்து வெளியே செல்கிறது."),
                        )),
                    )),
                    BookSubsection(b("Three grades of sponge organisation", "கடற்பஞ்சு அமைப்பின் மூன்று நிலைகள்"), listOf(
                        BookTable(
                            b("Comparison of the asconoid, syconoid and leuconoid grades", "அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு நிலைகளின் ஒப்பீடு"),
                            listOf(b("Feature", "பண்பு"), b("Asconoid", "அஸ்கோனாய்டு"), b("Syconoid", "சைக்கனாய்டு"), b("Leuconoid", "லியூக்கோனாய்டு")),
                            listOf(
                                listOf(b("Example", "எடுத்துக்காட்டு"), b("Leucosolenia", "லியூக்கோசோலீனியா"), b("Sycon", "சைக்கான்"), b("Spongilla and most sponges", "ஸ்பாஞ்சில்லா மற்றும் பெரும்பாலான கடற்பஞ்சுகள்")),
                                listOf(b("Body wall", "உடற்சுவர்"), b("Thin and unfolded", "மெல்லியது, மடிப்பற்றது"), b("Thick and folded into canals", "தடித்தது, கால்வாய்களாக மடிந்தது"), b("Very thick, full of chambers", "மிகத் தடித்தது, அறைகள் நிறைந்தது")),
                                listOf(b("Choanocytes line", "கோனோசைட்டுகள் வரிசைப்படுத்துவது"), b("The whole central cavity", "முழு நடுக்குழி"), b("The radial canals only", "ஆரக் கால்வாய்கள் மட்டும்"), b("Small rounded flagellated chambers", "சிறு உருண்டை கசையிழை அறைகள்")),
                                listOf(b("Central cavity", "நடுக்குழி"), b("Large, lined by choanocytes", "பெரியது, கோனோசைட் வரிசை உள்ளது"), b("Present, lined by flat cells", "உண்டு, தட்டை செல் வரிசை"), b("Not a distinct cavity; exhalant canals instead", "தனிக்குழி இல்லை; வெளிச்செல்லும் கால்வாய்கள் உண்டு")),
                                listOf(b("Water path", "நீரின் பாதை"), b("Ostia, central cavity, osculum", "ஆஸ்டியா, நடுக்குழி, ஆஸ்குலம்"), b("Ostia, incurrent canal, prosopyle, radial canal, apopyle, central cavity, osculum", "ஆஸ்டியா, உட்செல்லும் கால்வாய், புரோசோபைல், ஆரக் கால்வாய், அப்போபைல், நடுக்குழி, ஆஸ்குலம்"), b("Ostia, incurrent canals, chambers, exhalant canals, osculum", "ஆஸ்டியா, உட்செல்லும் கால்வாய்கள், அறைகள், வெளிச்செல்லும் கால்வாய்கள், ஆஸ்குலம்")),
                                listOf(b("Filtering surface for body size", "உடல் அளவுக்கான வடிகட்டும் பரப்பு"), b("Smallest", "மிகச் சிறியது"), b("Larger", "பெரியது"), b("Largest", "மிகப் பெரியது")),
                            )),
                        p("The sequence from the asconoid to the leuconoid grade is a sequence of increasing folding, which increases the area of choanocytes in relation to the volume of the body. This is why the larger sponges are leuconoid. The Sycon pattern is therefore a type, not a universal feature of sponges.",
                            "அஸ்கோனாய்டிலிருந்து லியூக்கோனாய்டு வரையிலான வரிசை மடிப்பு அதிகரிக்கும் வரிசை; இதனால் உடலின் கொள்ளளவுக்கு ஏற்ப கோனோசைட் பரப்பு கூடுகிறது. அதனால்தான் பெரிய கடற்பஞ்சுகள் லியூக்கோனாய்டு வகையைச் சேர்ந்தவை. எனவே சைக்கான் அமைப்பு ஓர் இன ஆய்வே; எல்லாக் கடற்பஞ்சுகளுக்கும் பொதுவான பண்பு அல்ல."),
                    )),
                ),
            ),
            BookSection(
                id = "life-processes",
                heading = b("Life processes", "உயிர்ச்செயல்கள்"),
                blocks = emptyList(),
                subsections = listOf(
                    BookSubsection(b("Water current and feeding", "நீரோட்டமும் உணவூட்டமும்"), listOf(
                        p("The flagella of the choanocytes beat continuously, drawing water in through the ostia and driving it out through the osculum. The water carries bacteria, single-celled algae and fine organic particles. The very small ostia and prosopyles keep out large particles, and the collars of the choanocytes trap the fine particles that remain.",
                            "கோனோசைட்டுகளின் கசையிழைகள் இடைவிடாமல் அசைந்து ஆஸ்டியா வழியாக நீரை இழுத்து ஆஸ்குலம் வழியாக வெளியேற்றுகின்றன. நீரில் பாக்டீரியா, ஒருசெல் பாசிகள், நுண்ணிய கரிமத் துகள்கள் உள்ளன. மிகச் சிறிய ஆஸ்டியாவும் புரோசோபைல்களும் பெரிய துகள்களைத் தடுக்கின்றன; எஞ்சிய நுண்துகள்களை கோனோசைட் காலர்கள் பிடிக்கின்றன."),
                        p("Digestion takes place inside cells. The captured particle is taken into a food vacuole of a choanocyte or passed to an amoebocyte, digested there, and the products are carried by amoebocytes to the other cells. Undigested material is returned to the water leaving the sponge.",
                            "செரிமானம் செல்லுக்குள்ளேயே நடைபெறுகிறது. பிடிக்கப்பட்ட துகள் கோனோசைட்டின் உணவுக் குமிழுக்குள் எடுக்கப்படுகிறது அல்லது அமீபோசைட்டுக்குக் கடத்தப்பட்டு அங்கு செரிக்கப்படுகிறது; செரிமானப் பொருள்களை அமீபோசைட்டுகள் பிற செல்களுக்குக் கொண்டு செல்கின்றன. செரிக்காத பொருள்கள் வெளியேறும் நீருடன் சேர்ந்து வெளியேறுகின்றன."),
                        def("Intracellular digestion", "செல்லுக்குள் செரிமானம்",
                            "Digestion of food inside the food vacuoles of individual cells, not in a digestive cavity.",
                            "செரிமானக் குழியில் அல்லாமல் தனித்தனி செல்களின் உணவுக் குமிழ்களுக்குள் உணவு செரிக்கப்படுதல்."),
                    )),
                    BookSubsection(b("Respiration and excretion", "சுவாசமும் கழிவு நீக்கமும்"), listOf(
                        p("There are no respiratory or excretory organs. Oxygen dissolved in the water diffuses into the cells, and carbon dioxide and nitrogenous waste, chiefly ammonia, diffuse out into the water. The continuous current keeps fresh water in contact with the cells.",
                            "சுவாச உறுப்போ கழிவு நீக்க உறுப்போ இல்லை. நீரில் கரைந்த ஆக்சிஜன் செல்களுக்குள் ஊடுருவுகிறது; கார்பன் டை ஆக்சைடும் நைட்ரஜன் கழிவும் (முக்கியமாக அம்மோனியா) நீருக்குள் வெளியேறுகின்றன. தொடர் நீரோட்டம் செல்களுக்குப் புதிய நீரைத் தருகிறது."),
                    )),
                    BookSubsection(b("Coordination and regeneration", "ஒருங்கிணைப்பும் மீளுருவாக்கமும்"), listOf(
                        p("Sycon has no nerve cells or sense organs. Responses such as the slow narrowing of the osculum are local reactions of the cells concerned. Sponges have a high power of regeneration, and a damaged or cut piece can restore the missing part.",
                            "சைக்கானுக்கு நரம்பு செல்களோ உணர்வு உறுப்புகளோ இல்லை. ஆஸ்குலம் மெதுவாகச் சுருங்குவது போன்ற பதில்கள் அந்தந்த செல்களின் உள்ளூர் எதிர்வினைகளே. கடற்பஞ்சுகளுக்கு மீளுருவாக்க ஆற்றல் அதிகம்; சேதமடைந்த அல்லது வெட்டப்பட்ட துண்டு இழந்த பகுதியை மீட்டுக் கொள்ளும்."),
                        BookKeyPoints(b("Structures that Sycon does not have", "சைக்கானில் இல்லாத அமைப்புகள்"), listOf(
                            b("A mouth and a digestive tract", "வாயும் செரிமானக் குழாயும்"),
                            b("A heart, blood vessels or blood", "இதயம், இரத்தக் குழாய்கள் அல்லது இரத்தம்"),
                            b("Nerve cells and sense organs", "நரம்பு செல்கள், உணர்வு உறுப்புகள்"),
                            b("Excretory and respiratory organs", "கழிவு நீக்க, சுவாச உறுப்புகள்"),
                            b("True tissues organised into organs", "உறுப்புகளாக ஒருங்கமைந்த உண்மையான திசுக்கள்"),
                        )),
                    )),
                ),
            ),
            BookSection(
                id = "reproduction",
                heading = b("Reproduction and development", "இனப்பெருக்கமும் வளர்ச்சியும்"),
                blocks = emptyList(),
                subsections = listOf(
                    BookSubsection(b("Asexual reproduction", "பாலிலா இனப்பெருக்கம்"), listOf(
                        p("Sycon reproduces asexually by budding. A bud arises as an outgrowth from the body wall near the base, grows, forms its own osculum and either breaks away or stays attached so that a small cluster is formed. Gemmules, which are the resistant asexual bodies of freshwater sponges such as Spongilla, are not formed in Sycon.",
                            "சைக்கான் மொட்டுவிடுதல் மூலம் பாலிலா முறையில் இனப்பெருக்கம் செய்கிறது. அடிப்பகுதிக்கு அருகில் உடற்சுவரிலிருந்து ஒரு வளர்ச்சியாக மொட்டு தோன்றி வளர்ந்து தனக்கான ஆஸ்குலத்தை உருவாக்கி, பிரிந்து செல்கிறது அல்லது ஒட்டியிருந்து சிறு கூட்டமாகிறது. ஸ்பாஞ்சில்லா போன்ற நன்னீர் கடற்பஞ்சுகளின் தாங்கும் பாலிலா உடல்களான ஜெம்யூல்கள் சைக்கானில் உருவாவதில்லை."),
                        def("Budding", "மொட்டுவிடுதல்",
                            "Asexual reproduction in which a new individual develops as an outgrowth of the parent and later separates from it or remains attached.",
                            "தாய் உயிரியின் வளர்ச்சியாகப் புதிய உயிரி தோன்றி, பின்னர் பிரிந்து செல்லும் அல்லது ஒட்டியிருக்கும் பாலிலா இனப்பெருக்கம்."),
                    )),
                    BookSubsection(b("Sexual reproduction and fertilisation", "பாலின இனப்பெருக்கமும் கருவுறுதலும்"), listOf(
                        p("Sycon is usually described as hermaphrodite, but the sperms and eggs of one individual mature at different times, so that cross-fertilisation is the rule. The eggs arise from amoebocytes in the mesohyl. The sperms are shed into the water through the osculum, and a sperm entering another sponge with the incurrent water is taken up by a choanocyte, which loses its flagellum and collar and carries the sperm to an egg. Fertilisation occurs in the mesohyl. Details differ slightly in different textbooks, and the account given in your prescribed book should be followed.",
                            "சைக்கான் பொதுவாக இருபாலுயிரி எனப்படுகிறது; ஆனால் ஒரே உயிரியின் விந்தும் முட்டையும் வெவ்வேறு காலத்தில் முதிர்வதால் பிறவி கருவுறுதலே வழக்கம். முட்டைகள் மீசோஹைலில் உள்ள அமீபோசைட்டுகளிலிருந்து தோன்றுகின்றன. விந்துகள் ஆஸ்குலம் வழியாக நீருக்குள் வெளியேறுகின்றன; மற்றொரு கடற்பஞ்சுக்குள் உட்செல்லும் நீருடன் நுழையும் விந்தை ஒரு கோனோசைட் எடுத்துக்கொண்டு, தன் கசையிழையையும் காலரையும் இழந்து, அதை முட்டையிடம் கொண்டு செல்கிறது. கருவுறுதல் மீசோஹைலில் நிகழ்கிறது. பாடநூல்களிடையே விவரங்கள் சற்று மாறுபடும்; உங்கள் பாடநூலின் விளக்கத்தைப் பின்பற்றுக."),
                        def("Hermaphrodite", "இருபாலுயிரி",
                            "An individual that produces both male gametes (sperms) and female gametes (eggs).",
                            "ஆண் இனச்செல் (விந்து), பெண் இனச்செல் (முட்டை) இரண்டையும் உருவாக்கும் உயிரி."),
                    )),
                    BookSubsection(b("Cleavage, inversion and the larva", "பிளவுறுதல், உள்வெளித் திருப்பம், இளவுயிரி"), listOf(
                        p("The zygote divides repeatedly in the mesohyl and forms a hollow ball of cells called the stomoblastula. Its wall is made of small flagellated cells, the micromeres, whose flagella at first point into the central cavity, together with a group of larger granular cells, the macromeres. The embryo then turns inside out through an opening in its wall. This turning, called inversion, brings the flagella of the micromeres to the outer surface.",
                            "கருமுட்டை மீசோஹைலில் தொடர்ந்து பிளவுபட்டு ஸ்டோமோபிளாஸ்டுலா என்ற வெற்று செல் உருண்டையை உருவாக்குகிறது. இதன் சுவர் சிறிய கசையிழை செல்களான மைக்ரோமியர்களாலும் பெரிய துகள் செல்களான மேக்ரோமியர்களாலும் ஆனது; மைக்ரோமியர்களின் கசையிழைகள் தொடக்கத்தில் நடுக்குழியை நோக்கியிருக்கும். பின்னர் கரு தன் சுவரிலுள்ள ஒரு திறப்பு வழியாக உள்வெளியாகத் திரும்புகிறது. இந்தத் திருப்பமே inversion; இதனால் மைக்ரோமியர்களின் கசையிழைகள் வெளிப்பரப்புக்கு வருகின்றன."),
                        def("Stomoblastula", "ஸ்டோமோபிளாஸ்டுலா",
                            "The hollow blastula of Sycon, formed in the parent, whose flagellated micromeres have their flagella directed into the central cavity.",
                            "தாய் உடலுக்குள் உருவாகும் சைக்கானின் வெற்று பிளாஸ்டுலா; இதன் மைக்ரோமியர்களின் கசையிழைகள் நடுக்குழியை நோக்கியிருக்கும்."),
                        def("Inversion", "உள்வெளித் திருப்பம் (இன்வெர்ஷன்)",
                            "The turning of the stomoblastula inside out through an opening, so that the flagellated cells come to lie on the outside.",
                            "ஸ்டோமோபிளாஸ்டுலா ஒரு திறப்பு வழியாக உள்வெளியாகத் திரும்பி, கசையிழை செல்கள் வெளியே வருதல்."),
                        def("Amphiblastula", "ஆம்ஃபிபிளாஸ்டுலா",
                            "The free-swimming larva of Sycon, formed after inversion, in which one half of the body consists of flagellated micromeres and the other half of large macromeres.",
                            "உள்வெளித் திருப்பத்துக்குப் பின் உருவாகும் சைக்கானின் நீந்தும் இளவுயிரி; உடலின் ஒரு பாதி கசையிழை மைக்ரோமியர்களாலும் மறுபாதி பெரிய மேக்ரோமியர்களாலும் ஆனது."),
                        BookTable(
                            b("Stomoblastula compared with amphiblastula", "ஸ்டோமோபிளாஸ்டுலாவும் ஆம்ஃபிபிளாஸ்டுலாவும் ஒப்பீடு"),
                            listOf(b("Feature", "பண்பு"), b("Stomoblastula", "ஸ்டோமோபிளாஸ்டுலா"), b("Amphiblastula", "ஆம்ஃபிபிளாஸ்டுலா")),
                            listOf(
                                listOf(b("Flagella of the micromeres", "மைக்ரோமியர்களின் கசையிழை"), b("Directed inwards", "உள்நோக்கி"), b("Directed outwards", "வெளிநோக்கி")),
                                listOf(b("Position", "இருப்பிடம்"), b("Inside the body of the parent", "தாய் உடலுக்குள்"), b("Free-swimming in the sea", "கடலில் நீந்துகிறது")),
                                listOf(b("Role", "பணி"), b("Stage before inversion", "உள்வெளித் திருப்பத்துக்கு முந்தைய நிலை"), b("Dispersal and settlement", "பரவலும் நிலைபெறுதலும்")),
                            )),
                        BookFigure("sycon/reproduction",
                            b("Stages in the development of a calcareous sponge (a generalised diagram, not specific to Sycon; see the credit line).",
                                "சுண்ணாம்புக் கடற்பஞ்சின் வளர்ச்சி நிலைகள் (பொதுவான படம்; சைக்கானுக்கு மட்டும் உரியதல்ல; மூலக் குறிப்பைப் பார்க்க).")),
                        BookNote(b("How to explain inversion", "இன்வெர்ஷனை எவ்வாறு விளக்குவது"),
                            b("Describe inversion as a definite event that brings the flagellated cells to the outside so that the larva can swim. Do not explain it as happening because the adult water current would otherwise fail.",
                                "இன்வெர்ஷனை, இளவுயிரி நீந்துவதற்காகக் கசையிழை செல்களை வெளியே கொண்டு வரும் குறிப்பிட்ட நிகழ்வாக விளக்குக. முதிர்ந்த கடற்பஞ்சின் நீரோட்டம் செயலிழக்கும் என்பதால் நிகழ்கிறது என்று கூறக் கூடாது.")),
                    )),
                    BookSubsection(b("Settlement and metamorphosis", "நிலைபெறுதலும் உருமாற்றமும்"), listOf(
                        p("The amphiblastula leaves the parent through the osculum with the outgoing current, swims for a short period and settles on a firm surface. During metamorphosis the flagellated cells move inwards and take part in forming the choanocyte-bearing layer, while the other cells contribute to the outer layer. A small, simple, vase-shaped young sponge called the olynthus results. Folding of its wall later converts it into the syconoid adult.",
                            "ஆம்ஃபிபிளாஸ்டுலா வெளியேறும் நீரோட்டத்துடன் ஆஸ்குலம் வழியாகத் தாயை விட்டு நீங்கி, சிறிது காலம் நீந்தி, உறுதியான பரப்பில் நிலைபெறுகிறது. உருமாற்றத்தில் கசையிழை செல்கள் உள்நோக்கி நகர்ந்து கோனோசைட் அடுக்கை உருவாக்கப் பங்கேற்கின்றன; மற்ற செல்கள் வெளி அடுக்குக்குப் பங்களிக்கின்றன. இதனால் ஓலிந்தஸ் எனப்படும் சிறிய எளிய குவளை வடிவ இளம் கடற்பஞ்சு உருவாகிறது. அதன் சுவர் பின்னர் மடிந்து சைக்கனாய்டு முதிர்உயிரியாக மாறுகிறது."),
                        def("Olynthus", "ஓலிந்தஸ்",
                            "The young, simple, asconoid stage of a calcareous sponge that develops from the settled larva and later becomes syconoid by folding of its wall.",
                            "நிலைபெற்ற இளவுயிரியிலிருந்து உருவாகும் சுண்ணாம்புக் கடற்பஞ்சின் இளம், எளிய, அஸ்கோனாய்டு நிலை; சுவர் மடிவதால் பின்னர் சைக்கனாய்டாகிறது."),
                    )),
                ),
            ),
            BookSection(
                id = "identification",
                heading = b("Identification points and common errors", "அடையாளக் குறிப்புகளும் பொதுவான பிழைகளும்"),
                blocks = listOf(
                    BookKeyPoints(b("Points to record in a practical", "செய்முறையில் பதிவு செய்ய வேண்டிய குறிப்புகள்"), listOf(
                        b("Vase-shaped or cylindrical body with a single apical osculum surrounded by a fringe of spicules.", "குவளை அல்லது உருளை வடிவ உடல்; நுனியில் ஊசி விளிம்புடன் கூடிய ஒரே ஆஸ்குலம்."),
                        b("Spicules dissolve in dilute acid with effervescence: they are calcareous, so the class is Calcarea.", "ஊசிகள் நீர்த்த அமிலத்தில் குமிழ்களுடன் கரையும்: அவை சுண்ணாம்புடையவை; எனவே கால்கேரியா வகுப்பு."),
                        b("Transverse section shows alternating incurrent and radial canals.", "குறுக்குவெட்டில் மாறிமாறி அமைந்த உட்செல்லும், ஆரக் கால்வாய்கள் தெரியும்."),
                        b("Choanocytes are found only in the radial canals.", "கோனோசைட்டுகள் ஆரக் கால்வாய்களில் மட்டுமே உள்ளன."),
                        b("The central cavity is lined by flat cells, so the grade is syconoid.", "நடுக்குழி தட்டை செல்களால் மூடப்பட்டுள்ளது; எனவே நிலை சைக்கனாய்டு."),
                    )),
                    BookKeyPoints(b("Common errors to avoid", "தவிர்க்க வேண்டிய பொதுவான பிழைகள்"), listOf(
                        b("Calling the osculum an ostium.", "ஆஸ்குலத்தை ஆஸ்டியம் என்று குறிப்பது."),
                        b("Placing choanocytes in the central cavity of Sycon.", "சைக்கானின் நடுக்குழியில் கோனோசைட்டுகளை வைப்பது."),
                        b("Interchanging the prosopyle and the apopyle.", "புரோசோபைலையும் அப்போபைலையும் மாற்றி எழுதுவது."),
                        b("Describing the spongocoel as a stomach.", "ஸ்பாஞ்சோசீலை வயிறு என்று விவரிப்பது."),
                        b("Treating the Sycon canal system as the pattern of all sponges.", "சைக்கான் கால்வாய் அமைப்பை எல்லாக் கடற்பஞ்சுகளின் அமைப்பாகக் கருதுவது."),
                    )),
                ),
            ),
        ),
        summary = listOf(
            b("Sycon is a marine, sessile, radially symmetrical sponge of the class Calcarea, with calcareous spicules.", "சைக்கான் கால்கேரியா வகுப்பைச் சேர்ந்த, சுண்ணாம்பு ஊசிகள் கொண்ட, கடல்வாழ், நிலைத்த, கதிர்ச் சமச்சீர் கடற்பஞ்சு."),
            b("The single large opening at the apex is the osculum; the many minute inhalant pores are the ostia.", "நுனியிலுள்ள ஒரே பெரிய துளை ஆஸ்குலம்; பல நுண்ணிய உட்செல்லும் துளைகள் ஆஸ்டியா."),
            b("The body wall has a flat outer layer, a gelatinous mesohyl with amoebocytes and spicules, and an inner layer of choanocytes confined to the radial canals.", "உடற்சுவரில் தட்டை வெளி அடுக்கும், அமீபோசைட்டுகளும் ஊசிகளும் உள்ள ஜெல் மீசோஹைலும், ஆரக் கால்வாய்களில் மட்டுமுள்ள கோனோசைட் உள் அடுக்கும் உள்ளன."),
            b("Water flows from the ostia through incurrent canals, prosopyles, radial canals and apopyles into the spongocoel and out through the osculum.", "நீர் ஆஸ்டியாவிலிருந்து உட்செல்லும் கால்வாய், புரோசோபைல், ஆரக் கால்வாய், அப்போபைல் வழியாக ஸ்பாஞ்சோசீலை அடைந்து ஆஸ்குலம் வழியாக வெளியேறுகிறது."),
            b("Digestion is intracellular, and respiration and excretion take place by diffusion; there is no gut, blood or nervous system.", "செரிமானம் செல்லுக்குள் நடைபெறுகிறது; சுவாசமும் கழிவு நீக்கமும் விரவல் மூலம் நடைபெறுகின்றன; குடல், இரத்தம், நரம்புமண்டலம் இல்லை."),
            b("Asexual reproduction is by budding; sexual reproduction is by cross-fertilisation between hermaphrodite individuals.", "பாலிலா இனப்பெருக்கம் மொட்டுவிடுதல்; பாலின இனப்பெருக்கம் இருபாலுயிரிகளிடையே பிறவி கருவுறுதல்."),
            b("The stomoblastula turns inside out by inversion to form the free-swimming amphiblastula, which settles and becomes an olynthus and then a syconoid sponge.", "ஸ்டோமோபிளாஸ்டுலா இன்வெர்ஷன் மூலம் உள்வெளியாகத் திரும்பி நீந்தும் ஆம்ஃபிபிளாஸ்டுலா ஆகிறது; அது நிலைபெற்று ஓலிந்தஸாகி பின்னர் சைக்கனாய்டு கடற்பஞ்சாகிறது."),
        ),
        quickChecks = listOf(
            BookQuickCheck(b("Where are the choanocytes located in Sycon?", "சைக்கானில் கோனோசைட்டுகள் எங்கு உள்ளன?"),
                b("In the radial canals only. The central cavity and the incurrent canals are lined by flat cells.", "ஆரக் கால்வாய்களில் மட்டும். நடுக்குழியும் உட்செல்லும் கால்வாய்களும் தட்டை செல்களால் மூடப்பட்டுள்ளன.")),
            BookQuickCheck(b("Name the larva of Sycon.", "சைக்கானின் இளவுயிரியின் பெயரைக் கூறுக."),
                b("The amphiblastula.", "ஆம்ஃபிபிளாஸ்டுலா.")),
            BookQuickCheck(b("Through which opening does water leave a sponge?", "கடற்பஞ்சிலிருந்து நீர் எந்தத் துளை வழியாக வெளியேறுகிறது?"),
                b("Through the osculum.", "ஆஸ்குலம் வழியாக.")),
            BookQuickCheck(b("What is the function of a porocyte?", "போரோசைட்டின் பணி என்ன?"),
                b("It forms the ostium, a channel through the cell, and so regulates the entry of water.", "செல்லின் வழியாகச் செல்லும் வழியான ஆஸ்டியத்தை உருவாக்கி, நீர் நுழைவை ஒழுங்குபடுத்துகிறது.")),
            BookQuickCheck(b("How can you show that the spicules of Sycon are calcareous?", "சைக்கானின் ஊசிகள் சுண்ணாம்புடையவை என எவ்வாறு காட்டுவது?"),
                b("They dissolve in dilute acid with effervescence.", "நீர்த்த அமிலத்தில் குமிழ்களுடன் கரையும்.")),
            BookQuickCheck(b("Give two differences between the asconoid and the syconoid grade.", "அஸ்கோனாய்டு, சைக்கனாய்டு நிலைகளுக்கு இடையே இரு வேறுபாடுகள் தருக."),
                b("In the asconoid grade the wall is unfolded and the choanocytes line the whole central cavity; in the syconoid grade the wall is folded into radial canals and the choanocytes are confined to them.", "அஸ்கோனாய்டில் சுவர் மடிப்பற்றது, கோனோசைட்டுகள் முழு நடுக்குழியையும் மூடுகின்றன; சைக்கனாய்டில் சுவர் ஆரக் கால்வாய்களாக மடிந்து, கோனோசைட்டுகள் அவற்றில் மட்டும் உள்ளன.")),
        ),
        reviewQuestionIds = listOf("u2-sycon-A5-1", "u2-sycon-A5-2"),
        furtherReading = listOf(
            b("Ruppert, E. E., Fox, R. S. and Barnes, R. D. Invertebrate Zoology: A Functional Evolutionary Approach, 7th edition, Brooks/Cole: chapter on Porifera. Page numbers to be added by the reviewer.",
                "ரூப்பர்ட், ஃபாக்ஸ், பார்ன்ஸ்: Invertebrate Zoology: A Functional Evolutionary Approach, 7ஆம் பதிப்பு, Brooks/Cole: போரிஃபெரா அத்தியாயம். பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."),
            b("Brusca, R. C., Moore, W. and Shuster, S. M. Invertebrates, 3rd edition, Sinauer, 2016: chapter on Porifera. Page numbers to be added by the reviewer.",
                "புரூஸ்கா, மூர், ஷஸ்டர்: Invertebrates, 3ஆம் பதிப்பு, Sinauer, 2016: போரிஃபெரா அத்தியாயம். பக்க எண்களை மதிப்பாய்வாளர் சேர்க்க வேண்டும்."),
            b("Hyman, L. H. The Invertebrates, volume 1: Protozoa through Ctenophora, McGraw-Hill, 1940: account of the Calcarea.",
                "ஹைமன்: The Invertebrates, தொகுதி 1: Protozoa through Ctenophora, McGraw-Hill, 1940: கால்கேரியா பற்றிய பகுதி."),
        ),
    )
}

object BookChapters {
    val byLesson: Map<String, BookChapter> = listOf(SyconBookChapter.chapter).associateBy { it.lessonId }
    fun forLesson(lessonId: String): BookChapter? = byLesson[lessonId]
}
