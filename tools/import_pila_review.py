#!/usr/bin/env python3
"""Install rights-unverified Pila textbook reference views for LOCAL REVIEW ONLY.

Usage: python3 tools/import_pila_review.py /path/to/pila_android_staging_review_only.zip
Never writes to type_studies or treats these images as academic approval.
"""
import hashlib
import sys
import zipfile
from pathlib import Path

NAMES = (
    "01_shell_ventral_REFERENCE.png",
    "02_operculum_outer_inner_REFERENCE.png",
    "03_shell_dorsal_REFERENCE.png",
    "04_animal_front_view_REFERENCE.png",
)
SOURCE_PREFIX = "app/src/main/assets/review_only/pila/"
TARGET = Path(__file__).resolve().parents[1] / "native-app/app/src/main/assets/review_only/pila/"
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"


def import_archive(archive: Path) -> None:
    if not archive.is_file():
        raise FileNotFoundError(archive)
    with zipfile.ZipFile(archive) as bundle:
        names = set(bundle.namelist())
        missing = [name for name in NAMES if SOURCE_PREFIX + name not in names]
        if missing:
            raise ValueError("Missing expected reference files: " + ", ".join(missing))
        staged = {}
        for name in NAMES:
            data = bundle.read(SOURCE_PREFIX + name)
            if len(data) < 1024 or not data.startswith(PNG_SIGNATURE):
                raise ValueError("Invalid PNG: " + name)
            staged[name] = data
    TARGET.mkdir(parents=True, exist_ok=True)
    for name, data in staged.items():
        dest = TARGET / name
        dest.write_bytes(data)
        print(f"REVIEW_ONLY {dest.relative_to(TARGET.parents[4])} sha256={hashlib.sha256(data).hexdigest()}")
    print("Reference bitmaps installed for local review. Rights/biology approval NOT granted.")
    print("Mandatory type-study inventory unchanged.")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        raise SystemExit("Usage: python3 tools/import_pila_review.py <staging.zip>")
    import_archive(Path(sys.argv[1]))
