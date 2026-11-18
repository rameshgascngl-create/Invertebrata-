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
- Chapters #3–#5 now also have targeted bilingual editorial work:
  - `PoriferaReproductionBookChapter.kt`: 45 phrase/term instances amended (32 rules). Corrected **embryonic inversion** (exposes larval cilia before release) versus **post-settlement reorganisation** (larval cells contribute to new tissues); revised English and Tamil life-cycle captions, summary, examination guidance and quick-check answer. Improved gemmule, archaeocyte and reproduction terminology.
  - `CnidariaBookChapter.kt`: 34 Tamil instances amended (28 main changes plus 6 anatomical-term normalisations). Corrected `Physalia` misidentified as a fish; standardised `எப்பிதீலியத் தசைச் செல்கள்`; clarified nematocyst and digestion explanations and polyp/medusa reproductive roles.
  - `ObeliaBookChapter.kt`: 49 phrase/term instances amended (25 rules). Replaced `கூட்டுயிரி` with the more precise colonial `காலனி` in relevant Obelia passages; clarified feeding and reproductive zooids, coenosarc and Obelia metagenesis. Repaired a scientifically misleading English summary comparing Obelia and `Physalia`.
- **Chapters #6–#8 targeted source-language and science correction (18 November 2026):**
  - `CoralsBookChapter.kt` (#6): **27 changed phrases/instances**, including coral *colony* vs coral–algal *symbiosis*, coral-polyp structure, feeding cavity, larval settlement, primary production, bleaching and `calicoblastic epidermis` terminology. Reconciled the English reef framework claim with the existing Tamil qualification that calcareous algae also help build/strengthen reefs.
  - `CoralEconomicsBookChapter.kt` (#7): **28 substantive phrase/term amendments** to natural-capital concepts, extraction versus ecosystem services, reef recovery, snorkelling/scuba terminology, coastal sediment and economic exam responses. Corrected two English–Tamil legal descriptions: protected groups, including stony and black corals, appear in **Schedule I** of the Wild Life (Protection) Act, 1972 as amended in 2022; specific permissions and current exceptions still require independent legal validation.
  - `PolymorphismBookChapter.kt` (#8): **29 targeted amendments** covering `காலனி`, zooid functions, `Physalia` float and prey-catching tentacles, distinctions between metagenesis and polymorphism, non-misleading Tamil for social-insect castes, and an overgeneralised English claim about hydrozoans lacking organs.
- **Chapters #1–#8 remain IN PROGRESS**, not fully line-by-line reviewed or independently certified. Source-keyword regression checks are *not* comprehensive bilingual peer review.
- External references for the new corrections: NOAA reef construction https://oceanservice.noaa.gov/facts/coralmadeof.html ; Great Barrier Reef Marine Park Authority, coralline algae in reef framework https://outlookreport.gbrmpa.gov.au/values/3-ecosystem-health/34-ecological-processes/348-reef-building ; NITI Aayog coral-law summary https://www.niti.gov.in/node/1369 ; India Schedule I source https://indiacode.ecourtsindia.com/wild-life-protection-act-1972/schedule/1/ .
- English was intentionally corrected only where scientific meaning was inconsistent: sponge inversion and the Obelia–Physalia summary (#3 and #5), exclusivity of reef construction (#6), protected coral groups under India's amended wildlife legislation (#7), and an overgeneralisation about hydrozoan organ systems (#8). English in chapters #1, #2 and #4 is unchanged.
- Original images and credits, chapter IDs, curriculum registration and frozen A5 JSON assessment records remain unchanged.
- Scientific references for embryonic inversion and separate settlement events: https://pmc.ncbi.nlm.nih.gov/articles/PMC11164562/ , https://sicb.org/abstracts/development-of-the-amphiblastula-of-the-calcareous-sponge-sycon-coactum/ and https://www.tandfonline.com/doi/abs/10.1080/07924259.1993.9672327 .

## Required completion checks (every chapter, including extensions)

1. Compare **every** English/Tamil learning objective, paragraph, point, definition, table cell, figure caption, summary and question/answer for full meaning equivalence; do not shorten content.
2. Verify terminology in the Tamil Nadu school/college zoology textbook register. Keep cilia (குறுஇழைகள்) distinct from flagella (கசையிழைகள்); standardise taxonomic and anatomical transliterations only after verifying meaning.
3. Check zoological correctness independently from language style: canal morphology, development, taxon examples, hosts and life cycles; record any English-source error instead of translating it verbatim.
4. Read the actual in-app text at English/Tamil 100% and Tamil 200% font scaling, portrait and landscape; verify wrapping, layout, figures, and no blank-page regressions.
5. Track each chapter as `NOT_REVIEWED`, `IN_PROGRESS`, `INTERNAL_BILINGUAL_REVIEWED`, or `INDEPENDENTLY_ACCEPTED`. Never infer the final status from a keyword search or successful compilation.

**Next new chapter in curriculum order: #9 Platyhelminthes.** Separately, complete the outstanding full line-by-line, caption, table, A5 assessment and in-app checks for chapters #1–#8; these eight have only targeted corrections and remain IN PROGRESS. Continue #9–#43 and extension chapters #44–#63 without changing the 52 owner-accepted illustrations.