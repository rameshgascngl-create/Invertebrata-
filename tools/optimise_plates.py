#!/usr/bin/env python3
"""Non-destructive plate optimiser for release packaging.

NEVER modifies the source plates (they are hash-frozen and covered by tests).
It reads native-app/app/src/main/assets and writes optimised copies, plus a
manifest, to a separate output directory that a release pipeline can overlay.

Modes
  lossless (default)  pixel-identical WebP; ~25-30 % smaller. Verified per file.
  lossy               WebP q90, longest edge <= 2560 px (the cap docs/ADDING_PLATES.md
                      already allows); ~80 % smaller. Review labelled plates visually.

Debug-only review material (review_only/, paramecium/fission/) is skipped.

Usage
  python tools/optimise_plates.py --out build/optimised-assets            # lossless
  python tools/optimise_plates.py --out build/optimised-assets --mode lossy
  python tools/optimise_plates.py --dry-run                               # sizes only

Requires: pip install pillow
Note: the app resolves a plate by stem with either .png or .webp; an overlay must
therefore REPLACE the .png (never ship both for one stem) or the packaged-inventory
test "Two incompatible raster formats compete" will fail.
"""
import argparse, hashlib, io, json, os, sys
from pathlib import Path
from PIL import Image, ImageChops

SKIP = ("review_only", "paramecium/fission")
EXT = (".png", ".webp", ".jpg", ".jpeg")


def encode(img, mode, quality, max_edge):
    buf = io.BytesIO()
    if mode == "lossless":
        img.save(buf, "WEBP", lossless=True, quality=75, method=4)
    else:
        w, h = img.size
        r = min(1.0, max_edge / max(w, h))
        if r < 1.0:
            img = img.resize((round(w * r), round(h * r)), Image.LANCZOS)
        img.save(buf, "WEBP", quality=quality, method=4)
    return buf.getvalue()


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--assets", default="native-app/app/src/main/assets")
    ap.add_argument("--out", default="build/optimised-assets")
    ap.add_argument("--mode", choices=("lossless", "lossy"), default="lossless")
    ap.add_argument("--quality", type=int, default=90)
    ap.add_argument("--max-edge", type=int, default=2560)
    ap.add_argument("--dry-run", action="store_true")
    a = ap.parse_args()

    root, out = Path(a.assets), Path(a.out)
    if not root.is_dir():
        sys.exit(f"assets directory not found: {root}")
    rows, before, after = [], 0, 0
    for p in sorted(root.rglob("*")):
        rel = p.relative_to(root).as_posix()
        if p.suffix.lower() not in EXT or any(s in rel for s in SKIP):
            continue
        size = p.stat().st_size
        img = Image.open(p)
        img.load()
        img = img.convert("RGBA" if "A" in img.getbands() else "RGB")
        data = encode(img, a.mode, a.quality, a.max_edge)
        if len(data) >= size:                      # never make a file bigger
            data, kept = p.read_bytes(), True
        else:
            kept = False
        if a.mode == "lossless" and not kept:      # prove pixel identity
            back = Image.open(io.BytesIO(data)).convert(img.mode)
            if ImageChops.difference(back, img).getbbox() is not None:
                sys.exit(f"LOSSLESS CHECK FAILED: {rel}")
        target = rel if kept else str(Path(rel).with_suffix(".webp").as_posix())
        before += size
        after += len(data)
        rows.append({"source": rel, "output": target, "bytes_before": size, "bytes_after": len(data),
                     "sha256_after": hashlib.sha256(data).hexdigest(), "kept_original": kept})
        if not a.dry_run:
            dst = out / target
            dst.parent.mkdir(parents=True, exist_ok=True)
            dst.write_bytes(data)
    summary = {"mode": a.mode, "files": len(rows), "mb_before": round(before / 1e6, 1),
               "mb_after": round(after / 1e6, 1), "saved_percent": round(100 * (1 - after / max(before, 1)), 1)}
    if not a.dry_run:
        out.mkdir(parents=True, exist_ok=True)
        (out / "optimisation-manifest.json").write_text(json.dumps({"summary": summary, "files": rows}, indent=1))
        print(f"wrote {len(rows)} files to {out}")
    print(json.dumps(summary))


if __name__ == "__main__":
    main()
