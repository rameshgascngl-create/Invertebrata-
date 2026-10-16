# U3.4 — Exact original-pixel anatomical evidence, strictly native Android

## Proven engineering baseline

U3.3 commit `f8b8b58331a5ffa73ec4e66db482fda166c51a8a`, GitHub
Actions `38888251403`. Independently parsed original evidence archive:
- Unit III 6/6 pass at 100% font and 6/6 at 200%.
- Unit II 4/4 pass at 100% font and 4/4 at 200%.
- `status.txt` records the exact SHA, PASS and exit 0.

**But** the U3.3 screenshot of the *Fasciola* historical multiple-panel
raster has a draft marker labeled "Oral sucker" at `x=480,y=640`.
This is near the centre of the composite image, not independently
established as its oral sucker. Source-bound coordinates alone cannot
demonstrate the organ is correctly identified.

## Native-only U3.4 implementation

1. Retain the existing U3.3 **debug-only** 1×–4× full-size native
   Kotlin/Jetpack Compose bitmap reviewer; no source/image replacements.
2. Add a SECONDARY full-screen review dialog that crops a bounded
   **actual Bitmap.createBitmap window from the identical decoded
   original** (default 181×181 pixels). The original source coordinate
   of the selected marker is drawn on the crop; the window's exact
   left/top/width/height and full packaged PNG SHA-256 are shown.
3. Crop validity is fail-closed on image bounds, NaN, infinity, unknown
   plates/organ IDs and oversized radii. No edge-clamped phantom point.
4. For each provisional organ point the researcher may record
   `NOT_REVIEWED`, `VISIBLE_CANDIDATE`, `CANNOT_RESOLVE`,
   `WRONG_STRUCTURE`, and an optional note up to 400 characters.
   **There is no academic APPROVED state in the app.**
5. Draft JSON export records source/crop bounds, visual finding, note,
   exact source-image SHA-256, plate asset stem, original pixel X/Y,
   `scientific_anatomy_approved=false`,
   `learner_hotspots_enabled=false`, and the existing
   `DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF` status.
6. All screenshot review controls remain Android **debug-only**; in
   production the 6 plate coordinates still have no approved source
   image mapping. `UnitIIIPlateHotspots.coordinateApprovedStems`
   remains empty; **no** organ touch, leader-line endpoint or
   organ-specific audio is activated for learners.
7. Existing full-size raster, curriculum, bilingual lessons,
   assessments, application identifiers, permissions and package
   assets are unchanged; no HTML/WebView, SVG imitation or
   image generation is introduced.

## Non-bypassable engineering tests

- Kotlin/JVM `UnitIIIOriginalPixelEvidenceTest`: exact source pixel
  window, edge crop, invalid points, bounded notes, no "APPROVED"
  enum and all six curriculum scopes remaining draft-only.
- Exact candidate Gradle debug/JVM/AndroidTest compilation.
- API34 airplane-mode automated tests: **Unit III 6/6 and Unit II
  4/4**, at both 100% and 200% system fonts. Keep all existing
  image-first, zoom, anatomical hotspot disable and Tamil assertions.
- Source review test explicitly opens crop for *Fasciola external* in
  English and *Ascaris life-cycle* in Tamil, checks image display,
  SHA, and provisional reviewer status.
- Require screenshot evidence:
  `unit3-100-fasciola-actual-pixel-crop.png`,
  `unit3-200-fasciola-actual-pixel-crop.png`,
  `unit3-100-ascaris-actual-pixel-crop.png`,
  `unit3-200-ascaris-actual-pixel-crop.png`.
- Inspect actual screenshot crop: corresponding pixel region MUST
  visibly match the correct original panel. A green Android test is
  **not** zoologist accreditation.

## Mandatory scientific and device signoff still OPEN

For each of the six composite teaching rasters: independently establish
the historical figure's organism, life-cycle stage, printed
subfigure/panel, allowed figure reproduction rights, and anatomical
identification. Review source bitmap against the detailed labels:
*Fasciola* oral sucker vs genital pore/ventral sucker/intestinal
caeca and *Ascaris* female reproductive coils vs intestine, oral
lips and cloacal spicules. Two zoologists must independently inspect
and sign any proposed organ-to-original-pixel leader endpoint.
The debug reviewer cannot certify their identities or replace a
signed external review. Tamil editorial review, audible offline TTS,
TalkBack and physical handset/rotation/back/camera QA remain
separate holds.

**No production merge, release signing, AAB/APK publication or
student hotspot activation is authorized by U3.4.**
