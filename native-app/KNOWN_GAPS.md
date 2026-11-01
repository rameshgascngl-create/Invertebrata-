# Known gaps (owner decisions, 2026-11-01)

## 1. Organ-level atlas selection is unreachable
The legacy Paramecium atlas composables (`AtlasSection`, `VacuoleAtlasSection`, `AtlasSketch`,
`ParameciumRasterAtlas`) have no callers, so students cannot touch-select organs or open the
vacuole mechanism review from the Anatomy tab. The master plan lists organ touch/highlight as
required; **this is a product gap, not a retired requirement.** Restoring it needs PNG-compatible
hotspots with scientific review.

What students CAN do today: open the Oral apparatus and Contractile vacuoles plates in the
Anatomy gallery, open the full-screen viewer (pinch/pan, zoom in/out/reset/close), use the Listen
tab narration, and run the Simulate tab (osmoregulation, conjugation 7 stages, etc.).

Tests skipped with `@Ignore` while the gap stands (they exercise the unreachable controls or the
removed Canvas): r11VacuoleAtlas..., r12ContractileComplex..., r14UnitOpensIllustratedTextbook...,
nativeParameciumCanvasButtons..., provisionalCanvasDirectTap..., fourStagesAndTouchSelection...,
r14NativeReaderRetainsReadableTamilHeadings..., tamilTwoHundredPercentCanvasControls...,
r12MechanismControlsAndTamilTerminology... Replacement coverage for what is reachable:
the two gallery tests in `NativeUiAcceptanceTest` (`oralApparatusAndContractileVacuolePlatesAreReachableFromAnatomy`, `plateOpensFullScreenViewerWithZoomControls`).

Likely also affected, not run in the four workflows used so far: `NativeUnitIDiagramAccessTest`
(uses `r1-atlas-<panel>` and `r1-atlas-select-*`).

## 2. Plate resolution
The Paramecium PNGs are small (ciliary 460x372, oral 305x328, vacuoles 405x375 px). At 220 dp tall
they are enlarged about 1.8x on a modern phone and the zoom viewer softens them further.
Higher-resolution originals are recommended.

## 3. Draft questions inside chapter pages
Student practice and its count are limited to the 86 validated questions. The 40 DRAFT_UNVERIFIED
extension questions (Unit VI Insecta 24, Unit VII supplementary 16) still appear inside their own
chapter pages.

## 4. Uploaded plate candidates
Six plates added/replaced on 2026-10-26 have no recorded source or licence. See
`provenance/UPLOADED_CANDIDATES_20261026.md`.

## 5. Draft marking on chapter pages (addressed)
Before this change, none of the 43 A5 chapter readers showed a chapter-level draft notice
(only figure-level "review pending" credits; the earthworm reader and the Paramecium lab had
their own warnings). A one-line bilingual notice now appears in the header of the book, Unit II
and Unit III readers whenever the chapter status is not ACCEPTED (tag `chapter-draft-notice`).
It reuses the app's existing review-warning wording and disappears automatically once a chapter
is accepted.

Dead code: the generic chapter list's `lessonDraft` / `n22-draft-notice` block can no longer be
reached (the only chapter with a draft lesson, `u1-paramecium`, opens in the book reader). It can
be removed in a later clean-up; it was left untouched here.

## 6. CI screenshot evidence
`.github/scripts/run-native-api34-tests.sh` used to REQUIRE six Canvas-era screenshots
(paramecium-normal-oral-groove, r14-textbook-normal, r15-feeding-normal, r14-textbook-tamil200,
r15-feeding-tamil200, paramecium-tamil200-cytoproct). All six were written by tests that are now
skipped (section 1), so the runner failed with ANATOMY_SCREENSHOT_MISSING before the Tamil 200%
phase. It now requires real screenshots of the reachable gallery instead:
`paramecium-anatomy-gallery-normal.png`, `paramecium-plate-viewer-normal.png` (normal phase) and
`paramecium-gallery-tamil200.png` (Tamil 200% phase, new test
`anatomyGalleryIsReadableAtTamilTwoHundredPercent`). Screenshot evidence stays mandatory; only
its subject changed.

## 7. Tamil 200% tests updated for the real readers
`tamilNavigationAndFullAnswerRemainReachableAtTwoHundredPercent` now scrolls to each question/answer
node in the book reader instead of assuming it is composed. `parameciumDraftSections...` tested the
unreachable draft-lesson sections; it is replaced by
`parameciumChapterDraftNoticeAndQuestionsAreReachableAtActualTamil200Percent` (draft notice at the
top, validated questions at the bottom of the reader the chapter really opens in).
