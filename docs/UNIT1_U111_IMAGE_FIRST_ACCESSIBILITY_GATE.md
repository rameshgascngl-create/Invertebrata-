# U1.11 — Image-first native Android anatomy review (QA candidate)

## Why this stage exists
U1.10 exact-commit Actions run `38767065241` succeeded: 4/4 API34
instrumentation cases at **100% and 200%** font scale. Its screenshot
`u12-200-u110-trypanosoma-source-review.png` showed the long enlarged
explanation and native offline voice controls displacing the actual raster
below the first physical screen. That is a visual accessibility defect
even though the code and tests passed.

## Implemented, Android only
- The real decoded attributed `unit1/references/trypanosoma.png`
  raster appears immediately after short title, original resolution, and
  horizontal structure selectors. Detailed biology, stage disclaimer
  and optional offline TTS follow **below** the bitmap in a scrollable
  native Compose reviewer.
- The primary image retains its real `ContentScale.Fit` bitmap and the
  existing inverse pixel calibration, pan, pinch zoom and Canvas markers.
  There is **no** repainted vector anatomy and no unapproved leader-line
  endpoint; the original packaged source bytes remain identical.
- The raster receives a native semantics tag
  `u111-source-raster-visible-trypanosoma`. Android instrumentation
  opens the review modal and asserts that the **actual image is displayed
  without requiring a scroll**, its bitmap node has at least 200dp height,
  and it records `u12-100/200-u111-trypanosoma-image-first.png`.
- Draft markers and source-image SHA-256 checks remain intact. Dialog
  remains **debug-only** and never turns exported draft pixel positions
  into approved student anatomical hotspots.

## Same-commit acceptance
1. Candidate commit is precisely identified. Compare to U1.10: academic A5
   corpus, program identifiers, scientific explanations, original raster
   masters, and Android non-network rule all unchanged.
2. Compile debug and release, compile Android test and run all JVM tests.
3. API34 offline UI suite: 4/4 cases at 100% and 200%. Inspect the
   separate XML outputs for zero failures.
4. Open two new images from the exact workflow evidence. Ensure the
   source raster is visible **without force-scrolling**, is not clipped
   offscreen at 200%, and remains marked as an attributed **source
   reference, not a pencil anatomical master**.
5. Physical handset: check 200% Tamil, TalkBack, images and choice chips,
   finger accuracy of draft pixel coordinates across zoom/pan/rotation,
   offline voice absent/present, file export, and lifecycle UI gestures.

## Academic hold
The present vector-derived attributed reference is **not** a real scanned
hand-pencil master; the artwork resembles a stylized reference, not a
final zoological pencil plate. Student-facing hotspots, organ-touch TTS,
anatomical labels, and release signing remain blocked pending an original
source-licensed pencil master and independent zoologist/Tamil review.
Leishmania amastigote and promastigote must remain distinct images.
This is **U1.11 engineering QA only**, not Unit I academic acceptance.
