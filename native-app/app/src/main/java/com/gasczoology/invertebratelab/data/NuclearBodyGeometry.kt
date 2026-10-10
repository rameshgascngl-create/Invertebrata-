package com.gasczoology.invertebratelab.data

data class NuclearBodyCell(val cx: Float,val cy: Float,val width: Float,val height: Float,val furrow: Float=0f)
data class NuclearBodyMark(val kind: String,val x: Float,val y: Float,val width: Float,val height: Float)
data class NuclearBodyLayout(val cells: List<NuclearBodyCell>,val marks: List<NuclearBodyMark>)
/** Shared native art/hit geometry. Counts are teaching products, not measured sizes. */
object NuclearBodyGeometry {
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
