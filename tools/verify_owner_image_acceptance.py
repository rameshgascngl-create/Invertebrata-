#!/usr/bin/env python3
"""Verify the owner's explicit decision to use every existing mandatory image.

Owner acceptance is separate from independent biology/Tamil review and rights clearance.
No image pixels, plate credits or academic review records are changed by this tool.
"""
import argparse
import hashlib
import json
import runpy
from pathlib import Path

DECISION = "ACCEPT_ALL_CURRENT_MANDATORY_ILLUSTRATIONS"

def validate(root: Path) -> list[dict]:
    record = json.loads((root / "qa-intake/owner-image-acceptance.json").read_text(encoding="utf-8"))
    assert record["schema_version"] == 1
    assert record["decision"] == DECISION
    assert record["expected_mandatory_slots"] == 52
    assert record["academic_review_status"] == "NOT_ATTESTED"
    assert record["rights_clearance_status"] == "NOT_ATTESTED"
    rows = runpy.run_path(str(root / ".github/scripts/audit-five-unit-assets.py"))["audit"](root)
    required = [r for r in rows if r["required"]]
    assert len(required) == 52, f"Mandatory plate count changed: {len(required)}"
    assert len({r["required_stem"] for r in required}) == 52, "Duplicate mandatory slots"
    result = []
    for plate in required:
        assert plate["state"] == "PRESENT_UNVERIFIED", (
            "Owner acceptance requires existing valid raster", plate["required_stem"], plate["state"])
        file = root / "native-app/app/src/main/assets" / plate["file"]
        digest = hashlib.sha256(file.read_bytes()).hexdigest()
        assert digest == plate["sha256"], ("Source image changed during audit", plate["required_stem"])
        result.append({
            "unit": plate["unit"], "asset_stem": plate["required_stem"],
            "source_file": plate["file"], "sha256": digest,
            "owner_use_status": "ACCEPTED_FOR_TEXTBOOK_USE",
            "independent_science_tamil_review": "NOT_ATTESTED",
            "source_rights_clearance": "NOT_ATTESTED",
        })
    return result

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--out", type=Path, default=Path("qa-evidence/owner-image-acceptance.json"))
    args = parser.parse_args()
    rows = validate(args.repo_root)
    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_text(json.dumps({
        "decision": DECISION, "summary": "Owner accepts all existing 52 mandatory plates for textbook use.",
        "academic_and_rights_reviews_are_not_claimed": True, "plates": rows
    }, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print(f"OWNER_ACCEPTED_TEXTBOOK_PLATES={len(rows)}/52; INDEPENDENT_REVIEW_NOT_ATTESTED")

if __name__ == "__main__":
    main()
