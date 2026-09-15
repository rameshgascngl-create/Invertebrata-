# INVERTEBRATA v1.8.8 — Earthworm targeted-remediation evidence 01

Controlling pre-remediation audit: `50d63bdb6c13002701900c19156b5ba1032014a2`  
Pre-remediation report: `provenance/V188_EARTHWORM_SIX_PLATE_AUDIT_01.csv`  
Authoritative Earthworm visual reference: `Invertebrata draft.pdf`, p.30 only.  
Remediation source commit: `237a4a3a01f95aa32be4cc3827d4c0531950f5a3`.

## Scope control

Only five established Earthworm defects were modified. `earthworm-nervous` was deliberately left anatomically unchanged as the audit control. No Sycon, Fasciola, Ascaris, Penaeus, Pila, Asterias, Obelia, Nereis, Protozoa or Batch-3 figure is modified by the remediation script. The Batch-1 Earthworm composite remains `FAIL — SUPERSEDED AS PRIMARY TEACHING PLATE` and is not used as a requirement substitute.

The remediation script does not invoke Gradle, does not alter `.github/workflows`, does not dispatch `workflow_dispatch`, and does not reconcile the build pipeline.

## Revision provenance

| Original Figure ID | Corrected Figure ID | Established defect | Correction applied |
|---|---|---|---|
| `batch2#EARTHWORM-EXTERNAL` / `earthworm-external` | `earthworm-external-r2` | Required setae absent | Added a restrained row of anatomically oriented setae while retaining body outline, segmentation, clitellum and A/P orientation. |
| `batch2#EARTHWORM-DIGESTIVE` / `earthworm-digestive` | `earthworm-digestive-r2` | Crop and anus absent; typhlosole not depicted; leader hit undifferentiated intestine | Added distinct crop, terminal anus and an actual folded typhlosole representation; retargeted typhlosole leader. |
| `batch2#EARTHWORM-VASCULAR` / `earthworm-vascular` | `earthworm-vascular-r2` | Heart leader missed an arch; central horizontal line ambiguous | Retargeted leader onto an actual arch; removed the unsupported/ambiguous central horizontal line. Four arches and dorsal/ventral vessels preserved. |
| `batch2#EARTHWORM-NERVOUS` / `earthworm-nervous` | unchanged `earthworm-nervous` | No established anatomical defect | **No anatomical redraw.** Control candidate preserved. |
| `batch2#EARTHWORM-EXCRETORY` / `earthworm-excretory` | `earthworm-excretory-r2` | Three generic nephridia; type/distribution/discharge teaching absent | Reconstructed only enough to distinguish integumentary, septal and pharyngeal nephridia and the p.30-supported exonephric/enteronephric relationships. |
| `batch2#EARTHWORM-REPRODUCTIVE` / `earthworm-reproductive` | `earthworm-reproductive-r2` | Conflated internal structures and unsupported topology | Removed the unsupported conflated internal schematic. Retained only p.30-supported reproductive landmarks: clitellum, female genital pore segment 14, male genital pore segment 18. Internal topology is explicitly `REFERENCE RESOLUTION REQUIRED`; no unsupported duct/junction was invented. |

## English terminology gate

Source-level terminology after correction is internally explicit and no longer uses the rejected phrases `Seminal vesicle / receptacle region`, `Ovary / oviduct region`, or `Nephridiopore / enteronephric discharge depends on nephridial type`. English is PASS for the five revised source plates and remains PASS for the unchanged nervous plate, subject to the separate lesson-context rendering gate.

## Tamil terminology gate

The existing Tamil captions are preserved. The current accepted evidence does not establish a complete academically reviewed Tamil label set for the internal labels of all six system plates. Therefore all six remain:

`TERMINOLOGY REVIEW PENDING`

No Tamil term is promoted merely from transliteration.

## Embedding gate

The remediation operates on the already-embedded Batch-2 Earthworm figure nodes after Batch-2 reconciliation, replacing exactly one original Earthworm node for each modified `data-v188-plate`. The nervous node is untouched. The replacement is fail-closed: zero or multiple matches raise an error. Both payload copies must remain byte-identical after application. This establishes the intended embedding transformation at source level, but does not substitute for reconstruction of the exact accepted illustrated source chain.

## 360 px lesson-context gate

`PENDING` for all six.

Reason: the known `BUILD PIPELINE RECONCILIATION REQUIRED` blocker remains OPEN, and the exact accepted illustrated source chain has not been formally reconstructed in this operation. An isolated SVG or synthetic wrapper render would not satisfy the requested **actual lesson-context** evidence. No 360px PASS is fabricated.

## Enlarged source-view gate

`PENDING` for all six.

Reason: the vector SVG source remains compatible with the existing enlargement mechanism in principle, but the exact accepted illustrated lesson source has not been reconstructed here. Therefore no enlarged-view PASS is inferred from SVG syntax alone.

## Source-level biological re-audit notes

- External: setae are now actually drawn and labelled; previously accepted segmentation/clitellum/orientation retained.
- Digestive: crop and gizzard are distinct; the alimentary pathway terminates at a labelled anus; typhlosole is represented as an internal fold and its leader ends on that fold.
- Vascular: four arch-like hearts are retained; the heart leader now terminates on an arch; the ambiguous central horizontal line is removed.
- Nervous: no new defect established; anatomy remains unchanged.
- Excretory: the three p.30 categories are no longer represented as interchangeable generic nephridia; source labels distinguish body-surface/exonephric versus internal/enteronephric relationships at the level supported by the controlling p.30 audit.
- Reproductive: unsupported internal topology is deliberately not asserted. This plate therefore cannot receive system-completeness PASS until accepted lesson/reference evidence resolves the internal reproductive organisation required by the lesson.

## Gate status after this evidence record

This file is source-level audit evidence only. It is **not physical-device QA**.

`BUILD PIPELINE RECONCILIATION REQUIRED — OPEN / BLOCKING BUILD ELIGIBILITY`  
`BUILD — BLOCKED`  
`APK — NOT GENERATED`  
`WORKFLOW — UNCHANGED`  
`DEVICE QA — NOT STARTED`
