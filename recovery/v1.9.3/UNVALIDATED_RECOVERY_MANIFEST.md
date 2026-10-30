# INVERTEBRATA v1.9.3 — UNVALIDATED recovery checkpoint

This checkpoint records recovered candidate artifacts and source lineage. It is NOT academic approval, NOT P0/P1 integration, and NOT release approval.

## Authoritative repository lineage
- Recovery base branch: release/v189-build-optimization-20260922
- Recovery base commit: 5620e9d4556861ea1d1f5b414f12d2dd2bb2c052
- Academic source path: payload/v189_clean_academic/index.html
- Git blob SHA-1: 31cbd2e09caac4f790a4ae885b46c2af4896c625
- Academic payload size: 2475518 bytes
- Academic payload SHA-256: 7f3dfbb64bc2c966b528a6d17245ef5e0e6277ff605ef6ccdeb7d9f0cabfdd00
- Payload provenance commit: 540831a5a140e9c114b73e05666c800e24992fd5

Repository inspection found no pre-existing v1.9.3 branch/file and no committed A5-validation work newer than this lineage.

## Durable preserved artifact set
Persistent recovery folder:
`/INVERTEBRATA/v1.9.3/recovery/2026-10-08-unvalidated/`

The files listed in RECOVERY_ARTIFACT_INVENTORY.csv were copied there unchanged before this checkpoint.

## Missing/unrecovered artifacts
1. INVERTEBRATA_v1.9.3_BILINGUAL_TEXTBOOK_MASTER_R13.html
   - Conversation artifact record exists.
   - Backing bytes are unavailable for readback/materialization.
   - Older Phase-2 HTML MUST NOT be silently substituted.

2. Corrected bilingual A5 candidate containing all 86 five-mark question-answer pairs
   - No persisted artifact was found.
   - It must be reconstructed from verified source material with a change register.

## Gate state at checkpoint
- A5 scientifically validated and committed: 0/86
- P0/P1 candidates: preserved, UNVALIDATED, NOT integrated
- Android integration: prohibited until A5 = 86/86 PASS and committed

## Transfer limitation
The connected file service preserved the original candidate bytes in the persistent recovery folder. Direct large-file transfer from that service into GitHub was blocked by the connector safety boundary during recovery, so this Git commit records immutable filenames/checksums and durable storage location rather than pretending the large candidates were embedded in Git.
