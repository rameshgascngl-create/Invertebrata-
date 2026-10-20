#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/u61-unit5"
D="/sdcard/Download/five-unit-qa"
mkdir -p "$E"
result="NOT_STARTED"
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
# sys.boot_completed can precede /sdcard mount; never weaken screenshots.
ready=0
for attempt in $(seq 1 60); do
  if adb shell mkdir -p "$D" > /dev/null 2>&1; then ready=1; break; fi
  sleep 2
done
[[ "$ready" == "1" ]] || { result="EXTERNAL_STORAGE_NOT_READY"; exit 1; }
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0; do
  result="TESTING_FONT_$scale"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { result="FONT_SCALE_MISMATCH_$scale"; exit 1; }
  F="$E/font-$scale"
  mkdir -p "$F"
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeFiveUnitRestorationTest \
    > "$F/gradle.log" 2>&1; then
    cp -a native-app/app/build/outputs/androidTest-results/connected "$F/xml" 2>/dev/null || true
    result="ANDROID_TEST_FAILURE_$scale"
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$F/xml"
  python3 - "$F/xml" <<'PY'
from pathlib import Path
import sys,xml.etree.ElementTree as ET
expected="com.gasczoology.invertebratelab.NativeFiveUnitRestorationTest"
xml=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
cases=[n for p in xml for n in ET.parse(p).getroot().iter("testcase")]
assert len(cases)==6,("EXPECTED_SIX_UI_TESTS",len(cases))
assert all(c.get("classname")==expected for c in cases)
errors=[(c.get("name"),n) for c in cases for n in ("failure","error","skipped") if c.find(n) is not None]
assert not errors,errors
print("UNIT_V_BILINGUAL_SCROLLING_AND_MISSING_PLATES=PASS",len(cases))
PY
done
adb pull "$D" "$E/captured-final" > "$E/pull-final.log"
for font in 100 200; do
  for scene in \
    unit1-protozoa-english \
    unit5-pila-tamil-missing-plates \
    unit5-pila-tamil-final-lesson \
    unit5-asterias-english-missing-plate \
    unit5-asterias-english-final-lesson \
    unit5-pila-english-late-chapter \
    unit5-asterias-tamil-late-chapter \
    unit4-penaeus-tamil-missing-appendages; do
    [[ -s "$E/captured-final/r2-$font-$scene.png" ]] || {
      result="MISSING_SCREENSHOT_$font-$scene"; exit 1;
    }
  done
done
find "$E/captured-final" -name '*.png' -type f -print0 | xargs -0 -r sha256sum > "$E/screenshot-sha256.txt"
result="API34_100_200_BILINGUAL_UI_PASS_ACADEMIC_IMAGES_PENDING"
