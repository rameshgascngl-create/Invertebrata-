# Unit I Paramecium — inspected APK evidence and scientific figure gate
Date: 2026-11-14
Baseline APK: INVERTEBRATA-N23E3-NATIVE-DEBUG-QA.apk (commit ce3421ea). Scope is **APK asset inspection**, not current source HEAD or rendered emulator output.

## Verified A5 English lesson-question content
`assets/a5/U1_A5_VALIDATED.json` contains `u1-paramecium` with a question “Outline conjugation in Paramecium.” Its answer correctly describes oral-surface pairing, micronuclear meiosis, loss of three haploid products (usual simplified account), mitotic formation of stationary and migratory pronuclei, exchange, fusion to synkaryon, separation and later nuclear reorganisation. **Do not call conjugation absent.** It needs a stage-correct illustration and cross-check against the complete Tamil answer.
The same chapter contains an osmoregulation/feeding answer discussing the oral groove, food vacuoles, cytoproct and two contractile-vacuole complexes. `u1-protozoa` correctly states that Protozoa is a traditional non-monophyletic teaching assemblage and mentions asexual fission.

## Visually inspected PNGs in baseline APK
| Path | Pixel dimensions | Observed content | Acceptance decision |
|---|---:|---|---|
| `assets/paramecium/fission/F01_vegetative_PROVISIONAL.png` | 1536×1024 | Anatomically detailed bilingual single-cell plate; macronucleus, micronucleus, cilia, vacuoles and oral structures indicated | HOLD — visually readable at full resolution, but morphology, endpoints and Tamil terminology not independently signed off |
| `assets/paramecium/fission/F02_micronuclear_mitosis_PROVISIONAL.png` | 1536×1024 | Single-cell stage with nuclear annotations | HOLD — stage mechanism and timing require specialist check |
| `assets/paramecium/fission/F03_chromosome_segregation_PROVISIONAL.png` | 1536×1024 | Elongated cell with nuclear/chromosome depiction | HOLD — verify depicted micronuclear segregation versus macronuclear division and caption accuracy |
| `assets/unit1/concepts/paramecium_binary_fission.png` | 2699×694 | Small sequential oval-cell symbols with simple coloured nuclei, minimal morphological detail | REJECT as final anatomical plate; retain only if already used as an explicitly schematic concept diagram |
| `assets/unit1/concepts/paramecium_conjugation.png` | 2529×1204 | Repeated paired oval symbols with small coloured nuclei and little anatomical detail | REJECT as final anatomical plate; not sufficient to establish accurate conjugation chronology |
| `assets/unit1/concepts/paramecium_ciliary_locomotion.png` | 460×372 | Enlarged stylised cilia and membrane surface | Not a whole-organism anatomical reference; retain pending usage and label check |

The three provisional plates are NOT the same thing as approved dedicated binary-fission stage illustrations. Their file presence is not proof of runtime use. The previous eight-panel poster remains excluded from approval because its sequence is scientifically disputed.

## Precise outstanding tasks
1. Fetch the current Kotlin reader/lesson source and Tamil A5 answer; compare each claim against an authoritative ciliate reproduction reference. Do not silently overwrite verified existing text.
2. Independently check fission sequence: micronuclear mitosis, macronuclear division without conventional mitosis, oral apparatus duplication/reorganisation and transverse cytokinesis. Label each stage and verify orientation and leader endpoints.
3. Independently check conjugation sequence: meiotic micronuclear divisions, stationary/migratory pronuclei, reciprocal exchange, synkaryon, exconjugant nuclear reorganisation and post-conjugational divisions. Avoid presenting newly formed macronuclei too early.
4. Replace only after high-resolution **individual** PNG candidates pass separate Zoology, Tamil, rights and runtime screenshot review.
5. Run exact-commit CI and inspect 100% and 200% Tamil emulator captures before generating a new QA APK. Keep physical-device and production release holds.

No existing illustration is deleted, reclassified as approved, or integrated by this audit.
