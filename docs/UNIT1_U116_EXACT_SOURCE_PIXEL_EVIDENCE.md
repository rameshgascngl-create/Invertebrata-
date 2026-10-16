# U1.16 — independently inspect real source raster pixels (native Android)

**Base:** U1.15 `73c37c12835e5434cba6ff96ceca981e96180980`, engineering-accepted
([Actions 38853424360](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38853424360);
API 34 7/7 at 100% and 7/7 at 200%). The actual U1.15 **Canvas** figures
remain digital teaching sketches and NOT approved scanned pencil anatomy.

## Scientific bottleneck

Zooming and moving a mark do not demonstrate that the marked structure
is present in, or corresponds to, the actual underlying image. Existing
U1.9–U1.14 review UI already mapped touch and leader endpoints into
actual source pixels with original SHA-256. U1.16 extends that existing
tool; it does **not** duplicate/reinvent coordinate transforms.

## What is genuinely implemented

- Native Kotlin/Jetpack Compose, debug-build-only reviewer with a bounded,
  actual **Bitmap.createBitmap** crop from the *same decoded source* that
  the researcher taps. A clear provisional ring shows the exact source
  pixel, even after zoom/pan/rotation; crop coordinates are independent of
  screen density, display orientation, or font scaling.
- Raster crop window maximum 257×257 pixels; no high-resolution image
  is re-encoded or resampled to fabricate anatomical structure. Exact
  crop-left, crop-top, width and height are included in JSON per mark.
- Exact original packaged reference or imported private source image
  **SHA-256** binds evidence. Wrong/missing/nonlowercase/non-64-byte
  SHA prohibits draft export. Export never becomes academic acceptance.
- Reviewer can choose one of four **unapproved** states:
  `NOT_REVIEWED`, `VISIBLE_CANDIDATE`, `CANNOT_RESOLVE`,
  `WRONG_STRUCTURE`. No `APPROVED`, `RELEASED`,
  or `SCIENTIFICALLY_VERIFIED` state can be selected in this UI.
- Add a human inspection note (up to 400 characters). All notes, crop
  window and finding are recorded alongside the exact candidate
  pixel coordinate, organism, original source identity and morphotype.
- Moving the leader endpoint by a single pixel invalidates the prior
  finding and note to prevent attaching an outdated observation to a
  different source pixel.
- The draft evidence export explicitly fixes
  `independent_scientific_signoff=false`,
  `student_hotspots_enabled=false` and
  `status=DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF`.
  No automated test or self-entered inspection note can override those
  fields.
- Imported scans of unknown stage still carry
  `UNVERIFIED_IMPORTED_STAGE_REQUIRES_SCIENTIFIC_REVIEW`, with
  organ-specific voice disabled, and cannot be automatically treated
  as an authenticated species/stage.
- The **source bitmap** and original student-facing anatomy/reference
  materials, A5 lessons and Android package/version remain unchanged.
  No HTML, JS, WebView, cloud inference, Internet, or release signing.

## Acceptance gates

1. Verify the exact SHA and diff against U1.15; no anatomical images
   or curriculum edits.
2. JVM test `NativeSourcePixelEvidenceTest`: correct source-pixel
   crop origin/focus, center & edge, source-image boundary rejection,
   NaN rejection, bounded max size, hash validation, no approved status,
   reviewer note bound.
3. Compile debug/release Kotlin, Android instrumentation and all JVM tests
   for **the same exact commit**; no acceptance threshold relaxation.
4. Android API 34 offline 7/7 tests at both **100%** and **200%** font.
   The existing raster review test must locate the crop, verify SHA
   evidence display, use the horizontally accessible provisional
   finding picker and confirm a visible message that no finding
   confers scientific approval.
5. Capture `u12-100-u116-true-source-pixel-crop.png` and
   `u12-200-u116-true-source-pixel-crop.png`, inspect that the
   crop visually matches the whole-source region and its ring
   is placed on the same source pixel. A green CI status alone
   cannot establish biological correctness.
6. Physical Android device: test 200% Tamil, rotation, pan,
   pinch, new mark, pixel nudges, draft state invalidation,
   offline TTS unavailability, TalkBack, source hash, JSON export
   and intended reviewer-only build restriction.

## Still mandatory before academically approved hotspots

1. Obtain a **genuine source-licensed scanned pencil plate**. No such
   approved master has been retrieved or committed for the relevant
   Trypanosoma/Leishmania forms.
2. Independently identify *T. brucei* bloodstream trypomastigote,
   *Leishmania* intracellular amastigote, and sand-fly promastigote
   on **different scientifically suitable original images**.
   The existing amastigote raster cannot establish promastigote organs.
3. Two independent zoological reviewers must examine each original
   full plate and pixel crop, nucleus/kinetoplast positions, cell
   boundaries and organ leader endpoints. Record reviewer, date,
   stage, taxon, original image checksum, finding, unresolved/absence
   and revision notes in an external signed review record.
   The app's provisional findings are **evidence**, not
   proof of reviewer identity or academic approval.
4. Faculty Tamil editorial review, source permissions and physical
   Android device QA must pass. Only **after** separate accepted
   manifests and verified assets may student organ-touch narration
   be considered. These changes are **not** implemented by U1.16.

**Status on branch creation: SOURCE IMPLEMENTED; engineering CI pending;
academic pencil-masters, real-handset QA and production release HOLD.**
