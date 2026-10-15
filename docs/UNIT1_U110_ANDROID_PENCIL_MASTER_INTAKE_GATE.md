# U1.10 — Native Android pencil-master import and anatomical review gate

## Objective

Import a **real supplied** pencil drawing/scan of *Trypanosoma* or
*Leishmania* through Android's offline Storage Access Framework, preserving
original bytes and a reproducible SHA-256; inspect it at source pixel
coordinates in the U1.9 Kotlin calibration tool. This is an **input workflow**:
no authentic pencil scan has been independently supplied or approved in this
candidate branch. The application must not fabricate approval.

## Platform contract

1. Native **Kotlin + Jetpack Compose**, Android `OpenDocument` and internal
   `filesDir`. No HTML, JS, WebView, external browser, cloud TTS, Internet
   permission or new privileged storage permission.
2. Access restricted to `ApplicationInfo.FLAG_DEBUGGABLE`. Reviewer intake
   controls do not appear in a properly non-debuggable production build.
3. Only allowlisted organisms `trypanosoma`, `leishmania`; user-selected
   filenames are never used to construct paths.
4. Android resolver opens selected PNG/JPEG and copies original bytes
   immediately. Intake ignores provider-supplied MIME in favor of binary
   magic. Reject nonimage data, corrupt images, bytes outside 4KiB–12MiB,
   dimensions outside 600–5000 pixels each or over 10 megapixels.
5. Decode bounds first, then actual pixels, and require exact dimensions.
   Keep full original PNG/JPEG bytes (not compressed, cropped, "sharpened",
   stylized, recolored or regenerated). File is written to an internal temp
   path with `fd.sync()` and atomic rename.
6. Document bytes remain entirely app-private. Import only copies bytes, not
   access permissions or external provider URIs. SHA-256 is recomputed when
   loaded; a stored review candidate is **unverified**.
7. Attached reviewer uses `NativeRasterCalibration.hit/project` against
   **this imported decoded bitmap** and exports draft normalized/source
   coordinates keyed to the same image SHA-256. Never calibrate a scan using
   old `unit1/references/` hashes. Each changed source hash must reset
   draft marks.
8. User may preview, calibrate, export draft coordinate JSON or discard a
   candidate. JSON reports
   `DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF`. It is not a signed certificate.
9. **Never** promote an imported candidate into published student assets or
   accepted organ labels automatically. Provenance and zoological figure
   acceptance require explicit external academic review.

## Tests required before acceptance

- Static/source: Native Kotlin route, allowlisted scan storage, debuggable guard,
  OpenDocument UI, no WebView, unmodified A5 JSON and Android identifiers.
- JVM pure geometry tests carried over from U1.9; all model and lifecycle tests
  carried from U1.8.
- Exactly FIVE Unit I Android instrumentation methods on API 34 at 100% and
  200% system fonts, with network disabled. New test generates **synthetic PNG
  fixture only** to verify byte-for-byte private persistence, actual source
  SHA-256, preview and pixel tap. It rejects HTML disguised as an image and
  `../` path traversal. Fixture is always discarded in `finally`.
- Required `u12-100-u110-imported-review.png` and
  `u12-200-u110-imported-review.png` in the same exact-commit screenshot
  archive as preceding Unit I evidence. Inspect UI at 200% Tamil on real phone.
- Compile both `compileDebugKotlin` and `compileReleaseKotlin`, along with
  Android tests/JVM tests. A green build alone does not prove biology.
- Real device: OpenDocument picker, actual PNG and JPEG, bad file and size
  rejection, document provider cancellation, zoom/rotation, checksum export,
  persistence across activity restart and app-private deletion.
- Confirm **non-debuggable release APK** does not expose the reviewer,
  candidate image or debug controls; verify offline audio remains optional.

## Academic prerequisites for later student-visible anatomical hotspots

The initial actual pencil plate requires:

- Authorship/provenance; source photograph or scan; permission for educational
  reproduction; explicit original vs commissioned vs modified artwork status.
- Zoologist confirmation of life-cycle stage and taxon, true nucleus/
  kinetoplast and flagellar structures, orientation, biological labels and
  leader-line endpoints.
- Separate images for **Leishmania amastigote and promastigote**; a photo of
  intracellular amastigotes cannot be presented as a promastigote.
- Tamil editorial review plus audio pronunciation/transcript review.
- Final verified master raster hash and approved source-pixel marker set in a
  dedicated, reviewed student asset manifest, not a provisional JSON export.

## Current decision

SOURCE IMPLEMENTED on a draft branch; Kotlin/Android CI, screenshot inspection,
physical-device testing, actual original pencil scans and academic review
are **PENDING**. No claim of genuine pencil-plate integration or signed APK.

The prior generated *infographic/mockup* is not a native application asset,
not an approved scientific master, and not evidence of a GitHub build.
