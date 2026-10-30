# INVERTEBRATA — content structure audit and "physical book" blueprint

Audit date: 2026-10-22 (branch `feat/plate-pipeline`). Counts are regex-derived from the Kotlin lesson files and the A5 JSON; treat them as ±10 %.

## 1. What exists today

| Layer | Location | Shape | Role |
|---|---|---|---|
| A5 exam notes | `assets/a5/U1–U5_A5_VALIDATED.json` (43 chapters) | question, 5–6 telegraphic bullets, two 2-mark and two 5-mark model answers, EN + TA | Accepted corpus; **do not edit** |
| Native lessons | `data/UnitIILessons`, `UnitIIILessons`, `UnitIVSupplementaryLessons`, `FiveUnitRestorationLessons`, `NativeLessonDrafts`, Earthworm, Nereis, Penaeus files | lesson → sections → 2–3 paragraphs (EN+TA) + URL sources | Drafts, all `DRAFT_UNVERIFIED` |
| Plates | `assets/type_studies/**` (35 slots, all filled) + credits | one gallery per type study | Figures |

## 2. Findings

**F1 — Depth is about a tenth of a textbook.** 41 lesson blocks hold ~13,800 English words in total (median ≈ 280 words per chapter; largest: Earthworm ≈ 1,200, Pila ≈ 760, Crustacean larvae ≈ 650). A printed B.Sc. chapter on the same topics runs 3,000–5,000 words. Unit I/V "restoration" chapters are 6–7 paragraphs each.

**F2 — Structure is flat.** The contract (`BilingualLessonContract.kt`) has only `LessonSection(id, heading, paragraphs, sources)`. There are no chapter numbers, no section numbering (1.1 / 1.1.1), no sub-heads, no introduction, no objectives, no summary.

**F3 — No definitions layer.** Terms (prosopyle, metamerism, torsion, ostium…) are explained inline or not at all. No definition boxes, no per-chapter key terms, no unit glossary, no index.

**F4 — Figures are detached from text.** Plates are shown in a gallery per organism; paragraphs never refer to "Fig. 2.3". Captions are the slot title only.

**F5 — Assessment is detached.** The 2- and 5-mark answers live in the A5 JSON and are not linked to the chapter that teaches them; chapters have no review questions.

**F6 — Sources are bare URLs** (OpenStax, NOAA, university PDFs). A printed book needs citations (author, title, edition, chapter) plus a further-reading list.

**F7 — Uneven coverage.** Unit II/III chapters have 3 sections (type studies 6); Unit I/V chapters have no explicit sections in code; Earthworm has 15. Tamil parity is enforced only as "non-blank".

**F8 — Status.** Every lesson is DRAFT_UNVERIFIED; no scientific or Tamil review is recorded for any chapter.

## 3. Book blueprint (target layout)

**Front matter (per app):** title page, preface, syllabus map (A5 chapter ↔ lesson ↔ plates), how to use, list of figures.

**Each chapter, in this order:**
1. Chapter number and title (EN/TA)
2. Learning objectives (3–6 bullets)
3. Introduction (100–150 words)
4. Numbered sections `N.1`, sub-sections `N.1.1` with explanatory prose
5. Definition boxes: term (EN/TA), one-sentence definition, optional etymology
6. Key-point and "note" call-outs; comparison tables
7. Numbered figures (`Fig. N.k`) tied to plate slots, with credit line
8. Chapter summary (5–8 bullets)
9. Key terms list
10. Review questions: 2-mark and 5-mark items linked to the A5 corpus by id
11. Further reading (full citations)

**Back matter (per unit):** glossary (EN/TA), index, references.

## 4. Implementation plan

