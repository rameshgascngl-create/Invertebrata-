package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import kotlin.math.pow

/**
 * Named native vector geometry, not an imported pencil plate or an
 * identified specimen photograph. External generalised penaeid lateral view.
 * The actual 19 appendage pairs are explained in the neighbouring explorer:
 * not every paired structure is visible in a lateral silhouette.
 *
 * Both image taps and accessible text buttons identify the SAME named organs.
 * Never attach source-image pixel coordinates to this pedagogical schematic.
 */
internal data class PenaeusExternalPart(
    val id: String,
    val name: BilingualText,
    val explanation: BilingualText,
    val anchor: Offset,
    val radius: Float = 54f,
)
internal object PenaeusExternalAtlas {
    private fun bi(en: String, ta: String) = BilingualText(en, ta)
    val parts = listOf(
        PenaeusExternalPart("rostrum",bi("Rostrum","ரோஸ்ட்ரம்"),
            bi("The anterior extension of the carapace bears teeth used in taxonomic descriptions; tooth counts require a verified species specimen.",
               "முன்புற புறக்கவச நீட்சியில் பற்கள் காணப்படும்; பற்களின் எண்ணிக்கையை இனமறிந்த மாதிரியில் உறுதிப்படுத்த வேண்டும்."),Offset(53f,195f),54f),
        PenaeusExternalPart("eyes",bi("Stalked compound eye","காம்புள்ள கூட்டுக்கண்"),
            bi("A stalked compound eye is an external sensory organ. The two eyes are paired, although one is most evident in lateral view.",
               "காம்புகளில் அமைந்த கூட்டுக்கண்கள் பார்வை உணர்வுக்குரியவை; பக்கப் பார்வையில் ஒரு கண் தெளிவாகத் தெரியும்."),Offset(130f,164f),43f),
        PenaeusExternalPart("antennules",bi("Antennules","முதல் உணர்கொம்புகள்"),
            bi("The first antennal pair carries chemosensory and mechanosensory structures; it is distinct from the longer second antenna.",
               "முதல் உணர்கொம்புகள் வேதியியல் மற்றும் இயந்திர உணர்வில் பங்கெடுக்கின்றன; நீளமான இரண்டாம் உணர்கொம்புகளிலிருந்து வேறுபடும்."),Offset(94f,132f),46f),
        PenaeusExternalPart("antennae",bi("Antennae","இரண்டாம் உணர்கொம்புகள்"),
            bi("The second antennae are paired head appendages, distinct from the antennules and walking legs.",
               "இரண்டாம் உணர்கொம்புகள் தலையின் இணை உறுப்புகள்; முதலுணர்கொம்புகளும் நடைக்கால்களும் அல்ல."),Offset(73f,95f),57f),
        PenaeusExternalPart("carapace",bi("Carapace over cephalothorax","தலைமார்பின் புறக்கவசம்"),
            bi("A carapace covers the fused head and thorax, including the branchial region. It is not a separate abdominal segment.",
               "இணைந்த தலை மற்றும் மார்புப் பகுதியை புறக்கவசம் மூடுகிறது; செவுள் பகுதியும் இதனுள் அடங்கும். இது வயிற்றுக் கண்டமல்ல."),Offset(262f,247f),116f),
        PenaeusExternalPart("abdomen",bi("Six-segmented abdomen","ஆறு கண்ட வயிற்றுப்பகுதி"),
            bi("The flexible six-segmented muscular abdomen powers swimming and rapid escape flexions. This is separate from the cephalothorax.",
               "ஆறு கண்டங்களுடைய தசைமிக்க வயிற்றுப்பகுதி நீந்துதலுக்கும் வேகமான பின்செலுத்தலுக்கும் உதவுகிறது."),Offset(606f,246f),122f),
        PenaeusExternalPart("pereiopods",bi("Five pairs of pereiopods","ஐந்து ஜோடி நடைக்கால்கள்"),
            bi("Five thoracic walking-leg pairs attach to the cephalothorax. Some anterior pairs bear small chelae; do not replace them with giant Macrobrachium claws.",
               "தலைமார்பில் ஐந்து ஜோடி நடைக்கால்கள் உள்ளன. சில முன் ஜோடிகளில் சிறு பிடிப்புகள் உள்ளன; Macrobrachium-இன் பெரிய பிடிக்கால்களை இங்கு சேர்க்கக் கூடாது."),Offset(294f,387f),110f),
        PenaeusExternalPart("pleopods",bi("Five pairs of pleopods","ஐந்து ஜோடி நீந்துகால்கள்"),
            bi("Pleopods arise from the first five abdominal segments. They aid swimming; male first pleopods are associated with the petasma.",
               "முதல் ஐந்து வயிற்றுக் கண்டங்களில் நீந்துகால்கள் உள்ளன. ஆணில் முதல் ஜோடி பெட்டாஸ்மா அமைப்புடன் தொடர்புடையது."),Offset(633f,345f),115f),
        PenaeusExternalPart("uropods",bi("Paired uropods","இணை யூரோபாட்கள்"),
            bi("The paired uropods arise from the sixth abdominal segment and form the lateral parts of the tail fan.",
               "ஆறாவது வயிற்றுக் கண்டத்தின் இணை யூரோபாட்கள் வால் விசிறியின் இருபுறங்களையும் உருவாக்குகின்றன."),Offset(871f,267f),60f),
        PenaeusExternalPart("telson",bi("Unpaired telson","ஒற்றை டெல்சன்"),
            bi("The median telson completes the tail fan, but it is not a twentieth appendage pair.",
               "வால் விசிறியின் நடுவில் உள்ள டெல்சன் ஒற்றையானது; இருபதாவது இணைக்கால் ஜோடி அல்ல."),Offset(929f,253f),58f)
    )
    fun byId(id: String) = parts.firstOrNull { it.id == id } ?: parts.first { it.id == "carapace" }

