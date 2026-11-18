package com.gasczoology.invertebratelab.data

/**
 * R1.1 illustrative native Canvas atlas contract.
 * Markers are pedagogical, not registered to a P. caudatum micrograph.
 * Contractile-vacuole complexes are NOT digestive food vacuoles.
 */
data class VacuolePlateLandmark(
    val id: String,
    val organId: String,
    val x: Float,
    val y: Float,
    val radiusFraction: Float,
    val label: BilingualText,
) {
    init {
        require(id.isNotBlank() && organId.isNotBlank())
        require(x in 0f..1f && y in 0f..1f)
        require(radiusFraction in .04f.. .12f)
        require(label.english.isNotBlank() && label.tamil.isNotBlank())
    }
}

object ParameciumVacuolePlate {
    val landmarks = listOf(
        VacuolePlateLandmark(
            "anterior-contractile-complex", "contractile-vacuole",
            .28f, .37f, .078f,
            BilingualText("Anterior contractile-vacuole complex",
                "முன்புறச் சுருங்கும் நுண்குமிழ் தொகுதி")
        ),
        VacuolePlateLandmark(
            "posterior-contractile-complex", "contractile-vacuole",
            .74f, .59f, .078f,
            BilingualText("Posterior contractile-vacuole complex",
                "பின்புறச் சுருங்கும் நுண்குமிழ் தொகுதி")
        ),
        VacuolePlateLandmark(
            "digestive-food-vacuole", "food-vacuole",
            .49f, .68f, .078f,
            BilingualText("Digestive food vacuole", "செரிமான உணவுக் குமிழ்")
        ),
    )

    /** Pixel-space circle matches Canvas marker placement in every viewport. */
    fun hitCanvas(x: Float, y: Float, width: Float, height: Float): VacuolePlateLandmark? {
        if (!x.isFinite() || !y.isFinite() || !width.isFinite() || !height.isFinite()
            || width <= 0f || height <= 0f || x !in 0f..width || y !in 0f..height
        ) return null
        val unit = minOf(width, height)
        return landmarks.asSequence()
            .map { feature ->
                val dx = x - feature.x * width
                val dy = y - feature.y * height
                feature to (dx * dx + dy * dy)
            }
            .filter { (feature, distance2) ->
                distance2 <= feature.radiusFraction * unit *
                    feature.radiusFraction * unit
            }
            .minByOrNull { it.second }?.first
    }

    init {
        require(landmarks.map { it.id }.distinct().size == landmarks.size)
        require(landmarks.count { it.organId == "contractile-vacuole" } == 2)
        require(landmarks.count { it.organId == "food-vacuole" } == 1)
        require(landmarks.all {
            ParameciumLearningEngine.organs.any { organ -> organ.id == it.organId }
        })
    }
}
