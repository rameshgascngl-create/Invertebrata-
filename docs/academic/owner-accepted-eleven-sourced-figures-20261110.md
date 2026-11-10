# INVERTEBRATA — owner-approved sourced figure integration (10 November 2026)

The owner approved all eleven individually prepared scientific-source illustrations for inclusion with minor cosmetic imperfections and without another stringent visual review. This approval authorises **regular native Android textbook inclusion**, rather than debug-only previews.

## Implementation

- All eleven existing PNG blob objects from `native-app/app/src/debug/assets/qa_source_plates/` are copied **without recompression, editing or changed hashes** to `native-app/app/src/main/assets/qa_source_plates/`.
- The ten `SourcedQaAnatomicalPlates.byTypeStudy` identifiers are resolved in both the type-study gallery and paginated book reader across build variants.
- The eleventh, `lib/leishmania-amastigote`, resolves to `11_leishmania_two_forms.png` in all build variants.
- Existing mandatory original files are retained unmodified as fallback; the earlier 41 green-ticked figures are preserved without changes.
- Each source retains its original author, source, licence and necessary scientific qualifications. The separate `qa-source-intake/remaining_seven_sources.json` and `qa-source-intake/source_candidates.json` describe original-source records; **do not claim publication or independent academic rights approval** based solely on owner approval.
- `qa-source-intake/verify_staged_debug_assets.py` validates all eleven main-asset byte hashes and compares them to the pre-existing debug-asset copies.
- The existing academic gate remains a distinct process and is **not used to exclude the eleven figures from classroom QA or ordinary app inclusion**.

## Source-image slots

- `01_paramecium_oral_sourced.png` — exact Git blob `3e331971fbe31438b70b4cf3455cdaa0eb216621`
- `02_sycon_external_sourced.png` — exact Git blob `447ea036e973ef11ec0723ab97e4460717b6ec95`
- `03_sycon_development.png` — exact Git blob `340fc98a6621931b6c58ebebef4327dd5b187955`
- `04_obelia_medusa_sourced.png` — exact Git blob `45c7322d9ba4c3c3312591544be3f59206eb3da2`
- `05_obelia_lifecycle_sourced.png` — exact Git blob `3255ad4291b461375f2d4d8d5e3be6f5c18f5cd0`
- `06_ascaris_female_sourced.png` — exact Git blob `ed2be05fd0cf3d4c3e7b95e7404b3f7cef14712c`
- `07_earthworm_excretory_sourced.png` — exact Git blob `251d21d084d2895cda5657ef9aaa5bb4ee7fa294`
- `08_pila_external_sourced.png` — exact Git blob `7b99da180ef24ab2faafa4736f4f54a348bc87a4`
- `09_asterias_dissection.png` — exact Git blob `c6305805ce89e85252ea1e57771c996b2e2c195e`
- `10_asterias_larval_reference.png` — exact Git blob `6ab2147feb21e71b5445824d3c8657d6ced132a9`
- `11_leishmania_two_forms.png` — exact Git blob `e2e0c22c03f3e1cf30608d6c78cfa897080265d7`

## Limitations

This is **image integration approval**, not evidence that each illustration illustrates every structure in a lesson. Historical and composite plates preserve their documented limitations. No copyrighted figure is silently represented as original or AI-generated artwork. Normal software compilation and APK packaging verification remain in place.
