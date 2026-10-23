package com.gasczoology.invertebratelab.data

internal data class NuclearTerm(val name: BilingualText, val definition: BilingualText)

internal object ParameciumNuclearGlossary {
    val fission = listOf(
        NuclearTerm(BilingualText("Transverse binary fission", "குறுக்குத் திசை இருபிளவு"),
            BilingualText("Asexual division of one Paramecium cell into two daughters across its longitudinal axis.",
                "பாரமீசியம் செல்லின் நீள அச்சிற்குக் குறுக்காக ஒரு தாய்செல் இரண்டு மகள் செல்களாகப் பிரியும் பாலிலா இனப்பெருக்கம்.")),
        NuclearTerm(BilingualText("Micronucleus", "சிற்றுட்கரு"),
            BilingualText("Germline nucleus: mitosis in vegetative fission, meiosis during conjugation.",
                "இயல்பான இருபிளவில் மைட்டாசிஸும் இணைவின்போது ஒடுக்கற்பிரிவும் அடையும் மரபுவழி உட்கரு.")),
        NuclearTerm(BilingualText("Macronucleus", "பேருட்கரு"),
            BilingualText("Somatic nucleus supporting vegetative gene expression; its division is not conventional mitosis.",
                "செல்லின் இயல்பான மரபணுச் செயல்பாட்டை ஆதரிக்கும் உடலியக்க உட்கரு; இதன் பிரிவு வழக்கமான மைட்டாசிஸ் அல்ல."))
    )
    val conjugation = listOf(
        NuclearTerm(BilingualText("Conjugation", "இணைவு"),
            BilingualText("Temporary pairing and exchange of haploid nuclear material without immediate cell multiplication.",
                "இரு செல்கள் தற்காலிகமாக இணைந்து ஒற்றைமடிய உட்கருப் பொருளைப் பரிமாறும் நிகழ்வு; உடனடி செல் எண்ணிக்கைப் பெருக்கமல்ல.")),
        NuclearTerm(BilingualText("Synkaryon", "சின்கேரியான்"),
            BilingualText("Diploid nucleus formed by fusion of incoming and stationary haploid pronuclei.",
                "இடம்பெயர்ந்து வந்த மற்றும் நிலையான ஒற்றைமடிய முன்உட்கருக்கள் இணைந்து உருவாகும் இரட்டைமடிய உட்கரு.")),
        NuclearTerm(BilingualText("Exconjugant", "இணைவுக்குப் பின் பிரிந்த செல்"),
            BilingualText("One of the two cells after the conjugating pair separates; nuclear reorganisation follows.",
                "இணைந்திருந்த செல்கள் பிரிந்தபின் கிடைக்கும் ஒவ்வொரு செல்; பின்னர் உட்கரு மறுசீரமைப்பு நிகழ்கிறது."))
    )
}
