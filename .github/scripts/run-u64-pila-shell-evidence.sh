#!/usr/bin/env bash
set -euo pipefail
bash .github/scripts/run-u61-unit5-bilingual.sh

for font in 100 200; do
  for lang in english tamil; do
    photo="qa-evidence/u61-unit5/captured-final/r2-${font}-unit5-pila-shell-museum-${lang}.png"
    [[ -s "$photo" ]] || {
      printf 'Missing Pila museum shell screenshot: %s\n' "$photo" >&2
      exit 1
    }
    bytes="$(wc -c < "$photo")"
    [[ "$bytes" -gt 30000 ]] || {
      printf 'Truncated Pila museum shell screenshot: %s (%s bytes)\n' "$photo" "$bytes" >&2
      exit 1
    }
    printf 'VERIFIED_PILA_SHELL_SCREENSHOT %s %s\n' "$photo" "$bytes"
  done
done
echo 'U64_PILA_SHELL_BILINGUAL_100_200_EVIDENCE_PASS'

# Separate targeted run: the U61 suite does not execute the recovered-Pila
# image test. Capture both original drawings at the normal Android font scale.
adb shell settings put system font_scale 1.0
mkdir -p qa-evidence/u61-unit5/recovered-pila-test
gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeFiveUnitMissingRasterCoverageTest#pilaShowsBothRecoveredDrawingsButDoesNotClaimFourAcceptedAnatomyPlates \
  > qa-evidence/u61-unit5/recovered-pila-test/gradle.log 2>&1
cp -a native-app/app/build/outputs/androidTest-results/connected \
  qa-evidence/u61-unit5/recovered-pila-test/xml
adb pull /sdcard/Download/five-unit-qa \
  qa-evidence/u61-unit5/recovered-pila-test/captured
python3 - <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET
root=Path("qa-evidence/u61-unit5/recovered-pila-test")
cases=[c for p in (root/"xml").rglob("TEST-*.xml")
       for c in ET.parse(p).getroot().iter("testcase")
       if c.get("name")=="pilaShowsBothRecoveredDrawingsButDoesNotClaimFourAcceptedAnatomyPlates"]
assert len(cases)==1,("RECOVERED_PILA_TEST_NOT_EXECUTED",len(cases))
assert all(c.find(tag) is None for tag in ("failure","error","skipped") for c in cases)
for stem in ("04_animal_front","08_respiratory_system"):
    matches=list((root/"captured").rglob(f"r3-100-u5-pila-recovered-{stem}.png"))
    assert len(matches)==1,("RECOVERED_PILA_SCREENSHOT_MISSING",stem,matches)
    assert matches[0].stat().st_size>12000,("RECOVERED_PILA_SCREENSHOT_TRUNCATED",stem)
    print("RECOVERED_PILA_SCREENSHOT_PASS",stem,matches[0].stat().st_size)
PY
echo 'U64_PILA_RECOVERED_DRAWINGS_ANDROID_EVIDENCE_PASS'
