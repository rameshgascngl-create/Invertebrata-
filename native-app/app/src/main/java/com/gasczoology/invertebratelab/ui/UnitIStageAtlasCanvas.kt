package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.StageAtlasPlate
import com.gasczoology.invertebratelab.data.UnitIStageAtlas

/**
 * U1.15: ORIGINAL PROGRAMMATIC *DIGITAL* graphite-toned concepts in native
 * Android Canvas. NOT physically pencil-drawn, photographs, diagnostic
 * reference specimens or source-verified anatomical plates.
 *
 * Structure selection comes from explicit buttons, not unreviewed
 * source-raster hotspots. No source image is substituted.
 */
private val atlasPaper = Color(0xFFFAF8F2)
private val atlasInk = Color(0xFF393B3C)
private val atlasPale = Color(0xFFD0D0CB)
private val atlasMark = Color(0xFF92472D)

@Composable
internal fun UnitIStageAtlasChapter(language: AppLanguage) {
    val tamil = language == AppLanguage.TAMIL
    val context = LocalContext.current
    val narrator = remember(context) { OfflineNarrator(context.applicationContext) }
    val narratorPhase by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }

    var plateId by rememberSaveable { mutableStateOf(UnitIStageAtlas.plates.first().id) }
    val plate = requireNotNull(UnitIStageAtlas.get(plateId))
    var structureId by rememberSaveable(plateId) {
        mutableStateOf(plate.structures.first().id)
    }
    val structure = plate.structures.firstOrNull { it.id == structureId }
        ?: plate.structures.first()
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(plateId, structureId, language) { narrator.stop() }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("u115-morphotype-atlas"),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Text(
                if (tamil) "கசையிழை ஒட்டுண்ணிகள் — ஒப்பீட்டு உடலமைப்பு வரைபடம்"
                else "Flagellate anatomy — comparative drawing",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("u115-heading").semantics { heading() },
            )
            Text(
                if (tamil)
                    "ஆண்ட்ராய்டு Canvas மூலம் வரையப்பட்ட டிஜிட்டல் கல்வி விளக்கப்படம்; கையால் வரையப்பட்ட பென்சில் ஸ்கேனோ உறுதிசெய்யப்பட்ட நுண்ணோக்கிப் படமோ அல்ல. குறிகள் மூலப்படத்தின் அங்கீகரிக்கப்பட்ட புள்ளிகள் அல்ல."
                else
                    "Native Android digital teaching schematic — NOT a hand-scanned pencil master, microscope image, or approved source-image hotspot. Distinct stages must not be interchanged.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u115-digital-artwork-warning"),
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().testTag("u115-stage-picker"),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(UnitIStageAtlas.plates, key = { it.id }) { option ->
                    FilterChip(
                        selected = option.id == plateId,
                        onClick = { plateId = option.id },
                        label = { Text(if (tamil) option.taxonTa else option.taxonEn) },
                        modifier = Modifier.heightIn(min = 48.dp)
                            .testTag("u115-stage-" + option.id),
                    )
                }
            }
            Text(
                if (tamil) plate.taxonTa else plate.taxonEn,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("u115-current-stage"),
            )
            AtlasDrawing(
                plate = plate,
                selectedStructureId = structure.id,
                language = language,
                modifier = Modifier.fillMaxWidth().height(255.dp)
                    .testTag("u115-concept-canvas"),
            )
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .testTag("u115-enlarge"),
            ) { Text(if (tamil) "கல்வி வரைபடத்தைப் பெரிதாக்கு" else "Enlarge conceptual drawing") }
            Text(
                if (tamil) plate.contextTa else plate.contextEn,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("u115-stage-explanation"),
            )
            Text(
                if (tamil) "அமைப்பை விளக்கமாகத் தேர்ந்தெடு — படத்தைத் தொடும் மருத்துவக் குறியீடு அல்ல."
                else "Select a named structure for teaching emphasis; these are not calibrated source-image touch hotspots.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().testTag("u115-structure-picker"),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(plate.structures, key = { it.id }) { item ->
                    FilterChip(
                        selected = item.id == structure.id,
                        onClick = { structureId = item.id },
                        label = { Text(if (tamil) item.nameTa else item.nameEn) },
                        modifier = Modifier.heightIn(min = 48.dp)
                            .testTag("u115-organ-" + item.id),
                    )
                }
            }
            Text(
                if (tamil) structure.nameTa else structure.nameEn,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("u115-organ-name"),
            )
            Text(
                if (tamil) structure.explanationTa else structure.explanationEn,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("u115-organ-explanation"),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        narrator.speak(
                            BilingualText(
                                structure.nameEn + ". " + structure.explanationEn,
                                structure.nameTa + ". " + structure.explanationTa,
                            ),
                            language,
                        )
                    },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u115-listen"),
                ) {
                    Text(if (tamil) "விளக்கத்தைக் கேள்" else "Hear explanation")
                }
                OutlinedButton(
                    onClick = { narrator.stop() },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag("u115-stop-audio"),
                ) {
                    Text(if (tamil) "நிறுத்து" else "Stop narration")
                }
            }
            Text(
                (if (tamil) "சாதனத்தின் ஆஃப்லைன் குரல் நிலை: "
                 else "Device offline speech: ") + narratorPhase.name,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.testTag("u115-narrator-status"),
            )
            Text(
                (if (tamil) "உயிரியல் ஆதாரம்: " else "Biological reference: ") +
                    plate.source,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Surface(modifier = Modifier.fillMaxSize().testTag("u115-expanded")) {
                Column(
                    modifier = Modifier.fillMaxSize().statusBarsPadding()
                        .navigationBarsPadding().padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        if (tamil) plate.taxonTa else plate.taxonEn,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        if (tamil) "டிஜிட்டல் கல்வி வரைபடம்; அசல் பென்சில் ஸ்கேன் அல்ல"
                        else "DIGITAL CONCEPT — NOT A PENCIL MASTER",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge,
                    )
                    AtlasDrawing(
                        plate, structure.id, language,
                        Modifier.fillMaxWidth().weight(1f).testTag("u115-expanded-canvas"),
                    )
                    Text(
                        if (tamil) structure.explanationTa else structure.explanationEn,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Button(
                        onClick = { expanded = false },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u115-expanded-close"),
                    ) { Text(if (tamil) "மூடு" else "Close") }
                }
            }
        }
    }
}

