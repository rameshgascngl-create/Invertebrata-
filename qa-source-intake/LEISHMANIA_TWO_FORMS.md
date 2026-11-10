# Leishmania morphology source replacement (QA only)

The previous image displays the amastigote only. This sourced figure puts two separately licensed drawings from **Servier Medical Art** into one comparison plate:
- Amastigote: https://commons.wikimedia.org/wiki/File:Leishmania_amastigote.png
- Promastigote: https://commons.wikimedia.org/wiki/File:Leishmania_promastigote.png

**Both are CC BY 2.0 (author Servier Medical Art, 2013)**. The PNG composite has no AI redrawing. It uniformly resizes the original artwork, places the two forms side-by-side, adds a non-anatomical header and two short stage/host descriptions. The automatic intake artifact contains LEISHMANIA_PROVENANCE.json with SHA-256 hashes for both exact source files and the composite PNG.

**Android integration:** The textbook's existing lib/leishmania-amastigote figure slot shows the two-form plate and its complete source credit only in debuggable QA builds when the staged PNG is available. Other builds retain their previous amastigote-only figure and disclosure. No production image bytes, provenance or release-review gate is modified. The 41 previously green-ticked images remain intact.

**Editorial limitation:** Morphology comparison, not a microscopic field, life-cycle timescale or measured relative size; the figure is explicitly not to scale. In-app Tamil note is outside the image for readable wrapping.
