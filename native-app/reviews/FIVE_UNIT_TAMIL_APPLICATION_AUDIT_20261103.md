# INVERTEBRATA: five-unit Tamil scientific and application audit

Date: 3 November 2026. Audited baseline: `5f37e01dc071d5c5c5a13f940dc058e6e33d71fb`.

## Scope and confidence

All **43 core chapter sources** were mapped to the five frozen A5 files. The systematic scan covered **5,586 English–Tamil string pairs**, **72 literal figure references**, and **125 additional main Kotlin files**. Scientific and editorial reading concentrated on high-risk passages: cilia/flagella, reproduction, ploidy, ootype, hydraulic support, historical classification, source credits and user-facing Tamil status text. This is a source audit, not independent certification of every paragraph or image. Extensions outside Units I–V are not included in the 43-chapter acceptance claim.

The companion [JSON audit](FIVE_UNIT_TAMIL_APPLICATION_AUDIT_20261103.json) gives a row for every core chapter and separates checks performed from checks not performed.

## Main findings and corrections

| Finding | Action |
|---|---|
| Sycon still used the inflected “பிறவி கருவுறுதலே” after the earlier exact-word fix | Corrected to “அயல் கருவுறுதலே”; aligns with TN XI Biology–Zoology, printed p.63 |
| The Fasciola term “ஊடைப்பு” obscured the meaning of ootype | Replaced with a descriptive Tamil explanation and retained the English anatomical term. This does not declare a newly invented standard textbook term. |
| Three literal “key” metaphors made the Tamil awkward | Rephrased as clear explanations. These were **not** three taxonomic-key errors. |
| Foot chapter used the unclear hydraulic-support phrase “நீர்நிலைக் கூடு” | Explained fluid-pressure support, retaining *hydrostatic skeleton* as an English anchor. |
| Gallery title still differed from the textbook water-vascular term | Standardised the external caption to “நீர்க்குழல் மண்டலம்”, as on TN XI printed p.29 |
| Removing JSON attribution labels did not remove hard-coded library-origin claims | Suppressed unsupported AI/schematic or historical-source claims for 15 supplied-image entries; preserved all image stems and the two user-confirmed parasite AI credits. |
| Historical Asterias gallery text was duplicated beside current replacement credits | Removed the obsolete extra block and the false unlabelled/species-specific title; current registry credit is shown once. |
| Entamoeba/Plasmodium gallery still attributed replacement PNGs to old Commons files | Removed stale author/licence/source links; SOURCE_PROVENANCE.txt explicitly excludes these replacement bytes. Historical records remain intact. |
| Library aliases did not fall back to the actual asset credit key | Added a shared credit-slot resolver so existing evidenced registry credits can be reused. |
| Five Tamil screens displayed raw English narration enums | Added Tamil display text for all eight phases. English status and stored enum identities are unchanged. |

## Frozen question bank

| Unit | Chapters | Questions per language | Answer points per language |
|---|---:|---:|---:|
| I | 7 | 14 | 70 |
| II | 9 | 18 | 90 |
| III | 7 | 14 | 70 |
| IV | 8 | 16 | 80 |
| V | 12 | 24 | 120 |
| **Total** | **43** | **86** | **430** |

All chapter IDs agree with their keys. Questions and answer arrays have the expected bilingual dimensions; every answer has five points. No blank question/answer fields or repeated English questions were found by these structural checks. The targeted cilia/flagella comparison found no further candidate mismatch in the frozen bank. These checks do not establish that all 430 translations are scientifically perfect.

**Three ploidy passages require author review:** Paramecium, conjugation question, answer points 2–4. “இரட்டையணு/ஒற்றையணு” does not clearly express two/one chromosome sets. The separate [review candidates](A5_TAMIL_PLOIDY_REVIEW_20261103.json) preserve n/2n explicitly. No frozen A5 bytes were edited. “மியாசிஸ்” is treated as a transliteration/style question; it is not automatically declared a different biological process solely from spelling.

## Images and attribution

All 72 literal textbook figure references resolve to PNG/WebP source assets in the audited Git tree. This is **not** a fresh APK decode, pixel-quality or device-rendering test. No images were changed or removed.

