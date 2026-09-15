# INVERTEBRATA v1.8.8 — Obelia p.16 formal audit 01

## Controls
Reference recovery: PASS — authoritative `Invertebrata draft(1).pdf`, 47 pages, p.16 directly inspected.
Inventory freeze: `provenance/V188_OBELIA_P16_INVENTORY_01.md`.
This is AUDIT ONLY. No SVG creation/correction, APK build, workflow modification/dispatch, device QA, or pipeline reconciliation is performed.

Historical Batch-2 PDFREF `obelia: p17` is superseded by the corrected authoritative map: Obelia = p.16.

## Active production candidates recovered
`ci/reconcile_v188_svg_batch2.py` defines and maps four active Batch-2 candidates to `u2-obelia`:
- `obelia_colony` / `data-v188-plate="obelia-colony"` — colony overview.
- `obelia_hydranth_gonangium` / `data-v188-plate="obelia-zooids"` — paired hydranth/gonangium detail.
- `obelia_medusa` / `data-v188-plate="obelia-medusa"` — medusa anatomy.
- `obelia_lifecycle` / `data-v188-plate="obelia-life-cycle"` — metagenetic life cycle.
The mapping is `M['u2-obelia']=['obelia_colony','obelia_hydranth_gonangium','obelia_medusa','obelia_lifecycle']`; these are active production candidates, not merely historical virtual-laboratory references.

## Formal audit table

| Requirement ID | Figure/Process | Frozen Requirement Type | Existing Candidate | p.16 | Lesson Match | Biology | Completeness | Connections/Sequence | Leaders | EN | TA | Embedded | 360px | Enlarged | Overall | Exact Finding | Rectification Authorized? |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| OBELIA-01 | Colony architecture | INDEPENDENT FIGURE REQUIRED | `obelia_colony` + required zooid-detail companion | YES | YES | FAIL | FAIL | FAIL | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS — mapped to `u2-obelia` | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | FAIL | Candidate shows hydrorhiza, hydrocaulus, hydranth/gastrozooid and gonangium/gonozooid, but does not depict or label the p.16/lesson-required perisarc and internal living coenosarc. Therefore colony wall/living-tissue topology is incomplete. | YES — FAIL, TARGETED CORRECTION AUTHORIZED |
| OBELIA-01A | Hydranth / gastrozooid detail | SATISFIED AS SUBFIGURE | `obelia_hydranth_gonangium` left subfigure | YES | YES | PENDING | PENDING | PENDING | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | Mouth + tentacles and gastrovascular cavity are represented. Exact hypostome/mouth differentiation and hydrotheca relationship visible on p.16 are not established by the current simplified geometry/labels; evidence does not justify a redraw until leader/topology resolution is completed. | NO — PENDING IS NOT REDRAW AUTHORIZATION |
| OBELIA-01B | Gonangium / gonozooid detail | SATISFIED AS SUBFIGURE | `obelia_hydranth_gonangium` right subfigure | YES | YES | PENDING | PENDING | PENDING | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | Gonotheca and medusa buds on blastostyle are represented and feeding-tentacle organisation is absent, preserving the feeding/reproductive distinction. Exact protective-covering/blastostyle/bud leader endpoints remain unresolved. | NO — PENDING IS NOT REDRAW AUTHORIZATION |
| OBELIA-02 | Medusa anatomy | INDEPENDENT FIGURE REQUIRED | `obelia_medusa` | YES | YES | FAIL | FAIL | FAIL | FAIL | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | FAIL | p.16 explicitly requires umbrella and velum. Current candidate labels manubrium/mouth, radial canals, ring canal, gonad and tentacles but does not label/differentiate umbrella or velum. More seriously, the `Manubrium / mouth` leader originates on the top of the bell rather than the dependent central manubrium/mouth region, so the leader endpoint is anatomically wrong. | YES — FAIL, TARGETED CORRECTION AUTHORIZED |
| OBELIA-03 | Life cycle / metagenesis | INDEPENDENT FIGURE REQUIRED | `obelia_lifecycle` | YES | YES | FAIL | FAIL | FAIL | PENDING | FAIL | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | FAIL | Candidate compresses p.16's explicit process into colony → medusa → `Gametes / fertilisation` → planula → settlement/young colony. It omits separate male/female medusae, explicit gamete stage identity/ploidy, fertilisation → zygote, and the zygote → planula transition. p.16's ploidy teaching (colony/medusa/zygote 2n; gametes n) is absent. The process therefore cannot teach the accepted metagenetic sequence accurately enough. | YES — FAIL, TARGETED CORRECTION AUTHORIZED |
| OBELIA-POLY | Polymorphism | NOT REQUIRED AS INDEPENDENT FIGURE | `obelia_colony` + `obelia_hydranth_gonangium` | YES | YES | PENDING | PENDING | PENDING | PENDING | PASS | TERMINOLOGY REVIEW PENDING | PASS | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED | PENDING | No separate polymorphism SVG is required. Existing colony/zooid treatment visibly distinguishes feeding hydranth/gastrozooid from reproductive gonangium/gonozooid, but overall satisfaction remains dependent on closure of OBELIA-01A/01B topology/leader gates. | NO |

