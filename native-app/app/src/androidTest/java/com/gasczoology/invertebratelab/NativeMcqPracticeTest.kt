package com.gasczoology.invertebratelab

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.*
import com.gasczoology.invertebratelab.ui.McqPracticeScreen
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

/** The quiz screen against fixture questions. No quiz content ships in the app; these are test data only. */
class NativeMcqPracticeTest {
    @get:Rule val rule = createComposeRule()
    private val tamil get() = InstrumentationRegistry.getArguments().getString("functionalLanguage") == "TAMIL"
    private val language get() = if (tamil) AppLanguage.TAMIL else AppLanguage.ENGLISH
    private fun t(s: String) = BilingualText("EN $s", "த $s")
    private fun q(id: String, key: Int) = McqQuestion(id, "u1-paramecium", t("stem $id"),
        listOf(t("a$id"), t("b$id"), t("c$id"), t("d$id")), key, t("because $id"))
    private val bank = listOf(q("q1", 0), q("q2", 3), q("q3", 2))

    private fun tap(tag: String) { rule.onNodeWithTag(tag).performScrollTo().performClick() }
    private fun show(questions: List<McqQuestion> = bank, verified: Boolean = false) =
        rule.setContent { MaterialTheme { McqPracticeScreen(questions, verified, language, onClose = {}) } }
    private fun answer(option: Int) { tap("mcq-option-$option"); tap("mcq-submit") }

    @Test fun fullRunScoresShowsExplanationsAndRetriesOnlyTheMissedQuestion() {
        show()
        rule.onNodeWithTag("mcq-draft-notice").assertExists()
        rule.onNodeWithTag("mcq-stem").assertTextContains(language.let { if (tamil) "த stem q1" else "EN stem q1" }, substring = true)
        rule.onNodeWithTag("mcq-submit").assertIsNotEnabled()               // nothing selected yet
        rule.onNodeWithTag("mcq-option-0").performScrollTo().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
        tap("mcq-option-0"); rule.onNodeWithTag("mcq-option-0").assertIsSelected(); rule.onNodeWithTag("mcq-submit").assertIsEnabled()
        tap("mcq-submit")
        rule.onNodeWithTag("mcq-explanation").assertExists()
        tap("mcq-option-1")                                                   // locked after submit
        rule.onNodeWithTag("mcq-option-0").assertIsSelected()
        tap("mcq-next")
        answer(0)                                                             // q2 key is option 3: wrong
        rule.onNodeWithTag("mcq-explanation").assertExists()
        tap("mcq-next"); answer(2); tap("mcq-next")
        rule.onNodeWithTag("mcq-score").assertTextContains("2", substring = true)
        rule.onNodeWithTag("mcq-missed-q2").assertExists(); rule.onNodeWithTag("mcq-missed-q1").assertDoesNotExist()
        tap("mcq-retry")
        rule.onNodeWithTag("mcq-progress").assertTextContains("1", substring = true)
        rule.onNodeWithTag("mcq-stem").assertTextContains("q2", substring = true)
        answer(3); tap("mcq-next")
        rule.onNodeWithTag("mcq-score").assertTextContains("1", substring = true)
        rule.onNodeWithTag("mcq-retry").assertDoesNotExist()                  // nothing left to retry
    }

    @Test fun positionAndSelectionSurviveRecreation() {
        val restoration = StateRestorationTester(rule)
        restoration.setContent { MaterialTheme { McqPracticeScreen(bank, true, language, onClose = {}) } }
        rule.onNodeWithTag("mcq-draft-notice").assertDoesNotExist()           // verified bank: no draft notice
        answer(0); tap("mcq-next"); tap("mcq-option-2")
        restoration.emulateSavedInstanceStateRestore()
        rule.onNodeWithTag("mcq-stem").assertTextContains("q2", substring = true)
        rule.onNodeWithTag("mcq-option-2").assertIsSelected()
        tap("mcq-submit"); tap("mcq-next"); answer(2); tap("mcq-next")
        restoration.emulateSavedInstanceStateRestore()                        // restored on the results screen too
        rule.onNodeWithTag("mcq-score").assertTextContains("2", substring = true)
    }

    @Test fun anEmptyBankShowsNoQuiz() {
        show(emptyList())
        rule.onNodeWithTag("mcq-empty").assertExists()
        rule.onNodeWithTag("mcq-submit").assertDoesNotExist()
        rule.onNodeWithTag("mcq-close").assertExists()
    }
}
