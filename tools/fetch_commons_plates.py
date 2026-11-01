#!/usr/bin/env python3
"""Verify and stage openly licensed Wikimedia Commons images as plate candidates.

    python tools/fetch_commons_plates.py "File:Paramecium diagram.svg" \
        --slot unit1/concepts/paramecium_nutrition [--allow-sa] [--width 2048]

Run on YOUR machine (needs internet). For each file it queries the Commons
imageinfo/extmetadata API, REFUSES anything whose licence is not on the allow
list, downloads a render at --width px, flattens it to PNG on white, and writes
into  staging/commons/<slot>.png  plus  <slot>.provenance.json (author, licence,
source URL, SHA-256 of the staged bytes, credit line).

It never writes into app assets and never approves anatomy, Tamil labels or
rights. A zoologist must still compare the image with the plate brief, and the
file must be moved to its exact stem path by hand (then plate_intake.py check).
Requires Pillow and requests-free stdlib networking.
"""
import argparse, hashlib, io, json, re, sys, urllib.parse, urllib.request
from pathlib import Path

API = "https://commons.wikimedia.org/w/api.php"
UA = "INVERTEBRATA-plate-staging/1.0 (GASC Nagercoil; educational, offline app)"
SAFE = {"public domain", "pd", "cc0", "cc0 1.0", "cc-by", "cc by", "pdm", "no restrictions"}
SHARE_ALIKE = {"cc-by-sa", "cc by-sa"}   # allowed only with --allow-sa (app must carry the credit)
OUT = Path(__file__).resolve().parents[1] / "staging" / "commons"


def classify(licence_short: str, licence_url: str = "") -> str:
    """Return 'ok', 'share-alike' or 'refuse'. Non-commercial / no-derivatives are always refused."""
    s = f"{licence_short} {licence_url}".lower()
    if re.search(r"\b(nc|nd)\b|non-?commercial|no-?deriv|fair use|all rights", s):
        return "refuse"
    if "sa" in re.findall(r"[a-z]+", s) and "cc" in s:
        return "share-alike"
    if "public domain" in s or re.search(r"\bpd\b|cc0|publicdomain", s):
        return "ok"
    if re.search(r"cc[- ]by(?![- ]?(sa|nc|nd))", s):
        return "ok"
    return "refuse"


def strip_html(x: str) -> str:
    return re.sub(r"\s+", " ", re.sub(r"<[^>]+>", "", x or "")).strip()


def parse_info(payload: dict) -> dict:
    page = next(iter(payload["query"]["pages"].values()))
    if "imageinfo" not in page:
        raise ValueError("file not found on Commons: " + page.get("title", "?"))
    ii = page["imageinfo"][0]
    md = ii.get("extmetadata", {})
    g = lambda k: strip_html(md.get(k, {}).get("value", ""))
    return {
        "title": page["title"], "url": ii.get("thumburl") or ii["url"], "original_url": ii["url"],
        "page_url": ii.get("descriptionurl", ""), "width": ii.get("width"), "height": ii.get("height"),
        "licence": g("LicenseShortName"), "licence_url": g("LicenseUrl"),
        "author": g("Artist"), "credit": g("Credit"), "restrictions": g("Restrictions"),
    }


def http(url: str) -> bytes:
    req = urllib.request.Request(url, headers={"User-Agent": UA})
    with urllib.request.urlopen(req, timeout=60) as r:
        return r.read()


def query(title: str, width: int) -> dict:
    q = {"action": "query", "format": "json", "titles": title, "prop": "imageinfo",
         "iiprop": "url|size|sha1|extmetadata", "iiurlwidth": width}
    return parse_info(json.loads(http(API + "?" + urllib.parse.urlencode(q))))


def stage(info: dict, slot: str, allow_sa: bool, fetch=http) -> Path:
    verdict = classify(info["licence"], info["licence_url"])
    if verdict == "refuse":
        raise SystemExit(f"REFUSED {info['title']}: licence '{info['licence']}' is not on the allow list")
    if verdict == "share-alike" and not allow_sa:
        raise SystemExit(f"HELD {info['title']}: '{info['licence']}' is share-alike; re-run with --allow-sa "
                         "only if you accept publishing the credit and licence in the app")
    if not info["author"]:
        raise SystemExit(f"REFUSED {info['title']}: no author recorded; attribution cannot be written")
    from PIL import Image
    im = Image.open(io.BytesIO(fetch(info["url"])))
    if im.mode in ("RGBA", "LA", "P"):
        im = im.convert("RGBA")
        bg = Image.new("RGB", im.size, "white"); bg.paste(im, mask=im.split()[-1]); im = bg
    else:
        im = im.convert("RGB")
    OUT.mkdir(parents=True, exist_ok=True)
    name = slot.replace("/", "__")
    png = OUT / f"{name}.png"
    im.save(png, optimize=True)
    rec = {**info, "slot": slot, "staged_file": png.name, "staged_width": im.width, "staged_height": im.height,
           "sha256": hashlib.sha256(png.read_bytes()).hexdigest(), "licence_verdict": verdict,
           "credit_line": f"{info['author']} / Wikimedia Commons / {info['licence']}",
           "review_status": "UNREVIEWED: anatomy, Tamil labels and rights need independent sign-off"}
    (OUT / f"{name}.provenance.json").write_text(json.dumps(rec, indent=1, ensure_ascii=False), encoding="utf-8")
    return png


def main(argv=None):
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("title"); ap.add_argument("--slot", required=True)
    ap.add_argument("--allow-sa", action="store_true"); ap.add_argument("--width", type=int, default=2048)
    a = ap.parse_args(argv)
    if not a.title.startswith("File:"): ap.error("title must start with 'File:'")
    info = query(a.title, a.width)
    print(f"{info['title']}: {info['licence']} by {info['author']} ({info['width']}x{info['height']})")
    print("staged:", stage(info, a.slot, a.allow_sa))
    print("Now: compare with the brief, then move to the exact stem path and run plate_intake.py check.")


if __name__ == "__main__":
    main()
