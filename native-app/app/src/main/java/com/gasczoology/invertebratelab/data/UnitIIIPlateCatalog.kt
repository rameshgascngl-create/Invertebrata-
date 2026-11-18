package com.gasczoology.invertebratelab.data

/**
 * Unit III's complete academically required plate inventory. An asset stem
 * names a *candidate* raster; existence is not scientific acceptance.
 * Never replace missing plates with fabricated Compose/Canvas/SVG anatomy.
 */
data class UnitIIIPlateSpec(
    val organismId: String,
    val chapterId: String,
    val plateId: String,
    val title: BilingualText,
    val learningAim: BilingualText
) {
    val assetStem: String get() = "type_studies/$organismId/$plateId"
}

object UnitIIIPlateCatalog {
    private fun spec(
        organism: String, chapter: String, plate: String,
        en: String, ta: String, aimEn: String, aimTa: String
    ) = UnitIIIPlateSpec(
        organism, chapter, plate, BilingualText(en, ta), BilingualText(aimEn, aimTa)
    )

    val plates: List<UnitIIIPlateSpec> = listOf(
        spec("fasciola", "u3-fasciola", "external",
            "01 · External features / body outline", "01 · புறத்தோற்றம் / உடல் வடிவம்",
            "Identify the cephalic cone, shoulders, oral sucker, ventral sucker and their relative positions.",
            "முன்கூம்பு, தோள்பகுதி, வாயுறிஞ்சி, வயிற்றுறிஞ்சி ஆகியவற்றின் இடங்களை வேறுபடுத்துக."),
        spec("fasciola", "u3-fasciola", "digestive-reproductive",
            "02 · Digestive and reproductive anatomy", "02 · செரிமான மற்றும் இனப்பெருக்க உறுப்புகள்",
            "Verify paired branched intestinal caeca, single branched ovary, tandem branched testes and lateral vitellaria. Never treat a digestive branch as a reproductive duct.",
            "இருகிளை குடற்பைகள், ஒற்றைக் கிளைத்த சூலகம், ஒன்றன்பின் ஒன்றாக அமைந்த கிளைத்த விந்தகங்கள், பக்கவாட்டு மஞ்சள்கருச் சுரப்பிகள் ஆகியவற்றைச் சரிபார்க்கவும்."),
        spec("fasciola", "u3-fasciola", "life-cycle",
            "03 · Life cycle and host transitions", "03 · வாழ்க்கைச் சுழற்சியும் புரவலன் மாற்றமும்",
            "Show freshwater hatching, lymnaeid snail stages, metacercaria on vegetation and adult bile-duct habitat.",
            "நன்னீரில் பொரிதல், நத்தைக்குள் வளர்ச்சி, நீர்ச்செடியிலுள்ள மெட்டாசெர்கேரியா மற்றும் பித்தநாள வளர்ந்த நிலை ஆகியவற்றைக் காட்டுக."),
        spec("ascaris", "u3-ascaris", "male-female-external",
            "04 · Adult male and female (external)", "04 · ஆண்–பெண் புறத்தோற்றம்",
            "Contrast larger straight-tailed female and smaller ventrally curved male; position three lips, vulva, anus and male cloaca correctly.",
            "நேரான பின்முனையுள்ள பெரிய பெண்ணையும் வளைந்த பின்முனையுள்ள சிறிய ஆணையும் ஒப்பிட்டு உறுப்புத் துளைகளைச் சரியான இடத்தில் காட்டுக."),
        spec("ascaris", "u3-ascaris", "female-internal",
            "05 · Female internal anatomy", "05 · பெண் புழுவின் உள் உறுப்புகள்",
            "Differentiate the relatively straight intestine from paired slender coiled ovaries, oviducts, uteri, vagina and vulva.",
            "ஓரளவு நேரான குடலையும் இரட்டைச் சுருண்ட சூலகங்கள், சூலகக் குழாய்கள், கருப்பைகள், யோனி, வல்வா ஆகியவற்றையும் வேறுபடுத்துக."),
        spec("ascaris", "u3-ascaris", "life-cycle",
            "06 · Environmental eggs and lung migration", "06 · சூழலிலுள்ள முட்டைகளும் நுரையீரல் இடம்பெயர்ச்சியும்",
            "Include egg development in soil, ingestion of embryonated eggs, portal/systemic circulation, lungs, reswallowing and adults in intestine.",
            "மண்ணில் முட்டை வளர்ச்சி, கருவுள்ள முட்டை விழுங்குதல், இரத்த ஓட்டம், நுரையீரல், மீண்டும் விழுங்குதல், சிறுகுடல் வளர்ந்த நிலை ஆகியவற்றைக் காட்டுக.")
    )

    fun forChapter(chapterId: String): List<UnitIIIPlateSpec> =
        plates.filter { it.chapterId == chapterId }

    /** Invariant: these six slots exactly match the authoritative native syllabus. */
    fun validateAgainstSyllabus() {
        require(plates.map { it.assetStem }.distinct().size == 6)
        for (organism in listOf("fasciola", "ascaris")) {
            val required = NativeCurriculumManifest.typeStudies.single {
                it.organismId == organism
            }
            require(plates.filter { it.organismId == organism }.map { it.plateId } ==
                required.requiredPlateIds)
            require(plates.filter { it.organismId == organism }.all { it.chapterId == required.lessonId })
        }
    }
}
