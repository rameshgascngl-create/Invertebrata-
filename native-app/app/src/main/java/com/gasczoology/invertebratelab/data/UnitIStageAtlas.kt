package com.gasczoology.invertebratelab.data

/**
 * U1.15 scientific TEACHING SCHEMATICS — explicitly NOT microscope scans,
 * scanned pencil masters, licensed reproduction, or approved raster hotspots.
 * Host/vector and organ location contracts constrain the Compose drawings.
 */
data class StageAtlasStructure(
    val id: String,
    val nameEn: String,
    val nameTa: String,
    val explanationEn: String,
    val explanationTa: String,
    val x: Float,
    val y: Float,
)

data class StageAtlasPlate(
    val id: String,
    val taxonEn: String,
    val taxonTa: String,
    val morphotype: String,
    val contextEn: String,
    val contextTa: String,
    val source: String,
    val structures: List<StageAtlasStructure>,
) {
    init {
        require(id in setOf("brucei-blood", "leishmania-amastigote", "leishmania-promastigote"))
        require(source.startsWith("https://www.cdc.gov/dpdx/"))
        require(structures.isNotEmpty() && structures.map { it.id }.distinct().size == structures.size)
        require(structures.all {
            it.nameEn.isNotBlank() && it.nameTa.isNotBlank() &&
                it.explanationEn.isNotBlank() && it.explanationTa.isNotBlank() &&
                it.x in 0f..1000f && it.y in 0f..560f
        })
    }
}

