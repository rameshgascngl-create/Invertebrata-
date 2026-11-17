# INVERTEBRATA — Scientific audit, Phase 1: Porifera (17 November 2026)

**Scope:** Source-based scientific review only. The accepted English–Tamil editorial content, figures and Android code have not been changed. Findings are **candidates for independent scientific review**, not certifications.

**Source branch:** `audit/undergraduate-coverage-register-20261114`

## Source-verified findings

| ID | File and source location | Finding | Recommended disposition |
|---|---|---|---|
| POR-SCI-01 | `PoriferaReproductionBookChapter.kt`, line 94, “Cleavage and the larva” | The English paragraph first describes the swimming larva as bearing **flagella** and then says it differs from the adult by being **ciliated on the outside**. These terms are inconsistent within the same paragraph and risk conflating flagella with cilia in the Tamil/English textbook. | **Editorial/scientific review required:** reconcile the English terminology with the specified larval morphology, then assess the corresponding Tamil passage. Do not silently change the English text. |
| POR-SCI-02 | `PoriferaReproductionBookChapter.kt`, lines 94–114 | Amphiblastula and parenchymula are both described, followed by an explanation of inversion and settlement. These stages require a taxon-specific scientific reference check before certification. | **Verification pending:** compare against authoritative sponge developmental biology references and confirm diagrams and captions. |
| POR-SCI-03 | `PoriferaBookChapter.kt`, lines 111–113 and 154 | Water-current, choanocyte filtration, intracellular digestion and freshwater osmoregulation are covered. Their presence in the source is confirmed, but external biological verification has not been completed. | **Verification pending**, not a reported defect. |
| POR-SCI-04 | `PoriferaBookChapter.kt`, lines 47, 59, 76 | Choanocyte structure and its distinction from other sponge cells are explicitly taught and illustrated. | **Figure inspection pending:** validate the image against labels, source and rights records. |

## Acceptance requirements for this phase

1. Review the full English scientific explanations of Porifera general characters, canal systems and reproduction, distinguishing verified defects from reference-check candidates.
2. Resolve any confirmed English scientific defects separately from Tamil translation work, with a source-backed rationale.
3. Confirm figure accuracy, labels, attribution and licence evidence before scientific sign-off.
4. Run compilation and Android emulator acceptance only after source changes warrant it; no build or test is claimed in this document.
5. Preserve `DRAFT_UNVERIFIED` until an independent reviewer signs off.

**Current phase result:** Initial scientific issue register created. **Not scientifically certified.** No APK generated.


## 17 November 2026 — source corrections and references

- **POR-SCI-01 resolved in source** (`f91b8617c6ea28df78f3ba77f8a2e2ddb25547c4`): `PoriferaReproductionBookChapter.kt` larval description, larval-type comparison table and definitions now distinguish motile larval **cilia** (குறுஇழைகள்) from adult choanocyte **flagellum** (கசையிழை). Sponge literature sometimes uses both English labels for the larval motile projections; the chapter now consistently distinguishes the developmental contexts. Sources: Bergquist et al. (1977), DOI 10.1016/0040-8166(77)90058-1, https://pubmed.ncbi.nlm.nih.gov/898174/ ; *Reproduction and Development in Calcareous Sponges: A Panorama of the Last Two Centuries*, https://pmc.ncbi.nlm.nih.gov/articles/PMC13336810/ .
- **POR-SCI-02 source correction** (`90041063ab5fabea10f48e920d9282a5f3da70c0`): `PoriferaReproductionBookChapter.kt`, “Settlement and inversion” and its definition/note, previously conflated **embryonic inversion before larval release** with **cell rearrangement after settlement**. English and Tamil now distinguish these events and avoid generalising developmental sequences across all sponges. Sources: https://pmc.ncbi.nlm.nih.gov/articles/PMC11164562/ ; Amano & Hori (1993), DOI 10.1080/07924259.1993.9672327; https://pubmed.ncbi.nlm.nih.gov/31036299/ .
- **POR-SCI-03 partly reference-checked, no change**: Choanocyte flagella drive inward water flow through pores to chambers, then outward through excurrent canals/osculum; feeding is intracellular. Source: https://pmc.ncbi.nlm.nih.gov/articles/PMC9077847/ . Broader sponge nutrition and osmoregulation claims still need complete reference review.
- **POR-SCI-04 not closed**: Source figure IDs and captions need comparison with actual artwork, licence and attribution. A source-caption inspection alone is insufficient.

**Certification decision:** NOT SCIENTIFICALLY REVIEWED. The whole Porifera corpus and illustrations have not been independently validated. Preserve `DRAFT_UNVERIFIED`. No Android build or emulator test executed in this session.
