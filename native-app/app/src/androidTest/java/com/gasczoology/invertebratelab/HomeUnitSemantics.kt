package com.gasczoology.invertebratelab

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText

/**
 * Since commit dfecd360 the Home unit button renders the unit title ("Unit 1" /
 * "அலகு 1") and the chapter count ("7 chapters" / "7 அத்தியாயங்கள்") as two Text
 * nodes merged into one button, instead of one combined "Unit 1 · 7 chapters" string.
 *
 * This keeps the original strength of the check: the unit-1 button must be present
 * AND show both the title and the chapter count in the requested language.
 */
internal fun SemanticsNodeInteractionsProvider.homeUnitOneShows(tamil: Boolean): Boolean =
    onAllNodes(
        hasTestTag("unit-1") and
            hasText(if (tamil) "அலகு 1" else "Unit 1") and
            hasText(if (tamil) "7 அத்தியாயங்கள்" else "7 chapters"),
    ).fetchSemanticsNodes().isNotEmpty()
