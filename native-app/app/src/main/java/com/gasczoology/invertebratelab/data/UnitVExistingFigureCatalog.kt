package com.gasczoology.invertebratelab.data

/**
 * Original repository PNGs, copied byte-for-byte to the offline reader.
 * REVIEW ONLY: these are not substitutes for any still-unaccepted mandatory
 * anatomical masters. No generated bitmap is recaptioned as a verified specimen.
 */
internal data class UnitVExistingFigure(
    val organism: String,
    val id: String,
    val assetStem: String,
    val caption: BilingualText,
    val caution: BilingualText,
)

internal object UnitVExistingFigureCatalog {
    val figures: List<UnitVExistingFigure> = listOf(
        UnitVExistingFigure("pila","01_shell_ventral","unit5/candidates/pila/01_shell_ventral",
            BilingualText("Shell: ventral view","ஓடு: வயிற்றுப்புறத் தோற்றம்"),
            BilingualText("Shell orientation and labels require specimen-level verification.","ஓட்டின் திசையும் குறியீடுகளும் மாதிரி அடிப்படையில் சரிபார்க்கப்பட வேண்டும்.")),
        UnitVExistingFigure("pila","02_operculum_views","unit5/candidates/pila/02_operculum_views",
            BilingualText("Operculum: inner and outer surfaces","ஓடுமூடி: உள்ளும் புறமும்"),
            BilingualText("Operculum surfaces and growth markings are illustrative, not independently validated.","ஓடுமூடியின் இருபக்க அமைப்புகளும் வளர்ச்சிக் குறிகளும் தனியாகச் சரிபார்க்கப்படவில்லை.")),
        UnitVExistingFigure("pila","03_shell_dorsal","unit5/candidates/pila/03_shell_dorsal",
            BilingualText("Shell: dorsal view","ஓடு: முதுகுப்புறத் தோற்றம்"),
            BilingualText("Confirm spire, sutures and body-whorl orientation against a real Pila specimen.","சுருள், சுருளிணைப்பு, உடற்சுருள் ஆகியவற்றின் இடங்களை உண்மை மாதிரியுடன் ஒப்பிட வேண்டும்.")),
        UnitVExistingFigure("pila","04_animal_front","unit5/candidates/pila/04_animal_front",
            BilingualText("Anterior external view","முன்புற புறத்தோற்றம்"),
            BilingualText("Eye and tentacle placement in this generated image is suspect; do not memorise it as verified anatomy.","இந்த உருவாக்கப் படத்தில் கண் மற்றும் உணர்கொம்புகளின் இடங்கள் சந்தேகத்திற்குரியவை; உறுதிப்படுத்தப்பட்ட உடற்கூறாக மனப்பாடம் செய்ய வேண்டாம்.")),
        UnitVExistingFigure("pila","05_nervous_system","unit5/candidates/pila/05_nervous_system",
            BilingualText("Nervous-system candidate","நரம்பு மண்டல வரைவு"),
            BilingualText("Ganglion identities and nerve connections have not passed anatomical review.","நரம்புக் கணுக்கள் மற்றும் இணைப்புகளின் உடற்கூறியல் சரிபார்ப்பு நிலுவையில் உள்ளது.")),
        UnitVExistingFigure("pila","06_buccal_mass","unit5/candidates/pila/06_buccal_mass",
            BilingualText("Buccal mass and radula candidate","வாய்த்தசைத் தொகுதியும் ரேடுலாவும்"),
            BilingualText("The bitmap contains spelling/leader-line errors near the radula; follow the written lesson instead.","ரேடுலா அருகே படத்தில் எழுத்துப் பிழைகளும் குறிக்கோடு சிக்கல்களும் உள்ளன; பாட உரையைப் பின்பற்றவும்.")),
        UnitVExistingFigure("pila","07_digestive_system","unit5/candidates/pila/07_digestive_system",
            BilingualText("Digestive-system candidate","செரிமான மண்டல வரைவு"),
            BilingualText("Crop, stomach and intestinal connections need independent anatomical checking.","உணவுப் பை, இரைப்பை மற்றும் குடல் இணைப்புகள் தனியாகச் சரிபார்க்கப்பட வேண்டும்.")),
        UnitVExistingFigure("pila","08_respiratory_system","unit5/candidates/pila/08_respiratory_system",
            BilingualText("Mantle cavity and ctenidium candidate","மேன்டில் அறையும் சீனிடியமும்"),
            BilingualText("A duplicated/incorrect mantle-cavity label is printed in the bitmap; air and water routes remain unverified.","படத்தில் மேன்டில் அறை குறியீடு தவறாக அல்லது மீண்டும் உள்ளது; நீர் மற்றும் காற்றுப் பாதைகள் சரிபார்க்கப்படவில்லை.")),
        UnitVExistingFigure("pila","09_circulatory_system","unit5/candidates/pila/09_circulatory_system",
            BilingualText("Circulatory-system candidate","இரத்தச் சுற்றோட்ட வரைவு"),
            BilingualText("Confirm auricle, ventricle, arteries and haemocoel relationships against Pila anatomy.","ஆரிக்கிள், வென்ட்ரிக்கிள், தமனிகள், உடலிடைவெளிகளின் தொடர்புகளைச் சரிபார்க்க வேண்டும்.")),
        UnitVExistingFigure("pila","10_male_reproductive","unit5/candidates/pila/10_male_reproductive",
            BilingualText("Male reproductive-system candidate","ஆண் இனப்பெருக்க மண்டல வரைவு"),
            BilingualText("Reproductive-duct topology and organ proportions have not been verified.","இனப்பெருக்கக் குழாய்களின் இணைப்பும் உறுப்பு அளவுகளும் சரிபார்க்கப்படவில்லை.")),
        UnitVExistingFigure("pila","11_female_reproductive","unit5/candidates/pila/11_female_reproductive",
            BilingualText("Female reproductive-system candidate","பெண் இனப்பெருக்க மண்டல வரைவு"),
            BilingualText("Spermathecal labels appear duplicated and organ placements need review.","விந்துச் சேமிப்புறுப்பின் குறியீடு மீண்டும் வருகிறது; உறுப்புகளின் இடங்கள் ஆய்வு செய்யப்பட வேண்டும்.")),
        UnitVExistingFigure("pila","12_excretory_system","unit5/candidates/pila/12_excretory_system",
            BilingualText("Kidney and excretory-system candidate","சிறுநீரகமும் கழிவுநீக்க மண்டலமும்"),
            BilingualText("The renal opening, nephrostome and pericardial connections are not independently validated.","சிறுநீரகத் திறப்பு, நெஃப்ரோஸ்டோம், இதயச்சுற்றறை தொடர்புகள் தனியாகச் சரிபார்க்கப்படவில்லை.")),
        UnitVExistingFigure("asterias","external","unit5/candidates/asterias/external",
            BilingualText("Mixed internal-anatomy reference","கலந்த உள் உடற்கூறு மேற்கோள்"),
            BilingualText("Despite the original filename, this shows opened internal organs, NOT an intact oral/aboral external specimen.","கோப்புப் பெயர் புறத்தோற்றம் என இருந்தாலும் இது திறக்கப்பட்ட உள்ளுறுப்புகளைக் காட்டுகிறது; முழுமையான புறத்தோற்றம் அல்ல.")),
        UnitVExistingFigure("asterias","water_vascular","unit5/candidates/asterias/water_vascular",
            BilingualText("Water-vascular schematic candidate","நீர்க்குழாய் அமைப்பின் வரைவு"),
            BilingualText("Original figure calls the exposed canals an aboral view; orientation and stone-canal relationships are incorrect or ambiguous.","படத்தில் வெளிப்பட்ட கால்வாய்களை எதிர்வாய்ப்புறத் தோற்றம் எனக் கூறுகிறது; திசையும் கற்குழாய் தொடர்பும் தெளிவற்றவை.")),
        UnitVExistingFigure("asterias","digestive","unit5/candidates/asterias/digestive",
            BilingualText("Digestive-system schematic candidate","செரிமான அமைப்பின் வரைவு"),
            BilingualText("The original inset conflates eversible cardiac stomach with pyloric stomach; do not rely on this label.","படக்குறிப்பு வெளியே திரும்பக்கூடிய கார்டியாக் இரைப்பையையும் பைலோரிக் இரைப்பையையும் குழப்புகிறது.")),
        UnitVExistingFigure("asterias","nervous","unit5/candidates/asterias/nervous",
            BilingualText("Nervous-system candidate","நரம்பு அமைப்பின் வரைவு"),
            BilingualText("Circumoral/radial nerve relations and all leader endpoints require independent review.","வாயைச் சுற்றிய வளைய நரம்பு, ஆர நரம்புகள், குறிக்கோடுகளின் முனைகள் ஆகியவை சரிபார்க்கப்பட வேண்டும்."))
    )
    init {
        require(figures.size == 16)
        require(figures.map { it.assetStem }.distinct().size == figures.size)
        require(figures.map { it.organism to it.id }.distinct().size == figures.size)
        require(figures.count { it.organism == "pila" } == 12)
        require(figures.count { it.organism == "asterias" } == 4)
    }
    // A generated file name is not proof of anatomical subject identity:
    // notably Asterias external_REVIEW is an INTERNAL composite, so it
    // belongs with the internal-system discussion, never the external view.
    private val sectionByFigure = mapOf(
        "pila/01_shell_ventral" to "external",
        "pila/02_operculum_views" to "external",
        "pila/03_shell_dorsal" to "external",
        "pila/04_animal_front" to "external",
        "pila/08_respiratory_system" to "dual-respiration",
        "pila/06_buccal_mass" to "radula-and-gut",
        "pila/07_digestive_system" to "radula-and-gut",
        "pila/09_circulatory_system" to "heart-and-haemocoel",
        "pila/05_nervous_system" to "renal-and-sensory",
        "pila/12_excretory_system" to "renal-and-sensory",
        "pila/10_male_reproductive" to "reproduction-and-development",
        "pila/11_female_reproductive" to "reproduction-and-development",
        "asterias/water_vascular" to "water-vascular",
        "asterias/external" to "internal",
        "asterias/digestive" to "feeding-and-stomachs",
        "asterias/nervous" to "nerve-and-sense"
    )
    // Archived images are debug-only until reviewed. The mapping is available
    // to the editorial QA reader, but release chapters must not resolve these
    // assets: src/debug/assets is deliberately absent from release builds.
    fun forSection(chapterId: String, sectionId: String): List<UnitVExistingFigure> =
        emptyList()

    /** Editorial QA: return only existing archived figures mapped to this section.
     * Never use this API in a release chapter: these PNGs are debug-only.
     */
    fun forReviewSection(chapterId: String, sectionId: String): List<UnitVExistingFigure> {
        val organism = when (chapterId) {
            "u5-pila" -> "pila"
            "u5-asterias" -> "asterias"
            else -> return emptyList()
        }
        return figures.filter { figure ->
            figure.organism == organism &&
                sectionByFigure["${figure.organism}/${figure.id}"] == sectionId
        }
    }

    fun forChapter(chapterId: String): List<UnitVExistingFigure> = when (chapterId) {
        "u5-pila" -> figures.filter { it.organism == "pila" }
        "u5-asterias" -> figures.filter { it.organism == "asterias" }
        else -> emptyList()
    }
}
