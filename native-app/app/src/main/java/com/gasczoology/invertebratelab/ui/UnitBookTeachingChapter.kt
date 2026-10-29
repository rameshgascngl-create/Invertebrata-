package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*

private fun ut(lang: AppLanguage, en: String, ta: String) = if (lang == AppLanguage.TAMIL) ta else en

/**
 * Printed-book reader for chapters of Units I, IV and V that have a [BookChapter].
 * The book comes first; the validated A5 questions follow unchanged. Plate figures
 * render inline once, so no separate plate gallery is shown.
 */
@Composable
internal fun UnitBookTeachingChapter(
    chapter: Chapter, book: BookChapter, language: AppLanguage, onBack: () -> Unit,
    revealedQuestionId: String, onToggle: (A5Question) -> Unit,
) {
    val context = LocalContext.current
    val narrator = remember(context, chapter.id) { OfflineNarrator(context) }
    DisposableEffect(narrator) { onDispose { narrator.close() } }
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
            .testTag("book-reader-" + chapter.id),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp,
            end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item(key = "header") {
            OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp).testTag("book-back")) {
                Text(ut(language, "Back", "பின்செல்"))
            }
        }
        bookChapterItems(book, language) { narrator.speak(it, language) }
        item(key = "practice-heading") {
            Text(ut(language, "EXAM PRACTICE · AFTER THE LESSON", "தேர்வுப் பயிற்சி · பாடத்திற்குப் பின்"),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("book-practice-heading").semantics { heading() })
            Text(ut(language, "The validated A5 question/answer corpus is preserved verbatim.",
                "சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினா–விடைத் தொகுப்பு மாற்றமின்றிப் பேணப்பட்டுள்ளது."))
        }
        items(chapter.a5Questions, key = { it.id }) { question ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(question.question.value(language), style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.testTag("a5-question-" + question.id))
                    OutlinedButton(onClick = { onToggle(question) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("a5-toggle-" + question.id)) {
                        Text(ut(language, if (revealedQuestionId == question.id) "Hide answer" else "Reveal five points",
                            if (revealedQuestionId == question.id) "விடையை மறை" else "ஐந்து குறிப்புகளைக் காட்டு"))
                    }
                    if (revealedQuestionId == question.id)
                        question.answerPoints.forEachIndexed { at, point ->
                            Text("${at + 1}. " + point.value(language),
                                modifier = Modifier.testTag("a5-point-" + question.id + "-" + (at + 1)))
                        }
                }
            }
        }
        if (chapter.id == "u4-nereis") {
            items((0..7).toList(), key = { "u44-nereis-plate-$it" }) { plateIndex ->
                UnitIVNereisStaticPlate(plateIndex, language)
            }
        }
    }
}
