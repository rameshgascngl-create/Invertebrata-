# INVERTEBRATA figure-review decisions — 8a42b096

**Decision type: evidence triage, not academic or rights-holder approval.** Created 2026-11-04 for isolated QA correction branch.
**Production release: HOLD.** No supplied illustration or existing credit was deleted, replaced or reclassified.

## Group decisions

- **38 review-only assets — EXCLUDE from eventual production release** until approved. They remain intact in QA while reviewed; no archive assets were deleted by this commit.
- **5 current Unit I reference binaries — BLOCK RIGHTS SIGN-OFF**: original provenance records describe different bytes. Identify the current source and licence for each exact binary before acceptance.
- **55 images described as AI-generated in existing credit records — NOT APPROVED AUTOMATICALLY**: retain current words for now, but establish generation provenance, the creator/rights-holder declaration, permitted reuse, and exact asset identity. The label alone is not proof of rights. 28 correspond to straightforward packaged file paths; 27 require mapping checks.
- **29 logical credit entries without a matching direct file at the expected location — MAP ASSET PATHS**: some may be referenced through other loaders; this is not proof of missing illustrations.
- **3 provisional Paramecium images — HOLD** for separate science, Tamil and rights review.
- **Existing historical and third-party source-credited plates — PRESERVE** current credits, verify each against the exact modified/resized asset and the source page; do not change credits to AI-generated on assumption.

## Public source records checked

- `sycon/external`: Wikimedia Commons page **Sycon ciliatum 29886028.jpg** records creator **Julien Renoult**, licence **CC BY 4.0**, original photo dated 2018-12-29. https://commons.wikimedia.org/wiki/File:Sycon_ciliatum_29886028.jpg . **Decision: SOURCE PAGE VERIFIED ONLY; packaged WebP binary derivation and credit completeness remain pending**.
- `sycon/reproduction`: Wikimedia Commons page **EB1911 Sponges - Types of Sponge Larvae.jpg** records **Encyclopaedia Britannica (1911), after E. A. Minchin**, and marks the historical source **public domain in the United States**. https://commons.wikimedia.org/wiki/File:EB1911_Sponges_-_Types_of_Sponge_Larvae.jpg . **Decision: SOURCE PAGE VERIFIED ONLY; packaged WebP match and jurisdictional licensing review pending**.
- `obelia/medusa`: existing credit refers to `File:Obelia Medusa Larva.jpg`, but publicly found pages have differing titles (`Obelia Medusae.jpg` and `Obelia medusa lava.jpg`). **Decision: AMBIGUOUS REFERENCE, HOLD**; do not guess which creator/licence applies to the embedded artwork.

## Required signatures (all absent)

Each accepted figure requires: APK asset path; SHA-256; source image URL + source identity; creator; licence/permission with modification terms; exact in-app attribution; independent biological reviewer; Tamil scientific-label reviewer; rights reviewer; final accepted decision. The separate 113-row ledger in the QA packet is the review working list; **none is approved**.

No editor or rights reviewer was impersonated; no figure was marked ACCEPTED. This document records technical triage decisions and identified source evidence only.
