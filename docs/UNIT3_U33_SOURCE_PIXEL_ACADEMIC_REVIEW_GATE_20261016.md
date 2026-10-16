# INVERTEBRATA U3.3 — original raster source-pixel anatomical review

**Status:** Android native engineering DRAFT — **not** a completed verified anatomical atlas or a release.

## Baseline
- U3.2 source HEAD: `b2e0f83924670b35de7ef8c1407a4c5c422e2d5a`.
- GitHub Actions `38883183744` **PASS**: source generation, compile/JVM, API34 tests in airplane mode at 100% and 200% font: **6/6 Unit III + 4/4 Unit II each size**. Exact evidence ZIP was inspected, including 8 new full-screen 1×/4× screenshots, all passed XML and `status.txt`.
- The U3.2 full-screen source image is readable, but its organs remain unapproved and unmapped. Historical printed images and a grayscale CDC source facsimile are not documented as original scanned pencil plates.

## Implemented on U3.3
- `UnitIIIPixelReviewDraft.kt`: strict Kotlin proof-only model for all six syllabus plate stems, an exact hexadecimal 64-character PNG SHA-256, decoded source raster dimensions, valid **plate-specific** organ IDs and **inside-raster** finite pixel coordinates. Reject unknown organs, duplicated markers, NaN, negative and out-of-image pixels. All exports permanently indicate `DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF`. No function authorises student anatomy hotspots.
- `UnitIIIPixelReviewDialog.kt`: Android native, **debuggable-only** full-screen source-image reviewer. A human examiner chooses an organism-specific label, zooms/pans the actual bitmap, taps an observable organ and records its exact source-pixel position. Display and tap calibration reuse `NativeRasterCalibration.hit/project` with actual raster width/height; anti-letterbox, inverse zoom+translation and wrong-pixel protections apply. An on-screen circle is an **unapproved reviewer mark**, never a student anatomy leader line.
- Computes SHA-256 from the original **packaged APK PNG bytes**, not an external URL, screen capture or renderer; actual decoded dimensions are included. No change to original image contents.
- Android Storage Access Framework `CreateDocument("application/json")` writes a draft with source SHA, plate stem, label ID, original pixels and normalized values, with blank reviewer/subfigure approval fields. Nothing is uploaded or stored on a remote server.
- Debug-only entry button in `UnitIIIPlateGallery.kt` behind `isNativeSourceAuditDebuggable(context.applicationInfo.flags)`; no activation in non-debug student builds. Existing six source-specific `UnitIIIPlateHotspots.geometryApproved` flags remain disabled.

## Visual-quality correction after first green automation

The first U3.3 Actions run `38886343944` returned PASS (6/6 Unit III and 4/4 Unit II at 100% and 200%). Its actual 200% Tamil screenshots, however, were NOT visually acceptable: the `Clear` and `Draft JSON` controls wrapped vertically into narrow columns, leaving too little of the source raster visible. Green instrumentation alone was therefore not sufficient.

The next corrective source commit shortens the bilingual reviewer title/disclaimer, truncates the **on-screen** SHA display (the exported JSON retains the full source SHA-256), uses compact one-line 1× / + / × / JSON controls with explicit Tamil/English accessibility descriptions, and tests a **minimum 230 dp actual decoded-image viewport** at both font sizes. Exact screenshot review is required again before accepting this milestone.

## Acceptance checklist
1. Exact U3.3 SHA, compilation debug and release Kotlin, JVM `UnitIIIPixelReviewDraftTest`, Android instrumentation compilation and no A5/lesson/raster/Android permission changes.
2. API34 airplane-mode Unit III 6/6 and Unit II 4/4, **twice**, at actual 100% and 200% font scales; required `fasciola-source-review-draft` and `ascaris-source-review-draft` screenshots each at both sizes, alongside all previous evidence. Inspect original XML and image files, not just workflow green.
3. On actual device, reviewer confirms every purported spot on the **source bitmap**, within the correct subfigure and image SHA. Visually inspect 1×/2×/4× and pan, especially small organs and Tamil label mapping.
4. A reviewer must independently record source authorship/reuse permission, figure/subfigure, visible organ, leader terminus and zoological confirmation for each exported point. At least one other qualified reviewer checks the same evidence. Draft JSON alone never proves anatomy.
5. Physical Android QA: 100% and 200% Tamil, TalkBack, portrait/landscape, system bars, one- and two-finger gestures, rotation, Back and process death, SAF export/open, offline TTS pronunciation of existing separate organ controls and debug feature absent in non-debug release.
6. Only a later **distinct, explicitly approved** source-hash-bound release change may use reviewed endpoints and enable organ taps in production; reject any modified PNG or provenance mismatch.

## Current holds
- Genuine hand-scanned original pencil masters for the six plates are NOT obtained or independently certified. Copyright/attribution and each anatomical source-panel interpretation remain open.
- Human anatomical and Tamil editorial acceptance: **OPEN**.
- Verified physical handset and actual audible offline Tamil: **OPEN**.
- Production Google Play and Indus deployment: **HOLD**.
- Unit II and Unit I have separate academic holds; U3.3 must not silently merge their source paths.

**Academic truth:** geometry evidence is not anatomical truth. A marker whose pixel coordinate is reproducible may still point to the wrong organ or wrong source figure.
