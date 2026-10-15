# Unit I — Paramecium PNG pencil atlas candidate (academic HOLD)

## Architecture now active
Existing Unit I / Paramecium chapter navigation, state persistence and all
43 chapters of the A5 academic corpus were deliberately preserved.
The four visible teaching anatomy modes (external, oral, vacuole, internal)
route only through `ParameciumRasterAtlas` and require offline assets:
`unit1/paramecium_external.png`, `paramecium_oral.png`,
`paramecium_vacuole.png`, and `paramecium_internal.png`.

The source illustration is an actual photographed hand-drawn pencil diagram,
not generated Compose geometry, SVG, or a rasterised copy of SVG. Compose is
used exclusively for bitmap display, 1–4× zoom/pan, organ-name controls and
requesting bilingual offline TTS; there are **no claimed source-verified
image-space hotspots**. A missing PNG displays an error instead of an
anatomical Canvas fallback. Old simulator Canvas code is untouched, but the
four-mode anatomy atlas no longer invokes it.

## Image licence and limitations
Source: Rijan Bhandari, *Paramecium caudatum-hand labeled.jpg* (24 June
2014), Wikimedia Commons, **CC BY-SA 3.0**:
https://commons.wikimedia.org/wiki/File:Paramecium_caudatum-hand_labeled.jpg
Source original SHA-1:
`0d9d108ccc8310ed82de617d0cf71ca64ce0aad7`
Original: 1536×2048 JPEG. Build process performs only grayscale,
contrast adjustment, cropping, and attribution/layout around unredrawn
source pixels. Four **regional views from the same source** are not
represented as four independently drawn scientific anatomical masters.
Derivatives remain CC BY-SA 3.0, and source attribution is embedded and
visible in the app.

**Scientific caveats:** This is a rough student-authored pencil drawing,
not a vetted textbook anatomical plate. Handwritten labels and
potentially inaccurate organ locations need a named zoologist to review
each target and Tamil scientific terms; do not imply peer review.
In particular, confirm the *P. caudatum* orientation, oral apparatus,
cytoproct, food vs contractile vacuoles, trichocysts, nuclei and
both radial canal complexes. All four plates remain DRAFT_UNVERIFIED.

## Required acceptance
- Same SHA + same GitHub run: decode all 4 PNG assets in Android, no
  old `n23b-external-canvas` in active hierarchy, A5 route preserved,
  English/Tamil 100% and 200% screenshots, no overlap.
- Source-specific, signed anatomical leader endpoint ledger before
  allowing direct taps (previous Canvas coordinates MUST NOT transfer).
- Tamil language editorial check and offline audible TTS physical-phone
  test across portrait/landscape/rotation and 1–4× zoom.
- Further original pencil masters for Entamoeba, Trypanosoma,
  Leishmania, Plasmodium, and specialized physiology figures.

**No release, no academic approval, no production merge** until all required
gates pass. App download on debug is for engineering QA only.
