# INVERTEBRATA Android v1.8.7

Canonical Android release repository for the INVERTEBRATA undergraduate theory application.

## Frozen release identity

- Application ID / namespace: `com.gasczoology.invertebratelab`
- Version name: `1.8.7`
- Version code: `18700`
- Minimum SDK: `24`
- Target / compile SDK: `36`

## Frozen integrity anchors

- Original reconciled source ZIP SHA-256: `281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3`
- Deterministic R2 source ZIP SHA-256: `3f3ffd5d0b03da74b9f7b85cfc755883594aea3141149b1131ee11963d9a184a`
- Academic payload SHA-256: `25220886837b724f781612f66ae5f8fd4a983ca644559c4871aff91b479e24b7`
- Launcher master SHA-256: `dbd00a8d0e8ce09574c9c730b5fb4fa0049b3470171204c6db4712184bb54f7e`

The canonical source archive must be committed at repository root as:

`INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip`

The build pipeline must verify its hash before extraction. The academic payload must not be modified by Android-shell reconciliation.

## Release gate

A release is not final until source integrity, lint, unit-test gate, APK/AAB build, signing, signature verification, installation, offline startup and device QA have passed.

Permanent signing credentials must be stored only as GitHub Actions secrets and must never be committed to this repository.
