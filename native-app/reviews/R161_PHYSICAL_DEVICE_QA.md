# R1.6.1 physical Android acceptance — PENDING: PHYSICAL DEVICE REQUIRED

No physical Android device is exposed to the current workspace. Emulator screenshots/test results cannot close this gate. Use the **exact candidate commit** and APKs from that commit's `native-r161-exact-commit-apk` workflow artifact. Record its `commit.txt`, verify both SHA-256 entries in `SHA256SUMS`, and retain package/signature audit output. Android versionName 1.8.9/versionCode 18900 are preserved release identifiers; the Git commit identifies the candidate.

Use a dedicated QA handset/profile: the same package ID and debug signing can conflict with an existing data-bearing installation. The commands never uninstall an application or weaken Android signature checking.

## Exact executable preparation and process test

```bash
# From the artifact directory: hashes must pass before installation.
sha256sum -c SHA256SUMS
adb devices -l
# Supply the observed physical serial, the actual APK files, commit file,
# and an empty evidence directory; do not substitute an emulator serial.
bash ci/r161_physical_device_qa.sh SERIAL app-debug.apk app-debug-androidTest.apk commit.txt physical-evidence
```

The script rejects an emulator, installs the checked APK pair, records handset/OS/commit/APK hashes, and executes both the unchanged N1.4 assessment process-death sequence and the new ciliary stage-4 sequence. It confirms package UID, ActivityManager background state, `am kill`, disappearance of the original PID, different new PID, and separate no-fixture-mutation verifiers. It does not claim manual touch, speech, TalkBack or performance acceptance.

## Manual cases — enter PASS / FAIL / PENDING with evidence

Record reviewer, model, Android/API/build, display size/density, date/time/timezone, serial/profile, navigation mode, exact source SHA and APK hashes. A completed row needs an observation and original screenshot/video/log filename with SHA-256.

| ID | Action and expected result | Evidence needed |
| --- | --- | --- |
| PD01 | Install exact pair; cold-launch offline with Wi-Fi/mobile data off. All written lessons, diagrams and revision work. | Hash verification, installation log, radios/connectivity and first screen. |
| PD02 | English 100%, portrait: inspect all four ciliary views and both insets; compare legend/phase explanation. | Four original screenshots; distinguish effective from low recovery and mixed metachrony. |
| PD03 | Tamil 100%, portrait/landscape: scroll every paragraph and navigate sections/tabs, then return. Stage/reading section retained. | Screenshots, navigation observation; no glyph clipping or unreachable controls. |
| PD04 | Android Settings actual font scale 2.0, Tamil, portrait/landscape. View whole Canvas; activate every stage/structure/audio/navigation button. | Settings scale screenshot or ADB value, app screenshots, usable 48dp targets. |
| PD05 | Tap actual cilia, basal body inset/bodies and axoneme inset, then use accessible selection buttons. Correct structure highlights. | Recorded touch/selection plus full original PNGs. |
| PD06 | Enable genuine TalkBack; focus diagram and headings; hear selected stage/structure; invoke three Canvas actions and all buttons. | Human spoken-label log/video. Semantics assertions alone do not close TalkBack acceptance. |
| PD07 | In flight mode, verify installed **embedded** English voice. Play somatic-cilia organ narration and all four views; replay. Hear accurate complete text. | Voice name/locale/network flag, sound recording/reviewer log and callback diagnostics. Initialization alone is insufficient. |
| PD08 | Same for installed embedded Tamil voice; human reviewer checks pronunciation, technical terms and sentence correspondence. | Real audible recording and Tamil reviewer findings. |
| PD09 | While audio plays: stop, replay, switch language, navigate away, background, rotate, interrupt using another audio source/call as feasible. No overlapping/stale-language playback or resource leak. The candidate requests transient audio focus and stops on focus loss; the handset must verify actual platform interruptions. | Audio/interruption log or video; any platform audio-focus limitation recorded as defect. |
| PD10 | On QA profile without Tamil voice package (or a separately controlled missing-voice case), attempt Tamil narration offline. Clear message and complete written explanation retained; no false playing claim. | Voice inventory, screenshot and observed absence of audio. Do not delete the user's normal voice package. |
| PD11 | Run scripted N1.4 and stage-4 kill/relaunch. No force-stop substituted for background death. | Two setup/verify outputs, UID/polls, original/gone/new PIDs, UI dumps and recovery screenshots. |
| PD12 | Repeat rotation, system Back, Home/re-entry, lesson/chapter changes and 20 stage changes. Preserve chapter/stage, no crash or unexpected restart. | Interaction video/log and crash log. |
| PD13 | At least 20 minutes of lesson scrolling/diagrams/audio; collect meminfo before/after and frame statistics. Investigate crash, sustained growth or visible jank rather than inventing new thresholds. | Native memory/frame logs, observed performance and reproducible defect steps. |
| PD14 | Review current scientific/Tamil audit alongside original source; record separate dated biology/Tamil decisions and asset revisions. | Named reviewer decisions; no CI result substituted. |

Capture unaltered evidence:

```bash
adb -s SERIAL shell settings get system font_scale
adb -s SERIAL exec-out screencap -p > device-view.png
adb -s SERIAL shell dumpsys meminfo com.gasczoology.invertebratelab > memory.txt
adb -s SERIAL shell dumpsys gfxinfo com.gasczoology.invertebratelab framestats > frames.txt
adb -s SERIAL logcat -d -v threadtime > logcat.txt
sha256sum device-view.png memory.txt frames.txt logcat.txt > physical-evidence.sha256
```

## Open gates

Physical acceptance, actual TalkBack speech, audible offline English/Tamil, pronunciation and sustained handset performance remain PENDING until real observations exist. Human academic approvals remain PENDING. Keep PR #8 draft; this candidate does not authorize production release.
