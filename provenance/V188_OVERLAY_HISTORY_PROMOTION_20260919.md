# INVERTEBRATA v1.8.8 — Overlay History / Scroll-Return Remediation Promotion Provenance

Date: 2026-09-19

## Defect classification

INTERACTION / HISTORY-SCROLL RETURN

## Former authoritative state

- Former authoritative main: `d840590d8a045c035f95487d8b956eb0e992260a`
- Former authoritative reconstructed HTML SHA-256: `62bc38edec4c0256d14952c79c106ac91698a035d441937399f756e28c0d1b95`
- Former authoritative reconstructed HTML size: `2387111` bytes
- Canonical ZIP SHA-256: `281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3`
- Former source-tree SHA-256 manifest: `dc97bc03842122b3d41b9a818e1d49c28d0bbe6fb9ef279d949e93b25e8d791f`
- Release workflow Git blob: `8a5a851a17669fc5d7e26c076cce4b2fcd1e04df`

## Audited remediation provenance

- Remediation audit branch: `remediation/visual-overlay-history-return-20260919`
- Remediation audit branch HEAD: `3dcc134fc4727ec0aa68603bac0b471ea0195a2f`
- Audited implementation commit: `731afba26e919dc6ae2b3db7108207d647096235`
- Audited `ci/reconcile_v188.py` Git blob: `1161c46184c3d69530c1cad74db774734b4947af`
- Audited Step-2 SHA-256: `350054a4c93c0849117c6eefc9da4f4bcbf5841d92970fbe1ab19151681380d3`
- Audited accepted-transform manifest commit: `cb084e53d745c8974270aff5c52ee558d7c8c968`
- Audited manifest Git blob: `149c2f860f86bb810a98ce6bf1a3bc7a53c7a351`
- Successful remediation audit run: `35454016908`
- Audited candidate reconstructed HTML SHA-256: `9d4a0e5b6bd23e8fd6cd7e148100bb6017374d2cd9dea45098007eff0c899f65`
- Audited candidate reconstructed HTML size: `2389584` bytes
- Accepted transforms: `19`
- Superseded executable transforms: `0`

## Promotion mechanics

Fresh promotion branch: `promote/overlay-history-return-20260919`

The promotion branch was created from exact former authoritative main `d840590d8a045c035f95487d8b956eb0e992260a`.

Production promotion was restricted to the exact audited content of:
- `ci/reconcile_v188.py`
- `ci/v188_accepted_transform_manifest.json`

Promotion commits before provenance:
- implementation promotion: `35dc7d9df68ac611fc5be77a582e0073e546566f`
- manifest promotion: `23b2614bae6c0e368d12859e51333382df4f73d5`

THE REMEDIATION BRANCH WAS NOT MERGED WHOLESALE.

Audit helper files, temporary workflows, browser harness files, generated evidence, diagnostic scripts, and branch-specific infrastructure from the remediation branch were not promoted as production changes.

## Root cause

The former explicit Close path hid the visual overlay before requesting `history.back()`. When the resulting `popstate` arrived, the central history router could no longer recognize that the transition belonged to an open visual overlay. The event could therefore fall through into ordinary lesson/chapter history restoration and restore an earlier navigation snapshot, producing the observed wrong scroll return.

## Promoted overlay-owned history architecture

The audited implementation:
1. captures exact pre-overlay return state, including stable figure identity, chapter identity, `scrollX`, `scrollY`, and focus/trigger;
2. pushes a transient visual-overlay-owned history entry;
3. leaves the overlay active while explicit Close requests the history unwind;
4. consumes the corresponding `popstate` in the overlay path before normal lesson/chapter restoration;
5. finalizes overlay close;
6. restores exact scroll and focus;
7. returns immediately from the central history router.

Browser Back follows the same overlay finalization route while the overlay is open. A subsequent Back after overlay closure remains available for normal lesson/chapter navigation.

## Duplicate-open guard

The audited generic duplicate-open guard in `openTheoryVisualNode()` is retained:

`if (!overlay.hidden) return;`

This prevents multiple history entries if more than one activation route reaches the same visual. It is generic and contains no figure-specific exception.

## Audit evidence accepted before promotion

The isolated remediation audit established:
- targeted history-rich stress activations: `136`;
- full 82-figure Run A: PASS;
- full 82-figure Run B: PASS;
- explicit-Close scroll failures: `0`;
- Browser-Back scroll failures: `0`;
- wrong-chapter returns: `0`;
- focus failures: `0`;
- duplicate/multiple overlay failures: `0`;
- runtime duplicates: `0`;
- horizontal-overflow failures: `0`;
- closed-details placements: `0`;
- below-threshold contextual placements: `0`;
- runtime errors: `0`;
- failed figures: `0`.

Former representative defect targets:
- `earthworm-reproductive-r2`
- `PARAMECIUM-CV-CILIA-N1`
- `PENAEUS-01-R1`
- `master-sycon-canal`
- `fasciola-excretory`

Each passed explicit Close and Back-close return with 0 px scroll displacement under the established audit.

## Contextual-placement freeze

The remediation does not modify contextual placement semantics.

Required frozen state:
- valid visible contextual matches: `75`
- neutral original-container fallbacks: `7`
- neutral lesson-anchor fallbacks: `0`
- below-threshold contextual placements: `0`
- closed-details contextual placements: `0`
- index/arbitrary fallback placements: `0`
- relevance threshold: `3`
- contextual-atlas script SHA-256: `005c2e130b35454fd56b7b5b3ce6295a51adc33dea7b258c312f6f312b4597d2`

Scoring, keyword logic, visibility eligibility, closed-details exclusion, neutral fallback, and placement destination selection are frozen.

## Academic / figure freeze

The remediation is interaction/history code only.

Frozen:
- English theory
- Tamil theory
- taxonomy
- terminology
- captions
- SVG artwork
- plate IDs
- labels
- examples
- chapter order
- subsection order

Expected figure inventory remains 82 occurrences / 82 unique plate IDs with no missing, extra, or duplicate plate IDs.

## Workflow freeze

The release workflow is outside this remediation and must remain byte-for-byte unchanged.

Required release workflow Git blob:
`8a5a851a17669fc5d7e26c076cce4b2fcd1e04df`

## Promotion boundary

This promotion authorizes only exact overlay-history remediation promotion, authoritative manifest/provenance update, canonical 19-step deterministic reconstruction, and authoritative Phase-B2 rerun.

It does not authorize build-pipeline reconciliation, Gradle execution, APK/AAB generation, signing, device QA, Play Console work, or final release.
