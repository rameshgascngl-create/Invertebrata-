# Generated anatomical sheet — quarantine and integration review (2026-10-18)

**Status: REVIEW_ONLY; zero plates authorised for student-facing integration.**

Source: assistant-generated composite PNG, 1536 × 1024 pixels, 12 panels, titled Pila / Asterias / Penaeus. It is not a scanned college practical record or independently verified anatomical master. Do not copy or crop the composite into `native-app/app/src/main/assets/type_studies/` until individual plates are corrected and reviewed. Preserve existing masters and the fail-closed gates.

| Panel | Subject | Potential mandatory slot | Assessment |
|---|---|---|---|
| 1 | Pila external | `pila/external` | Candidate reference only: operculum, shell coiling, aperture and head-foot must be independently checked |
| 2 | Pila digestive | None (supplementary) | Do not map to mantle cavity or ctenidium; internal organ relations need verification |
| 3 | Pila respiratory/circulatory | `pila/mantle-cavity`, `pila/ctenidium` | REJECT as replacement: mixed systems, insufficient separation of ctenidium, pulmonary chamber, osphradium and water route |
| 4 | Pila nervous | None (supplementary) | Verify ganglia and nerve connections; not a mandatory replacement |
| 5 | Asterias oral/aboral | `asterias/external` | Candidate reference only: oral/aboral distinction and madreporite location require review |
| 6 | Asterias water vascular | `asterias/water-vascular-system` | REJECT as replacement pending full madreporite→stone canal→ring canal→radial canal→lateral canal→ampulla→tube foot anatomy |
| 7 | Asterias digestive | `asterias/internal` | Incomplete internal plate; pyloric caeca, stomach regions and orientation require verification |
| 8 | Asterias reproductive | `asterias/internal` | Not an acceptable substitute for comprehensive internal anatomy; sex and gonad distribution require review |
| 9 | Penaeus external | `penaeus/external` | Candidate reference only: segmentation and rostral/appendage morphology require review |
| 10 | Penaeus appendages | `penaeus/appendages` | REJECT as complete appendage series; appendage identity, biramous branches and segment-specific structures insufficiently distinguished |
| 11 | Penaeus digestive | `penaeus/internal` | Partial internal systems only; not a complete internal anatomy plate |
| 12 | Penaeus circulatory/nervous | `penaeus/internal` | Mixed systems in a single panel; anatomical connections require separate verification |

**No panel addresses** `pila/torsion`, `asterias/larval-development`, or `penaeus/larval-development`. None addresses missing Earthworm, Sycon, Obelia or Paramecium slots.

## Acceptance and integration sequence

1. Produce a dedicated, legible, high-resolution raster master for each *exact* required slot; do not reuse this composite as a source of independent scientific plates.
2. Check organism-specific morphology, every leader-line endpoint, correct English terms, Tamil editorial accuracy, and provenance/source rights. Record reviewer and decision for each plate.
3. Integrate only accepted PNG/WebP under the exact `type_studies/<organism>/<slot>.<ext>` path; preserve the single-plate-per-slot mapping.
4. Re-run `.github/scripts/audit-five-unit-assets.py --gate`, Kotlin/JVM tests, Android instrumentation, English and 200%-font Tamil screenshot checks, and physical-device image zoom/rotation/offline tests.
5. Keep independent academic approval and publication HOLD until all gates pass.

This file records a negative scientific acceptance decision, **not** a claim that new images were imported.
