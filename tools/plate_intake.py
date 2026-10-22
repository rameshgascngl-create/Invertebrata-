#!/usr/bin/env python3
"""INVERTEBRATA plate intake: add or audit anatomical plate rasters safely.

    python tools/plate_intake.py list
    python tools/plate_intake.py check
    python tools/plate_intake.py add --organism pila --plate torsion --src my.png \
        --license "CC0 1.0" --author "Name" --source-url https://... --ai-generated no

`add` converts to a lossless WebP (or PNG), flattens transparency onto white,
caps the longest edge, refuses unknown slot names, refuses byte-identical
duplicates and double formats, and records provenance in provenance/plates.json.
It makes the Gradle tests pass; it never approves science, Tamil or rights.
Requires Pillow (pip install pillow).
"""
import argparse, datetime, hashlib, json, re, sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MANIFEST = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/NativeCurriculumManifest.kt"
ASSETS = ROOT / "native-app/app/src/main/assets/type_studies"
PROV = ROOT / "provenance/plates.json"
CREDITS = ASSETS / "plate_credits.json"
MAX_EDGE_DEFAULT, MAX_EDGE_HARD, MIN_EDGE = 2560, 4096, 256
EXTRAS = {"pila/shell-specimen-reference", "pila/swainson-1821-pila-ampullacea"}


def slots():
    text = MANIFEST.read_text(encoding="utf-8")
    out = {}
    for org, plates in re.findall(r'TypeStudyRequirement\("([a-z]+)",.*?listOf\(([^)]*)\)', text, re.S):
        out[org] = re.findall(r'"([a-z0-9-]+)"', plates)
    if sum(len(v) for v in out.values()) != 35:
        sys.exit("Could not parse 35 slots from NativeCurriculumManifest.kt - parser needs updating")
    return out


def images():
    return sorted(p for p in ASSETS.rglob("*") if p.is_file() and p.suffix.lower() in {".png", ".webp", ".jpg", ".jpeg", ".gif"})


def stem(p):
    return p.relative_to(ASSETS).with_suffix("").as_posix()


def sha(p):
    return hashlib.sha256(p.read_bytes()).hexdigest()


def problems():
    from PIL import Image
    valid = {f"{o}/{p}" for o, ps in slots().items() for p in ps} | EXTRAS
    errs, seen, by_stem = [], {}, {}
    for p in images():
        by_stem.setdefault(stem(p), []).append(p)
        if p.suffix not in {".png", ".webp"}:
            errs.append(f"{p.relative_to(ROOT)}: use lowercase .png or .webp")
        if stem(p) not in valid:
            errs.append(f"{p.relative_to(ROOT)}: unknown plate id (valid: {sorted(valid)})")
        w, h = Image.open(p).size
        if max(w, h) > MAX_EDGE_HARD:
            errs.append(f"{p.relative_to(ROOT)}: {w}x{h} exceeds {MAX_EDGE_HARD}px")
        if min(w, h) < MIN_EDGE:
            errs.append(f"{p.relative_to(ROOT)}: {w}x{h} below {MIN_EDGE}px")
        d = sha(p)
        if d in seen:
            errs.append(f"{stem(p)} is byte-identical to {seen[d]}")
        seen[d] = stem(p)
    for s, files in by_stem.items():
        if len(files) > 1:
            errs.append(f"{s}: both formats present {[f.name for f in files]}")
    return errs


