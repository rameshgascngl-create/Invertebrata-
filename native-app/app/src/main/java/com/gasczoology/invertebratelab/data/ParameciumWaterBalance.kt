package com.gasczoology.invertebratelab.data

data class WaterBalanceReading(val id: String, val heading: BilingualText, val paragraphs: List<BilingualText>)
data class WaterBalanceStructure(val id: String, val name: BilingualText, val explanation: BilingualText)
data class WaterBalanceStage(val id: String, val heading: BilingualText, val explanation: BilingualText)

/** Additive schema-1 position. Restoration is always paused, never an automatic discharge. */
data class WaterBalanceProgress(
    val stageId: String = "osmosis", val readingId: String = "4.1",
    val selectedStructure: String = "reservoir", val phasePermille: Int = 0,
    val reducedMotion: Boolean = false,
) {
    fun normalized() = copy(
        stageId = stageId.takeIf { id -> ParameciumWaterBalance.stages.any { it.id == id } } ?: "osmosis",
        readingId = readingId.takeIf { id -> ParameciumWaterBalance.readings.any { it.id == id } } ?: "4.1",
        selectedStructure = selectedStructure.takeIf { id -> ParameciumWaterBalance.structures.any { it.id == id } } ?: "reservoir",
        phasePermille = phasePermille.coerceIn(0, 1000),
    )
}

