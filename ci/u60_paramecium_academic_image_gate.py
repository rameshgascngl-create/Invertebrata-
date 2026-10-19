#!/usr/bin/env python3
"""U60: evidence-only native Android artwork gate. Never approves a plate by file presence."""
import argparse
import hashlib
import json
from pathlib import Path
import re
import struct

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "native-app/app/src/main/assets"
UI = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui"
CONCEPTS = {
    "binary_fission": "paramecium_binary_fission.png",
    "conjugation": "paramecium_conjugation.png",
}

def png_metadata(path):
    data = path.read_bytes()
    if data[:8] != bytes.fromhex("89504e470d0a1a0a") or data[12:16] != b"IHDR":
        raise ValueError("Not a valid PNG header: " + str(path))
    width, height = struct.unpack(">II", data[16:24])
    return {"width": width, "height": height, "bytes": len(data),
            "sha256": hashlib.sha256(data).hexdigest()}

def run_audit():
    reader = (UI / "ParameciumTextbookReader.kt").read_text()
    simulator = (UI / "ParameciumProcessPng.kt").read_text()
    findings = []
    plates = {}
    for concept, name in CONCEPTS.items():
        path = ASSETS / "unit1" / name
        visible = bool(re.search(r'"' + ('"fission"' if concept == "binary_fission" else '"conjugation"')
            + r'\s*->\s*"unit1/' + re.escape(name[:-4]) + r'"', reader))
        meta = png_metadata(path) if path.is_file() else None
        plates[concept] = {"production_path": str(path.relative_to(ROOT)),
                           "exists": meta is not None, "metadata": meta,
                           "reader_student_visible": visible,
                           "academic_approval": "NOT_VERIFIED"}
        if visible:
            findings.append({"severity": "BLOCKER",
                "code": "UNAPPROVED_REPRODUCTION_PLATE_DISPLAYED",
                "concept": concept,
                "reason": "File presence and CI do not verify nuclear chronology, labels or rights."})
        if meta is None:
            findings.append({"severity": "BLOCKER", "code": "MISSING_APPROVED_MASTER",
                             "concept": concept})
        elif max(meta["width"], meta["height"]) < 1600:
            findings.append({"severity": "BLOCKER", "code": "INSUFFICIENT_RASTER_DIMENSIONS",
                             "concept": concept})
    # Fail CI if the reader reconnects either rejected reproduction image.\n    # This is a source-visibility safeguard, not proof of scientific correctness.\n    if any(p["reader_student_visible"] for p in plates.values()):\n        raise RuntimeError("U62 quarantine violated: rejected reproduction PNG is student-visible")\n    for name in ("BINARY_FISSION", "CONJUGATION"):
        if f"ParameciumProcess.{name} to null" not in simulator:
            findings.append({"severity": "BLOCKER", "code": "SIMULATOR_UNREVIEWED_MAPPING",
                             "concept": name.lower()})
    for organism in ("earthworm", "penaeus", "pila", "asterias"):
        production = [p for p in ASSETS.rglob("*.png")
                      if organism in p.relative_to(ASSETS).as_posix().lower()]
        if not production:
            findings.append({"severity": "BLOCKER", "code": "NO_PRODUCTION_RASTER_MASTER",
                             "concept": organism})
    return {"audit": "U60", "source_ref": "current checked-out commit",
            "academic_release": "HOLD", "plates": plates, "findings": findings,
            "notes": [
                "The two reproduction images were uploaded as candidates; no academic approval is recorded.",
                "Existing Paramecium anatomy PNGs are source-derived crops, not independently verified masters.",
                "No Canvas/SVG substitute may satisfy an absent approved PNG.",
                "A5 chapter corpus and Android device acceptance are independent release gates."
            ]}

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--strict", action="store_true",
                        help="Fail while academic blockers remain.")
    parser.add_argument("--output", default="qa-evidence/u60-academic-image-audit.json")
    args = parser.parse_args()
    result = run_audit()
    output = ROOT / args.output
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(result, indent=2, ensure_ascii=False) + "\n")
    print("U60_IMAGE_AUDIT", json.dumps({"academic_release": result["academic_release"],
          "blockers": len(result["findings"]),
          "reader_visible": {k: v["reader_student_visible"] for k, v in result["plates"].items()},
          "output": str(output.relative_to(ROOT))}))
    if args.strict and result["findings"]:
        raise SystemExit(1)

if __name__ == "__main__":
    main()
