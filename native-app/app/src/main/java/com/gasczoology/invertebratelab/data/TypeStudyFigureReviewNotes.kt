package com.gasczoology.invertebratelab.data

/**
 * Provisional student-QA figure cautions. These are not formal morphology,
 * Tamil or source-rights approvals. No original raster pixels are changed.
 *
 * Ten held type-study images are annotated here; the eleventh held image,
 * Leishmania, is a BookFigureLibrary figure and has its own limited-stage credit.
 */
object TypeStudyFigureReviewNotes {
    val held: Map<String, BilingualText> = mapOf(
        "paramecium/oral-apparatus" to BilingualText(
            "This 305 × 328-pixel crop cannot reliably resolve the oral groove, cytostome or cytopharynx. Consult the lesson's labelled explanation; do not copy unclear image labels.",
            "305 × 328 படப்புள்ளிகள் கொண்ட இப்படத்தில் வாய்ப்பள்ளம், செல்வாய், செல்தொண்டை ஆகியவற்றின் பெயர்க்குறிப்புகள் தெளிவாக இல்லை. பாட விளக்கத்தைப் பயன்படுத்துக; தெளிவற்ற பெயர்க்குறிப்புகளை நகலெடுக்க வேண்டாம்."),
        "sycon/external" to BilingualText(
            "Specimen photograph only: ostia, osculum and the Sycon body-wall layers are not adequately identified. Use the labelled canal-system plate for anatomy.",
            "இது மாதிரியின் ஒளிப்படம் மட்டுமே. ஆஸ்டியாக்கள், ஆஸ்குலம் மற்றும் சைகான் உடற்சுவர் அடுக்குகள் போதிய அளவில் குறிக்கப்படவில்லை. உடற்கூற்றிற்கு பெயர்குறிப்புள்ள நீர்க்கால்வாய் அமைப்புப் படத்தைப் பயன்படுத்துக."),
        "sycon/reproduction" to BilingualText(
            "The panels are not a fully keyed reproductive sequence. Identify gametes, fertilisation, amphiblastula development and settlement from the lesson text, not by guessing at individual panels.",
            "இந்தப் படத்தொகுப்பில் இனப்பெருக்க நிலைகளுக்கான முழுமையான பெயர்க்குறிப்புகள் இல்லை. இனச்செல்கள், கருவுறுதல், ஆம்பிபிளாஸ்டுலா வளர்ச்சி, நிலைபெறுதல் ஆகியவற்றைப் பாட உரையுடன் ஒப்பிட்டறிக."),
        "obelia/medusa" to BilingualText(
            "Low-detail medusa photograph: radial canals, manubrium and gonads cannot be verified from this image. It is not an accepted medusa-anatomy reference.",
            "இது தெளிவுத்திறன் குறைந்த மெடூசா ஒளிப்படம். ஆரக் கால்வாய்கள், மனுபிரியம், இனப்பெருக்கச் சுரப்பிகள் ஆகியவற்றை இதில் உறுதிசெய்ய முடியாது. உடற்கூற்று ஆதாரப் படமாக இதைப் பயன்படுத்த வேண்டாம்."),
        "obelia/life-cycle" to BilingualText(
            "Incomplete image. Correct sequence: colonial hydroid gonangia bud medusae; medusae produce gametes; fertilisation gives a planula larva, which settles and develops into a new hydroid colony.",
            "இப்படம் முழுமையான வாழ்க்கைச் சுழற்சியைக் காட்டவில்லை. சரியான வரிசை: குடியிருப்பின் கோனாங்கியங்களில் மெடூசாக்கள் அரும்புகின்றன; மெடூசாக்கள் இனச்செல்களை உருவாக்குகின்றன; கருவுறுதலுக்குப் பின் பிளானுலா இளநிலை தோன்றி, நிலைபெற்று புதிய ஹைட்ராய்டு குடியிருப்பாக வளர்கிறது."),
        "ascaris/female-internal" to BilingualText(
            "Internal labels are too faint for practical identification. A female Ascaris has a relatively straight intestine and paired, slender, extensively coiled ovaries, oviducts and uteri; distinguish them using the lesson text.",
            "இப்படத்தின் உள் உறுப்பு பெயர்க்குறிப்புகள் தெளிவாக இல்லை. பெண் அஸ்காரிஸில் ஓரளவு நேரான குடலும், இணையாக அமைந்த நீண்ட சுருண்ட சூலகங்கள், சூலகக் குழாய்கள், கருப்பைகளும் உள்ளன. பாட விளக்கத்தைப் பயன்படுத்தி அவற்றை வேறுபடுத்துக."),
        "earthworm/excretory" to BilingualText(
            "The nephridial types and their openings are not legible at the supplied image resolution. Do not identify the exact openings from these blurred labels.",
            "வழங்கப்பட்ட படத்தின் தெளிவுத்திறனில் நெஃப்ரிடியாக்களின் வகைகளையும் அவற்றின் திறப்புகளையும் வாசிக்க இயலவில்லை. மங்கலான பெயர்க்குறிப்புகளைக் கொண்டு திறப்புகளை அடையாளம் காண வேண்டாம்."),
        "pila/external" to BilingualText(
            "Anatomical correction: in Pila, the eyes are borne on small prominences on the outer side of the BASES of the cephalic tentacles, not at their distal tips. Treat any conflicting image pointer as erroneous.",
            "உடற்கூற்றுத் திருத்தம்: பைலாவில் கண்கள் தலை உணர்நீட்சிகளின் முனைகளில் அல்ல; அவற்றின் அடிப்பகுதியின் வெளிப்புறமுள்ள சிறு மேடுகளில் அமைந்துள்ளன. படத்தில் இதற்கு மாறான சுட்டுக்கோடு இருந்தால் அது தவறானது."),
        "asterias/internal" to BilingualText(
            "This composite overlaps organ systems; the digestive-cavity pointer may be misplaced. Do not infer the positions of the stomach, pyloric caeca or gonads from unclear arrows.",
            "இக்கூட்டுப் படத்தில் உறுப்பு மண்டலங்கள் ஒன்றின்மேல் ஒன்று காட்டப்பட்டுள்ளன; செரிமான அறையைச் சுட்டும் கோடு தவறான இடத்தில் இருக்கலாம். தெளிவற்ற அம்புக்குறிகளிலிருந்து இரைப்பை, பைலோரிக் குடற்கிளைகள், இனச்சுரப்பிகள் ஆகியவற்றின் இடத்தைத் தீர்மானிக்க வேண்டாம்."),
        "asterias/larval-development" to BilingualText(
            "The cropped historical figures do not clearly identify bipinnaria and brachiolaria stages. Use a separately labelled developmental comparison before making stage-specific claims.",
            "வெட்டப்பட்ட வரலாற்றுப் படங்களில் பைபின்னாரியா, பிராக்கியோலாரியா இளநிலைகள் தெளிவாக வேறுபடுத்தப்படவில்லை. இளநிலை பற்றிய குறிப்பிட்ட முடிவுகளுக்கு முன் பெயர்க்குறிப்புள்ள ஒப்பீட்டுப் படத்தைப் பயன்படுத்துக.")
    )
    init { check(held.size == 10) { "Ten type-study figures are held for QA correction" } }
}
