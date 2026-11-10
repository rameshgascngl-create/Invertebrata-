# INVERTEBRATA — held anatomical plate correction queue
**Date:** 10 November 2026  
**QA figure decision source:** `qa-intake/provisional-figure-inclusion-20261110.json`  
**Implementation checkpoint:** `38cec5d4` (bilingual scientific captions; compile/JVM tests passed)

## Scope and acceptance
The owner's 41 `GREEN_TICK_QA` figures remain intact. The 11 entries marked `HOLD_CORRECTION` are not approved anatomical master plates. Preserve all original bytes, author records and rights evidence; stage corrected figures as **separate candidate PNGs** until visual and anatomical checks pass. Do not use SVG or pretend that a photo is an anatomical diagram. English image labels are acceptable; Tamil explanations and cautions must remain native text. Source attribution must refer to the exact image bytes, not a previous version. User's provisional legibility approval does **not** waive the independent science, Tamil or rights gate.

A corrected candidate must: (a) show the essential learning objective in a self-contained drawing; (b) have clear readable label leaders at phone-size and in the 200% Tamil view; (c) be distinguishable from another lesson's figure; (d) preserve pinch zoom and a complete figure without clipping; (e) record author/source/licence and SHA-256; (f) receive a new explicit QA review before replacing any registered master. Existing source assets must not be overwritten without comparison and approval.

## Prioritised replacement specifications

| Priority | Held asset | Correction required | Acceptance evidence |
|---|---|---|---|
| P0 | `type_studies/pila/external` | Redraw the anterior soft parts and relocate each eye to the small lateral prominence **near the base of its cephalic tentacle**, not the tip. Distinguish tentacle, snout, eyes, operculum and aperture. | All pointers verified; English-labelled PNG; source and scale disclosed |
| P0 | `type_studies/obelia/life-cycle` | One complete metagenesis plate: sessile colony/gonangium budding medusae → sexual medusae/gametes → fertilisation → ciliated planula → settlement → new hydroid colony. Explicitly distinguish asexual hydroid budding from sexual medusa stage. | Complete uninterrupted cycle with arrows and labelled planula |
| P0 | `unit1/replacements/leishmania_morphology` | A **two-form** original comparative plate: round/ovoid intracellular amastigote (nucleus and kinetoplast, no prominent free flagellum) beside elongated anteriorly flagellated sand-fly promastigote (central nucleus, anterior kinetoplast). Do not reuse the old amastigote-only image as full morphology. | Both forms and host contexts separately labelled; no false image-source rights |
| P1 | `type_studies/asterias/internal` | Replace overlapping, wrongly pointing composite with clearly separated internal organ-system views: cardiac/pyloric stomach, pyloric caeca, gonads, and (if shown) water-vascular parts. Label the actual organ, not the surrounding cavity. | Legible leader endpoints; organ separation apparent |
| P1 | `type_studies/asterias/larval-development` | Provide uncropped, labelled bipinnaria and brachiolaria diagrams; indicate brachiolar arms/attachment apparatus where present. Do not mix an unrelated larval taxon. | Stages independently recognisable, complete outlines |
| P1 | `type_studies/ascaris/female-internal` | Distinguish straight alimentary tract from paired, elongated coiled ovaries, oviducts and uteri, vagina/vulva; preserve female-specific proportions. | Each reproductive organ linked to a visible structure |
| P1 | `type_studies/earthworm/excretory` | Legible enlargement of nephridia. If illustrating types, specify septal, integumentary and pharyngeal nephridia and route/opening differences, instead of one cramped unlabeled body plan. | Type/duct/outlet mapping consistent; large labels |
| P2 | `unit1/replacements/paramecium_oral-apparatus` | Replace the 305 × 328 px crop with a full readable oral-structure diagram: oral groove, vestibule, cytostome, cytopharynx and food-vacuole formation. | Complete label leaders; no cropped text |
| P2 | `type_studies/sycon/external` | Replace specimen-only photograph with diagnostic **Sycon** body form, osculum, ostia and wall architecture in context. | Organism identifiable without relying on a caption alone |
| P2 | `type_studies/sycon/reproduction` | Replace six unkeyed thumbnails with clearly ordered reproductive development including fertilisation, larval differentiation (amphiblastula), release and settlement. | Stage order arrows and captions legible |
| P2 | `type_studies/obelia/medusa` | Replace indistinct medusa photo with a clear labelled medusa anatomy plate (umbrella, manubrium, radial canals, gonads, marginal tentacles). | Main anatomy visible at phone size |

## Source checks for the high-risk corrections

- *Pila globosa*: the eyes occupy small prominences on the **outer sides of the tentacle bases**. Source: [Freshwater Biodiversity Portal, Pila globosa](https://freshwaterbiodiversity.org.bd/species/pila-globosa_gastropod/).
- *Obelia*: colonial hydroid, free-living sexual medusa and planula are distinct life-history stages. Source: [Marine Life Information Network — Obelia longissima](https://www.marlin.ac.uk/species/detail/1912/1000).
- *Leishmania*: amastigotes occur in vertebrate macrophages; flagellated promastigotes occur in sand flies. Source: [CDC DPDx Leishmaniasis](https://www.cdc.gov/dpdx/leishmaniasis/). This biological reference is **not** an attribution or licence for any replacement artwork.

## Native Android correction already implemented

- `TypeStudyFigureReviewNotes.kt`: ten specific bilingual teaching cautions accompany the held type-study pictures.
- `RequiredTypeStudyPlates.kt`: these cautions appear within the book-style gallery and full-screen image viewer; provisionally accepted figures are identified as QA-only.
- `BookFigureLibrary.kt`: the Leishmania illustration is explicitly described as an **amastigote-only** candidate. It does not claim to depict the promastigote.
- `HeldFigureTeachingCorrectionsTest.kt`: guards the ten hold keys and key life-history corrections.

**Remaining:** The 11 source images themselves are not replaced. None is promoted to scientific or rights approval. The existing 52-plate academic release gate remains fail-closed.
