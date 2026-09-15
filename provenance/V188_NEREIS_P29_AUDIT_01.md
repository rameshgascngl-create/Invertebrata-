# INVERTEBRATA v1.8.8 — Nereis p.29 formal audit 01

## Controls and evidence
AUDIT ONLY. No Nereis SVG was created/modified/reconstructed/corrected. No remediation of another organism, APK/AAB generation, workflow modification/dispatch, device QA or build-pipeline reconciliation occurred.

Authoritative visual reference: recovered 47-page `Invertebrata draft(1).pdf`, p.29, directly inspected. Page title: `NEREIS — Type study of a marine polychaete annelid`.
Frozen instructional inventory: `provenance/V188_NEREIS_P29_INVENTORY_01.md`.
Accepted lesson: `u4-nereis` scope recovered from accepted app material. Historical Batch-2 `PDFREF nereis:p30` is superseded by p.29.

## Active production candidates
Current `ci/reconcile_v188_svg_batch2.py` defines five active Nereis candidates and maps them to `u4-nereis`:
- `nereis_external` / `data-v188-plate="nereis-external"`
- `nereis_parapodium` / `data-v188-plate="nereis-parapodium"`
- `nereis_digestive` / `data-v188-plate="nereis-digestive"`
- `nereis_circulatory` / `data-v188-plate="nereis-circulatory"`
- `nereis_nervous` / `data-v188-plate="nereis-nervous"`

No active Batch-2 Nereis excretory or reproduction/epitoky/development candidate is mapped to `u4-nereis`.

## Row-level audit
| Requirement ID | Figure/System | Frozen Requirement Type | Existing Candidate | p.29 | Lesson Match | Biology | Completeness | Connections | Orientation | Leaders | EN | TA | Embedded | 360px | Enlarged | Overall | Exact Finding | Rectification Authorized? |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| NEREIS-01 | External morphology | INDEPENDENT FIGURE REQUIRED | `nereis_external` | YES | YES | FAIL | FAIL | PENDING | PASS | FAIL | PASS | TERMINOLOGY REVIEW PENDING | PASS — mapped to `u4-nereis` | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | FAIL | Candidate establishes elongated segmented body, anterior head region, segmental lateral appendages and pygidium, but collapses p.29's prostomium/peristomium into one `Head: prostomium + peristomium` label and omits the reference-supported eyes, antennae, palps, tentacular cirri and anal cirri. The `Segmental parapodia` leader terminates on the lower setal/appendage-line region rather than unambiguously on a parapodial lobe. | YES — FAIL, TARGETED CORRECTION AUTHORIZED |
| NEREIS-01A | Enlarged anterior/head region | SATISFIED AS SUBFIGURE requirement | No adequate dedicated/inset subfigure in active candidate | YES | YES | — | — | — | — | — | — | TERMINOLOGY REVIEW PENDING | — | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | NOT YET CREATED AS REQUIRED SUBFIGURE | p.29 separately resolves prostomium, eyes, antennae, palps, tentacular cirri and mouth. The active external plate has only a broad combined head label and cannot satisfy this frozen subfigure obligation. This does not create a new independent master requirement; correction belongs within/alongside NEREIS-01. | YES — TARGETED SUBFIGURE CREATION/CORRECTION WITH NEREIS-01 AUTHORIZED |
| NEREIS-02 | Biramous parapodium | INDEPENDENT FIGURE REQUIRED | `nereis_parapodium` | YES | YES | FAIL | FAIL | FAIL | PENDING | FAIL | FAIL | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | FAIL | Candidate intends notopodium, neuropodium, dorsal/ventral cirri, notochaetae/neurochaetae and acicular support, but its geometry does not cleanly reproduce the p.29 dorsal-versus-ventral biramous organisation: the two main rami are drawn as left/right arms from a common central stem, making dorsal/ventral interpretation ambiguous. `Dorsal cirrus / notochaetae` and `Ventral cirrus / neurochaetae` are conflated paired labels rather than anatomically separate structures. `Aciculum-supported parapodial base` does not identify the separate internal acicula shown by p.29. | YES — FAIL, TARGETED CORRECTION AUTHORIZED |
| NEREIS-03 | Digestive system | INDEPENDENT FIGURE REQUIRED | `nereis_digestive` | YES | YES | PENDING | PENDING | PENDING | PASS | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | Candidate provides a continuous mouth/anterior pharyngeal region → oesophagus → intestine → anus pathway and explicitly references an eversible pharynx with jaws. However p.29 separately resolves mouth, buccal cavity, pharynx/proboscis, oesophagus, intestine, rectum and anus, while the current simplified geometry/labels do not establish all of those subdivisions or exact jaw endpoints. Evidence is insufficient to call this a demonstrated biological defect rather than an unresolved scope/detail question; no redraw is authorized while PENDING. | NO — PENDING IS NOT REDRAW AUTHORIZATION |
| NEREIS-03A | Eversible pharynx/proboscis + jaws | SATISFIED AS SUBFIGURE requirement | anterior part of `nereis_digestive` | YES | YES | PENDING | PENDING | PENDING | PENDING | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | The production plate names the eversible pharynx and jaws and contains paired anterior shapes, but source inspection alone does not establish that these are independently legible, correctly targeted jaw structures comparable with the dedicated p.29 eversible-pharynx panel. | NO |
| NEREIS-04 | Closed circulatory system | INDEPENDENT FIGURE REQUIRED | `nereis_circulatory` | YES | YES | PENDING | PENDING | PENDING | PASS | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | Candidate depicts dorsal and ventral longitudinal vessels connected by repeated segmental vessels and labels parapodial circulation, directionally matching p.29's closed-system domain. Exact p.29 vessel identities, parapodial branches and leader endpoints cannot be promoted to PASS from this simplified source geometry alone. | NO — PENDING IS NOT REDRAW AUTHORIZATION |
| NEREIS-05 | Nervous system | INDEPENDENT FIGURE REQUIRED | `nereis_nervous` | YES | YES | PENDING | PENDING | PENDING | PASS | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | Candidate shows cerebral ganglia, circumpharyngeal connectives and a ventral ganglionated cord, matching the accepted lesson at overview level. p.29 additionally resolves subpharyngeal ganglion/ventral nerve cord/segmental ganglia relationships; exact topology and leaders require closer accepted-source comparison before PASS or FAIL. | NO — PENDING IS NOT REDRAW AUTHORIZATION |
| NEREIS-06 | Excretory system | INDEPENDENT FIGURE REQUIRED | NONE | YES | YES — nephridia | — | — | — | — | — | — | TERMINOLOGY REVIEW PENDING | NO | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | NOT YET CREATED | p.29 contains a dedicated excretory panel showing a metanephridial/nephridial tubule arrangement and openings. The accepted lesson explicitly requires nephridia. No active Nereis excretory candidate exists in the Batch-2 `u4-nereis` mapping. | YES — NOT YET CREATED, TARGETED CREATION AUTHORIZED |
| NEREIS-07 | Reproduction / epitoky / development | INDEPENDENT FIGURE REQUIRED | NONE | YES | YES — type-study/revision includes reproduction/epitoky; accepted lesson includes trochophore development | — | — | — | — | — | — | TERMINOLOGY REVIEW PENDING | NO | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | NOT YET CREATED | p.29 explicitly teaches dioecy, external fertilisation, trochophore larva and epitoky/heteronereis reproductive transformation. No active Nereis reproduction/epitoky/development plate is mapped to `u4-nereis`; external morphology cannot substitute for this process/transformation domain. | YES — NOT YET CREATED, TARGETED CREATION AUTHORIZED |
| NEREIS-07A | Trochophore larva | SATISFIED AS SUBFIGURE requirement | NONE | YES | YES | — | — | — | — | — | — | TERMINOLOGY REVIEW PENDING | NO | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | NOT YET CREATED AS REQUIRED SUBFIGURE | Trochophore is explicitly present in p.29 and accepted lesson development scope. It need not be a separate master SVG, but must be a readable stage/subfigure within the later NEREIS-07 treatment. | YES — creation only as part of authorized NEREIS-07 treatment |

