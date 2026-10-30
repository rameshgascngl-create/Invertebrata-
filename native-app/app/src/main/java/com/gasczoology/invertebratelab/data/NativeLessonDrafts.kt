package com.gasczoology.invertebratelab.data

/**
 * N2.2: first substantive bilingual lesson DRAFT. No clinical, diagrammatic or
 * editorial approval is implied. Source references are intentionally preserved.
 * The accepted A5 JSON documents are untouched.
 */
object NativeLessonDrafts {
    private const val OPENSTAX = "https://openstax.org/books/biology-2e/pages/23-3-groups-of-protists"
    private const val PROTIST_MOVEMENT = "https://openstax.org/books/biology-2e/pages/23-2-characteristics-of-protists"
    private const val PARA_REVIEW = "https://pmc.ncbi.nlm.nih.gov/articles/PMC10143506/"
    private const val OSMO_REVIEW = "https://pubmed.ncbi.nlm.nih.gov/38688044/"
    private const val SWIMMING_REVIEW = "https://pmc.ncbi.nlm.nih.gov/articles/PMC8208649/"
    private const val ORAL_ULTRASTRUCTURE = "https://pmc.ncbi.nlm.nih.gov/articles/PMC2109358/"

    val paramecium: NativeBilingualLesson = NativeBilingualLesson(
        lessonId = "u1-paramecium",
        title = BilingualText("Paramecium: organization and physiology",
            "பாரமீசியம்: உடலமைப்பும் உடலியங்கியலும்"),
        status = AcademicWorkStatus.DRAFT_UNVERIFIED,
        sections = listOf(
            LessonSection(
                id = "identity-and-habitat",
                heading = BilingualText("Identity and habitat", "அடையாளமும் வாழிடமும்"),
                paragraphs = listOf(
                    BilingualText(
                        "Paramecium is a genus of unicellular eukaryotic ciliates, commonly studied in freshwater samples. A single cell carries out movement, feeding, osmoregulation and reproduction.",
                        "பாரமீசியம் என்பது குறுஇழைகளைக் கொண்ட ஒருசெல் யூகேரியோட்டுகளின் ஒரு பேரினம். நன்னீர் மாதிரிகளில் இது பொதுவாகக் கற்கப்படுகிறது; ஒரே செல் இயக்கம், உணவூட்டம், நீர்ச்சமநிலை ஒழுங்குபடுத்தல் மற்றும் இனப்பெருக்கம் ஆகியவற்றைச் செய்கிறது."
                    ),
                    BilingualText(
                        "The historical term Protozoa remains useful for interpreting older zoology syllabi, but it does not represent one natural evolutionary lineage in modern classification.",
                        "பழைய விலங்கியல் பாடத்திட்டங்களில் வரும் ‘புரோட்டோசோவா’ என்ற பெயரை அறிந்திருப்பது பயனுள்ளது. ஆனால் நவீன இனவரலாற்று வகைப்பாட்டில் அது ஒரே இயற்கையான பரிணாம வழித்தோன்றலைக் குறிக்காது."
                    )
                ),
                scientificSources = listOf(OPENSTAX, PARA_REVIEW)
            ),
            LessonSection(
                id = "pellicle-and-cilia",
                heading = BilingualText("Cell surface and ciliary movement",
                    "செல் மேற்பரப்பும் குறுஇழை இயக்கமும்"),
                paragraphs = listOf(
                    BilingualText(
                        "The pellicle supports the characteristic cell outline, while many short cilia cover its surface. Coordinated ciliary beating propels the organism through water.",
                        "பெல்லிக்கிள் செல் வடிவத்தைப் பேண உதவுகிறது. செல் மேற்பரப்பில் ஏராளமான குறுகிய குறுஇழைகள் அமைந்துள்ளன; அவற்றின் ஒருங்கிணைந்த அசைவு உயிரியை நீரில் நகர்த்துகிறது."
                    ),
                    BilingualText(
                        "Neighbouring cilia can beat in travelling metachronal waves. Changes in ciliary activity contribute to the avoidance response when the cell encounters an obstacle.",
                        "அருகிலுள்ள குறுஇழைகளின் அசைவுகள் அலைபோல் ஒருங்கிணையலாம்; இதனை மெட்டாக்ரோனல் அலை என்கிறோம். ஒரு தடையைச் சந்திக்கும் போது குறுஇழை அசைவு மாறுவது தவிர்ப்பு எதிர்வினைக்கு உதவுகிறது."
                    ),
                    BilingualText(
                        "Cilia also create local water currents that help direct suspended food particles towards the feeding apparatus.",
                        "குறுஇழைகள் அருகிலுள்ள நீரில் ஓட்டத்தை உருவாக்கி மிதக்கும் உணவுத்துகள்களை உணவெடுக்கும் அமைப்பை நோக்கி நகர்த்துகின்றன."
                    )
                ),
                scientificSources = listOf(PROTIST_MOVEMENT, PARA_REVIEW, SWIMMING_REVIEW)
            ),
            LessonSection(
                id = "feeding-and-digestion",
                heading = BilingualText("Feeding and intracellular digestion",
                    "உணவூட்டமும் செல்லுக்குள் செரிமானமும்"),
                paragraphs = listOf(
                    BilingualText(
                        "Ciliary currents carry particles into the oral groove and toward the cytostome and cytopharynx. Food is packaged into membrane-bound food vacuoles.",
                        "குறுஇழைகளின் ஓட்டம் உணவுத்துகள்களை வாய்ப்பள்ளத்திலிருந்து சைட்டோஸ்டோம் மற்றும் சைட்டோஃபாரிங்ஸ் நோக்கிக் கொண்டு செல்கிறது. உணவு படலத்தால் சூழப்பட்ட உணவுக் குமிழ்களுக்குள் அடைக்கப்படுகிறது."
                    ),
                    BilingualText(
                        "Digestive processes occur inside these vacuoles as they move through the cytoplasm. Absorbed nutrients enter the cytoplasm, and indigestible residues leave through the cytoproct.",
                        "உணவுக் குமிழ்கள் சைட்டோபிளாஸ்மத்தில் நகரும் போது அவற்றுக்குள் செரிமானம் நிகழ்கிறது. ஊட்டச்சத்துகள் சைட்டோபிளாஸ்மத்திற்குள் உறிஞ்சப்படுகின்றன; செரியாத கழிவுகள் செல் கழிவுத்துளை (cytoproct) வழியாக வெளியேறுகின்றன."
                    )
                ),
                scientificSources = listOf(OPENSTAX, PARA_REVIEW)
            ),
            LessonSection(
                id = "osmoregulation",
                heading = BilingualText("Contractile vacuoles and osmoregulation",
                    "சுருங்கும் நுண்குமிழ்களும் நீர்ச்சமநிலையும்"),
                paragraphs = listOf(
                    BilingualText(
                        "Because freshwater is generally hypotonic relative to the cytoplasm, water tends to enter the cell osmotically. A contractile-vacuole system collects and periodically expels the excess.",
                        "நன்னீர் பொதுவாகச் சைட்டோபிளாஸ்மத்தைவிடக் குறைந்த கரைசல் செறிவு கொண்டது. எனவே ஒஸ்மோசிஸ் மூலம் நீர் செல்லுக்குள் நுழைகிறது; சுருங்கும் நுண்குமிழ் அமைப்பு மிகைநீரைச் சேகரித்து இடைவெளிகளுடன் வெளியேற்றுகிறது."
                    ),
                    BilingualText(
                        "A familiar Paramecium teaching diagram shows two contractile-vacuole complexes with associated collecting structures. Their chief classroom function is water-balance regulation, not digestion.",
                        "பொதுவாகப் பயன்படும் பாரமீசியம் பாடப்படத்தில் சேகரிப்பு அமைப்புகளுடன் கூடிய இரண்டு சுருங்கும் நுண்குமிழ் தொகுதிகள் காட்டப்படுகின்றன. இவற்றின் முதன்மைப் பணி நீர்ச்சமநிலையைப் பேணுவது; உணவைச் செரிப்பது அல்ல."
                    ),
                    BilingualText(
                        "The mechanism involves membrane transport as well as water movement; the vacuole must not be described as merely a passive water bubble.",
                        "இந்தச் செயலில் நீரின் இயக்கத்துடன் படலவழிப் பொருள் கடத்தலும் பங்காற்றுகிறது. எனவே நுண்குமிழைச் செயலற்ற நீர்க்குமிழாக மட்டும் விளக்கக்கூடாது."
                    )
                ),
                scientificSources = listOf(OPENSTAX, OSMO_REVIEW)
            ),
            LessonSection(
                id = "nuclear-dimorphism",
                heading = BilingualText("Macronucleus, micronucleus and fission",
                    "பேருட்கரு, சிற்றுட்கரு மற்றும் இருபிளவு"),
                paragraphs = listOf(
                    BilingualText(
                        "Paramecium exhibits nuclear dimorphism. The macronucleus carries out most routine gene-expression functions, while the micronucleus serves an essential germline role during sexual processes.",
                        "பாரமீசியத்தில் இரண்டு வகை உட்கருக்கள் உள்ளன. பேருட்கரு வழக்கமான மரபணு வெளிப்பாட்டு செயல்களில் முக்கியப் பங்கு வகிக்கிறது; சிற்றுட்கரு பாலியல் நிகழ்வுகளின் மரபுவழிச் செயல்களுக்கு இன்றியமையாதது."
                    ),
                    BilingualText(
                        "Asexual multiplication occurs by transverse binary fission. This increases cell number without requiring two compatible mating partners.",
                        "பாலிலா இனப்பெருக்கம் குறுக்குத் திசை இருபிளவு மூலம் நடைபெறுகிறது. இதற்கு இணக்கமான இரண்டு இணைவுயிர்கள் தேவையில்லை; செல் எண்ணிக்கை அதிகரிக்கிறது."
                    )
                ),
                scientificSources = listOf(OPENSTAX, PARA_REVIEW)
            ),
            LessonSection(
                id = "conjugation",
                heading = BilingualText("Conjugation and genetic reorganization",
                    "இணைவும் மரபணு மறுசீரமைப்பும்"),
                paragraphs = listOf(
                    BilingualText(
                        "During conjugation, compatible Paramecium cells temporarily pair. Micronuclear meiosis, the exchange of haploid nuclear material and fusion of pronuclei allow genetic recombination.",
                        "இணைவின் போது இணக்கமான இரண்டு பாரமீசியம் செல்கள் தற்காலிகமாக இணைகின்றன. சிற்றுட்கருவில் மியாசிஸ், ஒற்றையணுக் கருப்பொருள் பரிமாற்றம் மற்றும் முன்கருக்களின் இணைவு ஆகியவை மரபணு மறுசேர்க்கையை ஏற்படுத்துகின்றன."
                    ),
                    BilingualText(
                        "The original macronuclear organization is replaced during subsequent nuclear reorganization. Conjugation is a genetic exchange process; it should not be confused with immediate multiplication by binary fission.",
                        "பின்னர் நடைபெறும் உட்கரு மறுசீரமைப்பில் பழைய பேருட்கரு அமைப்பு மாற்றப்படுகிறது. இணைவு என்பது மரபணுப் பரிமாற்ற நிகழ்வு; அதை உடனடியாக செல் எண்ணிக்கையை அதிகரிக்கும் இருபிளவுடன் குழப்பக்கூடாது."
                    )
                ),
                scientificSources = listOf(OPENSTAX, PARA_REVIEW)
            ),
            LessonSection(
                id = "systematics-and-species",
                heading = BilingualText("Systematics and limits of traditional Protozoa",
                    "வகைப்பாடும் பாரம்பரிய புரோட்டோசோவா கருத்தின் வரம்புகளும்"),
                paragraphs = listOf(
                    BilingualText(
                        "Paramecium caudatum belongs to the eukaryotic alveolates (SAR group), phylum Ciliophora and class Oligohymenophorea. Many classification schemes place the genus in order Peniculida. Classical zoology textbooks frequently place ciliates within Protozoa; this is a historical teaching framework, not a single monophyletic animal group.",
                        "பாரமீசியம் கௌடேட்டம் யூகேரியோட்டுகளில் SAR தொகுதியைச் சேர்ந்த ஆல்வியோலேட்டுகளுள் அமைந்துள்ளது. இது Ciliophora தொகுதியிலும் Oligohymenophorea வகுப்பிலும் அடங்குகிறது; பல நவீன முறைகளில் Peniculida வரிசையில் வைக்கப்படுகிறது. பழைய பாடநூல்களின் ‘புரோட்டோசோவா’ ஒரே இயற்கையான பரிணாமக் குழு அல்ல."),
                    BilingualText(
                        "The reference species matters: a feature shown for a different Paramecium species cannot automatically be claimed as validated in P. caudatum. A whole-cell light micrograph, a transverse electron-microscope section and a stylized teaching illustration answer different anatomical questions.",
                        "ஆய்வுக்குரிய இனத்தைத் துல்லியமாகக் குறிப்பிட வேண்டும். வேறு பாரமீசியம் இனத்தில் காணப்பட்ட உறுப்பை P. caudatum இலும் அப்படியே உறுதிசெய்ய முடியாது. முழுச் செல் ஒளிநுண்ணோக்கிப் படம், எலக்ட்ரான் நுண்ணோக்கிக் குறுக்குவெட்டு, விளக்க வரைபடம் ஆகியவை வேறுபட்ட உடலமைப்புக் கேள்விகளுக்குப் பதில் தருகின்றன.")
                ),
                scientificSources = listOf(PARA_REVIEW, OPENSTAX)
            ),
            LessonSection(
                id = "oral-apparatus-details",
                heading = BilingualText("Oral apparatus and food-vacuole route",
                    "வாய்ப்புற அமைப்பும் உணவுக் குமிழ் செல்லும் பாதையும்"),
                paragraphs = listOf(
                    BilingualText(
                        "The oral groove, vestibule, cytostome and cytopharynx are related but distinct structures. Oral ciliary currents move bacteria and other suitably sized suspended particles toward the cytostome. New food vacuoles form near the cytopharyngeal region; the groove itself must not be described as a permanent digestive cavity.",
                        "வாய்ப்பள்ளம், வெஸ்டிப்யூல், சைட்டோஸ்டோம், சைட்டோஃபாரிங்ஸ் ஆகியவை தொடர்புடைய தனித்தனி அமைப்புகள். வாய்ப்புறக் குறுஇழை ஓட்டம் பாக்டீரியா போன்ற சிறுதுகள்களை சைட்டோஸ்டோம் நோக்கிக் கொண்டு செல்கிறது. சைட்டோஃபாரிங்ஸ் அருகே புதிய உணவுக் குமிழ்கள் உருவாகின்றன; வாய்ப்பள்ளமே நிரந்தரச் செரிமான அறை அல்ல."),
                    BilingualText(
                        "Vacuoles move through the cytoplasm as acidification and digestive events proceed. The organism assimilates useful molecules, while the cytoproct is a specialized egestion site for indigestible residue. Its normally closed cortical appearance should not be drawn as an always-open hole.",
                        "சைட்டோபிளாஸ்மத்தில் உணவுக் குமிழ்கள் நகரும் போது அமிலத்தன்மை மாற்றங்களும் செரிமான நிகழ்வுகளும் நடக்கின்றன. பயன்படும் மூலக்கூறுகள் உறிஞ்சப்படுகின்றன; செரியாத எச்சங்கள் செல் கழிவுவெளியேற்றப் பகுதி வழியாக வெளியேறுகின்றன. இயல்பில் மூடியிருக்கும் அதன் புறப்படல அமைப்பை நிரந்தரத் திறந்த துளையாக வரையக்கூடாது.")
                ),
                scientificSources = listOf(ORAL_ULTRASTRUCTURE, PARA_REVIEW)
            ),
            LessonSection(
                id = "cortical-avoidance-response",
                heading = BilingualText("Cortical organization and avoidance response",
                    "புறப்படல ஒழுங்கமைவும் தடையைத் தவிர்க்கும் எதிர்வினையும்"),
                paragraphs = listOf(
                    BilingualText(
                        "Basal bodies beneath the cell surface anchor the cilia; neighbouring units beat with phase offsets. A suitable anterior stimulus can depolarize the membrane and open voltage-gated calcium channels in the ciliary membrane. Calcium entering the cilia reverses the effective stroke, briefly producing backward swimming. As ciliary calcium falls, the cell reorients and swims forward; posterior stimulation may instead accelerate forward swimming.",
                        "செல் மேற்பரப்புக்குக் கீழுள்ள அடித்தள உடல்கள் குறுஇழைகளைத் தாங்குகின்றன; அடுத்தடுத்த குறுஇழைகள் கட்ட வேறுபாட்டுடன் அசைகின்றன. பொருத்தமான முன்புறத் தூண்டுதல் படலத்தின் உட்புற எதிர்மின்னழுத்தத்தைக் குறைத்து, குறுஇழைப் படலத்திலுள்ள மின்னழுத்தச் சார்பு கால்சியம் அயனிக் கால்வாய்களைத் திறக்கலாம். குறுஇழைக்குள் நுழையும் கால்சியம் செயல்திறன் அடியின் திசையை மாற்றுவதால் சிறிது நேரம் பின்னோக்கு நீந்தல் நிகழ்கிறது. கால்சியம் அளவு குறையும்போது உயிரி திசையை மாற்றி முன்னோக்கி நீந்துகிறது; பின்புறத் தூண்டுதல் முன்னோக்கு நீந்தலை வேகப்படுத்தலாம்."),
                    BilingualText(
                        "A moving schematic may use exaggerated cilia and slowed beat cycles to reveal metachronal coordination. Such animation explains the sequence; it cannot supply a measured native beat frequency, a microscopy-calibrated number of cilia or an experimentally verified migration speed.",
                        "மெட்டாக்ரோனல் ஒருங்கிணைப்பைக் காட்ட விளக்க இயக்கப்படத்தில் குறுஇழைகள் பெரிதாக்கப்பட்டும் அசைவு மெதுவாக்கப்பட்டும் இருக்கலாம். இக்காட்சி உண்மையான அசைவு அதிர்வெண், குறுஇழைகளின் அளவிடப்பட்ட எண்ணிக்கை அல்லது நீந்தும் வேகத்தை நிரூபிக்காது.")
                ),
                scientificSources = listOf(SWIMMING_REVIEW, PROTIST_MOVEMENT, PARA_REVIEW)
            ),
            LessonSection(
                id = "asexual-division-process",
                heading = BilingualText("Binary fission: cytological sequence",
                    "இருபிளவு: செல்நிலை நிகழ்வுகளின் வரிசை"),
                paragraphs = listOf(
                    BilingualText(
                        "During vegetative transverse binary fission, nuclear and cortical components must be apportioned to the daughter cells. The germline micronucleus undergoes mitotic division; the somatic macronucleus divides by a distinct process often described as amitosis, followed by transverse cytokinesis.",
                        "வளர்ச்சிநிலைக் குறுக்குத் திசை இருபிளவில் உட்கரு மற்றும் புறப்படல அமைப்புகள் இரு மகள் செல்களுக்கும் பகிரப்படுகின்றன. சிற்றுட்கரு மைட்டாசிஸ் மூலம் பிரிகிறது; பேருட்கரு பெரும்பாலும் அமிட்டாசிஸ் எனப்படும் வேறுபட்ட முறையில் பிரிந்து, பின்னர் குறுக்குச் சைட்டோகைனிசிஸ் நடைபெறுகிறது."),
                    BilingualText(
                        "Unlike conjugation, binary fission directly generates two descendants from one parent cell. The inherited cytoplasmic and cortical organization continues to mature after separation; an animation should not imply that every structure is built instantaneously during the final cleavage.",
                        "இணைவிலிருந்து மாறுபட்டு இருபிளவில் ஒரே தாய்செல்லிலிருந்து இரண்டு மகள் செல்கள் உருவாகின்றன. பிரிவுக்குப் பின்னரும் புறப்படல மற்றும் சைட்டோபிளாஸ்ம அமைப்புகள் முதிர்ச்சியடைகின்றன; இறுதி பிரிவின்போதே அனைத்து அமைப்புகளும் கணநேரத்தில் உருவாவதாக இயக்கப்படம் காட்டக்கூடாது.")
                ),
                scientificSources = listOf(PARA_REVIEW, OPENSTAX)
            ),
            LessonSection(
                id = "laboratory-reasoning",
                heading = BilingualText("Practical interpretation, evidence and misconceptions",
                    "செய்முறை விளக்கம், ஆதாரம், பொதுவான தவறான கருத்துகள்"),
                paragraphs = listOf(
                    BilingualText(
                        "In a live preparation, learners may see the cell outline, swimming path and large vacuoles, but a light-microscope view rarely resolves every subpellicular organ. Microscopic observations must be separated from inferred organ functions and from features shown solely on schematic teaching plates.",
                        "உயிர்மாதிரியில் மாணவர்கள் செல் எல்லை, நீந்தும் பாதை மற்றும் பெரிய நுண்குமிழ்களைப் பார்க்கலாம். ஆனால் ஒளிநுண்ணோக்கியில் பெல்லிக்கிளின் கீழுள்ள அனைத்து நுண் உறுப்புகளும் தெளிவாகத் தெரியாது. நேரடியாகக் கண்ட அமைப்புகளையும் ஊகித்த பணிகளையும் விளக்க வரைபடத்தில் மட்டும் காட்டியவற்றையும் வேறுபடுத்த வேண்டும்."),
                    BilingualText(
                        "Ask students to predict the effects of hypotonic freshwater on water balance, explain how coordinated ciliary beats create movement, and distinguish a food vacuole from a contractile vacuole. Check that conjugation is treated as genetic exchange rather than instantaneous population multiplication.",
                        "குறைந்த கரைசல் செறிவுடைய நன்னீரில் நீர்ச்சமநிலை எப்படி மாறும், குறுஇழைகளின் ஒருங்கிணைந்த அசைவு எவ்வாறு நகர்த்தும், உணவுக் குமிழுக்கும் சுருங்கும் நுண்குமிழுக்கும் வேறுபாடு என்ன என்பதைக் கணிக்கச் சொல்லவும். இணைவை உடனடி செல் எண்ணிக்கைப் பெருக்கம் என மாணவர்கள் தவறாகக் கருதுகிறார்களா என்பதையும் சோதிக்கவும்.")
                ),
                scientificSources = listOf(OPENSTAX, OSMO_REVIEW, PARA_REVIEW)
            ),
            LessonSection(
                id = "classroom-observation",
                heading = BilingualText("Microscopy: evidence and questions",
                    "நுண்ணோக்கி: கவனிப்பும் வினாக்களும்"),
                paragraphs = listOf(
                    BilingualText(
                        "In a suitable live freshwater preparation, students can look for the cell outline, swimming direction and ciliary movement. Visibility depends on magnification, illumination and specimen condition.",
                        "உகந்த நன்னீர் உயிர்மாதிரியில் மாணவர்கள் செல் வடிவம், நீந்தும் திசை மற்றும் குறுஇழை அசைவைக் கவனிக்கலாம். எந்த அமைப்பு தெளிவாகத் தெரியும் என்பது உருப்பெருக்கம், ஒளியமைப்பு மற்றும் மாதிரியின் நிலை ஆகியவற்றைப் பொறுத்தது."
                    ),
                    BilingualText(
                        "Ask learners to explain why contractile vacuoles are especially useful in freshwater, and how the macronucleus and micronucleus differ in function. Distinguish observed structures from inferred processes.",
                        "நன்னீரில் சுருங்கும் நுண்குமிழ்கள் ஏன் முக்கியம், பேருட்கரு மற்றும் சிற்றுட்கருவின் பணிகள் எவ்வாறு வேறுபடுகின்றன என மாணவர்களிடம் கேட்கலாம். நேரடியாகக் கண்ட அமைப்புகளையும் விளக்கத்தின் மூலம் ஊகிக்கப்படும் செயல்களையும் வேறுபடுத்தச் சொல்ல வேண்டும்."
                    )
                ),
                scientificSources = listOf(OPENSTAX, PARA_REVIEW)
            )
        )
    ).also { BilingualLessonContract.validate(it) }

    /** Lessons not yet authored must never be silently replaced with generic content. */
    fun forChapter(chapterId: String): NativeBilingualLesson? =
        if (chapterId == paramecium.lessonId) paramecium else null
}
