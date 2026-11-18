# U1.13 — Reconcile native pencil-scan importer and image-first anatomy review

**Branch:** `native/u113-pencil-intake-unification-20261016`
**Base:** `native/u112-pixel-endpoint-review-20261016` (draft PR #26)

## Why this is necessary

The real-scan import work in PR #22 (`native/u110-pencil-master-review-import-20261015`)
was developed **on a sibling branch** from the morphotype/voice and U1.11/U1.12
reviewer improvements. It was not present in U1.12. Advancing again without
reconciling these two native Android developments would discard required
features and misrepresent progress.

U1.13 brings the original scan intake/preview/store into the current U1.12
ancestry and resolves the reviewer conflict so that one source-pixel reader
handles the existing attributed raster AND a reviewer-supplied local scan,
each with its **own SHA-256 and provenance**.

## Platform requirements

- **100% Kotlin/Android/Jetpack Compose**; no HTML, JS, WebView, SVG anatomical
  substitution, web downloads, network permissions, external storage permissions
  or cloud dependencies.
- `NativePencilMasterStore`: offline Android `OpenDocument` stream, binary
  PNG/JPEG signature, max 12MiB and 10MP, exact full-size source pixel decode,
  allowlisted organisms, private app file and atomic write, SHA-256 identity,
  reload after Activity recreation and discard.
- `UnitIOriginalPencilMasterIntake`: Android Files picker, image preview
  decoded off UI thread, bilingual error/draft labels, local deletion,
  and debug-only launcher on both organism lessons.
- `NativeImportedPlateReviewer`: reuse the current **image-first** native
  pixel-review dialog and U1.12 one-pixel adjustments, rather than falling
  back to the obsolete U1.9 reviewer.
- **Source identity:** reference image exports use
  `unit1/references/<organism>.png` and checksum of that bundled image.
  Local scan exports use `app-private:u110_unverified_pencil_candidates/`
  and checksum of the exact imported byte stream. Mixing these constitutes
  a FAIL and MUST NOT be hidden by a CI PASS.
- The imported candidate uses morphotype
  `UNVERIFIED_IMPORTED_STAGE_REQUIRES_SCIENTIFIC_REVIEW`. The preselected
  anatomy labels are **review prompts only**, not confirmed observations;
  organ-linked Android TTS is **disabled** for that unknown morphotype.
  The original attributed reference keeps its existing identified
  morphotype-specific reviewer narration, still debug-only and unapproved.
- Last-draft marker and zoom state reset on source SHA change. The private
  image must remain visually accessible before long text at 200% font.
- No automatic migration of imported scan into published student resources,
  and no automatic approval even if the image resembles hand pencil art.

## Same-commit engineering gate

1. Review PR diff to confirm only reconciled Kotlin UI/storage, Android
   instrumentation, exact-commit CI and audit documents changed.
2. Run **compileDebugKotlin**, **compileReleaseKotlin**,
   **compileDebugAndroidTestKotlin** and the existing **testDebugUnitTest**
   on this exact SHA. Include native U1.12 coordinate invariants.
3. API34 emulator in airplane mode: **5/5** instrumentation methods PASS at
   **100% and 200%** Android system font scale.
4. The fifth test must supply a synthetically generated PNG **strictly as a
   test fixture**, not an anatomy asset, and verify original-bytes preservation,
   exact SHA-256, invalid file/path rejection, preview, imported image dialog,
   `UNVERIFIED_IMPORTED_STAGE` warning, disabled organ-specific voice and
   pixel touch. The fixture must be discarded.
5. Inspect screenshots `u12-100-u110-imported-review.png`,
   `u12-200-u110-imported-review.png`, plus preserved
   `u111-trypanosoma-image-first` and
   `u112-trypanosoma-pixel-refinement` evidence. The real bitmap must not
   collapse under Tamil enlarged fonts; labels are not authoritative.
6. On physical Android handset, verify PNG/JPEG picker, external provider
   cancellation, invalid and oversized images, rapid switching, zoom/pan,
   source-origin pixel accuracy, Back/rotation, TalkBack, offline voice absence,
   persistence, and removal. Confirm all intake/review controls are absent
   from a non-debuggable release build.
7. Keep the 43 original A5 exam JSONs, Unit I curriculum and source reference
   assets unchanged. Do not sign APK/AAB or promote to production.

## Independent academic-artwork gate (HOLD)

**No true pencil scan has been supplied or independently verified.** The
synthetic instrumentation fixture is not teaching art. Before student-visible
hotspots, require a real source-licensed scanned pencil master, documented
provenance, correct organism and life stage, zoologist-approved anatomy
leaders and pixel coordinates, Tamil editorial signoff, speech review,
and handset/device QA. Leishmania amastigote and promastigote need
different scientifically reviewed plates. Source-level changes do not
constitute biological approval.

CI for U1.11/U1.12 is not verifiable via the available commit-status
endpoint; U1.13 remains **engineering PENDING** until exact workflow run
and screenshot artifacts are independently inspected.
