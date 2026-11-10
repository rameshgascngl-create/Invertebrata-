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
        "AI-generated educational illustration · Department of Zoology, GASC Nagercoil",
        "AI மூலம் உருவாக்கப்பட்ட கல்வி விளக்கப்படம் · விலங்கியல் துறை, அரசு கலைக் கல்லூரி, நாகர்கோவில்")

    private val schematic = PlateCredit(
        "Original teaching schematic for this textbook · not to scale; see the numbered key in the lesson",
        "இப்பாடநூலுக்காக உருவாக்கப்பட்ட விளக்கப்படம் · அளவுக்கேற்ப அல்ல; பாடத்திலுள்ள எண் விளக்கங்களைப் பார்க்கவும்")

    // Reuse the more detailed existing atlas illustration instead of the
    // defective numbered icon-drawing in the polymorphism chapter. Reuse does
    // not settle source rights or scientific review of the historic raster.
    private val obeliaNativeRaster = PlateCredit(
        "Obelia colony type-study illustration · accompanying lesson explains hydranth and gonangium",
        "ஒபீலியா குடியிருப்பு இன ஆய்வுப் படம் · ஹைட்ராந்த், கோனாங்கியம் அமைப்புகள் பாடத்தில் விளக்கப்பட்டுள்ளன")

    private val coralNoaa = PlateCredit(
        "Original NOAA coral-polyp anatomy diagram · NOAA Ocean Service, Flower Garden Banks National Marine Sanctuary · full native 1000 × 1005 resolution, lossless PNG from source JPEG · US federal public domain",
        "NOAA மூல பவள பாலிப் உடற்கூறுப் படம் · NOAA Ocean Service, Flower Garden Banks தேசியக் கடல் சரணாலயம் · மூலத் தெளிவுத்திறன் 1000 × 1005, JPEG-யிலிருந்து தர இழப்பின்றி PNG வடிவம் · அமெரிக்க அரசு பொதுப் பயன்பாட்டுப் படம்")

    /** Matches the dimensions of the Commons file; the bytes are unverified, so re-check before release. */
    private val plasmodiumCredit = PlateCredit(
        "Sina Adl · CC BY 4.0 (creativecommons.org/licenses/by/4.0) · Wikimedia Commons, File:Malaria_Plasmodium_infection_cycle.png · unchanged",
        "Sina Adl · CC BY 4.0 (creativecommons.org/licenses/by/4.0) · விக்கிமீடியா காமன்ஸ், File:Malaria_Plasmodium_infection_cycle.png · மாற்றமில்லை")

    // This uploaded illustration depicts the amastigote ONLY. It must not be
    // misrepresented as a complete two-form Leishmania morphology plate.
    // Biological cross-check: CDC DPDx Leishmaniasis; not the image licence.
    private val leishmaniaAmastigoteOnly = PlateCredit(
        "AI-generated amastigote illustration (macrophage stage only). The flagellated promastigote in the sand fly is not shown; see the separate two-form illustration. Biological reference: CDC DPDx Leishmaniasis.",
        "AI உருவாக்கிய அமாஸ்டிகோட் விளக்கப்படம் (மேக்ரோஃபேஜ் நிலை மட்டும்). மணல் ஈயில் காணப்படும் கசையிழையுடைய புரோமாஸ்டிகோட் இதில் காட்டப்படவில்லை; இரு நிலை ஒப்பீட்டுப் படத்தைப் பார்க்கவும். உயிரியல் ஆதாரம்: CDC DPDx Leishmaniasis.")

    val all: List<BookLibraryFigure> = listOf(
        BookLibraryFigure("obelia-colony", "type_studies/obelia/colony", obeliaNativeRaster),
        BookLibraryFigure("coral-polyp", "book_figures/coral-polyp-noaa-original", coralNoaa),
        BookLibraryFigure("trypanosoma-cartoon", "unit1/references/trypanosoma", aiPlate),
        BookLibraryFigure("plasmodium-cycle", "unit1/references/plasmodium", plasmodiumCredit),
        BookLibraryFigure("pila-excretory-system", "pila/pila_excretory_system", schematic),
        BookLibraryFigure("pila-male-reproductive-system", "pila/pila_male_reproductive_system", schematic),
        BookLibraryFigure("pila-female-reproductive-system", "pila/pila_female_reproductive_system", schematic),
        BookLibraryFigure("nereis-external", "nereis/01_external_morphology", aiPlate),
        BookLibraryFigure("nereis-head", "nereis/02_enlarged_head", aiPlate),
        BookLibraryFigure("nereis-parapodium", "nereis/03_biramous_parapodium", aiPlate),
        BookLibraryFigure("nereis-digestive", "nereis/04_digestive_system", aiPlate),
        BookLibraryFigure("nereis-circulatory", "nereis/05_circulatory_system", aiPlate),
        BookLibraryFigure("nereis-nervous", "nereis/06_nervous_system", aiPlate),
        BookLibraryFigure("nereis-excretory", "nereis/07_excretory_system", aiPlate),
        BookLibraryFigure("nereis-trochophore", "nereis/08_trochophore_larva", aiPlate),
        BookLibraryFigure("entamoeba-mature-cyst", "unit1/references/entamoeba_mature_cyst", aiPlate),
        BookLibraryFigure("ascaris-fertilized-egg", "type_studies/ascaris/fertilized-egg", aiPlate),
        BookLibraryFigure("entamoeba-cycle", "unit1/references/entamoeba", aiPlate),
        BookLibraryFigure("leishmania-amastigote", "unit1/references/leishmania", leishmaniaAmastigoteOnly),
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

    fun creditFor(slot: String): PlateCredit? =
        if (isLibrarySlot(slot)) byId[slot.removePrefix(PREFIX)]?.credit else null
}
