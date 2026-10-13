# Unit III — exact-commit technical pass; anatomical plate rejection

**Date:** 13 October 2026  
**Artwork/engineering source commit:** `cc8944b9612da9a24dd9b2606ea0ce78ca713161`  
**Isolated candidate:** `native/u3-anatomy-atlas-candidate-20261013`  
**CI run:** https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38400990573  
**Disposition:** **ACADEMIC AND PHYSICAL-DEVICE ACCEPTANCE: HOLD. DO NOT MERGE OR RELEASE.**

## Engineering evidence
- CI compiled native Kotlin and Android tests: PASS.
- Original six 960x1280 candidate rasters were generated, decoded and saved as an Actions artifact.
- API 34 instrumentation: **5 Unit III + 4 Unit II = 9 tests at each font scale**; **18/18 passed** across 100% and 200% fonts.
- Original A5 corpus unchanged; historical Unit II/III reference assets preserved.
- New native plate slots, text-based fallback, direct anatomy-coordinate touch selection, native leader-line marker, and organ-specific narration request are implemented.
- Screenshots were captured for the full-screen selected-marker interaction at each font scale. They demonstrate UI rendering but do **not** validate anatomy.
- Speech engine log contains **`R161_AUDIO: phase=UNAVAILABLE`** at multiple organ-touch events in emulator. The application retains the bilingual written explanation. Spoken Tamil/English **not accepted**.
- A real physical Android phone was **not** connected; no physical-device evidence exists.

## Academic image-audit outcome — FAILED
The 6 PNGs are **original digitally generated line illustrations**, not scans of real pencil drawings; they look insufficiently like hand-drawn anatomical atlas masters. In particular:

| Required plate | Visual audit |
| --- | --- |
| Fasciola external | The body outline and two suckers exist but tegument, anterior cone/shoulders, surface texture and biological morphology are overly schematic. |
| Fasciola digestive-reproductive | The reproductive systems do not resolve clearly into a single convincingly branched ovary and two distinct tandem highly branched testes; intestines and neighbouring lines remain ambiguous. Reject. |
| Fasciola life-cycle | Schematic icons do not explicitly or adequately depict all sporocyst/redia/cercarial anatomical stages or mature metacercarial encystment. Reject as an authoritative life-cycle plate. |
| Ascaris male–female external | Two essentially straight lines instead of robust cylindrical nematode bodies, inadequate three-lipped head and poor male cloacal/spicule morphology. Reject. |
| Ascaris female internal | Paired ovaries/oviducts/uteri and the straight digestive tract are not visually distinguishable or detailed enough for an anatomy lesson. Reject. |
| Ascaris life-cycle | Icons oversimplify the egg embryonation and hepatopulmonary migratory pathway. Reject. |

## Gate decision
- **DO NOT** promote these original digital schematics to the stable native unit or release repositories.
- Do not confuse raster presence, test success or leader-marker coordinates with anatomical accuracy.
- The initial six source-art candidates must be replaced by independently zoologist-reviewed hand-drawn/verified graphite plate masters.
- Ensure the image master itself is accurate **before** drawing leader lines or assigning hotspots; independently review English and Tamil organ names against each structure.
- Then reproduce exact-commit 100/200% font screenshots, test installed offline English/Tamil voices with audible output on a real Android device, landscape/portrait rotations, zoom/touch interaction, offline reboot, and physical-device Back behaviour.
- Preserve existing engineering green baseline `2de62e96001561e7715eab4d7bb1d2f4b54c1980` until a future academically accepted image candidate is available.

## Authoritative validation sources
- https://www.cdc.gov/dpdx/fascioliasis/
- https://www.cdc.gov/dpdx/ascariasis/
- Classic morphological references / qualified zoology reviewer additionally required for accurate internal organ layout.
