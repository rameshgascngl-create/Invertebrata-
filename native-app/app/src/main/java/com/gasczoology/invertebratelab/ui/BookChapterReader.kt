package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import java.text.BreakIterator
import java.util.Locale
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.BookBlock
import com.gasczoology.invertebratelab.data.BookChapter
import com.gasczoology.invertebratelab.data.BookDefinition
import com.gasczoology.invertebratelab.data.BookFigure
import com.gasczoology.invertebratelab.data.BookFigureLibrary
import com.gasczoology.invertebratelab.data.BookKeyPoints
import com.gasczoology.invertebratelab.data.BookLayout
import com.gasczoology.invertebratelab.data.BookNote
import com.gasczoology.invertebratelab.data.BookParagraph
import com.gasczoology.invertebratelab.data.BookQuickCheck
import com.gasczoology.invertebratelab.data.BookRow
import com.gasczoology.invertebratelab.data.BookTable

private fun t(language: AppLanguage, en: String, ta: String) =
    if (language == AppLanguage.TAMIL) ta else en

private val keyAnatomyTerms = Regex(
    """\b(?:radula|odontophore|operculum|ctenidium|osphradium|mantle cavity|pulmonary sac|hepatopancreas|gastric mill|madreporite|stone canal|ring canal|radial canal|ampullae|tube feet|pyloric caeca|nerve ring|radial nerve|petasma|thelycum)\b""",
    RegexOption.IGNORE_CASE,
)

private fun emphasizeAnatomy(text: String) = buildAnnotatedString {
    var cursor = 0
    keyAnatomyTerms.findAll(text).forEach { match ->
        append(text.substring(cursor, match.range.first))
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(match.value) }
        cursor = match.range.last + 1
    }
    append(text.substring(cursor))
}

private val romanUnits = listOf("", "I", "II", "III", "IV", "V", "VI", "VII")

/**
 * Printed-book rendering of a [BookChapter]: objectives, introduction, contents,
 * numbered sections and sub-sections with definition boxes, numbered figures
 * and tables, then summary, glossary, check questions and further reading.
 * Figures are shown inline, once; the plate gallery is not repeated.
 */
internal fun LazyListScope.bookChapterItems(
    chapter: BookChapter,
    language: AppLanguage,
    onListen: (BilingualText) -> Unit,
) {
    val unit = BookLayout.unitNumber(chapter.lessonId)
    val number = BookLayout.chapterNumber(chapter.lessonId)
    val rows = BookLayout.rows(chapter)
    val chapterLabel = t(language, "Unit ${romanUnits[unit]} · Chapter $number",
        "அலகு ${romanUnits[unit]} · அத்தியாயம் $number")

    item(key = "book-objectives") {
        Card(modifier = Modifier.fillMaxWidth().testTag("book-objectives")) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(chapterLabel, style = MaterialTheme.typography.labelLarge)
                Text(t(language, "Learning objectives", "கற்றல் நோக்கங்கள்"),
                    style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold,
                    modifier = Modifier.semantics { heading() })
                Text(t(language, "After studying this chapter you should be able to:",
                    "இந்த அத்தியாயத்தைக் கற்ற பின் நீங்கள் செய்யக்கூடியவை:"))
                chapter.objectives.forEachIndexed { i, o ->
                    Text("${i + 1}. " + o.value(language))
                }
            }
        }
    }
    item(key = "book-intro") {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.testTag("book-introduction")) {
            Text(t(language, "Introduction", "முன்னுரை"),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { heading() })
            chapter.introduction.forEach {
                Text(it.value(language), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
    item(key = "book-contents") {
        Card(modifier = Modifier.fillMaxWidth().testTag("book-contents")) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(t(language, "Contents of this chapter", "இந்த அத்தியாயத்தின் உள்ளடக்கம்"),
                    style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold,
                    modifier = Modifier.semantics { heading() })
                rows.filterIsInstance<BookRow.SectionHeading>().forEach {
                    Text(it.number + "  " + it.heading.value(language))
                }
            }
        }
    }
    items(rows.size, key = { "book-row-$it" }) { index ->
        BookRowView(rows[index], language, onListen)
    }
    item(key = "book-summary") {
        Card(modifier = Modifier.fillMaxWidth().testTag("book-summary")) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(t(language, "Chapter summary", "அத்தியாயச் சுருக்கம்"),
                    style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                    modifier = Modifier.semantics { heading() })
                chapter.summary.forEach { Text("• " + it.value(language)) }
            }
        }
    }
    item(key = "book-glossary") {
        val sorted = chapter.definitions.sortedBy { it.term.english.lowercase() }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.testTag("book-glossary")) {
            Text(t(language, "Key terms", "கலைச்சொற்கள்"),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { heading() })
            sorted.forEach { d ->
                Column {
                    Text(d.term.value(language), fontWeight = FontWeight.SemiBold)
                    Text(d.meaning.value(language), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
    item(key = "book-quick-checks-heading") {
        Text(t(language, "Check your understanding", "புரிதலைச் சரிபார்க்க"),
            style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
            modifier = Modifier.semantics { heading() }.testTag("book-quick-checks"))
    }
    items(chapter.quickChecks.size, key = { "book-qc-$it" }) { index ->
        BookQuickCheckView(chapter.lessonId, index, chapter.quickChecks[index], language)
    }
    item(key = "book-review-note") {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(t(language, "Review questions", "மீளாய்வு வினாக்கள்"),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { heading() })
            Text(t(language,
                "This chapter prepares you for the validated five-mark questions that follow under Exam practice.",
                "இந்த அத்தியாயம், பின்வரும் தேர்வுப் பயிற்சியிலுள்ள சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினாக்களுக்கு உங்களைத் தயார்படுத்துகிறது."))
        }
    }
    item(key = "book-further-reading") {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.testTag("book-further-reading")) {
            Text(t(language, "Further reading", "மேலும் படிக்க"),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { heading() })
            chapter.furtherReading.forEach { Text("• " + it.value(language),
                style = MaterialTheme.typography.bodyMedium) }
        }
    }
}

@Composable
private fun BookRowView(row: BookRow, language: AppLanguage, onListen: (BilingualText) -> Unit) {
    when (row) {
        is BookRow.SectionHeading -> Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(row.number + "  " + row.heading.value(language),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { heading() }.testTag("book-section-" + row.number))
            OutlinedButton(onClick = { onListen(row.listenText) },
                modifier = Modifier.heightIn(min = 48.dp).testTag("book-listen-" + row.number)) {
                Text(t(language, "Listen to this section", "இந்தப் பகுதியைக் கேள்"))
            }
        }
        is BookRow.SubHeading -> Text(row.number + "  " + row.heading.value(language),
            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.semantics { heading() })
        is BookRow.Block -> BookBlockView(row.block, row.label, language)
    }
}