1. Extend the contract additively: `ChapterFrontMatter(objectives, introduction)`, `ContentBlock` (Paragraph, Definition, KeyPoints, Note, FigureRef, Table), `ChapterBackMatter(summary, keyTerms, reviewQuestionIds, furtherReading)`. Existing `LessonSection` stays valid.
2. Validation tests: objectives 3–6; every key term defined; every `FigureRef` resolves to a packaged plate; every review id exists in A5; bilingual parity of block counts.
3. Renderer: a single `BookChapterReader` replacing per-unit teaching chapters (numbered headings, definition cards, figure captions, summary, key terms, review questions).
4. Content: author chapters unit by unit. Pilot: **Sycon** (all three plates now present), then Obelia, Fasciola, Ascaris.
5. Gate: scientific review and Tamil review per chapter before status leaves DRAFT.

## 5. Effort and risks

- Full rewrite ≈ 40 chapters × ~3,500 words × 2 languages ≈ 280,000 words. Do it per unit.
- Tamil terminology needs the author's review; draft Tamil is not to be treated as approved.
- Facts must be checked against the cited textbooks; nothing here is subject-expert reviewed.

## 6. Implementation status (updated 2026-10-23)

**All 43 chapters in `NativeCurriculumManifest.a5ChapterIdsByUnit` now have a book-format chapter** (`data/BookChapters.all`), enforced by `BookChapterContract` (≥2,000 English words, ≥5 numbered sections, ≥8 definitions, 3–6 objectives, 5–8 summary points, ≥3 quick checks, ≥2 further-reading entries, valid unique plate slots, no blank EN/TA strings).

| Unit | Chapters | Status |
|---|---|---|
| I | 7 | Complete |
| II | 9 | Complete |
| III | 7 | Complete |
| IV | 8 | Complete |
| V | 12 | Complete |

**Known limits (not resolved by this work):**

- **Earthworm type study** is not in the A5 chapter list, so it cannot be numbered and stays as the existing lesson.
- **Penaeus interactive explorers and Nereis section lessons** are no longer shown where a book chapter exists (single-render policy).
- **Tamil is a working draft** and needs the author's terminology review before any chapter leaves `DRAFT_UNVERIFIED`.
- **Facts flagged for reviewer confirmation:** corals schedule, *Hydractinia* zooid count, *Physalia* terminology; medical content (parasites, malaria) is biology and control principles only, with no treatment guidance.
- **Image provenance still open:** authors and licences for the Sycon and Obelia images, Haeckel plate number, several low-quality plates, and supplied images not yet placed.
- **Scientific review** against the cited textbooks has not been done by a subject expert.

### 6.1 Tamil terminology alignment (2026-10-23)

- Terms were checked against the Tamil Nadu State Board Class 11 Zoology, Class 11 practical manual and Class 12 Zoology / Bio-Zoology (Tamil medium) text layers. **599 defined terms** were catalogued in `All_units_Tamil_terms_review.csv`.
- **30 terms are confirmed** from those books and glossaries and were applied. **About 504 remain draft transliterations**: the school books cover few invertebrate terms and no Tamil zoology term dictionary was available.
- Terms with competing forms and no source, left unchanged: cuticle, biramous, septum, protostome, peristalsis, sessile, reservoir, acoelomate, regeneration, haemocoel, hypnozoite, cilium, nacre.
- The author's `Approved?` / `Preferred Tamil` columns in the review sheet are the next input; corrections apply by word-boundary replacement.

### 6.2 Chapter depth (2026-10-23)

- The contract floor is 2,000 English words; printed-book length is nominally 3,000 words.
- **Result:** all 43 chapters are now at least 2,640 words (mean about 2,900). Every chapter that was near the floor has been deepened, and each deepened chapter gained at least one worked exam-answering or comparison section.
- Remaining below 3,000 words (2,640 to 2,900): protozoan parasites, Annelida, crustacean larvae, metamerism, water vascular system, echinoderm larvae, and a few others. These meet the printed-book intent and can be extended later if wanted.
- New content was written from general textbook knowledge and hedged where uncertain (for example the Indian reef figures, the sea cucumber legal status, the filariasis drug programme, the calcium role in ciliary reversal, the Cambrian lobopodian relationships, pearl-oyster acidification risk). None of it is subject-expert reviewed.
- The host term is unified as விருந்தோம்பி in all chapters written in this series. Unit I files written earlier still use the older form in a few places.

