# Review-only figure intake — 27 October 2026

**Status:** 16 generated PNG crops supplied as ZIP in conversation; **binary assets not yet uploaded to GitHub**. These are unverified drafts, NOT textbook-approved figures. Do not reference them from production lessons or add them to the production plate inventory until corrected and accepted.

Intended destination: `native-app/app/src/debug/assets/review_only/<genus>/<filename>`.

| File | Intake status | Anatomical and Tamil-label review |
|---|---|---|
| `pila/pila_buccal_mass_radula.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `pila/pila_operculum.png` | Awaiting binary upload / review | Verify operculum is borne by foot, not a bivalve shell. |
| `pila/pila_digestive.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `pila/pila_nervous.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `pila/pila_circulatory.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `pila/pila_excretory_reproductive.png` | Awaiting binary upload / review | Split excretory and reproductive systems; verify separate organs and sex-specific labels. |
| `penaeus/penaeus_gill_structure.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `penaeus/penaeus_gill_arrangement.png` | Awaiting binary upload / review | Verify gill attachments, branchiostegite and water-flow direction. |
| `penaeus/penaeus_digestive.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `penaeus/penaeus_nervous.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `penaeus/penaeus_reproductive.png` | Awaiting binary upload / review | Check male petasma vs female thelycum and internal gonad/duct anatomy; avoid mixing external structures. |
| `asterias/asterias_digestive.png` | Awaiting binary upload / review | Verify eversible cardiac stomach, pyloric stomach and paired pyloric caeca. |
| `asterias/asterias_nervous.png` | Awaiting binary upload / review | Check circumoral nerve ring and five radial nerves; no central brain. |
| `asterias/asterias_water_vascular.png` | Awaiting binary upload / review | Verify madreporite → stone canal → ring canal → radial canals → lateral canals → ampullae/tube feet. |
| `asterias/asterias_internal.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |
| `asterias/asterias_tube_foot.png` | Awaiting binary upload / review | Verify organ identity, label leader lines, English spelling, Tamil terminology and agreement with chapter text. |

For every plate: verify source chapter anatomy, all labels and arrow endpoints, bilingual captions, crop resolution and whether a prior approved plate already covers the same subject. Keep original PNGs unchanged in staging. Only accepted plates are copied to `src/main/assets/type_studies/`, registered in the Python/Kotlin inventories, referenced in the correct `BookChapter`, and covered by build tests. Device QA and APK-size optimization remain deferred.
