# INVERTEBRATA v1.8.8 — BUILD PIPELINE RECONCILIATION PHASE A 01

## Governing state

`SOURCE AUDIT CONDITIONALLY CLOSED — PIPELINE RECONCILIATION ELIGIBLE`

Controlling source contract:
- Matrix 02 commit `8719c2c19bd10c447700f5ba01b2b1b2a6942962` — `provenance/V188_SOURCE_GATE_MATRIX_02.csv`
- Closure 02 commit `9dbe86fd469b100f44ff7ac2d94ed260789808e2` — `provenance/V188_SOURCE_AUDIT_CLOSURE_02.md`

Phase A is planning/evidence only. It does not modify the workflow, academic payload, Gradle files, signing, or Android build configuration; it does not dispatch a workflow and does not build an APK.

## Current workflow audit

Authoritative v1.8.8 workflow: `.github/workflows/invertebrata-v188-debug.yml`.

Trigger state: **manual `workflow_dispatch` only**. No `push` or `pull_request` trigger is present.

Current source-reconstruction portion:
1. verify/extract `INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip`, SHA-256 `281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3`;
2. run `ci/reconcile_r2.py`, checking deterministic R2 ZIP SHA-256 `3f3ffd5d0b03da74b9f7b85cfc755883594aea3141149b1131ee11963d9a184a`;
3. run `ci/reconcile_v188.py` and verify version 18800 / 1.8.8 plus the foundational source manifest;
4. immediately proceeds to Java/SDK/Gradle build steps.

Confirmed defect: the workflow does **not** reconstruct the accepted Batch-1, Batch-2, master, Batch-3, organism-specific remediation, Pass-2 correction, ID-integrity, or specialist-terminology chain required by Closure 02.

## Definitive accepted transformation ledger

