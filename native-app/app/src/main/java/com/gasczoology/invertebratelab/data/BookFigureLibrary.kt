package com.gasczoology.invertebratelab.data

/**
 * Figures that a book chapter may use in addition to the 35 required type-study plates.
 *
 * A library slot is written "lib/<id>". [stem] is the asset path WITHOUT extension (the
 * reader tries .png then .webp). Every entry carries a bilingual credit that the reader
 * shows under the figure, so no image can reach a chapter without attribution.
 *
 * Kinds of entry:
 *  - Nereis plates: AI-generated, bilingual-labelled plates already shipped for Unit IV.
 *  - Source images: public-domain references from Wikimedia Commons (provenance in
 *    assets/unit1/references/SOURCE_PROVENANCE.txt). The two CC-licensed files carry the
 *    author named in their credit.
 *  - Original schematics drawn by tools/book_figure_gen.py. They carry numerals only; the
 *    key is in the bilingual caption. They are NOT yet reviewed by a subject expert.
 */
data class BookLibraryFigure(val id: String, val stem: String, val credit: PlateCredit) {
    val slot: String get() = BookFigureLibrary.PREFIX + id
}

object BookFigureLibrary {
    const val PREFIX = "lib/"

    private val aiPlate = PlateCredit(
        "AI-generated illustration · Dept of Zoology, GASC Nagercoil · rights holder to confirm",
        "AI உருவாக்கிய விளக்கப்படம் · விலங்கியல் துறை, அரசு கலைக் கல்லூரி நாகர்கோவில் · உரிமையாளர் உறுதிப்படுத்த வேண்டும்")

    private val schematic = PlateCredit(
        "Original schematic drawn for this app · not to scale · subject-expert review pending",
        "இந்த செயலிக்காக வரையப்பட்ட மூலப் படத்திட்டம் · அளவுக்கேற்ப அல்ல · துறைசார் நிபுணர் மதிப்பாய்வு நிலுவையில்")

    private fun commonsPublicDomain(file: String) = PlateCredit(
        "Public domain · Wikimedia Commons, File:$file · simplified teaching reference, review pending",
        "பொதுப் பயன்பாட்டு உரிமை (Public domain) · விக்கிமீடியா காமன்ஸ், File:$file · எளிய கற்பித்தல் குறிப்புப் படம், மதிப்பாய்வு நிலுவையில்")

    /** Author details below were supplied by the app owner from the Commons file pages (2026-10-26)
     *  and are to be re-checked against those pages before release. */
    private val trypanosomaCredit = PlateCredit(
        "Johannahoog · CC BY-SA 3.0 (creativecommons.org/licenses/by-sa/3.0) · Wikimedia Commons, File:Trypanosoma_brucei.svg · converted from SVG to PNG, otherwise unchanged",
        "Johannahoog · CC BY-SA 3.0 (creativecommons.org/licenses/by-sa/3.0) · விக்கிமீடியா காமன்ஸ், File:Trypanosoma_brucei.svg · SVG-யிலிருந்து PNG ஆக மாற்றப்பட்டது, மற்றபடி மாற்றமில்லை")

    private val plasmodiumCredit = PlateCredit(
        "Sina Adl · CC BY 4.0 (creativecommons.org/licenses/by/4.0) · Wikimedia Commons, File:Malaria_Plasmodium_infection_cycle.png · unchanged",
        "Sina Adl · CC BY 4.0 (creativecommons.org/licenses/by/4.0) · விக்கிமீடியா காமன்ஸ், File:Malaria_Plasmodium_infection_cycle.png · மாற்றமில்லை")

    val all: List<BookLibraryFigure> = listOf(
        BookLibraryFigure("trypanosoma-cartoon", "unit1/references/trypanosoma", trypanosomaCredit),
        BookLibraryFigure("plasmodium-cycle", "unit1/references/plasmodium", plasmodiumCredit),
        BookLibraryFigure("nereis-external", "nereis/01_external_morphology", aiPlate),
        BookLibraryFigure("nereis-head", "nereis/02_enlarged_head", aiPlate),
        BookLibraryFigure("nereis-parapodium", "nereis/03_biramous_parapodium", aiPlate),
        BookLibraryFigure("nereis-digestive", "nereis/04_digestive_system", aiPlate),
        BookLibraryFigure("nereis-circulatory", "nereis/05_circulatory_system", aiPlate),
        BookLibraryFigure("nereis-nervous", "nereis/06_nervous_system", aiPlate),
        BookLibraryFigure("nereis-excretory", "nereis/07_excretory_system", aiPlate),
        BookLibraryFigure("nereis-trochophore", "nereis/08_trochophore_larva", aiPlate),
        BookLibraryFigure("entamoeba-cycle", "unit1/references/entamoeba",
            commonsPublicDomain("Entamoeba_histolytica_life_cycle-en.svg")),
        BookLibraryFigure("leishmania-amastigote", "unit1/references/leishmania",
            commonsPublicDomain("Leishmania_Intracellular_Amastigote_(NIH_BioArt_872_-_798227).png")),
    ) + listOf(
        "insect-tagmata", "metamorphosis", "parasitoid-cycle", "vector-cycle", "leech",
        "polychaete-asexual", "crinoid", "aurelia-cycle", "anemone-section",
        "taenia-cycle", "taenia-parts", "sponge-grades", "cnidarian-forms", "metameres",
        "host-cycles", "pearl-formation", "body-cavities", "nematode-bodywall", "nematode-routes",
        "mollusc-plan", "echino-symmetry", "cilium-stroke", "coral-polyp",
        "annelid-section", "flame-cell", "myriapod-sections",
    ).map { BookLibraryFigure(it, "book_figures/$it", schematic) }

    private val byId = all.associateBy { it.id }

    val slots: Set<String> = all.map { it.slot }.toSet()

    fun isLibrarySlot(slot: String) = slot.startsWith(PREFIX)

    /** Asset stem (no extension) for a "lib/<id>" slot, or null when it is not a library slot. */
    fun stemFor(slot: String): String? =
        if (isLibrarySlot(slot)) byId[slot.removePrefix(PREFIX)]?.stem else null

    fun creditFor(slot: String): PlateCredit? =
        if (isLibrarySlot(slot)) byId[slot.removePrefix(PREFIX)]?.credit else null
}
