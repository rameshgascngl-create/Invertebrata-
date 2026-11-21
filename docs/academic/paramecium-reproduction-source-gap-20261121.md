# Paramecium reproduction: exact-source asset gap audit (21 November 2026)

**Baseline:** `31921529504b6ce33ce34d4ec47f4490cb25ea6b`. This audit makes no changes to the five-unit native application, 43 chapters, Tamil corrections, reader, or approved image bytes.

## Verified source facts
- `native-app/app/src/main/java/com/gasczoology/invertebratelab/data/ParameciumFissionPlateCatalogue.kt` lists only F01, F02 and F03, all named `*_PROVISIONAL.png`. Its declared stage IDs cover `mother` and `nuclei` only. **No F04/F05/F06 stage is represented in this catalogue**, although the documented scientific acceptance matrix requires F04 macronuclear division, F05 transverse furrow and F06 separated proter/opisthe.
- A source catalogue entry is not evidence that the corresponding PNG is packaged or shown in the Compose simulator. Do not report complete binary-fission coverage from three catalogue records.
- `docs/paramecium-reproduction-plate-acceptance-20261119.md` explicitly marks reproduction artwork **NOT APPROVED**.
- `docs/academic/paramecium-reproduction-png-acceptance-2026-10-30.md` rejects the eight-panel conjugation poster for nuclear-chronology and Tamil errors and the eight-stage fission poster for label/legibility errors.
- Historical U57 `d8a3f044` PNG zoom emulator success is not a current-head image or scientific acceptance result.

## Isolated next engineering change, conditional on scientific acceptance
1. Produce separate high-resolution raster PNG masters for F04–F06, with a documented reference, morphology review, rights/AI-origin declaration, SHA-256 and two distinct editorial sign-offs. Do not derive them from enlarged fuzzy source crops.
2. Produce the conjugation sequence as individual C01–C10 PNGs. The cited species-specific sequence places the first postzygotic synkaryon division before exconjugant separation. Do not depict mature new macronuclei at pair separation or confuse three haploid nuclei with a triploid nucleus.
3. Confirm correct bilingual scientific labels and leader-line endpoints on every native-resolution PNG before integrating. Preserve approved assets until a replacement passes.
4. Add actual asset-path-to-stage tests and inspect APK ZIP contents. Add full-screen 100%/200% Tamil screenshots and check visible pixel legibility; test existence alone is insufficient.
5. Run exact-commit JVM/foundation, five-unit compilation, API34 Tamil emulator acceptance and physical-device release checks independently. Preserve production hold until science, Tamil, provenance and device acceptance are recorded.

**Status:** Source gap documented; no newly approved artwork, no simulator integration, no APK release.
