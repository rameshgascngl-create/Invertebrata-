package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.gasczoology.invertebratelab.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import android.graphics.Paint as AndroidPaint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.UnitIVNereisLessons
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * U4.4: EIGHT STATIC, HAND-AUTHORED VECTOR PENCIL-STYLE TEACHING SKETCHES.
 * Their on-screen Compose Canvas output is a drawing, NOT a specimen photo,
 * historical plate or signed-off zoological master.
 *
 * No click, pointer, hotspot, structure-selection or simulation controls.
 * Each fixed numbered leader points to a depicted anatomical structure;
 * labels are rendered in ordinary accessible Compose Text under each drawing,
 * in English on the first line and Tamil on the second.
 * Tamil nomenclature is PROVISIONAL until matched to the college manual.
 *
 * Original graphite scans in the user's references cannot be falsely
 * represented as having been incorporated or cleared for redistribution.
 */
internal data class NereisPlateStructure(
    val english:String,val tamil:String,val x:Float,val y:Float,val side:Int
)
private val pencilBlack=Color(0xFF333432)
private val pencilGray=Color(0xFFB2B3AD)
private val paper=Color(0xFFFAF9F5)
private fun p(x:Float,y:Float)=Offset(x,y)
private fun DrawScope.seg(x:Float,y:Float,x2:Float,y2:Float,weight:Float=2.3f,color:Color=pencilBlack) =
    drawLine(color,p(x,y),p(x2,y2),weight)
private fun DrawScope.curve(vararg coords:Float,width:Float=2.5f,color:Color=pencilBlack) {
    if(coords.size<4 || coords.size%2!=0)return
    val path=Path().apply {
        moveTo(coords[0],coords[1])
        var j=2
        while(j+1<coords.size){lineTo(coords[j],coords[j+1]);j+=2}
    }
    drawPath(path,color,style=Stroke(width))
}
private fun DrawScope.ring(x:Float,y:Float,r:Float,width:Float=2.2f,color:Color=pencilBlack)=
    drawCircle(color,r,p(x,y),style=Stroke(width))
private fun DrawScope.inkDot(x:Float,y:Float,r:Float=5f)=drawCircle(pencilBlack,r,p(x,y))
private fun DrawScope.ellipse(x:Float,y:Float,rx:Float,ry:Float,width:Float=2.6f) =
    drawOval(pencilBlack,Offset(x-rx,y-ry),androidx.compose.ui.geometry.Size(rx*2,ry*2),style=Stroke(width))
