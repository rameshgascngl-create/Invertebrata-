# Unit I U1.7 — Native flagellate type-study acceptance gate

**Candidate:** `native/u17-flagellate-bilingual-type-studies-20261015` · [draft PR #19](https://github.com/rameshgascngl-create/Invertebrata-/pull/19)

## Academic content and layout

- The parasite chapter must present original instructional prose **before** A5 assessment, not a short series of questions and answer-reveal buttons.
- Trypanosoma brucei includes: bloodstream trypomastigote morphology; mammalian replication and clinical significance; tsetse vector differentiation; comparison with T. cruzi.
- Leishmania spp. includes: intracellular amastigote morphology; host-cell replication; sand-fly promastigote development; taxonomy and diagnostic limits.
- Every one of the eight subsections is a separate, permanently readable native Compose lesson entry. Do not hide prose behind quiz-like stage selectors.
- Preserve English and Tamil as full versions; no automatic translation is accepted as peer-reviewed copy.
- Unit I Paramecium and Entamoeba modules, and all validated A5 JSON, remain outside U1.7's edit scope.

## Figure integrity

- Each type study presents its **own** attributed image from the already sourced offline `unit1/references` package.
- Figure expands directly from its lesson into the existing full-screen pan-and-pinch reader. At 200% font scale: image viewport must remain visible and the close/zoom controls must be operable.
- These are **source-derived reference artworks**, not scanned pencil masters, accepted anatomical maps, diagnostic micrographs or animated lifecycle plates. Missing PNG is an explicit error; never silently draw a substitute.
- Source image stage, morphology, species limits and licence/credits must remain clear.
- Dedicated original pencil sketches, leader-line calibration and touch hotspots require separate biology review before promotion.

## Evidence required to accept engineering candidate

1. Confirm actual HEAD SHA in PR #19.
2. Run the `native-unit1-protozoa-references.yml` workflow on that same commit (push or manual dispatch).
3. Inspect both jobs: source manifest/asset generation/JVM compilation, then API34 offline UI at English 100% and Tamil 200% system font. Job completion alone is not a complete content inspection.
4. Inspect exact SHA screenshot artifacts including `u12-200-tamil-trypanosoma-reading.png`, `u12-200-tamil-leishmania-reading.png`, all existing reference-gallery screenshots, and fullscreen diagram close/zoom.
5. Reject truncation, missing figures, sections that cannot scroll to the bottom, or spurious scientific labels.
6. Check real Android phone navigation, rapid back/rotate, English–Tamil selection, offline startup, 200% fonts, app memory, TalkBack and image scaling.
7. Record English/Tamil zoologist signoff and artwork-source rights separately from build success.

## Acceptance record

- **Source-level integration:** implemented; source-routing / lazy items / stage text / no-WebView inspection completed.
- **Native Kotlin + emulator CI:** **UNVERIFIED** unless linked exact-commit run logs and screenshots are inspected.
- **Full scientific and Tamil acceptance:** **PENDING**.
- **Original pencil anatomical drawings + leader/hotspot review:** **PENDING**.
- **Spoken organ-specific bilingual descriptions:** **PENDING**.
- **Physical-device QA:** **PENDING**.
- **Final unit acceptance, merge to release, APK/AAB signing:** **HOLD**.

The next phase must not reclassify these reference figures as source-verified pencil images. The application remains **native Kotlin/Compose**, not HTML/WebView.

### Reference biology
- https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html
- https://www.cdc.gov/dpdx/trypanosomiasisAmerican/
- https://www.cdc.gov/dpdx/leishmaniasis/
