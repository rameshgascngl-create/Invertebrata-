package com.gasczoology.invertebratelab.data

data class NuclearBodyCell(val cx: Float,val cy: Float,val width: Float,val height: Float,val furrow: Float=0f,
    val mirrorVentral: Boolean=false)
data class NuclearBodyMark(val kind: String,val x: Float,val y: Float,val width: Float,val height: Float,
    val role: String="",val cellIndex: Int=0,val origin: PartnerOrigin?=null)
data class NuclearBodyLayout(val cells: List<NuclearBodyCell>,val marks: List<NuclearBodyMark>)
/** Shared native art/hit geometry. Counts are teaching products, not measured sizes. */
object NuclearBodyGeometry {
    fun conjugation(stage: NuclearView,phase: Float): NuclearBodyLayout {
        val t=phase.coerceIn(0f,1f)
        val cells=when(stage.cellCount) {
            8 -> List(8) { NuclearBodyCell(103f+(it%4)*132f,170f+(it/4)*195f,117f,125f) }
            4 -> List(4) { NuclearBodyCell(174f+(it%2)*268f,170f+(it/2)*195f,235f,135f) }
            else -> List(2) { NuclearBodyCell(310f,if(it==0) 178f-(if(stage.id=="separation") t*18 else 0f)
                else 362f+(if(stage.id=="separation") t*18 else 0f),520f,if(stage.paired) 160f else 142f,
                mirrorVentral=stage.paired && it==1) }
        }
        val marks=mutableListOf<NuclearBodyMark>()
        cells.forEachIndexed { index,c ->
            val sx=c.width/520f;val sy=c.height/160f
            fun mark(kind:String,x:Float,y:Float,w:Float,h:Float,role:String="",origin:PartnerOrigin?=null) {
                marks+=NuclearBodyMark(kind,c.cx+x*sx,c.cy+y*sy,w*sx,h*sy,role,index,origin)
            }
            if(stage.parentalMacState==ParentalMacState.INTACT || stage.parentalMacState==ParentalMacState.SKEIN)
                mark("macronucleus",92f,15f,108f,62f,"parental-mac")
            else if(stage.cellCount==2) for(i in 0..2)mark("macronucleus",80f+i*43,-40f,17f,11f,"old-fragment")
            repeat(stage.macronuclearAnlagenPerCell) { i ->
                val columns=if(stage.macronuclearAnlagenPerCell==4)2 else stage.macronuclearAnlagenPerCell
                mark("macronucleus",75f+(i%columns)*70,-12f+(i/columns)*52,
                    if(stage.id=="new-mac")58f else 45f,if(stage.id=="new-mac")44f else 34f,"new-mac",PartnerOrigin.BOTH)
            }
            val genomes=ConjugationGenetics.genomes(stage,index)
            genomes.forEachIndexed { i,g ->
                var x=-162f+(i%4)*48f;var y=-22f+(i/4)*42f
                if(stage.id=="selection") { x=-35f;y=if(index==0)55f else -55f }
                if(stage.id in setOf("pronuclei","exchange")) {
                    x=if(g.role==GermlineRole.STATIONARY)-128f else -52f
                    y=if(g.role==GermlineRole.STATIONARY)-30f else if(index==0)58f else -58f
                    if(stage.id=="exchange" && g.role==GermlineRole.MIGRATORY) {
                        x=if(index==0)-25f else -79f
                        // Incoming origin is explicit. At t=0 it is still in the other
                        // cell; at t=1 it reaches this cell's paroral region.
                        val ownY=c.cy+y*sy;val otherY=cells[1-index].cy-y*sy
                        y=((otherY+(ownY-otherY)*t)-c.cy)/sy
                    }
                }
                if(stage.event in setOf(NuclearEvent.POSTZYGOTIC_MITOSIS,NuclearEvent.HOMOLOG_SEPARATION,NuclearEvent.SISTER_SEPARATION)) {
                    x+=(if(i%2==0)-1 else 1)*t*8f
                }
                val r=if(stage.germNucleiPerCell>=4)13f else 17f
                mark("micronucleus",x,y,r*2,r*2,g.role.name,g.origin)
            }
            repeat(stage.degeneratingGermNucleiPerCell){i ->mark("micronucleus",-160f+i*45f,
                if(index==0)-25f else 25f,19f,19f,"degeneration")}
        }
        return NuclearBodyLayout(cells,marks)
    }
    fun fission(stage: NuclearView,phase: Float): NuclearBodyLayout {
        val t=phase.coerceIn(0f,1f)
        fun mac(x:Float,y:Float,w:Float=126f,h:Float=85f)=NuclearBodyMark("macronucleus",x,y,w,h)
        fun mic(x:Float,y:Float,r:Float=20f)=NuclearBodyMark("micronucleus",x,y,r*2,r*2)
        if(stage.id=="daughters") return NuclearBodyLayout(
            listOf(NuclearBodyCell(177f-t*14,270f,245f,190f),NuclearBodyCell(452f+t*14,270f,245f,190f)),
            listOf(mac(165f-t*14,278f,83f,65f),mic(221f-t*14,240f,16f),mac(434f+t*14,278f,83f,65f),mic(490f+t*14,240f,16f)))
        val cell=NuclearBodyCell(310f,270f,530f,205f,if(stage.id=="constriction") .55f+t*.35f else 0f)
        val marks=when(stage.id) {
            "mic-mitosis" -> listOf(mac(275f,295f),mic(280f-t*45,220f,18f),mic(375f+t*45,220f,18f))
            "mac-elongation" -> listOf(mac(310f,280f,220f+t*35,80f),mic(180f,222f),mic(445f,222f))
            "constriction" -> listOf(mac(200f,280f,104f),mac(420f,280f,104f),mic(180f,222f),mic(445f,222f))
            else -> listOf(mac(275f,278f),mic(380f,245f))
        }
        return NuclearBodyLayout(listOf(cell),marks)
    }
}
