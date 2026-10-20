#!/usr/bin/env python3
"""Install the seven approved-for-candidate-review Paramecium PNG replacements.

Usage: python ci/install_paramecium_generated_assets.py /path/to/paramecium_android_replacement_assets.zip
Run from the repository root, then commit the changed PNG files.
"""
from pathlib import Path
from zipfile import ZipFile
import argparse
import io
import struct

TARGETS = (
    "native-app/app/src/main/assets/unit1/paramecium_external.png",
    "native-app/app/src/main/assets/unit1/paramecium_internal.png",
    "native-app/app/src/main/assets/unit1/paramecium_oral.png",
    "native-app/app/src/main/assets/unit1/paramecium_vacuole.png",
    "native-app/app/src/main/assets/type_studies/paramecium/external-cilia.png",
    "native-app/app/src/main/assets/type_studies/paramecium/oral-apparatus.png",
    "native-app/app/src/main/assets/type_studies/paramecium/contractile-vacuoles.png",
)

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("archive", type=Path)
    parser.add_argument("--root", type=Path, default=Path("."))
    args = parser.parse_args()
    root = args.root.resolve()
    with ZipFile(args.archive) as archive:
        names = set(archive.namelist())
        missing = set(TARGETS) - names
        if missing:
            raise SystemExit("Missing replacement PNGs: " + ", ".join(sorted(missing)))
        # Validate all assets before modifying any repository file.
        validated = {}
        for relative in TARGETS:
            payload = archive.read(relative)
            if payload[:8] != b"\\x89PNG\\r\\n\\x1a\\n" or len(payload) < 33:
                raise SystemExit(f"Invalid PNG: {relative}")
            width, height = struct.unpack(">II", payload[16:24])
            if width < 100 or height < 100:
                raise SystemExit(f"Unexpectedly small PNG: {relative} ({width}x{height})")
            validated[relative] = payload
        for relative, payload in validated.items():
            target = root / relative
            if not target.is_file():
                raise SystemExit(f"Expected existing asset missing: {target}")
        for relative, payload in validated.items():
            target = root / relative
            target.write_bytes(payload)
            print(f"Replaced {relative} ({len(payload)} bytes)")
    print("Seven PNG assets installed. Build, inspect screenshots, and retain academic review HOLD.")

if __name__ == "__main__":
    main()
