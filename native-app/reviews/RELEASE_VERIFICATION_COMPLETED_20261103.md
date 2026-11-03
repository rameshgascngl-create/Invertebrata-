# Release verification completed — 3 November 2026

**The previously failing release functional cohort now passes in all four configurations.**

Tested commit: [727e7f7891ecd2ce42f6aed31083e5a0b74d531a](https://github.com/rameshgascngl-create/Invertebrata-/commit/727e7f7891ecd2ce42f6aed31083e5a0b74d531a).
[Functional workflow 41569136932](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41569136932): seven jobs passed, including compilation.
[Tamil/JVM workflow 41569136959](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41569136959): passed.

## Raw XML evidence

| Build | Configuration | Passed | Failed / errors / skipped |
|---|---|---:|---:|
| Release | English 100% | 3 | 0 / 0 / 0 |
| Release | Tamil 100% | 3 | 0 / 0 / 0 |
| Release | Tamil 200% portrait | 3 | 0 / 0 / 0 |
| Release | Tamil 200% landscape | 3 | 0 / 0 / 0 |
| Debug | Tamil 200% portrait | 3 | 0 / 0 / 0 |
| Debug | Tamil 200% landscape | 3 | 0 / 0 / 0 |
| **Total** | **Six configurations** | **18** | **0 / 0 / 0** |

Every uploaded raw TEST XML was read, not merely the Actions job badge. Each configuration ran exactly the expected three methods:
- defaultPagesAcrossAllFiveUnitsKeepPositionContentsAndExactAnswers
- allFiveSimulatorsMoveRealDiagramPixelsAndPause
- parasiteAndPrawnInteractivePagesAreReachable

Release XML names NativeReleaseFunctionalTest; debug XML names NativeFunctionalRepairsTest.
The simulator method exercised osmoregulation, ciliary motion, feeding, binary fission and conjugation, preserving changed-pixel, intermediate-phase and stopped-pixel assertions.

The current JVM artifact was also read: **252 tests, zero failures/errors/skips**. Logs confirm eight Tamil Python tests and five evidence-classification tests passed, and instrumentation compiled.

## What changed

The release test driver now uses advertised vertical accessibility actions instead of a container aspect ratio, searches back towards controls above a stage selector, records lookup hierarchies, reaches Play/Pause after full-plate positioning, and waits for stable bounds before diagram capture. Comparison images are saved before assertions.

The intervening failed runs are retained in [the viewport investigation](RELEASE_VIEWPORT_VERIFICATION_20261103.md). No dimension assertion was removed, no comparison image was resized and no missing-node result was treated as success. These observations support a test-navigation/capture explanation for the investigated failures; they do not certify every app interaction or every device.

## Landscape capture inspection

The final binary-fission and conjugation start/paused PNGs were downloaded and visually inspected. All four captures measure **2126 × 499 px**. They show the complete diagram region at consistent dimensions; the functional assertions confirm changed diagram pixels and stable pixels after pause. This is rendering evidence, not independent scientific approval of the schematic.

[Machine-readable evidence](RELEASE_VERIFICATION_RESULTS_20261103.json) records testcase rows, artifact IDs/digests and each runner's APK checksums. APKs were built independently on separate runners; their recorded identities must not be collapsed into one supposedly identical APK.

## Scope and release hold

Environment: API 34, Pixel 6 profile, x86_64 emulators. Release used the established lossless WebP overlay and minified build path. The eight broader established regression groups were not rerun by this focused workflow. Physical-device execution, audible Tamil TTS and production signing remain unverified.

This closes the investigated functional cohort at the tested commit. It does **not** clear academic sign-offs, unresolved image source/rights records, frozen-A5 proposals, flagellar-pocket terminology approval, production signing, physical-device acceptance or the required merge order. No production release or merge was performed.
