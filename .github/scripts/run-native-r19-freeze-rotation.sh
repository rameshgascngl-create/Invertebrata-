#!/usr/bin/env bash
# Isolate the ORIGINAL R1.9 Tamil200 landscape rotation-state gate.
# Do not substitute generic screenshot capture or skip existing assertions.
set -euo pipefail
evidence="qa-evidence/r19-freeze-rotation"
method="readerLinkAndActualRotationPreserveDetailedLearningPosition"
class="com.gasczoology.invertebratelab.NativeR19TeachingAcceptanceTest"
mkdir -p "$evidence"
result=NOT_RUN
trap 'code=$?; printf "result=%s\nexit=%s\ncommit=%s\nmethod=%s\n" "$result" "$code" "$GITHUB_SHA" "$method" > "$evidence/result.txt"; adb logcat -d -v threadtime > "$evidence/logcat.txt" 2>&1 || true; adb pull /sdcard/Download/native-anatomy-evidence "$evidence/screenshots" >/dev/null 2>&1 || true' EXIT
adb shell mkdir -p /sdcard/Download/native-anatomy-evidence
adb shell settings put system font_scale 2.0
adb shell settings get system font_scale > "$evidence/font-scale.txt"
grep -Eq '^2(\.0+)?' "$evidence/font-scale.txt" || { result=WRONG_FONT_SCALE; exit 1; }
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
offline=0
for i in {1..30}; do
  adb shell dumpsys connectivity > "$evidence/network.txt"
  if grep -q 'Active default network: none' "$evidence/network.txt"; then offline=1; break; fi
  sleep 1
done
[[ "$offline" == 1 ]] || { result=NOT_OFFLINE; exit 1; }
rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
if gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class="$class#$method" \
  -Pandroid.testInstrumentationRunnerArguments.r19Language=TAMIL \
  -Pandroid.testInstrumentationRunnerArguments.r19Orientation=landscape \
  -Pandroid.testInstrumentationRunnerArguments.r19Scale=2.0 \
  > "$evidence/gradle.txt" 2>&1; then
  result=INSTRUMENTATION_COMPLETED
else
  result=INSTRUMENTATION_FAIL
  cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/results" 2>/dev/null || true
  cp -a native-app/app/build/reports/androidTests/connected "$evidence/reports" 2>/dev/null || true
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/results"
cp -a native-app/app/build/reports/androidTests/connected "$evidence/reports"
python3 - "$evidence/results" "$method" <<'PYXML'
from pathlib import Path
import sys,xml.etree.ElementTree as ET
paths=list(Path(sys.argv[1]).rglob('TEST-*.xml'))
assert paths,'MISSING_XML'
cases=[c for p in paths for c in ET.parse(p).getroot().iter('testcase')]
assert len(cases)==1 and cases[0].attrib['name']==sys.argv[2],('WRONG_COHORT',[c.attrib.get('name') for c in cases])
assert not any(cases[0].find(t) is not None for t in ('error','failure','skipped')),'TEST_NOT_PASS'
print('PASS one genuine Tamil 200% landscape rotation test')
PYXML
adb shell dumpsys connectivity > "$evidence/network-after.txt"
grep -q 'Active default network: none' "$evidence/network-after.txt" || { result=NETWORK_RECONNECTED; exit 1; }
result=PASS
