# Unit III — Ascaris complete life-cycle reference replacement

**Candidate status: engineering QA in progress; academic/real-device acceptance HOLD.**

## Scientific correction

Previous master: Pfurtscheller historical **eggs/adults fragment**, missing soil embryonation and larval hepatopulmonary migration; its use as a life-cycle atlas was **rejected**.

Replacement raster: **CDC DPDx — Ascariasis**, from the [Wikimedia Commons preservation of the official full life-cycle figure](https://commons.wikimedia.org/wiki/File:Ascariasis_LifeCycle_lg.jpg), originally at https://www.cdc.gov/dpdx/ascariasis/modules/Ascariasis_LifeCycle_lg.jpg. The direct CDC URL returned HTTP 403 to the CI runner; the archived original was successfully retrieved and inspected. Reference explanation: https://www.cdc.gov/dpdx/ascariasis/.

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
- The source and output SHA-256 values were pinned after inspecting the generated master: source `6dac332abb790e13a39bff0d206a0a7a63816f6fd9385757924a53174db1af9f`, raster `6fdc10fe1296238cf738b54592465f6185f4e9b77d7849339626eb15c56caac0`. This proves asset identity, **not** independent approval of all organ positions.
- Stage buttons provide **text/voice selection**, not a claim that pixel hotspots are correct. Direct image touches and leaders remain disabled until a source-specific zoological reviewer supplies coordinates checked at multiple zoom levels.
- Physical handset QA, actual audio output and Tamil editorial review require evidence from an Android device, not the emulator.
- All six plate statuses stay `DRAFT_UNVERIFIED` pending independently verified anatomy. Play/Indus release merge is prohibited.

References: https://www.cdc.gov/dpdx/ascariasis/ (biology), https://commons.wikimedia.org/wiki/File:Ascariasis_LifeCycle_lg.jpg (original archive raster and authorship), https://www.cdc.gov/dpdx/ascariasis/modules/Ascariasis_LifeCycle_lg.jpg (original CDC location).
