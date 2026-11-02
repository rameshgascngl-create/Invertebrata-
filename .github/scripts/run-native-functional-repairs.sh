#!/usr/bin/env bash
set -euo pipefail
variant="$1"
configuration="$2"
language=ENGLISH
scale=1.0
orientation=portrait
case "$configuration" in
  english100) ;;
  tamil100) language=TAMIL ;;
  tamil200) language=TAMIL; scale=2.0 ;;
  tamil200landscape) language=TAMIL; scale=2.0; orientation=landscape ;;
  *) exit 2 ;;
esac
mkdir -p qa-evidence/functional
finish() {
  code=$?
  trap - EXIT
  adb logcat -d -v threadtime > qa-evidence/functional/logcat.txt 2>&1 || true
  adb pull /sdcard/Download/native-functional-evidence qa-evidence/functional/screenshots >/dev/null 2>&1 || true
  cp -R native-app/app/build/outputs/androidTest-results qa-evidence/functional/ 2>/dev/null || true
  cp -R native-app/app/build/reports/androidTests qa-evidence/functional/ 2>/dev/null || true
  printf 'commit=%s\nvariant=%s\nconfiguration=%s\nexit=%s\n' "$GITHUB_SHA" "$variant" "$configuration" "$code" > qa-evidence/functional/result.txt
  exit "$code"
}
trap finish EXIT
adb shell rm -rf /sdcard/Download/native-functional-evidence
adb shell mkdir -p /sdcard/Download/native-functional-evidence
adb shell settings put system font_scale "$scale"
adb shell svc wifi disable
adb shell svc data disable
adb shell settings get system font_scale > qa-evidence/functional/font-scale.txt
properties=()
case "$variant" in
  debug) task=connectedDebugAndroidTest; apkdir=debug ;;
  release) task=connectedReleaseAndroidTest; apkdir=release; properties+=(-PfunctionalRelease=true) ;;
  *) exit 2 ;;
esac
gradle -p native-app ":app:$task" "${properties[@]}" --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeFunctionalRepairsTest \
  -Pandroid.testInstrumentationRunnerArguments.functionalLanguage="$language" \
  -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
  -Pandroid.testInstrumentationRunnerArguments.functionalOrientation="$orientation"
cp native-app/app/build/outputs/apk/"$apkdir"/*.apk qa-evidence/functional/
(cd qa-evidence/functional && sha256sum *.apk > APK-SHA256.txt)
if [[ "$variant" == release ]]; then
  python3 - <<'PY'
import pathlib,zipfile
apk=next(pathlib.Path('qa-evidence/functional').glob('*.apk'))
with zipfile.ZipFile(apk) as archive:
    assert not any(n.startswith(('assets/review_only/', 'assets/paramecium/fission/')) for n in archive.namelist())
PY
fi
