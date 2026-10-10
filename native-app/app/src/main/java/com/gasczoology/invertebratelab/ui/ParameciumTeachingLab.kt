package com.gasczoology.invertebratelab.ui

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.NativeLessonDrafts
import com.gasczoology.invertebratelab.data.ParameciumLearningEngine
import com.gasczoology.invertebratelab.data.ParameciumVacuolePlate
import com.gasczoology.invertebratelab.data.ParameciumCvcMechanism
import com.gasczoology.invertebratelab.data.ParameciumProcess
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

private fun bi(lang: AppLanguage, en: String, ta: String) =
    if(lang == AppLanguage.TAMIL) ta else en

private data class AtlasNode(val id: String, val x: Float, val y: Float)
private fun nodes(panel: String): List<AtlasNode> = when(panel) {
    "oral" -> listOf(AtlasNode("oral-groove",.45f,.71f),
        AtlasNode("food-vacuole",.60f,.40f),AtlasNode("cytoproct",.76f,.70f))
    "vacuole" -> listOf(AtlasNode("contractile-vacuole",.32f,.40f),
        AtlasNode("food-vacuole",.65f,.64f))
    else -> listOf(AtlasNode("macronucleus",.48f,.44f),
        AtlasNode("micronucleus",.63f,.44f),
        AtlasNode("trichocysts",.29f,.26f),
        AtlasNode("food-vacuole",.34f,.64f))
}

/**
 * R1 native learning vertical slice. Geometry and Tamil scientific terms remain
 * DRAFT_UNVERIFIED. Simulated stage timing is illustrative, not measured kinetics.
 */
