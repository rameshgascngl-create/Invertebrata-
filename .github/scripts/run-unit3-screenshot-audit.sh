#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/unit3"
D="/sdcard/Download/unit3-qa"
mkdir -p "$E"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt";  adb pull "$D" "$E/screen-captures" > "$E/pull.log" 2>&1 || true; adb pull "/sdcard/Download/unit2-qa" "$E/unit2-screens" > "$E/unit2-pull.log" 2>&1 || true; find "$E" -name "*.png" -print0 | xargs -0 -r sha256sum > "$E/SHA256SUMS"; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
# The preserved Unit II screenshot instrumentation writes into its own
# absolute Android /sdcard/Download/unit2-qa directory. Create both directories
# before either suite runs; do not bypass the decoded-PNG checks.
ready=0
for n in {1..50}; do
  if adb shell mkdir -p "$D" /sdcard/Download/unit2-qa >/dev/null 2>&1; then
    ready=1
    break
  fi
  sleep 1
done
[[ "$ready" == 1 ]] || { result="SCREENSHOT_STORAGE_NOT_READY"; exit 1; }
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
  # Android Gradle runner does not reliably accept multiple comma-separated
  # classes here. Previous run executed ONLY Unit III (3 tests), yet our
  # aggregate assertion expected Unit II + III (7). Invoke independently,
  # archive each XML before Gradle replaces the connected test-results folder,
  # and verify the exact class names/test totals.
  for suite in UnitIIITeachingTest UnitIITeachingTest; do
    result="RUNNING_${scale}_${suite}"
    rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
    if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
      -Pandroid.testInstrumentationRunnerArguments.class="com.gasczoology.invertebratelab.Native${suite}" \
      > "$folder/gradle-${suite}.txt" 2>&1; then
      result="UI_TEST_FAILED_${scale}_${suite}"
      cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results-${suite}" 2>/dev/null || true
      exit 1
    fi
    cp -a native-app/app/build/outputs/androidTest-results/connected "$folder/results-${suite}"
    python3 - "$folder/results-${suite}" "$suite" <<'PY'
from pathlib import Path
import sys,xml.etree.ElementTree as ET
target= "com.gasczoology.invertebratelab.Native" + sys.argv[2]
cases=[x for f in Path(sys.argv[1]).rglob('TEST-*.xml') for x in ET.parse(f).getroot().iter('testcase')]
expected = 8 if sys.argv[2] == "UnitIIITeachingTest" else 4
assert len(cases)==expected,(sys.argv[2],len(cases),expected)
assert all(c.get("classname")==target for c in cases),(target,[c.get("classname") for c in cases])
assert all(c.find(y) is None for c in cases for y in ('failure','error','skipped'))
print("PASS suite="+sys.argv[2]+" count="+str(len(cases)))
PY
  done
done
adb pull "$D" "$E/captured" > "$E/final-pull.log"
for scale in 100 200;do
  for scene in fasciola-historical ascaris-historical fasciola-revised ascaris-revised ascaris-cdc-complete ascaris-graphite-bitmap-visible fasciola-magnified-4x ascaris-magnified-4x fasciola-fullscreen-1x fasciola-fullscreen-4x ascaris-fullscreen-1x ascaris-fullscreen-4x fasciola-source-review-draft ascaris-source-review-draft fasciola-compare-ready ascaris-compare-ready fasciola-full-warning ascaris-full-warning fasciola-pencil-intake-provisional ascaris-pencil-intake-pending fasciola-pencil-intake-full-disclosure ascaris-pencil-intake-full-disclosure;do
    [[ -s "$E/captured/unit3-$scale-$scene.png" ]] || { result="MISSING_IMAGE_$scale-$scene"; exit 1; }
  done
done
result=PASS
