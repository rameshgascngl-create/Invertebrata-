package com.gasczoology.invertebratelab.data

/** Book-style chapter: metamerism. DRAFT_UNVERIFIED; Tamil needs the author's review. */
object MetamerismBookChapter {
    val chapter = bchapter(
        lessonId = "u4-metamerism",
        title = "Metamerism in Annelida" to "அனெலிடாவில் உடற்கண்டமாதல்",
        objectives = listOf(
            "Define metamerism and describe how it appears in an annelid." to "உடற்கண்டமாதலை வரையறுத்து அனெலிடாவில் அது எவ்வாறு தோன்றுகிறது என விவரிக்க.",
            "List the structures that are repeated in each segment." to "ஒவ்வொரு கண்டத்திலும் மீள்வரும் அமைப்புகளைப் பட்டியலிட.",
            "Explain how and where new segments are added." to "புதிய கண்டங்கள் எவ்வாறு, எங்கு சேர்க்கப்படுகின்றன என விளக்க.",
            "Explain the advantages of metamerism for movement and body organisation." to "இயக்கத்துக்கும் உடல் அமைப்புக்கும் உடற்கண்டமாதலின் நன்மைகளை விளக்க.",
            "Distinguish an annelid metamere from a nematode annulation and a tapeworm proglottid." to "அனெலிடா மெட்டாமியரை நெமட்டோடா வளையத்திலிருந்தும் நாடாப்புழு புரோகுளோட்டிடிலிருந்தும் வேறுபடுத்த.",
        ),
        introduction = listOf(
            "Of all the features of annelids, none is more often discussed or more often misunderstood than segmentation. Books use the words metamerism, segmentation and annulation as if they meant the same, and then a student who has been told that the earthworm is segmented is asked whether a tapeworm is segmented too. The answer depends upon what is being repeated and how, and the purpose of this chapter is to make the definition exact." to
                "அனெலிடாக்களின் பண்புகளில் கண்டமாதலை விட அடிக்கடி விவாதிக்கப்படுவதும் தவறாகப் புரிந்துகொள்ளப்படுவதும் வேறில்லை. நூல்கள் மெட்டாமெரிசம், கண்டமாதல், வளையமாதல் ஆகிய சொற்களை ஒரே பொருளுடையவை போலப் பயன்படுத்துகின்றன; பின்னர் மண்புழு கண்டமுடையது என்று கூறப்பட்ட மாணவரிடம் நாடாப்புழுவும் கண்டமுடையதா என்று கேட்கப்படுகிறது. விடை எது மீள்கிறது, எவ்வாறு மீள்கிறது என்பதைப் பொறுத்தது; வரையறையைத் துல்லியமாக்குவதே இந்த அத்தியாயத்தின் நோக்கம்.",
            "The chapter defines metamerism, lists the repeated parts, describes the growth zone at the posterior end, sets out the advantages of the plan, and ends with the comparison with look-alike structures in other worms that the examination likes to ask for." to
                "அத்தியாயம் உடற்கண்டமாதலை வரையறுக்கிறது; மீள்வரும் பகுதிகளைப் பட்டியலிடுகிறது; பின் முனை வளர்ச்சி மண்டலத்தை விவரிக்கிறது; திட்டத்தின் நன்மைகளை முன்வைக்கிறது; தேர்வு கேட்க விரும்பும் பிற புழுக்களின் ஒத்த தோற்ற அமைப்புகளுடன் ஒப்பீட்டுடன் முடிகிறது.",
        ),
        sections = listOf(
            bsec("definition", "What metamerism is", "உடற்கண்டமாதல் என்றால் என்ன",
                blocks = listOf(
                    bp("Metamerism is the serial repetition of similar body units along the longitudinal axis of the animal. Each unit is a metamere, or segment. In a typical annelid the external division of the body into rings corresponds broadly with an internal division of the coelom into compartments by transverse septa, and the compartments contain repeated sets of organs. The word broadly is used because the correspondence is not perfect: in the earthworm, for example, the septa are absent from the front of the body in some segments, and in the leeches the internal divisions are largely lost." to
                        "உடற்கண்டமாதல் என்பது விலங்கின் நீளவாட்டு அச்சில் ஒத்த உடல் அலகுகளின் தொடர் மீள்வு. ஒவ்வொரு அலகும் மெட்டாமியர் அல்லது கண்டம். வழக்கமான அனெலிடாவில் உடலை வளையங்களாக வெளிப்புறம் பிரிப்பது குறுக்குத் தடுப்புகளால் உடற்குழியை அறைகளாக உள்ளே பிரிப்பதுடன் பெரும்பாலும் ஒத்துப்போகிறது; அறைகளில் மீள்வரும் உறுப்புத் தொகுப்புகள் உள்ளன. பெரும்பாலும் என்ற சொல் பயன்படுகிறது; ஏனெனில் பொருத்தம் முழுமையானதல்ல: எடுத்துக்காட்டாக மண்புழுவில் உடலின் முன் பகுதியில் சில கண்டங்களில் தடுப்புகள் இல்லை; அட்டைகளில் உள் பிரிவுகள் பெரிதும் இழக்கப்பட்டுள்ளன."),
                    bp("A definition for an examination must therefore name the three parts of the idea: the units are similar, they are repeated in series, and the repetition includes the internal organs and not the surface alone. Whenever the repetition is limited to the cuticle or to the skin, the word metamerism is inappropriate, and when it is limited to a group of reproductive organs, it is also inappropriate." to
                        "எனவே தேர்வுக்கான வரையறை கருத்தின் மூன்று பகுதிகளையும் பெயரிட வேண்டும்: அலகுகள் ஒத்தவை; தொடராக மீள்கின்றன; மீள்வு மேற்பரப்பை மட்டுமல்ல, உள் உறுப்புகளையும் உள்ளடக்குகிறது. மீள்வு புறத்தோலுக்கோ தோலுக்கோ மட்டும் வரையறுக்கப்பட்டால் உடற்கண்டமாதல் என்ற சொல் பொருந்தாது; இனப்பெருக்க உறுப்புகளின் தொகுப்புக்கு மட்டும் வரையறுக்கப்பட்டாலும் பொருந்தாது."),
                    bdef("Metamere", "மெட்டாமியர்", "One of the repeated, similar body units of a segmented animal." to
                        "கண்டமுடைய விலங்கின் மீள்வரும் ஒத்த உடல் அலகுகளில் ஒன்று."),
                    bdef("Serial repetition", "தொடர் மீள்வு", "The occurrence of similar parts one after another along the axis of the body." to
                        "உடலின் அச்சில் ஒத்த பகுதிகள் ஒன்றன் பின் ஒன்றாக அமைதல்."),
                    bdef("Homonomous segmentation", "ஒத்த கண்டமாதல்", "Segmentation in which most of the segments are alike, as in the trunk of an earthworm." to
                        "மண்புழுவின் உடற்பகுதி போல, பெரும்பாலான கண்டங்கள் ஒத்தவையாக இருக்கும் கண்டமாதல்."),
                )),
            bsec("repeated-parts", "What is repeated in each segment", "ஒவ்வொரு கண்டத்திலும் மீள்வது என்ன",
                blocks = listOf(
                    bpoints("Structures repeated segmentally in an annelid", "அனெலிடாவில் கண்ட வாரியாக மீள்வரும் அமைப்புகள்",
                        "A compartment of the coelom bounded by septa." to "தடுப்புகளால் எல்லையிடப்பட்ட உடற்குழி அறை.",
                        "A ganglion of the ventral nerve cord, with a set of segmental nerves." to "வயிற்றுப்புற நரம்புவடத்தின் நரம்புத் திரளும் கண்ட நரம்புகளின் தொகுப்பும்.",
                        "A pair of nephridia, in most of the segments." to "பெரும்பாலான கண்டங்களில் ஓர் இணை நெஃப்ரிடியாக்கள்.",
                        "Circular and longitudinal muscle blocks." to "வட்ட, நீளவாட்டுத் தசைத் தொகுதிகள்.",
                        "Segmental blood vessels, linking the dorsal and ventral longitudinal vessels." to "முதுகுப்புற, வயிற்றுப்புற நீளவாட்டு நாளங்களை இணைக்கும் கண்ட இரத்த நாளங்கள்.",
                        "Chaetae, and in polychaetes parapodia, in every trunk segment." to "ஒவ்வொரு உடற்பகுதிக் கண்டத்திலும் சீட்டாக்கள்; பாலிகீட்டாக்களில் பாரபோடியாக்கள்.",
                        "In many forms, gonads or their ducts in some or all of the segments." to "பல வடிவங்களில், சில அல்லது அனைத்து கண்டங்களிலும் இனப்பெருக்க சுரப்பிகள் அல்லது அவற்றின் நாளங்கள்.",
                    ),
                    bp("The list shows that a segment is a workstation with its own supply of nerves, muscle, vessels and excretory organs. This is the sense in which the term is meant when it is applied to the annelids, and it is the reason for thinking that the pattern is of a deep kind, in which the body is built from repeating modules. Not all of the organs are repeated. The gut passes through all the segments as a single tube, and the dorsal blood vessel is a single longitudinal channel. The reproductive system of the earthworm is restricted to a few segments." to
                        "பட்டியல் கண்டம் தன் நரம்பு, தசை, நாளங்கள், கழிவு உறுப்புகளின் விநியோகத்துடன் கூடிய பணிநிலையம் என்பதைக் காட்டுகிறது. அனெலிடாக்களுக்குப் பயன்படுத்தும்போது சொல் இப்பொருளில் கொள்ளப்படுகிறது; உடல் மீள்வரும் தொகுதிகளால் கட்டப்பட்ட ஆழமான வகை அமைப்பு என்று நினைக்க இதுவே காரணம். எல்லா உறுப்புகளும் மீள்வதில்லை. குடல் ஒற்றைக் குழாயாக எல்லாக் கண்டங்கள் வழியாகவும் செல்கிறது; முதுகுப்புற இரத்த நாளம் ஒரே நீளவாட்டுப் பாதை. மண்புழுவின் இனப்பெருக்க மண்டலம் சில கண்டங்களுக்கு மட்டுமே வரையறுக்கப்பட்டது."),
                    bdef("Ganglion", "நரம்புத் திரள்", "A swelling of the nervous system that contains the cell bodies of nerve cells." to
                        "நரம்பு செல்களின் செல் உடல்களைக் கொண்ட நரம்பு மண்டலத்தின் புடைப்பு."),
                    bdef("Nephridium", "நெஃப்ரிடியம்", "A tubular excretory organ of annelids and some other invertebrates." to
                        "அனெலிடாக்கள், வேறு சில முதுகெலும்பிலிகளின் குழல் வடிவ கழிவு உறுப்பு."),
                )),
            bsec("growth", "How segments are formed and where", "கண்டங்கள் எவ்வாறு, எங்கு உருவாகின்றன",
                blocks = listOf(
                    bp("The segments of an annelid are not all formed at the same time. In the larva, and in the young worm, a zone of growth lies just in front of the terminal pygidium, and new segments are cut off from it in turn. The oldest segments are therefore those at the front of the trunk, and the youngest those next to the pygidium. The head and its parts, the prostomium and the peristomium, are not formed from this zone, and the pygidium is not itself a segment. This is the answer to the question where new segments are added: in front of the pygidium." to
                        "அனெலிடாவின் கண்டங்கள் அனைத்தும் ஒரே நேரத்தில் உருவாவதில்லை. லார்வாவிலும் இளம் புழுவிலும் முனை பிஜிடியத்துக்கு சற்று முன்னால் ஒரு வளர்ச்சி மண்டலம் உள்ளது; புதிய கண்டங்கள் அதிலிருந்து முறையாகப் பிரிக்கப்படுகின்றன. எனவே மிகப் பழைய கண்டங்கள் உடற்பகுதியின் முன்புறத்தில் உள்ளவை; இளையவை பிஜிடியத்தை அடுத்தவை. தலையும் அதன் பகுதிகளான புரோஸ்டோமியம், பெரிஸ்டோமியமும் இம்மண்டலத்திலிருந்து உருவாவதில்லை; பிஜிடியம் தானே ஒரு கண்டமல்ல. புதிய கண்டங்கள் எங்கு சேர்க்கப்படுகின்றன என்ற கேள்விக்கு இதுவே விடை: பிஜிடியத்துக்கு முன்னால்."),
                    bp("The growth zone explains why the regions of the body of an annelid differ in age, and why the later segments are sometimes smaller than the earlier. It also explains why a worm which loses its tail can often form a new one, since the zone, or a tissue similar to it, is capable of producing more segments. The capacity for regeneration differs greatly, and in many earthworms the loss of the posterior part can be repaired, but the loss of the anterior part is repaired in few." to
                        "வளர்ச்சி மண்டலம் அனெலிடாவின் உடல் பகுதிகள் வயதில் ஏன் வேறுபடுகின்றன, பிந்தைய கண்டங்கள் சில சமயம் முந்தையதை விடச் சிறியவையாக ஏன் உள்ளன என்பதை விளக்குகிறது. வாலை இழக்கும் புழு பெரும்பாலும் புதியதை உருவாக்க முடிவதையும் விளக்குகிறது; ஏனெனில் மண்டலம் அல்லது அதற்கு ஒத்த திசு மேலும் கண்டங்களை உருவாக்கும் திறன் உடையது. மீளுருவாக்கத் திறன் பெரிதும் மாறுபடுகிறது; பல மண்புழுக்களில் பின் பகுதி இழப்பு சீர் செய்யப்படலாம்; ஆனால் முன் பகுதி இழப்பு சில வற்றில் மட்டுமே சீர் செய்யப்படுகிறது."),
                    bdef("Growth zone", "வளர்ச்சி மண்டலம்", "The region just in front of the pygidium from which new segments are formed." to
                        "புதிய கண்டங்கள் உருவாகும் பிஜிடியத்துக்கு சற்று முன்னுள்ள பகுதி."),
                    bdef("Tagmatisation", "டேக்மாதல்", "The grouping of segments into regions of the body, each with its own function." to
                        "கண்டங்கள் ஒவ்வொன்றும் தன் செயலுடன் உடலின் பகுதிகளாகத் தொகுக்கப்படுதல்."),
                )),
            bsec("advantages", "Advantages of metamerism", "உடற்கண்டமாதலின் நன்மைகள்",
                blocks = listOf(
                    bp("The first advantage concerns movement. Because the coelom is divided by septa, the fluid in each compartment can be put under pressure by itself, and so the muscles of a segment have an incompressible skeleton of their own to work against. The circular and longitudinal muscles contract in waves that pass along the body, and so the worm can lengthen and thin one part of its body while it shortens and thickens another. This is the basis of crawling and burrowing by peristalsis, and it is much more efficient than the movement of an unsegmented, undivided body cavity, in which a contraction in one place raises the pressure everywhere." to
                        "முதல் நன்மை இயக்கம் தொடர்பானது. உடற்குழி தடுப்புகளால் பிரிக்கப்பட்டிருப்பதால் ஒவ்வொரு அறையிலுள்ள திரவமும் தனியாக அழுத்தத்தில் வைக்கப்படலாம்; எனவே கண்டத்தின் தசைகளுக்கு எதிராகச் செயல்பட தம் சொந்த அழுத்த முடியாத எலும்புக்கூடு உண்டு. வட்ட, நீளவாட்டுத் தசைகள் உடலில் கடக்கும் அலைகளாகச் சுருங்குகின்றன; எனவே புழு உடலின் ஒரு பகுதியை நீட்டி மெலிதாக்கும்போது மற்றொன்றை குட்டையாக்கித் தடிக்கச் செய்யலாம். இதுவே அலைச் சுருக்கத்தால் ஊர்தல், துளையிடலின் அடிப்படை; ஓரிடச் சுருக்கம் எல்லா இடத்திலும் அழுத்தத்தை உயர்த்தும் பிரிக்கப்படாத, பிரிவில்லா உடற்குழியின் இயக்கத்தை விட இது மிகத் திறன் வாய்ந்தது."),
                    bpoints("Advantages of the segmented plan", "கண்டமுடைய திட்டத்தின் நன்மைகள்",
                        "Local hydrostatic pressure and wave-like muscle contraction improve the control of movement." to "உள்ளூர் நீரழுத்தமும் அலை போன்ற தசைச் சுருக்கமும் இயக்கக் கட்டுப்பாட்டை மேம்படுத்துகின்றன.",
                        "Regions of the body can be specialised, as in the head, the clitellum and the reproductive segments, without every segment becoming identical." to "தலை, கிளைடெல்லம், இனப்பெருக்கக் கண்டங்கள் போல, ஒவ்வொரு கண்டமும் ஒரே மாதிரி ஆகாமல் உடலின் பகுதிகள் சிறப்புறலாம்.",
                        "A local injury or loss of a few segments need not destroy the function of the rest, as each segment has its own supply of nerves and vessels." to "ஒவ்வொரு கண்டத்துக்கும் தன் நரம்பு, நாள விநியோகம் இருப்பதால் உள்ளூர் காயம் அல்லது சில கண்டங்களின் இழப்பு மற்றவற்றின் செயலை அழிக்க வேண்டியதில்லை.",
                        "The body can be lengthened by the addition of segments, so growth is simple to arrange." to "கண்டங்களைச் சேர்ப்பதால் உடலை நீட்டலாம்; எனவே வளர்ச்சியை ஏற்பாடு செய்வது எளிது.",
                    ),
                    bp("It should not be said that all segments are identical or that every segment can live on its own, since the repetition is only partial. The segments are specialised in the anterior region, and their functions are integrated by the nervous system and the circulation. The statement that is justified is that the arrangement allows regional specialisation, local control of movement and some tolerance of damage, with no need for all the segments to be the same." to
                        "மீள்வு பகுதியளவே என்பதால் அனைத்துக் கண்டங்களும் ஒத்தவை அல்லது ஒவ்வொரு கண்டமும் தனியாக வாழ முடியும் என்று கூறக்கூடாது. முன்புறப் பகுதியில் கண்டங்கள் சிறப்புற்றவை; அவற்றின் செயல்கள் நரம்பு மண்டலத்தாலும் இரத்த ஓட்டத்தாலும் ஒருங்கிணைக்கப்படுகின்றன. நியாயமான கூற்று: அனைத்துக் கண்டங்களும் ஒரே மாதிரி இருக்கத் தேவையின்றி, பகுதி வாரிச் சிறப்பு, இயக்கத்தின் உள்ளூர்க் கட்டுப்பாடு, சிறிதளவு சேதத் தாங்குதிறன் ஆகியவற்றை அமைப்பு அனுமதிக்கிறது."),
                    bdef("Peristalsis", "அலைச் சுருக்கம்", "A wave of muscular contraction passing along a tube or a body, used here for movement by an annelid." to
                        "ஒரு குழாய் அல்லது உடலில் கடக்கும் தசைச் சுருக்க அலை; இங்கு அனெலிடாவின் இயக்கத்துக்குப் பயன்படுகிறது."),
                )),
            bsec("variation", "Variation in the pattern: earthworm, polychaete and leech", "அமைப்பின் மாறுபாடு: மண்புழு, பாலிகீட்டா, அட்டை",
                blocks = listOf(
                    bp("The annelid pattern is expressed in different degrees in the three classes. In polychaetes such as Nereis the segmentation is very clear: the septa are thin, the trunk segments are much alike, each has a pair of parapodia, and the repetition of ganglia and nephridia can be seen with ease. The head is formed from the prostomium and peristomium, with the specialisation of tentacles and palps, and in some other polychaetes a number of anterior segments are fused into a head with a distinct pattern of appendages. The segments are thus alike in the trunk and unlike in the head, a state called heteronomous segmentation when it is marked." to
                        "அனெலிடா அமைப்பு மூன்று வகுப்புகளில் வெவ்வேறு அளவுகளில் வெளிப்படுகிறது. நெரிஸ் போன்ற பாலிகீட்டாக்களில் கண்டமாதல் மிகத் தெளிவு: தடுப்புகள் மெல்லியவை; உடற்பகுதிக் கண்டங்கள் பெரிதும் ஒத்தவை; ஒவ்வொன்றுக்கும் ஓர் இணை பாரபோடியாக்கள் உண்டு; நரம்புத் திரள்கள், நெஃப்ரிடியாக்களின் மீள்வு எளிதில் தெரியும். தலை உணர்கொம்புகள், பால்ப்களின் சிறப்புடன் புரோஸ்டோமியம், பெரிஸ்டோமியத்தால் உருவாகிறது; வேறு சில பாலிகீட்டாக்களில் பல முன்புறக் கண்டங்கள் தனி இணைப்பு அமைப்புடைய தலையாக இணைகின்றன. எனவே கண்டங்கள் உடற்பகுதியில் ஒத்தும் தலையில் வேறுபட்டும் உள்ளன; இது அழுத்தமாக இருக்கும்போது ஹெட்டிரோநாமஸ் கண்டமாதல் எனப்படுகிறது."),
                    bp("In the earthworm the external rings are clear, and the internal septa are well developed in most of the body. A few of the front segments are modified. The first has the mouth, a few bear the male and female openings, and a group of segments in the front half bears the clitellum, the glandular band which makes the cocoon. The reproductive organs lie in particular segments and are not repeated all along the trunk, a case of partial repetition. The nephridia, by contrast, are present in nearly every segment, and are of several kinds." to
                        "மண்புழுவில் வெளிப்புற வளையங்கள் தெளிவானவை; பெரும்பாலான உடலில் உள் தடுப்புகள் நன்கு வளர்ந்துள்ளன. முன்புறக் கண்டங்கள் சிலவற்றில் மாற்றம் உள்ளது. முதலாவதில் வாய் உள்ளது; சிலவற்றில் ஆண், பெண் துளைகள்; முன் பாதியில் ஒரு தொகுதி கண்டங்கள் கூட்டைச் செய்யும் சுரப்பிப் பட்டையான கிளைடெல்லத்தைத் தாங்குகின்றன. இனப்பெருக்க உறுப்புகள் குறிப்பிட்ட கண்டங்களில் உள்ளன; உடற்பகுதி முழுவதும் மீள்வதில்லை; இது பகுதி மீள்வுக்கு எடுத்துக்காட்டு. இதற்கு மாறாக நெஃப்ரிடியாக்கள் கிட்டத்தட்ட ஒவ்வொரு கண்டத்திலும் உள்ளன; பல வகையின."),
                    bp("The leeches show the pattern in a reduced and altered form. A leech has a fixed number of segments, and the external rings are subdivided so that each segment carries several annuli on the surface, which means that the number of rings is greater than the number of true segments. The septa are lost and the coelom is reduced to a system of channels by the growth of connective tissue, and the chaetae are lost. The nervous system and the nephridia still show the segmental pattern. The leech thus shows both the principle of the plan and the way in which it may be altered by the way of life." to
                        "அட்டைகள் அமைப்பைக் குறைக்கப்பட்ட, மாற்றப்பட்ட வடிவில் காட்டுகின்றன. அட்டைக்கு நிலையான கண்ட எண்ணிக்கை உண்டு; வெளிப்புற வளையங்கள் உட்பிரிக்கப்பட்டுள்ளதால் ஒவ்வொரு கண்டமும் மேற்பரப்பில் பல வளையங்களைத் தாங்குகிறது; அதாவது வளையங்களின் எண்ணிக்கை உண்மைக் கண்டங்களை விட அதிகம். இணைப்புத் திசு வளர்ச்சியால் தடுப்புகள் இழக்கப்பட்டு உடற்குழி கால்வாய்களின் அமைப்பாகக் குறைக்கப்பட்டுள்ளது; சீட்டாக்கள் இழக்கப்பட்டுள்ளன. நரம்பு மண்டலமும் நெஃப்ரிடியாக்களும் இன்னும் கண்ட அமைப்பைக் காட்டுகின்றன. எனவே அட்டை திட்டத்தின் கொள்கையையும் வாழ்முறையால் அது எவ்வாறு மாற்றப்படலாம் என்பதையும் காட்டுகிறது."),
                    bdef("Heteronomous segmentation", "மாறுபட்ட கண்டமாதல்", "Segmentation in which different segments or groups of segments are specialised for different functions." to
                        "வெவ்வேறு கண்டங்கள் அல்லது கண்டத் தொகுதிகள் வெவ்வேறு செயல்களுக்குச் சிறப்புறும் கண்டமாதல்."),
                    bdef("Annulus", "வளையம் (அன்னுலஸ்)", "A superficial ring of the skin; in a leech, several annuli make up one true segment." to
                        "தோலின் மேலோட்டமான வளையம்; அட்டையில் பல வளையங்கள் ஒரு உண்மைக் கண்டத்தை உருவாக்குகின்றன."),
                )),
            bsec("comparison", "Metamere, annulation and proglottid compared", "மெட்டாமியர், வளையம், புரோகுளோட்டிட் ஒப்பீடு",
                blocks = listOf(
                    bp("Three structures resemble one another at a glance: the segments of an annelid, the transverse rings on the surface of some nematodes, and the proglottids of a tapeworm. All three give a repeated appearance to the outside of a long worm, but their internal organisation and their origin differ, and they should not be used interchangeably in an answer or as evidence that the worms are closely related." to
                        "மூன்று அமைப்புகள் பார்வையில் ஒன்றையொன்று ஒத்துள்ளன: அனெலிடாவின் கண்டங்கள், சில நெமட்டோடாக்களின் மேற்பரப்பிலுள்ள குறுக்கு வளையங்கள், நாடாப்புழுவின் புரோகுளோட்டிட்கள். மூன்றும் நீண்ட புழுவின் வெளிப்புறத்துக்கு மீள்வரும் தோற்றம் தருகின்றன; ஆனால் அவற்றின் உள் அமைப்பும் தோற்றமும் வேறுபடுகின்றன; விடையில் ஒன்றுக்குப் பதில் மற்றொன்றைப் பயன்படுத்தவோ புழுக்கள் நெருங்கிய தொடர்புடையவை என்பதற்குச் சான்றாகக் கொள்ளவோ கூடாது."),
                    btable("Three repeated structures compared", "மீள்வரும் மூன்று அமைப்புகளின் ஒப்பீடு",
                        listOf("Feature" to "பண்பு", "Annelid metamere" to "அனெலிடா மெட்டாமியர்", "Nematode annulation" to "நெமட்டோடா வளையம்", "Tapeworm proglottid" to "நாடாப்புழு புரோகுளோட்டிட்"),
                        listOf(
                            listOf("What it is" to "அது என்ன", "A true body segment" to "உண்மையான உடற்கண்டம்", "A cuticular ring on the surface" to "மேற்பரப்பில் புறத்தோல் வளையம்", "A serial reproductive unit" to "தொடர் இனப்பெருக்க அலகு"),
                            listOf("Coelomic partitions" to "உடற்குழித் தடுப்புகள்", "Present (septa)" to "உண்டு (தடுப்புகள்)", "Absent" to "இல்லை", "Absent; there is no coelom" to "இல்லை; உடற்குழி இல்லை"),
                            listOf("Repeated organs" to "மீள்வரும் உறுப்புகள்", "Ganglia, nephridia, muscles, vessels" to "நரம்புத் திரள்கள், நெஃப்ரிடியாக்கள், தசைகள், நாளங்கள்", "None" to "இல்லை", "Reproductive organs, mainly" to "முக்கியமாக இனப்பெருக்க உறுப்புகள்"),
                            listOf("Origin" to "தோற்றம்", "From a growth zone in front of the pygidium" to "பிஜிடியத்துக்கு முன்னுள்ள வளர்ச்சி மண்டலத்திலிருந்து", "Folding of the cuticle" to "புறத்தோல் மடிப்பு", "Budded from the neck, behind the scolex" to "ஸ்கோலெக்ஸுக்குப் பின்னால் கழுத்திலிருந்து அரும்பியது"),
                        )),
                    bp("The tapeworm result needs a particular comment. A proglottid is produced by the neck region, and as the proglottids move backwards they ripen, until the oldest contain eggs. They are a series of reproductive packets. They have no septate coelomic compartment, no ganglion and no excretory unit of their own in the sense of an annelid segment. The condition is called strobilation, and in most accounts it is not regarded as true segmentation of the annelid kind." to
                        "நாடாப்புழு முடிவுக்கு தனிக் குறிப்பு தேவை. புரோகுளோட்டிட் கழுத்துப் பகுதியால் உருவாக்கப்படுகிறது; புரோகுளோட்டிட்கள் பின்னோக்கி நகரும்போது முதிர்ந்து, மிகப் பழையவை முட்டைகளைக் கொள்ளும் வரை செல்கின்றன. அவை இனப்பெருக்கப் பொட்டலங்களின் தொடர். தடுப்புடைய உடற்குழி அறை, நரம்புத் திரள், அனெலிடா கண்டத்தின் பொருளில் தம் சொந்த கழிவு அலகு ஆகியவை அவற்றுக்கு இல்லை. இந்நிலை ஸ்ட்ரோபிலேஷன் எனப்படுகிறது; பெரும்பாலான விவரிப்புகளில் அனெலிடா வகை உண்மையான கண்டமாதலாகக் கருதப்படுவதில்லை."),
                    bdef("Annulation", "வளையமாதல்", "The appearance of rings on the surface of an animal that are not matched by repeated internal organs." to
                        "உள் உறுப்புகள் மீள்வதுடன் பொருந்தாத, விலங்கின் மேற்பரப்பில் வளையங்கள் தோன்றுதல்."),
                    bdef("Proglottid", "புரோகுளோட்டிட்", "One of the reproductive units in the chain that makes up the body of a tapeworm." to
                        "நாடாப்புழுவின் உடலை உருவாக்கும் சங்கிலியிலுள்ள இனப்பெருக்க அலகுகளில் ஒன்று."),
                    bdef("Strobilation", "ஸ்ட்ரோபிலேஷன்", "The production of a chain of proglottids by budding from the neck of a tapeworm." to
                        "நாடாப்புழுவின் கழுத்திலிருந்து அரும்பி புரோகுளோட்டிட் சங்கிலி உருவாதல்."),
                    bnote("Do not argue from resemblance", "ஒற்றுமையிலிருந்து வாதிடாதீர்கள்",
                        "A repeated appearance does not show common ancestry. The test is internal organisation and development, and the terms metamere, annulation and proglottid belong to different structures." to
                            "மீள்வரும் தோற்றம் பொது மூதாதையைக் காட்டாது. சோதனை உள் அமைப்பும் வளர்ச்சியும்; மெட்டாமியர், வளையம், புரோகுளோட்டிட் ஆகியவை வெவ்வேறு அமைப்புகளுக்குரியவை."),
                )),
        ),
        summary = listOf(
            "Metamerism is the serial repetition of similar body units along the axis of the body." to "உடற்கண்டமாதல் என்பது உடலின் அச்சில் ஒத்த உடல் அலகுகளின் தொடர் மீள்வு.",
            "In annelids, external rings correspond broadly with coelomic compartments separated by septa." to "அனெலிடாக்களில் வெளிப்புற வளையங்கள் தடுப்புகளால் பிரிக்கப்பட்ட உடற்குழி அறைகளுடன் பெரும்பாலும் ஒத்துப்போகின்றன.",
            "Ganglia, nephridia, muscles and vessels are repeated, while the gut and the dorsal vessel are single." to "நரம்புத் திரள்கள், நெஃப்ரிடியாக்கள், தசைகள், நாளங்கள் மீள்கின்றன; குடலும் முதுகுப்புற நாளமும் ஒற்றை.",
            "New segments are added in front of the pygidium from a growth zone." to "புதிய கண்டங்கள் வளர்ச்சி மண்டலத்திலிருந்து பிஜிடியத்துக்கு முன்னால் சேர்க்கப்படுகின்றன.",
            "Metamerism allows local hydrostatic control, wave-like movement, regional specialisation and some tolerance of damage." to "உடற்கண்டமாதல் உள்ளூர் நீரழுத்தக் கட்டுப்பாடு, அலை போன்ற இயக்கம், பகுதி வாரிச் சிறப்பு, சிறிதளவு சேதத் தாங்குதிறனை அனுமதிக்கிறது.",
            "A nematode annulation and a tapeworm proglottid are not metameres." to "நெமட்டோடா வளையமும் நாடாப்புழு புரோகுளோட்டிடும் மெட்டாமியர்கள் அல்ல.",
        ),
        checks = listOf(
            bqc("Where are new segments added in an annelid?", "அனெலிடாவில் புதிய கண்டங்கள் எங்கு சேர்க்கப்படுகின்றன?",
                "In front of the pygidium.", "பிஜிடியத்துக்கு முன்னால்."),
            bqc("Why is a cestode proglottid not an example of metamerism?", "நாடாப்புழு புரோகுளோட்டிட் ஏன் உடற்கண்டமாதலுக்கு எடுத்துக்காட்டு அல்ல?",
                "It is a reproductive packet budded from the neck, with no septate coelomic compartment or repeated nerves and nephridia.", "இது கழுத்திலிருந்து அரும்பிய இனப்பெருக்கப் பொட்டலம்; தடுப்புடைய உடற்குழி அறையோ மீள்வரும் நரம்புகளோ நெஃப்ரிடியாக்களோ இல்லை."),
            bqc("Name three structures repeated in each annelid segment.", "ஒவ்வொரு அனெலிடா கண்டத்திலும் மீள்வரும் மூன்று அமைப்புகளைப் பெயரிடுக.",
                "Any three of: a coelomic compartment, a ganglion, a pair of nephridia, muscles, segmental vessels.", "உடற்குழி அறை, நரம்புத் திரள், இணை நெஃப்ரிடியாக்கள், தசைகள், கண்ட நாளங்கள் ஆகியவற்றில் ஏதேனும் மூன்று."),
            bqc("Are the rings on a nematode a sign of metamerism?", "நெமட்டோடாவின் வளையங்கள் உடற்கண்டமாதலின் அடையாளமா?",
                "No; they are superficial cuticular annulations without repeated internal organs.", "இல்லை; மீள்வரும் உள் உறுப்புகள் இல்லாத மேலோட்டமான புறத்தோல் வளையங்கள்."),
        ),
    )
}
