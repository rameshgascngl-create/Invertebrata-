# U5.9 — Five-unit raster-image gap audit (19 October 2026)

Scope: native Android tree at U5.8 commit 7cf7159. This is a source-tree and selected Kotlin-reference audit, **not** a visual anatomical approval.

## Inventory
- No tracked `.svg` files in this commit.
- 35 PNG/WebP files in `native-app/app/src/main/assets/`.
- One XML drawable candidate: `native-app/app/src/main/res/drawable/nereis_external_graphite_candidate.xml`.
- 7 Asterias review-only PNGs under `native-app/app/src/debug/assets/review_only/asterias/`, excluded from production assets.
- 12 Pila review-only PNGs at repository root; not packaged as production assets.
- Unit I: 6 Paramecium PNGs, 4 parasite reference PNGs, 3 type-study Paramecium PNGs.
- Unit II: 3 unit-level WebPs plus 3 type-study WebPs.
- Unit III: 2 unit-level PNGs plus 6 type-study PNGs.
- Unit IV: 8 Nereis PNGs; no production Earthworm or Penaeus PNGs in assets tree.
- Unit V: no production Pila or Asterias PNGs in assets tree.

## Native vector/Canvas anatomical images still requiring raster replacement
- `ui/UnitIVEarthwormExternalAtlas.kt`: explicitly an original vector schematic rendered with Compose Canvas.
- `ui/UnitIVPenaeusVectorAtlas.kt`: explicitly a vector anatomical atlas rendered with Compose Canvas.
- Other `Canvas` calls must be inspected individually: some may be overlays, annotations, or non-anatomical interactions; do not blanket-delete them.
- `ui/UnitIIImagePlate.kt` already demands raster PNG/WebP and visibly flags missing files.

## Confirmed production raster gaps
1. Earthworm: no production raster master in assets. Obtain and scientifically verify external and internal systems plates before replacing its Canvas schematic.
2. Penaeus: no production raster master in assets. Obtain and verify external, appendages and internal anatomy before replacing vector atlas.
3. Pila: review candidates at repository root, but no production assets. Promote only reviewed plates with provenance, scientific correctness and Tamil labels.
4. Asterias: debug-only reference images, no production assets. Never silently package debug-only review artwork.
5. Sycon, Obelia, Fasciola, Ascaris: some raster files exist; complete per-lesson mapping and morphology audit are still needed.
6. Nereis: eight production PNGs exist; ensure the XML drawable is not displayed as a substitute and inspect all eight PNGs for source and morphology.
7. Unit I: verify that the four parasite reference images are displayed in the relevant lessons and not repeated indiscriminately.

## Acceptance rules
- Never call a Canvas-rendered diagram an SVG file: these are different representations.
- Do not rasterize an unapproved inaccurate drawing and claim it is a genuine pencil plate.
- Use independently reviewed PNG masters, stored in `src/main/assets`, with exact reader path and a visible failure state if absent.
- Maintain one illustration per relevant concept, accessible enlargement, English–Tamil readability and no unrelated repeats.
- Preserve existing academic text, A5 exercises, navigation and offline behaviour.
- After each batch: same-commit build, emulator screenshots at normal and 200% Tamil, visual review and physical-device QA.
- Do not promote review-only or debug-only files into production without academic and rights review.

Status: AUDIT OPEN. Asset replacement and scientific approval are NOT yet complete.
