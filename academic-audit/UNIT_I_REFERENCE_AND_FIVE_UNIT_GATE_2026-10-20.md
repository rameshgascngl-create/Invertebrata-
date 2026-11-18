# INVERTEBRATA — source-grounded academic audit checkpoint (2026-10-20)

Status: PARTIAL SOURCE AUDIT; NOT A FIVE-UNIT PASS. No image, textbook chapter, or production-release approval is implied.

## Evidence examined
- U57 source commit d8a3f044a07a73878d067410df8fabb1a0212018, .github/scripts/run-u57-paramecium-png.sh.
- Source commit e8017cd0f3f5e25e94bda6336d6575ccefa0fca1, native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/UnitIProtozoaReferenceGallery.kt.
- Uploaded image commit b23ff4e93a33637f4efdb55074130b2b23a926af contains four paramecium PNG files at repository root; placement in Android resources is NOT established by this upload alone.

## Verified findings from Kotlin source
1. The parasite reference gallery declares four different named references (Entamoeba, Trypanosoma, Leishmania, Plasmodium), with English/Tamil teaching text, limitations, credits, and source URLs.
2. The UI deliberately identifies those images as *references*, not independently accepted anatomical pencil plates. Preserve these scientific/provenance qualifications.
3. The Plasmodium fullscreen reader offers liver/blood/mosquito selections. It attempts dedicated `unit1/references/plasmodium_<focus>` PNG assets, and explicitly falls back to the **full overview** when a phase crop is absent. Do not describe that fallback as a phase-specific illustration.
4. The Tamil and English descriptions cover different life-cycle stages, but require a bilingual editorial review against a standard parasitology text before academic sign-off.
5. U57 script requires one Android test at each font scale (1.0 and 2.0), plus source, enlarged, and no-vector screenshots. That is a focused regression gate, NOT coverage of all 43 chapters.

## Five-unit academic acceptance checklist (not yet passed)
- Unit I: Protozoa classification; Paramecium type study, physiology, fission and conjugation; Entamoeba/Trypanosoma/Leishmania/Plasmodium biology.
- Unit II: Porifera classification, Sycon, canal systems; Cnidaria classification, Obelia, polymorphism, corals and reefs.
- Unit III: Platyhelminthes classification, Fasciola; Nematoda classification, Ascaris; adaptations and life cycles.
- Unit IV: Annelida classification and Earthworm systems; Arthropoda classification, Penaeus and appendages.
- Unit V: Mollusca classification, Pila, torsion; Echinodermata classification, Asterias and water vascular system.

## Mandatory next source inspection
Extract the actual 43-chapter Kotlin registry and every chapter's English/Tamil lesson body from the SAME target commit. For each chapter record: unit, chapter ID/title, syllabus outcome, definition, connected prose, morphology, physiology, reproduction/life cycle, image asset path, Tamil equivalence, references, and explicit PASS/FAIL/NOT VERIFIED. Do not mark chapters complete from chapter titles, image files, CI status or earlier narrative summaries.

## Release controls
Retain all 43 chapters and existing vetted images; no Canvas substitutes or fabricated artwork. Keep zoology, Tamil, source-rights and physical-device acceptance as separate HOLD gates. Green CI is not academic acceptance.
