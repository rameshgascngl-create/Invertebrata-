package com.gasczoology.invertebratelab.data

/** Exact downloadable scientific-source diagrams staged ONLY in debug Android assets.
 * A screenshot or legible PNG does NOT count as two-reviewer scientific acceptance.
 * The old masters and the 52-plate production gate are deliberately unchanged.
 */
internal object SourcedQaAnatomicalPlates {
    val byTypeStudy: Map<String, String> = mapOf(
        "sycon/reproduction" to "qa_source_plates/03_sycon_development",
        "asterias/internal" to "qa_source_plates/09_asterias_dissection",
        "asterias/larval-development" to "qa_source_plates/10_asterias_larval_reference",
    )
    val credit: Map<String, BilingualText> = mapOf(
        "sycon/reproduction" to BilingualText(
            "Sycon raphanus developmental stages · after F. E. Schulze, Encyclopædia Britannica (1911) · public domain historical image · Wikimedia Commons. Image illustrates the ovum through young sponge, not sperm transfer/fertilisation details. Scientific and Tamil sign-off pending.",
            "சைகான் ரஃபானஸ் வளர்ச்சி நிலைகள் · F. E. Schulze வரைபடத்தை அடிப்படையாகக் கொண்ட Encyclopædia Britannica (1911) படைப்பு · வரலாற்றுப் பொதுக்களப் படம் · விக்கிமீடியா காமன்ஸ். முட்டையிலிருந்து இளம் கடற்பஞ்சு வரையான வளர்ச்சி காட்டப்படுகிறது; விந்தணு பரிமாற்றம், கருவுறுதல் விவரங்கள் காட்டப்படவில்லை. அறிவியல், தமிழ் மதிப்பாய்வு நிலுவையில் உள்ளது."),
        "asterias/internal" to BilingualText(
            "Asterias rubens dissection · Hans Hillewaert (2007), CC BY-SA 3.0 · Wikimedia Commons; original numbered anatomical key 1–11: 1 pyloric stomach, 2 intestine, 3 rectal gland, 4 stone canal, 5 madreporite, 6 pyloric duct, 7 pyloric caecum, 8 cardiac stomach, 9 gonad, 10 ambulacral plates, 11 ampullae. Raster conversion to PNG; expert review pending.",
            "Asterias rubens உட்புற உடற்கூறு · Hans Hillewaert (2007), CC BY-SA 3.0 · விக்கிமீடியா காமன்ஸ். எண்கள் 1–11: 1 பைலோரிக் இரைப்பை, 2 குடல், 3 மலக்குடல் சுரப்பி, 4 கற்கால்வாய், 5 மாட்ரிபோரைக் தட்டு, 6 பைலோரிக் நாளம், 7 பைலோரிக் குடற்கிளை, 8 கார்டியாக் இரைப்பை, 9 இனச்சுரப்பி, 10 ஆம்புலாக்ரல் தகடுகள், 11 ஆம்புலாக்ரல் புடைப்புகள். PNG ஆக மாற்றப்பட்டது; நிபுணர் மதிப்பாய்வு நிலுவையில் உள்ளது."),
        "asterias/larval-development" to BilingualText(
            "Starfish larvae: Ernst Haeckel, Kunstformen der Natur (1904); Wikimedia Commons cropped edition by Chiswick Chap, CC BY-SA 3.0. Historical panels 2 scaphularia, 3 bipinnaria, 4 young brachiolaria; not a timed life-cycle diagram. PNG conversion; review pending.",
            "நட்சத்திரமீன் இளநிலைகள்: Ernst Haeckel, Kunstformen der Natur (1904); Chiswick Chap உருவாக்கிய விக்கிமீடியா காமன்ஸ் வெட்டுப் பதிப்பு, CC BY-SA 3.0. வரலாற்றுப் படங்கள்: 2 ஸ்காஃபுலாரியா, 3 பைபின்னாரியா, 4 இளம் பிராக்கியோலாரியா; இது காலவரிசை வரைபடமல்ல. PNG மாற்றம்; மதிப்பாய்வு நிலுவையில் உள்ளது.")
    )
    init {
        check(byTypeStudy.keys == credit.keys)
        check(byTypeStudy.size == 3)
        check(byTypeStudy.values.all { it.startsWith("qa_source_plates/") })
    }
}
