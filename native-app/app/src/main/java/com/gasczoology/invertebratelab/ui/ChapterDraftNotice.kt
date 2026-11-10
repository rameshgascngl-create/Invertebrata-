package com.gasczoology.invertebratelab.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gasczoology.invertebratelab.data.AcademicWorkStatus
import com.gasczoology.invertebratelab.data.AppLanguage

/** Reader compatibility entry point. Editorial workflow status belongs in CI and
 * manuscript records, not as boilerplate on a student's textbook page.
 */
@Composable
internal fun ChapterDraftNotice(
    @Suppress("UNUSED_PARAMETER") status: AcademicWorkStatus,
    @Suppress("UNUSED_PARAMETER") language: AppLanguage,
    @Suppress("UNUSED_PARAMETER") modifier: Modifier = Modifier,
    @Suppress("UNUSED_PARAMETER") compact: Boolean = false,
) {
    // No banner and no blank space in the paginated reader.
}
