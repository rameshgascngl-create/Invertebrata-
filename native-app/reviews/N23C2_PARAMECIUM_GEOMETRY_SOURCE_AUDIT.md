# N2.3C2 — *Paramecium caudatum* scientific geometry candidate

## Status
ENGINEERING CANDIDATE ONLY. The oral apparatus and cytoproct are **NOT** scientifically signed off. This document is a source audit, not a pixel-to-microscopy validation report.

## Source scope and limits

1. **Whole cell, optical view:** Richard Allen's collection, Chapter 10a, Fig. 0, *Paramecium caudatum*, phase-contrast micrograph of an intact cell showing the oral region and two contractile vacuole complexes. URL: https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html . It illustrates the intact organism but does **not** independently assign approved x/y coordinates to the current Bézier artwork.
2. **Oral region, ultrastructure:** Richard Allen, Fig. 22, *P. caudatum*, cross section through the anterior oral region, showing vestibule and buccal apparatus. URL: https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html . This section cannot establish the whole-cell curvature or exact placement of the schematic U-shaped oral groove.
3. **Cytoproct, closed-state ultrastructure:** Cell Image Library CIL:39181, *P. caudatum*, TEM showing a closed cytoproct ridge along the posterior suture on the ventral surface. URL: https://www.cellimagelibrary.org/images/39181 . The reference supports a **ridge-shaped closed state** and a **posterior-ventral association**, not a permanent hole or an approved Canvas center point.
4. **Surface/cortex context:** Cell Image Library CIL:36755, *P. caudatum*, near-anterior cross section documenting cortical trichocysts. URL: https://www.cellimagelibrary.org/images/36755 . This remains background support for the previous N2.3C1 candidate, not approval of rod placement.

## Minimal native correction
- Replace the filled circular cytoproct dot with a short curved ridge mark in the same approximate posterior-ventral area. The candidate's points are manually chosen, not traced or calibrated to microscopy.
- Keep the existing cytoproct touch hotspot at reference x=776, y=420 and the native accessible selection button unchanged.
- Display an explicit bilingual caveat when the cytoproct is selected, distinguishing a schematic depiction from validated geometry.
- Add independent Kotlin unit checks for species evidence, evidence review flags, provisional anatomy status, and touch mapping across viewport shapes.
- Add Compose instrumentation assertions for the new English/Tamil caveat, without relaxing any old assertions.
- Do not modify A5 source assets, unit chapters, publication identity, process-death logic, native architecture, test tolerances or CI workflows.

## Further mandatory scientific gates
- Species-authenticated reference plate with specimen orientation and measured feature overlays signed by a zoology reviewer.
- Do not infer an oral-groove centerline from TEM cross-sections. Confirm optical whole-cell and serial-section correspondence first.
- Confirm that the cytoproct position is consistent with the species specimen and closed/open physiological state.
- Five feature-specific biological and Tamil sign-offs, physical-device portrait/landscape/200%-Tamil/TalkBack/hit-target evidence.
- All existing automated tests on the exact new commit, followed by manual review of new captured PNGs.

**Immutable acceptance constraint:** `AcademicWorkStatus.DRAFT_UNVERIFIED`; `AnatomyReviewRecord = null`. No automatic acceptance from green CI.
