# INVERTEBRATA — Exact-commit Android QA execution runbook

Baseline verified from GitHub: `e335f5922ffced7ba9ab26ebbb8536382c787467` on `feat/native-textbook-reader-qa`.
Always replace the baseline with the current branch HEAD before executing. No CI run IDs or APK hashes are asserted here.

## Run order

1. Inspect all workflows and any existing push-triggered runs for the **current** commit on https://github.com/rameshgascngl-create/Invertebrata-/actions .
2. Prefer existing `Native foundation check`, `Native five-unit restoration`, and `Native R1.9 focused Paramecium completion` workflows. Each includes the QA branch in its push trigger.
3. Treat independent academic-review HOLD as **separate** from compilation and device-QA failure.
4. Record `workflow run ID / job ID / commit SHA / conclusion / error excerpt / artifact name` for each run.
5. Do not publish or certify an APK without checking the source SHA, signature, package identity, installed behavior, image contents and checksum.

## Local reproducible commands

Execute from the repository root with Java 17, Android SDK and Gradle 9.6.0 as configured in CI:

```sh
git rev-parse HEAD
python3 .github/scripts/check-student-caption-editorial.py
gradle -p native-app :app:compileDebugKotlin :app:compileDebugAndroidTestKotlin :app:testDebugUnitTest --stacktrace
gradle -p native-app :app:assembleDebug :app:assembleDebugAndroidTest --stacktrace
python3 .github/scripts/audit-five-unit-assets.py --out qa-evidence/five-unit-assets
sha256sum native-app/app/build/outputs/apk/debug/app-debug.apk
unzip -t native-app/app/build/outputs/apk/debug/app-debug.apk
```

The `audit-five-unit-assets.py` inventory has **52 mandatory raster entries**, separate from **43 validated chapters**; it enumerates 35 type-study entries, 9 Unit I replacements/concepts and 8 Nereis entries. It additionally covers 15 supplementary and 12 Pila review entries (79 total). Presence is not expert approval.

## Acceptance matrix

| Area | Required evidence |
| --- | --- |
| Academic text | All 43 validated chapters present, separate supplements VI–VII, bilingual scientific review register |
| Figures | Exact mandatory mapping, immutable accepted PNGs, source/rights traceability; recorded HOLD where missing |
| Kotlin | Successful compileDebugKotlin and compileDebugAndroidTestKotlin |
| JVM | Complete testDebugUnitTest reports with zero unexplained failures |
| Simulator | Nutrition, egestion, ciliary playback, controls and process-death tests |
| UI | English/Tamil, normal and 200% font, portrait/landscape, page navigation |
| APK | Exact SHA, binary checksum, manifest/package, signature, artifact URL and installation result |
| Production | Independent biology/Tamil/rights and physical-device acceptance — no inference from CI green |

## Critical distinction

The `complete-plate-acceptance` and `independent-academic-review` jobs in `native-five-unit-restoration.yml` deliberately fail closed if exact asset/reviewer acceptance is unavailable. This should **not** be fixed by bypassing the validators or fabricating reviews. A development QA APK may be used for testing while production remains blocked.

## Evidence status at authoring

The connected GitHub tool exposed no commit-associated workflow runs for the checkpoint; its workflow-run lookup is limited to pull-request-triggered runs and therefore cannot rule out push-triggered jobs. Actual Actions execution, APK production and emulator results are **not confirmed**. Do not claim they passed.

## Developer reporting contract

Every subsequent update: baseline SHA → changed files → commits → tests actually executed and their results → workflow IDs/artifacts when available → scientific, technical and physical-device blockers. Preserve accepted assets and do not alter `main`.
