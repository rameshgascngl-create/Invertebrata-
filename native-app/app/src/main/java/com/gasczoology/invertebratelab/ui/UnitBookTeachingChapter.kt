package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

internal data class ReadingPositionAccess(
    val positions: Map<String, BookReadingPosition> = emptyMap(),
    val save: (String, BookReadingPosition) -> Unit = { _, _ -> },
)
internal val LocalReadingPositions = staticCompositionLocalOf { ReadingPositionAccess() }
internal val ReadingPositionSemantics = SemanticsPropertyKey<BookReadingPosition>("BookReadingPosition")
internal val ReadingCanScrollForwardSemantics = SemanticsPropertyKey<Boolean>("BookCanScrollForward")
private fun ut(lang: AppLanguage, en: String, ta: String) = if (lang == AppLanguage.TAMIL) ta else en

/** Default logical pages, with a deliberate continuous-reading option for reference work. */
@Composable
internal fun UnitBookTeachingChapter(
    chapter: Chapter, book: BookChapter, language: AppLanguage, onBack: () -> Unit,
    revealedQuestionId: String, onToggle: (A5Question) -> Unit,
    containerPrefix: String = "book-reader-", backTag: String = "book-back",
    continuousReader: (@Composable () -> Unit)? = null,
    interactiveReader: (@Composable () -> Unit)? = null,
    onOpenFullLesson: (() -> Unit)? = null,
    onOpenAnatomy: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val narrator = remember(context, chapter.id) { OfflineNarrator(context) }
    DisposableEffect(narrator) { onDispose { narrator.close() } }
    LaunchedEffect(language) { narrator.stop() }
    val positions by rememberUpdatedState(LocalReadingPositions.current)
    val pages = remember(book, chapter.a5Questions) { BookReadingPages.forChapter(book, chapter.a5Questions.map { it.id }) }
    var pageId by rememberSaveable(chapter.id) {
        mutableStateOf(positions.positions[chapter.id]?.pageId?.takeIf { id -> pages.any { it.id == id } } ?: pages.first().id)
    }
    var pageHistory by rememberSaveable(chapter.id) { mutableStateOf(emptyList<String>()) }
    var continuous by rememberSaveable(chapter.id) { mutableStateOf(false) }
    var referenceGalleryOpen by rememberSaveable(chapter.id) { mutableStateOf(false) }
    var trypanosomaStage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    var leishmaniaStage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    var penaeusAppendage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    var penaeusStage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    val pageIndex = pages.indexOfFirst { it.id == pageId }.coerceAtLeast(0)
    val page = pages[pageIndex]
    val listState = remember(chapter.id, pageId, continuous, referenceGalleryOpen) {
        val stored = positions.positions[chapter.id]
        LazyListState(if (!referenceGalleryOpen && stored?.pageId == pageId) stored.itemIndex else 0,
            if (!referenceGalleryOpen && stored?.pageId == pageId) stored.itemOffset else 0)
    }
    val activePage by rememberUpdatedState(pageId)
    fun savePosition() { if (!referenceGalleryOpen) positions.save(chapter.id, BookReadingPosition(pageId, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)) }
    fun selectPage(id: String) {
        narrator.stop()
        referenceGalleryOpen = false
        continuous = false
        if (id != pageId) pageHistory = pageHistory + pageId
        pageId = id
        positions.save(chapter.id, BookReadingPosition(id))
    }
    androidx.activity.compose.BackHandler {
        narrator.stop()
        when {
            referenceGalleryOpen -> referenceGalleryOpen = false
            continuous -> continuous = false
            pageHistory.isNotEmpty() -> {
                pageId = pageHistory.last()
                pageHistory = pageHistory.dropLast(1)
                positions.save(chapter.id, BookReadingPosition(pageId))
            }
            else -> { savePosition(); onBack() }
        }
    }
    LaunchedEffect(chapter.id, pageId, listState, continuous) {
        if (!continuous && !referenceGalleryOpen) snapshotFlow { Triple(listState.isScrollInProgress, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) }
            .filter { !it.first }.distinctUntilChanged().collect { (_, item, offset) ->
                positions.save(chapter.id, BookReadingPosition(pageId, item, offset))
            }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(chapter.id, pageId, listState, lifecycle, continuous) {
        val thisPage = pageId
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) { narrator.stop(); if (!continuous) savePosition() }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            if (!continuous && activePage == thisPage) savePosition()
        }
    }
    val referenceStudy = remember(chapter.id) {
        NativeCurriculumManifest.typeStudies.firstOrNull {
            it.lessonId == chapter.id && it.organismId in setOf("penaeus", "pila", "asterias")
        }
    }
    Scaffold(
        topBar = {
            Row(Modifier.fillMaxWidth().statusBarsPadding(), horizontalArrangement = Arrangement.SpaceEvenly) {
                if (!(continuous && continuousReader != null)) TextButton(onClick = { savePosition(); onBack() },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag(backTag)) {
                    Text(ut(language, "Back", "பின்செல்"))
                }
                TextButton(onClick = { selectPage("contents") }, modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("reading-contents")) {
                    Text(ut(language, "Contents", "பொருளடக்கம்"))
                }
                TextButton(onClick = { continuous = !continuous; referenceGalleryOpen = false },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag(if (continuous) "reading-mode-paged" else "reading-mode-continuous")) {
                    Text(ut(language, if (continuous) "Pages" else "All pages", if (continuous) "பக்கங்கள்" else "முழுப் பாடம்"))
                }
            }
        },
        bottomBar = {
            if (!continuous && !referenceGalleryOpen) Column(Modifier.navigationBarsPadding().padding(horizontal = 8.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedButton(onClick = { selectPage(pages[pageIndex - 1].id) }, enabled = pageIndex > 0,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("reading-page-previous")
                            .semantics { contentDescription = ut(language, "Previous page", "முந்தைய பக்கம்") }) { Text("‹") }
                    Text(ut(language, "Page ${pageIndex + 1}/${pages.size}", "பக்கம் ${pageIndex + 1}/${pages.size}"),
                        modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically).testTag("reading-page-position"),
                        style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = { selectPage(pages[pageIndex + 1].id) }, enabled = pageIndex < pages.lastIndex,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("reading-page-next")
                            .semantics { contentDescription = ut(language, "Next page", "அடுத்த பக்கம்") }) { Text("›") }
                }
            }
        },
    ) { padding ->
        if (continuous && continuousReader != null) {
            Box(Modifier.fillMaxSize().padding(padding)) { continuousReader() }
        } else if (!continuous && pageId == "interactive" && interactiveReader != null) {
            Box(Modifier.fillMaxSize().padding(padding)) { interactiveReader() }
        } else LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(padding).testTag(containerPrefix + chapter.id).semantics {
                this[ReadingPositionSemantics] = BookReadingPosition(pageId, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
                this[ReadingCanScrollForwardSemantics] = listState.canScrollForward
            },
            contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item(key = "header") {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(book.title.value(language), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    ChapterDraftNotice(book.status, language)
                    if (!continuous) Text(page.title.value(language), modifier = Modifier.testTag("reading-page-id").semantics {
                        contentDescription = page.id; heading()
                    }, style = MaterialTheme.typography.titleMedium)
                }
            }
            if (referenceStudy != null) item(key = "optional-reference-gallery-toggle") {
                OutlinedButton(onClick = { referenceGalleryOpen = !referenceGalleryOpen },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("book-reference-gallery-toggle-" + chapter.id)) {
                    Text(ut(language, if (referenceGalleryOpen) "Return to textbook" else "Open anatomical reference gallery",
                        if (referenceGalleryOpen) "பாடநூலுக்குத் திரும்பு" else "உடற்கூறு ஆதாரப் படத் தொகுப்பைத் திற"))
                }
            }
            if (referenceGalleryOpen && referenceStudy != null) item { RequiredTypeStudyPlates(referenceStudy, language) }
            else {
                if (!continuous && pageId == "contents") {
                    items(pages, key = { "contents-" + it.id }) { destination ->
                        OutlinedButton(onClick = { selectPage(destination.id) },
                            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("reading-open-" + destination.id)) {
                            Text("${pages.indexOf(destination) + 1}. " + destination.title.value(language))
                        }
                    }
                } else bookChapterItems(book, language, if (continuous) null else page, ::selectPage) { narrator.speak(it, language) }
                if (continuous || pageId in setOf("trypanosoma", "leishmania", "appendages", "larvae", "interactive")) {
                    if (chapter.id == "u1-proto-parasites") {
                        if (continuous || pageId == "trypanosoma") item(key = "trypanosoma-cycle") {
                            UnitIFlagellateLifeCycleSimulator("trypanosoma", language, trypanosomaStage) { trypanosomaStage = it }
                        }
                        if (continuous || pageId == "leishmania") item(key = "leishmania-cycle") {
                            UnitIFlagellateLifeCycleSimulator("leishmania", language, leishmaniaStage) { leishmaniaStage = it }
                        }
                    }
                    if (chapter.id == "u4-penaeus") {
                        if (continuous || pageId == "appendages") item(key = "penaeus-appendages") {
                            UnitIVPenaeusAppendageExplorer(language, penaeusAppendage) { penaeusAppendage = it }
                        }
                        if (continuous || pageId == "larvae") item(key = "penaeus-larvae") {
                            UnitIVPenaeusLifeCycleExplorer(language, penaeusStage) { penaeusStage = it }
                        }
                    }
                    if (chapter.id == "u1-paramecium") item(key = "paramecium-laboratory") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            onOpenFullLesson?.let { open -> Button(onClick = open, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("r14-chapter-open-textbook")) {
                                Text(ut(language, "Open the illustrated laboratory and simulations", "படவிளக்க ஆய்வகத்தையும் இயக்கக் காட்சிகளையும் திற"))
                            } }
                            onOpenAnatomy?.let { open -> OutlinedButton(onClick = open, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("u1-paramecium-open-diagrams")) {
                                Text(ut(language, "Open anatomy plates", "உடலமைப்புப் படங்களைத் திற"))
                            } }
                        }
                    }
                }
                if (continuous || pageId.startsWith("practice-")) {
                    item(key = "practice-heading") {
                        Text(ut(language, "EXAM PRACTICE · AFTER THE LESSON", "தேர்வுப் பயிற்சி · பாடத்திற்குப் பின்"),
                            style = MaterialTheme.typography.titleLarge, modifier = Modifier.testTag("book-practice-heading").semantics { heading() })
                    }
                    items(chapter.a5Questions.filter { continuous || pageId == "practice-${it.id}" }, key = { it.id }) { question ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(question.question.value(language), style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.testTag((if (chapter.unitNumber == 3) "u3-question-" else "a5-question-") + question.id))
                                OutlinedButton(onClick = { onToggle(question) }, modifier = Modifier.heightIn(min = 48.dp).testTag("a5-toggle-" + question.id)) {
                                    Text(ut(language, if (revealedQuestionId == question.id) "Hide answer" else "Reveal five points",
                                        if (revealedQuestionId == question.id) "விடையை மறை" else "ஐந்து குறிப்புகளைக் காட்டு"))
                                }
                                if (revealedQuestionId == question.id) question.answerPoints.forEachIndexed { at, point ->
                                    Text("${at + 1}. " + point.value(language), modifier = Modifier.testTag("a5-point-${question.id}-${at + 1}"))
                                }
                            }
                        }
                    }
                }
                if (chapter.id == "u4-nereis" && (continuous || pageId == "references")) {
                    items((0..7).toList(), key = { "u44-nereis-plate-$it" }) { UnitIVNereisStaticPlate(it, language) }
                }
            }
        }
    }
}
