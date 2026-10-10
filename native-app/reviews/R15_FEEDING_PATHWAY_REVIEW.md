# R1.5 — Native Paramecium feeding pathway plate

Status: ENGINEERING CANDIDATE; R1.3 anatomical, Tamil and physical-device approval remains OPEN.

## Exact source and limits
* Base native HEAD: `b1a69e2f22e05771949eb75f5fbeeaac144c6bea`.
* The four stages and bilingual explanations are consumed directly from `ParameciumLearningEngine.simulation(ParameciumProcess.FEEDING)`. The academic A5 corpus is not edited.
* The plate is original lightweight Compose Canvas artwork. Nodes/arrows represent the functional sequence, **not** actual spatial coordinates of organelles, microscopic particle trajectories or physiological timing.
* The same screen contains source-derived prose, four distinct stages, touch-selected graphite highlights, full-size accessible control buttons and per-stage audio via the existing TextToSpeech callback.
* Both feeding lesson chapters use it instead of the former duplicated static oral sketch.

## Acceptance required
1. Compile native Kotlin/JVM tests on the EXACT new commit; verify no WebView/HTML runtime.
2. API34 normal and **actual** Tamil 200% instrumentation: all four buttons accessible, captions untruncated, selected stage/explanation correct; preserve old screenshot threshold/gates.
3. Independent review must check oral vestibule, cytostome, cytopharynx, food vacuole formation, vacuolar digestion and egestion. Cytoproct must remain distinct from contractile-vacuole discharge.
4. Physical-device English/Tamil/TalkBack/voice/rotation/offline review remains NOT RUN.
5. R1.3 graphite anatomical plate biological sign-offs 0/5; Tamil sign-offs 0/5. No release or review status promotion.

Stop if CI fails; repair independently before proposing merge to active native development branch.
