package com.gasczoology.invertebratelab.data

/** Book-style chapter: Fasciola hepatica. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object FasciolaBookChapter {
    val chapter = bchapter(
        lessonId = "u3-fasciola",
        title = "Fasciola hepatica: the liver fluke" to "ஃபாசியோலா ஹெப்பாட்டிகா: கல்லீரல் ஃப்ளூக்",
        objectives = listOf(
            "Describe the habitat, external features and body covering of Fasciola hepatica." to "ஃபாசியோலா ஹெப்பாட்டிகாவின் வாழிடம், புறத்தோற்றம், உடல் உறையை விவரிக்க.",
            "Describe the digestive, excretory, nervous and reproductive systems." to "செரிமான, கழிவு நீக்க, நரம்பு, இனப்பெருக்க மண்டலங்களை விவரிக்க.",
            "Explain how the reproductive organs of a hermaphrodite fluke are arranged." to "இருபால் உயிரி ஃப்ளூக்கின் இனப்பெருக்க உறுப்புகள் எவ்வாறு அமைந்துள்ளன என்பதை விளக்க.",
            "Write the life cycle in the correct sequence, naming each larval stage and host." to "வாழ்க்கைச் சுழற்சியை சரியான வரிசையில், ஒவ்வொரு லார்வா நிலையையும் விருந்தோம்பியையும் பெயரிட்டு எழுத.",
            "Trace the migration of the juvenile fluke in the mammalian host." to "பாலூட்டி விருந்தோம்பியில் இளம் ஃப்ளூக்கின் நகர்வைப் பின்தொடர.",
        ),
        introduction = listOf(
            "Fasciola hepatica is the liver fluke, a trematode that lives in the bile ducts of sheep, cattle and other grazing mammals, and sometimes of people. It causes fascioliasis, or liver rot, which is a serious disease of livestock and is an important cause of loss to farmers. The animal is the standard type study of the class Trematoda, and it is the first in which the student meets a complex parasitic life cycle with several larval stages and an intermediate host." to
                "ஃபாசியோலா ஹெப்பாட்டிகா கல்லீரல் ஃப்ளூக்; ஆடு, மாடு, பிற மேயும் பாலூட்டிகளின், சில நேரங்களில் மனிதர்களின், பித்த நாளங்களில் வாழும் டிரிமட்டோடா. இது கால்நடைகளின் கடுமையான நோயான ஃபாசியோலியாசிஸ் அல்லது கல்லீரல் அழுகலை ஏற்படுத்துகிறது; விவசாயிகளுக்கு முக்கிய இழப்புக் காரணம். இவ்விலங்கு டிரிமட்டோடா வகுப்பின் நிலையான வகை ஆய்வு; பல லார்வா நிலைகளும் இடைநிலை விருந்தோம்பியும் கொண்ட சிக்கலான ஒட்டுண்ணி வாழ்க்கைச் சுழற்சியை மாணவர் முதலில் சந்திப்பது இதில்தான்.",
            "The chapter follows the usual plan: habit and habitat, external features, the body wall, the systems of the body, reproduction, and the life cycle. Three figures are inserted at the places in the text where they are needed, and each is shown once." to
                "அத்தியாயம் வழக்கமான திட்டத்தைப் பின்பற்றுகிறது: வாழ்முறையும் வாழிடமும், புறத்தோற்றம், உடற்சுவர், உடலின் மண்டலங்கள், இனப்பெருக்கம், வாழ்க்கைச் சுழற்சி. மூன்று படங்கள் உரையில் தேவைப்படும் இடங்களில் செருகப்பட்டுள்ளன; ஒவ்வொன்றும் ஒருமுறை காட்டப்படுகிறது.",
        ),
        sections = listOf(
            bsec("habitat", "Habit and habitat", "வாழ்முறையும் வாழிடமும்",
                blocks = listOf(
                    bp("The adult fluke lives in the bile ducts of the liver of its definitive host, which is usually a sheep or a cow. It is found in countries throughout the world wherever sheep and cattle are kept in wet pastures, because the snails on which the life cycle depends live in such places. The fluke feeds on the bile, on blood and on the cells of the lining of the duct, and a heavy infection can block and damage the ducts and the liver." to
                        "முதிர்ந்த ஃப்ளூக் தன் முதன்மை விருந்தோம்பியின், பொதுவாக ஆடு அல்லது மாட்டின், கல்லீரலின் பித்த நாளங்களில் வாழ்கிறது. வாழ்க்கைச் சுழற்சி சார்ந்துள்ள நத்தைகள் வாழும் ஈரமான மேய்ச்சல் நிலங்களில் ஆடு, மாடுகள் வளர்க்கப்படும் உலகின் அனைத்து நாடுகளிலும் காணப்படுகிறது. ஃப்ளூக் பித்தம், இரத்தம், நாளத்தின் உள்ளடுக்கு செல்களை உண்கிறது; கடுமையான தொற்று நாளங்களையும் கல்லீரலையும் அடைத்துச் சேதப்படுத்தும்."),
                    bdef("Definitive host", "முதன்மை விருந்தோம்பி", "The host in which a parasite reaches sexual maturity and reproduces." to
                        "ஒட்டுண்ணி பாலின முதிர்ச்சி அடைந்து இனப்பெருக்கம் செய்யும் விருந்தோம்பி."),
                    bdef("Intermediate host", "இடைநிலை விருந்தோம்பி", "The host in which a parasite passes its larval stages and multiplies asexually; for Fasciola this is a freshwater snail." to
                        "ஒட்டுண்ணி தன் லார்வா நிலைகளைக் கழித்து பாலிலா முறையில் பெருகும் விருந்தோம்பி; ஃபாசியோலாவுக்கு இது நன்னீர் நத்தை."),
                    bdef("Endoparasite", "அகவொட்டுண்ணி", "A parasite that lives inside the body of its host." to
                        "விருந்தோம்பியின் உடலுக்குள் வாழும் ஒட்டுண்ணி."),
                )),
            bsec("external", "External features", "புறத்தோற்றம்",
                blocks = listOf(
                    bp("The adult is a flat, leaf-shaped worm, about two to three centimetres long and one centimetre wide. It is greyish-brown in colour and is pointed at the front into a small conical projection, the oral cone, with the mouth at its tip, surrounded by the oral sucker. A second, larger sucker, the ventral sucker or acetabulum, lies a little behind it on the ventral surface and is the main organ of attachment. Between the two suckers is the genital pore, through which both the male and the female ducts open. The excretory pore is at the posterior end." to
                        "முதிர்ந்த புழு தட்டையான, இலை வடிவ, சுமார் இரண்டு முதல் மூன்று சென்டிமீட்டர் நீளமும் ஒரு சென்டிமீட்டர் அகலமும் உடையது. சாம்பல் கலந்த பழுப்பு நிறம்; முன்னால் வாய் உறிஞ்சியால் சூழப்பட்ட நுனியில் வாயுடன் சிறு கூம்பு நீட்சியான வாய்க் கூம்பாகக் குறுகுகிறது. அதற்குச் சற்றுப் பின்னால் வயிற்றுப் பரப்பில் இரண்டாவது, பெரிய உறிஞ்சியான வயிற்று உறிஞ்சி அல்லது அசிட்டாபுலம் உள்ளது; இது ஒட்டுவதற்கான முதன்மை உறுப்பு. இரு உறிஞ்சிகளுக்கும் இடையே ஆண், பெண் நாளங்கள் இரண்டும் திறக்கும் இனப்பெருக்கத் துளை உள்ளது. கழிவு நீக்கத் துளை பின் முனையில் உள்ளது."),
                    bfig("fasciola/external", "External features of Fasciola hepatica, ventral view, showing the oral cone and oral sucker, the ventral sucker, the genital pore and the posterior excretory pore." to
                        "ஃபாசியோலா ஹெப்பாட்டிகாவின் புறத்தோற்றம் (வயிற்றுப் பார்வை): வாய்க் கூம்பு, வாய் உறிஞ்சி, வயிற்று உறிஞ்சி, இனப்பெருக்கத் துளை, பின்புறக் கழிவு நீக்கத் துளை."),
                    bdef("Oral sucker", "வாய் உறிஞ்சி", "The muscular sucker surrounding the mouth at the anterior end of a fluke." to
                        "ஃப்ளூக்கின் முன் முனையில் வாயைச் சுற்றியுள்ள தசை உறிஞ்சி."),
                    bdef("Acetabulum", "அசிட்டாபுலம்", "The ventral sucker of a fluke, used chiefly for attachment." to
                        "ஃப்ளூக்கின் வயிற்று உறிஞ்சி; முக்கியமாக ஒட்டுவதற்குப் பயன்படுகிறது."),
                )),
            bsec("body-wall", "The body wall", "உடற்சுவர்",
                blocks = listOf(
                    bp("The body is covered by a tegument, which is a living layer that is not shed, and the outer surface of which is armed with small backwardly directed spines. Below it lie layers of circular, longitudinal and diagonal muscle, and the rest of the space is filled with parenchyma, in which the organs are embedded. The tegument protects the fluke against the digestive enzymes and the immune reactions of the host, and takes part in absorption and in the exchange of gases. The spines help the worm to hold its place in the duct." to
                        "உடல் உதிர்க்கப்படாத உயிருள்ள அடுக்கான டெகுமென்ட்டால் மூடப்பட்டுள்ளது; அதன் வெளிப்பரப்பில் பின்னோக்கிய சிறு முட்கள் உள்ளன. அதன் கீழே வட்ட, நீளவாக்கு, மூலைவிட்ட தசை அடுக்குகள் உள்ளன; மீதி இடம் உறுப்புகள் பதிந்துள்ள பாரங்கைமாவால் நிரம்பியுள்ளது. டெகுமென்ட் ஃப்ளூக்கை விருந்தோம்பியின் செரிமான நொதிகள், நோயெதிர்ப்பு வினைகளிலிருந்து பாதுகாத்து, உறிஞ்சலிலும் வாயுப் பரிமாற்றத்திலும் பங்கேற்கிறது. முட்கள் புழு நாளத்தில் தன் இடத்தைப் பற்றிக்கொள்ள உதவுகின்றன."),
                    bdef("Tegument", "டெகுமென்ட்", "The living, non-ciliated outer covering of a fluke, which replaces the epidermis of free-living flatworms." to
                        "தனி வாழ்வுத் தட்டைப்புழுக்களின் எப்பிடெர்மிஸுக்குப் பதிலாக ஃப்ளூக்கின் உயிருள்ள, கசையிழையற்ற வெளி உறை."),
                )),
            bsec("digestive", "Digestive system", "செரிமான மண்டலம்",
                blocks = listOf(
                    bp("The mouth at the tip of the oral cone leads to a small muscular pharynx and a short oesophagus. The oesophagus divides into two main intestinal caeca, one running down each side of the body, and each of these gives off many side branches that end blindly. The gut thus branches through nearly the whole body and carries digested food to all the tissues, in the absence of a circulatory system. There is no anus, and undigested matter is passed out through the mouth." to
                        "வாய்க் கூம்பின் நுனியிலுள்ள வாய், சிறு தசைத் தொண்டை, குறுகிய உணவுக்குழலுக்குச் செல்கிறது. உணவுக்குழல் உடலின் ஒவ்வொரு பக்கமும் செல்லும் இரு முதன்மைக் குடல் கெகாக்களாகப் பிரிகிறது; அவை ஒவ்வொன்றும் குருட்டு முடிவுடைய பல பக்கக் கிளைகளைத் தருகின்றன. எனவே குடல் கிட்டத்தட்ட முழு உடலிலும் கிளைத்து, சுற்றோட்ட மண்டலம் இல்லாததால் செரித்த உணவை எல்லா திசுக்களுக்கும் கொண்டு செல்கிறது. மலவாய் இல்லை; செரியாதவை வாய் வழியாக வெளியேற்றப்படுகின்றன."),
                    bp("Digestion begins in the lumen of the caeca and is completed inside the cells of their lining. The fluke feeds on bile, blood and tissue fluid, and it uses glycogen stored in its body as a reserve. Since the bile duct contains little oxygen, the energy is obtained mainly by anaerobic respiration." to
                        "செரிமானம் கெகாக்களின் குழியில் தொடங்கி அவற்றின் உள்ளடுக்கு செல்களினுள் முடிகிறது. ஃப்ளூக் பித்தம், இரத்தம், திசு நீரை உண்கிறது; உடலில் சேமித்த கிளைக்கோஜனைக் கையிருப்பாகப் பயன்படுத்துகிறது. பித்த நாளத்தில் ஆக்சிஜன் குறைவாக இருப்பதால், ஆற்றல் முக்கியமாக காற்றில்லாச் சுவாசத்தால் பெறப்படுகிறது."),
                    bdef("Caecum (plural: caeca)", "கெகம் (பன்மை: கெகா)", "A blind-ending branch of the gut." to
                        "குடலின் குருட்டு முடிவுடைய கிளை."),
                )),
            bsec("systems", "Excretory, nervous and sensory systems", "கழிவு நீக்க, நரம்பு, உணர் மண்டலங்கள்",
                blocks = listOf(
                    bp("The excretory system is of the protonephridial type. Many flame cells, scattered through the parenchyma, drain by fine capillaries into larger ducts, and these join to form two main excretory canals that run along the sides of the body and open through a common excretory pore at the posterior end. The system serves chiefly for osmoregulation and for the removal of some metabolic wastes." to
                        "கழிவு நீக்க மண்டலம் புரோட்டோநெஃப்ரிடிய வகை. பாரங்கைமா முழுவதும் சிதறிய பல சுடர் செல்கள் நுண் குழாய்கள் வழியாக பெரிய நாளங்களுக்கு வடிகின்றன; அவை இணைந்து உடலின் பக்கங்களில் செல்லும் இரு முதன்மைக் கழிவு நீக்கக் கால்வாய்களாகி, பின் முனையிலுள்ள பொதுக் கழிவு நீக்கத் துளை வழியாகத் திறக்கின்றன. இம்மண்டலம் முக்கியமாக சவ்வூடு பரவல் ஒழுங்குபாட்டுக்கும் சில வளர்சிதை கழிவுகளை அகற்றவும் பயன்படுகிறது."),
                    bp("The nervous system consists of a nerve ring around the oesophagus, with a pair of ganglia, and three pairs of longitudinal nerve cords, dorsal, lateral and ventral, that run backwards and are joined by transverse commissures. The sense organs are poorly developed, as befits a parasite that lives in the dark in a uniform environment. Sensory endings in the suckers and in the tegument respond to touch and to chemicals. The adult has no eyes, though the free-swimming larva has eye spots." to
                        "நரம்பு மண்டலத்தில் உணவுக்குழலைச் சுற்றிய நரம்பு வளையமும் ஒரு இணை நரம்புத்திரள்களும், முதுகுப்புற, பக்க, வயிற்றுப்புற என மூன்று இணை நீளவாக்கு நரம்பு வடங்களும் உள்ளன; அவை பின்னோக்கிச் சென்று குறுக்கு இணைப்புகளால் இணைகின்றன. ஒரே மாதிரியான சூழலில் இருட்டில் வாழும் ஒட்டுண்ணிக்கு ஏற்றபடி உணர் உறுப்புகள் குறைவாக வளர்ந்துள்ளன. உறிஞ்சிகளிலும் டெகுமென்ட்டிலும் உள்ள உணர்முனைகள் தொடுதலுக்கும் வேதிப்பொருட்களுக்கும் துலங்குகின்றன. முதிர்ந்த புழுவுக்குக் கண்கள் இல்லை; நீந்தும் லார்வாவுக்குக் கண்புள்ளிகள் உண்டு."),
                    bnote("No respiratory or circulatory system", "சுவாச, சுற்றோட்ட மண்டலங்கள் இல்லை",
                        "The fluke has neither. Oxygen, where it is used, enters by diffusion across the tegument, and the branched gut distributes the food." to
                            "ஃப்ளூக்கில் இரண்டும் இல்லை. பயன்படுத்தப்படும் இடத்தில் ஆக்சிஜன் டெகுமென்ட் வழியாக விரவி நுழைகிறது; கிளைத்த குடல் உணவைப் பகிர்கிறது."),
                )),
            bsec("reproductive", "Reproductive system", "இனப்பெருக்க மண்டலம்",
                blocks = listOf(
                    bfig("fasciola/digestive-reproductive", "Digestive and reproductive systems of Fasciola hepatica, showing the branched caeca, the testes, the ovary, the vitelline glands, the coiled uterus and the genital pore." to
                        "ஃபாசியோலா ஹெப்பாட்டிகாவின் செரிமான, இனப்பெருக்க மண்டலங்கள்: கிளைத்த கெகா, விந்தகங்கள், சூற்பை, மஞ்சள் கரு சுரப்பிகள், சுருண்ட கருப்பை, இனப்பெருக்கத் துளை."),
                ),
                subs = listOf(
                    bsub("The male system", "ஆண் மண்டலம்",
                        bp("There are two much-branched testes, one behind the other, in the middle of the body. A fine vas efferens leaves each testis, and the two join to form a vas deferens that runs forwards to a seminal vesicle. A narrow ejaculatory duct leads from this into a muscular cirrus, enclosed in a cirrus sac, which can be protruded through the genital pore. The prostate glands open into the base of the cirrus sac." to
                            "உடலின் நடுவில் ஒன்றன்பின் ஒன்றாக மிகவும் கிளைத்த இரு விந்தகங்கள் உள்ளன. ஒவ்வொரு விந்தகத்திலிருந்தும் ஒரு நுண் வாஸ் எஃபரென்ஸ் புறப்பட்டு, இரண்டும் இணைந்து முன்னோக்கிச் செல்லும் வாஸ் டிஃபெரன்ஸ் ஆகி விந்துப்பையை அடைகிறது. அதிலிருந்து குறுகிய வெளியேற்று நாளம், சிர்ரஸ் பையால் சூழப்பட்ட, இனப்பெருக்கத் துளை வழியாக வெளியே நீட்டக்கூடிய தசைச் சிர்ரஸுக்குச் செல்கிறது. புரோஸ்டேட் சுரப்பிகள் சிர்ரஸ் பையின் அடியில் திறக்கின்றன."),
                    ),
                    bsub("The female system", "பெண் மண்டலம்",
                        bp("The single ovary is branched and lies on one side in front of the testes. Its oviduct leads to the ootype, a small chamber surrounded by the unicellular shell glands, known as Mehlis' gland. Into the oviduct also open the duct of the seminal receptacle, the Laurer's canal, and the common vitelline duct, which carries yolk from the two lateral rows of vitelline glands. The uterus is a long, coiled tube that runs from the ootype forwards to the genital pore, and in it the eggs are held as they pass out." to
                            "ஒற்றைச் சூற்பை கிளைத்தது; விந்தகங்களுக்கு முன்பாக ஒரு பக்கத்தில் உள்ளது. அதன் சூல்நாளம் மெலிஸ் சுரப்பி எனப்படும் ஒருசெல் ஓட்டுச் சுரப்பிகளால் சூழப்பட்ட சிறு அறையான ஊடைப்புக்குச் செல்கிறது. சூல்நாளத்தில் விந்து சேமிப்பகத்தின் நாளம், லாரர் கால்வாய், இரு பக்க வரிசை மஞ்சள் கரு சுரப்பிகளிலிருந்து மஞ்சள் கருவைக் கொண்டு செல்லும் பொது மஞ்சள் கரு நாளம் ஆகியவையும் திறக்கின்றன. கருப்பை ஊடைப்பிலிருந்து இனப்பெருக்கத் துளை வரை முன்னோக்கிச் செல்லும் நீண்ட, சுருண்ட குழாய்; முட்டைகள் வெளியேறும்போது அதில் தங்குகின்றன."),
                    ),
                    bsub("Fertilisation and egg formation", "கருவுறுதலும் முட்டை உருவாக்கமும்",
                        bp("Fasciola is a hermaphrodite and may cross-fertilise with another worm, or may fertilise itself. Sperm are stored in the seminal receptacle, and an ovum is fertilised in the oviduct before it reaches the ootype. There it receives yolk cells from the vitelline glands and a shell formed from the secretion of the shell glands. The egg that results is large, yellowish-brown, oval and operculate, which means that it has a small lid at one end. The eggs pass along the uterus to the genital pore and into the bile duct, and from there into the gut of the host." to
                            "ஃபாசியோலா இருபால் உயிரி; மற்றொரு புழுவுடன் குறுக்குக் கருவுறலாம் அல்லது தன்கருவுறலாம். விந்தணுக்கள் விந்து சேமிப்பகத்தில் சேமிக்கப்படுகின்றன; முட்டை ஊடைப்பை அடையும் முன் சூல்நாளத்தில் கருவுறுகிறது. அங்கு மஞ்சள் கரு சுரப்பிகளிலிருந்து மஞ்சள் கரு செல்களையும், ஓட்டுச் சுரப்பிகளின் சுரப்பிலிருந்து உருவான ஓட்டையும் பெறுகிறது. விளைந்த முட்டை பெரிய, மஞ்சள் கலந்த பழுப்பு, முட்டை வடிவம், மூடியுடையது; அதாவது ஒரு முனையில் சிறு மூடி உண்டு. முட்டைகள் கருப்பை வழியாக இனப்பெருக்கத் துளைக்கும் பித்த நாளத்துக்கும், அங்கிருந்து விருந்தோம்பியின் குடலுக்கும் செல்கின்றன."),
                        bdef("Ootype", "ஊடைப்பு", "The chamber in a fluke in which the egg is formed by the union of ovum, yolk and shell material." to
                            "முட்டை, மஞ்சள் கரு, ஓட்டுப் பொருள் இணைந்து முட்டை உருவாகும் ஃப்ளூக்கின் அறை."),
                        bdef("Operculate egg", "மூடியுடைய முட்டை", "An egg with a small lid, the operculum, through which the larva leaves." to
                            "லார்வா வெளியேறும் சிறு மூடியான ஆப்பர்குலம் கொண்ட முட்டை."),
                        bdef("Vitelline gland", "மஞ்சள் கரு சுரப்பி", "A gland that supplies yolk cells and shell material to the eggs of a flatworm." to
                            "தட்டைப்புழுவின் முட்டைகளுக்கு மஞ்சள் கரு செல்களையும் ஓட்டுப் பொருளையும் தரும் சுரப்பி."),
                    ),
                )),
            bsec("life-cycle", "Life cycle", "வாழ்க்கைச் சுழற்சி",
                blocks = listOf(
                    bp("The life cycle of Fasciola is indirect, with two hosts. The sheep or cow is the definitive host, in which the fluke becomes an adult and reproduces sexually, and a freshwater snail is the intermediate host, in which the larvae multiply asexually. The snail is a species of Lymnaea, or a closely related genus, according to the region." to
                        "ஃபாசியோலாவின் வாழ்க்கைச் சுழற்சி இரு விருந்தோம்பிகளுடன் மறைமுகமானது. ஆடு அல்லது மாடு முதன்மை விருந்தோம்பி; அதில் ஃப்ளூக் முதிர்ந்து பாலின இனப்பெருக்கம் செய்கிறது. நன்னீர் நத்தை இடைநிலை விருந்தோம்பி; அதில் லார்வாக்கள் பாலிலா முறையில் பெருகுகின்றன. பகுதிக்கு ஏற்ப நத்தை லிம்னேயா இனம் அல்லது நெருங்கிய பேரினம்."),
                    bpoints("Sequence of the life cycle", "வாழ்க்கைச் சுழற்சியின் வரிசை",
                        "Unembryonated eggs, laid by the adults in the bile ducts, pass with the bile into the intestine and leave the host in the faeces." to "பித்த நாளங்களிலுள்ள முதிர்ந்த புழுக்கள் இடும் கருவளராத முட்டைகள் பித்தத்துடன் குடலுக்குச் சென்று மலத்துடன் வெளியேறுகின்றன.",
                        "In fresh water the egg embryonates over some weeks and releases a ciliated, free-swimming miracidium." to "நன்னீரில் முட்டை சில வாரங்களில் கரு வளர்ந்து, கசையிழை, சுதந்திரமாக நீந்தும் மிராசிடியத்தை வெளியிடுகிறது.",
                        "The miracidium swims to a suitable snail and penetrates it." to "மிராசிடியம் தகுந்த நத்தையிடம் நீந்திச் சென்று உள்ளே ஊடுருவுகிறது.",
                        "In the snail it becomes a sporocyst, which gives rise to rediae." to "நத்தையில் அது ஸ்போரோசிஸ்டாகி, ரெடியாக்களைத் தருகிறது.",
                        "The rediae produce numerous cercariae by asexual multiplication, and sometimes a second generation of rediae." to "ரெடியாக்கள் பாலிலா பெருக்கத்தால் எண்ணற்ற செர்க்கேரியாக்களை, சில நேரங்களில் இரண்டாம் தலைமுறை ரெடியாக்களையும், உருவாக்குகின்றன.",
                        "The tailed cercariae leave the snail, swim briefly, lose their tails and encyst on water plants or other surfaces as metacercariae." to "வால் உள்ள செர்க்கேரியாக்கள் நத்தையை விட்டு வெளியேறி, சிறிது நீந்தி, வாலை இழந்து, நீர்த் தாவரங்கள் அல்லது பிற பரப்புகளில் மெட்டாசெர்க்கேரியாக்களாக உறைகின்றன.",
                        "A grazing mammal swallows the metacercariae with the plants or water, and the cycle is completed." to "மேயும் பாலூட்டி தாவரங்கள் அல்லது நீருடன் மெட்டாசெர்க்கேரியாக்களை விழுங்க, சுழற்சி நிறைவடைகிறது.",
                    ),
                    bfig("fasciola/life-cycle", "The life cycle of Fasciola hepatica, from the egg and miracidium through the stages in the snail, sporocyst, redia and cercaria, to the metacercaria and the adult in the bile duct." to
                        "ஃபாசியோலா ஹெப்பாட்டிகாவின் வாழ்க்கைச் சுழற்சி: முட்டை, மிராசிடியத்திலிருந்து நத்தையிலுள்ள ஸ்போரோசிஸ்ட், ரெடியா, செர்க்கேரியா வழியாக மெட்டாசெர்க்கேரியா, பித்த நாளத்திலுள்ள முதிர்ந்த புழு வரை."),
                    btable("Larval stages of Fasciola", "ஃபாசியோலாவின் லார்வா நிலைகள்",
                        listOf("Stage" to "நிலை", "Where found" to "காணப்படும் இடம்", "Main feature" to "முதன்மைப் பண்பு"),
                        listOf(
                            listOf("Miracidium" to "மிராசிடியம்", "Fresh water" to "நன்னீர்", "Ciliated, free-swimming, with eye spots" to "கசையிழையுடையது, நீந்துவது, கண்புள்ளிகளுடன்"),
                            listOf("Sporocyst" to "ஸ்போரோசிஸ்ட்", "Snail" to "நத்தை", "Sac-like, produces rediae" to "பை போன்றது, ரெடியாக்களை உருவாக்குகிறது"),
                            listOf("Redia" to "ரெடியா", "Snail" to "நத்தை", "Has mouth and gut, produces cercariae" to "வாயும் குடலும் உண்டு, செர்க்கேரியாக்களை உருவாக்குகிறது"),
                            listOf("Cercaria" to "செர்க்கேரியா", "Leaves the snail" to "நத்தையிலிருந்து வெளியேறுகிறது", "Tailed, swims, encysts" to "வால் உடையது, நீந்தும், உறையும்"),
                            listOf("Metacercaria" to "மெட்டாசெர்க்கேரியா", "On plants or other surfaces" to "தாவரங்கள் அல்லது பிற பரப்புகளில்", "Encysted, infective to the mammal" to "உறையிடப்பட்டது, பாலூட்டிக்குத் தொற்றுவது"),
                        )),
                    bdef("Miracidium", "மிராசிடியம்", "The ciliated, free-swimming first larva of a fluke, which hatches from the egg and infects a snail." to
                        "முட்டையிலிருந்து பொரிந்து நத்தையைத் தொற்றும், ஃப்ளூக்கின் கசையிழை, நீந்தும் முதல் லார்வா."),
                    bdef("Sporocyst", "ஸ்போரோசிஸ்ட்", "The saclike second larval stage that develops in the snail and gives rise to rediae." to
                        "நத்தையில் வளர்ந்து ரெடியாக்களைத் தரும் பை போன்ற இரண்டாம் லார்வா நிலை."),
                    bdef("Redia", "ரெடியா", "A larval stage in the snail with a mouth and a simple gut, which produces cercariae." to
                        "வாயும் எளிய குடலும் கொண்ட, செர்க்கேரியாக்களை உருவாக்கும், நத்தையிலுள்ள லார்வா நிலை."),
                    bdef("Cercaria", "செர்க்கேரியா", "The tailed larva that leaves the snail and swims before encysting." to
                        "நத்தையை விட்டு வெளியேறி உறைவதற்கு முன் நீந்தும் வால் உடைய லார்வா."),
                    bdef("Metacercaria", "மெட்டாசெர்க்கேரியா", "The encysted, infective stage of a fluke, which is swallowed by the definitive host." to
                        "முதன்மை விருந்தோம்பியால் விழுங்கப்படும், ஃப்ளூக்கின் உறையிடப்பட்ட, தொற்றும் நிலை."),
                    bnote("Sporocyst and redia", "ஸ்போரோசிஸ்டும் ரெடியாவும்",
                        "The cycle of Fasciola includes both a sporocyst and a redia. It should not be confused with the cycle of some other flukes, and the metacercaria is the stage that infects the mammal, not the cercaria." to
                            "ஃபாசியோலாவின் சுழற்சியில் ஸ்போரோசிஸ்ட், ரெடியா இரண்டும் உள்ளன. சில பிற ஃப்ளூக்குகளின் சுழற்சியுடன் குழப்பக் கூடாது; பாலூட்டியைத் தொற்றும் நிலை மெட்டாசெர்க்கேரியா; செர்க்கேரியா அல்ல."),
                )),
            bsec("migration", "Migration in the mammal", "பாலூட்டியில் நகர்வு",
                blocks = listOf(
                    bp("Infection begins when a mammal swallows metacercariae attached to water plants or in contaminated water. In the duodenum the cysts open, and the young flukes bore through the wall of the intestine into the body cavity. They then make their way to the liver, penetrate its capsule, and travel through the liver tissue, feeding as they go, for some weeks. Finally they enter the bile ducts, where they mature into adults, and the first eggs appear after about three months." to
                        "நீர்த் தாவரங்களில் ஒட்டிய அல்லது மாசடைந்த நீரிலுள்ள மெட்டாசெர்க்கேரியாக்களைப் பாலூட்டி விழுங்கும்போது தொற்று தொடங்குகிறது. முன்சிறுகுடலில் உறைகள் திறக்க, இளம் புழுக்கள் குடல் சுவரைத் துளைத்து உடற்குழிக்குள் செல்கின்றன. பின்னர் கல்லீரலை அடைந்து அதன் உறையை ஊடுருவி, சில வாரங்கள் கல்லீரல் திசு வழியாக உண்டுகொண்டே பயணிக்கின்றன. இறுதியில் பித்த நாளங்களில் நுழைந்து முதிர்ந்த புழுக்களாகின்றன; சுமார் மூன்று மாதங்களுக்குப் பின் முதல் முட்டைகள் தோன்றுகின்றன."),
                    bpoints("Route of the juvenile fluke", "இளம் ஃப்ளூக்கின் பாதை",
                        "Metacercariae are swallowed with plants or water." to "மெட்டாசெர்க்கேரியாக்கள் தாவரங்கள் அல்லது நீருடன் விழுங்கப்படுகின்றன.",
                        "They excyst in the duodenum." to "முன்சிறுகுடலில் உறை திறக்கின்றன.",
                        "Juveniles penetrate the intestinal wall into the peritoneal cavity." to "இளம் புழுக்கள் குடல் சுவரைத் துளைத்து பெரிட்டோனியக் குழிக்குள் செல்கின்றன.",
                        "They penetrate the liver capsule and migrate through the hepatic tissue." to "கல்லீரல் உறையை ஊடுருவி கல்லீரல் திசு வழியாக நகர்கின்றன.",
                        "They enter the bile ducts and mature into adults, which lay eggs." to "பித்த நாளங்களுக்குள் நுழைந்து முதிர்ந்து முட்டையிடும் புழுக்களாகின்றன.",
                    ),
                    bp("The migration through the liver damages the tissue and causes the acute disease, while the adults in the ducts cause the chronic one, with inflammation and thickening of the ducts and anaemia. Control depends upon treatment of the animals, the avoidance of wet pastures and the control of the snail hosts where this is appropriate. People are infected by eating raw water plants such as watercress." to
                        "கல்லீரல் வழி நகர்வு திசுவைச் சேதப்படுத்தி கடுமையான நோயை ஏற்படுத்துகிறது; நாளங்களிலுள்ள முதிர்ந்த புழுக்கள் நாளங்களின் வீக்கம், தடிப்பு, இரத்தச்சோகையுடன் நாட்பட்ட நோயை ஏற்படுத்துகின்றன. கட்டுப்பாடு விலங்குகளுக்குச் சிகிச்சை, ஈர மேய்ச்சல் நிலங்களைத் தவிர்த்தல், பொருத்தமான இடங்களில் நத்தை விருந்தோம்பிகளைக் கட்டுப்படுத்துதல் ஆகியவற்றைச் சார்ந்தது. வாட்டர்கிரெஸ் போன்ற பச்சை நீர்த் தாவரங்களை உண்பதால் மனிதர்கள் தொற்றுக்குள்ளாகின்றனர்."),
                )),
        ),
        summary = listOf(
            "Fasciola hepatica is a leaf-shaped trematode that lives in the bile ducts of sheep, cattle and sometimes people." to "ஃபாசியோலா ஹெப்பாட்டிகா ஆடு, மாடு, சில நேரங்களில் மனிதர்களின் பித்த நாளங்களில் வாழும் இலை வடிவ டிரிமட்டோடா.",
            "It has an oral sucker, a ventral sucker (acetabulum), a spiny tegument, a branched gut with no anus, and flame cells." to "வாய் உறிஞ்சி, வயிற்று உறிஞ்சி (அசிட்டாபுலம்), முள் டெகுமென்ட், மலவாய் இல்லாத கிளைத்த குடல், சுடர் செல்கள் உண்டு.",
            "It is hermaphrodite, with branched testes and ovary, vitelline glands, an ootype with Mehlis' gland, and a coiled uterus." to "இருபால் உயிரி; கிளைத்த விந்தகங்களும் சூற்பையும், மஞ்சள் கரு சுரப்பிகள், மெலிஸ் சுரப்பியுடன் ஊடைப்பு, சுருண்ட கருப்பை.",
            "The cycle is indirect: egg, miracidium, sporocyst, redia, cercaria and metacercaria, with a snail as intermediate host." to "சுழற்சி மறைமுகமானது: முட்டை, மிராசிடியம், ஸ்போரோசிஸ்ட், ரெடியா, செர்க்கேரியா, மெட்டாசெர்க்கேரியா; இடைநிலை விருந்தோம்பி நத்தை.",
            "The metacercaria on water plants or other surfaces is the stage infective to the mammal." to "நீர்த் தாவரங்கள் அல்லது பிற பரப்புகளிலுள்ள மெட்டாசெர்க்கேரியாவே பாலூட்டிக்குத் தொற்றும் நிலை.",
            "The juvenile passes from the duodenum through the intestinal wall and peritoneal cavity, and through the liver tissue to the bile ducts." to "இளம் புழு முன்சிறுகுடலிலிருந்து குடல் சுவர், பெரிட்டோனியக் குழி வழியாக, கல்லீரல் திசு வழியாக பித்த நாளங்களுக்குச் செல்கிறது.",
        ),
        checks = listOf(
            bqc("Name the intermediate host of Fasciola hepatica.", "ஃபாசியோலா ஹெப்பாட்டிகாவின் இடைநிலை விருந்தோம்பியைக் குறிப்பிடுக.",
                "A freshwater snail, such as Lymnaea.", "நன்னீர் நத்தை; லிம்னேயா போன்றது."),
            bqc("Which larva infects the sheep?", "எந்த லார்வா ஆட்டைத் தொற்றுகிறது?",
                "The metacercaria.", "மெட்டாசெர்க்கேரியா."),
            bqc("Why does Fasciola have no anus?", "ஃபாசியோலாவுக்கு ஏன் மலவாய் இல்லை?",
                "Its gut is incomplete and blind-ending; undigested matter leaves by the mouth.", "அதன் குடல் முழுமையற்றது, குருட்டு முடிவுடையது; செரியாதவை வாய் வழியாக வெளியேறுகின்றன."),
            bqc("Where is the egg of Fasciola formed?", "ஃபாசியோலாவின் முட்டை எங்கு உருவாகிறது?",
                "In the ootype, which is surrounded by Mehlis' gland.", "மெலிஸ் சுரப்பியால் சூழப்பட்ட ஊடைப்பில்."),
            bqc("Which larval stages multiply in the snail?", "நத்தையில் எந்த லார்வா நிலைகள் பெருகுகின்றன?",
                "Sporocyst and redia, by asexual multiplication, which produces many cercariae.", "ஸ்போரோசிஸ்ட், ரெடியா; பாலிலா பெருக்கத்தால் பல செர்க்கேரியாக்களை உருவாக்குகின்றன."),
        ),
    )
}
