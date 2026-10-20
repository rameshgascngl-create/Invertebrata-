#!/usr/bin/env bash
set -euo pipefail
D="/sdcard/Download/u60-qa"
E="qa-evidence/u60-replaced-images"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/screens" > "$E/pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
ready=0
for attempt in $(seq 1 45); do
  if adb shell mkdir -p "$D" >/dev/null 2>&1; then ready=1;break;fi
  sleep 2
done
[[ "$ready" == 1 ]] || { result="SCREEN_STORAGE_NOT_READY";exit 1;}
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
for scale in 1.0 2.0; do
  result="RUNNING_$scale"
  adb shell settings put system font_scale "$scale"
  got="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$got" == "$scale" ]] || { result="FONT_SCALE_ERROR_$got";exit 1;}
  folder="$E/font-$scale"
  mkdir -p "$folder"
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeU60ReplacementImageTest" \
    > "$folder/gradle.txt" 2>&1; then
    cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/xml" 2>/dev/null || true
    result="UI_TEST_FAIL_$scale"
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/xml"
  python3 - "$folder/xml" <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET,sys
files=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
cases=[t for f in files for t in ET.parse(f).getroot().iter("testcase")]
assert len(cases)==3,(len(cases),files)
assert all(t.attrib.get("classname","").endswith("NativeU60ReplacementImageTest") for t in cases)
assert all(t.find(k) is None for t in cases for k in ("failure","error","skipped"))
print("PASS: THREE U60 REPLACEMENT TESTS AT FONT",sys.argv[1])
PY
done
adb pull "$D" "$E/screens" > "$E/pull-final.log" 2>&1
for scale in 100 200; do
  for shot in trypanosoma-replacement-visible leishmania-replacement-visible paramecium-replacement-visible;do
    [[ -s "$E/screens/u60-$scale-$shot.png" ]] || { result="MISSING_SCREENSHOT_$scale-$shot";exit 1;}
  done
done
result=PASS
