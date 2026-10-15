# Unit I U1.6 — Plasmodium mobile-readable reference excerpts

**Isolated native branch:** \`native/u16-plasmodium-readable-panels-20261015\`.
This corrects **reference-gallery usability**, not the separately required
four original Paramecium pencil plates or species-specific parasite drawings.

## Engineering intent

The source figure is Sina Adl (2025), *Malaria Plasmodium infection cycle*,
Wikimedia Commons, CC BY 4.0. The single 2100×1900 labelled overview is
retained. Build-time operations select six defined original-pixel regions
and display them as three vertically paired excerpts, enabling a taller
image on Android portrait screens:

- Liver: source regions (255,155,925,570) and (920,150,1840,570)
- Human blood: (1165,520,1860,820) and (330,645,1170,1120)
- Mosquito: (655,940,1840,1325) and (260,1295,1845,1765)

**Coordinates are source-image crop bounds, NOT accepted organ leader or
hotspot endpoints.** They are locked to the SHA-256 of the generated
overview raster (\`02ff9625c97052fcf3b26800de09c022910598afb2dc82b095110802407422cb\`).
Any upstream pixel change blocks CI for anatomical editorial recalibration.

Each focus panel contains two faithfully sampled excerpts from that single
reference illustration; stage margins of a neighbouring panel may be visible.
The complete overview remains available so no omitted context is lost.
No claim of an independent pencil plate or an authored parasite anatomy
master is made.

## Native UI

The Plasmodium full-screen modal offers four two-column 48dp-minimum
selectors in English/Tamil: overview, liver, blood, mosquito. Every focus
selection resets zoom and pan and loads a same-commit offline PNG.
The old source overview remains selectable; missing panels explicitly fail
without substitute Canvas, SVG anatomy or silent fallback.

The captions distinguish \`P. falciparum\` liver and erythrocytic stages
from mosquito gametogenesis/fertilisation/oocyst development and do not
claim a \`P. falciparum\` dormant liver hypnozoite.

## Acceptance

- Exact-commit JVM and API34 native UI tests, offline at 100%/200% font scale,
  must verify all four selectors, raster-present tags, close and zoom.
- English/Tamil actual emulator screenshots captured for all three focus
  panels; image legibility examined without confusing engineering pass
  with scientific completeness.
- Original 43 A5 chapters, navigation and Entamoeba lesson unchanged.
- All source anatomy, labels, translation, leader calibration, pencil master
  creation and physical-device testing require independent review.

**Unit I academic release: HOLD.**


## U1.6 accessibility refinement — 200% Tamil

The verified 100%/200% Android screenshot evidence showed that one-row Tamil
phase selectors squeeze their text, and the single-line stage caption truncates.
The next source adjustment uses two selectors per row at fontScale >= 1.75
and retains the full English/Tamil explanation in an accessible scrollable
native Details dialog. Closing the dialog returns to the same source PNG.

This improves **caption access and control width**, not the artwork's
scientific validity. Continue to require exact-commit Android 100%/200%
tests and physical-device QA; do not mistake source crop bounds for approved
image-space anatomical leaders.
