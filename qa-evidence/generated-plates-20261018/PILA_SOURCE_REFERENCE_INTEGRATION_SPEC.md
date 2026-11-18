# Pila globosa — reference-controlled integration specification

**Decision:** Source anatomy is usable for editorial comparison, but reproduction rights are unknown. Do not ship the user-provided textbook image or its upscaled crops in a publicly distributed APK without permission or a confirmed license.

## Source image
- User-provided Pila globosa textbook montage, four anatomical views
- Four crops in conversation artifact `pila_four_reference_views.zip`; not committed as app assets
- Resampling: 2× Lanczos; no reconstructed anatomical information
- Source and edition: not established; verify before publishing

## Plate-by-plate audit

| Reference view | Scientifically relevant features | Proposed app presentation | Gate |
| --- | --- | --- | --- |
| Shell ventral | Apex, penultimate whorl, suture, varix, body whorl, umbilicus, columellar lip, outer lip, aperture | One external-shell view within `pila/external` only after a rights-cleared master is prepared | HOLD: rights and crop-edge/leader audit |
| Operculum outer/inner | Concentric growth lines and nucleus (outer); shallow groove and attachment boss (inner) | Separate supplementary operculum figure within external-morphology lesson; **not** a second copy of `pila/external` | HOLD: rights, image clarity |
| Shell dorsal | Columella and spire visible from alternate aspect | Supplementary comparison view; not an independent mandatory type-study slot | HOLD: rights and orientation review |
| Front view without shell | Visceral mass, mantle, head, eye, first and second tentacles, paired pseudepipodia, foot and operculum | External morphology comparison only; do not infer anatomy absent from the original | HOLD: source quality, rights and anatomy |

## Correction to prior generated images
- Reject generated four-tentacle eyestalk compositions as substitutes for the supplied front view.
- Reject generated mantle cavity drawings with paired pipe-like siphons as an unverified depiction of nuchal lobes.
- Do not represent a gill with lamellae on both sides of the axis as monopectinate.
- Do not combine nervous, vascular, reproductive and digestive systems in one purportedly verified internal plate.
- Never burn uncertain Tamil labels into bitmap pixels; native UI text remains separately editable.

## Engineering integration rule

The native gallery currently uses **one** raster stem for each required plate ID. `pila/external` is a single mandatory slot. Four cropped reference views must not be inserted as four duplicate replacements for that slot, nor renamed to fill `pila/mantle-cavity`, `pila/ctenidium`, or `pila/torsion`.

**Publication path:** identify rights-cleared source → compare pixel geometry and leader endpoints → independent zoological review → Tamil editorial review → prepare one appropriately composed, full-resolution `pila/external.png` master and optional distinctly named supplementary images → check SHA-256 duplicate inventory → compile and screenshot-test exact commit → physical-device QA.

**Current result:** no new Pila PNG committed; release remains on academic hold.
