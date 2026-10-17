#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/unit4"
D="/sdcard/Download/unit4-qa"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; find "$E" -name "*.png" -print0 | xargs -0 -r sha256sum > "$E/SHA256SUMS"; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
ready=0
for n in {1..50};do
  if adb shell mkdir -p "$D" >/dev/null 2>&1; then ready=1;break;fi
  sleep 1
done
[[ "$ready" == 1 ]] || { result=SCREENSHOT_STORAGE_NOT_READY;exit 1;}
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
  [[ "$actual" == "$scale" ]] || { result="WRONG_FONT_$actual";exit 1;}
  ready=0
  for n in {1..45};do
    adb shell dumpsys connectivity > "$folder/network.txt"
    if grep -Eq '^Active default network: none$' "$folder/network.txt"; then ready=1;break;fi
    sleep 1
  done
  [[ "$ready" == 1 ]] || { result="NOT_OFFLINE_$scale";exit 1;}
  # Run each native Android test class independently. The instrumentation
  # runner does not reliably accept a comma-separated list of class names;
  # that previously executed only the three Earthworm methods.
  for suite in NativeUnitIVEarthwormTest NativeUnitIVPenaeusTest NativeUnitIVNereisTest; do
    suite_results="$folder/results-$suite"
    rm -rf native-app/app/build/outputs/androidTest-results/connected \
      native-app/app/build/reports/androidTests/connected
    if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
      "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.$suite" \
      > "$folder/gradle-$suite.txt" 2>&1; then
      result="UI_TEST_FAILED_${scale}_${suite}"
      cp -a native-app/app/build/outputs/androidTest-results/connected "$suite_results" 2>/dev/null || true
      exit 1
    fi
    cp -a native-app/app/build/outputs/androidTest-results/connected "$suite_results"
    python3 - "$suite_results" "$suite" <<'PY'
from pathlib import Path
import sys, xml.etree.ElementTree as ET
root=Path(sys.argv[1])
name=sys.argv[2]
expected="com.gasczoology.invertebratelab."+name
expected_count=3 if name=="NativeUnitIVNereisTest" else 4
cases=[node for file in root.rglob("TEST-*.xml")
       for node in ET.parse(file).getroot().iter("testcase")]
assert len(cases)==expected_count,("Incorrect Unit IV test count",name,len(cases),expected_count)
assert all((node.get("classname") or "") == expected for node in cases), (
    "Wrong/missing Android test class", name, [c.get("classname") for c in cases])
assert all(node.find(k) is None for node in cases
           for k in ("failure","error","skipped")),("Android test failure",name)
print("UNIT4_API34_TESTS=PASS class="+name+" count="+str(len(cases)))
PY
  done
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for scale in 100 200;do
  [[ -s "$E/captured/unit4-$scale-nereis-tamil-fullscreen-zoom.png" ]] || { result="MISSING_NEREIS_ZOOM_EVIDENCE_$scale";exit 1;}
  for scene in earthworm-english-digestive earthworm-tamil-excretory earthworm-return-navigation earthworm-tamil-external-atlas penaeus-english-appendages penaeus-tamil-protozoea penaeus-tamil-stage-retained penaeus-A5-preserved penaeus-tamil-interactive-telson;do
    [[ -s "$E/captured/unit4-$scale-$scene.png" ]] || { result="MISSING_SCREEN_$scale-$scene";exit 1;}
  done
  # Hard QA gate: every plate in both locales, with additional Tamil legend frames.
  for plate in {1..8}; do
    printf -v serial '%02d' "$plate"
    for locale in english tamil; do
      file="$E/captured/unit4-$scale-nereis-$locale-plate-$serial.png"
      [[ -s "$file" ]] || { result="MISSING_NEREIS_DRAWING_$scale-$locale-$serial"; exit 1; }
    done
    for frame in start end; do
      file="$E/captured/unit4-$scale-nereis-tamil-legend-$frame-$serial.png"
      [[ -s "$file" ]] || { result="MISSING_NEREIS_LEGEND_$scale-$frame-$serial"; exit 1; }
    done
  done
done
result=PASS