@Composable
fun ParameciumTeachingLab(
    language: AppLanguage,
    onBack: (com.gasczoology.invertebratelab.data.NativeLearningState) -> Unit,
    onPractice: () -> Unit,
    learningState: com.gasczoology.invertebratelab.data.NativeLearningState,
    onLearningChanged: (com.gasczoology.invertebratelab.data.NativeLearningState) -> Unit,
) {
    val context=LocalContext.current
    val narrator = remember(context) { com.gasczoology.invertebratelab.OfflineNarrator(context) }
    val audioPhase by narrator.phase.collectAsState()
    val audioScript by narrator.script.collectAsState()
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(narrator, lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_STOP) narrator.stop()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer); narrator.close() }
    }
    LaunchedEffect(language) { narrator.stop() }
    fun speak(script: BilingualText) { narrator.speak(script, language) }
    val tab = learningState.laboratoryTab
    val nutritionPlayer: com.gasczoology.invertebratelab.NutritionPlaybackViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    fun checkpoint(): com.gasczoology.invertebratelab.data.NativeLearningState {
        if(tab!="nutrition") return learningState
        android.util.Log.i("R19_NUTRITION","checkpoint stage=${learningState.nutritionProgress.stageId} phase=${nutritionPlayer.state.value.phase}")
        nutritionPlayer.pause()
        return learningState.copy(nutritionProgress=learningState.nutritionProgress.copy(
            phasePermille=(nutritionPlayer.state.value.phase*1000).toInt()))
    }
    fun selectTab(value: String) { onLearningChanged(checkpoint().copy(laboratoryTab = value)) }
    fun leave() { onBack(checkpoint()) }
    // Keep simulation state at the stable laboratory level. Subtree recreation
    // from TTS initialization or tab changes must not reset the current stage.
    var simulationId by rememberSaveable {
        mutableStateOf(ParameciumProcess.OSMOREGULATION.name)
    }
    var simulationStep by rememberSaveable { mutableIntStateOf(0) }
    var simulationPlaying by rememberSaveable { mutableStateOf(false) }
    androidx.activity.compose.BackHandler(onBack = ::leave)
    Scaffold { insets ->
        Column(modifier=Modifier.padding(insets).verticalScroll(rememberScrollState())
            .padding(16.dp).testTag("r1-paramecium-lab"),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick=::leave, modifier=Modifier.heightIn(min=48.dp)) {
                Text(bi(language,"Back","பின்செல்"))
            }
            val savedSection = NativeLessonDrafts.paramecium.sections.first { it.id == learningState.textbookSectionId }
            Text(bi(language, "Reading position: ", "வாசிப்பு நிலை: ") + savedSection.heading.value(language) +
                bi(language, " · ciliary view ", " · குறுஇழைக் காட்சி ") +
                (learningState.ciliaryStageIndex + 1) + "/4",
                modifier = Modifier.testTag("r161-learning-position"))
            if (tab == "nuclear") {
                val p = learningState.nuclearProgress
                Text("R1.7 · ${p.chapterId} · ${p.viewId()} · ${p.readingId} · ${p.selectedNucleus}",
                    modifier = Modifier.testTag("r17-learning-position"))
            }
            if (tab == "nutrition") {
                val p = learningState.nutritionProgress
                Text("R1.9 · " + p.stageId + " · " + p.readingId + " · " + p.selectedStructure,
                    modifier = Modifier.testTag("r19-learning-position"))
            }
            if (tab == "water-balance") {
                val p = learningState.waterBalanceProgress
                Text("R1.8 · ${p.stageId} · ${p.readingId} · ${p.selectedStructure}",
                    modifier = Modifier.testTag("r18-learning-position"))
            }
            Text(
                bi(language, "PARAMECIUM · DIGITAL ZOOLOGY TEXTBOOK",
                    "பாரமீசியம் · மின்னணு விலங்கியல் பாடநூல்"),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("r14-textbook-hero")
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F5F3)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(bi(language,
                        "Read illustrated chapters, investigate organs, then test the processes.",
                        "படவிளக்கப் பாடங்களைப் படித்து, உறுப்புகளை ஆராய்ந்து, செயல்முறைகளைச் சோதிக்கவும்."),
                        style = MaterialTheme.typography.titleMedium)
                    Text(bi(language,
                        "Reference species: Paramecium caudatum · original graphite atlases · offline bilingual explanations.",
                        "ஆய்வு இனம்: பாரமீசியம் கௌடேட்டம் · அசல் பென்சில் உடலமைப்புப் படங்கள் · இணையமின்றி இருமொழி விளக்கம்."),
                        style = MaterialTheme.typography.bodyMedium)
                    Text(bi(language,
                        "Academic review pending: illustrations and Tamil vocabulary remain drafts.",
                        "அறிவியல் மதிப்பாய்வு நிலுவை: வரைபடங்களும் தமிழ் சொற்களும் வரைவு நிலையில் உள்ளன."),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("r1-review-warning"))
                }
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val status = when (audioPhase) {
                        com.gasczoology.invertebratelab.data.NarrationPhase.UNAVAILABLE -> bi(language,
                            "Installed offline voice unavailable for the requested language. Install an English or Tamil voice in Android speech settings. The complete explanation remains below.",
                            "தேர்ந்தெடுத்த மொழிக்கான நிறுவப்பட்ட இணையமில்லா குரல் கிடைக்கவில்லை. Android உரை-ஒலி அமைப்பில் ஆங்கிலம் அல்லது தமிழ் குரலை நிறுவவும். முழு விளக்கம் கீழே உள்ளது.")
                        com.gasczoology.invertebratelab.data.NarrationPhase.ERROR -> bi(language,
                            "Speech failed. The complete written explanation remains available.",
                            "ஒலிவிளக்கம் செயல்படவில்லை. முழு எழுத்து விளக்கம் தொடர்ந்து உள்ளது.")
                        com.gasczoology.invertebratelab.data.NarrationPhase.PLAYING -> bi(language,
                            "Android speech playback started.", "Android ஒலிவிளக்கம் தொடங்கியுள்ளது.")
                        com.gasczoology.invertebratelab.data.NarrationPhase.QUEUED -> bi(language,
                            "Explanation queued for the installed offline voice.", "நிறுவப்பட்ட இணையமில்லா குரலில் விளக்கம் ஒலிக்கக் காத்திருக்கிறது.")
                        com.gasczoology.invertebratelab.data.NarrationPhase.COMPLETE -> bi(language,
                            "Android reported speech completion.", "ஒலிவிளக்கம் முடிந்ததாக Android தெரிவித்துள்ளது.")
                        else -> bi(language,
                            "Audio uses installed offline English/Tamil voices. Written lessons are always available.",
                            "ஒலிவிளக்கம் நிறுவப்பட்ட இணையமில்லா ஆங்கில / தமிழ் குரல்களைப் பயன்படுத்துகிறது. எழுத்துப் பாடங்கள் எப்போதும் உள்ளன.")
                    }
                    Text(status, modifier = Modifier.testTag("r1-speech-status")
                        .semantics { liveRegion = androidx.compose.ui.semantics.LiveRegionMode.Polite })
                    audioScript?.let { Text(it.value(language), modifier = Modifier.testTag("r161-audio-written-fallback")) }
                    OutlinedButton(onClick = narrator::stop, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r161-stop-audio")) {
                        Text(bi(language, "Stop audio", "ஒலியை நிறுத்து"))
                    }
                }
            }
            val menu = listOf("study", "anatomy", "simulate", "listen", "practice", "nuclear", "water-balance", "nutrition")
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for (pair in menu.chunked(2)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()) {
                        for (section in pair) {
                            val title = when (section) {
                                "study" -> bi(language, "01 · Read", "01 · பாடம்")
                                "anatomy" -> bi(language, "02 · Anatomy", "02 · உடலமைப்பு")
                                "simulate" -> bi(language, "03 · Simulate", "03 · இயக்கக் காட்சி")
                                "listen" -> bi(language, "04 · Listen", "04 · ஒலி விளக்கம்")
                                "nuclear" -> bi(language, "06 · Nuclear biology", "06 · உட்கரு உயிரியல்")
                                "water-balance" -> bi(language, "07 · Water balance", "07 · நீர்ச் சமநிலை")
                                "nutrition" -> bi(language, "08 · Nutrition", "08 · ஊட்டமுறை")
                                else -> bi(language, "05 · Practice", "05 · பயிற்சி")
                            }
                            val buttonModifier = Modifier.weight(1f)
                                .heightIn(min = 56.dp).testTag("r1-tab-" + section)
                            if (tab == section) {
                                Button(shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), onClick = { selectTab(section) }, modifier = buttonModifier) {
                                    Text(title)
                                }
                            } else {
                                OutlinedButton(shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), onClick = { selectTab(section) },
                                    modifier = buttonModifier) {
                                    Text(title)
                                }
                            }
                        }
                    }
                }
            }
            when(tab) {
                "nutrition" -> ParameciumNutritionTextbook(language, ::speak, learningState.nutritionProgress,
                    onProgress = { onLearningChanged(learningState.copy(nutritionProgress = it)) },player=nutritionPlayer)
                "water-balance" -> ParameciumWaterBalanceTextbook(language, ::speak, learningState.waterBalanceProgress,
                    onProgress = { onLearningChanged(learningState.copy(waterBalanceProgress = it)) })
                "nuclear" -> ParameciumNuclearTextbook(language, ::speak, learningState.nuclearProgress,
                    onProgress = { onLearningChanged(learningState.copy(nuclearProgress = it)) })
                "study" -> StudySection(language, ::speak, onAnatomy = { selectTab("anatomy") },
                    onSimulation = { selectTab("simulate") },
                    learningState = learningState, onLearningChanged = onLearningChanged)
                "anatomy" -> AtlasSection(language, ::speak, onExploreOsmoregulation = {
                    simulationId = ParameciumProcess.OSMOREGULATION.name
                    simulationStep = 0
                    simulationPlaying = false
                    selectTab("simulate")
                })
                "simulate" -> SimulatorSection(
                    language, ::speak, simulationId, simulationStep, simulationPlaying,
                    onChoose = { newId ->
                        simulationId = newId
                        simulationStep = 0
                        simulationPlaying = false
                    },
                    onStep = { simulationStep = it },
                    onPlaying = { simulationPlaying = it },
                    onNuclearChapter = { chapter ->
                        simulationPlaying=false
                        onLearningChanged(learningState.copy(laboratoryTab="nuclear",
                            nuclearProgress=learningState.nuclearProgress.selectChapter(chapter)))
                    },
                )
                "listen" -> NarrationSection(language,::speak)
                else -> {
                    Text(bi(language,"Use the preserved question bank for revision after studying the biology.",
                        "உயிரியலைக் கற்ற பின் ஏற்கெனவே உள்ள வினாவங்கியை மீள்பயிற்சிக்குப் பயன்படுத்தவும்."))
                    Button(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick=onPractice,modifier=Modifier.heightIn(min=48.dp)
                        .fillMaxWidth().testTag("r1-open-practice")) {
                        Text(bi(language,"Open Paramecium practice","பாரமீசியம் வினாப் பயிற்சி"))
                    }
                }
            }

        }
    }
}

