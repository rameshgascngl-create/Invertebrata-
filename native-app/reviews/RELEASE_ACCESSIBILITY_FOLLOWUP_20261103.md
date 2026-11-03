# Release accessibility follow-up — 3 November 2026

Baseline: acb13b6a, functional run [41472533403](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41472533403).
Changes prepared from audited branch b3d0e6ea.

## Evidence inspected
Raw XML and failure PNGs from artifacts 13157380838 (Tamil 200% portrait) and 13157755451 (landscape) were read.
- Portrait: missing r17-inline-play-pause; failure screen shows nuclear explanatory prose.
- Landscape: missing ciliary-inline-play-pause; failure screen shows the large ciliary raster.
- Landscape: missing u4-penaeus-appendage-name; failure screen shows the lower appendage controls, below the requested name.
These screenshots establish the viewport at failure, not whether an offscreen node exists.

## Test navigation defects corrected
1. The scroll fallback selected a container only when height >= width * 0.6. This rejects wide, short landscape viewports. It now selects visible containers advertising the requested vertical accessibility action; horizontal rows are excluded by direction rather than aspect ratio.
2. After choosing a stage, transport controls lie above the stage selector. The former forward-first bounded search could move further away through long Tamil prose. Transport controls and the appendage name now search upwards first.
3. Scroll events count as movement even if clipped raster bounds and text remain identical.
4. Traversed accessibility nodes are recycled; a retained candidate uses its own snapshot.
5. A failed lookup now saves an accessibility hierarchy alongside its screenshot, including IDs, bounds, visibility and advertised actions.

## Validation boundary
The three functional methods, diagram-pixel comparison, intermediate-pause assertions and five simulator cases remain intact. No test is skipped and no success is substituted for a missing node.
This is a test-navigation repair candidate, not proof that release playback or accessibility is fixed.
The existing no-APK audit workflow compiles instrumentation and runs JVM/Python checks. A fresh installed release emulator matrix is still required to close the runtime fault.
No production UI, frozen A5 files, image assets, source-credit claims or review gates are changed by this follow-up. No new APK generation is enabled.
