package com.gasczoology.invertebratelab.data

/**
 * U1.10: REVIEW-ONLY taxonomically and stage-constrained anatomical explanations.
 * These descriptions DO NOT assign pixel endpoints, license drawings, or approve
 * any anatomy plate. A reviewer selects a structure before recording a pixel.
 */
data class AnatomyReviewStructure(
    val id: String,
    val en: String,
    val ta: String,
    val explanationEn: String,
    val explanationTa: String,
) {
    init {
        require(listOf(id, en, ta, explanationEn, explanationTa).all { it.isNotBlank() })
    }
    val narration: BilingualText get() = BilingualText(
        "$en. $explanationEn", "$ta. $explanationTa",
    )
}

data class AnatomyReviewPlate(
    val organism: String,
    val morphotype: String,
    val source: String,
    val structures: List<AnatomyReviewStructure>,
) {
    init {
        require(organism.isNotBlank() && morphotype.isNotBlank())
        require(source.startsWith("https://www.cdc.gov/dpdx/"))
        require(structures.isNotEmpty())
        require(structures.map { it.id }.distinct().size == structures.size)
    }
}

object UnitIAnatomyReviewDescriptions {
    private val trypanosoma = AnatomyReviewPlate(
        organism = "trypanosoma",
        morphotype = "mammalian_bloodstream_trypomastigote",
        source = "https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html",
        structures = listOf(
            AnatomyReviewStructure("nucleus", "Nucleus", "உட்கரு",
                "The single nucleus is usually near the middle of the elongated bloodstream trypomastigote. It is not the kinetoplast.",
                "நீண்ட இரத்த டிரிப்போமாஸ்டிகோட்டின் நடுப்பகுதியில் பொதுவாக ஓர் உட்கரு காணப்படுகிறது. இது கைனெட்டோபிளாஸ்ட் அல்ல."),
            AnatomyReviewStructure("kinetoplast", "Kinetoplast", "கைனெட்டோபிளாஸ்ட்",
                "A small mitochondrial DNA-containing structure near the posterior end, separate from the central nucleus.",
                "மைய உட்கருவிலிருந்து வேறுபட்ட, பின்முனைக்கு அருகிலுள்ள சிறிய மைட்டோகாண்டிரிய DNA அமைப்பு."),
            AnatomyReviewStructure("undulating-membrane", "Undulating membrane", "அலைவுச் சவ்வு",
                "A membrane along the cell margin associated with the attached portion of the flagellum; it contributes to movement.",
                "செல் விளிம்பில் கசையிழையின் ஒட்டிய பகுதியுடன் தொடர்புடைய அலைவுச் சவ்வு இயக்கத்திற்கு உதவுகிறது."),
            AnatomyReviewStructure("flagellum", "Free anterior flagellum", "முன்புறச் சுதந்திரக் கசையிழை",
                "The flagellum follows the undulating membrane and extends beyond the anterior end as a free portion.",
                "கசையிழை அலைவுச் சவ்வுடன் சென்று முன்முனையைக் கடந்து சுதந்திரமாக நீள்கிறது."),
            AnatomyReviewStructure("posterior", "Posterior end", "பின்முனை",
                "The posterior cell end is close to the small kinetoplast, not to the free flagellum emerging at the anterior end.",
                "செல்லின் பின்முனை சிறிய கைனெட்டோபிளாஸ்டுக்கு அருகில் உள்ளது; முன்புறச் சுதந்திரக் கசையிழை இங்கு தோன்றுவதில்லை."),
        ),
    )
    private val leishmania = AnatomyReviewPlate(
        organism = "leishmania",
        morphotype = "intracellular_mammalian_amastigote",
        source = "https://www.cdc.gov/dpdx/leishmaniasis/",
        structures = listOf(
            AnatomyReviewStructure("amastigote", "Amastigote boundary", "அமாஸ்டிகோட் செல் எல்லை",
                "A small round or oval intracellular parasite. A long external flagellum is not present in the mammalian amastigote.",
                "பாலூட்டிச் செல்லுக்குள் உள்ள சிறிய வட்ட அல்லது முட்டைவடிவ ஒட்டுண்ணி. அமாஸ்டிகோட்டில் வெளியே நீண்ட கசையிழை இல்லை."),
            AnatomyReviewStructure("nucleus", "Parasite nucleus", "ஒட்டுண்ணியின் உட்கரு",
                "An amastigote contains a nucleus separate from the much smaller kinetoplast; do not confuse it with the macrophage nucleus.",
                "ஒவ்வோர் அமாஸ்டிகோட்டிலும் சிறிய கைனெட்டோபிளாஸ்டிலிருந்து வேறுபட்ட உட்கரு உள்ளது; மேக்ரோபேஜ் உட்கருவுடன் குழப்பக் கூடாது."),
            AnatomyReviewStructure("kinetoplast", "Kinetoplast", "கைனெட்டோபிளாஸ்ட்",
                "A distinctive mitochondrial DNA region within each amastigote, separate from the parasite nucleus.",
                "ஒவ்வோர் அமாஸ்டிகோட்டிலும் அதன் உட்கருவிலிருந்து வேறான மைட்டோகாண்டிரிய DNA பகுதி."),
            AnatomyReviewStructure("macrophage", "Host macrophage", "ஓம்புயிரி மேக்ரோபேஜ்",
                "This is the mammalian host phagocyte containing amastigotes, NOT an organ of the parasite.",
                "இது அமாஸ்டிகோட்டுகளை உள்ளடக்கிய பாலூட்டியின் விழுங்கிச் செல்; ஒட்டுண்ணியின் உறுப்பு அல்ல."),
        ),
    )

    fun forOrganism(organism: String): AnatomyReviewPlate? = when (organism) {
        trypanosoma.organism -> trypanosoma
        leishmania.organism -> leishmania
        else -> null
    }

    /**
     * Promastigotes in the sand fly require a DIFFERENT plate: the existing
     * Leishmania source raster depicts intracellular amastigotes only.
     */
    fun isCompatible(organism: String, morphotype: String, structureId: String): Boolean {
        val plate = forOrganism(organism) ?: return false
        return morphotype == plate.morphotype && plate.structures.any { it.id == structureId }
    }
}
