# Adding anatomical plates without breaking the build

A plate is one image file. Nothing else needs editing unless you add a *new* slot.

## 1. See what is missing
```
python tools/plate_intake.py list
```
Prints all 35 required slots, which exist, their format and size.

## 2. Add an image (any PNG/JPG/WebP, any size)
```
python tools/plate_intake.py add --organism pila --plate ctenidium --src my_drawing.png \
    --license "CC0 1.0" --author "Name" --source-url "https://..." --ai-generated no
```
For AI-generated art use `--ai-generated yes --generator "ChatGPT image model"`.
The tool: converts to lossless WebP, flattens transparency to white, caps the longest
edge at 2560 px, rejects unknown slot names, rejects bytes already used by another slot,
refuses to keep both `.png` and `.webp`, and records provenance in `provenance/plates.json`.

## 3. Check before you push
```
python tools/plate_intake.py check
```
These are the same rules the Gradle test `PackagedPlateInventoryTest` enforces.

## Why builds used to fail
| Symptom | Cause | Now |
|---|---|---|
| Test: "Two incompatible raster formats compete for ..." | `.png` and `.webp` both present | tool keeps one |
| Test: "Repeated anatomical bitmap used in distinct slots" | same image bytes reused for two slots | tool refuses, test names both slots |
| Plate never appears, no error | wrong name/case (`Locomotion.PNG`, `pila-ctenidium.png`) | `check` and the test name the file and list valid ids |
| Red "image not available" box on a file that exists | very large image decoded at full size (OutOfMemory) | decoder sub-samples to 2560 px and retries smaller |
| Janks or blank on first open | decoded on main thread every recomposition | decoded once, cached (LRU) |

## Rules the tests still enforce (intentional)
* Slot ids come from `NativeCurriculumManifest.typeStudies` (35 slots). A new slot also needs a bilingual title in `RestoredPlateTitles`.
* Longest edge <= 4096 px, shortest >= 256 px.
* Passing = well-formed and correctly named. It is **not** scientific, Tamil or rights approval; `review_status` stays `DRAFT_UNVERIFIED` until you change it.

## Labels
Do not rely on text inside AI-generated rasters (Tamil glyphs and spellings are frequently wrong). Prefer unlabelled art with numbered markers, and supply the bilingual legend from app strings.
