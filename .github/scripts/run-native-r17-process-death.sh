#!/usr/bin/env bash
# Android API 34 R1.7 dedicated nuclear recovery. This driver must prove a genuine backgrounded-process
# termination. am force-stop is intentionally prohibited.
set -euo pipefail
package="com.gasczoology.invertebratelab"
runner="$package.test/androidx.test.runner.AndroidJUnitRunner"

chapter="${1:-dimorphism}"
stage="${2:-germline}"
reading="${3:-1.3}"
evidence="qa-evidence/r17-process/$chapter"
mkdir -p "$evidence"
result="NOT_STARTED"
old_pid=""
new_pid=""
uid=""
uid_state=""

capture_failure_evidence() {
    local exit_status=$?
    {
      printf 'status=%s\nexit=%s\noriginal_pid=%s\nnew_pid=%s\nuid=%s\nlast_uid_state=%s\n' \
        "$result" "$exit_status" "$old_pid" "$new_pid" "$uid" "$uid_state"
    } > "$evidence/decision.txt"
    adb shell dumpsys activity processes > "$evidence/activity-manager-processes.txt" 2>&1 || true
    adb shell dumpsys activity activities > "$evidence/activity-manager-activities.txt" 2>&1 || true
    adb shell ps -A -o PID,NAME > "$evidence/ps-final.txt" 2>&1 || true
    adb logcat -d -v threadtime -s R17_PROGRESS:I AndroidRuntime:E ActivityManager:I '*:S' \
       > "$evidence/logcat.txt" 2>&1 || true
    adb exec-out screencap -p > "$evidence/screenshot.png" 2>/dev/null || true
    adb shell uiautomator dump /sdcard/r17-current.xml > "$evidence/uiautomator-output.txt" 2>&1 || true
    adb pull /sdcard/r17-current.xml "$evidence/ui-hierarchy.xml" >/dev/null 2>&1 || true
    # Only filenames and file lengths, never private DataStore bytes.
    adb shell run-as "$package" ls -l files/datastore \
        > "$evidence/datastore-metadata.txt" 2>&1 || true
}
trap capture_failure_evidence EXIT

instrument() {
    local test_class="$1" log_path="$2"
    adb shell am instrument -w -r -e class "$test_class" \
      -e r17RecoveryChapter "$chapter" -e r17RecoveryStage "$stage" -e r17RecoveryReading "$reading" "$runner" | tee "$log_path"
    grep -q 'INSTRUMENTATION_CODE: -1' "$log_path"
    if grep -Eq 'FAILURES!!!|INSTRUMENTATION_RESULT: shortMsg=|INSTRUMENTATION_FAILED' "$log_path"; then
        return 1
    fi
}

# Build and install *this commit*, separately from the legacy HTML shell.
gradle -p native-app :app:installDebug :app:installDebugAndroidTest --stacktrace \
    > "$evidence/install.log" 2>&1 || { result=COMPILE_OR_INSTALL_FAILURE; exit 1; }

