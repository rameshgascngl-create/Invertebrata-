package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gasczoology.invertebratelab.NuclearPlaybackViewModel
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private fun text(lang: AppLanguage, en: String, ta: String) = if (lang == AppLanguage.TAMIL) ta else en

@Composable
internal fun ParameciumNuclearTextbook(
    language: AppLanguage, speak: (BilingualText) -> Unit,
    initialProgress: NuclearLearningProgress, onProgress: (NuclearLearningProgress) -> Unit,
) {
    // Apply interaction state synchronously. DataStore acknowledgement is asynchronous;
    // ON_STOP must never overwrite a newly selected stage with an older emitted snapshot.
    var progress by remember { mutableStateOf(initialProgress) }
    val persist by rememberUpdatedState(onProgress)
    fun change(next: NuclearLearningProgress) { progress = next; persist(next) }
    val chapter = ParameciumNuclearBiology.chapter(progress.chapterId)
    val position = chapter.views.indexOfFirst { it.id == progress.viewId() }.coerceAtLeast(0)
    val stage = chapter.views[position]
    val reading = chapter.readings.firstOrNull { it.id == progress.readingId } ?: chapter.readings.first()
    val player: NuclearPlaybackViewModel = viewModel()
    val playback by player.state.collectAsState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(progress.chapterId, stage.id) { player.restore(progress.phasePermille) }
    DisposableEffect(player, lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                player.pause()
                change(progress.copy(phasePermille = (player.state.value.phase * 1000).toInt()))
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer); player.pause() }
    }
    fun chooseView(id: String) {
        player.rewind()
        var next = progress.selectView(id)
        if (chapter.id == "dimorphism" && id != "whole-cell") {
            next = next.copy(selectedNucleus = if (id == "somatic") "macronucleus" else "micronucleus")
        }
        change(next)
    }
    fun advance(): Boolean {
        val current = progress
        val c = ParameciumNuclearBiology.chapter(current.chapterId)
        val index = c.views.indexOfFirst { it.id == current.viewId() }
        if (index >= c.views.lastIndex) return false
        change(current.selectView(c.views[index + 1].id))
        return true
    }
    val shape = RoundedCornerShape(12.dp)
    val scope = rememberCoroutineScope()
    val requester = remember { BringIntoViewRequester() }
    fun chooseNucleus(id: String) {
        change(progress.copy(selectedNucleus = id))
        speak(ParameciumNuclearBiology.nucleus(id))
    }
    Text(text(language, "NUCLEAR AND REPRODUCTIVE BIOLOGY · Paramecium caudatum",
        "உட்கரு மற்றும் இனப்பெருக்க உயிரியல் · Paramecium caudatum"),
        style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold,
        modifier = Modifier.testTag("r17-textbook-title").semantics { heading() })
    Text("DRAFT_UNVERIFIED · " + text(language,
        "Original graphite schematics, not to scale. Biology, geometry and Tamil specialist approval pending.",
        "அசல் பென்சில் விளக்கப்படங்கள்; அளவுக்கு ஏற்ப அல்ல. உயிரியல், வடிவம், தமிழ் நிபுணர் ஒப்புதல் நிலுவை."),
        modifier = Modifier.testTag("r17-review-status"))
    Text(chapter.heading.value(language) + " · " + (position + 1) + "/" + chapter.views.size + " · " +
        ParameciumNuclearBiology.nucleusName(progress.selectedNucleus).value(language),
        modifier = Modifier.testTag("r17-progress"))
    for (candidate in ParameciumNuclearBiology.chapters) {
        OutlinedButton(onClick = {
            player.rewind()
            change(progress.copy(chapterId = candidate.id, phasePermille = 0).normalized())
        }, shape = shape, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
            .testTag("r17-chapter-" + candidate.id).semantics { selected = candidate.id == chapter.id }) {
            Text(candidate.heading.value(language))
        }
    }
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8))) {
        Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(chapter.heading.value(language), style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
            chapter.readings.forEach { item ->
                OutlinedButton(onClick = { change(progress.copy(readingId = item.id)) }, shape = shape,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-reading-" + item.id)
                        .semantics { selected = item.id == reading.id }) { Text(item.heading.value(language)) }
            }
            Text(reading.heading.value(language), style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("r17-reading-heading").semantics { heading() })
            reading.paragraphs.forEachIndexed { i, paragraph ->
                Text(paragraph.value(language), style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("r17-paragraph-" + i))
            }
            OutlinedButton(onClick = { speak(BilingualText(reading.paragraphs.joinToString("\n\n") { it.english },
                reading.paragraphs.joinToString("\n\n") { it.tamil })) }, shape = shape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-narrate-reading")) {
                Text(text(language, "Hear the complete subsection", "முழுப் பாடப்பகுதி விளக்கத்தைக் கேள்"))
            }
        }
    }
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF8F3))) {
        Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text(language, "View ", "காட்சி ") + (position + 1) + "/" + chapter.views.size + " — " + stage.heading.value(language),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("r17-stage-title").semantics { heading() })
            Text(text(language, "Cells shown: ", "காட்டப்படும் செல்கள்: ") + stage.cellCount,
                modifier = Modifier.testTag("r17-cell-count"))
            Button(onClick = { scope.launch { requester.bringIntoView() } }, shape = shape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-view-plate")) {
                Text(text(language, "Bring the complete pencil plate into view", "முழுப் பென்சில் படத்தைத் திரையில் காண்க"))
            }
            Canvas(Modifier.fillMaxWidth().height(240.dp).bringIntoViewRequester(requester)
                .testTag("r17-nuclear-canvas").pointerInput(stage.id, progress.selectedNucleus) {
                    detectTapGestures { p ->
                        val f = NuclearFigureGeometry.fit(size.width.toFloat(), size.height.toFloat())
                        NuclearFigureGeometry.nucleusAt(f.logicalX(p.x), f.logicalY(p.y))?.let(::chooseNucleus)
                    }
                }.semantics {
                    contentDescription = text(language,
                        "Original schematic, Paramecium caudatum: hatched macronucleus and compact micronucleus; separately enlarged insets are not additional nuclei. Anterior left, posterior right. Current view: ",
                        "அசல் கௌடேட்டம் விளக்கப்படம்: கோடிட்ட பேருட்கரு, செறிவான சிற்றுட்கரு; பெரிதாக்கிய சிறுபடங்கள் கூடுதல் உட்கருக்கள் அல்ல. முன்முனை இடது, பின்முனை வலது. தற்போதைய காட்சி: ") + stage.heading.value(language)
                    stateDescription = ParameciumNuclearBiology.nucleusName(progress.selectedNucleus).value(language) +
                        text(language, " highlighted; cells ", " சிறப்பிக்கப்பட்டது; செல்கள் ") + stage.cellCount
                    customActions = listOf("macronucleus", "micronucleus").map { id ->
                        CustomAccessibilityAction(ParameciumNuclearBiology.nucleusName(id).value(language)) { chooseNucleus(id); true }
                    }
                }) { drawNuclearPencilPlate(chapter.id, stage, progress.selectedNucleus, playback.phase) }
            Text(text(language,
                "MAC: larger hatched somatic nucleus. MIC: compact germline nucleus. Insets are enlarged independently. Representative chromatin, cilia and oral outline; not a calibrated specimen or chromosome count.",
                "MAC: பெரிய கோடிட்ட உடலியக்க உட்கரு. MIC: செறிவான மரபுவழி உட்கரு. சிறுபடங்கள் தனித்தனியே பெரிதாக்கப்பட்டன. குரோமாட்டின், குறுஇழைகள், வாய்ப்புறம் பிரதிநிதிக் குறிகள்; அளவுத்திருத்த மாதிரியோ குரோமோசோம் எண்ணிக்கையோ அல்ல."),
                modifier = Modifier.testTag("r17-plate-legend"))
            listOf("macronucleus", "micronucleus").forEach { id ->
                OutlinedButton(onClick = { chooseNucleus(id) }, shape = shape,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-select-" + id)
                        .semantics { selected = progress.selectedNucleus == id }) { Text(ParameciumNuclearBiology.nucleusName(id).value(language)) }
            }
            Text(ParameciumNuclearBiology.nucleus(progress.selectedNucleus).value(language),
                modifier = Modifier.testTag("r17-organ-explanation"), style = MaterialTheme.typography.bodyLarge)
            Text(stage.explanation.value(language), modifier = Modifier.testTag("r17-stage-explanation"),
                style = MaterialTheme.typography.bodyLarge)
            chapter.views.forEachIndexed { index, view ->
                OutlinedButton(onClick = { chooseView(view.id) }, shape = shape,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-stage-" + view.id)
                        .semantics { selected = view.id == stage.id }) { Text("${index + 1}. " + view.heading.value(language)) }
            }
            Text(text(language,
                "Illustrative presentation speed; views separate biological events and may overlap in real time. Manual stepping is available. Restored demonstrations stay paused.",
                "கற்பித்தலுக்கான காட்சி வேகம்; நிகழ்வுகள் தனியாக விளக்கப்படுகின்றன, இயற்கையில் நேரங்கள் ஒன்றோடொன்று அமையலாம். கையால் படிநிலை மாற்றலாம். மீட்டமைந்த காட்சி இடைநிறுத்தத்தில் இருக்கும்."))
            Text(text(language, if (playback.playing) "Playing" else "Paused", if (playback.playing) "இயங்குகிறது" else "இடைநிறுத்தம்"),
                modifier = Modifier.testTag("r17-playback-state").semantics { liveRegion = LiveRegionMode.Polite })
            OutlinedButton(onClick = { if (playback.playing) {
                player.pause(); change(progress.copy(phasePermille = (player.state.value.phase * 1000).toInt()))
            } else player.play(::advance) }, shape = shape, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-play-pause")) {
                Text(text(language, if (playback.playing) "Pause demonstration" else "Play staged demonstration",
                    if (playback.playing) "காட்சியை இடைநிறுத்து" else "படிநிலைக் காட்சியை இயக்கு"))
            }
            OutlinedButton(onClick = { chooseView(chapter.views[(position - 1).coerceAtLeast(0)].id) },
                enabled = position > 0, shape = shape, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-previous")) {
                Text(text(language, "Previous view", "முந்தைய காட்சி"))
            }
            OutlinedButton(onClick = { chooseView(chapter.views[(position + 1).coerceAtMost(chapter.views.lastIndex)].id) },
                enabled = position < chapter.views.lastIndex, shape = shape, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-next")) {
                Text(text(language, "Next view", "அடுத்த காட்சி"))
            }
            OutlinedButton(onClick = { player.rewind(); player.play(::advance) }, shape = shape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-replay")) {
                Text(text(language, "Replay from this view", "இக்காட்சியிலிருந்து மீண்டும் இயக்கு"))
            }
            OutlinedButton(onClick = { chooseView(chapter.views.first().id) }, shape = shape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-reset")) {
                Text(text(language, "Reset this demonstration", "இக்காட்சியைத் தொடக்க நிலைக்குக் கொண்டுசெல்"))
            }
            OutlinedButton(onClick = { speak(stage.explanation) }, shape = shape,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r17-narrate-stage")) {
                Text(text(language, "Hear the selected stage", "தேர்ந்தெடுத்த படிநிலை விளக்கத்தைக் கேள்"))
            }
        }
    }
    Text(text(language, "Scientific reading and limits", "அறிவியல் ஆதாரங்களும் வரம்புகளும்"),
        style = MaterialTheme.typography.titleLarge, modifier = Modifier.semantics { heading() })
    chapter.sourceIds.forEach { Text(ParameciumNuclearBiology.sources.getValue(it), style = MaterialTheme.typography.bodySmall) }
}

