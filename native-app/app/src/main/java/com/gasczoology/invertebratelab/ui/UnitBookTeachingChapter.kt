package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

internal data class ReadingPositionAccess(
    val positions: Map<String, BookReadingPosition> = emptyMap(),
    val save: (String, BookReadingPosition) -> Unit = { _, _ -> },
)
internal val LocalReadingPositions = staticCompositionLocalOf { ReadingPositionAccess() }
internal val ReadingPositionSemantics = SemanticsPropertyKey<BookReadingPosition>("BookReadingPosition")
internal val ReadingCanScrollForwardSemantics = SemanticsPropertyKey<Boolean>("BookCanScrollForward")
internal val ReadingPositionRestoredSemantics = SemanticsPropertyKey<Boolean>("BookPositionRestored")
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
    val bookRows = remember(book) { com.gasczoology.invertebratelab.data.BookLayout.rows(book) }
    val bookmarkAccess by rememberUpdatedState(LocalBookmarks.current)
    var searchQuery by rememberSaveable(chapter.id) { mutableStateOf("") }
    val searchHits = remember(book, pages, chapter.a5Questions, searchQuery, language) {
        BookChapterSearch.search(book, pages, chapter.a5Questions, searchQuery, language)
    }
    var searchTarget by remember(chapter.id) { mutableStateOf<BookTarget?>(null) }
    val scope = rememberCoroutineScope()
    // Multiple-choice practice appears only when a validated question file exists for this chapter.
    val mcqBank = remember(context) { McqAssets.load(context) }
    val chapterMcq = remember(mcqBank, chapter.id) { mcqBank.forChapter(chapter.id) }
    var mcqOpen by rememberSaveable(chapter.id) { mutableStateOf(false) }
    val pageIndex = pages.indexOfFirst { it.id == pageId }.coerceAtLeast(0)
    val page = pages[pageIndex]
    val restorePosition = remember(chapter.id, pageId, continuous, referenceGalleryOpen) {
        positions.positions[chapter.id]?.takeIf { !referenceGalleryOpen && it.pageId == pageId }
    }
    val listState = remember(chapter.id, pageId, continuous, referenceGalleryOpen) {
        LazyListState(restorePosition?.itemIndex ?: 0, restorePosition?.itemOffset ?: 0)
    }
    var positionRestored by remember(listState) { mutableStateOf(false) }
    var checkpointEnabled by remember(listState) { mutableStateOf(true) }
    val activePage by rememberUpdatedState(pageId)
    fun savePosition() { if (positionRestored && checkpointEnabled && !continuous && !referenceGalleryOpen) positions.save(chapter.id, BookReadingPosition(pageId, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)) }
    fun selectPage(id: String) {
        narrator.stop()
        referenceGalleryOpen = false
        continuous = false
        if (id != pageId) pageHistory = pageHistory + pageId
        pageId = id
        positions.save(chapter.id, BookReadingPosition(id))
    }
    val hasGalleryToggle = NativeCurriculumManifest.typeStudies.any {
        it.lessonId == chapter.id && it.organismId in setOf("penaeus", "pila", "asterias")
    }
    /** Lazy item index of a chapter row on its page: header, optional toggle, page prefix, then the rows. */
    fun itemIndexOf(target: BookTarget): Int {
        val target_page = pages.firstOrNull { it.id == target.pageId } ?: return 0
        val at = target_page.rowIndices.indexOf(target.rowIndex)
        if (at < 0) return 0
        val prefix = (if (target.pageId == "objectives") 1 + (if (target_page.introductionIndex >= 0) 1 else 0) else 0)
        return 1 + (if (hasGalleryToggle) 1 else 0) + prefix + at
    }
    fun openTarget(target: BookTarget) {
        if (target.pageId == pageId && !continuous && !referenceGalleryOpen) {
            if (target.rowIndex >= 0) scope.launch { listState.scrollToItem(itemIndexOf(target)) }
            else scope.launch { listState.scrollToItem(0) }
        } else {
            searchTarget = target.takeIf { it.rowIndex >= 0 }
            selectPage(target.pageId)
        }
    }
    androidx.activity.compose.BackHandler {
        narrator.stop()
        when {
            mcqOpen -> mcqOpen = false
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
        // Scaffold bars and window insets can change the first measured viewport.
        // Reapply the checkpoint after layout, before an initial clamp can replace it.
        withFrameNanos { }
        withFrameNanos { }
        if (!continuous && !referenceGalleryOpen) restorePosition?.let { saved ->
            listState.scrollToItem(saved.itemIndex, saved.itemOffset)
            android.util.Log.i("NATIVE_READING", "restore " + chapter.id + " requested=" + saved +
                " applied=" + listState.firstVisibleItemIndex + "/" + listState.firstVisibleItemScrollOffset +
                " viewport=" + listState.layoutInfo.viewportSize)
        }
        searchTarget?.takeIf { it.pageId == pageId }?.let { target ->
            listState.scrollToItem(itemIndexOf(target))
            searchTarget = null
        }
        positionRestored = true
        if (!continuous && !referenceGalleryOpen) snapshotFlow { if (checkpointEnabled) Triple(listState.isScrollInProgress, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) else null }
            .filterNotNull().filter { !it.first }.distinctUntilChanged().collect { (_, item, offset) ->
                positions.save(chapter.id, BookReadingPosition(pageId, item, offset))
            }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(chapter.id, pageId, listState, lifecycle, continuous) {
        val thisPage = pageId
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    // Freeze the last reading position before teardown changes the viewport.
                    savePosition()
                    checkpointEnabled = false
                    narrator.stop()
                }
                Lifecycle.Event.ON_RESUME -> checkpointEnabled = true
                Lifecycle.Event.ON_STOP -> narrator.stop()
                else -> Unit
            }
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
            Row(Modifier.fillMaxWidth().statusBarsPadding().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (!(continuous && continuousReader != null)) TextButton(onClick = { savePosition(); onBack() },
                    modifier = Modifier.widthIn(min = 104.dp).heightIn(min = 48.dp).testTag(backTag)) {
                    Text(ut(language, "Back", "பின்செல்"))
                }
                TextButton(onClick = { selectPage("contents") }, modifier = Modifier.widthIn(min = 104.dp).heightIn(min = 48.dp).testTag("reading-contents")) {
                    Text(ut(language, "Contents", "பொருளடக்கம்"))
                }
                TextButton(onClick = { continuous = !continuous; referenceGalleryOpen = false },
                    modifier = Modifier.widthIn(min = 104.dp).heightIn(min = 48.dp).testTag(if (continuous) "reading-mode-paged" else "reading-mode-continuous")) {
                    Text(ut(language, if (continuous) "Pages" else "All pages", if (continuous) "பக்கங்கள்" else "முழுப் பாடம்"))
                }
            }
        },
        bottomBar = {
            // Scaffold reserves bottomBar height but does not automatically inset the bar itself.
            // Keep the controls above the system navigation area; the LazyColumn receives
            // the resulting measured Scaffold padding, without a second content spacer.
            if (!continuous && !referenceGalleryOpen && !mcqOpen) Column(Modifier.navigationBarsPadding().padding(horizontal = 8.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedButton(onClick = { selectPage(pages[pageIndex - 1].id) }, enabled = pageIndex > 0,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("reading-page-previous")
                            .semantics { contentDescription = ut(language, "Previous page", "முந்தைய பக்கம்") }) { Text("‹") }
                    val marked = pageId in bookmarkAccess.bookmarks[chapter.id].orEmpty()
                    if (pageId != "contents") OutlinedButton(onClick = { bookmarkAccess.toggle(chapter.id, pageId) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag(if (marked) "reading-bookmark-remove" else "reading-bookmark-add")
                            .semantics { contentDescription = ut(language, if (marked) "Remove bookmark" else "Add bookmark",
                                if (marked) "புத்தகக்குறியை நீக்கு" else "புத்தகக்குறி சேர்") }) { Text(if (marked) "★" else "☆") }
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
        if (mcqOpen && chapterMcq.isNotEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding)) {
                McqPracticeScreen(chapterMcq, mcqBank.verified, language, { mcqOpen = false }, "mcq-" + chapter.id)
            }
        } else if (continuous && continuousReader != null) {
            Box(Modifier.fillMaxSize().padding(padding)) { continuousReader() }
        } else if (!continuous && pageId == "interactive" && interactiveReader != null) {
            Box(Modifier.fillMaxSize().padding(padding)) { interactiveReader() }
        } else LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(padding).testTag(containerPrefix + chapter.id).semantics {
                this[ReadingPositionSemantics] = BookReadingPosition(pageId, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
                this[ReadingCanScrollForwardSemantics] = listState.canScrollForward
                this[ReadingPositionRestoredSemantics] = positionRestored
            },
            contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item(key = "header") {
                // Compact header: a smaller title, the full review notice only on the opening and
                // contents pages, and a page label only where the page body does not already
                // start with its own heading (otherwise the same words appear twice).
                val firstRow = page.rowIndices.firstOrNull()?.let { bookRows[it] }
                val bodyHasOwnHeading = page.id in setOf("objectives", "summary", "quick-checks") ||
                    firstRow is BookRow.SectionHeading || firstRow is BookRow.SubHeading
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(book.title.value(language), style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                    ChapterDraftNotice(book.status, language, compact = !continuous && pageIndex > 0 && pageId != "contents")
                    if (!continuous) {
                        val labelModifier = Modifier.testTag("reading-page-id").semantics {
                            contentDescription = page.id; heading()
                        }
                        if (bodyHasOwnHeading) Spacer(labelModifier.height(0.dp))
                        else Text(page.title.value(language), modifier = labelModifier,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
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
                    chapterFindItems(chapter.id, pages, bookmarkAccess.bookmarks[chapter.id].orEmpty(), searchQuery,
                        searchHits, language, { searchQuery = it }, ::openTarget)
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
                if (chapterMcq.isNotEmpty() && (continuous || pageId == "quick-checks")) item(key = "mcq-entry") {
                    Button(onClick = { mcqOpen = true }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("mcq-open")) {
                        Text(ut(language, "Practise multiple-choice questions (${chapterMcq.size})",
                            "பல்தேர்வு வினாக்களைப் பயிலுக (${chapterMcq.size})"))
                    }
                }
                if (chapter.id == "u4-nereis" && (continuous || pageId == "quick-checks")) {
                    items((0..7).toList(), key = { "u44-nereis-plate-$it" }) { UnitIVNereisStaticPlate(it, language) }
                }
            }
        }
    }
}
