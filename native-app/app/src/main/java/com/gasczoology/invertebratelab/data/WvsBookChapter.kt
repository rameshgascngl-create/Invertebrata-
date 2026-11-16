package com.gasczoology.invertebratelab.data

/** Book-style chapter: the echinoderm water-vascular system, pathway and function. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object WvsBookChapter {
    val chapter = bchapter(
        lessonId = "u5-wvs",
        title = "The Water-Vascular System" to "நீர்க்குழல் மண்டலம்",
        objectives = listOf(
            "Identify the principal canals and associated structures." to
                "முதன்மைக் கால்வாய்களையும் அவற்றுடன் தொடர்புடைய உறுப்பமைப்புகளையும் அடையாளம் காணுதல்.",
            "Explain tube-foot action through fluid pressure and muscles." to
                "திரவ அழுத்தமும் தசைச் செயல்பாடும் இணைந்து குழாய்க்கால்களை இயக்கும் முறையை விளக்குதல்.",
            "Relate the system to movement, attachment and feeding." to
                "இம்மண்டலத்தின் செயல்பாடுகளை இடப்பெயர்ச்சி, பற்றுதல் மற்றும் உணவுண்ணுதல் ஆகியவற்றுடன் தொடர்புபடுத்துதல்.",
        ),
        introduction = listOf(
            "The water-vascular system consists of fluid-filled canals connected to tube feet. Following the canals and examining tube-foot action helps explain movement, attachment and feeding in echinoderms." to
                "நீர்க்குழல் மண்டலம், திரவம் நிரம்பிய கால்வாய்களையும் அவற்றுடன் தொடர்புடைய குழாய்க்கால்களையும் கொண்டுள்ளது. கால்வாய்களின் தொடரமைப்பையும் குழாய்க்கால்கள் இயங்கும் விதத்தையும் அறிந்துகொள்வதன் மூலம், முட்தோலிகளின் இடப்பெயர்ச்சி, பற்றுதல் மற்றும் உணவுண்ணுதல் ஆகியவற்றைப் புரிந்துகொள்ளலாம்.",
        ),
        sections = listOf(
            bsec("definition", "Definition and origin", "வரையறையும் தோற்றமும்",
                blocks = listOf(
                    bp("The water-vascular system is a coelom-derived hydraulic network of canals, unique to the echinoderms. In the embryo and larva, the left side of the coelom forms a series of outgrowths, among them the hydrocoel, and the hydrocoel becomes the canal system of the adult. The origin from the coelom means that the fluid inside is a coelomic fluid, similar to sea water in its salts, with coelomocytes and a little protein, and not simply sea water that has been drawn in." to
                        "நீர்க்குழல் மண்டலம் முட்தோலிகளுக்கே உரிய, உடற்குழியிலிருந்து உருவான நீரழுத்தக் கால்வாய்களின் வலைப்பின்னல். கரு மற்றும் லார்வா வளர்ச்சியின்போது இடப்புற உடற்குழியிலிருந்து ஹைட்ரோசீல் உள்ளிட்ட பல வெளிநீட்சிகள் உருவாகின்றன; ஹைட்ரோசீல் முதிர்ந்த உயிரியின் நீர்க்குழாய்க்கால்வாய் மண்டலமாக வளர்ச்சியடைகிறது. உடற்குழியிலிருந்து தோற்றம் என்பதால் உள்ளிருக்கும் திரவம் உடற்குழித் திரவம்; உப்புகளின் செறிவில் கடல்நீரை ஒத்துள்ளது; உடற்குழிச் செல்களையும் சிறிதளவு புரதத்தையும் கொண்டுள்ளது; வெளியிலிருந்து உள்ளிழுக்கப்பட்ட கடல்நீர் மட்டுமே அல்ல."),
                    bp("The system has three kinds of parts. There are the main canals, which are the ring canal and the radial canals; the lateral canals and ampullae, which connect the main canals to the feet; and the tube feet, which are the working end-organs. The madreporite and the stone canal connect the system to the outside, and in some echinoderms there are other accessory parts, such as Tiedemann's bodies and Polian vesicles, whose details vary and are not required in this answer." to
                        "இம்மண்டலத்தில் மூன்று முக்கிய வகைப் பகுதிகள் உள்ளன: முதன்மைக் கால்வாய்களான வளையக் கால்வாயும் ஆரக் கால்வாய்களும்; முதன்மைக் கால்வாய்களை குழாய்க்கால்களுடன் இணைக்கும் பக்கவாட்டுக் கால்வாய்களும் ஆம்புலாக்களும்; இயக்கச் செயல்பாட்டை மேற்கொள்ளும் இறுதி உறுப்புகளான குழாய்க்கால்களும். மாட்ரிபோரைட்டும் கற்கால்வாயும் இம்மண்டலத்தை வெளிப்புறத்துடன் இணைக்கின்றன; சில முட்தோலிகளில் டைட்மன் உடல்கள், போலியன் பைகள் போன்ற பிற துணைப் பகுதிகள் உள்ளன; இவற்றின் அமைப்பு இனங்களுக்கு இடையே மாறுபடும்; இவ்விடையில் அவற்றின் விரிவான விளக்கம் தேவையில்லை."),
                    bdef("Water-vascular system", "நீர்க்குழல் மண்டலம்", "A coelom-derived hydraulic system of canals and tube feet unique to echinoderms." to
                        "முட்தோலிகளுக்கே உரிய, உடற்குழியிலிருந்து தோன்றிய கால்வாய்கள், குழாய்க்கால்களின் நீரழுத்த அமைப்பு."),
                    bdef("Hydrocoel", "ஹைட்ரோசீல் (Hydrocoel)", "The part of the larval coelom that gives rise to the water-vascular system." to
                        "நீர்க்குழல் மண்டலமாக வளர்ச்சியடையும் லார்வாவின் உடற்குழிப் பகுதி."),
                    bdef("Hydraulic", "நீரழுத்த (Hydraulic)", "Working by the pressure of a liquid in a closed space." to
                        "மூடப்பட்ட இடத்தினுள் உள்ள திரவத்தின் அழுத்தத்தைப் பயன்படுத்திச் செயல்படும்."),
                )),
            bsec("pathway", "The pathway of canals", "கால்வாய்ப் பாதை",
                blocks = listOf(
                    bfig("asterias/water-vascular-system", "The water-vascular system of a starfish: madreporite, stone canal, ring canal, radial canals, lateral canals, ampullae and tube feet." to "நட்சத்திரமீனின் நீர்க்குழல் மண்டலம்: மேட்ரிபோரைட், கற்கால்வாய், வளையக் கால்வாய், ஆரக் கால்வாய்கள், பக்கக் கால்வாய்கள், ஆம்புலாக்கள், குழாய்க்கால்கள்."),
                    bpoints("The pathway, in order", "பாதை, வரிசையாக",
                        "The madreporite, a porous plate on the aboral surface, communicates with the system and helps to regulate its fluid." to "வாய்க்கு எதிர்ப்புற மேற்பரப்பிலுள்ள நுண்துளைகளைக் கொண்ட தகடான மேட்ரிபோரைட் நீர்க்குழல் மண்டலத்துடன் தொடர்புகொண்டு அதிலுள்ள திரவத்தை ஒழுங்குபடுத்த உதவுகிறது.",
                        "A stone canal, with calcareous deposits in its wall, connects the madreporite to the ring canal." to "சுவரில் சுண்ணாம்புப் படிவுகளைக் கொண்ட கற்கால்வாய் மேட்ரிபோரைட்டை வளையக் கால்வாயுடன் இணைக்கிறது.",
                        "The ring canal surrounds the mouth." to "வளையக் கால்வாய் வாயைச் சுற்றியுள்ளது.",
                        "Five radial canals arise from the ring canal and run along the ambulacral region of the arms." to "வளையக் கால்வாயிலிருந்து ஐந்து ஆரக் கால்வாய்கள் கிளம்பி கைகளின் ஆம்புலாக்ரல் பகுதியில் செல்கின்றன.",
                        "Paired lateral canals with valves join each radial canal to the individual ampulla and tube-foot units." to "வால்வுகளைக் கொண்ட இணையான பக்கக் கால்வாய்கள் ஒவ்வொரு கதிர்க் கால்வாயையும் தனித்தனி ஆம்புலா, குழாய்க்கால அலகுகளுடன் இணைக்கின்றன.",
                    ),
                    bp("The pathway is an anatomical description of the connections, and its purpose is to show how the parts are joined. It should not be taken as a statement that sea water flows continuously in one direction, in through the madreporite, along the canals and out through the feet. The fluid in the system is largely a closed volume that is moved to and fro by the contraction of muscles, and the madreporite is a route for the exchange of a little fluid and for the regulation of the pressure. The student who draws arrows suggesting a through-flow loses marks." to
                        "இந்தப் பாதை உறுப்புகளுக்கிடையேயான இணைப்புகளின் உடற்கூறியல் விளக்கமாகும்; பகுதிகள் எவ்வாறு இணைகின்றன என்பதைக் காட்டுவதே நோக்கம். மேட்ரிபோரைட் வழியே உள்ளே நுழைந்து கால்வாய்கள் வழியே சென்று பாதங்கள் வழியே வெளியேறும் ஒருதிசைத் தொடர் கடல்நீர் ஓட்டம் என்ற கூற்றாக எடுத்துக்கொள்ளக் கூடாது. அமைப்பிலுள்ள திரவம் பெரும்பாலும் தசைச் சுருக்கங்களால் முன்னும் பின்னும் நகர்த்தப்படும் பெரும்பாலும் மூடப்பட்ட திரவத் தொகுதி; மேட்ரிபோரைட் சிறிதளவு திரவப் பரிமாற்றத்துக்கும் அழுத்த ஒழுங்குபடுத்தலுக்குமான வழி. ஒருதிசையில் நீர் தொடர்ந்து உட்புகுந்து வெளியேறுவதைக் காட்டும் அம்புகளை வரைவது தவறான விளக்கமாகும்."),
                    bnote("Anatomical pathway, not a flow chart", "உடற்கூறியல் பாதை, ஓட்ட வரைபடம் அல்ல",
                        "The sequence shows how the canals are connected. It is not a continuous one-way stream of sea water through every tube foot." to
                            "வரிசை கால்வாய்கள் எவ்வாறு இணைந்துள்ளன என்பதைக் காட்டுகிறது. ஒவ்வொரு குழாய்க்கால் வழியேயும் செல்லும் தொடர் ஒருதிசை கடல்நீர் ஓட்டம் அல்ல."),
                    bdef("Stone canal", "கற்கால்வாய் (Stone canal)", "The canal, with calcified walls, that joins the madreporite to the ring canal." to
                        "மேட்ரிபோரைட்டை வளையக் கால்வாயுடன் இணைக்கும், சுண்ணாம்பேறிய சுவருடைய கால்வாய்."),
                    bdef("Ring canal", "வளையக் கால்வாய் (Ring canal)", "The circular canal around the mouth, from which the radial canals arise." to
                        "ஆரக் கால்வாய்கள் கிளம்பும், வாயைச் சுற்றிய வட்டக் கால்வாய்."),
                    bdef("Radial canal", "ஆரக் கால்வாய் (Radial canal)", "A canal that runs along each arm beneath the ambulacral groove." to
                        "ஆம்புலாக்ரல் பள்ளத்துக்குக் கீழ் ஒவ்வொரு கையிலும் செல்லும் கால்வாய்."),
                    bdef("Lateral canal", "பக்கக் கால்வாய் (Lateral canal)", "A short valved canal that joins a radial canal to an ampulla and tube foot." to
                        "கதிர்க் கால்வாயை ஆம்புலா, குழாய்க்காலத்துடன் இணைக்கும் வால்வுடைய குறுகிய கால்வாய்."),
                )),
            bsec("mechanism", "The mechanism of a tube foot", "குழாய்க்காலத்தின் செயல்முறை",
                blocks = listOf(
                    bp("Each tube foot is a hollow, extensible cylinder with a flattened disc at the tip, and the base of the foot is connected through the skeleton to a muscular bulb, the ampulla, which lies in the coelom. The wall of the foot has longitudinal muscle fibres and connective tissue, and the lining is ciliated. The foot works as a piston. When the ampulla contracts, the valve in the lateral canal closes, so that the fluid is forced into the foot and not back into the radial canal. The fluid pressure extends the foot." to
                        "ஒவ்வொரு குழாய்க்காலமும் நுனியில் தட்டையான பற்றுவட்டைக் கொண்ட உள்ளே குழிவுடைய, நீளக்கூடிய உருளை வடிவ உறுப்பு; பாதத்தின் அடிப்பகுதி அகச்சட்டகத்தின் வழியாக உடற்குழியிலுள்ள தசைக் குமிழான ஆம்புலாவுடன் இணைந்துள்ளது. பாதத்தின் சுவரில் நீள்தசை இழைகளும் இணைப்புத் திசுவும் உள்ளன; உட்புறப் படலத்தில் குறுஇழைகள் காணப்படுகின்றன. ஆம்புலாவின் தசைகள் சுருங்கி திரவத்தைக் குழாய்க்காலுக்குள் செலுத்துவதால் இது நீரழுத்தப் பம்பைப் போன்று செயல்படுகிறது. ஆம்புலா சுருங்கும்போது பக்கக் கால்வாயின் வால்வு மூடுகிறது; எனவே திரவம் ஆரக் கால்வாய்க்குப் பின்னோக்கிச் செல்லாமல் குழாய்க்காலுக்குள் செலுத்தப்படுகிறது. திரவ அழுத்தம் பாதத்தை நீட்டுகிறது."),
                    bp("A step is a sequence of four actions: extension, attachment, contraction and release. The extended foot touches the surface and the disc attaches, by an adhesive secretion, and then the longitudinal muscle of the foot contracts and shortens it, drawing the body forward over the point of attachment. The disc then releases, aided by a second secretion, and the foot is carried forward for the next step. The ampulla refills from the radial canal as the foot retracts. The feet work in waves along the arms, with each foot at a different stage, so that the animal moves smoothly." to
                        "குழாய்க்காலின் ஓர் இயக்கப் படிநிலை நான்கு செயல்களைக் கொண்டது: நீள்தல், பற்றிக்கொள்ளுதல், சுருங்குதல், விடுபடுதல். நீண்ட குழாய்க்கால் மேற்பரப்பைத் தொடும்போது அதன் பற்றுவட்டு ஒட்டும் சுரப்பினால் மேற்பரப்புடன் இணைகிறது; பின்னர் பாதத்தின் நீள்தசை சுருங்கி அதைக் குட்டையாக்கி பற்றியுள்ள இடத்தை நோக்கி உடலை முன்னே இழுக்கிறது. வட்டு இரண்டாம் சுரப்பின் உதவியுடன் விடுபடுகிறது; அடுத்த அடிக்காக பாதம் முன்னே கொண்டுசெல்லப்படுகிறது. பாதம் உள்வாங்கும்போது ஆம்புலா கதிர்க் கால்வாயிலிருந்து மீண்டும் நிரம்புகிறது. குழாய்க்கால்கள் கரங்களின் நீளவாக்கில் அலைபோன்ற ஒருங்கிணைந்த வரிசையில், ஒவ்வொன்றும் வெவ்வேறு நிலையில், செயல்படுகின்றன; எனவே விலங்கு சீராக நகர்கிறது."),
                    btable("The cycle of a tube foot", "குழாய்க்காலச் சுழற்சி",
                        listOf("Stage" to "நிலை", "What happens" to "என்ன நடக்கிறது", "Driving force" to "இயக்கும் விசை"),
                        listOf(
                            listOf("Extension" to "நீள்தல்", "The foot lengthens and reaches forward" to "பாதம் நீண்டு முன்னே எட்டுகிறது", "Ampulla contracts; valve closed; fluid pushed into the foot" to "ஆம்புலா சுருங்குகிறது; வால்வு மூடியுள்ளது; திரவம் பாதத்துக்குள் தள்ளப்படுகிறது"),
                            listOf("Attachment" to "ஒட்டுதல்", "The disc sticks to the surface" to "வட்டு மேற்பரப்பில் ஒட்டுகிறது", "Adhesive secretion" to "பற்றவைக்கும் சுரப்பு"),
                            listOf("Contraction" to "சுருங்குதல்", "The foot shortens and pulls the body forward" to "பாதம் குட்டையாகி உடலை முன்னே இழுக்கிறது", "Longitudinal muscle of the foot" to "பாதத்தின் நீள்தசை"),
                            listOf("Release" to "விடுபடுதல்", "The disc lets go and the foot is carried forward" to "வட்டு விடுபட்டு பாதம் முன்னே கொண்டுசெல்லப்படுகிறது", "Release secretion; fluid returns to the ampulla" to "பற்றுதலை விடுவிக்கும் சுரப்பு; திரவம் ஆம்புலாவுக்குத் திரும்புகிறது"),
                        )),
                    bp("Attachment is by adhesion, and not simply by suction, though the disc and its muscles have some part in the grip, and the glue is released and removed in cycles. A tube foot is thus a self-contained working unit of a muscle, a valve, a bulb of fluid and an adhesive disc." to
                        "குழாய்க்கால் மேற்பரப்பில் ஒட்டுவதற்கு பற்றவைக்கும் சுரப்புகள் உதவுகின்றன; இதை வெறும் உறிஞ்சுதல் என்று விளக்கக் கூடாது; வட்டும் அதன் தசைகளும் பிடிப்பில் ஓரளவு பங்கு வகிக்கின்றன; பசை சுழற்சிகளில் வெளியிடப்பட்டு அகற்றப்படுகிறது. எனவே குழாய்க்கால் தசை, வால்வு, திரவம் நிறைந்த தசைக் குமிழ், ஒட்டும் வட்டு ஆகியவற்றாலான தனித்துச் செயல்படும் இயக்க அலகு."),
                    bdef("Ampulla", "ஆம்புலா (Ampulla)", "A muscular bulb at the base of a tube foot whose contraction drives fluid into the foot." to
                        "சுருங்கி திரவத்தைப் பாதத்துக்குள் செலுத்தும் குழாய்க்காலத்தின் அடியிலுள்ள தசைக் குமிழ்."),
                    bdef("Podium", "போடியம் (Podium)", "The extensible part of the tube foot outside the skeleton." to
                        "கூட்டுக்கு வெளியே குழாய்க்காலத்தின் நீளக்கூடிய பகுதி."),
                    bdef("Valve", "வால்வு", "A flap in the lateral canal that prevents fluid from flowing back into the radial canal." to
                        "திரவம் ஆரக் கால்வாய்க்குப் பின்னோக்கிப் பாய்வதைத் தடுக்கும் பக்கக் கால்வாயிலுள்ள மடல்."),
                    bdef("Adhesion", "ஒட்டுதல்", "Sticking to a surface by a chemical secretion." to
                        "வேதிச் சுரப்பால் மேற்பரப்பில் ஒட்டுதல்."),
                )),
            bsec("development", "Origin of the system in development", "வளர்ச்சியில் அமைப்பின் தோற்றம்",
                blocks = listOf(
                    bp("The water-vascular system arises in the larva from the coelom. The archenteron gives off coelomic pouches, and the left anterior pouch becomes the hydrocoel, which is the rudiment of the system. The hydrocoel opens to the outside by a pore, grows into a ring around the oesophagus of the developing adult and sends out five outgrowths that become the radial canals. The tube feet bud off from the radial canals. This coelomic origin explains why the system is lined by a ciliated coelomic epithelium and why its fluid is similar to that of the body cavity.",
                        "நீர்க்குழல் மண்டலம் லார்வா நிலையில் உடற்குழியிலிருந்து உருவாகிறது. முதன்மைக் குடலிலிருந்து (archenteron) உடற்குழிப் பைகள் உருவாகின்றன; இடப்புற முன் உடற்குழிப் பை ஹைட்ரோசீலாக வளர்ச்சியடைகிறது; இதுவே நீர்க்குழல் மண்டலத்தின் தொடக்க அமைப்பாகும். ஹைட்ரோசீல் ஒரு துளை வழியே வெளியே திறந்து, வளர்ந்துவரும் இளம் உயிரியின் உணவுக்குழலைச் சுற்றி வளைய வடிவில் வளர்ந்து, ஆரக் கால்வாய்களாகும் ஐந்து வெளிநீட்சிகளை உருவாக்குகிறது. ஆரக் கால்வாய்களிலிருந்து குழாய்க்கால்கள் மொட்டுகளாக உருவாகின்றன. இவ்வுடற்குழித் தோற்றம், மண்டலம் குறுஇழைகள் கொண்ட உடற்குழி மேலணித் திசுவால் மூடப்பட்டிருப்பதையும், அதன் திரவம் உடற்குழி திரவத்தை ஒத்திருப்பதையும் விளக்குகிறது."),
                    bp("Because of this origin, the system is often described as a coelomic derivative and not as an organ system that has no counterpart in other animals. Its pentaradial arrangement is imposed on the larval structure at metamorphosis, when the left side of the larva becomes the oral surface of the young echinoderm. This provides the clearest link between the radial adult and the bilateral larva, and it is the reason why the system appears so often in the questions on larvae and symmetry.",
                        "இத்தோற்றம் காரணமாக, மண்டலம் பிற விலங்குகளின் உறுப்பமைப்புகளுடன் பரிணாமத் தொடர்பே இல்லாத அமைப்பாகக் கருதாமல் உடற்குழியிலிருந்து தோன்றிய அமைப்பு எனப் பெரும்பாலும் விவரிக்கப்படுகிறது. அதன் உருமாற்ற வளர்ச்சியின்போது லார்வாவின் உடலமைப்பிலிருந்து ஐந்து ஆரங்களைக் கொண்ட அமைப்பு உருவாகிறது; அப்போது லார்வாவின் இடப்பக்கம் இளம் முட்தோலியின் வாய்ப்புறப் பரப்பாக மாறுகிறது. இது ஆரச்சமச்சீருடைய முதிர்ந்த உயிரிக்கும் இருபக்கச் சமச்சீருடைய லார்வாவுக்கும் இடையிலான தெளிவான தொடர்பை அளிக்கிறது; லார்வா, சமச்சீர் பற்றிய வினாக்களில் மண்டலம் அடிக்கடி வருவதற்கு இதுவே காரணம்."),
                )),
            bsec("adhesion", "How a tube foot sticks and lets go", "குழாய்க்கால் எவ்வாறு பற்றிக்கொண்டு விடுபடுகிறது",
                blocks = listOf(
                    bp("The older books say that the sucker of a tube foot holds by suction, produced when the muscles of the ampulla and the foot pull back the centre of the disc. Present evidence is that suction is less important than adhesion by chemical means. The disc of the tube foot has two kinds of gland cells. One releases a sticky secretion that attaches the disc to the surface, and the other releases a secretion that breaks the attachment, so that the foot can be released and moved. The adhesion is thus a cycle of attaching and detaching, and it works under water, which is a difficult condition for an adhesive.",
                        "பழைய நூல்கள் குழல் காலின் உறிஞ்சி, ஆம்புலா, காலின் தசைகள் பற்றுவட்டின் மையப்பகுதியைப் பின்னோக்கி இழுப்பதால் ஏற்படும் உறிஞ்சும் விசையின் மூலம் பற்றிக்கொள்கிறது என்று விளக்கப்பட்டுள்ளது. தற்போதைய சான்றுகளின்படி உறிஞ்சும் விசையைவிட வேதிச் சுரப்புகளால் ஏற்படும் பற்றுதலே முக்கியமானதாகும். குழல் காலின் வட்டில் இரு வகைச் சுரப்பி செல்கள் உள்ளன. ஒன்று வட்டைப் பரப்புடன் இணைக்கும் ஒட்டும் சுரப்பை வெளியிடுகிறது; மற்றொன்று பற்றுதலை நீக்கும் சுரப்பைப் வெளியிடுகிறது; எனவே கால் விடுவிக்கப்பட்டு நகர்த்தப்படலாம். ஒட்டுதல் இவ்வாறு ஒட்டி விடுவிக்கும் சுழற்சி; ஒட்டும் பொருள்கள் செயல்படுவதற்குச் சவாலான நீருக்கடியிலும் இம்முறை செயல்படுகிறது."),
                    bp("For an examination answer the usual teaching is acceptable if the use of both ideas is clear: the tube foot attaches by its disc, using adhesive secretions and, to some extent, suction, and the muscular control of the ampulla and the foot allows the attachment to be released. The student should not claim that suction is the sole mechanism, nor that a single tube foot is strong. It is the coordinated action of hundreds of feet, each holding a small part of the load, that gives the starfish its force.",
                        "தேர்வு விடைக்கு இரு கருத்துகளின் பயன்பாடும் தெளிவாக இருந்தால் வழக்கமான கற்பித்தல் ஏற்கத்தக்கது: குழாய்க்கால் பற்றவைக்கும் சுரப்புகளையும் ஓரளவு உறிஞ்சுதலையும் பயன்படுத்தி வட்டால் இணைகிறது; ஆம்புலா, காலின் தசைக் கட்டுப்பாடு இணைப்பை விடுவிக்க அனுமதிக்கிறது. உறிஞ்சுதலே ஒரே வழிமுறை என்றோ தனியொரு குழாய்க்கால் மிகுந்த விசையை உருவாக்குகிறது என்றோ மாணவர் கூறக்கூடாது. நூற்றுக்கணக்கான கால்கள், ஒவ்வொன்றும் மொத்த இழுவிசையின் ஒரு சிறு பகுதியைத் தாங்கி, ஒருங்கிணைந்து செயல்படுவதே நட்சத்திரமீனுக்குத் தேவையான மொத்தப் பற்றுவிசையை உருவாக்குகிறது."),
                    bdef("Duo-gland adhesive system", "இரட்டைச் சுரப்பிப் பற்றுதல் அமைப்பு (Duo-gland adhesive system)", "The arrangement in a tube foot of one gland cell that secretes an adhesive and another that secretes a de-adhesive, allowing repeated attachment and release." to
                        "ஒட்டும் பொருளைச் சுரக்கும் செல்லும் இணைப்பை விடுவிக்கும் பொருளைச் சுரக்கும் செல்லும் கொண்டு, மீண்டும் மீண்டும் பற்றிக்கொள்ளவும் விடுபடவும் உதவும் குழாய்க்கால் அமைப்பு."),
                )),
            bsec("madreporite", "The madreporite and the fluid of the system", "மேட்ரிபோரைட்டும் மண்டலத்தின் திரவமும்",
                blocks = listOf(
                    bp("The madreporite is a porous plate on the aboral surface through which the stone canal communicates with the outside. In the standard account, sea water enters through the pores and supplies the system. It is now thought that the main role is not a supply of sea water but the regulation of pressure, because the pores are lined with cilia that beat in such a way as to drive fluid out, and the system is a largely closed one that holds a fluid similar in composition to the sea water. The madreporite may also admit a little fluid to make good losses.",
                        "மேட்ரிபோரைட் என்பது மேற்புறத்திலுள்ள, கற்கால்வாய் வெளியுடன் தொடர்பு கொள்ளும் துளையுடைய தகடு. வழக்கமான விளக்கத்தின்படி கடல்நீர் இத்துளைகளின் வழியே நுழைந்து மண்டலத்திற்குத் திரவத்தை வழங்குகிறது. தற்போதைய கருத்துப்படி கடல்நீரை வழங்குவதைவிட மண்டலத்தின் திரவ அழுத்தத்தை ஒழுங்குபடுத்துவதே இதன் முக்கியச் செயலாகக் கருதப்படுகிறது; ஏனெனில் துளைகள் திரவத்தை வெளியே நோக்கி நகர்த்தும் குறுஇழைகளால் மூடப்பட்டுள்ளன; மண்டலம் பெரும்பாலும் கடல்நீரைப் போன்ற வேதியியல் கூறுகளைக் கொண்ட திரவம் நிரம்பிய பெரும்பாலும் மூடிய அமைப்பாகும். இழந்த திரவத்தை ஈடுசெய்ய மேட்ரிபோரைட் சிறிதளவு திரவம் உட்புகவும் அனுமதிக்கக்கூடும்."),
                    bp("A student should write the madreporite in the answer as the plate that connects the system to the outside and helps to regulate it, and may add that the details are still being studied. The fluid in the canals is not blood, although it is sometimes called the water-vascular fluid, and it contains cells that engulf particles and a little protein.",
                        "மாணவர் மேட்ரிபோரைட்டை மண்டலத்தை வெளியுடன் இணைத்து அதைக் கட்டுப்படுத்த உதவும் தகடு என்று எழுத வேண்டும்; விவரங்கள் இன்னும் ஆராயப்படுகின்றன என்றும் சேர்க்கலாம். கால்வாய்களிலுள்ள திரவம் இரத்தம் அல்ல; சில நேரங்களில் நீர்க்குழல் மண்டலத் திரவம் எனப்பட்டாலும், அதில் துகள்களை விழுங்கி உட்கொள்ளும் செல்களும் சிறிதளவு புரதமும் உள்ளன."),
                )),
            bsec("functions", "Functions of the system", "அமைப்பின் செயல்கள்",
                blocks = listOf(
                    bpoints("What the system does", "அமைப்பு என்ன செய்கிறது",
                        "Locomotion, by waves of extension, attachment, contraction and release along the ambulacral grooves." to "ஆம்புலாக்ரல் பள்ளங்களின் வழியே குழாய்க்கால்கள் அலைபோன்று நீண்டு, பற்றிக்கொண்டு, சுருங்கி, விடுபடுவதன் மூலம் இடப்பெயர்ச்சி.",
                        "Attachment to rock and other surfaces, and holding prey." to "பாறைகள் மற்றும் பிற மேற்பரப்புகளில் பற்றிக்கொள்ளுதலும் இரையைப் பிடித்திருத்தலும்.",
                        "Feeding, by holding and handling prey, and by moving particles in the sea cucumbers and crinoids." to "இரையைப் பிடித்துக் கையாளுதல், கடல் வெள்ளரிகள் மற்றும் கிரினாய்டுகளில் உணவுத் துகள்களை நகர்த்துவதன் மூலம் உணவுண்ணுதல்.",
                        "Gas exchange, across the thin walls of the feet." to "குழாய்க்கால்களின் மெல்லிய சுவர்களின் வழியாக வாயுப் பரிமாற்றம்.",
                        "Sensory function, since the feet bear sensory cells and touch the surroundings." to "குழாய்க்கால்களில் உள்ள உணர்வேற்பிச் செல்கள் சுற்றுச்சூழலைத் தொட்டு உணர உதவுகின்றன.",
                    ),
                    bp("The balance among these differs between the classes. In the starfishes and sea urchins the feet are the means of walking. In the brittle stars the feet are not used much for walking, since the arms are moved by muscles in a snake-like manner, and the feet help in feeding and sensing. In the sea cucumbers the tube feet around the mouth are modified into tentacles for feeding, and the feet on the body may be reduced or absent in some groups. In the crinoids the tube feet are used in feeding on particles, and in the gas exchange. The organ is the same, but its use is varied, as is shown by the scheme of the classes." to
                        "இச்செயல்பாடுகளின் முக்கியத்துவம் வெவ்வேறு முட்தோலி வகுப்புகளுக்கிடையே மாறுபடுகிறது. நட்சத்திர மீன்கள், கடல் முள்ளெலிகளில் குழாய்க்கால்கள் இடப்பெயர்ச்சிக்குப் பயன்படுகின்றன. உடையக்கூடிய நட்சத்திரமீன்களில் கரங்கள் தசைகளின் உதவியால் பாம்பைப் போன்று அசைவதால் பாதங்கள் நடக்க அதிகம் பயன்படுவதில்லை; உண்ணவும் உணரவும் உதவுகின்றன. கடல் வெள்ளரிகளில் வாயைச் சுற்றிய குழாய்க்கால்கள் உணவைச் சேகரிக்கும் பற்றுநீட்சிகளாக மாறியுள்ளன; உடலிலுள்ள பாதங்கள் சில குழுக்களில் குறைந்தவை அல்லது இல்லாதவை. கிரினாய்டுகளில் குழாய்க்கால்கள் துகள்களை உண்ணவும் வாயுப் பரிமாற்றத்திலும் பயன்படுகின்றன. அடிப்படை உறுப்பு ஒன்றாக இருந்தாலும் அதன் செயல்பாடுகள் வேறுபடுகின்றன; வகுப்புகளின் திட்டம் இதைக் காட்டுகிறது."),
                    bdef("Ambulacral groove", "ஆம்புலாக்ரல் பள்ளம் (Ambulacral groove)", "The groove along the oral side of a starfish arm that carries the tube feet." to
                        "குழாய்க்காலங்களைக் கொண்ட நட்சத்திரமீனின் கையின் வாய்ப்புறப் பள்ளம்."),
                    bdef("Ambulacrum", "ஆம்புலாக்ரம் (Ambulacrum)", "A radial zone of the body that carries the tube feet." to
                        "குழாய்க்காலங்களைத் தாங்கும் உடலின் கதிர் மண்டலம்."),
                    bdef("Locomotion", "இயக்கம்", "Movement of the whole body from place to place." to
                        "முழு உடலும் ஓரிடத்திலிருந்து மற்றோர் இடத்துக்கு நகர்தல்."),
                )),
            bsec("classes", "The system across the classes", "வகுப்புகளில் அமைப்பு",
                blocks = listOf(
                    btable("Use of the tube feet in the five classes", "ஐந்து வகுப்புகளில் குழாய்க்கால்களின் பயன்பாடுகள்",
                        listOf("Class" to "வகுப்பு", "Principal use of the tube feet" to "குழாய்க்கால்களின் முதன்மைப் பயன்"),
                        listOf(
                            listOf("Asteroidea" to "அஸ்டீராய்டியா", "Walking, attachment, handling prey" to "நடந்து செல்லுதல், பற்றிக்கொள்ளுதல் மற்றும் இரையைக் கையாளுதல்"),
                            listOf("Ophiuroidea" to "ஓஃபியூராய்டியா", "Feeding and sensing; the arms do most of the moving" to "உணவுண்ணுதல் மற்றும் உணர்தல்; இடப்பெயர்ச்சிக்கு முக்கியமாகக் கரங்கள் பயன்படுகின்றன"),
                            listOf("Echinoidea" to "எக்கினாய்டியா", "Walking and attachment, together with the spines" to "முட்களுடன் இணைந்து இடப்பெயர்ச்சிக்கும் பற்றுதலுக்கும் உதவுதல்"),
                            listOf("Holothuroidea" to "ஹோலோதூரோய்டியா", "Oral tube feet are modified to feeding tentacles" to "வாய்ப்புறக் குழாய்க்கால்கள் உணவைச் சேகரிக்கும் பற்றுநீட்சிகளாக மாற்றமடைந்துள்ளன"),
                            listOf("Crinoidea" to "கிரினாய்டியா", "Collecting food particles; gas exchange" to "உணவுத் துகள்களைச் சேகரித்தல்; வாயுப் பரிமாற்றம்"),
                        )),
                    bp("The table is a summary of the general use in each class, and the details differ between species. What it shows is that a single system has been adapted to the different habits of the classes, and that the starfish is the example in which the system is most directly connected to walking. The student should not assume that every class walks on its tube feet." to
                        "அட்டவணை ஒவ்வொரு வகுப்பிலும் பொதுப் பயன்பாட்டின் சுருக்கம்; செயல்பாட்டு விவரங்கள் இனத்திற்கு இனம் மாறுபடுகின்றன. ஒரே அமைப்பு வெவ்வேறு வகுப்புகளின் வாழ்க்கை முறைகளுக்கு ஏற்ப தகவமைந்துள்ளது என்பதையும், நட்சத்திரமீனே அமைப்பு நடத்தலுடன் மிக நேரடியாக இணைந்த எடுத்துக்காட்டு என்பதையும் இது காட்டுகிறது. ஒவ்வொரு வகுப்பும் தன் குழாய்க்காலங்களில் நடக்கிறது என மாணவர் கருதக்கூடாது."),
                    bdef("Tentacle", "உணர்கொம்பு", "A slender, flexible extension of the body used for feeding or sensing, as in the oral tube feet of sea cucumbers." to
                        "கடல் வெள்ளரிகளின் வாய்ப்புறக் குழாய்க்கால்கள் போல உண்ணவோ உணரவோ பயன்படும் மெல்லிய, நெகிழும் உடல் நீட்சி."),
                )),
            bsec("feeding", "Tube feet and the opening of bivalves", "குழாய்க்காலங்களும் இருவோடு திறத்தலும்",
                blocks = listOf(
                    bp("Asterias is a predator of bivalves, and the tube feet are its means of getting at the soft tissue of the prey. Many tube feet attach firmly to the two valves of the bivalve. The starfish arches its body over the prey and pulls on the valves with sustained force. The coordinated hydraulic extension and the muscular tension in the numerous feet give a steady pull, and not a single violent opening. The pull acts against the adductor muscle of the bivalve, which holds the valves closed." to
                        "அஸ்டீரியாஸ் இருவோடுகளை வேட்டையாடுகிறது; இரையின் மென்மையான திசுக்களை அடைவதற்கு அதன் குழாய்க்கால்கள் உதவுகின்றன. பல குழாய்க்கால்கள் இரட்டை ஓட்டின் இரு ஓடுகளிலும் உறுதியாகப் பற்றிக்கொள்கின்றன. நட்சத்திர மீன் இரையின் மீது உடலை வளைத்து ஓடுகளைத் தொடர்ந்து இழுக்கிறது. எண்ணற்ற பாதங்களில் ஒருங்கிணைந்த நீரழுத்த நீட்சியும் தசை இழுவிசையும் ஒரே தடவையில் வலுவாகத் திறப்பதற்குப் பதிலாகத் தொடர்ச்சியான இழுவிசையை அளிக்கின்றன. மூடிகளை மூடி வைக்கும் இருவோட்டின் ஓடுகளை மூடும் இணைப்புத் தசைக்கு எதிராக இழுப்பு செயல்படுகிறது."),
                    bp("When the adductor muscle of the bivalve fatigues, a small gap opens between the valves, and a very small gap is enough. The starfish then everts its cardiac stomach through its mouth and inserts it into the gap. Digestive enzymes act on the prey outside the body of the starfish, and the liquefied tissue is taken into the stomach. It follows that the tube feet assist in opening the prey, and do not crush the shell. The explanation of the feeding in terms of the tube feet is therefore an explanation of how the prey is opened, and the digestion that follows is a separate matter, treated in the chapter on Asterias." to
                        "இருவோட்டின் ஓடுகளை மூடும் இணைப்புத் தசை சோர்வடையும்போது மூடிகளுக்கிடையே சிறு இடைவெளி திறக்கிறது; மிகச் சிறிய இடைவெளியே இதற்குப் போதுமானதாகும். நட்சத்திர மீன் பின்னர் தன் கார்டியாக் இரைப்பையை வாய் வழியாக வெளியே புரட்டி அந்த இடைவெளிக்குள் செலுத்துகிறது. செரிமான நொதிகள் நட்சத்திரமீனின் உடலுக்கு வெளியே இரைமேல் செயல்படுகின்றன; திரவமாக்கப்பட்ட இரைத் திசுக்கள் இரைப்பைக்குள் எடுத்துக்கொள்ளப்படுகின்றன. எனவே குழாய்க்கால்கள் இரையைத் திறக்க உதவுகின்றன; ஓடுகளை நொறுக்குவதில்லை. குழாய்க்காலங்களால் உணவெடுப்பை விளக்குவது இரை எவ்வாறு திறக்கப்படுகிறது என்பதை விளக்குவதாகும்; தொடரும் செரிமானம் தனி விஷயம்; அஸ்டீரியாஸ் அத்தியாயத்தில் கையாளப்படுகிறது."),
                    bdef("Adductor muscle", "ஓடுகளை மூடும் இணைப்புத் தசை (Adductor muscle)", "The muscle that holds the two valves of a bivalve shut." to
                        "இருவோட்டின் இரு மூடிகளையும் மூடி வைக்கும் தசை."),
                    bdef("Fatigue", "சோர்வு", "The reduction of the force of a muscle after sustained use." to
                        "தொடர்ச்சியான செயல்பாட்டிற்குப் பிறகு தசை உருவாக்கும் விசை குறைதல்."),
                    bdef("Extra-oral digestion", "வாய்க்கு வெளிப்புறச் செரிமானம் (Extra-oral digestion)", "Digestion of food outside the body by enzymes secreted onto it." to
                        "உணவின்மீது சுரக்கப்படும் நொதிகளால் உடலுக்கு வெளியே நடைபெறும் செரிமானம்."),
                )),
            bsec("answering", "Writing the answer", "விடை எழுதுதல்",
                blocks = listOf(
                    bp("The recommended order for an answer on this topic is to define the system as a coelom-derived hydraulic canal network, unique to the echinoderms, to trace the pathway from the madreporite to the tube foot, to explain the mechanism of the ampulla and the foot, and to relate the system to locomotion and then to feeding. The conclusion can refer to the diversity of function among the classes. A labelled figure of the pathway with the aboral surface and oral surface marked is of great value." to
                        "இத்தலைப்புக்கான விடையைப் பின்வரும் வரிசையில் அமைக்கலாம்: அமைப்பை முட்தோலிகளுக்கே உரிய உடற்குழியிலிருந்து உருவான நீரழுத்தக் கால்வாய் வலைப்பின்னல் என வரையறுத்தல்; மேட்ரிபோரைட்டிலிருந்து குழாய்க்கால் வரையிலான உறுப்புகளின் இணைப்பை வரிசையாக விளக்குதல்; ஆம்புலா, பாதத்தின் செயல்முறையை விளக்குதல்; அமைப்பை இயக்கம், பின்னர் உணவெடுப்புடன் தொடர்புபடுத்துதல். முடிவில் வெவ்வேறு வகுப்புகளில் இம்மண்டலத்தின் செயல்பாடுகள் எவ்வாறு மாறுபடுகின்றன என்பதைக் குறிப்பிடலாம். வாய்க்கு எதிர்ப்புற மற்றும் வாய்ப்புறப் பரப்புகளைக் குறித்துத் தெளிவாகப் பெயரிடப்பட்ட படம் விடைக்கு மிகவும் பயனுள்ளதாகும்."),
                    bpoints("Common errors", "பொதுவான தவறுகள்",
                        "Drawing arrows to show sea water flowing through the system and out of the feet." to "கடல்நீர் இம்மண்டலத்தினுள் தொடர்ந்து பாய்ந்து குழாய்க்கால்கள் வழியே வெளியேறுவதாக அம்புக்குறிகள் வரைதல்.",
                        "Placing the madreporite on the oral surface." to "மேட்ரிபோரைட்டை வாய்ப்புறப் பரப்பில் இருப்பதாகக் காட்டுதல்.",
                        "Describing the attachment of the foot as simple suction." to "குழாய்க்காலின் பற்றுதலை உறிஞ்சும் விசையால் மட்டுமே ஏற்படுவதாக விவரித்தல்.",
                        "Saying that the tube feet crush the shell of the prey." to "குழாய்க்கால்கள் இரையின் ஓட்டை நொறுக்குகின்றன எனக் கூறுதல்.",
                        "Omitting the valve in the lateral canal when explaining the extension." to "குழாய்க்கால் நீள்வதை விளக்கும்போது பக்கக் கால்வாயிலுள்ள வால்வின் செயல்பாட்டைக் குறிப்பிடாமல் விடுதல்.",
                    ),
                    bdef("Aboral", "மேற்புறம்", "Facing away from the mouth." to
                        "வாயிலிருந்து விலகிய திசையை நோக்கிய."),
                    bdef("Oral", "வாய்ப்புறம் (Oral)", "Facing the mouth, or bearing the mouth." to
                        "வாயை நோக்கிய அல்லது வாய் அமைந்துள்ள."),
                )),
        ),
        summary = listOf(
            "The water-vascular system is a coelom-derived hydraulic canal network unique to the echinoderms." to "நீர்க்குழல் மண்டலம் முட்தோலிகளுக்கே உரிய உடற்குழியிலிருந்து தோன்றிய நீரழுத்தக் கால்வாய் வலைப்பின்னல்.",
            "Pathway: madreporite, stone canal, ring canal, radial canals, lateral canals, ampullae, tube feet." to "பாதை: மேட்ரிபோரைட், கற்கால்வாய், வளையக் கால்வாய், ஆரக் கால்வாய்கள், பக்கக் கால்வாய்கள், ஆம்புலாக்கள், குழாய்க்கால்கள்.",
            "The pathway is anatomical, not a continuous one-way flow of sea water through every foot." to "இது உறுப்புகளின் உடற்கூறியல் இணைப்பைக் குறிக்கும் பாதையாகும்; ஒவ்வொரு குழாய்க்கால் வழியாகவும் கடல்நீர் தொடர்ந்து ஒரே திசையில் பாய்வதைக் குறிக்காது.",
            "Ampullary contraction, with the lateral canal valve closed, extends the foot; the foot muscles retract it." to "பக்கக் கால்வாயின் வால்வு மூடிய நிலையில் ஆம்புலா சுருங்குவதால் குழாய்க்கால் நீள்கிறது; குழாய்க்காலின் தசைகள் அதனை மீண்டும் சுருக்குகின்றன.",
            "The system serves locomotion, attachment, feeding, gas exchange and sensing." to "இம்மண்டலம் இடப்பெயர்ச்சி, பற்றுதல், உணவுண்ணுதல், வாயுப் பரிமாற்றம் மற்றும் உணர்தல் ஆகிய செயல்களுக்குப் பயன்படுகிறது.",
            "Tube feet help to open bivalve prey by sustained traction; they do not crush the shell." to "குழாய்க்கால்கள் தொடர்ச்சியான இழுவிசையை ஏற்படுத்தி இரட்டை ஓடுடைய இரையைத் திறக்க உதவுகின்றன; ஓடுகளை நொறுக்குவதில்லை.",
        ),
        checks = listOf(
            bqc("Where does the water-vascular system arise in the embryo?", "கரு வளர்ச்சியின்போது நீர்க்குழல் மண்டலம் எதிலிருந்து உருவாகிறது?",
                "From part of the coelom, the hydrocoel.", "உடற்குழியின் ஒரு பகுதியான ஹைட்ரோசீலிலிருந்து உருவாகிறது."),
            bqc("What closes when the ampulla contracts, and why?", "ஆம்புலா சுருங்கும்போது எது மூடுகிறது, ஏன்?",
                "The valve in the lateral canal, so that fluid is forced into the tube foot and not back into the radial canal.", "பக்கக் கால்வாயிலுள்ள வால்வு மூடுகிறது; இதனால் திரவம் ஆரக் கால்வாய்க்குத் திரும்பிச் செல்லாமல் குழாய்க்காலுக்குள் செலுத்தப்படுகிறது."),
            bqc("Name the four stages of a tube-foot step.", "குழாய்க்கால அடியின் நான்கு நிலைகளைப் பெயரிடுக.",
                "Extension, attachment, contraction and release.", "நீள்தல், பற்றிக்கொள்ளுதல், சுருங்குதல், விடுபடுதல்."),
            bqc("Do tube feet crush the shell of a bivalve?", "குழாய்க்கால்கள் இருவோட்டின் ஓட்டை நொறுக்குமா?",
                "No. They pull the valves apart until the adductor fatigues; the stomach then does the digestion.", "இல்லை. ஓடுகளை மூடும் இணைப்புத் தசை சோர்வடையும் வரை மூடிகளை இழுத்துப் பிரிக்கின்றன; பின்னர் இரைப்பை செரிக்கிறது."),
        ),
    )
}
