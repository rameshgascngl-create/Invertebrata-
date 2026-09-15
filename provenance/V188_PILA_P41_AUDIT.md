# INVERTEBRATA v1.8.8 — Pila globosa p.41 row-level audit

Audit-only record. No drawing, rectification, APK build, workflow modification, or pipeline reconciliation is performed by this commit.

## Repository-state check before audit

The latest repository history was inspected before starting this audit. No commit with a Pila audit was present. The latest completed committed state was the Penaeus audit/rectification evidence sequence ending at `2ab2b44eaef2b84cde1212afda1a20651b98cbb6`. Therefore this Pila audit begins from the start and does not repeat committed Pila work.

## Authoritative scope

- Organism: *Pila globosa*
- Lesson: `u5-pila`
- Authoritative visual page: user-supplied `Invertebrata draft.pdf`, p.41.
- Corrected repository map: p.41 covers morphology, shell, digestive, respiratory, circulatory, excretory, nervous/sensory and reproductive systems.
- Historical Batch-2 `p42` reference is superseded by the corrected p.41 map and is not used as audit evidence.
- Existing production candidates inspected from `ci/reconcile_v188_svg_batch2.py`: `pila_external`, `pila_pallial`, `pila_digestive`, `pila_circulatory`, `pila_nervous`, `pila_reproductive`.
- Existing Batch-1 overview `batch1#PILA` remains an overview and cannot substitute for a missing or failed system-specific instructional plate.

## Row-level findings

| Figure ID | p.41 reference | Biological / content result | Leader-line / label result | EN | TA | 360 px | Enlarged | Final row status | Reason / correction required |
|---|---|---|---|---|---|---|---|---|---|
| `batch2#pila_external` | External morphology + shell features | **FAIL** | **FAIL** | **FAIL** | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **FAIL** | Candidate labels only spire/whorls, aperture/operculum region and head-foot/tentacles. p.41 independently resolves shell, apex, spire, whorls, suture, body whorl, aperture, peristome, columella, operculum, tentacle, eye, snout, mouth and broad muscular foot. The present plate is too incomplete to function as the required p.41 external/shell teaching plate. |
| `batch2#pila_pallial` | Dual respiration / pallial complex | **FAIL** | **FAIL** | **FAIL** | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **FAIL** | Candidate contains pulmonary sac, ctenidium, generic pallial partition and generic air/water routes, but p.41 separately shows right nuchal lobe/siphon for aquatic respiration, left nuchal lobe/siphon for aerial respiration, mantle cavity, water current, air current and osphradium. Those diagnostic relationships are not independently resolved. |
| `batch2#pila_digestive` | Digestive system + radula | **FAIL** | **FAIL** | **FAIL** | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **FAIL** | Candidate collapses oesophagus/stomach and intestine/rectum and omits crop and anus as independent landmarks. p.41 also gives a separate radula teaching element with transverse rows and radular formula. Current plate is insufficient for the p.41 digestive requirement. |
| `batch2#pila_circulatory` | Open circulatory system | **PASS — biological core only** | PENDING | PASS for present terms | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **PENDING** | Candidate correctly distinguishes one auricle and one ventricle and represents open circulation/haemocoelic return. p.41 supports the same core organization. Overall PASS is withheld until leader endpoints, Tamil terminology, 360 px readability and enlarged source rendering are independently audited. |
| `NOT-YET-CREATED-PILA-EXCRETORY` | Excretory system / Organ of Bojanus | **NOT YET CREATED** | NOT YET CREATED | NOT YET CREATED | TERMINOLOGY REVIEW PENDING | NOT YET CREATED | NOT YET CREATED | **NOT YET CREATED** | p.41 contains a distinct excretory-system requirement (kidney/Organ of Bojanus, nephrostome and opening to mantle cavity). No dedicated Pila excretory candidate exists in the Batch-2 `u5-pila` figure list. The Batch-1 overview cannot satisfy this independent requirement. Targeted remediation is therefore permitted later, but no drawing is performed in this audit. |
| `batch2#pila_nervous` | Nervous + sensory system | **FAIL** | **FAIL** | **FAIL** | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **FAIL** | Candidate shows cerebral, pleural, pedal and visceral ganglion/loop only. p.41 also resolves buccal ganglia and a separate sensory set: eyes, tentacles, osphradium and statocysts. The required nervous/sensory scope is therefore incomplete. |
| `batch2#pila_reproductive` | Dioecious male and female reproductive systems | **FAIL** | **FAIL** | **FAIL** | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **FAIL** | Candidate is a generic gonad/gonoduct/accessory-structure schematic. p.41 independently resolves male testis → vas deferens → seminal vesicle → penis and female ovary → oviduct → uterus → vagina. Sex-specific topology is not auditable in the current plate. |
| `batch1#PILA` | Pallial respiratory structures + principal systems overview | PENDING | PENDING | PENDING | TERMINOLOGY REVIEW PENDING | PENDING | PENDING | **PENDING / overview only** | Existing audit policy already states this overview cannot substitute for Pila-specific system rows. It is not promoted or used to erase any FAIL/NOT-YET-CREATED result above. |

## Important source limitation

This audit treats p.41 as the user-designated visual/anatomical reference and compares the current SVG candidates against what p.41 visibly requires. It does not silently correct or expand p.41 from external zoology sources. Any future rectification must remain a separate, explicitly authorized operation.

## Resulting gate state

- Pila p.41 audit: **FAIL / INCOMPLETE**.
- Confirmed specific FAIL rows: external/shell, pallial/dual respiration, digestive, nervous/sensory, reproductive.
- Confirmed independent NOT YET CREATED requirement: Pila excretory system / Organ of Bojanus.
- Circulatory row: biological core PASS only; overall PENDING.
- Batch-1 overview: PENDING / overview only; cannot substitute.
- Tamil terminology: remains review-pending for all audited Pila instructional figures.
- 360 px source-render audit: not performed in this audit; remains PENDING.
- Enlarged-view source audit: not performed in this audit; remains PENDING.
- Physical-device QA: NOT STARTED.
- SVG source gate: NOT PASSED.
- BUILD: BLOCKED.
- APK: DO NOT BUILD.
- Workflow: unchanged/manual-dispatch gate retained.
- Pipeline reconciliation: NOT STARTED.
- RELEASE: NOT FINAL.