private fun DrawScope.body(center:Float=405f,top:Float=70f,tail:Float=540f, count:Int=19, outlineOnly:Boolean=false) {
    val step=(tail-top)/count
    for (n in 0 until count) {
        val y=top+n*step
        val rr=(if(n<2)57f else if(n>count-5)56f-(n-count+5)*7f else 64f)
        ellipse(center,y+step*.5f,rr,step*.60f,1.8f)
        if(!outlineOnly && n>1 && n<count-2) {
            for(side in listOf(-1f,1f)){
                val x=center+side*(rr+8f)
                val yy=y+step*.48f
                curve(center+side*rr*.8f,yy-5f, x,yy, x+side*17f,yy-8f,width=1.8f)
                for(j in 0..4)seg(x+side*7f,yy-4f,x+side*(21+j*4f),yy-(16+j*2f),1f)
            }
        }
    }
    // Head: palps, prostomial antennae and paired ocular dots.
    ellipse(center,top-7f,59f,32f)
    for(side in listOf(-1f,1f)) {
        curve(center+side*17f,top-31f,center+side*27f,top-70f,center+side*37f,top-91f,width=3f)
        curve(center+side*43f,top-12f,center+side*82f,top-63f,center+side*100f,top-65f,width=3f)
    }
    for(x in listOf(center-21f,center+21f)) {
        inkDot(x,top-12f,5f);inkDot(x,top-2f,4f)
    }
    // terminal pygidium and paired anal cirri
    ellipse(center,tail+6f,20f,17f)
    for(side in listOf(-1f,1f))
        curve(center+side*9f,tail+12f,center+side*29f,tail+43f,center+side*35f,tail+60f,width=2f)
    inkDot(center,tail+15f,2.8f)
}
private fun DrawScope.head() {
    val cx=390f;val cy=300f
    ellipse(cx,cy,110f,85f)
    // Upper frontal prostomium and posterior peristomial margin
    curve(283f,cy+35f,290f,cy+78f,342f,cy+105f,420f,cy+108f,497f,cy+61f,width=3f)
    for(side in listOf(-1f,1f)) {
        curve(cx+side*27f,cy-70f,cx+side*32f,cy-135f,cx+side*49f,cy-173f,width=4f)
        curve(cx+side*70f,cy-57f,cx+side*105f,cy-125f,cx+side*125f,cy-138f,width=3f)
        curve(cx+side*93f,cy+32f,cx+side*160f,cy-36f,cx+side*184f,cy-92f,width=3f)
        curve(cx+side*99f,cy+46f,cx+side*170f,cy+15f,cx+side*197f,cy+30f,width=2.8f)
        curve(cx+side*93f,cy+68f,cx+side*155f,cy+87f,cx+side*181f,cy+125f,width=2.8f)
        ring(cx+side*33f,cy-24f,8f);ring(cx+side*39f,cy+4f,6f)
    }
    // paired palps represented as shorter prostomial projections
    for(side in listOf(-1f,1f)) {
        curve(cx+side*68f,cy+17f,cx+side*118f,cy+57f,cx+side*127f,cy+83f,width=4f)
    }
}
private fun DrawScope.parapodium() {
    // Dorsal notopodial and ventral neuropodial rami, distinct acicula.
    val not=Path().apply {
        moveTo(270f,325f);quadraticTo(325f,193f,478f,165f)
        quadraticTo(566f,160f,604f,195f)
        quadraticTo(515f,282f,381f,341f);close()
    }
    val neu=Path().apply {
        moveTo(270f,322f);quadraticTo(350f,315f,472f,341f)
        quadraticTo(565f,365f,604f,421f)
        quadraticTo(427f,474f,271f,360f);close()
    }
    drawPath(not,Color(0xFFF4F3EE));drawPath(not,pencilBlack,style=Stroke(3.5f))
    drawPath(neu,Color(0xFFF4F3EE));drawPath(neu,pencilBlack,style=Stroke(3.5f))
    // support acicula in both rami, not separate external chaetae
    curve(268f,328f,392f,295f,526f,208f,width=6f)
    curve(271f,340f,397f,358f,535f,414f,width=5f)
    curve(505f,180f,532f,109f,544f,75f,width=3f)
    curve(515f,440f,538f,493f,564f,526f,width=3f)
    for(i in 0..11) {
        val d=i-6f
        seg(535f,242f+d*3.8f,678f,207f+d*10f,1.35f)
        seg(532f,405f+d*2.0f,688f,401f+d*9.0f,1.35f)
    }
    // lobes and ligules within each ramus
    curve(385f,260f,452f,225f,546f,203f,width=1.4f,color=pencilGray)
    curve(390f,382f,451f,412f,537f,421f,width=1.4f,color=pencilGray)
}
private fun DrawScope.gut() {
    body(outlineOnly=true)
    ellipse(405f,140f,42f,53f,2.7f)
    // paired anterior jaws in the eversible pharynx
    for(side in listOf(-1f,1f)) {
        val x=405f+side*15f
        curve(x,116f,x+side*11f,132f,x,152f,width=3f)
    }
    curve(405f,180f,405f,260f,width=12f)
    // Tubular intestine: real continuity to posterior anus, not crop/gizzard.
    val intestinal=Path().apply {
        moveTo(391f,257f);cubicTo(381f,334f,389f,413f,395f,541f)
        moveTo(419f,257f);cubicTo(430f,334f,420f,429f,414f,541f)
    }
    drawPath(intestinal,pencilBlack,style=Stroke(3f))
    for(i in 0..21) {
        val y=275f+i*11.5f
        seg(392f,y,417f,y+4f,.8f,pencilGray)
    }
}
private fun DrawScope.circulation() {
    body(outlineOnly=true)
    // Diagram explicitly projects dorsal and ventral longitudinal vessels
    // to separate lanes; their anatomical planes are different.
    curve(391f,131f,388f,210f,390f,300f,390f,410f,391f,535f,width=5f)
    curve(423f,143f,421f,220f,421f,332f,420f,428f,422f,534f,width=5f,color=Color(0xFF777877))
    for(i in 0..17) {
        val y=160f+i*20f
        curve(390f,y,370f,y-7f,357f,y+3f,360f,y+10f,421f,y+11f,width=1.8f)
    }
    // Small transverse inset: vessel located dorsal and ventral to gut.
    ellipse(580f,493f,46f,44f,2f)
    ring(580f,493f,19f)
    inkDot(580f,455f,5f)
    inkDot(580f,531f,5f)
}
private fun DrawScope.nerves() {
    body(outlineOnly=true)
    for(side in listOf(-1f,1f)) {
        ellipse(405f+side*18f,121f,17f,15f)
        curve(405f+side*18f,137f,405f+side*44f,162f,405f+side*31f,190f,405f+side*11f,199f,width=3.5f)
        curve(405f+side*10f,199f,405f+side*10f,545f,width=3.8f)
    }
    ellipse(405f,196f,26f,12f)
    for(i in 0..14) {
        val y=225f+i*20.5f
        ellipse(405f,y,20f,6f,2.2f)
        for(side in listOf(-1f,1f)) seg(405f+side*16f,y,405f+side*44f,y+3f,1.3f)
    }
}
private fun DrawScope.nephridium() {
    // Two adjoining coelomic compartments separated by a septum.
    seg(390f,97f,390f,548f,2.6f)
    for(j in 0..16) {
        val y=112f+j*26f
        seg(386f,y,394f,y+10f,1f,color=pencilGray)
    }
    // Ciliated funnel lies in preceding compartment.
    val funnel=Path().apply {
        moveTo(270f,182f)
        quadraticTo(310f,134f,352f,180f)
        quadraticTo(328f,203f,313f,231f)
    }
    drawPath(funnel,pencilBlack,style=Stroke(3.4f))
    for(i in 0..11) {
        val a=(i-5.5f)*.17f
        val x=311f+i*7f
        seg(x,167f+8f*sin(a),x+3f,144f+7f*sin(a),1.3f)
    }
    val tubule=Path().apply {
        moveTo(313f,231f)
        cubicTo(315f,274f,350f,301f,413f,288f)
        cubicTo(472f,275f,491f,328f,449f,344f)
        cubicTo(381f,364f,389f,414f,458f,405f)
        cubicTo(517f,397f,535f,442f,491f,479f)
        quadraticTo(479f,513f,572f,523f)
    }
    drawPath(tubule,pencilBlack,style=Stroke(10f))
    drawPath(tubule,Color(0xFFF9F9F5),style=Stroke(4f))
    drawCircle(pencilBlack,5f,p(574f,523f))
    curve(570f,529f,603f,533f,631f,536f,width=2f)
}
private fun DrawScope.trochophore() {
    val cx=407f
    val larva=Path().apply {
        moveTo(cx,135f)
        cubicTo(305f,135f,273f,228f,298f,351f)
        cubicTo(310f,437f,368f,521f,cx,538f)
        cubicTo(460f,511f,501f,441f,516f,346f)
        cubicTo(531f,214f,494f,139f,cx,135f)
        close()
    }
    drawPath(larva,Color(0xFFF5F4EF));drawPath(larva,pencilBlack,style=Stroke(3f))
    // Apical tuft must resolve as a fan of discrete cilia on a phone,
    // not one fused thick stroke: the earlier endpoints all clustered left.
    for(j in -9..9) {
        val offset=j.toFloat()
        val rootX=cx+offset*1.5f
        val tipX=cx+offset*4.8f
        val tipY=68f+kotlin.math.abs(offset)*3.0f
        seg(rootX,136f,tipX,tipY,1.15f)
    }
    // Circumferential prototroch, a wide transverse anterior ciliary band.
    for(j in 0..32) {
        val x=294f+j*7.0f
        val y=263f+12f*cos((j-16f)*.08f)
        seg(x,y-15f,x+(j-16f)*.32f,y+21f,1.6f)
    }
    curve(294f,263f,354f,277f,407f,278f,468f,273f,518f,260f,width=3f)
    ring(cx,325f,12f) // mouth
    val gut=Path().apply {
        moveTo(cx,338f);cubicTo(376f,389f,401f,431f,410f,482f)
    }
    drawPath(gut,pencilBlack,style=Stroke(3f))
    ring(411f,503f,7f)
    for(i in 0..22) {
        val theta=(PI*.25+i*PI*1.5/22).toFloat()
        val x=cx+56f*cos(theta);val y=492f+31f*sin(theta)
        seg(x,y,x+9f*cos(theta),y+9f*sin(theta),.8f)
    }
}


