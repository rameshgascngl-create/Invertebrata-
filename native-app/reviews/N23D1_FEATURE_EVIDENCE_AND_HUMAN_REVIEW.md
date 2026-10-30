# N2.3D1 — Evidence-to-feature audit and mandatory human review dossier

**Decision: SOURCE TRACEABILITY ONLY — ANATOMICAL APPROVAL NOT GRANTED.**

## Baseline and integrity
- Base native commit: `d8d1780d8ac707a2fa76797fbc71170b5f06b9c3`.
- Authoritative application: `native-app/` Kotlin and Jetpack Compose, offline-first.
- Five external feature IDs and `AcademicWorkStatus.DRAFT_UNVERIFIED` remain unchanged.
- No biological or Tamil review signatures are available.
- Physical Android device testing, including TalkBack, landscape, real font scaling and hit regions, has not yet been supplied.

## Verified source types and their legitimate scope

| Feature | Primary source / locator | What is supported | Still unresolved |
| --- | --- | --- | --- |
| Pellicle | Sacred Heart College UG type-study reference, pellicle section slides 6–7 | Described external boundary | Verified cell outline coordinate overlay; source-figure licensing |
| Somatic cilia | Same UG reference, cilia section slides 9–10 | Surface cilia and caudal tuft | Count, exact spacing, specimen-level locations |
| Oral groove | Same UG reference slide 8 + Richard Allen phase contrast Fig. 0 + oral section Fig. 22 | Oral region and vestibular ultrastructure | Projected ventral curve, whole-cell orientation registration |
| Cytoproct | Cell Image Library CIL:39181, *P. caudatum* TEM | Closed posterior-ventral ridge | Whole-cell projected endpoints, dimensions and selected-marker occlusion |
| Trichocysts | Cell Image Library CIL:36755, *P. caudatum* TEM | Subpellicular cortical layer | Exact density, orientation and manually-spaced rod layout |

Source details:
- Whole cell: https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html (species-authenticated phase-contrast light micrograph; hosted under a separate permission/licensing notice).
- Oral vestibule: https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html (transverse TEM, not a coordinate transform).
- Cytoproct: https://www.cellimagelibrary.org/images/39181 (CIL:39181; closed ridge along posterior ventral suture, TEM).
- Trichocysts: https://www.cellimagelibrary.org/images/36755 (CIL:36755; cortical TEM).
- Secondary UG pedagogical reference: https://www.shcollege.ac.in/wp-content/uploads/NAAC_Documents_IV_Cycle/Criterion-II/2.3.2/Type-UG1-AnimalDiversity-NC-Paramecium.pdf

**Licensing boundary:** no microscopy images are imported into the app or traced. Figure 0 explicitly states its license and copyright; review and permission are required before redistribution. Presence of a URL is not a publication license.

## Actual blocker summary
- Five biological geometry sign-offs: **0/5 documented**.
- Five Tamil nomenclature sign-offs: **0/5 documented**.
- Specimen orientation registration: **PENDING**.
- Physical-device portrait/landscape/Tamil 200%/TalkBack/direct-organ touch checks: **PENDING**.
- Accepted anatomical plate: **NO**. Native Canvas remains a schematic prototype.
- Evidence uploaded by automated CI proves app behaviour, **not** zoological accuracy.

## N2.3D1 native deliverables
1. A typed, offline review dossier, linked to the five existing feature identifiers.
2. Each feature exposes a species-source locator and separate 'supports' / 'does not establish' bilingual language.
3. Read-only review counts displayed in the native Compose diagram view, with accessibility tags.
4. JVM safeguards against accidental promotion and modality conflation.
5. Android UI assertions to confirm the source dossier remains visible in English and Tamil 200% text.

## Required independent review workflow (not executed)
1. Select a legally usable authenticated *P. caudatum* reference whole-cell specimen and record imaging modality, figure/crop orientation and magnification.
2. A zoological subject expert inspects annotated feature-by-feature overlays and records deviations/corrections. For features visible only by TEM, do not claim whole-cell coordinates from the section.
3. A competent Tamil zoology reviewer signs off terminology, language register and label readability.
4. Test the actual Android build on at least one physical device in portrait and landscape, 200%-Tamil font, TalkBack and direct taps.
5. Supply reviewer names, dates and per-feature evidence IDs **only after real independent inspection**.
6. Evaluate the existing `ParameciumExternalReviewGate`; do not change `DRAFT_UNVERIFIED` automatically.

## Explicitly out of scope
This phase does not redraw anatomy, fabricate specimen overlays, certify Tamil labels, sign on behalf of a reviewer, alter A5 sources, change release identifiers, weaken CI, or introduce WebView/HTML/Internet permission.
