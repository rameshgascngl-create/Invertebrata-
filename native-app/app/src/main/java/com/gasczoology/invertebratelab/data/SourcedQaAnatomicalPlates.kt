package com.gasczoology.invertebratelab.data

/** Owner-approved, credited scientific-source diagrams in all Android build variants.
 * The eleven original raster masters remain preserved for history and fallback.
 * Independent scientific/Tamil reviews and source licences are tracked separately.
 */
internal object SourcedQaAnatomicalPlates {
    val byTypeStudy: Map<String, String> = mapOf(
        "sycon/reproduction" to "qa_source_plates/03_sycon_development",
        "asterias/internal" to "qa_source_plates/09_asterias_dissection",
        "asterias/larval-development" to "qa_source_plates/10_asterias_larval_reference",
        "paramecium/oral-apparatus" to "qa_source_plates/01_paramecium_oral_sourced",
        "sycon/external" to "qa_source_plates/02_sycon_external_sourced",
        "obelia/medusa" to "qa_source_plates/04_obelia_medusa_sourced",
        "obelia/life-cycle" to "qa_source_plates/05_obelia_lifecycle_sourced",
        "ascaris/female-internal" to "qa_source_plates/06_ascaris_female_sourced",
        "earthworm/excretory" to "qa_source_plates/07_earthworm_excretory_sourced",
        "pila/external" to "qa_source_plates/08_pila_external_sourced",
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
            "நட்சத்திரமீன் இளநிலைகள்: Ernst Haeckel, Kunstformen der Natur (1904); Chiswick Chap உருவாக்கிய விக்கிமீடியா காமன்ஸ் வெட்டுப் பதிப்பு, CC BY-SA 3.0. வரலாற்றுப் படங்கள்: 2 ஸ்காஃபுலாரியா, 3 பைபின்னாரியா, 4 இளம் பிராக்கியோலாரியா; இது காலவரிசை வரைபடமல்ல. PNG மாற்றம்; மதிப்பாய்வு நிலுவையில் உள்ளது."),
        "paramecium/oral-apparatus" to BilingualText(
            "Deuterostome, Paramecium caudatum diagram, CC BY-SA 4.0, Wikimedia Commons (Paramecium diagram.svg). PNG conversion and caption, no anatomical relabelling. Complete cell view; mouth, buccal overture and cytostome are distinct, but this is NOT an enlarged oral-apparatus dissection. Scientific/Tamil review pending.",
            "Deuterostome வரைந்த பாரமீசியம் முழுச் செல் வரைபடம், CC BY-SA 4.0, விக்கிமீடியா காமன்ஸ் (Paramecium diagram.svg). PNG மாற்றமும் வெளிப்புற விளக்கமும் மட்டும்; உடற்கூற்றுப் பெயர்க்குறிப்புகள் மாற்றப்படவில்லை. செல்வாய், வாய்த்துளை அமைப்பு முதலியவற்றைக் காட்டும் முழுச் செல் படம்; பெரிதாக்கிய வாயுறுப்பு வெட்டு அல்ல. அறிவியல், தமிழ் மதிப்பாய்வு நிலுவை."),
        "sycon/external" to BilingualText(
            "Kelvinsong, Sea sponge diagram.svg, CC BY-SA 3.0, Wikimedia Commons. Derived source image rasterised to PNG. This is a GENERALISED SYCONOID sponge, not a confirmed Sycon species specimen; osculum and water-canal architecture are clear. Editorial and rights sign-off pending.",
            "Kelvinsong எழுதிய Sea sponge diagram.svg, CC BY-SA 3.0, விக்கிமீடியா காமன்ஸ்; PNG வடிவமாக மாற்றப்பட்டது. இது சைகான் போன்ற சைகோனாய்டு கடற்பஞ்சின் பொதுவான அமைப்பு; குறிப்பிட்ட Sycon சிற்றின மாதிரி என உறுதிப்படுத்த முடியாது. ஆஸ்குலம், நீர்க்கால்வாய்கள் தெளிவாகக் காணலாம். மதிப்பாய்வு நிலுவை."),
        "obelia/medusa" to BilingualText(
            "Charles Wesley Hargitt (1905), Obelia diaphana (= O. geniculata), side and oral historical drawings, FMIB / University of Washington, public domain. Source linework is archival and requires zoom; individual anatomy must be taught from the chapter key, not invented label arrows. Scientific and Tamil review pending.",
            "Charles Wesley Hargitt (1905) வரைந்த ஒபீலியா டயஃபனா (= O. geniculata) மெடூசாவின் பக்க, வாய்ப்புறத் தோற்றங்கள்; வாஷிங்டன் பல்கலைக்கழக FMIB, பொதுக்களம். வரலாற்று வரைபடம் என்பதால் பெரிதாக்கிப் பார்க்கவும்; இல்லாத அம்புக்குறிகள் கற்பனை செய்து உறுப்புகளை அடையாளம் காண வேண்டாம். அறிவியல், தமிழ் மதிப்பாய்வு நிலுவை."),
        "obelia/life-cycle" to BilingualText(
            "Source figures: Obelia dichotoma colony and O. diaphana medusa, historical FMIB/Charles Wesley Hargitt (1905), public domain. Eight-stage cycle arrows, zygote, planula and polyp icons are NEW ORIGINAL SCHEMATIC, not source anatomical renderings. Colony illustration repeated for context; gonangium is NOT directly labelled in the historical drawing. Expert review pending.",
            "ஆதாரப் படங்கள்: Obelia dichotoma குடியிருப்பு, O. diaphana மெடூசா, FMIB / Charles Wesley Hargitt (1905), பொதுக்களம். எட்டு நிலைச் சுழற்சி, கருமுட்டை, பிளானுலா, இளம் பாலிப் குறியீடுகள் புதிய விளக்கப்படத்திட்டம்; அசல் உடற்கூறு வரைபடங்கள் அல்ல. குடியிருப்புப் படம் சூழலுக்காக மீண்டும் பயன்படுத்தப்பட்டுள்ளது; கோனாங்கியம் நேரடியாகச் சுட்டப்படவில்லை. மதிப்பாய்வு நிலுவை."),
        "ascaris/female-internal" to BilingualText(
            "Paul Pfurtscheller, Nematodes Ascaris lumbricoides, Table 38a, pre-1927, public domain (Wikimedia Commons). Historical full plate contains female AND comparative male views; female organ cutaway is on the LEFT. Do not treat the right male figure as female anatomy. Editorial review pending.",
            "Paul Pfurtscheller எழுதிய Ascaris lumbricoides, தட்டு 38a, 1927-க்கு முன், பொதுக்களம் (விக்கிமீடியா காமன்ஸ்). வரலாற்றுத் தட்டில் இடப்புறம் பெண் புழுவின் உள் உறுப்புகளும் ஒப்பீட்டிற்காக வலப்புறம் ஆண் புழுவும் உள்ளன. வலப்புற ஆண் படத்தைப் பெண் உடற்கூறாகக் கருத வேண்டாம். மதிப்பாய்வு நிலுவை."),
        "earthworm/excretory" to BilingualText(
            "Reytan/Xvazquez, Oligochaeta anatomy 2.svg, public domain; KDS4444, Metanephridium.svg, CC BY-SA 4.0. Combined and captioned; original anatomy not redrawn. General segment and Lumbricus-type metanephridium are shown; Pheretima's integumentary, septal and pharyngeal nephridial drainage differences appear only in explanatory text. Expert review pending.",
            "Reytan/Xvazquez வரைந்த Oligochaeta anatomy 2.svg பொதுக்களம்; KDS4444 எழுதிய Metanephridium.svg CC BY-SA 4.0. இரு மூலப்படங்களும் ஒன்றாக வைக்கப்பட்டன; உடற்கூறு மாற்றப்படவில்லை. பொதுவான மண்புழு கண்டமும் Lumbricus நெஃப்ரிடியமும் காட்டப்படுகின்றன; Pheretima-வின் தோல், இடைத்திரை, தொண்டை நெஃப்ரிடியா கழிவுநீக்க வழிகள் கீழே விளக்கமாக மட்டும் கொடுக்கப்பட்டுள்ளன. மதிப்பாய்வு நிலுவை."),
        "pila/external" to BilingualText(
            "Siyaulhaque (2025), Pila globosa specimen photograph, CC BY-SA 4.0; The New International Encyclopaedia (1905), figure 9, historical apple snail/Ampullaria, public domain. Comparative engraving is NOT species-confirmed Pila globosa. Source photo shows the shell, not exposed eye bases: in live Pila eyes sit near the OUTER BASES of cephalic tentacles, NOT their tips. This is a LIMITED external-morphology teaching reference; full organ identification still needs a reviewed diagram. Independent scientific, Tamil and rights review pending.",
            "Siyaulhaque (2025) எடுத்த Pila globosa மாதிரி ஒளிப்படம், CC BY-SA 4.0; The New International Encyclopaedia (1905) பட எண் 9, Ampullaria வகை ஆப்பிள் நத்தை, பொதுக்களம். ஒப்பீட்டு வரைபடம் குறிப்பிட்ட Pila globosa சிற்றினம் என்று உறுதிசெய்யப்படவில்லை. மாதிரிப் படத்தில் கண்களின் அடிப்பகுதி வெளிப்படவில்லை: உயிருள்ள பைலாவில் கண்கள் தலை உணர்நீட்சிகளின் வெளிப்புற அடிமேடுகளில் அமைந்திருக்கும், நுனிகளில் அல்ல. இது வரம்புடைய புறத்தோற்றப் படம்; முழு உறுப்புக் குறியீடுகளுக்கு மேலதிக மதிப்பாய்வு தேவை."),
    )
    init {
        check(byTypeStudy.keys == credit.keys)
        check(byTypeStudy.size == 10)
        check(byTypeStudy.values.all { it.startsWith("qa_source_plates/") })
    }
}
