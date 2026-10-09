# N2.3E2 — Source-access audit and independent-evidence intake

**Engineering scope only. Human zoological or Tamil approval is NOT granted.**

Base native Kotlin commit: `9f8e12e999c81969f5f2fa3daa738719112e6ebb`; audit date: **2026-10-09**.

## Independent reference check (text metadata, not signed microscopic inspection)

| Source | Identity and evidence | Access observed | Rights evidence | Can certify current Canvas coordinates? |
| --- | --- | --- | --- | --- |
| Allen whole cell, Fig. 0 | Species-named `P. caudatum`, phase-contrast image; oral region and contractile vacuoles | Page description directly retrieved | Image by D.J. Patterson / Mark Farmer, used with permission; **do not redistribute or trace without licensed rights** | NO — review requires specimen-registered overlay |
| Allen oral apparatus, Fig. 22 | Species-named anterior oral-region transverse TEM | Page description directly retrieved | No license to import established | NO — transverse TEM is not projected longitudinal groove |
| CIL:39181 | Indexed `P. caudatum` TEM description: closed cytoproct forms posterior-ventral ridge | Record searchable; direct detail retrieval returned **502** during this audit | Individual record rights **unconfirmed** | NO — thin section cannot localize marker in full cell |
| CIL:36755 | Indexed `P. caudatum` anterior cross-section; subpellicular trichocysts, cortical kineties | Indexed descriptive metadata verified; direct detail retrieval intermittently unavailable | Record displays **Public Domain**, subject to reconfirmation before reproduction | NO — section cannot justify 44 whole-cell rods |
| Sacred Heart College, Type-study PDF | 58-page PDF, morphology, pellicle, oral groove, cilia | PDF text retrieved | Link/reference only; no general figure republication permission inferred | NO — educational description is not calibrated microscopy |

Source links:
- https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html
- https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html
- https://www.cellimagelibrary.org/images/39181
- https://www.cellimagelibrary.org/images/36755
- https://www.shcollege.ac.in/wp-content/uploads/NAAC_Documents_IV_Cycle/Criterion-II/2.3.2/Type-UG1-AnimalDiversity-NC-Paramecium.pdf

**Scientific caution:** The existing longitudinal U-like oral-groove arc, number and placement of cilia, trichocyst density, and cytoproct position are all schematic candidates. The underlying scientific descriptions do not validate this drawing's x/y coordinates or morphology.

## E2 native handoff validation

`ParameciumN23E2EvidenceIntake.kt` provides a fixed source register and an empty five-line review intake. For EACH feature a real named zoological reviewer must record:

- Whole-cell reference identifier, figure/crop details, and orientation observation.
- A uniquely numbered, independently inspected specimen-to-CANVAS overlay and written geometry findings.
- Reviewer name/date and explicit biology verdict.
- Separate Tamil reviewer, evidence ID, written nomenclature findings, date and verdict.

The Kotlin gate rejects missing IDs, malformed dates, missing findings, duplicate features and TEM/PDF-only whole-cell coordinate claims. It cannot authenticate who submitted a name or who physically reviewed the image. Those require human oversight outside this software.

**Additional mandatory release blockers:** the existing `ParameciumExternalReviewGate` still requires independently evidenced species authentication, specimen-orientation signoff and **physical-device** QA (portrait, landscape, Tamil at true 200% font, TalkBack, direct organ touch). This E2 intake does not replace or weaken that gate.

## Verdict / next intervention

- Source traceability and native reviewer intake: prepared.
- Recorded biology and Tamil expert decisions: **0/5 each**.
- Source-specific pixel registration: **NOT PERFORMED**.
- Physical-device QA: **NOT PERFORMED**.
- Release: **HOLD**. Anatomy remains `DRAFT_UNVERIFIED`.

Human reviewers must now inspect original legally usable reference images and the native diagram side by side, submit their real evidence, and request corrections as needed. Do not assert human signoff based on passing CI.
