# INVERTEBRATA v1.9.0 — permanent signing key setup (release-review only)

**Package:** `com.gasczoology.invertebratelab`  
**Source branch:** `feat/native-textbook-reader-qa`  
**Version:** `1.9.0` / `versionCode=19000`  
**Workflow:** `.github/workflows/native-v190-secure-signing.yml`

## Essential choice before inserting credentials

If this package has ever been published, retrieve its **existing upload key** for Google Play and its **existing APK signing certificate/private key** for standalone APK distribution. For Google Play, the upload key and app-signing key may be different. A standalone APK signed with an unrelated upload key cannot update Play-installed APKs. If this is a *first publication*, deliberately establish a new permanent upload/signing key and archive it securely. Never invent a replacement key for an existing listing.

Do **not** upload a keystore, password, or base64 value to a GitHub code branch, ChatGPT, documentation or repository issue. Keep at least two protected offline backups of the keystore and its alias/passwords.

## Configure five encrypted GitHub Actions secrets

Repository → Settings → Secrets and variables → Actions → New repository secret:

| Secret name | Description |
| --- | --- |
| `KS_B64` | Base64 of the existing release/upload keystore (.jks / .keystore), without newlines |
| `KS_PASS` | Keystore password |
| `KEY_ALIAS` | Alias of the private key inside that keystore |
| `KEY_PASS` | Password for that private key |
| `INVERTEBRATA_RELEASE_CERT_SHA256` | SHA-256 of the *approved* existing signing/upload certificate, 64 hexadecimal digits; optionally colon-delimited |

**Important:** `INVERTEBRATA_RELEASE_CERT_SHA256` must be grounded in the actual existing store upload certificate or previously signed production APK. It is not the fingerprint of a throwaway QA key.

On Windows PowerShell, obtain the base64 keystore safely without printing it to the terminal:

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\SECURE\existing-invertebrata-release.jks")) | Set-Clipboard
```

Paste into the `KS_B64` secret, then clear the clipboard. Use an actual local path; do not commit the file.

To inspect the **public certificate** fingerprint of an existing signed APK (no password needed), use Android build-tools:

```powershell
apksigner verify --print-certs "C:\SECURE\existing-production.apk"
```

For a known upload keystore, `keytool -list -v -keystore ... -alias ...` also displays its certificate SHA-256. If Google Play uses a separate upload certificate, check **Play Console → Test and release → Setup → App signing**, and use its **upload certificate**, not the Play-managed app-signing certificate, to validate the AAB upload.

## Build and sign

The dedicated workflow runs on its initial QA-branch file push, and is also configured for manual dispatch once GitHub exposes it. When the secrets are missing, it deliberately fails **before** using a keystore. After completing the secrets, open the failed signing run in GitHub Actions and use **Re-run failed jobs** to execute on the same source revision.

The workflow:
1. Requires a complete secret set and a 64-digit SHA-256 fingerprint.
2. Builds `assembleRelease`, `bundleRelease` and JVM unit tests.
3. Validates the private-key certificate against `INVERTEBRATA_RELEASE_CERT_SHA256`, rejecting a debug/test certificate.
4. Zip-aligns and signs the APK using `apksigner` (v2/v3), signs the AAB using `jarsigner`, and checks both signatures.
5. Verifies exact package/version, API level, absent Internet permission, bundled EN/TA privacy text and absent debug-only review assets.
6. Saves signed **REVIEW-ONLY** APK/AAB and checksum/certificate evidence as GitHub Actions artifacts; it does not automatically publish or merge.

Keystore bytes are confined to ephemeral GitHub runner storage and are erased after signing. GitHub secrets must remain in GitHub; never paste them into chat.

## Release holds that signing does not override

- Independent figure science/Tamil/licensing approval is still incomplete; do **not** mark `0/52` as approved.
- Verify all English/Tamil chapter navigation and Android font scaling in the signed APK.
- Test the final signed APK on a real phone, and capture screenshots from that exact build.
- Verify a permanent public HTTPS privacy-policy URL.
- Confirm title, screenshots, publisher identity, age rating, data safety and application access in the relevant app-store console.
- For an existing listing, ensure `versionCode` exceeds the currently published code before uploading.

**Signing creates a cryptographically genuine developer build. It does not certify academic quality or authorize public release.**
