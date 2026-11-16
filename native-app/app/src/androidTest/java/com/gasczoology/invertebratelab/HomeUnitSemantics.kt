package com.gasczoology.invertebratelab

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText

/** Match both bilingual labels as descendants of the Unit 1 card. */
internal fun SemanticsNodeInteractionsProvider.homeUnitOneShows(tamil: Boolean): Boolean =
    onAllNodes(
        hasTestTag("unit-1") and
            hasAnyDescendant(hasText(if (tamil) "அலகு 1" else "Unit 1")) and
            hasAnyDescendant(hasText(if (tamil) "7 அத்தியாயங்கள்" else "7 chapters")),
        useUnmergedTree = true,
    ).fetchSemanticsNodes().isNotEmpty()