### 6.3 English in brackets for unconfirmed Tamil terms (2026-10-24)

- Rule from the author: where the exact Tamil word cannot be found, the English term is shown in brackets after the Tamil term.
- Applied to every defined term in the 43 book chapters whose Tamil does not appear verbatim in the State Board 11th Zoology or 12th Bio-Zoology/Zoology books (643 terms). 237 terms found in those books are left without brackets.
- Text extraction from the PDFs is noisy, so a term that is in the books but was not matched will carry the English in brackets as well; this is harmless.
- New chapters should follow the same rule.

### 6.4 Unit VI: Insecta and applied entomology (2026-10-24)

- Added at the author's request to cover the TANSCHE Volume II insect topics (items 14, 16, 21 to 35). **Nine extension chapters**: class Insecta, cockroach, pollinators, predators and parasitoids, disease-carrying insects, household insects, pests and IPM, cereal and stored-grain pests, other crop pests.
- The extension sits **outside the validated 43-chapter corpus** (`ValidatedCorpusContract` still requires 5 units, 43 chapters, 86 question pairs and is untouched). Unit VI is appended in `MainViewModel` after validation. The chapters have no validated A5 questions (`reviewIds` empty). They carry **draft five-mark questions** (2 per chapter, 5 bilingual points each; 24 for the 12 chapters of Unit VI) in `InsectaQuestions.kt`, written by the assistant, not from the validated JSON, and not subject-expert reviewed. The home-screen practice button now shows the computed total (86 validated plus the draft questions of Units VI and VII) instead of a hard-coded 86.
- All nine pass `BookChapterContract` (each at least 2,296 English words). Tamil is draft: State Board term where one exists, otherwise a descriptive or transliterated term with the English term in brackets.
- **Syllabus slips noted in the chapters:** "Earias insulana" is the spiny bollworm (the spotted bollworm is E. vittella); "diamond black moth" should read diamondback moth.
- Hedged facts needing expert review: Thai sacbrood virus, papaya mealybug parasitoids, Bt cotton and bollworm resistance, the endosulfan order, pollination share figures, Wolbachia, rhinoceros-beetle virus resistance, and all durations and thresholds. No pesticide doses are given.
- The Compose reader for Unit VI is verified only by CI.

### 6.5 Unit VI extended to the remaining Arthropoda topics (2026-10-25)

- Three chapters added to Unit VI (now 12): `u6-chelicerata` (Trilobitomorpha, Chelicerata, Merostomata, Arachnida, Pycnogonida), `u6-myriapoda` (Mandibulata, Myriapoda, Scolopendra, Spirostreptus) and `u6-minor-arthropods` (Tardigrada, Pentastomida, Panarthropoda). Unit VI is renamed "Further Arthropoda, Insecta and applied entomology". Six more draft five-mark questions bring Unit VI to 24.
- Hedged: the Pancrustacea and Panarthropoda relationships, Pentastomida as crustaceans, tardigrade survival claims, leg-count ranges, the Indian tick and mite diseases named.
- The topics then still without a chapter were covered by Unit VII (section 6.6).

### 6.6 Unit VII: supplementary topics in invertebrate zoology (2026-10-25)

