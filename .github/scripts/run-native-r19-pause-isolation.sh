#!/usr/bin/env bash
# Isolate ONLY the previously failing Tamil200 physiology test. Exact-commit,
# real emulator screenshot and strict one-test XML cohort are required.
set -euo pipefail
evidence="qa-evidence/r19-pause-isolation"
device_dir="/sdcard/Download/native-anatomy-evidence"
package="com.gasczoology.invertebratelab"
method="physiologicalViewsDistinguishExchangeFromEnergyMetabolismWithRealMotion"
class="com.gasczoology.invertebratelab.NativeR19TeachingAcceptanceTest"
result="NOT_RUN"
mkdir -p "$evidence"

cleanup() {
  local code=$?
  printf 'result=%s\nexit=%s\ncommit=%s\nmethod=%s\n' \
    "$result" "$code" "$GITHUB_SHA" "$method" > "$evidence/result.txt"
  adb logcat -d -v threadtime > "$evidence/logcat.txt" 2>&1 || true
  adb pull "$device_dir" "$evidence/screenshots" > "$evidence/pull.txt" 2>&1 || true
  adb shell dumpsys connectivity > "$evidence/connectivity.txt" 2>&1 || true
  adb shell dumpsys meminfo "$package" > "$evidence/meminfo.txt" 2>&1 || true
  find "$evidence" -type f -name '*.png' -print0 | xargs -0 -r sha256sum > "$evidence/png.sha256" || true
}
trap cleanup EXIT

adb shell mkdir -p "$device_dir"
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
adb shell settings put system font_scale 2.0
adb shell settings get system font_scale > "$evidence/font-scale.txt"
grep -q '^2\.0' "$evidence/font-scale.txt" || {
  result="FONT_SCALE_NOT_200"; exit 1;
}
offline=0
for i in {1..30}; do
  adb shell dumpsys connectivity > "$evidence/connectivity-before.txt"
  if grep -q 'Active default network: none' "$evidence/connectivity-before.txt"; then
    offline=1; break
  fi
  sleep 1
done
[[ "$offline" == 1 ]] || { result="NETWORK_NOT_DISCONNECTED"; exit 1; }

rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
if gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class="$class#$method" \
  -Pandroid.testInstrumentationRunnerArguments.r19Language=TAMIL \
  -Pandroid.testInstrumentationRunnerArguments.r19Orientation=portrait \
  -Pandroid.testInstrumentationRunnerArguments.r19Scale=2.0 \
  > "$evidence/gradle.txt" 2>&1; then
  result="TEST_EXECUTED"
else
  result="INSTRUMENTATION_FAILED"
  cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/results" 2>/dev/null || true
  cp -a native-app/app/build/reports/androidTests/connected "$evidence/reports" 2>/dev/null || true
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/results"
cp -a native-app/app/build/reports/androidTests/connected "$evidence/reports"
python3 - "$evidence/results" "$method" <<'PYCODE'
import pathlib,sys,xml.etree.ElementTree as ET
root=pathlib.Path(sys.argv[1])
expected=sys.argv[2]
paths=list(root.rglob("TEST-*.xml"))
assert paths, "NO_INSTRUMENTATION_XML"
cases=[t for p in paths for t in ET.parse(p).getroot().iter("testcase")]
assert len(cases)==1 and cases[0].attrib["name"]==expected, ("WRONG_TEST_COHORT",[(t.attrib.get("name")) for t in cases])
assert not any(cases[0].find(n) is not None for n in ("error","failure","skipped")), "TEST_NOT_PASS"
print("EXACTLY_ONE_TEST_PASS",expected)
PYCODE
adb shell dumpsys connectivity > "$evidence/connectivity-after.txt"
grep -q 'Active default network: none' "$evidence/connectivity-after.txt" || {
  result="NETWORK_RECONNECTED"; exit 1;
}
adb pull "$device_dir" "$evidence/screenshots-final" > "$evidence/screenshot-pull.txt"
find "$evidence/screenshots-final" -type f -name 'r19-tamil-200-portrait-physiology-*-intermediate.png' | grep -q . || {
  result="INTERMEDIATE_PNG_MISSING"; exit 1;
}
result="PASS"
echo "ISOLATED_TAMIL200_INTERMEDIATE_PHASE_PASS commit=$GITHUB_SHA"
