#!/usr/bin/env python3
"""Verify that the user-accepted Paramecium PNG is present and referenced.

Run from repository root after the PNG has been imported. This is a strict
acceptance gate: it does not modify any textbook content or image.
"""
from __future__ import annotations

import hashlib
import pathlib
import sys

ROOT = pathlib.Path(__file__).resolve().parents[1]
SHA256 = "8f54a66d89843688d64108cc6ebacae35db497e2bbb8bcbef4c959eefbb44d51"
CHAPTER = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/ParameciumBookChapter.kt"
ASSETS = ROOT / "native-app/app/src/main"
PNG_NAME = "Paramecium_Reproduction_Accepted_Candidate.png"


def main() -> int:
    candidates = [
        p for p in ASSETS.rglob("*.png")
        if p.is_file() and hashlib.sha256(p.read_bytes()).hexdigest() == SHA256
    ]
    if len(candidates) != 1:
        print(f"FAIL: expected exactly one approved PNG under {ASSETS}; found {len(candidates)}")
        print(f"Source asset: {PNG_NAME}; SHA-256: {SHA256}")
        return 1

    data = candidates[0].read_bytes()
    if data[:8] != bytes.fromhex("89504e470d0a1a0a"):
        print("FAIL: asset does not have PNG signature")
        return 1
    width = int.from_bytes(data[16:20], "big")
    height = int.from_bytes(data[20:24], "big")
    if (width, height) != (1536, 1024):
        print(f"FAIL: image dimensions {width}x{height}, expected 1536x1024")
        return 1

    chapter = CHAPTER.read_text(encoding="utf-8")
    if "nuclei-reproduction" not in chapter or "Stages of conjugation" not in chapter:
        print("FAIL: expected reproduction section missing from chapter")
        return 1
    slot = "paramecium/reproduction-approved"
    if f'bfig("{slot}"' not in chapter:
        print(f"FAIL: accepted figure slot {slot} is not referenced in the chapter")
        return 1
    if chapter.index(f'bfig("{slot}"') > chapter.index('btable("Stages of conjugation"'):
        print("FAIL: accepted reproduction plate must precede the conjugation table")
        return 1
    print(f"PASS: approved PNG at {candidates[0].relative_to(ROOT)}")
    print("PASS: exact checksum, dimensions and chapter figure placement")
    print("NOTE: figure slot registration, rendering and emulator screenshots still require verification")
    return 0


if __name__ == "__main__":
    sys.exit(main())
