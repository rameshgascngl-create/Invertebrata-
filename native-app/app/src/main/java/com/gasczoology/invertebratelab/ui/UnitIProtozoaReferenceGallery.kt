package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage

/**
 * U1.2: Four INDEPENDENT source illustrations, not pencil-artwork acceptance.
 * The referenced image's content is never reinterpreted as verified anatomy.
 * No hotspot geometry is inferred from published illustration pixels.
 */
private data class ProtozoaReference(
    val id:String, val en:String, val ta:String,
    val enTeaching:String, val taTeaching:String,
    val enLimit:String, val taLimit:String, val credit:String, val source:String
)

private val references=listOf(
    ProtozoaReference("entamoeba","Entamoeba histolytica — trophozoite, cyst and transmission",
        "என்டமீபா ஹிஸ்டோலிட்டிகா — வளர் நிலை, சிஸ்ட், பரவும் வழி",
        "The trophozoite has one nucleus and moves by pseudopodia. A mature cyst usually has four nuclei; cyst ingestion is the principal infection route. A diagnostic image of an E. histolytica/E. dispar cyst cannot identify the species from morphology alone.",
        "வளர் நிலையில் ஓர் உட்கரு காணப்படும்; போலிக்கால்கள் மூலம் நகர்கிறது. முதிர்ந்த சிஸ்டில் பொதுவாக நான்கு உட்கருக்கள் காணப்படும். சிஸ்டை உட்கொள்வதன் மூலம் தொற்று பரவுகிறது. சிஸ்ட் வடிவத்தால் மட்டுமே E. histolytica மற்றும் E. dispar இனங்களைப் பிரித்தறிய இயலாது.",
        "Public-domain life-cycle artwork, not a verified hand-drawn anatomical master. Diagram labels and sequence need independent scientific and Tamil review.",
        "பொதுச்சொத்து வாழ்க்கைச் சுழற்சி விளக்கப்படம் மட்டுமே; சரிபார்க்கப்பட்ட பென்சில் உடலமைப்புப் படமல்ல. குறிகளும் வரிசையும் தனித்த மதிப்பாய்வுக்குட்பட்டவை.",
        "Mariana Ruiz Villarreal (LadyofHats) · public domain",
        "https://commons.wikimedia.org/wiki/File:Entamoeba_histolytica_life_cycle-en.svg"),
    ProtozoaReference("trypanosoma","Trypanosoma brucei — trypomastigote overview",
        "டிரிப்பனோசோமா புரூசை — டிரிப்போமாஸ்டிகோட் தோற்றம்",
        "Bloodstream trypomastigotes possess a nucleus, a kinetoplast, an undulating membrane and a flagellum. Tsetse flies (Glossina) transmit African trypanosomes. This picture depicts body form, NOT every stage of the vector cycle.",
        "இரத்த டிரிப்போமாஸ்டிகோட்டில் உட்கரு, கைனெட்டோபிளாஸ்ட், அலைவுச் சவ்வு மற்றும் கசையிழை காணப்படும். குளோசினா (ட்ஸெட்ஸெ) ஈக்கள் ஆப்பிரிக்க டிரிப்பனோசோம்களைப் பரப்புகின்றன. இங்கே உடல் வடிவம் மட்டுமே காட்டப்படுகிறது; முழு வாழ்க்கைச் சுழற்சி அல்ல.",
        "Author's zoological cartoon; stage-specific kinetoplast positions and real pencil master require expert review. Human-infective subspecies are not individually identified by this image.",
        "இது ஓர் விளக்க வரைபடம்; வாழ்க்கை நிலை சார்ந்த கைனெட்டோபிளாஸ்ட் இடமும் பென்சில் படமும் மதிப்பாய்வுக்குரியவை. மனிதரைத் தாக்கும் துணையினங்கள் தனித்தனியாகக் காட்டப்படவில்லை.",
        "Johannahoog · CC BY-SA 3.0", "https://commons.wikimedia.org/wiki/File:Trypanosoma_brucei.svg"),
    ProtozoaReference("leishmania","Leishmania spp. — intracellular amastigote",
        "லீஷ்மேனியா இனங்கள் — செல்லுக்குள்ளான அமாஸ்டிகோட்",
        "Intracellular amastigotes are observed in mammalian macrophages; promastigotes are the elongated flagellated forms associated with the sand fly vector. This source illustrates amastigotes ONLY and does not by itself establish Leishmania donovani species identity.",
        "பாலூட்டிகளின் மேக்ரோபேஜ் செல்களில் அமாஸ்டிகோட் நிலைகள் காணப்படும். மணல் ஈயுடன் தொடர்புடைய நீளமான கசையிழை உடைய நிலை புரோமாஸ்டிகோட் ஆகும். இந்தப் படம் அமாஸ்டிகோட்டை மட்டுமே காட்டுகிறது; Leishmania donovani என்ற இனத்தைத் தனியாக உறுதிப்படுத்தாது.",
        "NIAID reference illustration, not the promastigote-to-amastigote cycle or a species-verified pencil plate.",
        "இது NIAID விளக்கப்படம் மட்டுமே; முழு வாழ்க்கைச் சுழற்சியோ இனச் சான்றுடைய பென்சில் படமோ அல்ல.",
        "NIAID / Ryan Kissinger · US public domain",
        "https://commons.wikimedia.org/wiki/File:Leishmania_Intracellular_Amastigote_(NIH_BioArt_872_-_798227).png"),
    ProtozoaReference("plasmodium","Plasmodium — liver, blood and mosquito stages",
        "பிளாஸ்மோடியம் — கல்லீரல், இரத்தம், கொசு நிலைகள்",
        "An infected female Anopheles mosquito introduces sporozoites. Liver-stage replication precedes erythrocytic ring, trophozoite and schizont stages. Ingested gametocytes develop through fertilisation, ookinete, oocyst and sporozoites in the mosquito.",
        "தொற்றுடைய பெண் அனாபிலிஸ் கொசு ஸ்போரோசோயிட்டுகளை உட்செலுத்துகிறது. கல்லீரல் பெருக்கத்துக்குப் பின் இரத்தச் சிவப்பணுக்களில் வளைய, வளர் மற்றும் ஸ்கிசோன்ட் நிலைகள் ஏற்படுகின்றன. கொசுவில் கேமீட்டுகள் சேர்ந்து ஊகைனீட், ஊசிஸ்ட், ஸ்போரோசோயிட் நிலைகளாக வளர்கின்றன.",
        "Source life-cycle plate is based on P. falciparum. Do not infer a dormant liver hypnozoite for P. falciparum; species-specific diagnostic drawings remain outstanding.",
        "இப்படம் P. falciparum அடிப்படையிலானது; இவ்வினத்தில் உறக்க கல்லீரல் ஹிப்னோசோயிட் இருப்பதாகக் கருதக்கூடாது. இனத்திற்குரிய சோதனைப் படங்கள் இன்னும் நிலுவையில் உள்ளன.",
        "Sina Adl (2025) · CC BY 4.0", "https://commons.wikimedia.org/wiki/File:Malaria_Plasmodium_infection_cycle.png")
)

