#!/usr/bin/env python3
"""Builds the 43-chapter academic audit register from repository data only.

Every cell is either read from a file in the repository or is an explicit gap. Nothing here is
a scientific judgement: status is VERIFIED only when qa-intake/five-unit-academic-reviews.json
holds a review for the chapter with a named reviewer and evidence; otherwise it is PROVISIONAL,
or MISSING when the chapter has no textbook-style chapter source.

Usage: tools/build_audit_register.py [--check]   (writes native-app/ACADEMIC_AUDIT_REGISTER.md and .csv)
"""
import csv, glob, json, re, sys, pathlib

ROOT = pathlib.Path(__file__).resolve().parent.parent
APP = ROOT / "native-app/app/src/main"
DATA = APP / "java/com/gasczoology/invertebratelab/data"

def chapters():
    out = []
    for f in sorted(glob.glob(str(APP / "assets/a5/U*_A5_VALIDATED.json"))):
        unit = int(re.search(r"U(\d)_", f).group(1))
        for cid, body in json.load(open(f, encoding="utf-8")).items():
            out.append((unit, cid, body))
    return out

def book_sources():
    """lesson id -> (file name, source text)."""
    found = {}
    for f in sorted(glob.glob(str(DATA / "*.kt"))):
        text = open(f, encoding="utf-8").read()
        # Only a chapter definition counts, not a lesson draft or a page list that names the id.
        for m in re.finditer(r'(?:bchapter|BookChapter)\(\s*(?:lessonId\s*=\s*)?"(u\d-[a-z0-9-]+)"', text):
            found.setdefault(m.group(1), (pathlib.Path(f).name, text))
    return found

def credits():
    path = APP / "assets/type_studies/plate_credits.json"
    return json.load(open(path, encoding="utf-8")) if path.exists() else {}

def library_credits():
    """lib/<id> slot -> credit kind, read from BookFigureLibrary.kt."""
    text = open(DATA / "BookFigureLibrary.kt", encoding="utf-8").read()
    kind = {"aiPlate": "AI-generated image", "schematic": "original schematic, expert review pending",
            "obeliaNativeRaster": "existing raster, source and rights review pending",
            "coralNoaa": "NOAA credit recorded", "plasmodiumCredit": "CC BY 4.0 credit recorded, file bytes unverified"}
    out = {}
    for m in re.finditer(r'BookLibraryFigure\("([^"]+)",\s*"[^"]+",\s*(\w+)\)', text):
        out["lib/" + m.group(1)] = kind.get(m.group(2), "credited: " + m.group(2))
    # The long list of ids that all use the schematic credit.
    tail = text[text.index("+ listOf("):]
    for m in re.finditer(r'"([a-z0-9-]+)"', tail.split(".map")[0]):
        out.setdefault("lib/" + m.group(1), kind["schematic"])
    return out

LIB = None
def credit_class(slot, table):
    global LIB
    if LIB is None: LIB = library_credits()
    if slot in LIB: return LIB[slot]
    entry = table.get(slot)
    if entry is None:
        return "NO CREDIT RECORDED"
    en = entry.get("en", "")
    if en.lower().startswith("ai-generated"):
        return "AI-generated image"
    return "credited: " + en[:60]

def reviews():
    path = ROOT / "qa-intake/five-unit-academic-reviews.json"
    data = json.load(open(path, encoding="utf-8"))
    return {r.get("chapter"): r for r in data.get("reviews", []) if r.get("chapter")}

