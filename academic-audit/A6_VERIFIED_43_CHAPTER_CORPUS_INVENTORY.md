# A6 — Verified A5 curriculum inventory
Source: five JSON assets at `native-app/app/src/main/assets/a5/U1_A5_VALIDATED.json` through `U5_A5_VALIDATED.json`, U57 branch, inspected 2026-10-20.

This is the actual assessment corpus inventory, not an assertion that complete textbook lessons exist. Preserve all chapter IDs and question content unchanged.

## Unit 1 — 7 chapter IDs

| Chapter ID | Accepted A5 topic/question | Textbook acceptance |
|---|---|---|
| `u1-intro` | Write short notes on taxonomic hierarchy. | NOT VERIFIED |
| `u1-protozoa` | Give the general characters of Protozoa with suitable examples. | NOT VERIFIED |
| `u1-paramecium` | Describe osmoregulation and feeding in Paramecium. | NOT VERIFIED |
| `u1-proto-parasites` | Compare Entamoeba histolytica, Trypanosoma brucei and Leishmania in a tabular form. | NOT VERIFIED |
| `u1-nutrition` | Explain four modes of nutrition in protozoan/protist forms with suitable examples. | NOT VERIFIED |
| `u1-host-parasite` | Compare the life cycles of Entamoeba histolytica and Plasmodium. | NOT VERIFIED |
| `u1-locomotion` | Compare cilia, flagella and pseudopodia as locomotory structures in protists. | NOT VERIFIED |

## Unit 2 — 9 chapter IDs

| Chapter ID | Accepted A5 topic/question | Textbook acceptance |
|---|---|---|
| `u2-porifera` | Give general characters and classification of Porifera up to classes. | NOT VERIFIED |
| `u2-sycon` | Describe Sycon with special reference to canal system and reproduction. | NOT VERIFIED |
| `u2-canals` | Give an account of canal systems in sponges. | NOT VERIFIED |
| `u2-reproduction` | Write an essay on reproduction in sponges. | NOT VERIFIED |
| `u2-cnidaria` | Give general characters and classification of Coelenterata up to classes. | NOT VERIFIED |
| `u2-obelia` | Give a detailed account of Obelia. | NOT VERIFIED |
| `u2-corals` | Write an essay on corals and coral reefs. | NOT VERIFIED |
| `u2-coral-economics` | Write on the economic importance of corals and coral reefs. | NOT VERIFIED |
| `u2-polymorphism` | Write an essay on polymorphism in Hydrozoa. | NOT VERIFIED |

## Unit 3 — 7 chapter IDs

| Chapter ID | Accepted A5 topic/question | Textbook acceptance |
|---|---|---|
| `u3-platy` | Give general characters and classification of Platyhelminthes up to classes. | NOT VERIFIED |
| `u3-fasciola` | Give a detailed account of Fasciola hepatica. | NOT VERIFIED |
| `u3-platy-adapt` | Write an essay on parasitic adaptations in Platyhelminthes. | NOT VERIFIED |
| `u3-aschel` | Give general characters of Aschelminthes and the place of Nematoda. | NOT VERIFIED |
| `u3-ascaris` | Give a detailed account of Ascaris lumbricoides. | NOT VERIFIED |
| `u3-nematode-parasites` | Write notes on Wuchereria, Enterobius and Ancylostoma. | NOT VERIFIED |
| `u3-nematode-adapt` | Write an essay on parasitic adaptations in nematodes. | NOT VERIFIED |

## Unit 4 — 8 chapter IDs

| Chapter ID | Accepted A5 topic/question | Textbook acceptance |
|---|---|---|
| `u4-annelida` | Give general characters and classification of Annelida up to classes. | NOT VERIFIED |
| `u4-nereis` | Give a detailed account of Nereis. | NOT VERIFIED |
| `u4-metamerism` | Write an essay on metamerism in annelids. | NOT VERIFIED |
| `u4-modes-life` | Write an essay on modes of life in annelids. | NOT VERIFIED |
| `u4-arthropoda` | Give general characters and classification of Arthropoda up to classes. | NOT VERIFIED |
| `u4-penaeus` | Give a detailed account of Penaeus indicus. | NOT VERIFIED |
| `u4-peripatus` | Write an essay on the affinities of Peripatus. | NOT VERIFIED |
| `u4-crustacean-larvae` | Give an account of larval forms in Crustacea. | NOT VERIFIED |

## Unit 5 — 12 chapter IDs

| Chapter ID | Accepted A5 topic/question | Textbook acceptance |
|---|---|---|
| `u5-mollusca` | Give general characters and classification of Mollusca up to classes. | NOT VERIFIED |
| `u5-pila` | Give a detailed account of Pila globosa. | NOT VERIFIED |
| `u5-torsion` | Write an essay on torsion in gastropods. | NOT VERIFIED |
| `u5-pearl` | Write an essay on pearl formation. | NOT VERIFIED |
| `u5-echinodermata` | Give general characters and classification of Echinodermata up to classes. | NOT VERIFIED |
| `u5-asterias` | Give a detailed account of Asterias. | NOT VERIFIED |
| `u5-echino-larvae` | Give an account of larval forms in echinoderms. | NOT VERIFIED |
| `u5-echino-affinities` | Write an essay on affinities of echinoderms. | NOT VERIFIED |
| `u5-fossils` | Write an essay on invertebrate fossils of teaching importance. | NOT VERIFIED |
| `u5-foot` | Discuss, with reference to Pila, Unio and a cephalopod, the structural modifications of the molluscan foot and relate each modification to its mode of life. | NOT VERIFIED |
| `u5-wvs` | With a labelled diagram, describe the water-vascular system of Asterias and explain how it operates the tube feet during locomotion and feeding. | NOT VERIFIED |
| `u5-cephalopods` | Justify the statement 'Cephalopods are advanced molluscs', discussing nervous, sensory, circulatory and locomotory features, and comment on their economic importance. | NOT VERIFIED |

**Total: 43 accepted A5 chapter IDs.** One additional Earthworm teaching slot `u4-earthworm-type-study` is specified in `NativeCurriculumManifest.kt`, making 44 teaching slots.

## Next inspection and implementation
1. Cross-reference these exact IDs with `NativeCurriculumManifest.kt` and Kotlin teaching objects `FiveUnitRestorationLessons`, `UnitIILessons`, `UnitIIILessons`, `UnitIVEarthwormLessons`, `UnitIVNereisLessons`, `UnitIVPenaeusTeaching`, `UnitIVSupplementaryLessons`, and other referenced modules.
2. Count explanatory sections and English/Tamil paragraphs, distinguish placeholders and assessment answers from full lessons, and flag deficient chapters.
3. Improve learner-visible Kotlin prose in failing chapters without editing accepted A5 JSON or substituting unverified artwork.
4. Run exact-commit tests, then separate biology/Tamil and device review. Release HOLD.