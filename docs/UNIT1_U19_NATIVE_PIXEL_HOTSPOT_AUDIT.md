# U1.9 — NATIVE ANDROID anatomical pixel calibration (DEBUG QA ONLY)

## Purpose

The native Unit I source-reference gallery contains two independent attributed figures
(`unit1/references/trypanosoma.png`, `unit1/references/leishmania.png`).
They are **not approved handwritten graphite anatomy plates**. Previously the
application lacked a reproducible way to let a reviewer mark a specific structure
on the actual decoded source pixels, measure the marker endpoint and attach proof
of the exact reference image. U1.9 supplies that *review tool*, not an unapproved
student-facing anatomy overlay.

## Strict Android implementation

- **Kotlin data geometry:** `NativeRasterCalibration.kt` inversely maps a
  Compose `ContentScale.Fit` image under centre-origin graphics-layer zoom and pan
  into genuine raster pixel `(x, y)` and normalized `(x/w, y/h)` coordinates.
  Taps on empty letterbox padding are rejected instead of snapped to an image edge.
  The forward projection function keeps draft markers attached to pixels when zooming.
- **Debug-only Compose reviewer:** `UnitIAnatomyRasterAudit.kt` is accessible from
  the existing parasite figure **only** when Android `FLAG_DEBUGGABLE` is set.
  Release builds omit the launcher via a runtime check. It does not create final
  organ hotspots or overlay labels for ordinary students.
- **Portrait, landscape, 200% Tamil:** the full-screen native dialog scrolls;
  the source-image touch viewport stays at a fixed 360dp rather than collapsing
  when long Tamil copy or system font scaling consumes the screen.
- **Real image identity:** source image dimensions and SHA-256 of the packaged
  PNG are exported with every provisional marker. Source bytes are unmodified.
- **Android offline evidence export:** draft points are exported as a local
  `ACTION_CREATE_DOCUMENT` JSON file (no cloud or web dependency) using the
  explicit status `DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF`.
- **Review structures:** trypanosome nucleus, kinetoplast, undulating membrane,
  free flagellum and posterior endpoint; Leishmania *amastigote* boundary, nucleus,
  kinetoplast and host macrophage. **No promastigote label is mapped onto a
  source image depicting amastigotes only.**
- No new permissions, network access, WebView, HTML, JS or production academic
  content modifications.

## Same-commit acceptance gates

1. Lock exact SHA in draft PR and compare against U1.8. A5 JSON, release version
   and application ID must be unchanged.
2. Kotlin/JVM unit tests: letterbox rejection; correct inverse/forward
   geometry after zoom+pan; orientation-independent source pixel coordinates;
   fail-closed invalid dimensions/zoom.
3. Android API34 offline emulator at both normal and 200% font: **four**
   Unit I instrumentation tests, including actual native tap coordinate creation,
   48dp zoom controls, undo and export disabled after undo.
4. Inspect evidence `u12-100-u19-trypanosoma-calibration.png` and
   `u12-200-u19-trypanosoma-calibration.png`. Confirm image occupies a
   noncollapsed viewport, the draft marker is visible and Tamil controls are
   reachable by scrolling.
5. Independently test **release/non-debug build** with the review launchers
   absent, even when U1.7 reference figures are visible.
6. Actual handset: confirm touch coordinate stability after rotation, pinch,
   pan, 200% Tamil, on-device PNG SHA-256, TalkBack access, process recovery,
   export file and source image regeneration checksum.
7. An appointed zoology reviewer must verify biological identity/structure
   and plate/source licence before approving any endpoint. A second Tamil
   reviewer must check terms and spoken descriptions. **The exported JSON is
   evidence for review; it is NOT an approval.**

## Engineering and academic holds

- Native CI build, test result and screenshot artifacts remain **UNVERIFIED**
  until inspected on the exact U1.9 HEAD.
- **Original hand-pencil anatomical raster masters are still missing.**
  This tool cannot turn digital source illustrations into hand-drawn scans.
- No accepted organ hotspot coordinates, source-aligned leader lines, tactile
  organ-specific narration or final Unit I image-accreditation sign-off.
- Keep PR draft; do **not** merge to release or sign production APK/AAB.

## Academic source context

- https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html
- https://www.cdc.gov/dpdx/leishmaniasis/
