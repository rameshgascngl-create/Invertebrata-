#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/five-unit-r7"
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
  for suite in NativeFiveUnitRestorationTest NativeFiveUnitMissingRasterCoverageTest NativeR6ParameciumPencilTest NativeR7SinglePlateLabTest;do
    suite_results="$E/scale-$scale/$suite"
    rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
    if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
      "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.$suite" \
      > "$E/scale-$scale/gradle-$suite.txt" 2>&1;then
      result="INSTRUMENTATION_FAILED_${scale}_$suite"
      cp -a native-app/app/build/outputs/androidTest-results/connected "$suite_results" 2>/dev/null || true
      echo "::error::Android suite failed: scale=$scale suite=$suite"
      tail -n 160 "$E/scale-$scale/gradle-$suite.txt" || true
      find "$suite_results" -name "TEST-*.xml" -exec grep -n -E "failure|error|Assertion|Exception" {} + || true
      exit 1
    fi
    cp -a native-app/app/build/outputs/androidTest-results/connected "$suite_results"
    python3 - "$suite_results" "$suite" <<'PY'
from pathlib import Path
import sys
import xml.etree.ElementTree as ET
files=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
assert files, "Missing real Android instrumentation XML"
cases=[c for f in files for c in ET.parse(f).getroot().iter("testcase")]
src=Path("native-app/app/src/androidTest/java/com/gasczoology/invertebratelab/"+sys.argv[2]+".kt").read_text(encoding="utf-8")
expected=sum(1 for line in src.splitlines() if line.strip().startswith("@Test"))
assert expected>0, ("No @Test found in source",sys.argv[2])
assert len(cases)==expected, ("Not all image/textbook tests ran",len(cases),"expected",expected,sys.argv[2])
assert all(c.get("classname")=="com.gasczoology.invertebratelab."+sys.argv[2] for c in cases)
assert all(c.find(k) is None for c in cases for k in ("failure","error","skipped"))
print("R7_ANDROID_REAL_UI_TESTS=PASS",sys.argv[2],len(cases))
PY
  done
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for size in 100 200;do
  for scene in unit1-protozoa-english unit5-pila-tamil-missing-plates unit5-asterias-english-source-plates unit4-penaeus-tamil-missing-appendages;do
    [[ -s "$E/captured/r2-$size-$scene.png" ]] || { result="MISSING_UI_SCREEN_${size}_${scene}";exit 1; }
  done
  for scene in u2-sycon-english u2-obelia-tamil u3-fasciola-english u3-ascaris-tamil;do
    [[ -s "$E/captured/r3-$size-$scene.png" ]] || { result="MISSING_R3_UI_SCREEN_${size}_${scene}";exit 1; }
  done
  [[ -s "$E/captured/r6-$size-unit1-paramecium-pencil-tamil.png" ]] || { result="R6_PARAMECIUM_REAL_BITMAP_SCREEN_MISSING_$size"; exit 1; }
  [[ -s "$E/captured/r7-$size-paramecium-single-source-tamil.png" ]] || { result="R7_PARAMECIUM_NO_DUPLICATE_SCREEN_MISSING_$size"; exit 1; }
done
result="PASS_UNVERIFIED_ARTWORK"
