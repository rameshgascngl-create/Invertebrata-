# INVERTEBRATA — U1.14 native scan orientation and mobile QA

**Source branch:** `native/u114-scan-orientation-mobile-qa-20261016`  
**Base:** `native/u113-pencil-intake-unification-20261016` (draft PR #27)

## Why U1.14 is necessary

The U1.13 native reviewer preserves original PNG/JPEG bytes, uses SHA-256
and records image-based anatomy coordinates. Yet Android `BitmapFactory`
does not automatically apply all JPEG EXIF rotation and mirror instructions.
If an external camera wrote the original image sideways and encoded display
orientation only in EXIF, a naïve coordinate reviewer could place an organ
endpoint against pixels that do not correspond to the author's display
orientation. This cannot be academically accepted.

The earlier importer was also embedded inside a very tall organism figure
card. At enlarged fonts and with an imported image preview, its controls
could compete with the normal attributed zoology figure and make scrolling
and testing less deterministic.

## Kotlin-only corrective implementation

1. `NativePencilMasterStore` checks the *actual imported JPEG bytes* with
   platform-native `android.media.ExifInterface(InputStream)`, supported
   from API 24 (the project's minSdk). JPEG orientation may be absent or
   `ORIENTATION_NORMAL`; all explicit rotation and horizontal/vertical
   mirroring values are rejected. EXIF parser failures are rejected rather
   than silently assumed normal. PNG raw source pixels remain unchanged.
2. The rejection is shown in Tamil or English. The Tamil error explicitly
   instructs the reviewer to export the scan at its correct physical
   orientation as PNG/JPEG before importing again.
3. `UnitIOriginalPencilMasterIntake` now has an independent Compose
   `LazyColumn` item after each organism's original attributed figure.
   Normal student figures, image credits, source zoom and the other eight
   permanent textbook subsections remain separate and unchanged.
4. All import and pixel-adjustment UIs remain locked behind Android
   `ApplicationInfo.FLAG_DEBUGGABLE`; there is no release admission of
   draft candidate labels, unverified touch hotspots or original-photo
   narrator audio. No permission or network access is added.
5. Original private-file bytes, SHA-256, bounded dimensions and memory
   protections, stage-unknown morphotype, disabled audio, image-first
   source review and one-pixel source-coordinate refinement are preserved.

## Automated acceptance gates

- Run `compileDebugKotlin`, `compileReleaseKotlin`,
  `compileDebugAndroidTestKotlin` and `testDebugUnitTest` at the **same
  exact candidate commit**.
- API 34 Android emulator **offline**: 5/5 Unit I tests at 100% system fonts
  and 5/5 at 200%, without disabling prior semantic checks.
- In the existing fifth native Android test, generate a **synthetic**
  900px PNG only as an engineering test fixture. Confirm complete SHA-256
  and byte-for-byte storage round-trip, invalid HTML and path rejection.
- Derive a JPEG test fixture, save `ORIENTATION_ROTATE_90` with Android
  EXIF, verify `sourceOrientationAccepted("jpeg", data) == false`, confirm
  `importBytes` rejects it, and confirm the previously imported PNG
  remains unchanged.
- Navigate to the dedicated `u110-native-plate-intake-trypanosoma` item
  directly at 200% and check that its visible Android Files button and
  preview remain scrollable.
- Review existing exact-commit screenshot artifacts, including the
  imported scan, source-image-first, coordinate-refinement, full-screen
  plates, and English/Tamil lifecycle. Capture additional evidence if
  the new layout materially changes the viewport.
- Run release/nondebuggable instrumentation separately for absence of
  the importer and reviewer. **Release compile alone is not this check.**

## Real Android phone and academic review

- Verify genuine phone-camera JPEGs with EXIF rotations 3/6/8 and mirrored
  2/4/5/7; test normalized local PNG/JPEG import, screen rotation, Tamil
  at 200%, TalkBack, Back handling and offline Files providers.
- Compare draft coordinates to real source pixels after every zoom/pan;
  report missing or incorrect nuclei/kinetoplast/morphotype images.
- Actual original hand-drawn graphite pencil plates are **not part of
  this commit** and do not become accepted via the importer. Distinct
  *Leishmania* amastigote and promastigote images need independent
  zoologist, source-rights and Tamil editorial approval.

## Status policy

Source committed does not mean Kotlin compile or API34 PASS. GitHub's
currently available connector only returns pull-request-triggered runs
for `fetch_commit_workflow_runs` and does not confirm the push-triggered
run; no CI result or signed APK can be inferred from an empty response.

**Decision: Engineering QA PENDING; Academic HOLD; Release HOLD.**
