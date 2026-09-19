# INVERTEBRATA v1.8.8 — Step-2 semantic-neutral contextual fallback promotion

## Promotion identity

- Starting production main: `8416ec813a7b8bb783896a4c4024ef22c1b401ad`
- Promotion branch: `promote/step2-semantic-neutral-fallback-20260919`
- Audited implementation commit: `0a53426f98a75b62dd9c85e667c2dfbf190e87ba`
- Audited implementation Git blob: `35f288d6c30d98816140d08f7e8b20aa09522bf5`
- Audited implementation SHA-256: `6e026563f236ac8355e4540695088fb5ad481aa1d2f416903890c71d53638ad0`
- Promotion source commit carrying the exact audited blob: `4df50e38de99ac099f6e657934978a2fbac626a0`
- Originating audit branch: `audit/contextual-neutral-fallback-20260919`
- Originating audit branch HEAD: `74bc2a1b8f78948bc1a6c92b706ab7880d2debda`
- Successful audit workflow run: `35448088968`
- Audited reconstructed HTML SHA-256: `62bc38edec4c0256d14952c79c106ac91698a035d441937399f756e28c0d1b95`
- Audited reconstructed HTML size: `2,387,111 bytes`

The audit branch was **not merged wholesale**. The production-relevant Step-2 implementation was applied from the production baseline and reproduced the exact audited Git blob.

## Previous authoritative Step 2

- Path: `ci/reconcile_v188.py`
- Governing commit: `e59f1b67a37a92f8ffa17094cfc2eb151eae2ea3`
- Git blob: `a6c27042df66a728b320db8d9c26fce660ebc669`

## Reason for supersession

The prior contextual placement logic could place figures inside default-collapsed `<details>` content and, when visibility filtering was first tested, its index-based fallback could associate a figure with an academically unrelated visible subsection.

Supersession reason:

**CONTEXTUAL DESTINATION SCORER ALLOWED HIDDEN CLOSED-DETAILS DESTINATIONS AND THE LEGACY INDEX-BASED FALLBACK COULD FORCE BELOW-THRESHOLD FIGURES BESIDE UNRELATED VISIBLE SUBSECTIONS.**

## Promoted semantic-neutral rule

The promoted Step-2 behavior preserves the existing contextual scorer and relevance threshold while adding only destination eligibility and neutral fallback semantics:

1. A contextual subsection is eligible only when it is default-visible; any ancestor `details:not([open])` makes it ineligible.
2. The existing contextual score function is unchanged.
3. The existing minimum relevance threshold remains `3`.
4. A visible subsection is used only when its score reaches the existing threshold.
5. If no visible subsection reaches threshold, index/ordinal/nearest-visible fallback is prohibited.
6. Preferred neutral fallback is the figure's original default-visible `.textbook-visuals` instructional container.
7. If that original visual container is hidden, the same figure moves to a dedicated default-visible lesson-level `.v188-neutral-figure-anchor`.
8. The existing delegated enlargement routing through `.sys-fig` is preserved.

## Targeted audit evidence

Previously blocked figures all resolved to neutral placement because their highest-scoring default-visible subsection score was `0`, below threshold `3`:

- `earthworm-reproductive-r2` — neutral placement; not beside `Closed vessels and work`.
- `nereis-circulatory` — neutral placement; not beside `Parapodium as a multifunctional limb`.
- `gastropod-torsion` — neutral placement; not forced beside `Reproduction`.

Previously valid generic matches remained valid under the same scorer:

- `PENAEUS-01-R1` → `Appendages`, score `7`.
- `master-sycon-canal` → `Position and habit`, score `10`.
- `PARAMECIUM-CV-CILIA-N1` → `Pellicle and cilia`, score `10`.

The isolated audit also verified 0 px horizontal overflow and retained click/touch/Enter/Space enlargement behavior for the seven targeted live figures.

## Integrity boundary

This promotion changes contextual placement mechanics only.

Explicitly unchanged:

- English academic prose
- Tamil academic prose
- headings and subsection order
- taxonomy and terminology
- figure captions
- SVG artwork and paths
- illustration labels
- figure identities and inventory
- relevance scoring algorithm
- relevance threshold
- Android wrapper
- Gradle configuration
- package/application identity
- versionName/versionCode
- existing release workflow

Audited figure inventory safeguards:

- figure/plate occurrences: `82 → 82`
- unique plate IDs: `82 → 82`
- missing IDs: `0`
- extra IDs: `0`
- duplicate plate IDs: `0`
- figure inventory multiset: identical

## Release boundary

The release workflow remains frozen at Git blob:

`8a5a851a17669fc5d7e26c076cce4b2fcd1e04df`

This promotion does not authorize Gradle, APK/AAB generation, signing, device QA, Play Console submission, or release finalization.

## Next required gate

After authoritative promotion, restart the complete 19-step deterministic reconstruction from canonical ZIP SHA-256:

`281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3`

Then run the targeted seven-figure post-reconstruction contextual regression. Full Phase B2 closure and the complete contextual atlas audit remain separate later gates.
