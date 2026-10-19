# U5.9 — 17-image import staging (REVIEW ONLY)

Source archive: INVERTEBRATA_17_LATEST_AVAILABLE_ORDERED_REVIEW.zip (user-selected latest available set). **Do not promote to production assets or release.** The 17 PNGs include unresolved scientific and Tamil-label defects.

Upload destination in this branch: `native-app/app/src/debug/assets/review_only/u59/` (NOT `src/main/assets`). Keep these exact relative paths from the ZIP:

| No. | ZIP relative path | Intended teaching context |
|---|---|---|
| 01 | EARTHWORM/01_earthworm_external_REVIEW_ONLY.png | Unit IV Earthworm external |
| 02 | EARTHWORM/02_earthworm_digestive_REVIEW_ONLY.png | Unit IV Earthworm digestive |
| 03 | EARTHWORM/03_earthworm_vascular_REVIEW_ONLY.png | Unit IV Earthworm vascular |
| 04 | EARTHWORM/04_earthworm_nervous_REVIEW_ONLY.png | Unit IV Earthworm nervous |
| 05 | EARTHWORM/05_earthworm_excretory_REVIEW_ONLY.png | Unit IV Earthworm excretory |
| 06 | EARTHWORM/06_earthworm_reproductive_REVIEW_ONLY.png | Unit IV Earthworm reproductive |
| 07 | PENAEUS/07_penaeus_external_REVIEW_ONLY.png | Unit IV Penaeus external |
| 08 | PENAEUS/08_penaeus_mouthparts_REVIEW_ONLY.png | Unit IV Penaeus mouthparts |
| 09 | PENAEUS/09_penaeus_gills_REVIEW_ONLY.png | Unit IV Penaeus gills |
| 10 | PENAEUS/10_penaeus_internal_REVIEW_ONLY.png | Unit IV Penaeus internal |
| 11 | PENAEUS/11_penaeus_lifecycle_REVIEW_ONLY.png | Unit IV Penaeus life cycle |
| 12 | PILA/12_pila_external_REVIEW_ONLY.png | Unit V Pila external |
| 13 | PILA/13_pila_internal_REVIEW_ONLY.png | Unit V Pila internal |
| 14 | PILA/14_pila_radula_REVIEW_ONLY.png | Unit V Pila radula |
| 15 | ASTERIAS/15_asterias_external_REVIEW_ONLY.png | Unit V Asterias external |
| 16 | ASTERIAS/16_asterias_water_vascular_REVIEW_ONLY.png | Unit V Asterias water vascular |
| 17 | ASTERIAS/17_asterias_internal_REVIEW_ONLY.png | Unit V Asterias internal |

The archive also contains `00_ANDROID_ASSET_MANIFEST.csv` and `00_READ_ME_FIRST.txt`; preserve them beside the review-only collection.

**Import status:** This document records the destination and intended mapping only. It does not mean the PNG binaries have been uploaded. Verify all 17 Git blobs and hashes after upload. Release gate stays HOLD.

## Upload verified and binary assets staged
Upload commit: `1e04824d4d70eb5d568f22af226ab0f6fc40bb91` (17 files at repository root).
Debug-only staging commit: `52ad5e5f397228bb6157db4fc8e5a3d25a530b33` (all 17 original blobs copied into `native-app/app/src/debug/assets/review_only/u59/{earthworm,penaeus,pila,asterias}/`). No binary transformation; originals preserved.

### Running-text placement — editorial mapping
- Earthworm reader `UnitIVEarthwormReader.kt`: requiredPlates: 01 external, 02 digestive, 03 vascular, 04 nervous, 05 excretory, 06 reproductive. Corresponding explanatory lesson sections should display a matching approved figure directly after their text, rather than in a remote gallery.
- Penaeus `UnitIVPenaeusTextbook.kt`: 07 `external`, 08 `appendage-architecture`, 09 `respiration`, 10 `nutrition` / `circulation` / `reproduction` (only after internal labels pass), 11 `development`.
- Pila: 12 external morphology; 13 organ-system anatomy; 14 radula / feeding.
- Asterias: 15 external morphology; 16 water vascular system / locomotion; 17 internal systems.
- No reuse of a single full poster as multiple misleading organ-specific figures.

### Blocking scientific issues
The 17 uploaded files have not passed anatomical and Tamil review. Earlier AI plates included an erroneous Pila hermaphroditic system and Asterias excretory pore, and the revised Asterias internal candidate shows both male and female gonads in one specimen. All remain **DEBUG REVIEW ONLY**. Do not move them to `src/main/assets` or show them in release textbook pages until each is approved.
