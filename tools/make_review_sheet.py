#!/usr/bin/env python3
"""Generate the plate sign-off sheet (XLSX) from the repo's own mandatory-plate audit.

The sheet lists every mandatory plate (the same register the CI academic gate uses), the SHA-256
of the exact bytes now in the app, and the fields a reviewer must fill in. Fill it in, then run
tools/import_review_sheet.py to produce qa-intake/five-unit-academic-reviews.json.

Regenerate whenever a plate changes: a sign-off is bound to the SHA-256 of the exact file reviewed.

Usage:  python tools/make_review_sheet.py [--out plate-review-sheet.xlsx]
Needs:  pip install openpyxl
"""
import argparse, collections, runpy
from pathlib import Path
from openpyxl import Workbook
from openpyxl.styles import Alignment, Font, PatternFill
from openpyxl.worksheet.datavalidation import DataValidation

INPUT_COLUMNS = [
    ("status", "ACCEPTED only when EVERY field below is complete and true; otherwise leave blank or HOLD"),
    ("biology_reviewer", "Name of the zoologist who checked the anatomy"),
    ("tamil_reviewer", "Name of the Tamil editor; MUST be a different person from the biology reviewer"),
    ("source_reference", "Author, title/URL or file origin of this exact image"),
    ("rights_status", "public_domain | licensed | author_owned | permission_documented"),
    ("rights_evidence", "Licence text/URL, permission letter reference, or author statement"),
    ("pixel_leaders_verified", "TRUE if every leader line and label points to the right structure"),
    ("morphology_verified", "TRUE if the anatomy is scientifically correct for this species"),
    ("tamil_terms_verified", "TRUE if all Tamil terms/labels were checked"),
    ("display_reviewed", "TRUE if it was reviewed as displayed on a phone (zoom, labels legible)"),
    ("review_date", "YYYY-MM-DD"),
    ("notes", "Corrections needed, if any"),
]

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--repo-root", type=Path, default=Path("."))
    ap.add_argument("--out", type=Path, default=Path("plate-review-sheet.xlsx"))
    a = ap.parse_args()
    audit = runpy.run_path(str(a.repo_root / ".github/scripts/audit-five-unit-assets.py"))["audit"]
    plates = [x for x in audit(a.repo_root) if x["required"]]
    by_hash = collections.defaultdict(list)
    for p in plates:
        if p["sha256"]:
            by_hash[p["sha256"]].append(p["required_stem"])

    wb = Workbook()
    ins = wb.active
    ins.title = "Instructions"
    lines = [
        "INVERTEBRATA - plate sign-off sheet",
        "",
        f"{len(plates)} mandatory plates. Fill one row per plate on the 'Plates' sheet.",
        "A plate counts as accepted only if status = ACCEPTED and ALL of these are true:",
        "  - a named biology reviewer AND a different named Tamil reviewer",
        "  - a source reference and rights evidence, with rights_status from the fixed list",
        "  - pixel_leaders_verified, morphology_verified, tamil_terms_verified and display_reviewed all TRUE",
        "  - the SHA-256 on the row still matches the file in the app (sign-off is bound to exact bytes)",
        "If an image is changed after sign-off, regenerate this sheet and review again.",
        "Rows marked MISSING have no image yet: they cannot be reviewed until a plate is added.",
        "Rows with a note about identical files share the same image bytes with another slot.",
        "",
        "Then run:  python tools/import_review_sheet.py --sheet <this file> --check",
        "The importer rejects incomplete rows and refuses to write a partial or invalid record.",
    ]
    for i, t in enumerate(lines, 1):
        ins.cell(row=i, column=1, value=t)
    ins["A1"].font = Font(bold=True, size=14)
    ins.column_dimensions["A"].width = 110

    ws = wb.create_sheet("Plates")
    head = ["unit", "organism", "plate", "asset_stem", "image_file", "state", "sha256_now",
            "repo_note"] + [c for c, _ in INPUT_COLUMNS]
    ws.append(head)
    for p in plates:
        notes = []
        if p["state"] != "PRESENT_UNVERIFIED":
            notes.append("MISSING: no image in the app yet")
        twins = [s for s in by_hash.get(p["sha256"], []) if s != p["required_stem"]] if p["sha256"] else []
        if twins:
            notes.append("identical bytes also used by: " + ", ".join(twins))
        ws.append([p["unit"], p["organism"], p["plate"], p["required_stem"], p["file"], p["state"],
                   p["sha256"] or "", "; ".join(notes)] + [""] * len(INPUT_COLUMNS))

    blue = PatternFill("solid", fgColor="D9E8F5")
    grey = PatternFill("solid", fgColor="E7E6E6")
    amber = PatternFill("solid", fgColor="FFF2CC")
    for c in range(1, len(head) + 1):
        cell = ws.cell(row=1, column=c)
        cell.font = Font(bold=True)
        cell.fill = blue if c > 8 else grey
        cell.alignment = Alignment(wrap_text=True, vertical="top")
    for ci, (name, hint) in enumerate(INPUT_COLUMNS, start=9):
        ws.cell(row=1, column=ci).value = name + "\n(" + hint + ")"
    ws.row_dimensions[1].height = 90
    for r in range(2, ws.max_row + 1):
        for c in range(1, 9):
            ws.cell(row=r, column=c).fill = amber if (c == 8 and ws.cell(row=r, column=8).value) else grey
    widths = [6, 12, 22, 44, 48, 20, 22, 44] + [22, 24, 24, 34, 22, 34, 14, 14, 14, 14, 12, 30]
    for i, w in enumerate(widths, 1):
        ws.column_dimensions[ws.cell(row=1, column=i).column_letter].width = w
    ws.freeze_panes = "E2"
    last = ws.max_row
    col = {name: 9 + i for i, (name, _) in enumerate(INPUT_COLUMNS)}
    def dv(values, name):
        v = DataValidation(type="list", formula1='"' + ",".join(values) + '"', allow_blank=True)
        ws.add_data_validation(v)
        L = ws.cell(row=2, column=col[name]).column_letter
        v.add(f"{L}2:{L}{last}")
    dv(["ACCEPTED", "HOLD"], "status")
    dv(["public_domain", "licensed", "author_owned", "permission_documented"], "rights_status")
    for f in ("pixel_leaders_verified", "morphology_verified", "tamil_terms_verified", "display_reviewed"):
        dv(["TRUE", "FALSE"], f)
    wb.save(a.out)
    print(f"wrote {a.out}: {len(plates)} plates "
          f"({sum(p['state'] != 'PRESENT_UNVERIFIED' for p in plates)} missing)")

if __name__ == "__main__":
    main()
