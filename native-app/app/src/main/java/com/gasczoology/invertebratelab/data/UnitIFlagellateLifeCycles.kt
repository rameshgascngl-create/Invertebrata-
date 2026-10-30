package com.gasczoology.invertebratelab.data

/**
 * U1.8 — teaching sequences, not real elapsed parasite-development times.
 * No original microscope/pencil artwork or figure hotspot positions are implied.
 * Biology based on CDC DPDx African trypanosomiasis and leishmaniasis cycles.
 */
data class FlagellateLifePhase(
    val id: String,
    val titleEn: String,
    val titleTa: String,
    val placeEn: String,
    val placeTa: String,
    val explanationEn: String,
    val explanationTa: String,
)

data class FlagellateLifeCycle(
    val id: String,
    val titleEn: String,
    val titleTa: String,
    val source: String,
    val phases: List<FlagellateLifePhase>,
) {
    init {
        require(phases.size >= 4) { "A lifecycle needs a meaningful sequence" }
        require(phases.map { it.id }.distinct().size == phases.size) { "Duplicate stage IDs" }
        require(phases.all {
            it.titleEn.isNotBlank() && it.titleTa.isNotBlank() &&
            it.explanationEn.isNotBlank() && it.explanationTa.isNotBlank()
        }) { "Incomplete English or Tamil text" }
    }
}

object UnitIFlagellateLifeCycles {
    val trypanosoma = FlagellateLifeCycle(
        id = "trypanosoma",
        titleEn = "Trypanosoma brucei — mammal and Glossina",
        titleTa = "டிரிப்பனோசோமா புரூசை — பாலூட்டியும் குளோசினா ஈயும்",
        source = "https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html",
        phases = listOf(
            FlagellateLifePhase(
                "metacyclic", "1. Infective metacyclic trypomastigote",
                "1. தொற்றை ஏற்படுத்தும் மெட்டாசைக்கிளிக் டிரிப்போமாஸ்டிகோட்",
                "Tsetse fly → mammalian skin", "ட்ஸெட்ஸெ ஈ → பாலூட்டியின் தோல்",
                "During a blood meal an infected tsetse fly injects metacyclic trypomastigotes into the skin. This is the infective stage for the mammal.",
                "தொற்றுள்ள ட்ஸெட்ஸெ ஈ இரத்த உணவு எடுக்கும்போது மெட்டாசைக்கிளிக் டிரிப்போமாஸ்டிகோட்டுகளைத் தோலுக்குள் செலுத்துகிறது. இது பாலூட்டிக்கான தொற்றுநிலை."
            ),
            FlagellateLifePhase(
                "blood", "2. Bloodstream trypomastigotes",
                "2. இரத்த டிரிப்போமாஸ்டிகோட்டுகள்",
                "Mammalian blood and lymph", "பாலூட்டியின் இரத்தமும் நிணநீரும்",
                "Parasites become extracellular bloodstream trypomastigotes; they spread through blood and lymph and may reach other body fluids.",
                "ஒட்டுண்ணிகள் செல்களுக்கு வெளியே வாழும் இரத்த டிரிப்போமாஸ்டிகோட்டுகளாக மாறி இரத்தம், நிணநீர் மற்றும் பிற உடல் நீர்மங்களில் பரவலாம்."
            ),
            FlagellateLifePhase(
                "binary-fission", "3. Longitudinal multiplication",
                "3. நீளவாக்குப் பிளவால் பெருக்கம்",
                "Mammalian extracellular compartment", "பாலூட்டியில் செல்களுக்கு வெளியே",
                "Bloodstream trypomastigotes multiply by binary fission. Unlike Trypanosoma cruzi, the African bloodstream forms replicate extracellularly.",
                "இரத்த டிரிப்போமாஸ்டிகோட்டுகள் இருபிளவின் மூலம் பெருகுகின்றன. T. cruzi போலல்லாமல், ஆப்பிரிக்க இரத்த நிலைகள் செல்களுக்கு வெளியே பெருகுகின்றன."
            ),
            FlagellateLifePhase(
                "ingestion", "4. Uptake by a tsetse fly",
                "4. ட்ஸெட்ஸெ ஈ ஒட்டுண்ணியை உட்கொள்ளுதல்",
                "Mammal → Glossina", "பாலூட்டி → குளோசினா ஈ",
                "A tsetse fly takes a blood meal and ingests circulating bloodstream trypomastigotes from an infected mammal.",
                "பாதிக்கப்பட்ட பாலூட்டியிலிருந்து இரத்த உணவு எடுக்கும் ட்ஸெட்ஸெ ஈ இரத்த டிரிப்போமாஸ்டிகோட்டுகளை உட்கொள்கிறது."
            ),
            FlagellateLifePhase(
                "procyclic", "5. Procyclic trypomastigotes",
                "5. புரோசைக்கிளிக் டிரிப்போமாஸ்டிகோட்டுகள்",
                "Tsetse midgut", "ட்ஸெட்ஸெ ஈயின் நடுக்குடல்",
                "In the tsetse midgut the parasite develops into multiplying procyclic trypomastigotes. These are vector stages, not the human bloodstream image.",
                "ட்ஸெட்ஸெ ஈயின் நடுக்குடலில் ஒட்டுண்ணிகள் பெருகும் புரோசைக்கிளிக் டிரிப்போமாஸ்டிகோட்டுகளாக மாறுகின்றன. இவை ஈக்குரிய நிலைகள்; மனித இரத்தப் படத்தில் காட்டப்பட்ட நிலைகள் அல்ல."
            ),
            FlagellateLifePhase(
                "epimastigote", "6. Epimastigote and salivary gland",
                "6. எபிமாஸ்டிகோட் மற்றும் உமிழ்நீர்ச் சுரப்பி",
                "Tsetse salivary glands", "ட்ஸெட்ஸெ ஈயின் உமிழ்நீர்ச் சுரப்பிகள்",
                "After leaving the midgut, parasite forms develop into epimastigotes and multiply in salivary glands, before differentiating into transmissible metacyclic trypomastigotes.",
                "நடுக்குடலைவிட்டு வெளியேறும் நிலைகள் எபிமாஸ்டிகோட்டுகளாக மாறி உமிழ்நீர்ச் சுரப்பிகளில் பெருகிய பின் பரவக்கூடிய மெட்டாசைக்கிளிக் டிரிப்போமாஸ்டிகோட்டுகளாக வேறுபடுகின்றன."
            ),
        )
    )

