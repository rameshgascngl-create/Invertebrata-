#!/usr/bin/env bash
set -euo pipefail

DEST="qa-evidence/unit23-editorial"
DEVICE="/sdcard/Download/unit23-editorial-qa"
mkdir -p "$DEST"
result="NOT_STARTED"
scale="none"
collect() {
    code=$?
    printf 'commit=%s\nscale=%s\nresult=%s\nexit=%s\n' "${GITHUB_SHA:-unknown}" "$scale" "$result" "$code" > "$DEST/status.txt"
    adb pull "$DEVICE" "$DEST/captured" > "$DEST/screenshots-pull.txt" 2>&1 || true
    adb logcat -d -v threadtime > "$DEST/logcat.txt" 2>&1 || true
    find "$DEST" -name '*.png' -print0 | xargs -0 -r sha256sum > "$DEST/SHA256SUMS"
}
trap collect EXIT

for attempt in $(seq 1 45); do
    if adb shell mkdir -p "$DEVICE" > /dev/null 2>&1; then break; fi
    sleep 2
done
adb shell mkdir -p "$DEVICE"
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
for scale in 1.0 2.0; do
    result="TESTING_$scale"
    folder="$DEST/font-$scale"
    mkdir -p "$folder"
    adb shell settings put system font_scale "$scale"
    actual="$(adb shell settings get system font_scale | tr -d '\r')"
    [[ "$actual" == "$scale" ]] || { result="WRONG_FONT_SCALE_$actual"; exit 1; }
    rm -rf native-app/app/build/outputs/androidTest-results/connected
    if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
        -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnitIIIIIEditorialSmokeTest \
        -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
        > "$folder/gradle.log" 2>&1; then
        mkdir -p "$folder/xml"
        cp -a native-app/app/build/outputs/androidTest-results/connected/. "$folder/xml/" 2>/dev/null || true
        result="ANDROID_FAILURE_$scale"
        grep -E -m 20 -A 2 'failed|FAILED|Failure|Assertion|Exception' "$folder/gradle.log" | tail -55 >&2 || true
        exit 1
    fi
    mkdir -p "$folder/xml"
    cp -a native-app/app/build/outputs/androidTest-results/connected/. "$folder/xml/"
done

adb pull "$DEVICE" "$DEST/captured" > "$DEST/screenshots-pull.txt"
python3 - <<'PY'
from pathlib import Path
from xml.etree import ElementTree as ET
root = Path('qa-evidence/unit23-editorial')
for scale in ('1.0','2.0'):
    xmls = list((root/f'font-{scale}'/'xml').rglob('TEST-*.xml'))
    assert xmls, ('MISSING_ANDROID_TEST_RESULTS', scale)
    cases = [c for p in xmls for c in ET.parse(p).getroot().iter('testcase')
        if c.get('classname') == 'com.gasczoology.invertebratelab.NativeUnitIIIIIEditorialSmokeTest']
    assert len(cases) == 8, ('EXPECTED_8_CHAPTER_LANGUAGE_CASES', scale, len(cases))
    assert all(c.find(k) is None for c in cases for k in ('failure','error','skipped'))
pics = list((root/'captured').rglob('*.png'))
expected = {f'{chapter}-{lang}-{font}-{where}.png'
    for chapter in ('u2-sycon','u2-obelia','u3-fasciola','u3-ascaris')
    for lang in ('english','tamil') for font in ('100','200')
    for where in ('heading','introduction','practice')}
actual = {p.name for p in pics}
assert expected == actual, ('MISSING_SCREENSHOTS',sorted(expected-actual),
                             'UNEXPECTED_SCREENSHOTS',sorted(actual-expected))
assert all(p.stat().st_size > 10000 for p in pics)
print('UNIT_II_III_ENGLISH_TAMIL_100_200_ALL_16_CASES_48_SCREENSHOTS_PASS')
PY
result="PASS"
