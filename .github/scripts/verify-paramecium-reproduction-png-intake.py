#!/usr/bin/env python3
"""Fail-closed technical intake for individual Paramecium reproduction PNGs.

This checks raster integrity and declared provenance, NOT zoological correctness,
Tamil accuracy, source permissions, or Android screenshot acceptance.
"""
import argparse
import hashlib
import json
from pathlib import Path
import struct
import sys
import zlib

IDS = [f"F{i:02}" for i in range(1, 7)] + [f"C{i:02}" for i in range(1, 11)]
SIGNATURE = b"\x89PNG\r\n\x1a\n"
MAX_BYTES = 80 * 1024 * 1024

def inspect_png(path):
    data = path.read_bytes()
    if len(data) > MAX_BYTES or not data.startswith(SIGNATURE):
        raise ValueError("invalid PNG signature or oversized file")
    pos, width, height, seen_ihdr, seen_idat, seen_iend = 8, 0, 0, False, False, False
    while pos < len(data):
        if pos + 12 > len(data):
            raise ValueError("truncated PNG chunk")
        length = struct.unpack(">I", data[pos:pos + 4])[0]
        kind = data[pos + 4:pos + 8]
        end = pos + 12 + length
        if length > MAX_BYTES or end > len(data):
            raise ValueError("invalid chunk length")
        payload = data[pos + 8:pos + 8 + length]
        actual_crc = struct.unpack(">I", data[pos + 8 + length:end])[0]
        if zlib.crc32(kind + payload) & 0xffffffff != actual_crc:
            raise ValueError("PNG chunk CRC mismatch")
        if not seen_ihdr and kind != b"IHDR":
            raise ValueError("IHDR must be first")
        if kind == b"IHDR":
            if seen_ihdr or length != 13:
                raise ValueError("invalid IHDR")
            width, height, depth, colour, compression, filtering, interlace = struct.unpack(">IIBBBBB", payload)
            if depth not in (8, 16) or colour not in (0, 2, 4, 6) or compression or filtering or interlace:
                raise ValueError("unsupported PNG encoding")
            seen_ihdr = True
        elif kind == b"IDAT":
            seen_idat = True
        elif kind == b"IEND":
            if length or not seen_idat or end != len(data):
                raise ValueError("invalid IEND or trailing data")
            seen_iend = True
            break
        pos = end
    if not (seen_ihdr and seen_idat and seen_iend):
        raise ValueError("missing mandatory PNG chunks")
    if width < 1 or height < 1:
        raise ValueError("invalid image dimensions")
    return {"width": width, "height": height, "sha256": hashlib.sha256(data).hexdigest()}

def verify(root, manifest):
    records = json.loads(manifest.read_text(encoding="utf-8"))
    if not isinstance(records, dict) or set(records) != set(IDS):
        raise ValueError("manifest must contain exactly F01-F06 and C01-C10")
    outcomes = []
    for plate_id in IDS:
        record = records[plate_id]
        blockers = []
        if not isinstance(record, dict):
            record = {}
            blockers.append("INVALID_RECORD")
        relative = record.get("path", "")
        path = root / relative if isinstance(relative, str) else root
        if not isinstance(relative, str) or not relative.startswith("qa-intake/paramecium/") or ".." in Path(relative).parts or path.suffix.lower() != ".png":
            blockers.append("INVALID_PATH")
        elif not path.is_file():
            blockers.append("PNG_MISSING")
        else:
            try:
                result = inspect_png(path)
                if record.get("sha256") != result["sha256"]:
                    blockers.append("HASH_MISMATCH")
            except (ValueError, OSError) as exc:
                blockers.append("PNG_INVALID:" + str(exc))
        for key in ("creator", "source_url_or_user_provenance", "licence_or_permission_evidence", "attribution", "species", "observed_or_schematic"):
            if not isinstance(record.get(key), str) or not record[key].strip():
                blockers.append("MISSING_" + key.upper())
        if record.get("species") != "Paramecium caudatum":
            blockers.append("SPECIES_NOT_CONFIRMED")
        if record.get("observed_or_schematic") not in ("observed", "schematic"):
            blockers.append("INVALID_DEPICTION_TYPE")
        # Approval is intentionally NOT derivable from this script.
        outcomes.append({"plate": plate_id, "technical_intake": "PASS" if not blockers else "HOLD", "blockers": blockers})
    return outcomes

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--manifest", type=Path, default=Path("qa-intake/paramecium/reproduction-png-intake.json"))
    args = parser.parse_args()
    try:
        result = verify(args.repo_root, args.repo_root / args.manifest)
    except (ValueError, OSError, json.JSONDecodeError) as exc:
        print("TECHNICAL_INTAKE=HOLD:", exc)
        return 2
    for row in result:
        print(row["plate"], row["technical_intake"], ",".join(row["blockers"]))
    passed = sum(row["technical_intake"] == "PASS" for row in result)
    print(f"PARAMECIUM_RASTER_TECHNICAL_INTAKE={passed}/16")
    print("ZOOLOGY_TAMIL_RIGHTS_DEVICE_APPROVAL=NOT_ASSESSED")
    return 0 if passed == 16 else 2

if __name__ == "__main__":
    sys.exit(main())
