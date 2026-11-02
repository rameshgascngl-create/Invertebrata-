package com.gasczoology.invertebratelab.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.gasczoology.invertebratelab.data.AcademicWorkStatus
import com.gasczoology.invertebratelab.data.AppLanguage

/**
 * One calm, bilingual line shown in the header of every chapter reader whose content is not
 * yet ACCEPTED, so students are never left to assume unreviewed material is certified.
 *
 * The wording is the app's own existing review warning (ParameciumTeachingLab r1-review-warning),
 * reused verbatim so no new Tamil is introduced. It lives INSIDE each reader's existing header
 * item, so no LazyColumn item index shifts (several tests rely on those positions). It disappears
 * automatically when a chapter's status becomes ACCEPTED.
 */
@Composable
internal fun ChapterDraftNotice(
    status: AcademicWorkStatus,
    language: AppLanguage,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
) {
    if (status == AcademicWorkStatus.ACCEPTED) return
    // The full sentence opens each chapter; later pages keep a one-line reminder so the notice is
    // still visible without taking two lines of every page.
    Text(
        text = if (compact) {
            if (language == AppLanguage.TAMIL) "வரைவு · அறிவியல் மதிப்பாய்வு நிலுவை" else "Draft · academic review pending"
        } else if (language == AppLanguage.TAMIL)
            "அறிவியல் மதிப்பாய்வு நிலுவை: வரைபடங்களும் தமிழ் சொற்களும் வரைவு நிலையில் உள்ளன."
        else "Academic review pending: illustrations and Tamil vocabulary remain drafts.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.testTag("chapter-draft-notice"),
    )
}