/**
 * Split only long explanatory prose into sentence-boundary reading blocks.
 * No scientific text, bilingual phrasing or assessment content is discarded.
 * Short paragraphs, definitions, tables and source text are left untouched.
 */
internal fun readingParagraphs(raw: String, language: AppLanguage): List<String> {
    val text = raw.trim()
    if (text.length <= 360) return listOf(text)
    val boundaries = BreakIterator.getSentenceInstance(
        if (language == AppLanguage.TAMIL) Locale.forLanguageTag("ta-IN") else Locale.UK
    )
    boundaries.setText(text)
    val sentences = mutableListOf<String>()
    var start = boundaries.first()
    var end = boundaries.next()
    while (end != BreakIterator.DONE) {
        val sentence = text.substring(start, end).trim()
        if (sentence.isNotEmpty()) sentences += sentence
        start = end
        end = boundaries.next()
    }
    if (sentences.size <= 1) return listOf(text)
    val result = mutableListOf<String>()
    var paragraph = ""
    for (sentence in sentences) {
        if (paragraph.isNotEmpty() && paragraph.length + sentence.length + 1 > 360) {
            result += paragraph
            paragraph = ""
        }
        paragraph = if (paragraph.isEmpty()) sentence else "$paragraph $sentence"
    }
    if (paragraph.isNotEmpty()) result += paragraph
    return result
}

@Composable
private fun BookBlockView(block: BookBlock, label: String?, language: AppLanguage) {
    when (block) {
        is BookParagraph -> Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            readingParagraphs(block.text.value(language), language).forEach { segment ->
                Text(emphasizeAnatomy(segment), style = MaterialTheme.typography.bodyLarge)
            }
        }
        is BookDefinition -> Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            modifier = Modifier.fillMaxWidth()
                .testTag("book-def-" + block.term.english.lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-'))) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(t(language, "DEFINITION", "வரையறை"), style = MaterialTheme.typography.labelSmall)
                Text(block.term.value(language), fontWeight = FontWeight.Bold)
                Text(block.meaning.value(language))
            }
        }
        is BookKeyPoints -> Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(block.title.value(language), fontWeight = FontWeight.Bold)
                block.points.forEachIndexed { i, pt -> Text("${i + 1}. " + pt.value(language)) }
            }
        }
        is BookNote -> Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(block.title.value(language), fontWeight = FontWeight.Bold)
                Text(block.text.value(language))
            }
        }
        is BookFigure -> BookFigureView(block, label ?: "", language)
        is BookTable -> BookTableView(block, label ?: "", language)
    }
}

