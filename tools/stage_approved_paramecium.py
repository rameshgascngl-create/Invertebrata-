#!/usr/bin/env python3
"""Safely stage the accepted Paramecium plate for native Android integration.

Does not mutate the Kotlin chapter: a figure slot must first be registered in
the existing image pipeline. This avoids broken BookChapterContract references.
Usage: python3 tools/stage_approved_paramecium.py /path/to/approved.png
"""
from __future__ import annotations

import argparse
import hashlib
from pathlib import Path
import sys

EXPECTED_SHA256 = "8f54a66d89843688d64108cc6ebacae35db497e2bbb8bcbef4c959eefbb44d51"
EXPECTED_SIZE = (1536, 1024)
SLOT = "paramecium/reproduction-approved"


def verify(path: Path) -> None:
    if not path.is_file():
        raise ValueError(f"PNG not found: {path}")
    data = path.read_bytes()
    digest = hashlib.sha256(data).hexdigest()
    if digest != EXPECTED_SHA256:
        raise ValueError(f"Approved image checksum mismatch: {digest}")
    if data[:8] != bytes.fromhex("89504e470d0a1a0a"):
        raise ValueError("Not a PNG")
    dimensions = (int.from_bytes(data[16:20], "big"), int.from_bytes(data[20:24], "big"))
    if dimensions != EXPECTED_SIZE:
        raise ValueError(f"Unexpected dimensions: {dimensions}")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("png", type=Path, help="Approved PNG from integration kit")
    args = parser.parse_args()
    try:
        verify(args.png)
    except (ValueError, OSError) as exc:
        print(f"FAIL: {exc}", file=sys.stderr)
        return 1

    print("PASS: exact user-approved PNG; no image modification necessary")
    print(f"Approved slot: {SLOT}")
    print("NEXT: locate the existing packaged-figure registry, register this slot and copy the verified PNG")
    print("NEXT: insert the bfig reference before 'Stages of conjugation' and run BookChapterContract tests")
    print("NEXT: run tools/verify_approved_paramecium.py and Android emulator QA")
    print("No files changed. Intentionally refuses to invent a figure-asset path.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