@Composable
private fun StudySection(
    language: AppLanguage,
    speak: (BilingualText) -> Unit,
    onAnatomy: () -> Unit,
    onSimulation: () -> Unit,
    learningState: com.gasczoology.invertebratelab.data.NativeLearningState,
    onLearningChanged: (com.gasczoology.invertebratelab.data.NativeLearningState) -> Unit,
) {
    ParameciumTextbookReader(language, speak, onAnatomy, onSimulation,
        sectionId = learningState.textbookSectionId,
        ciliaryStageIndex = learningState.ciliaryStageIndex,
        onSectionSelected = { onLearningChanged(learningState.copy(textbookSectionId = it)) },
        onCiliaryStageSelected = { onLearningChanged(learningState.copy(ciliaryStageIndex = it)) },
        onWaterBalance = { onLearningChanged(learningState.copy(laboratoryTab = "water-balance")) },
        onNuclearChapter = { chapter -> onLearningChanged(learningState.copy(laboratoryTab = "nuclear",
            nuclearProgress = learningState.nuclearProgress.selectChapter(chapter))) })
}

@Composable
private fun NarrationSection(language:AppLanguage,speak:(BilingualText)->Unit) {
    Text(bi(language,"Organ-specific scientific narration","உறுப்பு சார்ந்த அறிவியல் ஒலிவிளக்கம்"),
        modifier=Modifier.testTag("r1-narration-heading"))
    for(organ in ParameciumLearningEngine.organs) {
        Text(organ.name.value(language))
        Text(organ.narration.value(language))
        Button(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={speak(organ.narration)},modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
            .testTag("r1-narrate-"+organ.id)) {
            Text(bi(language,"Play explanation","விளக்கத்தை ஒலிக்கச் செய்"))
        }
    }
    Text(bi(language,"Tamil speech requires an installed Tamil TTS voice. No network access is requested.",
        "தமிழ் ஒலிக்கு சாதனத்தில் தமிழ் உரை-ஒலி குரல் தேவை; இணைய அணுகல் கோரப்படவில்லை."))
}

@Composable
private fun AtlasSection(language:AppLanguage, speak:(BilingualText)->Unit,
    onExploreOsmoregulation:()->Unit) {
    var panel by rememberSaveable { mutableStateOf("external") }
    var selected by rememberSaveable { mutableStateOf("oral-groove") }
    Text(bi(language,"Explore anatomical plates","உடலமைப்புப் படங்களை ஆராய்க"),
        modifier=Modifier.testTag("r1-atlas-heading"))
    Column(modifier=Modifier.fillMaxWidth(),
        verticalArrangement=Arrangement.spacedBy(6.dp)) {
        for(p in listOf("external","oral","vacuole","internal")) {
            OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={
                panel = p
                if (p == "vacuole") selected = ParameciumVacuolePlate.landmarks.first().id
                else if (p != "external") selected = nodes(p).first().id
            },
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r1-atlas-"+p)) {
                Text(when(p) {
                    "external" -> bi(language,"External/cilia","வெளிப்புறம்")
                    "oral" -> bi(language,"Oral feeding","உணவமைப்பு")
                    "vacuole" -> bi(language,"Vacuoles","நுண்குமிழ்கள்")
                    else -> bi(language,"Nuclei/internal","உட்கரு / உள்")
                })
            }
        }
    }
    if(panel=="external") {
        ParameciumExternalCanvas(language, onOrganSelected = { id ->
            speak(ParameciumLearningEngine.organ(id).narration)
        })
    } else if (panel == "vacuole") {
        VacuoleAtlasSection(language, selected,
            onSelect = { selected = it }, speak = speak,
            onExploreOsmoregulation = onExploreOsmoregulation)
    } else {
        val points=nodes(panel)
        AtlasSketch(panel,points,selected,language,{ id ->
            selected=id
            speak(ParameciumLearningEngine.organ(id).narration)
        })
        val organ=ParameciumLearningEngine.organ(selected)
        Text(organ.name.value(language),modifier=Modifier.testTag("r1-atlas-selected"))
        Text(organ.narration.value(language),modifier=Modifier.testTag("r1-atlas-explanation"))
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={speak(organ.narration)},modifier=Modifier.fillMaxWidth()
            .heightIn(min=48.dp).testTag("r1-atlas-speak")) {
            Text(bi(language,"Hear this organ","இந்த உறுப்பின் விளக்கத்தைக் கேள்"))
        }
        for(point in points) {
            OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={
                selected=point.id
                speak(ParameciumLearningEngine.organ(point.id).narration)
            },modifier=Modifier.fillMaxWidth()
                .heightIn(min=48.dp).testTag("r1-atlas-select-"+point.id)) {
                Text(ParameciumLearningEngine.organ(point.id).name.value(language))
            }
        }
        Text(bi(language,"These positions are teaching schematics, not verified whole-cell microscopy coordinates.",
            "இவை கற்பித்தல் வரைபட இடங்கள் மட்டுமே; சரிபார்க்கப்பட்ட நுண்ணோக்கி ஆயத்தொலைவுகள் அல்ல."))
    }
}

