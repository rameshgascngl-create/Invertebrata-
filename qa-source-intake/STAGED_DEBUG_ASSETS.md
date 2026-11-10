# Three externally sourced PNG candidates installed in the QA build

**Reviewed intake commit:** `58422694` (GitHub Actions). Native Android debug-only assets:
`native-app/app/src/debug/assets/qa_source_plates/`.

| Teaching slot | Committed PNG | Creator / rights | Source | Limit |
| --- | --- | --- | --- | --- |
| Sycon development | 03_sycon_development.png | After F. E. Schulze; Encyclopædia Britannica 1911; Commons public-domain notice | https://commons.wikimedia.org/wiki/File:EB1911_Sponges_-_Development_of_Sycon_raphanus.jpg | Development from ovum through young sponge, not male gamete transfer |
| Asterias dissection | 09_asterias_dissection.png | Hans Hillewaert; CC BY-SA 3.0 | https://commons.wikimedia.org/wiki/File:Asterias_rubens,_dissection.svg | Numbered original with published 1–11 key; PNG rendering |
| Asterias larvae | 10_asterias_larval_reference.png | Ernst Haeckel; edited crop Chiswick Chap; CC BY-SA 3.0 | https://commons.wikimedia.org/wiki/File:Haeckel_Asteridea_Larvae.jpg | Historical panels 2 scaphularia, 3 bipinnaria, 4 young brachiolaria |

**Not promoted:** Main assets and all 41 visually accepted figures are unchanged. These source PNGs replace held graphics for student inspection in *debuggable QA APKs only*. Android release builds cannot load them because they are absent from the main and release asset source sets. On ordinary release, original held figures and cautions remain until two independent reviewer approvals and rights checks.

**Not yet replaced:** Paramecium oral detail (source is whole-cell illustration), Sycon external, Obelia medusa and complete life cycle, Ascaris female longitudinal anatomy (source is a transverse micrograph), the full earthworm excretory distribution (source is a single metanephridium), Pila external anatomy, and Leishmania two-form morphology.

For source review downloads and SHA-256 receipt, see the `zoology-source-verified-review-png` artifact at run 42757225058. The exact bytes installed are pinned to Git blobs by `qa-source-intake/verify_staged_debug_assets.py`.