## Inventory and candidate conclusions
Proposed visual domains considered: 7 (six p.16 domains plus polymorphism-as-separate-plate necessity question).
Frozen independent organism-level requirements: 3.
Subfigure-satisfied requirement classes: 3 (hydranth detail, gonangium detail, planula within life-cycle treatment). Planula is present in the life-cycle candidate but the parent life-cycle requirement FAILS, so this does not promote OBELIA-03.
Not independently required: 1 (polymorphism as a separate SVG).
NOT YET CREATED independent requirements: 0. All three frozen independent requirements have active production candidates.

## Derived audit counts — independent requirements only
PASS: 0
FAIL: 3 — OBELIA-01, OBELIA-02, OBELIA-03
PENDING: 0 at parent independent-requirement level
NOT YET CREATED: 0

Subfigure/derived rows:
- PENDING: OBELIA-01A, OBELIA-01B, OBELIA-POLY
- SATISFIED AS SUBFIGURE inventory obligations: hydranth, gonangium, planula (planula remains inside failed OBELIA-03 parent)
- NOT REQUIRED AS INDEPENDENT FIGURE: polymorphism

## Terminology and render gates
Tamil terminology: TERMINOLOGY REVIEW PENDING for every audited Obelia row. No Tamil technical equivalent is promoted merely because a Tamil caption exists.
360px and enlarged lesson-context gates: PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED. Isolated SVG source inspection is not contextual render evidence.

## Later remediation boundary — NOT STARTED
Evidence now authorizes later targeted correction only for:
1. OBELIA-01 colony architecture — add/resolve p.16-supported perisarc/coenosarc topology without opportunistic redesign.
2. OBELIA-02 medusa — restore p.16-required umbrella/velum differentiation and correct the manubrium/mouth leader/geometry.
3. OBELIA-03 life cycle — reconstruct the process topology to show the p.16/lesson-supported sexual transition, zygote, planula, settlement and ploidy distinctions.

OBELIA-01A and OBELIA-01B remain PENDING and are not independent redraw authorizations. Polymorphism does not authorize a new standalone SVG.

## Preserved external gates
Pila remains frozen at `77ea6361919eec11fcefcadb283db2c042d94592`.
Asterias remains preserved at `1b8943506ea994acc0511d773f999dbc0c04d59f`; WVS biological topology PASS is protected, and its five absent-figure inventory candidates remain provisional pending independent-figure necessity confirmation.
Nereis audit is NOT STARTED.

BUILD PIPELINE RECONCILIATION REQUIRED — OPEN / BLOCKING BUILD ELIGIBILITY
BUILD — BLOCKED
APK — NOT GENERATED
WORKFLOW — UNCHANGED / NOT DISPATCHED
DEVICE QA — NOT STARTED
FINAL — NO
