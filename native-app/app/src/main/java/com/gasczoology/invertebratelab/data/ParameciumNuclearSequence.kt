package com.gasczoology.invertebratelab.data

/**
 * Sequential textbook account of Paramecium nuclear processes.
 *
 * These are explanatory stages, NOT verified image plates or timed animations.
 * The nuclear sequence can differ among species and mating systems; the
 * description avoids imposing a single numerical fate on every Paramecium.
 * English/Tamil scientific editorial sign-off remains required.
 */
internal data class ParameciumNuclearStage(
    val id: String,
    val heading: BilingualText,
    val explanation: BilingualText,
)

internal object ParameciumNuclearSequence {
    val binaryFission: List<ParameciumNuclearStage> = listOf(
        ParameciumNuclearStage("growth",
            BilingualText("1. Growth and preparation", "1. வளர்ச்சியும் பிரிவுக்கான ஆயத்தமும்"),
            BilingualText(
                "The parent cell grows and prepares to duplicate its cortical and feeding structures. Binary fission is asexual cell multiplication, not an exchange between two cells.",
                "தாய்செல் வளர்ந்து, அதன் புறப்படல மற்றும் உணவெடுக்கும் அமைப்புகளை இரட்டிப்பாக்க ஆயத்தமாகிறது. இருபிளவு என்பது பாலிலா செல் பெருக்கம்; இரண்டு செல்களுக்கிடையிலான மரபணுப் பரிமாற்றம் அல்ல.")),
        ParameciumNuclearStage("micronuclear-division",
            BilingualText("2. Micronuclear mitosis", "2. சிற்றுட்கருவின் மைட்டாசிஸ் பிரிவு"),
            BilingualText(
                "The germline micronucleus divides mitotically so that its genetic material can be distributed to the two daughter cells.",
                "மரபுவழிச் சிற்றுட்கரு மைட்டாசிஸ் முறையில் பிரிந்து, அதன் மரபணுப் பொருள் இரண்டு மகள் செல்களுக்கும் பகிரப்பட உதவுகிறது.")),
        ParameciumNuclearStage("macronuclear-division",
            BilingualText("3. Macronuclear division", "3. பேருட்கருவின் பிரிவு"),
            BilingualText(
                "The somatic macronucleus elongates and partitions by a division process distinct from ordinary micronuclear mitosis. Do not depict it as a second conventional mitotic spindle.",
                "உடலியக்கப் பேருட்கரு நீண்டு, சிற்றுட்கருவின் வழக்கமான மைட்டாசிஸிலிருந்து வேறுபட்ட முறையில் பிரிகிறது. அதனை மற்றொரு வழக்கமான மைட்டாசிஸ் கதிர்க்கோலாக வரையக் கூடாது.")),
        ParameciumNuclearStage("cortical-division",
            BilingualText("4. Oral and cortical reorganization", "4. வாயமைப்பும் புறப்படலமும் மறுசீரமைதல்"),
            BilingualText(
                "New oral and cortical components are organized for the future daughters. The exact sequence of stomatogenesis depends on the species and is not established by an external-outline sketch.",
                "புதிய மகள் செல்களுக்கான வாயமைப்பு மற்றும் புறப்படலக் கூறுகள் ஒழுங்குபடுத்தப்படுகின்றன. புதிய வாயமைப்பு உருவாகும் துல்லிய வரிசை இனத்திற்கேற்ப மாறலாம்; புறவடிவ வரைபடம் மட்டும் அதை நிரூபிக்காது.")),
        ParameciumNuclearStage("cytokinesis",
            BilingualText("5. Transverse constriction and separation", "5. குறுக்குச் சுருக்கமும் பிரிதலும்"),
            BilingualText(
                "A transverse furrow divides the parent into two daughter cells. Each must receive the appropriate nuclear and cortical systems; the daughters then resume independent growth.",
                "குறுக்காக உருவாகும் பிளவுப் பள்ளம் தாய்செல்லை இரண்டு மகள் செல்களாகப் பிரிக்கிறது. ஒவ்வொரு மகள் செல்லும் உரிய உட்கரு மற்றும் புறப்படல அமைப்புகளைப் பெற்று தனித்த வளர்ச்சியைத் தொடர்கிறது."))
    )

