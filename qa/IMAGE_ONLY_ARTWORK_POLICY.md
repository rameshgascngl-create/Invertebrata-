# INVERTEBRATA — IMAGE-ONLY ANATOMICAL PLATES (Units I–V)
**Decision: 17 October 2026 — non-negotiable student-facing artwork rule**

The user explicitly rejected computer-generated Kotlin/Compose Canvas sketches in **every unit**. Use **reviewed, static, genuine image assets** (PNG/WebP/high-resolution scans; keep original master + redistribution permission and source provenance) for organism morphology, internal organ systems, life cycles, and practical-record drawings. Do not create replacement sketches with `Canvas`, `DrawScope`, `drawPath`, `VectorDrawable`, CSS/SVG line rendering, or tap-generated geometry. A computer-generated *raster* is still subject to anatomical, typography and provenance review; converting a Canvas sketch to bitmap does **not** confer academic approval.

The native Android view shall use `Image(bitmap = ...)` or `Image(painter = ...)` for approved static files. English/Tamil headings, verified anatomical nomenclature, source credits, and explanatory lessons remain accessible native `Text`, not generated writing painted over anatomical parts. No organ hotspots, interactive outline diagrams or animated organ sketches. Retain textbook lessons, A5 data, state recovery, and approved non-diagram activities. Do not silently remove course content to make the artwork check pass.

## Approval gates
1. Every image plate is tied to an exact organism, projection, specimen or generalized diagram type and has a source/rights record.
2. Scientific editorial review signs off the depicted structures and all leader endpoints. Reject invented organs, including an unsupported crop/gizzard or lateral hearts in *Nereis*.
3. Tamil and English terminology is independently reviewed against the college practical manual. Avoid embedding unverified bilingual wording into the irreversible image.
4. Inspect actual PNG screenshots on Android at 100% and 200% font scale; confirm complete framing, authentic picture pixels, legibility, no clipping, no previous-item captures, and all final labels.
5. Build, device acceptance and academic approval remain distinct. A green Kotlin build or emulator suite does not grant artwork acceptance.
6. When a verified pencil image is unavailable, retain the biological lesson and keep the illustration unpublished, marked for editorial acquisition. Never replace it with a low-fidelity procedural sketch, placeholder anatomy presented as final, or unverified generated drawing.
7. A dedicated CI image-only release gate **fails** while any legacy Compose Canvas sketch or generated VectorDrawable illustration remains in the app source. Do not weaken or bypass it to obtain a green release.

## Scope inventory — existing legacy artwork, not accepted
- **Unit I:** Paramecium anatomy/feeding/nutrition/ciliary/nuclear/egestion/physiology/osmoregulation/teaching-lab Canvas figures, and flagellate life-cycle/stage illustrations. The independent raster reference gallery may remain subject to review.
- **Unit II:** Sycon/Obelia image viewer contains a Canvas annotation overlay; remove overlay while preserving genuine existing WebP files and text, subject to image-rights review.
- **Unit III:** Fasciola/Ascaris gallery contains a Canvas hotspot overlay. Retain verified source raster files, remove the procedural overlay.
- **Unit IV:** Nereis 7 remaining Canvas plates and one Canvas leader overlay; the U4.8 vector candidate is *not* an authenticated pencil master. Earthworm and Penaeus have Canvas anatomy sketches. Replace, do not certify the currently programmatic renderers.
- **Unit V:** No current Canvas-based anatomical plate verified in source, but full image coverage and academic approval are NOT established. No special exemption.

**Migration order:** maintain U4.8 as pre-policy engineering baseline; migrate reviewable Unit II/III raster surfaces first, then replace Nereis, Earthworm and Penaeus figure assets, followed by Unit I's numerous anatomy/physiology figures. Audit Unit V's missing source-reviewed images separately. Preserve source and QA provenance on a new branch until the image-only release gate passes.

No Play Store / Indus production APK should be built or published from a branch failing the image-only gate. 
