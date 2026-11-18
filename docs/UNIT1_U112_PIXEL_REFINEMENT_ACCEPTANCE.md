# U1.12 — Native Android anatomical endpoint refinement (debug-only QA)

**Source branch:** `native/u112-pixel-endpoint-review-20261016`
**Parent:** `native/u111-image-first-review-accessibility-20261015` (draft PR #25)

## Scope and architectural contract

U1.12 enables a zoological reviewer to **measure and refine a draft mark**
against the *actual immutable* raster pixels in the existing, debuggable-only
Jetpack Compose image reader for `T. brucei` bloodstream trypomastigote and
`Leishmania` intracellular amastigote. The scope is NOT original pencil
art creation, accepted labels, digital pencil simulation, release artwork or
student-facing touch hotspots.

- **Native Kotlin only:** `NativeDraftPixelRefinement.kt`, Compose controls in
  `UnitIAnatomyRasterAudit.kt`, JVM and Android instrumentation.
- **No HTML, SVG/WebView, online services, new permissions or asset changes.**
- U1.11 image-first raster position remains unchanged; candidate-coordinate
  controls are placed **below the raster**, before verbose editor narration.
- After a tap, show the most recent draft point's exact source
  `pixelX, pixelY, normalizedX, normalizedY` values, in both language modes.
- Provide minimum 48dp X−, X+, Y−, Y+ Android controls, with
  meaningful English/Tamil TalkBack content descriptions.
- Reproject the highlighted endpoint on the same bitmap after any zoom/pan.
  A ring denotes the currently adjusted *draft* endpoint, not a proven organ.
- Moving one source pixel preserves subpixel coordinate precision. Reject
  out-of-bounds and non-finite locations, impossible image dimensions, and
  invalid move increments. **Never snap an off-image tap to the nearest organ.**
- Existing source SHA-256, stage/morphotype and draft-only JSON provenance
  are retained. One-pixel changes are reflected in export; undo still removes
  the last mark. No draft points are published to student mode.
- Real handwritten/graphite images remain absent. Do not misrepresent
  attributed digital source plates as independent pencil scans.

## Same-commit verification

1. Inspect exact HEAD SHA, diff scope and unchanged 43 A5 academic JSONs,
   app identifiers, images and native architecture.
2. Kotlin JVM `NativeDraftPixelRefinementTest`: one-pixel reversibility,
   normalized value recalculation, source-raster bounds, non-finite rejection,
   zoom/pan inverse-projection round trip.
3. Compile `compileDebugKotlin`, `compileReleaseKotlin`,
   `compileDebugAndroidTestKotlin` and `testDebugUnitTest` for the same SHA.
4. API34 airplane-mode instrumentation: 4/4 original Unit I tests at both
   100% and 200% font, including U1.11 immediate image visibility and U1.12
   X+ then X− coordinate restoration.
5. Visually inspect screenshot pair:
   `u12-100-u112-trypanosoma-pixel-refinement.png`,
   `u12-200-u112-trypanosoma-pixel-refinement.png`.
   The image must remain intact, not clip, and the draft marker and 48dp
   coordinate controls must be accessible through normal scrolling.
6. Physical Android phone: exact finger endpoints, pinch/pan, 200% Tamil,
   TalkBack, rotation, offline operation, no network voice, source checksum
   export, and a non-debuggable build with zero reviewer UI.

## Acceptance policy

- Source commits and static inspection DO NOT establish a Kotlin build pass.
- An unverified source plate / morphotype remains an academic **HOLD** even
  if every Gradle and emulator job succeeds.
- No original pencil plate, organ hotspot, morphotype label, Tamil narration
  or leader line is academically accepted by these endpoint controls.
- U1.11 exact-commit pass remains independent of U1.12. Do not manufacture
  a past run ID or claim a CI PASS from missing check output.
- Keep all dependent PRs draft; no production merge, signed APK/AAB or store upload.
