# Unit I U1.5 — Entamoeba morphology: native lesson and artwork candidate

**Status:** U1.5 academic HOLD / engineering CI to be proved on the exact commit.

## What is implemented
- Native Kotlin Jetpack Compose teaching lesson `ui/UnitIEntamoebaMorphology.kt`, inserted into Unit I's `u1-proto-parasites` chapter before the existing four-species reference gallery and preserved A5 bank.
- English/Tamil morphological descriptions and separate section headings for trophozoite, mature cyst, and transmission / invasion.
- Native optional image slots `unit1/original_candidates/entamoeba_trophozoite` and `.../entamoeba_mature_cyst`, read by the shared native raster loader.
- The slots **fail closed**: they report absent unapproved assets, and the existing attributed reference gallery remains available. No synthetic anatomy is drawn as a silent fallback.
- Automated checks require English/Tamil lesson semantics, the unapproved-artwork notice, existing gallery, protected A5 corpus and exact-commit API34 QA.

## Separate artwork package — NOT yet committed to app assets
**Download/archive name:** `INVERTEBRATA_U1_5_Entamoeba_draft_plates.zip` (available in the originating ChatGPT conversation). It contains two 1500×1500 PNG masters and two corresponding WebP derivatives, an asset manifest and a review guide.

The two panels are **separations from one newly generated graphite-style image**, not two independent scans. This is AI-generated draft artwork, **not photographed hand-pencil artwork**, not a photomicrograph and not expert-validated anatomy.

| Figure | WebP SHA-256 | PNG SHA-256 |
| --- | --- | --- |
| Trophozoite | `03d2bd4155c23b9ef281978e1b87bc27f133b06393d86837a70801d643a36d12` | `f70e132fca2406c16e183fc8a335dc4393d065734c8f0e5a408e379932eaf8a8` |
| Mature cyst | `4876938b7bb38976482750de2543a553dc230691fecdf5ee310a252599aaa659` | `096902069ca430c011825f7a6e044392aef540e869565779052673a5b3ae22af` |

Composite image SHA-256: `c5a8e8de460b6a7e9c0a8254d844a9dcab2370f2727085f44618e4fc78f65492`.

### Scientific review: required before app embedding
Primary diagnostic morphology: [CDC DPDx — Amebiasis](https://www.cdc.gov/dpdx/amebiasis/) and [CDC differential microscopy table](https://www.cdc.gov/dpdx/diagnosticprocedures/stool/morphcomp.html).

1. Trophozoite: exactly one typical vesicular nucleus; fine uniformly distributed peripheral chromatin and central karyosome; nonuniform amoeboid outline; hyaline fingerlike pseudopodia.
2. Mature cyst: **four** nuclei with the appropriate nuclear morphology, smooth spherical wall, and chromatoid bars with rounded ends where depicted; no pseudopodia.
3. Confirm apparent granules do not masquerade as unequivocally engulfed erythrocytes; the latter are not a guaranteed species discriminator.
4. Do not claim the plates can distinguish E. histolytica from E. dispar.
5. Independent zoology signoff for **every** depicted structure and future leader-line endpoint, followed by Tamil terminology audit.

### Candidate asset import (manual review-gated engineering step, not production)
1. Extract the ZIP and verify the SHA-256 values against the manifest.
2. After a named academic reviewer accepts the individual figures, put the WebP files under `native-app/app/src/main/assets/unit1/original_candidates/`.
3. Rebuild the exact committed source, capture 100%/200% English/Tamil screenshots, confirm labelled organ selections and zoom coordinates, and verify physical-device audio.
4. Retain `DRAFT_UNVERIFIED` and do **not** promote the release until expert-approved anatomy and physical device acceptance are evidenced.

**Not done:** Uploading binary generated art to GitHub; signed scientific artwork approval; image-coordinate touch hotspots; audible physical-device QA; separate hand-scanned graphite masters. No claim of completion should be made until these are verified.

## Unchanged contracts
All 43 A5 chapters, Unit II–V content and navigation contracts are untouched. U1.4 is the last independently verified engineering checkpoint as of the phase start.