sha256sum native-app/app/build/outputs/apk/debug/*.apk native-app/app/build/outputs/apk/androidTest/debug/*.apk > "$evidence/tested-job-apk-sha256.txt"
# Setup traverses the actual Kotlin Compose UI and verifies the DataStore commit.
if ! instrument "$package.NativeR17ProcessDeathSetupTest" "$evidence/setup-instrumentation.txt"; then
    result=SETUP_OR_DATASTORE_ASSERTION_FAILURE
    exit 1
fi

# Test frameworks may close the Activity on teardown. Relaunch the real app
# through its launcher intent, then obtain the original native process PID.
adb shell am start -W -a android.intent.action.MAIN \
    -c android.intent.category.LAUNCHER -n "$package/.MainActivity" \
    > "$evidence/initial-launch.txt" 2>&1

get_pid() {
    adb shell pidof -s "$package" 2>/dev/null | tr -d '\r' | awk '{print $1}'
}
for attempt in {1..20}; do
    old_pid="$(get_pid)"
    if [[ "$old_pid" =~ ^[0-9]+$ ]]; then break; fi
    sleep 1
done
if [[ ! "$old_pid" =~ ^[0-9]+$ ]]; then
    result=PRE_KILL_PID_UNAVAILABLE
    exit 1
fi
printf '%s\n' "$old_pid" > "$evidence/original-pid.txt"

# A native Compose semantics tag must be exposed to Android accessibility
# through testTagsAsResourceId, not observed by OCR or screen coordinates.
adb shell uiautomator dump /sdcard/r17-before.xml > "$evidence/ui-before-command.txt" 2>&1
adb pull /sdcard/r17-before.xml "$evidence/ui-before.xml" >/dev/null
if ! grep -Fq "r17-learning-position" "$evidence/ui-before.xml" ||
   ! grep -Fq "$stage" "$evidence/ui-before.xml"; then
    result=PRE_KILL_UI_ASSERTION_FAILURE
    exit 1
fi

adb shell dumpsys activity processes > "$evidence/processes-before-home.txt"

# Resolve the exact installed package UID through PackageManager. Independently
# cross-check the UID against ActivityManager's record for the observed app PID.
# The old dumpsys-package userId grep silently returned empty on API 34.
adb shell cmd package list packages -U --user 0 "$package" \
    > "$evidence/package-uid-query.txt" 2>&1 || true
pkg_uid="$(awk -v pkg="$package" '
    $1 == "package:" pkg {
        for (i=2; i<=NF; i++) {
            if ($i ~ /^uid:[0-9]+$/) {
                sub(/^uid:/, "", $i)
                print $i
                exit
            }
        }
    }
' "$evidence/package-uid-query.txt")"
proc_uid="$(awk -v pid="$old_pid" -v pkg="$package" '
    $1 == "*APP*" && $2 == "UID" && $3 ~ /^[0-9]+$/ {
        for (i=4; i<=NF; i++) {
            if (index($i, pid ":" pkg "/") == 1) {
                print $3
                exit
            }
        }
    }
' "$evidence/processes-before-home.txt")"
printf 'old_pid=%s\npackage_uid=%s\nprocess_record_uid=%s\n' \
    "$old_pid" "$pkg_uid" "$proc_uid" > "$evidence/uid-crosscheck.txt"
if [[ -n "$pkg_uid" && -n "$proc_uid" && "$pkg_uid" != "$proc_uid" ]]; then
    result=BACKGROUND_UID_MISMATCH
    exit 1
fi
uid="${pkg_uid:-$proc_uid}"
if [[ ! "$uid" =~ ^[0-9]+$ ]]; then
    result=BACKGROUND_UID_UNAVAILABLE
    exit 1
fi

adb shell input keyevent KEYCODE_HOME

# ActivityManager's process-state API gives stronger evidence than merely
# assuming the HOME key successfully backgrounded the application.
background_verified=0
for attempt in {1..25}; do
    uid_state="$(adb shell am get-uid-state "$uid" 2>&1 | tr -d '\r' || true)"
    printf 'attempt=%s state=%s\n' "$attempt" "$uid_state" >> "$evidence/uid-state-poll.txt"
    if echo "$uid_state" | grep -Eiq 'CACHED|BACKGROUND|LAST_ACTIVITY|SERVICE' &&
       ! echo "$uid_state" | grep -Eiq 'TOP|FOREGROUND|BOUND_TOP'; then
        background_verified=1
        break
    fi
    sleep 1
done
adb shell dumpsys activity activities > "$evidence/activities-after-home.txt"
adb shell dumpsys activity processes > "$evidence/processes-after-home.txt"
if [[ "$background_verified" != "1" ]]; then
    result=INVALID_BACKGROUND_PREREQUISITE
    exit 1
fi

# No force-stop, kill -9 or system process restart is permitted.
adb shell am kill "$package" > "$evidence/am-kill.txt" 2>&1
gone=0
for attempt in {1..25}; do
    adb shell ps -A -o PID,NAME > "$evidence/processes-after-kill.txt"
    if ! awk -v target="$old_pid" 'NR>1 && $1==target {found=1} END {exit !found}' \
         "$evidence/processes-after-kill.txt"; then
        gone=1
        break
    fi
    sleep 1
done
if [[ "$gone" != "1" ]]; then
    result=INVALID_PROCESS_KILL_ATTEMPT
    exit 1
fi

adb shell am start -W -a android.intent.action.MAIN \
    -c android.intent.category.LAUNCHER -n "$package/.MainActivity" \
    > "$evidence/relaunch.txt" 2>&1
for attempt in {1..20}; do
    new_pid="$(get_pid)"
    if [[ "$new_pid" =~ ^[0-9]+$ && "$new_pid" != "$old_pid" ]]; then break; fi
    sleep 1
done
if [[ ! "$new_pid" =~ ^[0-9]+$ || "$new_pid" == "$old_pid" ]]; then
    result=PROCESS_RELAUNCH_PID_FAILURE
    exit 1
fi
printf '%s\n' "$new_pid" > "$evidence/new-pid.txt"

adb shell uiautomator dump /sdcard/r17-after.xml > "$evidence/ui-after-command.txt" 2>&1
adb pull /sdcard/r17-after.xml "$evidence/ui-after.xml" >/dev/null
if ! grep -Fq "r17-learning-position" "$evidence/ui-after.xml" ||
   ! grep -Fq "$stage" "$evidence/ui-after.xml"; then
    result=POST_RELAUNCH_UI_ASSERTION_FAILURE
    exit 1
fi
# This second instrumentation run does not rewrite the fixture. It verifies
# the recovered screen and DataStore after a *different* process is observed.
if ! instrument "$package.NativeR17ProcessDeathVerifyTest" "$evidence/verify-instrumentation.txt"; then
    result=GENUINE_STATE_RESTORATION_FAILURE
    exit 1
fi
printf '%s\n' "$GITHUB_SHA" > "$evidence/commit.txt"
adb pull /sdcard/Download/native-anatomy-evidence "$evidence/restored-native-screenshots" >/dev/null
result=PASS