/**
 * Manual audit targets, in the same ordinal sequence as terms(index).
 * Markers are *fixed* labels on a static teaching drawing, not touch hotspots.
 * Points are taken from the path geometry in this file, not screen percentages.
 *
 * Where two chaetal bundles share one practical-record term, a marker has
 * two terminating leaders. Source-scanned graphite masters remain pending.
 */
private data class NereisNumberedLeader(
    val marker:Offset,
    val anatomy:List<Offset>
)
private fun leader(mx:Float,my:Float,x:Float,y:Float)=
    NereisNumberedLeader(p(mx,my),listOf(p(x,y)))
private fun leaderPair(mx:Float,my:Float,x:Float,y:Float,x2:Float,y2:Float)=
    NereisNumberedLeader(p(mx,my),listOf(p(x,y),p(x2,y2)))
private val numberedLeaders=listOf(
    // 01: prostomium; peristomium; chaetiger; paired parapodium; pygidium; cirri
    listOf(
        leader(132f,101f,405f,61f), leader(688f,122f,453f,138f),
        leader(132f,271f,405f,264f), leader(688f,289f,480f,286f),
        leader(132f,479f,405f,546f), leader(688f,547f,440f,585f)
    ),
    // 02: prostomium; palps; antennae; eyes; peristomium; tentacular cirri
    listOf(
        leader(129f,143f,390f,273f), leader(686f,378f,507f,364f),
        leader(129f,87f,427f,152f), leader(686f,205f,423f,280f),
        leader(129f,476f,393f,406f), leader(686f,487f,562f,272f)
    ),
    // 03: notopodium; neuropodium; dorsal/ventral cirri; acicula; both chaetal bundles
    listOf(
        leader(129f,135f,465f,232f), leader(129f,451f,469f,385f),
        leader(699f,115f,541f,107f), leader(699f,513f,554f,506f),
        leaderPair(129f,325f,423f,274f,426f,365f),
        leaderPair(699f,365f,652f,228f,652f,411f)
    ),
    // 04: mouth; eversible pharynx; jaws; oesophagus; intestine; anus
    listOf(
        leader(129f,102f,405f,89f), leader(699f,143f,405f,147f),
        leader(129f,245f,390f,139f), leader(699f,284f,405f,218f),
        leader(129f,416f,397f,402f), leader(699f,532f,405f,545f)
    ),
    // 05: dorsal and ventral vessels; segmental connections; transverse inset
    listOf(
        leader(129f,152f,390f,222f), leader(699f,212f,422f,332f),
        leader(129f,398f,367f,365f), leader(699f,488f,580f,493f)
    ),
    // 06: brain; circumpharyngeal connectives; suboesophageal ganglion;
    // ventral nerve cord; segmental ganglia
    listOf(
        leader(129f,103f,387f,122f), leader(699f,191f,365f,177f),
        leader(129f,275f,405f,198f), leader(699f,404f,415f,386f),
        leader(129f,486f,405f,432f)
    ),
    // 07: ciliated nephrostome; septum; coiled tubule; external pore
    listOf(
        leader(129f,175f,315f,178f), leader(699f,173f,390f,194f),
        leader(129f,440f,453f,402f), leader(699f,526f,574f,523f)
    ),
    // 08: apical tuft; prototroch; mouth; gut; anus
    listOf(
        leader(129f,106f,405f,113f), leader(699f,263f,407f,277f),
        leader(129f,341f,407f,325f), leader(699f,399f,406f,416f),
        leader(129f,533f,411f,502f)
    )
)
private val markerTextPaint=AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
    color=android.graphics.Color.rgb(51,52,50)
    textSize=32f
    textAlign=AndroidPaint.Align.CENTER
    typeface=android.graphics.Typeface.DEFAULT_BOLD
}
private fun DrawScope.drawNumberedLeaders(index:Int) {
    val leaders=numberedLeaders[index]
    check(leaders.size==terms(index).size) {
        "Nereis plate ${index+1}: numbered leader/terminology mismatch"
    }
    leaders.forEachIndexed { ordinal, item ->
        val elbow=if(item.marker.x<400f)item.marker.x+27f else item.marker.x-27f
        item.anatomy.forEach { target ->
            // An actual endpoint dot is essential: these are not floating label lines.
            seg(target.x,target.y,elbow,item.marker.y,1.45f,pencilGray)
            drawCircle(pencilBlack,3.8f,target)
        }
        seg(elbow,item.marker.y,item.marker.x,item.marker.y,1.8f,pencilBlack)
        drawCircle(paper,22f,item.marker)
        drawCircle(pencilBlack,22f,item.marker,style=Stroke(2.6f))
        drawContext.canvas.nativeCanvas.drawText(
            (ordinal+1).toString(),item.marker.x,item.marker.y+11f,markerTextPaint
        )
    }
}

