# INVERTEBRATA — Scientific and editorial assessment

**Date:** 10 November 2026  
**QA branch:** `feat/native-textbook-reader-qa`  
**Method:** automated text-contract checks over all 43 native book chapters, targeted biological reading of high-risk type-study passages, student UI string audit, figure-credit/asset-integrity checks, and existing native Android unit and emulator jobs.

## Changes made

1. Removed *Academic review pending*, *DRAFT_UNVERIFIED*, Tamil equivalents, figure-approval status lines, and outdated image-pending notices from student-facing chapter and simulator screens. The book reader does not allocate a blank status card.
2. Preserved useful biological caveats about schematics, separate food/contractile vacuoles, non-measured process timing, genus-versus-species illustrations, and source figure limitations.
3. `Leishmania` — captions now accurately describe both amastigote (mammalian macrophage) and promastigote (sand fly). Reference: https://www.cdc.gov/dpdx/leishmaniasis/
4. `Asterias` — chapter progression and historical image caption explicitly distinguish bipinnaria and brachiolaria; typical indirect development includes settlement and metamorphosis after brachiolaria. References: https://academic.oup.com/icb/article/61/2/337/6287660 and https://www.sciencedirect.com/science/chapter/edited-volume/pii/B9780081028711000166
5. `Paramecium` — the source illustration is a whole-cell orientation diagram and is no longer described as though every oral structure is magnified.
6. `Ascaris` — explained the copulatory spicules' role in widening/holding the vulva during sperm transfer, rather than falsely depicting the spicules as sperm ducts; removed review-status text from the egg caption. References: https://pmc.ncbi.nlm.nih.gov/articles/PMC5118333/ and https://www.cdc.gov/dpdx/ascariasis/
7. `Pila` — described the eyes on prominences outside the bases of the longer cephalic tentacles (not the tips). Reference: https://freshwaterbiodiversity.org.bd/species/pila-globosa_gastropod/
8. `Entamoeba histolytica / E. dispar` — preserved the scientifically essential warning that mature-cyst morphology alone does not reliably identify the species. Reference: https://www.cdc.gov/dpdx/amebiasis/
9. Cleaned common Further Reading entries; bibliographic records now identify publications without instructions to future reviewers to add pages.
10. Kept author/source/licence attributions for the eleven sourced PNG plates, and left 41 previously accepted images and their exact bytes unchanged.

## Regression and remaining acceptance

The new `StudentTextbookEditorialAuditTest` checks all registered chapters, their printed Further Reading entries, their figure credits, bilingual presence, and specific high-risk biological captions. Existing `BookChapterContractTest` and `WholeBookTamilTerminologyAuditTest` remain active.

**Scope limitation:** This was a systematic source-level academic/editorial audit with targeted fact-checking, not a claim that two independent people reviewed every paragraph or that every historical image is fit for publication. The internal scientific, Tamil and exact-source-rights approval register remains unchanged. User-facing text intentionally does **not** display these internal statuses. Physical-device acceptance and the full emulator matrix must still be read from CI results.

## Scientific reading used

- CDC DPDx — Leishmaniasis, Ascariasis, Amebiasis.
- Jaeckle, Komatsu & Boyle (2025), *Atlas of Marine Invertebrate Larvae*, Asteroidea.
- Echinoderm larval developmental research: *Integrative and Comparative Biology*, 61(2), 337 onward (2021).
- Nematode copulatory-spicule functional review: *Nematodes ultrastructure: complex systems and processes* (2016).
- `Pila globosa` morphology: Freshwater Biodiversity Portal of Bangladesh.

**Production note:** No source illustration has been silently changed, no copyright licence has been invented, and no internal release gate was removed to achieve the student-text cleanup.
