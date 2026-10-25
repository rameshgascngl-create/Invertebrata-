package com.gasczoology.invertebratelab.data

/** Draft five-mark questions for Unit VII chapters. DRAFT_UNVERIFIED; not part of the validated 86. */
object FurtherQuestions {
    private fun q(chapterId: String, n: Int, en: String, ta: String, vararg points: Pair<String, String>) = A5Question(
        id = "$chapterId-A5-$n",
        chapterId = chapterId,
        question = BilingualText(en, ta),
        answerPoints = points.map { BilingualText(it.first, it.second) },
    )

    val byChapter: Map<String, List<A5Question>> = listOf<A5Question>(
        q("u7-minor-annelids", 1, "Write notes on the Echiuroidea.", "எக்கியூராய்டியா பற்றிக் குறிப்பு எழுதுக.",
            "They are marine spoon worms with a sausage-shaped, unsegmented trunk." to "அவை தொத்திறைச்சி வடிவ, கண்டமற்ற உடற்பகுதியுள்ள கடல் கரண்டிப் புழுக்கள்.",
            "A flattened, non-retractile proboscis collects food particles from the mud." to "தட்டையான, உள்ளிழுக்க இயலாத துதிக்கை சேற்றிலிருந்து உணவுத் துகள்களைச் சேகரிக்கிறது.",
            "One pair of ventral chaetae lies behind the mouth, and the blood system is closed." to "வாய்க்குப் பின் ஒரு இணை வயிற்றுப்புற சீட்டாக்கள் உள்ளன; இரத்தத் தொகுதி மூடியது.",
            "Excretion is by nephridia and anal sacs, and the larva is a trochophore." to "கழிவு நீக்கம் நெஃப்ரிடியா, குத்துப் பைகளால்; புழு ட்ரோக்கோஃபோர்.",
            "Bonellia shows sexual dimorphism: a large female and a tiny parasitic male, with sex determined by the environment." to "பொனெல்லியா பால் இருவடிவம் காட்டுகிறது: பெரிய பெண், சிறிய ஒட்டுண்ணி ஆண்; பால் சூழலால் தீர்மானிக்கப்படுகிறது."),
        q("u7-minor-annelids", 2, "Compare the Sipunculoidea and the Priapulida.", "சிப்புங்குலாய்டியாவையும் பிரையாப்புலிடாவையும் ஒப்பிடுக.",
            "Both are unsegmented marine worms with a retractable introvert." to "இரண்டும் உள்ளிழுக்கும் இன்ட்ரோவெர்ட்டுள்ள கண்டமற்ற கடற்புழுக்கள்.",
            "Sipunculans have tentacles around the mouth, whereas priapulids have scalids and teeth." to "சிப்புங்குலன்களுக்கு வாயைச் சுற்றி உணர்நீட்சிகள்; பிரையாப்புலிட்களுக்கு ஸ்கேலிடுகளும் பற்களும்.",
            "Sipunculans have a U-shaped gut and haemerythrin; priapulids have a straight gut." to "சிப்புங்குலன்களுக்கு U வடிவ குடலும் ஹீமெரித்ரினும்; பிரையாப்புலிட்களுக்கு நேரான குடல்.",
            "Sipunculans have a trochophore larva and are probably annelids." to "சிப்புங்குலன்களுக்கு ட்ரோக்கோஃபோர் புழு உண்டு; அவை ஒருவேளை வளைதசையுடலிகள்.",
            "Priapulids moult their cuticle and belong to the Ecdysozoa." to "பிரையாப்புலிட்கள் தோலை உரிக்கின்றன; எக்டைசோசோவாவைச் சேர்ந்தவை."),
        q("u7-leech", 1, "Describe the external features of Hirudinaria granulosa.", "ஹிருடினேரியா கிரானுலோசாவின் வெளி அமைப்பை விவரிக்க.",
            "It is a large freshwater leech with an elongated, dorsoventrally flattened body." to "இது நீளமான, முதுகு-வயிறு தட்டையான உடலுள்ள பெரிய நன்னீர் அட்டை.",
            "An anterior sucker surrounds the mouth, and a large posterior sucker lies at the hind end." to "முன் உறிஞ்சி வாயைச் சூழ்ந்துள்ளது; பெரிய பின் உறிஞ்சி பின் முனையில் உள்ளது.",
            "The body has thirty-four fixed segments, each divided externally into annuli." to "உடலுக்கு முப்பத்து நான்கு நிலையான கண்டங்கள்; ஒவ்வொன்றும் வெளியில் அனுலிகளாகப் பிரிந்துள்ளது.",
            "Chaetae and parapodia are absent, and the skin has small papillae." to "சீட்டாக்கள், பாராபோடியா இல்லை; தோலில் சிறு பாப்பிலாக்கள் உள்ளன.",
            "Ocelli lie at the front, and the male and female genital pores open on the underside." to "முன்புறம் ஓசெல்லிகள் உள்ளன; ஆண், பெண் இனப்பெருக்கத் துளைகள் அடிப்பகுதியில் திறக்கின்றன."),
        q("u7-leech", 2, "Explain the adaptations of the leech to blood-sucking.", "இரத்தம் உறிஞ்சலுக்கான அட்டையின் தகவமைப்புகளை விளக்குக.",
            "Suckers allow it to attach to the host and to move by looping." to "உறிஞ்சிகள் விருந்தோம்பியில் ஒட்டவும் வளைந்து நகரவும் உதவுகின்றன.",
            "Three toothed jaws cut a Y-shaped wound, and a muscular pharynx pumps the blood." to "பற்களுடன் மூன்று தாடைகள் Y வடிவக் காயத்தை வெட்டுகின்றன; தசைத் தொண்டை இரத்தத்தை செலுத்துகிறது.",
            "Saliva contains an anticoagulant and an anaesthetic." to "உமிழ்நீரில் இரத்த உறைதல் தடுப்பியும் உணர்வு நீக்கியும் உள்ளன.",
            "The large crop with caeca stores a big meal, and slow digestion is aided by bacteria." to "குருட்டுக் குழல்களுடன் பெரிய கிராப் பெரிய உணவைச் சேமிக்கிறது; பாக்டீரியாக்கள் உதவும் மெதுவான செரிமானம்.",
            "Nephridia remove excess water, so the meal is concentrated." to "நெஃப்ரிடியா மிகை நீரை நீக்குவதால் உணவு செறிவாகிறது."),
    ).groupBy { it.chapterId }
}
