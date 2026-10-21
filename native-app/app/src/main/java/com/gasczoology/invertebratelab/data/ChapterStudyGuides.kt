package com.gasczoology.invertebratelab.data

/**
 * Evidence-aware learning outcomes and revision prompts for type-study chapters.
 * These are editorial teaching aids, not claims of anatomical-plate acceptance.
 */
internal data class ChapterStudyGuide(
    val objectives: List<BilingualText>,
    val reviewPrompts: List<BilingualText>,
) {
    init {
        require(objectives.size >= 3 && reviewPrompts.size >= 3)
        require((objectives + reviewPrompts).all {
            it.en.isNotBlank() && it.ta.isNotBlank()
        })
    }
}

internal object ChapterStudyGuides {
    private fun b(en: String, ta: String) = BilingualText(en, ta)

    val byChapterId: Map<String, ChapterStudyGuide> = mapOf(
        "u5-pila" to ChapterStudyGuide(
            objectives = listOf(
                b("Identify the shell, aperture, operculum and head-foot without confusing shell evidence with soft anatomy.",
                  "ஓடு, ஓட்டுவாய், ஓடுமூடி, தலை–பாதம் ஆகியவற்றை அறிந்து, ஓட்டுச் சான்றையும் மென் உடற்கூற்றையும் வேறுபடுத்துதல்."),
                b("Explain the distinct roles of ctenidium, pulmonary sac and osphradium.",
                  "சீனிடியம், நுரையீரல் பை, ஆஸ்ப்ரேடியம் ஆகியவற்றின் வெவ்வேறு பணிகளை விளக்குதல்."),
                b("Describe feeding, open circulation, excretion and separate sexes using anatomically correct terminology.",
                  "உணவூட்டம், திறந்த இரத்தச் சுற்றோட்டம், கழிவுநீக்கம், தனித்த ஆண்–பெண் நிலை ஆகியவற்றைச் சரியான உடற்கூறுச் சொற்களால் விளக்குதல்.")
            ),
            reviewPrompts = listOf(
                b("Which structures are visible in a dry shell, and which require a dissection?",
                  "உலர்ந்த ஓட்டில் எந்த அமைப்புகள் தெரியும்? எவற்றைப் பார்க்கப் பிரித்தாய்வு தேவை?"),
                b("Why must the osphradium not be labelled as the ctenidium?",
                  "ஆஸ்ப்ரேடியத்தைச் சீனிடியம் என்று ஏன் குறிக்கக் கூடாது?"),
                b("How does aquatic respiration differ from aerial respiration in Pila?",
                  "பைலாவில் நீர்ச் சுவாசமும் காற்றுச் சுவாசமும் எவ்வாறு வேறுபடுகின்றன?")
            )
        ),
        "u5-asterias" to ChapterStudyGuide(
            objectives = listOf(
                b("Distinguish oral and aboral surfaces and identify the madreporite, ambulacral grooves and tube feet.",
                  "வாய்ப்புறம், எதிர்வாய்ப்புறம் ஆகியவற்றை வேறுபடுத்தி, மேட்ரிபோரைட், அம்புலாக்ரல் பள்ளங்கள், குழாய்க்கால்களை அறிதல்."),
                b("Trace the water-vascular pathway from madreporite through stone and ring canals to the radial canals and podia.",
                  "மேட்ரிபோரைட்டிலிருந்து கற்குழாய், வளையக்குழாய், ஆரக்குழாய்கள் வழியாகக் குழாய்க்கால்கள் வரை நீர்க்குழாய் அமைப்பைப் பின்தொடர்தல்."),
                b("Differentiate digestive caeca from gonads and explain feeding and larval development.",
                  "செரிமானக் குடற்பைகளையும் இனச்சுரப்பிகளையும் வேறுபடுத்தி, உணவெடுப்பையும் இளநிலை வளர்ச்சியையும் விளக்குதல்.")
            ),
            reviewPrompts = listOf(
                b("Where are the mouth and madreporite situated relative to the two body surfaces?",
                  "வாயும் மேட்ரிபோரைட்டும் உடலின் எந்தெந்த புறங்களில் அமைந்துள்ளன?"),
                b("What is the difference between a radial canal, an ampulla and a tube foot?",
                  "ஆரக்குழாய், ஆம்புல்லா, குழாய்க்கால் ஆகியவற்றின் வேறுபாடு என்ன?"),
                b("Why should a generalized water-vascular diagram not be described as a complete Asterias dissection?",
                  "பொதுவான நீர்க்குழாய் அமைப்புப் படத்தை அஸ்டீரியாஸின் முழுமையான பிரித்தாய்வுப் படம் என்று ஏன் கூறக் கூடாது?")
            )
        )
    )
}
