#!/usr/bin/env python3
"""Read-only, dependency-free audit of educational raster assets in an Android APK.

Usage: python3 tools/audit_apk_plates.py app-debug.apk --out qa-evidence/plate-audit.json
This is a triage gate, NOT scientific, Tamil or rights approval.
"""
import argparse
import collections
import hashlib
import json
import struct
import zipfile
from pathlib import Path


def dimensions(data):
    if data.startswith(b"\x89PNG\r\n\x1a\n") and len(data) >= 24:
        return struct.unpack(">II", data[16:24])
    if data[:4] == b"RIFF" and data[8:12] == b"WEBP":
        tag = data[12:16]
        if tag == b"VP8X" and len(data) >= 30:
            return (int.from_bytes(data[24:27], "little") + 1,
                    int.from_bytes(data[27:30], "little") + 1)
        if tag == b"VP8L" and len(data) >= 25 and data[20] == 0x2f:
            bits = int.from_bytes(data[21:25], "little")
            return ((bits & 0x3fff) + 1, ((bits >> 14) & 0x3fff) + 1)
        if tag == b"VP8 " and len(data) >= 30 and data[23:26] == b"\x9d\x01\x2a":
            return (int.from_bytes(data[26:28], "little") & 0x3fff,
                    int.from_bytes(data[28:30], "little") & 0x3fff)
    return None


def audit(apk):
    rows = []
    with zipfile.ZipFile(apk) as z:
        corrupt = z.testzip()
        if corrupt:
            raise ValueError("ZIP CRC failure: " + corrupt)
        for name in sorted(z.namelist()):
            if not name.startswith("assets/") or not name.lower().endswith((".png", ".webp", ".jpg", ".jpeg", ".svg")):
                continue
            data = z.read(name)
            dims = dimensions(data)
            rows.append(dict(path=name, extension=Path(name).suffix.lower(),
                             bytes=len(data), sha256=hashlib.sha256(data).hexdigest(),
                             width=dims[0] if dims else None,
                             height=dims[1] if dims else None,
                             review_only="/review_only/" in name,
                             provisional="PROVISIONAL" in name.upper(),
                             small=bool(dims and min(dims) < 400)))
    hashes = collections.defaultdict(list)
    for row in rows:
        hashes[row["sha256"]].append(row["path"])
    return dict(apk_sha256=hashlib.sha256(Path(apk).read_bytes()).hexdigest(),
                educational_asset_count=len(rows),
                extensions=dict(collections.Counter(r["extension"] for r in rows)),
                review_only_count=sum(r["review_only"] for r in rows),
                provisional_count=sum(r["provisional"] for r in rows),
                small_image_count=sum(r["small"] for r in rows),
                duplicates=[v for v in hashes.values() if len(v) > 1],
                images=rows,
                caveat="Static triage only; no zoology, Tamil, copyright or device approval")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("apk", type=Path)
    parser.add_argument("--out", type=Path)
    args = parser.parse_args()
    report = audit(args.apk)
    output = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
    if args.out:
        args.out.parent.mkdir(parents=True, exist_ok=True)
        args.out.write_text(output, encoding="utf-8")
    else:
        print(output)
    print("Educational images:", report["educational_asset_count"])
    print("Review-only:", report["review_only_count"],
          "provisional:", report["provisional_count"],
          "small:", report["small_image_count"])
    print("Exact duplicate groups:", len(report["duplicates"]))


if __name__ == "__main__":
    main()
