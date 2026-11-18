# Unit III — archive-specific leader, accessibility, and speech gate

Candidate branch: `native/u3-anatomy-atlas-candidate-20261013`. Status: **ACADEMIC HOLD**, even if CI succeeds.

## Source-image calibration decision

Six source-backed PNGs replaced programmatic anatomy drawings. These figures
contain original multi-panel annotations and, in the Ascaris life-cycle slot,
a **partial eggs/adults historical fragment**, not a full cycle.

The coordinates of the rejected 960×1280 *procedural* artwork were removed
from `UnitIIIPlateHotspots.kt`. They are now `Float.NaN`, and
`geometryApproved(stem)` returns false for all six sources.
The gallery must show no leader line or direct image selection for any
unreviewed geometry. All available organ-name buttons still display their
bilingual academic explanation and request the matching TTS script.

To unlock a hotspot on a future exact PNG SHA-256:
1. The reviewer identifies the correct original **organ and subfigure**,
   including the original page letter and its magnification.
2. Record the exact raster SHA-256, image width/height, source pixel (x,y)
   *inside the actual bitmap*, the labeled organ and the leader terminus.
3. Visually inspect each endpoint at 1×, 2×, and 4× magnification,
   independently in English and Tamil.
4. A qualified Zoologist approves the label-to-organ mapping. Translate
   only after the anatomical target is confirmed.
5. Add a **new image-hash-bound coordinate manifest** and enable direct taps
   only for the approved points. Do **not** transplant old percentages or
   normalize to the historical composite's inset positions by guesswork.

**The current state cannot honestly claim leader-line verification PASS.**

## Android 100/200 percent accessibility

The organ controls are now fully realized vertical 48dp-or-greater buttons;
they cannot disappear due to `LazyRow` off-screen recycling. The UI test
selects Fasciola ventral sucker (English) and Ascaris vulva (Tamil), requires
the selected name and explanation, and checks the most recently **requested**
TTS script in the same language. The requested script is a testable Android
TTS dispatch input, **not proof of audible pronunciation**. The JVM contract
also checks every organ ID in all six plates.

## True physical-device acceptance — NOT RUN

On the actual debug APK/commit, capture model, Android version, font scale,
navigation mode, audio engine and exact APK SHA-256. In **both portrait and
landscape**, repeat:

- 100% and 200% English/Tamil: scrolling, taps, high-contrast legibility,
  TalkBack labels, Back navigation, long article access, title/system bar
  clearance and small-text clipping.
- Select every available organ-name control; compare the narration request,
  visible explanation and, with local voice data installed, **audible**
  pronunciation for both languages in airplane mode.
- Verify actual bitmap zoom/pan if offered. Lack of zoom controls must be
  recorded as a functional defect, not a PASS.
- Rotate while scrolled and after organ selection. Resume, background,
  relaunch, and repeat under memory pressure. No stale organ routing, crash,
  lost plate bitmap or spontaneous app closure.
- Before enabling direct image taps, verify all the source-pixel leader
  endpoints using human-reviewed artwork. Currently these are disabled.

## Decision

Native Android automation can demonstrate layout and text-request behavior,
not physical audio or a human zoological approval. Keep academic release,
merging to main, and physical-device QA as **PENDING**.
