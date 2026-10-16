package com.gasczoology.invertebratelab.ui

import com.gasczoology.invertebratelab.data.UnitIIIEndpointComparison
import com.gasczoology.invertebratelab.data.UnitIIIPixelReviewMark
import org.json.JSONArray
import org.json.JSONObject

/**
 * Exportable AUDIT EVIDENCE ONLY. Neither geometrical agreement nor this
 * report can authenticate peer reviewer identity or publish a teaching hotspot.
 */
internal object UnitIIIComparisonReportJson {
    const val SCHEMA = "invertebrata.u35.endpoint-disagreement-report.v1"
    const val STATUS = "DRAFT_COMPARISON_NOT_ANATOMY_APPROVAL"

    private fun sourceMark(mark: UnitIIIPixelReviewMark?): Any =
        mark?.let {
            JSONObject().put("organ_id", it.organId)
                .put("source_pixel_x", it.pixelX.toDouble())
                .put("source_pixel_y", it.pixelY.toDouble())
        } ?: JSONObject.NULL

    fun serialize(result: UnitIIIEndpointComparison): String {
        require(result.status == STATUS && !result.approvedForStudentHotspots)
        require(result.sourceSha256.matches(Regex("[0-9a-f]{64}")))
        val rows = JSONArray()
        result.rows.forEach { item ->
            rows.put(JSONObject()
                .put("organ_id", item.organId)
                .put("finding", item.finding.name)
                .put("first_candidate", sourceMark(item.first))
                .put("second_candidate", sourceMark(item.second))
                .put("distance_source_pixels", item.distancePixels?.toDouble()
                    ?: JSONObject.NULL))
        }
        return JSONObject()
            .put("schema", SCHEMA)
            .put("status", STATUS)
            .put("plate_stem", result.plateStem)
            .put("source_png_sha256", result.sourceSha256)
            .put("comparison_tolerance_source_pixels", result.tolerancePixels.toDouble())
            .put("count_within_tolerance", result.withinTolerance)
            .put("count_requiring_attention", result.needsAttention)
            .put("independent_reviewers_verified", false)
            .put("anatomical_accuracy_approved", false)
            .put("student_image_hotspots_authorized", false)
            .put("reviewer_signatures", JSONObject.NULL)
            .put("source_image_rights_review", "PENDING")
            .put("notes", "Geometric evidence only; two files may come from one person. Human zoological and Tamil review required.")
            .put("comparison_rows", rows).toString(2)
    }
}
