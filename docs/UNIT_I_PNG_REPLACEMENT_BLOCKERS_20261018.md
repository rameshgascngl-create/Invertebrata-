# Unit I PNG replacement: asset-level blockers (2026-10-18)

Status: **NOT ACCEPTED**. This document distinguishes actual PNG bytes from Kotlin image slots.

## Actual existing Unit I PNG files

- `unit1/paramecium_external.png`
- `unit1/paramecium_internal.png`
- `unit1/paramecium_oral.png`
- `unit1/paramecium_vacuole.png`
- `unit1/references/entamoeba.png`
- `unit1/references/trypanosoma.png`
- `unit1/references/leishmania.png`
- `unit1/references/plasmodium.png`

Presence alone does **not** prove anatomical correctness, precise parasite life stage, provenance approval, Tamil proofreading or screenshot acceptance.

## Missing files referenced by current native textbook components

| Expected asset | Lesson location | Required scientific review |
|---|---|---|
| `unit1/concepts/paramecium_nutrition.png` | Feeding and digestion | Oral groove → vestibule → cytostome/cytopharynx → food-vacuole formation and circulation → cytoproct egestion; distinguish contractile-vacuole pore |
| `unit1/concepts/paramecium_ciliary_locomotion.png` | Ciliary locomotion | Anterior/posterior orientation, effective/recovery strokes, metachronal wave; avoid conflating axoneme 9+2 with basal-body nine triplets |
| `unit1/concepts/paramecium_binary_fission.png` | Asexual division | Transverse constriction; macronuclear and micronuclear events not interchanged; two daughter cells |
| `unit1/concepts/paramecium_conjugation.png` | Conjugation | Temporary pairing, meiosis, exchange, nuclear reorganisation; do not depict immediate four-cell multiplication |

## Mandatory acceptance gates

1. Commit real, distinct PNG files under the exact asset paths; **no Canvas, SVG or text-only placeholders as substitutes**.
2. Independent anatomical audit of each image, including leader-line endpoints, direction arrows, label spellings and life-stage correspondence.
3. Check English and Tamil text and captions; preserve original image-source licence and attribution.
4. Verify each image appears **once** in the correct lesson section, with no duplicates or clipping, at normal and 200% Tamil font scale.
5. Run Kotlin/JVM and Android instrumentation on the **same commit**; inspect captured screenshots before acceptance.

The earlier build's compilation success does not close these image acceptance gates.
