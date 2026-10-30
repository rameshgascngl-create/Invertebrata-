# N2.3C3 — Species-authenticated geometry comparison & visual fidelity

## Decision scope
This is an **engineering candidate**, not an accepted *Paramecium caudatum* atlas plate. The external-cilia plate must remain `DRAFT_UNVERIFIED` with no biological or Tamil sign-off.

### Audited baseline
- Previous accepted engineering HEAD: `74770bc35d501c266d921f5c7b527db828516f9f` (N2.3C2).
- Native Kotlin/Jetpack Compose only. No WebView, HTML runtime or INTERNET permission.
- Existing native A5 assets, original CSS-free Canvas contour, hit-test model, acceptance thresholds, process-death implementation and release identifiers remain untouched.

### Authenticated visual evidence and scientific limits

| Structure | Authenticated specimen reference | What the source demonstrates | What remains UNVERIFIED |
| --- | --- | --- | --- |
| Whole-cell orientation/oral region | Richard Allen Fig. 0, *P. caudatum* phase contrast; https://www6.pbrc.hawaii.edu/allen/ch10a/00-pca.html | Whole cell, oral region and two contractile-vacuole complexes | Alignment of specimen to the schematic anterior-left, oral-bottom plane; any precise oral-groove Bézier coordinates |
| Oral apparatus | Richard Allen Fig. 22, same species, TEM cross section near anterior oral region; https://www6.pbrc.hawaii.edu/allen/ch10a/22-pca740131-54.html | Vestibulum, buccal cavity, ciliary quadrulus/peniculi | Longitudinal arc/centerline and oral-groove surface placement in the native whole-cell profile |
| Cytoproct, closed state | Cell Image Library CIL:39181, same species, TEM; https://www.cellimagelibrary.org/images/39181 | Narrow closed ridge along ventral posterior suture; non-permanent opening | Location coordinates of the source-derived ridge in a whole-cell projection and geometry approval |
| Cortical trichocysts | Allen Fig. 1; https://www6.pbrc.hawaii.edu/allen/ch10a/01-pca740118-4.html | Cortical layer under cell surface | Exact density and position of 44 manually-spaced rods |

**No TEM cross section is treated as a registration transform for a whole-cell longitudinal Canvas.** The reviewed images are references only, not copied or traced into source artwork.

### N2.3C3 targeted native refinement
1. Enlarge the **cytoproct selection indicator only**, keeping the original ridge, oral groove, all biological geometry and all hit regions unchanged. Ensure a conservative reference-space gap between the orange indicator and the entire quadratic curve with its stroke.
2. Show a bilingual on-screen evidence limitation when students select the **oral groove**; retain the previously implemented cytoproct caveat.
3. Add JVM tests for the indicator/curve clearance in the common aspect-preserving viewport, genus/species provenance and strict denial of premature accepted status.
4. Add new English and Tamil 200% Compose assertions without reducing previous instrumentation or screenshot requirements.

### Biological / editorial blocker for formal acceptance
The available figures do not supply an independently aligned, species-authenticated, longitudinal set of coordinates for oral groove or cytoproct. Formal acceptance must await a zoological overlay review (including source image usage rights), signed Tamil terminology review and physical-device accessibility testing. If no adequately calibrated whole-cell specimen is available, keep the plate labelled **schematic**.

### Mandatory engineering acceptance
On the exact candidate SHA: Native foundation/JVM tests, API 34 Compose UI acceptance (normal and 200% Tamil), deterministic process-death/PID recovery, actual emulator screenshot inspection and retained hashes; no modified CI gates, academic corpus or app identifiers.
