# Unit V image quarantine — validation and acceptance checklist

Baseline: commit 3f73f418c5001a750285ee4f6605c72b10892381 moved 12 Pila and 4 Asterias candidate PNGs into debug assets. This is an image-quarantine change, not an approved replacement.

## Build-specific tests required
- Check exact commit SHA in Actions checkout and APK metadata.
- Run `./gradlew :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease` where signing configuration permits; verify no rejected PNGs are packaged in the release APK using a ZIP entry listing, not just the filesystem.
- Review the source catalog for accidental references to `src/debug/assets` from release code.
- Inspect emulator screenshots in English and Tamil at 100% and 200% font scales, including zoom and missing-image states.
- Test offline navigation and process restoration; collect screenshot and assertion XML artifacts.
- Preserve separate zoological, Tamil editorial, rights and physical-device signoffs.

## Image acceptance rule
Never mark a chapter's mandatory anatomical plate present merely because a PNG file exists. Require a subject-specific, anatomically reviewed image, accurate leader-line endpoints, legible at enlargement, and documented original creator, licence and modifications. Credit alone does not establish reuse rights.

## Current result
This checklist is documentation only. No new plates are approved, no runtime tests were executed by this commit, and release remains HOLD.