| Order | Script / transformation | Source commit | Accepted? | Superseded? | Must execute? | Expected input | Expected output | Dependency / ordering reason |
|---:|---|---|---|---|---|---|---|---|
| 0 | Verify/extract frozen v1.8.7 source ZIP | frozen source evidence | YES | NO | YES | repository ZIP with frozen SHA-256 | clean canonical Android source tree | Hash-anchored root of every deterministic reconstruction. |
| 1 | `ci/reconcile_r2.py` | `b1833264bad70203f4ee146047254e9c5c5204ca` | YES | NO | YES | untouched canonical v1.8.7 tree; payload SHA `252208...`; launcher SHA `dbd00a...` | R2 Android-shell-compatible tree; academic payload unchanged | Script is fail-closed on frozen hashes and is the proven Android-shell reconciliation. |
| 2 | `ci/reconcile_v188.py` | `b1d5278339e45e6b359d4d3d60b467f12bd0d95d` current script revision | YES — foundational transform only | NO as script; old candidate release itself is superseded | YES | R2 tree with original payload SHA `252208...` | v1.8.8 contextual/mobile layer; versionCode 18800/versionName 1.8.8; `v188-contextual-atlas` anchor | It explicitly requires the old payload hash, so it must precede all academic illustration transforms. |
| 3 | `ci/reconcile_v188_svg_batch1.py` | `23d9c2fb2fd45f07a69f84e10217fa6c14b3e5a4` | YES — foundational illustration batch | NO | YES | v1.8.8 contextual source | Batch-1 high-risk/overview `ORG_SYSTEM_DIAGRAMS` assignments and `v188-pencil-batch1` marker | Batch-2 explicitly requires both v1.8.8 and Batch-1 markers. Some Batch-1 figures are supporting/historical rather than accepted primary candidates; later regression must prevent them replacing remediated primaries. |
| 4 | `ci/reconcile_v188_svg_batch2.py` | `6bcd3961aaf67db02799c2f5f589dd832d50d36f` | YES — foundational system figures | NO | YES | v1.8.8 + Batch-1 | system-specific serialized `ORG_SYSTEM_DIAGRAMS` figures | Provides the original candidates later remediated. Must precede every remediation targeting Batch-2 figure IDs. |
| 5 | `ci/reconcile_v188_pencil_atlas_master.py` | `edddef3ed89744751b3c46967b027027ed8bed27` | YES | NO | YES | v1.8.8 + Batch-1 + Batch-2 | four accepted master-style variants, including protected Sycon and Asterias masters | Script itself states it applies after v1.8.8 + Batch-1 + Batch-2. Master variants do not add academic requirement counts. |
| 6 | `ci/v188_pencil_atlas_completion_batch3.py` | `dc0f43d1aad1b6e2431badcd0b812c192aef42e1` | YES | NO | YES | foundational illustrated source | 20 mapped Batch-3 production figures | Must exist before serializer-aware Pass-2 Batch-3 callouts can find all 20 exact figure IDs. |
| 7 | Earthworm targeted remediation effect from `ci/reconcile_v188_earthworm_remediation.py` | `237a4a3a01f95aa32be4cc3827d4c0531950f5a3` | YES | NO | YES, but **not by blindly invoking current raw-regex implementation** | Batch-2 Earthworm candidates | accepted external/digestive/vascular/excretory/reproductive-landmarks replacements; nervous candidate retained | Current implementation searches literal raw `<figure ...>` markup, whereas Batch-2 figures are serialized JSON strings in JS assignments. Phase B needs a serializer-aware execution adapter preserving the exact accepted replacement markup. |
| 8 | `ci/reconcile_v188_earthworm_tamil_terms_01.py` accepted terminology effect | `b0bcdef3e628eb68c2206169d5aedceea9cad394` | YES | NO | YES, after Earthworm remediation | accepted Earthworm remediated vascular caption | vascular Tamil caption corrected to `இரத்த ஓட்ட மண்டலம்` | Has an exact one-occurrence precondition on the revised Earthworm caption; therefore depends on order 7 succeeding. |
| 9 | `ci/v188_penaeus_targeted_rectification.py` | `4a1a8ed552a3629f407114eda816583d021d96e2` | YES | NO | YES | Batch-1/2/master Penaeus source | PENAEUS-01-R1, specialised appendage child series, PENAEUS-05-R1, PENAEUS-08-N1, PENAEUS-09-R1 appended as an accepted targeted overlay | Natively emits a serialized `ORG_SYSTEM_DIAGRAMS["u4-penaeus"]` assignment and has an already-applied guard. Pass-2 systems later requires PENAEUS-09-R1. |
| 10 | Obelia targeted remediation effect from `ci/v188_obelia_targeted_remediation.py` | `7c6e9b9c911f556f12818f2decac0e3cfc7ee74a` | YES | NO | YES via serializer-aware adapter | Batch-2 Obelia colony/medusa/life-cycle candidates | OBELIA-01-R1, OBELIA-02-R1, OBELIA-03-R1 replacing those candidates | Must occur after Batch-2. Current raw-regex implementation is not safe against JSON-serialized figure definitions; preserve exact accepted markup through a serializer-aware adapter. |
| 11 | Nereis targeted remediation effect from `ci/v188_nereis_targeted_remediation.py` | `f3d3331ced8a1374bea5897ae5f6cd30b348681b` | YES | NO | YES via serializer-aware adapter | Batch-2 Nereis candidates | NEREIS-01-R1, NEREIS-02-R1 plus NEREIS-06-N1 and NEREIS-07-N1; digestive/circulatory/nervous preserved at this stage | Pass-2 systems later expects `NEREIS-06-N1` and modifies digestive/nervous. Current target script is raw-regex and requires serializer-aware execution. |
| 12 | Pila targeted remediation effect from `ci/v188_pila_targeted_remediation.py` | `b4c31e6e1f73b4006da09c21ca1ba22f98ffdc51` | YES | NO | YES via serializer-aware adapter | Batch-2 Pila candidates | PILA-EXT-R1, PALLIAL-R1, DIG-R1, EXC-N1, NER-R1, REP-R1; circulatory candidate untouched | Must preserve the protected Batch-2 circulatory figure. Current script performs raw figure regex replacement/insertion and therefore needs serializer-aware execution. |
| 13 | Asterias targeted remediation effect from `ci/v188_asterias_targeted_remediation.py` | `28b062761b9759835b79ca8cf173ec41515c76f6` | YES | NO | YES via serializer-aware adapter | Batch-2 Asterias digestive/reproductive candidates plus protected masters | ASTERIAS-DIG-R1 and ASTERIAS-REP-R1 only | WVS/oral/aboral/nervous are protected; no five former provisional masters may be created. Current implementation requires serializer-aware execution. |
| 14 | Fasciola + Ascaris reproductive remediation effect from `ci/v188_fasciola_ascaris_targeted_remediation.py` | `362dd0632fd45aa50a5e1562270360e070e7a583` | YES | NO | YES via serializer-aware adapter | master Fasciola reproductive candidate + existing combined Ascaris reproductive candidate | FASCIOLA-REP-R1; separate ASCARIS-FEMALE-REP-R1 and ASCARIS-MALE-REP-R1 | Must precede Pass-2 terminology, which targets FASCIOLA-REP-R1. Current implementation is a direct HTML/figure replacement and needs serializer-safe application. |
| 15 | Paramecium CV/cilia targeted creation effect from `ci/v188_paramecium_cv_cilia_targeted_creation.py` | `525b6f0c202d4fb03943d980e4d67588b03a3a7c` | YES | NO | YES via serializer-aware adapter | Batch-3 Paramecium external/oral candidate | independent PARAMECIUM-CV-CILIA-N1 inserted without replacing external/oral plate | Both frozen requirements must coexist. Current direct figure-anchor implementation must be adapted to the serialized source. |
| 16 | `ci/v188_source_blocker_pass2_batch3_labels.py` — serializer-aware | `08cb7fa08ada4ee04a4fcf1e8e9d4074b1c09086` | YES | NO | YES | Batch-3 figures already present | visible callouts + language-aware legends on exactly 20 Batch-3 figures | Uses JSON decode/re-serialize of serialized diagram assignments and fails unless every expected figure occurs exactly once. Must not use superseded `77a9...`. |
| 17 | `ci/v188_source_blocker_pass2_systems.py` — serializer-aware | `a5799a3110e3adc851071e3844de697b8398ce4e` | YES | NO | YES | accepted targeted organism states, including NEREIS-06-N1 and PENAEUS-09-R1 | 12 accepted Pass-2 system/scope corrections, including NEREIS-06-R2 and p.29/p.34/p.21 scope closures | Exact-occurrence fail-closed transform. Must run after organism-specific remediation so the candidates it expects exist. Must not use superseded `3523...`. |
| 18 | `ci/v188_source_blocker_pass2_integrity.py` | `1ee876c290befd01afdbfee8f83007b717f2c305` | YES | NO | YES, last structural transform | all accepted figure-creating/modifying transformations complete | Sycon source-presentation correction + figure-scoped duplicate SVG IDs with internal references rewritten; source-definition integrity asserted | Must be after every figure-producing transform, otherwise later transforms could reintroduce collisions or the old Sycon prose. It fail-closes on duplicate IDs, broken refs and duplicate plate IDs in decoded source definitions. |
| 19 | `ci/v188_source_blocker_pass2_terminology.py` | `1a2185cb852d3cedba781a99fb561bf9bdedaef5` | YES | NO | YES, final content-text overlay | final accepted structural figures including FASCIOLA-REP-R1, PILA-EXC-N1, NEREIS-07-N1, PENAEUS-09-R1 | accepted specialist Tamil/scientific-term treatment | Serializer-aware exact-occurrence transform. Must follow all structural replacement steps so terminology is applied to the final candidates and is not overwritten. |

