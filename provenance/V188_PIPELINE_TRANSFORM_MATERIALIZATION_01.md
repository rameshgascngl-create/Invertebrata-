# INVERTEBRATA v1.8.8 — Phase B1 persistence closure

## Decision

**PIPELINE PHASE B1 PASS — 19/19 ACCEPTED TRANSFORMATIONS MATERIALIZED AND PERSISTED**

This commit sequence persists the already-verified Phase-B1 reconstruction mechanics in the repository so subsequent runs do not need to recover the 19-script executable set again.

No transformation was executed. No academic SVG/content was modified. The GitHub workflow was not modified or dispatched. Gradle was not run. No APK or signing operation occurred.

## Persisted mechanics

- Reconstruction driver: `ci/reconstruct_v188_accepted_source.py`
  - persistence commit: `efa9e09f8ef4293a7a190a176d874f30459ab23e`
  - repository blob after commit: `96ee2ade682dbbe747f4a88aa27b3b9333d00488`
- Accepted 19-step manifest: `ci/v188_accepted_transform_manifest.json`
  - persistence commit: `5bded8a14cd476d6bbec7fd3c86658a945f0628f`
  - repository blob after commit: `485336a8f8f018441fe6ef75fb36ee2296317665`
- Materialization matrix: `provenance/V188_PIPELINE_TRANSFORM_MATERIALIZATION_01.csv`
  - persistence commit: `0733a29df122129abe324afe14c81e646ccef255`
  - repository blob after commit: `c72c8236f3e450da2c339e6d85f57fd4df045c4e`

## Live repository verification

All 19 executable transformation files currently on `main` were checked against the exact file at their governing commit. Every current Git blob matched the governing Git blob.

The executable order remains exactly steps 1–19 from Phase A.

Explicitly excluded from the executable set:

- `77a9f0610564c7e2513fc3ea552c90f7656f0adb`
- `3523f3493f92bf3a7b176a9bff86f524499eefd2`
- `70c1b32e517e8711eafc055d611c34b0d0f1992a`

Manifest check:

- accepted executable entries: **19**
- step order: **1 through 19**
- superseded governing commits in executable entries: **0**
- Matrix-02 frozen requirement rows: **76**

## Serializer-aware mechanics

The persisted driver provides serializer-aware surrogate execution for the accepted legacy raw-HTML targeted remediations:

- Earthworm
- Earthworm Tamil overlay
- Obelia
- Nereis
- Pila
- Asterias
- Fasciola/Ascaris
- Paramecium

It preserves the accepted academic replacement markup by executing the original accepted remediation script against decoded serialized `ORG_SYSTEM_DIAGRAMS` payloads, then deterministically reserializing only changed assignments.

Penaeus remains on its accepted native serialized-overlay path.

The Obelia safeguard remains unchanged: hydranth and gonangium are subrequirements within the single existing `obelia-zooids` plate; no separate master plates are introduced.

## Driver safeguards

The persisted driver includes:

- canonical ZIP SHA-256 verification;
- exact 19-entry manifest validation;
- exact Git-blob verification for every executable transformation;
- Matrix-02 exact 76-row verification;
- safe canonical ZIP extraction;
- deterministic `PYTHONHASHSEED=0` subprocess environment;
- fail-closed transform execution;
- serializer-aware legacy adapter;
- identical academic-payload-copy checks before and after transformations;
- explicit exclusion of superseded executable commits.

The driver was syntax-validated before persistence. The manifest was JSON-validated before persistence.

## Evidence boundary

This persistence closure establishes only that the Phase-B1 mechanics and accepted executable set are durably available in the repository.

It does **not** establish:

- Run A success;
- Run B success;
- deterministic reconstruction;
- authoritative reconstructed-source SHA-256;
- final full-document duplicate-ID status;
- final broken-reference status;
- 76-row reconstructed-source regression PASS;
- contextual 360px/enlarged rendering PASS;
- build eligibility.

## Hard stop

- **PHASE B1 PERSISTENCE — PASS**
- **ACCEPTED TRANSFORMATIONS AVAILABLE — 19/19**
- **MANIFEST ENTRIES — 19/19**
- **SUPERSEDED EXECUTABLE TRANSFORMATIONS — 0**
- **MATRIX-02 ROWS — 76**
- **RECONSTRUCTION DRIVER PERSISTED — YES**
- **MANIFEST PERSISTED — YES**
- **RUN A — NOT PERFORMED**
- **RUN B — NOT PERFORMED**
- **RECONSTRUCTION DETERMINISM — NOT TESTED**
- **WORKFLOW MODIFIED — NO**
- **WORKFLOW DISPATCH — NOT PERFORMED**
- **GRADLE BUILD — NOT PERFORMED**
- **APK — NOT GENERATED**
- **DEVICE QA — NOT STARTED**
- **FINAL — NO**

## Next admissible operation

**PIPELINE PHASE B2 — Run A → clean Run B → deterministic comparison → full reconstructed-source integrity → 76-row reconstruction regression.**