def main():
    check = "--check" in sys.argv
    table, review, sources = credits(), reviews(), book_sources()
    rows = []
    for unit, cid, body in chapters():
        src = sources.get(cid)
        text = src[1] if src else ""
        slots = re.findall(r'(?:bfig|BookFigure)\(\s*"([^"]+)"', text)
        slot_note = "; ".join(f"{s}: {credit_class(s, table)}" for s in dict.fromkeys(slots)) or "none"
        rev = review.get(cid)
        verified = bool(rev and rev.get("reviewer") and rev.get("evidence") and rev.get("status") == "VERIFIED")
        status = "VERIFIED" if verified else ("DISPUTED" if rev and rev.get("status") == "DISPUTED" else
                 ("PROVISIONAL" if src else "MISSING"))
        q5 = len(body.get("q5_en", []))
        tamil_q = len(body.get("q5_ta", []))
        placeholder_refs = text.count("to be added by the reviewer") + text.count("BookRefs.")
        rows.append({
            "Unit": unit, "Chapter ID": cid,
            "Lesson source": src[0] if src else "NONE",
            "Sections": len(re.findall(r"\b(?:bsec|BookSection)\(", text)),
            "Definitions": len(re.findall(r"\b(?:bdef|BookDefinition)\(", text)),
            "Tables": len(re.findall(r"\b(?:btable|BookTable)\(", text)),
            "Figures": len(slots),
            "Figure attribution": slot_note,
            "A5 questions (validated)": q5,
            "Tamil A5 parity": "OK" if q5 == tamil_q else f"MISMATCH {q5} EN vs {tamil_q} TA",
            "MCQ items": 0,
            "Scientific accuracy issues": "None recorded; no expert review" if status != "VERIFIED" else rev.get("issues", ""),
            "Tamil terminology issues": "Not reviewed" if status != "VERIFIED" else rev.get("tamil", ""),
            "References": "Textbook pointers only; page numbers to be added by reviewer" if placeholder_refs else "NONE IN SOURCE",
            "Status": status,
            "Reviewer and evidence": (f"{rev.get('reviewer')}: {rev.get('evidence')}" if verified else "NONE"),
        })
    assert len(rows) == 43, len(rows)
    counts = {s: sum(r["Status"] == s for r in rows) for s in ("VERIFIED", "PROVISIONAL", "DISPUTED", "MISSING")}
    csv_path = ROOT / "native-app/ACADEMIC_AUDIT_REGISTER.csv"
    md_path = ROOT / "native-app/ACADEMIC_AUDIT_REGISTER.md"
    md = ["# Academic audit register, 43 validated chapters", "",
          "Generated by `tools/build_audit_register.py` from repository files. It records what the source holds; it is not a scientific review.",
          "Status is VERIFIED only with a named reviewer and evidence in `qa-intake/five-unit-academic-reviews.json`.", "",
          f"Totals: VERIFIED {counts['VERIFIED']}, PROVISIONAL {counts['PROVISIONAL']}, DISPUTED {counts['DISPUTED']}, MISSING {counts['MISSING']}.", "",
          "| Unit | Chapter | Sections | Defs | Tables | Figs | A5 | MCQ | Figure attribution | Status |", "|---|---|---|---|---|---|---|---|---|---|"]
    for r in rows:
        md.append(f"| {r['Unit']} | {r['Chapter ID']} | {r['Sections']} | {r['Definitions']} | {r['Tables']} | {r['Figures']} | "
                  f"{r['A5 questions (validated)']} | {r['MCQ items']} | {r['Figure attribution'].replace('|','/')} | {r['Status']} |")
    md += ["", "Columns not shown here (scientific accuracy issues, Tamil terminology issues, references, reviewer and evidence) are in the CSV.", ""]
    # Priority figures named for replacement review; rows only list what the source holds.
    priority = ("fission", "conjugation", "pila", "penaeus", "asterias", "sycon", "obelia")
    md += ["## Illustration replacement register (priority figures)", "",
           "Lists every figure slot used by a chapter whose slot or chapter id names a priority subject. "
           "'Attribution' is what the source records; no figure here has passed anatomical review.", "",
           "| Chapter | Slot | Attribution | Review |", "|---|---|---|---|"]
    for unit, cid, body in chapters():
        src = sources.get(cid)
        for slot in dict.fromkeys(re.findall(r'(?:bfig|BookFigure)\(\s*"([^"]+)"', src[1] if src else "")):
            if any(k in slot.lower() or k in cid for k in priority):
                md.append(f"| {cid} | {slot} | {credit_class(slot, table)} | NOT REVIEWED (0 reviews on record) |")
    md.append("")
    if check:
        assert csv_path.exists() and md_path.exists(), "register not generated"
        print("register present"); return
    with open(csv_path, "w", newline="", encoding="utf-8") as fh:
        w = csv.DictWriter(fh, fieldnames=list(rows[0])); w.writeheader(); w.writerows(rows)
    md_path.write_text("\n".join(md), encoding="utf-8")
    print(counts, "->", md_path.name)
    mism = [r["Chapter ID"] for r in rows if r["Tamil A5 parity"] != "OK"]
    miss = [r["Chapter ID"] for r in rows if r["Lesson source"] == "NONE"]
    nocred = [r["Chapter ID"] for r in rows if "NO CREDIT" in r["Figure attribution"]]
    print("Tamil A5 mismatch:", mism); print("no lesson source:", miss); print("figures without credit:", nocred)

main()
