package com.gasczoology.invertebratelab.data

/**
 * Figures that a book chapter may use in addition to the 35 required type-study plates.
 *
 * A library slot is written "lib/<id>". [stem] is the asset path WITHOUT extension (the
 * reader tries .png then .webp). Evidenced bilingual credits are shown under their figures.
 * Pending source records never become invented AI-generation or creator claims.
 *
 * Kinds of entry:
 *  - Nereis plates: supplied bilingual-labelled plates already shipped for Unit IV; origin credits remain pending.
 *  - Source images: supplied reference rasters. Historical Commons attributions
 *    in SOURCE_PROVENANCE.txt do not cover later replacements; only current,
 *    evidenced credits may be returned.
 *  - Original schematics drawn by tools/book_figure_gen.py. They carry numerals only; the
 *    key is in the bilingual caption. They are NOT yet reviewed by a subject expert.
 */
/** RECORDED means metadata exists; it does not certify rights or academic acceptance. */
enum class FigureAttributionState { RECORDED, PENDING }

data class BookLibraryFigure(
    val id: String,
    val stem: String,
    val credit: PlateCredit,
    val attributionState: FigureAttributionState = FigureAttributionState.RECORDED,
) {
    val slot: String get() = BookFigureLibrary.PREFIX + id
}

object BookFigureLibrary {
    const val PREFIX = "lib/"

    private val aiPlate = PlateCredit(
        "AI-generated image · Dept of Zoology, GASC Nagercoil · rights holder to confirm",
        "AI உருவாக்கிய படம் · விலங்கியல் துறை, அரசு கலைக் கல்லூரி நாகர்கோவில் · உரிமையாளர் உறுதிப்படுத்த வேண்டும்")

    // Empty internal credit means no evidenced attribution is available; render no invented claim.
    private val pendingSource = PlateCredit("", "")

    private val schematic = PlateCredit(
        "Original schematic drawn for this app · not to scale · subject-expert review pending",
        "இந்த செயலிக்காக வரையப்பட்ட மூலப் படத்திட்டம் · அளவுக்கேற்ப அல்ல · துறைசார் நிபுணர் மதிப்பாய்வு நிலுவையில்")

    // Reuse the more detailed existing atlas illustration instead of the
    // defective numbered icon-drawing in the polymorphism chapter. Reuse does
    // not settle source rights or scientific review of the historic raster.
    private val obeliaNativeRaster = PlateCredit(
        "Existing Obelia colony type-study raster · source and rights review pending",
        "ஏற்கெனவே உள்ள ஒபீலியா குடியிருப்பின் இன ஆய்வுப் படம் · மூலமும் பயன்பாட்டு உரிமையும் மதிப்பாய்வு நிலுவையில்")

    private val coralNoaa = PlateCredit(
        "Original NOAA coral-polyp anatomy diagram · NOAA Ocean Service, Flower Garden Banks National Marine Sanctuary · full native 1000 × 1005 resolution, lossless PNG from source JPEG · US federal public domain · review pending",
        "NOAA மூல பவள பாலிப் உடற்கூறுப் படம் · NOAA Ocean Service, Flower Garden Banks தேசியக் கடல் சரணாலயம் · மூலத் தெளிவுத்திறன் 1000 × 1005, JPEG-யிலிருந்து தர இழப்பின்றி PNG வடிவம் · அமெரிக்க அரசு பொதுப் பயன்பாட்டுப் படம் · மதிப்பாய்வு நிலுவையில்")

    val all: List<BookLibraryFigure> = listOf(
        BookLibraryFigure("obelia-colony", "type_studies/obelia/colony", obeliaNativeRaster),
        BookLibraryFigure("coral-polyp", "book_figures/coral-polyp-noaa-original", coralNoaa),
        BookLibraryFigure("trypanosoma-cartoon", "unit1/references/trypanosoma", aiPlate),
        BookLibraryFigure("plasmodium-cycle", "unit1/references/plasmodium", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("pila-excretory-system", "pila/pila_excretory_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("pila-male-reproductive-system", "pila/pila_male_reproductive_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("pila-female-reproductive-system", "pila/pila_female_reproductive_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-external", "nereis/01_external_morphology", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-head", "nereis/02_enlarged_head", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-parapodium", "nereis/03_biramous_parapodium", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-digestive", "nereis/04_digestive_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-circulatory", "nereis/05_circulatory_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-nervous", "nereis/06_nervous_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-excretory", "nereis/07_excretory_system", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("nereis-trochophore", "nereis/08_trochophore_larva", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("entamoeba-mature-cyst", "unit1/references/entamoeba_mature_cyst", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("ascaris-fertilized-egg", "type_studies/ascaris/fertilized-egg", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("entamoeba-cycle", "unit1/references/entamoeba", pendingSource, FigureAttributionState.PENDING),
        BookLibraryFigure("leishmania-amastigote", "unit1/references/leishmania", aiPlate),
    ) + listOf(
        "insect-tagmata", "metamorphosis", "parasitoid-cycle", "vector-cycle", "leech",
        "polychaete-asexual", "crinoid", "aurelia-cycle", "anemone-section",
        "taenia-cycle", "taenia-parts", "sponge-grades", "cnidarian-forms", "metameres",
        "host-cycles", "pearl-formation", "body-cavities", "nematode-bodywall", "nematode-routes",
        "mollusc-plan", "echino-symmetry", "cilium-stroke",
        "annelid-section", "flame-cell", "myriapod-sections", "symmetry-types", "protozoan-groups",
        "blastopore-fate", "sponge-cells",
        "cuticle-layers", "limb-lever", "cephalopod-circulation", "cockroach-gut",
        "wuchereria-cycle", "fluke-larvae", "sponge-sexual-cycle", "food-vacuole",
        "tardigrade", "scaphopod", "termite-castes", "waggle-dance",
        "peripatus-external", "squid-jet", "chelicerate-plan", "stem-borer-cycle",
    ).map { BookLibraryFigure(it, "book_figures/$it", schematic) }

    private val byId = all.associateBy { it.id }

    val slots: Set<String> = all.map { it.slot }.toSet()

    fun isLibrarySlot(slot: String) = slot.startsWith(PREFIX)

    /** Asset stem (no extension) for a "lib/<id>" slot, or null when it is not a library slot. */
    fun stemFor(slot: String): String? =
        if (isLibrarySlot(slot)) byId[slot.removePrefix(PREFIX)]?.stem else null

    fun creditSlotFor(slot: String): String =
        stemFor(slot)?.removePrefix("type_studies/") ?: slot

    fun creditFor(slot: String): PlateCredit? =
        if (isLibrarySlot(slot)) byId[slot.removePrefix(PREFIX)]
            ?.takeIf { it.attributionState == FigureAttributionState.RECORDED }?.credit
            ?.takeIf { it.en.isNotBlank() && it.ta.isNotBlank() } else null
}