## Derived counts — seven frozen independent requirements only
PASS: 0
FAIL: 2 — NEREIS-01, NEREIS-02
PENDING: 3 — NEREIS-03, NEREIS-04, NEREIS-05
NOT YET CREATED: 2 — NEREIS-06, NEREIS-07

Subfigure obligations are tracked separately and do not inflate independent-figure counts:
- NEREIS-01A: NOT YET CREATED AS REQUIRED SUBFIGURE of NEREIS-01
- NEREIS-03A: PENDING within NEREIS-03
- NEREIS-07A: NOT YET CREATED AS REQUIRED SUBFIGURE of NEREIS-07

## Terminology gates
English terminology is broadly aligned for the active candidates except NEREIS-02, where combined labels obscure the anatomical distinction between cirri and chaetal bundles/acicula.
Tamil specialised Nereis terminology remains `TERMINOLOGY REVIEW PENDING` throughout. No invented Tamil equivalent is used to force closure.

Reference-resolution blockers: none for p.29 identity or the frozen visual domains. Some PENDING rows require closer topology/leader resolution, not a substitute reference.

## Later evidence-authorized remediation — NOT STARTED
- NEREIS-01 — FAIL: targeted external/head correction authorized, including its required head subfigure; do not broaden scope opportunistically.
- NEREIS-02 — FAIL: targeted parapodial topology/label/leader correction authorized.
- NEREIS-06 — NOT YET CREATED: targeted independent excretory plate creation authorized from p.29 + accepted lesson scope.
- NEREIS-07 — NOT YET CREATED: targeted reproduction/epitoky/development treatment authorized; trochophore is a subfigure obligation, not a separate master.
- NEREIS-03/04/05 — PENDING: NO redraw authorization merely because audit gates remain unresolved.

## Preserved project state
Pila remains frozen at `77ea6361919eec11fcefcadb283db2c042d94592`.
Asterias remains preserved at `1b8943506ea994acc0511d773f999dbc0c04d59f`; WVS biological topology PASS remains protected; five absent-figure candidates remain PROVISIONAL — INDEPENDENT-FIGURE NECESSITY CONFIRMATION REQUIRED.
Obelia inventory `fa9331782af7d5faf9aaf9859ff1cefbeedc0859` and audit `ad586f255bb2f804f7604a7688ddef5e9af3081f` remain unchanged; OBELIA-01/02/03 FAIL rows are not remediated.

NEREIS RECTIFICATION — NOT STARTED
OBELIA RECTIFICATION — NOT STARTED
BUILD PIPELINE RECONCILIATION REQUIRED — OPEN / BLOCKING BUILD ELIGIBILITY
BUILD — BLOCKED
APK — NOT GENERATED
WORKFLOW — UNCHANGED / NOT DISPATCHED
DEVICE QA — NOT STARTED
FINAL — NO
