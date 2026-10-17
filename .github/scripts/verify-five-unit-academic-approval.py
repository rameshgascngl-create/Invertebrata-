#!/usr/bin/env python3
"""Fail-closed academic approval for exact raster bytes, biology, Tamil and rights.

The academic acceptance decision is not inferred from successful image decode,
an emulator screenshot, AI generation, or the presence of an attribution.
"""
import argparse
import hashlib
import json
from pathlib import Path
import runpy
import sys

ALLOWED_RIGHTS = {"public_domain", "licensed", "author_owned", "permission_documented"}
FIELDS = ("biology_reviewer", "tamil_reviewer", "source_reference",
          "rights_evidence", "pixel_leaders_verified", "morphology_verified",
          "tamil_terms_verified", "display_reviewed", "rights_status")

def verify(repo_root: Path, reviews_path: Path) -> list[dict]:
    asset_script = repo_root / ".github/scripts/audit-five-unit-assets.py"
    audit = runpy.run_path(str(asset_script))["audit"]
    required = [x for x in audit(repo_root) if x["required"]]
    assert len(required) == 43
    doc = json.loads(reviews_path.read_text(encoding="utf-8"))
    assert doc.get("schema_version") == 1, "Invalid academic review schema"
    reviews = doc.get("reviews", [])
    if not isinstance(reviews, list):
        raise ValueError("Review records must be an array")
    ids = [x.get("asset_stem") for x in reviews]
    if len(ids) != len(set(ids)):
        raise ValueError("Duplicate review record for the same plate is prohibited")
    if set(ids) - {r["required_stem"] for r in required}:
        raise ValueError("Review records contain noncurriculum anatomical plates")
    indexed = {x["asset_stem"]: x for x in reviews}
    outcomes = []
    for plate in required:
        state = plate["state"]
        problem = []
        entry = indexed.get(plate["required_stem"])
        if state != "PRESENT_UNVERIFIED":
            problem.append("MISSING_OR_CORRUPT_RASTER")
        if entry is None:
            problem.append("NO_SCIENTIFIC_TAMIL_RIGHTS_REVIEW")
        else:
            if entry.get("status") != "ACCEPTED":
                problem.append("NOT_APPROVED")
            if entry.get("sha256") != plate["sha256"] or not plate["sha256"]:
                problem.append("SOURCE_BYTES_CHANGED_OR_MISSING")
            for field in FIELDS:
                if not entry.get(field):
                    problem.append("MISSING_" + field.upper())
            for field in ("pixel_leaders_verified", "morphology_verified",
                          "tamil_terms_verified", "display_reviewed"):
                if entry.get(field) is not True:
                    problem.append("NOT_VERIFIED_" + field.upper())
            if entry.get("rights_status") not in ALLOWED_RIGHTS:
                problem.append("INVALID_RIGHTS_STATUS")
            if entry.get("biology_reviewer") == entry.get("tamil_reviewer"):
                # Independent reviewer names must be captured separately;
                # this can be waived only by a future explicit review-policy revision.
                problem.append("INDEPENDENT_REVIEW_REQUIRED")
        outcomes.append(dict(unit=plate["unit"], asset=plate["required_stem"],
                             image=plate["file"], status="ACCEPTED" if not problem else "HOLD",
                             blockers=problem))
    return outcomes

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--reviews", type=Path, default=Path("qa-intake/five-unit-academic-reviews.json"))
    parser.add_argument("--out", type=Path, default=Path("qa-evidence/five-unit-academic"))
    args = parser.parse_args()
    outcomes = verify(args.repo_root, args.repo_root / args.reviews)
    args.out.mkdir(parents=True, exist_ok=True)
    (args.out / "academic-review-register.json").write_text(
        json.dumps(outcomes, indent=2, ensure_ascii=False), encoding="utf-8")
    approved = sum(item["status"] == "ACCEPTED" for item in outcomes)
    print(f"ACADEMIC_ACCEPTANCE_REVIEWED_PLATES={approved}/{len(outcomes)}")
    print("SCIENCE_TAMIL_RIGHTS_GATE=" + ("PASS" if approved == 43 else "FAIL"))
    for row in [x for x in outcomes if x["status"] == "HOLD"][:12]:
        print("REVIEW_HOLD=" + row["asset"] + " :: " + ",".join(row["blockers"]))
    return 0 if approved == 43 else 2
if __name__ == "__main__":
    sys.exit(main())
