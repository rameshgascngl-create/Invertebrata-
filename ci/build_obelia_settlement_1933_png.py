#!/usr/bin/env python3
"""Build labelled Obelia settlement pencil plate from Wolcott's historic scan.

No SVG, no software-generated organism shapes, no screenshot crop and no
resampling of the original scanned organism drawing.
The source is a public-domain, hand-drawn Fig. 66 from Robert Henry Wolcott,
Animal Biology, McGraw-Hill, 1933, via Internet Archive Book Images / Flickr.
Scientific peer review is a separate release requirement.
"""
from pathlib import Path
from io import BytesIO
import hashlib
import urllib.request
from PIL import Image, ImageDraw, ImageFont

URL = "https://live.staticflickr.com/7757/18197697381_d89d1d6e91_b.jpg"
SHA256 = "c47f9514827b1407ababed351a52aea6e975d4482569c1caa36e19c9e6a67f9e"
SOURCE_DIMS = (1024, 814)
OUTPUT_DIMS = (1500, 1250)
OUTPUT = Path("native-app/app/src/main/assets/unit2/obelia_settlement.png")

def font(size, bold=False):
    family = "/usr/share/fonts/truetype/dejavu/DejaVuSans"
    return ImageFont.truetype(family + ("-Bold" if bold else "") + ".ttf", size)

def main():
    req = urllib.request.Request(URL, headers={"User-Agent":
        "Mozilla/5.0 (academic scan verification; zoology teaching asset)"})
    with urllib.request.urlopen(req, timeout=40) as response:
        data = response.read(2_500_000)
    assert hashlib.sha256(data).hexdigest() == SHA256, (
        "The archival illustration changed: refuse unreviewed source bytes"
    )
    with Image.open(BytesIO(data)) as src:
        assert src.format == "JPEG" and src.size == SOURCE_DIMS
        source = src.convert("RGB")

    plate = Image.new("RGB", OUTPUT_DIMS, (252, 250, 246))
    pencil = (35, 32, 30)
    secondary = (80, 73, 69)
    d = ImageDraw.Draw(plate)
    d.text((100, 25), "OBELIA — PLANULA SETTLEMENT & PRIMARY POLYP",
           font=font(38, True), fill=pencil)
    d.text((100, 86),
           "Historical hand-drawn anatomy • Wolcott (1933), Animal Biology, Fig. 66",
           font=font(22), fill=secondary)
    # SOURCE PIXELS MUST REMAIN UNRESAMPLED. The published Fig.66
    # retains its original leader lines and D / E / F / A captions.
    x0 = (OUTPUT_DIMS[0]-source.width)//2
    y0 = 143
    plate.paste(source, (x0,y0))
    d.line((80,1004,OUTPUT_DIMS[0]-80,1004),fill=(120,110,98),width=2)
    cards = (
        ("D  Swimming planula", "Free-swimming, ciliated larva"),
        ("E  Attachment", "Planula settles and transforms"),
        ("F  Primary polyp", "Hydranth and nascent hydrorhiza"),
    )
    for n,(title,description) in enumerate(cards):
        xx=70+n*480; yy=1035
        d.rounded_rectangle((xx,yy,xx+440,yy+125),radius=12,
            outline=(136,126,110),width=2,fill=(247,245,240))
        d.text((xx+18,yy+17),title,font=font(25,True),fill=pencil)
        d.text((xx+18,yy+67),description,font=font(21),fill=(70,62,55))
    d.text((70,1200),
        "Source: public-domain zoology scan (1933). Scientific approval pending.",
        font=font(18), fill=(100,92,83))
    OUTPUT.parent.mkdir(parents=True,exist_ok=True)
    plate.save(OUTPUT,format="PNG", optimize=True)
    with Image.open(OUTPUT) as result:
        assert result.format=="PNG" and result.size==OUTPUT_DIMS
    print(f"OBELIA_SETTLEMENT_SOURCE_SHA256={SHA256}")
    print(f"OBELIA_SETTLEMENT_PNG_SHA256={hashlib.sha256(OUTPUT.read_bytes()).hexdigest()}")
    print("OBELIA_SETTLEMENT_PENCIL_DRAWING_1TO1=PASS; APPROVAL=PENDING")

if __name__ == "__main__":
    main()
