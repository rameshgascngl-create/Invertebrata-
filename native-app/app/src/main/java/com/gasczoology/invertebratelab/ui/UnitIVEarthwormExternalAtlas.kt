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
 * U4.3 ORIGINAL editable vector external-study *schematic*, not a scanned
 * specimen or an accepted pencil plate. Positions of genital pores use the
 * conventional Pheretima/Metaphire posthuma type-study account and require
 * an independent zoological check before becoming reference-plate labels.
 *
 * Segment counts are deliberately printed conceptually: only the first forty
 * segments are rendered, not the full length of an actual adult earthworm.
 * All hit regions originate from this vector's own coordinates, NEVER from
 * pixel calibration against a photograph.
 */
internal data class EarthwormExternalPart(
    val id: String,
    val label: BilingualText,
    val explanation: BilingualText,
    val point: Offset,
    val radius: Float = 40f,
)

internal object EarthwormExternalTeaching {
    private fun bi(en: String, ta: String) = BilingualText(en, ta)
    private const val x0 = 62f
    private const val width = 864f
    private const val segments = 40
    fun segmentCenter(n: Int) = x0 + (n-0.5f)*width/segments
    fun furrow(after: Int) = x0 + after*width/segments

    val structures = listOf(
        EarthwormExternalPart("prostomium",bi("Prostomium","புரோஸ்டோமியம்"),
            bi("A preoral lobe overhangs the anterior mouth; it is not counted as a true body segment.",
                "வாய்க்கு முன்புள்ள சிறு மடல் புரோஸ்டோமியம் ஆகும்; இது உண்மையான உடற்கண்டமாக எண்ணப்படாது."),
            Offset(43f,194f),34f),
        EarthwormExternalPart("peristomium",bi("Peristomium · segment I","பெரிஸ்டோமியம் · முதல் கண்டம்"),
            bi("The first true body segment surrounds the mouth. The mouth opens on the ventral/anterior region.",
                "முதல் உண்மையான உடற்கண்டம் வாயைச் சூழ்கிறது. வாய் முன்புற வயிற்றுப்பக்கத்தில் திறக்கிறது."),
            Offset(segmentCenter(1),467f),35f),
        EarthwormExternalPart("spermathecal",bi("Four pairs of spermathecal pores","நான்கு ஜோடி விந்துச் சேமிப்புத் துளைகள்"),
            bi("In the conventional posthuma account, paired spermathecal openings occur in intersegmental grooves 5/6 through 8/9. Paired markers here indicate schematic positions, not measured tissue coordinates.",
                "வழக்கமான posthuma விவரிப்பில் 5/6 முதல் 8/9 இடைக்கண்டப் பள்ளங்களில் நான்கு ஜோடி விந்துச் சேமிப்புத் துளைகள் உள்ளன. இங்கு குறிக்கப்படுவது விளக்கத்திற்கான இடங்கள் மட்டுமே."),
            Offset(furrow(7),470f),60f),
        EarthwormExternalPart("clitellum",bi("Clitellum · segments XIV–XVI","கிளிட்டெல்லம் · XIV–XVI கண்டங்கள்"),
            bi("The mature worm develops a glandular clitellar band across segments 14–16. Its secretions participate in cocoon formation. Perichaetine setae are absent from the clitellar band.",
                "முதிர்ந்த மண்புழுவில் XIV–XVI கண்டங்களில் சுரப்பித் தன்மை கொண்ட கிளிட்டெல்லம் உள்ளது. அதன் சுரப்பு கூடு உருவாக்கத்திற்கு உதவும்; இப்பகுதியில் சீட்டாக்கள் காணப்படுவதில்லை."),
            Offset(segmentCenter(15),196f),59f),
        EarthwormExternalPart("female-pore",bi("Median female genital pore · XIV","பெண் இனப்பெருக்கத் துளை · XIV"),
            bi("A single female genital opening is represented midventrally on segment 14. The diagram shows its segmental region rather than a histological pinpoint.",
                "14-ஆம் கண்டத்தின் நடுவயிற்றுப்புறத்தில் ஒரு பெண் இனப்பெருக்கத் துளை காட்டப்பட்டுள்ளது. இது திசுவியல் அளவீடு அல்ல; கண்டத்தின் பொதுவான இடத்தைக் குறிக்கிறது."),
            Offset(segmentCenter(14),468f),32f),
        EarthwormExternalPart("male-pores",bi("Paired male genital pores · XVIII","ஆண் இனப்பெருக்கத் துளைகள் · XVIII"),
            bi("The conventional male openings are paired on segment 18, posterior to the clitellum. The ventral-view dots are illustrative, not a specimen-based position measurement.",
                "18-ஆம் கண்டத்தில் கிளிட்டெல்லத்துக்குப் பின்னால் இணையான ஆண் இனப்பெருக்கத் துளைகள் காட்டப்பட்டுள்ளன. இப்புள்ளிகள் மாதிரி சார்ந்த அளவீடுகள் அல்ல."),
            Offset(segmentCenter(18),467f),36f),
        EarthwormExternalPart("annuli",bi("Metameres and intersegmental grooves","கண்டங்களும் இடைக்கண்டப் பள்ளங்களும்"),
            bi("Externally repeated segments form the metameric body plan. Grooves separate the visible units. This schematic displays forty example segments, not the complete adult count.",
                "மீண்டும் மீண்டும் அமைந்த உடற்கண்டங்கள் மெட்டாமெரிசத்தை விளக்குகின்றன. பள்ளங்கள் கண்டங்களைப் பிரிக்கின்றன. முழுமையான முதிர்ந்த புழுவை அல்ல, எடுத்துக்காட்டாக 40 கண்டங்களை மட்டும் படம் காட்டுகிறது."),
            Offset(segmentCenter(27),195f),55f),
        EarthwormExternalPart("setae",bi("Perichaetine setae","சுற்றமைந்த சீட்டாக்கள்"),
            bi("Short chitinous setae project around most body segments and aid traction. The first, last and clitellar segments are not presented as setose in this teaching view.",
                "பெரும்பாலான கண்டங்களின் சுற்றிலும் சிறிய கைட்டின் சீட்டாக்கள் இருந்து நிலத்தைப் பற்ற உதவுகின்றன. முதல், கடைசி, கிளிட்டெல்லம் உள்ள கண்டங்கள் இவ்வரைபடத்தில் சீட்டாக்களுடன் காட்டப்படவில்லை."),
            Offset(segmentCenter(26),237f),54f),
        EarthwormExternalPart("anus",bi("Posterior anus","பின்புற ஆசனவாய்"),
            bi("The terminal anus opens at the posterior end. This drawing ends after forty illustrative segments and is not a full adult somite count.",
                "உடலின் பின்முனையில் ஆசனவாய் திறக்கிறது. இந்தப் படம் 40 விளக்கக் கண்டங்களுடன் நிறைவதால் முழு முதிர்ந்த புழுவின் கண்ட எண்ணிக்கையைப் பிரதிபலிக்காது."),
            Offset(933f,194f),35f),
    )
    fun get(id: String) = structures.firstOrNull { it.id==id } ?: structures.first { it.id=="clitellum" }
    fun hit(x:Float,y:Float) = structures
        .map { it to (x-it.point.x).pow(2)+(y-it.point.y).pow(2) }
        .filter { (part,dist) -> dist <= part.radius*part.radius }
        .minByOrNull { it.second }?.first?.id
}

