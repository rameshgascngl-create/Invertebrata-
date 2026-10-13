#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/unit3"
D="/sdcard/Download/unit3-qa"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/screen-captures" > "$E/pull.log" 2>&1 || true; find "$E" -name "*.png" -print0 | xargs -0 -r sha256sum > "$E/SHA256SUMS"; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
for n in {1..50}; do
  if adb shell mkdir -p "$D" >/dev/null 2>&1; then break; fi
  sleep 1
done
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0; do
  folder="$E/scale-$scale"
  mkdir -p "$folder"
  result="RUNNING_$scale"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { result="WRONG_FONT_SCALE_$actual"; exit 1; }
  ready=0
  for n in {1..45};do
    adb shell dumpsys connectivity > "$folder/network.txt"
    if grep -Eq '^Active default network: none$' "$folder/network.txt"; then ready=1; break;fi
    sleep 1
  done
  [[ "$ready" == 1 ]] || { result="NOT_OFFLINE_$scale";exit 1;}
  rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnitIIITeachingTest > "$folder/gradle.txt" 2>&1;then
    result="UI_TEST_FAILED_$scale"
    cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results" 2>/dev/null || true
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results"
  python3 - "$folder/results" <<'PY'
from pathlib import Path
import sys,xml.etree.ElementTree as ET
cases=[x for f in Path(sys.argv[1]).rglob('TEST-*.xml') for x in ET.parse(f).getroot().iter('testcase')]
assert len(cases)==3,len(cases)
assert all(c.find(y) is None for c in cases for y in ('failure','error','skipped'))
PY
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for scale in 100 200;do
  for scene in fasciola-historical ascaris-historical;do
    [[ -s "$E/captured/unit3-$scale-$scene.png" ]] || { result="MISSING_IMAGE_$scale-$scene"; exit 1; }
  done
done
result=PASS
