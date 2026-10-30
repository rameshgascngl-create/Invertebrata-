# Unit I — chapter-by-chapter anatomical PNG requirements

Source: `NativeCurriculumManifest.a5ChapterIdsByUnit[1]` and the corresponding seven chapter manuscripts in `FiveUnitRestorationLessons.kt`. This is an **asset placement and academic review inventory**, not an assertion that unverified drawings are accepted.

## Mandatory teaching figure placement

| Chapter | Required distinct PNG figures | Current state |
|---|---|---|
| u1-intro — Introduction | Comparison of ciliate, amoeboid, flagellate and apicomplexan cell organization | Missing accepted comparison PNG |
| u1-protozoa — classification | Traditional class comparison (Sarcodina, Mastigophora, Ciliata, Sporozoa), clearly marked historical; Amoeba morphology | Missing distinct Amoeba PNG and classification PNG |
| u1-paramecium — type study | External cilia, oral apparatus, internal morphology, contractile-vacuole complex, ciliary locomotion, transverse binary fission, conjugation | Existing `unit1/paramecium_{external,oral,internal,vacuole}.png` and three `type_studies/paramecium/*.png`; locomotion and reproduction source-based PNGs not confirmed |
| u1-proto-parasites — parasitic protozoa | *Entamoeba histolytica* trophozoite/cyst and life cycle; *Trypanosoma brucei* bloodstream trypomastigote and tsetse transmission; *Leishmania* amastigote and promastigote; *Plasmodium* blood stages and mosquito/human life cycle | Four distinct **reference** PNGs at `unit1/references/{entamoeba,trypanosoma,leishmania,plasmodium}.png`; stage-specific anatomical pencil masters remain unapproved/missing |
| u1-nutrition — feeding | Amoeba phagocytosis; Paramecium oral feeding/food vacuole/egestion | Separate feeding-sequence PNGs pending |
| u1-host-parasite — relationships | *Entamoeba* transmission and *Plasmodium* human–mosquito stages | Reference PNGs exist; approved stage-specific teaching plates pending |
| u1-locomotion — motility | Amoeboid pseudopod formation; ciliary beat in *Paramecium*; flagellar propulsion and undulating membrane in *Trypanosoma* | Separate locomotion PNGs pending |

## Production requirements

1. Use PNG anatomical illustrations in the native pages. Do not display Compose Canvas/SVG as anatomical substitutes when a required PNG is missing.
2. Do **not** confuse *Amoeba* (free-living amoeboid teaching specimen) with *Entamoeba histolytica* (intestinal parasite). They need different figures and captions.
3. Existing sourced PNGs must be reused, not multiplied across the same gallery. Where the same figure supports another chapter, reuse its asset reference rather than copying bytes.
4. Preserve the source image, accurate bilingual caption, attribution, rights, review state, pinch zoom and offline access. Do not infer hotspots from unverified pixels.
5. The four acquired parasitic-protozoan illustrations are **references**, not certified original pencil plates; source, morphological and Tamil review remain open.
6. For *Paramecium*, verify pellicle/cilia, oral groove → cytostome/cytopharynx, macronucleus/micronucleus, food vacuoles, anterior/posterior contractile vacuoles with radiating canals, and cytoproct. Avoid a composite plate with misplaced leader lines.
7. For *Trypanosoma*, distinguish nucleus, posterior kinetoplast, undulating membrane and anterior free flagellum; for *Leishmania*, distinguish intracellular amastigote from flagellated promastigote; for *Plasmodium*, identify species and stage explicitly.
8. Accept a diagram only after anatomical inspection, English/Tamil label review, source-rights verification, and native Android screenshots at 100% and 200% font scale.

## Immediate priority

Obtain or recover an independently sourced **Amoeba PNG**; inspect it before integration. Then replace remaining anatomy schematics in chapter order, keeping existing `ParameciumRasterAtlas` PNGs. Missing images are blocking academic completion, not permission to fabricate or mark accepted.
