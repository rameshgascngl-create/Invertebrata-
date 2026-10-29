#!/usr/bin/env bash
set -euo pipefail

DEST="qa-evidence/unit45-gallery"
DEVICE="/sdcard/Download/unit45-gallery-qa"
mkdir -p "$DEST"
state="NOT_STARTED"
scale="none"
collect() {
  code=$?
  printf 'commit=%s\nstate=%s\nscale=%s\nexit=%s\n' "${GITHUB_SHA:-unknown}" "$state" "$scale" "$code" > "$DEST/status.txt"
  adb pull "$DEVICE" "$DEST/captured" > "$DEST/screenshots-pull.txt" 2>&1 || true
  adb logcat -d -v threadtime > "$DEST/logcat.txt" 2>&1 || true
  find "$DEST" -name '*.png' -print0 | xargs -0 -r sha256sum > "$DEST/SHA256SUMS"
}
trap collect EXIT
for attempt in $(seq 1 45); do
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
  state="RUNNING_$scale"
  location="$DEST/font-$scale"
  mkdir -p "$location"
  adb shell settings put system font_scale "$scale"
  actual="$(adb shell settings get system font_scale | tr -d '\r')"
  [[ "$actual" == "$scale" ]] || { state="FONT_SCALE_MISMATCH"; exit 1; }
  ready=0
  for n in $(seq 1 45); do
    adb shell dumpsys connectivity > "$location/connectivity.txt"
    if grep -Eq '^Active default network: none$' "$location/connectivity.txt"; then ready=1; break; fi
    sleep 1
  done
  [[ "$ready" == 1 ]] || { state="NOT_OFFLINE_$scale"; exit 1; }
  rm -rf native-app/app/build/outputs/androidTest-results/connected
  if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
      -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUnit45GalleryAccessTest \
      -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
      > "$location/gradle.log" 2>&1; then
      mkdir -p "$location/xml"
      cp -a native-app/app/build/outputs/androidTest-results/connected/. "$location/xml/" 2>/dev/null || true
      state="UI_FAILURE_$scale"
      grep -E -m 20 -A 3 'failed|FAILED|Assertion|NoMatching|Exception' "$location/gradle.log" | tail -70 >&2 || true
      exit 1
  fi
  mkdir -p "$location/xml"
  cp -a native-app/app/build/outputs/androidTest-results/connected/. "$location/xml/"
done
adb pull "$DEVICE" "$DEST/captured" > "$DEST/screenshots-pull.txt"
python3 - <<'PY'
from pathlib import Path
from xml.etree import ElementTree as ET
root=Path("qa-evidence/unit45-gallery")
for scale in ("1.0","2.0"):
    reports=list((root/f"font-{scale}"/"xml").rglob("TEST-*.xml"))
    assert reports,("MISSING_XML",scale)
    cases=[c for f in reports for c in ET.parse(f).getroot().iter("testcase")
           if c.get("classname")=="com.gasczoology.invertebratelab.NativeUnit45GalleryAccessTest"]
    assert len(cases)==6,("EXPECTED_6_CASES",scale,len(cases))
    assert all(c.find(t) is None for c in cases for t in ("failure","error","skipped"))
expected={f"u{unit}-{org}-{lang}-{scale}-{phase}.png"
          for unit,org in ((4,"penaeus"),(5,"pila"),(5,"asterias"))
          for lang in ("english","tamil")
          for scale in ("100","200")
          for phase in ("textbook","gallery","restored-practice")}
pictures=list((root/"captured").rglob("*.png"))
actual={p.name for p in pictures}
assert actual==expected,("SCREENSHOTS_MISSING",sorted(expected-actual),"UNEXPECTED",sorted(actual-expected))
assert all(p.stat().st_size>10000 for p in pictures)
print("UNIT_IV_V_12_ANDROID_CASES_36_SCREENSHOTS_PASS")
PY
state="PASS"
