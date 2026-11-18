# INVERTEBRATA — Unit III U3.5 readable safety notice and comparison evidence

**Implementation candidate only. Scholarly anatomy, Tamil speech and physical-device acceptance remain OPEN.**

## Verified baseline and visual evidence
- Parent branch `native/u34-unit3-independent-pixel-comparison-20261016`, exact SHA `360bee0b5482d2b1583c402982f01f729124b587`.
- [GitHub Actions #38891879005](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38891879005) passed Kotlin/JVM and API34 emulator; each font 1.0 and 2.0 executed 6 Unit III and 4 Unit II tests, zero failures. Downloaded original 94MB API34 evidence includes XML and screenshots.
- **Actual visual defect:** in `unit3-200-fasciola-compare-ready.png` the red safety notice ends at "learner hots…" and the header is cut to "Pixel re…". The original accessible educational meaning that student organ hotspots are NOT approved is partially hidden on the screen. Green UI instrumentation did not catch this semantic clipping.
- The existing 200% Tamil anatomy image itself is legible enough to inspect but **cannot be accepted** as a labelled, approved hand-pencil anatomical plate.

## Scoped U3.5 native Android changes
1. **Visible complete safety message** in compact English `UNAPPROVED • hotspots OFF ⓘ` and Tamil `ஒப்புதல் இல்லை • தொடுபுள்ளிகள் முடக்கம் ⓘ`. Both avoid ellipsis and provide a tappable **full scientific/provenance warning**, suitable for 200% text and TalkBack with an explicit descriptive content label. Title shortens to `DRAFT` / `வரைவு` instead of truncating meaningful content.
2. **Offline export** of the currently displayed U3.4 two-draft comparison using Android `CreateDocument(application/json)`. Exports exact source PNG SHA-256, plate identity, organ IDs, both original-pixel points, Euclidean distance, missing-structure flags, pixel tolerance and safe source attribution/rights fields. The picker stores a snapshot of the report before leaving the dialog. Writes locally, never network.
3. Both report and UI unconditionally state `DRAFT_COMPARISON_NOT_ANATOMY_APPROVAL`, `anatomical_accuracy_approved=false`, `independent_reviewers_verified=false`, and `student_image_hotspots_authorized=false`. Two identical files can originate from one person. No student production leader lines, touched organs or handwritten pencil claims are enabled.
4. No change to any six anatomy PNG bytes, U1/U2 lessons, A5 teaching questions, Android app identifiers, permissions, offline stage animations or non-debug release behaviour.

## Required exact-commit tests
- Kotlin debug+AndroidTest compile, release compile where supported, JVM cross-plate/source-hash comparisons and all source build/corpus invariants.
- API34 in airplane mode: **Unit III 6/6 and Unit II 4/4** per scale at real Android font 1.0 and 2.0. Tests must open/close the complete English/Tamil warning and parse an actual Kotlin-produced report using `org.json`, verifying missing credentials/approval never become true. Preserve 4 extra screenshots of **full** warning dialog at `fasciola-full-warning` and `ascaris-full-warning` for both font scales, plus older images.
- Visually inspect the native 200% screenshots for clipped Tamil words, alert-dialog scroll, system-bar overlap, and remaining image viewport of at least 230dp. A green CI badge without image inspection is not academic or usability signoff.
- Physical Android device must later open actual Files picker and save a comparison JSON, re-open exported bytes, test TalkBack, portrait/landscape, 2× and 4× coordinate mapping, process death, airplane mode, and installed English/Tamil offline speech.
- Qualified **two independent zoologists** must separately examine the true original artwork and figure subpanels; validate species, stage, organ IDs and endpoint anatomy; and record rights/license, source citations, independent identity signatures and Tamil translation review. Matching pixels alone are inadequate.
- Actual pencil master provenance is unverified. No Google Play / Indus production merging/signing is authorised.

**U3.5 can attain engineering acceptance only. Unit III scholarly anatomy acceptance remains HOLD.**
