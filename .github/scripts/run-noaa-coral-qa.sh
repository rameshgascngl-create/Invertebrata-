#!/usr/bin/env bash
set -euo pipefail

DEST="qa-evidence/noaa-coral"
DEVICE="/sdcard/Download/noaa-coral-qa"
mkdir -p "$DEST"
adb shell mkdir -p "$DEVICE"
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable

for scale in 1.0 2.0; do
    adb shell settings put system font_scale "$scale"
    actual="$(adb shell settings get system font_scale | tr -d '\r')"
    [[ "$actual" == "$scale" ]] || { echo "FONT_SCALE_MISMATCH $actual != $scale"; exit 1; }
    gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
        -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeNoaaCoralBookTest \
        -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale"
    mkdir -p "$DEST/xml-$scale"
    cp -a native-app/app/build/outputs/androidTest-results/connected/. "$DEST/xml-$scale/"
done

adb pull "$DEVICE" "$DEST/captured"
python3 - <<'PY'
from pathlib import Path
from xml.etree import ElementTree as ET
root = Path('qa-evidence/noaa-coral')
cases = [c for p in root.rglob('TEST-*.xml') for c in ET.parse(p).getroot().iter('testcase')
         if c.get('classname') == 'com.gasczoology.invertebratelab.NativeNoaaCoralBookTest']
assert len(cases) == 4, ('EXPECTED_4_CASES', len(cases))
bad = [(c.get('name'), tag) for c in cases for tag in ('failure', 'error', 'skipped') if c.find(tag) is not None]
assert not bad, bad
photos = list((root / 'captured').rglob('coral-*.png'))
assert len(photos) == 8, ('EXPECTED_8_REAL_SCREENSHOTS', [p.name for p in photos])
assert all(p.stat().st_size > 10000 for p in photos)
print('NOAA_ORIGINAL_CORAL_API34_EN_TA_100_200_ZOOM=PASS')
PY
