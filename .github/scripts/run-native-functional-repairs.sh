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
logcat_pid=""
finish() {
  code=$?
  trap - EXIT
  if [[ -n "$logcat_pid" ]]; then kill "$logcat_pid" 2>/dev/null || true; wait "$logcat_pid" 2>/dev/null || true; fi
  adb logcat -d -v threadtime > qa-evidence/functional/logcat.txt 2>&1 || true
  adb shell dumpsys activity activities > qa-evidence/functional/activities.txt 2>&1 || true
  adb shell dumpsys activity instrumentation > qa-evidence/functional/instrumentation.txt 2>&1 || true
  adb pull /sdcard/Download/native-functional-evidence qa-evidence/functional/screenshots >/dev/null 2>&1 || true
  cp -R native-app/app/build/outputs/androidTest-results qa-evidence/functional/ 2>/dev/null || true
  cp -R native-app/app/build/reports/androidTests qa-evidence/functional/ 2>/dev/null || true
  printf 'commit=%s\nvariant=%s\nconfiguration=%s\nexit=%s\n' "$GITHUB_SHA" "$variant" "$configuration" "$code" > qa-evidence/functional/result.txt
  exit "$code"
}
trap finish EXIT
trap 'exit 130' INT
trap 'exit 143' TERM
bash .github/scripts/wait-native-external-storage.sh
adb shell rm -rf /sdcard/Download/native-functional-evidence
adb shell mkdir -p /sdcard/Download/native-functional-evidence
adb shell settings put system font_scale "$scale"
adb shell svc wifi disable
adb shell svc data disable
adb shell settings get system font_scale > qa-evidence/functional/font-scale.txt
properties=()
case "$variant" in
  debug) task=connectedDebugAndroidTest; apkdir=debug; test_class=com.gasczoology.invertebratelab.NativeFunctionalRepairsTest ;;
  release) task=connectedReleaseAndroidTest; apkdir=release; test_class=com.gasczoology.invertebratelab.NativeReleaseFunctionalTest; properties+=(-PfunctionalRelease=true) ;;
  *) exit 2 ;;
