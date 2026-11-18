#!/usr/bin/env python3
"""Report provenance/visible-credit inconsistencies without changing artwork or approvals.

Source-rights verification belongs to independent reviewers. A credit that says
'public domain' while the provenance still requests confirmation is a review
finding, not evidence that the image is infringing or incorrectly attributed.
"""
import argparse
import json
from pathlib import Path

def audit(provenance: dict, credits: dict) -> list[dict]:
    findings = []
    for slot, record in sorted(provenance.items()):
        visible = credits.get(slot)
        if not visible:
            findings.append({"slot": slot, "issue": "NO_DISPLAY_CREDIT",
                             "provenance_license": record.get("license", ""),
                             "visible_credit": ""})
            continue
        en = str(visible.get("en", ""))
        license_text = str(record.get("license", ""))
        pending = any(word in license_text.casefold()
                      for word in ("confirm", "unverified", "pending"))
        definitive_public_domain = "public domain" in en.casefold()
        mentions_uncertainty = any(word in en.casefold()
                                   for word in ("confirm", "verify", "unverified", "pending",
                                                "believed", "per file page"))
        if pending and definitive_public_domain and not mentions_uncertainty:
            findings.append({"slot": slot,
                             "issue": "PUBLIC_DOMAIN_CREDIT_WITH_UNVERIFIED_RIGHTS",
                             "provenance_license": license_text, "visible_credit": en})
        elif pending and not mentions_uncertainty:
            findings.append({"slot": slot, "issue": "RIGHTS_HOLD_NOT_STATED_IN_VISIBLE_CREDIT",
                             "provenance_license": license_text, "visible_credit": en})
    return findings

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--out", type=Path, required=True)
    args = parser.parse_args()
    base = args.repo_root
    provenance = json.loads((base / "provenance/plates.json").read_text(encoding="utf-8"))["plates"]
    credits = json.loads((base / "native-app/app/src/main/assets/type_studies/plate_credits.json").read_text(encoding="utf-8"))
    results = audit(provenance, credits)
    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_text(json.dumps({
        "purpose": "Reviewer triage only, not a rights conclusion or a corrected attribution",
        "provenance_slots": len(provenance), "visible_credit_slots": len(credits),
        "findings": results
    }, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print("PROVENANCE_CREDIT_REVIEW_FINDINGS=" + str(len(results)))
    for item in results:
        print("CREDIT_REVIEW=" + item["slot"] + " :: " + item["issue"])

if __name__ == "__main__":
    main()