private val graphite = Color(0xFF494641)
private val pencil = Color(0xFF968F85)
private val selectedInk = Color(0xFF99612A)
private fun DrawScope.mac(cx: Float, cy: Float, w: Float, h: Float, selected: Boolean) {
    val path = Path().apply {
        moveTo(cx-w/2,cy)
        cubicTo(cx-w/2,cy-h*.7f,cx+w*.4f,cy-h*.7f,cx+w/2,cy-h*.05f)
        cubicTo(cx+w*.10f,cy-h*.05f,cx+w*.05f,cy+h*.35f,cx+w*.40f,cy+h*.42f)
        cubicTo(cx,cy+h*.7f,cx-w/2,cy+h*.45f,cx-w/2,cy)
        close()
    }
    drawPath(path, Color(0xFFEAE4D8))
    clipPath(path) {
        for (i in -12..12) drawLine(pencil.copy(alpha=.65f), Offset(cx+i*14-100,cy-110), Offset(cx+i*14+100,cy+110), 1.4f)
        for (i in 0..12) drawCircle(graphite.copy(alpha=.45f), 2.5f,Offset(cx-w*.33f+(i%5)*w*.12f,cy-h*.22f+(i/5)*h*.20f))
    }
    drawPath(path, if (selected) selectedInk else graphite, style=Stroke(if(selected) 5f else 2.8f))
    if(selected) drawOval(graphite,Offset(cx-w*.56f,cy-h*.65f),Size(w*1.12f,h*1.28f),style=Stroke(1.4f))
}
private fun DrawScope.mic(cx: Float, cy: Float, r: Float, selected: Boolean) {
    drawCircle(Color(0xFFE1DCD3), r, Offset(cx,cy))
    drawCircle(if(selected) selectedInk else graphite,r,Offset(cx,cy),style=Stroke(if(selected) 5f else 2.5f))
    drawCircle(pencil,r*.82f,Offset(cx,cy),style=Stroke(1.2f))
    for(i in 0..9) {
        val a=i*2*PI/10
        drawLine(graphite,Offset(cx+cos(a).toFloat()*r*.25f,cy+sin(a).toFloat()*r*.25f),
            Offset(cx+cos(a+.4).toFloat()*r*.62f,cy+sin(a+.4).toFloat()*r*.62f),1.3f)
    }
    if(selected) drawCircle(graphite,r+7,Offset(cx,cy),style=Stroke(1.5f))
}
internal fun DrawScope.drawNuclearPencilPlate(chapter: String, stage: NuclearView, selected: String, phase: Float) {
    drawRect(Color(0xFFFAF8F3))
    val frame = NuclearFigureGeometry.fit(size.width,size.height)
    withTransform({ translate(frame.left,frame.top);scale(frame.scale,frame.scale,pivot=Offset.Zero) }) {
        drawOval(Color(0xFFF1EEE7),Offset(45f,135f),Size(530f,275f))
        drawOval(graphite,Offset(45f,135f),Size(530f,275f),style=Stroke(3f))
        drawOval(pencil,Offset(50f,140f),Size(520f,265f),style=Stroke(1.5f))
        for(i in 0..63) {
            val a=i*2*PI/64;val d=Offset(cos(a).toFloat(),sin(a).toFloat())
            val point=Offset(310f+d.x*266,272f+d.y*139)
            drawLine(pencil,point,point+Offset(d.x*17-d.y*4,d.y*17+d.x*4),1.5f)
        }
        val oral=Path().apply { moveTo(130f,322f);cubicTo(190f,377f,395f,384f,465f,318f);cubicTo(430f,346f,330f,332f,280f,347f) }
        drawPath(oral,pencil,style=Stroke(3f))
        drawLine(pencil,Offset(88f,106f),Offset(150f,106f),2f)
        drawLine(pencil,Offset(530f,106f),Offset(465f,106f),2f)
        mac(275f,270f,126f,90f,selected=="macronucleus")
        mic(380f,246f,22f,selected=="micronucleus")
        mac(780f,155f,if(stage.id=="somatic") 280f else 215f,if(stage.id=="somatic") 136f else 100f,selected=="macronucleus")
        mic(780f,380f,if(stage.id=="germline") 77f else 55f,selected=="micronucleus")
        drawLine(pencil,Offset(335f,224f),Offset(640f,154f),1.2f)
        drawLine(pencil,Offset(402f,246f),Offset(702f,368f),1.2f)
        // Presentation focus marker, not an inferred movement of the nuclei.
        if(stage.id!="whole-cell") drawLine(selectedInk,Offset(645f,if(stage.id=="somatic") 252f else 490f),
            Offset(915f,if(stage.id=="somatic") 252f else 490f),3f+phase)
    }
}
