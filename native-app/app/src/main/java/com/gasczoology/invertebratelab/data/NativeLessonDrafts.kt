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
