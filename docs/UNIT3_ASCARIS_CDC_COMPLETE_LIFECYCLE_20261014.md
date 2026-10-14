# Unit III — Ascaris complete life-cycle reference replacement

**Candidate status: engineering QA in progress; academic/real-device acceptance HOLD.**

## Scientific correction

Previous master: Pfurtscheller historical **eggs/adults fragment**, missing soil embryonation and larval hepatopulmonary migration; its use as a life-cycle atlas was **rejected**.

Replacement raster source: **CDC DPDx — Ascariasis**, [official complete life-cycle illustration](https://www.cdc.gov/dpdx/ascariasis/modules/Ascariasis_LifeCycle_lg.jpg). Reference explanation: https://www.cdc.gov/dpdx/ascariasis/.

The official source figure depicts:
1. Adult male and female *Ascaris lumbricoides* in the small intestine.
2. Fertilised eggs (potentially developing) and **unfertilised eggs (not infective)** passed with faeces.
3. Embryonation of fertile eggs in suitable soil; **18 days to several weeks** according to conditions.
4. Ingestion of the **infective embryonated egg**, not percutaneous larval entry.
5. Hatching in the small intestine and entry through intestinal mucosa.
6. Portal/systemic circulation to the lungs; further development for **10–14 days**.
7. Bronchial migration to pharynx, then re-swallowing and maturation in the small intestine.
8. Egg-laying approximately **2–3 months after ingestion of infective eggs**, not 6–8 weeks.

## Native implementation

The existing six-plate syllabus inventory is preserved. Only
`type_studies/ascaris/life-cycle.png` is replaced in the source asset pipeline, from
the **entire original CDC diagram** (including legend/arrows and all seven CDC stage markers), not a procedurally drawn shell or crop. It is resized preserving aspect ratio into the 960×1280 native gallery master with source attribution **outside** its pixels.

The native Kotlin gallery shows a bilingual CDC source disclosure and detailed content description. Additional semantic buttons explain non-infective eggs and re-swallowing. The existing step-by-step simulation remains separate and available offline; no new internet permission is required at runtime.

**Provenance:** CDC illustration is official scientific artwork, **not a graphite pencil scan**. Do not relabel it as hand-drawn. The generated AI composite plate was rejected because its developmental stages, timings and leader lines could not be independently certified.

## Validation and unresolved gates

- CI requires 6 unique 960×1280 PNGs, offline Kotlin compilation and JVM/instrumentation tests at 100%/200% Tamil, and an exact-commit evidence artifact.
- The new CDC source and output SHA-256 values must be **pinned after reviewing the first successful generated-artifact bytes**; the initial discovery run must not be represented as immutable-source certified.
- Stage buttons provide **text/voice selection**, not a claim that pixel hotspots are correct. Direct image touches and leaders remain disabled until a source-specific zoological reviewer supplies coordinates checked at multiple zoom levels.
- Physical handset QA, actual audio output and Tamil editorial review require evidence from an Android device, not the emulator.
- All six plate statuses stay `DRAFT_UNVERIFIED` pending independently verified anatomy. Play/Indus release merge is prohibited.

References: https://www.cdc.gov/dpdx/ascariasis/ (Life Cycle and Image Gallery), https://www.cdc.gov/dpdx/ascariasis/modules/Ascariasis_LifeCycle_lg.jpg .