The JSON credit registry still has 46 entries against 39 provenance records; seven entries have no matching structured provenance row. Existing embedded or separately curated credits may still exist. There are 24 wording differences between registered and displayed credits, largely “image/illustration” wording, and 32 entries contain qualified or incomplete source wording. These are recorded for reconciliation, not treated as proof that every image has unlawful or unknown origin. The library fallback fix prevents removed labels being resurrected through a second UI path.

## Evidence and limits

The TN XI Biology–Zoology PDF was downloaded and inspected; its identity and printed/PDF page references are recorded in [the textbook-alignment audit](TAMIL_TEXTBOOK_ALIGNMENT_20261103.json). Printed p.29 was visually checked for the water-vascular term; the glossary's existing *Egestion* wording was retained rather than replaced merely for preference. The specialised ootype explanation is an editorial clarification anchored to the English source, not a claim of textbook approval.

Ploidy biology references: [NHGRI Diploid](https://www.genome.gov/genetics-glossary/Diploid), [NHGRI Haploid](https://www.genome.gov/genetics-glossary/haploid). The Tamil ploidy candidates also reference the government TN XII Botany publication linked in their review record; no uninspected page number is claimed.

Historical syllabus groupings are explicitly distinguished from modern evolutionary classification in the selected Protozoa, Aschelminthes and Platyhelminthes passages; their historical names alone are not treated as scientific errors.

No new APK, production signing, reviewer approval, physical-device acceptance or full current-commit emulator matrix is implied by this audit. Images, source-rights decisions, the Ascaris gate and all independent acceptance gates are unchanged. Embedded raster labels, specialised flagellar-pocket wording, remaining provenance reconciliation and audible device TTS remain outside the completed checks.

## Historical runtime baseline — superseded by verified follow-up

The full functional run [41472533403](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41472533403), at inherited baseline `acb13b6a`, completed **15 jobs passed / 2 jobs failed**. All four debug configurations, release English100/Tamil100, and all eight established regression groups passed. The failing XML artifacts were read directly:

| Release configuration | Failing method | Exact message |
|---|---|---|
| Tamil 200% portrait | `allFiveSimulatorsMoveRealDiagramPixelsAndPause` | `Android accessibility node not found: r17-inline-play-pause` |
| Tamil 200% landscape | `allFiveSimulatorsMoveRealDiagramPixelsAndPause` | `Android accessibility node not found: ciliary-inline-play-pause` |
| Tamil 200% landscape | `parasiteAndPrawnInteractivePagesAreReachable` | `Android accessibility node not found: u4-penaeus-appendage-name` |

Portrait reported two passed and one failed method; landscape one passed and two failed. These are accessibility control-lookup failures, not evidence by themselves that animation is broken or that an emulator flaked. The current audit does not claim to fix them. A current-commit emulator and device investigation is still needed before release acceptance.

## Platform source checks

The source configuration remains compile/target SDK 36, minimum SDK 24, version 1.9.0/code 19000. The source manifest disables backup, declares no `uses-permission` entries and exports the launcher MainActivity. No `android.webkit.WebView` import was found in the retrieved main Kotlin sources. These are source checks only: they do not substitute for inspection of the merged release manifest, a security assessment, real signing-key verification or physical-device tests.


### Explicit attribution contract

Fifteen library figures now carry an explicit PENDING attribution state, with no unsupported creator or licence claim published. RECORDED means metadata is present, not independently verified rights or academic approval. The previous blanket nonblank-credit tests were replaced with state-specific assertions: recorded credits must be bilingual; pending credits must remain unpublished. All image existence, PNG signature, size and dimension checks remain. Pila must remain academically unaccepted while its attribution is pending. This does not resolve the outstanding source/rights review.


## Verified functional follow-up

The inherited failures recorded above were investigated after this source audit. At tested commit `727e7f7891ecd2ce42f6aed31083e5a0b74d531a`, [run 41569136932](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41569136932) passed all four release configurations (English100, Tamil100, Tamil200 portrait/landscape) and both Tamil200 debug configurations: 18 methods passed, zero failures/errors/skips, verified from raw XML. Current JVM results: 252 passed; eight Tamil Python tests and five evidence-classification tests passed. The old runtime table is historical evidence, not the current cohort status. See [completed release verification](RELEASE_VERIFICATION_COMPLETED_20261103.md) for exact scope and retained release holds.
