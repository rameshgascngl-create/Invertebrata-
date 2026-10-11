# R1.9 integration freeze — Paramecium only

Status: **FREEZE / CANDIDATE / NOT RELEASE-APPROVED**. Date: 2026-10-11.

## Authoritative branch and lineage
- Existing R1.8 full automated acceptance remains on `81f1f12ed4176b8da59d6e9d32c705c1c9251b1c` (run 38055572127). This is a historical milestone, not the current R1.9 acceptance.
- R1.9 focused work `d1ccf8b13b6efcac059e6f2ecedf7e5eddd396e6` compiled but failed combined teaching and process-death gates (run 38102205461).
- Native egestion hold and genuinely killed-process recovery: `67052bfe073b41b8f4414187ae69f2c2b54ffb26`, descended from R1.9 and independently passed run 38102903565.
- Separate Tamil200 pause isolation: `06ed532649ddad637ce349488e77255903e04b48`, isolated pass run 38101808510. The Paramecium physiology plate blob matches the egestion head exactly; the reconciliation uses already-combined source, **not** a blind divergent-branch merge.
- This frozen branch is the only candidate for subsequent bug fixes, academic dossier work, and combined validation. Prior branches must be preserved read-only as references. The default `main` is the earlier legacy production lineage and must NOT be overwritten.

## Strict change control
1. **NO feature expansion** and **NO Sycon/Obelia/Fasciola/Ascaris/Earthworm/Penaeus/Pila/Asterias implementation**.
2. NO changes to `versionCode`, `versionName`, `applicationId`, accepted A5 source, release signing, release workflow, source references or biological assertions except through documented academic correction.
3. Changes permitted: demonstrable R1.9 defects, deterministic acceptance, native app accessibility/usability defects, source-backed Paramecium science corrections, editorial documentation, reviewer evidence.
4. Do not relax existing `0.4..0.85` intermediate-frame gate, exact phase / bitmap / cohort assertions, actual Android font scale, true PID kill, or screenshot evidence requirements.
5. Separate exact-commit **engineering pass** from **independent zoological and Tamil approval** and **physical Android testing**. No isolated pass or green APK job alone closes the gate.
6. Every candidate must record parent SHA, affected files, test case, failure root cause, normal/Tamil 100%/Tamil 200% outcomes and output evidence.
7. Validation sequence: deterministic isolated test first; ONLY after pass, focused four-configuration UI and process death combined; then review screenshots and APK identity. No unchanged full reruns.

## Last failed combined test and correction scope
- R1.9 `d1ccf8b1` run 38102205461: English 100 portrait 4/4, Tamil 100 portrait 4/4, Tamil 200 portrait 4/4, Tamil 200 landscape 3/4. Failure: `readerLinkAndActualRotationPreserveDetailedLearningPosition` 12s timeout before actual rotation, when manual scroll and pause race 6-second animation.
- The source already contains a user-visible real-frame hold to exactly 75/120 = **0.625**, with selected stage and DataStore persistence. Isolated pause and egestion recovery passed separately. The integration test now drives this genuine clock hold before rotation, without substituting a synthetic phase.
- R1.9 `d1ccf8b1` process-death 15s setup timeout; the descendant egestion candidate fixes it with a genuine intermediate hold and passed one isolated PID kill/relaunch.
- This does **not** constitute a combined R1.9 pass until all exact-commit gates have passed.

## Academic review status
- Native curriculum: nine type studies, 44 teaching slots. Only Paramecium is `DRAFT_UNVERIFIED`; eight further type studies remain `NOT_AUTHORED`. **Zero** accepted type studies.
- Native Paramecium teaching currently covers illustrated anatomy, feeding/digestion, egestion, ciliary movement, nuclei, division/conjugation, and water balance via multiple screens. The signed science/Tamil/illustration approval is still **PENDING**; strong software tests cannot substitute for it.
- Inspect every proposed plate for anatomical topology, species attribution to *Paramecium caudatum*, meaningful/legible leaders at 100 and 200% Tamil, qualitative vs measured timing, distinguishable cytoproct and contractile-vacuole pores, pH/enzymes vs direct observation, and accurate molecular evidence.
- Academic review form must record chapter/plate ID, primary source (and species), testable claim, English/Tamil wording, proposed correction, reviewer, date, image SHA, and PASS/FAIL/DEFER. No reviewer credentials or approval are inferred.
- Student-centered tasks: chapter navigation, meaningful anatomical zoom, selectable organs, real stage comparisons, 2-language written and audible narration, complete reference/limitation explanations, separate practice mode.

## Usability and release gates
- Original emulator PNGs: normal, Tamil 100 portrait, Tamil 200 portrait and landscape. Inspect readability, full-plate bringIntoView, headings and subheadings, long button line wrapping, target height, button collisions, organ taps, reduced-motion behavior and retained chapter/state after rotation and Back.
- Physical QA remains PENDING: install exact debug QA APK with hash; test portrait/landscape/real system font 200%, genuinely enabled TalkBack, offline English and Tamil TTS, interruptions, process-death recovery, sustained use, cold launch, device temperature/memory/performance. Emulator automation cannot certify audible speech or human scientific correctness.
- Keep release HOLD. Do not call Sycon implementation until the entire Paramecium module has an explicit academic approval record, physical handset QA is documented, and all R1.9 exact-commit engineering gates pass.