    val leishmania = FlagellateLifeCycle(
        id = "leishmania",
        titleEn = "Leishmania spp. — mammal and sand fly",
        titleTa = "லீஷ்மேனியா இனங்கள் — பாலூட்டியும் மணல் ஈயும்",
        source = "https://www.cdc.gov/dpdx/leishmaniasis/",
        phases = listOf(
            FlagellateLifePhase(
                "inoculation", "1. Promastigote inoculation",
                "1. புரோமாஸ்டிகோட் உட்செலுத்தல்",
                "Female phlebotomine sand fly → skin", "பெண் மணல் ஈ → தோல்",
                "An infected female sand fly inoculates infective promastigotes into mammalian skin while taking a blood meal.",
                "தொற்றுடைய பெண் மணல் ஈ இரத்த உணவு எடுக்கும்போது தொற்றை ஏற்படுத்தும் புரோமாஸ்டிகோட்டுகளைப் பாலூட்டியின் தோலுக்குள் செலுத்துகிறது."
            ),
            FlagellateLifePhase(
                "phagocytosis", "2. Uptake by macrophages",
                "2. மேக்ரோபேஜ்கள் ஒட்டுண்ணியை விழுங்குதல்",
                "Mammalian tissue macrophages", "பாலூட்டித் திசு மேக்ரோபேஜ்கள்",
                "Promastigotes are phagocytosed by macrophages and other mononuclear phagocytes at the site of infection.",
                "தொற்றுப் பகுதியில் புரோமாஸ்டிகோட்டுகளை மேக்ரோபேஜ்களும் பிற ஒற்றை உட்கரு விழுங்கிச் செல்களும் உட்கொள்கின்றன."
            ),
            FlagellateLifePhase(
                "amastigotes", "3. Intracellular amastigotes multiply",
                "3. செல்லுக்குள் அமாஸ்டிகோட் பெருக்கம்",
                "Mammalian mononuclear phagocytes", "பாலூட்டியின் விழுங்கிச் செல்கள்",
                "Inside host cells promastigotes transform into amastigotes, which replicate and spread to additional phagocytes.",
                "ஓம்புயிரிச் செல்களுக்குள் புரோமாஸ்டிகோட்டுகள் அமாஸ்டிகோட்டுகளாக மாறிப் பெருகி மற்ற விழுங்கிச் செல்களுக்கும் பரவுகின்றன."
            ),
            FlagellateLifePhase(
                "sandfly-meal", "4. Sand fly ingests infected cells",
                "4. மணல் ஈ பாதிக்கப்பட்ட செல்களை உட்கொள்ளுதல்",
                "Mammal → female sand fly", "பாலூட்டி → பெண் மணல் ஈ",
                "A female sand fly feeding on infected tissue acquires parasitized cells containing amastigotes.",
                "பாதிக்கப்பட்ட திசுவிலிருந்து இரத்த உணவு எடுக்கும் பெண் மணல் ஈ அமாஸ்டிகோட்டுகள் உள்ள செல்களை உட்கொள்கிறது."
            ),
            FlagellateLifePhase(
                "gut", "5. Promastigotes in the vector gut",
                "5. மணல் ஈ குடலில் புரோமாஸ்டிகோட்டுகள்",
                "Sand fly digestive tract", "மணல் ஈயின் செரிமானப் பாதை",
                "Amastigotes develop into flagellated promastigotes and multiply in the sand fly gut. Gut site varies between Leishmania and Viannia groups.",
                "மணல் ஈயின் குடலில் அமாஸ்டிகோட்டுகள் கசையிழையுடைய புரோமாஸ்டிகோட்டுகளாக மாறிப் பெருகுகின்றன. லீஷ்மேனியா மற்றும் வியானியா குழுக்களில் குடல் பகுதி வேறுபடலாம்."
            ),
            FlagellateLifePhase(
                "proboscis", "6. Transmission-ready promastigotes",
                "6. மீண்டும் பரவத் தயாரான புரோமாஸ்டிகோட்டுகள்",
                "Sand fly feeding apparatus", "மணல் ஈயின் உணவெடுக்கும் அமைப்பு",
                "Infective promastigotes are positioned for transmission during a later bite. The cycle returns to mammalian inoculation.",
                "தொற்றுநிலை புரோமாஸ்டிகோட்டுகள் அடுத்த கடியின் போது பரவுவதற்குத் தயாராகின்றன. சுழற்சி மீண்டும் பாலூட்டித் தோலில் உட்செலுத்தலுக்குத் திரும்புகிறது."
            ),
        )
    )

    fun forOrganism(id: String): FlagellateLifeCycle? = when (id) {
        "trypanosoma" -> trypanosoma
        "leishmania" -> leishmania
        else -> null
    }
}
