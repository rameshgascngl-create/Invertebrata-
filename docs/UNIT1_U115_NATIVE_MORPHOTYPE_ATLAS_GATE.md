# Unit I U1.15 — native stage-specific conceptual anatomy atlas

## Delivery

Strictly native **Kotlin + Jetpack Compose Canvas** teaching illustration,
NOT a user-supplied physically hand-pencil-drawn scan. This distinction is
visible on the learner page and fullscreen drawing; reviewers must not use
digital schematics to bypass original pencil-source approvals.

The source base is U1.14 **engineering-accepted** commit
`35d38721eb5829c9dc928dbe3671599a6c91951a`
(Actions run [38836233116](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38836233116):
5/5 offline 100% + 5/5 offline 200% native Android instrumentation pass).

## Three anatomically distinct forms

1. **Trypanosoma brucei**: mammalian *bloodstream trypomastigote*, elongated
   extracellular, one central nucleus, kinetoplast posterior to nucleus,
   undulating membrane along the flagellar margin, free anterior flagellum.
   Transmission involves the tsetse fly (*Glossina*). The flagellum is not
   shown emerging from the posterior pole.
2. **Leishmania spp.** mammalian *intracellular amastigote* in host
   macrophage. The host cell and host nucleus differ from the parasite's
   small oval cells, each with its own nucleus and kinetoplast; there is
   **NO long external flagellum** on the amastigote. Species cannot be
   identified from a genus-level depiction alone.
3. **Leishmania spp.** sand-fly *promastigote*: elongated flagellated form,
   nucleus posterior to anterior kinetoplast, projecting free anterior
   flagellum. **No undulating membrane.** It must not be drawn as an
   intracellular amastigote or labeled as proven `L. donovani`.

These are three separate biological models/drawing branches, not identical
raster figures duplicated with different caption overlays.

## Teaching and Android UI

- Programmatic *digital graphite-toned* Canvas uses the native Compose
  drawing API. This is an original software illustration, **not an
  authentic hand-drawn/scanned pencil master**, micrograph or published
  academic anatomical plate.
- English/Tamil title, biological setting and substantive explanatory
  paragraph for each form. Stage-specific nucleus, kinetoplast,
  posterior/anterior, membrane, macrophage and flagellum selection.
- The conceptual selection ring is a **teaching overlay on the Canvas**
  and is not derived from a verified raster pixel or approved touch hotspot.
  Learners select from native 48dp accessible structure controls; no
  unreviewed image-touch hotspot inference occurs.
- Android offline `OfflineNarrator` speaks the selected bilingual
  explanation when a **button** is pressed; no cloud voice assumption.
  Audio is a concept-level narration, not signed-off organ-on-scan touch
  narration; always provide written transcript.
- Dedicated full-screen native Compose Canvas enlargement dialog.
- All UI stays in existing Kotlin `LazyColumn` chapter before the
  untouched original A5 assessment.
- The existing linked source illustrations, real-scan importer and
  debug-only calibration screen remain intact. No HTML, JS, SVG/WebView,
  remote image loads, Internet permission or release identifier change.

## Non-bypassable validation

1. Confirm exact candidate HEAD and diff against U1.14.
2. `UnitIStageAtlasTest`: JVM checks for three distinct stage IDs and
   morphotypes; central vs posterior/anterior kinetoplast, no external
   amastigote flagellum, no promastigote undulating membrane, host vs
   parasite nuclear identities and complete bilingual copy.
3. Compile debug AND release Kotlin, Android instrumentation Kotlin and
   all JVM tests on one exact commit.
4. Run API34 Android emulator **offline**, verify 7/7 Unit I methods at
   100% font AND 7/7 at 200% font. Do not accept partial results.
5. Capture and visually inspect all required previous figures PLUS
   `u115-english-promastigote-canvas`,
   `u115-english-amastigote-canvas`,
   `u115-tamil-promastigote-canvas` at both scales. In particular
   check that host nucleus and parasite nucleus are not conflated, long
   flagellum does not appear on an amastigote and the stage selector is
   accessible at 200% Tamil. The screenshot image alone is not a proof of
   scientific accuracy.
6. Test fullscreen enlargement, orientation, back navigation, offline
   audio availability/unavailability and TalkBack on a real phone.
7. The **pencil-master and source-pixel academic gate remains OPEN**.
   Before a scanned pencil atlas can replace the conceptual drawings,
   require an actual scan with documented authorship/licensing, external
   zoologist's anatomical review, original source SHA, audited leader
   endpoints and Tamil/editorial approval.

## Biology sources

- CDC DPDx African Trypanosomiasis:
  https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html
- CDC DPDx Leishmaniasis:
  https://www.cdc.gov/dpdx/leishmaniasis/

## Current release policy

Native source committed does not equal engineering PASS. The candidate
must remain a **DRAFT PR** until the exact-commit CI and screenshot evidence
are examined; even then production signing/Play/Indus upload remains on
academic and physical-device hold.