/**
 * R1.1 — correctly distinguish osmoregulatory complexes from food vacuoles.
 * Schematic positions and radial-arm numbers are NOT microscopy-derived.
 * The same authored landmarks serve native Canvas input and accessible buttons.
 */
@Composable
private fun VacuoleAtlasSection(
    language: AppLanguage,
    selected: String,
    onSelect: (String) -> Unit,
    speak: (BilingualText) -> Unit,
    onExploreOsmoregulation: () -> Unit,
) {
    val landmark = ParameciumVacuolePlate.landmarks.firstOrNull { it.id == selected }
        ?: ParameciumVacuolePlate.landmarks.first()
    fun activate(id: String) {
        val chosen = ParameciumVacuolePlate.landmarks.single { it.id == id }
        onSelect(id)
        speak(ParameciumLearningEngine.organ(chosen.organId).narration)
    }
    Text(bi(language, "Vacuoles: separate physiological systems",
        "நுண்குமிழ்கள்: வெவ்வேறு உடலியக்க அமைப்புகள்"),
        modifier = Modifier.testTag("r11-vacuole-heading"))
    Canvas(
        modifier = Modifier.fillMaxWidth().height(300.dp)
            .testTag("r11-vacuole-canvas")
            .semantics {
                contentDescription = bi(language,
                    "Schematic showing two contractile vacuole complexes and one distinct food vacuole. Accessible buttons below identify each.",
                    "இரண்டு சுருங்கும் நுண்குமிழ் தொகுதிகளும் தனியான உணவுக் குமிழும் காட்டப்பட்டுள்ளன; கீழுள்ள பொத்தான்கள் மூலம் தேர்வு செய்யலாம்.")
            }
            .pointerInput(onSelect, speak) {
                detectTapGestures { tap ->
                    ParameciumVacuolePlate.hitCanvas(
                        tap.x, tap.y, size.width.toFloat(), size.height.toFloat()
                    )?.let { activate(it.id) }
                }
            }
    ) {
        val w = size.width
        val h = size.height
        val unit = minOf(w, h)
        drawRect(PencilAtlasPalette.paper)
        parameciumCell(Offset(w * .5f, h * .5f), w * .82f, h * .76f)
        for (feature in ParameciumVacuolePlate.landmarks) {
            val center = Offset(w * feature.x, h * feature.y)
            val selectedFeature = feature.id == landmark.id
            if (feature.organId == "contractile-vacuole") {
                val radius = unit * .042f
                drawCircle(PencilAtlasPalette.graphite, radius = radius,
                    center = center, style = Stroke(3.5f))
                // Six representative arms, NOT the verified number or density.
                for (i in 0 until 6) {
                    val angle = i * 2 * PI / 6
                    val dir = Offset(cos(angle).toFloat(), sin(angle).toFloat())
                    drawLine(PencilAtlasPalette.mid,
                        center + dir * (radius + 3f),
                        center + dir * (radius + unit * .045f), 3f)
                }
            } else {
                // Digestive food vacuole is amber and lacks radial collecting arms.
                drawCircle(PencilAtlasPalette.cell, radius = unit * .054f, center = center)
                drawCircle(PencilAtlasPalette.graphite, radius = unit * .054f,
                    center = center, style = Stroke(3f))
                drawCircle(PencilAtlasPalette.graphite, radius = unit * .011f,
                    center = center + Offset(-unit * .014f, unit * .012f))
                drawCircle(PencilAtlasPalette.graphite, radius = unit * .008f,
                    center = center + Offset(unit * .018f, -unit * .013f))
            }
            if (selectedFeature) {
                drawCircle(Color(0xFFB55B16), radius = feature.radiusFraction * unit * .77f,
                    center = center, style = Stroke(4f))
            }
        }
    }
    Text(bi(language,
        "Graphite radial forms indicate contractile vacuoles; the granular round vesicle indicates a digestive food vacuole.",
        "கருநிற ஆர அமைப்புகள் சுருங்கும் நுண்குமிழ்களைக் குறிக்கின்றன; புள்ளியிட்ட தனி வட்டக்குமிழ் செரிமான உணவுக் குமிழ்."),
        modifier = Modifier.testTag("r11-vacuole-distinction"))
    CvcMechanismReviewInset(language, speak)
    Text(bi(language, "Selected landmark: ", "தேர்ந்தெடுத்த அமைப்பு: ") +
        landmark.label.value(language), modifier = Modifier.testTag("r11-vacuole-selected"))
    val lesson = ParameciumLearningEngine.organ(landmark.organId)
    Text(lesson.narration.value(language),
        modifier = Modifier.testTag("r11-vacuole-explanation"))
    for (feature in ParameciumVacuolePlate.landmarks) {
        OutlinedButton(onClick = { activate(feature.id) },
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                .testTag("r11-vacuole-select-" + feature.id)) {
            Text(feature.label.value(language))
        }
    }
    Button(onClick = onExploreOsmoregulation,
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
            .testTag("r11-open-osmoregulation")) {
        Text(bi(language, "Run the contractile-vacuole cycle",
            "சுருங்கும் நுண்குமிழ் சுழற்சியை இயக்குக"))
    }
    Text(bi(language,
        "Research review pending: radial arms, whole-cell positions and organ outlines are illustrative. A food vacuole is not a collecting arm or contractile vacuole.",
        "ஆய்வு நிலுவை: ஆரக் கால்வாய்களின் எண்ணிக்கை, செல் முழுவதிலான இடங்கள், உறுப்புகளின் வடிவங்கள் விளக்கத்திற்கானவை மட்டுமே. உணவுக் குமிழ், சேகரிப்புக் கால்வாயோ சுருங்கும் நுண்குமிழோ அல்ல."),
        modifier = Modifier.testTag("r11-vacuole-review-warning"))
    Text("Scientific reading: https://pubmed.ncbi.nlm.nih.gov/23919298/")
    Text("Scientific reading: https://www.sciencedirect.com/science/article/pii/S1065699502909376")
}


