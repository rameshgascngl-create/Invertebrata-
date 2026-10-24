#!/usr/bin/env bash
set -euo pipefail
D="/sdcard/Download/u57-qa"
E="qa-evidence/u57-paramecium"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
# Emulator images may not mount /sdcard immediately after sys.boot_completed.
# Wait for emulated external storage before creating the evidence directory.
for attempt in $(seq 1 30); do
  if adb shell '[ -d /sdcard/Download ]' >/dev/null 2>&1; then
    break
  fi
  sleep 2
done
adb shell mkdir -p /sdcard/Download
adb shell mkdir -p "$D"
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0; do
  result="TESTING_$scale"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { result="FONT_SCALE_WRONG";exit 1; }
  root="$E/scale-$scale"
  mkdir -p "$root"
  adb shell dumpsys connectivity > "$root/network.txt"
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeU57ParameciumProcessPngTest" \
      > "$root/gradle.txt" 2>&1; then
    cp -a native-app/app/build/outputs/androidTest-results/connected "$root/xml" 2>/dev/null || true
    echo "::error::Offline Paramecium Android test failed at font scale $scale"
    echo "===== Instrumentation failure excerpts ($scale) ====="
    grep -n -E -A 18 -B 8 'FAILED|FAILURE|AssertionError|ComposeNotIdleException|NoMatchingRootException|SemanticsNode|Exception|error:' "$root/gradle.txt" | tail -180 || true
    echo "===== Instrumentation log tail ($scale) ====="
    tail -100 "$root/gradle.txt" || true
    echo "===== JUnit XML failures ($scale) ====="
    find "$root/xml" -name 'TEST-*.xml' -exec grep -n -E -A 14 '<failure|<error' {} + || true
    result="API34_UNIT1_UI_FAIL_$scale"
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$root/xml"
  python3 - "$root/xml" <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET
import sys
docs=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
tests=[node for path in docs for node in ET.parse(path).getroot().iter("testcase")]
expected="com.gasczoology.invertebratelab.NativeU57ParameciumProcessPngTest"
assert len(tests)==1,(len(tests),docs)
assert tests[0].get("classname")==expected
assert all(tests[0].find(n) is None for n in ("failure","error","skipped"))
print("OFFLINE_UNIT_I_PNG_TEST=PASS")
PY
done
adb pull "$D" "$E/captured" > "$E/pull-final.log"
for scale in 100 200; do
  for view in vacuole-source-png vacuole-png-enlarged conjugation-text-no-vector; do
    [[ -s "$E/captured/paramecium-$scale-$view.png" ]] || { result="MISSING_IMAGE_$scale-$view";exit 1;}
  done
done
result=PASS
