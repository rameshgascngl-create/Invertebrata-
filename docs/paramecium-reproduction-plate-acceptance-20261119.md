# Paramecium reproduction plates — scientific acceptance register (19 November 2026)

**Status: ARTWORK NOT APPROVED.** This document defines acceptance requirements; it does not certify an illustration, create a PNG, or authorise an APK release.

## Scope and non-regression
- Preserve the five-unit Kotlin/Jetpack Compose application, its 43 validated A5 chapters, existing PNG viewer, zoom and Tamil font-scale behaviour.
- Do not substitute Canvas sketches, symbolic cartoons, reused anatomy crops, or the previously rejected eight-panel bilingual conjugation poster.
- The existing U57 simulator evidence (commit `d8a3f044a07a73878d067410df8fabb1a0212018`, workflow `39142782939`) establishes historical behaviour only, not acceptance of current HEAD.
- The project owner's acceptance of 52 existing mandatory images does **not** constitute independent anatomy, Tamil or rights sign-off.

## Required independent PNG plates
1. **Transverse binary fission**: mature vegetative Paramecium; micronuclear mitosis and macronuclear division; oral apparatus duplication; transverse furrow; anterior and posterior daughter cells. Sequence must distinguish stages rather than imply simultaneous instantaneous division. Depict ciliation, cell polarity and oral grooves consistently.
2. **Conjugation**: compatible conjugants in temporary contact; meiotic micronuclear divisions; degeneration of supernumerary haploid nuclei; exchange of migratory pronuclei; fusion with stationary pronuclei to form synkarya; separation of exconjugants; subsequent nuclear reorganisation and vegetative divisions. Do not show a fully reconstituted macronucleus before the appropriate post-conjugation reorganisation. Distinguish conjugation (genetic exchange) from reproduction by fission.
3. **Morphological anatomy**: independent high-resolution, scientifically checked depiction of ciliary rows, oral groove/cytostome, macronucleus, micronucleus, anterior/posterior contractile vacuoles and associated radial canals; no repeated fuzzy crop masquerading as distinct source art.

## Artwork and language QA
- Each PNG must be independently legible at 100% and 200% Tamil text scaling, without clipped labels or leader-line endpoints falling on the wrong organelle.
- English/Tamil pairs require a separate Tamil scientific-textbook editorial review; labels must be checked against the corresponding lesson, not translated mechanically.
- Record scientific reference, creator, date, generation/editing method, rights or AI-origin declaration, and SHA-256 for **each** individual PNG.
- Use an original high-resolution raster plate; do not upscale the low-resolution pencil photograph and call it a new anatomical source.
- Obtain separate sign-off from a zoology reviewer and a different Tamil reviewer; record discrepancies and their resolution.

## Acceptance sequence
1. Produce and inspect the individual PNGs against published ciliate cytology references and user-supplied reference images.
2. Confirm biological chronology and label endpoint accuracy before committing any art.
3. Integrate only accepted assets in an isolated commit; retain old assets and credit records until explicit replacement is verified.
4. Run exact-commit foundation/JVM tests and API 34 Tamil 100%/200% emulator tests; inspect actual screenshots and APK contents.
5. Keep independent scientific, language, provenance and physical-device production release holds in place.
