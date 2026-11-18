#!/usr/bin/env bash
set -euo pipefail

DEST="qa-evidence/noaa-coral"
DEVICE="/sdcard/Download/noaa-coral-qa"
mkdir -p "$DEST"
result="NOT_STARTED"
scale="none"
# Collect evidence on both passing and failing executions. A failed Android
# assertion must not be reduced to an opaque Gradle exit code.
collect() {
    code=$?
    printf 'commit=%s\nresult=%s\nfont_scale=%s\nexit=%s\n' "${GITHUB_SHA:-unknown}" "$result" "$scale" "$code" > "$DEST/status.txt"
    mkdir -p "$DEST/failure-xml"
    cp -a native-app/app/build/outputs/androidTest-results/connected/. "$DEST/failure-xml/" 2>/dev/null || true
    adb pull "$DEVICE" "$DEST/captured" > "$DEST/adb-pull.log" 2>&1 || true
    adb logcat -d -v threadtime > "$DEST/logcat.txt" 2>&1 || true
}
trap collect EXIT

for attempt in $(seq 1 45); do
    if adb shell mkdir -p "$DEVICE" >/dev/null 2>&1; then break; fi
    sleep 2
done
adb shell mkdir -p "$DEVICE"
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable

for scale in 1.0 2.0; do
    result="RUNNING_FONT_$scale"
    adb shell settings put system font_scale "$scale"
    actual="$(adb shell settings get system font_scale | tr -d '\r')"
    [[ "$actual" == "$scale" ]] || { result="SCALE_MISMATCH"; exit 1; }
    output="$DEST/font-$scale"
    mkdir -p "$output"
    rm -rf native-app/app/build/outputs/androidTest-results/connected
    if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
        -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeNoaaCoralBookTest \
        -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
        > "$output/gradle.log" 2>&1; then
        mkdir -p "$output/xml"
        cp -a native-app/app/build/outputs/androidTest-results/connected/. "$output/xml/" 2>/dev/null || true
        echo 'Android test failed. Relevant report:' >&2
        grep -E -m 25 -A 3 'failed|FAILED|Failure|Assertion|NoMatching|Exception' "$output/gradle.log" | tail -55 >&2 || true
        result="ANDROID_ASSERTION_FAILED_$scale"
        exit 1
    fi
    mkdir -p "$output/xml"
    cp -a native-app/app/build/outputs/androidTest-results/connected/. "$output/xml/"
done

# Keep image evidence even when the final validation fails.
adb pull "$DEVICE" "$DEST/captured" > "$DEST/pull-final.log" 2>&1 || true
python3 - <<'PY'
from pathlib import Path
from xml.etree import ElementTree as ET
root = Path('qa-evidence/noaa-coral')
cases=[]
for scale in ('1.0','2.0'):
    report_dir=root/f'font-{scale}'/'xml'
    reports=list(report_dir.rglob('TEST-*.xml'))
    assert reports, ('NO_XML_AT_SCALE', scale)
    picked=[case for path in reports for case in ET.parse(path).getroot().iter('testcase')
        if case.get('classname')=='com.gasczoology.invertebratelab.NativeNoaaCoralBookTest']
    assert len(picked)==2,('EXPECTED_TWO_TEST_CASES',scale,len(picked))
    cases.extend(picked)
bad=[(c.get('name'),tag) for c in cases for tag in ('failure','error','skipped') if c.find(tag) is not None]
assert not bad,bad
photos=list((root/'captured').rglob('coral-*.png'))
assert len(photos)==8,('EXPECTED_8_SCREENSHOTS',[p.name for p in photos])
assert all(p.stat().st_size>10000 for p in photos)
print('NOAA_ORIGINAL_CORAL_ANDROID_ENGLISH_TAMIL_100_200_PASS')
PY
result="PASSED_BOTH_SCALES"
