# Nereis U4.5 — Exact-commit Android evidence and U4.6 corrective hold

**Source reviewed:** `5867664f2892f90ddff8c6d008829e8958516400`  
**GitHub Actions run:** `38998484622` (two jobs: success)  
**Artifact:** `unit4-earthworm-academic-foundation-api34-evidence`, artifact ID `12088740541`  
**App:** native Kotlin / Compose; offline API 34 Pixel 6 emulator  
**Evidence status:** `qa-evidence/unit4/status.txt` reports identical source commit, `result=PASS`, `exit=0`.  
**Acceptance:** ENGINEERING AUTOMATION PASS / ACADEMIC ARTWORK HOLD / PHYSICAL-DEVICE QA OPEN.

## Verified automation
- Both workflow jobs succeeded: `academic-and-compile` and `android-ui`.
- Kotlin compilation, Android instrumentation source compilation, and Gradle JVM tests succeeded.
- 8 English + 8 Tamil drawing screenshots at **each** of 100% and 200% font scales = 32 drawings.
- 8 Tamil legend captures at **each** font scale = 16 additional frames, total **48 Nereis frames**.
- Raster size of representative frames: **1080 x 2400**. All eight figures draw; no empty-image placeholders observed.

## Independent visible-pixel review of U4.5 screenshots

| Plate | Visual finding | Disposition |
|---|---|---|
| 01 External morphology | Numerically labelled, but trunk consists of stacked near-identical ellipses rather than convincing continuous segmented annelid pencil anatomy. | Redraw required for college practical acceptance. |
| 02 Enlarged head | Eyes and appendages visible; prostomium/peristomium boundaries and attachment of sensory cirri are not sufficiently realistic to authenticate as a zoology atlas plate. | Detailed anatomical rework and manual comparison required. |
| 03 Biramous parapodium | Noto-/neuropodia and two chaetal bundles identifiable; rami look like overlapping elongated leaf outlines; tissue lobes and insertion details are schematic. | Anatomical drawing rework required. |
| 04 Digestive system | Coarse tubular lines and jaws; essential anatomical transitions are not illustrated as a rigorous dissected digestive tract. | Anatomical master required. |
| 05 Circulatory system | Vessels drawn as parallel projected stripes and accompanied by small cross-section inset; dorsal/ventral position can be misunderstood without explicit projection cues. | Better anatomical projection required. |
| 06 Nervous system | Ganglia and nerve cords numerically marked, but repeated trunk ellipses obscure a convincing dissection view. | Anatomical master required. |
| 07 Excretory system | Simplified nephrostome/septum/tubule/pore schematic, not an authentic pencil anatomical plate. | Practical-master review required. |
| 08 Trochophore | Apical cilia are visibly fused into one thick stroke in the original U4.5 rendering. | Definite graphical defect; **source corrected in U4.6**, emulator recheck pending. |

The screenshot framing is also weak: for plates 02–08 the top of the frame often contains the previous card and its legend. The **nominal Tamil legend screenshots**, at 200% font scale, may show only the first term(s), not the whole legend. Thus a green instrumentation count is **not** proof of complete Tamil legend evidence.

## U4.6 corrective branch
`native/u46-nereis-legend-evidence-remediation-20261017`

1. Preserve the original on-device screenshots, and **capture separate first-term and last-term Tamil legend frames** at both scales. Enforce 64 Nereis evidence PNGs via the shell driver.
2. Replace the fused trochophore apical stroke with spaced, individually drawn cilia in `UnitIVNereisStaticPlates.kt`; include the geometric signature in source audit.
3. Do not weaken 100/200%, offline, A5, exact-commit, or per-suite XML requirements.
4. Await **new exact-commit emulator PASS and actual inspection of corrected screenshots**. Do not claim U4.6 accepted from source edits alone.

## Academic release blockers independent of emulator success
- The current diagrams are **Compose Canvas vector sketches**, *not* original graphite scans. Do not label them authenticated scans.
- Tamil nomenclature has not been checked word by word against the college practical record/manual.
- No physical-device screenshot, accessibility, orientation or actual-screen pencil fidelity acceptance is yet available for the current candidate.
- Continue maintaining strict HOLD on student-facing academic acceptance and production release.

This report records observations from actual GitHub Actions PNG pixels, rather than inferring successful artwork from compilation.
