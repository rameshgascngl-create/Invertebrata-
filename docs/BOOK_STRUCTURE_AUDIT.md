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
