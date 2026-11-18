# INVERTEBRATA U1.10 — native morphotype-constrained anatomy review (DRAFT)

## Non-negotiable Android scope
All user-facing rendering remains **Kotlin + Jetpack Compose** and source-reference art remains genuine decoded PNG. `OfflineNarrator` uses Android's installed non-network voices only. **No HTML, WebView, JS, SVG anatomy, or network permission.**

## What has actually been implemented

1. The two existing, correctly attributed raster **reference illustrations** keep their SHA-256 evidence and zoom/pan pixel-calibration path (U1.9).
2. A pure Kotlin `UnitIAnatomyReviewDescriptions` catalog now identifies:
   - **T. brucei bloodstream trypomastigote**: nucleus, kinetoplast, undulating membrane, free anterior flagellum, posterior endpoint.
   - **Leishmania intracellular amastigote**: amastigote outline, *parasite* nucleus, kinetoplast, and host macrophage (not a parasite organ).
   - **Separate sand-fly promastigote**: NOT represented by the currently packaged amastigote bitmap. Therefore there is **no** promastigote marker, narrated hotspot, or anatomical figure pretending to show it.
3. A **debug-build-only** native anatomical reviewer exposes selectable English–Tamil organ descriptions and explicit **Speak selected structure / Stop voice** actions. Audio is activated by the reviewer, never automatically by an unapproved student hotspot. Missing offline voices must report unavailable, never switch to cloud.
4. Draft marker export preserves original source pixel coordinates, decoded dimensions, SHA-256 and **new morphotype metadata**. Status remains `DRAFT_UNVERIFIED_NO_ANATOMY_SIGNOFF`, and the schema is `invertebrata.u110.morphotype-coordinate-draft.v1`.
5. Kotlin JVM tests reject false stage-organ mappings. Android API34 instrumentation and screenshot gate now require the source review UI at normal and 200% Tamil device font setting.

## Independent sign-off required before student-facing anatomical activation

- **Original scanned pencil masters** are **NOT AVAILABLE** for these two forms and must not be replaced by a color SVG conversion described as a real scan.
- A separately reviewed **Leishmania promastigote** pencil master is needed for that anatomical stage. Existing amastigote PNG cannot be reused.
- At least two independent zoology reviewers must identify visible organ locations in each original raster and audit leader endpoints; each label needs source-pixel geometry proof.
- Confirm rights and display proper original authorship and credit for every incorporated raster. CDC descriptions are scientific references, not automatic licenses for any unrelated artwork.
- A separate Tamil language reviewer checks terminology, explanatory scripts, and voice reading on a real Android device.
- After these steps, only a **new explicit release change** may map approved organ hotspots to real PNG coordinates and invoke native offline TTS on a student's touch. This debug QA interface is **not** an authorised release implementation.

## Reproducible validation

- Baseline depends on U1.9a regression correction (stage navigation, Reset and Play in oversized Tamil). Do not merge U1.10 ahead of U1.9a QA PASS.
- Exact HEAD: compileDebugKotlin, compileReleaseKotlin, compileDebugAndroidTestKotlin, testDebugUnitTest.
- Same-commit API34 offline screenshot suite: four original reference+teaching tests at **100% and 200%**; XML must say **4 tests, 0 failures** at both sizes.
- Must capture `u12-100-u110-trypanosoma-source-review.png` and `u12-200-u110-trypanosoma-source-review.png`, and verify actual labels, controls and a visible bitmap.
- Check no modification to `native-app/app/src/main/assets/a5/*.json`, existing PNG source bytes or app version identifiers.
- Physical device QA: actual tap/zoom/pan; TalkBack; sound off/unavailable; 200% Tamil; screen rotation; process death; local draft export; and no review launch control in **non-debug release**.

## Scientific references
- CDC DPDx African trypanosomiasis: https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html
- CDC DPDx leishmaniasis: https://www.cdc.gov/dpdx/leishmaniasis/

**Gate: Engineering candidate only. Anatomy, original pencil scan, independently reviewed hotspots, human-device testing and production release remain OPEN.**
