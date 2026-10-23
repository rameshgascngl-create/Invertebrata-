package com.gasczoology.invertebratelab.data

/** Book-style chapter: invertebrate fossils (enrichment). DRAFT_UNVERIFIED; Tamil needs the author's review. */
object FossilsBookChapter {
    val chapter = bchapter(
        lessonId = "u5-fossils",
        title = "Invertebrate Fossils" to "முதுகெலும்பிலி புதைபடிவங்கள்",
        objectives = listOf(
            "Explain why hard parts are over-represented in the fossil record." to "புதைபடிவப் பதிவில் கடினப் பகுதிகள் ஏன் மிகையாகப் பிரதிநிதித்துவம் பெறுகின்றன என விளக்க.",
            "State what makes a good index fossil." to "நல்ல வழிகாட்டிப் புதைபடிவத்தை உருவாக்குவது எது எனக் கூற.",
            "Write notes on trilobites, ammonites and crinoids." to "டிரைலோபைட்டுகள், அம்மோனைட்டுகள், கிரினாய்டுகள் பற்றி குறிப்புகள் எழுத.",
            "Explain the fossil record of unicellular organisms and its exceptions." to "ஒருசெல் உயிரிகளின் புதைபடிவப் பதிவையும் அதன் விதிவிலக்குகளையும் விளக்க.",
            "Distinguish fossil groups from living types studied in the laboratory." to "புதைபடிவக் குழுக்களை ஆய்வகத்தில் படிக்கும் வாழும் வகைகளிலிருந்து வேறுபடுத்த.",
        ),
        introduction = listOf(
            "A fossil is the preserved remains or traces of an organism of the geological past, and the study of fossils is the study of the history of life. For the student of the invertebrates the fossil record is important in two ways. It provides direct evidence of groups that no longer exist, such as the trilobites and the ammonites, and it gives a time scale for rocks, in which certain fossils are used as markers. It is also a record with a strong bias, and a good answer must say what the bias is and why it arises." to
                "புதைபடிவம் என்பது புவியியல் கடந்த காலத்தின் உயிரியின் பாதுகாக்கப்பட்ட எச்சங்கள் அல்லது தடயங்கள்; புதைபடிவங்களின் ஆய்வு உயிர் வாழ்க்கையின் வரலாற்று ஆய்வு. முதுகெலும்பிலிகளைப் படிக்கும் மாணவருக்கு புதைபடிவப் பதிவு இரு வழிகளில் முக்கியம்: டிரைலோபைட்டுகள், அம்மோனைட்டுகள் போல இனி இல்லாத குழுக்களுக்கு நேரடிச் சான்றைத் தருகிறது; சில புதைபடிவங்கள் குறியீடுகளாகப் பயன்படும் பாறைகளுக்கு காலஅளவையும் தருகிறது. வலுவான சார்பு கொண்ட பதிவும் கூட; நல்ல விடை அச்சார்பு என்ன, ஏன் எழுகிறது எனக் கூற வேண்டும்.",
            "The topic is classed as enrichment. It asks the student to apply what has been learned about the invertebrate groups to the question of what is preserved and what is not, and to avoid a common confusion: the living animals that are studied in the laboratory, such as Pila and Asterias, are not fossils, and the extinct groups described here are not available as living types." to
                "தலைப்பு செறிவூட்டலாக வகைப்படுத்தப்படுகிறது. முதுகெலும்பிலிக் குழுக்கள் பற்றிக் கற்றதை, எது பாதுகாக்கப்படுகிறது எது இல்லை என்ற கேள்விக்குப் பயன்படுத்தவும் பொதுவான குழப்பத்தைத் தவிர்க்கவும் மாணவரைக் கேட்கிறது: ஆய்வகத்தில் படிக்கும் பைலா, அஸ்டீரியாஸ் போன்ற வாழும் விலங்குகள் புதைபடிவங்கள் அல்ல; இங்கு விவரிக்கப்படும் அழிந்த குழுக்கள் வாழும் வகைகளாகக் கிடைப்பதில்லை.",
        ),
        sections = listOf(
            bsec("preservation", "Fossilisation and preservation bias", "புதைபடிவமாதலும் பாதுகாப்புச் சார்பும்",
                blocks = listOf(
                    bp("For an organism to become a fossil, its remains must be buried quickly, before scavengers and decay destroy them, and must be preserved through the changes that accompany burial and the formation of rock. The study of what happens to an organism from death to the fossil is called taphonomy. Different processes preserve different things: the original material may remain, as in shells that keep their mineral; the remains may be replaced by a mineral, or dissolved to leave a mould, which may be filled to make a cast; and soft animals may leave traces, such as burrows and tracks." to
                        "உயிரி புதைபடிவமாக, அதன் எச்சங்கள் துப்புரவாளர்களும் அழுகலும் அழிக்கும் முன் விரைவாகப் புதைக்கப்பட வேண்டும்; புதைப்புடனும் பாறை உருவாக்கத்துடனும் வரும் மாற்றங்கள் வழியே பாதுகாக்கப்பட வேண்டும். இறப்பிலிருந்து புதைபடிவம் வரை உயிருக்கு நிகழ்வதன் ஆய்வு டாஃபோனமி எனப்படுகிறது. வெவ்வேறு செயல்முறைகள் வெவ்வேறு பொருட்களைப் பாதுகாக்கின்றன: தங்கள் கனிமத்தைத் தக்கவைக்கும் ஓடுகள் போல முதல் பொருள் எஞ்சலாம்; எச்சங்கள் கனிமத்தால் மாற்றப்படலாம் அல்லது கரைந்து அச்சை விடலாம்; அது நிரப்பப்பட்டு வார்ப்பாகலாம்; மென்விலங்குகள் வளைகள், பாதச்சுவடுகள் போன்ற தடயங்களை விடலாம்."),
                    bp("The consequence is preservation bias. Mineralised hard parts, such as shells, skeletons and exoskeletons, survive burial far more readily than the soft tissues, and so groups with such parts are over-represented, while soft-bodied groups are under-represented. An animal that lives in a place where burial is rapid, such as a sea floor in which sediment accumulates, is more likely to be preserved than one that lives on a rocky shore or in a forest. The fossil record is therefore a biased sample, and the absence of fossils of a group is not evidence that it was rare or absent." to
                        "விளைவு பாதுகாப்புச் சார்பு. ஓடுகள், கூடுகள், புறச்சட்டகங்கள் போன்ற கனிமமாக்கப்பட்ட கடினப் பகுதிகள் மென்திசுக்களை விடப் புதைப்பில் மிகவும் எளிதாகத் தப்புகின்றன; எனவே அத்தகைய பகுதிகளுடைய குழுக்கள் மிகையாகவும் மென்உடல் குழுக்கள் குறைவாகவும் பிரதிநிதித்துவம் பெறுகின்றன. படிவு சேரும் கடலடி போல விரைவாகப் புதைக்கப்படும் இடத்தில் வாழும் விலங்கு, பாறைக் கரை அல்லது காட்டில் வாழ்வதை விட பாதுகாக்கப்பட அதிக வாய்ப்புடையது. எனவே புதைபடிவப் பதிவு சார்புடைய மாதிரி; ஒரு குழுவின் புதைபடிவங்கள் இல்லாதது அது அரிது அல்லது இல்லை என்பதற்குச் சான்றல்ல."),
                    bpoints("Forms of fossil preservation", "புதைபடிவப் பாதுகாப்பின் வடிவங்கள்",
                        "Original material retained, as in a shell that keeps its mineral." to "கனிமத்தைத் தக்கவைக்கும் ஓடு போல, முதல் பொருள் தக்கவைக்கப்படுதல்.",
                        "Replacement of the original material by another mineral." to "முதல் பொருள் மற்றொரு கனிமத்தால் மாற்றப்படுதல்.",
                        "A mould, left when the remains dissolve, and a cast, formed when the mould is filled." to "எச்சங்கள் கரையும்போது எஞ்சும் அச்சு; அச்சு நிரப்பப்படும்போது உருவாகும் வார்ப்பு.",
                        "Trace fossils, such as burrows and tracks, which record behaviour and not the body." to "உடலை அல்ல, நடத்தையைப் பதிவு செய்யும் வளைகள், பாதச்சுவடுகள் போன்ற தடய புதைபடிவங்கள்.",
                    ),
                    bdef("Fossil", "புதைபடிவம்", "The preserved remains or traces of an organism from the geological past." to
                        "புவியியல் கடந்த காலத்தின் உயிரியின் பாதுகாக்கப்பட்ட எச்சங்கள் அல்லது தடயங்கள்."),
                    bdef("Taphonomy", "டாஃபோனமி", "The study of the processes that affect an organism from death to fossilisation." to
                        "இறப்பிலிருந்து புதைபடிவமாதல் வரை உயிரியைப் பாதிக்கும் செயல்முறைகளின் ஆய்வு."),
                    bdef("Preservation bias", "பாதுகாப்புச் சார்பு", "The over-representation of organisms with durable hard parts in the fossil record." to
                        "புதைபடிவப் பதிவில் நீடித்த கடினப் பகுதிகளுடைய உயிரிகளின் மிகைப் பிரதிநிதித்துவம்."),
                    bdef("Mould", "அச்சு", "An impression left in rock by a dissolved organism." to
                        "கரைந்த உயிரியால் பாறையில் எஞ்சிய பதிவு."),
                    bdef("Trace fossil", "தடய புதைபடிவம்", "A fossil that records the activity of an organism, such as a track or burrow." to
                        "பாதச்சுவடு, வளை போன்ற உயிரியின் செயல்பாட்டைப் பதிவு செய்யும் புதைபடிவம்."),
                )),
            bsec("index", "Index fossils and biostratigraphy", "வழிகாட்டிப் புதைபடிவங்களும் உயிர்படிவ அடுக்கியலும்",
                blocks = listOf(
                    bp("Fossils are used to date and correlate rocks, which is the subject of biostratigraphy. The principle is that the fauna of the past changed with time in an irreversible way, so that a particular fossil species is found only in rocks of a limited age. If the same fossil is found in rocks in two distant places, the rocks can be correlated in time. A fossil used in this way is an index fossil, or guide fossil." to
                        "புதைபடிவங்கள் பாறைகளுக்கு காலம் குறிக்கவும் ஒப்பிடவும் பயன்படுகின்றன; இது உயிர்படிவ அடுக்கியலின் பொருள். கொள்கை: கடந்த கால விலங்கினம் காலத்துடன் மீளமுடியாத வகையில் மாறியது; எனவே குறிப்பிட்ட புதைபடிவ இனம் வரம்புடைய வயதுப் பாறைகளில் மட்டுமே காணப்படுகிறது. இரு தொலைவிடங்களிலுள்ள பாறைகளில் ஒரே புதைபடிவம் காணப்பட்டால், பாறைகளைக் காலத்தில் ஒப்பிடலாம். இவ்வாறு பயன்படும் புதைபடிவம் வழிகாட்டிப் புதைபடிவம்."),
                    bpoints("What makes a good index fossil", "நல்ல வழிகாட்டிப் புதைபடிவத்தை உருவாக்குவது",
                        "Rapid evolution, so that each species lasted only a short time." to "ஒவ்வொரு இனமும் குறுகிய காலமே நிலைத்திருக்கும் வகையில் விரைவான பரிணாமம்.",
                        "Wide geographic distribution, so that it can be found in many places." to "பல இடங்களில் காணப்படும் வகையில் பரந்த புவியியல் பரவல்.",
                        "Abundance, so that it is likely to be found." to "கண்டுபிடிக்க வாய்ப்புள்ள வகையில் மிகுதி.",
                        "A restricted time range, with a clear first and last appearance." to "தெளிவான முதல், கடைசித் தோற்றத்துடன் வரம்புடைய கால எல்லை.",
                        "Durable hard parts that are easily preserved and recognised." to "எளிதில் பாதுகாக்கப்பட்டு அடையாளம் காணப்படும் நீடித்த கடினப் பகுதிகள்.",
                    ),
                    bp("It is important not to overstate the point. Not every species of a trilobite, ammonite or crinoid is equally useful as an index fossil. A species that lived for a very long time, or in a narrow habitat, or that is rare, is of little use for correlation. The groups discussed in this chapter provide many index fossils, and that is a statement about the groups as sources of candidates, and not a claim that each of their species is an index fossil." to
                        "புள்ளியை மிகைப்படுத்தாமல் இருப்பது முக்கியம். டிரைலோபைட், அம்மோனைட், கிரினாய்டின் ஒவ்வொரு இனமும் வழிகாட்டிப் புதைபடிவமாக சமமாகப் பயன்படாது. மிக நீண்ட காலம், குறுகிய வாழிடத்தில் வாழ்ந்த அல்லது அரிதான இனம் ஒப்பீட்டுக்குச் சிறிதே பயன்படும். இவ்வத்தியாயத்தில் விவாதிக்கப்படும் குழுக்கள் பல வழிகாட்டிப் புதைபடிவங்களைத் தருகின்றன; இது குழுக்கள் வேட்பாளர்களின் மூலங்கள் என்ற கூற்று; அவற்றின் ஒவ்வொரு இனமும் வழிகாட்டிப் புதைபடிவம் என்ற கூற்று அல்ல."),
                    bdef("Index fossil", "வழிகாட்டிப் புதைபடிவம்", "A fossil that is widespread, abundant and restricted in time, and so is used to date and correlate rocks." to
                        "பரவலான, மிகுதியான, காலத்தில் வரம்புடைய, எனவே பாறைகளுக்குக் காலம் குறிக்கவும் ஒப்பிடவும் பயன்படும் புதைபடிவம்."),
                    bdef("Biostratigraphy", "உயிர்படிவ அடுக்கியல்", "The use of fossils to date and correlate rock layers." to
                        "பாறை அடுக்குகளுக்குக் காலம் குறிக்கவும் ஒப்பிடவும் புதைபடிவங்களைப் பயன்படுத்துதல்."),
                    bdef("Correlation", "ஒப்பீடு", "Matching rocks of the same age in different places." to
                        "வெவ்வேறு இடங்களிலுள்ள ஒரே வயதுப் பாறைகளைப் பொருத்துதல்."),
                )),
            bsec("trilobites", "Trilobites", "டிரைலோபைட்டுகள்",
                blocks = listOf(
                    bp("The trilobites are extinct marine arthropods, with a calcified, segmented exoskeleton. The body is divided lengthwise into three lobes, a central axis and two side parts, which gives the group its name, and it is divided into a head shield, a thorax of movable segments and a tail shield. Each segment carried a pair of jointed limbs, which are known only from rare fossils because they were not calcified. Many trilobites could roll up, like a woodlouse, and the rolled specimens are common." to
                        "டிரைலோபைட்டுகள் சுண்ணாம்பேறிய, துண்டுபட்ட புறச்சட்டகமுடைய அழிந்த கடல் கணுக்காலிகள். உடல் நீளவாக்கில் மைய அச்சும் இரு பக்கப் பகுதிகளுமான மூன்று மடல்களாகப் பிரிகிறது; இது குழுவுக்குப் பெயர் தருகிறது; தலைக் கவசம், அசையும் துண்டுகளுடைய மார்பு, வால் கவசம் எனப் பிரிகிறது. ஒவ்வொரு துண்டும் இணைக் கணுவுடைய உறுப்புகளைத் தாங்கியது; அவை சுண்ணாம்பேறாததால் அரிய புதைபடிவங்களிலிருந்து மட்டுமே அறியப்படுகின்றன. பல டிரைலோபைட்டுகள் மர ஈ போல சுருண்டுகொள்ள முடிந்தன; சுருண்ட மாதிரிகள் பொதுவானவை."),
                    bp("Trilobites are important because they were abundant and diverse in the Palaeozoic seas and evolved rapidly, and because their wide distribution allows the correlation of rocks of that era. Many species are therefore useful in the biostratigraphy of the Palaeozoic, with the proviso given above that not every species is. The trilobites are arthropods, and not crustaceans, and the relationship of the group to the living arthropod groups is a question for specialists. A student may say simply that they are an extinct class of arthropods." to
                        "டிரைலோபைட்டுகள் முக்கியமானவை; ஏனெனில் பேலியோசோயிக் கடல்களில் மிகுதியாகவும் பன்முகமாகவும் இருந்து விரைவாகப் பரிணமித்தன; பரந்த பரவல் அக்காலப் பாறைகளின் ஒப்பீட்டை அனுமதிக்கிறது. எனவே மேலே தரப்பட்ட, ஒவ்வொரு இனமும் அல்ல என்ற நிபந்தனையுடன், பல இனங்கள் பேலியோசோயிக் உயிர்படிவ அடுக்கியலில் பயனுள்ளவை. டிரைலோபைட்டுகள் கணுக்காலிகள்; நண்டுவகைகள் அல்ல; வாழும் கணுக்காலிக் குழுக்களுடனான குழுவின் உறவு நிபுணர்களுக்கான கேள்வி. அவை அழிந்த கணுக்காலி வகுப்பு என மாணவர் எளிமையாகக் கூறலாம்."),
                    bdef("Trilobite", "டிரைலோபைட்", "An extinct marine arthropod with a three-lobed, segmented, calcified exoskeleton." to
                        "மூன்று மடல், துண்டுபட்ட, சுண்ணாம்பேறிய புறச்சட்டகமுடைய அழிந்த கடல் கணுக்காலி."),
                    bdef("Palaeozoic", "பேலியோசோயிக்", "The era of geological time before the Mesozoic, in which the trilobites flourished." to
                        "மீசோசோயிக்குக்கு முந்தைய, டிரைலோபைட்டுகள் செழித்த புவியியல் கால ஊழி."),
                    bdef("Exoskeleton", "புறச்சட்டகம்", "A skeleton on the outside of the body." to
                        "உடலின் வெளியே உள்ள கூடு."),
                )),
            bsec("ammonites", "Ammonites", "அம்மோனைட்டுகள்",
                blocks = listOf(
                    bp("The ammonites are extinct cephalopod molluscs, with planispirally coiled, chambered shells, in which the coil lies in one plane. The shell is divided by partitions, the septa, into chambers, and the animal lived in the last, the body chamber. A tube, the siphuncle, ran through the chambers. The edges of the septa meet the outer wall of the shell in lines called sutures, and the pattern of the sutures, which is often complex in the ammonites, is used to tell the species and the groups apart." to
                        "அம்மோனைட்டுகள் சுருள் ஒரே தளத்தில் அமையும் சுற்றுத்தளச் சுருள், அறை ஓடுடைய அழிந்த தலைக்காலி மெல்லுடலிகள். ஓடு செப்டா எனும் பிரிப்புகளால் அறைகளாகப் பிரிக்கப்படுகிறது; விலங்கு கடைசி உடல் அறையில் வாழ்ந்தது. அறைகள் வழியே சைஃபன்கிள் எனும் குழாய் சென்றது. செப்டாக்களின் விளிம்புகள் ஓட்டின் வெளிச்சுவரைச் சந்திக்கும் கோடுகள் சூச்சர்கள் எனப்படுகின்றன; அம்மோனைட்டுகளில் பெரும்பாலும் சிக்கலான சூச்சர் அமைப்பு இனங்களையும் குழுக்களையும் வேறுபடுத்தப் பயன்படுகிறது."),
                    bp("Many ammonite lineages are important index fossils of the Mesozoic. The point that a student must remember is that an ammonite is a cephalopod, and that a coiled shell does not make it a gastropod. The shell of a snail like Pila is a single, unchambered, spirally coiled tube, usually coiled in a spire, and the shell of an ammonite is a chambered, planispiral shell of a swimming predator. The two are not closely related. The living cephalopod with an external chambered shell is the nautilus, which is related to the ammonites more distantly than the squids, and it is a comparison rather than a descendant." to
                        "பல அம்மோனைட்டுப் பரம்பரைகள் மீசோசோயிக்கின் முக்கிய வழிகாட்டிப் புதைபடிவங்கள். மாணவர் நினைவில் வைக்க வேண்டிய புள்ளி: அம்மோனைட் ஒரு தலைக்காலி; சுருள் ஓடு அதை இரைப்பைக்காலியாக்காது. பைலா போன்ற நத்தையின் ஓடு அறைகளற்ற, வழக்கமாக கூம்பாகச் சுருண்ட ஒற்றைச் சுருள் குழாய்; அம்மோனைட்டின் ஓடு நீந்தும் வேட்டையாடியின் அறைகளுடைய சுற்றுத்தளச் சுருள் ஓடு. இரண்டும் நெருங்கிய உறவினர் அல்ல. வெளிப்புற அறை ஓடுடைய வாழும் தலைக்காலி நாட்டிலஸ்; அது அம்மோனைட்டுகளுடன் ஸ்குவிட்களை விடத் தொலைவில் தொடர்புடையது; வழித்தோன்றல் அல்ல, ஒப்பீடு."),
                    bnote("Ammonite is not a gastropod", "அம்மோனைட் இரைப்பைக்காலி அல்ல",
                        "An ammonite is a cephalopod with a chambered shell. A coiled shell alone does not make an animal a snail." to
                            "அம்மோனைட் அறை ஓடுடைய தலைக்காலி. சுருள் ஓடு மட்டும் விலங்கை நத்தையாக்காது."),
                    bdef("Ammonite", "அம்மோனைட்", "An extinct cephalopod with a planispirally coiled, chambered shell; an important Mesozoic index fossil." to
                        "சுற்றுத்தளச் சுருள், அறை ஓடுடைய அழிந்த தலைக்காலி; முக்கிய மீசோசோயிக் வழிகாட்டிப் புதைபடிவம்."),
                    bdef("Septum", "செப்டம்", "A partition between the chambers of a cephalopod shell." to
                        "தலைக்காலி ஓட்டின் அறைகளுக்கிடையேயான பிரிப்பு."),
                    bdef("Suture", "சூச்சர்", "The line where a septum meets the outer wall of the shell." to
                        "செப்டம் ஓட்டின் வெளிச்சுவரைச் சந்திக்கும் கோடு."),
                    bdef("Mesozoic", "மீசோசோயிக்", "The era of geological time after the Palaeozoic, in which ammonites were abundant." to
                        "பேலியோசோயிக்குக்குப் பிந்தைய, அம்மோனைட்டுகள் மிகுதியாக இருந்த புவியியல் கால ஊழி."),
                )),
            bsec("crinoids", "Crinoid fossils", "கிரினாய்டு புதைபடிவங்கள்",
                blocks = listOf(
                    bp("The crinoids are echinoderms, the sea lilies and feather stars, with a body of a cup, the calyx, that holds the organs, arms that radiate from it, and in many forms a stalk by which the animal was attached to the bottom. The skeleton of calcareous ossicles is made of many plates, and the stalk is made of rings, the columnals, that separate easily after death. The columnals and the calyx plates are among the commonest of fossils in some limestones, where whole rock layers may be made of crinoid debris." to
                        "கிரினாய்டுகள் முட்தோலிகள், கடல் அல்லிகளும் இறகு நட்சத்திரங்களும்; உறுப்புகளைக் கொண்ட கிண்ணம், கேலிக்ஸ்; அதிலிருந்து கதிர்போல் பிரியும் கரங்கள்; பல வடிவங்களில் விலங்கு அடியில் ஒட்டியிருந்த தண்டு ஆகியவற்றுடன் உடல். சுண்ணாம்பு ஆஸிக்கிள்களின் கூடு பல தகடுகளால் ஆனது; தண்டு இறப்புக்குப் பின் எளிதில் பிரியும் வளையங்களான கொலும்னல்களால் ஆனது. சில சுண்ணாம்புக் கற்களில் கொலும்னல்களும் கேலிக்ஸ் தகடுகளும் மிகப் பொதுவான புதைபடிவங்கள்; அங்கு முழுப் பாறை அடுக்குகளும் கிரினாய்டுச் சிதறல்களால் ஆனவையாக இருக்கலாம்."),
                    bp("Crinoid fossils record the communities of ancient seas, and some are used in correlation. Because their skeleton is made of calcite plates that are loosely joined, whole specimens are less common than the isolated parts, and the finding of an entire crinoid usually shows that it was buried rapidly. The crinoids illustrate that an animal group can be abundant in the fossil record and still be represented today by a small number of living forms." to
                        "கிரினாய்டுப் புதைபடிவங்கள் பண்டைய கடல்களின் சமூகங்களைப் பதிவு செய்கின்றன; சில ஒப்பீட்டுக்குப் பயன்படுகின்றன. அவற்றின் கூடு தளர்வாக இணைந்த கால்சைட் தகடுகளால் ஆனது என்பதால் முழு மாதிரிகள் தனிப் பகுதிகளை விட அரிது; முழு கிரினாய்டு கண்டறியப்படுவது பொதுவாக அது விரைவாகப் புதைக்கப்பட்டது எனக் காட்டுகிறது. ஒரு விலங்குக் குழு புதைபடிவப் பதிவில் மிகுதியாக இருந்தும் இன்று சிறு எண்ணிக்கை வாழும் வடிவங்களால் பிரதிநிதித்துவம் பெறலாம் என்பதை கிரினாய்டுகள் விளக்குகின்றன."),
                    bdef("Calyx", "கேலிக்ஸ்", "The cup-shaped body of a crinoid that holds the organs." to
                        "உறுப்புகளைக் கொண்ட கிரினாய்டின் கிண்ண வடிவ உடல்."),
                    bdef("Columnal", "கொலும்னல்", "One of the ring-shaped ossicles that make up the stalk of a crinoid." to
                        "கிரினாய்டின் தண்டை உருவாக்கும் வளைய வடிவ ஆஸிக்கிள்களில் ஒன்று."),
                    bdef("Crinoid", "கிரினாய்டு", "An echinoderm with a calyx, arms and often a stalk; a sea lily or feather star." to
                        "கேலிக்ஸ், கரங்கள், பெரும்பாலும் தண்டுடைய முட்தோலி; கடல் அல்லி அல்லது இறகு நட்சத்திரம்."),
                )),
            bsec("protozoa", "Soft-bodied protozoans and their exceptions", "மென்உடல் புரோட்டோசோவாக்களும் விதிவிலக்குகளும்",
                blocks = listOf(
                    bp("Naked protozoan cells, such as an Amoeba or a Paramecium, are made of soft cytoplasm and membranes that decay quickly after death, and so they have a low chance of preservation under ordinary conditions. The lack of mineralised hard parts leaves them vulnerable to loss in the processes that precede fossilisation. A student must be able to explain this, and also to say that the poor fossil record of such cells is a bias of preservation and is not proof that single-celled organisms were rare in ancient ecosystems." to
                        "அமீபா, பாரமீசியம் போன்ற வெற்று புரோட்டோசோவா உயிரணுக்கள் இறப்புக்குப் பின் விரைவாக அழுகும் மென்சைட்டோபிளாசம், சவ்வுகளால் ஆனவை; எனவே சாதாரண சூழலில் பாதுகாக்கப்படும் வாய்ப்பு குறைவு. கனிமமாக்கப்பட்ட கடினப் பகுதிகள் இல்லாமை புதைபடிவமாவதற்கு முந்தைய செயல்முறைகளில் இழப்புக்கு எளிதில் உட்படுத்துகிறது. இதை விளக்கவும், அத்தகைய உயிரணுக்களின் மோசமான புதைபடிவப் பதிவு பாதுகாப்புச் சார்பு; ஒருசெல் உயிரிகள் பண்டைய சூழல்களில் அரிதாக இருந்தன என்பதற்கான சான்று அல்ல எனக் கூறவும் மாணவர் வல்லவராக இருக்க வேண்டும்."),
                    bp("There are important exceptions. The foraminiferans produce shells, called tests, of calcium carbonate, and the radiolarians produce skeletons of silica, and both fossilise extremely well. They are abundant in marine sediments, and the microfossils of these groups are widely used in biostratigraphy, in the reconstruction of ancient environments and in the correlation of sedimentary sequences. Thus the protozoans include some of the best-represented fossils of all, and it is only the naked forms that are poorly known." to
                        "முக்கிய விதிவிலக்குகள் உள்ளன. ஃபோராமினிஃபெரன்கள் கால்சியம் கார்பனேட் ஓடுகளான டெஸ்ட்களை உருவாக்குகின்றன; ரேடியோலேரியன்கள் சிலிகா கூடுகளை உருவாக்குகின்றன; இரண்டும் மிக நன்றாகப் புதைபடிவமாகின்றன. அவை கடல் படிவுகளில் மிகுதி; இக்குழுக்களின் நுண்புதைபடிவங்கள் உயிர்படிவ அடுக்கியல், பண்டைய சூழல் மறுகட்டமைப்பு, படிவ வரிசைகளின் ஒப்பீட்டில் பரவலாகப் பயன்படுகின்றன. எனவே புரோட்டோசோவாக்கள் மிகச் சிறப்பாகப் பிரதிநிதித்துவம் பெறும் சில புதைபடிவங்களையும் உள்ளடக்குகின்றன; வெற்று வடிவங்கள் மட்டுமே மோசமாக அறியப்பட்டவை."),
                    btable("Protozoan groups and their fossil record", "புரோட்டோசோவா குழுக்களும் அவற்றின் புதைபடிவப் பதிவும்",
                        listOf("Group" to "குழு", "Hard part" to "கடினப் பகுதி", "Fossil record" to "புதைபடிவப் பதிவு"),
                        listOf(
                            listOf("Naked amoebae and ciliates" to "வெற்று அமீபாக்களும் சிலியேட்டுகளும்", "None" to "இல்லை", "Poor, owing to rapid decay" to "விரைவான அழுகலால் மோசம்"),
                            listOf("Foraminiferans" to "ஃபோராமினிஃபெரன்கள்", "Calcareous test" to "சுண்ணாம்பு டெஸ்ட்", "Excellent; widely used microfossils" to "சிறப்பு; பரவலாகப் பயன்படும் நுண்புதைபடிவங்கள்"),
                            listOf("Radiolarians" to "ரேடியோலேரியன்கள்", "Siliceous skeleton" to "சிலிகா கூடு", "Excellent; used in biostratigraphy" to "சிறப்பு; உயிர்படிவ அடுக்கியலில் பயன்படுகிறது"),
                        )),
                    bdef("Foraminiferan", "ஃபோராமினிஃபெரன்", "A protozoan with a calcareous shell, the test, that fossilises well." to
                        "நன்கு புதைபடிவமாகும் சுண்ணாம்பு ஓடான டெஸ்ட் உடைய புரோட்டோசோவா."),
                    bdef("Radiolarian", "ரேடியோலேரியன்", "A protozoan with a skeleton of silica that fossilises well." to
                        "நன்கு புதைபடிவமாகும் சிலிகா கூடுடைய புரோட்டோசோவா."),
                    bdef("Microfossil", "நுண்புதைபடிவம்", "A fossil so small that it must be studied with a microscope." to
                        "நுண்ணோக்கியால் மட்டுமே படிக்கக்கூடிய அளவு சிறிய புதைபடிவம்."),
                )),
            bsec("living-types", "Fossils and the living types", "புதைபடிவங்களும் வாழும் வகைகளும்",
                blocks = listOf(
                    bp("One point is a matter of practice in the laboratory and in writing. The animals that are examined in the practical course, among them Pila and Asterias, are living types and not fossils. They are used to learn the plan of their groups. The fossil groups described here, the trilobites and the ammonites, have no living representative that can be used in the same way. Crinoids have living forms, and the living sea lilies and feather stars are used in teaching. A student should be careful to say whether a form is living or extinct." to
                        "ஒரு புள்ளி ஆய்வகத்திலும் எழுத்திலும் நடைமுறை சார்ந்தது. செய்முறைப் பாடத்தில் ஆராயப்படும் விலங்குகள், பைலா, அஸ்டீரியாஸ் உட்பட, வாழும் வகைகள்; புதைபடிவங்கள் அல்ல. தம் குழுக்களின் திட்டத்தைக் கற்க அவை பயன்படுகின்றன. இங்கு விவரிக்கப்படும் புதைபடிவக் குழுக்கள், டிரைலோபைட்டுகள், அம்மோனைட்டுகள், அதே வழியில் பயன்படுத்தக்கூடிய வாழும் பிரதிநிதி இல்லாதவை. கிரினாய்டுகளுக்கு வாழும் வடிவங்கள் உண்டு; வாழும் கடல் அல்லிகளும் இறகு நட்சத்திரங்களும் கற்பித்தலில் பயன்படுகின்றன. ஒரு வடிவம் வாழ்வதா அழிந்ததா என மாணவர் கவனமாகக் கூற வேண்டும்."),
                    bpoints("A summary of the fossil groups", "புதைபடிவக் குழுக்களின் சுருக்கம்",
                        "Trilobites: extinct arthropods; Palaeozoic; useful in correlation." to "டிரைலோபைட்டுகள்: அழிந்த கணுக்காலிகள்; பேலியோசோயிக்; ஒப்பீட்டில் பயனுள்ளவை.",
                        "Ammonites: extinct cephalopods; Mesozoic; chambered coiled shells; not gastropods." to "அம்மோனைட்டுகள்: அழிந்த தலைக்காலிகள்; மீசோசோயிக்; அறைகளுடைய சுருள் ஓடுகள்; இரைப்பைக்காலிகள் அல்ல.",
                        "Crinoids: echinoderms with living and fossil forms; stem columnals and calyx plates are common." to "கிரினாய்டுகள்: வாழும், புதைபடிவ வடிவங்களுடைய முட்தோலிகள்; தண்டு கொலும்னல்களும் கேலிக்ஸ் தகடுகளும் பொதுவானவை.",
                        "Foraminiferans and radiolarians: protozoans with durable skeletons and excellent microfossil records." to "ஃபோராமினிஃபெரன்களும் ரேடியோலேரியன்களும்: நீடித்த கூடுகளும் சிறந்த நுண்புதைபடிவப் பதிவும் உடைய புரோட்டோசோவாக்கள்.",
                    ),
                    bdef("Extinct", "அழிந்த", "No longer existing as a living species or group." to
                        "வாழும் இனமாக அல்லது குழுவாக இனி இல்லாத."),
                    bdef("Living type", "வாழும் வகை", "A living species studied as an example of the plan of its group." to
                        "தன் குழுவின் திட்டத்துக்கு எடுத்துக்காட்டாகப் படிக்கப்படும் வாழும் இனம்."),
                )),
        ),
        summary = listOf(
            "Fossils are biased towards organisms with mineralised hard parts, which is a preservation bias." to "புதைபடிவங்கள் கனிமமாக்கப்பட்ட கடினப் பகுதிகளுடைய உயிரிகளை நோக்கிச் சார்புடையவை; இது பாதுகாப்புச் சார்பு.",
            "A good index fossil evolves rapidly, is widespread and abundant, and has a restricted time range." to "நல்ல வழிகாட்டிப் புதைபடிவம் விரைவாகப் பரிணமிக்கிறது, பரவலானது, மிகுதியானது, வரம்புடைய கால எல்லை உடையது.",
            "Trilobites are extinct Palaeozoic arthropods useful in biostratigraphy." to "டிரைலோபைட்டுகள் உயிர்படிவ அடுக்கியலில் பயனுள்ள அழிந்த பேலியோசோயிக் கணுக்காலிகள்.",
            "Ammonites are extinct cephalopods with chambered coiled shells and are not gastropods." to "அம்மோனைட்டுகள் அறைகளுடைய சுருள் ஓடுடைய அழிந்த தலைக்காலிகள்; இரைப்பைக்காலிகள் அல்ல.",
            "Crinoid columnals and calyx plates are common fossils that record ancient marine communities." to "கிரினாய்டு கொலும்னல்களும் கேலிக்ஸ் தகடுகளும் பண்டைய கடல் சமூகங்களைப் பதிவு செய்யும் பொதுவான புதைபடிவங்கள்.",
            "Foraminiferans and radiolarians fossilise well; the poor record of naked protozoans is a bias, not proof of rarity." to "ஃபோராமினிஃபெரன்களும் ரேடியோலேரியன்களும் நன்கு புதைபடிவமாகின்றன; வெற்று புரோட்டோசோவாக்களின் மோசமான பதிவு சார்பே; அரிதாக இருந்தன என்பதற்குச் சான்றல்ல.",
        ),
        checks = listOf(
            bqc("Why are soft-bodied animals poorly represented as fossils?", "மென்உடல் விலங்குகள் புதைபடிவங்களாக ஏன் குறைவாகப் பிரதிநிதித்துவம் பெறுகின்றன?",
                "Their tissues decay quickly and lack mineralised hard parts, so they are rarely preserved.", "அவற்றின் திசுக்கள் விரைவாக அழுகுகின்றன; கனிமமாக்கப்பட்ட கடினப் பகுதிகள் இல்லை; எனவே அரிதாகவே பாதுகாக்கப்படுகின்றன."),
            bqc("Name four properties of a good index fossil.", "நல்ல வழிகாட்டிப் புதைபடிவத்தின் நான்கு பண்புகளைப் பெயரிடுக.",
                "Rapid evolution, wide distribution, abundance and a restricted time range.", "விரைவான பரிணாமம், பரந்த பரவல், மிகுதி, வரம்புடைய கால எல்லை."),
            bqc("To which class does an ammonite belong?", "அம்மோனைட் எந்த வகுப்பைச் சேர்ந்தது?",
                "Cephalopoda, not Gastropoda.", "தலைக்காலி; இரைப்பைக்காலி அல்ல."),
            bqc("Which protozoans fossilise well, and why?", "எந்த புரோட்டோசோவாக்கள் நன்கு புதைபடிவமாகின்றன, ஏன்?",
                "Foraminiferans, with calcareous tests, and radiolarians, with siliceous skeletons.", "சுண்ணாம்பு டெஸ்ட்களுடைய ஃபோராமினிஃபெரன்களும் சிலிகா கூடுகளுடைய ரேடியோலேரியன்களும்."),
        ),
    )
}
