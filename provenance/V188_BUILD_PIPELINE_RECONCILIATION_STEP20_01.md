# INVERTEBRATA v1.8.8 — BUILD PIPELINE RECONCILIATION STEP-20 01

## Scope

This change reconciles the manual v1.8.8 debug-build workflow to the already accepted 20-step deterministic source reconstruction. It changes pipeline mechanics only. No academic payload, figure, caption, label, placement rule, contextual scorer, Step-2 transform, Step-20 transform, Android package identity, or version identity is edited.

## Governing accepted identities

- Step-20 implementation commit: `38e02d891d466dcd6d00734b0123656b190c8b3a`
- Step-20 Git blob: `3247e5c2dac58f9c779c6282ca4203722daa206c`
- Step-20 SHA-256: `51a4234efce0489b113dc94a485af0c827d38fdec1ab39a1ece9534f82776e79`
- Accepted-transform manifest commit: `adc980e00346b7c849824c9e84a35e4711404ac8`
- Accepted-transform count: **20**
- Step-2 Git blob: `1161c46184c3d69530c1cad74db774734b4947af`
- Step-2 SHA-256: `350054a4c93c0849117c6eefc9da4f4bcbf5841d92970fbe1ab19151681380d3`
- Step-2 changed: **NO**
- Authoritative HTML SHA-256: `bdb39d4fa1f438c5de83fc9a84ab33905016854c1f641227dbc23063c14c2c46`
- Authoritative HTML size: **2,393,951 bytes**
- Authoritative source-tree manifest SHA-256: `dbcf20925081e3ba67e3a2e4110f8c5fff619d237c262a8bd47d507de3358544`

## Reconciliation rule

The manual debug workflow must call `ci/reconstruct_v188_accepted_source.py`. It must not hand-assemble the old R2 + Step-2-only pipeline. Before Java, Android SDK, Gradle, or APK generation, the workflow must:

1. pass the accepted-transform preflight;
2. reconstruct from the frozen canonical ZIP twice;
3. execute exactly 20 accepted transforms and zero superseded transforms;
4. prove Run A and Run B are byte-identical;
5. prove the final HTML hash and size match the accepted Step-20 identity;
6. prove the full source-tree manifest hash matches the accepted identity;
7. prove versionCode 18800 and versionName 1.8.8 remain present.

Only after all seven checks pass may the manual workflow install build tools and run Gradle.

## Uploaded 1.0.3 Android project

The separately supplied `InvertebrateLab_1.0.3_android_project.zip` is not an authoritative academic payload for this release. Its bundled HTML is 1,045,646 bytes, SHA-256 `d48d16ef2f66ff5b651e717cfd7e7334d195bf1ef438af8bcb61a038fdd957fe`, which differs from the accepted v1.8.8 payload. Its hardened offline WebView design may be used as reference, but its academic HTML must not replace the accepted source.

## Audit boundary

The reconciliation audit workflow is source-only. It may run on the audit branch to prove the workflow wiring and deterministic source identity. It must not install Gradle, invoke Gradle, build an APK/AAB, sign, or start device QA.

The production/debug build workflow remains `workflow_dispatch` only.

## State before reconciliation audit

BUILD PIPELINE RECONCILIATION: IMPLEMENTED ON AUDIT BRANCH / AUDIT PENDING  
GRADLE: NOT INVOKED  
APK: NOT GENERATED  
AAB: NOT GENERATED  
DEVICE QA: NOT STARTED  
FINAL: NO
