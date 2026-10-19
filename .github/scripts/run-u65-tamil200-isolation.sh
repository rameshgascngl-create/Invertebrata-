#!/usr/bin/env bash
# U65: reproduce the previously failing 200% Tamil selector BEFORE the full
# U57/U58 battery. Never skip the original assertions or relax font scaling.
set -euo pipefail
E="qa-evidence/u65-isolated"
mkdir -p "$E"
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell settings put system font_scale 2.0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
test "$(adb shell settings get system font_scale | tr -d '\r')" = "2.0"
echo "U65_ISOLATED_FONT_SCALE=2.0"
rm -rf native-app/app/build/outputs/androidTest-results/connected
if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeU57ParameciumProcessPngTest" > "$E/gradle.txt" 2>&1; then
  cp -a native-app/app/build/outputs/androidTest-results/connected "$E/junit" 2>/dev/null || true
  echo "U65_ISOLATED_TAMIL200=FAIL; full U58 battery blocked"
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected "$E/junit"
python3 - "$E/junit" <<'PY'
from pathlib import Path
from xml.etree import ElementTree as ET
import sys
paths=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
cases=[case for path in paths for case in ET.parse(path).getroot().iter("testcase")]
assert len(cases)==1 and "NativeU57ParameciumProcessPngTest" in cases[0].attrib["classname"]
assert all(cases[0].find(tag) is None for tag in ("failure","error","skipped"))
print("U65_TAMIL200_ISOLATED_REAL_INSTRUMENTATION=PASS")
PY
echo "U65_RUN_FULL_NORMAL_AND_200_TAMIL_READER_TESTS"
bash .github/scripts/run-u58-paramecium-context-png.sh
