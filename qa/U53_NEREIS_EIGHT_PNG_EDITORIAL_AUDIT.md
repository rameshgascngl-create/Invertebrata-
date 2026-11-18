# U5.3 Nereis eight-image academic review — exact-byte intake, acceptance HOLD

Date: 2026-10-17. This report concerns the eight `1122 × 1402` PNG assets captured in the source integration package. They are generated pencil-style raster draft images, **not authenticated hand-drawn pencil scans**.

## Bytes and software
- Eight original image files independently match the checksum manifest in `.github/scripts/verify-nereis-png-masters.py`.
- Integration intake ZIP is 16,974,812 bytes with SHA-256 `f552a6156fc09343700d2a12e534b2c5b17e499924b1c09f3b954c48c5325250`.
- GitHub branch currently has the Nereis image-only loader but is missing all eight binary PNGs, pending upload into `qa-intake/`.
- Exact-byte upload is not a substitute for inspecting the actual rendered drawing on Android.

## Zoological and Tamil editorial checkpoint
| Plate | Features for independent biological verification |
| --- | --- |
| 01 External morphology | Prostomium, peristomium, segmental parapodia/chaetae, pygidium/anal cirri; inspect the enlarged head and parapodium inset leader endpoints |
| 02 Enlarged head | Two pairs of eyes, antennae, palps, tentacular cirri, peristomium, nuchal-organ labeling; verify all endpoints |
| 03 Biramous parapodium | Notopodium, neuropodium, ligules, cirri, acicula and chaetal bundles; verify structural orientation |
| 04 Digestive system | Eversible pharynx and jaws versus remaining alimentary tract; verify pharyngeal boundaries and labels |
| 05 Circulatory system | Review claimed circumesophageal, intestinal and parapodial vessel arrangements against an authoritative *Nereis* source |
| 06 Nervous system | Cerebral ganglion, circumpharyngeal connectives, subpharyngeal ganglion, ventral nerve cord and segmental branches |
| 07 Excretory system | Nephrostome, nephridial tubule, pore, segment relation, and especially the plate's 'bladder' label |
| 08 Trochophore | Apical tuft, prototroch, telotroch, gut and anus; confirm stage-specific morphology |

**Tamil editorial concern:** Image-embedded labels cannot be corrected, searched or enlarged using Android font scaling. The separate Compose text labels help accessibility but **do not repair inaccurate printed leader lines**. Do not approve until both image-pixel labels and bilingual native terms agree with the college practical manual.

## Release gate
1. Integrate **exact original PNG bytes**; never create Canvas/vector replacements or weaken hashes.
2. Run exact-commit CI and view all eight Android raster captures in English/Tamil at 100% and 200% font scale.
3. Obtain anatomy/source-rights and English/Tamil editorial sign-off.
4. Complete physical Android-device QA.

Current decision: **SOURCE-PNG BYTES VERIFIED / REPOSITORY IMAGE UPLOAD PENDING / ACADEMIC ACCEPTANCE HOLD**.
