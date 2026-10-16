package com.gasczoology.invertebratelab.data

/**
 * U3.3 coordinates are REVIEW CANDIDATES ONLY, not academically accepted
 * source-image hotspots or annotations in a student version.
 */
internal data class UnitIIIPixelReviewMark(
    val organId: String,
    val pixelX: Float,
    val pixelY: Float,
    val finding: UnitIIIReviewFinding = UnitIIIReviewFinding.NOT_REVIEWED,
    val note: String = "",
)

internal data class UnitIIIPixelReviewDraft(
    val plateStem: String,
    val sourceSha256: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val marks: List<UnitIIIPixelReviewMark>,
) {
    val approvalStatus: String get() = "DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF"

    init {
        val specs = UnitIIIPlateCatalog.plates
        require(specs.any { it.assetStem == plateStem }) { "Unknown curriculum plate" }
        require(sourceSha256.matches(Regex("[a-f0-9]{64}"))) {
            "Missing verified source-byte SHA-256"
        }
        require(imageWidth in 1..5000 && imageHeight in 1..5000)
        val validIds = UnitIIIPlateHotspots.forStem(plateStem).map { it.id }.toSet()
        require(marks.isNotEmpty()) { "No candidate organs selected" }
        require(marks.map { it.organId }.distinct().size == marks.size) {
            "An organ must have exactly one current review candidate"
        }
        for (p in marks) {
            require(p.note.length <= UnitIIIOriginalPixelEvidence.MAX_NOTE_LENGTH &&
                !p.note.contains('\u0000')) { "Invalid provisional review note" }
            require(p.organId in validIds) { "Wrong organism/plate organ ID" }
            require(p.pixelX.isFinite() && p.pixelY.isFinite() &&
                p.pixelX >= 0f && p.pixelY >= 0f &&
                p.pixelX < imageWidth && p.pixelY < imageHeight) {
                "Out-of-raster organ endpoint"
            }
        }
    }

    /** No approval API: the exported evidence cannot enable learner hotspots. */
    fun normalizedPoints(): List<Pair<Float, Float>> =
        marks.map { it.pixelX / imageWidth to it.pixelY / imageHeight }
}