esac
# Preserve the tested application and runner even when instrumentation never returns.
build_variant="${variant^}"
gradle -p native-app ":app:assemble$build_variant" ":app:assemble${build_variant}AndroidTest" "${properties[@]}" --stacktrace
cp native-app/app/build/outputs/apk/"$apkdir"/*.apk qa-evidence/functional/
cp native-app/app/build/outputs/apk/androidTest/"$apkdir"/*.apk qa-evidence/functional/
(cd qa-evidence/functional && sha256sum *.apk > APK-SHA256.txt)
adb logcat -c
adb logcat -v threadtime > qa-evidence/functional/logcat-live.txt 2>&1 &
logcat_pid="$!"
adb install -r -t "qa-evidence/functional/app-$variant.apk" > qa-evidence/functional/install-target.txt 2>&1
timeout --signal=TERM --kill-after=15s 30s adb shell am start -W -n com.gasczoology.invertebratelab/.MainActivity > qa-evidence/functional/launch.txt 2>&1
# Three functional methods complete in under a minute in the debug matrix.
# A bounded invocation captures release-runner stalls instead of waiting 50 minutes.
# At 200% Tamil landscape the release R8 suite completed its first two real
# instrumentation methods, then hit the 540s outer guard during the third.
# This is a runner time budget, not an assertion failure. Keep ALL original
# tests intact and give only that configuration sufficient completion time.
instrumentation_limit=540
if [[ "$variant" == release && "$configuration" == tamil200landscape ]]; then
  instrumentation_limit=780
fi
timeout --signal=TERM --kill-after=15s "${instrumentation_limit}s" gradle -p native-app ":app:$task" "${properties[@]}" --stacktrace --info \
  -Pandroid.testInstrumentationRunnerArguments.class="$test_class" \
  -Pandroid.testInstrumentationRunnerArguments.functionalLanguage="$language" \
  -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
  -Pandroid.testInstrumentationRunnerArguments.functionalOrientation="$orientation" 2>&1 | tee qa-evidence/functional/instrumentation-gradle.txt
if [[ "$variant" == debug ]]; then
  # Extra classes run after the established one, so those tests run on an untouched emulator.
  # Gradle replaces its results directory on each run: keep every earlier set.
  cp -R native-app/app/build/outputs/androidTest-results/connected qa-evidence/functional/connected-established
  for extra in NativeReaderContinuityTest:continuity NativeMcqPracticeTest:mcq; do
    [[ "$extra" == NativeMcqPracticeTest:mcq ]] && cp -R native-app/app/build/outputs/androidTest-results/connected qa-evidence/functional/connected-continuity
    timeout --signal=TERM --kill-after=15s 540s gradle -p native-app ":app:$task" --stacktrace --info \
      -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.${extra%%:*} \
      -Pandroid.testInstrumentationRunnerArguments.functionalLanguage="$language" \
      -Pandroid.testInstrumentationRunnerArguments.expectedScale="$scale" \
      -Pandroid.testInstrumentationRunnerArguments.functionalOrientation="$orientation" 2>&1 | tee qa-evidence/functional/instrumentation-gradle-${extra##*:}.txt
  done
fi
python3 - "$test_class" "$variant" <<'PYTEST'
import pathlib, sys, xml.etree.ElementTree as ET
expected={
    "defaultPagesAcrossAllFiveUnitsKeepPositionContentsAndExactAnswers",
    "allFiveSimulatorsMoveRealDiagramPixelsAndPause",
    "parasiteAndPrawnInteractivePagesAreReachable",
}
established="com.gasczoology.invertebratelab.NativeFunctionalRepairsTest"
continuity="com.gasczoology.invertebratelab.NativeReaderContinuityTest"
mcq="com.gasczoology.invertebratelab.NativeMcqPracticeTest"
# The established class runs first, on an untouched emulator; the reader-continuity class runs after it.
expected={(established, n) for n in expected}
if sys.argv[2]=="debug":
    expected|={(continuity, n) for n in (
        "chapterSearchOpensTheMatchingBlockAndBookmarksPersist",
        "switchingLanguageKeepsTheSemanticPage",
        "figureViewerZoomsSurvivesRecreationAndReturnsToTheSamePage",
    )} | {(mcq, n) for n in (
        "fullRunScoresShowsExplanationsAndRetriesOnlyTheMissedQuestion",
        "positionAndSelectionSurviveRecreation",
        "anEmptyBankShowsNoQuiz",
    )}
else:
    expected={(sys.argv[1], n) for _, n in expected}
roots=['native-app/app/build/outputs/androidTest-results/connected']
if sys.argv[2]=="debug": roots += ['qa-evidence/functional/connected-established', 'qa-evidence/functional/connected-continuity']
cases=[case for root in roots for path in pathlib.Path(root).rglob('TEST-*.xml')
       for case in ET.parse(path).getroot().iter('testcase')]
actual=[(case.get('classname'), case.get('name')) for case in cases]
assert len(cases)==len(expected) and set(actual)==expected, ("WRONG_FUNCTIONAL_COHORT", actual)
assert not any(case.find(tag) is not None for case in cases for tag in ('failure','error','skipped')), "NONPASS_FUNCTIONAL_TEST"
print("VERIFIED_FUNCTIONAL_COHORT", len(cases), sorted(n for _, n in actual))
PYTEST
cp native-app/app/build/outputs/apk/"$apkdir"/*.apk qa-evidence/functional/
(cd qa-evidence/functional && sha256sum *.apk > APK-SHA256.txt)
if [[ "$variant" == release ]]; then
  python3 - <<'PY'
import pathlib,zipfile
apks=[p for p in pathlib.Path('qa-evidence/functional').glob('*.apk') if not p.name.endswith('-androidTest.apk')]
assert len(apks)==1, apks
apk=apks[0]
with zipfile.ZipFile(apk) as archive:
    assert not any(n.startswith(('assets/review_only/', 'assets/paramecium/fission/')) for n in archive.namelist())
PY
fi
