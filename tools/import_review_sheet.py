#!/usr/bin/env python3
"""Turn the filled plate sign-off sheet into qa-intake/five-unit-academic-reviews.json.

Mirrors the CI gate (.github/scripts/verify-five-unit-academic-approval.py): only rows with
status ACCEPTED are written, and a row is rejected unless it satisfies every rule the gate checks.
Nothing is written if any ACCEPTED row is invalid, if an ACCEPTED row has no asset stem, or if a
stem appears twice. Review records are bound to the SHA-256 of the exact image bytes in the repo.

  --check   validate and run the CI gate on a temporary copy; NEVER writes --out.
  (default) validate, run the gate on a temporary copy, then replace --out atomically only if every
            record passes. A sheet with zero ACCEPTED rows is refused unless --allow-empty, so a
            blank sheet cannot wipe existing sign-offs.

Usage:
  python tools/import_review_sheet.py --sheet staging/plate-review-sheet.xlsx --check
  python tools/import_review_sheet.py --sheet staging/plate-review-sheet.xlsx
"""
import argparse, csv, json, os, runpy, sys, tempfile
from pathlib import Path

ALLOWED_RIGHTS = {"public_domain", "licensed", "author_owned", "permission_documented"}
TEXT = ("biology_reviewer", "tamil_reviewer", "source_reference", "rights_evidence")
BOOLS = ("pixel_leaders_verified", "morphology_verified", "tamil_terms_verified", "display_reviewed")

def read_rows(path: Path):
    if path.suffix.lower() == ".xlsx":
        from openpyxl import load_workbook
        ws = load_workbook(path, data_only=True)["Plates"]
        it = ws.iter_rows(values_only=True)
        head = [str(h).split("\n")[0].strip() if h is not None else "" for h in next(it)]
        return [dict(zip(head, ["" if v is None else v for v in row])) for row in it]
    with path.open(encoding="utf-8-sig", newline="") as f:
        return list(csv.DictReader(f))

def truthy(v):
    return v is True or str(v).strip().upper() in {"TRUE", "YES", "Y", "1"}

def build_reviews(rows, required):
    """Pure validation: returns (reviews, errors). errors is a list of (label, [problems])."""
    reviews, errors, seen = [], [], set()
    for n, row in enumerate(rows, start=2):
        stem = str(row.get("asset_stem", "")).strip()
        accepted = str(row.get("status", "")).strip().upper() == "ACCEPTED"
        if not accepted:
            continue
        if not stem:
            errors.append((f"sheet row {n}", ["ACCEPTED row has no asset_stem"]))
            continue
        problems = []
        if stem in seen:
            problems.append("duplicate ACCEPTED row for this plate")
        seen.add(stem)
        plate = required.get(stem)
        if plate is None:
            problems.append("not a mandatory plate")
        elif plate["state"] != "PRESENT_UNVERIFIED" or not plate["sha256"]:
            problems.append("image is missing or corrupt, cannot be accepted")
        elif str(row.get("sha256_now", "")).strip() != plate["sha256"]:
            problems.append("sha256 on the sheet no longer matches the file in the repo; regenerate and re-review")
        for f in TEXT:
            if not str(row.get(f, "")).strip():
                problems.append("missing " + f)
        for f in BOOLS:
            if not truthy(row.get(f)):
                problems.append(f + " is not TRUE")
        if str(row.get("rights_status", "")).strip() not in ALLOWED_RIGHTS:
            problems.append("rights_status must be one of " + "/".join(sorted(ALLOWED_RIGHTS)))
        bio, tam = str(row.get("biology_reviewer", "")).strip(), str(row.get("tamil_reviewer", "")).strip()
        if bio and bio.casefold() == tam.casefold():
            problems.append("biology and Tamil reviewers must be different people")
        if problems:
            errors.append((stem, problems))
            continue
        reviews.append({
            "asset_stem": stem, "status": "ACCEPTED", "sha256": plate["sha256"],
            **{f: str(row[f]).strip() for f in TEXT},
            **{f: True for f in BOOLS},
            "rights_status": str(row["rights_status"]).strip(),
            "review_date": str(row.get("review_date", "")).strip(),
            "notes": str(row.get("notes", "")).strip(),
        })
    return reviews, errors


def run(rows, required, out: Path, check: bool, verify_fn, allow_empty=False) -> int:
    """Validate, gate-check a temporary copy, and only then (unless check) replace `out` atomically."""
    reviews, errors = build_reviews(rows, required)
    if errors:
        print("REFUSING TO WRITE: these ACCEPTED rows are invalid:")
        for label, ps in errors:
            print(" -", label, "::", "; ".join(ps))
        return 1
    doc = {"schema_version": 1,
           "disclosure": f"{len(reviews)} of {len(required)} mandatory plates accepted by named, "
                         "independent biology and Tamil reviewers; see reviews.",
           "reviews": reviews}
    text = json.dumps(doc, indent=2, ensure_ascii=False) + "\n"
    if not reviews and not check and not allow_empty:
        print("REFUSING TO WRITE: the sheet has no ACCEPTED rows (use --allow-empty to write an empty record).")
        return 1
    with tempfile.TemporaryDirectory() as td:
        tmp = Path(td) / "reviews.json"
        tmp.write_text(text, encoding="utf-8")
        outcome = verify_fn(tmp)
        ok = sum(o["status"] == "ACCEPTED" for o in outcome)
        print(f"CI gate would report ACADEMIC_ACCEPTANCE_REVIEWED_PLATES={ok}/{len(outcome)}")
        if reviews and ok != len(reviews):
            print("REFUSING TO WRITE: the CI gate rejected at least one record.")
            return 1
    if check:
        print(f"check only: {len(reviews)} ACCEPTED rows valid; nothing written")
        return 0
    out.parent.mkdir(parents=True, exist_ok=True)
    fd, tmp_name = tempfile.mkstemp(dir=out.parent, prefix=".reviews-", suffix=".tmp")
    try:
        with os.fdopen(fd, "w", encoding="utf-8") as f:
            f.write(text)
        os.replace(tmp_name, out)
    finally:
        if os.path.exists(tmp_name):
            os.unlink(tmp_name)
    print(f"wrote {out}: {len(reviews)} accepted of {len(required)}")
    return 0


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--sheet", type=Path, required=True)
    ap.add_argument("--repo-root", type=Path, default=Path("."))
    ap.add_argument("--out", type=Path, default=Path("qa-intake/five-unit-academic-reviews.json"))
    ap.add_argument("--check", action="store_true", help="validate and run the CI gate; never writes --out")
    ap.add_argument("--allow-empty", action="store_true", help="permit writing a record with zero ACCEPTED rows")
    a = ap.parse_args()
    audit = runpy.run_path(str(a.repo_root / ".github/scripts/audit-five-unit-assets.py"))["audit"]
    required = {p["required_stem"]: p for p in audit(a.repo_root) if p["required"]}
    verify = runpy.run_path(str(a.repo_root / ".github/scripts/verify-five-unit-academic-approval.py"))["verify"]
    return run(read_rows(a.sheet), required, a.out, a.check, lambda path: verify(a.repo_root, path), a.allow_empty)


if __name__ == "__main__":
    sys.exit(main())
