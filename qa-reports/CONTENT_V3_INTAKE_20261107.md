# INVERTEBRATA content v3.0 intake — NOT APPROVED FOR APK

Source commit: de15cb93087af2311e5cd3347d2f19a50b20a039
Source file: invertebrata_content_final.json
Intake branch: qa/content-v3-intake-20261107

## Observed structure
- 10 phylum entries: Porifera, Cnidaria, Ctenophora, Platyhelminthes, Aschelminthes, Annelida, Arthropoda, Mollusca, Echinodermata, Hemichordata.
- 60 quiz questions.
- Language metadata: en and ta.
- Version metadata: 3.0.
- The file's own audit_status is 'corrected_and_expanded'; this is not independent scientific or Tamil editorial approval.

## Integration gates (all pending)
1. Inspect invertebrata_update_package.tar.gz safely: archive manifest, file paths, symlinks, file sizes and checksums; never execute bundled scripts blindly.
2. Identify the actual native Kotlin/Compose Android application source and its latest accepted baseline. Do not treat this JSON-only upload as a compiled Android project.
3. Compare all content against the existing 43 validated A5 chapters by stable chapter IDs; never overwrite, remove or reorder those chapters automatically.
4. Verify phylum classification, species examples, factual claims and answer keys against authoritative zoology references.
5. Independently review Tamil terminology against Tamil Nadu Biology/Zoology textbook conventions; keep English and Tamil page content separate.
6. Preserve all approved PNG assets and attribution records. Do not introduce Canvas, vector sketches or unrelated placeholder diagrams.
7. Build on a separate native QA integration branch, compile, run JVM and offline API34 instrumentation tests, and inspect Tamil 100%/200% screenshots.
8. Only after successful checks publish a debug QA APK with exact commit, workflow run, artifact ID, byte size and SHA-256.
9. Production release remains blocked on independent zoology, Tamil, source-rights and physical-device acceptance.

## Current disposition
INTAKE ONLY. No assertion of native integration, compilation, APK generation, scientific acceptance or classroom readiness.
