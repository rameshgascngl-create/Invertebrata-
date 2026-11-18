# Asterias four-raster anatomical audit — 2026-10-18

Scope: the actual 1536 x 1024 PNGs committed as debug-only editorial candidates in `native-app/app/src/debug/assets/review_only/asterias/`. This is a visual academic audit, not a physical-device rendering test. Do not promote to `type_studies/asterias/`.

| File | Result | Visible defects | Required correction |
|---|---|---|---|
| external_REVIEW.png | FAIL | Heading says 'Internal Anatomy (All Systems)' and shows opened organs, despite gallery entry 'External morphology'; multiple crowded systems and insets. | Replace with a dedicated intact external specimen plate, clearly separating oral and aboral surfaces, mouth/ambulacral grooves/tube feet on oral side and madreporite/spines/papulae on aboral side. Do not relabel the existing internal composite as an external specimen. |
| water_vascular_REVIEW.png | FAIL | Figure says 'Aboral view of water-vascular system' while displaying ring/radial canals and tube feet as though directly exposed on aboral surface. Stone canal/ring canal are simplified and spatial relations are ambiguous. | Produce an explicitly labeled schematic cutaway/oral-dissection view: aboral interradial madreporite -> stone canal -> circumoral ring canal -> five oral radial canals -> lateral canals/ampullae/podia. Make oral/aboral location and dissection convention explicit. |
| digestive_REVIEW.png | FAIL | Inset uses phrase 'Cardiac stomach with everted pyloric stomach', conflating eversible cardiac stomach with non-eversible pyloric stomach. Stomach and caecum leader lines are difficult to reconcile; extra crowded insets. | Redraw a single aboral-dissection plate with pyloric stomach dorsally, cardiac stomach ventrally (eversible), paired pyloric caeca extending into each arm and pyloric ducts; do not depict pyloric stomach as everted. Use separately validated mouth/oesophagus/short intestine/anus annotations where visible. |
| nervous_REVIEW.png | REVIEW HOLD | Oral nerve ring and five radial nerves broadly plausible. Dense insets and fine leader endpoints, especially terminal/skin sensory structures, have not been independently checked. | Confirm circumoral nerve ring around mouth and radial nerve cords within oral ambulacral grooves; check lateral branches and sensory epithelium against zoological references; simplify to one clean primary plate. |

## Editorial and technical gates

- All four are candidate illustrations, not approved scientific plates. Generated raster text, including Tamil, must undergo line-by-line editorial proofreading.
- The PNGs are currently under `src/debug/assets/review_only`, not release assets. A production APK cannot be assumed to contain them.
- Existing Android UI test verifies image presence in debug builds, not scientific correctness.
- Keep mandatory `type_studies/asterias/{external,water-vascular-system,internal,larval-development}.png` absent until individual corrected and reviewed masters exist.
- No evidence of a new Android build, emulator screenshot inspection or physical-device test was obtained during this audit.
- Next: replace failed plates with scientifically verified standalone figures, then run compile, image-tag UI tests, inspect screenshots at normal and 200% Tamil fonts, and record academic approval separately.
