#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/u58-reader"
mkdir -p "$E" "$E/captured"
adb shell mkdir -p /sdcard/Download/u12-reference-qa
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull /sdcard/Download/u57-qa "$E/captured/u57" > "$E/u57-pull.log" 2>&1 || true; adb pull /sdcard/Download/u58-qa "$E/captured/u58" > "$E/u58-pull.log" 2>&1 || true; adb pull /sdcard/Download/u12-reference-qa "$E/captured/u12" > "$E/u12-pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
adb shell mkdir -p /sdcard/Download/u57-qa /sdcard/Download/u58-qa
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0; do
    adb shell settings put system font_scale "$scale"
    actual="$(adb shell settings get system font_scale | tr -d '\r')"
    [[ "$actual" == "$scale" ]] || { result="FONT_SCALE_INVALID_$actual";exit 1; }
    for suite in NativeU57ParameciumProcessPngTest NativeU58ParameciumReaderPngTest NativeUnitIProtozoaReferenceTest#englishUnitOneDisplaysFourIndependentReferenceBitmaps; do
        result="RUNNING_$scale-$suite"
        root="$E/scale-$scale/${suite%%#*}"
        mkdir -p "$root"
        adb shell dumpsys connectivity > "$root/network.txt"
        rm -rf native-app/app/build/outputs/androidTest-results/connected
        if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
           "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.$suite" \
           > "$root/gradle.txt" 2>&1; then
            cp -a native-app/app/build/outputs/androidTest-results/connected "$root/xml" 2>/dev/null || true
            result="ANDROID_UI_FAIL_$scale-$suite"
            tail -n 100 "$root/gradle.txt" || true
            exit 1
        fi
        cp -a native-app/app/build/outputs/androidTest-results/connected "$root/xml"
        python3 - "$root/xml" "$suite" <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET
import sys
files=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
cases=[t for p in files for t in ET.parse(p).getroot().iter("testcase")]
assert len(cases)>=1,("No instrumentation test cases",len(cases))
assert all(c.get("classname")=="com.gasczoology.invertebratelab."+sys.argv[2].split("#")[0] for c in cases)
assert all(c.find(x) is None for c in cases for x in ("failure","error","skipped"))
print("UNIT1_OFFLINE_TEST=PASS",sys.argv[2])
PY
    done
done
adb pull /sdcard/Download/u57-qa "$E/captured/u57" > "$E/u57-pull-final.log"
adb pull /sdcard/Download/u58-qa "$E/captured/u58" > "$E/u58-pull-final.log"
adb pull /sdcard/Download/u12-reference-qa "$E/captured/u12" > "$E/u12-pull-final.log"
for scale in 100 200; do
    for view in vacuole-source-png vacuole-png-enlarged conjugation-text-no-vector; do
        [[ -s "$E/captured/u57/paramecium-$scale-$view.png" ]] || { result="MISSING_U57_$scale-$view";exit 1;}
    done
    for phase in liver blood mosquito; do
        [[ -s "$E/captured/u12/u12-$scale-plasmodium-$phase-english.png" ]] || { result="MISSING_PROTOZOA_$scale-$phase";exit 1;}
    done
    for view in external-png-fullscreen water-balance-png-fullscreen other-lesson-no-duplicate-image fission-candidate-png-fullscreen; do
        [[ -s "$E/captured/u58/reader-$scale-$view.png" ]] || { result="MISSING_U58_$scale-$view";exit 1;}
    done
done
result=PASS
