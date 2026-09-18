# INVERTEBRATA v1.8.8 — Step-4 Batch-2 assertion supersession

## Decision

**STEP-4 FALSE-POSITIVE OFFLINE ASSERTION — VALIDATED REPLACEMENT PROMOTED**

This provenance record supersedes only the offline-safety assertion mechanics in reconstruction Step 4. The Batch-2 academic/figure transformation itself is not characterized as biologically or academically defective.

## Previous accepted Step 4

- Path: `ci/reconcile_v188_svg_batch2.py`
- Governing commit: `6bcd3961aaf67db02799c2f5f589dd832d50d36f`
- Git blob: `5114abd6b5d15915b205abf3c5fc84d6923ca913`
- Defect: blanket rejection of every `https://` token after Batch-2 insertion.

During genuine GitHub Actions reconstruction, Steps 1–3 passed and Step 4 stopped with:

`forbidden token after batch2: https://`

## Diagnostic evidence

GitHub Actions diagnostic run demonstrated that the Step-3 payload contained exactly one HTTPS occurrence:

`https://appassets.androidplatform.net`

It occurred only inside the accepted Content-Security-Policy directive:

`img-src 'self' data: blob: https://appassets.androidplatform.net;`

Diagnostic findings:

- accepted local CSP occurrence: exactly 1
- raw HTTPS occurrence: exactly 1
- remote resource loads: 0
- JavaScript network calls: 0
- Step-3 payload SHA-256: `ed25eb867f5ed060989156d677a660c6ffe22999e489305ac198002e53af6912`

The origin is the accepted Android WebViewAssetLoader local application origin. Its presence does not introduce an Internet dependency.

## Validated replacement

- Validation branch patch commit: `1391a735818de1ac02629eb777a3ffaaf4c2628b`
- Validated replacement Git blob: `a716562c70db61fbf91a9a73d19bde8372e95f0d`
- Validation Actions run: `35366754812`
- Validation result: **SUCCESS**
- Validation artifact digest: `sha256:118b8f835c84cde474086aa5ef88433075f35cb26680923bd28b470cb42e276f`

The replacement permits only the exact accepted local CSP directive, exactly once, then continues to reject all remaining:

- `http://`
- `https://`
- `<iframe`
- `eval(`
- `new Function(`

Validation also proved rejection of:

- an external HTTPS image
- an external HTTP image
- an iframe
- `eval(`
- `new Function(`
- a second occurrence of the trusted appassets origin

The patched Step 4 executed successfully with:

- trusted local origin occurrences: 1
- raw HTTPS occurrences: 1
- remote resource loads: 0

## Authoritative promotion

- Promoted Step-4 commit on `main`: `8bb44d8ee4d2c9ae57869aa9ac812ef7d92180ee`
- Promoted Step-4 Git blob: `a716562c70db61fbf91a9a73d19bde8372e95f0d`
- Manifest pointer update commit: `c4090bc25dfcaa1e881c0fe910bba6da87693e8d`
- Manifest executable step count remains: **19**
- Step order remains: **1–19**
- Old Step-4 governing commit is recorded as superseded and is not executable.

## Scope boundary

This promotion changes **validation mechanics only**.

It does not change:

- Batch-2 figure markup
- zoological academic content
- the other 18 reconstruction transformations
- Android wrapper
- Gradle configuration
- applicationId/package identity
- versionName/versionCode
- release workflow
- release state

The offline guarantee is preserved and made more precise.

## Next gate

Re-run complete Phase B2 from the verified canonical ZIP:

fresh Run A → independent fresh Run B → deterministic SHA-256 comparison → full-source integrity → Matrix 02 Run A → Matrix 02 Run B.

No Android build is authorized by this promotion.
