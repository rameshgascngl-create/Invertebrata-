#!/usr/bin/env python3
"""Package exact mandatory raster masters for independent offline review.

No alterations to anatomy, pixels, captions, credits or reviewer approvals.
Every bundled file is checked against the canonical asset audit and recorded
by its actual SHA-256. A review-ready package is NOT an accepted image set.
"""
import argparse
import csv
import hashlib
import json
from pathlib import Path
import runpy
from zipfile import ZipFile, ZIP_DEFLATED

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--out", type=Path, required=True)
    args = parser.parse_args()
    root = args.repo_root.resolve()
    audit = runpy.run_path(str(root / ".github/scripts/audit-five-unit-assets.py"))["audit"]
    rows = [r for r in audit(root) if r["required"]]
    assert len(rows) == 52, f"Mandatory image count drift: {len(rows)}"
    manifest = []
    paths = set()
    for record in rows:
        if record["state"] != "PRESENT_UNVERIFIED" or not record["file"]:
            raise ValueError(f"Missing review master: {record['required_stem']}")
        rel = Path("native-app/app/src/main/assets") / record["file"]
        path = (root / rel).resolve()
        if not path.is_relative_to(root) or not path.is_file():
            raise ValueError(f"Invalid image location: {record['file']}")
        data = path.read_bytes()
        digest = hashlib.sha256(data).hexdigest()
        if digest != record["sha256"]:
            raise ValueError(f"Image changed during review packaging: {rel}")
        arcname = "review-images/" + rel.as_posix()
        if arcname in paths:
            raise ValueError(f"Two mandatory slots share one review file: {arcname}")
        paths.add(arcname)
        manifest.append({
            "unit": record["unit"], "organism": record["organism"],
            "plate": record["plate"], "asset_stem": record["required_stem"],
            "review_file": arcname, "sha256": digest,
            "review_state": "PRESENT_UNVERIFIED",
        })
    args.out.parent.mkdir(parents=True, exist_ok=True)
    with ZipFile(args.out, "w", compression=ZIP_DEFLATED, compresslevel=6) as archive:
        for item in manifest:
            archive.write(root / item["review_file"].removeprefix("review-images/"), item["review_file"])
        archive.writestr("REVIEW_NOT_APPROVAL.txt",
            "INVERTEBRATA - 52 exact source images for independent review.\\n"
            "Images are candidates, NOT scientifically or legally approved.\\n"
            "Review original anatomy, labels, mobile legibility, source and rights.\\n"
            "Use the separate hash-bound reviewer worksheet for sign-off.\\n")
        archive.writestr("image-manifest.json",
                         json.dumps(manifest, ensure_ascii=False, indent=2) + "\\n")
    with ZipFile(args.out) as archive:
        assert archive.testzip() is None, "Corrupt reviewer ZIP"
        for item in manifest:
            assert hashlib.sha256(archive.read(item["review_file"])).hexdigest() == item["sha256"]
    print(f"REVIEW_IMAGE_BUNDLE=52/52 EXACT_BYTES VERIFIED; ACADEMIC_APPROVAL=HOLD")
    print(f"REVIEW_BUNDLE_SIZE={args.out.stat().st_size}")

if __name__ == "__main__":
    main()
