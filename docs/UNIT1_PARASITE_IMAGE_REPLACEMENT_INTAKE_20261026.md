# Unit I parasite-image replacement intake (26 October 2026)

Status: SOURCE CANDIDATES ONLY — NO APK IMAGE REPLACEMENT, NO ACADEMIC ACCEPTANCE.

## Purpose
Replace cartoon-like parasite references shown in real-device screenshots with high-resolution, species-appropriate microscopy or carefully verified scientific plates. Do not use the AI-generated six-panel montage: its labels and provenance are unverified.

## Verified source candidates
1. **Leishmania amastigotes in host tissue** — CDC DPDx: https://www.cdc.gov/dpdx/leishmaniasis/ . The diagnostic image caption for Leish_amast_WBC1_DPDx.jpg describes an intact macrophage filled with amastigotes with visible nuclei and kinetoplasts. Also CDC PHIL #141: https://wwwn.cdc.gov/phil/details_linked.aspx?pid=141 (public domain; high-resolution download unavailable). Alternative high-resolution CDC PHIL #468: https://phil.cdc.gov/details.aspx?pid=468 (Leishmania donovani in bone-marrow smear, 6.56 MB, public domain; not necessarily an intact macrophage).
2. **Trypanosomes in human blood smear** — CDC PHIL #613: https://wwwn.cdc.gov/phil/Details.aspx?pid=613 (17.1 MB high-resolution, CDC/Dr. Myron G. Schultz, public domain with CDC attribution). Image is identified as Trypanosoma sp. in African trypanosomiasis, NOT definitively species-level T. brucei by morphology. Use caption accordingly. CDC PHIL #21026 https://wwwn.cdc.gov/phil/Details.aspx?pid=21026 shows flagellum and undulating membrane; species not established.
3. **Leishmania life cycle** — CDC PHIL #3400: https://phil.cdc.gov/details.aspx?pid=3400 (22.71 MB, CDC/Alexander J. da Silva, public domain). This is a separate concept plate, not a substitute for intracellular amastigote morphology.

CDC DPDx states that most reference/training material is free of copyright but lists exceptions; verify each selected image's specific terms: https://www.cdc.gov/dpdx/about.html .

## Asset integration requirements
- Inspect original high-resolution pixels, dimensions and actual diagnostic features before download/approval.
- Maintain original bytes in provenance with source URL, provider, creator, rights, retrieval date and SHA-256. Avoid unverified or invented credits.
- Produce separate portrait-friendly PNG plates. Any annotations must point to genuine structures and must be reviewed in English and Tamil.
- Preserve the existing image files until replacements have passed biological, rights, and Android display QA. Do not restore the removed photographed Paramecium pencil plates.
- Unit I references currently reside under native-app/app/src/main/assets/unit1/references/; inspect Kotlin binding before replacing.
- For Entamoeba, Fasciola, Ascaris and nematode transmission, collect and validate separate source candidates; none are approved by this intake.
- Device QA: normal and 200% Tamil fonts, zoom, caption visibility, navigation-bar overlap, image legibility and reader scroll reset.
- The 43-chapter A5 curriculum invariant is unchanged. Scientific, Tamil, source-rights and physical-device release holds remain.