The executable transformation count after canonical source extraction is therefore **19 accepted transforms**. Order 0 is the frozen-input verification/extraction gate and is not counted as a source transform.

## Explicitly excluded / superseded implementations

The following must **not** execute in the authoritative chain:

- `77a9f0610564c7e2513fc3ea552c90f7656f0adb` — initial Batch-3 label implementation; superseded by serializer-aware `08cb7fa08ada4ee04a4fcf1e8e9d4074b1c09086`.
- `3523f3493f92bf3a7b176a9bff86f524499eefd2` — initial Pass-2 systems implementation; superseded by serializer-aware `a5799a3110e3adc851071e3844de697b8398ce4e`.
- `70c1b32e517e8711eafc055d611c34b0d0f1992a` — earlier mobile-text-only revision of `reconcile_v188.py`; superseded by the current foundational script revision at `b1d527...`.
- The old v1.8.8 candidate build/run and its generated APK are evidence/history only, not pipeline inputs.
- Audit/provenance transforms such as `reconcile_v188_svg_batch2_audit.py`, `reconcile_v188_pencil_atlas_audit.py`, `v188_audit_phase1_reconcile.py`, `v188_batch3_audit_overlay.py`, `v188_formal_audit_overlay_01.py`, and files under `provenance/` are evidence/audit controls and must not be executed as academic source transformations.

