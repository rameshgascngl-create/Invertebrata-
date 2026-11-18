# Unit I U1.8 — STRICTLY NATIVE Android life-cycle simulators

## Unalterable architecture
- Kotlin + Jetpack Compose + native Compose Canvas/animation.
- Academic sequences embedded as Kotlin data, loaded entirely offline.
- Stage-specific narration uses the app's **existing** native Android `OfflineNarrator`, which selects an installed **non-network** TTS voice and exposes UNAVAILABLE rather than substituting cloud audio.
- No HTML, WebView, JavaScript, web-asset rendering or generated anatomical tracing is introduced.
- No changes to validated 43 A5 chapters, Paramecium diagrams, Entamoeba source figures, Unit II/III or release identifier.

## Two six-stage teaching sequences
**T. brucei:** metacyclic inoculation → mammalian blood/lymph → bloodstream fission → tsetse blood meal → midgut procyclic → salivary-gland epimastigote → returns to the first metacyclic stage.

**Leishmania spp.:** sand fly injects promastigotes → macrophage phagocytosis → intracellular amastigote multiplication → vector acquisition → promastigote development in fly gut → infectious forms at feeding apparatus → returns to inoculation.

- The 2.2-second play interval is explicitly a classroom stage transition, never a statement about real parasite kinetics.
- Previous/Next, Play/Pause, Reset and TTS control are separate native 48dp-minimum buttons.
- The full stage explanation is always readable; image/progress animation does not hide the text.
- Stage index is saveable; autoplay never restarts automatically following Android activity recreation or process death.
- The Canvas graphic is explicitly a stage-track **conceptual schematic**. It does not depict/anatomically annotate source figure pixels.
- Tamil 200% font, offline mode, no installed Tamil voice, rapid language switch and screen rotation require separate device validation.

## Engineering evidence gates
1. Exact candidate HEAD and branch diff verified.
2. `.github/workflows/native-unit1-protozoa-references.yml` runs the original Kotlin/JVM and API34 instrumented tests on this exact SHA; captures at 100% and 200% system font with network disabled.
3. New `UnitIFlagellateLifeCyclesTest` checks 6 stages per organism, unique IDs, bilingual copy, CDC source and scientifically critical extracellular/intracellular differences.
4. Android instrumentation has **three** methods: existing English atlas, existing Tamil atlas + simulator, and new English lifecycle controls. Every test must pass. Inspect complete XML; a green job alone does not certify drawings.
5. Screenshot verification: `u12-100/200-u18-trypanosoma-english.png`, `...-leishmania-english.png`, `...-trypanosoma-tamil.png`, `...-leishmania-tamil.png` (plus all legacy required screenshots).
6. Genuine handset: screen clipping, long Tamil labels, stage auto-advance/pause/stop, background/foreground, process recovery, TalkBack, no offline voice, and PNG zoom remain open.

## Independent academic hold
- Original pencil anatomical drawings and labeled leader-line endpoints are **not yet verified**.
- No organ-image coordinate hotspot mapping is claimed.
- Audio is stage narration, not organ-touch narration.
- Scientific figure and Tamil editorial sign-offs remain open.
- No merge, release signing, Play/Indus upload, or Unit I academic completion until all gates are satisfied.

## Primary references
- CDC DPDx African trypanosomiasis: https://www.cdc.gov/dpdx/trypanosomiasisAfrican/index.html
- CDC DPDx leishmaniasis: https://www.cdc.gov/dpdx/leishmaniasis/