- Closes the last 27 gaps in the TANSCHE coverage map. Eight chapters, appended after validation through the `Extensions` facade (`FurtherInvertebratesExtension`, `FurtherQuestions`), outside the validated 43-chapter corpus: `u7-minor-annelids` (Archiannelida, Echiuroidea, Sipunculoidea, Priapulida, Myzostomaria), `u7-leech` (*Hirudinaria granulosa*), `u7-annelid-excretion-asexual` (nephridia, nitrogenous wastes, polychaete asexual reproduction), `u7-minor-molluscs` (Aplacophora, Monoplacophora, Polyplacophora, Scaphopoda, Pelecypoda, economic importance), `u7-echino-classes` (Pelmatozoa, Eleutherozoa, Crinoidea and the fossil classes), `u7-aurelia-mesenteries`, `u7-tapeworm-host-parasite` and `u7-protozoa-economics`.
- Each chapter passes `BookChapterContract` and carries 2 draft five-mark questions (16 in all), written by the assistant and not subject-expert reviewed. The reader's unit numerals now include VII.
- Hedged, for expert review: the traditional versus modern placement of the minor annelid groups; Neopilina and molluscan segmentation; Aplacophora as most primitive; Pelmatozoa as a non-natural group; Heterostelea and the ophiocistioids (affinities disputed); canal counts in Aurelia and the numbering of earthworm nephridia by segment; proglottid counts and uterine branch counts in *Taenia*; the status of the microsporidia (now with the fungi); durations, lengths and dates in all chapters.
- Medical safety: the leech, tapeworm and protozoa chapters give no treatment doses or clinical instructions, and say so.
- Section ids must not contain underscores (the contract rejects them).


### 6.7 Figures in the book chapters (2026-10-26)

Audit of the older-lineage APK (`u65-unit1-unapproved-debug-apk`) and of this branch:
before this change 10 of 63 chapters carried a `bfig`; none of the 20 chapters of Units VI and VII
had one, and the four protozoan reference images lived only in a separate gallery.

What changed:
- `data/BookFigureLibrary.kt` adds `lib/<id>` figure slots beside the 35 required plates. Each
  entry names an asset stem and a bilingual credit, which the reader shows under the figure.
  The 35-plate manifest, `plate_intake.py` and the tests that pin it are untouched.
- 43 figure placements in 29 chapters (Nereis 8, protozoan parasites 4, insects 2, IPM 1,
  medical insects 1, protozoa economics 2, leech 1, annelid asexual reproduction 1,
  Aurelia/anemone 2, crinoid 1, Taenia 2). Placement is scripted in `tools/book_figure_place.py`.
- 47 original schematics (30 in the first batch, 17 in the second) drawn by `tools/book_figure_gen.py`. They carry numerals only; the
  key is in the bilingual caption. Credit: "Original schematic · subject-expert review pending".
  Eight further drafts were rejected on review (unclear anatomy, or not supported by the
  chapter text) and are not shipped.
- Four Commons references are placed: Entamoeba and Leishmania (public domain), Trypanosoma (Johannahoog, CC BY-SA 3.0) and Plasmodium (Sina Adl, CC BY 4.0). The two author names were supplied by the owner and must be re-checked on the file pages before release.

Audit of the figure sections (2026-10-26, after the last batch):
- 42 library entries, all with an image file and a bilingual credit; none unused; 43 library placements in
  29 chapters. One figure (vector cycle) is deliberately used in two chapters. Caption keys, English and Tamil,
  match each other everywhere, and match the numerals drawn in the image.
- **Fixed: numerals unreadable on a phone.** The schematics were 1200 px wide with numerals about 5 dp tall
  when shown at phone width. Circles and numerals were enlarged (about 9 dp) and all 30 redrawn; the full-size view
  is still available by tapping.
- **Fixed: clipping.** The Taenia body-plan strobilus ran off the right edge and the top numerals of the nematode
  routes figure were cut; both redrawn.
- **Decided (owner, 2026-10-26): the chapter spelling stands** (நெரிஸ், பாரபோடியம்); the Nereis plates are to be regenerated later with matching Tamil, and stay as they are until then. Background: Tamil spelling of the animal name. The Nereis chapter writes நெரிஸ்; the eight
  Nereis plates (burned-in labels) write நெரீஸ், and the app as a whole uses both (13 and 15 occurrences). The
  plates also use பாராபோடியா where the chapter writes பாரபோடியம். Labels burned into a raster cannot be edited;
  either the plates are regenerated or the chapter adopts the plate spelling.
- **Accepted for now (owner): burned-in labels.** The Nereis plates carry English and Tamil labels inside the image, unlike the
  type-study plates, whose labels are kept outside the pixels by project policy. Each plate is about 2 MB and
  eight are now in one chapter; they are sub-sampled by the decoder.
