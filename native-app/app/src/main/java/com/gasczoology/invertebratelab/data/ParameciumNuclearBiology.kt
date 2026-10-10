package com.gasczoology.invertebratelab.data

enum class NuclearEvent { NONE, REPLICATION, MITOSIS, CYTOKINESIS, HOMOLOG_SEPARATION,
    SISTER_SEPARATION, PRONUCLEAR_MITOSIS, RECIPROCAL_EXCHANGE, FERTILIZATION,
    POSTZYGOTIC_MITOSIS, DIFFERENTIATION }
enum class ParentalMacState { INTACT, SKEIN, FRAGMENTS, RESIDUAL_FRAGMENTS }

/** Explicit P. caudatum teaching model; geometry and Tamil await human review. */
data class NuclearReading(val id: String, val heading: BilingualText, val paragraphs: List<BilingualText>)
data class NuclearView(
    val id: String,
    val heading: BilingualText,
    val explanation: BilingualText,
    val cellCount: Int = 1,
    val germNucleiPerCell: Int = 1,
    val germPloidy: Int = 2,
    val drawing: String = id,
    val event: NuclearEvent = NuclearEvent.NONE,
    val macronuclearAnlagenPerCell: Int = 0,
    val degeneratingGermNucleiPerCell: Int = 0,
    val paired: Boolean = false,
    val parentalMacState: ParentalMacState = ParentalMacState.INTACT,
    val residualGermlinePossible: Boolean = false,
)
data class NuclearChapter(val id: String, val number: Int, val heading: BilingualText,
    val readings: List<NuclearReading>, val views: List<NuclearView>, val sourceIds: List<String>)

data class NuclearLearningProgress(
    val chapterId: String = "dimorphism",
    val dimorphismView: String = "whole-cell",
    val fissionView: String = "preparation",
    val conjugationView: String = "pairing",
    val selectedNucleus: String = "macronucleus",
    val readingId: String = "1.1",
    val phasePermille: Int = 0,
    val dimorphismReading: String = "1.1",
    val fissionReading: String = "2.1",
    val conjugationReading: String = "3.1",
    val reducedMotion: Boolean = false,
) {
    fun viewId(chapter: String = chapterId): String = when (chapter) {
        "fission" -> fissionView
        "conjugation" -> conjugationView
        else -> dimorphismView
    }
    fun selectView(id: String): NuclearLearningProgress = when (chapterId) {
        "fission" -> copy(fissionView = id, phasePermille = 0)
        "conjugation" -> copy(conjugationView = id, phasePermille = 0)
        else -> copy(dimorphismView = id, phasePermille = 0)
    }
    fun readingFor(chapter: String) = when(chapter) {
        "fission" -> fissionReading
        "conjugation" -> conjugationReading
        else -> dimorphismReading
    }
    fun selectReading(id: String) = when(chapterId) {
        "fission" -> copy(readingId=id,fissionReading=id)
        "conjugation" -> copy(readingId=id,conjugationReading=id)
        else -> copy(readingId=id,dimorphismReading=id)
    }
    fun selectChapter(id: String) = selectReading(readingId).copy(chapterId=id,readingId=readingFor(id),phasePermille=0).normalized()
    fun normalized(): NuclearLearningProgress {
        val chapters = ParameciumNuclearBiology.chapters
        val chapter = chapters.firstOrNull { it.id == chapterId } ?: chapters.first()
        fun valid(id: String, key: String): String = chapters.firstOrNull { it.id == key }?.let {
            id.takeIf { value -> it.views.any { view -> view.id == value } } ?: it.views.first().id
        } ?: id
        fun validReading(id: String,key: String) = chapters.firstOrNull { it.id==key }?.let { c ->
            id.takeIf { v -> c.readings.any{it.id==v} } ?: c.readings.first().id
        } ?: id
        return copy(chapterId = chapter.id,
            dimorphismReading=validReading(if(chapter.id=="dimorphism")readingId else dimorphismReading,"dimorphism"),
            fissionReading=validReading(if(chapter.id=="fission")readingId else fissionReading,"fission"),
            conjugationReading=validReading(if(chapter.id=="conjugation")readingId else conjugationReading,"conjugation"),
            dimorphismView = valid(dimorphismView, "dimorphism"),
            fissionView = valid(fissionView, "fission"), conjugationView = valid(conjugationView, "conjugation"),
            selectedNucleus = selectedNucleus.takeIf { it in setOf("macronucleus", "micronucleus") } ?: "macronucleus",
            readingId = readingId.takeIf { id -> chapter.readings.any { it.id == id } } ?: chapter.readings.first().id,
            phasePermille = phasePermille.coerceIn(0, 1000))
    }
}