private fun DrawScope.drawStaticPlate(index:Int) {
    when(index) {
        0 -> body()
        1 -> head()
        2 -> parapodium()
        3 -> gut()
        4 -> circulation()
        5 -> nerves()
        6 -> nephridium()
        7 -> trochophore()
    }
    // Subtle pencil cross-hatching on the page; not a claim of hand graphite.
    for(i in 0..16) {
        val x=311f+i*7f
        seg(x,579f,x+7f,582f,.48f,pencilGray)
    }
    drawNumberedLeaders(index)
}

private fun terms(index:Int):List<BilingualText>{
    fun b(en:String,ta:String)=BilingualText(en,ta)
    return when(index){
        0->listOf(b("Prostomium","புரோஸ்டோமியம்"),b("Peristomium","பெரிஸ்டோமியம்"),b("Chaetigerous segments","சீட்டா கொண்ட கண்டங்கள்"),b("Paired parapodia","இணை பாராப்போடியா"),b("Pygidium","பைகிடியம்"),b("Anal cirri","ஆசனச் சிர்ரைகள்"))
        1->listOf(b("Prostomium","புரோஸ்டோமியம்"),b("Palps","பல்புகள்"),b("Antennae","உணர்கொம்புகள்"),b("Eyes","கண்கள்"),b("Peristomium","பெரிஸ்டோமியம்"),b("Tentacular cirri","கூடாரச் சிர்ரைகள்"))
        2->listOf(b("Notopodium","நோட்டோபோடியம்"),b("Neuropodium","நியூரோபோடியம்"),b("Dorsal cirrus","முதுகுச் சிர்ரஸ்"),b("Ventral cirrus","வயிற்றுச் சிர்ரஸ்"),b("Acicula","அசிகுலா"),b("Notochaetae / neurochaetae","நோட்டோசீட்டா / நியூரோசீட்டா"))
        3->listOf(b("Mouth","வாய்"),b("Eversible pharynx","வெளிநீளும் தொண்டை"),b("Paired jaws","இணைத் தாடைகள்"),b("Oesophagus","உணவுக்குழாய்"),b("Intestine","குடல்"),b("Anus","ஆசனவாய்"))
        4->listOf(b("Dorsal vessel","முதுகு இரத்த நாளம்"),b("Ventral vessel","வயிற்று இரத்த நாளம்"),b("Segmental connections","கண்டநாள இணைப்புகள்"),b("Dorsal–ventral cross section","முதுகு–வயிறு குறுக்குவெட்டு"))
        5->listOf(b("Cerebral ganglia","மூளை நரம்புத்திரள்கள்"),b("Circumpharyngeal connectives","தொண்டைச் சுற்று இணைப்புகள்"),b("Suboesophageal ganglion","தொண்டைக்கீழ் நரம்புத்திரள்"),b("Ventral nerve cord","வயிற்று நரம்புக்கயிறு"),b("Segmental ganglia","கண்ட நரம்புத்திரள்கள்"))
        6->listOf(b("Ciliated nephrostome","சிலியா கொண்ட நெப்ரோஸ்டோம்"),b("Septum","இடைத்தடுப்பு"),b("Coiled nephridial tubule","சுருண்ட நெப்ரிடியக் குழாய்"),b("Nephridiopore","நெப்ரிடியத் துளை"))
        else->listOf(b("Apical tuft","உச்சிச் சிலியாக் கொத்து"),b("Prototroch","புரோட்டோட்ரோக்"),b("Mouth","வாய்"),b("Alimentary canal","உணவுக் குழாய்"),b("Anus","ஆசனவாய்"))
    }
}
@Composable
internal fun UnitIVNereisStaticPlate(index:Int,language:AppLanguage){
    require(index in 0..7)
    val title=UnitIVNereisLessons.plateTopics[index]
    // Static pencil-style DRAWINGS. Their accessibility is separate from
    // bitmap pixels, which otherwise hide Tamil terms from screen readers.
    Card(Modifier.fillMaxWidth().testTag("u44-nereis-static-plate-"+(index+1))) {
        Column(Modifier.fillMaxWidth().padding(14.dp),verticalArrangement=Arrangement.spacedBy(9.dp)){
            Text(title.english,style=MaterialTheme.typography.titleLarge)
            Text(title.tamil,style=MaterialTheme.typography.titleMedium)
            if(index==0) {
                // Original static Android VectorDrawable; anatomical draft,
                // not a scan and not approved as a college record plate.
                Box(Modifier.fillMaxWidth().aspectRatio(800f/640f)
                    .background(paper)
                    .border(1.dp,Color(0xFFC1C4BF))
                    .testTag("u44-nereis-pencil-style-drawing-1")) {
                    Image(
                        painter=painterResource(R.drawable.nereis_external_graphite_candidate),
                        contentDescription=null,
                        contentScale=ContentScale.FillBounds,
                        modifier=Modifier.fillMaxSize()
                    )
                    Canvas(Modifier.fillMaxSize()) {
                        withTransform({scale(size.width/800f,size.height/640f,pivot=Offset.Zero)}) {
                            drawNumberedLeaders(0)
                        }
                    }
                }
            } else {
                Canvas(Modifier.fillMaxWidth().aspectRatio(800f/640f)
                    .background(paper)
                    .border(1.dp,Color(0xFFC1C4BF))
                    .testTag("u44-nereis-pencil-style-drawing-"+(index+1))){
                    withTransform({scale(size.width/800f,size.height/640f,pivot=Offset.Zero)}){
                        drawStaticPlate(index)
                    }
                }
            }
            Text(if(language==AppLanguage.TAMIL)
                "எண்ணிட்ட சுட்டுக்கோடுகள் · English / தமிழ்"
                else "Numbered anatomical leaders · English / தமிழ்",
                style=MaterialTheme.typography.labelLarge)
            terms(index).forEachIndexed {i,term ->
                Text((i+1).toString()+". "+term.english+"\n"+term.tamil,
                    style=MaterialTheme.typography.bodyMedium,
                    modifier=Modifier.testTag("u44-nereis-plate-"+(index+1)+"-term-"+i))
            }
            Text(if(language==AppLanguage.TAMIL)
                "தனித்த கற்றல் வரைவு. அசல் பென்சில் ஸ்கேன் அல்ல. தமிழ் சொற்கள் கல்லூரிச் செய்முறை கையேட்டுடன் ஒப்பிட்டு சரிபார்க்கப்பட வேண்டும்."
                else "Original static teaching sketch, NOT a pencil scan. Tamil label forms await verification against the actual college practical manual.",
                style=MaterialTheme.typography.bodySmall,
                color=MaterialTheme.colorScheme.error,
                modifier=Modifier.testTag("u44-nereis-image-provenance-"+(index+1)))
        }
    }
}