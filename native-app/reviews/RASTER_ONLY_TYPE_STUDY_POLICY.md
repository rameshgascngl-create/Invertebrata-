# Mandatory pencil-raster anatomical plates — all native type studies

**Scope:** Sycon and Obelia now; all future Fasciola, Ascaris, Earthworm,
Penaeus, Pila, Asterias and subsequently revised Paramecium plates.
This policy does **not** silently replace or edit the existing R1.9
Paramecium teaching module, validated A5 corpus or curriculum chapter IDs.

## Runtime architecture

* Primary visible organism anatomy MUST be a labelled **PNG** or **WebP**
  pencil-drawn master, decoded with Android BitmapFactory. PNG has priority.
* SVG files, Compose Canvas/DrawScope anatomy and generated vector fallback
  images are **not** permitted as teaching plates.
* Compose Canvas is allowed **only** for controls superimposed on a genuine
  bitmap (hotspot selection ring, guides, pan/zoom interaction).
* Missing/corrupt raster shows a visible error; the application may not
  silently show a schematic substitute. The Android instrumentation test
  requires an actual rendered bitmap.
* A lower-resolution copy cannot be declared accepted solely because
  it decodes; native CI validates minimum image dimensions, label
  readability still requires human review.

## Current Unit II candidate

* `unit2/sycon_plate.webp`
* `unit2/obelia_colony.webp`
* `unit2/obelia_lifecycle.webp`

The candidate workflow may apply deterministic source-pixel corrections
before packaging. **These assets are provisional**, not a claim of genuine
scanned origin or of independent anatomical approval. Existing atlas
source art is raster; this policy alone cannot prove it was drawn by hand.

The renderer uses `rememberRequiredRasterBitmap(stem)`, which in turn
uses `RasterArtworkPolicy.selectAsset()`. It never calls a native
schematic fallback.

## All future units — type-study plate contract

Use `NativeCurriculumManifest.typeStudies[*].requiredPlateIds` as the
complete per-organism plate inventory. Place each new actual pencil plate at:

`native-app/app/src/main/assets/type_studies/<organismId>/<plateId>.png`

or the `.webp` equivalent. Do not mark a type study academically
accepted until `RasterArtworkPolicy.requireCompleteTypeStudy()` succeeds
for every listed plate and the visible teaching screen uses the shared
raster loader.

**The nine organisms remain at their recorded DRAFT or NOT_AUTHORED
statuses**. This commit specifies the architecture and acceptance gate;
it does not invent or auto-approve unavailable artwork.

## Academic / device acceptance is separate

Manual check of anatomical pathways, leader-line endpoints, bilingual
labels, authentic pencil appearance, zoom/rotation/TalkBack, offline audio,
and physical handset behavior is still mandatory. A green emulator
or bitmap source check alone is not a final release authorization.
