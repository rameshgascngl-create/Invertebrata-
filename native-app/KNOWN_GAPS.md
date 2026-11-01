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

## 5. Still open (needs product input)
`parameciumDraftIsReachableInEnglishAndTamilWithoutChangingA5` expects the Paramecium draft notice
(`n22-draft-notice`, rendered only by the generic chapter list) on the Paramecium chapter page.
Decide where that notice should appear for the reader the chapter now opens in.
