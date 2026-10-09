# N2.3E1 — Independent Paramecium external-anatomy review handoff

**Decision: REVIEW PACKAGE PREPARED; ANATOMICAL ACCEPTANCE NOT GRANTED.**

## Provenance and strict limits

- Native Kotlin/Jetpack Compose baseline: `73bd7af879c5bfc4376d8f8310a1f5b353ed2947` (N2.3D2, 3/3 CI gates PASS).
- Source organism is **Paramecium caudatum**; retain its exact species identification in every review.
- The current Canvas is an **illustrative, original, unregistered diagram**. Its hotspot coordinates, oral groove arc, 90 cilium strokes and 44 trichocyst rods are not specimen measurements.
- This phase exposes bilingual reviewer questions in the native app, retains source locators, and creates a reproducible five-feature review worksheet. It does **not** alter anatomy geometry, quiz data, release identifiers, image-licensing permissions, or acceptance thresholds.
- **Approval ledger:** Biology **0/5**; Tamil **0/5**; orientation signoff **PENDING**; physical Android QA **PENDING**. Every missing approval remains a blocker.
- Prior CI visual acceptance verifies that the Canvas and selected feature render on a Google API 34 emulator at Tamil 200%, not the zoological correctness of an anatomical marker.

## Exact five-feature review matrix

| ID | Current authoritative evidence | Independent human zoological question | Known limitation |
| --- | --- | --- | --- |
| pellicle | Sacred Heart College UG type-study PDF, slides 6–7 | Compare anterior/posterior outline against a licensed, authenticated whole-cell image. | Pedagogical figure does not supply calibrated Canvas coordinates. |
| somatic-cilia | Same PDF, slides 9–10; Allen, P. caudatum cortical images | Check surface cilia, kineties and caudal tuft. | 90 drawn strokes are illustrative, not measured count or spacing. |
| oral-groove | Same PDF slide 8; Allen whole-cell Fig. 0 and oral section Fig. 22 | Compare ventral-side orientation and projected groove arc with a whole-cell reference. | Fig. 22 transverse TEM is not a longitudinal contour registration. |
| cytoproct | Cell Image Library **CIL:39181**, P. caudatum TEM | Confirm closed posterior-ventral cytoproct ridge; separately inspect marker placement. | A 75 nm thin section cannot determine whole-cell x/y coordinates. |
| trichocysts | Cell Image Library **CIL:36755**, P. caudatum anterior cortical TEM | Confirm subpellicular row/distribution; compare schematic placement. | The anterior section does not certify 44 rods, complete cell density or spacing. |

Primary source endpoints:
- Whole-cell phase-contrast `P. caudatum` figure 0: https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html
- Oral ultrastructure figure 22: https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html
- Cytoproct, species-identified thin section: https://www.cellimagelibrary.org/images/39181
- Trichocyst layer, species-identified thin section: https://www.cellimagelibrary.org/images/36755
- Undergraduate teaching PDF: https://www.shcollege.ac.in/wp-content/uploads/NAAC_Documents_IV_Cycle/Criterion-II/2.3.2/Type-UG1-AnimalDiversity-NC-Paramecium.pdf

**Licensing constraint:** Allen Fig. 0 is explicitly a permissioned third-party micrograph. Do not import, redistribute or trace this image without rights confirmation. Confirm the *individual record's* license before incorporating a CIL image. Source links and review notes do not imply redistribution rights.

## Human review worksheet (not completed)

For **each** of the five IDs, the responsible reviewer must enter:

1. Specimen figure URL/accession; source species; microscopy modality; figure/slide locator; acquisition context and crop orientation.
2. An independently inspected candidate-versus-reference annotated overlay with persistent evidence ID. Identify deviations and proposed geometry/hotspot corrections. Never treat transverse TEM data as longitudinal or calibrated projected whole-cell coordinates.
3. Biological verdict (CORRECTION_REQUIRED or SIGNED_OFF), reviewer name, date and exact evidence reference. A 'pending' verdict is not an approval.
4. Tamil nomenclature and label readability review, including English-Tamil meaning, academic register, native-script rendering and marker placement; separate expert verdict/name/date and evidence reference.
5. Specimen orientation decision: anterior, posterior, oral/ventral and scale/crop alignment. Keep `orientationVerifiedAgainstFigure=false` until independent signoff.

## Physical-device QA still required

Install the debug QA APK on at least one **physical** Android device; record model, Android version and build SHA. Capture portrait, landscape and actual **200% system Tamil** scaling; activate TalkBack to verify labels/actions; directly tap every organ and check highlighting and 48dp alternate selection controls. Check scrolling, nav-bar insets, clipping, back navigation, process restoration and offline operation. Preserve dated screenshot/video evidence IDs. Emulator CI evidence must not be passed off as physical-device evidence.

## Stop/go rule

- If **any** of five biological reviews, five Tamil reviews, orientation signoff, or physical-device evidence is incomplete, the authoritative plate stays `DRAFT_UNVERIFIED`.
- Existing `ParameciumExternalReviewGate` is the independent completeness/readiness gate; a structurally ready packet is **not an approval by itself**.
- Do not generate a production release based on this phase.
