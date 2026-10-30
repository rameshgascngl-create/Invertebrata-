# R1.7 external acceptance — PENDING

This record is not approval. The exact engineering commit, run, APK SHA-256 and screenshots are supplied by the final QA report. Do not merge or release based solely on emulator gates. Prior R1.6.1 evidence is unchanged.

## Physical Android device checklist — PHYSICAL DEVICE REQUIRED

Record reviewer, date, model, Android build, RAM, display size, language, TTS engine/version, installed voice names and airplane-mode state. Verify the downloaded APK pair against the report's SHA-256 before installation. These are debug QA binaries, not a production release.

```bash
sha256sum app-debug.apk app-debug-androidTest.apk
adb devices -l
adb shell getprop ro.product.model
adb shell getprop ro.build.fingerprint
adb install -r app-debug.apk
adb install -r app-debug-androidTest.apk
adb shell settings put system font_scale 1.0
adb shell am start -W -n com.gasczoology.invertebratelab/.MainActivity
```

1. Open the Paramecium laboratory and Nuclear biology. Read all numbered subsections in English and Tamil; confirm full paragraphs, not only quiz prompts. Switch chapters and return; verify saved stages. Go Home and reopen; verify position.
2. Touch MAC and MIC on the anatomical plate and use their accessible buttons. Confirm distinct shapes, non-colour selection outline and full organ explanation. Tap the independently enlarged insets; ensure they are not described as extra cells/nuclei.
3. Manually walk every stage, then Play/Pause/Previous/Next/Replay/Reset. Observe real visible changes and paused restoration. Compare MIC mitosis, MAC partition and transverse cytokinesis; count cells. In conjugation follow stationary/migratory pronuclei and A/B parent patterns. Count two cells during exchange; later fission is a separately identified event.
4. Rotate portrait/landscape at 100% and actual 200% Tamil. Inspect every diagram and all multiline controls. Confirm whole drawing fits when using its bring-into-view control; scroll to all text and buttons; no overlap, cut glyphs or unreachable controls. Record genuine PNGs with `adb exec-out screencap -p > device-case.png`. Do not replace them with mocks.
5. Execute the dedicated R1.7 recovery driver using the exact APK/source commit in the report. It must establish ActivityManager background state, use `am kill`, prove original PID absent/new PID different and inspect recovered UI/explanation/DataStore. Repeat both fission and conjugation fixtures; existing N1.4 and ciliary recovery remain separate required regressions. Never substitute force-stop or activity recreation.
6. Disable Wi-Fi/mobile data, enable airplane mode and confirm connectivity. Navigate/read/touch/animate/recover offline. Local UI acceptance and missing-voice fallback do not prove audible speech.
7. After warm-up, loop navigation, organ touches, playback and rotation for at least 30 minutes. Record input responsiveness, crash/ANR observations and start/mid/end `adb shell dumpsys meminfo com.gasczoology.invertebratelab`; attach logcat. Investigate persistent growth or jank rather than inventing a passing performance number. No established threshold is changed by this checklist.
8. Restore the review device's original font-scale/orientation/network preferences after testing. Record PASS/FAIL and attach exact evidence for each case.

## Audible offline TextToSpeech — PENDING, VOICE AND LISTENING EVIDENCE REQUIRED

Install each desired voice package while connected. Record engine, English/Tamil voice names, offline/network-required metadata and device configuration. Then disable radios and confirm real audible pronunciation of every organ, subsection and stage script. Try replay, stop, interruption by backgrounding, language switching during narration and audio focus interruption. Confirm no old-language queue resumes unexpectedly. Deliberately test missing English/Tamil packages and an unavailable engine: a clear in-app message and complete written explanation must remain usable. Successful initialization, emulator service inventory, written fallback and fake unit-test engines are insufficient for this gate.

## Genuine TalkBack — PENDING, ENABLED SERVICE REQUIRED

Record TalkBack package/version, enabled accessibility-service settings, language/voice and reviewer. Navigate with real gestures and focus, not instrumentation semantics alone. Confirm numbered headings, chapter/stage selected state, meaningful plate descriptions, MAC/MIC custom actions, correct cell counts, play/pause and written audio error. Try Tamil 200%, landscape and reading after process restoration. Attach a focus walkthrough/video and observed speech; automated semantics are separate supporting evidence.

## Independent academic and Tamil review — PENDING, NAMED DECISION REQUIRED

A named zoology/ciliate specialist must compare the actual app and drawings to the dossier's primary microscopy and research, distinguish P. caudatum from other species, inspect closed MIC mitosis, MAC mechanics, meiotic reduction, reciprocal exchange, synkaryon/postzygotic fate and delayed old-MAC degradation. Review schematic scale, chromosome-symbol limits and distinctions between observations and interpretation. A Tamil scientific-language reviewer must check every parallel paragraph, label, heading and narration for accuracy and undergraduate readability. Record reviewer identity, qualification, date, reviewed exact SHA, source/asset decisions, corrections and explicit accept/reject. DRAFT_UNVERIFIED remains until that decision exists.
