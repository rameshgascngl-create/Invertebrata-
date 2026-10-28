#!/usr/bin/env bash
set -euo pipefail

E="qa-evidence/unit1-intro"
D="/sdcard/Download/u1-intro-evidence"
mkdir -p "$E"
result="NOT_STARTED"
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull "$D" "$E/captured" > "$E/pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT

for attempt in $(seq 1 60); do
  if adb shell mkdir -p "$D" >/dev/null 2>&1; then break; fi
  sleep 2
done
adb shell mkdir -p "$D"
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable

for scale in 1.0 2.0; do
  result="TESTING_FONT_$scale"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { result="FONT_SCALE_MISMATCH_$scale"; exit 1; }
  F="$E/font-$scale"
  mkdir -p "$F"
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnitIIntroductionBookRenderingTest,com.gasczoology.invertebratelab.NativeUnitIRevisedChaptersRenderingTest \
    -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
    > "$F/gradle.log" 2>&1; then
    cp -a native-app/app/build/outputs/androidTest-results/connected "$F/xml" 2>/dev/null || true
    result="ANDROID_TEST_FAILURE_$scale"
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$F/xml"
  python3 - "$F/xml" <<'PY'
from pathlib import Path
import sys, xml.etree.ElementTree as ET
expected={"com.gasczoology.invertebratelab.NativeUnitIIntroductionBookRenderingTest",\n          "com.gasczoology.invertebratelab.NativeUnitIRevisedChaptersRenderingTest"}
xml=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
cases=[c for p in xml for c in ET.parse(p).getroot().iter("testcase")
       if c.get("classname") in expected]
assert len(cases)==8,("EXPECTED_EIGHT_BILINGUAL_TESTS",len(cases))
errors=[(c.get("name"),kind) for c in cases for kind in ("failure","error","skipped")
        if c.find(kind) is not None]
assert not errors,errors
print("UNIT1_ALL_REVISED_CHAPTER_BILINGUAL_TESTS=PASS",len(cases))
PY
done

adb pull "$D" "$E/captured-final" > "$E/pull-final.log"
for scale in 100 200; do
  for language in english tamil; do
    for scene in introduction first-section last-section; do
      name="u1-intro-$language-$scale-$scene.png"
      [[ -s "$E/captured-final/$name" ]] || {
        result="MISSING_SCREENSHOT_$name"
        exit 1
      }
    done
  done
done
find "$E/captured-final" -name '*.png' -type f -print0 | xargs -0 -r sha256sum > "$E/screenshot-sha256.txt"
result="API34_ENGLISH_TAMIL_100_200_PASS_ACADEMIC_RELEASE_HOLD"
