#!/usr/bin/env bash
# Exact APK installation and real physical PID recovery. Human gates remain separate.
set -euo pipefail
if [[ $# != 5 ]]; then
  echo 'Usage: bash ci/r161_physical_device_qa.sh SERIAL APP_APK TEST_APK COMMIT_FILE EVIDENCE_DIR' >&2
  exit 2
fi
selectedDeviceSerial="$1"
app_apk="$(realpath "$2")"
test_apk="$(realpath "$3")"
commit_file="$(realpath "$4")"
out="$(realpath -m "$5")"
package=com.gasczoology.invertebratelab
runner="$package.test/androidx.test.runner.AndroidJUnitRunner"
commit="$(tr -d '\r\n' < "$commit_file")"
[[ "$commit" =~ ^[0-9a-f]{40}$ ]] || { echo 'Invalid commit identity' >&2; exit 1; }
[[ -s "$app_apk" && -s "$test_apk" ]] || exit 1
[[ "$(dirname "$app_apk")" == "$(dirname "$commit_file")" &&
   "$(dirname "$test_apk")" == "$(dirname "$commit_file")" &&
   "$(basename "$app_apk")" == app-debug.apk &&
   "$(basename "$test_apk")" == app-debug-androidTest.apk ]] || {
  echo 'APKs must be the exact pair beside the commit/checksum files' >&2; exit 1;
}
# The included checksum file is authoritative for the artifact pair.
(cd "$(dirname "$commit_file")" && sha256sum -c SHA256SUMS)
a() { adb -s "$selectedDeviceSerial" "$@"; }
a get-state
if [[ "$(a shell getprop ro.kernel.qemu | tr -d '\r')" == "1" ]]; then
  echo 'PENDING: PHYSICAL DEVICE REQUIRED; emulator rejected' >&2
  exit 1
fi
mkdir -p "$out"
printf 'commit=%s\nserial=%s\n' "$commit" "$selectedDeviceSerial" > "$out/identity.txt"
sha256sum "$app_apk" "$test_apk" > "$out/apk-hashes.txt"
a shell getprop > "$out/device-properties.txt"
a shell settings get system font_scale > "$out/font-scale.txt"
a install -r "$app_apk" > "$out/install-app.txt"
a install -r "$test_apk" > "$out/install-test.txt"
a shell dumpsys package "$package" > "$out/installed-package.txt"
# Setup classes intentionally establish QA fixtures on this dedicated test profile.
instrument() {
  a shell am instrument -w -r -e class "$1" "$runner" | tee "$2"
  grep -q 'INSTRUMENTATION_CODE: -1' "$2"
  grep -q 'OK (1 test)' "$2"
  if grep -Eq 'FAILURES!!!|INSTRUMENTATION_RESULT: shortMsg=|INSTRUMENTATION_FAILED' "$2"; then return 1; fi
}
for gate in n14 ciliary; do
  folder="$out/$gate"
  mkdir -p "$folder"
  if [[ "$gate" == n14 ]]; then
    setup="$package.NativeProcessDeathSetupTest"
    verify="$package.NativeProcessDeathVerifyTest"
  else
    setup="$package.NativeR161CiliaryProcessDeathSetupTest"
    verify="$package.NativeR161CiliaryProcessDeathVerifyTest"
  fi
  instrument "$setup" "$folder/setup.txt"
  a shell am start -W -a android.intent.action.MAIN -c android.intent.category.LAUNCHER \
    -n "$package/.MainActivity" > "$folder/initial-launch.txt"
  old_pid="$(a shell pidof -s "$package" | tr -d '\r')"
  [[ "$old_pid" =~ ^[0-9]+$ ]] || exit 1
  printf '%s\n' "$old_pid" > "$folder/original-pid.txt"
  a shell cmd package list packages -U --user 0 "$package" > "$folder/package-uid.txt"
  uid="$(awk -v pkg="$package" '$1=="package:"pkg {for(i=2;i<=NF;i++)if($i~/^uid:[0-9]+$/){sub(/^uid:/,"",$i);print $i;exit}}' "$folder/package-uid.txt")"
  [[ "$uid" =~ ^[0-9]+$ ]] || exit 1
  a shell dumpsys activity processes > "$folder/processes-before-home.txt"
  proc_uid="$(awk -v pid="$old_pid" -v pkg="$package" '$1=="*APP*"&&$2=="UID" {for(i=4;i<=NF;i++)if(index($i,pid":"pkg"/")==1){print $3;exit}}' "$folder/processes-before-home.txt")"
  [[ "$uid" == "$proc_uid" ]] || exit 1
  a shell input keyevent KEYCODE_HOME
  background=0
  for attempt in {1..25}; do
    state="$(a shell am get-uid-state "$uid" | tr -d '\r')"
    printf 'attempt=%s state=%s\n' "$attempt" "$state" >> "$folder/uid-state-poll.txt"
    if echo "$state" | grep -Eiq 'CACHED|BACKGROUND|LAST_ACTIVITY|SERVICE' &&
       ! echo "$state" | grep -Eiq 'TOP|FOREGROUND|BOUND_TOP'; then background=1; break; fi
    sleep 1
  done
  [[ "$background" == 1 ]] || exit 1
  a shell dumpsys activity processes > "$folder/background-processes.txt"
  a shell am kill "$package" > "$folder/am-kill.txt"
  gone=0
  for attempt in {1..25}; do
    a shell ps -A -o PID,NAME > "$folder/after-kill.txt"
    if ! awk -v pid="$old_pid" 'NR>1&&$1==pid {found=1} END {exit !found}' "$folder/after-kill.txt"; then gone=1; break; fi
    sleep 1
  done
  [[ "$gone" == 1 ]] || exit 1
  a shell am start -W -a android.intent.action.MAIN -c android.intent.category.LAUNCHER \
    -n "$package/.MainActivity" > "$folder/relaunch.txt"
  new_pid="$(a shell pidof -s "$package" | tr -d '\r')"
  [[ "$new_pid" =~ ^[0-9]+$ && "$new_pid" != "$old_pid" ]] || exit 1
  printf '%s\n' "$new_pid" > "$folder/new-pid.txt"
  instrument "$verify" "$folder/verify.txt"
  a exec-out screencap -p > "$folder/restored-screen.png"
  printf 'PASS commit=%s uid=%s old_pid=%s new_pid=%s\n' "$commit" "$uid" "$old_pid" "$new_pid" > "$folder/decision.txt"
done
a shell dumpsys meminfo "$package" > "$out/meminfo.txt"
a logcat -d -v threadtime > "$out/logcat.txt"
echo 'Physical install/PID gates PASS. Manual audio, TalkBack, touch, performance and academic decisions remain PENDING until completed.'
