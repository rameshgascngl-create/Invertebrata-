# Isolated branch QA execution

The original SHA-256 in this inherited protocol describes the **previous debug APK at 8a42b096**. After this branch is built, replace it in the result sheet with the **new exact APK SHA-256** from GitHub Actions. All 26 test statuses remain NT until measured on physical handsets. Passing emulator jobs is not real-device sign-off.

# INVERTEBRATA — Physical-device acceptance protocol

**Status:** NOT EXECUTED; no real handset has been tested in this work session.  
**QA source:** `8a42b0969263e3fec84e8980356c95c1ffd76a3d`  
**QA binary:** `INVERTEBRATA_QA_8a42b096_DEBUG.apk`  
**Expected SHA-256:** `61fa44e191da95ef863e4a72805c5bc70296f2e2d60816e172ecabab7c68ada0`  
**Reference emulator CI:** [Run 41595036022](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41595036022), 16/16 passing. Emulator CI is not physical-device acceptance.

## 1. Test environment recording

| Field | Tester completes |
|---|---|
| Date / tester | |
| Device manufacturer/model | |
| Android version / API level | |
| RAM / available storage | |
| Display resolution / refresh | |
| App version shown in Android settings | |
| APK SHA-256 on source workstation | |
| Prior installed version / data retained? | |
| Accessibility settings (font size, display size, TalkBack) | |
| Device screenshot / screen recording bundle URL | |

**Installation safety:** Preserve student/app data. Do not clear app storage or uninstall an existing signed build without backing up and agreeing to the data consequences. A debug build with a different signing key may not install as an update. If Android returns `INSTALL_FAILED_UPDATE_INCOMPATIBLE`, stop and document it; do not automatically remove the existing application.

```bash
sha256sum INVERTEBRATA_QA_8a42b096_DEBUG.apk
adb devices -l
adb install -r INVERTEBRATA_QA_8a42b096_DEBUG.apk
adb shell dumpsys package com.gasczoology.invertebratelab | grep -E 'versionCode|versionName|targetSdk|debuggable'
```

## 2. Acceptance test matrix

Use **P** (pass), **F** (fail), **NT** (not tested); retain screenshots/recordings and steps for any failure.

| ID | Test | Expected result | Status | Evidence |
|---|---|---|---|---|
| D01 | Install without wiping existing data | Installed and launches, or safe documented signing mismatch | NT | |
| D02 | Home → Units I–V | Each unit displays correct headings and topic list; no blank screen | NT | |
| D03 | Open a chapter in each unit | Heading and opening content start at top, not previous chapter's scroll position | NT | |
| D04 | Next/previous lesson **pages** | Correct sequential page number and content; no blocked buttons | NT | |
| D05 | Table of contents jump | Selected page appears immediately and begins at its title | NT | |
| D06 | Reopen chapter after returning Home | Intended saved page/position restored, no unrelated chapter offset | NT | |
| D07 | Activity recreation (rotation/config change) | Current lesson, language and page retained | NT | |
| D08 | Process death (background + Android kills process) | State restored from persisted checkpoint, not false default | NT | |
| D09 | App Back gesture/button | Returns to prior context without quitting unexpectedly | NT | |
| D10 | All five Unit I simulator modes | Scientific diagram pixels visibly animate when Play is pressed | NT | |
| D11 | Simulator Pause and Replay | Pause freezes diagram/progress; Replay begins from start | NT | |
| D12 | Simulator mode change | New process starts in correct stage, no stale previous graphics | NT | |
| D13 | Unit I parasitic life cycles | Trypanosoma and Leishmania stage explorers advance correctly | NT | |
| D14 | Unit IV prawn interactive pages | Appendages and larval sequence change labels/artwork correctly | NT | |
| D15 | Unit II & III interactive pages | Relevant teaching diagrams/stages reachable from Contents | NT | |
| D16 | Diagrams: tap, enlarge, pinch, drag | Sharp enough to read; labels/line endpoints correct; no clipping | NT | |
| D17 | English ↔ Tamil within chapter | Headings, questions and navigation switch consistently | NT | |
| D18 | Tamil 200% font scale portrait | Buttons ≥48dp, all page content accessible and no overlap | NT | |
| D19 | Tamil 200% font scale landscape | Top/bottom controls reachable; stage controls remain usable | NT | |
| D20 | Offline operation (airplane mode) | Curriculum, illustrations and simulators work without network | NT | |
| D21 | Answer reveal in 2- and 5-mark exercises | Correct question matched to complete answer; no missing terms | NT | |
| D22 | Long-read performance on low-memory device | No crash, unresponsive scrolling or repeatable ANR | NT | |
| D23 | TalkBack labels/reading order | All controls identifiable and operable without sight | NT | |
| D24 | No credential/data risk | No unrequested permissions or external network access | NT | |
| D25 | Figure credits on representative images | Visible credits match the actual audited file and licence | NT | |
| D26 | Fresh install separate test device | Works without pre-existing user preferences and stale assets | NT | |

## 3. Minimum evidence packet

Capture at least: home screen; one chapter start in **each** of Units I–V; page next/back and contents jump; a Tamil 200% portrait and landscape lesson; five simulator modes (before/play/pause); each parasite explorer; the prawn interactive study; one enlarged anatomical plate; one reproduction/larval diagram; two-mark and five-mark Q&A; and screenshots of copyright/attribution metadata. Log package version, Android details, date and exact failure reproduction.

Collect the complete `adb logcat` around a crash or ANR **with personal data redacted**. Prefer a 30–60 second annotated recording per functional defect; preserve before/after states and device orientation.

## 4. Final acceptance gates

- **Science:** Two independent reviewers accept the biological diagrams, stage sequences, label arrows, taxonomy, lesson accuracy and answer keys; unresolved rows remain clearly marked.
- **Tamil:** Independent Zoology/Tamil review accepts terminology, typography, bilingual equivalence and enlarged-font readability, including unedited lesson and `steps_ta`/`a5_ta` fields.
- **Rights:** Each packaged artwork has an exact binary hash, source/creator, licence or permission, matching in-app credit, and accepted reviewer signatures. Do not infer rights from appearance or filename.
- **Physical device:** All critical D-tests pass on at least one representative handset and a constrained/low-memory handset. Any F must be fixed and retested.
- **Release:** Generate a **new** non-debuggable APK/AAB; exclude review-only assets; validate the production signing certificate with `apksigner`, run final release tests and archive hashes.

**Do not confuse the QA debug APK with the eventual production release binary.** Passing this test matrix on the QA APK is necessary but is not by itself production sign-off.