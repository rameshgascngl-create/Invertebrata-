# Unit II — repaired underlying source masters (candidate, not release)
**Branch:** `native/u2-scanned-plate-candidate-20261011`.
**Binary-asset integration commit:** `28d9599215bdd55d0cd20b6d4d39d2ba6f21fcfd`.
**Verification base workflow:** [38162104853](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/38162104853) — Kotlin/JVM PASS, Android API34 PASS, 4/4 UI tests at genuine 100% and 4/4 at genuine 200% font scale. Screenshots came from the *generated asset bytes* before they were committed. This report itself triggers another same-commit build of the committed image hashes.

| Source master | Original SHA-256 | Corrected SHA-256 | Action |
|---|---|---|---|
| sycon_plate.webp | 8a3fae39bbc0c594ce603b3b1270111f45bfe775c53525a56deefc0624bd9c55 | 1b7b8b0ceee848603c24581f60d87866abb03773570f83c43f54665bbb00c05a | Fixed printed opening/canal terms and leaders within WebP pixels |
| obelia_colony.webp | b6f746f2ecd24f714056e8ecf7882aa8f9ac196b86657f5ead2c1fe3cf4eddaa | a4072d35ae5ef51901ffff6dd4d8e8b85caabc172adcea51d179bdf7c5397a93 | Fixed printed gonotheca / gonangium terms within WebP pixels |
| obelia_lifecycle.webp | 2ee1ee163a363d2574b3af294cffde4adabf98b0246a031878e046a034ebadf9 | SAME | Unmodified |

## Kotlin source and coordinate integrity
- Previous Canvas text overprint/masking code REMOVED. Source imagery comes from the corrected `.webp` bytes alone. Native hotspot/selection rings are retained.
- Sycon prospective prosopyle leader endpoint at (584, 742) source pixels; hotspot centre (0.584, 0.594) => (584, 742.5) px, 0.5-pixel error.
- Sycon prospective apopyle leader endpoint at (648, 617) source pixels; hotspot centre (0.648, 0.494) => (648, 617.5) px, 0.5-pixel error.
- These measurements establish geometric co-registration **only**. Identifying an exact microscopic opening on a schematic section requires expert anatomy approval. The image does not resolve every prosopyle on an actual specimen.
- The Obelia gonotheca leader is aligned to the protective sheath; the gonangium leader targets the reproductive zooid near its stalk, but the blastostyle/medusa-bud histology still requires specialist sign-off.

## Acceptance matrix
| Gate | Status |
|---|---|
| Kotlin, JVM, API34 emulator at 100% and 200% | PASS on generated master candidate; same-commit rerun required |
| English and Tamil text snapshots | Captured; expert Tamil editorial signoff PENDING |
| Visible master pixels and no contradictory legacy wording | PASS on candidate screenshot inspection |
| Native touch interaction/stage selection | 4/4 Android tests per scale; pass |
| Pinch zoom, physical phone portrait/landscape, TalkBack, true offline TTS | PHYSICAL DEVICE QA PENDING |
| Independent zoologist verification of leader anatomy, pore topology and life-cycle detail | SCIENCE REVIEW PENDING |
| Release authorization | HOLD |

Preserve R1.9 Paramecium source and validated Unit II A5 unchanged. Never claim CI substitutes for physical device or zoologist sign-off.
