# INVERTEBRATA — U3.6, six-slot original pencil candidate intake (Android only)

**Engineering candidate. No scanned master has yet been scientifically authenticated.**

## Why this phase
U3.5 exact SHA `e2a7f9cdaa582a6b8a39f4479c8e04c598fcb022`, Actions
`38894959814`: native Kotlin + JVM PASS, API34 airplane-mode Unit III
6/6 and Unit II 4/4 at both 100% and 200% font PASS.
Existing six packaged reference raster artworks remain **unverified** as
authentic scanned pencil atlas masters. Geometric accuracy of a mark is not
proof of correct anatomy; a grayscale facsimile is not an original drawing.

## U3.6 deliverables
1. `UnitIIIPencilScanCandidateStore`: native local storage with SIX distinct
   fixed curriculum plate slots (three Fasciola, three Ascaris). The path is
   derived only from `UnitIIIPlateCatalog`, never from a supplied filename.
   It uses a new, isolated app-private directory, without modifying any
   packaged `type_studies/...` image, the Unit I storage, or student sources.
2. Accept bounded PNG/JPEG bytes from Android Files using
   `OpenDocument` (4KiB–12MiB, 600–5000px per dimension, ≤10MP). Check
   PNG/JPEG magic bytes, correct JPEG EXIF orientation, readable pixels,
   decoded dimensions and byte-exact SHA-256. Reject invalid input **before**
   writing. Atomic staged commit and revalidation on reload; offline only.
3. `UnitIIIPencilScanIntake`: DEBUG-ONLY Kotlin/Jetpack Compose image-first
   inspection dialog with a memory-bounded preview and bilingual warnings.
   Allows choosing, viewing and discarding a candidate without replacing
   the packaged source. Clearly states that SHA-256 authenticates only
   file bytes, not actual hand-pencil authorship or correct organ morphology.
4. Native `CreateDocument(application/json)` exports a provenance
   *candidate* manifest: six-plate ID, original source SHA/dimensions/format,
   `UNVERIFIED_USER_SUPPLIED_SOURCE`, rights and independent review pending,
   and `student_atlas_integration_authorized=false`.
5. No student-touch coordinates, approved leader lines, image source swaps,
   release version identifiers, A5 academic content or online dependencies.

## Strict acceptance
- Validate the exact branch HEAD, compile debug/release + instrumented Kotlin,
  run all JVM tests, preserve Unit II 4/4 at 100% & 200%.
- Unit III instrumented tests expand from 6 to **8** at both font scales.
  One test uses a *packaged* image **strictly as synthetic engineering test
  input** (NOT a genuine pencil-scan proof), checks import/load/invalid
  replacement/source asset immutability and cleanup. The other checks the
  empty Tamil Ascaris intake, no spurious accepted artwork, picker readiness,
  and disabled unapproved learner hotspots.
- Check real screenshot files `unit3-{100,200}-fasciola-pencil-intake-provisional.png`
  and `unit3-{100,200}-ascaris-pencil-intake-pending.png`.
  Visually inspect 200% Tamil, close/picker controls, screen inset and
  bitmap viewport; engineering green alone is not academic acceptance.
- Physical-device inspection still REQUIRED: select an actual hand-drawn scan
  in Android Files, verify file hash after app restart, rotation, document
  selection cancellation, zero network, memory, TalkBack and actual 200%
  Tamil rendering and pronunciation (where narrated).
- Independent zoologists must confirm the source creator, rights, **species
  and stage**, entire organ anatomy, source-panel integrity and Tamil
  labels. Source-pixel leaders in U3.3/U3.4 must be reverified for the
  *new image SHA* after any master candidate approval. Never carry old
  leader coordinates onto a new drawing.

## Academic and release decision
No authentic source-scan files have been supplied or integrated as approved
student atlas artwork. This intake **does not** automatically identify,
redraw, clean, sharpen, or certify pencil scans. Every imported candidate
is held under an independent review process. Unit II and Unit I academic
gates remain separately open. Google Play and Indus Appstore release: HOLD.
