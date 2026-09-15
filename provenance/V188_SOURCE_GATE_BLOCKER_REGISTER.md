# INVERTEBRATA v1.8.8 — Source-Gate Blocker Register

This register supplements, and does not overwrite or weaken, the audit controls established by commits `a0ff3e4ad2d37df88dfed718d7e6adf90430b848` and `f799549dbcee684168c509ef666be25108bb9a39`.

No APK build is authorised by this register. The existing manual-dispatch workflow is intentionally left untouched.

## BUILD PIPELINE RECONCILIATION REQUIRED

**State: OPEN — BLOCKING BUILD ELIGIBILITY**

The current manual v1.8.8 workflow reconstructs an earlier source state and does not presently demonstrate application of the complete subsequently accepted illustration-integration chain. Therefore a successful per-figure/source audit alone cannot make the repository build-eligible.

When, and only when, the illustration/source gate has passed, the build pipeline must be reconciled to the exact accepted source state before manual dispatch. That reconciliation must itself be audited so that approved figures, audit overlays, terminology corrections, and targeted remediation cannot be silently omitted from the produced source tree.

Required future evidence before build eligibility:

1. exact accepted source/illustration commit anchor;
2. deterministic ordered reconciliation chain from frozen source to that accepted state;
3. proof that every accepted figure/integration stage is included exactly once;
4. proof that no superseded historical/+1 page mapping or rejected plate is re-promoted by the build reconstruction;
5. proof that the manual workflow remains manual-dispatch only;
6. source-manifest/hash evidence for the reconstructed tree;
7. independent review of the pipeline reconciliation before any manual workflow dispatch.

Until those conditions are satisfied:

`COMPLETE SOURCE AUDIT -> BUILD PIPELINE RECONCILIATION REQUIRED -> BUILD ELIGIBLE`.

Do not infer Android WebView, installation, Android Back, physical zoom/pan, offline-device, PackageManager, or DEVICE QA results from source-level evidence.

## Frozen Asterias reference precision

For the Asterias water-vascular-system plate, the authoritative **visible** reference is **PDF p.44**. Unavailable/non-evidential p.45 must not be retained as visible supporting evidence merely because older mappings used `pp.44–45` or historical approximate numbering.

## Current gate

`PER-FIGURE AUDIT ACTIVE -> TARGETED REMEDIATION -> COMPLETE SOURCE AUDIT -> BUILD PIPELINE RECONCILIATION REQUIRED -> BUILD ELIGIBLE -> MANUAL BUILD -> DEVICE QA -> FINAL`

BUILD remains BLOCKED.