/**
 * R1.2 native CVC organ-mechanism cutaway. The independent microscope reference
 * supports component identity, NOT the illustrated orientation, counts or scale.
 * The 4 phases are visual interpretations of the existing authored lesson stages.
 */
@Composable
private fun CvcMechanismReviewInset(
    language: AppLanguage,
    speak: (BilingualText) -> Unit,
) {
    val existingCycle = remember {
        ParameciumLearningEngine.simulation(ParameciumProcess.OSMOREGULATION)
    }
    var step by rememberSaveable { mutableIntStateOf(0) }
    val stage = existingCycle.stages[step]
    val phase = ParameciumCvcMechanism.phase(stage.id)
    Text(bi(language,
        "Contractile-vacuole complex: mechanism close-up",
        "சுருங்கும் நுண்குமிழ் தொகுதி: செயல்முறை விரிவுக் காட்சி"),
        modifier = Modifier.testTag("r12-cvc-mechanism-title"))
    Text(bi(language,
        "This cutaway enlarges one representative contractile-vacuole complex. The two complexes in the whole-cell atlas are NOT asserted to discharge synchronously.",
        "இந்த விரிவுக் காட்சி ஒரு சுருங்கும் நுண்குமிழ் தொகுதியை எடுத்துக்காட்டுகிறது. முழுச் செல் வரைபடத்தின் இரு தொகுதிகளும் ஒரே நேரத்தில் நீர் வெளியேற்றுவதாகக் கருதக்கூடாது."),
        modifier = Modifier.testTag("r12-cvc-asynchrony-note"))
    Canvas(
        modifier = Modifier.fillMaxWidth().height(270.dp)
            .testTag("r12-cvc-mechanism-canvas")
            .semantics { contentDescription = bi(language,
                "Not-to-scale cutaway: contractile vacuole, spongiome, collecting canals, ampullae and discharge pore. The four stages change fluid filling and pore state.",
                "அளவுக்கேற்றதல்லாத விரிவுக் காட்சி: சுருங்கும் நுண்குமிழ், ஸ்பாஞ்சியோம், சேகரிப்புக் கால்வாய்கள், ஆம்புல்லாக்கள், வெளியேற்றத் துளை.") }
    ) {
        val w = size.width
        val h = size.height
        val unit = minOf(w, h)
        val center = Offset(w * .5f, h * .57f)
        val radius = unit * .12f
        val pore = Offset(center.x, h * .14f)
        drawRect(PencilAtlasPalette.paper)
        // Cortex boundary: the opening belongs to the membrane, not the cytoproct.
        drawLine(Color(0xFF146E76), Offset(w * .10f, pore.y),
            Offset(pore.x - unit * .035f, pore.y), 4f)
        drawLine(Color(0xFF146E76), Offset(pore.x + unit * .035f, pore.y),
            Offset(w * .90f, pore.y), 4f)
        drawCircle(if (phase.poreOpen) Color(0xFFB55B16) else Color(0xFF146E76),
            radius = unit * .025f, center = pore, style = Stroke(4f))
        drawLine(Color(0xFF77B6C8), center - Offset(0f, radius),
            pore + Offset(0f, unit * .025f), 4f)
        val canalColor = if (phase.collectingActive)
            Color(0xFF1269A0) else Color(0xFF75A6BB)
        for (i in 0 until 6) {
            val angle = i * 2 * PI / 6 + PI / 6
            val dir = Offset(cos(angle).toFloat(), sin(angle).toFloat())
            val canalStart = center + dir * (radius + 6f)
            val ampulla = center + dir * (radius + unit * .15f)
            drawLine(canalColor, canalStart, ampulla, 5f)
            drawCircle(if (phase.collectingActive) Color(0xFF59B9D0)
                else Color(0xFFBEDCE4), radius = unit * .03f, center = ampulla)
            // Spongiome: a schematic tubular mesh represented as dots,
            // deliberately not an actual count or SEM/TEM-derived arrangement.
            for (j in 1..3) {
                val pos = ampulla + dir * (j * unit * .024f)
                drawCircle(Color(0xFF88A7B6), radius = unit * .007f, center = pos)
            }
        }
        drawCircle(PencilAtlasPalette.graphite, radius = radius, center = center,
            style = Stroke(4f))
        drawCircle(Color(0xFF8FD5EC), radius = radius * phase.lumenFraction,
            center = center)
        if (phase.poreOpen) {
            for (i in 1..3) {
                drawCircle(Color(0xFF1689C7), radius = unit * .014f,
                    center = pore - Offset(0f, i * unit * .039f))
            }
        }
    }
    Text(bi(language,
        "Structures: central fluid lumen (blue); radial collecting canals (blue lines); ampullae (rounded terminals); spongiome (tubular dotted network); cortical discharge pore (top).",
        "அமைப்புகள்: நடுவிலுள்ள நீர்த்திரவப் பகுதி (நீலம்); சேகரிப்புக் கால்வாய்கள் (நீலக் கோடுகள்); ஆம்புல்லாக்கள் (வட்ட முனைகள்); ஸ்பாஞ்சியோம் (புள்ளிக் குழாய் வலை); புறப்படல வெளியேற்றத் துளை (மேல்)."),
        modifier = Modifier.testTag("r12-cvc-structure-legend"))
    Text(bi(language, "Stage ", "நிலை ") + (step + 1) + "/" +
        existingCycle.stages.size + " — " + stage.heading.value(language),
        modifier = Modifier.testTag("r12-cvc-stage-title"))
    Text(stage.explanation.value(language),
        modifier = Modifier.testTag("r12-cvc-stage-explanation"))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(
            onClick = { step = (step - 1).coerceAtLeast(0) },
            modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                .testTag("r12-cvc-previous")
        ) { Text(bi(language, "Previous", "முந்தையது")) }
        Button(
            onClick = { step = (step + 1) % existingCycle.stages.size },
            modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                .testTag("r12-cvc-next")
        ) { Text(bi(language, "Next", "அடுத்தது")) }
    }
    OutlinedButton(
        onClick = { speak(stage.explanation) },
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
            .testTag("r12-cvc-speak")
    ) {
        Text(bi(language, "Hear this mechanism stage",
            "இச்செயல்நிலையின் ஒலி விளக்கத்தைக் கேள்"))
    }
    Text(bi(language,
        "Review status: DRAFT_UNVERIFIED. Central lumen size, arm count, spongiome arrangement, membrane-pore geometry and stage timing are NOT measured. Contractile-vacuole discharge is separate from cytoproct egestion.",
        "மதிப்பாய்வு நிலை: உறுதிப்படுத்தப்படாத வரைவு. நுண்குமிழ் அளவு, கால்வாய் எண்ணிக்கை, ஸ்பாஞ்சியோம் அமைவு, வெளியேற்றத் துளை வடிவு, கால அளவு ஆகியவை அளவீடுகள் அல்ல. நீர் வெளியேற்றமும் செரியாத உணவுக் கழிவு வெளியேற்றமும் வேறுபட்ட செயல்கள்."),
        modifier = Modifier.testTag("r12-cvc-review-warning"))
    Text("Microscopy (P. caudatum collecting canal and spongiome): " +
        "https://www6.pbrc.hawaii.edu/allen/ch10a/46-pca4201.html")
    Text("Membrane dynamics: " +
        "https://doi.org/10.1111/j.1550-7408.1988.tb04078.x")
}

