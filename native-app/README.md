# INVERTEBRATA native migration

**Authoritative project continuity:** Read [the original full educational specification and recovery roadmap](EDUCATIONAL_MASTER_PLAN.md) and [the verified implementation ledger](IMPLEMENTATION_STATUS_LEDGER.md) before any further development. This must become a full bilingual native Zoology teaching/simulation app, not primarily an A5 question bank. Completing CI is not completing the syllabus.

This directory is the native Kotlin/Jetpack Compose replacement for the legacy HTML/WebView runtime.

## Non-negotiable architecture

- Kotlin + Jetpack Compose only for application UI.
- No WebView, HTML rendering, JavaScript engine, or browser runtime.
- Offline-first; no INTERNET permission.
- MVVM/StateFlow state model.
- Native Compose Navigation.
- Academic content migrates from the committed recovery validation corpus without editorial drift.
- Native diagrams will be implemented with Compose vector/Canvas APIs only after their scientific validation gate.

## Academic provenance gate

Source recovery branch: `recovery/v193-academic-a5-20261008`

Accepted A5 gate HEAD: `c35cc668820464614c5b5aeadff9573716f2263a`

Required counts: 5 units, 43 chapters, 86 bilingual A5 pairs (14/18/14/16/24).

The initial scaffold deliberately contains no migrated academic prose. Migration is a later parity-controlled phase.

## Release identity

During migration the existing `applicationId`, `versionCode` and `versionName` remain unchanged. Version promotion to v1.9.3 requires explicit approval after academic, diagram, native-behaviour and device-QA gates pass.