@Composable
private fun AtlasDrawing(
    plate: StageAtlasPlate,
    selectedStructureId: String,
    language: AppLanguage,
    modifier: Modifier,
) {
    val label = if (language == AppLanguage.TAMIL) plate.taxonTa else plate.taxonEn
    val anatomy = plate.structures.firstOrNull { it.id == selectedStructureId }
    Canvas(
        modifier = modifier.semantics {
            contentDescription = (if (language == AppLanguage.TAMIL)
                "டிஜிட்டல் கல்வி வரைபடம். தேர்ந்த அமைப்பு: "
                else "Digital teaching schematic. Selected structure: ") +
                (if (language == AppLanguage.TAMIL) anatomy?.nameTa else anatomy?.nameEn).orEmpty() +
                ". " + label
        },
    ) {
        drawAtlas(plate.id, anatomy?.x, anatomy?.y)
    }
}

private fun DrawScope.drawAtlas(stage: String, focusX: Float?, focusY: Float?) {
    drawRect(atlasPaper)
    val factor = minOf(size.width / 1000f, size.height / 560f)
    val ox = (size.width - 1000f * factor) / 2f
    val oy = (size.height - 560f * factor) / 2f
    fun p(x: Float, y: Float) = Offset(ox + x * factor, oy + y * factor)
    fun path(block: Path.() -> Unit): Path = Path().apply(block)
    fun graphite(path: Path, light: Boolean = true) {
        if (light) drawPath(path, color = Color(0xFFE4E3DE))
        drawPath(path, color = atlasInk, style = Stroke(width = 3f * factor))
    }
    fun dot(x: Float, y: Float, radius: Float, light: Boolean = false) {
        drawCircle(if (light) atlasPale else atlasInk, radius * factor, p(x, y))
    }
    fun ring(x: Float, y: Float, rx: Float, ry: Float) {
        drawOval(atlasInk, topLeft = p(x-rx, y-ry),
            size = Size(2f * rx * factor, 2f * ry * factor),
            style = Stroke(width = 2.1f * factor))
    }
    when (stage) {
        "brucei-blood" -> {
            val outline = path {
                moveTo(p(155f, 405f).x, p(155f, 405f).y)
                cubicTo(p(258f,330f).x,p(258f,330f).y,p(332f,260f).x,p(332f,260f).y,p(431f,271f).x,p(431f,271f).y)
                cubicTo(p(551f,275f).x,p(551f,275f).y,p(659f,192f).x,p(659f,192f).y,p(793f,128f).x,p(793f,128f).y)
                cubicTo(p(760f,178f).x,p(760f,178f).y,p(666f,262f).x,p(666f,262f).y,p(553f,307f).x,p(553f,307f).y)
                cubicTo(p(415f,355f).x,p(415f,355f).y,p(315f,381f).x,p(315f,381f).y,p(239f,416f).x,p(239f,416f).y)
                cubicTo(p(205f,426f).x,p(205f,426f).y,p(167f,417f).x,p(167f,417f).y,p(155f,405f).x,p(155f,405f).y)
                close()
            }
            graphite(outline)
            // Attached flagellum with conspicuous undulating membrane.
            val wave = path {
                moveTo(p(230f,375f).x,p(230f,375f).y)
                cubicTo(p(309f,274f).x,p(309f,274f).y,p(333f,304f).x,p(333f,304f).y,p(400f,252f).x,p(400f,252f).y)
                cubicTo(p(444f,291f).x,p(444f,291f).y,p(481f,232f).x,p(481f,232f).y,p(523f,248f).x,p(523f,248f).y)
                cubicTo(p(579f,260f).x,p(579f,260f).y,p(625f,192f).x,p(625f,192f).y,p(684f,192f).x,p(684f,192f).y)
                cubicTo(p(725f,180f).x,p(725f,180f).y,p(749f,153f).x,p(749f,153f).y,p(793f,128f).x,p(793f,128f).y)
                cubicTo(p(841f,112f).x,p(841f,112f).y,p(839f,65f).x,p(839f,65f).y,p(921f,69f).x,p(921f,69f).y)
            }
            drawPath(wave, color = atlasInk, style = Stroke(width = 3f * factor))
            dot(252f,359f,7.5f)
            ring(488f,281f,25f,19f)
            dot(488f,281f,8f,light=true)
            for (i in 0..16) {
                val x = 282f + 20f*i
                val y = 359f - 6.5f*i
                drawLine(atlasPale, p(x,y), p(x+12f,y-16f), 1f*factor)
            }
        }
        "leishmania-amastigote" -> {
            val host = path {
                moveTo(p(143f,220f).x,p(143f,220f).y)
                cubicTo(p(105f,90f).x,p(105f,90f).y,p(380f,80f).x,p(380f,80f).y,p(518f,128f).x,p(518f,128f).y)
                cubicTo(p(790f,85f).x,p(790f,85f).y,p(925f,248f).x,p(925f,248f).y,p(845f,400f).x,p(845f,400f).y)
                cubicTo(p(754f,530f).x,p(754f,530f).y,p(345f,487f).x,p(345f,487f).y,p(171f,415f).x,p(171f,415f).y)
                cubicTo(p(95f,372f).x,p(95f,372f).y,p(105f,274f).x,p(105f,274f).y,p(143f,220f).x,p(143f,220f).y)
                close()
            }
            graphite(host)
            ring(343f,300f,67f,56f)
            for (i in 0..15) {
                val r = 16f + (i%5)*8f
                dot(343f+(i%4-2)*r*.7f,300f+(i/4-2)*r*.4f,1.6f)
            }
            val xs=floatArrayOf(636f,532f,754f,609f,788f)
            val ys=floatArrayOf(258f,375f,353f,415f,231f)
            xs.indices.forEach { i ->
                ring(xs[i],ys[i],30f,22f)
                dot(xs[i]-4f,ys[i]-6f,6.5f)
                dot(xs[i]+24f,ys[i]+11f,3.5f)
            }
            // Do not draw free flagella on intracellular amastigotes.
        }
        "leishmania-promastigote" -> {
            val cell = path {
                moveTo(p(328f,430f).x,p(328f,430f).y)
                cubicTo(p(402f,359f).x,p(402f,359f).y,p(477f,286f).x,p(477f,286f).y,p(584f,235f).x,p(584f,235f).y)
                cubicTo(p(663f,196f).x,p(663f,196f).y,p(706f,187f).x,p(706f,187f).y,p(724f,181f).x,p(724f,181f).y)
                cubicTo(p(683f,239f).x,p(683f,239f).y,p(612f,325f).x,p(612f,325f).y,p(514f,367f).x,p(514f,367f).y)
                cubicTo(p(425f,409f).x,p(425f,409f).y,p(362f,431f).x,p(362f,431f).y,p(328f,430f).x,p(328f,430f).y)
                close()
            }
            graphite(cell)
            val flagellum = path {
                moveTo(p(724f,181f).x,p(724f,181f).y)
                cubicTo(p(770f,132f).x,p(770f,132f).y,p(815f,115f).x,p(815f,115f).y,p(827f,88f).x,p(827f,88f).y)
                cubicTo(p(861f,53f).x,p(861f,53f).y,p(900f,67f).x,p(900f,67f).y,p(944f,41f).x,p(944f,41f).y)
            }
            drawPath(flagellum,color=atlasInk,style=Stroke(width=3f*factor))
            ring(529f,313f,23f,17f)
            dot(665f,244f,6f)
            for (i in 0..12) {
                val x=380f+i*23f
                val y=397f-i*10f
                drawLine(atlasPale,p(x,y),p(x+11f,y-12f),1f*factor)
            }
            // No undulating membrane on Leishmania promastigote.
        }
    }
    if (focusX != null && focusY != null) {
        drawCircle(atlasMark,radius=25f*factor,center=p(focusX,focusY),
            style=Stroke(width=3f*factor))
        drawCircle(atlasMark,radius=3.5f*factor,center=p(focusX,focusY))
    }
}
