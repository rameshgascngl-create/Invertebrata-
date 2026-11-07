# Content v3 mapping review — preliminary

Branch: qa/content-v3-intake-20261107
Input: invertebrata_content_final.json (uploaded at de15cb93087af2311e5cd3347d2f19a50b20a039)

## Structural findings (read-only)
- 10 phylum entries and 60 bilingual multiple-choice questions.
- Every question inspected has four English options, four Tamil options, a zero-based correct-answer index in 0..3, and English/Tamil explanations.
- Phylum fields are heterogeneous: classes is present for Cnidaria, Arthropoda, Mollusca and Echinodermata, but absent for the other six entries; exam_tip and common_name fields are also optional.
- The data has no stable links to the validated 43 A5 chapter IDs. Do not replace the existing lesson store or infer chapter IDs from phylum IDs.
- The uploaded content is not a native Kotlin/Compose source patch. A source archive exists in the repository root; do not assume that the archive matches the more recent native QA branches.
- Structural validation does not imply factual accuracy, translation quality, rights clearance, or build success.

## Required mapping contract
1. Keep content v3 as a separate candidate namespace until independently reviewed.
2. Build an explicit chapter-to-content mapping table; reject unknown chapter IDs and duplicate bindings.
3. Optional phylum metadata must be nullable and handled explicitly in Kotlin.
4. Quiz answer indices remain zero-based; keep English and Tamil option order identical.
5. Do not alter existing PNG plates or simulator image behaviour.
6. Compile and run instrumented tests from the actual native app baseline before publishing any APK.

## CI evidence
No workflow runs were returned by the connected commit-specific workflow lookup for 2c1416d3021ecc1a0b25aa14a0fdfe2eb18aff63. This lookup is limited to pull-request-triggered runs; absence is not proof that no push-triggered run exists.

Disposition: REVIEW ONLY. APK generation and production acceptance remain blocked.
