#!/usr/bin/env bash
# R1.9 single-defect isolation: 200% Tamil cytoproct genuine midpoint.
# This script does NOT modify source or weaken assertions in the full suite.
set -euo pipefail
evidence="qa-evidence/r19-cytoproct-isolation"
device_dir="/sdcard/Download/native-anatomy-evidence"
method="cytoproctReallyOpensReleasesResiduesAndReclosesWithAccessibleHits"
class="com.gasczoology.invertebratelab.NativeR19TeachingAcceptanceTest"
result=NOT_STARTED
mkdir -p "$evidence"
cleanup() {
  local exitcode=$?
  printf 'result=%s\nexit=%s\ncommit=%s\nmethod=%s\n' "$result" "$exitcode" "$GITHUB_SHA" "$method" > "$evidence/result.txt"
  adb logcat -d -v threadtime > "$evidence/logcat.txt" 2>&1 || true
  adb pull "$device_dir" "$evidence/screenshots" > "$evidence/screenshot-pull.txt" 2>&1 || true
  find "$evidence" -type f -name '*.png' -print0 | xargs -0 -r sha256sum > "$evidence/png-sha256.txt" || true
}
trap cleanup EXIT
# sys.boot_completed can precede the emulated shared-storage mount.
# Never classify an external-storage startup race as an app failure.
storage_ready=0
for attempt in {1..60}; do
  if adb shell mkdir -p "$device_dir" >/dev/null 2>&1 &&
     adb shell test -d "$device_dir"; then
    storage_ready=1
    printf 'storage_ready_after_attempt=%s\n' "$attempt" > "$evidence/storage-ready.txt"
    break
  fi
  printf 'attempt=%s storage_not_ready\n' "$attempt" >> "$evidence/storage-poll.txt"
  sleep 1
done
[[ "$storage_ready" == 1 ]] || {
  result=STORAGE_MOUNT_NOT_READY
  adb shell ls -ld /sdcard /storage/emulated/0 /sdcard/Download > "$evidence/storage-diagnostic.txt" 2>&1 || true
  exit 1
}
adb shell settings put system font_scale 2.0
adb shell settings get system font_scale > "$evidence/font-scale.txt"
grep -Eq '^2(\.0+)?$' "$evidence/font-scale.txt" || { result=FONT_SCALE_NOT_200; exit 1; }
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
offline=0
for i in {1..30}; do
    adb shell dumpsys connectivity > "$evidence/connectivity-before.txt"
    if grep -q 'Active default network: none' "$evidence/connectivity-before.txt"; then offline=1; break; fi
    sleep 1
done
[[ "$offline" == 1 ]] || { result=NOT_OFFLINE; exit 1; }
rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
if gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class="$class#$method" \
  -Pandroid.testInstrumentationRunnerArguments.r19Language=TAMIL \
  -Pandroid.testInstrumentationRunnerArguments.r19Orientation=portrait \
  -Pandroid.testInstrumentationRunnerArguments.r19Scale=2.0 \
  > "$evidence/gradle.txt" 2>&1; then
  result=INSTRUMENTATION_COMPLETED
else
  result=INSTRUMENTATION_FAILED
  cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/results" 2>/dev/null || true
  cp -a native-app/app/build/reports/androidTests/connected "$evidence/reports" 2>/dev/null || true
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/results"
cp -a native-app/app/build/reports/androidTests/connected "$evidence/reports"
python3 - "$evidence/results" "$method" <<'PY'
import pathlib,sys,xml.etree.ElementTree as ET
paths=list(pathlib.Path(sys.argv[1]).rglob('TEST-*.xml'))
assert paths,'NO_JUNIT_XML'
cases=[c for p in paths for c in ET.parse(p).getroot().iter('testcase')]
assert len(cases)==1 and cases[0].attrib['name']==sys.argv[2],('WRONG_COHORT',[c.attrib.get('name') for c in cases])
assert all(cases[0].find(k) is None for k in ('error','failure','skipped')),'NONPASS_TEST'
print('EXACTLY_ONE_CYTOPROCT_TAMIL200_TEST_PASSED')
PY
adb shell dumpsys connectivity > "$evidence/connectivity-after.txt"
grep -q 'Active default network: none' "$evidence/connectivity-after.txt" || {result=NETWORK_RECONNECTED;exit 1;}
# Must have independently rendered Android screenshots of all three egestion
# phases, rather than believing XML-only green status.
adb pull "$device_dir" "$evidence/screenshots-verified" > "$evidence/final-screenshot-pull.txt"
for stage in docking egestion recycling; do
  find "$evidence/screenshots-verified" -type f -name "r19-tamil-200-portrait-cytoproct-${stage}-intermediate.png" | grep -q . ||
     { result=MISSING_REAL_MIDPOINT_SCREENSHOT; exit 1; }
done
result=PASS
