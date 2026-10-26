#!/usr/bin/env python3
"""One-off placement of library figures into chapter sources (idempotent).

Each entry names a chapter file, the section id, optionally a bsub title, and the figure.
The figure is inserted before the first definition/table/note/points/check block of that
section (or sub-section), so it follows the opening paragraphs.
"""
import re
import sys
from pathlib import Path

D = Path(__file__).resolve().parents[1] / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data"
IND = re.compile(r"^(\s*)")
BLOCK_START = re.compile(r"^\s*b(def|table|note|points|qc)\(")

# (file, section id, bsub title or None, slot id, English caption, Tamil caption)
PLACEMENTS = [
    ("NereisBookChapter.kt", "external", None, "nereis-external",
     "External features of Nereis, dorsal view.",
     "நெரிஸின் வெளிப்புற அம்சங்கள், முதுகுப் புறக் காட்சி."),
    ("NereisBookChapter.kt", "external", None, "nereis-head",
     "The enlarged head region of Nereis.",
     "நெரிஸின் தலைப் பகுதி, பெரிதாக்கிய காட்சி."),
    ("NereisBookChapter.kt", "parapodium", None, "nereis-parapodium",
     "A biramous parapodium of Nereis.",
     "நெரிஸின் இருகிளைப் பாரபோடியம்."),
    ("NereisBookChapter.kt", "internal", None, "nereis-digestive",
     "The digestive system of Nereis.",
     "நெரிஸின் செரிமான மண்டலம்."),
    ("NereisBookChapter.kt", "internal", None, "nereis-circulatory",
     "The circulatory system of Nereis.",
     "நெரிஸின் இரத்த ஓட்ட மண்டலம்."),
    ("NereisBookChapter.kt", "internal", None, "nereis-nervous",
     "The nervous system of Nereis.",
     "நெரிஸின் நரம்பு மண்டலம்."),
    ("NereisBookChapter.kt", "internal", None, "nereis-excretory",
     "The excretory system of Nereis.",
     "நெரிஸின் கழிவு நீக்க மண்டலம்."),
    ("NereisBookChapter.kt", "reproduction", None, "nereis-trochophore",
     "The trochophore larva of Nereis.",
     "நெரிஸின் ட்ரோகோஃபோர் இளவுயிரி."),
    ("ProtoParasitesBookChapter.kt", "entamoeba", None, "entamoeba-cycle",
     "Life cycle of Entamoeba histolytica, from a public-domain reference diagram (English labels). "
     "The stages are explained in the text; the diagram is for orientation only.",
     "எண்டமீபா ஹிஸ்டோலிடிகாவின் வாழ்க்கைச் சுழற்சி; பொதுப் பயன்பாட்டு வரைபடத்திலிருந்து (ஆங்கிலக் குறிப்புகளுடன்). "
     "நிலைகள் உரையில் விளக்கப்பட்டுள்ளன; இப்படம் பொது அறிமுகத்துக்கு மட்டுமே."),
    ("ProtoParasitesBookChapter.kt", "leishmania", None, "leishmania-amastigote",
     "A Leishmania amastigote inside a host cell, from a public-domain reference diagram.",
     "புரவலர் செல்லுக்குள் இருக்கும் லீஷ்மேனியா அமாஸ்டிகோட்; பொதுப் பயன்பாட்டு வரைபடத்திலிருந்து."),
    ("ProtoParasitesBookChapter.kt", "trypanosoma", None, "trypanosoma-cartoon",
     "A cartoon of Trypanosoma brucei: the cell body with its rounded nucleus and the flagellum "
     "running along the cell as a dark line.",
     "Trypanosoma brucei-யின் கேலிச்சித்திரப் படம்: செல் உடல், உருண்ட உட்கரு, செல்லின் நீளத்தில் கருங்கோடாக ஓடும் கசையிழை."),
    ("ProtozoaEconomicsBookChapter.kt", "malaria", None, "plasmodium-cycle",
     "Life cycle of Plasmodium, after the author's diagram (based on P. falciparum). The stages are "
     "described in the text; the numbers in the diagram are the author's and are not keyed here.",
     "பிளாஸ்மோடியத்தின் வாழ்க்கைச் சுழற்சி (P. falciparum அடிப்படையிலான ஆசிரியரின் வரைபடம்). நிலைகள் உரையில் "
     "விளக்கப்பட்டுள்ளன; படத்திலுள்ள எண்கள் ஆசிரியருடையவை, இங்கே விளக்கப்படவில்லை."),
    ("PoriferaCanalsBookChapter.kt", "comparison", None, "sponge-grades",
     "The three grades of canal system, in longitudinal section (schematic; blue arrows show the water current, "
     "green lines the layer of choanocytes): (A) asconoid, (B) syconoid, (C) leuconoid. "
     "(1) osculum, (2) spongocoel, (3) ostium, (4) choanocyte layer lining the spongocoel, (5) radial canal, "
     "(6) incurrent canal, (7) flagellated chamber, (8) excurrent canal.",
     "கால்வாய்த் தொகுதியின் மூன்று நிலைகள், நீளவெட்டு (படத்திட்டம்; நீல அம்புகள் நீரோட்டத்தையும் பச்சைக் கோடுகள் காலர் செல் அடுக்கையும் காட்டுகின்றன): "
     "(A) அஸ்கோனாய்டு, (B) சைக்கனாய்டு, (C) லியூக்கோனாய்டு. "
     "(1) ஆஸ்குலம், (2) ஸ்பாஞ்சோசீல், (3) ஆஸ்டியம், (4) ஸ்பாஞ்சோசீலை வரிசையிடும் காலர் செல் அடுக்கு, (5) ஆரக் கால்வாய், "
     "(6) உள்வரு கால்வாய், (7) கசையிழை அறை, (8) வெளிச்செல் கால்வாய்."),
    ("CnidariaBookChapter.kt", "forms", None, "cnidarian-forms",
     "The two body forms of the Cnidaria as one plan (schematic): (A) polyp, (B) medusa. "
     "A: (1) mouth on the hypostome, (2) body wall, (3) gastrovascular cavity, (4) tentacle, (5) pedal disc. "
     "B: (6) exumbrella, the outer surface of the bell, (2) body wall of the bell, (7) manubrium, (1) mouth.",
     "நிடேரியாவின் இரு உடல் வடிவங்கள் ஒரே அமைப்பாக (படத்திட்டம்): (A) பாலிப், (B) மெடூசா. "
     "A: (1) ஹைப்போஸ்டோமில் உள்ள வாய், (2) உடற்சுவர், (3) இரைப்பை-நாளக் குழி, (4) உணர்நீட்சி, (5) அடிப்பாதத் தட்டு. "
     "B: (6) மணியின் வெளிப்புறப் பரப்பு (எக்சும்ப்ரெல்லா), (2) மணியின் உடற்சுவர், (7) மானுப்ரியம், (1) வாய்."),
    ("MetamerismBookChapter.kt", "repeated-parts", None, "metameres",
     "Five segments of a segmented worm in longitudinal section, dorsal side up (schematic): (1) body wall, "
     "(2) coelomic compartment, (3) septum, (4) dorsal blood vessel, (5) ventral nerve cord, (6) ganglion, "
     "(7) gut, which runs through every segment, (8) chaeta, (9) prostomium, (10) pygidium, (11) nephridium.",
     "கண்டமுடைய புழுவின் ஐந்து கண்டங்கள், முதுகுப்புறம் மேலே இருக்க நீளவெட்டு (படத்திட்டம்): (1) உடற்சுவர், "
     "(2) உடற்குழிப் பெட்டகம், (3) தடுப்புச் சுவர் (செப்டம்), (4) முதுகுப்புற இரத்த நாளம், (5) வயிற்றுப்புற நரம்புத் தண்டு, (6) நரம்புத் திரள் (கேங்கிலியம்), "
     "(7) எல்லாக் கண்டங்கள் வழியாகவும் செல்லும் குடல், (8) சீட்டா, (9) புரோஸ்டோமியம், (10) பைஜிடியம், (11) நெஃப்ரிடியம்."),
    ("HostParasiteBookChapter.kt", "terms", None, "host-cycles",
     "Two kinds of parasite life cycle (schematic). (A) One host: (1) host, (2) parasite stages inside it, "
     "(3) resistant infective stage outside the host, (4) new host of the same species. "
     "(B) Two hosts: (5) definitive host, in which the parasite reaches sexual maturity, "
     "(6) intermediate host, in which it passes its larval or asexual stages, (7) and (8) transfer between them.",
     "ஒட்டுண்ணி வாழ்க்கைச் சுழற்சிகளின் இரு வகைகள் (படத்திட்டம்). (A) ஒரே புரவலர்: (1) புரவலர், (2) அதனுள் இருக்கும் ஒட்டுண்ணி நிலைகள், "
     "(3) புரவலருக்கு வெளியே தாங்கும் தொற்று நிலை, (4) அதே இனத்தின் புதிய புரவலர். "
     "(B) இரு புரவலர்கள்: (5) இறுதிப் புரவலர் (பாலின முதிர்ச்சி அடையும் இடம்), "
     "(6) இடைநிலைப் புரவலர் (இளநிலை அல்லது பாலிலா நிலைகள் கழியும் இடம்), (7), (8) அவற்றிடையே நிகழும் மாற்றம்."),
    ("PearlBookChapter.kt", "natural", None, "pearl-formation",
     "Shell and pearl (schematic). (A) Section through the shell and mantle: (1) periostracum, the thin outer organic layer, "
     "(2) middle calcareous layer, (3) nacreous layer, (4) mantle epithelium, which secretes the shell, (5) mantle tissue. "
     "(B) A pearl in section: (6) nucleus, (7) layers of nacre, (8) pearl sac of mantle epithelium, (9) host tissue.",
     "ஓடும் முத்தும் (படத்திட்டம்). (A) ஓடு, மேண்டில் வழி வெட்டு: (1) பெரியோஸ்ட்ரகம், மெல்லிய கரிம வெளி அடுக்கு, "
     "(2) நடு சுண்ணாம்பு அடுக்கு, (3) நாக்கர் அடுக்கு, (4) ஓட்டைச் சுரக்கும் மேண்டில் மேலணி, (5) மேண்டில் திசு. "
     "(B) முத்தின் வெட்டுத்தோற்றம்: (6) மையக்கரு, (7) நாக்கர் அடுக்குகள், (8) மேண்டில் மேலணியாலான முத்துப் பை, (9) புரவலர் திசு."),
    ("AschelminthesBookChapter.kt", "pseudocoelom", None, "body-cavities",
     "Three body plans in cross-section (schematic): (A) acoelomate, as in a flatworm, with no cavity; "
     "(B) pseudocoelomate, as in a nematode, with a cavity between the body-wall mesoderm and the gut; "
     "(C) coelomate, with a cavity lined by mesodermal peritoneum on both sides. "
     "(1) epidermis, (2) mesoderm, (3) wall of the gut, (4) lumen of the gut, (5) body cavity, (6) peritoneum.",
     "குறுக்கு வெட்டில் மூன்று உடல் அமைப்புகள் (படத்திட்டம்): (A) தட்டைப்புழுவைப் போன்ற உடற்குழியற்றது; "
     "(B) நெமட்டோடாவைப் போன்ற போலி உடற்குழியுடையது; உடற்சுவர் மீசோடெர்முக்கும் குடலுக்கும் இடையே குழி உள்ளது; "
     "(C) இருபுறமும் மீசோடெர்ம் பெரிட்டோனியத்தால் வரிசையிடப்பட்ட குழியுடைய உண்மை உடற்குழியுடையது. "
     "(1) மேலணி, (2) மீசோடெர்ம், (3) குடலின் சுவர், (4) குடல் உட்குழி, (5) உடற்குழி, (6) பெரிட்டோனியம்."),
    ("AschelminthesBookChapter.kt", "bodywall", None, "nematode-bodywall",
     "Cross-section of a nematode (schematic): (1) cuticle, (2) epidermis, thickened along four lines, "
     "(3) longitudinal muscle cell, there being no circular muscle, (4) pseudocoelom, (5) intestine.",
     "நெமட்டோடாவின் குறுக்கு வெட்டு (படத்திட்டம்): (1) கியூட்டிக்கிள், (2) நான்கு கோடுகளில் தடித்த மேலணி, "
     "(3) நீள்வாட்டுத் தசைச் செல் (வட்டத் தசை இல்லை), (4) போலி உடற்குழி, (5) குடல்."),
    ("NematodeAdaptationsBookChapter.kt", "transmission", None, "nematode-routes",
     "Three routes of transmission of human nematodes (schematic): (A) Ascaris, (B) hookworm, (C) Wuchereria. "
     "Columns: (1) the infected person, (2) the stage that leaves the host (egg, egg, or microfilariae in blood taken up by a mosquito), "
     "(3) development in the soil, as an embryonated egg or a free larva, or in the mosquito, "
     "(4) the new host: the egg is swallowed (A), the larva bores through the skin (B), the mosquito bites (C).",
     "மனித நெமட்டோடாக்களின் மூன்று பரவல் வழிகள் (படத்திட்டம்): (A) அஸ்காரிஸ், (B) கொக்கிப்புழு, (C) வுச்செரேரியா. "
     "நெடுவரிசைகள்: (1) தொற்றுற்றவர், (2) புரவலரை விட்டு வெளியேறும் நிலை (முட்டை, முட்டை, அல்லது கொசு உறிஞ்சும் இரத்த மைக்ரோஃபைலேரியா), "
     "(3) மண்ணில் கரு வளர்ந்த முட்டையாக அல்லது தனிவாழ் லார்வாவாக, அல்லது கொசுவில் வளர்ச்சி, "
     "(4) புதிய புரவலர்: முட்டை விழுங்கப்படுகிறது (A), லார்வா தோலைத் துளைக்கிறது (B), கொசு கடிக்கிறது (C)."),
    ("MolluscaBookChapter.kt", "mantle", None, "mollusc-plan",
     "A generalised mollusc in side view, head to the left (schematic; not a particular species): (1) shell, (2) mantle, "
     "(3) mantle cavity, (4) ctenidium, (5) visceral mass, (6) foot, (7) head with eye and tentacles, (8) mouth.",
     "பொதுவான மெல்லுடலியின் பக்கக் காட்சி, தலை இடப்புறம் (படத்திட்டம்; குறிப்பிட்ட இனம் அல்ல): (1) ஓடு, (2) மேண்டில், "
     "(3) மேண்டில் குழி, (4) டீனிடியம், (5) உள்ளுறுப்புத் திரள், (6) பாதம், (7) கண், உணர்கொம்புகளுடன் தலை, (8) வாய்."),
    ("EchinodermataBookChapter.kt", "symmetry", None, "echino-symmetry",
     "Pentaradial adult and bilateral larva (schematic): (A) a sea star from the aboral side, (B) the same from the oral side, "
     "(C) a bilateral larva. (1) central disc, (2) arm, (3) madreporite, (4) mouth, (5) tube feet along an ambulacral groove, "
     "(6) ciliated band, (7) the single plane of symmetry, shown dashed.",
     "ஐந்தாரச் சமச்சீருடைய முதிர்ந்த உயிரியும் இருபக்கச் சமச்சீருடைய லார்வாவும் (படத்திட்டம்): (A) கடல் நட்சத்திரம், வாய் எதிர்ப் பக்கக் காட்சி, "
     "(B) அதே, வாய்ப் பக்கக் காட்சி, (C) இருபக்கச் சமச்சீர் லார்வா. (1) மைய வட்டு, (2) கை, (3) மேட்ரிபோரைட், (4) வாய், "
     "(5) அம்புலாக்ரல் பள்ளத்தில் உள்ள குழாய்க் கால்கள், (6) குறுஇழைப் பட்டை, (7) ஒரே சமச்சீர்த் தளம் (புள்ளிக்கோடு)."),
    ("LocomotionBookChapter.kt", "cilia", None, "cilium-stroke",
     "Ciliary beat (schematic; time runs from pale to dark). (A) Effective stroke: the cilium is stiff and sweeps through water. "
     "(B) Recovery stroke: it bends and rolls back close to the surface. (C) Neighbouring cilia beat slightly out of step, "
     "which produces a metachronal wave. (1) base of the cilium at the cell surface, (2) cilium, "
     "(3) direction of the effective stroke, (4) direction of the recovery stroke, (5) direction of the wave.",
     "குறுஇழை அசைவு (படத்திட்டம்; வெளிர் நிறத்திலிருந்து கருமைக்கு நேரம் செல்கிறது). (A) செயல்திறன் அடி: குறுஇழை விறைத்து நீரைத் துடுப்பிடுகிறது. "
     "(B) மீட்பு அடி: வளைந்து மேற்பரப்புக்கு அருகில் திரும்புகிறது. (C) அடுத்தடுத்த குறுஇழைகள் சற்றே முன்பின்னாக அசைவதால் "
     "மெட்டாக்ரோனல் அலை உருவாகிறது. (1) செல் மேற்பரப்பில் குறுஇழையின் அடி, (2) குறுஇழை, "
     "(3) செயல்திறன் அடியின் திசை, (4) மீட்பு அடியின் திசை, (5) அலையின் திசை."),
    ("CoralsBookChapter.kt", "polyp", None, "coral-polyp",
     "A stony-coral polyp in its skeletal cup, in section (schematic): (1) tentacle, (2) mouth on a short pharynx, "
     "(3) body wall, (4) gastrovascular cavity, (5) skeletal cup (calyx), (6) septum, a skeletal ridge, "
     "(7) zooxanthellae in the tissue.",
     "கல் பவளத்தின் பாலிப், எலும்புக்கூட்டுக் கிண்ணத்துக்குள், வெட்டுத்தோற்றம் (படத்திட்டம்): (1) உணர்நீட்சி, (2) குறுகிய தொண்டையின் மேல் வாய், "
     "(3) உடற்சுவர், (4) இரைப்பை-நாளக் குழி, (5) எலும்புக்கூட்டுக் கிண்ணம் (கேலிக்ஸ்), (6) செப்டம், எலும்புக்கூட்டு முகடு, "
     "(7) திசுவிலுள்ள சூக்சாந்தெல்லா."),
    ("AnnelidaBookChapter.kt", "body-wall", None, "annelid-section",
     "Transverse section through the trunk of an annelid, dorsal side up (schematic): (1) cuticle, (2) epidermis, "
     "(3) circular muscle, (4) longitudinal muscle, (5) coelom, (6) gut, (7) dorsal blood vessel, (8) ventral blood vessel, "
     "(9) ventral nerve cord, (10) nephridium, cut across its tubule.",
     "அனெலிடாவின் உடற்பகுதி குறுக்கு வெட்டு, முதுகுப்புறம் மேலே (படத்திட்டம்): (1) கியூட்டிக்கிள், (2) மேலணி, "
     "(3) வட்டத் தசை, (4) நீள்வாட்டுத் தசை, (5) உடற்குழி, (6) குடல், (7) முதுகுப்புற இரத்த நாளம், (8) வயிற்றுப்புற இரத்த நாளம், "
     "(9) வயிற்றுப்புற நரம்புத் தண்டு, (10) நெஃப்ரிடியம், குழல் குறுக்காக வெட்டப்பட்டது."),
    ("PlatyhelminthesBookChapter.kt", "flame-cells", None, "flame-cell",
     "A flame cell and its duct in the parenchyma of a flatworm (schematic): (1) flame cell, a hollow cell, "
     "(2) tuft of beating cilia in its cavity, (3) nucleus, (4) excretory capillary, (5) collecting duct, "
     "(6) excretory pore, (7) parenchyma.",
     "தட்டைப்புழுவின் பாரங்கைமாவிலுள்ள சுடர்ச் செல்லும் அதன் நாளமும் (படத்திட்டம்): (1) சுடர்ச் செல், உள்ளீடற்ற செல், "
     "(2) அதன் குழிக்குள் அசையும் குறுஇழைக் கற்றை, (3) உட்கரு, (4) கழிவு நுண்குழல், (5) சேகரிப்பு நாளம், "
     "(6) கழிவுத் துளை, (7) பாரங்கைமா."),
    ("MyriapodaBookChapter.kt", "compare", None, "myriapod-sections",
     "Transverse sections of the two groups (schematic): (A) centipede, flattened from above downwards, with one pair of legs "
     "on each segment; (B) millipede, cylindrical, in a section through a diplosegment with two pairs of legs. "
     "(1) dorsal plate, (2) gut, (3) leg, (4) ventral nerve cord.",
     "இரு குழுக்களின் குறுக்கு வெட்டுகள் (படத்திட்டம்): (A) நூறுகாலி, மேலிருந்து கீழாகத் தட்டையானது, ஒவ்வொரு கண்டத்திலும் ஓர் இணைக் கால்; "
     "(B) ஆயிரங்காலி, உருளை வடிவம், இரட்டைக் கண்டத்தின் வழி வெட்டில் இரண்டு இணைக் கால்கள். "
     "(1) முதுகுத் தகடு, (2) குடல், (3) கால், (4) வயிற்றுப்புற நரம்புத் தண்டு."),
    ("IntroInvertebrataBookChapter.kt", "plans", None, "symmetry-types",
     "Three kinds of body symmetry (schematic): (A) asymmetrical, with no plane that divides the body into equal halves; "
     "(B) radial, in which any plane through the central axis does so; (C) bilateral, in which only one plane does, "
     "separating left from right. (1) central axis, (2) plane of symmetry, dashed, (3) anterior end, (4) posterior end.",
     "மூன்று வகை உடல் சமச்சீர் (படத்திட்டம்): (A) சமச்சீரற்றது; உடலைச் சம பாதிகளாகப் பிரிக்கும் தளம் இல்லை; "
     "(B) ஆரச் சமச்சீர்; மையஅச்சின் வழியாகச் செல்லும் எந்தத் தளமும் பிரிக்கும்; (C) இருபக்கச் சமச்சீர்; "
     "இடப்புறத்தையும் வலப்புறத்தையும் பிரிக்கும் ஒரே ஒரு தளம் மட்டும் உள்ளது. "
     "(1) மைய அச்சு, (2) சமச்சீர்த் தளம் (புள்ளிக்கோடு), (3) முன் முனை, (4) பின் முனை."),
    ("ProtozoaBookChapter.kt", "classes", None, "protozoan-groups",
     "Organelles of locomotion in four traditional groups (schematic): (A) a cell moving by pseudopodia, "
     "(B) a flagellate with one flagellum, (C) a ciliate covered with cilia, (D) a sporozoan cell, drawn without the "
     "organelles of the other three. (1) pseudopodium, (2) nucleus, (3) flagellum, (4) cilia.",
     "நான்கு மரபுவழிக் குழுக்களின் இயக்க நுண்ணுறுப்புகள் (படத்திட்டம்): (A) போலிக்கால்களால் நகரும் செல், "
     "(B) ஒரு கசையிழையுடைய கசையிழையுயிரி, (C) குறுஇழைகள் மூடிய சிலியேட், (D) மற்ற மூன்றின் நுண்ணுறுப்புகள் இல்லாமல் வரையப்பட்ட ஸ்போரோசோவா செல். "
     "(1) போலிக்கால், (2) உட்கரு, (3) கசையிழை, (4) குறுஇழைகள்."),
    ("InsectaGeneralBookChapter.kt", "body", "The head", "insect-tagmata",
     "A generalised insect in side view: (1) head, (2) thorax, (3) abdomen, (4) antenna, "
     "(5) compound eye, (6) forewing, (7) hindwing, (8) leg, (9) spiracle.",
     "பொதுவான பூச்சியின் பக்கக் காட்சி: (1) தலை, (2) மார்பு, (3) வயிறு, (4) உணர்கொம்பு, "
     "(5) கூட்டுக்கண், (6) முன் இறக்கை, (7) பின் இறக்கை, (8) கால், (9) சுவாசத்துளை."),
    ("InsectaGeneralBookChapter.kt", "development", None, "metamorphosis",
     "(A) Incomplete metamorphosis: (1) egg, (2) young nymph, (3) older nymph with wing pads, (4) adult. "
     "(B) Complete metamorphosis: (1) egg, (2) larva, (3) pupa, (4) adult.",
     "(A) முழுமையற்ற உருமாற்றம்: (1) முட்டை, (2) இளம் நிம்ஃப், (3) இறக்கை அரும்புகளுடன் முதிர்ந்த நிம்ஃப், (4) முதிர்ந்த பூச்சி. "
     "(B) முழு உருமாற்றம்: (1) முட்டை, (2) புழு (லார்வா), (3) கூட்டுப்புழு (பியூபா), (4) முதிர்ந்த பூச்சி."),
    ("PestsIpmBookChapter.kt", "ipm", None, "parasitoid-cycle",
     "Life cycle of a parasitoid wasp on a caterpillar host: (1) the female lays an egg in or on the host, "
     "(2) the parasitoid larva feeds inside the living host, (3) the dying host with parasitoid pupae, "
     "(4) the adult parasitoid emerges.",
     "புழுவைப் புரவலராகக் கொண்ட ஒட்டுண்ணி குளவியின் வாழ்க்கைச் சுழற்சி: (1) பெண் குளவி புரவலரின் மேலோ உள்ளோ முட்டையிடுகிறது, "
     "(2) ஒட்டுண்ணி இளவுயிரி உயிருள்ள புரவலரின் உள்ளே உண்கிறது, (3) இறக்கும் புரவலரும் ஒட்டுண்ணிக் கூட்டுப்புழுக்களும், "
     "(4) முதிர்ந்த ஒட்டுண்ணி வெளிவருகிறது."),
    ("MedicalInsectsBookChapter.kt", "ways", None, "vector-cycle",
     "How a biting insect carries a parasite between people (schematic): (1) infected person, "
     "(2) the insect takes a blood meal and picks up the parasite, (3) the parasite develops in the insect, "
     "(4) the infective insect bites a new person, (5) disease may develop in the new host.",
     "கடிக்கும் பூச்சி ஒட்டுண்ணியை மனிதர்களிடையே கடத்தும் விதம் (படத்திட்டம்): (1) நோயுற்றவர், "
     "(2) பூச்சி இரத்தம் உறிஞ்சி ஒட்டுண்ணியைப் பெறுகிறது, (3) ஒட்டுண்ணி பூச்சியினுள் வளர்கிறது, "
     "(4) தொற்றும் நிலையிலுள்ள பூச்சி புதியவரைக் கடிக்கிறது, (5) புதிய புரவலருக்கு நோய் உருவாகலாம்."),
    ("ProtozoaEconomicsBookChapter.kt", "malaria", None, "vector-cycle",
     "Transmission of a vector-borne protozoan such as the malaria parasite (schematic): (1) infected person, "
     "(2) the mosquito takes a blood meal and picks up the parasite, (3) the parasite develops in the mosquito, "
     "(4) the infective mosquito bites a new person, (5) disease may develop in the new host.",
     "மலேரியா ஒட்டுண்ணி போன்ற பூச்சி மூலம் பரவும் புரோட்டோசோவாவின் பரவல் (படத்திட்டம்): (1) நோயுற்றவர், "
     "(2) கொசு இரத்தம் உறிஞ்சி ஒட்டுண்ணியைப் பெறுகிறது, (3) ஒட்டுண்ணி கொசுவினுள் வளர்கிறது, "
     "(4) தொற்றும் நிலையிலுள்ள கொசு புதியவரைக் கடிக்கிறது, (5) புதிய புரவலருக்கு நோய் உருவாகலாம்."),
    ("LeechBookChapter.kt", "external", None, "leech",
     "External features of a leech, schematic: (1) anterior sucker with the mouth, (2) eyes, "
     "(3) dorsal surface, (4) clitellum, (5) annuli, (6) posterior sucker.",
     "அட்டையின் வெளிப்புற அம்சங்கள் (படத்திட்டம்): (1) வாயுடன் கூடிய முன் உறிஞ்சி, (2) கண்கள், "
     "(3) முதுகுப் பரப்பு, (4) கிளைட்டெல்லம், (5) வளையங்கள், (6) பின் உறிஞ்சி."),
    ("AnnelidExcretionReproductionBookChapter.kt", "asexual", "Fission and fragmentation", "polychaete-asexual",
     "Asexual reproduction in polychaetes (schematic; green = head region, pink = tail region): "
     "(A) a worm breaks into pieces and each regenerates the missing ends; "
     "(B) a new head forms before the worm separates; "
     "(C) a gamete-bearing stolon with its own head forms behind the parent and separates.",
     "பாலிகீட்டாக்களின் பாலிலா இனப்பெருக்கம் (படத்திட்டம்; பச்சை = தலைப் பகுதி, இளஞ்சிவப்பு = வால் பகுதி): "
     "(A) புழு துண்டுகளாக உடைந்து ஒவ்வொன்றும் இல்லாத முனைகளை மீளுருவாக்குகிறது; "
     "(B) புழு பிரியும் முன்பே புதிய தலை உருவாகிறது; "
     "(C) தாய்ப்புழுவின் பின்னால் சொந்தத் தலையுடன் கூடிய, இனச்செல்கள் தாங்கிய ஸ்டோலான் உருவாகிப் பிரிகிறது."),
    ("AureliaMesenteriesBookChapter.kt", "life", None, "aurelia-cycle",
     "Life cycle of Aurelia (schematic): (1) adult medusa, (2) planula larva, (3) scyphistoma attached to a surface, "
     "(4) strobila, (5) ephyra, (6) the ephyra grows into the adult medusa.",
     "ஆரீலியாவின் வாழ்க்கைச் சுழற்சி (படத்திட்டம்): (1) முதிர்ந்த மெடூசா, (2) பிளானுலா இளவுயிரி, "
     "(3) பரப்பில் ஒட்டிய ஸ்கைஃபிஸ்டோமா, (4) ஸ்ட்ரோபிலா, (5) எஃபைரா, (6) எஃபைரா முதிர்ந்த மெடூசாவாக வளர்கிறது."),
    ("AureliaMesenteriesBookChapter.kt", "mesenteries", "Complete and incomplete mesenteries", "anemone-section",
     "Transverse section of a sea anemone through the pharynx (schematic): (1) body wall, (2) pharynx, "
     "(3) siphonoglyph, (4) complete mesentery reaching the pharynx, (5) incomplete mesentery that stops short of it, "
     "(6) free edge of a mesentery, (7) directive mesentery.",
     "கடல் அனிமோனின் தொண்டை வழியான குறுக்கு வெட்டு (படத்திட்டம்): (1) உடற்சுவர், (2) தொண்டை, "
     "(3) சைஃபனோகிளிஃப், (4) தொண்டையை எட்டும் முழுமையான மெசென்டரி, (5) தொண்டையை எட்டாத முழுமையற்ற மெசென்டரி, "
     "(6) மெசென்டரியின் தனி விளிம்பு, (7) வழிகாட்டி மெசென்டரி."),
    ("EchinodermClassesBookChapter.kt", "crinoids", "Structure", "crinoid",
     "A stalked crinoid (schematic): (1) calyx, (2) arm, (3) pinnule, (4) oral surface with the mouth, "
     "(5) stalk made of columnals, (6) cirrus, (7) holdfast.",
     "காம்புடைய கிரைனாய்டு (படத்திட்டம்): (1) கேலிக்ஸ், (2) கை, (3) பின்னியூல், (4) வாய் உள்ள வாய்ப்புறப் பரப்பு, "
     "(5) நெடுவரிசைத் துண்டுகளால் ஆன காம்பு, (6) சிர்ரஸ், (7) பற்றுக்கால்."),
    ("TapewormHostParasiteBookChapter.kt", "structure", "Body regions", "taenia-parts",
     "Body plan of Taenia solium (schematic): (1) scolex, (2) sucker, (3) rostellum with hooks, (4) neck, "
     "(5) immature proglottids, (6) mature proglottids, (7) gravid proglottids; "
     "enlarged: (8) branched uterus of a gravid proglottid, (9) egg.",
     "Taenia solium-ன் உடலமைப்பு (படத்திட்டம்): (1) ஸ்கொலெக்ஸ், (2) உறிஞ்சி, (3) கொக்கிகளுடன் கூடிய ரோஸ்டெல்லம், (4) கழுத்து, "
     "(5) முதிராத புரோகிளாட்டிட்டுகள், (6) முதிர்ந்த புரோகிளாட்டிட்டுகள், (7) முட்டை நிறைந்த புரோகிளாட்டிட்டுகள்; "
     "பெரிதாக்கியவை: (8) முட்டை நிறைந்த புரோகிளாட்டிட்டின் கிளைத்த கருப்பை, (9) முட்டை."),
    ("TapewormHostParasiteBookChapter.kt", "lifecycle", None, "taenia-cycle",
     "Life cycle of Taenia solium (schematic): (1) person with the adult worm, (2) eggs passed with the faeces, "
     "(3) the pig swallows the eggs, (4) oncospheres reach the muscles, (5) cysticerci develop in the pork, "
     "(6) a person eats undercooked pork, (7) the adult worm develops in the intestine, "
     "(8) dashed: a person who swallows the eggs may develop cysticercosis.",
     "Taenia solium-ன் வாழ்க்கைச் சுழற்சி (படத்திட்டம்): (1) முதிர்ந்த புழுவுடன் உள்ள மனிதர், (2) மலத்துடன் வெளியேறும் முட்டைகள், "
     "(3) பன்றி முட்டைகளை விழுங்குகிறது, (4) ஆன்கோஸ்ஃபியர்கள் தசைகளை அடைகின்றன, (5) பன்றி இறைச்சியில் சிஸ்டிசெர்கஸ் உருவாகிறது, "
     "(6) நன்கு வேகாத பன்றி இறைச்சியை மனிதர் உண்கிறார், (7) குடலில் முதிர்ந்த புழு வளர்கிறது, "
     "(8) புள்ளிக்கோடு: முட்டைகளை விழுங்கும் மனிதருக்குச் சிஸ்டிசெர்கோசிஸ் உண்டாகலாம்."),
]


