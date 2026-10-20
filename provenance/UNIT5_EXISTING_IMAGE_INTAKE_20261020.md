# Unit V existing-image intake — review-visible, not academically approved

Source branch: `native/u61-unit5-bilingual-android-proof-20261020`, source commit `102b3368ac721f0fbefc07602d66821538963c4b`.

The user requested use of the existing source images. This change copies **16 original PNG Git blobs byte-for-byte** into `src/main/assets/unit5/candidates/`. The original source files remain unchanged. Each image appears once in the native Unit V chapter, as a lazily loaded thumbnail with enlargement, a bilingual caption and a per-image caution. The filenames and descriptive captions do not constitute anatomical approval.

- **Pila (12):** existing repository-root `*_REVIEW.png` files, shell to excretory system.
- **Asterias (4):** the four original `src/debug/assets/review_only/asterias/*_REVIEW.png` files.
- **No SVG, Canvas fallback, image generation, image redrawing, image duplication or guessed crop** was introduced.
- **Not verified:** anatomy, bitmap-internal English wording, Tamil editorial equivalence, image rights and physical-device behaviour. Source candidates have visible deficiencies documented in `provenance/ASTERIAS_FOUR_RASTER_ANATOMICAL_AUDIT_20261018.md` and the figure-specific UI warnings. Pila radula and respiratory image labels include typographical errors.
- **Mandatory type-study slots** `type_studies/pila/` and `type_studies/asterias/` intentionally retain their required missing/approval indication; review candidates are not deceptively marked as verified anatomical masters.
- **Release:** HOLD until corrected accepted masters and independent scientific, bilingual, rights and device review.

## Exact source mapping

| Student reader candidate path | Existing repository file |
|---|---|
| `unit5/candidates/pila/01_shell_ventral.png` | `01_shell_ventral_REVIEW.png` |
| `unit5/candidates/pila/02_operculum_views.png` | `02_operculum_views_REVIEW.png` |
| `unit5/candidates/pila/03_shell_dorsal.png` | `03_shell_dorsal_REVIEW.png` |
| `unit5/candidates/pila/04_animal_front.png` | `04_animal_front_REVIEW.png` |
| `unit5/candidates/pila/05_nervous_system.png` | `05_nervous_system_REVIEW.png` |
| `unit5/candidates/pila/06_buccal_mass.png` | `06_buccal_mass_REVIEW.png` |
| `unit5/candidates/pila/07_digestive_system.png` | `07_digestive_system_REVIEW.png` |
| `unit5/candidates/pila/08_respiratory_system.png` | `08_respiratory_system_REVIEW.png` |
| `unit5/candidates/pila/09_circulatory_system.png` | `09_circulatory_system_REVIEW.png` |
| `unit5/candidates/pila/10_male_reproductive.png` | `10_male_reproductive_REVIEW.png` |
| `unit5/candidates/pila/11_female_reproductive.png` | `11_female_reproductive_REVIEW.png` |
| `unit5/candidates/pila/12_excretory_system.png` | `12_excretory_system_REVIEW.png` |
| `unit5/candidates/asterias/external.png` | `native-app/app/src/debug/assets/review_only/asterias/external_REVIEW.png` |
| `unit5/candidates/asterias/water_vascular.png` | `native-app/app/src/debug/assets/review_only/asterias/water_vascular_REVIEW.png` |
| `unit5/candidates/asterias/digestive.png` | `native-app/app/src/debug/assets/review_only/asterias/digestive_REVIEW.png` |
| `unit5/candidates/asterias/nervous.png` | `native-app/app/src/debug/assets/review_only/asterias/nervous_REVIEW.png` |
