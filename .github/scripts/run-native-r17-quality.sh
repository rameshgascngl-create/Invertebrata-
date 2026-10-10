#!/usr/bin/env bash
set -euo pipefail
evidence="qa-evidence/r17-quality"
device_dir="/sdcard/Download/native-anatomy-evidence"
mkdir -p "$evidence"
adb shell mkdir -p "$device_dir"
phase=NOT_STARTED
result=NOT_STARTED
finish() {
  local code=$?
  printf 'result=%s\nphase=%s\nexit=%s\ncommit=%s\n' "$result" "$phase" "$code" "$GITHUB_SHA" > "$evidence/result.txt"
  adb logcat -d -v threadtime > "$evidence/logcat.txt" 2>&1 || true
  adb pull "$device_dir" "$evidence/screenshots" >/dev/null 2>&1 || true
  adb shell dumpsys meminfo com.gasczoology.invertebratelab > "$evidence/meminfo.txt" 2>&1 || true
  adb shell dumpsys accessibility > "$evidence/accessibility.txt" 2>&1 || true
}
trap finish EXIT
# Disable radios: this validates offline UI/fallback, never audible voice acceptance.
adb shell svc wifi disable
adb shell svc data disable
adb shell settings put global airplane_mode_on 1
adb shell settings get global airplane_mode_on > "$evidence/airplane-setting.txt"
adb shell dumpsys connectivity > "$evidence/connectivity-before.txt"
adb shell cmd package query-services --brief -a android.intent.action.TTS_SERVICE > "$evidence/tts-services.txt" 2>&1 || true
for config in "ENGLISH 1.0 portrait" "TAMIL 1.0 portrait" "TAMIL 2.0 portrait" "TAMIL 2.0 landscape"; do
  read -r language scale orientation <<< "$config"
  phase="$language-scale-$scale-$orientation"
  mkdir -p "$evidence/$phase"
  adb shell settings put system font_scale "$scale"
  adb shell settings get system font_scale > "$evidence/font-scale-$scale.txt"
  adb shell mkdir -p /sdcard/Download/native-anatomy-evidence
  rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
  if gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeR17TeachingAcceptanceTest \
    -Pandroid.testInstrumentationRunnerArguments.r17Language="$language" \
    -Pandroid.testInstrumentationRunnerArguments.r17Orientation="$orientation" \
    -Pandroid.testInstrumentationRunnerArguments.r17Scale="$scale" > "$evidence/$phase/gradle.txt" 2>&1; then
    result=PASS
  else
    result=INSTRUMENTATION_FAIL
    cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/$phase/results" 2>/dev/null || true
    cp -a native-app/app/build/reports/androidTests/connected "$evidence/$phase/reports" 2>/dev/null || true
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/$phase/results"
  cp -a native-app/app/build/reports/androidTests/connected "$evidence/$phase/reports"
done
result=PASS
