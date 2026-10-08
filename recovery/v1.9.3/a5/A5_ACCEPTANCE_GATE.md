# INVERTEBRATA v1.9.3 — A5 acceptance gate

**A5 scientific audit: PASS — 86/86**

- Branch: `recovery/v193-academic-a5-20261008`
- U1: 14/14 PASS — commit `0086b35e360803be823599c1f79aa0a5c1ea33f9`
- U2: 18/18 PASS — commit `07fb65f737ce4033c024c91a4267a90e62a976ba`
- U3: 14/14 PASS — commit `ebfbec524c2032e33b9d0a1d985d4eb01e9a8587`
- U4: 16/16 PASS — commit `850fe16370baa37e18b37d7870bbf6259edf9ee5`
- U5: 24/24 PASS — commit `2cc85d0bd723ca587cc8395e9266de6d2c2024c3`

## Deterministic checks
- 43 chapter mappings: PASS
- 86 question IDs: unique
- Required English/Tamil q5/a5 fields: complete
- Five English answer points per A5 question: PASS
- Five Tamil answer points per A5 question: PASS
- Supporting reference on every validation row: PASS
- Final validation states: 86 PASS / 0 FAIL / 0 BLOCKED
- Original scientific verdicts: 47 PASS / 39 FAIL / 0 BLOCKED
- Scientific failures corrected: 39
- Tamil review: 86/86; 81 required correction, 5 were already acceptable

## Scope boundary
This gate certifies the **validated A5 corpus committed under `recovery/v1.9.3/a5/`**. It does **not** claim that the corrected bank has already been merged into the application academic payload. P0/P1 integration, CI, Android packaging and release/device acceptance remain separate gates.