## Phase-A compatibility finding

The accepted academic effects are known, but **the current legacy implementation of several targeted remediation scripts cannot safely be invoked directly in the final workflow**.

Reason: Batch-1/2/master/Batch-3 source figures are injected as JSON-serialized strings inside `window.ORG_SYSTEM_DIAGRAMS[...]` JavaScript assignments. The Earthworm, Obelia, Nereis, Pila, Asterias, Fasciola/Ascaris, and Paramecium targeted scripts search the raw HTML source for unescaped `<figure ...>` markup. That markup exists only after JSON decoding, so blindly executing those scripts after Batch-2 risks occurrence mismatch/failure and cannot be accepted as a deterministic reconciliation method.

This is a **pipeline-mechanics gap, not an academic-source contradiction**. Closure 02 remains the academic contract.

Phase B therefore needs a deterministic serializer-aware adapter/harness that:
1. decodes only the `ORG_SYSTEM_DIAGRAMS` serialized assignments;
2. applies the exact already-approved replacement/insertion markup from the accepted targeted scripts — no academic redesign;
3. requires exact candidate/precondition counts;
4. re-serializes deterministically;
5. fails closed when an expected input candidate is absent or a replacement is already present;
6. keeps both payload copies byte-identical.

Penaeus targeted rectification is already a serialized append overlay and does not need that raw-figure adapter.

## Required Phase-B execution plan

1. Keep `.github/workflows/invertebrata-v188-debug.yml` manual-only.
2. Preserve the canonical ZIP hash and R2 deterministic hash assertions.
3. Keep the foundational manifest/version validation immediately after `reconcile_v188.py`; do **not** misinterpret that manifest as the final post-illustration manifest.
4. Add a source-reconstruction-only sequence implementing orders 3–19 above, using serializer-aware adapters for orders 7 and 10–15.
5. Add exact precondition/idempotence guards before every transform.
6. After order 19, run final static source validation and generate a deterministic authoritative reconstructed-source SHA-256. Do not inject timestamps into source content.
7. Reconstruct twice from the same canonical input and compare final source hashes.
8. Use Matrix 02's 76 requirement rows as a presence/regression contract: accepted candidate exists, intended lesson mapping exists, visible instructional treatment exists, and a superseded candidate has not replaced it as the accepted primary requirement.
9. Audit final reconstructed DOM/SVG IDs and all internal URL/href/marker/mask/pattern/gradient/clip references.
10. Only after successful deterministic reconstruction may pipeline-dependent contextual 360px/enlarged source rendering be tested.
11. If any source contradiction or render defect appears, stop as `SOURCE REMEDIATION REQUIRED`; do not hide content changes in the pipeline.
12. Stop after pipeline audit; workflow dispatch, Gradle, APK generation, signing and device QA remain separately forbidden until later authorization.

## Phase-A gate

- Workflow filename confirmed: `.github/workflows/invertebrata-v188-debug.yml`.
- Trigger confirmed: **manual `workflow_dispatch` only**.
- Current pipeline defect confirmed: only R2 + foundational v1.8.8 are reconstructed before build setup.
- Accepted transformation ledger: **COMPLETE — 19 executable transforms after canonical extraction**.
- Superseded Pass-2 implementations to execute: **0**.
- Serializer-compatibility blocker for direct legacy remediation invocation: **OPEN — to be solved in Phase B without changing academic content**.
- Workflow modified in Phase A: **NO**.
- Workflow dispatched: **NO**.
- Gradle build: **NO**.
- APK generated: **NO**.
- Device QA: **NO**.
- FINAL: **NO**.
