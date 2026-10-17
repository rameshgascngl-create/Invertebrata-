#!/usr/bin/env python3
"""Five-unit native Android bitmap manifest and acceptance gate.

Noninteractive anatomy must be present as PNG or WebP masters. Source presence
proves only a decodable candidate exists, not anatomical, rights or Tamil QA.
This gate MUST fail until every mandatory type-study master has been imported.
"""
import argparse
import csv
import hashlib
import json
from pathlib import Path
import struct
import sys

REQUIRED_TYPE_STUDIES = {
    "paramecium": (1, ["external-cilia", "oral-apparatus", "contractile-vacuoles", "locomotion"]),
    "sycon": (2, ["external", "canal-system", "reproduction"]),
    "obelia": (2, ["colony", "hydranth-gonangium", "medusa", "life-cycle"]),
    "fasciola": (3, ["external", "digestive-reproductive", "life-cycle"]),
    "ascaris": (3, ["male-female-external", "female-internal", "life-cycle"]),
    "earthworm": (4, ["external-setae", "digestive", "circulatory", "nervous", "excretory", "reproductive"]),
    "penaeus": (4, ["external", "appendages", "internal", "larval-development"]),
    "pila": (5, ["external", "mantle-cavity", "ctenidium", "torsion"]),
    "asterias": (5, ["external", "water-vascular-system", "internal", "larval-development"]),
}
# Nereis has its own fixed 8-image viewer, independent of the nine type-study
# entries in the curriculum manifest.
NEREIS = [
    "nereis/01_external_morphology.png", "nereis/02_enlarged_head.png",
    "nereis/03_biramous_parapodium.png", "nereis/04_digestive_system.png",
    "nereis/05_circulatory_system.png", "nereis/06_nervous_system.png",
    "nereis/07_excretory_system.png", "nereis/08_trochophore_larva.png",
]
# Supplementary raster views currently referenced by source code. These
# are inventory checks, not hard type-study acceptance requirements.
EXTRA = {
    1: ["unit1/paramecium_external", "unit1/paramecium_oral",
        "unit1/paramecium_vacuole", "unit1/paramecium_internal",
        "unit1/references/entamoeba", "unit1/references/trypanosoma",
        "unit1/references/leishmania", "unit1/references/plasmodium",
        "unit1/original_candidates/entamoeba_trophozoite",
        "unit1/original_candidates/entamoeba_cyst"],
    2: ["unit2/sycon_plate", "unit2/obelia_colony", "unit2/obelia_lifecycle"],
    3: ["unit3/fasciola_type", "unit3/ascaris_type"],
}
PNG = b"\x89PNG\r\n\x1a\n"

def check_file(path: Path) -> tuple[bool, str]:
    try:
        data = path.read_bytes()
        if len(data) < 1024:
            return False, "FILE_TOO_SMALL"
        if path.suffix.lower() == ".png":
            if data[:8] != PNG or data[12:16] != b"IHDR":
                return False, "INVALID_PNG_HEADER"
            width, height = struct.unpack(">II", data[16:24])
        elif path.suffix.lower() == ".webp":
            if data[:4] != b"RIFF" or data[8:12] != b"WEBP":
                return False, "INVALID_WEBP_HEADER"
            # Header check only. Device decode must be verified separately.
            width = height = 1
        else:
            return False, "UNSUPPORTED_FORMAT"
        if width < 1 or height < 1:
            return False, "INVALID_IMAGE_SIZE"
        return True, hashlib.sha256(data).hexdigest()
    except (OSError, ValueError, struct.error) as err:
        return False, "UNREADABLE:" + type(err).__name__

def inspect(asset_root: Path, unit: int, organism: str, plate: str,
            stem: str, mandatory: bool) -> dict:
    paths = [asset_root / (stem + ext) for ext in (".png", ".webp")]
    existing = [p for p in paths if p.is_file()]
    if not existing:
        return dict(unit=unit, organism=organism, plate=plate,
                    required_stem=stem, required=mandatory, state="MISSING",
                    file="", sha256="", academic_approved=False)
    for p in existing:
        ok, detail = check_file(p)
        if ok:
            return dict(unit=unit, organism=organism, plate=plate,
                        required_stem=stem, required=mandatory,
                        state="PRESENT_UNVERIFIED",
                        file=p.relative_to(asset_root).as_posix(),
                        sha256=detail, academic_approved=False)
    return dict(unit=unit, organism=organism, plate=plate,
                required_stem=stem, required=mandatory, state="CORRUPT",
                file=existing[0].relative_to(asset_root).as_posix(),
                sha256="", academic_approved=False)

def audit(repo_root: Path) -> list[dict]:
    assets = repo_root / "native-app/app/src/main/assets"
    rows = []
    for organism, (unit, plates) in REQUIRED_TYPE_STUDIES.items():
        for plate in plates:
            stem = f"type_studies/{organism}/{plate}"
            rows.append(inspect(assets,unit,organism,plate,stem,True))
    for name in NEREIS:
        rows.append(inspect(assets,4,"nereis",name.split("/")[-1],
                            name.rsplit(".",1)[0],True))
    for unit, stems in EXTRA.items():
        for stem in stems:
            rows.append(inspect(assets,unit,stem.split("/")[1],
                                stem.split("/")[-1],stem,False))
    assert len(rows) == 58, len(rows) # 35 mandatory + 8 Nereis + 15 extra
    assert len({r["required_stem"] for r in rows}) == len(rows)
    return rows

def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--repo-root", type=Path, default=Path("."))
    ap.add_argument("--out", type=Path, default=Path("qa-evidence/five-unit-assets"))
    ap.add_argument("--gate", action="store_true")
    args = ap.parse_args()
    rows = audit(args.repo_root)
    args.out.mkdir(parents=True, exist_ok=True)
    with (args.out / "plate-register.csv").open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=list(rows[0]))
        writer.writeheader()
        writer.writerows(rows)
    (args.out / "plate-register.json").write_text(
        json.dumps(rows, ensure_ascii=False, indent=2), encoding="utf-8")
    required = [r for r in rows if r["required"]]
    absent = [r for r in required if r["state"] != "PRESENT_UNVERIFIED"]
    for unit in range(1,6):
        records = [r for r in required if r["unit"] == unit]
        available = sum(r["state"] == "PRESENT_UNVERIFIED" for r in records)
        print(f"UNIT_{unit}_MANDATORY_RASTERS={available}/{len(records)}")
    print(f"MANDATORY_RASTER_FILES={len(required)}; MISSING_OR_CORRUPT={len(absent)}")
    print("ANATOMICAL_SOURCE_RIGHTS_TAMIL_EDITS=PENDING")
    if absent:
        print("MISSING_PLATES:",*[r["required_stem"] for r in absent],sep="\n - ")
    if args.gate and absent:
        print("FIVE_UNIT_ANATOMICAL_ACCEPTANCE=FAIL")
        return 2
    print("FIVE_UNIT_INVENTORY=CREATED; ACADEMIC_APPROVAL=NOT_GRANTED")
    return 0
if __name__ == "__main__":
    sys.exit(main())
