#!/usr/bin/env bash
# Fail-closed signing of the native 1.9.0 release. Never prints or uploads secrets.
set -euo pipefail
umask 077
OUT=release/signed-review
mkdir -p "$OUT/evidence"
KEYSTORE="$RUNNER_TEMP/invertebrata-permanent.p12"
CERT="$RUNNER_TEMP/invertebrata-public-cert.pem"
ALIAS_CHECK="$RUNNER_TEMP/invertebrata-alias-check.txt"
ALIGNED="$RUNNER_TEMP/INVERTEBRATA-aligned.apk"
trap 'rm -f "$KEYSTORE" "$CERT" "$ALIAS_CHECK" "$ALIGNED"' EXIT

for variable in KS_B64 KS_PASS KEY_ALIAS KEY_PASS EXPECTED_CERT_SHA256; do
  if ! printenv "$variable" >/dev/null || [ -z "$(printenv "$variable")" ]; then
    echo "::error::Missing signing credential: $variable. See release/V190_SIGNING_SETUP.md. Do not send passwords in chat."
    exit 2
  fi
done
expected="$(printf '%s' "$EXPECTED_CERT_SHA256" | tr -d ':' | tr '[:lower:]' '[:upper:]' | tr -d '[:space:]')"
if ! [[ "$expected" =~ ^[0-9A-F]{64}$ ]]; then
  echo "::error::Pinned permanent release/upload certificate fingerprint must be 64 hexadecimal digits."
  exit 2
fi
# Fail with non-secret diagnostic messages before starting the expensive build.
if ! printf '%s' "$KS_B64" | base64 --decode > "$KEYSTORE" 2>/dev/null; then
  echo "::error::KS_B64 is not valid Base64; recopy the verified FINAL-VERIFIED-RELEASE.p12 to the GitHub secret."
  exit 2
fi
if [ ! -s "$KEYSTORE" ]; then
  echo "::error::KS_B64 decodes to an empty file; replace it with the complete verified .p12 content."
  exit 2
fi
if ! keytool -list -storetype PKCS12 -keystore "$KEYSTORE" -storepass:env KS_PASS >/dev/null 2>&1; then
  echo "::error::The uploaded PKCS12 keystore cannot be opened with KS_PASS. Make sure KS_B64 and KS_PASS come from the same verified key."
  exit 2
fi
if ! keytool -list -v -storetype PKCS12 -keystore "$KEYSTORE" -storepass:env KS_PASS -alias "$KEY_ALIAS" > "$ALIAS_CHECK" 2>&1; then
  echo "::error::KEY_ALIAS was not found in the verified keystore. Expected the alias of your permanent private key."
  exit 2
fi
if ! grep -Fq 'Entry type: PrivateKeyEntry' "$ALIAS_CHECK"; then
  echo "::error::KEY_ALIAS does not identify a private key entry."
  exit 2
fi
if ! keytool -exportcert -rfc -storetype PKCS12 -keystore "$KEYSTORE" -storepass:env KS_PASS -alias "$KEY_ALIAS" > "$CERT" 2>/dev/null; then
  echo "::error::Unable to export the public signing certificate from the keystore."
  exit 2
fi
if ! actual="$(openssl x509 -in "$CERT" -outform DER | sha256sum | cut -d ' ' -f 1 | tr '[:lower:]' '[:upper:]')"; then
  echo "::error::Unable to calculate SHA-256 certificate fingerprint."
  exit 2
fi
if [ "$actual" != "$expected" ]; then
  echo "::error::Signing key differs from pinned permanent release/upload certificate; no signing performed."
  exit 2
fi
if openssl x509 -in "$CERT" -noout -subject | grep -qi 'Android Debug'; then
  echo "::error::Android Debug certificate is prohibited for store signing."
  exit 2
fi
echo "PERMANENT_KEYSTORE_CERTIFICATE_PREFLIGHT=PASS"
if [[ "${1:-}" == "--preflight" ]]; then
  exit 0
fi

