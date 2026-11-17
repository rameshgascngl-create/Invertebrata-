# Tamil editorial release gate — finite acceptance contract (2026-11-17)

## Source of truth
- Immutable engineering baseline: `f755dd6cbf0f04972ea8af203b91e560c819a068`.
- Editorial candidate branch: `audit/tamil-terminology-completion-20261117`.
- Compare result at preparation: **10 commits ahead, 0 behind; 9 modified Kotlin chapter files**. Do not discard those changes by reverting to the baseline.
- The 43-row register `docs/tamil-editorial-register-20261115.md` records targeted review, **not** completion of 43 full bilingual reviews.
- Existing QA APK at the engineering baseline does **not** contain later editorial commits.

## Blocking defects — objective evidence required
1. Demonstrably false biological meaning or wrong anatomical relationship, stage, host, mechanism, number or scientific classification.
2. Missing essential Tamil explanation or contradictory English–Tamil statement.
3. Grammar or wording that changes meaning or makes the passage materially ambiguous.
4. Confirmed malformed or superseded scientific term on the approved project terminology contract.
5. Missing or broken Tamil assessment answer, caption or UI instruction that prevents comprehension or operation.
6. Failed Kotlin compilation, relevant JVM regression, Android rendering/accessibility or emulator acceptance.

## Non-blocking issues
- Stylistic preferences, synonymous scientifically correct expressions, minor sentence restructuring, and optional enrichment.
- Independent 52-plate illustration evidence/approval is tracked in the illustration QA process, not represented as completed by Tamil validation.

## Controlled terminology
- Water-vascular system: `நீரோட்டக் குழாய் மண்டலம்` (project's existing accepted/tested convention).
- Dendrobranchiate gills: `கிளைபிரிந்த செவுள்கள்` (retain the correct branching anatomy).
- Cilia and flagella must remain anatomically distinct: `குறுஇழைகள்` and `கசையிழைகள்` in the current chapter convention.
- Equivalent expressions are acceptable where they do not alter meaning; do not modify tests merely to silence a genuine scientific error.

## One-time release acceptance procedure
1. Review the **9 changed files** against baseline; retain meaning-changing and scientifically justified edits; do not reopen other accepted chapters without a reproducible defect.
2. Run existing `WholeBookTamilTerminologyAuditTest` and `EchinodermTamilTerminologyTest` plus the full foundation and five-unit JVM suites against the **same candidate SHA**.
3. Verify chapter/assessment/UI Tamil coverage and English–Tamil parity using an explicit source inventory; record any unexamined source as **NOT VERIFIED**, never PASS.
4. Run Android API 34/35 emulator acceptance, English and Tamil at 100% and Tamil at 200% including rotation and reader pagination. Record job links and results.
5. Record exact commit SHA, build artifact, integrity/hash, and independent illustration blockers separately.
6. Set status **INTERNALLY VALIDATED — FROZEN** only after all blocking checks have recorded PASS. Otherwise **CANDIDATE — NOT FROZEN**. Later changes require a documented, reproducible blocking defect.

## Current evidence and status
- Source comparison: PASS (10 commits ahead, 0 behind, 9 chapter files modified).
- Editorial commit reconciliation: PENDING.
- Complete Tamil coverage / English–Tamil parity: NOT VERIFIED.
- Kotlin/JVM tests at editorial HEAD: NOT RUN / NOT VERIFIED.
- Emulator tests at editorial HEAD: NOT RUN / NOT VERIFIED.
- Independent illustration review: SEPARATE / PENDING.
- **Release decision: CANDIDATE — NOT FROZEN.** No claim of editorial certification or final APK readiness.
