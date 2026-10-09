package com.gasczoology.invertebratelab.data

/**
 * R1.2 deterministic display model for ONE representative contractile-vacuole
 * complex, not measured kinetics, volumes or anatomical geometry.
 *
 * Do not infer that anterior and posterior complexes work in synchrony.
 * Reference: Allen 1988 https://doi.org/10.1111/j.1550-7408.1988.tb04078.x
 * Species-specific cortical ultrastructure:
 * https://www6.pbrc.hawaii.edu/allen/ch10a/46-pca4201.html
 */
data class CvcIllustrativePhase(
    val stageId: String,
    val lumenFraction: Float,
    val collectingActive: Boolean,
    val poreOpen: Boolean,
) {
    init {
        require(stageId.isNotBlank())
        require(lumenFraction in 0f..1f)
    }
}

object ParameciumCvcMechanism {
    val phases = listOf(
        CvcIllustrativePhase("osmosis", .18f, false, false),
        CvcIllustrativePhase("collect", .34f, true, false),
        CvcIllustrativePhase("fill", .90f, true, false),
        CvcIllustrativePhase("expel", .14f, false, true),
    )

    fun phase(stageId: String): CvcIllustrativePhase =
        phases.single { it.stageId == stageId }

    init {
        val stages = ParameciumLearningEngine
            .simulation(ParameciumProcess.OSMOREGULATION).stages
        require(stages.map { it.id } == phases.map { it.stageId })
        require(phases.map { it.stageId }.distinct().size == phases.size)
        require(phases.count { it.poreOpen } == 1 && phases.last().poreOpen)
        require(phases[2].lumenFraction > phases[1].lumenFraction)
        require(phases[3].lumenFraction < phases[2].lumenFraction)
    }
}
