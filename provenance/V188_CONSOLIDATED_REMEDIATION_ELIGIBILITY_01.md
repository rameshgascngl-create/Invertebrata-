# INVERTEBRATA v1.8.8 — consolidated remediation eligibility 01

Controlling rule: only previously established FAIL or confirmed NOT YET CREATED rows may be modified. PENDING, biological PASS, provisional, reference-resolution-required, satisfied-as-subfigure and not-required-as-independent rows are protected unless an earlier audit explicitly authorized correction.

| Organism | Requirement | Current State before this pass | Evidence | Reference | Eligible? | Reason |
|---|---|---|---|---|---|---|
| Sycon | canal-flow master | BIOLOGICAL TOPOLOGY PASS / overall PENDING | `47ade752...` | p.12–13 corrected map | NO | topology protected; presentation/Tamil/render gates do not authorize anatomical redraw |
| Fasciola | reproductive system | FAIL | `47ade752...` | p.21 | YES | full male-duct, vitelline/ootype and common-genital connection topology not independently resolved |
| Ascaris | female reproductive system | FAIL | `47ade752...` | p.25 | YES | combined-sex schematic insufficient for independent female topology |
| Ascaris | male reproductive system | FAIL | `47ade752...` | p.25 | YES | combined-sex schematic insufficient for independent male topology |
| Earthworm | six remediated system plates | biology corrected; overall PENDING | `237a4a3...`, `3bab053...`, `0079f9f...` | p.30 | NO | prior FAILs already remediated; remaining Tamil/render/reference gates do not authorize redraw |
| Penaeus | PENAEUS-01/03/05/08/09 | post-rectification PENDING | `2ab2b44...` | p.34 | NO | later remediation supersedes old FAIL/NYC state; no current FAIL or NYC remains |
| Penaeus | PENAEUS-02/04/06/07 | PENDING | `2ab2b44...` | p.34 | NO | protected PENDING rows |
| Pila | external/shell | FAIL | `77ea636...` | p.41 | YES | p.41 landmarks incomplete |
| Pila | pallial/dual respiration | FAIL | `77ea636...` | p.41 | YES | diagnostic siphon/nuchal-lobe, mantle-cavity, current and osphradium relationships missing |
| Pila | digestive + radula | FAIL | `77ea636...` | p.41 | YES | collapsed gut landmarks; crop/anus/radula requirement incomplete |
| Pila | circulatory | BIOLOGICAL CORE PASS / PENDING | `77ea636...` | p.41 | NO | protected biological core |
| Pila | excretory / Organ of Bojanus | NOT YET CREATED | `77ea636...` | p.41 | YES | frozen independent requirement with no active candidate |
| Pila | nervous/sensory | FAIL | `77ea636...` | p.41 | YES | buccal ganglia and sensory set incomplete |
| Pila | reproductive | FAIL | `77ea636...` | p.41 | YES | generic schematic fails sex-specific topology |
| Asterias | WVS | BIOLOGICAL TOPOLOGY PASS / PENDING | `1b894350...` | p.44 | NO | protected |
| Asterias | oral/aboral/nervous | biological core PASS / PENDING | `1b894350...` | p.44 | NO | protected PENDING |
| Asterias | digestive | FAIL | `1b894350...` | p.44 | YES | mouth→oesophagus→cardiac/pyloric stomach→caeca→intestine→rectum→anus not independently resolved |
| Asterias | reproductive | FAIL | `1b894350...` | p.44 | YES | plate too narrow for p.44 reproductive scope |
| Asterias | five absent candidates | PROVISIONAL independent-figure necessity unresolved | `1b894350...` + user freeze | p.44 | NO | explicit freeze: do not create in this pass |
| Obelia | OBELIA-01 colony | FAIL | `ad586f25...` | p.16 | YES | perisarc/coenosarc topology absent |
| Obelia | OBELIA-02 medusa | FAIL | `ad586f25...` | p.16 | YES | umbrella/velum missing and manubrium/mouth leader wrong |
| Obelia | OBELIA-03 life cycle | FAIL | `ad586f25...` | p.16 | YES | sexual stages, zygote and ploidy sequence incomplete |
| Obelia | hydranth/gonangium subfigures | PENDING | `ad586f25...` | p.16 | NO | pending subfigures protected |
| Nereis | NEREIS-01 external | FAIL | `1200c10...` | p.29 | YES | head landmarks incomplete and parapodium leader ambiguous |
| Nereis | NEREIS-02 parapodium | FAIL | `1200c10...` | p.29 | YES | dorsal/ventral biramous topology and cirri/chaetae/acicula distinctions fail |
| Nereis | NEREIS-03/04/05 | PENDING | `1200c10...` | p.29 | NO | protected PENDING |
| Nereis | NEREIS-06 excretory | NOT YET CREATED | `1200c10...` | p.29 | YES | frozen independent requirement |
| Nereis | NEREIS-07 reproduction/epitoky/development | NOT YET CREATED | `1200c10...` | p.29 | YES | frozen independent requirement; trochophore is subfigure only |
| Paramecium | contractile-vacuole/ciliary organisation | NOT YET CREATED | `47ade752...`, phase-1 reconciliation | p.5 | YES | independent missing requirement remains unresolved in current history |

No later repository commit supersedes the Pila/Asterias/Obelia/Nereis states above. Penaeus is explicitly excluded because its later rectification sequence leaves all nine rows PENDING rather than FAIL/NYC.

BUILD PIPELINE RECONCILIATION REQUIRED — OPEN. BUILD BLOCKED. Workflow untouched/manual dispatch only.