package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.*

private fun m(language: AppLanguage, en: String, ta: String) = if (language == AppLanguage.TAMIL) ta else en

/**
 * One question per screen, four options, Submit, feedback with explanation, then a score summary
 * with "retry incorrect". All state lives in [McqSession]; it is saved as text so that rotation and
 * process death resume at the same question and selection.
 */
@Composable
internal fun McqPracticeScreen(
    questions: List<McqQuestion>,
    verified: Boolean,
    language: AppLanguage,
    onClose: () -> Unit,
    stateKey: String = "mcq",
) {
    var raw by rememberSaveable(stateKey) { mutableStateOf<String?>(null) }
    val session = remember(raw, questions) { McqSession.decode(raw, questions) ?: McqSession(questions) }
    fun update(f: (McqSession) -> McqSession) { raw = f(session).encode() }
    androidx.activity.compose.BackHandler { onClose() }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("mcq-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (!verified) Text(m(language, "Practice questions · check answers against the chapter explanation", "பயிற்சி வினாக்கள் · விடைகளைப் பாடவிளக்கத்துடன் ஒப்பிட்டுச் சரிபார்க்கவும்"),
            style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.testTag("mcq-draft-notice"))
        val current = session.current
        when {
            questions.isEmpty() -> {
                Text(m(language, "No multiple-choice questions are available for this chapter yet.",
                    "இந்த அத்தியாயத்திற்கு இன்னும் பல்தேர்வு வினாக்கள் இல்லை."), modifier = Modifier.testTag("mcq-empty"))
                OutlinedButton(onClick = onClose, modifier = Modifier.heightIn(min = 48.dp).testTag("mcq-close")) { Text(m(language, "Close", "மூடு")) }
            }
            session.finished -> McqResult(session, language, onRetry = { session.retryIncorrect()?.let { r -> raw = r.encode() } },
                onRestart = { raw = McqSession(questions).encode() }, onClose = onClose)
            current != null -> McqQuestionView(session, current, language, ::update, onClose)
        }
    }
}

@Composable
private fun McqQuestionView(session: McqSession, q: McqQuestion, language: AppLanguage,
                            update: ((McqSession) -> McqSession) -> Unit, onClose: () -> Unit) {
    val letters = listOf("A", "B", "C", "D")
    Text(m(language, "Question ${session.index + 1} of ${session.questions.size}", "வினா ${session.index + 1} / ${session.questions.size}"),
        style = MaterialTheme.typography.labelLarge, modifier = Modifier.testTag("mcq-progress"))
    LinearProgressIndicator(progress = { session.index.toFloat() / session.questions.size }, modifier = Modifier.fillMaxWidth())
    Text(q.stem.value(language), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold,
        modifier = Modifier.semantics { heading() }.testTag("mcq-stem"))
    q.options.forEachIndexed { i, option ->
        val chosen = session.selected == i
        val isKey = session.submitted && i == q.correctIndex
        val isWrongChoice = session.submitted && chosen && i != q.correctIndex
        val stateText = when {
            isKey -> m(language, "Correct answer", "சரியான விடை")
            isWrongChoice -> m(language, "Your answer, incorrect", "உங்கள் விடை, தவறு")
            chosen -> m(language, "Selected", "தேர்ந்தெடுக்கப்பட்டது")
            else -> m(language, "Not selected", "தேர்ந்தெடுக்கப்படவில்லை")
        }
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = when { isKey -> MaterialTheme.colorScheme.tertiaryContainer; isWrongChoice -> MaterialTheme.colorScheme.errorContainer
                chosen -> MaterialTheme.colorScheme.secondaryContainer; else -> MaterialTheme.colorScheme.surface },
            border = BorderStroke(if (chosen || isKey) 2.dp else 1.dp,
                if (chosen || isKey) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
            modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp).testTag("mcq-option-$i")
                .selectable(selected = chosen, enabled = !session.submitted, role = Role.RadioButton) { update { it.select(i) } }
                .semantics { stateDescription = stateText },
        ) {
            Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(letters[i] + ".", fontWeight = FontWeight.Bold)
                Text(option.value(language), modifier = Modifier.weight(1f))
                // The marker is text, so correctness never depends on colour alone.
                if (isKey) Text("✓", fontWeight = FontWeight.Bold)
                if (isWrongChoice) Text("✗", fontWeight = FontWeight.Bold)
            }
        }
    }
    if (!session.submitted) {
        Button(onClick = { update { it.submit() } }, enabled = session.selected != null,
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("mcq-submit")) { Text(m(language, "Submit answer", "விடையைச் சமர்ப்பி")) }
    } else {
        val right = session.currentIsCorrect
        Card(modifier = Modifier.fillMaxWidth().testTag("mcq-feedback")) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(if (right) m(language, "Correct.", "சரி.")
                    else m(language, "Not correct. The right answer is ${letters[q.correctIndex]}.", "தவறு. சரியான விடை ${letters[q.correctIndex]}."),
                    fontWeight = FontWeight.Bold, modifier = Modifier.testTag("mcq-verdict"))
                Text(q.explanation.value(language), modifier = Modifier.testTag("mcq-explanation"))
            }
        }
        val last = session.index == session.questions.lastIndex
        Button(onClick = { update { it.next() } }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("mcq-next")) {
            Text(if (last) m(language, "See results", "முடிவுகளைக் காண்") else m(language, "Next question", "அடுத்த வினா"))
        }
    }
    OutlinedButton(onClick = onClose, modifier = Modifier.heightIn(min = 48.dp).testTag("mcq-close")) { Text(m(language, "Leave practice", "பயிற்சியை விடு")) }
}

@Composable
private fun McqResult(session: McqSession, language: AppLanguage, onRetry: () -> Unit, onRestart: () -> Unit, onClose: () -> Unit) {
    val total = session.questions.size
    Text(m(language, "Practice complete", "பயிற்சி முடிந்தது"), style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.semantics { heading() })
    Text(m(language, "Score: ${session.score} of $total", "மதிப்பெண்: $total இல் ${session.score}"),
        style = MaterialTheme.typography.titleMedium, modifier = Modifier.testTag("mcq-score"))
    if (session.incorrect.isNotEmpty()) {
        Text(m(language, "Review these questions:", "இந்த வினாக்களை மீண்டும் பார்க்க:"), fontWeight = FontWeight.SemiBold)
        session.incorrect.forEach { Text("• " + it.stem.value(language), modifier = Modifier.testTag("mcq-missed-${it.id}")) }
        Button(onClick = onRetry, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("mcq-retry")) {
            Text(m(language, "Retry incorrect questions (${session.incorrect.size})", "தவறிய வினாக்களை மீண்டும் முயல் (${session.incorrect.size})"))
        }
    }
    OutlinedButton(onClick = onRestart, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("mcq-restart")) {
        Text(m(language, "Start again", "மீண்டும் தொடங்கு"))
    }
    OutlinedButton(onClick = onClose, modifier = Modifier.heightIn(min = 48.dp).testTag("mcq-close")) { Text(m(language, "Close", "மூடு")) }
}