private val earthwormInk = Color(0xFF463B3A)
private val earthwormBand = Color(0xFFD6AC8E)
private val earthwormBody = Color(0xFFDCBFC0)
private val earthwormAccent = Color(0xFF007C78)

private fun wormRadius(t:Float):Float = when {
    t < .045f -> 14f+22f*t/.045f
    t > .90f -> 12f+24f*(1f-t)/.10f
    else -> 36f+3f*(1f-kotlin.math.abs(t-.46f))
}

private fun wormSilhouette(y:Float): Path = Path().apply {
    val left=62f;val width=864f
    moveTo(left,y-wormRadius(0f))
    for(i in 1..160) {
        val t=i/160f
        lineTo(left+t*width,y-wormRadius(t))
    }
    for(i in 160 downTo 0) {
        val t=i/160f
        lineTo(left+t*width,y+wormRadius(t))
    }
    close()
}

@Composable
internal fun UnitIVEarthwormExternalAtlas(
    language:AppLanguage,
    selectedId:String,
    onSelected:(String)->Unit,
) {
    val tamil=language==AppLanguage.TAMIL
    val selected=EarthwormExternalTeaching.get(selectedId)
    val context=LocalContext.current
    val narrator=remember(context){OfflineNarrator(context.applicationContext)}
    val voice by narrator.phase.collectAsState()
    DisposableEffect(narrator){onDispose{narrator.close()}}
    Card(Modifier.fillMaxWidth().testTag("u43-earthworm-external-atlas")) {
        Column(Modifier.padding(14.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(if(tamil)"மண்புழு — ஊடாடும் வெளியமைப்பியல் வரைபடம்"
                else "Earthworm · interactive external morphology",
                style=MaterialTheme.typography.titleLarge,
                modifier=Modifier.testTag("u43-earthworm-atlas-heading"))
            Text(if(tamil)
                "டிஜிட்டல் கற்பித்தல் வரைபடம் மட்டுமே; உண்மை பென்சில் ஸ்கேன் அல்ல. 40 எடுத்துக்காட்டுக் கண்டங்கள்; மேலே முதுகுப்புறம், கீழே வயிற்றுப்புறம். இனச் சிறப்புக் குறியீடுகளுக்கு அறிவியல் மறுஆய்வு தேவை."
                else "Original vector teaching schematic, not a specimen scan. Forty example segments; dorsal view above and schematic ventral view below. Species-specific pore positions await independent zoological approval.",
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.testTag("u43-earthworm-schematic-notice"))
            Text(if(tamil)"முதுகுப்புறம் (குறியீட்டு வெளிப்புற வடிவம்)" else "Dorsal view · segment boundaries and clitellum",
                style=MaterialTheme.typography.labelMedium)
            Canvas(Modifier.fillMaxWidth().aspectRatio(1000f/640f)
                .background(Color(0xFFF8F5EF))
                .border(1.dp,Color(0xFFC4B9AF))
                .pointerInput(onSelected) {
                    detectTapGestures {tap ->
                        EarthwormExternalTeaching.hit(
                            tap.x/size.width*1000f,tap.y/size.height*640f)?.let(onSelected)
                    }
                }.semantics {
                    contentDescription=if(tamil)
                        "வெளியமைப்பு படம். அணுகக்கூடிய உறுப்புப் பொத்தான்கள் கீழே வழங்கப்பட்டுள்ளன."
                        else "External earthworm morphology. Accessible organ buttons follow."
                }.testTag("u43-earthworm-canvas")
            ){
                withTransform({scale(size.width/1000f,size.height/640f,pivot=Offset.Zero)}) {
                    val bandStart=EarthwormExternalTeaching.furrow(13)
                    val bandEnd=EarthwormExternalTeaching.furrow(16)
                    // Both dorsal and ventral silhouettes are drawn independently.
                    for(centerY in listOf(196f,470f)) {
                        val outline=wormSilhouette(centerY)
                        drawPath(outline,earthwormBody)
                        drawPath(outline,earthwormInk,style=Stroke(3.4f))
                        // Regular external metameres: 40 are intentionally illustrative.
                        for(n in 1 until 40) {
                            val x=EarthwormExternalTeaching.furrow(n)
                            val rad=wormRadius(n/40f)
                            val groove=Path().apply {
                                moveTo(x,centerY-rad+1f)
                                quadraticTo(x+4.5f,centerY,x,centerY+rad-1f)
                            }
                            drawPath(groove,earthwormInk.copy(alpha=.50f),style=Stroke(1.9f))
                        }
                        val band=Path().apply {
                            moveTo(bandStart,centerY-39f)
                            lineTo(bandEnd,centerY-39f)
                            lineTo(bandEnd,centerY+39f)
                            lineTo(bandStart,centerY+39f)
                            close()
                        }
                        // Draw a true transverse clitellum region, not a free-floating label.
                        drawPath(band,if(selected.id=="clitellum")Color(0xFF9DE1C7) else earthwormBand)
                        drawLine(earthwormInk,Offset(bandStart,centerY-36f),Offset(bandStart,centerY+36f),2.3f)
                        drawLine(earthwormInk,Offset(bandEnd,centerY-36f),Offset(bandEnd,centerY+36f),2.3f)
                        // Perichaetine setae are shown as discrete lateral short strokes
                        // for ordinary segments, not as a continuous ciliary fringe.
                        for(n in 2 until 40) {
                            if(n in 14..16)continue
                            val x=EarthwormExternalTeaching.segmentCenter(n)
                            val r=wormRadius((n-.5f)/40f)
                            val col=if(selected.id=="setae")earthwormAccent else earthwormInk
                            drawLine(col,Offset(x,centerY+r-3f),Offset(x+2.5f,centerY+r+8f),2f)
                            drawLine(col,Offset(x+4f,centerY-r+3f),Offset(x+7f,centerY-r-7f),1.5f)
                        }
                        // Prostomium; a pre-segmental extension, not segment I.
                        val prost=Path().apply{
                            moveTo(62f,centerY-15f)
                            quadraticTo(30f,centerY-20f,29f,centerY)
                            quadraticTo(29f,centerY+13f,62f,centerY+14f)
                            close()
                        }
                        drawPath(prost,if(selected.id=="prostomium")Color(0xFF9DE1C7) else earthwormBand)
                        drawPath(prost,earthwormInk,style=Stroke(2.7f))
                        // Posterior terminal opening is not a separate complete segment.
                        drawCircle(if(selected.id=="anus")earthwormAccent else earthwormInk,
                            5f,Offset(925f,centerY))
                    }
                    // Ventral view: represent genital apertures at their segmental regions.
                    // The marks are NOT copied from any photographed specimen.
                    for(groove in 5..8) {
                        val x=EarthwormExternalTeaching.furrow(groove)
                        for(y in listOf(460f,480f)) {
                            drawCircle(if(selected.id=="spermathecal")earthwormAccent else earthwormInk,
                                4.0f,Offset(x,y))
                        }
                    }
                    drawCircle(if(selected.id=="female-pore")earthwormAccent else earthwormInk,
                        6.0f,Offset(EarthwormExternalTeaching.segmentCenter(14),470f))
                    for(y in listOf(459f,481f)){
                        drawCircle(if(selected.id=="male-pores")earthwormAccent else earthwormInk,
                            6.1f,Offset(EarthwormExternalTeaching.segmentCenter(18),y))
                    }
                    // Highlight ring matches the structure selection and voice content.
                    drawCircle(earthwormAccent,16f,selected.point,style=Stroke(4f))
                    drawCircle(earthwormAccent,4f,selected.point)
                }
            }
            Text(if(tamil)"மேல்: முதுகுப்புறம். கீழ்: வயிற்றுப்புறத் துளைகள் — குறியீட்டு இடங்கள்."
                else "Upper: dorsal. Lower: ventral, with schematic genital-pore positions.",
                style=MaterialTheme.typography.bodySmall)
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement=Arrangement.spacedBy(8.dp)){
                EarthwormExternalTeaching.structures.forEach {part ->
                    FilterChip(selected=part.id==selected.id,
                        onClick={onSelected(part.id);narrator.stop()},
                        label={Text(part.label.value(language))},
                        modifier=Modifier.heightIn(min=48.dp)
                            .testTag("u43-earthworm-select-"+part.id))
                }
            }
            Text(selected.label.value(language),style=MaterialTheme.typography.titleMedium,
                modifier=Modifier.testTag("u43-earthworm-selected-name"))
            Text(selected.explanation.value(language),
                modifier=Modifier.testTag("u43-earthworm-explanation"))
            OutlinedButton(onClick={narrator.speak(
                BilingualText(
                    selected.label.english+". "+selected.explanation.english,
                    selected.label.tamil+". "+selected.explanation.tamil
                ),language)},
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                    .testTag("u43-earthworm-speak")){
                Text(if(tamil)"இந்த அமைப்பின் விளக்கத்தைக் கேள்" else "Listen to structure explanation")
            }
            Text((if(tamil)"ஒலி நிலை: " else "Voice: ")+voice.name,
                style=MaterialTheme.typography.labelSmall)
        }
    }
}
