package com.gasczoology.invertebratelab.data

import kotlin.math.hypot

/**
 * U3.4: compares two independently acquired DRAFT coordinate files.
 *
 * Geometric proximity proves neither independent authorship nor biological
 * correctness. There is deliberately no approve()/release() API.
 */
internal enum class EndpointReviewFinding {
    WITHIN_PIXEL_TOLERANCE, REQUIRES_ANATOMY_REVIEW,
    ONLY_IN_FIRST_DRAFT, ONLY_IN_SECOND_DRAFT
}

internal data class UnitIIIEndpointComparisonRow(
    val organId: String,
    val first: UnitIIIPixelReviewMark?,
    val second: UnitIIIPixelReviewMark?,
    val distancePixels: Float?,
    val finding: EndpointReviewFinding,
)

internal data class UnitIIIEndpointComparison(
    val plateStem: String,
    val sourceSha256: String,
    val tolerancePixels: Float,
    val rows: List<UnitIIIEndpointComparisonRow>,
) {
    val status: String get() = "DRAFT_COMPARISON_NOT_ANATOMY_APPROVAL"
    val withinTolerance: Int get() =
        rows.count { it.finding == EndpointReviewFinding.WITHIN_PIXEL_TOLERANCE }
    val needsAttention: Int get() = rows.size - withinTolerance
    // Even 100% agreement NEVER opens learner-touch coordinates.
    val approvedForStudentHotspots: Boolean get() = false
}

internal object UnitIIIEndpointAgreement {
    fun compare(
        first: UnitIIIPixelReviewDraft,
        second: UnitIIIPixelReviewDraft,
        tolerancePixels: Float = 8f,
    ): UnitIIIEndpointComparison {
        require(first.plateStem == second.plateStem) {
            "Cannot compare different organism / anatomical plates"
        }
        require(first.sourceSha256 == second.sourceSha256) {
            "Cannot compare different original raster SHA-256 identities"
        }
        require(first.imageWidth == second.imageWidth &&
            first.imageHeight == second.imageHeight) {
            "Image dimensions differ"
        }
        require(tolerancePixels.isFinite() &&
            tolerancePixels >= 0.5f && tolerancePixels <= 50f) {
            "Unsafe geometric tolerance"
        }
        val a = first.marks.associateBy { it.organId }
        val b = second.marks.associateBy { it.organId }
        val ids = (a.keys + b.keys).toSortedSet()
        val rows = ids.map { id ->
            val left = a[id]
            val right = b[id]
            when {
                left == null -> UnitIIIEndpointComparisonRow(
                    id, null, right, null, EndpointReviewFinding.ONLY_IN_SECOND_DRAFT)
                right == null -> UnitIIIEndpointComparisonRow(
                    id, left, null, null, EndpointReviewFinding.ONLY_IN_FIRST_DRAFT)
                else -> {
                    val distance = hypot(
                        left.pixelX - right.pixelX, left.pixelY - right.pixelY)
                    UnitIIIEndpointComparisonRow(
                        id, left, right, distance,
                        if (distance <= tolerancePixels)
                            EndpointReviewFinding.WITHIN_PIXEL_TOLERANCE
                        else EndpointReviewFinding.REQUIRES_ANATOMY_REVIEW,
                    )
                }
            }
        }
        return UnitIIIEndpointComparison(
            first.plateStem, first.sourceSha256, tolerancePixels, rows)
    }
}
