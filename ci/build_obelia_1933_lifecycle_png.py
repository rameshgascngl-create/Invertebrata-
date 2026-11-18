#!/usr/bin/env python3
"""Make the *candidate* Obelia life-history PNG from real scanned line artwork.

Source is a scanned, hand-illustrated Obelia figure in Buchanan's 1933
Elements of Biology, figure 176 (Internet Archive Book Images, hosted by
Wikimedia Commons; image page has 'no known copyright restrictions').
The historical drawing is pasted at 1:1 source pixels, never upscaled.
The legend is text BELOW the scan; it does not alter anatomical lines.
Scientific and rights review remain independent prerequisites to release.
"""
from pathlib import Path
from io import BytesIO
import hashlib
import urllib.request
from PIL import Image, ImageDraw, ImageFont, ImageOps

SOURCE = (
    "https://upload.wikimedia.org/wikipedia/commons/0/09/"
    "Elements_of_biology%2C_with_special_reference_to_their_"
    "r%C3%B4le_in_the_lives_of_animals_%281933%29_%2821230103582%29.jpg"
)
CREDIT = "Buchanan (1933), Elements of Biology, Fig. 176 — Internet Archive / Wikimedia Commons"
OUTPUT = Path("native-app/app/src/main/assets/unit2/obelia_lifecycle.png")
SOURCE_DIMENSIONS = (1584, 2818)
EXPECTED_SOURCE_SHA256 = "003e1013f2f97103eecc98934723db61946d5f1704a711cfe5d00f8075c85cda"
CANVAS_DIMENSIONS = (2200, 3440)

def font(size, bold=False):
    names = (
        "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf" if bold
        else "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf",
        "/usr/share/fonts/truetype/liberation2/LiberationSerif-Bold.ttf"
        if bold else "/usr/share/fonts/truetype/liberation2/LiberationSerif-Regular.ttf",
    )
    for name in names:
        if Path(name).exists():
            return ImageFont.truetype(name, size)
    raise RuntimeError("The approved annotation font was not found")

def main():
    req = urllib.request.Request(SOURCE, headers={
        "User-Agent": "GASC-Zoology-Teaching-Atlas/1.0 (academic-source-review)"
    })
    with urllib.request.urlopen(req, timeout=35) as response:
        raw = response.read(6_000_000)
    assert len(raw) >= 50_000, "Source scan truncated"
    source_hash = hashlib.sha256(raw).hexdigest()
    assert source_hash == EXPECTED_SOURCE_SHA256, "Historical scan changed: refusing unreviewed replacement"
    with Image.open(BytesIO(raw)) as original:
        assert original.format == "JPEG", "Unexpected source scan format"
        assert original.size == SOURCE_DIMENSIONS, (
            f"Scan resolution changed unexpectedly: {original.size}"
        )
        scanned = ImageOps.grayscale(original).convert("RGB")
    page = Image.new("RGB", CANVAS_DIMENSIONS, "#fffdfa")
    draw = ImageDraw.Draw(page)
    ink = "#2d2925"
    subtle = "#67615c"
    draw.text((92, 42), "OBELIA — LIFE HISTORY",
              font=font(64, True), fill=ink)
    draw.text((94, 118),
              "Historical hand-drawn anatomical plate  |  Asexual colony and sexual medusae",
              font=font(29), fill=subtle)
    source_x = (CANVAS_DIMENSIONS[0] - scanned.width)//2
    source_y = 186
    # Keep every original source pixel: true historic line drawing, NOT SVG,
    # Compose Canvas, upscaling, crop of screenshots, or generic stock image.
    page.paste(scanned, (source_x, source_y))
    bottom_y = source_y + scanned.height + 16
    draw.line((78, bottom_y, 2122, bottom_y), fill="#7b726c", width=3)
    rows = [
        ["1  Gonangium & buds", "2  Free medusae", "3  Gametes", "4  Fertilisation"],
        ["5  Embryo", "6  Ciliated planula", "7  Settlement to new colony"],
    ]
    starts = [(90, bottom_y+19),(90, bottom_y+107)]
    for row_index, labels in enumerate(rows):
        y = starts[row_index][1]
        w = 495 if row_index==0 else 650
        for i, label in enumerate(labels):
            x = 90 + i*(w+16)
            draw.rounded_rectangle((x,y,x+w,y+66), radius=12,
                                   outline="#8a7e73", width=2, fill="#f9f6f1")
            draw.text((x+12,y+13),label,font=font(27,True),fill=ink)
    draw.text((86, 3365), CREDIT, font=font(22), fill=subtle)
    OUTPUT.parent.mkdir(parents=True,exist_ok=True)
    page.save(OUTPUT,format="PNG",optimize=True)
    with Image.open(OUTPUT) as check:
        assert check.size == CANVAS_DIMENSIONS and check.format == "PNG"
    print("OBELIA_SOURCE_URL=" + SOURCE)
    print("OBELIA_SOURCE_SHA256=" + source_hash)
    print("OBELIA_PNG_SHA256=" + hashlib.sha256(OUTPUT.read_bytes()).hexdigest())
    print("OBELIA_REAL_SCAN_PASTED_1_TO_1=PASS")
    print("OBELIA_BIOLOGY_AND_PROVENANCE_APPROVAL=OPEN")

if __name__ == "__main__":
    main()