@Composable
private fun AtlasSketch(panel:String, points:List<AtlasNode>, selected:String,
    language:AppLanguage, onSelect:(String)->Unit) {
    Canvas(modifier=Modifier.fillMaxWidth().height(270.dp).testTag("r1-atlas-canvas")
        .semantics { contentDescription=bi(language,
            "Illustrative organ map; accessible selection buttons follow",
            "உறுப்பு வரைபட முன்மாதிரி; கீழுள்ள பொத்தான்கள் மூலம் தேர்வு செய்யலாம்") }
        .pointerInput(panel,onSelect) {
            detectTapGestures { tap ->
                val x=tap.x/size.width.toFloat();val y=tap.y/size.height.toFloat()
                val near=points.minByOrNull { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y) }
                if(near!=null && (x-near.x)*(x-near.x)+(y-near.y)*(y-near.y)<.0225f)
                    onSelect(near.id)
            }
        }) {
        val w=size.width;val h=size.height
        drawRect(PencilAtlasPalette.paper)
        parameciumCell(Offset(w*.5f,h*.5f),w*.80f,h*.76f)
        when(panel) {
            "oral" -> {
                drawLine(Color(0xFF3185A5),Offset(w*.39f,h*.77f),Offset(w*.48f,h*.59f),7f)
                drawLine(Color(0xFF3185A5),Offset(w*.48f,h*.59f),Offset(w*.56f,h*.51f),7f)
                drawCircle(Color(0xFF5BB7C6),radius=h*.10f,center=Offset(w*.60f,h*.4f))
            }
            "vacuole" -> for(pos in listOf(Offset(w*.32f,h*.40f),
                Offset(w*.70f,h*.62f))) {
                drawCircle(Color(0xFF2386A7),radius=h*.10f,center=pos,style=Stroke(5f))
                for(j in 0 until 6) {
                    val angle=j*2*PI/6
                    val delta=Offset(cos(angle).toFloat(),sin(angle).toFloat())
                    drawLine(Color(0xFF56A6B9),pos+delta*(h*.12f),
                        pos+delta*(h*.20f),3f)
                }
            }
            else -> {
                drawOval(PencilAtlasPalette.hatch,topLeft=Offset(w*.37f,h*.33f),
                    size=Size(w*.25f,h*.23f))
                drawCircle(PencilAtlasPalette.graphite,radius=h*.05f,center=Offset(w*.63f,h*.44f))
                drawCircle(Color(0xFF57AFC8),radius=h*.10f,center=Offset(w*.34f,h*.64f))
            }
        }
        for(p in points) {
            drawCircle(if(p.id==selected)Color(0xFFB55B16)else Color(0xFF11787B),
                radius=if(p.id==selected)14f else 8f,
                center=Offset(p.x*w,p.y*h),style=Stroke(4f))
        }
    }
}

