# N2.3E3 — Native Kotlin physical-device QA handoff

**Engineering deliverable:** installable **DEBUG-ONLY** Android APK with CI-verified signature, package/version, no INTERNET permission, SHA-256 and source commit. Not a production release.

## Download
On GitHub, open the **Native N2.3E3 physical-device QA APK** action for the requested commit, then its **Artifacts** section; download `N23E3-native-Kotlin-physical-device-QA-<commit>`. Extract `INVERTEBRATA-N23E3-NATIVE-DEBUG-QA.apk`. The artifact expires after 30 days. Confirm the artifact's included `PROVENANCE.txt` matches the intended commit; verify `APK-SHA256.txt` if transferring between devices.

**Do not confuse the 1.8.9 Android versionName with project phase N2.3E3.** The version fields are intentionally left unchanged to preserve the release baseline. The source commit identifies this QA build.

## Installation safety

The QA application has the SAME package ID as the existing application: `com.gasczoology.invertebratelab`, but uses a **debug key**. Android may reject an upgrade if an installed app uses a different signature. **Do not uninstall a production or data-bearing app just to install this QA APK.** Uninstalling can erase app data. First use a separate test handset / Android user profile or arrange a verified backup and explicit device-owner approval. Do not rely on the old WebView APK's data to migrate automatically.

## Human QA evidence sheet — fill with real observations

Record: device manufacturer/model, Android version, build SHA, test date/time/timezone, reviewer, screen size, navigation mode (gestures/3-button), and exact system font scale.

| Case | Action (record PASS, FAIL, or NOT RUN) | Required genuine evidence |
|---|---|---|
| QA-01 | Cold start offline (flight mode); navigate Paramecium unit from Home | Device screenshot/video with OS/version record |
| QA-02 | Portrait, English: scroll full Paramecium Canvas; no clipped boundary/label | Full upper/lower screen capture, evidence ID |
| QA-03 | Landscape, English: diagram retains proportions and buttons accessible | Screenshot and orientation evidence ID |
| QA-04 | Tamil language; set **Android system font to 200%**, verify displayed scaling and no clipped text | OS font setting screenshot + app screenshot, evidence ID |
| QA-05 | Turn on TalkBack; navigate each feature and controls using accessibility focus | Spoken-label recording or structured tester log with five IDs |
| QA-06 | Direct tap each of the five visible organ regions; verify selection and highlight; test corresponding accessible buttons | Recorded taps/results for pellicle, somatic-cilia, oral-groove, cytoproct, trichocysts |
| QA-07 | Background app, kill its process using ADB or OS where available, relaunch | Evidence Q5/answer/Tamil state persists; include PID details if ADB |
| QA-08 | Rotate repeatedly, use system Back, navigate Home, scroll and enlarge text | Evidence for no crash, missing navigation or clipping |
| QA-09 | Reopen in airplane mode and inspect evidence/limitations for each selected feature | Evidence that all local reference descriptions remain available |
| QA-10 | Independent zoology and Tamil specialists inspect authentic licensed source and candidate geometry side-by-side | Their dated findings/corrections; **never replace with CI test status** |

### Optional ADB evidence capture (computer-assisted)

```bash
adb devices -l
adb shell getprop ro.build.version.release
adb shell settings get system font_scale
adb exec-out screencap -p > paramecium-qa-device.png
adb shell dumpsys package com.gasczoology.invertebratelab
```

A screenshot alone does **not** prove TalkBack speech, a genuine direct tap, reviewer identity, species morphology or an anatomical approval. Label each file and record its checksum. Do not include student/private data in uploaded evidence. When using ADB to kill a process, preserve the exact old/new PIDs and don't confuse force-stop with actual background process death.

## Independent academic review handoff

Use `N23E1_INDEPENDENT_REVIEW_HANDOFF.md` and `N23E2_SOURCE_ACCESS_AND_INDEPENDENT_REVIEW_INTAKE.md`. Each external feature requires authenticated *P. caudatum* whole-cell reference, specimen orientation/geometry comparison, written specialist findings and dated separate Tamil signoff. Electron micrographs of thin sections do not prove projected whole-cell coordinates. Figure URLs are not distribution licenses.

**Stop condition:** Until actual phone testing and independent reviewer evidence are supplied, physical QA is **NOT RUN** and biological/Tamil approvals remain **0/5**. The anatomy stays `DRAFT_UNVERIFIED`. Even fully green CI does not approve the plate or authorize Play Store release.