object ParameciumNuclearBiology {
    const val status = "DRAFT_UNVERIFIED"
    const val species = "Paramecium caudatum"
    private fun b(en: String, ta: String) = BilingualText(en, ta)
    val sources = linkedMapOf(
        "mikami1996" to "Mikami (1996), nuclear divisions in P. caudatum conjugation; primary microsurgery study, abnormal extra divisions are not the normal model. DOI 10.1111/j.1550-7408.1996.tb02471.x · https://onlinelibrary.wiley.com/doi/10.1111/j.1550-7408.1996.tb02471.x",
        "allen48" to "Allen, P. caudatum TEM / Gortz (ed.), Paramecium (1988), p.34: enveloped MIC spindle. https://www6.pbrc.hawaii.edu/allen/ch10a/48-pca740125-46.html",
        "aihara50" to "Aihara / Allen microscopy atlas, J. Protozool. 35:400–405 (1988): vegetative nuclei and transverse constriction. https://www6.pbrc.hawaii.edu/allen/ch10a/50-pca.html",
        "ishida1999" to "Ishida, Nakajima, Kurokawa & Mikami (1999), Zool. Sci. 16:915–926; primary anti-tubulin/DAPI microscopy. Scanned primary paper: https://dl.ndl.go.jp/pid/10862438",
        "alberts" to "Alberts et al., Molecular Biology of the Cell: homologs, sister chromatids and meiosis. Established cell-biology source, not a Paramecium karyotype. https://www.ncbi.nlm.nih.gov/books/NBK26840/",
        "taka2006" to "Taka et al. (2006), P. caudatum germinal-nucleus selection; primary transplantation/fluorescence study. DOI 10.1111/j.1550-7408.2006.00091.x · https://pubmed.ncbi.nlm.nih.gov/16677339/",
        "nakajima2002" to "Nakajima, Ishida & Mikami (2002), P. caudatum postmeiotic nuclear movement; primary antibody/microinjection experiments. DOI 10.1111/j.1550-7408.2002.tb00344.x · https://pubmed.ncbi.nlm.nih.gov/11908901/",
        "yang1999" to "Yang & Takahashi (1999), P. caudatum postzygotic nuclear determination; primary heat-shock experiments. DOI 10.1111/j.1550-7408.1999.tb04583.x · https://pubmed.ncbi.nlm.nih.gov/10188260/",
        "kimura2004" to "Kimura, Mikami & Endoh (2004), P. caudatum parental-MAC DNA degradation; primary observations. DOI 10.1002/gene.20060 · https://pubmed.ncbi.nlm.nih.gov/15354289/",
        "duret2008" to "Duret et al. (2008), P. tetraurelia nuclear genomes; primary sequencing, not a P. caudatum copy-number measurement. DOI 10.1101/gr.074534.107 · https://genome.cshlp.org/content/18/4/585",
        "tucker1980" to "Tucker et al. (1980), P. tetraurelia macronuclear division/microtubules; primary microscopy, species limitation applies. DOI 10.1242/jcs.44.1.135 · https://pubmed.ncbi.nlm.nih.gov/7440651/",
    )
    val macronucleusExplanation = b(
        "The macronucleus (MAC) is the larger somatic nucleus. It supports vegetative gene expression, growth and the cell's everyday physiological work. Its genome contains many copies of expressed sequences and is developmentally reorganized relative to the germline genome. A large nucleus does not mean a male nucleus, a brain or a fixed chromosome count. During vegetative fission it elongates and partitions by the process traditionally called amitosis, without the conventional mitotic chromosome sequence. This term must not imply an unorganized split or absence of microtubule-mediated mechanics. The hatched outline is original schematic anatomy; size, indentation and position are not calibrated measurements of a specimen.",
        "பேருட்கரு (MAC) என்பது பெரிய உடலியக்க உட்கரு. இயல்பான மரபணு வெளிப்பாடு, வளர்ச்சி, அன்றாட உடலியல் பணிகள் ஆகியவற்றை ஆதரிக்கிறது. வெளிப்படும் மரபணுப் பகுதிகளின் பல பிரதிகளைக் கொண்ட அதன் மரபணுத்தொகுப்பு, மரபுவழித் தொகுப்பிலிருந்து வளர்ச்சிசார் மறுசீரமைப்பைப் பெற்றது. பெரிய உட்கரு என்பதால் அது ஆண் உட்கருவோ மூளையோ அல்ல; அதன் வரைபடம் ஒரு நிலையான குரோமோசோம் எண்ணிக்கையையும் குறிக்காது. இயல்பான இருபிளவின்போது வழக்கமான மைட்டாசிஸ் குரோமோசோம் தொடரின்றி நீண்டு, பாரம்பரியமாக அமிட்டாசிஸ் எனப்படும் முறையில் பகிரப்படுகிறது. இச்சொல் ஒழுங்கற்ற வெட்டுதலையோ நுண்குழாய் இயக்கவியல் இல்லாமையையோ குறிக்கக்கூடாது. கோடிட்ட பென்சில் வடிவம் அசல் விளக்க வரைபடம்; அதன் அளவு, வளைவு, அமைவிடம் ஆகியவை மாதிரியில் அளவிடப்பட்டவை அல்ல.")
    val micronucleusExplanation = b(
        "The micronucleus (MIC) is the compact germline nucleus, drawn beside the MAC in this P. caudatum teaching model. The usual vegetative complement is one MIC. It preserves hereditary information and divides mitotically during vegetative fission; during conjugation its derivatives undergo meiosis, selection, pronuclear formation, reciprocal exchange and fertilization. MAC and MIC are functional nuclear compartments, not male and female organs. Diploid describes homologous chromosome sets, not nuclear size or total DNA mass. The large MIC inset is a separate scale and the drawn chromosome marks are not a species karyotype.",
        "சிற்றுட்கரு (MIC) என்பது சிறிய, செறிவான மரபுவழி உட்கரு; இப்பாரமீசியம் கௌடேட்டம் கற்பித்தல் மாதிரியில் பேருட்கருவுக்கு அருகில் காட்டப்படுகிறது. இயல்பான வளர்ச்சி நிலையில் பொதுவாக ஒரு சிற்றுட்கரு உள்ளது. மரபுரிமைத் தகவலைப் பேணி, இருபிளவின்போது மைட்டாசிஸ் முறையில் பிரிகிறது. இணைவின்போது அதிலிருந்து தோன்றும் உட்கருக்கள் மியோசிஸ், தேர்வு, முன்உட்கரு உருவாக்கம், பரஸ்பரப் பரிமாற்றம், கருவுறுதல் ஆகிய நிகழ்வுகளில் பங்கேற்கின்றன. பேருட்கருவும் சிற்றுட்கருவும் பணிசார் பகுதிகள்; ஆண், பெண் உறுப்புகள் அல்ல. இருமயம் என்பது ஒத்த குரோமோசோம் தொகுதிகளைக் குறிக்கும்; உட்கரு அளவையோ மொத்த DNA நிறையையோ குறிக்காது. சிற்றுட்கருவின் பெரிதாக்கிய சிறுபடம் வேறு அளவில் உள்ளது; குரோமோசோம் குறிகள் இனத்தின் உண்மையான காரியோடைப் அல்ல.")
    val dimorphism = NuclearChapter("dimorphism", 1,
        b("1. Nuclear dimorphism", "1. உட்கரு இருவகைமை"),
        listOf(
            NuclearReading("1.1", b("1.1 Two nuclei in one cell", "1.1 ஒரே செல்லில் இருவகை உட்கருக்கள்"), listOf(b(
                "Paramecium is one eukaryotic cell with a specialized cortex, cilia, feeding structures and two kinds of nucleus in the same cytoplasm. Nuclear dimorphism is a division of nuclear roles, not a division into two cells. In the named P. caudatum model a larger MAC and one compact MIC are distinguished. Other Paramecium species can have different germline complements; the common two-MIC P. tetraurelia diagram must not be silently transferred to this plate. Touch either nucleus or its enlarged inset, then compare its full explanation with the other nucleus.",
                "பாரமீசியம் ஒரே யூகேரியோட்டு செல்; சிறப்புப் புறப்படலம், குறுஇழைகள், உணவமைப்பு, ஒரே சைட்டோபிளாசத்தில் இருவகை உட்கருக்கள் ஆகியவற்றைக் கொண்டது. உட்கரு இருவகைமை என்பது உட்கருப் பணிகள் வேறுபடுதல்; செல் இரண்டாகப் பிரிதல் அல்ல. இங்கு பெயரிடப்பட்ட கௌடேட்டம் மாதிரியில் பெரிய பேருட்கருவும் ஒரு செறிவான சிற்றுட்கருவும் வேறுபடுத்தப்படுகின்றன. பிற பாரமீசியம் இனங்களில் மரபுவழி உட்கரு எண்ணிக்கை வேறுபடலாம். டெட்ராஆரீலியாவில் வழக்கமாகக் காட்டப்படும் இரண்டு சிற்றுட்கருக்களை இப்பொழுது கௌடேட்டம் படத்தில் சேர்க்கக்கூடாது. உட்கருவையோ பெரிதாக்கிய சிறுபடத்தையோ தொட்டு, அதன் முழு விளக்கத்தை மற்ற உட்கருவுடன் ஒப்பிடுக."))),
            NuclearReading("1.2", b("1.2 Somatic expression and germline inheritance", "1.2 உடலியக்க மரபணு வெளிப்பாடும் மரபுரிமையும்"), listOf(macronucleusExplanation, micronucleusExplanation)),
            NuclearReading("1.3", b("1.3 Ploidy, replication and genome copies", "1.3 மயநிலை, DNA இரட்டிப்பும் மரபணுப் பிரதிகளும்"), listOf(b(
                "A diploid germline nucleus has homologous chromosome sets. DNA replication makes sister chromatids before division; it does not by itself turn a diploid nucleus into a tetraploid nucleus. In a chromosome teaching cartoon a replicated chromosome is shown as two joined sister chromatids. Homologous chromosomes separate at meiosis I, and sister chromatids separate at meiosis II. Macronuclear gene copy number and germline chromosome-set ploidy are different quantities. Quantitative somatic copy numbers from P. tetraurelia sequencing are not measurements for this P. caudatum cell.",
                "இருமய மரபுவழி உட்கருவில் ஒத்த குரோமோசோம் தொகுதிகள் உள்ளன. பிரிவுக்கு முன் DNA இரட்டிப்பதால் சகோதரி குரோமாட்டிடுகள் உருவாகின்றன; இதனால் மட்டும் இருமய உட்கரு நான்குமயமாக மாறுவதில்லை. கற்பித்தல் படத்தில் இரட்டித்த குரோமோசோம் இணைந்த இரண்டு சகோதரி குரோமாட்டிடுகளாகக் காட்டப்படுகிறது. மியோசிஸ் I-இல் ஒத்த குரோமோசோம்களும் மியோசிஸ் II-இல் சகோதரி குரோமாட்டிடுகளும் பிரிகின்றன. பேருட்கருவின் மரபணுப் பிரதிகள் எண்ணிக்கையும் சிற்றுட்கருவின் குரோமோசோம் தொகுதி மயநிலையும் வேறு அளவுகள். டெட்ராஆரீலியா மரபணு வரிசையாக்கத்தில் பெறப்பட்ட பிரதிகள் எண்ணிக்கை இக்கௌடேட்டம் செல்லின் அளவீடு அல்ல."))),
            NuclearReading("1.4", b("1.4 Evidence and anatomical limits", "1.4 ஆதாரங்களும் உடலமைப்பு வரம்புகளும்"), listOf(b(
                "Staining and appropriate microscopy can reveal nuclear shape and position, but an unstained moving wet mount may not resolve the MIC. Fluorescence, antibody localization, transplantation and genomic experiments answer different questions about nuclear function. This original graphite plate combines an anatomical outline with separately enlarged teaching insets. Neither the chromosome marks nor the difference in drawing size is a microscopy measurement. The whole-cell orientation, nuclear indentation and Tamil terminology remain DRAFT_UNVERIFIED until an identified specialist records a decision.",
                "சாயமேற்றலும் பொருத்தமான நுண்ணோக்கியும் உட்கருவின் வடிவம், அமைவிடத்தை வெளிப்படுத்தலாம்; சாயமேற்றாத நகரும் ஈரமாதிரியில் சிற்றுட்கரு தெளிவாகத் தெரியாமல் இருக்கலாம். ஒளிர்வுக் குறியீடு, எதிர்ப்புரத அமைவிட ஆய்வு, உட்கரு மாற்று, மரபணுத்தொகுப்பு ஆய்வு ஆகியவை வெவ்வேறு பணிசார் கேள்விகளுக்குப் பதிலளிக்கின்றன. இப்பென்சில் படம் உடலமைப்பு வரைபடத்தையும் தனியே பெரிதாக்கிய கற்பித்தல் சிறுபடங்களையும் இணைக்கிறது. குரோமோசோம் குறிகளும் வரைபட அளவு வேறுபாடும் நுண்ணோக்கி அளவீடுகள் அல்ல. செல் திசை, உட்கரு வளைவு, தமிழ் சொற்கள் ஆகியவை பெயரிடப்பட்ட நிபுணர் முடிவு தரும் வரை DRAFT_UNVERIFIED நிலையில் இருக்கும்."))),
        ), listOf(
            NuclearView("whole-cell", b("Whole-cell nuclear relationship", "முழுச்செல்லின் உட்கரு உறவு"), b(
                "The two nuclear compartments share one cytoplasm. The larger hatched MAC and compact MIC are separately selectable. The right-hand insets enlarge each nucleus at a different scale; they are not extra nuclei inside the cell. Anterior is at the left and posterior at the right in this schematic horizontal projection. Cilia and the oral region provide context, not a complete species-certified atlas.",
                "இரு உட்கருப் பகுதிகளும் ஒரே சைட்டோபிளாசத்தில் உள்ளன. பெரிய கோடிட்ட பேருட்கருவையும் சிறிய சிற்றுட்கருவையும் தனித்தனியாகத் தேர்ந்தெடுக்கலாம். வலப்புறச் சிறுபடங்கள் அவற்றை வேறு அளவில் பெரிதாக்குகின்றன; அவை செல்லுக்குள் உள்ள கூடுதல் உட்கருக்கள் அல்ல. இக்கிடைமட்ட விளக்கத்தில் முன்முனை இடதிலும் பின்முனை வலதிலும் உள்ளது. குறுஇழைகளும் வாய்ப்பகுதியும் சூழலை விளக்குகின்றன; முழுமையான இனச் சான்றளிக்கப்பட்ட உடலமைப்புப் படம் அல்ல.")),
            NuclearView("somatic", b("Macronucleus: somatic work", "பேருட்கரு: உடலியக்கப் பணி"), macronucleusExplanation),
            NuclearView("germline", b("Micronucleus: hereditary continuity", "சிற்றுட்கரு: மரபுரிமைத் தொடர்ச்சி"), micronucleusExplanation),
        ), listOf("taka2006", "duret2008", "tucker1980"))
    val chapters: List<NuclearChapter> get() = listOf(dimorphism, ParameciumFissionChapter.chapter, ParameciumConjugationChapter.chapter)
    fun chapter(id: String) = chapters.firstOrNull { it.id == id } ?: dimorphism
    fun nucleus(id: String) = if (id == "micronucleus") micronucleusExplanation else macronucleusExplanation
    fun nucleusName(id: String) = if (id == "micronucleus") b("Micronucleus · MIC", "சிற்றுட்கரு · MIC") else b("Macronucleus · MAC", "பேருட்கரு · MAC")
}
