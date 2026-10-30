#!/usr/bin/env bash
set -euo pipefail
mkdir -p qa-evidence/unit1-png
for scale in 1.0 2.0; do
  adb shell settings put system font_scale "$scale"
  if ! gradle -p native-app :app:connectedDebugAndroidTest "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnitIProtozoaReferenceTest" > "qa-evidence/unit1-png/gradle-$scale.txt" 2>&1; then
    tail -n 180 "qa-evidence/unit1-png/gradle-$scale.txt"
    cp -r native-app/app/build/outputs/androidTest-results/connected "qa-evidence/unit1-png/xml-$scale" || true
    exit 1
  fi
  cp -r native-app/app/build/outputs/androidTest-results/connected "qa-evidence/unit1-png/xml-$scale" || true
done
adb pull /sdcard/Download/u12-reference-qa qa-evidence/unit1-png/screenshots || true
