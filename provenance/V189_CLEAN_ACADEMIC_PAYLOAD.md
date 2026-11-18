# INVERTEBRATA v1.8.9 Clean Academic Payload Provenance

Status: authoritative payload candidate reconciled from the user-supplied current v1.8.9 APK.

## Source identity

- APK filename: `INVERTEBRATA-v1.8.9-INSTALLABLE.apk`
- APK SHA-256: `5c325ae7a9e9dffd8f5017ddbd884c45848dc7322c9fa595306f4ed567b2003a`
- Extracted APK member: `assets/www/index.html`
- Payload title marker: `INVERTEBRATA v1.8.9 Clean Academic Edition`
- Payload byte size: `2475518`
- Payload SHA-256: `7f3dfbb64bc2c966b528a6d17245ef5e0e6277ff605ef6ccdeb7d9f0cabfdd00`
- Git blob SHA-1: `31cbd2e09caac4f790a4ae885b46c2af4896c625`

The extracted APK payload was independently compared with the separately materialized
`uploaded_v189_index.html` and was byte-identical.

## Integration rule

The frozen v1.8.8 20-step reconstruction remains the Android/source baseline and must
pass its original hash gate first. Only after that gate passes may this exact v1.8.9
payload replace:

- `academic_payload/index.html`
- `app/src/main/assets/www/index.html`
- `frontend/dist/index.html`

The v1.8.9 build must then verify that the packaged APK contains exactly one
`assets/www/index.html` whose SHA-256 remains the value above.

No academic-content transformation is permitted during the Android-shell optimization step.
