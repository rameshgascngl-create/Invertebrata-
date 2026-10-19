# U5.8 Android screenshot/XML evidence inspection — 19 October 2026

## Provenance and exact build

- Workflow: https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/39245956382
- Commit: `bd94af0705acb4884d2777624fb294fca797fded` (do not confuse with later documentation-only commits).
- Downloaded artifact: `unit1-u58-context-png-offline-evidence` (GitHub artifact 12182744338); ZIP contains 62 entries.
- `status.txt`: `result=PASS`, `exit=0` and exact commit SHA.
- Both GitHub jobs (`build-and-jvm`, `emulator`) completed successfully. Four Android JUnit XML files, one U57 and one U58 test at each of font scales 1.0 and 2.0, each report 1 test, 0 failures, 0 errors, 0 skipped.

## Screenshot audit — observed, not inferred

- 24 PNG screenshot files, but **12 distinct screenshot bytes**: each of six U57 and six U58 screenshots was duplicated under the corresponding `u57-qa` / `u58-qa` capture subfolder. These are **evidence-archive duplicates**, not proof of duplicated images on a student page.
- U58: six unique 1080 × 2400 screenshots: external anatomy, water-balance concept and an unrelated theory lesson at 100% and 200% Tamil font scale.
- External anatomy and vacuole concept screenshots visibly depict the **same overall Paramecium pencil plate**, although with different viewport/crop and displayed scale. The vacuole plate includes a contractile-vacuole title and labels, but it is not an isolated mechanism diagram. This is **concept-level reuse** and must be evaluated for pedagogical suitability; it is not evidence of three identical images on one page.
- The unrelated lesson screenshot shows Tamil explanatory text without an anatomical image, consistent with no overview illustration being injected into that section.
- At 200% Tamil font size the visible prose wraps into tall lines, but the displayed portion is readable. The screenshots do **not** cover the entire scrollable lesson; therefore no whole-page no-clipping claim can be made.
- Image screenshots show English pencil-plate annotations embedded in the raster and Tamil UI headings/controls outside it. **English–Tamil anatomical labels within the plate are not established.** Some embedded pencil labels are too small/faint to certify from these screenshots.
- The visible pencil morphology broadly resembles a ciliated Paramecium with oral-groove region, macronucleus and contractile-vacuole structures; **precise anatomical label endpoints, nuclear interpretation and rights provenance remain unapproved** without full-resolution source-plate review.

## Test coverage boundaries

- U58 instrumented test checks the external and osmoregulation figures and verifies concept-specific visibility/nonvisibility across selected sections. It does **not** screenshot every section, prove full five-unit coverage, or scientifically certify all plates.
- Workflow step `Verify all 17 debug-only PNG assets on Android emulator` passed, but the evidence archive does **not** include 17 separate anatomical screenshot captures. Thus file presence/runtime checks passed; **visual anatomical inspection of all 17 images is not evidenced**.
- No physical-device QA evidence is in this archive. All debug-only review plates remain unapproved for student-facing release.

## Acceptance decision

**ENGINEERING EMULATOR GATE: PASS for this exact commit.**

**NATIVE TEXTBOOK READER FINAL ACCEPTANCE: HOLD.** Resolve concept-level image reuse, verify original full-resolution anatomy and leader lines, establish bilingual plate-label correctness, review all 17 review-only assets visually, inspect full scroll/rotation/physical device, and complete chapter-level academic approval before release.
