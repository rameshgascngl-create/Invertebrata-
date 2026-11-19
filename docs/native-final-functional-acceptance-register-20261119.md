# INVERTEBRATA — Native Android final acceptance register

**Scope:** Five units, 43 book chapters, English/Tamil, Kotlin/Jetpack Compose, offline textbook.
**Branch:** `audit/undergraduate-coverage-register-20261114`.
**Status (19 November 2026):** Automated v1.9.1 packaging, signing, foundation, five-unit and 9/9 emulator matrix have passed at exact code SHA `7b27c8c372b4e4afae9aea6763e466f2f7e14c4c`. Physical-device upgrade, accessibility review, publisher credentials and app-store acceptance remain separate. **NOT TESTED** is not a failure and must never be reported as PASS.
**Preservation:** No changes to accepted illustrations, captions, chapter text, academic tests, or image credits.

## Evidence rules

Each row requires (1) exact source commit, (2) workflow run URL or physical-device identifier, (3) test name and device/API/font/locale/orientation, (4) pass/fail evidence and log or screenshot reference, (5) reviewer and date. A historical pass at another commit is **HISTORICAL PASS**, not current release acceptance. Record actual test failures as FAIL; never convert them into environment failures without supporting logs. A build artifact is not proof of installation or runtime behaviour.

## Acceptance matrix (current release candidate)

| ID | Required check | Environment / method | Current result | Evidence |
|---|---|---|---|---|
| PKG-01 | Debug APK assembles and is signed with a QA-only key | `native-installable-qa-apk.yml` | PASS (CI package) | [Run 44517314478](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314478); direct installation of this downloaded artifact on a physical device is a separate check |
| PKG-02 | Release APK and AAB assemble; shrinker succeeds | `native-release-packaging-candidate.yml` | PASS (CI) | [Unsigned package run 44517314326](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314326); [signed release run 44517314159](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314159) |
| PKG-03 | Exact source SHA and SHA-256 match APK/AAB | CI provenance and signed archive SHA256SUMS | PASS (artifact integrity) | Commit `7b27c8c3`; signed APK SHA-256 `b120928c989836faca05261af7e0578ed243a2a5a8e5d24e48a399f79e19339b`; AAB SHA-256 `800d31164e797c89701dfd9eed9b2849afcb1979dc3f5f4146995259b09cb8a6` |
| PKG-04 | Installable QA APK installs and launches | API 34 and API 35 emulator or physical Android device | PARTIAL | API 34/35 `connectedDebugAndroidTest` installed and exercised debug app; separate installation of the packaged QA artifact and physical-device signed APK upgrade is NOT TESTED |
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

## Current-commit automated evidence (newer than original register)

| Pipeline | GitHub Actions | Result |
| --- | --- | --- |
| Foundation checks | [44517313934](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517313934) | PASS |
| Five-unit restoration | [44517314054](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314054) | PASS |
| Release configuration | [44517313974](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517313974) | PASS |
| Unsigned release packaging (APK and AAB) | [44517314326](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314326) | PASS |
| Signed permanent-key release APK and AAB | [44517314159](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314159) | PASS |
| Installable debug QA APK | [44517314478](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314478) | PASS |
| API 34/35 emulator acceptance matrix | [44517315092](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517315092) | **9/9 PASS**, including API 34 200% font full suite, process-death, Unit I/III and Penaeus suites |

**Academic/rights gate clarification:** The `independent-academic-review` job in a later five-unit workflow [#44520829870](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44520829870) exits successfully only because its script deliberately catches a failed optional review. Its actual output says `ACADEMIC_ACCEPTANCE_REVIEWED_PLATES=0/52` and `SCIENCE_TAMIL_RIGHTS_GATE=FAIL`. The asset audit reports five identical-image-hash groups, which require interpretation rather than automatic rejection. Therefore a green five-unit workflow must **not** be cited as independent plate rights or Tamil/science sign-off. Preserve the owner's prior visual acceptance and all integrated images; verify source permissions from already recorded provenance before publishing.\n\nThe tested **application-code commit** is `7b27c8c3`. Subsequent changes `b2ba64d9`, `2f2accb8` and `a1b5fa0c` affect only privacy and store documentation, not packaged Android application source. Store screenshots are historical real captures from `2828ca51`; verify visual equivalence with the final build before uploading.

### Remaining independent publisher checks

- Confirm direct installation and in-place upgrade using the signed v1.9.1 APK on an actual Android device, retaining existing user data and certificate continuity.
- Confirm public privacy policy: [INVERTEBRATA privacy policy](https://github.com/rameshgascngl-create/Invertebrata-/blob/audit/undergraduate-coverage-register-20261114/docs/PRIVACY_POLICY.md).
- Enter an actual monitored developer support email, complete account verification/KYC, and select accurate age-target and education category declarations.
- Confirm that image reuse and provenance comply with the app-store's developer policies. Owner teaching-use acceptance has been recorded and must not be mistaken for a third-party licence.
- Finalise the [PhonePe submission metadata packet](https://github.com/rameshgascngl-create/Invertebrata-/blob/audit/undergraduate-coverage-register-20261114/docs/store/PHONEPE_SUBMISSION_1.9.1.md), preview the eight screenshots and icon, then submit and preserve the dashboard receipt.

## Required emulator configurations

Use API 34 and API 35. For each, cover English 100%, Tamil 100%, and Tamil 200% in portrait and landscape. Also run an accessibility session with TalkBack and an offline session. Existing successful results at earlier commits may be referenced as historical evidence only; rerun only the tests needed to establish current-commit acceptance.

## Next execution sequence

1. Obtain the GitHub Actions run URL for `native-installable-qa-apk.yml`. If no run exists, dispatch it on the branch above.
2. Inspect job steps and logs. If the build fails, isolate the first causal error and correct only that defect.
3. Retrieve the signed QA APK artifact, record exact commit, byte size, SHA-256 and `apksigner verify` output.
4. Install the APK on API 34 and API 35. Capture package installation, launcher, reader, navigation, Tamil/English and recovery results.
5. Mark each row PASS/FAIL only with evidence; attach run links and screenshots. Keep unrelated items NOT TESTED.
6. Treat production release signing, store privacy metadata and Play Console acceptance as separate gates.

**Release decision:** AUTOMATED ANDROID BUILD + EMULATOR MATRIX ACCEPTED FOR THE v1.9.1 RELEASE CANDIDATE. Publisher submission remains conditional upon physical-device upgrade/signing continuity, an accessible privacy-policy URL, publisher support email/account verification, accurate listing disclosures and resolution of applicable illustration usage rights. A successful emulator job is not a certificate of every untested usability or accessibility scenario.
