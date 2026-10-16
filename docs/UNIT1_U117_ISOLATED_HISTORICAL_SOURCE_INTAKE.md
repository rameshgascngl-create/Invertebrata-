# U1.17 — Android-native isolated original-archive intake

## Source of truth
- U1.16 proposed engineering HEAD: `fda489701f3abf26c7ff1bc7a7d6e6e50354a357`.
- Published source: Chandler (1922), *Animal Parasites and Human Disease*, Fig. 14, after Leishman.
- Wikimedia Commons catalog: https://commons.wikimedia.org/wiki/File:Leishmania_donovani_(02).jpg
- Catalogued original: 314025 bytes, 1552 × 1584 pixels, SHA-1
  `3a08d99564510b548416ffb0bb4318eef68c1948`.
- This is a historical **PRINTED** plate, not an independently certified pencil master.
- F–O are cultured developmental forms, with differing stages; J–L are captioned flagellated culture forms. No individual panel has been independently approved as a promastigote anatomical reference.

## Correctness defect addressed
U1.16 loaded the historical file from
`NativePencilMasterStore.load(context, "leishmania")`,
the *same private slot* holding provisional user-supplied original pencil
drawings. Importing Chandler's printed archival file into that slot could
overwrite a genuine hand-drawn candidate, and crop review of the printed
page relied on a differently named and classified storage interface.

## U1.17 native implementation
- A separate `NativeHistoricalOriginalStore` now stores published archival
  source bytes under `filesDir/u117_verified_historical_sources/`.
- Accept **only** the exact original JPEG by native signature, catalogued
  byte count, decoded image dimensions and SHA-1. All mismatches fail
  **before** the existing archived file can be modified.
- A bounded, offline Android `ActivityResultContracts.OpenDocument` picker
  acquires the local original directly from Android Files. It copies bytes
  to app-private storage without Internet or a new Android permission.
- U1.16 native crop-review UI now reads **only** this archival store and
  never accesses the hand-pencil candidate store. The U1.16 real-pixel
  crop/PNG provenance export and status
  `DRAFT_FIGURE_CROP_NOT_ANATOMY_SIGNOFF` are unchanged.
- Debug build only; neither the picker nor the crop reviewer is visible
  in a production non-debug build.
- No modification to validated A5 lesson/question assets, Compose
  lifecycle simulators, digital teaching comparison, raster reference PNGs,
  app identifiers or release/signing workflows.

## Candidate acceptance and verification
1. Same-commit native debug and release compilation, JVM tests and API34
   offline UI suite must PASS **8/8** at both 100% and 200% Android font.
2. Existing original source-identity negative test must confirm that a
   synthetic PNG cannot pass archival verification or open crop exports.
3. New regression check stores that same PNG *as an independent provisional
   pencil candidate*, attempts archival acceptance, and proves it is rejected
   without altering the pencil candidate.
4. Actual Android screenshots `u117-unmatched-source-isolated.png` at both
   font scales must show the enabled archive file picker and fail-closed
   identity state.
5. **Positive original-file test remains OPEN** until the real, exact
   314025-byte JPEG is supplied to a phone/test fixture and its hash,
   source-pixel coordinate crop and PNG SHA-256 are checked.
6. Physical handset QA: Android Files picker, privacy, offline import and
   reload, 200% Tamil, zoom/pixel corner selection, SAF exports, storage
   reopening, rotation, process death and release debug-feature absence.
7. Human zoologist reviews actual source-pixel crops for A–O separately;
   independently confirm scientific morphology, caption associations,
   attribution, historical limits and Tamil explanation before educational
   figure adoption.

## Action for a reviewer with the original historical JPEG
Open the Commons catalog link above on the Android device, save **Original
file** unchanged (do not screenshot, re-encode, compress, rotate, crop, or
send through an image-processing messenger), then in the debug APK choose
**Choose verified historical original (JPEG)**. The app checks the full
source identity before enabling an archival crop. A matching SHA-1 confirms
the published **file's identity**, never the accuracy of a specific label
or authenticity of a modern pencil drawing.

## Final HOLD
No accepted source-derived anatomical PNG or verified organ hotspot is
being inserted into the student-facing atlas by U1.17. No source file was
fabricated or presented as a verified original. If original bytes cannot
be acquired, the crop-review state remains locked. Do not merge into a
production release or publish to Google Play/Indus on this engineering
checkpoint alone.