def esc(s: str) -> str:
    return s.replace("\\", "\\\\").replace('"', '\\"')


def place(entry) -> str:
    fname, sec, sub, slot, en, ta = entry
    path = D / fname
    lines = path.read_text(encoding="utf-8").split("\n")
    tag = f'bfig("lib/{slot}"'
    if any(tag in l for l in lines):
        return f"{fname}: {slot} already placed"
    start = next((i for i, l in enumerate(lines) if f'bsec("{sec}"' in l), None)
    if start is None:
        return f"{fname}: NO SECTION {sec}"
    end = next((i for i in range(start + 1, len(lines)) if re.match(r'^\s*bsec\(', lines[i])), len(lines))
    anchor = start
    if sub:
        anchor = next((i for i in range(start, end) if f'bsub("{sub}"' in lines[i]), None)
        if anchor is None:
            return f"{fname}: NO SUB {sub}"
        sub_end = next((i for i in range(anchor + 1, end) if re.match(r'^\s*bsub\(', lines[i])), end)
        end = sub_end
        # the bsub header must end its line with a comma (blocks follow on later lines)
        assert lines[anchor].rstrip().endswith(","), f"{fname}: bsub header not single-line"
    else:
        anchor = next(i for i in range(start, end) if "blocks = listOf(" in lines[i])
    # insert before the first definition/table/note block after the anchor, else right after it
    where = next((i for i in range(anchor + 1, end) if BLOCK_START.match(lines[i])), anchor + 1)
    indent = IND.match(lines[anchor + 1]).group(1)
    new = f'{indent}bfig("lib/{slot}",\n{indent}    "{esc(en)}",\n{indent}    "{esc(ta)}"),'
    lines[where:where] = new.split("\n")
    path.write_text("\n".join(lines), encoding="utf-8")
    return f"{fname}: placed {slot}"


if __name__ == "__main__":
    for e in PLACEMENTS:
        print(place(e))