@Composable
private fun SimulatorSection(
    language:AppLanguage,
    speak:(BilingualText)->Unit,
    chosen:String,
    step:Int,
    playing:Boolean,
    onChoose:(String)->Unit,
    onStep:(Int)->Unit,
    onPlaying:(Boolean)->Unit,
    onNuclearChapter:(String)->Unit,
) {
    val process=ParameciumProcess.valueOf(chosen)
    val model=ParameciumLearningEngine.simulation(process)
    LaunchedEffect(playing,chosen,step) {
        if(playing) {
            delay(1600L)
            if(step < model.stages.lastIndex) onStep(step + 1) else onPlaying(false)
        }
    }
    val stage=model.stages[step]
    val progress by animateFloatAsState(stage.illustrationProgress,
        animationSpec=tween(700),label="r1-stage-animation")
    Text(bi(language,"Live teaching simulations","இயங்கும் கற்பித்தல் செயல்முறைகள்"),
        modifier=Modifier.testTag("r1-simulator-heading"))
    for(p in ParameciumProcess.entries) {
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={onChoose(p.name)},modifier=Modifier.fillMaxWidth()
            .heightIn(min=48.dp).testTag("r1-process-"+p.name.lowercase())) {
            Text(ParameciumLearningEngine.simulation(p).title.value(language))
        }
    }
    Text(model.title.value(language))
    if(process==ParameciumProcess.BINARY_FISSION || process==ParameciumProcess.CONJUGATION) {
        Button(onClick={onNuclearChapter(if(process==ParameciumProcess.BINARY_FISSION)"fission" else "conjugation")},
            shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r17-from-simulation")) {
            Text(bi(language,"Open the complete textbook and nuclear sequence","முழுப் பாடத்தையும் உட்கருத் தொடரையும் திற"))
        }
    }
    ProcessSketch(process,progress,language)
    Text(bi(language,"Stage ","நிலை ")+(step+1).toString()+"/"+
        model.stages.size.toString()+" — "+stage.heading.value(language),
        modifier=Modifier.testTag("r1-stage-title"))
    Text(stage.explanation.value(language),modifier=Modifier.testTag("r1-stage-explanation"))
    Column(modifier=Modifier.fillMaxWidth(),
        verticalArrangement=Arrangement.spacedBy(6.dp)) {
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={onStep(model.previous(step));onPlaying(false)},
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r1-prev")) {
            Text(bi(language,"Previous","முந்தையது"))
        }
        Button(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={
            if(step==model.stages.lastIndex)onStep(0)
            onPlaying(!playing)
        },
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r1-play")) {
            Text(if(playing)bi(language,"Pause","இடைநிறுத்து")
                else bi(language,"Play","இயக்கு"))
        }
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={onStep(model.advance(step));onPlaying(false)},
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r1-next")) {
            Text(bi(language,"Next","அடுத்தது"))
        }
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={onStep(0);onPlaying(false)},
            modifier=Modifier.fillMaxWidth().heightIn(min=48.dp).testTag("r1-replay")) {
            Text(bi(language,"Replay","மீளியக்கு"))
        }
    }
    Button(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={speak(stage.explanation)},modifier=Modifier.fillMaxWidth()
        .heightIn(min=48.dp).testTag("r1-stage-audio")) {
        Text(bi(language,"Speak this physiological stage",
            "இந்நிலையின் உடலியங்கியலை ஒலிக்கச் செய்"))
    }
    Text(model.teachingCaution.value(language),modifier=Modifier.testTag("r1-simulation-caution"))
    Text(model.scientificSource)
    Checkpoint(process,language)
}

@Composable
private fun Checkpoint(process:ParameciumProcess,language:AppLanguage) {
    var answer by rememberSaveable(process.name) { mutableIntStateOf(-1) }
    val statement=when(process) {
        ParameciumProcess.OSMOREGULATION -> BilingualText(
            "The contractile vacuole primarily regulates excess water, not digestion.",
            "சுருங்கும் நுண்குமிழ் மிகைநீரைச் சீராக்குகிறது; உணவு செரிப்பதில்லை.")
        ParameciumProcess.CILIARY_MOTION -> BilingualText(
            "Coordinated cilia contribute to Paramecium locomotion.",
            "ஒருங்கிணைந்த குறுஇழைகள் பாரமீசியம் நகர உதவுகின்றன.")
        ParameciumProcess.FEEDING -> BilingualText(
            "Food vacuoles form only when two Paramecium cells conjugate.",
            "இரண்டு பாரமீசியம் செல்கள் இணையும் போது மட்டுமே உணவுக் குமிழ்கள் உருவாகின்றன.")
        ParameciumProcess.BINARY_FISSION -> BilingualText(
            "Transverse binary fission increases the number of cells.",
            "குறுக்குத் திசை இருபிளவு செல் எண்ணிக்கையை அதிகரிக்கிறது.")
        ParameciumProcess.CONJUGATION -> BilingualText(
            "Conjugation immediately increases cell number like binary fission.",
            "இணைவு இருபிளவைப் போலவே உடனடியாக செல் எண்ணிக்கையை அதிகரிக்கிறது.")
    }
    val correct=process!=ParameciumProcess.FEEDING && process!=ParameciumProcess.CONJUGATION
    Text(bi(language,"Think & check: true or false?","சிந்தித்து சரிபார்: சரியா, தவறா?"))
    Text(statement.value(language))
    Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={answer=1},modifier=Modifier.heightIn(min=48.dp)
            .testTag("r1-answer-true")) { Text(bi(language,"True","சரி")) }
        OutlinedButton(shape=androidx.compose.foundation.shape.RoundedCornerShape(12.dp),onClick={answer=0},modifier=Modifier.heightIn(min=48.dp)
            .testTag("r1-answer-false")) { Text(bi(language,"False","தவறு")) }
    }
    if(answer>=0) Text(if((answer==1)==correct)
        bi(language,"Correct. Your answer matches the biological explanation.",
            "சரி. உங்கள் விடை உயிரியல் விளக்கத்துடன் பொருந்துகிறது.")
        else bi(language,"Revisit the stage sequence and try again.",
            "நிலைகளை மீண்டும் படித்து முயலவும்."),
        modifier=Modifier.testTag("r1-checkpoint-feedback"))
}

