# INVERTEBRATA — Seven remaining source-derived PNGs staged for student debug QA

Date: 10 November 2026. Commit base `89d8c295`.

All eleven previously held figure subjects now have **11 independently sourced or source-composited PNG candidates** in `native-app/app/src/debug/assets/qa_source_plates` and are selected by the native Compose textbook in debuggable QA builds only. The previous four staged plates (Sycon reproduction, Asterias internal/larvae, Leishmania) remain unchanged, and seven new images are present. These are **QA candidates**, not eleven formally ACCEPTED academic masters.

The new seven correspond exactly to:
- `paramecium/oral-apparatus` → `01_paramecium_oral_sourced.png`
- `sycon/external` → `02_sycon_external_sourced.png`
- `obelia/medusa` → `04_obelia_medusa_sourced.png`
- `obelia/life-cycle` → `05_obelia_lifecycle_sourced.png`
- `ascaris/female-internal` → `06_ascaris_female_sourced.png`
- `earthworm/excretory` → `07_earthworm_excretory_sourced.png`
- `pila/external` → `08_pila_external_sourced.png`

**Known teaching limitations (must not be concealed):** Paramecium plate presents whole-cell oral structures, not a large cytopharynx cutaway; Sycon is a generalised syconoid body plan; Obelia medusa has small historical linework; Obelia life-cycle stages include original explanatory schematic symbols; the Ascaris plate includes a male for comparison as well as the left female dissection; the nephridia panel combines source-accurate generic oligochaete/Lumbricus figures with Pheretima-specific explanatory notes; Pila's specimen photograph does not expose its eyes and the 1905 Ampullaria engraving is a comparative apple snail, not the same identified species.

Each new source, author, licence, URL and exact-source SHA-256 is documented in the source-workflow artefact `remaining-seven-review/PROVENANCE.json` and checked into source metadata at `qa-source-intake/remaining_seven_sources.json`. Every committed PNG is pinned to its Git blob digest in `qa-source-intake/verify_staged_debug_assets.py`.

Do not merge or approve release: the original mandatory raster masters and 41 green-ticked figures have not been overwritten. The independent scientific, Tamil and source-rights sign-off sheet remains unapproved, and physical-device acceptance is required before distribution. This QA decision means **viewable for feedback**, not a certification of anatomical completeness.
