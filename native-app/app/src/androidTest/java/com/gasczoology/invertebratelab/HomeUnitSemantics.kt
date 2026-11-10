package com.gasczoology.invertebratelab

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onAllNodesWithTag

/**
 * Verify both the unit heading and chapter count inside the actual Unit 1 card.
 *
 * Material3 OutlinedButton may merge child Text semantics into one accessible
 * sentence, especially after a language change or Android font scaling.
 * Testing two exact independent hasText() matchers on the button wrongly
 * rejects the same correct bilingual content when merged this way.
 *
 * Inspect the merged and unmerged semantic trees of the tagged Unit 1 card,
 * without asserting anything elsewhere on the page or relaxing either label.
 */
internal fun SemanticsNodeInteractionsProvider.homeUnitOneShows(tamil: Boolean): Boolean {
    val unitCard = onAllNodesWithTag("unit-1", useUnmergedTree = true)
        .fetchSemanticsNodes().firstOrNull() ?: return false
    fun visibleText(node: SemanticsNode): String =
        (node.config.getOrNull(SemanticsProperties.Text)
            ?.joinToString(" ") { it.text }.orEmpty() + " " +
            node.children.joinToString(" ") { visibleText(it) }).trim()

    val text = visibleText(unitCard)
    val unitLabel = if (tamil) "அலகு 1" else "Unit 1"
    val countLabel = if (tamil) "7 அத்தியாயங்கள்" else "7 chapters"
    return text.contains(unitLabel) && text.contains(countLabel)
}
