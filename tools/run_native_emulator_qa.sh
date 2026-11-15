#!/usr/bin/env bash
set -eu

expected_scale=$1
suite=${2:-full}
test_filter=()
case "$suite" in
  full) test_timeout=42m ;;
  rotation)
    test_timeout=6m
    test_filter+=('-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeUiAcceptanceTest#tamilAnswerAndQuestionPositionSurviveRotation')
    ;;
  reader)
    test_timeout=8m
    test_filter+=('-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.NativeReleaseFunctionalTest#defaultPagesAcrossAllFiveUnitsKeepPositionContentsAndExactAnswers')
    ;;
  *) echo "Unknown emulator test suite: $suite" >&2; exit 2 ;;
esac
mkdir -p native-app/app/build/qa-evidence
adb version > native-app/app/build/qa-evidence/adb-before-tests.txt 2>&1
adb devices -l >> native-app/app/build/qa-evidence/adb-before-tests.txt 2>&1
ss -ltnp '( sport = :5037 )' >> native-app/app/build/qa-evidence/adb-before-tests.txt 2>&1 || true
if ! timeout 20s adb -s emulator-5554 get-state | grep -qx device; then
  echo "ADB device unavailable; attempting one bounded server restart" >&2
  timeout 15s adb kill-server || true
  timeout 20s adb start-server
  timeout 30s adb -s emulator-5554 wait-for-device
fi
timeout 20s adb -s emulator-5554 get-state | grep -qx device
boot_ok=0
for attempt in $(seq 1 30); do
  if [ "$(timeout 10s adb -s emulator-5554 shell getprop sys.boot_completed | tr -d '\r')" = "1" ]; then
    boot_ok=1
    break
  fi
  sleep 5
done
if [ "$boot_ok" -ne 1 ]; then
  echo "Emulator failed to reach sys.boot_completed=1" >&2
  exit 1
fi
timeout 30s adb -s emulator-5554 shell getprop ro.build.version.sdk > native-app/app/build/qa-evidence/device-api.txt
timeout 20s adb -s emulator-5554 shell cmd package resolve-activity --brief \
  -a android.intent.action.MAIN -c android.intent.category.HOME \
  > native-app/app/build/qa-evidence/home-launcher.txt 2>&1 || true
timeout 20s adb -s emulator-5554 shell settings put system font_scale "$expected_scale"
timeout 20s adb -s emulator-5554 shell setprop persist.sys.locale ta-IN || true
timeout 20s adb -s emulator-5554 shell am broadcast -a android.intent.action.LOCALE_CHANGED || true
timeout 20s adb -s emulator-5554 shell settings get system font_scale > native-app/app/build/qa-evidence/font-scale.txt
actual_scale="$(tr -d '\r\n' < native-app/app/build/qa-evidence/font-scale.txt)"
if [ "$actual_scale" != "$expected_scale" ]; then
  echo "Font scale mismatch: requested $expected_scale, observed $actual_scale" >&2
  exit 1
fi
{
  echo "requested_locale=ta-IN"
  echo "persist.sys.locale=$(timeout 20s adb -s emulator-5554 shell getprop persist.sys.locale || true)"
  echo "persist.sys.language=$(timeout 20s adb -s emulator-5554 shell getprop persist.sys.language || true)"
  echo "system_locales=$(timeout 20s adb -s emulator-5554 shell settings get system system_locales || true)"
  echo "ro.product.locale=$(timeout 20s adb -s emulator-5554 shell getprop ro.product.locale || true)"
  echo "NOTE: These diagnostics do not establish effective application locale; instrumentation must assert Tamil rendering."
} > native-app/app/build/qa-evidence/locale-preflight.txt
# Acceptance tests save real device captures under these shared folders.
# Prepare the paths used by older tests and include their PNGs in CI evidence.
screenshot_root=/sdcard/Download
screenshot_folders=(
  five-unit-qa native-anatomy-evidence native-functional-evidence noaa-coral-qa
  pila-biology-qa u1-diagram-qa u1-intro-evidence u12-reference-qa u57-qa
  u60-qa unit2-qa unit23-editorial-qa unit3-qa unit4-qa unit45-gallery-qa
)
screenshot_paths=()
for folder in "${screenshot_folders[@]}"; do
  screenshot_paths+=("$screenshot_root/$folder")
