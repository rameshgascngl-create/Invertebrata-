# U72 — Paramecium reproduction plate and editorial gate (2026-10-27)

**Branch:** `native/u72-paramecium-reproduction-editorial-20261027`  
**Scope:** Unit I, `u1-paramecium`. This is a source-review record, **not academic acceptance**.

## Textbook corrections implemented

- Divided the existing chapter's reproduction section into four numbered UG/PG subsections while retaining the original four registered Paramecium type-study figures and 43 A5 chapter IDs.
- Clarified that the parental macronucleus is **initially present** when compatible conjugants pair; it degenerates during later nuclear reorganization, not automatically at the moment of pairing.
- Distinguished **micronuclear mitosis**, **macronuclear partitioning**, and **transverse cytokinesis** during fission. The anterior daughter is **proter**, posterior daughter **opisthe**.
- Clarified that **reciprocal haploid pronuclear exchange** precedes **diploid synkaryon** formation; subsequent nuclear divisions and macronuclear anlagen formation precede postconjugation cell multiplication.
- Added bilingual key points and a PG interpretation note. The Tamil terms remain subject to independent editorial approval.

## Image candidates generated in the working conversation (NOT integrated)

| Candidate | Native raster | Decision | Scientific concern |
|---|---|---|---|
| Transverse binary fission, four-stage illustration | PNG, 1774 × 887 | **REVIEW ONLY** | Inspect continuity of the original oral apparatus in the anterior proter through the constriction stage; verify furrow plane and each MIC/MAC leader endpoint |
| Eight-panel conjugation illustration | PNG, 1536 × 1024 | **REJECT** | Stage 2 labels parental macronucleus as degenerating too early; stage 7 presents a fully formed new macronucleus without showing the required postzygotic nuclear multiplication; some stage-to-stage contact and nuclear counts are ambiguous |

**No new reproduction PNG was copied to** `native-app/app/src/main/assets/`. **No new plate ID was registered or mapped** to `ParameciumProcessPngIndex`. Both `BINARY_FISSION` and `CONJUGATION` remain `null` in the simulator's production mapping. Existing unrelated anatomy crops must not be substituted.

## Approval criteria for future individual PNGs

1. Use separate high-resolution PNGs for **transverse binary fission** and **conjugation**, not a cropped general-anatomy plate, generic composite, or Canvas/vector fallback.
2. Fission: show one parent cell, MIC mitosis, MAC elongation/partition, **transverse** constriction, and **two** daughters with functioning oral organization. Proter is anterior; opisthe posterior.
3. Conjugation: compatible partners stay **two cells** through pairing, meiosis I/II, selection of one of four haploid products, pronuclear mitosis, reciprocal exchange and synkaryon fusion. Afterward show postzygotic nuclear multiplication, old MAC degeneration, differentiation of new MAC anlagen and later exconjugant divisions **in the correct order**. No premature new MAC.
4. All leader lines must terminate on the named structures. English and Tamil labels and caption must be proofread separately; if in-image Tamil labels are too dense, use numbered labels with a complete bilingual key in the chapter.
5. Record the source image, author, licence/permissions, modifications, and attribution before moving from review-only to production. Human zoology/Tamil sign-off remains necessary.
6. When accepted, package under `type_studies/paramecium/<plate-id>.png`, register the ID in the type-study inventory, add bilingual `bfig` in the correct section, update any separate simulator mapping, and run inventory/JVM/APK/emulator checks at 100% and 200% Tamil scales.

## References used to constrain chronology (not image reproduction permissions)

- OpenStax, *Biology 2e*, §23.3, Fig. 23.25, [Groups of Protists](https://openstax.org/books/biology-2e/pages/23-3-groups-of-protists). Describes four haploid products, three degeneration fates, reciprocal exchange, diploid synkaryon, postzygotic divisions and later daughter-cell formation.
- Yang, X. et al. (2007), [Detection of haploid nuclear death in living *Paramecium caudatum*](https://www.researchgate.net/figure/Schematic-representation-of-major-nuclear-stages-during-the-conjugation-of-Paramecium_fig2_228920202). The figure legend distinguishes meiotic products, paroral survivor selection, synkaryon divisions, macronuclear anlagen and residual parental MAC material.
- Andhra Pradesh Intermediate Zoology I, [Locomotion and Reproduction, Fig. 5.9](https://jnanabhumi.ap.gov.in/Uploads/Materials/Zoology-I.pdf), for the transverse-fission proter/opisthe teaching convention.

**Release status:** HOLD — no claim of approved academic artwork, Tamil validation, physical-device QA, production signing or store readiness.
