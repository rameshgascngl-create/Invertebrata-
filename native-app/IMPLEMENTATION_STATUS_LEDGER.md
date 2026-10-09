# INVERTEBRATA — verified progress ledger
Snapshot prepared 2026-10-09. Refer first to EDUCATIONAL_MASTER_PLAN.md. This ledger differentiates working code from curriculum completion.

## Audited source baseline
The implementation preceding this documentation checkpoint was commit 23afbcc496e70f8d46b9536450851caf2ff92e9e. Application uses native Kotlin/Jetpack Compose, not HTML/WebView. There are five accepted A5 JSON source assets covering 43 chapters, 86 bilingual five-mark questions and 430 answer points, plus a separate Earthworm teaching slot. The eight other type studies and complete syllabus lessons do not exist merely because chapter IDs are declared.

## Education deliverables, not engineering placeholders
| Deliverable | Verifiable state | Acceptance |
| --- | --- | --- |
| Full syllabus teaching lessons | Paramecium seven-section bilingual DRAFT only; other chapters lack authored lesson data | INCOMPLETE |
| Nine detailed organism type studies | Nine requirements documented; zero complete organism teaching modules | 0/9 |
| Native anatomy | Only external Paramecium Canvas prototype with five select/highlight controls; four Paramecium plates specified but not completed | Not academically accepted |
| Animated physiology and life cycles | No completed functioning native simulator | NOT IMPLEMENTED |
| Organ-specific speech | Android organ-narration TextToSpeech is not integrated | NOT IMPLEMENTED |
| Bilingual teaching | Question corpus and initial Paramecium draft only | INCOMPLETE |
| Independent anatomical / Tamil review | Paramecium five biological and five Tamil approvals remain pending | 0/5 and 0/5 |
| Physical device UX validation | Native debug QA APK exists, but real-device QA evidence not submitted | NOT RUN |
| Android release | Debug QA build only; application identity frozen at 1.8.9 / 18900 pending authorized version promotion | HOLD |

## Engineering snapshot on source commit 23afbcc
- Native foundation check: PASS, GitHub Actions 37899834340.
- Process-death recovery: PASS, 37899835161.
- Native debug QA APK compilation, package/signature, permission and checksum verification: PASS, 37899834391. Debug APK SHA-256: 6370d524d003070e844c4d4621203ea40b3e20bfb461147ac42340c576a0d8b6.
- Latest API 34 UI run: FAILED at emulator package download, 37899834341. Gradle/android sdkmanager recorded 'Error on ZipFile unknown archive'; emulator never booted, adb port 5554 refused. This is an INFRASTRUCTURE FAILURE, not a failed native UI assertion and not an accepted pass. Prior successes on different commits are not proof of this exact build's UI outcome.
- Historic anatomical defects remain unresolved, especially Earthworm setae, crop/anus/typhlosole, hearts, three nephridial forms, reproductive segments, Penaeus appendages, Pila and Asterias water-vascular detail.

## Highest-priority next coding milestone
Implement R1 Paramecium as an actual native interactive Zoology lesson, not a modified question-bank screen. First integrate learner navigation to Study / Anatomy / Simulate / Listen / Practice and a genuine scientifically described contractile-vacuole cycle simulation with stateful, testable native play/pause/replay. Expand anatomical and physiological content and organ-specific English/Tamil audio, then implement feeding, locomotion, fission and conjugation. All incomplete science remains visibly DRAFT_UNVERIFIED. Extend complete type-study functionality to the remaining eight organisms in the master-plan sequence.


## R1.1 native vacuole-plate separation (2026-10-09)
- **Scope:** In the native Paramecium Anatomy tab, explicitly separate two blue
  osmoregulatory contractile-vacuole complexes from a distinct amber digestive
  food vacuole. Provide three registered touch/click hotspots, English–Tamil
  landmark labels, organ-specific written/TTS explanations, and a direct link
  from this atlas to the stageful native osmoregulation simulation.
- **Scientific issue corrected:** The earlier vacuole schematic rendered a
  selectable food vacuole using the radial-arm contractile-vacuole symbol.
  Food vacuoles must not appear as contractile-vacuole complexes.
- **Independent references:** Plattner, 2015,
  https://pubmed.ncbi.nlm.nih.gov/23919298/ ; fluid-filling and discharge
  mechanism, https://www.sciencedirect.com/science/article/pii/S1065699502909376 .
  The drawn number of collecting arms and whole-cell coordinates remain
  illustrative; no source image has been copied or traced.
- **Tests added:** JVM marker/classification and viewport hit alignment;
  native API 34 anatomy selection, 48dp controls and osmoregulation navigation.
  Preserve all existing A5, process-death and Tamil 200% acceptance assertions.
- **Approval and release:** DRAFT_UNVERIFIED, biology 0/5 and Tamil 0/5;
  physical Android device QA PENDING. This is a development increment, not
  Paramecium R1 completion. Production release remains HOLD.
- **Next educational step:** refine anatomical plate accuracy and
  physiology visualization with independent biological/Tamil review and
  physical-device verification, then complete the remaining R1 content.

## Update discipline
After EACH true educational implementation commit, update this ledger with (1) exact HEAD and changed native feature paths; (2) newly authored full lessons, count out of 44; (3) which organism modules actually work, count out of nine; (4) working diagrams and simulations; (5) speech and Tamil coverage; (6) source/reviewer decisions; (7) device and CI evidence; (8) blocking scientific inaccuracies and precise next work. Do not equate green GitHub CI with subject-matter completion. Do not erase unresolved issues, invent reviewer signoffs, or release another QA-only milestone as though it were the requested product.
