# Unit III — Generated pencil plates: academic audit (13 October 2026)

## Scope and disposition
Reviewed four AI-generated candidate PNGs (1122 × 1402 pixels each) against the *Fasciola hepatica* and *Ascaris lumbricoides* authoritative biological literature. **No image is academically accepted for release**. These outputs are generated artwork, **not actual hand-scanned pencil plates**. A visually credible graphite texture is not evidence of authentic or correct anatomical geometry.

| Candidate filename | Decision | Required change |
| --- | --- | --- |
| `fasciola_hepatica_anatomical_plate.png` | FAIL — anatomical | Two highly branched testes must be tandem (one posterior to the other), not a bilateral pair; ovary must be dendritic, not a cluster of eggs; genital pore belongs midventrally between oral and ventral suckers. Redraw underlying structures and leader lines. |
| `fasciola_hepatica_life_cycle_diagram.png` | REVISION — teaching flow | Show **unembryonated eggs** passed in faeces and embryonation in freshwater; depict miracidium as ciliated, cercaria as tailed/non-ciliated, and place sporocyst → redia → cercaria wholly in the lymnaeid snail. Clarify excystation in duodenum and migration through intestinal wall/peritoneal cavity to liver. |
| `ascaris_lumbricoides_anatomy_plate.png` | FAIL — anatomical | Female vulva is too posterior (should be ventral, near anterior/middle-third junction); anus should be ventral and subterminal, not at tail tip; ovaries are slender tortuous tubes, not clusters of ova; show paired uteri and distinct gut. The male testis is a long tubular organ, not a large isolated sac; validate cloaca and spicules. Redraw underlying anatomy and labels. |
| `ascaris_lumbricoides_life_cycle_diagram.png` | REVISION — teaching flow | Replace misleading “In human host” title: lifecycle includes **soil maturation**. Fresh faecal eggs are not infective; only embryonated eggs are. Confirm intestinal hatch, portal/systemic lung migration, ascent and reswallowing. Do not teach the 2–4 week interval as fixed. |

## Required native type-study plate inventory
The existing `NativeCurriculumManifest.kt` requires **six separately reviewable IDs**:
- Fasciola: `external`, `digestive-reproductive`, `life-cycle`.
- Ascaris: `male-female-external`, `female-internal`, `life-cycle`.

Four generic composite drawings do **not** satisfy these six required plates; the external and internal views require separation where necessary.

## Integration decision
1. **Do not copy** failed or revision-status generated bitmaps into `native-app/app/src/main/assets/unit3/` or `type_studies/` as authoritative lessons.
2. Keep the currently existing CI-generated historical reference images and `AcademicWorkStatus.DRAFT_UNVERIFIED` unchanged; their own provenance/anatomy review is still open.
3. Corrected factual bilingual anatomy and lifecycle paragraphs are committed in `UnitIIILessons.kt` on this review branch. This is lesson **content integration**, not new image integration.
4. Redraw source geometry as scientifically faithful raster pencil plates; check all leader endpoints against the underlying structures **before** any coordinates/hotspots are attached.
5. After artwork insertion into native assets, confirm decoder/pixel integrity, orientation, no cropping at normal/200% Tamil font, English/Tamil captions, matched hotspots, offline interactions, audio, screenshots and **real-device** handling.
6. Record an exact-commit CI green run, independent zoological reviewer signoff and physical-device QA before promotion. Keep academic release HOLD until all three pass.

## References
- CDC DPDx — Fascioliasis: https://www.cdc.gov/dpdx/fascioliasis/
- CDC DPDx — Ascariasis: https://www.cdc.gov/dpdx/ascariasis/
- Hanna et al. (2015), *Fasciola hepatica*: Histology of the Reproductive Organs, Fig. 1 and arrangement of gonads: https://pmc.ncbi.nlm.nih.gov/articles/PMC4584266/

## Provenance
These four candidate PNGs were generated inside the ChatGPT conversation, not sourced from the GitHub repository. They are review-only source candidates, and **are not in this commit**. Do not claim a native APK or revised raster build from this report alone.
