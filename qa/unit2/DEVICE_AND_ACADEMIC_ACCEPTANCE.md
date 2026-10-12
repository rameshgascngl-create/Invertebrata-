# INVERTEBRATA — Unit II academic-artwork and physical-device acceptance

**Scope:** Sycon / Obelia in the native Kotlin app; source baseline `d009525a3a3c8e86a91ac506960358911c31fc76`. The full Unit III curriculum and other type studies are out of scope. **All plates remain provisional.**

## Evidence and the two independent lanes

1. **Engineering/device-QA lane:** Kotlin/Android CI PASS, fixed original APK hash, all PNG screenshots, touch/zoom/TTS/Back/rotation/offline checks on an actual phone. This proves interaction and delivery, **not** zoological accuracy.
2. **Academic/artwork lane:** independently sourced *genuine photographed/scanned graphite originals* where requested; evidence of authorship/rights; professor-level biology review of source-pixel anatomy, printed leader endpoints, English/Tamil terminology, and touch-region registration.

**No derived/digitally simulated graphite plate may be claimed as a photographed hand drawing.** Engineering CI is not a substitute for either lane.

## Exact debug candidate for the device lane

- GitHub Actions: https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38248065451
- Exact Android-tested commit: `d009525a3a3c8e86a91ac506960358911c31fc76`
- APK SHA-256: `780093acfcb5edb88fd6cfaebcce8e530d79466f5daa8435bb9d31a8115a7fc5`
- Application ID: `com.gasczoology.invertebratelab`; debug-signed, not Play/Indus signed.
- If a differently signed copy is installed, **do not uninstall without first safeguarding its data**. A signing mismatch is an expected install blocker, not a failed academic test.

## Actual-phone test session (Android)

Record phone model, Android version, font scale, orientation, APK SHA-256, tester, date and original screen recording/screenshot IDs. Offline means airplane mode with both Wi-Fi and mobile data disabled. Do not substitute emulator screenshots for a physical-phone result.

| ID | Real-device action | Evidence / pass criterion |
| --- | --- | --- |
| D01 | Fresh launch offline; Unit II > Sycon | No remote dependency, crash, blank atlas or schematic substitution |
| D02 | Inspect static Sycon pencil-style raster | Entire figure, printed English labels and arrows visible; separate from interactive atlas |
| D03 | Direct tap prosopyle / apopyle / spongocoel in *Interactive Anatomy* | Only correct structure is selected; hotspot corresponds to printed source-pixel aperture |
| D04 | Pinch to 2× and 4×, pan, reset | Image and touch layer move together, no clipping or false nearby hit |
| D05 | Open Obelia static colony and life-history images | Figures legible and unoccluded, not replaced by vector drawings |
| D06 | Obelia feeding hydranth, gonangium, free medusa, planula | Correct stage selected and anatomically described |
| D07 | Switch English↔Tamil and set **200% font** | No clipped Tamil headings, labels, buttons or reset target |
| D08 | Rotate portrait↔landscape | No lost stage, broken touch alignment or vanishing navigation |
| D09 | Background/foreground, Back, return to same chapter | State and navigation retained as specified |
| D10 | Offline touch-to-speak in English and installed Tamil voice | Correct selected organ/section spoken; no invented online TTS success |
| D11 | Screen reader / TalkBack and minimum touch targets | Usable spoken labels and controls without relying on inaccessible bitmap hotspots |
| D12 | Repeat D03/D06 offline after screen rotation | Hotspot registration and narration remain correct |

**Device-QA PASS** requires raw date-stamped evidence for every critical D01–D12 item, no undisclosed crashes, a tested APK SHA, and a recorded tester sign-off. Otherwise DEVICE_QA=PENDING or FAIL.

## Independent source-pixel biological review

- **Sycon:** Dermal ostia → incurrent canal → **prosopyle** → choanocyte-lined radial canal → **apopyle** → pinacocyte-lined spongocoel → osculum. The drawing must actually depict these separate openings, not merely print correct labels. The **radial-canal collar/flagellar apparatus must not appear to filter inside the spongocoel**. Inspect printed arrows, blind outer canal tips, choanocyte placement, spicules and section orientation at image-pixel magnification.
- **Obelia colony:** Feeding hydranth (hypostome/mouth, tentacles, hydrotheca) separate from gonangium with blastostyle and medusa buds enclosed by the gonotheca; coenosarc and colony structure labelled appropriately. Verify every leader line at the pixels it points to.
- **Obelia life cycle:** Current **730 × 300** three-stage asset is not a complete stand-alone life-cycle plate. Replace it with an independently reviewed, legible full sequence: colony → medusa budding and release → free medusa → gametes/fertilisation → zygote/embryo → planula → settlement → new colony. Do not misrepresent direct polyp-to-medusa conversion.
- **Tamil and authenticity:** Review all printed bilingual terms separately. Record original scan/photograph, creator/license or permissions, correction master, and approval dated by reviewer. Digitally rendered source art remains labelled as digital.

The canonical exact image-byte evidence and pending reviewer flags live in `qa/unit2/artwork_review_manifest.json`. **Changing a raster, adding a PNG that shadows a WebP, or moving hotspots requires a new source hash, new biological review and new exact-commit Android screenshot evidence.**

## Promotion sequence (non-bypassable)

`CANDIDATE SOURCE → IMAGE INTEGRITY → ANDROID EMULATOR → PHYSICAL DEVICE QA → INDEPENDENT BIOLOGY/TAMIL/SOURCE RIGHTS REVIEW → ACADEMIC PROMOTION → SIGNED PRODUCTION`

Academic promotion must run `python ci/unit2_artwork_acceptance.py --mode release` against the corrected exact-source branch and obtain PASS. Until a documented hand-scanned original (if required), anatomy, Tamil, rights, hotspots and actual-phone evidence pass, the gate intentionally fails. **Do not merge PR #11 or submit to an app store on a green engineering run alone.**
