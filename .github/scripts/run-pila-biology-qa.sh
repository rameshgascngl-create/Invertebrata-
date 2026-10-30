#!/usr/bin/env bash
set -euo pipefail
DEST="qa-evidence/pila-biology"
DEVICE="/sdcard/Download/pila-biology-qa"
mkdir -p "$DEST"
scale="none"
result="NOT_STARTED"
collect() {
  code=$?
  printf 'sha=%s\nfont_scale=%s\nresult=%s\nexit=%s\n' "${GITHUB_SHA:-unknown}" "$scale" "$result" "$code" > "$DEST/status.txt"
  adb pull "$DEVICE" "$DEST/captured" > "$DEST/screenshots-pull.txt" 2>&1 || true
  adb logcat -d -v threadtime > "$DEST/logcat.txt" 2>&1 || true
  find "$DEST" -name '*.png' -print0 | xargs -0 -r sha256sum > "$DEST/SHA256SUMS"
}
trap collect EXIT
for n in $(seq 1 45); do
  if adb shell mkdir -p "$DEVICE" >/dev/null 2>&1; then break; fi
  sleep 2
done
adb shell mkdir -p "$DEVICE"
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0; do
  result="ANDROID_TESTS_$scale"
  out="$DEST/font-$scale"
  mkdir -p "$out"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { result="BAD_FONT_SCALE_$scale"; exit 1; }
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
     -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativePilaBiologyTextbookTest \
     -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
     > "$out/gradle.log" 2>&1; then
    mkdir -p "$out/xml"
    cp -a native-app/app/build/outputs/androidTest-results/connected/. "$out/xml/" 2>/dev/null || true
    result="ANDROID_FAILURE_$scale"
    grep -E -m 25 -A 3 'FAILED|failed|Assertion|NoMatching|Exception' "$out/gradle.log" | tail -60 >&2 || true
    exit 1
  fi
  mkdir -p "$out/xml"
  cp -a native-app/app/build/outputs/androidTest-results/connected/. "$out/xml/"
done
adb pull "$DEVICE" "$DEST/captured" > "$DEST/screenshots-pull.txt"
python3 - <<'PY'
from pathlib import Path
from xml.etree import ElementTree as ET
root=Path("qa-evidence/pila-biology")
for scale in ("1.0","2.0"):
    reports=list((root/f"font-{scale}"/"xml").rglob("TEST-*.xml"))
    assert reports, ("NO_XML",scale)
    cases=[c for p in reports for c in ET.parse(p).getroot().iter("testcase")
        if c.get("classname") == "com.gasczoology.invertebratelab.NativePilaBiologyTextbookTest"]
    assert len(cases)==2,("EXPECTED_TWO_TESTS_PER_FONT",scale,len(cases))
    assert all(c.find(k) is None for c in cases for k in ("failure","error","skipped"))
expected={f"pila-{lang}-{font}-{part}.png"
    for lang in ("english","tamil") for font in ("100","200")
    for part in ("egg-laying","operculum","torsion")}
shots=list((root/"captured").rglob("pila-*.png"))
actual={p.name for p in shots}
assert expected==actual,("MISSING",sorted(expected-actual),"UNEXPECTED",sorted(actual-expected))
assert all(p.stat().st_size>10000 for p in shots)
print("PILA_BILINGUAL_EGGS_OPERCULUM_TORSION_100_200_PASS")
PY
result="PASS"
