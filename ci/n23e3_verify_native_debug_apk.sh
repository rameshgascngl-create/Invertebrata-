#!/usr/bin/env bash
# Deterministic N2.3E3 evidence: reject release APKs, wrong packages and browser shells.
set -euo pipefail

EXPECTED_SHA="$(git rev-parse HEAD)"
if [[ -n "${GITHUB_SHA:-}" && "${GITHUB_SHA}" != "$EXPECTED_SHA" ]]; then
  echo "FAIL: checkout SHA does not match workflow event" >&2
  exit 1
fi
EXPECTED_PACKAGE="com.gasczoology.invertebratelab"
EXPECTED_VERSION_NAME="1.8.9"
EXPECTED_VERSION_CODE="18900"
APK="native-app/app/build/outputs/apk/debug/app-debug.apk"
OUT="qa-evidence/n23e3"
mkdir -p "$OUT"
test -s "$APK" || { echo "FAIL: no compiled Kotlin debug APK" >&2; exit 1; }

SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
test -n "$SDK_ROOT" && test -d "$SDK_ROOT/build-tools" || {
  echo "FAIL: Android SDK build-tools are missing" >&2; exit 1;
}
AAPT="$(find "$SDK_ROOT/build-tools" -type f -name aapt | sort -V | tail -n 1)"
APKSIGNER="$(find "$SDK_ROOT/build-tools" -type f -name apksigner | sort -V | tail -n 1)"
test -x "$AAPT" && test -x "$APKSIGNER" || {
  echo "FAIL: Android package inspectors not available" >&2; exit 1;
}

"$AAPT" dump badging "$APK" > "$OUT/aapt-badging.txt"
"$AAPT" dump permissions "$APK" > "$OUT/aapt-permissions.txt"
"$APKSIGNER" verify --verbose --print-certs "$APK" > "$OUT/apksigner-verification.txt"
grep -Fq "package: name='$EXPECTED_PACKAGE'" "$OUT/aapt-badging.txt" || {
  echo "FAIL: unexpected applicationId" >&2; exit 1;
}
grep -Fq "versionCode='$EXPECTED_VERSION_CODE'" "$OUT/aapt-badging.txt" || {
  echo "FAIL: versionCode changed" >&2; exit 1;
}
grep -Fq "versionName='$EXPECTED_VERSION_NAME'" "$OUT/aapt-badging.txt" || {
  echo "FAIL: versionName changed" >&2; exit 1;
}
grep -Fq 'application-debuggable' "$OUT/aapt-badging.txt" || {
  echo "FAIL: only debuggable QA APK is authorized" >&2; exit 1;
}
if grep -Fq 'android.permission.INTERNET' "$OUT/aapt-permissions.txt"; then
  echo "FAIL: INTERNET permission present" >&2
  exit 1
fi
if grep -RniE 'WebView|android\.webkit|<script|javascript:' native-app/app/src/main; then
  echo "FAIL: native production sources reference legacy browser runtime" >&2
  exit 1
fi

cp "$APK" "$OUT/INVERTEBRATA-N23E3-NATIVE-DEBUG-QA.apk"
(
  cd "$OUT"
  sha256sum INVERTEBRATA-N23E3-NATIVE-DEBUG-QA.apk > APK-SHA256.txt
)
cat > "$OUT/PROVENANCE.txt" <<EOF
Phase: N2.3E3
Commit: $EXPECTED_SHA
App package: $EXPECTED_PACKAGE
Version: $EXPECTED_VERSION_NAME ($EXPECTED_VERSION_CODE)
Variant: debug (NOT production release)
Architecture: native Kotlin / Jetpack Compose
Evidence scope: build/signature/package/permissions ONLY
Zoological accuracy: NOT approved
Tamil academic review: NOT approved
Physical device QA: NOT performed by CI
Warning: package ID is unchanged and debug signing may conflict with an existing installation. Back up app data before uninstalling any app.
EOF

echo "PASS: native debug APK independently verified for $EXPECTED_SHA"
cat "$OUT/APK-SHA256.txt"
