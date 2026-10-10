package com.gasczoology.invertebratelab.data

enum class PartnerOrigin { A, B, BOTH }
enum class GermlineRole { PRODUCT, STATIONARY, MIGRATORY, SYNKARYON }
data class ConjugationGenome(val origin: PartnerOrigin, val ploidy: Int,
    val replicated: Boolean=false, val role: GermlineRole=GermlineRole.PRODUCT,
    val representativeHomolog: Int?=null)

/** Partner origin is symbolic, not a genotype, allele map or measured karyotype. */
object ConjugationGenetics {
    fun genomes(stage: NuclearView, cell: Int): List<ConjugationGenome> {
        val own=if(cell%2==0) PartnerOrigin.A else PartnerOrigin.B
        val other=if(own==PartnerOrigin.A) PartnerOrigin.B else PartnerOrigin.A
        return when(stage.id) {
            "pronuclei" -> listOf(ConjugationGenome(own,1,role=GermlineRole.STATIONARY),
                ConjugationGenome(own,1,role=GermlineRole.MIGRATORY))
            "exchange" -> listOf(ConjugationGenome(own,1,role=GermlineRole.STATIONARY),
                ConjugationGenome(other,1,role=GermlineRole.MIGRATORY))
            "fusion" -> listOf(ConjugationGenome(PartnerOrigin.BOTH,2,role=GermlineRole.SYNKARYON))
            else -> List(stage.germNucleiPerCell) { i ->
                ConjugationGenome(if(stage.id in setOf("pairing","meiotic-replication","meiosis-i","meiosis-ii","selection")) own else PartnerOrigin.BOTH,
                    stage.germPloidy, replicated=stage.id in setOf("meiotic-replication","meiosis-i"),
                    representativeHomolog=when(stage.id){"meiosis-i" -> i;"meiosis-ii" -> i/2;else -> null})
            }
        }
    }
}
