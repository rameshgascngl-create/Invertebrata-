#!/usr/bin/env python3
"""Verify owner-approved Paramecium conjugation PNG intake; no external dependencies.

This is an asset-integrity gate, NOT scientific/Tamil approval.
Usage: python3 tools/verify_paramecium_conjugation_intake.py PATH_TO_PNG
"""
import argparse
import hashlib
from pathlib import Path
import struct
import sys

EXPECTED_SHA256 = "5db2d73e912cae0cd96ead08cd2500d380592bee94904a4857897ad08bcc4181"
EXPECTED_DIMENSIONS = (1672, 941)
EXPECTED_SIZE = 2521564
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"


def verify(path: Path) -> list[str]:
    errors = []
    if not path.is_file():
        return [f"Missing candidate PNG: {path}"]
    data = path.read_bytes()
    if not data.startswith(PNG_SIGNATURE):
        return ["File is not a PNG"]
    if len(data) < 33 or data[12:16] != b"IHDR":
        return ["PNG IHDR missing or truncated"]
    width, height = struct.unpack(">II", data[16:24])
    if (width, height) != EXPECTED_DIMENSIONS:
        errors.append(f"Dimensions changed: {width}x{height}")
    if len(data) != EXPECTED_SIZE:
        errors.append(f"Byte size changed: {len(data)}")
    digest = hashlib.sha256(data).hexdigest()
    if digest != EXPECTED_SHA256:
        errors.append(f"SHA-256 mismatch: {digest}")
    return errors


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("png", type=Path, help="Original provisional PNG, not a regenerated or recompressed copy")
    args = parser.parse_args()
    errors = verify(args.png)
    if errors:
        for error in errors:
            print(f"FAIL: {error}", file=sys.stderr)
        return 1
    print("PASS: exact original owner-approved provisional PNG verified")
    print("HOLD: scientific chronology, leader lines, Tamil labels, rights and physical-device QA")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
