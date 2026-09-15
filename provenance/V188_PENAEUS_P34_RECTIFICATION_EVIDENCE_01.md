# INVERTEBRATA v1.8.8 — Penaeus p.34 targeted rectification evidence 01

## Frozen audit baseline
This record supplements and does not rewrite or weaken:
- `652fb5a5962dffae143be6e98927d73543b534d1` — `provenance/V188_PENAEUS_P34_PLATE_AUDIT_01.csv`
- `697a146b6ac803819ef2d1c4ae682a78a1f7ff2c` — `provenance/V188_PENAEUS_P34_AUDIT_REPORT_01.md`

Authoritative visual/anatomical reference: `Invertebrata draft.pdf` p.34. Accepted lesson evidence was used only where it supports the same Penaeus teaching scope.

## Evidence-supported lesson scope
Accepted lesson evidence supports: cephalothorax/carapace, anterior rostrum, six-segmented abdomen, stalked compound eyes, antennules, antennae, three maxillipeds, five pereiopods, pleopods, uropods/telson; serially homologous appendages modified for sensation, feeding, walking, swimming and reproduction; foregut gastric-mill/filtering regions and hepatopancreas; antennal/green gland opening at antenna base; sexes separate. It also supports the roster: antennule, antenna, mandible, maxillula/maxillule, maxilla, three maxillipeds, five pereiopods, first five abdominal somites with pleopods, sixth with uropods.

## Rectification type distinction
### Existing FAIL plates corrected/reconstructed
- PENAEUS-01: `batch2#penaeus_external` FAIL → revised candidate `PENAEUS-01-R1`. Defect addressed: rostrum was not anatomically distinct/leader endpoint was inadequate; required external landmarks were incomplete.
- PENAEUS-05: `batch2#penaeus_digestive` FAIL → revised candidate `PENAEUS-05-R1`. Defect addressed: gastric-mill region was not structurally differentiated and hepatopancreatic relationship was insufficient.
- PENAEUS-09: `batch2#penaeus_reproductive` FAIL → revised candidate `PENAEUS-09-R1`. Defect addressed: generic sex-unspecific gonad schematic replaced by separate male/female subfigures.

### NEW REQUIRED PLATES — PREVIOUSLY NOT YET CREATED
- PENAEUS-03: specialised appendage series → new parent treatment with `PENAEUS-03-OVERVIEW`, `PENAEUS-03-CEPHALIC`, `PENAEUS-03-THORACIC`, `PENAEUS-03-ABDOMINAL`. These are child figures of ONE organism-level requirement.
- PENAEUS-08: excretory system → new `PENAEUS-08-N1`, centred on the Penaeus antennal/green gland and antenna-base opening relationship. Finer sac/bladder/duct topology is explicitly marked `REFERENCE RESOLUTION REQUIRED` rather than invented.

## Protected PENDING rows
No source reconstruction is authorized or performed for:
- PENAEUS-02 `master-penaeus-appendage`
- PENAEUS-04 `batch2#penaeus_respiratory`
- PENAEUS-06 `batch2#penaeus_circulatory`
- PENAEUS-07 `batch2#penaeus_nervous`

## Terminology control
English labels use the accepted lesson vocabulary and p.34 scope. Tamil remains an independent gate. The new/revised SVGs deliberately expose `TERMINOLOGY REVIEW PENDING` in their Tamil legend metadata instead of inventing unverified technical equivalents. Existing Tamil lesson terms may support general names, but specialised appendage, gastric, green-gland and reproductive-duct terminology is not promoted without a dedicated terminology audit.

## Unsupported-detail controls
- PENAEUS-03 does not force every specialised appendage into one generic biramous shape. The existing generalized biramous master remains separate.
- PENAEUS-05 differentiates a gastric-mill/filtering region but does not invent named ossicles/teeth unsupported by the accepted source.
- PENAEUS-08 does not invent finer excretory sac/bladder/duct connections.
- PENAEUS-09 separates male and female systems but marks finer internal duct/opening micro-topology as `REFERENCE RESOLUTION REQUIRED` where the accepted project evidence is insufficient.

## Embedding and downstream gates
`ci/v188_penaeus_targeted_rectification.py` appends only these five requirement treatments to `u4-penaeus` in both payload copies. It does not delete theory, alter language architecture, run Gradle, reconcile the build pipeline, or modify the workflow. The corrected/new candidates require post-rectification re-audit before any PASS promotion.

Exact 360px and enlarged lesson-context gates remain `PENDING — ACCEPTED-SOURCE CONTEXTUAL RENDER REQUIRED`. Pipeline reconciliation is downstream and was not used to manufacture render evidence.
