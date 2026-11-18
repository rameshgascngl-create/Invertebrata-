# Unit III — native full-screen raster inspection (U3.2)

**Engineering candidate — no zoological/physical-device or release acceptance.**

## Evidence-based defect
- Parent exact commit `a6d2351d0c1a431c95cb60fcaf747788e708796f`, Actions `38872886062`: all 6 Unit III and 4 Unit II Android API34 tests passed at system fonts 100% and 200%. The downloaded screenshots show the enlarged plate occupying only the bottom of the visible textbook page after scrolling. A passing bitmap-zoom test is not evidence of a usable reading viewport.
- No approved hand-pencil anatomical originals or mapped, independent source-pixel leaders exist for Unit III. Preserving disabled student organ hotspot interaction is required.

## Scoped implementation
- `UnitIIIPlateFullscreenViewer.kt`: **Jetpack Compose** fullscreen Dialog with an actual decoded `ImageBitmap`, not re-generated SVG/Canvas anatomy, WebView or a fake replacement.
- Fit-to-viewport at 1× followed by centre-pivot 2× and 4× with pan, bounded to the original display viewport; plus Reset and Close.
- At 200% Tamil font, the title is ellipsized instead of pushing the actual bitmap below the fold, controls remain at the bottom with ≥48dp touch size and the image occupies the remaining flexible height (`weight(1f)`).
- Prominent evidence label: **original/source plate remains scientifically UNAPPROVED**. No source labels, bitmap pixels, A5 JSON, life-cycle stages, academic lesson or speech content altered.
- No direct click-to-organ handlers in fullscreen; `UnitIIIPlateHotspots.geometryApproved()` remains false for all six sourced candidates in the standard gallery.
- Existing inline reader and accessible stage/organ buttons remain available.

## Exact-commit acceptance (must check real evidence)
1. Compile debug Kotlin, Android instrumentation and all JVM tests on exact HEAD. Unit II regression 4/4 and Unit III 6/6 at both 100% and 200% fonts, in airplane mode, unchanged.
2. Verify fullscreen viewer with standard Fasciola anatomy and Tamil Ascaris life cycle, opening WITHOUT zooming the article page, showing a bitmap viewport of ≥350dp vertically before scrolling, correct zoom 1→2→4× and Reset/Close.
3. CI must produce `unit3-{100,200}-{fasciola,ascaris}-fullscreen-{1x,4x}.png` in addition to all existing screenshots. Visually inspect each: no image lost behind long lesson text, no hidden toolbar, no Tamil clipping, anatomy not falsely identified as a hand-made scan.
4. Physical Android device: pinch/pan centre-pivot behaviour, landscape and portrait, back to chapter, offline/200% Tamil, TalkBack, transient process death and memory pressure, plus live offline audible speech in the *separate* organ explanation controls.
5. Zoology, attribution/rights, Tamil editor and physical Android sign-off remain **PENDING**. Do not activate any unverified hotspot or claim release readiness.

## Dependency safety
This branch is based on the exact Unit III passed candidate `a6d2351d...` and does not merge unrelated Unit I U1.17 work. Unit I and Unit II continue separate academic release holds. Production Google Play and Indus builds are NOT authorised.
