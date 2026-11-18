# Unit IV U4.1 — *Penaeus indicus* native Kotlin textbook and appendage simulator

**Engineering candidate; zoological artwork and real-device approval remain OPEN.**

## Why this phase, not another Unit III importer

Unit III U3.6a exact SHA `c4865a2fb7661a58ce87f64254d3c23d092bbb73`, Actions `38910045932`, passed Kotlin/JVM and Android API34 at font 100%/200%: Unit III 8/8 and Unit II 4/4 at both sizes. Actual full-scroll screenshots were inspected. Academic masters still need independent expert/rights/Tamil review (issue #41).

Unit IV already contained a long Earthworm/Metaphire native reader, but **Penaeus indicus** was still an assessment-only preview. The priority is *real teaching content in a complete native app*, not more empty or question-only screens.

## What has been implemented

- `UnitIVPenaeusTeaching.kt`: twelve long, structured EN–TA zoology teaching chapters: taxonomic identity, marine ecology, external segmentation, moulting, complete segmental appendage series, digestive system, gills, open circulation/excretion, nervous and sensory system, petasma/thelycum, larval development and practical observation.
- Exact *Penaeus indicus* type-study identity, including the alternative combination *Fenneropenaeus indicus* under some taxonomic treatments. Species-specific characters are not fabricated where the source only supports generalized penaeid anatomy.
- A selectable native **19 appendage-pair explorer** grouped 5 cephalic + (3 maxillipeds + 5 pereiopods) thoracic + (5 pleopods + 1 uropod pair) abdominal; unpaired telson explicitly not counted as twentieth paired limb. 48dp buttons, EN–TA scientific descriptions, opt-in embedded/offline Android TTS.
- A **seven-stage penaeid development explorer**: egg → nauplius → protozoea → mysis → postlarva → juvenile → adult; learner stage is `rememberSaveable` **outside** lazy-row disposal, so it must survive 200% Tamil scrolling.
- The existing Chapter route `u4-penaeus` presents the substantive teaching sections, appendage module, lifecycle and an honest four-pencil-plate pending notice **before** the untouched original validated A5 question cards. It remains routable with Android back and the native chapter state model; no new parallel WebView/HTML app.
- Manifest marks the new Penaeus study **DRAFT_UNVERIFIED**, not accepted. Original 43 A5 chapter IDs, 86 five-mark questions, package identity, version and PNG source assets remain unchanged.

## Reliable teaching comparators

- FAO https://www.fao.org/4/AC179E/AC179E00.htm — penaeid rostrum/body, petasma/thelycum, general ecology and life cycle
- FAO https://www.fao.org/4/ac232e/ac232e04.htm — nauplius, protozoea, mysis, postlarva development
- FAO https://www.fao.org/4/ac765t/ac765t06.htm — scientific *Penaeus* taxonomy including *P. indicus* and diagnostic characters

Freshwater *Macrobrachium* is NOT a substitute for *Penaeus*. Separate giant freshwater second chelipeds or brooded freshwater eggs must not be reused as penaeid structures.

## Evidence acceptance (exact-commit mandatory)

1. Compile Kotlin debug and Android instrumentation sources, run all JUnit tests. Maintain NativeCurriculumManifest 43 A5 chapters, immutable validated JSON and Earthworm unit content.
2. Offline API34 test Unit IV **six tests each at 100% and 200% Android font**: 3 previously validated Earthworm cases, 3 new Penaeus cases. Screenshots required at both fonts: original Earthworm 3 plus Penaeus English appendages, Tamil protozoea, retained stage, A5 preserved (seven scenes × two scales).
3. Inspect actual screenshot pixels for real headings, Tamil font and 48dp touch target legibility, lifecycle selection persistence and absence of invented anatomical plate. Green Actions status is not visual acceptance.
4. Full native application navigation smoke test on physical device: Units I–IV opening after process-death, chapter Back and Home, offline, 200% Tamil, portrait/landscape, TalkBack, memory pressure, and audible Tamil **installed offline voice**. Must not infer audible sound from a TTS button assertion.
5. Academic specialist and separate Tamil editor to review all 12 sections and 19 appendage groups. Confirm specific species and image provenance. Four original pencil master slots (external, appendages, internal and larval series) remain missing. Do not enable anatomy-hotspot taps or describe any generated vector as a hand-scanned original.
6. **No production Google Play/Indus APK/AAB** or merge to `main` until full integration and academic acceptance. A source build and passing emulator suite are engineering gates only.

## Source compatibility and release caution

This branch is based on the exact Unit III engineering PASS SHA and includes existing Unit I–III modules. It is an independent feature, **not** an automatic merge of the long draft PR chain. Cross-unit integration verification and a release PR are still separate decisions.