- Not checked: how any figure looks on a device; Tamil caption wrapping.

Second batch and final audit (2026-10-26, owner-approved: draw only what the chapter text supports):
- 17 further original schematics in 15 chapters (Arthropoda 2, Cephalopods 2, Cockroach, Nematode parasites,
  Platyhelminth adaptations, Porifera reproduction, Nutrition, Minor arthropods, Minor molluscs, Household insects,
  Pollinators, Polymorphism, Peripatus, Chelicerata, Cereal pests). Two drafts (crustacean larval sequence, fossil order) were
  rejected on review as uninformative and are not shipped.
- Totals: 47 schematics + 12 other library images = 59 library entries, all with an image file and credit, none unused;
  60 placements in 44 of 63 book chapters (one figure, the vector cycle, is used in two chapters on purpose).
- Caption keys checked by script against the numerals drawn: English matches Tamil and the drawing everywhere.
  One defect found and fixed (fluke larvae: numeral 7 was drawn but not keyed).
- Layout defects found and fixed: the Aurelia medusa dome was cut by the top edge, several numerals touched the picture edge
  (`num()` now keeps every marker inside the canvas), and numerals 1 and 4 of the nematode routes figure covered the heads.
  The only figure that fills its edges is the tusk shell, where the sediment is deliberately full-bleed.
- 8 chapters still have no figure, deliberately: Foot (a concept comparison; the three feet would need invented anatomy),
  Fossils (the text gives only two dates; no figure adds to the table), Minor annelids, Annelid modes of life,
  Crustacean larvae (the stage drawings were unclear), Coral economics, Crop pests (five different insects), Natural enemies
  (the parasitoid cycle is already shown in the insect chapters).
- Not checked: how any figure looks on a device; Tamil caption wrapping. The schematics and the AI-generated Nereis plates
  have had no subject-expert review (owner: not required for now). Tamil captions are drafts.

### 6.8 Stale CI guards fixed (2026-10-26)

The workflow `native-five-unit-restoration.yml` failed in two places because its guards still described the repository as it was before 22 Oct. Nothing was wrong with the app assets; the guards were out of date.

- **`asset-inventory` / Paramecium step.** It compared `unit1/paramecium_{external,oral,vacuole}.png` with the type-study crops, but those photographed pencil sources were removed on purpose in b7a4ee5 (their CC BY-SA obligation was the reason). They were **not restored**. The step now checks the four current Paramecium plates against the sha256, size, dimensions and licence hold recorded in `provenance/plates.json`, and fails if a legacy photograph reappears.
- **`asset-inventory` / plate register step.** Pinned to 43 required and 20 present; the audit script now reports 52 required, 46 present and 6 missing. The step pins the new counts and names the six missing Unit I stems, so a lost plate or an unrecorded new one both fail. No plate is academically approved.
- **`android-ui-r7` / emulator suites.** Five instrumented tests asserted the "missing plate" card for plates that are now packaged (Pila x4, Penaeus appendages, Paramecium locomotion, Obelia hydranth-gonangium and medusa; all 35 required type-study rasters now ship). They now use `assertPlateState`, which follows the real asset state: a shipped raster must render as a bitmap and not as a missing card, an absent one must show the missing card. Packaging a plate does not accept it. The runner also expected 4 tests where `NativeFiveUnitRestorationTest` has 6, and a screenshot name that the test no longer writes; the expected count is now read from the test source.
- **Not changed, and still stale:** `NativeU60ImagePlacementTest` still expects Trypanosoma and Leishmania "pending" cards, and has a literal `\n` inside a comment that comments out its A5-heading check. `RequiredTypeStudyPlates.kt` still names the removed `unit1/paramecium_*` files as unused fallbacks (and `Native43ChapterTextbookCoverageTest` pins that mapping). The `u57`/`u60` workflows run only on other branches.
- **Holds preserved:** anatomical accuracy, Tamil terminology, source rights ("rights holder to confirm") and the physical-device release check are untouched and are still reported as HOLD by the workflow.
