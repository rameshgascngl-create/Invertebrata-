#!/usr/bin/env bash
set -euo pipefail
evidence="qa-evidence/r19-quality"
device_dir="/sdcard/Download/native-anatomy-evidence"
selected_test="com.gasczoology.invertebratelab.NativeR19TeachingAcceptanceTest"
# AndroidJUnitRunner annotation filtering selects an explicit method cohort.
# Never trust a green runner result without the exact expected XML cohort below.
quality_args=()
quality_scope="${1:-all}"
if [[ "$quality_scope" == "physiology" ]]; then
  quality_args=(-Pandroid.testInstrumentationRunnerArguments.annotation=com.gasczoology.invertebratelab.R19PhysiologyGate)
elif [[ "$quality_scope" == "egestion" ]]; then
  selected_test+="#cytoproctReallyOpensReleasesResiduesAndReclosesWithAccessibleHits"
fi
verify_cohort() {
  python3 - "$1" "${2:-$quality_scope}" <<'PYTEST'
import sys,pathlib,xml.etree.ElementTree as E
scope=sys.argv[2]
core={"stagesAndRealStructureTapsAreVisibleAndAccessible","numberedReadingAndNarrationKeepCompleteWrittenExplanations","physiologicalViewsDistinguishExchangeFromEnergyMetabolismWithRealMotion","readerLinkAndActualRotationPreserveDetailedLearningPosition"}
full=core|{"actualFormationPixelsAndIntermediatePhaseSurviveRecreationAndNavigation","maturationStagesHaveDistinctNativeEndpointsAndRealIntermediateMotion","cytoproctReallyOpensReleasesResiduesAndReclosesWithAccessibleHits","leavingDuringPlaybackCheckpointsPhaseWithoutOverwritingNavigation"}
expected={"malformedNewKeysNormalizeWithoutResettingAcceptedFields","legacyR18PreferencesWithoutNutritionKeysRetainEveryEarlierAnchor"} if scope=="compatibility" else {"cytoproctReallyOpensReleasesResiduesAndReclosesWithAccessibleHits"} if scope=="egestion" else core if scope=="physiology" else full
cases=[c for p in pathlib.Path(sys.argv[1]).rglob('TEST-*.xml') for c in E.parse(p).getroot().iter('testcase')]
actual=[c.attrib['name'] for c in cases]
assert len(actual)==len(expected) and set(actual)==expected,("WRONG_TEST_COHORT",scope,sorted(expected),actual)
assert not any(c.find(n) is not None for c in cases for n in ('failure','error','skipped')),"NONPASS_TEST"
print("VERIFIED_COHORT",scope,len(actual),sorted(actual))
PYTEST
}
mkdir -p "$evidence"
adb shell mkdir -p "$device_dir"
phase=NOT_STARTED
result=NOT_STARTED
finish() {
  local code=$?
  printf 'result=%s\nphase=%s\nexit=%s\ncommit=%s\n' "$result" "$phase" "$code" "$GITHUB_SHA" > "$evidence/result.txt"
  sha256sum native-app/app/build/outputs/apk/debug/*.apk native-app/app/build/outputs/apk/androidTest/debug/*.apk > "$evidence/tested-job-apk-sha256.txt" 2>/dev/null || true
  adb logcat -d -v threadtime > "$evidence/logcat.txt" 2>&1 || true
  adb pull "$device_dir" "$evidence/screenshots" >/dev/null 2>&1 || true
  adb shell dumpsys meminfo com.gasczoology.invertebratelab > "$evidence/meminfo.txt" 2>&1 || true
  adb shell dumpsys accessibility > "$evidence/accessibility.txt" 2>&1 || true
}
trap finish EXIT
# Disable radios: this validates offline UI/fallback, never audible voice acceptance.
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
adb shell settings get global airplane_mode_on > "$evidence/airplane-setting.txt"
# Radio changes are asynchronous. Require actual ConnectivityService disconnection,
# not an airplane setting or a fixed sleep taken as proof of being offline.
offline_verified=0
for attempt in {1..30}; do
  adb shell dumpsys connectivity > "$evidence/connectivity-before.txt"
  printf 'attempt=%s ' "$attempt" >> "$evidence/offline-state-poll.txt"
  sed -n '/Active default network:/p' "$evidence/connectivity-before.txt" >> "$evidence/offline-state-poll.txt"
  if grep -q 'Active default network: none' "$evidence/connectivity-before.txt"; then
    offline_verified=1
    break
  fi
  sleep 1
done
[[ "$offline_verified" == 1 ]] || { result=OFFLINE_SETUP_FAIL; exit 1; }
adb shell cmd package query-services --brief -a android.intent.action.TTS_SERVICE > "$evidence/tts-services.txt" 2>&1 || true
for config in "ENGLISH 1.0 portrait" "TAMIL 1.0 portrait" "TAMIL 2.0 portrait" "TAMIL 2.0 landscape"; do
  read -r language scale orientation <<< "$config"
  phase="$language-scale-$scale-$orientation"
  mkdir -p "$evidence/$phase"
  adb shell settings put system font_scale "$scale"
  adb shell settings get system font_scale > "$evidence/font-scale-$scale.txt"
  adb shell mkdir -p /sdcard/Download/native-anatomy-evidence
  rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
  if gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
    -Pandroid.testInstrumentationRunnerArguments.class="$selected_test" "${quality_args[@]}" \
    -Pandroid.testInstrumentationRunnerArguments.r19Language="$language" \
    -Pandroid.testInstrumentationRunnerArguments.r19Orientation="$orientation" \
    -Pandroid.testInstrumentationRunnerArguments.r19Scale="$scale" > "$evidence/$phase/gradle.txt" 2>&1; then
    result=PASS
  else
    result=INSTRUMENTATION_FAIL
    cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/$phase/results" 2>/dev/null || true
    cp -a native-app/app/build/reports/androidTests/connected "$evidence/$phase/reports" 2>/dev/null || true
    exit 1
  fi
  cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/$phase/results"
  cp -a native-app/app/build/reports/androidTests/connected "$evidence/$phase/reports"
  if ! verify_cohort "$evidence/$phase/results" > "$evidence/$phase/verified-cohort.txt" 2>&1; then
    result=WRONG_TEST_COHORT
    exit 1
  fi
  adb shell dumpsys connectivity > "$evidence/$phase/connectivity-after.txt"
  grep -q 'Active default network: none' "$evidence/$phase/connectivity-after.txt" || { result=OFFLINE_LOST; exit 1; }
  adb logcat -d -v threadtime -s R19_NUTRITION:I '*:S' > "$evidence/$phase/nutrition-pixels.txt"
done
# A correction runs only its exact isolated cohort before the failed full gate.
if [[ "$quality_scope" == "egestion" ]]; then
  result=PASS
  exit 0
fi
phase="datastore-compatibility"
mkdir -p "$evidence/$phase"
rm -rf native-app/app/build/outputs/androidTest-results/connected native-app/app/build/reports/androidTests/connected
if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
  -Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeR19DataStoreCompatibilityTest \
  > "$evidence/$phase/gradle.txt" 2>&1; then
  result=INSTRUMENTATION_FAIL
  cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/$phase/results" 2>/dev/null || true
  cp -a native-app/app/build/reports/androidTests/connected "$evidence/$phase/reports" 2>/dev/null || true
  exit 1
fi
cp -a native-app/app/build/outputs/androidTest-results/connected "$evidence/$phase/results"
cp -a native-app/app/build/reports/androidTests/connected "$evidence/$phase/reports"
if ! verify_cohort "$evidence/$phase/results" compatibility > "$evidence/$phase/verified-cohort.txt" 2>&1; then
  result=WRONG_TEST_COHORT
  exit 1
fi
result=PASS
