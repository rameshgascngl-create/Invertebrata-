# U3.4 — Source-locked anatomical endpoint reconciliation (native Android)

**Status: ENGINEERING CANDIDATE. NOT an anatomical sign-off or release.**

## Existing verified engineering baseline

- U3.3 exact HEAD `f8b8b58331a5ffa73ec4e66db482fda166c51a8a`.
- Actions run `38888251403`: native Kotlin/JVM PASS, Android API34 in airplane mode PASS, 6 Unit III and 4 Unit II tests at **100% and 200%** font each. Source evidence ZIP and original pixel-review screenshots captured.
- Six Fasciola and Ascaris source-backed rasters remain **ACADEMICALLY UNVERIFIED**, with no approved organ leader-line endpoints, no student image taps, no authenticated original pencil scans and no verified physical handset test.

## U3.4 implementation

`UnitIIIEndpointAgreement.kt`: Pure Kotlin, deterministic comparison of **two independently obtained unapproved draft coordinates**, constrained to the *same* syllabus plate ID, **byte-identical packaged source PNG SHA-256** and decoded image dimensions. For each organ label:
- WITHIN_PIXEL_TOLERANCE — Euclidean source-pixel distance ≤8px (default). This is only geometric agreement.
- REQUIRES_ANATOMY_REVIEW — both drafts name the organ but their exact original pixel positions differ by more than 8px.
- ONLY_IN_FIRST_DRAFT or ONLY_IN_SECOND_DRAFT — evidence is missing in one file.
- Invalid or excessively permissive tolerances, mismatched images or dissimilar organism stages **fail closed**.

`UnitIIIDraftComparisonIntake.kt`: Android local JSON reader accepting only U3.3 `invertebrata.u33.source-pixel-review.v1` documents marked `DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF`. Enforces 64KiB input bound and existing strict plate/organ/pixel-validation model. No alternate image is downloaded, imported as a learner asset or trusted as peer-reviewed.

`UnitIIIPixelReviewDialog.kt`: A **debug-build-only** Compare button in the existing anatomy reviewer opens the second local draft using native Android Files (Storage Access Framework, offline, no INTERNET permission), then displays per-organ source-pixel difference and missing-organ status. Failed source identity or unsupported JSON produces a rejection, rather than silently choosing a nearest organ. Changing original candidate marks invalidates the old comparison.

**Important limitation:** a file picker and matching coordinates cannot establish that drafts came from two distinct human reviewers. Importing the *same file twice* could produce zero distance. Therefore `approvedForStudentHotspots` is always **false**, even when 100% of coordinates match. Human identity, specific subfigure, morphological correctness, accurate Tamil wording and artwork provenance still require independent authentication and reviewer signatures outside the app.

## Evidence, verification, and stopping conditions

1. Compile native debug, release, instrumentation and JVM on the exact U3.4 SHA; execute the `UnitIIIEndpointAgreementTest` cases for matching, mismatching, absent organ labels, wrong plate, changed SHA and invalid distances.
2. Preserve Unit III **6/6** and Unit II **4/4** Android tests at **100% and 200%** font with no mobile data/Wi-Fi. New tests confirm Compare button disabled with zero draft points and enabled with a valid SHA-bound pixel selection; Android parser rejects a spoofed approval label or cross-plate organ.
3. Evidence artifact must contain `unit3-{100,200}-fasciola-compare-ready.png` and `unit3-{100,200}-ascaris-compare-ready.png`, plus previously required images. Inspect real 200% Tamil controls and image viewport ≥230dp.
4. On a physical phone, create **two genuinely independent files** for the exact same source image and with a human-verified reviewer chain, import the second file, inspect discrepancy report, compare at zoom 1×/2×/4× and verify rejection of wrong/edited PNG or other plate. A UI test alone is not a real Android Files handoff.
5. Qualified zoologists must inspect *actual source panels* and organ leader points, resolve disagreement, then separately provide author names/date/signatures and scientific/rights/Tamil decisions. Even a final reviewed draft is **not automatically an app release endpoint manifest**.
6. No Unit I/II content, original raster bytes, lesson or validated A5 source, Android package/permissions, student hotspot approvals or production signing is altered by U3.4.

**Gate outcome remains:** engineering PASS/PENDING depending on CI; anatomical approval HOLD; real-device acceptance HOLD; Google Play/Indus production HOLD.
