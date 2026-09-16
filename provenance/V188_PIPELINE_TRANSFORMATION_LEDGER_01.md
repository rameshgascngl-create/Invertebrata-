# INVERTEBRATA v1.8.8 — accepted pipeline transformation ledger 01

Purpose: freeze the executable source-reconstruction chain authorized by Source Audit Closure 02 (`9dbe86fd469b100f44ff7ac2d94ed260789808e2`). This is pipeline evidence, not an APK build authorization.

## Current workflow defect confirmed

Target workflow: `.github/workflows/invertebrata-v188-debug.yml`.

Current trigger is `workflow_dispatch` only. Current reconstruction stops after deterministic R2 reconciliation plus `ci/reconcile_v188.py`; therefore it does **not** reconstruct the later accepted Batch-1/Batch-2/master/Batch-3/organism-remediation/Pass-2 source state represented by Matrix 02.

## Definitive accepted chain

| Order | Transformation | Provenance commit | Accepted? | Superseded? | Input state | Output state | Must pipeline apply? | Reason |
|---:|---|---|---|---|---|---|---|---|
| 1 | `ci/reconcile_r2.py` | `b1833264bad70203f4ee146047254e9c5c5204ca` | YES | NO | canonical v1.8.7 source ZIP | deterministic R2 shell/source | YES | frozen Android-shell reconciliation and hash gate |
| 2 | `ci/reconcile_v188.py` | `b1d5278339e45e6b359d4d3d60b467f12bd0d95d` | YES | NO | R2 source | v1.8.8 mobile/contextual baseline | YES | establishes version 18800/1.8.8 and contextual-atlas anchor required by later figure injections |
| 3 | `ci/reconcile_v188_svg_batch1.py` | `23d9c2fb2fd45f07a69f84e10217fa6c14b3e5a4` | YES | NO | v1.8.8 baseline | first high-risk pencil figures | YES | accepted historical illustration layer; specifically superseded individual plates are removed later by bridge |
| 4 | `ci/reconcile_v188_svg_batch2.py` | `6bcd3961aaf67db02799c2f5f589dd832d50d36f` | YES | NO | Batch-1 source | system-specific production candidates | YES | active base candidates for Matrix-02 system rows |
| 5 | `ci/reconcile_v188_pencil_atlas_master.py` | `edddef3ed89744751b3c46967b027027ed8bed27` | YES | NO | Batch-2 source | accepted Sycon/Fasciola/Penaeus/Asterias master plates | YES | provides protected Sycon canal, generalized Penaeus appendage and Asterias WVS masters |
| 6 | `ci/v188_pencil_atlas_completion_batch3.py` | `dc0f43d1aad1b6e2431badcd0b812c192aef42e1` | YES | NO | master source | 20 remaining mapped Batch-3 production plates | YES | source for the 20 Matrix-02 Batch-3 rows |
| 7 | Earthworm targeted content from `ci/reconcile_v188_earthworm_remediation.py` | `237a4a3a01f95aa32be4cc3827d4c0531950f5a3` | YES | direct legacy `main()` NOT used | generic batches | accepted Earthworm R2 figures | YES, serializer-aware bridge | accepted content is preserved; direct legacy matcher is incompatible with serialized diagram definitions |
| 8 | Earthworm Tamil vascular-caption correction from `ci/reconcile_v188_earthworm_tamil_terms_01.py` | `b0bcdef3e628eb68c2206169d5aedceea9cad394` | YES | direct legacy `main()` NOT used | Earthworm R2 figures | accepted caption terminology | YES, serializer-aware bridge | narrow accepted terminology correction |
| 9 | Penaeus targeted content from `ci/v188_penaeus_targeted_rectification.py` | `4a1a8ed552a3629f407114eda816583d021d96e2` | YES | append-all legacy `main()` NOT used | Batch-2/master Penaeus | R1/N1 accepted candidates while protected 02/04/06/07 remain | YES, serializer-aware bridge | replace superseded candidates instead of retaining old + new duplicates |
| 10 | Obelia targeted content from `ci/v188_obelia_targeted_remediation.py` | `7c6e9b9c911f556f12818f2decac0e3cfc7ee74a` | YES | direct legacy `main()` NOT used | Batch-2 Obelia | OBELIA-01/02/03-R1 | YES, serializer-aware bridge | preserve p.16 remediated state |
| 11 | Nereis targeted content from `ci/v188_nereis_targeted_remediation.py` | `f3d3331ced8a1374bea5897ae5f6cd30b348681b` | YES | direct legacy `main()` NOT used | Batch-2 Nereis | NEREIS-01/02-R1 + 06/07 new treatments | YES, serializer-aware bridge | preserve accepted target state; Pass-2 later updates NEREIS-06 to R2 |
| 12 | Pila targeted content from `ci/v188_pila_targeted_remediation.py` | `b4c31e6e1f73b4006da09c21ca1ba22f98ffdc51` | YES | direct legacy `main()` NOT used | Batch-2 Pila | accepted Pila R1/N1 figures; circulatory untouched | YES, serializer-aware bridge | preserve protected circulatory core and accepted p.41 remediation |
| 13 | Asterias targeted content from `ci/v188_asterias_targeted_remediation.py` | `28b062761b9759835b79ca8cf173ec41515c76f6` | YES | direct legacy `main()` NOT used | Batch-2/master Asterias | digestive/reproductive R1 only | YES, serializer-aware bridge | WVS/oral/aboral/nervous protected; five rejected independent masters remain absent |
| 14 | Fasciola/Ascaris targeted content from `ci/v188_fasciola_ascaris_targeted_remediation.py` | `362dd0632fd45aa50a5e1562270360e070e7a583` | YES | direct legacy `main()` NOT used | Batch-1/2/master figures | Fasciola reproductive R1 + separate Ascaris female/male R1 | YES, serializer-aware bridge | prevents superseded generic reproductive plates from remaining active |
| 15 | Paramecium CV/cilia content from `ci/v188_paramecium_cv_cilia_targeted_creation.py` | `525b6f0c202d4fb03943d980e4d67588b03a3a7c` | YES | direct legacy `main()` NOT used | Batch-3 Paramecium | independent PARAMECIUM-CV-CILIA-N1 beside external/oral figure | YES, serializer-aware bridge | preserves both independent frozen requirements |
| 16 | `ci/v188_source_blocker_pass2_batch3_labels.py` | `08cb7fa08ada4ee04a4fcf1e8e9d4074b1c09086` | YES | NO | Batch-3 figures present | accepted 20 figure-specific visible callout treatments | YES | serializer-aware replacement; supersedes `77a9f0610564c7e2513fc3ea552c90f7656f0adb` |
| 17 | `ci/v188_source_blocker_pass2_systems.py` | `a5799a3110e3adc851071e3844de697b8398ce4e` | YES | NO | accepted organism remediations present | 12 accepted system/scope corrections | YES | serializer-aware replacement; supersedes `3523f3493f92bf3a7b176a9bff86f524499eefd2` |
| 18 | `ci/v188_source_blocker_pass2_integrity.py` | `1ee876c290befd01afdbfee8f83007b717f2c305` | YES | NO | Pass-2 content complete | Sycon presentation correction + scoped SVG IDs + rewritten internal refs | YES | final source-definition integrity transform |
| 19 | `ci/v188_source_blocker_pass2_terminology.py` | `1a2185cb852d3cedba781a99fb561bf9bdedaef5` | YES | NO | integrity-scoped source | accepted specialist terminology treatment | YES | final text-only terminology layer |

