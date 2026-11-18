# Tamil editorial scope — entire native textbook (18 November 2026)

## Corpus boundaries, verified from Kotlin registries

- **Units I–V:** 43 validated A5 curriculum chapters, listed in `BookChapters.kt` and `NativeCurriculumManifest.kt`. The A5 assessment JSONs are a separate frozen corpus and were not edited.
- **Unit VI:** 12 additional book chapters in `InsectaExtension.bookChapters`: InsectaGeneral, Cockroach, Pollinators, NaturalEnemies, MedicalInsects, HouseholdInsects, PestsIpm, CerealPests, CropPests, Chelicerata, Myriapoda, MinorArthropods.
- **Unit VII:** 8 additional book chapters in `FurtherInvertebratesExtension.bookChapters`: MinorAnnelids, Leech, AnnelidExcretionReproduction, MinorMolluscs, EchinodermClasses, AureliaMesenteries, TapewormHostParasite, ProtozoaEconomics.
- **Runtime total: 63 `BookChapter` entries (43 + 12 + 8).** This is distinct from the 43-chapter validated A5 curriculum contract. Additional flat native lessons, quiz screens and image captions require their own content audit; 63 is not an exhaustive count of all text surfaces.

## Current verification status

The register `docs/tamil-editorial-register-20261115.md` documents targeted editorial fixes for the 43 core chapters. Most are **not** fully line-by-line English–Tamil reviewed. The 20 extension chapter manuscripts are **NOT FULLY REVIEWED** in this register. No independent Tamil/science sign-off is claimed for any chapter.

### Focused source corrections in this branch

- `PoriferaBookChapter.kt`: 13 Tamil replacements involving consistent pinacocyte/spongin spelling, bioerosion explanation, microbial defence, medicinal relevance and exam-answer language.
- `PoriferaCanalsBookChapter.kt`: 15 Tamil replacements involving asconoid/leuconoid and Leucosolenia spelling, choanocyte filtration, water-flow regulation, rhagon explanation and examination guidance.
- Both chapters remain **IN PROGRESS**: these are source-language corrections, not full independent content or on-device rendering acceptance.
- Original English chapter strings, image files and credits, chapter IDs, curriculum registration and A5 JSON records are unchanged.

## Required completion checks (every chapter, including extensions)

1. Compare **every** English/Tamil learning objective, paragraph, point, definition, table cell, figure caption, summary and question/answer for full meaning equivalence; do not shorten content.
2. Verify terminology in the Tamil Nadu school/college zoology textbook register. Keep cilia (குறுஇழைகள்) distinct from flagella (கசையிழைகள்); standardise taxonomic and anatomical transliterations only after verifying meaning.
3. Check zoological correctness independently from language style: canal morphology, development, taxon examples, hosts and life cycles; record any English-source error instead of translating it verbatim.
4. Read the actual in-app text at English/Tamil 100% and Tamil 200% font scaling, portrait and landscape; verify wrapping, layout, figures, and no blank-page regressions.
5. Track each chapter as `NOT_REVIEWED`, `IN_PROGRESS`, `INTERNAL_BILINGUAL_REVIEWED`, or `INDEPENDENTLY_ACCEPTED`. Never infer the final status from a keyword search or successful compilation.

**Next editorial checkpoint:** close the complete line-by-line comparison of chapters #1 and #2, including assessments and image captions, then continue through core chapters #3–43 and extension chapters #44–63. Preserve all 52 owner-accepted illustrations.