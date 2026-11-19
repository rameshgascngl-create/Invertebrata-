#!/usr/bin/env python3
"""Import the *existing* owner-approved provisional conjugation PNG safely.

Run from a full Git checkout on the academic-audit branch:
    git fetch origin 172eb188fdf5e42c0786286859e181d66dfecfea
    python3 tools/import_paramecium_conjugation_png.py

This script does not merge histories, alter Kotlin, or declare scientific approval.
Afterwards commit the PNG separately and register it in the existing book figure
library only after checking its appearance in the reader.
"""
from __future__ import annotations

import hashlib
from io import BytesIO
from pathlib import Path
import subprocess
import sys
from zipfile import ZipFile, BadZipFile

ROOT = Path(__file__).resolve().parents[1]
UPLOAD_COMMIT = "172eb188fdf5e42c0786286859e181d66dfecfea"
ZIP_NAME = "INVERTEBRATA_Paramecium_Conjugation_Provisional_Integration_Package.zip"
PNG_NAME = "paramecium_conjugation_owner_approved_provisional.png"
EXPECTED_SHA256 = "5db2d73e912cae0cd96ead08cd2500d380592bee94904a4857897ad08bcc4181"
EXPECTED_SIZE = 2521564
EXPECTED_DIMS = (1672, 941)
DEST = ROOT / "native-app/app/src/main/assets/paramecium/conjugation-provisional.png"


def main() -> int:
    try:
        archive_bytes = subprocess.check_output(
            ["git", "-C", str(ROOT), "show", f"{UPLOAD_COMMIT}:{ZIP_NAME}"],
            stderr=subprocess.PIPE,
        )
    except subprocess.CalledProcessError:
        print("ERROR: Upload commit not present. Fetch the pinned upload commit first.", file=sys.stderr)
        return 2
    try:
        with ZipFile(BytesIO(archive_bytes)) as archive:
            if archive.namelist().count(PNG_NAME) != 1:
                raise ValueError("Expected exactly one matching PNG archive entry")
            data = archive.read(PNG_NAME)
    except (BadZipFile, ValueError, OSError) as exc:
        print(f"ERROR: Invalid upload archive: {exc}", file=sys.stderr)
        return 2

    actual = hashlib.sha256(data).hexdigest()
    if actual != EXPECTED_SHA256 or len(data) != EXPECTED_SIZE:
        print(f"ERROR: PNG byte identity mismatch: sha256={actual} bytes={len(data)}", file=sys.stderr)
        return 2
    if data[:8] != bytes.fromhex("89504e470d0a1a0a") or data[12:16] != b"IHDR":
        print("ERROR: Invalid PNG header", file=sys.stderr)
        return 2
    dimensions = (int.from_bytes(data[16:20], "big"), int.from_bytes(data[20:24], "big"))
    if dimensions != EXPECTED_DIMS:
        print(f"ERROR: Unexpected PNG dimensions {dimensions}", file=sys.stderr)
        return 2
    if DEST.exists() and hashlib.sha256(DEST.read_bytes()).hexdigest() != EXPECTED_SHA256:
        print(f"ERROR: Refusing to overwrite a different asset: {DEST}", file=sys.stderr)
        return 2
    DEST.parent.mkdir(parents=True, exist_ok=True)
    DEST.write_bytes(data)
    print(f"PASS: exact provisional PNG staged at {DEST.relative_to(ROOT)}")
    print("NEXT: register as a separately labelled provisional figure; do not replace the approved reproduction plate.")
    print("HOLD: independent zoology/Tamil/rights review and emulator screenshots remain outstanding.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
