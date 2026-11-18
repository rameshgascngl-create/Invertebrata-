# U5.3 Nereis native image-only correction — source implemented, eight binary plates pending

**Branch:** `native/u53-nereis-eight-static-png-masters-20261017` (isolated from latest successful U4.8 Nereis engineering gate, `ff4b0b2069e4dc2b2e7d7e1b1510dbe3911c0b32`).

## Student-facing implementation

- The Nereis chapter keeps the fourteen bilingual biology sections and original Unit IV A5 assessment.
- `UnitIVNereisStaticPlate` now delegates to `UnitIVNereisRasterPlate`, which loads **eight named offline PNG images** from Android `assets/nereis/`.
- Removed all eight obsolete Compose Canvas sketches and the external VectorDrawable substitute from the student-facing plate composable. There are no anatomical hotspots, gestures, organ-selection buttons or tap-to-highlight controls on Nereis drawings.
- Full-size 1122×1402 images are rendered at natural proportions using `ContentScale.Fit`; real bilingual Compose text survives outside the raster for screen readers.
- On missing/unreadable raster, a visible asset defect is shown; student users are not misled by generated fallback anatomy.

## Exactly required APK image assets

All eight are **generated pencil-style draft** graphics, not photographs, authentic scanned pencil drawings, or academically accepted anatomy. The locally prepared integration ZIP uses this exact relative folder:

`native-app/app/src/main/assets/nereis/`

1. `01_external_morphology.png`
2. `02_enlarged_head.png`
3. `03_biramous_parapodium.png`
4. `04_digestive_system.png`
5. `05_circulatory_system.png`
6. `06_nervous_system.png`
7. `07_excretory_system.png`
8. `08_trochophore_larva.png`

**Critical blocker:** the execution environment cannot upload binary PNG bytes to this GitHub connection. The Kotlin/image loader source and `.github/scripts/verify-nereis-png-masters.py` are committed, but **the eight binary files are NOT on this branch**. The integrity script deliberately fails until all eight real images are committed with exactly the bytes/dimensions declared. Do not claim a fresh APK containing these eight images until GitHub receives all eight and exact-commit CI passes.

## Next actions before any completion claim

1. Add all eight actual 1122×1402 PNG files, with exact names, to the stated branch and directory. Run `python3 .github/scripts/verify-nereis-png-masters.py` in GitHub Actions. NO bypasses, silent substitute artwork, weakened assertions, or fake green build.
2. Check source compilation, JVM and API34 instrumentation at 100% and 200% Tamil font scaling and offline; inspect screenshots of **all eight actual bitmaps**, not just the existence of Compose containers.
3. Perform independent zoology diagram review (leader endpoints and taxon-specific structures). The generated head and alimentary/circulatory/nephridial plates may contain anatomical misunderstandings; source art should be corrected when necessary, not relabelled over an anatomically defective image.
4. Match Tamil terminology to the *actual college practical record/manual*; the user-provided diagrams currently have English labels, not a verified bilingual college terminology list.
5. Maintain ACADEMIC ARTWORK HOLD and physical-device-release gate. Do not publish Google Play/Indus AAB before both pass.

**Lesson quality and engineering compilation are necessary but not sufficient for scientific atlas acceptance.**