/**
 * Keep figure titles short and place numbered anatomical keys on separate lines.
 * The source caption remains unchanged; only the 200%-font presentation is split.
 */
internal data class FigureCaptionParts(val title: String, val numberedKey: List<Pair<String, String>>)

internal fun parseFigureCaption(raw: String): FigureCaptionParts {
    val markers = Regex("""\((\d{1,2})\)\s*""").findAll(raw).toList()
    if (markers.isEmpty() || markers.first().groupValues[1] != "1") {
        return FigureCaptionParts(raw, emptyList())
    }
    val title = raw.substring(0, markers.first().range.first).trim().trimEnd(':', '：')
    val key = markers.mapIndexed { index, marker ->
        val end = markers.getOrNull(index + 1)?.range?.first ?: raw.length
        val explanation = raw.substring(marker.range.last + 1, end)
            .trim().trimEnd(',', '，', ';', '；').trim()
        marker.groupValues[1] to explanation
    }.filter { it.second.isNotBlank() }
    return if (title.isBlank() || key.isEmpty()) FigureCaptionParts(raw, emptyList())
        else FigureCaptionParts(title, key)
}

@Composable
private fun BookFigureView(figure: BookFigure, label: String, language: AppLanguage) {
    val bitmap = rememberCanonicalPlateBitmap(
        BookFigureLibrary.stemFor(figure.slot) ?: ("type_studies/" + figure.slot))
    var expanded by rememberSaveable("book-figure-" + figure.slot) { mutableStateOf(false) }
    val typeStudyCredit = rememberPlateCredit(figure.slot)
    val tag = figure.slot.replace('/', '-')
    val parts = parseFigureCaption(figure.caption.value(language))
    val caption = label + "  " + parts.title
    Column(verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth().testTag("book-figure-$tag")) {
        if (bitmap == null) {
            Text(t(language, "This figure is not available in this build.",
                "இந்தப் படம் இந்த வெளியீட்டில் இல்லை."),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("r2-plate-missing-$tag"))
        } else {
            Image(bitmap = bitmap, contentDescription = caption,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(bitmap.width.toFloat() / bitmap.height.toFloat())
                    .clickable(onClickLabel = t(language, "Open full-size image",
                        "முழுத்திரையில் பெரிதாக்கு")) { expanded = true }
                    .testTag("r2-plate-bitmap-$tag"))
            if (expanded) {
                RequiredTypeStudyFullImage(image = bitmap, title = caption,
                    language = language, onDismiss = { expanded = false })
            }
        }
        Text(caption, style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.testTag("book-figure-caption-$tag"))
        // At 200% Tamil font these must wrap as independent keyed entries,
        // rather than a single unbroken paragraph under the raster.
        parts.numberedKey.forEach { (number, explanation) ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("$number.", style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold)
                Text(explanation, style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f))
            }
        }
        (BookFigureLibrary.creditFor(figure.slot) ?: typeStudyCredit)?.let {
            Text(it.text(language), style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("plate-credit-$tag"))
        }
    }
}

@Composable
private fun BookTableView(table: BookTable, label: String, language: AppLanguage) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth().testTag("book-table-" + label.replace(' ', '-'))) {
        Text(label + "  " + table.caption.value(language),
            style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        if (table.header.size <= 3) {
            Row(Modifier.fillMaxWidth()) {
                table.header.forEach {
                    Text(it.value(language), fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f).padding(end = 8.dp))
                }
            }
            table.rows.forEach { r ->
                Row(Modifier.fillMaxWidth()) {
                    r.forEach { cell ->
                        Text(cell.value(language), modifier = Modifier.weight(1f).padding(end = 8.dp))
                    }
                }
            }
        } else {
            // Wide tables are stacked per row so that Tamil text stays readable on a phone.
            table.rows.forEach { r ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(r[0].value(language), fontWeight = FontWeight.Bold)
                        for (i in 1 until r.size) {
                            Text(table.header[i].value(language) + ": " + r[i].value(language))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookQuickCheckView(lessonId: String, index: Int, check: BookQuickCheck, language: AppLanguage) {
    var shown by rememberSaveable(lessonId, index) { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().testTag("book-qc-$index")) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${index + 1}. " + check.question.value(language), fontWeight = FontWeight.SemiBold)
            OutlinedButton(onClick = { shown = !shown },
                modifier = Modifier.heightIn(min = 48.dp).testTag("book-qc-toggle-$index")) {
                Text(if (shown) t(language, "Hide answer", "விடையை மறை")
                    else t(language, "Show answer", "விடையைக் காட்டு"))
            }
            if (shown) Text(check.answer.value(language))
        }
    }
}
