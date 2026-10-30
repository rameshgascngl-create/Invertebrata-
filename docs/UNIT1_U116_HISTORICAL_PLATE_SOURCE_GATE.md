# U1.16 — Historical real-image source integrity and exact-pixel archival figure isolation

## Authoritative engineering base

- U1.15 accepted Android engineering HEAD: `73c37c12835e5434cba6ff96ceca981e96180980`.
- CI: https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38853424360
- Native Kotlin, Jetpack Compose, API34 airplane-mode instrumentation: **7/7 at 100% and 7/7 at 200%** on that exact base SHA. This is not academic or phone QA.

## Why a real historical source instead of inventing graphite pixels

The existing U1.15 Compose Canvas schematic is biologically differentiated but
not equivalent to a faithful scanned hand-pencil anatomical plate. The
infographic-style generated image was rejected. Original user-supplied hand
drawings cannot be recovered from the accessible file library in this phase.

A **published 1922 printed illustration**, NOT a recently hand-drawn pencil
master, provides a documented historical biology source for careful figure
extraction. Its precise original bytes can be recognized cryptographically.

Historical public-domain candidate:
- File: `Leishmania donovani (02).jpg` (1922), Fig. 14 of *Animal Parasites and Human Disease*, Asa C. Chandler, after Leishman.
- Licence/catalogue: https://commons.wikimedia.org/wiki/File:Leishmania_donovani_(02).jpg
- Source original: https://upload.wikimedia.org/wikipedia/commons/0/0a/Leishmania_donovani_%2802%29.jpg
- Public Domain Mark reported on Wikimedia Commons.
- Registered original size: **314,025 bytes**, **1552 × 1584** pixels.
- Published file's **SHA-1**: `3a08d99564510b548416ffb0bb4318eef68c1948`.

These values are from the Commons file description/structured original-file
metadata, **not** an estimated fingerprint of a screenshot or derivative image.

### Biological-caption distinctions (not approvals)

- **A–E:** isolated tissue forms/host-cell-associated parasite material; caption
  distinguishes spleen parasites, liver/bone-marrow dividing forms, host cells
  and leucocyte ingestion. Stage by individual letter remains **unverified**.
- **F–O:** CULTURE development series, **not** ten independent confirmed
  promastigotes. Caption explicitly notes F/G early post-ingestion forms,
  H dividing, I flagellum development, J a small flagellate, K/L flagellated
  Herpetomonas-like forms, M/N unequal division, O resulting parasites.
- **J–L** may be selected as an archival-caption subgroup, not treated as
  a visually proved, complete *Leishmania* promastigote plate.
- The source is a historically reproduced multi-figure printed illustration,
  not a modern stage-diagnostic photograph. Modern morphology should be
  crosschecked against CDC DPDx:
  https://www.cdc.gov/dpdx/leishmaniasis/

## Native Android implementation

- `UnitIHistoricalPlateArchive.kt`: authoritative immutable source identity,
  published SHA-1/bytes/dimensions, figure-group caption descriptions and
  fail-closed validation. An arbitrary similar photo or screenshot CANNOT
  inherit Commons provenance because it fails exact byte matching.
- `UnitIHistoricalPlateCropReviewer.kt`: debug-only native Compose archive
  panel in an **independent LazyColumn item** after the Leishmania import card,
  includes offline refresh button, two-original-pixel-corner selection,
  original pixel rectangle, and a 50×50 minimum to reject accidental tiny
  taps. Full-size images are decoded only on matching bytes.
- Exact source PNG cropped with `Bitmap.createBitmap()`, without interpolated
  resizing. Output through Android Storage Access Framework
  `CreateDocument("image/png")`, plus separate JSON containing original
  file SHA-1, imported original SHA-256, exact source pixel rectangle,
  resulting PNG SHA-256, historical caption subgroup and
  `DRAFT_FIGURE_CROP_NOT_ANATOMY_SIGNOFF`.
- Cropping is **disabled entirely** when the original file is absent or its
  source hash/dimensions/byte count do not match. Preview of ordinary
  user-supplied scans remains available separately under the existing
  debuggable-only importer.
- No Internet permission, no WebView/HTML/JS, no cloud image proxy.
  Reviewer manually obtains the historical file via external archives.
- No historical image is committed as a student asset in U1.16, because its
  exact original bytes were not retrievable through the active asset runtime.
  **Do not advertise it as a bundled original drawing.**
- The new figure-group selector does NOT assign approved morphology or
  enable a source organ touch hotspot. The existing digitally drawn
  teaching comparison remains visible and explicitly digital.

## Exact-commit tests and further release gates

1. Check changes against accepted U1.15; 43 A5 educational question sources,
   source-reference PNGs, production version and identifiers untouched.
2. JVM: published source identity, SHA1/size/dimension mismatch rejection,
   caption-stage restrictions, exclusive pixel geometry, NaN/bad crop rejection.
3. Android API34 test method 8: synthetic in-app PNG **cannot** pass published
   fingerprint and does not expose PNG/JSON crop-export controls. The same
   fixture tests actual `BitmapFactory` lossless crop pixels, so the crop
   algorithm is exercised without forging archival identity.
4. Run **8/8** native Android tests offline at both system font scales (100%
   and 200%), including all seven earlier complete acceptance tests, plus
   `u116-unmatched-archive-identity.png` in both evidence sets.
5. Distinct **positive** real historical source test remains OPEN until
   byte-identical original image is acquired. Do not manufacture a passing
   test with generated or textually reconstituted picture bytes.
6. Individual figure cropping and biological interpretation must be inspected
   by a zoologist before any student-visible raster plate or organ label
   appears. Author/source and Tamil academic review open.
7. Physical-device test: Android Files original source, offline import,
   horizontal/vertical taps, 200% Tamil, safe-area, multi-touch, source SHA,
   emitted crop PNG and provenance JSON, back/rotation, memory. A release build
   must expose **none of the debug curator tools**.

## Decisions

- U1.16 draft source is **not engineering accepted** until new exact-SHA CI
  and screenshot artifacts pass.
- Historical image authenticity is NOT hand-pencil authorship.
- Student-facing annotated pencil-plate replacement, verified leader-line
  endpoints and organ-on-touch speech: **ACADEMIC HOLD**.
- Production merger, signing, Google Play/Indus publication: **HOLD**.
