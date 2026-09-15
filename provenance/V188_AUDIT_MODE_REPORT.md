# INVERTEBRATA v1.8.8 — Per-Figure Audit Mode

No APK build is authorised by this report. No new bulk SVG production is authorised.

## Frozen reference rule

The corrected page references used by `ci/v188_audit_phase1_reconcile.py` supersede all historical approximate/+1 Batch-2 references. An older audit script must not be allowed to regress the manifest to historical numbering.

## Phase 1 — inventory reconciliation

Two independent requirements are explicitly protected from organism-level completion:

1. **Paramecium — contractile-vacuole / ciliary organisation (p5): NOT YET CREATED.** The existing external/oral-apparatus plate does not independently demonstrate the radiating contractile-vacuole canals and ciliary organisation required by the mapped reference/lesson.
2. **Penaeus — specialised appendage series, identity and attachment positions (p34): NOT YET CREATED.** A generalised biramous appendage plan is pedagogically useful but does not prove the serial identity/modification of the actual prawn appendages.

## Initial audit promotions/rejections

These are conservative source-audit findings; they are not physical-device QA.

| Candidate | Biological/source audit | Reason |
|---|---|---|
| Sycon canal-flow master | PASS for topology; mobile/leader/Tamil audits remain PENDING | Explicit sequence is ostia → incurrent canals → prosopyles → radial canals → apopyles → spongocoel → osculum, consistent with the mapped Sycon/canal-system reference. |
| Earthworm multi-system Batch-1 composite | FAIL | Anatomical systems are congested when combined; retained only as an overview and superseded for teaching by system-specific plates. |
| Penaeus generalised biramous appendage master | PENDING / insufficient for specialised requirement | General protopodite/endopodite/exopodite architecture cannot establish appendage identity, serial order, specialisation or attachment position. |
| Paramecium external/oral plate | PENDING as a separate plate; does not satisfy second requirement | External/oral organisation exists, but contractile-vacuole/ciliary organisation requires an independent figure and audit. |

## Audit protocol now controlling

For every PENDING production plate, audit in this order:

1. corrected PDF page + adjacent lesson correspondence;
2. anatomical topology, orientation, proportions and connections;
3. label text and leader-line endpoint;
4. English terminology and scientific nomenclature;
5. Tamil terminology (unresolved terms remain `TERMINOLOGY REVIEW PENDING`);
6. source render at approximately 360 px and enlarged view;
7. only then promote the individual manifest row to PASS, otherwise FAIL/PENDING with an explicit reason.

A biological PASS does not imply a final row PASS when mobile, label or Tamil audits remain unresolved.

## Current gate

REFERENCE MAPPING: PASS
PENCIL STYLE: ESTABLISHED
SVG CREATION/EMBEDDING: IN PROGRESS — bulk production stopped
PER-FIGURE BIOLOGICAL/SOURCE AUDIT: ACTIVE
TAMIL AUDIT: ACTIVE/PENDING
BUILD: BLOCKED
DEVICE QA: NOT STARTED
FINAL: NO
