# Unit III — six source-backed raster plate replacement

**Source candidate branch only. Academic approval: HOLD.**

Replaces the six procedural anatomy drawings made by
`ci/build_unit3_anatomy_candidate_masters.py` with six actual bitmap
illustrations from publicly archived figures. No Pillow geometric curves,
silhouettes or SVG source is permitted for organism anatomy.

The native app uses `type_studies/<organism>/<plate>.png`. The source and
decoded PNG SHA-256, source dimensions, image URL and interpretation limitations
are archived per CI run in `qa-evidence/unit3/source-backed-plate-manifest.json`.

| Candidate | Reference image | Important qualification |
| --- | --- | --- |
| Fasciola external | *Encyclopaedia Britannica* 9th ed., 1888, Trematoda Fig 1 | Multi-system original, requires organ-level editorial isolation |
| Fasciola digestive-reproductive | Webster, *Diagnostic Methods* (1909), Fig 51 | Tandem branched testes / ovary require leader-level review |
| Fasciola life cycle | Chandler, *Animal Parasites and Human Disease* (1918), Fig 63 | Multiple snail redia stages; review encystment & hosts |
| Ascaris male-female external | Pfurtscheller Table 38a (pre-1927) | Original anatomical wall chart, historical coloration |
| Ascaris female internal | *Text-book of Comparative Anatomy* (1898), figs 168-169 | Original source contains male/female figures; review female only |
| Ascaris life-cycle **PARTIAL** | Pfurtscheller (pre-1927), cropped eggs and adult stages | **Lung migration and soil embryonation NOT depicted. Plate remains academically rejected.** |

### Important separation

* A source-backed raster is **not automatically a genuine pencil scan**.
* A verified digital image hash is **not anatomical approval**.
* The incomplete Ascaris historical crop depicts eggs and adults, **not**
  the complete life cycle. A future separately sourced, biologically complete
  pencil plate is mandatory. Do not interpret its current panel as showing
  soil embryonation, lung migration or reswallowing.
* Original source figure letters must be reconciled to the native English and
  Tamil labels. Never move leaders based on an obsolete procedural master.
* Device-geometry, touch, English/Tamil narration and accessibility QA must be
  verified after replacing the assets.

**Release remains BLOCKED until independent zoological review, coordinate
recalibration, bilingual editing and physical-device QA pass.**

Source links:
- https://commons.wikimedia.org/wiki/File:EB_9th_Volume23_Trematota_Fig_1.png
- https://commons.wikimedia.org/wiki/File:Diagnostic_methods,_chemical,_bacteriological_and_microscopical_-_a_text-book_for_students_and_practitioners_(1909)_(14781538944).jpg
- https://commons.wikimedia.org/wiki/File:Animal_parasites_and_human_disease_(1918)_(18198420775).jpg
- https://commons.wikimedia.org/wiki/File:Pfurtscheller_Table_38_a.png
- https://commons.wikimedia.org/wiki/File:Text-book_of_comparative_anatomy_(1898)_(14776166501).jpg
- https://commons.wikimedia.org/wiki/File:Pfurtscheller_Table_38_a.png (partial Ascaris life-history image)
- https://www.cdc.gov/dpdx/ascariasis/index.html (scientific lifecycle checking, NOT raster asset)

## Exact asset integrity seal
Six source SHA-256 and six output SHA-256 values are pinned in the generator after examination of first-pass artwork bytes. Source geometry has changed from the rejected procedural masters, so **direct bitmap hotspots and leader overlays remain blocked until source-specific human review**. The translated native organ control buttons remain functional but do not imply verified leader coordinates.
