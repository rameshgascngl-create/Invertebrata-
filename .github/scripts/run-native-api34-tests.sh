#!/usr/bin/env bash
# Run in ONE shell: reactivecircus/android-emulator-runner executes script lines
# separately otherwise. All existing test assertions remain unchanged.
set -uo pipefail
mkdir -p qa-evidence
normal_exit=NOT_RUN
large_text_exit=NOT_RUN

capture_evidence() {
    adb logcat -d -v threadtime > qa-evidence/logcat.txt 2>&1 || true
    # Diagnostic copies must be collected even if a screenshot assertion fails.
    # These are evidence only and never alter the job's PASS/FAIL gate.
    adb pull /sdcard/Download/native-anatomy-evidence/paramecium-normal-oral-groove.png \
        qa-evidence/paramecium-normal-oral-groove-diagnostic.png >/dev/null 2>&1 || true
    adb pull /sdcard/Download/native-anatomy-evidence/paramecium-tamil200-cytoproct.png \
        qa-evidence/paramecium-tamil200-cytoproct-diagnostic.png >/dev/null 2>&1 || true
    adb exec-out screencap -p > qa-evidence/final-screen.png 2>/dev/null || true
    adb shell uiautomator dump /sdcard/qa-window.xml >/dev/null 2>&1 || true
    adb pull /sdcard/qa-window.xml qa-evidence/window.xml >/dev/null 2>&1 || true
    printf 'normal=%s\ntamil200=%s\n' "$normal_exit" "$large_text_exit" | tee qa-evidence/result.txt
}
trap capture_evidence EXIT

if ! adb shell settings put system font_scale 1.0; then
    normal_exit=DEVICE_CONFIG_FAILED
    exit 1
fi

# Create durable screenshot destination *before* running instrumentation.
# Android UiAutomation executes raw argv and cannot evaluate shell compounds.
if ! adb shell mkdir -p /sdcard/Download/native-anatomy-evidence; then
    normal_exit=ANATOMY_SCREENSHOT_DIR_FAILED
    exit 1
fi

gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUiAcceptanceTest
normal_exit=$?

cp -a native-app/app/build/outputs/androidTest-results/connected \
    qa-evidence/normal-test-results 2>/dev/null || true
cp -a native-app/app/build/reports/androidTests/connected \
    qa-evidence/normal-reports 2>/dev/null || true

# A failed normal run must be diagnosed before starting a second full Gradle run.
if [ "$normal_exit" -ne 0 ]; then
    exit 1
fi

# N2.3B4: capture the REAL instrumented native Canvas, fail if missing.
# These emulator screenshots are visual-review aids, not physical-device QA.
# Persist across instrumentation teardown and any test-package cleanup.
# The instrumentation process copies PNGs here before each test finishes.
anatomy_dir="/sdcard/Download/native-anatomy-evidence"
if ! adb shell test -s "$anatomy_dir/paramecium-normal-oral-groove.png"; then
    normal_exit=ANATOMY_SCREENSHOT_MISSING
    exit 1
fi
if ! adb pull "$anatomy_dir/paramecium-normal-oral-groove.png" \
    qa-evidence/paramecium-normal-oral-groove.png; then
    normal_exit=ANATOMY_SCREENSHOT_PULL_FAILED
    exit 1
fi

# Preserve an independently captured actual learner-facing textbook screenshot.
if ! adb shell test -s "$anatomy_dir/r14-textbook-normal.png"; then
    normal_exit=R14_TEXTBOOK_NORMAL_SCREENSHOT_MISSING
    exit 1
fi
if ! adb pull "$anatomy_dir/r14-textbook-normal.png" qa-evidence/r14-textbook-normal.png; then
    normal_exit=R14_TEXTBOOK_NORMAL_SCREENSHOT_PULL_FAILED
    exit 1
fi

# R1.5 requires genuine emulator evidence of the selected feeding stage.
if ! adb shell test -s "$anatomy_dir/r15-feeding-normal.png"; then
    normal_exit=R15_FEEDING_NORMAL_SCREENSHOT_MISSING
    exit 1
fi
if ! adb pull "$anatomy_dir/r15-feeding-normal.png" qa-evidence/r15-feeding-normal.png; then
    normal_exit=R15_FEEDING_NORMAL_SCREENSHOT_PULL_FAILED
    exit 1
fi

if ! adb shell settings put system font_scale 2.0; then
    large_text_exit=DEVICE_CONFIG_FAILED
    exit 1
fi
adb shell am force-stop com.gasczoology.invertebratelab || true

gradle -p native-app :app:connectedDebugAndroidTest --rerun-tasks --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeTamilLargeTextAcceptanceTest
large_text_exit=$?

cp -a native-app/app/build/outputs/androidTest-results/connected \
    qa-evidence/tamil-200-test-results 2>/dev/null || true
cp -a native-app/app/build/reports/androidTests/connected \
    qa-evidence/tamil-200-reports 2>/dev/null || true

if [ "$large_text_exit" -ne 0 ]; then
    exit 1
fi

if ! adb shell test -s "$anatomy_dir/paramecium-tamil200-cytoproct.png"; then
    large_text_exit=ANATOMY_TAMIL200_SCREENSHOT_MISSING
    exit 1
fi
if ! adb pull "$anatomy_dir/paramecium-tamil200-cytoproct.png" \
    qa-evidence/paramecium-tamil200-cytoproct.png; then
    large_text_exit=ANATOMY_TAMIL200_SCREENSHOT_PULL_FAILED
    exit 1
fi
# Do not count green instrumentation if the actual Tamil textbook plate is
# absent. The full existing paper/cell/graphite pixel checks run in the test.
if ! adb shell test -s "$anatomy_dir/r14-textbook-tamil200.png"; then
    large_text_exit=R14_TEXTBOOK_TAMIL_SCREENSHOT_MISSING
    exit 1
fi
if ! adb pull "$anatomy_dir/r14-textbook-tamil200.png" qa-evidence/r14-textbook-tamil200.png; then
    large_text_exit=R14_TEXTBOOK_TAMIL_SCREENSHOT_PULL_FAILED
    exit 1
fi
if ! adb shell test -s "$anatomy_dir/r15-feeding-tamil200.png"; then
    large_text_exit=R15_FEEDING_TAMIL200_SCREENSHOT_MISSING
    exit 1
fi
if ! adb pull "$anatomy_dir/r15-feeding-tamil200.png" qa-evidence/r15-feeding-tamil200.png; then
    large_text_exit=R15_FEEDING_TAMIL200_SCREENSHOT_PULL_FAILED
    exit 1
fi
sha256sum qa-evidence/paramecium-normal-oral-groove.png \
    qa-evidence/paramecium-tamil200-cytoproct.png \
    qa-evidence/r14-textbook-normal.png \
    qa-evidence/r14-textbook-tamil200.png \
    qa-evidence/r15-feeding-normal.png \
    qa-evidence/r15-feeding-tamil200.png \
    > qa-evidence/anatomy-screenshot-sha256.txt