@Composable
internal fun UnitIProtozoaReferenceGallery(language:AppLanguage) {
    val tamil=language==AppLanguage.TAMIL
    var selected by rememberSaveable { mutableStateOf("entamoeba") }
    val ref=references.single { it.id==selected }
    var zoom by rememberSaveable(selected){ mutableFloatStateOf(1f) }
    var dx by rememberSaveable(selected){ mutableFloatStateOf(0f) }
    var dy by rememberSaveable(selected){ mutableFloatStateOf(0f) }
    var expanded by rememberSaveable(selected){ mutableStateOf(false) }
    Column(modifier=Modifier.fillMaxWidth().testTag("u1-protozoa-reference-gallery"),
        verticalArrangement=Arrangement.spacedBy(10.dp)) {
        Text(if(tamil) "ஒட்டுண்ணி ஒருசெல் உயிரினங்கள் — ஆதார விளக்கப்படங்கள்"
             else "Parasitic protozoa — referenced teaching illustrations",
            style=MaterialTheme.typography.titleLarge)
        Text(if(tamil) "நான்கு வெவ்வேறு ஆதாரப் படங்கள்; பென்சில் உடலமைப்பு ஆய்வுப் படங்கள் இன்னும் நிலுவையில் உள்ளன."
             else "Four distinct source illustrations. Independent pencil anatomy masters and expert review are still pending.",
            color=MaterialTheme.colorScheme.error,modifier=Modifier.testTag("u1-protozoa-reference-hold"))
        references.forEach { record ->
            OutlinedButton(onClick={selected=record.id},modifier=Modifier.fillMaxWidth()
                .heightIn(min=48.dp).testTag("u1-reference-select-"+record.id)) {
                Text(if(tamil)record.ta else record.en)
            }
        }
        Text(if(tamil)ref.ta else ref.en,style=MaterialTheme.typography.titleMedium,
            modifier=Modifier.testTag("u1-reference-title"))
        val image=rememberRequiredRasterBitmap("unit1/references/"+selected,pngOnly=true)
        if(image==null) {
            Text(if(tamil) "ஆதாரப் படம் கிடைக்கவில்லை — தவறான மாற்றுப் படம் காட்டப்படாது."
                 else "Reference image missing — no fabricated fallback.",
                color=MaterialTheme.colorScheme.error,
                modifier=Modifier.testTag("u1-reference-missing"))
        } else {
            Box(modifier=Modifier.fillMaxWidth().height(310.dp).clipToBounds()) {
                Image(image,contentDescription=if(tamil)ref.ta else ref.en,
                    contentScale=ContentScale.Fit,
                    modifier=Modifier.fillMaxSize()
                        .graphicsLayer { scaleX=zoom;scaleY=zoom;translationX=dx;translationY=dy }
                        .pointerInput(selected) {
                            detectTransformGestures { _,pan,scale,_ ->
                                zoom=(zoom*scale).coerceIn(1f,4f)
                                if(zoom<=1.01f) { dx=0f;dy=0f }
                                else {
                                    dx=(dx+pan.x).coerceIn(-size.width.toFloat(),size.width.toFloat())
                                    dy=(dy+pan.y).coerceIn(-size.height.toFloat(),size.height.toFloat())
                                }
                            }
                        }
                        .testTag("u1-reference-image-"+selected))
            }
            Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick={zoom=1f;dx=0f;dy=0f},
                    modifier=Modifier.weight(1f).heightIn(min=48.dp).testTag("u1-reference-reset")) {
                    Text(if(tamil)"மீட்டமை" else "Reset zoom")
                }
                Button(onClick={expanded=true},
                    modifier=Modifier.weight(1f).heightIn(min=48.dp).testTag("u1-reference-expand")) {
                    Text(if(tamil)"முழுத்திரை" else "Full screen")
                }
            }
            if(expanded) {
                ExpandedProtozoaReferenceImage(
                    bitmap=image,
                    title=if(tamil)ref.ta else ref.en,
                    language=language,
                    onClose={expanded=false},
                )
            }
        }
        Text(if(tamil)ref.taTeaching else ref.enTeaching,
            modifier=Modifier.testTag("u1-reference-explanation"))
        Text(if(tamil)ref.taLimit else ref.enLimit,color=MaterialTheme.colorScheme.error)
        Text(ref.credit,style=MaterialTheme.typography.bodySmall)
        Text(ref.source,style=MaterialTheme.typography.bodySmall)
    }
}

