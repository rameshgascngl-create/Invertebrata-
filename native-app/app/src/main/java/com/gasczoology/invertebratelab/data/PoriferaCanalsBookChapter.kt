package com.gasczoology.invertebratelab.data

/** Book-style chapter: canal systems in Porifera. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object PoriferaCanalsBookChapter {
    val chapter = bchapter(
        lessonId = "u2-canals",
        title = "Canal systems in sponges" to "கடற்பஞ்சுகளில் கால்வாய் அமைப்புகள்",
        objectives = listOf(
            "Explain why a sponge needs a canal system and how the system relates to body size." to
                "கடற்பஞ்சுக்குக் கால்வாய் அமைப்பு ஏன் தேவை என்பதையும், அது உடல் அளவுடன் எவ்வாறு தொடர்புடையது என்பதையும் விளக்க.",
            "Describe the asconoid, syconoid and leuconoid grades and name an example of each." to
                "அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு நிலைகளை விவரித்து, ஒவ்வொன்றுக்கும் எடுத்துக்காட்டு கூற.",
            "Trace the path of water in each grade, using the correct names for each opening." to
                "ஒவ்வொரு நிலையிலும் நீரின் பாதையை, ஒவ்வொரு துளையின் சரியான பெயருடன் எழுத.",
            "Compare the three grades in a table and state why the sequence increases efficiency." to
                "மூன்று நிலைகளையும் அட்டவணையில் ஒப்பிட்டு, வரிசை முன்னேறும்போது திறன் அதிகரிப்பதற்கான காரணத்தைக் கூற.",
            "Explain why the direction of flow must be shown correctly in a diagram." to
                "வரைபடத்தில் நீரோட்டத்தின் திசை சரியாகக் காட்டப்பட வேண்டியதன் காரணத்தை விளக்க.",
        ),
        introduction = listOf(
            "A sponge is a pump with no moving parts. Its whole way of life, feeding, breathing and removing waste, depends on a current of water that passes through the body, and the current can only be maintained if water has somewhere to enter, somewhere to be filtered and somewhere to leave. The arrangement of these spaces is called the canal system, or aquiferous system, and it is the most useful single guide to the organisation of a sponge." to
                "கடற்பஞ்சு நகரும் பாகங்கள் இல்லாத ஒரு நீரேற்றி. உணவூட்டம், சுவாசம், கழிவு நீக்கம் என அதன் முழு வாழ்க்கைமுறையும் உடல் வழியே செல்லும் நீரோட்டத்தைச் சார்ந்தது; நீர் நுழைய ஓர் இடம், வடிகட்டப்பட ஓர் இடம், வெளியேற ஓர் இடம் இருந்தால் மட்டுமே இந்த ஓட்டத்தைப் பேண முடியும். இந்த இடங்களின் அமைப்பு கால்வாய் அமைப்பு அல்லது நீர்வழி அமைப்பு எனப்படுகிறது; கடற்பஞ்சின் அமைப்பை அறிய இதுவே மிகப் பயனுள்ள ஒற்றை வழிகாட்டி.",
            "Three patterns of increasing complexity are recognised: the asconoid, the syconoid and the leuconoid. This chapter describes each, follows the water through it, and explains what the increase in complexity achieves. The patterns are grades of organisation. They are not classes, and unrelated sponges may share a grade." to
                "அதிகரிக்கும் சிக்கலுடைய மூன்று அமைப்புகள் அடையாளம் காணப்படுகின்றன: அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு. இந்த அத்தியாயம் ஒவ்வொன்றையும் விவரித்து, அதன் வழியே நீரைப் பின்தொடர்ந்து, சிக்கல் அதிகரிப்பதால் என்ன பயன் என விளக்குகிறது. இவை அமைப்பு நிலைகள்; வகுப்புகள் அல்ல; தொடர்பற்ற கடற்பஞ்சுகளும் ஒரே நிலையைப் பகிரலாம்.",
        ),
        sections = listOf(
            bsec("need", "Why a sponge needs a canal system", "கடற்பஞ்சுக்குக் கால்வாய் அமைப்பு ஏன் தேவை",
                blocks = listOf(
                    bp("Every cell of a sponge must be close to moving water. A cell takes oxygen and food from water and gives carbon dioxide and ammonia back to it, and diffusion over more than a short distance is too slow to serve a large body. A sponge solves this problem by bringing the water to the cells through channels, instead of leaving the cells to wait for it." to
                        "கடற்பஞ்சின் ஒவ்வொரு செல்லும் ஓடும் நீருக்கு அருகில் இருக்க வேண்டும். செல் நீரிலிருந்து ஆக்சிஜனையும் உணவையும் எடுத்து, கார்பன் டை ஆக்சைடையும் அம்மோனியாவையும் அதற்குத் திருப்பித் தருகிறது; குறுந்தொலைவுக்கு மேல் விரவல் பெரிய உடலுக்குப் போதுமான வேகத்தில் நடைபெறாது. செல்கள் நீருக்காகக் காத்திருக்காமல், வழிகள் மூலம் நீரைச் செல்களிடம் கொண்டு வருவதன் மூலம் கடற்பஞ்சு இச்சிக்கலைத் தீர்க்கிறது."),
                    bp("The central difficulty is one of geometry. If a body grows to twice its length in every direction, its volume increases eight times, but its surface increases only four times. A sponge that simply grew larger would therefore soon have far more cells to supply than surface to supply them. The three grades are three ways of folding the wall so that the area of choanocytes exposed to water keeps pace with the volume of the body." to
                        "முதன்மைச் சிக்கல் வடிவியல் சார்ந்தது. உடல் எல்லாத் திசைகளிலும் இருமடங்கு நீளமானால் அதன் கொள்ளளவு எட்டு மடங்கும் பரப்பு நான்கு மடங்கும் மட்டுமே கூடும். எனவே வெறுமனே பெரிதாக வளரும் கடற்பஞ்சுக்கு, அவற்றைப் பேணும் பரப்பைவிட ஊட்ட வேண்டிய செல்கள் மிக அதிகமாகிவிடும். நீருக்கு வெளிப்படும் கோனோசைட் பரப்பு உடலின் கொள்ளளவுடன் சேர்ந்து வளர உடற்சுவரை மடிக்கும் மூன்று வழிகளே இந்த மூன்று நிலைகள்."),
                    bdef("Aquiferous system", "நீர்வழி அமைப்பு",
                        "The system of ostia, canals, chambers and oscula through which a current of water passes through a sponge." to
                            "கடற்பஞ்சின் வழியே நீரோட்டம் செல்லும் ஆஸ்டியா, கால்வாய், அறை, ஆஸ்குலம் ஆகியவற்றின் தொகுப்பு."),
                    bdef("Grade of organisation", "அமைப்பு நிலை",
                        "A level of structural complexity that may be reached independently by unrelated groups. Asconoid, syconoid and leuconoid are grades; they are not taxonomic classes." to
                            "தொடர்பற்ற குழுக்கள் தனித்தனியாகவும் அடையக்கூடிய அமைப்புச் சிக்கலின் நிலை. அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு என்பவை நிலைகள்; வகைப்பாட்டு வகுப்புகள் அல்ல."),
                    bdef("Ostium (plural: ostia)", "ஆஸ்டியம் (பன்மை: ஆஸ்டியா)",
                        "A minute inhalant pore in the outer wall through which water enters the sponge." to
                            "புறச்சுவரில் உள்ள, நீர் கடற்பஞ்சுக்குள் நுழையும் நுண்ணிய உட்செல்லும் துளை."),
                    bdef("Osculum", "ஆஸ்குலம்",
                        "A large exhalant opening through which water leaves the sponge." to
                            "நீர் கடற்பஞ்சிலிருந்து வெளியேறும் பெரிய வெளிச்செல்லும் துளை."),
                )),
            bsec("asconoid", "The asconoid grade", "அஸ்கோனாய்டு நிலை",
                blocks = listOf(
                    bp("The asconoid grade is the simplest. The body is a thin-walled tube, closed at the base and open at the top. The wall is pierced by ostia, which are formed by porocytes, and the whole of the central cavity, the spongocoel, is lined by choanocytes. The standard example is Leucosolenia, a small calcareous sponge that forms a cluster of slender tubes joined at the base." to
                        "அஸ்கோனாய்டு நிலை மிக எளியது. உடல் அடியில் மூடிய, மேலே திறந்த மெல்லிய சுவருடைய குழாய். சுவர் போரோசைட்டுகளால் உருவான ஆஸ்டியாவால் துளைக்கப்பட்டுள்ளது; நடுக்குழியான ஸ்பாஞ்சோசீல் முழுவதும் கோனோசைட்டுகளால் மூடப்பட்டுள்ளது. நிலையான எடுத்துக்காட்டு லியூக்கோசோலீனியா; அடியில் இணைந்த மெல்லிய குழாய்க் கொத்தை உருவாக்கும் சிறு சுண்ணாம்புக் கடற்பஞ்சு."),
                    bpoints("Path of water in an asconoid sponge", "அஸ்கோனாய்டு கடற்பஞ்சில் நீரின் பாதை",
                        "Water enters through the ostia in the thin outer wall." to "மெல்லிய வெளிச்சுவரிலுள்ள ஆஸ்டியா வழியாக நீர் நுழைகிறது.",
                        "It passes directly into the spongocoel, whose lining of choanocytes filters it." to "அது நேரடியாக ஸ்பாஞ்சோசீலுக்குள் செல்கிறது; அதன் கோனோசைட் உள்ளடுக்கு வடிகட்டுகிறது.",
                        "It leaves through the osculum at the top." to "மேலுள்ள ஆஸ்குலம் வழியாக வெளியேறுகிறது.",
                    ),
                    bp("The simplicity of the grade limits the size of the animal. The choanocytes cover only the area of the inner surface of the tube, which increases in step with the surface of the body and not with its volume. A large asconoid sponge would have a wide spongocoel containing a great deal of water served by a small area of choanocytes, and the current could not renew that water fast enough. For this reason asconoid sponges are small, rarely more than a few centimetres high." to
                        "இந்த நிலையின் எளிமை விலங்கின் அளவை வரையறுக்கிறது. கோனோசைட்டுகள் குழாயின் உள்பரப்பை மட்டுமே மூடுகின்றன; அது உடலின் பரப்புடன் உயர்கிறதே தவிர கொள்ளளவுடன் அல்ல. பெரிய அஸ்கோனாய்டு கடற்பஞ்சில் சிறிய கோனோசைட் பரப்பு, அதிக நீர் கொண்ட அகன்ற ஸ்பாஞ்சோசீலுக்குச் சேவை செய்ய வேண்டியிருக்கும்; ஓட்டத்தால் அந்த நீரை விரைவாகப் புதுப்பிக்க முடியாது. இதனால் அஸ்கோனாய்டு கடற்பஞ்சுகள் சிறியவை; சில சென்டிமீட்டருக்கு மேல் அரிதாகவே வளர்கின்றன."),
                    bdef("Asconoid", "அஸ்கோனாய்டு",
                        "The simplest grade of canal system, in which the wall is thin and unfolded and the spongocoel is lined by choanocytes. Example: Leucosolenia." to
                            "கால்வாய் அமைப்பின் எளிய நிலை; சுவர் மெல்லியதும் மடிப்பற்றதும், ஸ்பாஞ்சோசீல் கோனோசைட்டுகளால் மூடப்பட்டதும் ஆகும். எடுத்துக்காட்டு: லியூக்கோசோலீனியா."),
                    bdef("Spongocoel", "ஸ்பாஞ்சோசீல்",
                        "The central cavity of a sponge into which the water passes before it leaves by the osculum." to
                            "ஆஸ்குலம் வழியாக வெளியேறும் முன் நீர் செல்லும் கடற்பஞ்சின் நடுக்குழி."),
                )),
            bsec("syconoid", "The syconoid grade", "சைக்கனாய்டு நிலை",
                blocks = listOf(
                    bp("In the syconoid grade the body wall is thicker and is folded outwards into a series of finger-like pockets. The pockets are the radial canals, and they alternate with the incurrent canals, which are the spaces between them. The choanocytes are confined to the radial canals; the spongocoel is lined by flat cells and acts only as an exit chamber. The standard example is Sycon, described in the preceding chapter." to
                        "சைக்கனாய்டு நிலையில் உடற்சுவர் தடித்து, வெளிநோக்கி விரல்போன்ற பைகளாக மடிந்துள்ளது. இப்பைகள் ஆரக் கால்வாய்கள்; அவற்றுக்கிடையிலான இடைவெளிகளான உட்செல்லும் கால்வாய்களுடன் மாறிமாறி அமைந்துள்ளன. கோனோசைட்டுகள் ஆரக் கால்வாய்களில் மட்டுமே உள்ளன; ஸ்பாஞ்சோசீல் தட்டை செல்களால் மூடப்பட்டு வெளியேறும் அறையாக மட்டும் செயல்படுகிறது. நிலையான எடுத்துக்காட்டு சைக்கான்; முந்தைய அத்தியாயத்தில் விளக்கப்பட்டுள்ளது."),
                    bpoints("Path of water in a syconoid sponge", "சைக்கனாய்டு கடற்பஞ்சில் நீரின் பாதை",
                        "Water enters through the ostia." to "ஆஸ்டியா வழியாக நீர் நுழைகிறது.",
                        "It flows into the incurrent canals." to "உட்செல்லும் கால்வாய்களுக்குள் பாய்கிறது.",
                        "It passes through the prosopyles into the radial canals." to "புரோசோபைல்கள் வழியாக ஆரக் கால்வாய்களுக்குச் செல்கிறது.",
                        "The choanocytes of the radial canals filter it." to "ஆரக் கால்வாய்களின் கோனோசைட்டுகள் வடிகட்டுகின்றன.",
                        "It passes through the apopyles into the spongocoel." to "அப்போபைல்கள் வழியாக ஸ்பாஞ்சோசீலுக்குச் செல்கிறது.",
                        "It leaves through the osculum." to "ஆஸ்குலம் வழியாக வெளியேறுகிறது.",
                    ),
                    bp("The folding multiplies the area of choanocytes without greatly increasing the volume of the spongocoel, so a syconoid sponge can be larger than an asconoid one and can filter more water. Syconoid sponges are, however, still tubes or vases with a single osculum, and the arrangement cannot be extended indefinitely." to
                        "மடிப்பு, ஸ்பாஞ்சோசீலின் கொள்ளளவை அதிகம் கூட்டாமலே கோனோசைட் பரப்பைப் பெருக்குகிறது; எனவே சைக்கனாய்டு கடற்பஞ்சு அஸ்கோனாய்டைவிடப் பெரிதாக இருக்கவும் அதிக நீரை வடிகட்டவும் முடியும். எனினும் சைக்கனாய்டு கடற்பஞ்சுகள் ஒரே ஆஸ்குலம் கொண்ட குழாய் அல்லது குவளைகளே; இந்த அமைப்பை வரம்பின்றி விரிவாக்க இயலாது."),
                    bdef("Syconoid", "சைக்கனாய்டு",
                        "The grade of canal system in which the wall is folded into radial canals lined by choanocytes, alternating with incurrent canals, with a central cavity lined by flat cells. Example: Sycon." to
                            "சுவர் மடிந்து கோனோசைட்டுகள் உள்ள ஆரக் கால்வாய்களும் அவற்றுடன் மாறிமாறி உட்செல்லும் கால்வாய்களும், தட்டை செல்கள் மூடிய நடுக்குழியும் கொண்ட கால்வாய் அமைப்பு நிலை. எடுத்துக்காட்டு: சைக்கான்."),
                    bdef("Prosopyle", "புரோசோபைல்",
                        "The pore through which water passes from an incurrent canal into a radial canal." to
                            "உட்செல்லும் கால்வாயிலிருந்து ஆரக் கால்வாய்க்கு நீர் செல்லும் துளை."),
                    bdef("Apopyle", "அப்போபைல்",
                        "The opening through which water passes from a radial canal into the spongocoel." to
                            "ஆரக் கால்வாயிலிருந்து ஸ்பாஞ்சோசீலுக்கு நீர் செல்லும் திறப்பு."),
                )),
            bsec("leuconoid", "The leuconoid grade", "லியூக்கோனாய்டு நிலை",
                blocks = listOf(
                    bp("The leuconoid grade is the most complex and the most common; it is found in nearly all demosponges and in some calcareous sponges. The wall is very thick and the choanocytes are no longer lined along tubes. Instead they are gathered into hundreds or thousands of small, rounded, flagellated chambers scattered through the mesohyl. Water enters through ostia into a branching system of incurrent canals, passes into the chambers, and leaves through a branching system of excurrent canals that join to open at one or more oscula. The central cavity is much reduced or absent." to
                        "லியூக்கோனாய்டு நிலை மிகச் சிக்கலானதும் மிகப் பொதுவானதும்; கிட்டத்தட்ட எல்லா டெமோஸ்பாஞ்சுகளிலும் சில சுண்ணாம்புக் கடற்பஞ்சுகளிலும் உள்ளது. சுவர் மிகத் தடித்தது; கோனோசைட்டுகள் இனி குழாய்களில் வரிசையாக இல்லாமல் மீசோஹைல் முழுவதும் சிதறிய நூற்றுக்கணக்கான அல்லது ஆயிரக்கணக்கான சிறு உருண்டை கசையிழை அறைகளில் தொகுக்கப்பட்டுள்ளன. நீர் ஆஸ்டியா வழியாகக் கிளைத்த உட்செல்லும் கால்வாய்களில் நுழைந்து, அறைகளுக்குள் சென்று, கிளைத்த வெளிச்செல்லும் கால்வாய்கள் வழியாக ஒன்று அல்லது அதற்கு மேற்பட்ட ஆஸ்குலத்தில் வெளியேறுகிறது. நடுக்குழி மிகக் குறைந்தோ இல்லாமலோ உள்ளது."),
                    bpoints("Path of water in a leuconoid sponge", "லியூக்கோனாய்டு கடற்பஞ்சில் நீரின் பாதை",
                        "Water enters through the ostia." to "ஆஸ்டியா வழியாக நீர் நுழைகிறது.",
                        "It passes along the branching incurrent canals." to "கிளைத்த உட்செல்லும் கால்வாய்களில் செல்கிறது.",
                        "It enters the flagellated chambers, where the choanocytes filter it." to "கசையிழை அறைகளுக்குள் நுழைகிறது; அங்கு கோனோசைட்டுகள் வடிகட்டுகின்றன.",
                        "It leaves the chambers into the excurrent canals." to "அறைகளிலிருந்து வெளிச்செல்லும் கால்வாய்களுக்குள் வெளியேறுகிறது.",
                        "It leaves the sponge through one or more oscula." to "ஒன்று அல்லது அதற்கு மேற்பட்ட ஆஸ்குலம் வழியாக கடற்பஞ்சிலிருந்து வெளியேறுகிறது.",
                    ),
                    bp("Three subtypes are distinguished by the connection between a chamber and the excurrent canal. In the eurypylous type the chamber opens directly into the excurrent canal. In the aphodal type a short canal, the aphodus, joins the chamber to the excurrent canal. In the diplodal type both a short entrance canal, the prosodus, and an exit canal, the aphodus, are present. The distinction is of interest chiefly in advanced courses." to
                        "அறைக்கும் வெளிச்செல்லும் கால்வாய்க்கும் இடையிலான இணைப்பைக் கொண்டு மூன்று துணைவகைகள் பிரிக்கப்படுகின்றன. யூரிபைலஸ் வகையில் அறை நேரடியாக வெளிச்செல்லும் கால்வாயில் திறக்கிறது. அபோடல் வகையில் அபோடஸ் என்ற குறுகிய கால்வாய் அறையை வெளிச்செல்லும் கால்வாயுடன் இணைக்கிறது. டிப்ளோடல் வகையில் புரோசோடஸ் என்ற குறுகிய நுழைவுக் கால்வாயும் அபோடஸ் என்ற வெளியேற்றக் கால்வாயும் இரண்டும் உள்ளன. இந்த வேறுபாடு முக்கியமாக மேம்பட்ட பாடங்களுக்கே பொருத்தமானது."),
                    bdef("Leuconoid", "லியூக்கோனாய்டு",
                        "The most complex grade of canal system, in which the choanocytes are confined to numerous small flagellated chambers connected by incurrent and excurrent canals and the central cavity is reduced or absent. Example: Spongilla." to
                            "கோனோசைட்டுகள் எண்ணற்ற சிறு கசையிழை அறைகளில் மட்டும் இருந்து, உட்செல்லும், வெளிச்செல்லும் கால்வாய்களால் இணைக்கப்பட்டு, நடுக்குழி குறைந்தோ இல்லாமலோ உள்ள மிகச் சிக்கலான கால்வாய் அமைப்பு நிலை. எடுத்துக்காட்டு: ஸ்பாஞ்சில்லா."),
                    bdef("Flagellated chamber", "கசையிழை அறை",
                        "A small rounded chamber lined by choanocytes in a leuconoid sponge." to
                            "லியூக்கோனாய்டு கடற்பஞ்சில் கோனோசைட்டுகள் வரிசையாக உள்ள சிறு உருண்டை அறை."),
                    bdef("Excurrent canal", "வெளிச்செல்லும் கால்வாய்",
                        "A canal that carries filtered water away from the flagellated chambers towards an osculum." to
                            "வடிகட்டப்பட்ட நீரை கசையிழை அறைகளிலிருந்து ஆஸ்குலத்தை நோக்கிக் கொண்டு செல்லும் கால்வாய்."),
                )),
            bsec("comparison", "Comparison of the three grades", "மூன்று நிலைகளின் ஒப்பீடு",
                blocks = listOf(
                    btable("Comparison of asconoid, syconoid and leuconoid grades", "அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு நிலைகளின் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Asconoid" to "அஸ்கோனாய்டு", "Syconoid" to "சைக்கனாய்டு", "Leuconoid" to "லியூக்கோனாய்டு"),
                        listOf(
                            listOf("Example" to "எடுத்துக்காட்டு", "Leucosolenia" to "லியூக்கோசோலீனியா", "Sycon" to "சைக்கான்", "Spongilla" to "ஸ்பாஞ்சில்லா"),
                            listOf("Body wall" to "உடற்சுவர்", "Thin, unfolded" to "மெல்லியது, மடிப்பற்றது", "Thick, folded into canals" to "தடித்தது, கால்வாய்களாக மடிந்தது", "Very thick, with many chambers" to "மிகத் தடித்தது, பல அறைகளுடன்"),
                            listOf("Choanocytes" to "கோனோசைட்டுகள்", "Line the spongocoel" to "ஸ்பாஞ்சோசீலை மூடுகின்றன", "Line the radial canals" to "ஆரக் கால்வாய்களை மூடுகின்றன", "Line small chambers" to "சிறு அறைகளை மூடுகின்றன"),
                            listOf("Spongocoel" to "ஸ்பாஞ்சோசீல்", "Large, lined by choanocytes" to "பெரியது, கோனோசைட் உள்ளடுக்கு", "Present, lined by flat cells" to "உண்டு, தட்டை செல் உள்ளடுக்கு", "Reduced or absent" to "குறைந்தது அல்லது இல்லை"),
                            listOf("Oscula" to "ஆஸ்குலம்", "One" to "ஒன்று", "One" to "ஒன்று", "One to many" to "ஒன்று முதல் பல"),
                            listOf("Size reached" to "அடையும் அளவு", "Small" to "சிறியது", "Medium" to "நடுத்தரம்", "Large" to "பெரியது"),
                        )),
                    bp("The progression from asconoid to leuconoid is a progression in the area of choanocytes in relation to the volume of the sponge, and with it in the volume of water that can be filtered. Larger bodies can therefore be supported, and the leuconoid grade has made possible the large and varied sponges that dominate the phylum. The sequence is one of increasing efficiency; it should not be taken as a statement that every sponge passes through all three stages." to
                        "அஸ்கோனாய்டிலிருந்து லியூக்கோனாய்டுக்கு முன்னேறுவது, கடற்பஞ்சின் கொள்ளளவுக்கு ஏற்ப கோனோசைட் பரப்பு அதிகரிப்பதையும், அதனுடன் வடிகட்டக்கூடிய நீரின் அளவு அதிகரிப்பதையும் குறிக்கிறது. அதனால் பெரிய உடல்களைத் தாங்க முடிகிறது; தொகுதியில் ஆதிக்கம் செலுத்தும் பெரிய, பலவகை கடற்பஞ்சுகள் லியூக்கோனாய்டு நிலையால் சாத்தியமாயின. இது திறன் அதிகரிக்கும் வரிசை; எல்லாக் கடற்பஞ்சுகளும் மூன்று நிலைகளையும் கடக்கின்றன என்ற கூற்றாகக் கொள்ளக் கூடாது."),
                    bnote("Grades are not classes", "நிலைகள் வகுப்புகள் அல்ல",
                        "Calcarea include asconoid, syconoid and leuconoid members, whereas the Demospongiae are almost all leuconoid. The grade tells you how the canal system is built, not which class the sponge belongs to. Classes are separated by the skeleton." to
                            "கால்கேரியாவில் அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு மூன்றும் உண்டு; டெமோஸ்பாஞ்சுகள் கிட்டத்தட்ட அனைத்தும் லியூக்கோனாய்டு. கால்வாய் அமைப்பு எவ்வாறு அமைந்துள்ளது என்பதையே நிலை கூறுகிறது; கடற்பஞ்சு எந்த வகுப்பு என்பதை அல்ல. வகுப்புகளை எலும்புக்கூடே பிரிக்கிறது."),
                )),
            bsec("functions", "What the current does for the sponge", "நீரோட்டம் கடற்பஞ்சுக்குச் செய்வது என்ன",
                blocks = listOf(
                    bp("The current is the sponge's only means of exchange with its surroundings, and it serves four functions at once. It brings food, in the form of bacteria, minute algae and fine organic particles, which are caught by the collar of each choanocyte and passed into the cell. It brings dissolved oxygen to every cell and carries away carbon dioxide. It removes ammonia and other soluble wastes, together with undigested material, which are discharged through the osculum. Finally, in most sponges the current carries the sperm and the larvae, so that reproduction also depends on it." to
                        "இவ்வோட்டமே கடற்பஞ்சு சுற்றுச்சூழலுடன் பரிமாறிக்கொள்ளும் ஒரே வழி; அது ஒரே நேரத்தில் நான்கு பணிகளைச் செய்கிறது. பாக்டீரியா, நுண்பாசிகள், நுண் கரிமத் துகள்கள் வடிவில் உணவைக் கொண்டு வருகிறது; ஒவ்வொரு கோனோசைட்டின் காலரும் அதைப் பிடித்து செல்லுக்குள் செலுத்துகிறது. கரைந்த ஆக்சிஜனை ஒவ்வொரு செல்லுக்கும் கொண்டு வந்து கார்பன் டை ஆக்சைடை அகற்றுகிறது. அம்மோனியா மற்றும் பிற கரையும் கழிவுகளையும் செரிக்காத பொருளையும் ஆஸ்குலம் வழியாக வெளியேற்றுகிறது. இறுதியாக, பெரும்பாலான கடற்பஞ்சுகளில் விந்தணுக்களையும் இளம் உயிரிகளையும் ஓட்டமே கொண்டு செல்கிறது; இனப்பெருக்கமும் அதைச் சார்ந்தது."),
                    bp("The amount of water handled is large. A sponge a few centimetres high can pass many times its own volume through its body in a single hour, and the water leaving the osculum is moving faster than the water entering the ostia, because the same volume is gathered through a much narrower opening. This is why the osculum is the part of a sponge in which the current is easiest to see, for example when a dye is placed near it." to
                        "கையாளப்படும் நீரின் அளவு பெரியது. சில சென்டிமீட்டர் உயரமுள்ள கடற்பஞ்சு ஒரே மணி நேரத்தில் தன் கொள்ளளவைப் போல பல மடங்கு நீரை உடலின் வழியே செலுத்த முடியும்; அதே கொள்ளளவு மிகக் குறுகிய திறப்பின் வழியாகத் திரட்டப்படுவதால், ஆஸ்குலத்தின் வழியே வெளியேறும் நீர் ஆஸ்டியாவில் நுழையும் நீரைவிட வேகமாகச் செல்கிறது. இதனால்தான் ஆஸ்குலத்தின் அருகே சாயம் வைக்கும்போது, கடற்பஞ்சில் ஓட்டம் மிக எளிதாகத் தெரியும் பகுதி அதுவாகிறது."),
                    bnote("Sponges have no mouth or gut", "கடற்பஞ்சுக்கு வாயும் குடலும் இல்லை",
                        "The osculum is an exit for water, not a mouth, and the spongocoel is a water chamber, not a stomach. Digestion in a sponge is intracellular and takes place inside choanocytes and amoebocytes. Describing the osculum as the mouth is a frequent error in answers and is marked as one." to
                            "ஆஸ்குலம் நீர் வெளியேறும் வழி; வாய் அல்ல. ஸ்பாஞ்சோசீல் நீர் அறை; வயிறு அல்ல. கடற்பஞ்சில் செரிமானம் செல்லினுள் நிகழ்கிறது; கோனோசைட்டுகளிலும் அமீபோசைட்டுகளிலும் நடைபெறுகிறது. ஆஸ்குலத்தை வாய் எனக் கூறுவது விடைகளில் அடிக்கடி வரும் பிழை; அது பிழையாகவே மதிப்பிடப்படும்."),
                    bp("The current also explains why sponges are sensitive to their habitat. Water that carries much silt clogs the ostia and the chambers, and a sponge in turbid water must stop pumping, close its pores or grow in a form that sheds sediment. Many species are therefore found where the water is clear, either in deep water or on rock faces swept by currents, and a rise in suspended matter can kill a sponge that is otherwise healthy." to
                        "ஓட்டம், கடற்பஞ்சுகள் வாழிடத்துக்கு உணர்திறன் உடையவை என்பதையும் விளக்குகிறது. அதிக வண்டல் கொண்ட நீர் ஆஸ்டியாவையும் அறைகளையும் அடைக்கும்; கலங்கிய நீரில் கடற்பஞ்சு உந்துவதை நிறுத்த வேண்டும், துளைகளை மூட வேண்டும், அல்லது வண்டலை உதிர்க்கும் வடிவில் வளர வேண்டும். எனவே பல இனங்கள் ஆழ்நீரிலோ நீரோட்டம் வீசும் பாறை முகங்களிலோ தெளிந்த நீரில் காணப்படுகின்றன; மிதக்கும் பொருள் அதிகரித்தால் இல்லையெனில் ஆரோக்கியமான கடற்பஞ்சும் இறக்கக்கூடும்."),
                )),
            bsec("direction", "Drawing the direction of flow correctly", "நீரோட்டத்தின் திசையைச் சரியாக வரைதல்",
                blocks = listOf(
                    bp("A diagram of a canal system is a diagram of a process, and the arrows are its most important content. The sponge works only because water flows in one direction, from the inhalant openings to the exhalant opening, and the physiology represented by a diagram is changed if the arrows are reversed or if the pores are interchanged." to
                        "கால்வாய் அமைப்பின் வரைபடம் ஒரு செயல்முறையின் வரைபடம்; அம்புகளே அதன் மிக முக்கிய உள்ளடக்கம். உட்செல்லும் துளைகளிலிருந்து வெளிச்செல்லும் துளைக்கு நீர் ஒரு திசையில் மட்டும் பாய்வதாலேயே கடற்பஞ்சு செயல்படுகிறது; அம்புகள் தலைகீழாகவோ துளைகள் மாற்றியோ இருந்தால் வரைபடம் குறிக்கும் உடலியங்கியலே மாறிவிடும்."),
                    bpoints("Rules for the diagram", "வரைபடத்துக்கான விதிகள்",
                        "Arrows run from the ostia inwards and from the spongocoel or excurrent canals to the osculum, never the other way." to "அம்புகள் ஆஸ்டியாவிலிருந்து உள்ளும், ஸ்பாஞ்சோசீல் அல்லது வெளிச்செல்லும் கால்வாய்களிலிருந்து ஆஸ்குலத்துக்கும் செல்ல வேண்டும்; ஒருபோதும் எதிர்திசையில் அல்ல.",
                        "A prosopyle joins an incurrent canal to a choanocyte-lined radial canal." to "புரோசோபைல் உட்செல்லும் கால்வாயை கோனோசைட் உள்ள ஆரக் கால்வாயுடன் இணைக்கிறது.",
                        "An apopyle joins a radial canal to the spongocoel." to "அப்போபைல் ஆரக் கால்வாயை ஸ்பாஞ்சோசீலுடன் இணைக்கிறது.",
                        "Choanocytes are drawn only where the grade places them: in the spongocoel for asconoid, in radial canals for syconoid, in chambers for leuconoid." to "கோனோசைட்டுகள் அந்த நிலை அமைக்கும் இடத்தில் மட்டும் வரையப்பட வேண்டும்: அஸ்கோனாய்டில் ஸ்பாஞ்சோசீலில், சைக்கனாய்டில் ஆரக் கால்வாய்களில், லியூக்கோனாய்டில் அறைகளில்.",
                        "Label the large opening an osculum and the small ones ostia." to "பெரிய துளையை ஆஸ்குலம் என்றும் சிறியவற்றை ஆஸ்டியா என்றும் குறிக்க.",
                    ),
                    bnote("Common errors", "பொதுவான பிழைகள்",
                        "Interchanging prosopyle and apopyle; placing choanocytes in the spongocoel of Sycon; calling the spongocoel a stomach; and giving Sycon the leuconoid path. Each of these changes the physiology shown by the diagram and is marked as an error." to
                            "புரோசோபைலையும் அப்போபைலையும் மாற்றி எழுதுதல்; சைக்கானின் ஸ்பாஞ்சோசீலில் கோனோசைட்டுகளை வைத்தல்; ஸ்பாஞ்சோசீலை வயிறு எனல்; சைக்கானுக்கு லியூக்கோனாய்டு பாதையைத் தருதல். ஒவ்வொன்றும் வரைபடம் காட்டும் உடலியங்கியலை மாற்றுகிறது; பிழையாகக் கருதப்படும்."),
                )),
        ),
        summary = listOf(
            "A sponge feeds, respires and excretes by passing a current of water through a canal system, and the system limits the size of the body." to "கடற்பஞ்சு கால்வாய் அமைப்பு வழியாக நீரோட்டத்தைச் செலுத்தி உண்கிறது, சுவாசிக்கிறது, கழிவை நீக்குகிறது; இந்த அமைப்பு உடலின் அளவை வரையறுக்கிறது.",
            "In the asconoid grade the wall is unfolded and the spongocoel is lined by choanocytes; the example is Leucosolenia." to "அஸ்கோனாய்டு நிலையில் சுவர் மடிப்பற்றது; ஸ்பாஞ்சோசீல் கோனோசைட்டுகளால் மூடப்பட்டது; எடுத்துக்காட்டு லியூக்கோசோலீனியா.",
            "In the syconoid grade the wall is folded into radial canals lined by choanocytes; the example is Sycon, and the path is ostia, incurrent canals, prosopyles, radial canals, apopyles, spongocoel, osculum." to "சைக்கனாய்டு நிலையில் சுவர் கோனோசைட் உள்ள ஆரக் கால்வாய்களாக மடிந்துள்ளது; எடுத்துக்காட்டு சைக்கான்; பாதை ஆஸ்டியா, உட்செல்லும் கால்வாய், புரோசோபைல், ஆரக் கால்வாய், அப்போபைல், ஸ்பாஞ்சோசீல், ஆஸ்குலம்.",
            "In the leuconoid grade the choanocytes lie in many small chambers joined by incurrent and excurrent canals and the spongocoel is reduced or absent." to "லியூக்கோனாய்டு நிலையில் கோனோசைட்டுகள் உட்செல்லும், வெளிச்செல்லும் கால்வாய்களால் இணைந்த பல சிறு அறைகளில் உள்ளன; ஸ்பாஞ்சோசீல் குறைந்தோ இல்லாமலோ உள்ளது.",
            "The sequence increases the area of choanocytes in relation to the volume of the body and so allows larger and more efficient sponges." to "இவ்வரிசை உடலின் கொள்ளளவுக்கேற்ப கோனோசைட் பரப்பை அதிகரித்து, பெரிய, அதிகத் திறன் கொண்ட கடற்பஞ்சுகளை சாத்தியமாக்குகிறது.",
            "The grades are patterns of organisation and not taxonomic classes, and the direction of flow must always be drawn correctly." to "இவை அமைப்பு முறைகளே, வகைப்பாட்டு வகுப்புகள் அல்ல; நீரோட்டத்தின் திசை எப்போதும் சரியாக வரையப்பட வேண்டும்.",
        ),
        checks = listOf(
            bqc("Name the three grades of canal system in sponges.", "கடற்பஞ்சுகளின் கால்வாய் அமைப்பின் மூன்று நிலைகளைக் குறிப்பிடுக.",
                "Asconoid, syconoid and leuconoid.", "அஸ்கோனாய்டு, சைக்கனாய்டு, லியூக்கோனாய்டு."),
            bqc("Where are the choanocytes in a syconoid sponge?", "சைக்கனாய்டு கடற்பஞ்சில் கோனோசைட்டுகள் எங்கு உள்ளன?",
                "In the radial canals only.", "ஆரக் கால்வாய்களில் மட்டும்."),
            bqc("Which pore leads from the incurrent canal to the radial canal?", "உட்செல்லும் கால்வாயிலிருந்து ஆரக் கால்வாய்க்குச் செல்லும் துளை எது?",
                "The prosopyle.", "புரோசோபைல்."),
            bqc("Why are asconoid sponges small?", "அஸ்கோனாய்டு கடற்பஞ்சுகள் ஏன் சிறியவை?",
                "The choanocytes cover only the inner surface of a single tube, so the area of choanocytes does not keep pace with the volume of water in the spongocoel as the body grows.", "கோனோசைட்டுகள் ஒற்றைக் குழாயின் உள்பரப்பை மட்டுமே மூடுவதால், உடல் வளரும்போது ஸ்பாஞ்சோசீலிலுள்ள நீரின் கொள்ளளவுக்கு ஏற்ப கோனோசைட் பரப்பு உயராது."),
            bqc("Give an example of a leuconoid sponge.", "லியூக்கோனாய்டு கடற்பஞ்சுக்கு ஓர் எடுத்துக்காட்டு தருக.",
                "Spongilla, and nearly all demosponges.", "ஸ்பாஞ்சில்லா; கிட்டத்தட்ட எல்லா டெமோஸ்பாஞ்சுகளும்."),
        ),
    )
}
