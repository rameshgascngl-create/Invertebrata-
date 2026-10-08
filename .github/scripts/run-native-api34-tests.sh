#!/usr/bin/env bash
# Run in ONE shell: reactivecircus/android-emulator-runner executes script lines
# separately otherwise. All existing test assertions remain unchanged.
set -uo pipefail
mkdir -p qa-evidence
normal_exit=NOT_RUN
large_text_exit=NOT_RUN

capture_evidence() {
    adb logcat -d -v threadtime > qa-evidence/logcat.txt 2>&1 || true
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
