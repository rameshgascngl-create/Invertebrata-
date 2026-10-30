# Unit III — Native raster magnification phase (Fasciola and Ascaris)

**Engineering phase: implementation candidate, academic and physical-device acceptance PENDING.**

## Parent and scope

Parent: U1.16 engineering PASS `fda489701f3abf26c7ff1bc7a7d6e6e50354a357` / GitHub run `38870193116`.
Unit I U1.17 remains separately under test-compilation correction and is NOT a prerequisite for implementing Unit III. Unit II remains under final human science/phone acceptance. No validated A5 academic JSON changes.

## Why
The six Unit III source-backed plates were already presented as native Compose bitmaps with offline bilingual organ-narration buttons. However, users could not magnify actual anatomical image pixels. Small vitellaria, suckers, ducts, internal nematode reproductive anatomy and printed text require a source-faithful zoom/pan interface.

## Actual native correction
- `UnitIIIPlateGallery.kt` now uses centre-pivot `graphicsLayer` scaling of the **actual decoded bitmap and its optional same-space Canvas marker overlay**, inside a clipped original aspect-ratio viewport. No shape regeneration, recolouring, vector substitute or new Internet permission.
- Per-plate saveable 1–4× magnification, deterministic 1→2→4× and reset buttons (48 dp minimum) plus pinch/pan on the image. Pan is bounded to half the magnification overflow; reset and stepped zoom recenter.
- `UnitIIIPlateHotspots.geometryApproved()` is still FALSE for all six unreviewed archives. Therefore **image taps and organ leader strokes remain disabled**. Source labels and accessible organ buttons are unchanged; a descriptive narration request is not proof of audible device sound.
- For possible future reviewed geometry, inverse hit testing uses zoom/pan-aware local coordinates; neither guessed old hotspot percentages nor a science approval has been inserted.
- The static microscope-source and graphite-facsimile disclosures remain unchanged. The Ascaris CDC cycle remains a source-derived grey-toned illustration, NOT authenticated hand-pencil provenance.

## Exact-commit acceptance
1. Build Kotlin debug, Android instrumentation and JVM tests at the exact HEAD; no WebView, HTML educational engine, Internet permission, changes to A5 or app release identifiers.
2. Offline API34 instrumentation at font scales 1.0 and 2.0, Unit III **6/6** cases and preserved Unit II **4/4** cases. Test existing Sycon/Obelia and Unit III stage selection remains intact.
3. Verify 1×→2×→4×→reset via native controls on Fasciola external and Tamil Ascaris life-cycle at both font scales, and assert no direct anatomical markers are enabled.
4. Inspect actual pixel screenshots `unit3-100/200-fasciola-magnified-4x.png` and `unit3-100/200-ascaris-magnified-4x.png`; ensure no image disappearance, clipped control text or lower-system-navigation collision.
5. Physical Android phone validation: pinch and pan 1×/2×/4×; correct bounds after each zoom level, portrait+landscape, TalkBack focus and labels, 200% Tamil, back, rotate during zoom, app process death and offline memory stability. This is **not** replaceable by emulator green tests.
6. Independent Zoologist + English/Tamil editorial review of every source raster and anatomical leader-line coordinate remains HOLD. Do not activate learner organ taps or call a historical print a hand-pencilled scan.

## Pending separate work
- U1.17 instrumentation test `NativeUnitIProtozoaReferenceTest.kt` had missing JUnit `assertNull` import; keep the narrow fix on its own native/u117 branch.
- Unit II source-pencil authenticity, image labels, Tamil audio/actual handset remain open.
- For Unit III real pencil atlas, original verified scan masters are still needed for Fasciola/Ascaris, plus source-pixel reviewed leaders and organ-on-touch audio. Zoom support alone is not final teaching completion.

**Release: HOLD. Neither a passing compile nor bitmapped zoom is full academic acceptance.**
