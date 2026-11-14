# Evidence audit 01 — five-unit A5 question-answer assets
Date: 2026-11-14
Evidence source: locally inspected `INVERTEBRATA-N23E3-NATIVE-DEBUG-QA.apk` from commit ce3421ea; `assets/a5/U1_A5_VALIDATED.json` through `U5_A5_VALIDATED.json`.
Scope: **A5 Q&A content only**, not full native reader text, other assets, simulator code, or current branch HEAD. This is a baseline APK, not proof of source-level coverage or current CI acceptance.

## Inventory confirmed from APK
- Unit I: 7 A5 chapter IDs: u1-intro, u1-protozoa, u1-paramecium, u1-proto-parasites, u1-nutrition, u1-host-parasite, u1-locomotion.
- Unit II: 9: u2-porifera, u2-sycon, u2-canals, u2-reproduction, u2-cnidaria, u2-obelia, u2-corals, u2-coral-economics, u2-polymorphism.
- Unit III: 7: u3-platy, u3-fasciola, u3-platy-adapt, u3-aschel, u3-ascaris, u3-nematode-parasites, u3-nematode-adapt.
- Unit IV: 8: u4-annelida, u4-nereis, u4-metamerism, u4-modes-life, u4-arthropoda, u4-penaeus, u4-peripatus, u4-crustacean-larvae.
- Unit V: 12: u5-mollusca, u5-pila, u5-torsion, u5-pearl, u5-echinodermata, u5-asterias, u5-echino-larvae, u5-echino-affinities, u5-fossils, u5-foot, u5-wvs, u5-cephalopods.
Total = **43** distinct A5 chapter IDs.

## Evidence-based adjustments to the coverage register
| Target | Verified evidence in A5 assets | Correct interpretation |
|---|---|---|
| Paramecium conjugation | `u1-paramecium.q5_en` explicitly asks to outline conjugation; `u1-protozoa` also mentions conjugation | **PRESENT in A5 questions**; audit completeness and stage chronology before calling it PARTIAL |
| Paramecium binary fission | `u1-protozoa` includes binary-fission terminology | **MENTIONED in A5**; dedicated stage-by-stage plate remains unapproved |
| Cnidarian nematocysts | `u2-cnidaria` includes cnidocyte/nematocyst and functional description | **PRESENT in A5**; mechanism and figure require full-text review |
| Sponge cellular anatomy | `u2-porifera`, `u2-sycon`, `u2-canals` mention choanocytes or sponge tissue terminology | **PRESENT in A5**; not grounds to add a duplicate sponge histology lesson |
| Taenia | `u3-platy` and `u3-platy-adapt` mention Taenia/tapeworm | **MENTIONED in A5**; detailed life-cycle coverage unverified |
| Earthworm | `u4-modes-life.a5_en` discusses burrowing, peristalsis, chaetae and feeding | **PRESENT as comparative ecology**; full organ-system type study unverified |
| Cephalopods | `u5-cephalopods` is an existing A5 chapter; Sepia mentioned | **EXISTING CHAPTER**; do not add a duplicate chapter |
| Modern protist classification | `u1-protozoa.a5_en` correctly warns that Protozoa is a traditional, non-monophyletic teaching group | **VERIFIED PRESENT**; preserve this scientifically accurate caveat |

**Critical correction to prior recommendation:** Hydra, Taenia, earthworm, cockroach and related type studies must not be labelled wholly absent merely because they do not have dedicated chapter IDs. Existing sections and illustrations can cover them. Full lesson text needs inspection.

## Asset-intake caution from APK file list
The baseline APK contains `assets/paramecium/fission/F01_vegetative_PROVISIONAL.png`, `F02_micronuclear_mitosis_PROVISIONAL.png`, and `F03_chromosome_segregation_PROVISIONAL.png`, as well as `assets/unit1/concepts/paramecium_binary_fission.png` and `paramecium_conjugation.png`. Their mere presence **does not mean** they are displayed, scientifically approved, labelled correctly or suitable for replacement. Do not relabel these as MISSING FROM APK; label **APPROVED, DEDICATED PLATES NOT VERIFIED** until each image, runtime linkage and review record is checked.

## Next work requiring source and emulator verification
1. Fetch actual Unit I lesson bodies and compare A5 questions against narrative English/Tamil; record precise chapter/section IDs.
2. Verify image references and render paths, and visually inspect all candidate fission/conjugation assets; reject scientifically incorrect chronology.
3. Produce or source approved individual PNGs, with provenance, labels and independent Zoology/Tamil acceptance.
4. Run exact-commit CI and capture Tamil 100% and 200% emulator screenshots; retain physical-device and production release hold.

No app source or approved image is changed by this audit.
