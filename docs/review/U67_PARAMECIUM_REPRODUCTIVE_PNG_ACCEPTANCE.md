# Unit I — Paramecium reproductive PNG acceptance specification (U67)

Status: **REVIEW SPECIFICATION ONLY — NOT APPROVED ARTWORK**. This file adds no student-facing substitute image. Preserve the 43 A5 chapters, existing verified PNGs and full-screen viewer.

## Deliverables
- Separate **transverse binary fission** PNG sequence, with consistent *P. caudatum* morphology, transverse cytokinesis, micronuclear mitosis and macronuclear amitotic division; avoid depicting mitosis of the macronucleus.
- Separate **conjugation** PNG sequence. Pairing and cytoplasmic bridge; micronuclear meiosis and degeneration of three haploid products (species-dependent account must be specified); mitotic division of surviving haploid nucleus to migratory/stationary pronuclei; reciprocal exchange; fusion to synkaryon; separation of exconjugants; subsequent nuclear divisions/reorganisation and later macronuclear differentiation. **Do not place a mature replacement macronucleus before the requisite post-fusion nuclear reorganisation.**
- Each sequence: independent full-resolution PNG masters, one stage per plate, consistent anterior/posterior orientation, separate bilingual caption/legend, verified leader-line endpoints and documented source/derivation.

## Terminology gate
| English | Tamil review candidate |
|---|---|
| Transverse binary fission | குறுக்குப் இருபிளவுறுதல் |
| Conjugation | இணைவு |
| Macronucleus | பெருங்கரு |
| Micronucleus | சிறுகரு |
| Migratory pronucleus | இடம்பெயரும் முன்கரு |
| Stationary pronucleus | நிலையான முன்கரு |
| Synkaryon | இணைந்த கரு |
| Exconjugant | இணைவுக்குப் பிந்தைய தனியுயிரி |

Tamil terms above are **editorial candidates**, not final approved labels.

## Artwork QA gates
1. Verify stage chronology and nuclear ploidy against a species-specific authoritative account; record reviewer, source, page/figure, and date.
2. Inspect each plate at native pixels and 200% zoom; check that leaders terminate on the named structure rather than adjacent cilia or vacuoles.
3. Validate image dimensions, lossless PNG decoding, colour contrast and label legibility at 100% and 200% Tamil font scales.
4. Capture both emulator screenshots and UI hierarchy for the exact commit, and compare against user-supplied references.
5. Record creator, source URL, original license, attribution, derivative changes and redistribution conditions for **each** asset.
6. Reject the existing eight-panel bilingual conjugation poster until chronological errors are corrected and independently reviewed.
7. Do not use Canvas ProcessSketch, fabricated placeholder diagrams or reuse an unrelated anatomy crop for reproductive stages.

## Source rights reference
The Deuterostome *Paramecium caudatum* anatomy SVG is CC BY-SA 4.0: https://commons.wikimedia.org/wiki/File:Paramecium_diagram.svg . Its source page documents the author, attribution and share-alike requirements. This anatomical diagram is **not** itself a binary-fission or conjugation plate.

## Release gates
Zoology review: HOLD. Tamil editorial review: HOLD. Asset rights review: HOLD. Exact-commit Kotlin/JVM and emulator verification: NOT RUN FOR THIS SPECIFICATION. Physical-device production release: HOLD.


## U68 — Species-specific cytological reference and plate storyboard (24 October 2026)

**Primary reference for the intended *P. caudatum* conjugation plate**: Yang, X., Gao, X. & Shi, X. (2007), “Detection of haploid nuclear death in living Paramecium caudatum”, *Japanese Journal of Protozoology* 40, pp. 123–130, Fig. 1(a–k): https://protistology.jp/journal/jjp40/123-130Yang2.pdf . Reference images are **for scientific comparison only** pending explicit image-reuse rights review.

**Additional species-specific reference**: Haga (2022), “Micromanipulation in Paramecium: From non-mendelian inheritance to the outlook for versatile micromachines”, *Journal of Eukaryotic Microbiology*, https://pmc.ncbi.nlm.nih.gov/articles/PMC9543784/ . Distinguish the two postconjugational divisions and subsequent karyonide formation from the initial exchange event.

### Conjugation stage-to-image storyboard
| Plate | Essential depiction | Explicit rejection criterion |
|---|---|---|
| C01 | Complementary mating types pair, micronuclei and parental macronuclei identifiable | Two cells shown as permanently fused |
| C02 | Crescent-shaped micronuclear first meiotic prophase | Macronucleus presented as meiotic nucleus |
| C03 | First and second micronuclear meiotic divisions | Arbitrary nuclear count/ploidy |
| C04 | Three products degenerate; remaining haploid product moves toward paroral region | Degenerating products shown as future macronuclei |
| C05 | Third prezygotic division forms migratory and stationary gametic nuclei | Pronuclei incorrectly shown diploid |
| C06 | Reciprocal migratory pronucleus exchange and diploid synkaryon formation | Exchange omitted or fusion before exchange |
| C07 | First, second and third postzygotic synkaryon divisions: eight products | Mature macronucleus shown immediately after fusion |
| C08 | Anterior presumptive micronuclei, posterior macronuclear anlagen; parental MAC fragmentation and micronuclear selection | Nuclear differentiation depicted before postzygotic divisions |
| C09 | Exconjugant maturation and two successive cell divisions yielding karyonides | Conjugation itself represented as binary fission |

**Important sequence correction**: In Yang et al. Fig. 1, cell rounding and the three synkaryon divisions are shown before nuclear differentiation. The figure also depicts macronuclear fragmentation and later selection/degeneration of presumptive micronuclei. Do not infer a single universal timing sequence from *P. multimicronucleatum*: its documented postzygotic divisions are useful comparison, but its nuclear counts and timing must not be silently transferred to *P. caudatum*.

### Fission plate storyboard
| Plate | Required biological event |
|---|---|
| F01 | Vegetative cell, macronucleus and micronucleus distinct |
| F02 | Micronuclear mitosis begins; macronucleus elongates |
| F03 | Macronuclear amitotic constriction and micronuclear separation |
| F04 | Transverse furrow and reorganisation of oral apparatus in developing daughters |
| F05 | Two separated daughter cells, each with appropriate nuclei and oral structures |

**Rights checkpoint**: Do not copy Yang et al. Fig. 1 or the Haga figures into distributable assets until licensing or permission is documented. These citations validate the intended anatomy/chronology, not asset redistribution.

**QA state**: scientific storyboard prepared; PNG artwork NOT created; Tamil editorial NOT signed off; emulator/device verification NOT performed for this documentation-only change.
