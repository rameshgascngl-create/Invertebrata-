#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/u60-unit1"
D="/sdcard/Download/u60-qa"
mkdir -p "$E"
result="NOT_STARTED"
trap 'exitCode=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$exitCode" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
ready=0
for attempt in $(seq 1 50); do
  if adb shell mkdir -p "$D" >/dev/null 2>&1; then ready=1; break; fi
  sleep 2
done
[[ "$ready" == "1" ]] || { result="EXTERNAL_STORAGE_NOT_READY"; exit 1; }
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for font in 1.0 2.0; do
  result="TESTING_$font"
  adb shell settings put system font_scale "$font"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$font" ]] || { result="WRONG_FONT_SCALE_$font"; exit 1; }
  F="$E/font-$font"
  mkdir -p "$F"
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeU60ImagePlacementTest \
    > "$F/gradle.log" 2>&1; then
    cp -a native-app/app/build/outputs/androidTest-results/connected "$F/xml" 2>/dev/null || true
    result="IMAGE_ROUTING_TEST_FAILED_$font"
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$F/xml"
  python3 - "$F/xml" <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET
import sys
tests=[c for p in Path(sys.argv[1]).rglob("TEST-*.xml") for c in ET.parse(p).getroot().iter("testcase")]
expected="com.gasczoology.invertebratelab.NativeU60ImagePlacementTest"
assert len(tests)==3,("Expected exactly 3 tests",len(tests))
assert all(t.get("classname")==expected for t in tests),tests
assert all(t.find(x) is None for t in tests for x in ("failure","error","skipped"))
print("UNIT1_RETIRED_IMAGE_ROUTING_PASS=3")
PY
done
adb pull "$D" "$E/captured" > "$E/pull-final.log"
for scale in 100 200;do
  for scene in reference-scope trypanosoma-replacement-pending leishmania-replacement-pending paramecium-missing-unique-plates;do
    [[ -s "$E/captured/u60-$scale-$scene.png" ]] || {
      result="MISSING_SCREENSHOT_$scale-$scene";exit 1;
    }
  done
done
result="ROUTING_PASS_REPLACEMENT_ASSETS_PENDING"
