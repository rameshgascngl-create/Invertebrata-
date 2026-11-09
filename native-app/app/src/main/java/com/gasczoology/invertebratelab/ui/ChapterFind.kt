package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.*

internal data class BookmarkAccess(
    val bookmarks: Map<String, Set<String>> = emptyMap(),
    val toggle: (String, String) -> Unit = { _, _ -> },
)
internal val LocalBookmarks = staticCompositionLocalOf { BookmarkAccess() }

/** Where a search hit or bookmark should open: a semantic page and, optionally, a row on it. */
internal data class BookTarget(val pageId: String, val rowIndex: Int = -1)

private fun f(language: AppLanguage, en: String, ta: String) = if (language == AppLanguage.TAMIL) ta else en

private fun kindLabel(kind: SearchKind, language: AppLanguage) = when (kind) {
    SearchKind.HEADING -> f(language, "Heading", "தலைப்பு")
    SearchKind.DEFINITION -> f(language, "Definition", "வரையறை")
    SearchKind.PARAGRAPH -> f(language, "Text", "உரை")
    SearchKind.KEY_CONCEPT -> f(language, "Key concept", "முக்கியக் கருத்து")
    SearchKind.NOTE -> f(language, "Note", "குறிப்பு")
    SearchKind.TABLE -> f(language, "Table", "அட்டவணை")
    SearchKind.FIGURE -> f(language, "Figure", "படம்")
    SearchKind.OBJECTIVE -> f(language, "Objective", "நோக்கம்")
    SearchKind.INTRODUCTION -> f(language, "Introduction", "முன்னுரை")
    SearchKind.SUMMARY -> f(language, "Summary", "சுருக்கம்")
    SearchKind.QUICK_CHECK -> f(language, "Check question", "சோதனை வினா")
    SearchKind.QUESTION -> f(language, "Exam question", "தேர்வு வினா")
    SearchKind.FURTHER_READING -> f(language, "Further reading", "மேலும் படிக்க")
}

/** Search box, results and the bookmark list, shown at the top of the chapter contents page. */
internal fun LazyListScope.chapterFindItems(
    chapterId: String,
    pages: List<BookReadingPage>,
    bookmarks: Set<String>,
    query: String,
    hits: List<BookSearchHit>,
    language: AppLanguage,
    onQuery: (String) -> Unit,
    onOpen: (BookTarget) -> Unit,
) {
    item(key = "find-search") {
        OutlinedTextField(
            value = query, onValueChange = onQuery, singleLine = true,
            label = { Text(f(language, "Search this chapter", "இந்த அத்தியாயத்தில் தேடு")) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            modifier = Modifier.fillMaxWidth().testTag("reading-search-field"),
        )
    }
    if (query.trim().length >= BookChapterSearch.MIN_QUERY) {
        if (hits.isEmpty()) item(key = "find-none") {
            Text(f(language, "No matches in this chapter.", "இந்த அத்தியாயத்தில் பொருத்தம் இல்லை."),
                modifier = Modifier.testTag("reading-search-empty"))
        } else {
            item(key = "find-count") {
                Text(f(language, "${hits.size} match(es)", "${hits.size} பொருத்தங்கள்"),
                    style = MaterialTheme.typography.labelLarge, modifier = Modifier.testTag("reading-search-count"))
            }
            items(hits.size, key = { "find-hit-$it" }) { i ->
                val hit = hits[i]
                val page = pages.firstOrNull { it.id == hit.pageId }
                OutlinedButton(onClick = { onOpen(BookTarget(hit.pageId, hit.rowIndex)) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("reading-search-hit-$i")) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(kindLabel(hit.kind, language) + " · " + (page?.title?.value(language) ?: ""),
                            style = MaterialTheme.typography.labelMedium)
                        Text(hit.snippet, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
    // Stale ids (a page that no longer exists) are ignored rather than opened.
    val saved = pages.filter { it.id in bookmarks && it.id != "contents" }
    if (saved.isNotEmpty()) {
        item(key = "find-bookmarks-heading") {
            Text(f(language, "Bookmarks", "புத்தகக்குறிகள்"), style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() }.testTag("reading-bookmarks-heading"))
        }
        items(saved.size, key = { "find-bookmark-${saved[it].id}" }) { i ->
            val page = saved[i]
            OutlinedButton(onClick = { onOpen(BookTarget(page.id)) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("reading-bookmark-" + page.id)) {
                Text("★ ${pages.indexOf(page) + 1}. " + page.title.value(language))
            }
        }
    }
}
