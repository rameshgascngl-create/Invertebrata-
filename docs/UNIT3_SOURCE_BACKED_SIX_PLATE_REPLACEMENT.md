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
| Ascaris life cycle | CDC DPDx (2019) public-domain PNG | **Not a historical pencil drawing**; artwork sign-off blocked |

### Important separation

* A source-backed raster is **not automatically a genuine pencil scan**.
* A verified digital image hash is **not anatomical approval**.
* The 2019 CDC life-cycle diagram is an intentionally visible provenance
  exception: it is accurate clinical raster but **does not satisfy the user's
  final pencil-art standard**. Replace with separately reviewed drawn source
  before promotion.
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
- https://commons.wikimedia.org/wiki/File:Ascaris_lumbricoides_life_cycle.png
