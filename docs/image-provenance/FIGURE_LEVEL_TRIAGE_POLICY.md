# Image triage decision record — retain, correct, replace, rights hold

Applies to all 43 chapters, Units I–V. Existing committed images are presumptively retained, not presumptively rejected. The presence of a quarantined asset does not establish a scientific defect.

| Classification | Criteria | Action |
|---|---|---|
| KEEP | Anatomy and chronology sound; minor stylistic imperfections or readable blur | Keep existing image and record review |
| CORRECT | Typographical error, minor leader-line displacement, small remediable labelling issue | Correct affected label/endpoint, preserve source and track modification |
| REPLACE | Fundamental topology wrong, wrong organism, major structure missing or misleading developmental chronology; cannot be reliably corrected | Seek authoritative replacement with documented legal reuse rights and scientific review |
| RIGHTS_HOLD | Reuse licence or ownership unresolved | Do not distribute until permission/licence verified, regardless of anatomy |
| REVIEW | Evidence insufficient for one of the above decisions | Preserve in review archive; do not claim approved or defective |

## Review record required per figure
Chapter ID; existing asset path and SHA-256; target concept; observed issue; anatomical reference and page/figure; reviewer; severity; classification; exact proposed correction; source URL and creator; rights holder/licence and modification obligations; English/Tamil label checks; zoom legibility; approved replacement asset SHA-256 (if any).

## Unit V reconsideration
The 12 Pila and 4 Asterias PNGs moved to debug assets by commit 3f73f418 must be re-examined individually. Their previous quarantine is a provisional rights/science hold, NOT evidence that all sixteen have major biological errors. Restore any individually approved images through a separately reviewed commit; never bulk-restore or bulk-replace without a figure-level record.

## Release gates
No image is accepted based only on file existence or green CI. Check exact-commit build, APK packaged assets, offline screenshots at English/Tamil 100% and 200%, zoom, rights review and physical-device QA. Preserve 43-chapter structure. No Canvas fallback.
