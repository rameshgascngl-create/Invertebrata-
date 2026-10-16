#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/unit1-references"
D="/sdcard/Download/u12-reference-qa"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; find "$E" -name "*.png" -print0 | xargs -0 -r sha256sum > "$E/SHA256SUMS"; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
ready=0
for n in {1..50}; do
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
  for n in {1..45}; do
    adb shell dumpsys connectivity > "$folder/network.txt"
    if grep -Eq '^Active default network: none$' "$folder/network.txt"; then ready=1;break;fi
    sleep 1
  done
  [[ "$ready" == 1 ]] || { result="NOT_OFFLINE_$scale";exit 1;}
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
      -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnitIProtozoaReferenceTest \
      > "$folder/gradle-unit1.txt" 2>&1; then
    result="UI_TEST_FAILED_$scale"
    cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results-unit1" 2>/dev/null || true
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results-unit1"
  python3 - "$folder/results-unit1" <<'PY'
from pathlib import Path
import sys, xml.etree.ElementTree as ET
root=Path(sys.argv[1])
cases=[node for file in root.rglob("TEST-*.xml")
       for node in ET.parse(file).getroot().iter("testcase")]
assert len(cases)==8,("Expected EIGHT native atlas, references and historical-source authenticity tests",len(cases))
assert all((node.get("classname") or "").endswith("NativeUnitIProtozoaReferenceTest") for node in cases)
assert all(node.find(k) is None for node in cases for k in ("failure","error","skipped"))
print("UNIT1_PROTOZOA_REFERENCES_API34=PASS count="+str(len(cases)))
PY
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for scale in 100 200; do
  for scene in four-images-english tamil-parasite-atlas \
    u18-trypanosoma-english u18-leishmania-english \
    u18-trypanosoma-tamil u18-leishmania-tamil \
    u19-trypanosoma-calibration u110-trypanosoma-source-review \
    u111-trypanosoma-image-first u112-trypanosoma-pixel-refinement \
    u110-imported-review \
    u115-english-promastigote-canvas u115-english-amastigote-canvas \
    u115-tamil-promastigote-canvas \
    u116-unmatched-archive-identity u117-unmatched-source-isolated; do
    [[ -s "$E/captured/u12-$scale-$scene.png" ]] || { result="MISSING_SCREEN_$scale-$scene";exit 1;}
  done
done
result=PASS
