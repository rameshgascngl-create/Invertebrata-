#!/usr/bin/env bash
# R1.6 independent API 34 ciliary teaching-plate acceptance.
# The established N1.3–R1.5 harness and thresholds are not modified.
set -euo pipefail
evidence="qa-evidence/r16"
device_dir="/sdcard/Download/native-anatomy-evidence"
mkdir -p "$evidence"
normal="NOT_RUN"
tamil200="NOT_RUN"

diagnose() {
  local exit_code=$?
  printf 'normal=%s\ntamil200=%s\nexit=%s\ncommit=%s\n' \
    "$normal" "$tamil200" "$exit_code" "$GITHUB_SHA" \
    > "$evidence/result.txt"
  # A failing instrumentation run still produces authoritative XML/report
  # diagnostics. Preserve them before the emulator and runner are torn down.
  local failed_phase=""
  if [[ "$normal" == "INSTRUMENTATION_FAIL" ]]; then
    failed_phase="normal"
  elif [[ "$tamil200" == "INSTRUMENTATION_FAIL" ]]; then
    failed_phase="tamil200"
  fi
  if [[ -n "$failed_phase" ]]; then
    cp -a native-app/app/build/outputs/androidTest-results/connected \
      "$evidence/$failed_phase-results" 2>/dev/null || true
    cp -a native-app/app/build/reports/androidTests/connected \
      "$evidence/$failed_phase-reports" 2>/dev/null || true
  fi
  adb logcat -d -v threadtime > "$evidence/logcat.txt" 2>&1 || true
  adb exec-out screencap -p > "$evidence/final-screen.png" 2>/dev/null || true
  for name in r16-ciliary-normal.png r16-ciliary-tamil-normal.png \
      r16-ciliary-tamil200.png; do
    adb pull "$device_dir/$name" "$evidence/$name" >/dev/null 2>&1 || true
  done
}
trap diagnose EXIT

if ! adb shell mkdir -p "$device_dir"; then
  normal="SCREENSHOT_DIR_ERROR"
  exit 1
fi
if ! adb shell settings put system font_scale 1.0; then
  normal="FONT_SCALE_SETUP_FAILED"
  exit 1
fi
echo "R1.6: normal-scale Android Compose instrumentation"
if gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeR16CiliaryPlateTest \
  > "$evidence/normal-gradle.txt" 2>&1; then
  normal="PASS"
else
  normal="INSTRUMENTATION_FAIL"
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected \
  "$evidence/normal-results" 2>/dev/null || true
cp -a native-app/app/build/reports/androidTests/connected \
  "$evidence/normal-reports" 2>/dev/null || true
for name in r16-ciliary-normal.png r16-ciliary-tamil-normal.png; do
  if ! adb shell test -s "$device_dir/$name"; then
    normal="SCREENSHOT_MISSING:$name"
    exit 1
  fi
  if ! adb pull "$device_dir/$name" "$evidence/$name"; then
    normal="SCREENSHOT_PULL_FAILED:$name"
    exit 1
  fi
done

if ! adb shell settings put system font_scale 2.0; then
  tamil200="FONT_SCALE_SETUP_FAILED"
  exit 1
fi
adb shell am force-stop com.gasczoology.invertebratelab || true
echo "R1.6: genuine Tamil 200% Android Compose instrumentation"
if gradle -p native-app :app:connectedDebugAndroidTest --rerun-tasks --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeR16Tamil200AcceptanceTest \
  > "$evidence/tamil200-gradle.txt" 2>&1; then
  tamil200="PASS"
else
  tamil200="INSTRUMENTATION_FAIL"
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected \
  "$evidence/tamil200-results" 2>/dev/null || true
cp -a native-app/app/build/reports/androidTests/connected \
  "$evidence/tamil200-reports" 2>/dev/null || true
if ! adb shell test -s "$device_dir/r16-ciliary-tamil200.png"; then
  tamil200="SCREENSHOT_MISSING"
  exit 1
fi
if ! adb pull "$device_dir/r16-ciliary-tamil200.png" \
    "$evidence/r16-ciliary-tamil200.png"; then
  tamil200="SCREENSHOT_PULL_FAILED"
  exit 1
fi
sha256sum "$evidence/r16-ciliary-normal.png" \
  "$evidence/r16-ciliary-tamil-normal.png" \
  "$evidence/r16-ciliary-tamil200.png" \
  > "$evidence/screenshots.sha256"
echo "R1.6 candidate tests and actual screenshots: PASS"
