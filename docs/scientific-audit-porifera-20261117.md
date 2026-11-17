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