done
timeout 30s adb -s emulator-5554 shell mkdir -p "${screenshot_paths[@]}"

# The stock emulator launcher can leave its boot-time ANR dialog above our app.
# Only recover the resolved HOME process. Leave app and other system ANRs visible.
launcher=$(tr -d '\r' < native-app/app/build/qa-evidence/home-launcher.txt |
  sed -nE 's|^([[:alnum:]_]+(\.[[:alnum:]_]+)+)/[^[:space:]]+$|\1|p' | tail -1)
launcher_watch_pid=
if [[ -n "$launcher" && "$launcher" != com.gasczoology.invertebratelab ]]; then
  (
    for attempt in {1..30}; do
      if timeout 8s adb -s emulator-5554 shell dumpsys window windows |
        grep -Fq "Application Not Responding: $launcher}"; then
        echo "Detected emulator HOME ANR for $launcher"
        timeout 10s adb -s emulator-5554 shell am force-stop --user 0 "$launcher"
        timeout 8s adb -s emulator-5554 shell dumpsys window windows > native-app/app/build/qa-evidence/launcher-recovery-windows.txt
        break
      fi
      sleep 2
    done
  ) > native-app/app/build/qa-evidence/launcher-recovery.txt 2>&1 &
  launcher_watch_pid=$!
fi

set +e
timeout --signal=TERM --kill-after=30s "$test_timeout" gradle -p native-app :app:connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.expectedScale="$expected_scale" \
  -Pandroid.testInstrumentationRunnerArguments.r161Scale="$expected_scale" \
  "${test_filter[@]}" --stacktrace --console=plain \
  > native-app/app/build/qa-evidence/instrumentation-console.log 2>&1
test_status=$?
if [[ -n "$launcher_watch_pid" ]]; then
  kill "$launcher_watch_pid" 2>/dev/null || true
  wait "$launcher_watch_pid" 2>/dev/null || true
fi
evidence_dir=native-app/app/build/qa-evidence/reader-screenshots
mkdir -p "$evidence_dir"
for path in "${screenshot_paths[@]}"; do
  if ! timeout 10s adb -s emulator-5554 pull "$path" "$evidence_dir/" >> "$evidence_dir/adb-pull.log" 2>&1; then
    echo "Screenshot pull failed: $path" >&2
  fi
done
cat native-app/app/build/qa-evidence/instrumentation-console.log
timeout --signal=TERM --kill-after=5s 35s adb -s emulator-5554 logcat -d -v threadtime > native-app/app/build/qa-evidence/logcat.txt 2>&1 || true
timeout 20s adb -s emulator-5554 shell getprop persist.sys.locale > native-app/app/build/qa-evidence/locale.txt 2>&1 || true
timeout 25s adb -s emulator-5554 exec-out screencap -p > native-app/app/build/qa-evidence/post-test-screen.png 2>/dev/null || true
if [ ! -s native-app/app/build/qa-evidence/post-test-screen.png ]; then
  rm -f native-app/app/build/qa-evidence/post-test-screen.png
  echo "Post-test diagnostic screenshot unavailable" >&2
fi
# Launch the actual native app after instrumentation for a labelled home-screen capture.
# This is visual evidence only; it does not certify Tamil locale or lesson screens.
if timeout 20s adb -s emulator-5554 shell pm path com.gasczoology.invertebratelab | grep -q 'package:'; then
  timeout 20s adb -s emulator-5554 shell am force-stop com.gasczoology.invertebratelab || true
  if timeout 25s adb -s emulator-5554 shell am start -n com.gasczoology.invertebratelab/.MainActivity > native-app/app/build/qa-evidence/home-launch.txt 2>&1; then
    sleep 5
    timeout 25s adb -s emulator-5554 exec-out screencap -p > native-app/app/build/qa-evidence/native-home-screen.png 2>/dev/null || true
    if [ ! -s native-app/app/build/qa-evidence/native-home-screen.png ]; then
      rm -f native-app/app/build/qa-evidence/native-home-screen.png
      echo "Native home screenshot unavailable" >&2
    fi
  fi
else
  echo "Native package not installed; home screenshot unavailable" > native-app/app/build/qa-evidence/home-launch.txt
fi
echo "Instrumentation Gradle exit code: $test_status"
exit "$test_status"
