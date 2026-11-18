# INVERTEBRATA — authoritative educational master plan
Reconstructed 2026-10-09 from the user's original requirements, accepted syllabus and repository history.

## Original product
Build a COMPLETE undergraduate Zoology teaching and virtual-laboratory app, natively in Kotlin/Jetpack Compose, in English and Tamil. Its main experience must be detailed scientific lessons, high-fidelity original anatomical illustrations, organ touch/highlight and spoken explanations, operating physiological and life-cycle simulations, practical observation and assessment. The question bank is SUPPORTING material, not the product. Preserve offline-first operation, fast mobile performance, screen resizing, saved learning state, accessibility and 200% Tamil fonts. Never replace native implementation with HTML/WebView, and do not treat unreviewed diagrams as accepted.

## Five syllabus units
1. Protozoa: classification and Paramecium type study; Entamoeba, Trypanosoma, Leishmania and Plasmodium, nutrition, host-parasite relationships and locomotion.
2. Porifera/Cnidaria: classification, Sycon type study and canal flow/reproduction; Obelia type study, colony polymorphism and generations; coral reefs and economic significance.
3. Flatworms/nematodes: classification; Fasciola hepatica and Ascaris lumbricoides detailed anatomy, parasitic adaptations, host-linked life cycles.
4. Annelida/Arthropoda: classification; Earthworm (Metaphire/Pheretima), Penaeus detailed type studies; Nereis, metamerism, Peripatus, crustacean larvae.
5. Mollusca/Echinodermata: classification; Pila and Asterias complete type studies; torsion, ctenidium, water vascular system, larval development and remaining listed topics.

## Nine compulsory organism modules and essential simulations
- Paramecium: external/cilia, oral apparatus, contractile vacuoles, internal organ systems, ciliary propulsion, feeding, osmoregulation, binary fission and conjugation.
- Sycon: external, body wall and canal system; water-flow animation, feeding and reproduction.
- Obelia: colony, hydranth/gonangium, medusa; alternation of generations and life-cycle animation.
- Fasciola: external, internal digestive/excretory/reproductive anatomy; snail-associated lifecycle stages.
- Ascaris: male/female morphology and internal systems; transmission and larval migration.
- Earthworm: external setae, digestive, circulatory, nervous, excretory and reproductive systems with corrected anatomical labels, segment positions and functional motions.
- Penaeus: external, generalized and specialized appendages, internal systems, larval stages.
- Pila: external, mantle cavity, ctenidium, respiration and torsion.
- Asterias: external/internal, ambulacral water-vascular system, tube feet, reproductive/larval stages.

## Every completed teaching module must contain
Detailed bilingual scientific lessons with source references; accurately labelled native Canvas atlas drawings with magnification; organ selection and distinct highlight; working organ-specific Android text-to-speech explaining function; native play/pause/replay simulations with correct stage or flow ordering; practical learning activities, formative feedback and assessments; offline and screen-reader support. Biology and Tamil terminology require independent review. A list of lesson IDs, a noninteractive image, brief Q&A, compile success or a debug APK does NOT meet this standard.

## Corrected development sequence
R1: make Paramecium a functioning and substantial flagship module—complete prose, four or more plates, full organ interaction, explanations and narration, contractile-vacuole and feeding/ciliary/reproduction simulations.
R2: complete Sycon and Obelia with other Unit II teaching chapters.
R3: complete Fasciola and Ascaris with Unit III chapters.
R4: complete Earthworm and Penaeus, repair historical organ/segment/leader errors and complete Unit IV.
R5: complete Pila and Asterias with Unit V chapters.
R6: integrate and verify all syllabus lessons, English/Tamil content and assessment modes across the app.
R7: independent biological and Tamil acceptance, physical Android device QA, then authorized signed production release.

## Development governance
Current syllabus architecture contains 43 preserved A5 chapter IDs plus an additional Earthworm teaching slot (44 teaching slots in total), and 86 accepted five-mark bilingual questions with 430 answer points. These numbers are not evidence of completed teaching modules. Preserve accepted A5 data unchanged; keep any unfinished anatomy marked DRAFT_UNVERIFIED. Record each milestone with exact source SHA, actual learner-visible improvements, diagram/lesson/simulator/audio inventory, independent review status, test evidence and unresolved defects. CI checks are engineering gates, not curriculum completion. Work must now prioritize real academic content and interactive simulator functions before additional peripheral QA features. Do not claim completion or production readiness without the entire original product scope.
