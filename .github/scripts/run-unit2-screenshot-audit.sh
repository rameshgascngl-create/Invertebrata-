#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/unit2-screenshots"
D="/sdcard/Download/unit2-qa"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/screen-captures" > "$E/pull.log" 2>&1 || true; find "$E" -name "*.png" -print0 | xargs -0 -r sha256sum > "$E/SHA256SUMS"; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
ready=0
for n in {1..60};do
  if adb shell mkdir -p "$D" >/dev/null 2>&1 && adb shell test -d "$D";then ready=1;break;fi
  sleep 1
done
[[ "$ready" == 1 ]] || { result=STORAGE_NOT_READY; exit 1; }
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
# Android connectivity changes are asynchronous. A command's successful exit
# does not prove the emulator is offline. Never relax the offline requirement.
wait_for_offline() {
  local output="$1"
  local trace="$2"
  : > "$trace"
  for attempt in {1..45}; do
    adb shell dumpsys connectivity > "$output"
    local line
    line="$(grep -m1 '^Active default network:' "$output" || true)"
    printf 'attempt=%s %s\n' "$attempt" "$line" >> "$trace"
    if [[ "$line" == 'Active default network: none' ]]; then
      return 0
    fi
    if (( attempt % 10 == 0 )); then
      adb shell svc wifi disable >/dev/null 2>&1 || true
      adb shell svc data disable >/dev/null 2>&1 || true
    fi
    sleep 1
  done
  return 1
}
for scale in 1.0 2.0;do
  result="RUNNING_$scale"
  suffix=100
  [[ "$scale" == 2.0 ]] && suffix=200
  folder="$E/scale-$suffix"
  mkdir -p "$folder"
  adb shell settings put system font_scale "$scale"
  adb shell settings get system font_scale > "$folder/actual-scale.txt"
  grep -Fxq "$scale" "$folder/actual-scale.txt" || { result=WRONG_SCALE; exit 1; }
  wait_for_offline "$folder/connectivity.txt" "$folder/connectivity-wait.log" || {
    result="NOT_OFFLINE_$scale"
    exit 1
  }
  rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
     -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnitIITeachingTest > "$folder/gradle.txt" 2>&1;then
    result="UI_TEST_FAILED_$scale"
    cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results" 2>/dev/null || true
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results"
  python3 - "$folder/results" <<'PY'
from pathlib import Path
import sys,xml.etree.ElementTree as ET
cases=[c for p in Path(sys.argv[1]).rglob('TEST-*.xml') for c in ET.parse(p).getroot().iter('testcase')]
assert len(cases)==4,len(cases)
assert all(c.find(x) is None for c in cases for x in ('failure','error','skipped'))
PY
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for scale in 100 200;do
 for scene in sycon-lesson obelia-colony-lesson obelia-life-lesson sycon-external sycon-prosopyle sycon-apopyle obelia-colony obelia-hydranth obelia-gonangium obelia-medusa obelia-planula sycon-english obelia-english obelia-planula-english;do
  [[ -s "$E/captured/unit2-$scale-$scene.png" ]] || { result="MISSING_PNG_$scale-$scene"; exit 1; }
 done
done
result=PASS
