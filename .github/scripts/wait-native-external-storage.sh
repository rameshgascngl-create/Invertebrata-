#!/usr/bin/env bash
set -euo pipefail
for attempt in {1..30}; do
  if timeout 5s adb shell mkdir -p /sdcard/Download/native-anatomy-evidence /sdcard/Download/native-functional-evidence >/dev/null 2>&1; then
    exit 0
  fi
  sleep 1
done
echo "Android shared storage did not become writable after boot." >&2
exit 1
