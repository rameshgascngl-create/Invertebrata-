# Unit V sixteen-figure triage — evidence from existing Kotlin captions

Source: native-app/app/src/main/java/com/gasczoology/invertebratelab/data/UnitVExistingFigureCatalog.kt on U63 branch. These are preliminary classifications from pre-existing source annotations, NOT independent pixel inspection. No file is approved for production by this document.

| Figure | Existing documented issue | Triage | Review needed |
|---|---|---|---|
| Pila 01 shell ventral | orientation and labels unverified | REVIEW | compare specimen orientation |
| Pila 02 operculum | surfaces and growth markings unverified | REVIEW | compare operculum surfaces |
| Pila 03 shell dorsal | spire, sutures, whorl orientation unverified | REVIEW | compare shell specimen |
| Pila 04 animal front | eye and tentacle placement suspect | REVIEW | assess placement; replace only if fundamental |
| Pila 05 nervous | ganglia and connections unverified | REVIEW | check neural topology |
| Pila 06 buccal mass | spelling and leader lines | CORRECT_CANDIDATE | verify radula anatomy before minor correction |
| Pila 07 digestive | crop, stomach, intestinal connections unverified | REVIEW | check gut continuity |
| Pila 08 respiratory | duplicated mantle-cavity label, air/water paths unverified | CORRECT_CANDIDATE | fix duplication if respiratory topology correct |
| Pila 09 circulatory | auricle, ventricle, arteries and haemocoel unverified | REVIEW | check circulation |
| Pila 10 male reproductive | ducts and organ proportions unverified | REVIEW | check duct continuity |
| Pila 11 female reproductive | duplicated spermathecal labels, organ placement uncertain | CORRECT_CANDIDATE | correct duplication if anatomy valid |
| Pila 12 excretory | renal opening, nephrostome, pericardium unverified | REVIEW | check excretory connections |
| Asterias external | bitmap depicts opened internal organs, not intact external specimen | RECLASSIFY_CANDIDATE | retain as internal only if accurate; external plate remains missing |
| Asterias water vascular | orientation and stone canal relationship incorrect or ambiguous | MAJOR_REVIEW | check entire water-vascular topology |
| Asterias digestive | conflates cardiac and pyloric stomach | MAJOR_REVIEW | inspect inset and possibility of correcting it |
| Asterias nervous | circumoral/radial relations and endpoints unverified | REVIEW | check nerves and endpoints |

CORRECT_CANDIDATE does not mean corrected. MAJOR_REVIEW does not mean automatically replace. REVIEW means no verdict. Rights review and specimen-specific anatomical evidence are required before restoring any debug-archived bitmap to main assets.

Next evidence: inspect original 16 PNG pixels and original source/licences, assign KEEP/CORRECT/REPLACE/RIGHTS_HOLD individually; then make the smallest safe source change and validate the exact commit in CI. Production HOLD.
