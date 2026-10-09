# INVERTEBRATA R1.2 — Paramecium caudatum CVC mechanism review and physical-device QA

**Academic status: DRAFT_UNVERIFIED. CI does not confer biological or Tamil approval.**
Baseline before this change: `c45f7f6806822de394aa92938d05756f5398694b`. Implementation is fully native Kotlin/Compose.
R1.2 contributes a genuine *learner-visible* mechanism cutaway, not a question bank or HTML replacement.

## Independently verifiable science references
1. R.D. Allen (1988). "Membrane Dynamics of the Contractile Vacuole Complex of Paramecium",
   Journal of Protozoology. https://doi.org/10.1111/j.1550-7408.1988.tb04078.x —
   membranes change between tubular and planar configurations; do NOT imply measured
   contraction speed or simply a muscular squeezing balloon.
2. R.D. Allen, *Paramecium caudatum*, Chapter 10a, Fig. 46:
   https://www6.pbrc.hawaii.edu/allen/ch10a/46-pca4201.html —
   collecting canal, smooth/decorated spongiome; this is an ultrastructure section,
   NOT a calibrated whole-cell coordinate plate.
3. Plattner (2013), *Contractile vacuole complex — a dynamic membrane system*,
   primary literature review context. Verify exact bibliographic metadata
   before quoting: https://pubmed.ncbi.nlm.nih.gov/23919298/
4. Paramecium modern-model review, Fig. 1: *P. caudatum* light microscopy and
   schematic whole-cell contractile/food vacuoles:
   https://pmc.ncbi.nlm.nih.gov/articles/PMC10143506/
   Published figure permissions do not convey redraw/redistribution rights.
5. General level: OpenStax Biology 2e, protists:
   https://openstax.org/books/biology-2e/pages/23-3-groups-of-protists

## New R1.2 learning output
- Four phase selections linked BY ID to the existing scientific lesson
  (osmosis → collecting → filling → expulsion), plus previous/next/replay by
  explicit wrap to first stage.
- Dynamic expansion/contraction of an *illustrative* central fluid lumen,
  collecting canals and ampullae, surrounding spongiome dots and cortical
  discharge pore. The **single inset represents one CVC**, and **does not
  imply** that the two whole-cell CVCs empty synchronously.
- 48dp accessibility controls and stage-specific bilingual TextToSpeech using
  existing on-device speech handling; source and review warning on the screen.
- State model is deterministic and measured kinetics are NOT invented.
- Distinguishes osmoregulatory pore from cytoproct (indigestible food egestion).

## Open academic review — approvals cannot be inferred
| Structure / issue | Independent evidence required | Biology reviewer/date/evidence | Tamil reviewer/date/evidence |
|---|---|---|---|
| Central CV lumen and membrane | P. caudatum stage-relevant microscopy or source-linked diagram | PENDING | PENDING |
| Collecting canal / ampullae | Allen Fig. 46 and relevant primary figure | PENDING | PENDING |
| Spongiome architecture | Allen Fig. 46, source species and TEM modality verified | PENDING | PENDING |
| Cortical discharge pore | Species-identified pore image and orientation analysis | PENDING | PENDING |
| CVC vs food vacuole and cytoproct | Whole-cell, functional and Tamil terminology comparison | PENDING | PENDING |

Source count or a CI-green screenshot does not fill any review signature field.
All precise arm number, thickness, membrane location, connectedness,
geometry and relative scale are provisional *didactic symbols*, not a
registered microscope reconstruction.

## Tamil review vocabulary (draft, no sign-off)
| English concept | In-app Tamil | Editorial question |
|---|---|---|
| Contractile vacuole | சுருங்கும் நுண்குமிழ் | Verify accepted PG/UG textbook use |
| Collecting canals | சேகரிப்புக் கால்வாய்கள் | Distinguish from spongiome tubules |
| Spongiome | ஸ்பாஞ்சியோம் | Transliteration vs accepted technical term |
| Ampullae | ஆம்புல்லாக்கள் | Clarify distal swellings vs vacuoles |
| Discharge pore | வெளியேற்றத் துளை | Distinguish from செல் கழிவுவெளியேற்றப் பகுதி (cytoproct) |
| Osmoregulation | நீர்ச்சமநிலை ஒழுங்குபடுத்தல் | Confirm register for Tamil Zoology learners |

## Required ON-PHYSICAL-DEVICE QA — NOT DONE
Use R1.2 debug QA APK after all four CI checks pass on its exact commit.
Record model, Android version, orientation, device language/font scale,
TalkBack state, build SHA and screenshot/video filenames.
Perform actual direct Canvas taps and 48dp accessible button selections for
food vacuole, anterior CV and posterior CV. Confirm independent highlights
and organ narration in English and installed Tamil TTS (when available).
Inspect CVC close-up at all four stages, previous/next/replay,
off-screen scrolling, portrait, landscape and 200% Tamil font scale;
look for clipping, wrong organ identity, false pore/cytoproct coupling,
audio failure, app memory loss and unexpected resets after rotation/process death.
Document failures and retest with the EXACT signed debug APK + SHA.

- Physical-device observation: **PENDING**
- Biological approval: **0/5**
- Tamil terminology/diagram approval: **0/5**
- Production release: **HOLD**
- Paramecium module: **PARTIAL R1; NOT FULL ACADEMIC ACCEPTANCE**

Only a human reviewer with actual reference and device evidence can sign off.