@Composable
private fun ProcessSketch(process:ParameciumProcess,progress:Float,language:AppLanguage) {
    Canvas(modifier=Modifier.fillMaxWidth().height(260.dp)
        .testTag("r1-process-canvas")
        .semantics { contentDescription=bi(language,
            "Schematic visualization changes with stage; read the stage explanation below",
            "நிலைக்கு ஏற்ப மாறும் விளக்க வரைபடம்; நிலை விளக்கம் கீழே உள்ளது") }) {
        val w=size.width;val h=size.height;val center=Offset(w*.5f,h*.5f)
        drawRect(PencilAtlasPalette.paper)
        if(process==ParameciumProcess.CONJUGATION) {
            parameciumCell(Offset(w*.5f,h*.34f),w*.60f,h*.26f)
            parameciumCell(Offset(w*.5f,h*.67f),w*.60f,h*.26f)
            drawCircle(Color(0xFF765DA7),radius=11f,center=Offset(w*.45f,h*.33f))
            drawCircle(Color(0xFF765DA7),radius=11f,center=Offset(w*.55f,h*.67f))
            if(progress>.30f)drawLine(Color(0xFFB55B16),
                Offset(w*.46f,h*.46f),Offset(w*.54f,h*.55f),5f)
            if(progress>.65f) {
                drawCircle(Color(0xFFB55B16),radius=8f,center=Offset(w*.54f,h*.33f))
                drawCircle(Color(0xFFB55B16),radius=8f,center=Offset(w*.46f,h*.67f))
            }
        } else if(process==ParameciumProcess.BINARY_FISSION) {
            if(progress>=.99f) {
                parameciumCell(Offset(w*.29f,h*.50f),w*.36f,h*.43f)
                parameciumCell(Offset(w*.71f,h*.50f),w*.36f,h*.43f)
            } else {
                parameciumCell(center,w*.73f,h*.64f)
                drawCircle(Color(0xFF765DA7),radius=13f,center=Offset(w*.40f,h*.50f))
                if(progress>.30f)drawCircle(Color(0xFF765DA7),radius=13f,
                    center=Offset(w*.61f,h*.50f))
                if(progress>.60f)drawLine(Color(0xFFB55B16),
                    Offset(w*.5f,h*(.17f+progress*.12f)),
                    Offset(w*.5f,h*(.83f-progress*.12f)),5f)
            }
        } else {
            parameciumCell(center,w*.73f,h*.64f)
            when(process) {
                ParameciumProcess.OSMOREGULATION -> {
                    val radius=9f+progress*20f
                    for(v in listOf(Offset(w*.32f,h*.39f),Offset(w*.70f,h*.60f))) {
                        drawCircle(Color(0xFF1C85A7),radius=if(progress>=1f)7f else radius,
                            center=v,style=Stroke(4f))
                        for(k in 0 until 6) {
                            val a=k*2*PI/6
                            val d=Offset(cos(a).toFloat(),sin(a).toFloat())
                            drawLine(Color(0xFF63AFC3),v+d*(radius+2f),
                                v+d*(radius+19f),3f)
                        }
                    }
                    if(progress>=1f)drawCircle(Color(0xFF1C85A7),radius=8f,
                        center=Offset(w*.88f,h*.31f))
                }
                ParameciumProcess.CILIARY_MOTION -> {
                    for(k in 0 until 34) {
                        val a=2*PI*k/34
                        val d=Offset(cos(a).toFloat(),sin(a).toFloat())
                        val p=center+Offset(d.x*w*.365f,d.y*h*.32f)
                        val swing=sin(progress*2*PI+k*.34).toFloat()*14f
                        drawLine(Color(0xFF176F7F),p,
                            p+Offset(d.x*16f-d.y*swing,d.y*16f+d.x*swing),3f)
                    }
                    drawLine(Color(0xFFB55B16),Offset(w*.37f,h*.51f),
                        Offset(w*.62f,h*.51f),4f)
                }
                ParameciumProcess.FEEDING -> {
                    drawLine(Color(0xFF147C99),Offset(w*.43f,h*.77f),
                        Offset(w*.56f,h*.52f),6f)
                    val x=if(progress<.3f)w*(.40f+progress*.50f)
                        else if(progress<.7f)w*(.55f+(progress-.3f)*.2f)
                        else w*(.63f+(progress-.7f)*.68f)
                    val y=if(progress<.3f)h*(.75f-progress*.6f)
                        else if(progress<.7f)h*(.57f-(progress-.3f)*.4f)
                        else h*(.41f+(progress-.7f)*.9f)
                    drawCircle(Color(0xFFB55B16),radius=11f,center=Offset(x,y))
                    drawCircle(Color(0xFF288EB2),radius=19f,
                        center=Offset(w*.62f,h*.42f),style=Stroke(4f))
                }
                else -> Unit
            }
        }
    }
}

private fun DrawScope.parameciumCell(center:Offset,w:Float,h:Float) {
    val top=Offset(center.x-w/2,center.y-h/2)
    drawOval(PencilAtlasPalette.cell,topLeft=top,size=Size(w,h))
    drawOval(PencilAtlasPalette.hatch,topLeft=top,size=Size(w,h),
        style=Stroke(7f))
    drawOval(PencilAtlasPalette.graphite,topLeft=top,size=Size(w,h),
        style=Stroke(3.2f))
    for(i in 0 until 40) {
        val a=2*PI*i/40
        drawCircle(PencilAtlasPalette.mid,radius=1.5f,
            center=Offset(center.x+cos(a).toFloat()*w*.30f,
                center.y+sin(a).toFloat()*h*.30f))
    }
}