    /** Tap regions are designed for this vector diagram, not for historical scans. */
    fun hit(x: Float, y: Float): String? =
        parts.map { it to ((x-it.anchor.x).pow(2)+(y-it.anchor.y).pow(2)) }
            .filter { (part, distance) -> distance <= part.radius * part.radius }
            .minByOrNull { it.second }?.first?.id
}

private val graphite = Color(0xFF304653)
private val shell = Color(0xFFE5E2D7)
private val selectFill = Color(0xFFB9E6D9)
private val limb = Color(0xFF687B79)

private fun polygon(vararg points: Pair<Float,Float>): Path = Path().apply {
    if(points.isNotEmpty()) {
        moveTo(points[0].first,points[0].second)
        for(i in 1 until points.size) lineTo(points[i].first,points[i].second)
        close()
    }
}
private fun pt(x: Number,y: Number) = x.toFloat() to y.toFloat()

@Composable
internal fun UnitIVPenaeusVectorAtlas(
    language: AppLanguage,
    selectedId: String,
    onSelected: (String) -> Unit,
) {
    val tamil = language == AppLanguage.TAMIL
    val selected = PenaeusExternalAtlas.byId(selectedId)
    val context = LocalContext.current
    val narrator = remember(context) { OfflineNarrator(context.applicationContext) }
    val voice by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }

    Card(Modifier.fillMaxWidth().testTag("u42-penaeus-vector-atlas")) {
        Column(Modifier.padding(14.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(if(tamil) "Penaeus — தொடுதிறன் வெளியமைப்பு வரைபடம்"
                else "Penaeus · interactive external morphology",
                style=MaterialTheme.typography.titleLarge,
                modifier=Modifier.testTag("u42-penaeus-atlas-heading"))
            Text(if(tamil)
                "மூலப் பென்சில் ஸ்கேன் அல்ல. அறிவியல் கற்பித்தலுக்கான கணினி வரைவுச் சுருக்கம்; தனிப்பட்ட இனத்தின் அடையாளப்படம் அல்ல."
                else "Original digital teaching schematic, NOT a pencil scan or verified species photograph. Lateral view; paired structures may be partly hidden.",
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.testTag("u42-penaeus-schematic-disclosure"))
            Canvas(
                modifier=Modifier.fillMaxWidth().aspectRatio(1000f/600f)
                    .background(Color(0xFFF7F4EC))
                    .border(1.dp, Color(0xFFC2C7BE))
                    .pointerInput(onSelected) {
                        detectTapGestures { tap ->
                            val x=tap.x/size.width*1000f
                            val y=tap.y/size.height*600f
                            PenaeusExternalAtlas.hit(x,y)?.let(onSelected)
                        }
                    }.semantics {
                        contentDescription = if(tamil)
                            "இறாலின் தொடக்கூடிய பக்கப்பார்வை வரைபடம். கீழுள்ள பெயர்ப்பொத்தான்களிலும் உறுப்பைத் தேர்ந்தெடுக்கலாம்."
                        else "Tappable lateral shrimp schematic. Structure buttons below provide an accessible alternative."
                    }.testTag("u42-penaeus-vector-canvas")
            ) {
                withTransform({ scale(size.width/1000f,size.height/600f, pivot=Offset.Zero) }) {
                    // All geometry is authored here: no downloaded or generated specimen is asserted.
                    val carapace=Path().apply {
                        moveTo(106f,204f)
                        cubicTo(165f,181f,312f,166f,412f,211f)
                        cubicTo(418f,261f,402f,306f,378f,329f)
                        cubicTo(295f,334f,176f,332f,108f,289f)
                        close()
                    }
                    drawPath(carapace,if(selected.id=="carapace")selectFill else shell)
                    drawPath(carapace,graphite,style=Stroke(4f))
                    // Rostrum is an anterior continuation of the carapace.
                    val rostrum=polygon(pt(107,205),pt(18,194),pt(86,216),pt(113,224))
                    drawPath(rostrum,if(selected.id=="rostrum")selectFill else shell)
                    drawPath(rostrum,graphite,style=Stroke(3f))
                    for(i in 0..3) {
                        val x=43f+i*14
                        drawLine(graphite,Offset(x,198f),Offset(x+5f,190f),2.2f)
                    }
                    // Eye and stalk.
                    drawLine(if(selected.id=="eyes")Color(0xFF00876C) else graphite,
                        Offset(131f,202f),Offset(129f,169f),6f)
                    drawCircle(if(selected.id=="eyes")Color(0xFF00876C) else graphite,
                        12f,Offset(129f,159f))
                    // Distinct first antennule and second antenna processes.
                    val a1=Path().apply {moveTo(100f,211f);cubicTo(66f,149f,49f,145f,22f,136f)}
                    val a2=Path().apply {moveTo(101f,209f);cubicTo(24f,94f,59f,52f,88f,17f)}
                    drawPath(a1,if(selected.id=="antennules")Color(0xFF00876C) else graphite,style=Stroke(3f))
                    drawPath(a2,if(selected.id=="antennae")Color(0xFF00876C) else graphite,style=Stroke(2.5f))
                    // Abdomen: SIX articulated segments; last narrows into the tail fan.
                    val xs=listOf(406f,483f,557f,628f,696f,761f,819f)
                    for(i in 0..5) {
                        val top=214f+i*3.1f
                        val bottom=317f-i*8.3f
                        val part=polygon(
                            pt(xs[i]-6,top),pt(xs[i+1]+6,top+4),
                            pt(xs[i+1]+6,bottom-9),pt(xs[i],bottom))
                        drawPath(part,if(selected.id=="abdomen")selectFill else shell)
                        drawPath(part,graphite,style=Stroke(3.1f))
                        drawLine(Color(0xFFBEC3B8),
                            Offset(xs[i]+15f,top+10f),Offset(xs[i]+15f,bottom-14f),1.4f)
                    }
                    // Thoracic legs (stylised lateral projections, FIVE visible roots).
                    for(i in 0..4) {
                        val root=Offset(205f+i*38f,315f)
                        val knee=Offset(root.x-13f+i*2,369f+(i%2)*12)
                        val tip=Offset(knee.x+24f,437f-(i%2)*9)
                        val col=if(selected.id=="pereiopods")Color(0xFF00876C) else graphite
                        drawLine(col,root,knee,4f)
                        drawLine(col,knee,tip,3f)
                    }
                    // Five pleopod silhouettes along abdominal segments 1–5.
                    for(i in 0..4) {
                        val x=445f+i*72f
                        val fin=Path().apply {
                            moveTo(x,291f-i*6f)
                            quadraticTo(x+35f,357f-i*3f,x+9f,379f-i*8f)
                            quadraticTo(x-10f,348f-i*4f,x,291f-i*6f)
                            close()
                        }
                        drawPath(fin,if(selected.id=="pleopods")selectFill else Color(0xFFD4DDD3))
                        drawPath(fin,if(selected.id=="pleopods")Color(0xFF00876C) else limb,style=Stroke(2.5f))
                    }
                    // Paired uropods flank the SINGLE median telson.
                    val upper=polygon(pt(814,237),pt(887,208),pt(960,220),pt(850,251))
                    val lower=polygon(pt(818,269),pt(882,307),pt(956,291),pt(846,259))
                    drawPath(upper,if(selected.id=="uropods")selectFill else Color(0xFFD1DCD4))
                    drawPath(lower,if(selected.id=="uropods")selectFill else Color(0xFFD1DCD4))
                    drawPath(upper,graphite,style=Stroke(3f));drawPath(lower,graphite,style=Stroke(3f))
                    val telson=polygon(pt(821,250),pt(932,253),pt(974,257),pt(833,270))
                    drawPath(telson,if(selected.id=="telson")selectFill else shell)
                    drawPath(telson,graphite,style=Stroke(3f))
                    // Selected named target: informative outline, not an invented pixel overlay.
                    drawCircle(Color(0xFF007F67),18f,selected.anchor,style=Stroke(4f))
                    drawCircle(Color(0xFF007F67),4f,selected.anchor)
                }
            }
            Text(if(tamil) "படத்தில் உறுப்பைத் தொடவும் அல்லது கீழுள்ள பொத்தானைத் தேர்ந்தெடுக்கவும்."
                else "Tap a structure in the diagram or choose an accessible label.",
                style=MaterialTheme.typography.bodySmall)
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement=Arrangement.spacedBy(6.dp)) {
                PenaeusExternalAtlas.parts.forEach { part ->
                    FilterChip(
                        selected=part.id==selected.id,
                        onClick={onSelected(part.id);narrator.stop()},
                        label={Text(part.name.value(language))},
                        modifier=Modifier.heightIn(min=48.dp).testTag("u42-penaeus-select-"+part.id)
                    )
                }
            }
            Text(selected.name.value(language),style=MaterialTheme.typography.titleMedium,
                modifier=Modifier.testTag("u42-penaeus-selected-name"))
            Text(selected.explanation.value(language),
                modifier=Modifier.testTag("u42-penaeus-selected-explanation"))
            OutlinedButton(
                onClick={narrator.speak(BilingualText(
                    selected.name.english+". "+selected.explanation.english,
                    selected.name.tamil+". "+selected.explanation.tamil),language)},
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                    .testTag("u42-penaeus-selected-speak")
            ) { Text(if(tamil)"உறுப்பின் விளக்கத்தைக் கேள்" else "Listen to this structure (offline)") }
            Text((if(tamil)"ஒலி நிலை: " else "Voice: ")+voice.name,
                style=MaterialTheme.typography.labelSmall)
        }
    }
}