object UnitIStageAtlas {
    val plates: List<StageAtlasPlate> = listOf(
        StageAtlasPlate(
            id = "brucei-blood",
            taxonEn = "Trypanosoma brucei — bloodstream trypomastigote",
            taxonTa = "டிரிப்பனோசோமா புரூசை — இரத்த டிரிப்போமாஸ்டிகோட்",
            morphotype = "mammalian_bloodstream_trypomastigote",
            contextEn = "Mammalian blood: extracellular, elongated cell. Free flagellum extends from the anterior end; kinetoplast is posterior to the nucleus.",
            contextTa = "பாலூட்டியின் இரத்தம்: செல்லுக்கு வெளியே காணப்படும் நீண்ட செல். முன்முனையில் கசையிழை சுதந்திரமாக நீள்கிறது; கைனெட்டோபிளாஸ்ட் உட்கருவுக்குப் பின்புறத்தில் உள்ளது.",
            source = "https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html",
            structures = listOf(
                StageAtlasStructure("posterior", "Posterior tip", "பின்முனை",
                    "The tapered posterior end lies close to the kinetoplast, not the free anterior flagellum.",
                    "கூரான பின்முனை கைனெட்டோபிளாஸ்டுக்கு அருகில் உள்ளது; சுதந்திரமான முன்கசையிழை அருகில் அல்ல.", 162f, 403f),
                StageAtlasStructure("kinetoplast", "Posterior kinetoplast", "பின்புறக் கைனெட்டோபிளாஸ்ட்",
                    "Small mitochondrial DNA region near the posterior, clearly separate from the nucleus.",
                    "பின்புறத்தில் காணப்படும் சிறிய மைட்டோகாண்டிரிய DNA பகுதி; உட்கருவிலிருந்து தனித்தது.", 252f, 359f),
                StageAtlasStructure("nucleus", "Central nucleus", "மைய உட்கரு",
                    "One larger nucleus around the middle of the bloodstream cell.",
                    "இரத்த வடிவத்தின் நடுப்பகுதியில் உள்ள ஒரு பெரிய உட்கரு.", 488f, 281f),
                StageAtlasStructure("membrane", "Undulating membrane", "அலைவுச் சவ்வு",
                    "Wavy membrane following the attached part of the flagellum along the body margin.",
                    "உடல் ஓரத்தில் ஒட்டிய கசையிழையின் வழியே காணப்படும் அலைவுச் சவ்வு.", 605f, 217f),
                StageAtlasStructure("flagellum", "Anterior free flagellum", "முன்புறச் சுதந்திரக் கசையிழை",
                    "The flagellum continues beyond the anterior tip; it does not emerge from the posterior tip.",
                    "கசையிழை முன்முனையைக் கடந்து நீள்கிறது; பின்முனையிலிருந்து தோன்றுவதில்லை.", 840f, 92f),
            ),
        ),
        StageAtlasPlate(
            id = "leishmania-amastigote",
            taxonEn = "Leishmania spp. — intracellular amastigotes",
            taxonTa = "லீஷ்மேனியா இனங்கள் — செல்லுக்குள்ளான அமாஸ்டிகோட்டுகள்",
            morphotype = "intracellular_mammalian_amastigote",
            contextEn = "Mammalian macrophage: small round/oval parasite cells, each with a nucleus and kinetoplast; NO external long flagellum. A schematic cannot identify the species.",
            contextTa = "பாலூட்டியின் மேக்ரோபேஜ்: சிறிய வட்ட அல்லது முட்டைவடிவ ஒட்டுண்ணிகளில் உட்கருவும் கைனெட்டோபிளாஸ்டும் உள்ளன; வெளியே நீண்ட கசையிழை இல்லை. படத்தால் மட்டும் இனத்தை உறுதிசெய்ய முடியாது.",
            source = "https://www.cdc.gov/dpdx/leishmaniasis/",
            structures = listOf(
                StageAtlasStructure("host", "Host macrophage", "ஓம்புயிரி மேக்ரோபேஜ்",
                    "The large host cell is a phagocyte, not the body of one parasite.",
                    "பெரிய ஓம்புயிரிச் செல் ஒரு விழுங்கிச் செல்; ஒற்றை ஒட்டுண்ணியின் உடல் அல்ல.", 200f, 180f),
                StageAtlasStructure("host-nucleus", "Macrophage nucleus", "மேக்ரோபேஜ் உட்கரு",
                    "This large host nucleus must not be mistaken for a parasite nucleus.",
                    "இந்தப் பெரிய ஓம்புயிரி உட்கருவை ஒட்டுண்ணியின் உட்கருவாகக் கருதக் கூடாது.", 343f, 300f),
                StageAtlasStructure("parasite", "Intracellular amastigote", "செல்லுக்குள் அமாஸ்டிகோட்",
                    "A small oval Leishmania form inside a host phagocyte; genus-level depiction only.",
                    "ஓம்புயிரியின் விழுங்கிச் செல்லுக்குள் உள்ள சிறிய முட்டைவடிவ லீஷ்மேனியா; பேரின அளவிலான வரைபடம் மட்டும்.", 636f, 258f),
                StageAtlasStructure("nucleus", "Parasite nucleus", "ஒட்டுண்ணி உட்கரு",
                    "Each small amastigote has a parasite nucleus distinct from its kinetoplast.",
                    "ஒவ்வொரு சிறிய அமாஸ்டிகோட்டிலும் கைனெட்டோபிளாஸ்டிலிருந்து வேறுபட்ட உட்கரு உள்ளது.", 634f, 249f),
                StageAtlasStructure("kinetoplast", "Parasite kinetoplast", "ஒட்டுண்ணிக் கைனெட்டோபிளாஸ்ட்",
                    "Small mitochondrial DNA-containing dot in the amastigote, separate from the parasite nucleus.",
                    "அமாஸ்டிகோட்டில் ஒட்டுண்ணி உட்கருவிலிருந்து தனித்த சிறிய மைட்டோகாண்டிரிய DNA புள்ளி.", 660f, 270f),
            ),
        ),
        StageAtlasPlate(
            id = "leishmania-promastigote",
            taxonEn = "Leishmania spp. — sand-fly promastigote",
            taxonTa = "லீஷ்மேனியா இனங்கள் — மணல் ஈயின் புரோமாஸ்டிகோட்",
            morphotype = "flagellated_sand_fly_promastigote",
            contextEn = "Sand-fly stage: elongated extracellular promastigote; the kinetoplast lies anterior to the nucleus and a single free flagellum emerges anteriorly. No undulating membrane.",
            contextTa = "மணல் ஈ நிலை: நீண்ட செல்லுக்கு வெளியான புரோமாஸ்டிகோட்; உட்கருவுக்கு முன்பாகக் கைனெட்டோபிளாஸ்டும் முன்முனையில் ஒற்றைச் சுதந்திரக் கசையிழையும் உள்ளன. அலைவுச் சவ்வு இல்லை.",
            source = "https://www.cdc.gov/dpdx/leishmaniasis/",
            structures = listOf(
                StageAtlasStructure("posterior", "Posterior pointed end", "கூரான பின்முனை",
                    "The tapered posterior end lies opposite the free anterior flagellum.",
                    "கூரான பின்முனை, முன்புறச் சுதந்திரக் கசையிழைக்கு எதிர்த்திசையில் உள்ளது.", 334f, 423f),
                StageAtlasStructure("nucleus", "Central nucleus", "மைய உட்கரு",
                    "The parasite nucleus lies behind the anterior kinetoplast in this stage.",
                    "இந்த நிலையில் முன்கைனெட்டோபிளாஸ்டுக்குப் பின்னால் ஒட்டுண்ணியின் உட்கரு உள்ளது.", 529f, 313f),
                StageAtlasStructure("kinetoplast", "Anterior kinetoplast", "முன்புறக் கைனெட்டோபிளாஸ்ட்",
                    "Small mitochondrial DNA region lies toward the flagellar origin, anterior to the nucleus.",
                    "கசையிழை தோன்றும் முன்பகுதியை நோக்கி, உட்கருவுக்கு முன்னால் சிறிய மைட்டோகாண்டிரிய DNA பகுதி உள்ளது.", 665f, 244f),
                StageAtlasStructure("flagellum", "Free anterior flagellum", "முன்புறச் சுதந்திரக் கசையிழை",
                    "The long free anterior flagellum differentiates promastigote from the intracellular amastigote.",
                    "நீண்ட முன்கசையிழை, புரோமாஸ்டிகோட்டை செல்லுக்குள்ளான அமாஸ்டிகோட்டிலிருந்து வேறுபடுத்துகிறது.", 838f, 94f),
            ),
        ),
    )
    fun get(id: String): StageAtlasPlate? = plates.firstOrNull { it.id == id }
}