/** Full-viewport source raster only. This magnification does not imply expert approval. */
@Composable
private fun ExpandedProtozoaReferenceImage(
    bitmap:ImageBitmap, title:String, language:AppLanguage, onClose:()->Unit
) {
    val tamil=language==AppLanguage.TAMIL
    var scale by rememberSaveable { mutableFloatStateOf(1f) }
    var panX by rememberSaveable { mutableFloatStateOf(0f) }
    var panY by rememberSaveable { mutableFloatStateOf(0f) }
    Dialog(
        onDismissRequest=onClose,
        properties=DialogProperties(usePlatformDefaultWidth=false),
    ) {
        Surface(
            modifier=Modifier.fillMaxSize().testTag("u1-reference-fullscreen"),
            color=MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier=Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
                    .padding(horizontal=12.dp, vertical=6.dp),
                verticalArrangement=Arrangement.spacedBy(7.dp),
            ) {
                Text(title,style=MaterialTheme.typography.titleMedium,maxLines=2)
                Text(
                    if(tamil)"ஆதாரப் படம் மட்டுமே; உடலமைப்பு மதிப்பாய்வு நிலுவையில்."
                    else "Source illustration only; anatomical review remains open.",
                    color=MaterialTheme.colorScheme.error,
                    style=MaterialTheme.typography.labelMedium,
                )
                Box(modifier=Modifier.fillMaxWidth().weight(1f).clipToBounds()) {
                    Image(
                        bitmap=bitmap,
                        contentDescription=title,
                        contentScale=ContentScale.Fit,
                        modifier=Modifier.fillMaxSize()
                            .graphicsLayer {
                                scaleX=scale;scaleY=scale
                                translationX=panX;translationY=panY
                            }
                            .pointerInput(Unit) {
                                detectTransformGestures { _,pan,zoom,_ ->
                                    scale=(scale*zoom).coerceIn(1f,6f)
                                    if(scale<=1.01f){panX=0f;panY=0f}
                                    else {
                                        panX=(panX+pan.x).coerceIn(-size.width.toFloat(),size.width.toFloat())
                                        panY=(panY+pan.y).coerceIn(-size.height.toFloat(),size.height.toFloat())
                                    }
                                }
                            }
                            .testTag("u1-reference-expanded-bitmap"),
                    )
                }
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.spacedBy(7.dp),
                ) {
                    OutlinedButton(
                        onClick={scale=(scale/1.5f).coerceAtLeast(1f);if(scale==1f){panX=0f;panY=0f}},
                        modifier=Modifier.weight(1f).heightIn(min=48.dp)
                            .testTag("u1-reference-zoom-out"),
                    ) { Text("−") }
                    OutlinedButton(
                        onClick={scale=1f;panX=0f;panY=0f},
                        modifier=Modifier.weight(1f).heightIn(min=48.dp)
                            .testTag("u1-reference-zoom-reset"),
                    ) { Text("1×") }
                    OutlinedButton(
                        onClick={scale=(scale*1.5f).coerceAtMost(6f)},
                        modifier=Modifier.weight(1f).heightIn(min=48.dp)
                            .testTag("u1-reference-zoom-in"),
                    ) { Text("+") }
                    Button(
                        onClick=onClose,
                        modifier=Modifier.weight(2f).heightIn(min=48.dp)
                            .testTag("u1-reference-close"),
                    ) { Text(if(tamil)"மூடு" else "Close") }
                }
            }
        }
    }
}
