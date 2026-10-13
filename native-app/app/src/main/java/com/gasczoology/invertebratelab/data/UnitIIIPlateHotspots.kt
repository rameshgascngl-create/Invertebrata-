package com.gasczoology.invertebratelab.data

/** Normalized anatomical endpoints for original 960x1280 candidate plate masters.
 * Endpoints are locked to source geometry; NEVER guess from generated labels.
 * This coordinate audit is mechanical, not independent zoologist sign-off.
 */
data class UnitIIIHotspot(
    val id: String,
    val x: Float,
    val y: Float,
    val title: BilingualText,
    val explanation: BilingualText
)
object UnitIIIPlateHotspots {
    private fun h(id:String,x:Float,y:Float,en:String,ta:String,detailEn:String,detailTa:String) =
        UnitIIIHotspot(id,x,y,BilingualText(en,ta),BilingualText(detailEn,detailTa))

    val byStem: Map<String,List<UnitIIIHotspot>> = mapOf(
        "type_studies/fasciola/external" to listOf(
            h("oral",.5f,.105f,"Oral sucker","வாயுறிஞ்சி",
                "The oral sucker surrounds the mouth at the tip of the anterior cone.","முன்கூம்பின் முனையில் உள்ள வாயை வாயுறிஞ்சி சூழ்ந்துள்ளது."),
            h("genital",.5f,.234f,"Common genital pore","பொது இனப்பெருக்கத் துளை",
                "The common genital opening is on the ventral midline, anterior to the acetabulum.","பொது இனப்பெருக்கத் துளை நடுவயிற்றுக் கோட்டில் வயிற்றுறிஞ்சிக்கு முன்பாக உள்ளது."),
            h("acetabulum",.5f,.282f,"Ventral sucker","வயிற்றுறிஞ்சி",
                "The acetabulum is an attachment sucker, not a second mouth.","வயிற்றுறிஞ்சி பற்றுதலுக்கான உறுப்பு; இரண்டாவது வாய் அல்ல."),
            h("shoulder",.35f,.176f,"Cephalic shoulders","முன்புறத் தோள்கள்",
                "Broad shoulders arise just posterior to the anterior cone.","முன்கூம்புக்குப் பின்னால் அகன்ற தோள்பகுதிகள் காணப்படுகின்றன."),
            h("posterior",.5f,.939f,"Posterior end","பின்முனை",
                "The posterior tip tapers; there is no digestive anus in this trematode.","பின்முனை குறுகுகிறது; இந்தத் தட்டைப்புழுவில் செரிமான ஆசனவாய் இல்லை.")
        ),
        "type_studies/fasciola/digestive-reproductive" to listOf(
            h("caecum",.40f,.46f,"Branched intestinal caecum","கிளைத்த குடற்பை",
                "The digestive tract divides into two blind branched intestinal caeca.","செரிமானப்பாதை இரண்டு கிளைத்த, மூடிய முனையுள்ள குடற்பைகளாகப் பிரிகிறது."),
            h("uterus",.5f,.41f,"Coiled uterus","சுருண்ட கருப்பை",
                "The uterus occupies an anterior region; its loops must not be mistaken for intestinal branches.","முன்பகுதியில் கருப்பை சுருண்டுள்ளது; அதன் வளைவுகளை குடற்கிளைகளாகக் கருத வேண்டாம்."),
            h("ovary",.425f,.55f,"Single branched ovary","ஒற்றைக் கிளைத்த சூலகம்",
                "The branched ovary lies in front of the two successive testes.","கிளைத்த ஒற்றைச் சூலகம் ஒன்றன்பின் ஒன்றாக உள்ள இரண்டு விந்தகங்களுக்கு முன்புறம் உள்ளது."),
            h("vitellaria",.692f,.49f,"Lateral vitellaria","பக்கவாட்டு மஞ்சள்கருச் சுரப்பிகள்",
                "Vitelline follicles lie in lateral fields and supply material for eggs.","மஞ்சள்கருச் சுரப்பிகள் பக்கவாட்டில் பரவி முட்டை உருவாக்கத்திற்கான பொருட்களை வழங்குகின்றன."),
            h("testis-anterior",.51f,.686f,"Anterior branched testis","முன்புறக் கிளைத்த விந்தகம்",
                "The anterior of two highly branched testes occupies the posterior half.","கிளைத்த இரண்டு விந்தகங்களில் முன்புற விந்தகம் உடலின் பின்பாதியில் உள்ளது."),
            h("testis-posterior",.505f,.819f,"Posterior branched testis","பின்புறக் கிளைத்த விந்தகம்",
                "The second branched testis lies behind the first, not beside it.","இரண்டாவது கிளைத்த விந்தகம் முதல் விந்தகத்திற்குப் பின்னால் உள்ளது; அருகருகே இல்லை.")
        ),
        "type_studies/fasciola/life-cycle" to listOf(
            h("egg",.5f,.155f,"Unembryonated egg","கருவளராத முட்டை","Eggs pass in faeces before embryonation in fresh water.","மலத்துடன் வெளியேறும் முட்டைகள் நன்னீரில் கருவளர்ச்சி அடைகின்றன."),
            h("miracidium",.79f,.32f,"Ciliated miracidium","குறுஇழை மிராசிடியம்","This free-swimming larva enters a suitable lymnaeid snail.","சுதந்திரமாக நீந்தும் இளவுயிரி லிம்னேயிட் நத்தைக்குள் நுழைகிறது."),
            h("snail",.78f,.69f,"Intermediate snail","இடைப்புரவலன் நத்தை","Sporocyst, redial and cercarial development occur inside the snail.","ஸ்போரோசிஸ்ட், ரீடியா, செர்கேரியா வளர்ச்சி நத்தைக்குள் நடைபெறுகிறது."),
            h("cercaria",.5f,.86f,"Tailed cercaria","வாலுள்ள செர்கேரியா","Cercariae leave the snail and encyst on aquatic plants.","செர்கேரியா நத்தையை விட்டு வெளியேறி நீர்ச்செடிகளில் உறைகிறது."),
            h("metacercaria",.2f,.69f,"Encysted metacercaria","உறைந்த மெட்டாசெர்கேரியா","This is the infective stage for a grazing mammal or human.","மேயும் பாலூட்டிக்கும் மனிதருக்கும் தொற்றும் நிலை இதுவாகும்."),
            h("definitive",.2f,.32f,"Definitive host","இறுதிப் புரவலன்","Adult flukes mature in bile ducts after liver migration.","கல்லீரல் வழியாக இடம்பெயர்ந்த பின் முதிர்ந்த புழுக்கள் பித்தநாளங்களில் வாழ்கின்றன.")
        ),
        "type_studies/ascaris/male-female-external" to listOf(
            h("female-mouth",.30f,.12f,"Female: three-lipped mouth","பெண்: மூன்று உதடுள்ள வாய்","The anterior mouth has three lips.","முன்முனை வாயில் மூன்று உதடுகள் உள்ளன."),
            h("female-vulva",.310f,.386f,"Female vulva","பெண் வல்வா","The vulva lies ventrally near the boundary of the anterior and middle thirds.","முன் மற்றும் நடு மூன்றில் ஒரு பகுதிகள் சந்திக்கும் இடத்தில் வல்வா வயிற்றுப்பக்கத்தில் உள்ளது."),
            h("female-anus",.32f,.905f,"Female anus","பெண் ஆசனவாய்","The anal opening is just anterior to the straight posterior tip.","நேரான பின்முனைக்கு சற்றுமுன் ஆசனவாய் உள்ளது."),
            h("male-mouth",.67f,.18f,"Male: mouth","ஆண்: வாய்","The male is shorter but has the same three-lipped anterior mouth.","ஆண் குறுகியதாக இருந்தாலும் முன்புறத்தில் மூன்று உதடுள்ள வாயே உள்ளது."),
            h("male-cloaca",.744f,.817f,"Male cloaca and spicules","ஆண் கிளோயகா மற்றும் இணைவு முளைகள்",
                "The male tail curves ventrally; a subterminal cloaca and copulatory spicules occur near the curve.","ஆண் வால் வயிற்றுப்புறமாக வளைகிறது; அதன் அருகே கிளோயகாவும் இணைவு முளைகளும் உள்ளன.")
        ),
        "type_studies/ascaris/female-internal" to listOf(
            h("intestine",.49f,.62f,"Intestine","குடல்","A relatively straight alimentary tube is distinct from reproductive coils.","ஓரளவு நேரான குடல் இனப்பெருக்கச் சுருள்களிலிருந்து தனியாக உள்ளது."),
            h("ovary",.418f,.50f,"Coiled tubular ovary","சுருண்ட குழாய் வடிவச் சூலகம்","The ovary is a thin coiled tube, not a bunch of large eggs.","சூலகம் முட்டைக் கொத்தல்ல; மெல்லிய சுருண்ட குழாய்."),
            h("uterus",.416f,.77f,"Paired uteri","இரட்டை கருப்பைகள்","The paired broad reproductive tubes carry developing eggs.","இரட்டை அகன்ற இனப்பெருக்கக் குழாய்கள் வளரும் முட்டைகளை ஏந்துகின்றன."),
            h("vulva",.48f,.367f,"Vulva","வல்வா","The vagina opens via the vulva in the anterior third.","யோனி முன் மூன்றில் ஒரு பகுதியில் வல்வா வழியாகத் திறக்கிறது."),
            h("anus",.48f,.895f,"Anus","ஆசனவாய்","This complete gut terminates in a subterminal anus.","முழுமையான உணவுப்பாதை பின்முனைக்கு சற்றுமுன் ஆசனவாயில் முடிகிறது.")
        ),
        "type_studies/ascaris/life-cycle" to listOf(
            h("unembryonated",.50f,.15f,"Freshly passed egg","புதிதாக வெளியேறும் முட்டை","Fresh faecal eggs are not yet infective.","புதிதாக மலத்துடன் வெளியேறும் முட்டைகள் தொற்றுத்திறன் பெறவில்லை."),
            h("embryonated",.79f,.30f,"Embryonated soil egg","மண்ணில் கருவளர்ந்த முட்டை","Eggs mature in suitable soil to the infective embryonated stage.","ஏற்ற மண்ணில் முட்டைகள் கருவளர்ந்து தொற்றும் நிலையை அடைகின்றன."),
            h("ingestion",.79f,.68f,"Egg ingestion","முட்டை உட்கொள்ளுதல்","Humans acquire infection by swallowing infective embryonated eggs.","கருவளர்ந்த தொற்றுமுட்டைகளை விழுங்குவதால் மனிதருக்குத் தொற்று ஏற்படுகிறது."),
            h("hatch",.50f,.86f,"Intestinal hatching","குடலில் இளவுயிரி வெளிவருதல்","Larvae hatch, penetrate gut mucosa and enter circulation.","இளவுயிரிகள் குடலில் வெளிவந்து சளிச்சவ்வைத் துளைத்து இரத்த ஓட்டத்தில் செல்கின்றன."),
            h("lungs",.20f,.68f,"Larvae in lungs","நுரையீரலில் இளவுயிரிகள்","The larvae reach alveoli, then ascend the airways and are swallowed.","இளவுயிரிகள் காற்றுப்பைகளை அடைந்து சுவாசப்பாதை வழி மேலேறி மீண்டும் விழுங்கப்படுகின்றன."),
            h("adult",.20f,.30f,"Adults in small intestine","சிறுகுடலில் முதிர்ந்த புழுக்கள்","Male and female adults reproduce in the small intestine.","ஆண், பெண் முதிர்ந்த புழுக்கள் சிறுகுடலில் இனப்பெருக்கம் செய்கின்றன.")
        )
    )
    fun forStem(stem:String):List<UnitIIIHotspot> = byStem[stem].orEmpty()
    fun validate() {
        require(byStem.size == 6)
        for ((stem,items) in byStem) {
            require(items.isNotEmpty() && stem.startsWith("type_studies/"))
            require(items.map { it.id }.toSet().size == items.size)
            require(items.all { it.x in 0.05f..0.95f && it.y in 0.04f..0.96f })
        }
    }
}
