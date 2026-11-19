# INVERTEBRATA — Native Android final acceptance register

**Scope:** Five units, 43 book chapters, English/Tamil, Kotlin/Jetpack Compose, offline textbook.
**Branch:** `audit/undergraduate-coverage-register-20261114`.
**Status:** Evidence collection pending. **NOT TESTED** is not a failure and must never be reported as PASS.
**Preservation:** No changes to accepted illustrations, captions, chapter text, academic tests, or image credits.

## Evidence rules

Each row requires (1) exact source commit, (2) workflow run URL or physical-device identifier, (3) test name and device/API/font/locale/orientation, (4) pass/fail evidence and log or screenshot reference, (5) reviewer and date. A historical pass at another commit is **HISTORICAL PASS**, not current release acceptance. Record actual test failures as FAIL; never convert them into environment failures without supporting logs. A build artifact is not proof of installation or runtime behaviour.

## Acceptance matrix (current release candidate)

| ID | Required check | Environment / method | Current result | Evidence |
|---|---|---|---|---|
| PKG-01 | Debug APK assembles and is signed with a QA-only key | `native-installable-qa-apk.yml`; `apksigner verify` | NOT VERIFIED | No accessible successful run or artifact |
| PKG-02 | Release APK and AAB assemble; shrinker succeeds | `native-release-packaging-candidate.yml` | NOT VERIFIED | No accessible successful run or artifact |
| PKG-03 | Exact source SHA and SHA-256 match APK/AAB | CI provenance scripts | NOT VERIFIED | Await build artifact |
| PKG-04 | Installable QA APK installs and launches | API 34 and API 35 emulator or physical Android device | NOT TESTED | Await install session |
| READER-01 | All five units and 43 chapters open | English and Tamil, normal font | NOT TESTED | Capture chapter coverage result |
| READER-02 | Previous/Next pagination, page indicator, no empty fixed-height pages | Short/long chapters, portrait/landscape | NOT TESTED | Capture screenshots and traversal |
| READER-03 | Definition/section differentiation and readable text | English/Tamil, normal and 200% font | NOT TESTED | Compare screenshots |
| READER-04 | Bookmarks, search and reading progress persist | App restart and activity recreation | NOT TESTED | Record state before/after |
| LEARN-01 | Quizzes open, submit, reveal answers and restore state | English/Tamil; rotation and process death | NOT TESTED | Instrumentation + screenshots |
| LEARN-02 | Glossary, flashcards, and navigation return correctly | Normal/200% font | NOT TESTED | Interaction log |
| MEDIA-01 | Accepted illustrations load, captions appear, zoom returns safely | Portrait/landscape; offline | NOT TESTED | Screenshot references; no new image audit |
| ACCESS-01 | TalkBack semantics, focus order and labelled controls | Android 34/35, English/Tamil | NOT TESTED | Accessibility session |
| ACCESS-02 | 200% font scaling has no clipped essential controls | API 34/35, portrait/landscape | NOT TESTED | Screenshots and test results |
| STATE-01 | Reading and quiz state survive rotation and process death | API 34/35 | NOT TESTED | Instrumentation result |
| PRIV-01 | In-app privacy policy opens, scrolls and closes in both languages | English/Tamil, 200% font | NOT TESTED | UI screenshots |
| PRIV-02 | Offline reading works with network unavailable | Airplane mode; first launch and restart | NOT TESTED | Device session |
| STORE-01 | Privacy policy public URL, Data safety declaration and production signing | Store submission checklist | PENDING | User-controlled signing and store metadata |
| STORE-02 | Final Play Console upload/validation | Signed release AAB | PENDING | Play Console receipt |

## Required emulator configurations

Use API 34 and API 35. For each, cover English 100%, Tamil 100%, and Tamil 200% in portrait and landscape. Also run an accessibility session with TalkBack and an offline session. Existing successful results at earlier commits may be referenced as historical evidence only; rerun only the tests needed to establish current-commit acceptance.

## Next execution sequence

1. Obtain the GitHub Actions run URL for `native-installable-qa-apk.yml`. If no run exists, dispatch it on the branch above.
2. Inspect job steps and logs. If the build fails, isolate the first causal error and correct only that defect.
3. Retrieve the signed QA APK artifact, record exact commit, byte size, SHA-256 and `apksigner verify` output.
4. Install the APK on API 34 and API 35. Capture package installation, launcher, reader, navigation, Tamil/English and recovery results.
5. Mark each row PASS/FAIL only with evidence; attach run links and screenshots. Keep unrelated items NOT TESTED.
6. Treat production release signing, store privacy metadata and Play Console acceptance as separate gates.

**Release decision:** BLOCKED until packaging, installation, and essential functional acceptance have current-commit evidence. Do not call an unsigned release APK installable or a debug-signed QA APK store-ready.