## Explicitly excluded executable transformations

- `77a9f0610564c7e2513fc3ea552c90f7656f0adb` — superseded Batch-3 Pass-2 implementation. **EXECUTE: NO.**
- `3523f3493f92bf3a7b176a9bff86f524499eefd2` — superseded system-blocker Pass-2 implementation. **EXECUTE: NO.**
- Audit/provenance overlays are evidence, not source transformations. **EXECUTE: NO.**
- The legacy direct-match `main()` functions for organism remediation scripts listed at orders 7–15 are not used. Their already-audited figure constants are applied through one fail-closed serializer-aware bridge because the production figures live in JSON-serialized `ORG_SYSTEM_DIAGRAMS` assignments.

## Superseded individual figures removed by the serializer-aware bridge

To prevent earlier generic layers from remaining active beside their accepted replacements, the bridge removes only the explicitly superseded instructional figures after replacement is established: the failed Batch-1 Earthworm composite, the old Batch-1 Sycon canal plate after the protected master exists, the old Batch-1 Fasciola reproductive plate after `FASCIOLA-REP-R1` exists, and the old Batch-1 combined Ascaris reproductive plate after the separate female/male R1 figures exist. Other Batch-1 overview plates are retained unless Matrix-02/provenance explicitly supersedes them.

## Execution invariant

Accepted transformation count: **19**. Every accepted transformation is applied exactly once. Superseded Pass-2 implementations executed: **0**. Each critical step must fail closed on absent/duplicate preconditions or an already-applied marker.

No Gradle, workflow dispatch, signing, APK generation, or device QA is authorized by this ledger.