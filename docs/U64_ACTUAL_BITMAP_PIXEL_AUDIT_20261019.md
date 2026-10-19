# U64 — Actual bitmap inspection of committed review images (19 October 2026)

**Academic disposition: HOLD.** Evidence originates from the **native Android debug APK**, not inferred from filenames, Kotlin source or generated placeholders.

- APK: U62 [Actions run 39254636739](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/39254636739), `app-debug.apk`, 105,200,928 bytes, SHA-256 `77297c1d9ea12c5d56d6ca6dd64da7ce9e8c04c79a895d43876db65476b3ae94`.
- Inspected the image pixels inside the APK: six Earthworm, five Penaeus, three Pila U59, three Asterias U59, twelve Pila editorial images, seven Asterias editorial/reference images.
- Artifacts are unapproved development references and are **not authentic graphite/pencil-scan masters** merely because they are raster PNGs. Many use colour-rendered biological illustrations with typography/poster layouts.
- Source/licence rights, organism-level morphology, bilingual text spelling and leader-line endpoints are **not** certified by this visual audit.

## Definite wrong-subject placements (fixed in U64 debug reader)

| Committed filename | Actual pictured subject from visible image | Original debug lesson mapping | Correction |
|---|---|---|---|
| `15_asterias_external_REVIEW_ONLY.png` | Asterias **water-vascular system**: aboral/oral diagrams, madreporite, stone canal, radial canal, tube feet | `asterias/external` — **wrong** | Suppress this from external section; show only as **review-only water-vascular** image with filename-mismatch warning |
| `16_asterias_water_vascular_REVIEW_ONLY.png` | Asterias **nervous system**: nerve ring and radial nerves | `asterias/water-vascular` — **wrong** | Remove from water-vascular lesson; keep original committed file unchanged as review material |

This visual finding supersedes the U63 *filename-only* 16/7 inventory. The corrected **actual-subject candidate mapping is 15 named candidates; eight unfilled plate subjects have no directly associated reviewed original**, including Asterias external. There is **no automatic release approval** for the 15.

## Cropping / source-resolution defects visible in the APK

| Image | Actual pixel dimensions | Defect |
|---|---:|---|
| `03_earthworm_vascular_REVIEW_ONLY.png` | 250 × 425 | The left-hand part of the original diagram/caption is clipped; tiny bilingual legends |
| `04_earthworm_nervous_REVIEW_ONLY.png` | 235 × 425 | Left-side crop removes caption/context; inadequate image width |
| `05_earthworm_excretory_REVIEW_ONLY.png` | 240 × 425 | Left-side crop removes content; English/Tamil legends impractical at mobile display size |
| `09_penaeus_gills_REVIEW_ONLY.png` | 276 × 310 | Left-side crop and very small labels |
| `11_penaeus_lifecycle_REVIEW_ONLY.png` | 250 × 310 | Left-side crop and loss of full life-cycle framing |
| `13_pila_internal_REVIEW_ONLY.png` | 270 × 289 | Very low-resolution small reference; not a validated mantle-cavity master |
| Pila 12-panel editorial PNG series | approx. 384 × 341 each | Insufficient source resolution to certify tiny labels/zoom readability; panels are not pencil scans |

The U59 review assets provide multiple full-colour infographic/poster sheets (including Earthworm digestive/reproductive, Penaeus external/mouthparts/internal, Pila external/radula and Asterias internal). Those must **not** be represented as genuine hand-drawn graphite images. Large dimensions alone do not prove biological accuracy.

## Next engineering and teaching acceptance

1. Preserve all original Git objects, and keep every unapproved illustration in `src/debug/assets/review_only` or the original review archive.
2. Reconstruct or source corrected **independent** high-resolution pencil master drawings for cropped or wrong-subject content **only after checking whether corresponding genuine originals exist elsewhere in the committed repository**. Do not enlarge a 235-pixel crop and call it restored.
3. For every candidate, review correct organism, anterior/posterior and oral/aboral orientation, anatomical labels and leaders, standard Tamil zoological terminology, and rights/provenance.
4. Only after plate-specific sign-off, map independent verified image bytes into the mandatory `src/main/assets/type_studies/<organism>/<plate>.png` location, with one image per appropriate chapter lesson. Preserve the A5 assessment separately.
5. Run the actual Android API 34 screenshot + UI accessibility hierarchy tests at normal and 200% Tamil; inspect zoom/fullscroll, rotation, clipping, duplicate rendering and physical-device behaviour.

**This is an evidence-backed rejection of incorrect image placement**, not approval of the rest of the images.
