# Paramecium reproduction — plate-by-plate production QA

**Status: DRAFT; no image accepted or integrated.** Based on `paramecium-reproduction-png-acceptance-2026-10-30.md`. This checklist is an audit form, not scientific sign-off.

## Deliverables

| IDs | Required state | Image | Zoology | Tamil | Rights | Emulator pixels |
|---|---|---|---|---|---|---|
| F01–F06 | Vegetative cell; MIC replication; MIC segregation; MAC division; transverse furrow; separated proter/opisthe | PENDING | PENDING | PENDING | PENDING | PENDING |
| C01–C10 | Pairing; meiosis; degeneration; pronuclei; exchange; synkaryon; first division; separation; further divisions; differentiation/fission | PENDING | PENDING | PENDING | PENDING | PENDING |

Each ID requires **its own** lossless PNG, ideally >=1800 px long side, with a recorded SHA-256. Do not upscale the existing fuzzy source crops and call them high-resolution originals.

## Acceptance record required for EACH ID

- PNG path and SHA-256:
- Source creator, source URL or user-supplied provenance, licence/permission, attribution, transformations, access date:
- Species and observed-versus-schematic status:
- Number of **cells**, nuclei, MIC ploidy and MAC status:
- Morphological landmarks and each label leader-line endpoint:
- English label audit and Tamil textbook-language review (named reviewer/date):
- Scientific reviewer/date, rights reviewer/date:
- Android screenshot filenames for English/Tamil, 100%/200%, zoom and close:
- Result: **PENDING / REJECTED / APPROVED** (never infer approval from CI):

## Hard failures

1. C02/C03: `3n` for three separate haploid nuclei is false; use `three nuclei, each n`.
2. C07/C08: no mature new macronucleus before the intervening postzygotic nuclear divisions; follow cited *P. caudatum* sequence.
3. F05: furrow orientation must be transverse to the anterior-posterior axis.
4. F06/C10: daughter cells appear only after cytokinesis.
5. No generic vector/Canvas substitutes, unrelated anatomical images, invented scale bars or false chromosome counts.
6. No PNG integration until zoology, Tamil and rights gates pass separately.

## Android integration gate

Keep the existing Kotlin/Jetpack Compose reader, full-screen zoom route, and 43 validated A5 chapters unchanged. Keep `ParameciumProcessPngIndex` reproduction entries null until an individual image is approved. After integration, require exact-commit build/JVM and offline API34 instrumentation, actual pixel inspection at 100% and 200% Tamil font scales, and independent physical-device verification. **Production release: HOLD.**