BT="$(find "$ANDROID_HOME/build-tools" -mindepth 1 -maxdepth 1 -type d | sort -V | tail -n 1)"
test -x "$BT/apksigner"
test -x "$BT/zipalign"
test -x "$BT/aapt"
UNSIGNED_APK="native-app/app/build/outputs/apk/release/app-release-unsigned.apk"
UNSIGNED_AAB="native-app/app/build/outputs/bundle/release/app-release.aab"
test -s "$UNSIGNED_APK"
test -s "$UNSIGNED_AAB"
APK="$OUT/INVERTEBRATA-1.9.0-PERMANENT-KEY-SIGNED-REVIEW-ONLY.apk"
AAB="$OUT/INVERTEBRATA-1.9.0-PERMANENT-KEY-SIGNED-REVIEW-ONLY.aab"
"$BT/zipalign" -f -p 4 "$UNSIGNED_APK" "$ALIGNED"
"$BT/apksigner" sign --ks "$KEYSTORE" --ks-pass env:KS_PASS --ks-key-alias "$KEY_ALIAS" --key-pass env:KEY_PASS --out "$APK" "$ALIGNED"
jarsigner -keystore "$KEYSTORE" -storepass "$KS_PASS" -keypass "$KEY_PASS" -sigalg SHA256withRSA -digestalg SHA-256 -signedjar "$AAB" "$UNSIGNED_AAB" "$KEY_ALIAS"
"$BT/zipalign" -c -v 4 "$APK" > "$OUT/evidence/zipalign.txt"
"$BT/apksigner" verify --verbose --print-certs "$APK" > "$OUT/evidence/apksigner.txt"
grep -Fq 'Verified using v2 scheme (APK Signature Scheme v2): true' "$OUT/evidence/apksigner.txt"
grep -Fq 'Verified using v3 scheme (APK Signature Scheme v3): true' "$OUT/evidence/apksigner.txt"
apkcert="$(grep -i 'certificate SHA-256 digest:' "$OUT/evidence/apksigner.txt" | head -1 | sed 's/.*: //' | tr -d ':' | tr '[:lower:]' '[:upper:]' | tr -d '[:space:]')"
test "$apkcert" = "$expected"
jarsigner -verify "$AAB" > "$OUT/evidence/aab-signature.txt"
"$BT/aapt" dump badging "$APK" > "$OUT/evidence/badging.txt"
"$BT/aapt" dump permissions "$APK" > "$OUT/evidence/permissions.txt"
grep -Fq "package: name='com.gasczoology.invertebratelab'" "$OUT/evidence/badging.txt"
grep -Fq "versionCode='19000'" "$OUT/evidence/badging.txt"
grep -Fq "versionName='1.9.0'" "$OUT/evidence/badging.txt"
grep -Fq "targetSdkVersion:'36'" "$OUT/evidence/badging.txt"
! grep -Fq 'application-debuggable' "$OUT/evidence/badging.txt"
! grep -Fq 'android.permission.INTERNET' "$OUT/evidence/permissions.txt"
python3 - "$APK" "$AAB" <<'PY'
import sys,zipfile
for filename,prefix in ((sys.argv[1],"assets/"), (sys.argv[2],"base/assets/")):
    with zipfile.ZipFile(filename) as z:
        names=set(z.namelist())
        assert not any(n.startswith(prefix+"review_only/") for n in names), "Debug-only image in release"
        for lang in ("en","ta"):
            file=prefix+"privacy_policy_"+lang+".txt"
            assert file in names, "Missing policy: "+file
            assert "rameshgascngl@gmail.com" in z.read(file).decode("utf-8")
PY
(cd "$OUT" && sha256sum *.apk *.aab > evidence/sha256sums.txt)
printf 'SOURCE_COMMIT=%s\nCERTIFICATE_SHA256=%s\nPERMANENT_SIGNING_VERIFIED=PASS\nSTORE_PUBLICATION=NOT_APPROVED\nACADEMIC_REVIEW=STILL_REQUIRED\n' "$GITHUB_SHA" "$expected" > "$OUT/evidence/review-status.txt"
echo "PERMANENT_CERTIFICATE_SIGNING_AND_APK_AAB_VERIFICATION=PASS"
