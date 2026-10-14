#!/usr/bin/env python3
"""Graphite-toned CDC diagram: faithful TONAL FACSIMILE, NOT a real pencil scan."""
import hashlib, random, json
from pathlib import Path
from PIL import Image, ImageOps, ImageFilter

TARGET=Path("native-app/app/src/main/assets/type_studies/ascaris/life-cycle.png")
META=Path("qa-evidence/unit3/source-backed-plate-manifest.json")
BASE_SHA="6fdc10fe1296238cf738b54592465f6185f4e9b77d7849339626eb15c56caac0"

def main():
    raw=TARGET.read_bytes()
    assert hashlib.sha256(raw).hexdigest()==BASE_SHA, "CDC source artwork identity changed"
    with Image.open(TARGET) as old:
        assert old.format=="PNG" and old.size==(960,1280)
        gray=ImageOps.grayscale(old.convert("RGB"))
    # IMPORTANT: pixel-local only. Preserve CDC contours, stage labels,
    # path arrows and all biological targets in exactly the source positions.
    gray=gray.filter(ImageFilter.UnsharpMask(radius=1.2,percent=85,threshold=3))
    rng=random.Random(20261014)
    src=gray.tobytes()
    rgb=bytearray(len(src)*3)
    for k,val in enumerate(src):
        micrograin=rng.randrange(-5,4) if val>217 else 0
        v=max(0,min(255,val+micrograin))
        rgb[3*k]=min(255,v+2)
        rgb[3*k+1]=v
        rgb[3*k+2]=max(0,v-3)
    Image.frombytes("RGB",(960,1280),bytes(rgb)).save(TARGET,"PNG",optimize=True)
    sha=hashlib.sha256(TARGET.read_bytes()).hexdigest()
    assert sha!=BASE_SHA
    with Image.open(TARGET) as check:
        assert check.format=="PNG" and check.size==(960,1280)
        for y in range(90,1180,73):
            for x in range(40,930,47):
                r,g,b=check.getpixel((x,y))
                assert abs(r-g)<=3 and abs(g-b)<=4,(x,y,r,g,b)
    manifest=json.loads(META.read_text(encoding="utf-8"))
    rec=[r for r in manifest["source_records"]
         if r["organism"]=="ascaris" and r["plate_id"]=="life-cycle"]
    assert len(rec)==1 and rec[0]["output_sha256"]==BASE_SHA
    rec[0].update({
        "original_full_color_master_sha256":BASE_SHA,
        "output_sha256":sha,
        "tonal_style":"GRAPHITE_TONED_CDC_PIXEL_FACSIMILE",
        "pencil_style_facsimile":True,
        "pencil_origin_verified":False,
        "source_geometry_unchanged":True,
        "anatomy_academic_review":"PENDING",
        "hotspot_academic_review":"PENDING",
        "image_interpretation_note":"Pixel-local monochrome adaptation only: NOT a real pencil-drawn master."
    })
    META.write_text(json.dumps(manifest,indent=2,ensure_ascii=False),encoding="utf-8")
    print("GRAPHITE_CDC_MASTER_SHA256="+sha)
    print("PENCIL_ORIGIN=UNVERIFIED; SCIENTIFIC_APPROVAL=HOLD; REAL_DEVICE=HOLD")

if __name__=="__main__": main()