def build_credits(prov):
    """Write the in-app credit file from provenance entries (shown under each plate)."""
    out = {}
    for slot, e in sorted(prov["plates"].items()):
        en = e.get("credit_en") or f"Source: {e['author']} \u00b7 {e['license']}"
        ta = e.get("credit_ta") or f"\u0bae\u0bc2\u0bb2\u0bae\u0bcd: {e['author']} \u00b7 {e['license']}"
        out[slot] = {"en": en, "ta": ta}
    CREDITS.write_text(json.dumps(out, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")


def cmd_list(_):
    from PIL import Image
    present = {stem(p): p for p in images()}
    miss = 0
    for org, plates in slots().items():
        for pl in plates:
            p = present.get(f"{org}/{pl}")
            if p:
                w, h = Image.open(p).size
                print(f"  OK       {org}/{pl:<26} {p.suffix[1:]:4} {w}x{h}  {p.stat().st_size // 1024} KB")
            else:
                miss += 1
                print(f"  MISSING  {org}/{pl}")
    print(f"\n{35 - miss}/35 present, {miss} missing")


def cmd_check(_):
    errs = problems()
    print("\n".join(errs) if errs else "All packaged plates pass the intake rules.")
    return 1 if errs else 0


def cmd_add(a):
    from PIL import Image
    table = slots()
    if a.organism not in table or a.plate not in table[a.organism]:
        sys.exit(f"'{a.organism}/{a.plate}' is not a slot. Slots for {a.organism}: {table.get(a.organism)}")
    if not a.license.strip() or not a.author.strip():
        sys.exit("--license and --author are required (use 'original work' for your own drawing)")
    if a.ai_generated == "yes" and not a.generator:
        sys.exit("--generator is required when --ai-generated yes")
    im = Image.open(a.src)
    im.load()
    if im.mode in ("RGBA", "LA", "P"):
        im = im.convert("RGBA")
        bg = Image.new("RGB", im.size, "white")
        bg.paste(im, mask=im.split()[-1])
        im = bg
    else:
        im = im.convert("RGB")
    if max(im.size) > a.max_edge:
        k = a.max_edge / max(im.size)
        im = im.resize((round(im.width * k), round(im.height * k)), Image.LANCZOS)
    if min(im.size) < MIN_EDGE:
        sys.exit(f"Image is only {im.size}; at least {MIN_EDGE}px on the short edge is required")
    dest_dir = ASSETS / a.organism
    dest_dir.mkdir(parents=True, exist_ok=True)
    dest = dest_dir / f"{a.plate}.{a.format}"
    other = dest.with_suffix(".png" if a.format == "webp" else ".webp")
    if other.exists():
        if not a.replace:
            sys.exit(f"{other.name} already exists; use --replace to swap formats")
        other.unlink()
    if dest.exists() and not a.replace:
        sys.exit(f"{dest.name} already exists; use --replace to overwrite")
    if a.format == "webp":
        im.save(dest, "WEBP", lossless=True, quality=100, method=6)
    else:
        im.save(dest, "PNG", optimize=True)
    clash = [stem(p) for p in images() if p != dest and sha(p) == sha(dest)]
    if clash:
        dest.unlink()
        sys.exit(f"Refused: identical bytes already used for {clash}. Each slot needs its own figure.")
    prov = json.loads(PROV.read_text(encoding="utf-8")) if PROV.exists() else {"plates": {}}
    prov["plates"][f"{a.organism}/{a.plate}"] = {
        "file": dest.relative_to(ROOT).as_posix(), "sha256": sha(dest), "width": im.width,
        "height": im.height, "bytes": dest.stat().st_size, "license": a.license, "author": a.author,
        "source_url": a.source_url, "ai_generated": a.ai_generated == "yes", "generator": a.generator,
        "notes": a.notes, "credit_en": a.credit_en, "credit_ta": a.credit_ta, "review_status": "DRAFT_UNVERIFIED",
        "added_utc": datetime.datetime.now(datetime.timezone.utc).isoformat(timespec="seconds"),
    }
    PROV.parent.mkdir(exist_ok=True)
    PROV.write_text(json.dumps(prov, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    build_credits(prov)
    print(f"Added {dest.relative_to(ROOT)}  {im.width}x{im.height}  {dest.stat().st_size // 1024} KB")
    errs = problems()
    print("\n".join(errs) if errs else "Inventory check: OK")
    return 1 if errs else 0


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    sp = ap.add_subparsers(dest="cmd", required=True)
    sp.add_parser("list").set_defaults(fn=cmd_list)
    sp.add_parser("check").set_defaults(fn=cmd_check)
    sp.add_parser("credits", help="regenerate plate_credits.json from provenance").set_defaults(
        fn=lambda _a: build_credits(json.loads(PROV.read_text(encoding="utf-8"))))
    x = sp.add_parser("add")
    x.add_argument("--organism", required=True)
    x.add_argument("--plate", required=True)
    x.add_argument("--src", required=True)
    x.add_argument("--license", required=True)
    x.add_argument("--author", required=True)
    x.add_argument("--source-url", default="")
    x.add_argument("--ai-generated", choices=["yes", "no"], required=True)
    x.add_argument("--generator", default="")
    x.add_argument("--notes", default="")
    x.add_argument("--credit-en", default="", help="exact English credit line shown under the plate")
    x.add_argument("--credit-ta", default="", help="exact Tamil credit line (default: template)")
    x.add_argument("--format", choices=["webp", "png"], default="webp")
    x.add_argument("--max-edge", type=int, default=MAX_EDGE_DEFAULT)
    x.add_argument("--replace", action="store_true")
    x.set_defaults(fn=cmd_add)
    a = ap.parse_args()
    sys.exit(a.fn(a) or 0)


if __name__ == "__main__":
    main()
