# U74 — Paramecium reproductive image gate correction (25 October 2026)

## Decision: WITHDRAW premature student-facing integration

The U72/U73 code routed two **reference candidates** into the Unit I student reader/simulator even though `docs/review/U67_PARAMECIUM_REPRODUCTIVE_PNG_ACCEPTANCE.md` explicitly states **REVIEW SPECIFICATION ONLY — NOT APPROVED ARTWORK**, and U69 calls for image-by-image scientific and source-rights review. The red U67 GitHub Actions run #37 (24 October 2026 screenshot) cannot be considered a QA pass. Its first failed job/log has not been obtained, so do not claim a diagnosed CI root cause.

### Image-level examination
1. `native-app/INVERTEBRATA_Conjugation_reference_review.png` — **REJECT as a validated stage sequence**. The displayed nine-stage composite has duplicated stage number **5**, an ambiguous sequence/arrow flow, very small nuclei without sufficient stage-specific labels, and no independently verified species-specific nuclear chronology. It does not demonstrate the required meiotic products, reciprocal gametic exchange, three postzygotic divisions, macronuclear anlagen and karyonide formation in a reliably labelled *P. caudatum* series. This is **not** the user-provided eight-panel poster; that poster is separately rejected on chronology.
2. `native-app/INVERTEBRATA_Parker_reference_review.png` — **historical multipanel reference only**. Parker (1900) panel D depicts transverse division; the whole plate does not show F01–F05 in chronological order. It cannot serve as the required standalone five-stage binary-fission illustration. Image-level leader-line verification and source-country reuse status are outstanding.
3. The original three physiology PNG crops are derived from the same photographed pencil source and remain disclosed as **source-derived**, not certified independent anatomical plates.

### Engineering correction
- Restore `ParameciumProcess.BINARY_FISSION` and `CONJUGATION` image routes to `null` in the native simulator; restore corresponding textbook figure routes.
- Remove the two reference PNGs from `app/src/main/assets/unit1/reference/`, preserving originals under `native-app/` as unbundled review candidates.
- Restore existing fail-closed JVM assertions; add a regression test to prohibit premature rebundling.
- Update exact-commit CI preflight to assert missing student-facing reproductive PNGs, preserved reference candidates and no Canvas fallback.
- Keep the five-unit 43-chapter structure and the working 100%/200% zoom viewer unchanged.

### Scientifically acceptable replacement scope
**Binary fission:** five independent, consistently oriented high-resolution PNGs: vegetative nuclei; micronuclear mitosis/macronuclear elongation; macronuclear amitosis; transverse furrow plus oral apparatus reorganisation; two daughter cells with appropriate organelles. No invented cytology, and no full multipanel Parker substitute.

**Conjugation:** species-specific *P. caudatum* stages C01–C09 defined in U67/U68: mating pair; meiotic prophase; meiotic divisions; three degenerating haploid products; migratory/stationary pronuclei; reciprocal exchange/synkaryon; postzygotic divisions; macronuclear anlagen and parental MAC fragmentation; subsequent exconjugant divisions. Distinguish species-dependent accounts; do not promote an early replacement macronucleus or transfer *P. multimicronucleatum* counts to *P. caudatum*.

### Provenance and bilingual gate
Each future individual plate needs a master PNG, pixel dimensions and SHA-256, specific source and figure/page, rights/author/attribution/derivative status, morphological stage checklist, labelled English + independently checked Tamil terms and exact leader endpoints, with reviewer names/date. Example provisional terms: transverse binary fission — **குறுக்கு இருபிளவுறுதல்**; conjugation — **இணைவு**; macronucleus — **பெருங்கரு**; micronucleus — **சிறுகரு**; migratory pronucleus — **இடம்பெயரும் முன்கரு**; stationary pronucleus — **நிலையான முன்கரு**; synkaryon — **இணைந்த கரு**. These are *editorial candidates*, not certified Tamil labels.

Primary comparative reference: Yang, Gao & Shi (2007), *Detection of haploid nuclear death in living Paramecium caudatum*, Japanese Journal of Protozoology 40:123–130, Fig. 1. Additional review: Haga (2022), *Journal of Eukaryotic Microbiology* (PMC9543784). Scientific consultation does not grant rights to copy their figures.

### Verification and release gates
- Static source/asset inspection: to be recorded on corrective commit.
- GitHub Actions build/JVM and API34 emulator 100%/200% Tamil screenshots/XML: **PENDING exact-commit result**.
- Independent zoology review: **HOLD**; Tamil editorial review: **HOLD**; image source-rights review: **HOLD**; physical-device QA and production signing: **HOLD**.
- Do not call the app classroom-ready solely because CI becomes green.
