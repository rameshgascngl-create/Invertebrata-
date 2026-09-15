# INVERTEBRATA v1.8.8 — Formal Plate Audit 01

Scope: source-level audit and targeted-correction triage only. No APK build, no device QA, no bulk SVG production.

## Governing controls preserved
- Phase-1 reconciliation: `a0ff3e4ad2d37df88dfed718d7e6adf90430b848`
- Audit-mode report: `f799549dbcee684168c509ef666be25108bb9a39`
- Corrected page-map update: `09a14733721aa5e76742516a31e9606afdcd7e3d`

## High-risk decisions
1. **Sycon master:** biological topology remains PASS only. Overall row remains PENDING. The 360px source specimen fails because the in-SVG flow sentence is too small for dependable instructional reading; leader endpoints and Tamil terminology remain open.
2. **Fasciola reproductive master:** FAIL. The current simplified plate does not independently resolve the full male-duct, vitelline/ootype and common-genital connection topology required for a complete reproductive-system teaching plate. Targeted correction is justified.
3. **Ascaris reproductive systems:** female and male requirements are audited independently. The existing combined-sex schematic is insufficient for independent topology/connection verification; both are FAIL pending targeted sex-specific correction.
4. **Earthworm Batch-1 composite:** remains hard FAIL for mobile/pedagogical congestion. It is not downgraded to PENDING. Existing system-specific Batch-2 candidates are the corrective route and remain individually PENDING until reference + render audit.
5. **Penaeus generalised biramous master:** valid as a general biramous-plan plate but overall PENDING because Tamil terminology remains open. It does not satisfy the independent specialised appendage requirement, which remains NOT YET CREATED.
6. **Pila:** overview remains PENDING; p41 supports Pila-specific dual respiratory organisation and major systems, so a generalized molluscan plan cannot substitute.
7. **Asterias WVS:** biological topology PASS; overall PENDING. Corrected visible reference is p44. p45 is unavailable and is not used as visible evidence.
8. **Obelia/Nereis:** reference correspondence is established from corrected p16/p29, but individual candidate topology/leader/mobile/Tamil audits remain PENDING.
9. **Paramecium CV/ciliary organisation:** remains NOT YET CREATED. Corrected p5 visibly supports body ciliation and anterior/posterior contractile-vacuole organisation. Targeted remediation is permitted; unsupported detail must not be invented.

## Build-workflow blocker discovered during audit
The v1.8.8 debug workflow is correctly manual-dispatch only, but its current steps apply `reconcile_r2.py` and `reconcile_v188.py` only. It does **not** currently apply the later Batch-1/Batch-2/master/Batch-3 illustration integration scripts. Therefore even after future source-audit PASS, the workflow must not be dispatched until it is deliberately reconciled to the accepted illustrated source state. This is recorded now only as a blocker; the workflow is not modified during this audit.

## Evidence limits
The current audit includes direct inspection of corrected reference PDF pages and SVG source definitions, plus representative 360px/enlarged source renders for the master specimens. Full actual-lesson-context rendering for every candidate is not yet complete. Such rows remain PENDING. No source render is represented as Android WebView or physical-device QA.

## Gate
REFERENCE MAPPING: PASS
MASTER PENCIL STYLE: ESTABLISHED
SVG CREATION/EMBEDDING: IN PROGRESS
PER-FIGURE AUDIT: ACTIVE
TAMIL TERMINOLOGY AUDIT: ACTIVE/PENDING
BUILD: BLOCKED
DEVICE QA: NOT STARTED
FINAL: NO
