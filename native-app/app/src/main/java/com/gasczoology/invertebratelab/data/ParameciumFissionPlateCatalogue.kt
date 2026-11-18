package com.gasczoology.invertebratelab.data

/**
 * Canonical UG teaching plates committed to the repository root.
 *
 * These are intentionally NOT treated as Android drawable resource IDs:
 * packaging the source PNGs into the APK and connecting the Compose viewer
 * are separate integration steps. No fallback to pencil/Canvas sketches.
 *
 * The current binary-fission simulator has four teaching stages. F02 and F03
 * both illustrate micronuclear division, so both belong to "nuclei".
 */
object ParameciumFissionPlateCatalogue {
    data class Plate(
        val id: String,
        val sourceFile: String,
        val stageId: String,
        val englishCaption: String,
        val tamilCaption: String
    )

    val plates = listOf(
        Plate("F01", "F01_vegetative_PROVISIONAL.png", "mother",
            "Vegetative parent cell before division",
            "இருபிளவுக்கு முன்புள்ள தாய்செல்"),
        Plate("F02", "F02_micronuclear_mitosis_PROVISIONAL.png", "nuclei",
            "Micronuclear mitosis",
            "சிற்றுட்கருவின் மைட்டாசிஸ் பிரிவு"),
        Plate("F03", "F03_chromosome_segregation_PROVISIONAL.png", "nuclei",
            "Micronuclear chromosome segregation",
            "சிற்றுட்கருவில் நிறப்புரிகள் பிரிதல்")
    )

    fun forStage(stageId: String): List<Plate> =
        plates.filter { it.stageId == stageId }

    init {
        require(plates.map { it.id }.distinct().size == plates.size)
        require(plates.map { it.sourceFile }.distinct().size == plates.size)
        require(plates.all { it.stageId in setOf("mother", "nuclei", "furrow", "daughters") })
    }
}
