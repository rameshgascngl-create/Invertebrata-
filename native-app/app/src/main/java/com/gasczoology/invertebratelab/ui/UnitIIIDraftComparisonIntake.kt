package com.gasczoology.invertebratelab.ui

import com.gasczoology.invertebratelab.data.UnitIIIPixelReviewDraft
import com.gasczoology.invertebratelab.data.UnitIIIPixelReviewMark
import org.json.JSONObject

/**
 * Parses an UNTRUSTED second U3.3 draft from Android Files. No source file,
 * user text or external manifest is promoted to an approved anatomical map.
 */
internal object UnitIIIDraftComparisonIntake {
    const val MAX_JSON_BYTES = 65536
    const val SCHEMA = "invertebrata.u33.source-pixel-review.v1"

    fun parse(text: String): UnitIIIPixelReviewDraft {
        require(text.toByteArray(Charsets.UTF_8).size <= MAX_JSON_BYTES) {
            "Second draft exceeds bounded source-evidence size"
        }
        val json = JSONObject(text)
        require(json.getString("schema") == SCHEMA) { "Unknown draft format" }
        require(json.getString("approval_status") ==
            "DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF") {
            "Only unapproved coordinate drafts may be compared"
        }
        val points = json.getJSONArray("marks")
        require(points.length() in 1..60) { "Invalid organ marker count" }
        val marks = (0 until points.length()).map { index ->
            val item = points.getJSONObject(index)
            val x = item.getDouble("pixel_x")
            val y = item.getDouble("pixel_y")
            require(x.isFinite() && y.isFinite() &&
                x in 0.0..5000.0 && y in 0.0..5000.0) {
                "Unsafe source pixel values"
            }
            UnitIIIPixelReviewMark(
                item.getString("organ_id"), x.toFloat(), y.toFloat())
        }
        return UnitIIIPixelReviewDraft(
            plateStem = json.getString("plate_asset_stem"),
            sourceSha256 = json.getString("actual_apk_png_sha256"),
            imageWidth = json.getInt("source_width_px"),
            imageHeight = json.getInt("source_height_px"),
            marks = marks,
        )
    }
}