/** Original teaching prose and geometry; microscopy observations and mechanism evidence stay distinct. */
object ParameciumWaterBalance {
    const val reviewStatus = "DRAFT_UNVERIFIED"
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    val structures = listOf(
        WaterBalanceStructure("reservoir", b("C · Central contractile vacuole", "C · மையச் சுருங்கு நுண்குமிழ்"), b(
            "The central reservoir receives fluid collected by the complex and periodically discharges it at a cortical pore. It is not a digestive food vacuole. Its changing outline is visible by light microscopy, but the membrane network shown here is an enlarged interpretation; no muscular squeezing wall is implied.",
            "மைய நுண்குமிழ், இத்தொகுப்பு சேகரித்த திரவத்தைப் பெற்று, செல்புறத் துளை வழியாக அவ்வப்போது வெளியேற்றுகிறது. இது செரிமான உணவு நுண்குமிழ் அல்ல. அதன் வடிவ மாற்றத்தை ஒளி நுண்ணோக்கியில் காணலாம்; இங்கு பெரிதாக்கிய சவ்வு வலை விளக்கக் குறியே. தசையால் அழுத்தும் சுவர் இருப்பதாகக் கருதக்கூடாது.")),
        WaterBalanceStructure("ampulla", b("A · Ampulla of a radial arm", "A · ஆரக் கிளையின் விரிந்த பகுதி (ampulla)"), b(
            "The ampulla is the expanded region near the central vacuole along a radial arm. Allen's serial-section microscopy of P. multimicronucleatum shows changing membrane geometry. The enlarged comparative inset separates ampulla from collecting canal; it does not establish a permanently open connection in every phase.",
            "ஆரக் கிளையில் மைய நுண்குமிழுக்கு அருகிலுள்ள விரிந்த பகுதி ampulla எனப்படுகிறது. P. multimicronucleatum இனத்தில் Allen எடுத்த தொடர் வெட்டுப் படங்கள் சவ்வின் மாறும் வடிவத்தைக் காட்டுகின்றன. ஒப்பீட்டுச் சிறுபடம் இதைச் சேகரிப்புக் கால்வாயிலிருந்து வேறுபடுத்துகிறது; எல்லா நிலைகளிலும் இணைப்பு நிரந்தரமாகத் திறந்திருக்கும் என்பதற்கான சான்றல்ல.")),
        WaterBalanceStructure("canal", b("R · Collecting canal", "R · திரவச் சேகரிப்புக் கால்வாய்"), b(
            "Collecting canals form the conspicuous radial pattern around a contractile vacuole. A P. caudatum electron micrograph identifies a canal and both spongiome regions. Only representative arms are drawn: their displayed number and lengths are not species measurements. The diagram is a cutaway, not an unbroken pipe map.",
            "சேகரிப்புக் கால்வாய்கள் சுருங்கு நுண்குமிழைச் சுற்றி ஆர வடிவில் அமைந்துள்ளன. P. caudatum மின்னணு நுண்ணோக்கிப் படம் கால்வாயையும் இருவகை spongiome பகுதிகளையும் அடையாளப்படுத்துகிறது. பிரதிநிதிக் கிளைகளே வரையப்பட்டுள்ளன; அவற்றின் எண்ணிக்கையோ நீளமோ இனத்திற்கான அளவீடு அல்ல. இது வெட்டுத் தோற்ற விளக்கப்படம்; இடைவிடாத குழாய் வரைபடம் அல்ல.")),
        WaterBalanceStructure("smooth", b("S · Smooth spongiome", "S · மென்மையான குழாய் வலை (smooth spongiome)"), b(
            "The smooth spongiome is an associated membrane-tubule network, distinguished from the peg-bearing region. In the comparative P. multimicronucleatum inset it borders the collecting canal. Tubule thickness, spacing and magnification are illustrative; these details cannot be resolved in an ordinary undergraduate wet mount.",
            "Smooth spongiome என்பது இணைந்த சவ்வுக் குழாய் வலை; வெளிப்புறச் சிறு துருத்தங்கள் கொண்ட பகுதியிலிருந்து இது வேறுபடுகிறது. P. multimicronucleatum ஒப்பீட்டுச் சிறுபடத்தில் சேகரிப்புக் கால்வாயை ஒட்டி இது உள்ளது. குழாயின் தடிமன், இடைவெளி, பெரிதாக்கம் விளக்கத்திற்கானவை; சாதாரண மாணவர் ஈரத் தயாரிப்பில் இவ்விவரங்களைத் தெளிவாகக் காண முடியாது.")),
        WaterBalanceStructure("decorated", b("D · Decorated spongiome / V-ATPase", "D · துருத்தங்கள் கொண்ட குழாய் வலை / V-ATPase"), b(
            "The decorated tubules have cytosolic projections associated experimentally with V-ATPase proton pumps. This supports energy-dependent fluid segregation, not a pump that mechanically drags water molecules. Pegs in the comparative inset are exaggerated and confined to the outer tubule region around the canal; they are not a coat around the ampulla.",
            "Decorated குழாய்களின் சைட்டோசால் பக்கத் துருத்தங்கள் V-ATPase புரோட்டான் இறைப்பிகளுடன் சோதனை வழியாகத் தொடர்புபடுத்தப்பட்டுள்ளன. இது ஆற்றல் சார்ந்த திரவப் பிரித்தெடுப்பை ஆதரிக்கிறது; நீர் மூலக்கூறுகளை நேரடியாக இழுக்கும் இறைப்பி அல்ல. ஒப்பீட்டுப் படத்தில் துருத்தங்கள் பெரிதாக்கப்பட்டு, கால்வாயைச் சுற்றிய வெளிக் குழாய் பகுதியில் மட்டும் காட்டப்படுகின்றன; ampulla மேல் உறை அல்ல.")),
        WaterBalanceStructure("pore", b("P · Discharge pore and cortex", "P · வெளியேற்றுத் துளையும் செல்புறப் படலமும்"), b(
            "The discharge site connects the vacuole to the exterior during expulsion. Its position is anchored at the cortex, unlike a food-vacuole egestion site. The open or sealed symbol teaches discharge state; it is not a drawn valve muscle. Membrane recovery and reconnection require evidence beyond light-microscope observation.",
            "வெளியேற்றத்தின் போது இத்தளம் நுண்குமிழிலிருந்து வெளிச்சூழலுக்குத் திரவம் செல்ல உதவுகிறது. இதன் இடம் செல்புறப் படலத்தில் நிலைபெற்றுள்ளது; உணவு எச்சம் வெளியேறும் தளத்திலிருந்து வேறுபட்டது. திறந்த அல்லது மூடிய குறி வெளியேற்ற நிலையைக் கற்பிக்கிறது; வால்வு தசையைக் குறிக்கவில்லை. சவ்வு மீட்பையும் மீண்டும் இணைவதையும் அறிய ஒளி நுண்ணோக்கியைத் தாண்டிய சான்றுகள் தேவை.")),
    )
    val readings = listOf(
        WaterBalanceReading("4.1", b("4.1 · Freshwater and osmotic balance", "4.1 · நன்னீரும் சவ்வூடுபரவல் சமநிலையும்"), listOf(b(
            "A freshwater cell must maintain its volume while water exchanges across its membranes. In a hypotonic environment, net water entry is favoured by the difference in effective solute concentration. Osmoregulation is the regulation of this water and solute balance; it is not the digestion of swallowed food. The contractile-vacuole complex collects fluid and provides a route for periodic discharge.",
            "நன்னீரில் வாழும் செல், சவ்வுகள் வழியாக நீர் பரிமாறினாலும் தன் பருமனைப் பராமரிக்க வேண்டும். வெளிச்சூழலின் பயனுள்ள கரைபொருள் செறிவு குறைவாக இருக்கும் போது, செறிவு வேறுபாடு நிகர நீர் உட்புகுதலை ஆதரிக்கிறது. நீர் மற்றும் கரைபொருள் சமநிலையை ஒழுங்குபடுத்துவதே சவ்வூடுபரவல் ஒழுங்குபடுத்தல்; விழுங்கிய உணவைச் செரிப்பது அல்ல. சுருங்கு நுண்குமிழ்த் தொகுப்பு திரவத்தைச் சேகரித்து, அவ்வப்போது வெளியேற்ற வழி அளிக்கிறது."), b(
            "P. caudatum is taught with anterior and posterior contractile-vacuole complexes. The whole-cell locator shows their positions only; the large plate follows one representative complex. Do not infer simultaneous discharges from two symbols in a drawing. A visible pulse is an observation, whereas its osmotic function is an interpretation grounded in experiments.",
            "P. caudatum இல் முன்புற, பின்புறச் சுருங்கு நுண்குமிழ்த் தொகுப்புகள் விளக்கப்படுகின்றன. முழுச் செல் சிறுபடம் அவற்றின் இடங்களை மட்டும் காட்டுகிறது; பெரிய படம் ஒரு பிரதிநிதித் தொகுப்பைத் தொடர்கிறது. இரு குறிகள் இருப்பதால் இரண்டும் ஒரே நேரத்தில் வெளியேற்றும் என்று முடிவு செய்யக்கூடாது. காணப்படும் துடிப்பு ஒரு பார்வைப் பதிவு; அதன் சவ்வூடுபரவல் பணி சோதனைகளின் அடிப்படையிலான விளக்கம்."))),
        WaterBalanceReading("4.2", b("4.2 · Reservoir, radial arms and tubules", "4.2 · மைய நுண்குமிழ், ஆரக் கிளைகள், குழாய்கள்"), listOf(b(
            "Begin with the central reservoir, then trace a radial arm towards its ampulla and collecting canal. Beyond the light-microscope outline, electron microscopy reveals associated smooth and decorated spongiomes. Allen's P. caudatum collecting-canal image supplies species-specific structural evidence. The detailed tubule inset uses explicitly labelled comparative P. multimicronucleatum microscopy, rather than silently claiming identical fine geometry in every species.",
            "மைய நுண்குமிழிலிருந்து தொடங்கி, ஆரக் கிளையில் ampulla மற்றும் சேகரிப்புக் கால்வாயை அடையாளம் காண்க. ஒளி நுண்ணோக்கித் தோற்றத்தைத் தாண்டி, மின்னணு நுண்ணோக்கி smooth, decorated spongiome வலைகளைக் காட்டுகிறது. Allen இன் P. caudatum கால்வாய்ப் படம் இனம் சார்ந்த அமைப்புச் சான்றை அளிக்கிறது. நுண்குழாய்ச் சிறுபடம், வெளிப்படையாகக் குறிக்கப்பட்ட P. multimicronucleatum ஒப்பீட்டுச் சான்றை அடிப்படையாகக் கொண்டது; ஒவ்வோர் இனத்திலும் ஒரே நுண்வடிவம் இருப்பதாகக் கூறவில்லை."), b(
            "Touch a marked structure or use its named button to highlight it and read its complete explanation. Letter keys remain beside the graphite drawing so selection is not communicated by colour alone. Each inset has independent magnification. Representative radial arms, exaggerated tubules and discontinuity marks are teaching choices, not reconstructed measurements of one specimen.",
            "குறிக்கப்பட்ட அமைப்பைத் தொடுக அல்லது அதன் பெயருள்ள பொத்தானைப் பயன்படுத்துக; அது சிறப்பிக்கப்பட்டு முழு விளக்கம் கிடைக்கும். நிறத்தை மட்டும் நம்பாமல் அடையாளம் காண பென்சில் படத்தில் எழுத்துக் குறிகள் உள்ளன. ஒவ்வொரு சிறுபடமும் தனிப் பெரிதாக்கம் கொண்டது. பிரதிநிதி ஆரக் கிளைகள், பெரிதாக்கிய குழாய்கள், தொடர்ச்சி முறிவுக் குறிகள் அனைத்தும் கற்பித்தல் தேர்வுகள்; ஒரே மாதிரியின் அளவீடுகளை மீட்டமைத்தவை அல்ல."))),
        WaterBalanceReading("4.3", b("4.3 · Energy, ions and water segregation", "4.3 · ஆற்றல், அயன்கள், நீர்ப் பிரித்தெடுப்பு"), listOf(b(
            "Water follows osmotic gradients; it is not actively carried by the visible arrows. Work on Paramecium proton-pump-bearing tubules supports V-ATPase participation in fluid segregation. Energy-dependent ion transport and passive water movement must be distinguished. The enlarged peg symbols identify a membrane specialization; they do not depict measured proton trajectories or ATP consumption.",
            "நீர் சவ்வூடுபரவல் சரிவைப் பின்பற்றுகிறது; படத்திலுள்ள அம்புகள் அதைச் செயற்படக் கடத்துவதில்லை. Paramecium புரோட்டான் இறைப்பிக் குழாய்கள் பற்றிய சோதனைகள், திரவப் பிரித்தெடுப்பில் V-ATPase பங்கைக் காட்டுகின்றன. ஆற்றல் சார்ந்த அயன் கடத்தலையும் இயல்பான நீர் நகர்வையும் வேறுபடுத்த வேண்டும். பெரிதாக்கிய துருத்தக் குறிகள் சிறப்புச் சவ்வுப் பகுதியை அடையாளப்படுத்துகின்றன; அளவிடப்பட்ட புரோட்டான் பாதையையோ ATP செலவையோ காட்டவில்லை."), b(
            "Stock and colleagues measured cytosolic and vacuolar ion activities in P. multimicronucleatum, supporting an osmotic gradient into the complex under the tested conditions. This is comparative physiological evidence, not a concentration measured in the illustrated P. caudatum. The animation therefore displays qualitative fluid collection, without a numerical salt setting, universal pumping rate or claim of calibrated volume.",
            "Stock குழுவினர் P. multimicronucleatum இல் சைட்டோசால் மற்றும் நுண்குமிழ் அயன் செயல்பாடுகளை அளந்தனர்; சோதிக்கப்பட்ட சூழலில் தொகுப்பிற்குள் நீர் நகர்வுக்கான சவ்வூடுபரவல் சரிவை ஆதரித்தனர். இது ஒப்பீட்டு உடலியக்கச் சான்று; இங்கு வரையப்பட்ட P. caudatum இன் செறிவு அளவீடு அல்ல. எனவே இயக்கப்படம் பண்புசார் திரவச் சேகரிப்பை மட்டும் காட்டுகிறது; எண் சார்ந்த உப்புச் செறிவு, பொதுவான இறைப்பு வேகம் அல்லது அளவுத்திருத்தப் பருமன் கூறப்படவில்லை."))),
        WaterBalanceReading("4.4", b("4.4 · Filling, discharge and membrane recovery", "4.4 · நிரம்புதல், வெளியேற்றம், சவ்வு மீட்பு"), listOf(b(
            "Diastole denotes filling; systole denotes discharge. These describe vacuolar phases, not a vertebrate heart cycle. Collection, expansion and membrane changes overlap biologically, although this teaching sequence separates them. A growing lumen and an opening pore identify different events. The last view reduces the reservoir symbol as fluid exits; manual replay begins a new illustrative cycle.",
            "Diastole என்பது நிரம்பும் நிலை; systole என்பது வெளியேற்ற நிலை. இவை நுண்குமிழ் நிலைகள்; முதுகெலும்பியின் இதயச் சுழற்சி அல்ல. உயிரியல் நிகழ்வில் சேகரிப்பு, விரிவு, சவ்வு மாற்றங்கள் ஒன்றோடொன்று அமையலாம்; இங்கு கற்பித்தலுக்காகத் தனியாகக் காட்டப்படுகின்றன. பெரிதாகும் உட்குழியும் திறக்கும் துளையும் வெவ்வேறு நிகழ்வுகள். இறுதிக் காட்சியில் திரவம் வெளியேறும் போது மையக் குறி சிறிதாகிறது; மீண்டும் இயக்குவது புதிய விளக்கச் சுழற்சியைத் தொடங்குகிறது."), b(
            "Serial-section electron microscopy in P. multimicronucleatum describes extensive membrane remodelling. Permanent ampulla–vacuole connections and a sphincter-like closure were not verified in that work. Do not draw an encasing muscle or assume a rigid reservoir merely empties while every tube remains unchanged. The simplified plate omits the full recovery network and explicitly leaves that fine mechanism outside its geometry.",
            "P. multimicronucleatum தொடர் வெட்டு மின்னணு நுண்ணோக்கி ஆய்வு விரிவான சவ்வு மறுவடிவமைப்பைக் காட்டுகிறது. அந்த ஆய்வில் நிரந்தர ampulla–நுண்குமிழ் இணைப்போ வளையத் தசை போன்ற மூடல் அமைப்போ உறுதிப்படுத்தப்படவில்லை. சுற்றிய தசையை வரையவோ, எல்லாக் குழாய்களும் மாறாமல் இருக்க ஒரு விறைப்பான பை மட்டும் காலியாகிறது என்று கருதவோ கூடாது. எளிமைப்படுத்திய படம் முழுச் சவ்வு மீட்பு வலையைச் சேர்க்கவில்லை; அந்த நுண் இயக்கவியலைத் தன் வடிவ எல்லைக்கு வெளியே வைக்கிறது."))),
        WaterBalanceReading("4.5", b("4.5 · Wet-mount observation and evidence limits", "4.5 · ஈரத் தயாரிப்புப் பார்வையும் சான்றின் வரம்புகளும்"), listOf(b(
            "In a suitable living preparation, first locate the cell ends and contractile-vacuole pulses. Record what was actually visible, the observation interval and preparation conditions. A canal outline may be visible; individual membrane tubules, proton pumps and ion gradients are not resolved by routine light microscopy. A drawing should label inferred function separately from observed structures.",
            "ஏற்ற உயிருள்ள ஈரத் தயாரிப்பில், முதலில் செல் முனைகளையும் சுருங்கு நுண்குமிழ்த் துடிப்புகளையும் கண்டறிக. உண்மையில் தெரிந்தவற்றையும் பார்வைக் கால இடைவெளியையும் தயாரிப்புச் சூழலையும் பதிவு செய்க. கால்வாயின் வெளித்தோற்றம் தெரியலாம்; தனிச் சவ்வுக் குழாய்கள், புரோட்டான் இறைப்பிகள், அயன் சரிவுகள் சாதாரண ஒளி நுண்ணோக்கியில் பிரித்தறியப்படுவதில்லை. வரைபடத்தில் காணப்பட்ட அமைப்பையும் சான்றிலிருந்து விளக்கும் பணியையும் தனியாகக் குறிக்க வேண்டும்."), b(
            "This offline chapter is a textbook explanation and an interactive schematic, not a laboratory measurement. Its illustrative speed, proportions and blue flow cues are not experimental data or a stain. Compare the written source notes before transferring conclusions between species. Biology, Tamil terminology, physical-device usability, actual TalkBack and audible offline narration remain pending independent acceptance.",
            "இணையமின்றி இயங்கும் இப்பாடம் பாடநூல் விளக்கமும் தொடுபடும் விளக்கப்படமும் ஆகும்; ஆய்வக அளவீடு அல்ல. காட்சி வேகம், விகிதங்கள், நீல ஓட்டக் குறிகள் சோதனைத் தரவோ சாயமூட்டலோ அல்ல. இனங்களுக்கு இடையே முடிவுகளைப் பொருத்துவதற்கு முன் ஆதாரக் குறிப்புகளை ஒப்பிடுக. உயிரியல், தமிழ் சொற்கள், உண்மைச் சாதனப் பயன்பாடு, TalkBack, இணையமில்லா கேட்கக்கூடிய ஒலிவிளக்கம் ஆகியவற்றின் சுயாதீன ஏற்பு நிலுவையில் உள்ளது."))),
    )
    val stages = listOf(
        WaterBalanceStage("osmosis", b("Osmotic water entry", "சவ்வூடுபரவலால் நீர் உட்புகுதல்"), b("Freshwater favours net water entry into the cell. This opening view locates one representative complex; its small lumen is a teaching starting position, not an empty measured compartment. The cortical pore remains sealed in the diagram.", "நன்னீர்ச் சூழல் நிகர நீர் உட்புகுதலை ஆதரிக்கிறது. தொடக்கக் காட்சி ஒரு பிரதிநிதித் தொகுப்பைக் காட்டுகிறது; சிறிய உட்குழி கற்பித்தலுக்கான தொடக்க நிலை, அளவிடப்பட்ட காலிப் பகுதி அல்ல. படத்தில் செல்புறத் துளை மூடியுள்ளது.")),
        WaterBalanceStage("collect", b("Fluid segregation and collection", "திரவப் பிரித்தெடுப்பும் சேகரிப்பும்"), b("The associated tubule network participates in fluid segregation and collection. Moving blue cues indicate a qualitative route towards the reservoir, not direct molecular observations. The comparative spongiome inset remains independently enlarged; the pore is sealed.", "இணைந்த குழாய் வலை திரவப் பிரித்தெடுப்பிலும் சேகரிப்பிலும் பங்கேற்கிறது. நகரும் நீலக் குறிகள் மையத்தை நோக்கிய பண்புசார் பாதையைக் காட்டுகின்றன; நேரடி மூலக்கூறுப் பார்வை அல்ல. ஒப்பீட்டு spongiome சிறுபடம் தனிப் பெரிதாக்கம் கொண்டது; துளை மூடியுள்ளது.")),
        WaterBalanceStage("fill", b("Diastole: reservoir expansion", "Diastole: மைய நுண்குமிழ் விரிவு"), b("The central lumen enlarges during filling. Expansion and collection overlap in life; separating them makes the teaching sequence readable. The illustrated volume and fixed frame speed are not calibrated measurements. No simultaneous pulse is imposed on the other complex in the locator.", "நிரம்பும் நிலையில் மைய உட்குழி பெரிதாகிறது. உயிருள்ள செல்லில் விரிவும் சேகரிப்பும் ஒன்றோடொன்று அமையும்; கற்பித்தலில் அவை தனியாக விளக்கப்படுகின்றன. காட்டும் பருமனும் காட்சி வேகமும் அளவுத்திருத்த அளவீடுகள் அல்ல. சிறுபடத்தின் மற்ற தொகுப்பில் ஒரே நேரத் துடிப்பு திணிக்கப்படவில்லை.")),
        WaterBalanceStage("expel", b("Systole: pore discharge", "Systole: துளை வழி வெளியேற்றம்"), b("The pore opens in the schematic and outward flow accompanies a smaller reservoir symbol. The contracting outline is not evidence for a surrounding muscle. Fine membrane collapse and reassembly are not reconstructed here. Playback stops at discharge; replay and manual steps remain available.", "விளக்கப்படத்தில் துளை திறந்து, வெளிநோக்கிய ஓட்டத்துடன் மையக் குறி சிறிதாகிறது. சுருங்கும் தோற்றம் சுற்றிய தசை இருப்பதற்கான சான்றல்ல. நுண்சவ்வு மடிதலும் மீளமைதலும் இங்கு மீட்டமைக்கப்படவில்லை. வெளியேற்றத்தில் இயக்கம் நிற்கும்; மீண்டும் இயக்கவும் கையால் படிநிலை மாற்றவும் முடியும்.")),
    )
    val sourceNotes = listOf(
        "Allen microscopy: P. caudatum, Fig. 46 (collecting canal, smooth/decorated spongiome). https://www6.pbrc.hawaii.edu/allen/ch10a/46-pca4201.html",
        "Comparative P. multimicronucleatum radial arm, Fig. 14; decorated tubules around canal, absent near ampulla. https://www6.pbrc.hawaii.edu/allen/ch09/14-pmcvc800415-21.html",
        "Allen & Fok (1988), Membrane Dynamics. Comparative ultrastructure; permanent connections/closure not verified. DOI:10.1111/j.1550-7408.1988.tb04078.x",
        "Fok et al. (1995), proton-pump pegs; experimental interpretation, not direct water tracking. DOI:10.1242/jcs.108.10.3163; PMID:7593277",
        "Stock et al. (2002), P. multimicronucleatum ion-gradient measurements. DOI:10.1242/jcs.115.11.2339; PMID:12006618",
    )
    fun structure(id: String) = structures.first { it.id == id }
    /** Presentation fraction only: deliberately no physical volume, rate or universal salt-response formula. */
    fun lumen(stage: String, phase: Float): Float {
        val p = phase.coerceIn(0f, 1f)
        return when (stage) { "collect" -> .25f + .35f*p; "fill" -> .60f + .30f*p; "expel" -> .90f - .76f*p; else -> .18f + .07f*p }
    }
}

/** One logical geometry is shared by rendering and hit-testing; all magnifications are illustrative. */
object WaterBalanceFigure {
    data class Mark(val id: String, val x: Float, val y: Float)
    val marks = listOf(Mark("reservoir",340f,320f),Mark("ampulla",475f,320f),Mark("canal",565f,320f),
        Mark("smooth",855f,415f),Mark("decorated",860f,490f),Mark("pore",340f,110f))
    data class Fit(val scale: Float, val dx: Float, val dy: Float) {
        fun x(v: Float) = dx + v*scale
        fun y(v: Float) = dy + v*scale
    }
    fun fit(width: Float,height: Float): Fit { val s=minOf(width/1000f,height/540f);return Fit(s,(width-1000*s)/2,(height-540*s)/2) }
    fun at(x: Float,y: Float): String? = marks.minByOrNull { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y) }
        ?.takeIf { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y) <= 65f*65f }?.id
}
