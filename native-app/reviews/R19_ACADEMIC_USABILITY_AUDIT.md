# INVERTEBRATA R1.9 — Academic and usability evidence record
**11 October 2026. Audit type: static source + actual emulator screenshot inspection.**  
**Status: ENGINEERING INTEGRATION CANDIDATE; BIOLOGY, TAMIL AND PHYSICAL-DEVICE APPROVAL PENDING.**
Do not change any `AcademicWorkStatus` to `ACCEPTED` as a result of this source audit.

## Authoritative teaching-corpus inventory
- `NativeCurriculumManifest.kt`: nine type-study organisms, 44 required teaching slots. Only Paramecium is `DRAFT_UNVERIFIED`, eight other type studies are `NOT_AUTHORED`, zero type studies are academically accepted.
- `NativeLessonDrafts.kt` contains a native bilingual Paramecium lesson with twelve sections. It must not be confused with the separate A5 question corpus.
- `ParameciumNutrition.kt`, `ParameciumDigestiveMaturation.kt`, `ParameciumEgestion.kt`, `ParameciumPhysiology.kt` supply 11 nutrition/physiology readings (5.1–5.11), with separately authored structures, stages and source notes.
- Other native material: ciliary locomotion, nuclear biology, fission, conjugation and contractile-vacuole water balance. These are DRAFT graphics/lessons, not reviewed academic plates.

## Biology editorial matrix
| Teaching strand | Academic checks before signing | Status |
|---|---|---|
| Identity, habitat and classical classification | Differentiate historic Protozoa from modern ciliophoran placement; source species *P. caudatum* consistently | PENDING SCIENCE REVIEW |
| Cilia and cortex | Basal-body/axoneme orientation, effective/recovery stroke and metachronal wave; no unmeasured beat frequency | PENDING SCIENCE REVIEW |
| Feeding apparatus, 5.1–5.2 | Oral vestibule, cytostome, cytopharynx, vacuole entry, real vs illustrative position | PENDING SCIENCE REVIEW |
| Maturation and digestion, 5.3–5.5 | Acidification versus lysosomal fusion, measured vs inferred pH, digestion and absorption | PENDING SCIENCE REVIEW |
| Cytoproct, 5.6–5.7 | Discharge and cortical closure, membrane recycling, no cytoproct/CVC pore conflation | PENDING SCIENCE REVIEW |
| Gas exchange and excretion, 5.8–5.11 | Dissolved gas exchange vs respiration/ATP metabolism, oxygen not directly becoming carbon dioxide, species-specific nitrogen claims | PENDING SCIENCE REVIEW |
| Binary fission | Closed MIC mitosis, MAC division, proter/opisthe, transverse furrow and daughter complements | PENDING SCIENCE REVIEW |
| Conjugation | Paired exchange and nuclear sequence, no false increase in cell number, *P. caudatum* vs other Paramecium species | PENDING SCIENCE REVIEW |
| Contractile vacuole | Ampulla, canal, membrane transport, fill/discharge, qualitative temporal stages, schematic coordinates | PENDING SCIENCE REVIEW |

For each plate obtain named zoologist and illustrator decision with date, source microscopy/paper, comparator species and a list of corrected leader endpoints. For each corresponding passage obtain a named scientific Tamil editor decision; audio pronunciation is separately tested with genuine Tamil TTS. Do not imply independent approval from scripted comparisons.

## Existing source-specific evidence and limitations
Source review annotations distinguish original native Canvas from borrowed microscopy, mark geometry and timing as schematic, and explicitly flag microscopy-specific species extrapolations. These are responsible precautions but **not** confirmation that every atlas leader, organ adjacency, shape or Tamil translation is accurate. The curriculum manifest still records `DRAFT_UNVERIFIED`.

## Authentic Android usability evidence reviewed
The four real screenshot files below were retrieved from GitHub Actions R1.9 workflow `38102205461`. Widths/heights, originals and SHA-256 provide screenshot identity.
| View | Pixels | SHA-256 | Finding |
|---|---|---|---|
| English 100% portrait reading 5.11 | 1080×2400 | `13bd33b0df5c8adcca1b0ec66804448da5cb6d573765c452e56cc781dbe38199` | Long-form explanation exists and renders; requires student navigation inspection |
| Tamil 200% portrait physiology | 1080×2400 | `2ea49729fe40de715bb16bc5cc07fe5166af08e9b243fb501008b0b49e4c48fc` | Text readable but oversized multiline action consumes most of visible viewport; figure-text proximity weak |
| Tamil 200% landscape reading 5.11 | 2400×1080 | `43e42bf36a71af899e539e58307d32e2c1fb3de4c6c263a536ae35a8810affb5` | Large dense unsegmented passages dominate landscape view; test return-to-diagram and continuity |
| Tamil 200% landscape controls | 2400×1080 | `2c027b481d528180bf0d03694de842355fc0a3b52d3e562b338d58533b51396d` | Existing capture; manual inspect controls, line wraps and TalkBack target order |

**Usability defects/risks (P1):** reading hierarchy and figure proximity at Tamil 200%; clarity of manual pause vs teaching hold; Voice/TalkBack fallback under offline conditions; on-device frame pacing, rotation/Back/relaunch recovery. These are not definitively closed by emulator screenshots or Compose semantics tests.

## Reconciliation and precise automated gate
- Frozen integration branch from `67052bfe073b41b8f4414187ae69f2c2b54ffb26` retains already-combined physiology and egestion genuine-frame hold (actual 75/120=0.625; NO synthetic phase) and genuine PID-process-kill evidence.
- Source commit `67383d3db3565cf2df34c6e8f0b95e7780fe7ef6` corrected only the **rotation UI instrumentation** to use the user-visible real-clock hold. Isolated API34 Tamil 200% landscape test PASS: run `38106242620`, `result=PASS`, JUnit `1 test, 0 failures`, actual `font_scale=2.0`.
- The **full** R1.9 acceptance must run the original EIGHT expected teaching tests (rather than only four `R19PhysiologyGate` tests) × English 100 portrait / Tamil 100 portrait / Tamil 200 portrait / Tamil 200 landscape, plus two DataStore compatibility tests, plus genuine background kill/relaunch process-death job. Run on the same commit, collect full XML, screenshot hashes, exact APK identity and old/new PID. Do not relax assertions, substitute mock frames, or treat isolated results as overall PASS.
- After source correction and full R1.9 acceptance, prove R1.8 baseline regression and obtain the human/device approvals before release.

## Human acceptance record — must be completed externally
For each reviewer record: name, role/institution, date, candidate SHA, APK SHA-256, section/plate ID, cited source/species, PASS/FAIL/DEFER, evidence file hash and specific corrective action. **No reviewer decision has been received.**

Physical-device test record must include installed QA APK identity, actual handset and Android API, English/Tamil at normal and real 200% system scale in portrait/landscape, full organ touch semantics, genuine TalkBack, audible offline English/Tamil TTS, interruptions, app lifecycle and 20-minute sustained performance. **NOT RUN — physical QA cannot be inferred from emulator evidence.**

## Release and next organism
Release is on HOLD. Do not begin Sycon until: all combined exact-commit technical gates PASS, every relevant Paramecium scientific/Tamil illustration/lesson sign-off is formally recorded, and physical handset QA closes with traceable evidence.
