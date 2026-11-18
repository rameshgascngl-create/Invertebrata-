# Unit II — Android handset QA and independent academic acceptance

**HARD HOLD: academic approval and physical-device evidence are NOT yet obtained.**
This is a collection protocol, not a pre-filled certificate. **No release,
merge to production, or Unit III gate advancement** without verifiable proof.

## Source and artifact identity

- Source candidate: `native/u2-scanned-plate-candidate-20261011`.
- Candidate APK must be downloaded from the **same GitHub Actions run/commit**
  being reviewed: `.github/workflows/native-unit2-image-candidate.yml`.
- Record the actual commit SHA, workflow run ID, Android debug APK SHA-256,
  package name, Android model, OS version, navigation mode, device density
  and selected TTS engines and offline language availability.
- Candidate raster files packaged at build time:
  `unit2/sycon_plate.webp`, `unit2/obelia_colony.webp`,
  `unit2/obelia_lifecycle.png`, `unit2/obelia_settlement.png`.
- Historic Obelia settlement source: Wolcott (1933), *Animal Biology*, Fig. 66,
  Internet Archive Book Images / Flickr Public Domain:
  https://www.flickr.com/photos/internetarchivebookimages/18197697381
- **Do not** label provisional synthesized Sycon art as authenticated pencil
  scanning. Provenance and source fidelity are independent acceptance items.

## A. Actual phone — required evidence (NOT emulator)

For all rows, record **PASS/FAIL**, screenshot or uninterrupted video filename,
and reviewer initials. Use an installable, exact-commit candidate QA APK.

| Test | English 100% | Tamil 100% | English 200% | Tamil 200% |
|---|---|---|---|---|
| Portrait: title entirely below system status bar | PENDING | PENDING | PENDING | PENDING |
| Landscape: title, back, stage navigation unclipped | PENDING | PENDING | PENDING | PENDING |
| Portrait: two-finger pinch to 2x/4x and pan; Reset zoom | PENDING | PENDING | PENDING | PENDING |
| Landscape: two-finger pinch to 2x/4x and pan; Reset zoom | PENDING | PENDING | PENDING | PENDING |
| Sycon: prosopyle / apopyle / radial canal correct touch targets | PENDING | PENDING | PENDING | PENDING |
| Obelia: hydranth / gonangium direct image taps | PENDING | PENDING | PENDING | PENDING |
| Obelia: medusa → planula → settlement plate switching | PENDING | PENDING | PENDING | PENDING |
| Settlement plate: E attachment and F primary polyp targets | PENDING | PENDING | PENDING | PENDING |
| Spoken English organ explanation, airplane mode | PENDING | PENDING | PENDING | PENDING |
| Spoken Tamil organ explanation, airplane mode | PENDING | PENDING | PENDING | PENDING |
| Rotate while zoomed; no crash, blank image, misplaced hotspot | PENDING | PENDING | PENDING | PENDING |

### On-device procedure
1. Install the **exact run's debug APK**. Capture device model, Android OS,
   screen resolution/density, navigation mode and installed offline TTS voices.
2. In Settings > Display, select the requested Android **font size** (100%,
   then maximum/200% where supported); confirm actual device percentage
   using device settings / `adb shell settings get system font_scale`.
3. Open Unit II → Sycon, then Obelia. Capture an unscrolled top-of-page screen
   in each language and orientation showing the actual title and system bar.
4. Touch anatomy *on the bitmap*, rather than stage buttons alone. Note the
   named stage readout and whether selection ring stays on its source pixels
   after zoom/rotation. Use separate captures at 1x, 2x and 4x.
5. Test stage six of Obelia independently: E = attached transforming planula;
   F = emerging primary polyp/hydranth and stolon, not an adult medusa.
6. Disable **Wi-Fi and mobile data**, enable airplane mode, then select an
   organ. Listening must confirm audible local playback; a successful TTS
   API callback without sound is not a pass. Distinguish unsupported local
   Tamil voice (record actual limitation) from correct offline pronunciation.
7. Rotate with a selected hotspot, zoomed plate and a spoken explanation in
   progress. Return to portrait; ensure no app closure/clipped controls.
8. Save screenshots and a 15–30 second real-device screen recording **with
   audio** where permitted. Protect personally identifying material.

### Acceptance rule
Do not extrapolate emulator PASS to an actual phone. Failures must be fixed at
source, followed by an exact-commit rerun and fresh handset evidence. **If
Tamil offline voice data are absent, Tamil spoken QA remains NOT VERIFIED.**

## B. Zoology academic review — independent, still PENDING

Review every labelled leader line at magnification, including:
- **Sycon:** dermal ostia → incurrent canals → prosopyles → radial canals
  lined by choanocytes → apopyles → pinacocyte-lined spongocoel → osculum.
  Confirm radial canal blind tips, apopyle openings, flagellar orientation,
  spicule morphology and hotspot endpoints. The Sycon image is still a
  provisional drawing and is a release blocker until the underlying plate
  morphology passes expert review.
- **Obelia colony:** hydranth/hydrotheca vs. gonangium/gonotheca, blastostyle,
  correctly shown medusa buds, hydrocaulus and hydrorhiza.
- **Obelia life history:** medusa buds, released medusae, gametes,
  fertilisation, cleavage and blastula, ciliated planula, settlement (E),
  primary polyp (F), hydrorhiza/stolon and developing colony (A).
  Do not depict the planula as a miniature adult medusa.
- **Bilingual labels:** morphological terminology, readable Tamil glyphs,
  English spelling, font-scale and hotspot/label identity.
- **Provenance and rights:** credit each historical scan accurately, preserve
  authentic source-line structure, and document local reuse review.

## C. Sign-off (blank intentionally; no autonomous approval)

| Discipline | Named human reviewer | Decision | Date | Evidence |
|---|---|---|---|---|
| Zoological anatomy | **PENDING** | PENDING | — | — |
| English/Tamil academic editing | **PENDING** | PENDING | — | — |
| Image provenance/reuse | **PENDING** | PENDING | — | — |
| Physical Android QA | **PENDING** | PENDING | — | — |
| Release authorization | **PENDING** | HOLD | — | — |

The independent expert must post an explicit, source-commit-specific approval
with evidence. GitHub build status, chatbot statements and raster dimensions
are **not** academic approval.
