# U5.8 selected Paramecium PNGs — binary import manifest

## Selected source images (conversation-generated, not yet in repository)

| Concept | Target native asset | Source SHA-256 | Source size | Acceptance |
|---|---|---|---|---|
| Transverse binary fission | `native-app/app/src/main/assets/unit1/concepts/paramecium_binary_fission.png` | `617a80f56be87e31a95d61f976ad33933eb36057a304d582de82591879ebfdda` | 1774 × 887 | USER SELECTED, scientific editorial review pending |
| Conjugation | `native-app/app/src/main/assets/unit1/concepts/paramecium_conjugation.png` | `711f4ea4518a6b26184cd07008af48e057d986f2d118d069c46c843e02be1778` | 1774 × 887 | USER REQUESTED, scientific editorial review pending |

**Binary status: NOT IMPORTED.** This manifest does not substitute for committing the actual PNG bytes. Do not report either asset as integrated until the repository tree includes the real image blob at its target path and the app can load it.

## Correct import and verification

1. Upload the two PNGs to the exact paths above (preserve original bytes; verify SHA-256).
2. Confirm `rememberRequiredRasterBitmap(..., pngOnly = true)` resolves both paths and shows the image rather than `r14-concept-png-missing-*`.
3. In the reader, each concept appears only once in its matching lesson; verify full-screen image controls.
4. Review the conjugation image carefully: surviving haploid product divides into stationary and migratory pronuclei, migratory pronuclei exchange reciprocally, synkaryon forms, and nuclear reorganisation follows separation. Do not imply conjugation immediately doubles cell number. Review stage order and Tamil labels.
5. Review the binary-fission image: micronuclear mitosis versus macronuclear amitosis, transverse furrow, daughter nuclei and oral-apparatus organisation.
6. Run Android QA and inspect real screenshots at normal and 200% Tamil scaling on the same commit.

Do not change Unit V Pila.
