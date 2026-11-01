# Uploaded plate candidates integrated on 2026-10-26 (commit 4a1d08a1)

**Status: UNREVIEWED CANDIDATES. Source, author and licence are NOT recorded for the bytes
now bundled.** Commit message: "Integrate uploaded parasite plates into Android asset paths
(review candidates)". It changed six PNGs and added no provenance or checksum-gate entries.

Owner decision (2026-11-01): the immutable masters stay authoritative; every plate below goes
through review with recorded checksums and sources before it is treated as approved.

| Asset (under native-app/app/src/main/assets/) | Before 4a1d08a1 | Bundled now | Checksum gate |
|---|---|---|---|
| `type_studies/ascaris/male-female-external.png` | `d8df8c8eb4b79e9186017650d818060b756a302968eb4d73c4329e88d73908c9` (896,890 B) | `9aeb7e460c32d50fd952abef21df120945a76afab1a30815a4b384c9a538e5db` (1,732,052 B) | **pinned, currently failing** (native-five-unit-restoration.yml:141) |
| `type_studies/ascaris/fertilized-egg.png` | added by this commit | `3e23d40ae1f357ad39d1e1d5e72cd4a00e8e6ca1f542f58d26b507a90f675575` (2,224,270 B) | duplicate-hash checks only |
| `unit1/references/entamoeba.png` | `fd9cd3a0f2eb0dab0e656c340681d49908d65038407fca2db085c36f8c3a6a77` (631,444 B) | `4514e419bfdd4d37dce87d399c1e5229c5b47cc771a3c9d8d37fa561335c8a20` (2,314,428 B) | duplicate-hash checks only |
| `unit1/references/entamoeba_mature_cyst.png` | added by this commit | `6dbe9a4db594b8e31f3aadf4bec9991de04f9eb0051b1c62227756efa42b87a9` (2,263,541 B) | duplicate-hash checks only |
| `unit1/references/leishmania.png` | `e607e452cf4385dcb00e026ffe885dd819efe2e32be1c31d8ec52b9a6a8f73af` (87,155 B) | `cf026d3eaa0388a0af53731e76e8dd8be6c206a6affae5f4c9fdea1047b2604b` (1,882,683 B) | duplicate-hash checks only |
| `unit1/references/trypanosoma.png` | `22ecff656a16b734aa9e33193d54a6ea2f347322af983a079ff615052800623b` (55,965 B) | `f684d22810415c00820010d9c843e2f83a6b26839e3f5dd8db032325f0c7c133` (1,523,212 B) | duplicate-hash checks only |

## What is known and not known
- The root-level `ascaris_male-female-external.png` is byte-identical to the bundled Ascaris plate,
  so that plate came from an upload for review, not from the archived master.
- `unit1/references/SOURCE_PROVENANCE.txt` still records Wikimedia Commons hashes for Entamoeba,
  Trypanosoma and Leishmania (1f21e55e..., 3b81ee21..., cb19231f...) that no longer match the
  bundled files. Those attributions (public domain, CC BY-SA 3.0, CC BY 4.0 as recorded there)
  cannot be assumed to cover the replacement bytes.
- Even the files that existed BEFORE 4a1d08a1 do not match the hashes in SOURCE_PROVENANCE.txt
  (for example Entamoeba before: `fd9cd3a0...`, recorded: `1f21e55e...`), so that record was already
  stale and cannot be used as proof of source for either version.
- No source URL, author, licence, or reviewer is recorded for any of the six bundled files.

## To close each item
1. Record author, source URL and licence for the bundled bytes (or restore the master).
2. Name the scientific and Tamil reviewers and the date.
3. Pin the approved SHA-256 in the relevant gate and in `provenance/plates.json`.

## Restoring the Ascaris master (binary file; run in a clone, then push)
```
git checkout 166ab2ec -- native-app/app/src/main/assets/type_studies/ascaris/male-female-external.png
git commit -m "Restore Ascaris master pending review of candidate plate"
```
This makes the pinned gate pass again (`d8df8c8e...`). The candidate remains at the repo root.