    val conjugation: List<ParameciumNuclearStage> = listOf(
        ParameciumNuclearStage("pairing",
            BilingualText("1. Pairing of compatible cells", "1. இணக்கமான செல்கள் இணைதல்"),
            BilingualText(
                "Two compatible Paramecium cells make temporary contact at their oral regions and form a cytoplasmic connection. The pair still consists of two individuals.",
                "இணக்கமான இரண்டு பாரமீசியம் செல்கள் வாய்ப்பகுதிகளில் தற்காலிகமாக இணைந்து, செல்திரவத் தொடர்பை ஏற்படுத்துகின்றன. இந்த நிலையிலும் அவை இரண்டு தனி உயிரிகளே.")),
        ParameciumNuclearStage("meiosis",
            BilingualText("2. Micronuclear meiosis", "2. சிற்றுட்கருவின் ஒடுக்கற்பிரிவு"),
            BilingualText(
                "Each partner's micronuclear lineage undergoes meiosis to produce haploid nuclear products. In the conventional P. caudatum account, most products degenerate and one functional haploid nucleus remains in each partner.",
                "ஒவ்வொரு இணைச்செல்லிலும் சிற்றுட்கரு ஒடுக்கற்பிரிவடைந்து ஒற்றைமடிய உட்கருக் கூறுகளை உருவாக்குகிறது. பாரம்பரிய P. caudatum விளக்கத்தில் பெரும்பாலான கூறுகள் சிதைந்து, ஒவ்வொரு செல்லிலும் ஒரு செயல்படும் ஒற்றைமடிய உட்கரு எஞ்சுகிறது.")),
        ParameciumNuclearStage("pronuclei",
            BilingualText("3. Formation of stationary and migratory pronuclei", "3. நிலையான மற்றும் இடம்பெயரும் முன்உட்கருக்கள் உருவாதல்"),
            BilingualText(
                "The surviving haploid nucleus divides to form a stationary and a migratory pronucleus. These are transient nuclear roles, not permanent male and female organs.",
                "எஞ்சிய ஒற்றைமடிய உட்கரு பிரிந்து நிலையான முன்உட்கருவையும் இடம்பெயரும் முன்உட்கருவையும் உருவாக்குகிறது. இவை தற்காலிக உட்கருப் பணிகள்; நிலையான ஆண், பெண் உறுப்புகள் அல்ல.")),
        ParameciumNuclearStage("exchange",
            BilingualText("4. Reciprocal exchange", "4. இருவழி முன்உட்கருப் பரிமாற்றம்"),
            BilingualText(
                "The partners exchange migratory pronuclei through their cytoplasmic connection. A drawing must show exchange between the two cells, not fusion of their entire cell bodies.",
                "இணைச்செல்கள் செல்திரவத் தொடர்பு வழியாக இடம்பெயரும் முன்உட்கருக்களைப் பரிமாறிக் கொள்கின்றன. வரைபடத்தில் முழுச் செல்களும் ஒன்றாக இணைவதாக அல்லாமல், இரண்டு செல்களுக்கிடையிலான உட்கருப் பரிமாற்றமாகக் காட்ட வேண்டும்.")),
        ParameciumNuclearStage("synkaryon",
            BilingualText("5. Fusion of pronuclei", "5. முன்உட்கருக்கள் இணைதல்"),
            BilingualText(
                "Within each partner, the incoming migratory pronucleus fuses with the resident stationary pronucleus to form a diploid synkaryon (zygote nucleus). Each partner forms its own synkaryon.",
                "ஒவ்வொரு இணைச்செல்லிலும் வந்தடைந்த இடம்பெயரும் முன்உட்கரு, அங்குள்ள நிலையான முன்உட்கருவுடன் இணைந்து இரட்டைமடிய சின்கேரியான் (கருவுட்கரு) உருவாகிறது. இரண்டு செல்களிலும் தனித்தனியாகச் சின்கேரியான் உருவாகிறது.")),
        ParameciumNuclearStage("separation",
            BilingualText("6. Separation of exconjugants", "6. இணைவு முடிந்த செல்கள் பிரிதல்"),
            BilingualText(
                "The partners separate as two exconjugants. Conjugation has exchanged genetic material but has not itself doubled the number of cells.",
                "இணைவு முடிந்த பின்னர் இரண்டு செல்களும் தனித்தனியாகப் பிரிகின்றன. இணைவு மரபணுப் பொருளைப் பரிமாறியுள்ளது; ஆனால் இணைவு நிகழ்வு தானாகவே செல் எண்ணிக்கையை இரட்டிப்பாக்கவில்லை.")),
        ParameciumNuclearStage("reorganization",
            BilingualText("7. Post-conjugation nuclear reorganization", "7. இணைவுக்குப் பிந்தைய உட்கரு மறுசீரமைப்பு"),
            BilingualText(
                "Synkaryon descendants undergo further divisions and differentiate into new micronuclear and macronuclear lineages; the old macronuclear material is broken down. Do not show a mature replacement macronucleus before the necessary nuclear reorganization.",
                "சின்கேரியானிலிருந்து உருவாகும் உட்கருக்கள் மேலும் பிரிந்து புதிய சிற்றுட்கரு மற்றும் பேருட்கரு வழித்தோன்றல்களாக வேறுபடுகின்றன; பழைய பேருட்கருப் பொருள் சிதைகிறது. தேவையான உட்கரு மறுசீரமைப்பிற்கு முன்பே முழுமையான புதிய பேருட்கரு உருவானதாகக் காட்டக்கூடாது.")),
        ParameciumNuclearStage("subsequent-fissions",
            BilingualText("8. Later vegetative divisions", "8. பின்னர் நடைபெறும் இயல்பான இருபிளவுகள்"),
            BilingualText(
                "Subsequent cell divisions and maturation establish the vegetative descendants. Exact nuclear division counts and final progeny numbers vary across species descriptions and must be attributed to the named organism.",
                "பின்னர் நடைபெறும் செல் பிரிவுகளும் முதிர்ச்சியும் இயல்பாக வளரும் வழித்தோன்றல்களை உருவாக்குகின்றன. உட்கருப் பிரிவு எண்ணிக்கையும் இறுதி மகள் செல் எண்ணிக்கையும் இன விளக்கத்திற்கேற்ப மாறலாம்; அவற்றைக் குறிப்பிட்ட இனத்துடன் மட்டுமே இணைத்துக் கூற வேண்டும்."))
    )

    init {
        check(binaryFission.map { it.id }.distinct().size == binaryFission.size)
        check(conjugation.map { it.id }.distinct().size == conjugation.size)
        check((binaryFission + conjugation).all {
            it.heading.english.isNotBlank() && it.heading.tamil.isNotBlank() &&
                it.explanation.english.isNotBlank() && it.explanation.tamil.isNotBlank()
        })
    }
}
