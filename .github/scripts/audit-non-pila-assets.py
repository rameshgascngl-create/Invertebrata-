#!/usr/bin/env python3
"""Non-Pila anatomical asset inventory. Missing plates are reported, never invented."""
from pathlib import Path
import json
import re
import sys

root = Path(__file__).resolve().parents[2]
manifest = (root / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/NativeCurriculumManifest.kt").read_text()
assets = root / "native-app/app/src/main/assets"
# Extract each TypeStudyRequirement declaration, including its requiredPlateIds.
blocks = re.findall(r'TypeStudyRequirement\("([^"]+)",.*?listOf\((.*?)\).*?AcademicWorkStatus\.', manifest, re.S)
records = []
for organism, plate_source in blocks:
    if organism == "pila":
        continue
    plates = re.findall(r'"([^"]+)"', plate_source)
    for plate in plates:
        candidates = [
            *(assets / "type_studies" / organism / (plate + ext) for ext in (".png", ".webp", ".jpg", ".jpeg")),
            *(assets / "unit1/replacements" / ("paramecium_" + plate + ext) for ext in (".png", ".webp")),
        ]
        present = [str(p.relative_to(assets)) for p in candidates if p.is_file()]
        records.append({"organism": organism, "plate": plate,
                        "asset_paths": present, "status": "PRESENT_UNREVIEWED" if present else "MISSING"})
if len({r["organism"] for r in records}) != 8 or len(records) < 25:
    sys.exit("FAIL: curriculum parser did not find eight non-Pila type studies")
report = {"scope": "eight non-Pila type studies", "total_required": len(records),
          "present": sum(bool(r["asset_paths"]) for r in records),
          "missing": sum(not r["asset_paths"] for r in records),
          "plates": records,
          "note": "File presence is not anatomical, bilingual or licensing approval."}
out = root / "qa-evidence/non-pila-type-study-asset-inventory.json"
out.parent.mkdir(parents=True, exist_ok=True)
out.write_text(json.dumps(report, indent=2) + "\n")
print(json.dumps({k: v for k, v in report.items() if k != "plates"}, indent=2))
