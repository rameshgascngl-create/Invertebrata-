#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/five-unit-r2"
D="/sdcard/Download/five-unit-qa"
mkdir -p "$E"
result="NOT_STARTED"
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; find "$E" -name "*.png" -print0 | xargs -0 -r sha256sum > "$E/SHA256SUMS"; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
for n in {1..50};do
  if adb shell mkdir -p "$D" >/dev/null 2>&1;then break;fi
  sleep 1
done
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0;do
  result="RUNNING_${scale}"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { result="WRONG_FONT_SCALE_${actual}";exit 1; }
  mkdir -p "$E/scale-$scale"
  rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeFiveUnitRestorationTest" \
    > "$E/scale-$scale/gradle.txt" 2>&1;then
    result="INSTRUMENTATION_FAILED_${scale}"
    cp -a native-app/app/build/outputs/androidTest-results/connected "$E/scale-$scale/results" 2>/dev/null || true
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$E/scale-$scale/results"
  python3 - "$E/scale-$scale/results" <<'PY'
from pathlib import Path
import sys
import xml.etree.ElementTree as ET
files=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
assert files, "Missing real Android instrumentation XML"
cases=[c for f in files for c in ET.parse(f).getroot().iter("testcase")]
assert len(cases)==4, ("Not all four image/textbook tests ran",len(cases))
assert all(c.get("classname")=="com.gasczoology.invertebratelab.NativeFiveUnitRestorationTest" for c in cases)
assert all(c.find(k) is None for c in cases for k in ("failure","error","skipped"))
print("R2_ANDROID_REAL_UI_TESTS=PASS",len(cases))
PY
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for size in 100 200;do
  for scene in unit1-protozoa-english unit5-pila-tamil-missing-plates unit5-asterias-english-missing-plate unit4-penaeus-tamil-missing-appendages;do
    [[ -s "$E/captured/r2-$size-$scene.png" ]] || { result="MISSING_UI_SCREEN_${size}_${scene}";exit 1; }
  done
done
result="PASS_UNVERIFIED_ARTWORK"
