#!/usr/bin/env bash
set -eu

expected_scale=$1
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
set +e
timeout --signal=TERM --kill-after=30s 42m gradle -p native-app :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.expectedScale="$expected_scale" --stacktrace --console=plain > native-app/app/build/qa-evidence/instrumentation-console.log 2>&1
test_status=$?
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
