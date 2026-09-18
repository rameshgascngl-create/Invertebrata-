# INVERTEBRATA v1.8.8 — Contextual enlargement routing supersession

## Decision

**CONTEXTUAL FIGURE ENLARGEMENT ROUTING DEFECT — VALIDATED REPLACEMENT PROMOTED**

This record supersedes only the event-routing scope inside reconstruction Step 2. It is not an academic illustration correction.

## Governing transformation

- Step: **2**
- Path: `ci/reconcile_v188.py`
- Previous governing commit: `b1d5278339e45e6b359d4d3d60b467f12bd0d95d`
- Previous Git blob: `a13c43a4634847e155f50abc2f1ef1ca63753c5d`
- Corrected governing commit on `main`: `e59f1b67a37a92f8ffa17094cfc2eb151eae2ea3`
- Corrected Git blob: `a6c27042df66a728b320db8d9c26fce660ebc669`
- Manifest pointer update commit: `f3025427eca9d6b6bb497dfa9bc5280bab7e3968`

## Proven defect

The existing application uses a stable root-level delegated click handler for theory figures, but its generated-figure selector was ancestry-dependent:

`event.target.closest('.textbook-visuals .sys-fig')`

Step 2 also introduces contextual placement that adds `contextual-theory-figure` and physically relocates the same `.sys-fig` out of `.textbook-visuals` using `insertAdjacentElement('afterend', fig)`.

After relocation, the instructional figure remained a `.sys-fig` but no longer satisfied the enlargement selector. The contextual keyboard handler invokes `f.click()`, so Enter/Space inherited the same routing failure.

**Supersession reason: CONTEXTUAL FIGURE RELOCATION INVALIDATED ENLARGEMENT SELECTOR SCOPE.**

## Corrected invariant

The root-level delegated handler now follows semantic instructional eligibility:

`event.target.closest('.sys-fig')`

The stable ancestor delegation is unchanged. Contextual relocation is unchanged. The existing enlargement function and overlay are unchanged.

This means the same instructional figure remains enlargeable whether it is still inside `.textbook-visuals` or has been moved beside the relevant theory subsection.

## Scope boundary

The correction does not modify:

- SVG paths or geometry
- anatomical labels
- leader lines
- plate IDs
- captions
- academic prose
- organism/chapter mappings
- contextual destination scoring
- `insertAdjacentElement` placement logic
- overlay design
- Android wrapper
- Gradle configuration
- applicationId/package identity
- versionName/versionCode
- release workflow

**ACADEMIC FIGURE CONTENT CHANGED — NO**

**CONTEXTUAL PLACEMENT LOGIC CHANGED — NO**

## Targeted validation before promotion

The exact corrected 19-step candidate was reconstructed from the canonical source on audit branch `audit/contextual-enlargement-routing-20260918`.

Candidate reconstruction Actions run: `35371537059`

Candidate reconstructed HTML SHA-256: `11d966f72bd03d86c4c3efd193e8e148db92e150e4f03f1fe8e70782e4087dd6`

At a 360 × 800 CSS-px Chromium viewport the following contextual figures all passed pointer, Enter and Space enlargement with zero horizontal overflow and correct overlay content:

- `master-sycon-canal`
- `earthworm-external-r2`
- `PENAEUS-01-R1`
- `PARAMECIUM-CV-CILIA-N1`

Additional routing regressions passed:

- instructional `.sys-fig` while inside `.textbook-visuals` enlarges;
- the same figure after contextual relocation enlarges;
- repeated chapter navigation still inserts exactly one zoom-media node;
- an SVG outside the `.sys-fig` instructional system does not acquire theory enlargement behavior.

## Authoritative-chain state

The accepted transformation count remains exactly **19**. Step order remains 1–19. The previous Step-2 governing commit is recorded as superseded and is not executable.

Because an authoritative transformation changed, the former reconstructed source SHA-256

`af3f5d80302e4e5e853bdce71451785619a535fde74e6996106b9ce83d21a3ad`

is historical evidence only and must not identify the corrected source.

## Next gate

Restart deterministic reconstruction from the canonical ZIP and require fresh Run A + independent Run B, Matrix 02 76/76, SVG/source integrity, and exact SHA-256 equality before repeating the complete contextual render audit.

No Android build is authorized by this correction.
